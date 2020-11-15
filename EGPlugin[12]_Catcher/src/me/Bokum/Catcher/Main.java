package me.Bokum.Catcher;

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
import org.bukkit.event.entity.EntityDamageEvent.DamageCause;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.player.PlayerCommandPreprocessEvent;
import org.bukkit.event.player.PlayerDropItemEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.event.player.PlayerToggleSneakEvent;
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
	public static Location startpos[][] = new Location[3][10];
	public static int timer_start_num = 0;
	public static int timer_start_time = 0;
	public static int timer_game_num = 0;
	public static int timer_game_time = 0;
	public static int map = 0;
	public static ItemStack helpitem;
	public static Inventory gamehelper;
	public static Player catcher = null;
	public static Economy econ = null;
	public static boolean check_start = false;
	public static boolean check_lobbystart = false;
	public static int Forcestoptimer = 0;
	
	public void onEnable()
	{
		instance = this;
		getServer().getPluginManager().registerEvents(this, this);
		getLogger().info("��������÷������� �ε� �Ǿ����ϴ�.");
		
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
		getLogger().info("������� �÷������� ��ε� �Ǿ����ϴ�.");
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
		ConfigurationSection sec = getConfig().getConfigurationSection("map1");
		try
		{
			for(int j = 1; j <= 10; j++){
				  startpos[0][j-1]= new Location(Bukkit.getWorld(sec.getString("start_world"+j)), sec.getInt("start_x"+j), sec.getInt("start_y"+j)+2, sec.getInt("start_z"+j));
			}
		}
		catch (Exception e)
		{
			 getLogger().info("�� 1�� ���� ������ �Ϻ��� ���� �Ǿ����� �ʽ��ϴ�. ���� �߻��� ����� �ֽ��ϴ�.");
		}
		sec = getConfig().getConfigurationSection("map2");
		try
		{
			for(int j = 1; j <= 10; j++){
				  startpos[1][j-1]= new Location(Bukkit.getWorld(sec.getString("start_world"+j)), sec.getInt("start_x"+j), sec.getInt("start_y"+j)+2, sec.getInt("start_z"+j));
			}
		}
		catch (Exception e)
		{
			 getLogger().info("�� 2�� ���� ������ �Ϻ��� ���� �Ǿ����� �ʽ��ϴ�. ���� �߻��� ����� �ֽ��ϴ�.");
		}
		sec = getConfig().getConfigurationSection("map3");
		try
		{
			for(int j = 1; j <= 10; j++){
				  startpos[2][j-1]= new Location(Bukkit.getWorld(sec.getString("start_world"+j)), sec.getInt("start_x"+j), sec.getInt("start_y"+j)+2, sec.getInt("start_z"+j));
			}
		}
		catch (Exception e)
		{
			 getLogger().info("�� 3�� ���� ������ �Ϻ��� ���� �Ǿ����� �ʽ��ϴ�. ���� �߻��� ����� �ֽ��ϴ�.");
		}
	}
	
	public boolean onCommand(CommandSender talker, Command command, String str, String args[])
	{
		if(talker instanceof Player)
		{
			Player p = (Player) talker;
			if(str.equalsIgnoreCase("ecc") && p.isOp())
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
				}
			}
		}
		return false;
	}
	
	public void Helpmessages(Player p)
	{
		p.sendMessage(MS+"/ecc join");
		p.sendMessage(MS+"/ecc quit");
		p.sendMessage(MS+"/ecc set");
		p.sendMessage(MS+"/ecc reload");
		p.sendMessage(MS+"/ecc inv");
	}
	
	public static void Forcestop()
	{
		Bukkit.broadcastMessage(MS+"�±ر� ������Ⱑ ���� ���� �Ǿ����ϴ�.");
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
		check_lobbystart = false;
	}
	
	public void Setloc(Player p, String[] args)
	{
		if(args.length <= 1)
		{
			p.sendMessage(MS+"/ecc set lobby");
			p.sendMessage(MS+"/ecc set join");
			p.sendMessage(MS+"/ecc set ma<1~3> start 1~8");
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
				p.sendMessage(MS+"/ecc set map<1~3> start 1~10");
			}
			return;
		}
		else if(args.length >= 4)
		{
			if(args[2].equalsIgnoreCase("start"))
			{
				String map = args[1];
				if(!getConfig().isConfigurationSection(map)){
					getConfig().createSection(map);
				}
				ConfigurationSection sec = getConfig().getConfigurationSection(map);
					int i = Integer.valueOf(args[3]);
					if(!(1 <= i && i <= 10))
					{
						p.sendMessage("1~10�� ���ڸ� �Է� �����մϴ�.");
						return;
					}
						sec.set("start_world"+i, p.getWorld().getName());
						sec.set("start_x"+i, p.getLocation().getBlockX());
						sec.set("start_y"+i, p.getLocation().getBlockY());
						sec.set("start_z"+i, p.getLocation().getBlockZ());
					    saveConfig();
					    Loadconfig();
						p.sendMessage(MS+map+"�� "+i+"��° ���� ���� ���� �Ϸ�");
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
		if(plist.size() >= 8)
		{
			p.sendMessage(MS+"�̹� �ִ��ο�(8)�Դϴ�.");
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
			me.Bokum.EGM.Main.gamelist.put(p.getName(), "�������");
			Sendmessage(MS+p.getName()+" ���� ������⿡ �����ϼ̽��ϴ�. �ο� (��e "+plist.size()+"��7 / ��c4 ��f)");
			if(!check_lobbystart && plist.size() >= 4)
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
			return;
		}
		if(catcher == p){
			catcher = null;
			Sendmessage(MS+"���� ��6"+p.getName()+" ��f���� �����ϼ̽��ϴ�.\n"+MS+"��f�̹� ���尡 ���������� ��ٷ��ּ���.");
		}
	}
	
	public static void Win(final Player p)
	{
		Bukkit.getScheduler().cancelTask(timer_game_num);
		p.playSound(p.getLocation(), Sound.ANVIL_LAND, 2.5f, 1.5f);
		p.sendMessage(MS+"����� ���ķ� ���ҽ��ϴ�!");
		try{
		Bukkit.getScheduler().scheduleSyncDelayedTask(instance, new Runnable()
		{
			public void run()
			{
				econ.depositPlayer(p.getName(), 815);
				p.sendMessage(ChatColor.GOLD + "�¸� �������� 815���� �����̽��ϴ�.");
				try{
					me.Bokum.EGM.Main.Spawn(p);
					} catch(Exception e){
					//p.teleport(Main.Lobby);
					}
				Cleardata();
				Bukkit.broadcastMessage(MS+"��7��l�±ر� ��������f�� ��9"+p.getName()+"��f���� �¸��� ���� �ƽ��ϴ�.");
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
			  p.sendMessage("��7���� �̸� ��f: ��c�±ر� �������");
			  p.sendMessage("��f������ ���۵Ǹ� ������ �������ϴ�.");
			  p.sendMessage("��f������ ������ �÷��̾���� �Ӹ��� �� ������");
			  p.sendMessage("��f���� ������ ������ ������ �� �÷��̾ Ÿ�ݽ�");
			  p.sendMessage("��f������ ���Ѱ� Ÿ�ݵ� �÷��̾ ������ �˴ϴ�.");
			  p.sendMessage("��f����ġ�� Ÿ�̸Ӱ� ������ �� ������ Ż���Ǹ� ���� ���尡 ����˴ϴ�.");
			  p.sendMessage("��f������ 1���� ���������� ��ӵ˴ϴ�.");
			  p.closeInventory();
			  return;
			  
		  case 13:
			  p.sendMessage("��f������ ������ �� �����ϴ�.");
			  p.sendMessage("��f���� �������� ���� �ʽ��ϴ�.");
			  p.closeInventory();
			  return;
			  
		  default: return;
		  }  
	  }
	 
		public static void Startgame()
		{
			check_lobbystart = true;
			Bukkit.broadcastMessage(MS+"��b��l�±ر� ��������f�� �� ���۵˴ϴ�.");
			timer_start_time = 6;
			timer_start_num = Bukkit.getScheduler().scheduleSyncRepeatingTask(instance, new Runnable()
			{
				public void run()
				{
					if(timer_start_time > 0)
					{
						Sendmessage(MS+"������ "+timer_start_time*10+" �� �� ���۵˴ϴ�.");
						timer_start_time--;
					}
					else
					{
						Bukkit.getScheduler().cancelTask(timer_start_num);
						check_start = true;
						map = Getrandom(0, 2);
						for(int i = 0; i < plist.size(); i++){
							Player p = plist.get(i);
							p.getInventory().clear();
							p.playSound(p.getLocation(), Sound.ANVIL_LAND, 2.0f, 1.0f);
							p.sendMessage(MS+"������ ���� �ƽ��ϴ�.");
							p.teleport(startpos[map][Getrandom(0, 9)]);
							p.addPotionEffect(new PotionEffect(PotionEffectType.SPEED, 72000, 2));
						}
						Timer();
					}
				}
			}, 200L, 200L);
		}
		
		public static void setCatcher(){
			for(Player p : plist){
				p.getInventory().setHelmet(new ItemStack(Material.WOOL,1));
				p.playSound(p.getLocation(), Sound.CLICK, 2.0f, 1.0f);
			}
			catcher = plist.get(Getrandom(0, plist.size()-1));
			catcher.getInventory().setHelmet(null);
			Sendmessage(MS+"��c"+catcher.getName()+" ��7���� ������ ���������ϴ�!");
		}
		
		public static void Timer(){
			timer_game_time = 115;
			timer_game_num = Bukkit.getScheduler().scheduleSyncRepeatingTask(instance, new Runnable(){
				public void run(){
					if(timer_game_time > 115){
						timer_game_time--;
						if(timer_game_time == 116){
							for(Player p : plist){
								p.sendMessage(MS+"���尡 ���۵˴ϴ�.");
								p.playSound(p.getLocation(), Sound.NOTE_PLING, 2.0f, 1.0f);
								p.teleport(startpos[map][Getrandom(0, 9)]);
							}
						}
					} else if(timer_game_time >= 100){
						if(timer_game_time == 100){
							setCatcher();
						}else{
							Sendmessage(MS+(timer_game_time-100)+"�� �� ������ �������ϴ�.");
						}
						timer_game_time--;
					} else {
						if(timer_game_time > 0){
							timer_game_time--;
							for(Player p : plist){
								p.setExp((float)timer_game_time/100);
							}
						} else {
							if(catcher != null){
								catcher.damage(100);
								catcher = null;
							} else {
								Sendmessage(MS+"������ �������� �ʾ� ���� ����� �Ѿ�ϴ�.");
								if(plist.size() <= 1){
									try{
									Win(plist.get(0));
									}catch(Exception e){
										Forcestop();
									}
								}
							}
							map = Getrandom(0, 2);
							timer_game_time = 120;
							for(Player t : plist){
								t.getInventory().setHelmet(null);
								t.playSound(t.getLocation(), Sound.ANVIL_LAND, 2.0f, 1.0f);
							}
						}
					}
				}
			}, 80l, 20l);
		}
		
		@EventHandler
		public void onRightClick(PlayerInteractEvent e)
		{
			if(e.getAction() == Action.RIGHT_CLICK_BLOCK || e.getAction() == Action.RIGHT_CLICK_AIR)
			{
				final Player p = e.getPlayer();
				if(!plist.contains(p) || e.getItem() == null) return;
				if(e.getItem().getType() == Material.COMPASS)
						  p.openInventory(gamehelper);
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
				 if(e.getCurrentItem() != null && e.getCurrentItem().getType() == Material.WOOL){
					 p.sendMessage(MS+"������ ������ �� �����ϴ�.");
					 e.setCancelled(true);
				 }
			}
		}
		
		@EventHandler
		public void onPlayerDamage(EntityDamageEvent e){
			if(!(e.getEntity() instanceof Player)) return;
			if(plist.contains((Player) e.getEntity())){
				if(!check_start) e.setCancelled(true);
				else if(e.getCause() == DamageCause.FALL) e.setCancelled(true);
			}
		}
		
		@EventHandler
		public void onItemDrop(PlayerDropItemEvent e){
			if(!plist.contains(e.getPlayer())) return;
			if(e.getItemDrop().getItemStack().getType() == Material.COMPASS || e.getItemDrop().getItemStack().getType() == Material.WOOL) e.setCancelled(true);
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
		public void onBlockbreak(BlockBreakEvent e){
			  if(plist.contains(e.getPlayer()))
				  e.setCancelled(true);
		}
		  
		@EventHandler
		public void onBlockPlace(BlockPlaceEvent e){
			  if(plist.contains(e.getPlayer()))
			e.setCancelled(true);
		}
		
		@EventHandler
		public void onSneak(PlayerToggleSneakEvent e){
			if(plist.contains(e.getPlayer())){
				e.setCancelled(true);
				e.getPlayer().sendMessage(MS+"�� ���ӿ����� ����Ʈ�� ����� �� �����ϴ�.");
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
						if(catcher != d){
							e.setCancelled(true);
						} else {
							if(d.hasPotionEffect(PotionEffectType.SLOW)){
								e.setCancelled(true);
								d.sendMessage(MS+"���� ������ �� �����ϴ�.");
								return;
							}
							catcher = p;
							Sendmessage(MS+p.getName()+" ���� �±ر⸦ ���Ѱ� ������ �Ǿ����ϴ�.");
							p.addPotionEffect(new PotionEffect(PotionEffectType.SLOW, 100, 49));
							p.addPotionEffect(new PotionEffect(PotionEffectType.JUMP, 100, 199));
							for(Player t : plist){
								t.playSound(t.getLocation(), Sound.NOTE_PIANO, 2.0f, 1.0f);
							}
							p.getInventory().setHelmet(null);
							d.getInventory().setHelmet(new ItemStack(Material.WOOL, 1));
						}
					}
				}
			}
}

