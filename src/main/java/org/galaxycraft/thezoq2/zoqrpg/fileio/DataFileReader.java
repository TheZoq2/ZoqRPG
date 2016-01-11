package org.galaxycraft.thezoq2.zoqrpg.fileio;

import org.galaxycraft.thezoq2.zoqrpg.exceptions.*;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;

/**
 * Class that parses config files in my own format into a StructValue that contains all the variables in the file
 *
 * @see StructValue
 *
 * The file format is as follows:
 *
 * Any variable is defined by it's name, then an equals sign.
    varName =
 * It is case sensitive and because I was dumb when I wrote the parser, spaces are not read which means that
    "var Name     = "
 * is parsed as
    "varName="
 * A variable is a struct if the next character is a { and followed by a }. This means that in order to create a new struct, you would do this:
 *
    structName = {
 	    //Member variables
    }
 *
 *
 * A variable is treated as a number if the value can be parsed to a float and as a string if it can not. The end of the variable is marked by a semicolon.
 *
    aString=hello world; //Valid string, will be treated as helloworld because of whitespace removal

    anotherString=123.215sticks; //can't be parsed to float

    aNumber=123;
    anotherNumber=123.215;
 *
    blink={ //struct containing following variables
 	    base=blink; //String
 	    distance=10; //Number

 	    memberStruct= //New struct inside struct
 	    {
 		    memberMemberVar = something;
 	    }
    }
 *
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
    //An enum is obviously not going to be executed
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
                            throw new DuplicateVariableNameException(e.getVarName(), filename, currentChar);
                        }
                        break;
                    }
                }
            }

            currentChar = nextSpecial + 1;
        }

        return resultStruct;
    }

    /**
     * Removes all the whitespace from a string. used for later parsing
     * @param line the string to modify
     * @return the string that was modified
     */
    private String stripWhitespace(String line)
    {
        line = line.replaceAll("\n", "");
        line = line.replaceAll(" ", "");
        line = line.replaceAll("\t", "");

        return line;
    }

    /**
     * Removes anyting on a line that comes after two forward slashes
     * @param line the line to strip
     * @return the newly modified line
     */
    private String stripComments(String line)
    {
        int commentStart = line.indexOf("//");

        if(commentStart != -1)
        {
            line = line.substring(0, commentStart);
        }
        return line;
    }

    /**
     * Returns the next non alpha-numeric character after a cirtain point in a string
     * @param start the index to start the search at
     * @return the index of the next non-aplha numeric character. -1 if none exist
     */
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

    /**
     * Returns the line at which a cirtain character was on before the lines were stripped down to a single file
     * @param charPos the index of the character to look for
     * @return the position of the character. 0 if it wasn't in the original string
     */
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

    /**
     * Returns the bracket that matches an opened bracket before the start character. The matching bracket needs to
     * be on the same level as the starting bracket which means that any brackets opened before finding a closing bracket
     * need to be closed before the matching bracket is found.
     *
     * The function looks through the finalString variable
     *
     * @param start the character to start the search at.
     * @param openBracket the character to count as an open bracket
     * @param closeBracket the character to count as a closing bracket
     * @return the position of the matching bracket
     * @throws MissmatchedBracketException if no matching bracket is found before the end of the line
     */
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

    //Code 'stolen' from stackoverflow here: http://stackoverflow.com/questions/8564896/fastest-way-to-check-if-a-string-can-be-parsed-to-double-in-java

    /**
     * Returns true if a CharSequence can be parsed into a float
     * @param str the string to check
     * @return true if the string can be read as a number
     */
    //No warnings in this function apply to my project since it is not my code
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
