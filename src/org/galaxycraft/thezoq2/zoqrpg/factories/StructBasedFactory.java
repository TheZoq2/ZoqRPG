package org.galaxycraft.thezoq2.zoqrpg.factories;

import org.bukkit.Bukkit;
import org.galaxycraft.thezoq2.zoqrpg.CloneableObject;
import org.galaxycraft.thezoq2.zoqrpg.exceptions.FactoryCreationFailedException;
import org.galaxycraft.thezoq2.zoqrpg.exceptions.NoSuchTemplateObjectException;
import org.galaxycraft.thezoq2.zoqrpg.exceptions.NoSuchVariableException;
import org.galaxycraft.thezoq2.zoqrpg.exceptions.WrongDatatypeException;
import org.galaxycraft.thezoq2.zoqrpg.fileio.StringValue;
import org.galaxycraft.thezoq2.zoqrpg.fileio.StructValue;

import java.util.HashMap;
import java.util.Map;
import java.util.logging.Level;

/**
 * Abstract class containing methods needed to propperly parse structs by factories aswell as functions for creating and
 * cloning template objects that are created by factories.
 *
 * @see CloneableObject
 */


public abstract class StructBasedFactory<T extends CloneableObject>
{
    protected StructValue baseStruct;

    protected Map<String, T> objectTemplates;

    protected StructBasedFactory(StructValue baseStruct)
    {
        this.baseStruct = baseStruct;

        this.objectTemplates = new HashMap<>();

    }

    protected String getBaseValueFromStruct(StructValue sv) throws FactoryCreationFailedException
    {
        try
        {
            return sv.getVariableOfTypeByName("base", StringValue.class).getValue();
        }
        catch (WrongDatatypeException e)
        {
            throw new FactoryCreationFailedException("Base value " + e.getVarPath() + " must be a string");
        }
        catch (NoSuchVariableException e)
        {
            throw new FactoryCreationFailedException("Struct: " + e.getVarName() + " does contain a base value");
        }
    }

    protected void createTemplateObjects()
    {
        Map<String, StructValue> objectStructs = baseStruct.getAllVariablesOfType(StructValue.class);

        //Im not sure why the Map. is needed, probably because there is another class called Entry that gets imported
        //using the * import statement in this file. See comment there for explanation
        for(Map.Entry<String, StructValue> entry : objectStructs.entrySet())
        {
            try
            {
                T createdObject = createObjectFromStruct(entry.getValue());

                objectTemplates.put(entry.getKey(), createdObject);
            } catch (FactoryCreationFailedException e)
            {
                Bukkit.getLogger().log(Level.WARNING, "Failed to create object template. " + e.getReason());
            }

        }
    }

    //Creates a cloned version of the object in the list if such an object exists, if not, an exception is thrown
    protected T createObject(String name) throws NoSuchTemplateObjectException
    {
        assert(name != null);

        if(objectTemplates.containsKey(name))
        {
            //I might need to make it clear that cloneObject should always clones the same object type. For now, this
            //will not fail because cloneObject does return an object of  the same type even if it's not guaranteed by the code
            return (T) objectTemplates.get(name).cloneObject();
        }
        else
        {
            throw new NoSuchTemplateObjectException(name);
        }
    }

    protected abstract T createObjectFromStruct(StructValue sv) throws FactoryCreationFailedException;
}
