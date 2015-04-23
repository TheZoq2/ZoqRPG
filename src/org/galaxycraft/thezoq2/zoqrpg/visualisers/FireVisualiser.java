package org.galaxycraft.thezoq2.zoqrpg.visualisers;

import com.darkblade12.particleeffect.ParticleEffect;
import org.bukkit.Location;
import org.galaxycraft.thezoq2.zoqrpg.exceptions.NoSuchVariableException;
import org.galaxycraft.thezoq2.zoqrpg.exceptions.WrongDatatypeException;
import org.galaxycraft.thezoq2.zoqrpg.fileio.StringValue;
import org.galaxycraft.thezoq2.zoqrpg.fileio.StructValue;

/**
 *
 */
public class FireVisualiser implements Visualiser
{
    //Default values
    private final static float OFFSET_X = 0.3f;
    private final static float OFFSET_Y = 0.3f;
    private final static float OFFSET_Z = 0.3f;
    private final static float SPEED = 0;
    private final static int PARTICLE_AMOUNT = 10;
    private final static int PARTICLE_RANGE = 100;

    private float offsetX = OFFSET_X;
    private float offsetY = OFFSET_Y;
    private float offsetZ = OFFSET_Z;
    private float speed = SPEED;
    private int particleAmount = 10;
    private int particleRange = 100;

    public FireVisualiser(StructValue sv)
    {
        //Reading values from the struct
        try
        {
            this.offsetX = sv.getVariableOfTypeByName("offsetX", StringValue.class).getValueAsFloat();
            this.offsetY = sv.getVariableOfTypeByName("offsetY", StringValue.class).getValueAsFloat();
            this.offsetZ = sv.getVariableOfTypeByName("offsetZ", StringValue.class).getValueAsFloat();
            this.speed = sv.getVariableOfTypeByName("speed", StringValue.class).getValueAsFloat();
            this.particleAmount = (int) sv.getVariableOfTypeByName("particleAmount", StringValue.class).getValueAsFloat();
            this.particleRange = (int) sv.getVariableOfTypeByName("range", StringValue.class).getValueAsFloat();
        } catch (WrongDatatypeException e)
        {
            //TODO: Report error propperly
            e.printStackTrace();
        } catch (NoSuchVariableException e)
        {
            //TODO: Report error propperly
            e.printStackTrace();
        }
    }

    @Override
    public void showLocation(Location loc)
    {
        ParticleEffect.FLAME.display(offsetX, offsetY, offsetZ, speed, particleAmount, loc, particleRange);
    }
}
