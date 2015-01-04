package dev.hiros.Commands.HubCommands.Commands;

import org.bukkit.entity.Player;

import dev.hiros.Commands.HubCommands.HubCommandInfo;
import dev.hiros.Commands.HubCommands.HubPluginCommand;
import dev.hiros.Hub.HubManager;

@HubCommandInfo(command="hub")
public class Hub_hub extends HubPluginCommand {
	@Override
	public void onCommand(Player player, String[] args) {
		if(HubManager.getInstance().getPlayer(player) != null) {
			HubManager.getInstance().leaveHub(player);
			HubManager.getInstance().joinHub(player);
			return;
		}
		
		HubManager.getInstance().joinHub(player);
	}
}
