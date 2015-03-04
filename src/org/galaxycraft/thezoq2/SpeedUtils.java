package org.galaxycraft.thezoq2;

import org.bukkit.util.Vector;

import java.awt.image.DirectColorModel;

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
        return baseSpeed * (timePassed / 1000.0f);
    }
    public static Vector getCurrentMovementVector(Vector direction, float baseSpeed, long timePassed)
    {
        direction.multiply(getCurrentSpeed(baseSpeed, timePassed));
        //return direction.multiply(getCurrentSpeed(baseSpeed, timePassed));
        return direction;
    }
}
