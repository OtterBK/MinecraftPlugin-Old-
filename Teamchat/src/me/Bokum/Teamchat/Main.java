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
		getLogger().info(MS+"��ê �÷����� �ε� �Ϸ�");
	}
	
	public void onDisable()
	{
		getLogger().info(MS+"��ê �÷����� ��ε� �Ϸ�");
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
						player.sendMessage(MS+"������ �����ϴ�.");
						return true;
					}
					if(args.length <= 2)
					{
						player.sendMessage(MS+"/tc set <�г���> <���̸�>");
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
							player.sendMessage(MS+"���������� "+playertmp.getName()+"�� �� "+args[2]+" ������ �����߽��ϴ�.");
						}
						else
						{
							player.sendMessage(MS+args[1]+" ���� �������� �ʽ��ϴ�.");
						}
					}
					return true;
				}
				else if(args[0].equalsIgnoreCase("c"))
				{
					if(!plist.containsKey(player))
					{
						player.sendMessage(MS+"������ ���� �����ϴ�.");
						return true;
					}
					if(clist.contains(player))
					{
						clist.remove(player);
						player.sendMessage(MS+"�� ä���� �����ϴ�.");
					}
					else
					{
						clist.add(player);
						player.sendMessage(MS+"�� ä���� �׽��ϴ�.");
					}
					return true;
				}
				else if(args[0].equalsIgnoreCase("test"))
				{
					player.sendMessage(MS+"�÷����� ���󱸵� Ȯ�� ���� ����");
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
		player.sendMessage(MS+"/tc set <�г���> <���̸�> - <�г���>�� ���� <���̸�> ���� �����մϴ�.");
		player.sendMessage(MS+"/tc c - �� ä������ ��ȯ�մϴ�.");
		player.sendMessage(MS+"���� : Bokum ���� : 0.6");;
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
