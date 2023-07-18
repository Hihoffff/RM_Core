package com.ruinsmc.menus;

import com.ruinsmc.RM_Core;
import com.ruinsmc.data.PlayerData;
import com.ruinsmc.skills.Skill;
import com.ruinsmc.skills.Skills;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.InventoryView;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;

import java.util.List;

public class menusManager {
    private final RM_Core  plugin;
    private final IconMenu skillsMenu;
    public menusManager(RM_Core plugin){
        this.plugin = plugin;
        this.skillsMenu = new IconMenu("Навыки", 1, new IconMenu.onClick() {
            @Override
            public boolean menuClicked(Player clicker, IconMenu menu, IconMenu.Row row, int slot, ItemStack item) {
                return true;
            }
        }, new IconMenu.onOpen() {
            @Override
            public void menuOpened(Player opener, InventoryView menu) {
                PlayerData playerData = plugin.getPlayerManager().getPlayerData(opener.getUniqueId());
                if(playerData == null){return;}
                menu.getItem(0).setLore(List.of("",ChatColor.translateAlternateColorCodes('&',"&7LVL: &e"+playerData.getSkillLevel(Skills.MINING)),ChatColor.translateAlternateColorCodes('&',"&7XP: &e"+playerData.getSkillXp(Skills.MINING)+"/"+plugin.getSkillsManager().getXPforLevel(playerData.getSkillLevel(Skills.MINING)))));
                menu.getItem(1).setLore(List.of("",ChatColor.translateAlternateColorCodes('&',"&7LVL: &e"+playerData.getSkillLevel(Skills.COMBAT)),ChatColor.translateAlternateColorCodes('&',"&7XP: &e"+playerData.getSkillXp(Skills.MINING)+"/"+plugin.getSkillsManager().getXPforLevel(playerData.getSkillLevel(Skills.COMBAT)))));


            }
        });
        this.skillsMenu.addButton(skillsMenu.getRow(0),0,new ItemStack(Material.DIAMOND_PICKAXE),"Шахтерство");
        this.skillsMenu.addButton(skillsMenu.getRow(0),1,new ItemStack(Material.NETHERITE_SWORD),"Сражение");
        this.skillsMenu.addButton(skillsMenu.getRow(0),2,new ItemStack(Material.GOLDEN_HOE),"Фермерство");
        this.skillsMenu.addButton(skillsMenu.getRow(0),3,new ItemStack(Material.IRON_AXE),"Лесорубство");
        this.skillsMenu.addButton(skillsMenu.getRow(0),4,new ItemStack(Material.FISHING_ROD),"Рыболовство");
        this.skillsMenu.addButton(skillsMenu.getRow(0),5,new ItemStack(Material.BREWING_STAND),"Алхимия");
        this.skillsMenu.addButton(skillsMenu.getRow(0),6,new ItemStack(Material.ENCHANTING_TABLE),"Зачарование");
        this.skillsMenu.addButton(skillsMenu.getRow(0),7,new ItemStack(Material.CRAFTING_TABLE),"Крафтинг");
        this.skillsMenu.addButton(skillsMenu.getRow(0),8,new ItemStack(Material.GRAY_STAINED_GLASS_PANE),"");


    }
    public IconMenu getSkillsMenu(){
        return this.skillsMenu;
    }
}

