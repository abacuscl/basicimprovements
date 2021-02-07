package me.abacuscl.basicimprovements.commands.utilities;

import me.abacuscl.basicimprovements.Main;
import me.abacuscl.basicimprovements.utils.Chat;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class Feed implements CommandExecutor {

    private final Main PLUGIN;

    public Feed(Main plugin) {
        this.PLUGIN = plugin;
        plugin.getCommand("feed").setExecutor(this);
    }
    
    //When the /feed command is executed
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
                        
                        //Fill the food and saturation
                        target.setFoodLevel(20);
                        target.setSaturation(20);
                        
                        //Send a message to the target and the executor
                        sender.sendMessage(Chat.sendMessage("feed_give_message", target.getDisplayName(), PLUGIN));
                        target.sendMessage(Chat.sendMessage("feed_receive_message", "the console", PLUGIN));
                        return true;
                    } catch (Exception e) {
                        sender.sendMessage(Chat.sendErrorMessage("target"));
                        return true;
                    }
                    
                //No specified player
                case 0:
                    
                    //The console cannot execute /feed on itself
                    PLUGIN.getLogger().info(Chat.sendErrorMessage("console"));
                    return true;
                
                //Invalid arguments
                default:
                    return false;
            }
        }
        
        //PLAYER
        Player p = (Player) sender;
        
        //If the player does not have the permissions, then they cannot use /feed
        if (!p.hasPermission("basicimprovements.feed")) {
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
                    
                    //Fill the food and saturation
                    target.setFoodLevel(20);
                    target.setSaturation(20);
                    
                    //Send a message to the target
                    //If the target is the sender then don't show both a receiving and sending message
                    p.sendMessage(Chat.sendMessage("feed_give_message", target.getDisplayName(), PLUGIN));
                    
                    if (!target.getDisplayName().equals(p.getDisplayName())) {
                        target.sendMessage(Chat.sendMessage("feed_receive_message", p.getDisplayName(), PLUGIN));
                    }
                    return true;
                } catch (Exception e) {
                    p.sendMessage(Chat.sendErrorMessage("target"));
                    return true;
                }  
            
            //No specified player
            case 0:
                
                //Fill the sender's  food and saturation
                p.setFoodLevel(20);
                p.setSaturation(20);
                p.sendMessage(Chat.sendMessage("feed_give_message", "yourself", PLUGIN));
                return true;
            
            //Invalid arguments
            default:
                return false;
        }
    }
}
