package QuestTool.Event;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.bukkit.entity.Player;
import org.bukkit.event.player.PlayerChatEvent;

import QuestTool.GUI.QuestGUI;

public class Chat {
	public static List<String> addingQuestPlayer = new ArrayList<String>();
	public static HashMap<String, String> addingObjectPlayerQuest = new HashMap<String, String>();
	public static HashMap<String, String> addingObjectPlayerObject = new HashMap<String, String>();
	
	public static boolean ChatRouter(Player p, PlayerChatEvent e){
		if(p.isOp()){
			if(addingQuestPlayer.contains(p.getName())){
				addingQuestPlayer.remove(p.getName());
				QuestGUI questGui = new QuestGUI();
				questGui.openAddQuest(p, e.getMessage());
				return true; //true 반환시 채팅 이벤트 취소
			}
			if(addingObjectPlayerQuest.containsKey(p.getName())){
				
			}
		}
		return false;
	}
}
