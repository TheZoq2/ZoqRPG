package org.galaxycraft.thezoq2.zoqrpg.factories;

/**
 * Groups the factories needed to create spells together to make the passing of them faster
 */
public class SpellFactoryGroup
{
    private MoverFactory moverFactory;
    private BoonFactory boonFactory;
    private VisualiserFactory visualiserFactory;
    private VolumeFactory volumeFactory;

    public MoverFactory getMoverFactory()
    {
        return moverFactory;
    }

    public BoonFactory getBoonFactory()
    {
        return boonFactory;
    }

    public VisualiserFactory getVisualiserFactory()
    {
        return visualiserFactory;
    }

    public VolumeFactory getVolumeFactory()
    {
        return volumeFactory;
    }

    public SpellFactoryGroup(MoverFactory moverFactory, BoonFactory boonFactory, VisualiserFactory visualiserFactory, VolumeFactory volumeFactory)
    {
        this.moverFactory = moverFactory;
        this.boonFactory = boonFactory;
        this.visualiserFactory = visualiserFactory;
        this.volumeFactory = volumeFactory;
    }
}
