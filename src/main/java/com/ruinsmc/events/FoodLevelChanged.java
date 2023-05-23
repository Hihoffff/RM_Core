package com.ruinsmc.events;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.FoodLevelChangeEvent;

public class FoodLevelChanged implements Listener {
    @EventHandler
    public void onFoodLevelChanged(FoodLevelChangeEvent e){
        e.setCancelled(true);
    }
}
