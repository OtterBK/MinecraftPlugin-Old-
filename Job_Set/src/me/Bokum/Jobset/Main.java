package me.Bokum.Jobset;

import java.awt.print.Paper;
import java.util.ArrayList;
import java.util.List;

import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.player.PlayerDropItemEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.plugin.java.JavaPlugin;

public class Main extends JavaPlugin implements Listener
{
	public static Main instance;
	public static String MS = ChatColor.RESET+"["+ChatColor.AQUA+"JS"+ChatColor.RESET+"] "+ChatColor.GRAY;
	
	public void onEnable()
	{
		getServer().getPluginManager().registerEvents(this, this);
		getLogger().info("직업 설정 플러그인 로드 완료! - 제작 : Bokum");
		instance = this;
	}
	
	public void onDisable()
	{
		getLogger().info("직업 설정 플러그인 언로드");
		saveConfig();
	}
	
	public boolean onCommand(CommandSender talker, Command command, String string, String[] args)
	{
		if(talker instanceof Player)
		{
			Player player = (Player) talker;
			
			if(string.equalsIgnoreCase("js"))
			{
				if(args.length == 0)
				{
					Helpmessage(player);
				}
				else if(args[0].equalsIgnoreCase("set"))
				{
					if(args.length <= 2)
					{
						Falsearg(player);
						return true;
					}
					else
					{
							String str = getConfig().getString(args[1]);
							if(str != null)
							{
								player.sendMessage(MS+args[1]+" 님은 이미 "+str+" 로 전직을 한 상태 입니다.");
								return true;
							}
							getConfig().set(args[1], ChatColor.RESET+args[2]);
							saveConfig();
							player.sendMessage(MS+args[1]+" 해당 플레이어의 직업을 설정했습니다.");
							return true;
					}
				}
				else if(args[0].equalsIgnoreCase("add"))
				{
					if(args.length <= 1)
					{
						Falsearg(player);
						return true;
					}
					else
					{
						Setjobitem(player, args[1]);
						return true;
					}
				}
				else if(args[0].equalsIgnoreCase("check"))
				{
					String str = getConfig().getString(player.getName());
					if(str == null)
					{
						player.sendMessage(MS+"설정된 직업이 없습니다.");
						return true;
					}
					player.sendMessage(MS+"당신의 직업은 "+ChatColor.AQUA+getConfig().getString(player.getName())+ChatColor.GRAY+" 입니다.");
				}
			}
		}
		return true;
	}
	
	public void Falsearg(Player player)
	{
		player.sendMessage(MS+"잘못된 인수입니다. 다시 확인해주세요.");
	}
	
	public void Helpmessage(Player player)
	{
		player.sendMessage(MS+"/js set <플레이어> <직업명> - 플레이어를 해당직업으로 설정합니다.");
		player.sendMessage(MS+"/js check - 자신의 직업을 확인합니다.");
		player.sendMessage(MS+"/js add <직업명> - 손에 든 무기를 해당직업전용 무기로 설정합니다.");
		player.sendMessage(MS+"제작 : Bokum");
	}
	
	public void Setjobitem(Player player, String str)
	{
		ItemStack item = player.getItemInHand();
		ItemMeta meta = item.getItemMeta();
		List<String> lorelist;
		lorelist = meta.getLore();
		if(lorelist == null)
		{
			lorelist = new ArrayList();
		}
		lorelist.add(ChatColor.RESET+str+"직업 무기");
		meta.setLore(lorelist);
		item.setItemMeta(meta);
	}
	
	public boolean Checkjob(Player player)
	{
		try
		{
			ItemStack item = player.getItemInHand();
			List<String> lore = item.getItemMeta().getLore();
			String str;
			str = getConfig().getString(player.getName());
			if(str == null)
			{
				return true;
			}
			return lore.contains(str+"직업 무기");
		}
		catch(java.lang.NullPointerException e)
		{
			return true;
		}
	}
	
	@EventHandler
	public void onClick(PlayerInteractEvent e)
	{
		if(!(Checkjob(e.getPlayer())))
		{
			e.setCancelled(true);
			e.getPlayer().sendMessage(MS+"당신은 이 무기를 사용할 수 있는 직업이 아닙니다.");
		}
	}
	
	@EventHandler
	public void onHitPlayer(EntityDamageByEntityEvent e)
	{
		Entity atk = e.getDamager();
		if(!(atk instanceof Player))
		{
			return;
		}
		Player player = (Player) atk;
		if(!(Checkjob(player)))
		{
			e.setCancelled(true);
		}
	}
	
	@EventHandler
	public void onDropitem(PlayerDropItemEvent e)
	{
		if(e.getItemDrop().getItemStack().getTypeId() == 339)
		{
			e.setCancelled(true);
			e.getPlayer().sendMessage(MS+"중요아이템은 버리실 수 없습니다.");
		}
	}
}
