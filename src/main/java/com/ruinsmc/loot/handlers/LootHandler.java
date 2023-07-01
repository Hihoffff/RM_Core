package com.ruinsmc.loot.handlers;


import com.ruinsmc.RM_Core;
import com.ruinsmc.loot.BlockLoot;
import com.ruinsmc.loot.MobLoot;
import org.bukkit.GameMode;
import org.bukkit.block.Block;
import org.bukkit.block.data.Ageable;
import org.bukkit.block.data.BlockData;
import org.bukkit.entity.Mob;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.metadata.FixedMetadataValue;
import org.bukkit.scheduler.BukkitRunnable;

public class LootHandler implements Listener {
    private final RM_Core plugin;
    public LootHandler(RM_Core plugin){
        this.plugin = plugin;
    }

    @EventHandler
    public void onEntityDeath(org.bukkit.event.entity.EntityDeathEvent e){
        if(e.getEntity() instanceof Mob && e.getEntity().getKiller() instanceof Player){
            new BukkitRunnable(){
                public void run(){
                    Player player = e.getEntity().getKiller();
                    Mob mob = (Mob) e.getEntity();
                    if(player == null || mob == null || !player.isOnline()){return;}
                    MobLoot mobLoot = plugin.getLootManager().getMobLoot(mob.getType().name());
                    if(mobLoot == null){return;}
                    plugin.getSkillsManager().addSkillXp(player, mobLoot.getSkill(), mobLoot.getSkillXP());
                }
            }.runTaskAsynchronously(plugin);
        }

    }
    @EventHandler
    public void onBlockBreak(BlockBreakEvent e){
        Player player = e.getPlayer();
        Block block = e.getBlock();
        String blockName = block.getType().name();
        boolean isBlockPlacedNotByPlayer = e.getBlock().getMetadata("p").isEmpty();
        BlockData blockData = block.getBlockData().clone();
        new BukkitRunnable(){
            public void run(){
                if(!player.isOnline()){return;}
                BlockLoot blockLoot = plugin.getLootManager().getBlockLoot(blockName);
                if(blockLoot == null){return;}
                if(isBlockPlacedNotByPlayer || blockLoot.isBlockGrowable()) {
                    if(blockLoot.isBlockGrowable()){
                        if(!isPlantGrowFinished(blockData)){
                            return;
                        }
                    }
                    plugin.getSkillsManager().addSkillXp(player, blockLoot.getSkill(), blockLoot.getSkillXP());
                }
            }
        }.runTaskAsynchronously(plugin);
    }
    private boolean isPlantGrowFinished(BlockData blockData){
        try {
            Ageable age = (Ageable) blockData;
            if(age == null){return true;}
            if(age.getAge() == age.getMaximumAge()){
                return true;
            }
            return false;
        }catch (Exception ex){
            ex.printStackTrace();
            return true;
        }

    }

    @EventHandler
    public void onBlockPlace(BlockPlaceEvent e){
        if(e.getPlayer().getGameMode() != GameMode.CREATIVE){
            e.getBlockPlaced().setMetadata("p",new FixedMetadataValue(plugin, true));
        }
    }
}
