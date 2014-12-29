package dev.hiros.Hub;

import org.bukkit.entity.Player;

public class HubPlayer {
	private Player player;
	
	public HubPlayer(Player player) {
		this.player = player;
	}
	
	public Player getPlayer() {
		return this.player;
	}
}
