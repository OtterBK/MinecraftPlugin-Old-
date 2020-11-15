package me.Bokum.ESP;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.ConfigurationSection;
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
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
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
	public static Main instance;
	public static List<Player> plist = new ArrayList<Player>();
	public static List<String> cooldownlist = new ArrayList<String>();
	public static Location startpos[] = new Location[10];
	public static int tasknum[] = new int[10];
	public static int tasktime[] = new int[10];
	public static ItemStack helpitem;
	public static Inventory gamehelper;
	public static Economy econ = null;
	public static List<Location> blockloc = new ArrayList<Location>(300);
	public static boolean check_start = false;
	public static boolean check_lobbystart = false;
	public static int Forcestoptimer = 0;
	
	public void onEnable()
	{
		instance = this;
		getServer().getPluginManager().registerEvents(this, this);
		getLogger().info("���ø��� �÷������� �ε� �Ǿ����ϴ�.");
		
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
		
		helpitem = new ItemStack(345, 1);
		ItemMeta meta1 = helpitem.getItemMeta();
		meta1.setDisplayName("��f[ ��6���� ����� ��f]");
		lorelist.clear();
		lorelist.add("��f- ��7��Ŭ���� ���� ����� ���ϴ�.");
		meta1.setLore(lorelist);
		helpitem.setItemMeta(meta1);
		
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
		getLogger().info("���ø��� �÷������� ��ε� �Ǿ����ϴ�.");
	}
	
	public void Loadconfig()
	{
	  try
	  {
		joinpos = new Location(Bukkit.getWorld(getConfig().getString("Join_world")), getConfig().getInt("Join_x"), getConfig().getInt("Join_y"), getConfig().getInt("Join_z"));
	    Lobby = new Location(Bukkit.getWorld(getConfig().getString("Lobby_world")), getConfig().getInt("Lobby_x"), getConfig().getInt("Lobby_y"), getConfig().getInt("Lobby_z"));
	  }
	  catch (IllegalArgumentException e)
	  {
	    getLogger().info("���� ���� �Ǵ� �κ� �����Ǿ� ���� �ʽ��ϴ�");
	  }
		ConfigurationSection sec = getConfig();
		try
		{
			for(int j = 1; j <= 10; j++){
				  startpos[j-1]= new Location(Bukkit.getWorld(sec.getString("start_world"+j)), sec.getInt("start_x"+j), sec.getInt("start_y"+j), sec.getInt("start_z"+j));
			}
		}
		catch (Exception e)
		{
			 getLogger().info("���� ������ �Ϻ��� ���� �Ǿ����� �ʽ��ϴ�. ���� �߻��� ����� �ֽ��ϴ�.");
		}
		try
		{
			for(int j = 1; j <= getConfig().getInt("blockamt"); j++){
				  blockloc.add(new Location(getServer().getWorld("world"), getConfig().getInt("block_loc_"+j+"_x"), getConfig().getInt("block_loc_"+j+"_y"), getConfig().getInt("block_loc_"+j+"_z")));
			}
		}
		catch (Exception e)
		{
			 getLogger().info("���� ������ �Ϻ��� ���� �Ǿ����� �ʽ��ϴ�. ���� �߻��� ����� �ֽ��ϴ�.");
		}
	}
	
	public boolean onCommand(CommandSender talker, Command command, String str, String args[])
	{
		if(talker instanceof Player)
		{
			Player p = (Player) talker;
			if(str.equalsIgnoreCase("esp") && p.isOp())
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
					if(args[0].equalsIgnoreCase("test"))
					{
						p.teleport(startpos[Integer.valueOf(args[1])]);
						return true;
					}
					if(args[0].equalsIgnoreCase("block"))
					{
						SaveBlock(p, args[1], args[2], args[3], args[4], args[5], args[6]);
						return true;
					}
					if(args[0].equalsIgnoreCase("restore"))
					{
						RestoreBlock();
						return true;
					}
				}
			}
		}
		return false;
	}
	
	public void SaveBlock(Player p, String x1, String y1, String z1, String x2, String y2, String z2){
		blockloc.clear();
		int amt = 0;
		Location pos1 = new Location(getServer().getWorld("world"), Integer.valueOf(x1), Integer.valueOf(y1), Integer.valueOf(z1));
		Location pos2 = new Location(getServer().getWorld("world"), Integer.valueOf(x2), Integer.valueOf(y2), Integer.valueOf(z2));
		for(int x = pos1.getBlockX(); x <= pos2.getBlockX(); x++){
			for(int y = pos1.getBlockY(); y <= pos2.getBlockY(); y++){
				for(int z = pos1.getBlockZ(); z <= pos2.getBlockZ(); z++){
					Location block_loc = new Location(getServer().getWorld("world"), Integer.valueOf(x), Integer.valueOf(y), Integer.valueOf(z));
					if(block_loc.getBlock().getType() == Material.SNOW_BLOCK){
						amt++;
						getConfig().set("block_loc_"+amt+"_x", block_loc.getBlockX());
						getConfig().set("block_loc_"+amt+"_y", block_loc.getBlockY());
						getConfig().set("block_loc_"+amt+"_z", block_loc.getBlockZ());			
						blockloc.add(block_loc);
					}
				}
			}
		}
		getConfig().set("blockamt", amt);
		saveConfig();
		Loadconfig();
		p.sendMessage(MS+"�����Ϸ�");
	}
	
	public static void RestoreBlock(){
		for(Location loc : blockloc){
			loc.getBlock().setType(Material.SNOW_BLOCK);
		}
	}
	
	public void Helpmessages(Player p)
	{
		p.sendMessage(MS+"/esp join");
		p.sendMessage(MS+"/esp quit");
		p.sendMessage(MS+"/esp set");
		p.sendMessage(MS+"/esp reload");
		p.sendMessage(MS+"/esp inv");
	}
	
	public static void Forcestop()
	{
		Bukkit.broadcastMessage(MS+"���ø����� ���� ���� �Ǿ����ϴ�.");
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
		plist.clear();
		check_start = false;
		cooldownlist.clear();
		check_lobbystart = false;
	}
	
	public void Setloc(Player p, String[] args)
	{
		if(args.length <= 1)
		{
			p.sendMessage(MS+"/esp set lobby");
			p.sendMessage(MS+"/esp set join");
			p.sendMessage(MS+"/esp set start 1~15");
			p.sendMessage(MS+"/esp set box 1~15");
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
			else
			{
				p.sendMessage(MS+"/esp set start 1~15");
			}
			return;
		}
		else if(args.length >= 3)
		{
			if(args[1].equalsIgnoreCase("start"))
			{
					int i = Integer.valueOf(args[2]);
					if(!(1 <= i && i <= 10))
					{
						p.sendMessage("1~10�� ���ڸ� �Է� �����մϴ�.");
						return;
					}
						getConfig().set("start_world"+i, p.getWorld().getName());
						getConfig().set("start_x"+i, p.getLocation().getBlockX());
						getConfig().set("start_y"+i, p.getLocation().getBlockY());
						getConfig().set("start_z"+i, p.getLocation().getBlockZ());
					    saveConfig();
					    Loadconfig();
						p.sendMessage(MS+i+"��° ���� ���� ���� �Ϸ�");
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
	
	public static void GameJoin(Player p)
	{
		if(plist.contains(p))
		{
			p.sendMessage(MS+"�̹� ���ӿ� �������̽ʴϴ�.");
			return;
		}
		if(plist.size() >= 7)
		{
			p.sendMessage(MS+"�̹� �ִ��ο�(7)�Դϴ�.");
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
			me.Bokum.EGM.Main.gamelist.put(p.getName(), "���ø���");
			Sendmessage(MS+p.getName()+" ���� ���ø����� �����ϼ̽��ϴ�. �ο� (��e "+plist.size()+"��7 / ��c3 ��f)");
			if(!check_lobbystart && plist.size() >= 3)
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
		Sendmessage(MS+p.getName()+" ��f���� Ż���Ǽ̽��ϴ�.");
		if(plist.size() <= 1)
		{
			try{
				Win(plist.get(0));
			}catch(Exception e){
				
			}
		}
	}
	
	public static void Win(final Player p)
	{
		p.playSound(p.getLocation(), Sound.ANVIL_LAND, 2.5f, 1.5f);
		Sendmessage(MS+"����� ���ķ� ���ҽ��ϴ�!");
		try{
		Bukkit.getScheduler().scheduleSyncDelayedTask(instance, new Runnable()
		{
			public void run()
			{
				EconomyResponse r = econ.depositPlayer(p.getName(), 400);
				if (r.transactionSuccess()) {
					p.sendMessage(ChatColor.GOLD + "�¸� �������� 500���� �����̽��ϴ�.");
				}
				try{
					me.Bokum.EGM.Main.Spawn(p);
					} catch(Exception e){
					p.teleport(Main.Lobby);
					}
				Cleardata();
				Bukkit.broadcastMessage(MS+"��b��l���ø�����f�� ��9"+p.getName()+"��f���� �¸��� ���� �ƽ��ϴ�.");
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
			  p.sendMessage("��7���� �̸� ��f: ��c���ø���");
			  p.sendMessage("��f������ ���۵Ǹ� ��� �÷��̾�� ���� �����մϴ�.");
			  p.sendMessage("��f���� �̿��Ͽ� ������ ĳ��");
			  p.sendMessage("��f�ٸ� �÷��̾ ����߸��� ���������� ������");
			  p.sendMessage("��f�¸� �ϰԵ˴ϴ�.");
			  p.closeInventory();
			  return;
			  
		  case 13:
			  p.sendMessage("��f���� �������� �ʽ��ϴ�.");
			  p.sendMessage("��f���� ���븦 ��Ŭ�� �� �÷��� ������ �մϴ�.");
			  p.sendMessage("��f���� ���� �� ��ҿ� ��� �ӹ��� ���� �ȵ˴ϴ�.");
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
			Bukkit.broadcastMessage(MS+"��b��l���ø�����f�� �� ���۵˴ϴ�.");
			tasknum[cur] = Bukkit.getScheduler().scheduleSyncRepeatingTask(instance, new Runnable()
			{
				public void run()
				{
					if(tasktime[cur] > 0)
					{
						Sendmessage(MS+"������ "+tasktime[cur]*10+" �� �� ���۵˴ϴ�.");
						tasktime[cur]--;
						if(tasktime[cur] == 3){
							Bukkit.broadcastMessage(MS+"��a10�� �� ��b���ø�����f�� ���� �ʱ�ȭ�մϴ�. ��c���� �������ּ���!");
						}
						if(tasktime[cur] == 2){
							Bukkit.broadcastMessage(MS+"��b���ø�����f�� ���� �ʱ�ȭ�մϴ�. ��c������!!!");
							RestoreBlock();
						}
					}
					else
					{
						Canceltask(cur);
						check_start = true;
						for(int i = 0; i < plist.size(); i++){
							Player p = plist.get(i);
							p.getInventory().clear();
							p.playSound(p.getLocation(), Sound.ANVIL_LAND, 2.0f, 1.0f);
							p.sendMessage(MS+"������ ���� �ƽ��ϴ�.");
							p.teleport(startpos[i]);
							p.addPotionEffect(new PotionEffect(PotionEffectType.SPEED, 72000, 1));
							p.getInventory().addItem(new ItemStack(Material.DIAMOND_SPADE, 1));
							p.getInventory().addItem(new ItemStack(Material.STICK, 1));
						}
					}
				}
			}, 200L, 200L);
		}
		
		@EventHandler
		public void onOpenChestBlock(PlayerInteractEvent e)
		{
			if(e.getAction() == Action.RIGHT_CLICK_BLOCK || e.getAction() == Action.RIGHT_CLICK_AIR)
			{
				final Player p = e.getPlayer();
				if(!plist.contains(p) || e.getItem() == null) return;
				if(e.getItem().getType() == Material.COMPASS)
						  p.openInventory(gamehelper);
				if(e.getItem().getType() == Material.STICK && !cooldownlist.contains(p.getName())){
					cooldownlist.add(p.getName());
				      p.setVelocity(p.getLocation().getDirection().multiply(0.8D).setY(0.7D));
				      p.getWorld().playSound(p.getLocation(), Sound.BAT_TAKEOFF, 2.0f, 0.8f);
				      Bukkit.getScheduler().scheduleSyncDelayedTask(this, new Runnable(){
				    	  public void run(){
				    		  cooldownlist.remove(p.getName());
				    		  p.sendMessage(MS+"�÷��� ������ ��ٿ��� �������ϴ�.");
				    	  }
				      }, 100L);
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
			}
		}
		
		@EventHandler
		public void onPlayerDamage(EntityDamageEvent e){
			if(!(e.getEntity() instanceof Player)) return;
			if(plist.contains((Player) e.getEntity()) && !check_start) e.setCancelled(true);
		}
		
		@EventHandler
		public void onItemDrop(PlayerDropItemEvent e){
			if(!plist.contains(e.getPlayer())) return;
			if(e.getItemDrop().getItemStack().getType() == Material.COMPASS || e.getItemDrop().getItemStack().getType() == Material.DIAMOND_SPADE) e.setCancelled(true);
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
		public void onBlockbreak(BlockBreakEvent e){
			  if(plist.contains(e.getPlayer())){
				  if(!check_start) e.setCancelled(true);
				  if(e.getBlock().getType() != Material.SNOW_BLOCK){
						e.setCancelled(true);
				  } else {
					  e.getBlock().setType(Material.AIR);
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
				if(plist.contains(p))
				{
					GameQuit(p);
				}
			}
			
			@EventHandler
			public void onPvp(EntityDamageByEntityEvent e){
				if(e.getEntity() instanceof Player && e.getDamager() instanceof Player){
					Player p = (Player) e.getEntity();
					Player d = (Player) e.getDamager();
					if(plist.contains(p) && plist.contains(d)){
						e.setCancelled(true);
					}
				}
			}
}
