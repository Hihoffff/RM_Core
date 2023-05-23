package com.ruinsmc.skills;

import com.ruinsmc.RM_Core;
import com.ruinsmc.customevents.XpGainEvent;
import com.ruinsmc.data.PlayerData;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.jetbrains.annotations.Nullable;

import java.util.HashMap;
import java.util.Map;

public class skillsManager implements Listener {

    private final RM_Core plugin;
    private final Map<String, Skill> registeredSkills;
    public skillsManager(RM_Core plugin){
        this.plugin = plugin;
        this.registeredSkills = new HashMap<>();
        for(Skill sk : Skills.values()){
            registeredSkills.put(sk.name(),sk);
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
        PlayerData playerData = plugin.getPlayerManager().getPlayerData(player.getUniqueId());
        if(playerData.getSkillLevel(skill) >= 60){return;}
        while(playerData.getSkillXp(skill) >= RM_Core.getInstance().getConfig().getInt("levelxp."+(playerData.getSkillLevel(skill)+1))){
            int curLvl = playerData.getSkillLevel(skill);
            playerData.setSkillLevel(skill,curLvl+1);
            player.sendMessage("New lvl! "+skill+": "+(curLvl+1));
        }
    }
    @Nullable
    public Skill getRegisteredSkill(String name){
        return registeredSkills.get(name);
    }
    private void addSkillXp(Player player,Skill skill,double amount){
        plugin.getPlayerManager().getPlayerData(player.getUniqueId()).addSkillXp(skill,amount);
    }
}
