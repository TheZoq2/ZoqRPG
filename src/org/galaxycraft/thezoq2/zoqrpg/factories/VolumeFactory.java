package org.galaxycraft.thezoq2.zoqrpg.factories;

import org.galaxycraft.thezoq2.zoqrpg.exceptions.FactoryCreationFailedException;
import org.galaxycraft.thezoq2.zoqrpg.fileio.StructValue;
import org.galaxycraft.thezoq2.zoqrpg.volumes.SphereVolume;
import org.galaxycraft.thezoq2.zoqrpg.volumes.Volume;

/**
 * Created by frans on 14/04/15.
 */
public class VolumeFactory extends StructBasedFactory<Volume>
{

    public VolumeFactory(StructValue baseStruct)
    {
        super(baseStruct);
    }

    public Volume createVolume(String name) throws FactoryCreationFailedException
    {
        StructValue sv = super.getStructByName(name);
        String baseName = super.getBaseValueFromStruct(sv);

        switch(baseName)
        {
            case("sphere"):
            {
                return new SphereVolume(sv);
            }
            default:
                throw new FactoryCreationFailedException(baseName + " is not a valid volume base");
        }
    }
}
