package org.galaxycraft.thezoq2.zoqrpg.factories;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.entity.Entity;
import org.galaxycraft.thezoq2.zoqrpg.exceptions.NoSuchVariableException;
import org.galaxycraft.thezoq2.zoqrpg.exceptions.WrongDatatypeException;
import org.galaxycraft.thezoq2.zoqrpg.fileio.DataValue;
import org.galaxycraft.thezoq2.zoqrpg.fileio.StringValue;
import org.galaxycraft.thezoq2.zoqrpg.fileio.StructValue;
import org.galaxycraft.thezoq2.zoqrpg.spells.Spell;
import org.galaxycraft.thezoq2.zoqrpg.visualisers.Visualiser;

import java.security.InvalidParameterException;
import java.util.logging.Level;

public class SpellFactory extends StructBasedFactory
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
            spellValue = super.getVariableFromStruct(name);
        } catch (NoSuchVariableException e)
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
            spellStruct = (StructValue) spellValue;

            String baseName = null;
            try
            {
                DataValue baseValue = spellStruct.getVariableByName("base");

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
        return null;
    }

    //TODO: Possibly move baseName lookup to separate function and get rid of the top
    private Spell createSpellFromBaseName(String name, StructValue spellStruct, Entity caster, Location startPos)
    {
        switch (name)
        {
            case "modular":
            {


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
    }
}
