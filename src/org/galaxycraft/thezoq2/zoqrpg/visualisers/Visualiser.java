package org.galaxycraft.thezoq2.zoqrpg.visualisers;

import org.bukkit.Location;

/**
 * Used to visualise a single point
 */
public interface Visualiser
{
    void update();

    void showLocation(Location loc);
}
