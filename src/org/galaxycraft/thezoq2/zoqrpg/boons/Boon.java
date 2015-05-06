package org.galaxycraft.thezoq2.zoqrpg.boons;

import org.bukkit.entity.Entity;
import org.bukkit.event.player.PlayerInteractEvent;
import org.galaxycraft.thezoq2.zoqrpg.CloneableObject;
import org.galaxycraft.thezoq2.zoqrpg.Updatable;

/**
 * A Boon can be applied to an entity in order to cause a lasting effect on that entity. The boon will do something to
 * the affected entity until it is removed.
 *
 * Creation of new Boons should ideally only be done by the MoverFactory class. If a new mover needs to be
 * created outside of the factory, it should be cloned from a prexisting Boon
 * @see org.galaxycraft.thezoq2.zoqrpg.factories.BoonFactory
 *
 * Extends the CloneableObject interface in order to allow factories to create new boons by cloning templates
 * @see CloneableObject
 *
 * Extends Updatable to inherit basic functions called when a new server tick has passed.
 * @see Updatable
 */

public interface Boon extends CloneableObject, Updatable
{
    /**
     * Function run when the effect is applied to an entity. Returns false if the effect wasn't applied and should be
     * removed from the manager
     *
     * @param affectedEntity The entity about to be affected by the boon
     * @param strength The strength of the boon
     * @return false if the boon shouldn't be applied
     */
    boolean onApply(Entity affectedEntity, float strength);

    //If the player already has the same boon, this is called

    //This method is only needed for boons that shouldn't be applied twice. However, at the moment there are no such
    //boons and this method is unused.

    /**
     * Function run when the same type of boon is already applied to the entity. Will change the current boon instead
     * of creating a new one.
     *
     * @param strength The strength of the new boon that might override the old one.
     */
    void onReapply(float strength);

    Entity getAffectedEntity();

    /*

    Event handlers for bukkit events.

    Takes the event as parameter and returns false if the event should be canceled.
    The function itself shouldn't cancel the event

     */

    /**
     * Event handler that runs if a bukkit onPlayerInterractEvent is fired which invlovles this boon
     * Returns true if the event should be canceled. The function itself shouldn't cancel the event
     *
     * @param event The player interract event that was fired.
     * @return true if the event should be canceled
     */
    boolean onPlayerInterractEvent(PlayerInteractEvent event);

    @Override
    Boon cloneObject();
}
