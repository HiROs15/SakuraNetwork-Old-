package dev.hiros.Minigames.PvpParkour.Lobby;

import org.bukkit.ChatColor;
import org.bukkit.entity.Player;

public class LobbyChat {
	public static LobbyChat getInstance() {
		return new LobbyChat();
	}
	
	public void sendLobbyMessage(Player player, String message) {
		String prefix = "";
		if(player.hasPermission("sakuranetwork.vip")) {
			prefix = ChatColor.GREEN+""+ChatColor.BOLD+"[VIP] ";
		}
		if(player.hasPermission("sakuranetwork.overlord")) {
			prefix = ChatColor.GOLD+""+ChatColor.BOLD+"[OVERLORD] ";
		}
		if(player.hasPermission("sakuranetwork.mod")) {
			prefix = ChatColor.BLUE+""+ChatColor.BOLD+"[MOD] ";
		}
		if(player.hasPermission("sakuranetwork.admin")) {
			prefix = ChatColor.RED+""+ChatColor.BOLD+"[ADMIN] ";
		}
		if(player.hasPermission("sakuranetwork.sakuramember")) {
			prefix = ChatColor.LIGHT_PURPLE+""+ChatColor.BOLD+"[SAKURA MEMBER] ";
		}
		
		for(Player p : LobbyManager.getInstance().getLobby("phantom").getPlayers()) {
			p.sendMessage(prefix+ChatColor.YELLOW+""+player.getName()+" "+ChatColor.WHITE+""+message);
		}
	}
}
