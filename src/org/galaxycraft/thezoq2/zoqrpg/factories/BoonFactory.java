package org.galaxycraft.thezoq2.zoqrpg.factories;

import org.galaxycraft.thezoq2.zoqrpg.boons.Boon;
import org.galaxycraft.thezoq2.zoqrpg.exceptions.FactoryCreationFailedException;
import org.galaxycraft.thezoq2.zoqrpg.fileio.StructValue;
import org.galaxycraft.thezoq2.zoqrpg.movers.LinearMover;

/**
 * Created by frans on 01/03/15.
 */
public final class BoonFactory extends StructBasedFactory<Boon>
{
    protected BoonFactory(StructValue baseStruct)
    {
        super(baseStruct);
    }

    @Override
    public Boon finalizeObject(StructValue sv, String baseName) throws FactoryCreationFailedException
    {
        switch(baseName)
        {
            case "linear":
            {
                return new LinearMover(1, )
            }
            default:
                //TODO: Make more specific?
                throw new FactoryCreationFailedException("Base name: " + baseName + " is not a valid mover base");
        }
    }
}
