package GoldBigDragon_RPG.Skill;

import java.util.Arrays;
import java.util.Set;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Sound;
import org.bukkit.inventory.Inventory;

import GoldBigDragon_RPG.GUI.GUIutil;
import GoldBigDragon_RPG.GUI.OPBoxGUI;
import GoldBigDragon_RPG.Main.ServerOption;
import GoldBigDragon_RPG.Main.Object_UserData;
import GoldBigDragon_RPG.Util.YamlController;
import GoldBigDragon_RPG.Util.YamlManager;

import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryClickEvent;

public class OPBoxSkillGUI extends GUIutil
{
	public void AllSkillsGUI(Player player, short page, boolean isJobGUI,String WhatJob)
	{
		YamlController YC = new YamlController(GoldBigDragon_RPG.Main.Main.plugin);
		YamlManager SkillList  = YC.getNewConfig("Skill/SkillList.yml");
		Inventory inv = Bukkit.createInventory(null, 54, ChatColor.BLACK + "��ü ��ų ��� : " + (page+1));
		Object[] a = SkillList.getKeys().toArray();
		
		byte loc=0;
		for(int count = page*45; count < a.length;count++)
		{
			String SkillName = a[count].toString();
			short JobLevel=0;
			if(SkillList.contains(a[count].toString()+".SkillRank"))
				JobLevel= (short) SkillList.getConfigurationSection(a[count].toString()+".SkillRank").getKeys(false).size();
			if(count > a.length || loc >= 45) break;
			
			if(isJobGUI == true)
			{
				YamlManager JobList  = YC.getNewConfig("Skill/JobList.yml");
				if(WhatJob.equals("Maple")==true)
				{
					Object_UserData u = new Object_UserData();
					if(JobList.contains("MapleStory."+u.getString(player, (byte)3)+"."+u.getString(player, (byte)2)+".Skill."+SkillName)==false)
					{
						Stack2(ChatColor.WHITE + "" + ChatColor.BOLD + SkillName,  SkillList.getInt(a[count].toString()+".ID"),SkillList.getInt(a[count].toString()+".DATA"),SkillList.getInt(a[count].toString()+".Amount"),Arrays.asList(ChatColor.DARK_AQUA+"�ִ� ��ų ���� : " + ChatColor.WHITE+JobLevel,"",ChatColor.YELLOW+"[�� Ŭ���� ��ų ���]"), loc, inv);
						loc++;	
					}
				}
				else
				{
					if(JobList.contains("Mabinogi.Added."+SkillName)==false)
					{
						Stack2(ChatColor.WHITE + "" + ChatColor.BOLD + SkillName,  SkillList.getInt(a[count].toString()+".ID"),SkillList.getInt(a[count].toString()+".DATA"),SkillList.getInt(a[count].toString()+".Amount"),Arrays.asList(ChatColor.DARK_AQUA+"�ִ� ��ų ���� : " + ChatColor.WHITE+JobLevel,"",ChatColor.YELLOW+"[�� Ŭ���� ��ų ���]"), loc, inv);
						loc++;	
					}
				}

			}
			else
			{
				Stack2(ChatColor.WHITE + "" + ChatColor.BOLD + SkillName,  SkillList.getInt(a[count].toString()+".ID"),SkillList.getInt(a[count].toString()+".DATA"),SkillList.getInt(a[count].toString()+".Amount"),Arrays.asList(ChatColor.DARK_AQUA+"�ִ� ��ų ���� : " + ChatColor.WHITE+JobLevel,"",ChatColor.YELLOW+"[�� Ŭ���� ��ų ���� ����]",ChatColor.YELLOW+"[Shift + �� Ŭ���� ������ ����]",ChatColor.RED+"[Shift + �� Ŭ���� ��ų ����]"), loc, inv);
				loc++;	
			}
		}
		
		if(a.length-(page*44)>45)
			Stack2(ChatColor.WHITE + "" + ChatColor.BOLD + "���� ������", 323,0,1,Arrays.asList(ChatColor.GRAY + "���� �������� �̵� �մϴ�."), 50, inv);
		if(page!=0)
			Stack2(ChatColor.WHITE + "" + ChatColor.BOLD + "���� ������", 323,0,1,Arrays.asList(ChatColor.GRAY + "���� �������� �̵� �մϴ�."), 48, inv);

		if(isJobGUI == false)
			Stack2(ChatColor.WHITE + "" + ChatColor.BOLD + "�� ��ų", 386,0,1,Arrays.asList(ChatColor.GRAY + "���ο� ��ų�� �����մϴ�."), 49, inv);
		Stack2(ChatColor.WHITE + "" + ChatColor.BOLD + "���� ���", 323,0,1,Arrays.asList(ChatColor.GRAY + "���� ȭ������ ���ư��ϴ�.",ChatColor.BLACK+WhatJob), 45, inv);
		Stack2(ChatColor.WHITE + "" + ChatColor.BOLD + "�ݱ�", 324,0,1,Arrays.asList(ChatColor.GRAY + "â�� �ݽ��ϴ�.",ChatColor.BLACK+""+isJobGUI), 53, inv);
		player.openInventory(inv);
	}

	public void IndividualSkillOptionGUI(Player player, short page, String SkillName)
	{
		YamlController YC = new YamlController(GoldBigDragon_RPG.Main.Main.plugin);
		YamlManager SkillList  = YC.getNewConfig("Skill/SkillList.yml");
		
		Inventory inv = Bukkit.createInventory(null, 54, ChatColor.BLACK + "��ų ���� : " + (page+1));

		Set<String> b= SkillList.getConfigurationSection(SkillName+".SkillRank").getKeys(false);
		
		byte loc=0;
		for(int count = page*45; count < b.size();count++)
		{
			if(count > b.size() || loc >= 45) break;
			String lore = "";
			if(SkillList.getString(SkillName+".SkillRank."+(count+1)+".Command").equalsIgnoreCase("null"))
				lore = ChatColor.GRAY+"[������ Ŀ�ǵ� ����]%enter%";
			else
			{
				lore = ChatColor.DARK_AQUA+"Ŀ�ǵ� : "+ChatColor.WHITE+SkillList.getString(SkillName+".SkillRank."+(count+1)+".Command")+"%enter%";
				if(SkillList.getBoolean(SkillName+".SkillRank."+(count+1)+".BukkitPermission") == false)
					lore = lore + ChatColor.WHITE+"[���� ���� ���]%enter%";
				else
					lore = lore + ChatColor.LIGHT_PURPLE+"[�ܼ� ���� ���]%enter%";
			}
			
			if(SkillList.getString(SkillName+".SkillRank."+(count+1)+".MagicSpells").equalsIgnoreCase("null"))
				lore = lore + ChatColor.GRAY+"[���� �������� ����]%enter%";
			else
				lore = lore + ChatColor.DARK_AQUA+"�������� : "+ChatColor.WHITE+SkillList.getString(SkillName+".SkillRank."+(count+1)+".MagicSpells")+"%enter%";

			if((page*45)+count != 0)
			{
				if(SkillList.getInt(SkillName+".SkillRank."+(count+1)+".NeedLevel") >= 1)
					lore = lore + ChatColor.AQUA+"�±޽� �ʿ� ���� : "+SkillList.getInt(SkillName+".SkillRank."+(count+1)+".NeedLevel")+"%enter%";
				if(SkillList.getInt(SkillName+".SkillRank."+(count+1)+".NeedRealLevel") >= 1)
					lore = lore + ChatColor.AQUA+"�±޽� �ʿ� ���� ���� : "+SkillList.getInt(SkillName+".SkillRank."+(count+1)+".NeedRealLevel")+"%enter%";

				lore = lore + ChatColor.AQUA+"�±޽� �ʿ� ��ų ����Ʈ : "+SkillList.getInt(SkillName+".SkillRank."+(count+1)+".SkillPoint")+"%enter%";

			if(SkillList.getInt(SkillName+".SkillRank."+(count+1)+".BonusHP")<0)
				lore = lore + ChatColor.RED+"�±޽� ���ʽ� ����� : "+SkillList.getInt(SkillName+".SkillRank."+(count+1)+".BonusHP")+"%enter%";
			else if(SkillList.getInt(SkillName+".SkillRank."+(count+1)+".BonusHP")>0)
				lore = lore + ChatColor.AQUA+"�±޽� ���ʽ� ����� : "+SkillList.getInt(SkillName+".SkillRank."+(count+1)+".BonusHP")+"%enter%";

			if(SkillList.getInt(SkillName+".SkillRank."+(count+1)+".BonusMP")<0)
				lore = lore + ChatColor.RED+"�±޽� ���ʽ� ���� : "+SkillList.getInt(SkillName+".SkillRank."+(count+1)+".BonusMP")+"%enter%";
			else if(SkillList.getInt(SkillName+".SkillRank."+(count+1)+".BonusMP")>0)
				lore = lore + ChatColor.AQUA+"�±޽� ���ʽ� ���� : "+SkillList.getInt(SkillName+".SkillRank."+(count+1)+".BonusMP")+"%enter%";
			
			if(SkillList.getInt(SkillName+".SkillRank."+(count+1)+".BonusSTR")<0)
				lore = lore + ChatColor.RED+"�±޽� ���ʽ� "+ServerOption.STR+" : "+SkillList.getInt(SkillName+".SkillRank."+(count+1)+".BonusSTR")+"%enter%";
			else if(SkillList.getInt(SkillName+".SkillRank."+(count+1)+".BonusSTR")>0)
				lore = lore + ChatColor.AQUA+"�±޽� ���ʽ� "+ServerOption.STR+" : "+SkillList.getInt(SkillName+".SkillRank."+(count+1)+".BonusSTR")+"%enter%";

			if(SkillList.getInt(SkillName+".SkillRank."+(count+1)+".BonusDEX")<0)
				lore = lore + ChatColor.RED+"�±޽� ���ʽ� "+ServerOption.DEX+" : "+SkillList.getInt(SkillName+".SkillRank."+(count+1)+".BonusDEX")+"%enter%";
			else if(SkillList.getInt(SkillName+".SkillRank."+(count+1)+".BonusDEX")>0)
				lore = lore + ChatColor.AQUA+"�±޽� ���ʽ� "+ServerOption.DEX+" : "+SkillList.getInt(SkillName+".SkillRank."+(count+1)+".BonusDEX")+"%enter%";
				
			if(SkillList.getInt(SkillName+".SkillRank."+(count+1)+".BonusINT")<0)
				lore = lore + ChatColor.RED+"�±޽� ���ʽ� "+ServerOption.INT+" : "+SkillList.getInt(SkillName+".SkillRank."+(count+1)+".BonusINT")+"%enter%";
			else if(SkillList.getInt(SkillName+".SkillRank."+(count+1)+".BonusINT")>0)
				lore = lore + ChatColor.AQUA+"�±޽� ���ʽ� "+ServerOption.INT+" : "+SkillList.getInt(SkillName+".SkillRank."+(count+1)+".BonusINT")+"%enter%";

			if(SkillList.getInt(SkillName+".SkillRank."+(count+1)+".BonusWILL")<0)
				lore = lore + ChatColor.RED+"�±޽� ���ʽ� "+ServerOption.WILL+" : "+SkillList.getInt(SkillName+".SkillRank."+(count+1)+".BonusWILL")+"%enter%";
			else if(SkillList.getInt(SkillName+".SkillRank."+(count+1)+".BonusWILL")>0)
				lore = lore + ChatColor.AQUA+"�±޽� ���ʽ� "+ServerOption.WILL+" : "+SkillList.getInt(SkillName+".SkillRank."+(count+1)+".BonusWILL")+"%enter%";

			if(SkillList.getInt(SkillName+".SkillRank."+(count+1)+".BonusLUK")<0)
				lore = lore + ChatColor.RED+"�±޽� ���ʽ� "+ServerOption.LUK+" : "+SkillList.getInt(SkillName+".SkillRank."+(count+1)+".BonusLUK")+"%enter%";
			else if(SkillList.getInt(SkillName+".SkillRank."+(count+1)+".BonusLUK")>0)
				lore = lore + ChatColor.AQUA+"�±޽� ���ʽ� "+ServerOption.LUK+" : "+SkillList.getInt(SkillName+".SkillRank."+(count+1)+".BonusLUK")+"%enter%";

			if(SkillList.getInt(SkillName+".SkillRank."+(count+1)+".BonusBAL")<0)
				lore = lore + ChatColor.RED+"�±޽� ���ʽ� �뷱�� : "+SkillList.getInt(SkillName+".SkillRank."+(count+1)+".BonusBAL")+"%enter%";
			else if(SkillList.getInt(SkillName+".SkillRank."+(count+1)+".BonusBAL")>0)
				lore = lore + ChatColor.AQUA+"�±޽� ���ʽ� �뷱�� : "+SkillList.getInt(SkillName+".SkillRank."+(count+1)+".BonusBAL")+"%enter%";

			if(SkillList.getInt(SkillName+".SkillRank."+(count+1)+".BonusCRI")<0)
				lore = lore + ChatColor.RED+"�±޽� ���ʽ� ũ��Ƽ�� : "+SkillList.getInt(SkillName+".SkillRank."+(count+1)+".BonusCRI")+"%enter%";
			else if(SkillList.getInt(SkillName+".SkillRank."+(count+1)+".BonusCRI")>0)
				lore = lore + ChatColor.AQUA+"�±޽� ���ʽ� ũ��Ƽ�� : "+SkillList.getInt(SkillName+".SkillRank."+(count+1)+".BonusCRI")+"%enter%";
			
			if(SkillList.getInt(SkillName+".SkillRank."+(count+1)+".BonusDEF")<0)
				lore = lore + ChatColor.RED+"�±޽� ���ʽ� ��� : "+SkillList.getInt(SkillName+".SkillRank."+(count+1)+".BonusDEF")+"%enter%";
			else if(SkillList.getInt(SkillName+".SkillRank."+(count+1)+".BonusDEF")>0)
				lore = lore + ChatColor.AQUA+"�±޽� ���ʽ� ��� : "+SkillList.getInt(SkillName+".SkillRank."+(count+1)+".BonusDEF")+"%enter%";

			if(SkillList.getInt(SkillName+".SkillRank."+(count+1)+".BonusPRO")<0)
				lore = lore + ChatColor.RED+"�±޽� ���ʽ� ��ȣ : "+SkillList.getInt(SkillName+".SkillRank."+(count+1)+".BonusPRO")+"%enter%";
			else if(SkillList.getInt(SkillName+".SkillRank."+(count+1)+".BonusPRO")>0)
				lore = lore + ChatColor.AQUA+"�±޽� ���ʽ� ��ȣ : "+SkillList.getInt(SkillName+".SkillRank."+(count+1)+".BonusPRO")+"%enter%";

			if(SkillList.getInt(SkillName+".SkillRank."+(count+1)+".BonusMDEF")<0)
				lore = lore + ChatColor.RED+"�±޽� ���ʽ� ���� ��� : "+SkillList.getInt(SkillName+".SkillRank."+(count+1)+".BonusMDEF")+"%enter%";
			else if(SkillList.getInt(SkillName+".SkillRank."+(count+1)+".BonusMDEF")>0)
				lore = lore + ChatColor.AQUA+"�±޽� ���ʽ� ���� ��� : "+SkillList.getInt(SkillName+".SkillRank."+(count+1)+".BonusMDEF")+"%enter%";

			if(SkillList.getInt(SkillName+".SkillRank."+(count+1)+".BonusMPRO")<0)
				lore = lore + ChatColor.RED+"�±޽� ���ʽ� ���� ��ȣ : "+SkillList.getInt(SkillName+".SkillRank."+(count+1)+".BonusMPRO")+"%enter%";
			else if(SkillList.getInt(SkillName+".SkillRank."+(count+1)+".BonusMPRO")>0)
				lore = lore + ChatColor.AQUA+"�±޽� ���ʽ� ���� ��ȣ : "+SkillList.getInt(SkillName+".SkillRank."+(count+1)+".BonusMPRO")+"%enter%";
			
			}
			if((page*45)+count ==0)
				lore = lore + "%enter%"+ChatColor.YELLOW+"[�� Ŭ���� ��ũ ���� ����]%enter%"+ChatColor.RED+"[1���� ��ų�� ���� �Ұ���]";
			else if((count +1) == b.size())
				lore = lore + "%enter%"+ChatColor.YELLOW+"[�� Ŭ���� ��ũ ���� ����]%enter%"+ChatColor.RED+"[Shift + �� Ŭ���� ����]";
			else
				lore = lore + "%enter%"+ChatColor.YELLOW+"[�� Ŭ���� ��ũ ���� ����]%enter%"+ChatColor.RED+"[���� ��ũ ���� �� ���� ����]";
			
			String[] scriptA = lore.split("%enter%");
			for(short counter = 0; counter < scriptA.length; counter++)
				scriptA[counter] =  scriptA[counter];
			Stack2(ChatColor.WHITE + "" + ChatColor.BOLD + SkillName +" ���� "+(count +1) , 340,0,1,Arrays.asList(scriptA), loc, inv);
			loc++;
		}
		
		if(b.size()-(page*44)>45)
			Stack2(ChatColor.WHITE + "" + ChatColor.BOLD + "���� ������", 323,0,1,Arrays.asList(ChatColor.GRAY + "���� �������� �̵� �մϴ�."), 50, inv);
		if(page!=0)
			Stack2(ChatColor.WHITE + "" + ChatColor.BOLD + "���� ������", 323,0,1,Arrays.asList(ChatColor.GRAY + "���� �������� �̵� �մϴ�."), 48, inv);
		Stack2(ChatColor.WHITE + "" + ChatColor.BOLD + "�� ��ũ", 386,0,1,Arrays.asList(ChatColor.GRAY + "���ο� ��ų ��ũ�� ���� �մϴ�."), 49, inv);
		Stack2(ChatColor.WHITE + "" + ChatColor.BOLD + "���� ���", 323,0,1,Arrays.asList(ChatColor.GRAY + "���� ȭ������ ���ư��ϴ�."), 45, inv);
		Stack2(ChatColor.WHITE + "" + ChatColor.BOLD + "�ݱ�", 324,0,1,Arrays.asList(ChatColor.GRAY + "â�� �ݽ��ϴ�.",ChatColor.BLACK+SkillName), 53, inv);
		player.openInventory(inv);
	}
	
	public void SkillRankOptionGUI(Player player, String SkillName, short SkillLevel)
	{
		YamlController YC = new YamlController(GoldBigDragon_RPG.Main.Main.plugin);
		YamlManager SkillList  = YC.getNewConfig("Skill/SkillList.yml");
		Inventory inv = Bukkit.createInventory(null, 54, ChatColor.BLACK + "��ũ "+SkillLevel);
		String lore = "";
		if(SkillList.getString(SkillName+".SkillRank."+SkillLevel+".Command").equalsIgnoreCase("null"))
			lore = ChatColor.GRAY+"[  ����  ]";
		else
			lore = ChatColor.WHITE+SkillList.getString(SkillName+".SkillRank."+SkillLevel+".Command");
			
		Stack2(ChatColor.DARK_AQUA + "[Ŀ�ǵ� ����]", 137,0,1,Arrays.asList("",ChatColor.DARK_AQUA + "[���� ��ϵ� Ŀ�ǵ�]",lore,"",ChatColor.YELLOW + "[�� Ŭ���� Ŀ�ǵ� ����]"), 11, inv);
		if(SkillList.getBoolean(SkillName+".SkillRank."+SkillLevel+".BukkitPermission")==false)
			Stack2(ChatColor.DARK_AQUA + "[Ŀ�ǵ� ����]", 397,3,1,Arrays.asList("",ChatColor.DARK_AQUA + "[Ŀ�ǵ� ����� ���� ����]",ChatColor.GRAY+"[  ����  ]","",ChatColor.YELLOW + "[�� Ŭ���� ���� ����]"), 13, inv);
		else
			Stack2(ChatColor.DARK_AQUA + "[Ŀ�ǵ� ����]", 137,3,1,Arrays.asList("",ChatColor.DARK_AQUA + "[Ŀ�ǵ� ����� ���� ����]",ChatColor.LIGHT_PURPLE+"[  ��Ŷ  ]","",ChatColor.YELLOW + "[�� Ŭ���� ���� ����]"), 13, inv);

		if(Bukkit.getPluginManager().isPluginEnabled("MagicSpells") == true)
		{
			if(SkillList.getString(SkillName+".SkillRank."+SkillLevel+".MagicSpells").equalsIgnoreCase("null"))
				Stack2(ChatColor.DARK_AQUA + "[���� ����]", 280,0,1,Arrays.asList("",ChatColor.DARK_AQUA + "[���� ��ϵ� ����]",ChatColor.GRAY + "[  ����  ]","",ChatColor.YELLOW + "[�� Ŭ���� ���� ����]"), 15, inv);
			else
				Stack2(ChatColor.DARK_AQUA + "[���� ����]", 369,0,1,Arrays.asList("",ChatColor.DARK_AQUA + "[���� ��ϵ� ����]",ChatColor.WHITE+SkillList.getString(SkillName+".SkillRank."+SkillLevel+".MagicSpells"),"",ChatColor.YELLOW + "[�� Ŭ���� ���� ����]"), 15, inv);

			if(SkillList.contains(SkillName+".SkillRank."+SkillLevel+".AffectStat")==false)
			{
				SkillList.set(SkillName+".SkillRank."+SkillLevel+".AffectStat", "����");
				SkillList.saveConfig();
			}
			String IncreaseDamage = SkillList.getString(SkillName+".SkillRank."+SkillLevel+".AffectStat");
			
			if(IncreaseDamage.compareTo("����")==0)
				Stack2(ChatColor.WHITE+""+ChatColor.BOLD + "[��ų ���ݷ� ���]", 166,0,1,Arrays.asList("",ChatColor.WHITE+"[  "+IncreaseDamage+"  ]",ChatColor.RED + "[��ų ���� �������� ���]","",ChatColor.YELLOW + "[�� Ŭ���� ���� �ִ� ���� ����]"), 21, inv);
			else if(IncreaseDamage.compareTo("�����")==0)
				Stack2(ChatColor.WHITE+""+ChatColor.BOLD + "[��ų ���ݷ� ���]", 351,1,1,Arrays.asList("",ChatColor.WHITE+"[  "+IncreaseDamage+"  ]",ChatColor.RED + "[���� ����¿� ����Ͽ� ����� ���]","",ChatColor.YELLOW + "[�� Ŭ���� ���� �ִ� ���� ����]"), 21, inv);
			else if(IncreaseDamage.compareTo("����")==0)
				Stack2(ChatColor.WHITE+""+ChatColor.BOLD + "[��ų ���ݷ� ���]", 351,12,1,Arrays.asList("",ChatColor.WHITE+"[  "+IncreaseDamage+"  ]",ChatColor.RED + "[���� �������� ����Ͽ� ����� ���]","",ChatColor.YELLOW + "[�� Ŭ���� ���� �ִ� ���� ����]"), 21, inv);
			else if(IncreaseDamage.compareTo(ServerOption.STR)==0)
				Stack2(ChatColor.WHITE+""+ChatColor.BOLD + "[��ų ���ݷ� ���]", 267,0,1,Arrays.asList("",ChatColor.WHITE+"[  "+IncreaseDamage+"  ]",ChatColor.RED + "["+ServerOption.STR+"�� ����Ͽ� ����� ���]","",ChatColor.YELLOW + "[�� Ŭ���� ���� �ִ� ���� ����]"), 21, inv);
			else if(IncreaseDamage.compareTo(ServerOption.DEX)==0)
				Stack2(ChatColor.WHITE+""+ChatColor.BOLD + "[��ų ���ݷ� ���]", 261,0,1,Arrays.asList("",ChatColor.WHITE+"[  "+IncreaseDamage+"  ]",ChatColor.RED + "["+ServerOption.DEX+"�� ����Ͽ� ����� ���]","",ChatColor.YELLOW + "[�� Ŭ���� ���� �ִ� ���� ����]"), 21, inv);
			else if(IncreaseDamage.compareTo(ServerOption.INT)==0)
				Stack2(ChatColor.WHITE+""+ChatColor.BOLD + "[��ų ���ݷ� ���]", 369,0,1,Arrays.asList("",ChatColor.WHITE+"[  "+IncreaseDamage+"  ]",ChatColor.RED + "["+ServerOption.INT+"�� ����Ͽ� ����� ���]","",ChatColor.YELLOW + "[�� Ŭ���� ���� �ִ� ���� ����]"), 21, inv);
			else if(IncreaseDamage.compareTo(ServerOption.WILL)==0)
				Stack2(ChatColor.WHITE+""+ChatColor.BOLD + "[��ų ���ݷ� ���]", 370,0,1,Arrays.asList("",ChatColor.WHITE+"[  "+IncreaseDamage+"  ]",ChatColor.RED + "["+ServerOption.WILL+"�� ����Ͽ� ����� ���]","",ChatColor.YELLOW + "[�� Ŭ���� ���� �ִ� ���� ����]"), 21, inv);
			else if(IncreaseDamage.compareTo(ServerOption.LUK)==0)
				Stack2(ChatColor.WHITE+""+ChatColor.BOLD + "[��ų ���ݷ� ���]", 322,0,1,Arrays.asList("",ChatColor.WHITE+"[  "+IncreaseDamage+"  ]",ChatColor.RED + "["+ServerOption.LUK+"�� ����Ͽ� ����� ���]","",ChatColor.YELLOW + "[�� Ŭ���� ���� �ִ� ���� ����]"), 21, inv);
			else
				Stack2(ChatColor.WHITE+""+ChatColor.BOLD + "[��ų ���ݷ� ���]", 322,0,1,Arrays.asList("",ChatColor.RED+"[�� ������ �ʿ��մϴ�!]","",ChatColor.YELLOW + "[�� Ŭ���� ���� �ִ� ���� ����]"), 21, inv);
		}
		else
		{
			Stack2(ChatColor.RED + "[���� ����]", 166,0,1,Arrays.asList("",ChatColor.RED + "[MagicSpells �÷������� ã�� �� ����]",ChatColor.GRAY + "MagicSpells �÷������� ���� ���",ChatColor.GRAY + "����� �� �ִ� �ɼ��Դϴ�.",""), 15, inv);
			Stack2(ChatColor.RED + "[��ų ���ݷ� ���]", 166,0,1,Arrays.asList("",ChatColor.RED + "[MagicSpells �÷������� ã�� �� ����]",ChatColor.GRAY + "MagicSpells �÷������� ���� ���",ChatColor.GRAY + "����� �� �ִ� �ɼ��Դϴ�.",""), 21, inv);
		}

		if(SkillList.contains(SkillName+".SkillRank."+SkillLevel+".DistrictWeapon")==false)
		{
			SkillList.set(SkillName+".SkillRank."+SkillLevel+".DistrictWeapon", "����");
			SkillList.saveConfig();
		}
		String WeaponType = SkillList.getString(SkillName+".SkillRank."+SkillLevel+".DistrictWeapon");
		switch(WeaponType)
		{
			case "����":
				Stack2(ChatColor.WHITE+""+ChatColor.BOLD + "[���� Ÿ�� ����]", 166,0,1,Arrays.asList("",ChatColor.WHITE+"[  "+WeaponType+"  ]",ChatColor.AQUA + "[���� ���� ��ų �ߵ� ����]","",ChatColor.YELLOW + "[�� Ŭ���� ��� ���� ����]"), 19, inv);
				break;
			case "���� ����":
				Stack2(ChatColor.WHITE+""+ChatColor.BOLD + "[���� Ÿ�� ����]", 267,0,1,Arrays.asList("",ChatColor.WHITE+"[  "+WeaponType+"  ]",ChatColor.RED + "[�ش�Ǵ� ������ ������ �־�� �ߵ�]",ChatColor.RED + "  �Ѽ� ��",ChatColor.RED + "  ��� ��",ChatColor.RED + "  ����",ChatColor.RED + "  ��",ChatColor.RED + "  ���� ����",ChatColor.LIGHT_PURPLE + "  �Ϲ� ��/����/���� ������","",ChatColor.YELLOW + "[�� Ŭ���� ��� ���� ����]"), 19, inv);
				break;
			case "�Ѽ� ��":
				Stack2(ChatColor.WHITE+""+ChatColor.BOLD + "[���� Ÿ�� ����]", 267,0,1,Arrays.asList("",ChatColor.WHITE+"[  "+WeaponType+"  ]",ChatColor.RED + "[������ ���� '�Ѽ� ��'�� �־�� �ߵ�]",ChatColor.LIGHT_PURPLE + "[Ȥ�� �Ϲ� �� ������ ����]","",ChatColor.YELLOW + "[�� Ŭ���� ��� ���� ����]"), 19, inv);
				break;
			case "��� ��":
				Stack2(ChatColor.WHITE+""+ChatColor.BOLD + "[���� Ÿ�� ����]", 267,0,2,Arrays.asList("",ChatColor.WHITE+"[  "+WeaponType+"  ]",ChatColor.RED + "[������ ���� '��� ��'�� �־�� �ߵ�]","",ChatColor.YELLOW + "[�� Ŭ���� ��� ���� ����]"), 19, inv);
				break;
			case "����":
				Stack2(ChatColor.WHITE+""+ChatColor.BOLD + "[���� Ÿ�� ����]", 258,0,1,Arrays.asList("",ChatColor.WHITE+"[  "+WeaponType+"  ]",ChatColor.RED + "[������ ���� '����'�� �־�� �ߵ�]",ChatColor.LIGHT_PURPLE + "[Ȥ�� �Ϲ� ���� ������ ����]","",ChatColor.YELLOW + "[�� Ŭ���� ��� ���� ����]"), 19, inv);
				break;
			case "��":
				Stack2(ChatColor.WHITE+""+ChatColor.BOLD + "[���� Ÿ�� ����]", 292,0,1,Arrays.asList("",ChatColor.WHITE+"[  "+WeaponType+"  ]",ChatColor.RED + "[������ ���� '��'�� �־�� �ߵ�]",ChatColor.LIGHT_PURPLE + "[Ȥ�� �Ϲ� ���� ������ ����]","",ChatColor.YELLOW + "[�� Ŭ���� ��� ���� ����]"), 19, inv);
				break;
			case "���Ÿ� ����":
				Stack2(ChatColor.WHITE+""+ChatColor.BOLD + "[���� Ÿ�� ����]", 261,0,1,Arrays.asList("",ChatColor.WHITE+"[  "+WeaponType+"  ]",ChatColor.RED + "[�ش�Ǵ� ������ ������ �־�� �ߵ�]",ChatColor.RED + "  Ȱ",ChatColor.RED + "  ����",ChatColor.RED + "  ���Ÿ� ����",ChatColor.LIGHT_PURPLE + "  �Ϲ� Ȱ, �߻�� ������","",ChatColor.YELLOW + "[�� Ŭ���� ��� ���� ����]"), 19, inv);
				break;
			case "Ȱ":
				Stack2(ChatColor.WHITE+""+ChatColor.BOLD + "[���� Ÿ�� ����]", 261,0,1,Arrays.asList("",ChatColor.WHITE+"[  "+WeaponType+"  ]",ChatColor.RED + "[������ ���� 'Ȱ'�� �־�� �ߵ�]",ChatColor.LIGHT_PURPLE + "[Ȥ�� �Ϲ� Ȱ ������ ����]","",ChatColor.YELLOW + "[�� Ŭ���� ��� ���� ����]"), 19, inv);
				break;
			case "����":
				Stack2(ChatColor.WHITE+""+ChatColor.BOLD + "[���� Ÿ�� ����]", 23,0,1,Arrays.asList("",ChatColor.WHITE+"[  "+WeaponType+"  ]",ChatColor.RED + "[������ ���� '����'�� �־�� �ߵ�]",ChatColor.LIGHT_PURPLE + "[Ȥ�� �Ϲ� �߻�� ������ ����]","",ChatColor.YELLOW + "[�� Ŭ���� ��� ���� ����]"), 19, inv);
				break;
			case "���� ����":
				Stack2(ChatColor.WHITE+""+ChatColor.BOLD + "[���� Ÿ�� ����]", 280,0,1,Arrays.asList("",ChatColor.WHITE+"[  "+WeaponType+"  ]",ChatColor.RED + "[�ش�Ǵ� ������ ������ �־�� �ߵ�]",ChatColor.RED + "  ����",ChatColor.RED + "  ������",ChatColor.RED + "  ���� ����",ChatColor.LIGHT_PURPLE + "  �Ϲ� �����/��/������ ���� ������","",ChatColor.YELLOW + "[�� Ŭ���� ��� ���� ����]"), 19, inv);
				break;
			case "����":
				Stack2(ChatColor.WHITE+""+ChatColor.BOLD + "[���� Ÿ�� ����]", 280,0,1,Arrays.asList("",ChatColor.WHITE+"[  "+WeaponType+"  ]",ChatColor.RED + "[������ ���� '����'�� �־�� �ߵ�]",ChatColor.LIGHT_PURPLE + "[Ȥ�� �Ϲ� �����/�� ������ ����]","",ChatColor.YELLOW + "[�� Ŭ���� ��� ���� ����]"), 19, inv);
				break;
			case "������":
				Stack2(ChatColor.WHITE+""+ChatColor.BOLD + "[���� Ÿ�� ����]", 369,0,1,Arrays.asList("",ChatColor.WHITE+"[  "+WeaponType+"  ]",ChatColor.RED + "[������ ���� '������'�� �־�� �ߵ�]",ChatColor.LIGHT_PURPLE + "[Ȥ�� �Ϲ� ������ ���� ������ ����]","",ChatColor.YELLOW + "[�� Ŭ���� ��� ���� ����]"), 19, inv);
				break;
			default :
				Stack2(ChatColor.WHITE+""+ChatColor.BOLD + "[���� Ÿ�� ����]", 369,0,1,Arrays.asList("",ChatColor.WHITE+"[  "+WeaponType+"  ]",ChatColor.WHITE + "[������ ���� '"+ChatColor.stripColor(WeaponType)+"'�� �־�� �ߵ�]","",ChatColor.YELLOW + "[�� Ŭ���� ��� ���� ����]"), 19, inv);
				break;
		}

		if(SkillList.contains(SkillName+".SkillRank."+SkillLevel+".Lore")==false)
		{
			SkillList.set(SkillName+".SkillRank."+SkillLevel+".Lore", ChatColor.GRAY + "     [���� ����]     ");
			SkillList.saveConfig();
		}
		
		String lore2 = SkillList.getString(SkillName+".SkillRank."+SkillLevel+".Lore");

		String[] scriptA = lore2.split("%enter%");
		for(short counter = 0; counter < scriptA.length; counter++)
			scriptA[counter] =  scriptA[counter];
		Stack2(ChatColor.WHITE+""+ChatColor.BOLD + "[��ų ����]", 386,0,1,Arrays.asList(scriptA), 23, inv);
		
		if(SkillLevel != 1)
		{
			Stack2(ChatColor.WHITE+""+ChatColor.BOLD + "[�ʿ� ����]", 384,0,1,Arrays.asList("",ChatColor.AQUA + "["+SkillName+" "+(SkillLevel-1)+" ��������",ChatColor.AQUA +"���� ������ �±��ϴµ� �ʿ��� ����]",ChatColor.WHITE +""+ChatColor.BOLD +" ���� "+SkillList.getInt(SkillName+".SkillRank."+SkillLevel+".NeedLevel")+" �̻�",ChatColor.WHITE +""+ChatColor.BOLD +" ���� ���� "+SkillList.getInt(SkillName+".SkillRank."+SkillLevel+".NeedRealLevel") + " �̻�","",ChatColor.YELLOW + "[�� Ŭ���� ���� ����]"), 3, inv);
			Stack2(ChatColor.WHITE+""+ChatColor.BOLD + "[�ʿ� ��ų ����Ʈ]", 399,0,1,Arrays.asList("",ChatColor.AQUA + "["+SkillName+" "+(SkillLevel-1)+" ��������",ChatColor.AQUA +"���� ������ �±��ϴµ� �ʿ��� ��ų ����Ʈ]",ChatColor.WHITE +"     "+ChatColor.BOLD+SkillList.getInt(SkillName+".SkillRank."+SkillLevel+".SkillPoint") +"����Ʈ","",ChatColor.YELLOW + "[�� Ŭ���� ��ų ����Ʈ ����]"), 4, inv);
			Stack2(ChatColor.WHITE+""+ChatColor.BOLD + "[���ʽ� �����]", 351,1,1,Arrays.asList("",ChatColor.AQUA + "["+SkillName+" "+(SkillLevel-1)+" ��������",ChatColor.AQUA +"���� ������ �±��� �� ��� �����]",ChatColor.WHITE +"     "+ChatColor.BOLD+SkillList.getInt(SkillName+".SkillRank."+SkillLevel+".BonusHP") ,"",ChatColor.YELLOW + "[�� Ŭ���� ���ʽ� ���� ����]"), 28, inv);
			Stack2(ChatColor.WHITE+""+ChatColor.BOLD + "[���ʽ� ����]", 351,12,1,Arrays.asList("",ChatColor.AQUA + "["+SkillName+" "+(SkillLevel-1)+" ��������",ChatColor.AQUA +"���� ������ �±��� �� ��� ����]",ChatColor.WHITE +"     "+ChatColor.BOLD+SkillList.getInt(SkillName+".SkillRank."+SkillLevel+".BonusMP"),"",ChatColor.YELLOW + "[�� Ŭ���� ���ʽ� ���� ����]"), 29, inv);
			Stack2(ChatColor.WHITE+""+ChatColor.BOLD + "[���ʽ� "+ServerOption.STR+"]", 267,0,1,Arrays.asList("",ChatColor.AQUA + "["+SkillName+" "+(SkillLevel-1)+" ��������",ChatColor.AQUA +"���� ������ �±��� �� ��� "+ServerOption.STR+"]",ChatColor.WHITE +"     "+ChatColor.BOLD+SkillList.getInt(SkillName+".SkillRank."+SkillLevel+".BonusSTR"),"",ChatColor.YELLOW + "[�� Ŭ���� ���ʽ� ���� ����]"), 30, inv);
			Stack2(ChatColor.WHITE+""+ChatColor.BOLD + "[���ʽ� "+ServerOption.DEX+"]", 261,0,1,Arrays.asList("",ChatColor.AQUA + "["+SkillName+" "+(SkillLevel-1)+" ��������",ChatColor.AQUA +"���� ������ �±��� �� ��� "+ServerOption.DEX+"]",ChatColor.WHITE +"     "+ChatColor.BOLD+SkillList.getInt(SkillName+".SkillRank."+SkillLevel+".BonusDEX") ,"",ChatColor.YELLOW + "[�� Ŭ���� ���ʽ� ���� ����]"), 31, inv);
			Stack2(ChatColor.WHITE+""+ChatColor.BOLD + "[���ʽ� "+ServerOption.INT+"]", 369,0,1,Arrays.asList("",ChatColor.AQUA + "["+SkillName+" "+(SkillLevel-1)+" ��������",ChatColor.AQUA +"���� ������ �±��� �� ��� "+ServerOption.INT+"]",ChatColor.WHITE +"     "+ChatColor.BOLD+SkillList.getInt(SkillName+".SkillRank."+SkillLevel+".BonusINT"),"",ChatColor.YELLOW + "[�� Ŭ���� ���ʽ� ���� ����]"), 32, inv);
			Stack2(ChatColor.WHITE+""+ChatColor.BOLD + "[���ʽ� "+ServerOption.WILL+"]", 370,0,1,Arrays.asList("",ChatColor.AQUA + "["+SkillName+" "+(SkillLevel-1)+" ��������",ChatColor.AQUA +"���� ������ �±��� �� ��� "+ServerOption.WILL+"]",ChatColor.WHITE +"     "+ChatColor.BOLD+SkillList.getInt(SkillName+".SkillRank."+SkillLevel+".BonusWILL") ,"",ChatColor.YELLOW + "[�� Ŭ���� ���ʽ� ���� ����]"), 33, inv);
			Stack2(ChatColor.WHITE+""+ChatColor.BOLD + "[���ʽ� "+ServerOption.LUK+"]", 322,0,1,Arrays.asList("",ChatColor.AQUA + "["+SkillName+" "+(SkillLevel-1)+" ��������",ChatColor.AQUA +"���� ������ �±��� �� ��� "+ServerOption.LUK+"]",ChatColor.WHITE +"     "+ChatColor.BOLD+SkillList.getInt(SkillName+".SkillRank."+SkillLevel+".BonusLUK") ,"",ChatColor.YELLOW + "[�� Ŭ���� ���ʽ� ���� ����]"), 34, inv);
			Stack2(ChatColor.WHITE+""+ChatColor.BOLD + "[���ʽ� �뷱��]", 283,0,1,Arrays.asList("",ChatColor.AQUA + "["+SkillName+" "+(SkillLevel-1)+" ��������",ChatColor.AQUA +"���� ������ �±��� �� ��� �뷱��]",ChatColor.WHITE +"     "+ChatColor.BOLD+SkillList.getInt(SkillName+".SkillRank."+SkillLevel+".BonusBAL") ,"",ChatColor.YELLOW + "[�� Ŭ���� ���ʽ� ���� ����]"), 37, inv);
			Stack2(ChatColor.WHITE+""+ChatColor.BOLD + "[���ʽ� ũ��Ƽ��]", 262,0,1,Arrays.asList("",ChatColor.AQUA + "["+SkillName+" "+(SkillLevel-1)+" ��������",ChatColor.AQUA +"���� ������ �±��� �� ��� ũ��Ƽ��]",ChatColor.WHITE +"     "+ChatColor.BOLD+SkillList.getInt(SkillName+".SkillRank."+SkillLevel+".BonusCRI") ,"",ChatColor.YELLOW + "[�� Ŭ���� ���ʽ� ���� ����]"), 38, inv);
			Stack2(ChatColor.WHITE+""+ChatColor.BOLD + "[���ʽ� ���]", 307,0,1,Arrays.asList("",ChatColor.AQUA + "["+SkillName+" "+(SkillLevel-1)+" ��������",ChatColor.AQUA +"���� ������ �±��� �� ��� ���]",ChatColor.WHITE +"     "+ChatColor.BOLD+SkillList.getInt(SkillName+".SkillRank."+SkillLevel+".BonusDEF") ,"",ChatColor.YELLOW + "[�� Ŭ���� ���ʽ� ���� ����]"), 39, inv);
			Stack2(ChatColor.WHITE+""+ChatColor.BOLD + "[���ʽ� ��ȣ]", 306,0,1,Arrays.asList("",ChatColor.AQUA + "["+SkillName+" "+(SkillLevel-1)+" ��������",ChatColor.AQUA +"���� ������ �±��� �� ��� ��ȣ]",ChatColor.WHITE +"     "+ChatColor.BOLD+SkillList.getInt(SkillName+".SkillRank."+SkillLevel+".BonusPRO") ,"",ChatColor.YELLOW + "[�� Ŭ���� ���ʽ� ���� ����]"), 40, inv);
			Stack2(ChatColor.WHITE+""+ChatColor.BOLD + "[���ʽ� ���� ���]", 311,0,1,Arrays.asList("",ChatColor.AQUA + "["+SkillName+" "+(SkillLevel-1)+" ��������",ChatColor.AQUA +"���� ������ �±��� �� ��� ���� ���]",ChatColor.WHITE +"     "+ChatColor.BOLD+SkillList.getInt(SkillName+".SkillRank."+SkillLevel+".BonusMDEF") ,"",ChatColor.YELLOW + "[�� Ŭ���� ���ʽ� ���� ����]"), 41, inv);
			Stack2(ChatColor.WHITE+""+ChatColor.BOLD + "[���ʽ� ���� ��ȣ]", 310,0,1,Arrays.asList("",ChatColor.AQUA + "["+SkillName+" "+(SkillLevel-1)+" ��������",ChatColor.AQUA +"���� ������ �±��� �� ��� ���� ��ȣ]",ChatColor.WHITE +"     "+ChatColor.BOLD+SkillList.getInt(SkillName+".SkillRank."+SkillLevel+".BonusMPRO") ,"",ChatColor.YELLOW + "[�� Ŭ���� ���ʽ� ���� ����]"), 42, inv);
		}
		Stack2(ChatColor.WHITE + "" + ChatColor.BOLD + "���� ���", 323,0,1,Arrays.asList(ChatColor.GRAY + "���� ȭ������ ���ư��ϴ�.",ChatColor.BLACK+""+SkillLevel), 45, inv);
		Stack2(ChatColor.WHITE + "" + ChatColor.BOLD + "�ݱ�", 324,0,1,Arrays.asList(ChatColor.GRAY + "â�� �ݽ��ϴ�.",ChatColor.BLACK+SkillName), 53, inv);
		player.openInventory(inv);
	}
	
	
	
	
	
	public void AllSkillsGUIClick(InventoryClickEvent event)
	{
		GoldBigDragon_RPG.Effect.Sound s = new GoldBigDragon_RPG.Effect.Sound();
		event.setCancelled(true);
		Player player = (Player) event.getWhoClicked();
		short page =  (short) (Short.parseShort(event.getInventory().getTitle().split(" : ")[1])-1);
		String WhatJob = ChatColor.stripColor(event.getInventory().getItem(45).getItemMeta().getLore().get(1));
		boolean isJobGUI = Boolean.parseBoolean(ChatColor.stripColor(event.getInventory().getItem(53).getItemMeta().getLore().get(1)));

		Object_UserData u = new Object_UserData();
		switch (event.getSlot())
		{
		case 45://���� ���
			s.SP(player, Sound.ITEM_PICKUP, 0.8F, 1.0F);
			if(isJobGUI==true)
			{
				if(WhatJob.equals("Maple"))
				{
					GoldBigDragon_RPG.GUI.JobGUI JGUI = new GoldBigDragon_RPG.GUI.JobGUI();
					JGUI.MapleStory_JobSetting(player, u.getString(player, (byte)3));
					u.clearAll(player);
				}
				else
				{
					GoldBigDragon_RPG.GUI.JobGUI JGUI = new GoldBigDragon_RPG.GUI.JobGUI();
					JGUI.Mabinogi_ChooseCategory(player, (short) 0);
					u.clearAll(player);
				}
			}
			else
			{
				OPBoxGUI OGUI = new OPBoxGUI();
				OGUI.OPBoxGUI_Main(player, (byte) 2);
				u.clearAll(player);
			}
			break;
		case 53://������
			s.SP(player, Sound.PISTON_RETRACT, 0.8F, 1.8F);
			player.closeInventory();
			if(isJobGUI == true && WhatJob.equals("Maple"))
				u.clearAll(player);
			break;
		case 48://���� ������
			s.SP(player, Sound.ITEM_PICKUP, 0.8F, 1.0F);
			AllSkillsGUI(player,(short) (page-1),isJobGUI,WhatJob);
			break;
		case 50://���� ������
			s.SP(player, Sound.ITEM_PICKUP, 0.8F, 1.0F);
			AllSkillsGUI(player,(short) (page+1),isJobGUI,WhatJob);
			break;
		case 49://�� ��ų
			s.SP(player, Sound.ITEM_PICKUP, 0.8F, 1.0F);
			player.closeInventory();
			player.sendMessage(ChatColor.LIGHT_PURPLE+"[��ų] : ���ο� ��ų �̸��� ������ �ּ���!");
			u.setType(player, "Skill");
			u.setString(player, (byte)1, "CS");
			break;
		default :
			if(isJobGUI == true)
			{
				String SkillName = ChatColor.stripColor(event.getCurrentItem().getItemMeta().getDisplayName());
				if(WhatJob.equals("Maple"))
				{
					s.SP(player, Sound.ITEM_PICKUP, 0.8F, 1.0F);
					YamlController YC = new YamlController(GoldBigDragon_RPG.Main.Main.plugin);
					YamlManager JobList  = YC.getNewConfig("Skill/JobList.yml");
					JobList.set("MapleStory."+u.getString(player, (byte)3)+"."+u.getString(player, (byte)2)+".Skill."+SkillName+".1", null);
					JobList.saveConfig();
					GoldBigDragon_RPG.GUI.JobGUI JGUI = new GoldBigDragon_RPG.GUI.JobGUI();
					JGUI.MapleStory_JobSetting(player, u.getString(player, (byte)3));
					u.clearAll(player);
					YamlManager Config  = YC.getNewConfig("Config.yml");
					Config.set("Time.LastSkillChanged", new GoldBigDragon_RPG.Util.Number().RandomNum(0, 100000)-new GoldBigDragon_RPG.Util.Number().RandomNum(0, 100000));
					Config.saveConfig();
					new GoldBigDragon_RPG.ETC.Job().AllPlayerFixAllSkillAndJobYML();
				}
				else
				{
					s.SP(player, Sound.ITEM_PICKUP, 0.8F, 1.0F);
					YamlController YC = new YamlController(GoldBigDragon_RPG.Main.Main.plugin);
					YamlManager JobList  = YC.getNewConfig("Skill/JobList.yml");
					JobList.set("Mabinogi.Added."+SkillName, WhatJob);
					JobList.set("Mabinogi."+WhatJob + "."+SkillName, false);
					JobList.saveConfig();
					AllSkillsGUI(player, page, isJobGUI, WhatJob);
				}
				return;
			}
			else
			{
				if(event.isShiftClick()==true&&event.isLeftClick()==true)
				{
					s.SP(player, Sound.ITEM_PICKUP, 0.8F, 1.0F);
					player.closeInventory();
					player.sendMessage(ChatColor.LIGHT_PURPLE+"[��ų] : ��ų �������� ID���� �Է� �� �ּ���!!");
					u.setType(player, "Skill");
					u.setString(player, (byte)1, "CSID");
					u.setString(player, (byte)2, ChatColor.stripColor(event.getCurrentItem().getItemMeta().getDisplayName()));
					break;
				}
				else if(event.isLeftClick()==true&&event.isRightClick()==false)
				{
					s.SP(player, Sound.ITEM_PICKUP, 0.8F, 1.0F);
					IndividualSkillOptionGUI(player, (short) 0, ChatColor.stripColor(event.getCurrentItem().getItemMeta().getDisplayName()));
				}
				else if(event.isShiftClick()==true&&event.isRightClick()==true)
				{
					s.SP(player, Sound.LAVA_POP, 0.8F, 1.0F);
					YamlController YC = new YamlController(GoldBigDragon_RPG.Main.Main.plugin);
					YamlManager Config  = YC.getNewConfig("Config.yml");
					Config.set("Time.LastSkillChanged", new GoldBigDragon_RPG.Util.Number().RandomNum(0, 100000)-new GoldBigDragon_RPG.Util.Number().RandomNum(0, 100000));
					Config.saveConfig();
					YamlManager SkillList  = YC.getNewConfig("Skill/SkillList.yml");
					SkillList.removeKey(ChatColor.stripColor(event.getCurrentItem().getItemMeta().getDisplayName()));
					SkillList.saveConfig();
					AllSkillsGUI(player, (short) (Integer.parseInt(event.getInventory().getTitle().split(" : ")[1])-1),false,"Maple");
					GoldBigDragon_RPG.ETC.Job J = new GoldBigDragon_RPG.ETC.Job();
					J.AllPlayerFixAllSkillAndJobYML();
				}
			}
			return;
		}
	}

	public void IndividualSkillOptionGUIClick(InventoryClickEvent event)
	{
		String SkillName = ChatColor.stripColor(event.getInventory().getItem(53).getItemMeta().getLore().get(1));
		short page =  (short) (Short.parseShort(event.getInventory().getTitle().split(" : ")[1])-1);
		
		YamlController YC = new YamlController(GoldBigDragon_RPG.Main.Main.plugin);
		YamlManager SkillList  = YC.getNewConfig("Skill/SkillList.yml");
		GoldBigDragon_RPG.Effect.Sound s = new GoldBigDragon_RPG.Effect.Sound();

		short size= (short) SkillList.getConfigurationSection(SkillName+".SkillRank").getKeys(false).size();
		event.setCancelled(true);
		Player player = (Player) event.getWhoClicked();
		switch (event.getSlot())
		{
		case 45://���� ���
			s.SP(player, Sound.ITEM_PICKUP, 0.8F, 1.0F);
			AllSkillsGUI(player, (short) 0,false,"Maple");
			break;
		case 53://������
			s.SP(player, Sound.PISTON_RETRACT, 0.8F, 1.8F);
			player.closeInventory();
			break;
		case 48://���� ������
			s.SP(player, Sound.ITEM_PICKUP, 0.8F, 1.0F);
			IndividualSkillOptionGUI(player,(short) (page-1),SkillName);
			break;
		case 50://���� ������
			s.SP(player, Sound.ITEM_PICKUP, 0.8F, 1.0F);
			IndividualSkillOptionGUI(player,(short) (page+1),SkillName);
			break;
		case 49://�� ��ũ
			s.SP(player, Sound.ITEM_PICKUP, 0.8F, 1.0F);
			SkillList.set(SkillName+".SkillRank."+(size+1)+".Command","null");
			SkillList.set(SkillName+".SkillRank."+(size+1)+".BukkitPermission",false);
			SkillList.set(SkillName+".SkillRank."+(size+1)+".MagicSpells","null");
			SkillList.set(SkillName+".SkillRank."+(size+1)+".SkillPoint",1000);
			SkillList.saveConfig();
			IndividualSkillOptionGUI(player,  page, SkillName);
			break;
		default :
			if(event.isLeftClick()==true&&event.isRightClick()==false)
			{
				s.SP(player, Sound.ITEM_PICKUP, 0.8F, 1.0F);
				SkillRankOptionGUI(player,SkillName,(short) ((page*45)+event.getSlot()+1));
			}
			else if(event.isShiftClick()==true&&event.isRightClick()==true&&(page*45)+event.getSlot()!=0&&(page*45)+event.getSlot()+1==size)
			{
				YamlManager Config  = YC.getNewConfig("Config.yml");
				Config.set("Time.LastSkillChanged", new GoldBigDragon_RPG.Util.Number().RandomNum(0, 100000)-new GoldBigDragon_RPG.Util.Number().RandomNum(0, 100000));
				Config.saveConfig();
				s.SP(player, Sound.LAVA_POP, 0.8F, 1.0F);
				SkillList.removeKey(SkillName+".SkillRank."+(size));
				SkillList.saveConfig();
				IndividualSkillOptionGUI(player, page,SkillName);
				GoldBigDragon_RPG.ETC.Job J = new GoldBigDragon_RPG.ETC.Job();
				J.AllPlayerSkillRankFix();
			}
			return;
		}
	}
	
	public void SkillRankOptionGUIClick(InventoryClickEvent event)
	{
		String SkillName = ChatColor.stripColor(event.getInventory().getItem(53).getItemMeta().getLore().get(1));
		short SkillLevel = Short.parseShort(ChatColor.stripColor(event.getInventory().getItem(45).getItemMeta().getLore().get(1)));
		
		YamlController YC = new YamlController(GoldBigDragon_RPG.Main.Main.plugin);
		YamlManager SkillList  = YC.getNewConfig("Skill/SkillList.yml");
		GoldBigDragon_RPG.Effect.Sound s = new GoldBigDragon_RPG.Effect.Sound();

		Object_UserData u = new Object_UserData();
		
		event.setCancelled(true);
		Player player = (Player) event.getWhoClicked();
		switch (event.getSlot())
		{
		case 3://�ʿ� ����
		{
			s.SP(player, Sound.ITEM_PICKUP, 0.8F, 1.0F);
			player.closeInventory();
			player.sendMessage(ChatColor.LIGHT_PURPLE+"[��ų] : ��ų�� ��� �� �ִ� ������ ������ �ּ���!");
			player.sendMessage(ChatColor.LIGHT_PURPLE+"[���� ���� : 0] [�ִ� : "+Integer.MAX_VALUE+"]");
			u.setType(player, "Skill");
			u.setString(player, (byte)1, "NeedLV");
			u.setString(player, (byte)2, SkillName);
			u.setInt(player, (byte)4,SkillLevel);
		}
		return;
		case 4://�ʿ� ��ų ����Ʈ
			{
				s.SP(player, Sound.ITEM_PICKUP, 0.8F, 1.0F);
				player.closeInventory();
				player.sendMessage(ChatColor.LIGHT_PURPLE+"[��ų] : �ʿ��� ��ų ����Ʈ�� ������ �ּ���!");
				player.sendMessage(ChatColor.LIGHT_PURPLE+"[�ּ� : 0] [�ִ� : "+Byte.MAX_VALUE+"]");
				u.setType(player, "Skill");
				u.setString(player, (byte)1, "SP");
				u.setString(player, (byte)2, SkillName);
				u.setInt(player, (byte)4,SkillLevel);
			}
			return;
		case 11://Ŀ�ǵ� ����
			{
				s.SP(player, Sound.ITEM_PICKUP, 0.8F, 1.0F);
				player.sendMessage(ChatColor.DARK_AQUA+"[��ų] : /Ŀ�ǵ� [������ Ŀ�ǵ� �Է�]");
				player.sendMessage(ChatColor.LIGHT_PURPLE+"  /Ŀ�ǵ� ����"+ChatColor.WHITE+" �Է½� Ŀ�ǵ� ����");
				u.setType(player, "Skill");
				u.setString(player, (byte)1, "SKC");
				u.setString(player, (byte)2, SkillName);
				u.setInt(player, (byte)4,SkillLevel);
				player.closeInventory();
			}
			return;
		case 13://Ŀ�ǵ� ����
			s.SP(player, Sound.ITEM_PICKUP, 0.8F, 1.0F);
			if(SkillList.getBoolean(SkillName+".SkillRank."+SkillLevel+".BukkitPermission") == true)
				SkillList.set(SkillName+".SkillRank."+SkillLevel+".BukkitPermission", false);
			else
				SkillList.set(SkillName+".SkillRank."+SkillLevel+".BukkitPermission", true);
			SkillList.saveConfig();
			SkillRankOptionGUI(player, SkillName, SkillLevel);
			return;
		case 15://���� ����
			if(Bukkit.getPluginManager().isPluginEnabled("MagicSpells") == true)
			{
				s.SP(player, Sound.HORSE_ARMOR, 0.8F, 1.9F);
				player.closeInventory();
				new OtherPlugins.SpellMain().ShowAllMaigcGUI(player, (short)0,SkillName,SkillLevel,(byte)0);
			}
			else
				s.SP(player, Sound.ANVIL_LAND, 1.0F, 1.8F);
			return;
		case 19://���� ���� ����
			s.SP(player, Sound.ITEM_PICKUP, 0.8F, 1.0F);
			String DistrictWeapon = SkillList.getString(SkillName+".SkillRank."+SkillLevel+".DistrictWeapon");
			YamlManager Target = YC.getNewConfig("Item/CustomType.yml");

		  	Object[] Type = Target.getKeys().toArray();
		  	if(Type.length==0)
		  	{
				switch(DistrictWeapon)
				{
					case "����":
						SkillList.set(SkillName+".SkillRank."+SkillLevel+".DistrictWeapon", "���� ����");
						break;
					case "���� ����":
						SkillList.set(SkillName+".SkillRank."+SkillLevel+".DistrictWeapon", "�Ѽ� ��");
						break;
					case "�Ѽ� ��":
						SkillList.set(SkillName+".SkillRank."+SkillLevel+".DistrictWeapon", "��� ��");
						break;
					case "��� ��":
						SkillList.set(SkillName+".SkillRank."+SkillLevel+".DistrictWeapon", "����");
						break;
					case "����":
						SkillList.set(SkillName+".SkillRank."+SkillLevel+".DistrictWeapon", "��");
						break;
					case "��":
						SkillList.set(SkillName+".SkillRank."+SkillLevel+".DistrictWeapon", "���Ÿ� ����");
						break;
					case "���Ÿ� ����":
						SkillList.set(SkillName+".SkillRank."+SkillLevel+".DistrictWeapon", "Ȱ");
						break;
					case "Ȱ":
						SkillList.set(SkillName+".SkillRank."+SkillLevel+".DistrictWeapon", "����");
						break;
					case "����":
						SkillList.set(SkillName+".SkillRank."+SkillLevel+".DistrictWeapon", "���� ����");
						break;
					case "���� ����":
						SkillList.set(SkillName+".SkillRank."+SkillLevel+".DistrictWeapon", "����");
						break;
					case "����":
						SkillList.set(SkillName+".SkillRank."+SkillLevel+".DistrictWeapon", "������");
						break;
					case "������":
						SkillList.set(SkillName+".SkillRank."+SkillLevel+".DistrictWeapon", "����");
						break;
					default:
						SkillList.set(SkillName+".SkillRank."+SkillLevel+".DistrictWeapon", "����");
						break;
				}
		  	}
		  	else
		  	{
				switch(DistrictWeapon)
				{
					case "����":
						SkillList.set(SkillName+".SkillRank."+SkillLevel+".DistrictWeapon", "���� ����");
						break;
					case "���� ����":
						SkillList.set(SkillName+".SkillRank."+SkillLevel+".DistrictWeapon", "�Ѽ� ��");
						break;
					case "�Ѽ� ��":
						SkillList.set(SkillName+".SkillRank."+SkillLevel+".DistrictWeapon", "��� ��");
						break;
					case "��� ��":
						SkillList.set(SkillName+".SkillRank."+SkillLevel+".DistrictWeapon", "����");
						break;
					case "����":
						SkillList.set(SkillName+".SkillRank."+SkillLevel+".DistrictWeapon", "��");
						break;
					case "��":
						SkillList.set(SkillName+".SkillRank."+SkillLevel+".DistrictWeapon", "���Ÿ� ����");
						break;
					case "���Ÿ� ����":
						SkillList.set(SkillName+".SkillRank."+SkillLevel+".DistrictWeapon", "Ȱ");
						break;
					case "Ȱ":
						SkillList.set(SkillName+".SkillRank."+SkillLevel+".DistrictWeapon", "����");
						break;
					case "����":
						SkillList.set(SkillName+".SkillRank."+SkillLevel+".DistrictWeapon", "���� ����");
						break;
					case "���� ����":
						SkillList.set(SkillName+".SkillRank."+SkillLevel+".DistrictWeapon", "����");
						break;
					case "����":
						SkillList.set(SkillName+".SkillRank."+SkillLevel+".DistrictWeapon", "������");
						break;
					case "������":
						SkillList.set(SkillName+".SkillRank."+SkillLevel+".DistrictWeapon", Type[0].toString());
						break;
					default:
						{
							boolean isExit = false;
							for(int count = 0; count < Type.length; count++)
							{
								if((SkillList.getString(SkillName+".SkillRank."+SkillLevel+".DistrictWeapon").contains(Type[count].toString())))
								{
									if(count+1 == Type.length)
										SkillList.set(SkillName+".SkillRank."+SkillLevel+".DistrictWeapon","����");
									else
										SkillList.set(SkillName+".SkillRank."+SkillLevel+".DistrictWeapon",ChatColor.WHITE+Type[(count+1)].toString());
									isExit = true;
									break;
								}
							}
							if(isExit==false)
								SkillList.set(SkillName+".SkillRank."+SkillLevel+".DistrictWeapon","����");
						}
						break;
				}
		  	}
			SkillList.saveConfig();
			SkillRankOptionGUI(player, SkillName, SkillLevel);
			return;
		case 21://��ų ����� ���
			s.SP(player, Sound.ITEM_PICKUP, 0.8F, 1.0F);
			if(Bukkit.getPluginManager().isPluginEnabled("MagicSpells") == true)
			{
				String switchNeed = SkillList.getString(SkillName+".SkillRank."+SkillLevel+".AffectStat");
				if(switchNeed.compareTo("����")==0)
					SkillList.set(SkillName+".SkillRank."+SkillLevel+".AffectStat", "�����");
				else if(switchNeed.compareTo("�����")==0)
					SkillList.set(SkillName+".SkillRank."+SkillLevel+".AffectStat", "����");
				else if(switchNeed.compareTo("����")==0)
					SkillList.set(SkillName+".SkillRank."+SkillLevel+".AffectStat", ServerOption.STR);
				else if(switchNeed.compareTo(ServerOption.STR)==0)
					SkillList.set(SkillName+".SkillRank."+SkillLevel+".AffectStat", ServerOption.DEX);
				else if(switchNeed.compareTo(ServerOption.DEX)==0)
					SkillList.set(SkillName+".SkillRank."+SkillLevel+".AffectStat", ServerOption.INT);
				else if(switchNeed.compareTo(ServerOption.INT)==0)
					SkillList.set(SkillName+".SkillRank."+SkillLevel+".AffectStat", ServerOption.WILL);
				else if(switchNeed.compareTo(ServerOption.WILL)==0)
					SkillList.set(SkillName+".SkillRank."+SkillLevel+".AffectStat", ServerOption.LUK);
				else
					SkillList.set(SkillName+".SkillRank."+SkillLevel+".AffectStat", "����");

				SkillList.saveConfig();
				SkillRankOptionGUI(player, SkillName, SkillLevel);
			}
			else
			{
				s.SP(player, Sound.ANVIL_LAND, 1.0F, 1.8F);
			}
			return;

		case 23://��ų ����
			player.sendMessage(ChatColor.LIGHT_PURPLE+"[��ų] : ��ų ������ �ۼ� �� �ּ���!");
			player.sendMessage(ChatColor.GOLD + "%enter%"+ChatColor.WHITE + " - ���� ��� ���� -");
			player.sendMessage(ChatColor.WHITE + ""+ChatColor.BOLD + "&l " + ChatColor.BLACK + "&0 "+ChatColor.DARK_BLUE+"&1 "+ChatColor.DARK_GREEN+"&2 "+
			ChatColor.DARK_AQUA + "&3 " +ChatColor.DARK_RED + "&4 " + ChatColor.DARK_PURPLE + "&5 " +
					ChatColor.GOLD + "&6 " + ChatColor.GRAY + "&7 " + ChatColor.DARK_GRAY + "&8 " +
			ChatColor.BLUE + "&9 " + ChatColor.GREEN + "&a " + ChatColor.AQUA + "&b " + ChatColor.RED + "&c" +
					ChatColor.LIGHT_PURPLE + "&d " + ChatColor.YELLOW + "&e "+ChatColor.WHITE + "&f");
			u.setType(player, "Skill");
			u.setString(player, (byte)1, "SKL");
			u.setString(player, (byte)2, SkillName);
			u.setInt(player, (byte)4,SkillLevel);
			player.closeInventory();
			return;
		case 28://���ʽ� �����
			player.sendMessage(ChatColor.LIGHT_PURPLE+"[��ų] : ���ʽ� ����� ��ġ�� �Է��� �ּ���!");
			u.setType(player, "Skill");
			u.setString(player, (byte)1, "BH");
			break;
		case 29://���ʽ� ����
			player.sendMessage(ChatColor.LIGHT_PURPLE+"[��ų] : ���ʽ� ���� ��ġ�� �Է��� �ּ���!");
			u.setType(player, "Skill");
			u.setString(player, (byte)1, "BM");
			break;
		case 30://���ʽ� "+GoldBigDragon_RPG.ServerOption.STR+"
			player.sendMessage(ChatColor.LIGHT_PURPLE+"[��ų] : ���ʽ� "+ServerOption.STR+" ��ġ�� �Է��� �ּ���!");
			u.setType(player, "Skill");
			u.setString(player, (byte)1, "BSTR");
			break;
		case 31://���ʽ� "+GoldBigDragon_RPG.ServerOption.DEX+"
			player.sendMessage(ChatColor.LIGHT_PURPLE+"[��ų] : ���ʽ� "+ServerOption.DEX+" ��ġ�� �Է��� �ּ���!");
			u.setType(player, "Skill");
			u.setString(player, (byte)1, "BDEX");
			break;
		case 32://���ʽ� "+GoldBigDragon_RPG.ServerOption.INT+"
			player.sendMessage(ChatColor.LIGHT_PURPLE+"[��ų] : ���ʽ� "+ServerOption.INT+" ��ġ�� �Է��� �ּ���!");
			u.setType(player, "Skill");
			u.setString(player, (byte)1, "BINT");
			break;
		case 33://���ʽ� "+GoldBigDragon_RPG.ServerOption.WILL+"
			player.sendMessage(ChatColor.LIGHT_PURPLE+"[��ų] : ���ʽ� "+ServerOption.WILL+" ��ġ�� �Է��� �ּ���!");
			u.setType(player, "Skill");
			u.setString(player, (byte)1, "BWILL");
			break;
		case 34://���ʽ� "+GoldBigDragon_RPG.ServerOption.LUK+"
			player.sendMessage(ChatColor.LIGHT_PURPLE+"[��ų] : ���ʽ� "+ServerOption.LUK+" ��ġ�� �Է��� �ּ���!");
			u.setType(player, "Skill");
			u.setString(player, (byte)1, "BLUK");
			break;
		case 37://���ʽ� �뷱��
			player.sendMessage(ChatColor.LIGHT_PURPLE+"[��ų] : ���ʽ� �뷱�� ��ġ�� �Է��� �ּ���!");
			u.setType(player, "Skill");
			u.setString(player, (byte)1, "BBAL");
			break;
		case 38://���ʽ� ũ��Ƽ��
			player.sendMessage(ChatColor.LIGHT_PURPLE+"[��ų] : ���ʽ� ũ��Ƽ�� ��ġ�� �Է��� �ּ���!");
			u.setType(player, "Skill");
			u.setString(player, (byte)1, "BCRI");
			break;
		case 39://���ʽ� ���
			player.sendMessage(ChatColor.LIGHT_PURPLE+"[��ų] : ���ʽ� ��� ��ġ�� �Է��� �ּ���!");
			u.setType(player, "Skill");
			u.setString(player, (byte)1, "BDEF");
			break;
		case 40://���ʽ� ��ȣ
			player.sendMessage(ChatColor.LIGHT_PURPLE+"[��ų] : ���ʽ� ��ȣ ��ġ�� �Է��� �ּ���!");
			u.setType(player, "Skill");
			u.setString(player, (byte)1, "BPRO");
			break;
		case 41://���ʽ� ���� ���
			player.sendMessage(ChatColor.LIGHT_PURPLE+"[��ų] : ���ʽ� ���� ��� ��ġ�� �Է��� �ּ���!");
			u.setType(player, "Skill");
			u.setString(player, (byte)1, "BMDEF");
			break;
		case 42://���ʽ� ���� ��ȣ
			player.sendMessage(ChatColor.LIGHT_PURPLE+"[��ų] : ���ʽ� ���� ��ȣ ��ġ�� �Է��� �ּ���!");
			u.setType(player, "Skill");
			u.setString(player, (byte)1, "BMPRO");
			break;
		case 45://���� ���
			s.SP(player, Sound.ITEM_PICKUP, 0.8F, 1.0F);
			IndividualSkillOptionGUI(player, (short) 0, SkillName);
			return;
		case 53://������
			s.SP(player, Sound.PISTON_RETRACT, 0.8F, 1.8F);
			player.closeInventory();
			return;
		}
		player.closeInventory();
		player.sendMessage(ChatColor.LIGHT_PURPLE+"[�ּ� : "+Byte.MIN_VALUE+"] [�ִ� : "+Byte.MAX_VALUE+"]");
		s.SP(player, Sound.ITEM_PICKUP, 0.8F, 1.0F);
		u.setType(player, "Skill");
		u.setString(player, (byte)2, SkillName);
		u.setInt(player, (byte)4,SkillLevel);
	}

}
