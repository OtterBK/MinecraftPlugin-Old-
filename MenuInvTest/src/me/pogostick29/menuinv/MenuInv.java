package me.pogostick29.menuinv;
 
import org.bukkit.Bukkit;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.plugin.java.JavaPlugin;
 
public class MenuInv extends JavaPlugin implements Listener {
 
        private Menu menu;
       
        public void onEnable() {
                menu = new Menu(this);
                Bukkit.getServer().getPluginManager().registerEvents(this, this);
        }
       
        @EventHandler
        public void onPlayerInteract(PlayerInteractEvent e) {
                if (!(e.getAction() == Action.RIGHT_CLICK_BLOCK)) return;
                menu.show(e.getPlayer());
        }
}