package com.ruinsmc.rarity;

public enum Rarities implements Rarity{
    TRASH(0),
    COMMON(1),
    UNCOMMON(1),
    RARE(2),
    EPIC(3),
    LEGENDARY(3),
    MYTHIC(3);


    private int quality;

    Rarities(int quality) {
        this.quality = quality;
    }
    @Override
    public int quality() {
        return quality;
    }
}
