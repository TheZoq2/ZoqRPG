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


    public ModularSpell(Mover mover, Volume volume, Boon appliedBoon, Visualiser visualiser)
    {
        super.setCaster(caster);
        super.setStartPos(startPos);

        createSpell(mover, volume, appliedBoon, visualiser);
    }

    public ModularSpell(Location startPos, Entity caster, SpellFactoryGroup sfg, StructValue spellStruct) throws ModuleCreationFailedException, WrongDatatypeException, NoSuchVariableException
    {
        super.setCaster(caster);
        super.setStartPos(startPos);

        createFromStructValue(spellStruct, sfg);
    }

    public ModularSpell(StructValue sv, SpellFactoryGroup sfg) throws NoSuchVariableException, ModuleCreationFailedException, WrongDatatypeException
    {
        createFromStructValue(sv, sfg);
    }

    private void createFromStructValue(StructValue spellStruct, SpellFactoryGroup sfg) throws WrongDatatypeException, NoSuchVariableException, ModuleCreationFailedException
    {
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
            this.mover = sfg.getMoverFactory().create(moverName);
            this.visualiser = sfg.getVisualiserFactory().createVisualiser(visualName);
            this.volume = sfg.getVolumeFactory().createVolume(volumeName);
        } catch (FactoryCreationFailedException e)
        {
            throw new ModuleCreationFailedException(e);
        }
    }

    @Override
    public void setCaster(Entity caster)
    {
        super.setCaster(caster);

        this.mover.setDirection(caster.getLocation().getDirection());
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
        visualiser.update();

        //Recalculating the center position
        Vector newCenter = startPos.toVector();
        newCenter.add(mover.getPosition());
        Vector currentPos = startPos.add(mover.getPosition()).toVector();
        //volume.setCenter(newCenter);

        //Getting a list of all the entities that will be affected by this spell
        List<Entity> affectedEntities = volume.getEntitiesInVolume(currentPos, startPos.getWorld().getEntities());

        //Apply that effect to all the entities
        for(Entity entity : affectedEntities)
        {
            //TODO check if caster is entity in a better way
            if(entity != caster)
            {
                Boon newBoon = appliedBoon.clone();

                newBoon.onApply(entity, 1); //1 is strength. TODO: Implement

                boonManager.add(newBoon);

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

    //TODO: Implement
    @Override
    public ModularSpell clone()
    {
        return new ModularSpell(this.mover.clone(), this.volume.clone(), this.appliedBoon.clone(), this.visualiser.clone());
    }
}
