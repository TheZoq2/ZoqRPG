package org.galaxycraft.thezoq2.zoqrpg.boons;

import org.bukkit.entity.Entity;
import org.galaxycraft.thezoq2.zoqrpg.Updatable;

public interface Boon extends Updatable
{
    boolean onApply(Entity affectedEntity, float strength);
    @Override
    void update(long timePassed);
    @Override
    void onEnd();

    //If the player already has the same boon, this is called

    void onReapply(float strength);

    @Override
    boolean isDone();

    Entity getAffectedEntity();

    /*

    Event handlers for bukkit events.

    Takes the event as parameter and returns false if the event should be canceled.
    The function itself shouldn't cancel the event

     */
    boolean onPlayerInterractEvent();

    Boon cloneBoon();

    float getStrength();
}
