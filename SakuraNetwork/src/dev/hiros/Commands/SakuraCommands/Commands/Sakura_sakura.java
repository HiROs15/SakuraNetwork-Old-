package dev.hiros.Commands.SakuraCommands.Commands;

import org.bukkit.ChatColor;
import org.bukkit.entity.Player;

import dev.hiros.Commands.SakuraCommands.SakuraCommandInfo;
import dev.hiros.Commands.SakuraCommands.SakuraPluginCommand;

@SakuraCommandInfo(command="sakura")
public class Sakura_sakura extends SakuraPluginCommand {
	@Override
	public void onCommand(Player player, String[] args) {
		if(args.length > 0) {
			player.sendMessage("I have failed.");
			return;
		}
		
		player.sendMessage(ChatColor.AQUA+"=========="+ChatColor.YELLOW+"Sakura Command Help"+ChatColor.AQUA+"==========");
		player.sendMessage(" ");
		player.sendMessage(ChatColor.GOLD+"/sakura addcoins <player> <ammount>");
		player.sendMessage(ChatColor.GOLD+"/sakura removecoins <player> <ammount>");
		player.sendMessage(ChatColor.GOLD+"/sakura setcoins <player> <ammount>");
		player.sendMessage(ChatColor.GOLD+"/sakura resetstats <player>");
		return;
	}
}
