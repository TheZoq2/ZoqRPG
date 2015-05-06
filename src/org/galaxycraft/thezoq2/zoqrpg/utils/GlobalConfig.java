package org.galaxycraft.thezoq2.zoqrpg.utils;

/**
 * Singleton that contains data that should be loaded from config files in the future and that is used in many places.
 *
 * Also contains a bunch of functions that do common tasks based on the config values.
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

    public void printStackTraceForDefault(Exception e)
    {
        if(printStacktraceForDefault)
        {
            e.printStackTrace();
        }
    }
}
