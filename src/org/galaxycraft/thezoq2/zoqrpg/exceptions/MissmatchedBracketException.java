package org.galaxycraft.thezoq2.zoqrpg.exceptions;

/**
 * Exception thrown when an bracket open bracet does not have a matching closing bracket in a datafile
 *
 * Extends InvalidDatafileException to provide information about what line and file the error happned on.
 * @see InvalidDatafileException
 */

public class MissmatchedBracketException extends InvalidDatafileException
{
    //The line passed is the line of the bracket that is missmatched
    public MissmatchedBracketException(String file, int line, char matchingBracket)
    {
        super("Missing bracket to close " + matchingBracket, file, line);
    }
}
