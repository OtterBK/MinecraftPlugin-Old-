package me.Bokum.Team;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

public class Team {
	private List<String> list;
	private String teamName;
	
	public Team(){
		teamName = "";
		list = new ArrayList<String>();
	}
	
	public boolean setTeamName(String name){
		teamName = name;
		return true;
	}
	
	public void addTeam(Player p, String pname){
		for(int i = 0; i < list.size(); i++){
			String tname = list.get(i);
			if(tname.equalsIgnoreCase(pname)){
				p.sendMessage("§f[ §4경고 §f] §f"+pname+" 님은 이미 §b"+teamName+"§f에 속해계십니다.");
				return;
			} 
		}
		list.add(pname);
		p.sendMessage("§f[ §2팀 설정 §f] 성공적으로 §6"+pname+" 님을 "+"§b"+teamName+" §f에 추가하였습니다.");
	}
	
	public void removeTeam(Player p, String pname){
		for(int i = 0; i < list.size(); i++){
			String tname = list.get(i);
			if(tname.equalsIgnoreCase("pname")){
				list.remove(pname);
				p.sendMessage("§f[ §2팀 설정 §f] 성공적으로 §6"+pname+" 님을 "+"§b"+teamName+" §f에서 제외하였습니다.");
				return;
			}
		}
		p.sendMessage("§f[ §4경고 §f] §6"+pname+" 님은 §b"+teamName+"§6에 속해 있지않습니다.");
	}
	
	public void clear(Player p){
		list.clear();
		p.sendMessage("§f[ §2팀 설정 §f] 성공적으로 &b"+teamName+"§f을 초기화하였습니다.");
	}
	
	public void sendMessge(String msg){
		for(int i = 0; i < list.size(); i++){
			Player p = Bukkit.getPlayer(list.get(i));
			p.sendMessage("§f[ §4알림 §f] "+msg);
		}
	}
	
	public void teamMessage(Player p, String msg){
		for(int i = 0; i< list.size(); i++){
			Player t = Bukkit.getPlayer(list.get(i));
			p.sendMessage("§f[ §a팀챗 §f]§b "+p.getName()+": "+msg);
		}
	}
	
	public void listTeamList(Player p){
		p.sendMessage("§e============ §b"+teamName+" 목록 §e============");
		for(int i = 0; i < list.size(); i++){
			p.sendMessage("§a"+i+". §d"+list.get(i));
		}
		p.sendMessage("§e=================================================");
	}
	
	public void 
}
