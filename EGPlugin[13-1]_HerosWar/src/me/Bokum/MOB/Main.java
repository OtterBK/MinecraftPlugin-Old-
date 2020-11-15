package me.Bokum.MOB;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.Color;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Arrow;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.entity.EntityRegainHealthEvent;
import org.bukkit.event.entity.EntityRegainHealthEvent.RegainReason;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.event.entity.ProjectileLaunchEvent;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.player.PlayerChatEvent;
import org.bukkit.event.player.PlayerCommandPreprocessEvent;
import org.bukkit.event.player.PlayerDropItemEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerItemHeldEvent;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.event.player.PlayerRespawnEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.inventory.meta.LeatherArmorMeta;
import org.bukkit.plugin.RegisteredServiceProvider;
import org.bukkit.plugin.java.JavaPlugin;

import de.slikey.effectlib.EffectLib;
import de.slikey.effectlib.EffectManager;
import de.slikey.effectlib.effect.DonutEffect;
import me.Bokum.MOB.Ability.Ability;
import me.Bokum.MOB.Attacker.Assasin;
import me.Bokum.MOB.Attacker.BowMaster;
import me.Bokum.MOB.Attacker.Ninja;
import me.Bokum.MOB.Attacker.PassiveMaster;
import me.Bokum.MOB.Expert.Bomber;
import me.Bokum.MOB.Expert.Engineer;
import me.Bokum.MOB.Expert.Randomer;
import me.Bokum.MOB.Expert.Tracer;
import me.Bokum.MOB.Game.Cooldown;
import me.Bokum.MOB.Game.MobSystem;
import me.Bokum.MOB.Tanker.Blocker;
import me.Bokum.MOB.Tanker.Imrapu;
import me.Bokum.MOB.Tanker.Knight;
import me.Bokum.MOB.Tanker.Shielder;
import me.Bokum.Supporter.AreaCreator;
import me.Bokum.Supporter.Portal;
import me.Bokum.Supporter.Priest;
import me.Bokum.Supporter.Witch;
import net.milkbowl.vault.economy.Economy;

public class Main extends JavaPlugin implements Listener{
	public static String MS = "��f[ ��aEG ��f] ";
	public static Location Lobby;
	public static Location joinpos;
	public static Location Redspawn;
	public static Location Bluespawn;
	public static Location Redj;
	public static Location Bluej;
	public static Location Reddeath;
	public static Location Bluedeath;
	public static Location Core[] = new Location[5];
	public static ItemStack helpitem;
	public static ItemStack compassitem;
	public static Inventory Utility;
	public static Inventory gamehelper;
	public static Inventory JobSelect;
	public static Inventory Shopinven;
	public static Inventory Compassinven;
	public static ItemStack jobitem;
	public static ItemStack shopitem;
	public static ItemStack bluearmor[] = new ItemStack[4];
	public static ItemStack redarmor[] = new ItemStack[4];
	public static Economy econ = null;
	public static Main instance;
    public static EffectManager effectManager;
    public static int Forcestoptimer = 0;
	
	public void onEnable(){
		getServer().getPluginManager().registerEvents(this, this);
		
        if (!setupEconomy() ) {
            getLogger().info("[���� �߻� ��� ] Vault�÷������� �νĵ��� �ʾҽ��ϴ�!");
        }
        
        EffectLib lib = EffectLib.instance();
        effectManager = new EffectManager(lib);
        
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
		
		bluearmor[3] = new ItemStack(Material.LEATHER_BOOTS, 1);
		LeatherArmorMeta leathermeta4 = (LeatherArmorMeta) bluearmor[3].getItemMeta();
		leathermeta4.setColor(Color.BLUE);
		leathermeta4.addEnchant(Enchantment.DURABILITY, 100, true);
		bluearmor[3].setItemMeta(leathermeta4);

		bluearmor[2] = new ItemStack(Material.LEATHER_LEGGINGS, 1);
		LeatherArmorMeta leathermeta3 = (LeatherArmorMeta) bluearmor[2].getItemMeta();
		leathermeta3.setColor(Color.BLUE);
		leathermeta3.addEnchant(Enchantment.DURABILITY, 100, true);
		bluearmor[2].setItemMeta(leathermeta3);

		bluearmor[1] = new ItemStack(Material.LEATHER_CHESTPLATE, 1);
		LeatherArmorMeta leathermeta2 = (LeatherArmorMeta) bluearmor[1].getItemMeta();
		leathermeta2.setColor(Color.BLUE);
		leathermeta2.addEnchant(Enchantment.DURABILITY, 100, true);
		bluearmor[1].setItemMeta(leathermeta2);

		bluearmor[0] = new ItemStack(Material.LEATHER_HELMET, 1);
		LeatherArmorMeta leathermeta1 = (LeatherArmorMeta) bluearmor[0].getItemMeta();
		leathermeta1.setColor(Color.BLUE);
		leathermeta1.addEnchant(Enchantment.DURABILITY, 100, true);
		bluearmor[0].setItemMeta(leathermeta1);

		redarmor[0] = new ItemStack(Material.LEATHER_HELMET, 1);
		LeatherArmorMeta leathermeta = (LeatherArmorMeta) redarmor[0].getItemMeta();
		leathermeta.setColor(Color.RED);
		leathermeta.addEnchant(Enchantment.DURABILITY, 100, true);
		redarmor[0].setItemMeta(leathermeta);

		redarmor[1] = new ItemStack(Material.LEATHER_CHESTPLATE, 1);
		leathermeta = (LeatherArmorMeta) redarmor[1].getItemMeta();
		leathermeta.setColor(Color.RED);
		leathermeta.addEnchant(Enchantment.DURABILITY, 100, true);
		redarmor[1].setItemMeta(leathermeta);

		redarmor[2] = new ItemStack(Material.LEATHER_LEGGINGS, 1);
		leathermeta = (LeatherArmorMeta) redarmor[2].getItemMeta();
		leathermeta.setColor(Color.RED);
		leathermeta.addEnchant(Enchantment.DURABILITY, 100, true);
		redarmor[2].setItemMeta(leathermeta);

		redarmor[3] = new ItemStack(Material.LEATHER_BOOTS, 1);
		leathermeta = (LeatherArmorMeta) redarmor[3].getItemMeta();
		leathermeta.setColor(Color.RED);
		leathermeta.addEnchant(Enchantment.DURABILITY, 100, true);
		redarmor[3].setItemMeta(leathermeta);

		
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
		
		helpitem = new ItemStack(347, 1);
		ItemMeta meta1 = helpitem.getItemMeta();
		meta1.setDisplayName("��f[ ��6���� ����� ��f]");
		lorelist.clear();
		lorelist.add("��f- ��7��Ŭ���� ���� ����� ���ϴ�.");
		meta1.setLore(lorelist);
		helpitem.setItemMeta(meta1);
		
		compassitem = new ItemStack(345, 1);
		meta1 = compassitem.getItemMeta();
		meta1.setDisplayName("��f[ ��6������ Ž���� ��f]");
		lorelist.clear();
		lorelist.add("��f- ��7��Ŭ���� Ž���� �������� ���ϴ�.");
		meta1.setLore(lorelist);
		compassitem.setItemMeta(meta1);
		
		jobitem = new ItemStack(339, 1);
		meta1 = jobitem.getItemMeta();
		meta1.setDisplayName("��f[ ��6���� ���� ��f]");
		lorelist.clear();
		lorelist.add("��f- ��7��Ŭ���� ���� ����â�� ���ϴ�.");
		lorelist.add("��f- ��7��ħ���� �ٴ÷� ��ġ�� Ž���մϴ�.");
		meta1.setLore(lorelist);
		jobitem.setItemMeta(meta1);
		
		shopitem = new ItemStack(340, 1);
		meta1 = shopitem.getItemMeta();
		meta1.setDisplayName("��f[ ��6��� ��f]");
		lorelist.clear();
		lorelist.add("��f- ��7��Ŭ���� ���� ����� ���ϴ�.");
		meta1.setLore(lorelist);
		shopitem.setItemMeta(meta1);
		
		Compassinven = Bukkit.createInventory(null, 9, "��c��l������ Ž����");
		
		item = new ItemStack(345, 1);
		ItemMeta meta = item.getItemMeta();
		meta.setDisplayName("��f[ ��c1�� ������ ��f]");
		lorelist.clear();
		lorelist.add("��f- ��7Ŭ���� ��ħ���� 1�� �������� ����ŵ�ϴ�.");
		meta.setLore(lorelist);
		item.setItemMeta(meta);
		Compassinven.setItem(1 ,item);
		
		item = new ItemStack(345, 1);
		 meta = item.getItemMeta();
		meta.setDisplayName("��f[ ��c2�� ������ ��f]");
		lorelist.clear();
		lorelist.add("��f- ��7Ŭ���� ��ħ���� 2�� �������� ����ŵ�ϴ�.");
		meta.setLore(lorelist);
		item.setItemMeta(meta);
		Compassinven.setItem(4 ,item);
		
		item = new ItemStack(345, 1);
		meta = item.getItemMeta();
		meta.setDisplayName("��f[ ��c3�� ������ ��f]");
		lorelist.clear();
		lorelist.add("��f- ��7Ŭ���� ��ħ���� 3�� �������� ����ŵ�ϴ�.");
		meta.setLore(lorelist);
		item.setItemMeta(meta);
		Compassinven.setItem(7 ,item);
		
		Utility = Bukkit.createInventory(null, 9, "��c��l���");
		item = new ItemStack(340, 1);
		meta = item.getItemMeta();
		meta.setDisplayName("��f[ ��c���� ���� ��f]");
		lorelist.clear();
		lorelist.add("��f- ��7Ŭ���� ���� ������ ���ϴ�.");
		meta.setLore(lorelist);
		item.setItemMeta(meta);
		Utility.setItem(1 ,item);
		
		item = new ItemStack(340, 1);
		 meta = item.getItemMeta();
		meta.setDisplayName("��f[ ��c���� ��f]");
		lorelist.clear();
		lorelist.add("��f- ��7Ŭ���� ������ ���ϴ�.");
		meta.setLore(lorelist);
		item.setItemMeta(meta);
		Utility.setItem(4 ,item);
		
		item = new ItemStack(340, 1);
		meta = item.getItemMeta();
		meta.setDisplayName("��f[ ��cƼ�� ��Ȳ ��f]");
		lorelist.clear();
		lorelist.add("��f- ��7Ŭ���� Ƽ����Ȳ�� ���ϴ�.");
		meta.setLore(lorelist);
		item.setItemMeta(meta);
		Utility.setItem(7 ,item);
		
		
		Shopinven = Bukkit.createInventory(null, 18, "��c��l����");
		
		item = new ItemStack(373, 1, (short) 8197);
		meta = item.getItemMeta();
		meta.setDisplayName("��f[ ��cȸ������ ��f]");
		lorelist.clear();
		lorelist.add("��f- ��7����: ��6Ȳ�ݴ�� 1��");
		meta.setLore(lorelist);
		item.setItemMeta(meta);
		Shopinven.setItem(0 ,item);
		
		item = new ItemStack(373, 1, (short) 8229);
		meta = item.getItemMeta();
		meta.setDisplayName("��f[ ��cȸ������2 ��f]");
		lorelist.clear();
		lorelist.add("��f- ��7����: ��6Ȳ�ݴ�� 2��");
		meta.setLore(lorelist);
		item.setItemMeta(meta);
		Shopinven.setItem(2 ,item);
		
		item = new ItemStack(373, 1, (short) 16389);
		meta = item.getItemMeta();
		meta.setDisplayName("��f[ ��c��ô�� ȸ������ ��f]");
		lorelist.clear();
		lorelist.add("��f- ��7����: ��6Ȳ�ݴ�� 2��");
		meta.setLore(lorelist);
		item.setItemMeta(meta);
		Shopinven.setItem(4 ,item);
		
		item = new ItemStack(373, 1, (short) 16421);
		meta = item.getItemMeta();
		meta.setDisplayName("��f[ ��c��ô�� ȸ������2 ��f]");
		lorelist.clear();
		lorelist.add("��f- ��7����: ��6Ȳ�ݴ�� 4��");
		meta.setLore(lorelist);
		item.setItemMeta(meta);
		Shopinven.setItem(6 ,item);
		
		item = new ItemStack(387, 1);
		meta = item.getItemMeta();
		meta.setDisplayName("��f[ ��c�ñر� ��f]");
		lorelist.clear();
		lorelist.add("��f- ��7����: ��6Ȳ�ݴ�� 24��");
		meta.setLore(lorelist);
		item.setItemMeta(meta);
		Shopinven.setItem(8 ,item);
		
		item = new ItemStack(387, 1, (short) 8197);
		meta = item.getItemMeta();
		meta.setDisplayName("��f[ ��c�нú� ��f]");
		lorelist.clear();
		lorelist.add("��f- ��7����: ��6Ȳ�ݴ�� 12��");
		meta.setLore(lorelist);
		item.setItemMeta(meta);
		Shopinven.setItem(9 ,item);
		
		item = new ItemStack(298, 1);
		meta = item.getItemMeta();
		meta.setDisplayName("��f[ ��c���� ��ȭ (����) ��f]");
		lorelist.clear();
		lorelist.add("��f- ��7����: ��6Ȳ�ݴ�� 7��");
		meta.setLore(lorelist);
		item.setItemMeta(meta);
		Shopinven.setItem(11 ,item);
		
		item = new ItemStack(299, 1);
		meta = item.getItemMeta();
		meta.setDisplayName("��f[ ��c���� ��ȭ (����) ��f]");
		lorelist.clear();
		lorelist.add("��f- ��7����: ��6Ȳ�ݴ�� 11��");
		meta.setLore(lorelist);
		item.setItemMeta(meta);
		Shopinven.setItem(13 ,item);
		
		item = new ItemStack(300, 1);
		meta = item.getItemMeta();
		meta.setDisplayName("��f[ ��c���� ��ȭ (����) ��f]");
		lorelist.clear();
		lorelist.add("��f- ��7����: ��6Ȳ�ݴ�� 9��");
		meta.setLore(lorelist);
		item.setItemMeta(meta);
		Shopinven.setItem(15 ,item);
		
		item = new ItemStack(301, 1);
		meta = item.getItemMeta();
		meta.setDisplayName("��f[ ��c���� ��ȭ (�Ź�) ��f]");
		lorelist.clear();
		lorelist.add("��f- ��7����: ��6Ȳ�ݴ�� 7��");
		meta.setLore(lorelist);
		item.setItemMeta(meta);
		Shopinven.setItem(17 ,item);
		
		JobSelect = Bukkit.createInventory(null, 45, "��c��l���� ����");
		
		item = new ItemStack(34, 1);
		meta2 = item.getItemMeta();
		meta2.setDisplayName("��6���");
		item.setItemMeta(meta2);
		for(int i = 0; i <= 44; i+=9){
			JobSelect.setItem(i, item);
		}
		for(int i = 8; i <= 44; i+=9){
			JobSelect.setItem(i, item);
		}
		
		item = new ItemStack(276, 1);
		meta = item.getItemMeta();
		meta.setDisplayName("��f[ ��b�ϻ��� ��f]");
		lorelist.clear();
		lorelist.add("��f- ��7���ݷ� : ��67~9");
		lorelist.add("��f- ��7������ ���� : ��6���� ������ x 2");
		lorelist.add("��f- ��7���� : ��6�ڡڡڡڡ�");
		lorelist.add("��f- ��7�⵿ : ��6�ڡ١١١�");
		lorelist.add("��f- ��7���� : ��6�١١١١�");
		meta.setLore(lorelist);
		item.setItemMeta(meta);
		JobSelect.setItem(1 ,item);
		
		item = new ItemStack(318, 1);
		meta = item.getItemMeta();
		meta.setDisplayName("��f[ ��b�չ� ��f]");
		lorelist.clear();
		lorelist.add("��f- ��7���ݷ� : ��63~4");
		lorelist.add("��f- ��7������ ���� : ��6���� ������ x 1");
		lorelist.add("��f- ��7���� : ��6�ڡڡڡڡ�");
		lorelist.add("��f- ��7�⵿ : ��6�١١١١�");
		lorelist.add("��f- ��7���� : ��6�ڡ١١١�");
		meta.setLore(lorelist);
		item.setItemMeta(meta);
		JobSelect.setItem(3 ,item);
		
		item = new ItemStack(351, 1, (byte) 4);
		meta = item.getItemMeta();
		meta.setDisplayName("��f[ ��b���Ŀ ��f]");
		lorelist.clear();
		lorelist.add("��f- ��7���ݷ� : ��64~6");
		lorelist.add("��f- ��7������ ���� : ��6���� ������ x 1");
		lorelist.add("��f- ��7���� : ��6�ڡڡ١١�");
		lorelist.add("��f- ��7�⵿ : ��6�١١١١�");
		lorelist.add("��f- ��7���� : ��6�ڡڡڡڡ�");
		meta.setLore(lorelist);
		item.setItemMeta(meta);
		JobSelect.setItem(5 ,item);
		
		item = new ItemStack(351, 1, (byte) 2);
		meta = item.getItemMeta();
		meta.setDisplayName("��f[ ��b���� ������ ��f]");
		lorelist.clear();
		lorelist.add("��f- ��7���ݷ� : ��64~7");
		lorelist.add("��f- ��7������ ���� : ��6���� ������ x 1");
		lorelist.add("��f- ��7���� : ��6�ڡڡ١١�");
		lorelist.add("��f- ��7�⵿ : ��6�١١١١�");
		lorelist.add("��f- ��7���� : ��6�ڡڡڡڡ�");
		meta.setLore(lorelist);
		item.setItemMeta(meta);
		JobSelect.setItem(7 ,item);
		
		item = new ItemStack(261, 1);
		meta = item.getItemMeta();
		meta.setDisplayName("��f[ ��b���� ������ ��f]");
		lorelist.clear();
		lorelist.add("��f- ��7���ݷ� : ��61~10(������ ��丸ŭ)");
		lorelist.add("��f- ��7������ ���� : ��6���� ������ x 1��");
		lorelist.add("��f- ��7���� : ��6�ڡڡڡڡ�");
		lorelist.add("��f- ��7�⵿ : ��6�١١١١�");
		lorelist.add("��f- ��7���� : ��6�ڡڡ١١�");
		meta.setLore(lorelist);
		item.setItemMeta(meta);
		JobSelect.setItem(10 ,item);
		
		item = new ItemStack(257, 1);
		meta = item.getItemMeta();
		meta.setDisplayName("��f[ ��b�����Ͼ� ��f]");
		lorelist.clear();
		lorelist.add("��f- ��7���ݷ� : ��64~6");
		lorelist.add("��f- ��7������ ���� : ��6���� ������ x 0.8");
		lorelist.add("��f- ��7���� : ��6�ڡڡڡ١�");
		lorelist.add("��f- ��7�⵿ : ��6�ڡڡڡڡ�");
		lorelist.add("��f- ��7���� : ��6�١١١١�");
		meta.setLore(lorelist);
		item.setItemMeta(meta);
		JobSelect.setItem(12 ,item);
		
		item = new ItemStack(267, 1);
		meta = item.getItemMeta();
		meta.setDisplayName("��f[ ��b��� ��f]");
		lorelist.clear();
		lorelist.add("��f- ��7���ݷ� : ��65~7");
		lorelist.add("��f- ��7������ ���� : ��6���� ������ x 0.75");
		lorelist.add("��f- ��7���� : ��6�ڡڡڡ١�");
		lorelist.add("��f- ��7�⵿ : ��6�ڡڡڡڡ�");
		lorelist.add("��f- ��7���� : ��6�ڡ١١١�");
		meta.setLore(lorelist);
		item.setItemMeta(meta);
		JobSelect.setItem(14 ,item);
		
		item = new ItemStack(296, 1);
		meta = item.getItemMeta();
		meta.setDisplayName("��f[ ��b��Ż ��f]");
		lorelist.clear();
		lorelist.add("��f- ��7���ݷ� : ��63~5");
		lorelist.add("��f- ��7������ ���� : ��6���� ������ x 0.8");
		lorelist.add("��f- ��7���� : ��6�ڡ١١١�");
		lorelist.add("��f- ��7�⵿ : ��6�ڡڡڡڡ�");
		lorelist.add("��f- ��7���� : ��6�ڡڡڡڡ�");
		meta.setLore(lorelist);
		item.setItemMeta(meta);
		JobSelect.setItem(16 ,item);
		
		item = new ItemStack(276, 1);
		meta = item.getItemMeta();
		meta.setDisplayName("��f[ ��b���� ��f]");
		lorelist.clear();
		lorelist.add("��f- ��7���ݷ� : ��67");
		lorelist.add("��f- ��7������ ���� : ��6���� ������ x 1.25");
		lorelist.add("��f- ��7���� : ��6�ڡڡڡڡ�");
		lorelist.add("��f- ��7�⵿ : ��6�ڡڡڡ١�");
		lorelist.add("��f- ��7���� : ��6�١١١١�");
		meta.setLore(lorelist);
		item.setItemMeta(meta);
		JobSelect.setItem(19 ,item);
		
		item = new ItemStack(337, 1);
		meta = item.getItemMeta();
		meta.setDisplayName("��f[ ��b������ ��f]");
		lorelist.clear();
		lorelist.add("��f- ��7���ݷ� : ��63~11");
		lorelist.add("��f- ��7������ ���� : ��6���� ������ x 1");
		lorelist.add("��f- ��7���� : ��6�ڡڡڡڡ�");
		lorelist.add("��f- ��7�⵿ : ��6�ڡڡ١١�");
		lorelist.add("��f- ��7���� : ��6�١١١١�");
		meta.setLore(lorelist);
		item.setItemMeta(meta);
		JobSelect.setItem(21 ,item);
		
		item = new ItemStack(272, 1);
		meta = item.getItemMeta();
		meta.setDisplayName("��f[ ��b���� ��f]");
		lorelist.clear();
		lorelist.add("��f- ��7���ݷ� : ��65");
		lorelist.add("��f- ��7������ ���� : ��6���� ������ x 0.66��");
		lorelist.add("��f- ��7���� : ��6�ڡڡ١١�");
		lorelist.add("��f- ��7�⵿ : ��6�ڡ١١١�");
		lorelist.add("��f- ��7���� : ��6�ڡڡڡڡ�");
		meta.setLore(lorelist);
		item.setItemMeta(meta);
		JobSelect.setItem(23 ,item);
		
		item = new ItemStack(369, 1);
		meta = item.getItemMeta();
		meta.setDisplayName("��f[ ��b���� ��f]");
		lorelist.clear();
		lorelist.add("��f- ��7���ݷ� : ��66");
		lorelist.add("��f- ��7������ ���� : ��6���� ������ x 1");
		lorelist.add("��f- ��7���� : ��6�ڡڡڡڡ�");
		lorelist.add("��f- ��7�⵿ : ��6�١١١١�");
		lorelist.add("��f- ��7���� : ��6�ڡڡڡ١�");
		meta.setLore(lorelist);
		item.setItemMeta(meta);
		JobSelect.setItem(25 ,item);
		
		item = new ItemStack(286, 1);
		meta = item.getItemMeta();
		meta.setDisplayName("��f[ ��b�нú� ������ ��f]");
		lorelist.clear();
		lorelist.add("��f- ��7���ݷ� : ��66");
		lorelist.add("��f- ��7������ ���� : ��6���� ������ x 1.25");
		lorelist.add("��f- ��7���� : ��6�ڡڡڡ١�");
		lorelist.add("��f- ��7�⵿ : ��6�ڡڡڡڡ�");
		lorelist.add("��f- ��7���� : ��6�١١١١�");
		meta.setLore(lorelist);
		item.setItemMeta(meta);
		JobSelect.setItem(28 ,item);
		
		item = new ItemStack(267, 1);
		meta = item.getItemMeta();
		meta.setDisplayName("��f[ ��bƮ���̼� ��f]");
		lorelist.clear();
		lorelist.add("��f- ��7���ݷ� : ��64~6");
		lorelist.add("��f- ��7������ ���� : ��6���� ������ x 1.43��");
		lorelist.add("��f- ��7���� : ��6�ڡڡ١١�");
		lorelist.add("��f- ��7�⵿ : ��6�ڡڡڡڡ�");
		lorelist.add("��f- ��7���� : ��6�١١١١�");
		meta.setLore(lorelist);
		item.setItemMeta(meta);
		JobSelect.setItem(30, item);
		
		item = new ItemStack(338, 1);
		meta = item.getItemMeta();
		meta.setDisplayName("��f[ ��b�Ӷ��� ��f]");
		lorelist.clear();
		lorelist.add("��f- ��7���ݷ� : ��66~8");
		lorelist.add("��f- ��7������ ���� : ��6���� ������ x 1");
		lorelist.add("��f- ��7���� : ��6�ڡڡڡڡ�");
		lorelist.add("��f- ��7�⵿ : ��6�١١١١�");
		lorelist.add("��f- ��7���� : ��6�ڡڡڡ١�");
		meta.setLore(lorelist);
		item.setItemMeta(meta);
		JobSelect.setItem(32, item);
		
		item = new ItemStack(280, 1);
		meta = item.getItemMeta();
		meta.setDisplayName("��f[ ��b���� ��f]");
		lorelist.clear();
		lorelist.add("��f- ��7���ݷ� : ��64");
		lorelist.add("��f- ��7������ ���� : ��6���� ������ x 1��");
		lorelist.add("��f- ��7���� : ��6�ڡ١١١�");
		lorelist.add("��f- ��7�⵿ : ��6�١١١١�");
		lorelist.add("��f- ��7���� : ��6�ڡڡڡڡڡڡ�");
		meta.setLore(lorelist);
		item.setItemMeta(meta);
		JobSelect.setItem(34, item);
		
		MobSystem.bluejob = Bukkit.createInventory(null, 45, "��c��l���� ����");
		
		MobSystem.redjob = Bukkit.createInventory(null, 45, "��c��l���� ����");
		
		Loadconfig();
		
		getLogger().info("���� ���� ��Ʋ3 �÷������� �ε� �Ǿ����ϴ�.");
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
	
	public void Loadconfig()
	{
	  try {
		joinpos = new Location(Bukkit.getWorld(getConfig().getString("Join_world")), getConfig().getInt("Join_x"), getConfig().getInt("Join_y"), getConfig().getInt("Join_z"));
	    Lobby = new Location(Bukkit.getWorld(getConfig().getString("Lobby_world")), getConfig().getInt("Lobby_x"), getConfig().getInt("Lobby_y"), getConfig().getInt("Lobby_z"));
	  } catch (IllegalArgumentException e){
	    getLogger().info("���� ���� �Ǵ� �κ� �����Ǿ� ���� �ʽ��ϴ�");
	  }
	  try {
		Redspawn = new Location(Bukkit.getWorld(getConfig().getString("Redspawn_world")), getConfig().getInt("Redspawn_x"), getConfig().getInt("Redspawn_y"), getConfig().getInt("Redspawn_z"));
	    Bluespawn = new Location(Bukkit.getWorld(getConfig().getString("Bluespawn_world")), getConfig().getInt("Bluespawn_x"), getConfig().getInt("Bluespawn_y"), getConfig().getInt("Bluespawn_z"));
	  }catch (IllegalArgumentException e){
	    getLogger().info("������ �Ǵ� ����� ������ �����Ǿ� ���� �ʽ��ϴ�.");
	  }
	  try {
		Redj = new Location(Bukkit.getWorld(getConfig().getString("Redj_world")), getConfig().getInt("Redj_x"), getConfig().getInt("Redj_y"), getConfig().getInt("Redj_z"));
	    Bluej = new Location(Bukkit.getWorld(getConfig().getString("Bluej_world")), getConfig().getInt("Bluej_x"), getConfig().getInt("Bluej_y"), getConfig().getInt("Bluej_z"));
	  }catch (IllegalArgumentException e){
	    getLogger().info("������ ���� �Ǵ� ����� ���� �������� �����Ǿ� ���� �ʽ��ϴ�.");
	  }
	  try {
		Reddeath = new Location(Bukkit.getWorld(getConfig().getString("Reddeath_world")), getConfig().getInt("Reddeath_x"), getConfig().getInt("Reddeath_y"), getConfig().getInt("Reddeath_z"));
	    Bluedeath = new Location(Bukkit.getWorld(getConfig().getString("Bluedeath_world")), getConfig().getInt("Bluedeath_x"), getConfig().getInt("Bluedeath_y"), getConfig().getInt("Bluedeath_z"));
	  }catch (IllegalArgumentException e){
	    getLogger().info("������ �������Ʈ �Ǵ� ����� �������Ʈ ������ �����Ǿ� ���� �ʽ��ϴ�.");
	  }
	  for(int i = 0; i < 3; i++){
		  try {
			    Core[i] = new Location(Bukkit.getWorld(getConfig().getString("Core_world"+(i+1))), getConfig().getInt("Core_x"+(i+1)), getConfig().getInt("Core_y"+(i+1)), getConfig().getInt("Core_z"+(i+1)));
			  }catch (IllegalArgumentException e){
			    getLogger().info(i+" ��° �ھ �����Ǿ� ���� �ʽ��ϴ�.");
			  }
	  }
	}
    
	public void onDisable(){
		getLogger().info("���� ���� ��Ʋ3 �÷������� �ε� �Ǿ����ϴ�.");
	}
	
	public boolean onCommand(CommandSender talker, Command command, String str, String[] args){
		if(talker instanceof Player){
			Player p = (Player) talker;
			if(str.equalsIgnoreCase("mob") && p.isOp()){
				if(args.length <= 0){
					HelpMessages(p);
				}
				else{
					Execute(p, args);
				}
			}
		}
		return true;
	}
	
	public void HelpMessages(Player p){
		p.sendMessage(MS+"/mob join - ��������");
		p.sendMessage(MS+"/mob quit - ��������");
		p.sendMessage(MS+"/mob stop - ��������");
		p.sendMessage(MS+"/mob set - ���Ӽ���");
	}
	
	public void Execute(Player p, String[] args){
		if(args[0].equalsIgnoreCase("set")){
			if(args.length <= 1){
				p.sendMessage(MS+"/mob set lobby");
				p.sendMessage(MS+"/mob set join");
				p.sendMessage(MS+"/mob set blue");
				p.sendMessage(MS+"/mob set red");
				p.sendMessage(MS+"/mob set core1");
				p.sendMessage(MS+"/mob set core2");
				p.sendMessage(MS+"/mob set core3");
				p.sendMessage(MS+"/mob set core4");
				p.sendMessage(MS+"/mob set core5");
			} else if(args[1].equalsIgnoreCase("lobby")){
			    getConfig().set("Lobby_world", p.getLocation().getWorld().getName());
			    getConfig().set("Lobby_x", Integer.valueOf(p.getLocation().getBlockX()));
			    getConfig().set("Lobby_y", Integer.valueOf(p.getLocation().getBlockY() + 1));
			    getConfig().set("Lobby_z", Integer.valueOf(p.getLocation().getBlockZ()));
			    saveConfig();
			    Loadconfig();
				p.sendMessage(MS+"�κ� ���� �Ϸ�");
			} else if(args[1].equalsIgnoreCase("join")){
			    getConfig().set("Join_world", p.getLocation().getWorld().getName());
			    getConfig().set("Join_x", Integer.valueOf(p.getLocation().getBlockX()));
			    getConfig().set("Join_y", Integer.valueOf(p.getLocation().getBlockY() + 1));
			    getConfig().set("Join_z", Integer.valueOf(p.getLocation().getBlockZ()));
			    saveConfig();
			    Loadconfig();
				p.sendMessage(MS+"���� ���� �Ϸ�");
			} else if(args[1].equalsIgnoreCase("core1")){
				int i = 1;
			    getConfig().set("Core_world"+i, p.getLocation().getWorld().getName());
			    getConfig().set("Core_x"+i, Integer.valueOf(p.getLocation().getBlockX()));
			    getConfig().set("Core_y"+i, Integer.valueOf(p.getLocation().getBlockY() - 1));
			    getConfig().set("Core_z"+i, Integer.valueOf(p.getLocation().getBlockZ()));
			    saveConfig();
			    Loadconfig();
				p.sendMessage(MS+"�ھ�1 �����Ϸ�");
			} else if(args[1].equalsIgnoreCase("core2")){
				int i = 2;
			    getConfig().set("Core_world"+i, p.getLocation().getWorld().getName());
			    getConfig().set("Core_x"+i, Integer.valueOf(p.getLocation().getBlockX()));
			    getConfig().set("Core_y"+i, Integer.valueOf(p.getLocation().getBlockY() - 1));
			    getConfig().set("Core_z"+i, Integer.valueOf(p.getLocation().getBlockZ()));
			    saveConfig();
			    Loadconfig();
				p.sendMessage(MS+"�ھ�2 �����Ϸ�");
			} else if(args[1].equalsIgnoreCase("core3")){
				int i = 3;
			    getConfig().set("Core_world"+i, p.getLocation().getWorld().getName());
			    getConfig().set("Core_x"+i, Integer.valueOf(p.getLocation().getBlockX()));
			    getConfig().set("Core_y"+i, Integer.valueOf(p.getLocation().getBlockY() - 1));
			    getConfig().set("Core_z"+i, Integer.valueOf(p.getLocation().getBlockZ()));
			    saveConfig();
			    Loadconfig();
				p.sendMessage(MS+"�ھ�3 �����Ϸ�");
			} 
			else if(args[1].equalsIgnoreCase("redj")){
			    getConfig().set("Redj_world", p.getLocation().getWorld().getName());
			    getConfig().set("Redj_x", Integer.valueOf(p.getLocation().getBlockX()));
			    getConfig().set("Redj_y", Integer.valueOf(p.getLocation().getBlockY() + 1));
			    getConfig().set("Redj_z", Integer.valueOf(p.getLocation().getBlockZ()));
			    saveConfig();
			    Loadconfig();
				p.sendMessage(MS+"���� �������� �����Ϸ�");
			} 
			else if(args[1].equalsIgnoreCase("bluej")){
			    getConfig().set("Bluej_world", p.getLocation().getWorld().getName());
			    getConfig().set("Bluej_x", Integer.valueOf(p.getLocation().getBlockX()));
			    getConfig().set("Bluej_y", Integer.valueOf(p.getLocation().getBlockY() + 1));
			    getConfig().set("Bluej_z", Integer.valueOf(p.getLocation().getBlockZ()));
			    saveConfig();
			    Loadconfig();
				p.sendMessage(MS+"��� �������� �����Ϸ�");
			} 
			else if(args[1].equalsIgnoreCase("redd")){
			    getConfig().set("Reddeath_world", p.getLocation().getWorld().getName());
			    getConfig().set("Reddeath_x", Integer.valueOf(p.getLocation().getBlockX()));
			    getConfig().set("Reddeath_y", Integer.valueOf(p.getLocation().getBlockY() + 1));
			    getConfig().set("Reddeath_z", Integer.valueOf(p.getLocation().getBlockZ()));
			    saveConfig();
			    Loadconfig();
				p.sendMessage(MS+"���� ������� �����Ϸ�");
			} 
			else if(args[1].equalsIgnoreCase("blued")){
			    getConfig().set("Bluedeath_world", p.getLocation().getWorld().getName());
			    getConfig().set("Bluedeath_x", Integer.valueOf(p.getLocation().getBlockX()));
			    getConfig().set("Bluedeath_y", Integer.valueOf(p.getLocation().getBlockY() + 1));
			    getConfig().set("Bluedeath_z", Integer.valueOf(p.getLocation().getBlockZ()));
			    saveConfig();
			    Loadconfig();
				p.sendMessage(MS+"��� ������� �����Ϸ�");
			} 
			else if(args[1].equalsIgnoreCase("reds")){
			    getConfig().set("Redspawn_world", p.getLocation().getWorld().getName());
			    getConfig().set("Redspawn_x", Integer.valueOf(p.getLocation().getBlockX()));
			    getConfig().set("Redspawn_y", Integer.valueOf(p.getLocation().getBlockY() + 1));
			    getConfig().set("Redspawn_z", Integer.valueOf(p.getLocation().getBlockZ()));
			    saveConfig();
			    Loadconfig();
				p.sendMessage(MS+"���� �������� �����Ϸ�");
			} 
			else if(args[1].equalsIgnoreCase("blues")){
			    getConfig().set("Bluespawn_world", p.getLocation().getWorld().getName());
			    getConfig().set("Bluespawn_x", Integer.valueOf(p.getLocation().getBlockX()));
			    getConfig().set("Bluespawn_y", Integer.valueOf(p.getLocation().getBlockY() + 1));
			    getConfig().set("Bluespawn_z", Integer.valueOf(p.getLocation().getBlockZ()));
			    saveConfig();
			    Loadconfig();
				p.sendMessage(MS+"��� �������� �����Ϸ�");
			} 
		} else if(args[0].equalsIgnoreCase("join")){
			MobSystem.GameJoin(p);
		} else if(args[0].equalsIgnoreCase("quit")){
			MobSystem.GameQuit(p);
		} else if(args[0].equalsIgnoreCase("stop")){
			MobSystem.ForceStop();
		} else if(args[0].equalsIgnoreCase("block")){
			MobSystem.join_block = true;
			p.sendMessage(Main.MS+"�Ϸ�");
		}else if(args[0].equalsIgnoreCase("test")){
			switch(Integer.valueOf(args[1])){
			case -3: DonutEffect de = new DonutEffect(effectManager);
			de.setEntity(p);
			de.start();
			break;
			case -4: Cooldown.cooldownlist.clear(); break;
			case -2: MobSystem.bluelist.remove(p); MobSystem.redlist.add(p); p.sendMessage("���弳��"); break;
			case -1: MobSystem.redlist.remove(p); MobSystem.bluelist.add(p); p.sendMessage("��缳��"); break;
			case 0: MobSystem.ablist.get(p.getName()).description(); break;
			case 1: MobSystem.RemoveAbility(p); MobSystem.ablist.put(p.getName(), new BowMaster(p.getName(), p)); break;
			case 2: MobSystem.RemoveAbility(p); MobSystem.ablist.put(p.getName(), new Tracer(p.getName(), p)); break;
			case 3: MobSystem.RemoveAbility(p); MobSystem.ablist.put(p.getName(), new Ninja(p.getName(), p)); break;
			case 4: MobSystem.RemoveAbility(p); MobSystem.ablist.put(p.getName(), new Assasin(p.getName(), p)); break;
			case 5: MobSystem.RemoveAbility(p); MobSystem.ablist.put(p.getName(), new Shielder(p.getName(), p)); break;
			case 6: MobSystem.RemoveAbility(p); MobSystem.ablist.put(p.getName(), new Engineer(p.getName(), p)); break;
			case 7: MobSystem.RemoveAbility(p); MobSystem.ablist.put(p.getName(), new Portal(p.getName(), p)); break;
			case 8: MobSystem.RemoveAbility(p); MobSystem.ablist.put(p.getName(), new Witch(p.getName(), p)); break;
			case 9: MobSystem.RemoveAbility(p); MobSystem.ablist.put(p.getName(), new PassiveMaster(p.getName(), p)); break;
			case 10: MobSystem.RemoveAbility(p); MobSystem.ablist.put(p.getName(), new Bomber(p.getName(), p)); break;
			case 11: MobSystem.RemoveAbility(p); MobSystem.ablist.put(p.getName(), new Randomer(p.getName(), p)); break;
			case 12: MobSystem.RemoveAbility(p); MobSystem.ablist.put(p.getName(), new Knight(p.getName(), p)); break;
			case 13: MobSystem.RemoveAbility(p); MobSystem.ablist.put(p.getName(), new AreaCreator(p.getName(), p)); break;
			case 14: MobSystem.RemoveAbility(p); MobSystem.ablist.put(p.getName(), new Blocker(p.getName(), p)); break;
			case 15: MobSystem.RemoveAbility(p); MobSystem.ablist.put(p.getName(), new Imrapu(p.getName(), p)); break;
			case 16: MobSystem.RemoveAbility(p); MobSystem.ablist.put(p.getName(), new Priest(p.getName(), p)); break;
			
			
			default: return;
			}
			MobSystem.ablist.get(p.getName()).can_Ultimate = true;
			MobSystem.ablist.get(p.getName()).can_passive = true;
		}else if(args[0].equalsIgnoreCase("task")){
			Bukkit.getScheduler().scheduleSyncRepeatingTask(instance, new Runnable(){
				public void run(){
					Bukkit.broadcastMessage(Main.MS+"��");
				}
			}, 0l, 20);
		}else if(args[0].equalsIgnoreCase("cancel")){
			Bukkit.getScheduler().cancelTasks(instance);
		}
	}
	
	@EventHandler
	public void PlayerItemHeld(PlayerItemHeldEvent e){
		if(MobSystem.ablist.containsKey(e.getPlayer().getName())){
			Ability ability = MobSystem.ablist.get(e.getPlayer().getName());
			if(MobSystem.skill_block){
				e.getPlayer().sendMessage(Main.MS+"����߿��� ��ų�� ����Ͻ� �� �����ϴ�.");
				return;
			}
			if(MobSystem.bluedead.containsValue(e.getPlayer()) || MobSystem.reddead.containsValue(e.getPlayer())){
				e.getPlayer().sendMessage(Main.MS+"��Ȱ ����߿��� ��ų�� ����Ͻ� �� �����ϴ�.");
				return;
			}
			if(ability.cc){
				e.getPlayer().sendMessage(Main.MS+"ħ�� ���¿����� ��ų�� ����Ͻ� �� �����ϴ�.");
				e.getPlayer().getInventory().setHeldItemSlot(0);
				return;
			}else{
				ability.Active(e);
			}
		}
	}
	
	@EventHandler
	public void onPlayercommand(PlayerCommandPreprocessEvent e)
	{
		Player p = e.getPlayer();
		if(MobSystem.plist.contains(p) && e.getMessage().equalsIgnoreCase("/����") || e.getMessage().equalsIgnoreCase("/spawn")
				|| e.getMessage().equalsIgnoreCase("/����") || e.getMessage().equalsIgnoreCase("/����"))
		{
			MobSystem.GameQuit(p);
		}
	}
	
	  @EventHandler
	  public void onQuitPlayer(PlayerQuitEvent e) {
	    if (MobSystem.plist.contains(e.getPlayer()))
	    {
	      MobSystem.GameQuit(e.getPlayer());
	    }
	  }
	
	@EventHandler
	public void Passive(BlockBreakEvent e){
		Player p = e.getPlayer();
		if(!MobSystem.plist.contains(p)) return;
		if(MobSystem.ablist.containsKey(p.getName())){
			Ability ability = MobSystem.ablist.get(p.getName());
			ability.onBlockBreak(e);
		}else{
			e.setCancelled(true);
		}
	}
	
	@EventHandler
	public void Passive(PlayerDeathEvent e){
		Player p = e.getEntity();
		if(!MobSystem.plist.contains(p)) return;
		MobSystem.iteminven.put(p.getName(), p.getInventory().getContents());
		MobSystem.armorinven.put(p.getName(), p.getInventory().getArmorContents());
		e.getDrops().clear();
		e.setDroppedExp(0);
		if(MobSystem.ablist.containsKey(p.getName())){
			Ability ability = MobSystem.ablist.get(p.getName());
			ability.onDeath(e);
		}
		if(p.getKiller() instanceof Player){
			Player k = (Player) p.getKiller();
			if(MobSystem.ablist.containsKey(k.getName())){
				Ability ability2 = MobSystem.ablist.get(k.getName());
				ability2.onKill(e);
			}
		}
	}
	
	@EventHandler
	public void Passive(EntityRegainHealthEvent e){
		if(!(e.getEntity() instanceof Player)) return;
		if(!MobSystem.plist.contains((Player)e.getEntity())) return;
		if(e.getRegainReason() == RegainReason.REGEN || e.getRegainReason() == RegainReason.SATIATED)
		e.setCancelled(true);
	}
	
	@EventHandler
	public void Passive(EntityDamageByEntityEvent e){
		Player p = null;
		Player d = null;
		if(e.getEntity() instanceof Player){
			p = (Player) e.getEntity();
		}
		if(e.getDamager() instanceof Arrow){
			Arrow arrow = (Arrow) e.getDamager();
			d = (Player) arrow.getShooter();
			if(!MobSystem.ablist.containsKey(d.getName())) return;
			Ability d_ablity = MobSystem.ablist.get(d.getName());
			if(d_ablity.abilityName.equalsIgnoreCase("���� ������")) d_ablity.onArrowHit(p);
		}
		if(e.getDamager() instanceof Player){
			d = (Player) e.getDamager();
		}
		if(p == null || d == null) return;
		if(!MobSystem.ablist.containsKey(p.getName()) || !MobSystem.ablist.containsKey(d.getName())) return;
		Ability p_ability = MobSystem.ablist.get(p.getName());
		Ability d_ability = MobSystem.ablist.get(d.getName());
		if(d_ability.ignore){
			d_ability.ignore = false;
		}else{
			d_ability.onHit(e);
		}
		p_ability.onHitDamaged(e);
	}
	
	@EventHandler
	public void Passive(EntityDamageEvent e){
		if(!(e.getEntity() instanceof Player)) return;
		Player p = (Player) e.getEntity();
		if(!MobSystem.plist.contains(p)) return;
		if(!MobSystem.check_start) {
			e.setCancelled(true);
			return;
		}
		if(MobSystem.ablist.containsKey(p.getName())){
			Ability ability = MobSystem.ablist.get(p.getName());
			ability.onDamaged(e);
		}
	}
	
	@EventHandler
	public void Passive(BlockPlaceEvent e){
		Player p = e.getPlayer();
		if(!MobSystem.plist.contains(p)) return;
		if(MobSystem.ablist.containsKey(p.getName())){
			Ability ability = MobSystem.ablist.get(p.getName());
			ability.onBlockPlace(e);
		}
		e.setCancelled(true);
	}
	
	@EventHandler
	public void Passive(PlayerRespawnEvent e){
		Player p = e.getPlayer();
		if(!MobSystem.plist.contains(p)) return;
		if(MobSystem.ablist.containsKey(p.getName())){
			Ability ability = MobSystem.ablist.get(p.getName());
			ability.onRespawn(e);
		}
	}
	
	@EventHandler
	public void Passive(PlayerMoveEvent e){
		Player p = e.getPlayer();
		if(!MobSystem.plist.contains(p)) return;
		if(MobSystem.ablist.containsKey(p.getName())){
			Ability ability = MobSystem.ablist.get(p.getName());
			ability.onMove(e);
		}
	}

	@EventHandler
	public void Passive(ProjectileLaunchEvent e){
		if(e.getEntity().getShooter() == null || !(e.getEntity().getShooter() instanceof Player)) return;
		Player p = (Player) e.getEntity().getShooter();
		if(!MobSystem.plist.contains(p)) return;
		if(MobSystem.ablist.containsKey(p.getName())){
			Ability ability = MobSystem.ablist.get(p.getName());
			ability.onLaunch(e);
		}
	}
	
	@EventHandler
	public void onChat(PlayerChatEvent e){
		Player p = e.getPlayer();
		if(!MobSystem.plist.contains(p)) return;
		if((!MobSystem.bluelist.contains(p) && !MobSystem.redlist.contains(p)) || !MobSystem.plist.contains(p)) return;
		e.setCancelled(true);
		if(MobSystem.bluelist.contains(p)){
			MobSystem.SendBluemessages("��f[ ��b��l�����ä�� ��f] ��6"+p.getName()+"��f: "+e.getMessage());
		} else {
			MobSystem.SendRedmessages("��f[ ��c��l������ä�� ��f] ��6"+p.getName()+"��f: "+e.getMessage());
		}
	}
	
	@EventHandler
	public void onRightClick(PlayerInteractEvent e){
		if(!MobSystem.plist.contains(e.getPlayer())) return;
		if(e.getPlayer().getItemInHand() == null) return;
		if(!MobSystem.ablist.containsKey(e.getPlayer().getName())){
			if(e.getPlayer().getItemInHand().getType() == Material.PAPER){
				e.getPlayer().openInventory(MobSystem.bluelist.contains(e.getPlayer()) ? MobSystem.bluejob : MobSystem.redjob);
			} 
		}
		if(e.getPlayer().getItemInHand().getType() == Material.COMPASS){
			e.getPlayer().openInventory(Compassinven);
		} else if(e.getPlayer().getItemInHand().getType() == Material.BOOK){
			e.getPlayer().openInventory(Utility);
		}
	}
	
	@EventHandler
	public void onDropItem(PlayerDropItemEvent e){
		if(!MobSystem.plist.contains(e.getPlayer())) return;
		if(e.getItemDrop().getItemStack().getType() != Material.GOLDEN_CARROT){
			e.setCancelled(true);
		}
	}
	
	@EventHandler
	public void onClickInventory(InventoryClickEvent e){
		if(!(e.getWhoClicked() instanceof Player)) return;
		Player p = (Player) e.getWhoClicked();
		if(!MobSystem.plist.contains(p)) return;
		if(e.getInventory().getTitle().equalsIgnoreCase("��c��l���� ����")){
			e.setCancelled(true);
			MobSystem.clickjob(p, e.getSlot());
			return;
		}
		if(e.getInventory().getTitle().equalsIgnoreCase("��c��l����")){
			e.setCancelled(true);
			MobSystem.clickshop(p, e.getSlot());
			return;
		}
		if(e.getInventory().getTitle().equalsIgnoreCase("��c��l���")){
			e.setCancelled(true);
			MobSystem.clickutility(p, e.getSlot());
			return;
		}
		if(e.getInventory().getTitle().equalsIgnoreCase("��c��l������ Ž����")){
			e.setCancelled(true);
			MobSystem.clickcompass(p, e.getSlot());
			return;
		}
		if(e.getCurrentItem() != null && 
				(e.getCurrentItem().getType() == Material.IRON_INGOT
				|| e.getCurrentItem().getType() == Material.DIAMOND
				|| e.getCurrentItem().getType() == Material.EMERALD
				|| e.getCurrentItem().getType() == Material.LEATHER_BOOTS
				|| e.getCurrentItem().getType() == Material.LEATHER_CHESTPLATE
				|| e.getCurrentItem().getType() == Material.LEATHER_LEGGINGS
				|| e.getCurrentItem().getType() == Material.LEATHER_HELMET)){
			p.sendMessage(Main.MS+"�� �������� �ű�� �� �����ϴ�.");
			e.setCancelled(true);
			p.updateInventory();
		}
	}
}
