package me.Bokum.CTM;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.block.Block;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.player.PlayerChannelEvent;
import org.bukkit.event.player.PlayerChatEvent;
import org.bukkit.event.player.PlayerCommandPreprocessEvent;
import org.bukkit.event.player.PlayerDropItemEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.inventory.Inventory;
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
	public static Location joinpos;
	public static Location drawerpos;
	public static Main instance;
	public static List<Player> plist = new ArrayList<Player>();
	public static int tasknum[] = new int[10];
	public static int tasktime[] = new int[10];
	public static int timernum = 0;
	public static int timertime = 0;
	public static ItemStack helpitem;
	public static Inventory gamehelper;
	public static Inventory selectcolor;
	public static Inventory scorelist;
	public static int drawerint = 0;
	public static Player drawer = null;
	public static HashMap<String, Integer> score = new HashMap<String, Integer>();
	public static Economy econ = null;
	public static int round = 1;
	public static int timertime2 = 0;
	public static ItemStack brush;
	public static ItemStack eraser;
	public static ItemStack color;
	public static ItemStack paper;
	public static String topicstr;
	public static ItemStack topic;
	public static byte selcolor = 15;
	public static List<Player> answerlist = new ArrayList<Player>(); 
	public static List<Location> blockloc = new ArrayList<Location>(2550);
	public static List<String> topiclist = new ArrayList<String>(300);
	public static boolean check_start = false;
	public static boolean check_lobbystart = false;
	public static int Forcestoptimer = 0;
	
	public void onEnable()
	{
		instance = this;
		getServer().getPluginManager().registerEvents(this, this);
		getLogger().info("ĳġ���ε� �÷������� �ε� �Ǿ����ϴ�.");
		
		for(int i = 0; i < tasknum.length; i++){
			tasknum[i] = -5;
			tasktime[i] = -5;
		}
		
		topiclist.add("�±ر�");
		topiclist.add("�ҹ���");
		topiclist.add("����");
		topiclist.add("����");
		topiclist.add("Į");
		topiclist.add("����");
		topiclist.add("��ǻ��");
		topiclist.add("����ũ");
		topiclist.add("Ű����");
		topiclist.add("�ð�");
		topiclist.add("����");
		topiclist.add("�Ȱ�");
		topiclist.add("���");
		topiclist.add("����������");
		topiclist.add("�����÷�����");
		topiclist.add("����");
		topiclist.add("Ǯ��");
		topiclist.add("����Ŀ");
		topiclist.add("�ڵ���");
		topiclist.add("������");
		topiclist.add("�ΰ�");
		topiclist.add("��");
		topiclist.add("����");
		topiclist.add("����");
		topiclist.add("����");
		topiclist.add("��");
		topiclist.add("�౹");
		topiclist.add("����ũ����Ʈ");
		topiclist.add("īī����");
		topiclist.add("�Ź�");
		topiclist.add("�Ź߲�");
		topiclist.add("����");
		topiclist.add("��");
		topiclist.add("��");
		topiclist.add("�ٶ�");
		topiclist.add("���̾Ƹ��");
		topiclist.add("���޶���");
		topiclist.add("ö");
		topiclist.add("��ź");
		topiclist.add("�ܵ�");
		topiclist.add("��");
		topiclist.add("����");
		topiclist.add("��Ÿ��");
		topiclist.add("����");
		topiclist.add("õ��");
		topiclist.add("��伮");
		topiclist.add("��ݾ�");
		topiclist.add("ȭ��");
		topiclist.add("���ѹα�");
		topiclist.add("���ֵ�");
		topiclist.add("����");
		topiclist.add("�ٴ�");
		topiclist.add("���");
		topiclist.add("����");
		topiclist.add("�尩");
		topiclist.add("å��");
		topiclist.add("����");
		topiclist.add("��ǻ��");
		topiclist.add("��ǳ��");
		topiclist.add("��������");
		topiclist.add("����");
		topiclist.add("ȭ��");
		topiclist.add("����");
		topiclist.add("ȣ����");
		topiclist.add("�䳢");
		topiclist.add("�ź���");
		topiclist.add("����");
		topiclist.add("�Ź�");
		topiclist.add("�Ź���");
		topiclist.add("�Ƿη�");
		topiclist.add("¯��");
		topiclist.add("���󿡸�");
		topiclist.add("����");
		topiclist.add("����");
		topiclist.add("����");
		topiclist.add("���찳");
		topiclist.add("å");
		topiclist.add("�б�");
		topiclist.add("�����");
		topiclist.add("������");
		topiclist.add("����");
		topiclist.add("��");
		topiclist.add("����");
		topiclist.add("�ڵ���");
		topiclist.add("����");
		topiclist.add("ġŲ");
		topiclist.add("��");
		topiclist.add("��ǳ��");
		topiclist.add("����Ʈ");
		topiclist.add("����");
		topiclist.add("��");
		topiclist.add("������");
		topiclist.add("����");
		topiclist.add("����");
		topiclist.add("â��");
		topiclist.add("����");
		topiclist.add("����");
		topiclist.add("����");
		topiclist.add("�㸮��");
		topiclist.add("����");
		topiclist.add("����");
		topiclist.add("�Ʒ�");
		topiclist.add("�౸��");
		topiclist.add("�߱���");
		topiclist.add("ī��");
		topiclist.add("����");
		topiclist.add("���");
		topiclist.add("��");
		topiclist.add("����");
		topiclist.add("Ȱ");
		topiclist.add("ȭ��");
		topiclist.add("���ô�");
		topiclist.add("���");
		topiclist.add("����");
		topiclist.add("�丶��");
		topiclist.add("������");
		topiclist.add("ĩ��");
		topiclist.add("ġ��");
		topiclist.add("�ѱ�");
		topiclist.add("����");
		topiclist.add("����");
		topiclist.add("��");
		topiclist.add("���ٱ�");
		topiclist.add("��");
		topiclist.add("ȶ��");
		topiclist.add("���彺��");
		topiclist.add("ȭ��");
		topiclist.add("����");
		topiclist.add("��Ű");
		topiclist.add("����");
		topiclist.add("��ȣ��");
		topiclist.add("���");
		topiclist.add("�򶧱�");
		topiclist.add("����");
		topiclist.add("��ħ��");
		topiclist.add("���ּ�");
		topiclist.add("���");
		topiclist.add("�¾�");
		topiclist.add("����");
		topiclist.add("����");
		topiclist.add("������");
		topiclist.add("û�ݼ�");
		topiclist.add("����");
		topiclist.add("��");
		topiclist.add("����");
		topiclist.add("��������");
		topiclist.add("��");
		topiclist.add("����");
		topiclist.add("����");
		topiclist.add("�����");
		topiclist.add("������");
		topiclist.add("��Ű");
		topiclist.add("�Ź�");
		topiclist.add("�Ҹ�");
		topiclist.add("����");
		topiclist.add("������");
		topiclist.add("�����");
		topiclist.add("����");
		topiclist.add("����");
		topiclist.add("�ر�");
		topiclist.add("��");
		topiclist.add("����");
		topiclist.add("����");
		topiclist.add("ö��");
		topiclist.add("�±ر�");
		topiclist.add("���");
		topiclist.add("��ȭ");
		topiclist.add("����");
		topiclist.add("���ε�");
		topiclist.add("ķ��");
		topiclist.add("��Ʈ");
		topiclist.add("����");
		topiclist.add("�Ķ�����");
		topiclist.add("���");
		topiclist.add("����ũ");
		topiclist.add("��ȣ��");
		topiclist.add("����");
		topiclist.add("�ε�");
		topiclist.add("������");
		topiclist.add("����");
		topiclist.add("�ϴ�");
		topiclist.add("��");
		topiclist.add("����");
		topiclist.add("����");
		topiclist.add("����");
		topiclist.add("Ⱦ�ܺ���");
		topiclist.add("����");
		topiclist.add("�߱���");
		topiclist.add("�ݺ�");
		topiclist.add("���൹");
		topiclist.add("����");
		topiclist.add("���ݸ�");
		topiclist.add("����");
		topiclist.add("�︮����");
		topiclist.add("������");
		topiclist.add("�볪��");
		topiclist.add("����");
		topiclist.add("�ڷ�����");
		topiclist.add("���ͳ�");
		topiclist.add("��ư");
		//200
		topiclist.add("�̱�");
		topiclist.add("������");
		topiclist.add("�̸�Ʈ");
		topiclist.add("���ڸ�");
		topiclist.add("�̺�");
		topiclist.add("��");
		topiclist.add("��");
		topiclist.add("���丮");
		topiclist.add("����");
		topiclist.add("�������");
		//210
		topiclist.add("�޷�");
		topiclist.add("��ǳ");
		topiclist.add("������");
		topiclist.add("Ŭ��");
		topiclist.add("ȣ��");
		topiclist.add("�عٶ��");
		topiclist.add("ī�޶�");
		topiclist.add("��ġ");
		topiclist.add("ȣ��");
		topiclist.add("���ڵ�");
		//220
		topiclist.add("��");
		topiclist.add("��ũ");
		topiclist.add("�ﰢ��");
		topiclist.add("�簢��");
		topiclist.add("��");
		topiclist.add("��ȭ��");
		topiclist.add("�ø���");
		topiclist.add("��");
		topiclist.add("����");
		topiclist.add("������");
		//230
		topiclist.add("�׳�");
		topiclist.add("�ü�");
		topiclist.add("�̲���Ʋ");
		topiclist.add("ö��");
		topiclist.add("������");
		topiclist.add("��Ʈ");
		topiclist.add("Ŭ�ι�");
		topiclist.add("����");
		topiclist.add("�ĵ�");
		topiclist.add("��ź");
		//240
		topiclist.add("��");
		topiclist.add("���");
		topiclist.add("������");
		topiclist.add("����");
		topiclist.add("���̷���");
		topiclist.add("��");
		topiclist.add("��");
		topiclist.add("��");
		topiclist.add("��");
		topiclist.add("������");
		//250
		topiclist.add("������");
		topiclist.add("����ž");
		topiclist.add("��");
		topiclist.add("ũ����");
		topiclist.add("��Ÿ��");
		topiclist.add("��");
		topiclist.add("ť��");
		topiclist.add("�ֻ���");
		topiclist.add("��ǥ");
		topiclist.add("����");
		//260
		topiclist.add("�ܹ���");
		topiclist.add("���");
		topiclist.add("���ڷ�");
		topiclist.add("���ε�");
		topiclist.add("��Ÿ");
		topiclist.add("�����");
		topiclist.add("��������");
		topiclist.add("�帱");
		topiclist.add("��");
		topiclist.add("����");
		//270		
		
		scorelist = Bukkit.createInventory(null, 9, "��c��l������");
		
		selectcolor = Bukkit.createInventory(null, 18, "��c��l�� ����");
		
		for(int i = 0; i <= 15; i++){
			selectcolor.setItem(i, new ItemStack(35, 1, (byte) i));
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
		
		brush = new ItemStack(75, 1);
		meta1 = brush.getItemMeta();
		meta1.setDisplayName("��f[ ��a�� ��f]");
		lorelist.clear();
		lorelist.add("��f- ��7ĵ������ ��Ŭ������ �׸��� �׸�����!");
		meta1.setLore(lorelist);
		brush.setItemMeta(meta1);
		
		eraser = new ItemStack(265, 1);
		meta1 = eraser.getItemMeta();
		meta1.setDisplayName("��f[ ��f��ü ����� ��f]");
		lorelist.clear();
		lorelist.add("��f- ��7��Ŭ���� ĵ������ ����ϴ�.");
		meta1.setLore(lorelist);
		eraser.setItemMeta(meta1);
		
		color = new ItemStack(340, 1);
		meta1 = color.getItemMeta();
		meta1.setDisplayName("��f[ ��e�� ���� ��f]");
		lorelist.clear();
		lorelist.add("��f- ��7��Ŭ���� ���� �����մϴ�.");
		meta1.setLore(lorelist);
		color.setItemMeta(meta1);
		
		topic = new ItemStack(321, 1);
		meta1 = topic.getItemMeta();
		meta1.setDisplayName("����");
		lorelist.clear();
		meta1.setLore(lorelist);
		topic.setItemMeta(meta1);
		
		paper = new ItemStack(Material.PAPER, 1);
		meta1 = paper.getItemMeta();
		meta1.setDisplayName("������");
		lorelist.clear();
		meta1.setLore(lorelist);
		paper.setItemMeta(meta1);
		
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
		getLogger().info("ĳġ���ε� �÷������� ��ε� �Ǿ����ϴ�.");
	}
	
	public void Loadconfig()
	{
	  try
	  {
		joinpos = new Location(Bukkit.getWorld(getConfig().getString("Join_world")), getConfig().getInt("Join_x"), getConfig().getInt("Join_y"), getConfig().getInt("Join_z"));
	    Lobby = new Location(Bukkit.getWorld(getConfig().getString("Lobby_world")), getConfig().getInt("Lobby_x"), getConfig().getInt("Lobby_y"), getConfig().getInt("Lobby_z"));
	    drawerpos = new Location(Bukkit.getWorld(getConfig().getString("Draw_world")), getConfig().getInt("Draw_x"), getConfig().getInt("Draw_y"), getConfig().getInt("Draw_z"));
	  }
	  catch (IllegalArgumentException e)
	  {
	    getLogger().info("���� ���� �Ǵ� �κ� �����Ǿ� ���� �ʽ��ϴ�");
	  }
		ConfigurationSection sec = getConfig();
		try
		{
			for(int j = 1; j <= getConfig().getInt("blockamt"); j++){
				  blockloc.add(new Location(getServer().getWorld("world"), getConfig().getInt("block_loc_"+j+"_x"), getConfig().getInt("block_loc_"+j+"_y"), getConfig().getInt("block_loc_"+j+"_z")));
			}
		}
		catch (Exception e)
		{
			 getLogger().info("�� ������ �Ϻ��� ���� �Ǿ����� �ʽ��ϴ�. ���� �߻��� ����� �ֽ��ϴ�.");
		}
	}
	
	public boolean onCommand(CommandSender talker, Command command, String str, String args[])
	{
		if(talker instanceof Player)
		{
			Player p = (Player) talker;
			if(str.equalsIgnoreCase("ctm") && p.isOp())
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
					if(block_loc.getBlock().getType() == Material.WOOL){
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
			loc.getBlock().setTypeIdAndData(35, (byte) 0, true);
		}
	}
	
	public void Helpmessages(Player p)
	{
		p.sendMessage(MS+"/ctm join");
		p.sendMessage(MS+"/ctm quit");
		p.sendMessage(MS+"/ctm set");
		p.sendMessage(MS+"/ctm reload");
		p.sendMessage(MS+"/ctm inv");
	}
	
	public static void Forcestop()
	{
		Bukkit.broadcastMessage(MS+"ĳġ���ε尡 ���� ���� �Ǿ����ϴ�.");
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
		Bukkit.getScheduler().cancelTasks(Main.instance);
		Forcestoptimer = 0;
		plist.clear();
		check_start = false;
		check_lobbystart = false;
		drawerint = 0;
		drawer = null;
		score.clear();
		round = 1;
		topicstr = null;
		selcolor = 15;
		answerlist.clear();
		check_start = false;
		check_lobbystart = false;
		timertime2 = 0;
	}
	
	public void Setloc(Player p, String[] args)
	{
		if(args.length <= 1)
		{
			p.sendMessage(MS+"/ctm set lobby");
			p.sendMessage(MS+"/ctm set join");
			p.sendMessage(MS+"/ctm set drawing");
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
			else if(args[1].equalsIgnoreCase("drawing"))
			{
			    getConfig().set("Draw_world", p.getLocation().getWorld().getName());
			    getConfig().set("Draw_x", Integer.valueOf(p.getLocation().getBlockX()));
			    getConfig().set("Draw_y", Integer.valueOf(p.getLocation().getBlockY() + 1));
			    getConfig().set("Draw_z", Integer.valueOf(p.getLocation().getBlockZ()));
			    saveConfig();
			    Loadconfig();
				p.sendMessage(MS+"���� ���� �Ϸ�");
			}
			return;
		}
	}
	
	public static void Sendmessage(String str)
	{
		for(Player p : plist)
		{
			p.sendMessage(str);
		}
	}
	
	public static void SendAnswerchat(String str)
	{
		for(Player p : answerlist)
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
		if(plist.size() >= 6)
		{
			p.sendMessage(MS+"�̹� �ִ��ο�(6)�Դϴ�.");
			return;
		}
		if(check_start)
		{
			p.sendMessage(MS+"�̹� ������ �������Դϴ�. ���� ���� : "+round+" / 10");
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
			me.Bokum.EGM.Main.gamelist.put(p.getName(), "ĳġ���ε�");
			Sendmessage(MS+p.getName()+" ���� ĳġ���ε忡 �����ϼ̽��ϴ�. �ο� (��e "+plist.size()+"��7 / ��c4 ��f)");
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
		Sendmessage(MS+p.getName()+" ��f���� �����ϼ̽��ϴ�.");
		if(drawer == p){
			Sendmessage(MS+"�����ڰ� �����߽��ϴ�.");
			Bukkit.getScheduler().cancelTask(timernum);
			Timer();
		}
		if(plist.size() == 1)
		{
			try{
				Win(plist.get(0), 0);
			}catch(Exception e){
				Forcestop();
			}
		}
	}
	
	public static void Win(final Player p, int top_score)
	{
		p.playSound(p.getLocation(), Sound.ANVIL_LAND, 2.5f, 1.5f);
		Sendmessage(MS+"��6"+p.getName()+" ��7���� ��6"+top_score+"����7���� �¸��ϼ̽��ϴ�!");
		try{
		Bukkit.getScheduler().scheduleSyncDelayedTask(instance, new Runnable()
		{
			public void run()
			{
				EconomyResponse r = econ.depositPlayer(p.getName(), 500);
				if (r.transactionSuccess()) {
					p.sendMessage(ChatColor.GOLD + "�¸� �������� 500���� �����̽��ϴ�.");
				}
				for(Player t : plist){
					EconomyResponse r1 = econ.depositPlayer(t.getName(), 200);
					if (r1.transactionSuccess()) {
						t.sendMessage(ChatColor.GOLD + "�÷��� �������� 200���� �����̽��ϴ�.");
					}
					try{
						me.Bokum.EGM.Main.Spawn(t);
						} catch(Exception e){
						t.teleport(Main.Lobby);
						}
				}
				Cleardata();
				Bukkit.broadcastMessage(MS+"��6��lĳġ���ε��f�� ��9"+p.getName()+"��f���� �¸��� ���� �ƽ��ϴ�.");
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
			  p.sendMessage("��7���� �̸� ��f: ��cĳġ���ε�");
			  p.sendMessage("��f���� ������ �׸��� ����� �Ѹ� ���մϴ�.");
			  p.sendMessage("��f�׸��� ����� ��2������� ��f�������� �Ǹ�");
			  p.sendMessage("��f�׸��� ����� �־��� ��6������ �´¡�f �׸���");
			  p.sendMessage("��b90�� ��f���� �׸��� �˴ϴ�. ");
			  p.closeInventory();
			  return;
			  
		  case 13:
			  p.sendMessage("��f������ ������� �׸��� �׸��� ������!");
			  p.sendMessage("��f�̻��� �׸� �� �׸� ����� ó���� ���� �� �ֽ��ϴ�.");
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
		
		  public static void SetScorelist(){
			  for(int i = 0; i < 9; i++){
				 scorelist.setItem(i, null);
			  }
			  for(int i = 0; i < plist.size(); i++){
				  ItemStack item = new ItemStack(397, 1, (byte) 3);
				  ItemMeta meta = item.getItemMeta();
				  meta.setDisplayName(plist.get(i).getName());
				  List<String> lorelist = new ArrayList<String>();
				  lorelist.add("��c���� : ��6"+score.get(plist.get(i).getName()));
				  meta.setLore(lorelist);
				  item.setItemMeta(meta);
				  scorelist.setItem(i, item);
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
			Bukkit.broadcastMessage(MS+"��e��lĳġ���ε��f�� �� ���۵˴ϴ�.");
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
							p.getInventory().addItem(paper);
							p.getInventory().addItem(helpitem);
							p.playSound(p.getLocation(), Sound.ANVIL_LAND, 2.0f, 1.0f);
							p.sendMessage(MS+"������ ���� �ƽ��ϴ�.");
							score.put(p.getName(), 0);
						}
						SetScorelist();
						Timer();
					}
				}
			}, 200L, 200L);
		}
		
		public static void Timer(){
			timertime2 = 0;
			timernum = Bukkit.getScheduler().scheduleSyncRepeatingTask(instance, new Runnable(){
				public void run(){
					if(--timertime2 < 0){
						timertime2 = 100;
						if(drawer != null){
							for(Player p : plist){
								p.playSound(p.getLocation(), Sound.ANVIL_LAND, 2.0f, 1.0f);
							}
							drawer.getInventory().clear();
							drawer.teleport(joinpos);
							drawer.getInventory().addItem(paper);
							drawer.getInventory().addItem(helpitem);
							drawer.getInventory().addItem();
							Sendmessage(MS+"�ð��� �� �Ǿ����ϴ�! \n"+MS+"���� : ��6"+topicstr);
							answerlist.clear();
							RestoreBlock();
							if(++round >= 11){
								Player top = null;
								int top_score = 0;
								for(Player t : plist){
									int sc = score.get(t.getName());
									if(sc > top_score){
										top_score = sc;
										top = t;
									}
								}
								if(top == null){
									Forcestop();
								} else {
									try{
									Win(top, top_score);
									}catch(Exception e){
										Forcestop();
									}
								}
							}
						}
						try{
							if(drawerint >= plist.size()) drawerint = 0;
							drawer = plist.get(drawerint);
							if(++drawerint >= plist.size()){
								drawerint = 0;
							}
						}catch(Exception e){
							Forcestop();
						}
						Sendmessage(MS+drawer.getName()+" �Բ��� �׸��� �����Դϴ�. ��c���� ���� ( "+round+" / 10 )");
						drawer.teleport(drawerpos);
						topicstr = topiclist.get(Getrandom(0, topiclist.size()-1));
						ItemMeta meta = topic.getItemMeta();
						meta.setDisplayName("��c���� ��f: ��e"+topicstr);
						topic.setItemMeta(meta);
						Inventory di = drawer.getInventory();
						di.setItem(0, brush);
						di.setItem(2, eraser);
						di.setItem(1, color);
						di.setItem(3, topic);
						answerlist.add(drawer);
						drawer.sendMessage(MS+"��c���� ��f: ��e"+topicstr);
					}else{
						if(!check_start) Bukkit.getScheduler().cancelTask(timernum);
						for(Player p : plist){
							p.setExp(timertime2 <= 0 ? 0 : timertime2/100f);
						}
					}
				}
			}, 140l, 20l);
		}
		
		@EventHandler
		public void onRightClick(PlayerInteractEvent e)
		{
			if(e.getAction() == Action.RIGHT_CLICK_BLOCK || e.getAction() == Action.RIGHT_CLICK_AIR)
			{
				final Player p = e.getPlayer();
				if(!plist.contains(p) || e.getItem() == null) return;
				if(e.getItem().getType() == Material.REDSTONE_TORCH_OFF){
					Block bl = null;
					try{
						bl = p.getTargetBlock(null, 150);
					}catch(Exception exception){
						return;
					}
					if(bl == null) return;
					if(bl.getType() == Material.WOOL) bl.setTypeIdAndData(35, selcolor, true);
				}
				if(e.getItem().getType() == Material.COMPASS)
						  p.openInventory(gamehelper);
				if(e.getItem().getTypeId() == 265){
					p.sendMessage(MS+"���찳�� ����߽��ϴ�.");
					RestoreBlock();
				}
				if(e.getItem().getType() == Material.BOOK) p.openInventory(selectcolor);
				if(e.getItem().getType() == Material.PAPER) p.openInventory(scorelist);
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
				 } else if(e.getInventory().getTitle().equalsIgnoreCase("��c��l�� ����") && e.getSlot() <= 15){
					 selcolor = (byte) (e.getSlot());
					 p.closeInventory();
					 e.setCancelled(true);
				 } else if(e.getInventory().getTitle().equalsIgnoreCase("��c��l������")){
					 e.setCancelled(true);
				 }
			}
		}
		
		@EventHandler
		public void onPlayerDamage(EntityDamageEvent e){
			if(!(e.getEntity() instanceof Player)) return;
			if(plist.contains((Player) e.getEntity())) e.setCancelled(true);
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
			if(plist.contains(p) && (e.getMessage().equalsIgnoreCase("/����") || e.getMessage().equalsIgnoreCase("/spawn")
					|| e.getMessage().equalsIgnoreCase("/����") || e.getMessage().equalsIgnoreCase("/����")))
			{
				GameQuit(p);
			}
		}
		
		@EventHandler
		public void onBlockbreak(BlockBreakEvent e){
			  if(plist.contains(e.getPlayer())){
						e.setCancelled(true);
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
			public void onChat(PlayerChatEvent e){
				if(!plist.contains(e.getPlayer())) return;
				Player p = e.getPlayer();
				if(answerlist.contains(p)){
					SendAnswerchat("��f[ ��6��l������ ä�� ��f] "+p.getName()+" : ��a"+e.getMessage());
					e.setCancelled(true);
					return;
				}
				if(topicstr != null && e.getMessage().equalsIgnoreCase(topicstr)){
					e.setCancelled(true);
					int getscore = 0;
					if(answerlist.size() <= 1){
						getscore = 3;
						if(drawer != null){
							drawer.sendMessage(MS+"�����ڰ� ���� ������ �޾ҽ��ϴ�.");
							score.put(drawer.getName(), score.get(drawer.getName()) + 2);	
						}
					} else if(answerlist.size() <= 2){
						getscore = 2;
					} else{
						getscore = 1;
					}
					for(Player t : plist){
						t.playSound(t.getLocation(), Sound.ANVIL_LAND, 2.0f, 1.0f);
					}
					answerlist.add(p);
					score.put(p.getName(), score.get(p.getName()) + getscore);
					Sendmessage(MS+p.getName()+" ���� ������ ���߼̽��ϴ�!+"+getscore+"��");
					
					SetScorelist();
					if(answerlist.size() == plist.size()){
						Bukkit.getScheduler().cancelTask(timernum);
						Sendmessage(MS+"��� �÷��̾ ������ ���߾����ϴ�!");
						Timer();
					}
				}
			}
}
