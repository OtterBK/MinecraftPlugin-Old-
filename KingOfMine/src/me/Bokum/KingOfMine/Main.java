package me.Bokum.KingOfMine;

import java.util.HashMap;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.plugin.java.JavaPlugin;

public class Main extends JavaPlugin implements Listener
{
	public static String MS = ChatColor.AQUA+"["+ChatColor.GOLD+"EG KOM"+ChatColor.AQUA+"] "+ChatColor.GRAY;
	public static Main instance;
	public static Location lobby;
	public static Location start_loc;
	public static Player jp;
	public static HashMap<Integer, Location> bl = new HashMap<Integer, Location>() ;
	public static int schtmp = 0;
	public static int schtmp2 = 0;
	public static int schtime = 0;
	public static int score = 0;
	public static int lvcnt = 1;
	public static int lv = 1;
	public static long dif = 25L;
	public static boolean start_check = false;
	
	public void onEnable()
	{
		getServer().getPluginManager().registerEvents(this, this);
		getLogger().info("������ �� �÷����� �ε� �Ϸ�! - ���� : Bokum");
		LoadConfig();
		instance = this;
	}
	
	public void LoadConfig()
	{
		start_loc = new Location(Bukkit.getWorld(getConfig().getString("world")),
				getConfig().getInt("loc_x"),getConfig().getInt("loc_y"),getConfig().getInt("loc_z"));
		lobby = new Location(Bukkit.getWorld(getConfig().getString("world")),
				getConfig().getInt("lobby_x"),getConfig().getInt("lobby_y"),getConfig().getInt("lobby_z"));
		for(int i = 0; i < 9; i++)
		{
			Location loc = new Location(Bukkit.getWorld(getConfig().getString("world")),
			getConfig().getInt("bl_"+i+"_x"),getConfig().getInt("bl_"+i+"_y"),getConfig().getInt("bl_"+i+"_z"));
			bl.put(i, loc);
		}
	}
	
	public void onDisable()
	{
		getLogger().info("������ �� �÷����� ��ε�");
		saveConfig();
	}
	
	public boolean onCommand(CommandSender sender, Command command, String string, String[] args)
	{
		if(sender instanceof Player)
		{
			Player player = (Player) sender;
			if(!player.hasPermission("kom"))
			{
				player.sendMessage(MS+"������ �����ϴ�.");
				return true;
			}
			if(string.equalsIgnoreCase("kom"))
			{
				if(args.length <= 0)
				{
					HelpMessage(player);
					return true;
				}
				else if(args[0].equalsIgnoreCase("start"))
				{
					if(start_check)
					{
						player.sendMessage(MS+"�̹� "+jp.getName()+"�Բ��� �÷��� ���Դϴ�.");
						return true;
					}
					StartGame(player);
					return true;
				}
				else if(args[0].equalsIgnoreCase("quit"))
				{
					if(player == jp)
					{
						Fail();
					}
					return true;
				}
				else if(args[0].equalsIgnoreCase("stop"))
				{

					Bukkit.broadcastMessage(MS+"������ �� ������ ���� ���� �ƽ��ϴ�.");
					Cleardata();
					
					return true;
				}
				else if(args[0].equalsIgnoreCase("rank"))
				{
					player.sendMessage(MS+"�ְ� ������ : "+getConfig().getString("top_name"));
					player.sendMessage(MS+"�ְ� ���� : "+getConfig().getInt("top"));
					return true;
				}
				else
				{
					HelpMessage(player);
					return true;
				}
			}
		}
		return true;
	}
	
	public void HelpMessage(Player player)
	{
		player.sendMessage(MS+"/kom start - ���� ����");
		player.sendMessage(MS+"/kom quit - ���� ����");
		player.sendMessage(MS+"/kom stop - ���� ����");
		player.sendMessage(MS+"/kom rank - 1�� Ȯ��");
		player.sendMessage(ChatColor.BLUE+"���� - Bokum");
	}
	
	public void StartGame(final Player player)
	{
		Cleardata();
		start_check = true;
		player.sendMessage(MS+"���Ƿ� �̵��մϴ�.");
		player.teleport(start_loc);
		jp = player;
		schtime = 0;
		schtmp = Bukkit.getScheduler().scheduleSyncRepeatingTask(this, new Runnable()
				{
					public void run()
					{
						Bukkit.getScheduler().cancelTask(schtmp);
						GameSystem(player);
					}
				}, 100L, 20L);
	}
	
	public void Givebaseitem(Player player)
	{
		player.getInventory().clear();
		player.setLevel(lv);
		ItemMeta meta;
		ItemStack item = new ItemStack(Material.STONE_PICKAXE, 1);
		meta = item.getItemMeta();
		meta.addEnchant(Enchantment.DIG_SPEED, 5, true);
		meta.addEnchant(Enchantment.DURABILITY, 10, true);
		item.setItemMeta(meta);
		player.getInventory().addItem(item);
		
		item = new ItemStack(Material.GOLD_PICKAXE, 1);
		meta = item.getItemMeta();
		meta.addEnchant(Enchantment.DIG_SPEED, 5, true);
		meta.addEnchant(Enchantment.DURABILITY, 10, true);
		item.setItemMeta(meta);
		player.getInventory().addItem(item);

		item = new ItemStack(Material.IRON_PICKAXE, 1);
		meta = item.getItemMeta();
		meta.addEnchant(Enchantment.DIG_SPEED, 5, true);
		meta.addEnchant(Enchantment.DURABILITY, 10, true);
		item.setItemMeta(meta);
		player.getInventory().addItem(item);
		
		item = new ItemStack(Material.DIAMOND_PICKAXE, 1);
		meta = item.getItemMeta();
		meta.addEnchant(Enchantment.DIG_SPEED, 5, true);
		meta.addEnchant(Enchantment.DURABILITY, 10, true);
		item.setItemMeta(meta);
		player.getInventory().addItem(item);
	}

	public void GameSystem(final Player player)
	{
		player.sendMessage(MS+"10�� �� ������ �����մϴ�.");
		schtime = 10;
		Givebaseitem(player);
		schtmp = Bukkit.getScheduler().scheduleSyncRepeatingTask(this, new Runnable()
				{
					public void run()
					{
						if(schtime > 0)
						{
							if(schtime <= 3)
							{
								player.sendMessage(MS+schtime+"�� �� �����մϴ�.");
							}
							schtime--;
						}
						else
						{
							Bukkit.getScheduler().cancelTask(schtmp);
							Gamescheduler(player);
							SetBlockScheduler();
						}
					}
				}, 20L, 20L);
	}
	
	public void Gamescheduler(final Player player)
	{
		schtime = 30;
		schtmp2 = Bukkit.getScheduler().scheduleSyncRepeatingTask(this, new Runnable()
				{
					public void run()
					{
						if(!start_check)
						{
							Bukkit.getScheduler().cancelTask(schtmp2);
						}
						if(lv > 1)
						{
						if(schtime > 0)
						{
							schtime--;
						}
						else if(schtime <= 0)
						{
							schtime = 60;
							lv--;
							jp.setLevel(lv);
							jp.sendMessage(MS+"���赵 1����");
						}
						}
					}
				}, 0L, 20L);
	}
	
	public void SetBlockScheduler()
	{
		schtmp = Bukkit.getScheduler().scheduleSyncRepeatingTask(this, new Runnable()
		{
			public void run()
			{
				if(start_check)
				{
					SetBlock();
				}
				else
				{
					Bukkit.getScheduler().cancelTasks(instance);
				}
			}
		}, 20L, dif);
	}
	
	public void Levelup()
	{
		lvcnt += 1;
		if(dif > 11)
		{
			dif -= 1;
		}
		jp.sendMessage(MS+"���̵� ��� ���� ���̵� :"+lvcnt);
		Bukkit.getScheduler().cancelTask(schtmp);
		SetBlockScheduler();
	}
	
	public void SetBlock()
	{
		while(CheckBlock())
		{
			
		}
	}
	
	@EventHandler
	public void onBlockbreak(BlockBreakEvent e)
	{
		if(jp == e.getPlayer())
		{
			Material btype = e.getBlock().getType();
			if(!(btype == Material.STONE || btype == Material.IRON_ORE || btype == Material.GOLD_ORE || btype == Material.DIAMOND_ORE))
			{
				jp.sendMessage(MS+"������ �ٸ� ���� �μ� �� �����ϴ�.");
				e.setCancelled(true);
				return;
			}
			e.getBlock().setType(Material.AIR);
			e.setCancelled(true);
			if((btype == Material.STONE) && !(e.getPlayer().getItemInHand().getType() == Material.STONE_PICKAXE))
			{
				jp.sendMessage(MS+"�߸��� ��̷� �ν����ϴ�!");
				Miss();
				return;
			}
			if((btype == Material.GOLD_ORE) && !(e.getPlayer().getItemInHand().getType() == Material.GOLD_PICKAXE))
			{
				jp.sendMessage(MS+"�߸��� ��̷� �ν����ϴ�!");
				Miss();
				return;
			}
			if((btype == Material.IRON_ORE) && !(e.getPlayer().getItemInHand().getType() == Material.IRON_PICKAXE))
			{
				jp.sendMessage(MS+"�߸��� ��̷� �ν����ϴ�!");
				Miss();
				return;
			}
			if((btype == Material.DIAMOND_ORE) && !(e.getPlayer().getItemInHand().getType() == Material.DIAMOND_PICKAXE))
			{
				jp.sendMessage(MS+"�߸��� ��̷� �ν����ϴ�!");
				Miss();
				return;
			}
			Addscore();
		}
	}
	
	public void Miss()
	{
		lv++;
		if(lv >= 5)
		{
			Fail();
			return;
		}
		jp.setLevel(lv);
	}
	
	public void Fail()
	{
		jp.sendMessage(MS+"���� : "+score);
		jp.sendMessage(MS+"�ִ� ���� : "+ lvcnt);
		jp.sendMessage(MS+"�ְ� ������ : "+getConfig().getString("top_name"));
		jp.sendMessage(MS+"�ְ� ���� : "+getConfig().getInt("top"));
		jp.teleport(lobby);
		if(getConfig().getInt("top") < score)
		{
			Bukkit.broadcastMessage(ChatColor.GREEN+"[EG] "+ChatColor.GOLD+jp.getName()+ChatColor.GRAY+"�Բ��� "
					+ score+" ������ "+ChatColor.AQUA+getConfig().getString("top_name")+" �� "+ChatColor.GRAY+" �� ��ġ�� �ű���� ����̽��ϴ�!");
			getConfig().set("top", score);
			getConfig().set("top_name", jp.getName());
			saveConfig();
		}
		Cleardata();
	}
	
	public void Cleardata()
	{
		Bukkit.getScheduler().cancelTasks(instance);
		for(int i = 0; i < 9; i++)
		{
			bl.get(i).getBlock().setType(Material.AIR);
		}
		dif = 25L;
		score = 0;
		jp = null;
		schtmp = 0;
		schtime = 0;
		lvcnt = 1;
		lv = 1;
		start_check = false;
	}
	
	public void Addscore()
	{
		score++;
		if(score % 10 == 0)
		{
			Levelup();
		}
	}
	
	public boolean Isfull()
	{
		for(int i = 0; i < 9; i++)
		{
			if(bl.get(i).getBlock().getType() == Material.AIR)
			{
				return false;
			}
		}
		return true;
	}
	
	public boolean CheckBlock()
	{
		if(Isfull())
		{
			Miss();
			jp.sendMessage(MS+"���� ���� �μ�����! ���� ���赵 : "+lv);
			return false;
		}
		int random = (int)(Math.random()*9);
		if(bl.get(random).getBlock().getType() == Material.AIR)
		{
			int random2 = (int)(Math.random()*4)+1;
			Material bm = Material.AIR;
			switch(random2)
			{
			case 1:
				bm = Material.STONE;
				break;
			case 2:
				bm = Material.GOLD_ORE;
				break;
			case 3:
				bm = Material.IRON_ORE;
				break;
			case 4:
				bm = Material.DIAMOND_ORE;
				break;
			}
			bl.get(random).getBlock().setType(bm);
			return false;
		}
		else
		{
			return true;
		}
	}
	
}
