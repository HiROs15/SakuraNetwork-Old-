package dev.hiros.Hub.HubStuff;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.DyeColor;
import org.bukkit.Material;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.material.Dye;

import dev.hiros.SakuraNetwork;
import dev.hiros.Config.Config;
import dev.hiros.Utils.ItemMenu;

public class HubStuff {
	private static HubStuff inst = new HubStuff();
	public static HubStuff getInstance() {
		return inst;
	}
	
	public void openInventory(final Player player) {
		ItemMenu menu = new ItemMenu(9, ChatColor.GOLD+""+ChatColor.BOLD+"My Stuff", new ItemMenu.OptionClickEventHandler() {
			public void onOptionClick(ItemMenu.OptionClickEvent event) {
				event.setWillClose(true);
				//Particle effects
				if(event.getName().equals(ChatColor.GOLD+""+ChatColor.BOLD+"My Particle Effects")) {
					Bukkit.getServer().getScheduler().scheduleSyncDelayedTask(SakuraNetwork.getInstance(), new Runnable() {
						@Override
						public void run() {
							openParticleEffectsInventory(player);
						}
					}, 5);
				}
			}
		}, SakuraNetwork.getInstance());
		//Categories
		menu.setOption(0, new ItemStack(Material.GOLD_NUGGET, 1), ChatColor.GOLD+""+ChatColor.BOLD+"My Particle Effects", ChatColor.GRAY+"Click to view my particle effects");
		menu.open(player);
	}
	
	public void openParticleEffectsInventory(final Player player) {
		ItemMenu particleMenu = new ItemMenu(54, ChatColor.GOLD+""+ChatColor.BOLD+"My Particle Effects", new ItemMenu.OptionClickEventHandler() {
			public void onOptionClick(ItemMenu.OptionClickEvent event) {
				event.setWillDestroy(true);
				
				if(event.getName().equals(ChatColor.LIGHT_PURPLE+"Sakura Member Particle Effect")) {
					toggleParticleEffect(player, "sakuramember");
				}
			}
		}, SakuraNetwork.getInstance());
		//Load my particles effects
		FileConfiguration config = Config.getInstance().getConfig("/members/"+player.getName()+".dat");
		int index = 0;
		if(config.getConfigurationSection("stuff.mystuff.particles") == null) {
			for(int i = 0;i<54;i++) {
				Dye dye = new Dye();
				dye.setColor(DyeColor.GRAY);
				ItemStack is = dye.toItemStack(1);
				particleMenu.setOption(i, is , ChatColor.RED+""+ChatColor.BOLD+"You do not have any particle effects.");
			}
		}
		
		if(config.getConfigurationSection("stuff.mystuff.particles") != null && config.getBoolean("stuff.mystuff.particles.sakuramember") != false) {
			Dye dye = new Dye();
			if(config.getString("hub.particles.set") != "sakuramember") {
				dye.setColor(DyeColor.GRAY);
			} else {
				dye.setColor(DyeColor.PINK);
			}
			particleMenu.setOption(index, dye.toItemStack(1), ChatColor.LIGHT_PURPLE+"Sakura Member Particle Effect");
			index++;
		}
		
		particleMenu.open(player);
	}
	
	public void toggleParticleEffect(Player player, String effect) {
		String path = "/members/"+player.getName()+".yml";
		FileConfiguration config = Config.getInstance().getConfig(path);
		
		if(config.getString("hub.particles.set") == effect) {
			config.set("hub.particles.set", "none");
			Config.getInstance().saveConfig(path);
			
			player.sendMessage(ChatColor.GREEN+"SYSTEM> "+ChatColor.GRAY+"You have removed your particle effect.");
		} else {
			config.set("hub.particles.set", effect);
			Config.getInstance().saveConfig(path);
			
			player.sendMessage(ChatColor.GREEN+"SYSTEM> "+ChatColor.GRAY+"You have set a particle effect.");
		}
	}
}
