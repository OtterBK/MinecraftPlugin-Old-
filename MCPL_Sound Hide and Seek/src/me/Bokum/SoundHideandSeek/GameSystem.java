package me.Bokum.SoundHideandSeek;



import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.plugin.java.JavaPlugin;

public class GameSystem extends JavaPlugin implements Listener
{
	public static void Seekerfail(Player player)
	{
		Main.clist.remove(player);
		Main.plist.remove(player);
		player.sendMessage(Main.shs+"����� Ż�� �߽��ϴ�.");
		player.teleport(Main.lobby);
		Main.Sendmessage(Main.shs+player.getName()+"�Բ��� Ż�� �ϼ̽��ϴ�.");
		if(Main.clist.size() <= 0)
		{
			HiderWin();
		}
	}
	
	public static void Hiderfail(Player player)
	{
		Main.hlist.remove(player);
		Main.plist.remove(player);
		player.sendMessage(Main.shs+"����� Ż�� �߽��ϴ�.");
		player.teleport(Main.lobby);
		Main.Sendmessage(Main.shs+player.getName()+"�Բ��� Ż�� �ϼ̽��ϴ�.");
		if(Main.hlist.size() <= 0)
		{
			SeekerWin();
		}
	}
	
	public static void QuitLobby(Player player)
	{
		String message = Main.shs+ChatColor.BLUE+player.getName()+ChatColor.GRAY+" ���� ���� �ϼ̽��ϴ�."+ChatColor.GREEN+" ( "+ChatColor
				.BLUE+Main.plist.size()+"/"+ Main.MinPlayer+ChatColor.GREEN+" )";
		Main.Sendmessage(message);
		Main.plist.remove(player);
	}
	
	public void onQuit(PlayerQuitEvent e)
	{
		if(!Main.Startcheck)
		{
			QuitLobby(e.getPlayer());
			return;
		}
		if(Main.clist.contains(e.getPlayer()))
		{
			Seekerfail(e.getPlayer());
		}
		else
		{
			Hiderfail(e.getPlayer());
		}
	}
	
	public static void Cleardata()
	{
		Main.Startcheck = false;
		Main.timer = 60;
		Main.Schtmp = 0;
		Main.plist.clear();
		Main.clist.clear();
		Main.hlist.clear();
	}
	
	public static void Allspawn()
	{
		for(int i = 0; i < Main.plist.size(); i++)
		{
			Main.plist.get(i).teleport(Main.lobby);
		}
	}
	
	public static void SeekerWin()
	{
		Main.Sendmessage(Main.shs+"��� �����ڸ� ��ҽ��ϴ�.");
		Main.Sendmessage(Main.shs+"�������� �¸��Դϴ�!!!");
		//Main.timer = 0;
		Main.Schtmp = Bukkit.getServer().getScheduler().scheduleSyncRepeatingTask(Main.maininstance, new Runnable()
		{
			public void run()
			{
				for(int i = 0; i < Main.plist.size(); i++)
				{
					Main.plist.get(i).teleport(Main.lobby);
				}
				Bukkit.getServer().getScheduler().cancelTask(Main.Schtmp);
				Bukkit.broadcastMessage(Main.shs+ChatColor.AQUA+"�������� �¸��� ��� ���ٲ����� ���� �Ǿ����ϴ�.");
				Cleardata();
				/*
				if(Main.timer > 0)
				{
					Main.timer--;
				}
				else
				{
					Bukkit.getServer().getScheduler().cancelAllTasks();
				}*/
			}
		}, 100L, 20L);
	}

	public static void HiderWin()
	{
		Main.Sendmessage(Main.shs+"�� �̻� ������ �������� �ʽ��ϴ�.");
		Main.Sendmessage(Main.shs+"������ ���� �¸��Դϴ�!");
		//Main.timer = 0;
		Main.Schtmp = Bukkit.getServer().getScheduler().scheduleSyncRepeatingTask(Main.maininstance, new Runnable()
				{
					public void run()
					{
						for(int i = 0; i < Main.plist.size(); i++)
						{
							Main.plist.get(i).teleport(Main.lobby);
						}
						Bukkit.getServer().getScheduler().cancelTask(Main.Schtmp);
						Bukkit.broadcastMessage(Main.shs+ChatColor.AQUA+"���������� �¸��� ��� ���ٲ����� ���� �Ǿ����ϴ�.");
						Cleardata();
						/*
						if(Main.timer > 0)
						{
							Main.timer--;
						}
						else
						{
							Bukkit.getServer().getScheduler().cancelAllTasks();
						}
						*/
					}
				}, 100L, 20L);
	}
	
	public static void Joinplayer(Player player)
	{
		if(Main.Startcheck)
		{
			player.sendMessage(Main.shs+ChatColor.RED+"�̹� ������ ���� �Ǿ����ϴ�.");
			return;
		}
		if(Main.plist.contains(player))
		{
			player.sendMessage(Main.shs+ChatColor.RED+"�̹� ������ �����Ͻ� �����Դϴ�.");
			return;
		}
			Main.plist.add(player);
			player.setSneaking(true);
			String message = Main.shs+ChatColor.BLUE+player.getName()+ChatColor.GRAY+" ���� ���� �ϼ̽��ϴ�."+ChatColor.GREEN+" ( "+ChatColor
					.BLUE+Main.plist.size()+"/"+ Main.MinPlayer+ChatColor.GREEN+" )";
			Main.Sendmessage(message);
			Main.Checkstart();
	}
	
	public static void Quitplayer(Player player)
	{
		if(!Main.Startcheck)
		{
			QuitLobby(player);
			return;
		}
		if(Main.clist.contains(player))
		{
			Seekerfail(player);
		}
		else
		{
			Hiderfail(player);
		}
	}
}

