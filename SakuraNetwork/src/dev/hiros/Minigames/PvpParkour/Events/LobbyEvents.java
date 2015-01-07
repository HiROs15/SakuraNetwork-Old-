package dev.hiros.Minigames.PvpParkour.Events;

import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.entity.FoodLevelChangeEvent;
import org.bukkit.event.player.AsyncPlayerChatEvent;
import org.bukkit.event.player.PlayerCommandPreprocessEvent;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.event.player.PlayerQuitEvent;

import dev.hiros.Hub.HubManager;
import dev.hiros.Minigames.PvpParkour.Lobby.LobbyChat;
import dev.hiros.Minigames.PvpParkour.Lobby.LobbyManager;

public class LobbyEvents implements Listener {
	@EventHandler
	public void onPlayerDamage(EntityDamageEvent event) {
		if(event.getEntity() instanceof Player) {
			Player player = (Player) event.getEntity();
			if(LobbyManager.getInstance().isPlayerInLobby(player)) {
				event.setCancelled(true);
			}
		}
	}
	
	@EventHandler
	public void onPlayerBreakBlock(BlockBreakEvent event) {
		if(LobbyManager.getInstance().isPlayerInLobby(event.getPlayer())) {
			event.setCancelled(true);
		}
	}
	
	@EventHandler
	public void onPlayerPlaceBlock(BlockPlaceEvent event) {
		if(LobbyManager.getInstance().isPlayerInLobby(event.getPlayer())) {
			event.setCancelled(true);
		}
	}
	
	@SuppressWarnings("deprecation")
	@EventHandler
	public void onPlayerStarve(FoodLevelChangeEvent event) {
		if(LobbyManager.getInstance().isPlayerInLobby((Player) event.getEntity())) {
			event.setFoodLevel(19);
			
			Player player = (Player) event.getEntity();
			player.setHealth(20);
		}
	}
	
	@EventHandler
	public void onPlayerSayCommand(PlayerCommandPreprocessEvent event) {
		Player player = event.getPlayer();
		if(LobbyManager.getInstance().isPlayerInLobby(player)) {
			if(event.getMessage().equalsIgnoreCase("/hub")) {
				event.setCancelled(true);
				player.sendMessage(ChatColor.GREEN+"To return to the hub please walk into the portal frame.");
			}
		}
	}
	
	@EventHandler
	public void onPlayerChat(AsyncPlayerChatEvent event) {
		if(LobbyManager.getInstance().isPlayerInLobby(event.getPlayer())) {
			LobbyChat.getInstance().sendLobbyMessage(event.getPlayer(), event.getMessage());
			event.setCancelled(true);
		}
	}
	
	@EventHandler
	public void onPlayerMove(PlayerMoveEvent event) {
		Player player = event.getPlayer();
		if(LobbyManager.getInstance().isPlayerInLobby(player)) {
			Location loc = player.getLocation().add(0, -1, 0);
			if(loc.getBlock().getType() == Material.SEA_LANTERN) {
				LobbyManager.getInstance().getLobby(player).leaveLobby(player);
				HubManager.getInstance().joinHub(player);
			}
		}
	}
	
	@EventHandler
	public void onPlayerLeave(PlayerQuitEvent event) {
		Player player = event.getPlayer();
		if(LobbyManager.getInstance().isPlayerInLobby(player)) {
			LobbyManager.getInstance().getLobby(player).leaveLobby(player);
		}
	}
}
