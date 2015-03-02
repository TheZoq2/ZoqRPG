package org.galaxycraft.thezoq2;

import org.bukkit.Location;

/**
 * Created by frans on 02/03/15.
 */
public interface Mover
{
    public void setSpeed(float speed);
    public float getSpeed();

    public Location getNewLocation(long timePassed);
}
