package io.github.thezoq2;

import org.bukkit.entity.Player;

/**
 * Created by frans on 2/25/15.
 */
public class BleedingBoon extends BaseBoon
{
    //The walk speed of the player before the boon
    private float oldWalkSpeed;

    public BleedingBoon(Player target)
    {
        super(target);

        oldWalkSpeed = target.getWalkSpeed();
    }

    @Override
    public void onApply()
    {
        affectedPlayer.setWalkSpeed(0.7f);
    }

    @Override
    public void update()
    {

    }

    @Override
    public void onEnd()
    {
        affectedPlayer.setWalkSpeed(oldWalkSpeed);
    }
}
