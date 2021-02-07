package me.abacuscl.basicimprovements.timelogger;

import org.bukkit.entity.Player;

public class TimeLogger {
    
    private final Player PLAYER;
    private String joinTime;
    private String leaveTime;
    
    public TimeLogger(Player p) {
        this.PLAYER = p;
    }
    
    //Getters and Setters
    public void setJoinTime(String time) {
        this.joinTime = time;
    }
    
    public String getJoinTime() {
        return this.joinTime;
    }
    
    public void setLeaveTime(String time) {
        this.leaveTime = time;
    }
    
    public String getLeaveTime() {
        return this.leaveTime;
    }
    
    public Player getPlayer() {
        return this.PLAYER;
    }
}
