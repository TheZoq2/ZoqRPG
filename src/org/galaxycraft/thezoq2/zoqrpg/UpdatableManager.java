package org.galaxycraft.thezoq2.zoqrpg;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by frans on 3/4/15.
 */
public abstract class UpdatableManager<T extends Updatable>
{
    protected List<T> activeUpdatables;

    UpdatableManager()
    {
        activeUpdatables = new ArrayList<>();
    }

    public void updateAll(long timePassed)
    {
        List<Updatable> doneUpdatables = new ArrayList<>();

        for(Updatable updatable : activeUpdatables)
        {
            updatable.update(timePassed);

            if(updatable.isDone())
            {
                doneUpdatables.add(updatable);
            }
        }

        //Removing done updatables
        for(Updatable updatable : doneUpdatables)
        {
            updatable.onEnd();

            activeUpdatables.remove(updatable);
        }
    }

    public void add(T updatable)
    {
        activeUpdatables.add(updatable);
    }

    public List<T> getUpdatableList()
    {
        return activeUpdatables;
    }
}
