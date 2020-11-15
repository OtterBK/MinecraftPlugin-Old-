package me.Bokum.MOB.Game;

import java.util.HashMap;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Sound;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import me.Bokum.MOB.Main;

public class Cooldown {

	public static HashMap<String, Integer> cooldownlist = new HashMap<String, Integer>(130);
	
	
	public static boolean Checkcooldown(Player p, String str)
	{
		if(!cooldownlist.containsKey(p.getName()+str) || cooldownlist.get(p.getName()+str) <= (int)(java.lang.System.currentTimeMillis()/1000))
		{
			return true;
		}
		p.sendMessage(Main.MS+ChatColor.AQUA+(cooldownlist.get(p.getName()+str)-(int)(java.lang.System.currentTimeMillis()/1000))
				+ChatColor.RESET+"�� �� ��ų�� ����Ͻ� �� �ֽ��ϴ�.");
		p.playSound(p.getLocation(), Sound.NOTE_SNARE_DRUM, 1.0f, 0.7f);
		return false;
	}
	
	public static void Setcooldown(Player p, String str, int cooldown , boolean count)
	{
		Cooldown.cooldownlist.put(p.getName()+str, (int)(java.lang.System.currentTimeMillis()/1000)+cooldown);
		if(!count) return;
		int slot = 0;
		if(str.equalsIgnoreCase("�ֽ�ų")) slot = 1;
		if(str.equalsIgnoreCase("������ų")) slot = 2;
		if(str.equalsIgnoreCase("�ñر�")) slot = 3;
		Countdown(p, cooldown, str, slot);
	}
	
	public static void SkillCountdown(final Player p, long time, final String skill)
	{
		time *= 20;
		Bukkit.getScheduler().scheduleSyncDelayedTask(Main.instance, new Runnable()
		{
			public void run()
			{
				p.sendMessage(Main.MS+ChatColor.AQUA+"3���� "+ChatColor.RESET+skill+"�� �����˴ϴ�.");
			}
		}, time-60);
		Bukkit.getScheduler().scheduleSyncDelayedTask(Main.instance, new Runnable()
		{
			public void run()
			{
				p.sendMessage(Main.MS+ChatColor.AQUA+"2���� "+ChatColor.RESET+skill+"�� �����˴ϴ�.");
			}
		}, time-40);
		Bukkit.getScheduler().scheduleSyncDelayedTask(Main.instance, new Runnable()
		{
			public void run()
			{
				p.sendMessage(Main.MS+ChatColor.AQUA+"1���� "+ChatColor.RESET+skill+"�� �����˴ϴ�.");
			}
		}, time-20);
		Bukkit.getScheduler().scheduleSyncDelayedTask(Main.instance, new Runnable()
		{
			public void run()
			{
				p.sendMessage(Main.MS+ChatColor.AQUA+skill+"�ɷ��� �����ƽ��ϴ�.");
			}
		}, time);
	}
	
	public static void Countdown(final Player p, long time, final String skill, final int slot)
	{
		final ItemStack item = p.getInventory().getItem(slot);
		if(slot != 0){
			if(item != null){
				ItemMeta meta = item.getItemMeta();
				meta.addEnchant(Enchantment.ARROW_INFINITE, 1, true);
				item.setItemMeta(meta);
			}
		}
		time *= 20;
		if(time < 60){
			return;
		}
		Bukkit.getScheduler().scheduleSyncDelayedTask(Main.instance, new Runnable()
		{
			public void run()
			{
				p.sendMessage(Main.MS+ChatColor.AQUA+"3���� "+ChatColor.RESET+skill+"�� ��Ÿ���� �����ϴ�.");
			}
		}, time-60);
		Bukkit.getScheduler().scheduleSyncDelayedTask(Main.instance, new Runnable()
		{
			public void run()
			{
				p.sendMessage(Main.MS+ChatColor.AQUA+"2���� "+ChatColor.RESET+skill+"�� ��Ÿ���� �����ϴ�.");
			}
		}, time-40);
		Bukkit.getScheduler().scheduleSyncDelayedTask(Main.instance, new Runnable()
		{
			public void run()
			{
				p.sendMessage(Main.MS+ChatColor.AQUA+"1���� "+ChatColor.RESET+skill+"�� ��Ÿ���� �����ϴ�.");
			}
		}, time-20);
		Bukkit.getScheduler().scheduleSyncDelayedTask(Main.instance, new Runnable()
		{
			public void run()
			{
				p.sendMessage(Main.MS+ChatColor.RESET+skill+"�� ��Ÿ���� �������ϴ�.");
				p.playSound(p.getLocation(), Sound.ITEM_BREAK, 2.0f, 1.6f);
				if(slot != 0){
					if(item != null) item.removeEnchantment(Enchantment.ARROW_INFINITE);
				}
			}
		}, time);


	}
}
