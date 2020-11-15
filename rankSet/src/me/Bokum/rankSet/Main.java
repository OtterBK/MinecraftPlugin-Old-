package me.Bokum.rankSet;

import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.event.Listener;
import org.bukkit.plugin.java.JavaPlugin;

public class Main extends JavaPlugin implements Listener{
	public static String MS = "§f[ §aBJ뱀사 §f] ";
	
	public void onEnable(){
		getServer().getPluginManager().registerEvents(this, this);
		getLogger().info("랭크 설정 플러그인 로드");
	}
	
	public void onDisable(){
		getServer().getPluginManager().registerEvents(this, this);
		getLogger().info("랭크 설정 플러그인 언로드");
	}
	
	public boolean onCommand(CommandSender talker, Command command, String str, String[] args){
		if(str.equalsIgnoreCase("rank") && talker instanceof Player){
			Player p = (Player) talker;
			if(args.length <= 0){
				p.sendMessage(MS+"/rank add <닉네임> <점수> - 해당 플레이어의 점수를 더합니다.");
				p.sendMessage(MS+"/rank sub <닉네임> <점수> - 해당 플레이어의 점수를 뺍니다.");
				p.sendMessage(MS+"/rank check <닉네임> - 해당 플레이어의 점수를 봅니다.");
				p.sendMessage(MS+"/rank list - 상위 10위 플레이어의 점수를 봅니다.");
			}
		}
		return false;
	}
}
