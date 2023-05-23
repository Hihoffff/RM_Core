package com.ruinsmc.stats.wisdom;

import com.ruinsmc.RM_Core;
import com.ruinsmc.stats.Stats;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

public class WisdomMain {
    private final static int defaultMana = 20;
    private final RM_Core plugin;
    public WisdomMain(RM_Core plugin){
        this.plugin = plugin;
        startManaRegen();
    }

    public void startManaRegen(){
        plugin.getLogger().info("Players mana regen thread registering...");
        Bukkit.getScheduler().scheduleSyncRepeatingTask(plugin, () -> {
            for(Player player : Bukkit.getOnlinePlayers()){
                double curMana = getPlayerMana(player);
                double maxMana = getPlayerMaxMana(player);
                if(curMana >= maxMana){return;}
                double regenValue = maxMana * 0.04d;
                if(curMana + regenValue >= maxMana){
                    setPlayerMana(player,maxMana);
                    return;
                }
                setPlayerMana(player, curMana + regenValue);
            }
        },1,40);
    }
    public double getPlayerMaxMana(Player player){
        return plugin.getPlayerManager().getPlayerData(player.getUniqueId()).getStatLevel(Stats.WISDOM) + defaultMana;
    }
    public double getPlayerMana(Player player){
        return plugin.getPlayerManager().getPlayerData(player.getUniqueId()).getMana();
    }
    public void setPlayerMana(Player player, Double value){
        plugin.getPlayerManager().getPlayerData(player.getUniqueId()).setMana(value);
    }
}
