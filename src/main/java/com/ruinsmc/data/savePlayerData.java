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

import java.util.UUID;

public class savePlayerData implements Listener {
    private final RM_Core plugin;
    public savePlayerData(RM_Core plugin){
        this.plugin = plugin;
        startAutoSave();
    }
    public void saveStorage(UUID uuid){
        try{
            PlayerData playerData = plugin.getPlayerManager().getPlayerData(uuid);
            if(playerData == null){return;}
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
                saveStorage(player.getUniqueId());
                plugin.getPlayerManager().removePlayerData(player.getUniqueId());
            }
        }.runTaskAsynchronously(plugin);

    }
    private void startAutoSave(){
        plugin.getLogger().info("Registering AutoSave thread...");
        int interval = plugin.getConfig().getInt("player.AutoSaveDelay");
        new BukkitRunnable() {
            @Override
            public void run() {
                for (Player player : Bukkit.getOnlinePlayers()) {
                    new BukkitRunnable(){
                        public void run(){
                            saveStorage(player.getUniqueId());
                        }
                    }.runTaskAsynchronously(plugin);
                }
            }
        }.runTaskTimer(plugin, interval, interval);
    }
}

