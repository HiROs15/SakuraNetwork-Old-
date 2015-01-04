package dev.hiros.Hub.Parkour;

import java.util.ArrayList;

import me.dablakbandit.customentitiesapi.entities.CustomEntities;
import me.dablakbandit.customentitiesapi.entities.CustomEntitySkeleton;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.GameMode;
import org.bukkit.Location;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.entity.Skeleton;

import dev.hiros.Config.Config;
import dev.hiros.Hub.HubManager;
import dev.hiros.Utils.ParseColor;

public class ParkourManager {
	public static ParkourManager inst = new ParkourManager();
	public static ParkourManager getInstance() {
		return inst;
	}
	
	public ArrayList<CustomEntitySkeleton> parkourMobs = new ArrayList<CustomEntitySkeleton>();
	
	public ArrayList<ParkourBlock> parkourBlocks = new ArrayList<ParkourBlock>();
	
	public ArrayList<Player> parkourPlayers = new ArrayList<Player>();
	
	public void removeParkourMobs() {
		for(CustomEntitySkeleton s : parkourMobs) {
			s.setHealth(0);
		}
	}
	
	public void reloadParkourMobs() {
		String path = "/hub/parkour.dat";
		Config inst = Config.getInstance();
		FileConfiguration config = inst.getConfig(path);
		//Spawn all the mobs
		for(String key : config.getConfigurationSection("mobs").getKeys(false)) {
			Location loc = new Location(
					Bukkit.getServer().getWorld(config.getString("mobs."+key+".location.world")),
					config.getDouble("mobs."+key+".location.x"),
					config.getDouble("mobs."+key+".location.y"),
					config.getDouble("mobs."+key+".location.z")
					);
			
			String name = ParseColor.getInstance().convert(config.getString("mobs."+key+".name"));
			
			CustomEntitySkeleton parkourMob = CustomEntities.getNewCustomEntitySkeleton(loc);
			parkourMob.removeGoalSelectorPathfinderGoalAll();
			parkourMob.setCustomName(name);
			parkourMob.setAbleToMove(false);
			parkourMob.setDamageable(false);
			parkourMob.setPushable(false);
			parkourMob.newGoalSelectorPathfinderGoalLookAtPlayerDefault();
			Skeleton sk = (Skeleton) parkourMob.getBukkitEntity();
			sk.setRemoveWhenFarAway(false);
			
			parkourMobs.add(parkourMob);
		}
	}
	
	public boolean checkIfParkourMobExists(String name) {
		String path = "/hub/parkour.dat";
		Config inst = Config.getInstance();
		FileConfiguration config = inst.getConfig(path);
		
		for(String key : config.getConfigurationSection("mobs").getKeys(false)) {
			String kname = ParseColor.getInstance().convert(config.getString("mobs."+key+".name"));
			if(kname.equalsIgnoreCase(name)) {
				return true;
			}
		}
		return false;
	}
	
	public void loadParkourBlocks() {
		String path = "/hub/parkour.dat";
		Config inst = Config.getInstance();
		FileConfiguration config = inst.getConfig(path);
		
		for(String key : config.getConfigurationSection("blocks").getKeys(false)) {
			parkourBlocks.add(new ParkourBlock(key));
		}
	}
	
	public Player getPlayer(String name) {
		for(Player p : parkourPlayers) {
			if(p.getName().equalsIgnoreCase(name)) {
				return p;
			}
		}
		return null;
	}
	
	public void joinParkourMode(Player player) {
		if(HubManager.getInstance().getPlayer(player) == null) {
			player.sendMessage(ChatColor.RED+"PARKOUR> "+ChatColor.GRAY+"You are not in the hub.");
			return;
		}
		
		if(getPlayer(player.getName()) != null) {
			leaveParkourMode(player);
			return;
		}
		player.getInventory().clear();
		player.setGameMode(GameMode.ADVENTURE);
		
		parkourPlayers.add(player);
		
		player.sendMessage(ChatColor.GREEN+"PARKOUR> "+ChatColor.GRAY+"You are now in parkour mode. You have to re enter parkour mode if you fail.");
		return;
	}
	
	public void leaveParkourMode(Player player) {
		if(HubManager.getInstance().getPlayer(player) == null) {
			player.sendMessage(ChatColor.RED+"PARKOUR> "+ChatColor.GRAY+"You need to be in the hub.");
			return;
		}
		
		if(getPlayer(player.getName()) != null) {
			parkourPlayers.remove(player);
			HubManager.getInstance().setupHubInventory(player);
			if(player.hasPermission("sakuranetwork.sakuramember") || player.hasPermission("sakuranetwork.admin")) {
				player.setGameMode(GameMode.CREATIVE);
			} else {
				player.setGameMode(GameMode.ADVENTURE);
			}
			
			player.sendMessage(ChatColor.GREEN+"PARKOUR> "+ChatColor.GRAY+"You have been removed from Parkour Mode.");
			return;
		}
	}
	
	public void checkPlayerDistanceFromBlock(Player player) {
		boolean check = false;
		for(ParkourBlock block : parkourBlocks) {
			if(player.getLocation().distance(block.getLocation()) < 10) {
				check = true;
				return;
			}
		}
		
		if(check == false) {
			leaveParkourMode(player);
		}
	}
}
