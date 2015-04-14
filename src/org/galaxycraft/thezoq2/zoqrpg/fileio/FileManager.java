package org.galaxycraft.thezoq2.zoqrpg.fileio;

import java.io.*;

/**
 * Created by frans on 3/24/15.
 */
public final class FileManager
{
    private FileManager()
    {

    }

    private static final String pluginFolder = "plugins/ZoqRPG/";


    private static String getFullFilename(String filename)
    {
        StringBuilder finalFile = new StringBuilder(pluginFolder);
        finalFile.append(filename);

        return finalFile.toString();
    }

    public static void createPluginFoler()
    {
        File folder = new File(pluginFolder);
        if(!folder.exists())
        {
            folder.mkdirs();
        }
    }
    public static FileWriter getFileWriter(String filename) throws IOException
    {
        File file = new File(getFullFilename(filename));
        if(!file.exists())
        {
            file.createNewFile();
        }

        FileWriter writer = new FileWriter(file);

        return writer;
    }
    public static FileReader getFileReader(String filename) throws FileNotFoundException
    {
        FileReader reader = new FileReader(getFullFilename(filename));

        return reader;
    }
}
