package me.camden.basicimprovements.utils;

import me.camden.basicimprovements.Main;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;

public class Chat {
    
    private static final String PLUGIN_NAME = "&7[BasicImprovements] ";
    private static final String BAD_TARGET = "&c&lInvalid target player";
    private static final String MISSING_CONF = "&c&lChat message could not be found in the config";
    private static final String NO_CONSOLE_ACCESS = "&cYou cannot execute this command on yourself using the console";
    private static final String NO_TIME_COMMAND_ACCESS = "&cYou cannot use the shortened time command in the console";
    private static final String NO_PERMISSION = "&c&lYou do not have permission to use this command";
   
    //Translates the "&" character to the special color code character
    public static String chat(String s) {
        return ChatColor.translateAlternateColorCodes('&', s);
    }
    
    //Formats a chat message to be displayed to the server
    public static String sendMessage(String typ, String name, Main plugin) {
        try {
            String formattedName;
            if (plugin.getConfig().getBoolean("obfuscate_command_issuer") == true) {
                switch(typ) {
                    case "feed_receive_message":
                    case "heal_receive_message":
                    case "gamemode_receive_update":
                    case "time_set_day":
                    case "time_set_night":
                        formattedName = chat("&e&k" + name);
                        break;
                    default:
                        formattedName = chat("&e&l" + name);
                        break; 
                }
            } else {
                formattedName = chat("&e&l" + name);
            }
            String formattedText = chat(plugin.getConfig().getString(typ));
       
            return chat(PLUGIN_NAME + formattedText.replace("<player>", formattedName));
        } catch (Exception e) {
            debugToConsole(e);
            return PLUGIN_NAME + MISSING_CONF;
        }
    }
    
    //Formats an error chat message to be displayed to the server
    public static String sendErrorMessage(String typ) {
        String str;
        switch(typ) {
            case "target":
                str = PLUGIN_NAME + BAD_TARGET;
                return chat(str);
            case "permission":
                str = PLUGIN_NAME + NO_PERMISSION;
                return chat(str);
            case "console":
                return chat(NO_CONSOLE_ACCESS);
            case "console time":
                return chat(NO_TIME_COMMAND_ACCESS);
            default:
                str = PLUGIN_NAME + MISSING_CONF;
                return chat(str);
        }
    }
    
    //Outputs the debug information of an exception to the server console
    public static void debugToConsole(Exception e) {
        Bukkit.getLogger().warning("AN ERROR OCCURRED! Full error details:");
        e.printStackTrace();
    }
}
