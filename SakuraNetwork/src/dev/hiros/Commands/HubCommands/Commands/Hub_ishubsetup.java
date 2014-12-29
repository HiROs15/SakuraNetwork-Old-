package dev.hiros.Commands.HubCommands.Commands;

import org.bukkit.ChatColor;
import org.bukkit.entity.Player;

import dev.hiros.SakuraNetwork;
import dev.hiros.Commands.HubCommands.HubCommandInfo;
import dev.hiros.Commands.HubCommands.HubPluginCommand;

@HubCommandInfo(command="ishubsetup")
public class Hub_ishubsetup extends HubPluginCommand {
	@Override
	public void onCommand(Player player, String[] args) {
		if(player.hasPermission("sakuranetwork.sakuramember") || player.hasPermission("sakuranetwork.admin")) {
			if(SakuraNetwork.getInstance().getConfig().getBoolean("sakuranetwork.hub.setup")) {
				player.sendMessage(ChatColor.GREEN+"SYSTEM> "+ChatColor.GRAY+"The hub is currently setup correctly.");
				return;
			} else {
				player.sendMessage(ChatColor.GREEN+"SYSTEM> "+ChatColor.GRAY+"The hub needs to be setup.");
				return;
			}
		} else {
			player.sendMessage(ChatColor.GREEN+"SYSTEM> "+ChatColor.GRAY+"You do not have permission.");
			return;
		}
	}
}
