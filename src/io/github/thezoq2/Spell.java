package io.github.thezoq2;

import org.bukkit.Location;
import org.bukkit.World;

/**
 * Created by frans on 08/02/15.
 */
public interface Spell
{
    void update();
    boolean isDone();

    void setPosition(Location pos);
    Location getPosition();

}
