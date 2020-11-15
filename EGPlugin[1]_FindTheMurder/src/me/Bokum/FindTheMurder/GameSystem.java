package me.Bokum.FindTheMurder;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Set;
import java.util.logging.Logger;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.Server;
import org.bukkit.Sound;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.event.Listener;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.PlayerInventory;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scheduler.BukkitScheduler;

public class GameSystem extends JavaPlugin
  implements Listener
{
  public static int schtime = 0;
  public static int schtmp = 0;
  public static GameSystem instance;
  public static boolean isday;
  public static int daycnt = 0;
  public static String doctortarget = null;
  public static String detectivetarget = null;
  public static boolean uc_police = false;
  public static boolean uc_soldier = true;
  public static boolean uc_spy = false;
  public static boolean uc_reporter = false;
  public static int uc_guardiancnt = 0;
  public static boolean checkstart = false;
  public static String uc_guardian1 = null;
  public static String uc_guardian2 = null;
  public static boolean lobbystart = false;
  public static String astarget = null;
  public static Player assasin;
  public static List<Player> mlist = new ArrayList<Player>();
  public static List<Player> tmplist = new ArrayList<Player>();

  public static void Joingame(Player player)
  {
    if (Main.list.contains(player))
    {
      player.sendMessage(Main.MS + "�̹� ���ӿ� ������ �Դϴ�.");
      return;
    }
    if (checkstart)
    {
      player.sendMessage(Main.MS + "�̹� ������ ���۵Ǿ����ϴ�.");
      return;
    }
    if (Main.list.size() >= 13)
    {
      player.sendMessage(Main.MS + "�̹� �ִ��ο��� 13���� �÷��̾ �������Դϴ�.");
      return;
    }
    player.teleport(Main.joinpos);
    Main.list.add(player);
	player.getInventory().clear();
	player.getInventory().setHelmet(null);
	player.getInventory().setChestplate(null);
	player.getInventory().setLeggings(null);
	player.getInventory().setBoots(null);
    me.Bokum.EGM.Main.gamelist.put(player.getName(), "���θ��� ã�ƶ�4");
    player.getInventory().setItem(8, Main.helpitem);
    Main.SendMessage(Main.MS + ChatColor.AQUA + player.getName() + ChatColor.GRAY + " ���� �����߽��ϴ�. " + ChatColor.RESET + "[ " + ChatColor.GREEN + Main.list.size() + " / 5 " + ChatColor.RESET + "]");
    if ((Main.list.size() >= 5) && (!lobbystart))
    {
      lobbystart = true;
      Main.Startgame();
    }
	for(Player t : Main.list){
		t.playSound(t.getLocation(), Sound.NOTE_PLING, 2.0f, 1.0f);
	}
  }

  public static int Getrandom(int num)
  {
    return (int)(Math.random() * num);
  }

  public static void ForceEnd()
  {
    Bukkit.broadcastMessage(Main.MS + "���θ��� ã�ƶ�4 ������ ���� ���� �Ǿ����ϴ�.");
    schtime = 0;
    schtmp = 0;
    isday = false;
    daycnt = 0;
    doctortarget = null;
    detectivetarget = null;
    uc_police = false;
    uc_soldier = true;
    uc_spy = false;
    uc_reporter = false;
    uc_guardiancnt = 0;
    checkstart = false;
    uc_guardian1 = null;
    uc_guardian2 = null;
    lobbystart = false;
    for (int i = 0; i < Main.list.size(); i++)
    {
        try{
      	  me.Bokum.EGM.Main.Spawn(Main.list.get(i));
        } catch(Exception e){
      	  Main.list.get(i).teleport(Main.Lobby);
        }
    }
    mlist.clear();
    Main.list.clear();
    Main.plist.clear();
    Main.jlist.clear();
    Main.glist.clear();
    astarget = null;
    tmplist.clear();
    assasin = null;
    Main.chestlist.clear();
    Main.Cancelalltasks();
  }

  public static void End()
  {
	Bukkit.getScheduler().cancelTasks(Main.instance);
	Main.Forcestoptimer = 0;
    schtime = 0;
    schtmp = 0;
    Main.chestlist.clear();
    isday = false;
    daycnt = 0;
    doctortarget = null;
    detectivetarget = null;
    uc_police = false;
    uc_soldier = true;
    uc_spy = false;
    uc_reporter = false;
    uc_guardiancnt = 0;
    checkstart = false;
    uc_guardian1 = null;
    uc_guardian2 = null;
    lobbystart = false;
    mlist.clear();
    Main.list.clear();
    Main.plist.clear();
    Main.jlist.clear();
    Main.glist.clear();
    tmplist.clear();
    astarget = null;
    assasin = null;
  }

  public static void Setjob()
  {
    tmplist.clear();
    tmplist.addAll(Main.list);
    if (tmplist.size() < 5)
    {
      Main.SendMessage(Main.MS + "���� ���࿡ �ʿ��� �ּ��ο��� �������� �ʴ� ���װ� �߻��߽��ϴ�.");
      ForceEnd();
    }
    for (int i = 0; i < Main.list.size(); i++)
    {
      Player setplayer = (Player)Main.list.get(i);
      Main.plist.put(setplayer.getName(), setplayer);
    }
    int random = Getrandom(tmplist.size());
    SetMurder((Player)tmplist.get(random));
    random = Getrandom(tmplist.size());
    SetDoctor((Player)tmplist.get(Getrandom(tmplist.size())));
    random = Getrandom(tmplist.size());
    SetPolice((Player)tmplist.get(Getrandom(tmplist.size())));
    random = Getrandom(tmplist.size());
    SetSoldier((Player)tmplist.get(Getrandom(tmplist.size())));
    random = Getrandom(tmplist.size());
    SetSpy((Player)tmplist.get(Getrandom(tmplist.size())));
    if (tmplist.size() != 0)
    {
      random = Getrandom(tmplist.size());
      SetNice_Civilian((Player)tmplist.get(Getrandom(tmplist.size())));
    }

    if (tmplist.size() != 0)
    {
      random = Getrandom(tmplist.size());
      SetDetective((Player)tmplist.get(Getrandom(tmplist.size())));
    }

    if (tmplist.size() != 0)
    {
      random = Getrandom(tmplist.size());
      SetReporter((Player)tmplist.get(Getrandom(tmplist.size())));
    }

    if (tmplist.size() != 0)
    {
      random = Getrandom(tmplist.size());
      SetMagician((Player)tmplist.get(Getrandom(tmplist.size())));
    }

    if (tmplist.size() != 0)
    {
      random = Getrandom(tmplist.size());
      SetGuadian((Player)tmplist.get(Getrandom(tmplist.size())));
    }

    if (tmplist.size() != 0)
    {
      random = Getrandom(tmplist.size());
      SetCivilian((Player)tmplist.get(Getrandom(tmplist.size())));
    }

    if (tmplist.size() != 0)
    {
      random = Getrandom(tmplist.size());
      SetAssasin((Player)tmplist.get(Getrandom(tmplist.size())));
    }

    if (tmplist.size() != 0)
    {
      random = Getrandom(tmplist.size());
      SetPrisoner((Player)tmplist.get(Getrandom(tmplist.size())));
    }
    
    Main.SendMessage(Main.MS + "������ ���������ϴ�.");
    Main.SendMessage(Main.MS + "������ ���� ������ ���÷��� " + 
      ChatColor.AQUA + "/ftm help �� �Է����ּ���.");
    Main.SendMessage(Main.MS + "��������Ʈ�� ���÷��� /ftm list �� �Է����ּ���.");
    Mixlist();
  }

  public static void Mixlist()
  {
    if (Main.glist.size() == 0)
    {
      Bukkit.getLogger().info("��������Ʈ�� ����ִ� ���¿��� Mixgist �޼ҵ带 ȣ���� �� �����ϴ�.");
    }
    else
    {
      List keylist = new ArrayList();
      List valuelist = new ArrayList();
      keylist.addAll(Main.glist.keySet());
      valuelist.addAll(Main.glist.values());
      Main.glist.clear();
      while (keylist.size() > 0)
      {
        int ri = Getrandom(keylist.size());
        Main.glist.put((String)keylist.get(ri), (String)valuelist.get(ri));
        keylist.remove(ri);
        valuelist.remove(ri);
      }
    }
  }

  public static void dayinit()
  {
    Main.vplist.clear();
    Main.vlist.clear();
    detectivetarget = null;
    uc_police = false;
  }

  public static void nightinit()
  {
    doctortarget = null;
    uc_spy = false;
    uc_guardiancnt = 0;
    uc_guardian1 = null;
    uc_guardian2 = null;
  }

  public static void Setvotelist()
  {
    String topname = null;
    int top_int = 0;
    boolean bothtopcheck = false;
    Set keys = Main.vlist.keySet();
    Iterator it = keys.iterator();
    while (it.hasNext())
    {
      String name = (String)it.next();
      if (top_int < ((Integer)Main.vlist.get(name)).intValue())
      {
        top_int = ((Integer)Main.vlist.get(name)).intValue();
        topname = name;
        bothtopcheck = false;
      } else {
        if (top_int != ((Integer)Main.vlist.get(name)).intValue())
          continue;
        bothtopcheck = true;
      }
    }
    if ((bothtopcheck) || (topname == null))
    {
      Main.SendMessage(Main.MS + "�ִ���ǥ�� ���� �÷��̾ �������� �����Ƿ� ��ǥ�� ��� �ƽ��ϴ�.");
    }
    else
    {
      Main.SendMessage(Main.MS + topname + " ���� �ִ���ǥ�� �����̽��ϴ�. ( " + top_int + "ǥ )");
      if (Checkucguardian(topname))
      {
        Main.SendMessage(Main.MS + "������ ���𰡵��� �ɷ����� ���Ͽ� �� �÷��̾��� ������ ������ �� �������ϴ�!");
      }
      else if (!Main.plist.containsKey(topname))
      {
        Main.SendMessage(Main.MS + "�ִ� ��ǥ�� ���� �÷��̾ ����� ���ӿ� �������� �ƴϹǷ� ��� �ƽ��ϴ�.");
      }
      else
      {
    	String topJob = Getjob(topname); 
    	if(topJob.equalsIgnoreCase("�����")){
    		for(int i = 0; i < mlist.size(); i++){
    			if(mlist.get(i).getName().equalsIgnoreCase(topname)){
    				topJob = "�����(���θ���)";
    			} else {
    				topJob = "�����(�ù���)";
    			}
    		}
    	}
        Main.SendMessage(Main.MS + topname + " ���� ������ " + topJob + " �Դϴ�!");
        Main.glist.put(topname, topJob);
      }
      Main.SendMessage(Main.MS + "/ftm list �� �Է½� ��������Ʈ�� ���� �� �ֽ��ϴ�.");
    }
  }

  public static boolean Checkucguardian(String name)
  {
    if (uc_guardian1 != null)
    {
      if (uc_guardian1.equalsIgnoreCase(name))
      {
        return true;
      }
    }
    if (uc_guardian2 != null)
    {
      if (uc_guardian2.equalsIgnoreCase(name))
      {
        return true;
      }
    }
    return false;
  }

  public static boolean Checktarget(Player player, String target)
  {
    if (target == null)
    {
      player.sendMessage(Main.MS + "Ÿ���� �������ּ���.");
      return true;
    }
    if (!Main.plist.containsKey(target))
    {
      player.sendMessage(Main.MS + ChatColor.AQUA + target + ChatColor.GRAY + "���� ���ӿ� �������� �ƴմϴ�.");
      return true;
    }
    return false;
  }

  public static boolean Checkday(Player player)
  {
    if (daycnt == 1)
    {
      player.sendMessage(Main.MS + "ù° ������ ����� �Ұ����� �ɷ��Դϴ�.");
      return true;
    }
    return false;
  }

  public static String Getjob(String target)
  {
    return (String)Main.jlist.get(target);
  }

	public static void Use(Player player, String target)
	{
		switch(Getjob(player.getName()))
		{
			case "���θ�":
				UseMurder(player); return; 
			case "�ǻ�":
				UseDoctor(player, target); return; 
			case "����":
				UsePolice(player, target); return; 
			case "����":
				UseSoldier(player); return; 
			case "������":
				UseSpy(player, target); return; 
			case "Ž��":
				UseDetective(player, target); return; 
			case "����ù�":
				UseNice_Civilian(player); return; 
			case "����":
				UseReporter(player, target); return; 
			case "������":
				UseMagician(player, target); return; 
			case "���𰡵�":
				UseGuadian(player, target); return; 
			case "�ù�":
				UseCivilian(player); return; 
			case "�����":
				UseAssasin(player); return; 
		}
	}

  public static void UseMurder(Player player)
  {
    player.sendMessage(Main.MS + "���θ��� ����� �ɷ��� �����ϴ�. ���� �̿��Ͽ� ��� �÷��̾ ���̼���.");
  }

  public static void UseDoctor(Player player, String target)
  {
    if (doctortarget != null)
    {
      player.sendMessage(Main.MS + "�̹� �ɷ��� ����Ͽ����ϴ�. ���������� ��ٷ��ּ���.");
      return;
    }
    if (Checktarget(player, target))
    {
      return;
    }
    if (isday)
    {
      player.sendMessage(Main.MS + "�ǻ��� �ɷ��� �㿡�� ����� �����մϴ�.");
    }
    else if (target.equalsIgnoreCase(player.getName()))
    {
      player.sendMessage(Main.MS + "�ڽſ��Դ� ����� �Ұ����մϴ�.");
    }
    else
    {
      doctortarget = target;
      player.sendMessage(Main.MS + target + " ���� ����� ġ���մϴ�.");
    }
  }

  public static void UsePolice(Player player, String target)
  {
    if (uc_police)
    {
      player.sendMessage(Main.MS + "�̹� �ɷ��� ����Ͽ����ϴ�. ���������� ��ٷ��ּ���.");
      return;
    }
    if (Checktarget(player, target))
    {
      return;
    }
    if (Checkday(player))
    {
      return;
    }
    if (isday)
    {
      uc_police = true;
      if (Getjob(target).equalsIgnoreCase("���θ�"))
      {
        player.sendMessage(Main.MS + target + " ���� ���θ� �Դϴ�! �� ����� ��ο��� �˸�����!");
      }
      else
      {
        player.sendMessage(Main.MS + target + " ���� ���θ��� �ƴմϴ�.");
      }
    }
    else
    {
      player.sendMessage(Main.MS + "������ �ɷ��� ������ ����� �����մϴ�.");
    }
  }

  public static void UseSoldier(Player player)
  {
    player.sendMessage(Main.MS + "������ ����� �ɷ��� �����ϴ�. ������ �׾ 1�� ��Ȱ�մϴ�.");
  }

  public static void UseSpy(Player player, String target)
  {
    if (uc_spy)
    {
      player.sendMessage(Main.MS + "�̹� �ɷ��� ����Ͽ����ϴ�. ���������� ��ٷ��ּ���.");
      return;
    }
    if (Checktarget(player, target))
    {
      return;
    }
    if (isday)
    {
      player.sendMessage(Main.MS + "�������� �ɷ��� �㿡�� ����� �����մϴ�.");
    }
    else
    {
      uc_spy = true;
      if (Getjob(target).equalsIgnoreCase("���θ�"))
      {
        mlist.add(player);
        player.sendMessage(Main.MS + target + " ���� ���θ� �����ϴ�!");
        player.sendMessage(Main.MS + target + " �԰� ������ ���� �Ͽ����ϴ�!");
        ((Player)Main.plist.get(target)).sendMessage(ChatColor.RED + "������ " + ChatColor.AQUA + player.getName() + ChatColor.RED + " ���� ��Ű� �����߽��ϴ�.");
        Givespyitem(player);
        if (Getsurviver() <= 0)
        {
          Main.Murderwin();
        }
      }
      else
      {
        player.sendMessage(Main.MS + target + " ���� ���θ��� �ƴմϴ�.");
        player.sendMessage(Main.MS + target + " ���� ������ " + Getjob(target) + " �Դϴ�.");
        if (Getjob(target).equalsIgnoreCase("����"))
        {
          ((Player)Main.plist.get(target)).sendMessage(Main.MS + "������ " + player.getName() + " ���� ����� �����Ͽ����ϴ�!");
        }
      }
    }
  }

  public static void UseNice_Civilian(Player player)
  {
    player.sendMessage(Main.MS + "����ù��� ��ǥ�ÿ� �ɷ��� �ڵ������� Ȱ��ȭ�� �˴ϴ�.");
  }

  public static void UseDetective(Player player, String target)
  {
    if (isday)
    {
      if (Checktarget(player, target))
      {
        return;
      }
      if (detectivetarget != null)
      {
        player.sendMessage(Main.MS + "�̹� �ɷ��� ����Ͽ����ϴ�. ���������� ��ٷ��ּ���.");
        return;
      }
      detectivetarget = target;
      player.sendMessage(Main.MS + "���� " + target + " ���� �㿡 ������ �Ͻô��� �� �� �ֽ��ϴ�.");
    }
    else
    {
      if (detectivetarget == null)
      {
        player.sendMessage(Main.MS + "���� ������ ������ �� �������� �ʾ� �ɷ»���� �Ұ����մϴ�.");
        return;
      }
      if(!Main.jlist.containsKey(target))
      {
    	 player.sendMessage(Main.MS+"���� ����� ���ӿ� ���������� �ʽ��ϴ�.");
    	 return;
      }
      player.sendMessage(ChatColor.AQUA + detectivetarget + " ���� x��ǥ : " + ((Player)Main.plist.get(target)).getLocation().getBlockX());
      player.sendMessage(ChatColor.AQUA + detectivetarget + " ���� y��ǥ : " + ((Player)Main.plist.get(target)).getLocation().getBlockY());
      player.sendMessage(ChatColor.AQUA + detectivetarget + " ���� z��ǥ : " + ((Player)Main.plist.get(target)).getLocation().getBlockZ());
      player.sendMessage(ChatColor.AQUA + detectivetarget + " ���� ü�� : " + ((Player)Main.plist.get(target)).getHealth());
      if (((Player)Main.plist.get(target)).getItemInHand().hasItemMeta())
      {
        player.sendMessage(ChatColor.AQUA + detectivetarget + " ���� ��� �ִ� ���� : " + ((Player)Main.plist.get(target)).getItemInHand().getItemMeta().getDisplayName());
      }
      else
      {
        player.sendMessage(ChatColor.AQUA + detectivetarget + " ���� ��� �ִ� ���� : �ν��� �� �����ϴ�.");
      }
    }
  }

  public static void UseReporter(Player player, String target)
  {
    if (Checktarget(player, target))
    {
      return;
    }
    if (uc_reporter)
    {
      player.sendMessage(Main.MS + "�̹� �ɷ��� ����ϼ̽��ϴ�. ������ �ɷ��� 1���� ����� �����մϴ�.");
      return;
    }
    if (isday)
    {
      player.sendMessage(Main.MS + "������ �ɷ��� �㿡�� ����� �����մϴ�.");
    }
    else
    {
      uc_reporter = true;
      Main.SendMessage(Main.MS + "���ڰ� " + target + " ���� �����߽��ϴ�.");
      Main.SendMessage(Main.MS + target + " ���� ������ " + Getjob(target) + "�Դϴ�!!!");
      Main.glist.put(target, Getjob(target));
    }
  }

	public static void UseMagician(Player player, String target)
	{
		if(Checktarget(player, target))
		{
			return;
		}
		if(isday)
		{
			player.sendMessage(Main.MS+"�������� �ɷ��� ������ ����� �Ұ����մϴ�.");
		}
		if(daycnt > 2){
			player.sendMessage(Main.MS+"�������� �ɷ��� 3��°������ ����� �Ұ����մϴ�.");
		}
		else
		{
			if(target.equalsIgnoreCase(player.getName()))
			{
				player.sendMessage(Main.MS+"�ڽſ��Դ� ����� �Ұ����մϴ�.");
				return;
			}
			player.sendMessage(Main.MS+target+" ���� ������ "+Getjob(target)+"��(��) �������ϴ�.");
			Main.plist.get(target).sendMessage(Main.MS+"�����簡 ����� ������ �������ϴ�.");
			switch(Getjob(target))
			{
				case "���θ�":
					Player tmpplayer = Main.plist.get(target);
					Removeplayerfl(Main.plist.get(target));
					tmpplayer.sendMessage(Main.MS+"����� ���θ����� ������ ����ϰ� �˴ϴ�.");
					tmpplayer.setHealth(0);
					SetMurder(player);
					return; 
				case "�ǻ�":
					SetDoctor(player); break; 
				case "����":
					SetPolice(player); break; 
				case "����":
					SetSoldier(player); break; 
				case "������":
					SetSpy(player); return; 
				case "Ž��":
					SetDetective(player); break; 
				case "����ù�":
					SetNice_Civilian(player); break; 
				case "����":
					SetReporter(player); break; 
				case "������":
					SetMagician(player); break; 
				case "���𰡵�":
					SetGuadian(player); break; 
				case "�ù�":
					SetCivilian(player); break; 
				case "�����":
					SetAssasin(player);
					player.sendMessage(Main.MS+"��6"+target+"���� ����ڿ����ϴ�.");
					if(mlist.contains(Main.plist.get(target))){
						player.sendMessage(target+"��7���� ����� �� ����ڿ��� ������ ����� ���θ����� �˴ϴ�.");
					} else {
						player.sendMessage(target+"��7���� ����� �������� ����ڿ��� ������ ����� ���θ����� ���� �ʽ��ϴ�.");
					}
					return; 
				case "Ż����":
					player.sendMessage(Main.MS+"Ż������ ������ ������ �� �����ϴ�.");; return; 
				default:
					player.sendMessage(Main.MS+target+" ���� �Ҵ���� ���� �����Դϴ�. - ���� �ǽ�"); break;
			}
			if(mlist.contains(Main.plist.get(target)))
			{
				mlist.remove(Main.plist.get(target));
				Main.SendMessage(Main.MS+"���θ����̾��� "+target+" ���� �����翡�� ������ ���Ұ� �ù��� �Ǿ����ϴ�!");
			}
			Main.jlist.put(target, "�ù�");
			Main.glist.put(target, "�ù�");
		}
	}

  public static void UseGuadian(Player player, String target)
  {
    if (Checktarget(player, target))
    {
      return;
    }
    if (uc_guardiancnt >= 2)
    {
      player.sendMessage(Main.MS + "�̹� �ɷ��� 2�� ��� �ϼ̽��ϴ�. �������� ��ٸ�����");
      return;
    }
    if (isday)
    {
      player.sendMessage(Main.MS + "���𰡵��� �ɷ��� �㿡�� ����� �����մϴ�.");
    }
    else
    {
      if (uc_guardiancnt == 1)
      {
        uc_guardian1 = target;
      }
      if (uc_guardiancnt == 2)
      {
        uc_guardian2 = target;
      }
      player.sendMessage(Main.MS + target + " �Կ��� �ɷ��� ����Ͽ����ϴ�.");
      player.sendMessage(Main.MS + "�� �÷��̾�� ������ ���� ��ǥ�� ���Ͽ� ������ �߼����� �ʽ��ϴ�.");
      uc_guardiancnt += 1;
    }
  }

  public static void UseCivilian(Player player)
  {
    player.sendMessage(Main.MS + "�ù��� �ƹ��� �ɷ��� �����ϴ�.");
  }

  public static void UseAssasin(Player player)
  {
    player.sendMessage(Main.MS + "����� 1��°���� ������ �Ͽ� �ù����� ������� �ƴϸ� \n��7������� �ʰ� ���θ��� �ο��� ���Ҽ� �ֽ��ϴ�..");
  }

  public static void SetMurder(Player player)
  {
    tmplist.remove(player);
    player.setHealth(player.getMaxHealth());
    Main.jlist.put(player.getName(), "���θ�");
    Main.glist.put(player.getName(), "?");
    mlist.add(player);
    player.sendMessage(Main.MS + "����� ������ " + ChatColor.GOLD + 
      (String)Main.jlist.get(player.getName()) + " �Դϴ�.");
    Givemurderitem(player);
  }

  public static void SetDoctor(Player player)
  {
    tmplist.remove(player);
    player.setHealth(player.getMaxHealth());
    Main.jlist.put(player.getName(), "�ǻ�");
    Main.glist.put(player.getName(), "?");
    player.sendMessage(Main.MS + "����� ������ " + ChatColor.GOLD + 
      (String)Main.jlist.get(player.getName()) + " �Դϴ�.");
  }

  public static void SetPolice(Player player)
  {
    tmplist.remove(player);
    player.setHealth(player.getMaxHealth());
    Main.jlist.put(player.getName(), "����");
    Main.glist.put(player.getName(), "?");
    player.sendMessage(Main.MS + "����� ������ " + ChatColor.GOLD + 
      (String)Main.jlist.get(player.getName()) + " �Դϴ�.");
    Givepoliceitem(player);
  }

  public static void SetSoldier(Player player)
  {
    tmplist.remove(player);
    player.setHealth(player.getMaxHealth());
    Main.jlist.put(player.getName(), "����");
    Main.glist.put(player.getName(), "?");
    player.sendMessage(Main.MS + "����� ������ " + ChatColor.GOLD + 
      (String)Main.jlist.get(player.getName()) + " �Դϴ�.");
    uc_soldier = true;
    uc_reporter = false;
  }

  public static void SetSpy(Player player)
  {
    tmplist.remove(player);
    player.setHealth(player.getMaxHealth());
    Main.jlist.put(player.getName(), "������");
    Main.glist.put(player.getName(), "?");
    player.sendMessage(Main.MS + "����� ������ " + ChatColor.GOLD + 
      (String)Main.jlist.get(player.getName()) + " �Դϴ�.");
  }

  public static void SetDetective(Player player)
  {
    tmplist.remove(player);
    player.setHealth(player.getMaxHealth());
    Main.jlist.put(player.getName(), "Ž��");
    Main.glist.put(player.getName(), "?");
    player.sendMessage(Main.MS + "����� ������ " + ChatColor.GOLD + 
      (String)Main.jlist.get(player.getName()) + " �Դϴ�.");
  }

  public static void SetNice_Civilian(Player player)
  {
    tmplist.remove(player);
    player.setHealth(player.getMaxHealth());
    Main.jlist.put(player.getName(), "����ù�");
    Main.glist.put(player.getName(), "����ù�");
    player.sendMessage(Main.MS + "����� ������ " + ChatColor.GOLD + 
      (String)Main.jlist.get(player.getName()) + " �Դϴ�.");
    player.sendMessage(Main.MS + "����� ������ ��������Ʈ�� ����ù����� �÷������ϴ�.");
  }

  public static void SetReporter(Player player)
  {
    tmplist.remove(player);
    player.setHealth(player.getMaxHealth());
    Main.jlist.put(player.getName(), "����");
    Main.glist.put(player.getName(), "?");
    player.sendMessage(Main.MS + "����� ������ " + ChatColor.GOLD + 
      (String)Main.jlist.get(player.getName()) + " �Դϴ�.");
    uc_reporter = false;
  }

  public static void SetMagician(Player player)
  {
    tmplist.remove(player);
    player.setHealth(player.getMaxHealth());
    Main.jlist.put(player.getName(), "������");
    Main.glist.put(player.getName(), "?");
    player.sendMessage(Main.MS + "����� ������ " + ChatColor.GOLD + 
      (String)Main.jlist.get(player.getName()) + " �Դϴ�.");
  }

  public static void SetGuadian(Player player)
  {
    tmplist.remove(player);
    player.setHealth(player.getMaxHealth());
    Main.jlist.put(player.getName(), "���𰡵�");
    Main.glist.put(player.getName(), "?");
    player.sendMessage(Main.MS + "����� ������ " + ChatColor.GOLD + 
      (String)Main.jlist.get(player.getName()) + " �Դϴ�.");
  }

  public static void SetCivilian(Player player)
  {
    tmplist.remove(player);
    player.setHealth(player.getMaxHealth());
    Main.jlist.put(player.getName(), "�ù�");
    Main.glist.put(player.getName(), "?");
    player.sendMessage(Main.MS + "����� ������ " + ChatColor.GOLD + 
      (String)Main.jlist.get(player.getName()) + " �Դϴ�.");
  }

  public static void SetAssasin(Player player)
  {
    astarget = null;
    tmplist.remove(player);
    player.setHealth(player.getMaxHealth());
    Main.jlist.put(player.getName(), "�����");
    Main.glist.put(player.getName(), "?");
    player.sendMessage(Main.MS + "����� ������ " + ChatColor.GOLD + 
      (String)Main.jlist.get(player.getName()) + " �Դϴ�.");

    assasin = player;
  }
  
  public static void SetPrisoner(Player player)
  {
	    tmplist.remove(player);
	    player.setHealth(player.getMaxHealth());
	    Main.jlist.put(player.getName(), "Ż����");
	    Main.glist.put(player.getName(), "Ż����");
	    player.sendMessage(Main.MS + "����� ������ " + ChatColor.GOLD + 
	      (String)Main.jlist.get(player.getName()) + " �Դϴ�.");
	    player.sendMessage(Main.MS + "����� ������ ��������Ʈ�� Ż������ �÷������ϴ�.");
	    Main.SendMessage(Main.MS+"Ż���� "+player.getName()+" �� �ֽ��ϴ�. �����ϼ���!");
	    player.getInventory().addItem(Main.Makeitem(282, "Ż������ ��", 5, 6));
	    mlist.add(player);
  }

  public static void SetJoker(Player player)
  {
    tmplist.remove(player);
    player.setHealth(player.getMaxHealth());
    Main.jlist.put(player.getName(), "��Ŀ");
    Main.glist.put(player.getName(), "?");
    player.sendMessage(Main.MS + "����� ������ " + ChatColor.GOLD + 
      (String)Main.jlist.get(player.getName()) + " �Դϴ�.");
  }

  public static int Getsurviver()
  {
    return Main.list.size() - mlist.size();
  }

  public static void Murderdead(Player player)
  {
    Main.SendMessage(Main.MS + ChatColor.GRAY + "���θ����� " + ChatColor.RED + player.getName() + ChatColor.GRAY + " ���� ����߽��ϴ�.");
    String temp = Getjob(player.getName());
    Removeplayerfl(player);
    if (mlist.size() <= 0)
    {
      Main.Civilwin();
    }
    else if (temp.equalsIgnoreCase("���θ�"))
    {
      Main.SendMessage(Main.MS + "���θ��� ����Ͽ����� ���� ���θ��� ���ᰡ �����ֽ��ϴ�!");
    }
    else
    {
      Main.SendMessage(Main.MS + "���� ���θ��� ���ᰡ �����ֽ��ϴ�!");
    }
  }

  public static void Reverse(Player player)
  {
    player.teleport(Main.Startpos[Getrandom(Main.Startpos.length)]);
    player.setHealth(player.getMaxHealth());
    player.sendMessage(Main.MS + "��Ȱ �ϼ̽��ϴ�.");
    player.sendMessage(Main.MS + "�κ��丮�� �ʱ�ȭ �˴ϴ�.");
  }

  public static void Civildead(Player player)
  {
    Main.SendMessage(Main.MS + ChatColor.RED + player.getName() + ChatColor.GRAY + " ���� ����߽��ϴ�.");
    Removeplayerfl(player);
    if (Getsurviver() <= 0)
    {
      Main.Murderwin();
    }
  }

  public static void Removeplayerfl(Player player)
  {
    Main.list.remove(player);
    Main.plist.remove(player.getName());
    Main.glist.remove(player.getName());
    Main.jlist.remove(player.getName());
    if (mlist.contains(player))
    {
      mlist.remove(player);
    }
  }

  public static boolean CheckReverse(Player player)
  {
    if (doctortarget != null)
    {
      if (doctortarget.equalsIgnoreCase(player.getName()))
      {
        doctortarget = null;
        Main.SendMessage(Main.MS + "�ǻ��� ġ��� ���Ͽ� " + player.getName() + " ���� ���� �ʾҽ��ϴ�.");
        return true;
      }
    }
    else if ((Getjob(player.getName()).equalsIgnoreCase("����")) && (uc_soldier))
    {
      uc_soldier = false;
      Main.SendMessage(Main.MS + "������ " + player.getName() + " ���� ���θ��� ������ ���߳½��ϴ�!");
      Main.SendMessage(Main.MS + player.getName() + " ���� ������ �ù����� ����˴ϴ�.");
      Main.jlist.put(player.getName(), "�ù�");
      Main.glist.put(player.getName(), "�ù�");
      return true;
    }
    return false;
  }

  public static void Quitplayer(Player player)
  {
    if (!Main.list.contains(player))
    {
      return;
    }
    if (checkstart)
    {
      if (mlist.contains(player))
      {
        Murderdead(player);
      }
      else
      {
        Civildead(player);
      }
      Main.SendMessage(Main.MS + ChatColor.AQUA + player.getName() + " ���� ���� �ϼ̽��ϴ�.");
    }
    else
    {
      Main.list.remove(player);
      Main.SendMessage(Main.MS + ChatColor.AQUA + player.getName() + ChatColor.GRAY + " ���� ���� �߽��ϴ�. " + ChatColor.RESET + "[ " + ChatColor.GREEN + Main.list.size() + " / 6 " + ChatColor.RESET + "]");
    }
  }

  public static void Givespyitem(Player player)
  {
    ItemStack item = Main.Makeitem(283, "������ ��", 7, 7);
    player.getInventory().addItem(item);
  }

  public static void Givemurderitem(Player player)
  {
    ItemStack item = new ItemStack(351, 1, (byte) 1);
	  ItemMeta meta = item.getItemMeta();
	  meta.setDisplayName("��c�� ���� ��Ŭ");
	  List<String> lorelist = new ArrayList<String>();
	  String damage = "��7���ݷ�: 7";
	  lorelist.add(damage);
	  meta.setLore(lorelist);
	  item.setItemMeta(meta);
    for(int i = 0; i < 4; i++){
    	player.getInventory().addItem(Main.chestitem[16]);
    }
    player.getInventory().addItem(item);
  }

  public static void GiveBasicitem()
  {
    ItemStack item = new ItemStack(Material.PUMPKIN_PIE, 50);
    ItemMeta meta = item.getItemMeta();
    meta.setDisplayName(ChatColor.GOLD + "���ִ� ȣ�� ����");
    item.setItemMeta(meta);
    for (int i = 0; i < Main.list.size(); i++)
    {
      ((Player)Main.list.get(i)).getInventory().addItem(new ItemStack[] { item });
    }
  }
  
	public static String getHandItemName(Player p){
		ItemStack item = p.getItemInHand();
		if(item == null || !item.hasItemMeta() || !item.getItemMeta().hasDisplayName()) return "meta����";
		return item.getItemMeta().getDisplayName();
	}

  public static void Givepoliceitem(Player player)
  {
    ItemStack item = new ItemStack(Material.STICK, 3);
    ItemMeta meta = item.getItemMeta();
    meta.setDisplayName(ChatColor.AQUA + "������");
    meta.addEnchant(Enchantment.KNOCKBACK, 1, true);
    List lorelist = new ArrayList();
    lorelist.add(ChatColor.GRAY + "�������� �־����� �����");
    lorelist.add(ChatColor.GRAY + "�� ����� ���� �÷��̾�� 3�ʰ�");
    lorelist.add(ChatColor.GRAY + "�������� ���Ѵ�. (������ ��밡��)");
    meta.setLore(lorelist);
    item.setItemMeta(meta);
    player.getInventory().addItem(new ItemStack[] { item });
  }
}