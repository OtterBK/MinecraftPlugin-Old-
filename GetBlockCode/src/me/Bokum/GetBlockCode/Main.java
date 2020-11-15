package me.Bokum.GetBlockCode;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.plugin.java.JavaPlugin;

public class Main extends JavaPlugin implements Listener{
	public void onEnable(){
		getServer().getPluginManager().registerEvents(this, this);
		getLogger().info("�ε�");
	}
	
	public void onDisable(){
		getLogger().info("��ε�");
	}
	
	@EventHandler
	public void onBlockBreak(BlockBreakEvent e){
		e.getPlayer().sendMessage(e.getBlock().getTypeId()+" : "+e.getBlock().getData());
	}
}
