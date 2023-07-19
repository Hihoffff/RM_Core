package com.ruinsmc;

import com.ruinsmc.ActionBar.ActionBar;
import com.ruinsmc.Utils.Utils;
import com.ruinsmc.commands.playerCommands;
import com.ruinsmc.data.PlayerManager;
import com.ruinsmc.events.FoodLevelChanged;
import com.ruinsmc.items.ItemsLoader;
import com.ruinsmc.items.ItemsManager;
import com.ruinsmc.items.RecipesManager;
import com.ruinsmc.loot.LootLoader;
import com.ruinsmc.loot.LootManager;
import com.ruinsmc.loot.handlers.LootHandler;
import com.ruinsmc.scoreboard.ScoreBoardManager;
import com.ruinsmc.skills.fishing.FishingMain;
import com.ruinsmc.skills.skillsManager;
import com.ruinsmc.stats.CharacterStatsManager;
import com.ruinsmc.stats.defense.DefenseMain;
import com.ruinsmc.stats.handlers.StatsHandler;
import com.ruinsmc.stats.health.HealthMain;
import com.ruinsmc.stats.InventoryStatsManager;
import com.ruinsmc.stats.strength.StrengthMain;
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
    private CharacterStatsManager characterStatsManager;
    private InventoryStatsManager inventoryStatsManager;
    private LootManager lootManager;
    private LootLoader lootLoader;
    private ScoreBoardManager scoreBoardManager;


    @Override
    public void onEnable() {
        saveDefaultConfig();
        this.recipesManager = new RecipesManager(this);
        this.itemsManager = new ItemsManager(this);
        this.itemsLoader = new ItemsLoader(this);
        this.healthManager = new HealthMain(this);
        this.manaManager = new WisdomMain(this);
        this.playerManager = new PlayerManager(this);
        this.skillsManager = new skillsManager(this);
        this.inventoryStatsManager = new InventoryStatsManager(this);
        this.utils = new Utils(this);
        this.lootManager = new LootManager(this);
        this.lootLoader = new LootLoader(this);
        this.actionBar = new ActionBar(this);
        this.characterStatsManager = new CharacterStatsManager(this);
        this.scoreBoardManager = new ScoreBoardManager(this);

        registerEvents();
        registerCommands();
        getLogger().info("RM_Core enabled!");


    }
    private void registerCommands(){
        getLogger().info("Registering commands....");
        this.getCommand("admin").setExecutor(new playerCommands(this));
        getLogger().info("Commands registered!");
    }
    private void registerEvents(){
        getLogger().info("Registering events...");
        PluginManager pm = getServer().getPluginManager();

        //pm.registerEvents(new savePlayerInventory(this),this);
        //pm.registerEvents(new loadPlayerInventory(this),this);
        pm.registerEvents(getActionBar(),this);

        pm.registerEvents(new LootHandler(this),this);
        pm.registerEvents(new StatsHandler(this),this);

        pm.registerEvents(new FishingMain(this),this);

        pm.registerEvents(new DefenseMain(this),this);
        pm.registerEvents(new HealthMain(this),this);
        pm.registerEvents(new StrengthMain(this),this);

        pm.registerEvents(new FoodLevelChanged(),this);
        getLogger().info("Events registered!");
    }

    @Override
    public void onDisable() {
        this.getPlayerManager().saveAllPlayerDataToDisk(false);
        if(!this.getPlayerManager().getPlayerDataMap().isEmpty()){
            this.getPlayerManager().getPlayerDataMap().clear();
        }
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
    public InventoryStatsManager getInventoryStatsManager(){return this.inventoryStatsManager;}
    public CharacterStatsManager getCharacterStatsManager(){return this.characterStatsManager;}
}
