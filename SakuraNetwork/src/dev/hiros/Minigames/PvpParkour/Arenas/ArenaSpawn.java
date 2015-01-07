package dev.hiros.Minigames.PvpParkour.Arenas;

import org.bukkit.Location;

import dev.hiros.Config.Config;
import dev.hiros.Utils.LocationConfig;

public class ArenaSpawn {
	private String name;
	private Location loc;
	private String arena;
	
	public ArenaSpawn(String spawn, String arena) {
		this.name = spawn;
		this.arena = arena;
		
		String file = "/minigames/pvpparkour/pvpparkour.dat";
		Location l = LocationConfig.getInstance().getLocation(Config.getInstance().getConfig(file), "arenas."+arena+".spawns."+spawn+".location");
		
		this.loc = l;
	}
	
	public String getName() {
		return this.name;
	}
	
	public String getArena() {
		return this.arena;
	}
	
	public Location getLocation() {
		return this.loc;
	}
}
