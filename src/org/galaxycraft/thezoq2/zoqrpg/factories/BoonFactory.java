package org.galaxycraft.thezoq2.zoqrpg.factories;

import org.bukkit.entity.Entity;
import org.galaxycraft.thezoq2.zoqrpg.boons.BleedingBoon;
import org.galaxycraft.thezoq2.zoqrpg.boons.BlinkBoon;
import org.galaxycraft.thezoq2.zoqrpg.boons.Boon;
import org.galaxycraft.thezoq2.zoqrpg.fileio.StructValue;

import java.util.List;

/**
 * Created by frans on 01/03/15.
 */
public final class BoonFactory
{
    private StructValue boonStruct;

    public BoonFactory(StructValue boonStruct)
    {
        this.boonStruct = boonStruct;
    }

    public Boon createBoonByName(String name)
    {
        if(!boonStruct.variableExists(name))
        {

        }

        return null;
    }
}
