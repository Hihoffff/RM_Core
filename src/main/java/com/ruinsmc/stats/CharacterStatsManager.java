package com.ruinsmc.stats;

import com.ruinsmc.RM_Core;
import com.ruinsmc.data.PlayerData;
import com.ruinsmc.skills.Skill;
import com.ruinsmc.skills.Skills;
import org.bukkit.configuration.Configuration;
import org.bukkit.entity.Player;


import java.util.HashMap;

public class CharacterStatsManager {
    private final RM_Core plugin;
    private final HashMap<Skill, SkillLevelStats[]> SkillLvlCharacterStats; //
    public CharacterStatsManager(RM_Core plugin){
        this.plugin = plugin;
        this.SkillLvlCharacterStats = new HashMap<>();
        loadDataFromConfig();
    }
    private void loadDataFromConfig(){
        plugin.getLogger().info("(CharacterStatsManager) Loading skill stats...");
        HashMap<Skill, SkillLevelStats[]> SkillLvlCharacterStatsPerLvl = new HashMap<>();
        int maxLvl = plugin.getSkillsManager().getMaxLevel();
        HashMap<Stat,Double> defaultValue = new HashMap<>();
        for (Stat stat : Stats.values()){
            defaultValue.put(stat,0.0d);
        }
        for(Skill skill : Skills.values()){
            SkillLevelStats[] skillLevelStats = new SkillLevelStats[maxLvl];
            for(int lvl = 1; lvl <= maxLvl;lvl++){
                skillLevelStats[lvl-1] = new SkillLevelStats(skill,defaultValue);
            }
            SkillLvlCharacterStatsPerLvl.put(skill,skillLevelStats);
            SkillLvlCharacterStats.put(skill,skillLevelStats);
        }

        Configuration config = plugin.getConfig();
        for(String skillName : config.getConfigurationSection("SkillLvlRewards").getKeys(false)){
            Skill skill = plugin.getSkillsManager().getRegisteredSkill(skillName);
            SkillLevelStats[] skillLevelStats = SkillLvlCharacterStatsPerLvl.get(skill).clone();
            if(skill == null){plugin.getLogger().warning("(CharacterStatsManager) Skill "+skillName+" is not exist!");continue;}
            for(String range : config.getConfigurationSection("SkillLvlRewards."+skillName).getKeys(false)){
                if(range == null || range == "" || range == " "){continue;}
                boolean isRange = range.contains("-");
                int from = 0;
                int to = 0;
                if(isRange){
                    int centerIndex = range.indexOf("-");
                    from = Integer.parseInt(range.substring(0,centerIndex),10);
                    to = Integer.parseInt(range.substring(centerIndex+1),10);
                }else{
                    from = Integer.parseInt(range);
                    to = from;
                }
                if(from <= 0 || from > plugin.getSkillsManager().getMaxLevel() || from > to || to > plugin.getSkillsManager().getMaxLevel() || to <= 0){continue;}
                for(int lvl = from; lvl <= to; lvl++){
                    for(String statName : config.getConfigurationSection("SkillLvlRewards."+skillName+"."+range).getKeys(false)){
                        Stat stat = Stats.valueOf(statName);
                        if(stat == null){plugin.getLogger().warning("(CharacterStatsManager) Stat "+statName+" in range "+range+" in skill "+skillName+" is not exist!"); ;continue;}
                        Double statCount = config.getDouble("SkillLvlRewards."+skillName+"."+range+"."+statName);
                        if(statCount == null){plugin.getLogger().warning("(CharacterStatsManager) Stat "+statName+" in range "+range+" in skill "+skillName+" is null!"); ;continue;}
                        double previousCount = skillLevelStats[lvl-1].getStat(stat);
                        skillLevelStats[lvl - 1].setStat(stat,previousCount + statCount);
                    }
                }

            }
            SkillLvlCharacterStatsPerLvl.put(skill,skillLevelStats);
        }
        for(Skill skill : Skills.values()){
            SkillLevelStats[] skillLevelStats = new SkillLevelStats[maxLvl];

            for(int lvl = 1; lvl <= maxLvl;lvl++){
                if(lvl== 1){
                    skillLevelStats[lvl-1] = new SkillLevelStats(skill,SkillLvlCharacterStatsPerLvl.get(skill)[lvl-1].getStats());
                    plugin.getLogger().info(""+SkillLvlCharacterStatsPerLvl.get(skill)[lvl-1].getStats().toString());
                    continue;
                }
                HashMap<Stat,Double> previousStats = SkillLvlCharacterStats.get(skill)[lvl-2].getStats();
                for(Stat stat : Stats.values()){
                    previousStats.put(stat,previousStats.getOrDefault(stat,0.0)+SkillLvlCharacterStatsPerLvl.get(skill)[lvl-1].getStat(stat));
                }
                skillLevelStats[lvl-1] = new SkillLevelStats(skill,previousStats);
            }
            SkillLvlCharacterStats.put(skill,skillLevelStats);
        }
    }

    private double getSkillLevelStat(Skill skill,Stat stat,Integer level){
        if(level <= 0){return 0.0d;}
        return SkillLvlCharacterStats.get(skill)[level-1].getStat(stat);
    }
    public void updatePlayerCharacterStats(Player player){
        PlayerData playerData = plugin.getPlayerManager().getPlayerData(player.getUniqueId());
        if(playerData == null){return;}
        for (Stat stat : Stats.values()){
            double amount = 0.0d;
            for(Skill skill : Skills.values()){
               amount += getSkillLevelStat(skill,stat,playerData.getSkillLevel(skill));
            }
            plugin.getLogger().info("(CharacterStatsManager) Character stat "+stat.name()+" is "+amount);
            playerData.setCharacterStat(stat,amount);
        }
    }

    public HashMap<Stat,Double> getSkillLevelStats(Skill skill, Integer level){
        return this.SkillLvlCharacterStats.get(skill)[level - 1].getStats();
    }


}
