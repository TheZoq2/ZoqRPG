package org.galaxycraft.thezoq2.zoqrpg.exceptions;

/**
 * Created by frans on 4/1/15.
 */
public class MissmatchedBracketException extends InvalidDatafileException
{
    //TODO: Propper error message
    public MissmatchedBracketException(String file, int line)
    {
        super("Error at line: " + line + " in file: " + file);
    }
    public MissmatchedBracketException(String errorMessage, Throwable throwable, String file, int line)
    {
        super("Error at line: " + line + " in file: " + file, throwable);
    }
}
