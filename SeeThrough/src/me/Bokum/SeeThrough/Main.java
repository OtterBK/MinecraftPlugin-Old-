package me.Bokum.SeeThrough;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.event.Listener;
import org.bukkit.plugin.java.JavaPlugin;

public class Main extends JavaPlugin implements Listener
{
	public static String MS = "��f[��aBJ����f] ";
	
	public void onEnable()
	{
		getServer().getPluginManager().registerEvents(this, this);
		getLogger().info("���� �÷����� �ε� �Ϸ�");
	}
	
	public void onDisable()
	{
		getLogger().info("���� �÷����� ��ε� �Ϸ�");
	}
	
	public boolean onCommand(CommandSender talker, Command command, String string, String args[])
	{
		if(talker instanceof Player)
		{
			Player p = (Player) talker;
			if(string.equalsIgnoreCase("st") && p.hasPermission("st.use"))
			{
				SeeThrough(p ,args);
				return true;
			}
		}
		return false;
	}
	
	public void SeeThrough(final Player p, String[] args)
	{
		if(args.length <= 0)
		{
			p.sendMessage(MS+"/st <����>");
			return;
		}
		final int range = Integer.valueOf(args[0]);
		final Block base = p.getTargetBlock(null, 15);
		if(base.getLocation().distance(p.getLocation()) > 10)
		{
			p.sendMessage(MS+"10ĭ �̳��� ���� ���� ������ּ���.");
			return;
		}
		p.sendMessage(MS+"���ø� ����մϴ�.");
		for(int x = (range*-1); x <= range; x++)
		{
			for(int y = (range*-1); y <= range; y++)
			{
				for(int z = (range*-1); z <= range; z++)
				{
					p.sendBlockChange(base.getRelative(x, y, z).getLocation(), Material.AIR, (byte) 0);
				}
			}
		}
		Bukkit.getScheduler().scheduleSyncDelayedTask(this, new Runnable()
		{
			public void run()
			{
				for(int x = (range*-1); x <= range; x++)
				{
					for(int y = (range*-1); y <= range; y++)
					{
						for(int z = (range*-1); z <= range; z++)
						{
							p.sendBlockChange(base.getRelative(x, y, z).getLocation(), base.getRelative(x, y, z).getType(), (byte) 0);
						}
					}
				}
				p.sendMessage(MS+"������ ���ӽð��� �������ϴ�.");
			}
		}, 200L);
	}
}
