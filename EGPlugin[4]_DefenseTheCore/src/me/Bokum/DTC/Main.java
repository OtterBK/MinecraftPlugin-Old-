package me.Bokum.DTC;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Color;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Arrow;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.player.PlayerChatEvent;
import org.bukkit.event.player.PlayerCommandPreprocessEvent;
import org.bukkit.event.player.PlayerDropItemEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.event.player.PlayerRespawnEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.PlayerInventory;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.inventory.meta.LeatherArmorMeta;
import org.bukkit.plugin.RegisteredServiceProvider;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.util.Vector;

import net.milkbowl.vault.economy.Economy;
import net.milkbowl.vault.economy.EconomyResponse;

public class Main extends JavaPlugin implements Listener
{
	public static boolean check_start = false;
	public static boolean check_lobbystart = false;
	public static List<Player> plist = new ArrayList<Player>(); 
	public static String MS = "��f[ ��aEG ��f] ";
	public static Location Join_loc;
	public static Location Main_loc;
	public static Location Bluespawn_loc;
	public static Location Redspawn_loc;
	public static Location Redcore_loc;
	public static Location Bluecore_loc;
	public static Location RedArrive_loc;
	public static Location BlueArrive_loc;
	public static int tasknum[] = new int[50];
	public static int tasktime[] = new int[50];
	public static int timernum = 0;
	public static int timertime = 15;
	public static List<Player> bluelist = new ArrayList<Player>();
	public static List<Player> redlist = new ArrayList<Player>();
	public static Inventory jobs;
	public static boolean game_end = false;
	public static HashMap<String, Integer> cooldown = new HashMap<String, Integer>();
	public static HashMap<String, String> jlist = new HashMap<String, String>();
	public static List<String> archer1 = new ArrayList<String>();
	public static List<String> archer2 = new ArrayList<String>();
	public static List<String> healer1 = new ArrayList<String>();
	public static List<String> assasin1 = new ArrayList<String>();
	public static List<String> teamchat = new ArrayList<String>();
	public static int Bluecore_cnt = 0;
	public static int Redcore_cnt = 0;
	public static ItemStack helpitem;
	public static ItemStack redFlag;
	public static ItemStack blueFlag;
	public static Inventory gamehelper;
	public static ItemStack bluearmor1 = null;
	public static ItemStack bluearmor2 = null;
	public static ItemStack bluearmor3 = null;
	public static ItemStack bluearmor4 = null;
	public static ItemStack redarmor1 = null;
	public static ItemStack redarmor2 = null;
	public static ItemStack redarmor3 = null;
	public static ItemStack redarmor4 = null;
	public static Main instance;
	public static Economy econ = null;
	
	public void LoadConfig()
	{
		ConfigurationSection sec = getConfig().getConfigurationSection("Location_Join");
		Join_loc = new Location(getServer().getWorld(sec.getString("world")), sec.getInt("x"), sec.getInt("y"), sec.getInt("z"));
		sec = getConfig().getConfigurationSection("Location_Main");
		Main_loc = new Location(getServer().getWorld(sec.getString("world")), sec.getInt("x"), sec.getInt("y"), sec.getInt("z"));
		sec = getConfig().getConfigurationSection("Location_Blue");
		Bluespawn_loc = new Location(getServer().getWorld(sec.getString("world")), sec.getInt("x"), sec.getInt("y"), sec.getInt("z"));
		sec = getConfig().getConfigurationSection("Location_Red");
		Redspawn_loc = new Location(getServer().getWorld(sec.getString("world")), sec.getInt("x"), sec.getInt("y"), sec.getInt("z"));
		sec = getConfig().getConfigurationSection("Location_BlueCore");
		Bluecore_loc = new Location(getServer().getWorld(sec.getString("world")), sec.getInt("x"), sec.getInt("y"), sec.getInt("z"));
		sec = getConfig().getConfigurationSection("Location_RedCore");
		Redcore_loc = new Location(getServer().getWorld(sec.getString("world")), sec.getInt("x"), sec.getInt("y"), sec.getInt("z"));
		sec = getConfig().getConfigurationSection("Location_RedArrive");
		RedArrive_loc = new Location(getServer().getWorld(sec.getString("world")), sec.getInt("x"), sec.getInt("y"), sec.getInt("z"));
		sec = getConfig().getConfigurationSection("Location_BlueArrive");
		BlueArrive_loc = new Location(getServer().getWorld(sec.getString("world")), sec.getInt("x"), sec.getInt("y"), sec.getInt("z"));
	}
	
	public void Createjobsinv()
	{
		jobs = Bukkit.createInventory(null, 9, "��������");
		ItemStack item = new ItemStack(Material.STONE_SWORD, 1);
		ItemMeta meta = item.getItemMeta();
		meta.setDisplayName("��e��Ŀ");
		item.setItemMeta(meta);
		jobs.setItem(0, item);
		
		item = new ItemStack(Material.BOW, 1);
		meta = item.getItemMeta();
		meta.setDisplayName("��e����");
		item.setItemMeta(meta);
		jobs.setItem(2, item);
		
		item = new ItemStack(Material.IRON_SWORD, 1);
		meta = item.getItemMeta();
		meta.setDisplayName("��e������");
		item.setItemMeta(meta);
		jobs.setItem(4, item);
		
		item = new ItemStack(Material.DIAMOND_SWORD, 1);
		meta = item.getItemMeta();
		meta.setDisplayName("��e��ؽ�");
		item.setItemMeta(meta);
		jobs.setItem(6, item);
		
		item = new ItemStack(Material.WOOD_SWORD, 1);
		meta = item.getItemMeta();
		meta.setDisplayName("��e����");
		item.setItemMeta(meta);
		jobs.setItem(8, item);
	}
	
	public static int Getcursch()
	{
		for(int i = 0 ; i < 200; i++)
		{
			if(tasknum[i] == -5)
			{
				return i;
			}
		}
		return 0;
	}
	
	public void onEnable()
	{
		getServer().getPluginManager().registerEvents(this, this);
		getLogger().info("CTF �÷������� �ε� �Ǿ����ϴ�.");
		LoadConfig();
		Createjobsinv();
		for(int i = 0 ; i < tasknum.length; i++)
		{
			tasknum[i] = -5;
			tasktime[i] = -5;
		}
		instance = this;
		bluearmor4 = new ItemStack(Material.LEATHER_BOOTS, 1);
		LeatherArmorMeta meta4 = (LeatherArmorMeta) bluearmor4.getItemMeta();
		meta4.setColor(Color.BLUE);
		bluearmor4.setItemMeta(meta4);

		bluearmor3 = new ItemStack(Material.LEATHER_LEGGINGS, 1);
		LeatherArmorMeta meta3 = (LeatherArmorMeta) bluearmor3.getItemMeta();
		meta3.setColor(Color.BLUE);
		bluearmor3.setItemMeta(meta3);

		bluearmor2 = new ItemStack(Material.LEATHER_CHESTPLATE, 1);
		LeatherArmorMeta meta2 = (LeatherArmorMeta) bluearmor2.getItemMeta();
		meta2.setColor(Color.BLUE);
		bluearmor2.setItemMeta(meta2);

		bluearmor1 = new ItemStack(Material.LEATHER_HELMET, 1);
		LeatherArmorMeta meta1 = (LeatherArmorMeta) bluearmor1.getItemMeta();
		meta1.setColor(Color.BLUE);
		bluearmor1.setItemMeta(meta1);

		redarmor1 = new ItemStack(Material.LEATHER_HELMET, 1);
		LeatherArmorMeta meta = (LeatherArmorMeta) redarmor1.getItemMeta();
		meta.setColor(Color.RED);
		redarmor1.setItemMeta(meta);

		redarmor2 = new ItemStack(Material.LEATHER_CHESTPLATE, 1);
		meta = (LeatherArmorMeta) redarmor2.getItemMeta();
		meta.setColor(Color.RED);
		redarmor2.setItemMeta(meta);

		redarmor3 = new ItemStack(Material.LEATHER_LEGGINGS, 1);
		meta = (LeatherArmorMeta) redarmor3.getItemMeta();
		meta.setColor(Color.RED);
		redarmor3.setItemMeta(meta);

		redarmor4 = new ItemStack(Material.LEATHER_BOOTS, 1);
		meta = (LeatherArmorMeta) redarmor4.getItemMeta();
		meta.setColor(Color.RED);
		redarmor4.setItemMeta(meta);

		gamehelper = Bukkit.createInventory(null, 27, "��c��l�����");
		
		ItemStack item = new ItemStack(34, 1);
		ItemMeta metatmp = item.getItemMeta();
		metatmp.setDisplayName("��6���");
		item.setItemMeta(metatmp);
		for(int i = 0; i <= 9; i++){
			gamehelper.setItem(i, item);
		}
		for(int i = 17; i < 27; i++){
			gamehelper.setItem(i, item);
		}
		
		item = new ItemStack(Material.BOOK, 1);
		metatmp = item.getItemMeta();
		metatmp.setDisplayName("��e�÷��� ���");
		item.setItemMeta(metatmp);
		gamehelper.setItem(11, item);
		
		item = new ItemStack(Material.BOOK, 1);
		metatmp = item.getItemMeta();
		metatmp.setDisplayName("��e���� ��Ģ");
		item.setItemMeta(metatmp);
		gamehelper.setItem(13, item);
		
		item = new ItemStack(Material.BOOK, 1);
		metatmp = item.getItemMeta();
		metatmp.setDisplayName("��e��ä�� on/off");
		List<String> lorelist1 = new ArrayList<String>();
		lorelist1.add("��f- ��c������ ���۵� �� ����� �� �ֽ��ϴ�.");
		item.setItemMeta(metatmp);
		gamehelper.setItem(15, item);
		
		helpitem = new ItemStack(345, 1);
		metatmp = helpitem.getItemMeta();
		metatmp.setDisplayName("��f[ ��6���� ����� ��f]");
		lorelist1.clear();
		lorelist1.add("��f- ��7��Ŭ���� ���� ����� ���ϴ�.");
		meta1.setLore(lorelist1);
		helpitem.setItemMeta(metatmp);
		
		redFlag = new ItemStack(35, 1, (byte) 14);
		metatmp = redFlag.getItemMeta();
		metatmp.setDisplayName("��f[ ��4������ ��� ��f]");
		redFlag.setItemMeta(metatmp);
		
		blueFlag = new ItemStack(35, 1, (byte) 11);
		metatmp = blueFlag.getItemMeta();
		metatmp.setDisplayName("��f[ ��b����� ��� ��f]");
		blueFlag.setItemMeta(metatmp);
		
        if (!setupEconomy() ) {
            getLogger().info("[ ���� �߻� ��� ] Vault�÷������� �νĵ��� �ʾҽ��ϴ�!");
            getServer().getPluginManager().disablePlugin(this);
            return;
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
		getLogger().info("CTF �÷������� ��ε� �Ǿ����ϴ�.");
		saveConfig();
	}
	
	public boolean onCommand(CommandSender talker, Command command, String string, String args[])
	{
		if(talker instanceof Player)
		{
			Player p = (Player) talker;
			if(string.equalsIgnoreCase("dtc") && p.hasPermission("dtc"))
			{
				if(args.length >= 1)
				{
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
					if(args[0].equalsIgnoreCase("job"))
					{
						p.openInventory(jobs);
						return true;
					}
					if(args[0].equalsIgnoreCase("set"))
					{
						Setlocation(p, args);
						return true;
					}
					if(args[0].equalsIgnoreCase("stop"))
					{
						Forcestop();
						return true;
					}
				}
				else
				{
					Helpmessages(p);
				}
			}
		}
		return false;
	}
	
	public void Helpmessages(Player p)
	{
		p.sendMessage(MS+"/dtc join");
		p.sendMessage(MS+"/dtc quit");
		p.sendMessage(MS+"/dtc jobs");
		p.sendMessage(MS+"/dtc set");
		p.sendMessage(MS+"/dtc stop");
		p.sendMessage(MS+"���� 0.61");
	}
	
	public void Setlocation(Player p, String[] args)
	{
		if(args.length >= 2)
		{
			if(args[1].equalsIgnoreCase("join"))
			{
				if(!getConfig().isConfigurationSection("Location_Join"))
				{
					getConfig().createSection("Location_Join");
				}
				getConfig().getConfigurationSection("Location_Join").set("world", p.getWorld().getName());
				getConfig().getConfigurationSection("Location_Join").set("x", p.getLocation().getBlockX());
				getConfig().getConfigurationSection("Location_Join").set("y", p.getLocation().getBlockY()+1);
				getConfig().getConfigurationSection("Location_Join").set("z", p.getLocation().getBlockZ());
				p.sendMessage(MS+"���� ���� ������");
			}
			if(args[1].equalsIgnoreCase("main"))
			{
				if(!getConfig().isConfigurationSection("Location_Main"))
				{
					getConfig().createSection("Location_Main");
				}
				getConfig().getConfigurationSection("Location_Main").set("world", p.getWorld().getName());
				getConfig().getConfigurationSection("Location_Main").set("x", p.getLocation().getBlockX());
				getConfig().getConfigurationSection("Location_Main").set("y", p.getLocation().getBlockY()+1);
				getConfig().getConfigurationSection("Location_Main").set("z", p.getLocation().getBlockZ());
				p.sendMessage(MS+"�κ� ������");
			}
			if(args[1].equalsIgnoreCase("blue"))
			{
				if(!getConfig().isConfigurationSection("Location_Blue"))
				{
					getConfig().createSection("Location_Blue");
				}
				getConfig().getConfigurationSection("Location_Blue").set("world", p.getWorld().getName());
				getConfig().getConfigurationSection("Location_Blue").set("x", p.getLocation().getBlockX());
				getConfig().getConfigurationSection("Location_Blue").set("y", p.getLocation().getBlockY()+1);
				getConfig().getConfigurationSection("Location_Blue").set("z", p.getLocation().getBlockZ());
				p.sendMessage(MS+"��� ���� ������");
			}
			if(args[1].equalsIgnoreCase("red"))
			{
				if(!getConfig().isConfigurationSection("Location_Red"))
				{
					getConfig().createSection("Location_Red");
				}
				getConfig().getConfigurationSection("Location_Red").set("world", p.getWorld().getName());
				getConfig().getConfigurationSection("Location_Red").set("x", p.getLocation().getBlockX());
				getConfig().getConfigurationSection("Location_Red").set("y", p.getLocation().getBlockY()+1);
				getConfig().getConfigurationSection("Location_Red").set("z", p.getLocation().getBlockZ());
				p.sendMessage(MS+"���� ���� ������");
			}
			if(args[1].equalsIgnoreCase("redcore"))
			{
				if(!getConfig().isConfigurationSection("Location_RedCore"))
				{
					getConfig().createSection("Location_RedCore");
				}
				getConfig().getConfigurationSection("Location_RedCore").set("world", p.getWorld().getName());
				getConfig().getConfigurationSection("Location_RedCore").set("x", p.getLocation().getBlockX());
				getConfig().getConfigurationSection("Location_RedCore").set("y", p.getLocation().getBlockY()-1);
				getConfig().getConfigurationSection("Location_RedCore").set("z", p.getLocation().getBlockZ());
				p.sendMessage(MS+"���� �ھ� ������");
			}
			if(args[1].equalsIgnoreCase("bluecore"))
			{
				if(!getConfig().isConfigurationSection("Location_BlueCore"))
				{
					getConfig().createSection("Location_BlueCore");
				}
				getConfig().getConfigurationSection("Location_BlueCore").set("world", p.getWorld().getName());
				getConfig().getConfigurationSection("Location_BlueCore").set("x", p.getLocation().getBlockX());
				getConfig().getConfigurationSection("Location_BlueCore").set("y", p.getLocation().getBlockY()-1);
				getConfig().getConfigurationSection("Location_BlueCore").set("z", p.getLocation().getBlockZ());
				p.sendMessage(MS+"��� �ھ� ������");
			}
			if(args[1].equalsIgnoreCase("redarrive"))
			{
				if(!getConfig().isConfigurationSection("Location_Arrive"))
				{
					getConfig().createSection("Location_RedArrive");
				}
				getConfig().getConfigurationSection("Location_RedArrive").set("world", p.getWorld().getName());
				getConfig().getConfigurationSection("Location_RedArrive").set("x", p.getLocation().getBlockX());
				getConfig().getConfigurationSection("Location_RedArrive").set("y", p.getLocation().getBlockY()-1);
				getConfig().getConfigurationSection("Location_RedArrive").set("z", p.getLocation().getBlockZ());
				p.sendMessage(MS+"���� ������ ������");
			}
			if(args[1].equalsIgnoreCase("bluearrive"))
			{
				if(!getConfig().isConfigurationSection("Location_BlueArrive"))
				{
					getConfig().createSection("Location_BlueArrive");
				}
				getConfig().getConfigurationSection("Location_BlueArrive").set("world", p.getWorld().getName());
				getConfig().getConfigurationSection("Location_BlueArrive").set("x", p.getLocation().getBlockX());
				getConfig().getConfigurationSection("Location_BlueArrive").set("y", p.getLocation().getBlockY()-1);
				getConfig().getConfigurationSection("Location_BlueArrive").set("z", p.getLocation().getBlockZ());
				p.sendMessage(MS+"��� ������ ������");
			}
			saveConfig();
			LoadConfig();
		}
		else
		{
			p.sendMessage(MS+"/dtc set join");
			p.sendMessage(MS+"/dtc set main");
			p.sendMessage(MS+"/dtc set blue");
			p.sendMessage(MS+"/dtc set red");
			p.sendMessage(MS+"/dtc set bluecore");
			p.sendMessage(MS+"/dtc set redcore");
			p.sendMessage(MS+"/dtc set bluearrive");
			p.sendMessage(MS+"/dtc set redarrive");
		}
	}
	
	public static void Sendmessage(String str)
	{
		for(Player p : plist)
		{
			p.sendMessage(str);
		}
	}
	
	public void Bluemessage(String str)
	{
		for(Player p : bluelist)
		{
			p.sendMessage(str);
		}
	}
	
	public void Redmessage(String str)
	{
		for(Player p : redlist)
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
		if(plist.size() >= 20)
		{
			p.sendMessage(MS+"�̹� �ִ��ο�(20)�Դϴ�. �ش� ������ ���߿� ������ �����ϱ⶧���� ���߿� �ٽ� �õ����ּ���.");
			return;
		}
		if(check_start)
		{
			plist.add(p);
			if(bluelist.size() <= redlist.size())
			{
				bluelist.add(p);
				p.getInventory().clear();
				Bukkit.dispatchCommand(p.getServer().getConsoleSender(), "scoreboard teams join blue "+p.getName());
				p.teleport(Bluespawn_loc);
			}
			else
			{
				redlist.add(p);
				p.getInventory().clear();
				Bukkit.dispatchCommand(p.getServer().getConsoleSender(), "scoreboard teams join red "+p.getName());
				p.teleport(Redspawn_loc);
			}
			p.sendMessage(MS+"CTF ���ӿ� �ߵ� �����ϼ̽��ϴ�.");
		}
		else
		{
			plist.add(p);
			p.teleport(Join_loc);
			p.getInventory().clear();
			p.getInventory().setHelmet(null);
			p.getInventory().setChestplate(null);
			p.getInventory().setLeggings(null);
			p.getInventory().setBoots(null);
			p.getInventory().setItem(8, helpitem);
			me.Bokum.EGM.Main.gamelist.put(p.getName(), "CTF");
			Sendmessage(MS+p.getName()+" ���� CTF ���ӿ� �����ϼ̽��ϴ�. �ο� (��e "+plist.size()+"��7 / ��c8 ��f)");
			if(!(check_start || check_lobbystart) && plist.size() >= 8)
			{
				Startgame();
				Bluecore_loc.getBlock().setTypeIdAndData(35, (byte) 11, true);
				Redcore_loc.getBlock().setTypeIdAndData(35, (byte) 14, true);
			}
			for(Player t : plist){
				t.playSound(t.getLocation(), Sound.NOTE_PLING, 2.0f, 1.0f);
			}
		}
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
		Bukkit.broadcastMessage(MS+"��e��lCTF ��f������ �� ���۵˴ϴ�.");
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
					for(Player p : plist)
					{
						if(bluelist.size() <= redlist.size())
						{
							bluelist.add(p);
							p.teleport(Bluespawn_loc);
							p.sendMessage(MS+"����� ������Դϴ�. �������� �������ּ���.");
							Bukkit.dispatchCommand(p.getServer().getConsoleSender(), "scoreboard teams join blue "+p.getName());
							p.setDisplayName("��b"+p.getName());
						}
						else
						{
							redlist.add(p);
							p.teleport(Redspawn_loc);
							p.sendMessage(MS+"����� �������Դϴ�. �������� �������ּ���.");
							Bukkit.dispatchCommand(p.getServer().getConsoleSender(), "scoreboard teams join red "+p.getName());
							p.setDisplayName("��c"+p.getName());
						}
					}
					check_start = true;
				}
			}
		}, 200L, 200L);
	}
	
	  public static void GameHelper(Player p, int slot){
		  switch(slot){
		  case 11:
			  p.sendMessage("��7���� �̸� ��f: ��cCTF");
			  p.sendMessage("��f������ ���۵Ǹ� ��c��������f�� ��b�������f���� �������ϴ�.");
			  p.sendMessage("��f�� ������ ��c���(�� ������ ����)��f�̶�� ���� �����ϸ�");
			  p.sendMessage("��f����� ���(����)�� ĳ�� ����� ȹ���ϰԵ˴ϴ�. ");
			  p.sendMessage("��fȹ���� ����� �� ���� �����ϴ� ��b������(���޶��� ��)��f���� �����ϸ�");
			  p.sendMessage("��f��� ������ �ö󰡰Ե˴ϴ�. �� 5���� ����� �����ϸ� �¸��մϴ�.");
			  p.sendMessage("��b����ġ�� : ��7����� ����� 20�ʸ��� ������˴ϴ�. ����ġ�ٷ� �� �ð��� �� �� �ֽ��ϴ�.");
			  p.sendMessage("��b���� : ��7�ڽ��� ���� ȸ���� ����� ����� ��Ÿ���ϴ�.");
			  p.closeInventory();
			  return;
			  
		  case 13:
			  p.sendMessage("��f���� �������� ����ų�� �ϴ� ���� �Ұ����մϴ�.");
			  p.sendMessage("��c�� ��ų�� �ڿ������� �����ִ� ����������.");
			  p.closeInventory();
			  return;
			  
		  case 15:
			  if(!check_start) {
				  p.sendMessage("������ ���۵� �� ����Ͻ� �� �ֽ��ϴ�.");
				  p.closeInventory();
				  return;
			  }
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
		if(bluelist.contains(p))
		{
			bluelist.remove(p);
		}
		if(redlist.contains(p))
		{
			redlist.remove(p);
		}
		p.teleport(Main_loc);
	}
	
	public static boolean Checkcooldown(Player p, String str)
	{
		if(!Main.cooldown.containsKey(p.getName()+str) || Main.cooldown.get(p.getName()+str) <= (int)(java.lang.System.currentTimeMillis()/1000))
		{
			return true;
		}
		p.sendMessage(MS+ChatColor.AQUA+(Main.cooldown.get(p.getName()+str)-(int)(java.lang.System.currentTimeMillis()/1000))
				+ChatColor.RESET+"�� �� ��ų�� ����Ͻ� �� �ֽ��ϴ�.");
		return false;
	}
	
	public void Setcooldown(String str, int cooldown)
	{
		Main.cooldown.put(str, (int)(java.lang.System.currentTimeMillis()/1000)+cooldown);
	}
	
	public void Skill(final Player p, int id)
	{
		if(id == 347)
		{
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
			return;
		}
		
		if(!jlist.containsKey(p.getName()))
		{
			return;
		}
		
		switch(jlist.get(p.getName()))
		{
		case "��Ŀ":
			switch(id)
			{
			
			case 264:
				if(Checkcooldown(p, "1"))
				{
					Setcooldown(p.getName()+"1", 8);
					p.getWorld().playSound(p.getLocation(), Sound.ANVIL_LAND, 2.0f, 0.5f);
					p.addPotionEffect(new PotionEffect(PotionEffectType.DAMAGE_RESISTANCE, 60, 2), true);
					Bukkit.getScheduler().scheduleSyncDelayedTask(this, new Runnable()
							{
								public void run()
								{
									p.addPotionEffect(new PotionEffect(PotionEffectType.DAMAGE_RESISTANCE, 72000, 0));
								}
							}, 60L);
					return;
				}
				
				break;
				
			case 388:
				if(Checkcooldown(p, "2"))
				{
					Setcooldown(p.getName()+"2", 17);
					p.getWorld().playSound(p.getLocation(), Sound.ANVIL_BREAK, 2.0f, 0.5f);
					for(Player t : (bluelist.contains(p) ? redlist : bluelist))
					{
						if(t.getLocation().distance(p.getLocation()) <= 6)
						{
							t.addPotionEffect(new PotionEffect(PotionEffectType.SLOW, 40, 31));
							t.addPotionEffect(new PotionEffect(PotionEffectType.JUMP, 40, 240));
						}
					}
					return;
				}
				
				break;
				
			case 265:
				if(Checkcooldown(p, "3"))
				{
					Setcooldown(p.getName()+"3", 18);
					p.getWorld().playSound(p.getLocation(), Sound.ANVIL_USE, 2.0f, 0.5f);
					for(Player t : (!bluelist.contains(p) ? redlist : bluelist))
					{
						if(t.getLocation().distance(p.getLocation()) <= 6 && !jlist.get(t.getName()).equalsIgnoreCase("��Ŀ"))
						{
							t.addPotionEffect(new PotionEffect(PotionEffectType.DAMAGE_RESISTANCE, 60, 1));
							t.sendMessage(MS+"��Ŀ "+p.getName()+" ���� �ɷ����� ���� ������ �ö󰩴ϴ�.");
						}
					}
					return;
				}
				
				break;
				
			default: return;
			
			}  break;
			
		case "����":
			switch(id)
			{
			
			case 264:
				if(Checkcooldown(p, "1"))
				{
					Setcooldown(p.getName()+"1", 12);
					archer1.add(p.getName());
					p.getWorld().playSound(p.getLocation(), Sound.BLAZE_HIT, 2.0f, 0.5f);
					Bukkit.getScheduler().scheduleSyncDelayedTask(this, new Runnable()
							{
								public void run()
								{
									if(archer1.contains(p.getName()))
									{
										archer1.remove(p.getName());
										p.sendMessage(MS+"Ȯ�λ���� ���ӽð��� �������ϴ�.");
									}
								}
							}, 80L);
					return;
				}
				
				break;
				
			case 388:
				if(Checkcooldown(p, "2"))
				{
					Setcooldown(p.getName()+"2", 14);
					archer2.add(p.getName());
					p.getWorld().playSound(p.getLocation(), Sound.IRONGOLEM_HIT, 2.0f, 0.5f);
					Bukkit.getScheduler().scheduleSyncDelayedTask(this, new Runnable()
							{
								public void run()
								{
									if(archer2.contains(p.getName()))
									{
										archer2.remove(p.getName());
										p.sendMessage(MS+"����ȭ���� ���ӽð��� �������ϴ�.");
									}
								}
							}, 80L);
					return;
				}
				
				break;
				
			default: return;
			
			}  break;
			
		case "��ؽ�":
			switch(id)
			{
			
			case 264:
				if(Checkcooldown(p, "1"))
				{
					Setcooldown(p.getName()+"1", 12);
					p.getWorld().playSound(p.getLocation(), Sound.CREEPER_HISS, 2.0f, 0.5f);
					PotionEffect potion = new PotionEffect(PotionEffectType.INVISIBILITY, 120, 1);
					p.addPotionEffect(potion);
					final ItemStack[] armor = p.getInventory().getArmorContents();
					p.getInventory().setArmorContents(null);
					p.updateInventory();
					Bukkit.getScheduler().scheduleSyncDelayedTask(this, new Runnable()
					{
						public void run()
						{
							p.getInventory().setArmorContents(armor);
							p.updateInventory();
							if(assasin1.contains(p.getName()))
							{
								assasin1.remove(p.getName());
								p.sendMessage(MS+"������ Ǯ�� �ϻ콺ų�� ���� �ƽ��ϴ�.");
							}
						}
					}, 120);
				}
				
				break;
				
			case 388:
				if(Checkcooldown(p, "2"))
				{
					if(!p.hasPotionEffect(PotionEffectType.INVISIBILITY))
					{
						p.sendMessage(MS+"���� �����϶��� ��� �����մϴ�.");
						return;
					}
					Setcooldown(p.getName()+"2", 10);
					assasin1.add(p.getName());
					p.getWorld().playSound(p.getLocation(), Sound.CREEPER_DEATH, 2.0f, 0.5f);
					return;
				}
				
				break;
				
			default: return;
			
			}  break;
			
		case "������":
			switch(id)
			{
			
			case 264:
				if(Checkcooldown(p, "1"))
				{
					Setcooldown(p.getName()+"1", 13);
					p.getWorld().playSound(p.getLocation(), Sound.GHAST_DEATH, 2.0f, 2.0f);
					p.addPotionEffect(new PotionEffect(PotionEffectType.INCREASE_DAMAGE, 60, 0), true);
					return;
				}
				
				break;
				
			case 388:
				if(Checkcooldown(p, "2"))
				{
					Setcooldown(p.getName()+"2", 11);
					p.getWorld().playSound(p.getLocation(), Sound.BAT_TAKEOFF, 2.0f, 1.5f);
					p.addPotionEffect(new PotionEffect(PotionEffectType.SPEED, 80, 0), true);
					return;
				}
				
				break;
				
			case 265:
				if(Checkcooldown(p, "3"))
				{
					Setcooldown(p.getName()+"3", 20);
					p.getWorld().playSound(p.getLocation(), Sound.EXPLODE, 2.0f, 1.2f);
					for(Player t : (bluelist.contains(p) ? redlist : bluelist))
					{
						if(t.getLocation().distance(p.getLocation()) <= 4)
						{
							t.setVelocity(new Vector(0,1.3f,0));
						}
					}
					return;
				}
				
				break;
				
			default: return;
			
			}  break;
			
			
			
		case "����":
			switch(id)
			{
			
			case 264:
				if(Checkcooldown(p, "1"))
				{
					Setcooldown(p.getName()+"1", 10);
					healer1.add(p.getName());
					p.getWorld().playSound(p.getLocation(), Sound.LEVEL_UP, 2.0f, 1.5f);
					Bukkit.getScheduler().scheduleSyncDelayedTask(this, new Runnable()
					{
						public void run()
						{
							if(healer1.contains(p.getName()))
							{
								healer1.remove(p.getName());
								p.sendMessage(MS+"ġ���� ȭ���� ���ӽð��� �������ϴ�.");
							}
						}
					}, 80L);
					return;
				}
				
				break;
				
			case 388:
				if(Checkcooldown(p, "2"))
				{
					Setcooldown(p.getName()+"2", 23);
					p.getWorld().playSound(p.getLocation(), Sound.ORB_PICKUP, 2.0f, 0.8f);
					for(Player t : (!bluelist.contains(p) ? redlist : bluelist))
					{
						if(t.getLocation().distance(p.getLocation()) <= 5)
						{
							int hp = t.getHealth();
							hp += 8;
							if(hp > 20)
							{
								hp = 20;
							}
							t.setHealth(hp);
							t.sendMessage(MS+"������ ����� ȸ�� ��ŵ�ϴ�.");
						}
					}
					return;
				}
				
				break;
				
			default: return;
			
			}  break;
			
		default: return;
		
		}
	}
	
	public void Setjob(Player p, String str)
	{
		p.closeInventory();
		for(PotionEffect effect : p.getActivePotionEffects())
		{
			p.removePotionEffect(effect.getType());
		}
		
		if(bluelist.contains(p))
		{
			p.getInventory().setHelmet(bluearmor1);
			p.getInventory().setChestplate(bluearmor2);
			p.getInventory().setLeggings(bluearmor3);
			p.getInventory().setBoots(bluearmor4);
		}
		else
		{
			p.getInventory().setHelmet(redarmor1);
			p.getInventory().setChestplate(redarmor2);
			p.getInventory().setLeggings(redarmor3);
			p.getInventory().setBoots(redarmor4);
		}
		
		for(int i = 0; i < 36; i++)
		{
			p.getInventory().setItem(i, null);
		}
		
		ItemStack item;
		ItemMeta meta;
		List<String> lorelist = new ArrayList<String>();

		item = new ItemStack(Material.WATCH, 1);
		meta = item.getItemMeta();
		meta.setDisplayName("��e��ä��");
		lorelist.clear();
		lorelist.add("- ��7��Ŭ���� ��ê�� �Ѱų� ���ϴ�.");
		meta.setLore(lorelist);
		item.setItemMeta(meta);
		p.getInventory().setItem(8, item);
		
		switch(str)
		{
		case "��Ŀ":
			jlist.put(p.getName(), "��Ŀ");
			
			item = new ItemStack(Material.STONE_SWORD, 1);
			meta = item.getItemMeta();
			meta.setDisplayName("��b��Ŀ");
			meta.addEnchant(Enchantment.KNOCKBACK, 1, true);
			meta.addEnchant(Enchantment.DURABILITY, 10, true);
			item.setItemMeta(meta);
			p.getInventory().setItem(0, item);
			
			item = new ItemStack(Material.DIAMOND, 1);
			meta = item.getItemMeta();
			meta.setDisplayName("��bö��");
			lorelist.clear();
			lorelist.add("- ��73�ʰ� ����3");
			lorelist.add("- ��c��Ÿ�� 8��");
			meta.setLore(lorelist);
			item.setItemMeta(meta);
			p.getInventory().setItem(1, item);
			
			item = new ItemStack(Material.EMERALD, 1);
			meta = item.getItemMeta();
			meta.setDisplayName("��b����");
			lorelist.clear();
			lorelist.add("- ��7�ݰ� 6ĭ�̳� ���� 2�ʰ� �̵��Ұ�");
			lorelist.add("- ��c��Ÿ�� 17��");
			meta.setLore(lorelist);
			item.setItemMeta(meta);
			p.getInventory().setItem(2, item);
			
			item = new ItemStack(Material.IRON_INGOT, 1);
			meta = item.getItemMeta();
			meta.setDisplayName("��b�Լ�");
			lorelist.clear();
			lorelist.add("- ��7�ݰ� 6ĭ�̳� ���� ����1");
			lorelist.add("- ��c��Ÿ�� 18��");
			meta.setLore(lorelist);
			item.setItemMeta(meta);
			p.getInventory().setItem(3, item);
			
			p.addPotionEffect(new PotionEffect(PotionEffectType.DAMAGE_RESISTANCE, 72000, 0));
			
			break;
			
		case "����":
			jlist.put(p.getName(), "����");
			
			item = new ItemStack(Material.BOW, 1);
			meta = item.getItemMeta();
			meta.setDisplayName("��b����");
			meta.addEnchant(Enchantment.ARROW_INFINITE, 1, true);
			meta.addEnchant(Enchantment.ARROW_DAMAGE, 1, true);
			meta.addEnchant(Enchantment.DURABILITY, 10, true);
			item.setItemMeta(meta);
			p.getInventory().setItem(0, item);
			
			item = new ItemStack(Material.DIAMOND, 1);
			meta = item.getItemMeta();
			meta.setDisplayName("��bȮ�λ��");
			lorelist.clear();
			lorelist.add("- ��7����� ȭ��� ���� �����");
			lorelist.add("- ��7������ 1.5��, �� 4���̳��� ������Ѵ�.");
			lorelist.add("- ��c��Ÿ�� 12��");
			meta.setLore(lorelist);
			item.setItemMeta(meta);
			p.getInventory().setItem(1, item);
			
			item = new ItemStack(Material.EMERALD, 1);
			meta = item.getItemMeta();
			meta.setDisplayName("��b����ȭ��");
			lorelist.clear();
			lorelist.add("- ��7����� ȭ��� ���� �����");
			lorelist.add("- ��7�Ǹ�1 8��, �� 4���̳��� ������Ѵ�.");
			lorelist.add("- ��c��Ÿ�� 14��");
			meta.setLore(lorelist);
			item.setItemMeta(meta);
			p.getInventory().setItem(2, item);
			
			item = new ItemStack(Material.ARROW, 64);
			p.getInventory().setItem(3, item);
			
			break;
			
		case "��ؽ�":
			jlist.put(p.getName(), "��ؽ�");
			
			item = new ItemStack(Material.DIAMOND_SWORD, 1);
			meta = item.getItemMeta();
			meta.setDisplayName("��b��ؽ�");
			lorelist.clear();
			lorelist.add("- ��7�ڽ��� �޴� ��� ��������");
			lorelist.add("- ��71.3�谡 �˴ϴ�.");
			meta.setLore(lorelist);
			item.setItemMeta(meta);
			p.getInventory().setItem(0, item);
			
			item = new ItemStack(Material.DIAMOND, 1);
			meta = item.getItemMeta();
			meta.setDisplayName("��b����");
			lorelist.clear();
			lorelist.add("- ��76�ʰ� ����� ����ϴ�.");
			lorelist.add("- ��c��Ÿ�� 12��");
			meta.setLore(lorelist);
			item.setItemMeta(meta);
			p.getInventory().setItem(1, item);
			
			item = new ItemStack(Material.EMERALD, 1);
			meta = item.getItemMeta();
			meta.setDisplayName("��b�ϻ�");
			lorelist.clear();
			lorelist.add("- ��7���� ���¿����� ��밡��");
			lorelist.add("- ��7��� �� ù Ÿ�� ������ 1.5��");
			lorelist.add("- ��7������ ������ �� ��ų �ڵ�����");
			lorelist.add("- ��c��Ÿ�� 10��");
			meta.setLore(lorelist);
			item.setItemMeta(meta);
			p.getInventory().setItem(2, item);
			
			break;
			
		case "������":
			jlist.put(p.getName(), "������");
			
			item = new ItemStack(Material.IRON_SWORD, 1);
			meta = item.getItemMeta();
			meta.setDisplayName("��b������");
			meta.addEnchant(Enchantment.DURABILITY, 10, true);
			item.setItemMeta(meta);
			p.getInventory().setItem(0, item);
			
			item = new ItemStack(Material.DIAMOND, 1);
			meta = item.getItemMeta();
			meta.setDisplayName("��b����");
			lorelist.clear();
			lorelist.add("- ��73�ʰ� ���ݷ� ���");
			lorelist.add("- ��c��Ÿ�� 13��");
			meta.setLore(lorelist);
			item.setItemMeta(meta);
			p.getInventory().setItem(1, item);
			
			item = new ItemStack(Material.EMERALD, 1);
			meta = item.getItemMeta();
			meta.setDisplayName("��b����");
			lorelist.clear();
			lorelist.add("- ��74�ʰ� �̼�1 ����");
			lorelist.add("- ��c��Ÿ�� 11��");
			meta.setLore(lorelist);
			item.setItemMeta(meta);
			p.getInventory().setItem(2, item);
			
			item = new ItemStack(Material.IRON_INGOT, 1);
			meta = item.getItemMeta();
			meta.setDisplayName("��b�м�");
			lorelist.clear();
			lorelist.add("- ��7�ݰ� 3ĭ�̳��� ���� ���� ���");
			lorelist.add("- ��c��Ÿ�� 20��");
			meta.setLore(lorelist);
			item.setItemMeta(meta);
			p.getInventory().setItem(3, item);
			
			break;
			
		case "����":
			jlist.put(p.getName(), "����");
			
			item = new ItemStack(Material.BOW, 1);
			meta = item.getItemMeta();
			meta.setDisplayName("��b����");
			meta.addEnchant(Enchantment.ARROW_INFINITE, 1, true);
			lorelist.clear();
			lorelist.add("- ��7Ȱ�� ������ ����� ��������ŭ ü���� ȸ�������ݴϴ�.");
			meta.setLore(lorelist);
			item.setItemMeta(meta);
			p.getInventory().setItem(0, item);
			
			item = new ItemStack(Material.WOOD_SWORD, 1);
			meta = item.getItemMeta();
			meta.setDisplayName("��b����");
			meta.addEnchant(Enchantment.KNOCKBACK, 1, true);
			meta.addEnchant(Enchantment.DURABILITY, 10, true);
			item.setItemMeta(meta);
			p.getInventory().setItem(1, item);
			
			item = new ItemStack(Material.DIAMOND, 1);
			meta = item.getItemMeta();
			meta.setDisplayName("��bġ���� ȭ��");
			lorelist.clear();
			lorelist.add("- ��75�ʰ� ȭ�� ġ������ 2�谡 �˴ϴ�.");
			lorelist.add("- ��75�ʰ� ȭ�쿡 Ÿ�ݴ��� ���� ���� �ߵ��˴ϴ�.");
			lorelist.add("- ��c��Ÿ�� 10��");
			meta.setLore(lorelist);
			item.setItemMeta(meta);
			p.getInventory().setItem(2, item);
			
			item = new ItemStack(Material.EMERALD, 1);
			meta = item.getItemMeta();
			meta.setDisplayName("��b��");
			lorelist.clear();
			lorelist.add("- ��7�ݰ� 4ĭ �Ʊ� ü�� +8");
			lorelist.add("- ��c��Ÿ�� 16��");
			meta.setLore(lorelist);
			item.setItemMeta(meta);
			p.getInventory().setItem(3, item);
			
			item = new ItemStack(Material.ARROW, 64);
			p.getInventory().setItem(4, item);
			
			p.addPotionEffect(new PotionEffect(PotionEffectType.REGENERATION, 72000, 0));
			
			break;
			
			default: return;
		}
		
		p.updateInventory();
	}
	
	public static void Cleardata()
	{
		Bukkit.getScheduler().cancelTasks(instance);
		timernum = 0;
		timertime = 15;
		check_start = false;
		check_lobbystart = false;
		plist.clear();
		bluelist.clear();
		redlist.clear();
		game_end = false;
		cooldown.clear();
		jlist.clear();
		archer1.clear();
		archer2.clear();
		healer1.clear();
		teamchat.clear();
		Redcore_cnt = 0;
		Bluecore_cnt = 0;
	}
	
	public static void Forcestop()
	{
		Bukkit.broadcastMessage(MS+"CTF ������ �������� �Ǿ����ϴ�.");
		for(Player p : plist)
		{
	          try{
	        	  me.Bokum.EGM.Main.Spawn(p);
	          } catch(Exception e){
	        	  p.teleport(Main.Main_loc);
	          }
		}
		Cleardata();
	}
	
	public void RedWin()
	{
		game_end = true;
		Sendmessage(MS+"�������� ���� 5���� ����� ��ҽ��ϴ�!");
		Sendmessage(MS+"�������� �¸��� ������ ����ƽ��ϴ�!");
		try{
		Bukkit.getScheduler().scheduleSyncDelayedTask(this, new Runnable()
		{
	public void run()
	{
		for(Player p : plist)
		{
			if(!redlist.contains(p))
			{
			      EconomyResponse r = econ.depositPlayer(p.getName(), 600);
			      if (r.transactionSuccess()) {
			            p.sendMessage(ChatColor.GOLD + "�й� �������� 600���� �����̽��ϴ�.");
			      }
			}
			else
			{
			      EconomyResponse r = econ.depositPlayer(p.getName(), 1200);
			      if (r.transactionSuccess()) {
			            p.sendMessage(ChatColor.GOLD + "�¸� �������� 1200���� �����̽��ϴ�.");
			      }
			}
	          try{
	        	  me.Bokum.EGM.Main.Spawn(p);
	          } catch(Exception e){
	        	  p.teleport(Main.Main_loc);
	          }
		}
		Cleardata();
		Bukkit.broadcastMessage(MS+"��e��lCTF�� �������� �¸��� ���� �ƽ��ϴ�.");
	}
		}, 140L);
		}catch(Exception e){
			Forcestop();
		}
	}
	
	public static void BlueWin()
	{
		game_end = true;
		Sendmessage(MS+"������� ���� 5���� ����� ��ҽ��ϴ�.");
		Sendmessage(MS+"������� �¸��� ������ ����ƽ��ϴ�!");
		try{
		Bukkit.getScheduler().scheduleSyncDelayedTask(instance, new Runnable()
				{
			public void run()
			{
				for(Player p : plist)
				{
					if(bluelist.contains(p))
					{
					      EconomyResponse r = econ.depositPlayer(p.getName(), 1200);
					      if (r.transactionSuccess()) {
					            p.sendMessage(ChatColor.GOLD + "�¸� �������� 1200���� �����̽��ϴ�.");
					      }
					}
					else
					{
					      EconomyResponse r = econ.depositPlayer(p.getName(), 600);
					      if (r.transactionSuccess()) {
					            p.sendMessage(ChatColor.GOLD + "�й� �������� 600���� �����̽��ϴ�.");
					      }
					}
			          try{
			        	  me.Bokum.EGM.Main.Spawn(p);
			          } catch(Exception e){
			        	  p.teleport(Main.Main_loc);
			          }
				}
				Cleardata();
				Bukkit.broadcastMessage(MS+"��e��lCTF�� ������� �¸��� ���� �ƽ��ϴ�.");
			}
				}, 140L);
		}catch(Exception e){
	    	Forcestop();
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
	public void onClickInventory(InventoryClickEvent e)
	{
		if(e.getWhoClicked() instanceof Player)
		{
			Player p = (Player) e.getWhoClicked();
			if(!plist.contains(p))
			{
				return;
			}
			if(e.getSlot() == 36 || e.getSlot() == 37 || e.getSlot() == 38 || e.getSlot() == 39)
			{
				e.setCancelled(true);
			}
			if(e.getInventory().getName().equalsIgnoreCase("��������"))
			{
				e.setCancelled(true);
				switch(e.getSlot())
				{
				case 0:
					Setjob(p, "��Ŀ"); break;
				case 2:
					Setjob(p,"����"); break;
				case 4:
					Setjob(p, "������"); break;
				case 6:
					Setjob(p, "��ؽ�");; break;
				case 8:
					Setjob(p, "����"); break;
				default: return;
				}
			}
			 if(e.getInventory().getTitle().equalsIgnoreCase("��c��l�����")){
				 GameHelper(p, e.getSlot());
				 e.setCancelled(true);
			 }
		}
	}
	
	@EventHandler(priority = EventPriority.HIGHEST)
	public void onRespawn(PlayerRespawnEvent e)
	{
		if(plist.contains(e.getPlayer()) && check_start)
		{
			Player p = e.getPlayer();
			e.setRespawnLocation(bluelist.contains(p) ? Bluespawn_loc : Redspawn_loc);
		}
	}
	
	@EventHandler
	public void onQuit(PlayerQuitEvent e)
	{
		if(plist.contains(e.getPlayer()))
		{
			GameQuit(e.getPlayer());
		}
	}
	
	@EventHandler
	public void onRightClickItem(PlayerInteractEvent e)
	{
		if(!plist.contains(e.getPlayer())) return;
		Player p = e.getPlayer();
	 	if(e.getItem() != null && e.getItem().getType() == Material.COMPASS){
	 		p.openInventory(gamehelper); return;
	 	}
		if((e.getAction() == Action.RIGHT_CLICK_AIR || e.getAction() == Action.RIGHT_CLICK_BLOCK)){
			Skill(p, p.getItemInHand().getTypeId()); return;
		}
		if(e.getAction() == Action.RIGHT_CLICK_BLOCK || e.getAction() == Action.LEFT_CLICK_BLOCK){
			if(e.getClickedBlock().getTypeId() == 19) p.openInventory(jobs);
			Location bl = e.getClickedBlock().getLocation();
			Location l = new Location(bl.getWorld(), bl.getBlockX(), bl.getBlockY(), bl.getBlockZ());
			if(l.equals(RedArrive_loc) && redlist.contains(p) && !game_end){
				if(p.getInventory().contains(35)){
					p.getInventory().remove(35);
					Redcore_cnt++;
					Sendmessage(MS+"��c��l������ ��a��l"+p.getName()+"��6��l ���� ����� �����߽��ϴ�! ��e(��c��������f: "+Redcore_cnt+"�� ��b�����:��f "+Bluecore_cnt+"��");
					for(Player t : redlist){
						t.setLevel(Redcore_cnt);
					}
					for(Player t : plist){
						t.playSound(t.getLocation(), Sound.ANVIL_USE, 0.5f, 3.0f);
					}
					if(Redcore_cnt >= 5){
						RedWin();
					}
					removeFlagEffect(p);
				}else{
					p.sendMessage(MS+"����� ����� ������ ��Ŭ���ϼ���!");
				}
			}
			if(l.equals(BlueArrive_loc) && bluelist.contains(p) && !game_end){
				if(p.getInventory().contains(35)){
					p.getInventory().remove(35);
					Sendmessage(MS+"��b��l����� ��a��l"+p.getName()+"��6��l ���� ����� �����߽��ϴ�!");
					Bluecore_cnt++;
					for(Player t : bluelist){
						t.setLevel(Bluecore_cnt);
					}
					for(Player t : plist){
						t.playSound(t.getLocation(), Sound.ANVIL_USE, 0.5f, 3.0f);
					}
					if(Bluecore_cnt >= 5){
						BlueWin();
					}
					removeFlagEffect(p);
				}else{
					p.sendMessage(MS+"����� ����� ������ ��Ŭ���ϼ���!");
				}
			}
		}
	}
	
	@EventHandler
	public void onLobbyDamage(EntityDamageEvent e){
		if(!(e.getEntity() instanceof Player)) return;
		Player p = (Player) e.getEntity();
		if(!plist.contains(p)) return;
		if(bluelist.contains(p) && Bluespawn_loc.distance(p.getLocation()) <= 30){
			p.sendMessage(MS+"�������� 30ĭ���� �����Դϴ�.");
			e.setCancelled(true);
		}
		if(redlist.contains(p) && Redspawn_loc.distance(p.getLocation()) <= 30){
			p.sendMessage(MS+"�������� 30ĭ���� �����Դϴ�.");
			e.setCancelled(true);
		}
		if(!check_start){
			e.setCancelled(true); return;
		}
	}
	
	@EventHandler
	public void onDamage(EntityDamageByEntityEvent e)
	{
		if(e.getEntity() instanceof Player)
		{
			Player p = (Player) e.getEntity();
			if(!(plist.contains(p) && jlist.containsKey(p.getName())))
			{
				return;
			}
			if(!check_start)
			{
				e.setCancelled(true);
				return;
			}
			Player d = null;
			if(e.getDamager() instanceof Arrow)
			{
				Arrow arrow = (Arrow) e.getDamager();
				if(arrow.getShooter() instanceof Player)
				{
					d = (Player) arrow.getShooter();
				}
			}
			else if(e.getDamager() instanceof Player)
			{
				d = (Player) e.getDamager();
			}
			if(d == null || !(plist.contains(d) && jlist.containsKey(d.getName())))
			{
				return;
			}
			if(plist.contains(p) && plist.contains(d))
			{
				if((redlist.contains(p) && redlist.contains(d)) || (bluelist.contains(p) && bluelist.contains(d)))
				{
					if(jlist.get(d.getName()).equalsIgnoreCase("����"))
					{
						if(e.getDamager() instanceof Arrow)
						{
							int hp = p.getHealth();
							hp += healer1.contains(d.getName()) ? e.getDamage()*2 : e.getDamage();
							if(hp > 20)
							{
								hp = 20;
							}
							p.setHealth(hp);
							p.sendMessage(MS+"������ ����� ȸ�� ��ŵ�ϴ�.");
							p.getWorld().playSound(d.getLocation(), Sound.BREATH, 1.0f, 0.6f);
						}
					}
					e.setCancelled(true);
					return;
				}
				if(jlist.get(p.getName()).equalsIgnoreCase("��ؽ�"))
				{
					e.setDamage((int)(e.getDamage()*1.3));
				}
				if(e.getDamager() instanceof Arrow)
				{
					if(archer1.contains(d.getName()))
					{
						archer1.remove(d.getName());
						d.sendMessage(MS+p.getName()+" ���� �Ӹ��� ������ϴ�. (Ȯ�λ�� ��ų)");
						e.setDamage((int) (e.getDamage()*1.5));
					}
					if(healer1.contains(d.getName()))
					{
						healer1.remove(d.getName());
						d.sendMessage(MS+p.getName()+" ���� ���� �ߵ��˴ϴ�!");
						PotionEffect potion = new PotionEffect(PotionEffectType.POISON, 40, 1);
						p.addPotionEffect(potion);
					}
					if(archer2.contains(d.getName()))
					{
						d.sendMessage(MS+p.getName()+" ���� �þ߸� �����մϴ�. (����ȭ�� ��ų)");
						p.addPotionEffect(new PotionEffect(PotionEffectType.BLINDNESS, 160, 1));
					}
				}
				else
				{
					if(assasin1.contains(d.getName()))
					{
						assasin1.remove(d.getName());
						d.sendMessage(MS+p.getName()+" ���� �޼Ҹ� �����߽��ϴ�. (�ϻ� ��ų)");
						e.setDamage((int)(e.getDamage()*1.5));
					}
				}
			}
		}
	}
	
	/*@EventHandler
	public void onArrowHit(ProjectileHitEvent e)
	{
		if(e.getEntity() instanceof Arrow)
		{
			Arrow arrow = (Arrow) e.getEntity();
			if(arrow.getShooter() instanceof Player && arrow.get)
		}
	}*/
	
	@EventHandler
	public void onDropItem(PlayerDropItemEvent e)
	{
		if(plist.contains(e.getPlayer()))
		{
			e.setCancelled(true);
		}	
	}
	
	@EventHandler
	public void onDeath(PlayerDeathEvent e)
	{
		if(plist.contains(e.getEntity()))
		{
			e.getEntity().getInventory().clear();
			e.getDrops().clear();
		}
	}
	
	@EventHandler
	public void onBlockPlace(BlockPlaceEvent e){
		  if(plist.contains(e.getPlayer()))
		e.setCancelled(true);
	}
	
	public void removeFlagEffect(Player p){
		PlayerInventory pinv = p.getInventory();
		if(pinv.getHelmet() != null){
			ItemMeta meta = pinv.getHelmet().getItemMeta();
			meta.removeEnchant(Enchantment.ARROW_INFINITE);
			pinv.getHelmet().setItemMeta(meta);
		}
		if(pinv.getChestplate() != null){
			ItemMeta meta = pinv.getChestplate().getItemMeta();
			meta.removeEnchant(Enchantment.ARROW_INFINITE);
			pinv.getChestplate().setItemMeta(meta);
		}
		if(pinv.getLeggings() != null){
			ItemMeta meta = pinv.getLeggings().getItemMeta();
			meta.removeEnchant(Enchantment.ARROW_INFINITE);
			pinv.getLeggings().setItemMeta(meta);
		}
		if(pinv.getBoots() != null){
			ItemMeta meta = pinv.getBoots().getItemMeta();
			meta.removeEnchant(Enchantment.ARROW_INFINITE);
			pinv.getBoots().setItemMeta(meta);
		}
		p.removePotionEffect(PotionEffectType.SLOW);
	}
	
	public void getFlagEffect(Player p){
		PlayerInventory pinv = p.getInventory();
		if(pinv.getHelmet() != null){
			ItemMeta meta = pinv.getHelmet().getItemMeta();
			meta.addEnchant(Enchantment.ARROW_INFINITE, 1, true);
			pinv.getHelmet().setItemMeta(meta);
		}
		if(pinv.getChestplate() != null){
			ItemMeta meta = pinv.getChestplate().getItemMeta();
			meta.addEnchant(Enchantment.ARROW_INFINITE, 1, true);
			pinv.getChestplate().setItemMeta(meta);
		}
		if(pinv.getLeggings() != null){
			ItemMeta meta = pinv.getLeggings().getItemMeta();
			meta.addEnchant(Enchantment.ARROW_INFINITE, 1, true);
			pinv.getLeggings().setItemMeta(meta);
		}
		if(pinv.getBoots() != null){
			ItemMeta meta = pinv.getBoots().getItemMeta();
			meta.addEnchant(Enchantment.ARROW_INFINITE, 1, true);
			pinv.getBoots().setItemMeta(meta);
		}
		p.addPotionEffect(new PotionEffect(PotionEffectType.SLOW, 72000, 0));
	}
	
	@EventHandler
	public void onBlockbreak(final BlockBreakEvent e)
	{
		if(plist.contains(e.getPlayer()))
		{
			Player p = e.getPlayer();
			Location tmpbl = e.getBlock().getLocation();
			Location bl = new Location(p.getWorld(), tmpbl.getBlockX(), tmpbl.getBlockY(), tmpbl.getBlockZ());
			e.setCancelled(true);
			if(bl.equals(Bluecore_loc)){
				if(bluelist.contains(p)){
					p.sendMessage(MS+"���� ����� �μ���������!");
				}else if(p.getInventory().contains(35)){
					p.sendMessage(MS+"�̹� ����� ������ �ֽ��ϴ�. ������ �����ϼ���!");
				}else{
					e.getBlock().setType(Material.AIR);
					p.sendMessage(Main.MS+"������� ����� ������ϴ�! ������ �ִ� �������� �����ϼ���!");
					Sendmessage(MS+"��c��l������ ��a��l"+p.getName()+"��6��l ���� ��b��l�������6��l�� ����� ȹ���߽��ϴ�!\n"+MS+"����� ���� �÷��̾�� ������ �����ϴ�.");
					p.getInventory().addItem(blueFlag);
					getFlagEffect(p);
					final int cur = Getcursch();
					tasktime[cur] = 20;
					tasknum[cur] = Bukkit.getScheduler().scheduleSyncRepeatingTask(instance, new Runnable(){
						public void run(){
							if(--tasktime[cur] > 0){
								for(Player t : redlist){
									t.setExp(1/tasktime[cur]);
								}
							}else{
								Canceltask(cur);
								Redmessage(MS+"������� ����� ����� �Ǿ����ϴ�.");
								e.getBlock().setTypeIdAndData(35, (byte) 11, true);
							}
						}
					}, 0l, 20l);
					return;
				}
			}
			if(bl.equals(Redcore_loc)){
				if(redlist.contains(p)){
					p.sendMessage(MS+"���� ����� �μ���������!");
				}else if(p.getInventory().contains(35)){
					p.sendMessage(MS+"�̹� ����� ������ �ֽ��ϴ�. ������ �����ϼ���!");
				}else{
					e.getBlock().setType(Material.AIR);
					p.sendMessage(Main.MS+"�������� ����� ������ϴ�! ������ �ִ� �������� �����ϼ���!");
					Sendmessage(MS+"��b��l����� ��a��l"+p.getName()+"��6��l ���� ��c��l��������6��l�� ����� ȹ���߽��ϴ�!\n"+MS+"����� ���� �÷��̾�� ������ �����ϴ�.");
					p.getInventory().addItem(redFlag);
					getFlagEffect(p);
					final int cur = Getcursch();
					tasktime[cur] = 20;
					tasknum[cur] = Bukkit.getScheduler().scheduleSyncRepeatingTask(instance, new Runnable(){
						public void run(){
							if(--tasktime[cur] > 0){
								for(Player t : bluelist){
									t.setExp(1/tasktime[cur]);
								}
							}else{
								Canceltask(cur);
								Redmessage(MS+"�������� ����� ����� �Ǿ����ϴ�.");
								e.getBlock().setTypeIdAndData(35, (byte) 14, true);
							}
						}
					}, 0l, 20l);
					return;
				}
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
