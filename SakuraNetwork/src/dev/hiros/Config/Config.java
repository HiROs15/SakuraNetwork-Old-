package dev.hiros.Config;

import java.io.File;

import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;

import dev.hiros.SakuraNetwork;

public class Config {
	private static String txtfile;
	private static Config inst;
	
	private Config(String file) {
		Config.txtfile = file;
	}
	
	public static Config getInstance(String f) {
		Config.inst = new Config(f);
		return Config.inst;
	}
	
	private FileConfiguration config = null;
	private File file = null;
	private String filename = Config.txtfile+".yml";
	
	public void reloadConfig() {
		if(file == null) {
			file = new File(SakuraNetwork.getInstance().getDataFolder()+""+filename);
		}
			config = YamlConfiguration.loadConfiguration(file);
	}
	
	public FileConfiguration getConfig() {
		if(config == null) {
			reloadConfig();
		}
		return config;
	}
	
	public void saveConfig() {
		if(file == null) {
			reloadConfig();
		}
		try {
			config.save(file);
		} catch(Exception e) {}
	}
}
