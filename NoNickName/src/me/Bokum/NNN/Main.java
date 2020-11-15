package me.Bokum.NNN;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerRespawnEvent;
import org.bukkit.event.player.PlayerToggleFlightEvent;
import org.bukkit.event.player.PlayerToggleSneakEvent;
import org.bukkit.plugin.java.JavaPlugin;

public class Main extends JavaPlugin implements Listener{
	
	public void onEnable(){
		getServer().getPluginManager().registerEvents(this, this);
		getLogger().info("�г��� ���� �÷����� �ε� �Ϸ�");
	}
	
	public void onDisable(){
		getLogger().info("�г��� ���� �÷����� ��ε� �Ϸ�");
	}
	
	@EventHandler
	public void onPlayerJoin(PlayerJoinEvent e){
		e.getPlayer().setSneaking(true);
	}
	
	@EventHandler
	public void onPlayerRespawn(PlayerRespawnEvent e){
		final Player p = e.getPlayer();
		Bukkit.getScheduler().scheduleAsyncDelayedTask(this, new Runnable(){
			public void run(){
				p.setSneaking(true);
			}
		}, 1l);
	}
	
	@EventHandler
	public void onToggleSneak(PlayerToggleSneakEvent e){
		if(e.getPlayer().isSneaking()) e.setCancelled(true);
	}
}
