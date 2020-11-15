package me.Bokum.MOB.Attacker;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.entity.Entity;
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
import de.slikey.effectlib.effect.CircleEffect;
import de.slikey.effectlib.effect.CloudEffect;
import me.Bokum.MOB.Main;
import me.Bokum.MOB.Ability.Ability;
import me.Bokum.MOB.Game.Cooldown;
import me.Bokum.MOB.Game.MobSystem;
import me.Bokum.MOB.Utility.ParticleEffect;
import me.Bokum.MOB.Utility.Vector3D;

public class Assasin extends Ability{

	public Player assasin;
	public boolean skill2 = false;
	public boolean hiding = false;
	
	public Assasin(String playername, Player p){
		super(playername, "�ϻ���");
		assasin = p;
		ItemStack item = new ItemStack(276, 1);
		ItemMeta meta = item.getItemMeta();
		meta.setDisplayName("��f[ ��b�ϻ��� ��f]");
		List<String> lorelist = new ArrayList<String>();
		lorelist.add("��f- ��7���ݷ� : ��67~8");
		lorelist.add("��f- ��7������ ���� : ��6���� ������ x 2");
		lorelist.add("��f- ��7���� : ��6�ڡڡڡڡ�");
		lorelist.add("��f- ��7�⵿ : ��6�ڡ١١١�");
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
		assasin.sendMessage("��6============= ��f[ ��b�ϻ��� ��f] - ��e������ ��7: ��cAttacker ��6=============\n"
				+ "��f- ��a�ֽ�ų ��7: ��d����\n��77�ʰ� ����� ����ϴ�. ��c�÷��̾� Ÿ�ݽ� ������ �����˴ϴ�. �����Դ� ���ŵ� ������� �Ⱥ��Դϴ�. �����ð��� ����ġ�ٷ� ���� �� �ֽ��ϴ�.\n "
				+ "��f- ��a������ų ��7: ��d�ϻ�\n��7���Ż��¿��� Ÿ�ݽ� ���ݷ��� 2�谡 �˴ϴ�.\n"
				+ "��f- ��2�ñر��f(��c���Ž� ��밡�ɡ�f) ��7: ��d����\n��7�ֺ� 9ĭ�̳��� �ִ� ��� ���� �ڷ� ���� 9�� �������� �ְ�ɴϴ�.\n"
				+ "��f- ��2�нú��f(��c���Ž� ��밡�ɡ�f) ��7: ��d�Ͻ�\n��7�ϻ���� 14%Ȯ���� ���� ��Ÿ�� �ʱ�ȭ");
	}
	
	public void PrimarySkill(final Player p){
		if(Cooldown.Checkcooldown(p, "�ֽ�ų")){
			Cooldown.Setcooldown(p, "�ֽ�ų", 20, false);
			hiding = true;
			p.getWorld().playSound(p.getLocation(), Sound.BAT_TAKEOFF, 2.0f, 4.0f);
			ParticleEffect.SMOKE_LARGE.display(0.1f, 0.1f, 0.1f, 0f, 25, p.getLocation(), 20);
			for(Player t : MobSystem.getEnemy(p)){
				t.hidePlayer(p);
			}
			p.sendMessage(Main.MS+"7�ʰ� ����� ����ϴ�.");
			timertime = 11;
			timernum = Bukkit.getScheduler().scheduleSyncRepeatingTask(Main.instance, new Runnable(){
				public void run(){
					if(--timertime > 0){
						p.setExp((float)timertime/10);
						p.playSound(p.getLocation(), Sound.BREATH, 0.4f, 1.5f);
					} else {
						Bukkit.getScheduler().cancelTask(timernum);
						for(Player t : MobSystem.getEnemy(p)){
							t.showPlayer(p);
						}
						hiding = false;
						p.sendMessage(Main.MS+"������ �����ƽ��ϴ�.");
						if(skill2){
							p.sendMessage(Main.MS+"������ Ǯ�� �ϻ콺ų�� ���� �Ǿ����ϴ�.");
							p.playSound(p.getLocation(),Sound.SHOOT_ARROW, 1.0f, 2.5f);
							skill2 = false;
						}
						p.setExp(0);
					}

				}
			}, 0l, 20l);
		}
	}
	
	public void onDamaged(EntityDamageEvent e){
		e.setDamage((int) (e.getDamage()*2));
	}
	
	public void SecondarySkill(final Player p){
		if(Cooldown.Checkcooldown(p, "�ϻ�")){
			if(!hiding){
				p.sendMessage(Main.MS+"���Ż��¿����� ����� �����մϴ�.");
				return;
			}
			Cooldown.Setcooldown(p, "�ϻ�", 20, true);
			skill2 = true;
			p.getWorld().playSound(p.getLocation(), Sound.IRONGOLEM_DEATH, 2.0f, 3.0f);
			ParticleEffect.FLAME.display(0.1f, 0.1f, 0.1f, 0.1f, 25, p.getLocation(), 20);
		}
	}
	
	public void UltimateSkill(final Player p){
		if(Cooldown.Checkcooldown(p, "�ñر�")){
			Cooldown.Setcooldown(p, "�ñر�", 106, true);
			p.getWorld().playSound(p.getLocation(), Sound.MAGMACUBE_JUMP, 2.0f, 0.5f);
		      CloudEffect particle = new CloudEffect(Main.effectManager);
		      particle.setEntity(p);
		      particle.period = 1;
		      particle.iterations = 20;
		      particle.speed = 4;
		      particle.particleRadius = 1f;
		      particle.cloudSize = 2f;
		      particle.start();
			int amt = 1;
			for(int i = 0; i < MobSystem.getEnemy(p).size(); i++){
				final Player t = MobSystem.getEnemy(p).get(i);
				if(t.getLocation().distance(p.getLocation()) <= 9){
					Bukkit.getScheduler().scheduleSyncDelayedTask(Main.instance, new Runnable(){
						public void run(){
							p.teleport(t.getLocation().subtract(t.getEyeLocation().getDirection()));
							t.damage(9, p);
							t.getWorld().playSound(t.getLocation(), Sound.HURT, 1.5f, 1.2f);
						    ParticleEffect.REDSTONE.display(0.3f, 0.3f, 0.3f, 0, 20, t.getLocation(), 25);
						}
					},(amt++*10));
				}
			}
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
			if(!can_Ultimate){ assasin.sendMessage(Main.MS+"�ñر�� �������� ������ ��밡���մϴ�."); return;}
			e.getPlayer().getInventory().setHeldItemSlot(0);
			UltimateSkill(e.getPlayer());
		}
	}
	
	public void onHit(EntityDamageByEntityEvent e){
		Player d = (Player) e.getDamager();
		Player p = (Player) e.getEntity();
		e.setDamage(MobSystem.Getrandom(7, 8));
		if(hiding){
			Bukkit.getScheduler().cancelTask(timernum);
			for(Player t : MobSystem.getEnemy(d)){
				t.showPlayer(d);
			}
			d.setExp(0);
			hiding = false;
			d.sendMessage(Main.MS+"������ Ǯ�Ƚ��ϴ�!");
			if(skill2){
				d.sendMessage(Main.MS+"���� �޼Ҹ� �����߽��ϴ�! (�ϻ콺ų)");
				e.setDamage(MobSystem.Getrandom(14, 18));
				skill2 = false;
			}
		}
	}
	
}
