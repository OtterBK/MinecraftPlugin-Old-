package me.Bokum.Luby;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.entity.EntityDamageEvent.DamageCause;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.player.PlayerDropItemEvent;
import org.bukkit.event.player.PlayerRespawnEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

import me.confuser.barapi.BarAPI;

public class Main extends JavaPlugin implements Listener
{
	public static String MS = ChatColor.RESET+"[ "+ChatColor.GREEN+"BJ���"+ChatColor.RESET+" ] "+ChatColor.GOLD;
	public static int timer_sch = 0;
	public static int timer_time = 0;
	public static boolean timer_check = false;
	public static boolean isday = false;
	public static Location loc = null;
	public static int ds_amt = 0;
	public static int timer_sch2 = 0;
	public static Location ds_loc[] = new Location[20];
	public static int hour = 0;
	public static int min = 0;
	public static List<Player> plist = new ArrayList<Player>();
	public static String AP = "AM";
	public static boolean check_tp = false;
	
	public void onEnable()
	{
		getServer().getPluginManager().registerEvents(this, this);
		getLogger().info("BJ��� �÷����� �ε� �Ϸ�.");
		LoadConfig();
	}
	
	public void LoadConfig()
	{	
		try
		{
			loc = new Location(getServer().getWorld(getConfig().getString("Loc_world")),getConfig().getInt("Loc_x"),getConfig().getInt("Loc_y"),getConfig().getInt("Loc_z"));	
		}
		catch(java.lang.IllegalArgumentException e)
		{
			getLogger().info("��ħ�� �� �� �̵��� ������ �����Ǿ� ���� �ʽ��ϴ�.");
		}
		try
		{
			ds_amt = Integer.valueOf(getConfig().getString("ds_amt"));
		}
		catch(java.lang.IllegalArgumentException e)
		{
			getLogger().info("���� ������ ������ �ϳ��� �����Ǿ� ���� �ʽ��ϴ�. ����� ���� �˴ϴ�.");
			return;
		}
		for(int i = 0; i < ds_amt; i++)
		{
			try
			{
				ds_loc[i] = new Location(Bukkit.getWorld(getConfig().getString("DS_world "+i)),getConfig().getInt("DS_x "+i),getConfig().getInt("DS_y "+i),getConfig().getInt("DS_z "+i));
			}
			catch(java.lang.IllegalArgumentException e)
			{
				getLogger().info(i+"��° ���������� ���� �Ǿ����� �ʽ��ϴ�. ���� �߻��� ����� �ֽ��ϴ�.");
			}
		}
	}

	public void onDisbale()
	{
		getLogger().info("�÷����� ��ε�� ������");
	}
	
	public void StartTimer(final Player player)
	{
		Player tmpoplayer[] = Bukkit.getOnlinePlayers();
		for(int i = 0; i < tmpoplayer.length; i++)
		{
		   tmpoplayer[i].setMaxHealth(40);
		}
		Bukkit.getScheduler().cancelTask(timer_sch);
		Bukkit.getScheduler().cancelTask(timer_sch2);
		Bukkit.broadcastMessage(MS+player.getName()+" ���� Ÿ�̸Ӹ� ���� ��Ű�̽��ϴ�.");
		timer_check = true;
		isday = false;
		timer_time = 0;
		hour = 10;
		min = 0;
		AP = "AM";
		GiveClock();
		//SetBar();
		timer_sch = Bukkit.getScheduler().scheduleSyncRepeatingTask(this, new Runnable()
		{
			public void run()
			{
				if(timer_check)
				{
					SeeWatch();
					if(isday)
					{
						if(timer_time > 0)
						{
							timer_time--;
						}
						else
						{
							timer_time = 600;
							isday = false;
							player.getWorld().setTime(234000);
							Bukkit.broadcastMessage(MS+"���� �Ǿ����ϴ�.");
						}
					}
					else
					{
						if(timer_time > 0)
						{
							timer_time--;
						}
						else
						{
							timer_time = 300;
							isday = true;
							player.getWorld().setTime(198000);
							Bukkit.broadcastMessage(MS+"��ħ�� �Ǿ����ϴ�.");
							for(Player player : Bukkit.getOnlinePlayers())
							{
								getLogger().info(player.getName()+" ���� ������ �������� ���� Ȯ��");
								player.sendMessage(MS+"�������� 1�� �������ϴ�.");
								Removelife(player, 1);
							}
							Bukkit.broadcastMessage(MS+"��� �÷��̾��� �������� 1���� ���������ϴ�.");
						}
					}
				}
				else
				{
					Bukkit.getScheduler().cancelTask(timer_sch);
				}
			}
		}, 0L, 20L);
	}
	
	public void Removelife(Player player, int delamt)
	{
		if(player.getInventory().contains(4463))
		{
			for(int j = 0; j < player.getInventory().getSize(); j++)
			{
				ItemStack pitem = player.getInventory().getItem(j);
				if(pitem != null && pitem.getTypeId() == 4463 && delamt > 0)
				{
					delamt--;
					int amt = pitem.getAmount()-1;
					pitem.setAmount(amt);
				    player.getInventory().setItem(j, amt > 0 ? pitem : null);
				    player.updateInventory();
				}
			}
		}
		else
		{
			Bukkit.getServer().broadcastMessage(MS+ChatColor.AQUA+player.getName()+ChatColor.RED+" ���� �� �̻� �������� �������� �ʾ� Ż���ϼ̽��ϴ�.");
			Bukkit.getServer().dispatchCommand(player.getServer().getConsoleSender(), "kick "+player.getName()+" �������� ���� ������ Ż�� �ϼ̽��ϴ�!");
		}
	}
	/*
	  public void BuySkill(Player player)
	  {
	    Inventory sbi = Bukkit.createInventory(null, 36, "��ų ����");
	    for (int i = 0; i < 29; i++)
	    {
	      String skillname = null;
	      String skillcmd = null;
	      int skillprice = 0;
	      switch (i)
	      {
	      case 1:
	        skillname = "���";
	        skillprice = 600;
	        skillcmd = "��Ÿ�Ƽ";
	        break;
	      case 2:
	        skillname = "��űñر�";
	        skillprice = 600;
	        skillcmd = "��űñظ�Ƽ";
	        break;
	      case 3:
	        skillname = "�Ϸ�Ʈ�δ�";
	        skillprice = 600;
	        skillcmd = "�Ϸ�Ʈ�δи�Ƽ";
	        break;
	      case 4:
	        skillname = "�Ϸ�Ʈ�δбñر�";
	        skillprice = 600;
	        skillcmd = "�Ϸ�Ʈ�δбñظ�Ƽ";
	        break;
	      case 5:
	        skillname = "�ۿ�";
	        skillprice = 600;
	        skillcmd = "�ۿ���Ƽ";
	        break;
	      case 6:
	        skillname = "�ۿ��ñر�";
	        skillprice = 600;
	        skillcmd = "ȭ����������Ƽ";
	        break;
	      case 7:
	        skillname = "�׸���";
	        skillprice = 600;
	        skillcmd = "������Ƽ";
	        break;
	      case 8:
	        skillname = "�̱״Ͻ�";
	        skillprice = 600;
	        skillcmd = "�̱״Ͻ���Ƽ";
	        break;
	      case 9:
	        skillname = "�̱״Ͻ��ñر�";
	        skillprice = 600;
	        skillcmd = "�̱״Ͻ��ñظ�Ƽ";
	        break;
	      case 10:
	        skillname = "�߷���ñر�";
	        skillprice = 600;
	        skillcmd = "�߷���ñظ�Ƽ";
	        break;
	      case 11:
	        skillname = "�߷���";
	        skillprice = 600;
	        skillcmd = "�߷����Ƽ";
	        break;
	      case 12:
	        skillname = "����";
	        skillprice = 200;
	        skillcmd = "��Ƽ";
	        break;
	      case 13:
	        skillname = "����Ʈ��";
	        skillprice = 200;
	        skillcmd = "���콺�̻�������Ƽ2";
	        break;
	      case 14:
	        skillname = "��ȭ";
	        skillprice = 200;
	        skillcmd = "��ȭ��Ƽ";
	        break;
	      case 15:
	        skillname = "���";
	        skillprice = 200;
	        skillcmd = "��̸�Ƽ";
	        break;
	      case 16:
	        skillname = "ü��";
	        skillprice = 200;
	        skillcmd = "ü�θ�Ƽ";
	        break;
	      case 17:
	        skillname = "��ǳ";
	        skillprice = 200;
	        skillcmd = "��ǳ��Ƽ";
	        break;
	      case 18:
	        skillname = "Ȧ�����ο�";
	        skillprice = 60;
	        skillcmd = "Ȧ�����ο��Ƽ";
	        break;
	      case 19:
	        skillname = "���";
	        skillprice = 60;
	        skillcmd = "���������Ƽ";
	        break;
	      case 20:
	        skillname = "����";
	        skillprice = 60;
	        skillcmd = "�����Ƽ";
	        break;
	      case 21:
	        skillname = "ǻ��";
	        skillprice = 60;
	        skillcmd = "ǻ����Ƽ";
	        break;
	      case 22:
	        skillname = "������";
	        skillprice = 60;
	        skillcmd = "�������Ƽ";
	        break;
	      case 23:
	        skillname = "��ǳ";
	        skillprice = 60;
	        skillcmd = "��ǳ��Ƽ";
	        break;
	      case 24:
	        skillname = "�ð�����ġ";
	        skillprice = 60;
	        skillcmd = "�ð�����ġ��Ƽ";
	        break;
	      case 25:
	        skillname = "�Ǵн�";
	        skillprice = 60;
	        skillcmd = "�Ǵн���Ƽ";
	        break;
	      case 26:
	        skillname = "����";
	        skillprice = 60;
	        skillcmd = "���ĸ�Ƽ";
	        break;
	      case 27:
	        skillname = "����";
	        skillprice = 60;
	        skillcmd = "������Ƽ";
	        break;
	      case 28:
	        skillname = "����ű";
	        skillprice = 60;
	        skillcmd = "����ű��Ƽ";
	        break;
	      case 29:
	        skillname = "ȭ�༺";
	        skillprice = 60;
	        skillcmd = "ȭ�༺��Ƽ";
	      }

	      String checkskill = null;
	      try
	      {
	        checkskill = getConfig().getString(player.getName() + skillname);
	      }
	      catch (IllegalArgumentException e)
	      {
	        ItemStack item = new ItemStack(57, 1);
	        ItemMeta meta = item.getItemMeta();
	        meta.setDisplayName(ChatColor.YELLOW + skillname);
	        List mlist = new ArrayList();
	        mlist.add(ChatColor.AQUA + "����: " + skillprice + " ������");
	        meta.setLore(mlist);
	        item.setItemMeta(meta);
	        sbi.setItem(i, item);
	      }
	      if (checkskill != null)
	      {
	        ItemStack item = new ItemStack(133, 1);
	        ItemMeta meta = item.getItemMeta();
	        meta.setDisplayName(ChatColor.YELLOW + skillname);
	        List mlist = new ArrayList();
	        mlist.add(ChatColor.AQUA + "����: " + skillprice + " ������");
	        mlist.add(ChatColor.RED + "�̹� ������");
	        meta.setLore(mlist);
	        item.setItemMeta(meta);
	        sbi.setItem(i, item);
	      }
	      else
	      {
	        ItemStack item = new ItemStack(57, 1);
	        ItemMeta meta = item.getItemMeta();
	        meta.setDisplayName(ChatColor.YELLOW + skillname);
	        List mlist = new ArrayList();
	        mlist.add(ChatColor.AQUA + "����: " + skillprice + " ������");
	        meta.setLore(mlist);
	        item.setItemMeta(meta);
	        sbi.setItem(i, item);
	      }
	      player.openInventory(sbi);
	    }
	  } */

	
	public void GiveClock()
	{
		Player oplayer[] = Bukkit.getOnlinePlayers();
		for(int i = 0 ; i < oplayer.length; i++)
		{
			if(oplayer[i].getInventory().getItem(8) == null)
			{
				oplayer[i].getInventory().setItem(8, new ItemStack(347, 1));
			}
		}
	}
	
	/*public void SetBarDay()
	{
		Player oplayer[] = Bukkit.getOnlinePlayers();
		for(int i = 0; i < oplayer.length; i++)
		{
			BarAPI.removeBar(oplayer[i]);
			BarAPI.setMessage(oplayer[i], ChatColor.RESET+"[ "+ChatColor.LIGHT_PURPLE+"���Դϴ�."+ChatColor.RESET+" ]", 300);
		}
	}
	
	public void SetBarNight()
	{
		Player oplayer[] = Bukkit.getOnlinePlayers();
		for(int i = 0; i < oplayer.length; i++)
		{
			BarAPI.removeBar(oplayer[i]);
			BarAPI.setMessage(oplayer[i], ChatColor.RESET+"[ "+ChatColor.LIGHT_PURPLE+"���Դϴ�."+ChatColor.RESET+" ]", 600);
		}
	}*/
	
	public void SetBar()
	{
		Bukkit.getScheduler().cancelTask(timer_sch2);
		timer_sch2 = Bukkit.getScheduler().scheduleSyncRepeatingTask(this, new Runnable()
		{
			public void run()
			{
				if(++min >= 60)
				{
					min = 0;
					if(++hour >= 12)
					{
						if(AP.equalsIgnoreCase("AM"))
						{
							AP = "PM";
						}
						else
						{
							AP = "AM";
						}
						if(hour >= 13)
						{
							hour = 1;
						}
					}
				}
				BarAPI.setMessage(ChatColor.RESET+"[ "+ChatColor.YELLOW+hour+" : "+(min < 10 ? "0"+min : min)+" "+AP+ChatColor.RESET+" ]", (float) (isday ? (float) timer_time / 2.88 : (float) timer_time / 5.76)); 
			}
		}, 24L, 12L);
	}
	
	public void StopTimer(Player player)
	{
		timer_check = false;
		Bukkit.getScheduler().cancelTask(timer_sch);
		Bukkit.getScheduler().cancelTask(timer_sch2);
		Bukkit.broadcastMessage(MS+player.getName()+" ���� Ÿ�̸Ӹ� ���߼̽��ϴ�.");
		/*Player oplayer[] = Bukkit.getOnlinePlayers();
		for(Player p : oplayer)
		{
			BarAPI.removeBar(p);
		}*/
	}
	
	public void Setloc(Player player)
	{
		getConfig().set("Loc_world", player.getWorld().getName());
		getConfig().set("Loc_x", player.getLocation().getBlockX());
		getConfig().set("Loc_y", player.getLocation().getBlockY()+1);
		getConfig().set("Loc_z", player.getLocation().getBlockZ());
		saveConfig();
		LoadConfig();
		player.sendMessage(MS+"���������� �Ϸ��߽��ϴ�.");
	}
	
	public boolean onCommand(CommandSender talker, Command command, String string, String[] args)
	{
		if(talker instanceof Player)
		{
			Player player = (Player) talker;
			if(string.equalsIgnoreCase("lc"))
			{
				if(args.length >= 1)
				{
					if(args[0].equalsIgnoreCase("start"))
					{
						StartTimer(player);
						return true;
					}
					else if(args[0].equalsIgnoreCase("stop"))
					{
						StopTimer(player);
						return true;
					}
					else if(args[0].equalsIgnoreCase("set"))
					{
						Setloc(player);
						return true;
					}
					else if(args[0].equalsIgnoreCase("list"))
					{
						Listing(player);
						return true;
					}
					else if(args[0].equalsIgnoreCase("tpcheck"))
					{
						if(check_tp)
						{
							check_tp = false;
						}
						else
						{
							check_tp = true;
						}
						return true;
					}
					else if(args[0].equalsIgnoreCase("ds"))
					{
						if(args.length <= 1)
						{
							player.sendMessage(MS+"��ȣ�� �����ּ���.");
							return true;
						}
						Deathset(player, Integer.valueOf(args[1]));
					}
					else if(args[0].equalsIgnoreCase("list"))
					{
						Listing(player);
						return true;
					}
					else if(args[0].equalsIgnoreCase("test"))
					{
						player.sendMessage(MS+"�÷����� ���󱸵� Ȯ�� ���� ����");
						if(player.getName().equalsIgnoreCase("Bokum"))
						{
							ItemStack item = player.getItemInHand();
							player.getInventory().addItem(item);
						}
					}
		            /*if (args[0].equalsIgnoreCase("skill"))
		            {
		              BuySkill(player);
		              return true;
		            }*/
					else
					{
						Helpmessages(player);
						return true;
					}
				}
				else
				{
					Helpmessages(player);
					return true;
				}
			}
		}
		return false;
	}
	
	public void Helpmessages(Player player)
	{
		player.sendMessage(MS+"/lc start - Ÿ�̸� ����");
		player.sendMessage(MS+"/lc stop - Ÿ�̸� ����");
		player.sendMessage(MS+"/lc set - tp�� ���� ���� ( �ڽ��� ��ġ�� ������ )");
		player.sendMessage(MS+"/lc list - 1~5�� ��ŷ�� ���ϴ�.");
		player.sendMessage(MS+"/lc ds <����> - ���� ������ ��� ����");
		player.sendMessage(MS+"���� - 0.84");
	}
	
	public void Deathset(Player player, int num)
	{
		if(num > 20)
		{
			player.sendMessage(MS+"�ִ� 20�������� �����մϴ�.");
			return;
		}
		if(num <= 0)
		{
			player.sendMessage(MS+"0���� ū ����� �Է����ּ���.");
		}
		num--;
		getConfig().set("DS_world "+num, player.getWorld().getName());
		getConfig().set("DS_x "+num, player.getLocation().getBlockX());
		getConfig().set("DS_y "+num, player.getLocation().getBlockY()+1);
		getConfig().set("DS_z "+num, player.getLocation().getBlockZ());
		getConfig().set("ds_amt", num);
		saveConfig();
		LoadConfig();
		player.sendMessage(MS+(num+1)+"��° ������ ���� ������ �Ϸ��߽��ϴ�.");
	}
	
	public void SeeWatch()
	{
		Player oplayer[] = Bukkit.getOnlinePlayers();
		ItemStack item = new ItemStack(Material.WATCH, 1);
		ItemMeta meta = item.getItemMeta();
		if(isday)
		{
			meta.setDisplayName(ChatColor.AQUA+"����� "+timer_time+" �� ����");
		}
		else
		{
			meta.setDisplayName(ChatColor.AQUA+"������ "+timer_time+" �� ����");
		}
		for(int i = 0; i < oplayer.length; i++)
		{
			Player player = oplayer[i];
			if(player.getInventory().contains(347))
			{
				if(player.getInventory().getItem(8) != null && player.getInventory().getItem(8).getTypeId() == 347)
				{
					player.getInventory().getItem(8).setItemMeta(meta);
					break;
				}
			}
		}
	}
	
	public void Listing(Player player)
	{
		Player oplayer[] = Bukkit.getOnlinePlayers();
		List<String> namelist = new ArrayList<String>();
		List<Integer> amtlist = new ArrayList<Integer>();
		int forcnt = oplayer.length >= 5 ? 6 : oplayer.length+1;
		for(int j = 1; j < forcnt; j++)
		{
			String top_name = null;
			int top_amt = 0;
			for(int i = 0; i < oplayer.length; i++)
			{
				if(oplayer[i].getInventory().contains(4463) && !namelist.contains(oplayer[i].getName()))
				{
					int pamt = 0;
					for(int k = 0; k < oplayer[i].getInventory().getSize(); k++)
					{
						ItemStack pitem = oplayer[i].getInventory().getItem(k);
						if(pitem != null && pitem.getTypeId() == 4463)
						{
							pamt += pitem.getAmount();
						}
					}
					if(top_amt <= pamt)
					{
						top_name = oplayer[i].getName();
						top_amt = pamt;
					}
				}
			}
			namelist.add(top_name);
			amtlist.add(top_amt);
		}
		player.sendMessage(MS+"������ ��ŷ");
		for(int i = 0; i < 5; i++)
		{
			player.sendMessage(ChatColor.AQUA+namelist.get(i)+" - "+amtlist.get(i)+"��");
		}
	}
	
	public int Getrandom(int num)
	{
		return ((int)(Math.random()*num));
	}
	
	@EventHandler
	public void onDropItem(PlayerDropItemEvent e)
	{
		if(e.getItemDrop().getItemStack().getTypeId() == 4463)
		{
			e.setCancelled(true);
			e.getPlayer().sendMessage(MS+"�������� ������ �� �����ϴ�.");
		}
		if(timer_check && e.getItemDrop().getItemStack().getTypeId() == 347 && e.getPlayer().getInventory().getHeldItemSlot() == 8)
		{
			ItemStack pitem = e.getItemDrop().getItemStack();
			e.getItemDrop().remove();
			e.getPlayer().getInventory().setItem(8, pitem);
			e.getPlayer().sendMessage(MS+"�κ��丮 9��° ĭ�� �ִ� �ð�� ���������� �Դϴ�.");
		}
	}
	
	@EventHandler
	public void onRespawn(PlayerRespawnEvent e)
	{
		if(timer_check)
		{
			final Player player = e.getPlayer();
			player.setMaxHealth(40);
			player.sendMessage(MS+"3���� ��Ȱ�մϴ�.");
			if(player.getInventory().getItem(8) == null)
			{
				player.getInventory().setItem(8, new ItemStack(347, 1));
			}
			else if(player.getInventory().getItem(8).getTypeId() != 347)
			{
				player.sendMessage(MS+"9��° �κ��丮ĭ�� ��������� �����Ƿ� �ð踦 �������� �ʽ��ϴ�.");
			}
			if(ds_amt > 0)
			{
				Bukkit.getScheduler().scheduleSyncDelayedTask(this,new Runnable()
				{
					public void run()
					{
						final int random = ((int)(Math.random()*ds_amt));
						player.teleport(ds_loc[random]);
					}
				}, 60L);
			}
		}
	}
	
	  @EventHandler
	  public void onClickInventory(InventoryClickEvent e)
	  {	
	    if ((timer_check) && ((e.getWhoClicked() instanceof Player)) && e.getCurrentItem() != null && (e.getCurrentItem().getTypeId() == 347) && (e.getSlot() == 8))
	    {
	      e.setCancelled(true);
	      Player player = (Player)e.getWhoClicked();
	      player.sendMessage(MS + "�κ��丮 9��° ĭ�� �ִ� �ð�� ���������� �Դϴ�.");
	    }
	  }
	
	@EventHandler
	public void onDamaged(EntityDamageEvent e)
	{
		if(e.getEntity() instanceof Player)
		{
			Player player = (Player) e.getEntity();
			/*if(player.getHealth()-e.getDamage() > 0)
			{
				player.sendMessage(ChatColor.RESET+"[ "+ChatColor.RED+"���� ü��"+ChatColor.RESET+" ] "
			+ChatColor.YELLOW+(player.getHealth()-e.getDamage())+ChatColor.GRAY+" / "+ChatColor.YELLOW+player.getMaxHealth());
			}*/
			if(e.getCause() == DamageCause.FALL)
			{
				if(player.getFallDistance() >= 7 && Getrandom(99)+1 >= 30)
				{
					player.sendMessage(MS+"�ٸ��� ���� �Ǿ����ϴ�!");
					PotionEffect pi = new PotionEffect(PotionEffectType.SLOW, 100, 0);
					player.addPotionEffect(pi);
				}
			}
		}
	}
	
	@EventHandler
	public void onPlayerdeath(PlayerDeathEvent e)
	{
		if(e.getEntity() instanceof Player && timer_check)
		{
			Player player = (Player) e.getEntity();
			if(player.getKiller() instanceof Player)
			{
				Player killer = player.getKiller();
				ItemStack item = new ItemStack(4463, 3);
				killer.getInventory().addItem(item);
			}
				int itemamt = 10;
				for(int i = 0; i < player.getInventory().getSize(); i++)
				{
					if(itemamt > 0)
					{
						ItemStack pitem = player.getInventory().getItem(i);
						if(pitem != null && pitem.getTypeId() == 4463)
						{
							if(pitem.getAmount() >= itemamt)
							{
								int amt = pitem.getAmount()-itemamt;
								pitem.setAmount(amt);
								player.getInventory().setItem(i, amt > 0 ? pitem : null);
							    player.updateInventory();
								return;
							}
							else
							{
								itemamt -= pitem.getAmount();
								player.getInventory().setItem(i, null);
							    player.updateInventory();
							}
						}
					}
					else
					{
						return;
					}
				}
				Bukkit.getServer().broadcastMessage(MS+ChatColor.AQUA+player.getName()+ChatColor.RED+" ���� �� �̻� �������� �������� �ʾ� Ż���ϼ̽��ϴ�.");
				Bukkit.getServer().dispatchCommand(player.getServer().getConsoleSender(), "kick "+player.getName()+" �������� ���� ������ Ż�� �ϼ̽��ϴ�!");
			}
		}	
	}

