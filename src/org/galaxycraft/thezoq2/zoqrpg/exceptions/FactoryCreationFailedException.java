package org.galaxycraft.thezoq2.zoqrpg.exceptions;

/**
 * Created by frans on 4/20/15.
 */
public class FactoryCreationFailedException extends Exception
{
    private String reason;

    public FactoryCreationFailedException(String reason)
    {
        super("Factory failed to create object. " + reason);

        this.reason = reason;
    }
    public FactoryCreationFailedException(String reason, Throwable t)
    {
        super("Factory failed to create object. " + reason, t);

        this.reason = reason;
    }

}
