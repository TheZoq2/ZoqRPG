package org.galaxycraft.thezoq2.zoqrpg.visualisers;

import com.darkblade12.particleeffect.ParticleEffect;
import org.bukkit.Location;
import org.galaxycraft.thezoq2.zoqrpg.fileio.StructValue;

/**
 * Currently unused visualiser that looks like a cloud. All the heavy lifiting is done by the
 * ParticleVisualiser class
 *
 * @see ParticleVisualiser
 *
 */
public class AirVisualiser extends ParticleVisualiser
{
    AirVisualiser(float offsetX, float offsetY, float offsetZ, float speed, int particleAmount, int particleRange)
    {
        super(offsetX, offsetY, offsetZ, speed, particleAmount, particleRange);
    }

    AirVisualiser(StructValue sv)
    {
        super(sv);
    }

    @Override
    public void showLocation(Location loc)
    {
        ParticleEffect.EXPLOSION_NORMAL.display(offsetX, offsetY, offsetZ, speed, particleAmount, loc, particleRange);
    }

    @Override
    public ParticleVisualiser cloneObject()
    {
        return new AirVisualiser(offsetX, offsetY, offsetZ, speed, particleAmount, particleRange);
    }
}
