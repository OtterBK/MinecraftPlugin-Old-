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
import org.bukkit.event.entity.EntityDamageByEntityEvent;
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
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.inventory.meta.LeatherArmorMeta;
import org.bukkit.plugin.RegisteredServiceProvider;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.potion.PotionType;
import org.bukkit.util.Vector;

import net.milkbowl.vault.Vault;
import net.milkbowl.vault.VaultEco;
import net.milkbowl.vault.VaultEco.VaultBankAccount;
import net.milkbowl.vault.economy.Economy;
import net.milkbowl.vault.economy.EconomyResponse;

public class Main extends JavaPlugin implements Listener
{
	public static boolean check_start = false;
	public static boolean check_lobbystart = false;
	public static List<Player> plist = new ArrayList<Player>(); 
	public static String MS = "§f[ §aEG §f] ";
	public static Location Join_loc;
	public static Location Main_loc;
	public static Location Bluespawn_loc;
	public static Location Redspawn_loc;
	public static Location Bluecore_loc;
	public static Location Redcore_loc;
	public static int tasknum[] = new int[200];
	public static int tasktime[] = new int[200];
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
	public static int Bluecore_cnt = 9;
	public static int Redcore_cnt = 9;
	public static ItemStack bluearmor1 = null;
	public static ItemStack bluearmor2 = null;
	public static ItemStack bluearmor3 = null;
	public static ItemStack bluearmor4 = null;
	public static ItemStack redarmor1 = null;
	public static ItemStack redarmor2 = null;
	public static ItemStack redarmor3 = null;
	public static ItemStack redarmor4 = null;
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
	}
	
	public void Createjobsinv()
	{
		jobs = Bukkit.createInventory(null, 9, "직업선택");
		ItemStack item = new ItemStack(Material.STONE_SWORD, 1);
		ItemMeta meta = item.getItemMeta();
		meta.setDisplayName("§e탱커");
		item.setItemMeta(meta);
		jobs.setItem(0, item);
		
		item = new ItemStack(Material.BOW, 1);
		meta = item.getItemMeta();
		meta.setDisplayName("§e아쳐");
		item.setItemMeta(meta);
		jobs.setItem(2, item);
		
		item = new ItemStack(Material.IRON_SWORD, 1);
		meta = item.getItemMeta();
		meta.setDisplayName("§e워리어");
		item.setItemMeta(meta);
		jobs.setItem(4, item);
		
		item = new ItemStack(Material.DIAMOND_SWORD, 1);
		meta = item.getItemMeta();
		meta.setDisplayName("§e어쌔신");
		item.setItemMeta(meta);
		jobs.setItem(6, item);
		
		item = new ItemStack(Material.WOOD_SWORD, 1);
		meta = item.getItemMeta();
		meta.setDisplayName("§e힐러");
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
		getLogger().info("코어사수전 플러그인이 로드 되었습니다.");
		LoadConfig();
		Createjobsinv();
		for(int i = 0 ; i < 200; i++)
		{
			tasknum[i] = -5;
			tasktime[i] = -5;
		}
		bluearmor4 = new ItemStack(Material.LEATHER_BOOTS, 1);
		LeatherArmorMeta meta4 = (LeatherArmorMeta) bluearmor4.getItemMeta();
		meta4.setColor(Color.BLUE);
		meta4.addEnchant(Enchantment.DURABILITY, 100, true);
		bluearmor4.setItemMeta(meta4);

		bluearmor3 = new ItemStack(Material.LEATHER_LEGGINGS, 1);
		LeatherArmorMeta meta3 = (LeatherArmorMeta) bluearmor3.getItemMeta();
		meta3.setColor(Color.BLUE);
		meta3.addEnchant(Enchantment.DURABILITY, 100, true);
		bluearmor3.setItemMeta(meta3);

		bluearmor2 = new ItemStack(Material.LEATHER_CHESTPLATE, 1);
		LeatherArmorMeta meta2 = (LeatherArmorMeta) bluearmor2.getItemMeta();
		meta2.setColor(Color.BLUE);
		meta2.addEnchant(Enchantment.DURABILITY, 100, true);
		bluearmor2.setItemMeta(meta2);

		bluearmor1 = new ItemStack(Material.LEATHER_HELMET, 1);
		LeatherArmorMeta meta1 = (LeatherArmorMeta) bluearmor1.getItemMeta();
		meta1.setColor(Color.BLUE);
		meta1.addEnchant(Enchantment.DURABILITY, 100, true);
		bluearmor1.setItemMeta(meta1);

		redarmor1 = new ItemStack(Material.LEATHER_HELMET, 1);
		LeatherArmorMeta meta = (LeatherArmorMeta) redarmor1.getItemMeta();
		meta.setColor(Color.RED);
		meta.addEnchant(Enchantment.DURABILITY, 100, true);
		redarmor1.setItemMeta(meta);

		redarmor2 = new ItemStack(Material.LEATHER_CHESTPLATE, 1);
		meta = (LeatherArmorMeta) redarmor2.getItemMeta();
		meta.setColor(Color.RED);
		meta.addEnchant(Enchantment.DURABILITY, 100, true);
		redarmor2.setItemMeta(meta);

		redarmor3 = new ItemStack(Material.LEATHER_LEGGINGS, 1);
		meta = (LeatherArmorMeta) redarmor3.getItemMeta();
		meta.setColor(Color.RED);
		meta.addEnchant(Enchantment.DURABILITY, 100, true);
		redarmor3.setItemMeta(meta);

		redarmor4 = new ItemStack(Material.LEATHER_BOOTS, 1);
		meta = (LeatherArmorMeta) redarmor4.getItemMeta();
		meta.setColor(Color.RED);
		meta.addEnchant(Enchantment.DURABILITY, 100, true);
		redarmor4.setItemMeta(meta);

        if (!setupEconomy() ) {
            getLogger().info("[버그 발생 우려 ] Vault플러그인이 인식되지 않았습니다!");
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
		getLogger().info("코어사수전 플러그인이 언로드 되었습니다.");
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
		p.sendMessage(MS+"버젼 0.61");
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
			}
			if(args[1].equalsIgnoreCase("redcore"))
			{
				if(!getConfig().isConfigurationSection("Location_RedCore"))
				{
					getConfig().createSection("Location_RedCore");
				}
				getConfig().getConfigurationSection("Location_RedCore").set("world", p.getWorld().getName());
				getConfig().getConfigurationSection("Location_RedCore").set("x", p.getLocation().getBlockX());
				getConfig().getConfigurationSection("Location_RedCore").set("y", p.getLocation().getBlockY()+1);
				getConfig().getConfigurationSection("Location_RedCore").set("z", p.getLocation().getBlockZ());
			}
			if(args[1].equalsIgnoreCase("bluecore"))
			{
				if(!getConfig().isConfigurationSection("Location_BlueCore"))
				{
					getConfig().createSection("Location_BlueCore");
				}
				getConfig().getConfigurationSection("Location_BlueCore").set("world", p.getWorld().getName());
				getConfig().getConfigurationSection("Location_BlueCore").set("x", p.getLocation().getBlockX());
				getConfig().getConfigurationSection("Location_BlueCore").set("y", p.getLocation().getBlockY()+1);
				getConfig().getConfigurationSection("Location_BlueCore").set("z", p.getLocation().getBlockZ());
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
		}
	}
	
	public void Sendmessage(String str)
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
	
	public void GameJoin(Player p)
	{
		if(plist.contains(p))
		{
			p.sendMessage(MS+"이미 게임에 참여중이십니다.");
			return;
		}
		if(plist.size() >= 12)
		{
			p.sendMessage(MS+"이미 최대인원(12)입니다. 해당 게임은 도중에 참여가 가능하기때문에 나중에 다시 시도해주세요.");
			return;
		}
		if(check_start)
		{
			plist.add(p);
			if(bluelist.size() <= redlist.size())
			{
				bluelist.add(p);
				p.teleport(Bluespawn_loc);
			}
			else
			{
				redlist.add(p);
				p.teleport(Redspawn_loc);
			}
			p.sendMessage(MS+"코어사수전 게임에 중도 참여하셨습니다.");
		}
		else
		{
			plist.add(p);
			p.teleport(Join_loc);
			Sendmessage(MS+p.getName()+" 님이 코어사수전 게임에 참여하셨습니다. 인원 (§e "+plist.size()+"§7 / §c8 §f)");
			if(!(check_start || check_lobbystart) && plist.size() >= 8)
			{
				Startgame();
			}
		}
	}
	
	public void Canceltask(int cur)
	{
		Bukkit.getScheduler().cancelTask(tasknum[cur]);
		tasknum[cur] = -5;
		tasktime[cur] = -5;
	}
	
	public void Startgame()
	{
		check_lobbystart = true;
		final int cur = Getcursch();
		tasktime[cur] = 5;
		Bukkit.broadcastMessage(MS+"코어사수전 게임이 곧 시작됩니다.");
		tasknum[cur] = Bukkit.getScheduler().scheduleSyncRepeatingTask(this, new Runnable()
		{
			public void run()
			{
				if(tasktime[cur] > 0)
				{
					Sendmessage(MS+"게임이 "+tasktime[cur]*10+" 초 후 시작됩니다.");
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
							p.sendMessage(MS+"당신은 블루팀입니다. 직업군을 선택해주세요.");
						}
						else
						{
							redlist.add(p);
							p.teleport(Redspawn_loc);
							p.sendMessage(MS+"당신은 레드팀입니다. 직업군을 선택해주세요.");
						}
					}
					check_start = true;
				}
			}
		}, 200L, 200L);
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
				+ChatColor.RESET+"초 후 스킬을 사용하실 수 있습니다.");
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
				p.sendMessage(MS+"팀채팅을 껐습니다.");
			}
			else
			{
				teamchat.add(p.getName());
				p.sendMessage(MS+"팀채팅을 켰습니다.");
			}
			return;
		}
		
		if(!jlist.containsKey(p.getName()))
		{
			return;
		}
		
		switch(jlist.get(p.getName()))
		{
		case "탱커":
			switch(id)
			{
			
			case 264:
				if(Checkcooldown(p, "1"))
				{
					Setcooldown(p.getName()+"1", 9);
					p.playSound(p.getLocation(), Sound.ANVIL_LAND, 2.0f, 0.5f);
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
					p.playSound(p.getLocation(), Sound.ANVIL_BREAK, 2.0f, 0.5f);
					for(Player t : (bluelist.contains(p) ? redlist : bluelist))
					{
						if(t.getLocation().distance(p.getLocation()) <= 4)
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
					Setcooldown(p.getName()+"3", 19);
					p.playSound(p.getLocation(), Sound.ANVIL_USE, 2.0f, 0.5f);
					for(Player t : (!bluelist.contains(p) ? redlist : bluelist))
					{
						if(t.getLocation().distance(p.getLocation()) <= 4 && !jlist.get(t.getName()).equalsIgnoreCase("탱커"))
						{
							t.addPotionEffect(new PotionEffect(PotionEffectType.DAMAGE_RESISTANCE, 60, 1));
						}
					}
					return;
				}
				
				break;
				
			default: return;
			
			}  break;
			
		case "아쳐":
			switch(id)
			{
			
			case 264:
				if(Checkcooldown(p, "1"))
				{
					Setcooldown(p.getName()+"1", 12);
					archer1.add(p.getName());
					p.playSound(p.getLocation(), Sound.BLAZE_HIT, 2.0f, 0.5f);
					Bukkit.getScheduler().scheduleSyncDelayedTask(this, new Runnable()
							{
								public void run()
								{
									if(archer1.contains(p.getName()))
									{
										archer1.remove(p.getName());
										p.sendMessage(MS+"확인사살의 지속시간이 끝났습니다.");
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
					p.playSound(p.getLocation(), Sound.IRONGOLEM_HIT, 2.0f, 0.5f);
					Bukkit.getScheduler().scheduleSyncDelayedTask(this, new Runnable()
							{
								public void run()
								{
									if(archer2.contains(p.getName()))
									{
										archer2.remove(p.getName());
										p.sendMessage(MS+"구속화살의 지속시간이 끝났습니다.");
									}
								}
							}, 80L);
					return;
				}
				
				break;
				
			default: return;
			
			}  break;
			
		case "어쌔신":
			switch(id)
			{
			
			case 264:
				if(Checkcooldown(p, "1"))
				{
					Setcooldown(p.getName()+"1", 13);
					p.playSound(p.getLocation(), Sound.CREEPER_HISS, 2.0f, 0.5f);
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
								p.sendMessage(MS+"은신이 풀려 암살스킬이 해제 됐습니다.");
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
						p.sendMessage(MS+"은신 상태일때만 사용 가능합니다.");
						return;
					}
					Setcooldown(p.getName()+"2", 10);
					assasin1.add(p.getName());
					p.playSound(p.getLocation(), Sound.CREEPER_DEATH, 2.0f, 0.5f);
					return;
				}
				
				break;
				
			default: return;
			
			}  break;
			
		case "워리어":
			switch(id)
			{
			
			case 264:
				if(Checkcooldown(p, "1"))
				{
					Setcooldown(p.getName()+"1", 13);
					p.playSound(p.getLocation(), Sound.GHAST_DEATH, 2.0f, 2.0f);
					p.addPotionEffect(new PotionEffect(PotionEffectType.INCREASE_DAMAGE, 60, 0), true);
					return;
				}
				
				break;
				
			case 388:
				if(Checkcooldown(p, "2"))
				{
					Setcooldown(p.getName()+"2", 11);
					p.playSound(p.getLocation(), Sound.BAT_TAKEOFF, 2.0f, 1.5f);
					p.addPotionEffect(new PotionEffect(PotionEffectType.SPEED, 80, 0), true);
					return;
				}
				
				break;
				
			case 265:
				if(Checkcooldown(p, "3"))
				{
					Setcooldown(p.getName()+"3", 20);
					p.playSound(p.getLocation(), Sound.EXPLODE, 2.0f, 1.2f);
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
			
			
			
		case "힐러":
			switch(id)
			{
			
			case 264:
				if(Checkcooldown(p, "1"))
				{
					Setcooldown(p.getName()+"1", 18);
					healer1.add(p.getName());
					p.playSound(p.getLocation(), Sound.LEVEL_UP, 2.0f, 1.5f);
					Bukkit.getScheduler().scheduleSyncDelayedTask(this, new Runnable()
					{
						public void run()
						{
							if(healer1.contains(p.getName()))
							{
								healer1.remove(p.getName());
								p.sendMessage(MS+"치유의 화살의 지속시간이 끝났습니다.");
							}
						}
					}, 80L);
					return;
				}
				
				break;
				
			case 388:
				if(Checkcooldown(p, "2"))
				{
					Setcooldown(p.getName()+"2", 11);
					p.playSound(p.getLocation(), Sound.ORB_PICKUP, 2.0f, 0.8f);
					for(Player t : (!bluelist.contains(p) ? redlist : bluelist))
					{
						if(t.getLocation().distance(p.getLocation()) <= 4)
						{
							int hp = t.getHealth();
							hp += 8;
							if(hp > 20)
							{
								hp = 20;
							}
							t.setHealth(hp);
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
		meta.setDisplayName("§e팀채팅");
		lorelist.clear();
		lorelist.add("- §7우클릭시 팀챗을 켜거나 끕니다.");
		meta.setLore(lorelist);
		item.setItemMeta(meta);
		p.getInventory().setItem(8, item);
		
		switch(str)
		{
		case "탱커":
			jlist.put(p.getName(), "탱커");
			
			item = new ItemStack(Material.STONE_SWORD, 1);
			meta = item.getItemMeta();
			meta.setDisplayName("§b탱커");
			meta.addEnchant(Enchantment.KNOCKBACK, 1, true);
			meta.addEnchant(Enchantment.DURABILITY, 10, true);
			item.setItemMeta(meta);
			p.getInventory().setItem(0, item);
			
			item = new ItemStack(Material.DIAMOND, 1);
			meta = item.getItemMeta();
			meta.setDisplayName("§b철벽");
			lorelist.clear();
			lorelist.add("- §73초간 저항3");
			lorelist.add("- §c쿨타임 9초");
			meta.setLore(lorelist);
			item.setItemMeta(meta);
			p.getInventory().setItem(1, item);
			
			item = new ItemStack(Material.EMERALD, 1);
			meta = item.getItemMeta();
			meta.setDisplayName("§b제압");
			lorelist.clear();
			lorelist.add("- §7반경 3칸이내 적군 2초간 이동불가");
			lorelist.add("- §c쿨타임 17초");
			meta.setLore(lorelist);
			item.setItemMeta(meta);
			p.getInventory().setItem(2, item);
			
			item = new ItemStack(Material.IRON_INGOT, 1);
			meta = item.getItemMeta();
			meta.setDisplayName("§b함성");
			lorelist.clear();
			lorelist.add("- §7반경 4칸이내 팀원 저항1");
			lorelist.add("- §c쿨타임 19초");
			meta.setLore(lorelist);
			item.setItemMeta(meta);
			p.getInventory().setItem(3, item);
			
			p.addPotionEffect(new PotionEffect(PotionEffectType.DAMAGE_RESISTANCE, 72000, 0));
			
			break;
			
		case "아쳐":
			jlist.put(p.getName(), "아쳐");
			
			item = new ItemStack(Material.BOW, 1);
			meta = item.getItemMeta();
			meta.setDisplayName("§b아쳐");
			meta.addEnchant(Enchantment.ARROW_INFINITE, 1, true);
			meta.addEnchant(Enchantment.ARROW_DAMAGE, 1, true);
			meta.addEnchant(Enchantment.DURABILITY, 10, true);
			item.setItemMeta(meta);
			p.getInventory().setItem(0, item);
			
			item = new ItemStack(Material.DIAMOND, 1);
			meta = item.getItemMeta();
			meta.setDisplayName("§b확인사살");
			lorelist.clear();
			lorelist.add("- §7사용후 화살로 적을 맞출시");
			lorelist.add("- §7데미지 2배, 단 4초이내에 맞춰야한다.");
			lorelist.add("- §c쿨타임 12초");
			meta.setLore(lorelist);
			item.setItemMeta(meta);
			p.getInventory().setItem(1, item);
			
			item = new ItemStack(Material.EMERALD, 1);
			meta = item.getItemMeta();
			meta.setDisplayName("§b구속화살");
			lorelist.clear();
			lorelist.add("- §7사용후 화살로 적을 맞출시");
			lorelist.add("- §7실명1, 구속3 4초, 단 4초이내에 맞춰야한다.");
			lorelist.add("- §c쿨타임 14초");
			meta.setLore(lorelist);
			item.setItemMeta(meta);
			p.getInventory().setItem(2, item);
			
			item = new ItemStack(Material.ARROW, 64);
			p.getInventory().setItem(3, item);
			
			break;
			
		case "어쌔신":
			jlist.put(p.getName(), "어쌔신");
			
			item = new ItemStack(Material.DIAMOND_SWORD, 1);
			meta = item.getItemMeta();
			meta.setDisplayName("§b어쌔신");
			lorelist.clear();
			lorelist.add("- §7자신이 받는 모든 데미지는");
			lorelist.add("- §71.3배가 됩니다.");
			meta.setLore(lorelist);
			item.setItemMeta(meta);
			p.getInventory().setItem(0, item);
			
			item = new ItemStack(Material.DIAMOND, 1);
			meta = item.getItemMeta();
			meta.setDisplayName("§b은신");
			lorelist.clear();
			lorelist.add("- §76초간 모습을 감춥니다.");
			lorelist.add("- §c쿨타임 13초");
			meta.setLore(lorelist);
			item.setItemMeta(meta);
			p.getInventory().setItem(1, item);
			
			item = new ItemStack(Material.EMERALD, 1);
			meta = item.getItemMeta();
			meta.setDisplayName("§b암살");
			lorelist.clear();
			lorelist.add("- §7은신 상태에서만 사용가능");
			lorelist.add("- §7사용 후 첫 타격 데미지 1.5배");
			lorelist.add("- §7은신이 해제될 시 스킬 자동해제");
			lorelist.add("- §c쿨타임 10초");
			meta.setLore(lorelist);
			item.setItemMeta(meta);
			p.getInventory().setItem(2, item);
			
			break;
			
		case "워리어":
			jlist.put(p.getName(), "워리어");
			
			item = new ItemStack(Material.IRON_SWORD, 1);
			meta = item.getItemMeta();
			meta.setDisplayName("§b워리어");
			meta.addEnchant(Enchantment.DURABILITY, 10, true);
			item.setItemMeta(meta);
			p.getInventory().setItem(0, item);
			
			item = new ItemStack(Material.DIAMOND, 1);
			meta = item.getItemMeta();
			meta.setDisplayName("§b가격");
			lorelist.clear();
			lorelist.add("- §73초간 공격력 상승");
			lorelist.add("- §c쿨타임 13초");
			meta.setLore(lorelist);
			item.setItemMeta(meta);
			p.getInventory().setItem(1, item);
			
			item = new ItemStack(Material.EMERALD, 1);
			meta = item.getItemMeta();
			meta.setDisplayName("§b돌진");
			lorelist.clear();
			lorelist.add("- §74초간 이속1 증가");
			lorelist.add("- §c쿨타임 11초");
			meta.setLore(lorelist);
			item.setItemMeta(meta);
			p.getInventory().setItem(2, item);
			
			item = new ItemStack(Material.IRON_INGOT, 1);
			meta = item.getItemMeta();
			meta.setDisplayName("§b분쇄");
			lorelist.clear();
			lorelist.add("- §7반경 3칸이내의 적을 위로 띄움");
			lorelist.add("- §c쿨타임 20초");
			meta.setLore(lorelist);
			item.setItemMeta(meta);
			p.getInventory().setItem(3, item);
			
			break;
			
		case "힐러":
			jlist.put(p.getName(), "힐러");
			
			item = new ItemStack(Material.BOW, 1);
			meta = item.getItemMeta();
			meta.setDisplayName("§b힐러");
			meta.addEnchant(Enchantment.ARROW_INFINITE, 1, true);
			lorelist.clear();
			lorelist.add("- §7활로 팀원을 맞출시 데미지만큼 체력을 회복시켜줍니다.");
			meta.setLore(lorelist);
			item.setItemMeta(meta);
			p.getInventory().setItem(0, item);
			
			item = new ItemStack(Material.WOOD_SWORD, 1);
			meta = item.getItemMeta();
			meta.setDisplayName("§b힐러");
			meta.addEnchant(Enchantment.KNOCKBACK, 1, true);
			meta.addEnchant(Enchantment.DURABILITY, 10, true);
			item.setItemMeta(meta);
			p.getInventory().setItem(1, item);
			
			item = new ItemStack(Material.DIAMOND, 1);
			meta = item.getItemMeta();
			meta.setDisplayName("§b치유의 화살");
			lorelist.clear();
			lorelist.add("- §75초간 화살 치유량이 2배가 됩니다.");
			lorelist.add("- §75초간 화살에 타격당한 적은 독에 중독됩니다.");
			lorelist.add("- §c쿨타임 18초");
			meta.setLore(lorelist);
			item.setItemMeta(meta);
			p.getInventory().setItem(2, item);
			
			item = new ItemStack(Material.EMERALD, 1);
			meta = item.getItemMeta();
			meta.setDisplayName("§b고무");
			lorelist.clear();
			lorelist.add("- §7반경 4칸 아군 체력 +8");
			lorelist.add("- §c쿨타임 17초");
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
	
	public void Cleardata()
	{
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
		Bluecore_cnt = 9;
		Redcore_cnt = 9;
	}
	
	public void Forcestop()
	{
		Bukkit.broadcastMessage(MS+"코어사수전 게임이 강제종료 되었습니다.");
		for(Player p : plist)
		{
			p.teleport(Main_loc);
		}
		Cleardata();
	}
	
	public void RedWin(Player p)
	{
		game_end = true;
		Sendmessage(MS+p.getName()+" 님이 코어를 부셨습니다!");
		Sendmessage(MS+"레드팀의 승리로 게임이 종료됐습니다!");
		Bukkit.getScheduler().scheduleSyncDelayedTask(this, new Runnable()
		{
	public void run()
	{
		for(Player p : plist)
		{
			if(redlist.contains(p))
			{
			      EconomyResponse r = econ.depositPlayer(p.getName(), 300);
			      if (r.transactionSuccess()) {
			            p.sendMessage(ChatColor.GOLD + "패배 보상으로 300원을 받으셨습니다.");
			      }
			}
			else
			{
			      EconomyResponse r = econ.depositPlayer(p.getName(), 600);
			      if (r.transactionSuccess()) {
			            p.sendMessage(ChatColor.GOLD + "승리 보상으로 600원을 받으셨습니다.");
			      }
			}
			p.teleport(Main_loc);
		}
		Cleardata();
		Bukkit.broadcastMessage(MS+"§e§l코어 사수전이 레드팀의 승리로 종료 됐습니다.");
	}
		}, 140L);
	}
	
	public void BlueWin(Player p)
	{
		game_end = true;
		Sendmessage(MS+p.getName()+" 님이 코어를 부셨습니다!");
		Sendmessage(MS+"블루팀의 승리로 게임이 종료됐습니다!");
		Bukkit.getScheduler().scheduleSyncDelayedTask(this, new Runnable()
				{
			public void run()
			{
				for(Player p : plist)
				{
					if(bluelist.contains(p))
					{
					      EconomyResponse r = econ.depositPlayer(p.getName(), 600);
					      if (r.transactionSuccess()) {
					            p.sendMessage(ChatColor.GOLD + "승리 보상으로 300원을 받으셨습니다.");
					      }
					}
					else
					{
					      EconomyResponse r = econ.depositPlayer(p.getName(), 300);
					      if (r.transactionSuccess()) {
					            p.sendMessage(ChatColor.GOLD + "패배 보상으로 300원을 받으셨습니다.");
					      }
					}
					p.teleport(Main_loc);
				}
				Cleardata();
				Bukkit.broadcastMessage(MS+"§e§l코어 사수전이 블루팀의 승리로 종료 됐습니다.");
			}
				}, 140L);
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
			if(e.getInventory().getName().equalsIgnoreCase("직업선택"))
			{
				e.setCancelled(true);
				switch(e.getSlot())
				{
				case 0:
					Setjob(p, "탱커"); break;
				case 2:
					Setjob(p,"아쳐"); break;
				case 4:
					Setjob(p, "워리어"); break;
				case 6:
					Setjob(p, "어쌔신");; break;
				case 8:
					Setjob(p, "힐러"); break;
				default: return;
				}
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
		if((e.getAction() == Action.RIGHT_CLICK_AIR || e.getAction() == Action.RIGHT_CLICK_BLOCK) && plist.contains(e.getPlayer()) && check_start)
		{
			Player p = e.getPlayer();
			Skill(p, p.getItemInHand().getTypeId());
		}	
		if(e.getAction() == Action.LEFT_CLICK_BLOCK && plist.contains(e.getPlayer()))
		{
			Player p = e.getPlayer();
			Location bl = e.getClickedBlock().getLocation();
			if(bl.getBlockX() == Bluecore_loc.getBlockX() && bl.getBlockY() == Bluecore_loc.getBlockY() && bl.getBlockZ() == Bluecore_loc.getBlockZ())
			{
				if(bluelist.contains(p))
				{
					p.sendMessage(MS+"팀의 코어를 부수지마세요!");
					return;
				}
			}
			if(bl.getBlockX() == Redcore_loc.getBlockX() && bl.getBlockY() == Redcore_loc.getBlockY() && bl.getBlockZ() == Redcore_loc.getBlockZ())
			{
				if(redlist.contains(p))
				{
					p.sendMessage(MS+"팀의 코어를 부수지마세요!");
					return;
				}
			}
		}
	}
	
	@EventHandler
	public void onDamage(EntityDamageByEntityEvent e)
	{
		if(e.getEntity() instanceof Player)
		{
			Player p = (Player) e.getEntity();
			if(!(plist.contains(p) && jlist.containsKey(p)))
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
			if(d == null || !(plist.contains(d) && jlist.containsKey(p)))
			{
				return;
			}
			if(plist.contains(p) && plist.contains(d))
			{
				if((redlist.contains(p) && redlist.contains(d)) || (bluelist.contains(p) && bluelist.contains(d)))
				{
					if(jlist.get(d.getName()).equalsIgnoreCase("힐러"))
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
							p.getWorld().playSound(d.getLocation(), Sound.BREATH, 1.0f, 0.6f);
						}
					}
					e.setCancelled(true);
					return;
				}
				if(jlist.get(p.getName()).equalsIgnoreCase("어쌔신"))
				{
					e.setDamage((int)(e.getDamage()*1.3));
				}
				if(e.getDamager() instanceof Arrow)
				{
					if(archer1.contains(d.getName()))
					{
						archer1.remove(d.getName());
						d.sendMessage(MS+p.getName()+" 님의 머리를 맞췄습니다. (확실사살 스킬)");
						e.setDamage(e.getDamage()*2);
					}
					if(healer1.contains(d.getName()))
					{
						healer1.remove(d.getName());
						d.sendMessage(MS+p.getName()+" 님은 독에 중독됩니다!");
						PotionEffect potion = new PotionEffect(PotionEffectType.POISON, 40, 1);
						p.addPotionEffect(potion);
					}
					if(archer2.contains(d.getName()))
					{
						d.sendMessage(MS+p.getName()+" 님의 시야와 행동을 방해합니다.. (구속화살 스킬)");
						p.addPotionEffect(new PotionEffect(PotionEffectType.BLINDNESS, 80, 1));
						p.addPotionEffect(new PotionEffect(PotionEffectType.SLOW, 80, 4));
					}
				}
				else
				{
					if(assasin1.contains(d.getName()))
					{
						assasin1.remove(d.getName());
						d.sendMessage(MS+p.getName()+" 님의 급소를 공격했습니다. (암살 스킬)");
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
		if(plist.contains(e.getPlayer()) && check_start)
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
		}
	}
	
	@EventHandler
	public void onBlockbreak(BlockBreakEvent e)
	{
		if(plist.contains(e.getPlayer()))
		{
			e.setCancelled(true);
			Player p = e.getPlayer();
			Location bl = e.getBlock().getLocation();
			if(bl.getBlockX() == Bluecore_loc.getBlockX() && bl.getBlockY() == Bluecore_loc.getBlockY() && bl.getBlockZ() == Bluecore_loc.getBlockZ() && !game_end)
			{
				if(bluelist.contains(p))
				{
					p.sendMessage(MS+"팀의 코어를 부수지마세요!");
					return;
				}
				Bluecore_cnt--;
				if(Bluecore_cnt <=0)
				{
					RedWin(p);
				}
				else
				{
					Sendmessage(MS+"블루팀의 코어가 파괴 되었습니다. §c남은 코어수 : "+Bluecore_cnt+" 개");
				}
			}
			if(bl.getBlockX() == Redcore_loc.getBlockX() && bl.getBlockY() == Redcore_loc.getBlockY() && bl.getBlockZ() == Redcore_loc.getBlockZ() && !game_end)
			{
				if(redlist.contains(p))
				{
					p.sendMessage(MS+"팀의 코어를 부수지마세요!");
					return;
				}
				Redcore_cnt--;
				if(Redcore_cnt <=0)
				{
					BlueWin(p);
				}
				else
				{
					Sendmessage(MS+"레드팀의 코어가 파괴 되었습니다. §c남은 코어수 : "+Redcore_cnt+" 개");
				}
			}
		}
	}
	
	@EventHandler
	public void onPlayercommand(PlayerCommandPreprocessEvent e)
	{
		Player p = e.getPlayer();
		if(plist.contains(p) && e.getMessage().equalsIgnoreCase("/스폰") || e.getMessage().equalsIgnoreCase("/spawn")
				|| e.getMessage().equalsIgnoreCase("/넴주") || e.getMessage().equalsIgnoreCase("/ㅅㅍ"))
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
				Sendteamchat(bluelist.contains(p) ? bluelist : redlist, "§f[ §9팀채팅 §f] "+p.getName()+" : "+e.getMessage());
			}
		}
	}
}
