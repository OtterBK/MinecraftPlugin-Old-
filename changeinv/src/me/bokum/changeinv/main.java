package me.bokum.changeinv;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.java.JavaPlugin;

public class main extends JavaPlugin implements Listener
{
	String CC = ChatColor.GOLD + "["+ChatColor.GREEN+"EG"+ChatColor.GOLD+"]";
	List<Player> list = new ArrayList<Player>();
	int schtime = 0;

	
	public void onEnable ()
	{
		getServer().getPluginManager().registerEvents(this, this);
		getLogger().info("�κ��丮 ���� �÷����� �ε� �Ϸ�! - ����: Bokum");
	}
	
	public void onDisable()
	{
		getLogger().info("�κ��丮 ���� �÷����� ��ε� �Ϸ�!");
	}
	
	public boolean onCommand (CommandSender sender, Command command, String string,String[] args)
	{
		if(sender instanceof Player)
		{
			Player player = (Player) sender;
			if(string.equalsIgnoreCase("cinv"))
			{
				if(player.hasPermission("changeinv"))
				{
					if(args.length >= 1)
					{
						if(args[0].equalsIgnoreCase("force"))
						{
							if(list.size() <= 1)
							{
								player.sendMessage(CC+"������ �÷��̾�� ��� 2�� �̻��̿��� �մϴ�.");;
							}
							else
							{
								changeinv();
								player.sendMessage(CC+"�Ϸ�");
							}
						}
						if(args[0].equalsIgnoreCase("add"))
						{
							if(args.length < 2)
							{
								player.sendMessage(CC+"�÷��̾��� �̸��� �Է����ּ���.");
							}
							else
							{
								addplayer(player,args[1]);
							}
						}
						if(args[0].equalsIgnoreCase("remove"))
						{
							if(args.length < 2)
							{
								player.sendMessage(CC+"������ �÷��̾��� �̸��� �Է����ּ���.");
							}
							else
							{
								player.sendMessage(CC+"���� ���� Ȯ��");
								removeplayer(player,args[1]);
							}
						}
						if(args[0].equalsIgnoreCase("list"))
						{
							listplayer(player);
						}
						else if(args[0].equalsIgnoreCase("start"))
						{
							player.sendMessage(CC+"Ÿ�̸Ӹ� ���� �մϴ�.");
							Bukkit.getScheduler().cancelTask(schtime);
							Timer();
						}
						else if(args[0].equalsIgnoreCase("stop"))
						{
							player.sendMessage(CC+"Ÿ�̸Ӹ� ���� �մϴ�.");
							Bukkit.getScheduler().cancelTask(schtime);
						}
					}
					else
					{
						changeinvhelp(player);
					}
				}
				else
				{
					player.sendMessage(CC+"������ �����ϴ�.");
				}
			}
		}
		return true;
	}

	public void changeinvhelp(Player player)
	{
		player.sendMessage(CC+"/cinv add <�÷��̾�г���>"
				+ " - �ش� �÷��̾ �κ��丮 ��ȯ ��Ͽ� �߰��մϴ�.");
		player.sendMessage(CC+"/cinv remove <�÷��̾�г���>"
				+ "- �ش� �÷��̾ �κ��丮 ��ȯ ��Ͽ��� �����մϴ�.");
		player.sendMessage(CC+"/cinv list - "
				+ "��ȯ��Ͽ� ������ �÷��̾ ���ϴ�.");
		player.sendMessage(CC+"/cinv ??? - �κ��丮�� �����մϴ�. ( ����� ��û���� ��ɾ ���ܵ� )");
		player.sendMessage(CC+"/cinv start - 5�и��� �κ��丮�� �ٲ�� Ÿ�̸Ӹ� �����մϴ�.");
		player.sendMessage(CC+"/cinv stop - Ÿ�̸Ӹ� �����մϴ�.");
		player.sendMessage(CC+ChatColor.AQUA + "���� : 0.71");
		player.sendMessage(CC+ChatColor.AQUA + "���� : Bokum");
	}
	
	public void Timer()
	{
		schtime = Bukkit.getScheduler().scheduleSyncRepeatingTask(this, new Runnable()
		{
			public void run()
			{
				changeinv();
			}
		}, 6000L, 6000L);
	}
	
	public void addplayer(Player player, String target)
	{
		Player onlineplayer[] = Bukkit.getServer().getOnlinePlayers();
		for(int i = 0; i < onlineplayer.length ; i++ )
		{
			if(onlineplayer[i].getName().equalsIgnoreCase(target))
			{
				Player tmpplayer = onlineplayer[i];
				if(list.contains(tmpplayer))
				{
					player.sendMessage(CC+tmpplayer.getName()+" ���� �̹� ��Ͽ� �ֽ��ϴ�.");
					return;
				}
				list.add(tmpplayer);
				player.sendMessage(CC+tmpplayer.getName()+"���� �����Ǿ����ϴ�.");
				return;
			}
		}
		player.sendMessage(CC+target+" �̶�� �÷��̾�� �������� �ʽ��ϴ�.");
	}

	public void listplayer(Player player)
	{
		if(list.size() <= 0)
		{
			player.sendMessage(CC+"������ �÷��̾ �������� �ʽ��ϴ�.");
			return;
		}
		else
		{
			player.sendMessage(CC+"������ �÷��̾� ��� : ");
			for(int i = 0; i < list.size(); i++)
			{
				player.sendMessage(list.get(i).getName());
			}
		}
	}
	public void removeplayer(Player player, String target)
	{
		for(Player p : list)
		{
			if(p.getName().equalsIgnoreCase(target))
			{
				list.remove(p);
				return;
			}
		}
		player.sendMessage(CC+target+"�̶�� �÷��̾ �������� �ʽ��ϴ�.");
	}
	
	public int getrandom(int i)
	{
		return (int)(Math.random()*i);
	}
	
	public void changeinv()
	{
		List<Player> tmplist = new ArrayList();
		tmplist.addAll(list);
		List<Inventory> invenlist = new ArrayList();
		/*List<ItemStack> helmetlist = new ArrayList();
		List<ItemStack> platelist = new ArrayList();
		List<ItemStack> legslist = new ArrayList();
		List<ItemStack> bootslist = new ArrayList();*/
		List<ItemStack[]> armorlist = new ArrayList();
		for(int i = 0; i < tmplist.size(); i++)
		{
			Inventory iv = Bukkit.createInventory(null, 36);
			iv.setContents(tmplist.get(i).getInventory().getContents());
			invenlist.add(iv);
			/*ItemStack helmet = new ItemStack(tmplist.get(i).getInventory().getHelmet());
			ItemStack plate = new ItemStack(tmplist.get(i).getInventory().getChestplate());
			ItemStack legs = new ItemStack(tmplist.get(i).getInventory().getLeggings());
			ItemStack boots = new ItemStack(tmplist.get(i).getInventory().getBoots());
			helmetlist.add(helmet);
			platelist.add(plate);
			legslist.add(legs);
			bootslist.add(boots);*/

			ItemStack[] armorcontents = tmplist.get(i).getInventory().getArmorContents();
			armorlist.add(armorcontents);
		}
		while(tmplist.size() > 0)
		{
			int listrandom = getrandom(tmplist.size());
			int invrandom = getrandom(invenlist.size());
			if(tmplist.size() >= 2 && invenlist.size() >= 2 && listrandom == invrandom)
			{
				continue;
			}
			tmplist.get(listrandom).getInventory().setContents(invenlist.get(invrandom).getContents());
			tmplist.get(listrandom).getInventory().setArmorContents(armorlist.get(invrandom));
			tmplist.get(listrandom).sendMessage(CC+"�κ��丮�� ��ȯ �Ǿ����ϴ�.");
			tmplist.remove(listrandom);
			invenlist.remove(invrandom);
			armorlist.remove(invrandom);
		}
	}
	
	@EventHandler
	public void onJoin(PlayerJoinEvent e)
	{
		Player player = e.getPlayer();
		for(int i = 0; i < list.size(); i++)
		{
			if(list.get(i).getName().equalsIgnoreCase(player.getName()))
			{
				list.set(i, player);
				return;
			}
		}
	}
	
}