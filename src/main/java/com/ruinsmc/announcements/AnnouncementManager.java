package com.ruinsmc.announcements;

import com.ruinsmc.RM_Core;
import com.ruinsmc.loot.LootItem;
import com.ruinsmc.skills.Skill;
import com.ruinsmc.stats.Stat;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;

import java.util.HashMap;

public class AnnouncementManager {
    private final RM_Core plugin;
    public AnnouncementManager(RM_Core plugin){
        this.plugin = plugin;
    }
    public void sendPlayerGotLoot(Player player,LootItem lootItem){
        player.sendMessage(ChatColor.GRAY+"Вы получили "+lootItem.getRarity().color()+lootItem.getRarity().name()+" "+ChatColor.WHITE+lootItem.getItem().getItemMeta().displayName());
    }
    public void sendPlayerGotNewSkillLvl(Player player,Skill skill, Integer lvl){
        player.sendMessage(ChatColor.YELLOW+"НОВЫЙ УРОВЕНЬ НАВЫКА!");
        player.sendMessage(ChatColor.WHITE+"Навык: "+skill.color()+skill.displayName());
        player.sendMessage(ChatColor.WHITE+"Новый уровень: "+ChatColor.GOLD+lvl);
        player.sendMessage(ChatColor.GREEN+"--- Бонусы за новый уровень ---");
        HashMap<Stat,Double> stats = plugin.getCharacterStatsManager().getSkillLevelStats(skill,lvl);
        for(Stat stat : stats.keySet()){
            if(stats.get(stat) != 0.0d){
                player.sendMessage(stat.color()+stat.displayName()+": "+ChatColor.YELLOW+"+"+stats.get(stat));
            }
        }
    }
    public void sendPlayerGotNewPlayerLevel(Player player,Integer level){
        player.sendMessage(ChatColor.GOLD+"НОВЫЙ УРОВЕНЬ!");
        player.sendMessage(ChatColor.WHITE+"Текущий уровень: "+ChatColor.GOLD+level);
    }
}
