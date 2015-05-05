package org.galaxycraft.thezoq2.zoqrpg;

/**
 * Interface for an object that can persist for more than one server tick and needs to be updated during their lifetime
 * The update function is run every time a server tick is run. The isDone function should return true when the updatable
 * object has done everything it is supposed to and wants to stop being updated while the onEnd function is executed
 * before the object is removed in order to clean up anything left behind by the object.
 */
public interface Updatable
{
    void update(long timePassed);

    boolean isDone();

    void onEnd();
}
