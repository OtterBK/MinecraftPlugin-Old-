package me.Bokum.FirstHit;

import org.bukkit.event.Listener;
import org.bukkit.plugin.java.JavaPlugin;

public class Main extends JavaPlugin implements Listener
{
	public void onEnable()
	{
		getServer().getPluginManager().registerEvents(this, this);
		getLogger().info("�������� �÷����� �ε� �Ϸ�");
	}
	
	public void onDisable()
	{
		getLogger().info("�������� �÷����� ��ε� �Ϸ�");
	}
}
