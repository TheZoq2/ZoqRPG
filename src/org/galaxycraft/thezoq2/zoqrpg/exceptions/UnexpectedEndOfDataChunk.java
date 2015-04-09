package org.galaxycraft.thezoq2.zoqrpg.exceptions;

/**
 * Created by frans on 01/04/15.
 */
public class UnexpectedEndOfDataChunk extends InvalidDatafileException
{
    public UnexpectedEndOfDataChunk(String file, int line)
    {
        super("Unexpected end of datachunk, missing ;? ", file, line);
    }
    public UnexpectedEndOfDataChunk(String errorMessage, Throwable throwable, String file, int line)
    {
        super("Unexpected end of datachunk, missing ;? ", throwable, file, line);
    }
}
