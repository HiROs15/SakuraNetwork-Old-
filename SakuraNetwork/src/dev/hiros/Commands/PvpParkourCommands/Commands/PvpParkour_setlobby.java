package dev.hiros.Commands.PvpParkourCommands.Commands;

import org.bukkit.ChatColor;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;

import dev.hiros.Commands.PvpParkourCommands.PvpParkourCommandInfo;
import dev.hiros.Commands.PvpParkourCommands.PvpParkourPluginCommand;
import dev.hiros.Config.Config;

@PvpParkourCommandInfo(command="setlobby")
public class PvpParkour_setlobby extends PvpParkourPluginCommand {
	public void onCommand(Player player, String[] args) {
		if(player.hasPermission("sakuranetwork.sakuramember") || player.hasPermission("sakuranetwork.admin")) {
			if(args.length < 2) {
				player.sendMessage(ChatColor.RED+"PVPPARKOUR> "+ChatColor.GRAY+"You are missing arguments.");
				return;
			}
			
			String path = "/minigames/pvpparkour/pvpparkour.dat";
			Config inst = Config.getInstance();
			FileConfiguration config = inst.getConfig(path);
			
			String dat = "lobbies."+args[1]+"";
			
			config.set(dat+".name", args[1]);
			config.set(dat+".location.world", player.getLocation().getWorld().getName());
			config.set(dat+".location.x", player.getLocation().getX());
			config.set(dat+".location.y", player.getLocation().getY());
			config.set(dat+".location.z", player.getLocation().getZ());
			config.set(dat+".location.yaw", player.getLocation().getYaw());
			config.set(dat+".location.pitch", player.getLocation().getPitch());
			inst.saveConfig(path);
			inst.reloadConfig(path);
			
			player.sendMessage(ChatColor.GREEN+"PVPPARKOUR> "+ChatColor.GRAY+"You have saved the lobby for map "+args[1]+".");
			return;
		}
		return;
	}
}
