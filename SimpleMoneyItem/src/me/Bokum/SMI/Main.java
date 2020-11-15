package me.Bokum.SMI;


import java.util.ArrayList;
import java.util.List;

import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.plugin.RegisteredServiceProvider;
import org.bukkit.plugin.java.JavaPlugin;

import net.milkbowl.vault.economy.Economy;


public class Main extends JavaPlugin implements Listener{
	public static Economy econ = null;
	public String title = "��f[��eȭ���f] ";
	
	public void onEnable(){
	    getServer().getPluginManager().registerEvents(this, this);
	    getLogger().info("[����] ����ȭ�� �÷������� �ε� �Ǿ����ϴ�.");
	    if (!setupEconomy())
	        getLogger().info("[���� �߻� ��� ] Vault�÷������� �νĵ��� �ʾҽ��ϴ�!");
	}
	
	public void onDisable(){
	    getLogger().info("[����} ����ȭ�� �÷������� ��ε� �Ǿ����ϴ�.");
	}
	
	  private boolean setupEconomy()
	  {
	    if (getServer().getPluginManager().getPlugin("Vault") == null) {
	      return false;
	    }
	    RegisteredServiceProvider rsp = getServer().getServicesManager().getRegistration(Economy.class);
	    if (rsp == null) {
	      return false;
	    }
	    econ = (Economy)rsp.getProvider();
	    return econ != null;
	  }
	  
	  public boolean onCommand(CommandSender talker, Command command, String string, String[] args){
		  if(talker instanceof Player){
			  Player p = (Player) talker;
			  if((string.equalsIgnoreCase("ȭ��")) && (p.hasPermission("smi"))){
				  if(args.length <= 0){
					  helpMessages(p);
				  } else {
					  if(args[0].equalsIgnoreCase("����")) publish(p, args);
				  }
			  }
		  }
		  return false;
	  }
	  
	 private void helpMessages(Player p){
		 	p.sendMessage(title+"���� ������ ��: ��a"+econ.getBalance(p.getName()));	
		 	p.sendMessage(title+"/ȭ�� ���� �� ������ȭ���");	
	 }
	 
	 private int emptyInventorySpace(Player p){
		 Inventory inv = p.getInventory();
		 int check=0;
		 for (ItemStack item: inv.getContents()) {
			 if(item == null) {
				 check++;
			}
		 }
		 return check;
	 }
	 
	 private void publish(Player p, String[] args){
		 if(args.length >= 3){
			 int money;
			 int cnt;
			 try{
				 money = Integer.valueOf(args[1]);
				 cnt = Integer.valueOf(args[2]);
			 }catch(Exception e){
				 p.sendMessage(title+"���� ����ȭ����� ���ڷθ� �Է����ּ���.");
				 return;
			 }
			 if(money <= 0 || cnt <= 0){
				 p.sendMessage(title+"���� ����ȭ����� 1�̻��� ���ڸ� �Է��� �����մϴ�.");
			 }
			 if(cnt > 200){
				 p.sendMessage(title+"�ѹ��� �ִ� 200�������� ������ �����մϴ�.");
			 }
			 double hasMoney = econ.getBalance(p.getName());
			 int needMoney = money * cnt;
			 
			 if(hasMoney >= needMoney){
				 int needInvSpace = (int)(cnt / 64);
				 if((cnt % 64) != 0) needInvSpace++;
				 if(emptyInventorySpace(p) >= needInvSpace){
					 econ.withdrawPlayer(p.getName(), needMoney);
					 ItemStack item = new ItemStack(339, 1);
					 ItemMeta meta = item.getItemMeta();
					 meta.setDisplayName(title);
					 List<String> list = new ArrayList(1);
					 list.add(ChatColor.AQUA+" "+money+"��");
					 meta.setLore(list);
					 item.setItemMeta(meta);
					 for(int i = 0; i < cnt; i++){
						 p.getInventory().addItem(item);
					 }
					 p.sendMessage(title+"ȭ������� �Ϸ�Ǿ����ϴ�."); 
					 p.updateInventory();
				 } else {
					p.sendMessage(title+"ȭ�� ������ �κ��丮 ������ �����մϴ�."); 
				 }
			 } else {
				 p.sendMessage(title+"��û�Ͻ� ȭ�� �����Ϸ��� ��r"+(needMoney-hasMoney)+"��f�� �� �ʿ��մϴ�.");
			 }
		 }
	 }
	 
	 
	 @EventHandler
	 public void onRightClick(PlayerInteractEvent e){
		 if(e.getAction() == Action.RIGHT_CLICK_AIR || e.getAction() == Action.RIGHT_CLICK_BLOCK){
			 Player p = e.getPlayer();
			 if(e.getItem() == null) return;
			 ItemStack item = p.getItemInHand();
			 if ((item == null) || (!item.hasItemMeta()) || (!item.getItemMeta().hasDisplayName())) return;
			 if(item.getItemMeta().getDisplayName().contains(title) && item.getTypeId() == 339){
				 int money;
				 String tempStr = item.getItemMeta().getLore().get(0);
				 int start = tempStr.indexOf(" ");
				 int end = tempStr.indexOf("��");
				 String getStr = tempStr.substring(start+1, end);
				 try{
					 money = Integer.valueOf(getStr);
				 } catch(Exception ect){
					 p.sendMessage(title+"ȭ�������� �ùٸ��� �ʽ��ϴ�.");
					 return;
				 }
				 int nowItemCnt = item.getAmount();
				 if(nowItemCnt > 1) item.setAmount(nowItemCnt-1);
				 else p.getInventory().setItemInHand(new ItemStack(Material.AIR));
				 p.updateInventory();
				 econ.depositPlayer(p.getName(), money);
				 p.sendMessage(title+money+"���� �ԱݵǾ����ϴ�.");
			 }
		 }
	 }
	 

	
}
