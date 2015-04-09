package org.galaxycraft.thezoq2.zoqrpg.fileio;

import org.galaxycraft.thezoq2.zoqrpg.exceptions.DuplicateVariableNameException;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by frans on 3/31/15.
 */

//TODO: Possibly change to use map for faster variable lookup
public class StructValue implements DataValue
{
    Map<String, DataValue> values;

    public StructValue()
    {
        this.values = new HashMap<>();
    }

    public void addVariable(String name, DataValue val)
    {
        if(this.values.containsKey(name))
        {
            throw new DuplicateVariableNameException()
        }
        this.values.put(name, val);
    }

    public void getVariableByName(String name)
    {

    }
}
