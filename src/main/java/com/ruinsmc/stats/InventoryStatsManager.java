package com.ruinsmc.stats;

import com.ruinsmc.RM_Core;
import com.ruinsmc.data.PlayerData;
import de.tr7zw.nbtapi.NBTItem;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.Locale;

public class InventoryStatsManager {
    private final RM_Core plugin;
    public InventoryStatsManager(RM_Core plugin){
        this.plugin = plugin;
    }

    public void updatePlayerArmorStats(Player player){ //updates armor stats
        new BukkitRunnable(){
            public void run(){
                PlayerData playerData = plugin.getPlayerManager().getPlayerData(player.getUniqueId());
                if(playerData == null || !player.isOnline()) return;
                for(Stat stat : Stats.values()){
                    Integer value = checkArmorStat(player,stat.toString().toLowerCase(Locale.ROOT));
                    playerData.setInventoryStats(stat,(double) value);
                }
            }
        }.runTaskAsynchronously(plugin);
    }

    public void updatePlayerMainHandToolStats(Player player,ItemStack item){ //updates stats of tool in main hand
        PlayerData playerData = plugin.getPlayerManager().getPlayerData(player.getUniqueId());
        if(playerData == null || !player.isOnline()) return;
        for(Stat stat : Stats.values()){
            Integer value = checkItemStats(item,stat.toString().toLowerCase(Locale.ROOT));
            playerData.setToolStats(stat,(double) value);
        }
    }

    private Integer checkArmorStat(Player player,String statName){
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

}
