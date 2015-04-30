package org.galaxycraft.thezoq2.zoqrpg.visualisers;

import org.bukkit.Location;
import org.galaxycraft.thezoq2.zoqrpg.CloneableObject;

/**
 * Used to visualise a single point
 */
public interface Visualiser extends CloneableObject
{
    void update();

    void showLocation(Location loc);

    @Override
    Visualiser cloneObject();
}
