package dev.hiros.Hub.Parkour;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.configuration.file.FileConfiguration;

import dev.hiros.Config.Config;

public class ParkourBlock {
	private Location loc;
	private String name;
	
	public ParkourBlock(String name) {
		this.name = name;
		String path = "/hub/parkour.dat";
		Config inst = Config.getInstance();
		FileConfiguration config = inst.getConfig(path);
		
		Location loc = new Location(
				Bukkit.getServer().getWorld(config.getString("blocks."+name+".world")),
				config.getDouble("blocks."+name+".x"),
				config.getDouble("blocks."+name+".y"),
				config.getDouble("blocks."+name+".z")
		);
		
		this.loc = loc;
	}
	
	public String getName() {
		return this.name;
	}
	
	public Location getLocation() {
		return this.loc;
	}
}
