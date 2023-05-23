package com.ruinsmc.data;

import com.ruinsmc.RM_Core;
import com.ruinsmc.Storage;
import com.ruinsmc.skills.Skill;
import com.ruinsmc.skills.Skills;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.scheduler.BukkitRunnable;

public class savePlayerData implements Listener {
    private final RM_Core plugin;
    public savePlayerData(RM_Core plugin){
        this.plugin = plugin;
        startAutoSave();
    }
    public void saveStorage(Player player){
        PlayerData playerData = plugin.getPlayerManager().getPlayerData(player.getUniqueId());
        Storage storage = new Storage("./"+player.getUniqueId()+"/"+"skills", plugin);
        for(Skill skill : Skills.values()){
            storage.set("skills."+skill.name()+".xp",playerData.getSkillXp(skill));
            storage.set("skills."+skill.name()+".lvl",playerData.getSkillLevel(skill));
        }
        storage.save();
    }
    @EventHandler(priority = EventPriority.HIGHEST)
    public void onPlayerQuit(PlayerQuitEvent e){
        Player player = e.getPlayer();
        saveStorage(player);
        plugin.getPlayerManager().removePlayerData(player.getUniqueId());
    }
    private void startAutoSave(){
        plugin.getLogger().info("Registering AutoSave thread...");
        int interval = plugin.getConfig().getInt("player.AutoSaveDelay");
        new BukkitRunnable() {
            @Override
            public void run() {
                for (Player player : Bukkit.getOnlinePlayers()) {
                    PlayerData playerData = plugin.getPlayerManager().getPlayerData(player.getUniqueId());
                    if (playerData != null) {
                        plugin.getSavePlayerData().saveStorage(player);
                    }
                    else{
                        plugin.getLogger().info("{WARNING} [AutoSave] PlayerData of "+player.getName()+" = null!!!");
                    }
                }
            }
        }.runTaskTimerAsynchronously(plugin, interval, interval);
    }
}

