package org.galaxycraft.thezoq2.zoqrpg.movers;

import org.bukkit.util.Vector;
import org.galaxycraft.thezoq2.zoqrpg.utils.SpeedUtils;

/**
 * Created by frans on 3/4/15.
 */
public class LinearMover extends BaseMover
{
    private Vector direction;

    public LinearMover(float speed, Vector direction)
    {
        super(speed);

        direction.normalize();
        this.direction = direction;
    }

    @Override
    public void update(long timePassed)
    {
        Vector addVector = SpeedUtils.getCurrentMovementVector(this.direction, super.speed, timePassed);
        //Vector addVector = this.direction;
        super.position.add(addVector);

        int a = 0;
    }
}
