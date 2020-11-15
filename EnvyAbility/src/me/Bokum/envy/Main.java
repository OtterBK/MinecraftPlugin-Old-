package me.Bokum.envy;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.event.player.PlayerToggleSneakEvent;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.java.JavaPlugin;

public class Main extends JavaPlugin implements Listener
{
	public static List<Player> dlist = new ArrayList();
	public static Player envy;
	public static boolean sneakmode = false;
	public static int schtmp = 0;
	public static int schtime = 0;
	public static Plugin plugin;
	public static String MS = ChatColor.RESET+"["+ChatColor.GREEN+"BJ���"+ChatColor.RESET+"] "+ChatColor.GRAY;
	
	public void onEnable()
	{
		getServer().getPluginManager().registerEvents(this, this);
		getLogger().info("���� �ɷ� �÷����� �ε� �Ϸ�");
	}
	
	public void onDisable()
	{
		getLogger().info("���� �ɷ� �÷����� ��ε� �Ϸ�");
	}
	
	
	public boolean onCommand(CommandSender talker, Command commmand, String string, String args[])
	{
		if(talker instanceof Player)
		{
			Player player = (Player) talker;
			if(string.equalsIgnoreCase("ea"))
			{
				if(args.length == 0)
				{
					Helpmessage(player);
					return true;
				}
				else if(args[0].equalsIgnoreCase("set"))
				{
					String target = null;
					if(args.length > 1)
					{
						target = args[1];
					}
					SetEnvy(player, target);
					return true;
				}
				else if(args[0].equalsIgnoreCase("remove"))
				{
					envy = null;
					player.sendMessage(MS+"�ʱ�ȭ �Ϸ�");
					return true;
				}
				else if(args[0].equalsIgnoreCase("sneak"))
				{
					Sneakmode(player);
				}
			}
		}
		return true;
	}
	
	public void Sneakmode(Player player)
	{
		if(sneakmode)
		{
			sneakmode = false;
			Player oplayer[] = Bukkit.getOnlinePlayers();
			for(int i = 0; i < oplayer.length ; i++)
			{
				oplayer[i].setSneaking(false);
			}
			Bukkit.broadcastMessage(MS+player+" ���� �̸�ǥ ���߱� ��带 �����ϴ�.");
		}
		else
		{
			sneakmode = true;
			Player oplayer[] = Bukkit.getOnlinePlayers();
			for(int i = 0; i < oplayer.length ; i++)
			{
				oplayer[i].setSneaking(true);
			}
			Bukkit.broadcastMessage(MS+player+" ���� �̸�ǥ ���߱� ��带 �׽��ϴ�.");
		}
	}
	
	public void SetEnvy(Player player, String target)
	{
		Player oplayer[] = Bukkit.getOnlinePlayers();
		for(int i = 0; i < oplayer.length; i++)
		{
			if(oplayer[i].getName().equalsIgnoreCase(target))
			{
				envy = oplayer[i];
				Bukkit.getScheduler().cancelTask(schtmp);
				player.sendMessage(MS+envy.getName()+" ������ �ɷ� ����ڸ� ���� �߽��ϴ�.");
				return;
			}
		}
	}
	
	public void Helpmessage(Player player)
	{
		player.sendMessage(MS+"/ea set <�г���> - �ɷ��� ������ �÷��̾ �����մϴ�.");
		player.sendMessage(MS+"/ea remove - ������ �÷��̾ �����մϴ�.");
		player.sendMessage(MS+"���� : Bokum");
	}
	
	public void Killdlist()
	{
		for(int i = 0; i < dlist.size(); i++)
		{
			if(!(dlist.get(i).getName().equalsIgnoreCase(envy.getName())))
			{
				dlist.get(i).setHealth(0);
				dlist.get(i).sendMessage(MS+"���θ��� �ɷ����� ����ϼ̽��ϴ�.");
			}
		}
		dlist.clear();
	}	
	
	public void Startcount()
	{
		schtime = 200;
		schtmp = Bukkit.getScheduler().scheduleSyncRepeatingTask(this, new Runnable()
		{
			public void run()
			{
				if(schtime > 0)
				{
					if(schtime % 20 == 0)
					{
						envy.sendMessage(MS+(schtime/20)+"�� �� 8ĭ �������� �ڽ��� ������ ��� �÷��̾ ����մϴ�.");
					}
					schtime--;
				}
				else
				{
					Bukkit.getScheduler().cancelTask(schtmp);
					Killdlist();
					envy.sendMessage(MS+"�ɷ��� ��� �Ǿ����ϴ�.");
				}
				if(dlist.size() < 3)
				{
					Bukkit.getScheduler().cancelTask(schtmp);
					schtime = 0;
					dlist.clear();
					envy.sendMessage(MS+"8ĭ �������� �ڽ��� ������ �÷��̾ 3�� �̸��� �Ǿ� ��ҵ˴ϴ�.");
				}
			}
		}, 0L, 1L);
	}
	
	@EventHandler
	public void onMove(PlayerMoveEvent e)
	{
		if(envy == e.getPlayer())
		{
			if(envy.isDead())
			{
				return;
			}
			Player player = e.getPlayer();
			Player oplayer[] = Bukkit.getOnlinePlayers();
			int cnt = 0;
			for(int i = 0; i < oplayer.length; i++)
			{
				if(!oplayer[i].isDead())
				{
				if(player.getLocation().distance(oplayer[i].getLocation()) < 8L)
				{
					if(!dlist.contains(oplayer[i]))
					{
						dlist.add(oplayer[i]);
					}
					cnt++;
				}
				else if(dlist.contains(oplayer[i]))
				{
					dlist.remove(oplayer[i]);
				}
				}
			}
			if(cnt >= 3)
			{
				if(schtime <= 0)
				{
					Startcount();
				}
			}
		}
	}
	
	@EventHandler
	public void onSneak(PlayerToggleSneakEvent e)
	{
		if(sneakmode)
		{
			e.setCancelled(true);
			e.getPlayer().sendMessage(MS+"�̸�ǥ ����� ��尡 ���������Ƿ� ��ũ���⸦ �� �ʿ䰡 �����ϴ�.");
		}
	}
	
	@EventHandler
	public void onJoin(PlayerJoinEvent e)
	{
		if(envy == null)
		{
			return;
		}
		if(envy.getName().equalsIgnoreCase(e.getPlayer().getName()))
		{
			envy = e.getPlayer();
		}
	}
}
