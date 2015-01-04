package dev.hiros.Commands.HubCommands.Commands;

import org.bukkit.ChatColor;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;

import dev.hiros.Commands.HubCommands.HubCommandInfo;
import dev.hiros.Commands.HubCommands.HubPluginCommand;
import dev.hiros.Config.Config;

@HubCommandInfo(command="setquickwarppad")
public class Hub_setquickwarppad extends HubPluginCommand {
	@Override
	public void onCommand(Player player, String[] args) {
		if(player.hasPermission("sakuranetwork.sakuramember") || player.hasPermission("sakuranetwork.admin")) {
			if(args.length > 3) {
				player.sendMessage(ChatColor.GREEN+"SYSTEM> "+ChatColor.GRAY+"You are missing arguments.");
				return;
			}
			
			//Save the file to the config.
			Config inst = Config.getInstance();
			FileConfiguration config = inst.getConfig("/hub/quickwarp.dat");
			config.set("pads."+args[2]+".gameid", Integer.parseInt(args[1]));
			config.set("pads."+args[2]+".location.world", player.getLocation().getWorld().getName());
			config.set("pads."+args[2]+".location.x", player.getLocation().getX());
			config.set("pads."+args[2]+".location.y", player.getLocation().getY());
			config.set("pads."+args[2]+".location.z", player.getLocation().getZ());
			config.set("pads."+args[2]+".location.yaw", player.getLocation().getYaw());
			config.set("pads."+args[2]+".location.pitch", player.getLocation().getPitch());
			inst.saveConfig("/hub/quickwarp.dat");
			
			player.sendMessage(ChatColor.GREEN+"SYSTEM> "+ChatColor.GRAY+"You have setup a pad.");
			return;
		}
		player.sendMessage(ChatColor.GREEN+"SYSTEM> "+ChatColor.GRAY+"You do not have permission.");
		return;
	}
}
