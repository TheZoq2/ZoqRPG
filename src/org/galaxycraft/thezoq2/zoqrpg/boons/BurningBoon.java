package org.galaxycraft.thezoq2.zoqrpg.boons;

import org.bukkit.entity.Entity;
import org.bukkit.entity.LivingEntity;
import org.galaxycraft.thezoq2.zoqrpg.utils.GeneralUtils;
import org.galaxycraft.thezoq2.zoqrpg.visualisers.FireVisualiser;

/**
 *
 */
public class BurningBoon extends BaseBoon
{
    //TODO; make sure items don't burn
    //Constants that determine the way the boon behaves
    private static final long
            MAX_BURN_TIME = 5000,
            MIN_BURN_TIME = 1500;

    private long burnTime;
    private int burnTicks;

    @Override
    public boolean onApply(Entity affectedEntity, float strength)
    {
        if(!super.onApply(affectedEntity, strength))
        {
            return false;
        }

        if(affectedEntity instanceof LivingEntity)
        {
            super.visualiser = new FireVisualiser();

            //Calculating the time between burns
            burnTime = MAX_BURN_TIME - (long) (MIN_BURN_TIME * strength);
            burnTicks = (int) GeneralUtils.getTicksFromMilliseconds(burnTime);

            affectedEntity.setFireTicks(burnTicks);
            return true;
        }
        return false;
    }

    @Override
    public void update(long timePassed)
    {
        super.visualise();

        //Checking if the entity is still burning
        if(affectedEntity.getFireTicks() == -1)
        {
            done = true;
        }
    }

    @Override
    public void onEnd()
    {
    }

    @Override
    public void onReapply(float strength)
    {

    }

    @Override
    public Boon cloneBoon()
    {
        return new BurningBoon();
    }
}
