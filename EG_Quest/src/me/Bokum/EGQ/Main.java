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
	public static String MS = "§f[ §aEG 퀘스트 §f] §6";
	
	public void onEnable(){
		instance = this;
		
		writeQuestGui = Bukkit.createInventory(null, 54, "퀘스트 작성");
		ItemStack item = new ItemStack(Material.SIGN, 1);
		ItemMeta meta = item.getItemMeta();
		meta.setDisplayName("§f§l대사");
		List<String> lorelist = new ArrayList<String>();
		lorelist.add("§7대화창을 띄우고, 작성된");
		lorelist.add("§7스크립트를 유저에게 띄웁니다.");
		lorelist.add("§7(화자 : NPC)");
		meta.setLore(lorelist);
		writeQuestGui.setItem(0, item);
		
		item = new ItemStack(Material.SIGN, 1);
		meta = item.getItemMeta();
		meta.setDisplayName("§f§l독백");
		lorelist.clear();
		lorelist.add("§7대화창을 띄우고, 작성된");
		lorelist.add("§7스크립트를 유저에게 띄웁니다.");
		lorelist.add("§7(화자 : 유저)");
		meta.setLore(lorelist);
		writeQuestGui.setItem(1, item);
		
		item = new ItemStack(Material.EMERALD, 1);
		meta = item.getItemMeta();
		meta.setDisplayName("§f§l전달");
		lorelist.clear();
		lorelist.add("§7플레이어가 특정 아이템을");
		lorelist.add("§7NPC에게 줘야하는 퀘스트를 줍니다.");
		meta.setLore(lorelist);
		writeQuestGui.setItem(3, item);
		
		item = new ItemStack(Material.IRON_SWORD, 1);
		meta = item.getItemMeta();
		meta.setDisplayName("§f§l사냥");
		lorelist.clear();
		lorelist.add("§7플레이어에게 특정 몬스터를");
		lorelist.add("§7사냥하는 퀘스트를 줍니다.");
		meta.setLore(lorelist);
		writeQuestGui.setItem(4, item);
		
		item = new ItemStack(397, 1, (byte) 3);
		meta = item.getItemMeta();
		meta.setDisplayName("§f§l대화");
		lorelist.clear();
		lorelist.add("§7플레이어에게 특정 NPC에게");
		lorelist.add("§7말을 거는 퀘스트를 줍니다.");
		meta.setLore(lorelist);
		writeQuestGui.setItem(5, item);
		
		item = new ItemStack(Material.CHEST, 1);
		meta = item.getItemMeta();
		meta.setDisplayName("§f§l보상");
		lorelist.clear();
		lorelist.add("§7플레이어에게 보상을 줍니다.");
		meta.setLore(lorelist);
		writeQuestGui.setItem(6, item);
		
		item = new ItemStack(Material.ENDER_PEARL, 1);
		meta = item.getItemMeta();
		meta.setDisplayName("§f§l이동");
		lorelist.clear();
		lorelist.add("§7플레이어를 특정위치로");
		lorelist.add("§7이동 시킵니다.");
		meta.setLore(lorelist);
		writeQuestGui.setItem(7, item);
		
		item = new ItemStack(Material.DIAMOND_ORE, 1);
		meta = item.getItemMeta();
		meta.setDisplayName("§f§l채집");
		lorelist.clear();
		lorelist.add("§7플레이어에게 특정 블록을");
		lorelist.add("§7채취하는 퀘스트를 줍니다.");
		meta.setLore(lorelist);
		writeQuestGui.setItem(8, item);
		
		item = new ItemStack(Material.REDSTONE_BLOCK, 1);
		meta = item.getItemMeta();
		meta.setDisplayName("§f§l블록");
		lorelist.clear();
		lorelist.add("§7특정 위치에 정해진");
		lorelist.add("§7블록을 생성합니다.");
		meta.setLore(lorelist);
		writeQuestGui.setItem(9, item);
		
		item = new ItemStack(Material.JUKEBOX, 1);
		meta = item.getItemMeta();
		meta.setDisplayName("§f§l소리");
		lorelist.clear();
		lorelist.add("§7특정 위치에 소리가 나게 합니다.");
		meta.setLore(lorelist);
		writeQuestGui.setItem(10, item);
		
		item = new ItemStack(Material.BEACON, 1);
		meta = item.getItemMeta();
		meta.setDisplayName("§f§l전체");
		lorelist.clear();
		lorelist.add("§7서버 전체에 메시지가 나타납니다.");
		meta.setLore(lorelist);
		writeQuestGui.setItem(11, item);
		
		item = new ItemStack(Material.MAP, 1);
		meta = item.getItemMeta();
		meta.setDisplayName("§f§l네비");
		lorelist.clear();
		lorelist.add("§7플레이어에게 네비게이션을 작동 시킵니다.");
		meta.setLore(lorelist);
		writeQuestGui.setItem(12, item);
		
		getServer().getPluginManager().registerEvents(this, this);
		getLogger().info("EG 퀘스트 플러그인이 로드 되었습니다.");
	}
	
	public void onDisable(){
		getLogger().info("EG 퀘스트 플러그인이 언로드 되었습니다.");
	}
	
	public boolean onCommand(CommandSender talker, Command command, String str, String[] args){
		if(talker instanceof Player){
			Player p = (Player) talker;
			if(str.equalsIgnoreCase("eg퀘스트") && p.isOp()){
				if(args.length <= 0){
					helpMessages(p);
				}else if(args[0].equalsIgnoreCase("생성")){
					makeQuest(p, args);
				}
			}
		}
		return false;
	}
	
	public void helpMessages(Player p){
		p.sendMessage(MS+"/eg퀘스트 생성 [타입] [이름] §e- 새로운 퀘스트를 생성하며, 설정창으로 바로 넘어갑니다.");
		p.sendMessage(MS+"/eg퀘스트 구성 §e- 새로운 퀘스트를 만들거나, 기존의 퀘스트를 삭제합니다.");
	}
	
	public void makeQuest(Player p, String args[]){
		if(args.length <= 2){
			p.sendMessage(MS+"/eg퀘스트 생성 [타입] [이름]");
			p.sendMessage(MS+"§6타입 : 일반 / 반복 / 일일 / 일주 / 한달");
		} else {
			if(args[1].equalsIgnoreCase("일반")){
				Inventory makeinv = Bukkit.createInventory(null, 54, "퀘스트 작성");
				ItemStack item = new ItemStack(2, 1);
				ItemMeta meta = item.getItemMeta();
				meta.setDisplayName("§f§l새 오브젝트 작성");
				List<String> lorelist = new ArrayList<String>();
				lorelist.add("§f새로운 오브젝트를 추가합니다.");
				meta.setLore(lorelist);
				makeinv.setItem(49, item);
				questName.put(p.getName(), args[2]);
				p.openInventory(makeinv);
			} else if(args[1].equalsIgnoreCase("반복")){
				Inventory makeinv = Bukkit.createInventory(null, 54, "퀘스트 작성");
				ItemStack item = new ItemStack(2, 1);
				ItemMeta meta = item.getItemMeta();
				meta.setDisplayName("§f§l새 오브젝트 작성");
				List<String> lorelist = new ArrayList<String>();
				lorelist.add("§f새로운 오브젝트를 추가합니다.");
				meta.setLore(lorelist);
				makeinv.setItem(49, item);
				p.openInventory(makeinv);
				questName.put(p.getName(), args[2]);
			} else if(args[1].equalsIgnoreCase("일일")){
				Inventory makeinv = Bukkit.createInventory(null, 54, "퀘스트 작성");
				ItemStack item = new ItemStack(2, 1);
				ItemMeta meta = item.getItemMeta();
				meta.setDisplayName("§f§l새 오브젝트 작성");
				List<String> lorelist = new ArrayList<String>();
				lorelist.add("§f새로운 오브젝트를 추가합니다.");
				meta.setLore(lorelist);
				makeinv.setItem(49, item);
				p.openInventory(makeinv);
				questName.put(p.getName(), args[2]);
			} else if(args[1].equalsIgnoreCase("주일")){
				Inventory makeinv = Bukkit.createInventory(null, 54, "퀘스트 작성");
				ItemStack item = new ItemStack(2, 1);
				ItemMeta meta = item.getItemMeta();
				meta.setDisplayName("§f§l새 오브젝트 작성");
				List<String> lorelist = new ArrayList<String>();
				lorelist.add("§f새로운 오브젝트를 추가합니다.");
				meta.setLore(lorelist);
				makeinv.setItem(49, item);
				p.openInventory(makeinv);
				questName.put(p.getName(), args[2]);
			} else if(args[1].equalsIgnoreCase("한달")){
				Inventory makeinv = Bukkit.createInventory(null, 54, "퀘스트 작성");
				ItemStack item = new ItemStack(2, 1);
				ItemMeta meta = item.getItemMeta();
				meta.setDisplayName("§f§l새 오브젝트 작성");
				List<String> lorelist = new ArrayList<String>();
				lorelist.add("§f새로운 오브젝트를 추가합니다.");
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
	
	
	//이벤트
	
	public void onCloseInventory(InventoryCloseEvent e){
		Inventory inv = e.getInventory();
		if(!(e.getPlayer() instanceof Player)) return;
		Player p = (Player) e.getPlayer();
		if(inv.getTitle().equalsIgnoreCase("퀘스트 작성") || inv.getTitle().equalsIgnoreCase("퀘스트 생성")){
			questName.remove(p.getName());
			p.sendMessage(MS+"퀘스트 작성창을 닫았습니다.");
		}
	}
	
	public void onClickInventory(InventoryClickEvent e){
		if(!(e.getWhoClicked() instanceof Player)) return;
		Player p = (Player) e.getWhoClicked();
		Inventory inv = e.getInventory();
		if(inv.getTitle().equalsIgnoreCase("퀘스트 생성")){
			clickMakingGUI(p, e);
			e.setCancelled(true);
		}
		if(inv.getTitle().equalsIgnoreCase("퀘스트 작성")){
			if(e.getSlot() == 49) openWriteGUI(p);
			e.setCancelled(true);
		}
	}
	
}
