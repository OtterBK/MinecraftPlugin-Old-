package me.Bokum.MOB.Attacker;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.event.block.Action;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.entity.EntityDamageEvent.DamageCause;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.event.player.PlayerItemHeldEvent;
import org.bukkit.event.player.PlayerRespawnEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.util.Vector;

import de.slikey.effectlib.effect.CircleEffect;
import me.Bokum.MOB.Main;
import me.Bokum.MOB.Ability.Ability;
import me.Bokum.MOB.Game.Cooldown;
import me.Bokum.MOB.Game.MobSystem;
import me.Bokum.MOB.Utility.ParticleEffect;
import me.Bokum.MOB.Utility.Vector3D;

public class Ninja extends Ability{
	
	public boolean DSword = false;
	public Player ninja;
	public boolean Fall = false;
	
	public Ninja(String playername, Player p){
		super(playername, "����");
		ninja = p;
		ItemStack item = new ItemStack(276, 1);
		ItemMeta meta = item.getItemMeta();
		meta.setDisplayName("��f[ ��b���� ��f]");
		List<String> lorelist = new ArrayList<String>();
		lorelist.add("��f- ��7���ݷ� : ��67");
		lorelist.add("��f- ��7������ ���� : ��6���� ������ x 1.25");
		lorelist.add("��f- ��7���� : ��6�ڡڡڡڡ�");
		lorelist.add("��f- ��7�⵿ : ��6�ڡڡڡ١�");
		lorelist.add("��f- ��7���� : ��6�١١١١�");
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
		ninja.sendMessage("��6============= ��f[ ��b���� ��f] - ��e������ ��7: ��cAttacker ��6=============\n"
				+ "��f- ��a�ֽ�ų ��7: ��d��ǳ��\n��7������ ������ ���ư��� �������� ������ ������ 4�� �������� �ݴϴ�.\n&c��ǳ���� ���� ������ �����ϴ�.\n"
				+ "��f- ��a������ų ��7: ��d�ٲ�ġ��\n��7������ �ڷκ����� �볪���� �ٲ�Ĩ�ϴ�.\n"
				+ "��f- ��2�ñر��f(��c���Ž� ��밡�ɡ�f) ��7: ��d���\n��710�ʰ� ���� ���ݷ��� 15�� �˴ϴ�. ���ݼӵ��� �����մϴ�.\n"
				+ "��f- ��2�нú��f(��c���Ž� ��밡�ɡ�f) ��7: ��d��ǳ�� �ʱ�ȭ\n��7�÷��̾ ����� �� ��ǳ���� ��Ÿ���� �ʱ�ȭ�˴ϴ�.");
	}
	
	public void PrimarySkill(final Player p){
		if(Cooldown.Checkcooldown(p, "�ֽ�ų")){
			if(Main.Bluetop.distance(p.getLocation()) <= 35 || Main.Redtop.distance(p.getLocation()) <= 35){
				p.sendMessage(Main.MS+"��ž ��ó 35ĭ�������� ����Ͻ� �� �����ϴ�.");
				return;
			}
			Cooldown.Setcooldown(p, "�ֽ�ų", 7, false);
			Fall = true;
			p.getWorld().playSound(p.getLocation(), Sound.BAT_TAKEOFF, 2.0f, 4.0f);
			ParticleEffect.CLOUD.display(0.1f, 0.1f, 0.1f, 0.1f, 25, p.getLocation(), 20);

			final Vector direction = p.getEyeLocation().getDirection().multiply(2.65);
			p.setVelocity(direction);
			
			Bukkit.getScheduler().scheduleSyncDelayedTask(Main.instance, new Runnable(){
				public void run(){
					for(Entity t : p.getNearbyEntities(4, 2, 4)){
						if(t instanceof Player){
							Player target = (Player) t;
							if(MobSystem.getEnemy(p).contains(t)){
								target.damage(7, p);
								target.getWorld().playSound(t.getLocation(), Sound.HURT, 2.0f, 1.2f);
								ParticleEffect.REDSTONE.display(0.3f, 0.3f, 0.3f, 0, 20, t.getLocation(), 25);
							}
						}
					}
				}
			}, 10l);
		}
	}
	
	public void SecondarySkill(final Player p){
		if(Cooldown.Checkcooldown(p, "������ų")){
			if(Main.Bluetop.distance(p.getLocation()) <= 20 || Main.Redtop.distance(p.getLocation()) <= 20){
				p.sendMessage(Main.MS+"��ž ��ó 10ĭ�������� ����Ͻ� �� �����ϴ�.");
				return;
			}
			Cooldown.Setcooldown(p, "������ų", 5, true);
			final Location bl = p.getLocation().getBlock().getLocation();
			p.getWorld().playSound(p.getLocation(), Sound.BAT_TAKEOFF, 2.0f, 4.0f);
			ParticleEffect.CLOUD.display(0.1f, 0.1f, 0.1f, 0.1f, 25, p.getLocation(), 20);
	        final Player observer = p;
	        
	        Location observerPos = observer.getEyeLocation();
	        Vector3D observerDir = new Vector3D(observerPos.getDirection());
	        Vector3D observerStart = new Vector3D(observerPos);
	        
	        Location loc = p.getLocation();
	        
	        for(int i = -1; i > -7; i--){
	            Vector3D observerEnd = observerStart.add(observerDir.multiply(i));
	            loc = new Location(p.getWorld(), observerEnd.x, observerEnd.y, observerEnd.z);
	        	if(loc.getBlock().getType() != Material.AIR){
	        		if(i == -1){
	        			loc = p.getLocation();
	        		} else {
	        			observerEnd = observerStart.add(observerDir.multiply(i));
	                    loc = new Location(p.getWorld(), observerEnd.x, observerEnd.y, observerEnd.z);
	        		}
	    			break;
	        	}
	        }         
	        loc.setPitch(p.getLocation().getPitch());
	        loc.setYaw(p.getLocation().getYaw());
	        p.teleport(loc);
			Bukkit.getScheduler().scheduleSyncDelayedTask(Main.instance, new Runnable(){
				public void run(){
					if(bl.getBlock().getType() == Material.AIR)
					bl.getBlock().setType(Material.LOG);
					bl.setY(bl.getY()+1);
					if(bl.getBlock().getType() == Material.AIR)
					bl.getBlock().setType(Material.LOG);
					p.getWorld().playSound(p.getLocation(), Sound.DOOR_OPEN, 2.0f, 0.1f);
					ParticleEffect.CLOUD.display(0.1f, 0.1f, 0.1f, 0.1f, 25, p.getLocation(), 20);
				}
			}, 4l);
			Bukkit.getScheduler().scheduleSyncDelayedTask(Main.instance, new Runnable(){
				public void run(){
					if(bl.getBlock().getType() == Material.LOG)
					bl.getBlock().setType(Material.AIR);
					bl.setY(bl.getY()-1);
					if(bl.getBlock().getType() == Material.LOG)
					bl.getBlock().setType(Material.AIR);
					p.getWorld().playSound(p.getLocation(), Sound.DOOR_CLOSE, 2.0f, 0.1f);
					ParticleEffect.CLOUD.display(0.1f, 0.1f, 0.1f, 0.1f, 25, p.getLocation(), 20);
				}
			}, 40l);
		}
	}
	
	public void UltimateSkill(final Player p){
		if(Cooldown.Checkcooldown(p, "�ñر�")){
			Cooldown.Setcooldown(p, "�ñر�", 137, true);
			timertime = 20;
			DSword = true;
			p.setExp(1);
			p.getWorld().playSound(p.getLocation(), Sound.ANVIL_USE, 2.0f, 3.0f);
			p.getWorld().playSound(p.getLocation(), Sound.ENDERDRAGON_GROWL, 2.0f, 1.4f);
			ParticleEffect.SLIME.display(0.5f, 0.5f, 0.5f, 0, 25, p.getLocation(), 30);
			p.addPotionEffect(new PotionEffect(PotionEffectType.SLOW_DIGGING, 200, 9));
			timernum = Bukkit.getScheduler().scheduleSyncRepeatingTask(Main.instance, new Runnable(){
				public void run(){
					if(--timertime > 0){
						p.setExp((float)timertime/20);
					      CircleEffect circleeffect2 = new CircleEffect(Main.effectManager);
					      Location l = p.getLocation();
					      l.setY(l.getY()+1);
					      circleeffect2.setLocation(l);
					      circleeffect2.period = 1;
					      circleeffect2.speed = 1;
					      circleeffect2.radius = 1f;
					      circleeffect2.iterations = 20;
					      circleeffect2.updateLocations = true;
					      circleeffect2.enableRotation = true;
					      circleeffect2.start();
					} else {
						Bukkit.getScheduler().cancelTask(timernum);
						DSword = false;
						p.setExp(0);
					}
				}
			}, 0l, 10l);
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
			if(!can_Ultimate){ ninja.sendMessage(Main.MS+"�ñر�� �������� ������ ��밡���մϴ�."); return;}
			e.getPlayer().getInventory().setHeldItemSlot(0);
			UltimateSkill(e.getPlayer());
		}
	}
	
	public void onDamaged(EntityDamageEvent e){
		Player p = (Player) e.getEntity();
		if(e.getCause() == DamageCause.FALL && Fall){
			e.setCancelled(true);
			Fall = false;
		}else{
			e.setDamage((int) (e.getDamage()*1.25));
		}
	}
	
	public void onKill(PlayerDeathEvent e){
		if(can_passive){
			Player k = (Player) e.getEntity().getKiller();
			k.sendMessage(Main.MS+"�÷��̾ �׿� ��ǳ���� ��Ÿ���� �ʱ�ȭ �Ǿ����ϴ�.");
			Cooldown.Setcooldown(k, "�ֽ�ų", 0, false);
		}
	}
	
	public void onHit(EntityDamageByEntityEvent e){
		if(ninja.getInventory().getHeldItemSlot() == 0){
			Player d = (Player) e.getDamager();
			Player p = (Player) e.getEntity();
			if(d == ninja && DSword){
				e.setDamage(15);
				e.getEntity().getWorld().playSound(e.getEntity().getLocation(), Sound.ANVIL_LAND, 2.0f, 3.0f);
				ParticleEffect.REDSTONE.display(0.3f, 0.3f, 0.3f, 0, 40, e.getEntity().getLocation(), 25);
			}else{
				e.setDamage(7);
			}
		}
	}
}
