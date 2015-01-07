package dev.hiros.Hub.HubBank;

import me.dablakbandit.customentitiesapi.entities.CustomEntities;
import me.dablakbandit.customentitiesapi.entities.CustomEntityVillager;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.entity.Villager;
import org.bukkit.inventory.ItemStack;

import dev.hiros.SakuraNetwork;
import dev.hiros.Config.Config;
import dev.hiros.Economy.EconomyManager;
import dev.hiros.Hub.HubScoreboard;
import dev.hiros.Utils.ItemMenu;
import dev.hiros.Utils.LocationConfig;
public class HubBank {
	public ItemMenu bankerInv;
	
	
	private static HubBank inst = new HubBank();
	public static HubBank getInstance() {
		return inst;
	}
	
	public void spawnBanker(Player player, Location loc, int bankerid) {
		CustomEntityVillager banker = CustomEntities.getNewCustomEntityVillager(player.getLocation());
		banker.removeGoalSelectorPathfinderGoalAll();
		banker.setCustomName(ChatColor.GOLD+""+ChatColor.BOLD+"Sakura Banker");
		banker.setCustomNameVisible(true);
		banker.setAbleToMove(false);
		banker.setPushable(false);
		banker.setTradeable(false);
		banker.newGoalSelectorPathfinderGoalInteractDefault();
		
		Villager villagerbanker = (Villager) banker.getBukkitEntity();
		villagerbanker.setRemoveWhenFarAway(false);
		
		//Save banker to location
		String file = "/hub/bankers.dat";
		Config inst = Config.getInstance();
		LocationConfig.getInstance().setLocation(inst, player.getLocation(), file, "bankers.banker"+bankerid+"");
	}
	
	public void openBankerInv(final Player player) {
		bankerInv = new ItemMenu(9, ChatColor.GOLD+"Currency Exchange", new ItemMenu.OptionClickEventHandler() {
			@Override
			public void onOptionClick(ItemMenu.OptionClickEvent event) {
				event.setWillDestroy(true);
				//Handle the options
				if(event.getName().equalsIgnoreCase(ChatColor.GOLD+""+ChatColor.BOLD+"Convert Credits to Coins")) {
					convertToCoins(event.getPlayer());
					return;
				}
				
				if(event.getName().equalsIgnoreCase(ChatColor.LIGHT_PURPLE+""+ChatColor.BOLD+"Convert Coins to Credits")) {
					convertToCredits(event.getPlayer());
				}
			}
		},SakuraNetwork.getInstance());
		bankerInv.setOption(0, new ItemStack(Material.COAL, 1), ChatColor.GOLD+""+ChatColor.BOLD+"Convert Credits to Coins", ChatColor.BLUE+"250 Credits = 1 Coin");
		bankerInv.setOption(8, new ItemStack(Material.COAL, 1), ChatColor.LIGHT_PURPLE+""+ChatColor.BOLD+"Convert Coins to Credits", ChatColor.BLUE+"250 Credits = 1 Coin");
		bankerInv.open(player);
	}
	
	public void convertToCoins(Player player) {
		if(EconomyManager.getInstance().getCredits(player) < 250) {
			player.sendMessage(ChatColor.GREEN+"SYSTEM> "+ChatColor.GRAY+"You do not have enough credits.");
			
			//Play error sound
			Bukkit.getServer().dispatchCommand(Bukkit.getConsoleSender(), "playsound hub.error "+player.getName()+" "+player.getLocation().getX()+" "+player.getLocation().getY()+" "+player.getLocation().getZ()+" 100 1 1");
			return;
		}
		
		EconomyManager.getInstance().setCredits(player, EconomyManager.getInstance().getCredits(player)-250);
		EconomyManager.getInstance().setCoins(player, EconomyManager.getInstance().getCoins(player)+1);
		player.sendMessage(ChatColor.GREEN+"SYSTEM> "+ChatColor.GRAY+"You have converted 250 credits into 1 coins.");
		
		//Update the hubscoreboard
		HubScoreboard.getInstance().removeHubScoreboard(player);
		HubScoreboard.getInstance().showHubScoreboard(player);
		
		//play coin sound
		Bukkit.getServer().dispatchCommand(Bukkit.getConsoleSender(), "playsound hub.cash "+player.getName()+" "+player.getLocation().getX()+" "+player.getLocation().getY()+" "+player.getLocation().getZ()+" 100 1 1");
		return;
	}
	
	public void convertToCredits(Player player) {
		if(EconomyManager.getInstance().getCoins(player) < 1) {
			player.sendMessage(ChatColor.GREEN+"SYSTEM> "+ChatColor.GRAY+"You do not have enough coins.");
			Bukkit.getServer().dispatchCommand(Bukkit.getConsoleSender(), "playsound hub.error "+player.getName()+" "+player.getLocation().getX()+" "+player.getLocation().getY()+" "+player.getLocation().getZ()+" 100 1 1");
			return;
		}
		
		EconomyManager.getInstance().setCredits(player, EconomyManager.getInstance().getCredits(player)+250);
		EconomyManager.getInstance().setCoins(player, EconomyManager.getInstance().getCoins(player)-1);
		player.sendMessage(ChatColor.GREEN+"SYSTEM> "+ChatColor.GRAY+"You have converted 1 Coin into 250 Credits.");
		
		//Update hub scoreboard
		HubScoreboard.getInstance().removeHubScoreboard(player);
		HubScoreboard.getInstance().showHubScoreboard(player);
		Bukkit.getServer().dispatchCommand(Bukkit.getConsoleSender(), "playsound hub.cash "+player.getName()+" "+player.getLocation().getX()+" "+player.getLocation().getY()+" "+player.getLocation().getZ()+" 100 1 1");
		return;
	}
}
