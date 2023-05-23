package com.ruinsmc.skills.farming;

import com.ruinsmc.RM_Core;
import com.ruinsmc.customevents.XpGainEvent;
import com.ruinsmc.skills.Skill;
import com.ruinsmc.skills.Skills;
import org.bukkit.block.data.Ageable;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;

public class FarmingMain implements Listener {
    private final RM_Core plugin;
    private final Skill skill;

    public FarmingMain(RM_Core plugin){
        this.plugin = plugin;
        this.skill = Skills.FARMING;
    }

    @EventHandler
    public void onBlockBreak(BlockBreakEvent e){
        Player player = e.getPlayer();
        for(String bname : plugin.getInstance().getConfig().getConfigurationSection("farmingcrops").getKeys(false)){
            if(bname.equals(e.getBlock().getBlockData().getMaterial().name())){
                Ageable age = (Ageable) e.getBlock().getState().getBlockData();
                if(age.getAge() == age.getMaximumAge()){
                    XpGainEvent xpGainEvent = new XpGainEvent(player, skill,plugin.getInstance().getConfig().getDouble("farmingcrops."+bname));
                    plugin.getServer().getPluginManager().callEvent(xpGainEvent);
                    return;
                }
            }
        }
        if(e.getBlock().getMetadata("p").isEmpty()){
            for(String bname : plugin.getInstance().getConfig().getConfigurationSection("farmingblocks").getKeys(false)){
                if(bname.equals(e.getBlock().getBlockData().getMaterial().name())){
                    XpGainEvent xpGainEvent = new XpGainEvent(player, skill,plugin.getInstance().getConfig().getDouble("farmingblocks."+bname));
                    plugin.getServer().getPluginManager().callEvent(xpGainEvent);
                    return;
                }
            }
        }
    }
}
