package org.galaxycraft.thezoq2.zoqrpg.movers;

import org.bukkit.util.Vector;
import org.galaxycraft.thezoq2.zoqrpg.fileio.NumberValue;
import org.galaxycraft.thezoq2.zoqrpg.fileio.StructValue;
import org.galaxycraft.thezoq2.zoqrpg.utils.SpeedUtils;


public class LinearMover extends BaseMover
{
    private final static float DEFAULT_SPEED = 1;

    private Vector direction;

    public LinearMover(float speed, Vector direction)
    {
        super(speed);

        direction.normalize();
        this.direction = direction;
    }

    public LinearMover(StructValue sv, Vector startPos, Vector direction)
    {
        super(DEFAULT_SPEED);

        super.setSpeed((float)super.readValueWithFallback(sv, "speed", new NumberValue(DEFAULT_SPEED), NumberValue.class).getValue());

        direction.normalize();
        this.direction = direction;
    }

    @Override
    public void update(long timePassed)
    {
        Vector addVector = SpeedUtils.getCurrentMovementVector(this.direction, super.speed, timePassed);
        //Vector addVector = this.direction;
        super.position.add(addVector);
    }
}
