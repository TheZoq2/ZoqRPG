package org.galaxycraft.thezoq2.zoqrpg.utils;


/**
 * Class containing static utility methods that can be used to do simple calculations that need to be done in many
 * places around the code
 */
public final class GeneralUtils
{
    private GeneralUtils()
    {}

    /**
     * Returns the amount of game ticks a second is
     * There are 20 ticks per second
     */
    public static long getTicksFromMilliseconds(long milliseconds)
    {
        final float ticksPerSecond = 20.0f;
        float seconds = milliseconds / 1000.0f; //Magig number reason should be self explanatory
        return Math.round(seconds * ticksPerSecond);
    }
}
