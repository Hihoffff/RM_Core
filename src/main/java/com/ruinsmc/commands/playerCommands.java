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
                }

            }
            return true;
        }
        if(sender instanceof Player){
            Player player = (Player) sender;

            return true;
        }
        return true;
    }
}
