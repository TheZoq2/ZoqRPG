package org.galaxycraft.thezoq2.zoqrpg;

import java.util.ArrayList;
import java.util.List;

/**
 * Keeps track of a specific kind of updatable object.
 *
 * Has functions for updating all the tracked objects aswell as adding new objects to be tracked.
 *
 * @see Updatable
 */
 public class UpdatableManager<T extends Updatable>
{
    protected List<T> activeUpdatables;

    UpdatableManager()
    {
        activeUpdatables = new ArrayList<>();
    }

    /**
     * Iterate over the list of updatables and execute their update function. Remove them if they should be removed
     * @param timePassed the time passed since the last update
     */
    public void updateAll(long timePassed)
    {
        List<T> doneUpdatables = new ArrayList<>();

        for(T updatable : activeUpdatables)
        {
            updatable.update(timePassed);

            if(updatable.isDone())
            {
                doneUpdatables.add(updatable);
            }
        }

        //Removing done updatables
        for(T updatable : doneUpdatables)
        {
            updatable.onEnd();

            activeUpdatables.remove(updatable);
        }
    }

    public void add(T updatable)
    {
        activeUpdatables.add(updatable);
    }

    //I won't fix this issue because the function name is clearer about what the function does to the outside
    //while the variable name is clearer inside the class
    public List<T> getUpdatableList()
    {
        return activeUpdatables;
    }
}
