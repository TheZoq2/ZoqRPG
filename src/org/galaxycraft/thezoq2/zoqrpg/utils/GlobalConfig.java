package org.galaxycraft.thezoq2.zoqrpg.utils;

/**
 * Created by frans on 23/04/15.
 */
public class GlobalConfig
{
    private static GlobalConfig instance;

    private boolean printStacktraceForDefault = false;

    private GlobalConfig()
    {
    }

    public static GlobalConfig getInstance()
    {
        if(instance == null)
        {
            instance = new GlobalConfig();
        }

        return instance;
    }

    public boolean shouldPrintStackForDefault()
    {
        return printStacktraceForDefault;
    }

    public void printStackTraceForDefault(Exception e)
    {
        if(printStacktraceForDefault)
        {
            e.printStackTrace();
        }
    }
}
