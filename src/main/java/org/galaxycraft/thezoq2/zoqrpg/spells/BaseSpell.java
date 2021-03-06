package org.galaxycraft.thezoq2.zoqrpg.spells;

import org.bukkit.Location;
import org.bukkit.entity.Entity;
import org.galaxycraft.thezoq2.zoqrpg.UpdatableManager;
import org.galaxycraft.thezoq2.zoqrpg.boons.Boon;

/**
 * Basic implementation of the spell interface that contains some functions that are used by a lot of
 * spells
 */
public abstract class BaseSpell implements Spell
{
    protected Location startPos;
    protected Entity caster;

    protected boolean done = false;

    //Ideally this would be passed every update tick but in order to allow spells to inherit
    //updatable and allow updatable managers to handle spells, it is kept track of by the spell itself
    protected UpdatableManager<Boon> boonManager = null;


    BaseSpell()
    {
        this.startPos = null;
        this.caster = null;
    }

    @Override
    public void onCreate(UpdatableManager<Boon> boonManager)
    {
        this.boonManager = boonManager;
    }

    @Override
    public void setCaster(Entity caster)
    {
        this.caster = caster;
    }
    @Override
    public void setStartPos(Location pos)
    {
        this.startPos = pos;
    }

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

    @Override
    public abstract BaseSpell cloneObject();
}
