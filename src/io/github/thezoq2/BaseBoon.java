package io.github.thezoq2;

import org.bukkit.entity.Player;
import org.bukkit.event.player.PlayerEvent;

public abstract class BaseBoon implements Boon
{
    protected Player affectedPlayer;
    protected boolean done = false;

    public BaseBoon(Player target)
    {
        this.affectedPlayer = target;
    }

    @Override
    public Player getAffectedPlayer()
    {
        return affectedPlayer;
    }

    @Override
    public boolean isDone()
    {
        return done;
    }

    //Returns true if the event is triggered by the player affected by this event
    @Override
    public boolean boonAffectsPlayerEvent(PlayerEvent event)
    {
        if(event.getPlayer() == affectedPlayer)
        {
            return true;
        }
        return false;
    }

    @Override
    public void onPlayerInterractEvent()
    {

    }
}
