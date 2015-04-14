package org.galaxycraft.thezoq2.zoqrpg;

/**
 * Created by frans on 3/4/15.
 */
public interface Updatable
{
    void update(long timePassed);

    boolean isDone();

    void onEnd();
}
