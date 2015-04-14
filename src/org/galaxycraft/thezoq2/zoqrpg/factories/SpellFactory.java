package org.galaxycraft.thezoq2.zoqrpg.factories;

import org.bukkit.Bukkit;
import org.galaxycraft.thezoq2.zoqrpg.exceptions.NoSuchVariableException;
import org.galaxycraft.thezoq2.zoqrpg.exceptions.WrongDatatypeException;
import org.galaxycraft.thezoq2.zoqrpg.fileio.DataValue;
import org.galaxycraft.thezoq2.zoqrpg.fileio.StructValue;
import org.galaxycraft.thezoq2.zoqrpg.spells.Spell;

import java.security.InvalidParameterException;
import java.util.logging.Level;

public class SpellFactory extends StructBasedFactory
{
    private StructValue spellStruct;

    public SpellFactory(StructValue spellStruct)
    {
        super(spellStruct);
    }

    Spell createSpell(String name) throws WrongDatatypeException
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

        }
        else
        {
            throw new WrongDatatypeException(name, "struct");
        }
        return null;
    }
}
