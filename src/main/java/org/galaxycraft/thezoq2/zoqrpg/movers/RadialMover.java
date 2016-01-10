package org.galaxycraft.thezoq2.zoqrpg.movers;

import org.bukkit.util.Vector;
import org.galaxycraft.thezoq2.zoqrpg.fileio.NumberValue;
import org.galaxycraft.thezoq2.zoqrpg.fileio.StructValue;
import org.galaxycraft.thezoq2.zoqrpg.utils.SpeedUtils;

/**
 * Mover that moves in a spiral around a center point that can move around
 */
public class RadialMover extends BaseMover
{
    //Default values
    private static final float EXPANSION_RATE = 0.5f; //The amount of expansion to the radius in seconds
    private static final float REVOLUTION_TIME = 1; //The amount of time in  one revolution will take
    private static final float STARTING_RADIUS = 1;
    private static final float DEFAULT_SPEED = 0;
    private static final float DEFAULT_Y_SPEED = 1;

    private float expansionRate = EXPANSION_RATE;
    private float revolutionTime = REVOLUTION_TIME;
    private float radius = STARTING_RADIUS;
    private float ySpeed = DEFAULT_Y_SPEED;

    private float currentTime = 0;

    private Vector centerPos;

    private RadialMover(float expansionRate, float revolutionTime, float radius, float ySpeed, float speed)
    {
        super(speed);
        this.revolutionTime = revolutionTime;
        this.expansionRate = expansionRate;
        this.ySpeed = ySpeed;
        this.radius = radius;

        init();
    }
    public RadialMover(StructValue sv)
    {
        super(DEFAULT_SPEED);

        expansionRate = (float) readValueWithFallback(sv, "expansionSpeed", new NumberValue(EXPANSION_RATE), NumberValue.class).getValue();
        revolutionTime = (float) readValueWithFallback(sv, "revolutionTime", new NumberValue(REVOLUTION_TIME), NumberValue.class).getValue();
        radius = (float) readValueWithFallback(sv, "startingRadius", new NumberValue(radius), NumberValue.class).getValue();
        ySpeed = (float) readValueWithFallback(sv, "heightSpeed", new NumberValue(DEFAULT_Y_SPEED), NumberValue.class).getValue();

        setSpeed((float) readValueWithFallback(sv, "speed", new NumberValue(EXPANSION_RATE), NumberValue.class).getValue());

        init();
    }

    private void init()
    {
        centerPos = new Vector(0,0,0);
    }

    @Override
    public void update(long timePassed)
    {
        assert (direction != null);

        currentTime += timePassed / 1000.0f;

        radius += SpeedUtils.getCurrentSpeed(expansionRate, timePassed);

        //Calculating the added location on the x-axis
        //Revolution time calculation is not pretty. I just tried stuff until it works
        float offsetX = (float) (Math.cos(currentTime / revolutionTime * 2 * Math.PI) * radius);
        float offsetZ = (float) (Math.sin(currentTime / revolutionTime * 2 * Math.PI) * radius);

        Vector addedPos = new Vector(offsetX, 0, offsetZ);

        //Calculating the center position of the rotation
        centerPos.add(SpeedUtils.getCurrentMovementVector(direction, speed, timePassed));
        centerPos.add(new Vector(0, SpeedUtils.getCurrentSpeed(ySpeed, timePassed), 0));

        position = centerPos.clone();
        position.add(addedPos);

    }

    @Override
    public BaseMover cloneObject()
    {
        return new RadialMover(expansionRate, revolutionTime, radius, ySpeed, speed);
    }
}
