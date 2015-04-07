package org.galaxycraft.thezoq2.zoqrpg.exceptions;

/**
 * Created by frans on 4/1/15.
 */
public class MissmatchedBracketException extends InvalidDatafileException
{
    //TODO: Propper error message
    public MissmatchedBracketException(String file, int line)
    {
        super("Missmatched bracket", file, line);
    }
    public MissmatchedBracketException(String errorMessage, Throwable throwable, String file, int line)
    {
        super("Missmatched bracket", throwable, file, line);
    }
}
