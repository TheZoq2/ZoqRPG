package org.galaxycraft.thezoq2.zoqrpg.exceptions;

import org.galaxycraft.thezoq2.zoqrpg.fileio.DataValue;

/**
 * Exception thrown when a variable of a specific type was requested from a StructValue but the type of the found
 * variable was not the expected type.
 */
public class WrongDatatypeException extends Exception
{
    private final DataValue variable;
    private final String expectedDatatype;

    public WrongDatatypeException(DataValue variable, String expectedDatatype)
    {
        super("Wrong datatype for variable " + variable.getFullPath() + " expected " + expectedDatatype);
        this.variable = variable;
        this.expectedDatatype = expectedDatatype;
    }

    public String getVarPath()
    {
        return variable.getFullPath();
    }
    public String getExpectedDatatype()
    {
        return expectedDatatype;
    }
}
