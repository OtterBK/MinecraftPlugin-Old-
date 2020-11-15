package QuestTool.Event;

import java.io.File;
import java.util.HashMap;

import org.bukkit.Sound;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.Inventory;

import QuestTool.GUI.QuestGUI;
import QuestTool.Main.Main;
import QuestTool.Messenger.Messenger;
import QuestTool.Util.QuestUtil;

public class InventoryClick {
	public static HashMap<String, String> editingQuest = new HashMap<String, String>();
	public static HashMap<String, String> addObject = new HashMap<String, String>();
	
	public boolean InventoryClickRouter(InventoryClickEvent e){
		Player p = (Player) e.getWhoClicked();
		Inventory inv = e.getInventory();
		if(inv.getItem(e.getSlot()) == null) {
			p.playSound(p.getLocation(), Sound.CHICKEN_EGG_POP, 1.5f, 1.5f);
			return true;
		}
		if(p.isOp()){
			if(inv.getTitle().contains("����Ʈ �޴�")) {
				clickQuestMenu(p, e.getSlot()); return true;
			}
			if(editingQuest.containsKey(p.getName())){
				clickEditQuestMenu(p, e.getSlot()); return true;
			}
		}
		return false;
	}
	
	private void clickQuestMenu(Player p, int slot){
		p.playSound(p.getLocation(), Sound.CHICKEN_EGG_POP, 2.0f, 2.0f);
		switch(slot){
			case 0:{
				Chat.addingQuestPlayer.add(p.getName());
				p.closeInventory();
				p.sendMessage(Messenger.MS+"����Ʈ �̸��� ä��â�� �Է����ּ���.");
				break;
			}
			case 4:{
				QuestGUI questGui = new QuestGUI();
				questGui.openEditQuestList(p);
				break;
			}
			case 8:{
				QuestGUI questGui = new QuestGUI();
				questGui.openDelQuest(p);
				break;
			}
		default: return;
		}
	}
	
	private void clickEditQuestMenu(Player p, int slot){
		switch(slot){
			case 49:{
				if(!editingQuest.containsKey(p.getName())){
					p.sendMessage(Messenger.MS+"[����] editingQuest �콬�ʿ� ���� ����� ���� �Ǿ� ���� ����");
					return;
				}
				QuestGUI questGui = new QuestGUI();
				questGui.openObjectMenu(p, editingQuest.get(p.getName()));
			}
		}
	}
	
	private void clickAddObjectMenu(Player p, int slot){
		if(!editingQuest.containsKey(p.getName())){
			p.sendMessage(Messenger.MS+"[����] addObject �콬�ʿ� ���� ����� ���� �Ǿ� ���� ����");
			return;
		}
		String title = addObject.get(p.getName());
		File file = new File(Main.instance.getDataFolder()+"/Quests", title+".quest");
		FileConfiguration fileConfig = YamlConfiguration.loadConfiguration(file);
		if(!file.exists()){
			p.sendMessage(Messenger.MS+"[����] �ش� ����Ʈ ������ �������� �ʽ��ϴ�.");
			return;
		}
		int nowNumber = QuestUtil.getAddObjectNumber(title);
		switch(slot){
			case 0:{
				
			}
		}
	}
}
