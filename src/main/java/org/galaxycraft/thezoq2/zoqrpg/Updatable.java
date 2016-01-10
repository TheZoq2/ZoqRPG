package org.galaxycraft.thezoq2.zoqrpg;

/**
 * Interface for an object that can persist for more than one server tick and needs to be updated during their lifetime
 * The update function is run every time a server tick is run. The isDone function should return true when the updatable
 * object has done everything it is supposed to and wants to stop being updated while the onEnd function is executed
 * before the object is removed in order to clean up anything left behind by the object.
 */
public interface Updatable
{
    /**
     * Function executed by an updatable manager to perform activities that should be performed periodically
     * @param timePassed the time passed since the last update function was called
     */
    void update(long timePassed);

    /**
     * Returns true if the object is done updating and should be removed from the manager
     * @return true if the object should be removed from the manager
     */
    boolean isDone();

    /**
     * Function executed before the object is removed that should be used to cleanup any leftovers created by the object
     */
    //This method isn't used by any current updatables but will be used for more advanced boons that create things
    //which need to be destroyed when the boon is removed.
    void onEnd();
}

