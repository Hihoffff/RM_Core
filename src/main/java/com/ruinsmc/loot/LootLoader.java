package com.ruinsmc.loot;

import com.ruinsmc.RM_Core;
import com.ruinsmc.skills.Skill;
import org.bukkit.configuration.file.YamlConfiguration;



import java.io.File;


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
                LootStack lootStack = new LootStack(); //coming soon

                if(skill == null || blockName == null || skillXP == null || lootStack == null){
                    plugin.getLogger().warning("(LootLoader) Error while loading blockLoot for "+lootName);
                    continue;
                }
                BlockLoot blockLoot = new BlockLoot(blockName,skill,skillXP,lootStack);
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
                LootStack lootStack = new LootStack(); //coming soon

                if(skill == null || mobName == null || skillXP == null || lootStack == null){
                    plugin.getLogger().warning("(LootLoader) Error while loading mobLoot for "+lootName);
                    continue;
                }
                plugin.getLootManager().addMobLoot(new MobLoot(mobName,skill,skillXP,lootStack));
            }

        }

        plugin.getLogger().info("(LootLoader) LootData loaded successfully!");
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
