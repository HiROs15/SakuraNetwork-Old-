package dev.hiros.Commands.HubCommands.Commands;

import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;

import dev.hiros.SakuraNetwork;
import dev.hiros.Commands.HubCommands.HubCommandInfo;
import dev.hiros.Commands.HubCommands.HubPluginCommand;

@HubCommandInfo(command="sethub")
public class Hub_sethub extends HubPluginCommand {
	@Override
	public void onCommand(Player player, String[] args) {
		if(!player.hasPermission("sakuranetwork.sakuramember") || !player.hasPermission("sakuranetwork.admin")) {
			player.sendMessage(ChatColor.GREEN+"SYSTEM> "+ChatColor.GRAY+"You do not have permission.");
			return;
		}
		
		Location loc = player.getLocation();
		Plugin plugin = SakuraNetwork.getInstance();
		
		plugin.getConfig().set("sakuranetwork.hub.setup", true);
		plugin.getConfig().set("sakuranetwork.hub.world",loc.getWorld().getName());
		plugin.getConfig().set("sakuranetwork.hub.x", loc.getX());
		plugin.getConfig().set("sakuranetwork.hub.y", loc.getY());
		plugin.getConfig().set("sakuranetwork.hub.z", loc.getZ());
		plugin.getConfig().set("sakuranetwork.hub.yaw", loc.getYaw());
		plugin.getConfig().set("sakuranetwork.hub.pitch", loc.getPitch());
		plugin.saveConfig();
		
		player.sendMessage(ChatColor.GREEN+"SYSTEM> "+ChatColor.GRAY+"The hub has been setup.");
		return;
	}
}
