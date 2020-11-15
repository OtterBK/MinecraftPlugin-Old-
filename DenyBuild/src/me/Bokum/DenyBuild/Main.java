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
		getLogger().info("블럭 보호 플러그인이 로드 되었습니다.");
	}
	
	public void onDisable()
	{
		getLogger().info("블럭 보호 플러그인이 언로드 되었습니다.");
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
