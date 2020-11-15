package me.Bokum.Ex1;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.plugin.java.JavaPlugin;

public class Main extends JavaPlugin implements Listener{
	public void onEnable(){
		getServer().getPluginManager().registerEvents(this, this);
		getLogger().info("���� �÷����� 1�� �ε� �Ǿ����ϴ�.");
	}
	
	public void onDisable(){
		getLogger().info("���� �÷����� 1�� ��ε� �Ǿ����ϴ�.");
	}
	
	@EventHandler
	public void onBlockBreak(BlockBreakEvent e){
		if(e.getBlock().getTypeId() == 1){
			if(!e.getPlayer().getInventory().contains(276)){
				ItemStack item = new ItemStack(276, 1);
				ItemMeta meta = item.getItemMeta();
				meta.setDisplayName("��c������ ���̾��");
				List<String> lorelist = new ArrayList<String>();
				lorelist.add("�� ���� ������ ���̴�.");
				lorelist.add("�� ���� ������ �����̴�.");
				meta.setLore(lorelist);
				item.setItemMeta(meta);
				e.getPlayer().getInventory().addItem(item);
			}
		}
	}
	
}
