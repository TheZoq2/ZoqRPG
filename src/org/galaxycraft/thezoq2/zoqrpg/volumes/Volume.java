package org.galaxycraft.thezoq2.zoqrpg.volumes;

import org.bukkit.entity.Entity;
import org.bukkit.util.Vector;

import java.util.List;

/**
 * Created by frans on 02/03/15.
 */
public interface Volume
{
    void setCenter(Vector center);
    Vector getCenter();

    void update(float timePassed);

    boolean posIsInVolume(Vector pos);

    List<Entity> getEntitiesInVolume(List<Entity> allEntities);
    List<Vector> getBlocksInVolume();
    List<Vector> getRandomPositionsInVolume(float density);
}
