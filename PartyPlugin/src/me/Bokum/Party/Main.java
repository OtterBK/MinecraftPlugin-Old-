package me.Bokum.Party;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.event.Listener;
import org.bukkit.inventory.Inventory;
import org.bukkit.plugin.java.JavaPlugin;

public class Main extends JavaPlugin implements Listener
{
	public static Inventory partyMenu;
	
	public void onEnable(){
		getServer().getPluginManager().registerEvents(this, this);
		
		partyMenu = Bukkit.createInventory(null, 45, "§c§l파티 메뉴");
		
		getLogger().info("■■■■■■■■■■■■■■■■■■■■■■");
		getLogger().info("■ 보끔의 파티 플러그인 로드 완료 ■");
		getLogger().info("■■■■■■■■■■■■■■■■■■■■■■");
	}
	
	public void onDisable(){
		getLogger().info("■■■■■■■■■■■■■■■■■■■■■■■");
		getLogger().info("■ 보끔의 파티 플러그인 언로드 완료 ■");
		getLogger().info("■■■■■■■■■■■■■■■■■■■■■■■");
	}
	
	public boolean onCommand(CommandSender talker, Command command, String str, String[] args){
		if(talker instanceof Player){
			Player p = (Player) talker;
			if(str.equalsIgnoreCase("파티")){
				partyCommand(p, args);
				return true;
			}
		}
		return false;
	}
	
	public void partyCommand(Player p, String args[]){
		p.openInventory(partyMenu);
	}
}
