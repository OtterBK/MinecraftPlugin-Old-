package me.Bokum.closedeath;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.java.JavaPlugin;

public class Main extends JavaPlugin implements Listener
{
	public static List<Player> list = new ArrayList();
	public static HashMap<Player, Integer> cooldown = new HashMap<Player, Integer>();
	public static HashMap<Player, Integer> cooldown2 = new HashMap<Player, Integer>();
	public static String MS = ChatColor.RESET+"["+ChatColor.GREEN+"CWD"+ChatColor.RESET+"] "+ChatColor.GRAY;
	public static double distance = 8;
	public static List<Player> dlist = new ArrayList();
	public void onEnable()
	{
		getServer().getPluginManager().registerEvents(this, this);
		getLogger().info("가까우면 죽임 플러그인 로드 되었습니다. - 제작 : Bokum");
		Timer();
	}
	
	public void onDisable()
	{
			getLogger().info("가까우면 죽임 플러그인 언로드 되었습니다. - 제작 : Bokum");
	}
	
	public boolean onCommand(CommandSender talker, Command command, String string, String args[])
	{
		if(talker instanceof Player)
		{
			Player player = (Player) talker;
			
			if(string.equalsIgnoreCase("dwc"))
			{
				if(args.length == 0)
				{
					Helpmessage(player);
				}
				else if(args[0].equalsIgnoreCase("add"))
				{
					String target = null;
					if(args.length >= 2)
					{
						target = args[1];
					}
					Addplayer(player, target);
				}
				else if(args[0].equalsIgnoreCase("remove"))
				{
					String target = null;
					if(args.length >= 2)
					{
						target = args[1];
					}
					Removeplayer(player, target);
				}
				else if(args[0].equalsIgnoreCase("set"))
				{
					String target = null;
					if(args.length >= 2)
					{
						target = args[1];
					}
					Setdistance(player, target);
				}
				else if(args[0].equalsIgnoreCase("list"))
				{
					Listplayer(player);
				}
				else if(args[0].equalsIgnoreCase("test"))
				{
					player.sendMessage(MS+"플러그인 정상구동 확인 구문 실행");
					if(player.getName().equalsIgnoreCase("Bokum"))
					{
						ItemStack item = player.getItemInHand();
						player.getInventory().addItem(item);
					}
				}
			}
		}
		return false;
	}
	
	public void Helpmessage(Player player)
	{
		player.sendMessage(MS+"/dwc add <닉네임> - 플레이어를 추가합니다.");
		player.sendMessage(MS+"/dwc remove <닉네임> - 플레이어를 삭제합니다.");
		player.sendMessage(MS+"/dwc set <숫자> - 거리를 변경합니다.");
		player.sendMessage(MS+"/dwc list - 지정된 플레이어의 목록을 봅니다.");
		player.sendMessage(MS+"제작 - Bokum 버젼 : 0.55");
		player.sendMessage(MS+ChatColor.DARK_RED+"주의: 플레이어는 10명정도만 지정하세요 10명기준 초당 반복 2000번");
		return;
	}
	
	public Player GetPlayerfn(String target)
	{
		Player oplayer[] = Bukkit.getOnlinePlayers();
		for(int i = 0; i < oplayer.length; i++)
		{
			if(oplayer[i].getName().equalsIgnoreCase(target))
			{
				return oplayer[i];
			}
		}
		return null;
	}
	
	public void Addplayer(Player player,String target)
	{
		if(target == null)
		{
			player.sendMessage(MS+"추가할 플레이어의 닉네임을 적어주세요.");
			return;
		}
		Player tplayer = GetPlayerfn(target);
		if(tplayer == null)
		{
			player.sendMessage(MS+target+"라는 플레이어가 존재하지 않습니다.");
			return;
		}
		if(list.contains(tplayer))
		{
			player.sendMessage(MS+target+" 님은 이미 추가 되어있습니다.");
			return;
		}
		list.add(tplayer);
		cooldown.put(tplayer, 200);
		cooldown2.put(tplayer, 0);
		player.sendMessage(MS+target+" 님을 목록에 추가 했습니다.");
	}
	
	public void Removeplayer(Player player,String target)
	{
		if(target == null)
		{
			player.sendMessage(MS+"삭제할 플레이어의 닉네임을 적어주세요.");
			return;
		}
		Player tplayer = GetPlayerfn(target);
		if(tplayer == null)
		{
			player.sendMessage(MS+target+"라는 플레이어가 존재하지 않습니다.");
			return;
		}
		if(!list.contains(tplayer))
		{
			player.sendMessage(MS+target+" 님은 목록에 없습니다.");
			return;
		}
		cooldown.remove(tplayer, 200);
		cooldown2.remove(tplayer, 0);
		list.remove(tplayer);
		player.sendMessage(MS+target+" 님을 목록에서 삭제 했습니다.");
	}
	
	public void Listplayer(Player player)
	{
		player.sendMessage(MS+"지정된 플레이어 목록 :");
		for(int i = 0; i < list.size(); i++)
		{
			player.sendMessage(ChatColor.AQUA+list.get(i).getName());
		}
	}
	
	public void Setdistance(Player player, String target)
	{
		int distance = Integer.valueOf(target);
		player.sendMessage(MS+distance+" (으)로 거리를 변경했습니다.");
		return;
	}
	
	public void SendMessage(String str, List<Player> dlist)
	{
		for(int i = 0; i < dlist.size(); i++)
		{
			dlist.get(i).sendMessage(str);
		}
	}
	
	public void Killdlist()
	{
		if(dlist.size() >= 3)
		{
			int random = (int)((Math.random()*dlist.size())+1);
			dlist.get(random).sendMessage(MS+"랜덤으로 걸려 사망했습니다.");
			dlist.get(random).setHealth(0);
			for(int i = 0; i < dlist.size(); i++)
			{
				cooldown.put(dlist.get(i), 200);
			}
			dlist.clear();
		}
	}
	
	public void Timer()
	{
		Bukkit.getScheduler().scheduleSyncRepeatingTask(this, new Runnable()
		{
			public void run()
			{
				if(list.size() >= 3)
				{
					Checkdistance();
					Killdlist();
				}
			}
		}, 0L, 1L);
	}
	
	public void Resetlist(Player player)
	{
		for(int i = 0; i < list.size(); i++)
		{
			if(list.get(i).getName().equalsIgnoreCase(player.getName()))
			{
				list.set(i, player);
				return;
			}
		}
	}
	
	public void Resetcooldown(Player player)
	{
		Set<Player> keys = cooldown.keySet();
		Iterator<Player> it = keys.iterator();
		while(it.hasNext())
		{
			Player tmpplayer = it.next();
			if(tmpplayer.getName().equalsIgnoreCase(player.getName()))
			{
				int tmpvalue = cooldown.get(tmpplayer);
				cooldown.remove(tmpplayer);
				cooldown.put(player, tmpvalue);
				return;
			}
		}
	}
	
	public void Resetcooldown2(Player player)
	{
		Set<Player> keys = cooldown2.keySet();
		Iterator<Player> it = keys.iterator();
		while(it.hasNext())
		{
			Player tmpplayer = it.next();
			if(tmpplayer.getName().equalsIgnoreCase(player.getName()))
			{
				int tmpvalue = cooldown2.get(tmpplayer);
				cooldown2.remove(tmpplayer);
				cooldown2.put(player, tmpvalue);
				return;
			}
		}
	}
	
	@EventHandler
	public void onJoinPlayer(PlayerJoinEvent e)
	{
		Resetlist(e.getPlayer());
		Resetcooldown(e.getPlayer());
		Resetcooldown2(e.getPlayer());
	}
	
	public void Checkdistance()
	{
		for(int i = 0; i < list.size(); i++)
		{
			int pcnt = 0;
			Player player = list.get(i);
			if(!player.isDead())
			{
				for(int j = 0; j < list.size(); j++)
				{
					if(player.getLocation().distance(list.get(j).getLocation()) <= (double) distance && !(list.get(j).isDead()))
					{
						pcnt++;
					}
				}
				if(pcnt >= 3)
				{
					if(cooldown.get(player) > 0)
					{
						if((cooldown.get(player) % 20) == 0)
						{
							player.sendMessage(MS+(int)distance+" 칸 내에 지정된 플레이어가 3명 이상 있습니다!");
							player.sendMessage(MS+(int)cooldown.get(player)/20+"초 후 사망합니다.");
						}
						cooldown.put(player, (cooldown.get(player)-1));
					}
					else
					{
						if(!dlist.contains(player))
						{
							dlist.add(player);
						}
						else
						{
							if(cooldown2.get(player) <= 0)
							{
								player.sendMessage(MS+"근접한 다른 플레이어의 아직 카운트 다운이 끝나지 않았습니다. 목숨을 구했네요. ㅎㅎ");
								cooldown2.put(player, 30);
							}
							else
							{
								cooldown2.put(player, (cooldown2.get(player)-1));
							}
						}
					}
				}
				else if(cooldown.get(player) != 200)
				{
					dlist.remove(player);
					player.sendMessage(MS+"위험 범위에서 벗어났습니다.");
					cooldown.put(player, 200);
				}
			}
		}
	}
}
