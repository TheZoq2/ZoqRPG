package org.galaxycraft.thezoq2.zoqrpg.spells;

import org.bukkit.Location;
import org.bukkit.entity.Entity;
import org.bukkit.util.Vector;
import org.galaxycraft.thezoq2.zoqrpg.BoonManager;
import org.galaxycraft.thezoq2.zoqrpg.boons.Boon;
import org.galaxycraft.thezoq2.zoqrpg.movers.Mover;
import org.galaxycraft.thezoq2.zoqrpg.visualisers.Visualiser;
import org.galaxycraft.thezoq2.zoqrpg.volumes.Volume;

import java.util.List;

/**
 *
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
    public void update(long timePassed)
    {
        //super.update(timePassed, boonManager);

        //Update the position of the spell
        mover.update(timePassed);
        volume.update(timePassed);

        //Recalculating the center position
        Vector newCenter = super.startPos.toVector();
        newCenter.add(mover.getPosition());
        volume.setCenter(newCenter);

        //Getting a list of all the entities that will be affected by this spell
        List<Entity> affectedEntities = volume.getEntitiesInVolume(startPos.getWorld().getEntities());

        //Apply that effect to all the entities
        for(Entity entity : affectedEntities)
        {
            //TODO check if caster is entity in a better way
            if(entity == super.caster)
            {
                continue;
            }

            Boon newBoon = appliedBoon.cloneBoon();

            newBoon.onApply(entity, appliedBoon.getStrength());

            super.boonManager.addBoon(newBoon);

            System.out.println("Added boon");
        }

        //Visualising the spell
        for(Vector pos : volume.getBlocksInVolume())
        {
            //Location currentPos = volume.getCenter().toLocation(startPos.getWorld());
            Location currentPos = pos.toLocation(startPos.getWorld());

            visualiser.showLocation(currentPos);
        }

    }

    @Override
    public void onEnd()
    {

    }
}
