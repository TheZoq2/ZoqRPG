package org.galaxycraft.thezoq2.zoqrpg.spells;

import org.bukkit.Location;
import org.bukkit.entity.Entity;
import org.galaxycraft.thezoq2.zoqrpg.CloneableObject;
import org.galaxycraft.thezoq2.zoqrpg.Updatable;
import org.galaxycraft.thezoq2.zoqrpg.UpdatableManager;
import org.galaxycraft.thezoq2.zoqrpg.boons.Boon;

/**
 * Interface for spells which can be casted by an entity.
 *
 * Spells should only be constructed by the SpellFactory. If they need to be created outside the Factory, this should
 * be done by cloning a preexisting spell.
 * @see org.galaxycraft.thezoq2.zoqrpg.factories.SpellFactory
 *
 * Extends the Updatable interface in order to allow the spell to persist over many server ticks
 * @see Updatable
 *
 * Extends the ClonableObject interface in order to allow the spells to be cloned from template versions inside
 * and outside the factory.
 * @see CloneableObject
 */
public interface Spell extends Updatable, CloneableObject
{
    //Ideally boonManager would be passed every update tick but in order to allow spells to inherit
    //updatable and allow updatable managers to handle spells, it is kept track of by the spell itself
    void onCreate(UpdatableManager<Boon> boonManager);
    void setStartPos(Location pos);

    void setCaster(Entity caster);
    Entity getCaster(); //Not used at the moment but will be in the futrue when more of the plugin is implemented
}
