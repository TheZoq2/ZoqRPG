package org.galaxycraft.thezoq2.zoqrpg.exceptions;

import org.galaxycraft.thezoq2.zoqrpg.fileio.StructValue;

/**
 * Created by frans on 4/9/15.
 */
public class NoSuchVariableException extends Exception
{
    StructValue parentStruct;
    public NoSuchVariableException(String varName, StructValue parentStruct)
    {
        super("Variable " + varName + " does not exist in struct: " + parentStruct.getFullPath());

        this.parentStruct = parentStruct;
    }
    public NoSuchVariableException(String varName, StructValue parentStruct, Throwable t)
    {
        super("Variable " + varName + " does not exist in struct: " + parentStruct.getFullPath(), t);
    }

    public StructValue getParentStruct()
    {
        return this.parentStruct;
    }
}
