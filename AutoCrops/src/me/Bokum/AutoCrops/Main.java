package me.Bokum.AutoCrops;

import org.bukkit.Material;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.java.JavaPlugin;

public class Main extends JavaPlugin implements Listener{
	
	public void onEnable(){
		getServer().getPluginManager().registerEvents(this, this);
		getLogger().info("�ڵ� ���ѽɱ� �÷������� �ε�Ǿ����ϴ�."); //��Ŷ���� Ȱ��ȭ �������
	}
	
	public void onDisable(){
		getLogger().info("�ڵ� ���ѽɱ� �÷������� ��Ȱ��ȭ �Ǿ����ϴ�."); //��Ŷ���� ��Ȱ��ȭ �������
	}
	
	@EventHandler
	public void onBlockBreak(BlockBreakEvent e){ //���� �ν����� (�÷����ο� ���� ����� �� ����ɶ����� �ν����°� ����)
		//���� �ڵ尡 59�̰� ���� �����Ͱ� 7�̸�
		//@IF i <blockid> = 59
		//�� = 59:7
		//���� = 59:0
		//&& - �׸��� == - ����
		//e.getBlock().getData() - ���� ������ ��������
		//e.getBlock() - �� �������� e.getBlock().getTypeId() - �� �ڵ� ��������
		//e.getBlock().getLocation() - ���� �μ� ��ǥ
		//e.getBlock().getLocation().getBlock() - ���� �μ� ��ǥ�� �����ϴ� ��
		if(e.getBlock().getTypeId() == 59 && e.getBlock().getData() == 7){//�μ� ���� �ڵ尡 59�̰� �����Ͱ� 7�̸�
			e.getBlock().breakNaturally(); //�� ���� �ν���
			e.setCancelled(true); // ���� �μ� ����� ������
			e.getBlock().getLocation().getBlock().setTypeIdAndData(59,(byte) 0, true);// �������� �������ش�
		}
		if(e.getBlock().getTypeId() == 104 && e.getBlock().getData() == 7){//�μ� ���� �ڵ尡 104�̰� �����Ͱ� 7�̸�
			e.getBlock().breakNaturally(); //�� ���� �ν���
			e.setCancelled(true); // ���� �μ� ����� ������
			e.getBlock().getLocation().getBlock().setTypeIdAndData(104,(byte) 0, true);// ȣ�ھ������� �������ش�
		}
		if(e.getBlock().getTypeId() == 105 && e.getBlock().getData() == 7){//�μ� ���� �ڵ尡 105�̰� �����Ͱ� 7�̸�
			e.getBlock().breakNaturally(); //�� ���� �ν���
			e.setCancelled(true); // ���� �μ� ����� ������
			e.getBlock().getLocation().getBlock().setTypeIdAndData(105,(byte) 0, true);// ���ھ������� �������ش�
		}
	}// ����� �� �������� ���� ���� �μ�
	
}
