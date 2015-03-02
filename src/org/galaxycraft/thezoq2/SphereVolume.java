package org.galaxycraft.thezoq2;

import org.bukkit.Location;
import org.bukkit.entity.Entity;
import org.bukkit.util.Vector;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by frans on 02/03/15.
 */
public class SphereVolume extends BaseVolume
{
    protected float radius;

    public SphereVolume(Vector center, float size)
    {
        super(center, size);

        radius = size;
    }

    @Override
    public boolean posIsInVolume(Location pos)
    {
        if(pos.distanceSquared(pos) < Math.pow(radius, 2))
        {
            return true;
        }

        return false;
    }

    @Override
    public List<Entity> getEntitiesInVolume(List<Entity> allEntities)
    {
        List<Entity> result = new ArrayList<>();

        //Looping through all the entities
        for(Entity entity : allEntities)
        {
            if(posIsInVolume(entity.getLocation()))
            {
                result.add(entity);
            }
        }

        return result;
    }

    @Override
    //TODO: Implement
    public List<Location> getBlocksInVolume()
    {
        return new ArrayList<>();
    }

    @Override
    //TODO: Implement
    public List<Location> getRandomPositionsInVolume(float density)
    {
        return new ArrayList<>();
    }
}
