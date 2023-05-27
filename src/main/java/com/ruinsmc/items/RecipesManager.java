package com.ruinsmc.items;

import com.ruinsmc.RM_Core;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.ShapedRecipe;

import java.io.File;

public class RecipesManager {
    private final RM_Core plugin;
    public RecipesManager(RM_Core plugin){
        this.plugin = plugin;
    }
    public void loadRecipes(){
        loadRecipesDataFile();
    }
    public void removeVanillaRecipes(){
        plugin.getLogger().info("Removing vanilla recipes...");
        Bukkit.removeRecipe(Material.IRON_SWORD.getKey());
        Bukkit.removeRecipe(Material.STONE_SWORD.getKey());
        Bukkit.removeRecipe(Material.WOODEN_SWORD.getKey());

        //Bukkit.removeRecipe(Material.ANVIL.getKey());

        Bukkit.removeRecipe(Material.IRON_HELMET.getKey());
        Bukkit.removeRecipe(Material.IRON_BOOTS.getKey());
        Bukkit.removeRecipe(Material.IRON_LEGGINGS.getKey());
        Bukkit.removeRecipe(Material.IRON_CHESTPLATE.getKey());

        Bukkit.removeRecipe(Material.LEATHER_BOOTS.getKey());
        Bukkit.removeRecipe(Material.LEATHER_LEGGINGS.getKey());
        Bukkit.removeRecipe(Material.LEATHER_CHESTPLATE.getKey());
        Bukkit.removeRecipe(Material.LEATHER_HELMET.getKey());
        plugin.getLogger().info("Vanilla recipes were removed.");
    }
    private void loadRecipesDataFile(){
        plugin.getLogger().info("(RecipesLoader) Loading Recipes...");
        boolean convertRecipes = !new File(plugin.getDataFolder() + "/recipes", "recipes.yml").exists();

        File itemsDirectory = new File(plugin.getDataFolder() + "/recipes");
        if (!itemsDirectory.exists() || convertRecipes) {
            generateDefaultItemDataFile();
        }
        plugin.getItemsManager().getItemDataList().clear();
        File[] files = itemsDirectory.listFiles();
        if (files == null) return;
        for (File itemsFile : files) {
            loadRecipesFromFile(YamlConfiguration.loadConfiguration(itemsFile),itemsFile.getName());
        }

        plugin.getLogger().info("(RecipesLoader) RecipesData loaded successfully!");
    }
    private void generateDefaultItemDataFile(){
        File recipesFile = new File(plugin.getDataFolder() + "/recipes", "recipes.yml");

        if (!recipesFile.exists()) {
            plugin.saveResource("recipes/recipes.yml", false);
        }
    }

    private void loadRecipesFromFile(YamlConfiguration file,String fileName){
        plugin.getLogger().info("(RecipesLoader) Loading "+fileName+"...");
        for(String curRecipeNum : file.getConfigurationSection("recipes").getKeys(false)){
            String recipePath = "recipes."+curRecipeNum+".";
            String id = file.getString(recipePath+"item");
            ItemStack item = plugin.getItemsManager().getItem(id);
            if(item == null){
                plugin.getLogger().warning("(RecipesLoader) Item for recipe number "+curRecipeNum+" is not loaded!!! Searched itemID: <"+id+">");
                continue;
            }

            NamespacedKey key = new NamespacedKey(plugin,id);
            ShapedRecipe recipe = new ShapedRecipe(key,item);
            recipe.shape("123","456","789");
            for(String curMaterialNum : file.getConfigurationSection(recipePath+"recipe").getKeys(false)){
                String curMaterialPath = recipePath+"recipe."+curMaterialNum;
                Material material = Material.getMaterial(file.getString(curMaterialPath));
                if(material != null) {
                    recipe.setIngredient(curMaterialNum.toCharArray()[0],material);
                }
                else{
                    plugin.getLogger().warning("(RecipesLoader) Material number "+curMaterialNum+"for recipe number "+curRecipeNum+" is not loaded!!!");
                    continue;
                }
            }
            Bukkit.addRecipe(recipe);
        }
    }
}
