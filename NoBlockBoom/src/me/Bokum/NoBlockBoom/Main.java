package me.Bokum.NoBlockBoom;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockIgniteEvent;
import org.bukkit.event.entity.EntityExplodeEvent;
import org.bukkit.plugin.java.JavaPlugin;

public class Main extends JavaPlugin implements Listener{
	
	public void onEnable(){
		getServer().getPluginManager().registerEvents(this, this);
		getLogger().info("���� �� �ı� ���� �÷����� �ε� �Ϸ�");
	}
	
	public void onDisable(){
		getLogger().info("���� �� �ı� ���� �÷����� ��ε� �Ϸ�");
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
