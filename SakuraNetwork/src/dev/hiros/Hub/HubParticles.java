package dev.hiros.Hub;

import org.bukkit.Bukkit;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;

import dev.hiros.SakuraNetwork;
import dev.hiros.Config.Config;
import dev.hiros.Utils.ParticleEffect;

public class HubParticles {
	private static HubParticles inst = new HubParticles();
	public static HubParticles getInstance() {
		return inst;
	}
	
	public int particleid;
	
	public void startRunningParticle(final Player player) {
		final FileConfiguration config = Config.getInstance().getConfig("/members/"+player.getName()+".dat");
		if(config.getString("hub.particles.set") != "none" || config.getString("hub.particles.set") != null) {
			particleid = Bukkit.getServer().getScheduler().scheduleSyncRepeatingTask(SakuraNetwork.getInstance(), new Runnable() {
				@Override
				public void run() {
					if(HubManager.getInstance().getPlayer(player) == null) {
						Bukkit.getServer().getScheduler().cancelTask(particleid);
						return;
					}
					//All the particles
					if(config.getString("hub.particles.set") == "sakuramember") {
						try {
							ParticleEffect.CLOUD.display((float)0.2, 0, (float)0.2, 0, 5, player.getLocation(), 16);
							ParticleEffect.FLAME.display((float)0.5, 0, (float)0.5, (float)0.05, 2, player.getLocation().add(0, 2, 0), 16);
						} catch (Exception e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
					}
				}
			}, 0, 1);
	}
}
	
	public void cancelParticleTask() {
		Bukkit.getServer().getScheduler().cancelTask(particleid);
	}
}