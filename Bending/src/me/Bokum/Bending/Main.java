package me.Bokum.Bending;

import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.event.Listener;
import org.bukkit.plugin.java.JavaPlugin;

public class Main extends JavaPlugin implements Listener{
	
	public void onEnable(){
		getServer().getPluginManager().registerEvents(this, this);
		getLogger().info("���� �÷������� �ε� �Ǿ����ϴ�.");
	}
	
	public void onDisable(){
		getLogger().info("���� �÷������� ��ε� �Ǿ����ϴ�.");
	}
	
	public boolean onCommand(CommandSender talker, Command command, String str, String[] args){
		if(str.equalsIgnoreCase("bd")){
			if(!(talker instanceof Player)) return true;
			Player p = (Player) talker;
			if(args.length <= 0){
				
			}else if(args[0].equalsIgnoreCase("")){
				
			}
		}
		return false;
	}
	
}
