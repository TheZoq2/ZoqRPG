package org.galaxycraft.thezoq2.zoqrpg.fileio;

import org.galaxycraft.thezoq2.zoqrpg.exceptions.NoSuchVariableException;
import org.galaxycraft.thezoq2.zoqrpg.exceptions.StructContainsVariableException;
import org.galaxycraft.thezoq2.zoqrpg.exceptions.WrongDatatypeException;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class StructValue extends BaseDataValue
{
    private static final String TYPE_NAME = "struct";

    private Map<String, DataValue> values;

    public StructValue(String varName, StructValue parentStruct)
    {
        super(varName, parentStruct);
        this.values = new HashMap<>();
    }

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


    public <T extends DataValue> T getVariableOfTypeByName(String name, Class<T> type) throws NoSuchVariableException, WrongDatatypeException
    {
        DataValue val = getVariableByName(name);

        //Checking if the variable has the right type
        if(!val.getClass().equals(type))
        {
            throw new WrongDatatypeException(val, T.TYPE_NAME);
        }

        //This warning is wrong, the statement above does check if the cast will work
        //noinspection unchecked
        return (T)val;
    }

    public <T extends DataValue> Map<String, T> getAllVariablesOfType(Class<T> type)
    {
        Map<String, T> result = new HashMap<>();

        for(String key : values.keySet())
        {
            DataValue value = values.get(key);

            //Checking if the variable is what we are looking for
            if(value.getClass().equals(type))
            {
                //Adding it to the list of variables
                result.put(key, (T) value);
            }
        }

        return result;
    }
}
