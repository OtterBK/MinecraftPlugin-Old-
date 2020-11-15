package me.Bokum.Limitexp;

import org.bukkit.ChatColor;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerExpChangeEvent;
import org.bukkit.event.player.PlayerLevelChangeEvent;
import org.bukkit.plugin.java.JavaPlugin;

public class Main extends JavaPlugin implements Listener
{
	public static int limit = 0;
	public static boolean activate = false;
	
	public void onEnable()
	{
		getConfig().options().copyDefaults(true);
		saveConfig();
		try
		{
			limit = getConfig().getInt("레벨제한");
			if(limit != -1)
			{
				activate = true;
				getLogger().info("제한레벨: "+limit);
			}
			else
			{
				getLogger().info("제한레벨: 비활성화");
			}
		}
		catch(java.lang.IllegalArgumentException e)
		{	
			getConfig().set("레벨제한", -1);
			saveConfig();
			getLogger().info("Config파일을 생성합니다.");
		}
		getServer().getPluginManager().registerEvents(this, this);
		getLogger().info("레벨 제한 플러그인 로드 완료 - 제작 Bokum");
	}
	
	public void onDisable()
	{
		saveConfig();
		getLogger().info("레벨 제한 플러그인 언로드 완료");
	}
	
	@EventHandler
	public void onLevelup(PlayerLevelChangeEvent e)
	{
		if(e.getNewLevel() > limit && activate)
		{
			e.getPlayer().setLevel(limit);
			e.getPlayer().sendMessage(ChatColor.RESET+"[ "+ChatColor.GREEN+"LL"+ChatColor.RESET+" ]"+ChatColor.RED+" 최대 레벨은 "+limit+"까지 입니다.");
		}
	}
}
