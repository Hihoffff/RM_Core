package com.ruinsmc.skills.mining;

import com.ruinsmc.RM_Core;
import com.ruinsmc.customevents.XpGainEvent;
import com.ruinsmc.skills.Skill;
import com.ruinsmc.skills.Skills;
import org.bukkit.GameMode;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.metadata.FixedMetadataValue;

public class MiningMain implements Listener {
    private final RM_Core plugin;
    private final Skill skill;
    public MiningMain(RM_Core plugin){
        this.plugin = plugin;
        this.skill = Skills.MINING;
    }
    @EventHandler
    public void onBlockBreak(BlockBreakEvent e){
        Player player = e.getPlayer();
        if(e.getBlock().getMetadata("p").isEmpty()) {
            for (String bname : plugin.getInstance().getConfig().getConfigurationSection("mining").getKeys(false)) {
                if (bname.equals(e.getBlock().getBlockData().getMaterial().name())) {
                    XpGainEvent xpGainEvent = new XpGainEvent(player, skill, plugin.getInstance().getConfig().getDouble("mining." + bname));
                    plugin.getServer().getPluginManager().callEvent(xpGainEvent);
                    return;
                }
            }
        }
    }
    @EventHandler
    public void onBlockPlace(BlockPlaceEvent e){
        if(e.getPlayer().getGameMode() != GameMode.CREATIVE){
            e.getBlockPlaced().setMetadata("p",new FixedMetadataValue(plugin.getInstance(), true));
        }
    }
}
