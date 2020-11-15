package GoldBigDragon_RPG.Quest;

import java.util.Arrays;
import java.util.Collection;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.SkullMeta;
import org.bukkit.potion.PotionEffectType;

import GoldBigDragon_RPG.GUI.GUIutil;
import GoldBigDragon_RPG.Main.Object_UserData;
import GoldBigDragon_RPG.ServerTick.ServerTickMain;
import GoldBigDragon_RPG.Util.YamlController;
import GoldBigDragon_RPG.Util.YamlManager;

public class QuestGUI extends GUIutil
{
	public void MyQuestListGUI(Player player, short page)
	{
		YamlController YC = new YamlController(Main.instance);
		YamlManager QuestList  = YC.getNewConfig("Quest/QuestList.yml");
		YamlManager PlayerQuestList  = YC.getNewConfig("Quest/PlayerData/"+player.getUniqueId()+".yml");
		
		Inventory inv = Bukkit.createInventory(null, 54, ChatColor.BLACK + "����Ʈ ��� : " + (page+1));

		Object[] a = PlayerQuestList.getConfigurationSection("Started").getKeys(false).toArray();
		String QuestType = "";
		int ItemID = 0;
		byte ItemAmount = 1;
		byte loc=0;
		for(int count = page*45; count < a.length;count++)
		{
			if(QuestList.contains(a[count].toString())==false)
			{
				PlayerQuestList.removeKey("Started."+a[count].toString());
				PlayerQuestList.saveConfig();
			}
			else
			{
				switch(QuestList.getString(a[count].toString() + ".Type"))
				{
				case "N" :
					QuestType = "[�Ϲ�]";
					ItemID = 340;
					break;
				case "R" :
					QuestType = "[�ݺ�]";
					ItemID = 386;
					break;
				case "D" :
					QuestType = "[����]";
					ItemID = 403;
					break;
				case "W" :
					QuestType = "[�ְ�]";
					ItemID = 403;
					ItemAmount = 7;
					break;
				case "M" :
					QuestType = "[����]";
					ItemID = 403;
					ItemAmount = 31;
					break;
				}
				if(count > a.length || loc >= 45) break;

				switch(QuestList.getString(a[count].toString() + ".FlowChart."+PlayerQuestList.getInt("Started."+a[count].toString()+".Flow")+".Type"))
				{
				case "Nevigation":
					Stack2(ChatColor.WHITE + "" + ChatColor.BOLD + a[count].toString(), ItemID,0,ItemAmount,Arrays.asList(ChatColor.WHITE+"ȭ��ǥ�� ������.",""), loc, inv);
					break;
				case "Choice":
					Stack2(ChatColor.WHITE + "" + ChatColor.BOLD + a[count].toString(), ItemID,0,ItemAmount,Arrays.asList(ChatColor.WHITE+"�ϰ���� ���� ��������.","",ChatColor.YELLOW+"[��Ŭ���� ������ Ȯ��.]"), loc, inv);
					break;
				case "Script" :
					Stack2(ChatColor.WHITE + "" + ChatColor.BOLD + a[count].toString(), ItemID,0,ItemAmount,Arrays.asList(ChatColor.YELLOW+QuestList.getString(a[count].toString() + ".FlowChart."+PlayerQuestList.getInt("Started."+a[count].toString()+".Flow")+".NPCname")+ChatColor.WHITE+"�� ��ȭ�� �� ����."), loc, inv);
					break;
				case "PScript" :
					Stack2(ChatColor.WHITE + "" + ChatColor.BOLD + a[count].toString(), ItemID,0,ItemAmount,Arrays.asList(ChatColor.YELLOW+"[��Ŭ���� ���� Ȯ��]"), loc, inv);
					break;
				case "Visit" :
					YamlManager AreaList = YC.getNewConfig("Area/AreaList.yml");
					String AreaWorld = AreaList.getString(QuestList.getString(a[count].toString() + ".FlowChart."+PlayerQuestList.getInt("Started."+a[count].toString()+".Flow")+".AreaName")+".World");
					String AreaName = AreaList.getString(QuestList.getString(a[count].toString() + ".FlowChart."+PlayerQuestList.getInt("Started."+a[count].toString()+".Flow")+".AreaName")+".Name");
					int AreaX = AreaList.getInt(QuestList.getString(a[count].toString() + ".FlowChart."+PlayerQuestList.getInt("Started."+a[count].toString()+".Flow")+".AreaName")+".SpawnLocation.X");
					short AreaY = (short) AreaList.getInt(QuestList.getString(a[count].toString() + ".FlowChart."+PlayerQuestList.getInt("Started."+a[count].toString()+".Flow")+".AreaName")+".SpawnLocation.Y");
					int AreaZ = AreaList.getInt(QuestList.getString(a[count].toString() + ".FlowChart."+PlayerQuestList.getInt("Started."+a[count].toString()+".Flow")+".AreaName")+".SpawnLocation.Z");
					Stack2(ChatColor.WHITE + "" + ChatColor.BOLD + a[count].toString(), ItemID,0,ItemAmount,Arrays.asList(ChatColor.YELLOW+AreaName+ChatColor.WHITE+" ������ �湮����."
							,ChatColor.YELLOW + "���� : "+ChatColor.WHITE+AreaWorld,ChatColor.YELLOW + "X ��ǥ : "+ChatColor.WHITE+""+AreaX,ChatColor.YELLOW + "Y ��ǥ : "+ChatColor.WHITE+""+AreaY,ChatColor.YELLOW + "Z ��ǥ : "+ChatColor.WHITE+""+AreaZ), loc, inv);
					break;
				case "Talk" :
					Stack2(ChatColor.WHITE + "" + ChatColor.BOLD + a[count].toString(), ItemID,0,ItemAmount,Arrays.asList(ChatColor.YELLOW+QuestList.getString(a[count].toString() + ".FlowChart."+PlayerQuestList.getInt("Started."+a[count].toString()+".Flow")+".TargetNPCname")+ChatColor.WHITE+"���� ���� �ɾ� ����."), loc, inv);
					break;
				case "Give" :
					Stack2(ChatColor.WHITE + "" + ChatColor.BOLD + a[count].toString(), ItemID,0,ItemAmount,Arrays.asList(ChatColor.YELLOW+QuestList.getString(a[count].toString() + ".FlowChart."+PlayerQuestList.getInt("Started."+a[count].toString()+".Flow")+".TargetNPCname")+ChatColor.WHITE+"�� ��Ź��",ChatColor.WHITE+"��ǰ�� ��������.","",ChatColor.YELLOW+"[��Ŭ���� ���� ǰ�� Ȯ��.]"), loc, inv);
					break;
				case "Hunt":
					Stack2(ChatColor.WHITE + "" + ChatColor.BOLD +a[count].toString(), ItemID,0,ItemAmount,Arrays.asList(ChatColor.WHITE+"��ǥ ����� óġ����.","",ChatColor.YELLOW+"[��Ŭ���� óġ ��� Ȯ��]"), loc, inv);
					break;
				case "Harvest":
					Stack2(ChatColor.WHITE + "" + ChatColor.BOLD +a[count].toString(), ItemID,0,ItemAmount,Arrays.asList(ChatColor.WHITE+"����� ä������.","",ChatColor.YELLOW+"[��Ŭ���� ä�� ��� Ȯ��]"), loc, inv);
					break;
				case "Present" :
					Stack2(ChatColor.WHITE + "" + ChatColor.BOLD + a[count].toString(), ItemID,0,ItemAmount,Arrays.asList(ChatColor.YELLOW+QuestList.getString(a[count].toString() + ".FlowChart."+PlayerQuestList.getInt("Started."+a[count].toString()+".Flow")+".TargetNPCname")+ChatColor.WHITE+"����",ChatColor.WHITE+"������ ����.","",ChatColor.YELLOW+"[��Ŭ���� ���� Ȯ��.]"), loc, inv);
					break;
				}
				loc++;
			}
		}
		
		if(a.length-(page*44)>45)
			Stack2(ChatColor.WHITE + "" + ChatColor.BOLD + "���� ������", 323,0,1,Arrays.asList(ChatColor.GRAY + "���� �������� �̵� �մϴ�."), 50, inv);
		if(page!=0)
			Stack2(ChatColor.WHITE + "" + ChatColor.BOLD + "���� ������", 323,0,1,Arrays.asList(ChatColor.GRAY + "���� �������� �̵� �մϴ�."), 48, inv);

		Stack2(ChatColor.WHITE + "" + ChatColor.BOLD + "���� ���", 323,0,1,Arrays.asList(ChatColor.GRAY + "���� ȭ������ ���ư��ϴ�."), 45, inv);
		Stack2(ChatColor.WHITE + "" + ChatColor.BOLD + "�ݱ�", 324,0,1,Arrays.asList(ChatColor.GRAY + "â�� �ݽ��ϴ�."), 53, inv);
		player.openInventory(inv);
	}
	
	public void AllOfQuestListGUI(Player player, short page,boolean ChoosePrevQuest)
	{
		YamlController YC = new YamlController(GoldBigDragon_RPG.Main.Main.plugin);
		YamlManager QuestList  = YC.getNewConfig("Quest/QuestList.yml");
		
		Inventory inv = Bukkit.createInventory(null, 54, ChatColor.BLACK + "��ü ����Ʈ ��� : " + (page+1));

		Object[] a = QuestList.getKeys().toArray();
		
		byte loc=0;
		for(int count = page*45; count < a.length;count++)
		{
			String QuestName = a[count].toString();
			Object[] QuestFlow= QuestList.getConfigurationSection(QuestName+".FlowChart").getKeys(false).toArray();
			if(count > a.length || loc >= 45) break;
			if(ChoosePrevQuest == false)
			{
				switch(QuestList.getString(a[count].toString() + ".Type"))
				{
				case "N" :
					Stack2(ChatColor.WHITE + "" + ChatColor.BOLD + QuestName, 340,0,1,Arrays.asList(ChatColor.WHITE+"����Ʈ ���� ��� : "+QuestFlow.length+"��",ChatColor.DARK_AQUA+"����Ʈ Ÿ�� : �Ϲ� ����Ʈ","",ChatColor.YELLOW+"[��Ŭ���� ���� ������ �մϴ�.]",ChatColor.RED+"[Shift + ��Ŭ���� �����˴ϴ�.]"), loc, inv);
					break;
				case "R" :
					Stack2(ChatColor.WHITE + "" + ChatColor.BOLD + QuestName, 386,0,1,Arrays.asList(ChatColor.WHITE+"����Ʈ ���� ��� : "+QuestFlow.length+"��",ChatColor.DARK_AQUA+"����Ʈ Ÿ�� : �ݺ� ����Ʈ","",ChatColor.YELLOW+"[��Ŭ���� ���� ������ �մϴ�.]",ChatColor.RED+"[Shift + ��Ŭ���� �����˴ϴ�.]"), loc, inv);
					break;
				case "D" :
					Stack2(ChatColor.WHITE + "" + ChatColor.BOLD + QuestName, 403,0,1,Arrays.asList(ChatColor.WHITE+"����Ʈ ���� ��� : "+QuestFlow.length+"��",ChatColor.DARK_AQUA+"����Ʈ Ÿ�� : ���� ����Ʈ","",ChatColor.YELLOW+"[��Ŭ���� ���� ������ �մϴ�.]",ChatColor.RED+"[Shift + ��Ŭ���� �����˴ϴ�.]"), loc, inv);
					break;
				case "W" :
					Stack2(ChatColor.WHITE + "" + ChatColor.BOLD + QuestName, 403,0,7,Arrays.asList(ChatColor.WHITE+"����Ʈ ���� ��� : "+QuestFlow.length+"��",ChatColor.DARK_AQUA+"����Ʈ Ÿ�� : ���� ����Ʈ","",ChatColor.YELLOW+"[��Ŭ���� ���� ������ �մϴ�.]",ChatColor.RED+"[Shift + ��Ŭ���� �����˴ϴ�.]"), loc, inv);
					break;
				case "M" :
					Stack2(ChatColor.WHITE + "" + ChatColor.BOLD + QuestName, 403,0,31,Arrays.asList(ChatColor.WHITE+"����Ʈ ���� ��� : "+QuestFlow.length+"��",ChatColor.DARK_AQUA+"����Ʈ Ÿ�� : �Ѵ� ����Ʈ","",ChatColor.YELLOW+"[��Ŭ���� ���� ������ �մϴ�.]",ChatColor.RED+"[Shift + ��Ŭ���� �����˴ϴ�.]"), loc, inv);
					break;
				}
			}
			else
			{
				switch(QuestList.getString(a[count].toString() + ".Type"))
				{
				case "N" :
					Stack2(ChatColor.WHITE + "" + ChatColor.BOLD + QuestName, 340,0,1,Arrays.asList(ChatColor.WHITE+"����Ʈ ���� ��� : "+QuestFlow.length+"��",ChatColor.DARK_AQUA+"����Ʈ Ÿ�� : �Ϲ� ����Ʈ",""), loc, inv);
					break;
				case "R" :
					Stack2(ChatColor.WHITE + "" + ChatColor.BOLD + QuestName, 386,0,1,Arrays.asList(ChatColor.WHITE+"����Ʈ ���� ��� : "+QuestFlow.length+"��",ChatColor.DARK_AQUA+"����Ʈ Ÿ�� : �ݺ� ����Ʈ",""), loc, inv);
					break;
				case "D" :
					Stack2(ChatColor.WHITE + "" + ChatColor.BOLD + QuestName, 403,0,1,Arrays.asList(ChatColor.WHITE+"����Ʈ ���� ��� : "+QuestFlow.length+"��",ChatColor.DARK_AQUA+"����Ʈ Ÿ�� : ���� ����Ʈ",""), loc, inv);
					break;
				case "W" :
					Stack2(ChatColor.WHITE + "" + ChatColor.BOLD + QuestName, 403,0,7,Arrays.asList(ChatColor.WHITE+"����Ʈ ���� ��� : "+QuestFlow.length+"��",ChatColor.DARK_AQUA+"����Ʈ Ÿ�� : ���� ����Ʈ",""), loc, inv);
					break;
				case "M" :
					Stack2(ChatColor.WHITE + "" + ChatColor.BOLD + QuestName, 403,0,31,Arrays.asList(ChatColor.WHITE+"����Ʈ ���� ��� : "+QuestFlow.length+"��",ChatColor.DARK_AQUA+"����Ʈ Ÿ�� : �Ѵ� ����Ʈ",""), loc, inv);
					break;
				}
			}
			loc++;
		}
		
		if(a.length-(page*44)>45)
			Stack2(ChatColor.WHITE + "" + ChatColor.BOLD + "���� ������", 323,0,1,Arrays.asList(ChatColor.GRAY + "���� �������� �̵� �մϴ�."), 50, inv);
		if(page!=0)
			Stack2(ChatColor.WHITE + "" + ChatColor.BOLD + "���� ������", 323,0,1,Arrays.asList(ChatColor.GRAY + "���� �������� �̵� �մϴ�."), 48, inv);
		if(ChoosePrevQuest == false)
			Stack2(ChatColor.WHITE + "" + ChatColor.BOLD + "�� ����Ʈ", 386,0,1,Arrays.asList(ChatColor.GRAY + "���ο� ����Ʈ�� �����մϴ�."), 49, inv);

		Stack2(ChatColor.WHITE + "" + ChatColor.BOLD + "���� ���", 323,0,1,Arrays.asList(ChatColor.GRAY + "���� ȭ������ ���ư��ϴ�."), 45, inv);
		Stack2(ChatColor.WHITE + "" + ChatColor.BOLD + "�ݱ�", 324,0,1,Arrays.asList(ChatColor.GRAY + "â�� �ݽ��ϴ�.",ChatColor.BLACK+""+ChoosePrevQuest), 53, inv);
		player.openInventory(inv);
	}
	
	public void FixQuestGUI(Player player, short page, String QuestName)
	{
		YamlController YC = new YamlController(GoldBigDragon_RPG.Main.Main.plugin);
		YamlManager QuestList  = YC.getNewConfig("Quest/QuestList.yml");
		
		Inventory inv = Bukkit.createInventory(null, 54, ChatColor.BLACK +"����Ʈ �帧�� : " + (page+1));
		Object[] a = QuestList.getConfigurationSection(QuestName+".FlowChart").getKeys(false).toArray();
		
		byte loc=0;
		for(int count = page*45; count < a.length;count++)
		{
			if(count > a.length || loc >= 45) break;
			switch(QuestList.getString(QuestName+".FlowChart."+count+".Type"))
			{
				case "Cal":
				switch(QuestList.getInt(QuestName+".FlowChart."+count+".Comparison"))
				{
				case 1:
					Stack2(ChatColor.WHITE + "" + ChatColor.BOLD +""+count, 137,0,1,Arrays.asList(ChatColor.WHITE+"Ÿ�� : ���","",ChatColor.DARK_AQUA+"[     ��� ��     ]",ChatColor.DARK_AQUA+"�÷��̾� ���� �� "+QuestList.getInt(QuestName+".FlowChart."+count+".Value"),"",ChatColor.RED + "[Shift + ��Ŭ���� �����˴ϴ�.]"), loc, inv);
					break;
				case 2:
					Stack2(ChatColor.WHITE + "" + ChatColor.BOLD +""+count, 137,0,1,Arrays.asList(ChatColor.WHITE+"Ÿ�� : ���","",ChatColor.DARK_AQUA+"[     ��� ��     ]",ChatColor.DARK_AQUA+"�÷��̾� ���� �� "+QuestList.getInt(QuestName+".FlowChart."+count+".Value"),"",ChatColor.RED + "[Shift + ��Ŭ���� �����˴ϴ�.]"), loc, inv);
					break;
				case 3:
					Stack2(ChatColor.WHITE + "" + ChatColor.BOLD +""+count, 137,0,1,Arrays.asList(ChatColor.WHITE+"Ÿ�� : ���","",ChatColor.DARK_AQUA+"[     ��� ��     ]",ChatColor.DARK_AQUA+"�÷��̾� ���� �� "+QuestList.getInt(QuestName+".FlowChart."+count+".Value"),"",ChatColor.RED + "[Shift + ��Ŭ���� �����˴ϴ�.]"), loc, inv);
					break;
				case 4:
					Stack2(ChatColor.WHITE + "" + ChatColor.BOLD +""+count, 137,0,1,Arrays.asList(ChatColor.WHITE+"Ÿ�� : ���","",ChatColor.DARK_AQUA+"[     ��� ��     ]",ChatColor.DARK_AQUA+"�÷��̾� ���� �� "+QuestList.getInt(QuestName+".FlowChart."+count+".Value"),"",ChatColor.RED + "[Shift + ��Ŭ���� �����˴ϴ�.]"), loc, inv);
					break;
				case 5:
					Stack2(ChatColor.WHITE + "" + ChatColor.BOLD +""+count, 137,0,1,Arrays.asList(ChatColor.WHITE+"Ÿ�� : ���","",ChatColor.DARK_AQUA+"[     ��� ��     ]",ChatColor.DARK_AQUA+"�÷��̾� ���� �� "+QuestList.getInt(QuestName+".FlowChart."+count+".Value"),"",ChatColor.RED + "[Shift + ��Ŭ���� �����˴ϴ�.]"), loc, inv);
					break;
				}
				break;
			case "IF":
				switch(QuestList.getInt(QuestName+".FlowChart."+count+".Comparison"))
				{
				case 1:
					Stack2(ChatColor.WHITE + "" + ChatColor.BOLD +""+count, 184,0,1,Arrays.asList(ChatColor.WHITE+"Ÿ�� : IF","",ChatColor.DARK_AQUA+"[     �� ��     ]",ChatColor.DARK_AQUA+"�÷��̾� ���� == "+QuestList.getInt(QuestName+".FlowChart."+count+".Value"),"",ChatColor.RED + "[Shift + ��Ŭ���� �����˴ϴ�.]"), loc, inv);
					break;
				case 2:
					Stack2(ChatColor.WHITE + "" + ChatColor.BOLD +""+count, 184,0,1,Arrays.asList(ChatColor.WHITE+"Ÿ�� : IF","",ChatColor.DARK_AQUA+"[     �� ��     ]",ChatColor.DARK_AQUA+"�÷��̾� ���� != "+QuestList.getInt(QuestName+".FlowChart."+count+".Value"),"",ChatColor.RED + "[Shift + ��Ŭ���� �����˴ϴ�.]"), loc, inv);
					break;
				case 3:
					Stack2(ChatColor.WHITE + "" + ChatColor.BOLD +""+count, 184,0,1,Arrays.asList(ChatColor.WHITE+"Ÿ�� : IF","",ChatColor.DARK_AQUA+"[     �� ��     ]",ChatColor.DARK_AQUA+"�÷��̾� ���� > "+QuestList.getInt(QuestName+".FlowChart."+count+".Value"),"",ChatColor.RED + "[Shift + ��Ŭ���� �����˴ϴ�.]"), loc, inv);
					break;
				case 4:
					Stack2(ChatColor.WHITE + "" + ChatColor.BOLD +""+count, 184,0,1,Arrays.asList(ChatColor.WHITE+"Ÿ�� : IF","",ChatColor.DARK_AQUA+"[     �� ��     ]",ChatColor.DARK_AQUA+"�÷��̾� ���� < "+QuestList.getInt(QuestName+".FlowChart."+count+".Value"),"",ChatColor.RED + "[Shift + ��Ŭ���� �����˴ϴ�.]"), loc, inv);
					break;
				case 5:
					Stack2(ChatColor.WHITE + "" + ChatColor.BOLD +""+count, 184,0,1,Arrays.asList(ChatColor.WHITE+"Ÿ�� : IF","",ChatColor.DARK_AQUA+"[     �� ��     ]",ChatColor.DARK_AQUA+"�÷��̾� ���� >= "+QuestList.getInt(QuestName+".FlowChart."+count+".Value"),"",ChatColor.RED + "[Shift + ��Ŭ���� �����˴ϴ�.]"), loc, inv);
					break;
				case 6:
					Stack2(ChatColor.WHITE + "" + ChatColor.BOLD +""+count, 184,0,1,Arrays.asList(ChatColor.WHITE+"Ÿ�� : IF","",ChatColor.DARK_AQUA+"[     �� ��     ]",ChatColor.DARK_AQUA+"�÷��̾� ���� <= "+QuestList.getInt(QuestName+".FlowChart."+count+".Value"),"",ChatColor.RED + "[Shift + ��Ŭ���� �����˴ϴ�.]"), loc, inv);
					break;
				case 7:
					Stack2(ChatColor.WHITE + "" + ChatColor.BOLD +""+count, 184,0,1,Arrays.asList(ChatColor.WHITE+"Ÿ�� : IF","",ChatColor.DARK_AQUA+"[     �� ��     ]",ChatColor.DARK_AQUA+""+QuestList.getInt(QuestName+".FlowChart."+count+".Min")+" <= �÷��̾� ���� <= "+QuestList.getInt(QuestName+".FlowChart."+count+".Max"),"",ChatColor.RED + "[Shift + ��Ŭ���� �����˴ϴ�.]"), loc, inv);
					break;
				}
				break;
			case "QuestFail":
				Stack2(ChatColor.WHITE + "" + ChatColor.BOLD +""+count, 166,0,1,Arrays.asList(ChatColor.WHITE+"Ÿ�� : ����Ʈ ����","",ChatColor.RED + "[Shift + ��Ŭ���� �����˴ϴ�.]"), loc, inv);
				break;
			case "QuestReset":
				Stack2(ChatColor.WHITE + "" + ChatColor.BOLD +""+count, 395,0,1,Arrays.asList(ChatColor.WHITE+"Ÿ�� : ����Ʈ �ʱ�ȭ","",ChatColor.RED + "[Shift + ��Ŭ���� �����˴ϴ�.]"), loc, inv);
				break;
			case "ELSE":
				Stack2(ChatColor.WHITE + "" + ChatColor.BOLD +""+count, 167,0,1,Arrays.asList(ChatColor.WHITE+"Ÿ�� : ELSE","",ChatColor.RED + "[Shift + ��Ŭ���� �����˴ϴ�.]"), loc, inv);
				break;
			case "ENDIF":
				Stack2(ChatColor.WHITE + "" + ChatColor.BOLD +""+count, 191,0,1,Arrays.asList(ChatColor.WHITE+"Ÿ�� : ENDIF","",ChatColor.RED + "[Shift + ��Ŭ���� �����˴ϴ�.]"), loc, inv);
				break;
			case "VarChange":
				Stack2(ChatColor.WHITE + "" + ChatColor.BOLD +""+count, 143,0,1,Arrays.asList(ChatColor.WHITE+"Ÿ�� : ���� ����",ChatColor.WHITE+"���� �� : " + QuestList.getInt(QuestName+".FlowChart."+count+".Value") ,"",ChatColor.RED + "[Shift + ��Ŭ���� �����˴ϴ�.]"), loc, inv);
				break;
			case "Choice":
				int button = QuestList.getConfigurationSection(QuestName+".FlowChart."+count+".Choice").getKeys(false).size();
				Stack2(ChatColor.WHITE + "" + ChatColor.BOLD +""+count, 72,0,button,Arrays.asList(ChatColor.WHITE+"Ÿ�� : ����",ChatColor.WHITE+"������ ���� : " +button+"��" ,"",ChatColor.YELLOW+"[��Ŭ���� ����â Ȯ��]","",ChatColor.RED + "[Shift + ��Ŭ���� �����˴ϴ�.]"), loc, inv);
				break;
			case "Nevigation":
			{
				String UTC = QuestList.getString(QuestName+".FlowChart."+count+".NeviUTC");
				YamlManager NavigationConfig =YC.getNewConfig("Navigation/NavigationList.yml");
				if(NavigationConfig.contains(UTC))
				{
					String NaviName = NavigationConfig.getString(UTC+".Name");
					String world = NavigationConfig.getString(UTC+".world");
					int x = NavigationConfig.getInt(UTC+".x");
					short y = (short) NavigationConfig.getInt(UTC+".y");
					int z = NavigationConfig.getInt(UTC+".z");
					int Time = NavigationConfig.getInt(UTC+".time");
					short sensitive = (short) NavigationConfig.getInt(UTC+".sensitive");
					byte ShowArrow = (byte) NavigationConfig.getInt(UTC+".ShowArrow");
					
					String TimeS = ChatColor.DARK_AQUA+"<������ �� ���� ����>";
					String sensitiveS = ChatColor.BLUE+"<�ݰ� "+sensitive+"��� �̳��� �������� ����>";
					String ShowArrowS = ChatColor.DARK_AQUA+"<�⺻ ȭ��ǥ ���>";
					if(Time >= 0)
						TimeS = ChatColor.DARK_AQUA+"<"+Time+"�� ���� ����>";
					switch(ShowArrow)
					{
					default:
						ShowArrowS = ChatColor.DARK_AQUA+"<�⺻ ȭ��ǥ ���>";
						break;
					}
					Stack2(ChatColor.WHITE + "" + ChatColor.BOLD + count, 395,0,1,Arrays.asList(
					ChatColor.YELLOW+""+ChatColor.BOLD+NaviName,"",
					ChatColor.BLUE+"[���� ����]",ChatColor.BLUE+"���� : "+ChatColor.WHITE+world,
					ChatColor.BLUE+"��ǥ : " + ChatColor.WHITE+x+","+y+","+z,sensitiveS,"",
					ChatColor.DARK_AQUA+"[��Ÿ �ɼ�]",TimeS,ShowArrowS,""
					,ChatColor.RED+"[Shift + ��Ŭ���� �����˴ϴ�.]"), loc, inv);
				}
				else
					Stack2(ChatColor.WHITE + "" + ChatColor.BOLD + count, 166,0,1,Arrays.asList(ChatColor.RED+"[�׺���̼��� ã�� �� �����ϴ�!]","",ChatColor.RED+"[Shift + ��Ŭ���� �����˴ϴ�.]"),loc,inv);
			}
				break;
			case "Whisper":
			{
				String script3 = ChatColor.WHITE+"Ÿ�� : �Ӹ�%enter%%enter%"+QuestList.getString(QuestName+".FlowChart."+count+".Message")+"%enter% %enter%"+ChatColor.RED + "[Shift + ��Ŭ���� �����˴ϴ�.]";
				String[] scriptA3 = script3.split("%enter%");
				for(byte counter = 0; counter < scriptA3.length; counter++)
					scriptA3[counter] = ChatColor.WHITE + scriptA3[counter];
				Stack2(ChatColor.WHITE + "" + ChatColor.BOLD +""+count, 421,0,1,Arrays.asList(scriptA3), loc, inv);
			}
			break;
			case "BroadCast":
			{
				String script3 = ChatColor.WHITE+"Ÿ�� : ��ü%enter%%enter%"+QuestList.getString(QuestName+".FlowChart."+count+".Message")+"%enter% %enter%"+ChatColor.RED + "[Shift + ��Ŭ���� �����˴ϴ�.]";
				String[] scriptA3 = script3.split("%enter%");
				for(byte counter = 0; counter < scriptA3.length; counter++)
					scriptA3[counter] = ChatColor.WHITE + scriptA3[counter];
				Stack2(ChatColor.WHITE + "" + ChatColor.BOLD +""+count, 138,0,1,Arrays.asList(scriptA3), loc, inv);
			}
			break;
			case "Script":
				String script = ChatColor.WHITE+"Ÿ�� : ���%enter%"+ChatColor.WHITE+"���ϴ� ��ü : "+QuestList.getString(QuestName+".FlowChart."+count+".NPCname")+"%enter%%enter%"+QuestList.getString(QuestName+".FlowChart."+count+".Script")+"%enter% %enter%"+ChatColor.RED + "[Shift + ��Ŭ���� �����˴ϴ�.]";
				String[] scriptA = script.split("%enter%");
				for(byte counter = 0; counter < scriptA.length; counter++)
					scriptA[counter] = ChatColor.WHITE + scriptA[counter];
			Stack2(ChatColor.WHITE + "" + ChatColor.BOLD +""+count, 323,0,1,Arrays.asList(scriptA), loc, inv);
			break;
			case "PScript":
				String script3 = ChatColor.WHITE+"Ÿ�� : ���%enter%"+ChatColor.WHITE+"���ϴ� ��ü : �÷��̾�%enter%%enter%"+QuestList.getString(QuestName+".FlowChart."+count+".Message")+"%enter% %enter%"+ChatColor.RED + "[Shift + ��Ŭ���� �����˴ϴ�.]";
				String[] scriptA3 = script3.split("%enter%");
				for(byte counter = 0; counter < scriptA3.length; counter++)
					scriptA3[counter] = ChatColor.WHITE + scriptA3[counter];
			Stack2(ChatColor.WHITE + "" + ChatColor.BOLD +""+count, 323,0,1,Arrays.asList(scriptA3), loc, inv);
			break;
			case "Visit":
			Stack2(ChatColor.WHITE + "" + ChatColor.BOLD +""+count, 345,0,1,Arrays.asList(ChatColor.WHITE+"Ÿ�� : �湮",ChatColor.WHITE+"�湮 ���� : "+QuestList.getString(QuestName+".FlowChart."+count+".AreaName"),"",ChatColor.RED + "[Shift + ��Ŭ���� �����˴ϴ�.]"), loc, inv);
				break;
			case "Give":
				String script2 = ChatColor.WHITE+"Ÿ�� : ����%enter%"+ChatColor.WHITE+"���� ��� : "+ChatColor.YELLOW+QuestList.getString(QuestName+".FlowChart."+count+".TargetNPCname")+"%enter%"+ChatColor.WHITE+"NPC�� UUID%enter%"+ChatColor.DARK_AQUA + QuestList.getString(QuestName+".FlowChart."+count+".TargetNPCuuid")+"%enter%%enter%"+ChatColor.YELLOW+"[��Ŭ���� ���� ǰ�� Ȯ��]"+"%enter%"+ChatColor.RED + "[Shift + ��Ŭ���� �����˴ϴ�.]";
				String[] scriptB = script2.split("%enter%");
				for(byte counter = 0; counter < scriptB.length; counter++)
					scriptB[counter] = ChatColor.WHITE + scriptB[counter];
			Stack2(ChatColor.WHITE + "" + ChatColor.BOLD +""+count, 388,0,1,Arrays.asList(scriptB), loc, inv);
				break;
			case "Hunt":
				Stack2(ChatColor.WHITE + "" + ChatColor.BOLD +""+count, 267,0,1,Arrays.asList(ChatColor.WHITE+"Ÿ�� : ���","",ChatColor.YELLOW+"[��Ŭ���� óġ ��� Ȯ��]","",ChatColor.RED + "[Shift + ��Ŭ���� �����˴ϴ�.]"), loc, inv);
				break;
			case "Talk":
				Stack2(ChatColor.WHITE + "" + ChatColor.BOLD +""+count, 397,3,1,Arrays.asList(ChatColor.WHITE+"Ÿ�� : ��ȭ","",ChatColor.WHITE+"������ �� NPC �̸�","",ChatColor.YELLOW+QuestList.getString(QuestName+".FlowChart."+count+".TargetNPCname"),"",ChatColor.WHITE+"NPC�� UUID","",ChatColor.DARK_AQUA + QuestList.getString(QuestName+".FlowChart."+count+".TargetNPCuuid"),"",ChatColor.RED + "[Shift + ��Ŭ���� �����˴ϴ�.]"), loc, inv);
				break;
			case "Present":
				Stack2(ChatColor.WHITE + "" + ChatColor.BOLD +""+count, 54,0,1,Arrays.asList(ChatColor.WHITE+"Ÿ�� : ����",ChatColor.WHITE+"���� ��� : "+ChatColor.YELLOW+QuestList.getString(QuestName+".FlowChart."+count+".TargetNPCname"),ChatColor.WHITE+"NPC�� UUID","",ChatColor.DARK_AQUA + QuestList.getString(QuestName+".FlowChart."+count+".TargetNPCuuid"),"","",ChatColor.YELLOW+"[��Ŭ���� ���� Ȯ��]",ChatColor.RED + "[Shift + ��Ŭ���� �����˴ϴ�.]"), loc, inv);
				break;
			case "TelePort":
			Stack2(ChatColor.WHITE + "" + ChatColor.BOLD +""+count, 368,0,1,Arrays.asList(ChatColor.WHITE+"Ÿ�� : �̵�","",ChatColor.WHITE+"���� : "+QuestList.getString(QuestName+".FlowChart."+count+".World"),ChatColor.WHITE+"��ǥ : " + (int)QuestList.getDouble(QuestName+".FlowChart."+count+".X")+","+ (int)QuestList.getDouble(QuestName+".FlowChart."+count+".Y")+","+ (int)QuestList.getDouble(QuestName+".FlowChart."+count+".Z"),"",ChatColor.RED + "[Shift + ��Ŭ���� �����˴ϴ�.]"), loc, inv);
				break;
			case "Harvest":
				Stack2(ChatColor.WHITE + "" + ChatColor.BOLD +""+count, 56,0,1,Arrays.asList(ChatColor.WHITE+"Ÿ�� : ä��","",ChatColor.YELLOW+"[��Ŭ���� ä�� ��� Ȯ��]","",ChatColor.RED + "[Shift + ��Ŭ���� �����˴ϴ�.]"), loc, inv);
				break;
			case "BlockPlace":
				Stack2(ChatColor.WHITE + "" + ChatColor.BOLD +""+count, 152,0,1,Arrays.asList(ChatColor.WHITE+"Ÿ�� : ��� ��ġ",ChatColor.WHITE+"���� : "+QuestList.getString(QuestName+".FlowChart."+count+".World"),ChatColor.WHITE+"��ǥ : " + (int)QuestList.getDouble(QuestName+".FlowChart."+count+".X")+","+ (int)QuestList.getDouble(QuestName+".FlowChart."+count+".Y")+","+ (int)QuestList.getDouble(QuestName+".FlowChart."+count+".Z"),ChatColor.WHITE+"��� Ÿ�� : " + QuestList.getInt(QuestName+".FlowChart."+count+".ID")+":"+ QuestList.getInt(QuestName+".FlowChart."+count+".DATA"),"",ChatColor.RED + "[Shift + ��Ŭ���� �����˴ϴ�.]"), loc, inv);
				break;
			}
			loc++;
		}
		
		if(a.length-(page*44)>45)
			Stack2(ChatColor.WHITE + "" + ChatColor.BOLD + "���� ������", 323,0,1,Arrays.asList(ChatColor.GRAY + "���� �������� �̵� �մϴ�."), 50, inv);
		if(page!=0)
			Stack2(ChatColor.WHITE + "" + ChatColor.BOLD + "���� ������", 323,0,1,Arrays.asList(ChatColor.GRAY + "���� �������� �̵� �մϴ�."), 48, inv);

		Stack2(ChatColor.WHITE + "" + ChatColor.BOLD + "�� ������Ʈ �߰�", 2,0,1,Arrays.asList(ChatColor.GRAY + "���ο� ������Ʈ�� �߰��մϴ�."), 49, inv);
		Stack2(ChatColor.WHITE + "" + ChatColor.BOLD + "���� ���", 323,0,1,Arrays.asList(ChatColor.GRAY + "���� ȭ������ ���ư��ϴ�."), 45, inv);
		Stack2(ChatColor.WHITE + "" + ChatColor.BOLD + "�ݱ�", 324,0,1,Arrays.asList(ChatColor.GRAY + "â�� �ݽ��ϴ�.",ChatColor.BLACK + ChatColor.stripColor(QuestName)), 53, inv);
		player.openInventory(inv);
	}

	public void SelectObjectPage(Player player, byte page, String QuestName)
	{
		Inventory inv = Bukkit.createInventory(null, 54, ChatColor.BLACK + "������Ʈ �߰�");

		Stack2(ChatColor.WHITE + "" + ChatColor.BOLD + "���", 323,0,1,Arrays.asList(ChatColor.GRAY + "��ȭâ�� ����, �ۼ���",ChatColor.GRAY+"��ũ��Ʈ�� �������� ���ϴ�.",ChatColor.GRAY+"(ȭ�� : NPC)"), 0, inv);
		Stack2(ChatColor.WHITE + "" + ChatColor.BOLD + "����", 323,0,1,Arrays.asList(ChatColor.GRAY + "��ȭâ�� ����, �ۼ���",ChatColor.GRAY+"��ũ��Ʈ�� �������� ���ϴ�.",ChatColor.GRAY+"(ȭ�� : ����)"), 1, inv);
		Stack2(ChatColor.WHITE + "" + ChatColor.BOLD + "�湮", 345,0,1,Arrays.asList(ChatColor.GRAY + "�÷��̾�� Ư�� ������",ChatColor.GRAY+"�湮�ϴ� ����Ʈ�� �ݴϴ�."), 2, inv);
		Stack2(ChatColor.WHITE + "" + ChatColor.BOLD + "����", 388,0,1,Arrays.asList(ChatColor.GRAY + "�÷��̾ Ư�� ��������",ChatColor.GRAY+"NPC���� ����ϴ� ����Ʈ�� �ݴϴ�."), 3, inv);
		Stack2(ChatColor.WHITE + "" + ChatColor.BOLD + "���", 267,0,1,Arrays.asList(ChatColor.GRAY + "�÷��̾�� Ư�� ���͸�",ChatColor.GRAY+"����ϴ� ����Ʈ�� �ݴϴ�."), 4, inv);
		Stack2(ChatColor.WHITE + "" + ChatColor.BOLD + "��ȭ", 397,3,1,Arrays.asList(ChatColor.GRAY + "�÷��̾�� Ư�� NPC����",ChatColor.GRAY+"���� �Ŵ� ����Ʈ�� �ݴϴ�."), 5, inv);
		Stack2(ChatColor.WHITE + "" + ChatColor.BOLD + "����", 54,0,1,Arrays.asList(ChatColor.GRAY + "�÷��̾�� ������ �ݴϴ�."), 6, inv);
		Stack2(ChatColor.WHITE + "" + ChatColor.BOLD + "�̵�", 368,0,1,Arrays.asList(ChatColor.GRAY + "�÷��̾ Ư�� ��ġ��",ChatColor.GRAY+"�̵� ��ŵ�ϴ�."), 7, inv);
		Stack2(ChatColor.WHITE + "" + ChatColor.BOLD + "ä��", 56,0,1,Arrays.asList(ChatColor.GRAY + "�÷��̾�� Ư�� �����",ChatColor.GRAY+"ä���ϴ� ����Ʈ�� �ݴϴ�."), 8, inv);
		Stack2(ChatColor.WHITE + "" + ChatColor.BOLD + "���", 152,0,1,Arrays.asList(ChatColor.GRAY + "Ư�� ��ġ�� ������",ChatColor.GRAY+"����� �����մϴ�."), 9, inv);
		Stack2(ChatColor.WHITE + "" + ChatColor.BOLD + "�Ҹ�"+ ChatColor.RED + "[��� �Ұ�]", 84,0,1,Arrays.asList(ChatColor.GRAY + "Ư�� ��ġ�� �Ҹ��� ���� �մϴ�."), 10, inv);
		Stack2(ChatColor.WHITE + "" + ChatColor.BOLD + "�Ӹ�", 421,0,1,Arrays.asList(ChatColor.GRAY + "�÷��̾��� ä��â�� �޽����� ��Ÿ���ϴ�."), 11, inv);
		Stack2(ChatColor.WHITE + "" + ChatColor.BOLD + "��ü", 138,0,1,Arrays.asList(ChatColor.GRAY + "���� ��ü�� �޽����� ��Ÿ���ϴ�."), 12, inv);
		Stack2(ChatColor.WHITE + "" + ChatColor.BOLD + "�׺�", 358,0,1,Arrays.asList(ChatColor.GRAY + "�÷��̾�� �׺���̼��� �۵� ��ŵ�ϴ�."), 13, inv);
		

		Stack2(ChatColor.YELLOW + "" + ChatColor.BOLD + "����", 72,0,1,Arrays.asList(ChatColor.GRAY + "�÷��̾ ���ϴ� �����",ChatColor.GRAY+"���� �ϵ��� �մϴ�.",ChatColor.GRAY+"������ ��信 ����",ChatColor.GRAY+"�ٸ� �������� ���� �� �ֽ��ϴ�."), 36, inv);
		Stack2(ChatColor.YELLOW + "" + ChatColor.BOLD + "����", 143,0,1,Arrays.asList(ChatColor.GRAY + "�÷��̾��� ������ ������ �����մϴ�."), 37, inv);
		Stack2(ChatColor.YELLOW + "" + ChatColor.BOLD + "���", 137,0,1,Arrays.asList(ChatColor.GRAY + "�÷��̾��� ������ ������",ChatColor.GRAY+"����Ͽ� �����մϴ�."), 38, inv);
		Stack2(ChatColor.YELLOW + "" + ChatColor.BOLD + "IF", 184,0,1,Arrays.asList(ChatColor.GRAY + "�÷��̾��� ���� �������� Ȯ���Ͽ�",ChatColor.GRAY+"���� ���� ������ ���",ChatColor.GRAY+"IF�� ENDIFȤ�� IF�� ELSE",ChatColor.GRAY+"������ ������ �����ϰ� �˴ϴ�.","",ChatColor.RED+"[�ݵ�� IF�� ���� = ENDIF�� ����]"), 39, inv);
		Stack2(ChatColor.YELLOW + "" + ChatColor.BOLD + "ELSE", 167,0,1,Arrays.asList(ChatColor.GRAY + "�÷��̾��� ���� ��������",ChatColor.GRAY+"IF ���� ���� ���� ���",ChatColor.GRAY+"ELSE�� ENDIF ������ ������",ChatColor.GRAY+"�����ϰ� �˴ϴ�.",""), 40, inv);
		Stack2(ChatColor.YELLOW + "" + ChatColor.BOLD + "ENDIF", 191,0,1,Arrays.asList(ChatColor.GRAY + "IF�� �� �κ��� ��Ÿ���ϴ�.","",ChatColor.RED+"[�ݵ�� IF�� ���� = ENDIF�� ����]"), 41, inv);
		
		Stack2(ChatColor.RED + "" + ChatColor.BOLD + "����Ʈ �ʱ�ȭ", 395,0,1,Arrays.asList(ChatColor.GRAY + "����Ʈ�� �߰��� ���� �մϴ�.",ChatColor.GREEN+"����Ʈ�� �ٽ� ���� �� �ֽ��ϴ�."), 43, inv);
		Stack2(ChatColor.RED + "" + ChatColor.BOLD + "����Ʈ ����", 166,0,1,Arrays.asList(ChatColor.GRAY + "����Ʈ�� �߰��� ���� �մϴ�.",ChatColor.GRAY+"�Ϲ� ����Ʈ�� ��� �÷��̾��",ChatColor.RED+"����Ʈ�� �ٽ� ���� �� �����ϴ�."), 44, inv);
		
		Stack2(ChatColor.WHITE + "" + ChatColor.BOLD + "���� ���", 323,0,1,Arrays.asList(ChatColor.GRAY + "���� ȭ������ ���ư��ϴ�."), 45, inv);
		Stack2(ChatColor.WHITE + "" + ChatColor.BOLD + "�ݱ�", 324,0,1,Arrays.asList(ChatColor.GRAY + "â�� �ݽ��ϴ�.",ChatColor.BLACK + ChatColor.stripColor(QuestName)), 53, inv);
		player.openInventory(inv);
	}

	public void QuestTypeRouter(Player player,String QuestName)
	{
		Inventory inv = Bukkit.createInventory(null, 27, ChatColor.BLACK + "[����Ʈ]");
		Stack2(ChatColor.BLACK + ChatColor.stripColor(QuestName), 160,8,1,null, 19, inv);

		GoldBigDragon_RPG.Effect.Potion p = new GoldBigDragon_RPG.Effect.Potion();
		GoldBigDragon_RPG.Effect.Sound s = new GoldBigDragon_RPG.Effect.Sound();
		
		YamlController YC = new YamlController(GoldBigDragon_RPG.Main.Main.plugin);
		YamlManager QuestList  = YC.getNewConfig("Quest/QuestList.yml");
		YamlManager PlayerQuestList  = YC.getNewConfig("Quest/PlayerData/"+player.getUniqueId()+".yml");
		
		String QuestType = QuestList.getString(QuestName+".FlowChart."+PlayerQuestList.getInt("Started."+QuestName+".Flow")+".Type");
		short FlowChart = (short) PlayerQuestList.getInt("Started."+QuestName+".Flow");
		String NPCname = QuestList.getString(QuestName+".FlowChart."+FlowChart+".NPCname");
		if(QuestType == null)
		{
			GoldBigDragon_RPG.Util.ETC ETC = new GoldBigDragon_RPG.Util.ETC();
			YamlManager Config = YC.getNewConfig("config.yml");
			String message = Config.getString("Quest.ClearMessage").replace("%QuestName%", QuestName);
			player.sendMessage(message);
			PlayerQuestList.set("Ended."+QuestName+".ClearTime", ETC.getNowUTC());
			PlayerQuestList.removeKey("Started."+QuestName+".Flow");
			PlayerQuestList.removeKey("Started."+QuestName+".Type");
			PlayerQuestList.removeKey("Started."+QuestName);
			PlayerQuestList.saveConfig();
			YamlManager PlayerVarList  = YC.getNewConfig("Quest/PlayerVar/"+player.getUniqueId()+".yml");
			PlayerVarList.removeKey(QuestName);
			PlayerVarList.saveConfig();
			player.closeInventory();
			s.SP(player, Sound.NOTE_PLING, 1.0F, 1.8F);
			
		}
		else
		{
			PlayerQuestList.set("Started."+QuestName+".Type",QuestList.getString(QuestName+".FlowChart." + FlowChart+".Type") );
			PlayerQuestList.saveConfig();
			switch(QuestType)
			{
				case "Cal":
				{
					YamlManager PlayerVarList  = YC.getNewConfig("Quest/PlayerVar/"+player.getUniqueId()+".yml");
					int PlayerValue = PlayerVarList.getInt(QuestName);
					int SideValue = QuestList.getInt(QuestName+".FlowChart."+FlowChart+".Value");
					int total = 0;
					switch(QuestList.getInt(QuestName+".FlowChart."+FlowChart+".Comparison"))
					{
					case 1:
						total = PlayerValue+SideValue;
						break;
					case 2:
						total = PlayerValue-SideValue;
						break;
					case 3:
						total = PlayerValue*SideValue;
						break;
					case 4:
						total = PlayerValue/SideValue;
						break;
					case 5:
						total = PlayerValue%SideValue;
						break;
					}
					if(total > 40000)
						total = 40000;
					if(total < -2000)
						total = -2000;
					PlayerVarList.set(QuestName,total);
					PlayerVarList.saveConfig();
					PlayerQuestList.set("Started."+QuestName+".Flow",PlayerQuestList.getInt("Started."+QuestName+".Flow")+1);
					PlayerQuestList.saveConfig();
					QuestTypeRouter(player, QuestName);
					return;
				}
				case "IF":
				{
					YamlManager PlayerVarList  = YC.getNewConfig("Quest/PlayerVar/"+player.getUniqueId()+".yml");
					int PlayerValue = PlayerVarList.getInt(QuestName);
					int SideValue = QuestList.getInt(QuestName+".FlowChart."+FlowChart+".Value");
					boolean isMatch = false;
					switch(QuestList.getInt(QuestName+".FlowChart."+FlowChart+".Comparison"))
					{
					case 1:
						if(PlayerValue==SideValue)
							isMatch = true;
						break;
					case 2:
						if(PlayerValue!=SideValue)
							isMatch = true;
						break;
					case 3:
						if(PlayerValue>SideValue)
							isMatch = true;
						break;
					case 4:
						if(PlayerValue<SideValue)
							isMatch = true;
						break;
					case 5:
						if(PlayerValue>=SideValue)
							isMatch = true;
						break;
					case 6:
						if(PlayerValue<=SideValue)
							isMatch = true;
						break;
					case 7:
						SideValue = QuestList.getInt(QuestName+".FlowChart."+FlowChart+".Max");
						if(QuestList.getInt(QuestName+".FlowChart."+PlayerQuestList.getInt("Started."+QuestName+".Flow")+".Min")<=PlayerValue&&PlayerValue<=SideValue)
							isMatch = true;
						break;
					}
					if(isMatch)
					{
						PlayerQuestList.set("Started."+QuestName+".Flow", PlayerQuestList.getInt("Started."+QuestName+".Flow")+1);
						PlayerQuestList.saveConfig();
						QuestTypeRouter(player, QuestName);
					}
					else
					{
						QuestList.getString(QuestName+".FlowChart."+PlayerQuestList.getInt("Started."+QuestName+".Flow")+".Type");
						int MeetTheIF = 0;
						for(int count = FlowChart+1; count < QuestList.getConfigurationSection(QuestName+".FlowChart").getKeys(false).size(); count++)
						{
							if(QuestList.getString(QuestName+".FlowChart."+count+".Type").compareTo("IF")==0)
								MeetTheIF = MeetTheIF+1;
							else if(QuestList.getString(QuestName+".FlowChart."+count+".Type").compareTo("ENDIF")==0)
								if(MeetTheIF!=0)
									MeetTheIF = MeetTheIF-1;
							if(MeetTheIF==0&&(QuestList.getString(QuestName+".FlowChart."+count+".Type").compareTo("ENDIF")==0))
							{
								if(PlayerVarList.getInt(QuestName+".MeetELSE")!=0)
								{
									PlayerVarList.set(QuestName+".MeetELSE",PlayerVarList.getInt(QuestName+".MeetELSE")-1);
									PlayerVarList.saveConfig();
								}
								PlayerQuestList.set("Started."+QuestName+".Flow",count);
								PlayerQuestList.saveConfig();
								QuestTypeRouter(player, QuestName);
								return;
							}
							if(MeetTheIF==0&&(QuestList.getString(QuestName+".FlowChart."+count+".Type").compareTo("ELSE")==0))
							{
								PlayerVarList.set(QuestName+".MeetELSE",PlayerVarList.getInt(QuestName+".MeetELSE")+1);
								PlayerVarList.saveConfig();
								PlayerQuestList.set("Started."+QuestName+".Flow",count);
								PlayerQuestList.saveConfig();
								QuestTypeRouter(player, QuestName);
								return;
							}
						}
						//������ ENDIF�� ELSE�� IF�� ã�� ���ϸ� ����Ʈ �Ϸ�� �Ѿ
						GoldBigDragon_RPG.Util.ETC ETC = new GoldBigDragon_RPG.Util.ETC();
						YamlManager Config = YC.getNewConfig("config.yml");
						String message = Config.getString("Quest.ClearMessage").replace("%QuestName%", QuestName);
						player.sendMessage(message);
						PlayerQuestList.set("Ended."+QuestName+".ClearTime", ETC.getNowUTC());
						PlayerQuestList.removeKey("Started."+QuestName+".Flow");
						PlayerQuestList.removeKey("Started."+QuestName+".Type");
						PlayerQuestList.removeKey("Started."+QuestName);
						PlayerQuestList.saveConfig();
						PlayerVarList.removeKey(QuestName);
						PlayerVarList.saveConfig();
						player.closeInventory();
						s.SP(player, Sound.NOTE_PLING, 1.0F, 1.8F);
					}
				}
				break;
				case "ELSE":
				{
					YamlManager PlayerVarList  = YC.getNewConfig("Quest/PlayerVar/"+player.getUniqueId()+".yml");
					if(PlayerVarList.getInt(QuestName+".MeetELSE")==0)
					{
						QuestList.getString(QuestName+".FlowChart."+PlayerQuestList.getInt("Started."+QuestName+".Flow")+".Type");
						short MeetTheIF = 0;
						for(int count = FlowChart+1; count < QuestList.getConfigurationSection(QuestName+".FlowChart").getKeys(false).size(); count++)
						{
							if(QuestList.getString(QuestName+".FlowChart."+count+".Type").compareTo("IF")==0)
								MeetTheIF++;
							else if(MeetTheIF!=0&&QuestList.getString(QuestName+".FlowChart."+count+".Type").compareTo("ENDIF")==0)
								if(MeetTheIF!=0)
									MeetTheIF--;
							if(MeetTheIF==0&&(QuestList.getString(QuestName+".FlowChart."+count+".Type").compareTo("ENDIF")==0))
							{
								PlayerVarList.set(QuestName+".MeetElse",0);
								PlayerVarList.saveConfig();
								PlayerQuestList.set("Started."+QuestName+".Flow",count);
								PlayerQuestList.saveConfig();
								QuestTypeRouter(player, QuestName);
								return;
							}
						}
						//������ ENDIF�� ã�� ���ϸ� ����Ʈ �Ϸ�� �Ѿ
						GoldBigDragon_RPG.Util.ETC ETC = new GoldBigDragon_RPG.Util.ETC();
						YamlManager Config = YC.getNewConfig("config.yml");
						String message = Config.getString("Quest.ClearMessage").replace("%QuestName%", QuestName);
						player.sendMessage(message);
						PlayerQuestList.set("Ended."+QuestName+".ClearTime", ETC.getNowUTC());
						PlayerQuestList.removeKey("Started."+QuestName+".Flow");
						PlayerQuestList.removeKey("Started."+QuestName+".Type");
						PlayerQuestList.removeKey("Started."+QuestName);
						PlayerQuestList.saveConfig();
						PlayerVarList.removeKey(QuestName);
						PlayerVarList.saveConfig();
						player.closeInventory();
						s.SP(player, Sound.NOTE_PLING, 1.0F, 1.8F);
					}
					else
					{
						PlayerQuestList.set("Started."+QuestName+".Flow", PlayerQuestList.getInt("Started."+QuestName+".Flow")+1);
						PlayerQuestList.saveConfig();
						QuestTypeRouter(player, QuestName);
					}
				}
				break;
				case "ENDIF":
				{
					YamlManager PlayerVarList  = YC.getNewConfig("Quest/PlayerVar/"+player.getUniqueId()+".yml");
					if(PlayerVarList.getInt(QuestName+".MeetELSE")!=0)
					{
						PlayerVarList.set(QuestName+".MeetELSE",PlayerVarList.getInt(QuestName+".MeetELSE")-1);
						PlayerVarList.saveConfig();
					}
					PlayerQuestList.set("Started."+QuestName+".Flow", PlayerQuestList.getInt("Started."+QuestName+".Flow")+1);
					PlayerQuestList.saveConfig();
					QuestTypeRouter(player, QuestName);
				}
				break;
				case "QuestFail":
				{
					player.sendMessage(ChatColor.RED+"[����Ʈ] : ����Ʈ�� �ϼ����� ���Ͽ����ϴ�!");
					PlayerQuestList.set("Ended."+QuestName+".ClearTime", new GoldBigDragon_RPG.Util.ETC().getNowUTC());
					PlayerQuestList.removeKey("Started."+QuestName);
					PlayerQuestList.saveConfig();
					YamlManager PlayerVarList  = YC.getNewConfig("Quest/PlayerVar/"+player.getUniqueId()+".yml");
					PlayerVarList.removeKey(QuestName);
					PlayerVarList.saveConfig();
					player.closeInventory();
					s.SP(player, Sound.ORB_PICKUP, 1.0F, 1.8F);
					s.SP(player, Sound.WITHER_DEATH, 0.7F, 0.8F);
				}
				break;
				case "QuestReset":
				{
					player.sendMessage(ChatColor.YELLOW+"[����Ʈ] : ����Ʈ�� �����Ͽ����ϴ�!");
					PlayerQuestList.removeKey("Started."+QuestName);
					PlayerQuestList.saveConfig();
					YamlManager PlayerVarList  = YC.getNewConfig("Quest/PlayerVar/"+player.getUniqueId()+".yml");
					PlayerVarList.removeKey(QuestName);
					PlayerVarList.saveConfig();
					player.closeInventory();
					s.SP(player, Sound.LAVA_POP, 1.2F, 0.8F);
				}
			break;
			case "VarChange":
			{
				YamlManager PlayerVarList  = YC.getNewConfig("Quest/PlayerVar/"+player.getUniqueId()+".yml");
				PlayerVarList.set(QuestName, QuestList.getInt(QuestName+".FlowChart."+FlowChart+".Value"));
				PlayerVarList.saveConfig();
				PlayerQuestList.set("Started."+QuestName+".Flow", PlayerQuestList.getInt("Started."+QuestName+".Flow")+1);
				PlayerQuestList.saveConfig();
				QuestTypeRouter(player, QuestName);
			}
			break;
			case "Choice":
				Quest_UserChoice(player, QuestName,FlowChart);
				break;
			case "Nevigation":
			{
				String UTC = QuestList.getString(QuestName+".FlowChart."+FlowChart+".NeviUTC");
				YamlManager NavigationConfig =YC.getNewConfig("Navigation/NavigationList.yml");
				if(NavigationConfig.contains(UTC))
				{
					ServerTickMain.NaviUsingList.add(player.getName());
					player.closeInventory();
					s.SP(player, Sound.NOTE_PLING, 1.0F, 1.0F);
					
					GoldBigDragon_RPG.ServerTick.ServerTickScheduleObject STSO = new GoldBigDragon_RPG.ServerTick.ServerTickScheduleObject(Long.parseLong(UTC), "NV");
					STSO.setCount(0);//Ƚ �� �ʱ�ȭ
					STSO.setMaxCount(NavigationConfig.getInt(UTC+".time"));//N�ʰ� �׺���̼�
					//-1�� ������, N�ʰ��� �ƴ�, ã�� �� �� ���� �׺���̼� ����
					STSO.setString((byte)1, NavigationConfig.getString(UTC+".world"));//������ ���� �̸� ����
					STSO.setString((byte)2, player.getName());//�÷��̾� �̸� ����
					
					STSO.setInt((byte)0, NavigationConfig.getInt(UTC+".x"));//������X ��ġ����
					STSO.setInt((byte)1, NavigationConfig.getInt(UTC+".y"));//������Y ��ġ����
					STSO.setInt((byte)2, NavigationConfig.getInt(UTC+".z"));//������Z ��ġ����
					STSO.setInt((byte)3, NavigationConfig.getInt(UTC+".sensitive"));//���� ���� ����
					STSO.setInt((byte)4, NavigationConfig.getInt(UTC+".ShowArrow"));//��ƼŬ ����
					
					GoldBigDragon_RPG.ServerTick.ServerTickMain.Schedule.put(Long.parseLong(UTC), STSO);
					player.sendMessage(ChatColor.YELLOW+"[�׺���̼�] : ��ã�� �ý����� �����˴ϴ�!");
					player.sendMessage(ChatColor.YELLOW+"(ȭ��ǥ�� ������ ���� ���, [ESC] �� [����] �� [���� ����] ���� [����]�� [���]�� ������ �ּ���!)");
					
				}
				else
				{
					s.SP(player, Sound.NOTE_BASS, 1.0F, 1.0F);
					player.sendMessage(ChatColor.RED+"[�׺���̼�] : ��ϵ� �׺���̼��� ã�� �� �����ϴ�! �����ڿ��� �����ϼ���!");
				}
				PlayerQuestList.set("Started."+QuestName+".Flow", PlayerQuestList.getInt("Started."+QuestName+".Flow")+1);
				PlayerQuestList.saveConfig();
				QuestTypeRouter(player, QuestName);
			}
			break;
			case "Whisper":
			{
				String script3 = ChatColor.WHITE+QuestList.getString(QuestName+".FlowChart."+FlowChart+".Message");
				script3 = script3.replace("%player%", player.getName());
				YamlManager PlayerVarList  = YC.getNewConfig("Quest/PlayerVar/"+player.getUniqueId()+".yml");
				script3 = script3.replace("%value%", ""+PlayerVarList.getInt(QuestName));
				player.sendMessage(script3);
				PlayerQuestList.set("Started."+QuestName+".Flow", PlayerQuestList.getInt("Started."+QuestName+".Flow")+1);
				PlayerQuestList.saveConfig();
				QuestTypeRouter(player, QuestName);
			}
			break;
			case "BroadCast":
			{
				String script3 = ChatColor.WHITE+QuestList.getString(QuestName+".FlowChart."+FlowChart+".Message");
				script3 = script3.replace("%player%", player.getName());
				YamlManager PlayerVarList  = YC.getNewConfig("Quest/PlayerVar/"+player.getUniqueId()+".yml");
				script3 = script3.replace("%value%", ""+PlayerVarList.getInt(QuestName));
				Bukkit.getServer().broadcastMessage(script3);
				PlayerQuestList.set("Started."+QuestName+".Flow", PlayerQuestList.getInt("Started."+QuestName+".Flow")+1);
				PlayerQuestList.saveConfig();
				QuestTypeRouter(player, QuestName);
			}
			break;
			case "Script": 
			{
				String script = QuestList.getString(QuestName+".FlowChart."+FlowChart+".Script");
				script = script.replace("%player%", player.getName());
				YamlManager PlayerVarList  = YC.getNewConfig("Quest/PlayerVar/"+player.getUniqueId()+".yml");
				script = script.replace("%value%", ""+PlayerVarList.getInt(QuestName));
				String[] scriptA = script.split("%enter%");
				for(byte count = 0; count < scriptA.length; count++)
					scriptA[count] = ChatColor.WHITE + scriptA[count];
				QuestScriptTypeGUI(player, QuestName, NPCname, FlowChart, scriptA);
			}
			break;
			case "PScript": 
			{
				String script2 = QuestList.getString(QuestName+".FlowChart."+FlowChart+".Message");
				script2 = script2.replace("%player%", player.getName());
				YamlManager PlayerVarList  = YC.getNewConfig("Quest/PlayerVar/"+player.getUniqueId()+".yml");
				script2 = script2.replace("%value%", ""+PlayerVarList.getInt(QuestName));
				String[] scriptA2 = script2.split("%enter%");
				for(byte count = 0; count < scriptA2.length; count++)
					scriptA2[count] = ChatColor.WHITE + scriptA2[count];
				QuestScriptTypeGUI(player, QuestName, player.getName(), FlowChart, scriptA2);
			}
			break;
			case "Visit":
				PlayerQuestList.set("Started."+QuestName+".AreaName",QuestList.getString(QuestName+".FlowChart."+FlowChart+".AreaName"));
				PlayerQuestList.saveConfig();
				break;
			case "Give":
				ShowItemGUI(player, QuestName, FlowChart, false,false);
				break;
			case "Hunt":
				Object[] MobList = QuestList.getConfigurationSection(QuestName+".FlowChart."+FlowChart+".Monster").getKeys(false).toArray();
				for(short counter = 0; counter < MobList.length; counter++)
					PlayerQuestList.set("Started."+QuestName+".Hunt."+counter,0);
				PlayerQuestList.saveConfig();
				KillMonsterGUI(player, QuestName, FlowChart, false);
				break;
			case "Talk":
				break;
			case "Present":
				ShowItemGUI(player, QuestName, FlowChart, false,true);
				break;
			case "TelePort":
				{
					Location l = new Location(Bukkit.getServer().getWorld(QuestList.getString(QuestName+".FlowChart."+FlowChart+".World")), QuestList.getDouble(QuestName+".FlowChart."+FlowChart+".X"),
						QuestList.getDouble(QuestName+".FlowChart."+FlowChart+".Y")+1, QuestList.getDouble(QuestName+".FlowChart."+FlowChart+".Z"));
					player.teleport(l);
					p.givePotionEffect(player, PotionEffectType.BLINDNESS, 1, 15);
					s.SL(player.getLocation(), Sound.ENDERMAN_TELEPORT, 0.8F, 1.0F);
					PlayerQuestList.set("Started."+QuestName+".Flow", PlayerQuestList.getInt("Started."+QuestName+".Flow")+1);
					PlayerQuestList.saveConfig();
					QuestTypeRouter(player, QuestName);
				}
				break;
			case "BlockPlace":
				{
					Location l = new Location(Bukkit.getServer().getWorld(QuestList.getString(QuestName+".FlowChart."+FlowChart+".World")), QuestList.getDouble(QuestName+".FlowChart."+FlowChart+".X"),
						QuestList.getDouble(QuestName+".FlowChart."+FlowChart+".Y"), QuestList.getDouble(QuestName+".FlowChart."+FlowChart+".Z"));
					Block block = Bukkit.getWorld(QuestList.getString(QuestName+".FlowChart."+FlowChart+".World")).getBlockAt(l);
					block.setTypeId(QuestList.getInt(QuestName+".FlowChart."+FlowChart+".ID"));
					block.setData((byte)QuestList.getInt(QuestName+".FlowChart."+FlowChart+".DATA"));
					PlayerQuestList.set("Started."+QuestName+".Flow", PlayerQuestList.getInt("Started."+QuestName+".Flow")+1);
					PlayerQuestList.saveConfig();
					QuestTypeRouter(player, QuestName);
				}
				break;
			case "Harvest":
				Object[] BlockList = QuestList.getConfigurationSection(QuestName+".FlowChart."+FlowChart+".Block").getKeys(false).toArray();
				for(short counter = 0; counter < BlockList.length; counter++)
					PlayerQuestList.set("Started."+QuestName+".Block."+counter,0);
				PlayerQuestList.saveConfig();
				HarvestGUI(player, QuestName, FlowChart, false);
				break;
			}
		}
	}
	
	public void QuestScriptTypeGUI(Player player,String QuestName,String NPCname, short FlowChart, String[] script)
	{
		Inventory inv = Bukkit.createInventory(null, 27, ChatColor.BLACK + "[����Ʈ]");
		Stack2(ChatColor.BLACK + ChatColor.stripColor(QuestName), 160,8,1,null, 19, inv);
		
		for(byte count=0;count < script.length; count++)
			script[count] = script[count].replace("%player%", player.getName());
		if(NPCname.equals(player.getName()))
			ItemStackStack(getPlayerSkull(ChatColor.YELLOW+NPCname, 1, Arrays.asList(script), player.getName()), 13, inv);
		else
			Stack2(ChatColor.YELLOW + NPCname, 386,0,1,Arrays.asList(script), 13, inv);
		Stack2(ChatColor.WHITE + "" + ChatColor.BOLD + "�ݱ�", 324,0,1,Arrays.asList(ChatColor.GRAY + "â�� �ݽ��ϴ�.",ChatColor.BLACK + ChatColor.stripColor(QuestName)), 26, inv);
		player.openInventory(inv);
	}
	
	public void QuestOptionGUI(Player player, String QuestName)
	{
		Inventory inv = Bukkit.createInventory(null, 45, ChatColor.BLACK + "����Ʈ �ɼ�");

		YamlController YC = new YamlController(GoldBigDragon_RPG.Main.Main.plugin);
		YamlManager QuestList  = YC.getNewConfig("Quest/QuestList.yml");
		
		switch(QuestList.getString(QuestName + ".Type"))
		{
		case "N" :
			Stack2(ChatColor.WHITE + "" + ChatColor.BOLD + "����Ʈ Ÿ��", 340,0,1,Arrays.asList(ChatColor.WHITE + "�Ϲ� ����Ʈ"), 4, inv);
			break;
		case "R" :
			Stack2(ChatColor.WHITE + "" + ChatColor.BOLD + "����Ʈ Ÿ��", 386,0,1,Arrays.asList(ChatColor.WHITE + "�ݺ� ����Ʈ"), 4, inv);
			break;
		case "D" :
			Stack2(ChatColor.WHITE + "" + ChatColor.BOLD + "����Ʈ Ÿ��", 403,0,1,Arrays.asList(ChatColor.WHITE + "���� ����Ʈ"), 4, inv);
			break;
		case "W" :
			Stack2(ChatColor.WHITE + "" + ChatColor.BOLD + "����Ʈ Ÿ��", 403,0,7,Arrays.asList(ChatColor.WHITE + "�ְ� ����Ʈ"), 4, inv);
			break;
		case "M" :
			Stack2(ChatColor.WHITE + "" + ChatColor.BOLD + "����Ʈ Ÿ��", 403,0,31,Arrays.asList(ChatColor.WHITE + "���� ����Ʈ"), 4, inv);
			break;
		}

		Stack2(ChatColor.WHITE + "" + ChatColor.BOLD + "���� ����", 384,0,1,Arrays.asList(ChatColor.WHITE+"����Ʈ ���࿡ �ʿ��� ������ �����մϴ�.",ChatColor.GOLD+"������"+ChatColor.WHITE+" �ý����� ��� "+ChatColor.YELLOW+"��������"+ChatColor.WHITE+" �����̸�,",ChatColor.RED+"�����ý��丮"+ChatColor.WHITE+" �ý����� ��� "+ChatColor.YELLOW+"����"+ChatColor.WHITE+" �����Դϴ�.","",ChatColor.AQUA + "[�ʿ� ���� : " + QuestList.getInt(QuestName+".Need.LV")+"]"), 11, inv);
		Stack2(ChatColor.WHITE + "" + ChatColor.BOLD + "NPC ȣ���� ����", 38,0,1,Arrays.asList(ChatColor.WHITE+"����Ʈ ���࿡ �ʿ���",ChatColor.WHITE+"NPC���� ȣ������ �����մϴ�.","",ChatColor.AQUA + "[�ʿ� ȣ���� : " + QuestList.getInt(QuestName+".Need.Love")+"]"), 13, inv);
		Stack2(ChatColor.WHITE + "" + ChatColor.BOLD + "��ų ��ũ ����", 403,0,1,Arrays.asList(ChatColor.WHITE+"����Ʈ ���࿡ �ʿ���",ChatColor.WHITE+"��ų ��ũ�� �����մϴ�.",""/*,ChatColor.AQUA + "[�ʿ� ��ų : " + QuestList.getString(QuestName+".Need.Skill.Name")+"]",ChatColor.AQUA+"[�ʿ� ��ũ : " + QuestList.getInt(QuestName+".Need.Skill.Rank")+"]"*/), 15, inv);
		Stack2(ChatColor.WHITE + "" + ChatColor.BOLD + ""+GoldBigDragon_RPG.Main.ServerOption.STR+" ����", 267,0,1,Arrays.asList(ChatColor.WHITE+"����Ʈ ���࿡ �ʿ���",ChatColor.WHITE+""+GoldBigDragon_RPG.Main.ServerOption.STR+" ������ �����մϴ�.","",ChatColor.AQUA + "[�ʿ� "+GoldBigDragon_RPG.Main.ServerOption.STR+" : " + QuestList.getInt(QuestName+".Need.STR")+"]"), 20, inv);
		Stack2(ChatColor.WHITE + "" + ChatColor.BOLD + ""+GoldBigDragon_RPG.Main.ServerOption.DEX+" ����", 261,0,1,Arrays.asList(ChatColor.WHITE+"����Ʈ ���࿡ �ʿ���",ChatColor.WHITE+""+GoldBigDragon_RPG.Main.ServerOption.DEX+" ������ �����մϴ�.","",ChatColor.AQUA + "[�ʿ� "+GoldBigDragon_RPG.Main.ServerOption.DEX+" : " + QuestList.getInt(QuestName+".Need.DEX")+"]"), 21, inv);
		Stack2(ChatColor.WHITE + "" + ChatColor.BOLD + ""+GoldBigDragon_RPG.Main.ServerOption.INT+" ����", 369,0,1,Arrays.asList(ChatColor.WHITE+"����Ʈ ���࿡ �ʿ���",ChatColor.WHITE+""+GoldBigDragon_RPG.Main.ServerOption.INT+" ������ �����մϴ�.","",ChatColor.AQUA + "[�ʿ� "+GoldBigDragon_RPG.Main.ServerOption.INT+" : " + QuestList.getInt(QuestName+".Need.INT")+"]"), 22, inv);
		Stack2(ChatColor.WHITE + "" + ChatColor.BOLD + ""+GoldBigDragon_RPG.Main.ServerOption.WILL+" ����", 370,0,1,Arrays.asList(ChatColor.WHITE+"����Ʈ ���࿡ �ʿ���",ChatColor.WHITE+""+GoldBigDragon_RPG.Main.ServerOption.WILL+" ������ �����մϴ�.","",ChatColor.AQUA + "[�ʿ� "+GoldBigDragon_RPG.Main.ServerOption.WILL+" : " + QuestList.getInt(QuestName+".Need.WILL")+"]"), 23, inv);
		Stack2(ChatColor.WHITE + "" + ChatColor.BOLD + ""+GoldBigDragon_RPG.Main.ServerOption.LUK+" ����", 322,0,1,Arrays.asList(ChatColor.WHITE+"����Ʈ ���࿡ �ʿ���",ChatColor.WHITE+""+GoldBigDragon_RPG.Main.ServerOption.LUK+" ������ �����մϴ�.","",ChatColor.AQUA + "[�ʿ� "+GoldBigDragon_RPG.Main.ServerOption.LUK+" : " + QuestList.getInt(QuestName+".Need.LUK")+"]"), 24, inv);
		if(QuestList.getString(QuestName+".Need.PrevQuest").equalsIgnoreCase("null") == true)
			Stack2(ChatColor.WHITE + "" + ChatColor.BOLD + "�ʼ� �Ϸ� ����Ʈ", 386,0,1,Arrays.asList(ChatColor.WHITE+"���� ����Ʈ�� ������ ��",ChatColor.WHITE+"���� ����Ʈ�� ���� �ϵ��� �մϴ�.","",ChatColor.AQUA + "[���� ����Ʈ : ����]"),29, inv);
		else
			Stack2(ChatColor.WHITE + "" + ChatColor.BOLD + "�ʼ� �Ϸ� ����Ʈ", 386,0,1,Arrays.asList(ChatColor.WHITE+"���� ����Ʈ�� ������ ��",ChatColor.WHITE+"���� ����Ʈ�� ���� �ϵ��� �մϴ�.",ChatColor.RED+"[Shift + ��Ŭ���� �����˴ϴ�]","",ChatColor.AQUA + "[���� ����Ʈ : " +QuestList.getString(QuestName+".Need.PrevQuest")+"]"),29, inv);
		switch(QuestList.getInt(QuestName+".Server.Limit"))
		{
		case 0:
			Stack2(ChatColor.WHITE + "" + ChatColor.BOLD + "����Ʈ ����", 397,3,1,Arrays.asList(ChatColor.WHITE+"�������� �� �� ����",ChatColor.WHITE+"�� ����Ʈ�� ���� �� �� �ֽ��ϴ�.",ChatColor.WHITE+"�÷��̾ ����Ʈ�� ���� �� ���� 1�� ���̸�,",ChatColor.WHITE+"-1�� �� ��� ����Ʈ�� ���� �� �����ϴ�.",ChatColor.DARK_AQUA+"(0������ ������ ���, ������ ������ϴ�.)","",ChatColor.AQUA +"[���� ���� �÷��̾� �� : ���� ����]"), 33, inv);
			break;
		case -1:
			Stack2(ChatColor.WHITE + "" + ChatColor.BOLD + "����Ʈ ����", 397,3,1,Arrays.asList(ChatColor.WHITE+"�������� �� �� ����",ChatColor.WHITE+"�� ����Ʈ�� ���� �� �� �ֽ��ϴ�.",ChatColor.WHITE+"�÷��̾ ����Ʈ�� ���� �� ���� 1�� ���̸�,",ChatColor.WHITE+"-1�� �� ��� ����Ʈ�� ���� �� �����ϴ�.",ChatColor.DARK_AQUA+"(0������ ������ ���, ������ ������ϴ�.)","",ChatColor.RED +"[���̻� ���� �� ����]"), 33, inv);
			break;
		default:
			Stack2(ChatColor.WHITE + "" + ChatColor.BOLD + "����Ʈ ����", 397,3,1,Arrays.asList(ChatColor.WHITE+"�������� �� �� ����",ChatColor.WHITE+"�� ����Ʈ�� ���� �� �� �ֽ��ϴ�.",ChatColor.WHITE+"�÷��̾ ����Ʈ�� ���� �� ���� 1�� ���̸�,",ChatColor.WHITE+"-1�� �� ��� ����Ʈ�� ���� �� �����ϴ�.",ChatColor.DARK_AQUA+"(0������ ������ ���, ������ ������ϴ�.)","",ChatColor.AQUA +"[���� ���� �÷��̾� �� : "+QuestList.getInt(QuestName+".Server.Limit")+"]"), 33, inv);
			break;
		}
	
		Stack2(ChatColor.WHITE + "" + ChatColor.BOLD + "���� ���", 323,0,1,Arrays.asList(ChatColor.GRAY + "���� ȭ������ ���ư��ϴ�."), 36, inv);
		Stack2(ChatColor.WHITE + "" + ChatColor.BOLD + "�ݱ�", 324,0,1,Arrays.asList(ChatColor.GRAY + "â�� �ݽ��ϴ�.",ChatColor.BLACK + ChatColor.stripColor(QuestName)), 44, inv);
		player.openInventory(inv);
	}
	
	public void GetItemGUI(Player player, String QuestName)
	{
		Inventory inv = Bukkit.createInventory(null, 9, ChatColor.BLACK + "��ƾ� �� ������ ���");
		for(int count = 0;count<8;count++)
			Stack2(ChatColor.WHITE+ "[�������� �÷� �ּ���.]", 389,0,0,null, count, inv);
		
		Stack2(ChatColor.WHITE + "" + ChatColor.BOLD + "�ݱ�", 324,0,1,Arrays.asList(ChatColor.GRAY + "â�� �ݽ��ϴ�.",ChatColor.BLACK + ChatColor.stripColor(QuestName)), 8, inv);
		player.openInventory(inv);
	}

	public void GetPresentGUI(Player player, String QuestName)
	{
		YamlController YC = new YamlController(GoldBigDragon_RPG.Main.Main.plugin);
		YamlManager QuestConfig=YC.getNewConfig("Quest/QuestList.yml");

		Object_UserData u = new Object_UserData();
		
		Inventory inv = Bukkit.createInventory(null, 9, ChatColor.BLACK + "���� ������ ���");
		if(u.getInt(player, (byte)1) == -1)
			u.setInt(player, (byte)1, 0);
		if(u.getInt(player, (byte)2) == -1)
			u.setInt(player, (byte)2, 0);
		if(u.getInt(player, (byte)3) == -1)
			u.setInt(player, (byte)3, 0);
			
		Stack2(ChatColor.WHITE+ "[����� �����ϱ�]", 266,0,1,Arrays.asList(ChatColor.WHITE+""+ChatColor.BOLD+""+u.getInt(player, (byte)1)+" "+GoldBigDragon_RPG.Main.ServerOption.Money), 0, inv);
		Stack2(ChatColor.WHITE+ "[����ġ �����ϱ�]", 384,0,1,Arrays.asList(ChatColor.WHITE+""+ChatColor.BOLD+""+u.getInt(player, (byte)2)+ChatColor.AQUA+""+ChatColor.BOLD+ " EXP"), 1, inv);
		Stack2(ChatColor.WHITE+ "[NPC ȣ���� �����ϱ�]", 38,0,1,Arrays.asList(ChatColor.WHITE+""+ChatColor.BOLD+""+u.getInt(player, (byte)3)+ChatColor.LIGHT_PURPLE+""+ChatColor.BOLD+ " Love"), 2, inv);
		int ifItemExit = 0;
		for(int count = 3;count<8;count++)
		{
			if(QuestConfig.getItemStack(QuestName + ".FlowChart."+ u.getInt(player, (byte)5) +".Item."+ifItemExit) != null)
			{
				ItemStackStack(QuestConfig.getItemStack(QuestName + ".FlowChart."+ u.getInt(player, (byte)5) +".Item."+ifItemExit), count, inv);
				ifItemExit++;
			}
			else
				Stack2(ChatColor.WHITE+ "[�������� �÷� �ּ���.]", 389,0,0,null, count, inv);
		}
		
		Stack2(ChatColor.WHITE + "" + ChatColor.BOLD + "�ݱ�", 324,0,1,Arrays.asList(ChatColor.GRAY + "â�� �ݽ��ϴ�.",ChatColor.BLACK + ChatColor.stripColor(QuestName)), 8, inv);
		u.setString(player, (byte)4,null);
		player.openInventory(inv);
	}
	
	public void ShowItemGUI(Player player, String QuestName, short Flow, boolean isOP, boolean type)
	{
		YamlController YC = new YamlController(GoldBigDragon_RPG.Main.Main.plugin);
		YamlManager QuestList  = YC.getNewConfig("Quest/QuestList.yml");

		Inventory inv = null;
		
		if(QuestList.contains(QuestName+".FlowChart."+Flow+".Item") == true)
		{
			Object[] a =QuestList.getConfigurationSection(QuestName+".FlowChart."+Flow+".Item").getKeys(false).toArray();
			if(type == false)
			{
				inv = Bukkit.createInventory(null, 27, ChatColor.BLACK + "��ƾ� �� ������ ���");
				for(short count = 0;count<a.length;count++)
					ItemStackStack(QuestList.getItemStack(QuestName+".FlowChart."+Flow+".Item." + a[count]),count+10,inv);
			}
			else
			{
				inv = Bukkit.createInventory(null, 27, ChatColor.BLACK + "���� ���");
				Stack2(ChatColor.GOLD+ "[�����]", 266,0,1,Arrays.asList("",ChatColor.WHITE + "" + ChatColor.BOLD + QuestList.getInt(QuestName+".FlowChart."+Flow+".Money") +ChatColor.GOLD +" "+GoldBigDragon_RPG.Main.ServerOption.Money), 3, inv);
				Stack2(ChatColor.AQUA+ "[����ġ]", 384,0,1,Arrays.asList("",ChatColor.WHITE + "" + ChatColor.BOLD + QuestList.getInt(QuestName+".FlowChart."+Flow+".EXP") +ChatColor.AQUA + " EXP"), 4, inv);
				Stack2(ChatColor.LIGHT_PURPLE+ "[ȣ����]", 38,0,1,Arrays.asList("",ChatColor.WHITE + "" + ChatColor.BOLD + QuestList.getInt(QuestName+".FlowChart."+Flow+".Love") +ChatColor.LIGHT_PURPLE + " Love"), 5, inv);

				for(short count = 0;count<a.length;count++)
					ItemStackStack(QuestList.getItemStack(QuestName+".FlowChart."+Flow+".Item." + a[count]),count+11,inv);
				if(player.isOp())
				{
					Object_UserData u = new Object_UserData();
					if(u.getInt(player, (byte)1)!=-9)
					{
						u.clearAll(player);
						Stack2(ChatColor.WHITE + "" + ChatColor.BOLD + "���� �ޱ�", 54,0,1,Arrays.asList(ChatColor.GRAY + "������ �����մϴ�." ,ChatColor.BLACK +""+ Flow), 22, inv);
					}
				}
				else
					Stack2(ChatColor.WHITE + "" + ChatColor.BOLD + "���� �ޱ�", 54,0,1,Arrays.asList(ChatColor.GRAY + "������ �����մϴ�." ,ChatColor.BLACK +""+ Flow), 22, inv);
			}
		}
		else
		{
			if(type == false)
			{
				inv = Bukkit.createInventory(null, 27, ChatColor.BLACK + "��ƾ� �� ������ ���");
			}
			else
			{
				inv = Bukkit.createInventory(null, 27, ChatColor.BLACK + "���� ���");
				Stack2(ChatColor.GOLD+ "[�����]", 266,0,1,Arrays.asList("",ChatColor.WHITE + "" + ChatColor.BOLD + QuestList.getInt(QuestName+".FlowChart."+Flow+".Money") +ChatColor.GOLD +" "+GoldBigDragon_RPG.Main.ServerOption.Money), 3, inv);
				Stack2(ChatColor.AQUA+ "[����ġ]", 384,0,1,Arrays.asList("",ChatColor.WHITE + "" + ChatColor.BOLD + QuestList.getInt(QuestName+".FlowChart."+Flow+".EXP") +ChatColor.AQUA + " EXP"), 4, inv);
				Stack2(ChatColor.LIGHT_PURPLE+ "[ȣ����]", 38,0,1,Arrays.asList("",ChatColor.WHITE + "" + ChatColor.BOLD + QuestList.getInt(QuestName+".FlowChart."+Flow+".Love") +ChatColor.LIGHT_PURPLE + " Love"), 5, inv);
				if(player.isOp())
				{
					Object_UserData u = new Object_UserData();
					if(u.getInt(player, (byte)1)!=-9)
					{
						u.clearAll(player);
						Stack2(ChatColor.WHITE + "" + ChatColor.BOLD + "���� �ޱ�", 54,0,1,Arrays.asList(ChatColor.GRAY + "������ �����մϴ�." ,ChatColor.BLACK +""+ Flow), 22, inv);
					}
				}
				else
					Stack2(ChatColor.WHITE + "" + ChatColor.BOLD + "���� �ޱ�", 54,0,1,Arrays.asList(ChatColor.GRAY + "������ �����մϴ�." ,ChatColor.BLACK +""+ Flow), 22, inv);
			}
		}
		Stack2(ChatColor.WHITE + "" + ChatColor.BOLD + "���� ���", 323,0,1,Arrays.asList(ChatColor.GRAY + "���� ȭ������ ���ư��ϴ�.",ChatColor.BLACK +""+ isOP), 18, inv);
		Stack2(ChatColor.WHITE + "" + ChatColor.BOLD + "�ݱ�", 324,0,1,Arrays.asList(ChatColor.GRAY + "â�� �ݽ��ϴ�.",ChatColor.BLACK + ChatColor.stripColor(QuestName)), 26, inv);
		player.openInventory(inv);
	}
	
	public void KillMonsterGUI(Player player, String QuestName, short Flow, boolean isOP)
	{
		YamlController YC = new YamlController(GoldBigDragon_RPG.Main.Main.plugin);
		YamlManager QuestList  = YC.getNewConfig("Quest/QuestList.yml");
		YamlManager PlayerQuestList  = YC.getNewConfig("Quest/PlayerData/"+player.getUniqueId()+".yml");

		Inventory inv = Bukkit.createInventory(null, 27, ChatColor.BLACK + "��� �ؾ� �� ���� ���");
		
		Object[] c = QuestList.getConfigurationSection(QuestName+".FlowChart."+Flow+".Monster").getKeys(false).toArray();
		for(short counter = 0; counter < c.length; counter++)
		{
			String MobName = QuestList.getString(QuestName+".FlowChart."+Flow+".Monster."+counter+".MonsterName");
			int Amount = QuestList.getInt(QuestName+".FlowChart."+Flow+".Monster."+counter+".Amount");
			int PlayerKillAmount = PlayerQuestList.getInt("Started."+QuestName+".Hunt."+counter);
			
	        ItemStack skull = new ItemStack(Material.SKULL_ITEM, 1);
	        SkullMeta meta = (SkullMeta) skull.getItemMeta();
	        meta.setOwner(MobName);
	        meta.setDisplayName(ChatColor.GOLD + SkullType(MobName));
	        meta.setLore(Arrays.asList(ChatColor.WHITE + "[" +PlayerKillAmount+"/"+ Amount + "]"));
	        skull.setItemMeta(meta);
	        ItemStackStack(skull, counter, inv);
			//Stack2(ChatColor.GOLD+ MobName, 266,0,1,Arrays.asList(ChatColor.WHITE + "[" +PlayerKillAmount+"/"+ Amount + "]"), counter, inv);
		}
		
		
		Stack2(ChatColor.WHITE + "" + ChatColor.BOLD + "���� ���", 323,0,1,Arrays.asList(ChatColor.GRAY + "���� ȭ������ ���ư��ϴ�.",ChatColor.BLACK +""+ isOP), 18, inv);
		Stack2(ChatColor.WHITE + "" + ChatColor.BOLD + "�ݱ�", 324,0,1,Arrays.asList(ChatColor.GRAY + "â�� �ݽ��ϴ�.",ChatColor.BLACK + ChatColor.stripColor(QuestName)), 26, inv);
		player.openInventory(inv);
	}
	
	public void HarvestGUI(Player player, String QuestName, short Flow, boolean isOP)
	{
		YamlController YC = new YamlController(GoldBigDragon_RPG.Main.Main.plugin);
		YamlManager QuestList  = YC.getNewConfig("Quest/QuestList.yml");
		YamlManager PlayerQuestList  = YC.getNewConfig("Quest/PlayerData/"+player.getUniqueId()+".yml");

		Inventory inv = Bukkit.createInventory(null, 27, ChatColor.BLACK + "ä�� �ؾ� �� ��� ���");
		
		Object[] c = QuestList.getConfigurationSection(QuestName+".FlowChart."+Flow+".Block").getKeys(false).toArray();
		for(short counter = 0; counter < c.length; counter++)
		{
			int BlockID = QuestList.getInt(QuestName+".FlowChart."+Flow+".Block."+counter+".BlockID");
			byte BlockData = (byte) QuestList.getInt(QuestName+".FlowChart."+Flow+".Block."+counter+".BlockData");
			int Amount = QuestList.getInt(QuestName+".FlowChart."+Flow+".Block."+counter+".Amount");
			boolean DataEquals = QuestList.getBoolean(QuestName+".FlowChart."+Flow+".Block."+counter+".DataEquals");
			int PlayerHarvestAmount = PlayerQuestList.getInt("Started."+QuestName+".Block."+counter);
			GoldBigDragon_RPG.Event.Interact IT = new GoldBigDragon_RPG.Event.Interact();
			
			if(DataEquals == true)
				Stack(ChatColor.YELLOW+IT.SetItemDefaultName((short) BlockID,(byte)BlockData), BlockID, BlockData, 1, Arrays.asList(ChatColor.WHITE + "[" +PlayerHarvestAmount+"/"+ Amount + "]","",ChatColor.GRAY + "������ ID : " +BlockID,ChatColor.GRAY + "������ Data : " +BlockData), counter, inv);
			else
				Stack(ChatColor.YELLOW+"�ƹ��� "+IT.SetItemDefaultName((short) BlockID,(byte)BlockData)+ChatColor.YELLOW+" ����", BlockID, 0, 1, Arrays.asList(ChatColor.WHITE + "[" +PlayerHarvestAmount+"/"+ Amount + "]","",ChatColor.GRAY + "������ ID : " +BlockID), counter, inv);
		}
		
		Stack2(ChatColor.WHITE + "" + ChatColor.BOLD + "���� ���", 323,0,1,Arrays.asList(ChatColor.GRAY + "���� ȭ������ ���ư��ϴ�.",ChatColor.BLACK +""+ isOP), 18, inv);
		Stack2(ChatColor.WHITE + "" + ChatColor.BOLD + "�ݱ�", 324,0,1,Arrays.asList(ChatColor.GRAY + "â�� �ݽ��ϴ�.",ChatColor.BLACK + ChatColor.stripColor(QuestName)), 26, inv);
		player.openInventory(inv);
	}
	
	public void KeepGoing(Player player, String QuestName, short Flow, short Mob, boolean Harvest)
	{
		Inventory inv = Bukkit.createInventory(null, 27, ChatColor.BLACK + "��� ��� �Ͻðڽ��ϱ�?");

		if(Harvest == false)
		{
			Stack2(ChatColor.GREEN + "" + ChatColor.BOLD + "��� ����ϱ�", 386,0,1,Arrays.asList(ChatColor.GRAY + "��� ����� �߰��� ����մϴ�.",ChatColor.BLACK +""+Flow,ChatColor.BLACK + ""+Mob), 10, inv);
			Stack2(ChatColor.RED + "" + ChatColor.BOLD + "��� �ߴ��ϱ�", 166,0,1,Arrays.asList(ChatColor.GRAY + "��� ��� �߰��� �����մϴ�.",ChatColor.BLACK + ChatColor.stripColor(QuestName)), 16, inv);
		}
		else
		{
			Stack2(ChatColor.GREEN + "" + ChatColor.BOLD + "��� ����ϱ�", 386,0,1,Arrays.asList(ChatColor.GRAY + "ä�� ����� �߰��� ����մϴ�.",ChatColor.BLACK +""+Flow,ChatColor.BLACK + ""+Mob), 10, inv);
			Stack2(ChatColor.RED + "" + ChatColor.BOLD + "��� �ߴ��ϱ�", 166,0,1,Arrays.asList(ChatColor.GRAY + "ä�� ��� �߰��� �����մϴ�.",ChatColor.BLACK + ChatColor.stripColor(QuestName)), 16, inv);
		}
		player.openInventory(inv);
	}
	
	public void Quest_NavigationListGUI(Player player, short page, String QuestName)
	{
		YamlController YC = new YamlController(GoldBigDragon_RPG.Main.Main.plugin);
		YamlManager NavigationConfig =YC.getNewConfig("Navigation/NavigationList.yml");

		Inventory inv = Bukkit.createInventory(null, 54, ChatColor.BLACK + "����Ʈ �׺� ���� : " + (page+1));

		Object[] Navi= NavigationConfig.getConfigurationSection("").getKeys(false).toArray();
		
		byte loc=0;
		for(int count = page*45; count < Navi.length;count++)
		{
			if(count > Navi.length || loc >= 45) break;
			String NaviName = NavigationConfig.getString(Navi[count]+".Name");
			String world = NavigationConfig.getString(Navi[count]+".world");
			int x = NavigationConfig.getInt(Navi[count]+".x");
			short y = (short) NavigationConfig.getInt(Navi[count]+".y");
			int z = NavigationConfig.getInt(Navi[count]+".z");
			int Time = NavigationConfig.getInt(Navi[count]+".time");
			short sensitive = (short) NavigationConfig.getInt(Navi[count]+".sensitive");
			boolean Permition = NavigationConfig.getBoolean(Navi[count]+".onlyOPuse");
			byte ShowArrow = (byte) NavigationConfig.getInt(Navi[count]+".ShowArrow");
			
			
			String TimeS = ChatColor.DARK_AQUA+"<������ �� ���� ����>";
			String PermitionS = ChatColor.DARK_AQUA+"<OP�� ��� ����>";
			String sensitiveS = ChatColor.BLUE+"<�ݰ� "+sensitive+"��� �̳��� �������� ����>";
			String ShowArrowS = ChatColor.DARK_AQUA+"<�⺻ ȭ��ǥ ���>";
			if(Permition == false)
				PermitionS = ChatColor.DARK_AQUA+"<��� ��� ����>";
			if(Time >= 0)
				TimeS = ChatColor.DARK_AQUA+"<"+Time+"�� ���� ����>";
			switch(ShowArrow)
			{
			default:
				ShowArrowS = ChatColor.DARK_AQUA+"<�⺻ ȭ��ǥ ���>";
				break;
			}
			Stack2(ChatColor.BLACK + "" + ChatColor.BOLD + Navi[count].toString(), 395,0,1,Arrays.asList(
			ChatColor.YELLOW+""+ChatColor.BOLD+NaviName,"",
			ChatColor.BLUE+"[���� ����]",ChatColor.BLUE+"���� : "+ChatColor.WHITE+world,
			ChatColor.BLUE+"��ǥ : " + ChatColor.WHITE+x+","+y+","+z,sensitiveS,"",
			ChatColor.DARK_AQUA+"[��Ÿ �ɼ�]",TimeS,PermitionS,ShowArrowS,""
			,ChatColor.YELLOW+"[�� Ŭ���� �׺� ����]"), loc, inv);
			loc++;
		}
		
		if(Navi.length-(page*44)>45)
		Stack2(ChatColor.WHITE + "" + ChatColor.BOLD + "���� ������", 323,0,1,Arrays.asList(ChatColor.GRAY + "���� �������� �̵� �մϴ�."), 50, inv);
		if(page!=0)
		Stack2(ChatColor.WHITE + "" + ChatColor.BOLD + "���� ������", 323,0,1,Arrays.asList(ChatColor.GRAY + "���� �������� �̵� �մϴ�."), 48, inv);
		Stack2(ChatColor.WHITE + "" + ChatColor.BOLD + "���� ���", 323,0,1,Arrays.asList(ChatColor.GRAY + "���� ȭ������ ���ư��ϴ�."), 45, inv);
		Stack2(ChatColor.WHITE + "" + ChatColor.BOLD + "�ݱ�", 324,0,1,Arrays.asList(ChatColor.GRAY + "â�� �ݽ��ϴ�.",ChatColor.BLACK+QuestName), 53, inv);
		player.openInventory(inv);
	}
	
	public void Quest_OPChoice(Player player,String QuestName, short Flow,short page)
	{
		YamlController YC = new YamlController(GoldBigDragon_RPG.Main.Main.plugin);
		YamlManager QuestList  = YC.getNewConfig("Quest/QuestList.yml");

		Inventory inv = Bukkit.createInventory(null, 27, ChatColor.BLACK + "����Ʈ ���� Ȯ��");
		
		String[] script = (QuestList.getString(QuestName+".FlowChart."+Flow+".Choice.0.Lore")+"%enter%%enter%"+ChatColor.DARK_AQUA+""+ChatColor.BOLD+"������ ���� : " + ChatColor.WHITE+QuestList.getInt(QuestName+".FlowChart."+Flow+".Choice.0.Var")).split("%enter%");
		
		switch(QuestList.getConfigurationSection(QuestName+".FlowChart."+Flow+".Choice").getKeys(false).size())
		{
		case 1:
			Stack2(ChatColor.WHITE + "" + ChatColor.BOLD + "[����]", 72,0,1,Arrays.asList(script), 13, inv);
			break;
		case 2:
			Stack2(ChatColor.WHITE + "" + ChatColor.BOLD + "[����]", 72,0,1,Arrays.asList(script), 12, inv);
			script = (QuestList.getString(QuestName+".FlowChart."+Flow+".Choice.1.Lore")+"%enter%%enter%"+ChatColor.DARK_AQUA+""+ChatColor.BOLD+"������ ���� : " + ChatColor.WHITE+QuestList.getInt(QuestName+".FlowChart."+Flow+".Choice.1.Var")).split("%enter%");
			Stack2(ChatColor.WHITE + "" + ChatColor.BOLD + "[����]", 72,0,2,Arrays.asList(script), 14, inv);
			break;
		case 3:
			Stack2(ChatColor.WHITE + "" + ChatColor.BOLD + "[����]", 72,0,1,Arrays.asList(script), 11, inv);
			script = (QuestList.getString(QuestName+".FlowChart."+Flow+".Choice.1.Lore")+"%enter%%enter%"+ChatColor.DARK_AQUA+""+ChatColor.BOLD+"������ ���� : " + ChatColor.WHITE+QuestList.getInt(QuestName+".FlowChart."+Flow+".Choice.1.Var")).split("%enter%");
			Stack2(ChatColor.WHITE + "" + ChatColor.BOLD + "[����]", 72,0,2,Arrays.asList(script), 13, inv);
			script = (QuestList.getString(QuestName+".FlowChart."+Flow+".Choice.2.Lore")+"%enter%%enter%"+ChatColor.DARK_AQUA+""+ChatColor.BOLD+"������ ���� : " + ChatColor.WHITE+QuestList.getInt(QuestName+".FlowChart."+Flow+".Choice.2.Var")).split("%enter%");
			Stack2(ChatColor.WHITE + "" + ChatColor.BOLD + "[����]", 72,0,3,Arrays.asList(script), 15, inv);
			break;
		case 4:
			Stack2(ChatColor.WHITE + "" + ChatColor.BOLD + "[����]", 72,0,1,Arrays.asList(script), 10, inv);
			script = (QuestList.getString(QuestName+".FlowChart."+Flow+".Choice.1.Lore")+"%enter%%enter%"+ChatColor.DARK_AQUA+""+ChatColor.BOLD+"������ ���� : " + ChatColor.WHITE+QuestList.getInt(QuestName+".FlowChart."+Flow+".Choice.1.Var")).split("%enter%");
			Stack2(ChatColor.WHITE + "" + ChatColor.BOLD + "[����]", 72,0,2,Arrays.asList(script), 12, inv);
			script = (QuestList.getString(QuestName+".FlowChart."+Flow+".Choice.2.Lore")+"%enter%%enter%"+ChatColor.DARK_AQUA+""+ChatColor.BOLD+"������ ���� : " + ChatColor.WHITE+QuestList.getInt(QuestName+".FlowChart."+Flow+".Choice.2.Var")).split("%enter%");
			Stack2(ChatColor.WHITE + "" + ChatColor.BOLD + "[����]", 72,0,3,Arrays.asList(script), 14, inv);
			script = (QuestList.getString(QuestName+".FlowChart."+Flow+".Choice.3.Lore")+"%enter%%enter%"+ChatColor.DARK_AQUA+""+ChatColor.BOLD+"������ ���� : " + ChatColor.WHITE+QuestList.getInt(QuestName+".FlowChart."+Flow+".Choice.3.Var")).split("%enter%");
			Stack2(ChatColor.WHITE + "" + ChatColor.BOLD + "[����]", 72,0,4,Arrays.asList(script), 16, inv);
			break;
		}
		Stack2(ChatColor.WHITE + "" + ChatColor.BOLD + "���� ���", 323,0,1,Arrays.asList(ChatColor.GRAY + "���� ȭ������ ���ư��ϴ�.",ChatColor.BLACK+""+page), 18, inv);
		Stack2(ChatColor.WHITE + "" + ChatColor.BOLD + "�ݱ�", 324,0,1,Arrays.asList(ChatColor.GRAY + "â�� �ݽ��ϴ�.",ChatColor.BLACK+QuestName), 26, inv);
		player.openInventory(inv);
	}
	
	public void Quest_UserChoice(Player player,String QuestName, short Flow)
	{
		YamlController YC = new YamlController(GoldBigDragon_RPG.Main.Main.plugin);
		YamlManager QuestList  = YC.getNewConfig("Quest/QuestList.yml");

		Inventory inv = Bukkit.createInventory(null, 27, ChatColor.BLACK + "����Ʈ ����");

		String lore = QuestList.getString(QuestName+".FlowChart."+Flow+".Choice.0.Lore").replace("%player%", player.getName());
		String[] script = lore.split("%enter%");
		
		switch(QuestList.getConfigurationSection(QuestName+".FlowChart."+Flow+".Choice").getKeys(false).size())
		{
		case 1:
			Stack2(ChatColor.WHITE + "" + ChatColor.BOLD + "[����]", 72,0,1,Arrays.asList(script), 13, inv);
			break;
		case 2:
			Stack2(ChatColor.WHITE + "" + ChatColor.BOLD + "[����]", 72,0,1,Arrays.asList(script), 12, inv);
			lore = QuestList.getString(QuestName+".FlowChart."+Flow+".Choice.1.Lore").replace("%player%", player.getName());
			script = lore.split("%enter%");
			Stack2(ChatColor.WHITE + "" + ChatColor.BOLD + "[����]", 72,0,2,Arrays.asList(script), 14, inv);
			break;
		case 3:
			Stack2(ChatColor.WHITE + "" + ChatColor.BOLD + "[����]", 72,0,1,Arrays.asList(script), 11, inv);
			lore = QuestList.getString(QuestName+".FlowChart."+Flow+".Choice.1.Lore").replace("%player%", player.getName());
			script = lore.split("%enter%");
			Stack2(ChatColor.WHITE + "" + ChatColor.BOLD + "[����]", 72,0,2,Arrays.asList(script), 13, inv);
			lore = QuestList.getString(QuestName+".FlowChart."+Flow+".Choice.2.Lore").replace("%player%", player.getName());
			script = lore.split("%enter%");
			Stack2(ChatColor.WHITE + "" + ChatColor.BOLD + "[����]", 72,0,3,Arrays.asList(script), 15, inv);
			break;
		case 4:
			Stack2(ChatColor.WHITE + "" + ChatColor.BOLD + "[����]", 72,0,1,Arrays.asList(script), 10, inv);
			lore = QuestList.getString(QuestName+".FlowChart."+Flow+".Choice.1.Lore").replace("%player%", player.getName());
			script = lore.split("%enter%");
			Stack2(ChatColor.WHITE + "" + ChatColor.BOLD + "[����]", 72,0,2,Arrays.asList(script), 12, inv);
			lore = QuestList.getString(QuestName+".FlowChart."+Flow+".Choice.2.Lore").replace("%player%", player.getName());
			script = lore.split("%enter%");
			Stack2(ChatColor.WHITE + "" + ChatColor.BOLD + "[����]", 72,0,3,Arrays.asList(script), 14, inv);
			lore = QuestList.getString(QuestName+".FlowChart."+Flow+".Choice.3.Lore").replace("%player%", player.getName());
			script = lore.split("%enter%");
			Stack2(ChatColor.WHITE + "" + ChatColor.BOLD + "[����]", 72,0,4,Arrays.asList(script), 16, inv);
			break;
		}
		Stack2(ChatColor.WHITE + "" + ChatColor.BOLD + "�ݱ�", 324,0,1,Arrays.asList(ChatColor.GRAY + "â�� �ݽ��ϴ�.",ChatColor.BLACK+QuestName,ChatColor.BLACK+""+Flow), 26, inv);
		player.openInventory(inv);
	}
	
	
	public void QuestGUIClickRouter(InventoryClickEvent event, String InventoryName)
	{
	    GoldBigDragon_RPG.GUI.NPC_GUI NPGUI = new GoldBigDragon_RPG.GUI.NPC_GUI();
		if(InventoryName.compareTo("����Ʈ �ɼ�")==0)
			QuestOptionClick(event);
		else if(InventoryName.compareTo("[����Ʈ]")==0)
			QuestScriptTypeGUIClick(event);
		else if(InventoryName.contains("����"))
			NPGUI.QuestAcceptclickMain(event);
		else if(InventoryName.contains("��ü"))
			OPboxAllQuestListInventoryclick(event);
		else if(InventoryName.contains("���"))
			NPGUI.NPCQuestclickMain(event);
		else if(InventoryName.contains("�帧��"))
			FixQuestListInventoryclick(event);
		else if(InventoryName.contains("�׺�"))
			Quest_NavigationListGUIClick(event);
		else if(InventoryName.contains("����"))
		{
			if(InventoryName.contains("Ȯ��"))
				Quest_OPChoiceClick(event);
			else
				Quest_UserChoiceClick(event);
		}
		else
			MyQuestListInventoryclick(event);
		return;
	}
	
	
	public void OPboxAllQuestListInventoryclick(InventoryClickEvent event)
	{
		GoldBigDragon_RPG.Effect.Sound s = new GoldBigDragon_RPG.Effect.Sound();
		event.setCancelled(true);
		Player player = (Player) event.getWhoClicked();

		Object_UserData u = new Object_UserData();
		
		boolean ChooseQuestGUI = Boolean.parseBoolean(ChatColor.stripColor(event.getInventory().getItem(53).getItemMeta().getLore().get(1)));
		switch ((ChatColor.stripColor(event.getCurrentItem().getItemMeta().getDisplayName())))
		{
			case "���� ������":
				s.SP(player, Sound.ITEM_PICKUP, 0.8F, 1.0F);
				AllOfQuestListGUI(player,(short) (Short.parseShort(event.getInventory().getTitle().split(" : ")[1])-2),ChooseQuestGUI);
				break;
			case "���� ������":
				s.SP(player, Sound.ITEM_PICKUP, 0.8F, 1.0F);
				AllOfQuestListGUI(player, Short.parseShort(event.getInventory().getTitle().split(" : ")[1]),ChooseQuestGUI);
				break;
			case "���� ���":
				s.SP(player, Sound.ITEM_PICKUP, 0.8F, 1.0F);
				if(ChooseQuestGUI == true)
				{
					QuestOptionGUI(player, u.getString(player, (byte)1));
					u.clearAll(player);
				}
				else
				{
					GoldBigDragon_RPG.GUI.OPBoxGUI OGUI = new GoldBigDragon_RPG.GUI.OPBoxGUI();
					OGUI.OPBoxGUI_Main(player,(byte) 1);
				}
				break;
			case "�ݱ�":
				if(ChooseQuestGUI == true)
					u.clearAll(player);
				s.SP(player, Sound.PISTON_RETRACT, 0.8F, 1.8F);
				player.closeInventory();
				break;
			case "�� ����Ʈ":
				s.SP(player, Sound.ITEM_PICKUP, 0.8F, 1.0F);
				player.sendMessage(ChatColor.GOLD + "/����Ʈ ���� [Ÿ��] [�̸�]" );
				player.sendMessage(ChatColor.GREEN + "[Ÿ�� : �Ϲ� / �ݺ� / ���� / ���� / �Ѵ�]");
				player.closeInventory();
				return;
			default:
				if(ChooseQuestGUI == true)
				{
					String QuestName = ChatColor.stripColor(event.getCurrentItem().getItemMeta().getDisplayName());
					YamlController YC = new YamlController(GoldBigDragon_RPG.Main.Main.plugin);
					YamlManager QuestList  = YC.getNewConfig("Quest/QuestList.yml");
					if(QuestName.equalsIgnoreCase(u.getString(player, (byte)1)))
					{
						s.SP(player, Sound.ORB_PICKUP, 1.8F, 1.0F);
						player.sendMessage(ChatColor.RED+"[����Ʈ] : ���� ����Ʈ�� ����� �� �����ϴ�!");
					}
					else
					{
						QuestList.set(u.getString(player, (byte)1)+".Need.PrevQuest", QuestName);
						QuestList.saveConfig();
						QuestOptionGUI(player, u.getString(player, (byte)1));
						u.clearAll(player);
					}
				}
				else
				{
					if(event.getClick().isLeftClick() == true)
					{
						s.SP(player, Sound.ITEM_PICKUP, 0.8F, 1.0F);
						FixQuestGUI(player, (short) 0, ChatColor.stripColor(event.getCurrentItem().getItemMeta().getDisplayName()));
					}
					else if(event.getClick().isRightClick() == true && event.isShiftClick() == true)
					{
						String QuestName = ChatColor.stripColor(event.getCurrentItem().getItemMeta().getDisplayName());
						YamlController YC = new YamlController(GoldBigDragon_RPG.Main.Main.plugin);
						YamlManager QuestList  = YC.getNewConfig("Quest/QuestList.yml");
						QuestList.removeKey(QuestName);
						QuestList.saveConfig();
				    	Collection<? extends Player> playerlist = Bukkit.getServer().getOnlinePlayers();
				    	Player[] a = new Player[playerlist.size()];
				    	playerlist.toArray(a);
		  	  			for(short count = 0; count<a.length;count++)
		  	  			{
		  	  		    	if(a[count].isOnline() == true)
		  	  		    	{
		  						s.SP(a[count], Sound.LAVA_POP, 0.8F, 1.0F);
		  						a[count].sendMessage(ChatColor.LIGHT_PURPLE + "[������] : "+ChatColor.YELLOW + player.getName()+ChatColor.LIGHT_PURPLE + "�Բ��� " + ChatColor.YELLOW + QuestName+ChatColor.LIGHT_PURPLE + "����Ʈ�� �����ϼ̽��ϴ�!");
		  	  		    	}	
		  	  		    }
		  	  			Bukkit.getConsoleSender().sendMessage(ChatColor.YELLOW + player.getName()+ChatColor.LIGHT_PURPLE + "�Բ��� " + ChatColor.YELLOW + QuestName+ChatColor.LIGHT_PURPLE + "����Ʈ�� �����ϼ̽��ϴ�!");
						AllOfQuestListGUI(player,(short) (Integer.parseInt(event.getInventory().getTitle().split(" : ")[1])-1),false);
					}
					else if(event.getClick().isRightClick() == true)
					{
						s.SP(player, Sound.ITEM_PICKUP, 0.8F, 1.0F);
						QuestOptionGUI(player, ChatColor.stripColor(event.getCurrentItem().getItemMeta().getDisplayName()));
					}
				}
				break;
		}
		return;
	}

	public void FixQuestListInventoryclick(InventoryClickEvent event)
	{
		GoldBigDragon_RPG.Effect.Sound s = new GoldBigDragon_RPG.Effect.Sound();
		event.setCancelled(true);
		Player player = (Player) event.getWhoClicked();
		String QuestName = ChatColor.stripColor(event.getInventory().getItem(53).getItemMeta().getLore().get(1));

		int page =  Integer.parseInt(event.getInventory().getTitle().split(" : ")[1])-1;

		switch ((ChatColor.stripColor(event.getCurrentItem().getItemMeta().getDisplayName())))
		{
			case "���� ������":
				s.SP(player, Sound.ITEM_PICKUP, 0.8F, 1.0F);
				FixQuestGUI(player,(short) (Integer.parseInt(event.getInventory().getTitle().split(" : ")[1])-2),QuestName);
				break;
			case "���� ������":
				s.SP(player, Sound.ITEM_PICKUP, 0.8F, 1.0F);
				FixQuestGUI(player,(short) Integer.parseInt(event.getInventory().getTitle().split(" : ")[1]),QuestName);
				break;
			case "���� ���":
				s.SP(player, Sound.ITEM_PICKUP, 0.8F, 1.0F);
				AllOfQuestListGUI(player,(short) 0,false);
				break;
			case "�ݱ�":
				s.SP(player, Sound.PISTON_RETRACT, 0.8F, 1.8F);
				player.closeInventory();
				break;
			case "�� ������Ʈ �߰�":
				s.SP(player, Sound.ITEM_PICKUP, 0.8F, 1.0F);
				SelectObjectPage(player, (byte) 0, QuestName);
				return;
			default:
				s.SP(player, Sound.ITEM_PICKUP, 0.8F, 1.0F);
				short Flow = Short.parseShort(ChatColor.stripColor(event.getCurrentItem().getItemMeta().getDisplayName()));
				if(event.getClick().isLeftClick() == true)
				{
					if(event.getCurrentItem().getItemMeta().getLore().get(0).contains(" : "))
						switch(event.getCurrentItem().getItemMeta().getLore().get(0).split(" : ")[1])
						{
							case "����":
								ShowItemGUI(player, QuestName, (short) Flow,true,false);
								break;
							case "����":
								{
									new Object_UserData().setInt(player, (byte)1,-9);
									ShowItemGUI(player, QuestName, (short) Flow,true,true);
								}
								break;
							case "���":
								KillMonsterGUI(player, QuestName, (short) Flow, player.isOp());
								break;
							case "ä��" :
								HarvestGUI(player, QuestName, (short) Flow, player.isOp());
							case "����":
								Quest_OPChoice(player, QuestName, (short) Flow, (short) page);
								break;
							default :
								break;
						}
				}
				else if(event.getClick().isRightClick() == true && event.isShiftClick() == true)
				{
					YamlController YC = new YamlController(GoldBigDragon_RPG.Main.Main.plugin);
					YamlManager QuestList  = YC.getNewConfig("Quest/QuestList.yml");

					Object[] b = QuestList.getConfigurationSection(QuestName+".FlowChart").getKeys(false).toArray();

					for(short count = Flow; count <= b.length-1;count++)
						QuestList.set(QuestName+".FlowChart."+count,QuestList.get(QuestName+".FlowChart."+(count+1)));
					QuestList.saveConfig();
					FixQuestGUI(player,(short) (Integer.parseInt(event.getInventory().getTitle().split(" : ")[1])-1),QuestName);
				}
				return;
		}
		return;
	}
	
	public void MyQuestListInventoryclick(InventoryClickEvent event)
	{
		GoldBigDragon_RPG.Effect.Sound s = new GoldBigDragon_RPG.Effect.Sound();
		event.setCancelled(true);
		Player player = (Player) event.getWhoClicked();
		
		switch ((ChatColor.stripColor(event.getCurrentItem().getItemMeta().getDisplayName())))
		{
			case "���� ������":
				s.SP(player, Sound.ITEM_PICKUP, 0.8F, 1.0F);
				MyQuestListGUI(player,(short) (Integer.parseInt(event.getInventory().getTitle().split(" : ")[1])-2));
				break;
			case "���� ������":
				s.SP(player, Sound.ITEM_PICKUP, 0.8F, 1.0F);
				MyQuestListGUI(player,(short) Integer.parseInt(event.getInventory().getTitle().split(" : ")[1]));
				break;
			case "���� ���":
				GoldBigDragon_RPG.GUI.StatsGUI SGUI = new GoldBigDragon_RPG.GUI.StatsGUI();
				s.SP(player, Sound.ITEM_PICKUP, 0.8F, 1.0F);
				SGUI.StatusGUI(player);
				break;
			case "�ݱ�":
				s.SP(player, Sound.PISTON_RETRACT, 0.8F, 1.8F);
				player.closeInventory();
				break;
			default:
				s.SP(player, Sound.ITEM_PICKUP, 0.8F, 1.0F);
				String QuestName = ChatColor.stripColor(event.getCurrentItem().getItemMeta().getDisplayName());
				YamlController YC = new YamlController(GoldBigDragon_RPG.Main.Main.plugin);
				YamlManager PlayerQuestList  = YC.getNewConfig("Quest/PlayerData/"+player.getUniqueId()+".yml");
				int Flow = PlayerQuestList.getInt("Started."+QuestName+".Flow");
				if(event.getCurrentItem().getItemMeta().getLore().toString().contains("����") == true)
					ShowItemGUI(player, QuestName, (short) Flow,false,false);
				else if(event.getCurrentItem().getItemMeta().getLore().toString().contains("����") == true)
					ShowItemGUI(player, QuestName, (short) Flow,false,true);
				else if(event.getCurrentItem().getItemMeta().getLore().toString().contains("óġ") == true)
					KillMonsterGUI(player, QuestName, (short) Flow, player.isOp());
				else if(event.getCurrentItem().getItemMeta().getLore().toString().contains("����") == true)
					QuestTypeRouter(player, QuestName);
				else if(event.getCurrentItem().getItemMeta().getLore().toString().contains("ä��") == true)
					HarvestGUI(player, QuestName, (short) Flow, false);
				else if(event.getCurrentItem().getItemMeta().getLore().toString().contains("������") == true)
					Quest_UserChoice(player, QuestName, (short) Flow);
				
				break;
		}
		return;
	}

	public void ObjectAddInventoryClick(InventoryClickEvent event)
	{
		GoldBigDragon_RPG.Effect.Sound s = new GoldBigDragon_RPG.Effect.Sound();
		event.setCancelled(true);
		Player player = (Player) event.getWhoClicked();

		Object_UserData u = new Object_UserData();
		
		String QuestName = ChatColor.stripColor(event.getInventory().getItem(53).getItemMeta().getLore().get(1));
		YamlController YC = new YamlController(GoldBigDragon_RPG.Main.Main.plugin);
		YamlManager QuestConfig=YC.getNewConfig("Quest/QuestList.yml");
		short size = (short) QuestConfig.getConfigurationSection(QuestName+".FlowChart").getKeys(false).size();

		switch ((ChatColor.stripColor(event.getCurrentItem().getItemMeta().getDisplayName())))
		{
			case "����":
				s.SP(player, org.bukkit.Sound.ITEM_PICKUP, 0.5F,1.2F);
				u.setType(player, "Quest");
				u.setString(player, (byte)1,"CV");
				u.setString(player, (byte)2,QuestName);
				player.sendMessage(ChatColor.GREEN + "[����Ʈ] : �÷����� �������� �� ���� �����ұ��?");
				player.sendMessage(ChatColor.GRAY + "(�ּ� -1000 ~ �ִ� 30000)");
				player.closeInventory();
				return;
			case "����":
				s.SP(player, org.bukkit.Sound.ITEM_PICKUP, 0.5F,1.2F);
				u.setType(player, "Quest");
				u.setString(player, (byte)1,"CS");
				u.setString(player, (byte)2,QuestName);
				player.sendMessage(ChatColor.GREEN + "[����Ʈ] : �� ���� �������� ���� �� �ǰ���?");
				player.sendMessage(ChatColor.GRAY + "(�ּ� 1�� ~ �ִ� 4��)");
				player.closeInventory();
				return;
			case "�׺�":
				s.SP(player, org.bukkit.Sound.ITEM_PICKUP, 0.5F,1.2F);
				Quest_NavigationListGUI(player, (short) 0, QuestName);
				return;
			case "���":
			case "����":
				s.SP(player, org.bukkit.Sound.ITEM_PICKUP, 0.5F,1.2F);
				u.setType(player, "Quest");
				if(ChatColor.stripColor(event.getCurrentItem().getItemMeta().getDisplayName()).equalsIgnoreCase("���"))
					u.setString(player, (byte)1,"Script");
				else
					u.setString(player, (byte)1,"PScript");
				u.setString(player, (byte)2,QuestName);
				player.sendMessage(ChatColor.GREEN + "[SYSTEM] : ����� ��縦 ä��â�� �Է��ϼ���!");
				player.sendMessage(ChatColor.GOLD + "%enter%"+ChatColor.WHITE + " - ���� ��� ���� -");
				player.sendMessage(ChatColor.GOLD + "%player%"+ChatColor.WHITE + " - �÷��̾� ��Ī�ϱ� -");
				player.sendMessage(ChatColor.GOLD + "%value%"+ChatColor.WHITE + " - �÷��̾��� ���� ����Ʈ ���� ��Ī�ϱ� -");
				player.sendMessage(ChatColor.WHITE + ""+ChatColor.BOLD + "&l " + ChatColor.BLACK + "&0 "+ChatColor.DARK_BLUE+"&1 "+ChatColor.DARK_GREEN+"&2 "+
				ChatColor.DARK_AQUA + "&3 " +ChatColor.DARK_RED + "&4 " + ChatColor.DARK_PURPLE + "&5 " +
						ChatColor.GOLD + "&6 " + ChatColor.GRAY + "&7 " + ChatColor.DARK_GRAY + "&8 " +
				ChatColor.BLUE + "&9 " + ChatColor.GREEN + "&a " + ChatColor.AQUA + "&b " + ChatColor.RED + "&c" +
						ChatColor.LIGHT_PURPLE + "&d " + ChatColor.YELLOW + "&e "+ChatColor.WHITE + "&f");
				player.closeInventory();
				return;
			case "�湮":
				YamlManager AreaList = YC.getNewConfig("Area/AreaList.yml");
				Object[] arealist =AreaList.getConfigurationSection("").getKeys(false).toArray();

				if(arealist.length <= 0)
				{
					s.SP(player, org.bukkit.Sound.ORB_PICKUP, 2.0F, 1.7F);
					player.sendMessage(ChatColor.RED + "[����Ʈ] : ������ ������ �����ϴ�!");
					player.sendMessage(ChatColor.GOLD + "/���� <�̸�> ����"+ChatColor.YELLOW + " - ���ο� ������ �����մϴ�. -");
					player.closeInventory();
					return;
				}
				player.sendMessage(ChatColor.GREEN + "���������������������� ��Ϧ�����������������");
				for(short count =0; count <arealist.length;count++)
				{
					player.sendMessage(ChatColor.GREEN +"  "+ arealist[count].toString());
				}
				player.sendMessage(ChatColor.GREEN + "���������������������� ��Ϧ�����������������");
				player.sendMessage(ChatColor.DARK_AQUA + "[����Ʈ] : �湮�ؾ� �� ���� �̸��� ���� �ּ���!");
				s.SP(player, org.bukkit.Sound.ORB_PICKUP, 2.0F, 1.7F);
				u.setType(player, "Quest");
				u.setString(player, (byte)1,"Visit");
				u.setString(player, (byte)2,QuestName);
				player.closeInventory();
				return;
			case "����":
				u.setType(player, "Quest");
				u.setString(player, (byte)1,"Give");
				u.setString(player, (byte)3,QuestName);
				s.SP(player, org.bukkit.Sound.ITEM_PICKUP, 0.5F,1.2F);
				player.sendMessage(ChatColor.GREEN + "[SYSTEM] : "+ChatColor.RED+"����ǰ�� ���� �غ��Ͻ� ��,"+ChatColor.GREEN+" ���� NPC�� ��Ŭ�� �ϼ���!");
				player.closeInventory();
				return;
			case "���":
				u.setType(player, "Quest");
				u.setString(player, (byte)1,"Hunt");
				u.setString(player, (byte)2,QuestName);
				u.setString(player, (byte)3,"N");
				u.setInt(player, (byte)2, -1);
				u.setInt(player, (byte)3, -1);
				player.sendMessage(ChatColor.GREEN + "[SYSTEM] : ��� �ؾ� �� ���͸� ��Ŭ�� �ϼ���!");
				player.closeInventory();
				return;
			case "��ȭ":
				u.setType(player, "Quest");
				u.setString(player, (byte)1,"Talk");
				u.setString(player, (byte)2,QuestName);
				player.sendMessage(ChatColor.GREEN + "[SYSTEM] : ��ȭ �ؾ� �� NPC�� ��Ŭ�� �ϼ���!");
				player.closeInventory();
				return;
			case "����":
				u.setType(player, "Quest");
				u.setString(player, (byte)1,"Present");
				u.setString(player, (byte)3,QuestName);
				u.setInt(player, (byte)5, -1);
				player.sendMessage(ChatColor.GREEN + "[SYSTEM] : "+ChatColor.RED+"���� �������� ���� �غ��Ͻ� ��,"+ChatColor.GREEN+" ������ �� NPC�� ��Ŭ�� �ϼ���!");
				player.closeInventory();
				return;
			case "�̵�":
				u.setType(player, "Quest");
				u.setString(player, (byte)1,"TelePort");
				u.setString(player, (byte)3,QuestName);
				player.sendMessage(ChatColor.GREEN + "[SYSTEM] : �÷��̾ �̵� ��ų ��ġ�� ��Ŭ�� �ϼ���!");
				player.closeInventory();
				return;
			case "ä��":
				u.setType(player, "Quest");
				u.setString(player, (byte)1,"Harvest");
				u.setString(player, (byte)2,QuestName);
				u.setString(player, (byte)3,"null");

				u.setInt(player, (byte)1, 0);//��� ID
				u.setInt(player, (byte)2, 0);//��� DATA
				u.setInt(player, (byte)3, -1);//������ ���
				u.setInt(player, (byte)4, -1);//������ ���
				player.sendMessage(ChatColor.GREEN + "[SYSTEM] : ä���ؾ� �� ����� ��Ŭ�� �ϼ���!");
				player.closeInventory();
				return;
			case "���":
				s.SP(player, org.bukkit.Sound.ITEM_PICKUP, 0.5F,1.2F);
				u.setType(player, "Quest");
				u.setString(player, (byte)1,"BlockPlace");
				u.setString(player, (byte)2,QuestName);
				u.setString(player, (byte)3,"null");
				
				u.setInt(player, (byte)1, 0);//��� ID
				u.setInt(player, (byte)2, 0);//��� DATA
				player.sendMessage(ChatColor.GREEN + "[����Ʈ] : ����� ��ġ�� ������ ��Ŭ�� �ϼ���!");
				player.closeInventory();
				return;
			case "�Ҹ�":
				return;
			case "�Ӹ�":
				s.SP(player, org.bukkit.Sound.ITEM_PICKUP, 0.5F,1.2F);
				u.setType(player, "Quest");
				u.setString(player, (byte)1,"Whisper");
				u.setString(player, (byte)2,QuestName);
				player.sendMessage(ChatColor.GREEN + "[����Ʈ] : � �޽����� �����ϰ� �����Ű���?");
				player.sendMessage(ChatColor.GOLD + "%player%"+ChatColor.WHITE + " - �÷��̾� ��Ī�ϱ� -");
				player.sendMessage(ChatColor.GOLD + "%value%"+ChatColor.WHITE + " - �÷��̾��� ���� ����Ʈ ���� ��Ī�ϱ� -");
				player.sendMessage(ChatColor.WHITE + ""+ChatColor.BOLD + "&l " + ChatColor.BLACK + "&0 "+ChatColor.DARK_BLUE+"&1 "+ChatColor.DARK_GREEN+"&2 "+
				ChatColor.DARK_AQUA + "&3 " +ChatColor.DARK_RED + "&4 " + ChatColor.DARK_PURPLE + "&5 " +
						ChatColor.GOLD + "&6 " + ChatColor.GRAY + "&7 " + ChatColor.DARK_GRAY + "&8 " +
				ChatColor.BLUE + "&9 " + ChatColor.GREEN + "&a " + ChatColor.AQUA + "&b " + ChatColor.RED + "&c" +
						ChatColor.LIGHT_PURPLE + "&d " + ChatColor.YELLOW + "&e "+ChatColor.WHITE + "&f");
				player.closeInventory();
				return;
			case "��ü":
				s.SP(player, org.bukkit.Sound.ITEM_PICKUP, 0.5F,1.2F);
				u.setType(player, "Quest");
				u.setString(player, (byte)1,"BroadCast");
				u.setString(player, (byte)2,QuestName);
				player.sendMessage(ChatColor.GREEN + "[����Ʈ] : � �޽����� �����ϰ� �����Ű���?");
				player.sendMessage(ChatColor.GOLD + "%player%"+ChatColor.WHITE + " - �÷��̾� ��Ī�ϱ� -");
				player.sendMessage(ChatColor.GOLD + "%value%"+ChatColor.WHITE + " - �÷��̾��� ���� ����Ʈ ���� ��Ī�ϱ� -");
				player.sendMessage(ChatColor.WHITE + ""+ChatColor.BOLD + "&l " + ChatColor.BLACK + "&0 "+ChatColor.DARK_BLUE+"&1 "+ChatColor.DARK_GREEN+"&2 "+
				ChatColor.DARK_AQUA + "&3 " +ChatColor.DARK_RED + "&4 " + ChatColor.DARK_PURPLE + "&5 " +
						ChatColor.GOLD + "&6 " + ChatColor.GRAY + "&7 " + ChatColor.DARK_GRAY + "&8 " +
				ChatColor.BLUE + "&9 " + ChatColor.GREEN + "&a " + ChatColor.AQUA + "&b " + ChatColor.RED + "&c" +
						ChatColor.LIGHT_PURPLE + "&d " + ChatColor.YELLOW + "&e "+ChatColor.WHITE + "&f");
				player.closeInventory();
				return;
			case "���� ���":
				s.SP(player, Sound.ITEM_PICKUP, 0.8F, 1.0F);
				FixQuestGUI(player,(short) 0,QuestName);
				return;
			case "�ݱ�":
				s.SP(player, Sound.PISTON_RETRACT, 0.8F, 1.8F);
				player.closeInventory();
				return;
		}

		if(size != 0)
		{
			switch ((ChatColor.stripColor(event.getCurrentItem().getItemMeta().getDisplayName())))
			{
			case "���":
			{
				s.SP(player, org.bukkit.Sound.ITEM_PICKUP, 0.5F,1.2F);
				u.setType(player, "Quest");
				u.setString(player, (byte)1,"Cal");
				u.setString(player, (byte)2,QuestName);
				player.sendMessage(ChatColor.GREEN + "[����Ʈ] : � ������ �Ͻð� ��������?");
				player.sendMessage(ChatColor.GOLD+""+ChatColor.BOLD+"1. "+ChatColor.WHITE + "A �� B (�÷��̾� ������ B�� ���մϴ�.)");
				player.sendMessage(ChatColor.GOLD+""+ChatColor.BOLD+"2. "+ChatColor.WHITE + "A �� B (�÷��̾� ������ B�� ���ϴ�.)");
				player.sendMessage(ChatColor.GOLD+""+ChatColor.BOLD+"3. "+ChatColor.WHITE + "A �� B (�÷��̾� ������ B�� ���մϴ�.)");
				player.sendMessage(ChatColor.GOLD+""+ChatColor.BOLD+"4. "+ChatColor.WHITE + "A �� B (�÷��̾� ������ B�� ���� ���� ���մϴ�.)");
				player.sendMessage(ChatColor.GOLD+""+ChatColor.BOLD+"5. "+ChatColor.WHITE + "A �� B (�÷��̾� ������ B�� ���� �������� ���մϴ�.)");
				player.sendMessage(ChatColor.GRAY + "(�ּ� 1 ~ �ִ� 5 ���� �� �Է�)");
				player.closeInventory();
			}
			return;
			case "����Ʈ �ʱ�ȭ":
			{
				
				s.SP(player, org.bukkit.Sound.ITEM_PICKUP, 0.5F,1.2F);
				QuestConfig.set(QuestName+".FlowChart."+size+".Type", "QuestReset");
		    	QuestConfig.saveConfig();
				FixQuestGUI(player, (short) 0, QuestName);
			}
			break;
			case "����Ʈ ����":
				{
					s.SP(player, org.bukkit.Sound.ITEM_PICKUP, 0.5F,1.2F);
					QuestConfig.set(QuestName+".FlowChart."+size+".Type", "QuestFail");
	    	    	QuestConfig.saveConfig();
	    			FixQuestGUI(player, (short) 0, QuestName);
				}
			break;
			case "IF":
			{
				s.SP(player, org.bukkit.Sound.ITEM_PICKUP, 0.5F,1.2F);
				u.setType(player, "Quest");
				u.setString(player, (byte)1,"IFTS");
				u.setString(player, (byte)2,QuestName);
				player.sendMessage(ChatColor.GREEN + "[����Ʈ] : � �񱳸� �Ͻð� ��������?");
				player.sendMessage(ChatColor.GOLD+""+ChatColor.BOLD+"1. "+ChatColor.WHITE + "A == B (�÷��̾� ������ B�� ������?)");
				player.sendMessage(ChatColor.GOLD+""+ChatColor.BOLD+"2. "+ChatColor.WHITE + "A != B (�÷��̾� ������ B�� �ٸ���?)");
				player.sendMessage(ChatColor.GOLD+""+ChatColor.BOLD+"3. "+ChatColor.WHITE + "A > B (�÷��̾� ������ B���� ū��?)");
				player.sendMessage(ChatColor.GOLD+""+ChatColor.BOLD+"4. "+ChatColor.WHITE + "A < B (�÷��̾� ������ B���� ������?)");
				player.sendMessage(ChatColor.GOLD+""+ChatColor.BOLD+"5. "+ChatColor.WHITE + "A >= B (�÷��̾� ������ B���� ũ�ų� ������?)");
				player.sendMessage(ChatColor.GOLD+""+ChatColor.BOLD+"6. "+ChatColor.WHITE + "A <= B (�÷��̾� ������ B���� �۰ų� ������?)");
				player.sendMessage(ChatColor.GOLD+""+ChatColor.BOLD+"7. "+ChatColor.WHITE + "C <= A <= B (�÷��̾� ������ �ּ� C ~ �ִ� B �����ΰ�?)");
				player.sendMessage(ChatColor.GRAY + "(�ּ� 1 ~ �ִ� 7 ���� �� �Է�)");
				player.closeInventory();
			}
			break;
			case "ELSE":
			{
				s.SP(player, org.bukkit.Sound.ITEM_PICKUP, 0.5F,1.2F);
				QuestConfig.set(QuestName+".FlowChart."+size+".Type", "ELSE");
    	    	QuestConfig.saveConfig();
    			FixQuestGUI(player, (short) 0, QuestName);
			}
			break;
			case "ENDIF":
			{
				s.SP(player, org.bukkit.Sound.ITEM_PICKUP, 0.5F,1.2F);
				QuestConfig.set(QuestName+".FlowChart."+size+".Type", "ENDIF");
    	    	QuestConfig.saveConfig();
    			FixQuestGUI(player, (short) 0, QuestName);
			}
			break;
			}
		}
		else
		{
			s.SP(player, Sound.ORB_PICKUP, 1.0F, 1.8F);
			player.sendMessage(ChatColor.RED+ "[����Ʈ] : �ش� �׸��� ù ��° ���� ��ҷ� �� �� �����ϴ�!");
		}
		return;
	}

	public void QuestScriptTypeGUIClick(InventoryClickEvent event)
	{
		GoldBigDragon_RPG.Effect.Sound s = new GoldBigDragon_RPG.Effect.Sound();
		event.setCancelled(true);
		Player player = (Player) event.getWhoClicked();
		String QuestName = ChatColor.stripColor(event.getInventory().getItem(19).getItemMeta().getDisplayName());
		
		switch (event.getSlot())
		{
			case 26:
				s.SP(player, Sound.PISTON_RETRACT, 0.8F, 1.8F);
				player.closeInventory();
				break;
			case 13:
				s.SP(player, Sound.ITEM_PICKUP, 0.8F, 1.8F);
				YamlController YC = new YamlController(GoldBigDragon_RPG.Main.Main.plugin);
				YamlManager PlayerQuestList  = YC.getNewConfig("Quest/PlayerData/"+player.getUniqueId()+".yml");
				PlayerQuestList.set("Started."+QuestName+".Flow", PlayerQuestList.getInt("Started."+QuestName+".Flow")+1);
				PlayerQuestList.saveConfig();
				player.closeInventory();
				QuestTypeRouter(player, QuestName);
				break;
		}
		return;
	}
	
	public void ShowItemGUIInventoryClick(InventoryClickEvent event)
	{
		GoldBigDragon_RPG.Effect.Sound s = new GoldBigDragon_RPG.Effect.Sound();
		event.setCancelled(true);
		Player player = (Player) event.getWhoClicked();
		switch (event.getSlot())
		{
		case 18:
			s.SP(player, Sound.ITEM_PICKUP, 0.8F, 1.8F);
			if(ChatColor.stripColor(event.getCurrentItem().getItemMeta().getLore().get(1)).equalsIgnoreCase("false"))
				MyQuestListGUI(player, (short) 0);
			else
				FixQuestGUI(player, (short) 0, ChatColor.stripColor(event.getInventory().getItem(26).getItemMeta().getLore().get(1)));
			break;
		case 22:
				event.setCancelled(true);

				YamlController YC = new YamlController(GoldBigDragon_RPG.Main.Main.plugin);
				YamlManager YM,QuestList  = YC.getNewConfig("Quest/QuestList.yml");
				YamlManager PlayerQuestList  = YC.getNewConfig("Quest/PlayerData/"+player.getUniqueId()+".yml");
				
				String QuestName =  ChatColor.stripColor(event.getInventory().getItem(26).getItemMeta().getLore().get(1));
				short QuestFlow =  Short.parseShort(ChatColor.stripColor(event.getInventory().getItem(22).getItemMeta().getLore().get(1)));

				if(QuestList.contains(QuestName+".FlowChart."+QuestFlow+".Item") == true)
				{
					Object[] p =QuestList.getConfigurationSection(QuestName+".FlowChart."+QuestFlow+".Item").getKeys(false).toArray();
					short emptySlot = 0;
					ItemStack item[] = new ItemStack[p.length];
					
					for(int counter = 0; counter < p.length; counter++)
						item[counter] = QuestList.getItemStack(QuestName+".FlowChart."+QuestFlow+".Item."+counter);
					
					for(int counter = 0; counter < player.getInventory().getSize(); counter++)
					{
						if(player.getInventory().getItem(counter) == null)
							emptySlot++;
					}
					
					if(emptySlot >= item.length)
					{
						for(short counter = 0;counter < p.length; counter++)
							player.getInventory().addItem(item[counter]);
					}
					else
					{
						s.SP(player, org.bukkit.Sound.ORB_PICKUP, 2.0F, 1.7F);
						player.sendMessage(ChatColor.YELLOW + "[����Ʈ] : ���� �÷��̾��� �κ��丮 ������ ������� �ʾ� ������ ���� �� �����ϴ�!");
						return;
					}
				}
				GoldBigDragon_RPG.Main.ServerOption.PlayerList.get(player.getUniqueId().toString()).addStat_MoneyAndEXP(QuestList.getLong(QuestName + ".FlowChart."+QuestFlow+".Money"), 0, false);

		    	if(YC.isExit("NPC/PlayerData/"+player.getUniqueId()+".yml")==false)
		    	{
		    		YM=YC.getNewConfig("NPC/PlayerData/"+player.getUniqueId()+".yml");
		    		YM.set(QuestList.getString(QuestName + ".FlowChart."+QuestFlow+".TargetNPCuuid")+".love", QuestList.getInt(QuestName + ".FlowChart."+QuestFlow+".Love"));
		    		YM.set(QuestList.getString(QuestName + ".FlowChart."+QuestFlow+".TargetNPCuuid")+".Career", 0);
		    		YM.saveConfig();
		    	}
		    	else
		    	{
		    		YM=YC.getNewConfig("NPC/PlayerData/"+player.getUniqueId()+".yml");
		    		int ownlove = YM.getInt(QuestList.getString(QuestName + ".FlowChart."+QuestFlow+".TargetNPCuuid")+".love");
		    		int owncareer = YM.getInt(QuestList.getString(QuestName + ".FlowChart."+QuestFlow+".TargetNPCuuid")+".Career");
		    		YM.set(QuestList.getString(QuestName + ".FlowChart."+QuestFlow+".TargetNPCuuid")+".love", ownlove +QuestList.getInt(QuestName + ".FlowChart."+QuestFlow+".Love"));
		    		YM.set(QuestList.getString(QuestName + ".FlowChart."+QuestFlow+".TargetNPCuuid")+".Career", owncareer +QuestList.getInt(QuestName + ".FlowChart."+QuestFlow+".Career"));
		    		YM.saveConfig();
		    	}
	    		if(QuestList.getInt(QuestName + ".FlowChart."+QuestFlow+".EXP") != 0)
	    			new GoldBigDragon_RPG.Util.PlayerUtil().addMoneyAndEXP(player, 0, QuestList.getLong(QuestName + ".FlowChart."+QuestFlow+".EXP"), null, false, false);
				
				event.setCancelled(true);
				PlayerQuestList.set("Started."+QuestName+".Flow", PlayerQuestList.getInt("Started."+QuestName+".Flow")+1);
				PlayerQuestList.saveConfig();
				player.closeInventory();
				QuestTypeRouter(player, QuestName);
			break;
		case 26:
			s.SP(player, Sound.PISTON_RETRACT, 0.8F, 1.8F);
			player.closeInventory();
			break;
		}
		return;
	}
	
	public void SettingPresentClick(InventoryClickEvent event)
	{
		GoldBigDragon_RPG.Effect.Sound s = new GoldBigDragon_RPG.Effect.Sound();
		Player player = (Player) event.getWhoClicked();
		Object_UserData u = new Object_UserData();
		switch (event.getSlot())
		{
		case 0:
			event.setCancelled(true);
			player.sendMessage(ChatColor.GREEN + "[SYSTEM] : ������ ������� �Է� �� �ּ���. ("+ChatColor.YELLOW + "0"+ChatColor.GREEN+" ~ "+ChatColor.YELLOW+""+Integer.MAX_VALUE+ChatColor.GREEN+")");
			s.SP(player, Sound.PISTON_RETRACT, 0.8F, 1.8F);
			u.setType(player, "Quest");
			u.setString(player, (byte)4,"M");
			player.closeInventory();
			break;
		case 1:
			event.setCancelled(true);
			u.setInt(player, (byte)2, 0);
			player.sendMessage(ChatColor.GREEN + "[SYSTEM] : ��½�ų ����ġ�� �Է� �� �ּ���. ("+ChatColor.YELLOW + "1"+ChatColor.GREEN+" ~ "+ChatColor.YELLOW+""+Integer.MAX_VALUE+ChatColor.GREEN+")");
			s.SP(player, Sound.PISTON_RETRACT, 0.8F, 1.8F);
			u.setType(player, "Quest");
			u.setString(player, (byte)4,"E");
			player.closeInventory();
			break;
		case 2:
			event.setCancelled(true);
			u.setInt(player, (byte)3, 0);
			player.sendMessage(ChatColor.GREEN + "[SYSTEM] : ��½�ų NPC�� ȣ������ �Է� �� �ּ���. ("+ChatColor.YELLOW + "0"+ChatColor.GREEN+" ~ "+ChatColor.YELLOW+""+Integer.MAX_VALUE+ChatColor.GREEN+")");
			s.SP(player, Sound.PISTON_RETRACT, 0.8F, 1.8F);
			u.setType(player, "Quest");
			u.setString(player, (byte)4,"L");
			player.closeInventory();
			break;
		case 8:
			event.setCancelled(true);
			s.SP(player, Sound.PISTON_RETRACT, 0.8F, 1.8F);
			player.closeInventory();
			break;
		default :
			break;
		}
		return;
	}
	
	public void KeepGoingClick(InventoryClickEvent event)
	{
		GoldBigDragon_RPG.Effect.Sound s = new GoldBigDragon_RPG.Effect.Sound();

		Object_UserData u = new Object_UserData();
		
		Player player = (Player) event.getWhoClicked();
		String QuestName = ChatColor.stripColor(event.getInventory().getItem(16).getItemMeta().getLore().get(1));
		short Flow = Short.parseShort(ChatColor.stripColor(event.getInventory().getItem(10).getItemMeta().getLore().get(1)));
		short Mob = Short.parseShort(ChatColor.stripColor(event.getInventory().getItem(10).getItemMeta().getLore().get(2)));
		switch (event.getSlot())
		{
		case 10:
			s.SP(player, Sound.PISTON_RETRACT, 0.8F, 1.8F);
			if(event.getCurrentItem().getItemMeta().getLore().get(0).contains("���"))
			{
				u.setType(player, "Quest");
				u.setString(player, (byte)1,"Hunt");
				u.setString(player, (byte)2,QuestName);
				u.setString(player, (byte)3,"N");
				u.setInt(player, (byte)2, Mob+1);
				u.setInt(player, (byte)3, Flow);
				player.sendMessage(ChatColor.GREEN + "[SYSTEM] : ��� �ؾ� �� ���͸� ��Ŭ�� �ϼ���!");
			}
			else
			{
				u.setType(player, "Quest");
				u.setString(player, (byte)1,"Harvest");
				u.setString(player, (byte)2,QuestName);
				u.setString(player, (byte)3,"null");
				u.setInt(player, (byte)1, 0);
				u.setInt(player, (byte)2, 0);
				u.setInt(player, (byte)3, Flow);
				u.setInt(player, (byte)4, Mob+1);
				player.sendMessage(ChatColor.GREEN + "[SYSTEM] : ä�� �ؾ� �� ����� ��Ŭ�� �ϼ���!");
			}
			player.closeInventory();
			break;
		case 16:
			s.SP(player, Sound.PISTON_RETRACT, 0.8F, 1.8F);
			player.closeInventory();
	    	//FixQuestGUI(player, 0, QuestName);
			break;
		}
		return;
	}
	
	public void QuestOptionClick(InventoryClickEvent event)
	{
		GoldBigDragon_RPG.Effect.Sound s = new GoldBigDragon_RPG.Effect.Sound();

		event.setCancelled(true);
		Player player = (Player) event.getWhoClicked();
		String QuestName = ChatColor.stripColor(event.getInventory().getItem(44).getItemMeta().getLore().get(1));

		Object_UserData u = new Object_UserData();
		
		YamlController YC = new YamlController(GoldBigDragon_RPG.Main.Main.plugin);
		YamlManager QuestList  = YC.getNewConfig("Quest/QuestList.yml");
		switch (event.getSlot())
		{
		case 4://����Ʈ Ÿ��
			switch(QuestList.getString(QuestName + ".Type"))
			{
			case "N" :
				QuestList.set(QuestName+".Type", "R");
				break;
			case "R" :
				QuestList.set(QuestName+".Type", "D");
				break;
			case "D" :
				QuestList.set(QuestName+".Type", "W");
				break;
			case "W" :
				QuestList.set(QuestName+".Type", "M");
				break;
			case "M" :
				QuestList.set(QuestName+".Type", "N");
				break;
			}
			QuestList.saveConfig();
			s.SP(player, Sound.ITEM_PICKUP, 0.8F, 1.8F);
			QuestOptionGUI(player, QuestName);
			return;
		case 11://���� ����
			s.SP(player, Sound.PISTON_RETRACT, 0.8F, 1.8F);
			u.setType(player, "Quest");
			u.setString(player, (byte)1,"Level District");
			u.setString(player, (byte)2,QuestName);
			player.closeInventory();
			player.sendMessage(ChatColor.GREEN + "[SYSTEM] : �� ���� ���� ���� �����ϰ� �Ͻðڽ��ϱ�? ("+ChatColor.YELLOW + "0"+ChatColor.GREEN+" ~ "+ChatColor.YELLOW+""+Integer.MAX_VALUE+ChatColor.GREEN+"����)");
			return;
		case 13://ȣ���� ����
			s.SP(player, Sound.PISTON_RETRACT, 0.8F, 1.8F);
			u.setType(player, "Quest");
			u.setString(player, (byte)1,"Love District");
			u.setString(player, (byte)2,QuestName);
			player.closeInventory();
			player.sendMessage(ChatColor.GREEN + "[SYSTEM] : ȣ���� �� �̻���� ���� �����ϰ� �Ͻðڽ��ϱ�? ("+ChatColor.YELLOW + "0"+ChatColor.GREEN+" ~ "+ChatColor.YELLOW+""+Integer.MAX_VALUE+ChatColor.GREEN+")");
			return;
		case 15://��ų ��ũ ����
			s.SP(player, Sound.PISTON_RETRACT, 0.8F, 1.8F);
			s.SP(player, Sound.ITEM_PICKUP, 0.8F, 1.8F);
			//��ų ���� �� ��ų ��ũ �Է��ϱ�
			return;
		case 20://"+GoldBigDragon_RPG.Main.ServerOption.STR+" ����
			s.SP(player, Sound.PISTON_RETRACT, 0.8F, 1.8F);
			u.setType(player, "Quest");
			u.setString(player, (byte)1,"STR District");
			u.setString(player, (byte)2,QuestName);
			player.closeInventory();
			player.sendMessage(ChatColor.GREEN + "[SYSTEM] : "+GoldBigDragon_RPG.Main.ServerOption.STR+"�� �� �̻� �Ǿ�� ���� �����ϰ� �Ͻðڽ��ϱ�? ("+ChatColor.YELLOW + "0"+ChatColor.GREEN+" ~ "+ChatColor.YELLOW+""+Integer.MAX_VALUE+ChatColor.GREEN+")");
			return;
		case 21://"+GoldBigDragon_RPG.Main.ServerOption.DEX+" ����
			s.SP(player, Sound.PISTON_RETRACT, 0.8F, 1.8F);
			u.setType(player, "Quest");
			u.setString(player, (byte)1,"DEX District");
			u.setString(player, (byte)2,QuestName);
			player.closeInventory();
			player.sendMessage(ChatColor.GREEN + "[SYSTEM] : "+GoldBigDragon_RPG.Main.ServerOption.DEX+"�� �� �̻� �Ǿ�� ���� �����ϰ� �Ͻðڽ��ϱ�? ("+ChatColor.YELLOW + "0"+ChatColor.GREEN+" ~ "+ChatColor.YELLOW+""+Integer.MAX_VALUE+ChatColor.GREEN+")");
			return;
		case 22://"+GoldBigDragon_RPG.Main.ServerOption.INT+" ����
			s.SP(player, Sound.PISTON_RETRACT, 0.8F, 1.8F);
			u.setType(player, "Quest");
			u.setString(player, (byte)1,"INT District");
			u.setString(player, (byte)2,QuestName);
			player.closeInventory();
			player.sendMessage(ChatColor.GREEN + "[SYSTEM] : "+GoldBigDragon_RPG.Main.ServerOption.INT+"�� �� �̻� �Ǿ�� ���� �����ϰ� �Ͻðڽ��ϱ�? ("+ChatColor.YELLOW + "0"+ChatColor.GREEN+" ~ "+ChatColor.YELLOW+""+Integer.MAX_VALUE+ChatColor.GREEN+")");
			return;
		case 23://"+GoldBigDragon_RPG.Main.ServerOption.WILL+" ����
			s.SP(player, Sound.PISTON_RETRACT, 0.8F, 1.8F);
			u.setType(player, "Quest");
			u.setString(player, (byte)1,"WILL District");
			u.setString(player, (byte)2,QuestName);
			player.closeInventory();
			player.sendMessage(ChatColor.GREEN + "[SYSTEM] : "+GoldBigDragon_RPG.Main.ServerOption.WILL+"�� �� �̻� �Ǿ�� ���� �����ϰ� �Ͻðڽ��ϱ�? ("+ChatColor.YELLOW + "0"+ChatColor.GREEN+" ~ "+ChatColor.YELLOW+""+Integer.MAX_VALUE+ChatColor.GREEN+")");
			return;
		case 24://"+GoldBigDragon_RPG.Main.ServerOption.LUK+" ����
			s.SP(player, Sound.PISTON_RETRACT, 0.8F, 1.8F);
			u.setType(player, "Quest");
			u.setString(player, (byte)1,"LUK District");
			u.setString(player, (byte)2,QuestName);
			player.closeInventory();
			player.sendMessage(ChatColor.GREEN + "[SYSTEM] : "+GoldBigDragon_RPG.Main.ServerOption.LUK+"�� �� �̻� �Ǿ�� ���� �����ϰ� �Ͻðڽ��ϱ�? ("+ChatColor.YELLOW + "0"+ChatColor.GREEN+" ~ "+ChatColor.YELLOW+""+Integer.MAX_VALUE+ChatColor.GREEN+")");
			return;
		case 29://�ʼ� �Ϸ� ����Ʈ
			if(event.isLeftClick() == true && event.isShiftClick() == false)
			{
				s.SP(player, Sound.ITEM_PICKUP, 0.8F, 1.8F);
				u.setString(player, (byte)1,QuestName);
				AllOfQuestListGUI(player, (short) 0, true);
			}
			if(event.isRightClick() == true && event.isShiftClick() == true)
			{
				s.SP(player, Sound.LAVA_POP, 0.8F, 1.8F);
				QuestList.set(QuestName+".Need.PrevQuest", "null");
				QuestList.saveConfig();
				QuestOptionGUI(player, QuestName);
			}
			
			return;
		case 33://����Ʈ ����
			s.SP(player, Sound.PISTON_RETRACT, 0.8F, 1.8F);
			u.setType(player, "Quest");
			u.setString(player, (byte)1,"Accept District");
			u.setString(player, (byte)2,QuestName);
			player.closeInventory();
			player.sendMessage(ChatColor.GREEN + "[SYSTEM] : �� �� ���� �����ϰ� �Ͻðڽ��ϱ�? ("+ChatColor.YELLOW + "0"+ChatColor.GREEN+" ~ "+ChatColor.YELLOW+""+Integer.MAX_VALUE+ChatColor.GREEN+"��)");
			return;
		case 36:
			s.SP(player, Sound.ITEM_PICKUP, 0.8F, 1.8F);
			AllOfQuestListGUI(player, (short) 0,false);
			return;
		case 44:
			s.SP(player, Sound.PISTON_RETRACT, 0.8F, 1.8F);
			player.closeInventory();
			return;
		}
	}
	
	public void Quest_NavigationListGUIClick(InventoryClickEvent event)
	{
		GoldBigDragon_RPG.Effect.Sound s = new GoldBigDragon_RPG.Effect.Sound();
		event.setCancelled(true);
		Player player = (Player) event.getWhoClicked();
		short page =  (short) (Short.parseShort(event.getInventory().getTitle().split(" : ")[1])-1);
		String QuestName = ChatColor.stripColor(event.getInventory().getItem(53).getItemMeta().getLore().get(1));
		switch (event.getSlot())
		{
		case 45://���� ���
			s.SP(player, Sound.ITEM_PICKUP, 0.8F, 1.0F);
			SelectObjectPage(player, (byte) 0, QuestName);
			return;
		case 53://������
			s.SP(player, Sound.PISTON_RETRACT, 0.8F, 1.8F);
			player.closeInventory();
			return;
		case 48://���� ������
			s.SP(player, Sound.ITEM_PICKUP, 0.8F, 1.0F);
			Quest_NavigationListGUI(player, (short) (page-1), QuestName);
			return;
		case 50://���� ������
			s.SP(player, Sound.ITEM_PICKUP, 0.8F, 1.0F);
			Quest_NavigationListGUI(player, (short) (page+1), QuestName);
			return;
		default :
			if(event.isLeftClick() == true)
			{
				s.SP(player, Sound.ITEM_PICKUP, 0.8F, 1.0F);
				YamlController YC = new YamlController(GoldBigDragon_RPG.Main.Main.plugin);
				YamlManager QuestConfig=YC.getNewConfig("Quest/QuestList.yml");
	    		int size = QuestConfig.getConfigurationSection(QuestName+".FlowChart").getKeys(false).size();
	    		QuestConfig.set(QuestName+".FlowChart."+size+".Type", "Nevigation");
		    	QuestConfig.set(QuestName+".FlowChart."+size+".NeviUTC",ChatColor.stripColor(event.getCurrentItem().getItemMeta().getDisplayName()));
		    	QuestConfig.saveConfig();
				player.sendMessage(ChatColor.GREEN+"[����Ʈ] : �׺���̼��� ���������� ��ϵǾ����ϴ�!");
				FixQuestGUI(player, (short) 0, QuestName);
			}
			return;
		}
	}
	
	public void Quest_OPChoiceClick(InventoryClickEvent event)
	{
		GoldBigDragon_RPG.Effect.Sound s = new GoldBigDragon_RPG.Effect.Sound();
		event.setCancelled(true);
		Player player = (Player) event.getWhoClicked();

		short page = Short.parseShort(ChatColor.stripColor(event.getInventory().getItem(18).getItemMeta().getLore().get(1)));
		String QuestName = ChatColor.stripColor(event.getInventory().getItem(26).getItemMeta().getLore().get(1));
		switch (event.getSlot())
		{
		case 18://���� ���
			s.SP(player, Sound.ITEM_PICKUP, 0.8F, 1.0F);
			FixQuestGUI(player, page, QuestName);
			return;
		case 26://������
			s.SP(player, Sound.PISTON_RETRACT, 0.8F, 1.8F);
			player.closeInventory();
			return;
		}
	}
	
	public void Quest_UserChoiceClick(InventoryClickEvent event)
	{
		GoldBigDragon_RPG.Effect.Sound s = new GoldBigDragon_RPG.Effect.Sound();
		event.setCancelled(true);
		Player player = (Player) event.getWhoClicked();
		if(event.getSlot() == 26)
		{
			s.SP(player, Sound.PISTON_RETRACT, 0.8F, 1.8F);
			player.closeInventory();
			return;
		}
		else
		{
			YamlController YC = new YamlController(GoldBigDragon_RPG.Main.Main.plugin);
			YamlManager QuestList  = YC.getNewConfig("Quest/QuestList.yml");
			YamlManager PlayerQuestList  = YC.getNewConfig("Quest/PlayerData/"+player.getUniqueId()+".yml");
			YamlManager PlayerVarList  = YC.getNewConfig("Quest/PlayerVar/"+player.getUniqueId()+".yml");
			
			short Flow = Short.parseShort(ChatColor.stripColor(event.getInventory().getItem(26).getItemMeta().getLore().get(2)));
			String QuestName = ChatColor.stripColor(event.getInventory().getItem(26).getItemMeta().getLore().get(1));
			int ChoiceLevel = QuestList.getConfigurationSection(QuestName+".FlowChart."+Flow+".Choice").getKeys(false).size();
			byte Slot = (byte) event.getSlot();
			
			if(event.getCurrentItem()!= null)
				s.SP(player, Sound.ITEM_PICKUP, 1.0F, 1.0F);
			
			if((ChoiceLevel==1&&Slot==13)||(ChoiceLevel==2&&Slot==12)||(ChoiceLevel==3&&Slot==11)||(ChoiceLevel==4&&Slot==10))
				PlayerVarList.set(QuestName, QuestList.getInt(QuestName+".FlowChart."+Flow+".Choice.0.Var"));
			else if((ChoiceLevel==2&&Slot==14)||(ChoiceLevel==3&&Slot==13)||(ChoiceLevel==4&&Slot==12))
				PlayerVarList.set(QuestName, QuestList.getInt(QuestName+".FlowChart."+Flow+".Choice.1.Var"));
			else if((ChoiceLevel==3&&Slot==15)||(ChoiceLevel==4&&Slot==14))
				PlayerVarList.set(QuestName, QuestList.getInt(QuestName+".FlowChart."+Flow+".Choice.2.Var"));
			else if(ChoiceLevel==4&&Slot==16)
				PlayerVarList.set(QuestName, QuestList.getInt(QuestName+".FlowChart."+Flow+".Choice.3.Var"));
			PlayerVarList.saveConfig();
			PlayerQuestList.set("Started."+QuestName+".Flow", PlayerQuestList.getInt("Started."+QuestName+".Flow")+1);
			PlayerQuestList.saveConfig();
			player.closeInventory();
			QuestTypeRouter(player, QuestName);
		}
	}
	
	
	public void ItemAddInvnetoryClose(InventoryCloseEvent event)
	{
		Player player = (Player)event.getPlayer();
		Object_UserData u = new Object_UserData();
		u.setBoolean(player, (byte)1, false);
		GoldBigDragon_RPG.Effect.Sound s = new GoldBigDragon_RPG.Effect.Sound();
		YamlController YC = new YamlController(GoldBigDragon_RPG.Main.Main.plugin);
		YamlManager QuestConfig=YC.getNewConfig("Quest/QuestList.yml");
		
		String QuestName = u.getString(player, (byte)3);
		Object[] b = QuestConfig.getConfigurationSection(QuestName+".FlowChart").getKeys(false).toArray();
		QuestConfig.set(QuestName+".FlowChart."+b.length+".Type", "Give");
		QuestConfig.set(QuestName+".FlowChart."+b.length+".TargetNPCname", u.getString(player, (byte)2));
		QuestConfig.set(QuestName+".FlowChart."+b.length+".TargetNPCuuid", u.getString(player, (byte)1));
		byte itemacount = 0;
		for(byte count = 0; count <8; count++)
		{
			if(event.getInventory().getItem(count) != null)
			{
				if(event.getInventory().getItem(count).getAmount() > 0)
				{
					QuestConfig.set(QuestName+".FlowChart."+b.length+".Item."+itemacount, event.getInventory().getItem(count));
					itemacount++;
				}
			}
		}
		QuestConfig.saveConfig();
		s.SP((Player) event.getPlayer(), org.bukkit.Sound.ITEM_PICKUP, 0.5F,1.2F);
    	event.getPlayer().sendMessage(ChatColor.GREEN + "[SYSTEM] : ���������� ��ϵǾ����ϴ�!");
    	//FixQuestGUI((Player) event.getPlayer(), 0, Main.PSHM.get(player).get("Quest").get("3"));
		u.clearAll(player);
		return;
	}

	public void PresentAddInvnetoryClose(InventoryCloseEvent event)
	{
		GoldBigDragon_RPG.Effect.Sound s = new GoldBigDragon_RPG.Effect.Sound();
		Player player = (Player)event.getPlayer();
		YamlController YC = new YamlController(GoldBigDragon_RPG.Main.Main.plugin);
		YamlManager QuestConfig=YC.getNewConfig("Quest/QuestList.yml");
		Object_UserData u = new Object_UserData();
		String QuestName = u.getString(player, (byte)3);
		
		if(u.getInt(player, (byte)5) == -1)
		{
			Object[] b = QuestConfig.getConfigurationSection(QuestName+".FlowChart").getKeys(false).toArray();
			u.setInt(player, (byte)5, b.length);
			QuestConfig.set(QuestName+".FlowChart."+b.length+".Type", "Present");
			QuestConfig.set(QuestName+".FlowChart."+b.length+".TargetNPCname", u.getString(player, (byte)2));
			QuestConfig.set(QuestName+".FlowChart."+b.length+".TargetNPCuuid", u.getString(player, (byte)1));
			byte itemacount = 0;
			for(byte count = 3; count <8; count++)
			{
				if(event.getInventory().getItem(count) != null)
				{
					if(event.getInventory().getItem(count).getAmount() > 0)
					{
						QuestConfig.set(QuestName+".FlowChart."+b.length+".Item."+itemacount, event.getInventory().getItem(count));
						itemacount++;
					}
				}
			}
			QuestConfig.saveConfig();
		}
		else
		{
			if(u.getInt(player, (byte)1) == -1)
				u.setInt(player, (byte)1, 0);
			if(u.getInt(player, (byte)2) == -1)
				u.setInt(player, (byte)2, 0);
			if(u.getInt(player, (byte)3) == -1)
				u.setInt(player, (byte)3, 0);
			
			QuestConfig.set(QuestName+".FlowChart."+u.getInt(player, (byte)5)+".Type", "Present");
			QuestConfig.set(QuestName+".FlowChart."+u.getInt(player, (byte)5)+".TargetNPCname", u.getString(player, (byte)2));
			QuestConfig.set(QuestName+".FlowChart."+u.getInt(player, (byte)5)+".TargetNPCuuid", u.getString(player, (byte)1));
			QuestConfig.set(QuestName+".FlowChart."+u.getInt(player, (byte)5)+".Money", u.getInt(player, (byte)1));
			QuestConfig.set(QuestName+".FlowChart."+u.getInt(player, (byte)5)+".EXP", u.getInt(player, (byte)2));
			QuestConfig.set(QuestName+".FlowChart."+u.getInt(player, (byte)5)+".Love", u.getInt(player, (byte)3));
			byte itemacount = 0;
			for(byte count = 3; count <8; count++)
			{
				if(event.getInventory().getItem(count) != null)
				{
					if(event.getInventory().getItem(count).getAmount() > 0)
					{
						QuestConfig.set(QuestName+".FlowChart."+u.getInt(player, (byte)5)+".Item."+itemacount, event.getInventory().getItem(count));
						itemacount++;
					}
				}
			}
			QuestConfig.saveConfig();
		}
		if(u.getString(player, (byte)4)==null)
		{
			QuestConfig.saveConfig();
			s.SP((Player) event.getPlayer(), org.bukkit.Sound.ITEM_PICKUP, 0.5F,1.2F);
	    	event.getPlayer().sendMessage(ChatColor.GREEN + "[SYSTEM] : ���������� �����Ǿ����ϴ�!");
	    	u.clearAll(player);
		}
		return;
	}
	
	public String SkullType(String s)
	{
		String re = s;
		switch(s)
		{
			case "Zombie" : re = ChatColor.DARK_GREEN+"����"; break;
			case "Skeleton" : re = ChatColor.GRAY+"���̷���"; break;
			case "Giant" : re = ChatColor.DARK_GREEN+"���̾�Ʈ"; break;
			case "Spider" : re = ChatColor.GRAY+"�Ź�"; break;
			case "Witch" : re = ChatColor.DARK_PURPLE+"����"; break;
			case "Creeper" : re = ChatColor.GREEN+"ũ����"; break;
			case "Slime" : re = ChatColor.GREEN+"������"; break;
			case "Ghast" : re = ChatColor.GRAY+"����Ʈ"; break;
			case "Enderman" : re = ChatColor.DARK_PURPLE+"������"; break;
			case "Zombie Pigman" : re = ChatColor.LIGHT_PURPLE+"���� �Ǳ׸�"; break;
			case "Cave Spider" : re = ChatColor.GRAY+"���� �Ź�"; break;
			case "Silverfish" : re = ChatColor.GRAY+"������"; break;
			case "Blaze" : re = ChatColor.YELLOW+"������"; break;
			case "Magma Cube" : re = ChatColor.YELLOW+"���׸� ť��"; break;
			case "Bat" : re = ChatColor.GRAY+"����"; break;
			case "Endermite" : re = ChatColor.DARK_PURPLE+"���� �����"; break;
			case "Guardian" : re = ChatColor.DARK_AQUA+"�����"; break;
			case "Pig" : re = ChatColor.LIGHT_PURPLE+"����"; break;
			case "Sheep" : re = ChatColor.WHITE+"��"; break;
			case "Cow" : re = ChatColor.GRAY+"��"; break;
			case "Chicken" : re = ChatColor.WHITE+"��"; break;
			case "Squid" : re = ChatColor.GRAY+"��¡��"; break;
			case "Wolf" : re = ChatColor.WHITE+"����"; break;
			case "Mooshroom" : re = ChatColor.RED+"���� ��"; break;
			case "Ocelot" : re = ChatColor.YELLOW+"������"; break;
			case "Horse" : re = ChatColor.GOLD+"��"; break;
			case "Rabbit" : re = ChatColor.WHITE+"�䳢"; break;
			case "Villager" : re = ChatColor.GOLD+"�ֹ�"; break;
			case "Snow Golem" : re = ChatColor.WHITE+"�� ���"; break;
			case "Iron Golem" : re = ChatColor.WHITE+"ö ��"; break;
			case "Wither" : re = ChatColor.GRAY+""+ChatColor.BOLD+"����"; break;
			case "unknown" : re = ChatColor.DARK_PURPLE+""+ChatColor.BOLD+"���� �巡��"; break;
			default :
				re = s; break;
		}
		return re;
	}
}
