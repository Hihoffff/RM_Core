package com.ruinsmc.stats;

import com.ruinsmc.RM_Core;
import com.ruinsmc.data.PlayerData;
import de.tr7zw.nbtapi.NBTItem;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerItemHeldEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.inventory.ItemStack;

import java.util.Locale;

public class toolStatsUpdater implements Listener {
    private final RM_Core plugin;
    public toolStatsUpdater(RM_Core plugin){
        this.plugin = plugin;
    }

    private void updateToolStats(Player player,ItemStack item){
        PlayerData playerData = plugin.getPlayerManager().getPlayerData(player.getUniqueId());
        if(playerData == null) return;
        for(Stat stat : Stats.values()){
            Integer value = checkItemStats(item,stat.toString().toLowerCase(Locale.ROOT));
            playerData.setToolStats(stat,(double) value);
        }
    }

    private Integer checkItemStats(ItemStack item, String statName){
        if(item == null){return 0;}
        if(item.getType().equals(Material.AIR) || item.getType().equals(null)){return 0;}
        Integer value = 0;
        NBTItem nbtitem = new NBTItem(item);
        if(nbtitem.hasKey(statName)){value = nbtitem.getInteger(statName);}
       // plugin.getLogger().info(statName+": "+value);
        return value;
    }

    @EventHandler
    public void onToolInMainHandUpdated(PlayerItemHeldEvent e){
        updateToolStats(e.getPlayer(),e.getPlayer().getInventory().getItem(e.getNewSlot()));
    }
    @EventHandler
    public void onplayerJoin(PlayerJoinEvent e){
        updateToolStats(e.getPlayer(),e.getPlayer().getInventory().getItemInMainHand());
    }
}
