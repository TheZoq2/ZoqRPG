package org.galaxycraft.thezoq2;

import org.bukkit.Location;
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
    private Visualiser visualiser;
    private Boon appliedBoon;

    public ModularSpell(Location startPos, Entity caster, Mover mover, Volume volume, Boon appliedBoon, Visualiser visualiser)
    {
        super(startPos, caster);

        this.mover = mover;
        this.volume = volume;
        this.appliedBoon = appliedBoon;

        this.visualiser = visualiser;
    }

    @Override
    public void onCreate(BoonManager boonManager)
    {

    }

    @Override
    public void update(long timePassed)
    {
        //super.update(timePassed, boonManager);

        //Update the position of the spell
        mover.update(timePassed);
        volume.update(timePassed);

        volume.setCenter(mover.getPosition());

        //Getting a list of all the entities that will be affected by this spell
        List<Entity> affectedEntities = volume.getEntitiesInVolume(startPos.getWorld().getEntities());


        //Apply that effect to all the entities
        for(Entity e : affectedEntities)
        {
            //TODO Apply effects
        }

        //Visualising the spell
        for(Vector pos : volume.getBlocksInVolume())
        {
            Location currentPos = startPos.add(volume.getCenter());

            visualiser.showLocation(currentPos);
        }

    }

    @Override
    public void onEnd()
    {

    }
}
