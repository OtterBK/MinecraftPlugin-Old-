package me.Bokum.MOB.Expert;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.block.Block;
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

public class Engineer extends Ability{

	public Player engineer;
	public boolean power = false;
	
	
	public Engineer(String playername, Player p){
		super(playername, "�����Ͼ�");
		engineer = p;
		ItemStack item = new ItemStack(257, 1);
		ItemMeta meta = item.getItemMeta();
		meta.setDisplayName("��f[ ��b�����Ͼ� ��f]");
		List<String> lorelist = new ArrayList<String>();
		lorelist.add("��f- ��7���ݷ� : ��64~6");
		lorelist.add("��f- ��7������ ���� : ��6���� ������ x 0.8");
		lorelist.add("��f- ��7���� : ��6�ڡڡڡ١�");
		lorelist.add("��f- ��7�⵿ : ��6�ڡڡڡڡ�");
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
		engineer.sendMessage("��6============= ��f[ ��b�����Ͼ� ��f] - ��e������ ��7: ��cExpert ��6=============\n"
				+ "��f- ��a�ֽ�ų ��7: ��d���� �︮����\n��73.5�ʰ� �����մϴ�.\n"
				+ "��f- ��a������ų ��7: ��d�Ŀ� �尩\n��710�ʰ� ���ݷ��� 2����մϴ�.\n"
				+ "��f- ��2�ñر��f(��c���Ž� ��밡�ɡ�f) ��7: ��d���� �̵���\n��7100ĭ �̳��� �ٶ󺸴� ������ �̵��մϴ�.\n"
				+ "��f- ��2�нú��f(��c���Ž� ��밡�ɡ�f) ��7: ��d�Ŀ� ������\n��7�볪�� �︮������ ���ӽð��� 1.5��, �����尩�� ���ӽð��� 3�� �����մϴ�.");
	}
	
	public void PrimarySkill(final Player p){
		if(Cooldown.Checkcooldown(p, "�ֽ�ų")){
			if(Main.Bluetop.distance(p.getLocation()) <= 50 || Main.Redtop.distance(p.getLocation()) <= 50){
				p.sendMessage(Main.MS+"��ž ��ó 50ĭ�������� ����Ͻ� �� �����ϴ�.");
				return;
			}
			Cooldown.Setcooldown(p, "�ֽ�ų", 24, true);
			p.getWorld().playSound(p.getLocation(), Sound.ENDERDRAGON_WINGS, 2.0f, 1.0f);
		    ParticleEffect.SMOKE_LARGE.display(0.1f, 0.1f, 0.1f, 0, 20, p.getLocation(), 25);
			p.sendMessage(Main.MS+"���� �︮���͸� ����մϴ�.");
			p.setAllowFlight(true);
			p.setFlying(true);
			Cooldown.SkillCountdown(p, can_passive ? 5 : 3, "���� �︮���� ���");
			Bukkit.getScheduler().scheduleSyncDelayedTask(Main.instance, new Runnable(){
				public void run(){
					p.setAllowFlight(false);
					p.setFlying(false);
					p.getWorld().playSound(p.getLocation(), Sound.BAT_TAKEOFF, 2.0f, 2.0f);
				}
			}, can_passive ? 100 : 70);
		}
	}

	public void SecondarySkill(final Player p){
		if(Cooldown.Checkcooldown(p, "������ų")){
			Cooldown.Setcooldown(p, "������ų", 31, true);
			p.getWorld().playSound(p.getLocation(), Sound.IRONGOLEM_HIT, 2.0f, 2.0f);
			ParticleEffect.CRIT_MAGIC.display(0.1f, 0.1f, 0.1f, 0, 20, p.getLocation(), 25);
			p.sendMessage(Main.MS+"�Ŀ� �尩�� ����մϴ�.");
			power = true;
			Cooldown.SkillCountdown(p, can_passive ? 13 : 10, "�Ŀ� �尩 ���");
			Bukkit.getScheduler().scheduleSyncDelayedTask(Main.instance, new Runnable(){
				public void run(){
					power = false;
					p.getWorld().playSound(p.getLocation(), Sound.IRONGOLEM_DEATH, 2.0f, 2.0f);
				}
			}, can_passive ? 260 : 200);
		}
	}
	
	public void UltimateSkill(final Player p){
		if(Cooldown.Checkcooldown(p, "�ñر�")){
			Block b = p.getTargetBlock(null, 100);
			if(b == null && b.getType() == Material.AIR){
				p.sendMessage(Main.MS+"�Ÿ��� �ʹ� �ٴϴ�.");
				return;
			}
			if(b.getLocation().distance(Main.Bluetop) <= 20 || b.getLocation().distance(Main.Redtop) <= 20){
				p.sendMessage(Main.MS+"��ž �ֺ� 20ĭ���� ��ǥ�δ� ����Ͻ� �� �����ϴ�.");
				return;
			}
			Cooldown.Setcooldown(p, "�ñر�", 53, true);
			p.getWorld().playSound(p.getLocation(), Sound.ENDERMAN_TELEPORT, 2.0f, 0.5f);
		    ParticleEffect.PORTAL.display(0.1f, 0.1f, 0.1f, 0, 20, p.getLocation(), 25);
			b.getWorld().playSound(b.getLocation(), Sound.ENDERMAN_TELEPORT, 2.0f, 0.5f);
		    ParticleEffect.PORTAL.display(0.1f, 0.1f, 0.1f, 0, 20, b.getLocation(), 25);
		    b.getLocation().setPitch(p.getLocation().getPitch());
		    b.getLocation().setYaw(p.getLocation().getYaw());
		    b.getLocation().setY(b.getLocation().getY()+1);
		    p.teleport(b.getLocation());
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
			if(!can_Ultimate){ engineer.sendMessage(Main.MS+"�ñر�� �������� ������ ��밡���մϴ�."); return;}
			e.getPlayer().getInventory().setHeldItemSlot(0);
			UltimateSkill(e.getPlayer());
		}
	}
	
	public void onHit(EntityDamageByEntityEvent e){
		if(power){
			e.getEntity().getWorld().playSound(e.getEntity().getLocation(), Sound.SKELETON_DEATH, 2.0f, 0.5f);
		    ParticleEffect.TOWN_AURA.display(0.1f, 0.1f, 0.1f, 0, 15, e.getEntity().getLocation(), 25);
			e.setDamage(MobSystem.Getrandom(6, 8));
		}else{
			e.setDamage(MobSystem.Getrandom(4, 6));
		}
	}
	
}


