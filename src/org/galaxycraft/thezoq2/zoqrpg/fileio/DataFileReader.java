package org.galaxycraft.thezoq2.zoqrpg.fileio;

import org.galaxycraft.thezoq2.zoqrpg.exceptions.InvalidDatafileException;
import org.galaxycraft.thezoq2.zoqrpg.exceptions.UnexpectedEndOfDataChunk;
import org.galaxycraft.thezoq2.zoqrpg.exceptions.UnexpectedSpecialChar;

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
    private enum ParseStatus
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
            parseDataChunk(0, finalString.length());
        } catch (InvalidDatafileException e)
        {
            e.printStackTrace();
        } catch (UnexpectedEndOfDataChunk e)
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

    private List<DataVariable> parseDataChunk(int start, int end) throws InvalidDatafileException, UnexpectedEndOfDataChunk
    {
        List<DataVariable> dataVariables = new ArrayList<>();

        int currentChar = 0;
        boolean doneParsing = false;

        ParseStatus parseStatus = ParseStatus.LOOKING_FOR_VARIABLE;
        String cVariableName = "";

        while(!doneParsing)
        {
            int nextSpecial = findNextSpecialChar(start + currentChar);

            //If this is the last special char in the chunk
            if(nextSpecial == -1 || nextSpecial >= end)
            {
                //If no special characters are left in the chunk, the parsing has failed. Throw an error
                if(parseStatus != ParseStatus.LOOKING_FOR_VARIABLE)
                {
                    throw new UnexpectedEndOfDataChunk(filename, getLineFromFinal(currentChar + start));
                }

                doneParsing = true;
            }
            else
            {
                //There is a special char, parse it
                char specialChar = finalString.charAt(nextSpecial);

                switch(parseStatus)
                {
                    case LOOKING_FOR_VARIABLE:
                    {
                        //An equals sign marks the end of a variable name, anything else means something is
                        //wrong
                        if(specialChar == '=')
                        {
                            //Getting the variable name
                            cVariableName = finalString.substring(currentChar + start, nextSpecial);
                            parseStatus = ParseStatus.LOOKING_FOR_VALUE;
                        }
                        else
                        {
                            throw new UnexpectedSpecialChar(specialChar, '=', filename, getLineFromFinal(nextSpecial));
                        }
                        break;
                    }
                    case LOOKING_FOR_VALUE:
                    {
                        //If a semicolon is found before {, this is a single variable declaration
                        if(specialChar == ';')
                        {
                            //getting the value
                            String varValue = finalString.substring(currentChar, nextSpecial);

                            //Creating a DataVariable and adding it to the list
                            DataValue dVal = new StringValue(varValue);
                            DataVariable dVar = new DataVariable(cVariableName, dVal);

                            dataVariables.add(dVar);

                            //Reset the parseStatus
                            parseStatus = ParseStatus.LOOKING_FOR_VARIABLE;
                        }
                        else if(specialChar == '{')
                        {
                            //Finding the corresponding }
                        }
                        break;
                    }
                }
            }

            currentChar = nextSpecial - start + 1;
        }

        return dataVariables;
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

    private int findMatchingBracket(int start, char openBracket, char closeBracket)
    {
        int depth = 1;

        for(int i = start; i < finalString.length(); i++)
        {
            int cChar = finalString.charAt(i);
            //Another bracket opened, increase depth
            if(cChar == openBracket)
            {
                depth++;
            }
            else if(cChar == closeBracket)
            {
                depth--;
            }

            //The corresponding bracket has been found
            if(depth == 0)
            {
                return i;
            }
        }

        //The matching bracket wasn't found, report error
    }
}
