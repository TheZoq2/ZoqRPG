package org.galaxycraft.thezoq2.zoqrpg.fileio;

/**
 * Created by frans on 3/31/15.
 */
public class StringValue implements DataValue
{
    private String value;

    public StringValue(String value)
    {
        this.value = value;
    }

    @Override
    public String getValue()
    {
        return this.value;
    }
}
