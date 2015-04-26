package org.galaxycraft.thezoq2.zoqrpg.fileio;

/**
 * Created by frans on 23/04/15.
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

    //Returns the double version of the value. Possibly make general function?
    //TODO -""--?
    public double getValue()
    {
        return this.value;
    }
}
