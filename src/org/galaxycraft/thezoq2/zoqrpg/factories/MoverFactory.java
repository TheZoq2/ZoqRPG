package org.galaxycraft.thezoq2.zoqrpg.factories;

import org.bukkit.util.Vector;
import org.galaxycraft.thezoq2.zoqrpg.exceptions.FactoryCreationFailedException;
import org.galaxycraft.thezoq2.zoqrpg.fileio.StructValue;
import org.galaxycraft.thezoq2.zoqrpg.movers.LinearMover;
import org.galaxycraft.thezoq2.zoqrpg.movers.Mover;

/**
 * Created by frans on 14/04/15.
 */
@SuppressWarnings("UnnecessaryCodeBlock")
//This warning comes from switch case statements where I have added {}.
//I prefer to keep code blocks in the code because they make it easier to tell where a new case begins and ends.
public class MoverFactory extends StructBasedFactory
{
    public MoverFactory(StructValue moverStruct)
    {
        super(moverStruct);
    }

    public Mover create(String name, Vector startPos, Vector direction) throws FactoryCreationFailedException
    {
        assert(startPos != null);
        assert(direction != null);

        StructValue sv = getStructByName(name);
        String baseName = getBaseValueFromStruct(sv);

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
