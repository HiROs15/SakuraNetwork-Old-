package dev.hiros.Hub;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.scoreboard.DisplaySlot;
import org.bukkit.scoreboard.Objective;
import org.bukkit.scoreboard.Score;
import org.bukkit.scoreboard.Scoreboard;
import org.bukkit.scoreboard.ScoreboardManager;

import dev.hiros.Economy.EconomyManager;

public class HubScoreboard {
	private static HubScoreboard inst = new HubScoreboard();
	public static HubScoreboard getInstance() {
		return inst;
	}
	
	public void showHubScoreboard(Player player) {
		ScoreboardManager manager = Bukkit.getScoreboardManager();
		Scoreboard board = manager.getNewScoreboard();
		
		Objective obj = board.registerNewObjective("test", "dummy");
		obj.setDisplayName(ChatColor.LIGHT_PURPLE+""+ChatColor.BOLD+"SakuraNetwork Stats");
		obj.setDisplaySlot(DisplaySlot.SIDEBAR);
		
		//Setup the scores.
		addScore(obj, ChatColor.GOLD+"Coins", EconomyManager.getInstance().getCoins(player));
		addScore(obj, ChatColor.GREEN+"Credits", EconomyManager.getInstance().getCredits(player));
		
		player.setScoreboard(board);
	}
	
	public void removeHubScoreboard(Player player) {
		ScoreboardManager manager = Bukkit.getScoreboardManager();
		player.setScoreboard(manager.getNewScoreboard());
	}
	
	//Native Util
	public void addScore(Objective obj, String name, int value) {
		Score s = obj.getScore(name);
		s.setScore(value);
	}
}
