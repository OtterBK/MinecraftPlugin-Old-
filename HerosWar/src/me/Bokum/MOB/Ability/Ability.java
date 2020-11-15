package me.Bokum.MOB.Ability;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.entity.EntityRegainHealthEvent;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.event.entity.ProjectileHitEvent;
import org.bukkit.event.entity.ProjectileLaunchEvent;
import org.bukkit.event.player.PlayerItemHeldEvent;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.event.player.PlayerRespawnEvent;
import org.bukkit.inventory.ItemStack;

import me.Bokum.MOB.Main;
import me.Bokum.MOB.Game.Cooldown;
import me.Bokum.MOB.Game.MobSystem;

public class Ability
{
	public final String playerName;
	public final String abilityName;
	public boolean can_passive = false;
	public boolean can_Ultimate = false;
	public int timernum = 0;
	public int timertime = 0;
	public int timernum1 = 0;
	public int timertime1 = 0;
	public int timernum2 = 0;
	public int timertime2 = 0;
	public int corecatchnum = 0;
	public int corecatchtime = 0;
	public int reducedamage = 0;
	public boolean cc = false;
	public boolean ignore = false;
	public Location deathloc = null;
	public boolean check_dead = false;
	public Ability(String playerName, String abilityName)
	{
		this.playerName=playerName;
		this.abilityName=abilityName;
		this.can_passive=false;
		this.can_Ultimate=false;
		this.timernum = 0;
		this.timertime = 0;
		this.timernum1 = 0;
		this.timertime1 = 0;
		this.timernum2 = 0;
		this.timertime2 = 0;
		this.reducedamage = 0;
		this.cc = false;
		this.ignore = false;
		this.deathloc = null;
		this.check_dead = false;
	}
	
	public void description(){}
	
	public void onArrowHit(Player t){}
	
	public void Active(PlayerItemHeldEvent event){}
	
	public void onBlockBreak(BlockBreakEvent e){
		Block b = e.getBlock();
		Player p = e.getPlayer();
		Location loc = new Location(b.getWorld(), b.getLocation().getBlockX(), b.getLocation().getBlockY(), b.getLocation().getBlockZ());
		if(b.getType() == Material.SPONGE){
			if(MobSystem.healarea.containsKey(loc)){
				loc.getWorld().playSound(loc, Sound.DOOR_CLOSE, 2.0f, 0.5f);
				MobSystem.healarea.get(loc).ac.sendMessage(Main.MS+"치유 영역 생성기가 파괴되었습니다.");
				p.sendMessage(Main.MS+MobSystem.healarea.get(loc).ac.getName()+"님의 치유 영역 생성기를 파괴했습니다.");
				MobSystem.healarea.get(loc).healarea = null;
				loc.getBlock().setType(Material.AIR);
				if(loc.getBlock().getRelative(0, 1, 0).getType() == Material.WOOL)
				loc.getBlock().getRelative(0, 1, 0).setType(Material.AIR);
				MobSystem.healarea.remove(loc);
			}
			if(MobSystem.shieldarea.containsKey(loc)){
				loc.getWorld().playSound(loc, Sound.DOOR_CLOSE, 2.0f, 0.5f);
				MobSystem.shieldarea.get(loc).ac.sendMessage(Main.MS+"삭제 영역 생성기가 파괴되었습니다.");
				p.sendMessage(Main.MS+MobSystem.shieldarea.get(loc).ac.getName()+"님의 삭제 영역 생성기를 파괴했습니다.");
				MobSystem.shieldarea.get(loc).shieldarea = null;
				loc.getBlock().setType(Material.AIR);
				if(loc.getBlock().getRelative(0, 1, 0).getType() == Material.WOOL)
				loc.getBlock().getRelative(0, 1, 0).setType(Material.AIR);
				MobSystem.shieldarea.remove(loc);
			}
		}
		e.setCancelled(true);
	}
	
	public void onDeath(PlayerDeathEvent event){
		Player p = event.getEntity();
		if(p.getKiller() != null) p.getKiller().getInventory().addItem(new ItemStack(396, 1));
		deathloc = new Location(p.getWorld(), p.getLocation().getX(), p.getLocation().getY()+1, p.getLocation().getZ());
	}
	
	public void onKill(PlayerDeathEvent event){
	}
	
	public void onRegain(EntityRegainHealthEvent event){}
	
	public void onHit(EntityDamageByEntityEvent event){}
	
	public void onHitDamaged(EntityDamageByEntityEvent event){
		Player p = (Player) event.getEntity();
		for(Player t : MobSystem.getTeam(p)){
			if(t.getLocation().distance(p.getLocation()) <= 8 && MobSystem.ablist.get(t.getName()).abilityName.equalsIgnoreCase("쉴더")){
				if(t.getItemInHand() != null && t.getItemInHand().getType() == Material.IRON_INGOT){
					int amt = event.getDamage();
					int shield = (int) ((t.getExp()/0.005f));
					shield -= amt;
					if(shield < 0){
						event.setDamage(shield*-1);
						t.setExp(0);
					} else {
						t.setExp(shield*0.005f);
						event.setDamage(1);
					}
					return;
				}
			}
		}
		if(reducedamage > 0){
			event.setDamage(event.getDamage()-reducedamage <= 0 ? 1 : event.getDamage()-reducedamage);
		}
	}
	
	public void onBlockPlace(BlockPlaceEvent event) {}
	
	public void onRespawn(PlayerRespawnEvent event) {
		final Player p = event.getPlayer();
		p.setExp(0);
		event.setRespawnLocation(MobSystem.bluelist.contains(p) ? Main.Bluedeath : Main.Reddeath);
		try{
		Bukkit.getScheduler().scheduleSyncDelayedTask(Main.instance, new Runnable(){
			public void run(){
				p.getInventory().setContents(MobSystem.iteminven.get(p.getName()));
				p.getInventory().setArmorContents(MobSystem.armorinven.get(p.getName()));
			}
		}, 1l);
		}catch(Exception e){
			p.getInventory().setContents(MobSystem.backupinven.get(p.getName()));
			p.getInventory().setArmorContents(MobSystem.backuparmor.get(p.getName()));
			p.sendMessage(Main.MS+"인벤토리를 복구하는 중 오류가 발생해 백업을 불러옵니다.");
		}
		check_dead = true;
		p.sendMessage(Main.MS+"10초 후 부활합니다.");
		Bukkit.getScheduler().scheduleSyncDelayedTask(Main.instance, new Runnable(){
			public void run(){
				if(!check_dead || !MobSystem.plist.contains(p)) return;
				p.sendMessage(Main.MS+"5초 후 부활합니다.");
			}
		}, 100l);
		Bukkit.getScheduler().scheduleSyncDelayedTask(Main.instance, new Runnable(){
			public void run(){
				if(!check_dead || !MobSystem.plist.contains(p)) return;
				p.sendMessage(Main.MS+"부활했습니다.");
				check_dead = false;
				p.teleport(MobSystem.bluelist.contains(p) ? Main.Bluespawn : Main.Redspawn);
			}
		}, 200l);
	}
	
	public void Revive(Player p){
		if(deathloc != null){
			p.teleport(deathloc);
			check_dead = false;
		}
	}
	
	public void onMove(PlayerMoveEvent e){
		if(e.getFrom().getBlockX() == e.getTo().getBlockX() && e.getFrom().getBlockY() == e.getTo().getBlockY()
				&& e.getFrom().getBlockZ() == e.getTo().getBlockZ()) return;
		final Player p = e.getPlayer();
		final Location l = new Location(e.getTo().getWorld(), e.getTo().getBlockX(), e.getTo().getBlockY(), e.getTo().getBlockZ());
		if(MobSystem.portalloc.containsKey(l)){
			Location l1 = MobSystem.portalloc.get(l);
			Location l2 = new Location(l1.getWorld(), l1.getBlockX(), l1.getBlockY(), l1.getBlockZ());
			l2.setPitch(p.getLocation().getPitch());
			l2.setYaw(p.getLocation().getYaw());
			p.teleport(l2);
			p.getWorld().playSound(l, Sound.ENDERMAN_TELEPORT, 2.0f, 1.0f);
			p.getWorld().playSound(l2, Sound.ENDERMAN_TELEPORT, 2.0f, 1.0f);
		}
		l.setY(l.getBlockY()-1);
		for(int j = 0; j < 2; j++){
			if(Main.Core[j].equals(l)){
				catchCore(p, j, l);
			}
		}
		if(l.equals(Main.Bluetop)){
			if(MobSystem.bluelist.contains(p)){
				if(Cooldown.Checkcooldown(p, "포탑")){
					Cooldown.Setcooldown(p, "포탑", 10, false);
					p.teleport(Main.Bluespawn);
					p.sendMessage(Main.MS+"포탑에 올라 당근을 받으셨습니다!");
					p.getInventory().addItem(new ItemStack(396, 4));
					if(MobSystem.ablist.get(p.getName()).abilityName.equalsIgnoreCase("트레이서")){
						Cooldown.Setcooldown(p, "보조스킬", 15, true);
					}
				}
			}else{
				p.sendMessage(Main.MS+"적팀 포탑을 올라도 아무것도 받지 않습니다.");
			}
		}else if(l.equals(Main.Redtop)){
			if(MobSystem.redlist.contains(p)){
				if(Cooldown.Checkcooldown(p, "포탑")){
					Cooldown.Setcooldown(p, "포탑", 10, false);
					p.teleport(Main.Redspawn);
					p.sendMessage(Main.MS+"포탑에 올라 당근을 받으셨습니다!");
					p.getInventory().addItem(new ItemStack(396, 4));
					if(MobSystem.ablist.get(p.getName()).abilityName.equalsIgnoreCase("트레이서")){
						Cooldown.Setcooldown(p, "보조스킬", 15, true);
					}
				}
			}else{
				p.sendMessage(Main.MS+"적팀 포탑을 올라도 아무것도 받지 않습니다.");
			}
		}
		if(l.getBlock().getType() == Material.GOLD_BLOCK){
			if(Cooldown.Checkcooldown(p, "치유의샘")){
				Cooldown.Setcooldown(p, "치유의샘", 75, false);
				p.getWorld().playSound(p.getLocation(), Sound.LEVEL_UP, 1.5f, 1.0f);
				p.sendMessage(Main.MS+"치유의  샘을 사용했습니다.");
				MobSystem.Addhp(p, 14);
			}
		}
	}
	
	public void catchCore(final Player p, final int i, final Location l){
		if(!(MobSystem.bluelist.contains(p) ? MobSystem.check_Bluecatch : MobSystem.check_redcatch)
				&& MobSystem.core_team[i].equalsIgnoreCase("중립")){
			p.sendMessage(Main.MS+"이미 중립화 된 점령지입니다. §c점령하시려면 포탑건축가에게 당근을 주세요.");
			return;
		}
		if(MobSystem.core_team[i].equalsIgnoreCase(MobSystem.bluelist.contains(p) ? "블루" : "레드")){
			p.sendMessage(Main.MS+"이미 점령된 점령지입니다.");
			return;
		}
		p.sendMessage(Main.MS+"점령을 시작합니다.");
		corecatchtime = 10;
		Bukkit.getScheduler().scheduleSyncDelayedTask(Main.instance, new Runnable(){
			public void run(){
				Location ploc = new Location(p.getWorld(), p.getLocation().getBlockX(), p.getLocation().getY()-1, p.getLocation().getBlockZ());
				if(corecatchtime == 10 && ploc.equals(Main.Core[i])){
					p.sendMessage(Main.MS+"점령까지 남은시간 : "+corecatchtime--+" 초");
				}
			}
		}, 0l);
		Bukkit.getScheduler().scheduleSyncDelayedTask(Main.instance, new Runnable(){
			public void run(){
				Location ploc = new Location(p.getWorld(), p.getLocation().getBlockX(), p.getLocation().getY()-1, p.getLocation().getBlockZ());
				if(corecatchtime == 9&& ploc.equals(Main.Core[i])){
					p.sendMessage(Main.MS+"점령까지 남은시간 : "+corecatchtime--+" 초");
				}
			}
		}, 20l);
		Bukkit.getScheduler().scheduleSyncDelayedTask(Main.instance, new Runnable(){
			public void run(){
				Location ploc = new Location(p.getWorld(), p.getLocation().getBlockX(), p.getLocation().getY()-1, p.getLocation().getBlockZ());
				if(corecatchtime == 8&& ploc.equals(Main.Core[i])){
					p.sendMessage(Main.MS+"점령까지 남은시간 : "+corecatchtime--+" 초");
				}
			}
		}, 40l);
		Bukkit.getScheduler().scheduleSyncDelayedTask(Main.instance, new Runnable(){
			public void run(){
				Location ploc = new Location(p.getWorld(), p.getLocation().getBlockX(), p.getLocation().getY()-1, p.getLocation().getBlockZ());
				if(corecatchtime == 7&& ploc.equals(Main.Core[i])){
					p.sendMessage(Main.MS+"점령까지 남은시간 : "+corecatchtime--+" 초");
				}
			}
		}, 60l);
		Bukkit.getScheduler().scheduleSyncDelayedTask(Main.instance, new Runnable(){
			public void run(){
				Location ploc = new Location(p.getWorld(), p.getLocation().getBlockX(), p.getLocation().getY()-1, p.getLocation().getBlockZ());
				if(corecatchtime == 6&& ploc.equals(Main.Core[i])){
					p.sendMessage(Main.MS+"점령까지 남은시간 : "+corecatchtime--+" 초");
				}
			}
		}, 80l);
		Bukkit.getScheduler().scheduleSyncDelayedTask(Main.instance, new Runnable(){
			public void run(){
				Location ploc = new Location(p.getWorld(), p.getLocation().getBlockX(), p.getLocation().getY()-1, p.getLocation().getBlockZ());
				if(corecatchtime == 5&& ploc.equals(Main.Core[i])){
					p.sendMessage(Main.MS+"점령까지 남은시간 : "+corecatchtime--+" 초");
				}
			}
		}, 100l);
		Bukkit.getScheduler().scheduleSyncDelayedTask(Main.instance, new Runnable(){
			public void run(){
				Location ploc = new Location(p.getWorld(), p.getLocation().getBlockX(), p.getLocation().getY()-1, p.getLocation().getBlockZ());
				if(corecatchtime == 4&& ploc.equals(Main.Core[i])){
					p.sendMessage(Main.MS+"점령까지 남은시간 : "+corecatchtime--+" 초");
				}
			}
		}, 120l);
		Bukkit.getScheduler().scheduleSyncDelayedTask(Main.instance, new Runnable(){
			public void run(){
				Location ploc = new Location(p.getWorld(), p.getLocation().getBlockX(), p.getLocation().getY()-1, p.getLocation().getBlockZ());
				if(corecatchtime == 3&& ploc.equals(Main.Core[i])){
					p.sendMessage(Main.MS+"점령까지 남은시간 : "+corecatchtime--+" 초");
				}
			}
		}, 140l);
		Bukkit.getScheduler().scheduleSyncDelayedTask(Main.instance, new Runnable(){
			public void run(){
				Location ploc = new Location(p.getWorld(), p.getLocation().getBlockX(), p.getLocation().getY()-1, p.getLocation().getBlockZ());
				if(corecatchtime == 2&& ploc.equals(Main.Core[i])){
					p.sendMessage(Main.MS+"점령까지 남은시간 : "+corecatchtime--+" 초");
				}
			}
		}, 160l);
		Bukkit.getScheduler().scheduleSyncDelayedTask(Main.instance, new Runnable(){
			public void run(){
				Location ploc = new Location(p.getWorld(), p.getLocation().getBlockX(), p.getLocation().getY()-1, p.getLocation().getBlockZ());
				if(corecatchtime == 1&& ploc.equals(Main.Core[i])){
					p.sendMessage(Main.MS+"점령까지 남은시간 : "+corecatchtime--+" 초");
				}
			}
		}, 180l);
		Bukkit.getScheduler().scheduleSyncDelayedTask(Main.instance, new Runnable(){
			public void run(){
				Location ploc = new Location(p.getWorld(), p.getLocation().getBlockX(), p.getLocation().getY()-1, p.getLocation().getBlockZ());
				if(corecatchtime == 0&& ploc.equals(Main.Core[i])){
					if(!(MobSystem.bluelist.contains(p) ? MobSystem.check_Bluecatch : MobSystem.check_redcatch)){
						if(MobSystem.core_team[i].equalsIgnoreCase("중립")){
							p.sendMessage(Main.MS+"이미 중립화 된 점령지입니다.");
						}else{
							MobSystem.core_team[i] = "중립";
							l.getBlock().setType(Material.WOOL);
							MobSystem.calc();
							corecatchtime = 0;
							MobSystem.Sendmessage(Main.MS+"§6§n"+(MobSystem.bluelist.contains(p) ? "블루" : "레드")+"팀§c§n "+p.getName()+" §6§n님이 "+(i+1)+"번째 점령지를 중립화했습니다.");
							if(i == 0){
								for(Location bl : Main.core1blockloc){
									bl.getBlock().setTypeIdAndData(35, (byte) 0, true);
								}
							} else if(i == 1){
								for(Location bl : Main.core2blockloc){
									bl.getBlock().setTypeIdAndData(35, (byte) 0, true);
								}
							}
						}
						return;
					}
					if(MobSystem.core_team[i].equalsIgnoreCase(MobSystem.bluelist.contains(p) ? "블루" : "레드")){
						p.sendMessage(Main.MS+"이미 점령된 점령지입니다.");
					}else{
						MobSystem.core_team[i] = MobSystem.bluelist.contains(p) ? "블루" : "레드";
						l.getBlock().setTypeIdAndData(35, (byte) (MobSystem.bluelist.contains(p) ? 11 : 14), true);
						MobSystem.Sendmessage(Main.MS+"§6§n"+MobSystem.core_team[i]+"팀§c§n "+p.getName()+" §6§n님이 "+(i+1)+"번째 점령지를 점령했습니다.");
						MobSystem.calc();
						corecatchtime = 0;
						if(i == 0){
							if(MobSystem.bluelist.contains(p)){
								for(Location bl : Main.core1blockloc){
									bl.getBlock().setTypeIdAndData(35, (byte) 11, true);
								}
							}else{
								for(Location bl : Main.core1blockloc){
									bl.getBlock().setTypeIdAndData(35, (byte) 14, true);
								}
							}
						} else if(i == 1){
							if(MobSystem.bluelist.contains(p)){
								for(Location bl : Main.core2blockloc){
									bl.getBlock().setTypeIdAndData(35, (byte) 11, true);
								}
							}else{
								for(Location bl : Main.core2blockloc){
									bl.getBlock().setTypeIdAndData(35, (byte) 14, true);
								}
							}
						}
						p.getInventory().addItem(new ItemStack(396, 1));
					}
				}
			}
		}, 200l);
	}

	public void onLaunch(ProjectileLaunchEvent event){}

	public void onProjectileHit(ProjectileHitEvent event){}
	
	public void onDamaged(EntityDamageEvent e){
	}
	
}
