package org.galaxycraft.thezoq2.zoqrpg.volumes;

import org.bukkit.util.Vector;

/**
 * Created by frans on 02/03/15.
 */
public abstract class BaseVolume implements Volume
{
    protected Vector center;
    protected float size;

    public BaseVolume(Vector center, float size)
    {
        this.center = center;
        this.size = size;
    }

    @Override
    public void setCenter(Vector center)
    {
        this.center = center;
    }
    @Override
    public Vector getCenter()
    {
        return this.center;
    }
}
