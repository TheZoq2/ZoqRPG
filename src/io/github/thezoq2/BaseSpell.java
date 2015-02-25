package io.github.thezoq2;

import net.minecraft.server.v1_8_R1.Position;
import org.bukkit.Location;

/**
 * Created by frans on 08/02/15.
 */
public abstract class BaseSpell implements Spell
{
    protected Location pos;
    protected boolean done;

    public BaseSpell(Location pos)
    {
        this.pos = pos;
    }

    @Override
    public Location getPosition()
    {
        return pos;
    }

    @Override
    public void setPosition(Location pos)
    {
        this.pos = pos;
    }

    @Override
    public boolean isDone()
    {
        return done;
    }
}
