package dev.hiros.Hub.QuickWarp;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.configuration.file.FileConfiguration;

import dev.hiros.Config.Config;

public class QuickWarpPad {
	private Location loc;
	private int gameid;
	private String padName;
	
	public QuickWarpPad(String padName) {
		FileConfiguration config = Config.getInstance().getConfig("/hub/quickwarp.data");
		Location l = new Location(
					Bukkit.getServer().getWorld(config.getString("pads."+padName+".location.world")),
					config.getDouble("pads."+padName+".location.x"),
					config.getDouble("pads."+padName+".location.y"),
					config.getDouble("pads."+padName+".location.z"),
					(float) config.getDouble("pads."+padName+".location.yaw"),
					(float) config.getDouble("pads."+padName+".location.pitch")
				);
		this.padName = padName;
		this.gameid = config.getInt("pads."+padName+".gameid");
		this.loc = l;
	}
	
	public Location getLocation() {
		return this.loc;
	}
	
	public int getGameId() {
		return this.gameid;
	}
	
	public String getPadName() {
		return this.padName;
	}
}
