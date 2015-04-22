package org.galaxycraft.thezoq2.zoqrpg.fileio;

import org.bukkit.Bukkit;
import org.galaxycraft.thezoq2.zoqrpg.exceptions.WrongDatatypeException;

import java.util.logging.Level;

/**
 * Created by frans on 3/31/15.
 */
public class StringValue extends BaseDataValue
{
    private static String TYPE_NAME = "string";

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

    /*
    public float getValueAsFloat() throws WrongDatatypeException
    {
        try
        {
            return Float.parseFloat(this.value);
        }
        catch (NumberFormatException e)
        {
            throw new WrongDatatypeException(this, "float");
        }
    }
    public int getValueAsInt() throws WrongDatatypeException
    {
        try
        {
            return Integer.parseInt(this.value);
        }
        catch (NumberFormatException e)
        {
            throw new WrongDatatypeException(this, "integer");
        }
    }
    */
    public <T extends Object> T getValueAsDatatype(Class<T> type) throws WrongDatatypeException
    {
        if(type.isInstance(Integer.class))
        {
            try
            {
                return Integer.parseInt(this.value);
            }
            catch(NumberFormatException e)
            {
                throw new WrongDatatypeException(this, "integer");
            }
        }
        else if(type.isInstance(Integer.class))
        {
            try
            {
                return Float.parseFloat(this.value);
            }
            catch(NumberFormatException e)
            {
                throw new WrongDatatypeException(this, "float");
            }
        }
        else
        {
            Bukkit.getLogger().log(Level.SEVERE, "Internal error, no known konversion to specified datatype");
            WrongDatatypeException e = new WrongDatatypeException(this, "unknown");
            e.printStackTrace();
            throw e;
        }
    }
}
