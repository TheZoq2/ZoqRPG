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
public class MoverFactory extends StructBasedFactory<Mover>
{
    public MoverFactory(StructValue moverStruct)
    {
        super(moverStruct);

        createTemplateObjects();
    }


    public Mover create(String name, Vector direction) throws FactoryCreationFailedException
    {
        assert(direction != null);

        if(objectTemplates.containsKey(name))
        {
            Mover mover = objectTemplates.get(name).cloneObject();

            mover.setDirection(direction);
            return mover;
        }
        else
        {
            throw new FactoryCreationFailedException("Failed to create mover, no mover named " + name);
        }
    }
    public Mover create(String name) throws FactoryCreationFailedException
    {
        if(objectTemplates.containsKey(name))
        {
            Mover mover = objectTemplates.get(name).cloneObject();

            return mover;
        }
        else
        {
            throw new FactoryCreationFailedException("Failed to create mover, no mover named " + name);
        }
    }

    @Override
    protected Mover createObjectFromStruct(StructValue sv) throws FactoryCreationFailedException
    {
        String baseName = getBaseValueFromStruct(sv);

        switch (baseName)
        {
            case "linear":
            {
                LinearMover lm = new LinearMover(sv);

                return lm;
            }
            default:
            {
                throw new FactoryCreationFailedException(baseName + " is not a valid base for a mover");
            }
        }
    }
}
