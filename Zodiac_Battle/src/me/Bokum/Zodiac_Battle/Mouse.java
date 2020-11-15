package me.Bokum.Zodiac_Battle;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

public class Mouse
{
	public static List<Location> traploc = new ArrayList<Location>();
	public static List<String> avoidlist = new ArrayList<String>();
	
	public static void SetAvoid(Player p)
	{
		avoidlist.add(p.getName());
		p.sendMessage(Main.MS+"쥐의 능력으로 1번의 공격을 회피합니다.");
		p.getWorld().playSound(p.getLocation(), Sound.ANVIL_BREAK, 3.0f, 2.0f);
		ParticleEffect.HEART.display(0, 0, 0, 10, 10, p.getLocation().add(0,2,0), 15);
	}
	
	public static void Invisible(Player p)
	{
		PotionEffect potion = new PotionEffect(PotionEffectType.INVISIBILITY, 120, 1);
		p.addPotionEffect(potion);
		p.sendMessage(Main.MS+"6초간 은신상태가 됩니다.");
		Main.Countdown(p, 6, "생존");
		Main.MouseSkill3(p);
		System.Addcooldown(p.getName()+"3", 20);
		p.getWorld().playSound(p.getLocation(), Sound.BAT_TAKEOFF, 3.0F, 1.2F);
		ParticleEffect.SMOKE_LARGE.display(1, 1, 1, 0, 50, p.getLocation(), 15);
	}
	
	public static void Ultimate(Player p)
	{
		PotionEffect potion = new PotionEffect(PotionEffectType.SPEED, 140, 1);
		p.addPotionEffect(potion);
		p.sendMessage(Main.MS+"7초간 이동속도가 증가하고 무적상태가 됩니다.");
		Main.Setgod(p, 7);
		Main.Countdown(p, 7, "궁극기");
		System.Addcooldown(p.getName()+"4", 72);
		Main.MouseSkill4(p);
	}
	
	public static void SetTrap(Player p, Location loc)
	{
		traploc.add(new Location(loc.getWorld(), loc.getBlockX(), loc.getBlockY()+1, loc.getBlockZ()));
		p.sendMessage(Main.MS+"X: "+loc.getBlockX()+" Y: "+(loc.getBlockY()+1)+" Z: "+loc.getBlockZ()+" 좌표에 덫을 설치했습니다.");
		System.Addcooldown(p.getName()+"2", 24);
		p.getWorld().playSound(p.getLocation(), Sound.ANVIL_USE, 3.0f, 0.5f);
		ParticleEffect.SMOKE_NORMAL.display(0, 1, 0, 0, 30, new Location(loc.getWorld(), loc.getBlockX(), loc.getBlockY()+1, loc.getBlockZ()), 10);
	}
	
	public static void RightClick(Player p)
	{
		if(System.CheckHandItem(p, "주스킬") && System.Checkcooldown(p, "1"))
		{
			Mouse.SetAvoid(p);
			System.Addcooldown(p.getName()+"1", 9);
			return;
		}
		if(System.CheckHandItem(p, "생존스킬") && System.Checkcooldown(p, "3"))
		{
			Mouse.Invisible(p);
			return;
		}
		if(System.CheckHandItem(p, "궁극기") && System.Checkcooldown(p, "4"))
		{
			Mouse.Ultimate(p);
			return;
		}
	}
	
	public static void LeftClick(Player p, Location loc)
	{
		if(System.CheckHandItem(p, "보조스킬") && System.Checkcooldown(p, "2"))
		{
			Mouse.SetTrap(p, loc);
		}
	}
}
