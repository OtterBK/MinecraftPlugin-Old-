package me.Bokum.PickingCoin;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.event.Listener;
import org.bukkit.plugin.java.JavaPlugin;

public class System extends JavaPlugin implements Listener
{
	public static String MS = ChatColor.RESET+"[ "+ChatColor.GREEN+"PCJ"+" ] "+ChatColor.YELLOW;
	public static Location Lobby;
	public static Location Join;
	public static Location CatcherStart;
	public static Location RunnerStart;
	public static boolean lobbycheck = false;
	public static boolean gamecheck = false;
	public static int lobbysch = 0;
	public static int lobbyschtime = 0;
	public static int gamesch = 0;
	public static int gameschtime = 0;
	public static List<Player> list = new ArrayList<Player>();
	public static List<Player> clist = new ArrayList<Player>();
	public static List<Player> rlist = new ArrayList<Player>();
	
	public static void HelpMessages(Player player)
	{
		player.sendMessage(MS+"/pcj set <lobby/join/cstart/rstart> - ���� ������ �����մϴ�.");
		player.sendMessage(MS+"/pcj join - ���ӿ� �����մϴ�.");
		player.sendMessage(MS+"/pcj quit - ���ӿ��� �����մϴ�.");
		player.sendMessage(MS+"���� - Bokum");
	}
	
	public static int Getrandom(int i)
	{
		return (int)(Math.random() * i); //0~i-1 ���� �� �������� 1�� ��ȯ
	}
	
	public static void SendMessage(String str)
	{
		for(int i = 0; i < list.size(); i++)
		{
			list.get(i).sendMessage(str);
		}
	}

	public static void Joingame(Player player)
	{
		if(gamecheck)
		{
			player.sendMessage(MS+"�̹� ������ ���� �ƽ��ϴ�.");
		}
		else if(list.size() >= 10)
		{
			player.sendMessage(MS+"�̹� �ִ��ο��� 10���� �÷��̾ �������Դϴ�.");
		}
		else
		{
			list.add(player);
			SendMessage(MS+ChatColor.AQUA+player.getName()+ChatColor.YELLOW+" ���� ���ӿ� �����߽��ϴ�. "+ChatColor.RESET+"[ "+ChatColor.RED+list.size()+ChatColor.GREEN+"/"+ChatColor.GRAY+"5"+ChatColor.RESET+" ]");
			if(list.size() >= 5 && !lobbycheck)
			{
				Main.StartGame();
			}
		}
	}
	
	public static void Quitgame(Player player)
	{
		
	}
	
	public static void StartTP()
	{
		if(list.size() < 5)
		{
			Bukkit.broadcastMessage(MS+"�����ݴ� ¡¡���� �����ο��� 5�� ���ϰ� �Ǿ� �������� �˴ϴ�.");
			ForceEnd();
			return;
		}
		int ri = Getrandom(list.size());
		clist.add(list.get(ri));
		while(clist.size() >= 2)
		{
			ri = Getrandom(list.size());
			if(clist.contains(list.get(ri)))
			{
				continue;
			}
			else
			{
				clist.add(list.get(ri));
				break;
			}
		}
		for(int i = 0; i < rlist.size(); i++)
		{
			rlist.get(i).teleport(RunnerStart);
		}
		for(int i = 0; i < clist.size(); i++)
		{
			clist.get(i).teleport(CatcherStart);
			SendMessage(MS+ChatColor.AQUA+clist.get(i).getName()+ChatColor.GRAY+" ���� ���Ի������� ���� �ƽ��ϴ�.");
		}
	}
	
	public static void SendStartMessage()
	{
		for(int i = 0; i < clist.size(); i++)
		{
			clist.get(i).sendMessage(MS+"����� ������ "+ChatColor.RED+"���Ի���"+ChatColor.YELLOW+" �Դϴ�.");
			clist.get(i).sendMessage(MS+"����� 7�а� ¡¡�̰� Ż������ ���ϵ��� ����ؼ� ¡¡�̸� �׿����մϴ�.");
			clist.get(i).sendMessage(MS+"¡¡�̴� ����ؼ� ������ ���� ���Դϴ�. �̸� �����ϼ���!");
		}
		for(int i = 0; i < rlist.size(); i++)
		{
			rlist.get(i).sendMessage(MS+"����� ������ "+ChatColor.RED+"¡¡��"+ChatColor.YELLOW+" �Դϴ�.");
			rlist.get(i).sendMessage(MS+"�ݹ����� ��Ŭ���� �� �������� ���� ��ԵǸ� 1000���� ������ ����");
			rlist.get(i).sendMessage(MS+"�������信�� ���� �ɾ� ���踦 ������ �Ŀ� Ż���� �����մϴ�.");
		}
	}
	
	public static void CatcherWin()
	{
		
	}
	
	public static void RunnerWin()
	{
		
	}
}
