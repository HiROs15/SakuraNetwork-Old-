package dev.hiros.Minigames.PvpParkour.Lobby;

import java.util.ArrayList;

import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;

import dev.hiros.Config.Config;
import dev.hiros.Minigames.PvpParkour.Arenas.Arena;
import dev.hiros.Minigames.PvpParkour.Arenas.ArenaManager;
import dev.hiros.Minigames.PvpParkour.Arenas.ArenaState;

public class LobbyManager {
	private static LobbyManager inst = new LobbyManager();
	public static LobbyManager getInstance() {
		return inst;
	}
	
	public ArrayList<Lobby> lobbies = new ArrayList<Lobby>();
	
	public void loadLobbies() {
		String path = "/minigames/pvpparkour/pvpparkour.dat";
		Config inst = Config.getInstance();
		FileConfiguration config = inst.getConfig(path);
		
		for(String key : config.getConfigurationSection("lobbies").getKeys(false)) {
			lobbies.add(new Lobby(key));
		}
	}
	
	public String findOpenArena(String map) {
		for(Arena a : ArenaManager.getInstance().getArenas()) {
			if(a.getMap().equalsIgnoreCase(map) && a.getState().equals(ArenaState.READY)) {
				return a.getName();
			}
		}
		return "none";
	}
	
	public Lobby getLobby(String name) {
		for(Lobby l : lobbies) {
			if(l.getMap().equals(name)) {
				return l;
			}
		}
		return null;
	}
	
	public Lobby getLobby(Player player) {
		for(Lobby l : lobbies) {
			if(l.containsPlayer(player)) {
				return l;
			}
		}
		return null;
	}
	
	public boolean isPlayerInLobby(Player player) {
		for(Lobby l : lobbies) {
			if(l.containsPlayer(player)) {
				return true;
			}
		}
		return false;
	}
}