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
	
	public static String MS = ChatColor.RESET+"["+ChatColor.GREEN+"BJ뱀사"+ChatColor.RESET+"] "+ChatColor.YELLOW;
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
			getLogger().info("시작 지점 또는 로비가 설정되어 있지 않습니다");
		}
		for(int i = 1; i <= Startpos.length; i++)
		{
			try
			{
				Startpos[(i-1)] = new Location(Bukkit.getWorld(getConfig().getString("Start_world"+i)),getConfig().getInt("Start_x"+i),getConfig().getInt("Start_y"+i),getConfig().getInt("Start_z"+i));
			}
			catch(java.lang.IllegalArgumentException e)
			{
				getLogger().info(i+"번째 시작지점이 설정 되어있지 않습니다. 버그 발생의 우려가 있습니다.");
			}
		}
	}
	
	public static void Cancelalltasks()
	{
		Bukkit.getScheduler().cancelTasks(instance);
	}
	
	public static void Startgame()
	{
		Bukkit.broadcastMessage(Main.MS+ChatColor.DARK_RED+"범죄자를 찾아라 "+ChatColor.GRAY+"게임이 곧 시작됩니다.");
		GameSystem.schtime = 5;
		schtmp2 = Bukkit.getScheduler().scheduleSyncRepeatingTask(instance, new Runnable()
				{
					public void run()
					{
						if(GameSystem.schtime > 0)
						{
							Main.SendMessage(Main.MS+ChatColor.DARK_RED+"범죄자를 찾아라 "+ChatColor.GRAY+"게임이 "+ChatColor.AQUA+(GameSystem.schtime*10)+ChatColor.GRAY+" 초 후 시작합니다.");
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
		BarAPI.setMessage("범죄자 선택까지", 30);
		schtmp2 = Bukkit.getScheduler().scheduleSyncRepeatingTask(instance, new Runnable()
				{
					public void run()
					{
						if(GameSystem.schtime > 0)
						{
							Main.SendMessage(Main.MS+"범죄자가 "+ChatColor.GOLD+GameSystem.schtime*10+ChatColor.GRAY+" 초 후 정해집니다.");
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
		Main.SendMessage(Main.MS+ChatColor.AQUA+GameSystem.daycnt+ChatColor.YELLOW+"일째 날의 낮이 되었습니다.");
		Main.SendMessage(Main.MS+"직업을 밝히고 싶은 플레이어에게 투표하세요. /ftm vote <닉네임>");
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
		Main.SendMessage(Main.MS+ChatColor.AQUA+GameSystem.daycnt+ChatColor.YELLOW+"번째 날의 밤이 되었습니다.");
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
			Main.SendMessage(Main.MS+ChatColor.RED+"모든 시민팀이 사망했습니다!!!");
			Main.SendMessage(Main.MS+ChatColor.RED+"살인팀이 승리했습니다!");

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
						Bukkit.broadcastMessage(Main.MS+ChatColor.GREEN+ChatColor.BOLD+"범죄자팀의 승리로 범죄자를 찾아라가 종료 되었습니다.");
				}
			}, 100L);
	}
	
	public static void Civilwin()
	{	
			Main.SendMessage(Main.MS+ChatColor.RED+"모든 범죄자팀이 사망했습니다!!!");
			Main.SendMessage(Main.MS+ChatColor.RED+"시민팀이 승리했습니다!");

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
						Bukkit.broadcastMessage(Main.MS+ChatColor.GREEN+ChatColor.BOLD+"시민팀의 승리로 범죄자를 찾아라가 종료 되었습니다.");
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
		player.sendMessage(MS+"메인로비가 설정 되었습니다.");
	}
	
	public void SaveStartpos(Player player , int posn)
	{
		getConfig().set("Start_world"+posn, player.getLocation().getWorld().getName());
		getConfig().set("Start_x"+posn, player.getLocation().getBlockX());
		getConfig().set("Start_y"+posn, player.getLocation().getBlockY()+1);
		getConfig().set("Start_z"+posn, player.getLocation().getBlockZ());
		saveConfig();
		LoadConfig();
		player.sendMessage(MS+posn+"번째 시작지점이 설정 되었습니다.");
	}
	
	public void SaveJoinpos(Player player)
	{
		getConfig().set("Join_world", player.getLocation().getWorld().getName());
		getConfig().set("Join_x", player.getLocation().getBlockX());
		getConfig().set("Join_y", player.getLocation().getBlockY()+1);
		getConfig().set("Join_z", player.getLocation().getBlockZ());
		saveConfig();
		LoadConfig();
		player.sendMessage(MS+"로비가 설정 되었습니다.");
	}
	
	public void onEnable()
	{
		getServer().getPluginManager().registerEvents(this, this);
		getLogger().info("범죄자를 찾아라 플러그인 로드 완료! - 제작 : Bokum");
		instance = this;
		LoadConfig();
	}
	
	public void onDisable()
	{
		getLogger().info("범죄자를 찾아라 플러그인 언로드 완료");
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
						player.sendMessage(MS+"권한이 없습니다.");
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
						player.sendMessage(MS+"권한이 없습니다.");
						return false;
					}
					GameSystem.Startgame(player);
					return true;
				}
				else if(args[0].equalsIgnoreCase("quit"))
				{
					if(!player.hasPermission("ftm.quit"))
					{
						player.sendMessage(MS+"권한이 없습니다.");
						return false;
					}
					GameSystem.Quitplayer(player);
					return true;
				}
				else if(args[0].equalsIgnoreCase("use"))
				{
					if(!plist.containsKey(player.getName()))
					{
						player.sendMessage(MS+"게임에 참여중이지 않습니다.");
						return true;
					}
					if(!jlist.containsKey(player.getName()))
					{
						player.sendMessage(MS+"직업이 설정되어 있지 않습니다. - 버그로 추정");
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
						player.sendMessage(MS+"권한이 없습니다.");
						return false;
					}
					Jlisting(player);
					return true;
				}
				else if(args[0].equalsIgnoreCase("reload"))
				{
					if(!player.hasPermission("ftm.reload"))
					{
						player.sendMessage(MS+"권한이 없습니다.");
						return false;
					}
					LoadConfig();
					return true;
				}
				else if(args[0].equalsIgnoreCase("stop"))
				{
					if(!player.hasPermission("ftm.stop"))
					{
						player.sendMessage(MS+"권한이 없습니다.");
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
					player.sendMessage("삭제");
				}
			}
		}
		return true;
	}
	
	public void Voteplayer(Player player, String str)
	{
		if(!GameSystem.checkstart)
		{
			player.sendMessage(Main.MS+"게임이 시작되지 않았습니다.");
		}
		else if(!Main.list.contains(player))
		{
			player.sendMessage(Main.MS+"당신은 게임에 참가중이지 않습니다.");
		}
		else if(!GameSystem.isday)
		{
			player.sendMessage(Main.MS+"투표는 낮에만 가능합니다.");
		}
		else if(str == null)
		{
			player.sendMessage(MS+"이름을 입력해주세요.");
		}
		else if(!plist.containsKey(str))
		{
			player.sendMessage(MS+str+" 님은 게임에 참여중이 아닙니다.");
		}
		else if(vplist.contains(player))
		{
			player.sendMessage(MS+str+" 이미 투표 하셨습니다.");
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
			if(GameSystem.Getjob(player.getName()).equalsIgnoreCase("모범시민"))
			{
				vlist.put(str, vlist.get(str)+1);
				player.sendMessage(MS+"능력의 효과로 2표의 효과를 발휘합니다.");
			}
			player.sendMessage(MS+str+" 님을 투표했습니다.");
			SendMessage(MS+"누군가가 "+ChatColor.AQUA+str+ChatColor.YELLOW+" 님에게 투표했습니다.");
		}
	}
	
	public void Jobhelp(Player player)
	{
		if(!jlist.containsKey(player.getName()))
		{
			player.sendMessage(Main.MS+"직업이 없습니다.");
		}
		else
		{
			switch(GameSystem.Getjob(player.getName()))
			{
				case "범죄자": Help_Murder(player);return;
				case "의사": Help_Doctor(player);return;
				case "경찰": Help_Police(player);return;
				case "군인": Help_Soldier(player);return;
				case "스파이": Help_Spy(player);return;
				case "보디가드": Help_Guardian(player);return;
				case "마술사": Help_Magician(player);return;
				case "모범시민": Help_Nice_Civilian(player);return;
				case "시민": Help_Civilian(player);return;
				case "탐정": Help_Detective(player);return;
				case "기자": Help_Reporter(player);return;
				case "암살자": Help_Assasin(player);return;
				default: player.sendMessage("없는 직업입니다. - 버그 의심"); return;
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
		player.sendMessage(ChatColor.GOLD+"당신의 능력 : "+ChatColor.AQUA+GameSystem.Getjob(player.getName()));
		player.sendMessage(ChatColor.GRAY+"당신은 강력한 무기를 가지고 있습니다. 이 무기를 이용하여 모든 시민팀을 죽여야합니다.");
		player.sendMessage(ChatColor.GRAY+"스파이가 능력을 사용하여 당신과 접선시 스파이는 동료가 됩니다.");
		player.sendMessage(ChatColor.GRAY+"또한 암살자가 시민을 사살할시 당신의 동료가 됩니다.");
	}
	
	public void Help_Doctor(Player player)
	{
		player.sendMessage(ChatColor.GOLD+"당신의 능력 : "+ChatColor.AQUA+GameSystem.Getjob(player.getName()));
		player.sendMessage(ChatColor.GRAY+"당신은 밤마다 1명을 지정할 수 있습니다.");
		player.sendMessage(ChatColor.GRAY+"이 지정한 플레이어는 그 밤과 다음날 낮에 사망시 한번만 부활하며 ");
		player.sendMessage(ChatColor.GRAY+"다음 밤이 찾아온다면 다시 플레이어를 지정해야 합니다.");
		player.sendMessage(ChatColor.GRAY+"능력 사용방법 /ftm use <닉네임>");
	}
	
	public void Help_Police(Player player)
	{
		player.sendMessage(ChatColor.GOLD+"당신의 능력 : "+ChatColor.AQUA+GameSystem.Getjob(player.getName()));
		player.sendMessage(ChatColor.GRAY+"당신은 낮마다 1명을 조사할 수 있습니다.");
		player.sendMessage(ChatColor.GRAY+"조사한 플레이어가 범죄자일 경우 ");
		player.sendMessage(ChatColor.GRAY+"그 플레이어가 범죄자라는 메세지가 당신에게 전해집니다.");
		player.sendMessage(ChatColor.GRAY+"능력 사용방법 /ftm use <닉네임>");
	}
	
	public void Help_Soldier(Player player)
	{
		player.sendMessage(ChatColor.GOLD+"당신의 능력 : "+ChatColor.AQUA+GameSystem.Getjob(player.getName()));
		player.sendMessage(ChatColor.GRAY+"당신은 단련된 신체를 가졌습니다.");
		player.sendMessage(ChatColor.GRAY+"사망시 한번만 부활하게 됩니다. ");
		player.sendMessage(ChatColor.GRAY+"부활후에는 직업이 시민으로 변경됩니다.");
	}
	
	public void Help_Spy(Player player)
	{
		player.sendMessage(ChatColor.GOLD+"당신의 능력 : "+ChatColor.AQUA+GameSystem.Getjob(player.getName()));
		player.sendMessage(ChatColor.GRAY+"밤마다 1명의 플레이어와 접선이 가능합니다.");
		player.sendMessage(ChatColor.GRAY+"만약 접선한 플레이어가 범죄자라면 당신은 범죄자의 동료가 되며");
		player.sendMessage(ChatColor.GRAY+"시민팀을 죽이고 다닐 수 있습니다.");
		player.sendMessage(ChatColor.GRAY+"만약 접선한 플레이어가 범죄자가 아니라면 당신은 그 플레이어의 직업을 알 수 있습니다.");
		player.sendMessage(ChatColor.GRAY+"능력 사용방법 /ftm use <닉네임>");
	}
	
	public void Help_Guardian(Player player)
	{
		player.sendMessage(ChatColor.GOLD+"당신의 능력 : "+ChatColor.AQUA+GameSystem.Getjob(player.getName()));
		player.sendMessage(ChatColor.GRAY+"밤마다 두명의 플레이어를 지정하실 수 있습니다.");
		player.sendMessage(ChatColor.GRAY+"이 지정한 플레이어는 다음날 낮에 투표로 인하여 직업이 밝혀지지 않습니다.");
		player.sendMessage(ChatColor.GRAY+"능력 사용방법 /ftm use <닉네임>");
	}
	
	public void Help_Magician(Player player)
	{
		player.sendMessage(ChatColor.GOLD+"당신의 능력 : "+ChatColor.AQUA+GameSystem.Getjob(player.getName()));
		player.sendMessage(ChatColor.GRAY+"밤일 때 단 한번만 능력을 사용할 수 있으며");
		player.sendMessage(ChatColor.GRAY+"사용한 플레이어의 직업을 빼앗아 버립니다.");
		player.sendMessage(ChatColor.GRAY+"사용 대상의 직업이 범죄자라면 그 플레이어는 사망하게 됩니다.");
		player.sendMessage(ChatColor.GRAY+"사용 대상의 직업이 스파이, 암살자라면 빼앗는 것이 아닌 복사를 합니다.");
		player.sendMessage(ChatColor.GRAY+"능력 사용방법 /ftm use <닉네임>");
	}
	
	public void Help_Nice_Civilian(Player player)
	{
		player.sendMessage(ChatColor.GOLD+"당신의 능력 : "+ChatColor.AQUA+GameSystem.Getjob(player.getName()));
		player.sendMessage(ChatColor.GRAY+"투표시 당신의 투표는 2표로 취급되며 시작부터");
		player.sendMessage(ChatColor.GRAY+"자신의 직업이 직업리스트에 보여집니다.");
		player.sendMessage(ChatColor.GRAY+"당신은 누구에게도 의심받지 않을 것 입니다.");
	}
	
	public void Help_Civilian(Player player)
	{
		player.sendMessage(ChatColor.GOLD+"당신의 능력 : "+ChatColor.AQUA+GameSystem.Getjob(player.getName()));
		player.sendMessage(ChatColor.GRAY+"당신은 특별한 능력을 지니지 않았습니다.");
		player.sendMessage(ChatColor.GRAY+"또한 암살자는 언제나 당신을 노릴 것입니다.");
		player.sendMessage(ChatColor.GRAY+"조심하세요.");
	}
	
	public void Help_Detective(Player player)
	{
		player.sendMessage(ChatColor.GOLD+"당신의 능력 : "+ChatColor.AQUA+GameSystem.Getjob(player.getName()));
		player.sendMessage(ChatColor.GRAY+"낮마다 한명의 플레이어를 지정할 수 있습니다.");
		player.sendMessage(ChatColor.GRAY+"밤이 되었을 때 /ftm use 를 입력하시면");
		player.sendMessage(ChatColor.GRAY+"그 플레이어가 지금 들고있는 무기, 좌표, 체력 정보를 수시로");
		player.sendMessage(ChatColor.GRAY+"확인하실 수 있습니다.");
		player.sendMessage(ChatColor.GRAY+"능력 사용방법 /ftm use <닉네임>");
	}
	
	public void Help_Reporter(Player player)
	{
		player.sendMessage(ChatColor.GOLD+"당신의 능력 : "+ChatColor.AQUA+GameSystem.Getjob(player.getName()));
		player.sendMessage(ChatColor.GRAY+"밤일 때 한번만 능력을 사용할 수 있으며");
		player.sendMessage(ChatColor.GRAY+"능력을 사용한 플레이어의 직업을 모두에게");
		player.sendMessage(ChatColor.GRAY+"알립니다. 또한 직업리스트에 그 플레이어의 직업이");
		player.sendMessage(ChatColor.GRAY+"공개됩니다.");
		player.sendMessage(ChatColor.GRAY+"능력 사용방법 /ftm use <닉네임>");
	}
	
	public void Help_Assasin(Player player)
	{
		player.sendMessage(ChatColor.GOLD+"당신의 능력 : "+ChatColor.AQUA+GameSystem.Getjob(player.getName()));
		player.sendMessage(ChatColor.GRAY+"당신은 평범한 시민과 같습니다.");
		player.sendMessage(ChatColor.GRAY+"하지만 타겟이 사망하거나 게임에서 나갔을 시");
		player.sendMessage(ChatColor.GRAY+"당신은 범죄자의 동료가 되며 시민팀을 죽일 수 있습니다.");
		player.sendMessage(ChatColor.GRAY+"당신의 타겟 : "+ChatColor.RED+GameSystem.astarget);
		player.sendMessage(ChatColor.GRAY+"이 플레이어가 누구에게든 살해당하면 됩니다.");
	}
	
	public void HelpMessage(Player player)
	{
		player.sendMessage(MS+ChatColor.AQUA+"/ftm help - 능력의 설명을 봅니다.");
		player.sendMessage(MS+ChatColor.AQUA+"/ftm use <닉네임> - 해당 대상에게 능력을 사용합니다.");
		player.sendMessage(MS+ChatColor.AQUA+"/ftm list - 직업 리스트를 봅니다.");
		player.sendMessage(MS+"/ftm start - 게임을 시작시킵니다..");
		player.sendMessage(MS+"/ftm quit - 게임에서 퇴장합니다..");
		player.sendMessage(MS+"/ftm set lobby - 서있는 지점을 메인로비로 설정합니다.");
		player.sendMessage(MS+"/ftm set join- 서있는 지점을 로비로 설정합니다.");
		player.sendMessage(MS+"/ftm set start 1~15 - 서있는 지점을 시작지점으로 설정합니다.");
		player.sendMessage(MS+"/ftm reload - config를 리로드 합니다.");
		player.sendMessage(MS+"/ftm stop - 게임을 강제 종료 합니다.");
		player.sendMessage(MS+"버젼 1.34");
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
		player.sendMessage(MS+"직업 리스트 (인원 "+glist.size()+"명): ");
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
			player.sendMessage(MS+"직업 리스트 :");
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
			if(GameSystem.Getjob(damager.getName()).equalsIgnoreCase("경찰"))
			{
				if(damager.getItemInHand().hasItemMeta())
				{
					if(damager.getItemInHand().getItemMeta().hasDisplayName())
					{
						if(damager.getItemInHand().getItemMeta().getDisplayName().equalsIgnoreCase(ChatColor.AQUA+"경찰봉"))
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
							player.sendMessage(ChatColor.RED+"경찰봉에 맞았습니다!!!");
							damager.sendMessage(ChatColor.RED+"경찰봉을 사용 하였습니다.");
						}
					}
				}
			}
			if(GameSystem.mlist.contains(player) && GameSystem.mlist.contains(damager))
			{
				player.sendMessage(Main.MS+"같은 범죄자팀인 "+damager.getName()+"님이 당신을 공격했습니다! ");
				damager.sendMessage(Main.MS+player.getName()+" 님은 같은 범죄자팀 입니다! 조심해주세요!");
			}
			int damage = e.getDamage();
			if(player.getHealth() - damage <= 0 && !player.isDead())
			{
				if(!(GameSystem.mlist.contains(player)) && !(GameSystem.Getjob(player.getName()).equalsIgnoreCase("암살자")) && !(GameSystem.Getjob(player.getName()).equalsIgnoreCase("스파이")))
				{
					if(!GameSystem.mlist.contains(damager))
					{
						if(!(GameSystem.Getjob(damager.getName()).equalsIgnoreCase("암살자") && GameSystem.astarget.equalsIgnoreCase(player.getName())))
						{
							e.setCancelled(true);
							GameSystem.Removeplayerfl(damager);
							damager.sendMessage(Main.MS+"당신은 무고한 플레이어를 죽여버려 처형 되였습니다.");
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
				e.getPlayer().sendMessage(MS+"게임 도중에는 자동으로 웅크리기 상태가 됩니다.");
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
				e.getPlayer().sendMessage(MS+"게임 도중에는 블럭을 부술 수 없습니다..");
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
				e.getPlayer().sendMessage(MS+"게임 도중에는 블럭을 설치할 수 없습니다..");
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
		if(GameSystem.Getjob(damager.getName()).equalsIgnoreCase("경찰"))
		{
			if(damager.getItemInHand().hasItemMeta())
			{
				if(damager.getItemInHand().getItemMeta().hasDisplayName())
				{
					if(damager.getItemInHand().getItemMeta().getDisplayName().equalsIgnoreCase(ChatColor.AQUA+"경찰봉"))
					{
						e.setCancelled(true);
						damager.sendMessage(MS+"경찰봉은 버리실 수 없습니다.");
					}
				}
			}
		}
	}
}
