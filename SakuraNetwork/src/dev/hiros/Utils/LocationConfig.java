package dev.hiros.Utils;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.configuration.file.FileConfiguration;

import dev.hiros.Config.Config;

public class LocationConfig {
	public static LocationConfig getInstance() {
		return new LocationConfig();
	}
	
	public Location getLocation(FileConfiguration config, String path) {
		Location loc = new Location(
				Bukkit.getServer().getWorld(config.getString(path+".world")),
				config.getDouble(path+".x"),
				config.getDouble(path+".y"),
				config.getDouble(path+".z"),
				(float) config.getDouble(path+".yaw"),
				(float) config.getDouble(path+".pitch")
			);
		return loc;
	}
	
	public void setLocation(Config instance, Location loc, String file, String path) {
		FileConfiguration config = instance.getConfig(file);
		
		config.set(path+".world", loc.getWorld().getName());
		config.set(path+".x", loc.getX());
		config.set(path+".y", loc.getY());
		config.set(path+".z", loc.getZ());
		config.set(path+".yaw", loc.getYaw());
		config.set(path+".pitch", loc.getPitch());
		instance.saveConfig(file);
		instance.reloadConfig(file);
	}
}
