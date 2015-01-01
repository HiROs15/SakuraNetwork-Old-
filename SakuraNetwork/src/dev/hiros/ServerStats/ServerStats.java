package dev.hiros.ServerStats;

import org.bukkit.ChatColor;

public class ServerStats {
	private static ServerStats inst = new ServerStats();
	public static ServerStats getInstance() {
		return inst;
	}
	
	//Hard coded server stats
	public String motd = ChatColor.LIGHT_PURPLE+""+ChatColor.BOLD+"SakuraNetwork"+ChatColor.RESET+" "+ChatColor.AQUA+"Minigames, Economy, No Lag";
	public int slots = 2000;
}
