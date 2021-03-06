package org.galaxycraft.thezoq2.zoqrpg;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scheduler.BukkitScheduler;
import org.galaxycraft.thezoq2.zoqrpg.boons.Boon;
import org.galaxycraft.thezoq2.zoqrpg.exceptions.FactoryCreationFailedException;
import org.galaxycraft.thezoq2.zoqrpg.exceptions.InvalidDatafileException;
import org.galaxycraft.thezoq2.zoqrpg.exceptions.NoSuchVariableException;
import org.galaxycraft.thezoq2.zoqrpg.exceptions.WrongDatatypeException;
import org.galaxycraft.thezoq2.zoqrpg.factories.*;
import org.galaxycraft.thezoq2.zoqrpg.fileio.DataFileReader;
import org.galaxycraft.thezoq2.zoqrpg.fileio.FileManager;
import org.galaxycraft.thezoq2.zoqrpg.fileio.StructValue;
import org.galaxycraft.thezoq2.zoqrpg.spells.Spell;

import java.io.*;
import java.util.logging.Level;


/**
 * Main class of the project. Loads all config files required and sets up all base objects for later use.
 */

public class RPGMain extends JavaPlugin implements Listener
{
    private UpdatableManager<Spell> spellManager;
    private UpdatableManager<Boon> boonManager;

    private SpellFactory spellFactory;

    @Override
    public void onEnable()
    {
        super.onEnable();

        getLogger().info("Loading ZoqRPG");

        BukkitScheduler scheduler = this.getServer().getScheduler();
        scheduler.scheduleSyncRepeatingTask(this, new RPGUpdateTask(this), 0L, 1L);

        //Initialising managers
        spellManager = new UpdatableManager<>();
        boonManager = new UpdatableManager<>();

        getServer().getPluginManager().registerEvents(this, this);

        FileManager.createPluginFoler();

        loadSpellConfig();
    }


    public void updateSpells(long timePassed)
    {
        spellManager.updateAll(timePassed);
    }

    public void updateBoons(long timePassed)
    {
        boonManager.updateAll(timePassed);
    }

    /**
     * Function executed when a player interracts with the world by right or left clicking.
     * @param event The Bukkit interract event that was triggered
     */
    @EventHandler
    public void onPlayerInteractEvent(PlayerInteractEvent event)
    {
        Player plr = event.getPlayer();
        //Run all the interract events on boons for the player
        for(Boon boon : boonManager.getUpdatableList())
        {
            if (boon.getAffectedEntity() == plr)
            {
                if(!boon.onPlayerInterractEvent(event))
                {
                    event.setCancelled(true);
                    return;
                }
            }
        }

        Bukkit.getLogger().info("Interract event");

        Material playerItemMat = plr.getItemInHand().getData().getItemType();
        try
        {
            final Material fireItem = Material.BLAZE_ROD;
            final Material blinkItem = Material.ENDER_PEARL;
            if(playerItemMat == fireItem)
            {
                Spell spell = spellFactory.createSpell("fireball", plr);

                spell.onCreate(boonManager);

                spellManager.add(spell);
            }
            else if(playerItemMat == blinkItem)
            {
                Spell spell = spellFactory.createSpell("blink", plr);

                spell.onCreate(boonManager);

                spellManager.add(spell);

                Bukkit.getLogger().info("Casting spell");
            }
        } catch (FactoryCreationFailedException e)
        {
            e.printStackTrace();
        }
   }

    /**
     * Load the spell configuration file
     */
    private void loadSpellConfig()
    {
        boolean configsLoaded = false;
        try(FileReader fr = FileManager.getFileReader("spells"))
        {
            DataFileReader dr = new DataFileReader(fr, "ZoqRPG/spells");

            StructValue fileStruct = dr.getFileStruct();

            StructValue spellStruct = fileStruct.getVariableOfTypeByName("spells", StructValue.class);
            StructValue visStruct = fileStruct.getVariableOfTypeByName("visualisers", StructValue.class);
            StructValue volumeStruct = fileStruct.getVariableOfTypeByName("volumes", StructValue.class);
            StructValue moverStruct = fileStruct.getVariableOfTypeByName("movers", StructValue.class);
            StructValue boonStruct = fileStruct.getVariableOfTypeByName("boons", StructValue.class);

            //Create the factories
            MoverFactory moverFactory = new MoverFactory(moverStruct);
            VolumeFactory volumeFactory = new VolumeFactory(volumeStruct);
            BoonFactory boonFactory = new BoonFactory(boonStruct);
            VisualiserFactory visFactory = new VisualiserFactory(visStruct);

            SpellFactoryGroup sfg = new SpellFactoryGroup(moverFactory, boonFactory, visFactory, volumeFactory);

            spellFactory = new SpellFactory(spellStruct, sfg);

            configsLoaded = true;
        } catch (IOException e)
        {
            getLogger().log(Level.SEVERE, "Failed to load spell datafile");
            e.printStackTrace();
        } catch (InvalidDatafileException e)
        {
            getLogger().log(Level.SEVERE, "Failed to parse spell datafile.");
            getLogger().log(Level.INFO, e.getMessage());

            e.printStackTrace();
        } catch (WrongDatatypeException e)
        {
            getLogger().log(Level.SEVERE, "Failed to load spell file, " + e.getVarPath() + " is the wrong datatype " +
                    "expected: " + e.getExpectedDatatype());

            e.printStackTrace();
        } catch (NoSuchVariableException e)
        {
            getLogger().log(Level.SEVERE, "Failed to load spell file, struct " + e.getStructPath() + " does not cotain "
                    + e.getVarName());
            e.printStackTrace();
        }

        //If the datafiles failed to load propperly, unload the plugin
        if(!configsLoaded)
        {
            getServer().getPluginManager().disablePlugin(this);
        }
    }
}
