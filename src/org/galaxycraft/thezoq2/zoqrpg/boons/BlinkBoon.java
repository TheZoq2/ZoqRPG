package org.galaxycraft.thezoq2.zoqrpg.boons;

import com.darkblade12.particleeffect.ParticleEffect;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import java.util.HashSet;
import java.util.List;

/**
 * Created by frans on 01/03/15.
 */
public class BlinkBoon extends BaseBoon
{
    //The max distance the player can teleport with the boon
    //private static final int TELEPORT_DISTANCE = 10;
    private static final int BASE_DISTANCE = 7;
    private static final int STRENGTH_DISTANCE = 20;

    //Effect data
    private static final float PARTICLE_OFFSET = 0;
    private static final int PARTICLE_SPEED = 1;
    private static final int PARTICLE_AMOUNT = 50;
    private static final int PARTICLE_RANGE = 100;

    private static final float INDICATOR_OFFSET = 0.5f;
    private static final int INDICATOR_SPEED = 3;
    private static final int INDICATOR_AMOUNT = 25;

    private ItemStack itemInHand; //if the player does not have this item in their hand, the boon sould be removed
    private int teleportDistance;


    @Override
    public boolean onApply(Entity affectedEntity, float strength)
    {
        if(!super.onApply(affectedEntity, strength))
        {
            return false;
        }

        teleportDistance = (int)(BASE_DISTANCE + strength * STRENGTH_DISTANCE);

        if(affectedEntity instanceof Player)
        {
            //Cast the entity to player
            Player plr = (Player) affectedEntity;

            itemInHand = plr.getItemInHand();

        }
        else
        {
            done = true;
        }

        return false;
    }

    @Override
    public void update(long timePassed)
    {
        if(affectedEntity instanceof Player)
        {
            Player plr = (Player) affectedEntity;

            //If the player cancels the teleport
            if(!itemInHand.equals(plr.getItemInHand()))
            {
                done = true;
                return;
            }

            //The player still wants to perform the teleport

            //Add some fancy particles
            Location loc = plr.getLocation();
            ParticleEffect.PORTAL.display(PARTICLE_OFFSET, PARTICLE_OFFSET, PARTICLE_OFFSET, PARTICLE_SPEED, PARTICLE_AMOUNT, loc, PARTICLE_RANGE);


            Location targetPos = getTeleportTargetPos();
            if(targetPos != null)
            {
                //Creating an indcator particle for where the player ends up if they teleport
                ParticleEffect.REDSTONE.display(INDICATOR_OFFSET, INDICATOR_OFFSET, INDICATOR_OFFSET, INDICATOR_SPEED, INDICATOR_AMOUNT, targetPos, plr);
            }
        }
    }

    @Override
    public void onEnd()
    {

    }

    @Override
    public void onReapply(float strength)
    {

    }

    private Location getTeleportTargetPos()
    {
        if(affectedEntity instanceof Player)
        {
            Player plr = (Player) affectedEntity;

            //Block targetBlock = plr.getTargetBlock((HashSet<Material>) null, TELEPORT_DISTANCE);
            //Uses depricated getLineOfSight because the non depricated getLineOfSight(Set<Material>, int) causes a
            //NoSuchMethodException

            //For some reason, the bukkit api does not contain a working replacement for this function
            //which is why it is deprecated
            List<Block> plrLineOfSight = plr.getLineOfSight((HashSet<Byte>) null, teleportDistance);

            Block targetBlock = null;
            for(Block block : plrLineOfSight)
            {
                if(block.getType() != Material.AIR)
                {
                    targetBlock = block;
                }
            }

            if(targetBlock == null)
            {
                return null;
            }

            //Checking if the block above this block is free
            Location blockLocation = targetBlock.getLocation();

            //Checking the block above
            Location newLocation = blockLocation.clone();
            newLocation.add(0, 1, 0);

            if (newLocation.getBlock().isEmpty())
            {
                return newLocation;
            }

            return blockLocation;
        }

        return null;
    }

    @Override
    public boolean onPlayerInterractEvent()
    {
        super.onPlayerInterractEvent();

        if(affectedEntity instanceof Player)
        {
            //Getting the target block
            Location targetLoc = getTeleportTargetPos();

            targetLoc.setDirection(affectedEntity.getLocation().getDirection());

            //If the player can be teleported
            if (targetLoc != null)
            {
                affectedEntity.teleport(targetLoc);

                done = true;

                //Tell the caller to cancel the event
                return false;
            }
        }

        return true;
    }

    @Override
    public Boon cloneBoon()
    {
        return new BlinkBoon();
    }

}
