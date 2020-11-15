package me.Bokum.Fixer1;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryOpenEvent;
import org.bukkit.plugin.java.JavaPlugin;

public class Main extends JavaPlugin implements Listener{
	public static List<String> openlist = new ArrayList<String>();
	
	public void onEnable(){
		getServer().getPluginManager().registerEvents(this, this);
	}
	
	public void onDisable(){
	}
	
	@EventHandler
	public void onClickInventory(InventoryOpenEvent e){
		final Player p = (Player) e.getPlayer();
		if(openlist.contains(p.getName())){
			p.sendMessage("§c잠시 후 열어주세요.");
			e.setCancelled(true);
		}else{
			openlist.add(p.getName());
			Bukkit.getScheduler().scheduleSyncDelayedTask(this, new Runnable(){
				public void run(){
					openlist.remove(p.getName());
				}
			}, 40);
		}
	}
}
