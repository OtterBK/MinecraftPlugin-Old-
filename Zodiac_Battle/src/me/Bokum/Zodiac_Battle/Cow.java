package me.Bokum.Zodiac_Battle;

import org.bukkit.Bukkit;
import org.bukkit.Sound;
import org.bukkit.entity.Player;

public class Cow 
{
	public static boolean Cowskill1 = false;
	public static boolean Cowskill4 = false;
	
	public static void IncreaseDamage(Player p)
	{
		p.sendMessage(Main.MS+"4�ʰ� ��� �������� 2�� �߰��������� �ٽ��ϴ�.");
		Main.Countdown(p, 4, "��");;
		System.Addcooldown(p.getName()+"1", 10);
		p.getWorld().playSound(p.getLocation(), Sound.COW_IDLE, 2.5F, 0.7F);
		Main.CowSkill1(p);
	}
	
	public static void Heal(Player p)
	{
		p.sendMessage(Main.MS+"4�� ü���� ȸ���߽��ϴ�.");
		System.PlusHp(p, 5);
		System.Addcooldown(p.getName()+"3", 16);
		p.getWorld().playSound(p.getLocation(), Sound.BREATH, 2.5f, 0.6f);
		ParticleEffect.HEART.display(1, 1, 1, 0.2f, 30, p.getLocation().add(0,1,0), 60);
	}
	
	public static void Givedamage(Player p)
	{
		p.sendMessage(Main.MS+"������ ���鿡�� 3�� �������� ����ϴ�.");
		p.getWorld().playSound(p.getLocation(), Sound.ANVIL_BREAK, 2.5f, 1.7f);
		System.Addcooldown(p.getName()+"2", 15);
		for(Player t : System.GetEnemies(p))
		{
			if(t.getLocation().distance(p.getLocation()) <= 3 && p.getName() != t.getName())
			{
				t.sendMessage(Main.MS+"���� �ɷ����� 3�� �������� �޾ҽ��ϴ�.");
				System.Minusp(t, 3);;
				t.getWorld().playSound(p.getLocation(), Sound.BLAZE_HIT, 2.5f, 1.7f);
				ParticleEffect.VILLAGER_ANGRY.display(2, 0, 2, 0.2f, 40, t.getLocation(), 60);
			}
		}
	}
	
	public static void Ultimate(Player p)
	{
		p.sendMessage(Main.MS+"");
		Main.Countdown(p, 8, "�ñر�");;
		System.Addcooldown(p.getName()+"4", 70);
		Main.CowSkill4(p);
	}
	
	public static void RightClick(Player p)
	{
		if(System.CheckHandItem(p, "�ֽ�ų") && System.Checkcooldown(p, "1"))
		{
			Cow.IncreaseDamage(p);
			System.Addcooldown(p.getName()+"1", 10);
			return;
		}
		if(System.CheckHandItem(p, "������ų") && System.Checkcooldown(p, "2"))
		{
			Cow.Givedamage(p);
			return;
		}
		if(System.CheckHandItem(p, "������ų") && System.Checkcooldown(p, "3"))
		{
			Cow.Heal(p);
			return;
		}
		if(System.CheckHandItem(p, "�ñر�") && System.Checkcooldown(p, "4"))
		{
			Cow.Ultimate(p);
			return;
		}
	}
}
