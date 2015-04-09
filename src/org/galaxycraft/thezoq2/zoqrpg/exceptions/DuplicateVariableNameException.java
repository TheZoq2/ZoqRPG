package org.galaxycraft.thezoq2.zoqrpg.exceptions;

/**
 * Created by frans on 09/04/15.
 */
public class DuplicateVariableNameException extends InvalidDatafileException
{
    public DuplicateVariableNameException(String varName, String file, int line)
    {
        super("Variable " + varName + " has already been declared in this scope", file, line);
    }
    public DuplicateVariableNameException(String varName, String file, int line, Throwable t)
    {
        super("Variable " + varName + " has already been declared in this scope", t, file, line);
    }
}
