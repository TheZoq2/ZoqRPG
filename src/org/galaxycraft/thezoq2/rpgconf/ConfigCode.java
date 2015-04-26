package org.galaxycraft.thezoq2.rpgconf;

import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.text.BadLocationException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by frans on 26/04/15.
 */
public class ConfigCode implements DocumentListener
{
    //Static stuff
    private static final Map<StructType, String> structNames; //Map from struct types to names which is used by this class to add code to the right place
    //and by other classes to create UI for adding things
    static
    {
        structNames = new HashMap<>();

        structNames.put(StructType.BOON, "boons");
        structNames.put(StructType.MOVER, "movers");
        structNames.put(StructType.SPELL, "spells");
        structNames.put(StructType.VOLUME, "volumes");
        structNames.put(StructType.VISUALISER, "visualisers");
    }


    private StringBuilder code;
    private List<CodeUpdateListener> listeners;

    public ConfigCode()
    {
        listeners = new ArrayList<>();
        code = new StringBuilder("");

        //Adding top level structs to the code
        for(StructType st : StructType.values())
        {
            addEmptyStruct(structNames.get(st));
        }
    }

    public void addEmptyStruct(String name)
    {
        code.append(name);
        code.append(" = \n{\n}\n");

        notifyListeners();
    }

    public void addStruct(StructPrototype struct, StructType type)
    {
        int addIndex = getStartOfTopStruct(type);

        code.insert(addIndex, struct.toString());

        notifyListeners();
    }

    @Override
    public String toString()
    {
        return code.toString();
    }

    public void addUpdateListener(CodeUpdateListener listener)
    {
        this.listeners.add(listener);
    }

    public void notifyListeners()
    {
        for(CodeUpdateListener listener : listeners)
        {
            listener.onCodeUpdate();
        }
    }

    private int getStartOfTopStruct(StructType st)
    {
        String structName = structNames.get(st);
        int structStartIndex = code.indexOf(structName);

        if(structStartIndex == -1) //The struct doesn't exist for some reason, create it
        {
            addEmptyStruct(structName);
            return code.lastIndexOf("}") - 1;
        }

        //Finding the already existing struct
        int bracketIndex = code.indexOf("{", structStartIndex) + 1; //+1 to skip the \n

        if(bracketIndex != -1)
        {
            return bracketIndex;
        }
        else
        {
            //Reused code, to little time to fix. //TODO
            addEmptyStruct(structName);
            return code.lastIndexOf("}") - 1;
        }
    }

    @Override
    public void insertUpdate(DocumentEvent documentEvent)
    {
        onTextareaChange(documentEvent);
    }

    @Override
    public void removeUpdate(DocumentEvent documentEvent)
    {
        onTextareaChange(documentEvent);
    }

    @Override
    public void changedUpdate(DocumentEvent documentEvent)
    {
        onTextareaChange(documentEvent);
    }

    public void onTextareaChange(DocumentEvent documentEvent)
    {
        try
        {
            String text = documentEvent.getDocument().getText(0, documentEvent.getDocument().getLength());

            this.code = new StringBuilder(text);
        } catch (BadLocationException e)
        {
            e.printStackTrace();
        }
    }
}
