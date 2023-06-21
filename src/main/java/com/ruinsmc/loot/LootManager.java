package com.ruinsmc.loot;

import com.ruinsmc.RM_Core;
import org.jetbrains.annotations.Nullable;

import java.util.HashMap;

public class LootManager {
    private final RM_Core plugin;
    private final HashMap<String, MobLoot> MobsLoot;
    private final HashMap<String,BlockLoot> BlocksLoot;

    public LootManager(RM_Core plugin){
        this.plugin = plugin;
        this.MobsLoot = new HashMap<>();
        this.BlocksLoot = new HashMap<>();

    }
    @Nullable
    public MobLoot getMobLoot(String mobName){
        return this.MobsLoot.get(mobName);
    }
    @Nullable
    public BlockLoot getBlockLoot(String blockName){
        return this.BlocksLoot.get(blockName);
    }
    public void addMobLoot(MobLoot mobLoot){
        this.MobsLoot.put(mobLoot.getMobName(),mobLoot);
    }
    public void addBlockLoot(BlockLoot blockLoot){
        this.BlocksLoot.put(blockLoot.getBlockName(), blockLoot);
    }
    @Nullable
    public HashMap<String,MobLoot> getMobsLoot(){return this.MobsLoot;}
    @Nullable
    public HashMap<String,BlockLoot> getBlocksLoot(){
        return this.BlocksLoot;
    }

}
