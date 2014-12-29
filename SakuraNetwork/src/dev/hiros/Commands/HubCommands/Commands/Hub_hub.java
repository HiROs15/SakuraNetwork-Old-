package dev.hiros.Commands.HubCommands.Commands;

import org.bukkit.ChatColor;
import org.bukkit.entity.Player;

import dev.hiros.Commands.HubCommands.HubCommandInfo;
import dev.hiros.Commands.HubCommands.HubPluginCommand;
import dev.hiros.Hub.HubManager;

@HubCommandInfo(command="hub")
public class Hub_hub extends HubPluginCommand {
	@Override
	public void onCommand(Player player, String[] args) {
		if(HubManager.getInstance().getPlayer(player) != null) {
			player.sendMessage(ChatColor.GREEN+"SYSTEM> "+ChatColor.GRAY+"You are already in the main hub.");
			return;
		}
		
		HubManager.getInstance().joinHub(player);
	}
}
