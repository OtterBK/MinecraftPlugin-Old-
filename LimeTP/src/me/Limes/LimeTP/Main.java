package me.Limes.LimeTP;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.event.Listener;
import org.bukkit.plugin.java.JavaPlugin;

public class Main extends JavaPlugin implements Listener{
	public void onEnable(){
		getServer().getPluginManager().registerEvents(this, this); 
		//�������� �Ͼ�� �̺�Ʈ�� LimeTP�� �������ڽ��ϴ�.
		getLogger().info("LimeTP �÷������� �ε� �߽��ϴ�.");
		//Bukkitâ�� ���� ���
	}
	
	public void onDisable(){
		getLogger().info("LimeTP �÷������� ��ε� �߽��ϴ�.");
		//Bukkitâ�� ���� ���
	}
	//talker(CommandSender) -> ��ɾ �Է��ѻ�� str(String) -> ������ɾ��ΰ�? args[]->��ɾ� �ڿ� �� ���ڵ�
	public boolean onCommand(CommandSender talker, Command command, String str, String args[]){
		//���� �Է��� ��ɾ limetp �϶�
		if(str.equalsIgnoreCase("limetp")){
			Player tpFrom = null; //���� �÷��̾ tp ��ų�� �÷��̾����� ������ �ϳ� ����
			Player tpTo = null; //���� �÷��̾�� tp ��ų�� �÷��̾����� ������ �ϳ� ����
			//args[0] -> Lime
			//args[1] -> Bokum
			//Bokum, Toma, Nice ,Lime
			for(Player p : Bukkit.getOnlinePlayers()){
				if(p.getName().equalsIgnoreCase(args[0])){
					tpFrom = p;
				}
				if(p.getName().equalsIgnoreCase(args[1])){
					tpTo = p;
				}
			}
			//|| �� �Ǵ��̶�� �ǹ�
			if(tpFrom == null || tpTo == null){
				talker.sendMessage("�� �� �Ѹ��� ������ ���������� �ʽ��ϴ�.");
			} else {
				tpFrom.teleport(tpTo);
				talker.sendMessage("�ڷ���Ʈ�� �Ϸ��߽��ϴ�.");
			}
		}
		return false;
	}
}
