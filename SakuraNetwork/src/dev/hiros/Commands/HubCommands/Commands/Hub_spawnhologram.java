package dev.hiros.Commands.HubCommands.Commands;

import org.bukkit.ChatColor;
import org.bukkit.entity.Player;

import com.gmail.filoghost.holographicdisplays.api.Hologram;
import com.gmail.filoghost.holographicdisplays.api.HologramsAPI;

import dev.hiros.SakuraNetwork;
import dev.hiros.Commands.HubCommands.HubCommandInfo;
import dev.hiros.Commands.HubCommands.HubPluginCommand;
import dev.hiros.Config.Config;
import dev.hiros.Hub.Holograms;

@HubCommandInfo(command="spawnhologram")
public class Hub_spawnhologram extends HubPluginCommand {
	@Override
	public void onCommand(Player player, String[] args) {
		if(player.hasPermission("sakuranetwork.sakuramember") || player.hasPermission("sakuranetwork.admin")) {
			if(args.length == 1) {
				player.sendMessage(ChatColor.GREEN+"SYSTEM> "+ChatColor.GRAY+"Please suppy a hologram id.");
				return;
			}
			
			String msg = "";
			switch(args[1]) {
			case "1":
				msg = ChatColor.GREEN+""+ChatColor.BOLD+"PvP Parkour";
			break;
			case "2":
				msg = ChatColor.GREEN+""+ChatColor.BOLD+"Minecraft Races";
			break;
			case "3":
				msg = ChatColor.GREEN+""+ChatColor.BOLD+"Crazy Speef";
			break;
			case "4":
				msg = ChatColor.GREEN+""+ChatColor.BOLD+"Mob Hide and Seek";
			break;
			case "5":
				msg = ChatColor.GREEN+""+ChatColor.BOLD+"One in the Chamber";
			break;
			case "6":
				msg = ChatColor.GREEN+""+ChatColor.BOLD+"Sakura Tag";
			break;
			}
			
			Hologram hgram = HologramsAPI.createHologram(SakuraNetwork.getInstance(), player.getLocation());
			hgram.appendTextLine(msg);
			
			//Save to the config
			String path = "/hub/hologram.yml";
			
			Config.getInstance().getConfig(path).set("hologram."+args[1]+".world", player.getLocation().getWorld().getName());
			Config.getInstance().getConfig(path).set("hologram."+args[1]+".x", player.getLocation().getX());
			Config.getInstance().getConfig(path).set("hologram."+args[1]+".y", player.getLocation().getY());
			Config.getInstance().getConfig(path).set("hologram."+args[1]+".z", player.getLocation().getZ());
			Config.getInstance().getConfig(path).set("hologram."+args[1]+".yaw", player.getLocation().getYaw());
			Config.getInstance().getConfig(path).set("hologram."+args[1]+".ptich", player.getLocation().getPitch());
			Config.getInstance().saveConfig(path);
			
			Holograms.getInstance().hubHolograms.add(hgram);
			
			player.sendMessage(ChatColor.GREEN+"SYSTEM> "+ChatColor.GRAY+"You have created a hologram.");
			
			return;
		}
		
		player.sendMessage(ChatColor.GREEN+"SYSTEM> "+ChatColor.GRAY+"You do not have permission to do this.");
		return;
	}
}
