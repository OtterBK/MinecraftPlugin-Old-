package me.Bokum.FindTheMurder;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Effect;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.block.Block;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Arrow;
import org.bukkit.entity.EntityType;
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
	public ItemStack t = new ItemStack(Material.)
	
  public static String MS = ChatColor.RESET + "[" + ChatColor.GREEN + "EG" + ChatColor.RESET + "] " + ChatColor.YELLOW;
  public static List<Player> list = new ArrayList<Player>();
  public static HashMap<String, Player> plist = new HashMap<String, Player>();
  public static HashMap<String, String> jlist = new HashMap<String, String>();
  public static HashMap<String, String> glist = new HashMap<String, String>();
  public static HashMap<String, Integer> vlist = new HashMap<String, Integer>();
  public static List<Location> chestlist = new ArrayList<Location>();
  public static List<Player> vplist = new ArrayList<Player>();
  public static Main instance;
  public static Location Lobby;
  public static Location[] Startpos = new Location[15];
  public static Location joinpos;
  public static Inventory gamehelper;
  public static Inventory votemenu;
  public static int schtmp2 = 0;
  public static ItemStack[] chestitem = new ItemStack[23];
  public static ItemStack rose_sword;
  public static ItemStack helpitem;
  public static Economy econ = null;
  public static int Forcestoptimer = 0;

  public void LoadConfig()
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
    for (int i = 1; i <= Startpos.length; i++)
    {
      try
      {
        Startpos[(i - 1)] = new Location(Bukkit.getWorld(getConfig().getString("Start_world" + i)), getConfig().getInt("Start_x" + i), getConfig().getInt("Start_y" + i), getConfig().getInt("Start_z" + i));
      }
      catch (IllegalArgumentException e)
      {
        getLogger().info(i + "��° ���������� ���� �Ǿ����� �ʽ��ϴ�. ���� �߻��� ����� �ֽ��ϴ�.");
      }
    }
	gamehelper = Bukkit.createInventory(null, 27, "��c��l�����");
	
	if (!setupEconomy() ) {
        getLogger().info("[���� �߻� ��� ] Vault�÷������� �νĵ��� �ʾҽ��ϴ�!");
    }
	
	ItemStack item = new ItemStack(34, 1);
	ItemMeta meta = item.getItemMeta();
	meta.setDisplayName("��6���");
	item.setItemMeta(meta);
	for(int i = 0; i <= 9; i++){
		gamehelper.setItem(i, item);
	}
	for(int i = 17; i < 27; i++){
		gamehelper.setItem(i, item);
	}
	
	item = new ItemStack(Material.BOOK, 1);
	meta = item.getItemMeta();
	meta.setDisplayName("��e�÷��� ���");
	item.setItemMeta(meta);
	gamehelper.setItem(11, item);
	
	item = new ItemStack(Material.BOOK, 1);
	meta = item.getItemMeta();
	meta.setDisplayName("��e���� ��Ģ");
	item.setItemMeta(meta);
	gamehelper.setItem(13, item);
	
	item = new ItemStack(Material.BOOK, 1);
	meta = item.getItemMeta();
	meta.setDisplayName("��e��ǥ ���");
	List<String> lorelist = new ArrayList<String>();
	lorelist.add("��f- ��c������ ���۵� �� ����� �� �ֽ��ϴ�.");
	item.setItemMeta(meta);
	gamehelper.setItem(15, item);
	
	votemenu = Bukkit.createInventory(null, 18, "��c��l��ǥ");
	
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
	chestitem[20] = new ItemStack(298, 1);
	chestitem[21] = new ItemStack(299, 1);
	chestitem[22] = Makeitem(264, "������ ��", 1, 1);
	
	rose_sword = Makeitem(276, "���Į", 7, 8);
	
	helpitem = new ItemStack(345, 1);
	meta = helpitem.getItemMeta();
	meta.setDisplayName("��f[ ��6���� ����� ��f]");
	lorelist.clear();
	lorelist.add("��f- ��7��Ŭ���� ���� ����� ���ϴ�.");
	meta.setLore(lorelist);
	helpitem.setItemMeta(meta);
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

  public static void Cancelalltasks()
  {
    Bukkit.getScheduler().cancelTasks(instance);
  }

  public static void Startgame()
  {
    Bukkit.broadcastMessage(MS + ChatColor.DARK_RED + "���θ��� ã�ƶ� " + ChatColor.GRAY + "������ �� ���۵˴ϴ�.");
    GameSystem.schtime = 5;
    schtmp2 = Bukkit.getScheduler().scheduleSyncRepeatingTask(instance, new Runnable()
    {
      public void run()
      {
        if (GameSystem.schtime > 0)
        {
          Main.SendMessage(Main.MS + ChatColor.DARK_RED + "���θ��� ã�ƶ� " + ChatColor.GRAY + "������ " + ChatColor.AQUA + GameSystem.schtime * 10 + ChatColor.GRAY + " �� �� �����մϴ�.");
          GameSystem.schtime -= 1;
        }
        else
        {
          Bukkit.getScheduler().cancelTask(Main.schtmp2);
          Main.StartTP();
        }
      }
    }
    , 200L, 200L);
  }

  public static void StartTP()
  {
    Bukkit.broadcastMessage(MS + ChatColor.DARK_RED + "���θ��� ã�ƶ� " + ChatColor.GRAY + "�� ���� �Ǿ����ϴ�!");
    GameSystem.checkstart = true;
    for (int i = 0; i < list.size(); i++)
    {
      ((Player)list.get(i)).teleport(Startpos[GameSystem.Getrandom(Startpos.length)]);
    }
    GameSystem.schtime = 3;
    schtmp2 = Bukkit.getScheduler().scheduleSyncRepeatingTask(instance, new Runnable()
    {
      public void run()
      {
        if (GameSystem.schtime > 0)
        {
          Main.SendMessage(Main.MS + "������ " + ChatColor.GOLD + GameSystem.schtime * 10 + ChatColor.GRAY + "�� �� �������ϴ�.");
          GameSystem.schtime -= 1;
        }
        else
        {
          Bukkit.getScheduler().cancelTask(Main.schtmp2);
          GameSystem.Setjob();
          GameSystem.GiveBasicitem();
          Main.DayCycle();
          Setvoteinven();
        }
      }
    }
    , 0L, 200L);
  }


  public static void DayCycle()
  {
    if (GameSystem.checkstart)
    {
      if (GameSystem.isday)
      {
    	  GameSystem.Setvotelist();
        SetNight();
      }
      else
      {
        SetDay();
      }
    }
  }

  public static void SetDay()
  {
    GameSystem.isday = true;
    GameSystem.dayinit();
    GameSystem.daycnt += 1;
    for(Player p : Main.list){
    	p.playSound(p.getLocation(), Sound.CHICKEN_IDLE, 1.5f, 1.0f);
    	p.addPotionEffect(new PotionEffect(PotionEffectType.FIRE_RESISTANCE, 1140, 0));
    }
    SendMessage(MS + ChatColor.AQUA + GameSystem.daycnt + ChatColor.YELLOW + "��° ���� ���� �Ǿ����ϴ�.");
    SendMessage(MS + "������ ������ ���� �÷��̾�� ��ǥ�ϼ���. \n��c���� ����� -> ��ǥ���");
    for(int i = 0; i < list.size(); i++){
    	if(GameSystem.Getjob(list.get(i).getName()).equalsIgnoreCase("�����")){
    		list.get(i).sendMessage(MS+"����� �ù����� ������� �ʱ�� ����߽��ϴ�. (��4�ù� ���� �Ұ���.��f)");
    	}
    }
    Bukkit.getScheduler().scheduleSyncDelayedTask(instance, new Runnable()
    {
      public void run()
      {
    	  Main.SendMessage(Main.MS+"�� ���� �˴ϴ�. \n��ǥ�� ���� ���� �÷��̾�� ���� ��ǥ���ּ���!\n��c���� ����� -> ��ǥ���");
    	    for(Player p : Main.list){
    	    	p.playSound(p.getLocation(), Sound.ENDERDRAGON_GROWL, 1.5f, 0.5f);
    	    }
      }
    }
    , 1000L);
    Bukkit.getScheduler().scheduleSyncDelayedTask(instance, new Runnable()
    {
      public void run()
      {
        Main.DayCycle();
      }
    }
    , 1200L);
  }

  public static void SetNight()
  {
    GameSystem.isday = false;
    GameSystem.nightinit();
    for(Player p : Main.list){
    	p.playSound(p.getLocation(), Sound.GHAST_SCREAM, 1.5f, 1.4f);
    }
    SendMessage(MS + ChatColor.AQUA + GameSystem.daycnt + ChatColor.YELLOW + "��° ���� ���� �Ǿ����ϴ�.");
    PotionEffect pt = new PotionEffect(PotionEffectType.BLINDNESS, 940, 0);
    for (int i = 0; i < list.size(); i++)
    {
      ((Player)list.get(i)).addPotionEffect(pt);
    }
    Bukkit.getScheduler().scheduleSyncDelayedTask(instance, new Runnable()
    {
      public void run()
      {
    	  Main.SendMessage(Main.MS+"�� ���� �˴ϴ�.");
    	    for(Player p : Main.list){
    	    	p.playSound(p.getLocation(), Sound.COW_IDLE, 1.5f, 1.4f);
    	    }
      }
    }
    , 800L);
    Bukkit.getScheduler().scheduleSyncDelayedTask(instance, new Runnable()
    {
      public void run()
      {
        Main.DayCycle();
      }
    }
    , 1000L);
  }

  public static void Murderwin()
  {
    SendMessage(MS + ChatColor.RED + "��� �ù����� ����߽��ϴ�!!!");
    SendMessage(MS + ChatColor.RED + "�������� �¸��߽��ϴ�!");
    SendMessage(MS + "�÷��� �������� 600���� �����̽��ϴ�.");
    try{
    Bukkit.getScheduler().scheduleSyncDelayedTask(instance, new Runnable()
    {
      public void run()
      {
        for (Player player : list)
        {
        	econ.depositPlayer(player.getName(), 600);
          try{
        	  me.Bokum.EGM.Main.Spawn(player);
          } catch(Exception e){
        	  //player.teleport(Main.Lobby);
          }
        }
        GameSystem.End();
        Bukkit.broadcastMessage(Main.MS + ChatColor.GREEN + ChatColor.BOLD + "���θ����� �¸��� ���θ��� ã�ƶ� ���� �Ǿ����ϴ�.");
      }
    }
    , 100L);
    }catch(Exception e){
    	GameSystem.ForceEnd();
    }
  }

  public static void Civilwin()
  {
    SendMessage(MS + ChatColor.RED + "��� ���θ����� ����߽��ϴ�!!!");
    SendMessage(MS + ChatColor.RED + "�ù����� �¸��߽��ϴ�!");
    SendMessage(MS + "�÷��� �������� 400���� �����̽��ϴ�.");
    try{
    Bukkit.getScheduler().scheduleSyncDelayedTask(instance, new Runnable()
    {
      public void run()
      {
        for (Player player : list)
        {
            econ.depositPlayer(player.getName(), 400);
          try{
        	  me.Bokum.EGM.Main.Spawn(player);
          } catch(Exception e){
        	  //player.teleport(Main.Lobby);
          }
        }
        GameSystem.End();
        Bukkit.broadcastMessage(Main.MS + ChatColor.GREEN + ChatColor.BOLD + "�ù����� �¸��� ���θ��� ã�ƶ� ���� �Ǿ����ϴ�.");
      }
    }
    , 100L);
  }catch(Exception e){
  	GameSystem.ForceEnd();
  }
  }

  public void SaveLobby(Player player)
  {
    getConfig().set("Lobby_world", player.getLocation().getWorld().getName());
    getConfig().set("Lobby_x", Integer.valueOf(player.getLocation().getBlockX()));
    getConfig().set("Lobby_y", Integer.valueOf(player.getLocation().getBlockY() + 1));
    getConfig().set("Lobby_z", Integer.valueOf(player.getLocation().getBlockZ()));
    saveConfig();
    LoadConfig();
    player.sendMessage(MS + "���ηκ� ���� �Ǿ����ϴ�.");
  }

  public void SaveStartpos(Player player, int posn)
  {
    getConfig().set("Start_world" + posn, player.getLocation().getWorld().getName());
    getConfig().set("Start_x" + posn, Integer.valueOf(player.getLocation().getBlockX()));
    getConfig().set("Start_y" + posn, Integer.valueOf(player.getLocation().getBlockY() + 1));
    getConfig().set("Start_z" + posn, Integer.valueOf(player.getLocation().getBlockZ()));
    saveConfig();
    LoadConfig();
    player.sendMessage(MS + posn + "��° ���������� ���� �Ǿ����ϴ�.");
  }

  public void SaveJoinpos(Player player)
  {
    getConfig().set("Join_world", player.getLocation().getWorld().getName());
    getConfig().set("Join_x", Integer.valueOf(player.getLocation().getBlockX()));
    getConfig().set("Join_y", Integer.valueOf(player.getLocation().getBlockY() + 1));
    getConfig().set("Join_z", Integer.valueOf(player.getLocation().getBlockZ()));
    saveConfig();
    LoadConfig();
    player.sendMessage(MS + "�κ� ���� �Ǿ����ϴ�.");
  }

  public void onEnable()
  {
    getServer().getPluginManager().registerEvents(this, this);
    getLogger().info("���θ��� ã�ƶ� �÷����� �ε� �Ϸ�! - ���� : Bokum");
    instance = this;
    LoadConfig();
  }

  public void onDisable()
  {
    getLogger().info("���θ��� ã�ƶ� �÷����� ��ε� �Ϸ�");
  }

  public boolean onCommand(CommandSender talker, Command command, String string, String[] args)
  {
    if ((talker instanceof Player))
    {
      Player player = (Player)talker;
      if (string.equalsIgnoreCase("ftm"))
      {
        if (args.length == 0)
        {
          HelpMessage(player);
          return true;
        }
        if (args[0].equalsIgnoreCase("set"))
        {
          if (!player.hasPermission("ftm.set"))
          {
            player.sendMessage(MS + "������ �����ϴ�.");
            return false;
          }
          if (args.length <= 1)
          {
            HelpMessage(player);
            return true;
          }
          if (args[1].equalsIgnoreCase("start"))
          {
            if (args.length <= 2)
            {
              player.sendMessage(MS + "/ftm set start <1~15>");
              return true;
            }
            SaveStartpos(player, Integer.valueOf(args[2]).intValue());
            return true;
          }
          if (args[1].equalsIgnoreCase("lobby"))
          {
            SaveLobby(player);
            return true;
          }
          if (args[1].equalsIgnoreCase("join"))
          {
            SaveJoinpos(player);
            return true;
          }

          HelpMessage(player);
        }

        if (args[0].equalsIgnoreCase("join"))
        {
          if (!player.hasPermission("ftm.join"))
          {
            player.sendMessage(MS + "������ �����ϴ�.");
            return false;
          }
          GameSystem.Joingame(player);
          return true;
        }
        if (args[0].equalsIgnoreCase("quit"))
        {
          if (!player.hasPermission("ftm.quit"))
          {
            player.sendMessage(MS + "������ �����ϴ�.");
            return false;
          }
          GameSystem.Quitplayer(player);
          return true;
        }
        if (args[0].equalsIgnoreCase("use"))
        {
          if (!plist.containsKey(player.getName()))
          {
            player.sendMessage(MS + "���ӿ� ���������� �ʽ��ϴ�.");
            return true;
          }
          if (!jlist.containsKey(player.getName()))
          {
            player.sendMessage(MS + "������ �����Ǿ� ���� �ʽ��ϴ�. - ���׷� ����");
            return true;
          }
          String target = null;
          if (args.length > 1)
          {
            target = args[1];
          }
          GameSystem.Use(player, target);
          return true;
        }
        if (args[0].equalsIgnoreCase("help"))
        {
          Jobhelp(player);
          return true;
        }
        if (args[0].equalsIgnoreCase("list"))
        {
          Glisting(player);
          return true;
        }
        if (args[0].equalsIgnoreCase("jlist"))
        {
          if (!player.hasPermission("ftm.jlist"))
          {
            player.sendMessage(MS + "������ �����ϴ�.");
            return false;
          }
          Jlisting(player);
          return true;
        }
        if (args[0].equalsIgnoreCase("reload"))
        {
          if (!player.hasPermission("ftm.reload"))
          {
            player.sendMessage(MS + "������ �����ϴ�.");
            return false;
          }
          LoadConfig();
          return true;
        }
        if (args[0].equalsIgnoreCase("test"))
        {
          if (!player.hasPermission("ftm.test"))
          {
            player.sendMessage(MS + "������ �����ϴ�.");
            return false;
          }
          list.add(player);
		  Givechestitem(player);
          return true;
        }
        if (args[0].equalsIgnoreCase("stop"))
        {
          if (!player.hasPermission("ftm.stop"))
          {
            player.sendMessage(MS + "������ �����ϴ�.");
            return false;
          }
          GameSystem.ForceEnd();
          return true;
        }
        if (args[0].equalsIgnoreCase("vote"))
        {
          String str = null;
          if (args.length >= 2)
          {
            str = args[1];
          }
          Voteplayer(player, str);
          return true;
        }
      }
    }
    return true;
  }

  public void Voteplayer(Player player, String str)
  {
    if (!GameSystem.checkstart)
    {
      player.sendMessage(MS + "������ ���۵��� �ʾҽ��ϴ�.");
    }
    else if (!list.contains(player))
    {
      player.sendMessage(MS + "����� ���ӿ� ���������� �ʽ��ϴ�.");
    }
    else if (!GameSystem.isday)
    {
      player.sendMessage(MS + "��ǥ�� ������ �����մϴ�.");
    }
    else if (str == null)
    {
      player.sendMessage(MS + "�̸��� �Է����ּ���.");
    }
    else if (!plist.containsKey(str))
    {
      player.sendMessage(MS + str + " ���� ���ӿ� �������� �ƴմϴ�.");
    }
    else if (vplist.contains(player))
    {
      player.sendMessage(MS + str + " �̹� ��ǥ �ϼ̽��ϴ�.");
    }
    else
    {
      vplist.add(player);
      if (!vlist.containsKey(str))
      {
        vlist.put(str, Integer.valueOf(1));
      }
      else
      {
        vlist.put(str, Integer.valueOf(((Integer)vlist.get(str)).intValue() + 1));
      }
      if (GameSystem.Getjob(player.getName()).equalsIgnoreCase("����ù�"))
      {
        vlist.put(str, Integer.valueOf(((Integer)vlist.get(str)).intValue() + 1));
        player.sendMessage(MS + "�ɷ��� ȿ���� 2ǥ�� ȿ���� �����մϴ�.");
      }
      player.sendMessage(MS + str + " ���� ��ǥ�߽��ϴ�.");
      SendMessage(MS + "�������� " + ChatColor.AQUA + str + ChatColor.YELLOW + " �Կ��� ��ǥ�߽��ϴ�.");
      for(Player p : Main.list){
      	p.playSound(p.getLocation(), Sound.NOTE_PIANO, 1.5f, 1.2f);
      }
    }
  }

  public void Jobhelp(Player player)
  {
    if (!jlist.containsKey(player.getName()))
    {
      player.sendMessage(MS + "������ �����ϴ�.");
    }
	else
	{
		switch(GameSystem.Getjob(player.getName()))
		{
			case "���θ�": Help_Murder(player);return;
			case "�ǻ�": Help_Doctor(player);return;
			case "����": Help_Police(player);return;
			case "����": Help_Soldier(player);return;
			case "������": Help_Spy(player);return;
			case "���𰡵�": Help_Guardian(player);return;
			case "������": Help_Magician(player);return;
			case "����ù�": Help_Nice_Civilian(player);return;
			case "�ù�": Help_Civilian(player);return;
			case "Ž��": Help_Detective(player);return;
			case "����": Help_Reporter(player);return;
			case "�����": Help_Assasin(player);return;
			case "Ż����": Help_Prisoner(player);return;
			default: player.sendMessage("���� �����Դϴ�. - ���� �ǽ�"); return;
		}
    }
  }

  public void Help_Murder(Player player)
  {
    player.sendMessage(ChatColor.GOLD + "����� �ɷ� : " + ChatColor.AQUA + GameSystem.Getjob(player.getName()));
    player.sendMessage(ChatColor.GRAY + "����� ������ ���⸦ ������ �ֽ��ϴ�. �� ���⸦ �̿��Ͽ� ��� �ù����� �׿����մϴ�.");
    player.sendMessage(ChatColor.GRAY + "�����̰� �ɷ��� ����Ͽ� ��Ű� ������ �����̴� ���ᰡ �˴ϴ�.");
    player.sendMessage(ChatColor.GRAY + "���� ����ڰ� �ù��� ����ҽ� ����� ���ᰡ �˴ϴ�.");
  }

  public void Help_Doctor(Player player)
  {
    player.sendMessage(ChatColor.GOLD + "����� �ɷ� : " + ChatColor.AQUA + GameSystem.Getjob(player.getName()));
    player.sendMessage(ChatColor.GRAY + "����� �㸶�� 1���� ������ �� �ֽ��ϴ�.");
    player.sendMessage(ChatColor.GRAY + "�� ������ �÷��̾�� �� ��� ������ ���� ����� �ѹ��� ��Ȱ�ϸ� ");
    player.sendMessage(ChatColor.GRAY + "���� ���� ã�ƿ´ٸ� �ٽ� �÷��̾ �����ؾ� �մϴ�.");
    player.sendMessage(ChatColor.GRAY + "�ɷ� ����� /ftm use <�г���>");
  }

  public void Help_Police(Player player)
  {
    player.sendMessage(ChatColor.GOLD + "����� �ɷ� : " + ChatColor.AQUA + GameSystem.Getjob(player.getName()));
    player.sendMessage(ChatColor.GRAY + "����� ������ 1���� ������ �� �ֽ��ϴ�.");
    player.sendMessage(ChatColor.GRAY + "������ �÷��̾ ���θ��� ��� ");
    player.sendMessage(ChatColor.GRAY + "�� �÷��̾ ���θ���� �޼����� ��ſ��� �������ϴ�.");
    player.sendMessage(ChatColor.GRAY + "�ɷ� ����� /ftm use <�г���>");
  }

  public void Help_Soldier(Player player)
  {
    player.sendMessage(ChatColor.GOLD + "����� �ɷ� : " + ChatColor.AQUA + GameSystem.Getjob(player.getName()));
    player.sendMessage(ChatColor.GRAY + "����� �ܷõ� ��ü�� �������ϴ�.");
    player.sendMessage(ChatColor.GRAY + "����� �ѹ��� ��Ȱ�ϰ� �˴ϴ�. ");
    player.sendMessage(ChatColor.GRAY + "��Ȱ�Ŀ��� ������ �ù����� ����˴ϴ�.");
  }

  public void Help_Spy(Player player)
  {
    player.sendMessage(ChatColor.GOLD + "����� �ɷ� : " + ChatColor.AQUA + GameSystem.Getjob(player.getName()));
    player.sendMessage(ChatColor.GRAY + "�㸶�� 1���� �÷��̾�� ������ �����մϴ�.");
    player.sendMessage(ChatColor.GRAY + "���� ������ �÷��̾ ���θ���� ����� ���θ��� ���ᰡ �Ǹ�");
    player.sendMessage(ChatColor.GRAY + "�ù����� ���̰� �ٴ� �� �ֽ��ϴ�.");
    player.sendMessage(ChatColor.GRAY + "���� ������ �÷��̾ ���θ��� �ƴ϶�� ����� �� �÷��̾��� ������ �� �� �ֽ��ϴ�.");
    player.sendMessage(ChatColor.GRAY + "�ɷ� ����� /ftm use <�г���>");
  }

  public void Help_Guardian(Player player)
  {
    player.sendMessage(ChatColor.GOLD + "����� �ɷ� : " + ChatColor.AQUA + GameSystem.Getjob(player.getName()));
    player.sendMessage(ChatColor.GRAY + "�㸶�� �θ��� �÷��̾ �����Ͻ� �� �ֽ��ϴ�.");
    player.sendMessage(ChatColor.GRAY + "�� ������ �÷��̾�� ������ ���� ��ǥ�� ���Ͽ� ������ �������� �ʽ��ϴ�.");
    player.sendMessage(ChatColor.GRAY + "�ɷ� ����� /ftm use <�г���>");
  }

  public void Help_Magician(Player player)
  {
    player.sendMessage(ChatColor.GOLD + "����� �ɷ� : " + ChatColor.AQUA + GameSystem.Getjob(player.getName()));
    player.sendMessage(ChatColor.GRAY + "���� �� �� �ѹ��� �ɷ��� ����� �� ������");
    player.sendMessage(ChatColor.GRAY + "����� �÷��̾��� ������ ���Ѿ� �����ϴ�.");
    player.sendMessage(ChatColor.GRAY + "��� ����� ������ ���θ���� �� �÷��̾�� ����ϰ� �˴ϴ�.");
    player.sendMessage(ChatColor.GRAY + "�ɷ� ����� /ftm use <�г���>");
  }

  public void Help_Nice_Civilian(Player player)
  {
    player.sendMessage(ChatColor.GOLD + "����� �ɷ� : " + ChatColor.AQUA + GameSystem.Getjob(player.getName()));
    player.sendMessage(ChatColor.GRAY + "��ǥ�� ����� ��ǥ�� 2ǥ�� ��޵Ǹ� ���ۺ���");
    player.sendMessage(ChatColor.GRAY + "�ڽ��� ������ ��������Ʈ�� �������ϴ�.");
    player.sendMessage(ChatColor.GRAY + "����� �������Ե� �ǽɹ��� ���� �� �Դϴ�.");
  }

  public void Help_Civilian(Player player)
  {
    player.sendMessage(ChatColor.GOLD + "����� �ɷ� : " + ChatColor.AQUA + GameSystem.Getjob(player.getName()));
    player.sendMessage(ChatColor.GRAY + "����� Ư���� �ɷ��� ������ �ʾҽ��ϴ�.");
  }

  public void Help_Detective(Player player)
  {
    player.sendMessage(ChatColor.GOLD + "����� �ɷ� : " + ChatColor.AQUA + GameSystem.Getjob(player.getName()));
    player.sendMessage(ChatColor.GRAY + "������ �Ѹ��� �÷��̾ ������ �� �ֽ��ϴ�.");
    player.sendMessage(ChatColor.GRAY + "���� �Ǿ��� �� /ftm use �� �Է��Ͻø�");
    player.sendMessage(ChatColor.GRAY + "�� �÷��̾ ���� ����ִ� ����, ��ǥ, ü�� ������ ���÷�");
    player.sendMessage(ChatColor.GRAY + "Ȯ���Ͻ� �� �ֽ��ϴ�.");
    player.sendMessage(ChatColor.GRAY + "�ɷ� ����� /ftm use <�г���>");
  }

  public void Help_Reporter(Player player)
  {
    player.sendMessage(ChatColor.GOLD + "����� �ɷ� : " + ChatColor.AQUA + GameSystem.Getjob(player.getName()));
    player.sendMessage(ChatColor.GRAY + "���� �� �ѹ��� �ɷ��� ����� �� ������");
    player.sendMessage(ChatColor.GRAY + "�ɷ��� ����� �÷��̾��� ������ ��ο���");
    player.sendMessage(ChatColor.GRAY + "�˸��ϴ�. ���� ��������Ʈ�� �� �÷��̾��� ������");
    player.sendMessage(ChatColor.GRAY + "�����˴ϴ�.");
    player.sendMessage(ChatColor.GRAY + "�ɷ� ����� /ftm use <�г���>");
  }

  public void Help_Assasin(Player player)
  {
    player.sendMessage(ChatColor.GOLD + "����� �ɷ� : " + ChatColor.AQUA + GameSystem.Getjob(player.getName()));
    player.sendMessage(ChatColor.GRAY + "����� �ڽ��� ���ϴ� ���� ���� �� �ֽ��ϴ�.");
    player.sendMessage(ChatColor.GRAY + "ù���㿡 �ù����� ����ҽ� ����� ���θ����� �˴ϴ�.");
    player.sendMessage(ChatColor.GRAY + "�ݴ�� ù��°�� �ù����� ������� ������ �ù����� ���Ե˴ϴ�.");
    player.sendMessage(ChatColor.GRAY + "��� ������ ù��°�ȿ� �ؾ��մϴ�.");
    player.sendMessage(ChatColor.GRAY + "����� Ÿ�� : " + ChatColor.RED + GameSystem.astarget);
    player.sendMessage(ChatColor.GRAY + "�� �÷��̾ �������Ե� ���ش��ϸ� �˴ϴ�.");
  }
  
  public void Help_Prisoner(Player player)
  {
    player.sendMessage(ChatColor.GOLD + "����� �ɷ� : " + ChatColor.AQUA + GameSystem.Getjob(player.getName()));
    player.sendMessage(ChatColor.GRAY + "����� �����Ҹ� Ż���� Ż�����Դϴ�.");
    player.sendMessage(ChatColor.GRAY + "����� ���߿� ���θ�ó�� ������� ���� �� �ֽ��ϴ�.");
    player.sendMessage(ChatColor.GRAY + "��, �����ϼ���. ���� ����� ���̸� ü���˴ϴ�!");
  }

  public void HelpMessage(Player player)
  {
    player.sendMessage(MS + "/ftm help - �ɷ��� ������ ���ϴ�.");
    player.sendMessage(MS + "/ftm use <�г���> - �ش� ��󿡰� �ɷ��� ����մϴ�.");
    player.sendMessage(MS + "/ftm list - ���� ����Ʈ�� ���ϴ�.");
    player.sendMessage(MS + "/ftm join - ���ӿ� �����մϴ�.");
    player.sendMessage(MS + "/ftm quit - ���ӿ��� �����մϴ�..");
    player.sendMessage(MS + "/ftm set lobby - ���ִ� ������ ���ηκ�� �����մϴ�.");
    player.sendMessage(MS + "/ftm set join- ���ִ� ������ �κ�� �����մϴ�.");
    player.sendMessage(MS + "/ftm set start 1~15 - ���ִ� ������ ������������ �����մϴ�.");
    player.sendMessage(MS + "/ftm reload - config�� ���ε� �մϴ�.");
    player.sendMessage(MS + "/ftm stop - ������ ���� ���� �մϴ�.");
  }

  public static void SendMessage(String str)
  {
    for (int i = 0; i < list.size(); i++)
    {
      ((Player)list.get(i)).sendMessage(str);
    }
  }

  public void Glisting(Player player)
  {
    player.sendMessage(MS + "���� ����Ʈ (�ο� " + glist.size() + "��): ");
    Set keys = glist.keySet();
    Iterator it = keys.iterator();
    while (it.hasNext())
    {
      String name = (String)it.next();
      player.sendMessage(ChatColor.RED + name + ChatColor.RESET + " : " + ChatColor.GOLD + (String)glist.get(name));
    }
  }

  public void Jlisting(Player player)
  {
    player.sendMessage(MS + "���� ����Ʈ :");
    Set keys = jlist.keySet();
    Iterator it = keys.iterator();
    while (it.hasNext())
    {
      String name = (String)it.next();
      player.sendMessage(ChatColor.RED + name + ChatColor.RESET + " : " + ChatColor.GOLD + (String)jlist.get(name));
    }
  }

  public static int getItemDamage(Player p){
	  ItemStack item = p.getItemInHand();
	  if(item == null || !item.hasItemMeta() || !item.getItemMeta().hasLore()) return 1;
	  List<String> lorelist = item.getItemMeta().getLore();
	  if(lorelist.size() <= 0) return 1;
	  String damagestr = lorelist.get(0);
	  if(!damagestr.contains("���ݷ�")) return 1;
	  //��7���ݷ�: 7~8
	  if(!damagestr.contains("~"))return Integer.valueOf(damagestr.substring(7, 8));
	  int min = Integer.valueOf(damagestr.substring(7, 8));
	  int max = Integer.valueOf(damagestr.substring(9, 10));
	  return (int)(Math.random()*(max-min+1)+min);
  }
  
  public static void Givechestitem(Player p){
	  int num = GameSystem.Getrandom(26);
	  p.getWorld().playSound(p.getLocation(), Sound.CHEST_OPEN, 1.0f, 1.0f);
	  if(num == 25){
		  p.sendMessage("��7���� ������ ���� ������ �ʴ´�.");
		  return;
	  }
	  if(num == 24){
		  p.sendMessage("��7�����ۿ� ���� �� ����...");
		  return;
	  }
	  if(num == 23){
		  p.sendMessage("��7���ٸ��� �������� �ʴ´�.");
		  return;
	  }
	  if(num == 8 || num == 19 || num == 10){
		  for(int i = 0; i <= GameSystem.Getrandom(5); i++)
			  p.getInventory().addItem(chestitem[num]);
		  p.updateInventory();
		  return;
	  }
	  p.getInventory().addItem(chestitem[num]);
	  p.updateInventory();
  }
  
  public static void Setvoteinven(){
	  for(int i = 0; i < 18; i++){
		  votemenu.setItem(i, null);
	  }
	  for(int i = 0; i < list.size(); i++){
		  ItemStack item = new ItemStack(397, 1, (byte) 3);
		  ItemMeta meta = item.getItemMeta();
		  meta.setDisplayName(list.get(i).getName());
		  List<String> lorelist = new ArrayList<String>();
		  lorelist.add("��e���� ��f: ��b"+glist.get(list.get(i).getName()));
		  lorelist.add("��cŬ���� �� �÷��̾�� ��ǥ�մϴ�.");
		  meta.setLore(lorelist);
		  item.setItemMeta(meta);
		  votemenu.setItem(i, item);
	  }
  }
  
  public static void GameHelper(Player p, int slot){
	  switch(slot){
	  case 11:
		  p.sendMessage("��7���� �̸� ��f: ��c���θ��� ã�ƶ�");
		  p.sendMessage("��f������ ���۵ǰ� 30�� �� ��� �÷��̾��� ������ �������ϴ�.");
		  p.sendMessage("��f���� �� ��4���θ�, ������, Ż������f�� ���θ����̸�");
		  p.sendMessage("��f�� ���� �������� �ù����Դϴ�.");
		  p.sendMessage("��4��, ����ڴ� ���θ����� �� ���� �ֽ��ϴ�.");
		  p.sendMessage("��f�ù����� ��� ���θ����� �׿����ϸ�");
		  p.sendMessage("��f���θ����� ��� �ù����� �׿����մϴ�.");
		  p.sendMessage("��c�� �� ������ �ִ� ���ڸ� ��Ŭ���� �������� ������ �� �ֽ��ϴ�.");
		  p.closeInventory();
		  return;
		  
	  case 13:
		  p.sendMessage("��f���� ���� ����Ͻ� �Ŀ��� ���ӿ� �����Ͻ� �� �����ϴ�.");
		  p.sendMessage("��f���� ���� ����� ������ �Ұ����մϴ�.");
		  p.sendMessage("��f���θ� ���� ��� ���Ƿ� ���� ������ ���Ͻ� ��� ������ �� �ֽ��ϴ�.");
		  p.sendMessage("��c�� ���θ� ���� ���θ� ������ �������� �� �� �ֽ��ϴ�.");
		  p.closeInventory();
		  return;
		  
	  case 15:
		  if(!glist.containsKey(p.getName())) {
			  p.sendMessage("������ ���۵� �� ����Ͻ� �� �ֽ��ϴ�.");
			  p.closeInventory();
			  return;
		  }
		  p.openInventory(votemenu);
		  return;
		  
	  default: return;
	  }  
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
  
  @EventHandler
  public static void onHitPlayer(EntityDamageByEntityEvent e)
  {
    if (((e.getEntity() instanceof Player)) && (GameSystem.checkstart))
    {
      Player player = (Player)e.getEntity();
      Player damager = null;
      if(!list.contains(player)) return;
		if(e.getDamager() instanceof Snowball){
			Snowball snowball = (Snowball) e.getDamager();
			if(snowball.getShooter() instanceof Player){
				damager = (Player) snowball.getShooter();
				e.setDamage(7);
			}
		}
		if(e.getDamager() instanceof Arrow){
			Arrow arrow = (Arrow) e.getDamager();
			if(arrow.getShooter() instanceof Player){
				damager = (Player) arrow.getShooter();
			}
		}
      if(e.getDamager() instanceof Player) damager = (Player) e.getDamager();
      if(damager == null) return;
      if (!list.contains(damager) || (!jlist.containsKey(damager.getName())))
      {
        if (list.contains(player))
        {
          e.setCancelled(true);
        }
        return;
      }
      if(!(getItemDamage(damager) == 0)){
    	  e.setDamage(getItemDamage(damager));
      }
      if(GameSystem.Getjob(damager.getName()).equalsIgnoreCase("Ż����") && GameSystem.isday)
      {
    	  damager.sendMessage(MS+"������ ���Դϴ�! �� ����� ���̸� ü���� �� �Դϴ�!");
      }
      if (GameSystem.Getjob(damager.getName()).equalsIgnoreCase("����"))
      {
        if (damager.getItemInHand().hasItemMeta())
        {
          if (damager.getItemInHand().getItemMeta().hasDisplayName())
          {
            if (damager.getItemInHand().getItemMeta().getDisplayName().equalsIgnoreCase(ChatColor.AQUA + "������"))
            {
              if (damager.getItemInHand().getAmount() != 1)
              {
                damager.getItemInHand().setAmount(damager.getItemInHand().getAmount() - 1);
              }
              else
              {
                damager.setItemInHand(null);
              }
              player.addPotionEffect(new PotionEffect(PotionEffectType.SLOW, 60, 10));
              player.sendMessage(ChatColor.RED + "�������� �¾ҽ��ϴ�!!!");
              damager.sendMessage(ChatColor.RED + "�������� ��� �Ͽ����ϴ�.");
            }
          }
        }
      }
      if ((GameSystem.mlist.contains(player)) && (GameSystem.mlist.contains(damager)))
      {
        player.sendMessage(MS + "���� ���θ����� " + damager.getName() + "���� ����� �����߽��ϴ�! ");
        damager.sendMessage(MS + player.getName() + " ���� ���� ���θ��� �Դϴ�! �������ּ���!");
        e.setDamage(1);
      }
      int damage = e.getDamage();
      if ((player.getHealth() - damage <= 0) && (!player.isDead()))
      {
        if ((!GameSystem.mlist.contains(player)) && (!GameSystem.Getjob(player.getName()).equalsIgnoreCase("������")))
        {
          if (!GameSystem.mlist.contains(damager))
          {
        	if(GameSystem.Getjob(damager.getName()).equalsIgnoreCase("�����")){
        		damager.sendMessage(MS+"����� �ù����� ����ϱ�� ����߽��ϴ�. (��4���� �ù����� ���Ѿ��� ���� �� �ֽ��ϴ�.��f)");
        		GameSystem.mlist.add(damager);
        	} else if (!GameSystem.astarget.equalsIgnoreCase(player.getName())) {
        		if(!(GameSystem.Getjob(player.getName()).equalsIgnoreCase("�����") && GameSystem.daycnt == 1)){
        			e.setCancelled(true);
                    GameSystem.Removeplayerfl(damager);
                    damager.sendMessage(MS + "����� ������ �÷��̾ �׿����� ó�� �ǿ����ϴ�.");
                    damager.setHealth(0);
                    return;	
        		}
            }
          }
          if (GameSystem.CheckReverse(player))
          {
            e.setCancelled(true);
            GameSystem.Reverse(player);
            return;
          }

        }
        else if (GameSystem.CheckReverse(player))
        {
          e.setCancelled(true);
          GameSystem.Reverse(player);
          return;
        }
      }
    }
  }

  @EventHandler
  public static void onPlayerdeath(PlayerDeathEvent e)
  {
    if (((e.getEntity() instanceof Player)) && (GameSystem.checkstart))
    {
      Player player = e.getEntity();
      if(!list.contains(player)) return;
      if(GameSystem.isday)
      {
    	  if(player.getKiller() instanceof Player)
    	  {
    		  Player killer = (Player) player.getKiller();
    		  if(jlist.containsKey(killer.getName())  && GameSystem.Getjob(player.getKiller().getName()).equalsIgnoreCase("Ż����"))
    		  {
    	    	  killer.sendMessage(MS+"���� ������ ������ ü�� �Ǿ����ϴ�.");
    	    	  killer.setHealth(1);
    	    	  killer.damage(30);
    		  }
    	  }
      }
    	e.getDrops().clear();
    	e.setDroppedExp(0);
        if (GameSystem.mlist.contains(player))
        {
          GameSystem.Murderdead(player);
        }
        else
        {
          GameSystem.Civildead(player);
        }
        Setvoteinven();
        for(Player p : Main.list){
        	p.playSound(p.getLocation(), Sound.GHAST_DEATH, 1.5f, 1.6f);
        }
    }
  }

  @EventHandler
  public static void onQuitPlayer(PlayerQuitEvent e) {
    if (list.contains(e.getPlayer()))
    {
      GameSystem.Quitplayer(e.getPlayer());
    }
  }
  
  @EventHandler
  public void onRightClick(PlayerInteractEvent e){
	  if(!list.contains(e.getPlayer()) || (e.getAction() != Action.RIGHT_CLICK_AIR && e.getAction() != Action.RIGHT_CLICK_BLOCK)) return;
	  Player p = e.getPlayer();
	  if(e.getClickedBlock() != null && e.getClickedBlock().getTypeId() == 54 && GameSystem.checkstart){
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
			  p.sendMessage("��7�̹� ������ �о ������ �ִ�...");
		  }
	  }
	  if(e.getItem() == null || e.getItem().getType() == Material.AIR) return;
	  if(GameSystem.getHandItemName(p).equalsIgnoreCase("��f[ ��6���� ����� ��f]")){
		  p.openInventory(gamehelper);
	  } else if(e.getItem().getType() == Material.IRON_HOE){
		  if(!Takeitem(p, 362, 1)){
			  p.sendMessage("��c9mmźȯ�� �����մϴ�."); return;
		  }
		  Snowball snowball = p.launchProjectile(Snowball.class); //here we launch the snowball
		  Block target = p.getTargetBlock(null, 50);
		  Vector velocity = (target.getLocation().toVector().subtract(snowball.getLocation().toVector()).normalize()).multiply(6);
		  snowball.setVelocity(velocity);
		  snowball.setShooter(p);
		  p.getWorld().playSound(p.getLocation(), Sound.ANVIL_LAND, 4.0f, 2.0f);
		  p.getWorld().playEffect(e.getPlayer().getLocation(), Effect.SMOKE, 20); //We will play a smoke effect at the shooter's location
	  } else if(e.getItem().getType() == Material.IRON_SWORD){
		  if(Takeitem(p, 267, 3)){
			  p.sendMessage(Main.MS+"���Į�� �����!");
			  p.getInventory().addItem(rose_sword);
		  }
	  } else if(e.getItem().getTypeId() == 377){
		  p.addPotionEffect(new PotionEffect(PotionEffectType.INVISIBILITY, 300, 1));
		  Removeitem(p, 377, 1);
		  p.sendMessage(MS+"���� ���並 ��� �߽��ϴ�. 15�ʰ� ����� ����ϴ�.");
	  } else if(e.getItem().getTypeId() == 264){
		  p.addPotionEffect(new PotionEffect(PotionEffectType.DAMAGE_RESISTANCE, 100, 1));
		  Removeitem(p, 264, 1);
		  p.sendMessage(MS+"������ ���� ��� �߽��ϴ�. 5�ʰ� ����2�� �ްԵ˴ϴ�.");
	  }
  }
 
  @EventHandler
  public void onClickInventory(InventoryClickEvent e){
	  if(!(e.getWhoClicked() instanceof Player)) return; 
	  Player p = (Player) e.getWhoClicked();
	  if(!list.contains(p)) return;
	  if(e.getInventory().getTitle().equalsIgnoreCase("��c��l�����")){
		  GameHelper(p, e.getSlot());
		  e.setCancelled(true);
	  }
	  if(e.getInventory().getTitle().equalsIgnoreCase("��c��l��ǥ")){
		  e.setCancelled(true);
		  if(e.getCurrentItem() == null || e.getCurrentItem().getType() == Material.AIR)return;
		  p.closeInventory();
		  Voteplayer(p, e.getCurrentItem().getItemMeta().getDisplayName());
	  }
  }

	@EventHandler
	public void onPlayercommand(PlayerCommandPreprocessEvent e)
	{
		Player p = e.getPlayer();
		if(list.contains(p) && (e.getMessage().equalsIgnoreCase("/����") || e.getMessage().equalsIgnoreCase("/spawn")
				|| e.getMessage().equalsIgnoreCase("/����") || e.getMessage().equalsIgnoreCase("/����")))
		{
			GameSystem.Quitplayer(p);
		}
	}
  
  @EventHandler
  public void onBlockbreak(BlockBreakEvent e){
	  if(list.contains(e.getPlayer()))
	  e.setCancelled(true);
  }
  
  @EventHandler
  public void onBlockPlace(BlockPlaceEvent e){
	  if(list.contains(e.getPlayer()))
	  e.setCancelled(true);
  }
  
  @EventHandler
  public void onFall(EntityDamageEvent e) {
    if (e.getEntityType() == EntityType.PLAYER)
    {
      Player player = (Player)e.getEntity();
      if(!list.contains(player)) return;
      if ((e.getCause() == EntityDamageEvent.DamageCause.FALL) || !GameSystem.checkstart)
      {
        e.setCancelled(true);
      }
    }
  }
}