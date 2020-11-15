package me.Bokum.Slenderman;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

public class Main extends JavaPlugin implements Listener
{
	public static List<String> slenderlist = new ArrayList<String>();
	
	public void onEnable()
	{
		getServer().getPluginManager().registerEvents(this, this);
		getLogger().info("슬랜더맨 플러그인이 활성화 되었습니다.");
	}
	
	public void onDisable()
	{
		getLogger().info("슬랜더맨 플러그인이 비활성화 되었습니다.");
	}
	
	public boolean onCommand(CommandSender talker, Command command, String string, String args[])
	{
		if(talker instanceof Player)
		{
			Player player = (Player) talker;
			if(string.equalsIgnoreCase("sdm"))
			{
				if(args[0].equalsIgnoreCase("add"))
				{
					if(args.length == 1)
					{
						player.sendMessage("닉네임을 입력해주세요.");
					}
					else
					{
						AddSlenderMan(player, args[1]);
					}
					return true;
				}
				else if(args[0].equalsIgnoreCase("remove"))
				{
					if(args.length == 1)
					{
						player.sendMessage("닉네임을 입력해주세요.");
					}
					else
					{
						RemoveSlenderMan(player, args[1]);
					}
					return true;
				}
			}
		}
		return true;
	}

	public void AddSlenderMan(Player player, String target)
	{
		if(slenderlist.contains(target))
		{
			player.sendMessage("이미 슬랜더맨으로 지정된 플레이어입니다.");
			return;
		}
		player.sendMessage(target+" 님을 슬랜더맨으로 지정했습니다.");
		slenderlist.add(target);
	}
	
	public void RemoveSlenderMan(Player player, String target) //기능 제목
	{
		if(slenderlist.contains(target)) //만약 입력한 닉네임이 슬랜더맨리스트에 있다면
		{
			slenderlist.remove(target); //슬랜더리스트에서 삭제
			player.sendMessage(target+" 님을 삭제했습니다."); //삭제했다고 메세지 보내기
		}
		else //만약 입력한 닉네임이 슬랜더맨리스트에 없다면
		{
			player.sendMessage(target+" 님은 슬랜더맨에 등록되지 않았습니다.");
		}
	}
	
	@EventHandler //이벤트 받아오기
	public void onMove(PlayerMoveEvent e) //기능 이름
	{
		Player p = e.getPlayer();
		if(e.getFrom().getX() == e.getTo().getX() && e.getFrom().getY() == e.getTo().getY() && e.getFrom().getZ() == e.getTo().getZ())
		{
			return;
		}
		if(slenderlist.contains(p.getName()))
		{
			Player onlineplayer[] = Bukkit.getOnlinePlayers();
			for(int i = 0; i < onlineplayer.length; i++)//서버에 있는 플레이어수만큼 반복
			{
				Player t = onlineplayer[i];	
				if(!slenderlist.contains(t.getName()) && p.getLocation().distance(t.getLocation()) <= 15)
				{
					PotionEffect potion = new PotionEffect(PotionEffectType.CONFUSION, 72000, 2);
					t.addPotionEffect(potion);
				} 
				else
				{
					for(PotionEffect effect : t.getActivePotionEffects())
					{
						t.removePotionEffect(effect.getType());
					}
				}
			}
		}
		else
		{
			Player onlineplayer[] = Bukkit.getOnlinePlayers();
			for(int i = 0; i < onlineplayer.length; i++)//서버에 있는 플레이어수만큼 반복
			{
				Player t = onlineplayer[i];	
				if(t.getLocation().distance(p.getLocation()) <= 25 && slenderlist.contains(t.getName()))
				{
					PotionEffect potion = new PotionEffect(PotionEffectType.CONFUSION, 72000, 2);
					p.addPotionEffect(potion);
					return;
				}
			}
			for(PotionEffect effect : p.getActivePotionEffects())
			{
				p.removePotionEffect(effect.getType());
			}
		}
	}
}
