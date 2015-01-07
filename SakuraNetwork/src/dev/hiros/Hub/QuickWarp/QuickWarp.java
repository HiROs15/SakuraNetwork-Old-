package dev.hiros.Hub.QuickWarp;

import java.util.ArrayList;
import java.util.HashMap;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import dev.hiros.SakuraNetwork;
import dev.hiros.Config.Config;
import dev.hiros.Hub.HubManager;
import dev.hiros.Utils.ItemMenu;

public class QuickWarp {
	private static QuickWarp inst = new QuickWarp();
	public static QuickWarp getInstance() {
		return inst;
	}
	
	public ArrayList<QuickWarpPad> quickWarpPads = new ArrayList<QuickWarpPad>();
	
	HashMap<String, QuickWarpPad> gameMenuTimer = new HashMap<String, QuickWarpPad>();
	
	public void openInventory(Player player) {
		ItemMenu quickwarpinv = new ItemMenu(9, ChatColor.LIGHT_PURPLE+"Quick Warp", new ItemMenu.OptionClickEventHandler() {
			@Override
			public void onOptionClick(ItemMenu.OptionClickEvent event) {
				Player player = event.getPlayer();
				event.setWillDestroy(true);
				
				if(event.getName().equals(ChatColor.AQUA+""+ChatColor.BOLD+"Pvp Parkour")) {
					warp(player, 1);
				}
				
				if(event.getName().equals(ChatColor.AQUA+""+ChatColor.BOLD+"Minecraft Races")) {
					warp(player, 2);
				}
				
				if(event.getName().equals(ChatColor.AQUA+""+ChatColor.BOLD+"Crazy Spleef")) {
					warp(player, 3);
				}
				
				if(event.getName().equals(ChatColor.AQUA+""+ChatColor.BOLD+"Mob Hide and Seek")) {
					warp(player, 4);
				}
				
				if(event.getName().equals(ChatColor.AQUA+""+ChatColor.BOLD+"One in the Chamber")) {
					warp(player, 5);
				}
			}
		}, SakuraNetwork.getInstance());
		quickwarpinv.setOption(0, new ItemStack(Material.QUARTZ_BLOCK, 1), ChatColor.AQUA+""+ChatColor.BOLD+"Pvp Parkour");
		quickwarpinv.setOption(1, new ItemStack(Material.MINECART, 1), ChatColor.AQUA+""+ChatColor.BOLD+"Minecraft Races");
		quickwarpinv.setOption(2, new ItemStack(Material.DIAMOND_SPADE, 1), ChatColor.AQUA+""+ChatColor.BOLD+"Crazy Spleef");
		quickwarpinv.setOption(3, new ItemStack(Material.MELON_BLOCK, 1), ChatColor.AQUA+""+ChatColor.BOLD+"Mob Hide and Seek");
		quickwarpinv.setOption(4, new ItemStack(Material.BOW, 1), ChatColor.AQUA+""+ChatColor.BOLD+"One in the Chamber");
		
		quickwarpinv.open(player);
	}
	
	public void warp(Player player, int id) {
		String path = "/hub/quickwarp.dat";
		String way = "quickwarp."+id+".location";
		Config inst = Config.getInstance();
		Location loc = new Location(
				Bukkit.getServer().getWorld(inst.getConfig(path).getString(way+".world")),
				inst.getConfig(path).getDouble(way+".x"),
				inst.getConfig(path).getDouble(way+".y"),
				inst.getConfig(path).getDouble(way+".z"),
				(float) inst.getConfig(path).getDouble(way+".yaw"),
				(float) inst.getConfig(path).getDouble(way+".pitch")
				);
		player.teleport(loc);
	}
	
	public void loadPads() {
		for(String key : Config.getInstance().getConfig("/hub/quickwarp.dat").getConfigurationSection("pads").getKeys(false)) {
			quickWarpPads.add(new QuickWarpPad(key));
		}
	}
	
	public boolean isPlayerGameMenuTimeing(Player player) {
		return gameMenuTimer.containsKey(player.getName());
	}
	
	public void checkPlayerOnPad(Player player) {
		for(QuickWarpPad p : quickWarpPads) {
			if(player.getLocation().distance(p.getLocation()) < 1) {
				if(isPlayerGameMenuTimeing(player)) {
					return;
				}
				gameMenuTimer.put(player.getName(), p);
				openGameMenu(player, p.getGameId());
				return;
			}
			
			if(isPlayerGameMenuTimeing(player)) {
				if(player.getLocation().distance(gameMenuTimer.get(player.getName()).getLocation()) > 3) {
					gameMenuTimer.remove(player.getName());
				}
			}
		}
	}
	
	//Main game viewing methos
	public void openGameMenu(Player player, int id) {
		ItemMenu gameMenu = new ItemMenu(54, ChatColor.LIGHT_PURPLE+""+ChatColor.BOLD+"Choose a Map", new ItemMenu.OptionClickEventHandler() {
			@Override
			public void onOptionClick(ItemMenu.OptionClickEvent event) {
				event.setWillDestroy(true);
				event.setWillClose(true);
				
				HubManager.getInstance().leaveHub(event.getPlayer());
				
				//PvpParkour action
				if(event.getName().equalsIgnoreCase(ChatColor.GREEN+""+ChatColor.BOLD+"Phantom")) {
					dev.hiros.Minigames.PvpParkour.Lobby.LobbyManager.getInstance().getLobby("phantom").joinLobby(event.getPlayer());
					return;
				}
				
				HubManager.getInstance().joinHub(event.getPlayer());
			}
		}, SakuraNetwork.getInstance());
		if(id == 1) {
			if(dev.hiros.Minigames.PvpParkour.Lobby.LobbyManager.getInstance().findOpenArena("phantom") == "none") {
				for(int i = 0; i<54; i++) {
					gameMenu.setOption(i, new ItemStack(Material.REDSTONE_BLOCK, 1), ChatColor.RED+""+ChatColor.BOLD+"No Servers Could be Found");
				}
			} else {
				gameMenu.setOption(0, new ItemStack(Material.EMERALD_BLOCK, 1), ChatColor.GREEN+""+ChatColor.BOLD+"Phantom", ChatColor.GRAY+""+dev.hiros.Minigames.PvpParkour.Lobby.LobbyManager.getInstance().getLobby("phantom").getOnlinePlayers()+"/10", ChatColor.WHITE+"Map: "+ChatColor.YELLOW+"Phantom", ChatColor.WHITE+"Server: "+ChatColor.YELLOW+""+dev.hiros.Minigames.PvpParkour.Lobby.LobbyManager.getInstance().findOpenArena("phantom"));
			}
		}
		
		gameMenu.setOption(53, new ItemStack(Material.ARROW, 1), ChatColor.BLUE+""+ChatColor.BOLD+"Return to Hub", ChatColor.GRAY+"Click to return to the hub.");
		gameMenu.open(player);
	}
}
