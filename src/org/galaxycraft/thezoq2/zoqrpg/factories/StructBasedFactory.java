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
 * Created by frans on 4/10/15.
 */


//TODO: Make factories load their content on reload instead of every time it is used. This will prevent the current console spam
public abstract class StructBasedFactory<T extends CloneableObject>
{
    private static final String BASE_VAR_NAME = "base";

    protected StructValue baseStruct;

    protected Map<String, T> objectTemplates;

    protected StructBasedFactory(StructValue baseStruct)
    {
        this.baseStruct = baseStruct;

        this.objectTemplates = new HashMap<>();

    }

    protected StructValue getStructByName(String name) throws FactoryCreationFailedException
    {
        //Get the struct with the specified name
        try
        {
            return baseStruct.getVariableOfTypeByName(name, StructValue.class);
        }
        catch (WrongDatatypeException e)
        {
            throw new FactoryCreationFailedException("Variable " + e.getVarPath() + " needs to be a " + e.getExpectedDatatype());
        }
        catch (NoSuchVariableException e)
        {
            throw new FactoryCreationFailedException("Expected variable " + e.getVarName() + " in " +
                    e.getStructPath() + " to create " + name);
        }
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

        for(String key : objectStructs.keySet())
        {
            try
            {
                T createdObject = createObjectFromStruct(objectStructs.get(key));

                objectTemplates.put(key, createdObject);
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
            return (T) objectTemplates.get(name).cloneObject();
        }
        else
        {
            throw new NoSuchTemplateObjectException(name);
        }
    }

    protected abstract T createObjectFromStruct(StructValue sv) throws FactoryCreationFailedException;
}
