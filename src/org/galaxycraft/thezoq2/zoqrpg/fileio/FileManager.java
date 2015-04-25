package org.galaxycraft.thezoq2.zoqrpg.fileio;

import java.io.*;
import java.net.URL;

/**
 * Created by frans on 3/24/15.
 */
public final class FileManager
{
    private FileManager()
    {

    }

    private static final String PLUGIN_FOLDER = "plugins/ZoqRPG/";


    private static String getFullFilename(String filename)
    {
        String finalFile = FileManager.class.getResource(PLUGIN_FOLDER + filename).getFile();

        return finalFile;
    }

    public static void createPluginFoler()
    {
        String folderPath = FileManager.class.getResource(PLUGIN_FOLDER).getFile();

        File folder = new File(folderPath);
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
