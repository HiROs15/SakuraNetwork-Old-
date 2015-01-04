package dev.hiros;

import java.io.File;

import org.bukkit.Bukkit;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.java.JavaPlugin;

import dev.hiros.Commands.HubCommands.HubCommandManager;
import dev.hiros.Commands.SakuraCommands.SakuraCommandManager;
import dev.hiros.Hub.HubSetup;
import dev.hiros.Hub.Events.HubEvents;
import dev.hiros.Hub.Parkour.ParkourManager;
import dev.hiros.Hub.QuickWarp.QuickWarp;
import dev.hiros.ServerStats.Events.ServerStatsEvents;

public class SakuraNetwork extends JavaPlugin {
	public static Plugin getInstance() {
		return Bukkit.getServer().getPluginManager().getPlugin("SakuraNetwork");
	}
	
	@Override
	public void onEnable() {
		setupConfigs();
		
		setupCommands();
		
		setupEvents();
		
		setupDirectories();
		
		hubEnableMethods();
	}
	
	@Override
	public void onDisable() {
		hubDisableMethods();
	}
	
	//All startup methods
	
	public void setupConfigs() {
		//Setup the hub config for chat.
		HubSetup.getInstance().setupHubConfig();
		
		
	}
	
/*	@SuppressWarnings({ "rawtypes", "unchecked" })
	public void registerEntities() {
		//Register Banker
		try {
			Field c = EntityTypes.class.getDeclaredField("c");
			Field d = EntityTypes.class.getDeclaredField("d");
			Field e = EntityTypes.class.getDeclaredField("e");
			Field f = EntityTypes.class.getDeclaredField("f");
			Field g = EntityTypes.class.getDeclaredField("g");
			
			c.setAccessible(true);
			d.setAccessible(true);
			e.setAccessible(true);
			f.setAccessible(true);
			g.setAccessible(true);
			
			Map cmap = (Map) c.get(null);
			Map dmap = (Map) d.get(null);
			Map emap = (Map) e.get(null);
			Map fmap = (Map) f.get(null);
			Map gmap = (Map) g.get(null);
			
			cmap.put("Banker", EntityBanker.class);
			dmap.put(EntityBanker.class, "Banker");
			emap.put(120, EntityBanker.class);
			fmap.put(EntityBanker.class, 120);
			gmap.put("Banker", 120);
			
			c.set(null, cmap);
			d.set(null, dmap);
			e.set(null, emap);
			f.set(null, fmap);
			g.set(null, gmap);
		} catch(Exception e){}
	}*/
	
	public void setupCommands() {
		//Hub Commands
		getCommand("hub").setExecutor(new HubCommandManager());
		
		//Sakura Commands
		getCommand("sakura").setExecutor(new SakuraCommandManager());
	}
	
	public void setupEvents() {
		//Hub Events
		getServer().getPluginManager().registerEvents(new HubEvents(), this);
		
		//Server Ping Events
		getServer().getPluginManager().registerEvents(new ServerStatsEvents(), this);
	}
	
	public void setupDirectories() {
		//Create members folder if it doesnt exist
		File folder = new File(getDataFolder() + "/members");
		folder.mkdir();
		
		//Create the hub folder
		File foldera = new File(getDataFolder() + "/hub");
		foldera.mkdir();
		
		//Create minigames folder
		File folderb = new File(getDataFolder() + "/minigames");
		folderb.mkdir();
		
		//Create PvpParkour folder
		File folderc = new File(getDataFolder() + "/minigames/pvpparkour");
		folderc.mkdir();
	}
	
	public void hubEnableMethods() {
		//Load all the quick warp pads
		QuickWarp.getInstance().loadPads();
		
		//Respawn parkour mobs
		ParkourManager.getInstance().reloadParkourMobs();
		
		//Load all parkour blocks
		ParkourManager.getInstance().loadParkourBlocks();
	}
	
	//All Disable methods
	
	public void hubDisableMethods() {
		//Remove Parkour Mobs
		ParkourManager.getInstance().removeParkourMobs();
		
	}
}