package com.ruinsmc.stats;

import com.ruinsmc.skills.Skill;
import com.ruinsmc.skills.Skills;

import java.util.HashMap;

public class SkillLevelStats {
    private final Skill skill;
    private HashMap<Stat,Double> Stats;

    public SkillLevelStats(Skill skill, HashMap<Stat,Double> stats){
        this.skill = skill;
        this.Stats = stats;
    }
    public HashMap<Stat, Double> getStats(){//get stat which was rewarded for reaching lvl
        return this.Stats;
    }
    public void setStats(HashMap<Stat, Double> stats){
        this.Stats = stats;
    }
    public void setStat(Stat stat,Double value){
        this.Stats.put(stat,value);
    }
    public double getStat(Stat stat){
        return this.Stats.getOrDefault(stat,0.0);
    }

}
