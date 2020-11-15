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
	public static String MS = "§f[§a BJ뱀사 §f] ";
	public static List<String> plist = new ArrayList<String>();
	public static String catcher = null;
	private static int schnum = 0;
	private static int schtime = 0;
	
	public void onEnable(){
		getServer().getPluginManager().registerEvents(this, this);
		getLogger().info("블랙애플 플러그인이 로드 되었습니다.");
	}
	
	public void onDisable(){
		getLogger().info("블랙애플 플러그인이 언로드 되었습니다.");
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
							p.sendMessage(MS+"/ba out <닉네임>");
							return true;
						} else {
							for(String tname : plist){
								if(tname.equalsIgnoreCase(args[1])){
									gameQuit(tname);
									p.sendMessage(MS+"탈락 처리 완료");
									checkWhoWin();
									return true;
								}
							}
							p.sendMessage(MS+args[1]+"이라는 플레이어는 게임에 참여중이지 않습니다.");
						}
					}else if(args[0].equalsIgnoreCase("retimer")){
						p.sendMessage(MS+"강제로 술래를 다시정합니다.");
						setCatcherTimer();
					}else if(args[0].equalsIgnoreCase("list")){
						p.sendMessage(MS+"참여중인 플레이어 : ");
						for(String name : plist){
							p.sendMessage(name+(catcher.equalsIgnoreCase(name) ? " - 살인마" : ""));
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
		p.sendMessage(MS+"/ba start - 시작");
		p.sendMessage(MS+"/ba list - 참여자 확인");
		p.sendMessage(MS+"/ba out <닉네임> - 해당 플레이어 탈락");
		p.sendMessage(MS+"/ba retimer - 다시시작");
	}
	public void setCatcher(){
		catcher = plist.get(getRandom(0, plist.size()-1));
		Bukkit.broadcastMessage(MS+"살인마가 정해졌습니다.");
		Bukkit.broadcastMessage(MS+"남은 시간은 레벨을 확인하세요!");
		sendMessagesToCatcher(MS+"살인마가 되셨습니다. 20분(1200초)내에 다른 플레이어를 죽이세요!");
	}
	
	public void sendMessagesToCatcher(String str){
		for(Player p : Bukkit.getOnlinePlayers()){
			if(catcher.equalsIgnoreCase(p.getName())){
				p.sendMessage(str);
			}
			return;
		}
		getLogger().info(MS+"[버그] 살인마가 서버에 접속중이지 않습니다.");
	}
	
	public void gameStart(Player p){
		plist.clear();
		Bukkit.broadcastMessage(MS+"블랙애플 게임을 시작합니다.");
		for(Player t : Bukkit.getOnlinePlayers()){
			plist.add(t.getName());
		}
		setCatcherTimer();
	}
	
	public void setCatcherTimer(){
		Bukkit.getScheduler().cancelTask(schnum);
		schtime = 60;
		Bukkit.broadcastMessage(MS+"60초 후 살인마가 정해집니다.");
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
		p.sendMessage(MS+"탈락되셨습니다.");
	}
	
	public void checkWhoWin(){
		if(plist.size() == 1){
			Player winPlayer = getServer().getPlayer(plist.get(0));
			Bukkit.broadcastMessage(MS+"최후의 1인자 "+(catcher.equalsIgnoreCase(winPlayer.getName()) ? "살인마 " : "시민 ")+winPlayer.getName()+" 님이 승리하셨습니다.");
		}
	}
	
	public void catcherOut(){
		Bukkit.broadcastMessage(MS+"제한시간이 다 되어 살인마 "+catcher+" 님이 탈락하였습니다!");
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
			Bukkit.broadcastMessage(MS+"살인마 "+catcher+" 님이 사망하였습니다!");
			setCatcherTimer();
		}else if(catcher.equals(k.getName())){
			Bukkit.broadcastMessage(MS+"시민 "+p.getName()+" 님이 살인마에게 살해 당하였습니다!");
			Bukkit.getScheduler().cancelTask(schnum);
			setCatcherTimer();
		}
	}
}
