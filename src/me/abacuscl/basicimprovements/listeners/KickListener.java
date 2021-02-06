package me.abacuscl.basicimprovements.listeners;

import me.abacuscl.basicimprovements.Main;
import me.abacuscl.basicimprovements.utils.Chat;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerKickEvent;

public class KickListener implements Listener{
    
    private final Main PLUGIN;
    
    public KickListener(Main plugin) {
        this.PLUGIN = plugin;
        Bukkit.getPluginManager().registerEvents(this, plugin);
    }
    
    //When a player is kicked
    @EventHandler
    public void onKick(PlayerKickEvent pke) {
        Player p = pke.getPlayer();
        pke.setLeaveMessage("");
        
        Bukkit.broadcastMessage(Chat.kickMessage(p.getDisplayName(), pke.getReason(), PLUGIN));
    }
}
