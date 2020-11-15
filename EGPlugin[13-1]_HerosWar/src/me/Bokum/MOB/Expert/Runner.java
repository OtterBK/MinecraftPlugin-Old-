package me.Bokum.MOB.Expert;

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

public class Runner extends Ability{

	public Player runner;
	
	public Runner(String playername, Player p){
		super(playername, "����");
		runner = p;
		ItemStack item = new ItemStack(276, 1);
		ItemMeta meta = item.getItemMeta();
		meta.setDisplayName("��f[ ��b���� ��f]");
		List<String> lorelist = new ArrayList<String>();
		lorelist.add("��f- ��7���ݷ� : ��65~7");
		lorelist.add("��f- ��7������ ���� : ��6���� ������ x 1");
		lorelist.add("��f- ��7���� : ��6�ڡڡڡ١�");
		lorelist.add("��f- ��7�⵿ : ��6�ڡڡڡڡ�");
		lorelist.add("��f- ��7���� : ��6�ڡڡڡ١�");
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
		runner.sendMessage("��6============= ��f[ ��b���� ��f] - ��e������ ��7: ��cExpert ��6=============\n"
				+ "��f- ��a�ֽ�ų ��7: ��d�Ƶ巹����\n��7���׹̳��� ���� ȸ���մϴ�."
				+ "��f- ��a������ų ��7: ��d��ȣ��\n��7��ȣ�⸦ ��ġ�Ͽ� ��Ƽ������ ��ȣ���� ��ġ���� ��Ȱ�� �� �ֵ��� ���ݴϴ�.\n"
				+ "��f- ��2�ñر��f(��c���Ž� ��밡�ɡ�f) ��7: ��d����\n��715�ʰ� ��Ƽ���鿡�� �ż�3�� ������ �ݴϴ�.\n"
				+ "��f- ��2�нú��f(��c���Ž� ��밡�ɡ�f) ��7: ��d���� ����\n��7Ÿ�ݽ� 10%Ȯ���� 2�� �����մϴ�.");
	}
	
	public void PrimarySkill(final Player p){
		if(Cooldown.Checkcooldown(p, "�ֽ�ų")){
			Cooldown.Setcooldown(p, "�ֽ�ų", 21, true);
			p.getWorld().playSound(p.getLocation(), Sound.BREATH, 2.0f, 1.0f);
			p.sendMessage(Main.MS+"���׹̳��� ȸ���߽��ϴ�.");
			p.setExp(1);
		}
	}

	public void SecondarySkill(final Player p){
		if(Cooldown.Checkcooldown(p, "������ų")){
			Cooldown.Setcooldown(p, "�ֽ�ų", 60, true);
		}
	}
	
	public void UltimateSkill(final Player p){
		if(Cooldown.Checkcooldown(p, "�ñر�")){
			Cooldown.Setcooldown(p, "�ñر�", 102, true);
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
				if(t.getLocation().distance(p.getLocation()) <= 7){
					Bukkit.getScheduler().scheduleSyncDelayedTask(Main.instance, new Runnable(){
						public void run(){
							p.teleport(t.getLocation().subtract(t.getEyeLocation().getDirection()));
							t.damage(4, p);
							t.getWorld().playSound(t.getLocation(), Sound.HURT, 1.5f, 1.2f);
						    ParticleEffect.REDSTONE.display(0.3f, 0.3f, 0.3f, 0, 40, t.getLocation(), 25);
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
			if(!can_Ultimate){ runner.sendMessage(Main.MS+"�ñر�� �������� ������ ��밡���մϴ�."); return;}
			e.getPlayer().getInventory().setHeldItemSlot(0);
			UltimateSkill(e.getPlayer());
		}
	}
	
	public void onHit(EntityDamageByEntityEvent e){
		if(can_passive && MobSystem.Getrandom(0, 9) == 0){
			e.setDamage(MobSystem.Getrandom(5, 7)*2);
		    ParticleEffect.LAVA.display(0.3f, 0.3f, 0.3f, 0, 40, e.getEntity().getLocation(), 25);
		    Player d = (Player) e.getDamager();
			d.getWorld().playSound(d.getLocation(), Sound.DIG_STONE, 1.5f, 2.0f);
		    d.sendMessage(Main.MS+"�ι� �����߽��ϴ�!");
		}else{
			e.setDamage(MobSystem.Getrandom(5, 7));
		}
	}
	
}

