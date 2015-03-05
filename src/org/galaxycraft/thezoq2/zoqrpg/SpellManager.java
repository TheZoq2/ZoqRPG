package org.galaxycraft.thezoq2.zoqrpg;

import org.galaxycraft.thezoq2.zoqrpg.spells.Spell;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by frans on 3/4/15.
 */
public class SpellManager extends UpdatableManager
{
    public void addSpell(Spell spell)
    {
        activeUpdatables.add(spell);
    }

    public List<Spell> getSpellList()
    {
        List<Spell> result = new ArrayList<>();

        for(Updatable updatable : activeUpdatables)
        {
            result.add((Spell) updatable);
        }


        return result;
    }
}
