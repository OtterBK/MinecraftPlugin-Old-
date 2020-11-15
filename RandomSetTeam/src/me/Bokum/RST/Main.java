package me.Bokum.RST;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.event.Listener;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scoreboard.Scoreboard;
import org.bukkit.scoreboard.ScoreboardManager;
import org.bukkit.scoreboard.Team;

public class Main extends JavaPlugin implements Listener{
	public static int posTimer = 0;
	public static List<String> teamList = new ArrayList<String>(6);
	public static String MS = "��f[ ��aBJ��� ��f] ";
	public static ScoreboardManager manager;
	public static Scoreboard board;
	
	public void onEnable(){
		getServer().getPluginManager().registerEvents(this, this);
		
		manager = Bukkit.getScoreboardManager();
		board = manager.getNewScoreboard();
		registerTeam();
		
		getLogger().info("���� �� ���� �÷����� �ε� �Ϸ�");
	}
	
	private void registerTeam(){
		createTeam("������", "��c");
		createTeam("�����", "��b");
		createTeam("�׸���", "��a");
		createTeam("���ο���", "��e");
		createTeam("��������", "��6");
		createTeam("������", "��4");
	}
	
	private void createTeam(String teamName, String teamColor){
		/*Team team = board.registerNewTeam(teamColor+teamName);
		team.setPrefix(teamColor);
		team.setDisplayName(teamColor+teamName);
		team.setAllowFriendlyFire(false);
		team.setCanSeeFriendlyInvisibles(true);
		teamList.add(team);*/
		Bukkit.dispatchCommand(getServer().getConsoleSender(), "scoreboard teams add "+teamColor+teamName);
		Bukkit.dispatchCommand(getServer().getConsoleSender(), "scoreboard teams option "+teamColor+teamName+" friendlyfire false");
		String color = "red";
		if(teamColor.equalsIgnoreCase("��c")) color = "red";
		if(teamColor.equalsIgnoreCase("��b")) color = "blue";
		if(teamColor.equalsIgnoreCase("��a")) color = "green";
		if(teamColor.equalsIgnoreCase("��e")) color = "yellow";
		if(teamColor.equalsIgnoreCase("��6")) color = "gold";
		if(teamColor.equalsIgnoreCase("��4")) color = "dark_red";
		teamList.add(teamColor+teamName);
		Bukkit.dispatchCommand(getServer().getConsoleSender(), "scoreboard teams option "+teamColor+teamName+" color "+color);
	}
	
	public void onDisable(){
		getLogger().info("���� �� ���� �÷����� ��ε� �Ϸ�");
	}
	
	private int getRandom(int min, int max){
		  return (int)(Math.random() * (max-min+1)+min);
	}
	
	public boolean onCommand(CommandSender talker, Command command, String str, String[] args){
		if(talker instanceof Player){
			Player p = (Player) talker;
			if(!p.isOp()) return true;
			if(str.equalsIgnoreCase("start")){
				if(args.length <= 0){
					p.sendMessage(MS+"/start <���������ο���>");
				}else{
					try{
						int amt = Integer.valueOf(args[0]);
						if(Bukkit.getOnlinePlayers().length / amt >= 7){
							p.sendMessage(MS+"�ִ� 6���� ���� ������ �����մϴ�. ���� ���� �ο����� �������ּ���.");
							return true;
						}
						List<Player> randomList = new ArrayList<Player>(Bukkit.getOnlinePlayers().length);
						List<Player> tmpList = new ArrayList<Player>(Bukkit.getOnlinePlayers().length);
						for(Player t : Bukkit.getOnlinePlayers()){
							tmpList.add(t);
						}
						while(tmpList.size() > 0){
							int rn = getRandom(0, tmpList.size()-1);
							randomList.add(tmpList.get(rn));
							tmpList.remove(rn);
						}
						int teamNum = 0;
						int teamCnt = 0;
						Bukkit.broadcastMessage("[DB] 1.teamNum : "+teamNum);
						Bukkit.broadcastMessage("[DB] 1.teamCnt : "+teamCnt);
						for(Player t : randomList){
							if(teamCnt++ >= amt){
								teamCnt = 1;
								if(++teamNum >= teamList.size()){
									p.sendMessage(MS+"���� �ִ밳���� 6���Դϴ�. �� �̻��� �÷��̾�� �������� �ʽ��ϴ�.");
									break;
								}
							}
							Bukkit.dispatchCommand(getServer().getConsoleSender(), "scoreboard teams join "+teamList.get(teamNum)+" "+t.getName());
							t.sendMessage(MS+teamList.get(teamNum)+"���� �����Ǽ̽��ϴ�.");
							Bukkit.broadcastMessage("[DB] 2.teamNum : "+teamNum);
							Bukkit.broadcastMessage("[DB] 2.teamCnt : "+teamCnt);
						}
						posTimer();
						p.sendMessage(MS+"��� �÷��̾��� ���� �����Ǿ����ϴ�.\n��c�÷��̾��� ��ǥ�� 5�и��� ��µ˴ϴ�.");
					}catch(Exception e){
						p.sendMessage(MS+"���� �߻� : "+e.getCause()+" \n���� : "+e.getMessage());
					}
				}
			} else if(str.equalsIgnoreCase("gamestop")){
				Bukkit.getScheduler().cancelTask(posTimer);
				for(Player t : Bukkit.getOnlinePlayers()){
					Bukkit.dispatchCommand(getServer().getConsoleSender(), "scoreboard teams leave "+t.getName());
				}
				registerTeam();
				p.sendMessage(MS+"��� ���� �ʱ�ȭ�ϰ� ��ǥ ��� Ÿ�̸Ӹ� ������ϴ�.");
			}
		}
		return false;
	}
	
	private void posTimer(){
		Bukkit.getScheduler().cancelTask(posTimer);
		posTimer = Bukkit.getScheduler().scheduleSyncRepeatingTask(this, new Runnable(){
			public void run(){
				Bukkit.broadcastMessage(MS+"�÷��̾� ��ǥ");
				for(Player p : Bukkit.getOnlinePlayers()){
					Location l = p.getLocation();
					Bukkit.broadcastMessage("��6"+p.getName()+"��7:��c "+l.getBlockX()+", "+l.getBlockY()+", "+l.getBlockZ());
				}
			}
		}, 6000l, 6000l);
	}
}
