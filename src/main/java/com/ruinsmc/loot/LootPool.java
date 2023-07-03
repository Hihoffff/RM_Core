package com.ruinsmc.loot;

import com.ruinsmc.RM_Core;
import org.bukkit.inventory.ItemStack;
import org.jetbrains.annotations.Nullable;

import java.util.Random;

public class LootPool {
    private final RM_Core plugin;
    private final boolean replaceVanillaLoot;
    private final LootItem[] lootItems;
    private final Random random;

    public LootPool(RM_Core plugin,LootItem[] lootItems, Boolean replaceVanillaLoot){
        this.plugin = plugin;
        this.lootItems = lootItems.clone();
        this.random = new Random();
        this.replaceVanillaLoot = replaceVanillaLoot;
    }


    @Nullable
    public ItemStack getRandomLoot(double luck){
        int weight = 0;
        for(LootItem lootItem : lootItems){
            weight += (int) Math.floor(lootItem.getWeight() + (lootItem.getRarity().quality() * luck));
        }
        if(weight == 0) {
            return null;
        }
        int randomValue = random.nextInt(weight);
        int curWeight = 0;
        ItemStack randomItem = null;
        for(LootItem lootItem : lootItems){
            int lootWeight = (int) Math.floor(lootItem.getWeight() + (lootItem.getRarity().quality() * luck));
            if(randomValue >= curWeight && randomValue < curWeight + lootWeight){
                randomItem = lootItem.getItem().clone();
                randomItem.add(plugin.getUtils().randomInRangeInt(lootItem.getMinCount(),lootItem.getMaxCount())-1);
                break;
            }
            curWeight += lootWeight;
        }
        return randomItem;
    }
    public boolean replaceVanillaLoot(){
        return this.replaceVanillaLoot;
    }
}
