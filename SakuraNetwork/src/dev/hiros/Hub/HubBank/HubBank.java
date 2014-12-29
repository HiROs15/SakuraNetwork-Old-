package dev.hiros.Hub.HubBank;

import java.util.ArrayList;

import me.dablakbandit.customentitiesapi.entities.CustomEntities;
import me.dablakbandit.customentitiesapi.entities.CustomEntityVillager;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;

import dev.hiros.SakuraNetwork;

public class HubBank {
	private static HubBank inst = new HubBank();
	public static HubBank getInstance() {
		return inst;
	}
	
	ArrayList<HubBanker> hubBankers = new ArrayList<HubBanker>();
	
	
	public void spawnBanker(Player player, Location loc, int bankerid) {
		Plugin plugin = SakuraNetwork.getInstance();
		
		String path = "sakuranetwork.hub.bankers."+bankerid+".location";
		
		//Save the banker location info
		
		plugin.getConfig().set(path+".world", loc.getWorld());
		plugin.getConfig().set(path+".x", loc.getX());
		plugin.getConfig().set(path+".y", loc.getY());
		plugin.getConfig().set(path+".z", loc.getZ());
		plugin.getConfig().set(path+".yaw", loc.getYaw());
		plugin.getConfig().set(path+".pitch", loc.getPitch());
		plugin.saveConfig();
		plugin.reloadConfig();
		
		//Spawn in a temp villager
		CustomEntityVillager banker = CustomEntities.getNewCustomEntityVillager(loc);
		banker.setCustomName(ChatColor.GOLD+""+ChatColor.BOLD+"Sakura Banker");
		banker.setCustomNameVisible(true);
		banker.setAbleToMove(false);
		banker.setPushable(false);
		banker.setDamageable(false);
		banker.setTradeable(false);
		banker.newGoalSelectorPathfinderGoalLookAtPlayer(0);
		
		//Add the banker to array. Create the banker object
		hubBankers.add(new HubBanker(bankerid, banker));
		
		//Send the player a confirmed messaged 
		player.sendMessage(ChatColor.GREEN+"SYSTEM> "+ChatColor.GRAY+"A Hub banker with the id: "+bankerid+" has been created.");
	}
	
	public void removeAllBankers() {
		for(HubBanker banker : hubBankers) {
			banker.getBanker().setHealth(0);
		}
		hubBankers.clear();
	}
	
	public void loadBankers() {
		Plugin plugin = SakuraNetwork.getInstance();
		
		for(String id : plugin.getConfig().getConfigurationSection("sakuranetwork.hub.bankers").getKeys(false)) {
			String path = "sakuranetwork.hub.bankers."+id+".location";
			Location loc = new Location(
					Bukkit.getWorld(plugin.getConfig().getString(path+".world")),
					plugin.getConfig().getDouble(path+".x"),
					plugin.getConfig().getDouble(path+".y"),
					plugin.getConfig().getDouble(path+".z"),
					(float) plugin.getConfig().getDouble(path+".yaw"),
					(float) plugin.getConfig().getDouble(path+".pitch")
					);
			
			int bankerid = Integer.parseInt(id);
			
			CustomEntityVillager banker = CustomEntities.getNewCustomEntityVillager(loc);
			banker.setCustomName(ChatColor.GOLD+""+ChatColor.BOLD+"Sakura Banker");
			banker.setCustomNameVisible(true);
			banker.setAbleToMove(false);
			banker.setPushable(false);
			banker.setDamageable(false);
			banker.setTradeable(false);
			banker.newGoalSelectorPathfinderGoalLookAtPlayer(0);
			
			hubBankers.add(new HubBanker(bankerid, banker));
		}
	}
}
