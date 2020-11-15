package me.Bokum.EWG;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Effect;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.World;
import org.bukkit.block.Block;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Arrow;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.entity.EntityDamageEvent.DamageCause;
import org.bukkit.event.entity.EntityRegainHealthEvent;
import org.bukkit.event.entity.FoodLevelChangeEvent;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.event.entity.ProjectileLaunchEvent;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.player.PlayerBucketEmptyEvent;
import org.bukkit.event.player.PlayerChatEvent;
import org.bukkit.event.player.PlayerCommandPreprocessEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.event.player.PlayerRespawnEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.ShapedRecipe;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.plugin.RegisteredServiceProvider;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.util.Vector;

import net.milkbowl.vault.economy.Economy;
import net.milkbowl.vault.economy.EconomyResponse;

public class Main extends JavaPlugin implements Listener
{
	public static List<Player> plist = new ArrayList<Player>();
	public static boolean check_start = false;
	public static boolean check_lobbystart = false;
	public static int tasknum[] = new int[30];
	public static int tasktime[] = new int[30];
	public static Location Lobby;
	public static List<String> teamchat = new ArrayList<String>();
	public static Location Red_spawn;
	public static Location Blue_spawn;
	public static Location Red_core;
	public static Location Blue_core;
	public static Location specLoc;
	public static List<Player> redlist = new ArrayList<Player>();
	public static List<Player> bluelist = new ArrayList<Player>();
	public static Location joinpos;
	public static String MS = "��f[ ��aEG ��f] ";
	public static Main instance;
	public static ItemStack[] hades_inventory;
	public static ItemStack[] hades_armor;
	public static Player atena = null;
	public static HashMap<String, String> jlist = new HashMap<String, String>();
	public static Economy econ = null;
	public static ItemStack helpitem;
	public static boolean game_end = false;
	public static Inventory gamehelper;
	public static Location tntlocation = null;
	public static int timertime = 0;
	public static int timernum = 0;
	public static HashMap<String, Integer> cooldown = new HashMap<String, Integer>(80);
	public static HashMap<String, String> abilitytarget = new HashMap<String, String>();
	public static int Forcestoptimer = 0;
	public static HashMap<String, String> backuptlist = new HashMap<String, String>();
	
	public void onEnable(){
        ShapedRecipe recipe = new ShapedRecipe(new ItemStack(Material.BLAZE_ROD)).shape(new String[]{"|","|","|"}).setIngredient('|', Material.STICK);
		getServer().addRecipe(recipe);
		getServer().getPluginManager().registerEvents(this, this);
		for(int i = 0; i < tasknum.length; i++){
			tasknum[i] = -5;
			tasktime[i] = -5;
		}
		
		instance = this;
		
		getLogger().info("EG �ŵ��� ���� �÷������� �ε� �Ǿ����ϴ�.");
		
        if (!setupEconomy() ) {
            getLogger().info("[���� �߻� ��� ] Vault�÷������� �νĵ��� �ʾҽ��ϴ�!");
        }
        
        ItemStack blazestick = new ItemStack(Material.BLAZE_ROD, 1);        
        
	gamehelper = Bukkit.createInventory(null, 27, "��c��l�����");
		
		ItemStack item = new ItemStack(34, 1);
		ItemMeta meta2 = item.getItemMeta();
		meta2.setDisplayName("��6���");
		item.setItemMeta(meta2);
		for(int i = 0; i <= 9; i++){
			gamehelper.setItem(i, item);
		}
		for(int i = 17; i < 27; i++){
			gamehelper.setItem(i, item);
		}
		
		item = new ItemStack(Material.BOOK, 1);
		meta2 = item.getItemMeta();
		meta2.setDisplayName("��e�÷��� ���");
		item.setItemMeta(meta2);
		gamehelper.setItem(11, item);
		
		item = new ItemStack(Material.BOOK, 1);
		meta2 = item.getItemMeta();
		meta2.setDisplayName("��e���� ��Ģ");
		item.setItemMeta(meta2);
		gamehelper.setItem(13, item);
		
		item = new ItemStack(Material.BOOK, 1);
		meta2 = item.getItemMeta();
		meta2.setDisplayName("��e��ä�� on/off");
		List<String> lorelist1 = new ArrayList<String>();
		lorelist1.add("��f- ��c������ ���۵� �� ����� �� �ֽ��ϴ�.");
		item.setItemMeta(meta2);
		gamehelper.setItem(15, item);
		
		helpitem = new ItemStack(345, 1);
		meta2 = helpitem.getItemMeta();
		meta2.setDisplayName("��f[ ��6���� ����� ��f]");
		lorelist1.clear();
		lorelist1.add("��f- ��7��Ŭ���� ���� ����� ���ϴ�.");
		meta2.setLore(lorelist1);
		helpitem.setItemMeta(meta2);
		
		Loadconfig();
	}
	
    private boolean setupEconomy() {
        if (getServer().getPluginManager().getPlugin("Vault") == null) {
            return false;
        }
        RegisteredServiceProvider<Economy> rsp = getServer().getServicesManager().getRegistration(Economy.class);
        if (rsp == null) {
            return false;
        }
        econ = rsp.getProvider();
        return econ != null;
    }
	
	public void Loadconfig()
	{
	  try
	  {
	    joinpos = new Location(Bukkit.getWorld(getConfig().getString("Join_world")), getConfig().getInt("Join_x"), getConfig().getInt("Join_y"), getConfig().getInt("Join_z"));
	    Lobby = new Location(Bukkit.getWorld(getConfig().getString("Lobby_world")), getConfig().getInt("Lobby_x"), getConfig().getInt("Lobby_y"), getConfig().getInt("Lobby_z"));
	    specLoc = new Location(Bukkit.getWorld(getConfig().getString("Spec_world")), getConfig().getInt("Spec_x"), getConfig().getInt("Spec_y"), getConfig().getInt("Spec_z"));
	  }
	  catch (IllegalArgumentException e)
	  {
	    getLogger().info("���� ���� �Ǵ� �κ� �����Ǿ� ���� �ʽ��ϴ�.");
	  }
	  try
	  {
	    Red_spawn = new Location(Bukkit.getWorld(getConfig().getString("Red_world")), getConfig().getInt("Red_x"), getConfig().getInt("Red_y"), getConfig().getInt("Red_z"));
	    Blue_spawn = new Location(Bukkit.getWorld(getConfig().getString("Blue_world")), getConfig().getInt("Blue_x"), getConfig().getInt("Blue_y"), getConfig().getInt("Blue_z"));
	  }
	  catch (IllegalArgumentException e)
	  {
	    getLogger().info("������ ���� �Ǵ� ����� ������ �����Ǿ� ���� �ʽ��ϴ�.");
	  }
	  try
	  {
	    Red_core = new Location(Bukkit.getWorld(getConfig().getString("Redcore_world")), getConfig().getInt("Redcore_x"), getConfig().getInt("Redcore_y"), getConfig().getInt("Redcore_z"));
	    Blue_core = new Location(Bukkit.getWorld(getConfig().getString("Bluecore_world")), getConfig().getInt("Bluecore_x"), getConfig().getInt("Bluecore_y"), getConfig().getInt("Bluecore_z"));
	  }
	  catch (IllegalArgumentException e)
	  {
	    getLogger().info("������ �ھ� �Ǵ� ����� �ھ �����Ǿ� ���� �ʽ��ϴ�.");
	  }
	}
	
	public void onDisbale(){
		getLogger().info("EG �ŵ��� ���� �÷������� ��ε� �Ǿ����ϴ�..");
	}
	
	public boolean onCommand(CommandSender talker, Command command, String str, String[] args){
		if(talker instanceof Player){
			Player p = (Player) talker;
			if(str.equalsIgnoreCase("ewg") && p.isOp()){
				if(args.length <= 0){
					Helpmessages(p);
					return true;
				}
				else{
					if(args[0].equalsIgnoreCase("set")){
						Setloc(p, args);
						return true;
					}
					if(args[0].equalsIgnoreCase("join")){
						GameJoin(p);
						return true;
					}
					if(args[0].equalsIgnoreCase("quit")){
						GameQuit(p);
						return true;
					}
					if(args[0].equalsIgnoreCase("stop")){
						Forcestop();
						return true;
					}
				}
			}
			if(str.equalsIgnoreCase("wg") && plist.contains(p) && check_start){
				if(args.length <= 0){
					p.sendMessage(MS+"/wg <x/tc/item/help>"); return true;
				}
				if(args[0].equalsIgnoreCase("x") && plist.contains(p)){
					Targeting(p, args);
					return true;
				}
				if(args[0].equalsIgnoreCase("tc") && plist.contains(p)){
					if(teamchat.contains(p.getName()))
					{
						teamchat.remove(p.getName());
						p.sendMessage(MS+"��ä���� �����ϴ�.");
					}
					else
					{
						teamchat.add(p.getName());
						p.sendMessage(MS+"��ä���� �׽��ϴ�.");
					}
					return true;
				}
				if(args[0].equalsIgnoreCase("item") && plist.contains(p)){
					if(Checkcooldown(p, "�⺻��")){
						Addcooldown(p, "�⺻��", 300);
						giveBaseItem(p);
					}
					return true;
				}
				if(args[0].equalsIgnoreCase("help") && plist.contains(p)){
					AbilityHelp(p);
					return true;
				}
			}
		}
		return false;
	}
	
	public static void Forcestop(){
		Bukkit.broadcastMessage(MS+"�ŵ��� ������ ���� ���� �Ǿ����ϴ�.");
		for(Player p : plist)
		{
	          try{
	        	  me.Bokum.EGM.Main.Spawn(p);
	          } catch(Exception e){
	        	  p.teleport(Main.Lobby);
	          }
		}
		Cleardata();
	}
	
	public static void Targeting(Player player, String[] args){
		if(!plist.contains(player) || !jlist.containsKey(player.getName())) return;
		if(args.length <= 1){
			player.sendMessage(MS+"Ÿ�� ���� ��� : '/wg x �÷��̾�г���'");
			return;
		}
		String target = args[1];
		if(!jlist.containsKey(target)){
			player.sendMessage(MS+"�ش� �÷��̾�� ���ӿ� ���� ������ �ʽ��ϴ�."); return;
		}
		switch(jlist.get(player.getName())){
		case "�ڷ�����":
			Player targetp = null;
			for(Player t : bluelist.contains(player) ? bluelist : redlist){
				if(t.getName().equalsIgnoreCase(target))
					targetp = t;
			}
			if(targetp != null)
			{
				if (!target.equalsIgnoreCase(player.getName()))
				{
					abilitytarget.put(player.getName(), target);
					player.sendMessage("Ÿ���� ����߽��ϴ�.   "+ChatColor.GREEN+target);
				}
				else
					player.sendMessage("�ڱ� �ڽ��� Ÿ������ ��� �� �� �����ϴ�.");
			}
			else
				player.sendMessage("�ڽ��� ������ ������ �����մϴ�.");	
		}
	}
	
	public void Helpmessages(Player p)
	{
		p.sendMessage(MS+"/ewg join");
		p.sendMessage(MS+"/ewg quit");
		p.sendMessage(MS+"/ewg set");
		p.sendMessage(MS+"/ewg stop");
	}
	
	public static void Sendmessage(String str){
		for(Player p : plist){
			p.sendMessage(str);
		}
	}
	
	public void Setloc(Player p, String[] args){
		if(args.length <= 1){
			p.sendMessage(MS+"/ewg set lobby");
			p.sendMessage(MS+"/ewg set join");
			p.sendMessage(MS+"/ewg set red");
			p.sendMessage(MS+"/ewg set blue");
			p.sendMessage(MS+"/ewg set redcore");
			p.sendMessage(MS+"/ewg set bluecore");
			return;
		}
		if(args.length <= 2){
			if(args[1].equalsIgnoreCase("lobby")){
			    getConfig().set("Lobby_world", p.getLocation().getWorld().getName());
			    getConfig().set("Lobby_x", Integer.valueOf(p.getLocation().getBlockX()));
			    getConfig().set("Lobby_y", Integer.valueOf(p.getLocation().getBlockY() + 1));
			    getConfig().set("Lobby_z", Integer.valueOf(p.getLocation().getBlockZ()));
			    saveConfig();
			    Loadconfig();
				p.sendMessage(MS+"�κ� ���� �Ϸ�");
			}
			else if(args[1].equalsIgnoreCase("join")){
			    getConfig().set("Join_world", p.getLocation().getWorld().getName());
			    getConfig().set("Join_x", Integer.valueOf(p.getLocation().getBlockX()));
			    getConfig().set("Join_y", Integer.valueOf(p.getLocation().getBlockY() + 1));
			    getConfig().set("Join_z", Integer.valueOf(p.getLocation().getBlockZ()));
			    saveConfig();
			    Loadconfig();
				p.sendMessage(MS+"���� ���� �Ϸ�");
			} else if(args[1].equalsIgnoreCase("red")){
			    getConfig().set("Red_world", p.getLocation().getWorld().getName());
			    getConfig().set("Red_x", Integer.valueOf(p.getLocation().getBlockX()));
			    getConfig().set("Red_y", Integer.valueOf(p.getLocation().getBlockY() + 1));
			    getConfig().set("Red_z", Integer.valueOf(p.getLocation().getBlockZ()));
			    saveConfig();
			    Loadconfig();
				p.sendMessage(MS+"������ ���� ���� �Ϸ�");
			} else if(args[1].equalsIgnoreCase("blue")){
			    getConfig().set("Blue_world", p.getLocation().getWorld().getName());
			    getConfig().set("Blue_x", Integer.valueOf(p.getLocation().getBlockX()));
			    getConfig().set("Blue_y", Integer.valueOf(p.getLocation().getBlockY() + 1));
			    getConfig().set("Blue_z", Integer.valueOf(p.getLocation().getBlockZ()));
			    saveConfig();
			    Loadconfig();
				p.sendMessage(MS+"����� ���� ���� �Ϸ�");
			} else if(args[1].equalsIgnoreCase("redcore")){
			    getConfig().set("Redcore_world", p.getLocation().getWorld().getName());
			    getConfig().set("Redcore_x", Integer.valueOf(p.getLocation().getBlockX()));
			    getConfig().set("Redcore_y", Integer.valueOf(p.getLocation().getBlockY() - 1));
			    getConfig().set("Redcore_z", Integer.valueOf(p.getLocation().getBlockZ()));
			    saveConfig();
			    Loadconfig();
				p.sendMessage(MS+"������ �ھ� ���� �Ϸ�");
			} else if(args[1].equalsIgnoreCase("bluecore")){
			    getConfig().set("Bluecore_world", p.getLocation().getWorld().getName());
			    getConfig().set("Bluecore_x", Integer.valueOf(p.getLocation().getBlockX()));
			    getConfig().set("Bluecore_y", Integer.valueOf(p.getLocation().getBlockY() - 1));
			    getConfig().set("Bluecore_z", Integer.valueOf(p.getLocation().getBlockZ()));
			    saveConfig();
			    Loadconfig();
				p.sendMessage(MS+"����� �ھ� ���� �Ϸ�");
			} else if(args[1].equalsIgnoreCase("spec")){
			    getConfig().set("Spec_world", p.getLocation().getWorld().getName());
			    getConfig().set("Spec_x", Integer.valueOf(p.getLocation().getBlockX()));
			    getConfig().set("Spec_y", Integer.valueOf(p.getLocation().getBlockY() + 1));
			    getConfig().set("Spec_z", Integer.valueOf(p.getLocation().getBlockZ()));
			    saveConfig();
			    Loadconfig();
				p.sendMessage(MS+"���� ���� �Ϸ�");
			}
			return;
		}
	}
	
	public static void GameJoin(Player p){
		if(plist.contains(p)){
			p.sendMessage(MS+"�̹� ���ӿ� �������̽ʴϴ�.");
			return;
		}
		if(backuptlist.containsKey(p.getName())){
			plist.add(p);
			me.Bokum.EGM.Main.gamelist.put(p.getName(), "�ŵ��� ����");
			p.getInventory().clear();
			p.getInventory().setHelmet(null);
			p.getInventory().setChestplate(null);
			p.getInventory().setLeggings(null);
			p.getInventory().setBoots(null);
			p.getInventory().setItem(8, helpitem);
			Sendmessage(MS+p.getName()+" ���� �ŵ��� ���� ���ӿ� �������ϼ̽��ϴ�.");
			if(backuptlist.get(p.getName()).equalsIgnoreCase("���")){
				Bukkit.dispatchCommand(p.getServer().getConsoleSender(), "scoreboard teams join blue "+p.getName());
				bluelist.add(p);
				p.teleport(Blue_spawn);
			}else{
				Bukkit.dispatchCommand(p.getServer().getConsoleSender(), "scoreboard teams join red "+p.getName());
				redlist.add(p);
				p.teleport(Red_spawn);
			}
			return;
		}
		if(check_start){
			p.sendMessage(MS+"�̹� ������ ���� ���̱� ������ ������ �����մϴ�.");
			p.teleport(specLoc);
		} else if(plist.size() >= 12){
			p.sendMessage(MS+"�̹� �ִ��ο�(12)�Դϴ�.");
			return;
		}else{
			plist.add(p);
			p.teleport(joinpos);
			me.Bokum.EGM.Main.gamelist.put(p.getName(), "�ŵ��� ����");
			p.getInventory().clear();
			p.getInventory().setHelmet(null);
			p.getInventory().setChestplate(null);
			p.getInventory().setLeggings(null);
			p.getInventory().setBoots(null);
			p.getInventory().setItem(8, helpitem);
			Sendmessage(MS+p.getName()+" ���� �ŵ��� ���� ���ӿ� �����ϼ̽��ϴ�. �ο� (��e "+plist.size()+"��7 / ��c6 ��f)");
			if(!check_lobbystart && plist.size() >= 6){
				Startgame();
			}
			for(Player t : plist){
				t.playSound(t.getLocation(), Sound.NOTE_PLING, 2.0f, 1.0f);
			}
		}
	}
	
	public void GameQuit(Player p)
	{
		if(!plist.contains(p))
		{
			return;
		}
		if(teamchat.contains(p.getName()))
		{
			teamchat.remove(p.getName());
		}
		plist.remove(p);
		if(!check_start)
		{
			return;
		}
		if(bluelist.contains(p))
		{
			Sendmessage(MS+"��b����� ��6"+p.getName()+" ��f���� ���� �ϼ̽��ϴ�.");
			bluelist.remove(p);
		}
		if(redlist.contains(p))
		{
			Sendmessage(MS+"��c������ ��6"+p.getName()+" ��f���� ���� �ϼ̽��ϴ�.");
			redlist.remove(p);
		}
	}
	
	public static int Getcursch(){
		for(int i = 0 ; i < tasknum.length; i++){
			if(tasknum[i] == -5){
				return i;
			}
		}
		return 0;
	}
	
	
	public static boolean Checkcooldown(Player p, String str)
	{
		if(!Main.cooldown.containsKey(p.getName()+str) || Main.cooldown.get(p.getName()+str) <= (int)(java.lang.System.currentTimeMillis()/1000))
		{
			return true;
		}
		p.sendMessage(Main.MS+ChatColor.AQUA+(Main.cooldown.get(p.getName()+str)-(int)(java.lang.System.currentTimeMillis()/1000))
				+ChatColor.RESET+"�� �� ��ų�� ����Ͻ� �� �ֽ��ϴ�.");
		return false;
	}
	
	public static void Addcooldown(Player p, String str, int cooldown)
	{
		Main.cooldown.put(p.getName()+str, (int)(java.lang.System.currentTimeMillis()/1000)+cooldown);
		Countdown(p, cooldown, str);
	}

	
	public static void Canceltask(int cur)
	{
		Bukkit.getScheduler().cancelTask(tasknum[cur]);
		tasknum[cur] = -5;
		tasktime[cur] = -5;
	}
	
	public static void Countdown(final Player p, long time, final String skill)
	{
		time *= 20;
		if(time < 60){
			return;
		}
		Bukkit.getScheduler().scheduleSyncDelayedTask(instance, new Runnable()
		{
			public void run()
			{
				p.sendMessage(MS+ChatColor.AQUA+"3���� "+ChatColor.RESET+skill+"�ɷ��� ��Ÿ���� �����ϴ�.");
			}
		}, time-60);
		Bukkit.getScheduler().scheduleSyncDelayedTask(instance, new Runnable()
		{
			public void run()
			{
				p.sendMessage(MS+ChatColor.AQUA+"2���� "+ChatColor.RESET+skill+"�ɷ��� ��Ÿ���� �����ϴ�.");
			}
		}, time-40);
		Bukkit.getScheduler().scheduleSyncDelayedTask(instance, new Runnable()
		{
			public void run()
			{
				p.sendMessage(MS+ChatColor.AQUA+"1���� "+ChatColor.RESET+skill+"�ɷ��� ��Ÿ���� �����ϴ�.");
			}
		}, time-20);
		Bukkit.getScheduler().scheduleSyncDelayedTask(instance, new Runnable()
		{
			public void run()
			{
				p.sendMessage(MS+ChatColor.RESET+skill+"�ɷ��� ��Ÿ���� �������ϴ�.");
			}
		}, time);
	}
	
	  public static void GameHelper(Player p, int slot){
		  switch(slot){
		  case 11:
			  p.sendMessage("��7���� �̸� ��f: ��c�ŵ��� ����");
			  p.sendMessage("��f������ ���۵Ǹ� ��c��������f�� ��b�������f���� �������ϴ�.");
			  p.sendMessage("��f������ �������� ��b���̾ƺ�(�ھ�)��f�� �ֽ��ϴ�.");
			  p.sendMessage("��f������ ��b���̾ƺ���f�� ���� ��d�μ��¡�f ���� �¸��մϴ�.");
			  p.sendMessage("��f������ ��c�⺻�۰� ��e�ɷ¡�f�� ��������");
			  p.sendMessage("��e�ɷ¡�f�� ��6������ �����f�� ��a�� �Ǵ� ��Ŭ����f�Ͽ� ����մϴ�.");
			  p.closeInventory();
			  return;
			  
		  case 13:
			  p.sendMessage("��f������ ��eâ���f�� �� �� �ֽ��ϴ�.");
			  p.sendMessage("��f������ ��b���� �Ǵ� �ں��� ��ܡ�f�� ��c��ϡ�f�� ���� ������!\n ��f�� ������ ��4����׷���f�� ���ֵǾ� ó���� �޽��ϴ�. ");
			  p.sendMessage("��f�Ƿ� ���� �߿��� ���� ��d�ųʡ�f�Դϴ�. ���ΰ��� �弳�� ����������.");
			  p.sendMessage("��f���̾ƺ��� ��伮���� ���δ� ���� �ȵ˴ϴ�.");
			  p.sendMessage("��f���̾ƺ��� ����̷ε� Ķ �� �ֽ��ϴ�.");
			  p.sendMessage("��f������ ������ ���� ��ġ�ϴ� ���� �����մϴ�. \n ��f�������� ��c��� ��ų��f�� ó���� ������ �� �ֽ��ϴ�.");
			  p.sendMessage("��c�� ��ų�� �ڿ������� �����ִ� ����������.");
			  p.closeInventory();
			  return;
			  
		  case 15:
			  p.sendMessage("��f/wg x - ��e�ɷ� ��� ����� �����մϴ�. (���� �ɷ¸�)");
			  p.sendMessage("��f/wg tc - ��e��ä���� �Ѱų� ���ϴ�.");
			  p.sendMessage("��f/wg item - ��e���� �������� �޽��ϴ�. (��Ÿ�� 300��)");
			  p.sendMessage("��f/wg help - ��e�ɷ��� Ȯ���մϴ�.");
			  p.sendMessage("��c�� ������ ����� ��e�Ϲ� ������f�� ��b����� �� �١�f�� \n�����ø� ���� �����մϴ�.");
			  p.closeInventory();
			  return;
			  
		  default: return;
		  }  
	  }
	
	  public static boolean Takeitem(Player p, int id, int amt){
		  int tamt = amt;
			for(int i = 0; i < p.getInventory().getSize(); i++){
				if(tamt > 0){
					ItemStack pitem = p.getInventory().getItem(i);
					if(pitem != null && pitem.getTypeId() == id){
						tamt -= pitem.getAmount();
						if(tamt <= 0){
							Removeitem(p, id, amt);
							return true;
						}
					}
				}
			}
			p.sendMessage(MS+"��7���� ��c"+tamt+"��7�� �����մϴ�.");
			return false;
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
	
		public static void SkillCountdown(final Player p, long time, final String skill)
		{
			time *= 20;
			Bukkit.getScheduler().scheduleSyncDelayedTask(instance, new Runnable()
			{
				public void run()
				{
					p.sendMessage(MS+ChatColor.AQUA+"3���� "+ChatColor.RESET+skill+"�ɷ��� �����˴ϴ�.");
				}
			}, time-60);
			Bukkit.getScheduler().scheduleSyncDelayedTask(instance, new Runnable()
			{
				public void run()
				{
					p.sendMessage(MS+ChatColor.AQUA+"2���� "+ChatColor.RESET+skill+"�ɷ��� �����˴ϴ�.");
				}
			}, time-40);
			Bukkit.getScheduler().scheduleSyncDelayedTask(instance, new Runnable()
			{
				public void run()
				{
					p.sendMessage(MS+ChatColor.AQUA+"1���� "+ChatColor.RESET+skill+"�ɷ��� �����˴ϴ�.");
				}
			}, time-20);
			Bukkit.getScheduler().scheduleSyncDelayedTask(instance, new Runnable()
			{
				public void run()
				{
					p.sendMessage(MS+ChatColor.AQUA+skill+"�ɷ��� �����ƽ��ϴ�.");
				}
			}, time);
		}
	  
	public static void Setbase(Player p){
		p.teleport(bluelist.contains(p) ? Blue_spawn : Red_spawn);
		giveBaseItem(p);
		p.sendMessage(MS+"�ɷ��� Ȯ���Ͻ÷��� ��e/wg help ��f�� �Է��ϼ��� \n ��e/wg tc ��f��ɾ�� ��ä���� �Ѱų� ���ϴ�. \n ��e/wg item ��f��ɾ�� 300�ʸ��� ���� ���޹޽��ϴ�.");
	}
	
	public static boolean AirToFar(Player player, Block block)
	{
		if (block.getTypeId() != 0)
			return true;
		else
		{
			player.sendMessage(ChatColor.RED+"������ �Ÿ��� �ʹ� �ٴϴ�.");
			return false;
		}
	}
	
	public static void AbilityHelp(Player player){
		if(!plist.contains(player)) return;
		if(!jlist.containsKey(player.getName())){
			player.sendMessage(MS+"�ɷ��� �����ϴ�.");
		}
		switch(jlist.get(player.getName())){
		case "�ϵ���":
			player.sendMessage(ChatColor.DARK_GREEN+"=================== "+ChatColor.YELLOW+"�ɷ� ����"+ChatColor.DARK_GREEN+" ===================");
			player.sendMessage(ChatColor.YELLOW+"[ �ϵ��� ]  "+ChatColor.RED+"[ �� ]  "+ChatColor.BLUE+"Active,Passive  "+ChatColor.GREEN+"Rank[ S ]");
			player.sendMessage("������ ���Դϴ�.\n"+
							   "�нú� �ɷ����� ����� 80% Ȯ���� �������� ���� �ʽ��ϴ�.\n" +
							   "��Ƽ�� �ɷ����� ��븦 �������� ����߸� �� ������ ������ �ε带 ������� �ߵ� ��ų �� �ֽ��ϴ�.\n" +
							   "�Ϲݴɷ��� �ֺ� 2ĭ�� �ִ� �ڽ��� ������ ��� �÷��̾�,���Ϳ� �Բ� �������� ��������\n" +
							   "��޴ɷ��� �ֺ� 4ĭ�� �ִ� �ڽ��� ������ ��� �÷��̾�,���͸� �������� ����߸��ϴ�.\n" +
							   ChatColor.AQUA+"�Ϲ�(��Ŭ��) "+ChatColor.WHITE+" �ں��� 5�� �Ҹ�, ��Ÿ�� 100��\n" +
							   ChatColor.RED+"���(��Ŭ��) "+ChatColor.WHITE+" �ں��� 10�� �Ҹ�, ��Ÿ�� 200��\n");
			break;
		case "�����׸�":
			player.sendMessage(ChatColor.DARK_GREEN+"=================== "+ChatColor.YELLOW+"�ɷ� ����"+ChatColor.DARK_GREEN+" ===================");
			player.sendMessage(ChatColor.YELLOW+"[ �����׸� ]  "+ChatColor.RED+"[ �� ]  "+ChatColor.BLUE+"Active,Passive  "+ChatColor.GREEN+"Rank[ B ]");
			player.sendMessage("��Ȯ�� ���Դϴ�.\n"+
							   "�ں����� �̿��ؼ� ���� ���� �� �ֽ��ϴ�.\n" +
							   "�⺻������ ������� ���� ������ ü�� ȸ���ӵ��� 4��� ����մϴ�.\n" +
							   ChatColor.GREEN+"(��Ŭ��) "+ChatColor.WHITE+" �ں��� 10�� �Ҹ�, ��Ÿ�� 30��\n");
			break;
		case "���׳�":
			player.sendMessage(ChatColor.DARK_GREEN+"=================== "+ChatColor.YELLOW+"�ɷ� ����"+ChatColor.DARK_GREEN+" ===================");
			player.sendMessage(ChatColor.YELLOW+"[ ���׳� ]  "+ChatColor.RED+"[ �� ]  "+ChatColor.BLUE+"Active,Passive  "+ChatColor.GREEN+"Rank[ S ]");
			player.sendMessage("������ ���Դϴ�.\n"+
							   "�÷��̾���� ����� �� ���� ����ġ�� ����ϴ�.\n" +
							   "�ڽ��� ����ϸ� ����ġ�� �ʱ�ȭ�˴ϴ�.\n" +
							   "��Ŭ������ å�� ���� �� ������ ��Ŭ������ ��æƮ ���̺��� ������ �ֽ��ϴ�.\n" +
							   ChatColor.AQUA+"�Ϲ�(��Ŭ��) "+ChatColor.WHITE+" �ں��� 5�� �Ҹ�, ��Ÿ�� 10��\n" +
							   ChatColor.RED+"���(��Ŭ��) "+ChatColor.WHITE+" �ں��� 64�� �Ҹ�, ��Ÿ�� 3��\n");
			break;
		case "�Ƹ��׹̽�":
			player.sendMessage(ChatColor.DARK_GREEN+"=================== "+ChatColor.YELLOW+"�ɷ� ����"+ChatColor.DARK_GREEN+" ===================");
			player.sendMessage(ChatColor.YELLOW+"[ �Ƹ��׹̽� ]  "+ChatColor.RED+"[ �� ]  "+ChatColor.BLUE+"Active,Passive  "+ChatColor.GREEN+"Rank[ A ]");
			player.sendMessage("��ɰ� ���� ���Դϴ�.\n"+
							   "ȭ��� Ȱ�� �ں���� ��ȯ�Ҽ� �ֽ��ϴ�.(��Ŭ�� : ȭ�� 1��, ��Ŭ�� : Ȱ 1��)\n" +
							   "ȭ��� ���ݴ��� �÷��̾�� 20�ۼ�Ʈ�� Ȯ���� ����մϴ�.\n" +
							   ChatColor.AQUA+"�Ϲ�(��Ŭ��) "+ChatColor.WHITE+" �ں��� 7�� �Ҹ�, ��Ÿ�� 20��\n" +
							   ChatColor.RED+"���(��Ŭ��) "+ChatColor.WHITE+" �ں��� 15�� �Ҹ�, ��Ÿ�� 180��\n");
			break;
			
		case "�Ʒ���":
			player.sendMessage(ChatColor.DARK_GREEN+"=================== "+ChatColor.YELLOW+"�ɷ� ����"+ChatColor.DARK_GREEN+" ===================");
			player.sendMessage(ChatColor.YELLOW+"[ �Ʒ��� ]  "+ChatColor.RED+"[ �� ]  "+ChatColor.BLUE+"Passive  "+ChatColor.GREEN+"Rank[ A ]");
			player.sendMessage("������ ���Դϴ�.\n"+
							   "��� ���� �������� 1.5�� ����մϴ�.\n" +
							   "�߰� �нú� �ɷ����� 10�ۼ�Ʈ Ȯ���� ������ ȸ���մϴ�");
			break;
			
		case "�������佺":
			player.sendMessage(ChatColor.DARK_GREEN+"=================== "+ChatColor.YELLOW+"�ɷ� ����"+ChatColor.DARK_GREEN+" ===================");
			player.sendMessage(ChatColor.YELLOW+"[ �������佺 ]  "+ChatColor.RED+"[ �� ]  "+ChatColor.BLUE+"Active,Passive  "+ChatColor.GREEN+"Rank[ B ]");
			player.sendMessage("���� ���Դϴ�.\n"+
							   "ȭ�� �� ��� �������� ���� �ʽ��ϴ�.");
			break;
		case "�ƽ�Ŭ���Ǿ":
			player.sendMessage(ChatColor.DARK_GREEN+"=================== "+ChatColor.YELLOW+"�ɷ� ����"+ChatColor.DARK_GREEN+" ===================");
			player.sendMessage(ChatColor.YELLOW+"[ �ƽ�Ŭ���Ǿ ]  "+ChatColor.RED+"[ �� ]  "+ChatColor.BLUE+"Active  "+ChatColor.GREEN+"Rank[ B ]");
			player.sendMessage("�Ǽ��� ���Դϴ�.\n"+
							   "�ڽ��� ü��ȸ�� Ȥ�� �ֺ� ������ ü���� ȸ���մϴ�.\n" +
							   "�Ϲݴɷ����� �ڽ��� ��� ȸ�� ��ų �� ������\n" +
							   "��޴ɷ����� �ֺ� 5ĭ�� �ִ� �ڽ��� ������ ������ ü���� ��� ȸ�� ��ų �� �ֽ��ϴ�.\n" +
							   ChatColor.AQUA+"�Ϲ�(��Ŭ��) "+ChatColor.WHITE+" �ں��� 1�� �Ҹ�, ��Ÿ�� 50��\n" +
							   ChatColor.RED+"���(��Ŭ��) "+ChatColor.WHITE+" �ں��� 5�� �Ҹ�, ��Ÿ�� 100��\n");
			break;
		case "�츣�޽�":
			player.sendMessage(ChatColor.DARK_GREEN+"=================== "+ChatColor.YELLOW+"�ɷ� ����"+ChatColor.DARK_GREEN+" ===================");
			player.sendMessage(ChatColor.YELLOW+"[ �츣�޽� ]  "+ChatColor.RED+"[ �� ]  "+ChatColor.BLUE+"Active,Passive,Buff  "+ChatColor.GREEN+"Rank[ S ]");
			player.sendMessage("�������� ���Դϴ�.\n"+
							   "�⺻������ �̵��ӵ��� ������ ������ �ε带 ����� �ɷ��� ���� ���� �� �� �ֽ��ϴ�.(�����ϸ鼭 ���ø� �ٷ� �� �� �ֽ��ϴ�.)\n" +
							   "���� �߿��� ���� �������� ���� �ʽ��ϴ�.\n" +
							   ChatColor.GREEN+"��Ŭ�� : 5�ʰ� ���� �� �� �ֽ��ϴ�.\n" +
							   ChatColor.AQUA+"(��Ŭ��) "+ChatColor.WHITE+" �ں��� 2�� �Ҹ�, ��Ÿ�� 60��\n");
			break;
			
		case "����ϼҽ�":
			player.sendMessage(ChatColor.DARK_GREEN+"=================== "+ChatColor.YELLOW+"�ɷ� ����"+ChatColor.DARK_GREEN+" ===================");
			player.sendMessage(ChatColor.YELLOW+"[ ����ϼҽ� ]  "+ChatColor.RED+"[ �� ]  "+ChatColor.BLUE+"Passive,DeBuff  "+ChatColor.GREEN+"Rank[ A ]");
			player.sendMessage("���� ���Դϴ�.\n"+
							   "25% Ȯ���� �ڽ��� ������ 10�ʰ� ����� �þ߸� ����������\n" +
							   "���ÿ� ����� �̵��ӵ�, ���ݷ��� ����ϴ�.\n");
			break;
		case "����":
			player.sendMessage(ChatColor.DARK_GREEN+"=================== "+ChatColor.YELLOW+"�ɷ� ����"+ChatColor.DARK_GREEN+" ===================");
			player.sendMessage(ChatColor.YELLOW+"[ ���� ]  "+ChatColor.RED+"[ �ΰ� ]  "+ChatColor.BLUE+"Passive  "+ChatColor.GREEN+"Rank[ B ]");
			player.sendMessage("���� ȿ�������� ĳ�� �ɷ��Դϴ�.\n"+
							   "�ں����� Ķ �� ���� 3% Ȯ���� �ѹ��� 20���� ���� �� �ֽ��ϴ�.\n");
			break;
		case "�ڷ�����":
			player.sendMessage(ChatColor.DARK_GREEN+"=================== "+ChatColor.YELLOW+"�ɷ� ����"+ChatColor.DARK_GREEN+" ===================");
			player.sendMessage(ChatColor.YELLOW+"[ �ڷ����� ]  "+ChatColor.RED+"[ �ΰ� ]  "+ChatColor.BLUE+"Active  "+ChatColor.GREEN+"Rank[ B ]");
			player.sendMessage("�����̵��� ���� �������Դϴ�.\n"+
							   "������ �ε带 �̿��� �ڽ��� ���ϴ� ��ġ(15ĭ)�� �ڷ���Ʈ �� �� ������ ���� ���� ��ġ�� ����Ī�� �����մϴ�.\n" +
							   "��Ŭ������ �ڽ��� ����Ű�� �ִ°����� �ڷ���Ʈ �ϸ�.\n" +
							   "��Ŭ������ Ÿ�ٿ� ����� �� �ڽ��� ������ ��ġ�� ġȯ�մϴ�.(Ÿ�� ��Ϲ� : /wg x <Player>)\n" +
							   ChatColor.AQUA+"�Ϲ�(��Ŭ��) "+ChatColor.WHITE+" �ں��� 4�� �Ҹ�, ��Ÿ�� 25��\n" +
							   ChatColor.RED+"���(��Ŭ��) "+ChatColor.WHITE+" �ں��� 4�� �Ҹ�, ��Ÿ�� 40��\n");
			break;
			
		case "�չ�":
			player.sendMessage(ChatColor.DARK_GREEN+"=================== "+ChatColor.YELLOW+"�ɷ� ����"+ChatColor.DARK_GREEN+" ===================");
			player.sendMessage(ChatColor.YELLOW+"[ ���� ]  "+ChatColor.RED+"[ �ΰ� ]  "+ChatColor.BLUE+"Active  "+ChatColor.GREEN+"Rank[ A ]");
			player.sendMessage("������ �ٷ�� �ɷ��Դϴ�.\n"+
							   "������ ��ġ�� 2.0�� ������ ����ŵ�ϴ�.\n" +
							   "��Ŭ������ �ش� ��ġ�� ������ �ʴ� tnt�� ��ġ�ϸ�\n" +
							   "��Ŭ������ ��𼭵� ���߽�ų �� �ֽ��ϴ�.\n" +
							   ChatColor.GREEN+"(��Ŭ��) "+ChatColor.WHITE+" �ں��� 5�� �Ҹ�, ��Ÿ�� 27��"); 
			break;
			
		case "ũ����":
			player.sendMessage(ChatColor.DARK_GREEN+"=================== "+ChatColor.YELLOW+"�ɷ� ����"+ChatColor.DARK_GREEN+" ===================");
			player.sendMessage(ChatColor.YELLOW+"[ ũ���� ]  "+ChatColor.RED+"[ �ΰ� ]  "+ChatColor.BLUE+"Active,Passive  "+ChatColor.GREEN+"Rank[ C ]");
			player.sendMessage("������ �ɷ��Դϴ�.\n"+
							   "������ �ε带 ���� �ɷ��� ����ϸ�\n" +
							   "ũ���ۿ� ���� ���߷��� ������ ����ŵ�ϴ�.\n" +
							   ChatColor.GREEN+"(��Ŭ��) "+ChatColor.WHITE+" �ں��� 6�� �Ҹ�, ��Ÿ�� 33��\n");
			break;
			
		case "�ϻ���": 
			player.sendMessage(ChatColor.DARK_GREEN+"=================== "+ChatColor.YELLOW+"�ɷ� ����"+ChatColor.DARK_GREEN+" ===================");
			player.sendMessage(ChatColor.YELLOW+"[ �ϻ��� ]  "+ChatColor.RED+"[ �ΰ� ]  "+ChatColor.BLUE+"Active  "+ChatColor.GREEN+"Rank[ B ]");
			player.sendMessage("��ø�� ����� �������ִ� �ɷ��Դϴ�.\n"+
							   "������ �� �ɷ��� ����ϸ� ���� ���� �������� ������ �ѹ� �� �� �� �ֽ��ϴ�.\n" +
							   "��Ŭ������ �ش�������� ������ �մϴ�.\n" +
							   "��Ŭ������ �ݰ� 10ĭ���� �ִ� ���� ������ �����̵� �մϴ�.\n" +
							   ChatColor.AQUA+"�Ϲ�(��Ŭ��) "+ChatColor.WHITE+" �ں��� 0�� �Ҹ�, ��Ÿ�� 2��\n" +
							   ChatColor.RED+"���(��Ŭ��) "+ChatColor.WHITE+" �ں��� 2�� �Ҹ�, ��Ÿ�� 20��\n");
			break;
			
		case "�ݻ�":
			player.sendMessage(ChatColor.DARK_GREEN+"=================== "+ChatColor.YELLOW+"�ɷ� ����"+ChatColor.DARK_GREEN+" ===================");
			player.sendMessage(ChatColor.YELLOW+"[ �ݻ� ]  "+ChatColor.RED+"[ �ΰ� ]  "+ChatColor.BLUE+"Passive  "+ChatColor.GREEN+"Rank[ B ]");
			player.sendMessage("���� ���� �������� �����ڿ��� �ݻ��ϴ� �ɷ��Դϴ�..\n"+
							   "33% Ȯ���� �ڽ��� ���� �������� ���� ���濡�� �ݻ��մϴ�.(����)\n" +
							   "ȭ��� ���������� �޴µ������� �ݻ����� ���մϴ�.");
			break;
			
		case "����δ�":
			player.sendMessage(ChatColor.DARK_GREEN+"=================== "+ChatColor.YELLOW+"�ɷ� ����"+ChatColor.DARK_GREEN+" ===================");
			player.sendMessage(ChatColor.YELLOW+"[ ����δ� ]  "+ChatColor.RED+"[ �ΰ� ]  "+ChatColor.BLUE+"Active,Passive  "+ChatColor.GREEN+"Rank[ A ]");
			player.sendMessage("������ �þ߸� ������ �ɷ��Դϴ�.\n"+
							   "�ڽ��� ������ ���� 20%Ȯ���� �þ߰� �������ϴ�.(4�� ����)\n" +
							   "������ �ε带 �̿��� �ɷ����� �ڽ��� ������ ������ ��� ������ ����ε� �� �� �ֽ��ϴ�.(8�� ����)\n" +
							   ChatColor.GREEN+"(��Ŭ��) "+ChatColor.WHITE+" �ں��� 5�� �Ҹ�, ��Ÿ�� 73��\n");
			break;
			
		case "Ŭ��ŷ":
			player.sendMessage(ChatColor.DARK_GREEN+"=================== "+ChatColor.YELLOW+"�ɷ� ����"+ChatColor.DARK_GREEN+" ===================");
			player.sendMessage(ChatColor.YELLOW+"[ Ŭ��ŷ ]  "+ChatColor.RED+"[ �ΰ� ]  "+ChatColor.BLUE+"Active,Passive  "+ChatColor.GREEN+"Rank[ A ]");
			player.sendMessage("�����ð� �ڽ��� ���� ���� �� �ִ� �ɷ��Դϴ�.\n"+
								"Ÿ�� ������ 10%Ȯ���� �żӹ����� �޽��ϴ�.(4�� ����)\n" +
							   "�Ϲݴɷ��� �̿��� �ڽ��� ����� 15�ʰ� ���� ������ �޽��ϴ�.\n" +
							   ChatColor.GREEN+"(��Ŭ��) "+ChatColor.WHITE+" �ں��� 4�� �Ҹ�, ��Ÿ��30��\n");
			break;
			
		case "��������":
			player.sendMessage(ChatColor.DARK_GREEN+"=================== "+ChatColor.YELLOW+"�ɷ� ����"+ChatColor.DARK_GREEN+" ===================");
			player.sendMessage(ChatColor.YELLOW+"[ �������� ]  "+ChatColor.RED+"[ �ΰ� ]  "+ChatColor.BLUE+"Active  "+ChatColor.GREEN+"Rank[ S ]");
			player.sendMessage("ö,���̾Ƹ� ����� �� �� �ִ� �ɷ��Դϴ�.\n"+
							   "�Ϲݴɷ����� �ں����� �Һ��Ͽ� ö�� 8���� ȹ�� �� �� �ֽ��ϴ�.\n" +
							   "��޴ɷ����� ö���� �Һ��Ͽ� ���̾� 4���� ���� �� �ֽ��ϴ�.\n" +
							   ChatColor.AQUA+"�Ϲ�(��Ŭ��) "+ChatColor.WHITE+" �ں��� 70�� �Ҹ�, ��Ÿ�� 140��\n" +
							   ChatColor.RED+"���(��Ŭ��) "+ChatColor.WHITE+" �ں��� 70�� �Ҹ�, ��Ÿ�� 250��\n");
			break;
			
		case "����":
			player.sendMessage(ChatColor.DARK_GREEN+"=================== "+ChatColor.YELLOW+"�ɷ� ����"+ChatColor.DARK_GREEN+" ===================");
			player.sendMessage(ChatColor.YELLOW+"[ ���� ]  "+ChatColor.RED+"[ �ΰ� ]  "+ChatColor.BLUE+"Passive  "+ChatColor.GREEN+"Rank[ S ]");
			player.sendMessage("���� �ָ��� ����ϴ� �ɷ��Դϴ�.\n"+
							   "�ָ��� �̿��ؼ� �����ϸ� ���� ���� �ӵ��� ������ �� �ֽ��ϴ�.\n" +
							   "��밡 ���ŷ ���̶�� ȿ���� ���� �ʽ��ϴ�.\n" +
							   "����� ��Ŭ�Ƿ��� �����ּ���.(���丶�콺�� ��������Ф�)");
			break;
			
		case "����":
			player.sendMessage(ChatColor.DARK_GREEN+"=================== "+ChatColor.YELLOW+"�ɷ� ����"+ChatColor.DARK_GREEN+" ===================");
			player.sendMessage(ChatColor.YELLOW+"[ ���� ]  "+ChatColor.RED+"[ �ΰ� ]  "+ChatColor.BLUE+"Active, Buff  "+ChatColor.GREEN+"Rank[ B ]");
			player.sendMessage("������ ��� �� �� �ִ� �ɷ��Դϴ�.\n"+
							   "������ �ε带 �̿��ؼ� �ɷ��� ����� �� �ֽ��ϴ�.\n" +
							   "�Ϲݴɷ��� �ڽſ��� �������� ������ �����մϴ�.\n" +
							   "��޴ɷ��� �ڽ��� ������ �ڽ��� ��� �������� �������� ������ �����մϴ�.\n" +
							   ChatColor.AQUA+"�Ϲ�(��Ŭ��) "+ChatColor.WHITE+" �ں��� 1�� �Ҹ�, ��Ÿ�� 35��\n" +
							   ChatColor.RED+"���(��Ŭ��) "+ChatColor.WHITE+" �ں���2�� �Ҹ�, ��Ÿ�� 90��\n");
			break;
			
		case "����":
			player.sendMessage(ChatColor.DARK_GREEN+"=================== "+ChatColor.YELLOW+"�ɷ� ����"+ChatColor.DARK_GREEN+" ===================");
			player.sendMessage(ChatColor.YELLOW+"[ ���� ]  "+ChatColor.RED+"[ �ΰ� ]  "+ChatColor.BLUE+"Active,Passive  "+ChatColor.GREEN+"Rank[ B ]");
			player.sendMessage("������� ����ϴ� �ɷ��Դϴ�.\n"+
							   "������ �ε带 �̿��� �ɷ� ���� �ֺ� 10ĭ �ȿ� �ִ� �ڽ��� ������ ������ ��ο��� ���� 10�ʰ� ������� �����մϴ�.\n" +
							   "�ڽ��� ������ ���� 7% Ȯ���� 5�ʰ� ������� �ɸ��� �˴ϴ�. \n" +
							   ChatColor.GREEN+"(��Ŭ��) "+ChatColor.WHITE+" �ں��� 3�� �Ҹ�, ��Ÿ�� 66��\n");
			break;
			
		case "����":
			player.sendMessage(ChatColor.DARK_GREEN+"=================== "+ChatColor.YELLOW+"�ɷ� ����"+ChatColor.DARK_GREEN+" ===================");
			player.sendMessage(ChatColor.YELLOW+"[ ��ó ]  "+ChatColor.RED+"[ �ΰ� ]  "+ChatColor.BLUE+"Active,Passive  "+ChatColor.GREEN+"Rank[ B ]");
			player.sendMessage("�ü� �Դϴ�.\n"+
							   "Ȱ���� �������� 1.2��� ����մϴ�.\n ����� ȭ����  �ſ� �����߷��� ���� ������ ���� �ʽ��ϴ�.\n" +
							   "��Ŭ������ ȭ���� ������������ ��Ŭ������ Ȱ�� ������ �ֽ��ϴ�.\n" +
							   ChatColor.AQUA+"�Ϲ�(��Ŭ��) "+ChatColor.WHITE+" �ں��� 5�� �Ҹ�, ��Ÿ�� 20��\n" +
							   ChatColor.RED+"���(��Ŭ��) "+ChatColor.WHITE+" �ں��� 15�� �Ҹ�, ��Ÿ�� 60��\n");
			break;
			
		case "���Ľ�":
			player.sendMessage(ChatColor.DARK_GREEN+"=================== "+ChatColor.YELLOW+"�ɷ� ����"+ChatColor.DARK_GREEN+" ===================");
			player.sendMessage(ChatColor.YELLOW+"[ ���Ľ� ]  "+ChatColor.RED+"[ �ΰ� ]  "+ChatColor.BLUE+"Passive  "+ChatColor.GREEN+"Rank[ B ]");
			player.sendMessage("��� ���ݿ� ���Ͽ� �������� �ʽ��ϴ�..\n");
			break;
			
		default: return;
		}
	}
	
	public static void LeftClick(final Player player){
		if(!jlist.containsKey(player.getName())){
			player.sendMessage(MS+"�ɷ��� �����ϴ�.");
		}
		switch(jlist.get(player.getName())){
		case "�ϵ���":
			if (Checkcooldown(player, "��Ŭ��") && Takeitem(player, 4, 5))
			{
				Addcooldown(player, "��Ŭ��", 100);
				Entity entity=player;
				Location location = player.getLocation();
				location.setY(-2.0d);
				List<Entity> entitylist = entity.getNearbyEntities(2, 2, 2);
				for (Entity e : entitylist)
				{
					if (e instanceof LivingEntity)
					{
						e.teleport(location);
						if (e.getType() == EntityType.PLAYER)
							((Player)e).sendMessage("������ ���� �ɷ¿� ���� �������� �������ϴ�.");
						player.getWorld().playSound(player.getLocation(), Sound.ENDERMAN_STARE, 2.0f, 2.0f);
					}
				}
				player.teleport(location);
			} break;
			
		case "���׳�":
			if (Checkcooldown(player, "��Ŭ��") && Takeitem(player, 4, 5))
			{
				Addcooldown(player, "��Ŭ��", 10);
				player.getInventory().addItem(new ItemStack(Material.BOOK.getId(),3));
				player.getWorld().playSound(player.getLocation(), Sound.CHEST_OPEN, 2.0f, 2.0f);
			}
			break;
			
		case "�Ƹ��׹̽�":
			if (Checkcooldown(player, "��Ŭ��") && Takeitem(player, 4, 7))
			{
				Addcooldown(player, "��Ŭ��", 20);
				player.getInventory().addItem(new ItemStack(Material.ARROW.getId(), 1));
				player.getWorld().playSound(player.getLocation(), Sound.CHEST_OPEN, 2.0f, 2.0f);
			}
			break;
			
		case "�ƽ�Ŭ���Ǿ":
			if (Checkcooldown(player, "��Ŭ��") && Takeitem(player, 4, 1))
			{
				Addcooldown(player, "��Ŭ��", 50);
				player.setHealth(player.getMaxHealth());
				player.getWorld().playSound(player.getLocation(), Sound.LEVEL_UP, 2.0f, 1.5f);
			} break;
			
		case "�츣�޽�":
			if (Checkcooldown(player, "��Ŭ��") && Takeitem(player, 4, 2))
			{
				Addcooldown(player, "��Ŭ��", 60);
				player.setAllowFlight(true);
				player.setFlying(true);
				SkillCountdown(player, 5, "����");
				player.getWorld().playSound(player.getLocation(), Sound.ENDERDRAGON_WINGS, 2.0f, 1.7f);
				Bukkit.getScheduler().scheduleSyncDelayedTask(instance, new Runnable()
				{
					public void run()
					{
						player.setAllowFlight(false);
						player.setFlying(false);
					}
				}, 100L);
			} break;
		case "�ڷ�����":
			if (Checkcooldown(player, "��Ŭ��") && Takeitem(player, 4, 4))
			{
				Block block = null;
				try{
					block = player.getTargetBlock(null, 15);
				}catch(Exception exception){
					return;
				}
				if(block == null){
					return;
				}
				if (AirToFar(player, block))
				{
					Location location0 = block.getLocation();
					Location location1 = block.getLocation();
					location0.setY(location0.getY()+1);
					location1.setY(location1.getY()+2);
					Block block0 = location0.getBlock();
					Block block1 = location1.getBlock();
					if ((block0.getTypeId()==0 || block1.getTypeId() == 78)&&block1.getTypeId()==0)
					{
						Addcooldown(player, "��Ŭ��", 25);
						Location plocation = player.getLocation();
						Location tlocation = block.getLocation();
						tlocation.setPitch(plocation.getPitch());
						tlocation.setYaw(plocation.getYaw());
						tlocation.setY(tlocation.getY()+1);
						tlocation.setX(tlocation.getX()+0.5);
						tlocation.setZ(tlocation.getZ()+0.5);
						player.teleport(tlocation);
						player.getWorld().playSound(player.getLocation(), Sound.ENDERMAN_TELEPORT, 2.0f, 1.2f);
					}
					else
						player.sendMessage("�ڷ���Ʈ �� �� �ִ� ������ ���� �ڷ���Ʈ�� ���� �߽��ϴ�.");
				}
			}
			break;
			
		case "�չ�":
			Block block = null;
			try{
				block = player.getTargetBlock(null, 5);
			}catch(Exception exception){
				return;
			}
			if(block == null){
				return;
			}
			if (block.getTypeId() != 0)
			{
				Location location = block.getLocation();
				location.setY(location.getY()+1);
				tntlocation = location;
				player.sendMessage("�ش� ���� ��ź�� ��ġ�Ǿ����ϴ�.");	
				player.getWorld().playSound(player.getLocation(), Sound.DOOR_OPEN, 2.0f, 2.0f);
			} break;
			
		case "ũ����":
			if (Checkcooldown(player, "��Ŭ��") && Takeitem(player, 4, 6))
			{
				Addcooldown(player, "��Ŭ��", 33);
				World world = player.getWorld();
				Location location = player.getLocation();
				float power = 3.0f;
				player.damage(30);
				world.createExplosion(location, power);
			} break;
			
		case "�ϻ���":
			Location temp = player.getLocation();
			Block b = temp.add(0,-1,0).getBlock();
			if ((b.getTypeId()==0) || (b.getTypeId()==78) || (b.getTypeId()==44))
			{	
				if (Checkcooldown(player, "��Ŭ��"))
				{
					Addcooldown(player, "��Ŭ��", 2);
					World world = player.getWorld();
					Location location = player.getLocation();
					Vector v = player.getEyeLocation().getDirection();
					v.setY(0.5);			
					player.setVelocity(v);
					world.playEffect(location, Effect.ENDER_SIGNAL, 1);
					world.playSound(player.getLocation(), Sound.BAT_TAKEOFF, 2.0f, 1.5f);
				}
			} break;
			
		case "����δ�":
			if (Checkcooldown(player, "��Ŭ��") && Takeitem(player, 4, 5))
			{
				Addcooldown(player, "��Ŭ��", 73);
				player.sendMessage("�ڽ��� ������ ������ ��� �÷��̾ ����ε� �մϴ�.");
				for (Player e : bluelist.contains(player) ? redlist : bluelist)
				{
					e.addPotionEffect(new PotionEffect(PotionEffectType.BLINDNESS, 160, 0), true);
					e.sendMessage("����δ��� ���� �þ߰� ��ο����ϴ�.");
				}
			} break;
			
		case "Ŭ��ŷ":
			if (Checkcooldown(player, "��Ŭ��") && Takeitem(player, 4, 4))
			{
				Addcooldown(player, "��Ŭ��", 30);
				player.sendMessage(MS+"15�ʰ� ����� ���� ������ �޽��ϴ�.");
				player.getWorld().playSound(player.getLocation(), Sound.MAGMACUBE_JUMP, 2.0f, 1.2f);
				player.addPotionEffect(new PotionEffect(PotionEffectType.INVISIBILITY, 300, 1));
			} break;
			
		case "��������":
			if (Checkcooldown(player, "��Ŭ��") && Takeitem(player, 4, 60))
			{
				Addcooldown(player, "��Ŭ��", 140);
				World world = player.getWorld();
				world.dropItem(player.getLocation().add(0,2,0), new ItemStack(Material.IRON_INGOT.getId(), 10));
			}
			
			break;
			
		case "����":
			if (Checkcooldown(player, "��Ŭ��") && Takeitem(player, 4, 1))
			{
				Addcooldown(player, "��Ŭ��", 35);
				if (Getrandom(1, 2) == 1)
				{
					player.addPotionEffect(new PotionEffect(PotionEffectType.DAMAGE_RESISTANCE, 600,0),true);
					player.sendMessage(ChatColor.LIGHT_PURPLE+"������ ���� ȿ���� ����Ǿ����ϴ�.");
				}
				if (Getrandom(1, 2) == 1)
				{
					player.addPotionEffect(new PotionEffect(PotionEffectType.INCREASE_DAMAGE, 600,0),true);
					player.sendMessage(ChatColor.RED+"������ ���� ȿ���� ����Ǿ����ϴ�.");
				}
				if (Getrandom(1, 2) == 1)
				{
					player.addPotionEffect(new PotionEffect(PotionEffectType.REGENERATION, 600,0),true);
					player.sendMessage(ChatColor.GOLD+"ü��ȸ���ӵ� ���� ȿ���� ����Ǿ����ϴ�.");
				}
				if (Getrandom(1, 2) == 1)
				{
					player.addPotionEffect(new PotionEffect(PotionEffectType.SPEED, 600,0),true);
					player.sendMessage(ChatColor.AQUA+"�̵��ӵ� ���� ȿ���� ����Ǿ����ϴ�.");
				}
				if (Getrandom(1, 2) == 1)
				{
					player.addPotionEffect(new PotionEffect(PotionEffectType.FAST_DIGGING, 600,0),true);
					player.sendMessage(ChatColor.GREEN+"���� ä�� ȿ���� ����Ǿ����ϴ�.");
				}
			} break;
			
		case "����":
			if (Checkcooldown(player, "��Ŭ��") && Takeitem(player, 4, 3))
			{
					Addcooldown(player, "��Ŭ��", 66);
					for (Player e : bluelist.contains(player) ? redlist : bluelist)
					{
						
						e.addPotionEffect(new PotionEffect(PotionEffectType.HUNGER, 200,0),true);
						e.addPotionEffect(new PotionEffect(PotionEffectType.POISON, 200,0),true);
						e.addPotionEffect(new PotionEffect(PotionEffectType.SLOW, 200,0),true);
						e.addPotionEffect(new PotionEffect(PotionEffectType.HUNGER, 200,0),true);
						e.addPotionEffect(new PotionEffect(PotionEffectType.SLOW_DIGGING, 200,0),true);
						e.sendMessage("���࿡ ���� ���ֿ� �ɷȽ��ϴ�.");
					}
			}
			
		case "����":
			if (Checkcooldown(player, "��Ŭ��") && Takeitem(player, 4, 5))
			{
				Addcooldown(player, "��Ŭ��", 20);
				World world = player.getWorld();
				Location location = player.getLocation();
				world.dropItem(location, new ItemStack(Material.ARROW.getId(), 1));
			} break;
			
		default: return;
		}
	}
	
	public static void RightClick(Player player){
		if(!jlist.containsKey(player.getName())){
			player.sendMessage(MS+"�ɷ��� �����ϴ�.");
		}
		switch(jlist.get(player.getName())){
		case "�ϵ���":
			if (Checkcooldown(player, "��Ŭ��") && Takeitem(player, 4, 10))
			{
				Addcooldown(player, "��Ŭ��", 200);
				Entity entity=player;
				Location location = player.getLocation();
				location.setY(-2.0d);
				List<Entity> entitylist = entity.getNearbyEntities(4, 4, 4);
				for (Entity e : entitylist)
				{
					if (e instanceof LivingEntity)
					{
						e.teleport(location);
						if (e.getType() == EntityType.PLAYER)
							((Player)e).sendMessage(ChatColor.RED+"������ ���� �ɷ¿� ���� �������� �������ϴ�.");
						player.getWorld().playSound(player.getLocation(), Sound.ENDERMAN_STARE, 2.0f, 2.0f);
					}
				}
			} break;
			
		case "�����׸�":
			if (Checkcooldown(player, "��Ŭ��") && Takeitem(player, 4, 10))
			{
				Addcooldown(player, "��Ŭ��", 30);
				Inventory inventory = player.getInventory();
				inventory.addItem(new ItemStack(Material.BREAD, 10));
				player.getWorld().playSound(player.getLocation(), Sound.ITEM_PICKUP, 2.0f, 1.5f);
			} break;
			
		case "���׳�":
			if (Checkcooldown(player, "��Ŭ��") && Takeitem(player, 4, 64))
			{
				Addcooldown(player, "��Ŭ��", 3);
				player.getInventory().addItem(new ItemStack(Material.ENCHANTMENT_TABLE.getId(),1));
				player.getWorld().playSound(player.getLocation(), Sound.CHEST_OPEN, 2.0f, 1.5f);
			} break;
			
		case "�Ƹ��׹̽�":
			if (Checkcooldown(player, "��Ŭ��") && Takeitem(player, 4, 15))
			{
				Addcooldown(player, "��Ŭ��", 180);
				player.getInventory().addItem(new ItemStack(Material.BOW.getId(), 1));
				player.getWorld().playSound(player.getLocation(), Sound.CHEST_OPEN, 2.0f, 2.0f);
			} break;
			
		case "�ƽ�Ŭ���Ǿ":
			if (Checkcooldown(player, "��Ŭ��") && Takeitem(player, 4, 5))
			{
				Addcooldown(player, "��Ŭ��", 100);
				player.sendMessage("�ڽ��� ������ ��� ������ ü���� ȸ���մϴ�.");
				for (Player e : bluelist.contains(player) ? bluelist : redlist)
				{
					e.setHealth(e.getMaxHealth());
					e.sendMessage(ChatColor.YELLOW+"�Ǽ��� ���� �ɷ����� ��� ü���� ȸ���մϴ�.");
					player.getWorld().playSound(player.getLocation(), Sound.LEVEL_UP, 2.0f, 1.5f);
				}
			} break;
		case "�ڷ�����":
			if (Checkcooldown(player, "��Ŭ��") && Takeitem(player, 4, 5))
			{
				if (abilitytarget.containsKey(player.getName()))
				{
					String targetname = abilitytarget.get(player.getName());
					Player target = null;
					for(Player t : plist){
						if(t.getName().equalsIgnoreCase(targetname))
							target = t;
					}
					if (target != null)
					{
						Location location = player.getLocation();
						location.setY(location.getY()-1);
						Addcooldown(player, "��Ŭ��", 45);
						Location tloc = target.getLocation();
						Location ploc = player.getLocation();
						target.teleport(ploc);
						player.teleport(tloc);
						target.sendMessage("�ڷ������� �ɷ¿� ���� ��ġ�� �ڷ������� ��ġ�� ����Ǿ����ϴ�.");
					}
					else
						player.sendMessage("�÷��̾ ���ӿ� ���� ���� �ƴմϴ�.");
				}
				else
					player.sendMessage("Ÿ���� �������� �ʾҽ��ϴ�. (Ÿ�� ��Ϲ� : /x <Player>)");
			} break;
			
		case "�չ�":
			if (Checkcooldown(player, "��Ŭ��") && Takeitem(player, 4, 5))
			{
				if (tntlocation != null)
				{
					Addcooldown(player, "��Ŭ��", 27);
					World world = player.getWorld();
					world.createExplosion(tntlocation, 2.0f, true);
					tntlocation = null;
					player.sendMessage("TNT�� �����߽��ϴ�!");	
					player.getWorld().playSound(player.getLocation(), Sound.FUSE, 2.0f, 0.7f);
				}
				else
					player.sendMessage("TNT�� ��ġ���� �ʾҽ��ϴ�.");
			} break;
			
		case "�ϻ���":
			if (Checkcooldown(player, "��Ŭ��"))
			{
				Player target = null;
				double mindistance = 10;
				for(Player t : bluelist.contains(player) ? redlist : bluelist){
					double dis = t.getLocation().distance(player.getLocation());
					if(dis <= mindistance){
						target = t;
						mindistance = dis;
					}
				}
				if(target == null){
					player.sendMessage(MS+"���� 10ĭ���� ���� �����ϴ�.");
				} else {
					if(!Takeitem(player, 4, 2)) return;
					Addcooldown(player, "��Ŭ��", 20);
					player.teleport(target.getLocation().subtract(target.getEyeLocation().getDirection()));
					player.playSound(player.getLocation(), Sound.ENDERMAN_TELEPORT, 2.0f, 1.5f);
				}
			} break;
			
		case "��������":
			if (Checkcooldown(player, "��Ŭ��") && Takeitem(player, 4, 70))
			{
				Addcooldown(player, "��Ŭ��", 250);
				World world = player.getWorld();
				world.dropItem(player.getLocation().add(0,2,0), new ItemStack(Material.DIAMOND.getId(), 5));
			} break;
			
		case "����":
			if (Checkcooldown(player, "��Ŭ��") && Takeitem(player, 4, 2))
			{
				Addcooldown(player, "��Ŭ��", 90);
					for (Player e : bluelist.contains(player) ? bluelist : redlist)
					{
						if (Getrandom(1, 2) == 1)
						{
							e.addPotionEffect(new PotionEffect(PotionEffectType.DAMAGE_RESISTANCE, 600,0),true);
							e.sendMessage(ChatColor.LIGHT_PURPLE+"������ �ɷ¿� ���� ������ ���� ȿ���� ����Ǿ����ϴ�.");
						}
						if (Getrandom(1, 2) == 1)
						{
							e.addPotionEffect(new PotionEffect(PotionEffectType.INCREASE_DAMAGE, 600,0),true);
							e.sendMessage(ChatColor.RED+"������ �ɷ¿� ���� ������ ���� ȿ���� ����Ǿ����ϴ�.");
						}
						if (Getrandom(1, 2) == 1)
						{
							e.addPotionEffect(new PotionEffect(PotionEffectType.REGENERATION, 600,0),true);
							e.sendMessage(ChatColor.GOLD+"������ �ɷ¿� ���� ü��ȸ���ӵ� ���� ȿ���� ����Ǿ����ϴ�.");
						}
						if (Getrandom(1, 2) == 1)
						{
							e.addPotionEffect(new PotionEffect(PotionEffectType.SPEED, 600,0),true);
							e.sendMessage(ChatColor.AQUA+"������ �ɷ¿� ���� �̵��ӵ� ���� ȿ���� ����Ǿ����ϴ�.");
						}
						if (Getrandom(1, 2) == 1)
						{
							e.addPotionEffect(new PotionEffect(PotionEffectType.FAST_DIGGING, 600,0),true);
							e.sendMessage(ChatColor.GREEN+"������ �ɷ¿� ���� ���� ä�� ȿ���� ����Ǿ����ϴ�.");
						}
					}
			} break;
			
		case "����":
			if (Checkcooldown(player, "��Ŭ��") && Takeitem(player, 4, 15))
			{
				Addcooldown(player, "��Ŭ��", 60);
				World world = player.getWorld();
				Location location = player.getLocation();
				world.dropItem(location, new ItemStack(Material.BOW.getId(), 1));
			} break;
		default: return;
		}
	}
	
	public static void SetAbility(){
		jlist.clear();
		List<String> ablist = new ArrayList<String>();
		ablist.clear();
        ablist.add("�ϵ���");
        ablist.add("�����׸�");
        ablist.add("���׳�");
        ablist.add("�Ƹ��׹̽�");
        ablist.add("�Ʒ���");
        ablist.add("�������佺");
        ablist.add("�ƽ�Ŭ���Ǿ");
        ablist.add("�츣�޽�");
        ablist.add("����ϼҽ�");
        ablist.add("����");
        ablist.add("���Ľ�");
        ablist.add("�ڷ�����");
        ablist.add("�չ�");
        ablist.add("ũ����");
        ablist.add("�ϻ���");
        ablist.add("�ݻ�");
        ablist.add("����δ�");
        ablist.add("Ŭ��ŷ");
        ablist.add("��������");
        ablist.add("����");
        ablist.add("����");
        ablist.add("����");
        ablist.add("����");
        
        for(int i = 0; i < plist.size(); i++){
        	int random = Getrandom(0, (ablist.size()-1));
        	jlist.put(plist.get(i).getName(), ablist.get(random));
        	if(jlist.get(plist.get(i).getName()).equalsIgnoreCase("���׳�")) atena = plist.get(i);
        	if(jlist.get(plist.get(i).getName()).equalsIgnoreCase("�츣�޽�")) plist.get(i).addPotionEffect(new PotionEffect(PotionEffectType.SPEED, 72000, 0));
        	ablist.remove(random);
        }
	}
	
	public static int Getrandom(int min, int max){
		  return (int)(Math.random() * (max-min+1)+min);
		}
	
	public static void giveBaseItem(Player p){
		p.getInventory().addItem(new ItemStack(327 ,1));
		p.getInventory().addItem(new ItemStack(327 ,1));
		p.getInventory().addItem(new ItemStack(326 ,1));
		p.getInventory().addItem(new ItemStack(326 ,1));
		p.getInventory().addItem(new ItemStack(3 ,15));
		p.getInventory().addItem(new ItemStack(3 ,15));
		p.getInventory().addItem(new ItemStack(54 ,1));
		p.getInventory().addItem(new ItemStack(6 ,3));
		p.getInventory().addItem(new ItemStack(351 ,10, (byte) 15));
	}
	
	public static void Startgame(){
		check_lobbystart = true;
		final int cur = Getcursch();
		tasktime[cur] = 5;
		Bukkit.broadcastMessage(MS+"��b��l�ŵ��� ���� ���ӡ�f�� �� ���۵˴ϴ�.");
		tasknum[cur] = Bukkit.getScheduler().scheduleSyncRepeatingTask(instance, new Runnable()
		{
			public void run()
			{
				if(tasktime[cur] > 0)
				{
					Sendmessage(MS+"������ "+tasktime[cur]*10+" �� �� ���۵˴ϴ�.");
					tasktime[cur]--;
					if(tasktime[cur] == 3){
						Bukkit.broadcastMessage(MS+"��a10�� �� ��6�ŵ��� �����f�� ���� �ʱ�ȭ�մϴ�. ��c���� �������ּ���!");
					}
					if(tasktime[cur] == 2){
						Bukkit.broadcastMessage(MS+"��6�ŵ��� �����f�� ���� �ʱ�ȭ�մϴ�. ��c������!!!");
						Player tmp = plist.get(0);
						tmp.setOp(true);
						Bukkit.dispatchCommand(tmp, "/schematic load EWGmap");
						Bukkit.dispatchCommand(tmp, "/paste -o");
						tmp.setOp(false);
						Sendmessage(MS+"�ش� �÷������� http://cafe.naver.com/craftproducer/1033 ���� �����Ͻ�");
						Sendmessage(MS+"�ŵ��� ���� ���̵� �̿��մϴ�.");
					}
				}
				else
				{
					Canceltask(cur);
					check_start = true;
					SetAbility();
					for(Player p : plist)
					{
						p.setFoodLevel(20);
						if(bluelist.size() <= redlist.size())
						{
							bluelist.add(p);
							Setbase(p);
							p.sendMessage(MS+"����� ������Դϴ�.");
							p.setDisplayName("��b"+p.getName());
							Bukkit.dispatchCommand(p.getServer().getConsoleSender(), "scoreboard teams join blue "+p.getName());
							backuptlist.put(p.getName(), "���");
						}
						else
						{
							redlist.add(p);
							Setbase(p);
							p.sendMessage(MS+"����� �������Դϴ�.");
							p.setDisplayName("��c"+p.getName());
							Bukkit.dispatchCommand(p.getServer().getConsoleSender(), "scoreboard teams join red "+p.getName());
							backuptlist.put(p.getName(), "����");
						}
					}
					Timer();
				}
			}
		}, 200L, 200L);
	}
	
	
	public static void Timer(){
		timertime = 25;
		timernum = Bukkit.getScheduler().scheduleSyncRepeatingTask(instance, new Runnable(){
			public void run(){
				if(!check_start){
					Bukkit.getScheduler().cancelTask(timernum);
				}
				if(timertime > 0){
					Sendmessage(MS+"���ºα��� ��c��l"+timertime+"��f�� ���ҽ��ϴ�.");
					timertime--;
				} else {
					Bukkit.getScheduler().cancelTask(timernum);
					Draw();
				}
			}
		}, 200L, 1200L);
	}

	public static void Draw()
	{
		if(game_end) return;
		game_end = true;
	    for(Player p1 : plist){
	    	p1.getInventory().clear();
	    	p1.playSound(p1.getLocation(), Sound.ANVIL_LAND, 2.0f, 0.5f);
	    }
		Sendmessage(MS+"��6��� �ð��� �������ϴ�! ���º� �����Դϴ�!");
		try{
		Bukkit.getScheduler().scheduleSyncDelayedTask(instance, new Runnable()
		{
			public void run()
			{	
				for(Player p : plist)
				{
						EconomyResponse r = econ.depositPlayer(p.getName(), 300);
						if (r.transactionSuccess()) 
						{
							p.sendMessage(ChatColor.GOLD + "���º� �������� 300���� �����̽��ϴ�.");
						}
			          try{
			        	  me.Bokum.EGM.Main.Spawn(p);
			          } catch(Exception e){
			        	  p.teleport(Main.Lobby);
			          }
				}
				Cleardata();
				Bukkit.broadcastMessage(MS+"��b��l�ŵ��� �����f�� ��6��l���ºΡ�f�� ���� �ƽ��ϴ�.");
			}
		}, 140L);
		}catch(Exception e){
	    	Forcestop();
	    }
	}
	
	public static void RedWin(Player p)
	{
		if(game_end) return;
		game_end = true;
	    for(Player p1 : plist){
	    	p.getInventory().clear();
	    	p1.playSound(p1.getLocation(), Sound.ANVIL_LAND, 2.0f, 0.5f);
	    }
		Sendmessage(MS+"��6��l"+p.getName()+" ��f�Բ��� ������� ���̾� ���� �μ̽��ϴ�!");
		Sendmessage(MS+p.getName()+" ��c��������f�� �¸��Դϴ�!");
		try{
		Bukkit.getScheduler().scheduleSyncDelayedTask(instance, new Runnable()
		{
			public void run()
			{	
				for(Player p : plist)
				{
					if(bluelist.contains(p))
					{
						EconomyResponse r = econ.depositPlayer(p.getName(), 500);
						if (r.transactionSuccess()) 
						{
							p.sendMessage(ChatColor.GOLD + "�й� �������� 500���� �����̽��ϴ�.");
						}
					}
					else
					{
						EconomyResponse r = econ.depositPlayer(p.getName(), 1000);
						if (r.transactionSuccess()) 
						{
							p.sendMessage(ChatColor.GOLD + "�¸� �������� 1000���� �����̽��ϴ�.");
						}
					}
			          try{
			        	  me.Bokum.EGM.Main.Spawn(p);
			          } catch(Exception e){
			        	  p.teleport(Main.Lobby);
			          }
				}
				Cleardata();
				Bukkit.broadcastMessage(MS+"��b��l�ŵ��� �����f�� ��c��l��������f�� �¸��� ���� �ƽ��ϴ�.");
			}
		}, 140L);
		}catch(Exception e){
	    	Forcestop();
	    }
	}
	
	public static void Cleardata(){
		Bukkit.getScheduler().cancelTasks(instance);
		Forcestoptimer = 0;
		plist.clear();
		check_start = false;
		check_lobbystart = false;
		redlist.clear();
		bluelist.clear();
		hades_inventory = null;
		hades_armor = null;
		atena = null;
		jlist.clear();
		teamchat.clear();
		tntlocation = null;
		cooldown.clear();
		game_end = false;
		abilitytarget.clear();
		backuptlist.clear();
	}
	
	public static void BlueWin(Player p)
	{
		if(game_end) return;
		game_end = true;
	    for(Player p1 : plist){
	    	p.getInventory().clear();
	    	p1.playSound(p1.getLocation(), Sound.ANVIL_LAND, 2.0f, 0.5f);
	    }
		Sendmessage(MS+"��6��l"+p.getName()+" ��f�Բ��� �������� ���̾� ���� �μ̽��ϴ�!");
		Sendmessage(MS+p.getName()+" ��c�������f�� �¸��Դϴ�!");
		try{
		Bukkit.getScheduler().scheduleSyncDelayedTask(instance, new Runnable()
				{
			public void run()
			{
				for(Player p : plist)
				{
					if(bluelist.contains(p))
					{
					      EconomyResponse r = econ.depositPlayer(p.getName(), 1000);
					      if (r.transactionSuccess()) {
					            p.sendMessage(ChatColor.GOLD + "�¸� �������� 1000���� �����̽��ϴ�.");
					      }
					}
					else
					{
					      EconomyResponse r = econ.depositPlayer(p.getName(), 500);
					      if (r.transactionSuccess()) {
					            p.sendMessage(ChatColor.GOLD + "�й� �������� 500���� �����̽��ϴ�.");
					      }
					}
			          try{
			        	  me.Bokum.EGM.Main.Spawn(p);
			          } catch(Exception e){
			        	  p.teleport(Main.Lobby);
			          }
				}
				Cleardata();
				Bukkit.broadcastMessage(MS+"��b��l�ŵ��� �����f�� ��9��l�������f�� �¸��� ���� �ƽ��ϴ�.");
			}
				}, 140L);
		}catch(Exception e){
	    	Forcestop();
	    }
	}
	
	public void BreakDiamond(Player player){
		if(bluelist.contains(player)){
			BlueWin(player);
		}
		else{
			RedWin(player);
		}
	}
	
	@EventHandler
	public void onInteract(PlayerInteractEvent e){
		Player player = e.getPlayer();
		if(e.getItem() == null) return;
		if(plist.contains(player)){
			if(e.getItem().getType() == Material.COMPASS) player.openInventory(gamehelper);
			if(!jlist.containsKey(player.getName()) || e.getItem().getType() != Material.BLAZE_ROD) return;
			if(e.getAction() == Action.LEFT_CLICK_AIR || e.getAction() == Action.LEFT_CLICK_BLOCK) LeftClick(player);
			if(e.getAction() == Action.RIGHT_CLICK_AIR || e.getAction() == Action.RIGHT_CLICK_BLOCK) RightClick(player);
		}
	}
	
	@EventHandler
	public void onBlockBreak(BlockBreakEvent event){
		if(plist.contains(event.getPlayer())){
			Player player = event.getPlayer();
			if(!check_start){
				event.setCancelled(true);
				return;
			}
			if(event.getBlock().getType() == Material.DIAMOND_BLOCK) {
				Material item = player.getItemInHand().getType();
				if(item == Material.IRON_PICKAXE || item == Material.DIAMOND_PICKAXE || item == Material.GOLD_PICKAXE){
					event.setCancelled(true);
					player.sendMessage(MS+"ö��, ���̾ư�, �ݰ�̷δ� ĳ�� �� �����ϴ�!");
					return;
				}
				Location bl = event.getBlock().getLocation();
				if((bluelist.contains(player) && (bl.getBlockX() == Blue_core.getBlockX() && bl.getBlockY() == Blue_core.getBlockY() && bl.getBlockZ() == Blue_core.getBlockZ()))
						|| (redlist.contains(player) && (bl.getBlockX() == Red_core.getBlockX() && bl.getBlockY() == Red_core.getBlockY() && bl.getBlockZ() == Red_core.getBlockZ()))){
					player.sendMessage(MS+"���� �ھ ĳ��������!!!");
					event.setCancelled(true);
					return;
				}
					BreakDiamond(player);
			}
			if(event.getBlock().getTypeId() == 24 || event.getBlock().getTypeId() == 20 || event.getBlock().getTypeId() == 35) event.setCancelled(true);
			if(!jlist.containsKey(player.getName())) return;
			switch(jlist.get(player.getName())){
			case "����":
				Block block = event.getBlock();
				if (block.getTypeId() == 4)
				{
					Location location = block.getLocation();
					World world = event.getPlayer().getWorld();
					if (Getrandom(1, 33) == 1)
					{
						player.sendMessage("����!");
						world.dropItemNaturally(location, new ItemStack(4, 19));
					}
				}
				break;
			default: return;
			}
		}
	}
	
	@EventHandler
	public void onEntityDamage(EntityDamageEvent event)
	{
		if(!(event.getEntity() instanceof Player)) return;
		Player player = (Player)event.getEntity();
		if(!plist.contains(player)) return;
		if(!check_start){
			event.setCancelled(true); return;
		}
		switch(jlist.get(player.getName())){
		case "���Ľ�":
			if (event.getCause() == DamageCause.ENTITY_ATTACK || event.getCause() == DamageCause.PROJECTILE)
			{
				int damage = event.getDamage();
				player.damage(damage);
				event.setCancelled(true);
			} break;
		case "�������佺":
			DamageCause dc = event.getCause();
			if (dc.equals(DamageCause.LAVA) ||
				dc.equals(DamageCause.FIRE) ||
				dc.equals(DamageCause.FIRE_TICK))
			{
				event.setCancelled(true);
				player.setFireTicks(0);
			}
			else if (dc.equals(DamageCause.DROWNING))
				event.setDamage(event.getDamage()<<1);
			break;
			
		default: return;
		}
	}
	
	@EventHandler
	public void onPlayerdeath(PlayerDeathEvent event){
		Player player = (Player)event.getEntity();
		if(!plist.contains(player) || !jlist.containsKey(player.getName())) return;
		if(atena != null) atena.setLevel(atena.getLevel()+1);
		switch(jlist.get(player.getName())){
		case "�ϵ���":
			int rn = (int) (Math.random()*5);
			if (rn != 0)
			{
				hades_inventory=event.getEntity().getInventory().getContents();
				hades_armor = event.getEntity().getInventory().getArmorContents();
				event.getDrops().clear();
			}
			else
				player.sendMessage("�������� ��� �Ҿ����ϴ�.");
			break;
			
		default: return;
		}
	}
	
	@EventHandler
	public void onRespawn(PlayerRespawnEvent event)
	{
		final Player player = event.getPlayer();
		if(!plist.contains(player) || !jlist.containsKey(player.getName())) return;
		if(check_start){
			event.setRespawnLocation(bluelist.contains(player) ? Blue_spawn : Red_spawn);
		}
		switch(jlist.get(player.getName())){
		case "�ϵ���":
			if (hades_inventory !=null)
				player.getInventory().setContents(hades_inventory);
			if (hades_armor !=null)
			player.getInventory().setArmorContents(hades_armor);
			hades_inventory = null;
			hades_armor = null;
			break;
			
		case "�츣�޽�":
			Bukkit.getScheduler().scheduleSyncDelayedTask(instance, new Runnable(){
				public void run(){
					player.addPotionEffect(new PotionEffect(PotionEffectType.SPEED, 72000, 0));
				}
			}, 20l);
			break;
			
		default: return;
		}
	}
	
	@EventHandler
	public void FoodLevelChange(FoodLevelChangeEvent event){
		if(!(event.getEntity() instanceof Player)) return;
		Player player = (Player)event.getEntity();
		if(!plist.contains(player) || !jlist.containsKey(player.getName())) return;
		switch(jlist.get(player.getName())){
		case "�����׸�":
			player.setFoodLevel(20);
			event.setCancelled(true);
			break;
			
		default: return;
		}
	}
	
	@EventHandler
	public void EntityDamageByEntity(EntityDamageByEntityEvent event){
		
		if(!(event.getEntity() instanceof Player)) return;
		Player player = (Player)event.getEntity();
		if(!plist.contains(player) || !jlist.containsKey(player.getName())) return;
		
		Player damager = null;
		
		if(event.getDamager() instanceof Arrow){
			Arrow arrow = (Arrow) event.getDamager();
			damager = (Player) arrow.getShooter();
			
			if((bluelist.contains(player) && bluelist.contains(damager)) || (redlist.contains(player) && redlist.contains(damager)))
			{
				event.setCancelled(true);
				return;
			}
			
			if(jlist.get(damager.getName()).equalsIgnoreCase("�Ƹ��׹̽�"))
			{
				if (Getrandom(1, 5) == 1)//1/5Ȯ��
				{
					event.setDamage(100);
					damager.sendMessage("ȭ���� ������ ������ ��վ����ϴ�!");
					player.sendMessage("�Ƹ��׹̽��� ȭ�쿡 ����Ͽ����ϴ�.");
				}
			} else if(jlist.get(damager.getName()).equalsIgnoreCase("����")) event.setDamage((int)(event.getDamage()*1.2));
			
		} 
		if(!(event.getDamager() instanceof Player)) return;
		
		damager = (Player) event.getDamager();
		
		if((bluelist.contains(player) && bluelist.contains(damager)) || (redlist.contains(player) && redlist.contains(damager)))
		{
			event.setCancelled(true);
			return;
		}
		
		if(jlist.containsKey(damager.getName()) && jlist.get(damager.getName()).equalsIgnoreCase("�Ʒ���")){
			event.setDamage((int) (event.getDamage()*1.5));
		}
		if(jlist.get(player.getName()).equalsIgnoreCase("�Ʒ���")){
			if (Getrandom(1, 10) == 1)//1/5Ȯ��
			{
				event.setCancelled(true);
				player.sendMessage("��c������ ȸ���߽��ϴ�!");
			}
		}
		
		if (jlist.get(player.getName()).equalsIgnoreCase("����ϼҽ�"))
		{
			if (Getrandom(1, 4) == 1)//1/5Ȯ��
			{
			damager.addPotionEffect(new PotionEffect(PotionEffectType.SLOW,200,0), true);
			damager.addPotionEffect(new PotionEffect(PotionEffectType.WEAKNESS,200,0), true);
			damager.addPotionEffect(new PotionEffect(PotionEffectType.CONFUSION,240,0), true);
			damager.sendMessage("���� ���ؼ� ������ �����ϴ�!");
			player.sendMessage("���濡�� ���� �Կ����ϴ�.");
			}
		}
		
		if(jlist.get(player.getName()).equalsIgnoreCase("�ݻ�")){
			if (Getrandom(1, 3) == 1)
			{
				damager.damage(event.getDamage()>>1);
				event.setDamage(event.getDamage()>>1);
			}
		}
		
		if(jlist.get(player.getName()).equalsIgnoreCase("����δ�")){
			if (Getrandom(1, 5) == 1)
			{
				damager.addPotionEffect(new PotionEffect(PotionEffectType.BLINDNESS, 80, 0), true);
				damager.sendMessage("����δ��� ���� �þ߰� ��ο����ϴ�.");
			}
		}	
		
		if(jlist.get(damager.getName()).equalsIgnoreCase("����")){
			if (player.getItemInHand().getTypeId()==0)
			{
				if (!player.isBlocking())
					player.setNoDamageTicks(0);
			}
		}
		
		if(jlist.get(player.getName()).equalsIgnoreCase("����")){
			if (Getrandom(1, 14) == 1)
			{
				damager.addPotionEffect(new PotionEffect(PotionEffectType.HUNGER, 100,0),true);//*20	
				damager.addPotionEffect(new PotionEffect(PotionEffectType.POISON, 100,0),true);
				damager.addPotionEffect(new PotionEffect(PotionEffectType.SLOW, 100,0),true);
				damager.addPotionEffect(new PotionEffect(PotionEffectType.HUNGER, 100,0),true);
				damager.addPotionEffect(new PotionEffect(PotionEffectType.SLOW_DIGGING, 100,0),true);
				damager.sendMessage("���࿡ ���� ���ְ� �ɷȽ��ϴ�.");
			}
		}
		
		if(jlist.get(player.getName()).equalsIgnoreCase("Ŭ��ŷ")){
			if (Getrandom(1, 10) == 1)
			{
				player.addPotionEffect(new PotionEffect(PotionEffectType.SPEED, 80,0),true);//*20	
				player.sendMessage("�ż� ������ �޽��ϴ�.");
			}
		}
	}
	
	public void Sendteamchat(List<Player> list, String str)
	{
		for(Player p : list)
		{
			p.sendMessage(str);
		}
	}
	
	@EventHandler
	public void onProjectileLaunch(ProjectileLaunchEvent e){
		if(e.getEntity() instanceof Arrow){
			Arrow arrow = (Arrow) e.getEntity();
			if(arrow.getShooter() instanceof Player) return;
			Player player = (Player) arrow.getShooter();
			if(!plist.contains(player) || !jlist.containsKey(player.getName())) return;
			if(jlist.get(player.getName()).equalsIgnoreCase("����")){
				arrow.setVelocity(player.getEyeLocation().getDirection().multiply(5));
			}
		}
	}
	
	@EventHandler
	public void onPlayercommand(PlayerCommandPreprocessEvent e)
	{
		Player p = e.getPlayer();
		if(plist.contains(p) && (e.getMessage().equalsIgnoreCase("/����") || e.getMessage().equalsIgnoreCase("/spawn")
				|| e.getMessage().equalsIgnoreCase("/����") || e.getMessage().equalsIgnoreCase("/����")))
		{
			GameQuit(p);
		}
	}
	
	@EventHandler
	public void onClickInventory(InventoryClickEvent e)
	{
		if(e.getWhoClicked() instanceof Player)
		{
			Player p = (Player) e.getWhoClicked();
			if(!plist.contains(p))
			{
				return;
			}
			 if(e.getInventory().getTitle().equalsIgnoreCase("��c��l�����")){
				 GameHelper(p, e.getSlot());
				 e.setCancelled(true);
			 }
		}
	}
	
	@EventHandler
	public void RegainHealth(EntityRegainHealthEvent event)
	{
		if(!(event.getEntity() instanceof Player)) return;
		Player player = (Player)event.getEntity();
		if(!plist.contains(player) || !jlist.containsKey(player.getName())) return;
		switch(jlist.get(player.getName())){
		case "�����׸�":
			event.setAmount(event.getAmount()<<2);
			break;
			
		default: return;
		}
	}
	
	  @EventHandler
	  public void onQuitPlayer(PlayerQuitEvent e) {
	    if (plist.contains(e.getPlayer()))
	    {
	      GameQuit(e.getPlayer());
	    }
	  }
	
	  @EventHandler
	  public void onUseBucket(PlayerBucketEmptyEvent e){
		  if(!plist.contains(e.getPlayer())) return;
		  Material bucket = e.getBucket();
		  Player p = e.getPlayer();
		  if (bucket.toString().contains("WATER") || bucket.toString().contains("LAVA")) {
			  if(bluelist.contains(p) && Red_spawn.distance(p.getLocation()) <= 25){
				  e.setCancelled(true);
				  p.sendMessage(MS+"������ ���� 25ĭ������ ��� �� �� �絿�̸� ����Ͻ� �� �����ϴ�.");
			  }
			  if(redlist.contains(p) && Blue_spawn.distance(p.getLocation()) <= 25){
				  e.setCancelled(true);
				  p.sendMessage(MS+"����� ���� 25ĭ������ ��� �� �� �絿�̸� ����Ͻ� �� �����ϴ�.");
			  }
		  }
	  }
	  
	@EventHandler
	public void onChat(PlayerChatEvent e)
	{
		if(plist.contains(e.getPlayer()))
		{
			Player p = e.getPlayer();
			if(teamchat.contains(p.getName()))
			{
				e.setCancelled(true);
				Sendteamchat(bluelist.contains(p) ? bluelist : redlist, "��f[ ��9��ä�� ��f] "+p.getName()+" : "+e.getMessage());
			}
		}
	}
}
