package me.abacuscl.basicimprovements.listeners;

import me.abacuscl.basicimprovements.Main;
import me.abacuscl.basicimprovements.timelogger.LoggingHandler;
import me.abacuscl.basicimprovements.utils.Chat;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerQuitEvent;

public class LeaveListener implements Listener {
    
    private final Main PLUGIN;
    private final String LEAVE_MESSAGE = "<player> &fleft the server at <time> &e(Played for <hrs>hrs!)";
    
    public LeaveListener(Main plugin){
        this.PLUGIN = plugin;
        Bukkit.getPluginManager().registerEvents(this, plugin);
    }
    
    //When a player leaves the server
    @EventHandler
    public void onLeave(PlayerQuitEvent pqe) {
        Player p = pqe.getPlayer();
        pqe.setQuitMessage("");
        LoggingHandler.logExit(p);
        
        String leave = LEAVE_MESSAGE.replace("<player>", "&e&l" + p.getDisplayName());
        leave = leave.replace("<time>", LoggingHandler.getLeaveTime(p));
        leave = leave.replace("<hrs>", LoggingHandler.getTimeSpent(p));
        
        Bukkit.broadcastMessage(Chat.chat(leave));
    }
}
