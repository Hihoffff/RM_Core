package com.ruinsmc.commands;

import com.ruinsmc.RM_Core;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

public class playerCommands implements CommandExecutor {
    private final RM_Core plugin;

    public playerCommands(RM_Core plugin){
        this.plugin = plugin;
        plugin.getLogger().info("Registering commands....");
        plugin.getCommand("admin").setExecutor(this);
        plugin.getCommand("menu").setExecutor(this);
        plugin.getCommand("skills").setExecutor(this);
        plugin.getLogger().info("Commands registered!");
    }
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if(sender.isOp()){
            Player player = (Player) sender;
            if(command.getName().equalsIgnoreCase("admin")){
                if(args.length > 2){
                    if(args[0].equals("item")){
                        if(args[1].equals("give")){
                            String itemID = args[2];
                            ItemStack item = plugin.getItemsManager().getItem(itemID);
                            if(item != null && player != null){
                                player.getInventory().addItem(item);
                                player.sendMessage(ChatColor.GREEN+"(AdminCommand) Выдан предмет "+itemID);
                                return true;
                            }
                            return false;
                        }
                    }
                    return false;
                }

            }
        }
        if(sender instanceof Player){
            Player player = (Player) sender;
            if(command.getName().equalsIgnoreCase("menu")){
                plugin.getMenusManager().getMainMenu().open(player);
                return true;
            }
            else if(command.getName().equalsIgnoreCase("skills")){
                plugin.getMenusManager().getSkillsMenu().open(player);
                return true;
            }
            return true;
        }
        return true;
    }
}
