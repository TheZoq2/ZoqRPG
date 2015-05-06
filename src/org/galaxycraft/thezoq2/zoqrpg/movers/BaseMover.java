package org.galaxycraft.thezoq2.zoqrpg.movers;

import org.bukkit.util.Vector;
import org.galaxycraft.thezoq2.zoqrpg.fileio.StructBasedObject;

/**
 * Contains implementations of a bunch of functions common to most Movers
 *
 * @see Mover
 */
public abstract class BaseMover extends StructBasedObject implements Mover
{
    protected Vector position;
    protected Vector direction; //This warning is true and it is something I don't have a good way to fix. See my project
    //report for more in depth explanation. Anywhere an instance of this class is created, the setDirection method
    //should be called right afterwards

    protected float speed;

    protected BaseMover(float speed)
    {
        position = new Vector();
        this.speed = speed;
    }

    @Override
    public void setSpeed(float speed)
    {
        this.speed = speed;
    }
    @Override
    public float getSpeed()
    {
        return this.speed;
    }

    @Override
    public void setDirection(Vector direction)
    {
        this.direction = direction;
    }

    @Override
    public Vector getPosition()
    {
        return position;
    }

    @Override
    public abstract BaseMover cloneObject();
}
