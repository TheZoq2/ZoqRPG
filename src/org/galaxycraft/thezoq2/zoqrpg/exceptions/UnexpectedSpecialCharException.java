package org.galaxycraft.thezoq2.zoqrpg.exceptions;

/**
 * Created by frans on 01/04/15.
 */
public class UnexpectedSpecialCharException extends InvalidDatafileException
{
    public UnexpectedSpecialCharException(char invalidChar, char expectedChar, String file, int line)
    {
        super("Unexpected " + invalidChar + ", expected: " + expectedChar, file, line);
    }
}
