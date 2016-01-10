package org.galaxycraft.thezoq2.zoqrpg.exceptions;

/**
 * Base exception class that gets thrown when a datafile is missread. The exception message contains some
 * info about what happned aswell as what file and line it happned on.
 */
public class InvalidDatafileException extends Exception
{
    //This is static because this needs to be performed before the message is passed to
    //the superclass constructor
    private static String createMessage(String errorMessage, String file, int line)
    {
        return "Error when reading datafile: " + file + ". " + errorMessage + " at line: " + line;
    }

    //These methods are used by subclasses
    public InvalidDatafileException(String errorMessage, String file, int line)
    {
        super(createMessage(errorMessage, file, line));
    }
    public InvalidDatafileException(String errorMessage, Throwable throwable, String file, int line)
    {
        super(createMessage(errorMessage, file, line), throwable);
    }

}
