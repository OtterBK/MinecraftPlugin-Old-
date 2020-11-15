package me.Bokum.DenyBuild;

import org.bukkit.Bukkit;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockPhysicsEvent;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.plugin.java.JavaPlugin;

public class Main extends JavaPlugin implements Listener
{
	public void onEnable()
	{
		getServer().getPluginManager().registerEvents(this, this);
		getLogger().info("�� ��ȣ �÷������� �ε� �Ǿ����ϴ�.");
	}
	
	public void onDisable()
	{
		getLogger().info("�� ��ȣ �÷������� ��ε� �Ǿ����ϴ�.");
	}
	
	@EventHandler
	public void onBlockbreak(BlockBreakEvent e)
	{
		if(!e.getPlayer().isOp())
		{
			e.setCancelled(true);
		}
	}
	
	@EventHandler
	public void onBlockplace(BlockPlaceEvent e)
	{
		if(!e.getPlayer().isOp())
		{
			e.setCancelled(true);
		}
	}
}
