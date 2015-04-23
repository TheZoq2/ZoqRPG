package org.galaxycraft.thezoq2.zoqrpg.exceptions;

/**
 * Created by frans on 23/04/15.
 */
public class ModuleCreationFailedException extends Exception
{
    private FactoryCreationFailedException factoryFailException;

    public  ModuleCreationFailedException(FactoryCreationFailedException factoryFailException)
    {
        super("Module creation failed, factory failed to create object with reason: " + factoryFailException.getReason());

        this.factoryFailException = factoryFailException;
    }
    public  ModuleCreationFailedException(FactoryCreationFailedException factoryFailException, Throwable t)
    {
        super("Module creation failed, factory failed to create object with reason: " + factoryFailException.getReason(), t);

        this.factoryFailException = factoryFailException;
    }

    public String getFactoryFailReason()
    {
        return factoryFailException.getReason();
    }
}
