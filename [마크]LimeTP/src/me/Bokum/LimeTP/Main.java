package me.Bokum.LimeTP;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.event.Listener;
import org.bukkit.plugin.java.JavaPlugin;

public class Main extends JavaPlugin implements Listener{
	
	public void onEnable(){
		getServer().getPluginManager().registerEvents(this, this); //플레이어의 모든 이벤트를 가져온다
		getLogger().info("LimeTp 플러그인이 로드 되었습니다");
	}
	
	public void onDisable(){
		getLogger().info("LimeTp 플러그인이 언로드 되었습니다.");
	}
	
	public boolean onCommand(CommandSender talker, Command command, String str, String args[]){
		if(str.equalsIgnoreCase("limetp")){ //만약에 어떤 사람이 명령어를 쳤는데 이 명령어가 limetp일때
			Player tpFrom = null; //Player 데이터를 담을 수 있는 상자를 하나 만들고 이 상자의 이름을 tpFrom 이라고
			//이름 짓겠다.
			Player tpTo = null; //Player 데이터를 담을 수 있는 상자를 하나 만들고 이 상자으 이름을  tpTo라고 이름 짓겠다.
			
			for(Player p : Bukkit.getOnlinePlayers()){
				if(p.getName().equalsIgnoreCase(args[0])){ //만약에 args[0]
					tpFrom = p; //누굴 TP 시킬까 찾는다.
				}
				if(p.getName().equalsIgnoreCase(args[1])){
					tpTo = p;
				}
			}
			tpFrom.teleport(tpTo);
			talker.sendMessage("텔레포트에 성공했습니다.");
		}
		return false;
	}
}
