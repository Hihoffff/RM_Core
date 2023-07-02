package com.ruinsmc.data;

import com.ruinsmc.RM_Core;
import com.ruinsmc.Storage;
import com.ruinsmc.skills.Skill;
import com.ruinsmc.skills.Skills;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.UUID;

public class loadPlayerData implements Listener {
    private final RM_Core plugin;
    public loadPlayerData(RM_Core plugin){
        this.plugin = plugin;
        plugin.getServer().getPluginManager().registerEvents(this,plugin);
    }

    public void loadPlayerDataFromDisk(Player player){
        try{
            if(player == null){return;}
            if(!player.isOnline()){return;}
            PlayerData playerData = new PlayerData(player,plugin);
            Storage storage = new Storage("./"+player.getUniqueId()+"/"+"skills", plugin);
            for(Skill skill : Skills.values()){
                playerData.setSkillXp(skill,storage.getConfig().getDouble("skills."+skill.name()+".xp"));
                playerData.setSkillLevel(skill,storage.getConfig().getInt("skills."+skill.name()+".lvl"));
            }
            plugin.getPlayerManager().addPlayerData(playerData);
            plugin.getCharacterStatsManager().updatePlayerCharacterStats(player);
        }catch (Exception ex) {
            ex.printStackTrace();
            if(player.isOnline()){
                player.sendMessage(ChatColor.RED+"Возникла непредвиденная ошибка во время загрузки данных. Перезайдите на сервер.");
            }
        }

    }
    @EventHandler(priority = EventPriority.HIGHEST)
    public void onPlayerJoin(PlayerJoinEvent e){
        Player player = e.getPlayer();
        new BukkitRunnable() {
            @Override
            public void run() {
                loadPlayerDataFromDisk(player);
            }
        }.runTaskAsynchronously(plugin);

    }


}
