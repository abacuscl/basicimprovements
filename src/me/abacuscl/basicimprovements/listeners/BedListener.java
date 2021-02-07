package me.abacuscl.basicimprovements.listeners;

import me.abacuscl.basicimprovements.Main;
import me.abacuscl.basicimprovements.utils.Chat;
import org.bukkit.Bukkit;
import org.bukkit.World;
import org.bukkit.craftbukkit.v1_16_R3.entity.CraftPlayer;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerBedEnterEvent;
import org.bukkit.event.player.PlayerBedEnterEvent.BedEnterResult;

public class BedListener implements Listener {
    
    private final Main PLUGIN;
    
    public BedListener(Main plugin){
        this.PLUGIN = plugin;
        Bukkit.getPluginManager().registerEvents(this, plugin);
    }
    
    //When a player sleeps
    @EventHandler
    public void onSleep(PlayerBedEnterEvent be) {
        Player p = be.getPlayer();
        CraftPlayer cp = (CraftPlayer) p;
        
        //If the player can sleep, then sleep
        if(be.getBedEnterResult().equals(BedEnterResult.OK)) {
            sleep(p.getWorld(), cp);
            Bukkit.broadcastMessage(Chat.sendMessage("sleep_message", p.getDisplayName(), PLUGIN));
        } 
    }
    
    //Handles the sleeping for the server
    private void sleep(World wld, CraftPlayer cp) {
        //Set the time to day and clear the weather
        wld.setTime(0);
        wld.setStorm(false);
        wld.setThundering(false);
        wld.setWeatherDuration(0);
        
        //Greatly shorten the sleep cycle
        cp.getHandle().sleepTicks = 99;
        
        //If any other online players are sleeping then kick them out of bed as well
        for (Player p : Bukkit.getOnlinePlayers()) {
            CraftPlayer cpl = (CraftPlayer) p;
            if(p.isSleeping()) {
                cpl.getHandle().sleepTicks = 99;
            }
        }
    }
}
