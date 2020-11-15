package me.Bokum.Health;

import java.util.HashMap;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.event.player.PlayerExpChangeEvent;
import org.bukkit.event.player.PlayerRespawnEvent;
import org.bukkit.plugin.java.JavaPlugin;

public class Main extends JavaPlugin implements Listener
{
	public static HashMap<String, Integer> lvlist = new HashMap<String, Integer>();
	public static HashMap<String, Float> explist = new HashMap<String, Float>();
	
	public void onEnable()
	{
		getServer().getPluginManager().registerEvents(this, this);
		getLogger().info("레벨 제한 플러그인이 로드 되었습니다.");
	}
	
	public void onDisable()
	{
		getLogger().info("레벨 제한 플러그인이 언로드 되었습니다.");
	}
	
	@EventHandler(priority = EventPriority.HIGHEST)
	public void onLevelUp(PlayerExpChangeEvent e)
	{
		Player p = e.getPlayer();
		if(p.getLevel() >= 50)
		{
			e.setAmount(0);
			p.sendMessage("더 이상의 경험치 획득은 불가능합니다!");
		}
	}
	
	@EventHandler
	public void onPlayerDeath(PlayerDeathEvent e)
	{
		Player p = e.getEntity();
		lvlist.put(p.getName(), p.getLevel());
		explist.put(p.getName(), p.getExp());
	}
	
	@EventHandler
	public void onPlayerRespawn(PlayerRespawnEvent e)
	{
		final Player p = e.getPlayer();
		Bukkit.getScheduler().scheduleSyncDelayedTask(this, new Runnable()
		{
			public void run()
			{
				p.setLevel(lvlist.get(p.getName()));
				p.sendMessage(""+lvlist.get(p.getName()));
				p.setExp(explist.get(p.getName()));
				p.sendMessage(""+explist.get(p.getName()));
			}
		}, 4L);
	}
}
