package me.Bokum.MOB.Attacker;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.Sound;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.player.PlayerItemHeldEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

import me.Bokum.MOB.Main;
import me.Bokum.MOB.Ability.Ability;
import me.Bokum.MOB.Game.Cooldown;
import me.Bokum.MOB.Game.MobSystem;
import me.Bokum.MOB.Utility.ParticleEffect;

public class PassiveMaster extends Ability{

	public Player passiver;
	public boolean skill3 = false;
	
	public PassiveMaster(String playername, Player p){
		super(playername, "패시브마스터");
		passiver = p;
		ItemStack item = new ItemStack(286, 1);
		ItemMeta meta = item.getItemMeta();
		
		item = new ItemStack(265, 1);
		meta = item.getItemMeta();
		meta.setDisplayName("§f[ §c주스킬 §f]");
		item.setItemMeta(meta);
		p.getInventory().setItem(1 ,item);
		
		p.getInventory().setItem(2 ,null);
		p.getInventory().setItem(3 ,null);
	}
	
	public void description(){
		passiver.sendMessage("§6============= §f[ §b패시브 마스터 §f] - §e직업군 §7: §cAttacker §6=============\n"
				+ "§f- §a주스킬 §7: §d\n§713칸내에 바라보는 플레이어의 뒤로 이동"
				+ "§f- §a패시브2(보조스킬) §7: §d민첩한 몸놀림\n§7데미지를 받았을 시 25%확률로 신속1버프 5초\n"
				+ "§f- §2패시브4(궁극기)§f(§c구매시 사용가능§f) §7: §d크리티컬\n§7타격시 16%확률로 데미지 1.5배.\n"
				+ "§f- §2패시브3§f(§c구매시 사용가능§f) §7: §d매의 눈\n§725%확률로 모든 공격 회피");
	}
	
	public void PrimarySkill(final Player p){
		if(Cooldown.Checkcooldown(p, "주스킬")){
			LivingEntity t = MobSystem.Getcorsurplayer(p, 13);
			if(t == null){
				p.sendMessage(Main.MS+"13칸내에 바라보는곳에 엔티티가 없습니다.");
				return;
			}
			Cooldown.Setcooldown(p, "주스킬", 14, true);
			t.getWorld().playSound(t.getLocation(), Sound.BAT_TAKEOFF, 1.5f, 1.7f);
			p.getWorld().playSound(t.getLocation(), Sound.BAT_TAKEOFF, 1.5f, 1.7f);
			ParticleEffect.CLOUD.display(0.1f, 0.1f, 0.1f, 0, 15, p.getLocation(), 20);
			ParticleEffect.CLOUD.display(0.1f, 0.1f, 0.1f, 0, 15, t.getLocation(), 20);
			p.teleport(t.getLocation().subtract(t.getEyeLocation().getDirection()));
		}
	}
	
	public void Active(PlayerItemHeldEvent e){
		if(e.getNewSlot() == 1){
			e.getPlayer().getInventory().setHeldItemSlot(0);
			PrimarySkill(e.getPlayer());
		}
	}
	
	public void onHit(EntityDamageByEntityEvent e){
		if(passiver.getInventory().getHeldItemSlot() != 0) return;
		if(can_passive && MobSystem.Getrandom(0, 5) == 0){
			Player p = (Player) e.getDamager();
			p.sendMessage(Main.MS+"크리티컬!");
			p.getWorld().playSound(p.getLocation(), Sound.FIRE_IGNITE, 1.5f, 0.2f);
			ParticleEffect.CRIT.display(0.1f, 0.1f, 0.1f, 0, 15, p.getLocation(), 20);
			e.setDamage((int) (e.getDamage()*1.5));
		}
	}
	
	public void onHitDamaged(EntityDamageEvent e){
		Player p = (Player) e.getEntity();
		if(MobSystem.Getrandom(0, 2) == 0){
			p.addPotionEffect(new PotionEffect(PotionEffectType.SPEED, 100, 0));
		}
		if(can_passive && MobSystem.Getrandom(0, 3) == 0){
			e.setCancelled(true);
			p.sendMessage(Main.MS+"공격을 회피했습니다!");
			p.getWorld().playSound(p.getLocation(), Sound.BAT_HURT, 1.5f, 0.7f);
			ParticleEffect.WATER_BUBBLE.display(0.1f, 0.1f, 0.1f, 0, 18, p.getLocation(), 20);
		}
	}
	
	public void onDamaged(EntityDamageEvent e){
		e.setDamage((int) ((e.getDamage()*1.25)));
	}
}





