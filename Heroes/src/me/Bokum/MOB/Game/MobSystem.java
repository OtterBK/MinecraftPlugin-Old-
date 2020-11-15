package me.Bokum.MOB.Game;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.block.Block;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import me.Bokum.MOB.Main;
import me.Bokum.MOB.Ability.Ability;
import me.Bokum.MOB.Attacker.Assasin;
import me.Bokum.MOB.Attacker.BowMaster;
import me.Bokum.MOB.Attacker.FlameWizard;
import me.Bokum.MOB.Attacker.Ninja;
import me.Bokum.MOB.Attacker.PassiveMaster;
import me.Bokum.MOB.Expert.Bomber;
import me.Bokum.MOB.Expert.Engineer;
import me.Bokum.MOB.Expert.Randomer;
import me.Bokum.MOB.Expert.Tracer;
import me.Bokum.MOB.Tanker.Blocker;
import me.Bokum.MOB.Tanker.Imrapu;
import me.Bokum.MOB.Tanker.Knight;
import me.Bokum.MOB.Tanker.Shielder;
import me.Bokum.MOB.Utility.Vector3D;
import me.Bokum.Supporter.AreaCreator;
import me.Bokum.Supporter.Portal;
import me.Bokum.Supporter.Priest;
import me.Bokum.Supporter.Witch;
import net.milkbowl.vault.economy.EconomyResponse;

public class MobSystem {
	public static boolean join_block = false;
	public static boolean skill_block = false;
	public static List<Player> plist = new ArrayList<Player>();
	public static HashMap<Location, Location> portalloc = new HashMap<Location, Location>();
	public static HashMap<Location, AreaCreator> healarea = new HashMap<Location, AreaCreator>();
	public static HashMap<Location, AreaCreator> shieldarea = new HashMap<Location, AreaCreator>();
	public static HashMap<String, Ability> ablist = new HashMap<String, Ability>(40);
	
	public static void GameQuit(Player p){
		if(!plist.contains(p)){
			return;
		}
		plist.remove(p);
		if(ablist.containsKey(p.getName())){
			Ability ab = ablist.get(p.getName());
	    	RemoveAbility(p);
		}
	}
	
	public static void Cleardata(){
		Bukkit.getScheduler().cancelTasks(Main.instance);
		for(Location l : portalloc.keySet()){
			l.getBlock().setType(Material.AIR);
			l.getBlock().getRelative(0, -1, 0).setType(Material.AIR);
		}
		portalloc.clear();
		for(Location l : healarea.keySet()){
			l.getBlock().setType(Material.AIR);
			l.getBlock().getRelative(0, 1, 0).setType(Material.AIR);
		}
		healarea.clear();
		for(Location l : shieldarea.keySet()){
			l.getBlock().setType(Material.AIR);
			l.getBlock().getRelative(0, 1, 0).setType(Material.AIR);
		}
		shieldarea.clear();
		ablist.clear();
	}
	
	public static void ForceStop(){
		Cleardata();
	}
	
	public static void Sendmessage(String str)
	{
		for(Player p : plist)
		{
			p.sendMessage(str);
		}
	}
	
	  public static void Addhp(LivingEntity p, int amt){
		  if(p.isDead()) return;
		  p.setHealth(p.getHealth()+amt > p.getMaxHealth() ? p.getMaxHealth() : p.getHealth() + amt);
	  }
	  
	  public static void Minushp(LivingEntity p, int amt){
		  p.setHealth(p.getHealth()-amt < 0 ? 0 : p.getHealth() - amt);
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
	  
	  public static void RemoveAbility(Player p){
		  if(!MobSystem.ablist.containsKey(p.getName())) return;
		  Ability ability = MobSystem.ablist.get(p.getName());
		  Bukkit.getScheduler().cancelTask(ability.timernum);
		  Bukkit.getScheduler().cancelTask(ability.timernum1);
		  Bukkit.getScheduler().cancelTask(ability.timernum2);
		  try{
			  if(ability.abilityName.equalsIgnoreCase("포탈")){
				  Portal por = (Portal) ability;
				  if(por.blueportal.getBlock().getType() == Material.PORTAL)
				  por.blueportal.getBlock().setType(Material.AIR);
				  if(por.blueportal.getBlock().getRelative(0, -1, 0).getType() == Material.WOOL)
					  por.blueportal.getBlock().getRelative(0, -1, 0).setType(Material.AIR);
				  if(por.redportal.getBlock().getType() == Material.PORTAL)
				  por.redportal.getBlock().setType(Material.AIR);
				  if(por.redportal.getBlock().getRelative(0, -1, 0).getType() == Material.WOOL)
					  por.redportal.getBlock().getRelative(0, -1, 0).setType(Material.AIR);
				  portalloc.remove(por.blueportal);
				  portalloc.remove(por.redportal);
			  }
			  if(ability.abilityName.equalsIgnoreCase("영역 생성자")){
				  AreaCreator acr = (AreaCreator) ability;
				  if(acr.healarea != null && acr.healarea.getBlock().getType() == Material.SPONGE)
				  acr.healarea.getBlock().setType(Material.AIR);
				  if(acr.healarea.getBlock().getRelative(0, 1, 0).getType() == Material.WOOL)
					  acr.healarea.getBlock().getRelative(0, 1, 0).setType(Material.AIR);
				  if(acr.shieldarea != null && acr.shieldarea.getBlock().getType() == Material.SPONGE)
				  acr.shieldarea.getBlock().setType(Material.AIR);
				  if(acr.shieldarea.getBlock().getRelative(0, 1, 0).getType() == Material.WOOL)
					  acr.shieldarea.getBlock().getRelative(0, 1, 0).setType(Material.AIR);
				  healarea.remove(acr.healarea);
				  shieldarea.remove(acr.shieldarea);
			  }
			  if(ability.abilityName.equalsIgnoreCase("붐버")){
				  Bomber bb = (Bomber) ability;
				  for(Location l : bb.bomblist){
					  if(l.getBlock().getType() == Material.WOOD_PLATE)
					  l.getBlock().setType(Material.AIR);
				  }
				  bb.bomblist.clear();
			  }
			  if(ability.abilityName.equalsIgnoreCase("암살자")){
				  for(Player t : Bukkit.getOnlinePlayers()){
					  t.showPlayer(p);
				  }
			  }
		  }catch(Exception e){
			  
		  }
		  ablist.remove(p.getName());
	  }
	

	    public static LivingEntity Getcorsurplayer(Player p, int range) 
	    {
	        final Player observer = p;
	        
	        Location observerPos1 = observer.getEyeLocation();
	        Vector3D observerDir1 = new Vector3D(observerPos1.getDirection());
	        Vector3D observerStart1 = new Vector3D(observerPos1);
	        
	        Location loc = p.getLocation();
	        
	        for(int i = 1; i <= 10; i++){
	            Vector3D observerEnd1 = observerStart1.add(observerDir1.multiply(i));
	            loc = new Location(p.getWorld(), observerEnd1.x, observerEnd1.y, observerEnd1.z);
	        	if(loc.getBlock().getType() != Material.AIR){
	        		if(i == 1){
	        			range = i;
	        		}
	    			break;
	        	}
	        }
	        
	        Location observerPos = observer.getEyeLocation();
	        Vector3D observerDir = new Vector3D(observerPos.getDirection());

	        Vector3D observerStart = new Vector3D(observerPos);
	        Vector3D observerEnd = observerStart.add(observerDir.multiply(range));

	        LivingEntity hit = null;  

	        // Get nearby entities
	        for (LivingEntity target : observer.getWorld().getLivingEntities()) 
	        {
	        // Bounding box of the given player
	        	Vector3D targetPos = new Vector3D(target.getLocation());
	            Vector3D minimum = targetPos.add(-0.8, 0, -0.8);
	            Vector3D maximum = targetPos.add(0.8, 2, 0.8);

	            if (target != observer && hasIntersection(observerStart, observerEnd, minimum, maximum)) 
	            {
	            	if (hit == null || hit.getLocation().distanceSquared(observerPos) > target.getLocation().distanceSquared(observerPos)) 
	            	{    
	            		hit = target;
	            	}
	            }
	        }
	                
	        // Hit the closest player
	        if (hit != null) 
	        {
	        	return hit;
	        }
	        return null;
	        
	        
	    }
	    
	     // Source:
	    // [url]http://www.gamedev.net/topic/338987-aabb---line-segment-intersection-test/[/url]
	    private static boolean hasIntersection(Vector3D p1, Vector3D p2, Vector3D min, Vector3D max) {
	        final double epsilon = 0.0001f;
	         
	        Vector3D d = p2.subtract(p1).multiply(0.5);
	        Vector3D e = max.subtract(min).multiply(0.5);
	        Vector3D c = p1.add(d).subtract(min.add(max).multiply(0.5));
	        Vector3D ad = d.abs();
	         
	        if (Math.abs(c.x) > e.x + ad.x)
	            return false;
	        if (Math.abs(c.y) > e.y + ad.y)
	            return false;
	        if (Math.abs(c.z) > e.z + ad.z)
	            return false;
	         
	        if (Math.abs(d.y * c.z - d.z * c.y) > e.y * ad.z + e.z * ad.y + epsilon)
	            return false;
	        if (Math.abs(d.z * c.x - d.x * c.z) > e.z * ad.x + e.x * ad.z + epsilon)
	            return false;
	        if (Math.abs(d.x * c.y - d.y * c.x) > e.x * ad.y + e.y * ad.x + epsilon)
	            return false;
	         
	        return true;
	    }
	    
		public static int Getrandom(int min, int max){
			  return (int)(Math.random() * (max-min+1)+min);
			}
		
		public static void CancelSkill(final Player p, int time){
			if(!MobSystem.ablist.containsKey(p.getName())) return;
			final Ability ability = MobSystem.ablist.get(p.getName());
			p.sendMessage(Main.MS+"침묵 상태가 되었습니다! §c"+time+"§f초 간 스킬을 사용하실 수 없습니다!");
			ability.cc = true;
			Bukkit.getScheduler().scheduleSyncDelayedTask(Main.instance, new Runnable(){
				public void run(){
					p.sendMessage(Main.MS+"침묵 상태가 해제 되었습니다.");
					ability.cc = false;
				}
			}, (long)time*20);
		}
}
