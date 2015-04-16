package org.galaxycraft.thezoq2.zoqrpg.fileio;

import org.galaxycraft.thezoq2.zoqrpg.exceptions.*;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by frans on 3/24/15.
 */

//TODO: Check for duplicate names

//TODO: Fix space handling
    /*The current system interprets
        a=b
        c=d;

        as a=bc=d; which might result in weird behvaiour
     */
//TODO: Fix end of file detection,
    /*
        a=b;
        b=c;
        hello world

        will be parsed fine even though it should end due to hello world not being a propper statement
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

    private StructValue fileStruct;

    public DataFileReader(FileReader fileReader) throws IOException, InvalidDatafileException
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

        fileStruct = parseDataChunk(0, finalString.length());
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

    private StructValue parseDataChunk(int start, int end) throws InvalidDatafileException
    {
        //List<DataVariable> dataVariables = new ArrayList<>();
        StructValue resultStruct = new StructValue("root", null);

        int currentChar = start;
        boolean doneParsing = false;

        ParseStatus parseStatus = ParseStatus.LOOKING_FOR_VARIABLE;
        String cVariableName = "";

        while(!doneParsing)
        {
            int nextSpecial = findNextSpecialChar(currentChar);

            //If this is the last special char in the chunk
            if(nextSpecial == -1 || nextSpecial > end)
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
                            cVariableName = finalString.substring(currentChar, nextSpecial);
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
                        DataValue newValue = null;
                        //If a semicolon is found before {, this is a single variable declaration
                        if(specialChar == ';')
                        {
                            //getting the value
                            String varValue = finalString.substring(currentChar, nextSpecial);

                            //Creating a DataVariable and adding it to the list
                            newValue = new StringValue(varValue, cVariableName, resultStruct);
                        }
                        else if(specialChar == '{')
                        {
                            //Finding the corresponding }
                            int matching = findMatchingBracket(currentChar + 1, '{', '}');

                            //Call this function again to parse a deeper part of the datacunk.
                            newValue = parseDataChunk(currentChar + 1, matching - 1);

                            //Update the parent and name info of the new struct
                            //Cast can be performed becase parseDataChunk will always return a new struct
                            newValue.setParentStruct(resultStruct);
                            newValue.setVariableName(cVariableName);

                            nextSpecial = matching;
                        }

                        //Adding the new variable to the result
                        try
                        {
                            resultStruct.addVariable(cVariableName, newValue);

                            parseStatus = ParseStatus.LOOKING_FOR_VARIABLE;
                        } catch (StructContainsVariableException e)
                        {
                            //Throw a more detailed error
                            throw new DuplicateVariableNameException(cVariableName, filename, currentChar);
                        }
                        break;
                    }
                }
            }

            currentChar = nextSpecial + 1;
        }

        return resultStruct;
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

    private int findMatchingBracket(int start, char openBracket, char closeBracket) throws MissmatchedBracketException
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
        throw new MissmatchedBracketException(filename, getLineFromFinal(start - 1), openBracket);
    }
}
