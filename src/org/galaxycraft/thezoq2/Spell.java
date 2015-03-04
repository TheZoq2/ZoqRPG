package org.galaxycraft.thezoq2;

import org.bukkit.entity.Entity;

/**
 * Created by frans on 03/03/15.
 */
public interface Spell extends Updatable
{
    public void onCreate(BoonManager boonManager);

    public boolean isDone();

    public Entity getCaster();
}
