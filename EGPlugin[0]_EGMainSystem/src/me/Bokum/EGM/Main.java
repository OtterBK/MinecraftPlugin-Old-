package me.Bokum.EGM;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.OfflinePlayer;
import org.bukkit.Sound;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.entity.FoodLevelChangeEvent;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.player.PlayerChatEvent;
import org.bukkit.event.player.PlayerCommandPreprocessEvent;
import org.bukkit.event.player.PlayerInteractEntityEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerKickEvent;
import org.bukkit.event.player.PlayerLoginEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.help.HelpTopic;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.InventoryHolder;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.plugin.RegisteredServiceProvider;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.potion.PotionEffect;

import net.milkbowl.vault.economy.Economy;
import net.milkbowl.vault.economy.EconomyResponse;

public class Main extends JavaPlugin implements Listener
{
	public static String MS = "��f[ ��aEG ��f] ";
	public static HashMap<String, String> gamelist = new HashMap<String, String>(250);
	public static Location Loc_Lobby;
	public static Inventory Kit_Lobby;
	public static List<Player> aclist = new ArrayList<Player>();
	public static Inventory Minigame;
	public static Inventory Serverhelp;
	public static Inventory prefixshop;
	public static Inventory mobarenainven;
	public static Inventory parkourinven;
	public static Inventory serverrule;
	public static Inventory baseQuest;
	public static Inventory patchNote;
	public static Economy econ = null;
	public static int daycnt = 0;
	public static int broadcastcnt = 0;
	public static Main instance;
	public static HashMap<String, Integer> cooldown = new HashMap<String, Integer>(300);
	public static HashMap<String, Integer> muteList = new HashMap<String, Integer>();
	public static List<String> buildlist = new ArrayList<String>(80);
	public static List<String> pvplist = new ArrayList<String>(80);
	public static List<String> noAllVoiceList = new ArrayList<String>(300);
	public static List<String> solochat = new ArrayList<String>(300);
	
	public void onEnable(){
		getServer().getPluginManager().registerEvents(this, this);
		LoadInventory();
		LoadConfig();
		UpdateScheduler();
		instance = this;
	    getServer().setWhitelist(true);
		getLogger().info("EG���� ���� �ý����� �ε� �Ǿ����ϴ�.");
		
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
	
	public void LoadConfig(){
		Loc_Lobby = new Location(getServer().getWorld(getConfig().getString("Lobby_world")), getConfig().getInt("Lobby_x"),
				getConfig().getInt("Lobby_y"),getConfig().getInt("Lobby_z"));	
		Loc_Lobby.setPitch((float) getConfig().getDouble("Lobby_pitch"));
		Loc_Lobby.setYaw((float) getConfig().getDouble("Lobby_yaw"));
		solochat.addAll(getConfig().getStringList("solochatlist"));
	}
	
	private void SetInventory(){
		Kit_Lobby = Bukkit.createInventory(null, 36, "����Ŷ");
		
		ItemStack item = new ItemStack(Material.NETHER_STAR, 1);
		ItemMeta meta = item.getItemMeta();
		meta.setDisplayName("��f[ ��b�̴ϰ��� ��f]");
		List<String> lorelist = new ArrayList<String>();
		lorelist.add("��f- ��7�̴ϰ����� �÷��� �Ͻ÷���");
		lorelist.add("��f- ��7��Ŭ�����ּ���.");
		meta.setLore(lorelist);
		item.setItemMeta(meta);
		Kit_Lobby.setItem(0, item);
		
		item = new ItemStack(340, 1);
		meta = item.getItemMeta();
		lorelist.clear();
		meta.setDisplayName("��f[ ��b���� ���� ��f]");
		lorelist.add("��f- ��7������ ���� ����, ��Ģ �����");
		lorelist.add("��f- ��7���÷��� ��Ŭ�����ּ���.");
		meta.setLore(lorelist);
		item.setItemMeta(meta);
		Kit_Lobby.setItem(1, item);
		
		item = new ItemStack(339, 1);
		meta = item.getItemMeta();
		lorelist.clear();
		meta.setDisplayName("��f[ ��b��ġ��Ʈ ��f]");
		lorelist.add("��f- ��7���� ��ġ������ ���ϴ�.");
		meta.setLore(lorelist);
		item.setItemMeta(meta);
		Kit_Lobby.setItem(2, item);
		
		Minigame = Bukkit.createInventory(null, 54, "��c��l�̴ϰ���");
		
		item = new ItemStack(102, 1);
		meta = item.getItemMeta();
		meta.setDisplayName("��6���");
		item.setItemMeta(meta);
		Minigame.setItem(0, item);
		Minigame.setItem(9, item);
		Minigame.setItem(18, item);
		Minigame.setItem(27, item);
		Minigame.setItem(36, item);
		Minigame.setItem(35, item);
		Minigame.setItem(44, item);
		Minigame.setItem(53, item);
		Minigame.setItem(45, item);
		Minigame.setItem(8, item);
		Minigame.setItem(17, item);
		Minigame.setItem(26, item);
		
		item = new ItemStack(351, 1, (byte)1);
		meta = item.getItemMeta();
		lorelist.clear();
		meta.setDisplayName("��f[ ��c���θ��� ã�ƶ�4 ��f]");
		lorelist.add("��f- ��e�ο� : ��6"+me.Bokum.FindTheMurder.Main.list.size()+"�� / 13��");
		lorelist.add("��f- ��e�ּ��ο� : 5��");
		lorelist.add("��f- ��b���� �Ͻ÷��� Ŭ�����ּ���.");
		meta.setLore(lorelist);
		item.setItemMeta(meta);
		Minigame.setItem(10, item);
		
		item = new ItemStack(Material.EMERALD, 1);
		meta = item.getItemMeta();
		lorelist.clear();
		meta.setDisplayName("��f[ ��a�� ���� ��Ʋ��f]");
		lorelist.add("��f- ��e�ο� : ��6"+me.Bokum.TRB.Main.plist.size()+"�� / 12��");
		lorelist.add("��f- ��e�ּ��ο� : 6��");
		lorelist.add("��f- ��b���� �Ͻ÷��� Ŭ�����ּ���.");
		meta.setLore(lorelist);
		item.setItemMeta(meta);
		Minigame.setItem(12, item);
		
		item = new ItemStack(Material.DIAMOND_BLOCK, 1);
		meta = item.getItemMeta();
		lorelist.clear();
		meta.setDisplayName("��f[ ��6�ŵ��� �����f]");
		lorelist.add("��f- ��e�ο� : ��6"+me.Bokum.FindTheMurder.Main.list.size()+"�� / 12��");
		lorelist.add("��f- ��e�ּ��ο� : 6��");
		lorelist.add("��f- ��b���� �Ͻ÷��� Ŭ�����ּ���.");
		meta.setLore(lorelist);
		item.setItemMeta(meta);
		Minigame.setItem(14, item);
		
		item = new ItemStack(Material.WOOD, 1);
		meta = item.getItemMeta();
		lorelist.clear();
		meta.setDisplayName("��f[ ��2CTF ��f]");
		lorelist.add("��f- ��e�ο� : ��6"+me.Bokum.DTC.Main.plist.size()+"�� / 14��");
		lorelist.add("��f- ��e�ּ��ο� : 6��");
		lorelist.add("��f- ��4�ش� ������ �ߵ������� �����մϴ�.");
		lorelist.add("��f- ��b���� �Ͻ÷��� Ŭ�����ּ���.");
		meta.setLore(lorelist);
		item.setItemMeta(meta);
		Minigame.setItem(16, item);
		
		item = new ItemStack(Material.STICK, 1);
		meta = item.getItemMeta();
		lorelist.clear();
		meta.setDisplayName("��f[ ��d�������� ��f]");
		lorelist.add("��f- ��e�ο� : ��6"+me.Bokum.DTC.Main.plist.size()+"�� / 9��");
		lorelist.add("��f- ��e�ּ��ο� : 3��");
		lorelist.add("��f- ��b���� �Ͻ÷��� Ŭ�����ּ���.");
		meta.setLore(lorelist);
		item.setItemMeta(meta);
		Minigame.setItem(19, item);
		
		item = new ItemStack(Material.DIAMOND, 1);
		meta = item.getItemMeta();
		lorelist.clear();
		meta.setDisplayName("��f[ ��b�������� ��f]");
		lorelist.add("��f- ��e�ο� : ��6"+me.Bokum.DTC.Main.plist.size()+"�� / 30��");
		lorelist.add("��f- ��e�ּ��ο� : 20��");
		lorelist.add("��f- ��b���� �Ͻ÷��� Ŭ�����ּ���.");
		meta.setLore(lorelist);
		item.setItemMeta(meta);
		Minigame.setItem(25, item);
		
		item = new ItemStack(146, 1);
		meta = item.getItemMeta();
		lorelist.clear();
		meta.setDisplayName("��f[ ��c��Ű��� ��f]");
		lorelist.add("��f- ��4�ش� ������ ��Ȳ ������ ���� �ʽ��ϴ�.");
		lorelist.add("��f- ��bä���� �����Ͻ÷��� Ŭ�����ּ���.");
		meta.setLore(lorelist);
		item.setItemMeta(meta);
		Minigame.setItem(23, item);

		item = new ItemStack(Material.WOOL, 1 , (byte)14);
		meta = item.getItemMeta();
		lorelist.clear();
		meta.setDisplayName("��f[ ��4Ÿ�� �ϻ� ��f]");
		lorelist.add("��f- ��e�ο� : ��6"+me.Bokum.DTC.Main.plist.size()+"�� / 9��");
		lorelist.add("��f- ��e�ּ��ο� : 4��");
		lorelist.add("��f- ��b���� �Ͻ÷��� Ŭ�����ּ���.");
		meta.setLore(lorelist);
		item.setItemMeta(meta);
		Minigame.setItem(21, item);
		
		item = new ItemStack(Material.DIAMOND_SPADE, 1);
		meta = item.getItemMeta();
		lorelist.clear();
		meta.setDisplayName("��f[ ��b���ø��� ��f]");
		lorelist.add("��f- ��e�ο� : ��6"+me.Bokum.DTC.Main.plist.size()+"�� / 7��");
		lorelist.add("��f- ��e�ּ��ο� : 3��");
		lorelist.add("��f- ��b���� �Ͻ÷��� Ŭ�����ּ���.");
		meta.setLore(lorelist);
		item.setItemMeta(meta);
		Minigame.setItem(30, item);
		
		item = new ItemStack(Material.FENCE, 1);
		meta = item.getItemMeta();
		lorelist.clear();
		meta.setDisplayName("��f[ ��2���� ���� ��f]");
		lorelist.add("��f- ��e������ ������ ��������!");
		lorelist.add("��f- ��b������ �����Ͻ÷��� Ŭ�����ּ���.");
		meta.setLore(lorelist);
		item.setItemMeta(meta);
		Minigame.setItem(32, item);
		
		item = new ItemStack(Material.WOOL, 1, (byte) 5);
		meta = item.getItemMeta();
		lorelist.clear();
		meta.setDisplayName("��f[ ��a�÷���ġ ��f]");
		lorelist.add("��f- ��e�ο� : ��6"+me.Bokum.DTC.Main.plist.size()+"�� / 6��");
		lorelist.add("��f- ��e�ּ��ο� : 2��");
		lorelist.add("��f- ��b���� �Ͻ÷��� Ŭ�����ּ���.");
		meta.setLore(lorelist);
		item.setItemMeta(meta);
		Minigame.setItem(34, item);
		
		item = new ItemStack(Material.REDSTONE_TORCH_OFF, 1);
		meta = item.getItemMeta();
		lorelist.clear();
		meta.setDisplayName("��f[ ��eĳġ���ε� ��f]");
		lorelist.add("��f- ��e�ο� : ��6"+me.Bokum.DTC.Main.plist.size()+"�� / 6��");
		lorelist.add("��f- ��e�ּ��ο� : 4��");
		lorelist.add("��f- ��b���� �Ͻ÷��� Ŭ�����ּ���.");
		meta.setLore(lorelist);
		item.setItemMeta(meta);
		Minigame.setItem(28, item);
		
		item = new ItemStack(Material.WOOL, 1);
		meta = item.getItemMeta();
		lorelist.clear();
		meta.setDisplayName("��f[ ��e�±ر� ������� ��f]");
		lorelist.add("��f- ��e�ο� : ��6"+me.Bokum.DTC.Main.plist.size()+"�� / 8��");
		lorelist.add("��f- ��e�ּ��ο� : 4��");
		lorelist.add("��f- ��b���� �Ͻ÷��� Ŭ�����ּ���.");
		meta.setLore(lorelist);
		item.setItemMeta(meta);
		Minigame.setItem(37, item);
		
		item = new ItemStack(Material.BOOKSHELF, 1);
		meta = item.getItemMeta();
		lorelist.clear();
		meta.setDisplayName("��f[ ��a�� ���ٲ��� ��f]");
		lorelist.add("��f- ��e�ο� : ��6"+me.Bokum.BlockHunter.Main.plist.size()+"�� / 12��");
		lorelist.add("��f- ��e�ּ��ο� : 5��");
		lorelist.add("��f- ��b���� �Ͻ÷��� Ŭ�����ּ���.");
		meta.setLore(lorelist);
		item.setItemMeta(meta);
		Minigame.setItem(41, item);
		
		item = new ItemStack(Material.ANVIL, 1);
		meta = item.getItemMeta();
		lorelist.clear();
		meta.setDisplayName("��f[ ��e��� ���ϱ� ��f]");
		lorelist.add("��f- ��e�ο� : ��6"+me.Bokum.BlockHunter.Main.plist.size()+"�� / 7��");
		lorelist.add("��f- ��e�ּ��ο� : 2��");
		lorelist.add("��f- ��b���� �Ͻ÷��� Ŭ�����ּ���.");
		meta.setLore(lorelist);
		item.setItemMeta(meta);
		Minigame.setItem(43, item);
		
		item = new ItemStack(Material.FIREBALL, 1);
		meta = item.getItemMeta();
		lorelist.clear();
		meta.setDisplayName("��f[ ��c������ ��f]");
		lorelist.add("��f- ��e�ο� : ��6"+me.Bokum.BlockHunter.Main.plist.size()+"�� / 8��");
		lorelist.add("��f- ��e�ּ��ο� : 2��");
		lorelist.add("��f- ��b���� �Ͻ÷��� Ŭ�����ּ���.");
		meta.setLore(lorelist);
		item.setItemMeta(meta);
		Minigame.setItem(46, item);
		
		item = new ItemStack(Material.MINECART, 1);
		meta = item.getItemMeta();
		lorelist.clear();
		meta.setDisplayName("��f[ ��b�ڸ����� ��f]");
		lorelist.add("��f- ��e�ο� : ��6"+me.Bokum.BlockHunter.Main.plist.size()+"�� / 8��");
		lorelist.add("��f- ��e�ּ��ο� : 4��");
		lorelist.add("��f- ��b���� �Ͻ÷��� Ŭ�����ּ���.");
		meta.setLore(lorelist);
		item.setItemMeta(meta);
		Minigame.setItem(48, item);
		
		serverrule = Bukkit.createInventory(null, 27, "��c��l������Ģ");
		
		ItemStack deco = new ItemStack(102, 1);
		ItemMeta decometa = deco.getItemMeta();
		decometa.setDisplayName("��6���");
		deco.setItemMeta(decometa);
		for(int i = 0; i <= 9; i++){
			serverrule.setItem(i, deco);
		}
		for(int i = 17; i < 27; i++){
			serverrule.setItem(i, deco);
		}
		
		item = new ItemStack(Material.BOOK, 1);
		meta = item.getItemMeta();
		lorelist.clear();
		meta.setDisplayName("��f[ ��e������Ģ ��f]");
		lorelist.add("��f- ��6EG������ ��e�̴ϰ��ӡ�f�����Դϴ�.");
		lorelist.add("��f- ��6�Ʒ��� ��Ģ�鸸 �ؼ��Ͻø� ��̰� ������ �Ͻ� �� �ֽ��ϴ�.");
		lorelist.add("��f- ��e1. ��2ä�õ����7�� ����������.");
		lorelist.add("��f- ��e2. ��7Ÿ���� �߾��� �����ϵ� ����ģ Ÿ���� ȫ���� ����������.");
		lorelist.add("��f- ��e3. ��7Ư�� BJ�� ����� ���������ּ���.");
		lorelist.add("��f- ��e4. ��7���� �߰�, �����߻��ÿ��� ��c'/����'��7��ɾ ������ּ���.");
		lorelist.add("��f- ��e5. ��7�Ű�� ��cī���7�� ��Ź�帳�ϴ�.");
		meta.setLore(lorelist);
		item.setItemMeta(meta);
		serverrule.setItem(10, item);
		
		item = new ItemStack(Material.BOOK, 1);
		meta = item.getItemMeta();
		lorelist.clear();
		meta.setDisplayName("��f[ ��e������Ģ ��f]");
		lorelist.add("��f- ��e6. ��b����Ʈ����, �������7���� ���� �������������.");
		lorelist.add("��f- ��e7. ��c�弳 �� ���帳��7���� �߾��� ����������.");
		lorelist.add("��f- ��e8. ��d�г��ӽ�Ų��7�� ����� �����Դϴ�.");
		lorelist.add("��f- ��e9. ��c��l���Ӹ��� �־����� ���ӵ���̸� �� �а�");
		lorelist.add("��f- ��e9-1. ��c��l�� ������ ��Ģ�� �ؼ��ϼ���.");
		meta.setLore(lorelist);
		item.setItemMeta(meta);
		serverrule.setItem(11, item);
		
		item = new ItemStack(Material.BOOK, 1);
		meta = item.getItemMeta();
		lorelist.clear();
		meta.setDisplayName("��f[ ��eī���ּ� ��f]");
		lorelist.add("��f- ��7Ŭ���� ��ũ�� �޽��ϴ�.");
		meta.setLore(lorelist);
		item.setItemMeta(meta);
		serverrule.setItem(12, item);
		
		item = new ItemStack(Material.BOOK, 1);
		meta = item.getItemMeta();
		lorelist.clear();
		meta.setDisplayName("��f[ ��eOP��� ��f]");
		lorelist.add("��f- ��eLimes_ ��7: �Ѱ����� ");
		lorelist.add("��f- ��eTomaRovini_ ��7: ���డ ");
		lorelist.add("��f- ��eBokum ��7: �÷����� ����");
		lorelist.add("��f- ��eSeol_Yee ��7: ���� ������ ");
		lorelist.add("��f- ��eingwan ��7: ���� ������ ");
		lorelist.add("��f- ��eYoun_w ��7: ���� ������");
		lorelist.add("��f- ��eU_Uv ��7: ���� ������");
		lorelist.add("��f- ��eniceyonom02 ��7: ���� ������ ");
		lorelist.add("��f- ��easzs423 ��7: ���� ������ ");		
		lorelist.add("��f- ��eSunny_p ��7: ���� ������ ");
		lorelist.add("��f- ��eG_Hatem ��7: ���� ������ ");
		meta.setLore(lorelist);
		item.setItemMeta(meta);
		serverrule.setItem(13, item);
		
		
		Serverhelp = Bukkit.createInventory(null, 27, "��c��l���� ����");
		
		for(int i = 0; i <= 9; i++){
			Serverhelp.setItem(i, deco);
		}
		for(int i = 17; i < 27; i++){
			Serverhelp.setItem(i, deco);
		}
		
		item = new ItemStack(Material.BOOK, 1);
		meta = item.getItemMeta();
		meta.setDisplayName("��e���� ��Ģ �� ������");
		item.setItemMeta(meta);
		Serverhelp.setItem(11, item);
		
		item = new ItemStack(Material.ENCHANTED_BOOK, 1);
		meta = item.getItemMeta();
		meta.setDisplayName("��e����");
		item.setItemMeta(meta);
		Serverhelp.setItem(15, item);
		
		prefixshop = Bukkit.createInventory(null, 54, "��c��lĪȣ ����");
		
		for(int i = 0; i < 54; i++){
			switch(i+9){
			case 10: 	meta.setDisplayName("��f[��2���Ρ�f]"); break;
			case 11: 	meta.setDisplayName("��f[��3�׷Ρ�f]"); break;
			case 12: 	meta.setDisplayName("��f[��5������f]"); break;
			case 14: 	meta.setDisplayName("��f[��7������f]"); break;
			case 15: 	meta.setDisplayName("��f[��8�����f]"); break;
			case 16: 	meta.setDisplayName("��f[��9���ӡ�f]"); break;
			case 13: 	meta.setDisplayName("��f[��d��ǳ��f]"); break;
			case 19: 	meta.setDisplayName("��f[��3���ӡ�f]"); break;
			case 20: 	meta.setDisplayName("��f[��9�ð��f]"); break;
			case 21:	meta.setDisplayName("��f[��d�ӵ���f]"); break;
			case 22: 	meta.setDisplayName("��f[��7�屸��f]"); break;
			case 23: 	meta.setDisplayName("��f[��6�Ŀ���f]"); break;
			case 24: 	meta.setDisplayName("��f[��2���ߡ�f]"); break;
			case 25: 	meta.setDisplayName("��f[��3������f]"); break;
			case 26: 	meta.setDisplayName("��f[��3�����f]"); break;
			case 28: 	meta.setDisplayName("��f[��5������f]"); break;
			case 29: 	meta.setDisplayName("��f[��e��̡�f]"); break;
			case 30: 	meta.setDisplayName("��f[��a��ҡ�f]"); break;
			case 31: 	meta.setDisplayName("��f[��3�ξ��f]"); break;
			case 32: 	meta.setDisplayName("��f[��2ū�ɡ�f]"); break;
			case 33: 	meta.setDisplayName("��f[��9��ǳ��f]"); break;
			case 34: 	meta.setDisplayName("��f[��8���á�f]"); break;
			case 35: 	meta.setDisplayName("��f[��e���¡�f]"); break;
			case 37: 	meta.setDisplayName("��f[��7���¡�f]"); break;
			case 38: 	meta.setDisplayName("��f[��6�̵��f]"); break;
			case 39: 	meta.setDisplayName("��f[��5������f]"); break;
			case 40: 	meta.setDisplayName("��f[��e������f]"); break;
			case 41: 	meta.setDisplayName("��f[��d�̽���f]"); break;
			case 42: 	meta.setDisplayName("��f[��c�����f]"); break;
			case 43: 	meta.setDisplayName("��f[��b������f]"); break;
			case 46: 	meta.setDisplayName("��f[��a�ָ��f]"); break;
			case 47: 	meta.setDisplayName("��f[��2�ʷա�f]"); break;
			case 48:	meta.setDisplayName("��f[��5�Ѻ���f]"); break;
			case 49: 	meta.setDisplayName("��f[��a������f]"); break;
			case 50: 	meta.setDisplayName("��f[��b�̸���f]"); break;
			case 51: 	meta.setDisplayName("��f[��8����f]"); break;
			case 52: 	meta.setDisplayName("��f[��e������f]"); break;
			case 53: 	meta.setDisplayName("��f[��3��¡�f]"); break;
			case 55: 	meta.setDisplayName("��f[��3�����f]"); break;
			case 56: 	meta.setDisplayName("��f[��d���ȡ�f]"); break;
			case 57: 	meta.setDisplayName("��f[��a������f]"); break;
			case 58: 	meta.setDisplayName("��f[��3�ΰ���f]"); break;
			case 59: 	meta.setDisplayName("��f[��5���ġ�f]"); break;
			case 60: 	meta.setDisplayName("��f[��6�̰ס�f]"); break;
			case 61: 	meta.setDisplayName("��f[��7�ַΡ�f]"); break;
			case 62: 	meta.setDisplayName("��f[��e�����f]"); break;
			
			default: break;
			}
			item.setItemMeta(meta);
			prefixshop.setItem(i, item);				
		}
		
		baseQuest = Bukkit.createInventory(null, 54, "��c��l��������");
		
		item = new ItemStack(Material.BOOK, 1);
		meta = item.getItemMeta();
		meta.setDisplayName("��e��������");
		item.setItemMeta(meta);
		
		for(int i1 = 0; i1 < 40; i1++){
			switch(i1){
			case 0: meta.setDisplayName("��f[��6 ��Ÿ�� ��f]"); break;
			case 1: meta.setDisplayName("��f[��6 �������� ���ĳ��� ����! ��f]"); break;
			case 2: meta.setDisplayName("��f[��6 ����� ��f]"); break;
			case 3: meta.setDisplayName("��f[��6 ������ ���� ��f]"); break;
			case 4: meta.setDisplayName("��f[��6 ������ �� �� ��f]"); break;
			case 5: meta.setDisplayName("��f[��6 ���� ��f]"); break;
			case 6: meta.setDisplayName("��f[��6 ������ ���� ��f]"); break;
			case 7: meta.setDisplayName("��f[��6 ���� ��f]"); break;
			case 8: meta.setDisplayName("��f[��6 ��� ������ ��f]"); break;
			case 9: meta.setDisplayName("��f[��6 �θ��� ���� ��f]"); break;
			case 10: meta.setDisplayName("��f[��6 �ź� ���� ��f]"); break;
			case 11: meta.setDisplayName("��f[��6 �������� �һ� ��f]"); break;
			case 12: meta.setDisplayName("��f[��6 Ȱ���� ���� ��f]"); break;
			case 13: meta.setDisplayName("��f[��6 ������ ������... ��f]"); break;
			case 14: meta.setDisplayName("��f[��6 ����Ʈ ���� ��f]"); break;
			case 15: meta.setDisplayName("��f[��6 ������ ���� ��f]"); break;
			case 16: meta.setDisplayName("��f[��6 �ϴ��� ���� ��f]"); break;
			case 17: meta.setDisplayName("��f[��6 �� �⿩�� 100% ��f]"); break;
			case 18: meta.setDisplayName("��f[��6 ������ ���� ������ �ʴ� ��f]"); break;
			case 19: meta.setDisplayName("��f[��6 ���� ������ �ʾ�! ��f]"); break;
			case 20: meta.setDisplayName("��f[��6 �纸�սô� ��f]"); break;
			case 21: meta.setDisplayName("��f[��6 Ÿ���� ������� ��f]"); break;
			case 22: meta.setDisplayName("��f[��6 ��δ� ���� ��f]"); break;
			case 23: meta.setDisplayName("��f[��6 ȭ�� ��f]"); break;
			case 24: meta.setDisplayName("��f[��6 ���̿͵� �Ƚ�! ��f]"); break;
			case 25: meta.setDisplayName("��f[��6 ���� ��f]"); break;
			case 26: meta.setDisplayName("��f[��6 �ټ��� ��f]"); break;
			case 27: meta.setDisplayName("��f[��6 ������? ��f]"); break;
			case 28: meta.setDisplayName("��f[��6 �Ӵ��� ��f]"); break;
			case 29: meta.setDisplayName("��f[��6 ų���� ��f]"); break;
			case 30: meta.setDisplayName("��f[��6 ������ �ٷ� ����̾�! ��f]"); break;
			case 31: meta.setDisplayName("��f[��6 ������ �η��� ��f]"); break;
			case 32: meta.setDisplayName("��f[��6 ���ɼ��� ��f]"); break;
			case 33: meta.setDisplayName("��f[��6 ���߷� ��� ��f]"); break;
			case 34: meta.setDisplayName("��f[��6 ���¾� �౸�� ��f]"); break;
			case 35: meta.setDisplayName("��f[��6 �׷����� �� ��f]"); break;
			case 36: meta.setDisplayName("��f[��6 �������� ��f]"); break;
			case 37: meta.setDisplayName("��f[��6 ������ ������ ��f]"); break;
			case 38: meta.setDisplayName("��f[��6 �����غ� ��f]"); break;
			case 39: meta.setDisplayName("��f[��6 �������� ��f]"); break;
			default: break;
			}
			item.setItemMeta(meta);
			baseQuest.setItem(i1, item);	
		}
		
		for(int i = 0; i <= 53; i += 9){
			prefixshop.setItem(i, deco);
		}
		for(int i = 8; i <= 53; i += 9){
			prefixshop.setItem(i, deco);
		}
		
		parkourinven = Bukkit.createInventory(null, 27, "��c��l����");
		
		for(int i = 0; i <= 9; i++){
			parkourinven.setItem(i, deco);
		}
		for(int i = 17; i < 27; i++){
			parkourinven.setItem(i, deco);
		}
		
		item = new ItemStack(Material.PISTON_STICKY_BASE, 1);
		meta = item.getItemMeta();
		meta.setDisplayName("��e�Ʒ���");
		item.setItemMeta(meta);
		parkourinven.setItem(10, item);
		
		item = new ItemStack(Material.PISTON_BASE, 1);
		meta = item.getItemMeta();
		meta.setDisplayName("��e���� �Ʒ���");
		item.setItemMeta(meta);
		parkourinven.setItem(12, item);
		
		item = new ItemStack(Material.BEACON, 1);
		meta = item.getItemMeta();
		meta.setDisplayName("��e���� ���̽�");
		item.setItemMeta(meta);
		parkourinven.setItem(14, item);
		
		item = new ItemStack(Material.JACK_O_LANTERN, 1);
		meta = item.getItemMeta();
		meta.setDisplayName("��e���� ȣ������");
		item.setItemMeta(meta);
		parkourinven.setItem(16, item);
		
		mobarenainven = Bukkit.createInventory(null, 27, "��c��l�� �Ʒ���");
		
		for(int i = 0; i <= 9; i++){
			mobarenainven.setItem(i, deco);
		}
		for(int i = 17; i < 27; i++){
			mobarenainven.setItem(i, deco);
		}
		
		patchNote = Bukkit.createInventory(null, 9, "��c��l��ġ��Ʈ");
		
		item = new ItemStack(Material.BOOK, 1);
		meta = item.getItemMeta();
		lorelist.clear();
		meta.setDisplayName("��f[ ��e��ġ��Ʈ 17/2/21 (1)��f ]");
		lorelist.add("��f- ��6���θ��� ã�ƶ�4");
		lorelist.add("��7�� �ϻ��� ���� ����");
		lorelist.add("��7�� ����� ���� �߰�");
		lorelist.add("��7�� �� ����");
		lorelist.add("��f");
		lorelist.add("��f- ��6��������");
		lorelist.add("��7�� ��� ������ �ñر� ��Ÿ�� ���� (����)");
		lorelist.add("��7�� �ñر� ��� 24�� -> 16�� �� ����");
		lorelist.add("��7�� �нú� ��� 12�� -> 8�� �� ����");
		lorelist.add("��7�� Ƽ�� 100���� �߰�");
		lorelist.add("��7�� �Ӷ��� �ּ� ���ݷ� 1����");
		lorelist.add("��7�� ���� ���� ����");
		lorelist.add("��7�� ���� ����");
		lorelist.add("��7�� ����� �������� ������ ��Ȱ�� ����Ǵ� ���� ����");
		lorelist.add("��7�� �� �� ���� ��ó������ �������� �����ʰ� �߽��ϴ�.");
		lorelist.add("��7�� �� �� ��ž ��ó������ �������� ������ �ǰ� �߽��ϴ�.");
		lorelist.add("��7�� ����������, ��Ż ����� �������� ������ ���з� ������ �����մϴ�.");
		meta.setLore(lorelist);
		item.setItemMeta(meta);
		patchNote.setItem(0, item);
		
		item = new ItemStack(Material.BOOK, 1);
		meta = item.getItemMeta();
		lorelist.clear();
		meta.setDisplayName("��f[ ��e��ġ��Ʈ 17/11/25��f ]");
		lorelist.add("��f- ��6����ġ ����");
		lorelist.add("��7�� ���� ������ ������ �� �� �� �Ѱ��� ���� ������");
		lorelist.add("��7   �������� �νĵǴ� ���� ����");
		lorelist.add("��f");
		lorelist.add("��f- ��6��������");
		lorelist.add("��7�� ��Ż�� 3��° ��ų������ NPC�� ��ġ�� ����Ǵ� ���� ����");
		lorelist.add("��f");
		lorelist.add("��f- ��6�� ���� ��Ʋ");
		lorelist.add("��7�� ���� ������ ������ ������ ������� �ʴ� ���� ����");
		lorelist.add("��f");
		lorelist.add("��f- ��6����");
		lorelist.add("��7�� �Ŵ޸��� �� ������ ������ ���");
		lorelist.add("��f");
		lorelist.add("��f- ��6��Ű���");
		lorelist.add("��7�� ���� ������ ������ �������� ������� �ʴ� ���� ����");
		lorelist.add("��f");
		lorelist.add("��f- ��6�� ���ٲ���");
		lorelist.add("��7�� ���� ������ ��ΰ� �¸�ó���Ǵ� ���� ����");
		lorelist.add("��f");
		meta.setLore(lorelist);
		item.setItemMeta(meta);
		patchNote.setItem(2, item);
		
		/*item = new ItemStack(Material.BOOK, 1);
		meta = item.getItemMeta();
		lorelist.clear();
		meta.setDisplayName("��f[ ��e��ġ��Ʈ 9/22 (3)��f ]");
		lorelist.add("��f- ��6��� ���ϱ�");
		lorelist.add("��7�� ��簡 �������� �ý��� ����");
		lorelist.add("��7-> ��c���� ��� �������� ��� (0.05�ʴ� 1����)");
		lorelist.add("��7-> ��c�ʹݿ� ��簡 0.1�ʸ��� 1���� �������ϴ�.");
		lorelist.add("��7-> ��c10�ʸ��� �Ʒ��κ��� 3ĭ�������� �����ϴ� ���� ������ϴ�.");
		lorelist.add("��7-> ��c20�ʸ��� 0.1�ʴ� �������� ����� ������ 1���� �����մϴ�.");
		lorelist.add("��f");
		lorelist.add("��f- ��6�ڸ�����");
		lorelist.add("��7�� ���� ����īƮ�� ��Ÿ�ص� ź ����� �������� �ʵ��� �����߽��ϴ�.");
		lorelist.add("��f");
		lorelist.add("��f- ��6��������");
		lorelist.add("��7�� ��Ż�� �ñرⰡ ��Ż�� 3��° ��ų�� ����Ǿ����ϴ�.");
		lorelist.add("��7�� ��Ż ��ġ���� ��ų ��ȿ���� 40ĭ -> 25ĭ���� ����");
		lorelist.add("��7�� ��Ż ��ġ���� ��ų ��Ÿ�� 39�� -> 19�ʷ� ����");
		lorelist.add("��7�� ��Ż�� ���ο� �ñر� ���� �ָӴ� ��ų�� �߰��ƽ��ϴ�.");
		meta.setLore(lorelist);
		item.setItemMeta(meta);
		patchNote.setItem(7, item);*/
		
		saveInventoryToFile(patchNote, this.getDataFolder(), patchNote.getTitle());
		saveInventoryToFile(Serverhelp, this.getDataFolder(), Serverhelp.getTitle());
		saveInventoryToFile(Kit_Lobby, this.getDataFolder(), Kit_Lobby.getTitle());
		saveInventoryToFile(Minigame, this.getDataFolder(), Minigame.getTitle());
		saveInventoryToFile(prefixshop, this.getDataFolder(), prefixshop.getTitle());
		saveInventoryToFile(mobarenainven, this.getDataFolder(), mobarenainven.getTitle());
		saveInventoryToFile(serverrule, this.getDataFolder(), serverrule.getTitle());
		saveInventoryToFile(parkourinven, this.getDataFolder(), parkourinven.getTitle());
		saveInventoryToFile(baseQuest, this.getDataFolder(), baseQuest.getTitle());
		
		LoadInventory();
		}
	
	private void LoadInventory(){
		Kit_Lobby = getInventoryFromFile(new File(this.getDataFolder(), "����Ŷ.invsave"));
		Minigame = getInventoryFromFile(new File(this.getDataFolder(), "��c��l�̴ϰ���.invsave"));
		patchNote = getInventoryFromFile(new File(this.getDataFolder(), "��c��l��ġ��Ʈ.invsave"));
		Serverhelp = getInventoryFromFile(new File(this.getDataFolder(), "��c��l���� ����.invsave"));
		prefixshop = getInventoryFromFile(new File(this.getDataFolder(), "��c��lĪȣ ����.invsave"));
		mobarenainven = getInventoryFromFile(new File(this.getDataFolder(), "��c��l�� �Ʒ���.invsave"));
		serverrule = getInventoryFromFile(new File(this.getDataFolder(), "��c��l������Ģ.invsave"));
		parkourinven = getInventoryFromFile(new File(this.getDataFolder(), "��c��l����.invsave"));
		baseQuest = getInventoryFromFile(new File(this.getDataFolder(), "��c��l��������.invsave"));
	}
	
	public void onDisable(){
		getLogger().info("EG���� ���� �ý����� ��ε� �Ǿ����ϴ�.");
	}
	
	public boolean onCommand(CommandSender talker, Command command, String str, String[] args){
		if(talker instanceof Player){
			Player p = (Player) talker;
			if(str.equalsIgnoreCase("egs")){
				if(args.length <= 0){
					Helpmessages(p);
					return true;
				}else{
					EGScommand(p, args);
					return true;
				}
			} else if(str.equalsIgnoreCase("Ȯ����")){
				AllVoice(p, args);
				return true;
			} else if(str.equalsIgnoreCase("����")){
				Response(p, args);
				return true;
			} else if(str.equalsIgnoreCase("����Ʈ")){
				Pointcommand(p, args);
				return true;
			} else if(str.equalsIgnoreCase("������")){
				if(Checkcooldown(p, "������")){
					Setcooldown(p, "������", 20);
					Location loc = p.getLocation();
					loc.setY(loc.getY()+1);
					p.teleport(loc);
				}
				return true;
			} else if(str.equalsIgnoreCase("ä��")){
				chatRoomCommand(p, args);
			} else if(str.equalsIgnoreCase("Ȯ�������")){
				noAllVoice(p);
			} else if(str.equalsIgnoreCase("�����̷�")){
				SoloVoice(p, args);
			}
		}
		return false;
	}
	
	private void SoloVoice(Player p, String[] args){
		if(args.length > 0){
			String t_name = args[0];
			if(solochat.contains(t_name)){
				solochat.remove(t_name);
				p.sendMessage(MS+t_name+" ���� �����߽��ϴ�.");
			} else {
				solochat.add(t_name);
				p.sendMessage(MS+t_name+" ���� ���� ȥ�ڼ� ä���� ġ�� �˴ϴ�.");	
			}
			getConfig().set("solochatlist", solochat);
			saveConfig();
		} else {
			p.sendMessage(MS+"/�����̷� <�г���> - <�г���> �÷��̾��� ä���� �ڽ��� ������ ������ ���� ���ϰ� �˴ϴ�.");
		}
	}
	
	private void Helpmessages(Player p){
		p.sendMessage(MS+"/egs ac - ����ä��");
		p.sendMessage(MS+"/egs say - �����˸�");
		p.sendMessage(MS+"/egs setspawn - ��������");
		p.sendMessage(MS+"/egs setlobby - �κ���");
		p.sendMessage(MS+"/egs mute <�г���> - Ȯ���� ��Ʈ");
		p.sendMessage(MS+"/egs unmute - Ȯ���� ��Ʈ����");
	}
	
	private void Pointcommand(Player p, String[] args){
		if(args.length <= 0){
			p.sendMessage(MS+"��b/����Ʈ Ȯ�� ��7- �ڽ��� ����Ʈ�� Ȯ���մϴ�.");
			p.sendMessage(MS+"��b/����Ʈ ������ <�г���> <����> <�޼���> ��7- Ÿ�ο��� ����Ʈ�� �����ϴ�.");
			return;
		} else if(args[0].equalsIgnoreCase("������")){
			if(args.length <= 1){
				p.sendMessage(MS+"��c���� ����� �г����� �����ּ���.");
				return;
			} 
			String target = args[1];
			if(getServer().getPlayer(target) == null){
				p.sendMessage(MS+"��c"+target+" ���� �¶����� �ƴմϴ�.");
				return;
			}
			if(args.length <= 2){
				p.sendMessage(MS+"��c���� ������ �����ּ���.");
				return;
			} 
			int amt = 0;
			try{
				amt = Integer.valueOf(args[2]);
			} catch(Exception e){
				p.sendMessage(MS+"��c0�̻��� ���ڸ� �Է����ּ���.");
			}
			if(econ.getBalance(p.getName()) < amt){
				p.sendMessage(MS+(amt-econ.getBalance(p.getName())+" ��ŭ�� ����Ʈ�� �����մϴ�."));
				return;
			}
			if(args.length <= 3){
				p.sendMessage(MS+"��c�޼����� �����ּ���.");
				return;
			}
			String msg = "";
			for(int i = 3; i < args.length; i++){
				msg += " "+args[i];
			}
			econ.withdrawPlayer(p.getName(), amt);
			econ.depositPlayer(target, amt);
			p.sendMessage(MS+target+" �Բ� "+amt+"����Ʈ�� ���½��ϴ�.");
			Player t = getServer().getPlayer(target);
			t.sendMessage(MS+p.getName()+" �Բ��� "+amt+"����Ʈ�� �����̽��ϴ�.");
			t.sendMessage(MS+"÷�ε� �޼��� : ��6"+msg);
			t.playSound(t.getLocation(), Sound.ITEM_PICKUP, 2.0f, 1.0f);
			return;
		} else if(args[0].equalsIgnoreCase("Ȯ��")){
			p.sendMessage(MS+"������ ����Ʈ : ��c"+econ.getBalance(p.getName())+" ��P");
			return;
		}
	}
	
	private void EGScommand(Player p, String[] args){
		if(args[0].equalsIgnoreCase("setspawn") && p.isOp()){
			Location loc = p.getLocation();
			p.getWorld().setSpawnLocation(loc.getBlockX(), loc.getBlockY()+1, loc.getBlockZ());
			p.sendMessage(MS+"���� �����Ϸ�");
		} else if(args[0].equalsIgnoreCase("setinvdata") && p.isOp()){
			SetInventory();
			LoadInventory();
			p.sendMessage(MS+"�����Ϸ�");
		} else if(args[0].equalsIgnoreCase("tppos") && p.isOp()){
			p.teleport(new Location(p.getWorld(), Integer.valueOf(args[1]), Integer.valueOf(args[2]), Integer.valueOf(args[3])));
		} else if(args[0].equalsIgnoreCase("setlobby")){
			getConfig().set("Lobby_world", p.getWorld().getName());
			getConfig().set("Lobby_x", p.getLocation().getBlockX());
			getConfig().set("Lobby_y", p.getLocation().getBlockY()+1);
			getConfig().set("Lobby_z", p.getLocation().getBlockZ());
			getConfig().set("Lobby_pitch", p.getLocation().getPitch());
			getConfig().set("Lobby_yaw", p.getLocation().getYaw());
			saveConfig();
			LoadConfig();
			p.sendMessage(MS+"�κ� �����Ϸ�");
			
		} else if(args[0].equalsIgnoreCase("ac") && p.hasPermission("opchat")){
			Player target = null;
			for(Player t : aclist){
				if(p.getName().equalsIgnoreCase(t.getName())){
					target = t; break;
				}
			}
			if(target != null){
				aclist.remove(target);
				p.sendMessage(MS+"���� ���� ä���� �����ϴ�.");
			} else {
				aclist.add(p);
				p.sendMessage(MS+"���� ���� ä���� �׽��ϴ�.");
			}
		} else if(args[0].equalsIgnoreCase("say")&& p.isOp()){
			if(args.length <= 1){
				p.sendMessage(MS+"/egs say <�Ҹ�>"); return;
			}
			String str = "";
			for(int i = 1; i < args.length; i++){
				str += " "+args[i];
			}
			Bukkit.broadcastMessage("��f[ ��c��l�˸� ��f]��b��l"+str);
		} else if(args[0].equalsIgnoreCase("mute") && p.isOp()){
			if(args.length <= 2){
				if(args.length <= 1) p.sendMessage(MS+"�г����� �Է����ּ���.");
				else p.sendMessage(MS+"�ð��� �Է����ּ���.");
				p.sendMessage(MS+"/egs mute <�г���> <�ð�>");
			} else {
				long time = 0;
				try{
					time = Long.valueOf(args[2]);
				}catch(Exception e){
					p.sendMessage(MS+"�ð��� ���ڷ� �Է����ּ���.");
					return;
				}
				Player t = Bukkit.getPlayer(args[1]);
				if(muteList.containsKey(t.getName())){
					p.sendMessage(MS+"�̹� Ȯ���� ��Ʈ ������ �÷��̾��Դϴ�.");
					return;
				}
				if(!t.isOnline()){
					p.sendMessage(MS+args[1]+"���� ������ ���������� �ʽ��ϴ�.");
					return;
				}
				muteAllVoice(p, t, time);
			}
		} else if(args[0].equalsIgnoreCase("unmute") && p.isOp()){
			if(args.length <= 1){
				p.sendMessage(MS+"�г����� �Է����ּ���.");
				p.sendMessage(MS+"/egs unmute <�г���>");
				return;
			} else {
				if(muteList.containsKey(args[1])){
					Bukkit.getScheduler().cancelTask(muteList.get(args[1]));
					muteList.remove(args[1]);
					p.sendMessage(MS+args[1]+"���� ���� Ȯ���⸦ ����Ͻ� �� �ֽ��ϴ�.");
				} else {
					p.sendMessage(MS+args[1]+"���� Ȯ���� �������°� �ƴմϴ�.");
				}
			}
		}
	}
	
	public void muteAllVoice(final Player p, final Player t, long time){
		t.sendMessage(MS+p.getName()+"���� ����� "+time+"�� ���� Ȯ���� �������·� �����ϼ̽��ϴ�.");
		p.sendMessage(MS+t.getName()+"���� "+time+"�ʰ� Ȯ���� �������·� �����Ͽ����ϴ�.");
		muteList.put(t.getName(), Bukkit.getScheduler().scheduleSyncDelayedTask(Main.instance, new Runnable(){
			public void run(){
				if(muteList.containsKey(t.getName())){
					muteList.remove(t.getName());
					t.sendMessage(MS+"���� Ȯ���⸦ ����Ͻ� �� �ֽ��ϴ�.");
					p.sendMessage(MS+t.getName()+"���� Ȯ���� �������°� �����Ǿ����ϴ�.");
				}
			}
		}, time*20));
	}
	
	public void AllVoice(Player p, String[] args){
		if(args.length <= 0){
			p.sendMessage(MS+"/Ȯ���� <�Ҹ�>"); return;
		}
		if(econ.getBalance(p.getName()) < 300){
			p.sendMessage(MS+"Ȯ���⸦ ����Ͻ÷��� 300����Ʈ�� �ʿ��մϴ�.");return;
		}
		if(Checkcooldown(p, "Ȯ����")){
			if(muteList.containsKey(p.getName())){
				p.sendMessage("����� Ȯ���� ���� �����Դϴ�.");
			} else {
				Setcooldown(p, "Ȯ����", 20);
				EconomyResponse r = econ.withdrawPlayer(p.getName(), 300);
				String str = "";
				for(int i = 0; i < args.length; i++){
					str += " "+args[i];
				}
				str.replace("&", "");
				str.replace("��", "");
				p.sendMessage(MS+"Ȯ���� ������� 300����Ʈ�� ����ϼ̽��ϴ�.");
				Player[] onlinePlayers = Bukkit.getOnlinePlayers();
				for(int i = 0; i < onlinePlayers.length; i++){
					Player recivePlayer = onlinePlayers[i];
					if(!noAllVoiceList.contains(recivePlayer.getName()))
					recivePlayer.sendMessage("��f[ ��a��lȮ���� ��f] ��6"+p.getName()+" ��f: ��a"+str);
				}
		
			}
		}
	}
	
	public void noAllVoice(Player p){
		String pName = p.getName();
		if(noAllVoiceList.contains(pName)){
			noAllVoiceList.remove(pName);
			p.sendMessage(MS+"Ȯ���� ������ �����߽��ϴ�.");
		} else {
			noAllVoiceList.add(pName);
			p.sendMessage(MS+"��� Ȯ���� �޼����� �����մϴ�.");
		}
	}
	
	public void SendOPchat(String str){
		for(Player p : Bukkit.getOnlinePlayers()){
			if(p.hasPermission("opchat")){
				p.sendMessage(str);
			}
		}
	}
	
	public void Response(Player p, String[] args){
		if(args.length <= 0){
			p.sendMessage(MS+"/���� <�ǰ�>"); return;
		}
		String str = "";
		for(int i = 0; i < args.length; i++){
			str += " "+args[i];
		}
		String receiveList = "";
		for(OfflinePlayer op : getServer().getOperators()){
			if(op.isOnline()){
				Player t = (Player) op;
				t.sendMessage("��f[ ��4��l���� ��f]"+p.getName()+" : "+str);
				receiveList += t.getName()+" | ";
			}
		}
		if(receiveList.equalsIgnoreCase("")){
			p.sendMessage(MS+"���� ���Ǹ� ������ �ִ� OP�� ������ ���������� �ʽ��ϴ�.");
		}else p.sendMessage(MS+"���Ǹ� ���½��ϴ�. ��ø� ��ٷ��ּ���.\n���Ǹ� ���� OP��� : "+receiveList);
	}
	
	public void chatRoomCommand(Player p, String args[]){
		
	}
	
	public static String getHandItemName(Player p){
		ItemStack item = p.getItemInHand();
		if(item == null || !item.hasItemMeta() || !item.getItemMeta().hasDisplayName()) return null;
		return item.getItemMeta().getDisplayName();
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
	
	public static boolean questDataToFile(Inventory inventory, String fileName) {
		if (inventory == null || fileName == null) return false;
		try {
			File invFile = new File(instance.getDataFolder().getPath()+"/quest", fileName + ".quest");
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
	
	public void UpdateScheduler(){
		Bukkit.getScheduler().scheduleSyncRepeatingTask(this, new Runnable()
		{
			public void run(){
				UpdateMinigame();
				if(--daycnt < 0){
					daycnt = 60;
					getServer().getWorld("world").setTime(8000);
					broadcastcnt++;
					switch(broadcastcnt){
					case 1:
						Bukkit.broadcastMessage("[��cTip��f] ��6���� ���� ���ӿ��� �����Ͻ÷��� ��b'/spawn' ��6�Ǵ� ��b'/����'��6 ��ɾ �Է����ּ���."); break;
					case 2:
						Bukkit.broadcastMessage("[��cTip��f] ��6��� �÷��̾�� �� ������ �����ôٸ� ��b'/Ȯ����' ��6��ɾ ����ϼ���."); break;
					case 3:
						Bukkit.broadcastMessage("[��cTip��f] ��6����Ʈ ���� ����� ��b'/����Ʈ' ��6��ɾ ����ϼ���."); break;
					case 4:
						Bukkit.broadcastMessage("[��cTip��f] ��6�Ƿº��� �߿��� ���� ��b�ųʡ�6�Դϴ�."); break;
					case 5:
						Bukkit.broadcastMessage("[��cTip��f] ��6���� ���Ǵ� ��bī���6�� ���ּ���."); break;
					case 6:
						Bukkit.broadcastMessage("[��cTip��f] ��6ī�� �ּ� : ��ehttp://cafe.naver.com/boli2"); break;
					case 7:
						Bukkit.broadcastMessage("[��cTip��f] ��6���� ������ ���� ��c'/������' ��6��ɾ �Է����ּ���."); break;
					case 8:
						Bukkit.broadcastMessage("[��cTip��f] ��6���� �߰�, �÷��̾��� �Ű�, ���� ����� ��b'/����' ��6��ɾ ����ϼ���."); broadcastcnt = 0; break;
					default: broadcastcnt = 0; break;
					}
				}
			}
		}, 0L, 20L);
	}
	
	public void UpdateMinigame(){
		List<String> lorelist = new ArrayList<String>();
		
		lorelist.clear();
		if(me.Bokum.FindTheMurder.GameSystem.checkstart){
			lorelist.add("��f- ��c���� ���۵�");
			lorelist.add("��c���� �ο� : \n��7���θ� �� : ��f"+me.Bokum.FindTheMurder.GameSystem.mlist.size()+"��\n ��7�ù��� : ��f"+me.Bokum.FindTheMurder.GameSystem.Getsurviver()+"��");
			if(me.Bokum.FindTheMurder.Main.jlist.size() >= 1 && (me.Bokum.FindTheMurder.GameSystem.mlist.size() <= 0 || me.Bokum.FindTheMurder.GameSystem.Getsurviver() <= 0)){
				if(me.Bokum.FindTheMurder.Main.Forcestoptimer >= 15){
					me.Bokum.FindTheMurder.GameSystem.ForceEnd();
					me.Bokum.FindTheMurder.Main.Forcestoptimer = 0;
				}else{
					me.Bokum.FindTheMurder.Main.Forcestoptimer++;
				}
			}
		} else if(me.Bokum.FindTheMurder.GameSystem.lobbystart) {
			lorelist.add("��f- ��e�ο� : ��6"+me.Bokum.FindTheMurder.Main.list.size()+"�� / 13��");
			lorelist.add("��f- ��c���� ����� �� ������ ���۵˴ϴ�.");
			lorelist.add("��f- ��b���� �Ͻ÷��� Ŭ�����ּ���.");
		} else {
			lorelist.add("��f- ��e�ο� : ��6"+me.Bokum.FindTheMurder.Main.list.size()+"�� / 13��");
			lorelist.add("��f- ��e�ּ��ο� : 5��");
			lorelist.add("��f- ��b���� �Ͻ÷��� Ŭ�����ּ���.");
		}
		ItemMeta meta = Minigame.getItem(10).getItemMeta();
		meta.setLore(lorelist);
		Minigame.getItem(10).setItemMeta(meta);
		Minigame.getItem(10).setAmount(me.Bokum.FindTheMurder.Main.list.size() <= 0 ? 1 : me.Bokum.FindTheMurder.Main.list.size());
		
		lorelist.clear();
		if(me.Bokum.TRB.Main.check_start){
			lorelist.add("��f- ��c���� ���۵�");
			lorelist.add("��c���� ���� ��f: ��7"+me.Bokum.TRB.Main.round+" ����");
			lorelist.add("��e������ �¸��� ��f: ��7"+me.Bokum.TRB.Main.redscore+" ��");
			lorelist.add("��e����� �¸��� ��f: ��7"+me.Bokum.TRB.Main.bluescore+" ��");
		} else if(me.Bokum.TRB.Main.check_lobbystart) {
			lorelist.add("��f- ��e�ο� : ��6"+me.Bokum.TRB.Main.plist.size()+"�� / 12��");
			lorelist.add("��f- ��c���� ����� �� ������ ���۵˴ϴ�.");
			lorelist.add("��f- ��b���� �Ͻ÷��� Ŭ�����ּ���.");
		} else {
			lorelist.add("��f- ��e�ο� : ��6"+me.Bokum.TRB.Main.plist.size()+"�� / 12��");
			lorelist.add("��f- ��e�ּ��ο� : 6��");
			lorelist.add("��f- ��b���� �Ͻ÷��� Ŭ�����ּ���.");
		}
		meta = Minigame.getItem(12).getItemMeta();
		meta.setLore(lorelist);
		Minigame.getItem(12).setItemMeta(meta);
		Minigame.getItem(12).setAmount(me.Bokum.TRB.Main.plist.size() <= 0 ? 1 : me.Bokum.TRB.Main.plist.size());
		
		lorelist.clear();
		if(me.Bokum.EWG.Main.check_start){
			lorelist.add("��f- ��c���� ���۵� ( ������ ���� )");
			lorelist.add("��e���� ���� �ð� : ��7"+me.Bokum.EWG.Main.timertime+"��");
			lorelist.add("��f- ��c�����Ͻ÷��� Ŭ�����ּ���.");
		} else if(me.Bokum.EWG.Main.check_lobbystart) {
			lorelist.add("��f- ��e�ο� : ��6"+me.Bokum.EWG.Main.plist.size()+"�� / 12��");
			lorelist.add("��f- ��c���� ����� �� ������ ���۵˴ϴ�.");
			lorelist.add("��f- ��b���� �Ͻ÷��� Ŭ�����ּ���.");
		} else {
			lorelist.add("��f- ��e�ο� : ��6"+me.Bokum.EWG.Main.plist.size()+"�� / 12��");
			lorelist.add("��f- ��e�ּ��ο� : 6��");
			lorelist.add("��f- ��b���� �Ͻ÷��� Ŭ�����ּ���.");
		}
		meta = Minigame.getItem(14).getItemMeta();
		meta.setLore(lorelist);
		Minigame.getItem(14).setItemMeta(meta);
		Minigame.getItem(14).setAmount(me.Bokum.EWG.Main.plist.size() <= 0 ? 1 : me.Bokum.EWG.Main.plist.size());
		
		lorelist.clear();
		if(me.Bokum.DTC.Main.check_start){
			lorelist.add("��f- ��c���� ���۵� ( �ߵ����� ���� )");
			lorelist.add("��e�������� ȹ���� ��� : ��7"+me.Bokum.DTC.Main.Redcore_cnt+"��");
			lorelist.add("��e������� ȹ���� ��� : ��7"+me.Bokum.DTC.Main.Bluecore_cnt+"��");
		} else if(me.Bokum.DTC.Main.check_lobbystart) {
			lorelist.add("��f- ��e�ο� : ��6"+me.Bokum.DTC.Main.plist.size()+"�� / 20��");
			lorelist.add("��f- ��c���� ����� �� ������ ���۵˴ϴ�.");
			lorelist.add("��f- ��b���� �Ͻ÷��� Ŭ�����ּ���.");
		} else {
			lorelist.add("��f- ��e�ο� : ��6"+me.Bokum.DTC.Main.plist.size()+"�� / 20��");
			lorelist.add("��f- ��e�ּ��ο� : 8��");
			lorelist.add("��f- ��b���� �Ͻ÷��� Ŭ�����ּ���.");
		}
		meta = Minigame.getItem(16).getItemMeta();
		meta.setLore(lorelist);
		Minigame.getItem(16).setItemMeta(meta);
		Minigame.getItem(16).setAmount(me.Bokum.DTC.Main.plist.size() <= 0 ? 1 : me.Bokum.DTC.Main.plist.size());
		
		lorelist.clear();
		if(me.Bokum.FirstHit.Main.check_start){
			lorelist.add("��f- ��c���� ���۵�");
			lorelist.add("��e���� �÷��̾��� ų�� : ��6"+me.Bokum.FirstHit.Main.topkill);
			if(me.Bokum.FirstHit.Main.plist.size() <= 0){
				if(me.Bokum.FirstHit.Main.Forcestoptimer >= 15){
					me.Bokum.FirstHit.Main.Forcestop();
					me.Bokum.FirstHit.Main.Forcestoptimer = 0;
				}else{
					me.Bokum.FirstHit.Main.Forcestoptimer++;
				}
			}
		} else if(me.Bokum.FirstHit.Main.check_lobbystart) {
			lorelist.add("��f- ��e�ο� : ��6"+me.Bokum.FirstHit.Main.plist.size()+"�� / 7��");
			lorelist.add("��f- ��c���� ����� �� ������ ���۵˴ϴ�.");
			lorelist.add("��f- ��b���� �Ͻ÷��� Ŭ�����ּ���.");
		} else {
			lorelist.add("��f- ��e�ο� : ��6"+me.Bokum.FirstHit.Main.plist.size()+"�� / 7��");
			lorelist.add("��f- ��e�ּ��ο� : 3��");
			lorelist.add("��f- ��b���� �Ͻ÷��� Ŭ�����ּ���.");
		}
		meta = Minigame.getItem(19).getItemMeta();
		meta.setLore(lorelist);
		Minigame.getItem(19).setItemMeta(meta);
		Minigame.getItem(19).setAmount(me.Bokum.FirstHit.Main.plist.size() <= 0 ? 1 : me.Bokum.FirstHit.Main.plist.size());
		
		
		lorelist.clear();
		if(me.Bokum.KTT.Main.check_start){
			lorelist.add("��f- ��c���� ���۵�");
			lorelist.add("��e���� �ο� : ��6"+me.Bokum.KTT.Main.plist.size()+"��");
			if(me.Bokum.KTT.Main.plist.size() <= 0){
				if(me.Bokum.KTT.Main.Forcestoptimer >= 15){
					me.Bokum.KTT.Main.Forcestop();
					me.Bokum.KTT.Main.Forcestoptimer = 0;
				}else{
					me.Bokum.KTT.Main.Forcestoptimer++;
				}
			}
		} else if(me.Bokum.KTT.Main.check_lobbystart) {
			lorelist.add("��f- ��e�ο� : ��6"+me.Bokum.KTT.Main.plist.size()+"�� / 9��");
			lorelist.add("��f- ��c���� ����� �� ������ ���۵˴ϴ�.");
			lorelist.add("��f- ��b���� �Ͻ÷��� Ŭ�����ּ���.");
		} else {
			lorelist.add("��f- ��e�ο� : ��6"+me.Bokum.KTT.Main.plist.size()+"�� / 9��");
			lorelist.add("��f- ��e�ּ��ο� : 4��");
			lorelist.add("��f- ��b���� �Ͻ÷��� Ŭ�����ּ���.");
		}
		meta = Minigame.getItem(21).getItemMeta();
		meta.setLore(lorelist);
		Minigame.getItem(21).setItemMeta(meta);
		Minigame.getItem(21).setAmount(me.Bokum.KTT.Main.plist.size() <= 0 ? 1 : me.Bokum.KTT.Main.plist.size());
		
		lorelist.clear();
		if(me.Bokum.Hungergame.Main.check_start){
			lorelist.add("��f- ��c���� ���۵�");
			lorelist.add("��e���� �ο� : ��6"+me.Bokum.Hungergame.Main.plist.size()+"��");
			if(me.Bokum.Hungergame.Main.plist.size() <= 0){
				if(me.Bokum.Hungergame.Main.Forcestoptimer >= 15){
					me.Bokum.Hungergame.Main.Forcestop();
					me.Bokum.Hungergame.Main.Forcestoptimer = 0;
				}else{
					me.Bokum.Hungergame.Main.Forcestoptimer++;
				}
			}
		} else if(me.Bokum.Hungergame.Main.check_lobbystart) {
			lorelist.add("��f- ��e�ο� : ��6"+me.Bokum.Hungergame.Main.plist.size()+"�� / 8��");
			lorelist.add("��f- ��c���� ����� �� ������ ���۵˴ϴ�.");
			lorelist.add("��f- ��b���� �Ͻ÷��� Ŭ�����ּ���.");
		} else {
			lorelist.add("��f- ��e�ο� : ��6"+me.Bokum.Hungergame.Main.plist.size()+"�� / 8��");
			lorelist.add("��f- ��e�ּ��ο� : 3��");
			lorelist.add("��f- ��b���� �Ͻ÷��� Ŭ�����ּ���.");
		}
		meta = Minigame.getItem(23).getItemMeta();
		meta.setLore(lorelist);
		Minigame.getItem(23).setItemMeta(meta);
		Minigame.getItem(23).setAmount(me.Bokum.Hungergame.Main.plist.size() <= 0 ? 1 : me.Bokum.Hungergame.Main.plist.size());
		
		lorelist.clear();
		if(me.Bokum.ESP.Main.check_start){
			lorelist.add("��f- ��c���� ���۵�");
			lorelist.add("��e���� �ο� : ��6"+me.Bokum.ESP.Main.plist.size()+"��");
			if(me.Bokum.ESP.Main.plist.size() <= 0){
				if(me.Bokum.ESP.Main.Forcestoptimer >= 15){
					me.Bokum.ESP.Main.Forcestop();
					me.Bokum.ESP.Main.Forcestoptimer = 0;
				}else{
					me.Bokum.ESP.Main.Forcestoptimer++;
				}
			}
		} else if(me.Bokum.ESP.Main.check_lobbystart) {
			lorelist.add("��f- ��e�ο� : ��6"+me.Bokum.ESP.Main.plist.size()+"�� / 7��");
			lorelist.add("��f- ��c���� ����� �� ������ ���۵˴ϴ�.");
			lorelist.add("��f- ��b���� �Ͻ÷��� Ŭ�����ּ���.");
		} else {
			lorelist.add("��f- ��e�ο� : ��6"+me.Bokum.ESP.Main.plist.size()+"�� / 7��");
			lorelist.add("��f- ��e�ּ��ο� : 3��");
			lorelist.add("��f- ��b���� �Ͻ÷��� Ŭ�����ּ���.");
		}
		meta = Minigame.getItem(30).getItemMeta();
		meta.setLore(lorelist);
		Minigame.getItem(30).setItemMeta(meta);
		Minigame.getItem(30).setAmount(me.Bokum.ESP.Main.plist.size() <= 0 ? 1 : me.Bokum.ESP.Main.plist.size());
		
		lorelist.clear();
		if(me.Bokum.ECM.Main.check_start){
			lorelist.add("��f- ��c���� ���۵�");
			lorelist.add("��e���� �ο� : ��6"+me.Bokum.ECM.Main.plist.size()+"��");
			if(me.Bokum.ECM.Main.plist.size() <= 0){
				if(me.Bokum.ECM.Main.Forcestoptimer >= 15){
					me.Bokum.ECM.Main.Forcestop();
					me.Bokum.ECM.Main.Forcestoptimer = 0;
				}else{
					me.Bokum.ECM.Main.Forcestoptimer++;
				}
			}
		} else if(me.Bokum.ECM.Main.check_lobbystart) {
			lorelist.add("��f- ��e�ο� : ��6"+me.Bokum.ECM.Main.plist.size()+"�� / 6��");
			lorelist.add("��f- ��c���� ����� �� ������ ���۵˴ϴ�.");
			lorelist.add("��f- ��b���� �Ͻ÷��� Ŭ�����ּ���.");
		} else {
			lorelist.add("��f- ��e�ο� : ��6"+me.Bokum.ECM.Main.plist.size()+"�� / 6��");
			lorelist.add("��f- ��e�ּ��ο� : 2��");
			lorelist.add("��f- ��b���� �Ͻ÷��� Ŭ�����ּ���.");
		}
		meta = Minigame.getItem(34).getItemMeta();
		meta.setLore(lorelist);
		Minigame.getItem(34).setItemMeta(meta);
		Minigame.getItem(34).setAmount(me.Bokum.ECM.Main.plist.size() <= 0 ? 1 : me.Bokum.ECM.Main.plist.size());
		
		lorelist.clear();
		if(me.Bokum.Catcher.Main.check_start){
			lorelist.add("��f- ��c���� ���۵�");
			lorelist.add("��e���� �ο� : ��6"+me.Bokum.Catcher.Main.plist.size()+"��");
			if(me.Bokum.Catcher.Main.plist.size() <= 0){
				if(me.Bokum.Catcher.Main.Forcestoptimer >= 15){
					me.Bokum.Catcher.Main.Forcestop();
					me.Bokum.Catcher.Main.Forcestoptimer = 0;
				}else{
					me.Bokum.Catcher.Main.Forcestoptimer++;
				}
			}
		} else if(me.Bokum.Catcher.Main.check_lobbystart) {
			lorelist.add("��f- ��e�ο� : ��6"+me.Bokum.Catcher.Main.plist.size()+"�� / 8��");
			lorelist.add("��f- ��c���� ����� �� ������ ���۵˴ϴ�.");
			lorelist.add("��f- ��b���� �Ͻ÷��� Ŭ�����ּ���.");
		} else {
			lorelist.add("��f- ��e�ο� : ��6"+me.Bokum.Catcher.Main.plist.size()+"�� / 8��");
			lorelist.add("��f- ��e�ּ��ο� : 4��");
			lorelist.add("��f- ��b���� �Ͻ÷��� Ŭ�����ּ���.");
		}
		meta = Minigame.getItem(37).getItemMeta();
		meta.setLore(lorelist);
		Minigame.getItem(37).setItemMeta(meta);
		Minigame.getItem(37).setAmount(me.Bokum.Catcher.Main.plist.size() <= 0 ? 1 : me.Bokum.Catcher.Main.plist.size());
		
		lorelist.clear();
		if(me.Bokum.CTM.Main.check_start){
			lorelist.add("��f- ��c���� ���۵�");
			lorelist.add("��e���� ���� : ��6"+me.Bokum.CTM.Main.round+" / 10");
			if(me.Bokum.CTM.Main.plist.size() <= 0){
				if(me.Bokum.CTM.Main.Forcestoptimer >= 15){
					me.Bokum.CTM.Main.Forcestop();
					me.Bokum.CTM.Main.Forcestoptimer = 0;
				}else{
					me.Bokum.CTM.Main.Forcestoptimer++;
				}
			}
		} else if(me.Bokum.CTM.Main.check_lobbystart) {
			lorelist.add("��f- ��e�ο� : ��6"+me.Bokum.CTM.Main.plist.size()+"�� / 6��");
			lorelist.add("��f- ��c���� ����� �� ������ ���۵˴ϴ�.");
			lorelist.add("��f- ��b���� �Ͻ÷��� Ŭ�����ּ���.");
		} else {
			lorelist.add("��f- ��e�ο� : ��6"+me.Bokum.CTM.Main.plist.size()+"�� / 6��");
			lorelist.add("��f- ��e�ּ��ο� : 4��");
			lorelist.add("��f- ��b���� �Ͻ÷��� Ŭ�����ּ���.");
		}
		meta = Minigame.getItem(28).getItemMeta();
		meta.setLore(lorelist);
		Minigame.getItem(28).setItemMeta(meta);
		Minigame.getItem(28).setAmount(me.Bokum.CTM.Main.plist.size() <= 0 ? 1 : me.Bokum.CTM.Main.plist.size());
		
		lorelist.clear();
		if(me.Bokum.Parkour.Main.check_start_race){
			lorelist.add("��f- ��e�ο� : ��6"+me.Bokum.Parkour.Main.tlist.size()+"�� / 10��");
			lorelist.add("��f- ��c���� ���۵�");
			if(me.Bokum.Parkour.Main.tlist.size() <= 0){
				if(me.Bokum.Parkour.Main.Forcestoptimer_race >= 15){
					me.Bokum.Parkour.Main.Forcestop_race();
					me.Bokum.Parkour.Main.Forcestoptimer_race = 0;
				}else{
					me.Bokum.Parkour.Main.Forcestoptimer_race++;
				}
			}
		} else if(me.Bokum.Parkour.Main.check_lobbystart_race) {
			lorelist.add("��f- ��e�ο� : ��6"+me.Bokum.Parkour.Main.tlist.size()+"�� / 10��");
			lorelist.add("��f- ��c���� ����� �� ������ ���۵˴ϴ�.");
			lorelist.add("��f- ��b���� �Ͻ÷��� Ŭ�����ּ���.");
		} else {
			lorelist.add("��f- ��e�ο� : ��6"+me.Bokum.Parkour.Main.tlist.size()+"�� / 10��");
			lorelist.add("��f- ��e�ּ��ο� : 1��");
			lorelist.add("��f- ��b���� �Ͻ÷��� Ŭ�����ּ���.");
		}
		meta = parkourinven.getItem(14).getItemMeta();
		meta.setLore(lorelist);
		parkourinven.getItem(14).setItemMeta(meta);
		parkourinven.getItem(14).setAmount(me.Bokum.Parkour.Main.tlist.size() <= 0 ? 1 : me.Bokum.Parkour.Main.tlist.size());
		
		lorelist.clear();
		if(me.Bokum.Parkour.Main.check_start_bed){
			lorelist.add("��f- ��e�����ο� : ��6"+me.Bokum.Parkour.Main.blist.size()+"��");
			lorelist.add("��f- ��c���� ���۵�");
			if(me.Bokum.Parkour.Main.blist.size() <= 0){
				if(me.Bokum.Parkour.Main.Forcestoptimer_bed >= 15){
					me.Bokum.Parkour.Main.Forcestop_bed();
					me.Bokum.Parkour.Main.Forcestoptimer_bed = 0;
				}else{
					me.Bokum.Parkour.Main.Forcestoptimer_bed++;
				}
			}
		} else if(me.Bokum.Parkour.Main.check_lobbystart_bed) {
			lorelist.add("��f- ��e�ο� : ��6"+me.Bokum.Parkour.Main.blist.size()+"�� / 15��");
			lorelist.add("��f- ��c���� ����� �� ������ ���۵˴ϴ�.");
			lorelist.add("��f- ��b���� �Ͻ÷��� Ŭ�����ּ���.");
		} else {
			lorelist.add("��f- ��e�ο� : ��6"+me.Bokum.Parkour.Main.blist.size()+"�� / 15��");
			lorelist.add("��f- ��e�ּ��ο� : 7��");
			lorelist.add("��f- ��b���� �Ͻ÷��� Ŭ�����ּ���.");
		}
		meta = parkourinven.getItem(16).getItemMeta();
		meta.setLore(lorelist);
		parkourinven.getItem(16).setItemMeta(meta);
		parkourinven.getItem(16).setAmount(me.Bokum.Parkour.Main.blist.size() <= 0 ? 1 : me.Bokum.Parkour.Main.blist.size());
		
		lorelist.clear();
		if(me.Bokum.MOB.Game.MobSystem.check_start){
			lorelist.add("��f- ��c���� ���۵� ( ������ ���� )");
			lorelist.add("��e����� Ƽ�ϼ� : ��b"+me.Bokum.MOB.Game.MobSystem.blue_ticket+"��6��");
			lorelist.add("��e������ Ƽ�ϼ� : ��b"+me.Bokum.MOB.Game.MobSystem.red_ticket+"��6��");
		} else if(me.Bokum.MOB.Game.MobSystem.check_lobbystart) {
			lorelist.add("��f- ��e�ο� : ��6"+me.Bokum.MOB.Game.MobSystem.plist.size()+"�� / 34��");
			lorelist.add("��f- ��c���� ����� �� ������ ���۵˴ϴ�.");
			lorelist.add("��f- ��b���� �Ͻ÷��� Ŭ�����ּ���.");
		} else {
			lorelist.add("��f- ��e�ο� : ��6"+me.Bokum.MOB.Game.MobSystem.plist.size()+"�� / 34��");
			lorelist.add("��f- ��e�ּ��ο� : 20��");
			lorelist.add("��f- ��b���� �Ͻ÷��� Ŭ�����ּ���.");
		}
		meta = Minigame.getItem(25).getItemMeta();
		meta.setLore(lorelist);
		Minigame.getItem(25).setItemMeta(meta);
		Minigame.getItem(25).setAmount(me.Bokum.MOB.Game.MobSystem.plist.size() <= 0 ? 1 : me.Bokum.MOB.Game.MobSystem.plist.size());
		
		lorelist.clear();
		if(me.Bokum.BlockHunter.Main.check_start){
			lorelist.add("��f- ��c���� ���۵�");
			lorelist.add("��e���� ������ : ��6"+me.Bokum.BlockHunter.Main.blist.size()+"��");
			lorelist.add("��e���� ���� : ��6"+me.Bokum.BlockHunter.Main.clist.size()+"��");
			/*if(me.Bokum.BlockHunter.Main.blist.size() <= 0){
				if(me.Bokum.BlockHunter.Main.Forcestoptimer >= 15){
					me.Bokum.BlockHunter.Main.Forcestop();
					me.Bokum.BlockHunter.Main.Forcestoptimer = 0;
				}else{
					me.Bokum.BlockHunter.Main.Forcestoptimer++;
				}
			}*/
		} else if(me.Bokum.BlockHunter.Main.check_lobbystart) {
			lorelist.add("��f- ��e�ο� : ��6"+me.Bokum.BlockHunter.Main.plist.size()+"�� / 10��");
			lorelist.add("��f- ��c���� ����� �� ������ ���۵˴ϴ�.");
			lorelist.add("��f- ��b���� �Ͻ÷��� Ŭ�����ּ���.");
		} else {
			lorelist.add("��f- ��e�ο� : ��6"+me.Bokum.BlockHunter.Main.plist.size()+"�� / 10��");
			lorelist.add("��f- ��e�ּ��ο� : 5��");
			lorelist.add("��f- ��b���� �Ͻ÷��� Ŭ�����ּ���.");
		}
		meta = Minigame.getItem(41).getItemMeta();
		meta.setLore(lorelist);
		Minigame.getItem(41).setItemMeta(meta);
		Minigame.getItem(41).setAmount(me.Bokum.BlockHunter.Main.plist.size() <= 0 ? 1 : me.Bokum.BlockHunter.Main.plist.size());
		
		lorelist.clear();
		if(me.Bokum.SMG.Main.avoidanvil.isStart){
			lorelist.add("��f- ��c���� ���۵�");
			lorelist.add("��e���� �ο� : ��6"+me.Bokum.SMG.Main.avoidanvil.playerList.size()+"��");
			if(me.Bokum.SMG.Main.avoidanvil.playerList.size() <= 0){
				if(me.Bokum.SMG.Main.avoidanvil.Forcestoptimer >= 15){
					me.Bokum.SMG.Main.avoidanvil.forceStop();
					me.Bokum.SMG.Main.avoidanvil.Forcestoptimer = 0;
				}else{
					me.Bokum.SMG.Main.avoidanvil.Forcestoptimer++;
				}
			}
		} else if(me.Bokum.SMG.Main.avoidanvil.isLobbyStart) {
			lorelist.add("��f- ��e�ο� : ��6"+me.Bokum.SMG.Main.avoidanvil.playerList.size()+"�� / "+me.Bokum.SMG.Main.avoidanvil.maxPlayer+"��");
			lorelist.add("��f- ��c���� ����� �� ������ ���۵˴ϴ�.");
			lorelist.add("��f- ��b���� �Ͻ÷��� Ŭ�����ּ���.");
		} else {
			lorelist.add("��f- ��e�ο� : ��6"+me.Bokum.SMG.Main.avoidanvil.playerList.size()+"�� / "+me.Bokum.SMG.Main.avoidanvil.maxPlayer+"��");
			lorelist.add("��f- ��e�ּ��ο� : "+me.Bokum.SMG.Main.avoidanvil.minPlayer+"��");
			lorelist.add("��f- ��b���� �Ͻ÷��� Ŭ�����ּ���.");
		}
		meta = Minigame.getItem(43).getItemMeta();
		meta.setLore(lorelist);
		Minigame.getItem(43).setItemMeta(meta);
		Minigame.getItem(43).setAmount(me.Bokum.SMG.Main.playingList.size() <= 0 ? 1 : me.Bokum.SMG.Main.avoidanvil.playerList.size());
		
		lorelist.clear();
		if(me.Bokum.SMG.Main.deathrun.isStart){
			lorelist.add("��f- ��c���� ���۵�");
			lorelist.add("��e���� �ο� : ��6"+me.Bokum.SMG.Main.deathrun.playerList.size()+"��");
			if(me.Bokum.SMG.Main.deathrun.playerList.size() <= 0){
				if(me.Bokum.SMG.Main.deathrun.Forcestoptimer >= 15){
					me.Bokum.SMG.Main.deathrun.forceStop();
					me.Bokum.SMG.Main.deathrun.Forcestoptimer = 0;
				}else{
					me.Bokum.SMG.Main.deathrun.Forcestoptimer++;
				}
			}
		} else if(me.Bokum.SMG.Main.deathrun.isLobbyStart) {
			lorelist.add("��f- ��e�ο� : ��6"+me.Bokum.SMG.Main.deathrun.playerList.size()+"�� / "+me.Bokum.SMG.Main.deathrun.maxPlayer+"��");
			lorelist.add("��f- ��c���� ����� �� ������ ���۵˴ϴ�.");
			lorelist.add("��f- ��b���� �Ͻ÷��� Ŭ�����ּ���.");
		} else {
			lorelist.add("��f- ��e�ο� : ��6"+me.Bokum.SMG.Main.deathrun.playerList.size()+"�� / "+me.Bokum.SMG.Main.deathrun.maxPlayer+"��");
			lorelist.add("��f- ��e�ּ��ο� : "+me.Bokum.SMG.Main.deathrun.minPlayer+"��");
			lorelist.add("��f- ��b���� �Ͻ÷��� Ŭ�����ּ���.");
		}
		meta = Minigame.getItem(46).getItemMeta();
		meta.setLore(lorelist);
		Minigame.getItem(46).setItemMeta(meta);
		Minigame.getItem(46).setAmount(me.Bokum.SMG.Main.playingList.size() <= 0 ? 1 : me.Bokum.SMG.Main.deathrun.playerList.size());
		
		lorelist.clear();
		if(me.Bokum.SMG.Main.takeseat.isStart){
			lorelist.add("��f- ��c���� ���۵�");
			lorelist.add("��e���� �ο� : ��6"+me.Bokum.SMG.Main.takeseat.playerList.size()+"��");
			if(me.Bokum.SMG.Main.takeseat.playerList.size() <= 0){
				if(me.Bokum.SMG.Main.takeseat.Forcestoptimer >= 15){
					me.Bokum.SMG.Main.takeseat.forceStop();
					me.Bokum.SMG.Main.takeseat.Forcestoptimer = 0;
				}else{
					me.Bokum.SMG.Main.takeseat.Forcestoptimer++;
				}
			}
		} else if(me.Bokum.SMG.Main.takeseat.isLobbyStart) {
			lorelist.add("��f- ��e�ο� : ��6"+me.Bokum.SMG.Main.takeseat.playerList.size()+"�� / "+me.Bokum.SMG.Main.takeseat.maxPlayer+"��");
			lorelist.add("��f- ��c���� ����� �� ������ ���۵˴ϴ�.");
			lorelist.add("��f- ��b���� �Ͻ÷��� Ŭ�����ּ���.");
		} else {
			lorelist.add("��f- ��e�ο� : ��6"+me.Bokum.SMG.Main.takeseat.playerList.size()+"�� / "+me.Bokum.SMG.Main.takeseat.maxPlayer+"��");
			lorelist.add("��f- ��e�ּ��ο� : "+me.Bokum.SMG.Main.takeseat.minPlayer+"��");
			lorelist.add("��f- ��b���� �Ͻ÷��� Ŭ�����ּ���.");
		}
		meta = Minigame.getItem(48).getItemMeta();
		meta.setLore(lorelist);
		Minigame.getItem(48).setItemMeta(meta);
		Minigame.getItem(48).setAmount(me.Bokum.SMG.Main.playingList.size() <= 0 ? 1 : me.Bokum.SMG.Main.takeseat.playerList.size());
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
			inventory = Bukkit.getServer().createInventory(invHolder, invSize, ChatColor.translateAlternateColorCodes('��', invTitle));
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
	
	public static Inventory getQuestData(String name) {
		File file = new File(instance.getDataFolder().getPath()+"/quest", name+".quest");
		if (!file.exists() || file.isDirectory() || !file.getAbsolutePath().endsWith(".quest")) return null;
		try {
			FileConfiguration invConfig = YamlConfiguration.loadConfiguration(file);
			Inventory inventory = null;
			String invTitle = invConfig.getString("Title", "Inventory");
			int invSize = invConfig.getInt("Size", 27);
			int invMaxStackSize = invConfig.getInt("Max stack size", 64);
			InventoryHolder invHolder = null;
			if (invConfig.contains("Holder")) invHolder = Bukkit.getPlayer(invConfig.getString("Holder"));
			inventory = Bukkit.getServer().createInventory(invHolder, invSize, ChatColor.translateAlternateColorCodes('��', invTitle));
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
	
	public static FileConfiguration getPlayerData(String name) {
		if(name == null) return null;
		File file = new File(instance.getDataFolder().getPath()+"/data", name+".data");
		if (!file.exists() || file.isDirectory()) return null;
		try {
			FileConfiguration config = YamlConfiguration.loadConfiguration(file);
			return config;
		} catch(Exception e){
			return null;
		}
	}
	
	public static FileConfiguration getRank(String name) {
		if(name == null) return null;
		File file = new File(instance.getDataFolder().getPath()+"/rank", name+".rank");
		if (!file.exists() || file.isDirectory()) return null;
		try {
			FileConfiguration config = YamlConfiguration.loadConfiguration(file);
			return config;
		} catch(Exception e){
			return null;
		}
	}
	
	private static List<Player> getPlayingGame(Player p){
		return null;
	}
	
	private static double getDistance(Player p, Player t){
		return p.getLocation().distance(t.getLocation());
	}
	
	public void MinigameInvenClick(Player p, int slot){
		p.playSound(p.getLocation(), Sound.CHICKEN_EGG_POP, 2.0f, 0.8f);
		switch(slot){
		case 10: p.closeInventory(); me.Bokum.FindTheMurder.GameSystem.Joingame(p); return;
		case 12: p.closeInventory(); me.Bokum.TRB.Main.GameJoin(p); return;
		case 14: p.closeInventory(); me.Bokum.EWG.Main.GameJoin(p); return;
		case 16: p.closeInventory(); me.Bokum.DTC.Main.GameJoin(p); return;
		case 19: p.closeInventory(); me.Bokum.FirstHit.Main.GameJoin(p); return;
		case 21: p.closeInventory(); me.Bokum.KTT.Main.GameJoin(p); return;
		case 23: p.closeInventory(); me.Bokum.Hungergame.Main.GameJoin(p); return;
		case 39: p.closeInventory(); p.openInventory(mobarenainven); return;
		case 30: p.closeInventory(); me.Bokum.ESP.Main.GameJoin(p); return;
		case 32: p.closeInventory(); p.openInventory(parkourinven); return;
		case 34: p.closeInventory(); me.Bokum.ECM.Main.GameJoin(p); return;
		case 37: p.closeInventory(); me.Bokum.Catcher.Main.GameJoin(p); return;
		case 28: p.closeInventory(); me.Bokum.CTM.Main.GameJoin(p); return;
		case 25: p.closeInventory(); me.Bokum.MOB.Game.MobSystem.GameJoin(p); return;
		case 41: p.closeInventory(); me.Bokum.BlockHunter.Main.GameJoin(p); return;
		case 43: p.closeInventory(); me.Bokum.SMG.Main.avoidanvil.gameJoin(p); return;
		case 46: p.closeInventory(); me.Bokum.SMG.Main.deathrun.gameJoin(p); return;
		case 48: p.closeInventory(); me.Bokum.SMG.Main.takeseat.gameJoin(p); return;
		case 50: p.closeInventory(); me.Bokum.SMG.Main.chamber.gameJoin(p); return;
		default: return;
		}
	}
	
	public void ParkourInvenClick(Player p, int slot){
		p.playSound(p.getLocation(), Sound.CHICKEN_EGG_POP, 2.0f, 0.8f);
		switch(slot){
		case 10: p.closeInventory(); me.Bokum.Parkour.Main.TrainingJoin(p); return;
		case 12: p.closeInventory(); me.Bokum.Parkour.Main.PracticeJoin(p); return;
		case 14: p.closeInventory(); me.Bokum.Parkour.Main.GameJoinrace(p); return;
		case 16: p.closeInventory(); me.Bokum.Parkour.Main.GameJoinbed(p); return;
				
		default: return;
		}
	}
	
	public void ServerInfoInvenClick(Player p, int slot){
		p.playSound(p.getLocation(), Sound.CHICKEN_EGG_POP, 2.0f, 0.8f);
		switch(slot){
		case 11: p.closeInventory(); p.openInventory(serverrule); return;
		case 15: p.closeInventory(); p.openInventory(prefixshop); return;
		default: return;
		}
	}
	
	public void PrefixShopInvenClick(Player p, ItemStack item){
		p.playSound(p.getLocation(), Sound.CHICKEN_EGG_POP, 2.0f, 0.8f);
		if(item != null && item.getTypeId() != 102){
			p.closeInventory();
			if(!item.hasItemMeta() || !item.getItemMeta().hasDisplayName()) return;
			if(econ.getBalance(p.getName()) >= 6000){
				EconomyResponse r = econ.withdrawPlayer(p.getName(), 6000);
				String prefix = item.getItemMeta().getDisplayName();
				if(prefix.contains("[ ") || prefix.contains(" ]")) return;
				Bukkit.dispatchCommand(p.getServer().getConsoleSender(), "Īȣ �߰� "+p.getName()+" "+prefix);
			}else{
				p.sendMessage(MS+"Īȣ�� �����Ͻ÷��� ��c6000����Ʈ ��f�̻��� �ʿ��մϴ�.");
			}
		}
	}
	
	public void checkFirstJoin(File file, Player p) {
		if (file == null) return;
		if (file.isDirectory() || !file.getAbsolutePath().endsWith(".data")) return;
		if(!file.exists()){
		try {
				FileConfiguration dataConfig = YamlConfiguration.loadConfiguration(file);
				dataConfig.save(file);
				questDataToFile(baseQuest, p.getName());
				Bukkit.dispatchCommand(p.getServer().getConsoleSender(), "Īȣ �߰� "+p.getName()+" ��f[��b������f]");
				Bukkit.dispatchCommand(p, "Īȣ ��ǥ 1");
			} catch(Exception e){
				return;
			}
		}
	}

	public static boolean Checkcooldown(Player p, String str)
	{
		if(!Main.cooldown.containsKey(p.getName()+str) || Main.cooldown.get(p.getName()+str) <= (int)(java.lang.System.currentTimeMillis()/1000))
		{
			return true;
		}
		p.sendMessage(MS+ChatColor.AQUA+(Main.cooldown.get(p.getName()+str)-(int)(java.lang.System.currentTimeMillis()/1000))
				+ChatColor.RESET+"�� �� ����Ͻ� �� �ֽ��ϴ�.");
		return false;
	}
	
	public void Setcooldown(Player p, String str, int cooldown)
	{
		Main.cooldown.put(p.getName()+str, (int)(java.lang.System.currentTimeMillis()/1000)+cooldown);
	}
	
	public static void Spawn(Player p)
	{
		for(PotionEffect effect : p.getActivePotionEffects())
		{
			p.removePotionEffect(effect.getType());
		}
		p.setNoDamageTicks(0);
		p.setMaxHealth(20);
		p.setLevel(0);
		p.setExp(0);
		p.setMaxHealth(20);
		p.setHealth(20);
		p.setFoodLevel(20);
		p.setSneaking(false);
		p.setAllowFlight(false);
		p.setFlying(false);
		p.getInventory().clear();
		p.getInventory().setHelmet(null);
		p.getInventory().setChestplate(null);
		p.getInventory().setLeggings(null);
		p.getInventory().setBoots(null);
		p.getInventory().setContents(Kit_Lobby.getContents());
		p.teleport(Loc_Lobby);
		p.updateInventory();
		Bukkit.dispatchCommand(p.getServer().getConsoleSender(), "scoreboard teams leave "+p.getName());
		gamelist.put(p.getName(), "����");
		if(me.Bokum.SMG.Main.playingList.containsKey(p.getName())) me.Bokum.SMG.Main.playingList.remove(p.getName());
	}
	
	@EventHandler
	public void onChat(PlayerChatEvent e){
		if(aclist.contains(e.getPlayer())){
			e.setCancelled(true); SendOPchat("��f[ ��9��l����ä�� ��f] "+e.getPlayer().getName()+" : "+e.getMessage());
		} else if(solochat.contains(e.getPlayer().getName())){
			for(Player onp : Bukkit.getOnlinePlayers()){
				if(!onp.getName().equalsIgnoreCase(e.getPlayer().getName()))
					e.getRecipients().remove(onp);
			}
		}
	}
	
	@EventHandler
	public void onPlayerDeath(PlayerDeathEvent e){
		e.setDeathMessage(null);
	}
	
	@EventHandler
	public void onPlayerKicked(PlayerKickEvent e){
		e.setLeaveMessage(null);
	}
	
	@EventHandler
	public void onPlayerQuit(PlayerQuitEvent e){
		e.setQuitMessage(null);
	}
	
	@EventHandler(priority=EventPriority.HIGHEST, ignoreCancelled=true)
	public void onPlayerJoin(PlayerJoinEvent e){
		Player p = e.getPlayer();
		Spawn(p);
		e.setJoinMessage(null);
		checkFirstJoin(new File(this.getDataFolder().getPath()+"/data", p.getName()+".data"), p);
	}
	
	@EventHandler(priority=EventPriority.HIGHEST)
	public void onPlayerLoginEvent(PlayerLoginEvent e){
		Player p = e.getPlayer();
		if(!p.isWhitelisted()){
			if(p.getName().length() < 16){
				p.setWhitelisted(true);
				e.allow();
			} else {
				e.disallow(PlayerLoginEvent.Result.KICK_WHITELIST, "�������� ������ ��Ȱ���� �ʽ��ϴ�. ī�信 �������ּ���.");
			}
		}
	}
	
	@EventHandler
	public void onPlayercommand(PlayerCommandPreprocessEvent e)
	{
		Player p = e.getPlayer();
		if(e.getMessage().equalsIgnoreCase("/����") || e.getMessage().equalsIgnoreCase("/spawn")
				|| e.getMessage().equalsIgnoreCase("/����") || e.getMessage().equalsIgnoreCase("/����"))
		{
			Spawn(p);
			e.setCancelled(true);
		}
		if(e.getMessage().contains("/ma") && !p.isOp()) e.setCancelled(true);
	    if ((!e.isCancelled())) {
	        String command = e.getMessage().split(" ")[0];
	        HelpTopic htopic = Bukkit.getServer().getHelpMap().getHelpTopic(command);
	        if (htopic == null) {
	          p.sendMessage(MS+"��c�������� �ʴ� ��ɾ��Դϴ�.");
	          e.setCancelled(true);
	        }
	    } 
	}
	
	@EventHandler
	public void onRightClick(PlayerInteractEvent e){
		if(e.getAction() == Action.RIGHT_CLICK_AIR || e.getAction() == Action.RIGHT_CLICK_BLOCK){
			if(e.getItem() == null) return;
			Player p = e.getPlayer();
			String str = getHandItemName(p);
			if(str == null) return;
			switch(str){
			case "��f[ ��b�̴ϰ��� ��f]":
				p.openInventory(Minigame); return;
			case "��f[ ��b���� ���� ��f]":
				p.openInventory(Serverhelp); return;
			case "��f[ ��b��ġ��Ʈ ��f]":
				p.openInventory(patchNote); return;
			default: return;
			}
		}
	}
	
	@EventHandler
	public void onRightClickEntity(PlayerInteractEntityEvent e){
		Player p = e.getPlayer();
		if(e.getRightClicked() instanceof Player){
			Player t = (Player) e.getRightClicked();
			if(t.getName().equalsIgnoreCase("��f[��c�������ε��ư����f]")) Spawn(p);
		}
	}
	
	@EventHandler
	public void onFoodLevelChange(FoodLevelChangeEvent e){
		if(e.getEntity() instanceof Player){
			Player p = (Player) e.getEntity();
			if(gamelist.containsKey(p.getName()) && !gamelist.get(p.getName()).equalsIgnoreCase("�ŵ��� ����") && !gamelist.get(p.getName()).equalsIgnoreCase("��Ű���")){
				e.setFoodLevel(20);
			}
		}
	}
	
	@EventHandler
	public void onDamage(EntityDamageEvent e){
		if(!(e.getEntity() instanceof Player)) return;
		Player p = (Player) e.getEntity();
		if(!gamelist.containsKey(p.getName()) 
				|| !gamelist.get(p.getName()).equalsIgnoreCase("�ŵ��� ����")){
			ItemStack item = p.getInventory().getHelmet();
			if(item != null)item.setDurability((short) (item.getType().getMaxDurability()-item.getType().getMaxDurability()));
			item = p.getInventory().getChestplate();
			if(item != null)item.setDurability((short) (item.getType().getMaxDurability()-item.getType().getMaxDurability()));
			item = p.getInventory().getLeggings();
			if(item != null)item.setDurability((short) (item.getType().getMaxDurability()-item.getType().getMaxDurability()));
			item = p.getInventory().getBoots();
			if(item != null)item.setDurability((short) (item.getType().getMaxDurability()-item.getType().getMaxDurability()));
		}
	}
	
	@EventHandler
	public void onHit(EntityDamageByEntityEvent e){
		if(!(e.getDamager() instanceof Player)) return;
		Player d = (Player) e.getDamager();
		if(!gamelist.get(d.getName()).equalsIgnoreCase("�ŵ��� ����")){
			if(d.getItemInHand() != null && (
					d.getItemInHand().getType().toString().contains("AXE") || 
					d.getItemInHand().getType().toString().contains("HOE") ||
					d.getItemInHand().getType().toString().contains("SWORD") ||
					d.getItemInHand().getType().toString().contains("SPADE") ||
					d.getItemInHand().getType().toString().contains("BOW"))){
				d.getItemInHand().setDurability((short) (d.getItemInHand().getType().getMaxDurability() - d.getItemInHand().getType().getMaxDurability()));
			}
		}
	}
	
	@EventHandler
	public void onBlockBreak(BlockBreakEvent e){
		Player p = (Player) e.getPlayer();
		if(gamelist.containsKey(p.getName()) && gamelist.get(p.getName()).equalsIgnoreCase("����") && !p.isOp()){
			e.setCancelled(true);
		}
	}
	
	@EventHandler
	public void onBlockBreak(BlockPlaceEvent e){
		Player p = (Player) e.getPlayer();
		if(gamelist.containsKey(p.getName()) && gamelist.get(p.getName()).equalsIgnoreCase("����") && !p.isOp()){
			e.setCancelled(true);
		}
	}
	
	@EventHandler
	public void onClickInventory(InventoryClickEvent e){
		if(!(e.getWhoClicked() instanceof Player)) return;
		Player p = (Player) e.getWhoClicked();
		switch(e.getInventory().getTitle()){
		case "��c��l�̴ϰ���": e.setCancelled(true); p.updateInventory(); MinigameInvenClick(p, e.getSlot()); return;
		case "��c��l���� ����": e.setCancelled(true); p.updateInventory(); ServerInfoInvenClick(p, e.getSlot()); return;
		case "��c��lĪȣ ����": e.setCancelled(true); p.updateInventory(); PrefixShopInvenClick(p, e.getCurrentItem()); return;
		case "��c��l����": e.setCancelled(true); p.updateInventory(); ParkourInvenClick(p, e.getSlot()); return;
		case "��c��l��ġ��Ʈ": e.setCancelled(true); p.updateInventory(); return;
		case "��c��l������Ģ": e.setCancelled(true); if(e.getSlot() == 12) p.sendMessage("��a�����ּ� : ��6http://cafe.naver.com/boli2"); return;
		default: return;
		}
	}
}
