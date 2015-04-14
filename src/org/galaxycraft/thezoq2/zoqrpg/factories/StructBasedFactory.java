package org.galaxycraft.thezoq2.zoqrpg.factories;

import org.bukkit.Bukkit;
import org.galaxycraft.thezoq2.zoqrpg.exceptions.MissingVariableException;
import org.galaxycraft.thezoq2.zoqrpg.exceptions.NoSuchVariableException;
import org.galaxycraft.thezoq2.zoqrpg.exceptions.WrongDatatypeException;
import org.galaxycraft.thezoq2.zoqrpg.fileio.DataValue;
import org.galaxycraft.thezoq2.zoqrpg.fileio.StringValue;
import org.galaxycraft.thezoq2.zoqrpg.fileio.StructValue;

import java.util.logging.Level;

/**
 * Created by frans on 4/10/15.
 */
public abstract class StructBasedFactory
{
    private static final String BASE_VAR_NAME = "base";

    protected StructValue baseStruct;

    public StructBasedFactory(StructValue baseStruct)
    {
        this.baseStruct = baseStruct;
    }

    protected DataValue getVariableFromStruct(String name) throws NoSuchVariableException
    {
        return baseStruct.getVariableByName(name);
    }
    protected String getBaseName(StructValue objectStruct) throws NoSuchVariableException, WrongDatatypeException
    {
        DataValue baseVar = objectStruct.getVariableByName(BASE_VAR_NAME);

        if(baseVar instanceof StringValue)
        {
            return ((StringValue) baseVar).getValue();
        }
        else
        {
            throw new WrongDatatypeException(BASE_VAR_NAME, "string");
        }
    }
}
