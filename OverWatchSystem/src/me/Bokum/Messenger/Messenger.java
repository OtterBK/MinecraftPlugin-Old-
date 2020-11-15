package me.Bokum.Messenger;

import org.bukkit.entity.Player;

public class Messenger {
	public String MS = "§f[§dOWS§f] ";
	
	public void commandMainMsg(Player p){
		p.sendMessage(MS+"/ows team");
		p.sendMessage(MS+"/ows setarea");
		p.sendMessage(MS+"/ows setspawn");
		p.sendMessage(MS+"/ows setstartdoor");
		p.sendMessage(MS+"/ows sta");
	}
	
	public void commandTeamMsg(Player p){
		p.sendMessage(MS+"/ows team add <red/blue> <닉네임>");
		p.sendMessage(MS+"/ows team remove <red/blue> <닉네임>");
		p.sendMessage(MS+"/ows team list <red/blue>");
		p.sendMessage(MS+"/ows team clear <red/blue>");
	}
	
	public void commandSpawnMsg(Player p){
		p.sendMessage(MS+"/ows setspawn <attack/defense> <1/2>");
	}
	
	public void commandStartDoorMsg(Player p){
		p.sendMessage(MS+"/ows setstartdoor <x1> <y1> <z1> <x2> <y2> <z2>");
	}
	
	public void commandSetAreaMsg(Player p){
		p.sendMessage(MS+"/ows setarealoc <1/2> <x1> <y1> <z1> <x2> <y2> <z2>");
	}
}
