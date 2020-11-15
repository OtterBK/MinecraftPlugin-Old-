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
		super(playername, "랜더머");
		randomer = p;
		ItemStack item = new ItemStack(337, 1);
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
		
		item = new ItemStack(388, 1);
		meta = item.getItemMeta();
		meta.setDisplayName("§f[ §c궁극기 §f]");
		item.setItemMeta(meta);
		
		p.getInventory().setItem(3 ,item);
	}
	
	public void description(){
		randomer.sendMessage("§6============= §f[ §b랜더머 §f] - §e직업군 §7: §cExpert §6=============\n"
				+ "§f- §a주스킬 §7: §d죽기 살기\n§7약33%확률로 체력을 6회복합니다. 약33%확률로 6 데미지를 받습니다.\n약33%확률로 아무일도 일어나지 않습니다.\n"
				+ "§f- §a보조스킬 §7: §d업 다운\n§7약33%확률로 신속2 버프를 받습니다. 약33%확률로 구속2 버프를 받습니다.\n약33%확률로 아무일도 일어나지 않습니다. 최대6 데미지\n"
				+ "§f- §2궁극기§f(§c구매시 사용가능§f) §7: §d궁극기 랜덤 박스\n§7암살자, 쉴더, 붐버, 트레이서의 궁극기 중 1개를 사용합니다.\n"
				+ "§f- §2패시브§f(§c구매시 사용가능§f) §7: §d행운의 네잎클로버\n§7아무일도 일어나지 않을 확률이 사라지고 대신 좋은 일이 일어날 확률이 33% 증가합니다.");
	}
	
	public void PrimarySkill(final Player p){
		if(Cooldown.Checkcooldown(p, "주스킬")){
			Cooldown.Setcooldown(p, "주스킬", 10, true);
			Location l = p.getLocation();
			l.setY(l.getY()+2);
			switch(MobSystem.Getrandom(0, 2)){
			case 0:
				MobSystem.Addhp(p, 6); p.getWorld().playSound(p.getLocation(), Sound.LEVEL_UP, 1.5f, 1.5f);
				ParticleEffect.NOTE.display(0f, 0f, 0f, 0f, 1, l, 30);
				p.sendMessage(Main.MS+"좋은 일이 일어났네요!");break;
			case 1:
				MobSystem.Minushp(p, 6); p.getWorld().playSound(p.getLocation(), Sound.NOTE_STICKS, 1.5f, 1.0f);
				ParticleEffect.SMOKE_NORMAL.display(0f, 0f, 0f, 0f, 1, l, 30);
				p.sendMessage(Main.MS+"안좋은 일이 일어났네요!");break;
			case 2:
				if(can_passive){
					MobSystem.Addhp(p, 6); p.getWorld().playSound(p.getLocation(), Sound.LEVEL_UP, 1.5f, 1.5f);
					p.sendMessage(Main.MS+"좋은 일이 일어났네요!");
				}else{
					p.getWorld().playSound(p.getLocation(), Sound.CHICKEN_IDLE, 1.5f, 1.5f);
					ParticleEffect.FIREWORKS_SPARK.display(0f, 0f, 0f, 0f, 10, l, 30);
					p.sendMessage(Main.MS+"아무런 일도 일어나지 않았네요!");
				}
				break;
			}
		}
	}

	public void SecondarySkill(final Player p){
		if(Cooldown.Checkcooldown(p, "보조스킬")){
			Cooldown.Setcooldown(p, "보조스킬", 13, true);
			Location l = p.getLocation();
			l.setY(l.getY()+2);
			switch(MobSystem.Getrandom(0, 2)){
			case 0:
				p.addPotionEffect(new PotionEffect(PotionEffectType.SPEED, 120, 1));
				p.getWorld().playSound(p.getLocation(), Sound.LEVEL_UP, 1.5f, 1.5f);
				ParticleEffect.NOTE.display(0f, 0f, 0f, 0f, 1, l, 30);
				p.sendMessage(Main.MS+"좋은 일이 일어났네요!");break;
			case 1:
				p.addPotionEffect(new PotionEffect(PotionEffectType.SLOW, 120, 1));
				p.getWorld().playSound(p.getLocation(), Sound.NOTE_STICKS, 1.5f, 1.0f);
				ParticleEffect.NOTE.display(0f, 0f, 0f, 0f, 1, l, 30);
				p.sendMessage(Main.MS+"안좋은 일이 일어났네요!");break;
			case 2:
				if(can_passive){
					p.addPotionEffect(new PotionEffect(PotionEffectType.SPEED, 120, 1));
					p.getWorld().playSound(p.getLocation(), Sound.LEVEL_UP, 1.5f, 1.5f);
					p.sendMessage(Main.MS+"좋은 일이 일어났네요!");
				}else{
					p.getWorld().playSound(p.getLocation(), Sound.CHICKEN_IDLE, 1.5f, 1.5f);
					ParticleEffect.FIREWORKS_SPARK.display(0f, 0f, 0f, 0f, 10, l, 30);
					p.sendMessage(Main.MS+"아무런 일도 일어나지 않았네요!");
				}
				break;
			}
		}
	}
	
	public void UltimateSkill(final Player p){
		/*if(Cooldown.Checkcooldown(p, "궁극기")){
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
			if(!can_Ultimate){ randomer.sendMessage(Main.MS+"궁극기는 상점에서 구매후 사용가능합니다."); return;}
			e.getPlayer().getInventory().setHeldItemSlot(0);
			UltimateSkill(e.getPlayer());
		}
	}
	
	public void onHit(EntityDamageByEntityEvent e){
	}
}
