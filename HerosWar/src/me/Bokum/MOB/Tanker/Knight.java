package me.Bokum.MOB.Tanker;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.entity.EntityDamageEvent.DamageCause;
import org.bukkit.event.player.PlayerItemHeldEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.util.Vector;

import de.slikey.effectlib.effect.DonutEffect;
import de.slikey.effectlib.effect.HelixEffect;
import de.slikey.effectlib.effect.LineEffect;
import me.Bokum.MOB.Main;
import me.Bokum.MOB.Ability.Ability;
import me.Bokum.MOB.Game.Cooldown;
import me.Bokum.MOB.Game.MobSystem;
import me.Bokum.MOB.Utility.ParticleEffect;

public class Knight extends Ability{

	public Player knight;
	
	public Knight(String playername, Player p){
		super(playername, "���");
		knight = p;
		ItemStack item = new ItemStack(267, 1);
		ItemMeta meta = item.getItemMeta();
		meta.setDisplayName("��f[ ��b��� ��f]");
		List<String> lorelist = new ArrayList<String>();
		lorelist.add("��f- ��7���ݷ� : ��65~7");
		lorelist.add("��f- ��7������ ���� : ��6���� ������ x 0.75");
		lorelist.add("��f- ��7���� : ��6�ڡڡڡ١�");
		lorelist.add("��f- ��7�⵿ : ��6�ڡڡڡڡ�");
		lorelist.add("��f- ��7���� : ��6�ڡ١١١�");
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
	}
	
	public void description(){
		knight.sendMessage("��6============= ��f[ ��b��� ��f] - ��e������ ��7: ��cTanker ��6=============\n"
				+ "��f- ��a�ֽ�ų ��7: ��d����\n��7�������� �����մϴ�. ��c�����ϸ鼭 ������.\n"
				+ "��f- ��a������ų ��7: ��d���� �庮\n��74ĭ�̳��� ������ ���� �庮�� ���� 2�ʰ� ��ų�� ����� �� �������մϴ�.\n"
				+ "��f- ��2�ñر��f(��c���Ž� ��밡�ɡ�f) ��7: ��d���� ����\n��7�������� �����ϸ� ��λ��� ������ ���������ݴϴ�..\n"
				+ "��f- ��2�нú��f(��c���Ž� ��밡�ɡ�f) ��7: ��d£�� ���\n��7���� �庮�� ħ�� ���ӽð��� 1�� �����մϴ�.");
	}
	
	public void PrimarySkill(final Player p){
		if(Cooldown.Checkcooldown(p, "�ֽ�ų")){
			Cooldown.Setcooldown(p, "�ֽ�ų", 6, true);
			p.getWorld().playSound(p.getLocation(), Sound.GHAST_FIREBALL, 2.0f, 2.5f);
		    p.setVelocity(p.getLocation().getDirection().multiply(2.5D).setY(0.1D));
		    ParticleEffect.CLOUD.display(0f, 0f, 0f, 0.2f, 15, p.getLocation(), 25);
		}
	}

	public void SecondarySkill(final Player p){
		if(Cooldown.Checkcooldown(p, "������ų")){
			Cooldown.Setcooldown(p, "������ų", 12, true);
			p.getWorld().playSound(p.getLocation(), Sound.SKELETON_HURT, 2.0f, 0.5f);
			ParticleEffect.TOWN_AURA.display(0, 0, 0, 0.1f, 15, p.getLocation(), 30);
		    HelixEffect he = new HelixEffect(Main.effectManager);
		    he.particle = de.slikey.effectlib.util.ParticleEffect.SPELL_MOB;
		    he.period = 1;
		    he.particles = 20;
		    he.radius = 4;
		    he.setEntity(p);
		    he.start();
		    for(Player t : MobSystem.getEnemy(p)){
		    	if(t.getLocation().distance(p.getLocation()) <= 4){
		    		MobSystem.CancelSkill(t, can_passive ? 3 : 2);
					t.getWorld().playSound(p.getLocation(), Sound.EAT, 2.0f, 0.7f);
		    	}
		    }
		}
	}
	
	public void UltimateSkill(final Player p){
		if(Cooldown.Checkcooldown(p, "�ñر�")){
			Cooldown.Setcooldown(p, "�ñر�", 71, true);
			Location l = p.getLocation();
			Bukkit.getScheduler().scheduleSyncDelayedTask(Main.instance, new Runnable(){
				public void run(){
					p.getWorld().playSound(p.getLocation(), Sound.GHAST_FIREBALL, 2.0f, 2.5f);
				    p.setVelocity(p.getLocation().getDirection().multiply(2.5D).setY(0.1D));
				    ParticleEffect.CLOUD.display(0f, 0f, 0f, 0.2f, 15, p.getLocation(), 25);
				    for(Player t : MobSystem.getEnemy(p)){
				    	if(t.getLocation().distance(p.getLocation()) <= 5){
				    		t.damage(7, p);
				    		MobSystem.CancelSkill(t, 2);
							t.getWorld().playSound(p.getLocation(), Sound.HURT, 2.0f, 0.7f);
				    	}
				    }
				    DonutEffect dn = new DonutEffect(Main.effectManager);
				    dn.particle = de.slikey.effectlib.util.ParticleEffect.SPELL_MOB;
				    dn.period = 3;
				    dn.iterations = 20;
					dn.setEntity(p);
					dn.start();
				}
			}, 0l);
			Bukkit.getScheduler().scheduleSyncDelayedTask(Main.instance, new Runnable(){
				public void run(){
					p.getWorld().playSound(p.getLocation(), Sound.GHAST_FIREBALL, 2.0f, 2.5f);
				    p.setVelocity(p.getLocation().getDirection().multiply(2.5D).setY(0.1D));
				    ParticleEffect.CLOUD.display(0f, 0f, 0f, 0.2f, 15, p.getLocation(), 25);
				    for(Player t : MobSystem.getEnemy(p)){
				    	if(t.getLocation().distance(p.getLocation()) <= 5){
				    		t.damage(7, p);
				    		MobSystem.CancelSkill(t, 2);
							t.getWorld().playSound(p.getLocation(), Sound.HURT, 2.0f, 0.7f);
				    	}
				    }
				    DonutEffect dn = new DonutEffect(Main.effectManager);
				    dn.particle = de.slikey.effectlib.util.ParticleEffect.SPELL_MOB;
				    dn.period = 3;
				    dn.iterations = 20;
					dn.setEntity(p);
					dn.start();
				}
			}, 10l);
			Bukkit.getScheduler().scheduleSyncDelayedTask(Main.instance, new Runnable(){
				public void run(){
					p.getWorld().playSound(p.getLocation(), Sound.GHAST_FIREBALL, 2.0f, 2.5f);
				    p.setVelocity(p.getLocation().getDirection().multiply(2.5D).setY(0.1D));
				    ParticleEffect.CLOUD.display(0f, 0f, 0f, 0.2f, 15, p.getLocation(), 25);
				    for(Player t : MobSystem.getEnemy(p)){
				    	if(t.getLocation().distance(p.getLocation()) <= 5){
				    		t.damage(7, p);
				    		MobSystem.CancelSkill(t, 2);
							t.getWorld().playSound(p.getLocation(), Sound.HURT, 2.0f, 0.7f);
				    	}
				    }
				    DonutEffect dn = new DonutEffect(Main.effectManager);
				    dn.particle = de.slikey.effectlib.util.ParticleEffect.SPELL_MOB;
				    dn.period = 3;
				    dn.iterations = 20;
					dn.setEntity(p);
					dn.start();
				}
			}, 20l);
			Bukkit.getScheduler().scheduleSyncDelayedTask(Main.instance, new Runnable(){
				public void run(){
					p.getWorld().playSound(p.getLocation(), Sound.GHAST_FIREBALL, 2.0f, 2.5f);
				    p.setVelocity(p.getLocation().getDirection().multiply(2.5D).setY(0.1D));
				    ParticleEffect.CLOUD.display(0f, 0f, 0f, 0.2f, 15, p.getLocation(), 25);
				    for(Player t : MobSystem.getEnemy(p)){
				    	if(t.getLocation().distance(p.getLocation()) <= 5){
				    		t.damage(7, p);
				    		MobSystem.CancelSkill(t, 2);
							t.getWorld().playSound(p.getLocation(), Sound.HURT, 2.0f, 0.7f);
				    	}
				    }
				    DonutEffect dn = new DonutEffect(Main.effectManager);
				    dn.particle = de.slikey.effectlib.util.ParticleEffect.SPELL_MOB;
				    dn.period = 3;
				    dn.iterations = 20;
					dn.setEntity(p);
					dn.start();
				}
			}, 30l);
			Bukkit.getScheduler().scheduleSyncDelayedTask(Main.instance, new Runnable(){
				public void run(){
					p.getWorld().playSound(p.getLocation(), Sound.GHAST_FIREBALL, 2.0f, 2.5f);
				    p.setVelocity(p.getLocation().getDirection().multiply(2.5D).setY(0.1D));
				    ParticleEffect.CLOUD.display(0f, 0f, 0f, 0.2f, 15, p.getLocation(), 25);
				    for(Player t : MobSystem.getEnemy(p)){
				    	if(t.getLocation().distance(p.getLocation()) <= 5){
				    		t.damage(7, p);
				    		MobSystem.CancelSkill(t, 2);
							t.getWorld().playSound(p.getLocation(), Sound.HURT, 2.0f, 0.7f);
				    	}
				    }
				    DonutEffect dn = new DonutEffect(Main.effectManager);
				    dn.particle = de.slikey.effectlib.util.ParticleEffect.SPELL_MOB;
				    dn.period = 3;
				    dn.iterations = 20;
					dn.setEntity(p);
					dn.start();
				}
			}, 40l);
		}
	}
	
	public void onDamaged(EntityDamageEvent e){
		e.setDamage((int) (e.getDamage()*0.75));
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
			if(!can_Ultimate){ knight.sendMessage(Main.MS+"�ñر�� �������� ������ ��밡���մϴ�."); return;}
			e.getPlayer().getInventory().setHeldItemSlot(0);
			UltimateSkill(e.getPlayer());
		}
	}
	
	public void onHit(EntityDamageByEntityEvent e){
		e.setDamage(MobSystem.Getrandom(5, 6));
	}
}



