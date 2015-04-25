package org.galaxycraft.thezoq2.zoqrpg.factories;

import org.galaxycraft.thezoq2.zoqrpg.exceptions.FactoryCreationFailedException;
import org.galaxycraft.thezoq2.zoqrpg.exceptions.NoSuchVariableException;
import org.galaxycraft.thezoq2.zoqrpg.exceptions.WrongDatatypeException;
import org.galaxycraft.thezoq2.zoqrpg.fileio.StringValue;
import org.galaxycraft.thezoq2.zoqrpg.fileio.StructValue;

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

    protected StructValue getStructByName(String name) throws FactoryCreationFailedException
    {
        //TODO: Possibly foce lowercase. More user friendlynesssss
        //Get the struct with the specified name
        try
        {
            return baseStruct.getVariableOfTypeByName(name, StructValue.class);
        }
        catch (WrongDatatypeException e)
        {
            throw new FactoryCreationFailedException("Variable " + e.getVarPath() + " needs to be a " + e.getExpectedDatatype());
        }
        catch (NoSuchVariableException e)
        {
            throw new FactoryCreationFailedException("Expected variable " + e.getVarName() + " in " +
                    e.getStructPath() + " to create " + name);
        }
    }

    protected String getBaseValueFromStruct(StructValue sv) throws FactoryCreationFailedException
    {
        try
        {
            return sv.getVariableOfTypeByName("base", StringValue.class).getValue();
        }
        catch (WrongDatatypeException e)
        {
            throw new FactoryCreationFailedException("Base value " + e.getVarPath() + " must be a string");
        }
        catch (NoSuchVariableException e)
        {
            throw new FactoryCreationFailedException("Struct: " + e.getVarName() + " does contain a base value");
        }
    }


}
