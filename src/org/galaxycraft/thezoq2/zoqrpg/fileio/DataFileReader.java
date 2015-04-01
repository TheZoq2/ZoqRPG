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
    private enum DataStatus
    {
        LOOKING_FOR_VARIABLE,
        LOOKING_FOR_VALUE,
    }

    private List<String> lines;
    private String finalString;
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

        //TODO: THROW INSTEAD OF CATCH
        try
        {
            interpretData();
        } catch (InvalidDatafileException e)
        {
            e.printStackTrace();
        }
    }

    private void createDataString()
    {

        StringBuilder finalStringBuilder = new StringBuilder();

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

    private void interpretData() throws InvalidDatafileException
    {
        DataStatus parseStatus;
        parseStatus = DataStatus.LOOKING_FOR_VARIABLE;
        int currentIndex = 0;
        boolean parsingDone = false;
        int depth = 0;

        while (!parsingDone)
        {
            //Finding the next special char
            int nextSChar = findNextSpecialChar(currentIndex);

            //No more specialCharacters found
            //This means that the end of the file is reached
            if (nextSChar == -1)
            {
                //Check if the file is valid
                if (depth != 0)
                {
                    throw new InvalidDatafileException("Unexpected end of file, all brackets are not closed", filename, lineStarts.get(lineStarts.size() - 1));
                }
                if (parseStatus != DataStatus.LOOKING_FOR_VARIABLE)
                {
                    throw new InvalidDatafileException("Unexpected end of file", filename, lineStarts.get(lineStarts.size() - 1));
                }
                break;
            }

            char cSpecial = finalString.charAt(nextSChar);
            int currentLine = getLineFromFinal(nextSChar);

            switch (parseStatus)
            {
                case LOOKING_FOR_VARIABLE:
                {
                    if (cSpecial == '=')
                    {
                        //Getting the variable name
                        String varName = finalString.substring(currentIndex, nextSChar);

                        parseStatus = DataStatus.LOOKING_FOR_VALUE;
                    }
                    else
                    {
                        throw new InvalidDatafileException("Expected variable declaration, got: " + cSpecial, filename, currentLine);
                    }
                    break;
                }
                case LOOKING_FOR_VALUE:
                {
                    if (cSpecial == '{')
                    {
                        parseStatus = DataStatus.LOOKING_FOR_VARIABLE;
                    }
                    else if (cSpecial == ';')
                    {
                        parseStatus = DataStatus.LOOKING_FOR_VARIABLE;
                    }
                    else
                    {
                        throw new InvalidDatafileException("Expected variable value, got: " + cSpecial, filename, currentLine);
                    }
                    break;
                }
            }
            currentIndex = nextSChar + 1;
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
        int firstChar = -1;
        //Go through each char one by one
        for(int i = start; i < finalString.length(); i++)
        {
            String singleChar = finalString.substring(i,i+1);

            if(singleChar.matches("^.*[^a-zA-Z0-9 ].*$"))
            {
                firstChar = i;
                break;
            }
        }
        return firstChar;
    }

    //TODO:Handle errors
    private int getLineFromFinal(int charPos)
    {
        for(int i = 0; i < lineStarts.size(); i++)
        {
            if(charPos < lineStarts.get(i))
            {
                return i;
            }
        }

        return 0;
    }
}
