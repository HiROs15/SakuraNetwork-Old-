package dev.hiros.Hub;

import java.util.ArrayList;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.GameMode;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.entity.Item;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.plugin.Plugin;

import dev.hiros.SakuraNetwork;
import dev.hiros.Bungee.Bungee;
import dev.hiros.Economy.EconomyManager;

public class HubManager {
	private static HubManager inst = new HubManager();
	public static HubManager getInstance() {
		return inst;
	}
	
	ArrayList<HubPlayer> hubPlayers = new ArrayList<HubPlayer>();
	
	@SuppressWarnings("deprecation")
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
		if(player.hasPermission("sakuranetwork.sakuramember") || player.hasPermission("sakuranetwork.admin") || player.hasPermission("sakuranetwork.mod")) {
			player.setGameMode(GameMode.CREATIVE);
		} else {
			player.setGameMode(GameMode.ADVENTURE);
		}
		
		//Setup the economy for the player joining the hub
		EconomyManager.getInstance().setupEconomyForPlayer(player);
		
		//Setup the hub scoreboard
		HubScoreboard.getInstance().showHubScoreboard(player);
		
		//Set the hub texture pack
		player.setResourcePack("http://sakurapack.x10.mx/HubPack5.zip");
		
		//Start the particle effects
		HubParticles.getInstance().startRunningParticle(player);
		
		//Check if they are a sakura member and play the sound
		if(player.hasPermission("sakuranetwork.sakuramember")) {
			for(HubPlayer p : hubPlayers) {
				Bukkit.getServer().dispatchCommand(Bukkit.getConsoleSender(), "playsound hub.sakuramember_join "+p.getPlayer().getName()+" "+p.getPlayer().getLocation().getX()+" "+p.getPlayer().getLocation().getY()+" "+p.getPlayer().getLocation().getZ()+" 100 1 1");
				p.getPlayer().sendMessage(ChatColor.RED+""+ChatColor.BOLD+"ATTENTION "+ChatColor.RESET+""+ChatColor.BLUE+""+player.getName()+" has just entered the hub!");
			}
		}
		
		//Bungee the players
		for(Player p : Bukkit.getOnlinePlayers()) {
			if(getPlayer(p) == null) {
				Bungee.getInstance().hidePlayers(player, p);
			}
			else if(getPlayer(p) != null) {
				Bungee.getInstance().showPlayers(player, p);
			}
		}
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
		
		player.getInventory().setItem(0, createItem(Material.SLIME_BALL, ChatColor.AQUA+""+ChatColor.BOLD+"Quick Warp "+ChatColor.RESET+""+ChatColor.GRAY+"(Right Click)"));
		player.getInventory().setItem(4, createItem(Material.EMERALD, ChatColor.GOLD+""+ChatColor.BOLD+"Sakura Shop "+ChatColor.RESET+""+ChatColor.GRAY+"(Right Click)"));
		player.getInventory().setItem(8, createItem(Material.REDSTONE_BLOCK, ChatColor.BLUE+""+ChatColor.BOLD+"Settings "+ChatColor.RESET+""+ChatColor.GRAY+"(Right Click)"));
		String coinname = ChatColor.LIGHT_PURPLE+""+ChatColor.BOLD+"Coin"+ChatColor.RESET+""+ChatColor.GRAY+" (Press Q to drop)";
		player.getInventory().setItem(2, createItem(Material.COAL, coinname));
		player.getInventory().setItem(7, createItem(Material.NETHER_STAR, ChatColor.RED+""+ChatColor.BOLD+"My Stuff "+ChatColor.RESET+""+ChatColor.GRAY+"(Right Click)"));
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
	
	public void dropSakuraCoin(Player player) {
		if(EconomyManager.getInstance().getCoins(player) > 0) {
			//spawn the item
			Location coinLoc = player.getLocation().add(0, 1, 0);
			int rand = (int)(Math.random()*99999999);
			
			Item coin = player.getLocation().getWorld().dropItem(coinLoc, new ItemStack(Material.COAL));
			ItemMeta meta = coin.getItemStack().getItemMeta();
			meta.setDisplayName("Coin"+rand);
			coin.getItemStack().setItemMeta(meta);
			coin.setVelocity(coinLoc.getDirection().multiply(0.8));
			
			EconomyManager.getInstance().setCoins(player, EconomyManager.getInstance().getCoins(player)-1);
			
			//update the hub scoreboard
			HubScoreboard.getInstance().removeHubScoreboard(player);
			HubScoreboard.getInstance().showHubScoreboard(player);
			
			//Play the cash sound to all hub players
			for(HubPlayer p : hubPlayers) {
				Bukkit.getServer().dispatchCommand(Bukkit.getConsoleSender(), "playsound hub.cash "+p.getPlayer().getName()+" "+player.getLocation().getX()+" "+player.getLocation().getY()+" "+player.getLocation().getZ()+" 1.2");
			}
		}
	}
	
	public void pickupSakuraCoin(final Player player, Item item, int ammount) {
		EconomyManager.getInstance().setCoins(player, EconomyManager.getInstance().getCoins(player)+ammount);
		
		//Update the hub scoreboard
		HubScoreboard.getInstance().removeHubScoreboard(player);
		HubScoreboard.getInstance().showHubScoreboard(player);
		
		//reset the player inventory
		
		//play the cash sound
		for(HubPlayer p : hubPlayers) {
			Bukkit.getServer().dispatchCommand(Bukkit.getConsoleSender(), "playsound hub.cash "+p.getPlayer().getName()+" "+player.getLocation().getX()+" "+player.getLocation().getY()+" "+player.getLocation().getZ()+" 1.2");
		}
		
		SakuraNetwork.getInstance().getServer().getScheduler().scheduleSyncDelayedTask(SakuraNetwork.getInstance(), new Runnable() {
			public void run() {
				player.getInventory().clear();
				setupHubInventory(player);
			}
		}, 1L);
	}
	
	@SuppressWarnings("deprecation")
	public void leaveHub(Player player) {
		//remove from hubplayers array
		hubPlayers.remove(getPlayer(player));
		
		player.getInventory().clear();
		
		HubScoreboard.getInstance().removeHubScoreboard(player);
		
		SakuraNetwork.getInstance().getLogger().info(player.getName()+" has been removed from the hub.");
		
		//Bungee the players
		for(Player p : Bukkit.getOnlinePlayers()) {
			if(getPlayer(p) != null) {
				Bungee.getInstance().hidePlayers(p, player);
			} else if(getPlayer(p) == null) {
				Bungee.getInstance().showPlayers(player, p);
			}
		}
	}
}
