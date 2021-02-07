package me.abacuscl.basicimprovements.timelogger;

import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Date;
import org.bukkit.entity.Player;

public class LoggingHandler {
    
    private static ArrayList<TimeLogger> log = new ArrayList<>();
    
    //Logs the time a player joins the server
    public static void logJoin(Player toLog) {
        TimeLogger t = new TimeLogger(toLog);
        t.setJoinTime(getCurrentTime()); 
        log.add(t);
    }
    
    //Logs the time a player exits the server
    public static void logExit(Player toLog) {
        TimeLogger t = log.get(getLogIndex(toLog));
        t.setLeaveTime(getCurrentTime());
    }
    
    //Gets the time a player spent on the server
    //Also removes the player from the list
    public static String getTimeSpent(Player pl) {
        TimeLogger t = log.get(getLogIndex(pl));
        String diff = calculateDifference(t);
        log.remove(t);
        return diff;
    }
    
    //Returns the player's join time
    public static String getJoinTime(Player toGet) {
        TimeLogger t = log.get(getLogIndex(toGet));
        return t.getJoinTime();
    }
    
    //Returns the player's leave time
    public static String getLeaveTime(Player toGet) {
        TimeLogger t = log.get(getLogIndex(toGet));
        return t.getLeaveTime();
    }
    
    //Gets the current time as a formatted String
    private static String getCurrentTime() {
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("HH:mm:ss");
        LocalDateTime now = LocalDateTime.now();
        
        return dtf.format(now);
    }
    
    //Calculates the difference between the player joining and leaving
    private static String calculateDifference(TimeLogger t) {
        SimpleDateFormat fmt = new SimpleDateFormat("HH:mm:ss");
        try {
            Date d1 = fmt.parse(t.getJoinTime());
            Date d2 = fmt.parse(t.getLeaveTime());
            
            long diff = d2.getTime() - d1.getTime();
            DecimalFormat df = new DecimalFormat("#,##0.00");
            
            return df.format((diff / 1000 / 60 / 60.));
            
        } catch (Exception e) {
            return null;
        }
    }
    
    //Finds the index of the TimeLogger in the list that is logging the specified player
    private static int getLogIndex(Player p) {
        for (TimeLogger t : log) {
            if(t.getPlayer().getDisplayName().equals(p.getName())) {
                return log.indexOf(t);
            }
        }
        return -1;
    }
}
