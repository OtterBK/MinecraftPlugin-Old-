package me.Bokum.MOB.Attacker;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.Color;
import org.bukkit.FireworkEffect;
import org.bukkit.FireworkEffect.Type;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.block.Block;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Fireball;
import org.bukkit.entity.Firework;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.player.PlayerItemHeldEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.FireworkMeta;
import org.bukkit.inventory.meta.ItemMeta;

import de.slikey.effectlib.effect.LineEffect;
import de.slikey.effectlib.effect.StarEffect;
import me.Bokum.MOB.Main;
import me.Bokum.MOB.Ability.Ability;
import me.Bokum.MOB.Game.Cooldown;
import me.Bokum.MOB.Game.MobSystem;

public class FlameWizard extends Ability{

	public Player flameWizard;
	public boolean skill2 = false;
	public boolean hiding = false;
	
	public FlameWizard(String playername, Player p){
		super(playername, "�÷��� ���ڵ�");
		flameWizard = p;
		
		ItemStack item = new ItemStack(265, 1);
		ItemMeta meta = item.getItemMeta();
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
		flameWizard.sendMessage("��6============= ��f[ ��b�÷��� ���ڵ� ��f] - ��e������ ��7: ��cAttacker ��6=============\n"
				+ "��f- ��a�ֽ�ų ��7: ��d���̾\n��7���濡 ȭ������ �߻��մϴ�. ��cȭ������ ���� ���� 14�� �������� 4�ʰ� ȭ����°� �˴ϴ�.\n "
				+ "��f- ��a������ų ��7: ��d�÷��� ��\n��77ĭ���� �ִ� ���� 12�ʰ� ȭ����·� ����ϴ�.\n"
				+ "��f- ��2�ñر��f(��c���Ž� ��밡�ɡ�f) ��7: ��d���׿�\n��720ĭ���� �ٶ󺸴� ���� �ټ��� ȭ������ ��ȯ�մϴ�.\n"
				+ "��f- ��2�нú��f(��c���Ž� ��밡�ɡ�f) ��7: ��d�÷��� �Ͽ��\n��7��� ȭ������� ���ӽð� 4������");
	}
	
	public void PrimarySkill(final Player p){
		if(Cooldown.Checkcooldown(p, "�ֽ�ų")){
			Cooldown.Setcooldown(p, "�ֽ�ų", 7, false);
			Location loc = p.getEyeLocation().toVector().add(p.getLocation().getDirection()).toLocation(p.getWorld(), p.getLocation().getYaw(), p.getLocation().getPitch());
			Fireball fireball = p.getWorld().spawn(loc, Fireball.class);
			fireball.setShooter(p);
			fireball.setIsIncendiary(false);
			fireball.setFireTicks(can_passive ? 160 : 80);
			p.getWorld().playSound(p.getLocation(), Sound.GHAST_FIREBALL, 2.0f, 1.0f);
		}
	}
	
	public void onDamaged(EntityDamageEvent e){
		e.setDamage((int) (e.getDamage()*1.5));
	}
	
	public void SecondarySkill(final Player p){
		if(Cooldown.Checkcooldown(p, "������ų")){
			Cooldown.Setcooldown(p, "������ų", 22, false);
			Firework f = p.getWorld().spawn(p.getLocation(), Firework.class);
			FireworkMeta fm = f.getFireworkMeta();
			fm.addEffect(FireworkEffect.builder()
					.flicker(false)
					.trail(false)
					.with(Type.BURST)
					.withColor(Color.RED)
					.withFade(Color.LIME)
					.build());
			fm.setPower(0);
			f.setFireworkMeta(fm);
			Bukkit.getScheduler().scheduleSyncDelayedTask(Main.instance, new Runnable(){
				public void run(){
					for(Player t : Bukkit.getOnlinePlayers()){
						if(t.getLocation().distance(p.getLocation()) <= 7){
							t.setFireTicks(can_passive ? 320 : 240);
							LineEffect le = new LineEffect(Main.effectManager);
							le.particle = de.slikey.effectlib.util.ParticleEffect.FLAME;
							le.particles = 5;
							le.period = 1;
							le.iterations = 20;
							le.speed = 0f;
							le.particleOffsetX = 0;
							le.particleOffsetY = 0;
							le.particleOffsetZ = 0;
							le.setEntity(p);
							le.setTargetEntity(t);
							le.start();
							t.getWorld().playSound(t.getLocation(), Sound.ENDERMAN_HIT, 1.5f, 2.0f);
						}
					}
				}
			}, 14l);
		}
	}
	
	public void UltimateSkill(final Player p){
		if(Cooldown.Checkcooldown(p, "�ñر�")){
			Block tmpb = null;
			try{
				tmpb = p.getTargetBlock(null, 20);
			}catch(Exception exception){
				return;
			}
			if(tmpb == null || tmpb.getType() == Material.AIR){
				p.sendMessage(Main.MS+"20ĭ �̳��� ������ ����� �����մϴ�.");
				return;
			}
			final Block b = tmpb;
			StarEffect sr = new StarEffect(Main.effectManager);
			sr.period = 8;
			sr.iterations = 20;
			sr.speed = 0f;
			sr.setLocation(b.getRelative(0, 8, 0).getLocation());
			sr.start();
			Cooldown.Setcooldown(p, "�ñر�", 138, true);
			timertime = 90;
			timernum = Bukkit.getScheduler().scheduleSyncRepeatingTask(Main.instance, new Runnable(){
				public void run(){
					if(timertime-- > 0){
						Location loc = b.getRelative((MobSystem.Getrandom(0, 12)-6), 8, (MobSystem.Getrandom(0, 12)-6)).getLocation();
						Fireball fireball = p.getWorld().spawn(loc, Fireball.class);
						fireball.setShooter(p);
						fireball.setIsIncendiary(false);
						fireball.setFireTicks(can_passive ? 160 : 80);
					}else{
						Bukkit.getScheduler().cancelTask(timernum);
						p.sendMessage(Main.MS+"�ϴÿ��� �� ����� ��������ϴ�.");
					}
				}
			}, 40l, 2l);
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
			if(!can_Ultimate){ flameWizard.sendMessage(Main.MS+"�ñر�� �������� ������ ��밡���մϴ�."); return;}
			e.getPlayer().getInventory().setHeldItemSlot(0);
			UltimateSkill(e.getPlayer());
		}
	}
	
	public void onHit(EntityDamageByEntityEvent e){
		Player d = null;
		if(!(e.getEntity() instanceof LivingEntity)) return;
		LivingEntity p = (LivingEntity) e.getEntity();
		if(e.getDamager() instanceof Fireball){
			Fireball fireball = (Fireball) e.getDamager();
			if(fireball.getShooter() instanceof Player){
				d = (Player) fireball.getShooter();
			}
			if(p == null || d == null) return;
			e.setCancelled(true);
			p.getWorld().playSound(p.getLocation(), Sound.EXPLODE, 1.0f, 1.0f);
			ignore = true;
			p.damage(12, d);
			p.setFireTicks(can_passive ? 160 : 80);
		}
	}
}

