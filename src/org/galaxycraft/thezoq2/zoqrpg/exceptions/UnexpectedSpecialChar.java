package org.galaxycraft.thezoq2.zoqrpg.exceptions;

/**
 * Created by frans on 01/04/15.
 */
public class UnexpectedSpecialChar extends InvalidDatafileException
{
    public UnexpectedSpecialChar(char invalidChar, char expectedChar, String file, int line)
    {
        super("Unexpected " + invalidChar + ", expected: " + expectedChar, file, line);
    }
    public UnexpectedSpecialChar(char invalidChar, char expectedChar, String file, int line, Throwable throwable)
    {
        super("Unexpected " + invalidChar + ", expected: " + expectedChar, throwable, file, line);
    }
}
