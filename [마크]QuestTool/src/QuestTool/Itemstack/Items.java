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
		meta.setDisplayName("��f[ ��e��l����Ʈ �߰� ��f]");
		List<String> lorelist = new ArrayList<String>();
		lorelist.add("��f���ο� ����Ʈ�� �߰��մϴ�.");
		meta.setLore(lorelist);
		item.setItemMeta(meta);
		
		return item;
	}
	
	public ItemStack editQuest(){
		ItemStack item = new ItemStack(Material.ENCHANTED_BOOK, 1);
		ItemMeta meta = item.getItemMeta();
		meta.setDisplayName("��f[ ��e��l����Ʈ ���� ��f]");
		List<String> lorelist = new ArrayList<String>();
		lorelist.add("��f���� ����Ʈ�� �����մϴ�.");
		meta.setLore(lorelist);
		item.setItemMeta(meta);
		
		return item;
	}
	
	public ItemStack delQuest(){
		ItemStack item = new ItemStack(Material.ENCHANTED_BOOK, 1);
		ItemMeta meta = item.getItemMeta();
		meta.setDisplayName("��f[ ��e��l����Ʈ ���� ��f]");
		List<String> lorelist = new ArrayList<String>();
		lorelist.add("��f������ ����Ʈ�� �����մϴ�.(Quest_BackUp �������� ���� ����)");
		meta.setLore(lorelist);
		item.setItemMeta(meta);
		
		return item;
	}
	
	public ItemStack Quest(){
		ItemStack item = new ItemStack(Material.ENCHANTED_BOOK, 1);
		ItemMeta meta = item.getItemMeta();
		meta.setDisplayName("��f[ ��e��l����Ʈ ���� ��f]");
		List<String> lorelist = new ArrayList<String>();
		lorelist.add("��f������ ����Ʈ�� �����մϴ�.(Quest_BackUp �������� ���� ����)");
		meta.setLore(lorelist);
		item.setItemMeta(meta);
		
		return item;
	}
	
	public ItemStack addObject(){
		ItemStack item = new ItemStack(Material.WOOL, 1, (byte) 12);
		ItemMeta meta = item.getItemMeta();
		meta.setDisplayName("��f[ ��a��l������Ʈ �߰� ��f]");
		List<String> lorelist = new ArrayList<String>();
		lorelist.add("��f����Ʈ�� ������Ʈ�� �߰��մϴ�.");
		meta.setLore(lorelist);
		item.setItemMeta(meta);
		
		return item;
	}
	
	public ItemStack object_tellNPC(){
		ItemStack item = new ItemStack(Material.SIGN, 1, (byte) 12);
		ItemMeta meta = item.getItemMeta();
		meta.setDisplayName("��f[ ��f��l����f]");
		List<String> lorelist = new ArrayList<String>();
		lorelist.add("��7��ȭâ�� ����, �ۼ���");
		lorelist.add("&7��ũ��Ʈ�� �������� ���ϴ�.");
		lorelist.add("&7(ȭ�� : NPC)");
		meta.setLore(lorelist);
		item.setItemMeta(meta);
		
		return item;
	}
	
	public ItemStack object_tellPlayer(){
		ItemStack item = new ItemStack(Material.SIGN, 1, (byte) 12);
		ItemMeta meta = item.getItemMeta();
		meta.setDisplayName("��f[ ��f��l���� ��f]");
		List<String> lorelist = new ArrayList<String>();
		lorelist.add("��7��ȭâ�� ����, �ۼ���");
		lorelist.add("&7��ũ��Ʈ�� �������� ���ϴ�.");
		lorelist.add("&7(ȭ�� : ����)");
		meta.setLore(lorelist);
		item.setItemMeta(meta);
		
		return item;
	}
	
	public ItemStack object_giveItemToNPC(){
		ItemStack item = new ItemStack(Material.SIGN, 1, (byte) 12);
		ItemMeta meta = item.getItemMeta();
		meta.setDisplayName("��f[ ��f��l���� ��f]");
		List<String> lorelist = new ArrayList<String>();
		lorelist.add("��7�÷��̾ Ư�� ��������");
		lorelist.add("&7NPC���� ����ϴ� ����Ʈ�� �ݴϴ�.");
		meta.setLore(lorelist);
		item.setItemMeta(meta);
		
		return item;
	}
	
	public ItemStack object_hunt(){
		ItemStack item = new ItemStack(Material.SIGN, 1, (byte) 12);
		ItemMeta meta = item.getItemMeta();
		meta.setDisplayName("��f[ ��f��l��� ��f]");
		List<String> lorelist = new ArrayList<String>();
		lorelist.add("��7�÷��̾�� Ư�� ���͸�");
		lorelist.add("&7����ϴ� ����Ʈ�� �ݴϴ�.");
		meta.setLore(lorelist);
		item.setItemMeta(meta);
		
		return item;
	}
	
	public ItemStack object_talk(){
		ItemStack item = new ItemStack(Material.SIGN, 1, (byte) 12);
		ItemMeta meta = item.getItemMeta();
		meta.setDisplayName("��f[ ��f��l��ȭ ��f]");
		List<String> lorelist = new ArrayList<String>();
		lorelist.add("��7�÷��̾�� Ư�� NPC����");
		lorelist.add("&7���� �Ŵ� ����Ʈ�� �ݴϴ�.");
		meta.setLore(lorelist);
		item.setItemMeta(meta);
		
		return item;
	}
	
	public ItemStack object_reward(){
		ItemStack item = new ItemStack(Material.SIGN, 1, (byte) 12);
		ItemMeta meta = item.getItemMeta();
		meta.setDisplayName("��f[ ��f��l���� ��f]");
		List<String> lorelist = new ArrayList<String>();
		lorelist.add("��7�÷��̾�� ������ �ݴϴ�.");
		meta.setLore(lorelist);
		item.setItemMeta(meta);
		
		return item;
	}
	
	public ItemStack object_move(){
		ItemStack item = new ItemStack(Material.SIGN, 1, (byte) 12);
		ItemMeta meta = item.getItemMeta();
		meta.setDisplayName("��f[ ��f��l�̵� ��f]");
		List<String> lorelist = new ArrayList<String>();
		lorelist.add("��7�÷��̾ Ư�� ��ġ��");
		lorelist.add("&7�̵� ��ŵ�ϴ�.");
		meta.setLore(lorelist);
		item.setItemMeta(meta);
		
		return item;
	}
	
	public ItemStack object_collect(){
		ItemStack item = new ItemStack(Material.SIGN, 1, (byte) 12);
		ItemMeta meta = item.getItemMeta();
		meta.setDisplayName("��f[ ��f��l�̵� ��f]");
		List<String> lorelist = new ArrayList<String>();
		lorelist.add("��7�÷��̾ Ư�� �����");
		lorelist.add("&7ä���ϴ� ����Ʈ�� �ݴϴ�.");
		meta.setLore(lorelist);
		item.setItemMeta(meta);
		
		return item;
	}
	
	public ItemStack object_createBlock(){
		ItemStack item = new ItemStack(Material.SIGN, 1, (byte) 12);
		ItemMeta meta = item.getItemMeta();
		meta.setDisplayName("��f[ ��f��l��� ��f]");
		List<String> lorelist = new ArrayList<String>();
		lorelist.add("��7Ư�� ��ġ�� ������");
		lorelist.add("&7����� �����մϴ�.");
		meta.setLore(lorelist);
		item.setItemMeta(meta);
		
		return item;
	}
	
	public ItemStack object_createSound(){
		ItemStack item = new ItemStack(Material.SIGN, 1, (byte) 12);
		ItemMeta meta = item.getItemMeta();
		meta.setDisplayName("��f[ ��f��l�Ҹ� ��f]");
		List<String> lorelist = new ArrayList<String>();
		lorelist.add("��7Ư�� ��ġ�� �Ҹ���");
		lorelist.add("&7�����մϴ�.");
		meta.setLore(lorelist);
		item.setItemMeta(meta);
		
		return item;
	}
	
	public ItemStack object_whisper(){
		ItemStack item = new ItemStack(Material.SIGN, 1, (byte) 12);
		ItemMeta meta = item.getItemMeta();
		meta.setDisplayName("��f[ ��f��l�Ӹ� ��f]");
		List<String> lorelist = new ArrayList<String>();
		lorelist.add("��7�÷��̾��� ä��â��");
		lorelist.add("&7�޼����� ��Ÿ���ϴ�.");
		meta.setLore(lorelist);
		item.setItemMeta(meta);
		
		return item;
	}
	
	public ItemStack object_broadcast(){
		ItemStack item = new ItemStack(Material.SIGN, 1, (byte) 12);
		ItemMeta meta = item.getItemMeta();
		meta.setDisplayName("��f[ ��f��l��ü ��f]");
		List<String> lorelist = new ArrayList<String>();
		lorelist.add("��7���� ��ü��");
		lorelist.add("&7�޼����� ��Ÿ���ϴ�.");
		meta.setLore(lorelist);
		item.setItemMeta(meta);
		
		return item;
	}
}

