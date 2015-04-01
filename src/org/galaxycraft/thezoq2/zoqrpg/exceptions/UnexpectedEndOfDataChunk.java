package org.galaxycraft.thezoq2.zoqrpg.exceptions;

/**
 * Created by frans on 01/04/15.
 */
public class UnexpectedEndOfDataChunk extends Exception
{
    //TODO: Propper error message
    public UnexpectedEndOfDataChunk(String file, int line)
    {
        super("Error at line: " + line + " in file: " + file);
    }
    public UnexpectedEndOfDataChunk(String errorMessage, Throwable throwable, String file, int line)
    {
        super("Error at line: " + line + " in file: " + file, throwable);
    }
}
