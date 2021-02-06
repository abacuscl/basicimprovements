package me.abacuscl.basicimprovements.commands.backups;

import me.abacuscl.basicimprovements.Main;
import me.abacuscl.basicimprovements.utils.BackupHandler;
import me.abacuscl.basicimprovements.utils.Chat;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class Backup implements CommandExecutor {

    private final Main PLUGIN;

    public Backup(Main plugin) {
        this.PLUGIN = plugin;
        plugin.getCommand("backup").setExecutor(this);
    }

    //When the /backup command is executed
    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        
        //If the player does not have the permissions, they cannot use /backup
        if(sender instanceof Player) {
            Player p = (Player) sender;
            if(!p.hasPermission("basicimprovements.backup")) {
                p.sendMessage(Chat.sendErrorMessage("permission"));
                return true;
            }
        }
        
        //There should be no arguments in this command
        //If there are, then the command is invalid
        if (args.length == 0) {
            BackupHandler.manualBackup();
            return true;
        } else {
            return false;
        }
    }
}
