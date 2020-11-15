package QuestTool.Command;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import QuestTool.GUI.QuestGUI;
import QuestTool.Messenger.Messenger;

public class QuestCmd {
	public void QuestCmdRouter(Player p, String cmd, String args[]){
		if(cmd.equalsIgnoreCase("퀘스트제작")){
			p.sendMessage(Messenger.MS+"원하시는 기능을 클릭해주세요.");
			QuestGUI questGui = new QuestGUI();
			questGui.openQuestMenu(p);
			
		}
	}
}
