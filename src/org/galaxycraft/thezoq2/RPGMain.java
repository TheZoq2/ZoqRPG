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


        //addSpell(spellFactory.createEffect(SpellFactory.EffectType.WIRLWIND, 1, plr.getLocation()));

        //Boon newBoon = BoonFactory.addBoonToEntity(BoonType.BLEEDING, plr, getBoonsOnEntity(plr), 0.5f);
        /*Boon newBoon = BoonFactory.addBoonToEntity(BoonType.BLINK, plr, getBoonsOnEntity(plr), 0.5f);

        if(newBoon != null)
        {
            addBoonToList(newBoon);
        }*/

        Mover mover = new LinearMover(0.3f, plr.getLocation().getDirection());
        SphereVolume volume = new SphereVolume(plr.getLocation().getDirection(), 1);
        Boon boon = new BurningBoon();

        Spell spell = new ModularSpell(plr.getLocation().add(0,1,0), plr, mover,volume, boon, new FireVisualiser());

        spellManager.addSpell(spell);
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
