package me.Bokum.Region;

import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.plugin.java.JavaPlugin;

public class Main extends JavaPlugin implements Listener
{
	public static String MS = "��f[ ��aBJ��� ��f] ";
	
	public void onEnable()
	{
		getServer().getPluginManager().registerEvents(this, this);
		getLogger().info("���� �÷������� �ε� �Ǿ����ϴ�.");
	}
	
	public void onDisable()
	{
		getLogger().info("���� �÷������� ��ε� �Ǿ����ϴ�.");
	}
	
	@EventHandler
	public void onBlockPlace(BlockPlaceEvent e)
	{
		Player p = e.getPlayer();
		if(e.getBlock().getTypeId() == 220)
		{
			Block loc = e.getBlock();
			for(int i = -60; i < 60; i++)
			{
				loc.getRelative(60, 0, i).setType(Material.BEDROCK);
				loc.getRelative(-60, 0, i).setType(Material.BEDROCK);
			}
			for(int i = -60; i < 60; i++)
			{
				loc.getRelative(i, 0, 60).setType(Material.BEDROCK);
				loc.getRelative(i, 0, -60).setType(Material.BEDROCK);
			}
			p.sendMessage(MS+"������ �����Ǿ����ϴ�.");
		}
	}
}
