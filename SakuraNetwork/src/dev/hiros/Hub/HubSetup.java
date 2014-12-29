package dev.hiros.Hub;

import org.bukkit.plugin.Plugin;

import dev.hiros.SakuraNetwork;

public class HubSetup {
	private static HubSetup inst = new HubSetup();
	public static HubSetup getInstance() {
		return inst;
	}
	
	public void setupHubConfig() {
		Plugin plugin = SakuraNetwork.getInstance();
		
		if(plugin.getConfig().getBoolean("sakuranetwork.hub.setup") != true) {
			plugin.getConfig().set("sakuranetwork.hub.setup", false);
			plugin.saveConfig();
		}
	}
}
