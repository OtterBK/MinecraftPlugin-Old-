package me.Bokum.ZombieW;

import java.util.HashMap;

import org.bukkit.Location;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.entity.Zombie;
import org.bukkit.event.Listener;
import org.bukkit.plugin.java.JavaPlugin;

import de.ntcomputer.minecraft.controllablemobs.api.ControllableMob;
import de.ntcomputer.minecraft.controllablemobs.api.ControllableMobs;


public class Main extends JavaPlugin implements Listener
{
	private HashMap<Player,ControllableMob<Zombie>> zombieMap;
	
	public void onEnable()
	{
		this.zombieMap = new HashMap<Player,ControllableMob<Zombie>>();
		getServer().getPluginManager().registerEvents(this, this);
		getLogger().info("좀비 월드가 로드 되었습니다.");
	}
	
	public void onDisable()
	{
		getLogger().info("좀비 월드가 언로드 되었습니다.");
	}
	
	public boolean onCommand(CommandSender talker, Command command, String str, String args[])
	{
		if(talker instanceof Player)
		{
			Player p = (Player) talker;
			if(str.equalsIgnoreCase("zw"))
			{
				if(args.length >= 1)
				{
					if(args[0].equalsIgnoreCase("test"))
					{
						Test(p);
					}
				}
			}
		}
		return true;
	}
	
	private void spawnZombie(Player owner, Location spawnLocation) {
		Zombie zombie = spawnLocation.getWorld().spawn(spawnLocation, Zombie.class);
		ControllableMob<Zombie> controlledZombie = ControllableMobs.assign(zombie, true);
	}
	
	public void Test(Player p)
	{
		Zombie z = p.getWorld().spawn(p.getLocation().getBlock().getRelative(10, 1, 10).getLocation(), Zombie.class);
		ControllableMob<Zombie> controlledZombie = ControllableMobs.assign(z);
		controlledZombie.getActions().moveToAttacking(p.getLocation());
		z = p.getWorld().spawn(p.getLocation().getBlock().getRelative(10, 1, -10).getLocation(), Zombie.class);
		controlledZombie = ControllableMobs.assign(z);
		controlledZombie.getActions().moveToAttacking(p.getLocation());
		z = p.getWorld().spawn(p.getLocation().getBlock().getRelative(-10, 1, -10).getLocation(), Zombie.class);
		controlledZombie = ControllableMobs.assign(z);
		controlledZombie.getActions().moveToAttacking(p.getLocation());
		z = p.getWorld().spawn(p.getLocation().getBlock().getRelative(-10, 1, 10).getLocation(), Zombie.class);
		controlledZombie = ControllableMobs.assign(z);
		controlledZombie.getActions().moveToAttacking(p.getLocation());
		p.sendMessage("실행");
	}
}
