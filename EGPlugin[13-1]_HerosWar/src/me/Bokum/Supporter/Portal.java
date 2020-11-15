package me.Bokum.Supporter;

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

public class Portal extends Ability{

	public Player portal;
	public Location redportal;
	public Location blueportal;
	
	public Portal(String playername, Player p){
		super(playername, "��Ż");
		portal = p;
		ItemStack item = new ItemStack(296, 1);
		ItemMeta meta = item.getItemMeta();
		meta.setDisplayName("��f[ ��b��Ż ��f]");
		List<String> lorelist = new ArrayList<String>();
		lorelist.add("��f- ��7���ݷ� : ��63~5");
		lorelist.add("��f- ��7������ ���� : ��6���� ������ x 0.8");
		lorelist.add("��f- ��7���� : ��6�ڡ١١١�");
		lorelist.add("��f- ��7�⵿ : ��6�ڡڡڡڡ�");
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
		
		redportal = new Location(p.getWorld(), p.getLocation().getBlockX(), p.getLocation().getBlockY(), p.getLocation().getBlockZ());
		blueportal = new Location(p.getWorld(), p.getLocation().getBlockX(), p.getLocation().getBlockY(), p.getLocation().getBlockZ());
	}
	
	public void description(){
		portal.sendMessage("��6============= ��f[ ��b�Ŀ� ��f] - ��e������ ��7: ��cExpert ��6=============\n"
				+ "��f- ��a�ֽ�ų ��7: ��d��� ��Ż\n��710ĭ���� �ٶ󺸴� ���� �����Ż�� ��ġ�մϴ�.\n"
				+ "��f- ��a������ų ��7: ��d���� ��Ż\n��710ĭ���� �ٶ󺸴� ���� ������Ż�� ��ġ�մϴ�.\n"
				+ "��f- ��2�ñر��f(��c���Ž� ��밡�ɡ�f) ��7: ��d��ġ ��ȯ\n��720ĭ���� �ٶ󺸴� ���� ��ġ�� ��ȯ�մϴ�.\n"
				+ "��f- ��2�нú��f(��c���Ž� ��밡�ɡ�f) ��7: ��d��Ż ������ ���\n��7��Ż�� ��Ÿ���� 1�� �����մϴ�.");
	}
	
	public void PrimarySkill(final Player p){
		if(Cooldown.Checkcooldown(p, "�ֽ�ų")){
			Block b = null;
			try{
				b = p.getTargetBlock(null, 10);
			}catch(Exception exception){
				return;
			}
			if(b == null || b.getType() == Material.AIR){
				p.sendMessage(Main.MS+"10ĭ �̳��� ������ ����� �����մϴ�.");
				return;
			}
			if((b.getRelative(0, 1, 0).getType() != Material.AIR && b.getRelative(0, 1, 0).getType() != Material.PORTAL ) || b.getRelative(0, 2, 0).getType() != Material.AIR){
				p.sendMessage(Main.MS+"��� ���� 2ĭ ���� �ƹ����� ������մϴ�.");
				return;
			}
			Cooldown.Setcooldown(p, "�ֽ�ų", can_passive? 4 : 5, true);
			blueportal.getBlock().setType(Material.AIR);
			MobSystem.portalloc.remove(blueportal);
			MobSystem.portalloc.remove(redportal);
			blueportal = new Location(b.getWorld(), b.getLocation().getBlockX(), b.getLocation().getBlockY()+1
					, b.getLocation().getBlockZ());
			MobSystem.portalloc.put(blueportal, redportal);
			MobSystem.portalloc.put(redportal, blueportal);
			blueportal.getBlock().setType(Material.PORTAL);
			p.getWorld().playSound(p.getLocation(), Sound.ENDERMAN_HIT, 2.0f, 1.5f);
		}
	}

	public void SecondarySkill(final Player p){
		if(Cooldown.Checkcooldown(p, "������ų")){
			Block b = null;
			try{
				b = p.getTargetBlock(null, 10);
			}catch(Exception exception){
				return;
			}
			if(b == null || b.getType() == Material.AIR){
				p.sendMessage(Main.MS+"10ĭ �̳��� ������ ����� �����մϴ�.");
				return;
			}
			if((b.getRelative(0, 1, 0).getType() != Material.AIR && b.getRelative(0, 1, 0).getType() != Material.PORTAL ) || b.getRelative(0, 2, 0).getType() != Material.AIR){
				p.sendMessage(Main.MS+"��� ���� 2ĭ ���� �ƹ����� ������մϴ�.");
				return;
			}
			for(int i = 0; i < 3; i++){
				if(b.getRelative(0, -1, 0).getLocation().distance(Main.Core[i]) <= 40){
					p.sendMessage(Main.MS+"�������� 40ĭ������ ��Ż�� ��ġ�Ͻ� �� �����ϴ�.");
					return;
				}
			}
			Cooldown.Setcooldown(p, "������ų", can_passive? 4 : 5, true);
			redportal.getBlock().setType(Material.AIR);
			MobSystem.portalloc.remove(blueportal);
			MobSystem.portalloc.remove(redportal);
			redportal = new Location(b.getWorld(), b.getLocation().getBlockX(), b.getLocation().getBlockY()+1
					, b.getLocation().getBlockZ());
			MobSystem.portalloc.put(blueportal, redportal);
			MobSystem.portalloc.put(redportal, blueportal);
			redportal.getBlock().setType(Material.PORTAL);
			p.getWorld().playSound(p.getLocation(), Sound.ENDERMAN_HIT, 2.0f, 1.5f);
		}
	}
	
	public void UltimateSkill(final Player p){
		if(Cooldown.Checkcooldown(p, "�ñر�")){
			Player t = MobSystem.Getcorsurplayer(p, 20);
			if(t == null || MobSystem.getTeam(p).contains(t)){
				p.sendMessage(Main.MS+"20ĭ���� �ٶ󺸴°��� ���� �����ϴ�.");
				return;
			}
			Cooldown.Setcooldown(p, "�ñر�", 89, true);
			p.getWorld().playSound(p.getLocation(), Sound.ENDERMAN_TELEPORT, 2.0f, 0.5f);
		    ParticleEffect.PORTAL.display(0.1f, 0.1f, 0.1f, 0, 40, p.getLocation(), 25);
		    p.sendMessage(Main.MS+t.getName()+" �԰� ��ġ�� �ٲ�����ϴ�.");
			t.getWorld().playSound(p.getLocation(), Sound.ENDERMAN_TELEPORT, 2.0f, 0.5f);
		    ParticleEffect.PORTAL.display(0.1f, 0.1f, 0.1f, 0, 40, t.getLocation(), 25);
		    t.sendMessage(Main.MS+"��Ż�� �ñر⿡ ���� "+portal.getName()+" �԰� ��ġ�� �ٲ�����ϴ�.");
		    Location l = p.getLocation();
		    p.teleport(t.getLocation());
		    t.teleport(l);
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
			if(!can_Ultimate){ portal.sendMessage(Main.MS+"�ñر�� �������� ������ ��밡���մϴ�."); return;}
			e.getPlayer().getInventory().setHeldItemSlot(0);
			UltimateSkill(e.getPlayer());
		}
	}
	
	public void onHit(EntityDamageByEntityEvent e){
		if(portal.getInventory().getHeldItemSlot() != 0) return;
		e.setDamage(MobSystem.Getrandom(3, 5));
	}
	
}



