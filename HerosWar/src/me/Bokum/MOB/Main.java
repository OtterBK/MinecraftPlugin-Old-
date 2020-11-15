package me.Bokum.MOB;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.Color;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Arrow;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Fireball;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.entity.EntityExplodeEvent;
import org.bukkit.event.entity.EntityRegainHealthEvent;
import org.bukkit.event.entity.ExplosionPrimeEvent;
import org.bukkit.event.entity.EntityRegainHealthEvent.RegainReason;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.event.entity.ProjectileLaunchEvent;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.player.PlayerChatEvent;
import org.bukkit.event.player.PlayerCommandPreprocessEvent;
import org.bukkit.event.player.PlayerDropItemEvent;
import org.bukkit.event.player.PlayerInteractEntityEvent;
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
import de.slikey.effectlib.effect.AnimatedBallEffect;
import de.slikey.effectlib.effect.AtomEffect;
import de.slikey.effectlib.effect.BigBangEffect;
import de.slikey.effectlib.effect.ColoredImageEffect;
import de.slikey.effectlib.effect.ConeEffect;
import de.slikey.effectlib.effect.DiscoBallEffect;
import de.slikey.effectlib.effect.DonutEffect;
import de.slikey.effectlib.effect.EarthEffect;
import de.slikey.effectlib.effect.ExplodeEffect;
import de.slikey.effectlib.effect.FountainEffect;
import de.slikey.effectlib.effect.GridEffect;
import de.slikey.effectlib.effect.HeartEffect;
import de.slikey.effectlib.effect.HelixEffect;
import de.slikey.effectlib.effect.HillEffect;
import de.slikey.effectlib.effect.IconEffect;
import de.slikey.effectlib.effect.JumpEffect;
import de.slikey.effectlib.effect.LoveEffect;
import de.slikey.effectlib.effect.MusicEffect;
import de.slikey.effectlib.effect.SkyRocketEffect;
import de.slikey.effectlib.effect.SmokeEffect;
import de.slikey.effectlib.effect.TextEffect;
import de.slikey.effectlib.effect.TornadoEffect;
import de.slikey.effectlib.effect.TraceEffect;
import de.slikey.effectlib.effect.VortexEffect;
import de.slikey.effectlib.effect.WaveEffect;
import me.Bokum.MOB.Ability.Ability;
import me.Bokum.MOB.Attacker.Assasin;
import me.Bokum.MOB.Attacker.BowMaster;
import me.Bokum.MOB.Attacker.FlameWizard;
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
	public static String MS = "§f[ §aEG §f] ";
	public static Location Lobby;
	public static Location joinpos;
	public static Location Redspawn;
	public static Location Bluespawn;
	public static Location Redj;
	public static Location Bluej;
	public static Location Reddeath;
	public static Location Bluedeath;
	public static Location Redtop;
	public static Location Bluetop;
	public static Location Core[] = new Location[5];
	public static ItemStack helpitem;
	public static ItemStack compassitem;
	public static Inventory Utility;
	public static Inventory gamehelper;
	public static Inventory JobSelect;
	public static Inventory shop_armor_inven;
	public static Inventory shop_skill_inven;
	public static Inventory shop_potion_inven;
	public static ItemStack jobitem;
	public static ItemStack utilitem;
	public static ItemStack bluearmor[] = new ItemStack[4];
	public static ItemStack redarmor[] = new ItemStack[4];
	public static Economy econ = null;
	public static Main instance;
    public static EffectManager effectManager;
    public static int Forcestoptimer = 0;
    public static List<Location> core1blockloc = new ArrayList<Location>();
    public static List<Location> core2blockloc = new ArrayList<Location>();
    
	public void onEnable(){
		getServer().getPluginManager().registerEvents(this, this);
		
        if (!setupEconomy() ) {
            getLogger().info("[버그 발생 우려 ] Vault플러그인이 인식되지 않았습니다!");
        }
        
        EffectLib lib = EffectLib.instance();
        effectManager = new EffectManager(lib);
        
		instance = this;
		
		gamehelper = Bukkit.createInventory(null, 27, "§c§l도우미");
		
		ItemStack item = new ItemStack(34, 1);
		ItemMeta meta2 = item.getItemMeta();
		meta2.setDisplayName("§6장식");
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
		
		ItemMeta meta;
		
		item = new ItemStack(Material.BOOK, 1);
		meta2 = item.getItemMeta();
		meta2.setDisplayName("§e플레이 방법");
		item.setItemMeta(meta2);
		gamehelper.setItem(11, item);
		
		item = new ItemStack(Material.BOOK, 1);
		meta2 = item.getItemMeta();
		meta2.setDisplayName("§e게임 규칙");
		item.setItemMeta(meta2);
		gamehelper.setItem(13, item);
		
		List<String> lorelist = new ArrayList<String>();
		
		helpitem = new ItemStack(347, 1);
		ItemMeta meta1 = helpitem.getItemMeta();
		meta1.setDisplayName("§f[ §6게임 도우미 §f]");
		lorelist.clear();
		lorelist.add("§f- §7우클릭시 게임 도움미 엽니다.");
		meta1.setLore(lorelist);
		helpitem.setItemMeta(meta1);
		
		compassitem = new ItemStack(345, 1);
		meta1 = compassitem.getItemMeta();
		meta1.setDisplayName("§f[ §6점령지 탐지기 §f]");
		lorelist.clear();
		lorelist.add("§f- §7우클릭시 탐지할 점령지를 고릅니다.");
		meta1.setLore(lorelist);
		compassitem.setItemMeta(meta1);
		
		jobitem = new ItemStack(339, 1);
		meta1 = jobitem.getItemMeta();
		meta1.setDisplayName("§f[ §6직업 선택 §f]");
		lorelist.clear();
		lorelist.add("§f- §7우클릭시 직업 선택창을 엽니다.");
		lorelist.add("§f- §7나침반의 바늘로 위치를 탐지합니다.");
		meta1.setLore(lorelist);
		jobitem.setItemMeta(meta1);
		
		utilitem = new ItemStack(340, 1);
		meta1 = utilitem.getItemMeta();
		meta1.setDisplayName("§f[ §6기능 §f]");
		lorelist.clear();
		lorelist.add("§f- §7우클릭시 각종 기능을 엽니다.");
		meta1.setLore(lorelist);
		utilitem.setItemMeta(meta1);
		
		Utility = Bukkit.createInventory(null, 9, "§c§l기능");
		item = new ItemStack(340, 1);
		meta = item.getItemMeta();
		meta.setDisplayName("§f[ §c직업 설명 §f]");
		lorelist.clear();
		lorelist.add("§f- §7클릭시 직업 설명을 봅니다.");
		meta.setLore(lorelist);
		item.setItemMeta(meta);
		Utility.setItem(1 ,item);
		
		item = new ItemStack(340, 1);
		 meta = item.getItemMeta();
		meta.setDisplayName("§f[ §c활성화까지 남은 당근 §f]");
		lorelist.clear();
		lorelist.add("§f- §7클릭시 점령지 활성화까지 남은 당근수를 알 수 있습니다.");
		meta.setLore(lorelist);
		item.setItemMeta(meta);
		Utility.setItem(4 ,item);
		
		item = new ItemStack(340, 1);
		meta = item.getItemMeta();
		meta.setDisplayName("§f[ §c티겟 현황 §f]");
		lorelist.clear();
		lorelist.add("§f- §7클릭시 티켓현황을 봅니다.");
		meta.setLore(lorelist);
		item.setItemMeta(meta);
		Utility.setItem(7 ,item);
		
		
		shop_potion_inven = Bukkit.createInventory(null, 9, "§c§l포션 상점");
		shop_skill_inven = Bukkit.createInventory(null, 9, "§c§l스킬 상점");
		shop_armor_inven = Bukkit.createInventory(null, 9, "§c§l갑옷 상점");
		
		item = new ItemStack(373, 1, (short) 8197);
		meta = item.getItemMeta();
		meta.setDisplayName("§f[ §c회복포션 §f]");
		lorelist.clear();
		lorelist.add("§f- §7가격: §6황금당근 1개");
		meta.setLore(lorelist);
		item.setItemMeta(meta);
		shop_potion_inven.setItem(0 ,item);
		
		item = new ItemStack(373, 1, (short) 8229);
		meta = item.getItemMeta();
		meta.setDisplayName("§f[ §c회복포션2 §f]");
		lorelist.clear();
		lorelist.add("§f- §7가격: §6황금당근 2개");
		meta.setLore(lorelist);
		item.setItemMeta(meta);
		shop_potion_inven.setItem(2 ,item);
		
		item = new ItemStack(373, 1, (short) 16389);
		meta = item.getItemMeta();
		meta.setDisplayName("§f[ §c투척용 회복포션 §f]");
		lorelist.clear();
		lorelist.add("§f- §7가격: §6황금당근 2개");
		meta.setLore(lorelist);
		item.setItemMeta(meta);
		shop_potion_inven.setItem(4 ,item);
		
		item = new ItemStack(373, 1, (short) 16421);
		meta = item.getItemMeta();
		meta.setDisplayName("§f[ §c투척용 회복포션2 §f]");
		lorelist.clear();
		lorelist.add("§f- §7가격: §6황금당근 4개");
		meta.setLore(lorelist);
		item.setItemMeta(meta);
		shop_potion_inven.setItem(6 ,item);
		
		item = new ItemStack(387, 1);
		meta = item.getItemMeta();
		meta.setDisplayName("§f[ §c궁극기 §f]");
		lorelist.clear();
		lorelist.add("§f- §7가격: §6황금당근 16개");
		meta.setLore(lorelist);
		item.setItemMeta(meta);
		shop_skill_inven.setItem(0 ,item);
		
		item = new ItemStack(387, 1, (short) 8197);
		meta = item.getItemMeta();
		meta.setDisplayName("§f[ §c패시브 §f]");
		lorelist.clear();
		lorelist.add("§f- §7가격: §6황금당근 8개");
		meta.setLore(lorelist);
		item.setItemMeta(meta);
		shop_skill_inven.setItem(2 ,item);
		
		item = new ItemStack(298, 1);
		meta = item.getItemMeta();
		meta.setDisplayName("§f[ §c갑옷 강화 (모자) §f]");
		lorelist.clear();
		lorelist.add("§f- §7가격: §6황금당근 7개");
		meta.setLore(lorelist);
		item.setItemMeta(meta);
		shop_armor_inven.setItem(0 ,item);
		
		item = new ItemStack(299, 1);
		meta = item.getItemMeta();
		meta.setDisplayName("§f[ §c갑옷 강화 (상의) §f]");
		lorelist.clear();
		lorelist.add("§f- §7가격: §6황금당근 11개");
		meta.setLore(lorelist);
		item.setItemMeta(meta);
		shop_armor_inven.setItem(2 ,item);
		
		item = new ItemStack(300, 1);
		meta = item.getItemMeta();
		meta.setDisplayName("§f[ §c갑옷 강화 (바지) §f]");
		lorelist.clear();
		lorelist.add("§f- §7가격: §6황금당근 9개");
		meta.setLore(lorelist);
		item.setItemMeta(meta);
		shop_armor_inven.setItem(4 ,item);
		
		item = new ItemStack(301, 1);
		meta = item.getItemMeta();
		meta.setDisplayName("§f[ §c갑옷 강화 (신발) §f]");
		lorelist.clear();
		lorelist.add("§f- §7가격: §6황금당근 7개");
		meta.setLore(lorelist);
		item.setItemMeta(meta);
		shop_armor_inven.setItem(6 ,item);
		
		JobSelect = Bukkit.createInventory(null, 45, "§c§l직업 선택");
		
		item = new ItemStack(34, 1);
		meta2 = item.getItemMeta();
		meta2.setDisplayName("§6장식");
		item.setItemMeta(meta2);
		for(int i = 0; i <= 44; i+=9){
			JobSelect.setItem(i, item);
		}
		for(int i = 8; i <= 44; i+=9){
			JobSelect.setItem(i, item);
		}
		
		item = new ItemStack(276, 1);
		meta = item.getItemMeta();
		meta.setDisplayName("§f[ §b암살자 §f]");
		lorelist.clear();
		lorelist.add("§f- §7공격력 : §67~9");
		lorelist.add("§f- §7데미지 배율 : §6받은 데미지 x 2");
		lorelist.add("§f- §7공격 : §6★★★★★");
		lorelist.add("§f- §7기동 : §6★☆☆☆☆");
		lorelist.add("§f- §7지원 : §6☆☆☆☆☆");
		meta.setLore(lorelist);
		item.setItemMeta(meta);
		JobSelect.setItem(1 ,item);
		
		item = new ItemStack(318, 1);
		meta = item.getItemMeta();
		meta.setDisplayName("§f[ §b붐버 §f]");
		lorelist.clear();
		lorelist.add("§f- §7공격력 : §63~4");
		lorelist.add("§f- §7데미지 배율 : §6받은 데미지 x 1");
		lorelist.add("§f- §7공격 : §6★★★★★");
		lorelist.add("§f- §7기동 : §6☆☆☆☆☆");
		lorelist.add("§f- §7지원 : §6★☆☆☆☆");
		meta.setLore(lorelist);
		item.setItemMeta(meta);
		JobSelect.setItem(3 ,item);
		
		item = new ItemStack(351, 1, (byte) 4);
		meta = item.getItemMeta();
		meta.setDisplayName("§f[ §b블로커 §f]");
		lorelist.clear();
		lorelist.add("§f- §7공격력 : §64~6");
		lorelist.add("§f- §7데미지 배율 : §6받은 데미지 x 1");
		lorelist.add("§f- §7공격 : §6★★☆☆☆");
		lorelist.add("§f- §7기동 : §6☆☆☆☆☆");
		lorelist.add("§f- §7지원 : §6★★★★★");
		meta.setLore(lorelist);
		item.setItemMeta(meta);
		JobSelect.setItem(5 ,item);
		
		item = new ItemStack(351, 1, (byte) 2);
		meta = item.getItemMeta();
		meta.setDisplayName("§f[ §b영역 생성자 §f]");
		lorelist.clear();
		lorelist.add("§f- §7공격력 : §64~7");
		lorelist.add("§f- §7데미지 배율 : §6받은 데미지 x 1");
		lorelist.add("§f- §7공격 : §6★★☆☆☆");
		lorelist.add("§f- §7기동 : §6☆☆☆☆☆");
		lorelist.add("§f- §7지원 : §6★★★★★");
		meta.setLore(lorelist);
		item.setItemMeta(meta);
		JobSelect.setItem(7 ,item);
		
		item = new ItemStack(261, 1);
		meta = item.getItemMeta();
		meta.setDisplayName("§f[ §b보우 마스터 §f]");
		lorelist.clear();
		lorelist.add("§f- §7공격력 : §61~10(시위를 당긴만큼)");
		lorelist.add("§f- §7데미지 배율 : §6받은 데미지 x 1배");
		lorelist.add("§f- §7공격 : §6★★★★☆");
		lorelist.add("§f- §7기동 : §6☆☆☆☆☆");
		lorelist.add("§f- §7지원 : §6★★☆☆☆");
		meta.setLore(lorelist);
		item.setItemMeta(meta);
		JobSelect.setItem(10 ,item);
		
		item = new ItemStack(257, 1);
		meta = item.getItemMeta();
		meta.setDisplayName("§f[ §b엔지니어 §f]");
		lorelist.clear();
		lorelist.add("§f- §7공격력 : §64~6");
		lorelist.add("§f- §7데미지 배율 : §6받은 데미지 x 0.8");
		lorelist.add("§f- §7공격 : §6★★★☆☆");
		lorelist.add("§f- §7기동 : §6★★★★☆");
		lorelist.add("§f- §7지원 : §6☆☆☆☆☆");
		meta.setLore(lorelist);
		item.setItemMeta(meta);
		JobSelect.setItem(12 ,item);
		
		item = new ItemStack(267, 1);
		meta = item.getItemMeta();
		meta.setDisplayName("§f[ §b기사 §f]");
		lorelist.clear();
		lorelist.add("§f- §7공격력 : §65~7");
		lorelist.add("§f- §7데미지 배율 : §6받은 데미지 x 0.75");
		lorelist.add("§f- §7공격 : §6★★★☆☆");
		lorelist.add("§f- §7기동 : §6★★★★☆");
		lorelist.add("§f- §7지원 : §6★☆☆☆☆");
		meta.setLore(lorelist);
		item.setItemMeta(meta);
		JobSelect.setItem(14 ,item);
		
		item = new ItemStack(296, 1);
		meta = item.getItemMeta();
		meta.setDisplayName("§f[ §b포탈 §f]");
		lorelist.clear();
		lorelist.add("§f- §7공격력 : §63~5");
		lorelist.add("§f- §7데미지 배율 : §6받은 데미지 x 0.8");
		lorelist.add("§f- §7공격 : §6★☆☆☆☆");
		lorelist.add("§f- §7기동 : §6★★★★☆");
		lorelist.add("§f- §7지원 : §6★★★★☆");
		meta.setLore(lorelist);
		item.setItemMeta(meta);
		JobSelect.setItem(16 ,item);
		
		item = new ItemStack(276, 1);
		meta = item.getItemMeta();
		meta.setDisplayName("§f[ §b닌자 §f]");
		lorelist.clear();
		lorelist.add("§f- §7공격력 : §67");
		lorelist.add("§f- §7데미지 배율 : §6받은 데미지 x 1.25");
		lorelist.add("§f- §7공격 : §6★★★★☆");
		lorelist.add("§f- §7기동 : §6★★★☆☆");
		lorelist.add("§f- §7지원 : §6☆☆☆☆☆");
		meta.setLore(lorelist);
		item.setItemMeta(meta);
		JobSelect.setItem(19 ,item);
		
		item = new ItemStack(337, 1);
		meta = item.getItemMeta();
		meta.setDisplayName("§f[ §b랜더머 §f]");
		lorelist.clear();
		lorelist.add("§f- §7공격력 : §63~11");
		lorelist.add("§f- §7데미지 배율 : §6받은 데미지 x 1");
		lorelist.add("§f- §7공격 : §6★★★★★");
		lorelist.add("§f- §7기동 : §6★★☆☆☆");
		lorelist.add("§f- §7지원 : §6☆☆☆☆☆");
		meta.setLore(lorelist);
		item.setItemMeta(meta);
		JobSelect.setItem(21 ,item);
		
		item = new ItemStack(272, 1);
		meta = item.getItemMeta();
		meta.setDisplayName("§f[ §b쉴더 §f]");
		lorelist.clear();
		lorelist.add("§f- §7공격력 : §65");
		lorelist.add("§f- §7데미지 배율 : §6받은 데미지 x 0.66배");
		lorelist.add("§f- §7공격 : §6★★☆☆☆");
		lorelist.add("§f- §7기동 : §6★☆☆☆☆");
		lorelist.add("§f- §7지원 : §6★★★★☆");
		meta.setLore(lorelist);
		item.setItemMeta(meta);
		JobSelect.setItem(23 ,item);
		
		item = new ItemStack(369, 1);
		meta = item.getItemMeta();
		meta.setDisplayName("§f[ §b마녀 §f]");
		lorelist.clear();
		lorelist.add("§f- §7공격력 : §66");
		lorelist.add("§f- §7데미지 배율 : §6받은 데미지 x 1");
		lorelist.add("§f- §7공격 : §6★★★★☆");
		lorelist.add("§f- §7기동 : §6☆☆☆☆☆");
		lorelist.add("§f- §7지원 : §6★★★☆☆");
		meta.setLore(lorelist);
		item.setItemMeta(meta);
		JobSelect.setItem(25 ,item);
		
		item = new ItemStack(286, 1);
		meta = item.getItemMeta();
		meta.setDisplayName("§f[ §b패시브 마스터 §f]");
		lorelist.clear();
		lorelist.add("§f- §7공격력 : §66");
		lorelist.add("§f- §7데미지 배율 : §6받은 데미지 x 1.25");
		lorelist.add("§f- §7공격 : §6★★★☆☆");
		lorelist.add("§f- §7기동 : §6★★★★☆");
		lorelist.add("§f- §7지원 : §6☆☆☆☆☆");
		meta.setLore(lorelist);
		item.setItemMeta(meta);
		JobSelect.setItem(28 ,item);
		
		item = new ItemStack(267, 1);
		meta = item.getItemMeta();
		meta.setDisplayName("§f[ §b트레이서 §f]");
		lorelist.clear();
		lorelist.add("§f- §7공격력 : §64~6");
		lorelist.add("§f- §7데미지 배율 : §6받은 데미지 x 1.43배");
		lorelist.add("§f- §7공격 : §6★★☆☆☆");
		lorelist.add("§f- §7기동 : §6★★★★☆");
		lorelist.add("§f- §7지원 : §6☆☆☆☆☆");
		meta.setLore(lorelist);
		item.setItemMeta(meta);
		JobSelect.setItem(30, item);
		
		item = new ItemStack(338, 1);
		meta = item.getItemMeta();
		meta.setDisplayName("§f[ §b임라프 §f]");
		lorelist.clear();
		lorelist.add("§f- §7공격력 : §66~8");
		lorelist.add("§f- §7데미지 배율 : §6받은 데미지 x 1");
		lorelist.add("§f- §7공격 : §6★★★★☆");
		lorelist.add("§f- §7기동 : §6☆☆☆☆☆");
		lorelist.add("§f- §7지원 : §6★★★☆☆");
		meta.setLore(lorelist);
		item.setItemMeta(meta);
		JobSelect.setItem(32, item);
		
		item = new ItemStack(280, 1);
		meta = item.getItemMeta();
		meta.setDisplayName("§f[ §b사제 §f]");
		lorelist.clear();
		lorelist.add("§f- §7공격력 : §64");
		lorelist.add("§f- §7데미지 배율 : §6받은 데미지 x 1배");
		lorelist.add("§f- §7공격 : §6★☆☆☆☆");
		lorelist.add("§f- §7기동 : §6☆☆☆☆☆");
		lorelist.add("§f- §7지원 : §6★★★★★★★");
		meta.setLore(lorelist);
		item.setItemMeta(meta);
		JobSelect.setItem(34, item);
		
		item = new ItemStack(377, 1);
		meta = item.getItemMeta();
		meta.setDisplayName("§f[ §b플레임 위자드 §f]");
		lorelist.clear();
		lorelist.add("§f- §7공격력 : §64");
		lorelist.add("§f- §7데미지 배율 : §6받은 데미지 x 1.5");
		lorelist.add("§f- §7공격 : §6★★★★★★");
		lorelist.add("§f- §7기동 : §6☆☆☆☆☆");
		lorelist.add("§f- §7지원 : §6☆☆☆☆☆");
		meta.setLore(lorelist);
		meta.setLore(lorelist);
		item.setItemMeta(meta);
		JobSelect.setItem(37, item);
		
		MobSystem.bluejob = Bukkit.createInventory(null, 45, "§c§l직업 선택");
		
		MobSystem.redjob = Bukkit.createInventory(null, 45, "§c§l직업 선택");
		
		Loadconfig();
		
		getLogger().info("마인 오브 배틀3 플러그인이 로드 되었습니다.");
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
	    getLogger().info("참여 지점 또는 로비가 설정되어 있지 않습니다");
	  }
	  try {
		Redspawn = new Location(Bukkit.getWorld(getConfig().getString("Redspawn_world")), getConfig().getInt("Redspawn_x"), getConfig().getInt("Redspawn_y"), getConfig().getInt("Redspawn_z"));
	    Bluespawn = new Location(Bukkit.getWorld(getConfig().getString("Bluespawn_world")), getConfig().getInt("Bluespawn_x"), getConfig().getInt("Bluespawn_y"), getConfig().getInt("Bluespawn_z"));
	  }catch (IllegalArgumentException e){
	    getLogger().info("레드팀 또는 블루팀 스폰이 설정되어 있지 않습니다.");
	  }
	  try {
		Redj = new Location(Bukkit.getWorld(getConfig().getString("Redj_world")), getConfig().getInt("Redj_x"), getConfig().getInt("Redj_y"), getConfig().getInt("Redj_z"));
	    Bluej = new Location(Bukkit.getWorld(getConfig().getString("Bluej_world")), getConfig().getInt("Bluej_x"), getConfig().getInt("Bluej_y"), getConfig().getInt("Bluej_z"));
	  }catch (IllegalArgumentException e){
	    getLogger().info("레드팀 직업 또는 블루팀 직업 선택점이 설정되어 있지 않습니다.");
	  }
	  try {
		Redtop = new Location(Bukkit.getWorld(getConfig().getString("redtop_world")), getConfig().getInt("redtop_x"), getConfig().getInt("redtop_y"), getConfig().getInt("redtop_z"));
	    Bluetop = new Location(Bukkit.getWorld(getConfig().getString("bluetop_world")), getConfig().getInt("bluetop_x"), getConfig().getInt("bluetop_y"), getConfig().getInt("bluetop_z"));
	  }catch (IllegalArgumentException e){
	    getLogger().info("레드팀 포탑 또는 블루팀 포탑 선택점이 설정되어 있지 않습니다.");
	  }
	  try {
		Reddeath = new Location(Bukkit.getWorld(getConfig().getString("Reddeath_world")), getConfig().getInt("Reddeath_x"), getConfig().getInt("Reddeath_y"), getConfig().getInt("Reddeath_z"));
	    Bluedeath = new Location(Bukkit.getWorld(getConfig().getString("Bluedeath_world")), getConfig().getInt("Bluedeath_x"), getConfig().getInt("Bluedeath_y"), getConfig().getInt("Bluedeath_z"));
	  }catch (IllegalArgumentException e){
	    getLogger().info("레드팀 사망포인트 또는 블루팀 사망포인트 지점이 설정되어 있지 않습니다.");
	  }
	  for(int i = 0; i < 3; i++){
		  try {
			    Core[i] = new Location(Bukkit.getWorld(getConfig().getString("Core_world"+(i+1))), getConfig().getInt("Core_x"+(i+1)), getConfig().getInt("Core_y"+(i+1)), getConfig().getInt("Core_z"+(i+1)));
			  }catch (IllegalArgumentException e){
			    getLogger().info(i+" 번째 코어가 설정되어 있지 않습니다.");
			  }
	  }
		try
		{
			for(int j = 1; j <= getConfig().getInt("core1blockamt"); j++){
				  core1blockloc.add(new Location(getServer().getWorld("world"), getConfig().getInt("core1block_loc_"+j+"_x"), getConfig().getInt("core1block_loc_"+j+"_y"), getConfig().getInt("core1block_loc_"+j+"_z")));
			}
		}
		catch (Exception e)
		{
			 getLogger().info("코어1블럭이 완벽히 설정 되어있지 않습니다. 버그 발생의 우려가 있습니다.");
		}
		try
		{
			for(int j = 1; j <= getConfig().getInt("core2blockamt"); j++){
				  core2blockloc.add(new Location(getServer().getWorld("world"), getConfig().getInt("core2block_loc_"+j+"_x"), getConfig().getInt("core2block_loc_"+j+"_y"), getConfig().getInt("core2block_loc_"+j+"_z")));
			}
		}
		catch (Exception e)
		{
			 getLogger().info("코어2블럭이 완벽히 설정 되어있지 않습니다. 버그 발생의 우려가 있습니다.");
		}
	}
    
	public void onDisable(){
		getLogger().info("마인 오브 배틀3 플러그인이 로드 되었습니다.");
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
		p.sendMessage(MS+"/mob join - 게임참여");
		p.sendMessage(MS+"/mob quit - 게임퇴장");
		p.sendMessage(MS+"/mob stop - 게임종료");
		p.sendMessage(MS+"/mob set - 게임설정");
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
				p.sendMessage(MS+"로비 설정 완료");
			} else if(args[1].equalsIgnoreCase("join")){
			    getConfig().set("Join_world", p.getLocation().getWorld().getName());
			    getConfig().set("Join_x", Integer.valueOf(p.getLocation().getBlockX()));
			    getConfig().set("Join_y", Integer.valueOf(p.getLocation().getBlockY() + 1));
			    getConfig().set("Join_z", Integer.valueOf(p.getLocation().getBlockZ()));
			    saveConfig();
			    Loadconfig();
				p.sendMessage(MS+"대기실 설정 완료");
			} else if(args[1].equalsIgnoreCase("core1")){
				int i = 1;
			    getConfig().set("Core_world"+i, p.getLocation().getWorld().getName());
			    getConfig().set("Core_x"+i, Integer.valueOf(p.getLocation().getBlockX()));
			    getConfig().set("Core_y"+i, Integer.valueOf(p.getLocation().getBlockY() - 1));
			    getConfig().set("Core_z"+i, Integer.valueOf(p.getLocation().getBlockZ()));
			    saveConfig();
			    Loadconfig();
				p.sendMessage(MS+"코어1 설정완료");
			} else if(args[1].equalsIgnoreCase("core2")){
				int i = 2;
			    getConfig().set("Core_world"+i, p.getLocation().getWorld().getName());
			    getConfig().set("Core_x"+i, Integer.valueOf(p.getLocation().getBlockX()));
			    getConfig().set("Core_y"+i, Integer.valueOf(p.getLocation().getBlockY() - 1));
			    getConfig().set("Core_z"+i, Integer.valueOf(p.getLocation().getBlockZ()));
			    saveConfig();
			    Loadconfig();
				p.sendMessage(MS+"코어2 설정완료");
			} else if(args[1].equalsIgnoreCase("core3")){
				int i = 3;
			    getConfig().set("Core_world"+i, p.getLocation().getWorld().getName());
			    getConfig().set("Core_x"+i, Integer.valueOf(p.getLocation().getBlockX()));
			    getConfig().set("Core_y"+i, Integer.valueOf(p.getLocation().getBlockY() - 1));
			    getConfig().set("Core_z"+i, Integer.valueOf(p.getLocation().getBlockZ()));
			    saveConfig();
			    Loadconfig();
				p.sendMessage(MS+"코어3 설정완료");
			} 
			else if(args[1].equalsIgnoreCase("redj")){
			    getConfig().set("Redj_world", p.getLocation().getWorld().getName());
			    getConfig().set("Redj_x", Integer.valueOf(p.getLocation().getBlockX()));
			    getConfig().set("Redj_y", Integer.valueOf(p.getLocation().getBlockY() + 1));
			    getConfig().set("Redj_z", Integer.valueOf(p.getLocation().getBlockZ()));
			    saveConfig();
			    Loadconfig();
				p.sendMessage(MS+"레드 직업선택 설정완료");
			} 
			else if(args[1].equalsIgnoreCase("bluej")){
			    getConfig().set("Bluej_world", p.getLocation().getWorld().getName());
			    getConfig().set("Bluej_x", Integer.valueOf(p.getLocation().getBlockX()));
			    getConfig().set("Bluej_y", Integer.valueOf(p.getLocation().getBlockY() + 1));
			    getConfig().set("Bluej_z", Integer.valueOf(p.getLocation().getBlockZ()));
			    saveConfig();
			    Loadconfig();
				p.sendMessage(MS+"블루 직업선택 설정완료");
			} 
			else if(args[1].equalsIgnoreCase("redd")){
			    getConfig().set("Reddeath_world", p.getLocation().getWorld().getName());
			    getConfig().set("Reddeath_x", Integer.valueOf(p.getLocation().getBlockX()));
			    getConfig().set("Reddeath_y", Integer.valueOf(p.getLocation().getBlockY() + 1));
			    getConfig().set("Reddeath_z", Integer.valueOf(p.getLocation().getBlockZ()));
			    saveConfig();
			    Loadconfig();
				p.sendMessage(MS+"레드 사망지점 설정완료");
			} 
			else if(args[1].equalsIgnoreCase("blued")){
			    getConfig().set("Bluedeath_world", p.getLocation().getWorld().getName());
			    getConfig().set("Bluedeath_x", Integer.valueOf(p.getLocation().getBlockX()));
			    getConfig().set("Bluedeath_y", Integer.valueOf(p.getLocation().getBlockY() + 1));
			    getConfig().set("Bluedeath_z", Integer.valueOf(p.getLocation().getBlockZ()));
			    saveConfig();
			    Loadconfig();
				p.sendMessage(MS+"블루 사망지점 설정완료");
			} 
			else if(args[1].equalsIgnoreCase("reds")){
			    getConfig().set("Redspawn_world", p.getLocation().getWorld().getName());
			    getConfig().set("Redspawn_x", Integer.valueOf(p.getLocation().getBlockX()));
			    getConfig().set("Redspawn_y", Integer.valueOf(p.getLocation().getBlockY() + 1));
			    getConfig().set("Redspawn_z", Integer.valueOf(p.getLocation().getBlockZ()));
			    saveConfig();
			    Loadconfig();
				p.sendMessage(MS+"레드 스폰지점 설정완료");
			} 
			else if(args[1].equalsIgnoreCase("blues")){
			    getConfig().set("Bluespawn_world", p.getLocation().getWorld().getName());
			    getConfig().set("Bluespawn_x", Integer.valueOf(p.getLocation().getBlockX()));
			    getConfig().set("Bluespawn_y", Integer.valueOf(p.getLocation().getBlockY() + 1));
			    getConfig().set("Bluespawn_z", Integer.valueOf(p.getLocation().getBlockZ()));
			    saveConfig();
			    Loadconfig();
				p.sendMessage(MS+"블루 스폰지점 설정완료");
			} else if(args[1].equalsIgnoreCase("bluetop")){
			    getConfig().set("bluetop_world", p.getLocation().getWorld().getName());
			    getConfig().set("bluetop_x", Integer.valueOf(p.getLocation().getBlockX()));
			    getConfig().set("bluetop_y", Integer.valueOf(p.getLocation().getBlockY() - 1));
			    getConfig().set("bluetop_z", Integer.valueOf(p.getLocation().getBlockZ()));
			    saveConfig();
			    Loadconfig();
				p.sendMessage(MS+"블루포탑 설정완료");
			} else if(args[1].equalsIgnoreCase("redtop")){
			    getConfig().set("redtop_world", p.getLocation().getWorld().getName());
			    getConfig().set("redtop_x", Integer.valueOf(p.getLocation().getBlockX()));
			    getConfig().set("redtop_y", Integer.valueOf(p.getLocation().getBlockY() - 1));
			    getConfig().set("redtop_z", Integer.valueOf(p.getLocation().getBlockZ()));
			    saveConfig();
			    Loadconfig();
				p.sendMessage(MS+"레드포탑 설정완료");
			}
		} else if(args[0].equalsIgnoreCase("join")){
			MobSystem.GameJoin(p);
		} else if(args[0].equalsIgnoreCase("quit")){
			MobSystem.GameQuit(p);
		} else if(args[0].equalsIgnoreCase("stop")){
			MobSystem.ForceStop();
		} else if(args[0].equalsIgnoreCase("block")){
			MobSystem.join_block = true;
			p.sendMessage(Main.MS+"완료");
		}else if(args[0].equalsIgnoreCase("test")){
			switch(Integer.valueOf(args[1])){
			case -4: Cooldown.cooldownlist.clear(); break;
			case -3: MobSystem.RemoveAbility(p); break;
			case -2: MobSystem.bluelist.remove(p); MobSystem.redlist.add(p); p.sendMessage("레드설정"); break;
			case -1: MobSystem.redlist.remove(p); MobSystem.bluelist.add(p); p.sendMessage("블루설정"); break;
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
			case 17: MobSystem.RemoveAbility(p); MobSystem.ablist.put(p.getName(), new FlameWizard(p.getName(), p)); break;
			case 18: AnimatedBallEffect ef = new AnimatedBallEffect(Main.effectManager);
			ef.setTargetEntity(p); ef.setEntity(p); ef.start(); return;
			case 19: AtomEffect ef2 = new AtomEffect(Main.effectManager);
			ef2.setTargetEntity(p); ef2.setEntity(p); ef2.start(); return;
			case 20: BigBangEffect ef3 = new BigBangEffect(Main.effectManager);
			ef3.setTargetEntity(p); ef3.setEntity(p); ef3.start(); return;
			case 21: EarthEffect ef4 = new EarthEffect(Main.effectManager);
			ef4.setTargetEntity(p); ef4.setEntity(p); ef4.start(); return;
			case 22: ConeEffect ef5 = new ConeEffect(Main.effectManager);
			ef5.setTargetEntity(p); ef5.setEntity(p); ef5.start(); return;
			case 23: DiscoBallEffect ef6 = new DiscoBallEffect(Main.effectManager);
			ef6.setTargetEntity(p); ef6.setEntity(p); ef6.start(); return;
			case 24: ExplodeEffect ef7 = new ExplodeEffect(Main.effectManager);
			ef7.setTargetEntity(p); ef7.setEntity(p); ef7.start(); return;
			case 25: FountainEffect ef8 = new FountainEffect(Main.effectManager);
			ef8.setTargetEntity(p); ef8.setEntity(p); ef8.start(); return;
			case 26: GridEffect ef9 = new GridEffect(Main.effectManager);
			ef9.setTargetEntity(p); ef9.setEntity(p); ef9.start(); return;
			case 27: HeartEffect ef10 = new HeartEffect(Main.effectManager);
			ef10.setTargetEntity(p); ef10.setEntity(p); ef10.start(); return;
			case 28: HelixEffect ef11 = new HelixEffect(Main.effectManager);
			ef11.setTargetEntity(p); ef11.setEntity(p); ef11.start(); return;
			case 29: HillEffect ef12 = new HillEffect(Main.effectManager);
			ef12.setTargetEntity(p); ef12.setEntity(p); ef12.start(); return;
			case 30: IconEffect ef13 = new IconEffect(Main.effectManager);
			ef13.setTargetEntity(p); ef13.setEntity(p); ef13.start(); return;
			case 31: JumpEffect ef14 = new JumpEffect(Main.effectManager);
			ef14.setTargetEntity(p); ef14.setEntity(p); ef14.start(); return;
			case 32: LoveEffect ef15 = new LoveEffect(Main.effectManager);
			ef15.setTargetEntity(p); ef15.setEntity(p); ef15.start(); return;
			case 33: MusicEffect ef16 = new MusicEffect(Main.effectManager);
			ef16.setTargetEntity(p); ef16.setEntity(p); ef16.start(); return;
			case 34: SkyRocketEffect ef17 = new SkyRocketEffect(Main.effectManager);
			ef17.setTargetEntity(p); ef17.setEntity(p); ef17.start(); return;
			case 35: SmokeEffect ef18 = new SmokeEffect(Main.effectManager);
			ef18.setTargetEntity(p); ef18.setEntity(p); ef18.start(); return;
			case 36: TextEffect ef19 = new TextEffect(Main.effectManager);
			ef19.setTargetEntity(p); ef19.setEntity(p); ef19.start(); return;
			case 37: TornadoEffect ef20 = new TornadoEffect(Main.effectManager);
			ef20.setTargetEntity(p); ef20.setEntity(p); ef20.start(); return;
			case 38: TraceEffect ef21 = new TraceEffect(Main.effectManager);
			ef21.setTargetEntity(p); ef21.setEntity(p); ef21.start(); return;
			case 39: VortexEffect ef22 = new VortexEffect(Main.effectManager);
			ef22.setTargetEntity(p); ef22.setEntity(p); ef22.start(); return;
			case 40: WaveEffect ef23 = new WaveEffect(Main.effectManager);
			ef23.setTargetEntity(p); ef23.setEntity(p); ef23.start(); return;
			
			default: return;
			}
			MobSystem.ablist.get(p.getName()).can_Ultimate = true;
			MobSystem.ablist.get(p.getName()).can_passive = true;
		}else if(args[0].equalsIgnoreCase("task")){
			Bukkit.getScheduler().scheduleSyncRepeatingTask(instance, new Runnable(){
				public void run(){
					Bukkit.broadcastMessage(Main.MS+"얍");
				}
			}, 0l, 20);
		}else if(args[0].equalsIgnoreCase("cancel")){
			Bukkit.getScheduler().cancelTasks(instance);
		} else if(args[0].equalsIgnoreCase("core1block")){
			Savecore1Block(p, args[1], args[2], args[3], args[4], args[5], args[6]);
		} else if(args[0].equalsIgnoreCase("core2block")){
			Savecore2Block(p, args[1], args[2], args[3], args[4], args[5], args[6]);
		}
	}
	
	public void Savecore1Block(Player p, String x1, String y1, String z1, String x2, String y2, String z2){
		core1blockloc.clear();
		int amt = 0;
		Location pos1 = new Location(getServer().getWorld("world"), Integer.valueOf(x1), Integer.valueOf(y1), Integer.valueOf(z1));
		Location pos2 = new Location(getServer().getWorld("world"), Integer.valueOf(x2), Integer.valueOf(y2), Integer.valueOf(z2));
		for(int x = pos1.getBlockX(); x <= pos2.getBlockX(); x++){
			for(int y = pos1.getBlockY(); y <= pos2.getBlockY(); y++){
				for(int z = pos1.getBlockZ(); z <= pos2.getBlockZ(); z++){
					Location block_loc = new Location(getServer().getWorld("world"), Integer.valueOf(x), Integer.valueOf(y), Integer.valueOf(z));
					if(block_loc.getBlock().getType() == Material.WOOL){
						amt++;
						getConfig().set("core1block_loc_"+amt+"_x", block_loc.getBlockX());
						getConfig().set("core1block_loc_"+amt+"_y", block_loc.getBlockY());
						getConfig().set("core1block_loc_"+amt+"_z", block_loc.getBlockZ());			
						core1blockloc.add(block_loc);
					}
				}
			}
		}
		getConfig().set("core1blockamt", amt);
		saveConfig();
		Loadconfig();
		p.sendMessage(MS+"설정완료");
	}
	
	public void Savecore2Block(Player p, String x1, String y1, String z1, String x2, String y2, String z2){
		core2blockloc.clear();
		int amt = 0;
		Location pos1 = new Location(getServer().getWorld("world"), Integer.valueOf(x1), Integer.valueOf(y1), Integer.valueOf(z1));
		Location pos2 = new Location(getServer().getWorld("world"), Integer.valueOf(x2), Integer.valueOf(y2), Integer.valueOf(z2));
		for(int x = pos1.getBlockX(); x <= pos2.getBlockX(); x++){
			for(int y = pos1.getBlockY(); y <= pos2.getBlockY(); y++){
				for(int z = pos1.getBlockZ(); z <= pos2.getBlockZ(); z++){
					Location block_loc = new Location(getServer().getWorld("world"), Integer.valueOf(x), Integer.valueOf(y), Integer.valueOf(z));
					if(block_loc.getBlock().getType() == Material.WOOL){
						amt++;
						getConfig().set("core2block_loc_"+amt+"_x", block_loc.getBlockX());
						getConfig().set("core2block_loc_"+amt+"_y", block_loc.getBlockY());
						getConfig().set("core2block_loc_"+amt+"_z", block_loc.getBlockZ());			
						core2blockloc.add(block_loc);
					}
				}
			}
		}
		getConfig().set("core2blockamt", amt);
		saveConfig();
		Loadconfig();
		p.sendMessage(MS+"설정완료");
	}
	
	@EventHandler
	public void PlayerItemHeld(PlayerItemHeldEvent e){
		if(MobSystem.ablist.containsKey(e.getPlayer().getName())){
			Ability ability = MobSystem.ablist.get(e.getPlayer().getName());
			if(MobSystem.skill_block){
				e.getPlayer().sendMessage(Main.MS+"대기중에는 스킬을 사용하실 수 없습니다.");
				return;
			}
			if(MobSystem.ablist.containsKey(e.getPlayer().getName())){
				if(ability.check_dead){
					e.getPlayer().sendMessage(Main.MS+"부활 대기중에는 스킬을 사용하실 수 없습니다.");
					return;	
				}
			}
			if(ability.cc){
				e.getPlayer().sendMessage(Main.MS+"침묵 상태에서는 스킬을 사용하실 수 없습니다.");
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
		if(MobSystem.plist.contains(p) && (e.getMessage().equalsIgnoreCase("/스폰") || e.getMessage().equalsIgnoreCase("/spawn")
				|| e.getMessage().equalsIgnoreCase("/넴주") || e.getMessage().equalsIgnoreCase("/ㅅㅍ")))
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
	public void onEntityExplode(EntityExplodeEvent event) {
		Entity ent = event.getEntity();
		if (ent instanceof Fireball) {
			Fireball fb = (Fireball) event.getEntity();
			if(fb.getShooter() != null && fb.getShooter() instanceof Player){
				Player p = (Player) fb.getShooter();
				if(MobSystem.ablist.containsKey(p.getName())){
					event.setCancelled(true);
				}
			}
		}
	}
	
	@EventHandler
	public void onExplosionPrime(ExplosionPrimeEvent event) {
		Entity ent = event.getEntity();
		if (ent instanceof Fireball) {
			Fireball fb = (Fireball) event.getEntity();
			if(fb.getShooter() != null && fb.getShooter() instanceof Player){
				Player p = (Player) fb.getShooter();
				if(MobSystem.ablist.containsKey(p.getName())){
					event.setFire(false);
					event.setRadius(2);
				}
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
			if(d_ablity.abilityName.equalsIgnoreCase("보우 마스터")) d_ablity.onArrowHit(p);
		}
		if(e.getDamager() instanceof Fireball){
			Fireball fireball = (Fireball) e.getDamager();
			if(fireball.getShooter() instanceof Player){
				d = (Player) fireball.getShooter();
			}
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
			if(MobSystem.bluelist.contains(p)){
				if(Main.Bluespawn.distance(p.getLocation()) <= 75){
					p.sendMessage(Main.MS+"자신의 기지 근처에서는 데미지를 입지 않습니다.");
					e.setCancelled(true);
					return;
				}else if(Main.Bluetop.distance(p.getLocation()) <= 35){
					p.sendMessage(Main.MS+"자신의 포탑 근처에서는 데미지가 절반이 됩니다.");
					e.setDamage((int) e.getDamage()/2);
					return;
				}
			}
			if(MobSystem.redlist.contains(p)){
				if(Main.Redspawn.distance(p.getLocation()) <= 75){
					p.sendMessage(Main.MS+"자신의 기지 근처에서는 데미지를 입지 않습니다.");
					e.setCancelled(true);
					return;
				}else if(Main.Redtop.distance(p.getLocation()) <= 35){
					p.sendMessage(Main.MS+"자신의 포탑 근처에서는 데미지가 절반이 됩니다.");
					e.setDamage((int) e.getDamage()/2);
					return;
				}
			}
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
			MobSystem.SendBluemessages("§f[ §b§l블루팀채팅 §f] §6"+p.getName()+"§f: "+e.getMessage());
		} else {
			MobSystem.SendRedmessages("§f[ §c§l레드팀채팅 §f] §6"+p.getName()+"§f: "+e.getMessage());
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
		/*if(e.getPlayer().getItemInHand().getType() == Material.COMPASS){
			e.getPlayer().openInventory(Compassinven);
		} else */if(e.getPlayer().getItemInHand().getType() == Material.BOOK){
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
		if(e.getInventory().getTitle().equalsIgnoreCase("§c§l직업 선택")){
			e.setCancelled(true);
			MobSystem.clickjob(p, e.getSlot());
			return;
		}
		if(e.getInventory().getTitle().equalsIgnoreCase("§c§l포션 상점")){
			e.setCancelled(true);
			MobSystem.clickshop_potion(p, e.getSlot());
			return;
		}
		if(e.getInventory().getTitle().equalsIgnoreCase("§c§l스킬 상점")){
			e.setCancelled(true);
			MobSystem.clickshop_skill(p, e.getSlot());
			return;
		}
		if(e.getInventory().getTitle().equalsIgnoreCase("§c§l갑옷 상점")){
			e.setCancelled(true);
			MobSystem.clickshop_armor(p, e.getSlot());
			return;
		}
		if(e.getInventory().getTitle().equalsIgnoreCase("§c§l기능")){
			e.setCancelled(true);
			MobSystem.clickutility(p, e.getSlot());
			return;
		}
		/*if(e.getInventory().getTitle().equalsIgnoreCase("§c§l점령지 탐지기")){
			e.setCancelled(true);
			MobSystem.clickcompass(p, e.getSlot());
			return;
		}*/
		if(e.getCurrentItem() != null && 
				(e.getCurrentItem().getType() == Material.IRON_INGOT
				|| e.getCurrentItem().getType() == Material.DIAMOND
				|| e.getCurrentItem().getType() == Material.EMERALD
				|| e.getCurrentItem().getType() == Material.LEATHER_BOOTS
				|| e.getCurrentItem().getType() == Material.LEATHER_CHESTPLATE
				|| e.getCurrentItem().getType() == Material.LEATHER_LEGGINGS
				|| e.getCurrentItem().getType() == Material.LEATHER_HELMET)){
			p.sendMessage(Main.MS+"이 아이템은 옮기실 수 없습니다.");
			e.setCancelled(true);
			p.updateInventory();
		}
	}
	
	@EventHandler
	public void onRightClickEntity(PlayerInteractEntityEvent e){
		Player p = e.getPlayer();
		if(!MobSystem.plist.contains(p)) return;
		if(e.getRightClicked() instanceof Player){
			Player t = (Player) e.getRightClicked();
			if(t.getName().equalsIgnoreCase("§f[§c포탑 건축가§f]")){
				if(MobSystem.bluelist.contains(p)){
					MobSystem.Bluetopcheck(p);
				}else{
					MobSystem.Redtopcheck(p);
				}
			} else if(t.getName().equalsIgnoreCase("§f[§c갑옷 상점§f]")){
				p.getWorld().playSound(p.getLocation(), Sound.NOTE_PLING, 1.0f, 1.0f);
				p.openInventory(shop_armor_inven);
				p.sendMessage(Main.MS+"§f[ §c갑옷 상인 §f] 어서오시게.");
			} else if(t.getName().equalsIgnoreCase("§f[§c스킬 상점§f]")){
				p.getWorld().playSound(p.getLocation(), Sound.NOTE_PLING, 1.0f, 1.0f);
				p.openInventory(shop_skill_inven);
				p.sendMessage(Main.MS+"§f[ §c스킬 상인 §f] 스킬이 좋아야 킬도 많이하지!");
			} else if(t.getName().equalsIgnoreCase("§f[§c포션 상점§f]")){
				p.getWorld().playSound(p.getLocation(), Sound.NOTE_PLING, 1.0f, 1.0f);
				p.openInventory(shop_potion_inven);
				p.sendMessage(Main.MS+"§f[ §c포션 상인 §f] 히어로즈워에서는 자연치유가 안된다고! 포션은 필수지");
			}
		}
	}
}
