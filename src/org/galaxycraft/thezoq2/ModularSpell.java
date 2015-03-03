package org.galaxycraft.thezoq2;

import org.bukkit.World;
import org.bukkit.entity.Entity;
import org.bukkit.util.Vector;

import java.util.List;

/**
 * Created by frans on 03/03/15.
 */
public class ModularSpell extends BaseSpell
{
    private Mover mover;

    private Volume volume;

    private Boon appliedEffect;

    public ModularSpell(World world, Entity caster, Mover mover, Volume volume, Boon appliedEffect)
    {
        super(world, caster);

        this.mover = mover;
        this.volume = volume;
        this.appliedEffect = appliedEffect;
    }

    @Override
    public void onCreate()
    {

    }

    @Override
    public void update(float timePassed)
    {
        //Update the position of the spell
        mover.update(timePassed);
        volume.update(timePassed);

        volume.setCenter(mover.getPosition());

        //Getting a list of all the entities that will be affected by this spell
        List<Entity> affectedEntities = volume.getEntitiesInVolume(world.getEntities());


    }
}
