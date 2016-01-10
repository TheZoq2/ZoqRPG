package org.galaxycraft.thezoq2.zoqrpg.volumes;

import org.bukkit.entity.Entity;
import org.bukkit.util.Vector;
import org.galaxycraft.thezoq2.zoqrpg.fileio.NumberValue;
import org.galaxycraft.thezoq2.zoqrpg.fileio.StructValue;

import java.util.ArrayList;
import java.util.List;

/**
 * Volume that represents a sphere of blocks with a fixed radius.
 *
 * @see BaseVolume
 * @see Volume
 */
public class SphereVolume extends BaseVolume
{
    //Default values
    private static final float DEFAULT_RADIUS = 1;

    //Creating variables
    private float radius = DEFAULT_RADIUS;

    public SphereVolume(float size)
    {
        super(size);

        this.radius = size;
    }


    public SphereVolume(StructValue sv)
    {
        super(DEFAULT_RADIUS);

        this.radius = (float) readValueWithFallback(sv, "radius", new NumberValue(DEFAULT_RADIUS), NumberValue.class).getValue();

        setSize(radius);
    }

    @Override
    public void update(float timePassed)
    {

    }

    @Override
    public boolean posIsInVolume(Vector center, Vector pos)
    {
        float distance = (float) center.distance(pos);
        if(distance < this.radius)
        {
            return true;
        }

        return false;
    }

    @Override
    public List<Entity> getEntitiesInVolume(Vector center, List<Entity> allEntities)
    {
        List<Entity> result = new ArrayList<>();

        //Looping through all the entities
        for(Entity entity : allEntities)
        {
            if(posIsInVolume(center, entity.getLocation().toVector()))
            {
                result.add(entity);
            }
        }


        return result;
    }

    @Override
    public List<Vector> getBlocksInVolume(Vector center)
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

                    if(posIsInVolume(center, testPos))
                    {
                        result.add(testPos);
                    }
                }
            }
        }

        return result;
    }

    @Override
    public SphereVolume cloneObject()
    {
        return new SphereVolume(this.size);
    }
}
