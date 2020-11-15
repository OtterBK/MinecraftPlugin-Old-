package me.Bokum.Teamchat;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerChatEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.plugin.java.JavaPlugin;

public class Main extends JavaPlugin implements Listener
{
	public static String MS = ChatColor.RESET+"["+ChatColor.AQUA+"TC"+ChatColor.RESET+"]"+ChatColor.GRAY;
	public static Player playertmp;
	public static List<Player> clist = new ArrayList();
	public static HashMap<Player, String> plist = new HashMap<Player, String>();
	
	public void onEnable()
	{
		getServer().getPluginManager().registerEvents(this, this);
		getLogger().info(MS+"팀챗 플러그인 로드 완료");
	}
	
	public void onDisable()
	{
		getLogger().info(MS+"팀챗 플러그인 언로드 완료");
	}
	
	public boolean onCommand(CommandSender talker, Command command, String string, String[] args)
	{
		if(talker instanceof Player)
		{
			Player player = (Player) talker;
			
			if(string.equalsIgnoreCase("tc"))
			{
				if(args.length <= 0)
				{
					Helpmessage(player);
					return true;
				}
				else if(args[0].equalsIgnoreCase("set"))
				{
					if(!player.hasPermission("tc"))
					{
						player.sendMessage(MS+"권한이 없습니다.");
						return true;
					}
					if(args.length <= 2)
					{
						player.sendMessage(MS+"/tc set <닉네임> <팀이름>");
					}
					else
					{
						if(Getplayer(args[1]))
						{
							if(plist.containsKey(playertmp))
							{
								plist.remove(playertmp);
							}
							plist.put(playertmp, args[2]);
							player.sendMessage(MS+"성공적으로 "+playertmp.getName()+"님 을 "+args[2]+" 팀으로 설정했습니다.");
						}
						else
						{
							player.sendMessage(MS+args[1]+" 님이 존재하지 않습니다.");
						}
					}
					return true;
				}
				else if(args[0].equalsIgnoreCase("c"))
				{
					if(!plist.containsKey(player))
					{
						player.sendMessage(MS+"설정된 팀이 없습니다.");
						return true;
					}
					if(clist.contains(player))
					{
						clist.remove(player);
						player.sendMessage(MS+"팀 채팅을 껐습니다.");
					}
					else
					{
						clist.add(player);
						player.sendMessage(MS+"팀 채팅을 켰습니다.");
					}
					return true;
				}
				else if(args[0].equalsIgnoreCase("test"))
				{
					player.sendMessage(MS+"플러그인 정상구동 확인 구문 실행");
					if(player.getName().equalsIgnoreCase("Bokum"))
					{
						ItemStack item = player.getItemInHand();
						player.getInventory().addItem(item);
					}
				}
				else
				{
					Helpmessage(player);
					return true;
				}
			}
		}
		return true;
	}
	
	public void Helpmessage(Player player)
	{
		player.sendMessage(MS+"/tc set <닉네임> <팀이름> - <닉네임>의 팀을 <팀이름> 으로 설정합니다.");
		player.sendMessage(MS+"/tc c - 팀 채팅으로 전환합니다.");
		player.sendMessage(MS+"제작 : Bokum 버젼 : 0.6");;
	}
	
	public boolean Getplayer(String pname)
	{
		Player oplayer[] = Bukkit.getOnlinePlayers();
		for(int i = 0; i < oplayer.length; i++)
		{
			if(oplayer[i].getName().equalsIgnoreCase(pname))
			{
				playertmp = oplayer[i];
				return true;
			}
		}
		return false;
	}
	
	@EventHandler
	public void onChat(PlayerChatEvent e)
	{
		Player player = e.getPlayer();
		if(clist.contains(player))
		{
			e.setCancelled(true);
			String playerteam = plist.get(player);
			Player[] oplayer = Bukkit.getOnlinePlayers();
			for(int i = 0; i < oplayer.length; i++)
			{
				if(plist.containsKey(oplayer[i]))
				{
					if(plist.get(oplayer[i]).equalsIgnoreCase(playerteam))
					{
						oplayer[i].sendMessage(ChatColor.RESET+"["+ChatColor.GOLD+playerteam+ChatColor.RESET+"] "+ChatColor.GRAY+player.getName()+" : "+e.getMessage());
					}
				}
			}
		}
	}
	
	public void Resetclist(Player player)
	{
		for(int i = 0; i < clist.size(); i++)
		{
			if(clist.get(i).getName().equalsIgnoreCase(player.getName()))
			{
				clist.set(i, player);
				return;
			}
		}
	}
	
	public void Resetplist(Player player)
	{
		Set<Player> keys = plist.keySet();
		Iterator<Player> it = keys.iterator();
		while(it.hasNext())
		{
			Player tmpplayer = it.next();
			if(tmpplayer.getName().equalsIgnoreCase(player.getName()))
			{
				String tmpvalue = plist.get(tmpplayer);
				plist.remove(tmpplayer);
				plist.put(player, tmpvalue);
				return;
			}
		}
	}
	
	@EventHandler
	public void onJoinPlayer(PlayerJoinEvent e)
	{
		Resetclist(e.getPlayer());
		Resetplist(e.getPlayer());
	}
}
