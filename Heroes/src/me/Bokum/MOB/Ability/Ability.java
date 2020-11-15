package me.Bokum.MOB.Ability;

import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.block.Block;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.entity.EntityRegainHealthEvent;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.event.entity.ProjectileHitEvent;
import org.bukkit.event.entity.ProjectileLaunchEvent;
import org.bukkit.event.player.PlayerItemHeldEvent;
import org.bukkit.event.player.PlayerMoveEvent;

import me.Bokum.MOB.Main;
import me.Bokum.MOB.Game.Cooldown;
import me.Bokum.MOB.Game.MobSystem;

public class Ability
{
	public final String playerName;
	public final String abilityName;
	public boolean can_passive = false;
	public boolean can_Ultimate = false;
	public int timernum = 0;
	public int timertime = 0;
	public int timernum1 = 0;
	public int timertime1 = 0;
	public int timernum2 = 0;
	public int timertime2 = 0;
	public int reducedamage = 0;
	public boolean cc = false;
	public boolean ignore = false;
	public boolean check_dead = false;
	public Ability(String playerName, String abilityName)
	{
		this.playerName=playerName;
		this.abilityName=abilityName;
		this.can_passive=false;
		this.can_Ultimate=false;
		this.timernum = 0;
		this.timertime = 0;
		this.timernum1 = 0;
		this.timertime1 = 0;
		this.timernum2 = 0;
		this.timertime2 = 0;
		this.reducedamage = 0;
		this.cc = false;
		this.ignore = false;
		this.check_dead = false;
	}
	
	public void description(){}
	
	public void onArrowHit(Entity t){}
	
	public void Active(PlayerItemHeldEvent event){}
	
	public void onBlockBreak(BlockBreakEvent e){
		Block b = e.getBlock();
		Player p = e.getPlayer();
		Location loc = new Location(b.getWorld(), b.getLocation().getBlockX(), b.getLocation().getBlockY(), b.getLocation().getBlockZ());
		if(b.getType() == Material.SPONGE){
			if(MobSystem.healarea.containsKey(loc)){
				loc.getWorld().playSound(loc, Sound.DOOR_CLOSE, 2.0f, 0.5f);
				MobSystem.healarea.get(loc).ac.sendMessage(Main.MS+"치유 영역 생성기가 파괴되었습니다.");
				p.sendMessage(Main.MS+MobSystem.healarea.get(loc).ac.getName()+"님의 치유 영역 생성기를 파괴했습니다.");
				MobSystem.healarea.get(loc).healarea = null;
				loc.getBlock().setType(Material.AIR);
				if(loc.getBlock().getRelative(0, 1, 0).getType() == Material.WOOL)
				loc.getBlock().getRelative(0, 1, 0).setType(Material.AIR);
				MobSystem.healarea.remove(loc);
			}
			if(MobSystem.shieldarea.containsKey(loc)){
				loc.getWorld().playSound(loc, Sound.DOOR_CLOSE, 2.0f, 0.5f);
				MobSystem.shieldarea.get(loc).ac.sendMessage(Main.MS+"삭제 영역 생성기가 파괴되었습니다.");
				p.sendMessage(Main.MS+MobSystem.shieldarea.get(loc).ac.getName()+"님의 삭제 영역 생성기를 파괴했습니다.");
				MobSystem.shieldarea.get(loc).shieldarea = null;
				loc.getBlock().setType(Material.AIR);
				if(loc.getBlock().getRelative(0, 1, 0).getType() == Material.WOOL)
				loc.getBlock().getRelative(0, 1, 0).setType(Material.AIR);
				MobSystem.shieldarea.remove(loc);
			}
		}
	}
	
	public void onDeath(PlayerDeathEvent event){
		
	}
	
	public void onKill(PlayerDeathEvent event){
	}
	
	public void onRegain(EntityRegainHealthEvent event){}
	
	public void onHit(EntityDamageByEntityEvent event){}
	
	public void onHitDamaged(EntityDamageByEntityEvent event){
		Player p = (Player) event.getEntity();
		for(Entity entity : p.getNearbyEntities(8, 8, 8)){
			if(!(entity instanceof Player)) continue;
			Player t = (Player) entity;
			if(t.getLocation().distance(p.getLocation()) <= 8 && MobSystem.ablist.get(t.getName()).abilityName.equalsIgnoreCase("쉴더")){
				if(t.getItemInHand() != null && t.getItemInHand().getType() == Material.IRON_INGOT){
					int amt = event.getDamage();
					int shield = (int) ((t.getExp()/0.005f));
					shield -= amt;
					if(shield < 0){
						event.setDamage(shield*-1);
						t.setExp(0);
					} else {
						t.setExp(shield*0.005f);
						event.setDamage(1);
					}
					return;
				}
			}
		}
		if(reducedamage > 0){
			event.setDamage(event.getDamage()-reducedamage <= 0 ? 1 : event.getDamage()-reducedamage);
		}
	}
	
	public void onBlockPlace(BlockPlaceEvent event) {}
	
	public void onMove(PlayerMoveEvent e){
	}
	
	public void onLaunch(ProjectileLaunchEvent event){}

	public void onProjectileHit(ProjectileHitEvent event){}
	
	public void onDamaged(EntityDamageEvent e){
	}
	
}
