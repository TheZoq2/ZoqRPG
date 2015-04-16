package org.galaxycraft.thezoq2.zoqrpg.fileio;

/**
 * Created by frans on 3/31/15.
 */
public class StringValue extends BaseDataValue
{
    private String value;

    public StringValue(String value, String varName, StructValue parentStruct)
    {
        super(varName, parentStruct);
        this.value = value;
    }

    public String getValue()
    {
        return this.value;
    }
}
