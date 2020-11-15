package me.Bokum.ChatKic;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerChatEvent;
import org.bukkit.plugin.java.JavaPlugin;

public class Main extends JavaPlugin implements Listener
{
	List list = new ArrayList();
	
	public void onEnable()
	{
		getConfig().options().copyDefaults(true);
		list.addAll(getConfig().getList("alist"));
		getServer().getPluginManager().registerEvents(this, this);
		getLogger().info("욕설 킥 플러그인 로드 완료! - 제작 Bokum");
	}
	
	public void onDisable()
	{
		saveConfig();
		getLogger().info("욕설 킥 플러그인 언로드 완료! - 제작 Bokum");
	}
	
	@EventHandler
	public void onChat(PlayerChatEvent event)
	{
		String chat = event.getMessage();
		Player player = event.getPlayer();
		for(int i = 0; i < list.size(); i++)
		{
			if(chat.contains((String) list.get(i)))
			{
				event.setCancelled(true);
				player.kickPlayer("금지어를 치셨습니다.");
			}
		}
	}
}
