 # BasicImprovements
 A Bukkit plugin that provides some basic, quality of life, server improvements.
 
 **Current Minecraft Versions: 1.16**

### **This project has been archived and is no longer in development.**
 ----
 ## Table of Contents
 - [Features](#features)  
 - [Technologies](#technologies)  
 - [Setup](#setup)  
 - [Ideas to Implement](#ideas-to-implement)  
 
 ----
 ## Features
 
 ### Commands
 **All commands in this plugin require the player executing the command to have the proper
 permissions which are, by default, a server operator with at least permission level 2. 
 All permissions are detailed in their specific commands.**
 
 #### General Commands
 - `/backup`  
   - Permission: `basicimprovements.backup`  
 
 Starts a backup of the server.
 
 - `/feed [target]`
   - Short version: `/f [target]`  
   - Permission: `basicimprovements.feed`  
 
 Fills the target player's hunger and saturation.
 If there is no target player, it defaults to yourself.
 
 - `/heal [target]`
   - Short version: `/h [target]`  
   - Permission: `basicimprovements.heal`  
 
 Fills the target player's health to maximum.
 If there is no target player, it defaults to yourself.
 
 - `/tsd`  
   - Permission: `basicimprovements.shorttime`  
 
 Sets the time to day.
 
 - `/tsn`  
   - Permission: `basicimprovements.shorttime`  
 
 Sets the time to night.
 
 *Neither of the plugin provided time setting commands affect the server day counter; unlike the mojang provided time setting commands, which reset the day counter to 0.*
 
 #### Gamemode Commands
 - `/gma [target]`  
   - Permission: `basicimprovements.shortgamemode`  
 
 Sets the target player's gamemode to adventure.
 If there is no target player, it defaults to yourself.
 
 - `/gms [target]`  
   - Permission: `basicimprovements.shortgamemode`  
 
 Sets the target player's gamemode to survival.
 If there is no target player, it defaults to yourself.
 
 - `/gmc [target]`  
   - Permission: `basicimprovements.shortgamemode`  
 
 Sets the target player's gamemode to creative.
 If there is no target player, it defaults to yourself.
 
 - `/gmsp [target]`  
   - Permission: `basicimprovements.shortgamemode`  
 
 Sets the target player's gamemode to spectator.
 If there is no target player, it defaults to yourself.
 
 ### Chat Messages
 All configurable chat messages can be configured in the `config.yml` file.
 
 - New chat messages for players joining and leaving including time played and join/leave time.
 - New chat message for a player joining for the first time.
 - New chat message for a player sleeping in a bed.
 - New chat message for a player getting kicked.
 - New chat messages for the new commands.
 
 ### Backups
 The backups run every 24 hours and the default backup folder is: `/plugins/BasicImprovements/backups`. By default the start time is 24:00 (12:00am), but you can configure
 this time as well. Use the `/backup` command to run a manual backup before the automatic backup time.
 
 A backup consists of the entire server folder but not previous backups to save storage space. You can configure the number of
 kept server backups as well.
 
 If you stop the server while a backup is in progress, the server will wait until the backup is finished before shutting down completely.
 
 ### Single Player Sleep
 When one player sleeps, it sets the whole server's time to day and clears all weather. Now you no longer require all players
 to be sleeping in order to pass the night. The sleep cycle is also drastically decreased to allow for short sleeping animations.
 
 ----
 ## Technologies
 Created with:
 - Java AdoptOpenJDK 11
 - Spigot API v1.16
 
 ----
 ## Setup
 In order to set up the plugin:
 1. Click on "Releases" on the right side just below "About".
 2. From the latest release, download the .jar file.
    - Alternatively, go to the `releases` branch and clone the latest release.
 3. Place the BasicImprovements.jar inside your server plugins folder.
 4. Start the server and the plugin will load.
 
 You can find the config.yml in the `/plugins/BasicImprovements` folder to configure the plugin.
 
 The default backups folder is: `/plugins/BasicImprovements/backups`
 
 ----
 ## Bug Report
 To open a bug report or feature request, create an issue on the repository page.
 
 ----
 ## Ideas to Implement
 - [x] Time that the player joins the server (Release 2.0.0)
 - [x] Time that the player leaves the server (Release 2.0.0)
 - [x] Total time that the player was online for that session (Release 2.0.0)
 - [x] Time set shorthand commands (Release 2.0.0)
 - [ ] Enabling/disabling the automatic backups (Planned Release 2.1.0)
 - [ ] Allowing the user to choose the backup interval (Planned Release 2.1.0)
