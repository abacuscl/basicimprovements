 # BasicImprovements
 A Bukkit plugin that provides some basic, quality of life, server improvements.
 
 **Current Minecraft Versions: 1.16**

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
 permissions which are, by default, a server operator. All permissions are detailed in their
 specific commands.**
 
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
   - Permission: `basicimprovements.time`  
 
 Sets the time to day.
 
 - `/tsn`  
   - Permission: `basicimprovements.time`  
 
 Sets the time to night.
 
 #### Gamemode Commands
 - `/gma [target]`  
   - Permission: `basicimprovements.gamemode`  
 
 Sets the target player's gamemode to adventure.
 If there is no target player, it defaults to yourself.
 
 - `/gms [target]`  
   - Permission: `basicimprovements.gamemode`  
 
 Sets the target player's gamemode to survival.
 If there is no target player, it defaults to yourself.
 
 - `/gmc [target]`  
   - Permission: `basicimprovements.gamemode`  
 
 Sets the target player's gamemode to creative.
 If there is no target player, it defaults to yourself.
 
 - `/gmsp [target]`  
   - Permission: `basicimprovements.gamemode`  
 
 Sets the target player's gamemode to spectator.
 If there is no target player, it defaults to yourself.
 
 ### Chat Messages
 All chat messages are configurable in the `config.yml` file.
 
 - New chat messages for players joining and leaving.
 - New chat message for a player joining for the first time.
 - New chat message for a player sleeping in a bed.
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
 3. Place the BasicImprovements.jar inside your server plugins folder.
 4. Start the server and the plugin will load.
 
 You can find the config.yml in the `/plugins/BasicImprovements` folder to configure the plugin.
 
 The default backups folder is: `/plugins/BasicImprovements/backups`
 
 ----
 ## Bug Report
 To open a bug report or feature request, create an issue on the repository page.
 
 ----
 ## Ideas to Implement
 - [x] Time that the player joins the server (Planned Release 2.0.0)
 - [x] Time that the player leaves the server (Planned Release 2.0.0)
 - [x] Total time that the player was online for that session (Planned Release 2.0.0)
 - [x] Time set shorthand commands (Planned Release 2.0.0)
