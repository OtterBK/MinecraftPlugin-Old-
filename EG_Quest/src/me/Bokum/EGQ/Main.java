package me.Bokum.EGQ;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.plugin.java.JavaPlugin;

public class Main extends JavaPlugin implements Listener{
	public static Main instance;
	public static Inventory writeQuestGui;
	public static HashMap<String, String> questName = new HashMap<String, String>();
	public static String MS = "��f[ ��aEG ����Ʈ ��f] ��6";
	
	public void onEnable(){
		instance = this;
		
		writeQuestGui = Bukkit.createInventory(null, 54, "����Ʈ �ۼ�");
		ItemStack item = new ItemStack(Material.SIGN, 1);
		ItemMeta meta = item.getItemMeta();
		meta.setDisplayName("��f��l���");
		List<String> lorelist = new ArrayList<String>();
		lorelist.add("��7��ȭâ�� ����, �ۼ���");
		lorelist.add("��7��ũ��Ʈ�� �������� ���ϴ�.");
		lorelist.add("��7(ȭ�� : NPC)");
		meta.setLore(lorelist);
		writeQuestGui.setItem(0, item);
		
		item = new ItemStack(Material.SIGN, 1);
		meta = item.getItemMeta();
		meta.setDisplayName("��f��l����");
		lorelist.clear();
		lorelist.add("��7��ȭâ�� ����, �ۼ���");
		lorelist.add("��7��ũ��Ʈ�� �������� ���ϴ�.");
		lorelist.add("��7(ȭ�� : ����)");
		meta.setLore(lorelist);
		writeQuestGui.setItem(1, item);
		
		item = new ItemStack(Material.EMERALD, 1);
		meta = item.getItemMeta();
		meta.setDisplayName("��f��l����");
		lorelist.clear();
		lorelist.add("��7�÷��̾ Ư�� ��������");
		lorelist.add("��7NPC���� ����ϴ� ����Ʈ�� �ݴϴ�.");
		meta.setLore(lorelist);
		writeQuestGui.setItem(3, item);
		
		item = new ItemStack(Material.IRON_SWORD, 1);
		meta = item.getItemMeta();
		meta.setDisplayName("��f��l���");
		lorelist.clear();
		lorelist.add("��7�÷��̾�� Ư�� ���͸�");
		lorelist.add("��7����ϴ� ����Ʈ�� �ݴϴ�.");
		meta.setLore(lorelist);
		writeQuestGui.setItem(4, item);
		
		item = new ItemStack(397, 1, (byte) 3);
		meta = item.getItemMeta();
		meta.setDisplayName("��f��l��ȭ");
		lorelist.clear();
		lorelist.add("��7�÷��̾�� Ư�� NPC����");
		lorelist.add("��7���� �Ŵ� ����Ʈ�� �ݴϴ�.");
		meta.setLore(lorelist);
		writeQuestGui.setItem(5, item);
		
		item = new ItemStack(Material.CHEST, 1);
		meta = item.getItemMeta();
		meta.setDisplayName("��f��l����");
		lorelist.clear();
		lorelist.add("��7�÷��̾�� ������ �ݴϴ�.");
		meta.setLore(lorelist);
		writeQuestGui.setItem(6, item);
		
		item = new ItemStack(Material.ENDER_PEARL, 1);
		meta = item.getItemMeta();
		meta.setDisplayName("��f��l�̵�");
		lorelist.clear();
		lorelist.add("��7�÷��̾ Ư����ġ��");
		lorelist.add("��7�̵� ��ŵ�ϴ�.");
		meta.setLore(lorelist);
		writeQuestGui.setItem(7, item);
		
		item = new ItemStack(Material.DIAMOND_ORE, 1);
		meta = item.getItemMeta();
		meta.setDisplayName("��f��lä��");
		lorelist.clear();
		lorelist.add("��7�÷��̾�� Ư�� �����");
		lorelist.add("��7ä���ϴ� ����Ʈ�� �ݴϴ�.");
		meta.setLore(lorelist);
		writeQuestGui.setItem(8, item);
		
		item = new ItemStack(Material.REDSTONE_BLOCK, 1);
		meta = item.getItemMeta();
		meta.setDisplayName("��f��l���");
		lorelist.clear();
		lorelist.add("��7Ư�� ��ġ�� ������");
		lorelist.add("��7����� �����մϴ�.");
		meta.setLore(lorelist);
		writeQuestGui.setItem(9, item);
		
		item = new ItemStack(Material.JUKEBOX, 1);
		meta = item.getItemMeta();
		meta.setDisplayName("��f��l�Ҹ�");
		lorelist.clear();
		lorelist.add("��7Ư�� ��ġ�� �Ҹ��� ���� �մϴ�.");
		meta.setLore(lorelist);
		writeQuestGui.setItem(10, item);
		
		item = new ItemStack(Material.BEACON, 1);
		meta = item.getItemMeta();
		meta.setDisplayName("��f��l��ü");
		lorelist.clear();
		lorelist.add("��7���� ��ü�� �޽����� ��Ÿ���ϴ�.");
		meta.setLore(lorelist);
		writeQuestGui.setItem(11, item);
		
		item = new ItemStack(Material.MAP, 1);
		meta = item.getItemMeta();
		meta.setDisplayName("��f��l�׺�");
		lorelist.clear();
		lorelist.add("��7�÷��̾�� �׺���̼��� �۵� ��ŵ�ϴ�.");
		meta.setLore(lorelist);
		writeQuestGui.setItem(12, item);
		
		getServer().getPluginManager().registerEvents(this, this);
		getLogger().info("EG ����Ʈ �÷������� �ε� �Ǿ����ϴ�.");
	}
	
	public void onDisable(){
		getLogger().info("EG ����Ʈ �÷������� ��ε� �Ǿ����ϴ�.");
	}
	
	public boolean onCommand(CommandSender talker, Command command, String str, String[] args){
		if(talker instanceof Player){
			Player p = (Player) talker;
			if(str.equalsIgnoreCase("eg����Ʈ") && p.isOp()){
				if(args.length <= 0){
					helpMessages(p);
				}else if(args[0].equalsIgnoreCase("����")){
					makeQuest(p, args);
				}
			}
		}
		return false;
	}
	
	public void helpMessages(Player p){
		p.sendMessage(MS+"/eg����Ʈ ���� [Ÿ��] [�̸�] ��e- ���ο� ����Ʈ�� �����ϸ�, ����â���� �ٷ� �Ѿ�ϴ�.");
		p.sendMessage(MS+"/eg����Ʈ ���� ��e- ���ο� ����Ʈ�� ����ų�, ������ ����Ʈ�� �����մϴ�.");
	}
	
	public void makeQuest(Player p, String args[]){
		if(args.length <= 2){
			p.sendMessage(MS+"/eg����Ʈ ���� [Ÿ��] [�̸�]");
			p.sendMessage(MS+"��6Ÿ�� : �Ϲ� / �ݺ� / ���� / ���� / �Ѵ�");
		} else {
			if(args[1].equalsIgnoreCase("�Ϲ�")){
				Inventory makeinv = Bukkit.createInventory(null, 54, "����Ʈ �ۼ�");
				ItemStack item = new ItemStack(2, 1);
				ItemMeta meta = item.getItemMeta();
				meta.setDisplayName("��f��l�� ������Ʈ �ۼ�");
				List<String> lorelist = new ArrayList<String>();
				lorelist.add("��f���ο� ������Ʈ�� �߰��մϴ�.");
				meta.setLore(lorelist);
				makeinv.setItem(49, item);
				questName.put(p.getName(), args[2]);
				p.openInventory(makeinv);
			} else if(args[1].equalsIgnoreCase("�ݺ�")){
				Inventory makeinv = Bukkit.createInventory(null, 54, "����Ʈ �ۼ�");
				ItemStack item = new ItemStack(2, 1);
				ItemMeta meta = item.getItemMeta();
				meta.setDisplayName("��f��l�� ������Ʈ �ۼ�");
				List<String> lorelist = new ArrayList<String>();
				lorelist.add("��f���ο� ������Ʈ�� �߰��մϴ�.");
				meta.setLore(lorelist);
				makeinv.setItem(49, item);
				p.openInventory(makeinv);
				questName.put(p.getName(), args[2]);
			} else if(args[1].equalsIgnoreCase("����")){
				Inventory makeinv = Bukkit.createInventory(null, 54, "����Ʈ �ۼ�");
				ItemStack item = new ItemStack(2, 1);
				ItemMeta meta = item.getItemMeta();
				meta.setDisplayName("��f��l�� ������Ʈ �ۼ�");
				List<String> lorelist = new ArrayList<String>();
				lorelist.add("��f���ο� ������Ʈ�� �߰��մϴ�.");
				meta.setLore(lorelist);
				makeinv.setItem(49, item);
				p.openInventory(makeinv);
				questName.put(p.getName(), args[2]);
			} else if(args[1].equalsIgnoreCase("����")){
				Inventory makeinv = Bukkit.createInventory(null, 54, "����Ʈ �ۼ�");
				ItemStack item = new ItemStack(2, 1);
				ItemMeta meta = item.getItemMeta();
				meta.setDisplayName("��f��l�� ������Ʈ �ۼ�");
				List<String> lorelist = new ArrayList<String>();
				lorelist.add("��f���ο� ������Ʈ�� �߰��մϴ�.");
				meta.setLore(lorelist);
				makeinv.setItem(49, item);
				p.openInventory(makeinv);
				questName.put(p.getName(), args[2]);
			} else if(args[1].equalsIgnoreCase("�Ѵ�")){
				Inventory makeinv = Bukkit.createInventory(null, 54, "����Ʈ �ۼ�");
				ItemStack item = new ItemStack(2, 1);
				ItemMeta meta = item.getItemMeta();
				meta.setDisplayName("��f��l�� ������Ʈ �ۼ�");
				List<String> lorelist = new ArrayList<String>();
				lorelist.add("��f���ο� ������Ʈ�� �߰��մϴ�.");
				meta.setLore(lorelist);
				makeinv.setItem(49, item);
				p.openInventory(makeinv);
				questName.put(p.getName(), args[2]);
			}
		}
	}
	
	public void clickMakingGUI(Player p, InventoryClickEvent e){
		switch(e.getSlot()){
		case 0: add 
		}
	}
	
	public void openWriteGUI(Player p){
		p.openInventory(writeQuestGui);
	}
	
	
	//�̺�Ʈ
	
	public void onCloseInventory(InventoryCloseEvent e){
		Inventory inv = e.getInventory();
		if(!(e.getPlayer() instanceof Player)) return;
		Player p = (Player) e.getPlayer();
		if(inv.getTitle().equalsIgnoreCase("����Ʈ �ۼ�") || inv.getTitle().equalsIgnoreCase("����Ʈ ����")){
			questName.remove(p.getName());
			p.sendMessage(MS+"����Ʈ �ۼ�â�� �ݾҽ��ϴ�.");
		}
	}
	
	public void onClickInventory(InventoryClickEvent e){
		if(!(e.getWhoClicked() instanceof Player)) return;
		Player p = (Player) e.getWhoClicked();
		Inventory inv = e.getInventory();
		if(inv.getTitle().equalsIgnoreCase("����Ʈ ����")){
			clickMakingGUI(p, e);
			e.setCancelled(true);
		}
		if(inv.getTitle().equalsIgnoreCase("����Ʈ �ۼ�")){
			if(e.getSlot() == 49) openWriteGUI(p);
			e.setCancelled(true);
		}
	}
	
}
