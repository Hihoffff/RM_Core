package com.ruinsmc.data.Inventory;

import com.ruinsmc.RM_Core;
import com.ruinsmc.Storage;
import org.bukkit.configuration.Configuration;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.scheduler.BukkitRunnable;


public class savePlayerInventory implements Listener {
    private final RM_Core plugin;

    public savePlayerInventory(RM_Core plugin){
        this.plugin = plugin;

    }



    @EventHandler(priority = EventPriority.HIGHEST)
    public void onPlayerQuit(PlayerQuitEvent e){
        Player player = e.getPlayer();
        saveInventory(player);
        player.getInventory().clear();
        player.getEnderChest().clear();
    }

    public void saveInventory(Player player){
        new BukkitRunnable(){
            public void run(){
                if(player == null){return;}
                Storage inventoryStorage = new Storage("./"+player.getUniqueId().toString()+"/inventory",plugin);
                Configuration config = inventoryStorage.getConfig();
                config.set("Inventory",player.getInventory().getContents());
                config.set("EnderChest",player.getEnderChest().getContents());

                inventoryStorage.save();
            }
        }.runTaskAsynchronously(plugin);
    }
}
