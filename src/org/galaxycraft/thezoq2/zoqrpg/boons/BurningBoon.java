package org.galaxycraft.thezoq2.zoqrpg.boons;

import org.bukkit.entity.Entity;
import org.bukkit.entity.LivingEntity;
import org.galaxycraft.thezoq2.zoqrpg.fileio.NumberValue;
import org.galaxycraft.thezoq2.zoqrpg.fileio.StructValue;
import org.galaxycraft.thezoq2.zoqrpg.utils.GeneralUtils;

/**
 *
 */
public class BurningBoon extends BaseBoon
{
    //TODO; make sure items don't burn. Done?
    //Constants that determine the way the boon behaves
    private static final long
            DEFAULT_MAX_BURN_TIME = 5000,
            DEFAULT_MIN_BURN_TIME = 1500;

    //Long because timer functions use long
    private long maxBurnTime = DEFAULT_MAX_BURN_TIME;
    private long minBurnTime = DEFAULT_MIN_BURN_TIME;

    private long burnTime;
    private int burnTicks;

    private BurningBoon(long minBurnTime, long maxBurnTime)
    {
        this.minBurnTime = minBurnTime;
        this.maxBurnTime = maxBurnTime;
    }

    public BurningBoon(StructValue sv)
    {
        //Attempt to read specific values from the struct
        minBurnTime = (long) readValueWithFallback(sv, "minBurnTime", new NumberValue(DEFAULT_MIN_BURN_TIME), NumberValue.class).getValue();
        maxBurnTime = (long) readValueWithFallback(sv, "maxBurnTime", new NumberValue(DEFAULT_MAX_BURN_TIME), NumberValue.class).getValue();
    }

    @Override
    public boolean onApply(Entity affectedEntity, float strength)
    {
        if(!super.onApply(affectedEntity, strength))
        {
            return false;
        }

        if(affectedEntity instanceof LivingEntity)
        {
            /*
            super.visualiser = new FireVisualiser();
            */

            //Calculating the time between burns
            burnTime = maxBurnTime - (long) (minBurnTime * strength);
            burnTicks = (int) GeneralUtils.getTicksFromMilliseconds(burnTime);

            affectedEntity.setFireTicks(burnTicks);
            return true;
        }
        return false;
    }

    @Override
    public void update(long timePassed)
    {
        //super.visualise();

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
        return new BurningBoon(this.maxBurnTime, this.minBurnTime);
    }
}
