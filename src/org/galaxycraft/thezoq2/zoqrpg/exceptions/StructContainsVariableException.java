package org.galaxycraft.thezoq2.zoqrpg.exceptions;

/**
 * Exception thrown by a StructValue when a variable with the same name that is being created already exists
 * in the struct
 *
 * @see org.galaxycraft.thezoq2.zoqrpg.fileio.StructValue
 */
public class StructContainsVariableException extends Exception
{
    private final String varName;

    public StructContainsVariableException(String varName)
    {
        super("Struct contains key already");
        this.varName = varName;
    }

    public String getVarName()
    {
        return varName;
    }
}
