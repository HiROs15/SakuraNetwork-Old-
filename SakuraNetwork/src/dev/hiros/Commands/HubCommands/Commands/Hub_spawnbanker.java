package dev.hiros.Commands.HubCommands.Commands;

import org.bukkit.ChatColor;
import org.bukkit.entity.Player;

import dev.hiros.Commands.HubCommands.HubCommandInfo;
import dev.hiros.Commands.HubCommands.HubPluginCommand;
import dev.hiros.Hub.HubBank.HubBank;

@HubCommandInfo(command="spawnbanker")
public class Hub_spawnbanker extends HubPluginCommand {
	@Override
	public void onCommand(Player player, String[] args) {
		if(!player.hasPermission("sakuranetwork.sakuramember") || !player.hasPermission("sakuranetwork.admin")) {
			player.sendMessage(ChatColor.GREEN+"SYSTEM> "+ChatColor.GRAY+"You do not have permission.");
			return;
		}
		
		if(args.length < 2) {
			player.sendMessage(ChatColor.GREEN+"SYSTEM> "+ChatColor.GRAY+"You need to specify a banker id.");
			return;
		}
		
		HubBank.getInstance().spawnBanker(player, player.getLocation(), Integer.parseInt(args[1]));
		player.sendMessage(ChatColor.GREEN+"You have spawn a hub banker!");
		return;
	}
}
