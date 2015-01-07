package dev.hiros.Commands.PvpParkourCommands.Commands;

import org.bukkit.ChatColor;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;

import dev.hiros.Commands.PvpParkourCommands.PvpParkourCommandInfo;
import dev.hiros.Commands.PvpParkourCommands.PvpParkourPluginCommand;
import dev.hiros.Config.Config;
import dev.hiros.Utils.LocationConfig;

@PvpParkourCommandInfo(command="setarena")
public class PvpParkour_setarena extends PvpParkourPluginCommand {
	public void onCommand(Player player, String[] args) {
		if(player.hasPermission("sakuranetwork.sakuramember") || player.hasPermission("sakuranetwork.admin")) {
			if(args.length < 3) {
				player.sendMessage(ChatColor.RED+"PVPPARKOUR> "+ChatColor.GRAY+"You are missing arguments.");
				return;
			}
			
			String file = "/minigames/pvpparkour/pvpparkour.dat";
			Config inst = Config.getInstance();
			@SuppressWarnings("unused")
			FileConfiguration config = null;
			
			LocationConfig.getInstance().setLocation(inst, player.getLocation(), file, "arenas."+args[2]+".location");
			Config insta = Config.getInstance();
			FileConfiguration configa = insta.getConfig(file);
			configa.set("arenas."+args[2]+".name", args[2]);
			configa.set("arenas."+args[2]+".map", args[1]);
			
			insta.saveConfig(file);
			insta.reloadConfig(file);
			
			player.sendMessage(ChatColor.GREEN+"PVPPARKOUR> "+ChatColor.GRAY+"You have created an arena for pvpparkour.");
			return;
		}
		return;
	}
}

