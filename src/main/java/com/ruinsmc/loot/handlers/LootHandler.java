package com.ruinsmc.loot.handlers;


import com.ruinsmc.RM_Core;
import com.ruinsmc.data.PlayerData;
import com.ruinsmc.loot.BlockLoot;
import com.ruinsmc.loot.MobLoot;
import com.ruinsmc.stats.Stats;
import org.bukkit.GameMode;
import org.bukkit.World;
import org.bukkit.block.Block;
import org.bukkit.block.data.Ageable;
import org.bukkit.entity.Mob;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.metadata.FixedMetadataValue;
public class LootHandler implements Listener {
    private final RM_Core plugin;
    public LootHandler(RM_Core plugin){
        this.plugin = plugin;
    }

    @EventHandler
    public void onEntityDeath(org.bukkit.event.entity.EntityDeathEvent e){
        if(e.isCancelled()) return;
        if(e.getEntity() instanceof Mob && e.getEntity().getKiller() instanceof Player){
            Player player = e.getEntity().getKiller();
            Mob mob = (Mob) e.getEntity();
            if(player == null || mob == null){return;}
            MobLoot mobLoot = plugin.getLootManager().getMobLoot(mob.getType().name());
            if(mobLoot == null){return;}
            if(mobLoot.getLootPool() != null){
                PlayerData playerData = plugin.getPlayerManager().getPlayerData(player.getUniqueId());
                if(playerData != null){
                    ItemStack randomLoot = mobLoot.getLootPool().getRandomLoot(playerData.getStatLevel(Stats.LUCK));
                    if(randomLoot != null){
                        World world = mob.getWorld();
                        world.dropItemNaturally(mob.getLocation(),randomLoot);
                    }
                }
            }
            plugin.getSkillsManager().addSkillXp(player, mobLoot.getSkill(), mobLoot.getSkillXP());
        }

    }
    @EventHandler
    public void onBlockBreak(BlockBreakEvent e){
        if(e.isCancelled())return;
        Player player = e.getPlayer();
        Block block = e.getBlock();
        BlockLoot blockLoot = plugin.getLootManager().getBlockLoot(block.getType().name());
        if(blockLoot == null){return;}
        boolean isBlockPlacedNotByPlayer = e.getBlock().getMetadata("p").isEmpty();
        if(isBlockPlacedNotByPlayer || blockLoot.isBlockGrowable()) {
            if(blockLoot.isBlockGrowable()){
                if(!isPlantGrowFinished(block)){
                    return;
                }
            }
            if(blockLoot.getLootPool() != null){
                PlayerData playerData = plugin.getPlayerManager().getPlayerData(player.getUniqueId());
                if(playerData != null){
                    if(blockLoot.getLootPool().replaceVanillaLoot() == true){
                        e.setDropItems(false);
                    }
                    ItemStack randomLoot = blockLoot.getLootPool().getRandomLoot(playerData.getStatLevel(Stats.LUCK));
                    if(randomLoot != null){
                        World world = e.getBlock().getWorld();
                        world.dropItemNaturally(e.getBlock().getLocation(), randomLoot);
                    }
                }
            }
            plugin.getSkillsManager().addSkillXp(player, blockLoot.getSkill(), blockLoot.getSkillXP());
        }
    }
    private boolean isPlantGrowFinished(Block block){
        Ageable age = (Ageable) block.getState();
        if(age == null){return true;}
        if(age.getAge() == age.getMaximumAge()){
            return true;
        }
        return false;
    }

    @EventHandler
    public void onBlockPlace(BlockPlaceEvent e){
        if(e.isCancelled())return;
        if(e.getPlayer().getGameMode() != GameMode.CREATIVE){
            e.getBlockPlaced().setMetadata("p",new FixedMetadataValue(plugin, true));
        }
    }
}
