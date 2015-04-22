package org.galaxycraft.thezoq2.zoqrpg.factories;

import org.bukkit.Location;
import org.bukkit.util.Vector;
import org.galaxycraft.thezoq2.zoqrpg.exceptions.FactoryCreationFailedException;
import org.galaxycraft.thezoq2.zoqrpg.fileio.StructValue;
import org.galaxycraft.thezoq2.zoqrpg.movers.LinearMover;
import org.galaxycraft.thezoq2.zoqrpg.movers.Mover;

/**
 * Created by frans on 14/04/15.
 */
public class MoverFactory extends StructBasedFactory
{
    public MoverFactory(StructValue moverStruct)
    {
        super(moverStruct);
    }

    public Mover create(String name, Vector startPos, Vector direction) throws FactoryCreationFailedException
    {
        StructValue sv = super.getStructByName(name);
        String baseName = super.getBaseValueFromStruct(sv);

        switch (baseName)
        {
            case "linear":
            {
                LinearMover lm = new LinearMover(sv, startPos, direction);

                return lm;
            }
            default:
            {
                throw new FactoryCreationFailedException(baseName + " is not a valid base for a mover");
            }
        }
    }
}
