package com.ruinsmc.loot;

import com.ruinsmc.skills.Skill;
import org.jetbrains.annotations.Nullable;

public class BlockLoot {
    private final String blockName;
    private final Skill skill;
    private final double skillXP;
    private boolean isGrowable;

    private final LootPool lootPool;

    public BlockLoot(String blockName,Skill skill,Double skillXP,LootPool lootPool){
        this.blockName = blockName;
        this.skill = skill;
        this.skillXP = skillXP;
        this.isGrowable = false;
        this.lootPool = lootPool;
    }
    public double getSkillXP(){
        return this.skillXP;
    }
    public Skill getSkill(){
        return this.skill;
    }
    public String getBlockName(){
        return this.blockName;
    }
    public void setBlockGrowable(Boolean isGrowable){this.isGrowable = isGrowable;}
    public boolean isBlockGrowable(){return this.isGrowable;}
    @Nullable
    public LootPool getLootPool(){
        return this.lootPool;
    }
}
