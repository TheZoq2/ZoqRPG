package org.galaxycraft.thezoq2.zoqrpg.spells;

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

    //Create a spell from a struct value
    public ModularSelfSpell(StructValue sv, SpellFactoryGroup sfg) throws NoSuchVariableException, ModuleCreationFailedException, WrongDatatypeException
    {
        createFromStructValue(sv, sfg);
    }

    //Used by the clone method to create a copy of this spell
    private ModularSelfSpell(Boon appliedBoon)
    {
        this.appliedBoon = appliedBoon;
    }

    private void createFromStructValue(StructValue sv, SpellFactoryGroup sfg) throws WrongDatatypeException, NoSuchVariableException, ModuleCreationFailedException
    {
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
        Boon boon = appliedBoon.cloneObject();

        boon.onApply(caster, 1); //1 is strength //TODO: change

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

    //TODO: implement
    @Override
    public ModularSelfSpell cloneObject()
    {
        return new ModularSelfSpell(this.appliedBoon);
    }
}
