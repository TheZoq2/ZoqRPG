package org.galaxycraft.thezoq2.zoqrpg.visualisers;

import com.darkblade12.particleeffect.ParticleEffect;
import org.bukkit.Location;
import org.galaxycraft.thezoq2.zoqrpg.fileio.NumberValue;
import org.galaxycraft.thezoq2.zoqrpg.fileio.StructValue;

/**
 *
 */
public class FireVisualiser extends ParticleVisualiser
{
    //Default values
    private final static float OFFSET_X = 0.3f;
    private final static float OFFSET_Y = 0.3f;
    private final static float OFFSET_Z = 0.3f;
    private final static float SPEED = 0;
    private final static int PARTICLE_AMOUNT = 10;
    private final static int PARTICLE_RANGE = 100;

    private float offsetX = OFFSET_X;
    private float offsetY = OFFSET_Y;
    private float offsetZ = OFFSET_Z;
    private float speed = SPEED;
    private int particleAmount = 10;
    private int particleRange = 100;

    public FireVisualiser(StructValue sv)
    {
        //Reading values from the struct
        this.offsetX = (float)super.readValueWithFallback(sv, "offsetX", new NumberValue(OFFSET_X), NumberValue.class).getValue();
        this.offsetY = (float)super.readValueWithFallback(sv, "offsetY", new NumberValue(OFFSET_Y), NumberValue.class).getValue();
        this.offsetZ = (float)super.readValueWithFallback(sv, "offsetZ", new NumberValue(OFFSET_Z), NumberValue.class).getValue();
        this.speed = (float)super.readValueWithFallback(sv, "speed", new NumberValue(OFFSET_Z), NumberValue.class).getValue();
        this.particleAmount = (int)super.readValueWithFallback(sv, "particleAmount", new NumberValue(PARTICLE_AMOUNT), NumberValue.class).getValue();
        this.particleRange = (int)super.readValueWithFallback(sv, "range", new NumberValue(PARTICLE_RANGE), NumberValue.class).getValue();
    }

    @Override
    public void showLocation(Location loc)
    {
        ParticleEffect.FLAME.display(offsetX, offsetY, offsetZ, speed, particleAmount, loc, particleRange);
    }
}
