package me.Bokum.Hungergame;

import java.io.File;
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
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Item;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.event.player.PlayerCommandPreprocessEvent;
import org.bukkit.event.player.PlayerDropItemEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.InventoryHolder;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.plugin.RegisteredServiceProvider;
import org.bukkit.plugin.java.JavaPlugin;

import net.milkbowl.vault.economy.Economy;
import net.milkbowl.vault.economy.EconomyResponse;

public class Main extends JavaPlugin implements Listener
{
	public static String MS = "��f[ ��aEG ��f] ";
	public static Location Lobby;
	public static int mapnum = 0;
	public static Main instance;
	public static List<Location> chestlist = new ArrayList<Location>();
	public static List<Player> plist = new ArrayList<Player>();
	public static Location startpos[][] = new Location[3][8];
	public static int tasknum[] = new int[10];
	public static int tasktime[] = new int[10];
	public static ItemStack helpitem;
	public static Inventory gamehelper;
	public static Economy econ = null;
	public static boolean check_start = false;
	public static boolean pvp_start = false;
	public static boolean check_lobbystart = false;
	public static int Forcestoptimer = 0;
	public static List<Inventory> invenlist = new ArrayList<Inventory>();
	public static List<Item> clearitemlist = new ArrayList<Item>();
	
	public void onEnable()
	{
		instance = this;
		getServer().getPluginManager().registerEvents(this, this);
		getLogger().info("��Ű��� �÷������� �ε� �Ǿ����ϴ�.");
		
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
		LoadInventory();
		
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
    
	public void LoadInventory()
	{
		Inventory tmpinv = null;
		invenlist.clear();
		do
		{
			tmpinv = null;
			tmpinv = getInventoryFromFile(new File(this.getDataFolder(), "HG_Inventory_"+(invenlist.size()+1)+ ".invsave"));
			if(tmpinv != null)
			{
				invenlist.add(tmpinv);
			}
			else
			{
				getLogger().info(MS+invenlist.size()+"��°������ �κ��丮�� �ε� �Ǿ����ϴ�.");
				return;
			}
		}
		while(tmpinv != null);
	}
	
	public static boolean saveInventoryToFile(Inventory inventory, File path, String fileName) {
		if (inventory == null || path == null || fileName == null) return false;
		try {
			File invFile = new File(path, fileName + ".invsave");
			if (invFile.exists()) invFile.delete();
			FileConfiguration invConfig = YamlConfiguration.loadConfiguration(invFile);

			invConfig.set("Title", inventory.getTitle());
			invConfig.set("Size", inventory.getSize());
			invConfig.set("Max stack size", inventory.getMaxStackSize());

			ItemStack[] invContents = inventory.getContents();
			for (int i = 0; i < invContents.length; i++) {
				ItemStack itemInInv = invContents[i];
				if (itemInInv != null) if (itemInInv.getType() != Material.AIR) invConfig.set("Slot " + i, itemInInv);
			}

			invConfig.save(invFile);
			return true;
		} catch (Exception ex) {
			return false;
		}
	}
	
	public static Inventory getInventoryFromFile(File file) {
		if (file == null) return null;
		if (!file.exists() || file.isDirectory() || !file.getAbsolutePath().endsWith(".invsave")) return null;
		try {
			FileConfiguration invConfig = YamlConfiguration.loadConfiguration(file);
			Inventory inventory = null;
			String invTitle = invConfig.getString("Title", "Inventory");
			int invSize = invConfig.getInt("Size", 27);
			int invMaxStackSize = invConfig.getInt("Max stack size", 64);
			InventoryHolder invHolder = null;
			if (invConfig.contains("Holder")) invHolder = Bukkit.getPlayer(invConfig.getString("Holder"));
			inventory = Bukkit.getServer().createInventory(invHolder, invSize, ChatColor.translateAlternateColorCodes('&', invTitle));
			inventory.setMaxStackSize(invMaxStackSize);
			try {
				ItemStack[] invContents = new ItemStack[invSize];
				for (int i = 0; i < invSize; i++) {
					if (invConfig.contains("Slot " + i)) invContents[i] = invConfig.getItemStack("Slot " + i);
					else invContents[i] = new ItemStack(Material.AIR);
				}
				inventory.setContents(invContents);
			} catch (Exception ex) {
			}
			return inventory;
		} catch (Exception ex) {
			return null;
		}
	}
	
	public void SaveInventory(Player p, String[] args)
	{
		int inven_num = 0;
		try
		{
			inven_num = Integer.valueOf(args[1]);
		}
		catch(Exception e)
		{
			p.sendMessage(MS+"/ehg inv ����");
			return;
		}
		if(inven_num == 0)
		{
			p.sendMessage(MS+"0 �̻� �Է����ּ���.");
			return;
		}
		if(inven_num > (invenlist.size()+1))
		{
			p.sendMessage(MS+(invenlist.size()+1)+" �� �κ��丮�� ���� ������ּ���.");
			return;
		}
		Inventory inv = Bukkit.getServer().createInventory(null, 36, "HG_Inventory_"+inven_num);
		if(inven_num <= invenlist.size())
		{
			inv.setContents(invenlist.get(inven_num-1).getContents());
			p.sendMessage(MS+(inven_num-1)+"��° �κ��丮 ����");
		}
		p.openInventory(inv);
		p.sendMessage("�������� ��������.");
	}
	
	public void onDisable()
	{
		getLogger().info("��Ű��� �÷������� ��ε� �Ǿ����ϴ�.");
	}
	
	public void Loadconfig()
	{
	  try
	  {
	    Lobby = new Location(Bukkit.getWorld(getConfig().getString("Lobby_world")), getConfig().getInt("Lobby_x"), getConfig().getInt("Lobby_y"), getConfig().getInt("Lobby_z"));
	  }
	  catch (IllegalArgumentException e)
	  {
	    getLogger().info("���� ���� �Ǵ� �κ� �����Ǿ� ���� �ʽ��ϴ�");
	  }
	  for (int i = 1; i <= 3; i++)
	  {
			ConfigurationSection sec = getConfig().getConfigurationSection("Game"+i);
			try
			{
				for(int j = 1; j < 9; j++){
					  startpos[i-1][j-1] = new Location(Bukkit.getWorld(sec.getString("start_world"+j)), sec.getInt("start_x"+j), sec.getInt("start_y"+j), sec.getInt("start_z"+j));
				}
			}
			catch (Exception e)
			{
			  getLogger().info(i + "��°  ������ �Ϻ��� ���� �Ǿ����� �ʽ��ϴ�. ���� �߻��� ����� �ֽ��ϴ�.");
			}
	  }
	}
	
	public boolean onCommand(CommandSender talker, Command command, String str, String args[])
	{
		if(talker instanceof Player)
		{
			Player p = (Player) talker;
			if(str.equalsIgnoreCase("egh") && p.isOp())
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
					if(args[0].equalsIgnoreCase("inv")){
						SaveInventory(p, args);
					}
					if(args[0].equalsIgnoreCase("item")){
						getChestitem(p);
					}
				}
			}
		}
		return false;
	}
	
	public void Helpmessages(Player p)
	{
		p.sendMessage(MS+"/ehg join");
		p.sendMessage(MS+"/ehg quit");
		p.sendMessage(MS+"/ehg set");
		p.sendMessage(MS+"/ehg reload");
		p.sendMessage(MS+"/ehg inv");
	}
	
	public static void Forcestop()
	{
		Bukkit.broadcastMessage(MS+"��Ű����� ���� ���� �Ǿ����ϴ�.");
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
		mapnum = Getrandom(0, 2);
		chestlist.clear();
		plist.clear();
		check_start = false;
		pvp_start = false;
		check_lobbystart = false;
		for(Item dropItem : clearitemlist){
			dropItem.remove();
		}
	}
	
	public void Setloc(Player p, String[] args)
	{
		if(args.length <= 1)
		{
			p.sendMessage(MS+"/ehg set lobby");
			p.sendMessage(MS+"/ehg set join");
			p.sendMessage(MS+"/ehg set pos 1~3 1~8");
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
			else
			{
				p.sendMessage(MS+"/ehg set pos 1~3 1~8");
			}
			return;
		}
		else if(args.length >= 4)
		{
			if(args[1].equalsIgnoreCase("pos"))
			{
					int i = Integer.valueOf(args[2]);
					if(!(1 <= i && i <= 3))
					{
						p.sendMessage("1~3�� ���ڸ� �Է� �����մϴ�.");
						return;
					}
					int j = Integer.valueOf(args[3]);
					if(!(1 <= i && i <= 3))
					{
						p.sendMessage("1~8�� ���ڸ� �Է� �����մϴ�.");
						return;
					}
					if(!getConfig().isConfigurationSection("Game"+i))
					{
						getConfig().createSection("Game"+i);
					}
						getConfig().getConfigurationSection("Game"+i).set("start_world"+j, p.getWorld().getName());
						getConfig().getConfigurationSection("Game"+i).set("start_x"+j, p.getLocation().getBlockX());
						getConfig().getConfigurationSection("Game"+i).set("start_y"+j, p.getLocation().getBlockY());
						getConfig().getConfigurationSection("Game"+i).set("start_z"+j, p.getLocation().getBlockZ());
					    saveConfig();
					    Loadconfig();
						p.sendMessage(MS+"game "+i+"�� "+j+" ���� �Ϸ�");
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
			p.teleport(startpos[mapnum][plist.indexOf(p)]);
			p.getInventory().clear();
			p.getInventory().setHelmet(null);
			p.getInventory().setChestplate(null);
			p.getInventory().setLeggings(null);
			p.getInventory().setBoots(null);
			p.getInventory().setItem(8, helpitem);
			me.Bokum.EGM.Main.gamelist.put(p.getName(), "��Ű���");
			Sendmessage(MS+p.getName()+" ���� ��Ű��ӿ� �����ϼ̽��ϴ�. �ο� (��e "+plist.size()+"��7 / ��c3 ��f)");
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
		Sendmessage(MS+p.getName()+" ��f���� ����ϼ̽��ϴ�.");
		if(plist.size() == 1)
		{
			try{
				Win(plist.get(0));
			}catch(Exception e){
				Forcestop();
			}
		}
	}
	
	  public static void GameHelper(Player p, int slot){
		  switch(slot){
		  case 11:
			  p.sendMessage("��7���� �̸� ��f: ��c��Ű���");
			  p.sendMessage("��f�� �������� ���ڰ� �ֽ��ϴ�.");
			  p.sendMessage("��f���ڸ� ���� �������� �������� ������");
			  p.sendMessage("��f�� �����۵��� �̿��Ͽ� �ٸ� �÷��̾");
			  p.sendMessage("��c��� ���̸� �¸��մϴ�.");
			  p.sendMessage("��c���� �� 1�а��� �����Դϴ�.");
			  p.closeInventory();
			  return;
			  
		  case 13:
			  p.sendMessage("��f������ 2������� �����մϴ�.");
			  p.sendMessage("��f���� �ý��ۻ� ���ڸ� �ѹ� ������ �� \n�������� ��c���� �����ּ���.\n��f�ٽ� ������ ���� �������� �����ϴ�.");
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
		
		public static void getChestitem(Player p){
			Inventory getinv = Bukkit.getServer().createInventory(null, 36, "������");
			Inventory tmpinv = Bukkit.getServer().createInventory(null, 36, "������");
			ItemStack item = null;
			for(int i = 0; i < Getrandom(1, 8); i++){
				tmpinv = invenlist.get(Getrandom(0, invenlist.size()-1));
				item = tmpinv.getItem(Getrandom(0, tmpinv.getSize()-1));
				try{
					if(item.getType() != Material.POTION)
					item.setDurability((short) Getrandom(0, item.getType().getMaxDurability()-1));
				}catch(Exception e){
				}
				getinv.setItem(Getrandom(0, 35), item);
			}
			p.openInventory(getinv);
		}
		
		public static void Startgame()
		{
			check_lobbystart = true;
			final int cur = Getcursch();
			tasktime[cur] = 5;
			Bukkit.broadcastMessage(MS+"��d��l��Ű��ӡ�f�� �� ���۵˴ϴ�.");
			tasknum[cur] = Bukkit.getScheduler().scheduleSyncRepeatingTask(instance, new Runnable()
			{
				public void run()
				{
					if(tasktime[cur] > 0)
					{
						Sendmessage(MS+"������ "+tasktime[cur]*10+" �� �� ���۵˴ϴ�.");
						tasktime[cur]--;
					}
					else
					{
						Canceltask(cur);
						check_start = true;
						for(Player p : plist){
							p.getInventory().clear();
							for(Player s : plist)
								s.playSound(s.getLocation(), Sound.ANVIL_LAND, 2.0f, 1.0f);
							p.sendMessage(MS+"������ ���� �ƽ��ϴ�.");
							p.sendMessage(MS+"60�� �� ������ �����˴ϴ�.");
						}
						Startpvp();
						Bukkit.dispatchCommand(plist.get(0).getServer().getConsoleSender(), "lagg clear");
					}
				}
			}, 200L, 200L);
		}
		
		public static void Startpvp(){
			check_start = true;
			Bukkit.getScheduler().scheduleSyncDelayedTask(instance, new Runnable(){
				public void run(){
					pvp_start = true;
					for(Player p : plist){
						p.sendMessage(MS+"������ �����ƽ��ϴ�.");
					}
				}
			}, 1200L);
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
						p.sendMessage(ChatColor.GOLD + "�¸� �������� 700���� �����̽��ϴ�.");
					}
					try{
						me.Bokum.EGM.Main.Spawn(p);
						} catch(Exception e){
						p.teleport(Main.Lobby);
						}
					Cleardata();
					Bukkit.broadcastMessage(MS+"��d��l��Ű��ӡ�f�� ��9"+p.getName()+"��f���� �¸��� ���� �ƽ��ϴ�.");
				}
					}, 140L);
			}catch(Exception e){
		    	Forcestop();
		    }
		}
		
		public static int Getrandom(int min, int max){
			  return (int)(Math.random() * (max-min+1)+min);
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
			public void onPlayerMove(PlayerMoveEvent e){
				if(plist.contains(e.getPlayer()) && !check_start){
					Player p = e.getPlayer();
					if(!(p.getLocation().getBlockX() == e.getTo().getBlockX()
							&& p.getLocation().getBlockY() == e.getTo().getBlockY()
							&& p.getLocation().getBlockZ() == e.getTo().getBlockZ())){
						p.teleport(startpos[mapnum][plist.indexOf(p)]);
					}
				}
			}

			@EventHandler
			public void onPlayerDamage(EntityDamageEvent e){
				if(!(e.getEntity() instanceof Player)) return;
				if(plist.contains((Player) e.getEntity()) && !pvp_start) e.setCancelled(true);
			}
			
			@EventHandler
			public void onItemDrop(PlayerDropItemEvent e){
				if(!plist.contains(e.getPlayer())) return;
				if(e.getItemDrop().getItemStack().getType() == Material.COMPASS) e.setCancelled(true);
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
					for(ItemStack dropItem : e.getDrops()){
						if(dropItem instanceof Item){
							Item addItem = (Item) dropItem;
							clearitemlist.add(addItem);
						}
					}
					if(plist.contains(p))
					{
						GameQuit(p);
					}
				}
				
				@EventHandler
				public void onCloseInventory(InventoryCloseEvent e)
				{
					if(e.getPlayer() instanceof Player)
					{
						Player p = (Player) e.getPlayer();
						if(e.getInventory().getTitle().contains("HG_Inventory") && p.isOp())
						{
							saveInventoryToFile(e.getInventory(), this.getDataFolder(), e.getInventory().getTitle());
							LoadInventory();
							p.sendMessage(MS+"�κ��丮 ����Ϸ�");
							return;
						}
					}
				}
				
				@EventHandler
				public void onOpenChestBlock(final PlayerInteractEvent e)
				{
					if(e.getAction() == Action.RIGHT_CLICK_BLOCK || e.getAction() == Action.RIGHT_CLICK_AIR)
					{
						Player p = e.getPlayer();
						if(!plist.contains(p)) return;
						if(e.getItem() != null && e.getItem().getType() == Material.COMPASS)
								  p.openInventory(gamehelper);
						else if(e.getClickedBlock() != null && e.getClickedBlock().getTypeId() == 54){
						final Location bl = e.getClickedBlock().getLocation();
						if(!chestlist.contains(bl)){
							chestlist.add(bl);
							getChestitem(p);
							p.getWorld().playSound(e.getClickedBlock().getLocation(), Sound.CHEST_OPEN, 2.0f, 1.0f);
							e.setCancelled(true);
						  }
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
}
