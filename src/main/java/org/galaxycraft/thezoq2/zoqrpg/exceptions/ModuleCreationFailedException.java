package org.galaxycraft.thezoq2.zoqrpg.exceptions;

/**
 * Exception thrown by modular spells when one of the modules failed to create. The reason for failing is
 * aquired from a FactoryCreationFailedException and can be read using getFactoryFailReason.
 *
 * @see FactoryCreationFailedException
 */
public class ModuleCreationFailedException extends Exception
{
    private final FactoryCreationFailedException factoryFailException;

    public  ModuleCreationFailedException(FactoryCreationFailedException factoryFailException)
    {
        super("Module creation failed, factory failed to create object with reason: " + factoryFailException.getReason());

        this.factoryFailException = factoryFailException;
    }

    public String getFactoryFailReason()
    {
        return factoryFailException.getReason();
    }
}
