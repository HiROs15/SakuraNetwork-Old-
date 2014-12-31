package dev.hiros.Commands.SakuraCommands;

import java.util.ArrayList;

import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import dev.hiros.SakuraNetwork;

public class SakuraCommandManager implements CommandExecutor {
	private static SakuraCommandManager inst = new SakuraCommandManager();
	public static SakuraCommandManager getInstance() {
		return inst;
	}
	
	ArrayList<SakuraPluginCommand> cmds = new ArrayList<SakuraPluginCommand>();
	
	public SakuraCommandManager() {
		
	}
	
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
		if(!(sender instanceof Player)) {
			SakuraNetwork.getInstance().getLogger().warning("Please do not use SakuraNetwork commands through the console.");
			return true;
		}
		
		Player player = (Player) sender;
		
		if(cmd.getName().equalsIgnoreCase("sakura")) {
			if(args.length == 0) {
				
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
