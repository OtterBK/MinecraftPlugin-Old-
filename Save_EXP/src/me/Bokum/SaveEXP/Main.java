package me.Bokum.SaveEXP;

import java.util.HashMap;

import org.bukkit.Bukkit;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.event.player.PlayerRegisterChannelEvent;
import org.bukkit.event.player.PlayerRespawnEvent;
import org.bukkit.plugin.java.JavaPlugin;

public class Main extends JavaPlugin implements Listener{
	
	public static HashMap<String, Integer> levellist = new HashMap<String, Integer>();
	public static HashMap<String, Float> explist = new HashMap<String, Float>();
	
	public void onEnable(){
		getServer().getPluginManager().registerEvents(this, this);
		getLogger().info("레벨 저장 플러그인 로드 완료");
	}
	
	public void onDisable(){
		getLogger().info("레벨 저장 플러그인 언로드 완료");
	}
	
	@EventHandler
	public void onDeath(PlayerDeathEvent e){
		levellist.put(e.getEntity().getName(), e.getEntity().getLevel());
		explist.put(e.getEntity().getName(), e.getEntity().getExp());
		e.setDroppedExp(0);
	}
	
	@EventHandler
	public void onRespawn(final PlayerRespawnEvent e){
		Bukkit.getScheduler().scheduleSyncDelayedTask(this, new Runnable(){
			public void run(){
				e.getPlayer().setLevel(levellist.get(e.getPlayer().getName()));
				e.getPlayer().setExp(explist.get(e.getPlayer().getName()));
			}
		}, 5l);
	}
}
