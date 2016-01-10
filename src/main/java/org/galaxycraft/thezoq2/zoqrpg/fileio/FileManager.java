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


    /**
     * Return the full filename of a filename in the plugin folder relative to the working directory of the server
     * @param filename the name of the file or folder inside the plugin folder
     * @return the path to the file or folder
     */
    private static String getFullFilename(String filename)
    {

        return PLUGIN_FOLDER + filename;
    }

    /**
     * creates a new confguration folder if such a folder doesn't exist already
     */
    public static void createPluginFoler()
    {
        File folder = new File(PLUGIN_FOLDER);
        if(!folder.exists())
        {
            folder.mkdirs();
        }
    }

    /**
     * Create a fileReader object for a file in the plugin folder
     * @param filename the filename of the file to look for inside the plugin folder
     * @return a file object
     * @throws FileNotFoundException if the file isn't found
     */
    public static FileReader getFileReader(String filename) throws FileNotFoundException
    {
        FileReader reader = new FileReader(getFullFilename(filename));

        return reader;
    }
}
