package dev.hiros.Hub;

import org.bukkit.ChatColor;
import org.bukkit.entity.Player;

public class HubChat {
	private static HubChat inst = new HubChat();
	public static HubChat getInstance() {
		return inst;
	}
	
	public void sendHubMessage(Player player, String msg) {
		String prefix = "";
		if(player.hasPermission("sakuranetwork.member")) {
			prefix = "";
		}
		if(player.hasPermission("sakuranetwork.vip")) {
			prefix = ChatColor.AQUA+""+ChatColor.BOLD+"[VIP] ";
		}
		if(player.hasPermission("sakuranetwork.mod")) {
			prefix = ChatColor.DARK_BLUE+""+ChatColor.BOLD+"[MOD] ";
		}
		if(player.hasPermission("sakuranetwork.admin")) {
			prefix = ChatColor.RED+""+ChatColor.BOLD+"[ADMIN] ";
		}
		if(player.hasPermission("sakuranetwork.sakuramember")) {
			prefix = ChatColor.LIGHT_PURPLE+""+ChatColor.BOLD+"[SAKURA MEMBER] ";
		}
		
		//Start sending messages.
		for(HubPlayer hubplayer : HubManager.getInstance().hubPlayers) {
			hubplayer.getPlayer().sendMessage(prefix+""+ChatColor.YELLOW+""+player.getPlayer().getName()+" "+ChatColor.WHITE+""+msg);
		}
	}
}
