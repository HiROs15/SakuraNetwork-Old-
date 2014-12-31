package dev.hiros.Hub.HubBank;

import net.minecraft.server.v1_8_R1.EntityVillager;

import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.craftbukkit.v1_8_R1.CraftWorld;
import org.bukkit.entity.Player;
import org.bukkit.event.entity.CreatureSpawnEvent.SpawnReason;

import dev.hiros.Hub.HubBank.NMS.EntityBanker;
public class HubBank {
	private static HubBank inst = new HubBank();
	public static HubBank getInstance() {
		return inst;
	}
	
	public void spawnBanker(Player player, Location loc, int bankerid) {
		CraftWorld handle = (CraftWorld) loc.getWorld();
		EntityVillager banker = new EntityBanker(handle.getHandle(), 0);
		banker.setPosition(loc.getX(), loc.getY(), loc.getZ());
		banker.setCustomName(ChatColor.GOLD+""+ChatColor.BOLD+"Sakura Banker");
		banker.setCustomNameVisible(true);
		handle.getHandle().addEntity(banker, SpawnReason.CUSTOM);
	}
}
