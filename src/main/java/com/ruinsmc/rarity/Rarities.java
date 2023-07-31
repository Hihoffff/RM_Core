package com.ruinsmc.rarity;

import org.bukkit.ChatColor;

public enum Rarities implements Rarity{
    TRASH(0,ChatColor.GRAY),
    COMMON(1,ChatColor.WHITE),
    UNCOMMON(1,ChatColor.GREEN),
    RARE(2,ChatColor.BLUE),
    EPIC(3,ChatColor.DARK_PURPLE),
    LEGENDARY(3,ChatColor.GOLD),
    MYTHIC(3,ChatColor.LIGHT_PURPLE);


    private int quality;
    private ChatColor color;

    Rarities(int quality, ChatColor color) {
        this.quality = quality;
        this.color = color;
    }
    @Override
    public int quality() {
        return quality;
    }
    @Override
    public ChatColor color(){return color;}
}
