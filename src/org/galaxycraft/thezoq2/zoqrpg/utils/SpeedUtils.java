package org.galaxycraft.thezoq2.zoqrpg.utils;

import org.bukkit.util.Vector;

/**
 * Utitlity class that contains a couple of static functions for converting speeds to diffirent formats and based
 * on diffirent parameters.
 */
public final class SpeedUtils
{
    private SpeedUtils()
    {

    }

    public static float getCurrentSpeed(float baseSpeed, long timePassed)
    {
        assert(timePassed >= 0);

        float speed = baseSpeed * (timePassed / 1000.0f); //Magic number reason should be self explanatory
        return speed;
    }
    public static Vector getCurrentMovementVector(Vector direction, float baseSpeed, long timePassed)
    {
        assert(timePassed >= 0);

        Vector vec = direction.clone();

        vec.multiply(getCurrentSpeed(baseSpeed, timePassed));
        //return direction.multiply(getCurrentSpeed(baseSpeed, timePassed));
        return vec;
    }
}
