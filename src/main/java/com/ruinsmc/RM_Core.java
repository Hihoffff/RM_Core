package com.ruinsmc;

import com.ruinsmc.ActionBar.ActionBar;
import com.ruinsmc.Utils.Utils;
import com.ruinsmc.data.PlayerManager;
import com.ruinsmc.data.loadPlayerData;
import com.ruinsmc.data.savePlayerData;
import com.ruinsmc.events.EntityDeathEvent;
import com.ruinsmc.events.FoodLevelChanged;
import com.ruinsmc.skills.farming.FarmingMain;
import com.ruinsmc.skills.fishing.FishingMain;
import com.ruinsmc.skills.foraging.ForagingMain;
import com.ruinsmc.skills.mining.MiningMain;
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
    private Utils utils;
    private WisdomMain manaManager;
    private savePlayerData savePlayerData;
    private skillsManager skillsManager;

    private static RM_Core instance;
    public static RM_Core getInstance(){
        return instance;
    }
    @Override
    public void onEnable() {
        registerEvents();
        this.healthManager = new HealthMain(this);
        this.manaManager = new WisdomMain(this);
        this.savePlayerData = new savePlayerData(this);
        this.playerManager = new PlayerManager(this);
        this.skillsManager = new skillsManager(this);
        this.utils = new Utils(this);
        instance = this;
        getLogger().info("RM_Skills enabled!");


    }
    public void registerEvents(){
        getLogger().info("Registering events...");
        PluginManager pm = getServer().getPluginManager();
        //pm.registerEvents(this,this);
        pm.registerEvents(new savePlayerData(this),this);
        pm.registerEvents(new skillsManager(this),this);
        pm.registerEvents(new loadPlayerData(this),this);
        pm.registerEvents(new ActionBar(this),this);
        pm.registerEvents(new inventoryStatsUpdater(this),this);
        pm.registerEvents(new toolStatsUpdater(this),this);

        pm.registerEvents(new MiningMain(this),this);
        pm.registerEvents(new FarmingMain(this),this);
        pm.registerEvents(new FishingMain(this),this);
        pm.registerEvents(new ForagingMain(this),this);

        pm.registerEvents(new DefenseMain(this),this);
        pm.registerEvents(new HealthMain(this),this);
        pm.registerEvents(new StrengthMain(this),this);
        pm.registerEvents(new RegenerationMain(this),this);

        pm.registerEvents(new EntityDeathEvent(this),this);
        pm.registerEvents(new FoodLevelChanged(),this);
        getLogger().info("Events registered!");
    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
    }
    public PlayerManager getPlayerManager(){
        return playerManager;
    }
    public savePlayerData getSavePlayerData(){
        return savePlayerData;
    }
    public skillsManager getSkillsManager(){
        return skillsManager;
    }
    public HealthMain getHealthManager(){
        return healthManager;
    }
    public WisdomMain getManaManager(){
        return manaManager;
    }
    public Utils getUtils(){return utils;}

}
