package org.galaxycraft.thezoq2.zoqrpg.factories;

import org.galaxycraft.thezoq2.zoqrpg.boons.BlinkBoon;
import org.galaxycraft.thezoq2.zoqrpg.boons.Boon;
import org.galaxycraft.thezoq2.zoqrpg.boons.BurningBoon;
import org.galaxycraft.thezoq2.zoqrpg.exceptions.FactoryCreationFailedException;
import org.galaxycraft.thezoq2.zoqrpg.fileio.StructValue;

/**
 * Created by frans on 01/03/15.
 */
@SuppressWarnings("UnnecessaryCodeBlock")
//This warning comes from switch case statements.
//I prefer to keep code blocks in the code because they make it easier to tell where a new case begins and ends.
public final class BoonFactory extends StructBasedFactory<Boon>
{
    public BoonFactory(StructValue baseStruct)
    {
        super(baseStruct);
    }

    public Boon createBoon(String name) throws FactoryCreationFailedException
    {
        assert(name != null);

        StructValue sv = getStructByName(name);
        String baseName = getBaseValueFromStruct(sv);

        switch(baseName)
        {
            case "burning":
            {
                return new BurningBoon(sv);
            }
            case "blink":
            {
                return new BlinkBoon(sv);
            }
            default:
                throw new FactoryCreationFailedException("Base name: " + baseName + " is not a valid base boon");
        }
    }
}
