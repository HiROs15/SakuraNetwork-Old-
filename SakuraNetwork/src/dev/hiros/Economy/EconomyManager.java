package dev.hiros.Economy;

import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;

import dev.hiros.SakuraNetwork;

public class EconomyManager {
	private static EconomyManager inst = new EconomyManager();
	public static EconomyManager getInstance() {
		return inst;
	}
	
	public void setupEconomyForPlayer(Player player) {
		Plugin plugin = SakuraNetwork.getInstance();
		if(plugin.getConfig().getBoolean("sakuranetwork.economy."+player.getUniqueId().toString()+".setup") != true) {
			plugin.getConfig().set("sakuranetwork.economy."+player.getUniqueId().toString()+".setup", true);
			plugin.getConfig().set("sakuranetwork.economy."+player.getUniqueId().toString()+".coins", 0);
			plugin.getConfig().set("sakuranetwork.economy."+player.getUniqueId().toString()+".credits", 500);
			plugin.saveConfig();
		}
	}
	
	public int getCoins(Player player) {
		return SakuraNetwork.getInstance().getConfig().getInt("sakuranetwork.economy."+player.getUniqueId().toString()+".coins");
	}
	
	public int getCredits(Player player) {
		return SakuraNetwork.getInstance().getConfig().getInt("sakuranetwork.economy."+player.getUniqueId().toString()+".credits");
	}
	
	public void setCoins(Player player, int value) {
		SakuraNetwork.getInstance().getConfig().set("sakuranetwork.economy."+player.getUniqueId().toString()+".coins", value);
		SakuraNetwork.getInstance().saveConfig();
	}
	
	public void setCredits(Player player, int value) {
		SakuraNetwork.getInstance().getConfig().set("sakuranetwork.economy."+player.getUniqueId().toString()+".credits", value);
		SakuraNetwork.getInstance().saveConfig();
	}
}
