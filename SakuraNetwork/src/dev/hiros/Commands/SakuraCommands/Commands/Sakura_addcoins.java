package dev.hiros.Commands.SakuraCommands.Commands;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;

import dev.hiros.Commands.SakuraCommands.SakuraCommandInfo;
import dev.hiros.Commands.SakuraCommands.SakuraPluginCommand;
import dev.hiros.Economy.EconomyManager;
import dev.hiros.Hub.HubManager;
import dev.hiros.Hub.HubScoreboard;

@SakuraCommandInfo(command="addcoins")
public class Sakura_addcoins extends SakuraPluginCommand {
	@Override
	public void onCommand(Player player, String[] args) {
		if(!player.hasPermission("sakuranetwork.sakuramember") && !player.hasPermission("sakuranetwork.admin")) {
			player.sendMessage(ChatColor.GREEN+"SYSTEM> "+ChatColor.GRAY+"You do not have permission to use this.");
			return;
		}
		
		if(args.length != 3) {
			player.sendMessage(ChatColor.GREEN+"SYSTEM> "+ChatColor.GRAY+"Invalid arguments");
			return;
		}
		
		if(!Bukkit.getServer().getPlayer(args[1]).isOnline()) {
			player.sendMessage(ChatColor.GREEN+"SYSTEM> "+ChatColor.GRAY+"That player is not online.");
			return;
		}
		
		EconomyManager.getInstance().setCoins(Bukkit.getServer().getPlayer(args[1]), EconomyManager.getInstance().getCoins(Bukkit.getServer().getPlayer(args[1]))+Integer.parseInt(args[2]));
		player.sendMessage(ChatColor.GREEN+"SYSTEM> "+ChatColor.GRAY+"You have added "+args[2]+" coins to "+args[1]+"'s account.");
		Bukkit.getServer().getPlayer(args[1]).sendMessage(ChatColor.GREEN+"SYSTEM> "+ChatColor.GRAY+""+args[2]+" coins has been added to your account.");
		
		//update the players scoreboard if theyre in the hub.
		Player targ = Bukkit.getServer().getPlayer(args[1]);
		if(HubManager.getInstance().getPlayer(targ) != null) {
			HubScoreboard.getInstance().removeHubScoreboard(targ);
			HubScoreboard.getInstance().showHubScoreboard(targ);
		}
		return;
	}
}
