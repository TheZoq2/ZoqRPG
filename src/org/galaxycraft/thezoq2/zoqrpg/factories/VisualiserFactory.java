package org.galaxycraft.thezoq2.zoqrpg.factories;

import org.galaxycraft.thezoq2.zoqrpg.boons.BurningBoon;
import org.galaxycraft.thezoq2.zoqrpg.exceptions.FactoryCreationFailedException;
import org.galaxycraft.thezoq2.zoqrpg.fileio.StructValue;
import org.galaxycraft.thezoq2.zoqrpg.visualisers.FireVisualiser;
import org.galaxycraft.thezoq2.zoqrpg.visualisers.Visualiser;

public class VisualiserFactory extends StructBasedFactory<Visualiser>
{

    public VisualiserFactory(StructValue baseStruct)
    {
        super(baseStruct);
    }

    public Visualiser createVisualiser(String name) throws FactoryCreationFailedException
    {
        StructValue sv = super.getStructByName(name);
        String baseName = super.getBaseValueFromStruct(sv);

        switch(baseName)
        {
            case("fire"):
            {
                return new FireVisualiser(sv);
            }
            default:
                throw new FactoryCreationFailedException(baseName + " is not a valid base for a visualiser");
        }
    }
}
