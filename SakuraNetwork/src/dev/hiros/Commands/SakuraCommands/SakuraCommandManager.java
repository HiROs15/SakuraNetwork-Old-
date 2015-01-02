package dev.hiros.Commands.SakuraCommands;

import java.util.ArrayList;

import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import dev.hiros.SakuraNetwork;
import dev.hiros.Commands.SakuraCommands.Commands.Sakura_addcoins;
import dev.hiros.Commands.SakuraCommands.Commands.Sakura_giveparticleeffect;
import dev.hiros.Commands.SakuraCommands.Commands.Sakura_sakura;

public class SakuraCommandManager implements CommandExecutor {
	private static SakuraCommandManager inst = new SakuraCommandManager();
	public static SakuraCommandManager getInstance() {
		return inst;
	}
	
	ArrayList<SakuraPluginCommand> cmds = new ArrayList<SakuraPluginCommand>();
	
	public SakuraCommandManager() {
		cmds.add(new Sakura_addcoins());
		cmds.add(new Sakura_giveparticleeffect());
	}
	
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
		if(!(sender instanceof Player)) {
			SakuraNetwork.getInstance().getLogger().warning("Please do not use SakuraNetwork commands through the console.");
			return true;
		}
		
		Player player = (Player) sender;
		
		if(cmd.getName().equalsIgnoreCase("sakura")) {
			if(args.length == 0) {
				Sakura_sakura s = new Sakura_sakura();
				s.onCommand(player, args);
				return true;
			}
			
			for(SakuraPluginCommand command : cmds) {
				SakuraCommandInfo info = command.getClass().getAnnotation(SakuraCommandInfo.class);
				if(info.command().equalsIgnoreCase(args[0])) {
					command.onCommand(player, args);
					return true;
				}
			}
			
			player.sendMessage(ChatColor.GREEN+"SYSTEM> "+ChatColor.GRAY+"That command does not exist.");
			return true;
		}
		return true;
	}
}
