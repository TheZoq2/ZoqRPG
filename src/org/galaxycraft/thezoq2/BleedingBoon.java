package org.galaxycraft.thezoq2;

import com.darkblade12.particleeffect.ParticleEffect;
import org.bukkit.Location;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;

/**
 * Created by frans on 2/25/15.
 */
public class BleedingBoon extends BaseBoon
{
    //The minimum and maximum time that the boon will be active for
    //Unit is miliseconds
    private static final long MIN_TIME = 500;
    private static final long MAX_TIME = 2000;

    private static final float WALK_SPEED_MOD = 0.7f;

    private static final int PARTICLE_AMOUNT = 10;
    private static final float PARTICLE_SPEED = 1;
    private static final int PARTICLE_RANGE = 100;


    //The walk speed of the player before the boon was applied
    private float oldWalkSpeed;

    private TimerComponent boonTimer;

    @Override
    public void onApply(Entity entity, float strength)
    {
        super.onApply(entity, strength);

        //Calculating the time the boon will be applied
        long effectTime = Math.round(MIN_TIME + (MAX_TIME - MIN_TIME) * strength);

        //Creating a timer with that duration
        boonTimer = new TimerComponent(effectTime);

        //If the affected entity is a player, they should get slowed down
        if(affectedEntity instanceof Player)
        {
            oldWalkSpeed = ((Player) affectedEntity).getWalkSpeed();
            ((Player) affectedEntity).setWalkSpeed(WALK_SPEED_MOD);
        }

        System.out.println("Applyhing ");
    }

    @Override
    public void update(long timePassed)
    {
        if(boonTimer.isOver())
        {
            //The boon is done playing, let it be removed
            done = true;
        }

        //Creating a particle effect on the affected entity
        Location entityPos = affectedEntity.getLocation();

        ParticleEffect.REDSTONE.display(1.0f, 1.0f, 1.0f, PARTICLE_SPEED, PARTICLE_AMOUNT, entityPos, PARTICLE_RANGE);
    }

    @Override
    public void onEnd()
    {
        if(affectedEntity instanceof Player)
        {
            ((Player) affectedEntity).setWalkSpeed(oldWalkSpeed);
        }
    }

    @Override
    public void onReapply(float strength)
    {
        //((Player) affectedEntity).sendMessage("Reapplied");
        System.out.println("Reapplying effect");
    }

    //TODO: Implement
    @Override
    public BleedingBoon cloneBoon()
    {
        return new BleedingBoon();
    }
}
