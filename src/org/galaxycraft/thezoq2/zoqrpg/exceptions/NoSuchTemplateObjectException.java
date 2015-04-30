package org.galaxycraft.thezoq2.zoqrpg.exceptions;

/**
 * Created by frans on 29/04/15.
 */
public class NoSuchTemplateObjectException extends Exception
{
    private final String name;

    public NoSuchTemplateObjectException(String name)
    {
        super("Failed to create object, factory does not have a template named " + name);
        this.name = name;
    }

    public String getName()
    {
        return name;
    }
}
