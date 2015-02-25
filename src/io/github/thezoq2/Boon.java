package io.github.thezoq2;

import org.bukkit.entity.Player;
import org.bukkit.event.player.PlayerEvent;

/**
 * Created by frans on 21/02/15.
 */
public interface Boon
{
    void onApply();
    void update();
    void onEnd();

    boolean isActive();

    public Player getAffectedPlayer();

    public void onPlayerInterractEvent();

    public boolean boonAffectsPlayerEvent(PlayerEvent event);
}
