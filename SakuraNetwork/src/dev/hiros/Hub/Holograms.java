package dev.hiros.Hub;

import java.util.ArrayList;
import java.util.Set;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.configuration.ConfigurationSection;

import com.gmail.filoghost.holographicdisplays.api.Hologram;
import com.gmail.filoghost.holographicdisplays.api.HologramsAPI;

import dev.hiros.SakuraNetwork;
import dev.hiros.Config.Config;

public class Holograms {
	private static Holograms inst = new Holograms();
	public static Holograms getInstance() {
		return inst;
	}
	
	public ArrayList<Hologram> hubHolograms = new ArrayList<Hologram>();
	
	public void removeHolograms() {
		for(Hologram h : hubHolograms) {
			h.delete();
		}
	}
	
	@SuppressWarnings("unused")
	public void spawnAllHolograms() {
		String path = "/hub/hologram.yml";
		ConfigurationSection holograms = Config.getInstance().getConfig(path).getConfigurationSection("hologram");
		if(holograms == null) {
			holograms = Config.getInstance().getConfig(path).createSection("hologram");
		}
		Set<String> hologramsKey = holograms.getKeys(false);
		for(String key : hologramsKey) {
			String msg = "a";
			switch(key) {
			case "1":
				msg = ChatColor.GREEN+""+ChatColor.BOLD+"PvP Parkour";
			break;
			case "2":
				msg = ChatColor.GREEN+""+ChatColor.BOLD+"Minecraft Races";
			break;
			case "3":
				msg = ChatColor.GREEN+""+ChatColor.BOLD+"Crazy Speef";
			break;
			case "4":
				msg = ChatColor.GREEN+""+ChatColor.BOLD+"Mob Hide and Seek";
			break;
			case "5":
				msg = ChatColor.GREEN+""+ChatColor.BOLD+"One in the Chamber";
			break;
			case "6":
				msg = ChatColor.GREEN+""+ChatColor.BOLD+"Sakura Tag";
			break;
			}
			
			Location loc = new Location(
					Bukkit.getServer().getWorld(Config.getInstance().getConfig(path).getString("hologram."+key+".world")),
					Config.getInstance().getConfig(path).getDouble("hologram."+key+".x"),
					Config.getInstance().getConfig(path).getDouble("hologram."+key+".y"),
					Config.getInstance().getConfig(path).getDouble("hologram."+key+".z"),
					(float) Config.getInstance().getConfig(path).getDouble("hologram."+key+".yaw"),
					(float) Config.getInstance().getConfig(path).getDouble("hologram."+key+".pitch")
					);
			
			Hologram h = HologramsAPI.createHologram(SakuraNetwork.getInstance(), loc);
			h.appendTextLine("why hello");
			
			hubHolograms.add(h);
		}
	}
}
