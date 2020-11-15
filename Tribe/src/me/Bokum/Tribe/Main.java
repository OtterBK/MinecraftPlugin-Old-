package me.Bokum.Tribe;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Set;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
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
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.entity.EntityDamageEvent.DamageCause;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.player.PlayerExpChangeEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerLevelChangeEvent;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.event.player.PlayerRespawnEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

public class Main extends JavaPlugin implements Listener{
	public static String MS = "��f[ ��aBJ����f] ";
	public static Inventory tribeselect;
	public static int timecheck = 0;
	public static boolean isDay = false;
	public static HashMap<String, String> tribelist = new HashMap<String, String>(70);
	public static HashMap<String, Integer> statMap = new HashMap<String, Integer>();
	public static HashMap<String, Integer> powerMap = new HashMap<String, Integer>();
	public static HashMap<String, Integer> accurMap = new HashMap<String, Integer>();
	public static HashMap<String, Integer> defenMap = new HashMap<String, Integer>();
	public static HashMap<String, Inventory> invenMap = new HashMap<String, Inventory>();
	public static HashMap<String, Integer> levellist = new HashMap<String, Integer>();
	public static HashMap<String, Float> explist = new HashMap<String, Float>();
	public static List<String> statwait = new ArrayList<String>();
	public static float power_rate = 0.5f;
	public static float accur_rate = 6f;
	public static float defen_rate = 0.5f;
	public static Inventory stat;
	
	public void onEnable(){
		getServer().getPluginManager().registerEvents(this, this);
		tribeselect = Bukkit.createInventory(null, 45, "��c��l���� ����");
		ItemStack deco = new ItemStack(102, 1);
		ItemMeta decom = deco.getItemMeta();
		decom.setDisplayName("��6���");
		deco.setItemMeta(decom);
		for(int i = 0; i <= 9; i++){
			tribeselect.setItem(i, deco);
		}
		for(int i = 35; i <= 44; i++){
			tribeselect.setItem(i, deco);
		}
		tribeselect.setItem(18, deco);
		tribeselect.setItem(27, deco);
		tribeselect.setItem(26, deco);
		tribeselect.setItem(17, deco);
		List<String> lorelist = new ArrayList<String>();
		


		ItemStack item = new ItemStack(35, 1, (byte) 14);
		ItemMeta meta = item.getItemMeta();
		meta.setDisplayName("��4��l��󸸴�");
		lorelist.clear();
		lorelist.add("��f- ��7��� �������� +2 �߰� ������");
		lorelist.add("��f- ��7ȭ�� ������ ����");
		lorelist.add("��f- ��7�ͻ� ������ 2��� ����");
	        lorelist.add("��f- ��7���߿����� �߰� ������ ȿ�� ��ȿ");
		meta.setLore(lorelist);
		item.setItemMeta(meta);
		tribeselect.setItem(11, item);
		
		item = new ItemStack(35, 1, (byte) 9);
		meta = item.getItemMeta();
		meta.setDisplayName("��b��l����");
		lorelist.clear();
		lorelist.add("��f- ��7���߿� ���� �� ���1 ����");
		lorelist.add("��f- ��7���� �̵��ӵ� ����");
		lorelist.add("��f- ��7�ͻ� �������� ��������");
		meta.setLore(lorelist);
		item.setItemMeta(meta);
		tribeselect.setItem(13, item);
		
		item = new ItemStack(35, 1, (byte) 1);
		meta = item.getItemMeta();
		meta.setDisplayName("��6��l���");
		lorelist.clear();
		lorelist.add("��f- ��7�ִ�ü�� 10����");
		lorelist.add("��f- ��7Ÿ�ݴ��� �� 2%Ȯ���� ���� ���");
		meta.setLore(lorelist);
		item.setItemMeta(meta);
		tribeselect.setItem(15, item);
		
		item = new ItemStack(35, 1, (byte) 4);
		meta = item.getItemMeta();
		meta.setDisplayName("��b��lĳƮ��");
		lorelist.clear();
		lorelist.add("��f- ��711%Ȯ���� ��� ����ȸ��");
		meta.setLore(lorelist);
		item.setItemMeta(meta);
		tribeselect.setItem(20, item);
		
		item = new ItemStack(35, 1, (byte) 15);
		meta = item.getItemMeta();
		meta.setDisplayName("��8��l��������");
		lorelist.clear();
		lorelist.add("��f- ��7�÷��̾� Ÿ�ݽ� 5%Ȯ���� ��󿡰� �Ǹ�1 6��");
		lorelist.add("��f- ��7�÷��̾� Ÿ�ݽ� 5%Ȯ���� ��󿡰� ��1 6��");
		meta.setLore(lorelist);
		item.setItemMeta(meta);
		tribeselect.setItem(22, item);
		
		item = new ItemStack(35, 1, (byte) 7);
		meta = item.getItemMeta();
		meta.setDisplayName("��7��l��������");
		lorelist.clear();
		lorelist.add("��f- ��7ö�� 20���� �Ҹ��Ͽ�");
		lorelist.add("��f- ��7������ ��������");
		lorelist.add("��f- ��7��ɾ� ��c'/����'");
		meta.setLore(lorelist);
		item.setItemMeta(meta);
		tribeselect.setItem(24, item);
		
		item = new ItemStack(35, 1, (byte) 10);
		meta = item.getItemMeta();
		meta.setDisplayName("��9��l����");
		lorelist.clear();
		lorelist.add("��f- ��7���� �� �� �߰� ���ù��� ����");
		lorelist.add("��f- ��7�߰� ������ +1 �߰� ������");
		lorelist.add("��f- ��7�߰� ������ �޴� ��� ������ -1");
		meta.setLore(lorelist);
		item.setItemMeta(meta);
		tribeselect.setItem(29, item);
		
		item = new ItemStack(35, 1, (byte) 5);
		meta = item.getItemMeta();
		meta.setDisplayName("��a��l����");
		lorelist.clear();
		lorelist.add("��f- ��7�ż�1 ���� ����");
		lorelist.add("��f- ��74%Ȯ���� 1~Ÿ���� ������ ��");
		lorelist.add("��f- ��7���� �߰� ������");
		meta.setLore(lorelist);
		item.setItemMeta(meta);
		tribeselect.setItem(31, item);
		
		item = new ItemStack(35, 1, (byte) 6);
		meta = item.getItemMeta();
		meta.setDisplayName("��d��lǪŰ");
		lorelist.clear();
		lorelist.add("��f- ��7���� ������ ���� ������ 10%Ȯ����");
		lorelist.add("��f- ��7���� �������� ���2 ���� 5��");
		meta.setLore(lorelist);
		item.setItemMeta(meta);
		tribeselect.setItem(33, item);

		stat = Bukkit.createInventory(null, 9, "��c��l����");
		
		item = new ItemStack(339, 1);
		meta = item.getItemMeta();
		meta.setDisplayName("��c��l����� ���� ���� : ");
		item.setItemMeta(meta);
		stat.setItem(0, item);
		
		item = new ItemStack(102, 1);
		meta = item.getItemMeta();
		meta.setDisplayName("��6���");
		item.setItemMeta(meta);
		stat.setItem(1, item);
		
		item = new ItemStack(340, 1);
		meta = item.getItemMeta();
		meta.setDisplayName("��c��l��");
		lorelist.clear();
		lorelist.add("��f- ��7������ 0.5 ���");
		meta.setLore(lorelist);
		item.setItemMeta(meta);
		stat.setItem(3, item);
		
		item = new ItemStack(340, 1);
		meta = item.getItemMeta();
		meta.setDisplayName("��c��l���߷�");
		lorelist.clear();
		lorelist.add("��f- ��7ũ��Ƽ�� Ȯ�� 0.6% ���");
		meta.setLore(lorelist);
		item.setItemMeta(meta);
		stat.setItem(5, item);
		
		item = new ItemStack(340, 1);
		meta = item.getItemMeta();
		meta.setDisplayName("��c��l���");
		lorelist.clear();
		lorelist.add("��f- ��7��� ������ 0.5 ����");
		meta.setLore(lorelist);
		item.setItemMeta(meta);
		stat.setItem(7, item);
		
		Timer();
		
		getLogger().info("���� �÷������� �ε� �Ǿ����ϴ�");
	}
	
	public void Timer(){
	    long time = getServer().getWorld("world").getTime();    
		Bukkit.getScheduler().scheduleSyncRepeatingTask(this, new Runnable()
		{
			public void run(){
				if(--timecheck < 0){
					timecheck = 5;
					getLogger().info("���� ������ �ڵ� ���� ����");
					saveTribe();
					getLogger().info("���� ������ �ڵ� ���� ����");
					saveAllStat();
				}
			    long time = getServer().getWorld("world").getTime();    
			    if(isDay){
				    if(!(time < 13000 || time > 23850)){
				    		isDay = false;
				    		for(Player t : Bukkit.getOnlinePlayers()){
				    			t.sendMessage("��c��l���� �ƽ��ϴ�.");
				    			if(getTribe(t).equalsIgnoreCase("����")){
				    				t.addPotionEffect(new PotionEffect(PotionEffectType.NIGHT_VISION, 72000, 0));
				    			}
				    	}
				    }
			    } else {
			    	if(time < 13000 || time > 23850){
			    		isDay = true;
			    		for(Player t : Bukkit.getOnlinePlayers()){
			    			t.sendMessage("��c��l���� �ƽ��ϴ�.");
			    			if(getTribe(t).equalsIgnoreCase("����")){
			    				t.removePotionEffect(PotionEffectType.NIGHT_VISION);
			    			}
			    		}
			    	}
			    }
			}
		}, 0l, 1200L);
	}
	
	public void onDisable(){
		getLogger().info("���� �÷������� ��ε� �Ǿ����ϴ�");
		saveTribe();
		saveAllStat();
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
	
	public boolean onCommand(CommandSender talker, Command command, String str, String[] args){
		if(talker instanceof Player){
			Player p = (Player) talker;
			if(str.equalsIgnoreCase("tribe") && p.isOp()){
				SelectTribe(p);
				return true;
			} else if(str.equalsIgnoreCase("tribeset") && p.isOp()){
				String tribe, color;
				for(int i = 0; i < 9; i++){
					switch(i){
					case 0: tribe = "salamander"; color = "dark_red"; break;
					case 1: tribe = "undine"; color = "blue"; break;
					case 2: tribe = "noum"; color = "gold"; break;
					case 3: tribe = "catsy"; color = "yellow"; break;
					case 4: tribe = "sprigon"; color = "dark_gray"; break;
					case 5: tribe = "raplercon"; color = "gray"; break;
					case 6: tribe = "impe"; color = "dark_purple"; break;
					case 7: tribe = "silpe"; color = "green"; break;
					case 8: tribe = "puka"; color = "red"; break;
					
					default: return true;
					}
					
					Bukkit.dispatchCommand(getServer().getConsoleSender(), "scoreboard teams add "+tribe);
					Bukkit.dispatchCommand(getServer().getConsoleSender(), "scoreboard teams option "+tribe+" friendlyfire true");
					Bukkit.dispatchCommand(getServer().getConsoleSender(), "scoreboard teams option "+tribe+" color "+color);
				}
			}else if(str.equalsIgnoreCase("����") && getTribe(p).equalsIgnoreCase("��������")){
				if(p.getItemInHand() == null || p.getItemInHand().getType() == Material.AIR){
					p.sendMessage(MS+"������ �������� �տ� ����ּ���.");
					return true;
				}
				if(Takeitem(p, 265, 20)){
					ItemStack item = p.getItemInHand();
					item.setDurability((short) ((item.getType()
							.getMaxDurability()) - (item.getType()
							.getMaxDurability())));
					p.sendMessage(MS+"�������� �����ƽ��ϴ�.");
				} else {
					p.sendMessage(MS+"ö���� �����մϴ�.");
				}
				return true;
			}else if(str.equalsIgnoreCase("stat") || str.equalsIgnoreCase("����")){
				if(!invenMap.containsKey(p.getName())){
					Inventory inven = Bukkit.createInventory(null, 9, "��c��l����");
					inven.setContents(stat.getContents());
					invenMap.put(p.getName(), inven);
				}
				
				UpdateStatInven(p);
				
				p.openInventory(invenMap.get(p.getName()));
			}else if(str.equalsIgnoreCase("statset") && p.isOp()){
				try{
				power_rate = Float.valueOf(args[0]);
				accur_rate = Float.valueOf(args[1]);
				defen_rate = Float.valueOf(args[2]);
				p.sendMessage("������ :"+power_rate+" ���߷� ���� : "+accur_rate+" ������ : "+defen_rate);
				}catch(Exception e){
					p.sendMessage("/statset <������0.5> <���߷�����5> <������0.5> \n/statreset - �ʱ�ȭ");
				}
			}else if(str.equalsIgnoreCase("statreset") && p.isOp()){
				power_rate = 0.5f;
				accur_rate = 5f;
				defen_rate = 0.5f;
				p.sendMessage(Main.MS+"�ʱ�ȭ �Ϸ�");
			}else if(str.equalsIgnoreCase("returnstat")){
				int amt = 0;
				if(statMap.containsKey(p.getName())){
					amt += statMap.get(p.getName());
					statMap.put(p.getName(), 0);
				}
				if(accurMap.containsKey(p.getName())){
					amt += accurMap.get(p.getName());
					accurMap.put(p.getName(), 0);
				}
				if(powerMap.containsKey(p.getName())){
					amt += powerMap.get(p.getName());
					powerMap.put(p.getName(), 0);
				}
				if(defenMap.containsKey(p.getName())){
					amt += defenMap.get(p.getName());
					defenMap.put(p.getName(), 0);
				}
				statMap.put(p.getName(), amt);
				p.sendMessage(Main.MS+"��罺���� �ʱ�ȭ �Ǿ����ϴ�. - Sp��ȯ");
			}
		}
		return false;
	}
	
	public void UpdateStatInven(Player p){
		Inventory inv = invenMap.get(p.getName());
		ItemMeta meta = inv.getItem(0).getItemMeta();
		meta.setDisplayName("��c����� ���� SP : "+(statMap.containsKey(p.getName()) ? statMap.get(p.getName()) : 0));
		inv.getItem(0).setItemMeta(meta);
		
		meta = inv.getItem(3).getItemMeta();
		meta.setDisplayName("��c�� : "+(powerMap.containsKey(p.getName()) ? powerMap.get(p.getName()) : 0));
		inv.getItem(3).setItemMeta(meta);
		
		meta = inv.getItem(5).getItemMeta();
		meta.setDisplayName("��c���� : "+(accurMap.containsKey(p.getName()) ? accurMap.get(p.getName()) : 0));
		inv.getItem(5).setItemMeta(meta);
		
		meta = inv.getItem(7).getItemMeta();
		meta.setDisplayName("��c��� : "+(defenMap.containsKey(p.getName()) ? defenMap.get(p.getName()) : 0));
		inv.getItem(7).setItemMeta(meta);
		
		saveStat(p.getName());
	}
	public void saveTribe(){
		Set<String> keySet = tribelist.keySet();
		for(String s : keySet){
			if(!saveTribeData(s)) getLogger().info(s+" ���� ���� �����͸� ���� �� ���� �߻�");
		}
		getLogger().info("��� ���� ������ ���� �Ϸ�");
	}
	
	public void saveAllStat(){
		Set<String> keySet = statMap.keySet();
		for(String s : keySet){
			saveStat(s);
		}
		getLogger().info("��� ���� ������ ���� �Ϸ�");
	}
	
	public String getTribe(Player p){
		if(tribelist.containsKey(p.getName())) return tribelist.get(p.getName());
		return "�޸�";
	}
	
	public void ClickStat(Player p, int slot){
		p.playSound(p.getLocation(), Sound.CHICKEN_EGG_POP, 2.0f, 1.5f);
		switch(slot){
		case 3: if(!statMap.containsKey(p.getName()) || statMap.get(p.getName()) <= 0){
			p.sendMessage(Main.MS+"���� SP�� �������� �ʽ��ϴ�."); return;
		}
		if(powerMap.containsKey(p.getName())){
			powerMap.put(p.getName(), powerMap.get(p.getName())+1);
		}else{
			powerMap.put(p.getName(), 1);
		}
		statMap.put(p.getName(), statMap.get(p.getName())-1);
		p.sendMessage(Main.MS+"���� 1 �÷Ƚ��ϴ�."); UpdateStatInven(p); break;
		case 5: if(!statMap.containsKey(p.getName()) || statMap.get(p.getName()) <= 0){
			p.sendMessage(Main.MS+"���� SP�� �������� �ʽ��ϴ�."); return;
		}
		if(accurMap.containsKey(p.getName())){
			accurMap.put(p.getName(), accurMap.get(p.getName())+1);
		}else{
			accurMap.put(p.getName(), 1);
		}
		statMap.put(p.getName(), statMap.get(p.getName())-1);
		p.sendMessage(Main.MS+"���߷��� 1 �÷Ƚ��ϴ�."); UpdateStatInven(p); break;
		case 7: if(!statMap.containsKey(p.getName()) || statMap.get(p.getName()) <= 0){
			p.sendMessage(Main.MS+"���� SP�� �������� �ʽ��ϴ�."); return;
		}
		if(defenMap.containsKey(p.getName())){
			defenMap.put(p.getName(), defenMap.get(p.getName())+1);
		}else{
			defenMap.put(p.getName(), 1);
		}
		statMap.put(p.getName(), statMap.get(p.getName())-1);
		p.sendMessage(Main.MS+"�� 1 �÷Ƚ��ϴ�."); UpdateStatInven(p); break;
		
		default: return;
		}
	}
	
	public void saveStat(String p){
		try{
			File dataFile = new File(this.getDataFolder(), p+".stat");
			if(dataFile.exists()) dataFile.delete();
			FileConfiguration statConfig = YamlConfiguration.loadConfiguration(dataFile);
			if(statMap.containsKey(p)){
				statConfig.set("����", statMap.get(p));
			}
			if(powerMap.containsKey(p)){
				statConfig.set("��", powerMap.get(p));
			}
			if(accurMap.containsKey(p)){
				statConfig.set("����", accurMap.get(p));
			}
			if(defenMap.containsKey(p)){
				statConfig.set("���", defenMap.get(p));
			}
			statConfig.save(dataFile);
		}catch(Exception e){
			getLogger().info(p+"���� ���� �����͸� �����ϴ� �� �����߻�");
			return;
		}
	}
		
	public boolean saveTribeData(String s){
		try {
			File dataFile = new File(this.getDataFolder(), s+".tribe");
			if (dataFile.exists()) dataFile.delete();
			FileConfiguration TribeConfig = YamlConfiguration.loadConfiguration(dataFile);

			TribeConfig.set("����", tribelist.get(s));

			TribeConfig.save(dataFile);
			return true;
		} catch (Exception ex) {
			return false;
		}
	}
	//220
	public void SelectTribe(Player p){
		p.openInventory(tribeselect);
		p.sendMessage(MS+"������ �������ּ���.");
	}
	
	public void SetTribe(Player p, String str){
		for(PotionEffect potion : p.getActivePotionEffects()){
			p.removePotionEffect(potion.getType());
		}
		p.setMaxHealth(20);
		tribelist.put(p.getName(), str);
		saveTribeData(p.getName());
		String teamname;
		switch(str){
		case "��󸸴�": teamname = "salamander"; break;
		case "����": teamname = "undine"; break;
		case "���": teamname = "noum"; break;
		case "ĳƮ��": teamname = "catcy"; break;
		case "��������": teamname = "sprigon"; break;
		case "��������": teamname = "raplercon"; break;
		case "����": teamname = "impe"; break;
		case "����": teamname = "silpe"; break;
		case "ǪŰ": teamname = "puki"; break;
		
		default: return;
		}
		Bukkit.dispatchCommand(p.getServer().getConsoleSender(), "scoreboard teams join "+teamname+" "+p.getName());
		if(getTribe(p).equalsIgnoreCase("���")){
			p.setMaxHealth(30);
		}
		if(getTribe(p).equalsIgnoreCase("����") && !isDay){
			p.addPotionEffect(new PotionEffect(PotionEffectType.NIGHT_VISION, 72000, 1));
		}
		if(getTribe(p).equalsIgnoreCase("����")) {
			p.addPotionEffect(new PotionEffect(PotionEffectType.SPEED, 72000, 0));
		}
		p.sendMessage(MS+"���� ����� ������ "+str+" �Դϴ�.");
	}
	
	public void ClickTribeMenu(Player p, int slot){
		switch(slot){
		case 11: p.closeInventory(); SetTribe(p, "��󸸴�"); return;
		case 13: p.closeInventory(); SetTribe(p, "����"); return;
		case 15: p.closeInventory(); SetTribe(p, "���"); return;
		case 20: p.closeInventory(); SetTribe(p, "ĳƮ��"); return;
		case 22: p.closeInventory(); SetTribe(p, "��������"); return;
		case 24: p.closeInventory(); SetTribe(p, "��������"); return;
		case 29: p.closeInventory(); SetTribe(p, "����"); return;
		case 31: p.closeInventory(); SetTribe(p, "����"); return;
		case 33: p.closeInventory(); SetTribe(p, "ǪŰ"); return;
		
		default: return;
		}
	}
	
	public static int Getrandom(int min, int max){
		  return (int)(Math.random() * (max-min+1)+min);
		}
	
	public void Addhp(Player p, int amt){
		int hp = p.getHealth()+amt;
		if(hp > p.getMaxHealth()){
			hp = p.getMaxHealth();
		}
		p.setHealth(hp);
		p.getWorld().playSound(p.getLocation(), Sound.LEVEL_UP, 2.5f, 2.0f);
	}
	
	@EventHandler
	public void onClickInventory(InventoryClickEvent e){
		if(!(e.getWhoClicked() instanceof Player)) return;
		Player p = (Player) e.getWhoClicked();
		String title = e.getInventory().getTitle();
		switch(title){
		case "��c��l���� ����": e.setCancelled(true); p.updateInventory(); ClickTribeMenu(p, e.getSlot()); return;
		case "��c��l����": e.setCancelled(true); p.updateInventory(); ClickStat(p, e.getSlot()); return;
		default: return;
		}
	}
	
	@EventHandler
	public void onLevelChange(PlayerLevelChangeEvent e){
		Player p = e.getPlayer();
		if(statwait.contains(p.getName())) return;
		if(e.getNewLevel() > 50){
			p.setLevel(50);
			p.setExp(1);
			p.sendMessage(Main.MS+"�ִ� ������ 50���� �Դϴ�.");
		}else if(e.getNewLevel() > e.getOldLevel()){
			if(statMap.containsKey(p.getName())){
				statMap.put(p.getName(), statMap.get(p.getName())+1);
				p.sendMessage(Main.MS+"������ ����Ͽ� 1SP�� �����̽��ϴ�. ���� SP "+statMap.get(p.getName()));
			}else{
				statMap.put(p.getName(), 1);
			}
		}
	}
	
	public void LoadTribe(Player p){
		File dataFile = new File(this.getDataFolder(), p.getName()+".tribe");
		if (!dataFile.exists()) return;
		FileConfiguration TribeConfig = YamlConfiguration.loadConfiguration(dataFile);

		String tribe = TribeConfig.getString("����");
		if(tribe == null) return;
		tribelist.put(p.getName(), tribe);
		if(getTribe(p).equalsIgnoreCase("���")){
			p.setMaxHealth(30);
		}
		if(getTribe(p).equalsIgnoreCase("����") && !isDay){
			p.addPotionEffect(new PotionEffect(PotionEffectType.NIGHT_VISION, 72000, 1));
		}
		if(getTribe(p).equalsIgnoreCase("����")){
			p.addPotionEffect(new PotionEffect(PotionEffectType.SPEED, 72000, 0));
		}
		Bukkit.broadcastMessage("��2"+getTribe(p)+"��f ���� ��6"+p.getName()+" ��f�Բ��� ��c�α��� ��f�ϼ̽��ϴ�.");
	}
	
	public void LoadStat(Player p){
		File dataFile = new File(this.getDataFolder(), p.getName()+".stat");
		if (!dataFile.exists()) return;
		FileConfiguration statConfig = YamlConfiguration.loadConfiguration(dataFile);

		int stat = statConfig.getInt("����");
		statMap.put(p.getName(), stat);
		int power = statConfig.getInt("��");
		powerMap.put(p.getName(), power);
		int accur = statConfig.getInt("����");
		accurMap.put(p.getName(), accur);
		int defen = statConfig.getInt("���");
		defenMap.put(p.getName(), defen);
	}
	
	@EventHandler
	public void onJoin(PlayerJoinEvent e){
		Player p = e.getPlayer();
		try {
			LoadTribe(p);
		} catch (Exception ex) {
			Bukkit.broadcastMessage("��2"+getTribe(p)+"��f ���� ��6"+p.getName()+" ��f�Բ��� ��c�α��� ��f�ϼ̽��ϴ�.");
			getLogger().info(p.getName()+" ���� ���� �����͸� �ҷ����� �� ������ �߻��߽��ϴ�.");
		}
		try {
			LoadStat(p);
		} catch (Exception ex) {
			getLogger().info(p.getName()+" ���� ���� �����͸� �ҷ����� �� ������ �߻��߽��ϴ�.");
		}
	}
	
	@EventHandler
	public void onInteract(PlayerInteractEvent e){
		if(e.getAction() == Action.RIGHT_CLICK_BLOCK){
			if(e.getClickedBlock().getTypeId() == 220){
				if(getTribe(e.getPlayer()).equalsIgnoreCase("�޸�")){
					SelectTribe(e.getPlayer());
				} 
			}
		}
	}
	
	@EventHandler
	public void onDamage(EntityDamageEvent e){
		if(!(e.getEntity() instanceof Player)) return;
		DamageCause dc = e.getCause();
		Player p = (Player) e.getEntity();
		int damage = e.getDamage();
		if(getTribe(p).equalsIgnoreCase("��󸸴�"))
		{
			if (dc.equals(DamageCause.LAVA) ||
				dc.equals(DamageCause.FIRE) ||
				dc.equals(DamageCause.FIRE_TICK))
			{
				e.setCancelled(true);
				p.setFireTicks(0);
			}
			else if (dc.equals(DamageCause.DROWNING))
				damage*=2;
		}
		if(getTribe(p).equalsIgnoreCase("��󸸴�"))
		{
			if (dc.equals(DamageCause.DROWNING))
				e.setCancelled(true);
		} 
		if(defenMap.containsKey(p.getName())) 
		damage = (int) (damage - defenMap.get(p.getName())*defen_rate <= 0 ? 1 : damage - defenMap.get(p.getName())*defen_rate);
		e.setDamage(damage);
	}
	
	@EventHandler(priority = EventPriority.HIGHEST)
	public void onPvp(EntityDamageByEntityEvent e){
		Player d = null;
		Player p = null;
		int damage = e.getDamage();
		if(e.getDamager() instanceof Player){
			d = (Player) e.getDamager();
			if(getTribe(d).equalsIgnoreCase("��󸸴�")){
				if(Getrandom(1, 33) == 1){
					damage += 4;
					d.sendMessage("��c��l�޼Ҹ� �����߽��ϴ�!");
					d.getWorld().playSound(d.getLocation(),Sound.ANVIL_LAND , 2.5f, 1.2f);
				} else {
					damage += 2;
				}
			} else if(getTribe(d).equalsIgnoreCase("����") && Getrandom(1, 25) == 1){
				int rn = Getrandom(1, e.getDamage());
				damage += rn;
				d.sendMessage("��c��l������ �ѹ� �� �����Ͽ� "+rn+"��ŭ�� �������� �� ���!");
			} else if(getTribe(d).equalsIgnoreCase("����") && !isDay){
				damage += 1;
			}
		}
		if(e.getEntity() instanceof Player){
			p = (Player) e.getEntity();
			if(getTribe(p).equalsIgnoreCase("ĳƮ��") && Getrandom(1, 9) == 1){
				e.setCancelled(true);
				p.sendMessage("��c������ ȸ���߽��ϴ�!");
				p.getWorld().playSound(p.getLocation(), Sound.BAT_TAKEOFF, 2.0f, 1.6f);
			} else if(getTribe(p).equalsIgnoreCase("���") && Getrandom(1, 50) == 1){
				e.setCancelled(true);
				p.sendMessage("��c��l����� ������ ����߽��ϴ�!");
				Addhp(p, damage);
			} else if(getTribe(p).equalsIgnoreCase("����") && !isDay){
				if(damage - 1 < 0){
					damage = 0;
				}else{
					damage -= 1;
				}
			} else if(getTribe(p).equalsIgnoreCase("ǪŰ") && Getrandom(1, 10) == 1){
				for(Player t : Bukkit.getOnlinePlayers()){
					if(getTribe(t).equalsIgnoreCase("ǪŰ")){
						t.sendMessage("��6"+p.getName()+" ��f�Կ� ���� �ɷ��� �ߵ��˴ϴ�.");
						t.addPotionEffect(new PotionEffect(PotionEffectType.REGENERATION, 100, 1));
					}
				}
			}
		}
		if(accurMap.containsKey(d.getName()) && Getrandom(1, 1000) <= accurMap.get(d.getName())*accur_rate){
			 damage *= 2;
			 p.sendMessage(Main.MS+"ũ��Ƽ��! (������ 2��)");
		}
		if(powerMap.containsKey(d.getName())) damage += powerMap.get(d.getName())*power_rate;
		e.setDamage(damage);
		if(d == null || p == null) return;
		if(getTribe(d).equalsIgnoreCase("��������")){
			if(Getrandom(1, 20) == 1){
				p.addPotionEffect(new PotionEffect(PotionEffectType.POISON, 120, 0));
				d.sendMessage("��c����� ���� �ߵ� �ƽ��ϴ�!");
			}
			if(Getrandom(1, 20) == 1){
				p.addPotionEffect(new PotionEffect(PotionEffectType.BLINDNESS, 120, 0));
				d.sendMessage("��c����� �Ǹ� �ƽ��ϴ�!");
			}
		}
	}
	
	@EventHandler
	public void onQuit(PlayerQuitEvent e){
		Player p = e.getPlayer();
		e.setQuitMessage(null);
		Bukkit.broadcastMessage("��2"+getTribe(p)+"��f ���� ��6"+p.getName()+" ��f�Բ��� ��c�α׾ƿ� ��f�ϼ̽��ϴ�.");
		saveTribeData(p.getName());
		saveStat(p.getName());
	}
	
	@EventHandler
	public void onPlayerDeath(PlayerDeathEvent e){
		Player p = e.getEntity();
		levellist.put(e.getEntity().getName(), e.getEntity().getLevel());
		explist.put(e.getEntity().getName(), e.getEntity().getExp());
		statwait.add(p.getName());
		e.setDroppedExp(0);
		e.setDeathMessage(null);
		if(p.getKiller() != null){
			Bukkit.broadcastMessage("��2"+getTribe(p)+" ��f������6 "+p.getName()+"��f �Բ��� "+
					"��2"+getTribe(p.getKiller())+"��f���� ��6"+p.getKiller().getName()+" ��f�Բ� ������ ���ϼ̽��ϴ�.");
		} else {
			Bukkit.broadcastMessage("��2"+getTribe(p)+" ��f������6 "+p.getName()+"��f �Բ��� ��� �ϼ̽��ϴ�.");
		}
	}
	
	@EventHandler
	public void onRespawn(final PlayerRespawnEvent e){
		final Player p = e.getPlayer();
		Bukkit.getScheduler().scheduleSyncDelayedTask(this, new Runnable(){
			public void run(){
			if(getTribe(p).equalsIgnoreCase("���")){
				p.setMaxHealth(30);
				p.damage(1);
				p.setHealth(p.getMaxHealth());
			}
			if(getTribe(p).equalsIgnoreCase("����") && !isDay){
				p.addPotionEffect(new PotionEffect(PotionEffectType.NIGHT_VISION, 72000, 1));
			}
			if(getTribe(p).equalsIgnoreCase("����")){
				p.addPotionEffect(new PotionEffect(PotionEffectType.SPEED, 72000, 0));
			}
			}
		}, 20L);
		Bukkit.getScheduler().scheduleSyncDelayedTask(this, new Runnable(){
			public void run(){
				e.getPlayer().setLevel(levellist.get(p.getName()));
				e.getPlayer().setExp(explist.get(p.getName()));
				statwait.remove(p.getName());
			}
		}, 5l);
	}
	
	@EventHandler
	public void onPlayerMove(PlayerMoveEvent e){
		Player p = e.getPlayer();
		if(getTribe(p).equalsIgnoreCase("����")){
			if(p.getLocation().getBlock().getType() == Material.WATER || p.getLocation().getBlock().getType() == Material.STATIONARY_WATER){
				p.addPotionEffect(new PotionEffect(PotionEffectType.REGENERATION, 72000, 0));
				p.addPotionEffect(new PotionEffect(PotionEffectType.WATER_BREATHING, 72000, 0));
				p.addPotionEffect(new PotionEffect(PotionEffectType.SPEED, 72000, 4));
			} else {
				p.removePotionEffect(PotionEffectType.REGENERATION);
				p.removePotionEffect(PotionEffectType.WATER_BREATHING);
				p.removePotionEffect(PotionEffectType.SPEED);
			}
		}
	}
}
