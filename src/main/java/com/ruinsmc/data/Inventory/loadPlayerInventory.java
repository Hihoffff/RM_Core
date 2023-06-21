package com.ruinsmc.data.Inventory;

import com.ruinsmc.RM_Core;
import com.ruinsmc.Storage;
import org.bukkit.configuration.Configuration;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.List;


public class loadPlayerInventory implements Listener {
    private final RM_Core plugin;

    public loadPlayerInventory(RM_Core plugin){
        this.plugin = plugin;
    }

    @EventHandler(priority = EventPriority.HIGHEST)
    public void onPlayerJoin(PlayerJoinEvent e){
        Player player = e.getPlayer();
        loadInventory(player);
    }

    public void loadInventory(Player player){
        new BukkitRunnable(){
            public void run(){
                Storage storageInventory = new Storage("./"+player.getUniqueId().toString()+"/inventory",plugin);
                Configuration config = storageInventory.getConfig();
                Object invO = config.get("Inventory");
                Object enderChestO = config.get("EnderChest");
                if(invO == null || enderChestO == null){return;}
                ItemStack[] inventory = null;
                if (invO instanceof ItemStack[]){
                    inventory = (ItemStack[]) invO;
                } else if (invO instanceof List){
                    List lista = (List) invO;
                    inventory = (ItemStack[]) lista.toArray(new ItemStack[0]);
                }
                player.getInventory().clear();
                player.getInventory().setContents(inventory);
                ItemStack[] enderChest = null;
                if (enderChestO instanceof ItemStack[]){
                    enderChest = (ItemStack[]) enderChestO;
                } else if (enderChestO instanceof List){
                    List listb = (List) enderChestO;
                    enderChest = (ItemStack[]) listb.toArray(new ItemStack[0]);
                }
                player.getEnderChest().clear();
                player.getEnderChest().setContents(enderChest);

            }
        }.runTaskAsynchronously(plugin);
    }
}
