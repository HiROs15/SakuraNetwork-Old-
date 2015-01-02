package dev.hiros.Hub.QuickWarp;

import java.util.ArrayList;

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
		String path = "/hub/quickwarpdata.sakuradata";
		String way = "quickwarp."+id+".location";
		Location loc = new Location(
				Bukkit.getServer().getWorld(Config.getInstance().getConfig(path).getString(way+".world")),
				Config.getInstance().getConfig(path).getDouble(way+".x"),
				Config.getInstance().getConfig(path).getDouble(way+".y"),
				Config.getInstance().getConfig(path).getDouble(way+".z"),
				(float) Config.getInstance().getConfig(path).getDouble(way+".yaw"),
				(float) Config.getInstance().getConfig(path).getDouble(way+".pitch")
				);
		player.teleport(loc);
	}
	
	public void loadPads() {
		for(String key : Config.getInstance().getConfig("/hub/quickwarp.dat").getConfigurationSection("pads").getKeys(false)) {
			quickWarpPads.add(new QuickWarpPad(key));
		}
	}
	
	public void checkPlayerOnPad(Player player) {
		for(QuickWarpPad p : quickWarpPads) {
			if(player.getLocation().distanceSquared(p.getLocation()) < 1) {
				openGameMenu(player, p.getGameId());
			}
		}
	}
	
	//Main game viewing methos
	public void openGameMenu(Player player, int id) {
		ItemMenu menu = new ItemMenu(54, ChatColor.LIGHT_PURPLE+""+ChatColor.BOLD+"Null", new ItemMenu.OptionClickEventHandler() {
			public void onOptionClick(ItemMenu.OptionClickEvent event) {
				event.setWillDestroy(true);
				HubManager.getInstance().leaveHub(event.getPlayer());
				HubManager.getInstance().joinHub(event.getPlayer());
			}
		}, SakuraNetwork.getInstance());
		for(int i=0;i<54;i++) {
			menu.setOption(i, new ItemStack(Material.REDSTONE_BLOCK, 1), ChatColor.RED+""+ChatColor.BOLD+"Server "+i, ChatColor.GRAY+"Error contacting server 192.168.1.1:2556"+i);
		}
		menu.open(player);
	}
}
