package me.camden.basicimprovements.commands.utilities;

import me.camden.basicimprovements.Main;
import me.camden.basicimprovements.utils.Chat;
import org.bukkit.Bukkit;
import org.bukkit.attribute.Attribute;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class Heal implements CommandExecutor {

    private final Main PLUGIN;

    public Heal(Main plugin) {
        this.PLUGIN = plugin;
        plugin.getCommand("heal").setExecutor(this);
    }
    
    //When the /heal command is executed
    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        
        //Checks if the sender is the console or a player
        if (!(sender instanceof Player)) {
            
            //CONSOLE
            switch (args.length) {
                
                //Specified Player
                case 1:
                    //Set the target player to the first argument
                    //Will catch if the player is invalid
                    try {
                        Player target = Bukkit.getServer().getPlayer(args[0]);
                        
                        //Fully heal the player
                        target.setHealth(target.getAttribute(Attribute.GENERIC_MAX_HEALTH).getDefaultValue());
                        
                        //Send a message to the target and the executor
                        sender.sendMessage(Chat.sendMessage("heal_give_message", target.getDisplayName(), PLUGIN));
                        target.sendMessage(Chat.sendMessage("heal_receive_message", "the console", PLUGIN));
                        return true;
                    } catch (Exception e) {
                        sender.sendMessage(Chat.sendErrorMessage("target"));
                        return true;
                    }
                
                //No specified player
                case 0:
                    
                    //The console cannot execute /heal on itself
                    PLUGIN.getLogger().info(Chat.sendErrorMessage("console"));
                    return true;
                
                //Invalid arguments
                default:
                    return false;
            }
        }
        
        //PLAYER
        Player p = (Player) sender;
        
        //If the player is not an op, then they cannot use /heal
        if (!p.isOp()) {
            p.sendMessage(Chat.sendErrorMessage("op"));
            return true;
        }
        
        double maxHealth = p.getAttribute(Attribute.GENERIC_MAX_HEALTH).getDefaultValue();

        switch (args.length) {
            
            //Specified player
            case 1:
                try {
                    //Set the target player to the first argument
                    //Will catch if the player is invalid
                    Player target = Bukkit.getServer().getPlayer(args[0]);
                    
                    //Fully heal the player
                    target.setHealth(maxHealth);
                    
                    //Send a message to the target
                    //If the target is the sender then don't show both a receiving and sending message
                    p.sendMessage(Chat.sendMessage("heal_give_message", target.getDisplayName(), PLUGIN));
                    if (!target.getDisplayName().equals(p.getDisplayName())) {
                        target.sendMessage(Chat.sendMessage("heal_receive_message", p.getDisplayName(), PLUGIN));
                    }
                    return true;
                } catch (Exception e) {
                    sender.sendMessage(Chat.sendErrorMessage("target"));
                    return true;
                }  
            
            //No specified player
            case 0:
                
                //Fully heal the sender
                p.setHealth(maxHealth);
                p.sendMessage(Chat.sendMessage("heal_give_message", "yourself", PLUGIN));
                return true;
            
            //Invalid arguments
            default:
                return false;
        }
    }
}
