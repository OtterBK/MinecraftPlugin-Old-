package me.Bokum.KTT;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Effect;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.block.Block;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.entity.Arrow;
import org.bukkit.entity.Player;
import org.bukkit.entity.Snowball;
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
import org.bukkit.event.player.PlayerPickupItemEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
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
	public static String MS = "��f[ ��aEG ��f] ";
	public static Location Lobby;
	public static Inventory playerlist;
	public static Location joinpos;
	public static Main instance;
	public static int enabled_box = 0;
	public static String enabled_name = "����";
	public static ItemStack[] chestitem = new ItemStack[33];
	public static List<Location> chestlist = new ArrayList<Location>();
	public static List<Player> plist = new ArrayList<Player>();
	public static Location startpos[] = new Location[15];
	public static Location boxpos[] = new Location[10];
	public static int tasknum[] = new int[10];
	public static int tasktime[] = new int[10];
	public static ItemStack helpitem;
	public static int timernum = 0;
	public static int timertime = 0;
	public static Inventory gamehelper;
	public static Economy econ = null;
	public static List<String> woollist = new ArrayList<String>();
	public static boolean check_start = false;
	public static boolean check_lobbystart = false;
	public static ItemStack wool[] = new ItemStack[9];
	public static int Forcestoptimer = 0;
	
	public void onEnable()
	{
		instance = this;
		getServer().getPluginManager().registerEvents(this, this);
		getLogger().info("Ÿ�پϻ� �÷������� �ε� �Ǿ����ϴ�.");
		
		for(int i = 0; i < tasknum.length; i++){
			tasknum[i] = -5;
			tasktime[i] = -5;
		}
		
		wool[0] = (new ItemStack(Material.WOOL, 1, (short) 14));
		wool[1] = (new ItemStack(Material.WOOL, 1, (short) 1));
		wool[2] = (new ItemStack(Material.WOOL, 1, (short) 4));
		wool[3] = (new ItemStack(Material.WOOL, 1, (short) 5));
		wool[4] = (new ItemStack(Material.WOOL, 1, (short) 11));
		wool[5] = (new ItemStack(Material.WOOL, 1, (short) 9));
		wool[6] = (new ItemStack(Material.WOOL, 1, (short) 10));
		wool[7] = (new ItemStack(Material.WOOL, 1));
		wool[8] = (new ItemStack(Material.WOOL, 1, (short) 15));
		
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
		
		item = new ItemStack(Material.BOOK_AND_QUILL, 1);
		meta2 = item.getItemMeta();
		meta2.setDisplayName("��e���� ����");
		item.setItemMeta(meta2);
		gamehelper.setItem(15, item);
		
		List<String> lorelist = new ArrayList<String>();
		
		chestitem[0] = Makeitem(352, "��������", 3, 5);
		chestitem[1] = Makeitem(287, "ä��", 4, 4);
		chestitem[2] = Makeitem(406, "��������", 2, 3);
		chestitem[3] = Makeitem(267, "��Į", 6, 7);
		chestitem[4] = Makeitem(359, "����", 5, 5);
		chestitem[5] = Makeitem(369, "�ݼӹ�Ʈ", 4, 5);
		chestitem[6] = new ItemStack(261, 1);
		chestitem[7] = Makeitem(273, "������", 2, 2);
		chestitem[8] = new ItemStack(262, 2);
		chestitem[9] = MakeGun(292, "M9", 7, 7);
		chestitem[10] = Makeitem(362, "9mmźȯ", 1, 1);
		chestitem[11] = new ItemStack(373, 1, (short) 16389);
		chestitem[12] = new ItemStack(373, 1, (short) 16421);
		chestitem[13] = new ItemStack(373, 1, (short) 16386);
		chestitem[14] = new ItemStack(373, 1, (short) 16388);
		chestitem[15] = new ItemStack(373, 1, (short) 16385);
		chestitem[16] = Makeitem(377, "���� ����", 1, 1);
		chestitem[17] = Makeitem(286, "��ġ", 5, 6);
		chestitem[18] = Makeitem(256, "��", 5, 6);
		chestitem[19] = Makeitem(362, "9mmźȯ", 1, 1);
		chestitem[20] = new ItemStack(300, 1);
		chestitem[21] = new ItemStack(301, 1);
		chestitem[22] = new ItemStack(373, 1, (short) 8194);
		chestitem[23] = Makeitem(340, "����", 1, 5);
		chestitem[24] = Makeitem(336, "����", 1, 5);
		chestitem[25] = new ItemStack(368, 1);
		chestitem[26] = MakeGun(292, "M9", 7, 7);
		chestitem[27] = Makeitem(362, "9mmźȯ", 1, 1);
		chestitem[28] = Makeitem(287, "ä��", 4, 4);
		chestitem[29] = Makeitem(352, "��������", 3, 5);
		chestitem[30] = Makeitem(369, "�ݼӹ�Ʈ", 4, 5);
		chestitem[31] = Makeitem(267, "���Į", 7, 8);
		chestitem[32] = Makeitem(264, "������ ��", 1, 1);
		
		
		helpitem = new ItemStack(345, 1);
		ItemMeta meta1 = helpitem.getItemMeta();
		meta1.setDisplayName("��f[ ��6���� ����� ��f]");
		lorelist.clear();
		lorelist.add("��f- ��7��Ŭ���� ���� ����̸� ���ϴ�.");
		meta1.setLore(lorelist);
		helpitem.setItemMeta(meta1);
		
		playerlist = Bukkit.createInventory(null, 18, "��c��l��ȭ�ڽ�");
		
		Loadconfig();
		
        if (!setupEconomy() ) {
            getLogger().info("[���� �߻� ��� ] Vault�÷������� �νĵ��� �ʾҽ��ϴ�!");
        }
	}
	
	  public static void Setplayerlist(){
		  for(int i = 0; i < 18; i++){
			 playerlist.setItem(i, null);
		  }
		  for(int i = 0; i < plist.size(); i++){
			  ItemStack item = new ItemStack(397, 1, (byte) 3);
			  ItemMeta meta = item.getItemMeta();
			  meta.setDisplayName(plist.get(i).getName());
			  List<String> lorelist = new ArrayList<String>();
			  lorelist.add("��cŬ���� �� �÷��̾��� ���л��� �� �� �ֽ��ϴ�.");
			  meta.setLore(lorelist);
			  item.setItemMeta(meta);
			  playerlist.setItem(i, item);
		  }
	  }
	
	  public static ItemStack MakeGun(int id, String name, int min, int max){
		  ItemStack tmpitem = new ItemStack(id, 1);
		  ItemMeta meta = tmpitem.getItemMeta();
		  meta.setDisplayName("��c"+name);
		  List<String> lorelist = new ArrayList<String>();
		  String damage = "��7ȭ��: ";
		  if(max == min){
			  damage += String.valueOf(min);
		  } else {
			  damage += min+"~";
			  damage += max;
		  }
		  lorelist.add(damage);
		  meta.setLore(lorelist);
		  tmpitem.setItemMeta(meta);
		  return tmpitem;
	  }
	
	  public static ItemStack Makeitem(int id, String name, int min, int max){
		  ItemStack tmpitem = new ItemStack(id, 1);
		  ItemMeta meta = tmpitem.getItemMeta();
		  meta.setDisplayName("��c"+name);
		  List<String> lorelist = new ArrayList<String>();
		  String damage = "��7���ݷ�: ";
		  if(max == min){
			  damage += String.valueOf(min);
		  } else {
			  damage += min+"~";
			  damage += max;
		  }
		  lorelist.add(damage);
		  meta.setLore(lorelist);
		  tmpitem.setItemMeta(meta);
		  return tmpitem;
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
		getLogger().info("Ÿ�پϻ� �÷������� ��ε� �Ǿ����ϴ�.");
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
		try
		{
			for(int j = 1; j < 16; j++){
				  startpos[j-1]= new Location(Bukkit.getWorld(getConfig().getString("start_world"+j)), getConfig().getInt("start_x"+j), getConfig().getInt("start_y"+j), getConfig().getInt("start_z"+j));
			}
		}
		catch (Exception e)
		{
			 getLogger().info("���� ������ �Ϻ��� ���� �Ǿ����� �ʽ��ϴ�. ���� �߻��� ����� �ֽ��ϴ�.");
		}
		try
		{
			for(int j = 1; j < 10; j++){
				  boxpos[j-1]= new Location(Bukkit.getWorld(getConfig().getString("box_world"+j)), getConfig().getInt("box_x"+j), getConfig().getInt("box_y"+j), getConfig().getInt("box_z"+j));
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
			if(str.equalsIgnoreCase("ktt") && p.isOp())
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
					if(args[0].equalsIgnoreCase("test")){
						p.teleport(startpos[Integer.valueOf(args[1])]);
						return true;
					}
					if(args[0].equalsIgnoreCase("wool")){
							Setwool();
						return true;
					}
				}
			}
		}
		return false;
	}
	
	public void Helpmessages(Player p)
	{
		p.sendMessage(MS+"/ktt join");
		p.sendMessage(MS+"/ktt quit");
		p.sendMessage(MS+"/ktt set");
		p.sendMessage(MS+"/ktt reload");
		p.sendMessage(MS+"/ktt inv");
	}
	
	public static void Forcestop()
	{
		Bukkit.broadcastMessage(MS+"Ÿ�پϻ��� ���� ���� �Ǿ����ϴ�.");
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
		enabled_box = 0;
		enabled_name = "����";
		chestlist.clear();
		plist.clear();
		woollist.clear();
		check_start = false;
		check_lobbystart = false;
	}
	
	public void Setloc(Player p, String[] args)
	{
		if(args.length <= 1)
		{
			p.sendMessage(MS+"/ktt set lobby");
			p.sendMessage(MS+"/ktt set join");
			p.sendMessage(MS+"/kit set start 1~15");
			p.sendMessage(MS+"/kit set box 1~10");
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
				p.sendMessage(MS+"/ktt set start 1~15");
				p.sendMessage(MS+"/kit set box 1~10");
			}
			return;
		}
		else if(args.length >= 3)
		{
			if(args[1].equalsIgnoreCase("start"))
			{
					int i = Integer.valueOf(args[2]);
					if(!(1 <= i && i <= 15))
					{
						p.sendMessage("1~15�� ���ڸ� �Է� �����մϴ�.");
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
			if(args[1].equalsIgnoreCase("box"))
			{
					int i = Integer.valueOf(args[2]);
					if(!(1 <= i && i <= 15))
					{
						p.sendMessage("1~10�� ���ڸ� �Է� �����մϴ�.");
						return;
					}
						getConfig().set("box_world"+i, p.getWorld().getName());
						getConfig().set("box_x"+i, p.getLocation().getBlockX());
						getConfig().set("box_y"+i, p.getLocation().getBlockY()-1);
						getConfig().set("box_z"+i, p.getLocation().getBlockZ());
					    saveConfig();
					    Loadconfig();
						p.sendMessage(MS+i+"��° �ڽ� ���� ���� �Ϸ�");
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
		if(plist.size() >= 9)
		{
			p.sendMessage(MS+"�̹� �ִ��ο�(9)�Դϴ�.");
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
			me.Bokum.EGM.Main.gamelist.put(p.getName(), "Ÿ�� �ϻ�");
			Sendmessage(MS+p.getName()+" ���� Ÿ�� �ϻ쿡 �����ϼ̽��ϴ�. �ο� (��e "+plist.size()+"��7 / ��c4 ��f)");
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
		Setplayerlist();
		if(!check_start)
		{
			return;
		}
		Sendmessage(MS+p.getName()+" ��f���� Ż���Ǽ̽��ϴ�.");
		woollist.remove(p.getName());
		if(plist.size() <= 1)
		{
			try{
				Win(plist.get(0));
			}catch(Exception e){
				Forcestop();
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
					p.sendMessage(ChatColor.GOLD + "�¸� �������� 800���� �����̽��ϴ�.");
				}
				try{
					me.Bokum.EGM.Main.Spawn(p);
					} catch(Exception e){
					p.teleport(Main.Lobby);
					}
				Cleardata();
				Bukkit.broadcastMessage(MS+"��e��lŸ�� �ϻ��f�� ��9"+p.getName()+"��f���� �¸��� ���� �ƽ��ϴ�.");
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
			  p.sendMessage("��7���� �̸� ��f: ��cŸ�� �ϻ�");
			  p.sendMessage("��f������ ���۵Ǹ� ��� �÷��̾�� \n���� ��b�ٸ�����f�� ������ �����մϴ�.");
			  p.sendMessage("��f���л��� ���� ��c���ϼ� �ִ� ��f���л��� �������ֽ��ϴ�.");
			  p.sendMessage("��f���л��� ���� �÷��̾ �׿� ���������� ��Ƴ�����");
			  p.sendMessage("��f�¸� �ϰԵ˴ϴ�. ���� �� �������� ��e��ȭ�ڽ���f��\n�����մϴ�. �� ��ȭ�ڽ��� 2�и���");
			  p.sendMessage("��f�������� 1���� Ȱ��ȭ �Ǹ� Ȱ��ȭ �� ��e��ȭ�ڽ���f�� ��Ŭ���� ��\n�÷��̾� 1���� ���л��� �� �� �ֽ��ϴ�.");
			  p.sendMessage("��f���� 2���� ���� �ٸ� ��e��ȭ�ڽ���f�� Ȱ��ȭ �Ǹ� ������ �ִ�\n��e��ȭ�ڽ���f�� ��Ȱ��ȭ�˴ϴ�.");
			  p.closeInventory();
			  return;
			  
		  case 13:
			  p.sendMessage("��f������ �������� �ʽ��ϴ�.");
			  p.sendMessage("��f������ �״��� ������ �������� �ʽ��ϴ�.");
			  p.sendMessage("��f�� �������� ���ڰ� ������ \n��Ŭ���� �������� ������ �� �ֽ��ϴ�.\n���л��� ���� ���̴� ����:");
			  p.sendMessage("��c���� ��f-> ��6��Ȳ ��f-> ��e��� ��f-> ��a�ʷ� ��f-> ��b�Ķ� \n��f-> ��1û�� ��f-> ��9���� ��f-> �Ͼ� -> ��8����  -> ��c����");
			  p.closeInventory();
			  return;
			  
		  case 15:
			  p.sendMessage("��f- ���� �÷��̾��");
			  for(Player t : plist){
				  p.sendMessage("��c- ��e"+t.getName());
			  }
			  
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
			Bukkit.broadcastMessage(MS+"��4��lŸ�پϻ��f�� �� ���۵˴ϴ�.");
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
							p.sendMessage(MS+"������ ���� �ƽ��ϴ�.");
							p.teleport(startpos[Getrandom(0, 14)]);
						}
						Setwool();
						Timer();
					}
				}
			}, 200L, 200L);
		}
		
		 public static boolean Hasitem(Player p, int id, int amt){
			  int tamt = amt;
				for(int i = 0; i < p.getInventory().getSize(); i++){
					if(tamt > 0){
						ItemStack pitem = p.getInventory().getItem(i);
						if(pitem != null && pitem.getTypeId() == id){
							tamt -= pitem.getAmount();
							if(tamt <= 0){
								return true;
							}
						}
					}
				}
				
				return false;
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
				
				return false;
		  }
		  
		  public static void Timer(){
			  timernum = Bukkit.getScheduler().scheduleSyncRepeatingTask(instance, new Runnable(){
				  public void run(){
					  if(check_start){
						  int rn = Getrandom(0, 8);
						  enabled_box = rn;
						  enabled_name = null;
						  Setplayerlist();
						  Sendmessage(MS+(rn+1)+"�� ��ȭ�ڽ��� Ȱ��ȭ �Ǿ����ϴ�. \n��ġ : ��eX: "+boxpos[rn].getBlockX()+" Y: "+boxpos[rn].getBlockY()+" Z: "+boxpos[rn].getBlockZ());
					  }else{
						  Bukkit.getScheduler().cancelTask(timernum);
					  }
				  }
			  }, 1200L, 2400L);
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
		
		public static void Setwool(){
			List<String> lorelist = new ArrayList<String>();
			String str = "";
			if(plist.size() >= 1){
				str += "��c����";
			}
			if(plist.size() >= 2){
				str += "��f -> ��6��Ȳ";
				if(plist.size() == 2){
					str += "��f -> ��c���� ";
				}
			}
			if(plist.size() >= 3){
				str += "��f -> ��e���";
				if(plist.size() == 3){
					str += "��f -> ��c���� ";
				}
				lorelist.add(str);
				str = "";
			}
			if(plist.size() >= 4){
				str += "��f -> ��a�ʷ�";
				if(plist.size() == 4){
					str += "��f -> ��c���� ";
					lorelist.add(str);
				}
			}
			if(plist.size() >= 5){
				str += "��f -> ��b�Ķ�";
				if(plist.size() == 5){
					str += "��f -> ��c���� ";
					lorelist.add(str);
				}
			}
			if(plist.size() >= 6){
				str += "��f -> ��1û��";
				if(plist.size() == 6){
					str += "��f -> ��c���� ";
				}
				lorelist.add(str);
				str = "";
			}
			if(plist.size() >= 7){
				str += "��f -> ��9����";
				if(plist.size() == 7){
					str += "��f -> ��c���� ";
					lorelist.add(str);
				}
			}
			if(plist.size() >= 8){
				str += "��f -> ��f�Ͼ�";
				if(plist.size() == 8){
					str += "��f -> ��c���� ";
					lorelist.add(str);
				}
			}
			if(plist.size() >= 9){
				str += "��f -> ��8����";
				if(plist.size() == 9){
					str += "��f -> ��c���� ";
				}
				lorelist.add(str);
			}
			for(int i = 0; i < wool.length; i++){
				ItemMeta meta = wool[i].getItemMeta();
				meta.setLore(lorelist);
				wool[i].setItemMeta(meta);
			}
			List<Player> tmplist = new ArrayList<Player>();
			for(Player t : plist){
				tmplist.add(t);
			}
			for(int i = 0; i < plist.size(); i++){
				int rn = Getrandom(0, (tmplist.size()-1));
				Player p = tmplist.get(rn);
				woollist.add(p.getName());
				p.getInventory().setItem(8, wool[i]);
				tmplist.remove(p);
			}
		}
		
		public void UseBox(Player p, String name){
			Player t = getServer().getPlayer(name);
			Inventory inven = t.getInventory();
			String str ="";
			if(inven.contains(wool[0])) str = "����";
			if(inven.contains(wool[1])) str = "��Ȳ";
			if(inven.contains(wool[2])) str = "���";
			if(inven.contains(wool[3])) str = "�ʷ�";
			if(inven.contains(wool[4])) str = "�Ķ�";
			if(inven.contains(wool[5])) str = "û��";
			if(inven.contains(wool[6])) str = "����";
			if(inven.contains(wool[7])) str = "�Ͼ�";
			if(inven.contains(wool[8])) str = "����";
			p.sendMessage(MS+t.getName()+" ���� ���л��� ��7"+str+" �Դϴ�.");
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
		public void onPickUpItem(PlayerPickupItemEvent e){
			if(e.getItem().getItemStack().getTypeId() == 35){
				e.setCancelled(true);
			}
		}
		
		@EventHandler
		public void onItemDrop(PlayerDropItemEvent e){
			if(!plist.contains(e.getPlayer())) return;
			if(e.getItemDrop().getItemStack().getType() == Material.WOOL){
				e.getPlayer().sendMessage(MS+"������ ������ �� �����ϴ�.");
				e.setCancelled(true);
			}
		}
		
		@EventHandler
		public void onDamaged(EntityDamageEvent e){
			if(e.getEntity() instanceof Player){
				Player p = (Player) e.getEntity();
				if(plist.contains(p)){
					if(!check_start){
						e.setCancelled(true);
					} else if ((e.getCause() == EntityDamageEvent.DamageCause.FALL))
				      {
				        e.setCancelled(true);
				        p.damage(e.getDamage());
				      }
				}
			}
		}
		
		  public static void Givechestitem(Player p){
			  int num = Getrandom(0,35);
			  p.getWorld().playSound(p.getLocation(), Sound.CHEST_OPEN, 1.0f, 1.0f);
			  if(num == 35){
				  p.sendMessage("��7�ƹ��� �����۵� ã�� �� ����.");
				  return;
			  }
			  if(num == 34){
				  p.sendMessage("��7�⵿��Ϲۿ� ������ �ʴ´�.");
				  return;
			  }
			  if(num == 33){
				  p.sendMessage("��7Ư���Ѱ� �־���� �ʴ´�.");
				  return;
			  }
			  if(num == 8 || num == 19 || num == 10 || num == 27){
				  for(int i = 0; i <= Getrandom(1,5); i++)
					  p.getInventory().addItem(chestitem[num]);
				  p.updateInventory();
				  return;
			  }
			  p.getInventory().addItem(chestitem[num]);
			  p.updateInventory();
		  }
		  
		  public static int getItemDamage(Player p){
			  ItemStack item = p.getItemInHand();
			  if(item == null || !item.hasItemMeta()) return 1;
			  if(!item.getItemMeta().hasLore()) return 1;
			  List<String> lorelist = item.getItemMeta().getLore();
			  if(lorelist.size() <= 0) return 1;
			  String damagestr = lorelist.get(0);
			  if(!damagestr.contains("���ݷ�")) return 1;
			  if(!damagestr.contains("~"))return Integer.valueOf(damagestr.substring(7, 8));
			  int min = Integer.valueOf(damagestr.substring(7, 8));
			  int max = Integer.valueOf(damagestr.substring(9, 10));
			  return (int)(Math.random()*(max-min+1)+min);
		  }
		
		@EventHandler
		public void onPvp(EntityDamageByEntityEvent e){
			Player p = null;
			Player d = null;
			if(!(e.getEntity() instanceof Player)) return;
			p = (Player) e.getEntity();
			if(!plist.contains(p)) return;
			if(e.getDamager() instanceof Snowball){
				Snowball snowball = (Snowball) e.getDamager();
				if(snowball.getShooter() instanceof Player){
					d = (Player) snowball.getShooter();
					e.setDamage(7);
				}
			}
			if(e.getDamager() instanceof Arrow){
				Arrow arrow = (Arrow) e.getDamager();
				if(arrow.getShooter() instanceof Player){
					d = (Player) arrow.getShooter();
				}
			}
			if(e.getDamager() instanceof Player){
				d = (Player) e.getDamager();
			}
		      if(d != null && d.getItemInHand() != null && d.getItemInHand().getType() != Material.AIR && getItemDamage(d) != 0){
		    	  e.setDamage(getItemDamage(d));
		      }

			if(plist.contains(p) && plist.contains(d)){
				if((p.getHealth()-e.getDamage()) <= 0){
					int killerwool = woollist.indexOf(d.getName());
					int playerwool = woollist.indexOf(p.getName());
					if((killerwool+1) == woollist.size()){
						killerwool = -1;
					}
					if((killerwool+1) != playerwool){
						d.sendMessage(MS+"�߸��� �÷��̾ �׿� Ż���߽��ϴ�!");
						GameQuit(d);
						d.damage(100);
						p.setHealth(20);
						p.sendMessage(MS+"��6"+d.getName()+" ��f���� Ÿ���� ����� �ƴϾ����ϴ�!");
					}
				}
			}
		}		
		
		  @EventHandler
		  public void onRightClick(PlayerInteractEvent e){
			  if(!plist.contains(e.getPlayer()) || (e.getAction() != Action.RIGHT_CLICK_AIR && e.getAction() != Action.RIGHT_CLICK_BLOCK)) return;
			  Player p = e.getPlayer();
			  if(e.getClickedBlock() != null && e.getClickedBlock().getType() == Material.BOOKSHELF 
					  && check_start){
				  Location bl = e.getClickedBlock().getLocation();
				  Location bx = boxpos[enabled_box];
				  if(!(bl.getBlockX() == bx.getBlockX() && bl.getBlockY() == bx.getBlockY() && bl.getBlockZ() == bx.getBlockZ())) return;
				  if(enabled_name != null){
					  p.sendMessage(MS+"�̹� "+enabled_name+" �Բ��� ��ȭ�ڽ��� ����߽��ϴ�.");
					  return;
				  }
				  enabled_name = p.getName();
				  Sendmessage(MS+p.getName()+" ���� ��ȭ�ڽ��� ����߽��ϴ�!");
				  p.openInventory(playerlist);
			  }
			  if(e.getClickedBlock() != null && e.getClickedBlock().getTypeId() == 54 && check_start){
				  e.setCancelled(true);
				  final Location bl = e.getClickedBlock().getLocation();
				  if(!chestlist.contains(bl)){
					  chestlist.add(bl);
					  Givechestitem(p);
					  Bukkit.getScheduler().scheduleSyncDelayedTask(this, new Runnable(){
						  public void run(){
							  chestlist.remove(bl);
						  }
					  }, 1200L);
				  } else {
					  p.sendMessage("��7�̹� ������ �о �� �ϴ�.");
				  }
			  }
			  if(e.getItem() == null) return;
				if(e.getItem().getType() == Material.COMPASS)
					  p.openInventory(gamehelper);
				else if(e.getItem().getType() == Material.IRON_HOE){
				  if(!Takeitem(p, 362, 1)){
					  p.sendMessage("��c9mmźȯ�� �����մϴ�."); return;
				  }
				  Snowball snowball = p.launchProjectile(Snowball.class); //here we launch the snowball
				  Block target = p.getTargetBlock(null, 50);
				  Vector velocity = (target.getLocation().toVector().subtract(snowball.getLocation().toVector()).normalize()).multiply(7);
				  snowball.setVelocity(velocity);
				  snowball.setShooter(p);
				  p.getWorld().playSound(p.getLocation(), Sound.ANVIL_LAND, 4.0f, 2.0f);
				  p.getWorld().playEffect(e.getPlayer().getLocation(), Effect.SMOKE, 20); //We will play a smoke effect at the shooter's location
			  } else if(e.getItem().getTypeId() == 377){
				  p.addPotionEffect(new PotionEffect(PotionEffectType.INVISIBILITY, 300, 1));
				  Removeitem(p, 377, 1);
				  p.sendMessage(MS+"���� ���並 ��� �߽��ϴ�. 15�ʰ� ����� ����ϴ�.");
			  } else if(e.getItem().getTypeId() == 264){
				  p.addPotionEffect(new PotionEffect(PotionEffectType.DAMAGE_RESISTANCE, 100, 1));
				  Removeitem(p, 264, 1);
				  p.sendMessage(MS+"������ ���� ��� �߽��ϴ�.  5�ʰ� ����2�� �ްԵ˴ϴ�.");
			  }
		  }
		  
		  @EventHandler
		  public void onClickInventory(InventoryClickEvent e){
			  if(!(e.getWhoClicked() instanceof Player)) return; 
			  Player p = (Player) e.getWhoClicked();
			  if(!plist.contains(p)) return;
			  if(e.getInventory().getTitle().equalsIgnoreCase("��c��l�����")){
				  GameHelper(p, e.getSlot());
				  e.setCancelled(true);
			  }
			  if(e.getInventory().getTitle().equalsIgnoreCase("��c��l��ȭ�ڽ�")){
				  e.setCancelled(true);
				  if(e.getCurrentItem() == null || e.getCurrentItem().getType() == Material.AIR)return;
				  p.closeInventory();
				  UseBox(p, e.getCurrentItem().getItemMeta().getDisplayName());
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
			public void onPlayerdeath(PlayerDeathEvent e)
			{
				Player p = e.getEntity();
				if(plist.contains(p))
				{
					for(int i = 0; i < 9; i++){
						e.getDrops().remove(wool[i]);
					}
					GameQuit(p);
				}
			}
}
