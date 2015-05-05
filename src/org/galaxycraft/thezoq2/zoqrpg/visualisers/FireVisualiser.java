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

    public FireVisualiser(StructValue sv)
    {
        super(sv);
    }

    private FireVisualiser(float offsetX, float offsetY, float offsetZ, float speed, int particleAmount, int particleRange)
    {
        super(offsetX, offsetY, offsetZ, speed, particleAmount, particleRange);
    }

    @Override
    public void showLocation(Location loc)
    {
        ParticleEffect.FLAME.display(offsetX, offsetY, offsetZ, speed, particleAmount, loc, particleRange);
    }

    @Override
    public FireVisualiser cloneObject()
    {
        return new FireVisualiser(offsetX, offsetY, offsetZ, speed, particleAmount, particleRange);
    }
}
