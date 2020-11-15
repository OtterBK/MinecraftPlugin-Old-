package me.Bokum.Zodiac_Battle;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.Sound;
import org.bukkit.block.Block;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.entity.EntityDamageEvent.DamageCause;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.event.player.PlayerDropItemEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.event.player.PlayerRespawnEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.util.Vector;

public class Main extends JavaPlugin implements Listener
{
	public static String MS = "��f[��a��lEG��f] ";
	public static List<String> list = new ArrayList<String>();
	public static HashMap<String, String> ablist = new HashMap();
	public static Main instance;
	public static HashMap<String, Integer> cooldown = new HashMap();
	public static int tasknum[] = new int[200];
	public static int tasktime[] = new int[200];
	public static Location joinpos;
	public static Location Lobby;
	public static Location Bluestart;
	public static Location Redstart;
	
	public void onEnable()
	{
		instance = this;
		getServer().getPluginManager().registerEvents(this, this);
		getLogger().info("�������� ��Ʋ �÷������� �ε� �Ǿ����ϴ�. - ���� Bokum");
		for(int i = 0 ; i < 200; i++)
		{
			tasknum[i] = -5;
			tasktime[i] = -5;
		}
	}
	
	  public void LoadConfig()
	  {
	    try
	    {
	      joinpos = new Location(Bukkit.getWorld(getConfig().getString("Join_world")), getConfig().getInt("Join_x"), getConfig().getInt("Join_y"), getConfig().getInt("Join_z"));
	      Lobby = new Location(Bukkit.getWorld(getConfig().getString("Lobby_world")), getConfig().getInt("Lobby_x"), getConfig().getInt("Lobby_y"), getConfig().getInt("Lobby_z"));
	      Redstart = new Location(Bukkit.getWorld(getConfig().getString("Red_world")), getConfig().getInt("Red_x"), getConfig().getInt("Red_y"), getConfig().getInt("Red_z"));
	      Bluestart = new Location(Bukkit.getWorld(getConfig().getString("Blue_world")), getConfig().getInt("Blue_x"), getConfig().getInt("Blue_y"), getConfig().getInt("Blue_z"));
	    }
	    catch (IllegalArgumentException e)
	    {
	      getLogger().info("������ ���� �������� �ʾҽ��ϴ�!");
	    }
	  }
	
	  public void SaveLobby(Player player)
	  {
	    getConfig().set("Lobby_world", player.getLocation().getWorld().getName());
	    getConfig().set("Lobby_x", Integer.valueOf(player.getLocation().getBlockX()));
	    getConfig().set("Lobby_y", Integer.valueOf(player.getLocation().getBlockY() + 1));
	    getConfig().set("Lobby_z", Integer.valueOf(player.getLocation().getBlockZ()));
	    saveConfig();
	    LoadConfig();
	    player.sendMessage(MS + "���ηκ� ���� �Ǿ����ϴ�.");
	  }
	  
	  public void SaveRedStart(Player player)
	  {
	    getConfig().set("Red_world", player.getLocation().getWorld().getName());
	    getConfig().set("Red_x", Integer.valueOf(player.getLocation().getBlockX()));
	    getConfig().set("Red_y", Integer.valueOf(player.getLocation().getBlockY() + 1));
	    getConfig().set("Red_z", Integer.valueOf(player.getLocation().getBlockZ()));
	    saveConfig();
	    LoadConfig();
	    player.sendMessage(MS + "������ ���������� ���� �Ǿ����ϴ�.");
	  }

	  public void SaveBlueStart(Player player)
	  {
	    getConfig().set("Blue_world", player.getLocation().getWorld().getName());
	    getConfig().set("Blue_x", Integer.valueOf(player.getLocation().getBlockX()));
	    getConfig().set("Blue_y", Integer.valueOf(player.getLocation().getBlockY() + 1));
	    getConfig().set("Blue_z", Integer.valueOf(player.getLocation().getBlockZ()));
	    saveConfig();
	    LoadConfig();
	    player.sendMessage(MS + "����� ���������� ���� �Ǿ����ϴ�.");
	  }
	  
	  public void SaveJoinpos(Player player)
	  {
	    getConfig().set("Join_world", player.getLocation().getWorld().getName());
	    getConfig().set("Join_x", Integer.valueOf(player.getLocation().getBlockX()));
	    getConfig().set("Join_y", Integer.valueOf(player.getLocation().getBlockY() + 1));
	    getConfig().set("Join_z", Integer.valueOf(player.getLocation().getBlockZ()));
	    saveConfig();
	    LoadConfig();
	    player.sendMessage(MS + "�κ� ���� �Ǿ����ϴ�.");
	  }
	
	public void onDisable()
	{
		getLogger().info("�������� ��Ʋ �÷������� ��ε� �Ǿ����ϴ�.");
	}
	
	public static boolean Getchance(int chance)
	{
		int random = (int)((Math.random() * 100)+1);
		if(random <= 8)
		{
			return true;
		}
		return false;
	}
	
	public boolean onCommand(CommandSender talker, Command command, String string, String args[])
	{
		if(string.equalsIgnoreCase("zb") && talker instanceof Player)
		{
			Player player = (Player) talker;
			if(!player.isOp())
			{
				player.sendMessage(MS+"OP�� ��� �����մϴ�.");
				return true;
			}
			if(args[0].equalsIgnoreCase("start"))
			{
				Startgame();
				return true;
			}
	        if (args[0].equalsIgnoreCase("set"))
	        {
	          if (!player.hasPermission("zb.set"))
	          {
	            player.sendMessage(MS + "������ �����ϴ�.");
	            return false;
	          }
	          if (args.length <= 1)
	          {
	            System.Helpmessages(player);
	            return true;
	          }
	          if (args[1].equalsIgnoreCase("red"))
	          {
	            SaveRedStart(player);
	            return true;
	          }
	          if (args[1].equalsIgnoreCase("blue"))
	          {
	            SaveBlueStart(player);
	            return true;
	          }
	          if (args[1].equalsIgnoreCase("lobby"))
	          {
	            SaveLobby(player);
	            return true;
	          }
	          if (args[1].equalsIgnoreCase("join"))
	          {
	            SaveJoinpos(player);
	            return true;
	          }
	        }
			if(args[0].equalsIgnoreCase("stop"))
			{
				Stopgame();
				return true;
			}
			if(args[0].equalsIgnoreCase("ab"))
			{
				Setab(player, args);
				return true;
			}
			if(args[0].equalsIgnoreCase("item"))
			{
				System.SetSkillItem(player);
				return true;
			}
	        if (args[0].equalsIgnoreCase("join"))
	        {
	          if (!player.hasPermission("zb.join"))
	          {
	            player.sendMessage(MS + "������ �����ϴ�.");
	            return false;
	          }
	          System.Joingame(player);
	          return true;
	        }
	        if (args[0].equalsIgnoreCase("quit"))
	        {
	          if (!player.hasPermission("zb.quit"))
	          {
	            player.sendMessage(MS + "������ �����ϴ�.");
	            return false;
	          }
	          System.Death(player);
	          return true;
	        }
			if(args[0].equalsIgnoreCase("test"))
			{
				//Vector v = new Vector((float)(Integer.valueOf(args[1])/10),(float)(Integer.valueOf(args[2])/10),(float)(Integer.valueOf(args[3])/10));
				Vector v = player.getLocation().getDirection();
				v.add(new Vector(0,1,0));
				player.setVelocity(v.multiply((float)Integer.valueOf(args[1])/10));
				return true;
			}
			if(args[0].equalsIgnoreCase("test2"))
			{
				player.sendMessage("�۵�");
				ParticleEffect.SMOKE_NORMAL.display((float)Integer.valueOf(args[1]), (float)Integer.valueOf(args[2]), (float)Integer.valueOf(args[3]), (float)Integer.valueOf(args[4])/10, Integer.valueOf(args[5]), player.getLocation(), 60);
				return true;
			}
			System.Helpmessages(player);
			return true;
		}
		return false;
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
	
	  public static void Startgame_beta()
	  {
	    Bukkit.broadcastMessage(MS + ChatColor.DARK_RED + "�������� ����(��Ÿ) " + ChatColor.GRAY + "������ �� ���۵˴ϴ�.");
		final int cur = Getcursch();
		tasktime[cur] = 5;
	    tasknum[cur] = Bukkit.getScheduler().scheduleSyncRepeatingTask(instance, new Runnable()
	    {
	      public void run()
	      {
	        if (tasktime[cur] > 0)
	        {
	          System.SendMessage(Main.MS + ChatColor.DARK_RED + "�������� ����(��Ÿ) " + ChatColor.GRAY + "������ " + ChatColor.AQUA + tasktime[cur] * 10 + ChatColor.GRAY + " �� �� �����մϴ�.");
	          tasktime[cur]--;
	        }
	        else
	        {
	          Bukkit.getScheduler().cancelTask(tasknum[cur]);
	          Player tmp = System.list.get(0);
	          ablist.put(tmp.getName(), "��");
	          tmp.sendMessage(MS+tmp.getName()+" ���� "+ablist.get(tmp.getName())+" �� �����߽��ϴ�.");
	          tmp = System.list.get(1);
	          ablist.put(tmp.getName(), "��");
	          tmp.sendMessage(MS+tmp.getName()+" ���� "+ablist.get(tmp.getName())+" �� �����߽��ϴ�.");
	          tmp = System.list.get(2);
	          ablist.put(tmp.getName(), "ȣ����");
	          tmp.sendMessage(MS+tmp.getName()+" ���� "+ablist.get(tmp.getName())+" �� �����߽��ϴ�.");
	          tmp = System.list.get(3);
	          ablist.put(tmp.getName(), "�䳢");
	          tmp.sendMessage(MS+tmp.getName()+" ���� "+ablist.get(tmp.getName())+" �� �����߽��ϴ�.");
	          for(Player p : System.list)
	          {
	        	  if(System.redlist.size() <= System.bluelist.size())
	        	  {
	        		  System.SetRedTeam(p);
	        	  }
	        	  else
	        	  {
	        		  System.SetBlueTeam(p);
	        	  }
	          }
	          System.SendMessage(MS+"���� �̿ϼ������̶� ��ų������ ���� �ʾҽ��ϴ�. ��ų������ ī����� �����ϼ���.");
	        }
	      }
	    }
	    , 200L, 200L);
	  }
	
//====================================================== �ɷ� ȿ�� =====================================================
	
	public static void CowSkill1(final Player p)
	{
		Cow.Cowskill1 = true;
		final int cur = Getcursch();
		tasktime[cur] = 20;
		tasknum[cur] = Bukkit.getScheduler().scheduleSyncRepeatingTask(instance, new Runnable()
		{
			public void run()
			{
				if(tasktime[cur] > 0)
				{
					tasktime[cur]--;
					p.getWorld().playSound(p.getLocation(), Sound.COW_HURT, 3.0f, 0.8f);
					ParticleEffect.DRIP_WATER.display(1, 1, 1, 0.07f, 70, p.getLocation(), 60);
				}
				else
				{
					Bukkit.getScheduler().cancelTask(tasknum[cur]);
					tasktime[cur] = -5;
					tasknum[cur] = -5;
					Cow.Cowskill1 = false;
				}
			}
		}, 0L, 4L);
	}
	
	public static void MouseSkill4(final Player p)
	{
		final int cur = Getcursch();
		tasktime[cur] = 35;
		tasknum[cur] = Bukkit.getScheduler().scheduleSyncRepeatingTask(instance, new Runnable()
		{
			public void run()
			{
				if(tasktime[cur] > 0)
				{
					tasktime[cur]--;
					p.getWorld().playSound(p.getLocation(), Sound.BAT_LOOP, 3.5f, 1.4f);
					ParticleEffect.CLOUD.display(0, 1, 0, 0.3f, 15, p.getLocation(), 60);
				}
				else
				{
					Bukkit.getScheduler().cancelTask(tasknum[cur]);
					tasktime[cur] = -5;
					tasknum[cur] = -5;
				}
			}
		}, 0L, 4L);
	}
	
	public static void CowSkill4(final Player p)
	{
		Cow.Cowskill4 = true;
		final int cur = Getcursch();
		tasktime[cur] = 160;
		tasknum[cur] = Bukkit.getScheduler().scheduleSyncRepeatingTask(instance, new Runnable()
		{
			public void run()
			{
				if(tasktime[cur] > 0)
				{
					tasktime[cur]--;
					p.getWorld().playSound(p.getLocation(), Sound.DIG_STONE, 3.0f, 2.0f);
					for(int i = 0; i < 4; i++)
					{
						Vector inverseDirectionVec = p.getLocation().getDirection().normalize().multiply(-1);
				        Location loc = p.getLocation().add(inverseDirectionVec);
						ParticleEffect.PORTAL.display(0.6f, 0.2f, 0.6f, 0f, 30, loc.add(0,i,0), 60);
					}
				}
				else
				{
					Bukkit.getScheduler().cancelTask(tasknum[cur]);
					Cow.Cowskill4 = false;
					tasktime[cur] = -5;
					tasknum[cur] = -5;
				}
			}
		}, 0L, 1L);
	}
	
	public static void TigerSkill1(final Player p, final Player t)
	{
		final int cur = Getcursch();
		final Location start = p.getEyeLocation();
		final Vector increase = start.getDirection();
		tasktime[cur] = (int)(p.getLocation().distance(t.getLocation()));
		tasknum[cur] = Bukkit.getScheduler().scheduleSyncRepeatingTask(instance, new Runnable()
		{
			public void run()
			{
				if(tasktime[cur] > 0)
				{
					tasktime[cur]--;
					Location point = start.add(increase);
					p.teleport(point);
					ParticleEffect.FIREWORKS_SPARK.display(0F, 0F, 0F, 0F, 3, point, 60D);
				}
				else
				{
					Bukkit.getScheduler().cancelTask(tasknum[cur]);
					tasktime[cur] = -5;
					tasknum[cur] = -5;
					p.teleport(t.getLocation().subtract(t.getEyeLocation().getDirection()));
					t.sendMessage(Main.MS+"�����߽��ϴ�!");
					t.damage(5, p);
					Main.Setbind(t, 3);
					System.Addcooldown(p.getName()+"1", 14);
					p.playSound(p.getLocation(), Sound.ENDERDRAGON_GROWL, 3.0f, 2.0f);
				}
			}
		}, 0L, 2L);
	}
	
	public static void TigerSkill3(final Player p, final Location loc)
	{
		final int cur = Getcursch();
		final Location start = p.getEyeLocation();
		final Vector increase = start.getDirection();
		tasktime[cur] = (int)(p.getLocation().distance(loc));
		tasknum[cur] = Bukkit.getScheduler().scheduleSyncRepeatingTask(instance, new Runnable()
		{
			public void run()
			{
				if(tasktime[cur] > 0)
				{
					tasktime[cur]--;
					Location point = start.add(increase);
					p.teleport(point);
					ParticleEffect.FIREWORKS_SPARK.display(0F, 0F, 0F, 0F, 3, point, 60D);
				}
				else
				{
					Bukkit.getScheduler().cancelTask(tasknum[cur]);
					tasktime[cur] = -5;
					tasknum[cur] = -5;
					loc.setYaw(p.getLocation().getYaw());
					loc.setPitch(p.getLocation().getPitch());
					p.teleport(loc.add(0,1,0));
					System.Addcooldown(p.getName()+"3", 14);
					p.playSound(p.getLocation(), Sound.ENDERDRAGON_GROWL, 3.0f, 2.0f);
				}
			}
		}, 0L, 2L);
	}
	
	public static void TigerSkill4(final Player p)
	{
		final int cur = Getcursch();
		tasktime[cur] = 5;
		tasknum[cur] = Bukkit.getScheduler().scheduleSyncRepeatingTask(instance, new Runnable()
		{
			public void run()
			{
				if(tasktime[cur] > 0)
				{
					tasktime[cur]--;
					p.getWorld().playSound(p.getLocation(), Sound.BLAZE_DEATH, 3.0f, 0.4f);
					ParticleEffect.SMOKE_LARGE.display(1f, 1f, 1f, 0.25f, 300, p.getLocation(), 60);
				}
				else
				{
					Bukkit.getScheduler().cancelTask(tasknum[cur]);
					tasktime[cur] = -5;
					tasknum[cur] = -5;
				}
			}
		}, 0L, 20L);
	}
	
	public static void RabbitSkill4(final Player p, final Player t)
	{
		Bukkit.getScheduler().scheduleSyncDelayedTask(instance, new Runnable()
		{
			public void run()
			{
				t.damage(3 ,p);
			}
		}, 28L);
	}
//====================================================== �ɷ� ȿ�� ��=====================================================
	
	public void Startgame()
	{
		Player oplayer[] = Bukkit.getOnlinePlayers();
		for(Player p : oplayer)
		{
			list.add(p.getName());
			System.SetSkillItem(p);
		}
		
	}
	
	public void Stopgame()
	{
		System.ForceEnd();
	}
	
	public void Setab(Player p, String args[])
	{
		if(args.length <= 1)
		{
			p.sendMessage(MS+"/zb set <�ɷ��̸�>");
			return;
		}
		else
		{
			ablist.put(p.getName(), args[1]);
			p.sendMessage(MS+p.getName()+" ���� "+args[1]+" �� �����߽��ϴ�.");
		}
	}
	
	public static void Setgod(final Player p, long time)
	{
		time *= 20;
		System.godlist.add(p.getName());
		Bukkit.getScheduler().scheduleSyncDelayedTask(instance, new Runnable()
		{
			public void run()
			{
				System.godlist.remove(p.getName());
			}
		}, time);
	}
	
	public static void MouseSkill3(final Player p)
	{
		final ItemStack[] armor = p.getInventory().getArmorContents();
		p.getInventory().setArmorContents(null);
		p.updateInventory();
		Bukkit.getScheduler().scheduleSyncDelayedTask(instance, new Runnable()
		{
			public void run()
			{
				p.getInventory().setArmorContents(armor);
				p.updateInventory();
			}
		}, 120);
	}
	
	public static void Setbind(final Player p, long time)
	{
		time *= 20;
		System.bindlist.add(p.getName());
		Bukkit.getScheduler().scheduleSyncDelayedTask(instance, new Runnable()
		{
			public void run()
			{
				if(System.bindlist.contains(p.getName()))
				{
					p.sendMessage(MS+"�̵��Ұ� ���°� ���� �Ǿ����ϴ�.");
					System.bindlist.remove(p.getName());
				}
			}
		}, time);
	}
	
	public static void Setbreakout(final Player p, long time)
	{
		time *= 20;
		System.breaklist.add(p.getName());
		Bukkit.getScheduler().scheduleSyncDelayedTask(instance, new Runnable()
		{
			public void run()
			{
				if(System.breaklist.contains(p.getName()))
				{
					p.sendMessage(MS+"���ݺҰ� ���°� ���� �Ǿ����ϴ�.");
					System.breaklist.remove(p.getName());
				}
			}
		}, time);
	}
	
	public static void Countdown(final Player p, long time, final String skill)
	{
		time *= 20;
		Bukkit.getScheduler().scheduleSyncDelayedTask(instance, new Runnable()
		{
			public void run()
			{
				p.sendMessage(MS+ChatColor.AQUA+"3���� "+ChatColor.RESET+skill+"��ų�� �����˴ϴ�.");
			}
		}, time-60);
		Bukkit.getScheduler().scheduleSyncDelayedTask(instance, new Runnable()
		{
			public void run()
			{
				p.sendMessage(MS+ChatColor.AQUA+"2���� "+ChatColor.RESET+skill+"��ų�� �����˴ϴ�.");
			}
		}, time-40);
		Bukkit.getScheduler().scheduleSyncDelayedTask(instance, new Runnable()
		{
			public void run()
			{
				p.sendMessage(MS+ChatColor.AQUA+"1���� "+ChatColor.RESET+skill+"��ų�� �����˴ϴ�.");
			}
		}, time-20);
		Bukkit.getScheduler().scheduleSyncDelayedTask(instance, new Runnable()
		{
			public void run()
			{
				p.sendMessage(MS+ChatColor.AQUA+skill+"��ų�� �����ƽ��ϴ�.");
			}
		}, time);
	}
	
	//���м�=============================================================================================
	
	@EventHandler
	public void onPlayerDamagedByPlayer(EntityDamageByEntityEvent e)
	{
		if(e.getEntity() instanceof Player && e.getDamager() instanceof Player)
		{
			Player d = (Player) e.getDamager();
			Player p = (Player) e.getEntity();
			if(ablist.containsKey(p.getName()) && ablist.containsKey(d.getName()))
			{
				if(System.breaklist.contains(d.getName()))
				{
					d.sendMessage(Main.MS+"���� �Ұ� �����Դϴ�.");
					e.setCancelled(true);
					return;
				}
				if(ablist.get(p.getName()).equalsIgnoreCase("��"))
				{
					if(Getchance(3))
					{
						e.setCancelled(true);
						p.sendMessage(MS+"������ ȸ���߽��ϴ�!");
						return;
					}
					if(Getchance(8))
					{
						e.setDamage((int)e.getDamage()/2);
						p.sendMessage(MS+"������ ���ƽ��ϴ�!");
						return;
					}
					if(Mouse.avoidlist.contains(p.getName()))
					{
						Mouse.avoidlist.remove(p.getName());
						e.setCancelled(true);
						p.sendMessage(MS+"���� �ɷ����� ������ ���߽��ϴ�!");
						return;
					}
				}
				if(ablist.get(d.getName()).equalsIgnoreCase("��"))
				{
					if(System.CheckHandItem(d, "�ֽ�ų") && System.Checkcooldown(d, "1"))
					{
						e.setCancelled(true);
						Mouse.SetAvoid(p);
						System.Addcooldown(d.getName()+"1", 9);
						d.sendMessage(p.getName()+" �Կ��� �ֽ�ų�� ����߽��ϴ�.");
						return;
					}
				}
				if(ablist.get(p.getName()).equalsIgnoreCase("��"))
				{
					if(Cow.Cowskill4 && Getchance(50))
					{
						e.setDamage((int)(e.getDamage()*1.5));
					}
				}
				if(ablist.get(d.getName()).equalsIgnoreCase("��"))
				{
					if(Cow.Cowskill1)
					{
						e.setDamage(e.getDamage()+2);
						return;
					}
					if(Cow.Cowskill4 && Getchance(50))
					{
						e.setDamage(e.getDamage()*2);
						return;
					}
					if(Getchance(10))
					{
						e.setDamage(e.getDamage()+1);
						d.sendMessage(MS+"�޼Ҹ� �����߽��ϴ�!");
						return;
					}
				}
				if(ablist.get(d.getName()).equalsIgnoreCase("ȣ����"))
				{
					if(Getchance(4))
					{
						Setbind(p, 1);
						Setbreakout(p, 1);
						p.sendMessage(Main.MS+"���� �߽��ϴ�!");
						d.sendMessage(Main.MS+"�нú� �ɷ��� �ߵ� �Ǿ����ϴ�!");
					}
				}
				if(ablist.get(d.getName()).equalsIgnoreCase("�䳢"))
				{
					if(System.CheckHandItem(d, "�ֽ�ų") && System.Checkcooldown(d, "1"))
					{
						e.setCancelled(true);
						Rabbit.Throw(p,d);
						return;
					}
				}
				if(Mouse.avoidlist.contains(p.getName()))
				{
					Mouse.avoidlist.remove(p.getName());
					e.setCancelled(true);
					p.sendMessage(MS+"���� �ɷ����� ������ ���߽��ϴ�!");
					return;
				}
				if(!System.GetEnemies(d).contains(p))
				{
					e.setCancelled(true);
				}
			}
		}
	}
	
	@EventHandler
	public void onClickItem(PlayerInteractEvent e)
	{
		Player p = e.getPlayer();
		if(!ablist.containsKey(p.getName()))
		{
			return;
		}
		if(e.getAction() == org.bukkit.event.block.Action.RIGHT_CLICK_AIR || e.getAction() == org.bukkit.event.block.Action.RIGHT_CLICK_BLOCK)
		{
			if(ablist.get(p.getName()).equalsIgnoreCase("��"))
			{
				Mouse.RightClick(p);
				return;
			}
			if(ablist.get(p.getName()).equalsIgnoreCase("��"))
			{
				Cow.RightClick(p);
				return;
			}
			if(ablist.get(p.getName()).equalsIgnoreCase("ȣ����"))
			{
				Tiger.RightClick(p);
				return;
			}
			if(ablist.get(p.getName()).equalsIgnoreCase("�䳢"))
			{
				Rabbit.RightClick(p);
				return;
			}
		}
		if(e.getAction() == org.bukkit.event.block.Action.LEFT_CLICK_AIR || e.getAction() == org.bukkit.event.block.Action.LEFT_CLICK_BLOCK)
		{
			if(ablist.get(p.getName()).equalsIgnoreCase("��") && e.getAction() == org.bukkit.event.block.Action.LEFT_CLICK_BLOCK)
			{
				Mouse.LeftClick(p, e.getClickedBlock().getLocation());
				return;
			}
		}
	}
	
	@EventHandler
	public void onPlayerDamaged(EntityDamageEvent e)
	{
		if(e.getEntity() instanceof Player)
		{
			Player p = (Player) e.getEntity();
			if(ablist.containsKey(p.getName()))
			{
				if(ablist.get(p.getName()).equalsIgnoreCase("�䳢") && e.getCause() == DamageCause.FALL)
				{
					e.setCancelled(true);
					if(Rabbit.Skill4)
					{
						Rabbit.Ultimate2(p);
					}
				}
				if(System.godlist.contains(p.getName()))
				{
					e.setCancelled(true);
				}
			}
		}
	}
	
	@EventHandler
	public void onPlayerMove(PlayerMoveEvent e)
	{
		Player p = e.getPlayer();
		Location toloc = e.getTo();
		Location curloc = p.getLocation();
		if(curloc.getBlockX() == toloc.getBlockX() && curloc.getBlockY() == toloc.getBlockY() && curloc.getBlockZ() == toloc.getBlockZ())
		{
			return;
		}
		if(ablist.containsKey(p.getName()))
		{
			if(System.bindlist.contains(p.getName()))
			{
				e.setCancelled(true);
				p.sendMessage(Main.MS+"�̵� �Ұ��� �����Դϴ�.");
			}
			if(Mouse.traploc.contains(new Location(toloc.getWorld(), toloc.getBlockX(), toloc.getBlockY(), toloc.getBlockZ())))
			{
				Mouse.traploc.remove(new Location(toloc.getWorld(), toloc.getBlockX(), toloc.getBlockY(), toloc.getBlockZ()));
				p.sendMessage(Main.MS+"���� ��ҽ��ϴ�! - 7�ʰ� �̵��Ұ���");
				p.getWorld().playSound(p.getLocation(), Sound.ANVIL_LAND, 3.0f, 0.5f);
				ParticleEffect.SMOKE_NORMAL.display(1, 1, 1, 0.3f, 60, new Location(toloc.getWorld(), toloc.getBlockX(), toloc.getBlockY()+1, toloc.getBlockZ()), 60);
				p.damage(5);
				Setbind(p, 7);
			}
		}
	}
	
	@EventHandler
	public void onPlayerdeath(PlayerDeathEvent e)
	{
		Player p = e.getEntity();
		if(System.list.contains(p))
		{
			e.setDeathMessage(null);
			System.Death(p);
		}
	}
	
	@EventHandler
	public void onDropItem(PlayerDropItemEvent e)
	{
		Player p = e.getPlayer();
		if(System.list.contains(p))
		{
			p.sendMessage(MS+"������ �������� ������ �� �����ϴ�.");
			e.setCancelled(true);
		}
	}
	
	@EventHandler
	public void onPlayerRespawn(PlayerRespawnEvent e)
	{
		Player p = e.getPlayer();
		if(System.bindlist.contains(p.getName()))
		{
			System.bindlist.remove(p.getName());
		}
		if(System.godlist.contains(p.getName()))
		{
			System.godlist.remove(p.getName());
		}
	}
}
