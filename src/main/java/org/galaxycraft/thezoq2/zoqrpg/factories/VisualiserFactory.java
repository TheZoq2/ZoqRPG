package org.galaxycraft.thezoq2.zoqrpg.factories;

import org.galaxycraft.thezoq2.zoqrpg.exceptions.FactoryCreationFailedException;
import org.galaxycraft.thezoq2.zoqrpg.exceptions.NoSuchTemplateObjectException;
import org.galaxycraft.thezoq2.zoqrpg.fileio.StructValue;
import org.galaxycraft.thezoq2.zoqrpg.visualisers.AirVisualiser;
import org.galaxycraft.thezoq2.zoqrpg.visualisers.BlockVisualiser;
import org.galaxycraft.thezoq2.zoqrpg.visualisers.FireVisualiser;
import org.galaxycraft.thezoq2.zoqrpg.visualisers.Visualiser;


/**
 * Creates new Visualiser objects from StructValues. Extends the StructBasedFactory for common methods used to
 * create objects from structs.
 *
 * @see StructBasedFactory
 * @see StructValue
 */

//This warning comes from switch case statements where I have added {}.
//I prefer to keep code blocks in the code because they make it easier to tell where a new case begins and ends.
public class VisualiserFactory extends StructBasedFactory<Visualiser>
{
    public VisualiserFactory(StructValue baseStruct)
    {
        super(baseStruct);
        createTemplateObjects();
    }


    /**
     * Attempts to create a new visauliser with the specified name
     * @param name The name of the object to be created
     * @return the new object
     * @throws FactoryCreationFailedException if the creation fails
     */
    public Visualiser createVisualiser(String name) throws FactoryCreationFailedException
    {
        assert(name != null);

        try
        {
            return createObject(name);
        } catch (NoSuchTemplateObjectException e)
        {
            throw new FactoryCreationFailedException("Failed to create visualiser, no visualiser named " + e.getName());
        }
    }

    //TODO: Implement
    @Override
    protected Visualiser createObjectFromStruct(StructValue sv) throws FactoryCreationFailedException
    {
        String baseName = getBaseValueFromStruct(sv);

        switch(baseName)
        {
            case("fire"):
            {
                return new FireVisualiser(sv);
            }
            case("block"):
            {
                return new BlockVisualiser(sv);
            }
            case("air"):
            {
                return new AirVisualiser(sv);
            }
            default:
                throw new FactoryCreationFailedException(baseName + " is not a valid base for a visualiser");
        }
    }
}
