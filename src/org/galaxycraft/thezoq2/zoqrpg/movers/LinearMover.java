package org.galaxycraft.thezoq2.zoqrpg.movers;

import org.bukkit.Bukkit;
import org.bukkit.util.Vector;
import org.galaxycraft.thezoq2.zoqrpg.exceptions.FactoryCreationFailedException;
import org.galaxycraft.thezoq2.zoqrpg.exceptions.NoSuchVariableException;
import org.galaxycraft.thezoq2.zoqrpg.exceptions.WrongDatatypeException;
import org.galaxycraft.thezoq2.zoqrpg.fileio.StringValue;
import org.galaxycraft.thezoq2.zoqrpg.fileio.StructValue;
import org.galaxycraft.thezoq2.zoqrpg.utils.GlobalConfig;
import org.galaxycraft.thezoq2.zoqrpg.utils.SpeedUtils;

import java.util.logging.Level;


public class LinearMover extends BaseMover
{
    private static float DEFAULT_SPEED;

    private Vector direction;

    public LinearMover(float speed, Vector direction)
    {
        super(speed);

        direction.normalize();
        this.direction = direction;
    }

    public LinearMover(StructValue sv, Vector startPos, Vector direction)
    {
        super(DEFAULT_SPEED);

        //Getting the speed from the struct
        try
        {
            float speed = sv.getVariableOfTypeByName("speed", StringValue.class).getValueAsFloat();

        } catch (NoSuchVariableException e)
        {
            //TODO: Remove duplicate code
            Bukkit.getLogger().log(Level.WARNING, "Variable  " + e.getVarName() + " is missing, in burning boon" +
                    e.getStructPath() + "falling back to default");

            GlobalConfig.getInstance().printStackTraceForDefault(e);
        } catch (WrongDatatypeException e)
        {
            Bukkit.getLogger().log(Level.WARNING, "Variable  " + e.getVarPath() + " is wrong type in linear mover, " +
                    "falling back to default");

            GlobalConfig.getInstance().printStackTraceForDefault(e);
        }

        super.setSpeed(speed);
        direction.normalize();
        this.direction = direction;
    }

    @Override
    public void update(long timePassed)
    {
        Vector addVector = SpeedUtils.getCurrentMovementVector(this.direction, super.speed, timePassed);
        //Vector addVector = this.direction;
        super.position.add(addVector);
    }
}
