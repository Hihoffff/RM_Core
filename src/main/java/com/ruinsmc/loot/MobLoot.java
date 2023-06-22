package com.ruinsmc.loot;

import com.ruinsmc.skills.Skill;

public class MobLoot {
    private final String mobName;
    private final Skill skill;
    private final double skillXP;
    private final LootStack lootStack;

    public MobLoot(String mobName,Skill skill,Double skillXP,LootStack lootStack){
        this.mobName = mobName;
        this.skill = skill;
        this.skillXP = skillXP;
        this.lootStack = lootStack;
    }
    public double getSkillXP(){
        return this.skillXP;
    }
    public Skill getSkill(){
        return this.skill;
    }
    public String getMobName(){
        return this.mobName;
    }
    public LootStack getLootStack(){
        return this.lootStack;
    }

}
