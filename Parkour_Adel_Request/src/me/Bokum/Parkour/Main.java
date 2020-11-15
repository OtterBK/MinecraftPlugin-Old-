package me.Bokum.Parkour;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.GameMode;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.block.Block;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.event.player.PlayerRespawnEvent;
import org.bukkit.event.player.PlayerToggleSneakEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.util.Vector;

public class Main extends JavaPlugin implements Listener
{
	public static String MS = "§f[§a파쿠르§f] ";
	public static Main instance;
	public static List<String> plist = new ArrayList<String>();
	public static List<String> jumpinglist = new ArrayList<String>();
	public static HashMap<String, Integer> climbinglist = new HashMap<String, Integer>();
	
	public void onEnable()
	{
		instance = this;
		getServer().getPluginManager().registerEvents(this, this);
		getLogger().info("[보끔] 파쿠르 플러그인이 로드 되었습니다.");
		
		Timer();
	}
    
	public void onDisable()
	{
		getLogger().info("[보끔] 파쿠르 플러그인이 언로드 되었습니다.");
	}

	public boolean onCommand(CommandSender talker, Command command, String str, String args[])
	{
		if(talker instanceof Player)
		{
			Player p = (Player) talker;
			if(str.equalsIgnoreCase("pkr"))
			{
				if(!p.hasPermission("파쿠르")){
					p.sendMessage(MS+"권한이 없습니다.");
				}
				if(args.length <= 0)
				{
					Helpmessages(p);
					return true;
				}
				else
				{
					if(args[0].equalsIgnoreCase("join"))
					{
						if(args.length > 1){
							TrainingJoin(p, args[1]);
						} else {
							p.sendMessage(MS+"닉네임을 입력해주세요.");
						}
						return true;
					}
					if(args[0].equalsIgnoreCase("quit"))
					{
						if(args.length > 1){
							GameQuit(p, args[1]);
						} else {
							p.sendMessage(MS+"닉네임을 입력해주세요.");
						}
						return true;
					}
				}
			}
		}
		return false;
	}
	
	public void GameQuit(Player p, String str){
		if(!plist.contains(p.getName())){
			p.sendMessage(MS+"해당 플레이어는 이미 파쿠르를 사용하고 있지 않습니다.");
			return;
		}
		p.sendMessage(MS+str+"님은 이제 파쿠르를 할 수 없습니다.");
		plist.remove(str);
	}
	
	public void Helpmessages(Player p)
	{
		p.sendMessage(MS+"/pkr join <닉네임>");
		p.sendMessage(MS+"/pkr quit <닉네임>");
	}
	
	public static void TrainingJoin(Player p, String str){
		if(plist.contains(p.getName())){
			p.sendMessage(MS+"해당 플레이어는 이미 파쿠르를 사용하고 있습니다.");
			return;
		}
		p.sendMessage(MS+str+"님은 이제 파쿠르를 할 수 있습니다.");
		plist.add(str);
	}

		public static int Getrandom(int min, int max){
		  return (int)(Math.random() * (max-min+1)+min);
		}

		public void Timer(){
			Bukkit.getScheduler().scheduleSyncRepeatingTask(instance, new Runnable(){
				public void run(){
					for(String s : plist){
						Player p = getServer().getPlayer(s);
						if(p == null) return;
						if(p.getExp() != 1){
							if(!climbinglist.containsKey(p.getName())){
								if(!p.isSprinting()){
									p.setExp(p.getExp()+0.02f > 1 ? 1 : p.getExp()+0.075f);
								} else {
									p.setExp(p.getExp()+0.02f > 1 ? 1 : p.getExp()+0.05f);
								}
							} else {
								p.setExp(p.getExp()+0.02f > 1 ? 1 : p.getExp()+0.05f);
							}
						}
					}
				}
			}, 20L, 30l);
		}
				
		public boolean Usestamina(Player p, int amt){
			float minus = (float) amt / 100;
			if(p.getExp() - minus < 0){
				p.sendMessage(MS+"스테미나가 부족합니다.");
				return true;
			}
			p.setExp(p.getExp()-minus);
			return false;
		}
		
		
		@EventHandler
		public void onPlayerMove(PlayerMoveEvent e){
			Player p = e.getPlayer();
			if(!plist.contains(p.getName())) return;
				if(p.getGameMode() == GameMode.CREATIVE) return;
				/*if(p.getLocation().getY() < 0){
					p.damage(100);
					return;
				} y좌표가 0이하면 사망*/
				if(e.getFrom().getX() == e.getTo().getX() && e.getFrom().getY() == e.getTo().getY()
						&& e.getFrom().getZ() == e.getTo().getZ()) return;
				Material reb = p.getLocation().getBlock().getRelative(0, -1, 0).getType();
				if(reb == Material.PISTON_STICKY_BASE && p.getExp() < 1){
					p.setExp(1); p.sendMessage(MS+"스테미나가 회복 되었습니다.");
				}
				if(reb == Material.SPONGE){
				      p.setVelocity(p.getLocation().getDirection().multiply(0.8D).setY(0.8D));
				      p.getWorld().playSound(p.getLocation(), Sound.BAT_TAKEOFF, 2.0f, 0.8f);
				}
				if(climbinglist.containsKey(p.getName())){
					e.setCancelled(true);
				}
				if(reb != Material.AIR){
					if(jumpinglist.contains(p.getName()))
					jumpinglist.remove(p.getName());
				}
				Block tb = null;
				try{
					tb = p.getTargetBlock(null, 1);
				}catch(Exception exception){
					return;
				}
				if(tb == null){
					return;
				}
				double dix_x = Math.abs(p.getLocation().getX() - tb.getLocation().getBlockX());
				double dix_z = Math.abs(p.getLocation().getZ() - tb.getLocation().getBlockZ());
				if((tb.getTypeId() == 85 || tb.getTypeId() == 113 || tb.getTypeId() == 139) &&
						(dix_x <= 0.5 || dix_z <= 0.5)){
					p.getWorld().playSound(p.getLocation(), Sound.DIG_SNOW, 3.0f, 0.7f);
					p.setAllowFlight(true);
					p.setFlying(true);
				} else {
					p.setAllowFlight(false);
					p.setFlying(false);
				}
			    if (e.getTo().getBlockY() > e.getFrom().getBlockY()) {
					if(p.isSneaking()){
						if((!jumpinglist.contains(p.getName())
								&& reb != Material.AIR) || p.getLocation().getBlock().getType() == Material.STEP){
							if(Usestamina(p, 10)) return;
							jumpinglist.add(p.getName());
							p.setVelocity(new Vector(0,0.77f,0));
							p.getWorld().playSound(p.getLocation(), Sound.MAGMACUBE_JUMP, 3.0f, 0.8f);
						}
					}
			    }
		}
		
		@EventHandler
		public void onSneak(PlayerToggleSneakEvent e){
			if(!plist.contains(e.getPlayer().getName())) return;
			Player p = e.getPlayer();
			if(p.isSneaking()) return;
			if(p.getGameMode() == GameMode.CREATIVE) return;
			Block tb = null;
			try{
				tb = p.getTargetBlock(null, 1);
			}catch(Exception exception){
				return;
			}
			if(tb == null){
				return;
			}
			 if(climbinglist.containsKey(p.getName())){
					p.playSound(p.getLocation(), Sound.FALL_BIG, 3.0f, 1.6f);
					switch(climbinglist.get(p.getName())){
					case 1: p.setVelocity(new Vector(0,0.35f,0)); p.setVelocity(p.getLocation().getDirection().multiply(0.25D).setY(0.65D)); break;
					case 2: p.setVelocity(new Vector(0,0.50f,0)); p.setVelocity(p.getLocation().getDirection().multiply(0.25D).setY(0.76D));break;
					case 3: p.setVelocity(new Vector(0,0.65f,0)); p.setVelocity(p.getLocation().getDirection().multiply(0.25D).setY(0.87D));break;
					case 4: p.setVelocity(new Vector(0,0.77f,0)); p.setVelocity(p.getLocation().getDirection().multiply(0.25D).setY(0.96D));break;
					case 5: p.setVelocity(new Vector(0,0.77f,0)); p.setVelocity(p.getLocation().getDirection().multiply(0.25D).setY(1.05D));break;
					
					default: p.setVelocity(new Vector(0,0.77f,0)); p.setVelocity(p.getLocation().getDirection().multiply(0.2D).setY(1.05D));break;
					}
					climbinglist.remove(p.getName());	
					return;
			 }
			/*double dix_x = Math.abs(p.getLocation().getX() - tb.getLocation().getBlockX());
			double dix_z = Math.abs(p.getLocation().getZ() - tb.getLocation().getBlockZ());*/
			 if(p.getLocation().getBlock().getRelative(0, -1, 0).getType() == Material.AIR
					 && p.getLocation().getBlock().getType() != Material.STEP
					&& tb.getType() != Material.AIR && !climbinglist.containsKey(p.getName())){
					if(Usestamina(p, 5)) return;
				    p.setVelocity(p.getLocation().getDirection().multiply(-0.65D).setY(0.65D));
					p.getWorld().playSound(p.getLocation(), Sound.DIG_STONE, 3.0f, 0.3f);
					return;
				}
		}
		
		@EventHandler
		public void onRightClick(PlayerInteractEvent e)
		{
			if(!plist.contains(e.getPlayer().getName())) return;
			Player p = e.getPlayer();
			if((e.getAction() == Action.LEFT_CLICK_AIR || e.getAction() == Action.LEFT_CLICK_BLOCK)){
				if(climbinglist.containsKey(e.getPlayer().getName())){
					climbinglist.remove(e.getPlayer().getName());
					e.getPlayer().sendMessage(MS+"손을 놨습니다.");
					e.getPlayer().setAllowFlight(false);
					e.getPlayer().setFlying(false);
					return;
				}else if(e.getClickedBlock() != null && p.isSprinting()){
					Block bl = e.getClickedBlock();
					if(bl.getLocation().getY() == (p.getLocation().getY()+1) && (bl.getRelative(0, 1, 0).getType() == Material.AIR 
							|| bl.getRelative(0, 1, 0).getType() == Material.RAILS)){
						if(Usestamina(p, 5)) return;
						p.setVelocity(p.getLocation().getDirection().multiply(0.55D).setY(0.59D));
						p.playSound(p.getLocation(), Sound.LAVA_POP, 2.0f, 0.5f);	
					}
				}
			}
			if(e.getAction() == Action.RIGHT_CLICK_BLOCK || e.getAction() == Action.RIGHT_CLICK_AIR)
			{
				if(e.getItem() != null && e.getItem().getTypeId() == 370){
					Removeitem(p, 370, 1);
					p.setExp(1); p.sendMessage(MS+"스테미나가 회복 되었습니다.");
				}
				if(p.getGameMode() == GameMode.CREATIVE) return;
				if(e.getClickedBlock() != null && p.getLocation().getBlock().getRelative(0, -1, 0).getType() == Material.AIR
						&& e.getClickedBlock().getLocation().getBlockY() > p.getLocation().getY() 
						&& (e.getClickedBlock().getRelative(0, 1, 0).getType() == Material.AIR 
						|| e.getClickedBlock().getRelative(0, 1, 0).getType() == Material.THIN_GLASS 
						|| e.getClickedBlock().getRelative(0, 1, 0).getType() == Material.IRON_FENCE
						|| e.getClickedBlock().getRelative(0, 1, 0).getType() == Material.FENCE
						|| e.getClickedBlock().getRelative(0, 1, 0).getType() == Material.NETHER_FENCE
						|| e.getClickedBlock().getRelative(0, 1, 0).getType() == Material.COBBLE_WALL
						|| e.getClickedBlock().getRelative(0, 1, 0).getType() == Material.RAILS)){
					Block bl = e.getClickedBlock();
					double dis_y = Math.abs(bl.getLocation().getY() - p.getLocation().getY());
					if(dis_y <= 4
							&& (Math.abs(bl.getLocation().getBlockX()-p.getLocation().getX()) <= 3
							&& Math.abs(bl.getLocation().getBlockZ()-p.getLocation().getZ()) <= 3)){
						if(Usestamina(p, 25)) return;
						p.setAllowFlight(true);
						p.setFlying(true);
						p.teleport(p.getLocation());
						climbinglist.put(p.getName(), (int) Math.round(dis_y)+1);
						p.sendMessage(MS+"매달리셨습니다. - 좌클릭 : 해제 , 쉬프트 : 오르기");
					}
				}
			}
		}
		
		public static void Removeitem(Player p, int id, int amt){
	  		for(int i = 0; i < p.getInventory().getSize(); i++){
	  			if(amt > 0){
	  				ItemStack pitem = p.getInventory().getItem(i);
	  				if(pitem != null && pitem.getTypeId() == id){
	  					if(pitem.getAmount() >= amt){
	  						int itemamt = pitem.getAmount()-amt;
	  						pitem.setAmount(itemamt);
	  						p.getInventory().setItem(i, amt > 0 ? pitem : null);
	  					    p.updateInventory();
	  						return;
	  					}
	  					else{
	  						amt -= pitem.getAmount();
	  						p.getInventory().setItem(i, null);
	  					    p.updateInventory();
	  					} 
	  				}
	  			}
	  			else{
	  				return;
	  			}
	  		}
	    }
		
		@EventHandler
		public void onPlayerDamage(EntityDamageEvent e){
			if(!(e.getEntity() instanceof Player)) return;
			final Player p = (Player) e.getEntity();
			if(!plist.contains(p)) return;
			if ((e.getCause() == EntityDamageEvent.DamageCause.FALL)){
				e.setCancelled(true);
				if(!p.isSneaking() || e.getDamage() >= 40){
					if(e.getDamage() <= 19 || p.getLocation().getBlock().getRelative(0, 1, 0).getType() == Material.SPONGE) return;
							p.damage(1);
							p.getWorld().playSound(p.getLocation(), Sound.ITEM_BREAK, 2.0f, 2.0f);
							p.sendMessage(MS+"다리를 다쳤습니다!");
							p.addPotionEffect(new PotionEffect(PotionEffectType.JUMP, 60, 199));
				} else{
							p.getWorld().playSound(p.getLocation(), Sound.BAT_TAKEOFF, 2.0f, 3.0f);
				}
				return;
			}
		}
	}
