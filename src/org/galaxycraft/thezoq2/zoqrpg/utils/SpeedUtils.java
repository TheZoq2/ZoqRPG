package org.galaxycraft.thezoq2.zoqrpg.utils;

import org.bukkit.util.Vector;

/**
 * Created by frans on 3/4/15.
 */
public final class SpeedUtils
{
    private SpeedUtils()
    {

    }

    public static float getCurrentSpeed(float baseSpeed, long timePassed)
    {
        float speed = baseSpeed * (timePassed / 1000.0f);
        return speed;
    }
    public static Vector getCurrentMovementVector(Vector direction, float baseSpeed, long timePassed)
    {
        Vector vec = direction.clone();

        vec.multiply(getCurrentSpeed(baseSpeed, timePassed));
        //return direction.multiply(getCurrentSpeed(baseSpeed, timePassed));
        return vec;
    }
}
