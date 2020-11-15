package GoldBigDragon_RPG.Event;

import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryType;

public class InventoryClick
{
	public void InventoryClickRouter(InventoryClickEvent event, String InventoryName)
	{
		if(event.getClickedInventory() == null)
			return;
		if(event.getClickedInventory().getTitle().equalsIgnoreCase("container.inventory") == true)
		{
			if(InventoryName.compareTo("[NPC] ������ ��� ���� �ϼ���.")==0)
				new GoldBigDragon_RPG.GUI.NPC_GUI().ItemFixGuiClick(event);
			return;
		}
		if(InventoryName.compareTo("��ȯ")==0)
		{
			new GoldBigDragon_RPG.GUI.EquipGUI().ExchangeGUIclick(event);return;
	    }
		if (event.getCurrentItem() == null||event.getCurrentItem().getType() == Material.AIR||event.getCurrentItem().getAmount() == 0)
		{return;}
		
		if(InventoryName.compareTo("����")==0)
		{
		    if(event.getClickedInventory().getType() != InventoryType.CHEST)
		    {
		    	event.setCancelled(true);
		    	return;
		    }
		    new GoldBigDragon_RPG.GUI.StatsGUI().StatusInventoryclick(event); 
			return;
		}
		else if(InventoryName.compareTo("���� ��� ����")==0)
		{
			new GoldBigDragon_RPG.Monster.MonsterSpawn().ArmorGUIClick(event);return;
	    }
		else if(InventoryName.compareTo("��� ����")==0)
	    {
		    new GoldBigDragon_RPG.GUI.EquipGUI().optionInventoryclick(event);return;
	    }
		else if(InventoryName.compareTo("�ɼ�")==0)
	    {
			new GoldBigDragon_RPG.GUI.OptionGUI().optionInventoryclick(event);return;
	    }
		else if(InventoryName.compareTo("�ش� ����� ĳ�� ���� ������")==0)
	    {
		    new GoldBigDragon_RPG.GUI.AreaGUI().AreaBlockItemSettingGUIClick(event);return;
	    }
		else if(InventoryName.compareTo("���� ������")==0||InventoryName.compareTo("��Ȱ ������")==0)
	    {
		    new GoldBigDragon_RPG.GUI.OPBoxGUI().OPBoxGUI_RescueOrReviveClick(event);return;
	    }
		else if(InventoryName.contains("��Ȱ"))
	    {
			new GoldBigDragon_RPG.GUI.DeathGUI().ReviveSelectClick(event);return;
	    }
		else if(InventoryName.compareTo("���� �ӽ�")==0)
	    {
			new GoldBigDragon_RPG.GUI.GambleGUI().SlotMachine_PlayGUI_Click(event);return;
	    }

	    GoldBigDragon_RPG.GUI.ETCGUI EGUI = new GoldBigDragon_RPG.GUI.ETCGUI();
	    GoldBigDragon_RPG.Quest.QuestGUI QGUI = new GoldBigDragon_RPG.Quest.QuestGUI();
	    GoldBigDragon_RPG.GUI.JobGUI JGUI = new GoldBigDragon_RPG.GUI.JobGUI();
	    GoldBigDragon_RPG.Skill.OPBoxSkillGUI SKGUI = new GoldBigDragon_RPG.Skill.OPBoxSkillGUI();
	    GoldBigDragon_RPG.Skill.PlayerSkillGUI PSKGUI = new GoldBigDragon_RPG.Skill.PlayerSkillGUI();
		switch(InventoryName)
		{
			case "������ ���":
				PSKGUI.AddQuickBarGUIClick(event);
			break;
			case "�ý��� ����":
				JGUI.ChooseSystemGUIClick(event);
			break;
			case "��� ��� �Ͻðڽ��ϱ�?":
				QGUI.KeepGoingClick(event);
				break;
			case "���� ���":
			case "ä�� �ؾ� �� ��� ���":
			case "��� �ؾ� �� ���� ���":
				QGUI.ShowItemGUIInventoryClick(event); return;
			case "��Ÿ" : 
				EGUI.ETCInventoryclick(event);return;
			case "���̵�" : 
				EGUI.ETCInventoryclick(event); return;
			case "������Ʈ �߰�":
				QGUI.ObjectAddInventoryClick(event);return;
			default :
				if(InventoryName.contains("NPC"))
				{IC_NPC(event, InventoryName);return;}
				else if(InventoryName.contains("��Ƽ"))
				{new GoldBigDragon_RPG.Party.PartyGUI().PartyGUIClickRouter(event, InventoryName);; return;}
				else if(InventoryName.contains("������"))
				{IC_Item(event, InventoryName);return;}
				else if(InventoryName.contains("����Ʈ"))
				{new GoldBigDragon_RPG.Quest.QuestGUI().QuestGUIClickRouter(event, InventoryName);;}
				else if(InventoryName.contains("��ϵ�"))
				{JGUI.AddedSkillsListGUIClick(event);return;}
				else if(InventoryName.contains("[MapleStory]"))
				{IC_MapleStory(event, InventoryName);return;}
				else if(InventoryName.contains("[Mabinogi]"))
				{IC_Mabinogi(event, InventoryName);return;}
				else if(InventoryName.contains("��ų"))
				{IC_Skill(event, InventoryName);return;}
				else if(InventoryName.contains("��ũ"))
				{SKGUI.SkillRankOptionGUIClick(event);return;}
				else if(InventoryName.contains("������"))
				{PSKGUI.MapleStory_MainSkillsListGUIClick(event);return;}
				else if(InventoryName.contains("ī�װ�"))
				{PSKGUI.Mabinogi_MainSkillsListGUIClick(event);return;}
				else if(InventoryName.contains("������"))
				{IC_OP(event, InventoryName);return;}
				else if(InventoryName.contains("�̺�Ʈ"))
				{IC_Event(event, InventoryName);return;}
				else if(InventoryName.contains("����"))
				{IC_Area(event, InventoryName);return;}
				else if(InventoryName.contains("������"))
				{IC_Upgrade(event, InventoryName);return;}
				else if(InventoryName.contains("�ʽ���"))
				{IC_NewBie(event, InventoryName);return;}
				else if(InventoryName.contains("����"))
				{IC_Monster(event, InventoryName);return;}
				else if(InventoryName.contains("����"))
				{IC_World(event, InventoryName);return;}
				else if(InventoryName.contains("����"))
				{IC_Warp(event, InventoryName);return;}
				else if(InventoryName.contains("��������"))
				{new OtherPlugins.SpellMain().ShowAllMaigcGUIClick(event);return;}
				else if(InventoryName.contains("ģ��"))
				{IC_Friend(event, InventoryName);return;}
				else if(InventoryName.contains("�׺�"))
				{IC_Navi(event, InventoryName);return;}
				else if(InventoryName.contains("����"))
				{IC_Gamble(event, InventoryName);return;}
				else if(InventoryName.contains("��ü"))
				{IC_Structure(event, InventoryName);return;}
				return;
		}
		return;
	}
	
	private void IC_NPC(InventoryClickEvent event, String InventoryName)
	{
	    GoldBigDragon_RPG.GUI.NPC_GUI NPGUI = new GoldBigDragon_RPG.GUI.NPC_GUI();
    	if(InventoryName.compareTo("NPC ���� ����")==0)
    		NPGUI.NPCJobClick(event, ChatColor.stripColor(event.getInventory().getItem(18).getItemMeta().getLore().get(1)));
    	else if(InventoryName.contains("NPC"))
	    {
        	if(InventoryName.contains("[NPC]"))
    	    {
    			if(event.getInventory().getSize() <= 9)
    				NPGUI.NPCclickMain(event, InventoryName.split("C] ")[1]);
    			else if(event.getInventory().getSize() <= 27)
    				NPGUI.NPCclickMain(event, InventoryName.split("C] ")[1]);
    			else if(event.getInventory().getSize() <= 54)
    				NPGUI.NPCclickMain(event,InventoryName.split("C] ")[1]);	
    	    }
        	else if(InventoryName.contains("����"))
			{
    			if(InventoryName.contains("����"))
    				NPGUI.WarpMainGUIClick(event);
    			else if(InventoryName.contains("���"))
    				NPGUI.WarperGUIClick(event);
			}
        	else if(InventoryName.contains("����"))
        	{
    			if(InventoryName.contains("����"))
    				NPGUI.UpgraderGUIClick(event);
    			else
    				NPGUI.SelectUpgradeRecipeGUIClick(event);
    				
        	}
        	else if(InventoryName.contains("����ĥ"))
				NPGUI.AddAbleSkillsGUIClick(event);
        	else if(InventoryName.contains("��"))
				NPGUI.RuneEquipGUIClick(event);
        	else if(InventoryName.contains("���"))
        	{
        		if(InventoryName.contains("����"))
            		NPGUI.TalkSettingGUIClick(event);
        		else
        			NPGUI.TalkGUIClick(event);
        	}
	    }
		return;
	}

	private void IC_MapleStory(InventoryClickEvent event, String InventoryName)
	{
	    GoldBigDragon_RPG.GUI.JobGUI JGUI = new GoldBigDragon_RPG.GUI.JobGUI();

		if(InventoryName.contains("��ü"))
			JGUI.MapleStory_ChooseJobClick(event);
		else if(InventoryName.contains("����"))
			JGUI.MapleStory_JobSettingClick(event);
		return;
	}

	private void IC_Mabinogi(InventoryClickEvent event, String InventoryName)
	{
	    GoldBigDragon_RPG.GUI.JobGUI JGUI = new GoldBigDragon_RPG.GUI.JobGUI();

		if(InventoryName.contains("��ü"))
			JGUI.Mabinogi_ChooseCategoryClick(event);
		else if(InventoryName.contains("����"))
			JGUI.MapleStory_JobSettingClick(event);
		else if(InventoryName.contains("����"))
			JGUI.Mabinogi_SkillSettingClick(event);
		return;
	}

	private void IC_Skill(InventoryClickEvent event, String InventoryName)
	{
	    GoldBigDragon_RPG.Skill.OPBoxSkillGUI SKGUI = new GoldBigDragon_RPG.Skill.OPBoxSkillGUI();
	    GoldBigDragon_RPG.Skill.PlayerSkillGUI PSKGUI = new GoldBigDragon_RPG.Skill.PlayerSkillGUI();

		if(InventoryName.contains("��ü"))
			SKGUI.AllSkillsGUIClick(event);
		else if(InventoryName.contains("����"))
			SKGUI.IndividualSkillOptionGUIClick(event);
		else if(InventoryName.contains("����"))
			PSKGUI.SkillListGUIClick(event);
	    else if(InventoryName.contains("����"))
	    {
	    	GoldBigDragon_RPG.CustomItem.UseableItemGUI UIGUI = new GoldBigDragon_RPG.CustomItem.UseableItemGUI();
	    	UIGUI.SelectSkillGUIClick(event);
	    }
		return;
	}

	private void IC_OP(InventoryClickEvent event, String InventoryName)
	{
	    GoldBigDragon_RPG.GUI.OPBoxGUI OPGUI = new GoldBigDragon_RPG.GUI.OPBoxGUI();

	    if(InventoryName.contains("���̵�"))
	    	OPGUI.OPBoxGuideInventoryclick(event);
	    else if(InventoryName.contains("����"))
	    	OPGUI.OPBoxGUIInventoryclick(event);
	    else if(InventoryName.contains("�ɼ�"))
	    	OPGUI.OPBoxGUI_SettingInventoryClick(event);
	    else if(InventoryName.contains("��������"))
	    	OPGUI.OPBoxGUI_BroadCastClick(event);
	    else if(InventoryName.contains("����"))
	    	OPGUI.OPBoxGUI_StatChangeClick(event);
	    else if(InventoryName.contains("ȭ��"))
	    	OPGUI.OPBoxGUI_MoneyClick(event);
	    else if(InventoryName.contains("���"))
	    	OPGUI.OPBoxGUI_DeathClick(event);
	    return;
	}

	private void IC_Item(InventoryClickEvent event, String InventoryName)
	{
	    GoldBigDragon_RPG.Quest.QuestGUI QGUI = new GoldBigDragon_RPG.Quest.QuestGUI();
	    GoldBigDragon_RPG.CustomItem.ItemGUI IGUI = new GoldBigDragon_RPG.CustomItem.ItemGUI();

	    if(InventoryName.contains("�Ҹ�")==true)
	    {
	    	GoldBigDragon_RPG.CustomItem.UseableItemGUI UIGUI = new GoldBigDragon_RPG.CustomItem.UseableItemGUI();
			if(InventoryName.contains("���"))
		    	UIGUI.UseableItemListGUIClick(event);
			else if(InventoryName.contains("Ÿ��"))
		    	UIGUI.ChooseUseableItemTypeGUIClick(event);
			else if(InventoryName.contains("��"))
		    	UIGUI.NewUseableItemGUIclick(event);
	    }
	    else
	    {
		    if(InventoryName.compareTo("��ƾ� �� ������ ���")==0||InventoryName.compareTo("������ ������ ���")==0)
		    {
				if(event.getSlot() == 8)
					event.getWhoClicked().closeInventory();
		    }
		    else  if(InventoryName.compareTo("���� ������ ���")==0)
				QGUI.SettingPresentClick(event);
		    else  if(InventoryName.contains("��"))
				IGUI.NewItemGUIclick(event);
		    else  if(InventoryName.compareTo("��ƾ� �� ������ ���")==0)
				QGUI.ShowItemGUIInventoryClick(event);
			else if(InventoryName.contains("���"))
				IGUI.ItemListInventoryclick(event);
			else if(InventoryName.contains("����"))
				new GoldBigDragon_RPG.CustomItem.ItemGUI().JobGUIClick(event);
	    }
		return;
	}

	private void IC_Area(InventoryClickEvent event, String InventoryName)
	{
	    GoldBigDragon_RPG.GUI.AreaGUI AGUI = new GoldBigDragon_RPG.GUI.AreaGUI();
	    if(InventoryName.contains("����"))
			AGUI.AreaGUIInventoryclick(event);
	    else if(InventoryName.contains("��ü"))
	    	AGUI.AreaListGUIClick(event);
	    else if(InventoryName.contains("����"))
	    {
		    if(InventoryName.contains("��ü"))
		    	AGUI.AreaMonsterSettingGUIClick(event);
		    else if(InventoryName.contains("����"))
		    	AGUI.AreaAddMonsterListGUIClick(event);
		    else if(InventoryName.contains("����"))
		    	AGUI.AreaAddMonsterSpawnRuleGUIClick(event);
		    else if(InventoryName.contains("Ư��"))
		    	AGUI.AreaSpawnSpecialMonsterListGUIClick(event);
	    }
	    else if(InventoryName.contains("Ư��ǰ"))
	    	AGUI.AreaBlockSettingGUIClick(event);
	    else if(InventoryName.contains("���"))
	    	AGUI.AreaFishSettingGUIClick(event);
	    else if(InventoryName.contains("�����"))
	    	AGUI.AreaMusicSettingGUIClick(event);
		return;
	}

	private void IC_Upgrade(InventoryClickEvent event, String InventoryName)
	{
	    GoldBigDragon_RPG.GUI.UpGradeGUI UGUI = new GoldBigDragon_RPG.GUI.UpGradeGUI();
	    if(InventoryName.contains("���"))
	    	UGUI.UpgradeRecipeGUIClick(event);
	    else if(InventoryName.contains("����"))
	    	UGUI.UpgradeRecipeSettingGUIClick(event);
		return;
	}	
	
	private void IC_NewBie(InventoryClickEvent event, String InventoryName)
	{
	    GoldBigDragon_RPG.GUI.NewBieGUI NGUI = new GoldBigDragon_RPG.GUI.NewBieGUI();
	    if(InventoryName.contains("�ɼ�"))
	    	NGUI.NewBieGUIMainInventoryclick(event);
	    else if(InventoryName.contains("����")||InventoryName.contains("���̵�"))
	    	NGUI.NewBieSupportItemGUIInventoryclick(event);
	    else if(InventoryName.contains("�⺻��"))
	    	NGUI.NewBieQuestGUIInventoryclick(event);
		return;
	}
	
	private void IC_Monster(InventoryClickEvent event, String InventoryName)
	{
	    GoldBigDragon_RPG.Monster.MonsterGUI MGUI = new GoldBigDragon_RPG.Monster.MonsterGUI();
	    if(InventoryName.contains("���"))
	    	MGUI.MonsterListGUIClick(event);
	    else if(InventoryName.contains("����"))
	    	MGUI.MonsterOptionSettingGUIClick(event);
	    else if(InventoryName.contains("����"))
	    	MGUI.MonsterPotionGUIClick(event);
		return;
	}	
	
	private void IC_World(InventoryClickEvent event, String InventoryName)
	{
	    GoldBigDragon_RPG.GUI.WorldCreateGUI WGUI = new GoldBigDragon_RPG.GUI.WorldCreateGUI();
	    if(InventoryName.contains("����"))
	    	WGUI.WorldCreateGUIClick(event);
		return;
	}	

	private void IC_Warp(InventoryClickEvent event, String InventoryName)
	{
	    GoldBigDragon_RPG.GUI.WarpGUI WGUI = new GoldBigDragon_RPG.GUI.WarpGUI();
	    if(InventoryName.contains("���"))
	    	WGUI.WarpListGUIInventoryclick(event);
		return;
	}
	
	private void IC_Event(InventoryClickEvent event, String InventoryName)
	{
	    GoldBigDragon_RPG.GUI.EventGUI EGUI = new GoldBigDragon_RPG.GUI.EventGUI();
	    if(InventoryName.contains("����"))
	    	EGUI.AllPlayerGiveEventGUIclick(event);
		else if(InventoryName.contains("����"))
			EGUI.EventGUIInventoryclick(event);
		return;
	}		

	private void IC_Friend(InventoryClickEvent event, String InventoryName)
	{
	    GoldBigDragon_RPG.GUI.ETCGUI EGUI = new GoldBigDragon_RPG.GUI.ETCGUI();
	    if(InventoryName.contains("���"))
	    	EGUI.FriendsGUIclick(event);
	    if(InventoryName.contains("��û"))
	    	EGUI.WaittingFriendsGUIclick(event);
		return;
	}

	private void IC_Navi(InventoryClickEvent event, String InventoryName)
	{
	    GoldBigDragon_RPG.GUI.NavigationGUI NGUI = new GoldBigDragon_RPG.GUI.NavigationGUI();
	    if(InventoryName.contains("���"))
	    	NGUI.NavigationListGUIClick(event);
	    else if(InventoryName.contains("����"))
	    	NGUI.NavigationOptionGUIClick(event);
	    else if(InventoryName.contains("���"))
	    	NGUI.UseNavigationGUIClick(event);
		return;
	}

	private void IC_Gamble(InventoryClickEvent event, String InventoryName)
	{
	    GoldBigDragon_RPG.GUI.GambleGUI GGUI = new GoldBigDragon_RPG.GUI.GambleGUI();
	    if(InventoryName.contains("����"))
	    	GGUI.GambleMainGUI_Click(event);
	    else if(InventoryName.contains("��ǰ"))
	    {
	    	if(InventoryName.contains("���"))
	    		GGUI.GamblePresentGUI_Click(event);
	    	else if(InventoryName.contains("����"))
	    		GGUI.GambleDetailViewPackageGUI_Click(event);
	    }
	    else if(InventoryName.contains("���"))
	    {
	    	if(InventoryName.contains("���"))
	    		GGUI.SlotMachine_MainGUI_Click(event);
	    	else if(InventoryName.contains("����"))
	    		GGUI.SlotMachine_DetailGUI_Click(event);
	    	else if(InventoryName.contains("����"))
	    		GGUI.SlotMachineCoinGUI_Click(event);
	    }
		return;
	}
	
	private void IC_Structure(InventoryClickEvent event, String InventoryName)
	{
	    GoldBigDragon_RPG.Structure.StructureGUI SGUI = new GoldBigDragon_RPG.Structure.StructureGUI();
	    if(InventoryName.contains("��ü"))
	    	SGUI.StructureListGUIClick(event);
	    else if(InventoryName.contains("Ÿ��"))
	    	SGUI.SelectStructureTypeGUIClick(event);
	    else if(InventoryName.contains("����"))
	    	SGUI.SelectStructureDirectionGUIClick(event);
		return;
	}
}