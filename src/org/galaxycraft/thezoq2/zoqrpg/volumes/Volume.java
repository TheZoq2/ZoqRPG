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
    //which would implement this method
    @SuppressWarnings("EmptyMethod")
    void update(float timePassed);

    boolean posIsInVolume(Vector center, Vector pos);

    List<Entity> getEntitiesInVolume(Vector center, List<Entity> allEntities);
    List<Vector> getBlocksInVolume(Vector center);

    @Override
    Volume cloneObject();
}
