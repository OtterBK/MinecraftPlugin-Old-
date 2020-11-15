package me.Bokum.Party;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.event.Listener;
import org.bukkit.inventory.Inventory;
import org.bukkit.plugin.java.JavaPlugin;

public class Main extends JavaPlugin implements Listener
{
	public static Inventory partyMenu;
	
	public void onEnable(){
		getServer().getPluginManager().registerEvents(this, this);
		
		partyMenu = Bukkit.createInventory(null, 45, "��c��l��Ƽ �޴�");
		
		getLogger().info("�����������������������");
		getLogger().info("�� ������ ��Ƽ �÷����� �ε� �Ϸ� ��");
		getLogger().info("�����������������������");
	}
	
	public void onDisable(){
		getLogger().info("������������������������");
		getLogger().info("�� ������ ��Ƽ �÷����� ��ε� �Ϸ� ��");
		getLogger().info("������������������������");
	}
	
	public boolean onCommand(CommandSender talker, Command command, String str, String[] args){
		if(talker instanceof Player){
			Player p = (Player) talker;
			if(str.equalsIgnoreCase("��Ƽ")){
				partyCommand(p, args);
				return true;
			}
		}
		return false;
	}
	
	public void partyCommand(Player p, String args[]){
		p.openInventory(partyMenu);
	}
}
