package me.Bokum.FindTheMurder;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.block.Chest;
import org.bukkit.block.DoubleChest;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.entity.EntityDamageEvent.DamageCause;
import org.bukkit.event.inventory.InventoryOpenEvent;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.event.player.PlayerDropItemEvent;
import org.bukkit.event.player.PlayerInventoryEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.event.player.PlayerToggleSneakEvent;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

import me.confuser.barapi.BarAPI;


public class Main extends JavaPlugin implements Listener
{
	
	public static String MS = ChatColor.RESET+"["+ChatColor.GREEN+"BJ���"+ChatColor.RESET+"] "+ChatColor.YELLOW;
	public static List<Player> list = new ArrayList();
	public static HashMap<String, Player> plist = new HashMap<String, Player>();
	public static HashMap<String, String> jlist = new HashMap<String, String>();
	public static HashMap<String, String> glist = new HashMap<String, String>();
	public static HashMap<String, Integer> vlist = new HashMap<String, Integer>();
	public static List<Player> vplist = new ArrayList();
	public static Main instance;
	public static Location Lobby;
	public static Location Startpos[] = new Location[15];
	public static Location joinpos;
	public static int schtmp2 = 0;
	public static int timer_sch2 = 0;
	public static int min = 0;
	public static int hour = 0;
	public static String AP = "AM";
	public static int timer_cnt = 0;
	public static float timer = 0;
	
	public void LoadConfig()
	{
		try
		{
			joinpos = new Location(Bukkit.getWorld(getConfig().getString("Join_world")),getConfig().getInt("Join_x"),getConfig().getInt("Join_y"),getConfig().getInt("Join_z"));
			Lobby = new Location(Bukkit.getWorld(getConfig().getString("Lobby_world")),getConfig().getInt("Lobby_x"),getConfig().getInt("Lobby_y"),getConfig().getInt("Lobby_z"));
		}
		catch(java.lang.IllegalArgumentException e)
		{
			getLogger().info("���� ���� �Ǵ� �κ� �����Ǿ� ���� �ʽ��ϴ�");
		}
		for(int i = 1; i <= Startpos.length; i++)
		{
			try
			{
				Startpos[(i-1)] = new Location(Bukkit.getWorld(getConfig().getString("Start_world"+i)),getConfig().getInt("Start_x"+i),getConfig().getInt("Start_y"+i),getConfig().getInt("Start_z"+i));
			}
			catch(java.lang.IllegalArgumentException e)
			{
				getLogger().info(i+"��° ���������� ���� �Ǿ����� �ʽ��ϴ�. ���� �߻��� ����� �ֽ��ϴ�.");
			}
		}
	}
	
	public static void Cancelalltasks()
	{
		Bukkit.getScheduler().cancelTasks(instance);
	}
	
	public static void Startgame()
	{
		Bukkit.broadcastMessage(Main.MS+ChatColor.DARK_RED+"�����ڸ� ã�ƶ� "+ChatColor.GRAY+"������ �� ���۵˴ϴ�.");
		GameSystem.schtime = 5;
		schtmp2 = Bukkit.getScheduler().scheduleSyncRepeatingTask(instance, new Runnable()
				{
					public void run()
					{
						if(GameSystem.schtime > 0)
						{
							Main.SendMessage(Main.MS+ChatColor.DARK_RED+"�����ڸ� ã�ƶ� "+ChatColor.GRAY+"������ "+ChatColor.AQUA+(GameSystem.schtime*10)+ChatColor.GRAY+" �� �� �����մϴ�.");
							GameSystem.schtime--;
						}
						else
						{
							Bukkit.getScheduler().cancelTask(schtmp2);
							StartTP();
						}
					}
				}, 200L, 200L);
	}
	
	
	public static void StartTP()
	{
		GameSystem.checkstart = true;
		for(int i = 0; i < Main.list.size(); i++)
		{
			Main.list.get(i).setSneaking(true);
			Main.list.get(i).setMaxHealth(40);
			Main.list.get(i).teleport(Main.Startpos[GameSystem.Getrandom(Main.Startpos.length)]);
		}
		GameSystem.schtime = 3;
		BarAPI.setMessage("������ ���ñ���", 30);
		schtmp2 = Bukkit.getScheduler().scheduleSyncRepeatingTask(instance, new Runnable()
				{
					public void run()
					{
						if(GameSystem.schtime > 0)
						{
							Main.SendMessage(Main.MS+"�����ڰ� "+ChatColor.GOLD+GameSystem.schtime*10+ChatColor.GRAY+" �� �� �������ϴ�.");
							GameSystem.schtime--;
						}
						else
						{
							Bukkit.getScheduler().cancelTask(schtmp2);
							GameSystem.Setjob();
							GameSystem.GiveBasicitem();
							hour = 8;
							min = 0;
							AP = "AM";
							timer_cnt = 0;
							SetBar();
							DayCycle();
						}
					}
				}, 0L, 200L);
	}
	
	public static void DayCycle()
	{
		if(GameSystem.checkstart)
		{
			if(GameSystem.isday)
			{
				SetNight();
			}
			else
			{
				SetDay();
			}
		}
	}
	
	public static void SetDay()
	{
		GameSystem.isday = true;
		GameSystem.dayinit();
		GameSystem.daycnt++;
		timer = 144;
		timer_cnt = 0;
		Bukkit.getWorld("world").setTime(0);
		Main.SendMessage(Main.MS+ChatColor.AQUA+GameSystem.daycnt+ChatColor.YELLOW+"��° ���� ���� �Ǿ����ϴ�.");
		Main.SendMessage(Main.MS+"������ ������ ���� �÷��̾�� ��ǥ�ϼ���. /ftm vote <�г���>");
		Bukkit.getScheduler().scheduleSyncDelayedTask(instance, new Runnable()
		{
			public void run()
			{
				GameSystem.Setvotelist();
				DayCycle();
			}
		},2880L);
	}
	
	public static void SetNight()
	{
		GameSystem.isday = false;
		GameSystem.nightinit();
		Bukkit.getWorld("world").setTime(14000);
		timer = 144;
		timer_cnt = 0;
		Main.SendMessage(Main.MS+ChatColor.AQUA+GameSystem.daycnt+ChatColor.YELLOW+"��° ���� ���� �Ǿ����ϴ�.");
		/*PotionEffect pt = new PotionEffect(PotionEffectType.BLINDNESS, 940, 0);
		for(int i = 0; i < list.size(); i++)
		{
			list.get(i).addPotionEffect(pt);
		}*/
		Bukkit.getScheduler().scheduleSyncDelayedTask(instance, new Runnable()
		{
			public void run()
			{
				DayCycle();
			}
		},2880L);
	}
	
	public static void Murderwin()
	{
			Main.SendMessage(Main.MS+ChatColor.RED+"��� �ù����� ����߽��ϴ�!!!");
			Main.SendMessage(Main.MS+ChatColor.RED+"�������� �¸��߽��ϴ�!");

			Bukkit.getScheduler().scheduleSyncDelayedTask(instance, new Runnable()
			{
				public void run()
				{
						for(int i = 0; i < Main.list.size(); i++)
						{
							Player player = Main.list.get(i);
							player.teleport(Main.Lobby);
							BarAPI.removeBar(player);
						}
						GameSystem.End();
						Bukkit.broadcastMessage(Main.MS+ChatColor.GREEN+ChatColor.BOLD+"���������� �¸��� �����ڸ� ã�ƶ� ���� �Ǿ����ϴ�.");
				}
			}, 100L);
	}
	
	public static void Civilwin()
	{	
			Main.SendMessage(Main.MS+ChatColor.RED+"��� ���������� ����߽��ϴ�!!!");
			Main.SendMessage(Main.MS+ChatColor.RED+"�ù����� �¸��߽��ϴ�!");

			Bukkit.getScheduler().scheduleSyncDelayedTask(instance, new Runnable()
			{
				public void run()
				{
						for(int i = 0; i < Main.list.size(); i++)
						{
							Player player = Main.list.get(i);
							player.teleport(Main.Lobby);
							BarAPI.removeBar(player);
						}
						GameSystem.End();
						Bukkit.broadcastMessage(Main.MS+ChatColor.GREEN+ChatColor.BOLD+"�ù����� �¸��� �����ڸ� ã�ƶ� ���� �Ǿ����ϴ�.");
				}
			}, 100L);
	}
	
	public void SaveLobby(Player player)
	{
		getConfig().set("Lobby_world", player.getLocation().getWorld().getName());
		getConfig().set("Lobby_x", player.getLocation().getBlockX());
		getConfig().set("Lobby_y", player.getLocation().getBlockY()+1);
		getConfig().set("Lobby_z", player.getLocation().getBlockZ());
		saveConfig();
		LoadConfig();
		player.sendMessage(MS+"���ηκ� ���� �Ǿ����ϴ�.");
	}
	
	public void SaveStartpos(Player player , int posn)
	{
		getConfig().set("Start_world"+posn, player.getLocation().getWorld().getName());
		getConfig().set("Start_x"+posn, player.getLocation().getBlockX());
		getConfig().set("Start_y"+posn, player.getLocation().getBlockY()+1);
		getConfig().set("Start_z"+posn, player.getLocation().getBlockZ());
		saveConfig();
		LoadConfig();
		player.sendMessage(MS+posn+"��° ���������� ���� �Ǿ����ϴ�.");
	}
	
	public void SaveJoinpos(Player player)
	{
		getConfig().set("Join_world", player.getLocation().getWorld().getName());
		getConfig().set("Join_x", player.getLocation().getBlockX());
		getConfig().set("Join_y", player.getLocation().getBlockY()+1);
		getConfig().set("Join_z", player.getLocation().getBlockZ());
		saveConfig();
		LoadConfig();
		player.sendMessage(MS+"�κ� ���� �Ǿ����ϴ�.");
	}
	
	public void onEnable()
	{
		getServer().getPluginManager().registerEvents(this, this);
		getLogger().info("�����ڸ� ã�ƶ� �÷����� �ε� �Ϸ�! - ���� : Bokum");
		instance = this;
		LoadConfig();
	}
	
	public void onDisable()
	{
		getLogger().info("�����ڸ� ã�ƶ� �÷����� ��ε� �Ϸ�");
	}
	
	public boolean onCommand(CommandSender talker, Command command, String string, String args[])
	{
		if(talker instanceof Player)
		{
			Player player = (Player) talker;
			if(string.equalsIgnoreCase("ftm"))
			{
				if(args.length == 0)
				{
					HelpMessage(player);
					return true;
				}
				if(args[0].equalsIgnoreCase("set"))
				{
					if(!player.hasPermission("ftm.set"))
					{
						player.sendMessage(MS+"������ �����ϴ�.");
						return false;
					}
					if(args.length <= 1)
					{
						HelpMessage(player);
						return true;
					}
					else if(args[1].equalsIgnoreCase("start"))
					{
						if(args.length <= 2)
						{
							player.sendMessage(Main.MS+"/ftm set start <1~15>");
							return true;
						}
						SaveStartpos(player, Integer.valueOf(args[2]));
						return true;
					}
					else if(args[1].equalsIgnoreCase("lobby"))
					{
						SaveLobby(player);
						return true;
					}
					else if(args[1].equalsIgnoreCase("join"))
					{
						SaveJoinpos(player);
						return true;
					}
					else
					{
						HelpMessage(player);
					}
				}
				if(args[0].equalsIgnoreCase("start"))
				{
					if(!player.hasPermission("ftm.join"))
					{
						player.sendMessage(MS+"������ �����ϴ�.");
						return false;
					}
					GameSystem.Startgame(player);
					return true;
				}
				else if(args[0].equalsIgnoreCase("quit"))
				{
					if(!player.hasPermission("ftm.quit"))
					{
						player.sendMessage(MS+"������ �����ϴ�.");
						return false;
					}
					GameSystem.Quitplayer(player);
					return true;
				}
				else if(args[0].equalsIgnoreCase("use"))
				{
					if(!plist.containsKey(player.getName()))
					{
						player.sendMessage(MS+"���ӿ� ���������� �ʽ��ϴ�.");
						return true;
					}
					if(!jlist.containsKey(player.getName()))
					{
						player.sendMessage(MS+"������ �����Ǿ� ���� �ʽ��ϴ�. - ���׷� ����");
						return true;
					}
					String target = null;
					if(args.length > 1)
					{
						target = args[1];
					}
					GameSystem.Use(player, target);
					return true;
				}
				else if(args[0].equalsIgnoreCase("help"))
				{
					Jobhelp(player);
					return true;
				}
				else if(args[0].equalsIgnoreCase("list"))
				{
					Glisting(player);
					return true;
				}
				else if(args[0].equalsIgnoreCase("jlist"))
				{
					if(!player.hasPermission("ftm.jlist"))
					{
						player.sendMessage(MS+"������ �����ϴ�.");
						return false;
					}
					Jlisting(player);
					return true;
				}
				else if(args[0].equalsIgnoreCase("reload"))
				{
					if(!player.hasPermission("ftm.reload"))
					{
						player.sendMessage(MS+"������ �����ϴ�.");
						return false;
					}
					LoadConfig();
					return true;
				}
				else if(args[0].equalsIgnoreCase("stop"))
				{
					if(!player.hasPermission("ftm.stop"))
					{
						player.sendMessage(MS+"������ �����ϴ�.");
						return false;
					}
					GameSystem.ForceEnd();
					return true;
				}
				else if(args[0].equalsIgnoreCase("vote"))
				{
					String str = null;
					if(args.length >= 2)
					{
						str = args[1];
					}
					Voteplayer(player,str);
					return true;
				}
				else if(args[0].equalsIgnoreCase("test"))
				{
					player.sendMessage("����");
				}
			}
		}
		return true;
	}
	
	public void Voteplayer(Player player, String str)
	{
		if(!GameSystem.checkstart)
		{
			player.sendMessage(Main.MS+"������ ���۵��� �ʾҽ��ϴ�.");
		}
		else if(!Main.list.contains(player))
		{
			player.sendMessage(Main.MS+"����� ���ӿ� ���������� �ʽ��ϴ�.");
		}
		else if(!GameSystem.isday)
		{
			player.sendMessage(Main.MS+"��ǥ�� ������ �����մϴ�.");
		}
		else if(str == null)
		{
			player.sendMessage(MS+"�̸��� �Է����ּ���.");
		}
		else if(!plist.containsKey(str))
		{
			player.sendMessage(MS+str+" ���� ���ӿ� �������� �ƴմϴ�.");
		}
		else if(vplist.contains(player))
		{
			player.sendMessage(MS+str+" �̹� ��ǥ �ϼ̽��ϴ�.");
		}
		else
		{
			vplist.add(player);
			if(!vlist.containsKey(str))
			{
				vlist.put(str, 1);
			}
			else
			{
				vlist.put(str, vlist.get(str)+1);
			}
			if(GameSystem.Getjob(player.getName()).equalsIgnoreCase("����ù�"))
			{
				vlist.put(str, vlist.get(str)+1);
				player.sendMessage(MS+"�ɷ��� ȿ���� 2ǥ�� ȿ���� �����մϴ�.");
			}
			player.sendMessage(MS+str+" ���� ��ǥ�߽��ϴ�.");
			SendMessage(MS+"�������� "+ChatColor.AQUA+str+ChatColor.YELLOW+" �Կ��� ��ǥ�߽��ϴ�.");
		}
	}
	
	public void Jobhelp(Player player)
	{
		if(!jlist.containsKey(player.getName()))
		{
			player.sendMessage(Main.MS+"������ �����ϴ�.");
		}
		else
		{
			switch(GameSystem.Getjob(player.getName()))
			{
				case "������": Help_Murder(player);return;
				case "�ǻ�": Help_Doctor(player);return;
				case "����": Help_Police(player);return;
				case "����": Help_Soldier(player);return;
				case "������": Help_Spy(player);return;
				case "���𰡵�": Help_Guardian(player);return;
				case "������": Help_Magician(player);return;
				case "����ù�": Help_Nice_Civilian(player);return;
				case "�ù�": Help_Civilian(player);return;
				case "Ž��": Help_Detective(player);return;
				case "����": Help_Reporter(player);return;
				case "�ϻ���": Help_Assasin(player);return;
				default: player.sendMessage("���� �����Դϴ�. - ���� �ǽ�"); return;
			}
		}
	}
	
	public static void SetBar()
	{
		Bukkit.getScheduler().cancelTask(timer_sch2);
		timer_sch2 = Bukkit.getScheduler().scheduleSyncRepeatingTask(instance, new Runnable()
		{
			public void run()
			{
				timer_cnt++;
				if(timer_cnt >= 5)
				{
					timer_cnt = 0;
					timer--;
				}
				if(++min >= 60)
				{
					min = 0;
					if(++hour == 12)
					{
						if(AP.equalsIgnoreCase("AM"))
						{
							AP = "PM";
						}
						else
						{
							AP = "AM";
						}
					}
					if(hour >= 13)
					{
						hour = 1;
					}
				}
				BarAPI.setMessage(ChatColor.RESET+"[ "+ChatColor.YELLOW+hour+" : "+(min < 10 ? "0"+min : min)+" "+AP+ChatColor.RESET+" ]", (float) timer / (float) 1.44); 
			}
		}, 0L, 4L);
	}
	
	public void Help_Murder(Player player)
	{
		player.sendMessage(ChatColor.GOLD+"����� �ɷ� : "+ChatColor.AQUA+GameSystem.Getjob(player.getName()));
		player.sendMessage(ChatColor.GRAY+"����� ������ ���⸦ ������ �ֽ��ϴ�. �� ���⸦ �̿��Ͽ� ��� �ù����� �׿����մϴ�.");
		player.sendMessage(ChatColor.GRAY+"�����̰� �ɷ��� ����Ͽ� ��Ű� ������ �����̴� ���ᰡ �˴ϴ�.");
		player.sendMessage(ChatColor.GRAY+"���� �ϻ��ڰ� �ù��� ����ҽ� ����� ���ᰡ �˴ϴ�.");
	}
	
	public void Help_Doctor(Player player)
	{
		player.sendMessage(ChatColor.GOLD+"����� �ɷ� : "+ChatColor.AQUA+GameSystem.Getjob(player.getName()));
		player.sendMessage(ChatColor.GRAY+"����� �㸶�� 1���� ������ �� �ֽ��ϴ�.");
		player.sendMessage(ChatColor.GRAY+"�� ������ �÷��̾�� �� ��� ������ ���� ����� �ѹ��� ��Ȱ�ϸ� ");
		player.sendMessage(ChatColor.GRAY+"���� ���� ã�ƿ´ٸ� �ٽ� �÷��̾ �����ؾ� �մϴ�.");
		player.sendMessage(ChatColor.GRAY+"�ɷ� ����� /ftm use <�г���>");
	}
	
	public void Help_Police(Player player)
	{
		player.sendMessage(ChatColor.GOLD+"����� �ɷ� : "+ChatColor.AQUA+GameSystem.Getjob(player.getName()));
		player.sendMessage(ChatColor.GRAY+"����� ������ 1���� ������ �� �ֽ��ϴ�.");
		player.sendMessage(ChatColor.GRAY+"������ �÷��̾ �������� ��� ");
		player.sendMessage(ChatColor.GRAY+"�� �÷��̾ �����ڶ�� �޼����� ��ſ��� �������ϴ�.");
		player.sendMessage(ChatColor.GRAY+"�ɷ� ����� /ftm use <�г���>");
	}
	
	public void Help_Soldier(Player player)
	{
		player.sendMessage(ChatColor.GOLD+"����� �ɷ� : "+ChatColor.AQUA+GameSystem.Getjob(player.getName()));
		player.sendMessage(ChatColor.GRAY+"����� �ܷõ� ��ü�� �������ϴ�.");
		player.sendMessage(ChatColor.GRAY+"����� �ѹ��� ��Ȱ�ϰ� �˴ϴ�. ");
		player.sendMessage(ChatColor.GRAY+"��Ȱ�Ŀ��� ������ �ù����� ����˴ϴ�.");
	}
	
	public void Help_Spy(Player player)
	{
		player.sendMessage(ChatColor.GOLD+"����� �ɷ� : "+ChatColor.AQUA+GameSystem.Getjob(player.getName()));
		player.sendMessage(ChatColor.GRAY+"�㸶�� 1���� �÷��̾�� ������ �����մϴ�.");
		player.sendMessage(ChatColor.GRAY+"���� ������ �÷��̾ �����ڶ�� ����� �������� ���ᰡ �Ǹ�");
		player.sendMessage(ChatColor.GRAY+"�ù����� ���̰� �ٴ� �� �ֽ��ϴ�.");
		player.sendMessage(ChatColor.GRAY+"���� ������ �÷��̾ �����ڰ� �ƴ϶�� ����� �� �÷��̾��� ������ �� �� �ֽ��ϴ�.");
		player.sendMessage(ChatColor.GRAY+"�ɷ� ����� /ftm use <�г���>");
	}
	
	public void Help_Guardian(Player player)
	{
		player.sendMessage(ChatColor.GOLD+"����� �ɷ� : "+ChatColor.AQUA+GameSystem.Getjob(player.getName()));
		player.sendMessage(ChatColor.GRAY+"�㸶�� �θ��� �÷��̾ �����Ͻ� �� �ֽ��ϴ�.");
		player.sendMessage(ChatColor.GRAY+"�� ������ �÷��̾�� ������ ���� ��ǥ�� ���Ͽ� ������ �������� �ʽ��ϴ�.");
		player.sendMessage(ChatColor.GRAY+"�ɷ� ����� /ftm use <�г���>");
	}
	
	public void Help_Magician(Player player)
	{
		player.sendMessage(ChatColor.GOLD+"����� �ɷ� : "+ChatColor.AQUA+GameSystem.Getjob(player.getName()));
		player.sendMessage(ChatColor.GRAY+"���� �� �� �ѹ��� �ɷ��� ����� �� ������");
		player.sendMessage(ChatColor.GRAY+"����� �÷��̾��� ������ ���Ѿ� �����ϴ�.");
		player.sendMessage(ChatColor.GRAY+"��� ����� ������ �����ڶ�� �� �÷��̾�� ����ϰ� �˴ϴ�.");
		player.sendMessage(ChatColor.GRAY+"��� ����� ������ ������, �ϻ��ڶ�� ���Ѵ� ���� �ƴ� ���縦 �մϴ�.");
		player.sendMessage(ChatColor.GRAY+"�ɷ� ����� /ftm use <�г���>");
	}
	
	public void Help_Nice_Civilian(Player player)
	{
		player.sendMessage(ChatColor.GOLD+"����� �ɷ� : "+ChatColor.AQUA+GameSystem.Getjob(player.getName()));
		player.sendMessage(ChatColor.GRAY+"��ǥ�� ����� ��ǥ�� 2ǥ�� ��޵Ǹ� ���ۺ���");
		player.sendMessage(ChatColor.GRAY+"�ڽ��� ������ ��������Ʈ�� �������ϴ�.");
		player.sendMessage(ChatColor.GRAY+"����� �������Ե� �ǽɹ��� ���� �� �Դϴ�.");
	}
	
	public void Help_Civilian(Player player)
	{
		player.sendMessage(ChatColor.GOLD+"����� �ɷ� : "+ChatColor.AQUA+GameSystem.Getjob(player.getName()));
		player.sendMessage(ChatColor.GRAY+"����� Ư���� �ɷ��� ������ �ʾҽ��ϴ�.");
		player.sendMessage(ChatColor.GRAY+"���� �ϻ��ڴ� ������ ����� �븱 ���Դϴ�.");
		player.sendMessage(ChatColor.GRAY+"�����ϼ���.");
	}
	
	public void Help_Detective(Player player)
	{
		player.sendMessage(ChatColor.GOLD+"����� �ɷ� : "+ChatColor.AQUA+GameSystem.Getjob(player.getName()));
		player.sendMessage(ChatColor.GRAY+"������ �Ѹ��� �÷��̾ ������ �� �ֽ��ϴ�.");
		player.sendMessage(ChatColor.GRAY+"���� �Ǿ��� �� /ftm use �� �Է��Ͻø�");
		player.sendMessage(ChatColor.GRAY+"�� �÷��̾ ���� ����ִ� ����, ��ǥ, ü�� ������ ���÷�");
		player.sendMessage(ChatColor.GRAY+"Ȯ���Ͻ� �� �ֽ��ϴ�.");
		player.sendMessage(ChatColor.GRAY+"�ɷ� ����� /ftm use <�г���>");
	}
	
	public void Help_Reporter(Player player)
	{
		player.sendMessage(ChatColor.GOLD+"����� �ɷ� : "+ChatColor.AQUA+GameSystem.Getjob(player.getName()));
		player.sendMessage(ChatColor.GRAY+"���� �� �ѹ��� �ɷ��� ����� �� ������");
		player.sendMessage(ChatColor.GRAY+"�ɷ��� ����� �÷��̾��� ������ ��ο���");
		player.sendMessage(ChatColor.GRAY+"�˸��ϴ�. ���� ��������Ʈ�� �� �÷��̾��� ������");
		player.sendMessage(ChatColor.GRAY+"�����˴ϴ�.");
		player.sendMessage(ChatColor.GRAY+"�ɷ� ����� /ftm use <�г���>");
	}
	
	public void Help_Assasin(Player player)
	{
		player.sendMessage(ChatColor.GOLD+"����� �ɷ� : "+ChatColor.AQUA+GameSystem.Getjob(player.getName()));
		player.sendMessage(ChatColor.GRAY+"����� ����� �ùΰ� �����ϴ�.");
		player.sendMessage(ChatColor.GRAY+"������ Ÿ���� ����ϰų� ���ӿ��� ������ ��");
		player.sendMessage(ChatColor.GRAY+"����� �������� ���ᰡ �Ǹ� �ù����� ���� �� �ֽ��ϴ�.");
		player.sendMessage(ChatColor.GRAY+"����� Ÿ�� : "+ChatColor.RED+GameSystem.astarget);
		player.sendMessage(ChatColor.GRAY+"�� �÷��̾ �������Ե� ���ش��ϸ� �˴ϴ�.");
	}
	
	public void HelpMessage(Player player)
	{
		player.sendMessage(MS+ChatColor.AQUA+"/ftm help - �ɷ��� ������ ���ϴ�.");
		player.sendMessage(MS+ChatColor.AQUA+"/ftm use <�г���> - �ش� ��󿡰� �ɷ��� ����մϴ�.");
		player.sendMessage(MS+ChatColor.AQUA+"/ftm list - ���� ����Ʈ�� ���ϴ�.");
		player.sendMessage(MS+"/ftm start - ������ ���۽�ŵ�ϴ�..");
		player.sendMessage(MS+"/ftm quit - ���ӿ��� �����մϴ�..");
		player.sendMessage(MS+"/ftm set lobby - ���ִ� ������ ���ηκ�� �����մϴ�.");
		player.sendMessage(MS+"/ftm set join- ���ִ� ������ �κ�� �����մϴ�.");
		player.sendMessage(MS+"/ftm set start 1~15 - ���ִ� ������ ������������ �����մϴ�.");
		player.sendMessage(MS+"/ftm reload - config�� ���ε� �մϴ�.");
		player.sendMessage(MS+"/ftm stop - ������ ���� ���� �մϴ�.");
		player.sendMessage(MS+"���� 1.34");
	}
	
	public static void SendMessage(String str)
	{
		for(int i = 0; i < list.size(); i++)
		{
			list.get(i).sendMessage(str);
		}
	}
	
	public void Glisting(Player player)
	{
		player.sendMessage(MS+"���� ����Ʈ (�ο� "+glist.size()+"��): ");
		Set<String> keys = glist.keySet();
		Iterator<String> it = keys.iterator();
		while(it.hasNext())
		{
			String name = it.next();
			player.sendMessage(ChatColor.RED+name+ChatColor.RESET+" : "+ChatColor.GOLD+glist.get(name));
		}
	}
	
	public void Jlisting(Player player)
	{
			player.sendMessage(MS+"���� ����Ʈ :");
			Set<String> keys = jlist.keySet();
			Iterator<String> it = keys.iterator();
			while(it.hasNext())
			{
				String name = it.next();
				player.sendMessage(ChatColor.RED+name+ChatColor.RESET+" : "+ChatColor.GOLD+jlist.get(name));
			}
	}
	
	@EventHandler
	public static void onHitPlayer(EntityDamageByEntityEvent e)
	{
		if(e.getEntity() instanceof Player && e.getDamager() instanceof Player && GameSystem.checkstart)
		{
			Player player = (Player) e.getEntity();
			Player damager = (Player) e.getDamager();
			if(!list.contains(player) || !list.contains(damager) || !jlist.containsKey(damager.getName()))
			{
				if(list.contains(player))
				{
					e.setCancelled(true);
				}
				return;
			}
			if(GameSystem.Getjob(damager.getName()).equalsIgnoreCase("����"))
			{
				if(damager.getItemInHand().hasItemMeta())
				{
					if(damager.getItemInHand().getItemMeta().hasDisplayName())
					{
						if(damager.getItemInHand().getItemMeta().getDisplayName().equalsIgnoreCase(ChatColor.AQUA+"������"))
						{
							if(damager.getItemInHand().getAmount() != 1)
							{
								damager.getItemInHand().setAmount(damager.getItemInHand().getAmount()-1);
							}
							else
							{
								damager.setItemInHand(null);	
							}
							player.addPotionEffect(new PotionEffect(PotionEffectType.SLOW, 60, 10));
							player.sendMessage(ChatColor.RED+"�������� �¾ҽ��ϴ�!!!");
							damager.sendMessage(ChatColor.RED+"�������� ��� �Ͽ����ϴ�.");
						}
					}
				}
			}
			if(GameSystem.mlist.contains(player) && GameSystem.mlist.contains(damager))
			{
				player.sendMessage(Main.MS+"���� ���������� "+damager.getName()+"���� ����� �����߽��ϴ�! ");
				damager.sendMessage(Main.MS+player.getName()+" ���� ���� �������� �Դϴ�! �������ּ���!");
			}
			int damage = e.getDamage();
			if(player.getHealth() - damage <= 0 && !player.isDead())
			{
				if(!(GameSystem.mlist.contains(player)) && !(GameSystem.Getjob(player.getName()).equalsIgnoreCase("�ϻ���")) && !(GameSystem.Getjob(player.getName()).equalsIgnoreCase("������")))
				{
					if(!GameSystem.mlist.contains(damager))
					{
						if(!(GameSystem.Getjob(damager.getName()).equalsIgnoreCase("�ϻ���") && GameSystem.astarget.equalsIgnoreCase(player.getName())))
						{
							e.setCancelled(true);
							GameSystem.Removeplayerfl(damager);
							damager.sendMessage(Main.MS+"����� ������ �÷��̾ �׿����� ó�� �ǿ����ϴ�.");
							damager.setHealth(0);
							return;
						}
					}
					if(GameSystem.CheckReverse(player))
					{
						e.setCancelled(true);
						GameSystem.Reverse(player);
						return;
					}
				}
				else
				{
					if(GameSystem.CheckReverse(player))
					{
						e.setCancelled(true);
						GameSystem.Reverse(player);
						return;
					}
				}
			}
		}
	}
	
	@EventHandler
	public static void onPlayerdeath(PlayerDeathEvent e)
	{
		if(e.getEntity() instanceof Player && GameSystem.checkstart)
		{
			e.setDeathMessage(null);
			Player player = (Player) e.getEntity();
			if(Main.list.contains(player))
			{
				if(GameSystem.mlist.contains(player))
				{
					GameSystem.Murderdead(player);
				}
				else
				{
					GameSystem.Civildead(player);
				}
			}
		}
	}
	
	@EventHandler
	public void onToggleSneak(PlayerToggleSneakEvent e)
	{
		if(Main.list.contains(e.getPlayer()))
		{
			if(GameSystem.checkstart)
			{
				e.setCancelled(true);
				e.getPlayer().sendMessage(MS+"���� ���߿��� �ڵ����� ��ũ���� ���°� �˴ϴ�.");
			}
		}
	}
	
	@EventHandler
	public static void onQuitPlayer(PlayerQuitEvent e)
	{
		if(Main.list.contains(e.getPlayer()))
		{
			GameSystem.Quitplayer(e.getPlayer());
		}
	}
	
	@EventHandler
	public void onFall(EntityDamageEvent e)
	{
		if(e.getEntityType() == EntityType.PLAYER)
		{
			Player player = (Player) e.getEntity();
			if(e.getCause() == DamageCause.FALL && list.contains(player))
			{
				e.setCancelled(true);
			}
		}
	}
	
	@EventHandler
	public void onBlockbreak(BlockBreakEvent e)
	{
		if(Main.list.contains(e.getPlayer()))
		{
			if(GameSystem.checkstart)
			{
				e.setCancelled(true);
				e.getPlayer().sendMessage(MS+"���� ���߿��� ���� �μ� �� �����ϴ�..");
			}
		}
	}
	
	@EventHandler
	public void onBlockPlayer(BlockPlaceEvent e)
	{
		if(Main.list.contains(e.getPlayer()))
		{
			if(GameSystem.checkstart)
			{
				e.setCancelled(true);
				e.getPlayer().sendMessage(MS+"���� ���߿��� ���� ��ġ�� �� �����ϴ�..");
			}
		}
	}
	
	@EventHandler
	public void onOpenChest(InventoryOpenEvent e)
	{
		if(!(e.getPlayer() instanceof Player))
		{
			return;
		}
		Player player = (Player) e.getPlayer();
		if((e.getInventory().getHolder() instanceof Chest || e.getInventory().getHolder() instanceof DoubleChest)&& GameSystem.checkstart && Main.list.contains(player))
		{
			e.setCancelled(true);
			GameSystem.Getitem();
		}
	}
	
	@EventHandler
	public void onDropItem(PlayerDropItemEvent e)
	{
		Player damager = e.getPlayer();
		if(!GameSystem.checkstart || !jlist.containsKey(damager.getName()))
		{
			return;
		}
		if(GameSystem.Getjob(damager.getName()).equalsIgnoreCase("����"))
		{
			if(damager.getItemInHand().hasItemMeta())
			{
				if(damager.getItemInHand().getItemMeta().hasDisplayName())
				{
					if(damager.getItemInHand().getItemMeta().getDisplayName().equalsIgnoreCase(ChatColor.AQUA+"������"))
					{
						e.setCancelled(true);
						damager.sendMessage(MS+"�������� ������ �� �����ϴ�.");
					}
				}
			}
		}
	}
}
