package me.Limes.LimeTP;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.event.Listener;
import org.bukkit.plugin.java.JavaPlugin;

public class Main extends JavaPlugin implements Listener{
	public void onEnable(){
		getServer().getPluginManager().registerEvents(this, this); 
		//서버에서 일어나는 이벤트를 LimeTP에 가져오겠습니다.
		getLogger().info("LimeTP 플러그인을 로드 했습니다.");
		//Bukkit창에 문구 출력
	}
	
	public void onDisable(){
		getLogger().info("LimeTP 플러그인을 언로드 했습니다.");
		//Bukkit창에 문구 출력
	}
	//talker(CommandSender) -> 명령어를 입력한사람 str(String) -> 무슨명령어인가? args[]->명령어 뒤에 쓴 문자들
	public boolean onCommand(CommandSender talker, Command command, String str, String args[]){
		//만약 입력한 명령어가 limetp 일때
		if(str.equalsIgnoreCase("limetp")){
			Player tpFrom = null; //무슨 플레이어를 tp 시킬지 플레이어형의 변수를 하나 선언
			Player tpTo = null; //무슨 플레이어에게 tp 시킬지 플레이어형의 변수를 하나 선언
			//args[0] -> Lime
			//args[1] -> Bokum
			//Bokum, Toma, Nice ,Lime
			for(Player p : Bukkit.getOnlinePlayers()){
				if(p.getName().equalsIgnoreCase(args[0])){
					tpFrom = p;
				}
				if(p.getName().equalsIgnoreCase(args[1])){
					tpTo = p;
				}
			}
			//|| 는 또는이라는 의미
			if(tpFrom == null || tpTo == null){
				talker.sendMessage("둘 중 한명이 서버에 접속중이지 않습니다.");
			} else {
				tpFrom.teleport(tpTo);
				talker.sendMessage("텔레포트를 완료했습니다.");
			}
		}
		return false;
	}
}
