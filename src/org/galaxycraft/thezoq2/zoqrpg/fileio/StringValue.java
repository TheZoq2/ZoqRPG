package org.galaxycraft.thezoq2.zoqrpg.fileio;

/**
 * Created by frans on 3/31/15.
 */
public class StringValue extends BaseDataValue
{
    private final static String TYPE_NAME = "string";

    private String value = null;

    //TODO: Possibly make package protected
    public StringValue(String value)
    {
        super(value, null);
    }
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
