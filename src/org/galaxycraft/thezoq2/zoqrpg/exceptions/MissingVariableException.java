package org.galaxycraft.thezoq2.zoqrpg.exceptions;

/**
 * Created by frans on 4/10/15.
 */
public class MissingVariableException extends Exception
{
    public MissingVariableException(String variableName, String StructName)
    {
         super("Struct: " + StructName + " is missing variable: " + variableName);
    }
    public MissingVariableException(String variableName, String StructName, Throwable t)
    {
        super("Struct: " + StructName + " is missing variable: " + variableName, t);
    }
}
