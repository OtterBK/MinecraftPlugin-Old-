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
		super(playername, "���డ");
		architect = p;
		ItemStack item = new ItemStack(Material.DIAMOND_PICKAXE, 1);
		ItemMeta meta = item.getItemMeta();
		
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
	
	public void onDamaged(EntityDamageEvent e){
			e.setDamage((int) (e.getDamage()*1.5));
	}
	
	public void description(){
		architect.sendMessage("��6============= ��f[ ��b���డ ��f] - ��e������ ��7: ��cAttacker ��6=============\n"
				+ "��f- ��a�ֽ�ų ��7: ��d�ͷ� �Ǽ�\n��73���� �ð��� �鿩 �ͷ��� �Ǽ��մϴ�. ��c�Ǽ��� �̵��Ұ�, ���ݹ����� �Ǽ� ���\n"
				+ "��f- ��a������ų ��7: ��d�ͷ� ��ȭ/��ž ����\n��75���� �ð��� �鿩 �ͷ��� ��ȭ�մϴ�. ��c��ž�� �̹� ��ȭ�� ���¸� ��ž�� ������\n"
				+ "��f- ��2�ñر��f(��c���Ž� ��밡�ɡ�f) ��7: ��d���� �ͷ�\n��710�ʰ� �ͷ��� ü��, ���ݼӵ�, ���ݷ��� �����մϴ�.\n"
				+ "��f- ��2�нú��f(��c���Ž� ��밡�ɡ�f) ��7: ��d��� ���\n��7�ͷ��� ü���� 1.25�谡 �˴ϴ�.");
	}
	
	/*public void PrimarySkill(final Player p){
		if(Cooldown.Checkcooldown(p, "�ֽ�ų")){
			final Player t = MobSystem.Getcorsurplayer(p, 12);
			if ((t == null) || (MobSystem.getEnemy(p).contains(t))) {
				p.sendMessage(Main.MS + "12ĭ���� �ٶ󺸴°��� ���� �����ϴ�.");
			    return;
			}
			Cooldown.Setcooldown(p, "�ֽ�ų", 10, true);
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
			p.sendMessage(Main.MS + t.getName() + " ���� ü���� ȸ�������ݴϴ�.");
			t.sendMessage(Main.MS + "ġ���� �ް� �ֽ��ϴ�.");
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
						p.sendMessage(Main.MS + t.getName() + " ���� ȸ���� �������ϴ�.");
					}
				}
			}, 0l, 20l);
		}
	}

	public void SecondarySkill(final Player p){
		if(Cooldown.Checkcooldown(p, "������ų")){
			Cooldown.Setcooldown(p, "������ų", 18, true);
			for(Player t : MobSystem.getTeam(p)){
				if(t.getLocation().distance(p.getLocation()) <= 10){
					t.getWorld().playSound(t.getLocation(), Sound.ORB_PICKUP, 1.5f, 0.7f);
					Location loc = new Location(t.getWorld(), t.getLocation().getX(), t.getLocation().getY()+2, t.getLocation().getZ());
					ParticleEffect.HEART.display(0, 0, 0, 0, 1, loc, 20);
					p.sendMessage(Main.MS + t.getName() + " ���� ü���� ȸ�������ּ̽��ϴ�.");
					MobSystem.Addhp(t, can_passive ? 12 : 8);
				}
			}

		}
	}
	
	/*public void UltimateSkill(final Player p){
		if(Cooldown.Checkcooldown(p, "�ñر�")){
			int amt = 0;
			if((MobSystem.bluelist.contains(p) ? MobSystem.bluedead.size() : MobSystem.reddead.size()) <= 0){
				p.sendMessage(Main.MS+"���� ������ �����ϴ�.");
				return;
			}
			for(Location l : MobSystem.bluelist.contains(p) ? MobSystem.bluedead.keySet() : MobSystem.reddead.keySet()){
					if((MobSystem.bluelist.contains(p) ? MobSystem.bluedead : MobSystem.reddead).containsKey(l)){
						Player t = (MobSystem.bluelist.contains(p) ? MobSystem.bluedead : MobSystem.reddead).get(l);
						if(t != null){
							if(MobSystem.ablist.containsKey(t.getName())){
								Ability ability = MobSystem.ablist.get(t.getName());
								ability.Revive(t);
								t.sendMessage(Main.MS+"���� "+p.getName()+" �Կ� ���Ͽ� ��Ȱ�մϴ�.");
								amt++;
							}
						}
					}
			}
				p.sendMessage(Main.MS+amt+"���� ������ ��Ȱ���׽��ϴ�.");
				Cooldown.Setcooldown(p, "�ñر�", 213, true);
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
			if(!can_Ultimate){ architect.sendMessage(Main.MS+"�ñر�� �������� ������ ��밡���մϴ�."); return;}
			e.getPlayer().getInventory().setHeldItemSlot(0);
			//UltimateSkill(e.getPlayer());
		}
	}
	
	public void onHit(EntityDamageByEntityEvent e){
		e.setDamage(4);
	}*/
}


