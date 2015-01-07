package dev.hiros.Minigames.PvpParkour.Arenas;

import org.bukkit.Location;
import org.bukkit.entity.Player;

public class ArenaPlayer {
	private Player player;
	private int lives;
	private boolean spectator;
	private Location spawn;
	private double velocity;
	
	public ArenaPlayer(Player player) {
		this.player = player;
		this.lives = 3;
		this.spectator = false;
		this.spawn = null;
		this.velocity = 0.01;
	}
	
	public Player getPlayer() {
		return this.player;
	}
	
	public void setSpectator(boolean b) {
		this.spectator = b;
	}
	
	public double getVelocity() {
		return this.velocity;
	}
	
	public void setVelocity(double d) {
		this.velocity = d;
	}
	
	public void setLives(int i) {
		this.lives = i;
	}
	
	public Location getSpawn() {
		return this.spawn;
	}
	
	public void setSpawn(Location loc) {
		this.spawn = loc;
	}
	
	public int getLives() {
		return this.lives;
	}
	
	public boolean getSpectator() {
		return this.spectator;
	}
}
