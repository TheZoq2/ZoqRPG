package org.galaxycraft.thezoq2.zoqrpg.factories;

import org.galaxycraft.thezoq2.zoqrpg.exceptions.FactoryCreationFailedException;
import org.galaxycraft.thezoq2.zoqrpg.fileio.StructValue;
import org.galaxycraft.thezoq2.zoqrpg.volumes.SphereVolume;
import org.galaxycraft.thezoq2.zoqrpg.volumes.Volume;

/**
 * Created by frans on 14/04/15.
 */
@SuppressWarnings("UnnecessaryCodeBlock")
//This warning comes from switch case statements where I have added {}.
//I prefer to keep code blocks in the code because they make it easier to tell where a new case begins and ends.
public class VolumeFactory extends StructBasedFactory<Volume>
{

    public VolumeFactory(StructValue baseStruct)
    {
        super(baseStruct);
    }


    public Volume createVolume(String name) throws FactoryCreationFailedException
    {
        StructValue sv = getStructByName(name);
        String baseName = getBaseValueFromStruct(sv);

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

    //TODO: Implement
    @Override
    protected Volume createObjectFromStruct(StructValue sv) throws FactoryCreationFailedException
    {
        return null;
    }
}
