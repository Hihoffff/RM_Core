package com.ruinsmc;

import com.ruinsmc.ActionBar.ActionBar;
import com.ruinsmc.Utils.Utils;
import com.ruinsmc.commands.adminCommands;
import com.ruinsmc.data.PlayerManager;
import com.ruinsmc.data.loadPlayerData;
import com.ruinsmc.data.savePlayerData;
import com.ruinsmc.events.FoodLevelChanged;
import com.ruinsmc.items.ItemsLoader;
import com.ruinsmc.items.ItemsManager;
import com.ruinsmc.items.RecipesManager;
import com.ruinsmc.loot.LootLoader;
import com.ruinsmc.loot.LootManager;
import com.ruinsmc.loot.handlers.LootHandler;
import com.ruinsmc.skills.fishing.FishingMain;
import com.ruinsmc.skills.skillsManager;
import com.ruinsmc.stats.defense.DefenseMain;
import com.ruinsmc.stats.health.HealthMain;
import com.ruinsmc.stats.health.RegenerationMain;
import com.ruinsmc.stats.inventoryStatsUpdater;
import com.ruinsmc.stats.strength.StrengthMain;
import com.ruinsmc.stats.toolStatsUpdater;
import com.ruinsmc.stats.wisdom.WisdomMain;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;

public final class RM_Core extends JavaPlugin {

    private PlayerManager playerManager;
    private HealthMain healthManager;
    private ActionBar actionBar;
    private Utils utils;
    private ItemsManager itemsManager;
    private ItemsLoader itemsLoader;
    private RecipesManager recipesManager;
    private WisdomMain manaManager;
    private skillsManager skillsManager;
    private LootManager lootManager;
    private LootLoader lootLoader;


    @Override
    public void onEnable() {
        saveDefaultConfig();
        registerEvents();
        registerCommands();
        this.recipesManager = new RecipesManager(this);
        this.itemsManager = new ItemsManager(this);
        this.itemsLoader = new ItemsLoader(this);
        this.healthManager = new HealthMain(this);
        this.manaManager = new WisdomMain(this);
        this.playerManager = new PlayerManager(this);
        this.skillsManager = new skillsManager(this);
        this.utils = new Utils(this);
        this.lootManager = new LootManager(this);
        this.lootLoader = new LootLoader(this);
        this.actionBar = new ActionBar(this);
        getLogger().info("RM_Core enabled!");


    }
    private void registerCommands(){
        getLogger().info("Registering commands....");
        this.getCommand("admin").setExecutor(new adminCommands(this));
        getLogger().info("Commands registered!");
    }
    private void registerEvents(){
        getLogger().info("Registering events...");
        PluginManager pm = getServer().getPluginManager();

        //pm.registerEvents(new savePlayerInventory(this),this);
        //pm.registerEvents(new loadPlayerInventory(this),this);
        pm.registerEvents(new savePlayerData(this),this);
        pm.registerEvents(new loadPlayerData(this),this);
        pm.registerEvents(new ActionBar(this),this);
        pm.registerEvents(new inventoryStatsUpdater(this),this);
        pm.registerEvents(new toolStatsUpdater(this),this);
        pm.registerEvents(new LootHandler(this),this);

        pm.registerEvents(new FishingMain(this),this);

        pm.registerEvents(new DefenseMain(this),this);
        pm.registerEvents(new HealthMain(this),this);
        pm.registerEvents(new StrengthMain(this),this);
        pm.registerEvents(new RegenerationMain(this),this);

        pm.registerEvents(new FoodLevelChanged(),this);
        getLogger().info("Events registered!");
    }

    @Override
    public void onDisable() {
        getLogger().info("RM_Core disabled!");
    }
    public PlayerManager getPlayerManager(){
        return this.playerManager;
    }
    public skillsManager getSkillsManager(){
        return this.skillsManager;
    }
    public HealthMain getHealthManager(){
        return this.healthManager;
    }
    public WisdomMain getManaManager(){
        return this.manaManager;
    }
    public Utils getUtils(){return this.utils;}
    public ItemsManager getItemsManager(){return this.itemsManager;}
    public RecipesManager getRecipesManager(){return this.recipesManager;}
    public LootManager getLootManager(){return this.lootManager;}
    public ActionBar getActionBar(){return this.actionBar;}
}
