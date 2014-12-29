package dev.hiros.Hub.HubBank;

import me.dablakbandit.customentitiesapi.entities.CustomEntityVillager;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.plugin.Plugin;

import dev.hiros.SakuraNetwork;

public class HubBanker {
	private int bankerid;
	private Location loc;
	private CustomEntityVillager banker;
	
	public HubBanker(int bankerid, CustomEntityVillager banker) {
		Plugin plugin = SakuraNetwork.getInstance();
		String path = "sakuranetwork.hub.bankers."+bankerid+".location";
		Location loc = new Location(
				Bukkit.getServer().getWorld(plugin.getConfig().getString(path+".world")),
				plugin.getConfig().getDouble(path+".x"),
				plugin.getConfig().getDouble(path+".y"),
				plugin.getConfig().getDouble(path+".z"),
				(float) plugin.getConfig().getDouble(path+".yaw"),
				(float) plugin.getConfig().getDouble(path+".pitch")
				);
		
		this.bankerid = bankerid;
		this.loc = loc;
		this.banker = banker;
	}
	
	public int getBankerId() {
		return this.bankerid;
	}
	
	public Location getLocation() {
		return this.loc;
	}
	
	public CustomEntityVillager getBanker() {
		return this.banker;
	}
}
