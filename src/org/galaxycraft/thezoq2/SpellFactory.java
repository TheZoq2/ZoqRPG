package org.galaxycraft.thezoq2;

import org.bukkit.Location;

/**
 * Created by frans on 08/02/15.
 */
public class SpellFactory
{
    public static enum EffectType
    {
        WIRLWIND
    }

    public Spell createEffect(EffectType effect, float strength, Location pos)
    {
        switch(effect)
        {
            case WIRLWIND:
                return new SpellWirlwind(strength, pos);
        }

        return null;
    }
}
