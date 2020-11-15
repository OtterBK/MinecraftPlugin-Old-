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
		super(playername, "플레임 위자드");
		flameWizard = p;
		
		ItemStack item = new ItemStack(265, 1);
		ItemMeta meta = item.getItemMeta();
		meta.setDisplayName("§f[ §c주스킬 §f]");
		item.setItemMeta(meta);
		
		p.getInventory().setItem(1 ,item);
		
		item = new ItemStack(264, 1);
		meta = item.getItemMeta();
		meta.setDisplayName("§f[ §c보조스킬 §f]");
		item.setItemMeta(meta);
		
		p.getInventory().setItem(2 ,item);
		
		item = new ItemStack(388, 1);
		meta = item.getItemMeta();
		meta.setDisplayName("§f[ §c궁극기 §f]");
		item.setItemMeta(meta);
		
		p.getInventory().setItem(3 ,item);
	}
	
	public void description(){
		flameWizard.sendMessage("§6============= §f[ §b플레임 위자드 §f] - §e직업군 §7: §cAttacker §6=============\n"
				+ "§f- §a주스킬 §7: §d파이어볼\n§7전방에 화염구를 발사합니다. §c화염구에 맞은 적은 14의 데미지와 4초간 화상상태가 됩니다.\n "
				+ "§f- §a보조스킬 §7: §d플레임 워\n§77칸내에 있는 적을 12초간 화상상태로 만듭니다.\n"
				+ "§f- §2궁극기§f(§c구매시 사용가능§f) §7: §d메테오\n§720칸내에 바라보는 곳에 다수의 화염구를 소환합니다.\n"
				+ "§f- §2패시브§f(§c구매시 사용가능§f) §7: §d플레임 하운드\n§7모든 화상상태의 지속시간 4초증가");
	}
	
	public void PrimarySkill(final Player p){
		if(Cooldown.Checkcooldown(p, "주스킬")){
			Cooldown.Setcooldown(p, "주스킬", 7, false);
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
		if(Cooldown.Checkcooldown(p, "보조스킬")){
			Cooldown.Setcooldown(p, "보조스킬", 22, false);
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
		if(Cooldown.Checkcooldown(p, "궁극기")){
			Block tmpb = null;
			try{
				tmpb = p.getTargetBlock(null, 20);
			}catch(Exception exception){
				return;
			}
			if(tmpb == null || tmpb.getType() == Material.AIR){
				p.sendMessage(Main.MS+"20칸 이내의 블럭에만 사용이 가능합니다.");
				return;
			}
			final Block b = tmpb;
			StarEffect sr = new StarEffect(Main.effectManager);
			sr.period = 8;
			sr.iterations = 20;
			sr.speed = 0f;
			sr.setLocation(b.getRelative(0, 8, 0).getLocation());
			sr.start();
			Cooldown.Setcooldown(p, "궁극기", 138, true);
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
						p.sendMessage(Main.MS+"하늘에서 온 재앙은 사라졌습니다.");
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
			if(!can_Ultimate){ flameWizard.sendMessage(Main.MS+"궁극기는 상점에서 구매후 사용가능합니다."); return;}
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

