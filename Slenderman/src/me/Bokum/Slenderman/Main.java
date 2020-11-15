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
		getLogger().info("�������� �÷������� Ȱ��ȭ �Ǿ����ϴ�.");
	}
	
	public void onDisable()
	{
		getLogger().info("�������� �÷������� ��Ȱ��ȭ �Ǿ����ϴ�.");
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
						player.sendMessage("�г����� �Է����ּ���.");
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
						player.sendMessage("�г����� �Է����ּ���.");
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
			player.sendMessage("�̹� ������������ ������ �÷��̾��Դϴ�.");
			return;
		}
		player.sendMessage(target+" ���� ������������ �����߽��ϴ�.");
		slenderlist.add(target);
	}
	
	public void RemoveSlenderMan(Player player, String target) //��� ����
	{
		if(slenderlist.contains(target)) //���� �Է��� �г����� �������Ǹ���Ʈ�� �ִٸ�
		{
			slenderlist.remove(target); //����������Ʈ���� ����
			player.sendMessage(target+" ���� �����߽��ϴ�."); //�����ߴٰ� �޼��� ������
		}
		else //���� �Է��� �г����� �������Ǹ���Ʈ�� ���ٸ�
		{
			player.sendMessage(target+" ���� �������ǿ� ��ϵ��� �ʾҽ��ϴ�.");
		}
	}
	
	@EventHandler //�̺�Ʈ �޾ƿ���
	public void onMove(PlayerMoveEvent e) //��� �̸�
	{
		Player p = e.getPlayer();
		if(e.getFrom().getX() == e.getTo().getX() && e.getFrom().getY() == e.getTo().getY() && e.getFrom().getZ() == e.getTo().getZ())
		{
			return;
		}
		if(slenderlist.contains(p.getName()))
		{
			Player onlineplayer[] = Bukkit.getOnlinePlayers();
			for(int i = 0; i < onlineplayer.length; i++)//������ �ִ� �÷��̾����ŭ �ݺ�
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
			for(int i = 0; i < onlineplayer.length; i++)//������ �ִ� �÷��̾����ŭ �ݺ�
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
