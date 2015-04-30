package org.galaxycraft.thezoq2.zoqrpg.visualisers;

import org.bukkit.Location;
import org.galaxycraft.thezoq2.Clonable;

/**
 * Used to visualise a single point
 */
public interface Visualiser <T extends Visualiser> extends Clonable<T>
{
    void update();

    void showLocation(Location loc);

    @Override
    T clone();
}
