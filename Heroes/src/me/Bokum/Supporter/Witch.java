package me.Bokum.Supporter;

import org.bukkit.Bukkit;
import org.bukkit.Sound;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.player.PlayerItemHeldEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

import me.Bokum.MOB.Main;
import me.Bokum.MOB.Ability.Ability;
import me.Bokum.MOB.Game.Cooldown;
import me.Bokum.MOB.Game.MobSystem;
import me.Bokum.MOB.Utility.ParticleEffect;

public class Witch extends Ability{

	public Player witch;
	public boolean skill3 = false;
	
	public Witch(String playername, Player p){
		super(playername, "����");
		witch = p;
		ItemStack item = new ItemStack(369, 1);
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
		witch.sendMessage("��6============= ��f[ ��b�Ŀ� ��f] - ��e������ ��7: ��cSupporter ��6=============\n"
				+ "��f- ��a�ֽ�ų ��7: ��d����(��)\n��710ĭ���� �ٶ󺸴� ������ ��1�� 10�ʰ� �ߵ���ŵ�ϴ�."
				+ "��f- ��a������ų ��7: ��d����(����)\n��710ĭ���� �ٶ󺸴� ������ ����1��  10�ʰ� �ߵ���ŵ�ϴ�.\n"
				+ "��f- ��2�ñر��f(��c���Ž� ��밡�ɡ�f) ��7: ��d����(����)\n��710ĭ���� �ٶ󺸴� ������ ������ ���ָ� �̴ϴ�. ��c�ʴ� 3�� ü���� ����ϴ�.\n"
				+ "��f- ��2�нú��f(��c���Ž� ��밡�ɡ�f) ��7: ��d���� ��ȭ\n��7������ ���ӽð� 0.5������.");
	}
	
	  public void PrimarySkill(Player p)
	  {
	    if (Cooldown.Checkcooldown(p, "�ֽ�ų")) {
	      LivingEntity t = MobSystem.Getcorsurplayer(p, 10);
	      if ((t == null)) {
	        p.sendMessage(Main.MS + "10ĭ���� �ٶ󺸴°��� ���� �����ϴ�.");
	        return;
	      }
	      Cooldown.Setcooldown(p, "�ֽ�ų", 30, true);
	      t.getWorld().playSound(t.getLocation(), Sound.GHAST_SCREAM, 1.5F, 1.7F);
	      p.getWorld().playSound(t.getLocation(), Sound.GHAST_CHARGE, 1.5F, 1.7F);
	      ParticleEffect.SPELL_WITCH.display(0.1F, 0.1F, 0.1F, 0.0F, 15, t.getLocation(), 20.0D);
	      t.addPotionEffect(new PotionEffect(PotionEffectType.POISON, this.can_passive ? 300 : 200, 0));
	      p.sendMessage(Main.MS + "���ָ� �ɾ����ϴ�.");
	      ParticleEffect.REDSTONE.display(0.3F, 0.3F, 0.3F, 0.0F, 20, p.getLocation(), 25.0D);
	    }
	  }

	  public void SecondarySkill(Player p) {
	    if (Cooldown.Checkcooldown(p, "������ų")) {
	    	LivingEntity t = MobSystem.Getcorsurplayer(p, 10);
	      if (t == null) {
	        p.sendMessage(Main.MS + "10ĭ���� �ٶ󺸴°��� ���� �����ϴ�.");
	        return;
	      }
	      Cooldown.Setcooldown(p, "������ų", 30, true);
	      t.getWorld().playSound(t.getLocation(), Sound.GHAST_SCREAM, 1.5F, 1.7F);
	      p.getWorld().playSound(t.getLocation(), Sound.GHAST_CHARGE, 1.5F, 1.7F);
	      ParticleEffect.SPELL_WITCH.display(0.1F, 0.1F, 0.1F, 0.0F, 15, t.getLocation(), 20.0D);
	      t.addPotionEffect(new PotionEffect(PotionEffectType.WITHER, this.can_passive ? 300 : 200, 0));
	      p.sendMessage(Main.MS + "���ָ� �ɾ����ϴ�.");
	      ParticleEffect.REDSTONE.display(0.3F, 0.3F, 0.3F, 0.0F, 20, p.getLocation(), 25.0D);
	    }
	  }
	
	public void UltimateSkill(final Player p){
		if(Cooldown.Checkcooldown(p, "�ñر�")){
			final LivingEntity t = MobSystem.Getcorsurplayer(p, 10);
			if(t == null){
				p.sendMessage(Main.MS+"10ĭ���� �ٶ󺸴°��� ���� �����ϴ�.");
				return;
			}
			Cooldown.Setcooldown(p, "�ñر�", 114, true);
			t.getWorld().playSound(t.getLocation(), Sound.ENDERDRAGON_DEATH, 1.0f, 0.2f);
			p.getWorld().playSound(t.getLocation(), Sound.GHAST_CHARGE, 1.5f, 1.7f);
			ParticleEffect.SPELL_WITCH.display(0.1f, 0.1f, 0.1f, 0, 15, t.getLocation(), 20);
			p.sendMessage(Main.MS+"���ָ� �ɾ����ϴ�.");
			timertime = can_passive ? 5 : 8;
			timernum = Bukkit.getScheduler().scheduleSyncRepeatingTask(Main.instance, new Runnable(){
				public void run(){
					if(--timertime > 0){
						t.damage(3, p);
						t.getWorld().playSound(t.getLocation(), Sound.HURT, 1.5f, 1.2f);
						ParticleEffect.REDSTONE.display(0.3f, 0.3f, 0.3f, 0, 20, p.getLocation(), 25);
					} else {
						Bukkit.getScheduler().cancelTask(timernum);
					}
				}
			}, 20l, 20l);
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
			if(!can_Ultimate){ witch.sendMessage(Main.MS+"�ñر�� �������� ������ ��밡���մϴ�."); return;}
			e.getPlayer().getInventory().setHeldItemSlot(0);
			UltimateSkill(e.getPlayer());
		}
	}
	
	public void onHit(EntityDamageByEntityEvent e){
		if(witch.getInventory().getHeldItemSlot() != 0) return;
		e.setDamage(6);
	}
	
}




