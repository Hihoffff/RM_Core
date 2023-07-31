package com.ruinsmc.PlayerLevel;

import com.ruinsmc.RM_Core;
import com.ruinsmc.data.PlayerData;
import org.bukkit.configuration.Configuration;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.entity.Player;

public class PlayerLevelManager {
    private final RM_Core plugin;
    private final Integer[] XPforLevel; //how many xp player need to get new PlayerLevel
    private final int maxLevel;

    public PlayerLevelManager(RM_Core plugin){
        this.plugin = plugin;

        Configuration config = plugin.getConfig();

        ConfigurationSection XpSection = config.getConfigurationSection("XP_For_PlayerLevel");
        this.XPforLevel = new Integer[XpSection.getKeys(false).size()];
        for(String lvlStr : XpSection.getKeys(false)){
            Integer lvl = Integer.parseInt(lvlStr);
            Integer xp = config.getInt("XP_For_PlayerLevel."+lvlStr);
            if(lvl <= 0){
                continue;
            }
            this.XPforLevel[lvl - 1] = xp;
        }
        this.maxLevel = this.XPforLevel.length;
    }
    public int getMaxPlayerLVL(){
        return this.maxLevel;
    }
    public double getXPforPlayerLVL(Integer level){
        if(level > getMaxPlayerLVL()){
            return Double.MAX_VALUE;
        }
        return this.XPforLevel[level-1];
    }
    public void checkPlayerLevel(Player player){ //check if new player lvl
        PlayerData playerData = plugin.getPlayerManager().getPlayerData(player.getUniqueId());
        if(playerData == null){return;}
        while((playerData.getPlayerLvL() >= getXPforPlayerLVL(playerData.getPlayerLvL() + 1)) && (playerData.getPlayerLvL() < getMaxPlayerLVL())){
            playerData.setPlayerLvlXP(playerData.getPlayerLvLXP()-getXPforPlayerLVL(playerData.getPlayerLvL()+1));
            playerData.setPlayerLvL(playerData.getPlayerLvL()+1);
            //plugin.getAnnouncementManager().sendPlayerGotNewSkillLvl(player,skill,playerData.getSkillLevel(skill));
        }
    }
    public void addPlayerLevelXP(Player player,Double xp){
        PlayerData playerData = plugin.getPlayerManager().getPlayerData(player.getUniqueId());
        if(playerData == null){return;}
        playerData.setPlayerLvlXP(playerData.getPlayerLvLXP()+xp);
        checkPlayerLevel(player);
    }

}
