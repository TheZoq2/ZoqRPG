package org.galaxycraft.thezoq2;

import org.bukkit.Location;
import org.bukkit.entity.Entity;
import org.bukkit.util.Vector;

import java.util.List;

/**
 * Created by frans on 02/03/15.
 */
public interface Volume
{
    public void setCenter(Vector center);
    public Vector getCenter();

    public boolean posIsInVolume(Location pos);

    public List<Entity> getEntitiesInVolume(List<Entity> allEntities);
    public List<Location> getBlocksInVolume();
    public List<Location> getRandomPositionsInVolume(float density);
}
