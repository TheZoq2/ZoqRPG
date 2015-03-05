package org.galaxycraft.thezoq2.zoqrpg;

import org.galaxycraft.thezoq2.zoqrpg.boons.Boon;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by frans on 3/4/15.
 */
public class BoonManager extends UpdatableManager
{
    public void addBoon(Boon boon)
    {
        activeUpdatables.add(boon);
    }

    public List<Boon> getBoonList()
    {
        List<Boon> result = new ArrayList<>();

        for(Updatable updatable : activeUpdatables)
        {
            result.add((Boon) updatable);
        }

        return result;
    }
}
