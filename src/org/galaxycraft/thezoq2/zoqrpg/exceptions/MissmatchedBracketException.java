package org.galaxycraft.thezoq2.zoqrpg.exceptions;

/**
 * Created by frans on 4/1/15.
 */
public class MissmatchedBracketException extends InvalidDatafileException
{
    //The line passed is the line of the bracket that is missmatched
    public MissmatchedBracketException(String file, int line, char matchingBracket)
    {
        super("Missing bracket to close " + matchingBracket, file, line);
    }
}
