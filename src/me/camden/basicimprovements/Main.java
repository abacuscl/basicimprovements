package me.camden.basicimprovements;

import me.camden.basicimprovements.commands.backups.Backup;
import me.camden.basicimprovements.utils.BackupHandler;
import me.camden.basicimprovements.commands.utilities.Feed;
import me.camden.basicimprovements.commands.utilities.Gamemode;
import me.camden.basicimprovements.commands.utilities.Heal;
import me.camden.basicimprovements.listeners.BedListener;
import me.camden.basicimprovements.listeners.JoinListener;
import me.camden.basicimprovements.listeners.LeaveListener;
import org.bukkit.plugin.java.JavaPlugin;

public class Main extends JavaPlugin{
    
    //When the plugin starts
    @Override
    public void onEnable() {
        try {
            this.getLogger().info("Initializing...");
            init();
            this.getLogger().info("Successfully initialized!");
        } catch(Exception e) {
            this.getLogger().warning("Error while initializing");
            e.printStackTrace();
        }
    }
    
    //When the plugin exits
    @Override
    public void onDisable() {
        this.getLogger().info("Disabled!");
    }
    
    //Initialize the plugin
    private void init() {
        initConfig();
        initCommands();
        initListeners();
        initBackup();
    }
    
    //Initialize the config if it hasn't been already
    private void initConfig() {
        saveDefaultConfig();
    }
    
    //Initialize the plugin commands
    private void initCommands() {
        new Heal(this);
        new Feed(this);
        new Gamemode(this);
        new Backup(this);
        this.getLogger().info("Commands initialized!");
    }
    
    //Initialize the plugin listeners
    private void initListeners() {
        new JoinListener(this);
        new LeaveListener(this);
        new BedListener(this);
    }
    
    //Initialize the backup handler
    private void initBackup() {
        new BackupHandler(this);
        this.getLogger().info("Backup handler initialized!");
    }
}
