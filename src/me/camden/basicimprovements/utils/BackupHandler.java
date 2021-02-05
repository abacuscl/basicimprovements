package me.camden.basicimprovements.utils;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.file.FileVisitResult;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.SimpleFileVisitor;
import java.nio.file.attribute.BasicFileAttributes;
import java.text.DecimalFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Calendar;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;
import me.camden.basicimprovements.Main;
import org.bukkit.Bukkit;
import org.bukkit.World;

/*
Major thanks to Exolius, he wrote a plugin many years ago that is now deprecated 
and I used some code he wrote for his own backup program in this program. His app
helped further my understanding and I am very grateful.

His GitHub: https://github.com/Exolius/SimpleBackup

I also utilized the file zipping code from this website and modified it a bit to fit
my use case. Thank you!
https://www.quickprogrammingtips.com/java/how-to-zip-a-folder-in-java.html
 */
public class BackupHandler {

    private static Main plugin;
    private static final String WORKING_DIR = System.getProperty("user.dir");
    private static String backupPath = "plugins/BasicImprovements/backups";
    private static int keptVersions = 10;
    private static final double INTERVAL = 24;
    private static double startTime = 23;

    public BackupHandler(Main plugin) {
        BackupHandler.plugin = plugin;
        backupPath = plugin.getConfig().getString("backup_path");
        keptVersions = plugin.getConfig().getInt("kept_versions");
        startTime = plugin.getConfig().getInt("backup_time");
        autoBackup();
    }

    //Runs a manual backup
    public static void manualBackup() {
        
        //Backup needs to run asynchronously from the primary server thread
        plugin.getServer().getScheduler().runTaskAsynchronously(plugin, () -> {
            runBackup();
        });
    }

    //Runs the automatic backup
    private static void autoBackup() {
        DecimalFormat df = new DecimalFormat(".00");
        String out = "Next automatic backup in approx: " + df.format(getRemaining() / 72000.) + "hr";
        plugin.getLogger().info(out);

        //Interval is the amount of time in between runs in ticks
        //72000 is the number of ticks in 1 hour of real time
        long interval = (long) (72000 * INTERVAL);

        //Delay is the amount of time before the backup starts in ticks
        long delay = getRemaining();

        //Creates the task timer for saving that runs asynchronously
        plugin.getServer().getScheduler().runTaskTimerAsynchronously(plugin, () -> {
            runBackup();
        }, delay, interval);
    }

    //Executes the backup process
    private static void runBackup() {
        Bukkit.broadcastMessage(Chat.sendMessage("backup_started", "", plugin));
        
        //Needed to set world autosave synchronously
        plugin.getServer().getScheduler().runTask(plugin, () -> {
            try {
                for (World w : Bukkit.getWorlds()) {
                    w.setAutoSave(false);
                    w.save();
                }
            } catch (Exception e) {
                Chat.debugToConsole(e);
            }
        });

        Path sourceFolderPath = Paths.get(WORKING_DIR);
        
        //Creates the backup folder if it doesn't already exist
        if (!Files.exists(Paths.get(WORKING_DIR + "/" + backupPath))) {
            try {
                Files.createDirectory(Paths.get(WORKING_DIR + "/" + backupPath));
            } catch (IOException e) {
                Chat.debugToConsole(e);
            }
        }
        
        //Formats the date to append to the backup file name
        LocalDateTime date = LocalDateTime.now();
        String fmt = "dd-MM-yyyy HH.mm.ss";
        DateTimeFormatter dateFormat = DateTimeFormatter.ofPattern(fmt);
        String fmtDate = date.format(dateFormat);

        Path zipPath = Paths.get(WORKING_DIR + "/" + backupPath + "/" + "Backup " + fmtDate + ".zip");
        
        //Run through the entire server folder and zips the contents to the backups folder
        try (ZipOutputStream zos = new ZipOutputStream(new FileOutputStream(zipPath.toFile()))) {
            Files.walkFileTree(sourceFolderPath, new SimpleFileVisitor<Path>() {
                @Override
                public FileVisitResult visitFile(Path file, BasicFileAttributes attrs) throws IOException {
                    if ((file + "").contains("backups") || (file + "").contains("session.lock")) {
                        return FileVisitResult.CONTINUE;
                    }
                    zos.putNextEntry(new ZipEntry(sourceFolderPath.relativize(file).toString()));
                    Files.copy(file, zos);
                    zos.closeEntry();
                    return FileVisitResult.CONTINUE;
                }
            });
            deleteOld();
        } catch (Exception e) {
            Chat.debugToConsole(e);
        }
        
        //Needed to set world autosave synchronously
        plugin.getServer().getScheduler().runTask(plugin, () -> {
            try {
                for (World w : Bukkit.getWorlds()) {
                    w.setAutoSave(true);
                }
            } catch (Exception e) {
                Chat.debugToConsole(e);
            }
        });

        Bukkit.broadcastMessage(Chat.sendMessage("backup_finished", "", plugin));
    }

    //Gets the current time in hours
    private static double curHours() {
        double hour = Calendar.getInstance().get(Calendar.HOUR_OF_DAY);
        double min = Calendar.getInstance().get(Calendar.MINUTE);
        double sec = Calendar.getInstance().get(Calendar.SECOND);
        return hour + min / 60 + sec / 3600;
    }

    //Gets the remaining time until the next time interval
    //Thanks to Exolius!
    private static long getRemaining() {
        double now = curHours();
        double diff = now - startTime;

        if (diff < 0) {
            diff += 24;
        }

        double intervalPart = diff - Math.floor(diff / INTERVAL) * INTERVAL;
        double remaining = INTERVAL - intervalPart;

        return (long) (remaining * 72000);
    }
    
    //Deletes the oldest backup version
    private static void deleteOld() {
        File f = new File(WORKING_DIR + "/" + backupPath);
        String[] contents = f.list();
        
        if (contents.length > keptVersions) {
            try {
            String str = "Old backup file successfully deleted: " + contents[0];
            new File(f.getAbsolutePath() + "/" + contents[0]).delete();
            plugin.getLogger().info(str);
            } catch (Exception e) {
                Chat.debugToConsole(e);
            }
        }
    }
}
