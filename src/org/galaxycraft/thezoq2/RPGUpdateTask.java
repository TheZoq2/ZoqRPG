package org.galaxycraft.thezoq2;

import org.bukkit.scheduler.BukkitRunnable;

/**
 * Created by frans on 08/02/15.
 */
public class RPGUpdateTask extends BukkitRunnable
{
    private final RPGMain mainPlugin;

    public RPGUpdateTask(RPGMain mainPlugin)
    {
        this.mainPlugin = mainPlugin;
    }

    @Override
    public void run()
    {
        mainPlugin.updateSpells();
        mainPlugin.updateBoons();
    }
}
