package org.galaxycraft.thezoq2.zoqrpg.utils;

/**
 * Created by frans on 05/03/15.
 */
public class GeneralUtils
{
    /**
     * Returns the amount of game ticks a second is
     */
    public static long getTicksFromMilliseconds(long milliseconds)
    {
        return Math.round((milliseconds / 1000.0f) / 20.0f);
    }
}
