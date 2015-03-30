package org.galaxycraft.thezoq2.zoqrpg.fileio;

import org.galaxycraft.thezoq2.zoqrpg.exceptions.InvalidDatafileException;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by frans on 3/24/15.
 */
public class DataFileReader
{
    private static final char[] SPECIAL_CHARS =
            {
                    '=',
                    ';',
                    '{',
                    '}',
            };

    private enum DataStatus
    {
        LOOKING_FOR_VARIABLE,
        LOOKING_FOR_VALUE,
    }

    private List<String> lines;
    private String finalString;
    private StringBuilder finalStringBuilder;
    private String filename;

    //List of what char in the final string corresponds to a line ending
    private List<Integer> lineStarts;

    public DataFileReader(FileReader fileReader) throws IOException
    {
        lineStarts = new ArrayList<>();

        lines = new ArrayList<>();

        //Creating a buffered reader for reading the file
        BufferedReader br = new BufferedReader(fileReader);

        String line;
        do
        {
            line = br.readLine();

            if(line != null)
            {
                lines.add(line);
            }
        }while(line != null);

        createDataString();
    }

    private void createDataString()
    {

        StringBuilder dataBuilder;

        for(String line : lines)
        {
            //Stripping whitespace for the line
            line = stripWhitespace(line);

            System.out.println(line);

            line = stripComments(line);

            finalStringBuilder.append(line);

            //Append the current length to the line numbers
            lineStarts.add(finalStringBuilder.length());
        }

        finalString = finalStringBuilder.toString();
    }

    private void interpretData()
    {
        DataStatus parseStatus;
        parseStatus = DataStatus.LOOKING_FOR_VARIABLE;
        int currentIndex = 0;
        boolean parsingDone = false;
        int depth = 0;

        while(!parsingDone)
        {
            //Finding the next special char
            int nextSChar = findNextSpecialChar(currentIndex);
            char cSpecial = finalString.charAt(nextSChar);

            if(parseStatus == DataStatus.LOOKING_FOR_VARIABLE)
            {
                //If there is an equals sign
                if(cSpecial == '=')
                {

                }
                else
                {
                    throw new InvalidDatafileException("Expected variable declaration, got " + cSpecial, )
                }
            }

            //When there are no special chars left
            if(nextSChar == -1)
            {
                //TODO: Make sure this is the end of the file
            }
        }
    }

    private String stripWhitespace(String line)
    {
        line = line.replaceAll("\n", "");
        line = line.replaceAll(" ", "");
        line = line.replaceAll("\t", "");

        return line;
    }
    private String stripComments(String line)
    {
        int commentStart = line.indexOf("//");

        if(commentStart != -1)
        {
            System.out.println("Line has comment");
            line = line.substring(0, commentStart);
            System.out.println(line);
        }
        return line;
    }

    //Find the index of the next special char in the finalString
    private int findNextSpecialChar(int start)
    {
        int firstChar =finalString.length() + 1;

        for(char specialChar : SPECIAL_CHARS)
        {
            int index = finalString.indexOf(start);

            //Checking if this is the first special char we found yet
            if(index < firstChar)
            {
                firstChar = index;
            }
        }
        if(firstChar == finalString.length())
        {
            firstChar = -1;
        }

        return firstChar;
    }
}
