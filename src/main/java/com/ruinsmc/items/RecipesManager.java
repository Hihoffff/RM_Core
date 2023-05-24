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
    private void loadRecipesDataFile(){
        plugin.getLogger().info("(RecipesLoader) Loading Recipes...");
        boolean convertRecipes = !new File(plugin.getDataFolder() + "/items/recipes", "recipes.yml").exists();

        File itemsDirectory = new File(plugin.getDataFolder() + "/items/recipes");
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
        File recipesFile = new File(plugin.getDataFolder() + "/items/recipes", "recipes.yml");

        if (!recipesFile.exists()) {
            plugin.saveResource("items/recipes/recipes.yml", false);
        }
    }

    private void loadRecipesFromFile(YamlConfiguration file,String fileName){
        plugin.getLogger().info("(RecipesLoader) Loading "+fileName+"...");
        for(String curRecipeNum : file.getConfigurationSection("recipes").getKeys(false)){
            String recipePath = "recipes."+curRecipeNum+".";
            ItemStack item = plugin.getItemsManager().getItem(file.getString(recipePath+"item"));
            if(item != null){
                String id = file.getString(recipePath+"item");
                NamespacedKey key = new NamespacedKey(plugin,id);
                ShapedRecipe recipe = new ShapedRecipe(key,item);
                recipe.shape("123","456","789");
                for(String curMaterialNum : file.getConfigurationSection(recipePath+"recipe").getKeys(false)){
                    String curMaterialPath = recipePath+"recipe."+curMaterialNum;
                    Material material = Material.getMaterial(curMaterialPath);
                    if(material != null) {
                        recipe.setIngredient(curMaterialNum.toCharArray()[0],material);
                    }
                    else{
                        plugin.getLogger().warning("(RecipesLoader) Material number "+curMaterialNum+"for recipe number "+curRecipeNum+" is not loaded!!!");
                    }
                }
                Bukkit.addRecipe(recipe);
            }
            else{
                plugin.getLogger().warning("(RecipesLoader) Recipe number "+curRecipeNum+" is not loaded!!!");
            }
        }
    }
}
