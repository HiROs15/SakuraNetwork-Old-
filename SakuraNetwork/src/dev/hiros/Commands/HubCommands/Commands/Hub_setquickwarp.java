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
			String path = "/hub/quickwarpdata.sakuradata";
			
			Config.getInstance().getConfig(path).set("quickwarp."+args[1]+".location.world", player.getLocation().getWorld().getName());
			Config.getInstance().getConfig(path).set("quickwarp."+args[1]+".location.x", player.getLocation().getX());
			Config.getInstance().getConfig(path).set("quickwarp."+args[1]+".location.y", player.getLocation().getY());
			Config.getInstance().getConfig(path).set("quickwarp."+args[1]+".location.z", player.getLocation().getZ());
			Config.getInstance().getConfig(path).set("quickwarp."+args[1]+".location.yaw", player.getLocation().getYaw());
			Config.getInstance().getConfig(path).set("quickwarp."+args[1]+".location.pitch", player.getLocation().getPitch());
			Config.getInstance().saveConfig(path);
			
			player.sendMessage(ChatColor.GREEN+"SYSTEM> "+ChatColor.GRAY+"You have set the quick warp for "+args[1]+".");
			return;
		}
		player.sendMessage(ChatColor.GREEN+"SYSTEM> "+ChatColor.GRAY+"You do not have permission.");
		return;
	}
}
