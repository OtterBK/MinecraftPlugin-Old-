package me.Darong.practice;

import org.bukkit.event.Listener;
import org.bukkit.plugin.java.JavaPlugin;

public class main extends JavaPlugin implements Listener
{
	public void onEnable()
	{
		getServer().getPluginManager().registerEvents(this, this);
		getLogger().info("������ �÷������� �ε� �Ǿ����ϴ�.");
	}
	
	public void onDisable()
	{
		getLogger().info("������ �÷������� ��ε� �Ǿ����ϴ�.");
	}
	
	public 
}
