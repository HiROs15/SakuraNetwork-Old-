package dev.hiros.Commands.HubCommands.Commands;

import org.bukkit.ChatColor;
import org.bukkit.entity.Player;

import dev.hiros.Commands.HubCommands.HubCommandInfo;
import dev.hiros.Commands.HubCommands.HubPluginCommand;
import dev.hiros.Hub.HubManager;

@HubCommandInfo(command="leavehub")
public class Hub_leavehub extends HubPluginCommand {
	@Override
	public void onCommand(Player player, String[] args) {
		if(player.hasPermission("sakuranetwork.sakuramember") || player.hasPermission("sakuranetwork.admin")) {
			HubManager.getInstance().leaveHub(player);
			player.sendMessage(ChatColor.GREEN+"SYSTEM> "+ChatColor.GRAY+"You have left the hub.");
			return;
		}
		player.sendMessage(ChatColor.GREEN+"SYSTEM> "+ChatColor.GRAY+"You do not have permission.");
		return;
	}
}
