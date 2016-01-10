package org.galaxycraft.thezoq2.zoqrpg.fileio;

/**
 * A DataValue that contains a double.
 *
 * Extends BaseDataValue to inherit basic implementation of methods commonly used by DataValues
 *
 * @see BaseDataValue
 * @see DataValue
 */
public class NumberValue extends BaseDataValue
{
    //Saving with maximum prescition just to be sure
    private double value;

    public NumberValue(String value, String varName, StructValue parentStruct)
    {
        super(varName, parentStruct);

        //Parse it as a double
        this.value = Double.parseDouble(value);
    }
    public NumberValue(double value)
    {
        super("", null);

        this.value = value;
    }

    public double getValue()
    {
        return this.value;
    }
}
