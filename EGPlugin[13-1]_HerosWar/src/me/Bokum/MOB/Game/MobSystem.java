package me.Bokum.MOB.Game;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.block.Block;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import me.Bokum.MOB.Main;
import me.Bokum.MOB.Ability.Ability;
import me.Bokum.MOB.Attacker.Assasin;
import me.Bokum.MOB.Attacker.BowMaster;
import me.Bokum.MOB.Attacker.Ninja;
import me.Bokum.MOB.Attacker.PassiveMaster;
import me.Bokum.MOB.Expert.Bomber;
import me.Bokum.MOB.Expert.Engineer;
import me.Bokum.MOB.Expert.Randomer;
import me.Bokum.MOB.Expert.Tracer;
import me.Bokum.MOB.Tanker.Blocker;
import me.Bokum.MOB.Tanker.Imrapu;
import me.Bokum.MOB.Tanker.Knight;
import me.Bokum.MOB.Tanker.Shielder;
import me.Bokum.MOB.Utility.Vector3D;
import me.Bokum.Supporter.AreaCreator;
import me.Bokum.Supporter.Portal;
import me.Bokum.Supporter.Priest;
import me.Bokum.Supporter.Witch;
import net.milkbowl.vault.economy.EconomyResponse;

public class MobSystem {
	public static boolean join_block = false;
	public static boolean check_start = false;
	public static boolean check_lobbystart = false;
	public static boolean skill_block = false;
	public static List<Player> plist = new ArrayList<Player>();
	public static List<Player> redlist = new ArrayList<Player>();
	public static List<Player> bluelist = new ArrayList<Player>();
	public static List<Player> blueparty[] = new ArrayList[5];
	public static List<Player> redparty[] = new ArrayList[5];
	public static int redcorecnt = 0;
	public static int bluecorecnt = 0;
	public static int basetimer = 0;
	public static String core_team[] = new String[3];
	public static int timer_start_num = 0;
	public static int timer_start_time = 0;
	public static int timer_start2_num = 0;
	public static int timer_start2_time = 0;
	public static int bluetimer = 0;
	public static int redtimer = 0;
	public static int red_ticket = 0;
	public static int blue_ticket = 0;
	public static Inventory bluejob;
	public static Inventory redjob;
	public static HashMap<Location, Location> portalloc = new HashMap<Location, Location>();
	public static HashMap<Location, Player> bluedead = new HashMap<Location, Player>();
	public static HashMap<Location, Player> reddead = new HashMap<Location, Player>();
	public static HashMap<Location, AreaCreator> healarea = new HashMap<Location, AreaCreator>();
	public static HashMap<Location, AreaCreator> shieldarea = new HashMap<Location, AreaCreator>();
	public static HashMap<String, Ability> ablist = new HashMap<String, Ability>(40);
	public static HashMap<String, ItemStack[]> iteminven = new HashMap<String, ItemStack[]>();
	public static HashMap<String, ItemStack[]> armorinven = new HashMap<String, ItemStack[]>();
	public static HashMap<String, ItemStack[]> backupinven = new HashMap<String, ItemStack[]>();
	public static HashMap<String, ItemStack[]> backuparmor = new HashMap<String, ItemStack[]>();
	
	public static void GameJoin(Player p){
		if(join_block){
			p.sendMessage(Main.MS+"���� �������Դϴ�. �˼��մϴ�.");
			return;
		}
		if(plist.contains(p)){
			p.sendMessage(Main.MS+"�̹� ���ӿ� �������̽ʴϴ�.");
			return;
		}
		if(plist.size() >= 32)
		{
			p.sendMessage(Main.MS+"�̹� �ִ��ο�(32)�Դϴ�.");
			return;
		}
		if(check_start)
		{
			p.sendMessage(Main.MS+"�̹� ������ �������Դϴ�.");
			return;
		}
		else{
			plist.add(p);
			p.teleport(Main.joinpos);
			p.getInventory().clear();
			p.getInventory().setHelmet(null);
			p.getInventory().setChestplate(null);
			p.getInventory().setLeggings(null);
			p.getInventory().setBoots(null);
			p.getInventory().setItem(8, Main.helpitem);
			me.Bokum.EGM.Main.gamelist.put(p.getName(), "��������");
			Messenger.SendAllmessage(Main.MS+p.getName()+" ���� ��b����������f�� �����ϼ̽��ϴ�. �ο� (��e "+plist.size()+"��7 / ��c20 ��f)");
			if(!check_lobbystart && plist.size() >= 20){
				Startgame();
			}for(Player t : plist){
				t.playSound(t.getLocation(), Sound.NOTE_PLING, 2.0f, 1.0f);
			}
		}
	}
	
	public static void Start2(){
		skill_block = true;
		timer_start2_time = 6;
		for(Player p : plist){
			p.playSound(p.getLocation(), Sound.ANVIL_LAND, 2.0f, 1.0f);
		}
		timer_start2_num = Bukkit.getScheduler().scheduleSyncRepeatingTask(Main.instance, new Runnable()
		{
			public void run()
			{
				if(timer_start2_time > 0)
				{
					Sendmessage(Main.MS+timer_start2_time*10+" �� �� ������ �̵��մϴ�. ������ �������ּ���.");
					switch(timer_start2_time){
					
					case 5: Sendmessage(Main.MS+"��c��n������ ���۵� �� ����� ��Ŭ���Ͻø� \n��c��n��, ��������, Ƽ����Ȳ�� ���� �� �ֽ��ϴ�."); break;
					
					case 4: Sendmessage(Main.MS+"��c��n��ü ä���� �Ͻ� �� �����ϴ�."); break;
					
					case 3: Sendmessage(Main.MS+"��c��n���ɹ���� ������ ��� �����ø� �˴ϴ�."); break;
					
					case 2: Sendmessage(Main.MS+"��c��n���� �� ���׹߰� �� �������� ī�信 ���ּ���."); break;
					
					case 1: Sendmessage(Main.MS+"��c��n����� ��Ÿ�����Դϴ�. ���� ���װ� �ֽ��ϴ�."); break;
					
					}
					timer_start2_time--;
				}
				else
				{
					Bukkit.getScheduler().cancelTask(timer_start2_num);
					skill_block = false;
					for(Player p : plist){
						p.setExp(0);
						p.sendMessage(Main.MS+"������ �̵��ƽ��ϴ�!");
						backupinven.put(p.getName(), p.getInventory().getContents());
						backuparmor.put(p.getName(), p.getInventory().getArmorContents());
						if(bluelist.contains(p)){
							p.teleport(Main.Bluespawn);
						} else {
							p.teleport(Main.Redspawn);
						}
						p.playSound(p.getLocation(), Sound.ANVIL_LAND, 2.0f, 1.0f);
					}
					Baseeffect();
				}
			}
		}, 200L, 200L);
	}
	
	public static void clickjob(Player p, int slot){
		p.playSound(p.getLocation(), Sound.CHICKEN_EGG_POP, 2.0f, 0.8f);
		if(MobSystem.ablist.containsKey(p.getName())) RemoveAbility(p);
		switch(slot){
		case 1: 
				if((MobSystem.bluelist.contains(p) ? bluejob : redjob).getItem(slot).getItemMeta().hasEnchant(Enchantment.DURABILITY)){
					p.sendMessage(Main.MS+"�̹� ������ �� �����Դϴ�.");
					return;
				}
				p.closeInventory();
				MobSystem.ablist.put(p.getName(), new Assasin(p.getName(), p));
				if(!skill_block) p.teleport(MobSystem.bluelist.contains(p) ? Main.Bluespawn : Main.Redspawn);
				ItemStack item = (MobSystem.bluelist.contains(p) ? bluejob : redjob).getItem(slot);
				ItemMeta meta = item.getItemMeta();
				meta.addEnchant(Enchantment.DURABILITY, 1, true);
				item.setItemMeta(meta);
				(MobSystem.bluelist.contains(p) ? bluejob : redjob).setItem(slot, item);
				break;
		case 3: 
				if((MobSystem.bluelist.contains(p) ? bluejob : redjob).getItem(slot).getItemMeta().hasEnchant(Enchantment.DURABILITY)){
					p.sendMessage(Main.MS+"�̹� ������ �� �����Դϴ�.");
					return;
				}
				p.closeInventory();
				MobSystem.ablist.put(p.getName(), new Bomber(p.getName(), p)); 
				if(!skill_block) p.teleport(MobSystem.bluelist.contains(p) ? Main.Bluespawn : Main.Redspawn);
				item = (MobSystem.bluelist.contains(p) ? bluejob : redjob).getItem(slot);
				meta = item.getItemMeta();
				meta.addEnchant(Enchantment.DURABILITY, 1, true);
				item.setItemMeta(meta);
				(MobSystem.bluelist.contains(p) ? bluejob : redjob).setItem(slot, item);
				break;
		case 5: 
			if((MobSystem.bluelist.contains(p) ? bluejob : redjob).getItem(slot).getItemMeta().hasEnchant(Enchantment.DURABILITY)){
				p.sendMessage(Main.MS+"�̹� ������ �� �����Դϴ�.");
				return;
			}
			p.closeInventory();
			MobSystem.ablist.put(p.getName(), new Blocker(p.getName(), p)); 
			if(!skill_block) p.teleport(MobSystem.bluelist.contains(p) ? Main.Bluespawn : Main.Redspawn);
			item = (MobSystem.bluelist.contains(p) ? bluejob : redjob).getItem(slot);
			meta = item.getItemMeta();
			meta.addEnchant(Enchantment.DURABILITY, 1, true);
			item.setItemMeta(meta);
			(MobSystem.bluelist.contains(p) ? bluejob : redjob).setItem(slot, item);
			break;
		
		case 7: 
			if((MobSystem.bluelist.contains(p) ? bluejob : redjob).getItem(slot).getItemMeta().hasEnchant(Enchantment.DURABILITY)){
				p.sendMessage(Main.MS+"�̹� ������ �� �����Դϴ�.");
				return;
			}
			p.closeInventory();
			MobSystem.ablist.put(p.getName(), new AreaCreator(p.getName(), p));
			if(!skill_block) p.teleport(MobSystem.bluelist.contains(p) ? Main.Bluespawn : Main.Redspawn);
			item = (MobSystem.bluelist.contains(p) ? bluejob : redjob).getItem(slot);
			meta = item.getItemMeta();
			meta.addEnchant(Enchantment.DURABILITY, 1, true);
			item.setItemMeta(meta);
			(MobSystem.bluelist.contains(p) ? bluejob : redjob).setItem(slot, item);
			break;
			
		case 10: 
			if((MobSystem.bluelist.contains(p) ? bluejob : redjob).getItem(slot).getItemMeta().hasEnchant(Enchantment.DURABILITY)){
				p.sendMessage(Main.MS+"�̹� ������ �� �����Դϴ�.");
				return;
			}
			p.closeInventory();
			MobSystem.ablist.put(p.getName(), new BowMaster(p.getName(), p)); 
			if(!skill_block) p.teleport(MobSystem.bluelist.contains(p) ? Main.Bluespawn : Main.Redspawn);
			item = (MobSystem.bluelist.contains(p) ? bluejob : redjob).getItem(slot);
			meta = item.getItemMeta();
			meta.addEnchant(Enchantment.DURABILITY, 1, true);
			item.setItemMeta(meta);
			(MobSystem.bluelist.contains(p) ? bluejob : redjob).setItem(slot, item);
			break;
		
		case 12: 
			if((MobSystem.bluelist.contains(p) ? bluejob : redjob).getItem(slot).getItemMeta().hasEnchant(Enchantment.DURABILITY)){
				p.sendMessage(Main.MS+"�̹� ������ �� �����Դϴ�.");
				return;
			}
			p.closeInventory();
			MobSystem.ablist.put(p.getName(), new Engineer(p.getName(), p)); 
			if(!skill_block) p.teleport(MobSystem.bluelist.contains(p) ? Main.Bluespawn : Main.Redspawn);
			item = (MobSystem.bluelist.contains(p) ? bluejob : redjob).getItem(slot);
			meta = item.getItemMeta();
			meta.addEnchant(Enchantment.DURABILITY, 1, true);
			item.setItemMeta(meta);
			(MobSystem.bluelist.contains(p) ? bluejob : redjob).setItem(slot, item);
			break;
		
		case 14: 
			if((MobSystem.bluelist.contains(p) ? bluejob : redjob).getItem(slot).getItemMeta().hasEnchant(Enchantment.DURABILITY)){
				p.sendMessage(Main.MS+"�̹� ������ �� �����Դϴ�.");
				return;
			}
			p.closeInventory();
			MobSystem.ablist.put(p.getName(), new Knight(p.getName(), p));
			if(!skill_block) p.teleport(MobSystem.bluelist.contains(p) ? Main.Bluespawn : Main.Redspawn);
			item = (MobSystem.bluelist.contains(p) ? bluejob : redjob).getItem(slot);
			meta = item.getItemMeta();
			meta.addEnchant(Enchantment.DURABILITY, 1, true);
			item.setItemMeta(meta);
			(MobSystem.bluelist.contains(p) ? bluejob : redjob).setItem(slot, item);
			break;
		
		case 16:
			if((MobSystem.bluelist.contains(p) ? bluejob : redjob).getItem(slot).getItemMeta().hasEnchant(Enchantment.DURABILITY)){
				p.sendMessage(Main.MS+"�̹� ������ �� �����Դϴ�.");
				return;
			}
			p.closeInventory();
			MobSystem.ablist.put(p.getName(), new Portal(p.getName(), p)); 
			if(!skill_block) p.teleport(MobSystem.bluelist.contains(p) ? Main.Bluespawn : Main.Redspawn);
			item = (MobSystem.bluelist.contains(p) ? bluejob : redjob).getItem(slot);
			meta = item.getItemMeta();
			meta.addEnchant(Enchantment.DURABILITY, 1, true);
			item.setItemMeta(meta);
			(MobSystem.bluelist.contains(p) ? bluejob : redjob).setItem(slot, item);
			break;
		
		case 19:
			if((MobSystem.bluelist.contains(p) ? bluejob : redjob).getItem(slot).getItemMeta().hasEnchant(Enchantment.DURABILITY)){
				p.sendMessage(Main.MS+"�̹� ������ �� �����Դϴ�.");
				return;
			}
			p.closeInventory();
			MobSystem.ablist.put(p.getName(), new Ninja(p.getName(), p));
			if(!skill_block) p.teleport(MobSystem.bluelist.contains(p) ? Main.Bluespawn : Main.Redspawn);
			item = (MobSystem.bluelist.contains(p) ? bluejob : redjob).getItem(slot);
			meta = item.getItemMeta();
			meta.addEnchant(Enchantment.DURABILITY, 1, true);
			item.setItemMeta(meta);
			(MobSystem.bluelist.contains(p) ? bluejob : redjob).setItem(slot, item);
			break;
		
		case 21: 
			if((MobSystem.bluelist.contains(p) ? bluejob : redjob).getItem(slot).getItemMeta().hasEnchant(Enchantment.DURABILITY)){
				p.sendMessage(Main.MS+"�̹� ������ �� �����Դϴ�.");
				return;
			}
			p.closeInventory();
			MobSystem.ablist.put(p.getName(), new Randomer(p.getName(), p)); 
			if(!skill_block) p.teleport(MobSystem.bluelist.contains(p) ? Main.Bluespawn : Main.Redspawn);
			item = (MobSystem.bluelist.contains(p) ? bluejob : redjob).getItem(slot);
			meta = item.getItemMeta();
			meta.addEnchant(Enchantment.DURABILITY, 1, true);
			item.setItemMeta(meta);
			(MobSystem.bluelist.contains(p) ? bluejob : redjob).setItem(slot, item);
			break;
		
		case 23: 
			if((MobSystem.bluelist.contains(p) ? bluejob : redjob).getItem(slot).getItemMeta().hasEnchant(Enchantment.DURABILITY)){
				p.sendMessage(Main.MS+"�̹� ������ �� �����Դϴ�.");
				return;
			}
			p.closeInventory();
			MobSystem.ablist.put(p.getName(), new Shielder(p.getName(), p));
			if(!skill_block) p.teleport(MobSystem.bluelist.contains(p) ? Main.Bluespawn : Main.Redspawn);
			item = (MobSystem.bluelist.contains(p) ? bluejob : redjob).getItem(slot);
			meta = item.getItemMeta();
			meta.addEnchant(Enchantment.DURABILITY, 1, true);
			item.setItemMeta(meta);
			(MobSystem.bluelist.contains(p) ? bluejob : redjob).setItem(slot, item);
			break;
		
		case 25: 
			if((MobSystem.bluelist.contains(p) ? bluejob : redjob).getItem(slot).getItemMeta().hasEnchant(Enchantment.DURABILITY)){
				p.sendMessage(Main.MS+"�̹� ������ �� �����Դϴ�.");
				return;
			}
			p.closeInventory();
			MobSystem.ablist.put(p.getName(), new Witch(p.getName(), p)); 
			if(!skill_block) p.teleport(MobSystem.bluelist.contains(p) ? Main.Bluespawn : Main.Redspawn);
			item = (MobSystem.bluelist.contains(p) ? bluejob : redjob).getItem(slot);
			meta = item.getItemMeta();
			meta.addEnchant(Enchantment.DURABILITY, 1, true);
			item.setItemMeta(meta);
			(MobSystem.bluelist.contains(p) ? bluejob : redjob).setItem(slot, item);
			break;
		
		case 28: 
			if((MobSystem.bluelist.contains(p) ? bluejob : redjob).getItem(slot).getItemMeta().hasEnchant(Enchantment.DURABILITY)){
				p.sendMessage(Main.MS+"�̹� ������ �� �����Դϴ�.");
				return;
			}
			p.closeInventory();
			MobSystem.ablist.put(p.getName(), new PassiveMaster(p.getName(), p)); 
			if(!skill_block) p.teleport(MobSystem.bluelist.contains(p) ? Main.Bluespawn : Main.Redspawn);
			item = (MobSystem.bluelist.contains(p) ? bluejob : redjob).getItem(slot);
			meta = item.getItemMeta();
			meta.addEnchant(Enchantment.DURABILITY, 1, true);
			item.setItemMeta(meta);
			(MobSystem.bluelist.contains(p) ? bluejob : redjob).setItem(slot, item);
			break;
		
		case 30: 
			if((MobSystem.bluelist.contains(p) ? bluejob : redjob).getItem(slot).getItemMeta().hasEnchant(Enchantment.DURABILITY)){
				p.sendMessage(Main.MS+"�̹� ������ �� �����Դϴ�.");
				return;
			}
			p.closeInventory();
			MobSystem.ablist.put(p.getName(), new Tracer(p.getName(), p)); 
			if(!skill_block) p.teleport(MobSystem.bluelist.contains(p) ? Main.Bluespawn : Main.Redspawn);
			item = (MobSystem.bluelist.contains(p) ? bluejob : redjob).getItem(slot);
			meta = item.getItemMeta();
			meta.addEnchant(Enchantment.DURABILITY, 1, true);
			item.setItemMeta(meta);
			(MobSystem.bluelist.contains(p) ? bluejob : redjob).setItem(slot, item);
			break;
			
		case 32: 
			if((MobSystem.bluelist.contains(p) ? bluejob : redjob).getItem(slot).getItemMeta().hasEnchant(Enchantment.DURABILITY)){
				p.sendMessage(Main.MS+"�̹� ������ �� �����Դϴ�.");
				return;
			}
			p.closeInventory();
			MobSystem.ablist.put(p.getName(), new Imrapu(p.getName(), p)); 
			if(!skill_block) p.teleport(MobSystem.bluelist.contains(p) ? Main.Bluespawn : Main.Redspawn);
			item = (MobSystem.bluelist.contains(p) ? bluejob : redjob).getItem(slot);
			meta = item.getItemMeta();
			meta.addEnchant(Enchantment.DURABILITY, 1, true);
			item.setItemMeta(meta);
			(MobSystem.bluelist.contains(p) ? bluejob : redjob).setItem(slot, item);
			break;
			
		case 34: 
			if((MobSystem.bluelist.contains(p) ? bluejob : redjob).getItem(slot).getItemMeta().hasEnchant(Enchantment.DURABILITY)){
				p.sendMessage(Main.MS+"�̹� ������ �� �����Դϴ�.");
				return;
			}
			p.closeInventory();
			MobSystem.ablist.put(p.getName(), new Priest(p.getName(), p)); 
			if(!skill_block) p.teleport(MobSystem.bluelist.contains(p) ? Main.Bluespawn : Main.Redspawn);
			item = (MobSystem.bluelist.contains(p) ? bluejob : redjob).getItem(slot);
			meta = item.getItemMeta();
			meta.addEnchant(Enchantment.DURABILITY, 1, true);
			item.setItemMeta(meta);
			(MobSystem.bluelist.contains(p) ? bluejob : redjob).setItem(slot, item);
			break;
		
		default: return;
		}
	}
	
	public static void clickshop(Player p, int slot){
		p.playSound(p.getLocation(), Sound.CHICKEN_EGG_POP, 2.0f, 0.8f);
		switch(slot){
			case 0: if(Takeitem(p, 396, 1)){
					p.getInventory().addItem(new ItemStack(373, 1, (short)8197));
					p.sendMessage(Main.MS+"���� �Ϸ�");
				} break;
			case 2: if(Takeitem(p, 396, 2)){
				p.getInventory().addItem(new ItemStack(373, 1, (short)8229));
				p.sendMessage(Main.MS+"���� �Ϸ�");
			}break;
			case 4: if(Takeitem(p, 396, 2)){
				p.getInventory().addItem(new ItemStack(373, 1, (short)16389));
				p.sendMessage(Main.MS+"���� �Ϸ�");
			}break;
			case 6: if(Takeitem(p, 396, 4)){
				p.getInventory().addItem(new ItemStack(373, 1, (short)16421));
				p.sendMessage(Main.MS+"���� �Ϸ�");
			}break;
			case 8: if(MobSystem.ablist.get(p.getName()).can_Ultimate){
				p.sendMessage(Main.MS+"�̹� �ñر⸦ ���̽��ϴ�.");
				return;
			}
				if(Takeitem(p, 396, 24)){
				MobSystem.ablist.get(p.getName()).can_Ultimate = true;
				p.sendMessage(Main.MS+"���� �Ϸ�");
			}break;
			case 9: if(MobSystem.ablist.get(p.getName()).can_passive){
				p.sendMessage(Main.MS+"�̹� �нú긦 ���̽��ϴ�.");
				return;
			}
				if(Takeitem(p, 396, 12)){
				MobSystem.ablist.get(p.getName()).can_passive= true;
				p.sendMessage(Main.MS+"���� �Ϸ�");
			}break;
			case 11: if(p.getInventory().getHelmet().getItemMeta().hasEnchant(Enchantment.PROTECTION_ENVIRONMENTAL)){
				p.sendMessage(Main.MS+"�̹� ��ȭ �ϼ̽��ϴ�.");
				return;
			}
				if(Takeitem(p, 396, 7)){
				p.getInventory().getHelmet().addEnchantment(Enchantment.PROTECTION_ENVIRONMENTAL, 2);
				p.sendMessage(Main.MS+"���� �Ϸ�");
			}break;
			case 13: if(p.getInventory().getHelmet().getItemMeta().hasEnchant(Enchantment.PROTECTION_ENVIRONMENTAL)){
				p.sendMessage(Main.MS+"�̹� ��ȭ �ϼ̽��ϴ�.");
				return;
			}
				if(Takeitem(p, 396, 11)){
				p.getInventory().getChestplate().addEnchantment(Enchantment.PROTECTION_ENVIRONMENTAL, 2);
				p.sendMessage(Main.MS+"���� �Ϸ�");
			}break;
			case 15: if(p.getInventory().getHelmet().getItemMeta().hasEnchant(Enchantment.PROTECTION_ENVIRONMENTAL)){
				p.sendMessage(Main.MS+"�̹� ��ȭ �ϼ̽��ϴ�.");
				return;
			}
				if(Takeitem(p, 396, 9)){
				p.getInventory().getLeggings().addEnchantment(Enchantment.PROTECTION_ENVIRONMENTAL, 2);
				p.sendMessage(Main.MS+"���� �Ϸ�");
			}break;
			case 17: if(p.getInventory().getHelmet().getItemMeta().hasEnchant(Enchantment.PROTECTION_ENVIRONMENTAL)){
				p.sendMessage(Main.MS+"�̹� ��ȭ �ϼ̽��ϴ�.");
				return;
			}
				if(Takeitem(p, 396, 7)){
				p.getInventory().getBoots().addEnchantment(Enchantment.PROTECTION_ENVIRONMENTAL, 2);
				p.sendMessage(Main.MS+"���� �Ϸ�");
			}break;
		}
	}
	
	public static void clickcompass(Player p, int slot){
		p.playSound(p.getLocation(), Sound.CHICKEN_EGG_POP, 2.0f, 0.8f);
		switch(slot){
		case 1: p.setCompassTarget(Main.Core[0]); p.sendMessage(Main.MS+"���� ��ħ���� 1��° �������� ����ŵ�ϴ�."); break;
		
		case 4: p.setCompassTarget(Main.Core[1]); p.sendMessage(Main.MS+"���� ��ħ���� 2��° �������� ����ŵ�ϴ�."); break;
		
		case 7: p.setCompassTarget(Main.Core[2]); p.sendMessage(Main.MS+"���� ��ħ���� 3��° �������� ����ŵ�ϴ�."); break;
		
		default: return;
		}
		return;
	}
	
	public static void clickutility(Player p, int slot){
		p.playSound(p.getLocation(), Sound.CHICKEN_EGG_POP, 2.0f, 0.8f);
		switch(slot){
		case 1: p.closeInventory(); if(MobSystem.ablist.containsKey(p.getName())){
				MobSystem.ablist.get(p.getName()).description(); 
				} else {
					p.sendMessage(Main.MS+"���� ������ ����ּ���.");
				} break;
		
		case 4: p.openInventory(Main.Shopinven); break;
		
		default: return;
		}
		return;
	}
	
	public static void Startgame()
	{
		check_lobbystart = true;
		Bukkit.getScheduler().cancelTask(bluetimer);
		Bukkit.getScheduler().cancelTask(redtimer);
		Bukkit.getScheduler().cancelTask(basetimer);
		Bukkit.broadcastMessage(Main.MS+"��b��l��������3��f�� �� ���۵˴ϴ�.");
		Initdata();
		timer_start_time = 10;
		timer_start_num = Bukkit.getScheduler().scheduleSyncRepeatingTask(Main.instance, new Runnable()
		{
			public void run()
			{
				if(timer_start_time > 0)
				{
					Sendmessage(Main.MS+"������ "+timer_start_time*10+" �� �� ���۵˴ϴ�.");
					timer_start_time--;
				}
				else
				{
					Bukkit.getScheduler().cancelTask(timer_start_num);
					check_start = true;
					Start2();
					for(int i = 0; i < plist.size(); i++){
						Player p = plist.get(i);
						p.getInventory().clear();
						p.sendMessage(Main.MS+"������ ���� �ƽ��ϴ�.");
						if(bluelist.size() <= redlist.size()){
							bluelist.add(p);
							Bukkit.dispatchCommand(p.getServer().getConsoleSender(), "scoreboard teams join blue "+p.getName());
							p.getInventory().setHelmet(Main.bluearmor[0]);
							p.getInventory().setChestplate(Main.bluearmor[1]);
							p.getInventory().setLeggings(Main.bluearmor[2]);
							p.getInventory().setBoots(Main.bluearmor[3]);
							p.teleport(Main.Bluej);
						} else {
							redlist.add(p);
							Bukkit.dispatchCommand(p.getServer().getConsoleSender(), "scoreboard teams join red "+p.getName());
							p.getInventory().setHelmet(Main.redarmor[0]);
							p.getInventory().setChestplate(Main.redarmor[1]);
							p.getInventory().setLeggings(Main.redarmor[2]);
							p.getInventory().setBoots(Main.redarmor[3]);
							p.teleport(Main.Redj);
						}
						p.getInventory().setItem(0, Main.jobitem);
						p.getInventory().setItem(7, Main.shopitem);
						p.getInventory().setItem(8, Main.compassitem);
						p.sendMessage(Main.MS+"��c��n���̸� ��Ŭ���Ͽ� ������ ����ּ���.");
						p.setCompassTarget(Main.Core[2]);
					}
				}
			}
		}, 200L, 200L);
	}
	
	public static void Baseeffect(){
		Bukkit.getScheduler().cancelTask(basetimer);
		basetimer = Bukkit.getScheduler().scheduleSyncRepeatingTask(Main.instance, new Runnable(){
			public void run(){
				for(Player t : MobSystem.bluelist){
					if(Main.Bluespawn.distance(t.getLocation()) <= 30){
						t.setHealth(t.getMaxHealth());
					}
				}
				for(Player t : MobSystem.redlist){
					if(Main.Redspawn.distance(t.getLocation()) <= 30){
						t.setHealth(t.getMaxHealth());
					}
				}
			}
		}, 0l, 60l);
	}
	
	public static void GameQuit(Player p){
		if(!plist.contains(p)){
			return;
		}
		plist.remove(p);
		if(ablist.containsKey(p.getName())){
	    	RemoveAbility(p);
		}
		if(bluelist.contains(p)){
			bluelist.remove(p);
		}
		if(redlist.contains(p)){
			redlist.remove(p);
		}
	}
	
	public static void Cleardata(){
		Bukkit.getScheduler().cancelTasks(Main.instance);
		check_start = false;
		check_lobbystart = false;
		plist.clear();
		redlist.clear();
		bluelist.clear();
		redcorecnt = 0;
		bluecorecnt = 0;
		timer_start_time = 0;
		red_ticket = 900;
		blue_ticket = 900;
		for(Location l : portalloc.keySet()){
			l.getBlock().setType(Material.AIR);
		}
		portalloc.clear();
		for(Location l : healarea.keySet()){
			l.getBlock().setType(Material.AIR);
		}
		healarea.clear();
		for(Location l : shieldarea.keySet()){
			l.getBlock().setType(Material.AIR);
		}
		shieldarea.clear();
		iteminven.clear();
		armorinven.clear();
		backupinven.clear();
		backuparmor.clear();
		ablist.clear();
		Main.Forcestoptimer = 0;
	}
	
	public static void ForceStop(){
		Bukkit.broadcastMessage(Main.MS+"���������� ���� ���� �Ǿ����ϴ�.");
		for(Player p : plist){
	    	RemoveAbility(p);
			try{
				me.Bokum.EGM.Main.Spawn(p);
			}catch(Exception e){
				p.teleport(Main.Lobby);
			}
		}
		Cleardata();
	}
	
	public static List<Player> getParty(Player p){
		if(redlist.contains(p)){
			for(int i = 0; i < redparty.length; i++){
				if(redparty[i].contains(p)) return redparty[i];
			}
		} else{
			for(int i = 0; i < blueparty.length; i++){
				if(blueparty[i].contains(p)) return blueparty[i];
			}
		}
		return null;
	}
	
	public static void Sendmessage(String str)
	{
		for(Player p : plist)
		{
			p.sendMessage(str);
		}
	}
	
	public static void SendBluemessages(String str)
	{
		for(Player p : bluelist)
		{
			p.sendMessage(str);
		}
	}
	
	public static void SendRedmessages(String str)
	{
		for(Player p : redlist)
		{
			p.sendMessage(str);
		}
	}
	
	public static void Initdata(){
		Block b = Main.Core[0].getBlock();
		b.setTypeIdAndData(35, (byte) 0, true);
		b = Main.Core[1].getBlock();
		b.setTypeIdAndData(35, (byte) 0, true);
		b = Main.Core[2].getBlock();
		b.setTypeIdAndData(35, (byte) 0, true);
		for(int i = 0; i < 3; i++){
			core_team[i] = "�߸�";
		}
		red_ticket = 1000;
		blue_ticket = 1000;
		bluejob.setContents(Main.JobSelect.getContents());
		redjob.setContents(Main.JobSelect.getContents());
		
		List<String> lorelist = new ArrayList<String>();
		
		ItemMeta meta = Main.Compassinven.getItem(1).getItemMeta();
		meta.setDisplayName("��f[ ��c1�� ������ ��f]");
		lorelist.clear();
		lorelist.add("��f- ��7Ŭ���� ��ħ���� 1�� �������� ����ŵ�ϴ�.");
		lorelist.add("��f- ��7���� ������ �� ��f:��6 �߸�");
		meta.setLore(lorelist);
		Main.Compassinven.getItem(1).setItemMeta(meta);
		
		meta = Main.Compassinven.getItem(4).getItemMeta();
		meta.setDisplayName("��f[ ��c2�� ������ ��f]");
		lorelist.clear();
		lorelist.add("��f- ��7Ŭ���� ��ħ���� 2�� �������� ����ŵ�ϴ�.");
		lorelist.add("��f- ��7���� ������ �� ��f:��6 �߸�");
		meta.setLore(lorelist);
		Main.Compassinven.getItem(4).setItemMeta(meta);
		
		meta = Main.Compassinven.getItem(7).getItemMeta();
		meta.setDisplayName("��f[ ��c3�� ������ ��f]");
		lorelist.clear();
		lorelist.add("��f- ��7Ŭ���� ��ħ���� 3�� �������� ����ŵ�ϴ�.");
		lorelist.add("��f- ��7���� ������ �� ��f:��6 �߸�");
		meta.setLore(lorelist);
		Main.Compassinven.getItem(7).setItemMeta(meta);
	}
	
	public static void UpdateCompass(){
		List<String> lorelist = new ArrayList<String>();
		
		ItemMeta meta = Main.Compassinven.getItem(1).getItemMeta();
		meta.setDisplayName("��f[ ��c1�� ������ ��f]");
		lorelist.clear();
		lorelist.add("��f- ��7Ŭ���� ��ħ���� 1�� �������� ����ŵ�ϴ�.");
		lorelist.add("��f- ��7���� ������ �� ��f: ��6"+MobSystem.core_team[0]);
		meta.setLore(lorelist);
		Main.Compassinven.getItem(1).setItemMeta(meta);
		
		meta = Main.Compassinven.getItem(4).getItemMeta();
		meta.setDisplayName("��f[ ��c2�� ������ ��f]");
		lorelist.clear();
		lorelist.add("��f- ��7Ŭ���� ��ħ���� 2�� �������� ����ŵ�ϴ�.");
		lorelist.add("��f- ��7���� ������ �� ��f: ��6"+MobSystem.core_team[1]);
		meta.setLore(lorelist);
		Main.Compassinven.getItem(4).setItemMeta(meta);
		
		meta = Main.Compassinven.getItem(7).getItemMeta();
		meta.setDisplayName("��f[ ��c3�� ������ ��f]");
		lorelist.clear();
		lorelist.add("��f- ��7Ŭ���� ��ħ���� 3�� �������� ����ŵ�ϴ�.");
		lorelist.add("��f- ��7���� ������ �� ��f: ��6"+MobSystem.core_team[2]);
		meta.setLore(lorelist);
		Main.Compassinven.getItem(7).setItemMeta(meta);
	}
	
	public static void RedWin(){
	    for(Player p1 : plist){
	    	p1.getInventory().clear();
	    	p1.playSound(p1.getLocation(), Sound.ANVIL_LAND, 2.0f, 0.5f);
	    }
		Sendmessage(Main.MS+"��6��l������� Ƽ���� ��� ���������ϴ�!");
		Sendmessage(Main.MS+"��c��������f�� �¸��Դϴ�!");
		try{
		Bukkit.getScheduler().scheduleSyncDelayedTask(Main.instance, new Runnable()
		{
			public void run()
			{	
				for(Player p : plist)
				{
					if(bluelist.contains(p))
					{
						EconomyResponse r = Main.econ.depositPlayer(p.getName(), 500);
						if (r.transactionSuccess()) 
						{
							p.sendMessage(ChatColor.GOLD + "�й� �������� 1000���� �����̽��ϴ�.");
						}
					}
					else
					{
						EconomyResponse r = Main.econ.depositPlayer(p.getName(), 1000);
						if (r.transactionSuccess()) 
						{
							p.sendMessage(ChatColor.GOLD + "�¸� �������� 2000���� �����̽��ϴ�.");
						}
					}
			          try{
			        	  me.Bokum.EGM.Main.Spawn(p);
			          } catch(Exception e){
			        	  p.teleport(Main.Lobby);
			          }
				}
				Cleardata();
				Bukkit.broadcastMessage(Main.MS+"��b��l���ο����Ʋ��f�� ��c��l��������f�� �¸��� ���� �ƽ��ϴ�.");
			}
		}, 140L);
		}catch(Exception e){
	    	ForceStop();
	    }
	}
	
	public static void BlueWin(){
	    for(Player p1 : plist){
	    	RemoveAbility(p1);
	    	p1.getInventory().clear();
	    	p1.playSound(p1.getLocation(), Sound.ANVIL_LAND, 2.0f, 0.5f);
	    }
		Sendmessage(Main.MS+"��6��l�������� Ƽ���� ��� ���������ϴ�!");
		Sendmessage(Main.MS+"��b�������f�� �¸��Դϴ�!");
		Bukkit.getScheduler().cancelTasks(Main.instance);
		try{
		Bukkit.getScheduler().scheduleSyncDelayedTask(Main.instance, new Runnable()
		{
			public void run()
			{	
				for(Player p : plist)
				{
					if(!bluelist.contains(p))
					{
						EconomyResponse r = Main.econ.depositPlayer(p.getName(), 500);
						if (r.transactionSuccess()) 
						{
							p.sendMessage(ChatColor.GOLD + "�й� �������� 1000���� �����̽��ϴ�.");
						}
					}
					else
					{
						EconomyResponse r = Main.econ.depositPlayer(p.getName(), 1000);
						if (r.transactionSuccess()) 
						{
							p.sendMessage(ChatColor.GOLD + "�¸� �������� 2000���� �����̽��ϴ�.");
						}
					}
			          try{
			        	  me.Bokum.EGM.Main.Spawn(p);
			          } catch(Exception e){
			        	  p.teleport(Main.Lobby);
			          }
				}
				Cleardata();
				Bukkit.broadcastMessage(Main.MS+"��b��l���ο����Ʋ��f�� ��b��l�������f�� �¸��� ���� �ƽ��ϴ�.");
			}
		}, 140L);
		}catch(Exception e){
	    	ForceStop();
	    }
	}
	
	public static List<Player> getEnemy(Player p){
		if(redlist.contains(p)) return bluelist;
		if(bluelist.contains(p)) return redlist;
		return redlist;
	}
	
	public static List<Player> getTeam(Player p){
		if(redlist.contains(p)) return redlist;
		if(bluelist.contains(p)) return bluelist;
		return redlist;
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
			p.sendMessage(Main.MS+"��7Ȳ�ݴ���� �����մϴ�.");
			return false;
	  }
	  
	  public static void Addhp(Player p, int amt){
		  if(p.isDead()) return;
		  p.setHealth(p.getHealth()+amt > p.getMaxHealth() ? p.getMaxHealth() : p.getHealth() + amt);
	  }
	  
	  public static void Minushp(Player p, int amt){
		  p.setHealth(p.getHealth()-amt < 0 ? 0 : p.getHealth() - amt);
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
	  
	  public static void RemoveAbility(Player p){
		  if(!MobSystem.ablist.containsKey(p.getName())) return;
		  Ability ability = MobSystem.ablist.get(p.getName());
		  Bukkit.getScheduler().cancelTask(ability.timernum);
		  Bukkit.getScheduler().cancelTask(ability.timernum1);
		  Bukkit.getScheduler().cancelTask(ability.timernum2);
		  if(ability.abilityName.equalsIgnoreCase("��Ż")){
			  Portal por = (Portal) ability;
			  por.blueportal.getBlock().setType(Material.AIR);
			  por.redportal.getBlock().setType(Material.AIR);
			  portalloc.remove(por.blueportal);
			  portalloc.remove(por.redportal);
		  }
		  if(ability.abilityName.equalsIgnoreCase("���� ������")){
			  AreaCreator acr = (AreaCreator) ability;
			  if(acr.healarea != null)
			  acr.healarea.getBlock().setType(Material.AIR);
			  if(acr.shieldarea != null)
			  acr.shieldarea.getBlock().setType(Material.AIR);
			  healarea.remove(acr.healarea);
			  shieldarea.remove(acr.shieldarea);
		  }
		  if(ability.abilityName.equalsIgnoreCase("�ϻ���")){
			  for(Player t : Bukkit.getOnlinePlayers()){
				  t.showPlayer(p);
			  }
		  }
		  ablist.remove(p.getName());
	  }
	

	    public static Player Getcorsurplayer(Player p, int range) 
	    {
	        final Player observer = p;
	        
	        Location observerPos1 = observer.getEyeLocation();
	        Vector3D observerDir1 = new Vector3D(observerPos1.getDirection());
	        Vector3D observerStart1 = new Vector3D(observerPos1);
	        
	        Location loc = p.getLocation();
	        
	        for(int i = 1; i <= 10; i++){
	            Vector3D observerEnd1 = observerStart1.add(observerDir1.multiply(i));
	            loc = new Location(p.getWorld(), observerEnd1.x, observerEnd1.y, observerEnd1.z);
	        	if(loc.getBlock().getType() != Material.AIR){
	        		if(i == 1){
	        			range = i;
	        		}
	    			break;
	        	}
	        }
	        
	        Location observerPos = observer.getEyeLocation();
	        Vector3D observerDir = new Vector3D(observerPos.getDirection());

	        Vector3D observerStart = new Vector3D(observerPos);
	        Vector3D observerEnd = observerStart.add(observerDir.multiply(range));

	        Player hit = null;  

	        // Get nearby entities
	        for (Player target : observer.getWorld().getPlayers()) 
	        {
	        // Bounding box of the given player
	        	Vector3D targetPos = new Vector3D(target.getLocation());
	            Vector3D minimum = targetPos.add(-0.5, 0, -0.5);
	            Vector3D maximum = targetPos.add(0.5, 1.67, 0.5);

	            if (target != observer && hasIntersection(observerStart, observerEnd, minimum, maximum)) 
	            {
	            	if (hit == null || hit.getLocation().distanceSquared(observerPos) > target.getLocation().distanceSquared(observerPos)) 
	            	{    
	            		hit = target;
	            	}
	            }
	        }
	                
	        // Hit the closest player
	        if (hit != null) 
	        {
	        	return hit;
	        }
	        return null;
	        
	        
	    }
	    
	     // Source:
	    // [url]http://www.gamedev.net/topic/338987-aabb---line-segment-intersection-test/[/url]
	    private static boolean hasIntersection(Vector3D p1, Vector3D p2, Vector3D min, Vector3D max) {
	        final double epsilon = 0.0001f;
	         
	        Vector3D d = p2.subtract(p1).multiply(0.5);
	        Vector3D e = max.subtract(min).multiply(0.5);
	        Vector3D c = p1.add(d).subtract(min.add(max).multiply(0.5));
	        Vector3D ad = d.abs();
	         
	        if (Math.abs(c.x) > e.x + ad.x)
	            return false;
	        if (Math.abs(c.y) > e.y + ad.y)
	            return false;
	        if (Math.abs(c.z) > e.z + ad.z)
	            return false;
	         
	        if (Math.abs(d.y * c.z - d.z * c.y) > e.y * ad.z + e.z * ad.y + epsilon)
	            return false;
	        if (Math.abs(d.z * c.x - d.x * c.z) > e.z * ad.x + e.x * ad.z + epsilon)
	            return false;
	        if (Math.abs(d.x * c.y - d.y * c.x) > e.x * ad.y + e.y * ad.x + epsilon)
	            return false;
	         
	        return true;
	    }
	    
		public static int Getrandom(int min, int max){
			  return (int)(Math.random() * (max-min+1)+min);
			}
		
		public static void CancelSkill(final Player p, int time){
			final Ability ability = MobSystem.ablist.get(p.getName());
			p.sendMessage(Main.MS+"ħ�� ���°� �Ǿ����ϴ�! ��c"+time+"��f�� �� ��ų�� ����Ͻ� �� �����ϴ�!");
			ability.cc = true;
			Bukkit.getScheduler().scheduleSyncDelayedTask(Main.instance, new Runnable(){
				public void run(){
					p.sendMessage(Main.MS+"ħ�� ���°� ���� �Ǿ����ϴ�.");
					ability.cc = false;
				}
			}, (long)time*20);
		}
}
