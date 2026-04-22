package com.onursessiz.ilkplugin;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.entity.Player;
import org.bukkit.event.entity.EntityDeathEvent;





public class CoinSystem implements Listener {



    private final IlkPlugin plugin;

    public CoinSystem(IlkPlugin plugin) {
        this.plugin = plugin;
    }

    @EventHandler
    public void onKill(EntityDeathEvent deathEvent) {
      if (deathEvent.getEntity() instanceof Player){
          Player victim = (Player) deathEvent.getEntity();
          Player slayer = victim.getKiller();

          if (slayer != null) {
              plugin.addCoins(slayer.getUniqueId(), 10);
              slayer.sendMessage("10 coin kazandın!");
              plugin.getConfig().set("coins." + slayer.getUniqueId(), plugin.getCoins(slayer.getUniqueId()));
              plugin.saveConfig();
          }

      }
    }
}
