package org.galaxycraft.thezoq2.zoqrpg.spells;

import org.bukkit.entity.Entity;
import org.galaxycraft.thezoq2.zoqrpg.Updatable;
import org.galaxycraft.thezoq2.zoqrpg.UpdatableManager;
import org.galaxycraft.thezoq2.zoqrpg.boons.Boon;

/**
 * Created by frans on 03/03/15.
 */
public interface Spell extends Updatable
{
    //Ideally boonManager would be passed every update tick but in order to allow spells to inherit
    //updatable and allow updatable managers to handle spells, it is kept track of by the spell itself
    void onCreate(UpdatableManager<Boon> boonManager);

    Entity getCaster();
}
