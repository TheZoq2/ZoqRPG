package org.galaxycraft.thezoq2.zoqrpg.fileio;

import org.galaxycraft.thezoq2.zoqrpg.exceptions.NoSuchVariableException;
import org.galaxycraft.thezoq2.zoqrpg.exceptions.StructContainsVariableException;

import java.util.HashMap;
import java.util.Map;

public class StructValue implements DataValue
{
    private String structName;
    private StructValue parentStruct;

    private Map<String, DataValue> values;

    public StructValue(String structName, StructValue parentStruct)
    {
        this.values = new HashMap<>();
        this.structName = structName;
        this.parentStruct = parentStruct;
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

    public boolean variableExists(String name)
    {
        return values.containsKey(name);
    }

    public String getFullPath()
    {
        StringBuilder result = new StringBuilder();
        if(parentStruct != null)
        {
            result.append(parentStruct.getFullPath());
            result.append(".");
        }

        result.append(structName);

        return result.toString();
    }

    public void setStructName(String structName)
    {
        this.structName = structName;
    }

    public void setParentStruct(StructValue parentStruct)
    {
        this.parentStruct = parentStruct;
    }
}
