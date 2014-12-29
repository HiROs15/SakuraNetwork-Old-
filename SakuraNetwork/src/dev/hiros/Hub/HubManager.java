package dev.hiros.Hub;

import java.util.ArrayList;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.GameMode;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.plugin.Plugin;

import dev.hiros.SakuraNetwork;
import dev.hiros.Economy.EconomyManager;

public class HubManager {
	private static HubManager inst = new HubManager();
	public static HubManager getInstance() {
		return inst;
	}
	
	ArrayList<HubPlayer> hubPlayers = new ArrayList<HubPlayer>();
	
	public void joinHub(Player player) {
		if(getPlayer(player) != null) {
			player.sendMessage(ChatColor.GREEN+"SYSTEM> "+ChatColor.GRAY+"You are already in the hub.");
			return;
		}
		
		if(SakuraNetwork.getInstance().getConfig().getBoolean("sakuranetwork.hub.setup") != true) {
			player.sendMessage(ChatColor.GREEN+"SYSTEM> "+ChatColor.GRAY+"The hub is currently not setup.");
			return;
		}
		
		//Start the join hub stuff
		hubPlayers.add(new HubPlayer(player));
		teleportPlayerToHub(player);
		setupHubBossBar(player);
		setupHubInventory(player);
		
		//Change player mode to adventure
		player.setGameMode(GameMode.ADVENTURE);
		
		//Setup the economy for the player joining the hub
		EconomyManager.getInstance().setupEconomyForPlayer(player);
		
		//Setup the hub scoreboard
		HubScoreboard.getInstance().showHubScoreboard(player);
	}
	
	public void teleportPlayerToHub(Player player) {
		Plugin plugin = SakuraNetwork.getInstance();
		
		if(plugin.getConfig().getBoolean("sakuranetwork.hub.setup") != true) {
			player.sendMessage(ChatColor.GREEN+"SYSTEM> "+ChatColor.GRAY+"Server cannot teleport player to hub because it is not setup.");
			return;
		}
		
		Location loc = new Location(
				Bukkit.getServer().getWorld(plugin.getConfig().getString("sakuranetwork.hub.world")),
				plugin.getConfig().getDouble("sakuranetwork.hub.x"),
				plugin.getConfig().getDouble("sakuranetwork.hub.y"),
				plugin.getConfig().getDouble("sakuranetwork.hub.z"),
				(float) plugin.getConfig().getDouble("sakuranetwork.hub.yaw"),
				(float) plugin.getConfig().getDouble("sakuranetwork.hub.pitch")
				);
		player.teleport(loc);
	}
	
	public HubPlayer getPlayer(Player player) {
		for(HubPlayer hubplayer : hubPlayers) {
			if(hubplayer.getPlayer().equals(player)) {
				return hubplayer;
			}
		}
		return null;
	}
	
	public void setupHubInventory(Player player) {
		player.getInventory().clear();
		
		player.getInventory().setItem(0, createItem(Material.SLIME_BALL, ChatColor.AQUA+"Quick Warp"));
		player.getInventory().setItem(4, createItem(Material.EMERALD, ChatColor.GOLD+"Sakura Shop"));
		player.getInventory().setItem(8, createItem(Material.REDSTONE_BLOCK, ChatColor.BLUE+"Settings"));
	}
	
	public void setupHubBossBar(Player player) {
		me.mgone.bossbarapi.BossbarAPI.setMessage(player, ChatColor.AQUA+"★ "+ChatColor.LIGHT_PURPLE+""+ChatColor.BOLD+"SAKURA NETWORK"+ChatColor.RESET+" "+ChatColor.AQUA+"★");
	}
	
	public ItemStack createItem(Material m, String name) {
		ItemStack item = new ItemStack(m);
		ItemMeta meta = item.getItemMeta();
		meta.setDisplayName(name);
		item.setItemMeta(meta);
		return item;
	}
}
