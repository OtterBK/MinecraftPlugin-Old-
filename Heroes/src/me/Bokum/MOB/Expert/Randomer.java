package me.Bokum.MOB.Expert;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.block.Block;
import org.bukkit.craftbukkit.v1_5_R3.entity.CraftPlayer;
import org.bukkit.entity.Player;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.event.player.PlayerItemHeldEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

import de.slikey.effectlib.effect.CloudEffect;
import de.slikey.effectlib.effect.GridEffect;
import de.slikey.effectlib.effect.LineEffect;
import me.Bokum.MOB.Main;
import me.Bokum.MOB.Ability.Ability;
import me.Bokum.MOB.Game.Cooldown;
import me.Bokum.MOB.Game.MobSystem;
import me.Bokum.MOB.Utility.ParticleEffect;
import net.minecraft.server.v1_5_R3.DamageSource;

public class Randomer extends Ability{

	public Player randomer;
	
	public Randomer(String playername, Player p){
		super(playername, "������");
		randomer = p;
		ItemStack item = new ItemStack(337, 1);
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
	
	public void description(){
		randomer.sendMessage("��6============= ��f[ ��b������ ��f] - ��e������ ��7: ��cExpert ��6=============\n"
				+ "��f- ��a�ֽ�ų ��7: ��d�ױ� ���\n��7��33%Ȯ���� ü���� 6ȸ���մϴ�. ��33%Ȯ���� 6 �������� �޽��ϴ�.\n��33%Ȯ���� �ƹ��ϵ� �Ͼ�� �ʽ��ϴ�.\n"
				+ "��f- ��a������ų ��7: ��d�� �ٿ�\n��7��33%Ȯ���� �ż�2 ������ �޽��ϴ�. ��33%Ȯ���� ����2 ������ �޽��ϴ�.\n��33%Ȯ���� �ƹ��ϵ� �Ͼ�� �ʽ��ϴ�. �ִ�6 ������\n"
				+ "��f- ��2�ñر��f(��c���Ž� ��밡�ɡ�f) ��7: ��d�ñر� ���� �ڽ�\n��7�ϻ���, ����, �չ�, Ʈ���̼��� �ñر� �� 1���� ����մϴ�.\n"
				+ "��f- ��2�нú��f(��c���Ž� ��밡�ɡ�f) ��7: ��d����� ����Ŭ�ι�\n��7�ƹ��ϵ� �Ͼ�� ���� Ȯ���� ������� ��� ���� ���� �Ͼ Ȯ���� 33% �����մϴ�.");
	}
	
	public void PrimarySkill(final Player p){
		if(Cooldown.Checkcooldown(p, "�ֽ�ų")){
			Cooldown.Setcooldown(p, "�ֽ�ų", 10, true);
			Location l = p.getLocation();
			l.setY(l.getY()+2);
			switch(MobSystem.Getrandom(0, 2)){
			case 0:
				MobSystem.Addhp(p, 6); p.getWorld().playSound(p.getLocation(), Sound.LEVEL_UP, 1.5f, 1.5f);
				ParticleEffect.NOTE.display(0f, 0f, 0f, 0f, 1, l, 30);
				p.sendMessage(Main.MS+"���� ���� �Ͼ�׿�!");break;
			case 1:
				MobSystem.Minushp(p, 6); p.getWorld().playSound(p.getLocation(), Sound.NOTE_STICKS, 1.5f, 1.0f);
				ParticleEffect.SMOKE_NORMAL.display(0f, 0f, 0f, 0f, 1, l, 30);
				p.sendMessage(Main.MS+"������ ���� �Ͼ�׿�!");break;
			case 2:
				if(can_passive){
					MobSystem.Addhp(p, 6); p.getWorld().playSound(p.getLocation(), Sound.LEVEL_UP, 1.5f, 1.5f);
					p.sendMessage(Main.MS+"���� ���� �Ͼ�׿�!");
				}else{
					p.getWorld().playSound(p.getLocation(), Sound.CHICKEN_IDLE, 1.5f, 1.5f);
					ParticleEffect.FIREWORKS_SPARK.display(0f, 0f, 0f, 0f, 10, l, 30);
					p.sendMessage(Main.MS+"�ƹ��� �ϵ� �Ͼ�� �ʾҳ׿�!");
				}
				break;
			}
		}
	}

	public void SecondarySkill(final Player p){
		if(Cooldown.Checkcooldown(p, "������ų")){
			Cooldown.Setcooldown(p, "������ų", 13, true);
			Location l = p.getLocation();
			l.setY(l.getY()+2);
			switch(MobSystem.Getrandom(0, 2)){
			case 0:
				p.addPotionEffect(new PotionEffect(PotionEffectType.SPEED, 120, 1));
				p.getWorld().playSound(p.getLocation(), Sound.LEVEL_UP, 1.5f, 1.5f);
				ParticleEffect.NOTE.display(0f, 0f, 0f, 0f, 1, l, 30);
				p.sendMessage(Main.MS+"���� ���� �Ͼ�׿�!");break;
			case 1:
				p.addPotionEffect(new PotionEffect(PotionEffectType.SLOW, 120, 1));
				p.getWorld().playSound(p.getLocation(), Sound.NOTE_STICKS, 1.5f, 1.0f);
				ParticleEffect.NOTE.display(0f, 0f, 0f, 0f, 1, l, 30);
				p.sendMessage(Main.MS+"������ ���� �Ͼ�׿�!");break;
			case 2:
				if(can_passive){
					p.addPotionEffect(new PotionEffect(PotionEffectType.SPEED, 120, 1));
					p.getWorld().playSound(p.getLocation(), Sound.LEVEL_UP, 1.5f, 1.5f);
					p.sendMessage(Main.MS+"���� ���� �Ͼ�׿�!");
				}else{
					p.getWorld().playSound(p.getLocation(), Sound.CHICKEN_IDLE, 1.5f, 1.5f);
					ParticleEffect.FIREWORKS_SPARK.display(0f, 0f, 0f, 0f, 10, l, 30);
					p.sendMessage(Main.MS+"�ƹ��� �ϵ� �Ͼ�� �ʾҳ׿�!");
				}
				break;
			}
		}
	}
	
	public void UltimateSkill(final Player p){
		/*if(Cooldown.Checkcooldown(p, "�ñر�")){
			switch(MobSystem.Getrandom(0, 3)){
			case 0: u_1(p); break;
			case 1: u_2(p); break;
			case 2: u_3(p); break;
			case 3: u_4(p); break;
			//case 4: u_5(p); break;
			
			default: return;
			}
		}*/
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
			if(!can_Ultimate){ randomer.sendMessage(Main.MS+"�ñر�� �������� ������ ��밡���մϴ�."); return;}
			e.getPlayer().getInventory().setHeldItemSlot(0);
			UltimateSkill(e.getPlayer());
		}
	}
	
	public void onHit(EntityDamageByEntityEvent e){
	}
}
