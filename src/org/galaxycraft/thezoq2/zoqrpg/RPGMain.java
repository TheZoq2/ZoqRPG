package org.galaxycraft.thezoq2.zoqrpg;

import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scheduler.BukkitScheduler;
import org.bukkit.util.Vector;
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
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;

/*
This warning is thrown for instances that are set in the try statement for loading config files. If the config
loading fails, the plugin disables itself so this is not an issue
 */
@SuppressWarnings("InstanceVariableMayNotBeInitialized")
public class RPGMain extends JavaPlugin implements Listener
{
    private SpellManager spellManager;
    private BoonManager boonManager;

    private SpellFactory spellFactory;

    //TODO: Move config loading to separate method
    @Override
    public void onEnable()
    {
        super.onEnable();

        getLogger().info("Loading ZoqRPG");

        BukkitScheduler scheduler = this.getServer().getScheduler();
        //will look into undepricated version later //TODO
        scheduler.scheduleSyncRepeatingTask(this, new RPGUpdateTask(this), 0L, 1L);

        //Initialising managers
        spellManager = new SpellManager();
        boonManager = new BoonManager();

        getServer().getPluginManager().registerEvents(this, this);

        //spellFactory = new SpellFactory();

        FileManager.createPluginFoler();

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

    public void updateSpells(long timePassed)
    {
        spellManager.updateAll(timePassed);
    }

    public void updateBoons(long timePassed)
    {
        boonManager.updateAll(timePassed);
    }

    @EventHandler
    public void onPlayerInteractEvent(PlayerInteractEvent event)
    {
        Player plr = event.getPlayer();
        plr.sendMessage("Event");
        //Run all the interract events on boons for the player
        for(Boon boon : boonManager.getBoonList())
        {
            if (boon.getAffectedEntity() == plr)
            {
                if(!boon.onPlayerInterractEvent())
                {
                    event.setCancelled(true);
                    return;
                }
            }
        }

        Vector direction = plr.getLocation().getDirection();

        boolean useFire = true; //Demo only, change to true if you want to test the fire spell
        if(useFire)
        {
            try
            {
                Spell spell = spellFactory.createSpell("fireball", plr);

                spell.onCreate(boonManager);

                spellManager.addSpell(spell);
            } catch (FactoryCreationFailedException e)
            {
                e.printStackTrace();
            }
        }
        else
        {
        }
   }
}
