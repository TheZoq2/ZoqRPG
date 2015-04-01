package org.galaxycraft.thezoq2.zoqrpg.fileio;

import com.avaje.ebean.validation.NotNull;

import java.util.List;

/**
 * Created by frans on 3/31/15.
 */
public class StructValue implements DataValue
{
    List<DataValue> value;

    public StructValue(List<DataValue> value)
    {
        this.value = value;
    }

    @Override
    public List<DataValue> getValue()
    {
        return this.value;
    }
}
