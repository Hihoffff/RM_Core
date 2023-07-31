package com.ruinsmc.skills.fishing;

import com.ruinsmc.RM_Core;
import com.ruinsmc.skills.Skill;
import com.ruinsmc.skills.Skills;
import org.bukkit.entity.Item;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerFishEvent;

public class FishingMain implements Listener {
    private final RM_Core plugin;
    private final Skill skill = Skills.FISHING;

    public FishingMain(RM_Core plugin){
        this.plugin = plugin;
    }
    @EventHandler
    void onPlayerFishing(PlayerFishEvent e){
        if(e.getCaught() instanceof Item){
            plugin.getSkillsManager().addSkillXp(e.getPlayer(),skill,10);
        }
    }
}
