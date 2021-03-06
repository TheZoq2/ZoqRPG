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
 * A spell which behaviour is determined by 4 diffirent modules. Each modular spell contains one of each of Boon, Mover,
 * Visualiser and Volume which describes what it does.
 *
 * When the spell is cloned, so is the modules which means that the module factories are only required during the
 * creation of the template.
 *
 * @see Boon
 * @see Mover
 * @see Visualiser
 * @see Volume
 *
 */
public class ModularSpell extends BaseSpell
{
    private Mover mover;
    private Volume volume;
    private Visualiser visualiser;
    private Boon appliedBoon;


    private ModularSpell(Mover mover, Volume volume, Boon appliedBoon, Visualiser visualiser)
    {
        super.setCaster(caster);
        setStartPos(startPos);

        createSpell(mover, volume, appliedBoon, visualiser);
    }

    //This method is used in the spell factory. However, idea might not know that because it thinks that the functions
    //in RPG main won't be invoked since it doesn't know about bukkit
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
            this.mover = sfg.getMoverFactory().createMover(moverName);
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
            if(entity != caster) //Object compared using != instead of .equals: This is what I want to do
            {
                Boon newBoon = appliedBoon.cloneObject();

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

    @Override
    public ModularSpell cloneObject()
    {
        return new ModularSpell(this.mover.cloneObject(), this.volume.cloneObject(), this.appliedBoon.cloneObject(), this.visualiser.cloneObject());
    }
}
