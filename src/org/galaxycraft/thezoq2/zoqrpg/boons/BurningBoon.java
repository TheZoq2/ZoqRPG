package org.galaxycraft.thezoq2.zoqrpg.boons;

import org.bukkit.Bukkit;
import org.bukkit.entity.Entity;
import org.bukkit.entity.LivingEntity;
import org.galaxycraft.thezoq2.zoqrpg.exceptions.NoSuchVariableException;
import org.galaxycraft.thezoq2.zoqrpg.exceptions.WrongDatatypeException; import org.galaxycraft.thezoq2.zoqrpg.fileio.StringValue;
import org.galaxycraft.thezoq2.zoqrpg.fileio.StructValue;
import org.galaxycraft.thezoq2.zoqrpg.utils.GeneralUtils;
import org.galaxycraft.thezoq2.zoqrpg.visualisers.FireVisualiser;

import java.util.logging.Level;

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
    private long maxBurnTime;
    private long minBurnTime;

    private long burnTime;
    private int burnTicks;

    private BurningBoon(long minBurnTime, long maxBurnTime)
    {
        this.minBurnTime = minBurnTime;
        this.maxBurnTime = maxBurnTime;
    }

    public BurningBoon(StructValue sv)
    {
        //Set defaults
        minBurnTime = DEFAULT_MIN_BURN_TIME;
        maxBurnTime = DEFAULT_MAX_BURN_TIME;

        //Attempt to read specific values from the struct
        try
        {
            minBurnTime = (long) sv.getVariableOfTypeByName("minBurnTime", StringValue.class).getValueAsFloat();
            maxBurnTime = (long) sv.getVariableOfTypeByName("maxBurnTime", StringValue.class).getValueAsFloat();
        } catch (WrongDatatypeException e)
        {
            Bukkit.getLogger().log(Level.WARNING, "Variable  " + e.getVarPath() + " is wrong type in burningBoon, " +
                    "falling back to default");
            e.printStackTrace();
        } catch (NoSuchVariableException e)
        {
            Bukkit.getLogger().log(Level.WARNING, "Variable  " + e.getVarName() + " is missing, in " +
                    e.getStructPath() + "falling back to default");
            e.printStackTrace();
        }
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
