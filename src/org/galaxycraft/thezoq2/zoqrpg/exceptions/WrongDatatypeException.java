package org.galaxycraft.thezoq2.zoqrpg.exceptions;

import org.galaxycraft.thezoq2.zoqrpg.fileio.DataValue;

/**
 * Created by frans on 4/10/15.
 */
//TODO: Report struct path instead of just varname
public class WrongDatatypeException extends Exception
{
    public WrongDatatypeException(DataValue variable, String expectedDatatype)
    {
        super("Wrong datatype for variable " + variable.getFullPath() + " expected " + expectedDatatype);
    }

    public WrongDatatypeException(DataValue variable, String expectedDatatype, Throwable t)
    {
        super("Wrong datatype for variable " + variable.getFullPath() + " expected " + expectedDatatype, t);
    }
}
