package org.galaxycraft.thezoq2.zoqrpg.spells;

import org.bukkit.entity.Entity;
import org.galaxycraft.thezoq2.zoqrpg.BoonManager;
import org.galaxycraft.thezoq2.zoqrpg.boons.Boon;

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

    @Override
    public void onCreate(BoonManager boonManager)
    {
        super.onCreate(boonManager);

        //Cloning the boon and applying it to the caster
        Boon boon = appliedBoon.cloneBoon();

        boon.onApply(super.caster, boon.getStrength());

        boonManager.addBoon(boon);

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
