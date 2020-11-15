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
		getLogger().info("인벤토리 섞기 플러그인 로드 완료! - 제작: Bokum");
	}
	
	public void onDisable()
	{
		getLogger().info("인벤토리 섞기 플러그인 언로드 완료!");
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
								player.sendMessage(CC+"지정된 플레이어는 적어도 2명 이상이여야 합니다.");;
							}
							else
							{
								changeinv();
								player.sendMessage(CC+"완료");
							}
						}
						if(args[0].equalsIgnoreCase("add"))
						{
							if(args.length < 2)
							{
								player.sendMessage(CC+"플레이어의 이름을 입력해주세요.");
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
								player.sendMessage(CC+"삭제할 플레이어의 이름을 입력해주세요.");
							}
							else
							{
								player.sendMessage(CC+"삭제 실행 확인");
								removeplayer(player,args[1]);
							}
						}
						if(args[0].equalsIgnoreCase("list"))
						{
							listplayer(player);
						}
						else if(args[0].equalsIgnoreCase("start"))
						{
							player.sendMessage(CC+"타이머를 시작 합니다.");
							Bukkit.getScheduler().cancelTask(schtime);
							Timer();
						}
						else if(args[0].equalsIgnoreCase("stop"))
						{
							player.sendMessage(CC+"타이머를 중지 합니다.");
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
					player.sendMessage(CC+"권한이 없습니다.");
				}
			}
		}
		return true;
	}

	public void changeinvhelp(Player player)
	{
		player.sendMessage(CC+"/cinv add <플레이어닉네임>"
				+ " - 해당 플레이어를 인벤토리 교환 목록에 추가합니다.");
		player.sendMessage(CC+"/cinv remove <플레이어닉네임>"
				+ "- 해당 플레이어를 인벤토리 교환 목록에서 삭제합니다.");
		player.sendMessage(CC+"/cinv list - "
				+ "교환목록에 지정된 플레이어를 봅니다.");
		player.sendMessage(CC+"/cinv ??? - 인벤토리를 변경합니다. ( 뱀사의 요청으로 명령어를 숨겨둠 )");
		player.sendMessage(CC+"/cinv start - 5분마다 인벤토리가 바뀌도록 타이머를 시작합니다.");
		player.sendMessage(CC+"/cinv stop - 타이머를 중지합니다.");
		player.sendMessage(CC+ChatColor.AQUA + "버젼 : 0.71");
		player.sendMessage(CC+ChatColor.AQUA + "제작 : Bokum");
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
					player.sendMessage(CC+tmpplayer.getName()+" 님은 이미 목록에 있습니다.");
					return;
				}
				list.add(tmpplayer);
				player.sendMessage(CC+tmpplayer.getName()+"님이 지정되었습니다.");
				return;
			}
		}
		player.sendMessage(CC+target+" 이라는 플레이어는 존재하지 않습니다.");
	}

	public void listplayer(Player player)
	{
		if(list.size() <= 0)
		{
			player.sendMessage(CC+"지정한 플레이어가 존재하지 않습니다.");
			return;
		}
		else
		{
			player.sendMessage(CC+"지정된 플레이어 목록 : ");
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
		player.sendMessage(CC+target+"이라는 플레이어가 존재하지 않습니다.");
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
			tmplist.get(listrandom).sendMessage(CC+"인벤토리가 교환 되었습니다.");
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