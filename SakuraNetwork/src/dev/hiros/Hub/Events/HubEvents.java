package dev.hiros.Hub.Events;

import org.bukkit.Bukkit;
import org.bukkit.World;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.event.entity.CreatureSpawnEvent;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.entity.FoodLevelChangeEvent;
import org.bukkit.event.player.AsyncPlayerChatEvent;
import org.bukkit.event.player.PlayerDropItemEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerPickupItemEvent;

import dev.hiros.SakuraNetwork;
import dev.hiros.Hub.HubChat;
import dev.hiros.Hub.HubManager;

public class HubEvents implements Listener {
	@EventHandler
	public void onPlayerLogin(PlayerJoinEvent event) {
		HubManager.getInstance().joinHub(event.getPlayer());
	}
	
	//Protection
	
	@EventHandler
	public void onPlayerBreakBlock(BlockBreakEvent event) {
		Player player = event.getPlayer();
		if(HubManager.getInstance().getPlayer(player) != null) {
			event.setCancelled(true);
		}
	}
	
	@EventHandler
	public void onPlayerPlaceBlocks(BlockPlaceEvent event) {
		Player player = event.getPlayer();
		if(HubManager.getInstance().getPlayer(player) != null) {
			event.setCancelled(true);
		}
	}
	
	@EventHandler
	public void onPlayerTakeDamage(EntityDamageEvent event) {
		Player player = (Player) event.getEntity();
		if(event.getEntity() instanceof Player) {
			if(HubManager.getInstance().getPlayer(player) != null) {
				event.setCancelled(true);
			}
		}
	}
	
	@EventHandler
	public void onPlayerFoodLevelChange(FoodLevelChangeEvent event) {
		Player player = (Player) event.getEntity();
		if(event.getEntity() instanceof Player) {
			if(HubManager.getInstance().getPlayer(player) != null) {
				event.setCancelled(true);
				player.setFoodLevel(19);
			}
		}
	}
	
	 @EventHandler
	public void onMobSpawnInHub(CreatureSpawnEvent event) {
		World world = event.getLocation().getWorld();
		if(Bukkit.getServer().getWorld(SakuraNetwork.getInstance().getConfig().getString("sakuranetwork.hub.world")).equals(world)) {
			event.setCancelled(true);
		}
	}
	
	@EventHandler
	public void onPlayerChat(AsyncPlayerChatEvent event) {
		Player player = event.getPlayer();
		if(HubManager.getInstance().getPlayer(player) != null) {
			event.setCancelled(true);
			HubChat.getInstance().sendHubMessage(player, event.getMessage());
		}
	}
	
	@EventHandler
	public void onPlayerDropItem(PlayerDropItemEvent event) {
		Player player = event.getPlayer();
		if(HubManager.getInstance().getPlayer(player) != null) {
			event.setCancelled(true);
		}
	}
	
	@EventHandler
	public void onPlayerPickupItem(PlayerPickupItemEvent event) {
		Player player = event.getPlayer();
		if(HubManager.getInstance().getPlayer(player) != null) {
			event.setCancelled(true);
		}
	}
}
