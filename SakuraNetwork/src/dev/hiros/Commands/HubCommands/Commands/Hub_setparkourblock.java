package dev.hiros.Commands.HubCommands.Commands;

import org.bukkit.ChatColor;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;

import dev.hiros.Commands.HubCommands.HubCommandInfo;
import dev.hiros.Commands.HubCommands.HubPluginCommand;
import dev.hiros.Config.Config;
import dev.hiros.Hub.Parkour.ParkourBlock;
import dev.hiros.Hub.Parkour.ParkourManager;

@HubCommandInfo(command="setparkourblock")
public class Hub_setparkourblock extends HubPluginCommand {
	public void onCommand(Player player, String[] args) {
		if(player.hasPermission("sakuranetwork.sakuramember") || player.hasPermission("sakuranetwork.admin")) {
			if(args.length < 2) {
				player.sendMessage(ChatColor.GREEN+"SYSTEM> "+ChatColor.GRAY+"You are missing arguments.");
				return;
			}
			
			String path = "/hub/parkour.dat";
			Config inst = Config.getInstance();
			FileConfiguration config = inst.getConfig(path);
			
			config.set("blocks."+args[1]+".world", player.getLocation().getWorld().getName());
			config.set("blocks."+args[1]+".x", player.getLocation().getX());
			config.set("blocks."+args[1]+".y", player.getLocation().getY());
			config.set("blocks."+args[1]+".z", player.getLocation().getZ());
			
			inst.saveConfig(path);
			
			player.sendMessage(ChatColor.GREEN+"SYSTEM> "+ChatColor.GRAY+"You have set a parkour block.");
			
			ParkourManager.getInstance().parkourBlocks.add(new ParkourBlock(args[1]));
			
			return;
		}
		return;
	}
}
