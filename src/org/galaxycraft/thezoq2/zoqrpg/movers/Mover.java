package org.galaxycraft.thezoq2.zoqrpg.movers;

import org.bukkit.util.Vector;

/**
 * Created by frans on 02/03/15.
 */
public interface Mover
{
    void setSpeed(float speed);
    float getSpeed();

    //public Location getNewLocation(long timePassed);
    void update(long timePassed);

    Vector getPosition(); //Returns the position relative to the starting position (0,0,0)
}
