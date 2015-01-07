package dev.hiros.Minigames.PvpParkour.Arenas;

import java.util.ArrayList;

import org.bukkit.Bukkit;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;

import dev.hiros.Config.Config;

public class ArenaManager {
	private static ArenaManager inst = new ArenaManager();
	
	public static ArenaManager getInstance() {
		return inst;
	}
	
	private ArrayList<Arena> arenas = new ArrayList<Arena>();
	
	public void loadArenas() {
		String file = "/minigames/pvpparkour/pvpparkour.dat";
		Config inst = Config.getInstance();
		FileConfiguration config = inst.getConfig(file);
		
		for(String key : config.getConfigurationSection("arenas").getKeys(false)) {
			arenas.add(new Arena(key));
		}
		Bukkit.getLogger().warning("Arena size: "+arenas.size());
	}
	
	public ArrayList<Arena> getArenas() {
		return this.arenas;
	}
	
	public boolean isPlayerInArena(Player player) {
		for(Arena a : arenas) {
			if(a.containsPlayer(player)) {
				return true;
			}
		}
		return false;
	}
	
	public Arena getArena(String name) {
		for(Arena a : this.arenas) {
			if(a.getName().equals(name)) {
				return a;
			}
		}
		return null;
	}
	
	public Arena getArena(Player player) {
		for(Arena a : this.arenas) {
			if(a.containsPlayer(player)) {
				return a;
			}
		}
		return null;
	}
}
