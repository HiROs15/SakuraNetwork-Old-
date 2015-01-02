package dev.hiros.Commands.SakuraCommands.Commands;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;

import dev.hiros.Commands.SakuraCommands.SakuraCommandInfo;
import dev.hiros.Commands.SakuraCommands.SakuraPluginCommand;
import dev.hiros.Config.Config;

@SakuraCommandInfo(command="giveparticleeffect")
public class Sakura_giveparticleeffect extends SakuraPluginCommand {
	@Override
	public void onCommand(Player player, String[] args) {
		if(player.hasPermission("sakuranetwork.sakuramember") || player.hasPermission("sakuranetwork.adim")) {
			if(args.length < 3) {
				player.sendMessage(ChatColor.GREEN+"SYSTEM> "+ChatColor.GRAY+"You are missing arguments.");
				return;
			}
			FileConfiguration config = Config.getInstance().getConfig("/members/"+Bukkit.getServer().getPlayer(args[1]).getUniqueId().toString()+".sakuradata");
			config.set("stuff.mystuff.particles."+args[2]+"", true);
			Config.getInstance().saveConfig("/members/"+Bukkit.getServer().getPlayer(args[1]).getUniqueId().toString()+".sakuradata");
			
			player.sendMessage(ChatColor.GREEN+"SYSTEM> "+ChatColor.GRAY+"You have given the particle effect.");
			return;
		}
		player.sendMessage(ChatColor.GREEN+"SYSTEM> "+ChatColor.GRAY+"You do not have permission.");
		return;
	}
}
