package org.galaxycraft.thezoq2.zoqrpg.fileio;

/**
 * A DataValue containing a single string. Extends BaseDataValue to inherit some methods needed by DataValue
 *
 * @see DataValue
 * @see BaseDataValue
 */
public class StringValue extends BaseDataValue
{
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
