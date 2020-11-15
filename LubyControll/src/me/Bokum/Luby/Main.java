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
	public static String MS = ChatColor.RESET+"[ "+ChatColor.GREEN+"BJ뱀사"+ChatColor.RESET+" ] "+ChatColor.GOLD;
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
		getLogger().info("BJ뱀사 플러그인 로드 완료.");
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
			getLogger().info("아침이 될 시 이동할 지점이 지정되어 있지 않습니다.");
		}
		try
		{
			ds_amt = Integer.valueOf(getConfig().getString("ds_amt"));
		}
		catch(java.lang.IllegalArgumentException e)
		{
			getLogger().info("랜덤 리스폰 지점이 하나도 지정되어 있지 않습니다. 기능이 오프 됩니다.");
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
				getLogger().info(i+"번째 시작지점이 설정 되어있지 않습니다. 버그 발생의 우려가 있습니다.");
			}
		}
	}

	public void onDisbale()
	{
		getLogger().info("플러그인 언로드됨 ㅇㅅㅇ");
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
		Bukkit.broadcastMessage(MS+player.getName()+" 님이 타이머를 시작 시키셨습니다.");
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
							Bukkit.broadcastMessage(MS+"밤이 되었습니다.");
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
							Bukkit.broadcastMessage(MS+"아침이 되었습니다.");
							for(Player player : Bukkit.getOnlinePlayers())
							{
								getLogger().info(player.getName()+" 에게 라이프 삭제구문 적용 확인");
								player.sendMessage(MS+"라이프가 1개 없어집니다.");
								Removelife(player, 1);
							}
							Bukkit.broadcastMessage(MS+"모든 플레이어의 라이프가 1개씩 없어졌습니다.");
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
			Bukkit.getServer().broadcastMessage(MS+ChatColor.AQUA+player.getName()+ChatColor.RED+" 님은 더 이상 라이프가 존재하지 않아 탈락하셨습니다.");
			Bukkit.getServer().dispatchCommand(player.getServer().getConsoleSender(), "kick "+player.getName()+" 라이프가 전부 없어져 탈락 하셨습니다!");
		}
	}
	/*
	  public void BuySkill(Player player)
	  {
	    Inventory sbi = Bukkit.createInventory(null, 36, "스킬 구입");
	    for (int i = 0; i < 29; i++)
	    {
	      String skillname = null;
	      String skillcmd = null;
	      int skillprice = 0;
	      switch (i)
	      {
	      case 1:
	        skillname = "사신";
	        skillprice = 600;
	        skillcmd = "사신멀티";
	        break;
	      case 2:
	        skillname = "사신궁극기";
	        skillprice = 600;
	        skillcmd = "사신궁극멀티";
	        break;
	      case 3:
	        skillname = "일렉트로닉";
	        skillprice = 600;
	        skillcmd = "일렉트로닉멀티";
	        break;
	      case 4:
	        skillname = "일렉트로닉궁극기";
	        skillprice = 600;
	        skillcmd = "일렉트로닉궁극멀티";
	        break;
	      case 5:
	        skillname = "작염";
	        skillprice = 600;
	        skillcmd = "작염멀티";
	        break;
	      case 6:
	        skillname = "작염궁극기";
	        skillprice = 600;
	        skillcmd = "화염의정원멀티";
	        break;
	      case 7:
	        skillname = "그림자";
	        skillprice = 600;
	        skillcmd = "질럿멀티";
	        break;
	      case 8:
	        skillname = "이그니스";
	        skillprice = 600;
	        skillcmd = "이그니스멀티";
	        break;
	      case 9:
	        skillname = "이그니스궁극기";
	        skillprice = 600;
	        skillcmd = "이그니스궁극멀티";
	        break;
	      case 10:
	        skillname = "중력장궁극기";
	        skillprice = 600;
	        skillcmd = "중력장궁극멀티";
	        break;
	      case 11:
	        skillname = "중력장";
	        skillprice = 600;
	        skillcmd = "중력장멀티";
	        break;
	      case 12:
	        skillname = "괴력";
	        skillprice = 200;
	        skillcmd = "멀티";
	        break;
	      case 13:
	        skillname = "라이트닝";
	        skillprice = 200;
	        skillcmd = "제우스이빨번개멀티2";
	        break;
	      case 14:
	        skillname = "석화";
	        skillprice = 200;
	        skillcmd = "석화멀티";
	        break;
	      case 15:
	        skillname = "장미";
	        skillprice = 200;
	        skillcmd = "장미멀티";
	        break;
	      case 16:
	        skillname = "체인";
	        skillprice = 200;
	        skillcmd = "체인멀티";
	        break;
	      case 17:
	        skillname = "태풍";
	        skillprice = 200;
	        skillcmd = "태풍멀티";
	        break;
	      case 18:
	        skillname = "홀리에로우";
	        skillprice = 60;
	        skillcmd = "홀리에로우멀티";
	        break;
	      case 19:
	        skillname = "기습";
	        skillprice = 60;
	        skillcmd = "순간기습멀티";
	        break;
	      case 20:
	        skillname = "블러드";
	        skillprice = 60;
	        skillcmd = "블러드멀티";
	        break;
	      case 21:
	        skillname = "퓨리";
	        skillprice = 60;
	        skillcmd = "퓨리멀티";
	        break;
	      case 22:
	        skillname = "포이즌";
	        skillprice = 60;
	        skillcmd = "포이즌멀티";
	        break;
	      case 23:
	        skillname = "돌풍";
	        skillprice = 60;
	        skillcmd = "돌풍멀티";
	        break;
	      case 24:
	        skillname = "시공간장치";
	        skillprice = 60;
	        skillcmd = "시공간장치멀티";
	        break;
	      case 25:
	        skillname = "피닉스";
	        skillprice = 60;
	        skillcmd = "피닉스멀티";
	        break;
	      case 26:
	        skillname = "음파";
	        skillprice = 60;
	        skillcmd = "음파멀티";
	        break;
	      case 27:
	        skillname = "지진";
	        skillprice = 60;
	        skillcmd = "지진멀티";
	        break;
	      case 28:
	        skillname = "사이킥";
	        skillprice = 60;
	        skillcmd = "사이킥멀티";
	        break;
	      case 29:
	        skillname = "화약성";
	        skillprice = 60;
	        skillcmd = "화약성멀티";
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
	        mlist.add(ChatColor.AQUA + "가격: " + skillprice + " 라이프");
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
	        mlist.add(ChatColor.AQUA + "가격: " + skillprice + " 라이프");
	        mlist.add(ChatColor.RED + "이미 구입함");
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
	        mlist.add(ChatColor.AQUA + "가격: " + skillprice + " 라이프");
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
			BarAPI.setMessage(oplayer[i], ChatColor.RESET+"[ "+ChatColor.LIGHT_PURPLE+"밤입니다."+ChatColor.RESET+" ]", 300);
		}
	}
	
	public void SetBarNight()
	{
		Player oplayer[] = Bukkit.getOnlinePlayers();
		for(int i = 0; i < oplayer.length; i++)
		{
			BarAPI.removeBar(oplayer[i]);
			BarAPI.setMessage(oplayer[i], ChatColor.RESET+"[ "+ChatColor.LIGHT_PURPLE+"낮입니다."+ChatColor.RESET+" ]", 600);
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
		Bukkit.broadcastMessage(MS+player.getName()+" 님이 타이머를 멈추셨습니다.");
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
		player.sendMessage(MS+"지점설정을 완료했습니다.");
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
							player.sendMessage(MS+"번호를 적어주세요.");
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
						player.sendMessage(MS+"플러그인 정상구동 확인 구문 실행");
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
		player.sendMessage(MS+"/lc start - 타이머 시작");
		player.sendMessage(MS+"/lc stop - 타이머 중지");
		player.sendMessage(MS+"/lc set - tp할 지점 설정 ( 자신의 위치로 설정됨 )");
		player.sendMessage(MS+"/lc list - 1~5위 랭킹을 봅니다.");
		player.sendMessage(MS+"/lc ds <숫자> - 랜덤 리스폰 장소 설정");
		player.sendMessage(MS+"버젼 - 0.84");
	}
	
	public void Deathset(Player player, int num)
	{
		if(num > 20)
		{
			player.sendMessage(MS+"최대 20개까지만 가능합니다.");
			return;
		}
		if(num <= 0)
		{
			player.sendMessage(MS+"0보다 큰 양수를 입력해주세요.");
		}
		num--;
		getConfig().set("DS_world "+num, player.getWorld().getName());
		getConfig().set("DS_x "+num, player.getLocation().getBlockX());
		getConfig().set("DS_y "+num, player.getLocation().getBlockY()+1);
		getConfig().set("DS_z "+num, player.getLocation().getBlockZ());
		getConfig().set("ds_amt", num);
		saveConfig();
		LoadConfig();
		player.sendMessage(MS+(num+1)+"번째 리스폰 지점 설정을 완료했습니다.");
	}
	
	public void SeeWatch()
	{
		Player oplayer[] = Bukkit.getOnlinePlayers();
		ItemStack item = new ItemStack(Material.WATCH, 1);
		ItemMeta meta = item.getItemMeta();
		if(isday)
		{
			meta.setDisplayName(ChatColor.AQUA+"밤까지 "+timer_time+" 초 남음");
		}
		else
		{
			meta.setDisplayName(ChatColor.AQUA+"낮까지 "+timer_time+" 초 남음");
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
		player.sendMessage(MS+"라이프 랭킹");
		for(int i = 0; i < 5; i++)
		{
			player.sendMessage(ChatColor.AQUA+namelist.get(i)+" - "+amtlist.get(i)+"개");
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
			e.getPlayer().sendMessage(MS+"라이프는 버리실 수 없습니다.");
		}
		if(timer_check && e.getItemDrop().getItemStack().getTypeId() == 347 && e.getPlayer().getInventory().getHeldItemSlot() == 8)
		{
			ItemStack pitem = e.getItemDrop().getItemStack();
			e.getItemDrop().remove();
			e.getPlayer().getInventory().setItem(8, pitem);
			e.getPlayer().sendMessage(MS+"인벤토리 9번째 칸에 있는 시계는 고정아이템 입니다.");
		}
	}
	
	@EventHandler
	public void onRespawn(PlayerRespawnEvent e)
	{
		if(timer_check)
		{
			final Player player = e.getPlayer();
			player.setMaxHealth(40);
			player.sendMessage(MS+"3초후 부활합니다.");
			if(player.getInventory().getItem(8) == null)
			{
				player.getInventory().setItem(8, new ItemStack(347, 1));
			}
			else if(player.getInventory().getItem(8).getTypeId() != 347)
			{
				player.sendMessage(MS+"9번째 인벤토리칸이 비워져있지 않으므로 시계를 지급하지 않습니다.");
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
	      player.sendMessage(MS + "인벤토리 9번째 칸에 있는 시계는 고정아이템 입니다.");
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
				player.sendMessage(ChatColor.RESET+"[ "+ChatColor.RED+"남은 체력"+ChatColor.RESET+" ] "
			+ChatColor.YELLOW+(player.getHealth()-e.getDamage())+ChatColor.GRAY+" / "+ChatColor.YELLOW+player.getMaxHealth());
			}*/
			if(e.getCause() == DamageCause.FALL)
			{
				if(player.getFallDistance() >= 7 && Getrandom(99)+1 >= 30)
				{
					player.sendMessage(MS+"다리가 골절 되었습니다!");
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
				Bukkit.getServer().broadcastMessage(MS+ChatColor.AQUA+player.getName()+ChatColor.RED+" 님은 더 이상 라이프가 존재하지 않아 탈락하셨습니다.");
				Bukkit.getServer().dispatchCommand(player.getServer().getConsoleSender(), "kick "+player.getName()+" 라이프가 전부 없어져 탈락 하셨습니다!");
			}
		}	
	}

