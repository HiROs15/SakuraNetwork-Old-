package dev.hiros.Commands.HubCommands.Commands;

import org.bukkit.ChatColor;
import org.bukkit.entity.Player;

import dev.hiros.Commands.HubCommands.HubCommandInfo;
import dev.hiros.Commands.HubCommands.HubPluginCommand;
import dev.hiros.Config.Config;

@HubCommandInfo(command="setquickwarp")
public class Hub_setquickwarp extends HubPluginCommand {
	@Override
	public void onCommand(Player player, String[] args) {
		if(player.hasPermission("sakuranetwork.sakuramember") || player.hasPermission("sakuranetwork.admin")) {
			if(args.length == 1) {
				player.sendMessage(ChatColor.GREEN+"SYSTEM> "+ChatColor.GRAY+"You need to specify the warp id.");
				return;
			}
			String path = "/hub/quickwarp.dat";
			Config inst = Config.getInstance();
			
			inst.getConfig(path).set("quickwarp."+args[1]+".location.world", player.getLocation().getWorld().getName());
			inst.getConfig(path).set("quickwarp."+args[1]+".location.x", player.getLocation().getX());
			inst.getConfig(path).set("quickwarp."+args[1]+".location.y", player.getLocation().getY());
			inst.getConfig(path).set("quickwarp."+args[1]+".location.z", player.getLocation().getZ());
			inst.getConfig(path).set("quickwarp."+args[1]+".location.yaw", player.getLocation().getYaw());
			inst.getConfig(path).set("quickwarp."+args[1]+".location.pitch", player.getLocation().getPitch());
			inst.saveConfig(path);
			
			player.sendMessage(ChatColor.GREEN+"SYSTEM> "+ChatColor.GRAY+"You have set the quick warp for "+args[1]+".");
			return;
		}
		player.sendMessage(ChatColor.GREEN+"SYSTEM> "+ChatColor.GRAY+"You do not have permission.");
		return;
	}
}
