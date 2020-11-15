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
			limit = getConfig().getInt("��������");
			if(limit != -1)
			{
				activate = true;
				getLogger().info("���ѷ���: "+limit);
			}
			else
			{
				getLogger().info("���ѷ���: ��Ȱ��ȭ");
			}
		}
		catch(java.lang.IllegalArgumentException e)
		{	
			getConfig().set("��������", -1);
			saveConfig();
			getLogger().info("Config������ �����մϴ�.");
		}
		getServer().getPluginManager().registerEvents(this, this);
		getLogger().info("���� ���� �÷����� �ε� �Ϸ� - ���� Bokum");
	}
	
	public void onDisable()
	{
		saveConfig();
		getLogger().info("���� ���� �÷����� ��ε� �Ϸ�");
	}
	
	@EventHandler
	public void onLevelup(PlayerLevelChangeEvent e)
	{
		if(e.getNewLevel() > limit && activate)
		{
			e.getPlayer().setLevel(limit);
			e.getPlayer().sendMessage(ChatColor.RESET+"[ "+ChatColor.GREEN+"LL"+ChatColor.RESET+" ]"+ChatColor.RED+" �ִ� ������ "+limit+"���� �Դϴ�.");
		}
	}
}
