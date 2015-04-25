package org.galaxycraft.thezoq2.zoqrpg.exceptions;

/**
 * Created by frans on 4/10/15.
 */
public class MissingVariableException extends Exception
{
    public MissingVariableException(String variableName, String structName)
    {
         super("Struct: " + structName + " is missing variable: " + variableName);
    }
    public MissingVariableException(String variableName, String structName, Throwable t)
    {
        super("Struct: " + structName + " is missing variable: " + variableName, t);
    }
}
