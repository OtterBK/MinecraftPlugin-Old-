package me.Bokum.MOB.Attacker;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Sound;
import org.bukkit.block.Block;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Arrow;
import org.bukkit.entity.Entity;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.entity.ProjectileLaunchEvent;
import org.bukkit.event.player.PlayerItemHeldEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.util.Vector;

import me.Bokum.MOB.Main;
import me.Bokum.MOB.Ability.Ability;
import me.Bokum.MOB.Game.Cooldown;
import me.Bokum.MOB.Utility.ParticleEffect;

public class BowMaster extends Ability{
	
	public boolean Freezing_arrow = false;
	public boolean Speed_arrow = false;
	public boolean Explode_arrow = false;
	public Player bowmaster;
	
	public BowMaster(String playername, Player p){
		super(playername, "���� ������");
		bowmaster = p;
		ItemStack item = new ItemStack(261, 1);
		ItemMeta meta = item.getItemMeta();
		meta.addEnchant(Enchantment.ARROW_INFINITE, 1, true);
		meta.addEnchant(Enchantment.DURABILITY, 10, true);
		meta.setDisplayName("��f[ ��b���� ������ ��f]");
		List<String> lorelist = new ArrayList<String>();
		lorelist.add("��f- ��7���ݷ� : ��61~10(������ ��丸ŭ)");
		lorelist.add("��f- ��7������ ���� : ��6���� ������ x 1��");
		lorelist.add("��f- ��7���� : ��6�ڡڡڡڡ�");
		lorelist.add("��f- ��7�⵿ : ��6�١١١١�");
		lorelist.add("��f- ��7���� : ��6�ڡڡ١١�");
		meta.setLore(lorelist);
		item.setItemMeta(meta);
		p.getInventory().setItem(0 ,item);
		
		item = new ItemStack(265, 1);
		meta = item.getItemMeta();
		meta.setDisplayName("��f[ ��c�ֽ�ų ��f]");
		item.setItemMeta(meta);
		
		p.getInventory().setItem(1 ,item);
		
		item = new ItemStack(264, 1);
		meta = item.getItemMeta();
		meta.setDisplayName("��f[ ��c������ų ��f]");
		item.setItemMeta(meta);
		
		p.getInventory().setItem(2 ,item);
		
		item = new ItemStack(388, 1);
		meta = item.getItemMeta();
		meta.setDisplayName("��f[ ��c�ñر� ��f]");
		item.setItemMeta(meta);
		
		p.getInventory().setItem(3 ,item);
		
		p.getInventory().setItem(6, new ItemStack(262, 64));
	}
	
	public void description(){
		bowmaster.sendMessage("��6============= ��f[ ��b ���� ������ ��f] - ��e������ ��7: ��cAttacker ��6============= ");
		bowmaster.sendMessage("��f- ��a�ֽ�ų ��7: ��d����ȭ��\n��7����ȭ���� ���� ���� ���� �̵��ӵ��� 6�ʰ� ������ ����߸��ϴ�.\n��c4�ʰ� �ƹ��� ������ ���ϸ� ȿ���� ������ϴ�.\n");	
		bowmaster.sendMessage("��f- ��a������ų ��7: ��d�ҳ��� ȭ��\n��7�������� 6�� ���� ȭ���� �߻��մϴ�. �߷��� ������ ���� ���� ������ ������ ���� �� �����ϴ�.\n��c3�ʰ� �ƹ��� ������ ���ϸ� ȿ���� ������ϴ�.\n");
		bowmaster.sendMessage("��f- ��2�ñر��f(��c���Ž� ��밡�ɡ�f) ��7: ��d����ȭ��\n��7��ź�� ������ ȭ���� �߻��մϴ�. ����� ���� �� 2.5���� �����մϴ�.\n��c10�ʰ� �ƹ��� ������ ���ϸ� ȿ���� ������ϴ�.\n");
		bowmaster.sendMessage("��f- ��2�нú��f(��c���Ž� ��밡�ɡ�f) ��7: ��d������ ����\n��7�� �� �����ϰ� ���ϴ�. ��� ��ų�� ��ȿ�ð��� 1.5�谡 �˴ϴ�.");
	}
	
	public void PrimarySkill(final Player p){
		Long time = can_passive ? 160l : 80l;
		if(Cooldown.Checkcooldown(p, "�ֽ�ų")){
			Cooldown.Setcooldown(p, "�ֽ�ų", 13, true);
			Freezing_arrow = true;
			p.getWorld().playSound(p.getLocation(), Sound.CLICK, 2.0f, 2.0f);
			ParticleEffect.WATER_SPLASH.display(1, 1, 1, 0, 15, p.getLocation(), 50);
			Bukkit.getScheduler().scheduleSyncDelayedTask(Main.instance, new Runnable(){
				public void run(){
					if(Freezing_arrow){
						p.sendMessage(Main.MS+"- ��7����ȭ���� ��ȿ�ð��� �������ϴ�.");
						Freezing_arrow = false;
					}
				}
			}, time);
		}
	}
	
	public void SecondarySkill(final Player p){
		Long time = can_passive ? 120l : 60l;
		if(Cooldown.Checkcooldown(p, "������ų")){
			Cooldown.Setcooldown(p, "������ų", 22, true);
			Speed_arrow = true;
			p.getWorld().playSound(p.getLocation(), Sound.CLICK, 2.0f, 2.0f);
			ParticleEffect.CLOUD.display(1, 1, 1, 0, 15, p.getLocation(), 50);
			Bukkit.getScheduler().scheduleSyncDelayedTask(Main.instance, new Runnable(){
				public void run(){
					if(Speed_arrow){
						p.sendMessage(Main.MS+"- ��7���߷�ȭ���� ��ȿ�ð��� �������ϴ�.");
						Speed_arrow = false;
					}
				}
			}, time);
		}
	}
	
	public void UltimateSkill(final Player p){
		Long time = can_passive ? 300l : 200l;
		if(Cooldown.Checkcooldown(p, "�ñر�")){
			Cooldown.Setcooldown(p, "�ñر�", 84, true);
			Explode_arrow = true;
			p.getWorld().playSound(p.getLocation(), Sound.CLICK, 2.0f, 2.0f);
			ParticleEffect.REDSTONE.display(1, 1, 1, 0, 20, p.getLocation(), 50);
			Bukkit.getScheduler().scheduleSyncDelayedTask(Main.instance, new Runnable(){
				public void run(){
					if(Explode_arrow){
						p.sendMessage(Main.MS+"- ��7����ȭ���� ��ȿ�ð��� �������ϴ�.");
						Explode_arrow = false;
					}
				}
			}, time);
		}
	}
	
	public void onArrowHit(Entity entity){
		if(!(entity instanceof LivingEntity)) return;
		LivingEntity t = (LivingEntity) entity;
		if(Freezing_arrow){
			Freezing_arrow = true;
			ParticleEffect.WATER_SPLASH.display(1, 1, 1, 0.02f, 20, t.getLocation(), 50);
			t.getWorld().playSound(t.getLocation(), Sound.DIG_SNOW, 2.0f, 1.0f);
			t.addPotionEffect(new PotionEffect(PotionEffectType.SLOW, 120, 49));
			t.addPotionEffect(new PotionEffect(PotionEffectType.JUMP, 120, 199));
		}
		if(Explode_arrow){
			Explode_arrow = false;
			final Location l = t.getLocation();
			ParticleEffect.REDSTONE.display(1, 1, 1, 0.02f, 150, l, 50);
			l.getWorld().playSound(t.getLocation(), Sound.FUSE, 3.0f, 0.7f);
			Bukkit.getScheduler().scheduleSyncDelayedTask(Main.instance, new Runnable(){
				public void run(){
					l.getWorld().playSound(l, Sound.FUSE, 3.0f, 0.8f);
					ParticleEffect.REDSTONE.display(1, 1, 1, 0.02f, 50, l, 50);
				}
			}, 10l);
			Bukkit.getScheduler().scheduleSyncDelayedTask(Main.instance, new Runnable(){
				public void run(){
					l.getWorld().playSound(l, Sound.FUSE, 3.0f, 0.8f);
					ParticleEffect.REDSTONE.display(1.2f, 1.2f, 1.2f, 0.02f, 200, l, 50);
				}
			}, 20l);
			Bukkit.getScheduler().scheduleSyncDelayedTask(Main.instance, new Runnable(){
				public void run(){
					l.getWorld().playSound(l, Sound.FUSE, 3.0f, 0.8f);
					ParticleEffect.REDSTONE.display(1.4f, 1.4f, 1.4f, 0.02f, 250, l, 50);
				}
			}, 30l);
			Bukkit.getScheduler().scheduleSyncDelayedTask(Main.instance, new Runnable(){
				public void run(){
					l.getWorld().playSound(l, Sound.FUSE, 3.0f, 0.9f);
					ParticleEffect.REDSTONE.display(1.6f, 1.6f, 1.6f, 0.02f, 300, l, 50);
				}
			}, 40l);
			Bukkit.getScheduler().scheduleSyncDelayedTask(Main.instance, new Runnable(){
				public void run(){
					ParticleEffect.EXPLOSION_HUGE.display(5.0f, 5.0f, 5.0f, 0.03f, 10, l, 20);
					l.getWorld().playSound(l, Sound.EXPLODE, 3.0f, 1.0f);
					for(Player t : Bukkit.getOnlinePlayers()){
						if(t.getLocation().distance(l) <= 10){
							t.damage(15-(int)t.getLocation().distance(l), bowmaster);
						}
					}
				}
			}, 50l);
		}
	}
	
	public void Active(PlayerItemHeldEvent e){
		if(e.getNewSlot() == 1){
			e.getPlayer().getInventory().setHeldItemSlot(0);
			PrimarySkill(e.getPlayer());
		}
		if(e.getNewSlot() == 2){
			e.getPlayer().getInventory().setHeldItemSlot(0);
			SecondarySkill(e.getPlayer());
		}
		if(e.getNewSlot() == 3){
			e.getPlayer().getInventory().setHeldItemSlot(0);
			if(!can_Ultimate){ bowmaster.sendMessage(Main.MS+"�ñر�� �������� ������ ��밡���մϴ�."); return;}
			UltimateSkill(e.getPlayer());
		}
	}
	
	public void onLauch(ProjectileLaunchEvent e){
		if(e.getEntity() instanceof Arrow){
			if(!Speed_arrow) return;
			Arrow arrow = (Arrow) e.getEntity();
			Player p = (Player) arrow.getShooter();
			Speed_arrow = false;
			Block target = p.getTargetBlock(null, 150);
			Vector velocity = (target.getLocation().toVector().subtract(arrow.getLocation().toVector()).normalize()).multiply(6);
			arrow.setVelocity(velocity);
		}
	}
}
