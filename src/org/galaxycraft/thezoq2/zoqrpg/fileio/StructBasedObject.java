package org.galaxycraft.thezoq2.zoqrpg.fileio;

import org.bukkit.Bukkit;
import org.galaxycraft.thezoq2.zoqrpg.exceptions.NoSuchVariableException;
import org.galaxycraft.thezoq2.zoqrpg.exceptions.WrongDatatypeException;
import org.galaxycraft.thezoq2.zoqrpg.utils.GlobalConfig;

import java.util.logging.Level;

/**
 * Created by frans on 23/04/15.
 */
public abstract class StructBasedObject
{
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
