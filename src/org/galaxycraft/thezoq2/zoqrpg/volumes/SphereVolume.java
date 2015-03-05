package org.galaxycraft.thezoq2.zoqrpg.volumes;

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
    public void update(float timePassed)
    {

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
    public List<Vector> getBlocksInVolume()
    {
        List<Vector> result = new ArrayList<>();

        //Going through all the blocks that could be in the sphereA
        for(float x = -radius; x <= radius; x++)
        {
            for(float y = -radius; y <= radius; y++)
            {
                for(float z = -radius; z <= radius; z++)
                {
                    int xPos = (int) x;
                    int yPos = (int) y;
                    int zPos = (int) z;

                    result.add(new Vector(xPos, yPos, zPos));
                }
            }
        }

        return result;
    }

    @Override
    //TODO: Implement
    public List<Vector> getRandomPositionsInVolume(float density)
    {
        return new ArrayList<>();
    }
}
