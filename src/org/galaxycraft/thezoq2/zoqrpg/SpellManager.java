package org.galaxycraft.thezoq2.zoqrpg;

import org.galaxycraft.thezoq2.zoqrpg.spells.Spell;

/**
 * Created by frans on 3/4/15.
 */
public class SpellManager extends UpdatableManager
{
    public void addSpell(Spell spell)
    {
        activeUpdatables.add(spell);
    }

}
