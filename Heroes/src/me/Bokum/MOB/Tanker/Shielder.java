package me.Bokum.MOB.Tanker;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.entity.Entity;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.player.PlayerItemHeldEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

import de.slikey.effectlib.effect.GridEffect;
import me.Bokum.MOB.Main;
import me.Bokum.MOB.Ability.Ability;
import me.Bokum.MOB.Game.Cooldown;
import me.Bokum.MOB.Game.MobSystem;
import me.Bokum.MOB.Utility.ParticleEffect;

public class Shielder extends Ability{

	public Player shielder;
	public boolean hiding = false;
	public Shielder(String playername, Player p){
		super(playername, "쉴더");
		shielder = p;
		ItemStack item = new ItemStack(272, 1);
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
		
		Timer();
	}
	
	public void Timer(){
		timernum = Bukkit.getScheduler().scheduleSyncRepeatingTask(Main.instance, new Runnable(){
			public void run(){
				if(shielder.getExp() < 1 && (shielder.getItemInHand() != null && shielder.getItemInHand().getType() != Material.IRON_INGOT)){
					if(can_passive){
						shielder.setExp(shielder.getExp()+0.12f > 1 ? 1 : shielder.getExp()+0.12f);
					}else{
						shielder.setExp(shielder.getExp()+0.08f > 1 ? 1 : shielder.getExp()+0.08f);
					}
					shielder.getWorld().playSound(shielder.getLocation(), Sound.ITEM_PICKUP, 1.0f, 2.0f);
				}
			}
		}, 0l, 20l);
	}
	
	public void description(){
		shielder.sendMessage("§6============= §f[ §b쉴더 §f] - §e직업군 §7: §cTanker §6=============\n"
				+ "§f- §a주스킬 §7: §d장벽\n§7반경 8칸을 덮는 장벽을 생성합니다. §c반경내에 모든 팀원이 받는 데미지는 1이되며 장벽이 데미지를 받습니다.\n "
				+ "§f- §a보조스킬 §7: §d\n§7반경 6칸내에 있는 적을 튕겨냅니다.\n"
				+ "§f- §2궁극기§f(§c구매시 사용가능§f) §7: §d지면 강타\n§7주변 8칸이내에 있는 모든 적에게 7의 데미지를 주고 8초간 기절시킵니다.\n§c기절 상태에서는 스킬을 사용할 수 없습니다.\n"
				+ "§f- §2패시브§f(§c구매시 사용가능§f) §7: §d장벽 안정\n§7장벽 회복속도 1.5배");
	}
	
	public void PrimarySkill(final Player p){
			p.getWorld().playSound(p.getLocation(), Sound.ANVIL_USE, 2.0f, 4.0f);
			timernum1 = Bukkit.getScheduler().scheduleSyncRepeatingTask(Main.instance, new Runnable(){
				public void run(){
						if(shielder.getItemInHand() != null && shielder.getItemInHand().getType() == Material.IRON_INGOT){
							shielder.addPotionEffect(new PotionEffect(PotionEffectType.SLOW, 72000, 1));
							/*SphereEffect particle = new SphereEffect(Main.effectManager);
							particle.setLocation(p.getLocation());
							particle.particle = de.slikey.effectlib.util.ParticleEffect.CRIT;
							particle.period = 1;
							particle.iterations = 30;
							particle.radius = 8d;
							particle.particles = 80;
							particle.speed =0;
							particle.start();*/
						}else{
							Bukkit.getScheduler().cancelTask(timernum1);
							shielder.removePotionEffect(PotionEffectType.SLOW);
						}
					}
			}, 0l, 20l);
	}
	
	public void SecondarySkill(final Player p){
		if(Cooldown.Checkcooldown(p, "보조스킬")){
			Cooldown.Setcooldown(p, "보조스킬", 26, true);
			p.getWorld().playSound(p.getLocation(), Sound.ANVIL_LAND, 2.0f, 0.5f);
			for(Entity entity : p.getNearbyEntities(7, 7, 7)){
				if(!(entity instanceof LivingEntity)) return;
				LivingEntity t = (LivingEntity) entity;
				if(t.getLocation().distance(p.getLocation()) <= 6){
						t.setVelocity(t.getLocation().getDirection().multiply(-1.5));
						t.getWorld().playSound(t.getLocation(), Sound.IRONGOLEM_THROW, 1.5f, 2.0f);
						ParticleEffect.FLAME.display(0, 0, 0, 0, 15, p.getLocation(), 20);	
				}
			}
		}
	}
	
	public void UltimateSkill(final Player p){
		if(Cooldown.Checkcooldown(p, "궁극기")){
			Cooldown.Setcooldown(p, "궁극기", 83, true);
			p.getWorld().playSound(p.getLocation(), Sound.ANVIL_LAND, 2.0f, 0.5f);
			GridEffect particle = new GridEffect(Main.effectManager);
			particle.setEntity(p); 
			particle.period = 5;
			particle.iterations = 20;
			particle.particleCount = 40;
			particle.speed =1;
			particle.start();
			for(Entity entity : p.getNearbyEntities(9, 9, 9)){
				if(!(entity instanceof LivingEntity)) return;
				LivingEntity t = (LivingEntity) entity;
				if(t.getLocation().distance(p.getLocation()) <= 8){
					if(t instanceof Player) MobSystem.CancelSkill((Player) t, 8);
					t.damage(7);
					t.addPotionEffect(new PotionEffect(PotionEffectType.SLOW, 160, 99));
					t.addPotionEffect(new PotionEffect(PotionEffectType.JUMP, 160, 199));
				}
			}
		}
	}
	
	public void Active(PlayerItemHeldEvent e){
		if(e.getNewSlot() == 1){;
			PrimarySkill(e.getPlayer());
		}
		if(e.getNewSlot() == 2){
			e.getPlayer().getInventory().setHeldItemSlot(0);
			SecondarySkill(e.getPlayer());
		}
		if(e.getNewSlot() == 3){
			if(!can_Ultimate){ shielder.sendMessage(Main.MS+"궁극기는 상점에서 구매후 사용가능합니다."); return;}
			e.getPlayer().getInventory().setHeldItemSlot(0);
			UltimateSkill(e.getPlayer());
		}
	}
	
	public void onDamaged(EntityDamageEvent e){
		e.setDamage((int) (e.getDamage()*0.66));
	}
	
	public void onHit(EntityDamageByEntityEvent e){
	}
}

