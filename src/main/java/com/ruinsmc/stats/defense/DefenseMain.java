package com.ruinsmc.stats.defense;

import com.ruinsmc.RM_Core;
import com.ruinsmc.stats.Stats;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageEvent;

public class DefenseMain implements Listener {
    private final RM_Core plugin;
    public DefenseMain(RM_Core plugin){
        this.plugin = plugin;
    }
    @EventHandler
    public void onPlayerDamage(EntityDamageEvent e){
        if(e.getEntity() instanceof Player){
            Player player = ((Player) e.getEntity()).getPlayer();
            if(player == null){return;}
            Double defense = plugin.getPlayerManager().getPlayerData(player.getUniqueId()).getStatLevel(Stats.DEFENSE);
            Double damagereduction = defense / (defense + 100f);
            Double damage = (e.getDamage() * (1f - damagereduction));
            plugin.getHealthManager().removeHealth(player,damage);
            e.setDamage(0);
            plugin.getInstance().getLogger().info("Damage: "+damage);
        }
    }
}
