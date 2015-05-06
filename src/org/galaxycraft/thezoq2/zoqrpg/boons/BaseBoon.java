package org.galaxycraft.thezoq2.zoqrpg.boons;

import org.bukkit.entity.Entity;
import org.bukkit.event.player.PlayerInteractEvent;
import org.galaxycraft.thezoq2.zoqrpg.fileio.StructBasedObject;
import org.galaxycraft.thezoq2.zoqrpg.visualisers.Visualiser;

/**
 * Contains basic functions that are used by most boons. Also extends StructBased object in order to allow the
 * creation of boons based on StructValues. Implements the Boon interface.
 *
 * @see StructBasedObject
 * @see Boon
 */
public abstract class BaseBoon extends StructBasedObject implements Boon
{
    protected Entity affectedEntity = null;
    protected boolean done = false;
    protected float strength; //This value is used in subclasses

    protected Visualiser visualiser = null;

    @Override
    public boolean onApply(Entity affectedEntity, float strength)
    {
        this.strength = strength;

        this.affectedEntity = affectedEntity;

        return true;
    }

    @Override
    public Entity getAffectedEntity()
    {
        return affectedEntity;
    }

    @Override
    public boolean isDone()
    {
        return done;
    }

    @Override
    public boolean onPlayerInterractEvent(PlayerInteractEvent event)
    {
        return true;
    }

    /**
     * Play a visualiser effect at the position of the boon. if this should be done. if this is desired, the visualiser
     * variable should be set by a subclass.
     */
    //Currently unused method which I plan to use at some point.
    protected void visualise()
    {
        if(visualiser != null)
        {
            visualiser.showLocation(affectedEntity.getLocation());
        }
        else //This method isn't done yet, warning can be ignored
        {
            //TODO: Implement custom exception
        }
    }

    @Override
    public abstract BaseBoon cloneObject();
}
