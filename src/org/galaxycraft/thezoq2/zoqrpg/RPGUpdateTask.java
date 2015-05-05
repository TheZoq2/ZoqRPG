package org.galaxycraft.thezoq2.zoqrpg;

import org.bukkit.scheduler.BukkitRunnable;

/**
 * Contains an update function that is run every server tick in order to keep doing things even if bukkit has not triggered
 * any events.
 *
 * The main purpose of the function is to update all the Updatable objects in the main class.
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
