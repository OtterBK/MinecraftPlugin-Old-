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
				p.sendMessage("��f[ ��4��� ��f] ��f"+pname+" ���� �̹� ��b"+teamName+"��f�� ���ذ�ʴϴ�.");
				return;
			} 
		}
		list.add(pname);
		p.sendMessage("��f[ ��2�� ���� ��f] ���������� ��6"+pname+" ���� "+"��b"+teamName+" ��f�� �߰��Ͽ����ϴ�.");
	}
	
	public void removeTeam(Player p, String pname){
		for(int i = 0; i < list.size(); i++){
			String tname = list.get(i);
			if(tname.equalsIgnoreCase("pname")){
				list.remove(pname);
				p.sendMessage("��f[ ��2�� ���� ��f] ���������� ��6"+pname+" ���� "+"��b"+teamName+" ��f���� �����Ͽ����ϴ�.");
				return;
			}
		}
		p.sendMessage("��f[ ��4��� ��f] ��6"+pname+" ���� ��b"+teamName+"��6�� ���� �����ʽ��ϴ�.");
	}
	
	public void clear(Player p){
		list.clear();
		p.sendMessage("��f[ ��2�� ���� ��f] ���������� &b"+teamName+"��f�� �ʱ�ȭ�Ͽ����ϴ�.");
	}
	
	public void sendMessge(String msg){
		for(int i = 0; i < list.size(); i++){
			Player p = Bukkit.getPlayer(list.get(i));
			p.sendMessage("��f[ ��4�˸� ��f] "+msg);
		}
	}
	
	public void teamMessage(Player p, String msg){
		for(int i = 0; i< list.size(); i++){
			Player t = Bukkit.getPlayer(list.get(i));
			p.sendMessage("��f[ ��a��ê ��f]��b "+p.getName()+": "+msg);
		}
	}
	
	public void listTeamList(Player p){
		p.sendMessage("��e============ ��b"+teamName+" ��� ��e============");
		for(int i = 0; i < list.size(); i++){
			p.sendMessage("��a"+i+". ��d"+list.get(i));
		}
		p.sendMessage("��e=================================================");
	}
	
	public void 
}
