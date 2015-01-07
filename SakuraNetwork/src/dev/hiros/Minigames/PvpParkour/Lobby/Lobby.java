package dev.hiros.Minigames.PvpParkour.Lobby;

import java.util.ArrayList;

import org.bukkit.Bukkit;
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
import dev.hiros.Bungee.Bungee;
import dev.hiros.Config.Config;
import dev.hiros.Hub.HubManager;
import dev.hiros.Minigames.PvpParkour.Arenas.ArenaManager;
import dev.hiros.Utils.LocationConfig;

public class Lobby {
	private String name;
	private Location loc;
	private LobbyState state;
	private ArrayList<Player> lobbyPlayers;
	private String arena;
	private int maxPlayers;
	private int minPlayers;
	private int countdown;
	private boolean countdownStarted;
	
	BukkitTask counter;
	
	public Lobby(String name) {
		this.name = name;
		
		String path = "/minigames/pvpparkour/pvpparkour.dat";
		Config inst = Config.getInstance();
		FileConfiguration config = inst.getConfig(path);
		
		this.loc = LocationConfig.getInstance().getLocation(config, "lobbies."+name+".location");
		this.state = LobbyState.READY;
		this.lobbyPlayers = new ArrayList<Player>();
		this.arena = LobbyManager.getInstance().findOpenArena("phantom");
		this.maxPlayers = 10;
		this.minPlayers = 2;
		this.countdown = 45;
		this.countdownStarted = false;
	}
	
	public int getMaxPlayers() {
		return this.maxPlayers;
	}
	
	public int getCountdown() {
		return this.countdown;
	}
	
	public void setCountdown(int i) {
		this.countdown = i;
	}
	
	public void addPlayer(Player player) {
		lobbyPlayers.add(player);
	}
	
	public void removePlayer(Player player) {
		lobbyPlayers.remove(player);
	}
	
	public String getMap() {
		return this.name;
	}
	
	public Location getLocation() {
		return this.loc;
	}
	
	public LobbyState getState() {
		return this.state;
	}
	
	public void setState(LobbyState state) {
		this.state = state;
	}
	
	public String getArena() {
		return this.arena;
	}
	
	public int getOnlinePlayers() {
		return lobbyPlayers.size();
	}
	
	public ArrayList<Player> getPlayers() {
		return this.lobbyPlayers;
	}
	
	public boolean containsPlayer(Player player) {
		for(Player p : lobbyPlayers) {
			if(p.getName().equalsIgnoreCase(player.getName())) {
				return true;
			}
		}
		return false;
	}
	
	@SuppressWarnings("deprecation")
	public void BungeePlayers(Player player) {
		for(Player p : Bukkit.getOnlinePlayers()) {
			if(this.containsPlayer(p)) {
				Bungee.getInstance().showPlayers(player, p);
			} else {
				Bungee.getInstance().hidePlayers(player, p);
			}
		}
	}
	
	public void joinLobby(Player player) {
		if(this.state == LobbyState.FULL) {
			HubManager.getInstance().joinHub(player);
			player.sendMessage(ChatColor.RED+"That lobby is full.");
			return;
		}
		
		addPlayer(player);
		player.teleport(this.loc);
		player.setGameMode(GameMode.ADVENTURE);
		
		player.sendMessage(ChatColor.GREEN+"PVPPARKOUR> "+ChatColor.GRAY+"You have just entered the lobby.");
		
		updateState();
		
		//Display / Update all the players lobby scoreboard
		updateLobbyScoreboard(player);
		for(Player p : this.lobbyPlayers) {
			updateLobbyScoreboard(p);
		}
		
		//Bungee the players
		BungeePlayers(player);
		
		//Send Join Message
		sendJoinMessage(player);
	}
	
	public void leaveLobby(Player player) {
		if(containsPlayer(player)) {
			this.lobbyPlayers.remove(player);
			
			updateState();
			
			//Update all the players scoreboards
			for(Player p : this.lobbyPlayers) {
				updateLobbyScoreboard(p);
			}
			
			//Send Leave Message
			sendLeaveMessage(player);
		}
	}
	
	//CALLED when the game starts
	public void startGame() {
		for(Player p : this.lobbyPlayers) {
			this.leaveLobby(p);
			ArenaManager.getInstance().getArena(this.arena).joinArena(p);
			p.sendMessage(this.lobbyPlayers.size()+" :");
		}
		
		this.arena = LobbyManager.getInstance().findOpenArena(this.getMap());
		this.state = LobbyState.READY;
	}
	
	public void updateState() {
		if(this.getOnlinePlayers() == 0) {
			if(this.countdownStarted) {
				resetCounter();
			}
			
			this.state = LobbyState.READY;
		}
		
		if(this.getOnlinePlayers() > 0 && this.getOnlinePlayers() < this.minPlayers) {
			this.state = LobbyState.WAITING;
			if(this.countdownStarted) {
				resetCounter();
			}
		}
		
		if(this.getOnlinePlayers() >= this.minPlayers) {
			this.state = LobbyState.COUNTDOWN;
			if(!this.countdownStarted) {
				startCountdown();
				this.countdownStarted = true;
			}
		}
		
		if(getOnlinePlayers() == this.getMaxPlayers()) {
			this.state = LobbyState.FULL;
		}
	}
	
	public void updateLobbyScoreboard(Player player) {
		ScoreboardManager manager = Bukkit.getServer().getScoreboardManager();
		Scoreboard board = manager.getNewScoreboard();
		Objective obj = board.registerNewObjective("test", "dummy");
		obj.setDisplaySlot(DisplaySlot.SIDEBAR);
		obj.setDisplayName("Working..");
		
		if(this.state == LobbyState.WAITING) {
			obj.setDisplayName(ChatColor.AQUA+""+ChatColor.BOLD+"Waiting for players to join.");
		}
		
		if(this.state == LobbyState.COUNTDOWN) {
			obj.setDisplayName(ChatColor.GREEN+""+ChatColor.BOLD+"Starting in "+this.countdown+" seconds.");
		}
		
		createRow(obj, board, ChatColor.WHITE+"Server: "+ChatColor.GRAY+""+this.arena, 0);
		createRow(obj, board, " ", 1);
		createRow(obj, board, ChatColor.WHITE+"Game: "+ChatColor.YELLOW+"PvpParkour", 2);
		createRow(obj, board, "  ", 3);
		createRow(obj, board, ChatColor.WHITE+"Players: "+ChatColor.GRAY+""+this.getOnlinePlayers()+"/"+this.getMaxPlayers(), 4);
		createRow(obj, board, "   ", 5);
		
		if(this.state == LobbyState.WAITING) {
			createRow(obj, board, ChatColor.WHITE+"Players needed: "+ChatColor.YELLOW+""+(this.minPlayers-this.getOnlinePlayers()), 6);
		}
		
		else if(this.state == LobbyState.COUNTDOWN) {
			createRow(obj, board, ChatColor.GREEN+"The match is starting soon!",6);
		}
		
		createRow(obj, board, "    ", 7);
		
		createRow(obj, board, ChatColor.WHITE+"Map: "+ChatColor.YELLOW+""+this.getMap(), 8);
		createRow(obj, board, "     ", 9);
		
		player.setScoreboard(board);
	}
	
	public void createRow(Objective obj, Scoreboard board, String name, int score) {
		Score s = obj.getScore(name);
		s.setScore(score);
	}
	
	public void startCountdown() {
		Lobby lobby = LobbyManager.getInstance().getLobby(this.getMap());
		counter = new Countdown(lobby).runTaskTimer(SakuraNetwork.getInstance(), 0L, 20L);
		this.countdownStarted = true;
	}
	
	public void resetCounter() {
		counter.cancel();
		this.setCountdown(45);
		this.countdownStarted = false;
	}
	
	public void sendJoinMessage(Player player) {
		for(Player p : this.lobbyPlayers) {
			p.sendMessage(ChatColor.GREEN+""+player.getName()+" "+ChatColor.GRAY+"has joined the lobby.");
		}
	}
	
	public void sendLeaveMessage(Player player) {
		for(Player p : this.lobbyPlayers) {
			p.sendMessage(ChatColor.RED+""+player.getName()+" "+ChatColor.GRAY+"has left the lobby.");
		}
	}
}



class Countdown extends BukkitRunnable {
	private Lobby lobby;
	public Countdown(Lobby lobby) {
		this.lobby = lobby;
	}
	
	public Lobby getLobby() {
		return this.lobby;
	}
	
	@Override
	public void run() {
		if(this.getLobby().getCountdown() > 0) {
			this.getLobby().setCountdown(this.getLobby().getCountdown()-1);
			
			for(Player p : this.getLobby().getPlayers()) {
				this.getLobby().updateLobbyScoreboard(p);
			}
		}
		else if(this.getLobby().getCountdown() == 0) {
			this.getLobby().setCountdown(45);
			this.getLobby().startGame();
			this.cancel();
		}
	}
}