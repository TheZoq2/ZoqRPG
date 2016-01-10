package org.galaxycraft.thezoq2.zoqrpg.boons;

import org.bukkit.entity.Entity;
import org.bukkit.util.Vector;

/**
 * Created by frans on 06/05/15.
 */
public class PunchBoon extends BaseBoon
{
    private static final float PUNCH_HEIGHT = 0.5f;

    private float punchHeight = PUNCH_HEIGHT;

    public PunchBoon()
    {

    }

    @Override
    public boolean onApply(Entity affectedEntity, float strength)
    {
        super.onApply(affectedEntity, strength);

        Vector currentVel = affectedEntity.getVelocity();

        Vector newVel = currentVel.clone();
        newVel.add(new Vector(0, strength * punchHeight, 0));

        affectedEntity.setVelocity(newVel);
        done = true;

        return true;
    }

    @Override
    public void onReapply(float strength)
    {

    }

    @Override
    public PunchBoon cloneObject()
    {
        return new PunchBoon();
    }

    @Override
    public void update(long timePassed)
    {

    }

    @Override
    public void onEnd()
    {

    }
}
