package org.galaxycraft.thezoq2.zoqrpg.exceptions;

import org.galaxycraft.thezoq2.zoqrpg.fileio.StructValue;

/**
 * Created by frans on 4/9/15.
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
    public NoSuchVariableException(String varName, StructValue parentStruct, Throwable t)
    {
        super("Variable " + varName + " does not exist in struct: " + parentStruct.getFullPath(), t);
        this.parentStruct = parentStruct;
        this.varName = varName;
    }

    public StructValue getParentStruct()
    {
        return this.parentStruct;
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
