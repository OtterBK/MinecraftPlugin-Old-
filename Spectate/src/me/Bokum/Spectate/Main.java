package me.Bokum.Spectate;



import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.block.Chest;
import org.bukkit.block.DoubleChest;
import org.bukkit.entity.Entity;
import org.bukkit.entity.HumanEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryOpenEvent;
import org.bukkit.event.player.PlayerChatEvent;
import org.bukkit.event.player.PlayerDropItemEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerPickupItemEvent;
import org.bukkit.plugin.java.JavaPlugin;

public class Main extends JavaPlugin implements Listener
{
	String MS = ChatColor.GREEN+"[EG] "+ChatColor.GRAY;
	
	public void onEnable()
	{
		getServer().getPluginManager().registerEvents(this, this);
		getLogger().info("관전 플러그인 로드 완료 - 제작 : Bokum");
	}
	
	public void onDisbale()
	{
		getLogger().info("관전 플러그인 언로드 완료 - 제작 : Bokum");
	}
	@EventHandler
	public void onBlockBreak(BlockBreakEvent e)
	{
		Player player = e.getPlayer();
		if(!player.hasPermission("entry"))
		{
			e.setCancelled(true);
			player.sendMessage(MS+"대회 관전 중에는 경기에 개입 할 수 없습니다.");
		}
	}
	
	@EventHandler
	public void onBlockPlace(BlockPlaceEvent e)
	{
		Player player = e.getPlayer();
		if(!player.hasPermission("entry"))
		{
			e.setCancelled(true);
			player.sendMessage(MS+"대회 관전 중에는 경기에 개입 할 수 없습니다.");
		}
	}
	
	@EventHandler
	public void onIventoryClick(InventoryClickEvent e)
	{
		HumanEntity clicke = e.getWhoClicked();
		if(!(clicke instanceof Player))
		{
			return;
		}
		Player player = (Player) clicke;
		if(!player.hasPermission("entry"))
		{
			e.setCancelled(true);
			player.sendMessage(MS+"대회 관전 중에는 경기에 개입 할 수 없습니다.");
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
		if(!player.hasPermission("entry"))
		{
			e.setCancelled(true);
			player.sendMessage(MS+"대회 관전 중에는 경기에 개입 할 수 없습니다.");
		}
	}
	
	@EventHandler
	public void onPickupitem(PlayerPickupItemEvent e)
	{
		if(!e.getPlayer().hasPermission("entry"))
		{
			e.setCancelled(true);
		}
	}
	
	@EventHandler
	public void onThrowitem(PlayerDropItemEvent e)
	{
		if(!e.getPlayer().hasPermission("entry"))
		{
			e.setCancelled(true);
			e.getPlayer().sendMessage(MS+"대회 관전 중에는 경기에 개입 할 수 없습니다.");
		}
	}
	
	@EventHandler
	public void onOpenChest(InventoryOpenEvent e)
	{
		if(!(e.getPlayer() instanceof Player))
		{
			return;
		}
		Player player = (Player) e.getPlayer();
		if((e.getInventory().getHolder() instanceof Chest || 
				e.getInventory().getHolder() instanceof DoubleChest)&&
				!(player.hasPermission("entry")))
		{
			e.setCancelled(true);
			player.sendMessage(MS+"대회 관전 중에는 경기에 개입 할 수 없습니다.");
		}
	}
	
	@EventHandler
	public void onJoinPlayer(PlayerJoinEvent e)
	{
		if(!(e.getPlayer().hasPermission("entry")))
		{
			e.getPlayer().sendMessage(MS+"관전을 시작합니다. 플라이가 적용됩니다.");
			e.getPlayer().setAllowFlight(true);
			e.getPlayer().setFlying(true);
			Player player[] = Bukkit.getOnlinePlayers();
			for(int i = 0; i < player.length; i++)
			{
				if(player[i].hasPermission("entry"))
				{
					player[i].hidePlayer(e.getPlayer());
				}
			}
		}
		else
		{
			Bukkit.broadcastMessage(MS+e.getPlayer().getName()+"님이 대회에 재참가 합니다.");
			Player player[] = Bukkit.getOnlinePlayers();
			for(int i = 0; i < player.length; i++)
			{
				if(!(player[i].hasPermission("entry")))
				{
					e.getPlayer().hidePlayer(player[i]);
				}
			}
		}
	}
	
	@EventHandler
	public void onChat(PlayerChatEvent e)
	{
		if(!(e.getPlayer().hasPermission("entry")))
		{
			e.setCancelled(true);
			Player player[] = Bukkit.getOnlinePlayers();
			for(int i = 0; i < player.length; i++)
			{
				if(!(player[i].hasPermission("entry")))
				{
					player[i].sendMessage(ChatColor.RESET+"["+ChatColor.BLUE+"관전 대화"
						+ChatColor.RESET+"]"+e.getPlayer().getName()+" : "+e.getMessage());
				}
			}
		}
	}
	@EventHandler
	public void onDamge(EntityDamageEvent e)
	{
		if(!(e.getEntity() instanceof Player))
		{
			return;
		}
		Player player = (Player) e.getEntity();
		if(!(player.hasPermission("entry")))
		{
			e.setCancelled(true);
		}
	}
}
