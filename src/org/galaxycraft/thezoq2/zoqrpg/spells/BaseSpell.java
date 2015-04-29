package org.galaxycraft.thezoq2.zoqrpg.spells;

import org.bukkit.Location;
import org.bukkit.entity.Entity;
import org.galaxycraft.thezoq2.zoqrpg.UpdatableManager;
import org.galaxycraft.thezoq2.zoqrpg.boons.Boon;

/**
 * Created by frans on 03/03/15.
 */
public abstract class BaseSpell implements Spell
{
    protected Location startPos;
    protected Entity caster;

    protected boolean done = false;

    //Ideally this would be passed every update tick but in order to allow spells to inherit
    //updatable and allow updatable managers to handle spells, it is kept track of by the spell itself
    protected UpdatableManager<Boon> boonManager = null;


    BaseSpell(Location startPos, Entity caster)
    {
        this.startPos = startPos;
        this.caster = caster;
    }

    @Override
    public void onCreate(UpdatableManager<Boon> boonManager)
    {
        this.boonManager = boonManager;
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
}
