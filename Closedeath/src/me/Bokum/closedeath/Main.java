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
		getLogger().info("������ ���� �÷����� �ε� �Ǿ����ϴ�. - ���� : Bokum");
		Timer();
	}
	
	public void onDisable()
	{
			getLogger().info("������ ���� �÷����� ��ε� �Ǿ����ϴ�. - ���� : Bokum");
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
					player.sendMessage(MS+"�÷����� ���󱸵� Ȯ�� ���� ����");
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
		player.sendMessage(MS+"/dwc add <�г���> - �÷��̾ �߰��մϴ�.");
		player.sendMessage(MS+"/dwc remove <�г���> - �÷��̾ �����մϴ�.");
		player.sendMessage(MS+"/dwc set <����> - �Ÿ��� �����մϴ�.");
		player.sendMessage(MS+"/dwc list - ������ �÷��̾��� ����� ���ϴ�.");
		player.sendMessage(MS+"���� - Bokum ���� : 0.55");
		player.sendMessage(MS+ChatColor.DARK_RED+"����: �÷��̾�� 10�������� �����ϼ��� 10����� �ʴ� �ݺ� 2000��");
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
			player.sendMessage(MS+"�߰��� �÷��̾��� �г����� �����ּ���.");
			return;
		}
		Player tplayer = GetPlayerfn(target);
		if(tplayer == null)
		{
			player.sendMessage(MS+target+"��� �÷��̾ �������� �ʽ��ϴ�.");
			return;
		}
		if(list.contains(tplayer))
		{
			player.sendMessage(MS+target+" ���� �̹� �߰� �Ǿ��ֽ��ϴ�.");
			return;
		}
		list.add(tplayer);
		cooldown.put(tplayer, 200);
		cooldown2.put(tplayer, 0);
		player.sendMessage(MS+target+" ���� ��Ͽ� �߰� �߽��ϴ�.");
	}
	
	public void Removeplayer(Player player,String target)
	{
		if(target == null)
		{
			player.sendMessage(MS+"������ �÷��̾��� �г����� �����ּ���.");
			return;
		}
		Player tplayer = GetPlayerfn(target);
		if(tplayer == null)
		{
			player.sendMessage(MS+target+"��� �÷��̾ �������� �ʽ��ϴ�.");
			return;
		}
		if(!list.contains(tplayer))
		{
			player.sendMessage(MS+target+" ���� ��Ͽ� �����ϴ�.");
			return;
		}
		cooldown.remove(tplayer, 200);
		cooldown2.remove(tplayer, 0);
		list.remove(tplayer);
		player.sendMessage(MS+target+" ���� ��Ͽ��� ���� �߽��ϴ�.");
	}
	
	public void Listplayer(Player player)
	{
		player.sendMessage(MS+"������ �÷��̾� ��� :");
		for(int i = 0; i < list.size(); i++)
		{
			player.sendMessage(ChatColor.AQUA+list.get(i).getName());
		}
	}
	
	public void Setdistance(Player player, String target)
	{
		int distance = Integer.valueOf(target);
		player.sendMessage(MS+distance+" (��)�� �Ÿ��� �����߽��ϴ�.");
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
			dlist.get(random).sendMessage(MS+"�������� �ɷ� ����߽��ϴ�.");
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
							player.sendMessage(MS+(int)distance+" ĭ ���� ������ �÷��̾ 3�� �̻� �ֽ��ϴ�!");
							player.sendMessage(MS+(int)cooldown.get(player)/20+"�� �� ����մϴ�.");
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
								player.sendMessage(MS+"������ �ٸ� �÷��̾��� ���� ī��Ʈ �ٿ��� ������ �ʾҽ��ϴ�. ����� ���߳׿�. ����");
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
					player.sendMessage(MS+"���� �������� ������ϴ�.");
					cooldown.put(player, 200);
				}
			}
		}
	}
}
