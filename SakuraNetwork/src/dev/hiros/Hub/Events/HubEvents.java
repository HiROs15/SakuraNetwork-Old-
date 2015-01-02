package dev.hiros.Hub.Events;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.entity.FoodLevelChangeEvent;
import org.bukkit.event.player.AsyncPlayerChatEvent;
import org.bukkit.event.player.PlayerDropItemEvent;
import org.bukkit.event.player.PlayerInteractEntityEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.event.player.PlayerPickupItemEvent;
import org.bukkit.event.player.PlayerQuitEvent;

import dev.hiros.Economy.EconomyManager;
import dev.hiros.Hub.HubChat;
import dev.hiros.Hub.HubManager;
import dev.hiros.Hub.HubBank.HubBank;
import dev.hiros.Hub.HubStuff.HubStuff;
import dev.hiros.Hub.QuickWarp.QuickWarp;

public class HubEvents implements Listener {
	@EventHandler
	public void onPlayerLogin(PlayerJoinEvent event) {
		HubManager.getInstance().joinHub(event.getPlayer());
		event.setJoinMessage("");
	}
	
	@EventHandler
	public void onPlayerLogout(PlayerQuitEvent event) {
		event.setQuitMessage("");
		Player player = event.getPlayer();
		if(HubManager.getInstance().getPlayer(player) != null) {
			HubManager.getInstance().leaveHub(player);
		}
	}
	
	//Protection
	
	@EventHandler
	public void onPlayerBreakBlock(BlockBreakEvent event) {
		Player player = event.getPlayer();
		if(HubManager.getInstance().getPlayer(player) != null) {
			if(player.hasPermission("sakuranetwork.sakuramember") || player.hasPermission("sakuranetwork.admin")) {
				return;
			}
			
			event.setCancelled(true);
		}
	}
	
	@EventHandler
	public void onPlayerPlaceBlocks(BlockPlaceEvent event) {
		Player player = event.getPlayer();
		if(HubManager.getInstance().getPlayer(player) != null) {
			if(player.hasPermission("sakuranetwork.sakuramember") || player.hasPermission("sakuranetwork.admin")) {
				return;
			}
			
			event.setCancelled(true);
		}
	}
	
	@EventHandler
	public void onPlayerTakeDamage(EntityDamageEvent event) {
		if(event.getEntity() instanceof Player) {
			Player player = (Player) event.getEntity();
			if(HubManager.getInstance().getPlayer(player) != null) {
				event.setCancelled(true);
				return;
			}
		}
	}
	
	@EventHandler
	public void onPlayerDamageMob(EntityDamageByEntityEvent event) {
		if(event.getDamager() instanceof Player) {
			Player player = (Player) event.getDamager();
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
	
	 /*@EventHandler
	public void onMobSpawnInHub(CreatureSpawnEvent event) {
		World world = event.getLocation().getWorld();
		if(Bukkit.getServer().getWorld(SakuraNetwork.getInstance().getConfig().getString("sakuranetwork.hub.world")).equals(world)) {
			event.setCancelled(true);
		}
	}*/
	
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
			if(event.getItemDrop().getItemStack().getType() == Material.COAL) {
				if(EconomyManager.getInstance().getCoins(player) <= 0) {
					event.setCancelled(true);
					player.sendMessage(ChatColor.GREEN+"SYSTEM> "+ChatColor.GRAY+"You do not have any coins.");
					
					//Play error sound only to player
					Bukkit.getServer().dispatchCommand(Bukkit.getConsoleSender(), "playsound hub.error "+player.getName()+" "+player.getLocation().getX()+" "+player.getLocation().getY()+" "+player.getLocation().getZ()+" 100 1 1");
					return;
				}
				HubManager.getInstance().dropSakuraCoin(player);
				event.setCancelled(true);
				return;
			}
			event.setCancelled(true);
		}
	}
	
	@EventHandler
	public void onPlayerPickupItem(PlayerPickupItemEvent event) {
		Player player = event.getPlayer();
		if(HubManager.getInstance().getPlayer(player) != null) {
			if(event.getItem().getItemStack().getType() == Material.COAL) {
				HubManager.getInstance().pickupSakuraCoin(player, event.getItem(), event.getItem().getItemStack().getAmount());
				return;
			}
			event.setCancelled(true);
			return;
		}
	}
	
	@EventHandler
	public void onPlayerRightClickOnMob(PlayerInteractEntityEvent event) {
		Player player = event.getPlayer();
		if(HubManager.getInstance().getPlayer(player) != null) {
			if(event.getRightClicked().getType() == EntityType.VILLAGER && event.getRightClicked().getCustomName().equalsIgnoreCase(ChatColor.GOLD+""+ChatColor.BOLD+"Sakura Banker")) {
				HubBank.getInstance().openBankerInv(player);
				event.setCancelled(true);
				return;
			}
			return;
		}
		return;
	}
	
	@EventHandler(priority=EventPriority.HIGH)
	public void onPlayerRightClickWithItems(PlayerInteractEvent event) {
		Player player = event.getPlayer();
		if(HubManager.getInstance().getPlayer(player) != null) {
			if(player.getItemInHand().getType() == Material.SLIME_BALL) {
				QuickWarp.getInstance().openInventory(player);
			}
			
			//Hub stuff item
			if(player.getItemInHand().getType() == Material.NETHER_STAR) {
				HubStuff.getInstance().openInventory(player);
			}
		}
	}
	
	@EventHandler
	public void onPlayerMoveOverPad(PlayerMoveEvent event) {
		Player player = event.getPlayer();
		if(HubManager.getInstance().getPlayer(player) != null) {
			//Check if player has walked over a pad. *! MIGHT BE CPU INTENSIVE - Try to optimize
			QuickWarp.getInstance().checkPlayerOnPad(player);
		}
	}
}
