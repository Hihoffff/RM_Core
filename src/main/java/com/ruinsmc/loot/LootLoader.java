package com.ruinsmc.loot;

import com.ruinsmc.RM_Core;
import com.ruinsmc.rarity.Rarities;
import com.ruinsmc.rarity.Rarity;
import com.ruinsmc.skills.Skill;
import org.bukkit.Material;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.inventory.ItemStack;
import org.jetbrains.annotations.Nullable;


import java.io.File;
import java.util.ArrayList;
import java.util.List;


public class LootLoader {
    private final RM_Core plugin;

    public LootLoader(RM_Core plugin){
        this.plugin = plugin;
        loadLoot();
    }

    private void loadLoot(){
        plugin.getLogger().info("(LootLoader) Loading ItemData...");
        boolean convertBlocks = !new File(plugin.getDataFolder() + "/loot", "blocks.yml").exists();
        boolean convertMobs = !new File(plugin.getDataFolder() + "/loot", "mobs.yml").exists();

        File lootDirectory = new File(plugin.getDataFolder() + "/loot");
        if (!lootDirectory.exists() || convertBlocks || convertMobs) {
            generateDefaultItemDataFile();
        }

        if(plugin.getLootManager().getBlocksLoot() != null){
            plugin.getLootManager().getBlocksLoot().clear();
        }
        File blocksLootFile = new File(plugin.getDataFolder() + "/loot", "blocks.yml");
        if(blocksLootFile != null){//blocks
            YamlConfiguration config = YamlConfiguration.loadConfiguration(blocksLootFile);
            for(String lootName : config.getConfigurationSection("list").getKeys(false)){
                Double skillXP = config.getDouble("list."+lootName+".skillXP");
                String blockName = config.getString("list."+lootName+".blockName");
                Skill skill = plugin.getSkillsManager().getRegisteredSkill(config.getString("list."+lootName+".skill"));
                LootPool lootPool = loadLootPool(config,"list."+lootName);

                if(skill == null || blockName == null || skillXP == null){
                    plugin.getLogger().warning("(LootLoader) Error while loading blockLoot for "+lootName);
                    continue;
                }
                BlockLoot blockLoot = new BlockLoot(blockName,skill,skillXP,lootPool);
                Boolean isBlockGrowable = config.getBoolean("list."+lootName+".isGrowable");
                if(isBlockGrowable != null){
                    if(isBlockGrowable){
                        blockLoot.setBlockGrowable(true);
                    }
                }

                plugin.getLootManager().addBlockLoot(blockLoot);
            }
        }
        if(plugin.getLootManager().getMobsLoot() != null){
            plugin.getLootManager().getMobsLoot().clear();
        }
        File mobsLootFile = new File(plugin.getDataFolder() + "/loot", "mobs.yml");
        if(mobsLootFile != null){//mobs
            YamlConfiguration config = YamlConfiguration.loadConfiguration(mobsLootFile);
            for(String lootName : config.getConfigurationSection("list").getKeys(false)){
                Double skillXP = config.getDouble("list."+lootName+".skillXP");
                String mobName = config.getString("list."+lootName+".mobName");
                Skill skill = plugin.getSkillsManager().getRegisteredSkill(config.getString("list."+lootName+".skill"));
                LootPool lootPool = loadLootPool(config,"list."+lootName);
                if(skill == null || mobName == null || skillXP == null){
                    plugin.getLogger().warning("(LootLoader) Error while loading mobLoot for "+lootName);
                    continue;
                }

                plugin.getLootManager().addMobLoot(new MobLoot(mobName,skill,skillXP,lootPool));
            }

        }

        plugin.getLogger().info("(LootLoader) LootData loaded successfully!");
    }
    @Nullable
    private LootPool loadLootPool(YamlConfiguration config,String path){
        try{
            String lootPath = path+".loot";
            ConfigurationSection configSect = config.getConfigurationSection(lootPath);
            if(configSect == null){
                return null;
            }
            List<LootItem> lootItemList = new ArrayList<>();
            Boolean replaceVanillaLoot = config.getBoolean(path+".replaceVanillaLoot",false);
            for(String lootNum : configSect.getKeys(false)){
                Rarity rarity = Rarities.valueOf(config.getString(lootPath+"."+lootNum+".rarity",""));
                Byte minCount = (byte) config.getInt(lootPath+"."+lootNum+".minCount");
                Byte maxCount = (byte) config.getInt(lootPath+"."+lootNum+".maxCount");
                Integer weight = config.getInt(lootPath+"."+lootNum+".weight");
                Boolean isItemCustom = config.getBoolean(lootPath+"."+lootNum+".isCustom",false);
                String id = config.getString(lootPath+"."+lootNum+".id");
                ItemStack item = null;
                if(isItemCustom){
                    item = plugin.getItemsManager().getItem(id);
                }
                else {
                    item = new ItemStack(Material.getMaterial(id),1);
                }
                if(maxCount == null || minCount == null || rarity == null || weight == null || item == null) {
                    plugin.getLogger().warning("(LootLoader) Error while loading lootNum "+lootNum+" in loot path <"+lootPath+"> !!!");
                    continue;
                }
                LootItem lootItem = new LootItem(item,weight,rarity,minCount,maxCount);
                lootItemList.add(lootItem);
            }
            if(lootItemList.isEmpty()){return null;}
            LootItem[] lootItems = new LootItem[lootItemList.size()];
            for(int i = 0; i < lootItemList.size(); i++){
                lootItems[i] = lootItemList.get(i);
            }
            plugin.getLogger().info("(LootLoader) loaded lootPool with path "+ path);
            return new LootPool(plugin,lootItems,replaceVanillaLoot);
        }catch (Exception ex){
            ex.printStackTrace();
            return null;
        }
    }
    private void generateDefaultItemDataFile(){
        File blocksFile = new File(plugin.getDataFolder() + "/loot", "blocks.yml");
        File mobsFile = new File(plugin.getDataFolder() + "/loot", "mobs.yml");

        if (!blocksFile.exists()) {
            plugin.saveResource("loot/blocks.yml", false);
        }
        if (!mobsFile.exists()) {
            plugin.saveResource("loot/mobs.yml", false);
        }
    }
}
