package me.camden.basicimprovements.listeners;

import me.camden.basicimprovements.Main;
import me.camden.basicimprovements.utils.Chat;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerQuitEvent;

public class LeaveListener implements Listener {
    
    private final Main PLUGIN;
    
    public LeaveListener(Main plugin){
        this.PLUGIN = plugin;
        Bukkit.getPluginManager().registerEvents(this, plugin);
    }
    
    //When a player leaves the server
    @EventHandler
    public void onLeave(PlayerQuitEvent pqe) {
        Player p = pqe.getPlayer();
        pqe.setQuitMessage("");
        
        Bukkit.broadcastMessage(Chat.sendMessage("leave_message", p.getDisplayName(), PLUGIN));
    }
}
