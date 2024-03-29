package dev.hiros.Commands.HubCommands;

import java.util.ArrayList;

import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import dev.hiros.SakuraNetwork;
import dev.hiros.Commands.HubCommands.Commands.Hub_hub;
import dev.hiros.Commands.HubCommands.Commands.Hub_ishubsetup;
import dev.hiros.Commands.HubCommands.Commands.Hub_leavehub;
import dev.hiros.Commands.HubCommands.Commands.Hub_sethub;
import dev.hiros.Commands.HubCommands.Commands.Hub_setparkourblock;
import dev.hiros.Commands.HubCommands.Commands.Hub_setquickwarp;
import dev.hiros.Commands.HubCommands.Commands.Hub_setquickwarppad;
import dev.hiros.Commands.HubCommands.Commands.Hub_spawnbanker;
import dev.hiros.Commands.HubCommands.Commands.Hub_spawnhologram;
import dev.hiros.Commands.HubCommands.Commands.Hub_spawnparkourmob;

public class HubCommandManager implements CommandExecutor {
	ArrayList<HubPluginCommand> cmds = new ArrayList<HubPluginCommand>();
	
	public HubCommandManager() {
		cmds.add(new Hub_ishubsetup());
		cmds.add(new Hub_sethub());
		cmds.add(new Hub_spawnbanker());
		cmds.add(new Hub_spawnhologram());
		cmds.add(new Hub_setquickwarp());
		cmds.add(new Hub_leavehub());
		cmds.add(new Hub_setquickwarppad());
		cmds.add(new Hub_spawnparkourmob());
		cmds.add(new Hub_setparkourblock());
	}
	
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
		if(!(sender instanceof Player)) {
			SakuraNetwork.getInstance().getLogger().warning("Please do not use commands through the console.");
			return true;
		}
		
		Player player = (Player) sender;
		
		if(cmd.getName().equalsIgnoreCase("hub")) {
			if(args.length == 0) {
				Hub_hub hc = new Hub_hub();
				hc.onCommand(player, args);
				return true;
			}
			
			for(HubPluginCommand command : cmds) {
				HubCommandInfo info = command.getClass().getAnnotation(HubCommandInfo.class);
				if(info.command().equalsIgnoreCase(args[0])) {
					command.onCommand(player, args);
					return true;
				}
			}
			player.sendMessage(ChatColor.RED+"ERROR> "+ChatColor.GRAY+"That command does not exist.");
			return true;
		}
		return true;
	}
}
