package me.Bokum.BOT;

import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.event.Listener;
import org.bukkit.plugin.java.JavaPlugin;

public class Main extends JavaPlugin implements Listener
{
	public void onEnable()
	{
		getServer().getPluginManager().registerEvents(this, this);
		getLogger().info("종족 전쟁 플러그인이 로드 되었습니다.");
	}
	
	public void onDisable()
	{
		getLogger().info("종적 전쟁 플러그인이 언로드 되었습니다.");
	}
	
	public boolean onCommand(CommandSender talker, Command command, String str, String[] args)
	{
		if(talker instanceof Player)
		{
			Player p = (Player) talker;
			if(str.equalsIgnoreCase("bot"))
			{

			}
		}
	}
}
