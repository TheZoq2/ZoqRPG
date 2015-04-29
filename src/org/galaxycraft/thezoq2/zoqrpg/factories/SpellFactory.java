package org.galaxycraft.thezoq2.zoqrpg.factories;

import org.bukkit.entity.Entity;
import org.galaxycraft.thezoq2.zoqrpg.exceptions.FactoryCreationFailedException;
import org.galaxycraft.thezoq2.zoqrpg.exceptions.ModuleCreationFailedException;
import org.galaxycraft.thezoq2.zoqrpg.exceptions.NoSuchVariableException;
import org.galaxycraft.thezoq2.zoqrpg.exceptions.WrongDatatypeException;
import org.galaxycraft.thezoq2.zoqrpg.fileio.StructValue;
import org.galaxycraft.thezoq2.zoqrpg.spells.ModularSelfSpell;
import org.galaxycraft.thezoq2.zoqrpg.spells.ModularSpell;
import org.galaxycraft.thezoq2.zoqrpg.spells.Spell;

@SuppressWarnings({"UnnecessaryCodeBlock", "ThrowInsideCatchBlockWhichIgnoresCaughtException"})
/*
UnnessesaryCodeBlock:
    This warning comes from switch case statements where I have added {}.
    I prefer to keep code blocks in the code because they make it easier to tell where a new case begins and ends.
ThrowInsideCatchBlock...:
    Where this happens, I can still get the information I would need from the exception from the rest of the code.
    The factory class thows a more general exception.
*/
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
        assert(caster != null);

        StructValue sv = getStructByName(name);
        String baseName = getBaseValueFromStruct(sv);

        try
        {
            switch(baseName)
            {
                case "modular":
                {
                    return new ModularSpell(caster.getLocation(), caster, sfg, sv);
                }
                case "modularSelf":
                {
                    return new ModularSelfSpell(sv, caster, sfg);
                }
                default:
                {
                    throw new FactoryCreationFailedException("Base: " + baseName + " is not a valid spell base");
                }
            }
        }catch (NoSuchVariableException e)
        {
            throw new FactoryCreationFailedException("Module: " + e.getVarName() + " did not exist in "
                    + e.getStructPath() + " when creating basename");
        } catch (WrongDatatypeException e)
        {
            throw new FactoryCreationFailedException("Module name " + e.getVarPath() + " needs to be a string when creating " + baseName);
        } catch (ModuleCreationFailedException e)
        {
            //TODO: More precise error
            throw new FactoryCreationFailedException("Modular spell creation failed: " + e.getFactoryFailReason() + " when creating " + baseName);
        }
    }
}

