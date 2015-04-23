package org.galaxycraft.thezoq2.zoqrpg.spells;

import org.bukkit.Location;
import org.bukkit.entity.Entity;
import org.bukkit.util.Vector;
import org.galaxycraft.thezoq2.zoqrpg.boons.Boon;
import org.galaxycraft.thezoq2.zoqrpg.exceptions.FactoryCreationFailedException;
import org.galaxycraft.thezoq2.zoqrpg.exceptions.ModuleCreationFailedException;
import org.galaxycraft.thezoq2.zoqrpg.exceptions.NoSuchVariableException;
import org.galaxycraft.thezoq2.zoqrpg.exceptions.WrongDatatypeException;
import org.galaxycraft.thezoq2.zoqrpg.factories.SpellFactoryGroup;
import org.galaxycraft.thezoq2.zoqrpg.fileio.DataValue;
import org.galaxycraft.thezoq2.zoqrpg.fileio.StringValue;
import org.galaxycraft.thezoq2.zoqrpg.fileio.StructValue;
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

        createSpell(mover, volume, appliedBoon, visualiser);
    }

    public ModularSpell(Location startPos, Entity caster, SpellFactoryGroup sfg, StructValue spellStruct) throws ModuleCreationFailedException, WrongDatatypeException, NoSuchVariableException
    {
        super(startPos, caster);

        //Finding the data from the struct
        //This will throw no such variable exceptions that can be caught by the factory later and terminate the
        //creation of the spell
        DataValue boonVar = spellStruct.getVariableByName("boon");
        DataValue visualVar = spellStruct.getVariableByName("visualiser");
        DataValue moverVar = spellStruct.getVariableByName("mover");
        DataValue volumeVar = spellStruct.getVariableByName("volume");

        //TODO: Possibly remove duplicate code
        //Get the data from the variables
        //Throws wrong datatype exceptions which do the same thing as the variable lookup
        String boonName;
        String visualName;
        String moverName;
        String volumeName;

        boonName = spellStruct.getVariableOfTypeByName("boon", StringValue.class).getValue();
        visualName = spellStruct.getVariableOfTypeByName("visualiser", StringValue.class).getValue();
        moverName = spellStruct.getVariableOfTypeByName("mover", StringValue.class).getValue();
        volumeName = spellStruct.getVariableOfTypeByName("volume", StringValue.class).getValue();

        //Creating the parameters
        try
        {
            this.appliedBoon = sfg.getBoonFactory().createBoon(boonName);
            this.mover = sfg.getMoverFactory().create(moverName, caster.getLocation().toVector(), caster.getLocation().getDirection());
            this.visualiser = sfg.getVisualiserFactory().createVisualiser(visualName);
            this.volume = sfg.getVolumeFactory().createVolume(volumeName);
        } catch (FactoryCreationFailedException e)
        {
            throw new ModuleCreationFailedException(e);
        }
    }

    private void createSpell(Mover mover, Volume volume, Boon appliedBoon, Visualiser visualiser)
    {
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
        Vector currentPos = startPos.add(mover.getPosition()).toVector();
        //volume.setCenter(newCenter);

        //Getting a list of all the entities that will be affected by this spell
        List<Entity> affectedEntities = volume.getEntitiesInVolume(currentPos, startPos.getWorld().getEntities());

        //Apply that effect to all the entities
        for(Entity entity : affectedEntities)
        {
            //TODO check if caster is entity in a better way
            if(entity != super.caster)
            {
                Boon newBoon = appliedBoon.cloneBoon();

                newBoon.onApply(entity, appliedBoon.getStrength());

                super.boonManager.addBoon(newBoon);

                System.out.println("Added boon");
            }
        }

        //Visualising the spell
        for(Vector pos : volume.getBlocksInVolume(currentPos))
        {
            //Location currentPos = volume.getCenter().toLocation(startPos.getWorld());
            Location cPos = pos.toLocation(startPos.getWorld());

            visualiser.showLocation(cPos);
        }

    }

    @Override
    public void onEnd()
    {

    }
}
