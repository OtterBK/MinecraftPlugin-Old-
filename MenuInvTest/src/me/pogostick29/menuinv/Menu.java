package me.pogostick29.menuinv;
 
import java.util.Arrays;
 
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.DyeColor;
import org.bukkit.GameMode;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.material.Wool;
import org.bukkit.plugin.Plugin;
 
public class Menu implements Listener {
 
        private Inventory inv;
        private ItemStack c, s, a;
       
        public Menu(Plugin p) {
                inv = Bukkit.getServer().createInventory(null, 9, "Gamemode Chooser");
               
                c = createItem(DyeColor.GREEN, ChatColor.GREEN + "Creative");
                s = createItem(DyeColor.YELLOW, ChatColor.YELLOW + "Survival");
                a = createItem(DyeColor.RED, ChatColor.RED + "Adventure");
               
                inv.setItem(2, c);
                inv.setItem(4, s);
                inv.setItem(6, a);
               
                Bukkit.getServer().getPluginManager().registerEvents(this, p);
        }
       
        private ItemStack createItem(DyeColor dc, String name) {
                ItemStack i = new Wool(dc).toItemStack(1);
                ItemMeta im = i.getItemMeta();
                im.setDisplayName(name);
                im.setLore(Arrays.asList("Set your gamemode", "to " + name.toLowerCase() + " mode"));
                i.setItemMeta(im);
                return i;
        }
       
        public void show(Player p) {
                p.openInventory(inv);
        }
       
        @EventHandler
        public void onInventoryClick(InventoryClickEvent e) {
                if (!e.getInventory().getName().equalsIgnoreCase(inv.getName())) return;
                if (e.getCurrentItem().getItemMeta() == null) return;
                if (e.getCurrentItem().getItemMeta().getDisplayName().contains("Creative")) {
                        e.setCancelled(true);
                        e.getWhoClicked().setGameMode(GameMode.CREATIVE);
                        e.getWhoClicked().closeInventory();
                }
                if (e.getCurrentItem().getItemMeta().getDisplayName().contains("Survival")) {
                        e.setCancelled(true);
                        e.getWhoClicked().setGameMode(GameMode.SURVIVAL);
                        e.getWhoClicked().closeInventory();
                }
                if (e.getCurrentItem().getItemMeta().getDisplayName().contains("Adventure")) {
                        e.setCancelled(true);
                        e.getWhoClicked().setGameMode(GameMode.ADVENTURE);
                        e.getWhoClicked().closeInventory();
                }
        }
}