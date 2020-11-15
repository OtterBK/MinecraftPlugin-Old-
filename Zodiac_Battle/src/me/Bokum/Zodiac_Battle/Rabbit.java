package me.Bokum.Zodiac_Battle;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Sound;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.util.Vector;

public class Rabbit 
{
	public static boolean Skill4 = false;
	
	public static void Throw(Player t, Player d)
	{
		ParticleEffect.EXPLOSION_NORMAL.display(0f, 0f, 0f, 0.3f, 10, t.getLocation(), 60);
		t.getWorld().playSound(t.getLocation(), Sound.EXPLODE, 3.0f, 1.2f);
		System.Addcooldown(d.getName()+"1", 8);
		t.setVelocity(new Vector(0,1.1,0));
	}
	
	public static void SuperJump(Player p)
	{
		ParticleEffect.SLIME.display(0.2f, 02f, 0.2f, 0.05f, 40, p.getLocation(), 60);
		p.getWorld().playSound(p.getLocation(), Sound.SLIME_WALK, 3.0F, 0.3F);
		p.sendMessage(Main.MS+"점프력이 강화됩니다.");
		PotionEffect potion = new PotionEffect(PotionEffectType.JUMP, 80, 3);
		p.addPotionEffect(potion);
		System.Addcooldown(p.getName()+"3", 8);
	}
	
	public static void Ultimate(Player p)
	{
		ParticleEffect.SMOKE_LARGE.display(3f, 0.5f, 3f, 0.1f, 80, p.getLocation(), 60);
		p.getWorld().playSound(p.getLocation(), Sound.GHAST_CHARGE, 3.0f, 1.5f);
		p.setVelocity(new Vector(0,8,0));
		Skill4 = true;
		p.sendMessage(Main.MS+"슈~퍼 점프!");
		System.Addcooldown(p.getName()+"4", 54);
	}
	
	public static void Ultimate2(Player p)
	{
		ParticleEffect.EXPLOSION_HUGE.display(8f, 0f, 8f, 0.1f, 200, p.getLocation(), 60);
		p.getWorld().playSound(p.getLocation(), Sound.EXPLODE, 5.0f, 0.7f);
		for(Player t : System.GetEnemies(p))
		{
			if(t.getLocation().distance(p.getLocation()) <= 8 && t.getLocation().getY()-p.getLocation().getY() < 1 && t.getLocation().getY()-p.getLocation().getY() > -1 && p.getName() != t.getName())
			{
				t.setVelocity(new Vector(0,1.5f,0));
				Main.RabbitSkill4(p, t);
			}
		}
	}
	
	public static void Jumping(Player p)
	{
		ParticleEffect.CLOUD.display(5f, 5f, 5f, 0.05f, 40, p.getLocation(), 150);
		p.getWorld().playSound(p.getLocation(), Sound.GHAST_MOAN, 3.0f, 2.0f);
		System.Addcooldown(p.getName()+"2", 17);
		for(Player t : System.GetEnemies(p))
		{
			if(t.getLocation().distance(p.getLocation()) <= 5 && p.getName() != t.getName())
			{
				t.setVelocity(new Vector(0,1.2f,0));
				ParticleEffect.EXPLOSION_LARGE.display(0f, 0f, 0f, 0.1f, 1, t.getLocation(), 60);
				t.getWorld().playSound(p.getLocation(), Sound.EXPLODE, 3.0f, 0.9f);
			}
		}
	}
	
	public static void RightClick(Player p)
	{
		if(System.CheckHandItem(p, "보조스킬") && System.Checkcooldown(p, "2"))
		{
			Rabbit.Jumping(p);
			return;
		}
		if(System.CheckHandItem(p, "생존스킬") && System.Checkcooldown(p, "3"))
		{
			Rabbit.SuperJump(p);
			return;
		}
		if(System.CheckHandItem(p, "궁극기") && System.Checkcooldown(p, "4"))
		{
			Rabbit.Ultimate(p);
			return;
		}
	}
}
