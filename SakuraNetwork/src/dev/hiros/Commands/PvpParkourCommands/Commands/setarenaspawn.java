package dev.hiros.Commands.PvpParkourCommands.Commands;

import org.bukkit.ChatColor;
import org.bukkit.entity.Player;

import dev.hiros.Commands.PvpParkourCommands.PvpParkourCommandInfo;
import dev.hiros.Commands.PvpParkourCommands.PvpParkourPluginCommand;
import dev.hiros.Config.Config;
import dev.hiros.Utils.LocationConfig;

@PvpParkourCommandInfo(command="setarenaspawn")
public class setarenaspawn extends PvpParkourPluginCommand {
	public void onCommand(Player player, String[] args) {
		if(player.hasPermission("sakuranetwork.sakuramember") || player.hasPermission("sakuranetwork.admin")) {
			if(args.length < 3) {
				player.sendMessage(ChatColor.RED+"You are missing arguments.");
				return;
			}
			
			String file = "/minigames/pvpparkour/pvpparkour.dat";
			LocationConfig.getInstance().setLocation(Config.getInstance(), player.getLocation(), file, "arenas."+args[1]+".spawns."+args[2]+".location");
			
			player.sendMessage(ChatColor.GREEN+"You have set spawn: "+args[2]+".");
			return;
		}
		return;
	}
}
