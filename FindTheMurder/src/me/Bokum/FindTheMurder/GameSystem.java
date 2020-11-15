package me.Bokum.FindTheMurder;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.event.Listener;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.plugin.java.JavaPlugin;

import me.confuser.barapi.BarAPI;

public class GameSystem extends JavaPlugin implements Listener
{
	public static int schtime = 0;
	public static int schtmp = 0;
	public static GameSystem instance;
	public static boolean isday;
	public static int daycnt = 0;
	public static String doctortarget = null;
	public static String detectivetarget = null;
	public static boolean uc_police = false;
	public static boolean uc_soldier = true;
	public static boolean uc_spy = false;
	public static boolean uc_reporter = false;
	public static int uc_guardiancnt = 0;
	public static boolean checkstart = false;
	public static String uc_guardian1 = null;
	public static String uc_guardian2 = null;
	public static boolean lobbystart = false;
	public static String astarget = null;
	public static Player assasin;
	public static List<Player> mlist = new ArrayList(); 
	public static List<Player> tmplist = new ArrayList();

	
	
	public static void Joingame(Player player)
	{
		if(Main.list.contains(player))
		{
			player.sendMessage(Main.MS+"이미 게임에 참가중 입니다.");
			return;
		}
		if(checkstart)
		{
			player.sendMessage(Main.MS+"이미 게임이 시작되었습니다.");
			return;
		}
		if(Main.list.size() >= 12)
		{
			player.sendMessage(Main.MS+"이미 최대인원인 12명의 플레이어가 참가중입니다.");
			return;
		}
		player.teleport(Main.joinpos);
		Main.list.add(player);
		Main.SendMessage(Main.MS+ChatColor.AQUA+player.getName()+ChatColor.GRAY+" 님이 참여했습니다. "+ChatColor.RESET+"[ "+ChatColor.GREEN+Main.list.size()+" / 5 "+ChatColor.RESET+"]");
		if(Main.list.size() >= 5 && !lobbystart)
		{
			lobbystart = true;
			Main.Startgame();
		}		
	}
	
	public static void Startgame(Player player)
	{
		Player oplayer[] = Bukkit.getOnlinePlayers();
		if(oplayer.length > 12)
		{
			player.sendMessage(Main.MS+"게임 최대인원인 12명을 초과했습니다.");
			return;
		}
		else if(oplayer.length < 5)
		{
			player.sendMessage(Main.MS+"게임에 필요한 최소인원인 5명 미만입니다.");
			return;
		}
		else
		{
			Bukkit.getServer().broadcastMessage(Main.MS+player.getName()+" 님이 범죄자를 찾아라 게임을 시작 시켰습니다.");
			Main.list.clear();
			for(int i = 0; i < oplayer.length; i++)
			{
				Main.list.add(oplayer[i]);
			}
			lobbystart = true;
			Main.StartTP();
		}
	}
	
	public static int Getrandom(int num)
	{
		return ((int)(Math.random()*num)); //0~num-1까지중 랜덤으로 값을 반환
	}
	
	public static void ForceEnd()
	{
		Bukkit.broadcastMessage(Main.MS+"범죄자를 찾아라 게임이 강제 종료 되었습니다.");
		schtime = 0;
		schtmp = 0;
		isday = false;
		daycnt = 0;
		doctortarget = null;
		detectivetarget = null;
		uc_police = false;
		uc_soldier = true;
		uc_spy = false;
		uc_reporter = false;
		uc_guardiancnt = 0;
		checkstart = false;
		uc_guardian1 = null;
		uc_guardian2 = null;
		lobbystart = false;
		for(int i = 0; i < Main.list.size(); i++)
		{
			Main.list.get(i).teleport(Main.Lobby);
			BarAPI.removeBar(Main.list.get(i));
		}
		mlist.clear();
		Main.list.clear();
		Main.plist.clear();
		Main.jlist.clear();
		Main.glist.clear();
		astarget = null;
		tmplist.clear();
		assasin = null;
		Main.Cancelalltasks();
	}
	
	public static void End()
	{
		Bukkit.getScheduler().cancelTask(schtmp);
		schtime = 0;
		schtmp = 0;
		isday = false;
		daycnt = 0;
		doctortarget = null;
		detectivetarget = null;
		uc_police = false;
		uc_soldier = true;
		uc_spy = false;
		uc_reporter = false;
		uc_guardiancnt = 0;
		checkstart = false;
		uc_guardian1 = null;
		uc_guardian2 = null;
		lobbystart = false;
		mlist.clear();
		Main.list.clear();
		Main.plist.clear();
		Main.jlist.clear();
		Main.glist.clear();
		tmplist.clear();
		astarget = null;
		assasin = null;
	}
	
	public static void Setjob()
	{
		tmplist.clear();
		tmplist.addAll(Main.list);
		if(tmplist.size() < 5)
		{
			Main.SendMessage(Main.MS+"게임 진행에 필요한 최소인원이 충족하지 않는 버그가 발생했습니다.");
			ForceEnd();
		}
		for(int i = 0; i < Main.list.size(); i++)
		{
			Player setplayer = Main.list.get(i);
			Main.plist.put(setplayer.getName(), setplayer);
		}
			int random = Getrandom(tmplist.size());
			SetMurder(tmplist.get(random));
			random = Getrandom(tmplist.size());
			SetDoctor(tmplist.get(Getrandom(tmplist.size())));
			random = Getrandom(tmplist.size());
			SetPolice(tmplist.get(Getrandom(tmplist.size())));
			random = Getrandom(tmplist.size());
			SetSoldier(tmplist.get(Getrandom(tmplist.size())));
			random = Getrandom(tmplist.size());
			SetSpy(tmplist.get(Getrandom(tmplist.size())));
			if(tmplist.size() != 0)
			{
			random = Getrandom(tmplist.size());
			SetNice_Civilian(tmplist.get(Getrandom(tmplist.size())));
			}
			if(tmplist.size() != 0)
			{
				random = Getrandom(tmplist.size());
				SetDetective(tmplist.get(Getrandom(tmplist.size())));
			}
			if(tmplist.size() != 0)
			{
				random = Getrandom(tmplist.size());
				SetReporter(tmplist.get(Getrandom(tmplist.size())));
			}
			if(tmplist.size() != 0)
			{
				random = Getrandom(tmplist.size());
				SetMagician(tmplist.get(Getrandom(tmplist.size())));
				
			}
			if(tmplist.size() != 0)
			{
				random = Getrandom(tmplist.size());
				SetGuadian(tmplist.get(Getrandom(tmplist.size())));
				
			}
			if(tmplist.size() != 0)
			{
				random = Getrandom(tmplist.size());
				SetCivilian(tmplist.get(Getrandom(tmplist.size())));
				
			}
			if(tmplist.size() != 0)
			{
				random = Getrandom(tmplist.size());
				SetAssasin(tmplist.get(Getrandom(tmplist.size())));
				
			}
			Main.SendMessage(Main.MS+"직업이 정해졌습니다.");
			Main.SendMessage(Main.MS+"직업에 대한 설명을 보시려면 "+
			ChatColor.AQUA+ "/ftm help 를 입력해주세요.");
			Main.SendMessage(Main.MS+"직업리스트를 보시려면 /ftm list 를 입력해주세요.");
			Mixlist();
	}
	
	public static void Mixlist()
	{
		if(Main.glist.size() == 0)
		{
			Bukkit.getLogger().info("직업리스트가 비어있는 상태에서 Mixgist 메소드를 호출할 수 없습니다.");
		}
		else
		{
			List<String> keylist = new ArrayList();
			List<String> valuelist = new ArrayList();
			keylist.addAll(Main.glist.keySet());
			valuelist.addAll(Main.glist.values());
			Main.glist.clear();
			while(keylist.size() > 0)
			{
				int ri = Getrandom(keylist.size());
				Main.glist.put(keylist.get(ri), valuelist.get(ri));
				keylist.remove(ri);
				valuelist.remove(ri);
			}
		}
	}
	
	public static void dayinit()
	{
		Main.vplist.clear();
		Main.vlist.clear();
		detectivetarget = null;
		uc_police = false;
	}
	
	public static void nightinit()
	{
		doctortarget = null;
		uc_spy = false;
		uc_guardiancnt = 0;
		uc_guardian1 = null;
		uc_guardian2 = null;
	}
	
	public static void Setvotelist()
	{
		if(!checkstart)
		{
			return;
		}
		String topname = null;
		int top_int = 0;
		boolean bothtopcheck = false;
		Set<String> keys = Main.vlist.keySet();
		Iterator<String> it = keys.iterator();
		while(it.hasNext())
		{
			String name = it.next();
			if(top_int < Main.vlist.get(name))
			{
				top_int = Main.vlist.get(name);
				topname = name;
				bothtopcheck = false;
			}
			else if(top_int == Main.vlist.get(name))
			{
				bothtopcheck = true;
			}
		}
		if(bothtopcheck || topname == null)
		{
			Main.SendMessage(Main.MS+"최다투표를 받은 플레이어가 존재하지 않으므로 투표가 취소 됐습니다.");
		}
		else
		{
			Main.SendMessage(Main.MS+topname+" 님이 최다투표를 받으셨습니다. ( "+top_int+"표 )");
			if(Checkucguardian(topname))
			{
				Main.SendMessage(Main.MS+"하지만 보디가드의 능력으로 인하여 이 플레이어의 직업을 밝혀낼 수 없었습니다!");
			}
			else if(!Main.plist.containsKey(topname))
			{
				Main.SendMessage(Main.MS+"최다 투표를 받은 플레이어가 현재는 게임에 참가중이 아니므로 취소 됐습니다.");
			}
			else
			{
				Main.SendMessage(Main.MS+topname+" 님의 직업은 "+Getjob(topname)+" 입니다!");
				Main.glist.put(topname, Getjob(topname));
			}
			Main.SendMessage(Main.MS+"/ftm list 를 입력시 직업리스트를 보실 수 있습니다.");
		}
	}
	
	public static boolean Checkucguardian(String name)
	{
		if(uc_guardian1 != null)
		{
			if(uc_guardian1.equalsIgnoreCase(name))
			{
				return true;
			}
		}
		if(uc_guardian2 != null)
		{
			if(uc_guardian2.equalsIgnoreCase(name))
			{
				return true;
			}
		}
		return false;
	}
	
	public static boolean Checktarget(Player player,String target)
	{
		if(target == null)
		{
			player.sendMessage(Main.MS+"타겟을 지정해주세요.");
			return true;
		}
		else if(!Main.plist.containsKey(target))
		{
			player.sendMessage(Main.MS+ChatColor.AQUA+target+ChatColor.GRAY+"님은 게임에 참여중이 아닙니다.");
			return true;
		}
		return false;
	}
	
	public static boolean Checkday(Player player)
	{
		if(daycnt == 1)
		{
			player.sendMessage(Main.MS+"첫째 날에는 사용이 불가능한 능력입니다.");
			return true;
		}
		return false;
	}
	
	public static String Getjob(String target)
	{ 
		return Main.jlist.get(target);
	}
	
	public static void Use(Player player, String target)
	{
		switch(Getjob(player.getName()))
		{
			case "범죄자":
				UseMurder(player); return; 
			case "의사":
				UseDoctor(player, target); return; 
			case "경찰":
				UsePolice(player, target); return; 
			case "군인":
				UseSoldier(player); return; 
			case "스파이":
				UseSpy(player, target); return; 
			case "탐정":
				UseDetective(player, target); return; 
			case "모범시민":
				UseNice_Civilian(player); return; 
			case "기자":
				UseReporter(player, target); return; 
			case "마술사":
				UseMagician(player, target); return; 
			case "보디가드":
				UseGuadian(player, target); return; 
			case "시민":
				UseCivilian(player); return; 
			case "암살자":
				UseAssasin(player); return; 
		}
	}
	
	public static void UseMurder(Player player)
	{
		player.sendMessage(Main.MS+"범죄자는 사용할 능력이 없습니다. 밤을 이용하여 모든 플레이어를 죽이세요.");
	}
	
	public static void UseDoctor(Player player, String target)
	{
		if(doctortarget != null)
		{
			player.sendMessage(Main.MS+"이미 능력을 사용하였습니다. 다음날까지 기다려주세요.");
			return;
		}
		if(Checktarget(player, target))
		{
			return;
		}
		if(isday)
		{
			player.sendMessage(Main.MS+"의사의 능력은 밤에만 사용이 가능합니다.");
		}
		else if(target.equalsIgnoreCase(player.getName()))
		{
			player.sendMessage(Main.MS+"자신에게는 사용이 불가능합니다.");
		}
		else
		{
			doctortarget = target;
			player.sendMessage(Main.MS+target+" 님이 사망시 치료합니다.");
		}
	}
	
	public static void UsePolice(Player player, String target)
	{
		if(uc_police)
		{
			player.sendMessage(Main.MS+"이미 능력을 사용하였습니다. 다음날까지 기다려주세요.");
			return;
		}
		if(Checktarget(player, target))
		{
			return;
		}
		if(Checkday(player))
		{
			return;
		}
		if(isday)
		{
			uc_police = true;
			if(Getjob(target).equalsIgnoreCase("범죄자"))
			{
				player.sendMessage(Main.MS+target+" 님은 범죄자 입니다! 이 사실을 모두에게 알리세요!");
			}
			else
			{
				player.sendMessage(Main.MS+target+" 님은 범죄자가 아닙니다.");
			}
		}
		else
		{
			player.sendMessage(Main.MS+"경찰의 능력은 낮에만 사용이 가능합니다.");
		}
	}
	
	public static void UseSoldier(Player player)
	{
		player.sendMessage(Main.MS+"군인은 사용할 능력이 없습니다. 하지만 죽어도 1번 부활합니다.");
	}
	
	public static void UseSpy(Player player, String target)
	{
		if(uc_spy)
		{
			player.sendMessage(Main.MS+"이미 능력을 사용하였습니다. 다음날까지 기다려주세요.");
			return;
		}
		if(Checktarget(player, target))
		{
			return;
		}
		if(isday)
		{
			player.sendMessage(Main.MS+"스파이의 능력은 밤에만 사용이 가능합니다.");
		}
		else
		{
			uc_spy = true;
			if(Getjob(target).equalsIgnoreCase("범죄자"))
			{
				mlist.add(player);
				player.sendMessage(Main.MS+target+" 님은 범죄자 였습니다!");
				player.sendMessage(Main.MS+target+" 님과 접선에 성공 하였습니다!");
				Main.plist.get(target).sendMessage(ChatColor.RED+"스파이 "+ChatColor.AQUA+player.getName()+ChatColor.RED+" 님이 당신과 접선했습니다.");
				Givespyitem(player);
			}
			else
			{
				player.sendMessage(Main.MS+target+" 님은 범죄자가 아닙니다.");
				player.sendMessage(Main.MS+target+" 님의 직업은 "+Getjob(target)+" 입니다.");
				if(Getjob(target).equalsIgnoreCase("군인"))
				{
					Main.plist.get(target).sendMessage(Main.MS+"스파이 "+player.getName()+" 님이 당신을 조사하였습니다!");
				}
			}
		}
	}
	
	public static void UseNice_Civilian(Player player)
	{
		player.sendMessage(Main.MS+"모범시민은 투표시에 능력이 자동적으로 활성화가 됩니다.");
	}
	
	public static void UseDetective(Player player, String target)
	{
		if(isday)
		{
			if(Checktarget(player, target))
			{
				return;
			}
			if(detectivetarget != null)
			{
				player.sendMessage(Main.MS+"이미 능력을 사용하였습니다. 다음날까지 기다려주세요.");
				return;
			}
			detectivetarget = target;
			player.sendMessage(Main.MS+"이제 "+target+" 님이 밤에 무엇을 하시는지 볼 수 있습니다.");
		}
		else
		{
			if(detectivetarget == null)
			{
				player.sendMessage(Main.MS+"낮에 누구를 조사할 지 지정하지 않아 능력사용이 불가능합니다.");
				return;
			}
			player.sendMessage(ChatColor.AQUA+detectivetarget+" 님의 x좌표 : "+Main.plist.get(target).getLocation().getBlockX());
			player.sendMessage(ChatColor.AQUA+detectivetarget+" 님의 y좌표 : "+Main.plist.get(target).getLocation().getBlockY());
			player.sendMessage(ChatColor.AQUA+detectivetarget+" 님의 z좌표 : "+Main.plist.get(target).getLocation().getBlockZ());
			player.sendMessage(ChatColor.AQUA+detectivetarget+" 님의 체력 : "+Main.plist.get(target).getHealth());
			if(Main.plist.get(target).getItemInHand().hasItemMeta())
			{
				player.sendMessage(ChatColor.AQUA+detectivetarget+" 님이 들고 있는 무기 : "+Main.plist.get(target).getItemInHand().getItemMeta().getDisplayName());
			}
			else
			{
				player.sendMessage(ChatColor.AQUA+detectivetarget+" 님이 들고 있는 무기 : 인식할 수 없습니다.");
			}
		}
	}
	
	public static void UseReporter(Player player, String target)
	{
		if(Checktarget(player, target))
		{
			return;
		}
		if(uc_reporter)
		{
			player.sendMessage(Main.MS+"이미 능력을 사용하셨습니다. 기자의 능력은 1번만 사용이 가능합니다.");
			return;
		}
		if(isday)
		{
			player.sendMessage(Main.MS+"기자의 능력은 밤에만 사용이 가능합니다.");
		}
		else
		{
			uc_reporter = true;
			Main.SendMessage(Main.MS+"기자가 "+target+" 님을 조사했습니다.");
			Main.SendMessage(Main.MS+target+" 님의 직업은 "+Getjob(target)+"입니다!!!");
			Main.glist.put(target, Getjob(target));
		}
	}
	
	public static void UseMagician(Player player, String target)
	{
		if(Checktarget(player, target))
		{
			return;
		}
		if(isday)
		{
			player.sendMessage(Main.MS+"마술사의 능력은 낮에는 사용이 불가능합니다.");
		}
		else
		{
			if(target.equalsIgnoreCase(player.getName()))
			{
				player.sendMessage(Main.MS+"자신에게는 사용이 불가능합니다.");
				return;
			}
			player.sendMessage(Main.MS+target+" 님의 직업인 "+Getjob(target)+"을(를) 뺐었습니다.");
			Main.plist.get(target).sendMessage(Main.MS+"마술사가 당신의 직업을 빼었습니다.");
			switch(Getjob(target))
			{
				case "범죄자":
					Player tmpplayer = Main.plist.get(target);
					Removeplayerfl(Main.plist.get(target));
					tmpplayer.sendMessage(Main.MS+"당신은 범죄자였기 때문에 사망하게 됩니다.");
					tmpplayer.setHealth(0);
					SetMurder(player);
					return; 
				case "의사":
					SetDoctor(player); break; 
				case "경찰":
					SetPolice(player); break; 
				case "군인":
					SetSoldier(player); break; 
				case "스파이":
					SetSpy(player); return; 
				case "탐정":
					SetDetective(player); break; 
				case "모범시민":
					SetNice_Civilian(player); break; 
				case "기자":
					SetReporter(player); break; 
				case "마술사":
					SetMagician(player); break; 
				case "보디가드":
					SetGuadian(player); break; 
				case "시민":
					SetCivilian(player); break; 
				case "암살자":
					SetAssasin(player); return; 
				default:
					player.sendMessage(Main.MS+target+" 님은 할당되지 않은 직업입니다. - 버그 의심"); break;
			}
			Main.jlist.put(target, "시민");
		}
	}
	
	public static void UseGuadian(Player player, String target)
	{
		if(Checktarget(player, target))
		{
			return;
		}
		if(uc_guardiancnt >= 2)
		{
			player.sendMessage(Main.MS+"이미 능력을 2번 사용 하셨습니다. 다음날을 기다리세요");
			return;
		}
		if(isday)
		{
			player.sendMessage(Main.MS+"보디가드의 능력은 밤에만 사용이 가능합니다.");
		}
		else
		{
			if(uc_guardiancnt == 1)
			{
				uc_guardian1 = target;
			}
			if(uc_guardiancnt == 2)
			{
				uc_guardian2 = target;
			}
				player.sendMessage(Main.MS+target+" 님에게 능력을 사용하였습니다.");
				player.sendMessage(Main.MS+"이 플레이어는 다음날 낮에 투표로 인하여 직업이 발설되지 않습니다.");
			uc_guardiancnt++;
		}
	}
	
	public static void UseCivilian(Player player)
	{
		player.sendMessage(Main.MS+"시민은 아무런 능력이 없습니다.");
	}
	
	public static void UseAssasin(Player player)
	{
		player.sendMessage(Main.MS+"당신은 시민직업을 가진 플레이어를 찾아내어 사살해야 합니다.");
	}
	
	public static void SetMurder(Player player)
	{
		tmplist.remove(player);
		player.setHealth(player.getMaxHealth());;
		Main.jlist.put(player.getName(), "범죄자");
		Main.glist.put(player.getName(), "?");
		mlist.add(player);
		player.sendMessage(Main.MS+"당신의 직업은 "+ChatColor.GOLD+
				Main.jlist.get(player.getName())+" 입니다.");
		Givemurderitem(player);
	}
	
	public static void SetDoctor(Player player)
	{
		tmplist.remove(player);
		player.setHealth(player.getMaxHealth());
		Main.jlist.put(player.getName(), "의사");
		Main.glist.put(player.getName(), "?");
		player.sendMessage(Main.MS+"당신의 직업은 "+ChatColor.GOLD+
				Main.jlist.get(player.getName())+" 입니다.");
	}
	
	public static void SetPolice(Player player)
	{
		tmplist.remove(player);
		player.setHealth(player.getMaxHealth());
		Main.jlist.put(player.getName(), "경찰");
		Main.glist.put(player.getName(), "?");
		player.sendMessage(Main.MS+"당신의 직업은 "+ChatColor.GOLD+
				Main.jlist.get(player.getName())+" 입니다.");
		Givepoliceitem(player);
	}
	
	public static void SetSoldier(Player player)
	{
		tmplist.remove(player);
		player.setHealth(player.getMaxHealth());
		Main.jlist.put(player.getName(), "군인");
		Main.glist.put(player.getName(), "?");
		player.sendMessage(Main.MS+"당신의 직업은 "+ChatColor.GOLD+
				Main.jlist.get(player.getName())+" 입니다.");
		uc_soldier = true;
		uc_reporter = false;
	}
	
	public static void SetSpy(Player player)
	{
		tmplist.remove(player);
		player.setHealth(player.getMaxHealth());
		Main.jlist.put(player.getName(), "스파이");
		Main.glist.put(player.getName(), "?");
		player.sendMessage(Main.MS+"당신의 직업은 "+ChatColor.GOLD+
				Main.jlist.get(player.getName())+" 입니다.");
	}
	
	public static void SetDetective(Player player)
	{
		tmplist.remove(player);
		player.setHealth(player.getMaxHealth());
		Main.jlist.put(player.getName(), "탐정");
		Main.glist.put(player.getName(), "?");
		player.sendMessage(Main.MS+"당신의 직업은 "+ChatColor.GOLD+
				Main.jlist.get(player.getName())+" 입니다.");
	}
	
	public static void SetNice_Civilian(Player player)
	{
		tmplist.remove(player);
		player.setHealth(player.getMaxHealth());
		Main.jlist.put(player.getName(), "모범시민");
		Main.glist.put(player.getName(), "모범시민");
		player.sendMessage(Main.MS+"당신의 직업은 "+ChatColor.GOLD+
				Main.jlist.get(player.getName())+" 입니다.");
		player.sendMessage(Main.MS+"당신의 직업이 직업리스트에 모범시민으로 올려졌습니다.");
	}
	
	public static void SetReporter(Player player)
	{
		tmplist.remove(player);
		player.setHealth(player.getMaxHealth());
		Main.jlist.put(player.getName(), "기자");
		Main.glist.put(player.getName(), "?");
		player.sendMessage(Main.MS+"당신의 직업은 "+ChatColor.GOLD+
				Main.jlist.get(player.getName())+" 입니다.");
		uc_reporter = false;
	}
	
	public static void SetMagician(Player player)
	{
		tmplist.remove(player);
		player.setHealth(player.getMaxHealth());
		Main.jlist.put(player.getName(), "마술사");
		Main.glist.put(player.getName(), "?");
		player.sendMessage(Main.MS+"당신의 직업은 "+ChatColor.GOLD+
				Main.jlist.get(player.getName())+" 입니다.");
	}
	
	public static void SetGuadian(Player player)
	{
		tmplist.remove(player);
		player.setHealth(player.getMaxHealth());
		Main.jlist.put(player.getName(), "보디가드");
		Main.glist.put(player.getName(), "?");
		player.sendMessage(Main.MS+"당신의 직업은 "+ChatColor.GOLD+
				Main.jlist.get(player.getName())+" 입니다.");
	}
	
	public static void SetCivilian(Player player)
	{
		tmplist.remove(player);
		player.setHealth(player.getMaxHealth());
		Main.jlist.put(player.getName(), "시민");
		Main.glist.put(player.getName(), "?");
		player.sendMessage(Main.MS+"당신의 직업은 "+ChatColor.GOLD+
				Main.jlist.get(player.getName())+" 입니다.");
	}
	
	public static void SetAssasin(Player player)
	{
		astarget = null;
		tmplist.remove(player);
		player.setHealth(player.getMaxHealth());
		Main.jlist.put(player.getName(), "암살자");
		Main.glist.put(player.getName(), "?");
		player.sendMessage(Main.MS+"당신의 직업은 "+ChatColor.GOLD+
				Main.jlist.get(player.getName())+" 입니다.");
		while(astarget == null)
		{
			int ri = Getrandom(Main.list.size());
			Player tmpplayer = Main.list.get(ri);
			if(mlist.contains(tmpplayer) || Getjob(tmpplayer.getName()).equalsIgnoreCase("스파이") || tmpplayer.getName().equalsIgnoreCase(player.getName()))
			{
				continue;
			}
			else
			{
				astarget = Main.list.get(ri).getName();
			}
		}
		assasin = player;
	}
	
	public static void SetJoker(Player player)
	{
		tmplist.remove(player);
		player.setHealth(player.getMaxHealth());
		Main.jlist.put(player.getName(), "조커");
		Main.glist.put(player.getName(), "?");
		player.sendMessage(Main.MS+"당신의 직업은 "+ChatColor.GOLD+
				Main.jlist.get(player.getName())+" 입니다.");
	}
	
	public static int Getsurviver()
	{
		return Main.list.size() - mlist.size();
	}

	public static void Murderdead(Player player)
	{
		Main.SendMessage(Main.MS+ChatColor.GRAY+"범죄자팀인 "+ChatColor.RED+player.getName()+ChatColor.GRAY+" 님이 사망했습니다.");
		Removeplayerfl(player);
		BarAPI.removeBar(player);
		if(mlist.size() <= 0)
		{
			Main.Civilwin();
		}
		else
		{
			Main.SendMessage(Main.MS+"아직 또 다른 범죄자팀이 남아있습니다!");
		}
	}
	
	public static void Reverse(Player player)
	{
		player.teleport(Main.Startpos[Getrandom(Main.Startpos.length)]);
		player.setHealth(player.getMaxHealth());
		player.sendMessage(Main.MS+"부활 하셨습니다.");
		player.sendMessage(Main.MS+"인벤토리는 초기화 됩니다.");
	}
	
	public static void Civildead(Player player)
	{
		Main.SendMessage(Main.MS+ChatColor.RED+player.getName()+ChatColor.GRAY+" 님이 사망했습니다.");
		if(player.getName().equalsIgnoreCase(GameSystem.astarget) && Main.list.contains(assasin))
		{
			assasin.sendMessage(Main.MS+"타겟이 사망 하였습니다! 이제 누구든 죽일 수 있습니다!");
			GameSystem.mlist.add(assasin);
			for(int i = 0; i < GameSystem.mlist.size(); i++)
			{
				GameSystem.mlist.get(i).sendMessage(ChatColor.RED+"암살자 "+ChatColor.AQUA+assasin.getName()+ChatColor.RED+" 님이 동료가 되었습니다.");
			}
		}
		Removeplayerfl(player);
		BarAPI.removeBar(player);
		if(Getsurviver() <= 0)
		{
			Main.Murderwin();
		}
	}
	
	public static void Removeplayerfl(Player player)
	{
		Main.list.remove(player);
		Main.plist.remove(player.getName());
		Main.glist.remove(player.getName());
		Main.jlist.remove(player.getName());
		if(mlist.contains(player))
		{
			mlist.remove(player);
		}
	}
	
	public static boolean CheckReverse(Player player)
	{
		if(doctortarget != null)
		{
			if(doctortarget.equalsIgnoreCase(player.getName()))
			{
				doctortarget = null;
				Main.SendMessage(Main.MS+"의사의 치료로 인하여 "+player.getName()+" 님이 죽지 않았습니다.");
				return true;
			}
		}
		else if(Getjob(player.getName()).equalsIgnoreCase("군인") && uc_soldier)
		{
			uc_soldier = false;
			Main.SendMessage(Main.MS+"군인인 "+ player.getName()+" 님이 범죄자의 공격을 버텨냈습니다!");
			Main.SendMessage(Main.MS+ player.getName()+" 님의 직업이 시민으로 변경됩니다.");
			Main.jlist.put(player.getName(), "시민");
			Main.glist.put(player.getName(), "시민");
			return true;
		}
		return false;
	}
	
	public static void Quitplayer(Player player)
	{
		if(!Main.list.contains(player))
		{
			player.sendMessage(Main.MS+"게임에 참여중이 아닙니다.");
			return;
		}
		if(checkstart)
		{
			if(player.getName().equalsIgnoreCase(GameSystem.astarget) && Main.list.contains(assasin))
			{
				assasin.sendMessage(Main.MS+"타겟이 사망 하였습니다! 이제 누구든 죽일 수 있습니다!");
				GameSystem.mlist.add(assasin);
				for(int i = 0; i < GameSystem.mlist.size(); i++)
				{
					GameSystem.mlist.get(i).sendMessage(ChatColor.RED+"암살자 "+ChatColor.AQUA+assasin.getName()+ChatColor.RED+" 님이 동료가 되었습니다.");
				}
			}
			if(mlist.contains(player))
			{
				Murderdead(player);
			}
			else
			{
				Civildead(player);
			}
			Main.SendMessage(Main.MS+ChatColor.AQUA+player.getName()+" 님이 퇴장 하셨습니다.");
		}
		else
		{
			player.teleport(Main.Lobby);
			Main.list.remove(player);
			Main.SendMessage(Main.MS+ChatColor.AQUA+player.getName()+ChatColor.GRAY+" 님이 퇴장 했습니다. "+ChatColor.RESET+"[ "+ChatColor.GREEN+Main.list.size()+" / 6 "+ChatColor.RESET+"]");
		}
	}
	
	public static void Givespyitem(Player player)
	{
		ItemStack item = new ItemStack(Material.GOLD_SWORD, 1);
		ItemMeta meta = item.getItemMeta();
		meta.setDisplayName(ChatColor.YELLOW+"스파이 검");
		meta.addEnchant(Enchantment.DURABILITY, 7, true);
		List<String> lorelist = new ArrayList();
		lorelist.add(ChatColor.GRAY+"스파이가 접선시 주어지는 검이다.");
		meta.setLore(lorelist);
		item.setItemMeta(meta);
		player.getInventory().addItem(item);
	}
	
	public static void Givemurderitem(Player player)
	{
		/*ItemStack item = new ItemStack(Material.GOLD_SWORD, 1);
		ItemMeta meta = item.getItemMeta();
		meta.setDisplayName(ChatColor.YELLOW+"범죄자의 단검");
		meta.addEnchant(Enchantment.DURABILITY, 7, true);
		meta.addEnchant(Enchantment.FIRE_ASPECT, 1, true);
		List<String> lorelist = new ArrayList();
		lorelist.add(ChatColor.GRAY+"범죄자에게 주어지는 단검이다.");
		meta.setLore(lorelist);
		item.setItemMeta(meta);
		player.getInventory().addItem(item);*/
		Bukkit.dispatchCommand(player.getServer().getConsoleSender(), "kit 살인마킷 "+player.getName());
	}
	
	public static void GiveBasicitem()
	{
		ItemStack item = new ItemStack(Material.COOKED_CHICKEN, 30);
		ItemMeta meta = item.getItemMeta();
		meta.setDisplayName(ChatColor.GOLD+"식은 치킨");
		item.setItemMeta(meta);
		for(int i = 0; i < Main.list.size(); i++)
		{
			Main.list.get(i).getInventory().addItem(item);
		}
	}
	
	public static void Givepoliceitem(Player player)
	{
		ItemStack item = new ItemStack(Material.STICK, 3);
		ItemMeta meta = item.getItemMeta();
		meta.setDisplayName(ChatColor.AQUA+"경찰봉");
		meta.addEnchant(Enchantment.KNOCKBACK, 1, true);
		List<String> lorelist = new ArrayList();
		lorelist.add(ChatColor.GRAY+"경찰에게 주어지는 무기로");
		lorelist.add(ChatColor.GRAY+"이 무기로 때린 플레이어는 3초간");
		lorelist.add(ChatColor.GRAY+"움직이지 못한다. (경찰만 사용가능)");
		meta.setLore(lorelist);
		item.setItemMeta(meta);
		player.getInventory().addItem(item);
	}
}

