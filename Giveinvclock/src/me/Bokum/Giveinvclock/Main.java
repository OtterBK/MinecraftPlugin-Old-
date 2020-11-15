package me.Bokum.Giveinvclock;

import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.player.PlayerDropItemEvent;
import org.bukkit.event.player.PlayerRespawnEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.java.JavaPlugin;

public class Main extends JavaPlugin implements Listener
{
	public static String MS = ChatColor.RESET+"[ "+ChatColor.GREEN+"TB"+ChatColor.RESET+" ] ";
	
	public void onEnable()
	{
		getServer().getPluginManager().registerEvents(this, this);
		getLogger().info("시계 지급 플러그인 로드 완료 - 제작 Bokum");
	}
	
	public void onDisable()
	{
		getLogger().info("시계 지급 플러그인 언로드 완료 - 제작 Bokum");
	}
	
	/*@EventHandler
	public void onPlayerdeath(PlayerDeathEvent e)
	{
		if(e.getEntity() instanceof Player)
		{
			Player player = (Player) e.getEntity();
			for(int i = 0; i < player.getInventory().getSize(); i++)
			{
				ItemStack pitem = player.getInventory().getItem(i);
				if(pitem != null && pitem.getTypeId() == 347)
				{
					player.getInventory().setItem(i, null);
				}
			}
		}
	}*/
	
	@EventHandler
	public void onClickClock(InventoryClickEvent e)
	{
		if(e.getWhoClicked() instanceof Player && e.getSlot() == 8)
		{
			Player player = (Player) e.getWhoClicked();
			ItemStack pitem = e.getCurrentItem();
			if(pitem != null && pitem.getTypeId() == 347)
			{
				e.setCancelled(true);
				player.sendMessage(MS+ChatColor.RED + "9번째 칸에 있는 시계는 이동시킬 수 없습니다.");
			}
		}
	}
	
	@EventHandler
	public void onDropClock(PlayerDropItemEvent e)
	{
		Player player = (Player) e.getPlayer();
		if(e.getItemDrop().getItemStack().getTypeId() == 347 && player.getInventory().getHeldItemSlot() == 8)
		{
			player.sendMessage(MS+ChatColor.RED+"9번째 칸에 있는 시계는 버리실 수 없습니다.");
			ItemStack clock = new ItemStack(347, 1);
			player.getInventory().setItem(8, clock);
			e.getItemDrop().remove();
		}
	}
	
	@EventHandler
	public void onRespawn(PlayerRespawnEvent e)
	{
		Player player = (Player) e.getPlayer();
		ItemStack clock = new ItemStack(347, 1);
		player.getInventory().setItem(8, clock);
	}
}
