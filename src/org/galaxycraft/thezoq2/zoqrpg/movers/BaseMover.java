package org.galaxycraft.thezoq2.zoqrpg.movers;

import org.bukkit.util.Vector;
import org.galaxycraft.thezoq2.zoqrpg.fileio.StructBasedObject;

/**
 * Created by frans on 03/03/15.
 */
public abstract class BaseMover extends StructBasedObject implements Mover
{
    protected Vector position;
    protected Vector direction;

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
