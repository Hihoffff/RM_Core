package com.ruinsmc.data;

import com.ruinsmc.RM_Core;
import com.ruinsmc.Storage;
import com.ruinsmc.skills.Skill;
import com.ruinsmc.skills.Skills;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.UUID;

public class savePlayerData implements Listener {
    private final RM_Core plugin;
    public savePlayerData(RM_Core plugin){
        this.plugin = plugin;
        plugin.getServer().getPluginManager().registerEvents(this,plugin);
        startAutoSave();
    }
    public void savePlayerDataToDisk(PlayerData playerData){
        try{
            if(playerData == null){return;}
            UUID uuid = playerData.getPlayer().getUniqueId();
            Storage storage = new Storage("./"+uuid+"/"+"skills", plugin);
            for(Skill skill : Skills.values()){
                double skillXP = playerData.getSkillXp(skill);
                int skillLVL = playerData.getSkillLevel(skill);
                if(!(skillLVL <= 0)){
                    storage.set("skills."+skill.name()+".lvl",skillLVL);
                }
                if(!(skillXP <= 0)){
                    storage.set("skills."+skill.name()+".xp",skillXP);
                }
            }
            storage.set("money",playerData.getMoney());
            storage.save();
        }catch (Exception ex) {
            ex.printStackTrace();
        }
    }
    @EventHandler(priority = EventPriority.HIGHEST)
    public void onPlayerQuit(PlayerQuitEvent e){
        Player player = e.getPlayer();
        new BukkitRunnable(){
            public void run(){
                PlayerData playerData = plugin.getPlayerManager().getPlayerData(player.getUniqueId());
                savePlayerDataToDisk(playerData);
                plugin.getPlayerManager().removePlayerData(player.getUniqueId());
            }
        }.runTaskAsynchronously(plugin);

    }
    public void saveALlPlayerData(boolean onlyOnlinePlayers){
        for (UUID uuid : plugin.getPlayerManager().getPlayerDataMap().keySet()) {
            if(onlyOnlinePlayers){
                if(plugin.getServer().getPlayer(uuid).isOnline()){
                    PlayerData playerData = plugin.getPlayerManager().getPlayerData(uuid);
                    savePlayerDataToDisk(playerData);
                    continue;
                }
                continue;
            }
            PlayerData playerData = plugin.getPlayerManager().getPlayerData(uuid);
            savePlayerDataToDisk(playerData);
        }
    }
    private void startAutoSave(){
        plugin.getLogger().info("Registering AutoSave thread...");
        int interval = plugin.getConfig().getInt("player.AutoSaveDelay");
        new BukkitRunnable() {
            @Override
            public void run() {
                saveALlPlayerData(true);
            }
        }.runTaskTimerAsynchronously(plugin, interval, interval);
    }
}

