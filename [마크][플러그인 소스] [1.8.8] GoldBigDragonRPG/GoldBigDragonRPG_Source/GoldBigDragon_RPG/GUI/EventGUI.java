package GoldBigDragon_RPG.GUI;

import java.util.Arrays;
import java.util.Collection;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

import GoldBigDragon_RPG.Main.Object_UserData;
import GoldBigDragon_RPG.Util.YamlController;
import GoldBigDragon_RPG.Util.YamlManager;

public class EventGUI extends GUIutil
{
	public void EventGUI_Main (Player player)
	{
	  	YamlController YC = new YamlController(GoldBigDragon_RPG.Main.Main.plugin);
		YamlManager Config =YC.getNewConfig("config.yml");
		Inventory inv = Bukkit.createInventory(null, 45, ChatColor.BLACK + "�̺�Ʈ ����");
		
		if(Config.getInt("Event.Multiple_Level_Up_SkillPoint") == 1)
		{Stack2(ChatColor.WHITE +""+ ChatColor.BOLD + "��ų ����Ʈ �߰� ȹ��", 340,0,1,Arrays.asList(ChatColor.RED + "[�� Ȱ��ȭ]",ChatColor.GRAY + "���� ���� ��� ��ų ����Ʈ : " + Config.getInt("Server.Level_Up_SkillPoint")), 10, inv);}
		else
		{{Stack2(ChatColor.WHITE +""+ ChatColor.BOLD + "��ų ����Ʈ �߰� ȹ��", 403,0,Config.getInt("Event.Multiple_Level_Up_SkillPoint"),Arrays.asList(ChatColor.GREEN + "[Ȱ��ȭ]",ChatColor.YELLOW +""+Config.getInt("Event.Multiple_Level_Up_SkillPoint") +ChatColor.GRAY +"��",ChatColor.GRAY + "���� ���� ��� ��ų ����Ʈ : " + (Config.getInt("Server.Level_Up_SkillPoint") * Config.getInt("Event.Multiple_Level_Up_SkillPoint"))), 10, inv);}	}

		if(Config.getInt("Event.Multiple_Level_Up_StatPoint") == 1)
		{Stack2(ChatColor.WHITE +""+ ChatColor.BOLD + "���� ����Ʈ �߰� ȹ��", 351,15,1,Arrays.asList(ChatColor.RED + "[�� Ȱ��ȭ]",ChatColor.GRAY + "���� ���� ��� ���� ����Ʈ : " + Config.getInt("Server.Level_Up_StatPoint")), 11, inv);}
		else
		{{Stack2(ChatColor.WHITE +""+ ChatColor.BOLD + "���� ����Ʈ �߰� ȹ��", 399,0,Config.getInt("Event.Multiple_Level_Up_StatPoint"),Arrays.asList(ChatColor.GREEN + "[Ȱ��ȭ]",ChatColor.YELLOW +""+Config.getInt("Event.Multiple_Level_Up_StatPoint") +ChatColor.GRAY +"��",ChatColor.GRAY + "���� ���� ��� ���� ����Ʈ : " + (Config.getInt("Server.Level_Up_StatPoint")*Config.getInt("Event.Multiple_Level_Up_StatPoint"))), 11, inv);}	}

		if(Config.getInt("Event.Multiple_EXP_Get") == 1)
		{Stack2(ChatColor.WHITE +""+ ChatColor.BOLD + "����ġ �߰� ȹ��", 374,0,1,Arrays.asList(ChatColor.RED + "[�� Ȱ��ȭ]",ChatColor.GRAY + "����ġ ȹ��� : " + Config.getInt("Event.Multiple_EXP_Get")+"��"), 12, inv);}
		else
		{{Stack2(ChatColor.WHITE +""+ ChatColor.BOLD + "����ġ �߰� ȹ��", 384,0,Config.getInt("Event.Multiple_EXP_Get"),Arrays.asList(ChatColor.GREEN + "[Ȱ��ȭ]",ChatColor.GRAY + "����ġ ȹ��� : "+ChatColor.YELLOW +""+Config.getInt("Event.Multiple_EXP_Get") +ChatColor.GRAY +"��"), 12, inv);}	}
		
		if(Config.getInt("Event.DropChance") == 1)
		{Stack2(ChatColor.WHITE +""+ ChatColor.BOLD + "��ӷ� ���", 265,0,1,Arrays.asList(ChatColor.RED + "[�� Ȱ��ȭ]",ChatColor.GRAY + "��ӷ� : " + Config.getInt("Event.DropChance")+"��"), 13, inv);}
		else
		{{Stack2(ChatColor.WHITE +""+ ChatColor.BOLD + "��ӷ� ���", 266,0,Config.getInt("Event.DropChance"),Arrays.asList(ChatColor.GREEN + "[Ȱ��ȭ]",ChatColor.GRAY + "��ӷ� : "+ChatColor.YELLOW +""+Config.getInt("Event.DropChance") +ChatColor.GRAY +"��"), 13, inv);}	}

		if(Config.getInt("Event.Multiple_Proficiency_Get") == 1)
		{Stack2(ChatColor.WHITE +""+ ChatColor.BOLD + "���õ� ���", 145,0,1,Arrays.asList(ChatColor.RED + "[�� Ȱ��ȭ]",ChatColor.GRAY + "���õ� : " + Config.getInt("Event.Multiple_Proficiency_Get")+"��"), 14, inv);}
		else
		{{Stack2(ChatColor.WHITE +""+ ChatColor.BOLD + "���õ� ���", 145,2,Config.getInt("Event.Multiple_Proficiency_Get"),Arrays.asList(ChatColor.GREEN + "[Ȱ��ȭ]",ChatColor.GRAY + "���õ� : "+ChatColor.YELLOW +""+Config.getInt("Event.Multiple_Proficiency_Get") +ChatColor.GRAY +"��"), 14, inv);}	}
		
		Stack2(ChatColor.WHITE +""+ ChatColor.BOLD + "��ü �ֱ�", 54,0,1,Arrays.asList(ChatColor.GRAY + "���� ������ ��� ��������",ChatColor.GRAY+"�������� �����մϴ�."), 28, inv);
		Stack2(ChatColor.WHITE +""+ ChatColor.BOLD + "���� �ֱ�", 130,0,1,Arrays.asList(ChatColor.GRAY + "���� ������ ������ ��,",ChatColor.GRAY+"�� ������Ը� ������",ChatColor.GRAY+"�������� �����մϴ�."), 30, inv);

		
		Stack2(ChatColor.WHITE  + "" + ChatColor.BOLD + "���� ���", 323,0,1,Arrays.asList(ChatColor.GRAY + "�۾� ������ �޴��� ���ư��ϴ�."), 36, inv);

		Stack2(ChatColor.WHITE +""+ ChatColor.BOLD + "�ݱ�", 324,0,1,Arrays.asList(ChatColor.GRAY + "�۾� ������ â�� �ݽ��ϴ�."), 44, inv);
		
		player.openInventory(inv);
	}

	public void AllPlayerGiveEventGUI(Player player, boolean All)
	{
		Inventory inv = Bukkit.createInventory(null, 45, ChatColor.BLACK + "�̺�Ʈ ��ü ����");
		if(All==false)
			inv = Bukkit.createInventory(null, 45, ChatColor.BLACK + "�̺�Ʈ ���� ����");
		for(byte count = 0; count <10;count++)
			Stack2(ChatColor.YELLOW+"[ ���� �� ������ ]", 160, 4, 1, null, count, inv);
		Stack2(ChatColor.YELLOW+"[ ���� �� ������ ]", 160, 4, 1, null, 17, inv);
		Stack2(ChatColor.YELLOW+"[ ���� �� ������ ]", 160, 4, 1, null, 18, inv);
		for(byte count = 26; count <36;count++)
			Stack2(ChatColor.YELLOW+"[ ���� �� ������ ]", 160, 4, 1, null, count, inv);

		for(byte count = 36; count <44;count++)
			Stack2(ChatColor.YELLOW+" ", 160, 8, 1, null, count, inv);

		if(All)
			Stack2(ChatColor.WHITE  + "" + ChatColor.BOLD + "[���� ����!]", 54,0,1,Arrays.asList(ChatColor.GRAY + "��� �׵θ� ���� ��������",ChatColor.GRAY+"��� �÷��̾�� �����մϴ�!"), 40, inv);
		else
			Stack2(ChatColor.WHITE  + "" + ChatColor.BOLD + "[���� ����!]", 130,0,1,Arrays.asList(ChatColor.GRAY + "��� �׵θ� ���� ��������",ChatColor.GRAY+"���� ������ �𸨴ϴ�!"), 40, inv);
			
		Stack2(ChatColor.WHITE  + "" + ChatColor.BOLD + "���� ���", 323,0,1,Arrays.asList(ChatColor.GRAY + "�̺�Ʈ �޴��� ���ư��ϴ�."), 36, inv);
		Stack2(ChatColor.WHITE +""+ ChatColor.BOLD + "�ݱ�", 324,0,1,Arrays.asList(ChatColor.GRAY + "�۾� ������ â�� �ݽ��ϴ�."), 44, inv);
		player.openInventory(inv);
	}
	
	
	
	
	//���� GUIâ ���� �������� ������ ��, �ش� �����ܿ� ����� �ִ� �޼ҵ�1   -���� GUI, ���ǹڽ�, Ŀ���� ����GUI-//
	public void EventGUIInventoryclick(InventoryClickEvent event)
	{
		GoldBigDragon_RPG.Effect.Sound s = new GoldBigDragon_RPG.Effect.Sound();
		event.setCancelled(true);
		Player player = (Player) event.getWhoClicked();
		switch (event.getSlot())
		{
		case 28://��ü �ֱ�
			s.SP(player, Sound.ITEM_PICKUP, 1.0F, 1.0F);
			AllPlayerGiveEventGUI(player,true);
			return;
		case 30://���� �ֱ�
			s.SP(player, Sound.ITEM_PICKUP, 1.0F, 1.0F);
			AllPlayerGiveEventGUI(player,false);
			return;
		case 10:
			{
				Object_UserData u = new Object_UserData();
				s.SP(player, Sound.ITEM_PICKUP, 1.0F, 1.0F);
				player.closeInventory();
				player.sendMessage(ChatColor.GREEN+"[�̺�Ʈ] : ��ų ����Ʈ ��·��� �� ��� �ϽǷ���?");
				player.sendMessage(ChatColor.YELLOW+"(1 ~ 10) (1�� ��� �̺�Ʈ ����)");
				u.setType(player, "Event");
				u.setString(player, (byte)1, "SKP");
			}
			return;
		case 11:
			{
				Object_UserData u = new Object_UserData();
				s.SP(player, Sound.ITEM_PICKUP, 1.0F, 1.0F);
				player.closeInventory();
				player.sendMessage(ChatColor.GREEN+"[�̺�Ʈ] : ���� ����Ʈ ��·��� �� ��� �ϽǷ���?");
				player.sendMessage(ChatColor.YELLOW+"(1 ~ 10) (1�� ��� �̺�Ʈ ����)");
				u.setType(player, "Event");
				u.setString(player, (byte)1, "STP");
			}
			return;
		case 12:
			{
				Object_UserData u = new Object_UserData();
				s.SP(player, Sound.ITEM_PICKUP, 1.0F, 1.0F);
				player.closeInventory();
				player.sendMessage(ChatColor.GREEN+"[�̺�Ʈ] : ����ġ ��·��� �� ��� �ϽǷ���?");
				player.sendMessage(ChatColor.YELLOW+"(1 ~ 10) (1�� ��� �̺�Ʈ ����)");
				u.setType(player, "Event");
				u.setString(player, (byte)1, "EXP");
			}
			return;
		case 13:
			{
				Object_UserData u = new Object_UserData();
				s.SP(player, Sound.ITEM_PICKUP, 1.0F, 1.0F);
				player.closeInventory();
				player.sendMessage(ChatColor.GREEN+"[�̺�Ʈ] : ����ġ ��·��� �� ��� �ϽǷ���?");
				player.sendMessage(ChatColor.YELLOW+"(1 ~ 10) (1�� ��� �̺�Ʈ ����)");
				u.setType(player, "Event");
				u.setString(player, (byte)1, "DROP");
			}
			return;
		case 14:
		{
			Object_UserData u = new Object_UserData();
			s.SP(player, Sound.ITEM_PICKUP, 1.0F, 1.0F);
			player.closeInventory();
			player.sendMessage(ChatColor.GREEN+"[�̺�Ʈ] : ���õ� ��·��� �� ��� �ϽǷ���?");
			player.sendMessage(ChatColor.YELLOW+"(1 ~ 10) (1�� ��� �̺�Ʈ ����)");
			u.setType(player, "Event");
			u.setString(player, (byte)1, "Proficiency");
		}
		return;
		case 36:
			OPBoxGUI OGUI = new OPBoxGUI();
			s.SP(player, Sound.ITEM_PICKUP, 0.8F, 1.0F);
			OGUI.OPBoxGUI_Main(player,(byte) 1);
			return;
		case 44:
			s.SP(player, Sound.PISTON_RETRACT, 0.8F, 1.8F);
			player.closeInventory();
			return;
		}
		return;
	}

	public void AllPlayerGiveEventGUIclick(InventoryClickEvent event)
	{
		GoldBigDragon_RPG.Effect.Sound s = new GoldBigDragon_RPG.Effect.Sound();

		Player player = (Player) event.getWhoClicked();
		if(event.getSlot()>=0&&event.getSlot()<=9)
			event.setCancelled(true);
		if(event.getSlot()>=27&&event.getSlot()<=35)
			event.setCancelled(true);
		if(event.getSlot()>=35)
			event.setCancelled(true);
		switch (event.getSlot())
		{
		case 17:
		case 18:
		case 26:
			event.setCancelled(true);
			break;
		case 36:
			event.setCancelled(true);
			s.SP(player, Sound.ITEM_PICKUP, 0.8F, 1.0F);
			EventGUI_Main(player);
			return;
		case 40://���� ����
			event.setCancelled(true);
			GoldBigDragon_RPG.Event.Interact IT = new GoldBigDragon_RPG.Event.Interact();
			if(event.getInventory().getTitle().contains("����"))
			{
				boolean ItemExit = false;
  		    	Collection<? extends Player> playerlist = Bukkit.getServer().getOnlinePlayers();
  		    	Player[] a = new Player[playerlist.size()];
  		    	playerlist.toArray(a);
  		    	short LuckyGuy = (short) new GoldBigDragon_RPG.Util.Number().RandomNum(0, a.length-1);
				for(byte count = 10; count < 17;count++)
				{
					if(event.getInventory().getItem(count) != null)
					{
						ItemExit = true;
						ItemStack item = event.getInventory().getItem(count);
						new GoldBigDragon_RPG.Util.PlayerUtil().giveItemForce(a[LuckyGuy], item);
					}
				}
				for(byte count = 19; count < 26;count++)
				{
					if(event.getInventory().getItem(count) != null)
					{
						ItemExit = true;
						ItemStack item = event.getInventory().getItem(count);
						new GoldBigDragon_RPG.Util.PlayerUtil().giveItemForce(a[LuckyGuy], item);
					}
				}
				if(ItemExit)
				{
					s.SP(a[LuckyGuy], Sound.ITEM_PICKUP, 1.0F, 1.9F);
					Bukkit.broadcastMessage(ChatColor.YELLOW+"[�̺�Ʈ] : "+ChatColor.GOLD+""+ChatColor.BOLD+a[LuckyGuy].getName()+ChatColor.YELLOW+"�Բ��� ���� ������ ���޿� ��÷ �Ǽ̽��ϴ�!");
					EventGUI_Main(player);
				}
			}
			else
			{
				boolean ItemExit = false;
				for(byte count = 10; count < 17;count++)
				{
					if(event.getInventory().getItem(count) != null)
					{
						ItemExit = true;
						ItemStack item = event.getInventory().getItem(count);
		  		    	Collection<? extends Player> playerlist = Bukkit.getServer().getOnlinePlayers();
		  		    	Player[] a = new Player[playerlist.size()];
		  		    	playerlist.toArray(a);
		  	  			for(short counter = 0; counter<a.length;counter++)
		  	  			{
							new GoldBigDragon_RPG.Util.PlayerUtil().giveItemForce(a[counter], item);
		  	  				if(item.hasItemMeta())
		  	  				{
		  	  					if(item.getItemMeta().hasDisplayName())
		  	  						a[counter].sendMessage(ChatColor.YELLOW+"[�̺�Ʈ] : "+item.getItemMeta().getDisplayName()+ChatColor.YELLOW+" �������� "+item.getAmount()+"�� ���� �޾ҽ��ϴ�!");
		  	  				}
		  	  				else
	  	  						a[counter].sendMessage(ChatColor.YELLOW+"[�̺�Ʈ] : "+IT.SetItemDefaultName((short) item.getTypeId(), item.getData().getData())+ChatColor.YELLOW+" �������� "+item.getAmount()+"�� ���� �޾ҽ��ϴ�!");
		  	  				s.SP(a[counter], Sound.ITEM_PICKUP, 0.7F, 1.8F);
		  	  			}
					}
				}
				for(byte count = 19; count < 26;count++)
				{
					if(event.getInventory().getItem(count) != null)
					{
						ItemExit = true;
						ItemStack item = event.getInventory().getItem(count);
		  		    	Collection<? extends Player> playerlist = Bukkit.getServer().getOnlinePlayers();
		  		    	Player[] a = new Player[playerlist.size()];
		  		    	playerlist.toArray(a);
		  	  			for(short counter = 0; counter<a.length;counter++)
		  	  			{
							new GoldBigDragon_RPG.Util.PlayerUtil().giveItemForce(a[counter], item);
		  	  				if(item.hasItemMeta())
		  	  				{
		  	  					if(item.getItemMeta().hasDisplayName())
		  	  						a[counter].sendMessage(ChatColor.YELLOW+"[�̺�Ʈ] : "+item.getItemMeta().getDisplayName()+ChatColor.YELLOW+" �������� "+item.getAmount()+"�� ���� �޾ҽ��ϴ�!");
		  	  				}
		  	  				else
	  	  						a[counter].sendMessage(ChatColor.YELLOW+"[�̺�Ʈ] : "+IT.SetItemDefaultName((short) item.getTypeId(), item.getData().getData())+ChatColor.YELLOW+" �������� "+item.getAmount()+"�� ���� �޾ҽ��ϴ�!");
		  	  				s.SP(a[counter], Sound.ITEM_PICKUP, 0.7F, 1.8F);	
		  	  			}
					}
				}
				if(ItemExit)
					EventGUI_Main(player);
			}
			return;
		case 44:
			event.setCancelled(true);
			s.SP(player, Sound.PISTON_RETRACT, 0.8F, 1.8F);
			player.closeInventory();
			return;
		}
	}
}
