package org.galaxycraft.thezoq2.zoqrpg.volumes;

import org.galaxycraft.thezoq2.zoqrpg.fileio.StructBasedObject;

/**
 * Base class implementing methods commonly used by Base volumes.
 *
 * Extends the StructBasedObject class to allow the creation of new volumes based on structs
 * @see StructBasedObject
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

    public abstract BaseVolume cloneObject();
}
