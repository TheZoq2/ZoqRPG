package org.galaxycraft.thezoq2.zoqrpg.exceptions;

/**
 * Exception thrown when the end of a known datachunk is reached when something else is expected. This will most likley
 * mean that a semicolon or {} is missing after a variable declaration.
 *
 * Extends InvalidDatafileException to provide information about what line and file the error happned on.
 * @see InvalidDatafileException
 */
public class UnexpectedEndOfDataChunkException extends InvalidDatafileException
{
    public UnexpectedEndOfDataChunkException(String file, int line)
    {
        super("Unexpected end of datachunk, missing ';' or '{}'? ", file, line);
    }
}
