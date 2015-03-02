package org.galaxycraft.thezoq2;

import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.entity.*;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by frans on 08/02/15.
 */
public abstract class BaseAirSpell extends BaseSpell
{

    private static boolean entityIsAffected(Entity entity)
    {
        if(entity instanceof Vehicle)
            return false;
        else if(entity instanceof EnderCrystal)
            return false;
        else if(entity instanceof ArmorStand)
            return false;
        else if(entity instanceof Hanging)
            return false;
        return true;
    }

    public BaseAirSpell(Location pos)
    {
        super(pos);
    }

    public List<Entity> getAffectedEntities(World world)
    {
        List<Entity> result = new ArrayList<Entity>();

        List<Entity> worldEntities = world.getEntities();

        //Checking for entities
        for(Entity entity : worldEntities)
        {
            if(entityIsAffected(entity))
            {
                result.add(entity);

                //System.out.println(entity.toString());
            }
        }

        return result;
    }

}
