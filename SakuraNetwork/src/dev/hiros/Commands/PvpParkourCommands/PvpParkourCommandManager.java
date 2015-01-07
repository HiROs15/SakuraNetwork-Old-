package dev.hiros.Commands.PvpParkourCommands;

import java.util.ArrayList;

import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import dev.hiros.SakuraNetwork;
import dev.hiros.Commands.PvpParkourCommands.Commands.PvpParkour_setarena;
import dev.hiros.Commands.PvpParkourCommands.Commands.PvpParkour_setlobby;
import dev.hiros.Commands.PvpParkourCommands.Commands.setarenaspawn;
import dev.hiros.Commands.PvpParkourCommands.Commands.warparena;

public class PvpParkourCommandManager implements CommandExecutor {
	public static PvpParkourCommandManager getInstance() {
		return new PvpParkourCommandManager();
	}
	
	ArrayList<PvpParkourPluginCommand> cmds = new ArrayList<PvpParkourPluginCommand>();
	
	public PvpParkourCommandManager() {
		//Add commands
		cmds.add(new PvpParkour_setlobby());
		cmds.add(new PvpParkour_setarena());
		cmds.add(new warparena());
		cmds.add(new setarenaspawn());
	}
	
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
		if(!(sender instanceof Player)) {
			SakuraNetwork.getInstance().getLogger().warning("Please do not use SakuraNetwork commands through the console");
			return false;
		}
		
		Player player = (Player) sender;
		
		if(cmd.getName().equalsIgnoreCase("pvpparkour")) {
			if(args.length == 0) {
				
				return false;
			}
			
			for(PvpParkourPluginCommand command : cmds) {
				PvpParkourCommandInfo info = command.getClass().getAnnotation(PvpParkourCommandInfo.class);
				if(info.command().equalsIgnoreCase(args[0])) {
					command.onCommand(player, args);
					return false;
				}
			}
			player.sendMessage(ChatColor.RED+"PVPPARKOUR> "+ChatColor.GRAY+"That is not a command.");
			return false;
		}
		return false;
	}
}
