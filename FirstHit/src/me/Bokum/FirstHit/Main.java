package me.Bokum.FirstHit;

import org.bukkit.event.Listener;
import org.bukkit.plugin.java.JavaPlugin;

public class Main extends JavaPlugin implements Listener
{
	public void onEnable()
	{
		getServer().getPluginManager().registerEvents(this, this);
		getLogger().info("선빵게임 플러그인 로드 완료");
	}
	
	public void onDisable()
	{
		getLogger().info("선빵게임 플러그인 언로드 완료");
	}
}
