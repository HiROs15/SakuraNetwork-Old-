package dev.hiros.Minigames.PvpParkour.Events;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.entity.FoodLevelChangeEvent;
import org.bukkit.event.player.PlayerMoveEvent;

import dev.hiros.Minigames.PvpParkour.Arenas.ArenaManager;

public class ArenaEvents implements Listener {
	@EventHandler
	public void onBlockBreak(BlockBreakEvent event) {
		Player player = event.getPlayer();
		if(ArenaManager.getInstance().isPlayerInArena(player)) {
			event.setCancelled(true);
		}
	}
	
	@EventHandler
	public void onBlockPlace(BlockPlaceEvent event) {
		Player player = event.getPlayer();
		if(ArenaManager.getInstance().isPlayerInArena(player)) {
			event.setCancelled(true);
		}
	}
	
	@EventHandler
	public void onPlayerMove(PlayerMoveEvent event) {
		Player player = event.getPlayer();
		if(ArenaManager.getInstance().isPlayerInArena(player)) {
			if(ArenaManager.getInstance().getArena(player).pregamecounteractive) {
				player.teleport(ArenaManager.getInstance().getArena(player).getPlayer(player).getSpawn());
			}
			
			if(player.getLocation().getY() < 0) {
				ArenaManager.getInstance().getArena(player).playerDie(player);
			}
		}
	}
	
	@EventHandler
	public void onPlayerDamager(EntityDamageEvent event) {
		if(event.getEntity() instanceof Player) {
			Player player = (Player) event.getEntity();
			if(ArenaManager.getInstance().isPlayerInArena(player)) {
				event.setCancelled(true);
			}
		}
	}
	
	@SuppressWarnings("deprecation")
	@EventHandler
	public void onPlayerStarve(FoodLevelChangeEvent event) {
		if(event.getEntity() instanceof Player) {
			Player player = (Player) event.getEntity();
			if(ArenaManager.getInstance().isPlayerInArena(player)) {
				event.setFoodLevel(20);
				player.setHealth(20);
			}
		}
	}
}
