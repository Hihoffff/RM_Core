package com.ruinsmc.skills;

import com.ruinsmc.RM_Core;
import com.ruinsmc.customevents.XpGainEvent;
import com.ruinsmc.data.PlayerData;
import org.bukkit.configuration.Configuration;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.scheduler.BukkitRunnable;
import org.jetbrains.annotations.Nullable;

import java.util.HashMap;
import java.util.Map;

public class skillsManager implements Listener {

    private final RM_Core plugin;
    private final Map<String, Skill> registeredSkills; //all registered skills
    private final Integer[] XPforLevel; //how many xp player need to get new lvl
    public skillsManager(RM_Core plugin){
        this.plugin = plugin;
        this.registeredSkills = new HashMap<>();
        for(Skill sk : Skills.values()){
            registeredSkills.put(sk.name(),sk);
        }
        Configuration config = plugin.getConfig();

        ConfigurationSection XpSection = config.getConfigurationSection("XP_For_Level");
        this.XPforLevel = new Integer[XpSection.getKeys(false).size()];
        for(String lvlStr : XpSection.getKeys(false)){
            Integer lvl = Integer.parseInt(lvlStr);
            Integer xp = config.getInt("XP_For_Level."+lvlStr);
            if(lvl <= 0){
                continue;
            }
            this.XPforLevel[lvl - 1] = xp;
        }
    }

    @EventHandler
    public void onXpGainEvent(XpGainEvent e){
        Player player = e.getPlayer();
        Skill skill = e.getSkill();
        double amount = e.getAmount();

        addSkillXp(player,skill,amount);
        CheckSkillLvl(player,skill);
    }
    public void CheckSkillLvl(Player player, Skill skill){ //check if new lvl
        new BukkitRunnable(){
            @Override
            public void run(){
                PlayerData playerData = plugin.getPlayerManager().getPlayerData(player.getUniqueId());
                if(playerData == null){return;}
                double curXP = playerData.getSkillXp(skill);
                while((curXP >= getXPforLevel(playerData.getSkillLevel(skill) + 1)) && (playerData.getSkillLevel(skill) < getMaxLevel())){
                    playerData.setSkillLevel(skill,playerData.getSkillLevel(skill)+1);
                    player.sendMessage("New lvl! "+skill+": "+(playerData.getSkillLevel(skill)));
                }
            }
        }.runTaskAsynchronously(plugin);
    }
    @Nullable
    public Skill getRegisteredSkill(String name){
        return registeredSkills.get(name);
    }
    private void addSkillXp(Player player,Skill skill,double amount){
        PlayerData playerData = plugin.getPlayerManager().getPlayerData(player.getUniqueId());
        if(playerData != null){
            plugin.getPlayerManager().getPlayerData(player.getUniqueId()).addSkillXp(skill,amount);
        }
    }
    public Integer getXPforLevel(Integer level){
        if(level > getMaxLevel()){
            return 999999999;
        }
        return this.XPforLevel[level-1];
    }
    public Integer getMaxLevel(){
        return this.XPforLevel.length;
    }
}
