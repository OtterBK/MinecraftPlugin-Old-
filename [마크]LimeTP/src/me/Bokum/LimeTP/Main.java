package me.Bokum.LimeTP;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.event.Listener;
import org.bukkit.plugin.java.JavaPlugin;

public class Main extends JavaPlugin implements Listener{
	
	public void onEnable(){
		getServer().getPluginManager().registerEvents(this, this); //�÷��̾��� ��� �̺�Ʈ�� �����´�
		getLogger().info("LimeTp �÷������� �ε� �Ǿ����ϴ�");
	}
	
	public void onDisable(){
		getLogger().info("LimeTp �÷������� ��ε� �Ǿ����ϴ�.");
	}
	
	public boolean onCommand(CommandSender talker, Command command, String str, String args[]){
		if(str.equalsIgnoreCase("limetp")){ //���࿡ � ����� ��ɾ �ƴµ� �� ��ɾ limetp�϶�
			Player tpFrom = null; //Player �����͸� ���� �� �ִ� ���ڸ� �ϳ� ����� �� ������ �̸��� tpFrom �̶��
			//�̸� ���ڴ�.
			Player tpTo = null; //Player �����͸� ���� �� �ִ� ���ڸ� �ϳ� ����� �� ������ �̸���  tpTo��� �̸� ���ڴ�.
			
			for(Player p : Bukkit.getOnlinePlayers()){
				if(p.getName().equalsIgnoreCase(args[0])){ //���࿡ args[0]
					tpFrom = p; //���� TP ��ų�� ã�´�.
				}
				if(p.getName().equalsIgnoreCase(args[1])){
					tpTo = p;
				}
			}
			tpFrom.teleport(tpTo);
			talker.sendMessage("�ڷ���Ʈ�� �����߽��ϴ�.");
		}
		return false;
	}
}
