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
		getLogger().info("방울 소리 플러그인 로드 완료! - 제작 : Bokum");
		Loadconfig();
		maininstance = this;
	}
	
	public void onDisable()
	{
		getLogger().info("방울 소리 플러그인 언로드");
	}
	
	public void Helpmessages(Player player) 
	{
		player.sendMessage(shs+"/shs join <닉네임> - <닉네임> 게임에 참여합니다.");
		player.sendMessage(shs+"/shs quit <닉네임> - <닉네임> 게임에서 퇴장 합니다.");
		player.sendMessage(shs+"/shs start <닉네임> - 게임을 강제 시작합니다.");
		player.sendMessage(shs+"/shs clear - 게임을 강제 종료 합니다.");;
		player.sendMessage(ChatColor.BLUE+"제작 - Bokum" );
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
						Bukkit.broadcastMessage(shs+ChatColor.GOLD+"방울 숨바꼭질"
								+ChatColor.GRAY+"게임이 강제 종료 되었습니다.");
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
			Bukkit.getServer().broadcastMessage(shs+ChatColor.GOLD+"방울 숨바꼭질 게임"+ChatColor.BLUE+"이"+ChatColor.GOLD+" 60초 후"+ChatColor.BLUE+" 시작 됩니다.");
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
					String string = shs+ChatColor.GRAY+"게임이 "+ChatColor.RED+timer+ChatColor.GRAY+" 초 후에 시작됩니다.";
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
		Bukkit.getServer().broadcastMessage(shs+ChatColor.GOLD+"방울 숨바꼭질"+ChatColor.GRAY+" 게임이 시작됐습니다. 플레이 인원 :" + ChatColor.BLUE+plist.size());
		timer = 50;
		Schtmp = Bukkit.getServer().getScheduler().scheduleSyncRepeatingTask(maininstance, new Runnable()
				{
				public void run()
				{
					if(timer > 0)
					{
						String string = shs+ChatColor.GOLD+timer+ChatColor.GRAY+" 초 후에 술래가 선택 됩니다.";
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
		Sendmessage(shs+ChatColor.RED+plist.get(random).getName()+ChatColor.GRAY+" 님이 술래로 정해졌습니다.");
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
			player.sendMessage(shs+ChatColor.RED+"방울 숨바꼭질 중에는 자동으로 웅크린상태가 고정 됩니다.");
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
