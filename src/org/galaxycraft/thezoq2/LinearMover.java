package org.galaxycraft.thezoq2;

import org.bukkit.util.Vector;

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
        position.add(SpeedUtils.getCurrentMovementVector(this.direction, super.speed, timePassed));
    }
}
