package org.galaxycraft.thezoq2.zoqrpg.utils;

/**
 * Created by frans on 23/04/15.
 */
public final class GlobalConfig
{
    private static GlobalConfig instance;

    static
    {
        instance = new GlobalConfig();
    }

    private boolean printStacktraceForDefault = false;

    private GlobalConfig()
    {
    }

    public static GlobalConfig getInstance()
    {
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
