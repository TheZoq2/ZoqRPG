package org.galaxycraft.thezoq2.zoqrpg.exceptions;

/**
 * Exception thrown by the StructBasedFactory fails to create an object because it does not have such a template
 * object stored in the map of objects. This means that the struct doesn't have a variable with the name
 * that is being requested or that the variable is not a valid variable for creating objects based on it.
 *
 * @see org.galaxycraft.thezoq2.zoqrpg.factories.StructBasedFactory
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
