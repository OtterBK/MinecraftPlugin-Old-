package me.Bokum.BlackApple;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.plugin.java.JavaPlugin;

public class Main extends JavaPlugin implements Listener
{
	public static String MS = "��f[��a BJ��� ��f] ";
	public static List<String> plist = new ArrayList<String>();
	public static String catcher = null;
	private static int schnum = 0;
	private static int schtime = 0;
	
	public void onEnable(){
		getServer().getPluginManager().registerEvents(this, this);
		getLogger().info("������ �÷������� �ε� �Ǿ����ϴ�.");
	}
	
	public void onDisable(){
		getLogger().info("������ �÷������� ��ε� �Ǿ����ϴ�.");
	}
	
	public boolean onCommand(CommandSender talker, Command command, String str, String[] args){
		if(talker instanceof Player){
			Player p = (Player) talker;
			if(str.equalsIgnoreCase("ba")){
				if(args.length <= 0){
					helpMessages(p);
					return true;
				} else{
					if(args[0].equalsIgnoreCase("start")){
						gameStart(p);
						return true;
					}else if(args[0].equalsIgnoreCase("out")){
						if(args.length <= 1){
							p.sendMessage(MS+"/ba out <�г���>");
							return true;
						} else {
							for(String tname : plist){
								if(tname.equalsIgnoreCase(args[1])){
									gameQuit(tname);
									p.sendMessage(MS+"Ż�� ó�� �Ϸ�");
									checkWhoWin();
									return true;
								}
							}
							p.sendMessage(MS+args[1]+"�̶�� �÷��̾�� ���ӿ� ���������� �ʽ��ϴ�.");
						}
					}else if(args[0].equalsIgnoreCase("retimer")){
						p.sendMessage(MS+"������ ������ �ٽ����մϴ�.");
						setCatcherTimer();
					}else if(args[0].equalsIgnoreCase("list")){
						p.sendMessage(MS+"�������� �÷��̾� : ");
						for(String name : plist){
							p.sendMessage(name+(catcher.equalsIgnoreCase(name) ? " - ���θ�" : ""));
						}
					}
				}
			}
		}
		return false;
	}
	
	public static int getRandom(int min, int max){
		  return (int)(Math.random() * (max-min+1)+min);
	}
	
	public void helpMessages(Player p){
		p.sendMessage(MS+"/ba start - ����");
		p.sendMessage(MS+"/ba list - ������ Ȯ��");
		p.sendMessage(MS+"/ba out <�г���> - �ش� �÷��̾� Ż��");
		p.sendMessage(MS+"/ba retimer - �ٽý���");
	}
	public void setCatcher(){
		catcher = plist.get(getRandom(0, plist.size()-1));
		Bukkit.broadcastMessage(MS+"���θ��� ���������ϴ�.");
		Bukkit.broadcastMessage(MS+"���� �ð��� ������ Ȯ���ϼ���!");
		sendMessagesToCatcher(MS+"���θ��� �Ǽ̽��ϴ�. 20��(1200��)���� �ٸ� �÷��̾ ���̼���!");
	}
	
	public void sendMessagesToCatcher(String str){
		for(Player p : Bukkit.getOnlinePlayers()){
			if(catcher.equalsIgnoreCase(p.getName())){
				p.sendMessage(str);
			}
			return;
		}
		getLogger().info(MS+"[����] ���θ��� ������ ���������� �ʽ��ϴ�.");
	}
	
	public void gameStart(Player p){
		plist.clear();
		Bukkit.broadcastMessage(MS+"������ ������ �����մϴ�.");
		for(Player t : Bukkit.getOnlinePlayers()){
			plist.add(t.getName());
		}
		setCatcherTimer();
	}
	
	public void setCatcherTimer(){
		Bukkit.getScheduler().cancelTask(schnum);
		schtime = 60;
		Bukkit.broadcastMessage(MS+"60�� �� ���θ��� �������ϴ�.");
		schnum = Bukkit.getScheduler().scheduleSyncRepeatingTask(this, new Runnable(){
			public void run(){
				if(schtime-- > 0){
					for(Player p2 : Bukkit.getOnlinePlayers()){
						if(plist.contains(p2.getName())) p2.setLevel(schtime);
					}
				} else {
					Bukkit.getScheduler().cancelTask(schnum);
					setCatcher();
					Timer();
				}
			}
		}
		, 0L, 20L);
	}
	
	public void Timer(){
		Bukkit.getScheduler().cancelTask(schnum);
		schtime = 1200;
		schnum = Bukkit.getScheduler().scheduleSyncRepeatingTask(this, new Runnable(){
			public void run(){
				if(schtime-- > 0){
					for(Player p2 : Bukkit.getOnlinePlayers()){
						if(plist.contains(p2.getName())) p2.setLevel(schtime);
					}
				}else{
					Bukkit.getScheduler().cancelTask(schnum);
					catcherOut();
				}
			}
		}
		, 0L, 20L);
	}
	
	public void gameQuit(String pname){
		plist.remove(pname);
		Player p = getServer().getPlayer(pname);
		p.sendMessage(MS+"Ż���Ǽ̽��ϴ�.");
	}
	
	public void checkWhoWin(){
		if(plist.size() == 1){
			Player winPlayer = getServer().getPlayer(plist.get(0));
			Bukkit.broadcastMessage(MS+"������ 1���� "+(catcher.equalsIgnoreCase(winPlayer.getName()) ? "���θ� " : "�ù� ")+winPlayer.getName()+" ���� �¸��ϼ̽��ϴ�.");
		}
	}
	
	public void catcherOut(){
		Bukkit.broadcastMessage(MS+"���ѽð��� �� �Ǿ� ���θ� "+catcher+" ���� Ż���Ͽ����ϴ�!");
		gameQuit(catcher);
		for(Player p : Bukkit.getOnlinePlayers()){
			if(catcher.equalsIgnoreCase(p.getName())) {
				p.setHealth(1); 
				p.damage(100);
			}
		}
		checkWhoWin();
		setCatcherTimer();
	}
	
	@EventHandler
	public void onPlayerDeath(PlayerDeathEvent e){
		Player p = e.getEntity();
		if(!(p.getKiller() instanceof Player)) return;
		Player k = (Player) p.getKiller();
		if(catcher.equalsIgnoreCase(p.getName())){
			Bukkit.getScheduler().cancelTask(schnum);
			Bukkit.broadcastMessage(MS+"���θ� "+catcher+" ���� ����Ͽ����ϴ�!");
			setCatcherTimer();
		}else if(catcher.equals(k.getName())){
			Bukkit.broadcastMessage(MS+"�ù� "+p.getName()+" ���� ���θ����� ���� ���Ͽ����ϴ�!");
			Bukkit.getScheduler().cancelTask(schnum);
			setCatcherTimer();
		}
	}
}
