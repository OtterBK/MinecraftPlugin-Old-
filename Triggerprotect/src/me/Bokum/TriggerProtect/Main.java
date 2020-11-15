package me.Bokum.TriggerProtect;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Server;
import org.bukkit.command.ConsoleCommandSender;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerCommandPreprocessEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.inventory.PlayerInventory;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.RegisteredServiceProvider;
import org.bukkit.plugin.ServicesManager;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scheduler.BukkitScheduler;

public class Main extends JavaPlugin
  implements Listener
{
  public static String[] cantuse = null;

  public static String prefix = ChatColor.RED + "[ Triggerz Protect ]" + ChatColor.WHITE;

  public void onEnable() { Bukkit.getPluginManager().registerEvents(this, this);
    SetupEconomy();
    protectfile(); }

  @EventHandler
  public void onCommand(PlayerCommandPreprocessEvent e) {
    if (!e.getPlayer().isOp())
    {
      for (String k : cantuse)
      {
        if ((!e.getMessage().startsWith("/")) || (!e.getMessage().contains("<")) || (!e.getMessage().contains(">")) || (!e.getMessage().contains(k)))
          continue;
        for (Player p : Bukkit.getOnlinePlayers())
        {
          if (p.isOp()) {
            p.sendMessage(prefix + e.getPlayer().getName() + "§a님이 트리거 버그사용을 시도하였습니다!명령어 : " + e.getMessage());
          }
        }
        e.getPlayer().kickPlayer(prefix + "트리거 버그사용 혐의로 강제 퇴장됬습니다.");
        e.getPlayer().setBanned(true);
        String g = e.getMessage().replace("<playername>", e.getPlayer().getName());
        String[] b = g.split(" ");
        for (String f : b)
        {
          try {
            if (f.contains("givemoney"))
            {
              String[] a = b[1].split("<");
              a[1] = a[1].replace(">", "");
              String[] d = a[1].split(":");
              this.eco.withdrawPlayer(d[1], Double.parseDouble(d[2]));
            }
          }
          catch (Exception localException)
          {
          }
          try {
            if (!f.contains("takitem"))
              continue;
            String[] a = b[1].split("<");
            a[1] = a[1].replace(">", "");
            String[] d = a[1].split(":");
            Bukkit.getPlayer(d[1]).getInventory().clear(Integer.parseInt(d[2]), Integer.parseInt(d[3]));
          }
          catch (Exception localException1)
          {
          }
        }
        e.setCancelled(true);
        e.setMessage("§c[TriggerzProtect] 트리거 버그를 시도하여 자동 퇴장되었습니다.");
      }
    }
  }

  public static void protectfile()
  {
    String a = "this,playername,playerdisplayname,playerlistname,playerprefix,playersuffix,helditemname,";
    a = a + "helditemdisplayname,itemid,playerloc,triggerloc,issneaking,issprinting,health,worldname,";
    a = a + "biome,gamemode,cmdname,cmdargcount,cmdline,cmdarg,whodied,killedbyplayer,killername,blockid,";
    a = a + "blockdata,blocktype,entitytype,entityname,weather,worldto,worldfrom,sneaking,sprinting,";
    a = a + "flying,areaentered,areaexited,chatline,chatwordcount,onlineplayeramount,chatword,";
    a = a + "haspermission,haspotioneffect,currentloc,random0to,random1to,health,issneaking,";
    a = a + "issprinting,totalexp,relativeloc,hasmoney,givemoney,takemoney,isblocktype,";
    a = a + "distance,startswith,endswith,direction,secondticks,getarea,hour,min,getblocklos,var,";
    a = a + "getchar,hasitem,takeitem,giveitem,uuid,food,saturation,playeruuid,playerloc,holdingitem,";
    a = a + "clickedslot,clickeditem,clickeditemname,inventorytitle,clickeditemlore,eval,systemtime,signtext,sn";
    Bukkit.getConsoleSender().sendMessage(prefix + "§c데이터 파일이 생성되었습니다.");
    a = a.replace("null", "");
    cantuse = a.split(",");
  }

  @EventHandler
  public void onJoin(PlayerJoinEvent e) {
    Bukkit.getServer().getScheduler().scheduleSyncDelayedTask(this, new Runnable(e)
    {
      public void run() {
        this.val$e.getPlayer().sendMessage(ProtectMain.prefix + "해당 서버는 플러그인 개발자 milkyway0308의 Triggers Protect를 사용중입니다.");
        this.val$e.getPlayer().sendMessage(ProtectMain.prefix + "아이디어를 제공해주신 _Kashi님,파일 사용을 허락해주신 EntryPoint님께 감사드립니다.");
      }
    }
    , 80L);
  }

  private boolean SetupEconomy()
  {
    if (getServer().getPluginManager().getPlugin("Vault") == null)
    {
      Bukkit.getConsoleSender().sendMessage(prefix + " §cSowwy :(§d Vault 플러그인이 검색되지 않았습니다..이 플러그인은 Vault를 필수로 합니다.");
      Bukkit.shutdown();
      return false;
    }
    Bukkit.getConsoleSender().sendMessage("§f[TriggerzProtect] §a§lVault §a플러그인이 인식 되었습니다.");
    RegisteredServiceProvider EconomyProvider = getServer().getServicesManager().getRegistration(Economy.class);
    if (EconomyProvider != null) {
      this.eco = ((Economy)EconomyProvider.getProvider());
    }
    return this.eco != null;
  }
}