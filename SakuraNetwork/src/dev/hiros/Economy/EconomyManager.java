package dev.hiros.Economy;

import org.bukkit.entity.Player;
import dev.hiros.Config.Config;

public class EconomyManager {
	private static EconomyManager inst = new EconomyManager();
	public static EconomyManager getInstance() {
		return inst;
	}
	
	public void setupEconomyForPlayer(Player player) {
		String path = "/members/"+player.getUniqueId().toString()+".sakuradata";
		if(Config.getInstance().getConfig(path).getBoolean("economy.setup") != true) {
			Config.getInstance().getConfig(path).set("economy.setup", true);
			Config.getInstance().getConfig(path).set("economy.credits", 500);
			Config.getInstance().getConfig(path).set("economy.coins", 5);
			Config.getInstance().saveConfig(path);
		}
	}
	
	public int getCoins(Player player) {
		return Config.getInstance().getConfig("/members/"+player.getUniqueId().toString()+".sakuradata").getInt("economy.coins");
	}
	
	public int getCredits(Player player) {
		return Config.getInstance().getConfig("/members/"+player.getUniqueId().toString()+".sakuradata").getInt("economy.credits");
	}
	
	public void setCredits(Player player, int value) {
		String path = "/members/"+player.getUniqueId().toString()+".sakuradata";
		Config.getInstance().getConfig(path).set("economy.credits", value);
		Config.getInstance().saveConfig(path);
	}
	
	public void setCoins(Player player, int value) {
		String path = "/members/"+player.getUniqueId().toString()+".sakuradata";
		Config.getInstance().getConfig(path).set("economy.coins", value);
		Config.getInstance().saveConfig(path);
	}
}
