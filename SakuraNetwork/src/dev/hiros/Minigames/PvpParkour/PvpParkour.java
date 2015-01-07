package dev.hiros.Minigames.PvpParkour;

import java.util.ArrayList;

import org.bukkit.configuration.file.FileConfiguration;

import dev.hiros.Config.Config;
import dev.hiros.Minigames.PvpParkour.Arenas.Arena;

public class PvpParkour {
	private static PvpParkour inst = new PvpParkour();
	public static PvpParkour getInstance() {
		return inst;
	}
	
	public ArrayList<Arena> arenas = new ArrayList<Arena>();
	
	public void loadArenas() {
		String path = "/minigames/pvpparkour/pvpparkour.dat";
		Config inst = Config.getInstance();
		FileConfiguration config = inst.getConfig(path);
		
		for(String key : config.getConfigurationSection("arenas").getKeys(false)) {
			arenas.add(new Arena(key));
		}
	}
	
}
