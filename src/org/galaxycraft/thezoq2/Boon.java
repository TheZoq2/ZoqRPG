package org.galaxycraft.thezoq2;

import org.bukkit.entity.Entity;

public interface Boon extends Updatable
{
    public void onApply(Entity affectedEntity, float strength);
    @Override
    public void update(long timePassed);
    @Override
    public void onEnd();

    //If the player already has the same boon, this is called
    public void onReapply(float strength);

    @Override
    public boolean isDone();

    public Entity getAffectedEntity();

    /*

    Event handlers for bukkit events.

    Takes the event as parameter and returns false if the event should be canceled.
    The function itself shouldn't cancel the event

     */
    public boolean onPlayerInterractEvent();

    public Boon cloneBoon();
}
