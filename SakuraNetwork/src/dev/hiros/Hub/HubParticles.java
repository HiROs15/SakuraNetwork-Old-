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
		if(config.getString("hub.particles.set") != null) {
			particleid = Bukkit.getServer().getScheduler().scheduleSyncRepeatingTask(SakuraNetwork.getInstance(), new Runnable() {
				@Override
				public void run() {
					FileConfiguration configa = Config.getInstance().getConfig("/members/"+player.getName()+".dat");
					if(HubManager.getInstance().getPlayer(player) == null) {
						Bukkit.getServer().getScheduler().cancelTask(particleid);
					}
					//All the particles
					//SakuraMember particles
					if(configa.getString("hub.particles.set").equals("sakuramember")) {
						try {
							ParticleEffect.CLOUD.display((float)0.2, 0, (float)0.2, 0, 5, player.getLocation(), 16);
							ParticleEffect.FLAME.display((float)0.5, 0, (float)0.5, (float)0.05, 2, player.getLocation().add(0, 2, 0), 16);
						} catch (Exception e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
					}
					
					//Green Particles
					else if(configa.getString("hub.particles.set").equals("greenparticles")) {
						try {
							ParticleEffect.VILLAGER_HAPPY.display((float) 0.1, (float) 0.2, (float) 0.1, 0, 4, player.getLocation().add(0, 1, 0), 16);
						} catch(Exception e){}
					}
					
					//Blue Particles
					else if(configa.getString("hub.particles.set").equals("blueparticles")) {
						try {
							ParticleEffect.WATER_SPLASH.display((float) 0.1, (float) 0.2, (float) 0.1, 0, 10, player.getLocation().add(0, 1, 0), 16);
						} catch(Exception e) {}
					}
					
					//Ender Particles
					else if(configa.getString("hub.particles.set").equals("enderparticles")) {
						try {
							ParticleEffect.PORTAL.display((float) 0.5, (float)0, (float)0.5, (float)0.4, 8, player.getLocation().add(0, 0, 0), 16);
						} catch(Exception e) {}
					}
				}
			}, 0, 1);
	}
}
	
	public void cancelParticleTask() {
		Bukkit.getServer().getScheduler().cancelTask(particleid);
	}
}