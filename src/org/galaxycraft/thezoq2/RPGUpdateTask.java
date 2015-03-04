package org.galaxycraft.thezoq2;

import org.bukkit.scheduler.BukkitRunnable;

/**
 * Created by frans on 08/02/15.
 */
public class RPGUpdateTask extends BukkitRunnable
{
    private long lastUpdate;
    private final RPGMain mainPlugin;

    public RPGUpdateTask(RPGMain mainPlugin)
    {
        this.mainPlugin = mainPlugin;

        this.lastUpdate = System.currentTimeMillis();
    }

    @Override
    public void run()
    {
        long timePassed = System.currentTimeMillis() - lastUpdate;
        lastUpdate = System.currentTimeMillis();

        mainPlugin.updateSpells(timePassed);
        mainPlugin.updateBoons(timePassed);
    }
}
