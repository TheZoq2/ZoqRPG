package org.galaxycraft.thezoq2.zoqrpg.exceptions;

/**
 * Created by frans on 28/03/15.
 */
public class InvalidDatafileException extends Exception
{
    //This is static because this needs to be performed before the message is passed to
    //the superclass constructor
    private static String createMessage(String errorMessage, String file, int line)
    {
        StringBuilder msgBuilder = new StringBuilder();
        msgBuilder.append("Error when reading datafile: ");
        msgBuilder.append(file);
        msgBuilder.append(". ");
        msgBuilder.append(errorMessage);
        msgBuilder.append(" at line: ");
        msgBuilder.append(line);
        return msgBuilder.toString();
    }

    public InvalidDatafileException(String errorMessage, String file, int line)
    {
        super(createMessage(errorMessage, file, line));
    }
    public InvalidDatafileException(String errorMessage, Throwable throwable, String file, int line)
    {
        super(createMessage(errorMessage, file, line), throwable);
    }

}
