package org.galaxycraft.thezoq2.zoqrpg.boons;

import org.bukkit.entity.Entity;
import org.galaxycraft.thezoq2.zoqrpg.utils.GeneralUtils;
import org.galaxycraft.thezoq2.zoqrpg.visualisers.FireVisualiser;
import org.galaxycraft.thezoq2.zoqrpg.visualisers.Visualiser;

/**
 * Created by frans on 3/4/15.
 */
public class BurningBoon extends BaseBoon
{
    //Constants that determine the way the boon behaves
    private static final long
            MAX_BURN_TIME = 2000,
            MIN_BURN_TIME = 500;

    long burnTime;
    int burnTicks;

    @Override
    public void onApply(Entity affectedEntity, float strength)
    {
        super.onApply(affectedEntity, strength);
        super.visualiser = new FireVisualiser();

        //Calculating the time between burns
        burnTime =  MAX_BURN_TIME - (long)(MIN_BURN_TIME * strength);
        burnTicks = (int) GeneralUtils.getTicksFromMilliseconds(burnTime);

        affectedEntity.setFireTicks(burnTicks);
    }

    @Override
    public void update(long timePassed)
    {
        super.visualise();

        //Checking if the entity is still burning
        if(affectedEntity.getFireTicks() == 0)
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
