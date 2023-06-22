package com.ruinsmc.loot;

import com.ruinsmc.skills.Skill;

public class BlockLoot {
    private final String blockName;
    private final Skill skill;
    private final double skillXP;
    private final LootStack lootStack;
    private boolean isGrowable;

    public BlockLoot(String blockName,Skill skill,Double skillXP,LootStack lootStack){
        this.blockName = blockName;
        this.skill = skill;
        this.skillXP = skillXP;
        this.lootStack = lootStack;
        this.isGrowable = false;
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
    public LootStack getLootStack(){
        return this.lootStack;
    }
    public void setBlockGrowable(Boolean isGrowable){this.isGrowable = isGrowable;}
    public boolean isBlockGrowable(){return this.isGrowable;}
}
