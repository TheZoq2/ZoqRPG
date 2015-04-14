package org.galaxycraft.thezoq2.zoqrpg.exceptions;

/**
 * Created by frans on 4/9/15.
 */
public class StructContainsVariableException extends Exception
{
    private final String varName;

    public StructContainsVariableException(String varName)
    {
        super("Struct contains key already");
        this.varName = varName;
    }
    public StructContainsVariableException(String varName, Throwable throwable)
    {
        super("Struct contains key already", throwable);
        this.varName = varName;
    }

    String getVarName()
    {
        return varName;
    }
}
