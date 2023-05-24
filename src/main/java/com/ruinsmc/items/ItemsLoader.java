package com.ruinsmc.items;

import com.ruinsmc.RM_Core;
import de.tr7zw.nbtapi.NBTItem;
import org.bukkit.Material;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class ItemsLoader {
    private final RM_Core plugin;
    public ItemsLoader(RM_Core plugin){
        this.plugin = plugin;
        loadItemDataFile();
        plugin.getRecipesManager().loadRecipes();
    }

    private void loadItemDataFile(){
        plugin.getLogger().info("(ItemsLoader) Loading ItemData...");
        boolean convertWeapon = !new File(plugin.getDataFolder() + "/items", "weapon.yml").exists();
        boolean convertArmor = !new File(plugin.getDataFolder() + "/items", "armor.yml").exists();
        boolean convertIngredients = !new File(plugin.getDataFolder() + "/items", "ingredients.yml").exists();

        File itemsDirectory = new File(plugin.getDataFolder() + "/items");
        if (!itemsDirectory.exists() || convertWeapon || convertArmor || convertIngredients) {
            generateDefaultItemDataFile();
        }
        if(plugin.getItemsManager().getItemDataList() != null){
            plugin.getItemsManager().getItemDataList().clear();
        }
        File[] files = itemsDirectory.listFiles();
        if (files == null) return;
        for (File itemsFile : files) {
            loadItemsFromFile(YamlConfiguration.loadConfiguration(itemsFile),itemsFile.getName());
        }

        plugin.getLogger().info("(ItemsLoader) ItemData loaded successfully!");
    }

    private void loadItemsFromFile(YamlConfiguration file,String fileName){
        plugin.getLogger().info("(ItemsLoader) Loading "+fileName+"...");
        for(String curItem : file.getConfigurationSection("items").getKeys(false)){
            String itemPath = "items."+curItem+".";
            //plugin.getLogger().info("Material: "+file.getString(itemPath+"material"));

            Material material = Material.getMaterial(file.getString(itemPath+"material"));
            String id = file.getString(itemPath+"id");
            String name = file.getString(itemPath+"name");
            String rarity = file.getString(itemPath+"rarity");
            String type = file.getString(itemPath+"type");

            if(material == null){return;}
            ItemStack item = new ItemStack(material);
            NBTItem nbtitem = new NBTItem(item);
            List lore = new ArrayList();
            for(String curStat : file.getConfigurationSection(itemPath+"stats").getKeys(false)){
                Integer statValue = file.getInt(itemPath+".stats."+curStat);
                lore.add(curStat+": "+statValue);
                nbtitem.setInteger(curStat,statValue);
            }
            lore.add(" ");
            lore.add(rarity);

            nbtitem.setString("type",type);
            nbtitem.setString("id",id);
            nbtitem.setString("rarity",rarity);
            item = nbtitem.getItem();

            item.setLore(lore);
            ItemMeta meta = item.getItemMeta();
            meta.setDisplayName(name);
            meta.setUnbreakable(true);

            item.setItemMeta(meta);
            plugin.getItemsManager().addItem(id,item);
        }
    }
    private void generateDefaultItemDataFile(){
        File weaponFile = new File(plugin.getDataFolder() + "/items", "weapon.yml");
        File armorFile = new File(plugin.getDataFolder() + "/items", "armor.yml");
        File ingredientsFile = new File(plugin.getDataFolder() + "/items", "ingredients.yml");

        if (!weaponFile.exists()) {
            plugin.saveResource("items/weapon.yml", false);
        }
        if (!armorFile.exists()) {
            plugin.saveResource("items/armor.yml", false);
        }
        if (!ingredientsFile.exists()) {
            plugin.saveResource("items/ingredients.yml", false);
        }
    }
}
