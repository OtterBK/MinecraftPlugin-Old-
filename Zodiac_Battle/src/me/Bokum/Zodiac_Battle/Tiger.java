package me.Bokum.Zodiac_Battle;

import javax.swing.GroupLayout.SequentialGroup;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Sound;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;

public class Tiger 
{
	public static void Rush(Player p)
	{
		Player t = System.Getcorsurplayer(p, 8);
		if(System.Checktarget(t, 7, p))
		{
			Main.TigerSkill1(p, t);
		}
	}
	
	public static void AreaBind(Player p)
	{
		p.sendMessage(Main.MS+"������ ������ �������׽��ϴ�.");
		p.getWorld().playSound(p.getLocation(), Sound.BLAZE_DEATH, 3.0f, 1.2f);
		System.Addcooldown(p.getName()+"2", 27);
		for(Player t : System.GetEnemies(p))
		{
			if(t.getLocation().distance(p.getLocation()) <= 7 && p.getName() != t.getName())
			{
				t.sendMessage(Main.MS+"�����߽��ϴ�!");
				Main.Setbind(t, 1);
				Main.Setbreakout(t, 1);
				t.getWorld().playSound(p.getLocation(), Sound.BLAZE_HIT, 3.0f, 1.7f);
				ParticleEffect.SPELL_WITCH.display(0.5f, 0, 0.5f, 0.1f, 20, t.getLocation(), 60);
			}
		}
	}
	
	public static void Back(Player p)
	{
		Block tb = p.getTargetBlock(null, 10);
		if(tb == null || tb.getLocation().distance(p.getLocation()) > 9)
		{
			p.sendMessage(Main.MS+"9ĭ �̳������� �̵��� �� �ֽ��ϴ�.");
		}
		else
		{
			Main.TigerSkill3(p, tb.getLocation());
		}
	}
	
	public static void Ultimate(Player p)
	{
		p.sendMessage(Main.MS+"�ñر⸦ ����մϴ�.");
		p.getWorld().playSound(p.getLocation(), Sound.GHAST_SCREAM, 3.0f, 0.3f);
		System.Addcooldown(p.getName()+"4", 78);
		Main.Setgod(p, 3);
		for(Player t : System.GetEnemies(p))
		{
			if(t.getLocation().distance(p.getLocation()) <= 7 && p.getName() != t.getName())
			{
				t.sendMessage(Main.MS+"�����߽��ϴ�!");
				t.teleport(p.getLocation());
				Main.Setbind(t, 5);
				Main.Setbreakout(t, 5);
			}
		}
		Main.TigerSkill4(p);
	}
	
	public static void RightClick(Player p)
	{
		if(System.CheckHandItem(p, "�ֽ�ų") && System.Checkcooldown(p, "1"))
		{
			Tiger.Rush(p);
			return;
		}
		if(System.CheckHandItem(p, "������ų") && System.Checkcooldown(p, "2"))
		{
			Tiger.AreaBind(p);
			return;
		}
		if(System.CheckHandItem(p, "������ų") && System.Checkcooldown(p, "3"))
		{
			Tiger.Back(p);
			return;
		}
		if(System.CheckHandItem(p, "�ñر�") && System.Checkcooldown(p, "4"))
		{
			Tiger.Ultimate(p);
			return;
		}
	}
}
