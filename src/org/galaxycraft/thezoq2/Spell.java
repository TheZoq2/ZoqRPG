package org.galaxycraft.thezoq2;

import org.bukkit.World;
import org.bukkit.entity.Entity;

/**
 * Created by frans on 03/03/15.
 */
public interface Spell
{
    public void onCreate();
    public void update(float timePassed);

    public boolean isDone();

    public Entity getCaster();
}
