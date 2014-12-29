package dev.hiros;

import org.bukkit.Bukkit;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.java.JavaPlugin;

import dev.hiros.Commands.HubCommands.HubCommandManager;
import dev.hiros.Hub.HubSetup;
import dev.hiros.Hub.Events.HubEvents;
import dev.hiros.Hub.HubBank.HubBank;

public class SakuraNetwork extends JavaPlugin {
	public static Plugin getInstance() {
		return Bukkit.getServer().getPluginManager().getPlugin("SakuraNetwork");
	}
	
	@Override
	public void onEnable() {
		setupConfigs();
		
		setupCommands();
		
		setupEvents();
		
		//loadAllCustomMobs();
	}
	
	@Override
	public void onDisable() {
		removeAllCustomMobs();
	}
	
	//All startup methods
	
	public void setupConfigs() {
		//Setup the hub config for chat.
		HubSetup.getInstance().setupHubConfig();
		
		
	}
	
	public void setupCommands() {
		//Hub Commands
		getCommand("hub").setExecutor(new HubCommandManager());
	}
	
	public void setupEvents() {
		//Hub Events
		getServer().getPluginManager().registerEvents(new HubEvents(), this);
	}
	
	public void loadAllCustomMobs() {
		//Load the bankers when the server starts
		HubBank.getInstance().loadBankers();
	}
	
	//All Disable methods
	public void removeAllCustomMobs() {
		//Remove all bankers on shutdown
		HubBank.getInstance().removeAllBankers();
		
	}
}
