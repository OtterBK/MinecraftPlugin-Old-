package me.Bokum.SoundHideandSeek;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.Sound;
import org.bukkit.World;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.event.player.PlayerToggleSneakEvent;
import org.bukkit.plugin.java.JavaPlugin;


public class Main extends JavaPlugin implements Listener
{
	public static String shs = ChatColor.GREEN+"[EG] ";
	public static int MaxPlayer = 12;
	public static int MinPlayer = 4;
	public static boolean Startcheck = false;
	public static int timer = 60;
	public static int Schtmp = 0;
	public static List<Player> plist = new ArrayList();
	public static List<Player> clist = new ArrayList();
	public static List<Player> hlist = new ArrayList();
	public static Location lobby;
	public static Main maininstance;
	public static Location startloc;
	
	
	public void Loadconfig()
	{
		lobby = new Location(Bukkit.getServer().getWorld(getConfig().getString("lobby_world"))
				,getConfig().getInt("lobby_x"),getConfig().getInt("lobby_y"),getConfig().getInt("lobby_z"));
		startloc = new Location(Bukkit.getServer().getWorld(getConfig().getString("loc_world"))
				,getConfig().getInt("loc_x"),getConfig().getInt("loc_y"),getConfig().getInt("loc_z"));
	}
	
	public void onEnable()
	{
		getServer().getPluginManager().registerEvents(this, this);
		getLogger().info("��� �Ҹ� �÷����� �ε� �Ϸ�! - ���� : Bokum");
		Loadconfig();
		maininstance = this;
	}
	
	public void onDisable()
	{
		getLogger().info("��� �Ҹ� �÷����� ��ε�");
	}
	
	public void Helpmessages(Player player) 
	{
		player.sendMessage(shs+"/shs join <�г���> - <�г���> ���ӿ� �����մϴ�.");
		player.sendMessage(shs+"/shs quit <�г���> - <�г���> ���ӿ��� ���� �մϴ�.");
		player.sendMessage(shs+"/shs start <�г���> - ������ ���� �����մϴ�.");
		player.sendMessage(shs+"/shs clear - ������ ���� ���� �մϴ�.");;
		player.sendMessage(ChatColor.BLUE+"���� - Bokum" );
	}
	
	public boolean onCommand(CommandSender talker, Command command, String string,String args[])
	{
		if(talker instanceof Player)
		{
			Player player = (Player) talker;
			if(string.equalsIgnoreCase("shs"))
			{
				if(args.length >= 1)
				{
					if(args[0].equalsIgnoreCase("join"))
					{
						GameSystem.Joinplayer(player);
						return true;
					}
					if(args[0].equalsIgnoreCase("quit"))
					{
						GameSystem.Quitplayer(player);
						return true;
					}
					if(args[0].equalsIgnoreCase("start"))
					{
						GameCountdown();
						return true;
					}
					if(args[0].equalsIgnoreCase("clear"))
					{
						GameSystem.Cleardata();
						GameSystem.Allspawn();
						Bukkit.broadcastMessage(shs+ChatColor.GOLD+"��� ���ٲ���"
								+ChatColor.GRAY+"������ ���� ���� �Ǿ����ϴ�.");
						return true;
					}
				}
				Helpmessages(player);
				return true;
			}
		}
		return true;
	}
	
	public static void Sendmessage(String string)
	{
		for(int i = 0; i < plist.size(); i++)
		{
			Player player = plist.get(i);
			player.sendMessage(string);
		}
	}
	
	public static void Checkstart()
	{
		if(plist.size() >= MinPlayer && !Startcheck)
		{
			Startcheck = true;
			Bukkit.getServer().broadcastMessage(shs+ChatColor.GOLD+"��� ���ٲ��� ����"+ChatColor.BLUE+"��"+ChatColor.GOLD+" 60�� ��"+ChatColor.BLUE+" ���� �˴ϴ�.");
			GameCountdown();
		}
	}
	
	public static void GameCountdown()
	{
		Startcheck = true;
		timer = 60;
		Schtmp = Bukkit.getServer().getScheduler().scheduleSyncRepeatingTask(maininstance, new Runnable()
		{
			public void run()
			{
				if(timer > 0)
				{
					String string = shs+ChatColor.GRAY+"������ "+ChatColor.RED+timer+ChatColor.GRAY+" �� �Ŀ� ���۵˴ϴ�.";
					Sendmessage(string);
					timer -= 10;
				}
				else
				{
					Bukkit.getScheduler().cancelTask(Schtmp);
					Startgame();
				}
			}
		}, 0L, 200L);
	}
	
	public static void Startgame()
	{
		GameStart();
		Bukkit.getServer().broadcastMessage(shs+ChatColor.GOLD+"��� ���ٲ���"+ChatColor.GRAY+" ������ ���۵ƽ��ϴ�. �÷��� �ο� :" + ChatColor.BLUE+plist.size());
		timer = 50;
		Schtmp = Bukkit.getServer().getScheduler().scheduleSyncRepeatingTask(maininstance, new Runnable()
				{
				public void run()
				{
					if(timer > 0)
					{
						String string = shs+ChatColor.GOLD+timer+ChatColor.GRAY+" �� �Ŀ� ������ ���� �˴ϴ�.";
						Sendmessage(string);
						timer -= 10;
					}
					else
					{
						Bukkit.getServer().getScheduler().cancelTask(Schtmp);
						SelectCatcher();
						if(plist.size() > 6)
						{
							SelectCatcher();
						}
						if(plist.size() > 10)
						{
							SelectCatcher();
						}
					}
				}
				}, 0L, 200L);
	}
	
	public static void GameStart()
	{	
		for(int i = 0; i < plist.size(); i++)
		{
			Player playertmp = plist.get(i);
			playertmp.teleport(startloc);
		}
	}
	
	public static void SelectCatcher()
	{
		int random = (int)(Math.random()*plist.size());
		if(!(clist.contains(plist.get(random))))
		{
			clist.add(plist.get(random));
		}
		else
		{
			SelectCatcher();
			return;
		}
		Sendmessage(shs+ChatColor.RED+plist.get(random).getName()+ChatColor.GRAY+" ���� ������ ���������ϴ�.");
		Bukkit.getServer().dispatchCommand(plist.get(random).getServer().getConsoleSender(), "kit shsseeker"+plist.get(random).getName());
	}
	
	@EventHandler
	public void onMove(PlayerMoveEvent event)
	{
		if(!clist.contains(event.getPlayer()))
		{
			return;
		}
		if(event.getFrom().getBlockX()== event.getTo().getBlockX()&&
				event.getFrom().getBlockY()==event.getTo().getBlockY()&&
				event.getFrom().getBlockZ()==event.getTo().getBlockZ())
		{
			return;
		}
		Player player = event.getPlayer();
		if(event.getFrom().getBlockY() != event.getTo().getBlockY())
		{
			player.getLocation().getWorld().playSound(player.getLocation(), Sound.ORB_PICKUP, 0.58F, 0.7F);
			return;
		}
		/*if(player.isSneaking())
		{
			player.getLocation().getWorld().playSound(player.getLocation(), Sound.ORB_PICKUP, 0.04F, 0.7F);
			return;
		}*/
		if(player.isSprinting())
		{
			player.getLocation().getWorld().playSound(player.getLocation(), Sound.ORB_PICKUP, 0.32F, 0.7F);
		}
		else
		{
			player.getLocation().getWorld().playSound(player.getLocation(), Sound.ORB_PICKUP, 0.16F, 0.7f);
		}
	}
	
	@EventHandler
	public void SetSneak(PlayerToggleSneakEvent e)
	{
		Player player = e.getPlayer();
		if(plist.contains(player))
		{
			player.sendMessage(shs+ChatColor.RED+"��� ���ٲ��� �߿��� �ڵ����� ��ũ�����°� ���� �˴ϴ�.");
			e.setCancelled(true);
		}
	}
	
	@EventHandler
	public void onPlayerdeath(PlayerDeathEvent e)
	{
		if(!Main.Startcheck)
		{
			GameSystem.QuitLobby((Player)e.getEntity());
			return;
		}
		if(Main.clist.contains((Player)e.getEntity()))
		{
			GameSystem.Seekerfail((Player)e.getEntity());
		}
		else
		{
			GameSystem.Hiderfail((Player)e.getEntity());
		}
	}
}
