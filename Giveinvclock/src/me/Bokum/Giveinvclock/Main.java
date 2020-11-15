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
		getLogger().info("�ð� ���� �÷����� �ε� �Ϸ� - ���� Bokum");
	}
	
	public void onDisable()
	{
		getLogger().info("�ð� ���� �÷����� ��ε� �Ϸ� - ���� Bokum");
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
				player.sendMessage(MS+ChatColor.RED + "9��° ĭ�� �ִ� �ð�� �̵���ų �� �����ϴ�.");
			}
		}
	}
	
	@EventHandler
	public void onDropClock(PlayerDropItemEvent e)
	{
		Player player = (Player) e.getPlayer();
		if(e.getItemDrop().getItemStack().getTypeId() == 347 && player.getInventory().getHeldItemSlot() == 8)
		{
			player.sendMessage(MS+ChatColor.RED+"9��° ĭ�� �ִ� �ð�� ������ �� �����ϴ�.");
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
