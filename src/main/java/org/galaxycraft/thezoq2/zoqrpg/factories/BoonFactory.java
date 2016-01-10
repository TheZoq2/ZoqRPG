package org.galaxycraft.thezoq2.zoqrpg.factories;

import org.galaxycraft.thezoq2.zoqrpg.boons.BlinkBoon;
import org.galaxycraft.thezoq2.zoqrpg.boons.Boon;
import org.galaxycraft.thezoq2.zoqrpg.boons.BurningBoon;
import org.galaxycraft.thezoq2.zoqrpg.boons.PunchBoon;
import org.galaxycraft.thezoq2.zoqrpg.exceptions.FactoryCreationFailedException;
import org.galaxycraft.thezoq2.zoqrpg.exceptions.NoSuchTemplateObjectException;
import org.galaxycraft.thezoq2.zoqrpg.fileio.StructValue;

/**
 * Creates new Boon objects from StructValues. Extends the StructBasedFactory for common methods used to create objects
 * from structs.
 *
 * @see StructBasedFactory
 * @see StructValue
 */

//This warning comes from switch case statements.
//I prefer to keep code blocks in the code because they make it easier to tell where a new case begins and ends.
public final class BoonFactory extends StructBasedFactory<Boon>
{
    public BoonFactory(StructValue baseStruct)
    {
        super(baseStruct);
        createTemplateObjects();
    }

    /**
     * Create a new boon
     * @param name The name of the boon in the config files
     * @return The new boon.
     * @throws FactoryCreationFailedException If the factory failed to create a boon
     */
    public Boon createBoon(String name) throws FactoryCreationFailedException
    {
        assert(name != null);

        try
        {
            return createObject(name);
        } catch (NoSuchTemplateObjectException e)
        {
            //Warning about ignoring caught exception is wrong, e.getName is used. The FactoryCreationFailedException
            //is a more broad exception which means less split up try-catch statements.
            //This applies to all errors reporting "'throw' inside 'catch' block ignores the caught exception
            throw new FactoryCreationFailedException("Failed to create boon, no boon named " + e.getName());
        }

    }

    @Override
    protected Boon createObjectFromStruct(StructValue sv) throws FactoryCreationFailedException
    {
        String baseName = getBaseValueFromStruct(sv);

        //I prefer to leave braces around my case statements for increased readability.
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
            case "punch":
            {
                return new PunchBoon();
            }
            default:
                throw new FactoryCreationFailedException("Base name: " + baseName + " is not a valid base boon");
        }
    }
}
