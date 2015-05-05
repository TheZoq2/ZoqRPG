package org.galaxycraft.thezoq2.zoqrpg.exceptions;

/**
 * Exception thrown by the DataFileReader when a non alphanumeric character is found but not expected. An example of this
 * could be a semicolon before = ine a variable declaration or simply a non-alpha numeric character that the parser
 * doesn't understand.
 *
 * Extends InvalidDatafileException to provide information about what line and file the error happned on.
 * @see InvalidDatafileException
 *
 * @see org.galaxycraft.thezoq2.zoqrpg.fileio.DataFileReader
 */
public class UnexpectedSpecialCharException extends InvalidDatafileException
{
    public UnexpectedSpecialCharException(char invalidChar, char expectedChar, String file, int line)
    {
        super("Unexpected " + invalidChar + ", expected: " + expectedChar, file, line);
    }
}
