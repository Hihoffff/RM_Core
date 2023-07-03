package com.ruinsmc.loot;

import com.ruinsmc.rarity.Rarity;
import org.bukkit.inventory.ItemStack;


public class LootItem {
    private final ItemStack item;
    private final Rarity rarity;
    private final byte minCount;
    private final byte maxCount;
    private final int weight;

    public LootItem(ItemStack item, Integer weight,Rarity rarity,Byte minCount,Byte maxCount){
        this.item = item;
        this.rarity = rarity;
        this.weight = weight;
        if(maxCount < 0){this.maxCount = 0;}
        else{this.maxCount = maxCount;}
        if(minCount < 0){this.minCount = 0;}
        else{this.minCount = minCount;}
    }
    public Rarity getRarity(){
        return this.rarity;
    }
    public ItemStack getItem(){
        return this.item;
    }
    public byte getMinCount(){
        return this.minCount;
    }
    public byte getMaxCount(){
        return this.maxCount;
    }
    public int getWeight(){
        return this.weight;
    }

}
