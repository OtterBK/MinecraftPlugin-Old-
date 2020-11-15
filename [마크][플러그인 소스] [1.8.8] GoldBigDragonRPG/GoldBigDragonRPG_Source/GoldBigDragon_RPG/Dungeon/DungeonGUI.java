package GoldBigDragon_RPG.Dungeon;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.event.inventory.InventoryType;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.material.MaterialData;

import GoldBigDragon_RPG.GUI.GUIutil;
import GoldBigDragon_RPG.GUI.OPBoxGUI;
import GoldBigDragon_RPG.Main.Object_UserData;
import GoldBigDragon_RPG.ServerTick.ServerTickMain;
import GoldBigDragon_RPG.Util.YamlController;
import GoldBigDragon_RPG.Util.YamlManager;

public final class DungeonGUI extends GUIutil
{
	/*
	���� ����.
	������ ����.
	���� ����.
	�� ������ ���� ������ ����.
	���ܸ��� ������ ����.
	�Ϲ� �������� ���� ����� ���� ����.
	
	��� ���� ���� ���� ����.
	���� ���� ����.
	���� ���� ����.
	
	��� ����/����/�������� ��� ���� �ٸ�.
	 */
	
	//GUI Router//
	public void InventoryClickRouter(InventoryClickEvent event, String InventoryName)
	{
		String Striped = ChatColor.stripColor(event.getInventory().getName().toString());
		
		//�ݾƼ� �����ϴ� ���� ���⿡ �ֱ�
		if(event.getInventory().getType()==InventoryType.CHEST)
		{
			if(!(Striped.contains("����")||
			ChatColor.stripColor(InventoryName).contains("��ġ��")
			))
				event.setCancelled(true);
		}
		InventoryName = InventoryName.split(":")[0];

		if(ChatColor.stripColor(InventoryName).contains("��ġ��"))//�Ϲ� ���� ��� �ֱ�
			AltarUseGUIClick(event);
		else if(InventoryName.contains("��������"))
			DungeonEXITClick(event);
		else if(InventoryName.contains("��ϵ�"))
			AltarEnterCardSettingGUIClick(event);
		else if(InventoryName.contains("������"))
			AltarEnterCardListGUIClick(event);
		else if(InventoryName.contains("����"))
		{
			if(InventoryName.contains("���"))
				DungeonListMainGUIClick(event);
			else if(InventoryName.contains("����"))
				DungeonSetUpGUIClick(event);
			else if(InventoryName.contains("����"))
				DungeonChestRewardClick(event);
			else if(InventoryName.contains("����"))
			{
				if(event.getInventory().getSize()==9)
					DungeonMonsterChooseMainClick(event);
				else if(event.getInventory().getSize()==54)
						DungeonMonsterGUIMainClick(event);
			}
			else if(InventoryName.contains("�����"))
				DungeonMusicSettingGUIClick(event);
		}
		else if(InventoryName.contains("����"))
		{
			if(InventoryName.contains("���"))
				DungeonListMainGUIClick(event);
			else if(InventoryName.contains("����"))
				AltarShapeListGUIClick(event);
			else if(InventoryName.contains("����"))
				AltarSettingGUIClick(event);
			else if(InventoryName.contains("����"))
				AltarDungeonSettingGUIClick(event);
			return;
		}
		else if(InventoryName.contains("������"))
		{
			if(InventoryName.contains("���"))
				DungeonListMainGUIClick(event);	
			else if(InventoryName.contains("����"))
				EnterCardSetUpGUIClick(event);
			else if(InventoryName.contains("����"))
				EnterCardDungeonSettingGUIClick(event);
		}
		else if(InventoryName.contains("����"))
		{
			if(InventoryName.contains("�Ϲ�"))
				DungeonSelectNormalMonsterChooseClick(event);
			else if(InventoryName.contains("Ŀ����"))
				DungeonSelectCustomMonsterChooseClick(event);
		}
		return;
	}
	
	public void InventoryCloseRouter(InventoryCloseEvent event, String InventoryName)
	{
		if(InventoryName.contains("��ġ��"))
			AltarUSEGuiClose(event);
		else if(InventoryName.contains("����"))
		{
			if(InventoryName.contains("����"))
				DungeonChestRewardClose(event);
		}
	}
	//GUI Router//
	
	
	
	//DungeonGUI//
	public void DungeonListMainGUI(Player player, int page, int Type)
	{
	  	YamlController YC = new YamlController(GoldBigDragon_RPG.Main.Main.plugin);
		YamlManager DungeonConfig = YC.getNewConfig("Dungeon/DungeonList.yml");

		Inventory inv = null;
		if(Type==52)
			inv = Bukkit.createInventory(null, 54, ChatColor.DARK_GREEN +""+ChatColor.BOLD +""+ChatColor.BLACK+ "���� ��� : " + (page+1));
		else if(Type==358)
			inv = Bukkit.createInventory(null, 54, ChatColor.DARK_GREEN +""+ChatColor.BOLD +""+ChatColor.BLACK+ "������ ��� : " + (page+1));
		else if(Type==120)
			inv = Bukkit.createInventory(null, 54, ChatColor.DARK_GREEN +""+ChatColor.BOLD +""+ChatColor.BLACK+ "���� ��� : " + (page+1));
		Object[] DungeonList = null;
		if(Type==52)//����
		{
			DungeonList = DungeonConfig.getConfigurationSection("").getKeys(false).toArray();
			
			int loc=0;
			for(int count = page*45; count < DungeonList.length;count++)
			{
				YamlManager DungeonOptionYML = YC.getNewConfig("Dungeon/Dungeon/"+DungeonList[count].toString()+"/Option.yml");
				
				Stack2(ChatColor.WHITE + "" + ChatColor.BOLD + DungeonList[count].toString(), 52,0,1,Arrays.asList(
				"",ChatColor.BLUE+"���� ���� : "+ChatColor.WHITE+DungeonOptionYML.getString("Type.Name")
				,ChatColor.BLUE+"���� ũ�� : "+ChatColor.WHITE+DungeonOptionYML.getInt("Size")
				,ChatColor.BLUE+"���� ������ : "+ChatColor.WHITE+DungeonOptionYML.getInt("Maze_Level")
				,ChatColor.BLUE+"���� ���� : "+ChatColor.WHITE+DungeonOptionYML.getInt("District.Level")+" �̻�"
				,ChatColor.BLUE+"���� ���� ���� : "+ChatColor.WHITE+DungeonOptionYML.getInt("District.RealLevel")+" �̻�"
				,"",ChatColor.BLUE+"[�⺻ Ŭ���� ����]"
				,ChatColor.BLUE+" - "+ChatColor.WHITE+DungeonOptionYML.getInt("Reward.Money")+" "+GoldBigDragon_RPG.Main.ServerOption.Money
				,ChatColor.BLUE+" - "+ChatColor.WHITE+DungeonOptionYML.getInt("Reward.EXP")+ChatColor.AQUA+" "+ChatColor.BOLD+"EXP"
				,"",ChatColor.YELLOW+"[�� Ŭ���� ���� ����]",ChatColor.RED+"[Shift + ��Ŭ���� ���� ����]"), loc, inv);
				
				loc=loc+1;
			}
		}
		else if(Type==358)//������
		{
			DungeonConfig = YC.getNewConfig("Dungeon/EnterCardList.yml");
			DungeonList = DungeonConfig.getConfigurationSection("").getKeys(false).toArray();
			int loc=0;
			for(int count = page*45; count < DungeonList.length;count++)
			{
				String Time = null;
				if(DungeonConfig.getInt(DungeonList[count].toString()+".Hour")!=-1)
				{
					if(DungeonConfig.getInt(DungeonList[count].toString()+".Hour")!=0)
						Time = DungeonConfig.getInt(DungeonList[count].toString()+".Hour")+"�ð� ";
					if(DungeonConfig.getInt(DungeonList[count].toString()+".Min")!=0)
						Time = Time+DungeonConfig.getInt(DungeonList[count].toString()+".Min")+"�� ";
					Time = Time+DungeonConfig.getInt(DungeonList[count].toString()+".Sec")+"��";
				}
				else
					Time = "������";
				if(DungeonConfig.getString(DungeonList[count].toString()+".Dungeon")==null)
					Stack2(ChatColor.WHITE + "" + ChatColor.BOLD + DungeonList[count].toString(), DungeonConfig.getInt(DungeonList[count].toString()+".ID"),DungeonConfig.getInt(DungeonList[count].toString()+".DATA"),1,Arrays.asList(
					"",ChatColor.BLUE+"����� ���� : "+ChatColor.WHITE+"����",
					ChatColor.BLUE+"���� ���� �ο� : "+ChatColor.WHITE+""+DungeonConfig.getInt(DungeonList[count].toString()+".Capacity"),
					ChatColor.BLUE+"��ȿ �ð� : "+ChatColor.WHITE+""+Time,
					"",ChatColor.YELLOW+"[�� Ŭ���� ������ ����]",ChatColor.YELLOW+"[Shift + �� Ŭ���� ������ �߱�]",ChatColor.RED+"[Shift + ��Ŭ���� ������ ����]"), loc, inv);
				else
				{
					YamlManager Dungeon = YC.getNewConfig("Dungeon/DungeonList.yml");
					if(Dungeon.contains(DungeonConfig.getString(DungeonList[count].toString()+".Dungeon")))
					{
						Stack2(ChatColor.WHITE + "" + ChatColor.BOLD + DungeonList[count].toString(), DungeonConfig.getInt(DungeonList[count].toString()+".ID"),DungeonConfig.getInt(DungeonList[count].toString()+".DATA"),1,Arrays.asList(
						"",ChatColor.BLUE+"����� ���� : "+ChatColor.WHITE+""+DungeonConfig.getString(DungeonList[count].toString()+".Dungeon"),
						ChatColor.BLUE+"���� ���� �ο� : "+ChatColor.WHITE+""+DungeonConfig.getInt(DungeonList[count].toString()+".Capacity"),
						ChatColor.BLUE+"��ȿ �ð� : "+ChatColor.WHITE+""+Time,
						"",ChatColor.YELLOW+"[�� Ŭ���� ������ ����]",ChatColor.YELLOW+"[Shift + �� Ŭ���� ������ �߱�]",ChatColor.RED+"[Shift + ��Ŭ���� ������ ����]"), loc, inv);
					}
					else
					{
						DungeonConfig.set(DungeonList[count].toString()+".Dungeon",null);
						DungeonConfig.saveConfig();
						Stack2(ChatColor.WHITE + "" + ChatColor.BOLD + DungeonList[count].toString(), DungeonConfig.getInt(DungeonList[count].toString()+".ID"),DungeonConfig.getInt(DungeonList[count].toString()+".DATA"),1,Arrays.asList(
						"",ChatColor.BLUE+"����� ���� : "+ChatColor.WHITE+"����",
						ChatColor.BLUE+"���� ���� �ο� : "+ChatColor.WHITE+""+DungeonConfig.getInt(DungeonList[count].toString()+".Capacity"),
						ChatColor.BLUE+"��ȿ �ð� : "+ChatColor.WHITE+""+Time,
						"",ChatColor.YELLOW+"[�� Ŭ���� ������ ����]",ChatColor.YELLOW+"[Shift + �� Ŭ���� ������ �߱�]",ChatColor.RED+"[Shift + ��Ŭ���� ������ ����]"), loc, inv);
					}
						
				}
				
				loc=loc+1;
			}
		}
		else if(Type==120)//����
		{
			DungeonConfig = YC.getNewConfig("Dungeon/AltarList.yml");
			DungeonList = DungeonConfig.getKeys().toArray();
			int loc=0;
			for(int count = page*45; count < DungeonList.length;count++)
			{
				String AltarCode = DungeonList[count].toString();
				Stack2(ChatColor.WHITE+DungeonConfig.getString(AltarCode+".Name"), DungeonConfig.getInt(AltarCode+".ID"),DungeonConfig.getInt(AltarCode+".DATA"),1,Arrays.asList(
				"",ChatColor.BLUE+"[���� ��ġ]",
				ChatColor.WHITE+" "+DungeonConfig.getString(AltarCode+".World"),
				ChatColor.WHITE+" "+DungeonConfig.getInt(AltarCode+".X")+","+DungeonConfig.getInt(AltarCode+".Y")+","+DungeonConfig.getInt(AltarCode+".Z"),
				"",ChatColor.YELLOW+"[�� Ŭ���� ���� ����]",ChatColor.YELLOW+"[Shift + �� Ŭ���� ���� �̵�]",ChatColor.RED+"[Shift + ��Ŭ���� ���� ö��]","",AltarCode), loc, inv);
				
				loc=loc+1;
			}
		}
		if(Type==52)
			Stack2(ChatColor.BLUE + "" + ChatColor.BOLD + "[���� �׸�]", 52,0,1,Arrays.asList(ChatColor.BLUE + "���� �׸� : "+ChatColor.WHITE+"����","",ChatColor.YELLOW+"������ �����ϱ� ���ؼ���",ChatColor.YELLOW+"�Ʒ��� 3���� �������� ���� �ؾ� �մϴ�.",ChatColor.YELLOW+"[����, ������, ����]"), 47, inv);
		else if(Type==358)
			Stack2(ChatColor.BLUE + "" + ChatColor.BOLD + "[���� �׸�]", 358,0,1,Arrays.asList(ChatColor.BLUE + "���� �׸� : "+ChatColor.WHITE+"������","",ChatColor.YELLOW+"������ �����ϱ� ���ؼ���",ChatColor.YELLOW+"�Ʒ��� 3���� �������� ���� �ؾ� �մϴ�.",ChatColor.YELLOW+"[����, ������, ����]"), 47, inv);
		else if(Type==120)
			Stack2(ChatColor.BLUE + "" + ChatColor.BOLD + "[���� �׸�]", 120,0,1,Arrays.asList(ChatColor.BLUE + "���� �׸� : "+ChatColor.WHITE+"����","",ChatColor.YELLOW+"������ �����ϱ� ���ؼ���",ChatColor.YELLOW+"�Ʒ��� 3���� �������� ���� �ؾ� �մϴ�.",ChatColor.YELLOW+"[����, ������, ����]"), 47, inv);
		
		
		if(DungeonList.length-(page*44)>45)
		Stack2(ChatColor.WHITE + "" + ChatColor.BOLD + "���� ������", 323,0,1,Arrays.asList(ChatColor.GRAY + "���� �������� �̵� �մϴ�."), 50, inv);
		if(page!=0)
		Stack2(ChatColor.WHITE + "" + ChatColor.BOLD + "���� ������", 323,0,1,Arrays.asList(ChatColor.GRAY + "���� �������� �̵� �մϴ�."), 48, inv);

		if(Type==52)
			Stack2(ChatColor.WHITE + "" + ChatColor.BOLD + "���� ����", 383,0,1,Arrays.asList(ChatColor.GRAY + "���ο� ������ �����մϴ�."), 49, inv);
		if(Type==358)
			Stack2(ChatColor.WHITE + "" + ChatColor.BOLD + "������ ����", 386,0,1,Arrays.asList(ChatColor.GRAY + "���ο� �������� �����մϴ�."), 49, inv);
		if(Type==120)
			Stack2(ChatColor.WHITE + "" + ChatColor.BOLD + "���� �Ǽ�", 381,0,1,Arrays.asList(ChatColor.GRAY + "���ο� ������ �����մϴ�.","",ChatColor.RED+""+ChatColor.BOLD+"[������ ������ ������ �ٶ󺾴ϴ�.]"), 49, inv);
		Stack2(ChatColor.WHITE + "" + ChatColor.BOLD + "���� ���", 323,0,1,Arrays.asList(ChatColor.GRAY + "���� ȭ������ ���ư��ϴ�."), 45, inv);
		Stack2(ChatColor.WHITE + "" + ChatColor.BOLD + "�ݱ�", 324,0,1,Arrays.asList(ChatColor.GRAY + "â�� �ݽ��ϴ�."), 53, inv);
		player.openInventory(inv);
		return;
	}
	
	public void DungeonSetUpGUI(Player player, String DungeonName)
	{
	  	YamlController YC = new YamlController(GoldBigDragon_RPG.Main.Main.plugin);
		YamlManager DungeonConfig = YC.getNewConfig("Dungeon/Dungeon/"+DungeonName+"/Option.yml");
		
		Inventory inv = Bukkit.createInventory(null, 45, ChatColor.DARK_GREEN +""+ChatColor.BOLD +""+ChatColor.BLACK+ "���� ���� : " +DungeonName);
		Stack2(ChatColor.WHITE + "" + ChatColor.BOLD + "���� Ÿ��", DungeonConfig.getInt("Type.ID"),DungeonConfig.getInt("Type.DATA"),1,Arrays.asList(ChatColor.GRAY + "���� ���� Ÿ�� : "+DungeonConfig.getString("Type.Name")), 11, inv);
		
		Stack2(ChatColor.WHITE + "" + ChatColor.BOLD + "���� ũ��", 395,0,1,Arrays.asList(ChatColor.GRAY + "���� ���� ũ�� : "+DungeonConfig.getInt("Size"),ChatColor.DARK_GRAY + "�ּ� : 5",ChatColor.DARK_GRAY + "�ִ� : 30"), 13, inv);
		Stack2(ChatColor.WHITE + "" + ChatColor.BOLD + "���� ����", 53,0,1,Arrays.asList(ChatColor.GRAY + "���� �̷� ���� : "+DungeonConfig.getInt("Maze_Level"),"",ChatColor.YELLOW+"[���� �޴� �׸�]",ChatColor.YELLOW+" - ������ ���� ��",ChatColor.YELLOW+" - ���� ������ ����",ChatColor.YELLOW+" - ���� ������"), 15, inv);
		
		Stack2(ChatColor.WHITE + "" + ChatColor.BOLD + "���� ����", 101,0,1,Arrays.asList(ChatColor.GRAY + "���� ���� ������ �����մϴ�.",ChatColor.RED+"���� ���� : " + ChatColor.GRAY+DungeonConfig.getInt("District.Level"),ChatColor.RED+"���� ���� ���� : " + ChatColor.GRAY+ DungeonConfig.getInt("District.RealLevel")), 20, inv);
		Stack2(ChatColor.WHITE + "" + ChatColor.BOLD + "���� ����", 266,0,1,Arrays.asList(ChatColor.GRAY + "���� �⺻ ������ �����մϴ�.",ChatColor.YELLOW+"���� �ݾ� : " + ChatColor.GRAY+DungeonConfig.getInt("Reward.Money"),ChatColor.AQUA+"���� ����ġ : " + ChatColor.GRAY+DungeonConfig.getInt("Reward.EXP")), 22, inv);
		Stack2(ChatColor.WHITE + "" + ChatColor.BOLD + "���� ���� ����", 54,0,1,Arrays.asList(ChatColor.GRAY + "���� �߰� ������ �����մϴ�."), 24, inv);
		Stack2(ChatColor.WHITE + "" + ChatColor.BOLD + "���� ����", 383,0,1,Arrays.asList(ChatColor.GRAY + "���� ���͸� �����մϴ�."), 29, inv);
		if(Bukkit.getPluginManager().isPluginEnabled("NoteBlockAPI") == true)
		{
			String lore = "";
			int tracknumber = DungeonConfig.getInt("BGM.Normal");
			lore = " %enter%"+ChatColor.GRAY + "���� BGM�� �����մϴ�.%enter% %enter%"+ChatColor.BLUE + "[Ŭ���� ��Ʈ��� ���� ����]%enter% %enter%"+ChatColor.DARK_AQUA+"[Ʈ��] "+ChatColor.BLUE +""+ tracknumber+"%enter%"
			+ChatColor.DARK_AQUA+"[����] "+ChatColor.BLUE +""+ new OtherPlugins.NoteBlockAPIMain().getTitle(tracknumber)+"%enter%"
			+ChatColor.DARK_AQUA+"[����] "+ChatColor.BLUE+new OtherPlugins.NoteBlockAPIMain().getAuthor(tracknumber)+"%enter%"+ChatColor.DARK_AQUA+"[����] ";
			
			String Description = new OtherPlugins.NoteBlockAPIMain().getDescription(tracknumber);
			String lore2="";
			int a = 0;
			for(int count = 0; count <Description.toCharArray().length; count++)
			{
				lore2 = lore2+ChatColor.BLUE+Description.toCharArray()[count];
				a=a+1;
				if(a >= 15)
				{
					a = 0;
					lore2 = lore2+"%enter%      ";
				}
			}
			lore = lore + lore2;
			Stack2(ChatColor.WHITE + ""+ChatColor.BOLD+"[���� �����]", 2263,0,1,Arrays.asList(lore.split("%enter%")), 31, inv);

			lore = "";
			tracknumber = DungeonConfig.getInt("BGM.BOSS");
			lore = " %enter%"+ChatColor.GRAY + "���� BGM�� �����մϴ�.%enter% %enter%"+ChatColor.BLUE + "[Ŭ���� ��Ʈ��� ���� ����]%enter% %enter%"+ChatColor.DARK_AQUA+"[Ʈ��] "+ChatColor.BLUE +""+ tracknumber+"%enter%"
			+ChatColor.DARK_AQUA+"[����] "+ChatColor.BLUE +""+ new OtherPlugins.NoteBlockAPIMain().getTitle(tracknumber)+"%enter%"
			+ChatColor.DARK_AQUA+"[����] "+ChatColor.BLUE+new OtherPlugins.NoteBlockAPIMain().getAuthor(tracknumber)+"%enter%"+ChatColor.DARK_AQUA+"[����] ";
			
			Description = new OtherPlugins.NoteBlockAPIMain().getDescription(tracknumber);
			lore2="";
			a = 0;
			for(int count = 0; count <Description.toCharArray().length; count++)
			{
				lore2 = lore2+ChatColor.BLUE+Description.toCharArray()[count];
				a=a+1;
				if(a >= 15)
				{
					a = 0;
					lore2 = lore2+"%enter%      ";
				}
			}
			lore = lore + lore2;
			Stack2(ChatColor.WHITE + ""+ChatColor.BOLD+"[���� �����]", 2259,0,1,Arrays.asList(lore.split("%enter%")), 33, inv);
		}
		else
		{
			Stack2(ChatColor.RED + ""+ChatColor.BOLD+"[���� �����]", 2266,0,1,Arrays.asList("",ChatColor.GRAY + "���� ����� �׸� ����",ChatColor.GRAY+"��� ��ų �� �ֽ��ϴ�.","",ChatColor.RED + "[     �ʿ� �÷�����     ]",ChatColor.RED+" - NoteBlockAPI"), 33, inv);
			Stack2(ChatColor.RED + ""+ChatColor.BOLD+"[���� �����]", 2266,0,1,Arrays.asList("",ChatColor.GRAY + "������ ����� �׸� ����",ChatColor.GRAY+"��� ��ų �� �ֽ��ϴ�.","",ChatColor.RED + "[     �ʿ� �÷�����     ]",ChatColor.RED+" - NoteBlockAPI"), 35, inv);
		}
		Stack2(ChatColor.WHITE + "" + ChatColor.BOLD + "�ݱ�", 324,0,1,Arrays.asList(ChatColor.GRAY + "â�� �ݽ��ϴ�."), 44, inv);
		Stack2(ChatColor.WHITE + "" + ChatColor.BOLD + "���� ���", 323,0,1,Arrays.asList(ChatColor.GRAY + "���� ȭ������ ���ư��ϴ�."), 36, inv);

		player.openInventory(inv);
	}

	public void DungeonChestReward(Player player, String DungeonName)
	{
	  	YamlController YC = new YamlController(GoldBigDragon_RPG.Main.Main.plugin);
		YamlManager DungeonConfig = YC.getNewConfig("Dungeon/Dungeon/"+DungeonName+"/Reward.yml");
		
		Inventory inv = Bukkit.createInventory(null, 54, ChatColor.DARK_GREEN +""+ChatColor.BOLD +""+ "���� ���� : " +DungeonName);
	
		Stack2(ChatColor.BLUE + "" + ChatColor.BOLD + "[100% Ȯ��]", 160,11,1,Arrays.asList("",ChatColor.WHITE+"100% Ȯ���� ���� ��������",ChatColor.WHITE+"�� �ٿ� �����ø� �˴ϴ�.",ChatColor.WHITE+"100% Ȯ���� �� �ٿ� �ִ�",ChatColor.WHITE+"������ ��, �ϳ��� ���ɴϴ�.",""), 0, inv);
		Stack2(ChatColor.GREEN + "" + ChatColor.BOLD + "[90% Ȯ��]", 160,5,1,Arrays.asList("",ChatColor.WHITE+"90% Ȯ���� ���� ��������",ChatColor.WHITE+"�� �ٿ� �����ø� �˴ϴ�.",ChatColor.WHITE+"90% Ȯ���� �� �ٿ� �ִ�",ChatColor.WHITE+"������ ��, �ϳ��� ���ɴϴ�.",""), 9, inv);
		Stack2(ChatColor.YELLOW + "" + ChatColor.BOLD + "[50% Ȯ��]", 160,4,1,Arrays.asList("",ChatColor.WHITE+"50% Ȯ���� ���� ��������",ChatColor.WHITE+"�� �ٿ� �����ø� �˴ϴ�.",ChatColor.WHITE+"50% Ȯ���� �� �ٿ� �ִ�",ChatColor.WHITE+"������ ��, �ϳ��� ���ɴϴ�.",""), 18, inv);
		Stack2(ChatColor.GOLD + "" + ChatColor.BOLD + "[10% Ȯ��]", 160,1,1,Arrays.asList("",ChatColor.WHITE+"10% Ȯ���� ���� ��������",ChatColor.WHITE+"�� �ٿ� �����ø� �˴ϴ�.",ChatColor.WHITE+"10% Ȯ���� �� �ٿ� �ִ�",ChatColor.WHITE+"������ ��, �ϳ��� ���ɴϴ�.",""), 27, inv);
		Stack2(ChatColor.DARK_RED + "" + ChatColor.BOLD + "[1% Ȯ��]", 160,14,1,Arrays.asList("",ChatColor.WHITE+"1% Ȯ���� ���� ��������",ChatColor.WHITE+"�� �ٿ� �����ø� �˴ϴ�.",ChatColor.WHITE+"1% Ȯ���� �� �ٿ� �ִ�",ChatColor.WHITE+"������ ��, �ϳ��� ���ɴϴ�.",""), 36, inv);
		Stack2(ChatColor.DARK_PURPLE + "" + ChatColor.BOLD + "[0.1% Ȯ��]", 160,10,1,Arrays.asList("",ChatColor.WHITE+"0.1% Ȯ���� ���� ��������",ChatColor.WHITE+"�� �ٿ� �����ø� �˴ϴ�.",ChatColor.WHITE+"0.1% Ȯ���� �� �ٿ� �ִ�",ChatColor.WHITE+"������ ��, �ϳ��� ���ɴϴ�.",""), 45, inv);

		for(int count = 0; count < 8; count++)
		{
			if(DungeonConfig.getItemStack("100."+count)!=null)
				ItemStackStack(DungeonConfig.getItemStack("100."+count), count+1, inv);
			if(DungeonConfig.getItemStack("90."+count)!=null)
				ItemStackStack(DungeonConfig.getItemStack("90."+count), count+10, inv);
			if(DungeonConfig.getItemStack("50."+count)!=null)
				ItemStackStack(DungeonConfig.getItemStack("50."+count), count+19, inv);
			if(DungeonConfig.getItemStack("10."+count)!=null)
				ItemStackStack(DungeonConfig.getItemStack("10."+count), count+28, inv);
			if(DungeonConfig.getItemStack("1."+count)!=null)
				ItemStackStack(DungeonConfig.getItemStack("1."+count), count+37, inv);
			if(DungeonConfig.getItemStack("0."+count)!=null)
				ItemStackStack(DungeonConfig.getItemStack("0."+count), count+46, inv);
		}
		player.openInventory(inv);
	}
	
	public void DungeonMonsterGUIMain(Player player, String DungeonName)
	{
	  	YamlController YC = new YamlController(GoldBigDragon_RPG.Main.Main.plugin);
		YamlManager DungeonConfig = YC.getNewConfig("Dungeon/Dungeon/"+DungeonName+"/Monster.yml");
		
		Inventory inv = Bukkit.createInventory(null, 54, ChatColor.DARK_GREEN +""+ChatColor.BOLD +""+ "���� ���� : " +DungeonName);

		Stack2(ChatColor.DARK_RED + "" + ChatColor.BOLD + "[BOSS]", 160,14,1,Arrays.asList("",ChatColor.WHITE+"�����濡�� ���� ���ʹ�",ChatColor.WHITE+"�� �ٿ��� �����մϴ�.",""), 0, inv);
		Stack2(ChatColor.GOLD + "" + ChatColor.BOLD + "[�� ����]", 160,5,1,Arrays.asList("",ChatColor.WHITE+"������ �տ��� ���� ���ʹ�",ChatColor.WHITE+"�� �ٿ��� �����մϴ�.",""), 9, inv);
		Stack2(ChatColor.YELLOW + "" + ChatColor.BOLD + "[��� ����]", 160,4,1,Arrays.asList("",ChatColor.WHITE+"�Ϲ� �濡�� ���� �ſ� ���� ���ʹ�",ChatColor.WHITE+"�� �ٿ��� �����մϴ�.",""), 18, inv);
		Stack2(ChatColor.GREEN + "" + ChatColor.BOLD + "[�߱� ����]", 160,5,1,Arrays.asList("",ChatColor.WHITE+"�Ϲ� �濡�� ���� ���� ���ʹ�",ChatColor.WHITE+"�� �ٿ��� �����մϴ�.",""), 27, inv);
		Stack2(ChatColor.BLUE + "" + ChatColor.BOLD + "[�ϱ� ����]", 160,11,1,Arrays.asList("",ChatColor.WHITE+"�Ϲ� �濡�� ���� �Ϲ� ���ʹ�",ChatColor.WHITE+"�� �ٿ��� �����մϴ�.",""), 36, inv);

		Stack2(ChatColor.WHITE + "" + ChatColor.BOLD + "�ݱ�", 324,0,1,Arrays.asList(ChatColor.GRAY + "â�� �ݽ��ϴ�."), 53, inv);
		Stack2(ChatColor.WHITE + "" + ChatColor.BOLD + "���� ���", 323,0,1,Arrays.asList(ChatColor.GRAY + "���� ȭ������ ���ư��ϴ�."), 45, inv);

		String Type = "Boss";
		for(int count2 = 0; count2 < 5; count2 ++)
		{
			switch(count2)
			{
			case 0:
				Type = "Boss";	break;
			case 1:
				Type = "SubBoss";	break;
			case 2:
				Type = "High";	break;
			case 3:
				Type = "Middle";	break;
			case 4:
				Type = "Normal";	break;
			}
			
			for(int count = 0; count < 8; count ++)
			{
				if(DungeonConfig.getString(Type+"."+count)==null)
					Stack2(ChatColor.WHITE + "" + ChatColor.BOLD + "[����]", 383, 0, 1,Arrays.asList("",ChatColor.YELLOW + "[�� Ŭ���� ���]"), count+1+(count2*9), inv);
				else
				{
					switch(DungeonConfig.getString(Type+"."+count))
					{
					case "������":
						Stack2(ChatColor.DARK_GREEN + "" + ChatColor.BOLD + "[�Ϲ� ����]", 383, 54, 1,Arrays.asList(ChatColor.GRAY+"Ŀ���� ���Ͱ� �ƴ�",ChatColor.GRAY+"�Ϲ� ���� �����Դϴ�.","",ChatColor.YELLOW + "[�� Ŭ���� ����]"), count+1+(count2*9), inv);break;
					case "�ｺ�̷���":
						Stack2(ChatColor.GRAY + "" + ChatColor.BOLD + "[�Ϲ� ���̷���]", 383, 51, 1,Arrays.asList(ChatColor.GRAY+"Ŀ���� ���Ͱ� �ƴ�",ChatColor.GRAY+"�Ϲ� ���� �����Դϴ�.","",ChatColor.YELLOW + "[�� Ŭ���� ����]"), count+1+(count2*9), inv);break;
					case "��ũ����":
						Stack2(ChatColor.GREEN + "" + ChatColor.BOLD + "[�Ϲ� ũ����]", 383, 50, 1,Arrays.asList(ChatColor.GRAY+"Ŀ���� ���Ͱ� �ƴ�",ChatColor.GRAY+"�Ϲ� ���� �����Դϴ�.","",ChatColor.YELLOW + "[�� Ŭ���� ����]"), count+1+(count2*9), inv);break;
					case "��Ź�":
						Stack2(ChatColor.GRAY + "" + ChatColor.BOLD + "[�Ϲ� �Ź�]", 383, 52, 1,Arrays.asList(ChatColor.GRAY+"Ŀ���� ���Ͱ� �ƴ�",ChatColor.GRAY+"�Ϲ� ���� �����Դϴ�.","",ChatColor.YELLOW + "[�� Ŭ���� ����]"), count+1+(count2*9), inv);break;
					case "�ﵿ���Ź�":
						Stack2(ChatColor.DARK_GRAY + "" + ChatColor.BOLD + "[�Ϲ� ���� �Ź�]", 383, 59, 1,Arrays.asList(ChatColor.GRAY+"Ŀ���� ���Ͱ� �ƴ�",ChatColor.GRAY+"�Ϲ� ���� �����Դϴ�.","",ChatColor.YELLOW + "[�� Ŭ���� ����]"), count+1+(count2*9), inv);break;
					case "�￣����":
						Stack2(ChatColor.DARK_PURPLE + "" + ChatColor.BOLD + "[�Ϲ� ������]", 383, 58, 1,Arrays.asList(ChatColor.GRAY+"Ŀ���� ���Ͱ� �ƴ�",ChatColor.GRAY+"�Ϲ� ���� �����Դϴ�.","",ChatColor.YELLOW + "[�� Ŭ���� ����]"), count+1+(count2*9), inv);break;
					case "�ｽ����":
						Stack2(ChatColor.GREEN + "" + ChatColor.BOLD + "[�Ϲ� ������]", 383, 55, 1,Arrays.asList(ChatColor.GRAY+"Ŀ���� ���Ͱ� �ƴ�",ChatColor.GRAY+"�Ϲ� ���� �����Դϴ�.","",ChatColor.YELLOW + "[�� Ŭ���� ����]"), count+1+(count2*9), inv);break;
					case "�︶�׸�ť��":
						Stack2(ChatColor.YELLOW + "" + ChatColor.BOLD + "[�Ϲ� ���׸� ť��]", 383, 62, 1,Arrays.asList(ChatColor.GRAY+"Ŀ���� ���Ͱ� �ƴ�",ChatColor.GRAY+"�Ϲ� ���� �����Դϴ�.","",ChatColor.YELLOW + "[�� Ŭ���� ����]"), count+1+(count2*9), inv);break;
					case "�︶��":
						Stack2(ChatColor.DARK_PURPLE + "" + ChatColor.BOLD + "[�Ϲ� ����]", 383, 66, 1,Arrays.asList(ChatColor.GRAY+"Ŀ���� ���Ͱ� �ƴ�",ChatColor.GRAY+"�Ϲ� ���� �����Դϴ�.","",ChatColor.YELLOW + "[�� Ŭ���� ����]"), count+1+(count2*9), inv);break;
					case "�������Ǳ׸�":
						Stack2(ChatColor.LIGHT_PURPLE + "" + ChatColor.BOLD + "[�Ϲ� ���� �Ǳ׸�]", 383, 57, 1,Arrays.asList(ChatColor.GRAY+"Ŀ���� ���Ͱ� �ƴ�",ChatColor.GRAY+"�Ϲ� ���� �����Դϴ�.","",ChatColor.YELLOW + "[�� Ŭ���� ����]"), count+1+(count2*9), inv);break;
					case "�������":
						Stack2(ChatColor.YELLOW + "" + ChatColor.BOLD + "[�Ϲ� ������]", 383, 61, 1,Arrays.asList(ChatColor.GRAY+"Ŀ���� ���Ͱ� �ƴ�",ChatColor.GRAY+"�Ϲ� ���� �����Դϴ�.","",ChatColor.YELLOW + "[�� Ŭ���� ����]"), count+1+(count2*9), inv);break;
					case "�ﰡ��Ʈ":
						Stack2(ChatColor.WHITE + "" + ChatColor.BOLD + "[�Ϲ� ����Ʈ]", 383, 56, 1,Arrays.asList(ChatColor.GRAY+"Ŀ���� ���Ͱ� �ƴ�",ChatColor.GRAY+"�Ϲ� ���� �����Դϴ�.","",ChatColor.YELLOW + "[�� Ŭ���� ����]"), count+1+(count2*9), inv);break;	
					case "�ﰡ���":
						Stack2(ChatColor.DARK_AQUA + "" + ChatColor.BOLD + "[�Ϲ� ��ȣ��]", 383, 68, 1,Arrays.asList(ChatColor.GRAY+"Ŀ���� ���Ͱ� �ƴ�",ChatColor.GRAY+"�Ϲ� ���� �����Դϴ�.","",ChatColor.YELLOW + "[�� Ŭ���� ����]"), count+1+(count2*9), inv);break;	
					case "�����":
						Stack2(ChatColor.DARK_GRAY + "" + ChatColor.BOLD + "[�Ϲ� ����]", 383, 65, 1,Arrays.asList(ChatColor.GRAY+"Ŀ���� ���Ͱ� �ƴ�",ChatColor.GRAY+"�Ϲ� ���� �����Դϴ�.","",ChatColor.YELLOW + "[�� Ŭ���� ����]"), count+1+(count2*9), inv);break;	
					case "�����":
						Stack2(ChatColor.LIGHT_PURPLE + "" + ChatColor.BOLD + "[�Ϲ� ����]", 383, 90, 1,Arrays.asList(ChatColor.GRAY+"Ŀ���� ���Ͱ� �ƴ�",ChatColor.GRAY+"�Ϲ� ���� �����Դϴ�.","",ChatColor.YELLOW + "[�� Ŭ���� ����]"), count+1+(count2*9), inv);break;	
					case "���":
						Stack2(ChatColor.WHITE + "" + ChatColor.BOLD + "[�Ϲ� ��]", 383, 91, 1,Arrays.asList(ChatColor.GRAY+"Ŀ���� ���Ͱ� �ƴ�",ChatColor.GRAY+"�Ϲ� ���� �����Դϴ�.","",ChatColor.YELLOW + "[�� Ŭ���� ����]"), count+1+(count2*9), inv);break;	
					case "���":
						Stack2(ChatColor.GRAY + "" + ChatColor.BOLD + "[�Ϲ� ��]", 383, 92, 1,Arrays.asList(ChatColor.GRAY+"Ŀ���� ���Ͱ� �ƴ�",ChatColor.GRAY+"�Ϲ� ���� �����Դϴ�.","",ChatColor.YELLOW + "[�� Ŭ���� ����]"), count+1+(count2*9), inv);break;	
					case "���":
						Stack2(ChatColor.WHITE + "" + ChatColor.BOLD + "[�Ϲ� ��]", 383, 93, 1,Arrays.asList(ChatColor.GRAY+"Ŀ���� ���Ͱ� �ƴ�",ChatColor.GRAY+"�Ϲ� ���� �����Դϴ�.","",ChatColor.YELLOW + "[�� Ŭ���� ����]"), count+1+(count2*9), inv);break;	
					case "���¡��":
						Stack2(ChatColor.DARK_GRAY + "" + ChatColor.BOLD + "[�Ϲ� ��¡��]", 383, 94, 1,Arrays.asList(ChatColor.GRAY+"Ŀ���� ���Ͱ� �ƴ�",ChatColor.GRAY+"�Ϲ� ���� �����Դϴ�.","",ChatColor.YELLOW + "[�� Ŭ���� ����]"), count+1+(count2*9), inv);break;
					case "�����":
						Stack2(ChatColor.WHITE + "" + ChatColor.BOLD + "[�Ϲ� ����]", 383, 95, 1,Arrays.asList(ChatColor.GRAY+"Ŀ���� ���Ͱ� �ƴ�",ChatColor.GRAY+"�Ϲ� ���� �����Դϴ�.","",ChatColor.YELLOW + "[�� Ŭ���� ����]"), count+1+(count2*9), inv);break;
					case "�������":
						Stack2(ChatColor.RED + "" + ChatColor.BOLD + "[�Ϲ� ������]", 383, 96, 1,Arrays.asList(ChatColor.GRAY+"Ŀ���� ���Ͱ� �ƴ�",ChatColor.GRAY+"�Ϲ� ���� �����Դϴ�.","",ChatColor.YELLOW + "[�� Ŭ���� ����]"), count+1+(count2*9), inv);break;
					case "�������":
						Stack2(ChatColor.YELLOW + "" + ChatColor.BOLD + "[�Ϲ� ������]", 383, 98, 1,Arrays.asList(ChatColor.GRAY+"Ŀ���� ���Ͱ� �ƴ�",ChatColor.GRAY+"�Ϲ� ���� �����Դϴ�.","",ChatColor.YELLOW + "[�� Ŭ���� ����]"), count+1+(count2*9), inv);break;
					case "�︻":
						Stack2(ChatColor.GOLD + "" + ChatColor.BOLD + "[�Ϲ� ��]", 383, 100, 1,Arrays.asList(ChatColor.GRAY+"Ŀ���� ���Ͱ� �ƴ�",ChatColor.GRAY+"�Ϲ� ���� �����Դϴ�.","",ChatColor.YELLOW + "[�� Ŭ���� ����]"), count+1+(count2*9), inv);break;
					case "���䳢":
						Stack2(ChatColor.GOLD + "" + ChatColor.BOLD + "[�Ϲ� �䳢]", 383, 101, 1,Arrays.asList(ChatColor.GRAY+"Ŀ���� ���Ͱ� �ƴ�",ChatColor.GRAY+"�Ϲ� ���� �����Դϴ�.","",ChatColor.YELLOW + "[�� Ŭ���� ����]"), count+1+(count2*9), inv);break;
					case "���ֹ�":
						Stack2(ChatColor.GOLD + "" + ChatColor.BOLD + "[�Ϲ� �ֹ�]", 383, 120, 1,Arrays.asList(ChatColor.GRAY+"Ŀ���� ���Ͱ� �ƴ�",ChatColor.GRAY+"�Ϲ� ���� �����Դϴ�.","",ChatColor.YELLOW + "[�� Ŭ���� ����]"), count+1+(count2*9), inv);break;
					default:
						YamlManager MonsterList = YC.getNewConfig("Monster/MonsterList.yml");
						String MobName = DungeonConfig.getString(Type+"."+count);
						boolean isExit = false;
						for(int count3=0;count3<MonsterList.getKeys().size();count3++)
						{
							if(MonsterList.getKeys().toArray()[count3].toString().compareTo(MobName)==0)
							{
								String MonsterName = MobName;
								String Lore=null;
								GoldBigDragon_RPG.Attack.Damage d = new GoldBigDragon_RPG.Attack.Damage();
								Lore = "%enter%"+ChatColor.WHITE+""+ChatColor.BOLD+" �̸� : "+ChatColor.WHITE+MonsterList.getString(MonsterName+".Name")+"%enter%";
								Lore = Lore+ChatColor.WHITE+""+ChatColor.BOLD+" Ÿ�� : "+ChatColor.WHITE+MonsterList.getString(MonsterName+".Type")+"%enter%";
								Lore = Lore+ChatColor.WHITE+""+ChatColor.BOLD+" ���� ���̿� : "+ChatColor.WHITE+MonsterList.getString(MonsterName+".Biome")+"%enter%";
								Lore = Lore+ChatColor.RED+""+ChatColor.BOLD+" ����� : "+ChatColor.WHITE+MonsterList.getInt(MonsterName+".HP")+"%enter%";
								Lore = Lore+ChatColor.AQUA+""+ChatColor.BOLD+" ����ġ : "+ChatColor.WHITE+MonsterList.getInt(MonsterName+".EXP")+"%enter%";
								Lore = Lore+ChatColor.YELLOW+""+ChatColor.BOLD+" ��� �ݾ� : "+ChatColor.WHITE+MonsterList.getInt(MonsterName+".MIN_Money")+" ~ "+MonsterList.getInt(MonsterName+".MAX_Money")+"%enter%";
								Lore = Lore+ChatColor.RED+""+ChatColor.BOLD+" "+GoldBigDragon_RPG.Main.ServerOption.STR+" : "+ChatColor.WHITE+MonsterList.getInt(MonsterName+".STR")
								+ChatColor.GRAY+ " [���� : " + d.CombatMinDamageGet(null, 0, MonsterList.getInt(MonsterName+".STR")) + " ~ " + d.CombatMaxDamageGet(null, 0, MonsterList.getInt(MonsterName+".STR")) + "]%enter%";
								Lore = Lore+ChatColor.GREEN+""+ChatColor.BOLD+" "+GoldBigDragon_RPG.Main.ServerOption.DEX+" : "+ChatColor.WHITE+MonsterList.getInt(MonsterName+".DEX")
								+ChatColor.GRAY+ " [Ȱ�� : " + d.returnRangeDamageValue(null, MonsterList.getInt(MonsterName+".DEX"), 0, true) + " ~ " + d.returnRangeDamageValue(null, MonsterList.getInt(MonsterName+".DEX"), 0, false) + "]%enter%";
								Lore = Lore+ChatColor.DARK_AQUA+""+ChatColor.BOLD+" "+GoldBigDragon_RPG.Main.ServerOption.INT+" : "+ChatColor.WHITE+MonsterList.getInt(MonsterName+".INT")
								+ChatColor.GRAY+ " [���� : " + (MonsterList.getInt(MonsterName+".INT")/4)+ " ~ "+(int)(MonsterList.getInt(MonsterName+".INT")/2.5)+"]%enter%";
								Lore = Lore+ChatColor.GRAY+""+ChatColor.BOLD+" "+GoldBigDragon_RPG.Main.ServerOption.WILL+" : "+ChatColor.WHITE+MonsterList.getInt(MonsterName+".WILL")
								+ChatColor.GRAY+ " [ũ�� : " + d.getCritical(null,MonsterList.getInt(MonsterName+".LUK"), (int)MonsterList.getInt(MonsterName+".WILL"),0) + " %]%enter%";
								Lore = Lore+ChatColor.YELLOW+""+ChatColor.BOLD+" "+GoldBigDragon_RPG.Main.ServerOption.LUK+" : "+ChatColor.WHITE+MonsterList.getInt(MonsterName+".LUK")
								+ChatColor.GRAY+ " [ũ�� : " + d.getCritical(null,MonsterList.getInt(MonsterName+".LUK"), (int)MonsterList.getInt(MonsterName+".WILL"),0) + " %]%enter%";
								Lore = Lore+ChatColor.GRAY+""+ChatColor.BOLD+" ��� : "+ChatColor.WHITE+MonsterList.getInt(MonsterName+".DEF")+"%enter%";
								Lore = Lore+ChatColor.AQUA+""+ChatColor.BOLD+" ��ȣ : "+ChatColor.WHITE+MonsterList.getInt(MonsterName+".Protect")+"%enter%";
								Lore = Lore+ChatColor.BLUE+""+ChatColor.BOLD+" ���� ��� : "+ChatColor.WHITE+MonsterList.getInt(MonsterName+".Magic_DEF")+"%enter%";
								Lore = Lore+ChatColor.DARK_BLUE+""+ChatColor.BOLD+" ���� ��ȣ : "+ChatColor.WHITE+MonsterList.getInt(MonsterName+".Magic_Protect")+"%enter%";
								Lore = Lore+"%enter%"+ChatColor.YELLOW+""+ChatColor.BOLD+"[�� Ŭ���� ����]";

								String[] scriptA = Lore.split("%enter%");
								for(int counter = 0; counter < scriptA.length; counter++)
									scriptA[counter] =  " "+scriptA[counter];
								int id = 383;
								int data = 0;
								switch(MonsterList.getString(MonsterName+".Type"))
								{
									case "����ũ����" : case "ũ����" : data=50; break;
									case "�״����̷���" : case "���̷���" : data=51; break;
									case "�Ź�" : data=52; break;
									case "����" :case "���̾�Ʈ" : data=54; break;
									case "�ʴ���������" :case "Ư�뽽����" : case "ū������" :case "���뽽����" : case "����������" : data=55; break;
									case "����Ʈ" : data=56; break;
									case "�����Ǳ׸�" : data=57; break;
									case "������" : data=58; break;
									case "�����Ź�" : data=59; break;
									case "������" : data=60; break;
									case "������" : data=61; break;
									case "ū���׸�ť��" :case "Ư�븶�׸�ť��" : case "���븶�׸�ť��": case "���׸�ť��" : case "�������׸�ť��" : data=62; break;
									case "����" : data=65; break;
									case "����" : data=66; break;
									case "���������" : data=67; break;
									case "��ȣ��" : data=68; break;
									case "����" : data=90; break;
									case "��" : data=91; break;
									case "��" : data=92; break;
									case "��" : data=93; break;
									case "��¡��" : data=94; break;
									case "����" : data=95; break;
									case "������" : data=96; break;
									case "������" : data=98; break;
									case "��" : data=100; break;
									case "�䳢" : data=101; break;
									case "�ֹ�" : data=120; break;
									case "����" : id=399; break;
									case "�����巡��" : id=122; break;
									case "����ũ����Ż" : id=46; break;
								}
								Stack(ChatColor.WHITE+MonsterName, id, data, 1,Arrays.asList(scriptA), count+1+(count2*9), inv);
								ItemMeta a = inv.getItem(count+1+(count2*9)).getItemMeta();
								a.addEnchant(Enchantment.SILK_TOUCH, 3, true);
								inv.getItem(count+1+(count2*9)).setItemMeta(a);
								isExit = true;
								break;
							}
						}
						if(isExit==false)
						{
							DungeonConfig.set(Type+"."+count, null);
							DungeonConfig.saveConfig();
							Stack2(ChatColor.WHITE + "" + ChatColor.BOLD + "[����]", 383, 0, 1,Arrays.asList("",ChatColor.YELLOW + "[�� Ŭ���� ���]"), count+1+(count2*9), inv);
						}
					}
				}
			}
		}
		player.openInventory(inv);
	}
	
	public void DungeonMonsterChooseMain(Player player, String DungeonName, int slot)
	{
		Inventory inv = Bukkit.createInventory(null, 9, ChatColor.DARK_GREEN +""+ChatColor.BOLD +""+ "���� ���� : " +DungeonName);
		Stack2(ChatColor.RED + "" + ChatColor.BOLD + "[����]", 166,0,1,Arrays.asList(ChatColor.GRAY + "���� ������ ���� �ʽ��ϴ�."), 2, inv);
		Stack2(ChatColor.WHITE + "" + ChatColor.BOLD + "[�Ϲ�]", 383,0,1,Arrays.asList(ChatColor.GRAY + "�Ϲ����� ���� �� �ϳ��� ���ϴ�."), 4, inv);
		Stack2(ChatColor.AQUA + "" + ChatColor.BOLD + "[Ŀ����]", 52,0,1,Arrays.asList(ChatColor.GRAY + "Ŀ���� ���� �� �ϳ��� ���ϴ�.","",ChatColor.RED+"[���� ũ����Ż ������ ���͸�",ChatColor.RED+"������ ���, ������ ������ �˴ϴ�.]"), 6, inv);
		
		Stack2(ChatColor.WHITE + "" + ChatColor.BOLD + "�ݱ�", 324,0,1,Arrays.asList(ChatColor.GRAY + "â�� �ݽ��ϴ�.",ChatColor.BLACK+""+slot), 8, inv);
		Stack2(ChatColor.WHITE + "" + ChatColor.BOLD + "���� ���", 323,0,1,Arrays.asList(ChatColor.GRAY + "���� ȭ������ ���ư��ϴ�."), 0, inv);
		player.openInventory(inv);
	}
	
	public void DungeonSelectNormalMonsterChoose(Player player, String DungeonName, String Type, int slot)
	{
		Inventory inv = Bukkit.createInventory(null, 54, ChatColor.DARK_GREEN +""+ChatColor.BOLD +""+ "�Ϲ� ���� : " +DungeonName);

		Stack2(ChatColor.DARK_GREEN + "" + ChatColor.BOLD + "[����]",383,54,1,null, 0, inv);
		Stack2(ChatColor.GRAY + "" + ChatColor.BOLD + "[���̷���]",383,51,1,null, 1, inv);
		Stack2(ChatColor.GREEN + "" + ChatColor.BOLD + "[ũ����]",383,50,1,null, 2, inv);
		Stack2(ChatColor.GRAY + "" + ChatColor.BOLD + "[�Ź�]",383,52,1,null, 3, inv);
		Stack2(ChatColor.DARK_GRAY + "" + ChatColor.BOLD + "[���� �Ź�]",383,59,1,null, 4, inv);
		Stack2(ChatColor.DARK_PURPLE + "" + ChatColor.BOLD + "[������]",383,58,1,null, 5, inv);
		Stack2(ChatColor.GREEN + "" + ChatColor.BOLD + "[������]",383,55,1,null, 6, inv);
		Stack2(ChatColor.YELLOW + "" + ChatColor.BOLD + "[���׸� ť��]",383,62,1,null, 7, inv);
		Stack2(ChatColor.DARK_PURPLE + "" + ChatColor.BOLD + "[����]",383,66,1,null, 8, inv);
		Stack2(ChatColor.LIGHT_PURPLE + "" + ChatColor.BOLD + "[���� �Ǳ׸�]",383,57,1,null, 9, inv);
		Stack2(ChatColor.YELLOW + "" + ChatColor.BOLD + "[������]",383,61,1,null, 10, inv);
		Stack2(ChatColor.WHITE + "" + ChatColor.BOLD + "[����Ʈ]",383,56,1,null, 11, inv);
		Stack2(ChatColor.DARK_AQUA + "" + ChatColor.BOLD + "[��ȣ��]",383,68,1,null, 12, inv);
		Stack2(ChatColor.DARK_GRAY + "" + ChatColor.BOLD + "[����]",383,65,1,null, 13, inv);
		Stack2(ChatColor.LIGHT_PURPLE + "" + ChatColor.BOLD + "[����]",383,90,1,null, 14, inv);
		Stack2(ChatColor.WHITE + "" + ChatColor.BOLD + "[��]",383,91,1,null, 15, inv);
		Stack2(ChatColor.GRAY + "" + ChatColor.BOLD + "[��]",383,92,1,null, 16, inv);
		Stack2(ChatColor.WHITE + "" + ChatColor.BOLD + "[��]",383,93,1,null, 17, inv);
		Stack2(ChatColor.DARK_GRAY + "" + ChatColor.BOLD + "[��¡��]",383,94,1,null, 18, inv);
		Stack2(ChatColor.WHITE + "" + ChatColor.BOLD + "[����]",383,95,1,null, 19, inv);
		Stack2(ChatColor.WHITE + "" + ChatColor.BOLD + "[���� ��]",383,96,1,null, 20, inv);
		Stack2(ChatColor.WHITE + "" + ChatColor.BOLD + "[������]",383,98,1,null, 21, inv);
		Stack2(ChatColor.WHITE + "" + ChatColor.BOLD + "[��]",383,100,1,null, 22, inv);
		Stack2(ChatColor.WHITE + "" + ChatColor.BOLD + "[�䳢]",383,101,1,null, 23, inv);
		Stack2(ChatColor.WHITE + "" + ChatColor.BOLD + "[�ֹ�]",383,120,1,null, 24, inv);

		Stack2(ChatColor.WHITE + "" + ChatColor.BOLD + "�ݱ�", 324,0,1,Arrays.asList(ChatColor.GRAY + "â�� �ݽ��ϴ�.",ChatColor.BLACK+""+slot), 53, inv);
		Stack2(ChatColor.WHITE + "" + ChatColor.BOLD + "���� ���", 323,0,1,Arrays.asList(ChatColor.GRAY + "���� ȭ������ ���ư��ϴ�.",ChatColor.BLACK+Type), 45, inv);
		player.openInventory(inv);
	}
	
	public void DungeonSelectCustomMonsterChoose(Player player, String DungeonName, String Type, int slot, int page)
	{
	  	YamlController YC = new YamlController(GoldBigDragon_RPG.Main.Main.plugin);
		YamlManager MobList = YC.getNewConfig("Monster/MonsterList.yml");
		GoldBigDragon_RPG.Attack.Damage d = new GoldBigDragon_RPG.Attack.Damage();
		Inventory inv = Bukkit.createInventory(null, 54, ChatColor.DARK_GREEN +""+ChatColor.BOLD +ChatColor.BLACK+ "Ŀ���� ���� : " + (page+1));

		Object[] a= MobList.getKeys().toArray();

		int loc=0;
		for(int count = page*45; count < a.length;count++)
		{
			if(count > a.length || loc >= 45) break;
			String MonsterName =a[count].toString();
			String Lore=null;
			
			Lore = "%enter%"+ChatColor.WHITE+""+ChatColor.BOLD+" �̸� : "+ChatColor.WHITE+MobList.getString(MonsterName+".Name")+"%enter%";
			Lore = Lore+ChatColor.WHITE+""+ChatColor.BOLD+" Ÿ�� : "+ChatColor.WHITE+MobList.getString(MonsterName+".Type")+"%enter%";
			Lore = Lore+ChatColor.WHITE+""+ChatColor.BOLD+" ���� ���̿� : "+ChatColor.WHITE+MobList.getString(MonsterName+".Biome")+"%enter%";
			Lore = Lore+ChatColor.RED+""+ChatColor.BOLD+" ����� : "+ChatColor.WHITE+MobList.getInt(MonsterName+".HP")+"%enter%";
			Lore = Lore+ChatColor.AQUA+""+ChatColor.BOLD+" ����ġ : "+ChatColor.WHITE+MobList.getInt(MonsterName+".EXP")+"%enter%";
			Lore = Lore+ChatColor.YELLOW+""+ChatColor.BOLD+" ��� �ݾ� : "+ChatColor.WHITE+MobList.getInt(MonsterName+".MIN_Money")+" ~ "+MobList.getInt(MonsterName+".MAX_Money")+"%enter%";
			Lore = Lore+ChatColor.RED+""+ChatColor.BOLD+" "+GoldBigDragon_RPG.Main.ServerOption.STR+" : "+ChatColor.WHITE+MobList.getInt(MonsterName+".STR")
			+ChatColor.GRAY+ " [���� : " + d.CombatMinDamageGet(null, 0, MobList.getInt(MonsterName+".STR")) + " ~ " + d.CombatMaxDamageGet(null, 0, MobList.getInt(MonsterName+".STR")) + "]%enter%";
			Lore = Lore+ChatColor.GREEN+""+ChatColor.BOLD+" "+GoldBigDragon_RPG.Main.ServerOption.DEX+" : "+ChatColor.WHITE+MobList.getInt(MonsterName+".DEX")
			+ChatColor.GRAY+ " [Ȱ�� : " + d.returnRangeDamageValue(null, MobList.getInt(MonsterName+".DEX"), 0, true) + " ~ " + d.returnRangeDamageValue(null, MobList.getInt(MonsterName+".DEX"), 0, false) + "]%enter%";
			Lore = Lore+ChatColor.DARK_AQUA+""+ChatColor.BOLD+" "+GoldBigDragon_RPG.Main.ServerOption.INT+" : "+ChatColor.WHITE+MobList.getInt(MonsterName+".INT")
			+ChatColor.GRAY+ " [���� : " + (MobList.getInt(MonsterName+".INT")/4)+ " ~ "+(int)(MobList.getInt(MonsterName+".INT")/2.5)+"]%enter%";
			Lore = Lore+ChatColor.GRAY+""+ChatColor.BOLD+" "+GoldBigDragon_RPG.Main.ServerOption.WILL+" : "+ChatColor.WHITE+MobList.getInt(MonsterName+".WILL")
			+ChatColor.GRAY+ " [ũ�� : " + d.getCritical(null,MobList.getInt(MonsterName+".LUK"), (int)MobList.getInt(MonsterName+".WILL"),0) + " %]%enter%";
			Lore = Lore+ChatColor.YELLOW+""+ChatColor.BOLD+" "+GoldBigDragon_RPG.Main.ServerOption.LUK+" : "+ChatColor.WHITE+MobList.getInt(MonsterName+".LUK")
			+ChatColor.GRAY+ " [ũ�� : " + d.getCritical(null,MobList.getInt(MonsterName+".LUK"), (int)MobList.getInt(MonsterName+".WILL"),0) + " %]%enter%";
			Lore = Lore+ChatColor.GRAY+""+ChatColor.BOLD+" ��� : "+ChatColor.WHITE+MobList.getInt(MonsterName+".DEF")+"%enter%";
			Lore = Lore+ChatColor.AQUA+""+ChatColor.BOLD+" ��ȣ : "+ChatColor.WHITE+MobList.getInt(MonsterName+".Protect")+"%enter%";
			Lore = Lore+ChatColor.BLUE+""+ChatColor.BOLD+" ���� ��� : "+ChatColor.WHITE+MobList.getInt(MonsterName+".Magic_DEF")+"%enter%";
			Lore = Lore+ChatColor.DARK_BLUE+""+ChatColor.BOLD+" ���� ��ȣ : "+ChatColor.WHITE+MobList.getInt(MonsterName+".Magic_Protect")+"%enter%";

			String[] scriptA = Lore.split("%enter%");
			for(int counter = 0; counter < scriptA.length; counter++)
				scriptA[counter] =  " "+scriptA[counter];
			int id = 383;
			int data = 0;
			switch(MobList.getString(MonsterName+".Type"))
			{
				case "����ũ����" : case "ũ����" : data=50; break;
				case "�״����̷���" : case "���̷���" : data=51; break;
				case "�Ź�" : data=52; break;
				case "����" :case "���̾�Ʈ" : data=54; break;
				case "�ʴ���������" :case "Ư�뽽����" : case "ū������" :case "���뽽����" : case "����������" : data=55; break;
				case "����Ʈ" : data=56; break;
				case "�����Ǳ׸�" : data=57; break;
				case "������" : data=58; break;
				case "�����Ź�" : data=59; break;
				case "������" : data=60; break;
				case "������" : data=61; break;
				case "ū���׸�ť��" :case "Ư�븶�׸�ť��" : case "���븶�׸�ť��": case "���׸�ť��" : case "�������׸�ť��" : data=62; break;
				case "����" : data=65; break;
				case "����" : data=66; break;
				case "���������" : data=67; break;
				case "��ȣ��" : data=68; break;
				case "����" : data=90; break;
				case "��" : data=91; break;
				case "��" : data=92; break;
				case "��" : data=93; break;
				case "��¡��" : data=94; break;
				case "����" : data=95; break;
				case "������" : data=96; break;
				case "������" : data=98; break;
				case "��" : data=100; break;
				case "�䳢" : data=101; break;
				case "�ֹ�" : data=120; break;
				case "����" : id=399; break;
				case "�����巡��" : id=122; break;
				case "����ũ����Ż" : id=46; break;
			}
			
			Stack(ChatColor.WHITE+MonsterName, id, data, 1,Arrays.asList(scriptA), loc, inv);
			loc=loc+1;
		}
		
		if(a.length-(page*44)>45)
		Stack(ChatColor.WHITE + "" + ChatColor.BOLD + "���� ������", 323,0,1,Arrays.asList(ChatColor.GRAY + "���� �������� �̵� �մϴ�."), 50, inv);
		if(page!=0)
		Stack(ChatColor.WHITE + "" + ChatColor.BOLD + "���� ������", 323,0,1,Arrays.asList(ChatColor.GRAY + "���� �������� �̵� �մϴ�."), 48, inv);

		Stack(ChatColor.WHITE + "" + ChatColor.BOLD + "���� ���", 323,0,1,Arrays.asList(ChatColor.GRAY + "���� ȭ������ ���ư��ϴ�.",ChatColor.BLACK+Type), 45, inv);
		Stack(ChatColor.WHITE + "" + ChatColor.BOLD + "�ݱ�", 324,0,1,Arrays.asList(ChatColor.GRAY + "â�� �ݽ��ϴ�.",ChatColor.BLACK+""+slot,ChatColor.BLACK+DungeonName), 53, inv);
		player.openInventory(inv);
	}

	public void DungeonMusicSettingGUI(Player player, int page,String DungeonName, boolean isBOSS)
	{
		Inventory inv = Bukkit.createInventory(null, 54, ChatColor.DARK_GREEN +""+ChatColor.BOLD +""+ChatColor.BLACK + "���� ����� : " + (page+1));
		int loc=0;
		int model = new GoldBigDragon_RPG.Util.Number().RandomNum(0, 11);
		for(int count = page*45; count < new OtherPlugins.NoteBlockAPIMain().Musics.size();count++)
		{
			if(model<11)
				model=model+1;
			else
				model=0;
			String lore = " %enter%"+ChatColor.DARK_AQUA+"[Ʈ��] "+ChatColor.BLUE +""+ count+"%enter%"
			+ChatColor.DARK_AQUA+"[����] "+ChatColor.BLUE +""+ new OtherPlugins.NoteBlockAPIMain().getTitle(count)+"%enter%"
			+ChatColor.DARK_AQUA+"[����] "+ChatColor.BLUE+new OtherPlugins.NoteBlockAPIMain().getAuthor(count)+"%enter%"+ChatColor.DARK_AQUA+"[����] ";
			String Description = new OtherPlugins.NoteBlockAPIMain().getDescription(count);
			String lore2="";
			int a = 0;
			for(int counter = 0; counter <Description.toCharArray().length; counter++)
			{
				lore2 = lore2+ChatColor.BLUE+Description.toCharArray()[counter];
				a=a+1;
				if(a >= 15)
				{
					a = 0;
					lore2 = lore2+"%enter%      ";
				}
			}
			lore = lore + lore2+"%enter% %enter%"+ChatColor.YELLOW+"[�� Ŭ���� ����� ����]";
			if(count > new OtherPlugins.NoteBlockAPIMain().Musics.size() || loc >= 45) break;
			Stack2(ChatColor.WHITE + "" + ChatColor.BOLD + count, 2256+model,0,1,Arrays.asList(lore.split("%enter%")), loc, inv);
			
			loc=loc+1;
		}
		
		if(new OtherPlugins.NoteBlockAPIMain().Musics.size()-(page*44)>45)
		Stack2(ChatColor.WHITE + "" + ChatColor.BOLD + "���� ������", 323,0,1,Arrays.asList(ChatColor.GRAY + "���� �������� �̵� �մϴ�."), 50, inv);
		if(page!=0)
		Stack2(ChatColor.WHITE + "" + ChatColor.BOLD + "���� ������", 323,0,1,Arrays.asList(ChatColor.GRAY + "���� �������� �̵� �մϴ�."), 48, inv);

		Stack2(ChatColor.WHITE + "" + ChatColor.BOLD + "���� ���", 323,0,1,Arrays.asList(ChatColor.GRAY + "���� ȭ������ ���ư��ϴ�.",ChatColor.BLACK+""+isBOSS), 45, inv);
		Stack2(ChatColor.WHITE + "" + ChatColor.BOLD + "�ݱ�", 324,0,1,Arrays.asList(ChatColor.GRAY + "â�� �ݽ��ϴ�.",ChatColor.BLACK+DungeonName), 53, inv);
		player.openInventory(inv);
	}
	//DungeonGUI//
	
	
	//EnterCardGUI//
	public void EnterCardSetUpGUI(Player player, String EnterCardName)
	{
	  	YamlController YC = new YamlController(GoldBigDragon_RPG.Main.Main.plugin);
		YamlManager DungeonConfig = YC.getNewConfig("Dungeon/EnterCardList.yml");
		
		Inventory inv = Bukkit.createInventory(null, 9, ChatColor.DARK_GREEN+""+ChatColor.BOLD +ChatColor.BLACK+"������ ����");
		if(DungeonConfig.getString(EnterCardName+".Dungeon")!= null)
			Stack2(ChatColor.WHITE + "" + ChatColor.BOLD + "[������ ���� ����]", 52,0,1,Arrays.asList("",ChatColor.BLUE + "���� �̾��� ���� : "+ChatColor.WHITE+DungeonConfig.getString(EnterCardName+".Dungeon")), 2, inv);
		else
			Stack2(ChatColor.WHITE + "" + ChatColor.BOLD + "[������ ���� ����]", 166,0,1,Arrays.asList("",ChatColor.BLUE + "���� �̾��� ���� : "+ChatColor.WHITE+"����"), 2, inv);
			
		Stack2(ChatColor.WHITE + "" + ChatColor.BOLD + "[������ ���� ����]", DungeonConfig.getInt(EnterCardName+".ID"),DungeonConfig.getInt(EnterCardName+".DATA"),1,Arrays.asList("",ChatColor.BLUE + "���� ������ Ÿ�� : "+ChatColor.WHITE+DungeonConfig.getInt(EnterCardName+".ID")+":"+ DungeonConfig.getInt(EnterCardName+".DATA"),"",ChatColor.YELLOW+"[F3 + H �Է½� Ÿ�� Ȯ�� ����]"), 3, inv);
		Stack2(ChatColor.WHITE + "" + ChatColor.BOLD + "[������ ���� �ʱ�ȭ]", 325,0,1,Arrays.asList("",ChatColor.WHITE + "������ ���°� ��Ÿ���� ���� ��",ChatColor.WHITE+"������ �ʱ�ȭ �� �ݴϴ�."), 4, inv);
		Stack2(ChatColor.WHITE + "" + ChatColor.BOLD + "[������ ���� �ο�]", 397,3,1,Arrays.asList("",ChatColor.BLUE + "���� ���� �ο� : "+ChatColor.WHITE+DungeonConfig.getInt(EnterCardName+".Capacity")+" ��"), 5, inv);

		String Time = null;
		if(DungeonConfig.getInt(EnterCardName.toString()+".Hour")!=-1)
		{
			if(DungeonConfig.getInt(EnterCardName.toString()+".Hour")!=0)
				Time = DungeonConfig.getInt(EnterCardName.toString()+".Hour")+"�ð� ";
			if(DungeonConfig.getInt(EnterCardName.toString()+".Min")!=0)
				Time = Time+DungeonConfig.getInt(EnterCardName.toString()+".Min")+"�� ";
			Time = Time+DungeonConfig.getInt(EnterCardName.toString()+".Sec")+"��";
		}
		else
			Time = "������";
		Stack2(ChatColor.WHITE + "" + ChatColor.BOLD + "[������ ��ȿ �ð�]", 347,0,1,Arrays.asList("",ChatColor.WHITE+Time), 6, inv);

		Stack2(ChatColor.WHITE + "" + ChatColor.BOLD + "�ݱ�", 324,0,1,Arrays.asList(ChatColor.GRAY + "â�� �ݽ��ϴ�.",ChatColor.BLACK+EnterCardName), 8, inv);
		Stack2(ChatColor.WHITE + "" + ChatColor.BOLD + "���� ���", 323,0,1,Arrays.asList(ChatColor.GRAY + "���� ȭ������ ���ư��ϴ�."), 0, inv);

		player.openInventory(inv);
	}

	public void EnterCardDungeonSettingGUI(Player player, int page, String EnterCardName)
	{
	  	YamlController YC = new YamlController(GoldBigDragon_RPG.Main.Main.plugin);
		YamlManager DungeonConfig = YC.getNewConfig("Dungeon/DungeonList.yml");

		Inventory inv = Bukkit.createInventory(null, 54, ChatColor.DARK_GREEN +""+ChatColor.BOLD +""+ChatColor.BLACK+ "������ ���� : " + (page+1));
		Object[] DungeonList = DungeonConfig.getConfigurationSection("").getKeys(false).toArray();
		
		int loc=0;
		for(int count = page*45; count < DungeonList.length;count++)
		{
			YamlManager DungeonOptionYML = YC.getNewConfig("Dungeon/Dungeon/"+DungeonList[count].toString()+"/Option.yml");
			
			Stack2(ChatColor.WHITE + "" + ChatColor.BOLD + DungeonList[count].toString(), 52,0,1,Arrays.asList(
			"",ChatColor.BLUE+"���� ���� : "+ChatColor.WHITE+DungeonOptionYML.getString("Type.Name")
			,ChatColor.BLUE+"���� ũ�� : "+ChatColor.WHITE+DungeonOptionYML.getInt("Size")
			,ChatColor.BLUE+"���� ������ : "+ChatColor.WHITE+DungeonOptionYML.getInt("Maze_Level")
			,ChatColor.BLUE+"���� ���� : "+ChatColor.WHITE+DungeonOptionYML.getInt("District.Level")+" �̻�"
			,ChatColor.BLUE+"���� ���� ���� : "+ChatColor.WHITE+DungeonOptionYML.getInt("District.RealLevel")+" �̻�"
			,"",ChatColor.BLUE+"[�⺻ Ŭ���� ����]"
			,ChatColor.BLUE+" - "+ChatColor.WHITE+DungeonOptionYML.getInt("Reward.Money")+" "+GoldBigDragon_RPG.Main.ServerOption.Money
			,ChatColor.BLUE+" - "+ChatColor.WHITE+DungeonOptionYML.getInt("Reward.EXP")+ChatColor.AQUA+" "+ChatColor.BOLD+"EXP"
			,"",ChatColor.YELLOW+"[�� Ŭ���� ����]"), loc, inv);
			
			loc=loc+1;
		}
		
		if(DungeonList.length-(page*44)>45)
		Stack2(ChatColor.WHITE + "" + ChatColor.BOLD + "���� ������", 323,0,1,Arrays.asList(ChatColor.GRAY + "���� �������� �̵� �մϴ�."), 50, inv);
		if(page!=0)
		Stack2(ChatColor.WHITE + "" + ChatColor.BOLD + "���� ������", 323,0,1,Arrays.asList(ChatColor.GRAY + "���� �������� �̵� �մϴ�."), 48, inv);

		Stack2(ChatColor.WHITE + "" + ChatColor.BOLD + "���� ���", 323,0,1,Arrays.asList(ChatColor.GRAY + "���� ȭ������ ���ư��ϴ�."), 45, inv);
		Stack2(ChatColor.WHITE + "" + ChatColor.BOLD + "�ݱ�", 324,0,1,Arrays.asList(ChatColor.GRAY + "â�� �ݽ��ϴ�.",ChatColor.BLACK+EnterCardName), 53, inv);
		player.openInventory(inv);
		return;
	}
	//EnterCardGUI//

	//AltarGUI//
	public void AltarShapeListGUI(Player player)
	{
		Inventory inv = Bukkit.createInventory(null, 54, ChatColor.DARK_GREEN +""+ChatColor.BOLD +""+ChatColor.BLACK+ "���� ����");
		Stack2(ChatColor.DARK_GREEN + "" + ChatColor.BOLD + "[�̳� �� ����]", 48,0,1,Arrays.asList("",ChatColor.BLUE + "ũ�� : "+ChatColor.WHITE+"����",ChatColor.BLUE+"���� ���� �ð� : "+ChatColor.WHITE+"13��","",ChatColor.GOLD+""+ChatColor.BOLD+"[     ���డ     ]",ChatColor.WHITE+"GoldBigDragon [���]"), 0, inv);
		Stack2(ChatColor.YELLOW + "" + ChatColor.BOLD + "[����ף]", 41,0,1,Arrays.asList("",ChatColor.BLUE + "ũ�� : "+ChatColor.WHITE+"����",ChatColor.BLUE+"���� ���� �ð� : "+ChatColor.WHITE+"15��","",ChatColor.GOLD+""+ChatColor.BOLD+"[     ���డ     ]",ChatColor.WHITE+"ComputerFairy [����]",ChatColor.WHITE+"GoldBigDragon [��]"), 1, inv);
		Stack2(ChatColor.GRAY + "" + ChatColor.BOLD + "[���� ����]", 1,0,1,Arrays.asList("",ChatColor.BLUE + "ũ�� : "+ChatColor.WHITE+"����",ChatColor.BLUE+"���� ���� �ð� : "+ChatColor.WHITE+"1�� 5��","",ChatColor.GOLD+""+ChatColor.BOLD+"[     ���డ     ]",ChatColor.WHITE+"GoldBigDragon [���]"), 2, inv);
		Stack2(ChatColor.GRAY + "" + ChatColor.BOLD + "[�غδ�]", 1,5,1,Arrays.asList("",ChatColor.BLUE + "ũ�� : "+ChatColor.WHITE+"����",ChatColor.BLUE+"���� ���� �ð� : "+ChatColor.WHITE+"8��","",ChatColor.GOLD+""+ChatColor.BOLD+"[     ���డ     ]",ChatColor.WHITE+"GoldBigDragon [���]"), 3, inv);
		Stack2(ChatColor.GRAY + "" + ChatColor.BOLD + "[�׽�Ʈ�� ����]", 48,0,1,null, 44, inv);
		
		
		Stack2(ChatColor.WHITE + "" + ChatColor.BOLD + "���� ���", 323,0,1,Arrays.asList(ChatColor.GRAY + "���� ȭ������ ���ư��ϴ�."), 45, inv);
		Stack2(ChatColor.WHITE + "" + ChatColor.BOLD + "�ݱ�", 324,0,1,Arrays.asList(ChatColor.GRAY + "â�� �ݽ��ϴ�."), 53, inv);
		player.openInventory(inv);
		return;
	}

	public void AltarSettingGUI(Player player, String AltarName)
	{
		Inventory inv = Bukkit.createInventory(null, 9, ChatColor.DARK_GREEN +""+ChatColor.BOLD +""+ChatColor.BLACK+ "���� ����");

	  	YamlController YC = new YamlController(GoldBigDragon_RPG.Main.Main.plugin);
		YamlManager AltarList = YC.getNewConfig("Dungeon/AltarList.yml");
		YamlManager AltarConfig = YC.getNewConfig("Dungeon/Altar/"+AltarName+".yml");
		Stack2(ChatColor.WHITE+""+ChatColor.BOLD+"[�̸� ����]", 421,0,1,Arrays.asList(ChatColor.GRAY+"������ �̸��� �����մϴ�.","",ChatColor.BLUE+"[���� ���� �̸�]",ChatColor.WHITE+AltarList.getString(AltarName+".Name")), 2, inv);
		if(AltarConfig.getString("NormalDungeon")==null)
			Stack2(ChatColor.WHITE+""+ChatColor.BOLD+"[�Ϲ� ����]", 166,0,1,Arrays.asList(ChatColor.GRAY+"�� ���ܿ� �������� ������",ChatColor.GRAY+"�������� ������ ��� �����Ǵ�",ChatColor.GRAY+"�Ϲ� ������ �����մϴ�.","",ChatColor.BLUE+"[���� ������ �Ϲ� ����]",ChatColor.WHITE+"�������� ����"), 4, inv);
		else
		{
			YamlManager DungeonList = YC.getNewConfig("Dungeon/DungeonList.yml");
			if(DungeonList.contains(AltarConfig.getString("NormalDungeon")))
				Stack2(ChatColor.WHITE+""+ChatColor.BOLD+"[�Ϲ� ����]", 52,0,1,Arrays.asList(ChatColor.GRAY+"���ܿ� �������� ������",ChatColor.GRAY+"�������� ������ ��� �����Ǵ�",ChatColor.GRAY+"�Ϲ� ������ �����մϴ�.","",ChatColor.BLUE+"[���� ������ �Ϲ� ����]",ChatColor.WHITE+AltarConfig.getString("NormalDungeon")), 4, inv);
			else
			{
				AltarConfig.set("NormalDungeon", null);
				AltarConfig.saveConfig();
				Stack2(ChatColor.WHITE+""+ChatColor.BOLD+"[�Ϲ� ����]", 166,0,1,Arrays.asList(ChatColor.GRAY+"���ܿ� �������� ������",ChatColor.GRAY+"�������� ������ ��� �����Ǵ�",ChatColor.GRAY+"�Ϲ� ������ �����մϴ�.","",ChatColor.BLUE+"[���� ������ �Ϲ� ����]",ChatColor.WHITE+"�������� ����"), 4, inv);
			}
		}
		Stack2(ChatColor.WHITE+""+ChatColor.BOLD+"[������ ���]", 358,0,1,Arrays.asList(ChatColor.GRAY+"���ܿ��� ��� ������",ChatColor.GRAY+"�������� ����մϴ�.","",ChatColor.YELLOW+"[�� Ŭ���� ������ ���]"), 6, inv);

		Stack2(ChatColor.WHITE + "" + ChatColor.BOLD + "�ݱ�", 324,0,1,Arrays.asList(ChatColor.GRAY + "â�� �ݽ��ϴ�.",ChatColor.BLACK+AltarName), 8, inv);
		Stack2(ChatColor.WHITE + "" + ChatColor.BOLD + "���� ���", 323,0,1,Arrays.asList(ChatColor.GRAY + "���� ȭ������ ���ư��ϴ�."), 0, inv);

		player.openInventory(inv);
		return;
	}
	
	public void AltarDungeonSettingGUI(Player player, int page, String AltarName)
	{
	  	YamlController YC = new YamlController(GoldBigDragon_RPG.Main.Main.plugin);
		YamlManager DungeonConfig = YC.getNewConfig("Dungeon/DungeonList.yml");

		Inventory inv = Bukkit.createInventory(null, 54, ChatColor.DARK_GREEN +""+ChatColor.BOLD +""+ChatColor.BLACK+ "���� ���� : " + (page+1));
		Object[] DungeonList = DungeonConfig.getConfigurationSection("").getKeys(false).toArray();
		
		int loc=0;
		for(int count = page*45; count < DungeonList.length;count++)
		{
			YamlManager DungeonOptionYML = YC.getNewConfig("Dungeon/Dungeon/"+DungeonList[count].toString()+"/Option.yml");
			
			Stack2(ChatColor.WHITE + "" + ChatColor.BOLD + DungeonList[count].toString(), 52,0,1,Arrays.asList(
			"",ChatColor.BLUE+"���� ���� : "+ChatColor.WHITE+DungeonOptionYML.getString("Type.Name")
			,ChatColor.BLUE+"���� ũ�� : "+ChatColor.WHITE+DungeonOptionYML.getInt("Size")
			,ChatColor.BLUE+"���� ������ : "+ChatColor.WHITE+DungeonOptionYML.getInt("Maze_Level")
			,ChatColor.BLUE+"���� ���� : "+ChatColor.WHITE+DungeonOptionYML.getInt("District.Level")+" �̻�"
			,ChatColor.BLUE+"���� ���� ���� : "+ChatColor.WHITE+DungeonOptionYML.getInt("District.RealLevel")+" �̻�"
			,"",ChatColor.BLUE+"[�⺻ Ŭ���� ����]"
			,ChatColor.BLUE+" - "+ChatColor.WHITE+DungeonOptionYML.getInt("Reward.Money")+" "+GoldBigDragon_RPG.Main.ServerOption.Money
			,ChatColor.BLUE+" - "+ChatColor.WHITE+DungeonOptionYML.getInt("Reward.EXP")+ChatColor.AQUA+" "+ChatColor.BOLD+"EXP"
			,"",ChatColor.YELLOW+"[�� Ŭ���� ����]"), loc, inv);
			
			loc=loc+1;
		}
		
		if(DungeonList.length-(page*44)>45)
		Stack2(ChatColor.WHITE + "" + ChatColor.BOLD + "���� ������", 323,0,1,Arrays.asList(ChatColor.GRAY + "���� �������� �̵� �մϴ�."), 50, inv);
		if(page!=0)
		Stack2(ChatColor.WHITE + "" + ChatColor.BOLD + "���� ������", 323,0,1,Arrays.asList(ChatColor.GRAY + "���� �������� �̵� �մϴ�."), 48, inv);

		Stack2(ChatColor.WHITE + "" + ChatColor.BOLD + "���� ���", 323,0,1,Arrays.asList(ChatColor.GRAY + "���� ȭ������ ���ư��ϴ�."), 45, inv);
		Stack2(ChatColor.WHITE + "" + ChatColor.BOLD + "�ݱ�", 324,0,1,Arrays.asList(ChatColor.GRAY + "â�� �ݽ��ϴ�.",AltarName), 53, inv);
		player.openInventory(inv);
		return;
	}
	
	public void AltarEnterCardSettingGUI(Player player, int page, String AltarName)
	{
	  	YamlController YC = new YamlController(GoldBigDragon_RPG.Main.Main.plugin);
		YamlManager AlterConfig = YC.getNewConfig("Dungeon/Altar/"+AltarName+".yml");

		Inventory inv = Bukkit.createInventory(null, 54, ChatColor.DARK_GREEN +""+ChatColor.BOLD +""+ChatColor.BLACK+ "��ϵ� ������ : " + (page+1));

		if(AlterConfig.getConfigurationSection("EnterCard").getKeys(false).size()!=0)
		{
			Object[] EnterCardList = AlterConfig.getConfigurationSection("EnterCard").getKeys(false).toArray();

			int loc=0;
			for(int count = page*45; count < EnterCardList.length;count++)
			{
				YamlManager EnterCard = YC.getNewConfig("Dungeon/EnterCardList.yml");
				if(EnterCard.contains(EnterCardList[count].toString()))
				{
					if(EnterCard.getString(EnterCardList[count].toString()+".Dungeon")==null)
						Stack2(ChatColor.WHITE + "" + ChatColor.BOLD + EnterCardList[count].toString(), EnterCard.getInt(EnterCardList[count].toString()+".ID"),EnterCard.getInt(EnterCardList[count].toString()+".DATA"),1,Arrays.asList(
								"",ChatColor.BLUE+"����� ���� : "+ChatColor.WHITE+"����",
								ChatColor.BLUE+"���� ���� �ο� : "+ChatColor.WHITE+""+EnterCard.getInt(EnterCardList[count].toString()+".Capacity"),
								"",ChatColor.RED+"[Shift + ��Ŭ���� ��� ����]"), loc, inv);
					else
					{
						YamlManager Dungeon = YC.getNewConfig("Dungeon/DungeonList.yml");
						if(Dungeon.contains(EnterCard.getString(EnterCardList[count].toString()+".Dungeon")))
						{
							Stack2(ChatColor.WHITE + "" + ChatColor.BOLD + EnterCardList[count].toString(), EnterCard.getInt(EnterCardList[count].toString()+".ID"),EnterCard.getInt(EnterCardList[count].toString()+".DATA"),1,Arrays.asList(
							"",ChatColor.BLUE+"����� ���� : "+ChatColor.WHITE+""+EnterCard.getString(EnterCardList[count].toString()+".Dungeon"),
							ChatColor.BLUE+"���� ���� �ο� : "+ChatColor.WHITE+""+EnterCard.getInt(EnterCardList[count].toString()+".Capacity"),
							"",ChatColor.RED+"[Shift + ��Ŭ���� ��� ����]"), loc, inv);
						}
						else
						{
							EnterCard.set(EnterCardList[count].toString()+".Dungeon",null);
							EnterCard.saveConfig();
							Stack2(ChatColor.WHITE + "" + ChatColor.BOLD + EnterCardList[count].toString(), EnterCard.getInt(EnterCardList[count].toString()+".ID"),EnterCard.getInt(EnterCardList[count].toString()+".DATA"),1,Arrays.asList(
									"",ChatColor.BLUE+"����� ���� : "+ChatColor.WHITE+"����",
									ChatColor.BLUE+"���� ���� �ο� : "+ChatColor.WHITE+""+EnterCard.getInt(EnterCardList[count].toString()+".Capacity"),
									"",ChatColor.RED+"[Shift + ��Ŭ���� ��� ����]"), loc, inv);
						}
							
					}
					loc=loc+1;
				}
				else
				{
					AlterConfig.removeKey("EnterCard."+EnterCardList[count].toString());
					AlterConfig.saveConfig();
				}
			}
			if(EnterCardList.length-(page*44)>45)
			Stack2(ChatColor.WHITE + "" + ChatColor.BOLD + "���� ������", 323,0,1,Arrays.asList(ChatColor.GRAY + "���� �������� �̵� �մϴ�."), 50, inv);
			if(page!=0)
			Stack2(ChatColor.WHITE + "" + ChatColor.BOLD + "���� ������", 323,0,1,Arrays.asList(ChatColor.GRAY + "���� �������� �̵� �մϴ�."), 48, inv);
		}
		
		Stack2(ChatColor.WHITE + "" + ChatColor.BOLD + "���� ���", 323,0,1,Arrays.asList(ChatColor.GRAY + "���� ȭ������ ���ư��ϴ�."), 45, inv);
		Stack2(ChatColor.WHITE + "" + ChatColor.BOLD + "������ ���", 386,0,1,Arrays.asList(ChatColor.GRAY + "���ܿ� �������� ����մϴ�."), 49, inv);
		Stack2(ChatColor.WHITE + "" + ChatColor.BOLD + "�ݱ�", 324,0,1,Arrays.asList(ChatColor.GRAY + "â�� �ݽ��ϴ�.",AltarName), 53, inv);
		player.openInventory(inv);
		return;
	}
	
	public void AltarEnterCardListGUI(Player player, int page, String AltarName)
	{
	  	YamlController YC = new YamlController(GoldBigDragon_RPG.Main.Main.plugin);

		Inventory inv = Bukkit.createInventory(null, 54, ChatColor.DARK_GREEN +""+ChatColor.BOLD +""+ChatColor.BLACK+ "������ ������ ��� : " + (page+1));
		
		YamlManager DungeonConfig = YC.getNewConfig("Dungeon/EnterCardList.yml");
		Object[] DungeonList = DungeonConfig.getConfigurationSection("").getKeys(false).toArray();
		int loc=0;
		for(int count = page*45; count < DungeonList.length;count++)
		{
			if(DungeonConfig.getString(DungeonList[count].toString()+".Dungeon")==null)
				Stack2(ChatColor.WHITE + "" + ChatColor.BOLD + DungeonList[count].toString(), DungeonConfig.getInt(DungeonList[count].toString()+".ID"),DungeonConfig.getInt(DungeonList[count].toString()+".DATA"),1,Arrays.asList(
				"",ChatColor.BLUE+"����� ���� : "+ChatColor.WHITE+"����",
				ChatColor.BLUE+"���� ���� �ο� : "+ChatColor.WHITE+""+DungeonConfig.getInt(DungeonList[count].toString()+".Capacity"),
				"",ChatColor.YELLOW+"[�� Ŭ���� ������ ���]"), loc, inv);
			else
			{
				YamlManager Dungeon = YC.getNewConfig("Dungeon/DungeonList.yml");
				if(Dungeon.contains(DungeonConfig.getString(DungeonList[count].toString()+".Dungeon")))
				{
					Stack2(ChatColor.WHITE + "" + ChatColor.BOLD + DungeonList[count].toString(), DungeonConfig.getInt(DungeonList[count].toString()+".ID"),DungeonConfig.getInt(DungeonList[count].toString()+".DATA"),1,Arrays.asList(
					"",ChatColor.BLUE+"����� ���� : "+ChatColor.WHITE+""+DungeonConfig.getString(DungeonList[count].toString()+".Dungeon"),
					ChatColor.BLUE+"���� ���� �ο� : "+ChatColor.WHITE+""+DungeonConfig.getInt(DungeonList[count].toString()+".Capacity"),
					"",ChatColor.YELLOW+"[�� Ŭ���� ������ ���]"), loc, inv);
				}
				else
				{
					DungeonConfig.set(DungeonList[count].toString()+".Dungeon",null);
					DungeonConfig.saveConfig();
					Stack2(ChatColor.WHITE + "" + ChatColor.BOLD + DungeonList[count].toString(), DungeonConfig.getInt(DungeonList[count].toString()+".ID"),DungeonConfig.getInt(DungeonList[count].toString()+".DATA"),1,Arrays.asList(
					"",ChatColor.BLUE+"����� ���� : "+ChatColor.WHITE+"����",
					ChatColor.BLUE+"���� ���� �ο� : "+ChatColor.WHITE+""+DungeonConfig.getInt(DungeonList[count].toString()+".Capacity"),
					"",ChatColor.YELLOW+"[�� Ŭ���� ������ ���]"), loc, inv);
				}
			}
			loc=loc+1;
		}
		
		if(DungeonList.length-(page*44)>45)
		Stack2(ChatColor.WHITE + "" + ChatColor.BOLD + "���� ������", 323,0,1,Arrays.asList(ChatColor.GRAY + "���� �������� �̵� �մϴ�."), 50, inv);
		if(page!=0)
		Stack2(ChatColor.WHITE + "" + ChatColor.BOLD + "���� ������", 323,0,1,Arrays.asList(ChatColor.GRAY + "���� �������� �̵� �մϴ�."), 48, inv);

		Stack2(ChatColor.WHITE + "" + ChatColor.BOLD + "���� ���", 323,0,1,Arrays.asList(ChatColor.GRAY + "���� ȭ������ ���ư��ϴ�."), 45, inv);
		Stack2(ChatColor.WHITE + "" + ChatColor.BOLD + "�ݱ�", 324,0,1,Arrays.asList(ChatColor.GRAY + "â�� �ݽ��ϴ�.",AltarName), 53, inv);
		player.openInventory(inv);
		return;
	}
	
	public void AltarUseGUI(Player player, String AltarName)
	{
		Inventory inv = Bukkit.createInventory(null, 9, ChatColor.DARK_GREEN +""+ChatColor.BOLD +""+ChatColor.BLACK+ "���ܿ� ������ ��ġ�� �������� �̵��մϴ�");

		Stack2(AltarName, 160,0,1,null, 0, inv);
		Stack2(AltarName, 160,0,1,null, 1, inv);
		Stack2(AltarName, 160,0,1,null, 2, inv);
		Stack2(AltarName, 160,0,1,null, 3, inv);
		Stack2(AltarName, 160,0,1,null, 5, inv);
		Stack2(AltarName, 160,0,1,null, 6, inv);
		Stack2(AltarName, 160,0,1,null, 7, inv);
		Stack2(AltarName, 160,0,1,null, 8, inv);
		player.openInventory(inv);
		return;
	}
	//AltarGUI//
	
	public void DungeonEXIT(Player player)
	{
		Inventory inv = Bukkit.createInventory(null, 9, ChatColor.DARK_GREEN +""+ChatColor.BOLD +""+ChatColor.BLACK+ "�������� �����ðڽ��ϱ�?");

		Stack2(ChatColor.RED+""+ChatColor.BOLD+"[���� �ܷ�]", 166,0,1,null, 3, inv);
		Stack2(ChatColor.BLUE+""+ChatColor.BOLD+"[���� ����]", 138,0,1,null, 5, inv);
		player.openInventory(inv);
		return;
	}
	
	
	

	//DungeonGUI Click//
	public void DungeonListMainGUIClick(InventoryClickEvent event)
	{
		GoldBigDragon_RPG.Effect.Sound s = new GoldBigDragon_RPG.Effect.Sound();
		event.setCancelled(true);
		Player player = (Player) event.getWhoClicked();
		
		int page =  Integer.parseInt(event.getInventory().getTitle().split(" : ")[1])-1;
		int Type = event.getInventory().getItem(47).getTypeId();
		
		if(event.getCurrentItem()!=null)
			if(event.getCurrentItem().getType()!=Material.AIR)
				switch (event.getSlot())
				{
					case 45://���� ���
						s.SP(player, Sound.ITEM_PICKUP, 0.8F, 1.0F);
						new OPBoxGUI().OPBoxGUI_Main(player, (byte) 3);
						return;
					case 53://������
						s.SP(player, Sound.PISTON_RETRACT, 0.8F, 1.8F);
						player.closeInventory();
						return;
					case 47://Ÿ�� ����
						s.SP(player, Sound.ITEM_PICKUP, 0.8F, 1.0F);
						if(event.isLeftClick())
						{
							if(Type == 52)
								DungeonListMainGUI(player, 0,358);
							else if(Type == 358)
								DungeonListMainGUI(player, 0,120);
							else if(Type == 120)
								DungeonListMainGUI(player, 0,52);
						}
						else
						{
							if(Type == 52)
								DungeonListMainGUI(player, 0,120);
							else if(Type == 358)
								DungeonListMainGUI(player, 0,52);
							else if(Type == 120)
								DungeonListMainGUI(player, 0,358);
						}
						return;
					case 48://���� ������
						s.SP(player, Sound.ITEM_PICKUP, 0.8F, 1.0F);
						DungeonListMainGUI(player, page-1,Type);
						return;
					case 49://�� ����
						if(GoldBigDragon_RPG.Main.ServerOption.DungeonTheme.isEmpty())
						{
							s.SP(player, Sound.ORB_PICKUP, 1.0F, 1.8F);
							player.sendMessage(ChatColor.RED+"[SYSTEM] : ���� ������ ���� �׸��� ã�� �� �����ϴ�!");
							player.sendMessage(ChatColor.YELLOW+"(���� �׸� �ٿ�ε� : "+ChatColor.GOLD+"http://cafe.naver.com/goldbigdragon/56713"+ChatColor.YELLOW+")");
							return;
						}
						s.SP(player, Sound.ITEM_PICKUP, 0.8F, 1.0F);
						Object_UserData u = new Object_UserData();
						u.setTemp(player, "Dungeon");
						player.closeInventory();
						if(Type == 52)
						{
							u.setType(player, "DungeonMain");
							u.setString(player, (byte)0, "ND");//NewDungeon
							player.sendMessage(ChatColor.GREEN+"[����] : ���ο� ���� �̸��� �Է� �� �ּ���!");
						}
						else if(Type == 358)
						{
							u.setType(player, "EnterCard");
							u.setString(player, (byte)0, "NEC");//NewEnterCard
							player.sendMessage(ChatColor.GREEN+"[����] : ���ο� ������ �̸��� �Է� �� �ּ���!");
						}
						else if(Type == 120)
						{
							u.setType(player, "Altar");
							u.setString(player, (byte)0, "NA_Q");//NewAlter_Question
							player.sendMessage(ChatColor.GREEN+"[����] : ���� �� �ִ� ��ġ�� ������ ����ðڽ��ϱ�? (��/�ƴϿ�)");
						}
						return;
					case 50://���� ������
						s.SP(player, Sound.ITEM_PICKUP, 0.8F, 1.0F);
						DungeonListMainGUI(player, page+1,Type);
						return;
					default :
						if(event.getCurrentItem()==null)
							return;
						if(event.getCurrentItem().hasItemMeta()==false)
							return;
			
						if(Type == 52)
						{
							String DungeonName = ChatColor.stripColor(event.getCurrentItem().getItemMeta().getDisplayName());
							if(event.isLeftClick() == true)
							{
								s.SP(player, Sound.ITEM_PICKUP, 0.8F, 1.0F);
								DungeonSetUpGUI(player, DungeonName);
							}
							else if(event.isShiftClick() == true && event.isRightClick() == true)
							{
								s.SP(player, Sound.LAVA_POP, 0.8F, 1.0F);
							  	YamlController YC = new YamlController(GoldBigDragon_RPG.Main.Main.plugin);
								YamlManager DungeonConfig = YC.getNewConfig("Dungeon/DungeonList.yml");
								DungeonConfig.removeKey(DungeonName);
								DungeonConfig.saveConfig();
								File file = new File("plugins/GoldBigDragonRPG/Dungeon/Dungeon/"+DungeonName+"/Monster.yml");
								file.delete();
								file = new File("plugins/GoldBigDragonRPG/Dungeon/Dungeon/"+DungeonName+"/Option.yml");
								file.delete();
								file = new File("plugins/GoldBigDragonRPG/Dungeon/Dungeon/"+DungeonName+"/Reward.yml");
								file.delete();
								file = new File("plugins/GoldBigDragonRPG/Dungeon/Dungeon/"+DungeonName);
								file.delete();
								DungeonListMainGUI(player, page,Type);
							}
							else if(event.isShiftClick()==false&&event.isRightClick())
							{
							  	YamlController YC = new YamlController(GoldBigDragon_RPG.Main.Main.plugin);
								YamlManager DungeonConfig = YC.getNewConfig("Dungeon/Dungeon/"+DungeonName+"/Option.yml");
								new GoldBigDragon_RPG.Dungeon.DungeonCreater().CreateTestSeed(player, DungeonConfig.getInt("Size"), DungeonConfig.getInt("Maze_Level"), DungeonConfig.getString("Type.Name"));
							}
						}
						if(Type == 358)
						{
							String DungeonName = ChatColor.stripColor(event.getCurrentItem().getItemMeta().getDisplayName());
							if(event.isLeftClick() == true&&event.isShiftClick()==false)
							{
								s.SP(player, Sound.ITEM_PICKUP, 0.8F, 1.0F);
								EnterCardSetUpGUI(player, DungeonName);
							}
							else if(event.isShiftClick()&&event.isRightClick())
							{
								s.SP(player, Sound.LAVA_POP, 0.8F, 1.0F);
							  	YamlController YC = new YamlController(GoldBigDragon_RPG.Main.Main.plugin);
								YamlManager DungeonConfig = YC.getNewConfig("Dungeon/EnterCardList.yml");
								DungeonConfig.removeKey(DungeonName);
								DungeonConfig.saveConfig();
								DungeonListMainGUI(player, page,Type);
							}
							else if(event.isShiftClick()&& event.isLeftClick())
							{
								s.SP(player, Sound.ITEM_PICKUP, 0.8F, 1.0F);
							  	YamlController YC = new YamlController(GoldBigDragon_RPG.Main.Main.plugin);
								YamlManager DungeonConfig = YC.getNewConfig("Dungeon/EnterCardList.yml");
								ItemStack Icon = new MaterialData(DungeonConfig.getInt(DungeonName+".ID"), (byte) DungeonConfig.getInt(DungeonName+".DATA")).toItemStack(1);
								ItemMeta Icon_Meta = Icon.getItemMeta();
								Icon_Meta.setDisplayName(ChatColor.RED+""+ChatColor.BOLD+"[���� ������]");
								Calendar Today = Calendar.getInstance();
								String UseableTime = "[���� �ð� ����]";
								if(DungeonConfig.getInt(DungeonName+".Hour")!=-1)
								{
									Today.add(Calendar.MONTH, 1);
									Today.add(Calendar.HOUR, DungeonConfig.getInt(DungeonName+".Hour"));
									Today.add(Calendar.MINUTE, DungeonConfig.getInt(DungeonName+".Min"));
									Today.add(Calendar.SECOND, DungeonConfig.getInt(DungeonName+".Sec"));
									
									UseableTime = Today.get(Calendar.YEAR)+"�� "+Today.get(Calendar.MONTH)+"�� "+Today.get(Calendar.DATE)+"�� "+Today.get(Calendar.HOUR)+"�� "+Today.get(Calendar.MINUTE)+"�� "+Today.get(Calendar.SECOND)+"�� ����";
								}
								Icon_Meta.setLore(Arrays.asList("",ChatColor.RED+DungeonName,"",ChatColor.RED+"�ο� : "+ChatColor.WHITE+DungeonConfig.getInt(DungeonName+".Capacity"),"",ChatColor.WHITE+""+UseableTime));
								Icon.setItemMeta(Icon_Meta);
								player.getInventory().addItem(Icon);
							}
						}
						if(Type == 120)
						{
							String DungeonName = event.getCurrentItem().getItemMeta().getLore().get(event.getCurrentItem().getItemMeta().getLore().size()-1);
							if(event.isLeftClick() == true&&event.isShiftClick()==false)
							{
								s.SP(player, Sound.ITEM_PICKUP, 0.8F, 1.0F);
								AltarSettingGUI(player, DungeonName);
							}
							else if(event.isShiftClick() == true && event.isRightClick() == true)
							{
								s.SP(player, Sound.LAVA_POP, 0.8F, 1.0F);
							  	YamlController YC = new YamlController(GoldBigDragon_RPG.Main.Main.plugin);
								YamlManager DungeonConfig = YC.getNewConfig("Dungeon/AltarList.yml");
								Location loc = new Location(Bukkit.getServer().getWorld(DungeonConfig.getString(DungeonName+".World")), DungeonConfig.getInt(DungeonName+".X"), DungeonConfig.getInt(DungeonName+".Y"), DungeonConfig.getInt(DungeonName+".Z"));
								int radius = DungeonConfig.getInt(DungeonName+".radius");
								Object[] EntitiList = Bukkit.getServer().getWorld(DungeonConfig.getString(DungeonName+".World")).getNearbyEntities(loc, radius, radius, radius).toArray();
								for(int count=0; count<EntitiList.length;count++)
									if(((Entity)EntitiList[count]).getCustomName()!=null)
										if(((Entity)EntitiList[count]).getCustomName().compareTo(DungeonName)==0)
											((Entity)EntitiList[count]).remove();
								DungeonConfig.removeKey(DungeonName);
								DungeonConfig.saveConfig();
								File file = new File("plugins/GoldBigDragonRPG/Dungeon/Altar/"+DungeonName+".yml");
								file.delete();
								DungeonListMainGUI(player, page,Type);
							}
							else if(event.isShiftClick() == true && event.isLeftClick() == true)
							{
								s.SP(player, Sound.ENDERMAN_TELEPORT, 0.8F, 1.0F);
							  	YamlController YC = new YamlController(GoldBigDragon_RPG.Main.Main.plugin);
								YamlManager DungeonConfig = YC.getNewConfig("Dungeon/AltarList.yml");
								Location loc = new Location(Bukkit.getServer().getWorld(DungeonConfig.getString(DungeonName+".World")), DungeonConfig.getInt(DungeonName+".X"), DungeonConfig.getInt(DungeonName+".Y"), DungeonConfig.getInt(DungeonName+".Z"));
								player.teleport(loc);
								s.SP(player, Sound.ENDERMAN_TELEPORT, 0.8F, 1.0F);
							}
						}
						return;
				}
	}
	
	public void DungeonSetUpGUIClick(InventoryClickEvent event)
	{
		GoldBigDragon_RPG.Effect.Sound s = new GoldBigDragon_RPG.Effect.Sound();
		event.setCancelled(true);
		Player player = (Player) event.getWhoClicked();

		Object_UserData u = new Object_UserData();
		String DungeonName = ChatColor.stripColor(event.getInventory().getTitle().split(" : ")[1]);
		
		switch (event.getSlot())
		{
		case 36://���� ���
			s.SP(player, Sound.ITEM_PICKUP, 0.8F, 1.0F);
			DungeonListMainGUI(player, 0, 52);
			return;
		case 44://������
			s.SP(player, Sound.PISTON_RETRACT, 0.8F, 1.8F);
			player.closeInventory();
			return;
		case 11://���� Ÿ��
			{
				if(GoldBigDragon_RPG.Main.ServerOption.DungeonTheme.isEmpty())
				{
					s.SP(player, Sound.ORB_PICKUP, 1.0F, 1.8F);
					player.sendMessage(ChatColor.RED+"[SYSTEM] : ���� �׸��� ã�� �� �����ϴ�!");
					player.sendMessage(ChatColor.YELLOW+"(���� �׸� �ٿ�ε� : "+ChatColor.GOLD+"http://cafe.naver.com/goldbigdragon/56713"+ChatColor.YELLOW+")");
					return;
				}
				s.SP(player, Sound.ITEM_PICKUP, 1.0F, 1.0F);
			  	YamlController YC = new YamlController(GoldBigDragon_RPG.Main.Main.plugin);
				YamlManager DungeonConfig = YC.getNewConfig("Dungeon/Dungeon/"+DungeonName+"/Option.yml");
				String DungeonTheme = DungeonConfig.getString("Type.Name");
				if(GoldBigDragon_RPG.Main.ServerOption.DungeonTheme.contains(DungeonTheme)==false)
					DungeonConfig.set("Type.Name", GoldBigDragon_RPG.Main.ServerOption.DungeonTheme.get(0));
				else
				{
					for(int count = 0; count < GoldBigDragon_RPG.Main.ServerOption.DungeonTheme.size(); count++)
						if(GoldBigDragon_RPG.Main.ServerOption.DungeonTheme.get(count).compareTo(DungeonTheme)==0)
						{
							if(count+1 >= GoldBigDragon_RPG.Main.ServerOption.DungeonTheme.size())
								DungeonConfig.set("Type.Name", GoldBigDragon_RPG.Main.ServerOption.DungeonTheme.get(0));
							else
								DungeonConfig.set("Type.Name", GoldBigDragon_RPG.Main.ServerOption.DungeonTheme.get(count+1));
						}
				}
				DungeonConfig.saveConfig();
				DungeonSetUpGUI(player, DungeonName);
			}
			return;
		case 13://���� ũ��
			s.SP(player, Sound.ITEM_PICKUP, 1.0F, 1.0F);
			u.setTemp(player, "Dungeon");
			u.setType(player, "DungeonMain");
			u.setString(player, (byte)0, "DS");//DungeonSize
			u.setString(player, (byte)1, DungeonName);
			player.sendMessage(ChatColor.GREEN+"[����] : ���� ũ�⸦ �Է� �� �ּ���! (�ּ� 5 �ִ� 50)");
			player.closeInventory();
			return;
		case 15://���� �̷� ����
			s.SP(player, Sound.ITEM_PICKUP, 1.0F, 1.0F);
			u.setTemp(player, "Dungeon");
			u.setType(player, "DungeonMain");
			u.setString(player, (byte)0, "DML");//DungeonMazeLevel
			u.setString(player, (byte)1, DungeonName);
			player.sendMessage(ChatColor.GREEN+"[����] : ���� �̷� ������ �Է� �� �ּ���! (�ּ� 0 �ִ� 10)");
			player.sendMessage(ChatColor.YELLOW+"[����] �̷� ������ �������� �������� ��ĥ���� ����!");
			player.closeInventory();
			return;
		case 20://���� ����
			s.SP(player, Sound.ITEM_PICKUP, 1.0F, 1.0F);
			u.setTemp(player, "Dungeon");
			u.setType(player, "DungeonMain");
			u.setString(player, (byte)0, "DDL");//DungeonDistrictLevel
			u.setString(player, (byte)1, DungeonName);
			player.sendMessage(ChatColor.GREEN+"[����] : ���� ���� ���� ������ �Է� �� �ּ���!");
			player.closeInventory();
			return;
		case 22://��, ����ġ ���� ����
			s.SP(player, Sound.ITEM_PICKUP, 1.0F, 1.0F);
			u.setTemp(player, "Dungeon");
			u.setType(player, "DungeonMain");
			u.setString(player, (byte)0, "DRM");//DungeonRewardMoney
			u.setString(player, (byte)1, DungeonName);
			player.sendMessage(ChatColor.GREEN+"[����] : ���� Ŭ���� ������� �Է� �� �ּ���!");
			player.closeInventory();
			return;
		case 24://���� ����
			s.SP(player, Sound.HORSE_ARMOR, 0.8F, 1.8F);
			DungeonChestReward(player, DungeonName);
			return;
		case 29://����
			s.SP(player, Sound.WOLF_BARK, 0.8F, 1.0F);
			DungeonMonsterGUIMain(player, DungeonName);
			return;
		case 31://����BGM ����
			s.SP(player, Sound.ITEM_PICKUP, 0.8F, 1.0F);
			DungeonMusicSettingGUI(player, 0, DungeonName, false);
			return;
		case 33://����BGM ����
			s.SP(player, Sound.ITEM_PICKUP, 0.8F, 1.0F);
			DungeonMusicSettingGUI(player, 0, DungeonName, true);
			return;
		}
	}

	public void DungeonChestRewardClick(InventoryClickEvent event)
	{
		if(event.getClickedInventory().getName().contains("����"))
			if(event.getSlot()%9==0)
				event.setCancelled(true);
	}
	
	public void DungeonMonsterGUIMainClick(InventoryClickEvent event)
	{
		GoldBigDragon_RPG.Effect.Sound s = new GoldBigDragon_RPG.Effect.Sound();
		event.setCancelled(true);
		Player player = (Player) event.getWhoClicked();

		Object_UserData u = new Object_UserData();
		String DungeonName = ChatColor.stripColor(event.getInventory().getTitle().split(" : ")[1]);

		
		switch (event.getSlot())
		{
		case 45://���� ���
			s.SP(player, Sound.ITEM_PICKUP, 0.8F, 1.0F);
			DungeonSetUpGUI(player, DungeonName);
			return;
		case 53://������
			s.SP(player, Sound.PISTON_RETRACT, 0.8F, 1.8F);
			player.closeInventory();
			return;
		}
		if(event.getSlot()%9==0)
			event.setCancelled(true);
		else
		{
			s.SP(player, Sound.ITEM_PICKUP, 0.8F, 1.0F);
			DungeonMonsterChooseMain(player, DungeonName, event.getSlot());
		}
		return;
	}
	
	public void DungeonMonsterChooseMainClick(InventoryClickEvent event)
	{
		GoldBigDragon_RPG.Effect.Sound s = new GoldBigDragon_RPG.Effect.Sound();
		event.setCancelled(true);
		Player player = (Player) event.getWhoClicked();

		String DungeonName = ChatColor.stripColor(event.getInventory().getTitle().split(" : ")[1]);
		int Slot = Integer.parseInt(ChatColor.stripColor(event.getInventory().getItem(8).getItemMeta().getLore().get(1)));
		String Type = null;
		if(Slot< 9)
		{
			Type="Boss";
			Slot = Slot-1;
		}
		else if(Slot < 18)
		{
			Type="SubBoss";
			Slot = Slot-10;
		}
		else if(Slot < 27)
		{
			Type="High";
			Slot = Slot-19;
		}
		else if(Slot < 36)
		{
			Type="Middle";
			Slot = Slot-28;
		}
		else if(Slot < 45)
		{
			Type="Normal";
			Slot = Slot-37;
		}
		switch (event.getSlot())
		{
		case 0://���� ���
			s.SP(player, Sound.ITEM_PICKUP, 0.8F, 1.0F);
			DungeonMonsterGUIMain(player, DungeonName);
			return;
		case 2://����
			s.SP(player, Sound.ITEM_PICKUP, 0.8F, 1.0F);
		  	YamlController YC = new YamlController(GoldBigDragon_RPG.Main.Main.plugin);
			YamlManager DungeonConfig = YC.getNewConfig("Dungeon/Dungeon/"+DungeonName+"/Monster.yml");
			DungeonConfig.set(Type+"."+Slot+".1", null);
			DungeonConfig.saveConfig();
			DungeonMonsterGUIMain(player, DungeonName);
			return;
		case 4://�Ϲ� ����
			s.SP(player, Sound.ITEM_PICKUP, 0.8F, 1.0F);
			DungeonSelectNormalMonsterChoose(player, DungeonName, Type, Slot);
			return;
		case 6://Ŀ���� ����
			s.SP(player, Sound.ITEM_PICKUP, 0.8F, 1.0F);
			DungeonSelectCustomMonsterChoose(player, DungeonName, Type, Slot, 0);
			return;
		case 8://������
			s.SP(player, Sound.PISTON_RETRACT, 0.8F, 1.8F);
			player.closeInventory();
			return;
		}
	}
	
	public void DungeonSelectNormalMonsterChooseClick(InventoryClickEvent event)
	{
		GoldBigDragon_RPG.Effect.Sound s = new GoldBigDragon_RPG.Effect.Sound();
		event.setCancelled(true);
		Player player = (Player) event.getWhoClicked();

		String DungeonName = ChatColor.stripColor(event.getInventory().getTitle().split(" : ")[1]);
		int Slot = Integer.parseInt(ChatColor.stripColor(event.getInventory().getItem(53).getItemMeta().getLore().get(1)));
		String Type = ChatColor.stripColor(event.getInventory().getItem(45).getItemMeta().getLore().get(1));

		if(event.getCurrentItem()!=null)
			if(event.getCurrentItem().getTypeId()==383)
			{
			  	YamlController YC = new YamlController(GoldBigDragon_RPG.Main.Main.plugin);
				YamlManager DungeonConfig = YC.getNewConfig("Dungeon/Dungeon/"+DungeonName+"/Monster.yml");
				switch(event.getCurrentItem().getData().getData())
				{
				case 50://Creeper
					DungeonConfig.set(Type+"."+Slot, "��ũ����"); break;
				case 51://Skeleton
					DungeonConfig.set(Type+"."+Slot, "�ｺ�̷���"); break;
				case 52://Spider
					DungeonConfig.set(Type+"."+Slot, "��Ź�"); break;
				case 54://Zombie
					DungeonConfig.set(Type+"."+Slot, "������"); break;
				case 55://Slime
					DungeonConfig.set(Type+"."+Slot, "�ｽ����"); break;
				case 56://Ghast
					DungeonConfig.set(Type+"."+Slot, "�ﰡ��Ʈ"); break;
				case 57://Witch
					DungeonConfig.set(Type+"."+Slot, "�������Ǳ׸�"); break;
				case 58://Enderman
					DungeonConfig.set(Type+"."+Slot, "�￣����"); break;
				case 59://CaveSpider
					DungeonConfig.set(Type+"."+Slot, "�ﵿ���Ź�"); break;
				case 61://Blaze
					DungeonConfig.set(Type+"."+Slot, "�������"); break;
				case 62://MagmaCube
					DungeonConfig.set(Type+"."+Slot, "�︶�׸�ť��"); break;
				case 65://Bat
					DungeonConfig.set(Type+"."+Slot, "�����"); break;
				case 66://Witch
					DungeonConfig.set(Type+"."+Slot, "�︶��"); break;
				case 68://Guardian
					DungeonConfig.set(Type+"."+Slot, "�ﰡ���"); break;

				case 90://Pig
					DungeonConfig.set(Type+"."+Slot, "�����"); break;
				case 91://Sheep
					DungeonConfig.set(Type+"."+Slot, "���"); break;
				case 92://Cow
					DungeonConfig.set(Type+"."+Slot, "���"); break;
				case 93://Chicken
					DungeonConfig.set(Type+"."+Slot, "���"); break;
				case 94://Squid
					DungeonConfig.set(Type+"."+Slot, "���¡��"); break;
				case 95://Wolf
					DungeonConfig.set(Type+"."+Slot, "�����"); break;
				case 96://MushroomCow
					DungeonConfig.set(Type+"."+Slot, "�������"); break;
				case 98://Ocelot
					DungeonConfig.set(Type+"."+Slot, "�������"); break;
				case 100://Horse
					DungeonConfig.set(Type+"."+Slot, "�︻"); break;
				case 101://Rabbit
					DungeonConfig.set(Type+"."+Slot, "���䳢"); break;
				case 120://Villager
					DungeonConfig.set(Type+"."+Slot, "���ֹ�"); break;
				}
				DungeonConfig.saveConfig();
				s.SP(player, Sound.ITEM_PICKUP, 1.0F, 1.8F);
				DungeonMonsterGUIMain(player, DungeonName);
				return;
			}
		switch (event.getSlot())
		{
		case 45://���� ���
			s.SP(player, Sound.ITEM_PICKUP, 0.8F, 1.0F);
			DungeonMonsterChooseMain(player, DungeonName, Slot);
			return;
		case 53://������
			s.SP(player, Sound.PISTON_RETRACT, 0.8F, 1.8F);
			player.closeInventory();
			return;
		}
	}
	
	public void DungeonSelectCustomMonsterChooseClick(InventoryClickEvent event)
	{
		GoldBigDragon_RPG.Effect.Sound s = new GoldBigDragon_RPG.Effect.Sound();
		event.setCancelled(true);
		Player player = (Player) event.getWhoClicked();

		int page =  Integer.parseInt(event.getInventory().getTitle().split(" : ")[1])-1;
		String DungeonName = ChatColor.stripColor(event.getInventory().getItem(53).getItemMeta().getLore().get(2));
		int Slot = Integer.parseInt(ChatColor.stripColor(event.getInventory().getItem(53).getItemMeta().getLore().get(1)));
		String Type = ChatColor.stripColor(event.getInventory().getItem(45).getItemMeta().getLore().get(1));

		if(event.getCurrentItem()!=null)
			if(event.getCurrentItem().getTypeId()==383)
			{
			  	YamlController YC = new YamlController(GoldBigDragon_RPG.Main.Main.plugin);
				YamlManager DungeonConfig = YC.getNewConfig("Dungeon/Dungeon/"+DungeonName+"/Monster.yml");
				DungeonConfig.set(Type+"."+Slot, ChatColor.stripColor(event.getCurrentItem().getItemMeta().getDisplayName()));
				DungeonConfig.saveConfig();
				s.SP(player, Sound.ITEM_PICKUP, 1.0F, 1.8F);
				DungeonMonsterGUIMain(player, DungeonName);
				return;
			}
		switch (event.getSlot())
		{
		case 48://���� ������
			s.SP(player, Sound.ITEM_PICKUP, 0.8F, 1.0F);
			DungeonSelectCustomMonsterChoose(player, DungeonName, Type, Slot, page-1);
			return;
		case 50://���� ������
			s.SP(player, Sound.ITEM_PICKUP, 0.8F, 1.0F);
			DungeonSelectCustomMonsterChoose(player, DungeonName, Type, Slot, page+1);
			return;
		case 45://���� ���
			s.SP(player, Sound.ITEM_PICKUP, 0.8F, 1.0F);
			DungeonMonsterChooseMain(player, DungeonName, Slot);
			return;
		case 53://������
			s.SP(player, Sound.PISTON_RETRACT, 0.8F, 1.8F);
			player.closeInventory();
			return;
		}
	}
	
	public void DungeonMusicSettingGUIClick(InventoryClickEvent event)
	{
		GoldBigDragon_RPG.Effect.Sound s = new GoldBigDragon_RPG.Effect.Sound();
		event.setCancelled(true);
		Player player = (Player) event.getWhoClicked();
		
		String DungeonName = ChatColor.stripColor(event.getInventory().getItem(53).getItemMeta().getLore().get(1));

		int page =  Integer.parseInt(event.getInventory().getTitle().split(" : ")[1])-1;
		boolean isBoss = Boolean.parseBoolean(ChatColor.stripColor(event.getInventory().getItem(45).getItemMeta().getLore().get(1)));
		
		switch (event.getSlot())
		{
		case 45://���� ���
			s.SP(player, Sound.ITEM_PICKUP, 0.8F, 1.0F);
			DungeonSetUpGUI(player, DungeonName);
			return;
		case 53://������
			s.SP(player, Sound.PISTON_RETRACT, 0.8F, 1.8F);
			player.closeInventory();
			return;
		case 48://���� ������
			s.SP(player, Sound.ITEM_PICKUP, 0.8F, 1.0F);
			DungeonMusicSettingGUI(player, page-1,DungeonName,isBoss);
			return;
		case 50://���� ������
			s.SP(player, Sound.ITEM_PICKUP, 0.8F, 1.0F);
			DungeonMusicSettingGUI(player, page+1,DungeonName,isBoss);
			return;
		default :
			if(event.isLeftClick())
			{
				if(event.getCurrentItem() != null && event.getCurrentItem().hasItemMeta())
				{
					s.SP(player, Sound.ITEM_PICKUP, 0.8F, 1.0F);
				  	YamlController YC = new YamlController(GoldBigDragon_RPG.Main.Main.plugin);
					YamlManager DungeonConfig = YC.getNewConfig("Dungeon/Dungeon/"+DungeonName+"/Option.yml");
					if(isBoss)
						DungeonConfig.set("BGM.BOSS", Integer.parseInt(ChatColor.stripColor(event.getCurrentItem().getItemMeta().getDisplayName())));
					else
						DungeonConfig.set("BGM.Normal", Integer.parseInt(ChatColor.stripColor(event.getCurrentItem().getItemMeta().getDisplayName())));
					DungeonConfig.saveConfig();
					DungeonSetUpGUI(player, DungeonName);
				}
			}
			return;
		}
	}
	//DungeonGUI Click//

	
	//EnterCardGUI Click//
	public void EnterCardSetUpGUIClick(InventoryClickEvent event)
	{
		GoldBigDragon_RPG.Effect.Sound s = new GoldBigDragon_RPG.Effect.Sound();
		event.setCancelled(true);
		Player player = (Player) event.getWhoClicked();
		
		String EnterCardName = ChatColor.stripColor(event.getInventory().getItem(8).getItemMeta().getLore().get(1));

		Object_UserData u = new Object_UserData();
		switch (event.getSlot())
		{
		case 0://���� ���
			s.SP(player, Sound.ITEM_PICKUP, 0.8F, 1.0F);
			DungeonListMainGUI(player, 0, 358);
			return;
		case 2://���� ����
			{
			  	YamlController YC = new YamlController(GoldBigDragon_RPG.Main.Main.plugin);
				YamlManager DungeonConfig = YC.getNewConfig("Dungeon/DungeonList.yml");
				if(DungeonConfig.getKeys().size()==0)
				{
					s.SP(player, Sound.ORB_PICKUP, 1.0F, 1.8F);
					player.sendMessage(ChatColor.RED+"[����] : ������ ������ �����ϴ�! ������ ���� ����� ������!");
				}
				else
				{
					s.SP(player, Sound.ITEM_PICKUP, 1.0F, 1.8F);
					EnterCardDungeonSettingGUI(player, 0, EnterCardName);
				}
			}
			return;
		case 3://������ ���� ����
			s.SP(player, Sound.ITEM_PICKUP, 1.0F, 1.0F);
			u.setTemp(player, "Dungeon");
			player.closeInventory();
			u.setType(player, "EnterCard");
			u.setString(player, (byte)0, "ECID");//EnterCardID
			u.setString(player, (byte)1, EnterCardName);
			player.sendMessage(ChatColor.GREEN+"[������] : ������ ������ Ÿ�� ID�� �Է� �� �ּ���.");
			return;
		case 4://������ ���� �ʱ�ȭ
			{
				s.SP(player, Sound.IRONGOLEM_THROW, 1.0F, 1.8F);
			  	YamlController YC = new YamlController(GoldBigDragon_RPG.Main.Main.plugin);
				YamlManager DungeonConfig = YC.getNewConfig("Dungeon/EnterCardList.yml");
				DungeonConfig.set(EnterCardName+".ID",358);
				DungeonConfig.set(EnterCardName+".DATA",0);
				DungeonConfig.saveConfig();
				EnterCardSetUpGUI(player, EnterCardName);
			}
			return;
		case 5://���� �ο� ����
			s.SP(player, Sound.ITEM_PICKUP, 1.0F, 1.0F);
			u.setTemp(player, "Dungeon");
			player.closeInventory();
			u.setType(player, "EnterCard");
			u.setString(player, (byte)0, "ECC");//EnterCardCapacity
			u.setString(player, (byte)1, EnterCardName);
			player.sendMessage(ChatColor.GREEN+"[������] : �ʿ� ���� �ο� ���� �Է� �� �ּ���.");
			return;
		case 6://��ȿ�ð� ����
			s.SP(player, Sound.ITEM_PICKUP, 1.0F, 1.0F);
			u.setTemp(player, "Dungeon");
			player.closeInventory();
			u.setType(player, "EnterCard");
			u.setString(player, (byte)0, "ECUH");//EnterCardUseableHour
			u.setString(player, (byte)1, EnterCardName);
			player.sendMessage(ChatColor.GREEN+"[������] : ��ȿ �ð��� �Է� �� �ּ���. (�ִ� 24�ð�, -1�Է½� ������)");
			return;
		case 8://������
			s.SP(player, Sound.PISTON_RETRACT, 0.8F, 1.8F);
			player.closeInventory();
			return;
		}
	}

	public void EnterCardDungeonSettingGUIClick(InventoryClickEvent event)
	{
		GoldBigDragon_RPG.Effect.Sound s = new GoldBigDragon_RPG.Effect.Sound();
		event.setCancelled(true);
		Player player = (Player) event.getWhoClicked();
		
		String EnterCardName = ChatColor.stripColor(event.getInventory().getItem(53).getItemMeta().getLore().get(1));

		if(event.getCurrentItem()!=null)
			if(event.getCurrentItem().getType()!=Material.AIR)
			{
				switch (event.getSlot())
				{
				case 45://���� ���
					s.SP(player, Sound.ITEM_PICKUP, 0.8F, 1.0F);
					EnterCardSetUpGUI(player, EnterCardName);
					return;
				case 53://������
					s.SP(player, Sound.PISTON_RETRACT, 0.8F, 1.8F);
					player.closeInventory();
					return;
				default:
					s.SP(player, Sound.ITEM_PICKUP, 0.8F, 1.0F);
				  	YamlController YC = new YamlController(GoldBigDragon_RPG.Main.Main.plugin);
					YamlManager DungeonConfig = YC.getNewConfig("Dungeon/EnterCardList.yml");
					DungeonConfig.set(EnterCardName+".Dungeon", ChatColor.stripColor(event.getCurrentItem().getItemMeta().getDisplayName()));
					DungeonConfig.saveConfig();
					EnterCardSetUpGUI(player, EnterCardName);
					return;
				}
			}
	}
	//EnterCardGUI Click//

	
	//AltarGUI Click//
	public void AltarShapeListGUIClick(InventoryClickEvent event)
	{
		GoldBigDragon_RPG.Effect.Sound s = new GoldBigDragon_RPG.Effect.Sound();
		event.setCancelled(true);
		Player player = (Player) event.getWhoClicked();
		if(event.getCurrentItem()!=null)
			if(event.getCurrentItem().getTypeId()!=0)
			{
				switch (event.getSlot())
				{
				case 45://���� ���
					s.SP(player, Sound.ITEM_PICKUP, 0.8F, 1.0F);
					DungeonListMainGUI(player, 0, 120);
					return;
				case 53://������
					s.SP(player, Sound.PISTON_RETRACT, 0.8F, 1.8F);
					player.closeInventory();
					return;
				default:
					player.closeInventory();
					if(ServerTickMain.ServerTask.compareTo("null")!=0)
					{
						s.SP(player, Sound.ORB_PICKUP, 1.0F, 1.8F);
						player.sendMessage(ChatColor.RED+"[Server] : ���� ������ "+ChatColor.YELLOW+ServerTickMain.ServerTask+ChatColor.RED+" �۾� ���Դϴ�.");
						return;
					}
					s.SP(player, Sound.ITEM_PICKUP, 0.8F, 1.0F);
				  	YamlController YC = new YamlController(GoldBigDragon_RPG.Main.Main.plugin);
					YamlManager AltarList = YC.getNewConfig("Dungeon/AltarList.yml");
					String Code = ChatColor.BLACK+""+ChatColor.BOLD;
					Code = Code+ChatColor.WHITE+"[����]";
					String Salt = Code;
					int ID = 1;
					int DATA = 0;
					String Type = null;
					int radius = 5;
					switch(event.getSlot())
					{
					case 0:
						Type = "MossyAltar";
						ID = 48;
						radius = 3;
						break;
					case 1:
						Type = "GoldBigDragon";
						ID = 41;
						radius = 20;
						break;
					case 2:
						Type = "StoneHenge";
						ID = 1;
						radius = 8;
						break;
					case 3:
						Type = "AnatomicalBoard";
						ID = 1;
						DATA = 5;
						radius = 3;
						break;
					}
					for(;;)
					{
						for(int count=0;count < 6; count++)
							Salt = Salt+getRandomCode();
						if(AltarList.contains(Salt)==false)
							break;
						Salt = Code;
					}
					AltarList.set(Salt+".Name", "��� ������ ����");
					AltarList.set(Salt+".Type", Type);
					AltarList.set(Salt+".radius", radius);
					AltarList.set(Salt+".ID", ID);
					AltarList.set(Salt+".DATA", DATA);
					AltarList.set(Salt+".World", player.getLocation().getWorld().getName());
					AltarList.set(Salt+".X", (int)player.getLocation().getX());
					AltarList.set(Salt+".Y", (int)player.getLocation().getY());
					AltarList.set(Salt+".Z", (int)player.getLocation().getZ());
					AltarList.saveConfig();
					AltarList = YC.getNewConfig("Dungeon/Altar/"+Salt+".yml");
					AltarList.set("EnterCard.1", null);
					AltarList.saveConfig();
					new GoldBigDragon_RPG.Structure.StructureMain().CreateSturcture(player, Salt, (short) (101+event.getSlot()), 4);
					return;
				}
			}
	}

	public void AltarSettingGUIClick(InventoryClickEvent event)
	{
		String AltarName = event.getInventory().getItem(8).getItemMeta().getLore().get(1);
		GoldBigDragon_RPG.Effect.Sound s = new GoldBigDragon_RPG.Effect.Sound();
		event.setCancelled(true);
		Player player = (Player) event.getWhoClicked();
		if(event.getCurrentItem()!=null)
			if(event.getCurrentItem().getTypeId()!=0)
			{
				switch (event.getSlot())
				{
				case 0://���� ���
					s.SP(player, Sound.ITEM_PICKUP, 0.8F, 1.0F);
					DungeonListMainGUI(player, 0, 120);
					return;
				case 2://�̸� ����
					{
						Object_UserData u = new Object_UserData();
						s.SP(player, Sound.ITEM_PICKUP, 1.0F, 1.0F);
						u.setTemp(player, "Dungeon");
						player.closeInventory();
						u.setType(player, "Altar");
						u.setString(player, (byte)0, "EAN");//EditAltarName
						u.setString(player, (byte)1, AltarName);
						player.sendMessage(ChatColor.GREEN+"[����] : ���� �̸��� �Է� �� �ּ���.");
					}
					return;
				case 4://�Ϲ� ���� ����
					s.SP(player, Sound.ITEM_PICKUP, 1.0F, 1.0F);
					AltarDungeonSettingGUI(player, 0, AltarName);
					return;
				case 6://������ ����
					s.SP(player, Sound.ITEM_PICKUP, 1.0F, 1.0F);
					AltarEnterCardSettingGUI(player, 0, AltarName.substring(2, AltarName.length()));
					return;
				case 8://������
					s.SP(player, Sound.PISTON_RETRACT, 0.8F, 1.8F);
					player.closeInventory();
					return;
				}
			}
	}
	
	public void AltarUseGUIClick(InventoryClickEvent event)
	{
		if(event.getSlot()!=4)
			if(event.getCurrentItem()!=null)
				if(event.getCurrentItem().getTypeId()!=0)
					if(ChatColor.stripColor(event.getClickedInventory().getName()).compareTo("���ܿ� ������ ��ġ�� �������� �̵��մϴ�")==0)
							event.setCancelled(true);
	}
	
	public void AltarDungeonSettingGUIClick(InventoryClickEvent event)
	{
		GoldBigDragon_RPG.Effect.Sound s = new GoldBigDragon_RPG.Effect.Sound();
		event.setCancelled(true);
		Player player = (Player) event.getWhoClicked();
		
		String AltarName = event.getInventory().getItem(53).getItemMeta().getLore().get(1).substring(2, event.getInventory().getItem(53).getItemMeta().getLore().get(1).length());

		if(event.getCurrentItem()!=null)
			if(event.getCurrentItem().getType()!=Material.AIR)
			{
				switch (event.getSlot())
				{
				case 45://���� ���
					s.SP(player, Sound.ITEM_PICKUP, 0.8F, 1.0F);
					AltarSettingGUI(player, AltarName);
					return;
				case 53://������
					s.SP(player, Sound.PISTON_RETRACT, 0.8F, 1.8F);
					player.closeInventory();
					return;
				default:
					s.SP(player, Sound.ITEM_PICKUP, 0.8F, 1.0F);
				  	YamlController YC = new YamlController(GoldBigDragon_RPG.Main.Main.plugin);
					YamlManager DungeonConfig = YC.getNewConfig("Dungeon/Altar/"+AltarName+".yml");
					DungeonConfig.set("NormalDungeon", ChatColor.stripColor(event.getCurrentItem().getItemMeta().getDisplayName()));
					DungeonConfig.saveConfig();
					AltarSettingGUI(player, AltarName);
					return;
				}
			}
	}
	
	public void AltarEnterCardSettingGUIClick(InventoryClickEvent event)
	{
		GoldBigDragon_RPG.Effect.Sound s = new GoldBigDragon_RPG.Effect.Sound();
		event.setCancelled(true);
		Player player = (Player) event.getWhoClicked();

		String AltarName = event.getInventory().getItem(53).getItemMeta().getLore().get(1);

		int page =  Integer.parseInt(event.getInventory().getTitle().split(" : ")[1])-1;
		if(event.getCurrentItem()!=null)
			if(event.getCurrentItem().getType()!=Material.AIR)
			{
				switch (event.getSlot())
				{
				case 45://���� ���
					s.SP(player, Sound.ITEM_PICKUP, 0.8F, 1.0F);
					AltarSettingGUI(player, AltarName);
					return;
				case 48://���� ������
					s.SP(player, Sound.ITEM_PICKUP, 0.8F, 1.0F);
					AltarEnterCardSettingGUI(player, page-1, AltarName);
					return;
				case 49://������ ���
					s.SP(player, Sound.ITEM_PICKUP, 0.8F, 1.0F);
					AltarEnterCardListGUI(player, page, AltarName);
					return;
				case 50://���� ������
					s.SP(player, Sound.ITEM_PICKUP, 0.8F, 1.0F);
					AltarEnterCardSettingGUI(player, page+1, AltarName);
					return;
				case 53://������
					s.SP(player, Sound.PISTON_RETRACT, 0.8F, 1.8F);
					player.closeInventory();
					return;
				default:
					if(event.isShiftClick()&&event.isRightClick())
					{
						s.SP(player, Sound.LAVA_POP, 0.8F, 1.0F);
					  	YamlController YC = new YamlController(GoldBigDragon_RPG.Main.Main.plugin);
						YamlManager DungeonConfig = YC.getNewConfig("Dungeon/Altar/"+AltarName+".yml");
						DungeonConfig.removeKey("EnterCard."+ChatColor.stripColor(event.getCurrentItem().getItemMeta().getDisplayName()));
						DungeonConfig.saveConfig();
						AltarEnterCardSettingGUI(player, page, AltarName);
						return;
					}
				}
			}
	}
	
	public void AltarEnterCardListGUIClick(InventoryClickEvent event)
	{
		GoldBigDragon_RPG.Effect.Sound s = new GoldBigDragon_RPG.Effect.Sound();
		event.setCancelled(true);
		Player player = (Player) event.getWhoClicked();

		String AltarName = event.getInventory().getItem(53).getItemMeta().getLore().get(1);

		int page =  Integer.parseInt(event.getInventory().getTitle().split(" : ")[1])-1;
		if(event.getCurrentItem()!=null)
			if(event.getCurrentItem().getType()!=Material.AIR)
			{
				switch (event.getSlot())
				{
				case 45://���� ���
					s.SP(player, Sound.ITEM_PICKUP, 0.8F, 1.0F);
					AltarEnterCardSettingGUI(player, 0, AltarName);
					return;
				case 48://���� ������
					s.SP(player, Sound.ITEM_PICKUP, 0.8F, 1.0F);
					AltarEnterCardListGUI(player, page-1, AltarName);
					return;
				case 50://���� ������
					s.SP(player, Sound.ITEM_PICKUP, 0.8F, 1.0F);
					AltarEnterCardListGUI(player, page+1, AltarName);
					return;
				case 53://������
					s.SP(player, Sound.PISTON_RETRACT, 0.8F, 1.8F);
					player.closeInventory();
					return;
				default:
					s.SP(player, Sound.ITEM_PICKUP, 0.8F, 1.0F);
				  	YamlController YC = new YamlController(GoldBigDragon_RPG.Main.Main.plugin);
					YamlManager DungeonConfig = YC.getNewConfig("Dungeon/Altar/"+AltarName+".yml");
					DungeonConfig.set("EnterCard."+ChatColor.stripColor(event.getCurrentItem().getItemMeta().getDisplayName())+".1",null);
					DungeonConfig.saveConfig();
					AltarEnterCardSettingGUI(player, page, AltarName);
					return;
				}
			}
	}
	
	public String getRandomCode()
	{
		int randomNum = new GoldBigDragon_RPG.Util.Number().RandomNum(0, 15);
		switch(randomNum)
		{
			case 0:
				return ChatColor.BLACK+"";
			case 1:
				return ChatColor.DARK_BLUE+"";
			case 2:
				return ChatColor.DARK_GREEN+"";
			case 3:
				return ChatColor.DARK_AQUA+"";
			case 4:
				return ChatColor.DARK_RED+"";
			case 5:
				return ChatColor.DARK_PURPLE+"";
			case 6:
				return ChatColor.GOLD+"";
			case 7:
				return ChatColor.GRAY+"";
			case 8:
				return ChatColor.DARK_GRAY+"";
			case 9:
				return ChatColor.BLUE+"";
			case 10:
				return ChatColor.GREEN+"";
			case 11:
				return ChatColor.AQUA+"";
			case 12:
				return ChatColor.RED+"";
			case 13:
				return ChatColor.LIGHT_PURPLE+"";
			case 14:
				return ChatColor.YELLOW+"";
			case 15:
				return ChatColor.WHITE+"";
		}
		return ChatColor.BLACK+"";
	}
	//AltarGUI Click//
	
	public void DungeonEXITClick(InventoryClickEvent event)
	{
		GoldBigDragon_RPG.Effect.Sound s = new GoldBigDragon_RPG.Effect.Sound();
		event.setCancelled(true);
		Player player = (Player) event.getWhoClicked();

		if(event.getCurrentItem()!=null)
			if(event.getCurrentItem().getType()!=Material.AIR)
			{
				switch (event.getSlot())
				{
				case 3://���� �ܷ�
					s.SP(player, Sound.PISTON_RETRACT, 0.8F, 1.8F);
					player.closeInventory();
					return;
				case 5://���� ����
					new GoldBigDragon_RPG.Dungeon.DungeonWork().EraseAllDungeonKey(player, true);
					s.SP(player, Sound.PISTON_RETRACT, 0.8F, 1.8F);
					player.closeInventory();
				  	YamlController YC = new YamlController(GoldBigDragon_RPG.Main.Main.plugin);
					String DungeonName = GoldBigDragon_RPG.Main.ServerOption.PlayerList.get(player.getUniqueId().toString()).getDungeon_Enter();
					long UTC = GoldBigDragon_RPG.Main.ServerOption.PlayerList.get(player.getUniqueId().toString()).getDungeon_UTC();
					YamlManager PlayerConfig = YC.getNewConfig("Dungeon/Dungeon/"+DungeonName+"/Entered/"+UTC+".yml");
					if(PlayerConfig.contains("EnteredAlter"))
					{
						DungeonName = PlayerConfig.getString("EnteredAlter");
						PlayerConfig = YC.getNewConfig("Dungeon/AltarList.yml");
						if(PlayerConfig.contains(DungeonName))
						{
							Location loc = new Location(Bukkit.getServer().getWorld(PlayerConfig.getString(DungeonName+".World")), PlayerConfig.getLong(DungeonName+".X"), PlayerConfig.getLong(DungeonName+".Y")+1, PlayerConfig.getLong(DungeonName+".Z"));
							player.teleport(loc);
							return;
						}
					}
					new GoldBigDragon_RPG.Util.PlayerUtil().teleportToCurrentArea(player, true);
					return;
				}
			}
	}
	
	//DungeonGUI Close//
	public void DungeonChestRewardClose(InventoryCloseEvent event)
	{
		String DungeonName = ChatColor.stripColor(event.getInventory().getTitle().split(" : ")[1]);

	  	YamlController YC = new YamlController(GoldBigDragon_RPG.Main.Main.plugin);
		YamlManager DungeonConfig = YC.getNewConfig("Dungeon/Dungeon/"+DungeonName+"/Reward.yml");
		
		for(int count = 0; count < 8; count++)
		{
			if(event.getInventory().getItem(count+1)!=null)
				DungeonConfig.set("100."+count, event.getInventory().getItem(count+1));
			else
				DungeonConfig.set("100."+count+".1", null);
			if(event.getInventory().getItem(count+10)!=null)
				DungeonConfig.set("90."+count, event.getInventory().getItem(count+10));	
			else
				DungeonConfig.set("90."+count+".1", null);
			if(event.getInventory().getItem(count+19)!=null)
				DungeonConfig.set("50."+count, event.getInventory().getItem(count+19));	
			else
				DungeonConfig.set("50."+count+".1", null);
			if(event.getInventory().getItem(count+28)!=null)
				DungeonConfig.set("10."+count, event.getInventory().getItem(count+28));
			else
				DungeonConfig.set("10."+count+".1", null);
			if(event.getInventory().getItem(count+37)!=null)
				DungeonConfig.set("1."+count, event.getInventory().getItem(count+37));
			else
				DungeonConfig.set("1."+count+".1", null);		
			if(event.getInventory().getItem(count+46)!=null)
				DungeonConfig.set("0."+count, event.getInventory().getItem(count+46));
			else
				DungeonConfig.set("0."+count+".1", null);
		}
		DungeonConfig.saveConfig();

		new GoldBigDragon_RPG.Effect.Sound().SP((Player) event.getPlayer(), Sound.ITEM_PICKUP, 1.0F, 1.8F);
		event.getPlayer().sendMessage(ChatColor.GREEN+"[����] : ���� ���� �Ϸ�!");
	}
	
	public void AltarUSEGuiClose(InventoryCloseEvent event)
	{
		ItemStack item = event.getInventory().getItem(4);
		if(item!=null)
		{
			String AltarName = event.getInventory().getItem(0).getItemMeta().getDisplayName();
			Player player = (Player) event.getPlayer();
		  	YamlController YC = new YamlController(GoldBigDragon_RPG.Main.Main.plugin);
			YamlManager AltarConfig = YC.getNewConfig("Dungeon/Altar/"+AltarName+".yml");
			event.getInventory().setItem(4, null);
			GoldBigDragon_RPG.Effect.Sound s = new GoldBigDragon_RPG.Effect.Sound();
			int LvDistrict = -1;
			int RealLvDistrict = -1;
			if(GoldBigDragon_RPG.Main.ServerOption.PlayerList.get(player.getUniqueId().toString()).getDungeon_Enter() != null)
			{
				if(new GoldBigDragon_RPG.Util.PlayerUtil().giveItem(player, item)==false)
					new GoldBigDragon_RPG.Event.ItemDrop().CustomItemDrop(player.getLocation(), item);
				s.SP(player, Sound.IRONGOLEM_WALK, 1.0F, 1.0F);
				player.sendMessage(ChatColor.WHITE+"(�̹� ������ ����� ���� �ִ�...)");
				return;
			}
			if(item.hasItemMeta())
			{
				if(item.getItemMeta().hasDisplayName())
				{
					if(item.getItemMeta().getDisplayName().compareTo(ChatColor.RED+""+ChatColor.BOLD+"[���� ������]")==0)
					{
						if(AltarConfig.contains("EnterCard."+ChatColor.stripColor(item.getItemMeta().getLore().get(1))))
						{
							int capacity = Integer.parseInt(ChatColor.stripColor(item.getItemMeta().getLore().get(3)).split(" : ")[1]);
							String time = ChatColor.stripColor(item.getItemMeta().getLore().get(item.getItemMeta().getLore().size()-1));
							boolean canUse = false;
							if(time.compareTo("[���� �ð� ����]")==0)
								canUse = true;
							else
							{
								int year = Integer.parseInt(time.split(" ")[0].substring(0, time.split(" ")[0].length()-1));
								int month = Integer.parseInt(time.split(" ")[1].substring(0, time.split(" ")[1].length()-1));
								int day = Integer.parseInt(time.split(" ")[2].substring(0, time.split(" ")[2].length()-1));
								int hour = Integer.parseInt(time.split(" ")[3].substring(0, time.split(" ")[3].length()-1));
								int min = Integer.parseInt(time.split(" ")[4].substring(0, time.split(" ")[4].length()-1));
								int sec = Integer.parseInt(time.split(" ")[5].substring(0, time.split(" ")[5].length()-1));

								Calendar Today = Calendar.getInstance();
								Today.add(Calendar.MONTH, 1);
								
								if(year > Today.get(Calendar.YEAR))
									canUse = true;
								else if(year == Today.get(Calendar.YEAR))
									if(month > Today.get(Calendar.MONTH))
										canUse = true;
									else if(month == Today.get(Calendar.MONTH))
										if(day > Today.get(Calendar.DATE))
											canUse = true;
										else if(day == Today.get(Calendar.DATE))
											if(hour > Today.get(Calendar.HOUR))
												canUse = true;
											else if(hour == Today.get(Calendar.HOUR))
												if(min > Today.get(Calendar.MINUTE))
													canUse = true;
												else if(min == Today.get(Calendar.MINUTE))
													if(sec > Today.get(Calendar.SECOND))
														canUse = true;
							}
							if(canUse)
							{
								YamlManager EnterCardConfig = YC.getNewConfig("Dungeon/EnterCardList.yml");
								LvDistrict = YC.getNewConfig("Dungeon/Dungeon/"+EnterCardConfig.getString(ChatColor.stripColor(item.getItemMeta().getLore().get(1))+".Dungeon")+"/Option.yml").getInt("District.Level");
								RealLvDistrict = YC.getNewConfig("Dungeon/Dungeon/"+EnterCardConfig.getString(ChatColor.stripColor(item.getItemMeta().getLore().get(1))+".Dungeon")+"/Option.yml").getInt("District.RealLevel");
								if(EnterCardConfig.contains(ChatColor.stripColor(item.getItemMeta().getLore().get(1))))
								{
									PartyEnterDungeon(player, item, AltarName, capacity, EnterCardConfig.getString(ChatColor.stripColor(item.getItemMeta().getLore().get(1))+".Dungeon"), LvDistrict, RealLvDistrict);
								}
								else
								{
									if(new GoldBigDragon_RPG.Util.PlayerUtil().giveItem(player, item)==false)
										new GoldBigDragon_RPG.Event.ItemDrop().CustomItemDrop(player.getLocation(), item);
									s.SP(player, Sound.IRONGOLEM_WALK, 1.0F, 1.0F);
									player.sendMessage(ChatColor.WHITE+"(�� ������ ������ ��ĥ �� ���� �� �ϴ�...)");
									return;
								}
							}
							else
							{
								if(new GoldBigDragon_RPG.Util.PlayerUtil().giveItem(player, item)==false)
									new GoldBigDragon_RPG.Event.ItemDrop().CustomItemDrop(player.getLocation(), item);
								s.SP(player, Sound.IRONGOLEM_WALK, 1.0F, 1.0F);
								player.sendMessage(ChatColor.WHITE+"(���� �������� ��ȿ�ð��� ������...)");
								return;
							}
						}
						else
						{
							if(new GoldBigDragon_RPG.Util.PlayerUtil().giveItem(player, item)==false)
								new GoldBigDragon_RPG.Event.ItemDrop().CustomItemDrop(player.getLocation(), item);
							s.SP(player, Sound.IRONGOLEM_WALK, 1.0F, 1.0F);
							player.sendMessage(ChatColor.WHITE+"(�� ������ ������ ��ĥ �� ���� �� �ϴ�...)");
							return;
						}
					}
					else
					{
						if(AltarConfig.getString("NormalDungeon")!=null)
						{
							YamlManager DungeonConfig = YC.getNewConfig("Dungeon/DungeonList.yml");
							if(DungeonConfig.contains(AltarConfig.getString("NormalDungeon")))
								PartyEnterDungeon(player, item, AltarName, -1, AltarConfig.getString("NormalDungeon"), LvDistrict, RealLvDistrict);
							else
							{
								AltarConfig.set("NormalDungeon", null);
								AltarConfig.saveConfig();
								if(new GoldBigDragon_RPG.Util.PlayerUtil().giveItem(player, item)==false)
									new GoldBigDragon_RPG.Event.ItemDrop().CustomItemDrop(player.getLocation(), item);
								s.SP(player, Sound.IRONGOLEM_WALK, 1.0F, 1.0F);
								player.sendMessage(ChatColor.WHITE+"(�� ������ ������ ��ĥ �� ���� �� �ϴ�...)");
								return;
							}
						}
						else
						{
							if(new GoldBigDragon_RPG.Util.PlayerUtil().giveItem(player, item)==false)
								new GoldBigDragon_RPG.Event.ItemDrop().CustomItemDrop(player.getLocation(), item);
							s.SP(player, Sound.IRONGOLEM_WALK, 1.0F, 1.0F);
							player.sendMessage(ChatColor.WHITE+"(�� ������ ������ ��ĥ �� ���� �� �ϴ�...)");
							return;
						}
					}
				}
				return;
			}
			if(AltarConfig.getString("NormalDungeon")!=null)
			{
				YamlManager DungeonConfig = YC.getNewConfig("Dungeon/DungeonList.yml");
				if(DungeonConfig.contains(AltarConfig.getString("NormalDungeon")))
					PartyEnterDungeon(player, item, AltarName, -1, AltarConfig.getString("NormalDungeon"), LvDistrict, RealLvDistrict);
				else
				{
					AltarConfig.set("NormalDungeon", null);
					AltarConfig.saveConfig();
					if(new GoldBigDragon_RPG.Util.PlayerUtil().giveItem(player, item)==false)
						new GoldBigDragon_RPG.Event.ItemDrop().CustomItemDrop(player.getLocation(), item);
					s.SP(player, Sound.IRONGOLEM_WALK, 1.0F, 1.0F);
					player.sendMessage(ChatColor.WHITE+"(�� ������ ������ ��ĥ �� ���� �� �ϴ�...)");
					return;
				}
			}
			else
			{
				if(new GoldBigDragon_RPG.Util.PlayerUtil().giveItem(player, item)==false)
					new GoldBigDragon_RPG.Event.ItemDrop().CustomItemDrop(player.getLocation(), item);
				s.SP(player, Sound.IRONGOLEM_WALK, 1.0F, 1.0F);
				player.sendMessage(ChatColor.WHITE+"(�� ������ ������ ��ĥ �� ���� �� �ϴ�...)");
				return;
			}
		}
	}

	private void PartyEnterDungeon(Player player, ItemStack item, String AltarName, int capacity, String DungeonName, int LvDistrict, int RealLvDistrict)
	{
		if(GoldBigDragon_RPG.Main.ServerOption.PartyJoiner.containsKey(player))
		{
			GoldBigDragon_RPG.Effect.Sound s = new GoldBigDragon_RPG.Effect.Sound();
			if(capacity!=-1)
				if(GoldBigDragon_RPG.Main.ServerOption.Party.get(GoldBigDragon_RPG.Main.ServerOption.PartyJoiner.get(player)).getPartyMembers()!=capacity)
				{
					if(new GoldBigDragon_RPG.Util.PlayerUtil().giveItem(player, item)==false)
						new GoldBigDragon_RPG.Event.ItemDrop().CustomItemDrop(player.getLocation(), item);
					s.SP(player, Sound.IRONGOLEM_WALK, 1.0F, 1.0F);
					player.sendMessage(ChatColor.RED + "[����] : ���� ���� �ο��� ���� �ʽ��ϴ�! ("+capacity+"��)");
					return;
				}
		  	YamlController YC = new YamlController(GoldBigDragon_RPG.Main.Main.plugin);
			YamlManager AltarConfig = YC.getNewConfig("Dungeon/Altar/"+AltarName+".yml");
			YamlManager DungeonConfig = YC.getNewConfig("Dungeon/Dungeon/"+AltarConfig.getString("NormalDungeon")+"/Option.yml");
			if(LvDistrict==-1)
				LvDistrict = DungeonConfig.getInt("District.Level");
			if(RealLvDistrict==-1)
				RealLvDistrict = DungeonConfig.getInt("District.RealLevel");
			if(DungeonName!=null)
				DungeonConfig = YC.getNewConfig("Dungeon/Dungeon/"+DungeonName+"/Option.yml");
			long UTC = GoldBigDragon_RPG.Main.ServerOption.PartyJoiner.get(player);
			if(GoldBigDragon_RPG.Main.ServerOption.Party.get(UTC).getLeader().compareTo(player.getName())==0)
			{
				//��Ƽ�� �߰��ϱ�//
				ArrayList<Player> NearPartyMember = new ArrayList<Player>();
				GoldBigDragon_RPG.Main.ServerOption.Party.get(UTC).getMember();
				for(int count = 0; count < GoldBigDragon_RPG.Main.ServerOption.Party.get(UTC).getPartyMembers(); count++)
				{
					if(player.getWorld().getName().compareTo(GoldBigDragon_RPG.Main.ServerOption.Party.get(UTC).getMember()[count].getWorld().getName())==0)
						if(player.getLocation().distance(GoldBigDragon_RPG.Main.ServerOption.Party.get(UTC).getMember()[count].getLocation()) < 11)
							NearPartyMember.add(GoldBigDragon_RPG.Main.ServerOption.Party.get(UTC).getMember()[count]);
				}
				for(int count = 0; count < NearPartyMember.size(); count++)
				{
					if(GoldBigDragon_RPG.Main.ServerOption.PlayerList.get(NearPartyMember.get(count).getUniqueId().toString()).getStat_Level()< LvDistrict)
					{
						if(new GoldBigDragon_RPG.Util.PlayerUtil().giveItem(player, item)==false)
							new GoldBigDragon_RPG.Event.ItemDrop().CustomItemDrop(player.getLocation(), item);
						s.SP(player, Sound.IRONGOLEM_WALK, 1.0F, 1.0F);
						player.sendMessage(ChatColor.RED + "[����] : ��Ƽ�� "+NearPartyMember.get(count).getName()+"���� ������ ���� ������ ������ �� �����ϴ�!");
						player.sendMessage(ChatColor.RED + "(���� ���� : "+DungeonConfig.getInt("District.Level")+")");
						return;
					}
					if(GoldBigDragon_RPG.Main.ServerOption.PlayerList.get(NearPartyMember.get(count).getUniqueId().toString()).getStat_RealLevel()<RealLvDistrict)
					{
						if(new GoldBigDragon_RPG.Util.PlayerUtil().giveItem(player, item)==false)
							new GoldBigDragon_RPG.Event.ItemDrop().CustomItemDrop(player.getLocation(), item);
						s.SP(player, Sound.IRONGOLEM_WALK, 1.0F, 1.0F);
						player.sendMessage(ChatColor.RED + "[����] : ��Ƽ�� "+NearPartyMember.get(count).getName()+"���� ���� ������ ���� ������ ������ �� �����ϴ�!");
						player.sendMessage(ChatColor.RED + "(���� ���� ���� : "+DungeonConfig.getInt("District.RealLevel")+")");
						return;
					}
				}
				if(new GoldBigDragon_RPG.Dungeon.DungeonCreater().CreateDungeon(player, DungeonConfig.getInt("Size"), DungeonConfig.getInt("Maze_Level"), DungeonConfig.getString("Type.Name"),DungeonName,NearPartyMember, AltarName, item)==false)
				{
					if(new GoldBigDragon_RPG.Util.PlayerUtil().giveItem(player, item)==false)
						new GoldBigDragon_RPG.Event.ItemDrop().CustomItemDrop(player.getLocation(), item);
					s.SP(player, Sound.IRONGOLEM_WALK, 1.0F, 1.0F);
					return;
				}
			}
			else
			{
				if(new GoldBigDragon_RPG.Util.PlayerUtil().giveItem(player, item)==false)
					new GoldBigDragon_RPG.Event.ItemDrop().CustomItemDrop(player.getLocation(), item);
				s.SP(player, Sound.IRONGOLEM_WALK, 1.0F, 1.0F);
				player.sendMessage(ChatColor.RED+"[��Ƽ] : ��Ƽ�� ������ ���ܿ� ������ ��ĥ �� �ֽ��ϴ�!");
				return;
			}
		}
		else
			SoloEnterDungeon(player, item, AltarName, capacity, DungeonName, LvDistrict, RealLvDistrict);
	}
	
	private void SoloEnterDungeon(Player player, ItemStack item, String AltarName, int capacity, String DungeonName, int LvDistrict, int RealLvDistrict)
	{
		GoldBigDragon_RPG.Effect.Sound s = new GoldBigDragon_RPG.Effect.Sound();
		if(capacity==-1||capacity==1)
		{
		  	YamlController YC = new YamlController(GoldBigDragon_RPG.Main.Main.plugin);
			YamlManager AltarConfig = YC.getNewConfig("Dungeon/Altar/"+AltarName+".yml");
			YamlManager DungeonConfig = YC.getNewConfig("Dungeon/Dungeon/"+AltarConfig.getString("NormalDungeon")+"/Option.yml");
			if(DungeonName!=null)
				DungeonConfig = YC.getNewConfig("Dungeon/Dungeon/"+DungeonName+"/Option.yml");
			if(LvDistrict==-1)
				LvDistrict = DungeonConfig.getInt("District.Level");
			if(RealLvDistrict==-1)
				RealLvDistrict = DungeonConfig.getInt("District.RealLevel");
			if(GoldBigDragon_RPG.Main.ServerOption.PlayerList.get(player.getUniqueId().toString()).getStat_Level()< LvDistrict)
			{
				if(new GoldBigDragon_RPG.Util.PlayerUtil().giveItem(player, item)==false)
					new GoldBigDragon_RPG.Event.ItemDrop().CustomItemDrop(player.getLocation(), item);
				s.SP(player, Sound.IRONGOLEM_WALK, 1.0F, 1.0F);
				player.sendMessage(ChatColor.RED + "[����] : ����� ������ ���� ������ ������ �� �����ϴ�!");
				player.sendMessage(ChatColor.RED + "(���� ���� : "+DungeonConfig.getInt("District.Level")+")");
				return;
			}
			if(GoldBigDragon_RPG.Main.ServerOption.PlayerList.get(player.getUniqueId().toString()).getStat_RealLevel()<RealLvDistrict)
			{
				if(new GoldBigDragon_RPG.Util.PlayerUtil().giveItem(player, item)==false)
					new GoldBigDragon_RPG.Event.ItemDrop().CustomItemDrop(player.getLocation(), item);
				s.SP(player, Sound.IRONGOLEM_WALK, 1.0F, 1.0F);
				player.sendMessage(ChatColor.RED + "[����] : ����� ���� ������ ���� ������ ������ �� �����ϴ�!");
				player.sendMessage(ChatColor.RED + "(���� ���� ���� : "+DungeonConfig.getInt("District.RealLevel")+")");
				return;
			}
			if(new GoldBigDragon_RPG.Dungeon.DungeonCreater().CreateDungeon(player, DungeonConfig.getInt("Size"), DungeonConfig.getInt("Maze_Level"), DungeonConfig.getString("Type.Name"),DungeonName,null, AltarName, item)==false)
			{
				if(new GoldBigDragon_RPG.Util.PlayerUtil().giveItem(player, item)==false)
					new GoldBigDragon_RPG.Event.ItemDrop().CustomItemDrop(player.getLocation(), item);
				s.SP(player, Sound.IRONGOLEM_WALK, 1.0F, 1.0F);
				return;
			}
		}
		else
		{
			if(new GoldBigDragon_RPG.Util.PlayerUtil().giveItem(player, item)==false)
				new GoldBigDragon_RPG.Event.ItemDrop().CustomItemDrop(player.getLocation(), item);
			s.SP(player, Sound.IRONGOLEM_WALK, 1.0F, 1.0F);
			player.sendMessage(ChatColor.RED + "[����] : ���� ���� �ο��� ���� �ʽ��ϴ�! ("+capacity+"��)");
			return;
		}
	}
	//DungeonGUI Close//

}
