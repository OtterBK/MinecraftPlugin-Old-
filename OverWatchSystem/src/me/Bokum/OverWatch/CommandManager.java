package me.Bokum.OverWatch;

import java.util.HashMap;

import org.bukkit.entity.Player;

import me.Bokum.Messenger.Messenger;

public class CommandManager {
	
	Messenger mg = new Messenger();
	public static HashMap<String, SetArea> setAreaPlayer = new HashMap<String, SetArea>();
	
	public void commandTeam(Player p, String args[]){
		if(args.length <= 1){
			mg.commandTeamMsg(p);
		} else {
			switch(args[1]){
			case "add":{
				if(args.length <= 2){
					p.sendMessage(mg.MS+"red���� blue�� �� ����� �߰��Ұ��� �Է����ּ���.");
				} else if(!(args[2].equalsIgnoreCase("red") || args[2].equalsIgnoreCase("blue"))){
					p.sendMessage(mg.MS+"red �Ǵ� blue �� �Է��� �����մϴ�.");
				} else {
					if(args.length <= 3){
						p.sendMessage(mg.MS+"� �÷��̾ �߰��Ұ��� �Է����ּ���.");
					} else {
						if(args[2].equalsIgnoreCase("red")){
							GameData.redTeam.addTeam(p, args[3]);
						} else if(args[2].equalsIgnoreCase("blue")){
							GameData.blueTeam.addTeam(p, args[3]);
						}
					}
				}
				break;
			}
			case "remove":{
				if(args.length <= 2){
					p.sendMessage(mg.MS+"red���� blue�� �� ������� �����Ұ��� �Է����ּ���.");
				} else if(!(args[2].equalsIgnoreCase("red") || args[2].equalsIgnoreCase("blue"))){
					p.sendMessage(mg.MS+"red �Ǵ� blue �� �Է��� �����մϴ�.");
				} else {
					if(args.length <= 3){
						p.sendMessage(mg.MS+"� �÷��̾ �����Ұ��� �Է����ּ���.");
					} else {
						if(args[2].equalsIgnoreCase("red")){
							GameData.redTeam.removeTeam(p, args[3]);
						} else if(args[2].equalsIgnoreCase("blue")){
							GameData.blueTeam.removeTeam(p, args[3]);
						}
					}
				}
				break;
			}
			case "list":{
				if(args.length <= 2){
					p.sendMessage(mg.MS+"red���� blue�� �� ����� ���� ����� ������ �Է����ּ���.");
				} else if(!(args[2].equalsIgnoreCase("red") || args[2].equalsIgnoreCase("blue"))){
					p.sendMessage(mg.MS+"red �Ǵ� blue �� �Է��� �����մϴ�.");
				} else {
					if(args[2].equalsIgnoreCase("red")){
						GameData.redTeam.listTeamList(p);
					} else if(args[2].equalsIgnoreCase("blue")){
						GameData.blueTeam.listTeamList(p);
					}
						
				}
				break;
			}
			case "clear":{
				if(args.length <= 2){
					p.sendMessage(mg.MS+"red���� blue�� �� ����� �ʱ�ȭ���� �Է����ּ���.");
				} else if(!(args[2].equalsIgnoreCase("red") || args[2].equalsIgnoreCase("blue"))){
					p.sendMessage(mg.MS+"red �Ǵ� blue �� �Է��� �����մϴ�.");
				} else {
					if(args[2].equalsIgnoreCase("red")){
						GameData.redTeam.clear(p);
					} else if(args[2].equalsIgnoreCase("blue")){
						GameData.blueTeam.clear(p);
					}	
				}
				break;
			}
			default: mg.commandTeamMsg(p); return;
			}
		}
	}
	
	public void commandSetSpawn(Player p, String args[]){
		if(args.length <= 1){
			mg.commandSpawnMsg(p);
		} else {
			if(args.length <= 2){
				p.sendMessage(mg.MS+"���� ������ ���� ������ ����� ������ ������ ������ �� �Է����ּ���.");
			} else if(!(args[2].equalsIgnoreCase("red") || args[2].equalsIgnoreCase("blue"))){
				p.sendMessage(mg.MS+"attack �Ǵ� defense �� �Է��� �����մϴ�.");
			} else {
				if(args.length <= 3){
					p.sendMessage(mg.MS+"1°�� 2��° ������ �������ּ���.");
				} else if(!(args[3].equalsIgnoreCase("1") || args[3].equalsIgnoreCase("2"))){
					p.sendMessage(mg.MS+"1���� 2��°������ �����̰����մϴ�.");
				} else {
					if(args[2].equalsIgnoreCase("attack")){
						if(args[3].equalsIgnoreCase("1")){
							GameData.attackSpawn1 = p.getLocation();
						} else if(args[3].equalsIgnoreCase("2")){
							GameData.attackSpawn2 = p.getLocation();
						}
					} else if(args[2].equalsIgnoreCase("defense")){
						if(args[3].equalsIgnoreCase("1")){
							GameData.defenseSpawn1 = p.getLocation();
						} else if(args[3].equalsIgnoreCase("2")){
							GameData.defenseSpawn2 = p.getLocation();
						}
					}
					GameData.save();
				}
			}
		}
	}
	
	public void commandSetArea(Player p, String args[]){
		if(args.length <= 1){
			mg.commandSetAreaMsg(p);
		} else {
			if(args.length <= 2){
				p.sendMessage(mg.MS+"�� 1������ 2������ ��� ������ ���Ͽ� �������� �Է����ּ���.");
			} else if(!(args[1].equalsIgnoreCase("1") || args[2].equalsIgnoreCase("2"))){
				p.sendMessage(mg.MS+"�� 1������ 2������ ������ �����մϴ�.");
			} else {
				setAreaPlayer.put(p.getName(), new SetArea(p, (args[1].equalsIgnoreCase("1") ? 1:2)));
				p.sendMessage("������ �밢�� �������� Ŭ���ϼ���.");
			}
		}
	}
	
}
