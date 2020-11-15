package GoldBigDragon_RPG.GUI;

import java.util.Arrays;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.Inventory;

import GoldBigDragon_RPG.Main.ServerOption;
import GoldBigDragon_RPG.Skill.PlayerSkillGUI;
import GoldBigDragon_RPG.Util.YamlController;

public class OptionGUI extends GUIutil
{
	public void optionGUI(Player player)
	{
		Inventory inv = Bukkit.createInventory(null, 45, ChatColor.BLACK + "�ɼ�");

		Stack2(ChatColor.WHITE + "����", 397,3,1,Arrays.asList(ChatColor.GRAY + "������ Ȯ���մϴ�."), 0, inv);
		Stack2(ChatColor.WHITE + "��ų", 403,0,1,Arrays.asList(ChatColor.GRAY + "��ų�� Ȯ���մϴ�."), 9, inv);
		Stack2(ChatColor.WHITE + "����Ʈ", 358,0,1,Arrays.asList(ChatColor.GRAY + "���� �������� ����Ʈ�� Ȯ���մϴ�."), 18, inv);
		Stack2(ChatColor.WHITE + "�ɼ�", 160,4,1,Arrays.asList(ChatColor.GRAY + "��Ÿ ������ �մϴ�."), 27, inv);
		Stack2(ChatColor.WHITE + "��Ÿ", 354,0,1,Arrays.asList(ChatColor.GRAY + "��Ÿ ������ Ȯ���մϴ�."), 36, inv);
		
		Stack2(ChatColor.RED + " ", 66,0,1,Arrays.asList(""), 1, inv);
		Stack2(ChatColor.RED + " ", 66,0,1,Arrays.asList(""), 7, inv);
		Stack2(ChatColor.RED + " ", 66,0,1,Arrays.asList(""), 10, inv);
		Stack2(ChatColor.RED + " ", 66,0,1,Arrays.asList(""), 16, inv);
		Stack2(ChatColor.RED + " ", 66,0,1,Arrays.asList(""), 19, inv);
		Stack2(ChatColor.RED + " ", 66,0,1,Arrays.asList(""), 25, inv);
		Stack2(ChatColor.RED + " ", 66,0,1,Arrays.asList(""), 28, inv);
		Stack2(ChatColor.RED + " ", 66,0,1,Arrays.asList(""), 34, inv);
		Stack2(ChatColor.RED + " ", 66,0,1,Arrays.asList(""), 37, inv);
		Stack2(ChatColor.RED + " ", 66,0,1,Arrays.asList(""), 43, inv);

		if(GoldBigDragon_RPG.Main.ServerOption.PlayerList.get(player.getUniqueId().toString()).isAlert_EXPget()){Stack2(ChatColor.WHITE  + "" + ChatColor.BOLD + "��, ����ġ ȹ�� �˸�", 384,0,1,Arrays.asList(ChatColor.GREEN + "[Ȱ��ȭ]",ChatColor.GRAY+"���� ����ġ ȹ���� �˸��ϴ�."), 2, inv);}
			else{Stack2(ChatColor.WHITE  + "" + ChatColor.BOLD + "��, ����ġ ȹ�� �˸�", 166,0,1,Arrays.asList(ChatColor.RED + "[��Ȱ��ȭ]",ChatColor.GRAY+"���� ����ġ ȹ���� �˸��� �ʽ��ϴ�."), 2, inv);}
		if(GoldBigDragon_RPG.Main.ServerOption.PlayerList.get(player.getUniqueId().toString()).isAlert_ItemPickUp()){Stack2(ChatColor.WHITE  + "" + ChatColor.BOLD + "������ ȹ�� �˸�", 154,0,1,Arrays.asList(ChatColor.GREEN + "[Ȱ��ȭ]",ChatColor.GRAY+"������ ȹ���� �˸��ϴ�."), 3, inv);}
		else{Stack2(ChatColor.WHITE  + "" + ChatColor.BOLD + "������ ȹ�� �˸�", 166,0,1,Arrays.asList(ChatColor.RED + "[��Ȱ��ȭ]",ChatColor.GRAY+"������ ȹ���� �˸��� �ʽ��ϴ�."), 3, inv);}
		if(GoldBigDragon_RPG.Main.ServerOption.PlayerList.get(player.getUniqueId().toString()).isAlert_MobHealth()){Stack2(ChatColor.WHITE  + "" + ChatColor.BOLD + "���� �����", 381,0,1,Arrays.asList(ChatColor.GREEN + "[Ȱ��ȭ]",ChatColor.GRAY+"���� ���� ��Ȳ�� ���ϴ�."), 4, inv);}
		else{Stack2(ChatColor.WHITE  + "" + ChatColor.BOLD + "���� �����", 166,0,1,Arrays.asList(ChatColor.RED + "[��Ȱ��ȭ]",ChatColor.GRAY+"���� ���� ��Ȳ�� ���� �ʽ��ϴ�."), 4, inv);}
		if(GoldBigDragon_RPG.Main.ServerOption.PlayerList.get(player.getUniqueId().toString()).isAlert_Damage()){Stack2(ChatColor.WHITE  + "" + ChatColor.BOLD + "������ �˸�", 267,0,1,Arrays.asList(ChatColor.GREEN + "[Ȱ��ȭ]",ChatColor.GRAY+"������ ���� ���ظ� �˸��ϴ�."), 5, inv);}
		else{Stack2(ChatColor.WHITE  + "" + ChatColor.BOLD + "������ �˸�", 166,0,1,Arrays.asList(ChatColor.RED + "[��Ȱ��ȭ]",ChatColor.GRAY+"������ ���� ���ظ� �˸��� �ʽ��ϴ�."), 5, inv);}
		if(GoldBigDragon_RPG.Main.ServerOption.PlayerList.get(player.getUniqueId().toString()).isClickUse()){Stack2(ChatColor.WHITE  + "" + ChatColor.BOLD + "Ŭ���� ���", 373,8261,1,Arrays.asList(ChatColor.GREEN + "[Ȱ��ȭ]",ChatColor.GRAY+"�Һ� �������� Ŭ���� ����մϴ�."), 6, inv);}
		else{Stack2(ChatColor.WHITE  + "" + ChatColor.BOLD + "Ŭ���� ���", 166,0,1,Arrays.asList(ChatColor.RED + "[��Ȱ��ȭ]",ChatColor.GRAY+"�Һ� �������� ����Ű ó�� ����մϴ�."), 6, inv);}
		if(GoldBigDragon_RPG.Main.ServerOption.PlayerList.get(player.getUniqueId().toString()).isBgmOn()){Stack2(ChatColor.WHITE  + "" + ChatColor.BOLD + "BGM ���", 2256,0,1,Arrays.asList(ChatColor.GREEN + "[Ȱ��ȭ]",ChatColor.GRAY+"���� BGM�� ���� ��ŵ�ϴ�."), 14, inv);}
		else{Stack2(ChatColor.WHITE  + "" + ChatColor.BOLD + "BGM ���", 166,0,1,Arrays.asList(ChatColor.RED + "[��Ȱ��ȭ]",ChatColor.GRAY+"���� BGM�� ���� �ʽ��ϴ�."), 14, inv);}

		
		if(GoldBigDragon_RPG.Main.ServerOption.PlayerList.get(player.getUniqueId().toString()).isOption_EquipLook()){Stack2(ChatColor.WHITE  + "" + ChatColor.BOLD + "��� ����", 416,0,1,Arrays.asList(ChatColor.GREEN + "[���]",ChatColor.GRAY+"�ٸ� �÷��̾ �ڽ��� ���",ChatColor.GRAY+"������ �� �ֽ��ϴ�."), 11, inv);}
		else{Stack2(ChatColor.WHITE  + "" + ChatColor.BOLD + "��� ����", 166,0,1,Arrays.asList(ChatColor.RED + "[�����]",ChatColor.GRAY+"�ٸ� �÷��̾ �ڽ��� ���",ChatColor.GRAY+"������ �� �����ϴ�."), 11, inv);}

		switch(GoldBigDragon_RPG.Main.ServerOption.PlayerList.get(player.getUniqueId().toString()).getOption_ChattingType())
		{
		case 0:
			Stack2(ChatColor.WHITE  + "" + ChatColor.BOLD + "ä�� �ɼ�", 2264,0,1,Arrays.asList(ChatColor.WHITE + "[�Ϲ�]",ChatColor.GRAY+"�Ϲ����� ä���� �մϴ�."), 12, inv);
			break;
		case 1:
			Stack2(ChatColor.WHITE  + "" + ChatColor.BOLD + "ä�� �ɼ�", 2261,0,1,Arrays.asList(ChatColor.BLUE + "[��Ƽ]",ChatColor.GRAY+"��Ƽ ä���� �մϴ�."), 12, inv);
			break;
		case 2:
			Stack2(ChatColor.WHITE  + "" + ChatColor.BOLD + "ä�� �ɼ�", 2260,0,1,Arrays.asList(ChatColor.GREEN + "[����]",ChatColor.GRAY+"�� �ϰ� ������ ��Ⱑ ���� ������...."), 12, inv);
			break;
		case 3:
			Stack2(ChatColor.WHITE  + "" + ChatColor.BOLD + "ä�� �ɼ�", 2258,0,1,Arrays.asList(ChatColor.LIGHT_PURPLE + "[������]",ChatColor.GRAY+"������ ������ ä���� �մϴ�.",ChatColor.RED + "�� �Ϲ� ������ ����� �� �����ϴ�."), 12, inv);
			break;
		}
		if(GoldBigDragon_RPG.Main.ServerOption.PlayerList.get(player.getUniqueId().toString()).isOption_HotBarSound()){Stack2(ChatColor.WHITE  + "" + ChatColor.BOLD + "��� ��ȯ ����", 307,0,1,Arrays.asList(ChatColor.GREEN + "[���]",ChatColor.GRAY+"�ֹٸ� ������ �� ���� �Ҹ��� ���ϴ�."), 13, inv);}
		else{Stack2(ChatColor.WHITE  + "" + ChatColor.BOLD + "��� ��ȯ ����", 166,0,1,Arrays.asList(ChatColor.RED + "[�����]",ChatColor.GRAY+"�ֹٸ� �������� �Ҹ��� ���� �ʽ��ϴ�."), 13, inv);}

		Stack2(ChatColor.WHITE  + "" + ChatColor.BOLD + "�ݱ�", 324,0,1,Arrays.asList(ChatColor.GRAY + "â�� �ݽ��ϴ�."), 26, inv);

		player.openInventory(inv);
	}
	
	
	public void optionInventoryclick(InventoryClickEvent event)
	{
		YamlController YC = new YamlController(GoldBigDragon_RPG.Main.Main.plugin);
		StatsGUI SGUI = new StatsGUI();
		ETCGUI EGUI = new ETCGUI();
		GoldBigDragon_RPG.Effect.Sound s = new GoldBigDragon_RPG.Effect.Sound();
		event.setCancelled(true);
		Player player = (Player) event.getWhoClicked();
		
		switch (event.getSlot())
		{
			case 0: //����
				s.SP(player, Sound.ITEM_PICKUP, 0.8F, 1.0F);
				SGUI.StatusGUI(player);
				break;
			case 36://��Ÿ
				s.SP(player, Sound.ITEM_PICKUP, 0.8F, 1.0F);
				EGUI.ETCGUI_Main(player);
				break;
			case 9://��ų
				s.SP(player, Sound.ITEM_PICKUP, 0.8F, 1.0F);
				PlayerSkillGUI PGUI = new PlayerSkillGUI();
				PGUI.MainSkillsListGUI(player, (short) 0);
				break;
			case 18://����Ʈ
				GoldBigDragon_RPG.Quest.QuestGUI QGUI = new GoldBigDragon_RPG.Quest.QuestGUI();
				QGUI.MyQuestListGUI(player, (short) 0);
				s.SP(player, Sound.ITEM_PICKUP, 0.8F, 1.0F);
				break;
			case 26://�ݱ�
				s.SP(player, Sound.PISTON_RETRACT, 0.8F, 1.8F);
				player.closeInventory();
				break;
			case 2://����ġ ȹ�� �˸�
				if(GoldBigDragon_RPG.Main.ServerOption.PlayerList.get(player.getUniqueId().toString()).isAlert_EXPget()){GoldBigDragon_RPG.Main.ServerOption.PlayerList.get(player.getUniqueId().toString()).setAlert_EXPget(false);}
				else{GoldBigDragon_RPG.Main.ServerOption.PlayerList.get(player.getUniqueId().toString()).setAlert_EXPget(true);}
				s.SP(player, Sound.ITEM_PICKUP, 0.8F, 1.0F);
				optionGUI(player);
				break;
			case 3://������ ȹ�� �˸�
				if(GoldBigDragon_RPG.Main.ServerOption.PlayerList.get(player.getUniqueId().toString()).isAlert_ItemPickUp()){GoldBigDragon_RPG.Main.ServerOption.PlayerList.get(player.getUniqueId().toString()).setAlert_ItemPickUp(false);}
				else{GoldBigDragon_RPG.Main.ServerOption.PlayerList.get(player.getUniqueId().toString()).setAlert_ItemPickUp(true);}
				s.SP(player, Sound.ITEM_PICKUP, 0.8F, 1.0F);
				optionGUI(player);
				break;
			case 4://���� �����
				if(GoldBigDragon_RPG.Main.ServerOption.PlayerList.get(player.getUniqueId().toString()).isAlert_MobHealth()){GoldBigDragon_RPG.Main.ServerOption.PlayerList.get(player.getUniqueId().toString()).setAlert_MobHealth(false);}
				else{GoldBigDragon_RPG.Main.ServerOption.PlayerList.get(player.getUniqueId().toString()).setAlert_MobHealth(true);}
				s.SP(player, Sound.ITEM_PICKUP, 0.8F, 1.0F);
				optionGUI(player);
				break;
			case 5://������ �˸�
				if(GoldBigDragon_RPG.Main.ServerOption.PlayerList.get(player.getUniqueId().toString()).isAlert_Damage()){GoldBigDragon_RPG.Main.ServerOption.PlayerList.get(player.getUniqueId().toString()).setAlert_Damage(false);}
				else{GoldBigDragon_RPG.Main.ServerOption.PlayerList.get(player.getUniqueId().toString()).setAlert_Damage(true);}
				s.SP(player, Sound.ITEM_PICKUP, 0.8F, 1.0F);
				optionGUI(player);
				break;
			case 6://Ŭ���� ���
				if(GoldBigDragon_RPG.Main.ServerOption.PlayerList.get(player.getUniqueId().toString()).isClickUse()){GoldBigDragon_RPG.Main.ServerOption.PlayerList.get(player.getUniqueId().toString()).setClickUse(false);}
				else{GoldBigDragon_RPG.Main.ServerOption.PlayerList.get(player.getUniqueId().toString()).setClickUse(true);}
				s.SP(player, Sound.ITEM_PICKUP, 0.8F, 1.0F);
				optionGUI(player);
				break;
			case 11://��� ����
				if(GoldBigDragon_RPG.Main.ServerOption.PlayerList.get(player.getUniqueId().toString()).isOption_EquipLook()){GoldBigDragon_RPG.Main.ServerOption.PlayerList.get(player.getUniqueId().toString()).setOption_EquipLook(false);}
				else{GoldBigDragon_RPG.Main.ServerOption.PlayerList.get(player.getUniqueId().toString()).setOption_EquipLook(true);}
				s.SP(player, Sound.ITEM_PICKUP, 0.8F, 1.0F);
				optionGUI(player);
				break;
			case 12://ä�� �ɼ�
				if(GoldBigDragon_RPG.Main.ServerOption.PlayerList.get(player.getUniqueId().toString()).getOption_ChattingType() < 3)
					GoldBigDragon_RPG.Main.ServerOption.PlayerList.get(player.getUniqueId().toString()).setOption_ChattingType((byte) (GoldBigDragon_RPG.Main.ServerOption.PlayerList.get(player.getUniqueId().toString()).getOption_ChattingType()+1));
				else
					GoldBigDragon_RPG.Main.ServerOption.PlayerList.get(player.getUniqueId().toString()).setOption_ChattingType((byte) (0));
				s.SP(player, Sound.ITEM_PICKUP, 0.8F, 1.0F);
				optionGUI(player);
				break;
			case 13://��� ��ȯ ����
				if(GoldBigDragon_RPG.Main.ServerOption.PlayerList.get(player.getUniqueId().toString()).isOption_HotBarSound()){GoldBigDragon_RPG.Main.ServerOption.PlayerList.get(player.getUniqueId().toString()).setOption_HotBarSound(false);}
				else{GoldBigDragon_RPG.Main.ServerOption.PlayerList.get(player.getUniqueId().toString()).setOption_HotBarSound(true);}
				s.SP(player, Sound.ITEM_PICKUP, 0.8F, 1.0F);
				optionGUI(player);
				break;
			case 14://BGM ���
				if(GoldBigDragon_RPG.Main.ServerOption.PlayerList.get(player.getUniqueId().toString()).isBgmOn()){GoldBigDragon_RPG.Main.ServerOption.PlayerList.get(player.getUniqueId().toString()).setBgm(false);}
				else{GoldBigDragon_RPG.Main.ServerOption.PlayerList.get(player.getUniqueId().toString()).setBgm(true);}
				s.SP(player, Sound.ITEM_PICKUP, 0.8F, 1.0F);
				if(ServerOption.NoteBlockAPIAble)
					new OtherPlugins.NoteBlockAPIMain().Stop(player);
				optionGUI(player);
				break;
		}
		return;
	}
	
}
