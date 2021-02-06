package me.abacuscl.basicimprovements;

import me.abacuscl.basicimprovements.commands.backups.Backup;
import me.abacuscl.basicimprovements.utils.BackupHandler;
import me.abacuscl.basicimprovements.commands.utilities.Feed;
import me.abacuscl.basicimprovements.commands.utilities.Gamemode;
import me.abacuscl.basicimprovements.commands.utilities.Heal;
import me.abacuscl.basicimprovements.commands.utilities.Time;
import me.abacuscl.basicimprovements.listeners.BedListener;
import me.abacuscl.basicimprovements.listeners.JoinListener;
import me.abacuscl.basicimprovements.listeners.LeaveListener;
import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scheduler.BukkitWorker;

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
        //If there are active tasks still running, wait for them to finish before fully disabling
        if (!(getActiveTasks() == 0)) {
            BackupHandler.shutdown();
            waitForTasks();
        }
        
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
        new Time(this);
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
    
    //Waits for any remaining asynchronous tasks to complete before continuing
    //Primarily used for the backups which make use of asynchronous tasks
    private void waitForTasks() {
        int activeTasks = getActiveTasks();
        String str = "There is still " + activeTasks + " active task(s) waiting for completion before shutdown";
        this.getLogger().warning(str);
        
        //While there are still tasks running
        while (activeTasks > 0) {
            try {
                //Wait 30sec on the main thread
                Thread.sleep(30000);
            } catch (Exception e) {
                Thread.currentThread().interrupt();
            }
            
            //Refresh the status of the task and print out the number of remaining tasks
            activeTasks = getActiveTasks();
            this.getLogger().warning(str);
        }
        
        //Continue once finished
        this.getLogger().info("All active tasks finished!");
    }
    
    //Gets the number of currently active tasks
    private int getActiveTasks() {
        int tasks = 0;
        for (BukkitWorker w : Bukkit.getScheduler().getActiveWorkers()) {
            if (w.getOwner().equals(this)) {
                tasks++;
            }
        }
        return tasks;
    }
}
