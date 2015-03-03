package org.galaxycraft.thezoq2;

import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scheduler.BukkitScheduler;

import java.util.ArrayList;
import java.util.List;

public class RPGMain extends JavaPlugin implements Listener
{
    List<ModularSpell> activeSpells;
    List<Boon> activeBoons;

    SpellFactory spellFactory;
    @Override
    public void onEnable()
    {
        getLogger().info("Loading ZoqRPG");

        BukkitScheduler scheduler = this.getServer().getScheduler();
        scheduler.scheduleSyncRepeatingTask(this, new RPGUpdateTask(this), 0L, 1L);

        //Initialising lists
        activeSpells = new ArrayList<ModularSpell>();
        activeBoons = new ArrayList<Boon>();

        getServer().getPluginManager().registerEvents(this, this);

        spellFactory = new SpellFactory();
    }

    public void updateSpells()
    {
        List<ModularSpell> doneSpells = new ArrayList<ModularSpell>();
        //Looping through all the currently active spells
        for(ModularSpell spell : activeSpells)
        {
            spell.update();

            //Checking if the spell is done playing
            if(spell.isDone())
            {
                doneSpells.add(spell);
            }

        }

        //Removing the active spr ells
        for(ModularSpell doneSpell : doneSpells)
        {
            activeSpells.remove(doneSpell);
        }
    }

    public void updateBoons()
    {
        List<Boon> doneBoons = new ArrayList<Boon>();

        for(Boon boon : activeBoons)
        {
            boon.update();

            //Checkign if the boon is done playing
            if(boon.isDone())
            {
                doneBoons.add(boon);
            }
        }

        //Removing the boons that are done
        for(Boon boon : doneBoons)
        {
            removeBoon(boon);
        }
    }

    @EventHandler
    public void onPlayerInteractEvent(PlayerInteractEvent event)
    {
        Player plr = event.getPlayer();
        plr.sendMessage("Event");
        //Run all the interract events on boons for the player
        for(Boon boon : activeBoons)
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


        //addSpell(spellFactory.createEffect(SpellFactory.EffectType.WIRLWIND, 1, plr.getLocation()));

        //Boon newBoon = BoonFactory.addBoonToEntity(BoonType.BLEEDING, plr, getBoonsOnEntity(plr), 0.5f);
        Boon newBoon = BoonFactory.addBoonToEntity(BoonType.BLINK, plr, getBoonsOnEntity(plr), 0.5f);

        if(newBoon != null)
        {
            addBoonToList(newBoon);
        }
    }

    public void addSpell(ModularSpell spell)
    {
        activeSpells.add(spell);

        System.out.println("adding a spell");
    }

    public void addBoonToList(Boon boon)
    {
        activeBoons.add(boon);
    }

    //Removes a boon from the boon list
    public void removeBoon(Boon boon)
    {
        //Run the onEnd function
        boon.onEnd();

        activeBoons.remove(boon);
    }

    public List<Boon> getBoonsOnEntity(Entity entity)
    {
        List<Boon> entityBoons = new ArrayList<Boon>();

        for(Boon boon : activeBoons)
        {
            if(boon.getAffectedEntity() == entity)
            {
                entityBoons.add(boon);
            }
        }
        return entityBoons;
    }
}
