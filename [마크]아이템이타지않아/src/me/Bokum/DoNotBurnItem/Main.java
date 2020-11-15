package me.Bokum.DoNotBurnItem;

import org.bukkit.entity.EntityType;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.entity.EntityDamageEvent.DamageCause;
import org.bukkit.plugin.java.JavaPlugin;

public class Main extends JavaPlugin implements Listener{
	
	public void onEnable(){
		getServer().getPluginManager().registerEvents(this, this);
		getLogger().info("아이템안탈거야");
	}
	
	public void onDisable(){
		getLogger().info("ㅂㅇㅂㅇ");
	}
	
	@EventHandler
	public void oItemBurned(EntityDamageEvent e){
		EntityDamageEvent.DamageCause cause = e.getCause();
		if(e.getEntity().getType() == EntityType.DROPPED_ITEM){
			if(cause == DamageCause.LAVA || cause == DamageCause.FIRE || cause == DamageCause.FIRE_TICK) {
				e.setCancelled(true);
			}
		}
	}
}
