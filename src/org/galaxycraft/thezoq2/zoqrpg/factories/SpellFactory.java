package org.galaxycraft.thezoq2.zoqrpg.factories;

import org.bukkit.Bukkit;
import org.bukkit.entity.Entity;
import org.galaxycraft.thezoq2.zoqrpg.boons.Boon;
import org.galaxycraft.thezoq2.zoqrpg.exceptions.FactoryCreationFailedException;
import org.galaxycraft.thezoq2.zoqrpg.exceptions.ModuleCreationFailedException;
import org.galaxycraft.thezoq2.zoqrpg.exceptions.NoSuchVariableException;
import org.galaxycraft.thezoq2.zoqrpg.exceptions.WrongDatatypeException;
import org.galaxycraft.thezoq2.zoqrpg.fileio.StringValue;
import org.galaxycraft.thezoq2.zoqrpg.fileio.StructValue;
import org.galaxycraft.thezoq2.zoqrpg.movers.Mover;
import org.galaxycraft.thezoq2.zoqrpg.spells.ModularSpell;
import org.galaxycraft.thezoq2.zoqrpg.spells.Spell;
import org.galaxycraft.thezoq2.zoqrpg.visualisers.Visualiser;
import org.galaxycraft.thezoq2.zoqrpg.volumes.Volume;

import java.util.logging.Level;

public class SpellFactory extends StructBasedFactory
{

    private SpellFactoryGroup sfg;

    public SpellFactory(StructValue baseStruct, SpellFactoryGroup sfg)
    {
        super(baseStruct);
        this.sfg = sfg;
    }

    public Spell createSpell(String name, Entity caster) throws FactoryCreationFailedException
    {
        StructValue sv = super.getStructByName(name);
        String baseName = super.getBaseValueFromStruct(sv);

        switch(baseName)
        {
            case "modular":
            {
                try
                {
                    /*
                    //Read the modular parts
                    String visName = sv.getVariableOfTypeByName("visualiser", StringValue.class).getValue();
                    String volName = sv.getVariableOfTypeByName("volume", StringValue.class).getValue();
                    String boonName = sv.getVariableOfTypeByName("boon", StringValue.class).getValue();
                    String moverName = sv.getVariableOfTypeByName("mover", StringValue.class).getValue();

                    //Creating the modules
                    Mover mover = sfg.getMoverFactory().create(moverName, caster.getLocation().toVector(),
                                caster.getLocation().getDirection());
                    Volume volume = sfg.getVolumeFactory().createVolume(volName);
                    Boon boon = sfg.getBoonFactory().createBoon(boonName);
                    Visualiser visualiser = sfg.getVisualiserFactory().createVisualiser(visName);
                    */

                    return new ModularSpell(caster.getLocation(), caster, sfg, sv);
                } catch (NoSuchVariableException e)
                {
                    throw new FactoryCreationFailedException("Module: " + e.getVarName() + " did not exist in "
                            + e.getStructPath());
                } catch (WrongDatatypeException e)
                {
                    throw new FactoryCreationFailedException("Module name " + e.getVarPath() + " needs to be a string");
                } catch (ModuleCreationFailedException e)
                {
                    e.printStackTrace();
                }
            }
            default:
            {
                throw new FactoryCreationFailedException("Base: " + baseName + " is not a valid spell base");
            }
        }
    }

    /*
    @Override
    public Spell finalizeObject(StructValue sv, String baseName)
    {
        switch(baseName)
        {
            case "modular":
            {
                ModularSpell result;

                try
                {
                    //Getting the names of all the modules
                    String visName = sv.getVariableOfTypeByName("visualiser", StringValue.class).getValue();
                    String volName = sv.getVariableOfTypeByName("volume", StringValue.class).getValue();
                    String boonName = sv.getVariableOfTypeByName("boon", StringValue.class).getValue();
                    String moverName = sv.getVariableOfTypeByName("mover", StringValue.class).getValue();

                    //Attempting to create the modules

                } catch (NoSuchVariableException e)
                {
                    e.printStackTrace();
                } catch (WrongDatatypeException e)
                {
                    e.printStackTrace();
                }
            }

            default:
                Bukkit.getLogger().log(Level.WARNING, "Failed to create spell, base name: " + baseName + " is not" +
                        "a valid base");
                return null;
        }
    }
     */
}

/*public class SpellFactory extends StructBasedFactory
{
    private StructValue spellStruct;

    private SpellFactoryGroup sfg;

    public SpellFactory(StructValue spellStruct, SpellFactoryGroup sfg)
    {
        super(spellStruct);

        this.sfg = sfg;
    }

    Spell createSpell(String name, Entity caster, Location startPos)
    {

        DataValue spellValue = null;
        try
        {
            spellValue = spellStruct.getVariableByName(name);
        }
        catch (NoSuchVariableException e)
        {
            //Print an error
            StringBuilder errorMsg = new StringBuilder("No spell exists with that name in struct: ");
            errorMsg.append(e.getParentStruct().getFullPath());
            Bukkit.getLogger().log(Level.WARNING, errorMsg.toString());

            return null;
        }

        //Making sure the DataValue is a struct
        if(spellValue instanceof StructValue)
        {
            StructValue cSpellStruct = (StructValue) spellValue;

            String baseName = null;
            try
            {
                DataValue baseValue = cSpellStruct.getVariableByName("base");

                //TODO: Check if there is a better way of doing this... Current sollution "might" be ugly
                if(baseValue instanceof StringValue)
                {
                    baseName = ((StringValue) baseValue).getValue();
                }
                else
                {
                    //Don't throw exception, just print out the stack trace and return null.
                    //The method handles the exceptions istself but I still want the stack trace and fancy error report
                    //from the exception
                    new WrongDatatypeException(spellValue, "struct").printStackTrace();
                    return null;
                }
            }
            catch (NoSuchVariableException e)
            {
                //Spell creation failed, return null and report the error
                Bukkit.getLogger().log(Level.WARNING, "Failed to create spell, base variable does not exist in " + spellStruct.getFullPath());
                e.printStackTrace();
                return null;
            }

            //Base name has been found, start creating the spell
            return createSpellFromBaseName(baseName, spellStruct, caster, startPos);
        }
        else
        {
            //TODO: Report error in a nicer way
            //Don't throw exception, just print out the stack trace and return null.
            //The method handles the exceptions istself but I still want the stack trace and fancy error report
            //from the exception
            new WrongDatatypeException(spellValue, "struct").printStackTrace();
            return null;
        }
    }

    //TODO: Possibly move baseName lookup to separate function and get rid of the top
    private Spell createSpellFromBaseName(String name, StructValue cSpellStruct, Entity caster, Location startPos)
    {
        switch (name)
        {
            case "modular":
            {
                String boonName;
                String moverName;
                String visualiserName;
                String volumeName;

                try
                {
                    DataValue boonValue =
                    if(boonValue instanceof StringValue)
                    {

                    } cSpellStruct.getVariableByName("boon");
                    DataValue moverValue = cSpellStruct.getVariableByName("mover");
                    DataValue visualiserValue = cSpellStruct.getVariableByName("visualiser");
                    DataValue volumeValue = cSpellStruct.getVariableByName("volumeName");
                } catch (NoSuchVariableException e)
                {
                    e.printStackTrace();
                }

                break;
            }
            case "modularSelf":
            {

                break;
            }
            default:
                //Report error and return null
                Bukkit.getLogger().log(Level.WARNING, "Failed to create spell, base: " + name + " is not a valid base spell");
                return null;
        }
        //TODO: Fix
        return null;
    }
}*/
