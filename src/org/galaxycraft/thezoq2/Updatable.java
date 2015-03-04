package org.galaxycraft.thezoq2;

/**
 * Created by frans on 3/4/15.
 */
public interface Updatable
{
    public void update(long timePassed);

    public boolean isDone();

    public void onEnd();
}
