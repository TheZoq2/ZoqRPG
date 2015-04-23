package org.galaxycraft.thezoq2.zoqrpg.factories;

import org.galaxycraft.thezoq2.zoqrpg.boons.Boon;
import org.galaxycraft.thezoq2.zoqrpg.boons.BurningBoon;
import org.galaxycraft.thezoq2.zoqrpg.exceptions.FactoryCreationFailedException;
import org.galaxycraft.thezoq2.zoqrpg.fileio.StructValue;
import org.galaxycraft.thezoq2.zoqrpg.movers.LinearMover;

/**
 * Created by frans on 01/03/15.
 */
public final class BoonFactory extends StructBasedFactory<Boon>
{
    public BoonFactory(StructValue baseStruct)
    {
        super(baseStruct);
    }

    public Boon createBoon(String name) throws FactoryCreationFailedException
    {
        StructValue sv = super.getStructByName(name);
        String baseName = super.getBaseValueFromStruct(sv);

        switch(baseName)
        {
            case "burning":
            {
                return new BurningBoon(sv);
            }
            default:
                //TODO: Make more specific?
                throw new FactoryCreationFailedException("Base name: " + baseName + " is not a valid base boon");
        }
    }
}
