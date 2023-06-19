package com.ruinsmc.ActionBar;

import com.ruinsmc.RM_Core;
import com.ruinsmc.customevents.XpGainEvent;
import com.ruinsmc.data.PlayerData;
import com.ruinsmc.skills.Skill;
import com.ruinsmc.stats.Stats;
import net.md_5.bungee.api.ChatMessageType;
import net.md_5.bungee.api.chat.TextComponent;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerQuitEvent;

import java.util.HashMap;
import java.util.UUID;

public class ActionBar implements Listener {
    private final RM_Core plugin;
    private HashMap<UUID,String> HMXpAction = new HashMap<UUID,String>();

    public ActionBar(RM_Core plugin){
        this.plugin = plugin;
        startActionBarUpdate();
    }
    @EventHandler
    public void onPlayerQuit(PlayerQuitEvent e){
        HMXpAction.remove(e.getPlayer().getUniqueId());
    }


    @EventHandler
    public void onXpGainEvent(XpGainEvent e){
        sendXpActionBar(e.getPlayer(),e.getSkill(),e.getAmount());
    }
    public void startActionBarUpdate(){
        Integer delay = plugin.getConfig().getInt("player.ActionBarDelay");
        Bukkit.getScheduler().scheduleSyncRepeatingTask(plugin, () -> {
            for(Player player : Bukkit.getOnlinePlayers()){
                sendActionBar(player);
            }
        },1,delay);
    }

    private void sendActionBar(Player player){ //sending action bar for every player
        PlayerData playerData = plugin.getPlayerManager().getPlayerData(player.getUniqueId());
        if(playerData == null){return;}
        int curHealth = (int) plugin.getHealthManager().getHealth(player);
        int maxHealth = (int) plugin.getHealthManager().getMaxHealth(player);
        int curMana = (int) plugin.getManaManager().getPlayerMana(player);
        int maxMana = (int) plugin.getManaManager().getPlayerMaxMana(player);
        String xpshow = HMXpAction.get(player.getUniqueId());
        String text = (ChatColor.RED+""+curHealth+"/"+maxHealth+"❤"+"      "+ChatColor.YELLOW+""+xpshow+"      "+ChatColor.AQUA+""+curMana+"/"+maxMana+"◉");
        player.sendMessage(ChatMessageType.ACTION_BAR, new TextComponent(text));
        HMXpAction.put(player.getUniqueId(),"     "+ChatColor.GREEN+""+playerData.getStatLevel(Stats.DEFENSE)+"☗     ");
    }

    private void sendXpActionBar(Player player, Skill skill, Double adedXP){
        PlayerData playerData = plugin.getPlayerManager().getPlayerData(player.getUniqueId());
        int nexLvLXp = plugin.getSkillsManager().getXPforLevel(playerData.getSkillLevel(skill)+1);
        double curXp = playerData.getSkillXp(skill);
        int procent = (int) Math.floor(curXp/nexLvLXp*100);
        HMXpAction.put(player.getUniqueId(),"+"+adedXP+" "+skill+" ❖("+procent+"%)❖");
    }
}
