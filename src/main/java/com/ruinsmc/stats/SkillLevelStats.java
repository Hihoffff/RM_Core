package com.ruinsmc.stats;

import com.ruinsmc.skills.Skill;


import java.util.HashMap;

public class SkillLevelStats {
    private final Skill skill;
    private HashMap<Stat,Double> SkillStats;

    public SkillLevelStats(Skill skill, HashMap<Stat,Double> stats){
        this.skill = skill;
        this.SkillStats = new HashMap<>();
        for(Stat stat : Stats.values()){
            this.SkillStats.put(stat,stats.getOrDefault(stat,0.0d));
        }
    }
    public HashMap<Stat, Double> getStats(){//get stat which was rewarded for reaching lvl
        HashMap<Stat,Double> value = new HashMap<>();
        for(Stat stat: Stats.values()){
            value.put(stat,getStat(stat));
        }
        return value;
    }
    public void setStats(HashMap<Stat, Double> stats){
        for(Stat stat : Stats.values()){
            this.SkillStats.put(stat,stats.getOrDefault(stat,0.0d));
        }
    }
    public void setStat(Stat stat,Double value){
        this.SkillStats.put(stat,value);
    }
    public double getStat(Stat stat){
        return this.SkillStats.getOrDefault(stat,0.0d);
    }

}
