package com.ruinsmc.stats.handlers;

import com.destroystokyo.paper.event.player.PlayerArmorChangeEvent;
import com.ruinsmc.RM_Core;
import com.ruinsmc.data.PlayerData;
import com.ruinsmc.stats.Stats;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityRegainHealthEvent;
import org.bukkit.event.player.PlayerItemHeldEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.scheduler.BukkitRunnable;

public class StatsHandler implements Listener {
    private final RM_Core plugin;
    public StatsHandler(RM_Core plugin){
        this.plugin = plugin;
        plugin.getServer().getPluginManager().registerEvents(this,plugin);
    }
    @EventHandler
    public void onPlayerUpdatedArmor(PlayerArmorChangeEvent e){
        plugin.getInventoryStatsManager().updatePlayerArmorStats(e.getPlayer());
    }
    @EventHandler
    public void onToolInMainHandUpdated(PlayerItemHeldEvent e){
        plugin.getInventoryStatsManager().updatePlayerMainHandToolStats(e.getPlayer(),e.getPlayer().getInventory().getItem(e.getNewSlot()));
    }
    @EventHandler
    public void onPlayerJoin(PlayerJoinEvent e){
        Player player = e.getPlayer();
        new BukkitRunnable(){
            public void run(){
                plugin.getInventoryStatsManager().updatePlayerArmorStats(player);
            }
        }.runTaskLaterAsynchronously(plugin,60);
    }

    @EventHandler(priority = EventPriority.HIGHEST)
    public void onRegen(EntityRegainHealthEvent e) {
        if(e.getEntity() instanceof Player){
            Player player = (Player) e.getEntity();
            PlayerData playerData = plugin.getPlayerManager().getPlayerData(player.getUniqueId());
            if(playerData == null){return;}
            Double healthRegen = playerData.getStatLevel(Stats.REGENERATION);
            Double maxHealth = plugin.getHealthManager().getMaxHealth(player);
            Double regenValue = ((maxHealth/100)+1.5)*((healthRegen+100)/100);
            plugin.getHealthManager().addHealth(player,regenValue);
            e.setAmount(0);
        }
    }

}
