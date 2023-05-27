package com.ruinsmc.items;

import com.ruinsmc.RM_Core;
import org.bukkit.inventory.ItemStack;
import org.jetbrains.annotations.Nullable;

import java.util.concurrent.ConcurrentHashMap;

public class ItemsManager {
    private final RM_Core plugin;
    private final ConcurrentHashMap<String, ItemStack> ItemData; //all loaded items (id,ItemStack)

    public ItemsManager(RM_Core plugin){
        this.plugin = plugin;
        this.ItemData = new ConcurrentHashMap<>();
    }
    @Nullable
    public ItemStack getItem(String id){
        ItemStack item = ItemData.get(id);
        if(item != null){return item.clone();}
        else{return null;}
    }
    public boolean addItem(String id,ItemStack item){
        if(item != null){
            plugin.getLogger().info("(ItemsManager) Added item <"+id+"> with display name <"+item.getItemMeta().getDisplayName()+">.");
            ItemData.put(id,item);
            return true;
        }
        else
        {
            plugin.getLogger().warning("(ItemsManager) Item <"+id+"> is Null !!!");
            return false;
        }
    }

    public ConcurrentHashMap<String, ItemStack> getItemDataList(){
        return ItemData;
    }
}
