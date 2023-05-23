package com.ruinsmc.stats.strength;

import com.ruinsmc.RM_Core;
import com.ruinsmc.stats.Stats;
import de.tr7zw.nbtapi.NBTItem;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.inventory.ItemStack;

public class StrengthMain implements Listener {
    private final RM_Core plugin;
    public StrengthMain(RM_Core plugin){
        this.plugin = plugin;
    }
    @EventHandler
    public void onPlayerAtack(EntityDamageByEntityEvent e){
        if(e.getDamager() instanceof Player){
            Player player = ((Player) e.getDamager()).getPlayer();
            ItemStack itemInHand = player.getInventory().getItemInMainHand();
            if(itemInHand.getType().equals(Material.AIR) || itemInHand.getType().equals(null)){return;}
            NBTItem nbtitem = new NBTItem(itemInHand);
            double critchance = plugin.getPlayerManager().getPlayerData(player.getUniqueId()).getStatLevel(Stats.CRITCHANCE);
            double damage = getDamage(player);
            if(chekCritChanse((critchance))){
                e.setDamage(damage*2);
                player.sendMessage(ChatColor.DARK_RED+"-"+(damage*2));
            }
            else{
                e.setDamage(damage);
                player.sendMessage(ChatColor.RED+"-"+damage);
            }

        }
    }
    public boolean chekCritChanse(double critchance){
        double rndvalue = (Math.random()*100);
        if(rndvalue <= critchance){
            return true;
        }
        return false;
    }
    public double getDamage(Player player){
        ItemStack itemInHand = player.getInventory().getItemInMainHand();
        double weaponDamage = 0;
        if(!itemInHand.getType().equals(Material.AIR) && !itemInHand.getType().equals(null)){
            NBTItem nbtitem = new NBTItem(itemInHand);
            if(nbtitem.hasKey("damage")){
                weaponDamage += nbtitem.getInteger("damage");
            }
        }
        return (weaponDamage + 2) * (1 + (plugin.getPlayerManager().getPlayerData(player.getUniqueId()).getStatLevel(Stats.STRENGTH)));
    }

}
