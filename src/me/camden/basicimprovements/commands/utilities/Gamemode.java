package me.camden.basicimprovements.commands.utilities;

import me.camden.basicimprovements.Main;
import me.camden.basicimprovements.utils.Chat;
import org.bukkit.Bukkit;
import org.bukkit.GameMode;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class Gamemode implements CommandExecutor {

    private final Main PLUGIN;

    public Gamemode(Main plugin) {
        this.PLUGIN = plugin;
        plugin.getCommand("gms").setExecutor(this);
        plugin.getCommand("gmc").setExecutor(this);
        plugin.getCommand("gma").setExecutor(this);
        plugin.getCommand("gmsp").setExecutor(this);
    }
    
    //When a gamemode command is executed
    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        
        //Checks if the sender is the console or a player
        if (!(sender instanceof Player)) {
            
            //CONSOLE
            switch (args.length) {
                
                //Specified player
                case 1:
                    try {
                        //Set the target player to the first argument
                        //Will catch if the player is invalid
                        Player target = Bukkit.getServer().getPlayer(args[0]);
                        
                        //Sets the gamemode depending on the command name
                        if(setGamemode(cmd.getName().toLowerCase(), target) != -1) {
                            
                            //Send a message to the target and the executor
                            sender.sendMessage(Chat.sendMessage("gamemode_give_update", target.getDisplayName(), PLUGIN));
                            target.sendMessage(Chat.sendMessage("gamemode_receive_update", "the console", PLUGIN));
                            return true;
                        } else {
                            //If the command is invalid, exit
                            return false;
                        }
                    } catch (Exception e) {
                        sender.sendMessage(Chat.sendErrorMessage("target"));
                        return true;
                    }
                
                //Not specified player
                case 0:
                    PLUGIN.getLogger().info(Chat.sendErrorMessage("console"));
                    return true;
                
                //Invalid arguments
                default:
                    return false;
            }
        }
        
        //PLAYER
        Player p = (Player) sender;        
        
        //If the player does not have the permissions, then they cannot use the gamemode commands
        if (!p.hasPermission("basicimprovements.gamemode")) {
            p.sendMessage(Chat.sendErrorMessage("permission"));
            return true;
        }
        
        switch (args.length) {
            
            //Specified player
            case 1:
                try {
                    //Set the target player to the first argument
                    //Will catch if the player is invalid
                    Player target = Bukkit.getServer().getPlayer(args[0]);
                    
                    //Sets the gamemode depending on the command name
                    if(setGamemode(cmd.getName().toLowerCase(), target) != -1) {
                        
                        //Send a message to the target
                        //If the target is the sender then don't show both a receiving and sending message
                        sender.sendMessage(Chat.sendMessage("gamemode_give_update", target.getDisplayName(), PLUGIN));
                        if (!target.getDisplayName().equals(p.getDisplayName())) {
                            target.sendMessage(Chat.sendMessage("gamemode_receive_update", p.getDisplayName(), PLUGIN));
                        }
                        return true;
                    } else {
                        //If the command is invalid, exit
                        return false;
                    }
                } catch (Exception e) {
                    p.sendMessage(Chat.sendErrorMessage("target"));
                    return true;
                } 
                
            //No specified player
            case 0:
                
                //Sets the gamemode depending on the command name
                if(setGamemode(cmd.getName().toLowerCase(), p) != -1) {
                    sender.sendMessage(Chat.sendMessage("gamemode_give_update", "yourself", PLUGIN));
                    return true;
                } else {
                    //If the command is invalid, exit
                    return false;
                }
                
            //Invalid arguments
            default:
                return false;
        }
    }
    
    //Sets the gamemode based on the issued command
    private int setGamemode(String cmdType, Player p) {
        switch (cmdType) {
            case "gms":
                p.setGameMode(GameMode.SURVIVAL);
                return 0;
            case "gmc":
                p.setGameMode(GameMode.CREATIVE);
                return 1;
            case "gma":
                p.setGameMode(GameMode.ADVENTURE);
                return 2;
            case "gmsp":
                p.setGameMode(GameMode.SPECTATOR);
                return 3;
            default:
                return -1;
        }
    }
}
