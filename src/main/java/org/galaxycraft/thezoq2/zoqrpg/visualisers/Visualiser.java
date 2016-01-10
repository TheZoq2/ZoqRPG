package org.galaxycraft.thezoq2.zoqrpg.visualisers;

import org.bukkit.Location;
import org.galaxycraft.thezoq2.zoqrpg.CloneableObject;

/**
 * Used to visualise a single point in space.
 *
 * Implements the cloneable object in order to allow the creation of new objects through cloning.
 * @see CloneableObject
 *
 * Should be constructed by a VisualiserFactory or cloned if creation is done outside
 */
public interface Visualiser extends CloneableObject
{
    /**
     * Update variables that should update periodically within the visualiser
     */
    void update();

    /**
     * Visualise a specific location
     * @param loc the location to be visualised
     */
    void showLocation(Location loc);

    @Override
    Visualiser cloneObject();
}
