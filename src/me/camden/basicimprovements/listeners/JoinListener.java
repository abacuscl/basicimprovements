package me.camden.basicimprovements.listeners;

import me.camden.basicimprovements.Main;
import me.camden.basicimprovements.utils.Chat;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;

public class JoinListener implements Listener {
    
    private final Main PLUGIN;
    
    public JoinListener(Main plugin){
        this.PLUGIN = plugin;
        Bukkit.getPluginManager().registerEvents(this, plugin);
    }
    
    //When a player joins the server
    @EventHandler
    public void onJoin(PlayerJoinEvent pje) {
        Player p = pje.getPlayer();
        pje.setJoinMessage("");
        
        //If the player has not played before then show a special message
        if (!p.hasPlayedBefore()) {
            Bukkit.broadcastMessage(Chat.sendMessage("firstjoin_message", p.getDisplayName(), PLUGIN));
        } else {
            Bukkit.broadcastMessage(Chat.sendMessage("join_message", p.getDisplayName(), PLUGIN));
        }
    }
}
