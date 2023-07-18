package com.ruinsmc.data;

import com.ruinsmc.RM_Core;

import com.ruinsmc.skills.Skill;
import com.ruinsmc.stats.Stat;
import org.bukkit.entity.Player;
import java.util.HashMap;


public class PlayerData {
    private final Player player;
    private final RM_Core plugin;
    private long money;

    private final HashMap<Skill, Double> skillXp;
    private final HashMap<Skill, Integer> skillLevels;

    //Player stats
    private final HashMap<Stat, Double> inventoryStats;
    private final HashMap<Stat,Double> toolStats;
    private final  HashMap<Stat,Double> characterStats;
    private double curMana;


    public PlayerData(Player player, RM_Core plugin){
        this.player = player;
        this.plugin = plugin;
        this.skillXp = new HashMap<>();
        this.skillLevels = new HashMap<>();
        this.inventoryStats = new HashMap<>();
        this.toolStats = new HashMap<>();
        this.curMana = plugin.getConfig().getDouble("player.baseMana");
        this.characterStats = new HashMap<>();
        this.money = 0;
    }
    public RM_Core getPlugin(){
        return plugin;
    }
    public Player getPlayer(){
        return player;
    }
    public double getSkillXp(Skill skill){
        return skillXp.getOrDefault(skill,0.0d);
    }
    public int getSkillLevel(Skill skill){
        return skillLevels.getOrDefault(skill,0);
    }
    public void addSkillXp(Skill skill, Double amount) {
        skillXp.merge(skill, amount, Double::sum);
    }
    public void setSkillXp(Skill skill, Double amount) {
        skillXp.put(skill,amount);
    }
    public void setSkillLevel(Skill skill, Integer amount) {
        skillLevels.put(skill,amount);
    }
    public double getStatLevel(Stat stat) {
        return inventoryStats.getOrDefault(stat, 0.0d) + toolStats.getOrDefault(stat,0.0d) + characterStats.getOrDefault(stat,0.0d);
    }

    public void setInventoryStats(Stat stat,Double amount){
        inventoryStats.put(stat,amount);
    }
    public void setToolStats(Stat stat,Double amount){
        toolStats.put(stat,amount);
    }
    public void setCharacterStat(Stat stat, Double amount){characterStats.put(stat,amount);}
    public double getMana(){
        return curMana;
    }
    public void setMana(Double value){
        curMana = value;
    }
    public long getMoney(){
        return this.money;
    }
    public void setMoney(Long money){
        this.money = money;
    }
}
