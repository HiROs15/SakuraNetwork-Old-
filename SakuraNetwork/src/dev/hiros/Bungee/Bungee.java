package dev.hiros.Bungee;

import org.bukkit.entity.Player;

public class Bungee {
	private static Bungee inst = new Bungee();
	public static Bungee getInstance() {
		return inst;
	}
	
	public void hidePlayers(Player player, Player target) {
		player.hidePlayer(target);
		target.hidePlayer(player);
	}
	
	public void showPlayers(Player player, Player target) {
		player.showPlayer(target);
		target.showPlayer(player);
	}
}
