package com.ruinsmc.stats;

import org.bukkit.ChatColor;

public enum Stats implements Stat{
    DEFENSE("Защита",ChatColor.GREEN),
    WISDOM("Мудрость",ChatColor.AQUA),
    CRITCHANCE("Шанс критического удара",ChatColor.DARK_RED),
    STRENGTH("Сила",ChatColor.DARK_RED),
    HEALTH("Здоровье",ChatColor.RED),
    REGENERATION("Регенерация",ChatColor.RED),
    LUCK("Удача",ChatColor.LIGHT_PURPLE);
    private String displayName;
    private ChatColor color;
    Stats(String displayName, ChatColor color){
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
