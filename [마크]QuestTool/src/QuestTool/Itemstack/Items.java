package QuestTool.Itemstack;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

public class Items {
	public ItemStack addQuest(){
		ItemStack item = new ItemStack(Material.ENCHANTED_BOOK, 1);
		ItemMeta meta = item.getItemMeta();
		meta.setDisplayName("§f[ §e§l퀘스트 추가 §f]");
		List<String> lorelist = new ArrayList<String>();
		lorelist.add("§f새로운 퀘스트를 추가합니다.");
		meta.setLore(lorelist);
		item.setItemMeta(meta);
		
		return item;
	}
	
	public ItemStack editQuest(){
		ItemStack item = new ItemStack(Material.ENCHANTED_BOOK, 1);
		ItemMeta meta = item.getItemMeta();
		meta.setDisplayName("§f[ §e§l퀘스트 편집 §f]");
		List<String> lorelist = new ArrayList<String>();
		lorelist.add("§f기존 퀘스트를 수정합니다.");
		meta.setLore(lorelist);
		item.setItemMeta(meta);
		
		return item;
	}
	
	public ItemStack delQuest(){
		ItemStack item = new ItemStack(Material.ENCHANTED_BOOK, 1);
		ItemMeta meta = item.getItemMeta();
		meta.setDisplayName("§f[ §e§l퀘스트 삭제 §f]");
		List<String> lorelist = new ArrayList<String>();
		lorelist.add("§f만들어둔 퀘스트를 삭제합니다.(Quest_BackUp 폴더에서 복구 가능)");
		meta.setLore(lorelist);
		item.setItemMeta(meta);
		
		return item;
	}
	
	public ItemStack Quest(){
		ItemStack item = new ItemStack(Material.ENCHANTED_BOOK, 1);
		ItemMeta meta = item.getItemMeta();
		meta.setDisplayName("§f[ §e§l퀘스트 삭제 §f]");
		List<String> lorelist = new ArrayList<String>();
		lorelist.add("§f만들어둔 퀘스트를 삭제합니다.(Quest_BackUp 폴더에서 복구 가능)");
		meta.setLore(lorelist);
		item.setItemMeta(meta);
		
		return item;
	}
	
	public ItemStack addObject(){
		ItemStack item = new ItemStack(Material.WOOL, 1, (byte) 12);
		ItemMeta meta = item.getItemMeta();
		meta.setDisplayName("§f[ §a§l오브젝트 추가 §f]");
		List<String> lorelist = new ArrayList<String>();
		lorelist.add("§f퀘스트에 오브젝트를 추가합니다.");
		meta.setLore(lorelist);
		item.setItemMeta(meta);
		
		return item;
	}
	
	public ItemStack object_tellNPC(){
		ItemStack item = new ItemStack(Material.SIGN, 1, (byte) 12);
		ItemMeta meta = item.getItemMeta();
		meta.setDisplayName("§f[ §f§l대사§f]");
		List<String> lorelist = new ArrayList<String>();
		lorelist.add("§7대화창을 띄우고, 작성된");
		lorelist.add("&7스크립트를 유저에게 띄웁니다.");
		lorelist.add("&7(화자 : NPC)");
		meta.setLore(lorelist);
		item.setItemMeta(meta);
		
		return item;
	}
	
	public ItemStack object_tellPlayer(){
		ItemStack item = new ItemStack(Material.SIGN, 1, (byte) 12);
		ItemMeta meta = item.getItemMeta();
		meta.setDisplayName("§f[ §f§l독백 §f]");
		List<String> lorelist = new ArrayList<String>();
		lorelist.add("§7대화창을 띄우고, 작성된");
		lorelist.add("&7스크립트를 유저에게 띄웁니다.");
		lorelist.add("&7(화자 : 유저)");
		meta.setLore(lorelist);
		item.setItemMeta(meta);
		
		return item;
	}
	
	public ItemStack object_giveItemToNPC(){
		ItemStack item = new ItemStack(Material.SIGN, 1, (byte) 12);
		ItemMeta meta = item.getItemMeta();
		meta.setDisplayName("§f[ §f§l전달 §f]");
		List<String> lorelist = new ArrayList<String>();
		lorelist.add("§7플레이어가 특정 아이템을");
		lorelist.add("&7NPC에게 줘야하는 퀘스트를 줍니다.");
		meta.setLore(lorelist);
		item.setItemMeta(meta);
		
		return item;
	}
	
	public ItemStack object_hunt(){
		ItemStack item = new ItemStack(Material.SIGN, 1, (byte) 12);
		ItemMeta meta = item.getItemMeta();
		meta.setDisplayName("§f[ §f§l사냥 §f]");
		List<String> lorelist = new ArrayList<String>();
		lorelist.add("§7플레이어에게 특정 몬스터를");
		lorelist.add("&7사냥하는 퀘스트를 줍니다.");
		meta.setLore(lorelist);
		item.setItemMeta(meta);
		
		return item;
	}
	
	public ItemStack object_talk(){
		ItemStack item = new ItemStack(Material.SIGN, 1, (byte) 12);
		ItemMeta meta = item.getItemMeta();
		meta.setDisplayName("§f[ §f§l대화 §f]");
		List<String> lorelist = new ArrayList<String>();
		lorelist.add("§7플레이어에게 특정 NPC에게");
		lorelist.add("&7말을 거는 퀘스트를 줍니다.");
		meta.setLore(lorelist);
		item.setItemMeta(meta);
		
		return item;
	}
	
	public ItemStack object_reward(){
		ItemStack item = new ItemStack(Material.SIGN, 1, (byte) 12);
		ItemMeta meta = item.getItemMeta();
		meta.setDisplayName("§f[ §f§l보상 §f]");
		List<String> lorelist = new ArrayList<String>();
		lorelist.add("§7플레이어에게 보상을 줍니다.");
		meta.setLore(lorelist);
		item.setItemMeta(meta);
		
		return item;
	}
	
	public ItemStack object_move(){
		ItemStack item = new ItemStack(Material.SIGN, 1, (byte) 12);
		ItemMeta meta = item.getItemMeta();
		meta.setDisplayName("§f[ §f§l이동 §f]");
		List<String> lorelist = new ArrayList<String>();
		lorelist.add("§7플레이어를 특정 위치로");
		lorelist.add("&7이동 시킵니다.");
		meta.setLore(lorelist);
		item.setItemMeta(meta);
		
		return item;
	}
	
	public ItemStack object_collect(){
		ItemStack item = new ItemStack(Material.SIGN, 1, (byte) 12);
		ItemMeta meta = item.getItemMeta();
		meta.setDisplayName("§f[ §f§l이동 §f]");
		List<String> lorelist = new ArrayList<String>();
		lorelist.add("§7플레이어를 특정 블록을");
		lorelist.add("&7채취하는 퀘스트를 줍니다.");
		meta.setLore(lorelist);
		item.setItemMeta(meta);
		
		return item;
	}
	
	public ItemStack object_createBlock(){
		ItemStack item = new ItemStack(Material.SIGN, 1, (byte) 12);
		ItemMeta meta = item.getItemMeta();
		meta.setDisplayName("§f[ §f§l블록 §f]");
		List<String> lorelist = new ArrayList<String>();
		lorelist.add("§7특정 위치에 정해진");
		lorelist.add("&7블록을 생성합니다.");
		meta.setLore(lorelist);
		item.setItemMeta(meta);
		
		return item;
	}
	
	public ItemStack object_createSound(){
		ItemStack item = new ItemStack(Material.SIGN, 1, (byte) 12);
		ItemMeta meta = item.getItemMeta();
		meta.setDisplayName("§f[ §f§l소리 §f]");
		List<String> lorelist = new ArrayList<String>();
		lorelist.add("§7특정 위치에 소리가");
		lorelist.add("&7나게합니다.");
		meta.setLore(lorelist);
		item.setItemMeta(meta);
		
		return item;
	}
	
	public ItemStack object_whisper(){
		ItemStack item = new ItemStack(Material.SIGN, 1, (byte) 12);
		ItemMeta meta = item.getItemMeta();
		meta.setDisplayName("§f[ §f§l귓말 §f]");
		List<String> lorelist = new ArrayList<String>();
		lorelist.add("§7플레이어의 채팅창에");
		lorelist.add("&7메세지가 나타납니다.");
		meta.setLore(lorelist);
		item.setItemMeta(meta);
		
		return item;
	}
	
	public ItemStack object_broadcast(){
		ItemStack item = new ItemStack(Material.SIGN, 1, (byte) 12);
		ItemMeta meta = item.getItemMeta();
		meta.setDisplayName("§f[ §f§l전체 §f]");
		List<String> lorelist = new ArrayList<String>();
		lorelist.add("§7서버 전체에");
		lorelist.add("&7메세지가 나타납니다.");
		meta.setLore(lorelist);
		item.setItemMeta(meta);
		
		return item;
	}
}

