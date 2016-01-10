package org.galaxycraft.thezoq2.zoqrpg.exceptions;

/**
 * Exception thrown when the factory fails to create an object. The reason for the creatuib failing is saved and
 * can be read by calling getReason.
 */
public class FactoryCreationFailedException extends Exception
{
    private final String reason;

    public FactoryCreationFailedException(String reason)
    {
        super("Factory failed to create object. " + reason);

        this.reason = reason;
    }

    public String getReason()
    {
        return this.reason;
    }

}
