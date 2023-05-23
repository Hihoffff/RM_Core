package com.ruinsmc.skills.foraging;

import com.ruinsmc.RM_Core;
import com.ruinsmc.customevents.XpGainEvent;
import com.ruinsmc.skills.Skill;
import com.ruinsmc.skills.Skills;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;

public class ForagingMain implements Listener {
    private final RM_Core plugin;
    private final Skill skill;

    public ForagingMain(RM_Core plugin){
        this.plugin = plugin;
        this.skill = Skills.FORAGING;
    }

    @EventHandler
    public void onBlockBreak(BlockBreakEvent e){
        Player player = e.getPlayer();
        if(e.getBlock().getMetadata("p").isEmpty()) {
            for (String bname : plugin.getInstance().getConfig().getConfigurationSection("foraging").getKeys(false)) {
                if (bname.equals(e.getBlock().getBlockData().getMaterial().name())) {
                    XpGainEvent xpGainEvent = new XpGainEvent(player, skill, plugin.getInstance().getConfig().getDouble("foraging." + bname));
                    plugin.getServer().getPluginManager().callEvent(xpGainEvent);
                    return;
                }
            }
        }
    }
}
