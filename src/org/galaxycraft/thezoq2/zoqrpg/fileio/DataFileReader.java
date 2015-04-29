package org.galaxycraft.thezoq2.zoqrpg.fileio;

import org.galaxycraft.thezoq2.zoqrpg.exceptions.*;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;

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
@SuppressWarnings("UnnecessaryCodeBlock")
//This warning comes from switch case statements.
//I prefer to keep code blocks in the code because they make it easier to tell where a new case begins and ends.
public class DataFileReader
{
    private enum ParseStatus
    {
        LOOKING_FOR_VARIABLE,
        LOOKING_FOR_VALUE,
    }

    private List<String> lines;
    private String finalString;
    private String filename = null;

    //List of what char in the final string corresponds to a line ending
    private List<Integer> lineStarts;

    private StructValue fileStruct;

    public DataFileReader(FileReader fileReader, String filename) throws IOException, InvalidDatafileException
    {
        lineStarts = new ArrayList<>();

        lines = new ArrayList<>();

        this.filename = filename;

        //Creating a buffered reader for reading the file
        //Im fairly cirtain that this will not throw an exception since the fileReader has already loaded the file
        try(BufferedReader br = new BufferedReader(fileReader))
        {
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
    }

    public StructValue getFileStruct()
    {
        return fileStruct;
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

    //Because the warnings are similar in nature and are handled in the same way by the exception hadling where
    //the method is used, I want to keep the broad exception
    private StructValue parseDataChunk(int start, int end) throws UnexpectedSpecialCharException, UnexpectedEndOfDataChunkException, MissmatchedBracketException, DuplicateVariableNameException
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
                    throw new UnexpectedEndOfDataChunkException(filename, getLineFromFinal(currentChar + start));
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
                            throw new UnexpectedSpecialCharException(specialChar, '=', filename, getLineFromFinal(nextSpecial));
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

                            //Checking if the string can be interpreted as a number
                            if(isNumber(varValue))
                            {
                                newValue = new NumberValue(varValue, cVariableName, resultStruct);
                            }
                            else
                            {
                                //Creating a DataVariable and adding it to the list
                                newValue = new StringValue(varValue, cVariableName, resultStruct);
                            }
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
                            //TODO: Throw a more detailed error
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

            if(singleChar.matches("^.*[^a-zA-Z0-9 ._].*$"))
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

    //Code stolen from stackoverflow here: http://stackoverflow.com/questions/8564896/fastest-way-to-check-if-a-string-can-be-parsed-to-double-in-java

    @SuppressWarnings("ALL") //Supressing warnings because I didn't write code
    private boolean isNumber(CharSequence str)
    {
         final String Digits     = "(\\p{Digit}+)";
        final String HexDigits  = "(\\p{XDigit}+)";

        // an exponent is 'e' or 'E' followed by an optionally
        // signed decimal integer.
        final String Exp        = "[eE][+-]?"+Digits;
        final String fpRegex    =
                ("[\\x00-\\x20]*"+  // Optional leading "whitespace"
                        "[+-]?(" + // Optional sign character
                        "NaN|" +           // "NaN" string
                        "Infinity|" +      // "Infinity" string


                        // Digits ._opt Digits_opt ExponentPart_opt FloatTypeSuffix_opt
                        "((("+Digits+"(\\.)?("+Digits+"?)("+Exp+")?)|"+

                        // . Digits ExponentPart_opt FloatTypeSuffix_opt
                        "(\\.("+Digits+")("+Exp+")?)|"+

                        // Hexadecimal strings
                        "((" +
                        // 0[xX] HexDigits ._opt BinaryExponent FloatTypeSuffix_opt
                        "(0[xX]" + HexDigits + "(\\.)?)|" +

                        // 0[xX] HexDigits_opt . HexDigits BinaryExponent FloatTypeSuffix_opt
                        "(0[xX]" + HexDigits + "?(\\.)" + HexDigits + ")" +

                        ")[pP][+-]?" + Digits + "))" +
                        "[fFdD]?))" +
                        "[\\x00-\\x20]*");// Optional trailing "whitespace"

        if (Pattern.matches(fpRegex, str))
            return true;

        return false;
    }
}
