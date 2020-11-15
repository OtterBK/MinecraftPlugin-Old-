package me.Bokum.Luby;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.event.Listener;
import org.bukkit.plugin.java.JavaPlugin;

public class Skill extends JavaPlugin implements Listener
{
	
	public void SeeThrough(final Player p, String[] args)
	{
		if(args.length <= 0)
		{
			p.sendMessage(Main.MS+"/st <범위>");
			return;
		}
		final int range = Integer.valueOf(args[0]);
		final Block base = p.getTargetBlock(null, 15);
		if(base.getLocation().distance(p.getLocation()) > 10)
		{
			p.sendMessage(Main.MS+"10칸 이내의 블럭을 향해 사용해주세요.");
			return;
		}
		p.sendMessage(Main.MS+"투시를 사용합니다.");
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
				p.sendMessage(Main.MS+"투시의 지속시간이 끝났습니다.");
			}
		}, 200L);
	}
}
