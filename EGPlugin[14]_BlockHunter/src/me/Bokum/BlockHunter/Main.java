package me.Bokum.BlockHunter;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Color;
import org.bukkit.FireworkEffect;
import org.bukkit.FireworkEffect.Type;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.block.Block;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Firework;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.player.PlayerCommandPreprocessEvent;
import org.bukkit.event.player.PlayerDropItemEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.event.player.PlayerRespawnEvent;
import org.bukkit.event.player.PlayerToggleSneakEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.FireworkMeta;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.plugin.RegisteredServiceProvider;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

import net.milkbowl.vault.economy.Economy;
import net.milkbowl.vault.economy.EconomyResponse;

public class Main extends JavaPlugin implements Listener
{
	public static String MS = "��f[ ��aEG ��f] ";
	public static Location Lobby;
	public static Location joinpos;
	public static Location startpos[] = new Location[3];
	public static Main instance;
	public static List<Player> backuplist = new ArrayList<Player>();
	public static List<Player> plist = new ArrayList<Player>();
	public static List<Player> blist = new ArrayList<Player>();
	public static List<Player> clist = new ArrayList<Player>();
	public static List<Location> changeloc = new ArrayList<Location>();
	public static List<String> cooldownlist = new ArrayList<String>();
	public static List<Material> blacklist = new ArrayList<Material>();
	public static HashMap<String, Byte> bytelist = new HashMap<String, Byte>();
	public static HashMap<String, Integer> blocklist = new HashMap<String, Integer>();
	public static int tasknum[] = new int[10];
	public static int tasktime[] = new int[10];
	public static int map = 1;
	public static ItemStack helpitem;
	public static ItemStack hint;
	public static Inventory gamehelper;
	public static Economy econ = null;
	public static boolean blocking = false;
	public static boolean game_end = false;
	public static boolean check_start = false;
	public static boolean check_lobbystart = false;
	public static int Forcestoptimer = 0;
	
	public void onEnable()
	{
		instance = this;
		getServer().getPluginManager().registerEvents(this, this);
		getLogger().info("�� ���ٲ��� �÷������� �ε� �Ǿ����ϴ�.");
		
		for(int i = 0; i < tasknum.length; i++){
			tasknum[i] = -5;
			tasktime[i] = -5;
		}
		
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
		
		List<String> lorelist = new ArrayList<String>();
		
		helpitem = new ItemStack(Material.WATCH, 1);
		ItemMeta meta1 = helpitem.getItemMeta();
		meta1.setDisplayName("��f[ ��6���� ����� ��f]");
		lorelist.clear();
		lorelist.add("��f- ��7��Ŭ���� ���� ����̸� ���ϴ�.");
		meta1.setLore(lorelist);
		helpitem.setItemMeta(meta1);
		
		hint = new ItemStack(Material.BOOK, 1);
		meta2 = hint.getItemMeta();
		meta2.setDisplayName("��ef[ ��e��Ʈ ��f]");
		lorelist.clear();
		lorelist.add("��f- ��7��Ŭ���� ��Ʈ�� ����մϴ�.");
		meta1.setLore(lorelist);
		hint.setItemMeta(meta2);
		
		
		blacklist.add(Material.AIR);
		blacklist.add(Material.SAPLING);
		blacklist.add(Material.LADDER);
		blacklist.add(Material.WATER);
		blacklist.add(Material.LAVA);
		blacklist.add(Material.WEB);
		blacklist.add(Material.DEAD_BUSH);
		blacklist.add(Material.LONG_GRASS);
		blacklist.add(Material.YELLOW_FLOWER);
		blacklist.add(Material.RED_ROSE);
		blacklist.add(Material.RED_MUSHROOM);
		blacklist.add(Material.BROWN_MUSHROOM);
		blacklist.add(Material.TORCH);
		blacklist.add(Material.FIRE);
		blacklist.add(Material.WATER);
		blacklist.add(Material.LEVER);
		blacklist.add(Material.REDSTONE_TORCH_OFF);
		blacklist.add(Material.REDSTONE_TORCH_ON);
		blacklist.add(Material.STONE_BUTTON);
		blacklist.add(Material.WOOD_BUTTON);
		blacklist.add(Material.SNOW);
		blacklist.add(Material.WATER);
		blacklist.add(Material.IRON_FENCE);
		blacklist.add(Material.THIN_GLASS);
		blacklist.add(Material.VINE);
		blacklist.add(Material.WATER_LILY);
		blacklist.add(Material.PORTAL);
		blacklist.add(Material.ENDER_PORTAL);
		blacklist.add(Material.ENDER_PORTAL_FRAME);
		blacklist.add(Material.TRIPWIRE_HOOK);
		blacklist.add(Material.HOPPER);
		blacklist.add(Material.BOWL);
		blacklist.add(Material.SEEDS);
		blacklist.add(Material.STRING);
		blacklist.add(Material.STATIONARY_WATER);
		blacklist.add(Material.STATIONARY_LAVA);
		blacklist.add(Material.PAINTING);
		blacklist.add(Material.SIGN);
		blacklist.add(Material.SIGN_POST);
		blacklist.add(Material.CAKE);
		blacklist.add(Material.BED);
		blacklist.add(Material.BED_BLOCK);
		blacklist.add(Material.CAULDRON);
		blacklist.add(Material.CAULDRON_ITEM);
		blacklist.add(Material.ITEM_FRAME);
		blacklist.add(Material.SKULL);
		blacklist.add(Material.WHEAT);
		blacklist.add(Material.SKULL_ITEM);
		blacklist.add(Material.LEAVES);
		blacklist.add(Material.STEP);
		blacklist.add(Material.WOOD_STEP);
		blacklist.add(Material.TRAP_DOOR);
		blacklist.add(Material.WHEAT);
		
		Loadconfig();
		
        if (!setupEconomy() ) {
            getLogger().info("[���� �߻� ��� ] Vault�÷������� �νĵ��� �ʾҽ��ϴ�!");
        }
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
    
	public void onDisable()
	{
		getLogger().info("�� ���ٲ��� �÷������� ��ε� �Ǿ����ϴ�.");
	}
	
	public void Loadconfig()
	{
	  try
	  {
		joinpos = new Location(Bukkit.getWorld(getConfig().getString("Join_world")), getConfig().getInt("Join_x"), getConfig().getInt("Join_y"), getConfig().getInt("Join_z"));
	    Lobby = new Location(Bukkit.getWorld(getConfig().getString("Lobby_world")), getConfig().getInt("Lobby_x"), getConfig().getInt("Lobby_y"), getConfig().getInt("Lobby_z"));
	    for(int i = 0; i < 3; i++){
	    	startpos[i] = new Location(Bukkit.getWorld(getConfig().getString("Start_world"+i)), getConfig().getInt("Start_x"+i), getConfig().getInt("Start_y"+i), getConfig().getInt("Start_z"+i));
	    }
	  }
	  catch (IllegalArgumentException e)
	  {
	    getLogger().info("���� ���� �Ǵ� �κ� �����Ǿ� ���� �ʽ��ϴ�");
	  }
	}
	
	public boolean onCommand(CommandSender talker, Command command, String str, String args[])
	{
		if(talker instanceof Player)
		{
			Player p = (Player) talker;
			if(str.equalsIgnoreCase("ebh") && p.isOp())
			{
				if(args.length <= 0)
				{
					Helpmessages(p);
					return true;
				}
				else
				{
					if(args[0].equalsIgnoreCase("set"))
					{
						Setloc(p, args);
						return true;
					}
					if(args[0].equalsIgnoreCase("join"))
					{
						GameJoin(p);
						return true;
					}
					if(args[0].equalsIgnoreCase("quit"))
					{
						GameQuit(p);
						return true;
					}
					if(args[0].equalsIgnoreCase("stop"))
					{
						Forcestop();
						return true;
					}
					if(args[0].equalsIgnoreCase("blocking"))
					{
						if(blocking){
							blocking = false;
						}else{
							blocking = true;
						}
						return true;
					}
					if(args[0].equalsIgnoreCase("test")){
						SpawnFireWork(p);
					}
				}
			}
		}
		return false;
	}
	
	public void Helpmessages(Player p)
	{
		p.sendMessage(MS+"/ebh join");
		p.sendMessage(MS+"/ebh quit");
		p.sendMessage(MS+"/ebh set");
		p.sendMessage(MS+"/ebh reload");
		p.sendMessage(MS+"/ebh inv");
	}
	
	public static void Forcestop()
	{
		Bukkit.broadcastMessage(MS+"�� ���ٲ����� ���� ���� �Ǿ����ϴ�.");
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
	
	public static void Cleardata()
	{
		Bukkit.getScheduler().cancelTasks(instance);
		Forcestoptimer = 0;
		for(Player t : backuplist){
			t.showPlayer(t);
		}
		plist.clear();
		blist.clear();
		clist.clear();
		blocklist.clear();
		bytelist.clear();
		game_end = false;
		check_start = false;
		check_lobbystart = false;
	}
	
	public void SpawnFireWork(Player p){
		Firework f = p.getWorld().spawn(p.getLocation(), Firework.class);
		FireworkMeta fm = f.getFireworkMeta();
		fm.addEffect(FireworkEffect.builder()
				.flicker(false)
				.trail(true)
				.with(Type.STAR)
				.withColor(Color.GREEN)
				.withFade(Color.BLUE)
				.build());
		fm.setPower(0);
		f.setFireworkMeta(fm);
	}
	
	public void Setloc(Player p, String[] args)
	{
		if(args.length <= 1)
		{
			p.sendMessage(MS+"/ebh set lobby");
			p.sendMessage(MS+"/ebh set join");
			p.sendMessage(MS+"/ebh set start");
			return;
		}
		if(args.length <= 2)
		{
			if(args[1].equalsIgnoreCase("lobby"))
			{
			    getConfig().set("Lobby_world", p.getLocation().getWorld().getName());
			    getConfig().set("Lobby_x", Integer.valueOf(p.getLocation().getBlockX()));
			    getConfig().set("Lobby_y", Integer.valueOf(p.getLocation().getBlockY() + 1));
			    getConfig().set("Lobby_z", Integer.valueOf(p.getLocation().getBlockZ()));
			    saveConfig();
			    Loadconfig();
				p.sendMessage(MS+"�κ� ���� �Ϸ�");
			}
			else if(args[1].equalsIgnoreCase("join"))
			{
			    getConfig().set("Join_world", p.getLocation().getWorld().getName());
			    getConfig().set("Join_x", Integer.valueOf(p.getLocation().getBlockX()));
			    getConfig().set("Join_y", Integer.valueOf(p.getLocation().getBlockY() + 1));
			    getConfig().set("Join_z", Integer.valueOf(p.getLocation().getBlockZ()));
			    saveConfig();
			    Loadconfig();
				p.sendMessage(MS+"���� ���� �Ϸ�");
			}
			else if(args[1].equalsIgnoreCase("start1"))
			{
			    getConfig().set("Start_world"+0, p.getLocation().getWorld().getName());
			    getConfig().set("Start_x"+0, Integer.valueOf(p.getLocation().getBlockX()));
			    getConfig().set("Start_y"+0, Integer.valueOf(p.getLocation().getBlockY() + 1));
			    getConfig().set("Start_z"+0, Integer.valueOf(p.getLocation().getBlockZ()));
			    saveConfig();
			    Loadconfig();
				p.sendMessage(MS+"1�������� ���� �Ϸ�");
			}
			else if(args[1].equalsIgnoreCase("start2"))
			{
			    getConfig().set("Start_world"+1, p.getLocation().getWorld().getName());
			    getConfig().set("Start_x"+1, Integer.valueOf(p.getLocation().getBlockX()));
			    getConfig().set("Start_y"+1, Integer.valueOf(p.getLocation().getBlockY() + 1));
			    getConfig().set("Start_z"+1, Integer.valueOf(p.getLocation().getBlockZ()));
			    saveConfig();
			    Loadconfig();
				p.sendMessage(MS+"2�������� ���� �Ϸ�");
			}
			else if(args[1].equalsIgnoreCase("start3"))
			{
			    getConfig().set("Start_world"+2, p.getLocation().getWorld().getName());
			    getConfig().set("Start_x"+2, Integer.valueOf(p.getLocation().getBlockX()));
			    getConfig().set("Start_y"+2, Integer.valueOf(p.getLocation().getBlockY() + 1));
			    getConfig().set("Start_z"+2, Integer.valueOf(p.getLocation().getBlockZ()));
			    saveConfig();
			    Loadconfig();
				p.sendMessage(MS+"3�������� ���� �Ϸ�");
			}
		}
	}
	
	public static void Sendmessage(String str)
	{
		for(Player p : plist)
		{
			p.sendMessage(str);
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
	
	public void useHint(Player p){
		Removeitem(p, 340, 1);
		Sendmessage(MS+p.getName()+" �Բ��� ��Ʈ�� ����Ͻʴϴ�.");
		for(Player t : blist){
			SpawnFireWork(t);
		}
	}
	
	public static void GameJoin(Player p)
	{
		if(blocking){
			p.sendMessage(Main.MS+"������");
			return;
		}
		if(plist.contains(p))
		{
			p.sendMessage(MS+"�̹� ���ӿ� �������̽ʴϴ�.");
			return;
		}
		if(plist.size() >= 10)
		{
			p.sendMessage(MS+"�̹� �ִ��ο�(10)�Դϴ�.");
			return;
		}
		if(check_start)
		{
			p.sendMessage(MS+"�̹� ������ �������Դϴ�. �����ο� : "+plist.size());
			return;
		}
		else
		{
			plist.add(p);
			p.teleport(joinpos);
			p.getInventory().clear();
			p.getInventory().setHelmet(null);
			p.getInventory().setChestplate(null);
			p.getInventory().setLeggings(null);
			p.getInventory().setBoots(null);
			p.getInventory().setItem(8, helpitem);
			me.Bokum.EGM.Main.gamelist.put(p.getName(), "�� ���ٲ���");
			Sendmessage(MS+p.getName()+" ���� �� ���ٲ����� �����ϼ̽��ϴ�. �ο� (��e "+plist.size()+"��7 / ��c5 ��f)");
			if(!check_lobbystart && plist.size() >= 5)
			{
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
		plist.remove(p);
		if(!check_start)
		{
			return;
		}
		if(blist.contains(p)){
			blist.remove(p);
			if(blist.size() <= 0)
			{
				CatcherWin();
			}
		}
		if(clist.contains(p)){
			clist.remove(p);
			if(clist.size() <= 0 && !game_end){
				Sendmessage(Main.MS+"��� ������ ���� Ÿ�̸Ӱ� 0�ʰ� �˴ϴ�.");
				RunnerWin();
			}
		}
		Sendmessage(MS+p.getName()+" ��f���� Ż���Ǽ̽��ϴ�.");
	}
	
	public static void RunnerWin()
	{
		game_end = true;
		for(Player p : plist){
			p.playSound(p.getLocation(), Sound.ANVIL_LAND, 2.5f, 1.5f);
		}
		Sendmessage(MS+"�ð��� �� �Ǿ����ϴ�! ���������� �¸��Դϴ�!");
		try{
		Bukkit.getScheduler().scheduleSyncDelayedTask(instance, new Runnable()
		{
			public void run()
			{
				for(Player p : blist){
					EconomyResponse r = econ.depositPlayer(p.getName(), 700);
					if (r.transactionSuccess()) {
						p.sendMessage(ChatColor.GOLD + "�¸� �������� 700���� �����̽��ϴ�.");
					}
					try{
						me.Bokum.EGM.Main.Spawn(p);
						} catch(Exception e){
						p.teleport(Main.Lobby);
						}
				}
				for(Player p : clist){
					EconomyResponse r = econ.depositPlayer(p.getName(), 300);
					if (r.transactionSuccess()) {
						p.sendMessage(ChatColor.GOLD + "�й� �������� 300���� �����̽��ϴ�.");
					}
					try{
						me.Bokum.EGM.Main.Spawn(p);
						} catch(Exception e){
						p.teleport(Main.Lobby);
						}
				}
				Cleardata();
				Bukkit.broadcastMessage(MS+"��a��l�� ���ٲ�����f�� ��9����������f�� �¸��� ���� �ƽ��ϴ�.");
			}
				}, 140L);
		}catch(Exception e){
	    	Forcestop();
	    }
	}
	
	public static void CatcherWin()
	{
		game_end = true;
		for(Player p : plist){
			p.playSound(p.getLocation(), Sound.ANVIL_LAND, 2.5f, 1.5f);
		}
		Sendmessage(MS+"��� �����ڰ� �������ϴ�! �������� �¸��Դϴ�!");
		try{
		Bukkit.getScheduler().scheduleSyncDelayedTask(instance, new Runnable()
		{
			public void run()
			{
				for(Player p : clist){
					EconomyResponse r = econ.depositPlayer(p.getName(), 800);
					if (r.transactionSuccess()) {
						p.sendMessage(ChatColor.GOLD + "�¸� �������� 800���� �����̽��ϴ�.");
					}
					try{
						me.Bokum.EGM.Main.Spawn(p);
						} catch(Exception e){
						p.teleport(Main.Lobby);
						}
				}
				Cleardata();
				Bukkit.broadcastMessage(MS+"��a��l�� ���ٲ�����f�� ��9��������f�� �¸��� ���� �ƽ��ϴ�.");
			}
				}, 140L);
		}catch(Exception e){
	    	Forcestop();
	    }
	}
	
	public static int Getrandom(int min, int max){
		  return (int)(Math.random() * (max-min+1)+min);
		}

	  public static void GameHelper(Player p, int slot){
		  switch(slot){
		  case 11:
			  p.sendMessage("��7���� �̸� ��f: ��c�� ���ٲ���");
			  p.sendMessage("��f������ �������� ���� ���� �̵��մϴ�.");
			  p.sendMessage("��b50�� �ġ�f���� ������ �����ڰ� �������ϴ�.");
			  p.sendMessage("��f������ �ð����� �����ڰ� ��Ƴ����� �¸��ϰ� �ǰ�");
			  p.sendMessage("��f������ �ð����� ��6������f�� ��2�����ڡ�f�� ��� ������ ������ �¸��մϴ�.");
			  p.sendMessage("��c��, �����ڴ� Shift�� ���� �ڽ��� �߾Ʒ� ������ ������ �� �ֽ��ϴ�.");
			  p.closeInventory();
			  return;
			  
		  case 13:
			  p.sendMessage("��f������ �Ӹ��� �� �� ������ ���� �ֽ��ϴ�.");
			  p.sendMessage("��f�����ڴ� ��� ������ ������ �� �ִ°� �ƴմϴ�.");
			  p.sendMessage("��f������ �� ������ �����ø� �ȵ˴ϴ�.");
			  p.closeInventory();
			  return;
			  
		  default: return;
		  }  
	  }
	  
		public static int Getcursch()
		{
			for(int i = 0 ; i < tasknum.length; i++)
			{
				if(tasknum[i] == -5)
				{
					return i;
				}
			}
			return 0;
		}
		
		
		public static void Canceltask(int cur)
		{
			Bukkit.getScheduler().cancelTask(tasknum[cur]);
			tasknum[cur] = -5;
			tasktime[cur] = -5;
		}
		
		public static void Startgame()
		{
			check_lobbystart = true;
			final int cur = Getcursch();
			tasktime[cur] = 5;
			Bukkit.broadcastMessage(MS+"��b��l�� ���ٲ�����f�� �� ���۵˴ϴ�.");
			tasknum[cur] = Bukkit.getScheduler().scheduleSyncRepeatingTask(instance, new Runnable()
			{
				public void run()
				{
					if(tasktime[cur] > 0)
					{
						Sendmessage(MS+"������ "+tasktime[cur]--*10+" �� �� ���۵˴ϴ�.");
					}
					else
					{
						Canceltask(cur);
						check_start = true;
						map = Getrandom(0, 2);
						for(Player p : plist){
							blist.add(p);
							p.getInventory().clear();
							p.playSound(p.getLocation(), Sound.ANVIL_LAND, 2.0f, 1.0f);
							p.sendMessage(MS+"������ ���� �ƽ��ϴ�.");
							p.teleport(startpos[map]);
							backuplist.add(p);
						}
						CatcherTimer();
						Timer();
					}
				}
			}, 200L, 200L);
		}
		
		public static void CatcherTimer(){
			final int cur = Getcursch();
			tasktime[cur] = 5;
			tasknum[cur] = Bukkit.getScheduler().scheduleSyncRepeatingTask(instance, new Runnable()
			{
				public void run()
				{
					if(tasktime[cur] > 0)
					{
						Sendmessage(MS+"������ "+tasktime[cur]--*10+" �� �� �������ϴ�.");
					}
					else
					{
						Canceltask(cur);
						check_start = true;
						if(plist.size() == 0){
							Forcestop(); return;
						}
						Player p = blist.get(Getrandom(0, (blist.size()-1)));
						p.teleport(startpos[map]);
						SetCatcher(p);
						p.getInventory().addItem(hint);
						if(blist.size() >= 6){
							p = blist.get(Getrandom(0, (blist.size()-1)));
							p.teleport(startpos[map]);
							SetCatcher(p);
						p.getInventory().addItem(hint);
						}
					}
				}
			}, 0L, 200L);
		}
		
		public static void SetCatcher(Player p){
			if(blist.contains(p)) blist.remove(p);
			clist.add(p);
			p.sendMessage(Main.MS+"������ �Ǽ̽��ϴ�.\n"+MS+"å ��Ŭ���� ��Ʈ�� ����մϴ�.");
			p.getInventory().clear();
			p.addPotionEffect(new PotionEffect(PotionEffectType.SPEED, 72000, 0, false));
			for(Player t : clist){
				t.showPlayer(p);
			}
			p.getInventory().addItem(new ItemStack(Material.DIAMOND_SWORD, 1));
			p.getInventory().addItem(hint);
			p.getInventory().setHelmet(new ItemStack(Material.JACK_O_LANTERN, 1));
			if(blist.size() <= 0)
			{
				CatcherWin();
			}
		}
		
		public static void Timer(){
			final int cur = Getcursch();
			tasktime[cur] = blist.size()*120+100;
			Sendmessage(Main.MS+"��c��n���� �ð��� ������ ǥ�õ˴ϴ�.");
			tasknum[cur] = Bukkit.getScheduler().scheduleSyncRepeatingTask(instance, new Runnable()
			{
				public void run()
				{
					if(game_end){
						Canceltask(cur);
					}
					if(--tasktime[cur] > 0)
					{
						for(Player p : plist){
							p.setLevel((int)(tasktime[cur]/2));
							for(Location l : changeloc){
								p.sendBlockChange(l, l.getBlock().getTypeId(), l.getBlock().getData());
							}
							for(Player t : blist){
								if(blocklist.containsKey(t.getName()) && !t.getName().equalsIgnoreCase(p.getName())){
									for(Player c : clist){
										c.hidePlayer(t);
									}
									p.sendBlockChange(t.getLocation(), blocklist.get(t.getName()), 
											bytelist.containsKey(t.getName()) ? bytelist.get(t.getName()) : (byte) 0);
								}
							}
						}
						changeloc.clear();
						for(Player t : blist){
							if(blocklist.containsKey(t.getName())){
								changeloc.add(new Location(t.getWorld(),t.getLocation().getBlockX(),
										t.getLocation().getBlockY(), t.getLocation().getBlockZ()));
							}
						}
					}
					else
					{
						Canceltask(cur);
						RunnerWin();
					}
				}
			}, 0L, 10L);
		}
		
		@EventHandler
		public void onRightClick(PlayerInteractEvent e)
		{
			if(e.getAction() == Action.RIGHT_CLICK_BLOCK || e.getAction() == Action.RIGHT_CLICK_AIR)
			{
				final Player p = e.getPlayer();
				if(!plist.contains(p) || e.getItem() == null) return;
				if(e.getItem().getType() == Material.WATCH) p.openInventory(gamehelper);
				if(e.getItem().getType() == Material.BOOK) useHint(p);
			}
			if(e.getAction() == Action.LEFT_CLICK_BLOCK || e.getAction() == Action.RIGHT_CLICK_BLOCK){
				if(!plist.contains(e.getPlayer()) || e.getClickedBlock() == null) return;
				Block b = e.getClickedBlock();
				Location bl = new Location(b.getWorld(), b.getLocation().getBlockX(),b.getLocation().getBlockY(),b.getLocation().getBlockZ());
				try{
					for(String s : blocklist.keySet()){
						Player p = null;
						try{
							p = Bukkit.getPlayer(s);
						}catch(Exception excep){
							
						}
						if(p == null) continue;
						Location pl = new Location(p.getWorld(), p.getLocation().getBlockX(), p.getLocation().getBlockY(), p.getLocation().getBlockZ());
						if(bl.equals(pl)){
							if(blocklist.containsKey(s)){
								blocklist.remove(p.getName());
								bytelist.remove(p.getName());
								p.getInventory().setHelmet(null);
								p.sendMessage(Main.MS+"������ Ǯ�Ƚ��ϴ�.");
								for(Player t : clist){
									t.showPlayer(p);
								}
								p.damage(1);
							}
						}
					}
				}catch(Exception exception){
						
				}
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
				 if(e.getCurrentItem() != null && e.getCurrentItem().getType() == Material.JACK_O_LANTERN){
					 e.setCancelled(true);
					 p.updateInventory();
				 }
				 if(blocklist.containsKey(p.getName())){
					 e.setCancelled(true);
					 p.updateInventory();
				 }
			}
		}
		
		@EventHandler
		public void onPlayerDamage(EntityDamageEvent e){
			if(!(e.getEntity() instanceof Player)) return;
			if(plist.contains((Player) e.getEntity()) && (!check_start || game_end)) e.setCancelled(true);
		}
		
		@EventHandler
		public void onItemDrop(PlayerDropItemEvent e){
			if(!plist.contains(e.getPlayer())) return;
			e.setCancelled(true);
		}
		
		@EventHandler
		public void onPlayercommand(PlayerCommandPreprocessEvent e)
		{
			Player p = e.getPlayer();
			if(plist.contains(p) && e.getMessage().equalsIgnoreCase("/����") || e.getMessage().equalsIgnoreCase("/spawn")
					|| e.getMessage().equalsIgnoreCase("/����") || e.getMessage().equalsIgnoreCase("/����"))
			{
				GameQuit(p);
			}
		}
		
		@EventHandler
		public void onToggleSneak(PlayerToggleSneakEvent e){
			  if(plist.contains(e.getPlayer())){
				  final Player p = e.getPlayer();
				  /*if(blist.contains(p) && !p.isSneaking()){
					  Bukkit.getScheduler().scheduleSyncDelayedTask(instance, new Runnable(){
						  public void run(){
							  p.setExp(0.33f);
							  p.playSound(p.getLocation(), Sound.NOTE_PLING, 1.0f, 1.0f);
							  p.sendMessage(Main.MS+"3�� �� ���ŵ˴ϴ�.");
						  }
					  }, 0l);
					  Bukkit.getScheduler().scheduleSyncDelayedTask(instance, new Runnable(){
						  public void run(){
							  p.setExp(0.33f);
							  p.playSound(p.getLocation(), Sound.NOTE_PLING, 1.0f, 1.0f);
							  p.sendMessage(Main.MS+"3�� �� ���ŵ˴ϴ�.");
						  }
					  }, 20l);
					  Bukkit.getScheduler().scheduleSyncDelayedTask(instance, new Runnable(){
						  public void run(){
							  p.setExp(0.33f);
							  p.playSound(p.getLocation(), Sound.NOTE_PLING, 1.0f, 1.0f);
							  p.sendMessage(Main.MS+"3�� �� ���ŵ˴ϴ�.");
						  }
					  }, 40l);*/
				  if(clist.contains(p) || !check_start) return;
				  if(!p.isSneaking()){
						  Location l = new Location(p.getWorld(), p.getLocation().getBlockX(), p.getLocation().getBlockY()-1
								  ,p.getLocation().getBlockZ());
						  if(!blacklist.contains(l.getBlock().getType())){
							  blocklist.put(p.getName(), l.getBlock().getTypeId());
							  bytelist.put(p.getName(), l.getBlock().getData());
							  p.getInventory().setHelmet(new ItemStack(l.getBlock().getType(), 1));
							  p.playSound(p.getLocation(), Sound.NOTE_PLING, 1.0f, 1.0f);
							  p.sendMessage(Main.MS+blocklist.get(p.getName())+":"+bytelist.get(p.getName())+" �ڵ� ������ �����߽��ϴ�.");
							  p.playSound(p.getLocation(), Sound.CLICK, 1.0f, 1.0f);
						  }else{
							  p.sendMessage(Main.MS+"�� �����δ� �����Ͻ� �� �����ϴ�.");
						  }
				  }else{
						if(blocklist.containsKey(p.getName())){
							blocklist.remove(p.getName());
							bytelist.remove(p.getName());
							p.getInventory().setHelmet(null);
							p.sendMessage(Main.MS+"������ Ǯ�Ƚ��ϴ�.");
							for(Player t : clist){
								t.showPlayer(p);
							}
						}
				 // }
				  }
			  }
		}
		
		@EventHandler
		public void onBlockbreak(BlockBreakEvent e){
			  if(plist.contains(e.getPlayer())){
				  e.setCancelled(true);
			  }
		}
		
		@EventHandler
		public void onRespawn(PlayerRespawnEvent e){
			if(plist.contains(e.getPlayer())){
				final Player p = e.getPlayer();
				e.setRespawnLocation(startpos[map]);
				if(blist.contains(p) && clist.size() >= 1){
					Bukkit.getScheduler().scheduleSyncDelayedTask(instance, new Runnable(){
						public void run(){
							SetCatcher(p);
						}
					}, 10l);
				}
			}
		}
		  
		@EventHandler
		public void onBlockPlace(BlockPlaceEvent e){
			  if(plist.contains(e.getPlayer()))
				  e.setCancelled(true);
		}
		
		  @EventHandler
		  public void onQuitPlayer(PlayerQuitEvent e) {
		    if (plist.contains(e.getPlayer()))
		    {
		      GameQuit(e.getPlayer());
		    }
		  }
		  
			@EventHandler
			public void onPlayerdeath(PlayerDeathEvent e)
			{
				Player p = e.getEntity();
				if(!plist.contains(p)) return;
				e.getDrops().clear();
				e.setDroppedExp(0);
				if(!blist.contains(p)){
					GameQuit(p);
				}
			}

			@EventHandler
			public void onPvp(EntityDamageByEntityEvent e){
				if(e.getEntity() instanceof Player && e.getDamager() instanceof Player){
					Player p = (Player) e.getEntity();
					Player d = (Player) e.getDamager();
					if(!plist.contains(p)) return;
					if((clist.contains(p) && clist.contains(d)) || (blist.contains(p) && blist.contains(d))){
						e.setCancelled(true);return;
					}
				}
			}
}

