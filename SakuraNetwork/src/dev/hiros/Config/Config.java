package dev.hiros.Config;

import java.io.File;

import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;

import dev.hiros.SakuraNetwork;

public class Config {
	private static Config inst = new Config();
	public static Config getInstance() {
		return inst;
	}
	
	private File file = null;
	private FileConfiguration config = null;
	
	public void reloadConfig(String path) {
		if(file == null) {
			file = new File(SakuraNetwork.getInstance().getDataFolder()+path);
		}
		config = YamlConfiguration.loadConfiguration(file);
	}
	
	public FileConfiguration getConfig(String path) {
		if(config == null) {
			reloadConfig(path);
		}
		
		return config;
	}
	
	public void saveConfig(String path) {
		if(file == null || config == null) {
			reloadConfig(path);
		}
		
		try {
			config.save(file);
		}catch(Exception e) {}
	}
}
