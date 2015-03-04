package org.galaxycraft.thezoq2;

import org.bukkit.entity.Entity;

import java.util.List;

/**
 * Created by frans on 01/03/15.
 */
public final class BoonFactory
{
    private BoonFactory()
    {

    }

    /**
        Tries to add a boon to the entity specified. If the entity already has that boon, it will
        run the onReapply function instead.

        @param boonType The type of boon to be applied
        @param targetEntity The entity to apply the boon to
        @param boonsOnEntity List of boons allready applied to the entity
        @param strength The strength of the effect

        @return The newly created boon if it was created, otherwise null
     */
    public static Boon addBoonToEntity(BoonType boonType, Entity targetEntity, List<Boon> boonsOnEntity, float strength)
    {
        Boon newBoon = getBoonFromType(boonType, targetEntity);

        boolean boonShouldBeAdded = true;
        for(Boon boon : boonsOnEntity)
        {
            if(boon.getClass() == newBoon.getClass())
            {
                boon.onReapply(strength);

                boonShouldBeAdded = false;

            }
        }

        //If the boon should be added to the entity
        if(boonShouldBeAdded)
        {
            newBoon.onApply(targetEntity, strength);

            return newBoon;
        }
        else
        {
            return null;
        }
    }

    public static Boon getBoonFromType(BoonType type, Entity targetEntity)
    {
        switch (type)
        {
            case BLEEDING:
            {
                return new BleedingBoon();
            }
            case BLINK:
            {
                return new BlinkBoon();
            }
        }

        return null;
    }

}
