package org.galaxycraft.thezoq2;

import org.bukkit.World;
import org.bukkit.entity.Entity;

/**
 * Created by frans on 03/03/15.
 */
public abstract class BaseSpell implements Spell
{
    protected World world;
    protected Entity caster;

    BaseSpell(World world, Entity caster)
    {
        this.world = world;
        this.caster = caster;
    }

    protected boolean done = false;

    @Override
    public boolean isDone()
    {
        return done;
    }

    @Override
    public Entity getCaster()
    {
        return caster;
    }
}
