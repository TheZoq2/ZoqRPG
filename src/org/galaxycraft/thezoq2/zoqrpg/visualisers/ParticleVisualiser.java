package org.galaxycraft.thezoq2.zoqrpg.visualisers;

import org.galaxycraft.thezoq2.zoqrpg.fileio.NumberValue;
import org.galaxycraft.thezoq2.zoqrpg.fileio.StructBasedObject;
import org.galaxycraft.thezoq2.zoqrpg.fileio.StructValue;

/**
 * Abstract class that contains a bunch of variables aswell as functions for setting those variables for displaying
 * particle effects using darkblade12s particle effect library.
 *
 * @see com.darkblade12.particleeffect
 */
public abstract class ParticleVisualiser extends StructBasedObject implements Visualiser
{
    //Default values
    private final static float OFFSET_X = 0.3f;
    private final static float OFFSET_Y = 0.3f;
    private final static float OFFSET_Z = 0.3f;
    private final static float SPEED = 0;
    private final static int PARTICLE_AMOUNT = 10;
    private final static int PARTICLE_RANGE = 100;

    protected float offsetX = OFFSET_X;
    protected float offsetY = OFFSET_Y;
    protected float offsetZ = OFFSET_Z;
    protected float speed = SPEED;
    protected int particleAmount = 10;
    protected int particleRange = 100;

    ParticleVisualiser(StructValue sv)
    {
        //Reading values from the struct
        this.offsetX = (float) readValueWithFallback(sv, "offsetX", new NumberValue(OFFSET_X), NumberValue.class).getValue();
        this.offsetY = (float) readValueWithFallback(sv, "offsetY", new NumberValue(OFFSET_Y), NumberValue.class).getValue();
        this.offsetZ = (float) readValueWithFallback(sv, "offsetZ", new NumberValue(OFFSET_Z), NumberValue.class).getValue();
        this.speed = (float) readValueWithFallback(sv, "speed", new NumberValue(OFFSET_Z), NumberValue.class).getValue();
        this.particleAmount = (int) readValueWithFallback(sv, "particleAmount", new NumberValue(PARTICLE_AMOUNT), NumberValue.class).getValue();
        this.particleRange = (int) readValueWithFallback(sv, "range", new NumberValue(PARTICLE_RANGE), NumberValue.class).getValue();
    }

    ParticleVisualiser(float offsetX, float offsetY, float offsetZ, float speed, int particleAmount, int particleRange)
    {
        this.offsetX = offsetX;
        this.offsetY = offsetY;
        this.offsetZ = offsetZ;
        this.speed = speed;
        this.particleAmount = particleAmount;
        this.particleRange = particleRange;
    }
    /*
    This is the intended behaviour, particle effects don't need anything cleaned up every tick but they still need an
    update function to follow the visualiser contract
     */
    @SuppressWarnings("NoopMethodInAbstractClass")
    @Override
    public void update()
    {

    }

    @Override
    public abstract ParticleVisualiser cloneObject();
}
