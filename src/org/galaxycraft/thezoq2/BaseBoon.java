package org.galaxycraft.thezoq2;

import org.bukkit.Location;
import org.bukkit.entity.Entity;

public abstract class BaseBoon implements Boon
{
    protected Entity affectedEntity;
    protected boolean done = false;
    protected float strength;

    @Override
    public void onApply(Entity affectedEntity, float strength)
    {
        this.strength = strength;

        this.affectedEntity = affectedEntity;
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
}
