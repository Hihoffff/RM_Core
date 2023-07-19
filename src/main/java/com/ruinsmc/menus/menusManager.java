package com.ruinsmc.menus;

import com.ruinsmc.RM_Core;
import com.ruinsmc.data.PlayerData;
import com.ruinsmc.skills.Skills;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.InventoryView;
import org.bukkit.inventory.ItemStack;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;


public class menusManager {
    private final String serverType;
    private final RM_Core  plugin;
    private final IconMenu skillsMenu;
    private final IconMenu mainMenu;
    public menusManager(RM_Core plugin){
        this.plugin = plugin;
        this.serverType = plugin.getConfig().getString("server.type");
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
                menu.getItem(0).setLore(Arrays.asList("",ChatColor.translateAlternateColorCodes('&',"&7LVL: &e"+playerData.getSkillLevel(Skills.MINING)),ChatColor.translateAlternateColorCodes('&',"&7XP: &e"+playerData.getSkillXp(Skills.MINING)+"/"+plugin.getSkillsManager().getXPforLevel(playerData.getSkillLevel(Skills.MINING)))));
                menu.getItem(1).setLore(Arrays.asList("",ChatColor.translateAlternateColorCodes('&',"&7LVL: &e"+playerData.getSkillLevel(Skills.COMBAT)),ChatColor.translateAlternateColorCodes('&',"&7XP: &e"+playerData.getSkillXp(Skills.COMBAT)+"/"+plugin.getSkillsManager().getXPforLevel(playerData.getSkillLevel(Skills.COMBAT)))));
                menu.getItem(2).setLore(Arrays.asList("",ChatColor.translateAlternateColorCodes('&',"&7LVL: &e"+playerData.getSkillLevel(Skills.FARMING)),ChatColor.translateAlternateColorCodes('&',"&7XP: &e"+playerData.getSkillXp(Skills.FARMING)+"/"+plugin.getSkillsManager().getXPforLevel(playerData.getSkillLevel(Skills.FARMING)))));
                menu.getItem(3).setLore(Arrays.asList("",ChatColor.translateAlternateColorCodes('&',"&7LVL: &e"+playerData.getSkillLevel(Skills.FORAGING)),ChatColor.translateAlternateColorCodes('&',"&7XP: &e"+playerData.getSkillXp(Skills.FORAGING)+"/"+plugin.getSkillsManager().getXPforLevel(playerData.getSkillLevel(Skills.FORAGING)))));
                menu.getItem(4).setLore(Arrays.asList("",ChatColor.translateAlternateColorCodes('&',"&7LVL: &e"+playerData.getSkillLevel(Skills.FISHING)),ChatColor.translateAlternateColorCodes('&',"&7XP: &e"+playerData.getSkillXp(Skills.FISHING)+"/"+plugin.getSkillsManager().getXPforLevel(playerData.getSkillLevel(Skills.FISHING)))));
                menu.getItem(5).setLore(Arrays.asList("",ChatColor.translateAlternateColorCodes('&',"&7LVL: &e"+playerData.getSkillLevel(Skills.ALCHEMY)),ChatColor.translateAlternateColorCodes('&',"&7XP: &e"+playerData.getSkillXp(Skills.ALCHEMY)+"/"+plugin.getSkillsManager().getXPforLevel(playerData.getSkillLevel(Skills.ALCHEMY)))));
                menu.getItem(6).setLore(Arrays.asList("",ChatColor.translateAlternateColorCodes('&',"&7LVL: &e"+playerData.getSkillLevel(Skills.ENCHANTING)),ChatColor.translateAlternateColorCodes('&',"&7XP: &e"+playerData.getSkillXp(Skills.ENCHANTING)+"/"+plugin.getSkillsManager().getXPforLevel(playerData.getSkillLevel(Skills.ENCHANTING)))));
                menu.getItem(7).setLore(Arrays.asList("",ChatColor.translateAlternateColorCodes('&',"&7LVL: &e"+playerData.getSkillLevel(Skills.CRAFTING)),ChatColor.translateAlternateColorCodes('&',"&7XP: &e"+playerData.getSkillXp(Skills.CRAFTING)+"/"+plugin.getSkillsManager().getXPforLevel(playerData.getSkillLevel(Skills.CRAFTING)))));

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

        this.mainMenu = new IconMenu("Главное меню", 3, new IconMenu.onClick() {
            @Override
            public boolean menuClicked(Player clicker, IconMenu menu, IconMenu.Row row, int slot, ItemStack item) {
                return true;
            }
        }, new IconMenu.onOpen() {
            @Override
            public void menuOpened(Player opener, InventoryView menu) {
                PlayerData playerData = plugin.getPlayerManager().getPlayerData(opener.getUniqueId());
                if(playerData == null){return;}
                menu.getItem(4).setLore(Arrays.asList("",ChatColor.translateAlternateColorCodes('&',"&aHP")));
            }
        });
        switch (serverType){
            case "cave":
                this.mainMenu.addButton(this.skillsMenu.getRow(1),4,new ItemStack(Material.GRASS_BLOCK),"Телепорт в RM_World");
            case "rmworld":
                this.mainMenu.addButton(this.skillsMenu.getRow(1),4,new ItemStack(Material.STONE),"Телепорт в пещеру");
        }
        this.mainMenu.addButton(this.skillsMenu.getRow(1),1,new ItemStack(Material.DIAMOND_SWORD),"Навыки");
        this.mainMenu.addButton(this.skillsMenu.getRow(1),2,new ItemStack(Material.BOOK),"Рецепты");
        this.mainMenu.addButton(this.skillsMenu.getRow(1),3,new ItemStack(Material.EXPERIENCE_BOTTLE),"Уровень персонажа");
        this.mainMenu.addButton(this.skillsMenu.getRow(0),4,new ItemStack(Material.PLAYER_HEAD),"Персонаж");




    }
    public IconMenu getSkillsMenu(){
        return this.skillsMenu;
    }

    private List<String> convertStringListColorCodes(List<String> list){
        List<String> newList = new ArrayList<>();
        for(String line : list){
            newList.add(ChatColor.translateAlternateColorCodes('&',line));
        }
        return newList;
    }
}

