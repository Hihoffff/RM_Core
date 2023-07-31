package com.ruinsmc.skills;

import org.bukkit.ChatColor;

public enum Skills implements Skill{
    MINING("Шахтерство",ChatColor.DARK_GRAY),
    FARMING("Фермерство",ChatColor.YELLOW),
    FORAGING("Лесорубство",ChatColor.GREEN),
    COMBAT("Бой",ChatColor.DARK_RED),
    ENCHANTING("Зачарование",ChatColor.LIGHT_PURPLE),
    ALCHEMY("Алхимия",ChatColor.DARK_PURPLE),
    FISHING("Рыболовство",ChatColor.BLUE),
    CRAFTING("Крафтинг",ChatColor.WHITE);
    private String displayName;
    private ChatColor color;
    Skills(String displayName,ChatColor color){
        this.displayName = displayName;
        this.color = color;
    }
    @Override
    public String displayName(){
        return displayName;
    }
    @Override
    public ChatColor color(){
        return color;
    }

}
