package QuestTool.Command;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import QuestTool.GUI.QuestGUI;
import QuestTool.Messenger.Messenger;

public class QuestCmd {
	public void QuestCmdRouter(Player p, String cmd, String args[]){
		if(cmd.equalsIgnoreCase("����Ʈ����")){
			p.sendMessage(Messenger.MS+"���Ͻô� ����� Ŭ�����ּ���.");
			QuestGUI questGui = new QuestGUI();
			questGui.openQuestMenu(p);
			
		}
	}
}
