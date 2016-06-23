package org.galaxycraft.thezoq2.zoqrpg.visualisers;

import org.bukkit.Location;
import org.galaxycraft.thezoq2.zoqrpg.fileio.StructValue;

/**
 * Currently unused visualiser that looks like a cloud. All the heavy lifiting is done by the
 * ParticleVisualiser class
 *
 * @see ParticleVisualiser
 *
 */

//This visualiser hasn't been fully implemented yet and as such, is not used
public class AirVisualiser extends ParticleVisualiser
{
    private AirVisualiser(float offsetX, float offsetY, float offsetZ, float speed, int particleAmount, int particleRange)
    {
        super(offsetX, offsetY, offsetZ, speed, particleAmount, particleRange);
    }

    //This visualiser hasn't been fully implemented yet and as such, is not used
    public AirVisualiser(StructValue sv)
    {
        super(sv);
    }

    @Override
    public void showLocation(Location loc)
    {
        //ParticleEffect.EXPLOSION_NORMAL.display(offsetX, offsetY, offsetZ, speed, particleAmount, loc, particleRange);
        //TODO: Rimplement
    }

    @Override
    public ParticleVisualiser cloneObject()
    {
        return new AirVisualiser(offsetX, offsetY, offsetZ, speed, particleAmount, particleRange);
    }
}
