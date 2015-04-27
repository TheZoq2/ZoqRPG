package org.galaxycraft.thezoq2.zoqrpg.exceptions;

/**
 * Created by frans on 01/04/15.
 */
public class UnexpectedEndOfDataChunkException extends InvalidDatafileException
{
    public UnexpectedEndOfDataChunkException(String file, int line)
    {
        super("Unexpected end of datachunk, missing ;? ", file, line);
    }
}
