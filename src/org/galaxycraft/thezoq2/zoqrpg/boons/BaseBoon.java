package org.galaxycraft.thezoq2.zoqrpg.boons;

import org.bukkit.entity.Entity;
import org.galaxycraft.thezoq2.zoqrpg.visualisers.Visualiser;

public abstract class BaseBoon implements Boon
{
    protected Entity affectedEntity;
    protected boolean done = false;
    protected float strength;

    protected Visualiser visualiser = null;

    @Override
    public boolean onApply(Entity affectedEntity, float strength)
    {
        this.strength = strength;

        this.affectedEntity = affectedEntity;

        return true;
    }

    @Override
    public float getStrength()
    {
        return this.strength;
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
    public boolean onPlayerInterractEvent()
    {
        return true;
    }

    protected void visualise()
    {
        if(visualiser != null)
        {
            visualiser.showLocation(affectedEntity.getLocation());
        }
        else
        {
            //TODO: Implement custom exception
        }
    }

}
