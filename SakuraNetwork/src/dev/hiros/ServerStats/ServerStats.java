package dev.hiros.ServerStats;

import org.bukkit.ChatColor;

public class ServerStats {
	private static ServerStats inst = new ServerStats();
	public static ServerStats getInstance() {
		return inst;
	}
	
	//Hard coded server stats
	public String motd = ChatColor.LIGHT_PURPLE+"SakuraNetwork"+ChatColor.GOLD+" | "+ChatColor.AQUA+"Server under development \n"+ChatColor.BLUE+""+ChatColor.BOLD+"★"+ChatColor.YELLOW+""+ChatColor.BOLD+"Minigames, Survival Games, Custom Maps"+ChatColor.BLUE+""+ChatColor.BOLD+"★";
	public int slots = 2000;
}
