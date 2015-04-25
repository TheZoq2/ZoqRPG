package org.galaxycraft.thezoq2.zoqrpg.volumes;

import org.bukkit.entity.Entity;
import org.bukkit.util.Vector;

import java.util.List;

/**
 * Created by frans on 02/03/15.
 */
public interface Volume
{

    //This method is not used by current volumes but in the future, I plan to add things like expanding bubbles
    //which would implement this method
    @SuppressWarnings("EmptyMethod")
    void update(float timePassed);

    boolean posIsInVolume(Vector center, Vector pos);

    List<Entity> getEntitiesInVolume(Vector center, List<Entity> allEntities);
    List<Vector> getBlocksInVolume(Vector center);
    List<Vector> getRandomPositionsInVolume(Vector center, float density);
}
