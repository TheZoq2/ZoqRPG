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
import org.galaxycraft.thezoq2.zoqrpg.boons.BurningBoon;
import org.galaxycraft.thezoq2.zoqrpg.fileio.DataFileReader;
import org.galaxycraft.thezoq2.zoqrpg.fileio.FileManager;
import org.galaxycraft.thezoq2.zoqrpg.movers.LinearMover;
import org.galaxycraft.thezoq2.zoqrpg.movers.Mover;
import org.galaxycraft.thezoq2.zoqrpg.spells.ModularSpell;
import org.galaxycraft.thezoq2.zoqrpg.spells.Spell;
import org.galaxycraft.thezoq2.zoqrpg.visualisers.FireVisualiser;
import org.galaxycraft.thezoq2.zoqrpg.volumes.SphereVolume;

import java.io.*;
import java.net.FileNameMap;
import java.util.ArrayList;
import java.util.List;

public class RPGMain extends JavaPlugin implements Listener
{
    private SpellManager spellManager;
    private BoonManager boonManager;

    private SpellFactory spellFactory;
    @Override
    public void onEnable()
    {
        getLogger().info("Loading ZoqRPG");

        BukkitScheduler scheduler = this.getServer().getScheduler();
        scheduler.scheduleSyncRepeatingTask(this, new RPGUpdateTask(this), 0L, 1L);

        //Initialising managers
        spellManager = new SpellManager();
        boonManager = new BoonManager();

        getServer().getPluginManager().registerEvents(this, this);

        spellFactory = new SpellFactory();

        FileManager.createPluginFoler();

        try
        {
            FileReader fr = FileManager.getFileReader("spells");

            DataFileReader dr = new DataFileReader(fr);
        } catch (FileNotFoundException e)
        {
            e.printStackTrace();
        } catch (IOException e)
        {
            e.printStackTrace();
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
                if (boon.onPlayerInterractEvent() == false)
                {
                    event.setCancelled(true);
                    return;
                }
            }
        }

        Vector direction = plr.getLocation().getDirection();

        Mover mover = new LinearMover(10f, direction);
        SphereVolume volume = new SphereVolume(plr.getLocation().toVector(), 1);
        Boon boon = new BurningBoon();

        Spell spell = new ModularSpell(plr.getLocation().add(0,1,0), plr, mover,volume, boon, new FireVisualiser());
        spell.onCreate(boonManager);

        spellManager.addSpell(spell);

        //Spell spell = new ModularSelfSpell(plr, new BlinkBoon());
        //spell.onCreate(boonManager);

        //spellManager.addSpell(spell);
   }

    public void addSpell(ModularSpell spell)
    {
        spellManager.addSpell(spell);

        System.out.println("adding a spell");
    }

    public void addBoonToList(Boon boon)
    {
        boonManager.addBoon(boon);
    }

    public List<Boon> getBoonsOnEntity(Entity entity)
    {
        List<Boon> entityBoons = new ArrayList<Boon>();

        for(Boon boon : boonManager.getBoonList())
        {
            if(boon.getAffectedEntity() == entity)
            {
                entityBoons.add(boon);
            }
        }
        return entityBoons;
    }
}
