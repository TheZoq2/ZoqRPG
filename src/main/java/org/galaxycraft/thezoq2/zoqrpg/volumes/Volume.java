package org.galaxycraft.thezoq2.zoqrpg.volumes;

import org.bukkit.entity.Entity;
import org.bukkit.util.Vector;
import org.galaxycraft.thezoq2.zoqrpg.CloneableObject;

import java.util.List;

/**
 * A volume is a representation of a group of blocks in the world. Volumes are used by the modular spell to affect a
 * specific area of the world. Contains functions for returning all the entities inside the volume and all the blocks
 * inside the volume
 *
 * Implements the cloneable interface to allow the creation of new Volumes ny cloning prexisting ones.
 * @see CloneableObject
 *
 * New volumes should only be constructed by the VolumeFactory, if needed elsewhere, they should be cloned from
 * prexsisting volumes.
 */
public interface Volume extends CloneableObject
{

    //This method is not used by current volumes but in the future, I plan to add things like expanding bubbles
    //which would implement this method.
    //The time passed variable is the same thing, it will be used in more dynamic volumes.
    void update(float timePassed);

    boolean posIsInVolume(Vector center, Vector pos); //This method is used internally by some volumes. Might also be used
    //in  the future

    /**
     * Returns all the entities out of a list that are inside the volume
     * @param center the position of the volume in the world
     * @param allEntities a list of entities to check for inside the volume
     * @return a list of entities that are inside the volume
     */
    List<Entity> getEntitiesInVolume(Vector center, List<Entity> allEntities);

    /**
     * Returns all blocks that are inside the volume
     * @param center The position of the volume in the world
     * @return the list of block locations
     */
    List<Vector> getBlocksInVolume(Vector center);

    @Override
    Volume cloneObject();
}
