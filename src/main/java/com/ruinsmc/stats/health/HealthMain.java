package com.ruinsmc.stats.health;

import com.ruinsmc.RM_Core;
import com.ruinsmc.stats.Stats;
import org.bukkit.entity.Player;
import org.bukkit.event.Listener;

public class HealthMain implements Listener {
    private final RM_Core plugin;
    private static final int defaultHP = 20;
    private static final int heartsCount = 20;
    public HealthMain(RM_Core plugin){
        this.plugin = plugin;
    }
    public double getHealth(Player player){
        return (player.getHealth() / heartsCount) * getMaxHealth(player);
    }
    public double getMaxHealth(Player player){
        return plugin.getPlayerManager().getPlayerData(player.getUniqueId()).getStatLevel(Stats.HEALTH) + defaultHP;
    }
    public void removeHealth(Player player,Double amount){
        if(player == null){return;}
        if(player.isDead()){return;}
        if(!player.isOnline()){return;}

        Double damage = (amount / getMaxHealth(player))*heartsCount;
        Double health = player.getHealth();
        if(damage >= health){
            player.setHealth(0);
            return;
        }
        if(damage < health){
            player.setHealth(health - damage);
        }
    }
    public void addHealth(Player player,Double amount){
        if(player == null){return;}
        if(player.isDead()){return;}
        if(!player.isOnline()){return;}
        Double health = player.getHealth();
        if(health == defaultHP){return;}
        Double regen = (amount / getMaxHealth(player))*heartsCount;
        if(regen + health >= defaultHP){player.setHealth(defaultHP);return;}
        if(regen + health < defaultHP){player.setHealth(health + regen);}
    }
}
