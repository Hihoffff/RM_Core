package com.ruinsmc.Proxy;

import com.google.common.io.ByteArrayDataOutput;
import com.google.common.io.ByteStreams;
import com.ruinsmc.RM_Core;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;


import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

public class ProxyManager {
    private final RM_Core plugin;
    private final Set<UUID> playersInSending;

    public ProxyManager(RM_Core plugin){
        this.plugin = plugin;
        this.playersInSending = new HashSet<>();
    }

    public void sendPlayerToServer(String serverName, Player player){
        if(!player.isOnline()){return;}
        if(playersInSending.contains(player.getUniqueId())){player.sendMessage(ChatColor.GREEN+"Вы в процессе телепортации,подождите."); return;}
        this.playersInSending.add(player.getUniqueId());
        new BukkitRunnable(){
            public void run(){
                if(plugin.getPlayerManager().savePlayerDataToDisk(player.getUniqueId())){
                    ByteArrayDataOutput out = ByteStreams.newDataOutput();
                    out.writeUTF("Connect");
                    out.writeUTF(serverName);
                    player.sendPluginMessage(plugin,"BungeeCord", out.toByteArray());
                }
                else {
                    player.sendMessage(ChatColor.RED+"Возникла ошибка во время сохранения данных, попробуйте позже.");
                }
                playersInSending.remove(player.getUniqueId());
            }
        }.runTaskAsynchronously(plugin);

    }
}
