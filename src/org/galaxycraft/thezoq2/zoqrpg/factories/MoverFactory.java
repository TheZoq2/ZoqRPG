package org.galaxycraft.thezoq2.zoqrpg.factories;

import org.bukkit.util.Vector;
import org.galaxycraft.thezoq2.zoqrpg.exceptions.FactoryCreationFailedException;
import org.galaxycraft.thezoq2.zoqrpg.exceptions.NoSuchTemplateObjectException;
import org.galaxycraft.thezoq2.zoqrpg.fileio.StructValue;
import org.galaxycraft.thezoq2.zoqrpg.movers.LinearMover;
import org.galaxycraft.thezoq2.zoqrpg.movers.Mover;
import org.galaxycraft.thezoq2.zoqrpg.movers.SinMover;

/**
 * Creates new Mover objects from StructValues. Extends the StructBasedFactory for common methods used to create objects
 * from structs.
 *
 * @see StructBasedFactory
 * @see StructValue
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

    public Mover createMover(String name, Vector direction) throws FactoryCreationFailedException
    {
        assert(direction != null);
        assert(name != null);

        try
        {
            Mover mover = createObject(name);

            mover.setDirection(direction);

            return mover;
        } catch (NoSuchTemplateObjectException e)
        {
            throw new FactoryCreationFailedException("Failed to create mover, no mover named " + e.getName());
        }
    }
    public Mover createMover(String name) throws FactoryCreationFailedException
    {
        assert(name != null);

        try
        {
            Mover mover = createObject(name);

            return mover;
        } catch (NoSuchTemplateObjectException e)
        {
            throw new FactoryCreationFailedException("Failed to create mover, no mover named " + e.getName());
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
                Mover mover = new LinearMover(sv);

                return mover;
            }
            case "wobbly":
            {
                Mover mover = new SinMover(sv);

                return mover;
            }
            default:
            {
                throw new FactoryCreationFailedException(baseName + " is not a valid base for a mover");
            }
        }
    }
}
