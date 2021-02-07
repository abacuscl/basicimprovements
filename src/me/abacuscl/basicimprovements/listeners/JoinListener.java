package me.abacuscl.basicimprovements.listeners;

import me.abacuscl.basicimprovements.Main;
import me.abacuscl.basicimprovements.timelogger.LoggingHandler;
import me.abacuscl.basicimprovements.utils.Chat;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;

public class JoinListener implements Listener {
    
    private final Main PLUGIN;
    private final String JOIN_MESSAGE = "<player> &fjoined the server at <time>";
    
    public JoinListener(Main plugin){
        this.PLUGIN = plugin;
        Bukkit.getPluginManager().registerEvents(this, plugin);
    }
    
    //When a player joins the server
    @EventHandler
    public void onJoin(PlayerJoinEvent pje) {
        Player p = pje.getPlayer();
        pje.setJoinMessage("");
        LoggingHandler.logJoin(p);
        
        //If the player has not played before then show a special message
        if (!p.hasPlayedBefore()) {
            Bukkit.broadcastMessage(Chat.sendMessage("firstjoin_message", p.getDisplayName(), PLUGIN));
        } else {
            String join = JOIN_MESSAGE.replace("<player>", "&e&l" + p.getDisplayName());
            join = join.replace("<time>", LoggingHandler.getJoinTime(p));
            
            Bukkit.broadcastMessage(Chat.chat(join));
        }
    }
}
