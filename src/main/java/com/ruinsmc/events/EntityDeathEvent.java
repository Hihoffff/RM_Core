package com.ruinsmc.events;

import com.ruinsmc.RM_Core;
import com.ruinsmc.customevents.XpGainEvent;
import com.ruinsmc.skills.Skills;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;

public class EntityDeathEvent implements Listener {

    private final RM_Core plugin;
    public EntityDeathEvent(RM_Core plugin){
        this.plugin = plugin;
    }
    @EventHandler
    public void onEntityDeath(org.bukkit.event.entity.EntityDeathEvent e){
        if(e.getEntity() instanceof Entity && e.getEntity().getKiller() instanceof Player){
            Player player = e.getEntity().getKiller();
            XpGainEvent xpGainEvent = new XpGainEvent(player, Skills.COMBAT,10);
            plugin.getServer().getPluginManager().callEvent(xpGainEvent);
        }

    }
}
