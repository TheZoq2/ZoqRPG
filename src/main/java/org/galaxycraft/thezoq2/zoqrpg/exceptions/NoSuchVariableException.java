package org.galaxycraft.thezoq2.zoqrpg.exceptions;

import org.galaxycraft.thezoq2.zoqrpg.fileio.StructValue;

/**
 * Exception thrown by StructValue when a requested variable is not found within  the struct. The full "path" to
 * the variable aswell as the name of the requested variable can be read using getStructPath and getVarName.
 *
 * @see StructValue
 */
public class NoSuchVariableException extends Exception
{
    private final StructValue parentStruct;
    private final String varName;
    public NoSuchVariableException(String varName, StructValue parentStruct)
    {
        super("Variable " + varName + " does not exist in struct: " + parentStruct.getFullPath());

        this.parentStruct = parentStruct;
        this.varName = varName;
    }
    public String getStructPath()
    {
        return this.parentStruct.getFullPath();
    }
    public String getVarName()
    {
        return this.varName;
    }
}
