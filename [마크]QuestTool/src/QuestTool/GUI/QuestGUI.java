package QuestTool.GUI;

import java.io.File;

import org.bukkit.Bukkit;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;

import QuestTool.Event.InventoryClick;
import QuestTool.Itemstack.Items;
import QuestTool.Main.Main;
import QuestTool.Messenger.Messenger;

public class QuestGUI {
	public void openQuestMenu(Player p){
		Inventory inv = Bukkit.createInventory(null, 9, "§c§l퀘스트 메뉴");
		Items items = new Items();
		inv.setItem(0, items.addQuest());
		inv.setItem(4, items.editQuest());
		inv.setItem(8, items.delQuest());
		
		p.openInventory(inv);
	}
	
	public void openAddQuest(Player p, String title){
		File file = new File(Main.instance.getDataFolder()+"/Quests", title+".quest");
		if(file != null && file.exists()){
			p.sendMessage(Messenger.MS+"이미 해당 퀘스트가 존재합니다.");
			return;
		}
		if(file.isDirectory()){
			p.sendMessage(Messenger.MS+"제목에 '/'의 기호는 사용하실 수 없습니다.");
			return;
		}
		try {
			FileConfiguration questConfig = YamlConfiguration.loadConfiguration(file);
			questConfig.save(file);
			p.sendMessage(Messenger.MS+"§a"+title+" §f퀘스트 파일을 생성하였습니다.");
		} catch(Exception e){
			return;
		}
		openEditQuest(p, title);
	}
	
	public void openEditQuest(Player p, String questName){
		InventoryClick.editingQuest.put(p.getName(), questName);
		Inventory inv = Bukkit.createInventory(null, 54, questName);
		Items items = new Items();
		inv.setItem(49, items.addObject());
		p.openInventory(inv);
	}
	
	public void openEditQuestList(Player p){
		Inventory inv = Bukkit.createInventory(null, 9, "§c§l퀘스트 수정");
		Items items = new Items();
	}
	
	public void openDelQuest(Player p){
		Inventory inv = Bukkit.createInventory(null, 9, "§c§l퀘스트 삭제");
		Items items = new Items();
		
	}
	
	public void openObjectMenu(Player p, String questName){
		Inventory inv = Bukkit.createInventory(null, 54, questName);
		InventoryClick.editingQuest.put(p.getName(), questName);
		Items items = new Items();
		
		inv.setItem(0, items.object_tellNPC());
		inv.setItem(1, items.object_tellPlayer());
		inv.setItem(2, items.object_giveItemToNPC());
		inv.setItem(3, items.object_hunt());
		inv.setItem(4, items.object_talk());
		inv.setItem(5, items.object_reward());
		inv.setItem(6, items.object_move());
		inv.setItem(7, items.object_collect());
		inv.setItem(8, items.object_createBlock());
		inv.setItem(9, items.object_createSound());
		inv.setItem(10, items.object_whisper());
		inv.setItem(11, items.object_broadcast());
		
		p.openInventory(inv);
	}
}
