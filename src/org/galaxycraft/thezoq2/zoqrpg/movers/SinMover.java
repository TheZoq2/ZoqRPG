package org.galaxycraft.thezoq2.zoqrpg.movers;

import org.bukkit.util.Vector;
import org.galaxycraft.thezoq2.zoqrpg.fileio.NumberValue;
import org.galaxycraft.thezoq2.zoqrpg.fileio.StructValue;
import org.galaxycraft.thezoq2.zoqrpg.utils.SpeedUtils;

/**
 * A mover that moves in a straight line with an added sine wave on the y Axis to achieve a wave effect.
 *
 * @see Mover
 * @see BaseMover
 */
public class SinMover extends BaseMover
{
    private static final float DEFAULT_SPEED = 1;
    private static final float DEFAULT_PERIOD = 1; //The length of a single wave in seconds
    private static final float DEFAULT_AMPLITUDE = 2;
    private static final float DEFAULT_WAVE_ANGLE = 0;

    private float period = DEFAULT_PERIOD;
    private float amplitude = DEFAULT_AMPLITUDE;

    private float currentTime = 0;

    public SinMover(StructValue sv)
    {
        super(DEFAULT_SPEED);

        //Read stuff from the struct
        setSpeed((float) readValueWithFallback(sv, "speed", new NumberValue(DEFAULT_SPEED), NumberValue.class).getValue());
        this.period = ((float) readValueWithFallback(sv, "waveLength", new NumberValue(DEFAULT_PERIOD), NumberValue.class).getValue());
        this.amplitude = ((float) readValueWithFallback(sv, "waveHeight", new NumberValue(DEFAULT_AMPLITUDE), NumberValue.class).getValue());

        direction = new Vector(0,0,0);
    }
    private SinMover(float speed, float period, float amplitude)
    {
        super(speed);
        this.period = period;
        this.amplitude = amplitude;
    }

    @Override
    public void update(long timePassed)
    {
        currentTime += timePassed;

        float actualSpeed = SpeedUtils.getCurrentSpeed(speed, timePassed);

        float addY = amplitude * actualSpeed * (float) Math.cos(currentTime / (period * 1000) * 2 * Math.PI);

        Vector addDirection = SpeedUtils.getCurrentMovementVector(direction, speed, timePassed);

        addDirection = addDirection.add(new Vector(0, addY, 0));

        position = position.add(addDirection);
    }

    @Override
    public BaseMover cloneObject()
    {
        return new SinMover(this.speed, this.period, this.amplitude);
    }
}
