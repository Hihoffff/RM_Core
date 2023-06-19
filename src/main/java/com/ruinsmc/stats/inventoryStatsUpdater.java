package com.ruinsmc.stats;

import com.destroystokyo.paper.event.player.PlayerArmorChangeEvent;
import com.ruinsmc.RM_Core;
import com.ruinsmc.data.PlayerData;
import de.tr7zw.nbtapi.NBTItem;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.Locale;

public class inventoryStatsUpdater implements Listener {
    private final RM_Core plugin;
    public inventoryStatsUpdater(RM_Core plugin){
        this.plugin = plugin;
    }

    public void updateInventoryStats(Player player){
        new BukkitRunnable(){
            public void run(){
                PlayerData playerData = plugin.getPlayerManager().getPlayerData(player.getUniqueId());
                if(playerData == null) return;
                for(Stat stat : Stats.values()){
                    Integer value = checkStat(player,stat.toString().toLowerCase(Locale.ROOT));
                    playerData.setInventoryStats(stat,(double) value);
                }
            }
        }.runTaskAsynchronously(plugin);
    }
    private Integer checkStat(Player player,String statName){
        Integer value = 0;
        value += checkItemStats(player.getInventory().getBoots(),statName);
        value += checkItemStats(player.getInventory().getHelmet(),statName);
        value += checkItemStats(player.getInventory().getChestplate(),statName);
        value += checkItemStats(player.getInventory().getLeggings(),statName);
        return value;
    }
    private Integer checkItemStats(ItemStack item, String statName){
        if(item == null){return 0;}
        if(item.getType().equals(Material.AIR) || item.getType().equals(null)){return 0;}
        Integer value = 0;
        NBTItem nbtitem = new NBTItem(item);
        if(nbtitem.hasKey(statName)){value = nbtitem.getInteger(statName);}
        return value;
    }
    @EventHandler
    public void onPlayerUpdatedArmor(PlayerArmorChangeEvent e){
        updateInventoryStats(e.getPlayer());
    }

    @EventHandler
    public void onPlayerJoin(PlayerJoinEvent e){
        Player player = e.getPlayer();
        updateInventoryStats(player);
    }
}
