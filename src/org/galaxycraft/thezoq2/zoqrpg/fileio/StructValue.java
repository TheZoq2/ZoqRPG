package org.galaxycraft.thezoq2.zoqrpg.fileio;

import org.galaxycraft.thezoq2.zoqrpg.exceptions.NoSuchVariableException;
import org.galaxycraft.thezoq2.zoqrpg.exceptions.StructContainsVariableException;
import org.galaxycraft.thezoq2.zoqrpg.exceptions.WrongDatatypeException;

import java.util.HashMap;
import java.util.Map;

public class StructValue extends BaseDataValue
{
    static String TYPE_NAME = "struct";

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
        if(!val.getClass().isInstance(type))
        {
            throw new WrongDatatypeException(val, T.TYPE_NAME);
        }

        return (T)val;
    }

    public boolean variableExists(String name)
    {
        return values.containsKey(name);
    }
}
