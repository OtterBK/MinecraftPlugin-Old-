package GoldBigDragon_RPG.GUI;

import java.util.Arrays;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.Inventory;

import GoldBigDragon_RPG.Main.ServerOption;
import GoldBigDragon_RPG.Main.Object_UserData;
import GoldBigDragon_RPG.Util.YamlController;
import GoldBigDragon_RPG.Util.YamlManager;

public class UpGradeGUI extends GUIutil
{
	public void UpgradeRecipeGUI(Player player, int page)
	{
		YamlController YC = new YamlController(GoldBigDragon_RPG.Main.Main.plugin);
		YamlManager RecipeList = YC.getNewConfig("Item/Upgrade.yml");
		
		Inventory inv = Bukkit.createInventory(null, 54, ChatColor.BLACK + "������ ��� : " + (page+1));

		Object[] a= RecipeList.getKeys().toArray();

		byte loc=0;
		for(int count = page*45; count < a.length;count++)
		{
			if(count > a.length || loc >= 45) break;
			String ItemName =a[count].toString();
			String Lore=null;
			if(RecipeList.getString(ItemName+".Only").equals("null"))
				Lore = ChatColor.WHITE+"[��� ���]%enter%%enter%";
			else
				Lore = RecipeList.getString(ItemName+".Only")+"%enter%%enter%";

			if(RecipeList.getInt(ItemName+".MaxDurability") > 0)
				Lore = Lore+ChatColor.DARK_AQUA+" �� �ִ� ������ : "+RecipeList.getInt(ItemName+".MaxDurability")+"%enter%";
			else if(RecipeList.getInt(ItemName+".MaxDurability") < 0)
				Lore = Lore+ChatColor.RED+" �� �ִ� ������ : "+RecipeList.getInt(ItemName+".MaxDurability")+"%enter%";
			if(RecipeList.getInt(ItemName+".MinDamage") > 0)
				Lore = Lore+ChatColor.DARK_AQUA+" �� �ּ� "+ServerOption.Damage+" : "+RecipeList.getInt(ItemName+".MinDamage")+"%enter%";
			else if(RecipeList.getInt(ItemName+".MinDamage") < 0)
				Lore = Lore+ChatColor.RED+" �� �ּ� "+ServerOption.Damage+" : "+RecipeList.getInt(ItemName+".MinDamage")+"%enter%";
			if(RecipeList.getInt(ItemName+".MaxDamage") > 0)
				Lore = Lore+ChatColor.DARK_AQUA+" �� �ִ� "+ServerOption.Damage+" : "+RecipeList.getInt(ItemName+".MaxDamage")+"%enter%";
			else if(RecipeList.getInt(ItemName+".MaxDamage") < 0)
				Lore = Lore+ChatColor.RED+" �� �ִ� "+ServerOption.Damage+" : "+RecipeList.getInt(ItemName+".MaxDamage")+"%enter%";
			if(RecipeList.getInt(ItemName+".MinMaDamage") > 0)
				Lore = Lore+ChatColor.DARK_AQUA+" �� �ּ� "+ServerOption.MagicDamage+" : "+RecipeList.getInt(ItemName+".MinMaDamage")+"%enter%";
			else if(RecipeList.getInt(ItemName+".MinMaDamage") < 0)
				Lore = Lore+ChatColor.RED+" �� �ּ� "+ServerOption.MagicDamage+" : "+RecipeList.getInt(ItemName+".MinMaDamage")+"%enter%";
			if(RecipeList.getInt(ItemName+".MaxMaDamage") > 0)
				Lore = Lore+ChatColor.DARK_AQUA+" �� �ִ� "+ServerOption.MagicDamage+" : "+RecipeList.getInt(ItemName+".MaxMaDamage")+"%enter%";
			else if(RecipeList.getInt(ItemName+".MaxMaDamage") < 0)
				Lore = Lore+ChatColor.RED+" �� �ִ� "+ServerOption.MagicDamage+" : "+RecipeList.getInt(ItemName+".MaxMaDamage")+"%enter%";
			if(RecipeList.getInt(ItemName+".DEF") > 0)
				Lore = Lore+ChatColor.DARK_AQUA+" �� ��� : "+RecipeList.getInt(ItemName+".DEF")+"%enter%";
			else if(RecipeList.getInt(ItemName+".DEF") < 0)
				Lore = Lore+ChatColor.RED+" �� ��� : "+RecipeList.getInt(ItemName+".DEF")+"%enter%";
			if(RecipeList.getInt(ItemName+".Protect") > 0)
				Lore = Lore+ChatColor.DARK_AQUA+" �� ��ȣ : "+RecipeList.getInt(ItemName+".Protect")+"%enter%";
			else if(RecipeList.getInt(ItemName+".Protect") < 0)
				Lore = Lore+ChatColor.RED+" �� ��ȣ : "+RecipeList.getInt(ItemName+".Protect")+"%enter%";
			if(RecipeList.getInt(ItemName+".MaDEF") > 0)
				Lore = Lore+ChatColor.DARK_AQUA+" �� ���� ��� : "+RecipeList.getInt(ItemName+".MaDEF")+"%enter%";
			else if(RecipeList.getInt(ItemName+".MaDEF") < 0)
				Lore = Lore+ChatColor.RED+" �� ���� ��� : "+RecipeList.getInt(ItemName+".MaDEF")+"%enter%";
			if(RecipeList.getInt(ItemName+".MaProtect") > 0)
				Lore = Lore+ChatColor.DARK_AQUA+" �� ���� ��ȣ : "+RecipeList.getInt(ItemName+".MaProtect")+"%enter%";
			else if(RecipeList.getInt(ItemName+".MaProtect") < 0)
				Lore = Lore+ChatColor.RED+" �� ���� ��ȣ : "+RecipeList.getInt(ItemName+".MaProtect")+"%enter%";
			if(RecipeList.getInt(ItemName+".Balance") > 0)
				Lore = Lore+ChatColor.DARK_AQUA+" �� �뷱�� : "+RecipeList.getInt(ItemName+".Balance")+"%enter%";
			else if(RecipeList.getInt(ItemName+".Balance") < 0)
				Lore = Lore+ChatColor.RED+" �� �뷱�� : "+RecipeList.getInt(ItemName+".Balance")+"%enter%";
			if(RecipeList.getInt(ItemName+".Critical") > 0)
				Lore = Lore+ChatColor.DARK_AQUA+" �� ũ��Ƽ�� : "+RecipeList.getInt(ItemName+".Critical")+"%enter%";
			else if(RecipeList.getInt(ItemName+".Critical") < 0)
				Lore = Lore+ChatColor.RED+" �� ũ��Ƽ�� : "+RecipeList.getInt(ItemName+".Critical")+"%enter%";
			
			Lore = Lore+"%enter%"+RecipeList.getString(ItemName+".Lore")+"%enter%%enter%";

			Lore = Lore+ChatColor.YELLOW+" �� ���� Ƚ�� : "+ChatColor.WHITE+RecipeList.getInt(ItemName+".UpgradeAbleLevel")+ChatColor.YELLOW+" ȸ° ���� ����%enter%";
			Lore = Lore+ChatColor.YELLOW+" �� �ʿ� ���õ� : "+ChatColor.WHITE+RecipeList.getInt(ItemName+".DecreaseProficiency")+"%enter% ";

			Lore = Lore+"%enter%"+ChatColor.YELLOW+"[�� Ŭ���� ������ ����]%enter%"+ChatColor.RED+"[Shift + �� Ŭ���� ������ ����]%enter% ";

			String[] scriptA = Lore.split("%enter%");
			for(byte counter = 0; counter < scriptA.length; counter++)
				scriptA[counter] =  " "+scriptA[counter];
			
			Stack2(ChatColor.WHITE+ItemName, 395, 0, 1,Arrays.asList(scriptA), loc, inv);
			loc++;
		}
		
		if(a.length-(page*44)>45)
			Stack2(ChatColor.WHITE + "" + ChatColor.BOLD + "���� ������", 323,0,1,Arrays.asList(ChatColor.GRAY + "���� �������� �̵� �մϴ�."), 50, inv);
		if(page!=0)
			Stack2(ChatColor.WHITE + "" + ChatColor.BOLD + "���� ������", 323,0,1,Arrays.asList(ChatColor.GRAY + "���� �������� �̵� �մϴ�."), 48, inv);

		Stack2(ChatColor.WHITE + "" + ChatColor.BOLD + "�� ������", 339,0,1,Arrays.asList(ChatColor.GRAY + "���ο� �������� ����ϴ�."), 49, inv);
		Stack2(ChatColor.WHITE + "" + ChatColor.BOLD + "���� ���", 323,0,1,Arrays.asList(ChatColor.GRAY + "���� ȭ������ ���ư��ϴ�."), 45, inv);
		Stack2(ChatColor.WHITE + "" + ChatColor.BOLD + "�ݱ�", 324,0,1,Arrays.asList(ChatColor.GRAY + "â�� �ݽ��ϴ�."), 53, inv);
		player.openInventory(inv);
	}
	
	public void UpgradeRecipeSettingGUI(Player player, String RecipeName)
	{
		YamlController YC = new YamlController(GoldBigDragon_RPG.Main.Main.plugin);
		YamlManager RecipeList = YC.getNewConfig("Item/Upgrade.yml");
		
		Inventory inv = Bukkit.createInventory(null, 54, ChatColor.BLACK + "������ ����");

		String Lore=null;
		if(RecipeList.getString(RecipeName+".Only").equals("null"))
			Lore = ChatColor.WHITE+"[��� ���]%enter%%enter%";
		else
			Lore = RecipeList.getString(RecipeName+".Only")+"%enter%%enter%";

		if(RecipeList.getInt(RecipeName+".MaxDurability") > 0)
			Lore = Lore+ChatColor.DARK_AQUA+" �� �ִ� ������ : "+RecipeList.getInt(RecipeName+".MaxDurability")+"%enter%";
		else if(RecipeList.getInt(RecipeName+".MaxDurability") < 0)
			Lore = Lore+ChatColor.RED+" �� �ִ� ������ : "+RecipeList.getInt(RecipeName+".MaxDurability")+"%enter%";
		if(RecipeList.getInt(RecipeName+".MinDamage") > 0)
			Lore = Lore+ChatColor.DARK_AQUA+" �� �ּ� "+ServerOption.Damage+" : "+RecipeList.getInt(RecipeName+".MinDamage")+"%enter%";
		else if(RecipeList.getInt(RecipeName+".MinDamage") < 0)
			Lore = Lore+ChatColor.RED+" �� �ּ� "+ServerOption.Damage+" : "+RecipeList.getInt(RecipeName+".MinDamage")+"%enter%";
		if(RecipeList.getInt(RecipeName+".MaxDamage") > 0)
			Lore = Lore+ChatColor.DARK_AQUA+" �� �ִ� "+ServerOption.Damage+" : "+RecipeList.getInt(RecipeName+".MaxDamage")+"%enter%";
		else if(RecipeList.getInt(RecipeName+".MaxDamage") < 0)
			Lore = Lore+ChatColor.RED+" �� �ִ� "+ServerOption.Damage+" : "+RecipeList.getInt(RecipeName+".MaxDamage")+"%enter%";
		if(RecipeList.getInt(RecipeName+".MinMaDamage") > 0)
			Lore = Lore+ChatColor.DARK_AQUA+" �� �ּ� "+ServerOption.MagicDamage+" : "+RecipeList.getInt(RecipeName+".MinMaDamage")+"%enter%";
		else if(RecipeList.getInt(RecipeName+".MinMaDamage") < 0)
			Lore = Lore+ChatColor.RED+" �� �ּ� "+ServerOption.MagicDamage+" : "+RecipeList.getInt(RecipeName+".MinMaDamage")+"%enter%";
		if(RecipeList.getInt(RecipeName+".MaxMaDamage") > 0)
			Lore = Lore+ChatColor.DARK_AQUA+" �� �ִ� "+ServerOption.MagicDamage+" : "+RecipeList.getInt(RecipeName+".MaxMaDamage")+"%enter%";
		else if(RecipeList.getInt(RecipeName+".MaxMaDamage") < 0)
			Lore = Lore+ChatColor.RED+" �� �ִ� "+ServerOption.MagicDamage+" : "+RecipeList.getInt(RecipeName+".MaxMaDamage")+"%enter%";
		if(RecipeList.getInt(RecipeName+".DEF") > 0)
			Lore = Lore+ChatColor.DARK_AQUA+" �� ��� : "+RecipeList.getInt(RecipeName+".DEF")+"%enter%";
		else if(RecipeList.getInt(RecipeName+".DEF") < 0)
			Lore = Lore+ChatColor.RED+" �� ��� : "+RecipeList.getInt(RecipeName+".DEF")+"%enter%";
		if(RecipeList.getInt(RecipeName+".Protect") > 0)
			Lore = Lore+ChatColor.DARK_AQUA+" �� ��ȣ : "+RecipeList.getInt(RecipeName+".Protect")+"%enter%";
		else if(RecipeList.getInt(RecipeName+".Protect") < 0)
			Lore = Lore+ChatColor.RED+" �� ��ȣ : "+RecipeList.getInt(RecipeName+".Protect")+"%enter%";
		if(RecipeList.getInt(RecipeName+".MaDEF") > 0)
			Lore = Lore+ChatColor.DARK_AQUA+" �� ���� ��� : "+RecipeList.getInt(RecipeName+".MaDEF")+"%enter%";
		else if(RecipeList.getInt(RecipeName+".MaDEF") < 0)
			Lore = Lore+ChatColor.RED+" �� ���� ��� : "+RecipeList.getInt(RecipeName+".MaDEF")+"%enter%";
		if(RecipeList.getInt(RecipeName+".MaProtect") > 0)
			Lore = Lore+ChatColor.DARK_AQUA+" �� ���� ��ȣ : "+RecipeList.getInt(RecipeName+".MaProtect")+"%enter%";
		else if(RecipeList.getInt(RecipeName+".MaProtect") < 0)
			Lore = Lore+ChatColor.RED+" �� ���� ��ȣ : "+RecipeList.getInt(RecipeName+".MaProtect")+"%enter%";
		if(RecipeList.getInt(RecipeName+".Balance") > 0)
			Lore = Lore+ChatColor.DARK_AQUA+" �� �뷱�� : "+RecipeList.getInt(RecipeName+".Balance")+"%enter%";
		else if(RecipeList.getInt(RecipeName+".Balance") < 0)
			Lore = Lore+ChatColor.RED+" �� �뷱�� : "+RecipeList.getInt(RecipeName+".Balance")+"%enter%";
		if(RecipeList.getInt(RecipeName+".Critical") > 0)
			Lore = Lore+ChatColor.DARK_AQUA+" �� ũ��Ƽ�� : "+RecipeList.getInt(RecipeName+".Critical")+"%enter%";
		else if(RecipeList.getInt(RecipeName+".Critical") < 0)
			Lore = Lore+ChatColor.RED+" �� ũ��Ƽ�� : "+RecipeList.getInt(RecipeName+".Critical")+"%enter%";
		
		Lore = Lore+"%enter%"+RecipeList.getString(RecipeName+".Lore")+"%enter%%enter%";

		Lore = Lore+ChatColor.YELLOW+" �� ���� Ƚ�� : "+ChatColor.WHITE+RecipeList.getInt(RecipeName+".UpgradeAbleLevel")+ChatColor.YELLOW+" ȸ° ���� ����%enter%";
		Lore = Lore+ChatColor.YELLOW+" �� �ʿ� ���õ� : "+ChatColor.WHITE+RecipeList.getInt(RecipeName+".DecreaseProficiency")+"%enter% ";

		
		String[] scriptA = Lore.split("%enter%");
		for(byte counter = 0; counter < scriptA.length; counter++)
			scriptA[counter] =  " "+scriptA[counter];

		Stack2(ChatColor.DARK_AQUA + "[    �����    ]", 265,0,1,null, 9, inv);
		Stack2(ChatColor.DARK_AQUA + "[    �����    ]", 265,0,1,null, 10, inv);
		Stack2(ChatColor.DARK_AQUA + "[    �����    ]", 265,0,1,null, 11, inv);
		Stack2(ChatColor.DARK_AQUA + "[    �����    ]", 265,0,1,null, 18, inv);
		Stack2(ChatColor.DARK_AQUA + "[    �����    ]", 265,0,1,null, 20, inv);
		Stack2(ChatColor.DARK_AQUA + "[    �����    ]", 265,0,1,null, 27, inv);
		Stack2(ChatColor.DARK_AQUA + "[    �����    ]", 265,0,1,null, 28, inv);
		Stack2(ChatColor.DARK_AQUA + "[    �����    ]", 265,0,1,null, 29, inv);
		
		Stack2(ChatColor.WHITE+RecipeName, 395, 0, 1,Arrays.asList(scriptA), 19, inv);
		Stack2(ChatColor.WHITE+"[   ���� ����   ]", 421, 0, 1,Arrays.asList(ChatColor.WHITE+"�������� ������ �����մϴ�."), 37, inv);
		
		Stack2(ChatColor.DARK_AQUA + "[    Ÿ�� ����    ]", 61,0,1,Arrays.asList(ChatColor.WHITE+"���� ������ Ÿ����",ChatColor.WHITE+"�����մϴ�.","",ChatColor.WHITE+"[    ���� Ÿ��    ]",RecipeList.getString(RecipeName+".Only"),""), 13, inv);
		Stack2(ChatColor.DARK_AQUA + "[       "+ServerOption.Damage+"       ]", 267,0,1,Arrays.asList(ChatColor.WHITE+"������ "+ServerOption.Damage+"��",ChatColor.WHITE+"��� ��ŵ�ϴ�.",""), 14, inv);
		Stack2(ChatColor.DARK_AQUA + "[     "+ServerOption.MagicDamage+"     ]", 403,0,1,Arrays.asList(ChatColor.WHITE+"������ "+ServerOption.MagicDamage+"��",ChatColor.WHITE+"��� ��ŵ�ϴ�.",""), 15, inv);
		Stack2(ChatColor.DARK_AQUA + "[       �뷱��       ]", 262,0,1,Arrays.asList(ChatColor.WHITE+"������ �뷱����",ChatColor.WHITE+"��� ��ŵ�ϴ�.",""), 16, inv);
		Stack2(ChatColor.DARK_AQUA + "[        ���        ]", 307,0,1,Arrays.asList(ChatColor.WHITE+"������ ������",ChatColor.WHITE+"��� ��ŵ�ϴ�.",""), 22, inv);
		Stack2(ChatColor.DARK_AQUA + "[        ��ȣ        ]", 306,0,1,Arrays.asList(ChatColor.WHITE+"������ ��ȣ��",ChatColor.WHITE+"��� ��ŵ�ϴ�.",""), 23, inv);
		Stack2(ChatColor.DARK_AQUA + "[      ���� ���      ]", 311,0,1,Arrays.asList(ChatColor.WHITE+"������ ���� ��",ChatColor.WHITE+"��� ��ŵ�ϴ�.",""), 24, inv);
		Stack2(ChatColor.DARK_AQUA + "[      ���� ��ȣ      ]", 310,0,1,Arrays.asList(ChatColor.WHITE+"������ ���� ��ȣ��",ChatColor.WHITE+"��� ��ŵ�ϴ�.",""), 25, inv);
		Stack2(ChatColor.DARK_AQUA + "[      ũ��Ƽ��      ]", 377,0,1,Arrays.asList(ChatColor.WHITE+"������ ũ��Ƽ�� Ȯ����",ChatColor.WHITE+"��� ��ŵ�ϴ�.",""), 31, inv);
		Stack2(ChatColor.DARK_AQUA + "[       ������       ]", 145,2,1,Arrays.asList(ChatColor.WHITE+"������ �ִ� ��������",ChatColor.WHITE+"���� �մϴ�.",""), 32, inv);
		Stack2(ChatColor.DARK_AQUA + "[        ����        ]", 145,0,1,Arrays.asList(ChatColor.WHITE+"���� ������ ���� ������",ChatColor.WHITE+"���� �մϴ�.",""), 33, inv);
		Stack2(ChatColor.DARK_AQUA + "[       ���õ�       ]", 416,0,1,Arrays.asList(ChatColor.WHITE+"������ �ʿ��� ���õ���",ChatColor.WHITE+"���� �մϴ�.",""), 34, inv);
		
		Stack2(ChatColor.WHITE + "" + ChatColor.BOLD + "���� ���", 323,0,1,Arrays.asList(ChatColor.GRAY + "���� ȭ������ ���ư��ϴ�."), 45, inv);
		Stack2(ChatColor.WHITE + "" + ChatColor.BOLD + "�ݱ�", 324,0,1,Arrays.asList(ChatColor.GRAY + "â�� �ݽ��ϴ�.",ChatColor.BLACK+RecipeName), 53, inv);
		player.openInventory(inv);
	}
	
	
	public void UpgradeRecipeGUIClick(InventoryClickEvent event)
	{
		GoldBigDragon_RPG.Effect.Sound s = new GoldBigDragon_RPG.Effect.Sound();
		event.setCancelled(true);
		Player player = (Player) event.getWhoClicked();

		short page =  (short) (Short.parseShort(event.getInventory().getTitle().split(" : ")[1])-1);
		switch (event.getSlot())
		{
		case 45://���� ���
			s.SP(player, Sound.ITEM_PICKUP, 0.8F, 1.0F);
			OPBoxGUI OPGUI = new OPBoxGUI();
			OPGUI.OPBoxGUI_Main(player, (byte) 2);
			return;
		case 53://������
			s.SP(player, Sound.PISTON_RETRACT, 0.8F, 1.8F);
			player.closeInventory();
			return;
		case 48://���� ������
			s.SP(player, Sound.ITEM_PICKUP, 0.8F, 1.0F);
			UpgradeRecipeGUI(player, page-1);
			return;
		case 49://�� ������
			{
				s.SP(player, Sound.ITEM_PICKUP, 0.8F, 1.0F);
				player.closeInventory();
				player.sendMessage(ChatColor.DARK_AQUA+"[����] : ���ο� �������� �̸��� �Է� �ϼ���!");
				Object_UserData u = new Object_UserData();
				u.setType(player, "Upgrade");
				u.setString(player, (byte)1, "NUR");
			}
			return;
		case 50://���� ������
			s.SP(player, Sound.ITEM_PICKUP, 0.8F, 1.0F);
			UpgradeRecipeGUI(player, page+1);
			return;
		default :
			if(event.isLeftClick() == true&&event.isShiftClick()==false)
			{
				s.SP(player, Sound.ITEM_PICKUP, 0.8F, 1.0F);
				UpgradeRecipeSettingGUI(player, ChatColor.stripColor(event.getCurrentItem().getItemMeta().getDisplayName()));
			}
			else if(event.isRightClick()==true&&event.isShiftClick()==true)
			{
				s.SP(player, Sound.LAVA_POP, 0.8F, 1.0F);
				YamlController YC = new YamlController(GoldBigDragon_RPG.Main.Main.plugin);
				YamlManager ItemList = YC.getNewConfig("Item/Upgrade.yml");
				ItemList.removeKey(ChatColor.stripColor(event.getCurrentItem().getItemMeta().getDisplayName()));
				ItemList.saveConfig();
				UpgradeRecipeGUI(player, page);
			}
			return;
		}
	}
	
	public void UpgradeRecipeSettingGUIClick(InventoryClickEvent event)
	{
		GoldBigDragon_RPG.Effect.Sound s = new GoldBigDragon_RPG.Effect.Sound();
		event.setCancelled(true);
		Player player = (Player) event.getWhoClicked();

		Object_UserData u = new Object_UserData();
		
		YamlController YC = new YamlController(GoldBigDragon_RPG.Main.Main.plugin);
		YamlManager RecipeList = YC.getNewConfig("Item/Upgrade.yml");
		String RecipeName = ChatColor.stripColor(event.getInventory().getItem(53).getItemMeta().getLore().get(1));
		
		switch (event.getSlot())
		{
		case 13://Ÿ�� ����
			s.SP(player, Sound.ITEM_PICKUP, 0.8F, 1.0F);
			YamlManager Target = YC.getNewConfig("Item/CustomType.yml");
		  	Object[] Type = Target.getKeys().toArray();
		  	if(Type.length == 0)
		  	{
				if(RecipeList.getString(RecipeName+".Only").contains("[���� ����]"))
					RecipeList.set(RecipeName+".Only",ChatColor.RED+"[�Ѽ� ��]");
				else if(RecipeList.getString(RecipeName+".Only").contains("[�Ѽ� ��]"))
					RecipeList.set(RecipeName+".Only",ChatColor.RED+"[��� ��]");
				else if(RecipeList.getString(RecipeName+".Only").contains("[��� ��]"))
					RecipeList.set(RecipeName+".Only",ChatColor.RED+"[����]");
				else if(RecipeList.getString(RecipeName+".Only").contains("[����]"))
					RecipeList.set(RecipeName+".Only",ChatColor.RED+"[��]");
				else if(RecipeList.getString(RecipeName+".Only").contains("[��]"))
					RecipeList.set(RecipeName+".Only",ChatColor.DARK_GREEN+"[���Ÿ� ����]");
				else if(RecipeList.getString(RecipeName+".Only").contains("[���Ÿ� ����]"))
					RecipeList.set(RecipeName+".Only",ChatColor.DARK_GREEN+"[Ȱ]");
				else if(RecipeList.getString(RecipeName+".Only").contains("[Ȱ]"))
					RecipeList.set(RecipeName+".Only",ChatColor.DARK_GREEN+"[����]");
				else if(RecipeList.getString(RecipeName+".Only").contains("[����]"))
					RecipeList.set(RecipeName+".Only",ChatColor.DARK_AQUA+"[���� ����]");
				else if(RecipeList.getString(RecipeName+".Only").contains("[���� ����]"))
					RecipeList.set(RecipeName+".Only",ChatColor.DARK_AQUA+"[����]");
				else if(RecipeList.getString(RecipeName+".Only").contains("[����]"))
					RecipeList.set(RecipeName+".Only",ChatColor.DARK_AQUA+"[������]");
				else if(RecipeList.getString(RecipeName+".Only").contains("[������]"))
					RecipeList.set(RecipeName+".Only",ChatColor.WHITE+"[��]");
				else if(RecipeList.getString(RecipeName+".Only").contains("[��]"))
					RecipeList.set(RecipeName+".Only",ChatColor.GRAY+"[��Ÿ]");
				else
					RecipeList.set(RecipeName+".Only",ChatColor.RED+"[���� ����]");
		  	}
		  	else
		  	{

				if(RecipeList.getString(RecipeName+".Only").contains("[���� ����]"))
					RecipeList.set(RecipeName+".Only",ChatColor.RED+"[�Ѽ� ��]");
				else if(RecipeList.getString(RecipeName+".Only").contains("[�Ѽ� ��]"))
					RecipeList.set(RecipeName+".Only",ChatColor.RED+"[��� ��]");
				else if(RecipeList.getString(RecipeName+".Only").contains("[��� ��]"))
					RecipeList.set(RecipeName+".Only",ChatColor.RED+"[����]");
				else if(RecipeList.getString(RecipeName+".Only").contains("[����]"))
					RecipeList.set(RecipeName+".Only",ChatColor.RED+"[��]");
				else if(RecipeList.getString(RecipeName+".Only").contains("[��]"))
					RecipeList.set(RecipeName+".Only",ChatColor.DARK_GREEN+"[���Ÿ� ����]");
				else if(RecipeList.getString(RecipeName+".Only").contains("[���Ÿ� ����]"))
					RecipeList.set(RecipeName+".Only",ChatColor.DARK_GREEN+"[Ȱ]");
				else if(RecipeList.getString(RecipeName+".Only").contains("[Ȱ]"))
					RecipeList.set(RecipeName+".Only",ChatColor.DARK_GREEN+"[����]");
				else if(RecipeList.getString(RecipeName+".Only").contains("[����]"))
					RecipeList.set(RecipeName+".Only",ChatColor.DARK_AQUA+"[���� ����]");
				else if(RecipeList.getString(RecipeName+".Only").contains("[���� ����]"))
					RecipeList.set(RecipeName+".Only",ChatColor.DARK_AQUA+"[����]");
				else if(RecipeList.getString(RecipeName+".Only").contains("[����]"))
					RecipeList.set(RecipeName+".Only",ChatColor.DARK_AQUA+"[������]");
				else if(RecipeList.getString(RecipeName+".Only").contains("[������]"))
					RecipeList.set(RecipeName+".Only",ChatColor.WHITE+"[��]");
				else if(RecipeList.getString(RecipeName+".Only").contains("[��]"))
					RecipeList.set(RecipeName+".Only",ChatColor.GRAY+"[��Ÿ]");
				else if(RecipeList.getString(RecipeName+".Only").contains("[��Ÿ]"))
					RecipeList.set(RecipeName+".Only",ChatColor.WHITE+Type[0].toString());
		  		else
				{
					for(short count = 0; count < Type.length; count++)
					{
						if((RecipeList.getString(RecipeName+".Only").contains(Type[count].toString())))
						{
							if(count+1 == Type.length)
								RecipeList.set(RecipeName+".Only",ChatColor.RED+"[���� ����]");
							else
								RecipeList.set(RecipeName+".Only",ChatColor.WHITE+Type[(count+1)].toString());
							break;
						}
					}
				}
		  	}
			RecipeList.saveConfig();
			UpgradeRecipeSettingGUI(player,RecipeName );
			return;
		case 14://�����
			s.SP(player, Sound.ITEM_PICKUP, 0.8F, 1.0F);
			player.closeInventory();
			player.sendMessage(ChatColor.DARK_AQUA+"[����] : ��ȭ�� �ּ� ���ݷ� ��ġ�� �Է��ϼ���!");
			player.sendMessage(ChatColor.GREEN + "("+ChatColor.YELLOW + Integer.MIN_VALUE+ChatColor.GREEN+" ~ "+ChatColor.YELLOW+""+Integer.MAX_VALUE+ChatColor.GREEN+")");

			u.setType(player, "Upgrade");
			u.setString(player, (byte)1, "UMinD");
			u.setString(player, (byte)6, RecipeName);
			return;
		case 15://���� �����
			s.SP(player, Sound.ITEM_PICKUP, 0.8F, 1.0F);
			player.closeInventory();
			player.sendMessage(ChatColor.DARK_AQUA+"[����] : ��ȭ�� �ּ� ���� ���ݷ� ��ġ�� �Է��ϼ���!");
			player.sendMessage(ChatColor.GREEN + "("+ChatColor.YELLOW + Integer.MIN_VALUE+ChatColor.GREEN+" ~ "+ChatColor.YELLOW+""+Integer.MAX_VALUE+ChatColor.GREEN+")");

			u.setType(player, "Upgrade");
			u.setString(player, (byte)1, "UMMinD");
			u.setString(player, (byte)6, RecipeName);
			return;
		case 16://�뷱��
			s.SP(player, Sound.ITEM_PICKUP, 0.8F, 1.0F);
			player.closeInventory();
			player.sendMessage(ChatColor.DARK_AQUA+"[����] : ��ȭ�� �뷱���� �Է��ϼ���!");
			player.sendMessage(ChatColor.GREEN + "("+ChatColor.YELLOW + Integer.MIN_VALUE+ChatColor.GREEN+" ~ "+ChatColor.YELLOW+""+Integer.MAX_VALUE+ChatColor.GREEN+")");

			u.setType(player, "Upgrade");
			u.setString(player, (byte)1, "UB");
			u.setString(player, (byte)6, RecipeName);
			return;
		case 22://���
			s.SP(player, Sound.ITEM_PICKUP, 0.8F, 1.0F);
			player.closeInventory();
			player.sendMessage(ChatColor.DARK_AQUA+"[����] : ��ȭ�� ������ �Է��ϼ���!");
			player.sendMessage(ChatColor.GREEN + "("+ChatColor.YELLOW + Integer.MIN_VALUE+ChatColor.GREEN+" ~ "+ChatColor.YELLOW+""+Integer.MAX_VALUE+ChatColor.GREEN+")");

			u.setType(player, "Upgrade");
			u.setString(player, (byte)1, "UDEF");
			u.setString(player, (byte)6, RecipeName);
			return;
		case 23://��ȣ
			s.SP(player, Sound.ITEM_PICKUP, 0.8F, 1.0F);
			player.closeInventory();
			player.sendMessage(ChatColor.DARK_AQUA+"[����] : ��ȭ�� ��ȣ�� �Է��ϼ���!");
			player.sendMessage(ChatColor.GREEN + "("+ChatColor.YELLOW + Integer.MIN_VALUE+ChatColor.GREEN+" ~ "+ChatColor.YELLOW+""+Integer.MAX_VALUE+ChatColor.GREEN+")");

			u.setType(player, "Upgrade");
			u.setString(player, (byte)1, "UP");
			u.setString(player, (byte)6, RecipeName);
			return;
		case 24://���� ���
			s.SP(player, Sound.ITEM_PICKUP, 0.8F, 1.0F);
			player.closeInventory();
			player.sendMessage(ChatColor.DARK_AQUA+"[����] : ��ȭ�� ���� ������ �Է��ϼ���!");
			player.sendMessage(ChatColor.GREEN + "("+ChatColor.YELLOW + Integer.MIN_VALUE+ChatColor.GREEN+" ~ "+ChatColor.YELLOW+""+Integer.MAX_VALUE+ChatColor.GREEN+")");

			u.setType(player, "Upgrade");
			u.setString(player, (byte)1, "UMDEF");
			u.setString(player, (byte)6, RecipeName);
			return;
		case 25://���� ��ȣ
			s.SP(player, Sound.ITEM_PICKUP, 0.8F, 1.0F);
			player.closeInventory();
			player.sendMessage(ChatColor.DARK_AQUA+"[����] : ��ȭ�� ���� ������ �Է��ϼ���!");
			player.sendMessage(ChatColor.GREEN + "("+ChatColor.YELLOW + Integer.MIN_VALUE+ChatColor.GREEN+" ~ "+ChatColor.YELLOW+""+Integer.MAX_VALUE+ChatColor.GREEN+")");

			u.setType(player, "Upgrade");
			u.setString(player, (byte)1, "UMP");
			u.setString(player, (byte)6, RecipeName);
			return;
		case 31://ũ��Ƽ��
			s.SP(player, Sound.ITEM_PICKUP, 0.8F, 1.0F);
			player.closeInventory();
			player.sendMessage(ChatColor.DARK_AQUA+"[����] : ��ȭ�� ũ��Ƽ���� �Է��ϼ���!");
			player.sendMessage(ChatColor.GREEN + "("+ChatColor.YELLOW + Integer.MIN_VALUE+ChatColor.GREEN+" ~ "+ChatColor.YELLOW+""+Integer.MAX_VALUE+ChatColor.GREEN+")");

			u.setType(player, "Upgrade");
			u.setString(player, (byte)1, "UC");
			u.setString(player, (byte)6, RecipeName);
			return;
		case 32://������
			s.SP(player, Sound.ITEM_PICKUP, 0.8F, 1.0F);
			player.closeInventory();
			player.sendMessage(ChatColor.DARK_AQUA+"[����] : ��ȭ�� �ִ� �������� �Է��ϼ���!");
			player.sendMessage(ChatColor.GREEN + "("+ChatColor.YELLOW + Integer.MIN_VALUE+ChatColor.GREEN+" ~ "+ChatColor.YELLOW+""+Integer.MAX_VALUE+ChatColor.GREEN+")");

			u.setType(player, "Upgrade");
			u.setString(player, (byte)1, "UMD");
			u.setString(player, (byte)6, RecipeName);
			return;
		case 33://����
			s.SP(player, Sound.ITEM_PICKUP, 0.8F, 1.0F);
			player.closeInventory();
			player.sendMessage(ChatColor.DARK_AQUA+"[����] : �� ������ �ϱ� ���� ���� ������ �Է��ϼ���!");
			player.sendMessage(ChatColor.GREEN + "("+ChatColor.YELLOW +0+ChatColor.GREEN+" ~ "+ChatColor.YELLOW+""+Integer.MAX_VALUE+ChatColor.GREEN+")");

			u.setType(player, "Upgrade");
			u.setString(player, (byte)1, "UUL");
			u.setString(player, (byte)6, RecipeName);
			return;
		case 34://���õ�
			s.SP(player, Sound.ITEM_PICKUP, 0.8F, 1.0F);
			player.closeInventory();
			player.sendMessage(ChatColor.DARK_AQUA+"[����] : ���� ���� ������ ���õ��� �Է��ϼ���!");
			player.sendMessage(ChatColor.GREEN + "("+ChatColor.YELLOW +0+ChatColor.GREEN+" ~ "+ChatColor.YELLOW+"100"+ChatColor.GREEN+")");

			u.setType(player, "Upgrade");
			u.setString(player, (byte)1, "UDP");
			u.setString(player, (byte)6, RecipeName);
			return;
		case 37://���� ����
			s.SP(player, Sound.ITEM_PICKUP, 0.8F, 1.0F);
			player.closeInventory();
			player.sendMessage(ChatColor.DARK_AQUA+"[������] : �������� ������ �Է��� �ּ���!");
			player.sendMessage(ChatColor.GOLD + "%enter%"+ChatColor.WHITE + " - ���� ��� ���� -");
			player.sendMessage(ChatColor.WHITE + ""+ChatColor.BOLD + "&l " + ChatColor.BLACK + "&0 "+ChatColor.DARK_BLUE+"&1 "+ChatColor.DARK_GREEN+"&2 "+
			ChatColor.DARK_AQUA + "&3 " +ChatColor.DARK_RED + "&4 " + ChatColor.DARK_PURPLE + "&5 " +
					ChatColor.GOLD + "&6 " + ChatColor.GRAY + "&7 " + ChatColor.DARK_GRAY + "&8 " +
			ChatColor.BLUE + "&9 " + ChatColor.GREEN + "&a " + ChatColor.AQUA + "&b " + ChatColor.RED + "&c " +
					ChatColor.LIGHT_PURPLE + "&d " + ChatColor.YELLOW + "&e "+ChatColor.WHITE + "&f");
			u.setType(player, "Upgrade");
			u.setString(player, (byte)1, "ULC");
			u.setString(player, (byte)6, RecipeName);
			return;
		case 45://���� ���
			s.SP(player, Sound.ITEM_PICKUP, 0.8F, 1.0F);
			UpgradeRecipeGUI(player, 0);
			return;
		case 53://������
			s.SP(player, Sound.PISTON_RETRACT, 0.8F, 1.8F);
			player.closeInventory();
			return;
		}
	}
	
}
