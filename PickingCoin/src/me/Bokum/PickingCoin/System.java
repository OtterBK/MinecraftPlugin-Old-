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
		player.sendMessage(MS+"/pcj set <lobby/join/cstart/rstart> - 각종 워프를 지정합니다.");
		player.sendMessage(MS+"/pcj join - 게임에 참여합니다.");
		player.sendMessage(MS+"/pcj quit - 게임에서 퇴장합니다.");
		player.sendMessage(MS+"제작 - Bokum");
	}
	
	public static int Getrandom(int i)
	{
		return (int)(Math.random() * i); //0~i-1 까지 중 랜덤으로 1개 반환
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
			player.sendMessage(MS+"이미 게임이 시작 됐습니다.");
		}
		else if(list.size() >= 10)
		{
			player.sendMessage(MS+"이미 최대인원인 10명의 플레이어가 참가중입니다.");
		}
		else
		{
			list.add(player);
			SendMessage(MS+ChatColor.AQUA+player.getName()+ChatColor.YELLOW+" 님이 게임에 참가했습니다. "+ChatColor.RESET+"[ "+ChatColor.RED+list.size()+ChatColor.GREEN+"/"+ChatColor.GRAY+"5"+ChatColor.RESET+" ]");
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
			Bukkit.broadcastMessage(MS+"동전줍는 징징이의 참가인원이 5명 이하가 되어 강제종료 됩니다.");
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
			SendMessage(MS+ChatColor.AQUA+clist.get(i).getName()+ChatColor.GRAY+" 님이 집게사장으로 선택 됐습니다.");
		}
	}
	
	public static void SendStartMessage()
	{
		for(int i = 0; i < clist.size(); i++)
		{
			clist.get(i).sendMessage(MS+"당신의 역할은 "+ChatColor.RED+"집게사장"+ChatColor.YELLOW+" 입니다.");
			clist.get(i).sendMessage(MS+"당신은 7분간 징징이가 탈출하지 못하도록 계속해서 징징이를 죽여야합니다.");
			clist.get(i).sendMessage(MS+"징징이는 계속해서 코인을 모을 것입니다. 이를 방해하세요!");
		}
		for(int i = 0; i < rlist.size(); i++)
		{
			rlist.get(i).sendMessage(MS+"당신의 역할은 "+ChatColor.RED+"징징이"+ChatColor.YELLOW+" 입니다.");
			rlist.get(i).sendMessage(MS+"금발판을 좌클릭할 시 랜덤으로 돈을 얻게되며 1000원을 모으고 나서");
			rlist.get(i).sendMessage(MS+"스폰지밥에게 말을 걸어 열쇠를 구입한 후에 탈출이 가능합니다.");
		}
	}
	
	public static void CatcherWin()
	{
		
	}
	
	public static void RunnerWin()
	{
		
	}
}
