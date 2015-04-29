package org.galaxycraft.thezoq2.zoqrpg.factories;

import org.galaxycraft.thezoq2.zoqrpg.exceptions.FactoryCreationFailedException;
import org.galaxycraft.thezoq2.zoqrpg.fileio.StructValue;
import org.galaxycraft.thezoq2.zoqrpg.visualisers.BlockVisualiser;
import org.galaxycraft.thezoq2.zoqrpg.visualisers.FireVisualiser;
import org.galaxycraft.thezoq2.zoqrpg.visualisers.Visualiser;

@SuppressWarnings("UnnecessaryCodeBlock")
//This warning comes from switch case statements where I have added {}.
//I prefer to keep code blocks in the code because they make it easier to tell where a new case begins and ends.
public class VisualiserFactory extends StructBasedFactory<Visualiser>
{

    public VisualiserFactory(StructValue baseStruct)
    {
        super(baseStruct);
    }

    public Visualiser createVisualiser(String name) throws FactoryCreationFailedException
    {
        StructValue sv = getStructByName(name);
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
            default:
                throw new FactoryCreationFailedException(baseName + " is not a valid base for a visualiser");
        }
    }
}
