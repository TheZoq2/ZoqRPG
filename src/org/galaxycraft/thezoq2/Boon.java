package org.galaxycraft.thezoq2;

import org.bukkit.entity.Entity;

public interface Boon
{
    void onApply(Entity entity, float strength);
    void update();
    void onEnd();

    //If the player already has the same boon, this is called
    void onReapply(float strength);

    boolean isDone();

    public Entity getAffectedEntity();

    /*

    Event handlers for bukkit events.

    Takes the event as parameter and returns false if the event should be canceled.
    The function itself shouldn't cancel the event

     */
    public boolean onPlayerInterractEvent();

    public Boon clone();
}
