package org.galaxycraft.thezoq2.zoqrpg;

import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.FallingBlock;
import org.bukkit.entity.WitherSkull;
import org.bukkit.util.Vector;

/**
 * Created by frans on 25/04/15.
 */
public class FlyingBlock
{
    private Material material;

    private WitherSkull vehicle; //Witherskulls can be used to make a stationary flying object if they are made invisible
    private FallingBlock block;

    private Location loc;

    public FlyingBlock(Location loc, Material material)
    {
        this.loc = loc;
        this.material = material;
        //http://www.spigotmc.org/threads/spawn-falling-block-with-data.42467/ For reasoning behind using deprecated method
        block = loc.getWorld().spawnFallingBlock(loc, material.getId(), (byte) 0);

        vehicle = (WitherSkull) loc.getWorld().spawnEntity(loc, EntityType.WITHER_SKULL);
        vehicle.setPassenger(block);
        vehicle.setVelocity(new Vector(0,0,0)); //Make prevent the skull from moving
    }

    public void remove()
    {
        block.remove();
        vehicle.remove();
    }
}
