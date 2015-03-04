package org.galaxycraft.thezoq2;

/**
 * Created by frans on 3/4/15.
 */
public class BurningBoon extends BaseBoon
{

    @Override
    public void update(long timePassed)
    {
    }

    @Override
    public void onEnd()
    {
    }

    @Override
    public void onReapply(float strength)
    {

    }

    @Override
    public Boon cloneBoon()
    {
        return new BurningBoon();
    }

}
