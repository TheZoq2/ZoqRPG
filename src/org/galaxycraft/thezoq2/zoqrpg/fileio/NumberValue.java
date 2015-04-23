package org.galaxycraft.thezoq2.zoqrpg.fileio;

/**
 * Created by frans on 23/04/15.
 */
public class NumberValue extends BaseDataValue
{
    //Saving with maximum prescition just to be sure
    double valueD;

    public NumberValue(String value, String varName, StructValue parentStruct)
    {
        super(varName, parentStruct);

        //Parse it as a double
        this.valueD = Double.parseDouble(value);
    }
    public NumberValue(double valueD)
    {
        super("", null);

        this.valueD = valueD;
    }

    //Returns the double version of the value. Possibly make general function?
    //TODO -""--?
    public double getValue()
    {
        return this.valueD;
    }
}
