package com.ruinsmc.data;

import com.ruinsmc.RM_Core;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

public class PlayerManager {
    private final RM_Core plugin;
    private final savePlayerData savePlayerData;
    private final loadPlayerData loadPlayerData;
    private final ConcurrentHashMap<UUID,PlayerData> playerData;

    public PlayerManager(RM_Core plugin){
        this.plugin = plugin;
        this.playerData = new ConcurrentHashMap<>();
        this.savePlayerData = new savePlayerData(plugin);
        this.loadPlayerData = new loadPlayerData(plugin);
    }

    public void addPlayerData(@NotNull PlayerData playerData){
        this.playerData.put(playerData.getPlayer().getUniqueId(),playerData);
    }
    public void removePlayerData(UUID playerID){
        this.playerData.remove(playerID);
    }
    @Nullable
    public PlayerData getPlayerData(UUID playerID){
        return this.playerData.get(playerID);
    }

    @Nullable
    public ConcurrentHashMap<UUID, PlayerData> getPlayerDataMap() {
        return playerData;
    }

    public void savePlayerDataToDisk(UUID playerUUID){
        this.savePlayerData.savePlayerDataToDisk(this.getPlayerData(playerUUID));
    }
    public void saveAllPlayerDataToDisk(boolean onlyOnlinePlayers){
        this.savePlayerData.saveALlPlayerData(onlyOnlinePlayers);
    }
    public void loadPlayerDataFromDisk(Player player){
        this.loadPlayerData.loadPlayerDataFromDisk(player);
    }

}
