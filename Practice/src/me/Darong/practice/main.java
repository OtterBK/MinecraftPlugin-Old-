package me.Darong.practice;

import org.bukkit.event.Listener;
import org.bukkit.plugin.java.JavaPlugin;

public class main extends JavaPlugin implements Listener
{
	public void onEnable()
	{
		getServer().getPluginManager().registerEvents(this, this);
		getLogger().info("연습용 플러그인이 로드 되었습니다.");
	}
	
	public void onDisable()
	{
		getLogger().info("연습용 플러그인이 언로드 되었습니다.");
	}
	
	public 
}
