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
					p.sendMessage(mg.MS+"red팀과 blue팀 중 어떤팀에 추가할건지 입력해주세요.");
				} else if(!(args[2].equalsIgnoreCase("red") || args[2].equalsIgnoreCase("blue"))){
					p.sendMessage(mg.MS+"red 또는 blue 만 입력이 가능합니다.");
				} else {
					if(args.length <= 3){
						p.sendMessage(mg.MS+"어떤 플레이어를 추가할건지 입력해주세요.");
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
					p.sendMessage(mg.MS+"red팀과 blue팀 중 어떤팀에서 삭제할건지 입력해주세요.");
				} else if(!(args[2].equalsIgnoreCase("red") || args[2].equalsIgnoreCase("blue"))){
					p.sendMessage(mg.MS+"red 또는 blue 만 입력이 가능합니다.");
				} else {
					if(args.length <= 3){
						p.sendMessage(mg.MS+"어떤 플레이어를 삭제할건지 입력해주세요.");
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
					p.sendMessage(mg.MS+"red팀과 blue팀 중 어떤팀의 팀원 목록을 보실지 입력해주세요.");
				} else if(!(args[2].equalsIgnoreCase("red") || args[2].equalsIgnoreCase("blue"))){
					p.sendMessage(mg.MS+"red 또는 blue 만 입력이 가능합니다.");
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
					p.sendMessage(mg.MS+"red팀과 blue팀 중 어떤팀을 초기화할지 입력해주세요.");
				} else if(!(args[2].equalsIgnoreCase("red") || args[2].equalsIgnoreCase("blue"))){
					p.sendMessage(mg.MS+"red 또는 blue 만 입력이 가능합니다.");
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
				p.sendMessage(mg.MS+"공격 진영과 수비 진영중 어떤팀의 진영의 스폰을 설정할 지 입력해주세요.");
			} else if(!(args[2].equalsIgnoreCase("red") || args[2].equalsIgnoreCase("blue"))){
				p.sendMessage(mg.MS+"attack 또는 defense 만 입력이 가능합니다.");
			} else {
				if(args.length <= 3){
					p.sendMessage(mg.MS+"1째와 2번째 스폰중 선택해주세요.");
				} else if(!(args[3].equalsIgnoreCase("1") || args[3].equalsIgnoreCase("2"))){
					p.sendMessage(mg.MS+"1번과 2번째까지만 설정이가능합니다.");
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
				p.sendMessage(mg.MS+"제 1거점과 2거점중 어느 거점에 대하여 설정할지 입력해주세요.");
			} else if(!(args[1].equalsIgnoreCase("1") || args[2].equalsIgnoreCase("2"))){
				p.sendMessage(mg.MS+"제 1거점과 2거점만 설정이 가능합니다.");
			} else {
				setAreaPlayer.put(p.getName(), new SetArea(p, (args[1].equalsIgnoreCase("1") ? 1:2)));
				p.sendMessage("지역의 대각선 꼭지점을 클릭하세요.");
			}
		}
	}
	
}
