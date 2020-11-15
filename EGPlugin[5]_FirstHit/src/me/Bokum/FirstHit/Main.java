package me.Bokum.FirstHit;

import java.util.ArrayList;
import java.util.HashMap;
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
import org.bukkit.event.player.PlayerRespawnEvent;
import org.bukkit.event.player.PlayerToggleSneakEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.plugin.RegisteredServiceProvider;
import org.bukkit.plugin.java.JavaPlugin;

import net.milkbowl.vault.economy.Economy;
import net.milkbowl.vault.economy.EconomyResponse;

public class Main extends JavaPlugin implements Listener
{
	public static List<Player> plist = new ArrayList<Player>();
	public static String MS = "��f[��aEG��f]��e ";
	public static Economy econ = null;
	public static boolean check_start = false;
	public static boolean check_lobbystart = false;
	public static Location Lobby;
	public static Location[] startpos = new Location[30];
	public static Location joinpos;
	public static ItemStack helpitem;
	public static int schtime = 5;
	public static Main instance;
	public static boolean game_end = false;
	public static ItemStack stick;
	public static int schnum = 0;
	public static int topkill = 0;
	public static Inventory gamehelper;
	public static int Forcestoptimer = 0;
	public static HashMap<String, Integer> killlist = new HashMap<String, Integer>();
	
	public void onEnable()
	{
		getServer().getPluginManager().registerEvents(this, this);
		getLogger().info("�������� �÷������� �ε� �Ǿ����ϴ�.");
		
		instance = this;
		
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
		
		helpitem = new ItemStack(345, 1);
		ItemMeta meta1 = helpitem.getItemMeta();
		meta1.setDisplayName("��f[ ��6���� ����� ��f]");
		List<String> lorelist1 = new ArrayList<String>();
		lorelist1.clear();
		lorelist1.add("��f- ��7��Ŭ���� ���� ����� ���ϴ�.");
		meta1.setLore(lorelist1);
		helpitem.setItemMeta(meta1);
		
		stick = new ItemStack(280, 1);
		ItemMeta meta = stick.getItemMeta();
		meta.setDisplayName(MS+"��������");
		List<String> lorelist = new ArrayList<String>();
		lorelist.add("��b������ ��������!");
		meta.setLore(lorelist);
		item.setItemMeta(meta);
		
		Loadconfig();
		
        if (!setupEconomy() ) {
            getLogger().info("[���� �߻� ��� ] Vault�÷������� �νĵ��� �ʾҽ��ϴ�!");
            getServer().getPluginManager().disablePlugin(this);
            return;
        }
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
	  for (int i = 1; i <= startpos.length; i++)
	  {
			ConfigurationSection sec = getConfig().getConfigurationSection("Pos"+i);
			try
			{
			  startpos[i-1] = new Location(Bukkit.getWorld(sec.getString("Start_world")), sec.getInt("Start_x"), sec.getInt("Start_y"), sec.getInt("Start_z"));
			}
			catch (Exception e)
			{
			  getLogger().info(i + "��°  ������ �Ϻ��� ���� �Ǿ����� �ʽ��ϴ�. ���� �߻��� ����� �ֽ��ϴ�.");
			}
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
		getLogger().info("�������� �÷������� ��ε� �Ǿ����ϴ�.");
	}
	
	public boolean onCommand(CommandSender talker, Command command, String string, String args[])
	{
		if(talker instanceof Player)
		{
			Player p = (Player) talker;
			if(string.equalsIgnoreCase("fh") && p.hasPermission("fh") && args.length >= 1)
			{
				if(args[0].equalsIgnoreCase("join"))
				{
					GameJoin(p);
					return true;
				}
				if(args[0].equalsIgnoreCase("set"))
				{
					Setloc(p, args);
					return true;
				}
				else if(args[0].equalsIgnoreCase("quit"))
				{
					GameQuit(p);
					return true;
				}
				else if(args[0].equalsIgnoreCase("stop"))
				{
					Forcestop();
					return true;
				}
				else if(args[0].equalsIgnoreCase("test"))
				{
					p.teleport(startpos[Integer.valueOf(args[1])]);
					return true;
				}
			}
		}
		return false;
	}
	
	public void Setloc(Player p, String[] args)
	{
		if(args.length <= 1)
		{
			p.sendMessage(MS+"/fh set lobby");
			p.sendMessage(MS+"/fh set join");
			p.sendMessage(MS+"/fh set pos 1~30");
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
				p.sendMessage(MS+"/trb set pos 1~6 <spec/red/blue>");
			}
			return;
		}
		else if(args.length >= 3)
		{
			if(args[1].equalsIgnoreCase("pos"))
			{
					int i = Integer.valueOf(args[2]);
					if(!(1 <= i && i <= 30))
					{
						p.sendMessage("1~30�� ���ڸ� �Է� �����մϴ�.");
						return;
					}
					if(!getConfig().isConfigurationSection("Pos"+i))
					{
						getConfig().createSection("Pos"+i);
					}
						getConfig().getConfigurationSection("Pos"+i).set("Start_world", p.getWorld().getName());
						getConfig().getConfigurationSection("Pos"+i).set("Start_x", p.getLocation().getBlockX());
						getConfig().getConfigurationSection("Pos"+i).set("Start_y", p.getLocation().getBlockY()+1);
						getConfig().getConfigurationSection("Pos"+i).set("Start_z", p.getLocation().getBlockZ());
					    saveConfig();
					    Loadconfig();
						p.sendMessage(MS+"pos "+i+"�� spec ���� �Ϸ�");
			}
		}
	}
	
	public static void Forcestop()
	{
		Bukkit.broadcastMessage(MS+"���������� ���� ���� �Ǿ����ϴ�.");
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
	
	public static void Cleardata(){
		Bukkit.getScheduler().cancelTasks(instance);
		Forcestoptimer = 0;
		plist.clear();
		check_start = false;
		check_lobbystart = false;
		schtime = 5;
		topkill = 0;
		killlist.clear();
		game_end = false;
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
			p.sendMessage(MS+"�̹� ������ �������Դϴ�.");
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
			me.Bokum.EGM.Main.gamelist.put(p.getName(), "��������");
			Sendmessage(MS+p.getName()+" ���� �������ӿ� �����ϼ̽��ϴ�. �ο� (��e "+plist.size()+"��7 / ��c3 ��f)");
			if(!check_lobbystart && plist.size() >= 3)
			{
				Startgame();
			}
			for(Player t : plist){
				t.playSound(t.getLocation(), Sound.NOTE_PLING, 2.0f, 1.0f);
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
	
	public static void Startgame()
	{
		check_lobbystart = true;
		Bukkit.broadcastMessage(MS+"��d��l�������ӡ�f�� �� ���۵˴ϴ�.");
		schtime = 5;
		schnum = Bukkit.getScheduler().scheduleSyncRepeatingTask(instance, new Runnable()
		{
			public void run()
			{
				if(schtime > 0)
				{
					Sendmessage(MS+"������ "+schtime*10+" �� �� ���۵˴ϴ�.");
					schtime--;
				}
				else
				{
					Bukkit.getScheduler().cancelTask(schnum);
					check_start = true;
					for(Player p : plist){
						Setitem(p);
						p.teleport(startpos[Getrandom(0, 6)]);
					}
					if(plist.size() <= 0){
						Forcestop();
					}
				}
			}
		}, 200L, 200L);
	}
	
	public static int Getrandom(int min, int max){
		  return (int)(Math.random() * (max-min+1)+min);
		}
	
	public static void Setitem(final Player p){
			p.getInventory().addItem(stick);
			p.updateInventory();
			Bukkit.getScheduler().scheduleSyncDelayedTask(instance, new Runnable(){
				public void run(){
					p.setSneaking(true);
				}
			}, 10L);
	}
	
	public void Win(final Player p){
		game_end = true;
		Sendmessage(MS+p.getName()+" �Բ��� �¸��ϼ̽��ϴ�!");
		for(Player t : plist){
			t.getInventory().clear();
		}
		try{
		Bukkit.getScheduler().scheduleSyncDelayedTask(this, new Runnable()
		{
			public void run()
			{	
				for(Player t : plist)
				{
						EconomyResponse r = econ.depositPlayer(p.getName(), 100);
						if (r.transactionSuccess()) 
						{
							t.sendMessage(ChatColor.GOLD + "���� �������� 100���� �����̽��ϴ�.");
					          try{
					        	  me.Bokum.EGM.Main.Spawn(t);
					          } catch(Exception e){
					        	  t.teleport(Main.Lobby);
					          }
						}
				}
				Cleardata();
				EconomyResponse r = econ.depositPlayer(p.getName(), 300);
				p.sendMessage(ChatColor.GOLD + "�¸� �������� 300���� �����̽��ϴ�.");
				Bukkit.broadcastMessage(MS+"��e��l�������ӡ�f�� ��c"+p.getName()+"��f���� �¸��� ���� �ƽ��ϴ�.");
			}
		}, 140L);
		}catch(Exception e){
	    	Forcestop();
	    }
	}
	
	  public static void GameHelper(Player p, int slot){
		  switch(slot){
		  case 11:
			  p.sendMessage("��7���� �̸� ��f: ��c��������");
			  p.sendMessage("��f������ ���۵Ǹ� ��6������f�� �ϳ� �޽��ϴ�.");
			  p.sendMessage("��f�� ������ ��븦 Ÿ�ݽ� ���� ��� �ϰԵ˴ϴ�.");
			  p.sendMessage("��f�� 15ų�� �� �÷��̾ �¸��մϴ�.");
			  p.closeInventory();
			  return;
			  
		  case 13:
			  p.sendMessage("��f����ϼŵ� ��Ȱ�մϴ�. \n��f������ ��ġ�� �����Դϴ�.");
			  p.closeInventory();
			  return;
			  
		  default: return;
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
			Sendmessage(MS+"��6"+p.getName()+" ��f���� ���� �ϼ̽��ϴ�.");
			if(plist.size() == 1)
			{
				try{
					Win(plist.get(0));
				}catch(Exception e){
					Forcestop();
				}
			}
	}
	
	@EventHandler
	public void onPlayerdeath(PlayerDeathEvent e){
		if(plist.contains(e.getEntity())) e.getDrops().clear();
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
	  public void onQuitPlayer(PlayerQuitEvent e) {
	    if (plist.contains(e.getPlayer()))
	    {
	      GameQuit(e.getPlayer());
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
	public void onSneak(PlayerToggleSneakEvent e)
	{
		if(plist.contains(e.getPlayer()) && check_start)
		{
			e.getPlayer().sendMessage(MS+"�ش� ������ sneak�� ����� �ʿ䰡 �����ϴ�.");
			e.setCancelled(true);
		}
	}
	
	@EventHandler
	public void onRightClick(PlayerInteractEvent e)
	{
		Player p = e.getPlayer();
		if(e.getItem() == null) return;
		if(!plist.contains(p)) return;
		if((e.getAction() == Action.RIGHT_CLICK_AIR || e.getAction() == Action.RIGHT_CLICK_BLOCK))
		{
			 if(e.getItem().getType() == Material.COMPASS){
				  p.openInventory(gamehelper);
			 }
		}
	}
	
	@EventHandler
	public void onClickInventory(InventoryClickEvent e)
	{
		if(e.getWhoClicked() instanceof Player)
		{
			Player p = (Player) e.getWhoClicked();
			if(plist.contains(p) && e.getInventory().getTitle().equalsIgnoreCase("��c��l�����")){
					 GameHelper(p, e.getSlot());
					 e.setCancelled(true);
			}
		}
	}
	
	@EventHandler
	public void onRespawn(PlayerRespawnEvent e){
		if(plist.contains(e.getPlayer())){
			e.setRespawnLocation(startpos[Getrandom(0, 29)]);
			Setitem(e.getPlayer());
		}
	}
	
	@EventHandler
	public void onDrop(PlayerDropItemEvent e){
		if(plist.contains(e.getPlayer())){
			e.setCancelled(true);
		}
	}
	
	@EventHandler
	public void onDamge(EntityDamageEvent e){
		if(!(e.getEntity() instanceof Player)) return;
		Player p = (Player) e.getEntity();
		if(plist.contains(p) && !check_start){
			e.setCancelled(true); return;
		}
	}
	
	@EventHandler
	public void onHit(EntityDamageByEntityEvent e)
	{
		if(e.getEntity() instanceof Player && e.getDamager() instanceof Player)
		{
			Player p = (Player) e.getEntity();
			Player d = (Player) e.getDamager();
			if(plist.contains(p) && plist.contains(d) && d.getItemInHand().getTypeId() == 280 && !game_end)
			{
				e.setCancelled(true);
				p.damage(30);
				p.sendMessage(MS+d.getName()+" ���� ��ſ��� ������ ���Ƚ��ϴ�!");
				d.sendMessage(MS+p.getName()+" �Կ��� ������ ���Ƚ��ϴ�!");
			      EconomyResponse r = econ.depositPlayer(d.getName(), 25);
			      if (r.transactionSuccess()) {
			            d.sendMessage(ChatColor.GOLD + "��� �������� 25���� �����̽��ϴ�.");
			      }
			      if(!killlist.containsKey(d.getName())){
			    	  killlist.put(d.getName(), 1);
			    	  return;
			      }
			      int killcount = killlist.get(d.getName());
			      if(++killcount > topkill){
			    	  Sendmessage(MS+"���� ��6"+d.getName()+" ��f���� �����Դϴ�. (��c"+killcount+"ų��f)");
			    	  topkill = killcount;
			      }else d.sendMessage("��f���� ų �� : ��e"+killcount+"ų");
			      killlist.put(d.getName(), killcount);
			      if(killcount >= 15) Win(d);
			}
		}
	}
}
