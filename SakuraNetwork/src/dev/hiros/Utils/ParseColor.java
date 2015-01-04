package dev.hiros.Utils;

import org.bukkit.ChatColor;

public class ParseColor {
	private static ParseColor inst = new ParseColor();
	public static ParseColor getInstance() {
		return inst;
	}
	
	public String convert(String msg) {
		String out = msg;
		out = out.replaceAll("&0", ChatColor.BLACK+"");
		out = out.replaceAll("&1", ChatColor.DARK_BLUE+"");
		out = out.replaceAll("&2", ChatColor.DARK_GREEN+"");
		out = out.replaceAll("&3", ChatColor.DARK_AQUA+"");
		out = out.replaceAll("&4", ChatColor.DARK_RED+"");
		out = out.replaceAll("&5", ChatColor.DARK_PURPLE+"");
		out = out.replaceAll("&6", ChatColor.GOLD+"");
		out = out.replaceAll("&7", ChatColor.GRAY+"");
		out = out.replaceAll("&8", ChatColor.DARK_GRAY+"");
		out = out.replaceAll("&9", ChatColor.BLUE+"");
		out = out.replaceAll("&a", ChatColor.GREEN+"");
		out = out.replaceAll("&b", ChatColor.AQUA+"");
		out = out.replaceAll("&c", ChatColor.RED+"");
		out = out.replaceAll("&d", ChatColor.LIGHT_PURPLE+"");
		out = out.replaceAll("&e", ChatColor.YELLOW+"");
		out = out.replaceAll("&f", ChatColor.WHITE+"");
		out = out.replaceAll("&r", ChatColor.RESET+"");
		out = out.replaceAll("&l", ChatColor.BOLD+"");
		out = out.replaceAll("_", " ");
		
		return out;
	}
}
