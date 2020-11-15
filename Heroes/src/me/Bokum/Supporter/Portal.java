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
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.event.player.PlayerItemHeldEvent;
import org.bukkit.event.player.PlayerRespawnEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.util.Vector;

import com.avaje.ebean.enhance.asm.commons.GeneratorAdapter;

import de.slikey.effectlib.EffectType;
import de.slikey.effectlib.effect.AtomEffect;
import de.slikey.effectlib.effect.CircleEffect;
import de.slikey.effectlib.effect.CloudEffect;
import me.Bokum.MOB.Main;
import me.Bokum.MOB.Ability.Ability;
import me.Bokum.MOB.Game.Cooldown;
import me.Bokum.MOB.Game.MobSystem;
import me.Bokum.MOB.Utility.ParticleEffect;
import me.Bokum.MOB.Utility.Vector3D;

public class Portal extends Ability{

	public Player portal;
	public Location redportal = null;
	public Location blueportal = null;
	
	public Portal(String playername, Player p){
		super(playername, "포탈");
		portal = p;
		ItemStack item = new ItemStack(296, 1);
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
		
		item = new ItemStack(Material.GOLD_INGOT, 1);
		meta = item.getItemMeta();
		meta.setDisplayName("§f[ §c보조스킬2 §f]");
		item.setItemMeta(meta);
		
		p.getInventory().setItem(3 ,item);
		
		item = new ItemStack(388, 1);
		meta = item.getItemMeta();
		meta.setDisplayName("§f[ §c궁극기 §f]");
		item.setItemMeta(meta);
		
		p.getInventory().setItem(4 ,item);
	}
	
	public void description(){
		portal.sendMessage("§6============= §f[ §b포탈 §f] - §e직업군 §7: §cExpert §6=============\n"
				+ "§f- §a주스킬 §7: §d블루 포탈\n§75칸내에 바라보는 블럭에 블루포탈을 설치합니다.\n"
				+ "§f- §a보조스킬 §7: §d레드 포탈\n§75칸내에 바라보는 블럭에 레드포탈을 설치합니다.\n"
				+ "§f- §a보조스킬2 §7: §d위치 교환\n§725칸내에 바라보는 적과 위치를 교환합니다.\n"
				+ "§f- §2궁극기§f(§c구매시 사용가능§f) §7: §d차원 주머니\n§725칸내에 바라보는 곳에 차원 주머니를 10초간 생성합니다. \n§c차원 주머니에 들어간 적은 다른 차원으로 이동하여 곧 죽게됩니다,\n"
				+ "§f- §2패시브§f(§c구매시 사용가능§f) §7: §d포탈 게이지 상승\n§7포탈 생성의 쿨타임이 1초 감소합니다.");
	}
	
	public void PrimarySkill(final Player p){
		if(Cooldown.Checkcooldown(p, "주스킬")){
			Block b = null;
			try{
				b = p.getTargetBlock(null, 5);
			}catch(Exception exception){
				return;
			}
			if(b == null || b.getType() == Material.AIR){
				p.sendMessage(Main.MS+"5칸 이내의 블럭에만 사용이 가능합니다.");
				return;
			}
			if((b.getRelative(0, 1, 0).getType() != Material.AIR && b.getRelative(0, 1, 0).getType() != Material.PORTAL ) 
					|| b.getRelative(0, 2, 0).getType() != Material.AIR
					|| b.getRelative(0, 3, 0).getType() != Material.AIR){
				p.sendMessage(Main.MS+"대상 블럭의 3칸 위까지에 아무블럭이 없어야합니다.");
				return;
			}
			Cooldown.Setcooldown(p, "주스킬", can_passive? 4 : 5, true);
			if(blueportal != null){
				if(blueportal.getBlock().getType() == Material.PORTAL)
				blueportal.getBlock().setType(Material.AIR);
				if(blueportal.getBlock().getRelative(0, -1, 0).getType() == Material.WOOL)
				blueportal.getBlock().getRelative(0, -1, 0).setType(Material.AIR);
				MobSystem.portalloc.remove(blueportal);
				if(redportal != null) MobSystem.portalloc.remove(redportal);
			}
			blueportal = new Location(b.getWorld(), b.getLocation().getBlockX(), b.getLocation().getBlockY()+2
					, b.getLocation().getBlockZ());
			blueportal.getBlock().getRelative(0, -1, 0).setTypeIdAndData(35, (byte) 0, true);
			blueportal.getBlock().setType(Material.PORTAL);
			p.getWorld().playSound(p.getLocation(), Sound.ENDERMAN_HIT, 2.0f, 1.5f);
			if(redportal == null){
				p.sendMessage(Main.MS+"레드 포탈도 설치해주세요.");
				return;
			}
			MobSystem.portalloc.put(blueportal, redportal);
			MobSystem.portalloc.put(redportal, blueportal);
		}
	}

	public void SecondarySkill(final Player p){
		if(Cooldown.Checkcooldown(p, "보조스킬")){
			Block b = null;
			try{
				b = p.getTargetBlock(null, 5);
			}catch(Exception exception){
				return;
			}
			if(b == null || b.getType() == Material.AIR){
				p.sendMessage(Main.MS+"5칸 이내의 블럭에만 사용이 가능합니다.");
				return;
			}
			if((b.getRelative(0, 1, 0).getType() != Material.AIR && b.getRelative(0, 1, 0).getType() != Material.PORTAL )
					|| b.getRelative(0, 2, 0).getType() != Material.AIR
					|| b.getRelative(0, 3, 0).getType() != Material.AIR){
				p.sendMessage(Main.MS+"대상 블럭의 3칸 위까지에 아무블럭이 없어야합니다.");
				return;
			}
			Cooldown.Setcooldown(p, "보조스킬", can_passive? 4 : 5, true);
			if(redportal != null){
				if(redportal.getBlock().getType() == Material.PORTAL)
				redportal.getBlock().setType(Material.AIR);
				if(redportal.getBlock().getRelative(0, -1, 0).getType() == Material.WOOL)
				redportal.getBlock().getRelative(0, -1, 0).setType(Material.AIR);
				if(blueportal != null) MobSystem.portalloc.remove(blueportal);
				MobSystem.portalloc.remove(redportal);
			}
			redportal = new Location(b.getWorld(), b.getLocation().getBlockX(), b.getLocation().getBlockY()+2
					, b.getLocation().getBlockZ());
			redportal.getBlock().getRelative(0, -1, 0).setTypeIdAndData(35, (byte)0, true);
			redportal.getBlock().setType(Material.PORTAL);
			p.getWorld().playSound(p.getLocation(), Sound.ENDERMAN_HIT, 2.0f, 1.5f);
			if(blueportal == null){
				p.sendMessage(Main.MS+"블루 포탈도 설치해주세요.");
				return;
			}
			MobSystem.portalloc.put(blueportal, redportal);
			MobSystem.portalloc.put(redportal, blueportal);
		}
	}
	
	public void thirdSkill(final Player p){
		if(Cooldown.Checkcooldown(p, "보조스킬2")){
			LivingEntity t = MobSystem.Getcorsurplayer(p, 25);
			if(t == null){
				p.sendMessage(Main.MS+"25칸내에 바라보는곳에 몹이 없습니다.");
				return;
			}
			Cooldown.Setcooldown(p, "보조스킬2", 19, true);
			p.getWorld().playSound(p.getLocation(), Sound.ENDERMAN_TELEPORT, 2.0f, 0.5f);
		    ParticleEffect.PORTAL.display(0.1f, 0.1f, 0.1f, 0, 20, p.getLocation(), 25);
			t.getWorld().playSound(p.getLocation(), Sound.ENDERMAN_TELEPORT, 2.0f, 0.5f);
		    ParticleEffect.PORTAL.display(0.1f, 0.1f, 0.1f, 0, 20, t.getLocation(), 25);
		    Location l = p.getLocation();
		    p.teleport(t.getLocation());
		    t.teleport(l);
		}
	}
	
	public void UltimateSkill(final Player p){
		if(Cooldown.Checkcooldown(p, "궁극기")){
			Block b = null;
			try{
				b = p.getTargetBlock(null, 25);
			}catch(Exception exception){
				return;
			}
			if(b == null || b.getType() == Material.AIR){
				p.sendMessage(Main.MS+"25칸 이내의 블럭에만 사용이 가능합니다.");
				return;
			}
			p.sendMessage(Main.MS+"차원 이동 주머니를 생성하였습니다.");
			Cooldown.Setcooldown(p, "궁극기", 101, true);
			p.getWorld().playSound(p.getLocation(), Sound.ENDERMAN_TELEPORT, 2.0f, 0.5f);
			b.getWorld().playSound(p.getLocation(), Sound.ENDERMAN_TELEPORT, 2.0f, 0.5f);
		    ParticleEffect.PORTAL.display(0.1f, 0.1f, 0.1f, 0, 20, b.getLocation(), 25);
		    final Location loc = new Location(b.getWorld(), b.getLocation().getX(), b.getLocation().getY()+3, b.getLocation().getZ());
		    AtomEffect ae = new AtomEffect(Main.effectManager);
		    ae.setLocation(loc);
		    ae.start();
		    ae.iterations = 100;
		    timertime = 100;
		    timernum = Bukkit.getScheduler().scheduleSyncRepeatingTask(Main.instance, new Runnable(){
		    	public void run(){
		    		if(--timertime < 0){
		    			Bukkit.getScheduler().cancelTask(timernum);
		    			p.sendMessage(Main.MS+"차원 주머니는 시간이 지나 소멸됐습니다.");
		    		} else {
		    			loc.getWorld().playSound(loc, Sound.ORB_PICKUP, 1.5f, 2.0f);
						for(Entity entity : p.getNearbyEntities(4, 4, 4)){
							if(!(entity instanceof LivingEntity)) return;
							LivingEntity t = (LivingEntity) entity;
			    			if(t.getLocation().distance(loc) <= 3){
			    				t.damage(1, p);
			    				t.teleport(new Location(t.getWorld(), t.getLocation().getX(), -10, t.getLocation().getZ()));
			    				t.getWorld().playSound(t.getLocation(), Sound.PORTAL, 2.0f, 0.5f);
			    			}
			    		}
		    		}
		    	}
		    }, 20l, 2l);
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
			thirdSkill(e.getPlayer());
		}
		if(e.getNewSlot() == 4){
			if(!can_Ultimate){ portal.sendMessage(Main.MS+"궁극기는 상점에서 구매후 사용가능합니다."); return;}
			e.getPlayer().getInventory().setHeldItemSlot(0);
			UltimateSkill(e.getPlayer());
		}
	}
	
	public void onHit(EntityDamageByEntityEvent e){
	}
	
	public void onDeath(PlayerDeathEvent e){
		if(MobSystem.portalloc.containsKey(blueportal)) {
			if(blueportal.getBlock().getType() == Material.PORTAL)
			blueportal.getBlock().setType(Material.AIR);
			if(blueportal.getBlock().getRelative(0, -1, 0).getType() == Material.WOOL)
			blueportal.getBlock().getRelative(0, -1, 0).setType(Material.AIR);
			MobSystem.portalloc.remove(blueportal);
		}
		if(MobSystem.portalloc.containsKey(redportal)) {
			if(redportal.getBlock().getType() == Material.PORTAL)
			redportal.getBlock().setType(Material.AIR);
			if(redportal.getBlock().getRelative(0, -1, 0).getType() == Material.WOOL)
			redportal.getBlock().getRelative(0, -1, 0).setType(Material.AIR);
			MobSystem.portalloc.remove(redportal);
		}
		blueportal = null;
		redportal = null;
	}
	
}



