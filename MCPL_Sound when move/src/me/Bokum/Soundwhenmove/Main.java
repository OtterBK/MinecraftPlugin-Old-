package me.Bokum.Soundwhenmove;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Set;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Sound;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.event.player.PlayerToggleSneakEvent;
import org.bukkit.plugin.java.JavaPlugin;

import me.kazzababe.bukkit.NoNameTags;
import net.minecraft.server.v1_5_R3.DistanceComparator;

public class Main extends JavaPlugin implements Listener
{
	public static String SWM = ChatColor.RESET+"[ "+ChatColor.GREEN+"SWM"+ChatColor.RESET+" ]";
	HashMap<String, Boolean> plist = new HashMap<String, Boolean>();
	
	public void onEnable()
	{
		getServer().getPluginManager().registerEvents(this, this);
		getLogger().info("방울 소리 플러그인 로드 완료! - 제작 : Bokum");
	}
	
	public void onDisable()
	{
		getLogger().info("방울 소리 플러그인 언로드");
	}
	
	public boolean onCommand(CommandSender talker, Command command, String string,String args[])
	{
		if(talker instanceof Player)
		{
			Player player = (Player) talker;
			if(!player.isOp())
			{
				player.sendMessage(SWM+"op만 사용 가능합니다.");
				return true;
			}
			if(string.equalsIgnoreCase("swm"))
			{
				if(args.length >= 1)
				{
					if(args[0].equalsIgnoreCase("add"))
					{
						Addplayer(args,player);
						return true;
					}
					if(args[0].equalsIgnoreCase("remove"))
					{
						Removeplayer(args,player);
						return true;
					}
					if(args[0].equalsIgnoreCase("list"))
					{
						Listplayer(player);
						return true;
					}
				}
				Helpmessages(player);
				return true;
			}
		}
		return true;
	}
	
	public void RemoveName()
	{
		Player player[] = Bukkit.getOnlinePlayers();
		for(Player p : player)
		{
			p.setSneaking(true);
			p.sendMessage(SWM+"이름표를 감춥니다.");
		}
	}
	
	public void SeeName()
	{
		Player player[] = Bukkit.getOnlinePlayers();
		for(Player p : player)
		{
			p.setSneaking(false);
			p.sendMessage(SWM+"이름표를 보이게 합니다.");
		}
	}
	
	public void Helpmessages(Player player) 
	{
		player.sendMessage(SWM+"/swm add <닉네임> - <닉네임> 님을 목록에 추가합니다.");
		player.sendMessage(SWM+"/swm remove <닉네임> - <닉네임> 님을 목록에서 삭제합니다.");
		player.sendMessage(SWM+"/swm list <닉네임> - 지정한 플레이어 목록을 봅니다.");
		player.sendMessage(ChatColor.BLUE+"제작 - Bokum 버젼 - 0.78" );
	}

	public void Addplayer(String args[], Player player)
	{
		if(args.length == 1)
		{
			player.sendMessage(SWM+"플레이어의 이름을 입력해주세요.");
		}
		if(args.length >= 2)
		{
			plist.put(args[1], true);
			player.sendMessage(SWM+args[1]+"님을 지정했습니다.");
		}
	}
	
	public void Removeplayer(String args[], Player player)
	{
		if(args.length == 1)
		{
			player.sendMessage(SWM+"플레이어의 이름을 입력해주세요.");
		}
		if(args.length >= 2)
		{
			plist.remove(args[1]);
			player.sendMessage(SWM+args[1]+"님을 목록에서 삭제했습니다.");
		}
	}
	
	
	@SuppressWarnings("rawtypes")
	public void Listplayer(Player player)
	{
		player.sendMessage(SWM+"지정된 플레이어 목록");
		Set key = plist.keySet();
		for(Iterator iterator = key.iterator(); iterator.hasNext();)
		{
			String pname = (String) iterator.next();
			player.sendMessage(ChatColor.GOLD + pname);
		}
	}
	
	
	/*public void onMove(PlayerMoveEvent event)
	{
		if(!plist.containsKey(event.getPlayer().getName()))
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
		if(player.isSneaking())
		{
			player.getLocation().getWorld().playSound(player.getLocation(), Sound.ORB_PICKUP, 0.04F, 0.7F);
			return;
		}
		if(player.isSprinting())
		{
			player.getLocation().getWorld().playSound(player.getLocation(), Sound.ORB_PICKUP, 0.32F, 0.7F);
		}
		else
		{
			player.getLocation().getWorld().playSound(player.getLocation(), Sound.ORB_PICKUP, 0.16F, 0.7f);
		}
	}*/
	@EventHandler
	public void onMove(PlayerMoveEvent event)
	{
		if(!plist.containsKey(event.getPlayer().getName()))
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
			Playerjump(player);
			return;
		}
		if(player.isSneaking())
		{
			Playersneak(player);
			return;
		}
		if(player.isSprinting())
		{
			Playerrun(player);
		}
		else
		{
			Playerwalk(player);
		}
		}
	public void Playerjump(Player eplayer)
	{
		Player[] player = Bukkit.getOnlinePlayers();
		for(int i = 0; i < player.length; i++)
		{
		    Double Distance = Double.valueOf(Math.sqrt(
		    Math.pow(player[i].getLocation().getX() - 
		    eplayer.getLocation().getX(), 2.0D) + 
		    Math.pow(player[i].getLocation().getZ() - 
		    eplayer.getLocation().getZ(), 2.0D)));
		    int ycheck = (int) Math.abs(player[i].getLocation().getBlockY() - eplayer
		    		.getLocation().getBlockY());
		    
		    if(Distance.doubleValue() < 32 && ycheck < 4 && !plist.containsKey(player[i].getName()))
		    {
		    	player[i].playSound(eplayer.getLocation(), Sound.ORB_PICKUP, 0.14f, 0);
		    }
		}
	}
	public void Playerrun(Player eplayer)
	{
		Player[] player = Bukkit.getOnlinePlayers();
		for(int i = 0; i < player.length; i++)
		{
		    Double Distance = Double.valueOf(Math.sqrt(
		    Math.pow(player[i].getLocation().getX() - 
		    eplayer.getLocation().getX(), 2.0D) + 
		    Math.pow(player[i].getLocation().getZ() - 
		    eplayer.getLocation().getZ(), 2.0D)));
		    int ycheck = (int) Math.abs(player[i].getLocation().getBlockY() - eplayer
		    		.getLocation().getBlockY());
		    
		    if(Distance.doubleValue() < 24 && ycheck < 4 && !plist.containsKey(player[i].getName()))
		    {
		    	player[i].playSound(eplayer.getLocation(), Sound.ORB_PICKUP, 0.12f, 0);
		    }
		}
	}
	public void Playerwalk(Player eplayer)
	{
		Player[] player = Bukkit.getOnlinePlayers();
		for(int i = 0; i < player.length; i++)
		{
		    Double Distance = Double.valueOf(Math.sqrt(
		    Math.pow(player[i].getLocation().getX() - 
		    eplayer.getLocation().getX(), 2.0D) + 
		    Math.pow(player[i].getLocation().getZ() - 
		    eplayer.getLocation().getZ(), 2.0D)));
		    int ycheck = (int) Math.abs(player[i].getLocation().getBlockY() - eplayer
		    		.getLocation().getBlockY());
		    
		    if(Distance.doubleValue() < 16 && ycheck < 4 && !plist.containsKey(player[i].getName()))
		    {
		    	player[i].playSound(eplayer.getLocation(), Sound.ORB_PICKUP, 0.09f, 0);
		    }
		}
	}
	public void Playersneak(Player eplayer)
	{
		Player[] player = Bukkit.getOnlinePlayers();
		for(int i = 0; i < player.length; i++)
		{
		    Double Distance = Double.valueOf(Math.sqrt(
		    Math.pow(player[i].getLocation().getX() - 
		    eplayer.getLocation().getX(), 2.0D) + 
		    Math.pow(player[i].getLocation().getZ() - 
		    eplayer.getLocation().getZ(), 2.0D)));
		    int ycheck = (int) Math.abs(player[i].getLocation().getBlockY() - eplayer
		    		.getLocation().getBlockY());
		    
		    if(Distance.doubleValue() < 8 && ycheck < 4 && !plist.containsKey(player[i].getName()))
		    {
		    	player[i].playSound(eplayer.getLocation(), Sound.ORB_PICKUP, 0.04f, 0);
		    }
		}
	}
}
