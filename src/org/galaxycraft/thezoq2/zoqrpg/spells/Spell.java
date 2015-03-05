package org.galaxycraft.thezoq2.zoqrpg.spells;

import org.bukkit.entity.Entity;
import org.galaxycraft.thezoq2.zoqrpg.BoonManager;
import org.galaxycraft.thezoq2.zoqrpg.Updatable;

/**
 * Created by frans on 03/03/15.
 */
public interface Spell extends Updatable
{
    public void onCreate(BoonManager boonManager);

    public boolean isDone();

    public Entity getCaster();
}
