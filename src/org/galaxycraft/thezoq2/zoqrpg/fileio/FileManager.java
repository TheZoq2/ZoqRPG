package org.galaxycraft.thezoq2.zoqrpg.fileio;

import java.io.*;

/**
 * Utility class for loading and storing files in the correct location on the server
 */
public final class FileManager
{
    private FileManager()
    {

    }

    private static final String PLUGIN_FOLDER = "plugins/ZoqRPG/";


    private static String getFullFilename(String filename)
    {
        StringBuilder finalFile = new StringBuilder(PLUGIN_FOLDER);
        finalFile.append(filename);

        return finalFile.toString();
    }

    public static void createPluginFoler()
    {
        File folder = new File(PLUGIN_FOLDER);
        if(!folder.exists())
        {
            folder.mkdirs();
        }
    }
    public static FileReader getFileReader(String filename) throws FileNotFoundException
    {
        FileReader reader = new FileReader(getFullFilename(filename));

        return reader;
    }
}
