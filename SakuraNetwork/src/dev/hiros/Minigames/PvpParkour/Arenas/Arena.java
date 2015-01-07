package dev.hiros.Minigames.PvpParkour.Arenas;

import java.util.ArrayList;

import org.bukkit.ChatColor;
import org.bukkit.GameMode;
import org.bukkit.Location;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.scheduler.BukkitTask;
import org.bukkit.scoreboard.DisplaySlot;
import org.bukkit.scoreboard.Objective;
import org.bukkit.scoreboard.Score;
import org.bukkit.scoreboard.Scoreboard;
import org.bukkit.scoreboard.ScoreboardManager;

import dev.hiros.SakuraNetwork;
import dev.hiros.Config.Config;
import dev.hiros.Utils.LocationConfig;

public class Arena {
	private String name;
	private Location loc;
	private ArrayList<ArenaPlayer> players;
	private ArenaState state;
	private int maxplayers;
	private String map;
	
	public int pregamecounter = 15;
	public boolean pregamecounteractive = false;
	
	public int gracecounter = 15;
	public boolean gracecounteractive = false;
	
	private BukkitTask pregamecountertask;
	
	private BukkitTask gracecountertask;
	
	private ArrayList<ArenaSpawn> spawns = new ArrayList<ArenaSpawn>();
	
	public Player winner = null;
	
	public Arena(String name) {
		this.name = name;
		
		String path = "/minigames/pvpparkour/pvpparkour.dat";
		Config inst = Config.getInstance();
		FileConfiguration config = inst.getConfig(path);
		
		this.loc = LocationConfig.getInstance().getLocation(config, "arenas."+name+".location");
		this.players = new ArrayList<ArenaPlayer>();
		this.state = ArenaState.READY;
		this.maxplayers = 10;
		this.map = config.getString("arenas."+name+".map");
		
		this.loadSpawns();
	}
	
	public String getName() {
		return this.name;
	}
	
	public Location getLocation() {
		return this.loc;
	}
	
	public ArrayList<ArenaPlayer> getPlayers() {
		return this.players;
	}
	
	public ArenaState getState() {
		return this.state;
	}
	
	public int getMaxPlayers() {
		return this.maxplayers;
	}
	
	public String getMap() {
		return this.map;
	}
	
	public void setState(ArenaState state) {
		this.state = state;
	}
	
	public void loadSpawns() {
		String file = "/minigames/pvpparkour/pvpparkour.dat";
		Config inst = Config.getInstance();
		FileConfiguration config = inst.getConfig(file);
		for(String key : config.getConfigurationSection("arenas."+this.name+".spawns").getKeys(false)) {
			spawns.add(new ArenaSpawn(key, this.name));
		}
	}
	
	public boolean containsPlayer(Player player) {
		for(ArenaPlayer p : this.players) {
			if(p.getPlayer().getName().equals(player.getName())) {
				return true;
			}
		}
		return false;
	}
	
	public ArenaPlayer getPlayer(Player player) {
		for(ArenaPlayer p : this.players) {
			if(p.getPlayer().getName().equals(player.getName())) {
				return p;
			}
		}
		return null;
	}
	
	public void joinArena(Player player) {
		ArenaPlayer ap = new ArenaPlayer(player);
		this.players.add(ap);
		
		//Teleport player to a spawn
		player.teleport(this.getOpenSpawn().getLocation());
		
		//Set the spawn location
		ap.setSpawn(this.getOpenSpawn().getLocation());
		
		updateArenaScoreboard(player);
		for(ArenaPlayer p : this.players) {
			updateArenaScoreboard(p.getPlayer());
		}
		
		//Update the arena state
		this.updateState();
	}
	
	public void startGame() {
		this.state = ArenaState.INGAME;
		this.startGraceCounter();
		this.gracecounteractive = true;
	}
	
	public void updateArenaScoreboard(Player player) {
		ScoreboardManager manager = SakuraNetwork.getInstance().getServer().getScoreboardManager();
		Scoreboard board = manager.getNewScoreboard();
		Objective obj = board.registerNewObjective("test", "dummy");
		obj.setDisplaySlot(DisplaySlot.SIDEBAR);
		
		if(this.state == ArenaState.PREGAME) {
			obj.setDisplayName(ChatColor.AQUA+""+ChatColor.BOLD+"Starting in "+this.pregamecounter+" Seconds");
		}
		
		if(this.state == ArenaState.INGAME) {
			if(gracecounteractive) {
				obj.setDisplayName(ChatColor.DARK_PURPLE+""+ChatColor.BOLD+"Grace Period: "+this.gracecounter+" Seconds");
			}
			else if(!gracecounteractive) {
				obj.setDisplayName(ChatColor.GREEN+""+ChatColor.BOLD+"PvpParkour");
			}
		}
		
		for(ArenaPlayer p : this.players) {
			if(p.getLives() > 0) {
				createRow(obj, ChatColor.YELLOW+""+p.getPlayer().getName()+": ", p.getLives());
			}
		}
		
		//Display to player
		player.setScoreboard(board);
		
		//Gamemode to adventure
		player.setGameMode(GameMode.ADVENTURE);
	}
	
	public void createRow(Objective obj, String name, int score) {
		Score s = obj.getScore(name);
		s.setScore(score);
	}
	
	public ArenaSpawn getOpenSpawn() {
		return this.spawns.get(this.getOnlinePlayers()-1);
	}
	
	public int getOnlinePlayers() {
		return this.players.size();
	}
	
	public void updateState() {
		if(this.getOnlinePlayers() > 0) {
			this.state = ArenaState.PREGAME;
			startPregameCountdown();
		}
		
		if(this.getOnlinePlayers() == 0) {
			this.state = ArenaState.READY;
			this.resetPregameCounter();
		}
	}
	
	public void startPregameCountdown() {
		Arena arena = ArenaManager.getInstance().getArena(this.getName());
		pregamecountertask = new PregameCounter(arena).runTaskTimer(SakuraNetwork.getInstance(), 0L, 20L);
		this.pregamecounteractive = true;
	}
	
	public void resetPregameCounter() {
		pregamecountertask.cancel();
	}
	
	public void startGraceCounter() {
		Arena arena = ArenaManager.getInstance().getArena(this.getName());
		gracecountertask = new GraceCounter(arena).runTaskTimer(SakuraNetwork.getInstance(), 0L, 20L);
		this.gracecounteractive = true;
	}
	
	public void resetGraceCounter() {
		gracecountertask.cancel();
		this.gracecounteractive = false;
	}
	
	public void playerDie(Player player) {
		ArenaPlayer ap = this.getPlayer(player);
		
		if(ap.getLives() > 1) {
			ap.setLives(ap.getLives()-1);
			
			for(ArenaPlayer p : this.players) {
				p.getPlayer().sendMessage(ChatColor.YELLOW+""+p.getPlayer().getName()+" "+ChatColor.GRAY+"has just died.");
				
				this.updateArenaScoreboard(p.getPlayer());
			}
			
			//Teleport play back to their spawn
			player.teleport(ap.getSpawn());
		}
		
		if(ap.getLives() == 1) {
			//Player is out of the game.
			ap.setSpectator(true);
			
			for(ArenaPlayer p : this.players) {
				p.getPlayer().sendMessage(ChatColor.YELLOW+""+ChatColor.BOLD+""+p.getPlayer().getName()+" "+ChatColor.RED+""+ChatColor.BOLD+"is now out of lives.");
				
				this.updateArenaScoreboard(p.getPlayer());
			}
			
			player.setGameMode(GameMode.SPECTATOR);
			player.teleport(ap.getSpawn());
			
			//Hide the players that are out
			for(ArenaPlayer p : this.players) {
				if(!p.getSpectator()) {
					player.hidePlayer(p.getPlayer());
					p.getPlayer().showPlayer(player);
				}
				
				if(p.getSpectator()) {
					player.showPlayer(p.getPlayer());
					p.getPlayer().showPlayer(player);
				}
			}
			
			//Check if some one wins
			this.checkForWinner();
		}
	}
	
	public void checkForWinner() {
		ArrayList<Player> stillActive = new ArrayList<Player>();
		for(ArenaPlayer p : this.players) {
			if(!p.getSpectator()) {
				stillActive.add(p.getPlayer());
			}
		}
		
		if(stillActive.size() == 1) {
			this.state = ArenaState.POSTGAME;
			this.winner = stillActive.get(0);
			
			//Post game stuff!
		}
	}
}

class PregameCounter extends BukkitRunnable {
	private Arena arena;
	public PregameCounter(Arena arena) {
		this.arena = arena;
	}
	
	public Arena getArena() {
		return this.arena;
	}
	
	@Override
	public void run() {
		if(this.getArena().pregamecounter > 0) {
			this.getArena().pregamecounter -= 1;
			for(ArenaPlayer p : this.getArena().getPlayers()) {
				this.getArena().updateArenaScoreboard(p.getPlayer());
			}
		}
		
		if(this.getArena().pregamecounter == 0) {
			this.getArena().pregamecounter = 15;
			this.cancel();
			this.getArena().setState(ArenaState.INGAME);
			this.getArena().startGame();
			this.getArena().pregamecounteractive = false;
		}
	}
}

class GraceCounter extends BukkitRunnable {
	private Arena arena;
	public GraceCounter(Arena arena) {
		this.arena = arena;
	}
	
	public Arena getArena() {
		return this.arena;
	}
	
	@Override
	public void run() {
		if(this.getArena().gracecounter > 0) {
			this.getArena().gracecounter -= 1;
			this.getArena().gracecounteractive = true;
			
			for(ArenaPlayer p : this.getArena().getPlayers()) {
				this.getArena().updateArenaScoreboard(p.getPlayer());
			}
		}
		
		if(this.getArena().gracecounter == 0) {
			this.getArena().gracecounter = 15;
			this.getArena().gracecounteractive = false;
			this.cancel();
			
			//Update all players scoreboard
			for(ArenaPlayer p : this.getArena().getPlayers()) {
				this.getArena().updateArenaScoreboard(p.getPlayer());
			}
		}
	}
}
