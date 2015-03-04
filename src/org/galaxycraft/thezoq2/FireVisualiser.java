package org.galaxycraft.thezoq2;

import com.darkblade12.particleeffect.ParticleEffect;
import org.bukkit.Location;

/**
 *
 */
public class FireVisualiser implements Visualiser
{
    private final static float OFFSET_X = 0.3f;
    private final static float OFFSET_Y = 0.3f;
    private final static float OFFSET_Z = 0.3f;
    private final static float SPEED = 0;
    private final static int PARTICLE_AMOUNT = 1;
    private final static int PARTICLE_RANGE = 100;

    @Override
    public void showLocation(Location loc)
    {
        ParticleEffect.FLAME.display(OFFSET_X, OFFSET_Y, OFFSET_Z, SPEED, PARTICLE_AMOUNT, loc, PARTICLE_RANGE);
    }
}
