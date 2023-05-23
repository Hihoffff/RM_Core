package com.ruinsmc.stats.health;

import com.ruinsmc.RM_Core;
import com.ruinsmc.data.PlayerData;
import com.ruinsmc.stats.Stats;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityRegainHealthEvent;

public class RegenerationMain implements Listener {
    private final RM_Core plugin;
    public RegenerationMain(RM_Core plugin){
        this.plugin = plugin;
    }

    @EventHandler(priority = EventPriority.HIGHEST)
    public void onRegen(EntityRegainHealthEvent e) {
        if(e.getEntity() instanceof Player){
            Player player = (Player) e.getEntity();
            PlayerData playerData = plugin.getPlayerManager().getPlayerData(player.getUniqueId());
            Double healthRegen = playerData.getStatLevel(Stats.REGENERATION);
            Double maxHealth = plugin.getHealthManager().getMaxHealth(player);
            Double regenValue = ((maxHealth/100)+1.5)*((healthRegen+100)/100);
            plugin.getHealthManager().addHealth(player,regenValue);
            e.setAmount(0);
        }
    }
}
