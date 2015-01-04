package dev.hiros.Commands.HubCommands.Commands;

import me.dablakbandit.customentitiesapi.entities.CustomEntities;
import me.dablakbandit.customentitiesapi.entities.CustomEntitySkeleton;

import org.bukkit.ChatColor;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;

import dev.hiros.Commands.HubCommands.HubCommandInfo;
import dev.hiros.Commands.HubCommands.HubPluginCommand;
import dev.hiros.Config.Config;
import dev.hiros.Hub.Parkour.ParkourManager;
import dev.hiros.Utils.ParseColor;

@HubCommandInfo(command="spawnparkourmob")
public class Hub_spawnparkourmob extends HubPluginCommand {
	public void onCommand(Player player, String[] args) {
		if(player.hasPermission("sakuranetwork.sakuramember") || player.hasPermission("sakuranetwork.admin")) {
			if(args.length < 3) {
				player.sendMessage(ChatColor.GREEN+"SYSTEM> "+ChatColor.GRAY+"You are missing arguments.");
				return;
			}
			String path = "/hub/parkour.dat";
			Config inst = Config.getInstance();
			FileConfiguration config = inst.getConfig(path);
			
			config.set("mobs."+args[1]+".name", args[2]);
			config.set("mobs."+args[1]+".location.world", player.getLocation().getWorld().getName());
			config.set("mobs."+args[1]+".location.x", player.getLocation().getX());
			config.set("mobs."+args[1]+".location.y", player.getLocation().getY());
			config.set("mobs."+args[1]+".location.z", player.getLocation().getZ());
			config.set("mobs."+args[1]+".location.yaw", player.getLocation().getYaw());
			config.set("mobs."+args[1]+".location.pitch", player.getLocation().getPitch());
			
			inst.saveConfig(path);
			
			CustomEntitySkeleton parkourMob = CustomEntities.getNewCustomEntitySkeleton(player.getLocation());
			parkourMob.removeGoalSelectorPathfinderGoalAll();
			parkourMob.setCustomName(ParseColor.getInstance().convert(args[2]));
			parkourMob.setCustomNameVisible(true);
			parkourMob.setAbleToMove(false);
			parkourMob.setDamageable(false);
			parkourMob.setPushable(false);
			parkourMob.newGoalSelectorPathfinderGoalLookAtPlayerDefault();
			
			ParkourManager.getInstance().parkourMobs.add(parkourMob);
			return;
		}
		return;
	}
}
