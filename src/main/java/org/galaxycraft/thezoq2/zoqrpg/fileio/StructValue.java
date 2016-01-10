package org.galaxycraft.thezoq2.zoqrpg.fileio;

import org.galaxycraft.thezoq2.zoqrpg.exceptions.NoSuchVariableException;
import org.galaxycraft.thezoq2.zoqrpg.exceptions.StructContainsVariableException;
import org.galaxycraft.thezoq2.zoqrpg.exceptions.WrongDatatypeException;

import java.util.HashMap;
import java.util.Map;

/**
 * A DataValue which contains other DataValues stored as "variables".
 * The DataValues are stored in a map that maps their name in the struct to the datavalue. Contains a bunch
 * of different functions for returning specific variables.
 *
 * Extends BaseDataValue to inherit some general functions
 *
 * @see BaseDataValue
 * @see DataValue
 */
public class StructValue extends BaseDataValue
{
    private Map<String, DataValue> values;

    public StructValue(String varName, StructValue parentStruct)
    {
        super(varName, parentStruct);
        this.values = new HashMap<>();
    }

    /**
     * Add a new variable to the struct
     * @param name name of the variable
     * @param val value of the variable
     * @throws StructContainsVariableException if there is a variable in the struct with the same name already.
     */
    public void addVariable(String name, DataValue val) throws StructContainsVariableException
    {
        if(this.values.containsKey(name))
        {
            throw new StructContainsVariableException(name);
        }
        this.values.put(name, val);
    }

    public DataValue getVariableByName(String name) throws NoSuchVariableException
    {
        if(!values.containsKey(name))
        {
            throw new NoSuchVariableException(name, this);
        }

        return values.get(name);
    }


    /**
     * Returns the variable with the specified name if it is the specified datatype
     * @param name The name of the variable
     * @param type class of variable to datatype, should be a subclass of DataValue
     * @param <T>  the datatype of the variable
     * @return The variable if it exists and is the right type
     * @throws NoSuchVariableException if the variable doesn't exist
     * @throws WrongDatatypeException if the variable is the wrong datatype
     */
    public <T extends DataValue> T getVariableOfTypeByName(String name, Class<T> type) throws NoSuchVariableException, WrongDatatypeException
    {
        DataValue val = getVariableByName(name);

        //Checking if the variable has the right type
        if(!val.getClass().equals(type))
        {
            throw new WrongDatatypeException(val, "unknown");
        }

        //This warning is wrong, the statement above does check if the cast will work
        return (T)val;
    }

    /**
     * Returns all the variables in the struct of a specific datatype along with their name
     * @param type the class of the datatype to return
     * @param <T> the datatype
     * @return the map of variables
     */
    public <T extends DataValue> Map<String, T> getAllVariablesOfType(Class<T> type)
    {
        Map<String, T> result = new HashMap<>();

        for(Map.Entry<String, DataValue> entry : values.entrySet())
        {
            DataValue value = values.get(entry.getKey());

            //Checking if the variable is what we are looking for
            if(value.getClass().equals(type))
            {
                //Adding it to the list of variables
                result.put(entry.getKey(), (T) value); //The cast is safe because the if statement above does the cast check
            }
        }

        return result;
    }
}
