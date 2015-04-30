package org.galaxycraft.thezoq2.zoqrpg.movers;

import org.bukkit.util.Vector;
import org.galaxycraft.thezoq2.zoqrpg.fileio.NumberValue;
import org.galaxycraft.thezoq2.zoqrpg.fileio.StructValue;
import org.galaxycraft.thezoq2.zoqrpg.utils.SpeedUtils;


public class LinearMover extends BaseMover
{
    private final static float DEFAULT_SPEED = 1;

    public LinearMover(float speed)
    {
        super(speed);

        super.direction = new Vector(0,0,0);
    }

    public LinearMover(StructValue sv)
    {
        super(DEFAULT_SPEED);

        setSpeed((float) readValueWithFallback(sv, "speed", new NumberValue(DEFAULT_SPEED), NumberValue.class).getValue());

        super.direction = new Vector(0,0,0);
    }



    @Override
    public void update(long timePassed)
    {
        assert(direction != null); //If this assertion is triggered, it means that the mover did not recieve a direction
        //before being updated. This needs to be handled by the factory creating it

        Vector addVector = SpeedUtils.getCurrentMovementVector(this.direction, speed, timePassed);
        //Vector addVector = this.direction;
        position.add(addVector);
    }

    @Override
    public LinearMover cloneObject()
    {
        return new LinearMover(speed);
    }
}
