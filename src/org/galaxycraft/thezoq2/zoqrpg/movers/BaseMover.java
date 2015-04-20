package org.galaxycraft.thezoq2.zoqrpg.movers;

import org.bukkit.util.Vector;

/**
 * Created by frans on 03/03/15.
 */
public abstract class BaseMover implements Mover
{
    protected Vector position;

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
    public Vector getPosition()
    {
        return position;
    }
}
