package dev.hiros.Commands.PvpParkourCommands.Commands;

import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;

import dev.hiros.Commands.PvpParkourCommands.PvpParkourCommandInfo;
import dev.hiros.Commands.PvpParkourCommands.PvpParkourPluginCommand;
import dev.hiros.Config.Config;
import dev.hiros.Utils.LocationConfig;

@PvpParkourCommandInfo(command="warparena")
public class warparena extends PvpParkourPluginCommand {
	public void onCommand(Player player, String[] args) {
		if(player.hasPermission("sakuranetwork.sakuramember") || player.hasPermission("sakuranetwork.admin")) {
			if(args.length < 2) {
				player.sendMessage(ChatColor.RED+"You are missing arguments.");
				return;
			}
			
			String file = "/minigames/pvpparkour/pvpparkour.dat";
			Config inst = Config.getInstance();
			FileConfiguration config = inst.getConfig(file);
			
			Location loc = LocationConfig.getInstance().getLocation(config, "arenas."+args[1]+".location");
			player.teleport(loc);
			
			player.sendMessage(ChatColor.GREEN+"You have been teleported to arena "+args[1]+"");
			return;
		}
		return;
	}
}
