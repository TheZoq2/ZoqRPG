package org.galaxycraft.thezoq2.zoqrpg.exceptions;

/**
 * Created by frans on 4/10/15.
 */
public class WrongDatatypeException extends Exception
{
    public WrongDatatypeException(String variableName, String expectedDatatype)
    {
        super("Wrong datatype for variable " + variableName + " expected " + expectedDatatype);
    }

    public WrongDatatypeException(String variableName, String expectedDatatype, Throwable t)
    {
        super("Wrong datatype for variable " + variableName + " expected " + expectedDatatype, t);
    }
}
