package org.galaxycraft.thezoq2.zoqrpg.factories;

import com.darkblade12.particleeffect.ReflectionUtils;
import org.bukkit.Bukkit;
import org.galaxycraft.thezoq2.zoqrpg.exceptions.FactoryCreationFailedException;
import org.galaxycraft.thezoq2.zoqrpg.exceptions.NoSuchVariableException;
import org.galaxycraft.thezoq2.zoqrpg.exceptions.WrongDatatypeException;
import org.galaxycraft.thezoq2.zoqrpg.fileio.DataValue;
import org.galaxycraft.thezoq2.zoqrpg.fileio.StringValue;
import org.galaxycraft.thezoq2.zoqrpg.fileio.StructValue;

import java.util.logging.Level;

/**
 * Created by frans on 4/10/15.
 */


public abstract class StructBasedFactory<T>
{
    private static final String BASE_VAR_NAME = "base";

    protected StructValue baseStruct;

    protected StructBasedFactory(StructValue baseStruct)
    {
        this.baseStruct = baseStruct;
    }

    protected StructValue getStructByName(String name) throws WrongDatatypeException, NoSuchVariableException
    {
        //TODO: Possibly foce lowercase. More user friendlynesssss
        //Get the struct with the specified name
        return baseStruct.getVariableOfTypeByName(name, StructValue.class);
    }

    protected String getBaseValueFromStruct(StructValue sv) throws WrongDatatypeException, NoSuchVariableException
    {
        return sv.getVariableOfTypeByName("base", StringValue.class).getValue();
    }

    public T createObject(String name) throws FactoryCreationFailedException
    {
        //Finding the base name
        try
        {
            StructValue sv = getStructByName(name);

            String baseName = getBaseValueFromStruct(sv);

            return finalizeObject(sv, baseName);
        } catch (WrongDatatypeException e)
        {
            Bukkit.getLogger().log(Level.WARNING, "Failed to create object " + name + ", variable " + e.getVarPath()
                    + " was the wrong datatype. Expected: " + e.getExpectedDatatype());
            e.printStackTrace();

            return null;
        } catch (NoSuchVariableException e)
        {
            Bukkit.getLogger().log(Level.WARNING, "Failed to create object " + name + ", variable: " + e.getVarName()
                    + " did not exist in: " + e.getStructPath());
            e.printStackTrace();

            return null;
        }
    }

    public abstract T finalizeObject(StructValue sv, String baseName) throws FactoryCreationFailedException;
}
