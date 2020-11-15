package GoldBigDragon_RPG.Skill;

import java.util.Arrays;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Sound;
import org.bukkit.inventory.*;
import org.bukkit.inventory.meta.*;
import org.bukkit.material.*;

import GoldBigDragon_RPG.GUI.GUIutil;
import GoldBigDragon_RPG.GUI.StatsGUI;
import GoldBigDragon_RPG.Util.YamlController;
import GoldBigDragon_RPG.Util.YamlManager;

import org.bukkit.entity.*;
import org.bukkit.event.inventory.InventoryClickEvent;

public class PlayerSkillGUI extends GUIutil
{
	public void MainSkillsListGUI(Player player, short page)
	{
		YamlController YC = new YamlController(GoldBigDragon_RPG.Main.Main.plugin);
		YamlManager Config = YC.getNewConfig("config.yml");
		YamlManager PlayerSkillList  = YC.getNewConfig("Skill/PlayerData/"+player.getUniqueId().toString()+".yml");
		Inventory inv = null;
		if(Config.getBoolean("Server.Like_The_Mabinogi_Online_Stat_System") == false)
		{
			inv = Bukkit.createInventory(null, 54, ChatColor.BLACK + "������ ���� : " + (page+1));
			Object[] a= PlayerSkillList.getConfigurationSection("MapleStory").getKeys(false).toArray();

			byte loc=0;
			for(int count = page*45; count < a.length;count++)
			{
				String JobName = a[count].toString();
				short SkillAmount= (short) PlayerSkillList.getConfigurationSection("MapleStory."+a[count].toString()+".Skill").getKeys(false).size();
				if(count > a.length || loc >= 45) break;
			
				Stack2(ChatColor.WHITE + "" + ChatColor.BOLD + JobName,  340,0,1,Arrays.asList(ChatColor.DARK_AQUA+"��ų ���� : " + ChatColor.WHITE+SkillAmount +ChatColor.DARK_AQUA+" ��"), loc, inv);
				loc++;
			}
			if(a.length-(page*44)>45)
				Stack2(ChatColor.WHITE + "" + ChatColor.BOLD + "���� ������", 323,0,1,Arrays.asList(ChatColor.GRAY + "���� �������� �̵� �մϴ�."), 50, inv);
			if(page!=0)
				Stack2(ChatColor.WHITE + "" + ChatColor.BOLD + "���� ������", 323,0,1,Arrays.asList(ChatColor.GRAY + "���� �������� �̵� �մϴ�."), 48, inv);
		}
		else
		{
			inv = Bukkit.createInventory(null, 54, ChatColor.BLACK + "ī�װ� ���� : " + (page+1));
			Object[] Categori= PlayerSkillList.getConfigurationSection("Mabinogi").getKeys(false).toArray();

			byte loc=0;
			for(int count = (short) (page*45); count < Categori.length;count++)
			{
				String CategoriName = Categori[count].toString();
				short SkillAmount= (short) PlayerSkillList.getConfigurationSection("Mabinogi."+Categori[count].toString()).getKeys(false).size();
				if(count > Categori.length || loc >= 45) break;
			
				Stack2(ChatColor.WHITE + "" + ChatColor.BOLD + CategoriName,  340,0,1,Arrays.asList(ChatColor.DARK_AQUA+"��ų ���� : " + ChatColor.WHITE+SkillAmount +ChatColor.DARK_AQUA+" ��"), loc, inv);
				loc++;
			}
			if(Categori.length-(page*44)>45)
				Stack2(ChatColor.WHITE + "" + ChatColor.BOLD + "���� ������", 323,0,1,Arrays.asList(ChatColor.GRAY + "���� �������� �̵� �մϴ�."), 50, inv);
			if(page!=0)
				Stack2(ChatColor.WHITE + "" + ChatColor.BOLD + "���� ������", 323,0,1,Arrays.asList(ChatColor.GRAY + "���� �������� �̵� �մϴ�."), 48, inv);
		}

		Stack2(ChatColor.WHITE + "" + ChatColor.BOLD + "���� ���", 323,0,1,Arrays.asList(ChatColor.GRAY + "���� ȭ������ ���ư��ϴ�."), 45, inv);
		Stack2(ChatColor.WHITE + "" + ChatColor.BOLD + "�ݱ�", 324,0,1,Arrays.asList(ChatColor.GRAY + "â�� �ݽ��ϴ�."), 53, inv);
		player.openInventory(inv);
	}
	
	public void SkillListGUI(Player player, short page,boolean isMabinogi, String CategoriName)
	{
		YamlController YC = new YamlController(GoldBigDragon_RPG.Main.Main.plugin);
		YamlManager Config = YC.getNewConfig("config.yml");
		YamlManager PlayerSkillList  = YC.getNewConfig("Skill/PlayerData/"+player.getUniqueId().toString()+".yml");
		YamlManager AllSkillList  = YC.getNewConfig("Skill/SkillList.yml");
		Inventory inv = null;
		if(Config.getBoolean("Server.Like_The_Mabinogi_Online_Stat_System") == false)
		{
			inv = Bukkit.createInventory(null, 54, ChatColor.BLACK + "���� ��ų ��� : " + (page+1));
			Object[] a= PlayerSkillList.getConfigurationSection("MapleStory."+CategoriName+".Skill").getKeys(false).toArray();

			byte loc=0;
			for(int count = page*45; count < a.length;count++)
			{
				short SkillRank= (short) PlayerSkillList.getInt("MapleStory."+CategoriName+".Skill."+a[count]);
				short SkillMaxRank = (short) AllSkillList.getConfigurationSection(a[count]+".SkillRank").getKeys(false).size();
				int IConID = AllSkillList.getInt(a[count]+".ID");
				byte IConDATA = (byte) AllSkillList.getInt(a[count]+".DATA");
				byte IConAmount = (byte) AllSkillList.getInt(a[count]+".Amount");
				if(count > a.length || loc >= 45) break;
			
				String Skilllore = ChatColor.DARK_AQUA+"��ų ��ũ : " +SkillRank +" / "+SkillMaxRank+"%enter%"+AllSkillList.getString(a[count]+".SkillRank."+SkillRank+".Lore");
				Skilllore = Skilllore +"%enter%%enter%"+ChatColor.YELLOW + "[�� Ŭ���� ����Ű ���]";

				if(SkillRank<SkillMaxRank)
				{
					Skilllore = Skilllore +"%enter%"+ ChatColor.YELLOW + "[Shift + �� Ŭ���� ��ũ ��]%enter%";
					if(GoldBigDragon_RPG.Main.ServerOption.PlayerList.get(player.getUniqueId().toString()).getStat_SkillPoint() < AllSkillList.getInt(a[count]+".SkillRank."+(SkillRank+1)+".SkillPoint"))
					{
						Skilllore = Skilllore +"%enter%"+ ChatColor.RED + "�ʿ� ��ų ����Ʈ : "+AllSkillList.getInt(a[count]+".SkillRank."+(SkillRank+1)+".SkillPoint");
						Skilllore = Skilllore +"%enter%"+ ChatColor.RED + "���� ��ų ����Ʈ : "+GoldBigDragon_RPG.Main.ServerOption.PlayerList.get(player.getUniqueId().toString()).getStat_SkillPoint();
					}
					else
					{
						Skilllore = Skilllore +"%enter%"+ ChatColor.GREEN + "�ʿ� ��ų ����Ʈ : "+AllSkillList.getInt(a[count]+".SkillRank."+(SkillRank+1)+".SkillPoint");
						Skilllore = Skilllore +"%enter%"+ ChatColor.GREEN + "���� ��ų ����Ʈ : "+GoldBigDragon_RPG.Main.ServerOption.PlayerList.get(player.getUniqueId().toString()).getStat_SkillPoint();
					}
					if(AllSkillList.getInt(a[count]+".SkillRank."+(SkillRank+1)+".NeedLevel") > 0)
						if(GoldBigDragon_RPG.Main.ServerOption.PlayerList.get(player.getUniqueId().toString()).getStat_Level() < AllSkillList.getInt(a[count]+".SkillRank."+(SkillRank+1)+".NeedLevel"))
						{
							Skilllore = Skilllore +"%enter%"+ ChatColor.RED + "�ּ� ���� : "+AllSkillList.getInt(a[count]+".SkillRank."+(SkillRank+1)+".NeedLevel");
							Skilllore = Skilllore +"%enter%"+ ChatColor.RED + "���� ���� : "+GoldBigDragon_RPG.Main.ServerOption.PlayerList.get(player.getUniqueId().toString()).getStat_Level();
						}
						else
						{
							Skilllore = Skilllore +"%enter%"+ ChatColor.GREEN + "�ּ� ���� : "+AllSkillList.getInt(a[count]+".SkillRank."+(SkillRank+1)+".NeedLevel");
							Skilllore = Skilllore +"%enter%"+ ChatColor.GREEN + "���� ���� : "+GoldBigDragon_RPG.Main.ServerOption.PlayerList.get(player.getUniqueId().toString()).getStat_Level();
						}
					if(AllSkillList.getInt(a[count]+".SkillRank."+(SkillRank+1)+".NeedRealLevel") > 0)
						if(GoldBigDragon_RPG.Main.ServerOption.PlayerList.get(player.getUniqueId().toString()).getStat_RealLevel() < AllSkillList.getInt(a[count]+".SkillRank."+(SkillRank+1)+".NeedRealLevel"))
						{
							Skilllore = Skilllore +"%enter%"+ ChatColor.RED + "�ּ� ���� ���� : "+AllSkillList.getInt(a[count]+".SkillRank."+(SkillRank+1)+".NeedRealLevel");
							Skilllore = Skilllore +"%enter%"+ ChatColor.RED + "���� ���� ���� : "+GoldBigDragon_RPG.Main.ServerOption.PlayerList.get(player.getUniqueId().toString()).getStat_RealLevel();
						}
						else
						{
							Skilllore = Skilllore +"%enter%"+ ChatColor.GREEN + "�ּ� ���� ���� : "+AllSkillList.getInt(a[count]+".SkillRank."+(SkillRank+1)+".NeedRealLevel");
							Skilllore = Skilllore +"%enter%"+ ChatColor.GREEN + "���� ���� ���� : "+GoldBigDragon_RPG.Main.ServerOption.PlayerList.get(player.getUniqueId().toString()).getStat_RealLevel();
						}
				}
				
				String[] scriptA = Skilllore.split("%enter%");
				for(byte counter = 0; counter < scriptA.length; counter++)
					scriptA[counter] =  scriptA[counter];
				Stack2(ChatColor.WHITE + "" + ChatColor.BOLD + a[count],  IConID,IConDATA,IConAmount,Arrays.asList(scriptA), loc, inv);
				
				loc++;
			}
			if(a.length-(page*44)>45)
				Stack2(ChatColor.WHITE + "" + ChatColor.BOLD + "���� ������", 323,0,1,Arrays.asList(ChatColor.GRAY + "���� �������� �̵� �մϴ�."), 50, inv);
			if(page!=0)
				Stack2(ChatColor.WHITE + "" + ChatColor.BOLD + "���� ������", 323,0,1,Arrays.asList(ChatColor.GRAY + "���� �������� �̵� �մϴ�."), 48, inv);

		}
		else
		{
			inv = Bukkit.createInventory(null, 54, ChatColor.BLACK + "���� ��ų ��� : " + (page+1));
			Object[] a= PlayerSkillList.getConfigurationSection("Mabinogi."+CategoriName).getKeys(false).toArray();

			byte loc=0;
			for(int count = page*45; count < a.length;count++)
			{
				short SkillRank= (short) PlayerSkillList.getInt("Mabinogi."+CategoriName+"."+a[count]);
				short SkillMaxRank = (short) AllSkillList.getConfigurationSection(a[count]+".SkillRank").getKeys(false).size();
				int IConID = AllSkillList.getInt(a[count]+".ID");
				byte IConDATA = (byte) AllSkillList.getInt(a[count]+".DATA");
				byte IConAmount = (byte) AllSkillList.getInt(a[count]+".Amount");
				if(count > a.length || loc >= 45) break;
			
				String Skilllore = ChatColor.DARK_AQUA+"��ų ��ũ : " +SkillRank +" / "+SkillMaxRank+"%enter%"+AllSkillList.getString(a[count]+".SkillRank."+SkillRank+".Lore");
				Skilllore = Skilllore +"%enter%%enter%"+ChatColor.YELLOW + "[�� Ŭ���� ����Ű ���]";

				if(SkillRank<SkillMaxRank)
				{
					Skilllore = Skilllore +"%enter%"+ ChatColor.YELLOW + "[Shift + �� Ŭ���� ��ũ ��]%enter%";
					if(GoldBigDragon_RPG.Main.ServerOption.PlayerList.get(player.getUniqueId().toString()).getStat_SkillPoint() < AllSkillList.getInt(a[count]+".SkillRank."+(SkillRank+1)+".SkillPoint"))
					{
						Skilllore = Skilllore +"%enter%"+ ChatColor.RED + "�ʿ� ��ų ����Ʈ : "+AllSkillList.getInt(a[count]+".SkillRank."+(SkillRank+1)+".SkillPoint");
						Skilllore = Skilllore +"%enter%"+ ChatColor.RED + "���� ��ų ����Ʈ : "+GoldBigDragon_RPG.Main.ServerOption.PlayerList.get(player.getUniqueId().toString()).getStat_SkillPoint();
					}
					else
					{
						Skilllore = Skilllore +"%enter%"+ ChatColor.GREEN + "�ʿ� ��ų ����Ʈ : "+AllSkillList.getInt(a[count]+".SkillRank."+(SkillRank+1)+".SkillPoint");
						Skilllore = Skilllore +"%enter%"+ ChatColor.GREEN + "���� ��ų ����Ʈ : "+GoldBigDragon_RPG.Main.ServerOption.PlayerList.get(player.getUniqueId().toString()).getStat_SkillPoint();
					}
					if(AllSkillList.getInt(a[count]+".SkillRank."+(SkillRank+1)+".NeedLevel") > 0)
						if(GoldBigDragon_RPG.Main.ServerOption.PlayerList.get(player.getUniqueId().toString()).getStat_Level() < AllSkillList.getInt(a[count]+".SkillRank."+(SkillRank+1)+".NeedLevel"))
						{
							Skilllore = Skilllore +"%enter%"+ ChatColor.RED + "�ּ� ���� : "+AllSkillList.getInt(a[count]+".SkillRank."+(SkillRank+1)+".NeedLevel");
							Skilllore = Skilllore +"%enter%"+ ChatColor.RED + "���� ���� : "+GoldBigDragon_RPG.Main.ServerOption.PlayerList.get(player.getUniqueId().toString()).getStat_Level();
						}
						else
						{
							Skilllore = Skilllore +"%enter%"+ ChatColor.GREEN + "�ּ� ���� : "+AllSkillList.getInt(a[count]+".SkillRank."+(SkillRank+1)+".NeedLevel");
							Skilllore = Skilllore +"%enter%"+ ChatColor.GREEN + "���� ���� : "+GoldBigDragon_RPG.Main.ServerOption.PlayerList.get(player.getUniqueId().toString()).getStat_Level();
						}
					if(AllSkillList.getInt(a[count]+".SkillRank."+(SkillRank+1)+".NeedRealLevel") > 0)
						if(GoldBigDragon_RPG.Main.ServerOption.PlayerList.get(player.getUniqueId().toString()).getStat_RealLevel() < AllSkillList.getInt(a[count]+".SkillRank."+(SkillRank+1)+".NeedRealLevel"))
						{
							Skilllore = Skilllore +"%enter%"+ ChatColor.RED + "�ּ� ���� ���� : "+AllSkillList.getInt(a[count]+".SkillRank."+(SkillRank+1)+".NeedRealLevel");
							Skilllore = Skilllore +"%enter%"+ ChatColor.RED + "���� ���� ���� : "+GoldBigDragon_RPG.Main.ServerOption.PlayerList.get(player.getUniqueId().toString()).getStat_RealLevel();
						}
						else
						{
							Skilllore = Skilllore +"%enter%"+ ChatColor.GREEN + "�ּ� ���� ���� : "+AllSkillList.getInt(a[count]+".SkillRank."+(SkillRank+1)+".NeedRealLevel");
							Skilllore = Skilllore +"%enter%"+ ChatColor.GREEN + "���� ���� ���� : "+GoldBigDragon_RPG.Main.ServerOption.PlayerList.get(player.getUniqueId().toString()).getStat_RealLevel();
						}
				}
				String[] scriptA = Skilllore.split("%enter%");
				for(byte counter = 0; counter < scriptA.length; counter++)
					scriptA[counter] =  scriptA[counter];
				Stack2(ChatColor.WHITE + "" + ChatColor.BOLD + a[count],  IConID,IConDATA,IConAmount,Arrays.asList(scriptA), loc, inv);
				
				loc++;
			}
			if(a.length-(page*44)>45)
				Stack2(ChatColor.WHITE + "" + ChatColor.BOLD + "���� ������", 323,0,1,Arrays.asList(ChatColor.GRAY + "���� �������� �̵� �մϴ�."), 50, inv);
			if(page!=0)
				Stack2(ChatColor.WHITE + "" + ChatColor.BOLD + "���� ������", 323,0,1,Arrays.asList(ChatColor.GRAY + "���� �������� �̵� �մϴ�."), 48, inv);
		}
		Stack2(ChatColor.WHITE + "" + ChatColor.BOLD + "���� ���", 323,0,1,Arrays.asList(ChatColor.GRAY + "���� ȭ������ ���ư��ϴ�.",ChatColor.BLACK + CategoriName), 45, inv);
		Stack2(ChatColor.WHITE + "" + ChatColor.BOLD + "�ݱ�", 324,0,1,Arrays.asList(ChatColor.GRAY + "â�� �ݽ��ϴ�.",ChatColor.BLACK+""+isMabinogi), 53, inv);
		player.openInventory(inv);
	}
	
	public void AddQuickBarGUI(Player player,boolean isMabinogi,  String CategoriName, String Skillname)
	{
		Inventory inv = Bukkit.createInventory(null, 18, ChatColor.BLACK + "������ ���");

		for(byte count = 0;count < 9;count++)
		{
			if(player.getInventory().getItem(count) == null)
				Stack2(ChatColor.DARK_AQUA + "" + ChatColor.BOLD + "|||||||||||||||||||||[�� ����]|||||||||||||||||||||", 160,9,1,Arrays.asList("",ChatColor.YELLOW +""+ChatColor.BOLD+ "[Ŭ���� �ֹٿ� ��� �˴ϴ�]"),count, inv);
			else
				Stack2(ChatColor.RED + "" + ChatColor.BOLD + "||||||||||||||||||||||||||[�����]||||||||||||||||||||||||||", 160,14,1,Arrays.asList("",ChatColor.RED + ""+ChatColor.BOLD+"[�� ������ ����� �� �����ϴ�]"),count, inv);
		}
		
		Stack2(ChatColor.WHITE + "" + ChatColor.BOLD + "���� ���", 323,0,1,Arrays.asList(ChatColor.GRAY + "���� ȭ������ ���ư��ϴ�.",ChatColor.BLACK+""+isMabinogi), 9, inv);
		Stack2(ChatColor.WHITE + "" + ChatColor.BOLD + "�ݱ�", 324,0,1,Arrays.asList(ChatColor.GRAY + "â�� �ݽ��ϴ�.",ChatColor.BLACK+""+Skillname,ChatColor.BLACK+CategoriName), 17, inv);
		player.openInventory(inv);
	}
	
	
	
	public void MapleStory_MainSkillsListGUIClick(InventoryClickEvent event)
	{
		GoldBigDragon_RPG.Effect.Sound s = new GoldBigDragon_RPG.Effect.Sound();
		event.setCancelled(true);
		Player player = (Player) event.getWhoClicked();
		
		short page =  (short) (Short.parseShort(event.getInventory().getTitle().split(" : ")[1])-1);
		
		switch (event.getSlot())
		{
		case 45://���� ���
			s.SP(player, Sound.ITEM_PICKUP, 0.8F, 1.0F);
			StatsGUI SGUI = new StatsGUI();
			SGUI.StatusGUI(player);
			return;
		case 53://������
			s.SP(player, Sound.PISTON_RETRACT, 0.8F, 1.8F);
			player.closeInventory();
			return;
		case 48://���� ������
			s.SP(player, Sound.ITEM_PICKUP, 0.8F, 1.0F);
			MainSkillsListGUI(player, (short) (page-1));
			return;
		case 50://���� ������
			s.SP(player, Sound.ITEM_PICKUP, 0.8F, 1.0F);
			MainSkillsListGUI(player, (short) (page+1));
			return;
		default :
			s.SP(player, Sound.ITEM_PICKUP, 0.8F, 1.0F);
			SkillListGUI(player, (short) 0, false, ChatColor.stripColor(event.getCurrentItem().getItemMeta().getDisplayName()));
			return;
		}
	}
	
	public void Mabinogi_MainSkillsListGUIClick(InventoryClickEvent event)
	{
		GoldBigDragon_RPG.Effect.Sound s = new GoldBigDragon_RPG.Effect.Sound();
		event.setCancelled(true);
		Player player = (Player) event.getWhoClicked();
		short page =  (short) (Short.parseShort(event.getInventory().getTitle().split(" : ")[1])-1);
		switch (event.getSlot())
		{
		case 45://���� ���
			s.SP(player, Sound.ITEM_PICKUP, 0.8F, 1.0F);
			GoldBigDragon_RPG.GUI.StatsGUI SGUI = new GoldBigDragon_RPG.GUI.StatsGUI();
			SGUI.StatusGUI(player);
			return;
		case 53://������
			s.SP(player, Sound.PISTON_RETRACT, 0.8F, 1.8F);
			player.closeInventory();
			return;
		case 48://���� ������
			s.SP(player, Sound.ITEM_PICKUP, 0.8F, 1.0F);
			MainSkillsListGUI(player, (short) (page-1));
			return;
		case 50://���� ������
			s.SP(player, Sound.ITEM_PICKUP, 0.8F, 1.0F);
			MainSkillsListGUI(player, (short) (page+1));
			return;
		default :
			s.SP(player, Sound.ITEM_PICKUP, 0.8F, 1.0F);
			SkillListGUI(player, page, true, ChatColor.stripColor(event.getCurrentItem().getItemMeta().getDisplayName()));
			return;
		}
	}
	
	
	public void SkillListGUIClick(InventoryClickEvent event)
	{
		GoldBigDragon_RPG.Effect.Sound s = new GoldBigDragon_RPG.Effect.Sound();
		event.setCancelled(true);
		Player player = (Player) event.getWhoClicked();
		boolean isMabinogi = Boolean.parseBoolean(ChatColor.stripColor(event.getInventory().getItem(53).getItemMeta().getLore().get(1)));
		String CategoriName = ChatColor.stripColor(event.getInventory().getItem(45).getItemMeta().getLore().get(1));
		short page =  (short) (Short.parseShort(event.getInventory().getTitle().split(" : ")[1])-1);
		
		switch (event.getSlot())
		{
		case 45://���� ���
			s.SP(player, Sound.ITEM_PICKUP, 0.8F, 1.0F);
			MainSkillsListGUI(player, (short) 0);
			return;
		case 53://������
			s.SP(player, Sound.PISTON_RETRACT, 0.8F, 1.8F);
			player.closeInventory();
			return;
		case 48://���� ������
			s.SP(player, Sound.ITEM_PICKUP, 0.8F, 1.0F);
			SkillListGUI(player, (short) (page-1), isMabinogi, CategoriName);
			return;
		case 50://���� ������
			s.SP(player, Sound.ITEM_PICKUP, 0.8F, 1.0F);
			SkillListGUI(player, (short) (page+1), isMabinogi, CategoriName);
			return;
		default :
			s.SP(player, Sound.ITEM_PICKUP, 0.8F, 1.0F);
			if(event.isLeftClick() == true && event.isShiftClick() == false)
				AddQuickBarGUI(player, isMabinogi,CategoriName,ChatColor.stripColor(event.getCurrentItem().getItemMeta().getDisplayName()));
			else if(event.isLeftClick() == true && event.isShiftClick() == true)
			{
				YamlController YC = new YamlController(GoldBigDragon_RPG.Main.Main.plugin);
				String SkillName = ChatColor.stripColor(event.getCurrentItem().getItemMeta().getDisplayName());
				YamlManager PlayerSkillList  = YC.getNewConfig("Skill/PlayerData/"+player.getUniqueId().toString()+".yml");
				YamlManager AllSkillList  = YC.getNewConfig("Skill/SkillList.yml");
				short SkillRank = 1;
				short SkillMaxRank = (short) AllSkillList.getConfigurationSection(SkillName+".SkillRank").getKeys(false).size();
				if(isMabinogi==false)
					SkillRank= (short) PlayerSkillList.getInt("MapleStory."+CategoriName+".Skill."+SkillName);
				else
					SkillRank= (short) PlayerSkillList.getInt("Mabinogi."+CategoriName+"."+SkillName);
				if(SkillRank<SkillMaxRank)
				{
					if(GoldBigDragon_RPG.Main.ServerOption.PlayerList.get(player.getUniqueId().toString()).getStat_Level() < AllSkillList.getInt(SkillName+".SkillRank."+(SkillRank+1)+".NeedLevel"))
					{
						s.SP(player, Sound.ORB_PICKUP, 1.0F, 1.8F);
						player.sendMessage(ChatColor.RED + "[��ų] : ������ �����մϴ�!");
						return;
					}
					else if(GoldBigDragon_RPG.Main.ServerOption.PlayerList.get(player.getUniqueId().toString()).getStat_RealLevel() < AllSkillList.getInt(SkillName+".SkillRank."+(SkillRank+1)+".NeedRealLevel"))
					{
						s.SP(player, Sound.ORB_PICKUP, 1.0F, 1.8F);
						player.sendMessage(ChatColor.RED + "[��ų] : ���� ������ �����մϴ�!");
						return;
					}
					else if(GoldBigDragon_RPG.Main.ServerOption.PlayerList.get(player.getUniqueId().toString()).getStat_SkillPoint() >= AllSkillList.getInt(SkillName+".SkillRank."+(SkillRank+1)+".SkillPoint"))
					{
						if(isMabinogi == false)
							PlayerSkillList.set("MapleStory."+CategoriName+".Skill."+SkillName, SkillRank+1);
						else
							PlayerSkillList.set("Mabinogi."+CategoriName+"."+SkillName, SkillRank+1);
						
						GoldBigDragon_RPG.Main.ServerOption.PlayerList.get(player.getUniqueId().toString()).addStat_SkillPoint(-1 * AllSkillList.getInt(SkillName+".SkillRank."+(SkillRank+1)+".SkillPoint"));
						GoldBigDragon_RPG.Main.ServerOption.PlayerList.get(player.getUniqueId().toString()).addStat_MaxHP(AllSkillList.getInt(SkillName+".SkillRank."+(SkillRank+1)+".BonusHP"));
						GoldBigDragon_RPG.Main.ServerOption.PlayerList.get(player.getUniqueId().toString()).addStat_HP(AllSkillList.getInt(SkillName+".SkillRank."+(SkillRank+1)+".BonusHP"));
						GoldBigDragon_RPG.Main.ServerOption.PlayerList.get(player.getUniqueId().toString()).addStat_MaxMP(AllSkillList.getInt(SkillName+".SkillRank."+(SkillRank+1)+".BonusMP"));
						GoldBigDragon_RPG.Main.ServerOption.PlayerList.get(player.getUniqueId().toString()).addStat_MP(AllSkillList.getInt(SkillName+".SkillRank."+(SkillRank+1)+".BonusMP"));
						GoldBigDragon_RPG.Main.ServerOption.PlayerList.get(player.getUniqueId().toString()).addStat_Balance(AllSkillList.getInt(SkillName+".SkillRank."+(SkillRank+1)+".BonusBAL"));
						GoldBigDragon_RPG.Main.ServerOption.PlayerList.get(player.getUniqueId().toString()).addStat_Critical(AllSkillList.getInt(SkillName+".SkillRank."+(SkillRank+1)+".BonusCRI"));
						GoldBigDragon_RPG.Main.ServerOption.PlayerList.get(player.getUniqueId().toString()).addStat_STR(AllSkillList.getInt(SkillName+".SkillRank."+(SkillRank+1)+".BonusSTR"));
						GoldBigDragon_RPG.Main.ServerOption.PlayerList.get(player.getUniqueId().toString()).addStat_DEX(AllSkillList.getInt(SkillName+".SkillRank."+(SkillRank+1)+".BonusDEX"));
						GoldBigDragon_RPG.Main.ServerOption.PlayerList.get(player.getUniqueId().toString()).addStat_INT(AllSkillList.getInt(SkillName+".SkillRank."+(SkillRank+1)+".BonusINT"));
						GoldBigDragon_RPG.Main.ServerOption.PlayerList.get(player.getUniqueId().toString()).addStat_WILL(AllSkillList.getInt(SkillName+".SkillRank."+(SkillRank+1)+".BonusWILL"));
						GoldBigDragon_RPG.Main.ServerOption.PlayerList.get(player.getUniqueId().toString()).addStat_LUK(AllSkillList.getInt(SkillName+".SkillRank."+(SkillRank+1)+".BonusLUK"));
						GoldBigDragon_RPG.Main.ServerOption.PlayerList.get(player.getUniqueId().toString()).addStat_DEF(AllSkillList.getInt(SkillName+".SkillRank."+(SkillRank+1)+".BonusDEF"));
						GoldBigDragon_RPG.Main.ServerOption.PlayerList.get(player.getUniqueId().toString()).addStat_Protect(AllSkillList.getInt(SkillName+".SkillRank."+(SkillRank+1)+".BonusPRO"));
						GoldBigDragon_RPG.Main.ServerOption.PlayerList.get(player.getUniqueId().toString()).addStat_Magic_DEF(AllSkillList.getInt(SkillName+".SkillRank."+(SkillRank+1)+".BonusMDEF"));
						GoldBigDragon_RPG.Main.ServerOption.PlayerList.get(player.getUniqueId().toString()).addStat_Magic_Protect(AllSkillList.getInt(SkillName+".SkillRank."+(SkillRank+1)+".BonusMPRO"));
						PlayerSkillList.saveConfig();
						s.SP(player, Sound.LEVEL_UP, 0.8F, 1.7F);

						if(GoldBigDragon_RPG.Main.ServerOption.MagicSpellsCatched == true)
						{
							OtherPlugins.SpellMain MS = new OtherPlugins.SpellMain();
							MS.setPlayerMaxAndNowMana(player);
						}
						Damageable p = player;
						p.setMaxHealth(GoldBigDragon_RPG.Main.ServerOption.PlayerList.get(player.getUniqueId().toString()).getStat_MaxHP());
						p.setHealth(GoldBigDragon_RPG.Main.ServerOption.PlayerList.get(player.getUniqueId().toString()).getStat_HP());
						
						if(SkillRank!=SkillMaxRank-1)
							player.sendMessage(ChatColor.GREEN + "[��ų] : "+ChatColor.YELLOW+SkillName+ChatColor.GREEN+" ��ų�� "+ChatColor.YELLOW+"��ũ�� ���"+ChatColor.GREEN+"�Ͽ����ϴ�!");
						else
							player.sendMessage(ChatColor.DARK_PURPLE + "[��ų] : "+ChatColor.YELLOW+SkillName+ChatColor.DARK_PURPLE+" ��ų�� "+ChatColor.YELLOW+"������"+ChatColor.DARK_PURPLE+"�Ͽ����ϴ�!");
						
						SkillListGUI(player, page, isMabinogi, CategoriName);
					}
					else
					{
						s.SP(player, Sound.ORB_PICKUP, 1.0F, 1.8F);
						player.sendMessage(ChatColor.RED + "[��ų] : ���� ����Ʈ�� �����մϴ�!");
					}
				}
				else
				{
					s.SP(player, Sound.ANVIL_LAND, 0.8F, 1.7F);
				}
			}
			return;
		}
	}
	
	public void AddQuickBarGUIClick(InventoryClickEvent event)
	{
		GoldBigDragon_RPG.Effect.Sound s = new GoldBigDragon_RPG.Effect.Sound();
		event.setCancelled(true);
		Player player = (Player) event.getWhoClicked();

		boolean isMabinogi = Boolean.parseBoolean(ChatColor.stripColor(event.getInventory().getItem(9).getItemMeta().getLore().get(1)));
		String Skillname = ChatColor.stripColor(event.getInventory().getItem(17).getItemMeta().getLore().get(1));
		String CategoriName = ChatColor.stripColor(event.getInventory().getItem(17).getItemMeta().getLore().get(2));
		
		switch (event.getSlot())
		{
		case 9://���� ���
			s.SP(player, Sound.ITEM_PICKUP, 0.8F, 1.0F);
			SkillListGUI(player, (short) 0, isMabinogi, CategoriName);
			return;
		case 17://������
			s.SP(player, Sound.PISTON_RETRACT, 0.8F, 1.8F);
			player.closeInventory();
			return;
		default :
			s.SP(player, Sound.ITEM_PICKUP, 0.8F, 1.0F);
			if(player.getInventory().getItem(event.getSlot()) == null)
			{
				YamlController YC = new YamlController(GoldBigDragon_RPG.Main.Main.plugin);
				YamlManager Config = YC.getNewConfig("config.yml");
				YamlManager PlayerSkillList  = YC.getNewConfig("Skill/PlayerData/"+player.getUniqueId().toString()+".yml");
				YamlManager AllSkillList  = YC.getNewConfig("Skill/SkillList.yml");
				int IconID = AllSkillList.getInt(Skillname+".ID");
				byte IconDATA = (byte) AllSkillList.getInt(Skillname+".DATA");
				byte IconAmount = (byte) AllSkillList.getInt(Skillname+".Amount");
				String lore = ChatColor.WHITE + ""+CategoriName+"%enter%"+ChatColor.WHITE + ""+Skillname+"%enter%%enter%"+ChatColor.YELLOW + "[Ŭ���� �����Կ��� ����]%enter%";
				String[] scriptA = lore.split("%enter%");
				for(byte counter = 0; counter < scriptA.length; counter++)
					scriptA[counter] =  scriptA[counter];
				
				
				ItemStack Icon = new MaterialData(IconID, (byte) IconDATA).toItemStack(IconAmount);
				ItemMeta Icon_Meta = Icon.getItemMeta();
				Icon_Meta.setDisplayName(ChatColor.GREEN + "     [��ų ����Ű]     ");
				Icon_Meta.setLore(Arrays.asList(scriptA));
				Icon.setItemMeta(Icon_Meta);
				player.getInventory().setItem(event.getSlot(), Icon);
				SkillListGUI(player, (short) 0, isMabinogi, CategoriName);
			}
			else
			{
				AddQuickBarGUI(player, isMabinogi,CategoriName,Skillname);
			}
			return;
		}
	}
	
}
