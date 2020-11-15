package me.Bokum.Couples;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.java.JavaPlugin;

public class Main extends JavaPlugin implements Listener{
	public static List<String> slist = new ArrayList<String>();
	public static HashMap<String, String> clist = new HashMap<String, String>();
	public static String MS = "§f[ §aBJ뱀사 §f] ";
	
	public void onEnable(){
		getServer().getPluginManager().registerEvents(this, this);
		getLogger().info("커플 플러그인 로드 완료");
	}
	
	public void onDisable(){
		getLogger().info("커플 플러그인 언로드 완료");
	}
	
	public boolean onCommand(CommandSender talker, Command command, String str, String[] args){
		if(talker instanceof Player){
			Player p = (Player) talker;
			if(str.equalsIgnoreCase("couple")){
				if(args.length >= 2){
					Player p1 = null;
					Player p2 = null;
					for(Player t : Bukkit.getOnlinePlayers()){
						if(p1.getName().equalsIgnoreCase(t.getName())) p1 = t;
						if(p2.getName().equalsIgnoreCase(t.getName())) p2 = t;
					}
					if(p1 == null || p2 == null){
						p.sendMessage(MS+"존재하지 않는 플레이어입니다.");
						return true;
					}
					if(clist.containsKey(p1.getName())) {
						p.sendMessage(MS+p1.getName()+"님은 이미 커플입니다.");
						return true;
					}
					if(clist.containsKey(p2.getName())) {
						p.sendMessage(MS+p2.getName()+"님은 이미 커플입니다.");
						return true;
					}
					if(slist.contains(p1.getName())) slist.remove(p1.getName());
					if(slist.contains(p2.getName())) slist.remove(p2.getName());
					clist.put(p1.getName(), p2.getName());
					clist.put(p2.getName(), p1.getName());
					p.sendMessage(MS+"설정완료");
				}else{
					p.sendMessage(MS+"/커플 <닉네임> <닉네임>");
					return true;
				}
			}else if(str.equalsIgnoreCase("solo")){
				if(clist.containsKey(p.getName())){
					clist.remove(clist.get(p.getName()));
					clist.remove(p.getName());
				}
				p.sendMessage(MS+"설정완료");
			}else if(str.equalsIgnoreCase("list")){
				for(String s : clist.keySet()){
					p.sendMessage(MS+s+" - "+clist.get(s));
				}
			}else if(str.equalsIgnoreCase("init")){
				clist.clear();
				slist.clear();
			}
		}
		return false;
	}
	
	public void onDeath(PlayerDeathEvent e){
		Player p = e.getEntity();
		Player k = null;
		if(p.getKiller() != null && p.getKiller() instanceof Player){
			p.getKiller().getInventory().addItem(new ItemStack(4167, 1 , (byte) 6));
			k = p.getKiller();
		}
		if(clist.containsKey(p.getName()) && k != null){
			Bukkit.broadcastMessage(MS+"커플 "+p.getName()+"님과"+clist.get(p.getName())+"님이 깨졌습니다.");
			clist.remove(clist.get(p.getName()));
			clist.remove(p.getName());
		}
	}
}
