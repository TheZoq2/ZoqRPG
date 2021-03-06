package org.galaxycraft.thezoq2.zoqrpg.visualisers;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.galaxycraft.thezoq2.zoqrpg.fileio.StringValue;
import org.galaxycraft.thezoq2.zoqrpg.fileio.StructBasedObject;
import org.galaxycraft.thezoq2.zoqrpg.fileio.StructValue;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;

/**
 * Visualiser used to show a position as a floating block. Currently rendered in a bad way which shows
 *  the witherskulls that it is sitting on.
 */
public class BlockVisualiser extends StructBasedObject implements Visualiser
{
    private static final String DEFAULT_MATERIAL = "SAND";

    private List<FlyingBlock> lastBlocks; //Warning about field not being initalised is wrong. See init()
    private List<FlyingBlock>  currentBlocks;

    private Material material;
    public BlockVisualiser(StructValue sv)
    {
        init();

        String blockName = readValueWithFallback(sv, "block", new StringValue(DEFAULT_MATERIAL), StringValue.class).getValue();
        material = Material.getMaterial(blockName);

        if(material == null)
        {
            material = Material.getMaterial(DEFAULT_MATERIAL);

            Bukkit.getLogger().log(Level.WARNING, "Unknown material for block visualiser, falling back to default: " + DEFAULT_MATERIAL);
        }
    }

    public BlockVisualiser(Material material)
    {
        init();

        this.material = material;

        lastBlocks = new ArrayList<>();
        currentBlocks = new ArrayList<>();
    }

    public void init()
    {
        currentBlocks = new ArrayList<>();
        lastBlocks = new ArrayList<>();
    }

    @Override
    public void showLocation(Location loc)
    {
        currentBlocks.add(new FlyingBlock(loc, material));
    }

    @Override
    public void update()
    {
        //Remove the last blocks
        for(FlyingBlock block : lastBlocks)
        {
            block.remove();
        }

        lastBlocks = currentBlocks;

        //Allocate a new array list for storing current blocks
        currentBlocks = new ArrayList<>();
    }

    @Override
    public BlockVisualiser cloneObject()
    {
        return new BlockVisualiser(this.material);
    }
}
