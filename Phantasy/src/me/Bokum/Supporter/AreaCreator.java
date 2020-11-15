package me.Bokum.Supporter;

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
import me.Bokum.MOB.Main;
import me.Bokum.MOB.Ability.Ability;
import me.Bokum.MOB.Game.Cooldown;
import me.Bokum.MOB.Game.MobSystem;
import me.Bokum.MOB.Utility.ParticleEffect;

public class AreaCreator extends Ability{

	public Player ac;
	public Location healarea = null;
	public Location shieldarea;
	
	public AreaCreator(String playername, Player p){
		super(playername, "���� ������");
		ac = p;
		ItemStack item = new ItemStack(351, 1, (byte) 2);
		ItemMeta meta = item.getItemMeta();
		meta.setDisplayName("��f[ ��b���� ������ ��f]");
		List<String> lorelist = new ArrayList<String>();
		lorelist.add("��f- ��7���ݷ� : ��65");
		lorelist.add("��f- ��7������ ���� : ��6���� ������ x 1");
		lorelist.add("��f- ��7���� : ��6�ڡڡ١١�");
		lorelist.add("��f- ��7�⵿ : ��6�١١١١�");
		lorelist.add("��f- ��7���� : ��6�ڡڡڡڡ�");
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
		ac.sendMessage("��6============= ��f[ ��b���� ������ ��f] - ��e������ ��7: ��cSupporter ��6=============\n"
				+ "��f- ��a�ֽ�ų ��7: ��dġ�� ����\n��7���������� 10ĭ���� �������� ü���� 2�ʴ� 2ȸ�������ִ� ������ �����մϴ�.\n"
				+ "��f- ��a������ų ��7: ��d���� ����\n��7���������� 10ĭ���� �������� �����̻��� ȸ�������ݴϴ�.\n"
				+ "��f- ��2�ñر��f(��c���Ž� ��밡�ɡ�f) ��7: ��d���� ����\n��710�ʰ� �ڽ��� 10ĭ���� ���鿡�� �ʴ� 2�� �������� �ִ� ������ �����մϴ�.\n"
				+ "��f- ��2�нú��f(��c���Ž� ��밡�ɡ�f) ��7: ��d���� Ȯ��\n��7��� ������ ������ 3ĭ �þ�ϴ�.");
	}
	
	public void PrimarySkill(final Player p){
		if(Cooldown.Checkcooldown(p, "�ֽ�ų")){
			if(p.getLocation().getBlock().getType() != Material.AIR){
				p.sendMessage(Main.MS+"���⿡�� ��ġ�Ͻ� �� �����ϴ�.");
				return;
			}
			if(Main.Bluetop.distance(p.getLocation()) <= 35 || Main.Redtop.distance(p.getLocation()) <= 45){
				p.sendMessage(Main.MS+"��ž ��ó 45ĭ�������� ��ġ�Ͻ� �� �����ϴ�.");
				return;
			}
			Cooldown.Setcooldown(p, "�ֽ�ų", 30, true);
			if(healarea != null){
				Bukkit.getScheduler().cancelTask(timernum);
				if(healarea.getBlock().getType() == Material.SPONGE)
				healarea.getBlock().setType(Material.AIR);
				if(healarea.getBlock().getRelative(0, 1, 0).getType() == Material.WOOL)
				healarea.getBlock().getRelative(0, 1, 0).setType(Material.AIR);
				MobSystem.healarea.remove(healarea);
				healarea = null;
			}
			p.getLocation().getBlock().setType(Material.SPONGE);
			p.getLocation().getBlock().getRelative(0, 1, 0).setTypeIdAndData(35, (byte)(MobSystem.bluelist.contains(p) ? 11 : 14), true);
			healarea = new Location(p.getWorld(), p.getLocation().getBlockX(), p.getLocation().getBlockY(), p.getLocation().getBlockZ());
			MobSystem.healarea.put(healarea, this);
			healarea.getWorld().playSound(healarea, Sound.DOOR_OPEN, 2.0f, 0.5f);
			p.sendMessage(Main.MS+"ġ�� ���� ���� �Ϸ�");
			timernum = Bukkit.getScheduler().scheduleSyncRepeatingTask(Main.instance, new Runnable(){
				public void run(){
					if(healarea != null){
						Location l = new Location(healarea.getWorld(), healarea.getBlockX(), healarea.getBlockY(), healarea.getBlockZ());
						l.setY(l.getY()+1);
						ParticleEffect.HEART.display(0f, 0f, 0f, 0f, 1, l, 30);
					    HelixEffect he = new HelixEffect(Main.effectManager);
					    he.particle = de.slikey.effectlib.util.ParticleEffect.TOWN_AURA;
					    he.period = 1;
					    he.particles = 15;
					    he.radius = can_passive ? 10 : 8;
					    he.setLocation(l);
					    he.start();
						l.getWorld().playSound(l, Sound.ITEM_PICKUP, 1.0f, 2.0f);
						for(Player t : MobSystem.getTeam(p)){
							if(t.getLocation().distance(healarea) <= (can_passive ? 13 : 10) && Math.abs(t.getLocation().getY()-healarea.getY()) <= 3){
								Location l2 = p.getLocation();
								l2.setY(l2.getY()+2);
								MobSystem.Addhp(t, 2);
								ParticleEffect.HEART.display(0f, 0f, 0f, 0f, 1, l2, 30);
								t.getWorld().playSound(t.getLocation(), Sound.ITEM_PICKUP, 1.0f, 2.0f);
							}
						}
					} else {
						Bukkit.getScheduler().cancelTask(timernum);
					}

				}
			}, 20l, 40l);
		}
	}

	public void SecondarySkill(final Player p){
		if(Cooldown.Checkcooldown(p, "������ų")){
			if(p.getLocation().getBlock().getType() != Material.AIR ||
					p.getLocation().getBlock().getRelative(0, 1, 0).getType() != Material.AIR){
				p.sendMessage(Main.MS+"���⿡�� ��ġ�Ͻ� �� �����ϴ�.");
				return;
			}
			if(Main.Bluetop.distance(p.getLocation()) <= 35 || Main.Redtop.distance(p.getLocation()) <= 45){
				p.sendMessage(Main.MS+"��ž ��ó 45ĭ�������� ��ġ�Ͻ� �� �����ϴ�.");
				return;
			}
			Cooldown.Setcooldown(p, "������ų", 30, true);
			if(shieldarea != null){
				Bukkit.getScheduler().cancelTask(timernum);
				if(shieldarea.getBlock().getType() == Material.SPONGE)
				shieldarea.getBlock().setType(Material.AIR);
				if(shieldarea.getBlock().getRelative(0, 1, 0).getType() == Material.WOOL)
				shieldarea.getBlock().getRelative(0, 1, 0).setType(Material.AIR);
				MobSystem.shieldarea.remove(shieldarea);
				shieldarea = null;
			}
			p.getLocation().getBlock().setType(Material.SPONGE);
			p.getLocation().getBlock().getRelative(0, 1, 0).setTypeIdAndData(35, (byte)(MobSystem.bluelist.contains(p) ? 11 : 14), true);
			shieldarea = new Location(p.getWorld(), p.getLocation().getBlockX(), p.getLocation().getBlockY(), p.getLocation().getBlockZ());
			MobSystem.shieldarea.put(shieldarea, this);
			p.sendMessage(Main.MS+"���� ���� ���� �Ϸ�");
			shieldarea.getWorld().playSound(shieldarea, Sound.DOOR_OPEN, 2.0f, 0.5f);
			timernum1 = Bukkit.getScheduler().scheduleSyncRepeatingTask(Main.instance, new Runnable(){
				public void run(){
					if(shieldarea != null){
						Location l = new Location(shieldarea.getWorld(), shieldarea.getBlockX(), shieldarea.getBlockY(), shieldarea.getBlockZ());
						l.setY(l.getY()+1);
						ParticleEffect.VILLAGER_HAPPY.display(0.1f, 0.1f, 0.1f, 0.1f, 15, l, 30);
					    HelixEffect he = new HelixEffect(Main.effectManager);
					    he.particle = de.slikey.effectlib.util.ParticleEffect.ENCHANTMENT_TABLE;
					    he.period = 1;
					    he.particles = 15;
					    he.radius = can_passive ? 10 : 8;
					    he.setLocation(l);
					    he.start();
						l.getWorld().playSound(l, Sound.IRONGOLEM_THROW, 1.0f, 0.5f);
						for(Player t : MobSystem.getTeam(p)){
							if(t.getLocation().distance(shieldarea) <= (can_passive ? 13 : 10) && Math.abs(t.getLocation().getY()-shieldarea.getY()) <= 3){
								Location l2 = p.getLocation();
								l2.setY(l2.getY()+2);
								t.removePotionEffect(PotionEffectType.SLOW);
								t.removePotionEffect(PotionEffectType.POISON);
								t.removePotionEffect(PotionEffectType.WITHER);
								t.removePotionEffect(PotionEffectType.CONFUSION);
								t.removePotionEffect(PotionEffectType.BLINDNESS);
								t.setFireTicks(0);
								ParticleEffect.VILLAGER_HAPPY.display(0.1f, 0.1f, 0.1f, 0f, 15, l2, 30);
								t.getWorld().playSound(t.getLocation(), Sound.IRONGOLEM_THROW, 1.0f, 0.5f);
							}
						}
					} else {
						Bukkit.getScheduler().cancelTask(timernum1);
					}

				}
			}, 20l, 40l);
		}
	}
	
	public void UltimateSkill(final Player p){
		if(Cooldown.Checkcooldown(p, "�ñر�")){
			Cooldown.Setcooldown(p, "�ñر�", 105, true);
			p.sendMessage(Main.MS+"���� ���� ���� �Ϸ�");
			timertime2 = 11;
			timernum2 = Bukkit.getScheduler().scheduleSyncRepeatingTask(Main.instance, new Runnable(){
				public void run(){
					if(--timertime2 > 0){
						p.setExp((float)timertime2/10);
						Location l = p.getLocation();
						l.setY(l.getY()+1);
						ParticleEffect.VILLAGER_ANGRY.display(0f, 0f, 0f, 0f, 1, l, 30);
					    HelixEffect he = new HelixEffect(Main.effectManager);
					    he.particle = de.slikey.effectlib.util.ParticleEffect.ENCHANTMENT_TABLE;
					    he.period = 1;
					    he.particles = 15;
					    he.radius = can_passive ? 10 : 8;
					    he.setLocation(l);
					    he.start();
						l.getWorld().playSound(l, Sound.BLAZE_HIT, 1.0f, 1.5f);
						for(Player t : MobSystem.getEnemy(p)){
							if(t.getLocation().distance(p.getLocation()) <= (can_passive ? 13 : 10) && Math.abs(t.getLocation().getY()-p.getLocation().getY()) <= 3){
								Location l2 = p.getLocation();
								l2.setY(l2.getY()+2);
								t.damage(6, p);
								ParticleEffect.VILLAGER_ANGRY.display(0f, 0f, 0f, 0f, 1, l2, 30);
								t.getWorld().playSound(t.getLocation(), Sound.BLAZE_HIT, 1.0f, 1.5f);
							}
						}
					} else {
						Bukkit.getScheduler().cancelTask(timernum2);
						p.sendMessage(Main.MS+"���� ������ ���ӽð��� �������ϴ�.");
						p.setExp(0);
					}

				}
			}, 20l, 20l);
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
			if(!can_Ultimate){ ac.sendMessage(Main.MS+"�ñر�� �������� ������ ��밡���մϴ�."); return;}
			e.getPlayer().getInventory().setHeldItemSlot(0);
			UltimateSkill(e.getPlayer());
		}
	}
	
	public void onHit(EntityDamageByEntityEvent e){
		e.setDamage(5);
	}
}




