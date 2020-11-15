package me.Bokum.Parkour;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.GameMode;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.block.Block;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.file.FileConfiguration;
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
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.event.player.PlayerRespawnEvent;
import org.bukkit.event.player.PlayerToggleSneakEvent;
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
	public static Location joinpos_race;
	public static Location joinpos_bed;
	public static Location joinpos_pat;
	public static Main instance;
	public static List<Player> plist = new ArrayList<Player>();
	public static List<Player> tlist = new ArrayList<Player>();
	public static List<Player> blist = new ArrayList<Player>();
	public static List<Player> patlist = new ArrayList<Player>();
	public static List<Player> pat_police = new ArrayList<Player>();
	public static List<Player> pat_theif = new ArrayList<Player>();
	public static Location startpos_race;
	public static Location startpos_pat;
	public static Location training_pos;
	public static Location practice_pos;
	public static Location[] startpos_bed = new Location[20];
	public static Location checkpoint[] = new Location[10];
	public static Location pat_chest_pos[] = new Location[40];
	public static int tasknum[] = new int[15];
	public static int tasktime[] = new int[15];
	public static int endtimernum = 0;
	public static int endtimertime = 0;
	public static ItemStack helpitem;
	public static ItemStack pat_stunstick;
	public static ItemStack pat_stungun;
	public static ItemStack pat_invisible;
	public static ItemStack pumpkin;
	public static ItemStack clock;
	public static ItemStack posinfo;
	public static ItemStack seerank;
	public static Inventory gamehelper;
	public static Inventory gamehelper2;
	public static Inventory passwordinven;
	public static Inventory infoinven;
	public static Inventory shopinven;
	public static String Firstname = null;
	public static String Secondname = null;
	public static String Thirdname = null;
	public static double Firsttime = 0;
	public static double Secondtime = 0;
	public static double Thirdtime = 0;
	public static List<Location> pumpkinclearlist = new ArrayList<Location>();
	public static HashMap<String, String> insertingpassword = new HashMap<String, String>();
	public static HashMap<String, ItemStack[]> deathinven = new HashMap<String, ItemStack[]>();
	public static HashMap<String, Location> pumpkin_loc = new HashMap<String, Location>();
	public static HashMap<String, String> password = new HashMap<String, String>();
	public static HashMap<String, Player> solvingtarget = new HashMap<String, Player>();
	public static HashMap<String, Integer> cplist = new HashMap<String, Integer>();
	public static HashMap<String, Integer> deathcount = new HashMap<String, Integer>();
	public static HashMap<String, Double> time = new HashMap<String, Double>();
	public static List<Location> blockloc = new ArrayList<Location>();
	public static List<String> jumpinglist = new ArrayList<String>();
	public static HashMap<String, Integer> climbinglist = new HashMap<String, Integer>();
	public static Economy econ = null;
	public static ItemStack chestitem[] = new ItemStack[36];
	public static boolean check_start_race = false;
	public static boolean pvp = false;
	public static boolean check_lobbystart_race = false;
	public static boolean check_start_pat = false;
	public static boolean check_lobbystart_pat = false;
	public static boolean check_start_bed = false;
	public static boolean check_lobbystart_bed = false;
	public static boolean check_raceend = false;
	public static List<String> rank_name = new ArrayList<String>();
	public static List<Double> rank_time = new ArrayList<Double>();
	public static List<Location> chestlist = new ArrayList<Location>();
	public static int Forcestoptimer_race = 0;
	public static int Forcestoptimer_bed = 0;
	
	public void onEnable()
	{
		instance = this;
		getServer().getPluginManager().registerEvents(this, this);
		getLogger().info("���� �÷������� �ε� �Ǿ����ϴ�.");
		
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
		
		item = new ItemStack(Material.BOOK_AND_QUILL, 1);
		meta2 = item.getItemMeta();
		meta2.setDisplayName("��e��ŷ����");
		item.setItemMeta(meta2);
		gamehelper.setItem(15, item);
		
		gamehelper2 = Bukkit.createInventory(null, 27, "��c��l�����");
		
		item = new ItemStack(34, 1);
		meta2 = item.getItemMeta();
		meta2.setDisplayName("��6���");
		item.setItemMeta(meta2);
		for(int i = 0; i <= 9; i++){
			gamehelper2.setItem(i, item);
		}
		for(int i = 17; i < 27; i++){
			gamehelper2.setItem(i, item);
		}
		
		item = new ItemStack(Material.BOOK, 1);
		meta2 = item.getItemMeta();
		meta2.setDisplayName("��e�÷��� ���");
		item.setItemMeta(meta2);
		gamehelper2.setItem(11, item);
		
		item = new ItemStack(Material.BOOK, 1);
		meta2 = item.getItemMeta();
		meta2.setDisplayName("��e���� ��Ģ");
		item.setItemMeta(meta2);
		gamehelper2.setItem(13, item);
		
		List<String> lorelist = new ArrayList<String>();
		
		helpitem = new ItemStack(345, 1);
		ItemMeta meta1 = helpitem.getItemMeta();
		meta1.setDisplayName("��f[ ��6���� ����� ��f]");
		lorelist.clear();
		lorelist.add("��f- ��7��Ŭ���� ���� ����� ���ϴ�.");
		meta1.setLore(lorelist);
		helpitem.setItemMeta(meta1);
		
		clock = new ItemStack(347, 1);
		meta1 = clock.getItemMeta();
		meta1.setDisplayName("��f[ ��6���� ��f]");
		lorelist.clear();
		lorelist.add("��f- ��7��Ŭ���� ���� ������ ���ϴ�.");
		meta1.setLore(lorelist);
		clock.setItemMeta(meta1);
		
		seerank = new ItemStack(371, 1);
		meta1 = seerank.getItemMeta();
		meta1.setDisplayName("��f[ ��6��ŷ���� ��f]");
		lorelist.clear();
		lorelist.add("��f- ��7��Ŭ���÷��̽� ��ŷ�� ���ϴ�.");
		meta1.setLore(lorelist);
		seerank.setItemMeta(meta1);
		
		posinfo = new ItemStack(402, 1);
		meta1 = clock.getItemMeta();
		meta1.setDisplayName("��f[ ��6�ڽ��� ȣ�� ��ǥ ��f]");
		lorelist.clear();
		lorelist.add("��f- ��7��Ŭ���� �ڽ��� ȣ����ǥ�� ���ϴ�.");
		meta1.setLore(lorelist);
		posinfo.setItemMeta(meta1);
		
		pumpkin = new ItemStack(91, 1);
		meta1 = pumpkin.getItemMeta();
		meta1.setDisplayName(MS+"��ġ�ϼ���.");
		lorelist.clear();
		lorelist.add("��f- ��7��ġ�� ��Ҹ� �ڽ��� ������ �����մϴ�.");
		lorelist.add("��f- ��7��ġ�Ŀ��� ȣ���� Ŭ���Ͽ� ������ �̿��Ͻ� �� �ֽ��ϴ�.");
		lorelist.add("��f- ��7��ġ�Ŀ��� ��й�ȣ�� �����ϰԵ˴ϴ�.");
		meta1.setLore(lorelist);
		pumpkin.setItemMeta(meta1);
		
		passwordinven = Bukkit.createInventory(null, 45, "��c��l��й�ȣ");
		
		item = new ItemStack(34, 1);
		meta2 = item.getItemMeta();
		meta2.setDisplayName("��6���");
		item.setItemMeta(meta2);
		for(int i = 0; i <= 9; i++){
			passwordinven.setItem(i, item);
		}
		for(int i = 36; i <= 44; i++){
			passwordinven.setItem(i, item);
		}
		passwordinven.setItem(18, item);
		passwordinven.setItem(27, item);
		passwordinven.setItem(17, item);
		passwordinven.setItem(26, item);
		passwordinven.setItem(35, item);
		passwordinven.setItem(13, item);
		passwordinven.setItem(22, item);
		passwordinven.setItem(31, item);
		
		int amt = 1;
		
		for(int i = 10; i < 36; i += 9){
			for(int j = 0; j < 3; j++){
				item = new ItemStack(131, 1);
				meta2 = item.getItemMeta();
				meta2.setDisplayName("��b��l"+amt);
				item.setItemMeta(meta2);
				amt++;
				
				passwordinven.setItem(i+j, item);
			}
		}
		
		item = new ItemStack(131, 1);
		meta2 = item.getItemMeta();
		meta2.setDisplayName("��b��l0");
		item.setItemMeta(meta2);
		
		passwordinven.setItem(38, item);

		item = new ItemStack(Material.WOOL, 1, (byte) 14);
		meta2 = item.getItemMeta();
		meta2.setDisplayName("��b��l�ٽ� �Է�");
		item.setItemMeta(meta2);
		
		passwordinven.setItem(15, item);
		
		item = new ItemStack(Material.WOOL, 1, (byte) 5);
		meta2 = item.getItemMeta();
		meta2.setDisplayName("��b��lȮ��");
		item.setItemMeta(meta2);
		
		passwordinven.setItem(33, item);
		
		infoinven = Bukkit.createInventory(null, 18, "��c��l����");
		
		shopinven = Bukkit.createInventory(null, 36, "��c��l����");
		
		chestitem[0] = Makeitem(69, "��������", 3, 3, 0, 14);
		chestitem[1] = Makeitem(359, "����", 3, 4, 0, 18);
		chestitem[2] = Makeitem(369, "�ݼӹ�Ʈ", 4, 4, 0, 22);
		chestitem[3] = Makeitem(75, "������ ��Ʈ", 4, 5, 0, 26);
		chestitem[4] = Makeitem(268, "���� ��", 5, 5, 0, 30);
		chestitem[5] = Makeitem(272, "��Į", 5, 6, 0, 35);
		chestitem[6] = Makeitem(287, "ä��", 6, 6, 0, 40);
		chestitem[7] = Makeitem(283, "�Ѱ�", 6, 7, 0, 45);
		chestitem[8] = Makeitem(292, "����", 7, 7, 0, 50);
		chestitem[9] = Makeitem(377, "��Ŭ", 7, 8, 0, 56);
		chestitem[10] = Makeitem(267, "īŸ��", 8, 8, 0, 62);
		chestitem[11] = Makeitem(276, "��ü��", 8, 9, 0, 68);
		chestitem[12] = Makeitem(279, "�ظ�", 9, 9, 0, 74);
		chestitem[13] = Makeitem(261, "Ȱ", 0, 0, 0, 75);
		chestitem[14] = Makeitem(262, "ȭ��", 0, 0, 0, 1);
		chestitem[15] = Makeitem(261, "Ȱ", 0, 0, 0, 75);
		chestitem[16] = Makeitem(298, "���� ����", 0, 0, 0, 10);
		chestitem[17] = Makeitem(299, "���� Ʃ��", 0, 0, 0, 14);
		chestitem[18] = Makeitem(373, "ȸ�� ����", 0, 0, 8197, 3);
		chestitem[19] = Makeitem(373, "ȸ�� ���� 2�ܰ�", 0, 0, 8229, 5);
		chestitem[20] = Makeitem(373, "ȸ�� ���� ��ô��", 0, 0, 16389, 5);
		chestitem[21] = Makeitem(373, "ȸ�� ���� ��ô�� 2�ܰ�", 0, 0, 16421, 8);
		chestitem[22] = Makeitem(373, "�� ����", 0, 0, 16388, 13);
		chestitem[23] = Makeitem(373, "������ ����", 0, 0, 16396, 11);
		chestitem[24] = Makeitem(370, "�Ƶ巹����", 0, 0, 0, 4);
		item = new ItemStack(266, 1);
		ItemMeta meta = item.getItemMeta();
		meta.setDisplayName("��a����");
		lorelist.clear();
		lorelist.add("��f- ��7���� 10���� �Ҹ��� 1~19���� �������� ����ϴ�.");
		meta.setLore(lorelist);
		item.setItemMeta(meta);
		chestitem[25] = item;
		item = new ItemStack(339, 1);
		meta = item.getItemMeta();
		meta.setDisplayName("��a���� ����");
		lorelist.clear();
		lorelist.add("��f- ��7���޶��� 100���� �Һ��Ͽ� ��� �÷��̾��� ");
		lorelist.add("��f- ��7������ �߰��� ����ϴ�. ( ��� �÷��̾�� ���� )");
		meta.setLore(lorelist);
		item.setItemMeta(meta);
		chestitem[26] = item;
		chestitem[27] = Makeitem(300, "���� ����", 0, 0, 0, 13);
		chestitem[28] = Makeitem(301, "���� ��ȭ", 0, 0, 0, 10);
		chestitem[29] = Makeitem(302, "�罽 ����", 0, 0, 0, 20);
		chestitem[30] = Makeitem(303, "�罽 ����", 0, 0, 0, 27);
		chestitem[31] = Makeitem(304, "�罽 ����", 0, 0, 0, 24);
		chestitem[32] = Makeitem(305, "�罽 ����", 0, 0, 0, 20);
		
		for(int i = 0; i < 33; i++){
			shopinven.setItem(i, chestitem[i]);
		}
		
		for(int i = 0; i < 10; i++){
			rank_name.add(me.Bokum.EGM.Main.getRank("Parkour").getString(("rank_name_"+i)));
		}
		
		for(int i = 0; i < 10; i++){
			rank_time.add(me.Bokum.EGM.Main.getRank("Parkour").getDouble(("rank_time_"+i)));
		}
		
		Timer();
		
		Loadconfig();
		
        if (!setupEconomy() ) {
            getLogger().info("[ ���� �߻� ��� ] Vault�÷������� �νĵ��� �ʾҽ��ϴ�!");
        }
	}
	
	public static ItemStack coin(int amt){
		ItemStack item = new ItemStack(388, amt);
		ItemMeta meta = item.getItemMeta();
		meta.setDisplayName("��f[ ��a���� ��f]");
		item.setItemMeta(meta);
		return item;
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
    
	public void onDisable()
	{
		getLogger().info("���� �÷������� ��ε� �Ǿ����ϴ�.");
	}
	
	  public static ItemStack Makeitem(int id, String name, int min, int max, int shortnum, int price){
		  ItemStack tmpitem = null;
		  if(shortnum == 0){
			 tmpitem = new ItemStack(id, 1);
		  } else {
				 tmpitem = new ItemStack(id, 1, (short) shortnum);
		  }
		  ItemMeta meta = tmpitem.getItemMeta();
		  meta.setDisplayName("��c"+name);
		  List<String> lorelist = new ArrayList<String>();
		  if(min != 0 && max != 0){
			  String damage = "��7���ݷ�: ";
			  if(max == min){
				  damage += String.valueOf(min);
			  } else {
				  damage += min+"~";
				  damage += max;
			  }
			  lorelist.add(damage);
		  } else {
			  lorelist.add("��7������ �⺻ ������");
		  }

		  lorelist.add("��c���� ��f:��6 "+price);
		  meta.setLore(lorelist);
		  tmpitem.setItemMeta(meta);
		  return tmpitem;
	  }

	public void Loadconfig()
	{
	  try
	  {
		  training_pos = new Location(Bukkit.getWorld(getConfig().getString("training_world")), getConfig().getInt("training_x"), getConfig().getInt("training_y"), getConfig().getInt("training_z"));
		  practice_pos = new Location(Bukkit.getWorld(getConfig().getString("practice_world")), getConfig().getInt("practice_x"), getConfig().getInt("practice_y"), getConfig().getInt("practice_z"));
		joinpos_race = new Location(Bukkit.getWorld(getConfig().getString("Join_world_race")), getConfig().getInt("Join_x_race"), getConfig().getInt("Join_y_race"), getConfig().getInt("Join_z_race"));
		joinpos_bed = new Location(Bukkit.getWorld(getConfig().getString("Join_world_bed")), getConfig().getInt("Join_x_bed"), getConfig().getInt("Join_y_bed"), getConfig().getInt("Join_z_bed"));
	    Lobby = new Location(Bukkit.getWorld(getConfig().getString("Lobby_world")), getConfig().getInt("Lobby_x"), getConfig().getInt("Lobby_y"), getConfig().getInt("Lobby_z"));
	    startpos_race = new Location(Bukkit.getWorld(getConfig().getString("start_worldrace")), getConfig().getInt("start_xrace"), getConfig().getInt("start_yrace"), getConfig().getInt("start_zrace"));
	    for(int i = 1; i <= 20; i++)
	    startpos_bed[i-1] = new Location(Bukkit.getWorld(getConfig().getString("start_worldbed"+i)), getConfig().getInt("start_xbed"+i), getConfig().getInt("start_ybed"+i), getConfig().getInt("start_zbed"+i));
	  }
	  catch (IllegalArgumentException e)
	  {
	    getLogger().info("���� ���� �Ǵ� �κ�, ���������� �����Ǿ� ���� �ʽ��ϴ�");
	  }
		ConfigurationSection sec = getConfig();
		try
		{
			for(int j = 1; j <= 10; j++){
				  checkpoint[j-1]= new Location(Bukkit.getWorld(sec.getString("checkpoint_world"+j)), sec.getInt("checkpoint_x"+j), sec.getInt("checkpoint_y"+j), sec.getInt("checkpoint_z"+j));
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
			 getLogger().info("�� ������ �Ϻ��� ���� �Ǿ����� �ʽ��ϴ�. ���� �߻��� ����� �ֽ��ϴ�.");
		}
		try
		{
			for(int j = 1; j <= 40; j++){
				  pat_chest_pos[j-1]= new Location(Bukkit.getWorld(sec.getString("chestpos_world"+j)), sec.getInt("chestpos_x"+j), sec.getInt("chestpos_y"+j), sec.getInt("chestpos_z"+j));
			}
		}
		catch (Exception e)
		{
			 getLogger().info("pat���� ������ �Ϻ��� ���� �Ǿ����� �ʽ��ϴ�. ���� �߻��� ����� �ֽ��ϴ�.");
		}
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
					if(block_loc.getBlock().getType() == Material.IRON_FENCE){
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
			loc.getBlock().setType(Material.IRON_FENCE);
		}
	}
	
	public boolean onCommand(CommandSender talker, Command command, String str, String args[])
	{
		if(talker instanceof Player)
		{
			Player p = (Player) talker;
			if(str.equalsIgnoreCase("pkr"))
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
						GameJoinrace(p);
						return true;
					}
					if(args[0].equalsIgnoreCase("bedjoin"))
					{
						GameJoinbed(p);
						return true;
					}
					if(args[0].equalsIgnoreCase("quit"))
					{
						GameQuitrace(p);
						return true;
					}
					if(args[0].equalsIgnoreCase("stop"))
					{
						if(args.length <= 1){
							p.sendMessage(MS+"/pkr stop bed/race");
							return true;
						}
						if(args[1].equalsIgnoreCase("race")){
							Forcestop_race();
						} else if(args[1].equalsIgnoreCase("bed")){
							Forcestop_bed();
						}
						return true;
					}
					if(args[0].equalsIgnoreCase("block"))
					{
						SaveBlock(p, args[1], args[2], args[3], args[4], args[5], args[6]);
						return true;
					}
					if(args[0].equalsIgnoreCase("test"))
					{
						p.teleport(training_pos);
					}
				}
			}
		}
		return false;
	}
	
	public void Helpmessages(Player p)
	{
		p.sendMessage(MS+"/pkr join");
		p.sendMessage(MS+"/pkr quit");
		p.sendMessage(MS+"/pkr set");
		p.sendMessage(MS+"/pkr reload");
		p.sendMessage(MS+"/pkr inv");
	}
	
	public static void Forcestop_race()
	{
		Bukkit.broadcastMessage(MS+"���� ���̽��� ���� ���� �Ǿ����ϴ�.");
		for(Player p : tlist)
		{
	          try{
	        	  me.Bokum.EGM.Main.Spawn(p);
	          } catch(Exception e){
	        	  p.teleport(Main.Lobby);
	          }
		}
		Cleardata_race();
	}
	
	public static void Forcestop_bed()
	{
		Bukkit.broadcastMessage(MS+"���� ȣ�������� ���� ���� �Ǿ����ϴ�.");
		for(Player p : blist)
		{
	          try{
	        	  me.Bokum.EGM.Main.Spawn(p);
	          } catch(Exception e){
	        	  p.teleport(Main.Lobby);
	          }
		}
		Cleardata_bed();
	}
	
	public static void Cleardata_race()
	{
		Bukkit.getScheduler().cancelTask(endtimernum);
		Forcestoptimer_race = 0;
		for(Player t : tlist){
			plist.remove(t);
		}
		check_raceend = false;
		tlist.clear();
		cplist.clear();
		Firstname = null;
		Firsttime = 0;
		Secondname = null;
		Secondtime = 0;
		Thirdname = null;
		Thirdtime = 0;
		check_start_race = false;
		check_lobbystart_race = false;
	}
	
	public static void Cleardata_bed()
	{
		for(Player t : blist){
			plist.remove(t);
		}
		Forcestoptimer_bed = 0;
		check_start_bed = false;
		check_lobbystart_bed = false;
		blist.clear();
		pvp = false;
		chestlist.clear();
		insertingpassword.clear();
		for(Location l : pumpkinclearlist){
			l.getBlock().setType(Material.AIR);
		}
		pumpkinclearlist.clear();
		pumpkin_loc.clear();
		chestlist.clear();
		password.clear();
		solvingtarget.clear();
		deathcount.clear();
		check_start_bed = false;
		check_lobbystart_bed = false;
	}
	
	public void Setloc(Player p, String[] args)
	{
		if(args.length <= 1)
		{
			p.sendMessage(MS+"/pkr set race/bed lobby");
			p.sendMessage(MS+"/pkr set race/bed join");
			p.sendMessage(MS+"/pkr set race/bed start 1~15");
			p.sendMessage(MS+"/pkr set cp 1~10");
			return;
		}
		if(args.length <= 2)
		{
			p.sendMessage(MS+"/pkr set race/bed lobby");
			p.sendMessage(MS+"/pkr set race/bed join");
			p.sendMessage(MS+"/pkr set race/bed start 1~15");
			if(args[1].equalsIgnoreCase("training"))
			{
				p.sendMessage(MS+"Ʈ���̴׷� �����Ϸ�");
				getConfig().set("training_world", p.getWorld().getName());
				getConfig().set("training_x", p.getLocation().getBlockX());
				getConfig().set("training_y", p.getLocation().getBlockY()+1);
				getConfig().set("training_z", p.getLocation().getBlockZ());
				saveConfig();
				Loadconfig();
			}
			if(args[1].equalsIgnoreCase("practice"))
			{
				p.sendMessage(MS+"����������� �����Ϸ�");
				getConfig().set("practice_world", p.getWorld().getName());
				getConfig().set("practice_x", p.getLocation().getBlockX());
				getConfig().set("practice_y", p.getLocation().getBlockY()+1);
				getConfig().set("practice_z", p.getLocation().getBlockZ());
				saveConfig();
				Loadconfig();
			}
			return;
		}
		if(args.length >= 3)
		{
			if(args[1].equalsIgnoreCase("cp"))
			{
					int i = Integer.valueOf(args[2]);
					if(!(1 <= i && i <= 10))
					{
						p.sendMessage("1~10�� ���ڸ� �Է� �����մϴ�.");
						return;
					}
						getConfig().set("checkpoint_world"+i, p.getWorld().getName());
						getConfig().set("checkpoint_x"+i, p.getLocation().getBlockX());
						getConfig().set("checkpoint_y"+i, p.getLocation().getBlockY()-1);
						getConfig().set("checkpoint_z"+i, p.getLocation().getBlockZ());
					    saveConfig();
					    Loadconfig();
						p.sendMessage(MS+i+"��° üũ����Ʈ ���� ���� �Ϸ�");
						return;
			}
			if(!args[1].equalsIgnoreCase("bed") && !args[1].equalsIgnoreCase("race")) return;
			String gamename = args[1];
			if(args[2].equalsIgnoreCase("lobby"))
			{
			    getConfig().set("Lobby_world", p.getLocation().getWorld().getName());
			    getConfig().set("Lobby_x", Integer.valueOf(p.getLocation().getBlockX()));
			    getConfig().set("Lobby_y", Integer.valueOf(p.getLocation().getBlockY() + 1));
			    getConfig().set("Lobby_z", Integer.valueOf(p.getLocation().getBlockZ()));
			    saveConfig();
			    Loadconfig();
				p.sendMessage(MS+"�κ� ���� �Ϸ�");
			}
			else if(args[2].equalsIgnoreCase("join"))
			{
			    getConfig().set("Join_world_"+gamename, p.getLocation().getWorld().getName());
			    getConfig().set("Join_x_"+gamename, Integer.valueOf(p.getLocation().getBlockX()));
			    getConfig().set("Join_y_"+gamename, Integer.valueOf(p.getLocation().getBlockY() + 1));
			    getConfig().set("Join_z_"+gamename, Integer.valueOf(p.getLocation().getBlockZ()));
			    saveConfig();
			    Loadconfig();
				p.sendMessage(MS+"���� ���� �Ϸ�");
			}
			else if(args[2].equalsIgnoreCase("start"))
			{
				if(gamename.equalsIgnoreCase("race")){
			    getConfig().set("start_world"+gamename, p.getLocation().getWorld().getName());
			    getConfig().set("start_x"+gamename, Integer.valueOf(p.getLocation().getBlockX()));
			    getConfig().set("start_y"+gamename, Integer.valueOf(p.getLocation().getBlockY() + 1));
			    getConfig().set("start_z"+gamename, Integer.valueOf(p.getLocation().getBlockZ()));
				p.sendMessage(MS+"�������� ���� �Ϸ�");
				} else if(gamename.equals("bed")){
					if(args.length <= 3) return;
				    getConfig().set("start_world"+gamename+args[3], p.getLocation().getWorld().getName());
				    getConfig().set("start_x"+gamename+args[3], Integer.valueOf(p.getLocation().getBlockX()));
				    getConfig().set("start_y"+gamename+args[3], Integer.valueOf(p.getLocation().getBlockY() + 1));
				    getConfig().set("start_z"+gamename+args[3], Integer.valueOf(p.getLocation().getBlockZ()));
					p.sendMessage(MS+args[3]+"��° ���� ���� ���� �Ϸ�(�ִ� 20)");
				}
			    saveConfig();
			    Loadconfig();
			}
			else
			{
				p.sendMessage(MS+"/pkr set race/bed lobby");
				p.sendMessage(MS+"/pkr set race/bed join");
				p.sendMessage(MS+"/pkr set race/bed start 1~15");
				p.sendMessage(MS+"/pkr set cp 1~10");
			}
			return;
		}
	}
	
	public static void Sendpatmessage(String str)
	{
		for(Player p : patlist)
		{
			p.sendMessage(str);
		}
	}
	
	public static void Sendracemessage(String str)
	{
		for(Player p : tlist)
		{
			p.sendMessage(str);
		}
	}
	
	public static void Sendbedmessage(String str)
	{
		for(Player p : blist)
		{
			p.sendMessage(str);
		}
	}
	
	public static void GameJoinrace(Player p)
	{
		if(tlist.contains(p))
		{
			p.sendMessage(MS+"�̹� ���ӿ� �������̽ʴϴ�.");
			return;
		}
		if(tlist.size() >= 10)
		{
			p.sendMessage(MS+"�̹� �ִ��ο�(10)�Դϴ�.");
			return;
		}
		if(check_start_race)
		{
			p.sendMessage(MS+"�̹� ������ �������Դϴ�.");
			return;
		}
		else
		{
			tlist.add(p);
			plist.add(p);
			p.teleport(joinpos_race);
			p.getInventory().clear();
			p.getInventory().setHelmet(null);
			p.getInventory().setChestplate(null);
			p.getInventory().setLeggings(null);
			p.getInventory().setBoots(null);
			p.getInventory().setItem(8, helpitem);
			me.Bokum.EGM.Main.gamelist.put(p.getName(), "���� ���̽�");
			Sendracemessage(MS+p.getName()+" ���� ���� ���̽̿� �����ϼ̽��ϴ�. �ο� (��e "+tlist.size()+"��7 / ��c1 ��f)");
			p.sendMessage(MS+"��c��l��ħ�� ��Ŭ�� -> ��ŷ���� ��7�� ��ŷ�� ���� �� �ֽ��ϴ�.");
			if(!check_lobbystart_race && tlist.size() >= 1)
			{
				Startgamerace();
				RestoreBlock();
			}
			for(Player t : tlist){
				t.playSound(t.getLocation(), Sound.NOTE_PLING, 2.0f, 1.0f);
			}
		}
	}
	
	public static void GameJoinbed(Player p)
	{
		if(blist.contains(p))
		{
			p.sendMessage(MS+"�̹� ���ӿ� �������̽ʴϴ�.");
			return;
		}
		if(blist.size() >= 12)
		{
			p.sendMessage(MS+"�̹� �ִ��ο�(12)�Դϴ�.");
			return;
		}
		if(check_start_bed)
		{
			p.sendMessage(MS+"�̹� ������ �������Դϴ�.");
			return;
		}
		else
		{
			blist.add(p);
			plist.add(p);
			p.teleport(joinpos_bed);
			p.getInventory().clear();
			p.getInventory().setHelmet(null);
			p.getInventory().setChestplate(null);
			p.getInventory().setLeggings(null);
			p.getInventory().setBoots(null);
			p.getInventory().setItem(8, helpitem);
			me.Bokum.EGM.Main.gamelist.put(p.getName(), "���� ȣ������");
			Sendbedmessage(MS+p.getName()+" ���� ���� ȣ�����￡ �����ϼ̽��ϴ�. �ο� (��e "+blist.size()+"��7 / ��c7 ��f)");
			if(!check_lobbystart_bed && blist.size() >= 7)
			{
				Startgamebed();
			}
			for(Player t : blist){
				t.playSound(t.getLocation(), Sound.NOTE_PLING, 2.0f, 1.0f);
			}
		}
	}
	
	public static void GameJoinPAT(Player p)
	{
		if(patlist.contains(p))
		{
			p.sendMessage(MS+"�̹� ���ӿ� �������̽ʴϴ�.");
			return;
		}
		if(patlist.size() >= 12)
		{
			p.sendMessage(MS+"�̹� �ִ��ο�(12)�Դϴ�.");
			return;
		}
		if(check_start_pat)
		{
			p.sendMessage(MS+"�̹� ������ �������Դϴ�.");
			return;
		}
		else
		{
			patlist.add(p);
			plist.add(p);
			p.teleport(joinpos_pat);
			p.getInventory().clear();
			p.getInventory().setHelmet(null);
			p.getInventory().setChestplate(null);
			p.getInventory().setLeggings(null);
			p.getInventory().setBoots(null);
			p.getInventory().setItem(8, helpitem);
			me.Bokum.EGM.Main.gamelist.put(p.getName(), "������ ����");
			Sendpatmessage(MS+p.getName()+" ���� ���� ������ ���Ͽ� �����ϼ̽��ϴ�. �ο� (��e "+tlist.size()+"��7 / ��c8 ��f)");
			p.sendMessage(MS+"��c��l��ħ�� ��Ŭ�� -> ��ŷ���� ��7�� ��ŷ�� ���� �� �ֽ��ϴ�.");
			if(!check_lobbystart_race && tlist.size() >= 8)
			{
				Startgamepat();
			}
			for(Player t : patlist){
				t.playSound(t.getLocation(), Sound.NOTE_PLING, 2.0f, 1.0f);
			}
		}
	}
	
	public void GameQuitrace(Player p)
	{
		if(!tlist.contains(p) && !blist.contains(p))
		{
			return;
		}
		tlist.remove(p);
		plist.remove(p);
		if(!check_start_race)
		{
			return;
		}
		Sendracemessage(MS+p.getName()+" ��f���� �����ϼ̽��ϴ�.");
		if(tlist.size() <= 0)
		{
			Bukkit.broadcastMessage(MS+"���� ���̽��� ���� �Ǿ����ϴ�.");
			Cleardata_race();
		}
	}
	
	public void GameQuitpat(Player p)
	{
		if(!patlist.contains(p))
		{
			return;
		}
		patlist.remove(p);
		plist.remove(p);
		if(!check_start_pat)
		{
			return;
		}
		Sendpatmessage(MS+p.getName()+" ��f���� �����ϼ̽��ϴ�.");
		if(patlist.size() <= 0)
		{
			Bukkit.broadcastMessage(MS+"���� ������ ������ ���� �Ǿ����ϴ�.");
			//Cleardata_pat();
		}
	}
	
	public static void TrainingJoin(Player p){
		p.getInventory().clear();
		p.getInventory().addItem(seerank);
		p.sendMessage(MS+"Ʈ���̴׷����� �̵��մϴ�.");
		p.teleport(training_pos);
		plist.add(p);
	}
	
	public static void PracticeJoin(Player p){
		p.getInventory().clear();
		p.getInventory().addItem(seerank);
		p.sendMessage(MS+"���� ���������� �̵��մϴ�.");
		p.teleport(practice_pos);
		plist.add(p);
	}
	
	public static void GameQuitbed(Player p)
	{
		if(!tlist.contains(p) && !blist.contains(p))
		{
			return;
		}
		blist.remove(p);
		plist.remove(p);
		if(!check_start_bed)
		{
			return;
		}
		if(pumpkin_loc.containsKey(p.getName())){
			try{
			pumpkin_loc.get(p.getName()).getBlock().setType(Material.AIR);
			}catch(Exception e){
				
			}
		}
		insertingpassword.remove(p.getName());
		pumpkin_loc.remove(p.getName());
		password.remove(p.getName());
		solvingtarget.remove(p.getName());
		deathcount.remove(p.getName());
		Sendbedmessage(MS+p.getName()+" ��f���� Ż���ϼ̽��ϴ�.");
		UpdateInfo();
		if(blist.size() == 1)
		{
			try{
			Winbed(blist.get(0));
			}catch(Exception e){
				Forcestop_bed();
			}
		}
	}
	
	public static void Winrace(final Player p)
	{
		p.playSound(p.getLocation(), Sound.ANVIL_LAND, 2.5f, 1.5f);
		Sendracemessage(MS+p.getName()+"���� �¸��߽��ϴ�!");
		try{
		Bukkit.getScheduler().scheduleSyncDelayedTask(instance, new Runnable()
		{
			public void run()
			{
				EconomyResponse r = econ.depositPlayer(p.getName(), 1500);
				if (r.transactionSuccess()) {
					p.sendMessage(ChatColor.GOLD + "�¸� �������� 1500���� �����̽��ϴ�.");
				}
				for(Player t : tlist){
					EconomyResponse r1 = econ.depositPlayer(t.getName(), 800);
					if (r1.transactionSuccess()) {
						t.sendMessage(ChatColor.GOLD + "�÷��� �������� 800���� �����̽��ϴ�.");
					}
					try{
						me.Bokum.EGM.Main.Spawn(t);
						} catch(Exception e){
						t.teleport(Main.Lobby);
						}
				}
				Cleardata_race();
				Bukkit.broadcastMessage(MS+"��b��l���� ���̡̽�f�� ��9"+p.getName()+"��f���� �¸��� ���� �ƽ��ϴ�.");
			}
				}, 140L);
		}catch(Exception e){
	    	Forcestop_race();
	    }
	}
	
	public static void Winbed(final Player p)
	{
		p.playSound(p.getLocation(), Sound.ANVIL_LAND, 2.5f, 1.5f);
		if(pumpkin_loc.containsKey(p.getName())){
			try{
			pumpkin_loc.get(p.getName()).getBlock().setType(Material.AIR);
			}catch(Exception e){
				
			}
		}
		Sendbedmessage(MS+p.getName()+"���� �¸��߽��ϴ�!");
		try{
		Bukkit.getScheduler().scheduleSyncDelayedTask(instance, new Runnable()
		{
			public void run()
			{
				EconomyResponse r = econ.depositPlayer(p.getName(), 1500);
				p.sendMessage(ChatColor.GOLD + "�¸� �������� 1500���� �����̽��ϴ�.");
				me.Bokum.EGM.Main.Spawn(p);
				Cleardata_bed();
				Bukkit.broadcastMessage(MS+"��6��l���� ȣ�������f�� ��9"+p.getName()+"��f���� �¸��� ���� �ƽ��ϴ�.");
			}
				}, 140L);
		}catch(Exception e){
	    	Forcestop_race();
	    }
	}
	
	public static int Getrandom(int min, int max){
		  return (int)(Math.random() * (max-min+1)+min);
		}

	  public void GameHelperrace(Player p, int slot){
		  switch(slot){
		  case 11:
			  p.sendMessage("��7���� �̸� ��f: ��c���� ���̽�");
			  p.sendMessage("��f���ø� �����Ӱ� �ŴҸ� ����� �����մϴ�.");
			  p.sendMessage("��f�پ��� �ൿ�� ( ������, ����Ÿ��, �ָ��ٱ� ���)��");
			  p.sendMessage("��f�̿��Ͽ� ���� ���������!");
			  p.sendMessage("��c���ǿ��� Ʃ�丮���� �غ�����!");
			  p.closeInventory();
			  return;
			  
		  case 13:
			  p.sendMessage("��f�� Ʈ���̴׷뿡�� �⺻������ ��켼��!");
			  p.sendMessage("��f������ 1��, 2��, 3��Ը� �־����ϴ�.");
			  p.sendMessage("��f���� ����� ����� ��ŷ�� �����ϴ�.");
			  p.closeInventory();
			  return;
			  
		  case 15:
			  showRank(p);
			  p.closeInventory();
			  return;
			  
		  default: return;
		  }  
	  }
	  
	  public static void GameHelperbed(Player p, int slot){
		  switch(slot){
		  case 11:
			  p.sendMessage("��7���� �̸� ��f: ��c���� ȣ������");
			  p.sendMessage("��f������ ���۵Ǹ� ��eȣ�ڡ�f�� �ð踦 �޽��ϴ�.");
			  p.sendMessage("��fȣ���� ��ġ�ϸ� ��a��й�ȣ��f�� �Է��ϴ� â�� �߸�");
			  p.sendMessage("��f���ϴ� ��й�ȣ�� �����ؾ��մϴ�.");
			  p.sendMessage("��f��й�ȣ�� �����Ͻ� �� ȣ���� �ٽ� Ŭ���Ͻø�");
			  p.sendMessage("��f������ ���� �� �ֽ��ϴ�. ���� �ٸ� �÷��̾���");
			  p.sendMessage("��fȣ���� Ŭ���ϸ� �� �÷��̾ ������ ��й�ȣ�� �Է��ؾ��ϸ� ");
			  p.sendMessage("��f�ǹٸ� ��й�ȣ�� �Է��ϸ� �� �÷��̾ Ż����ų �� �ֽ��ϴ�.");
			  p.sendMessage("��f�̷��� ������ 1���� �Ǹ� �¸��մϴ�.");
			  p.closeInventory();
			  return;
			  
		  case 13:
			  p.sendMessage("��f������ 2����� �����մϴ�.");
			  p.sendMessage("��f�÷��̾ ���������� �� �÷��̾��� ��й�ȣ 1�ڸ���");
			  p.sendMessage("��f�� �� �ֽ��ϴ�. ���� �ʿ� �ִ� ���ڸ� Ŭ���ϸ�");
			  p.sendMessage("��f1~4���� ���� �� �������� ������ �� ������");
			  p.sendMessage("��f�÷��̾ ���� 10��, �÷��̾ Ż����ų��");
			  p.sendMessage("��f50���� ������ ������ �� �ֽ��ϴ�.");
			  p.sendMessage("��f���� ���Ĺ��� ��Ÿ���� 1���Դϴ�.");
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
		
		public static void Startgamerace()
		{
			check_lobbystart_race = true;
			final int cur = Getcursch();
			tasktime[cur] = 6;
			Bukkit.broadcastMessage(MS+"��2��l���� ���̡̽�f�� �� ���۵˴ϴ�.");
			tasknum[cur] = Bukkit.getScheduler().scheduleSyncRepeatingTask(instance, new Runnable()
			{
				public void run()
				{
					if(tasktime[cur] > 0)
					{
						Sendracemessage(MS+"������ "+tasktime[cur]*10+" �� �� ���۵˴ϴ�.");
						tasktime[cur]--;
					}
					else
					{
						Canceltask(cur);
						check_start_race = true;
						for(Player p : tlist){
							p.teleport(startpos_race);
						}
						CountDown();
					}
				}
			}, 200L, 200L);
		}
		
		public static void Startgamebed()
		{
			check_lobbystart_bed = true;
			final int cur = Getcursch();
			tasktime[cur] = 6;
			Bukkit.broadcastMessage(MS+"��6��l���� ȣ�������f�� �� ���۵˴ϴ�.");
			tasknum[cur] = Bukkit.getScheduler().scheduleSyncRepeatingTask(instance, new Runnable()
			{
				public void run()
				{
					if(tasktime[cur] > 0)
					{
						Sendbedmessage(MS+"������ "+tasktime[cur]*10+" �� �� ���۵˴ϴ�.");
						tasktime[cur]--;
					}
					else
					{
						Canceltask(cur);
						check_start_bed = true;
						for(Player p : blist){
							climbinglist.remove(p.getName());
							p.teleport(startpos_bed[blist.indexOf(p)]);
							GiveBaseItem(p);
							deathcount.put(p.getName(), 0);
						}
						checkplacepumpkin();
					}
				}
			}, 200L, 200L);
		}

		public static void Startgamepat()
		{
			check_lobbystart_bed = true;
			final int cur = Getcursch();
			tasktime[cur] = 6;
			Bukkit.broadcastMessage(MS+"��e��l���� ������ ���ϡ�f�� �� ���۵˴ϴ�.");
			tasknum[cur] = Bukkit.getScheduler().scheduleSyncRepeatingTask(instance, new Runnable()
			{
				public void run()
				{
					if(tasktime[cur] > 0)
					{
						Sendpatmessage(MS+"������ "+tasktime[cur]*10+" �� �� ���۵˴ϴ�.");
						tasktime[cur]--;
					}
					else
					{
						Canceltask(cur);
						check_start_pat = true;
						for(Player p : patlist){
							climbinglist.remove(p.getName());
							p.teleport(startpos_pat);
						}
						PATTimer();
						for(int i = 0; i < 10; i++){
							PAT_Place_Chest();
						}
					}
				}
			}, 200L, 200L);
		}
		
		public static void PATTimer(){
			List<Player> tmplist = new ArrayList<Player>(patlist.size());
			for(Player p : patlist){
				p.sendMessage(MS+"������ ���۵Ǿ����ϴ�.");
				p.playSound(p.getLocation(), Sound.ANVIL_LAND, 1.0f, 1.0f);
				tmplist.add(p);
			}
			
			for(int i = 0; i < tmplist.size(); i++){
				Player p = tmplist.get(Getrandom(0, tmplist.size()-1));
				if(pat_theif.size()-1 <= pat_police.size()){
					pat_theif.add(p);
					p.sendMessage(MS+"����� �����Դϴ�.");
					PAT_TheifItem(p);
				}else{
					pat_police.add(p);
					p.sendMessage(MS+"����� �����Դϴ�.");
					PAT_PoliceItem(p);
				}
			}
			
			final int cur = Getcursch(); 
			
			tasktime[cur] = 721;
			for(Player p : patlist){
				p.sendMessage(MS+"���� �ð��� ������ Ȯ���Ͻ� �� �ֽ��ϴ�.");
				p.playSound(p.getLocation(), Sound.CLICK, 1.5f, 0.5f);
			}
			tasknum[cur] = Bukkit.getScheduler().scheduleSyncRepeatingTask(instance, new Runnable(){
				public void run(){
					for(Player p : patlist){
						p.setLevel(--tasktime[cur]);
					}
					if(tasktime[cur] <= 0){
						Bukkit.getScheduler().cancelTask(tasktime[cur]);
						//PAT_PoliceWin();
					}
				}
			}, 200l, 20l);
		}

		public static void PAT_Place_Chest(){
			for(int i = 0; i < 100; i++){
				int rn = Getrandom(0, 39);
				if(pat_chest_pos[rn].getBlock().getType() == Material.AIR){
					pat_chest_pos[rn].getBlock().setType(Material.CHEST);
					break;
				}
			}
		}
		
		public static void checkplacepumpkin(){
			final int cur = Getcursch();
			tasktime[cur] = 200;
			tasknum[cur] = Bukkit.getScheduler().scheduleSyncRepeatingTask(instance, new Runnable()
			{
				public void run()
				{
					if(tasktime[cur] > 0)
					{
						for(Player p : blist){
							p.setLevel(tasktime[cur]);
						}
						if(tasktime[cur] <= 30){
							for(Player p : blist){
								p.playSound(p.getLocation(), Sound.NOTE_PLING, 2.0f, 1.0f);
								if(!password.containsKey(p.getName())){
									p.sendMessage(MS+"������ "+tasktime[cur]+"�� �ȿ� ��й�ȣ�� �������� ������ ����մϴ�. ");
								}
							}
						}
						tasktime[cur]--;
					}
					else
					{
						Canceltask(cur);
						for(int i = 0; i < blist.size(); i++){
							Player p = blist.get(i);
							p.playSound(p.getLocation(), Sound.ANVIL_LAND, 2.0f, 1.0f);
							if(!password.containsKey(p.getName())){
								if(pumpkin_loc.containsKey(p.getName())){
									Location l = pumpkin_loc.get(p.getName());
									l.getBlock().setType(Material.AIR);
								}
								p.sendMessage(MS+"��й�ȣ�� �������� �ʾ� ����ϼ̽��ϴ�.");
								GameQuitbed(p);
								me.Bokum.EGM.Main.Spawn(p);
								i--;
							}
						}
						Sendbedmessage(MS+"��� �÷��̾ ȣ���� ��ġ�߽��ϴ�. ���� �������� ������ �����մϴ�.");
						for(Player t : blist){
							t.setLevel(0);
							pumpkinclearlist.add(pumpkin_loc.get(t.getName()));
						}
						pvp = true;
						UpdateInfo();
					}
				}
			}, 20L, 20L);
		}
		
		public static void GiveBaseItem(Player p){	
			p.getInventory().addItem(pumpkin);
			p.getInventory().addItem(clock);
		}
		
		public static void PAT_TheifItem(Player p){
			for(int i = 0; i < 2; i++){
				p.getInventory().addItem(pat_invisible);
				p.getInventory().addItem(chestitem[24]);
			}
		}
		
		public static void PAT_PoliceItem(Player p){
			for(int i = 0; i < 2; i++){
				p.getInventory().addItem(pat_stunstick);
				p.getInventory().addItem(pat_stungun);
			}
		}
		
		public static void CountDown(){
			final int cur = Getcursch();
			tasktime[cur] = 11;
			tasknum[cur] = Bukkit.getScheduler().scheduleSyncRepeatingTask(instance, new Runnable(){
				public void run(){
					if(--tasktime[cur] > 0){
						for(Player p : tlist){
							p.playSound(p.getLocation(), Sound.NOTE_PLING, 2.0f, 1.0f);
							p.sendMessage(MS+tasktime[cur]+"���� ���ָ� �����մϴ�.");
						}
					}else{
						Canceltask(cur);
						for(Player p : tlist){
							p.playSound(p.getLocation(), Sound.NOTE_PIANO, 2.0f, 1.0f);
							p.sendMessage(MS+"���ְ� ���۵ƽ��ϴ�!");
							cplist.put(p.getName(), -1);
							time.put(p.getName(), (double)java.lang.System.currentTimeMillis()/1000);
						}
						Removefence();
						EndTimer();
					}
					
				}
			}, 0l, 20l);
		}
		
		public static void EndTimer(){
			Bukkit.getScheduler().cancelTask(endtimernum);
			endtimertime = 20;
			endtimernum = Bukkit.getScheduler().scheduleSyncRepeatingTask(instance, new Runnable(){
				public void run(){
					if(endtimertime > 0){
						for(Player p : tlist){
							p.playSound(p.getLocation(), Sound.ARROW_HIT, 2.0f, 0.5f);
						}
						Sendracemessage(MS+"��c"+endtimertime+" �� ��e���ҽ��ϴ�.");
						endtimertime--;
					} else {
						Bukkit.getScheduler().cancelTask(endtimernum);
						for(Player p : tlist){
							p.playSound(p.getLocation(), Sound.GHAST_CHARGE, 2.0f, 1.5f);
						}
						check_raceend = true;
						Sendracemessage(MS+"��c��Ⱑ ���� �Ǿ����ϴ�.");
						Bukkit.getScheduler().scheduleSyncDelayedTask(instance, new Runnable(){
							public void run(){
								for(Player t : tlist){
									econ.depositPlayer(t.getName(), 800);
									t.sendMessage(ChatColor.GOLD + "�÷��� �������� 800���� �����̽��ϴ�.");
									me.Bokum.EGM.Main.Spawn(t);
								}
								Cleardata_race();
							}
						}, 140l);
					}
				}
			}, 600L, 1200L);
		}

		public static void Removefence(){
			for(Location loc : blockloc){
				loc.getBlock().setType(Material.AIR);
			}
		}
		
		public void Timer(){
			Bukkit.getScheduler().scheduleSyncRepeatingTask(instance, new Runnable(){
				public void run(){
					for(Player p : plist){
						if(p.getExp() != 1){
							if(!climbinglist.containsKey(p.getName())){
								if(!p.isSprinting()){
									p.setExp(p.getExp()+0.02f > 1 ? 1 : p.getExp()+0.075f);
								} else {
									p.setExp(p.getExp()+0.02f > 1 ? 1 : p.getExp()+0.05f);
								}
							} else {
								p.setExp(p.getExp()+0.02f > 1 ? 1 : p.getExp()+0.05f);
							}
						}
					}
				}
			}, 20L, 30l);

		}
		
		public void PasswordClick(Player p, int slot){
			String num = "0";
			p.playSound(p.getLocation(),Sound.CHICKEN_EGG_POP, 2.0f, 0.8f);
			switch(slot){
			case 10: num = "1"; break;
			case 11: num = "2"; break;
			case 12: num = "3"; break;
			case 19: num = "4"; break;
			case 20: num = "5"; break;
			case 21: num = "6"; break;
			case 28: num = "7"; break;
			case 29: num = "8"; break;
			case 30: num = "9"; break;
			case 38: num = "0"; break;
			case 15: p.sendMessage(MS+"�ʱ�ȭ �߽��ϴ�."); insertingpassword.put(p.getName(), ""); return;
			case 33: if(insertingpassword.containsKey(p.getName())){
				if(insertingpassword.get(p.getName()).length() != 4){
					p.sendMessage(MS+"4�ڸ��� ��й�ȣ�� �Է����ּ���.");
					return;
				} else {
					p.closeInventory();
					p.sendMessage(MS+"��6��й�ȣ�� ������ �Ϸ�Ǿ����ϴ�. ��й�ȣ : ��c"+insertingpassword.get(p.getName()));
					password.put(p.getName(), insertingpassword.get(p.getName()));
					insertingpassword.remove(p.getName());
					deathcount.put(p.getName(), 0);
					return;
				}
			}
			default: return;
			}
			
			if(insertingpassword.containsKey(p.getName()) && insertingpassword.get(p.getName()).length() >= 4){
				p.sendMessage(MS+"��й�ȣ�� 4�ڸ������� �Է� �����մϴ�.");
			} else {
				insertingpassword.put(p.getName(), insertingpassword.containsKey(p.getName()) ? insertingpassword.get(p.getName())+num : num);
				p.sendMessage(MS+"�Է��� ��й�ȣ : "+insertingpassword.get(p.getName()));
			}
		}
		
		public void SolvePassword(Player p, int slot){
			String num = "0";
			p.playSound(p.getLocation(),Sound.CHICKEN_EGG_POP, 2.0f, 0.8f);
			switch(slot){
			case 10: num = "1"; break;
			case 11: num = "2"; break;
			case 12: num = "3"; break;
			case 19: num = "4"; break;
			case 20: num = "5"; break;
			case 21: num = "6"; break;
			case 28: num = "7"; break;
			case 29: num = "8"; break;
			case 30: num = "9"; break;
			case 38: num = "0"; break;
			case 15: p.sendMessage(MS+"�ʱ�ȭ �߽��ϴ�."); insertingpassword.put(p.getName(), ""); return;
			case 33: if(insertingpassword.containsKey(p.getName())){
				if(insertingpassword.get(p.getName()).length() != 4){
					p.sendMessage(MS+"4�ڸ��� ��й�ȣ�� �Է����ּ���.");
					return;
				} else {
					Player target = solvingtarget.get(p.getName());
					if(insertingpassword.get(p.getName()).equalsIgnoreCase(password.get(target.getName()))){
						p.closeInventory();
						insertingpassword.remove(p.getName());
						pumpkin_loc.get(target.getName()).getBlock().setType(Material.AIR);
						Sendbedmessage(MS+p.getName()+" ���� "+target.getName()+" ���� ��й�ȣ�� Ǯ�����ϴ�!");
						GameQuitbed(target);
						target.damage(100);
						p.getWorld().playSound(p.getLocation(), Sound.CHEST_OPEN, 2.0f, 1.0f);
						p.sendMessage(MS+"��ȣ ���� �������� 50������ �޾ҽ��ϴ�.");
						p.getInventory().addItem(coin(50));
						p.updateInventory();
					} else {
						insertingpassword.remove(p.getName());
						p.getWorld().playSound(p.getLocation(), Sound.ANVIL_USE, 2.0f, 1.0f);
						p.sendMessage(MS+"��й�ȣ�� Ʋ�Ƚ��ϴ�!");
					}
				}
			}
			default: return;
			}
			
			if(insertingpassword.containsKey(p.getName()) && insertingpassword.get(p.getName()).length() >= 4){
				p.sendMessage(MS+"��й�ȣ�� 4�ڸ������� �Է� �����մϴ�.");
			} else {
				insertingpassword.put(p.getName(), insertingpassword.containsKey(p.getName()) ? insertingpassword.get(p.getName())+num : num);
				p.sendMessage(MS+"�Է��� ��й�ȣ : "+insertingpassword.get(p.getName()));
			}
		}
		
		public boolean Usestamina(Player p, int amt){
			float minus = (float) amt / 100;
			if(p.getExp() - minus < 0){
				p.sendMessage(MS+"���׹̳��� �����մϴ�.");
				return true;
			}
			p.setExp(p.getExp()-minus);
			return false;
		}
		
		public static void UpdateInfo(){
			infoinven.clear();
			for(int j = 0; j < blist.size(); j++){
				Player p = blist.get(j);
				String pass = "";
				for(int i = 0; i < 4; i++){
					if(deathcount.get(p.getName()) > i){
						pass += password.get(p.getName()).charAt(i);
					} else {
						pass += "?";
					}
				}
				String loc = "?";
				if(deathcount.get(p.getName()) >= 5){
					Location pl = pumpkin_loc.get(p.getName());
					loc = "x : "+pl.getBlockX()+", y : "+pl.getBlockY()+", z : "+pl.getBlockZ();
				}
				ItemStack item = new ItemStack(397, 1, (byte) 3);
				ItemMeta meta = item.getItemMeta();
				meta.setDisplayName("��6"+p.getName());
				List<String> lorelist = new ArrayList<String>();
				lorelist.add("��c��й�ȣ : "+pass);
				lorelist.add("��c���� ��ǥ : "+loc);
				meta.setLore(lorelist);
				item.setItemMeta(meta);
				infoinven.setItem(j, item);
			}
			Sendbedmessage(MS+"��c���� ����� ���� �Ǿ����ϴ�. �ð踦 ��Ŭ���غ�����.");
		}
		
		public void InputPassword(Player p, Location bl){
			Player target = null;
			for(Player t : blist){
				if(pumpkin_loc.containsKey(t.getName())){
				Location loc = pumpkin_loc.get(t.getName());
				if(loc.getBlockX() == bl.getBlockX() && loc.getBlockY() == bl.getBlockY() && loc.getBlockZ() == bl.getBlockZ()){
					target = t;
				}
				}
			}
			if(target == null){
				p.sendMessage(MS+"�� ������ ������ ���ӿ� �������� �ƴմϴ�.");
				return;
			}
			if(!password.containsKey(target.getName())){
				p.sendMessage(MS+"�� ������ ������ ���� ��й�ȣ�� �������� �ʾҽ��ϴ�.");
				return;
			}
			if(target.getName() == p.getName()){
				p.openInventory(shopinven);
				return;
			}
			if(!pvp){
				p.sendMessage(MS+"���� ųŸ���� �ƴմϴ�.");
				return;
			}
			target.sendMessage(MS+"�������� ����� ��й�ȣ�� Ǯ���ֽ��ϴ�.");
			p.sendMessage(MS+"�� ������ "+target.getName()+" ���� �����Դϴ�.");
			insertingpassword.remove(p.getName());
			solvingtarget.put(p.getName(), target);
			p.openInventory(passwordinven);
		}
		
		public void Usecheckpoint(final Player p, Location bcl){
			int cpnum = -1;
			for(int i = 0; i < 10; i++){
				if(bcl.getBlockX() == checkpoint[i].getBlockX() && bcl.getBlockY() == checkpoint[i].getBlockY() && bcl.getBlockZ() == checkpoint[i].getBlockZ()){
					cpnum = i;
					break;
				}
			}
			if(cpnum == -1) return;
			if(cplist.get(p.getName()) >= cpnum) return;
			if(cplist.get(p.getName())+1 == cpnum){
				p.playSound(p.getLocation(), Sound.NOTE_PLING, 2.0f, 1.0f);
				cplist.put(p.getName(), cpnum);
				p.setLevel(cpnum+1);
				Sendracemessage(MS+p.getName()+" �Բ��� "+(cplist.get(p.getName())+1)+" ��° üũ����Ʈ�� �����߽��ϴ�.");
				for(Player t : tlist){
					t.playSound(t.getLocation(), Sound.ANVIL_LAND, 2.0f, 1.0f);
				}
				if(cpnum >= 9){
					Sendracemessage(MS+p.getName()+" ���� �����߽��ϴ�!");
					double racetime = ((double) java.lang.System.currentTimeMillis()/1000 - time.get(p.getName()));
					racetime = (int)(racetime*1000);
					racetime = (double) racetime / 1000;
					p.sendMessage(MS+"����� ��� : ��c"+racetime+"��f ��");
					if(!check_raceend){
						Bukkit.getScheduler().cancelTask(endtimernum);
						check_raceend = true;
						try{
						final int cur = Getcursch();
						tasktime[cur] = 30;
						tasknum[cur] = Bukkit.getScheduler().scheduleSyncRepeatingTask(this, new Runnable()
						{
							public void run()
							{
								if(tasktime[cur] > 0)
								{
									for(Player t : tlist){
										t.playSound(t.getLocation(), Sound.NOTE_PLING, 2.0f, 1.0f);
									}
									Sendracemessage(MS+"���ְ� "+tasktime[cur]+" �� �� ����˴ϴ�.");
									tasktime[cur]--;
								}
								else
								{
									Canceltask(cur);
									Winrace(p);
								}
							}
						}, 0L, 20L);
						}catch(Exception e){
							Forcestop_race();
						}
					}
					me.Bokum.EGM.Main.getPlayerData(p.getName()).set("Parkour_racetime", racetime);
					try{
						me.Bokum.EGM.Main.getPlayerData(p.getName()).save(new File(me.Bokum.EGM.Main.instance.getDataFolder().getPath()+"/data", p.getName()+".data"));
						} catch(Exception e){
							
						}
					for(int i = 0; i < rank_time.size(); i++){
						if(rank_time.get(i) > racetime){
							if(rank_name.contains(p.getName())){
								if(rank_time.get(rank_name.indexOf(p.getName())) <= racetime) return;
								else{
									for(int j = rank_name.indexOf(p.getName()); j > i; j--){
										rank_name.set(j, rank_name.get(j-1));
										rank_time.set(j, rank_time.get(j-1));
									} 
									rank_name.set(i, p.getName());
									rank_time.set(i, racetime);
								}
							}else{
								for(int j = 9; j > i; j--){
									rank_name.set(j, rank_name.get(j-1));
									rank_time.set(j, rank_time.get(j-1));
								} 
								rank_name.set(i, p.getName());
								rank_time.set(i, racetime);
							}
							saveRank();
							Bukkit.broadcastMessage(MS+"��2���� ���̡̽�7���� ��e"+p.getName()+" ��7�Բ��� ��e"+racetime+"��7�� �� ������� ��e"+(i+1)+"��7���� �ϼ̽��ϴ�.");
							return;
						}
					}
				}
			}
			if(cplist.get(p.getName())+1 < cpnum){
				p.sendMessage(MS+"���� "+(cplist.get(p.getName())+2)+" ��° üũ����Ʈ�� �����մϴ�.");
			}
		}
		
		public void showRank(Player p){
			for(int i = 0; i < 10; i++){
				p.sendMessage(MS+(i+1)+"�� : ��6"+rank_name.get(i)+" ��7- ��b"+rank_time.get(i)+" ��");
			}
		}
		
		public void saveRank(){
			FileConfiguration rankconfig = me.Bokum.EGM.Main.getRank("Parkour");
			for(int i = 0; i < 10; i++){
				rankconfig.set("rank_name_"+i, rank_name.get(i));
				rankconfig.set("rank_time_"+i, rank_time.get(i));
			}
			try{
			rankconfig.save(new File(me.Bokum.EGM.Main.instance.getDataFolder().getPath()+"/rank", "Parkour.rank"));
			} catch(Exception e){
				
			}
		}
		
		@EventHandler
		public void onPlayerMove(PlayerMoveEvent e){
			if(!plist.contains(e.getPlayer())) return;
				Player p = e.getPlayer();
				if(blist.contains(p) && pumpkin_loc.containsKey(p.getName()) && !password.containsKey(p.getName())){
					insertingpassword.remove(p.getName());
					p.openInventory(passwordinven);
					p.sendMessage(MS+"��й�ȣ�� �Է����ּ���.");
					return;
				}
				if(p.getGameMode() == GameMode.CREATIVE) return;
				if(p.getLocation().getY() < 0){
					p.damage(100);
					return;
				}
				if(e.getFrom().getX() == e.getTo().getX() && e.getFrom().getY() == e.getTo().getY()
						&& e.getFrom().getZ() == e.getTo().getZ()) return;
				Material reb = p.getLocation().getBlock().getRelative(0, -1, 0).getType();
				if(reb == Material.BEACON) Usecheckpoint(p,p.getLocation().getBlock().getRelative(0, -1, 0).getLocation());
				if(reb == Material.PISTON_BASE) p.teleport(new Location(p.getWorld(), -2000, 50, 400));
				if(reb == Material.PISTON_STICKY_BASE && p.getExp() < 1){
					p.setExp(1); p.sendMessage(MS+"���׹̳��� ȸ�� �Ǿ����ϴ�.");
				}
				if(p.getLocation().getY() <= 16){
					if(!p.hasPotionEffect(PotionEffectType.SLOW)){
					p.addPotionEffect(new PotionEffect(PotionEffectType.SLOW, 72000, 0));
					p.removePotionEffect(PotionEffectType.SPEED);
					}
				} else {
					p.removePotionEffect(PotionEffectType.SLOW);
					p.addPotionEffect(new PotionEffect(PotionEffectType.SPEED, 72000, 1));
				}
				if(reb == Material.SPONGE){
				      p.setVelocity(p.getLocation().getDirection().multiply(0.8D).setY(0.8D));
				      p.getWorld().playSound(p.getLocation(), Sound.BAT_TAKEOFF, 2.0f, 0.8f);
				}
				if(climbinglist.containsKey(p.getName())){
					e.setCancelled(true);
				}
				if(reb != Material.AIR){
					if(jumpinglist.contains(p.getName()))
					jumpinglist.remove(p.getName());
				}
				Block tb = null;
				try{
					tb = p.getTargetBlock(null, 1);
				}catch(Exception exception){
					return;
				}
				if(tb == null){
					return;
				}
				double dix_x = Math.abs(p.getLocation().getX() - tb.getLocation().getBlockX());
				double dix_z = Math.abs(p.getLocation().getZ() - tb.getLocation().getBlockZ());
				if((tb.getTypeId() == 85 || tb.getTypeId() == 113 || tb.getTypeId() == 139) &&
						(dix_x <= 0.5 || dix_z <= 0.5)){
					p.getWorld().playSound(p.getLocation(), Sound.DIG_SNOW, 3.0f, 0.7f);
					p.setAllowFlight(true);
					p.setFlying(true);
				} else {
					p.setAllowFlight(false);
					p.setFlying(false);
				}
			    if (e.getTo().getBlockY() > e.getFrom().getBlockY()) {
					if(p.isSneaking()){
						if((!jumpinglist.contains(p.getName())
								&& reb != Material.AIR) || p.getLocation().getBlock().getType() == Material.STEP){
							if(Usestamina(p, 10)) return;
							jumpinglist.add(p.getName());
							p.setVelocity(new Vector(0,0.77f,0));
							p.getWorld().playSound(p.getLocation(), Sound.MAGMACUBE_JUMP, 3.0f, 0.8f);
						}
					}
			    }
		}
		
		@EventHandler
		public void onSneak(PlayerToggleSneakEvent e){
			if(!plist.contains(e.getPlayer())) return;
			Player p = e.getPlayer();
			if(p.isSneaking()) return;
			if(p.getGameMode() == GameMode.CREATIVE) return;
			Block tb = null;
			try{
				tb = p.getTargetBlock(null, 1);
			}catch(Exception exception){
				return;
			}
			if(tb == null){
				return;
			}
			 if(climbinglist.containsKey(p.getName())){
					if(Usestamina(p, 25)) return;
					p.playSound(p.getLocation(), Sound.FALL_BIG, 3.0f, 1.6f);
					switch(climbinglist.get(p.getName())){
					case 1: p.setVelocity(new Vector(0,0.35f,0)); p.setVelocity(p.getLocation().getDirection().multiply(0.25D).setY(0.65D)); break;
					case 2: p.setVelocity(new Vector(0,0.50f,0)); p.setVelocity(p.getLocation().getDirection().multiply(0.25D).setY(0.76D));break;
					case 3: p.setVelocity(new Vector(0,0.65f,0)); p.setVelocity(p.getLocation().getDirection().multiply(0.25D).setY(0.87D));break;
					case 4: p.setVelocity(new Vector(0,0.77f,0)); p.setVelocity(p.getLocation().getDirection().multiply(0.25D).setY(0.96D));break;
					case 5: p.setVelocity(new Vector(0,0.77f,0)); p.setVelocity(p.getLocation().getDirection().multiply(0.25D).setY(1.05D));break;
					
					default: p.setVelocity(new Vector(0,0.77f,0)); p.setVelocity(p.getLocation().getDirection().multiply(0.2D).setY(1.05D));break;
					}
					climbinglist.remove(p.getName());	
					return;
			 }
			/*double dix_x = Math.abs(p.getLocation().getX() - tb.getLocation().getBlockX());
			double dix_z = Math.abs(p.getLocation().getZ() - tb.getLocation().getBlockZ());*/
			 if(p.getLocation().getBlock().getRelative(0, -1, 0).getType() == Material.AIR
					 && p.getLocation().getBlock().getType() != Material.STEP
					&& tb.getType() != Material.AIR && !climbinglist.containsKey(p.getName())){
					if(Usestamina(p, 5)) return;
				    p.setVelocity(p.getLocation().getDirection().multiply(-0.65D).setY(0.65D));
					p.getWorld().playSound(p.getLocation(), Sound.DIG_STONE, 3.0f, 0.3f);
					return;
				}
		}
		
		@EventHandler
		public void onPvp(EntityDamageByEntityEvent e){
			if(e.getEntity() instanceof Player && e.getDamager() instanceof Player){
				Player p = (Player) e.getEntity();
				Player damager = (Player) e.getDamager();
				if(blist.contains(p) && blist.contains(damager)){
				      if(!(getItemDamage(damager) == 0)){
				    	  e.setDamage(getItemDamage(damager));
				      }
				}
			}
		}
		
		@EventHandler
		public void onRightClick(PlayerInteractEvent e)
		{
			if(!plist.contains(e.getPlayer())) return;
			Player p = e.getPlayer();
			if((e.getAction() == Action.LEFT_CLICK_AIR || e.getAction() == Action.LEFT_CLICK_BLOCK)){
				if(climbinglist.containsKey(e.getPlayer().getName())){
					climbinglist.remove(e.getPlayer().getName());
					e.getPlayer().sendMessage(MS+"���� �����ϴ�.");
					e.getPlayer().setAllowFlight(false);
					e.getPlayer().setFlying(false);
					return;
				}else if(e.getClickedBlock() != null && p.isSprinting()){
					Block bl = e.getClickedBlock();
					if(bl.getLocation().getY() == (p.getLocation().getY()+1) && (bl.getRelative(0, 1, 0).getType() == Material.AIR 
							|| bl.getRelative(0, 1, 0).getType() == Material.RAILS)){
						if(Usestamina(p, 5)) return;
						p.setVelocity(p.getLocation().getDirection().multiply(0.55D).setY(0.70D));
						p.playSound(p.getLocation(), Sound.LAVA_POP, 2.0f, 0.5f);	
					}
				}
			}
			if(e.getAction() == Action.RIGHT_CLICK_BLOCK || e.getAction() == Action.RIGHT_CLICK_AIR)
			{
				if(e.getClickedBlock() != null && blist.contains(p)){
					if(e.getClickedBlock().getType() == Material.JACK_O_LANTERN){
						InputPassword(p, e.getClickedBlock().getLocation());
						return;
					} else if(e.getClickedBlock().getType() == Material.CHEST){
						e.setCancelled(true);
						  final Location bl = e.getClickedBlock().getLocation();
						  if(!chestlist.contains(bl)){
							  chestlist.add(bl);
								p.getInventory().addItem(coin(Getrandom(1, 3)));
								p.getWorld().playSound(p.getLocation(), Sound.CHEST_OPEN, 2.0f, 1.0f);
								p.updateInventory();
							  Bukkit.getScheduler().scheduleSyncDelayedTask(this, new Runnable(){
								  public void run(){
									  chestlist.remove(bl);
								  }
							  }, 1200L);
						  } else {
							  p.sendMessage("��7�� �����Դϴ�.");
						  }
					}
				}
				if(e.getItem() != null && e.getItem().getType() == Material.COMPASS){
					if(tlist.contains(p)) p.openInventory(gamehelper);
					if(blist.contains(p)) p.openInventory(gamehelper2);
				}
				if(e.getItem() != null && e.getItem().getTypeId() == 370){
					Removeitem(p, 370, 1);
					p.setExp(1); p.sendMessage(MS+"���׹̳��� ȸ�� �Ǿ����ϴ�.");
				}
				if(e.getItem() != null && e.getItem().getType() == Material.WATCH){
					if(blist.contains(p)) p.openInventory(infoinven);
				}
				if(e.getItem() != null && e.getItem().getTypeId() == 371){
					showRank(p);
				}
				if(e.getItem() != null && e.getItem().getTypeId() == 402){
					if(pumpkin_loc.containsKey(p)){
						Location pl = pumpkin_loc.get(p.getName());
						p.sendMessage(MS+"x : "+pl.getBlockX()+", y : "+pl.getBlockY()+", z : "+pl.getBlockZ());
					}
				}
				if(p.getGameMode() == GameMode.CREATIVE) return;
				if(e.getClickedBlock() != null && p.getLocation().getBlock().getRelative(0, -1, 0).getType() == Material.AIR
						&& e.getClickedBlock().getLocation().getBlockY() > p.getLocation().getY() 
						&& (e.getClickedBlock().getRelative(0, 1, 0).getType() == Material.AIR 
						|| e.getClickedBlock().getRelative(0, 1, 0).getType() == Material.THIN_GLASS 
						|| e.getClickedBlock().getRelative(0, 1, 0).getType() == Material.IRON_FENCE
						|| e.getClickedBlock().getRelative(0, 1, 0).getType() == Material.FENCE
						|| e.getClickedBlock().getRelative(0, 1, 0).getType() == Material.NETHER_FENCE
						|| e.getClickedBlock().getRelative(0, 1, 0).getType() == Material.COBBLE_WALL
						|| e.getClickedBlock().getRelative(0, 1, 0).getType() == Material.RAILS)){
					Block bl = e.getClickedBlock();
					double dis_y = Math.abs(bl.getLocation().getY() - p.getLocation().getY());
					if(dis_y <= 4
							&& (Math.abs(bl.getLocation().getBlockX()-p.getLocation().getX()) <= 3
							&& Math.abs(bl.getLocation().getBlockZ()-p.getLocation().getZ()) <= 3)){
						p.setAllowFlight(true);
						p.setFlying(true);
						p.teleport(p.getLocation());
						climbinglist.put(p.getName(), (int) Math.round(dis_y)+1);
						p.sendMessage(MS+"�Ŵ޸��̽��ϴ�. - ��Ŭ�� : ���� , ����Ʈ : ������");
					}
				}
			}
		}
		

		  public static int getItemDamage(Player p){
			  ItemStack item = p.getItemInHand();
			  if(item == null || !item.hasItemMeta() || !item.getItemMeta().hasLore()) return 1;
			  List<String> lorelist = item.getItemMeta().getLore();
			  if(lorelist.size() <= 0) return 1;
			  String damagestr = lorelist.get(0);
			  if(!damagestr.contains("���ݷ�")) return 1;
			  if(!damagestr.contains("~"))return Integer.valueOf(damagestr.substring(7, 8));
			  int min = Integer.valueOf(damagestr.substring(7, 8));
			  int max = Integer.valueOf(damagestr.substring(9, 10));
			  return (int)(Math.random()*(max-min+1)+min);
		  }
		  
		  

		  public static int getPrice(ItemStack item){
			  if(item == null || !item.hasItemMeta() || !item.getItemMeta().hasLore()) return 0;
			  List<String> lorelist = item.getItemMeta().getLore();
			  if(lorelist.size() <= 1) return 0;
			  String pricestr = lorelist.get(1);
			  if(!pricestr.contains("����")) return 0;
			  //"��[0]c[1]��[2]��[3] [4]��[5]f[6]:[7]��[8]6[9] [10]"
			  int price = Integer.valueOf(pricestr.substring(11, 12));
			  if(pricestr.length() > 12) {
				  price *= 10;
				  price += Integer.valueOf(pricestr.substring(12, 13));
			  }
			  return price;
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
					 if(tlist.contains(p)){
						 GameHelperrace(p, e.getSlot());
					 } else {
						 GameHelperbed(p, e.getSlot());
					 }
					 e.setCancelled(true);
				 }
				 if(e.getInventory().getTitle().equalsIgnoreCase("��c��l��й�ȣ")){
					 e.setCancelled(true);
					 if(blist.contains(p)){
						 if(!password.containsKey(p.getName())){
							 PasswordClick(p, e.getSlot());
						 } else {
							 SolvePassword(p, e.getSlot());
						 }
					 }
					 return;
				 }
				 if(e.getInventory().getTitle().equalsIgnoreCase("��c��l����")){
					 e.setCancelled(true);
					 if(blist.contains(p) && e.getCurrentItem() != null){
						 Shopping(p, e.getCurrentItem());
					 }
				 }
				 if(e.getInventory().getTitle().equalsIgnoreCase("��c��l����")) e.setCancelled(true);
			}
		}
		
		public void Shopping(Player p, ItemStack item) {
			if(item.getTypeId() == 266){
				if(Takeitem(p, 388, 10)){
					p.getWorld().playSound(p.getLocation(), Sound.ITEM_PICKUP, 2.0f, 0.7f);
					p.getInventory().addItem(coin(Getrandom(1, 19)));
					p.updateInventory();
				}else {
					p.getWorld().playSound(p.getLocation(), Sound.ITEM_BREAK, 2.0f, 2.0f);
					p.sendMessage(MS+"������ �����մϴ�.");
				}
				return;
			}
			if(item.getTypeId() == 339){
				if(Takeitem(p, 388, 100)){
					p.getWorld().playSound(p.getLocation(), Sound.ITEM_PICKUP, 2.0f, 0.7f);
					for(Player t : blist){
						if(!t.getName().equalsIgnoreCase(p.getName())){
							deathcount.put(t.getName(), deathcount.get(t.getName())+1);
						}
					}
					p.sendMessage(MS+"�ڽ��� ������ ����� ������ �Ѵܰ� �� ���Դϴ�.");
					UpdateInfo();
				}else {
					p.getWorld().playSound(p.getLocation(), Sound.ITEM_BREAK, 2.0f, 2.0f);
					p.sendMessage(MS+"������ �����մϴ�.");
				}
			}
			int price = getPrice(item);
			if(Takeitem(p, 388, price)){
				p.getWorld().playSound(p.getLocation(), Sound.ITEM_PICKUP, 2.0f, 0.7f);
				p.getInventory().addItem(item);
			} else {
				p.getWorld().playSound(p.getLocation(), Sound.ITEM_BREAK, 2.0f, 2.0f);
				p.sendMessage(MS+"������ �����մϴ�.");
			}
		}

		@EventHandler
		public void onPlayerDamage(EntityDamageEvent e){
			if(!(e.getEntity() instanceof Player)) return;
			final Player p = (Player) e.getEntity();
			if(!plist.contains(p)) return;
			if ((e.getCause() == EntityDamageEvent.DamageCause.FALL)){
				e.setCancelled(true);
				if(!p.isSneaking() || e.getDamage() >= 40){
					if(e.getDamage() <= 19 || p.getLocation().getBlock().getRelative(0, 1, 0).getType() == Material.SPONGE) return;
							p.damage(1);
							p.getWorld().playSound(p.getLocation(), Sound.ITEM_BREAK, 2.0f, 2.0f);
							p.sendMessage(MS+"�ٸ��� ���ƽ��ϴ�!");
							p.addPotionEffect(new PotionEffect(PotionEffectType.JUMP, 60, 199));
				} else{
							p.getWorld().playSound(p.getLocation(), Sound.BAT_TAKEOFF, 2.0f, 3.0f);
				}
				return;
				} else {
					if(blist.contains(p) && pvp){
						return;
					}
					e.setCancelled(true);
				}
		}
		
		@EventHandler
		public void onItemDrop(PlayerDropItemEvent e){
			if(!plist.contains(e.getPlayer())) return;
			if(e.getItemDrop().getItemStack().getType() == Material.COMPASS || e.getItemDrop().getItemStack().getType() == Material.WATCH) e.setCancelled(true);
		}
		
		@EventHandler
		public void onPlayercommand(PlayerCommandPreprocessEvent e)
		{
			Player p = e.getPlayer();
			if(plist.contains(p) && e.getMessage().equalsIgnoreCase("/����") || e.getMessage().equalsIgnoreCase("/spawn")
					|| e.getMessage().equalsIgnoreCase("/����") || e.getMessage().equalsIgnoreCase("/����"))
			{
				plist.remove(p);
			    if (tlist.contains(p))
			    {
			      GameQuitrace(p);
			    } else if(blist.contains(p)){
			    	GameQuitbed(p);
			    }
			}
			if(plist.contains(p) && e.getMessage().equalsIgnoreCase("/������")){
				p.sendMessage(MS+"�� ���ӿ����� ����Ͻ� �� �����ϴ�.");
				e.setCancelled(true);
			}
		}
		
		@EventHandler
		public void onBlockbreak(BlockBreakEvent e){
			if(plist.contains(e.getPlayer())){
				Player p = e.getPlayer();
					e.setCancelled(true);
			}

		}
		  
		@EventHandler
		public void onBlockPlace(BlockPlaceEvent e){
			  if(plist.contains(e.getPlayer())){
				  if(!blist.contains(e.getPlayer()) && !e.getPlayer().isOp()){
					  e.setCancelled(true);
					  return;
				  } else {
					  Player p = e.getPlayer();
					  if(e.getBlock().getType() == Material.JACK_O_LANTERN){
						  pumpkin_loc.put(p.getName(), e.getBlock().getLocation());
						  p.sendMessage(MS+"�̰��� ������ �����߽��ϴ�.");
						  p.sendMessage(MS+"��й�ȣ�� �Է����ּ���.");
						  insertingpassword.remove(p.getName());
						  p.openInventory(passwordinven);
					  }
				  }
			  }
		}
		
		  @EventHandler
		  public void onQuitPlayer(PlayerQuitEvent e) {
			  if(!plist.contains(e.getPlayer())) return;
			  plist.remove(e.getPlayer());
		    if (tlist.contains(e.getPlayer()))
		    {
		      GameQuitrace(e.getPlayer());
		    } else if(blist.contains(e.getPlayer())){
		    	GameQuitbed(e.getPlayer());
		    }
		  }
		  
		  @EventHandler
		  public void onRespawn(PlayerRespawnEvent e){
			  if(!plist.contains(e.getPlayer())) return;
			  final Player p = e.getPlayer();
			  if(climbinglist.containsKey(p.getName())){
				  climbinglist.remove(p.getName());
				  p.setFlying(false);
				  p.setAllowFlight(false);
			  }
			  if(blist.contains(e.getPlayer())){
				  if(!pumpkin_loc.containsKey(p.getName())){
					  p.sendMessage(MS+"ȣ���� ��ġ���� �ʰ� �׾� Ż���ϼ̽��ϴ�.");
					  GameQuitbed(p);
				  }else{
					  e.setRespawnLocation(pumpkin_loc.get(p.getName()));
					  if(deathinven.containsKey(p.getName())){
						  Bukkit.getScheduler().scheduleSyncDelayedTask(this, new Runnable(){
							  public void run(){
								  p.getInventory().setContents(deathinven.get(p.getName()));
								  deathinven.remove(p.getName());
							  }
						  }, 20l);						 
					  }
				  }
			  }
		  }
		  
			@EventHandler
			public void onPlayerdeath(PlayerDeathEvent e)
			{
				Player p = e.getEntity();
				  if(!plist.contains(p)) return;
				if(tlist.contains(p))
				{
					plist.remove(p);
					GameQuitrace(p);
				} else if(blist.contains(p)){
					if(password.containsKey(p.getName())){
						deathinven.put(p.getName(), p.getInventory().getContents());
						e.getDrops().clear();
						p.setFlying(false);
						p.setAllowFlight(false);
						if(p.getKiller() != null){
							Player k = p.getKiller();
							k.getInventory().addItem(coin(10));
							p.updateInventory();
							k.sendMessage(MS+"ų �������� ���޶��� 10���� �޾ҽ��ϴ�.");
						}
						if(deathcount.get(p.getName()) <= 5){
							deathcount.put(p.getName(), deathcount.get(p.getName())+1);
							UpdateInfo();
						} 
						if(climbinglist.containsKey(p.getName())) climbinglist.remove(p.getName());
					}
				}
			}
}
