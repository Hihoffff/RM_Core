package com.ruinsmc.loot;

import com.ruinsmc.skills.Skill;
import org.jetbrains.annotations.Nullable;

public class MobLoot {
    private final String mobName;
    private final Skill skill;
    private final double skillXP;
    private final LootPool lootPool;

    public MobLoot(String mobName,Skill skill,Double skillXP,LootPool lootPool){
        this.mobName = mobName;
        this.skill = skill;
        this.skillXP = skillXP;
        this.lootPool = lootPool;
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
    @Nullable
    public LootPool getLootPool(){
        return this.lootPool;
    }
}
