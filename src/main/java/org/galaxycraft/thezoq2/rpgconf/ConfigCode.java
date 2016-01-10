package org.galaxycraft.thezoq2.rpgconf;

import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.text.BadLocationException;
import java.util.ArrayList;
import java.util.List;

/**
 * Class that keeps track of and changes config file code.
 *
 * Implements DocumentListener to allow the code to be updated in a text area or similar
 */
public class ConfigCode implements DocumentListener
{
    //I read somewhere a while back that if you want to manipulate strings you should use a StringBuilder. This may
    //have changed but I don't have enough time to fix it
    private StringBuilder code;
    private List<CodeUpdateListener> listeners;

    public ConfigCode()
    {
        listeners = new ArrayList<>();
        code = new StringBuilder("");

        //Adding top level structs to the code
        for(StructType st : StructType.values())
        {
            addEmptyStruct(StructTypeTranslator.getStructConfigName(st));
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

        StringBuilder addedCode = new StringBuilder("");
        addedCode.append("name = \n");
        addedCode.append("{\n");
        addedCode.append(struct.getCode());
        addedCode.append("\n}\n");

        code.insert(addIndex, addedCode);

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
        String structName = StructTypeTranslator.getStructConfigName(st);
        int structStartIndex = code.indexOf(structName);

        if(structStartIndex == -1) //The struct doesn't exist for some reason, create it
        {
            addEmptyStruct(structName);
            return code.lastIndexOf("}") - 1;
        }

        //Finding the already existing struct
        int bracketIndex = code.indexOf("{", structStartIndex) + 2; //+1 to skip the \n

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
