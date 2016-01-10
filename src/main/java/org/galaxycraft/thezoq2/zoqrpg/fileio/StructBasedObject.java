package org.galaxycraft.thezoq2.zoqrpg.fileio;

import org.bukkit.Bukkit;
import org.galaxycraft.thezoq2.zoqrpg.exceptions.NoSuchVariableException;
import org.galaxycraft.thezoq2.zoqrpg.exceptions.WrongDatatypeException;
import org.galaxycraft.thezoq2.zoqrpg.utils.GlobalConfig;

import java.util.logging.Level;

/**
 * Base class for objects that are created from structs. Currently only contains one method which reads a variable from
 * a StructValue, returns the value if it exists and if not, returns a default value passed to the function.
 *
 * @see DataValue
 * @see StructValue
 */

//Abstract class without abstract methods: See BaseDataValue
//TODO: Add StructValue as field, remove from function parameters
public abstract class StructBasedObject
{
    /**
     * Reads a DataValue from a struct. If the value doesn't exist or is the wrong datatype, the default value is returned
     *
     * Also prints an error if the variable isn't found
     *
     * @param sv the struct to look for the variable in.
     * @param name the name of the variable
     * @param defaultValue value to fall back to if the variable isn't in the struct or doesn't have the right datatype
     * @param datatype the datatype of the DataValue
     * @param <T> the type of DataValue to return
     * @return the final value
     */
    protected <T extends DataValue> T readValueWithFallback(StructValue sv, String name, T defaultValue, Class<T> datatype)
    {
        //TODO: Check why warnings arn't being printed
        try
        {
            return sv.getVariableOfTypeByName(name, datatype);
        } catch (NoSuchVariableException e)
        {
            Bukkit.getLogger().log(Level.WARNING, "Failed read variable: " + name + " in: " + sv.getFullPath() +
                    " falling back to default value");

            GlobalConfig.getInstance().printStackTraceForDefault(e);
        } catch (WrongDatatypeException e)
        {
            Bukkit.getLogger().log(Level.WARNING, "Variable: " + e.getVarPath() + " is the wrong datatype, expecting: " +
                    e.getExpectedDatatype() + ". Falling back to default value");

            GlobalConfig.getInstance().printStackTraceForDefault(e);
        }

        return defaultValue;
    }
}
