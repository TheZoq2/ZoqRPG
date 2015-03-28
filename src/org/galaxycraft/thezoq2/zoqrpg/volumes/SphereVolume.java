package org.galaxycraft.thezoq2.zoqrpg.volumes;

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

        this.radius = size;
    }

    @Override
    public void update(float timePassed)
    {

    }

    @Override
    public boolean posIsInVolume(Vector pos)
    {
        float distance = (float) super.center.distance(pos);
        if(distance < this.radius)
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
            if(posIsInVolume(entity.getLocation().toVector()))
            {
                result.add(entity);
            }
        }


        return result;
    }

    @Override
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
                    Vector testPos = new Vector((int) x, (int) y, (int) z);

                    testPos.add(center);

                    if(posIsInVolume(testPos))
                    {
                        result.add(testPos);
                    }
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
