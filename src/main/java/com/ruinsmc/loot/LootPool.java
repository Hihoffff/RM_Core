package com.ruinsmc.loot;

import com.ruinsmc.RM_Core;
import org.bukkit.inventory.ItemStack;
import org.jetbrains.annotations.Nullable;

public class LootPool {
    private final RM_Core plugin;
    private final LootItem[] lootItems;

    public LootPool(RM_Core plugin,LootItem[] lootItems){
        this.plugin = plugin;
        this.lootItems = lootItems.clone();
    }

    @Nullable
    public ItemStack getRandomLoot(Double luck){return null;}
}
