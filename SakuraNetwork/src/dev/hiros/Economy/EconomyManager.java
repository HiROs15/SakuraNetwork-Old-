package dev.hiros.Economy;

import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;

import dev.hiros.Config.Config;

public class EconomyManager {
	private static EconomyManager inst = new EconomyManager();
	public static EconomyManager getInstance() {
		return inst;
	}
	
	public void setupEconomyForPlayer(Player player) {
		String path = "/members/"+player.getName()+".dat";
		Config inst = Config.getInstance();
		FileConfiguration config = inst.getConfig(path);
		if(config.getBoolean("economy.setup") != true) {
			config.set("economy.setup", true);
			config.set("economy.credits", 0);
			config.set("economy.coins", 5);
			inst.saveConfig(path);
		}
	}
	
	public int getCoins(Player player) {
		return Config.getInstance().getConfig("/members/"+player.getName()+".dat").getInt("economy.coins");
	}
	
	public int getCredits(Player player) {
		return Config.getInstance().getConfig("/members/"+player.getName()+".dat").getInt("economy.credits");
	}
	
	public void setCredits(Player player, int value) {
		String path = "/members/"+player.getName()+".dat";
		Config inst = Config.getInstance();
		inst.getConfig(path).set("economy.credits", value);
		inst.saveConfig(path);
	}
	
	public void setCoins(Player player, int value) {
		String path = "/members/"+player.getName()+".dat";
		Config inst = Config.getInstance();
		inst.getConfig(path).set("economy.coins", value);
		inst.saveConfig(path);
	}
}
