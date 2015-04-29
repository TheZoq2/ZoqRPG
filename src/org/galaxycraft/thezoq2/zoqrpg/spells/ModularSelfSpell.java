package org.galaxycraft.thezoq2.zoqrpg.spells;

import org.bukkit.entity.Entity;
import org.galaxycraft.thezoq2.zoqrpg.UpdatableManager;
import org.galaxycraft.thezoq2.zoqrpg.boons.Boon;
import org.galaxycraft.thezoq2.zoqrpg.exceptions.FactoryCreationFailedException;
import org.galaxycraft.thezoq2.zoqrpg.exceptions.ModuleCreationFailedException;
import org.galaxycraft.thezoq2.zoqrpg.exceptions.NoSuchVariableException;
import org.galaxycraft.thezoq2.zoqrpg.exceptions.WrongDatatypeException;
import org.galaxycraft.thezoq2.zoqrpg.factories.SpellFactoryGroup;
import org.galaxycraft.thezoq2.zoqrpg.fileio.StringValue;
import org.galaxycraft.thezoq2.zoqrpg.fileio.StructValue;

/**
 * Created by frans on 3/9/15.
 */
public class ModularSelfSpell extends BaseSpell
{
    private Boon appliedBoon;

    public ModularSelfSpell(Entity caster, Boon appliedBoon)
    {
        super(caster.getLocation(), caster);

        this.appliedBoon = appliedBoon;
    }

    public ModularSelfSpell(StructValue sv, Entity caster, SpellFactoryGroup sfg) throws WrongDatatypeException, NoSuchVariableException, ModuleCreationFailedException
    {
        super(caster.getLocation(), caster);

        String boonName = sv.getVariableOfTypeByName("boon", StringValue.class).getValue();

        try
        {
            this.appliedBoon = sfg.getBoonFactory().createBoon(boonName);
        } catch (FactoryCreationFailedException e)
        {
            throw new ModuleCreationFailedException(e);
        }
    }

    @Override
    public void onCreate(UpdatableManager<Boon> boonManager)
    {
        super.onCreate(boonManager);

        //Cloning the boon and applying it to the caster
        Boon boon = appliedBoon.cloneBoon();

        boon.onApply(caster, boon.getStrength());

        boonManager.add(boon);

        //Because the spell will only apply a boon to the caster, it will immidietly be done
        done = true;
    }

    @Override
    public void update(long timePassed)
    {
    }

    @Override
    public void onEnd()
    {
    }
}
