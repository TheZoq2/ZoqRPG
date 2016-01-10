package org.galaxycraft.thezoq2.zoqrpg.factories;

import org.bukkit.Bukkit;
import org.galaxycraft.thezoq2.zoqrpg.CloneableObject;
import org.galaxycraft.thezoq2.zoqrpg.exceptions.FactoryCreationFailedException;
import org.galaxycraft.thezoq2.zoqrpg.exceptions.NoSuchTemplateObjectException;
import org.galaxycraft.thezoq2.zoqrpg.exceptions.NoSuchVariableException;
import org.galaxycraft.thezoq2.zoqrpg.exceptions.WrongDatatypeException;
import org.galaxycraft.thezoq2.zoqrpg.fileio.StringValue;
import org.galaxycraft.thezoq2.zoqrpg.fileio.StructBasedObject;
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

    boolean setup = false; //Debug variable used to check if the structs have been read when creating objects. Used by assertions

    /**
     * Intialise the factory
     * @param baseStruct the struct to base the object creation on
     */
    protected StructBasedFactory(StructValue baseStruct)
    {
        this.baseStruct = baseStruct;

        this.objectTemplates = new HashMap<>();

    }

    /**
     * Returns the value of the 'base' variable in the passed struct value
     * @param sv The struct value to look for a base variable in
     * @return the value of the variable
     * @throws FactoryCreationFailedException if the base value doesn't exist or isn't a string
     */
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

    /**
     * Creates template Cloneables from the struct that can later be cloned in order to create new objects
     */
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

        setup = true;
    }

    /**
     * Clones a prexisting template object
     * @param name the name of the object in the struct and internal structure
     * @return the newly created object
     * @throws NoSuchTemplateObjectException if the object doesn't exist in the internal structure. Either because it was
     * malformed in the struct or didn't exist at all
     */
    //Creates a cloned version of the object in the list if such an object exists, if not, an exception is thrown
    protected T createObject(String name) throws NoSuchTemplateObjectException
    {
        assert(name != null);
        assert(setup = true);

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

    /**
     * Function for subclasses to implements that defines how to create objects from the StructValue
     * @param sv The struct value to create an object based on
     * @return the newly created template object
     * @throws FactoryCreationFailedException if the creation of the object fails
     */
    protected abstract T createObjectFromStruct(StructValue sv) throws FactoryCreationFailedException;
}
