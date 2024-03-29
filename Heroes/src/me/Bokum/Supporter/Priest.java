package me.Bokum.Supporter;

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
import de.slikey.effectlib.effect.SphereEffect;
import me.Bokum.MOB.Main;
import me.Bokum.MOB.Ability.Ability;
import me.Bokum.MOB.Game.Cooldown;
import me.Bokum.MOB.Game.MobSystem;
import me.Bokum.MOB.Utility.ParticleEffect;
import me.Bokum.MOB.Utility.Vector3D;

public class Priest extends Ability{

	public Player priest;
	
	public Priest(String playername, Player p){
		super(playername, "사제");
		priest = p;
		ItemStack item = new ItemStack(280, 1);
		ItemMeta meta = item.getItemMeta();
		
		item = new ItemStack(265, 1);
		meta = item.getItemMeta();
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
		priest.sendMessage("§6============= §f[ §b사제 §f] - §e직업군 §7: §cSupporter §6=============\n"
				+ "§f- §a주스킬 §7: §d치유\n§712칸내의 바라보는 팀원의 체력을 5초에 걸쳐 10회복시켜줍니다. §c주스킬\n"
				+ "§f- §a보조스킬 §7: §d생명의 바람\n§7주위 10칸내의 팀원들(자신포함)의 체력을 8회복시켜줍니다.\n"
				+ "§f- §2궁극기§f(§c구매시 사용가능§f) §7: §d부활\n§7죽은 팀원들을 모두 부활시킵니다.\n"
				+ "§f- §2패시브§f(§c구매시 사용가능§f) §7: §d치유 강화\n§7모든 치유량이 1.25배가 됩니다.");
	}
	
	public void PrimarySkill(final Player p){
		if(Cooldown.Checkcooldown(p, "주스킬")){
			final LivingEntity t = MobSystem.Getcorsurplayer(p, 12);
			if ((t == null)) {
				p.sendMessage(Main.MS + "12칸내에 바라보는곳에 몹이 없습니다.");
			    return;
			}
			Player pt = null;
			if(t instanceof Player) pt = (Player) t;
			Cooldown.Setcooldown(p, "주스킬", 10, true);
			LineEffect le = new LineEffect(Main.effectManager);
			le.particle = de.slikey.effectlib.util.ParticleEffect.VILLAGER_HAPPY;
			le.particles = 8;
			le.period = 5;
			le.iterations = 20;
			le.speed = 0f;
			le.particleOffsetX = 0;
			le.particleOffsetY = 0;
			le.particleOffsetZ = 0;
			le.setEntity(p);
			le.setTargetEntity(t);
			le.start();
			if(pt != null) p.sendMessage(Main.MS + pt.getName() + " 님의 체력을 회복시켜줍니다.");
			if(pt != null) pt.sendMessage(Main.MS + "치유를 받고 있습니다.");
			timertime = 6;
			timernum = Bukkit.getScheduler().scheduleSyncRepeatingTask(Main.instance, new Runnable(){
				public void run(){
					if(--timertime > 0){
						MobSystem.Addhp(t, can_passive ? 3 : 2);
						t.getWorld().playSound(t.getLocation(), Sound.NOTE_PLING, 1.5f, 1.0f);
						Location loc = new Location(t.getWorld(), t.getLocation().getX(), t.getLocation().getY()+2, t.getLocation().getZ());
						ParticleEffect.HEART.display(0, 0, 0, 0, 1, loc, 20);
					}else{
						Bukkit.getScheduler().cancelTask(timernum);
					}
				}
			}, 0l, 20l);
		}
	}

	public void SecondarySkill(final Player p){
		if(Cooldown.Checkcooldown(p, "보조스킬")){
			Cooldown.Setcooldown(p, "보조스킬", 18, true);
			for(Entity entity : p.getNearbyEntities(11, 11, 11)){
				if(!(entity instanceof LivingEntity)) return;
				LivingEntity t = (LivingEntity) entity;
				if(t.getLocation().distance(p.getLocation()) <= 10){
					t.getWorld().playSound(t.getLocation(), Sound.ORB_PICKUP, 1.5f, 0.7f);
					Location loc = new Location(t.getWorld(), t.getLocation().getX(), t.getLocation().getY()+2, t.getLocation().getZ());
					ParticleEffect.HEART.display(0, 0, 0, 0, 1, loc, 20);
					MobSystem.Addhp(t, can_passive ? 12 : 8);
				}
			}

		}
	}
	
	public void UltimateSkill(final Player p){
		/*if(Cooldown.Checkcooldown(p, "궁극기")){
			int amt = 0;
			for(Player t : MobSystem.getTeam(p)){
						if(t != null){
							if(MobSystem.ablist.containsKey(t.getName())){
								Ability ability = MobSystem.ablist.get(t.getName());
								if(ability.check_dead){
									ability.Revive(t);
									t.sendMessage(Main.MS+"사제 "+p.getName()+" 님에 의하여 부활합니다.");
									amt++;
								}
							}
						}
			}
			if(amt == 0){
				p.sendMessage(Main.MS+"죽은 팀원이 없습니다.");
			}else{
				p.getWorld().playSound(p.getLocation(), Sound.CAT_PURREOW, 2.0f, 0.5f);
				p.sendMessage(Main.MS+amt+"명의 팀원을 부활시켰습니다.");
				Cooldown.Setcooldown(p, "궁극기", 88, true);
			}
		}*/
	}
	
	public void onDamaged(EntityDamageEvent e){
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
			if(!can_Ultimate){ priest.sendMessage(Main.MS+"궁극기는 상점에서 구매후 사용가능합니다."); return;}
			e.getPlayer().getInventory().setHeldItemSlot(0);
			UltimateSkill(e.getPlayer());
		}
	}
	
	public void onHit(EntityDamageByEntityEvent e){
	}
}



