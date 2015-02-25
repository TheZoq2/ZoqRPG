package io.github.thezoq2;

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
    List<Spell> activeSpells;
    List<Boon> activeBoons;

    SpellFactory spellFactory;
    @Override
    public void onEnable()
    {
        getLogger().info("Loading ZoqRPG");

        BukkitScheduler scheduler = this.getServer().getScheduler();
        scheduler.scheduleSyncRepeatingTask(this, new RPGUpdateTask(this), 0L, 1L);

        //Initialising lists
        activeSpells = new ArrayList<Spell>();
        activeBoons = new ArrayList<Boon>();

        getServer().getPluginManager().registerEvents(this, this);

        spellFactory = new SpellFactory();
    }

    public void updateSpells()
    {
        List<Spell> doneSpells = new ArrayList<Spell>();
        //Looping through all the currently active spells
        for(Spell spell : activeSpells)
        {
            spell.update();

            //Checking if the spell is done playing
            if(spell.isDone())
            {
                doneSpells.add(spell);
            }

        }

        //Removing the active spells
        for(Spell doneSpell : doneSpells)
        {
            activeSpells.remove(doneSpell);
        }
    }

    @EventHandler
    public void onPlayerInteractEvent(PlayerInteractEvent event)
    {
        Player plr = event.getPlayer();

        plr.sendMessage("Event");

        addSpell(spellFactory.createEffect(SpellFactory.EffectType.WIRLWIND, 1, plr.getLocation()));
    }

    public void addSpell(Spell spell)
    {
        activeSpells.add(spell);

        System.out.println("adding a spell");
    }

    public void addBoon(Boon boon)
    {
        activeBoons.add(boon);
    }
}
