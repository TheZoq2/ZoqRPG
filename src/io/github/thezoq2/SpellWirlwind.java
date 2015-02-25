package io.github.thezoq2;

import com.darkblade12.particleeffect.ParticleEffect;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.entity.Entity;
import org.bukkit.util.Vector;

import java.util.List;

/**
 * Created by frans on 08/02/15.
 */
public class SpellWirlwind extends BaseAirSpell
{
    private double radius;
    private double currentRadius;

    private static double MAX_RADIUS = 15;
    private static double EXPANSION_SPEED = 0.3;
    private static double ACCELERATION = 1;
    private static double HEIGHT = 2;

    private float strength;

    public SpellWirlwind(double strength, Location pos)
    {
        super(pos);

        //Calculating the radius that this spell will influence
        this.radius = MAX_RADIUS * strength;
        this.currentRadius = 1;
    }
    @Override
    public void update()
    {
        //Fetching all entities that will be affected by the spell
        World world = pos.getWorld();

        List<Entity> affectedEntities = super.getAffectedEntities(world);

        for(Entity entity : affectedEntities)
        {
            //Checking if the entity is within the radius
            Location entityPos = entity.getLocation();
            double entityDistance = entityPos.distance(pos);

            if(entityDistance < currentRadius && entityDistance > currentRadius - EXPANSION_SPEED)
            {
                if(Math.abs(entityPos.getY() - pos.getY()) < HEIGHT)
                {
                    affectEntity(entity);
                }
            }
        }

        //ParticleEffect.EXPLOSION_NORMAL.display(1, 1, 1, 0.1f, 5, pos, 5);
        ParticleEffect.EXPLOSION_NORMAL.display(new Vector(0.3, 3.0, 0.3), 0.1f, pos, 200);

        currentRadius += EXPANSION_SPEED;

        if(currentRadius > radius)
        {
            done = true;
        }
    }

    public void affectEntity(Entity entity)
    {
        Vector oldVelocity = entity.getVelocity();
        Vector newVelocity = oldVelocity.add(new Vector(0, ACCELERATION, 0));

        entity.setVelocity(newVelocity);
    }
}
