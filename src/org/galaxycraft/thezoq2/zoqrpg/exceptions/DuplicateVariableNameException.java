package org.galaxycraft.thezoq2.zoqrpg.exceptions;

/**
 * Exception thrown when a variable that is being created already exists in the
 * struct it is being added to.
 *
 * Extends InvalidDatafileException to provide information about what line and file the error happned on.
 * @see InvalidDatafileException
 */
public class DuplicateVariableNameException extends InvalidDatafileException
{
    public DuplicateVariableNameException(String varName, String file, int line)
    {
        super("Variable " + varName + " has already been declared in this scope", file, line);
    }
}
