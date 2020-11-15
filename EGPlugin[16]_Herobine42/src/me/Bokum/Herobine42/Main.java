package me.Bokum.Herobine42;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Sound;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.event.Listener;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.RegisteredServiceProvider;
import org.bukkit.plugin.java.JavaPlugin;

import net.milkbowl.vault.economy.Economy;

public class Main extends JavaPlugin implements Listener{
	
	public static Location start;
	public static Location joinPos;
	public static Location nPos[] = new Location[8];
	public static Main instance;
	public static List<Player> plist = new ArrayList<Player>();
	public static List<Player> nlist = new ArrayList<Player>();
	public static HashMap<String, String> jobList = new HashMap<String, String>();
	public static int tasknum[] = new int[5];
	public static int tasktime[] = new int[5];
	public static int timernum = 0;
	public static long timertime = 0;
	public static ItemStack helpitem;
	public static Inventory gamehelper;
	public static Economy econ = null;
	public static boolean check_Start = false;
	public static boolean check_LobbyStart = false;
	public static boolean game_end = false;
	public static int forceStopTimer = 0;
	
	public static String MS = "§f[ §aEG §f] ";
 	
	public void onEnable(){
		getServer().getPluginManager().registerEvents(this, this);
		getLogger().info("히로빈42 플러그인이 로드 되었습니다.");
	}
	
    private boolean setupEconomy() {
        if (getServer().getPluginManager().getPlugin("Vault") == null) {
            return false;
        }
        RegisteredServiceProvider<Economy> rsp = getServer().getServicesManager().getRegistration(Economy.class);
        if (rsp == null) {
            return false;
        }
        econ = rsp.getProvider();
        return econ != null;
    }
	
	public void onDisable(){
		getLogger().info("히로빈42 플러그인이 언로드 되었습니다.");
	}
	
	public void loadConfig() {
		try {
			joinPos = new Location(Bukkit.getWorld(getConfig().getString("Join_world")), getConfig().getInt("Join_x"), getConfig().getInt("Join_y"), getConfig().getInt("Join_z"));
			start = new Location(Bukkit.getWorld(getConfig().getString("Start_world")), getConfig().getInt("Start_x"), getConfig().getInt("Start_y"), getConfig().getInt("Start_z"));
		} catch (IllegalArgumentException e) {
			getLogger().info("참여 지점 또는 로비가 설정되어 있지 않습니다");
		}
		for(int i = 0; i < 8; i++){
			try {
				nPos[i] = new Location(Bukkit.getWorld(getConfig().getString("n"+i+"_world")), getConfig().getInt("n"+i+"_x"), getConfig().getInt("n"+i+"_y"), getConfig().getInt("n"+i+"_z"));
			} catch (Exception e){
				getLogger().info(MS+i+"번째 밤 대기 지점이 설정되어있지 않습니다.");
			}
		}
	}
	
	public static int getCursch() {
		for(int i = 0 ; i < tasknum.length; i++) {
			if(tasknum[i] == -5) {
				return i;
			}
		}
		return 0;
	}
	
	
	public static void cancelTask(int cur) {
		Bukkit.getScheduler().cancelTask(tasknum[cur]);
		tasknum[cur] = -5;
		tasktime[cur] = -5;
	}
	
	public static int getRandom(int min, int max){
		  return (int)(Math.random() * (max-min+1)+min);
	}
	
	public void helpMessages(Player p){
		p.sendMessage(MS+"/hrb set join");
		p.sendMessage(MS+"/hrb set n 1~8");
		p.sendMessage(MS+"/hrb set start");
		p.sendMessage(MS+"/hrb join");
		p.sendMessage(MS+"/hrb quit");
		p.sendMessage(MS+"/hrb stop");
	}
	
	public static void Sendmessage(String str) {
		for(Player p : plist) {
			p.sendMessage(str);
		}
	}
	
	public boolean onCommand(CommandSender talker, Command command, String str, String args[]){
		if(str.equalsIgnoreCase("hrb")){
			if(talker instanceof Player){
				Player p = (Player) talker;
				if(p.isOp()){
					if(args.length <= 0){
						helpMessages(p);
						return true;
					}	else {
							if(args[0].equalsIgnoreCase("set")) {
								setLoc(p, args);
								return true;
							}
							if(args[0].equalsIgnoreCase("join")) {
								gameJoin(p);
								return true;
							}
							if(args[0].equalsIgnoreCase("quit")) {
								gameQuit(p);
								return true;
							}
							if(args[0].equalsIgnoreCase("stop")) {
								forceStop();
								return true;
							}
					}
				}
			}
		}
		
		return false;
	}
	
	public void setLoc(Player p, String[] args) {
		if(args.length <= 1) {
			p.sendMessage(MS+"/hrb set start");
			p.sendMessage(MS+"/hrb set join");
			p.sendMessage(MS+"/hrb set n 1~8");
			return;
		} else if(args.length <= 2) {
			if(args[1].equalsIgnoreCase("join")) {
			    getConfig().set("Join_world", p.getLocation().getWorld().getName());
			    getConfig().set("Join_x", Integer.valueOf(p.getLocation().getBlockX()));
			    getConfig().set("Join_y", Integer.valueOf(p.getLocation().getBlockY() + 1));
			    getConfig().set("Join_z", Integer.valueOf(p.getLocation().getBlockZ()));
			    saveConfig();
			    loadConfig();
				p.sendMessage(MS+"대기실 설정 완료");
			} else if(args[1].equalsIgnoreCase("start")) {
			    getConfig().set("Start_world", p.getLocation().getWorld().getName());
			    getConfig().set("Start_x", Integer.valueOf(p.getLocation().getBlockX()));
			    getConfig().set("Start_y", Integer.valueOf(p.getLocation().getBlockY() + 1));
			    getConfig().set("Start_z", Integer.valueOf(p.getLocation().getBlockZ()));
			    saveConfig();
			    loadConfig();
				p.sendMessage(MS+"낮 지점 설정 완료");
			}
			return;
		} else if(args.length <= 3 && args[2].equalsIgnoreCase("n")){
			if(args.length >= 4){
				try{
					int num = Integer.valueOf(args[3]);
					getConfig().set("n"+num+"_world", p.getLocation().getWorld().getName());
					getConfig().set("n"+num+"_x", Integer.valueOf(p.getLocation().getBlockX()));
					getConfig().set("n"+num+"_y", Integer.valueOf(p.getLocation().getBlockY() + 1));
					getConfig().set("n"+num+"_z", Integer.valueOf(p.getLocation().getBlockZ()));
					saveConfig();
					loadConfig();
					p.sendMessage(MS+num+"번째 밤 지점 설정 완료");
				} catch(Exception e){
					p.sendMessage(MS+"숫자만 입력해주세요.");
				}
			} else {
				p.sendMessage(MS+"/hrb set n 1~8");
			}
		}
	}

	public static void gameJoin(Player p) {
		if(plist.contains(p)) {
			p.sendMessage(MS+"이미 게임에 참여중이십니다.");
			return;
		}
		if(plist.size() >= 6) {
			p.sendMessage(MS+"이미 최대인원(6)입니다.");
			return;
		}
		if(check_Start) {
			p.sendMessage(MS+"이미 게임이 진행중입니다. 남은인원 : "+plist.size());
			return;
		} else {
			plist.add(p);
			p.teleport(joinPos);
			p.getInventory().clear();
			p.getInventory().setHelmet(null);
			p.getInventory().setChestplate(null);
			p.getInventory().setLeggings(null);
			p.getInventory().setBoots(null);
			p.getInventory().setItem(8, helpitem);
			me.Bokum.EGM.Main.gamelist.put(p.getName(), "히로빈42");
			Sendmessage(MS+p.getName()+" 님이 히로빈42에 참여하셨습니다. 인원 (§e "+plist.size()+"§7 / §c6 §f)");
			if(!check_LobbyStart && plist.size() >= 6) {
				startGame();
			}
			for(Player t : plist){
				t.playSound(t.getLocation(), Sound.NOTE_PLING, 2.0f, 1.0f);
			}
		}
	}
	
	public static void startGame() {
		check_LobbyStart = true;
		final int cur = getCursch();
		tasktime[cur] = 5;
		Bukkit.broadcastMessage(MS+"§b§l히로빈42§f가 곧 시작됩니다.");
		tasknum[cur] = Bukkit.getScheduler().scheduleSyncRepeatingTask(instance, new Runnable() {
			public void run() {
				if(tasktime[cur] > 0) {
					Sendmessage(MS+"게임이 "+tasktime[cur]--*10+" 초 후 시작됩니다.");
				} else {
					cancelTask(cur);
					check_Start = true;
					for(Player p : plist){
						p.getInventory().clear();
						p.getInventory().setHelmet(null);
						p.getInventory().setChestplate(null);
						p.getInventory().setLeggings(null);
						p.getInventory().setBoots(null);
						p.playSound(p.getLocation(), Sound.ANVIL_LAND, 2.0f, 1.0f);
						p.sendMessage(MS+"게임이 시작 됐습니다.");
					}
					setRole();
				}
			}
		}, 200L, 200L);
	}
	
	public static void setRole(){
		nlist.clear();
		List<Player> tmplist = new ArrayList<Player>();
		for(int i = 0; i < plist.size(); i++){
			nlist.add(plist.get(getRandom(0, plist.size())));
			tmplist.add(plist.get(getRandom(0, plist.size())));
		}
		List<String> tmpBaseJob = new ArrayList<String>();
		tmpBaseJob.add("마피아"); //마피아
		tmpBaseJob.add("경찰"); //경찰
		tmpBaseJob.add("의사");  //의사
		List<String> tmpSpecialJob = new ArrayList<String>();
		tmpSpecialJob.add("스파이"); //스파이
		tmpSpecialJob.add("영매");
		tmpSpecialJob.add("군인");
		tmpSpecialJob.add("정치인");
		tmpSpecialJob.add("연인");
		tmpSpecialJob.add("기자");
		tmpSpecialJob.add("짐승인간");
		tmpSpecialJob.add("건달");
		tmpSpecialJob.add("사립탐정");
		tmpSpecialJob.add("도굴꾼");
		tmpSpecialJob.add("테러리스트");
		tmpSpecialJob.add("마담");
	}
}

