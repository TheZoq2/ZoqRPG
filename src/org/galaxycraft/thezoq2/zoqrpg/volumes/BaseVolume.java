package org.galaxycraft.thezoq2.zoqrpg.volumes;

import org.galaxycraft.thezoq2.zoqrpg.fileio.StructBasedObject;

/**
 * Created by frans on 02/03/15.
 */
public abstract class BaseVolume extends StructBasedObject implements Volume
{
    //protected Vector center;
    protected float size;

    protected BaseVolume(float size)
    {
        //this.center = center;
        this.size = size;
    }

    protected void setSize(float size)
    {
        this.size = size;
    }

    /*@Override
    public void setCenter(Vector center)
    {
        this.center = center;
    }
    @Override
    public Vector getCenter()
    {
        return this.center;
    }*/

    public abstract BaseVolume clone();
}
