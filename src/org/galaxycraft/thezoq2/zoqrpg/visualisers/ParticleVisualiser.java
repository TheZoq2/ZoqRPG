package org.galaxycraft.thezoq2.zoqrpg.visualisers;

import org.galaxycraft.thezoq2.zoqrpg.fileio.StructBasedObject;

/**
 * Created by frans on 25/04/15.
 */
public abstract class ParticleVisualiser extends StructBasedObject implements Visualiser
{
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
    public abstract ParticleVisualiser clone();
}
