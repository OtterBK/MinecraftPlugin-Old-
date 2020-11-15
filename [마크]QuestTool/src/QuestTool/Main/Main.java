package QuestTool.Main;

import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.player.PlayerChangedWorldEvent;
import org.bukkit.event.player.PlayerChatEvent;
import org.bukkit.plugin.java.JavaPlugin;

import QuestTool.Command.QuestCmd;
import QuestTool.Event.Chat;
import QuestTool.Event.InventoryClick;

public class Main extends JavaPlugin implements Listener{
	public static Main instance;
	
	public void onEnable(){
		instance = this;
		getServer().getPluginManager().registerEvents(this, this);
		getLogger().info("EGSky ������Ʈ - QuestTool �÷������� �ε� �Ǿ����ϴ�.");
	}
	
	public void onDisable(){
		getLogger().info("QuestTool �÷������� ��ε� �Ǿ����ϴ�.");
	}
	
	public boolean onCommand(CommandSender talker, Command command, String str, String args[]){
		if(talker instanceof Player){
			Player p = (Player) talker;
			if(str.equalsIgnoreCase("����Ʈ����")){
				QuestCmd questcmd = new QuestCmd();
				questcmd.QuestCmdRouter(p, str, args);
			}
		}
		return false;
	}
	
	@EventHandler
	private void onInventoryClick(InventoryClickEvent e){
		if(!(e.getWhoClicked() instanceof Player)) return;
		InventoryClick ic = new InventoryClick();
		if(ic.InventoryClickRouter(e)) e.setCancelled(true);
	}

	@EventHandler
	private void onChat(PlayerChatEvent e){
		Player p = e.getPlayer();
		if(Chat.ChatRouter(p, e)) e.setCancelled(true);
	}
}
