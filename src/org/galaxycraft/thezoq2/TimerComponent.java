package org.galaxycraft.thezoq2;


/**
 * A component that can be used by boons and spells to create a time effect
 */
public class TimerComponent
{
    private long startTime;
    private long duration;

    public TimerComponent(long duration)
    {
        startTime = System.currentTimeMillis();

        this.duration = duration;
    }

    public boolean isOver()
    {
        if(System.currentTimeMillis() - startTime > duration)
        {
            return true;
        }

        return false;
    }
}
