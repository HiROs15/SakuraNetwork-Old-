package dev.hiros.ServerStats.Events;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.server.ServerListPingEvent;

import dev.hiros.ServerStats.ServerStats;

public class ServerStatsEvents implements Listener {
	@EventHandler
	public void onServerPing(ServerListPingEvent event) {
		event.setMotd(ServerStats.getInstance().motd);
		event.setMaxPlayers(ServerStats.getInstance().slots);
	}
}
