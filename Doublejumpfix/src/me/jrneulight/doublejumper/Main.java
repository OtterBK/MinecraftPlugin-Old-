package me.jrneulight.doublejumper;

import org.bukkit.Bukkit;
import org.bukkit.GameMode;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.Server;
import org.bukkit.Sound;
import org.bukkit.World;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.entity.EntityDamageEvent.DamageCause;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.event.player.PlayerToggleFlightEvent;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.util.Vector;

public class Main extends JavaPlugin
  implements Listener
{
  public void onEnable()
  {
    saveDefaultConfig();
    getServer().getPluginManager().registerEvents(this, this);
  }
  @EventHandler(priority=EventPriority.HIGH)
  public void onPlayerJoin(PlayerJoinEvent event) { Player p = event.getPlayer();

    if (getConfig().getString("FasterWalkingSpeed").equalsIgnoreCase("true"))
      p.setWalkSpeed(0.25F); }

  @EventHandler
  public void onEntityDamage(EntityDamageEvent e)
  {
    if (((e.getEntity() instanceof Player)) && (e.getCause() == EntityDamageEvent.DamageCause.FALL))
      e.setCancelled(true);
  }

  @EventHandler
  public void onMove(PlayerMoveEvent event) {
    if ((event.getPlayer().hasPermission("doublejump.use")) && (event.getPlayer().getGameMode() != GameMode.CREATIVE) && (event.getPlayer().getLocation().getBlock().getRelative(BlockFace.DOWN).getType() != Material.AIR))
    {
      event.getPlayer().setAllowFlight(true);
    }
  }

  @EventHandler
  public void onFly(PlayerToggleFlightEvent event) {
    Player player = event.getPlayer();
    if ((player.hasPermission("doublejump.use")) && (player.getGameMode() != GameMode.CREATIVE)) {
      event.setCancelled(true);
      player.setAllowFlight(false);
      player.setFlying(false);
      player.setVelocity(player.getLocation().getDirection().multiply(0.45D).setY(0.5D));

      if (getConfig().getString("TakeoffSound").equalsIgnoreCase("true")) {
        player.getLocation().getWorld().playSound(player.getLocation(), Sound.BAT_TAKEOFF, 1.0F, -5.0F);
      }
      if (getConfig().getString("CloudEffect").equalsIgnoreCase("true"))
        for (Player p : Bukkit.getOnlinePlayers())
          try {
            ParticleEffects.CLOUD.sendToPlayer(p, player.getLocation(), 1.0F, 1.0F, 1.0F, 1.0F, 40);
            player.setExp(0.0F);
          } catch (Exception e) {
            e.printStackTrace();
          }
    }
  }
}