package GoldBigDragon_RPG.Event;

import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryCloseEvent;

import GoldBigDragon_RPG.Main.Object_UserData;

public class InventoryClose
{
	public void InventoryCloseRouter(InventoryCloseEvent event)
	{
		Object_UserData u = new Object_UserData();
		Player player = (Player) event.getPlayer();
		String InventoryName = event.getInventory().getTitle();
		if(InventoryName.contains(" : "))
			if(InventoryName.split(" : ")[0].compareTo("��ü ��ų ���") == 0&&
			((u.getType(player).compareTo("Job")==0&&u.getString(player, (byte)2) != null&&u.getString(player, (byte)3) != null)||
				u.getType(player).compareTo("Skill")==0&&u.getString(player, (byte)1)==null&&u.getString(player, (byte)2)==null
				&&u.getString(player, (byte)3)==null&&u.getString(player, (byte)4)==null))
				u.clearAll(player);
		if(InventoryName.contains("��ü") &&InventoryName.contains("����")==false&&InventoryName.contains("[MapleStory]")==false&&InventoryName.contains("[Mabinogi]")==false
				&&InventoryName.contains("��ų")==false&&InventoryName.contains("����")==false
				&&InventoryName.contains("��ü")==false)
		{
			boolean ChooseQuestGUI = Boolean.parseBoolean(ChatColor.stripColor(event.getInventory().getItem(53).getItemMeta().getLore().get(1)));
			if(ChooseQuestGUI==true)
				u.setString(player, (byte)1, null);
		}
		else if(InventoryName.contains("����") == true)
		{InventoryClose_Monster(event, InventoryName);return;	}
		else if(InventoryName.contains("����")||InventoryName.contains("�����"))
		{InventoryClose_Area(event, InventoryName);return;}
		else if(InventoryName.contains("NPC")== true)
		{InventoryClose_NPC(event, InventoryName);return;}
		else if(ChatColor.stripColor(InventoryName).compareTo("��ȯ")==0)
		{new GoldBigDragon_RPG.GUI.EquipGUI().InventoryClose_ExchangeGUI(event);return;}
		else if(InventoryName.contains("�ʽ���")== true)
		{InventoryClose_NewBie(event, InventoryName);return;}
		else if(InventoryName.contains("���� ��ǰ ����")== true)
		{new GoldBigDragon_RPG.GUI.GambleGUI().GambleDetailViewPackageGUI_Close(event);return;}
		else if(InventoryName.contains("���� ��� ����")== true)
		{new GoldBigDragon_RPG.GUI.GambleGUI().SlotMachineCoinGUI_Close(event);return;}
		else if(ChatColor.stripColor(InventoryName).equals("��Ȱ ������")||ChatColor.stripColor(InventoryName).equals("���� ������"))
		{new GoldBigDragon_RPG.GUI.OPBoxGUI().OPBoxGUI_RescueOrReviveClose(event); return;}
		if(player.isOp())
			if(player.isOp()==false)
				u.UserDataInit(player);
			else
				if(u.getType(player).compareTo("Quest")==0)
				{
					InventoryClose_Quest(event, InventoryName, player);
					return;
				}
		return;
	}

	private void InventoryClose_Quest(InventoryCloseEvent event, String InventoryName, Player player)
	{
		Object_UserData u = new Object_UserData();
		if(u.getString(player, (byte)1)!=null)
		{
			if(u.getString(player, (byte)1)!=null
				&&u.getString(player, (byte)2)!=null
				&&u.getString(player, (byte)3)!=null)
			{
				GoldBigDragon_RPG.Quest.QuestGUI QGUI = new GoldBigDragon_RPG.Quest.QuestGUI();
				if(InventoryName.contains("��ƾ�"))
				{
					QGUI.ItemAddInvnetoryClose(event);
    		    	u.setBoolean(player, (byte)1, false);
				}
				else if(InventoryName.contains("����"))
				{
					QGUI.PresentAddInvnetoryClose(event);
				}
			}
			else if(InventoryName.contains("��ü"))
			{
				u.clearAll(player);
			}
			return;
		}
		return;
	}

	private void InventoryClose_Monster(InventoryCloseEvent event, String InventoryName)
	{
	    GoldBigDragon_RPG.Monster.MonsterSpawn MC = new GoldBigDragon_RPG.Monster.MonsterSpawn();
		if(InventoryName.contains("���") == true)
			MC.InventorySetting(event);
		return;
	}
	
	private void InventoryClose_Area(InventoryCloseEvent event, String InventoryName)
	{
	    GoldBigDragon_RPG.ETC.Area A = new GoldBigDragon_RPG.ETC.Area();
		 if(InventoryName.contains(ChatColor.stripColor("�����")) == true)
			A.BlockItemSettingInventoryClose(event);
		 else if(InventoryName.contains(ChatColor.stripColor("���")) == true)
			A.FishingSettingInventoryClose(event);
		return;
	}
	
	private void InventoryClose_NPC(InventoryCloseEvent event, String InventoryName)
	{
	    GoldBigDragon_RPG.ETC.NPC NP = new GoldBigDragon_RPG.ETC.NPC();
		 if(InventoryName.contains(ChatColor.stripColor("��")) == true &&
				 InventoryName.contains(ChatColor.stripColor("����")) == true)
			 new GoldBigDragon_RPG.ETC.NPC().InventoryClose_NPC(event);
		 else if(ChatColor.stripColor(InventoryName).compareTo("[NPC] ���� �������� �÷� �ּ���")==0)
			 new GoldBigDragon_RPG.GUI.NPC_GUI().PresentInventoryClose(event);
		return;
	}

	private void InventoryClose_NewBie(InventoryCloseEvent event,String InventoryName)
	{
	    GoldBigDragon_RPG.GUI.NewBieGUI NGUI = new GoldBigDragon_RPG.GUI.NewBieGUI();
		 if(InventoryName.contains(ChatColor.stripColor("���̵�"))
			 ||InventoryName.contains(ChatColor.stripColor("����")))
			 NGUI.InventoryClose_NewBie(event);
		return;
	}
}
