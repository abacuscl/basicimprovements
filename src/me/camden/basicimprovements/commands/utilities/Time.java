package me.camden.basicimprovements.commands.utilities;

import me.camden.basicimprovements.Main;
import me.camden.basicimprovements.utils.Chat;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class Time implements CommandExecutor {
    
    private final Main PLUGIN;
    
    public Time (Main plugin) {
        this.PLUGIN = plugin;
        plugin.getCommand("tsd").setExecutor(this);
        plugin.getCommand("tsn").setExecutor(this);
    }
    
    //When the shortened time command is executed
    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        
        //The console cannot currently execute these commands
        //This is because Bukkit requires a player to set the current world time
        if (!(sender instanceof Player)) {
            sender.sendMessage(Chat.sendErrorMessage("console time"));
            return true;
        }
        
        Player p = (Player) sender;
        
        //If the player does not have the permissions, they cannot execute the commands
        if(!p.hasPermission("basicimprovements.time")) {
            p.sendMessage(Chat.sendErrorMessage("permission"));
            return true;
        }
        
        //There should be no arguments for this command
        if (args.length > 0) {
            return false;
        }
        
        return setTime(cmd.getName(), p);
    }
    
    //Set the time based on the command type
    private boolean setTime(String cmdType, Player p) {
        
        switch (cmdType) {
            case "tsd":
                p.getWorld().setTime(0);
                Bukkit.getServer().broadcastMessage(Chat.sendMessage("time_set_day", p.getDisplayName(), PLUGIN));
                return true;
            case "tsn":
                p.getWorld().setTime(13000);
                Bukkit.getServer().broadcastMessage(Chat.sendMessage("time_set_night", p.getDisplayName(), PLUGIN));
                return true;
            default:
                return false;
        }
    }
}
