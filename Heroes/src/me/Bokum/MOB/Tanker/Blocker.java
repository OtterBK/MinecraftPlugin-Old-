package me.Bokum.MOB.Tanker;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
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
import org.bukkit.util.Vector;

import de.slikey.effectlib.effect.ArcEffect;
import de.slikey.effectlib.effect.CircleEffect;
import de.slikey.effectlib.effect.ConeEffect;
import me.Bokum.MOB.Main;
import me.Bokum.MOB.Ability.Ability;
import me.Bokum.MOB.Game.Cooldown;
import me.Bokum.MOB.Game.MobSystem;
import me.Bokum.MOB.Utility.ParticleEffect;

public class Blocker extends Ability{

	public Player blocker;
	public boolean shielding = false;
	public Player shieldtarget = null;
	public boolean ultimate = false;
	
	public Blocker(String playername, Player p){
		super(playername, "블로커");
		blocker = p;
		ItemStack item = new ItemStack(351, 1, (byte) 4);
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
		timernum2 = Bukkit.getScheduler().scheduleSyncRepeatingTask(Main.instance, new Runnable(){
			public void run(){
				if(!blocker.hasPotionEffect(PotionEffectType.DAMAGE_RESISTANCE) && blocker.getExp() < 1){
					if(!can_passive){
						blocker.setExp(blocker.getExp()+0.1f > 1 ? 1 : blocker.getExp() + 0.1f);
					} else {
						blocker.setExp(blocker.getExp()+0.175f > 1 ? 1 : blocker.getExp() + 0.175f);
					}
					blocker.getWorld().playSound(blocker.getLocation(), Sound.DIG_STONE, 1.2f, 0.3f);
				}
			}
		}, 0l, 20l);
	}
	
	public void description(){
		blocker.sendMessage("§6============= §f[ §b블로커 §f] - §e직업군 §7: §cTanker §6=============\n"
				+ "§f- §a주스킬 §7: §d방벽 전개\n§7자신에게 남은 에너지의 양만큼 방벽을 전개합니다. 방벽의 전개는 저항2의 버프를 받습니다.\n§c방벽 전개시에는 20% 이상의 에너지(경험치바)가 필요합니다.\n"
				+ "§f- §a보조스킬 §7: §d방벽 전달\n§720칸내의 바라보는 팀원에게 남은 에너지의 양만큼 방벽을 전개합니다.\n§c방벽 전달후에는 에너지가 충전되기 시작합니다.\n"
				+ "§f- §2궁극기§f(§c구매시 사용가능§f) §7: §d접근 금지\n§712초간 모든 데미지가 2상승하며 타격된 적을 멀리 밀쳐냅니다. \n"
				+ "§f- §2패시브§f(§c구매시 사용가능§f) §7: §d강화 방벽\n§7방벽 게이지 충전 속도가 1.75배가 됩니다.");
	}
	
	public void PrimarySkill(final Player p){
		if(p.getExp() < 0.2f){
			p.sendMessage(Main.MS+"에너지가 부족합니다. 현재 에너지 : "+p.getExp()*100+"%");
		}else{  
			Bukkit.getScheduler().cancelTask(timernum);
			int time = (int) (p.getExp()*10)+1;
			p.getWorld().playSound(p.getLocation(), Sound.ITEM_PICKUP, 2.0f, 2.0f);
		    p.sendMessage(Main.MS+"방벽을 전개했습니다. "+time+"초");
		    p.setExp(0);
		    p.addPotionEffect(new PotionEffect(PotionEffectType.DAMAGE_RESISTANCE, time*20, 1));
		    timertime = time;
		    timernum = Bukkit.getScheduler().scheduleSyncRepeatingTask(Main.instance, new Runnable(){
		    	public void run(){
		    		if(timertime-- > 0){
		    			CircleEffect circleeffect = new CircleEffect(Main.effectManager);
		  		      circleeffect.particle = de.slikey.effectlib.util.ParticleEffect.SPELL_WITCH;
		  		      circleeffect.setEntity(p);
		  		      circleeffect.period = 1;
		  		      circleeffect.iterations = 20;
		  		      circleeffect.particles = 30;
		  		      circleeffect.speed = 1;
		  		      circleeffect.particleOffsetY = 1;
		  		      circleeffect.start();
		    		}else{
		    			Bukkit.getScheduler().cancelTask(timernum);
		    			p.sendMessage(Main.MS+"방벽이 해제 되었습니다.");
		    			p.getWorld().playSound(p.getLocation(), Sound.ITEM_BREAK, 2.0f, 2.0f);
		    		}
		    	}
		    }, 0l, 20l);
		}
	}

	public void SecondarySkill(final Player p){
		if(Cooldown.Checkcooldown(p, "보조스킬")){
			if(p.getExp() < 0.2f){
				p.sendMessage(Main.MS+"에너지가 부족합니다. 현재 에너지 : "+p.getExp()*100+"%");
			}else{  
				final LivingEntity entity = MobSystem.Getcorsurplayer(p, 20);
				if(entity == null || !(entity instanceof Player)){
					p.sendMessage(Main.MS+"20칸내에 바라보는곳에 플레이어가 없습니다.");
					p.getWorld().playSound(p.getLocation(), Sound.BLAZE_HIT, 2.0f, 3.0f);
					return;
				}
				final Player t = (Player) entity;
				Bukkit.getScheduler().cancelTask(timernum1);
				Cooldown.Setcooldown(p, "보조스킬", 20, true);
				int time = (int) (p.getExp()*10)+1;
				t.sendMessage(Main.MS+p.getName()+" 님에게 방벽을 받았습니다."+time+"초");
				p.sendMessage(Main.MS+t.getName()+" 님에게 방벽을 부여합니다."+time+"초");
				p.setExp(0);
				t.getWorld().playSound(p.getLocation(), Sound.ITEM_PICKUP, 2.0f, 2.0f);
				p.getWorld().playSound(p.getLocation(), Sound.ITEM_PICKUP, 2.0f, 2.0f);
			    t.addPotionEffect(new PotionEffect(PotionEffectType.DAMAGE_RESISTANCE, time*20, 1));
			    timertime1 = time;
			    timernum1 = Bukkit.getScheduler().scheduleSyncRepeatingTask(Main.instance, new Runnable(){
			    	public void run(){
			    		if(timertime1-- > 0){
			    			CircleEffect circleeffect = new CircleEffect(Main.effectManager);
			  		      circleeffect.particle = de.slikey.effectlib.util.ParticleEffect.SPELL_WITCH;
			  		      circleeffect.setEntity(t);
			  		      circleeffect.period = 1;
			  		      circleeffect.iterations = 20;
			  		      circleeffect.particles = 30;
			  		      circleeffect.speed = 1;
			  		      circleeffect.particleOffsetY = 1;
			  		      circleeffect.start();
			    		}else{
			    			Bukkit.getScheduler().cancelTask(timernum1);
			    			t.sendMessage(Main.MS+"방벽이 해제 되었습니다.");
			    			p.sendMessage(Main.MS+t.getName()+"님께 전재한 방벽이 해제 되었습니다.");
			    			p.getWorld().playSound(p.getLocation(), Sound.ITEM_BREAK, 2.0f, 2.0f);
			    			t.getWorld().playSound(p.getLocation(), Sound.ITEM_BREAK, 2.0f, 2.0f);
			    		}
			    	}
			    }, 0l, 20l);
			}
			}
	}
	
	public void UltimateSkill(final Player p){
		if(Cooldown.Checkcooldown(p, "궁극기")){
			Cooldown.Setcooldown(p, "궁극기", 91, true);
			p.sendMessage(Main.MS+"강화 됐습니다!");
			p.getWorld().playSound(p.getLocation(), Sound.ENDERMAN_SCREAM, 2.0f, 2.0f);
			ultimate = true;
			Cooldown.SkillCountdown(p, 12, "접근 금지 스킬");
			Bukkit.getScheduler().scheduleSyncDelayedTask(Main.instance, new Runnable(){
				public void run(){
					ultimate = false;
					p.getWorld().playSound(p.getLocation(), Sound.ITEM_BREAK, 2.0f, 2.0f);
				}
			}, 240);
		}
	}
	
	public void onDamaged(EntityDamageEvent e){
		e.setDamage((int) (e.getDamage()*1));
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
			if(!can_Ultimate){ blocker.sendMessage(Main.MS+"궁극기는 상점에서 구매후 사용가능합니다."); return;}
			e.getPlayer().getInventory().setHeldItemSlot(0);
			UltimateSkill(e.getPlayer());
		}
	}
	
	public void onHit(EntityDamageByEntityEvent e){
		if(ultimate){
			Player p = (Player) e.getEntity();
			Player d = (Player) e.getDamager();
			e.setDamage(e.getDamage()+3);
			p.getWorld().playSound(p.getLocation(), Sound.PISTON_RETRACT, 2.0f, 2.0f);
			ParticleEffect.EXPLOSION_LARGE.display(0.03f,0.03f, 0.03f, 0.02f, 5, p.getLocation(), 20);
			final Vector direction = d.getEyeLocation().getDirection().multiply(3);
			p.setVelocity(direction);
		}
	}
}
