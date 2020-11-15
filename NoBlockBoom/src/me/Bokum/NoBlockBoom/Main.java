package me.Bokum.NoBlockBoom;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockIgniteEvent;
import org.bukkit.event.entity.EntityExplodeEvent;
import org.bukkit.plugin.java.JavaPlugin;

public class Main extends JavaPlugin implements Listener{
	
	public void onEnable(){
		getServer().getPluginManager().registerEvents(this, this);
		getLogger().info("폭발 블럭 파괴 방지 플러그인 로드 완료");
	}
	
	public void onDisable(){
		getLogger().info("폭발 블럭 파괴 방지 플러그인 언로드 완료");
	}
	
	@EventHandler
	public void onExplode1(EntityExplodeEvent e){
		e.setCancelled(true);
	}
	
	@EventHandler
	public void onExplode2(BlockIgniteEvent e){
		e.setCancelled(true);
	}

}
