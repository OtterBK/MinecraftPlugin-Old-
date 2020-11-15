package me.Bokum.Zodiac_Battle;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Color;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.Listener;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.inventory.meta.LeatherArmorMeta;
import org.bukkit.plugin.java.JavaPlugin;

public class System extends JavaPlugin implements Listener
{
	public static List<String> godlist = new ArrayList();
	public static List<String> bindlist = new ArrayList<String>();
	public static List<String> breaklist = new ArrayList<String>();
	public static List<Player> redlist = new ArrayList<Player>();
	public static List<Player> bluelist = new ArrayList<Player>();
	public static List<Player> list = new ArrayList<Player>();
	public static boolean checkstart = false;
	public static boolean lobbystart = false;
	
	public static void SetSkillItem(Player p)
	{
		p.getInventory().clear();
		ItemStack item = new ItemStack(Material.STICK, 1);
		ItemMeta meta = item.getItemMeta();
		meta.setDisplayName("§e§l주스킬");
		item.setItemMeta(meta);
		p.getInventory().setItem(0, item);
		meta.setDisplayName("§e§l보조스킬");
		item.setItemMeta(meta);
		p.getInventory().setItem(1, item);
		meta.setDisplayName("§e§l생존스킬");
		item.setItemMeta(meta);
		p.getInventory().setItem(2, item);
		meta.setDisplayName("§e§l궁극기");
		item.setItemMeta(meta);
		p.getInventory().setItem(3, item);
		p.sendMessage(Main.MS+"스킬 사용에 필요한 아이템을 받았습니다.");
	}
	
	  public static void Joingame(Player player)
	  {
	    if (list.contains(player))
	    {
	      player.sendMessage(Main.MS + "이미 게임에 참가중 입니다.");
	      return;
	    }
	    if (checkstart)
	    {
	      player.sendMessage(Main.MS + "이미 게임이 시작되었습니다.");
	      return;
	    }
	    if (list.size() >= 4)
	    {
	      player.sendMessage(Main.MS + "이미 최대인원인 4명의 플레이어가 참가중입니다. - 베타버젼이라 양해바랍니다.");
	      return;
	    }
	    player.teleport(Main.joinpos);
	    list.add(player);
	    SendMessage(Main.MS + ChatColor.AQUA + player.getName() + ChatColor.GRAY + " 님이 참여했습니다. " + ChatColor.RESET + "[ " + ChatColor.GREEN + System.list.size() + " / 5 " + ChatColor.RESET + "]");
	    if ((System.list.size() >= 4) && (!lobbystart))
	    {
	      lobbystart = true;
	      Main.Startgame_beta();
	    }
	  }
	
	  public static void SendMessage(String str)
	  {
	    for (int i = 0; i < list.size(); i++)
	    {
	      ((Player)list.get(i)).sendMessage(str);
	    }
	  }
	  
	public static boolean CheckHandItem(Player p, String str)
	{
		if(p.getInventory().getItemInHand().getItemMeta() == null || !p.getInventory().getItemInHand().getItemMeta().hasDisplayName())
		{
			return false;
		}
		return p.getInventory().getItemInHand().getItemMeta().getDisplayName().equalsIgnoreCase("§e§l"+str);
	}
	
	public static void Helpmessages(Player player)
	{
		
	}
	
	public static List<Player> GetEnemies(Player p)
	{
		if(redlist.contains(p.getName()))
		{
			return bluelist;
		}
		return redlist;
	}
	
	public static void ForceEnd()
	{
		for(Player p : list)
		{
			p.teleport(Main.Lobby);
		}
		Bukkit.broadcastMessage(Main.MS+"십이지신 전쟁 게임이 강제종료 되었습니다!");
	}
	
	public static void Clear()
	{
		godlist.clear();
		bindlist.clear();
		breaklist.clear();
		redlist.clear();
		bluelist.clear();
		list.clear();
	}
	
	public static void Death(Player p)
	{
		godlist.remove(p.getName());
		bindlist.remove(p.getName());
		breaklist.remove(p.getName());
		redlist.remove(p);
		bluelist.remove(p);
		list.remove(p);
		Main.ablist.remove(p.getName());
		if(checkstart = true)
		{
			SendMessage(Main.MS+p.getName()+" 님이 사망하셨습니다.");
			SendMessage(ChatColor.RED+"남은 레드팀 : "+redlist.size()+ChatColor.AQUA+" 남은 블루팀 : "+bluelist.size());
			if(redlist.size() <= 0)
			{
				BlueWin();
				return;
			}
			if(bluelist.size() <= 0)
			{
				RedWin();
				return;
			}
		}
	}
	
	public static void BlueWin()
	{
		for(Player p : list)
		{
			p.teleport(Main.Lobby);
		}
		Bukkit.broadcastMessage(Main.MS+"십이지신 전쟁(베타)가  블루팀의 승리로 종료 됐습니다!");
		Clear();
		checkstart = false;
		lobbystart = false;
	}
	
	public static void RedWin()
	{
		for(Player p : list)
		{
			p.teleport(Main.Lobby);
		}
		Bukkit.broadcastMessage(Main.MS+"십이지신 전쟁(베타)가  레드팀의 승리로 종료 됐습니다!");
		Clear();
		checkstart = false;
		lobbystart = false;
	}

	public static void PlusHp(Player p, int hp)
	{
		int health = p.getHealth()+hp;
		if(health > p.getMaxHealth())
		{
			p.setHealth(p.getMaxHealth());
		}
		else
		{
			p.setHealth(health);
		}
	}
	
	public static void SetRedTeam(Player p)
	{
		  System.redlist.add(p);
		  p.teleport(Main.Redstart);
		  p.sendMessage(Main.MS+"당신은 레드팀입니다.");
		  ItemStack item = new ItemStack(Material.LEATHER_HELMET, 1);
		  LeatherArmorMeta meta = (LeatherArmorMeta) item.getItemMeta();
		  meta.setColor(Color.fromRGB(255, 0, 0));
		  item.setItemMeta(meta);
		  p.getInventory().setHelmet(item);
		  item = new ItemStack(Material.LEATHER_CHESTPLATE, 1);
		  meta = (LeatherArmorMeta) item.getItemMeta();
		  meta.setColor(Color.fromRGB(255, 0, 0));
		  item.setItemMeta(meta);
		  p.getInventory().setChestplate(item);
		  item = new ItemStack(Material.LEATHER_LEGGINGS, 1);
		  meta = (LeatherArmorMeta) item.getItemMeta();
		  meta.setColor(Color.fromRGB(255, 0, 0));
		  item.setItemMeta(meta);
		  p.getInventory().setLeggings(item);
		  item = new ItemStack(Material.LEATHER_BOOTS, 1);
		  meta = (LeatherArmorMeta) item.getItemMeta();
		  meta.setColor(Color.fromRGB(255, 0, 0));
		  item.setItemMeta(meta);
		  p.getInventory().setBoots(item);
		  SetSkillItem(p);
		  p.getInventory().addItem(new ItemStack(Material.STONE_SWORD, 1));
		  p.getInventory().addItem(new ItemStack(Material.POTION, 1));
	}
	
	public static void SetBlueTeam(Player p)
	{
		  System.bluelist.add(p);
		  p.teleport(Main.Bluestart);
		  p.sendMessage(Main.MS+"당신은 블루팀입니다.");
		  ItemStack item = new ItemStack(Material.LEATHER_HELMET, 1);
		  LeatherArmorMeta meta = (LeatherArmorMeta) item.getItemMeta();
		  meta.setColor(Color.fromRGB(0, 255, 0));
		  item.setItemMeta(meta);
		  p.getInventory().setHelmet(item);
		  item = new ItemStack(Material.LEATHER_CHESTPLATE, 1);
		  meta = (LeatherArmorMeta) item.getItemMeta();
		  meta.setColor(Color.fromRGB(0, 255, 0));
		  item.setItemMeta(meta);
		  p.getInventory().setChestplate(item);
		  item = new ItemStack(Material.LEATHER_LEGGINGS, 1);
		  meta = (LeatherArmorMeta) item.getItemMeta();
		  meta.setColor(Color.fromRGB(0, 255, 0));
		  item.setItemMeta(meta);
		  p.getInventory().setLeggings(item);
		  item = new ItemStack(Material.LEATHER_BOOTS, 1);
		  meta = (LeatherArmorMeta) item.getItemMeta();
		  meta.setColor(Color.fromRGB(0, 255, 0));
		  item.setItemMeta(meta);
		  p.getInventory().setBoots(item);
		  SetSkillItem(p);
	}
	
	public static void Minusp(Player p, int hp)
	{
		int health = p.getHealth()-hp;
		if(health < 0)
		{
			p.setHealth(0);
		}
		else
		{
			p.setHealth(health);
		}
	}
	
	public static boolean Checktarget(Player t, int range, Player p)
	{
		if(t == null)
		{
			p.sendMessage(Main.MS+"바라보는 방향 "+range+"칸 이내에 적팀이 없습니다.");
			return false;
		}
		return true;
	}
	
	public static boolean Checkcooldown(Player p, String str)
	{
		if(!Main.cooldown.containsKey(p.getName()+str) || Main.cooldown.get(p.getName()+str) <= (int)(java.lang.System.currentTimeMillis()/1000))
		{
			return true;
		}
		p.sendMessage(Main.MS+ChatColor.AQUA+(Main.cooldown.get(p.getName()+str)-(int)(java.lang.System.currentTimeMillis()/1000))
				+ChatColor.RESET+"초 후 스킬을 사용하실 수 있습니다.");
		return false;
	}
	
	public static void Addcooldown(String str, int cooldown)
	{
		Main.cooldown.put(str, (int)(java.lang.System.currentTimeMillis()/1000)+cooldown);
	}

    public static Player Getcorsurplayer(Player p, int range) 
    {
        final Player observer = p;
        
        Location observerPos = observer.getEyeLocation();
        Vector3D observerDir = new Vector3D(observerPos.getDirection());

        Vector3D observerStart = new Vector3D(observerPos);
        Vector3D observerEnd = observerStart.add(observerDir.multiply(range));

        Player hit = null;

        // Get nearby entities
        for (Player target : observer.getWorld().getPlayers()) 
        {
        // Bounding box of the given player
        	Vector3D targetPos = new Vector3D(target.getLocation());
            Vector3D minimum = targetPos.add(-0.5, 0, -0.5);
            Vector3D maximum = targetPos.add(0.5, 1.67, 0.5);

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
}
