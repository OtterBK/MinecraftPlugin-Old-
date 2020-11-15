package me.Bokum.PickingCoin;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityRegainHealthEvent;
import org.bukkit.event.entity.EntityRegainHealthEvent.RegainReason;
import org.bukkit.plugin.java.JavaPlugin;

public class Main extends JavaPlugin implements Listener
{
	public static int test = 0;
	public static Main instance;
	
	public void onEnable()
	{
		getServer().getPluginManager().registerEvents(this, this);
		instance = this;
		LoadConfig();
		getLogger().info("동전줍는 징징이 플러그인 로드 완료!");
	}
	
	public void onDisable()
	{
		getLogger().info("동전줍는 징징이 플러그인 언로드 완료!");
	}
	
	public boolean onCommand(CommandSender talker, Command command, String string, String[] args)
	{
		if(talker instanceof Player)
		{
			Player player = (Player) talker;
			if(string.equalsIgnoreCase("pcj"))
			{
				if(args.length <= 0)
				{
					System.HelpMessages(player);
				}
				else if(args[0].equalsIgnoreCase("set"))
				{
					Setpos(player, args);
				}
				else if(args[0].equalsIgnoreCase("join"))
				{
					System.Joingame(player);
				}
			}
		}
		return false;
	}

	public void Setpos(Player player, String[] args)
	{
		if(args.length == 1)
		{
			player.sendMessage(System.MS+"/pcj set <lobby/join/cstart/rstart>");
		}
		else if(args[1].equalsIgnoreCase("lobby"))
		{
			SetLobby(player);
		}
		else if(args[1].equalsIgnoreCase("join"))
		{
			SetJoin(player);
		}
		else if(args[1].equalsIgnoreCase("cstart"))
		{
			SetCatcherStart(player);
		}
		else if(args[1].equalsIgnoreCase("rstart"))
		{
			SetRunnerStart(player);
		}
	}
	
	public void LoadConfig()
	{
		String str = "Lobby_";
		System.Lobby = new Location(getServer().getWorld(getConfig().getString(str+"world")),getConfig().getInt(str+"X"),getConfig().getInt(str+"Y"),getConfig().getInt(str+"Z"));
		str = "Join_";
		System.Join = new Location(getServer().getWorld(getConfig().getString(str+"world")),getConfig().getInt(str+"X"),getConfig().getInt(str+"Y"),getConfig().getInt(str+"Z"));
		str = "CatcherStart_";
		System.CatcherStart = new Location(getServer().getWorld(getConfig().getString(str+"world")),getConfig().getInt(str+"X"),getConfig().getInt(str+"Y"),getConfig().getInt(str+"Z"));
		str = "RunnerStart_";
		System.RunnerStart = new Location(getServer().getWorld(getConfig().getString(str+"world")),getConfig().getInt(str+"X"),getConfig().getInt(str+"Y"),getConfig().getInt(str+"Z"));
	}
	
	public void SetLobby(Player player)
	{
		getConfig().set("Lobby_world", player.getLocation().getWorld().getName());
		getConfig().set("Lobby_X", player.getLocation().getBlockX());
		getConfig().set("Lobby_Y", player.getLocation().getBlockY()+1);
		getConfig().set("Lobby_Z", player.getLocation().getBlockX());
		saveConfig();
		LoadConfig();
	}
	
	public void SetJoin(Player player)
	{
		getConfig().set("Join_world", player.getLocation().getWorld().getName());
		getConfig().set("Join_X", player.getLocation().getBlockX());
		getConfig().set("Join_Y", player.getLocation().getBlockY()+1);
		getConfig().set("Join_Z", player.getLocation().getBlockX());
		saveConfig();
		LoadConfig();
	}
	
	public void SetCatcherStart(Player player)
	{
		getConfig().set("CatcherStart_world", player.getLocation().getWorld().getName());
		getConfig().set("CatcherStart_X", player.getLocation().getBlockX());
		getConfig().set("CatcherStart_Y", player.getLocation().getBlockY()+1);
		getConfig().set("CatcherStart_Z", player.getLocation().getBlockX());
		saveConfig();
		LoadConfig();
	}
	
	public void SetRunnerStart(Player player)
	{
		getConfig().set("RunnerStart_world", player.getLocation().getWorld().getName());
		getConfig().set("RunnerStart_X", player.getLocation().getBlockX());
		getConfig().set("RunnerStart_Y", player.getLocation().getBlockY()+1);
		getConfig().set("RunnerStart_Z", player.getLocation().getBlockX());
		saveConfig();
		LoadConfig();
	}
	
	public static void StartGame()
	{
		System.lobbycheck = true;
		Bukkit.broadcastMessage(System.MS+ChatColor.GOLD+"동전줍는 징징이"+ChatColor.YELLOW+"가 곧 시작됩니다.");
		System.lobbyschtime = 5;
		System.lobbysch = Bukkit.getScheduler().scheduleSyncRepeatingTask(instance,new Runnable()
		{
			public void run()
			{
				if(System.lobbyschtime > 0)
				{
					System.SendMessage(System.MS+"게임이 "+ChatColor.RED+(System.lobbyschtime*10)+ChatColor.YELLOW+" 초 후 시작됩니다.");
					System.lobbyschtime--;
				}
				else
				{
					Bukkit.getScheduler().cancelTask(System.lobbysch);
					StartTimer();
				}
			}
		}, 200L, 200L);
	}
	
	public static void StartTimer()
	{
		System.StartTP();
		System.gamesch = Bukkit.getScheduler().scheduleSyncDelayedTask(instance, new Runnable()
		{
			public void run()
			{
				Bukkit.getScheduler().cancelTask(System.gamesch);
				System.SendStartMessage();
				Timer();
			}
		}, 140L);
	}
	
	public static void Timer()
	{
		System.gameschtime = 6;
		System.gamesch = Bukkit.getScheduler().scheduleSyncRepeatingTask(instance, new Runnable()
		{
			public void run()
			{
				if(System.gameschtime >= 1)
				{
					System.SendMessage(System.MS+ChatColor.RED+System.gameschtime+ChatColor.GRAY+" 분 남았습니다.");
					System.gameschtime--;
				}
				else
				{
					Bukkit.getScheduler().cancelTask(System.gamesch);
					FinishTimer();
				}
			}
		}, 100L, 1200L);
	}
	
	public static void FinishTimer()
	{
		System.gameschtime = 10;
		System.gamesch = Bukkit.getScheduler().scheduleSyncRepeatingTask(instance, new Runnable()
		{
			public void run()
			{
				if(System.gameschtime > 0)
				{
					System.SendMessage(System.MS+System.gameschtime+" 초 남았습니다.");
					System.gameschtime--;
				}
				else
				{
					Bukkit.getScheduler().cancelTask(System.gamesch);
					CatcherWin();
				}
			}
		}, 0L, 20L);
	}
	
	@EventHandler
	public void onChangeHealth(EntityRegainHealthEvent e)
	{
		if(System.gamecheck && e.getEntity() instanceof Player && e.getRegainReason() == RegainReason.SATIATED || e.getRegainReason() == RegainReason.REGEN)
		{
			Player player = (Player) e.getEntity();
			if(System.list.contains(player))
			{
				e.setCancelled(true);
			}
		}
	}
}
