package me.Bokum.ESR;

import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.event.Listener;
import org.bukkit.plugin.java.JavaPlugin;

public class Main extends JavaPlugin implements Listener{
	public void onEnable(){
		getServer().getPluginManager().registerEvents(this, this);
		getLogger().info("■■■■■■■■■■■■■■■■■■■■■■■■■■■■■");
		getLogger().info("■                           ■");
		getLogger().info("■                           ■");
		getLogger().info("■                           ■");
		getLogger().info("■                           ■");
		getLogger().info("------ EG SKY RPG 로드 완료 ------");
		getLogger().info("■                           ■");
		getLogger().info("■                           ■");
		getLogger().info("■                           ■");
		getLogger().info("■                           ■");
		getLogger().info("■■■■■■■■■■■■■■■■■■■■■■■■■■■■■");
		loadConfig();
		initData();
	}
	
	private void loadConfig(){
		
	}
	
	private void initData(){
		
	}
	
	public void onDisable(){
		getLogger().info("■■■■■■■■■■■■■■■■■■■■■■■■■■■■■");
		getLogger().info("■                           ■");
		getLogger().info("■                           ■");
		getLogger().info("■                           ■");
		getLogger().info("■                           ■");
		getLogger().info("------ EG SKY RPG 언로드 완료 ------");
		getLogger().info("■                           ■");
		getLogger().info("■                           ■");
		getLogger().info("■                           ■");
		getLogger().info("■                           ■");
		getLogger().info("■■■■■■■■■■■■■■■■■■■■■■■■■■■■■");
	}
	
	public boolean onCommand(CommandSender talker, Command command, String str, String[] args){
		if(talker instanceof Player){
			Player p = (Player) talker;
			if(str.equalsIgnoreCase("dg") && p.isOp()){
				
			}
		}
	}
}
