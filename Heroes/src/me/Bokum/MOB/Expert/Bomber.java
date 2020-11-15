package me.Bokum.MOB.Expert;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.block.Block;
import org.bukkit.entity.Entity;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.event.player.PlayerItemHeldEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

import de.slikey.effectlib.effect.LineEffect;
import me.Bokum.MOB.Main;
import me.Bokum.MOB.Ability.Ability;
import me.Bokum.MOB.Game.Cooldown;
import me.Bokum.MOB.Game.MobSystem;
import me.Bokum.MOB.Utility.ParticleEffect;

public class Bomber extends Ability{

	public Player bomber;
	public List<Location> bomblist = new ArrayList<Location>();
	
	public Bomber(String playername, Player p){
		super(playername, "�չ�");
		bomber = p;
		ItemStack item = new ItemStack(318, 1);
		ItemMeta meta = item.getItemMeta();
		
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
	}
	
	public void description(){
		bomber.sendMessage("��6============= ��f[ ��b�չ� ��f] - ��e������ ��7: ��cExpert ��6=============\n"
				+ "��f- ��a�ֽ�ų ��7: ��d��ź ��ġ\n��7��ź�� ��ġ�մϴ�. ��c�ִ� 3������\n"
				+ "��f- ��a������ų ��7: ��d����\n��7��ġ�� ��ź�� �����մϴ�. �ִ�8 ������\n"
				+ "��f- ��2�ñر��f(��c���Ž� ��밡�ɡ�f) ��7: ��d���� ����\n��730ĭ�� �ٶ󺸴� ���� ���� ���� �̻����� �߻��մϴ�. �ִ�28 ������\n"
				+ "��f- ��2�нú��f(��c���Ž� ��밡�ɡ�f) ��7: ��d����\n��7����� 20%Ȯ���� �����մϴ�.");
	}
	
	public void PrimarySkill(final Player p){
		if(Cooldown.Checkcooldown(p, "�ֽ�ų")){
			Cooldown.Setcooldown(p, "�ֽ�ų", 3, true);
			p.getWorld().playSound(p.getLocation(), Sound.CHICKEN_HURT, 2.0f, 2.0f);
			ParticleEffect.SNOWBALL.display(0.2f, 0.2f, 0.2f, 0, 20, p.getLocation(), 30);
			p.sendMessage(Main.MS+"��ź�� ��ġ�߽��ϴ�.");
			if(bomblist.size() >= 3){
				bomblist.get(0).getBlock().setType(Material.AIR);
				p.getLocation().getBlock().setType(Material.WOOD_PLATE);
				p.sendMessage(Main.MS+"��ź �ִ뷮�� �Ѿ� 1��° ��ź�� ��ü�߽��ϴ�.");
				bomblist.set(0, p.getLocation());
			} else {
				bomblist.add(p.getLocation());
				p.getLocation().getBlock().setType(Material.WOOD_PLATE);
			}
			p.setExp(1);
		}
	}

	public void SecondarySkill(final Player p){
		if(bomblist.size() <= 0){
			p.sendMessage(Main.MS+"��ġ�� ��ź�� �����ϴ�.");
		} else {
			int amt = 1;
			for(final Location l : bomblist){
				Bukkit.getScheduler().scheduleSyncDelayedTask(Main.instance, new Runnable(){
					public void run(){
						l.getBlock().setType(Material.AIR);
						ParticleEffect.EXPLOSION_LARGE.display(1f, 1f, 1f, 0.1f, 15, l, 30);
						l.getWorld().playSound(l, Sound.EXPLODE, 2.0f, 1.0f);
						for(Entity entity : p.getNearbyEntities(6, 6, 6)){
							if(!(entity instanceof LivingEntity)) return;
							LivingEntity t = (LivingEntity) entity;
							if(t.getLocation().distance(l) < 6){
								ignore = true;
								t.damage(10-(int)t.getLocation().distance(l), p);
							}
						}
					}
				}, amt++*10);
			}
			bomblist.clear();
		}
	}
	
	public void UltimateSkill(final Player p){
		if(Cooldown.Checkcooldown(p, "�ñر�")){
			final Block b = p.getTargetBlock(null, 30);
			if(b == null || b.getType() == Material.AIR){
				p.sendMessage(Main.MS+"30ĭ�� ���� ���� ����ϼ���.");
				return;
			}
			Cooldown.Setcooldown(p, "�ñر�", 98, true);
			p.getWorld().playSound(p.getLocation(), Sound.ENDERDRAGON_GROWL, 2.0f, 0.1f);
			LineEffect le = new LineEffect(Main.effectManager);
			le.particles = 30;
			le.period = 5;
			le.iterations = 20;
			le.speed = 0f;
			le.particleOffsetX = 0;
			le.particleOffsetY = 0;
			le.particleOffsetZ = 0;
			le.setEntity(p);
			le.setTargetLocation(b.getLocation());
			le.start();
			p.addPotionEffect(new PotionEffect(PotionEffectType.SLOW, 80, 99));
			p.addPotionEffect(new PotionEffect(PotionEffectType.JUMP, 80, 199));
			Bukkit.getScheduler().scheduleSyncDelayedTask(Main.instance, new Runnable(){
				public void run(){
					p.getLocation().getWorld().playSound(p.getLocation(), Sound.CLICK, 1.0f, 0.5f);
					b.getWorld().playSound(b.getLocation(), Sound.BLAZE_HIT, 2.5f, 0.25f);
				}
			}, 0l);
			Bukkit.getScheduler().scheduleSyncDelayedTask(Main.instance, new Runnable(){
				public void run(){
					p.getLocation().getWorld().playSound(p.getLocation(), Sound.CLICK, 1.0f, 0.5f);
					b.getWorld().playSound(b.getLocation(), Sound.BLAZE_HIT, 2.5f, 0.25f);
				}
			}, 20l);
			Bukkit.getScheduler().scheduleSyncDelayedTask(Main.instance, new Runnable(){
				public void run(){
					p.getLocation().getWorld().playSound(p.getLocation(), Sound.CLICK, 1.0f, 0.5f);
					b.getWorld().playSound(b.getLocation(), Sound.BLAZE_HIT, 2.5f, 0.25f);
				}
			}, 40l);
			Bukkit.getScheduler().scheduleSyncDelayedTask(Main.instance, new Runnable(){
				public void run(){
					p.getLocation().getWorld().playSound(p.getLocation(), Sound.CLICK, 1.0f, 0.5f);
					b.getWorld().playSound(b.getLocation(), Sound.BLAZE_HIT, 2.5f, 0.25f);
				}
			}, 60l);
			Bukkit.getScheduler().scheduleSyncDelayedTask(Main.instance, new Runnable(){
				public void run(){
					if(!p.isDead()){
						p.getLocation().getWorld().playSound(p.getLocation(), Sound.ENDERDRAGON_DEATH, 1.0f, 2.5f);
						ParticleEffect.EXPLOSION_HUGE.display(3f, 3f, 3f, 0.1f, 16, b.getLocation(), 30);
						b.getLocation().getWorld().playSound(b.getLocation(), Sound.EXPLODE, 2.0f, 1.0f);
						for(Entity entity : p.getNearbyEntities(12, 12, 12)){
							if(!(entity instanceof LivingEntity)) return;
							LivingEntity t = (LivingEntity) entity;
							if(t.getLocation().distance(b.getLocation()) < 12){
								ignore = true;
								t.damage(29-(int)t.getLocation().distance(b.getLocation()), p);
							}
						}
					}
				}
			}, 80l);
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
			if(!can_Ultimate){ bomber.sendMessage(Main.MS+"�ñر�� �������� ������ ��밡���մϴ�."); return;}
			e.getPlayer().getInventory().setHeldItemSlot(0);
			UltimateSkill(e.getPlayer());
		}
	}
	
	public void onDeath(PlayerDeathEvent e){
		for(Location l : bomblist){
			if(l.getBlock().getType() == Material.WOOD_PLATE)
			l.getBlock().setType(Material.AIR);
		}
		bomblist.clear();
		if(can_passive && MobSystem.Getrandom(0, 9) == 0){
			Player p = e.getEntity();
			p.sendMessage(Main.MS+"�����߽��ϴ�!");
			for(Entity entity : p.getNearbyEntities(7, 7, 7)){
				if(!(entity instanceof LivingEntity)) return;
				LivingEntity t = (LivingEntity) entity;
				if(t.getLocation().distance(p.getLocation()) < 7){
					t.damage(7-(int)t.getLocation().distance(p.getLocation()), p);
				}
			}
		}
	}
	
	public void onHit(EntityDamageByEntityEvent e){
	}
}


