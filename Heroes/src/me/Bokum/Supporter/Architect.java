package me.Bokum.Supporter;

import me.Bokum.MOB.Ability.Ability;

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
import de.slikey.effectlib.effect.SphereEffect;
import me.Bokum.MOB.Main;
import me.Bokum.MOB.Game.Cooldown;
import me.Bokum.MOB.Game.MobSystem;
import me.Bokum.MOB.Utility.ParticleEffect;
import me.Bokum.MOB.Utility.Vector3D;

public class Architect extends Ability{

	public Player architect;
	
	public Architect(String playername, Player p){
		super(playername, "건축가");
		architect = p;
		ItemStack item = new ItemStack(Material.DIAMOND_PICKAXE, 1);
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
	
	public void onDamaged(EntityDamageEvent e){
			e.setDamage((int) (e.getDamage()*1.5));
	}
	
	public void description(){
		architect.sendMessage("§6============= §f[ §b건축가 §f] - §e직업군 §7: §cAttacker §6=============\n"
				+ "§f- §a주스킬 §7: §d터렛 건설\n§73초의 시간을 들여 터렛을 건설합니다. §c건설중 이동불가, 공격받을시 건설 취소\n"
				+ "§f- §a보조스킬 §7: §d터렛 강화/포탑 수리\n§75초의 시간을 들여 터렛을 강화합니다. §c포탑이 이미 강화된 상태면 포탑을 수리함\n"
				+ "§f- §2궁극기§f(§c구매시 사용가능§f) §7: §d슈퍼 터렛\n§710초간 터렛의 체력, 공격속도, 공격력이 증가합니다.\n"
				+ "§f- §2패시브§f(§c구매시 사용가능§f) §7: §d고급 재료\n§7터렛의 체력이 1.25배가 됩니다.");
	}
	
	/*public void PrimarySkill(final Player p){
		if(Cooldown.Checkcooldown(p, "주스킬")){
			final Player t = MobSystem.Getcorsurplayer(p, 12);
			if ((t == null) || (MobSystem.getEnemy(p).contains(t))) {
				p.sendMessage(Main.MS + "12칸내에 바라보는곳에 팀이 없습니다.");
			    return;
			}
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
			p.sendMessage(Main.MS + t.getName() + " 님의 체력을 회복시켜줍니다.");
			t.sendMessage(Main.MS + "치유를 받고 있습니다.");
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
						p.sendMessage(Main.MS + t.getName() + " 님의 회복이 끝났습니다.");
					}
				}
			}, 0l, 20l);
		}
	}

	public void SecondarySkill(final Player p){
		if(Cooldown.Checkcooldown(p, "보조스킬")){
			Cooldown.Setcooldown(p, "보조스킬", 18, true);
			for(Player t : MobSystem.getTeam(p)){
				if(t.getLocation().distance(p.getLocation()) <= 10){
					t.getWorld().playSound(t.getLocation(), Sound.ORB_PICKUP, 1.5f, 0.7f);
					Location loc = new Location(t.getWorld(), t.getLocation().getX(), t.getLocation().getY()+2, t.getLocation().getZ());
					ParticleEffect.HEART.display(0, 0, 0, 0, 1, loc, 20);
					p.sendMessage(Main.MS + t.getName() + " 님의 체력을 회복시켜주셨습니다.");
					MobSystem.Addhp(t, can_passive ? 12 : 8);
				}
			}

		}
	}
	
	/*public void UltimateSkill(final Player p){
		if(Cooldown.Checkcooldown(p, "궁극기")){
			int amt = 0;
			if((MobSystem.bluelist.contains(p) ? MobSystem.bluedead.size() : MobSystem.reddead.size()) <= 0){
				p.sendMessage(Main.MS+"죽은 팀원이 없습니다.");
				return;
			}
			for(Location l : MobSystem.bluelist.contains(p) ? MobSystem.bluedead.keySet() : MobSystem.reddead.keySet()){
					if((MobSystem.bluelist.contains(p) ? MobSystem.bluedead : MobSystem.reddead).containsKey(l)){
						Player t = (MobSystem.bluelist.contains(p) ? MobSystem.bluedead : MobSystem.reddead).get(l);
						if(t != null){
							if(MobSystem.ablist.containsKey(t.getName())){
								Ability ability = MobSystem.ablist.get(t.getName());
								ability.Revive(t);
								t.sendMessage(Main.MS+"사제 "+p.getName()+" 님에 의하여 부활합니다.");
								amt++;
							}
						}
					}
			}
				p.sendMessage(Main.MS+amt+"명의 팀원을 부활시켰습니다.");
				Cooldown.Setcooldown(p, "궁극기", 213, true);
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
			if(!can_Ultimate){ architect.sendMessage(Main.MS+"궁극기는 상점에서 구매후 사용가능합니다."); return;}
			e.getPlayer().getInventory().setHeldItemSlot(0);
			//UltimateSkill(e.getPlayer());
		}
	}
	
	public void onHit(EntityDamageByEntityEvent e){
		e.setDamage(4);
	}*/
}


