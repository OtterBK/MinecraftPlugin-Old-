package me.Bokum.rankSet;

import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.event.Listener;
import org.bukkit.plugin.java.JavaPlugin;

public class Main extends JavaPlugin implements Listener{
	public static String MS = "��f[ ��aBJ��� ��f] ";
	
	public void onEnable(){
		getServer().getPluginManager().registerEvents(this, this);
		getLogger().info("��ũ ���� �÷����� �ε�");
	}
	
	public void onDisable(){
		getServer().getPluginManager().registerEvents(this, this);
		getLogger().info("��ũ ���� �÷����� ��ε�");
	}
	
	public boolean onCommand(CommandSender talker, Command command, String str, String[] args){
		if(str.equalsIgnoreCase("rank") && talker instanceof Player){
			Player p = (Player) talker;
			if(args.length <= 0){
				p.sendMessage(MS+"/rank add <�г���> <����> - �ش� �÷��̾��� ������ ���մϴ�.");
				p.sendMessage(MS+"/rank sub <�г���> <����> - �ش� �÷��̾��� ������ ���ϴ�.");
				p.sendMessage(MS+"/rank check <�г���> - �ش� �÷��̾��� ������ ���ϴ�.");
				p.sendMessage(MS+"/rank list - ���� 10�� �÷��̾��� ������ ���ϴ�.");
			}
		}
		return false;
	}
}
