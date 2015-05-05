package org.galaxycraft.thezoq2.zoqrpg.movers;

import org.bukkit.util.Vector;
import org.galaxycraft.thezoq2.zoqrpg.CloneableObject;

/**
 * Interface for a module that keeps track of a position and changes that position in some direction every time it
 * is updated. Movers have a general movement direction that needs to be specified before use aswell as a speed
 * value which decides how fast it should move
 *
 * Creation of new movers should ideally only be done by the MoverFactory class. If a new mover needs to be
 * created outside of the factory, it should be cloned from a prexisting mover
 *
 * Extends the CloneableObject interface to enable creation by cloning in the MoverFactory
 *
 * @see CloneableObject
 * @see org.galaxycraft.thezoq2.zoqrpg.factories.MoverFactory
 */
public interface Mover extends CloneableObject
{
    void setSpeed(float speed);
    float getSpeed();
    void setDirection(Vector direction);

    //public Location getNewLocation(long timePassed);
    void update(long timePassed);

    Vector getPosition(); //Returns the position relative to the starting position (0,0,0)

    @Override
    Mover cloneObject();
}
