package org.galaxycraft.thezoq2.zoqrpg.boons;

import org.bukkit.entity.Entity;
import org.galaxycraft.thezoq2.zoqrpg.CloneableObject;
import org.galaxycraft.thezoq2.zoqrpg.Updatable;

/**
 * A Boon can be applied to an entity in order to cause a lasting effect on that entity. The boon will do something to
 * the affected entity until it is removed.
 *
 * Extends the CloneableObject interface in order to allow factories to create new boons by cloning templates
 * @see CloneableObject
 *
 * Extends Updatable to inherit basic functions called when a new server tick has passed.
 * @see Updatable
 */

public interface Boon extends CloneableObject, Updatable
{
    boolean onApply(Entity affectedEntity, float strength);

    //If the player already has the same boon, this is called

    void onReapply(float strength);

    Entity getAffectedEntity();

    /*

    Event handlers for bukkit events.

    Takes the event as parameter and returns false if the event should be canceled.
    The function itself shouldn't cancel the event

     */
    boolean onPlayerInterractEvent();

    @Override
    Boon cloneObject();
}
