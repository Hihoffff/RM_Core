package com.ruinsmc.scoreboard;

import com.ruinsmc.RM_Core;
import com.ruinsmc.data.PlayerData;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.scheduler.BukkitRunnable;


import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

public class ScoreBoardManager implements Listener {

    private final RM_Core plugin;
    private final DateFormat dateFormat;
    private final Date date;
    public ScoreBoardManager(RM_Core plugin){
        this.plugin = plugin;
        this.date = new Date();
        this.dateFormat = new SimpleDateFormat("yyyy/MM/dd");

        plugin.getServer().getPluginManager().registerEvents(this,plugin);
        new BukkitRunnable(){
            public void run(){
                for(Player player : plugin.getServer().getOnlinePlayers()){
                    updateSideScoreBoard(player);
                }
            }
        }.runTaskTimer(plugin,20L,20L);
    }

    private void updateSideScoreBoard(Player player){
        if(sideScoreBoard.hasScore(player)){
            PlayerData playerData = plugin.getPlayerManager().getPlayerData(player.getUniqueId());
            if(playerData == null){return;}
            sideScoreBoard scoreBoard = sideScoreBoard.getByPlayer(player);
            scoreBoard.setSlot(2,"&fМонет: &e"+playerData.getMoney());
            scoreBoard.setSlot(4,"&fУровень персонажа: &71");
        }
    }
    private void createSideScoreBord(Player player){
        sideScoreBoard helper = sideScoreBoard.createScore(player);
        helper.setTitle("&aRuinsMC");
        helper.setSlot(5, "&7"+dateFormat.format(date));
        helper.setSlot(4, "&fУровень персонажа: &7загрузка...");
        helper.setSlot(3, "");
        helper.setSlot(2, "&fМонет: &7загрузка...");
        helper.setSlot(1, "&ejoin.ruinsmc.ru");
    }

    @EventHandler
    public void onPlayerJoin(PlayerJoinEvent e){
        createSideScoreBord(e.getPlayer());
    }
    @EventHandler
    public void onPlayerQuit(PlayerQuitEvent e){
        Player player = e.getPlayer();
        if(sideScoreBoard.hasScore(player)){
            sideScoreBoard.removeScore(player);
        }
    }

}

