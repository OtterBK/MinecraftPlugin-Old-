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
		meta.setDisplayName("��f[ ��b������ ��f]");
		List<String> lorelist = new ArrayList<String>();
		lorelist.add("��f- ��7���ݷ� : ��63~11");
		lorelist.add("��f- ��7������ ���� : ��6���� ������ x 1");
		lorelist.add("��f- ��7���� : ��6�ڡڡڡڡ�");
		lorelist.add("��f- ��7�⵿ : ��6�ڡڡ١١�");
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
	
	public void u_1(final Player p){
		Cooldown.Setcooldown(p, "�ñر�", 156, true);
		p.sendMessage(Main.MS+"�ϻ��� �ñر� �ߵ�");
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
	
	public void u_2(final Player p){
		Cooldown.Setcooldown(p, "�ñر�", 124, true);
		p.sendMessage(Main.MS+"Ʈ���̼� �ñر� �ߵ�");
		final Location l = p.getLocation();
		ParticleEffect.REDSTONE.display(1, 1, 1, 0.02f, 150, l, 50);
		l.getWorld().playSound(l, Sound.ANVIL_LAND, 3.0f, 3.0f);
		Bukkit.getScheduler().scheduleSyncDelayedTask(Main.instance, new Runnable(){
			public void run(){
				l.getWorld().playSound(l, Sound.ANVIL_LAND, 3.0f, 3.0f);
				ParticleEffect.VILLAGER_ANGRY.display(1.2f, 1.2f, 1.2f, 0.03f, 35, l, 20);
			}
		}, 4l);
		Bukkit.getScheduler().scheduleSyncDelayedTask(Main.instance, new Runnable(){
			public void run(){
				l.getWorld().playSound(l, Sound.ANVIL_LAND, 3.0f, 3.0f);
				ParticleEffect.VILLAGER_ANGRY.display(1.2f, 1.2f, 1.2f, 0.03f, 35, l, 20);
			}
		}, 8l);
		Bukkit.getScheduler().scheduleSyncDelayedTask(Main.instance, new Runnable(){
			public void run(){
				l.getWorld().playSound(l, Sound.ANVIL_LAND, 3.0f, 3.0f);
				ParticleEffect.VILLAGER_ANGRY.display(1.2f, 1.2f, 1.2f, 0.03f, 35, l, 20);
			}
		}, 12l);
		Bukkit.getScheduler().scheduleSyncDelayedTask(Main.instance, new Runnable(){
			public void run(){
				l.getWorld().playSound(l, Sound.ANVIL_LAND, 3.0f, 3.0f);
				ParticleEffect.VILLAGER_ANGRY.display(1.2f, 1.2f, 1.2f, 0.03f, 35, l, 20);
			}
		}, 16l);
		Bukkit.getScheduler().scheduleSyncDelayedTask(Main.instance, new Runnable(){
			public void run(){
				l.getWorld().playSound(l, Sound.ANVIL_LAND, 3.0f, 3.0f);
				ParticleEffect.VILLAGER_ANGRY.display(1.2f, 1.2f, 1.2f, 0.03f, 35, l, 20);
			}
		}, 20l);
		Bukkit.getScheduler().scheduleSyncDelayedTask(Main.instance, new Runnable(){
			public void run(){
				ParticleEffect.EXPLOSION_NORMAL.display(1.5f, 1.5f, 1.5f, 0.03f, 10, l, 20);
				l.getWorld().playSound(l, Sound.EXPLODE, 3.0f, 1.0f);
				for(Player t : MobSystem.getEnemy(randomer)){
					if(t.getLocation().distance(l) <= 4){
						ignore = true;
						t.damage((8-(int)t.getLocation().distance(l))*2, randomer);
					}
				}
			}
		}, 24l); 
	}
	
	public void u_3(final Player p){
		final Block b = p.getTargetBlock(null, 30);
		p.sendMessage(Main.MS+"�չ� �ñر� �ߵ�");
		if(b == null || b.getType() == Material.AIR){
			p.sendMessage(Main.MS+"30ĭ�� ���� ���� ����ϼ���.");
			p.sendMessage(Main.MS+"�չ� �ñر� ��ҵ�");
			return;
		}
		Cooldown.Setcooldown(p, "�ñر�", 120, true);
		p.getWorld().playSound(p.getLocation(), Sound.ENDERDRAGON_GROWL, 2.0f, 0.1f);
		LineEffect le = new LineEffect(Main.effectManager);
		le.particles = 30;
		le.period = 5;
		le.iterations = 20;
		le.speed = 0f;
		le.particleOffsetX = 0;
		le.particleOffsetY = 0;
		le.particleOffsetZ = 0;
		le.setEntity(p);
		le.setTargetLocation(b.getLocation());
		le.start();
		p.addPotionEffect(new PotionEffect(PotionEffectType.SLOW, 80, 99));
		p.addPotionEffect(new PotionEffect(PotionEffectType.JUMP, 80, 199));
		Bukkit.getScheduler().scheduleSyncDelayedTask(Main.instance, new Runnable(){
			public void run(){
				p.getLocation().getWorld().playSound(p.getLocation(), Sound.CLICK, 1.0f, 0.5f);
				b.getWorld().playSound(b.getLocation(), Sound.BLAZE_HIT, 2.5f, 0.25f);
			}
		}, 0l);
		Bukkit.getScheduler().scheduleSyncDelayedTask(Main.instance, new Runnable(){
			public void run(){
				p.getLocation().getWorld().playSound(p.getLocation(), Sound.CLICK, 1.0f, 0.5f);
				b.getWorld().playSound(b.getLocation(), Sound.BLAZE_HIT, 2.5f, 0.25f);
			}
		}, 20l);
		Bukkit.getScheduler().scheduleSyncDelayedTask(Main.instance, new Runnable(){
			public void run(){
				p.getLocation().getWorld().playSound(p.getLocation(), Sound.CLICK, 1.0f, 0.5f);
				b.getWorld().playSound(b.getLocation(), Sound.BLAZE_HIT, 2.5f, 0.25f);
			}
		}, 40l);
		Bukkit.getScheduler().scheduleSyncDelayedTask(Main.instance, new Runnable(){
			public void run(){
				p.getLocation().getWorld().playSound(p.getLocation(), Sound.CLICK, 1.0f, 0.5f);
				b.getWorld().playSound(b.getLocation(), Sound.BLAZE_HIT, 2.5f, 0.25f);
			}
		}, 60l);
		Bukkit.getScheduler().scheduleSyncDelayedTask(Main.instance, new Runnable(){
			public void run(){
				if(!p.isDead()){
					p.getLocation().getWorld().playSound(p.getLocation(), Sound.NOTE_BASS, 1.0f, 0.5f);
					ParticleEffect.EXPLOSION_HUGE.display(3f, 3f, 3f, 0.1f, 25, b.getLocation(), 30);
					b.getLocation().getWorld().playSound(b.getLocation(), Sound.EXPLODE, 2.0f, 1.0f);
					for(Player t : MobSystem.getEnemy(p)){
						if(t.getLocation().distance(b.getLocation()) < 13){
							ignore = true;
							t.damage(19-(int)t.getLocation().distance(b.getLocation()), p);
						}
					}
				}
			}
		}, 80l);
	}
	
	public void u_4(final Player p){
		if(Cooldown.Checkcooldown(p, "�ñر�")){
			Cooldown.Setcooldown(p, "�ñر�", 113, true);
			p.sendMessage(Main.MS+"���� �ñر� �ߵ�");
			p.getWorld().playSound(p.getLocation(), Sound.ANVIL_LAND, 2.0f, 0.5f);
			GridEffect particle = new GridEffect(Main.effectManager);
			particle.setEntity(p); 
			particle.period = 5;
			particle.iterations = 20;
			particle.particleCount = 40;
			particle.speed =1;
			particle.start();
			for(int i = 0; i < MobSystem.getEnemy(p).size(); i++){
				final Player t = MobSystem.getEnemy(p).get(i);
				if(t.getLocation().distance(p.getLocation()) <= 8){
					MobSystem.CancelSkill(t, 5);
					t.addPotionEffect(new PotionEffect(PotionEffectType.SLOW, 100, 99));
					t.addPotionEffect(new PotionEffect(PotionEffectType.JUMP, 100, 199));
				}
			}
		}
	}
	
	public void UltimateSkill(final Player p){
		if(Cooldown.Checkcooldown(p, "�ñر�")){
			switch(MobSystem.Getrandom(0, 3)){
			case 0: u_1(p); break;
			case 1: u_2(p); break;
			case 2: u_3(p); break;
			case 3: u_4(p); break;
			//case 4: u_5(p); break;
			
			default: return;
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
			if(!can_Ultimate){ randomer.sendMessage(Main.MS+"�ñر�� �������� ������ ��밡���մϴ�."); return;}
			e.getPlayer().getInventory().setHeldItemSlot(0);
			UltimateSkill(e.getPlayer());
		}
	}
	
	public void onHit(EntityDamageByEntityEvent e){
		e.setDamage(MobSystem.Getrandom(3, 8));
	}
}
