package GoldBigDragon_RPG.GUI;

import java.util.Arrays;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import GoldBigDragon_RPG.Attack.Damage;
import GoldBigDragon_RPG.Skill.PlayerSkillGUI;
import GoldBigDragon_RPG.Util.YamlController;
import GoldBigDragon_RPG.Util.YamlManager;

public class StatsGUI extends GUIutil
{
	//���� GUIâ�� 1 �������� ������ �ִ� �޼ҵ�//
	public void StatusGUI(Player player)
	{
		Damage dam = new Damage();
	    YamlController YC = new YamlController(GoldBigDragon_RPG.Main.Main.plugin);
	    YamlManager Config = YC.getNewConfig("config.yml");
		
		Inventory inv = Bukkit.createInventory(null, 45, ChatColor.BLACK + "����");

		int MaxStats = new GoldBigDragon_RPG.Main.ServerOption().MaxStats;
		
		Stack2(ChatColor.WHITE + "����", 160,4,1,Arrays.asList(ChatColor.GRAY + "������ Ȯ���մϴ�."), 0, inv);
		Stack2(ChatColor.WHITE + "��ų", 403,0,1,Arrays.asList(ChatColor.GRAY + "��ų�� Ȯ���մϴ�."), 9, inv);
		Stack2(ChatColor.WHITE + "����Ʈ", 358,0,1,Arrays.asList(ChatColor.GRAY + "���� �������� ����Ʈ�� Ȯ���մϴ�."), 18, inv);
		Stack2(ChatColor.WHITE + "�ɼ�", 145,0,1,Arrays.asList(ChatColor.GRAY + "��Ÿ ������ �մϴ�."), 27, inv);
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
		
		ItemStack EXIT = new ItemStack(Material.WOOD_DOOR, 1);
		ItemMeta EXIT_BUTTON = EXIT.getItemMeta();
		EXIT_BUTTON.setDisplayName(ChatColor.WHITE  + "" + ChatColor.BOLD + "�ݱ�");
		EXIT_BUTTON.setLore(Arrays.asList(ChatColor.GRAY + "â�� �ݽ��ϴ�."));
		EXIT.setItemMeta(EXIT_BUTTON);
		inv.setItem(26, EXIT);

		int StatPoint = GoldBigDragon_RPG.Main.ServerOption.PlayerList.get(player.getUniqueId().toString()).getStat_StatPoint();
		if(Config.getBoolean("Server.Like_The_Mabinogi_Online_Stat_System") == true)
		{
			Stack2(ChatColor.GREEN + "    [" + ChatColor.WHITE +""+ChatColor.BOLD + "����"+ChatColor.GREEN + "]", 397,3,1,
					Arrays.asList(ChatColor.WHITE + "[����] : " +ChatColor.BOLD + GoldBigDragon_RPG.Main.ServerOption.PlayerList.get(player.getUniqueId().toString()).getStat_Level(),
							ChatColor.WHITE + "[���� ����] : " +ChatColor.BOLD + GoldBigDragon_RPG.Main.ServerOption.PlayerList.get(player.getUniqueId().toString()).getStat_RealLevel(),
							ChatColor.WHITE + "[����ġ] : " + ChatColor.BOLD + GoldBigDragon_RPG.Main.ServerOption.PlayerList.get(player.getUniqueId().toString()).getStat_EXP() + " / " + GoldBigDragon_RPG.Main.ServerOption.PlayerList.get(player.getUniqueId().toString()).getStat_MaxEXP(),
							ChatColor.AQUA + "[��ų ����Ʈ] : " + ChatColor.WHITE + ChatColor.BOLD + GoldBigDragon_RPG.Main.ServerOption.PlayerList.get(player.getUniqueId().toString()).getStat_SkillPoint()), 13, inv);
		}
		else
		{
			YamlManager PlayerSkillYML = YC.getNewConfig("Skill/PlayerData/"+player.getUniqueId()+".yml");
			Stack2(ChatColor.GREEN + "       [" + ChatColor.WHITE +""+ChatColor.BOLD + "����"+ChatColor.GREEN + "]", 397,3,1,
					Arrays.asList(ChatColor.WHITE + "[����] : " +ChatColor.BOLD + GoldBigDragon_RPG.Main.ServerOption.PlayerList.get(player.getUniqueId().toString()).getStat_Level(),
							ChatColor.WHITE + "[����] : " +ChatColor.BOLD + PlayerSkillYML.getString("Job.Type"),
							ChatColor.WHITE + "[����ġ] : " + ChatColor.BOLD + GoldBigDragon_RPG.Main.ServerOption.PlayerList.get(player.getUniqueId().toString()).getStat_EXP() + " / " + GoldBigDragon_RPG.Main.ServerOption.PlayerList.get(player.getUniqueId().toString()).getStat_MaxEXP(),
							ChatColor.GREEN + "[���� ����Ʈ] : " + ChatColor.WHITE + ChatColor.BOLD + StatPoint,
							ChatColor.AQUA + "[��ų ����Ʈ] : " + ChatColor.WHITE + ChatColor.BOLD + GoldBigDragon_RPG.Main.ServerOption.PlayerList.get(player.getUniqueId().toString()).getStat_SkillPoint()), 13, inv);
		}
		
		int DefaultDamage = 0;
		if(player.getItemInHand().hasItemMeta() == true)
		{
			if(player.getItemInHand().getItemMeta().hasLore() == true)
			{
				if(player.getItemInHand().getItemMeta().getLore().toString().contains(GoldBigDragon_RPG.Main.ServerOption.Damage+" : ") == true)
				{
					switch(player.getItemInHand().getType())
					{
					case WOOD_SPADE :
					case GOLD_SPADE :
						DefaultDamage = 1;
						break;
					case WOOD_PICKAXE :
					case GOLD_PICKAXE:
					case STONE_SPADE:
						DefaultDamage = 2;
						break;
					case WOOD_AXE:
					case GOLD_AXE:
					case STONE_PICKAXE:
					case IRON_SPADE:
						DefaultDamage = 3;
						break;
					case WOOD_SWORD:
					case GOLD_SWORD:
					case STONE_AXE:
					case IRON_PICKAXE:
					case DIAMOND_SPADE:
						DefaultDamage = 4;
						break;
					case STONE_SWORD:
					case IRON_AXE:
					case DIAMOND_PICKAXE:
						DefaultDamage = 5;
						break;
					case IRON_SWORD:
					case DIAMOND_AXE:
						DefaultDamage = 6;
						break;
					case DIAMOND_SWORD:
						DefaultDamage = 7;
						break;
					}
				}
			}
		}
		int EquipmentStat = dam.getPlayerEquipmentStat(player, "STR", null, false)[0];
		int PlayerStat = GoldBigDragon_RPG.Main.ServerOption.PlayerList.get(player.getUniqueId().toString()).getStat_STR();
		if(PlayerStat > MaxStats)
			PlayerStat = MaxStats;
		String Additional = ChatColor.RED +""+ChatColor.BOLD+(dam.CombatMinDamageGet(player,DefaultDamage,PlayerStat)) + " ~ " + (dam.CombatMaxDamageGet(player,DefaultDamage, PlayerStat));
		String CurrentStat;
		if(EquipmentStat == 0)
			CurrentStat = ChatColor.WHITE +""+ChatColor.BOLD +""+ PlayerStat;
		else if(EquipmentStat > 0)
			CurrentStat = ChatColor.YELLOW +""+ChatColor.BOLD +""+ (PlayerStat + EquipmentStat) +ChatColor.WHITE + "("+ PlayerStat +")";
		else
			CurrentStat = ChatColor.RED +""+ChatColor.BOLD +""+(PlayerStat + EquipmentStat) +ChatColor.WHITE + "("+ PlayerStat+")";
		String lore = GoldBigDragon_RPG.Main.ServerOption.STR_Lore;
		lore = LineUp(CurrentStat, (byte) (GoldBigDragon_RPG.Main.ServerOption.STR.length()+20))+"%enter%"+lore.replace("%stat%", GoldBigDragon_RPG.Main.ServerOption.STR)
				+"%enter%"+ChatColor.AQUA + "" + ChatColor.BOLD +"[�߰� ���� ���ݷ�]%enter%"+LineUp(Additional, (byte) 24);
		
		Stack2(ChatColor.DARK_RED + LineUp(ChatColor.RED+"[" + ChatColor.WHITE +""+ChatColor.BOLD + ""+GoldBigDragon_RPG.Main.ServerOption.STR+""+ChatColor.DARK_RED + "]", (byte) 24), 267,0,1,
				Arrays.asList(lore.split("%enter%")), 20, inv);

		int DEX = GoldBigDragon_RPG.Main.ServerOption.PlayerList.get(player.getUniqueId().toString()).getStat_DEX();
		EquipmentStat=dam.getPlayerEquipmentStat(player, "DEX", null, false)[0];
		if(DEX > MaxStats)
			DEX = MaxStats;
		Additional = ChatColor.RED + "" + ChatColor.BOLD + "" + dam.returnRangeDamageValue(player, DEX, 0, true) + " ~ " + dam.returnRangeDamageValue(player, DEX, 0, false);
		if(EquipmentStat == 0)
			CurrentStat = ChatColor.WHITE +""+ChatColor.BOLD +""+ DEX;
		else if(EquipmentStat > 0)
			CurrentStat = ChatColor.YELLOW +""+ChatColor.BOLD +""+ (DEX + EquipmentStat) +ChatColor.WHITE + "("+ DEX+")";
		else
			CurrentStat = ChatColor.RED +""+ChatColor.BOLD +""+(DEX + EquipmentStat) +ChatColor.WHITE + "("+ DEX+")";

		lore = GoldBigDragon_RPG.Main.ServerOption.DEX_Lore;
		lore = LineUp(CurrentStat, (byte) (GoldBigDragon_RPG.Main.ServerOption.DEX.length()+20))+"%enter%"+lore.replace("%stat%", GoldBigDragon_RPG.Main.ServerOption.DEX)
					+"%enter%"+ChatColor.AQUA + "" + ChatColor.BOLD +"[�߰� ���Ÿ� ���ݷ�]%enter%"+LineUp(Additional, (byte) 24);
			
		Stack2(LineUp(ChatColor.GREEN+"[" + ChatColor.WHITE +""+ChatColor.BOLD + ""+GoldBigDragon_RPG.Main.ServerOption.DEX+""+ChatColor.GREEN + "]", (byte) 24), 261,0,1,
				Arrays.asList(lore.split("%enter%")), 21, inv);
		
		int INT = GoldBigDragon_RPG.Main.ServerOption.PlayerList.get(player.getUniqueId().toString()).getStat_INT();
		if(INT > MaxStats)
			INT = MaxStats;
		EquipmentStat=dam.getPlayerEquipmentStat(player, "INT", null, false)[0];
		Additional = ChatColor.RED + "" + ChatColor.BOLD + "" + ((INT+dam.getPlayerEquipmentStat(player, "INT", null, false)[0])*0.6+100) + " %";
		if(EquipmentStat == 0)
			CurrentStat = ChatColor.WHITE +""+ChatColor.BOLD +""+ INT;
		else if(EquipmentStat > 0)
			CurrentStat = ChatColor.YELLOW +""+ChatColor.BOLD +""+ (INT + EquipmentStat) +ChatColor.WHITE + "("+ INT+")";
		else
			CurrentStat = ChatColor.RED +""+ChatColor.BOLD +""+(INT + EquipmentStat) +ChatColor.WHITE + "("+ INT +")";

		lore = GoldBigDragon_RPG.Main.ServerOption.INT_Lore;
		lore = LineUp(CurrentStat, (byte) (GoldBigDragon_RPG.Main.ServerOption.INT.length()+20))+"%enter%"+lore.replace("%stat%", GoldBigDragon_RPG.Main.ServerOption.INT)
					+"%enter%"+ChatColor.AQUA + "" + ChatColor.BOLD +"[�߰� ��ų ���ݷ�]%enter%"+LineUp(Additional, (byte) 24);
			
		Stack2(LineUp(ChatColor.AQUA + "[" + ChatColor.WHITE +""+ChatColor.BOLD + ""+GoldBigDragon_RPG.Main.ServerOption.INT+""+ChatColor.AQUA + "]",(byte) 24), 369,0,1,
				Arrays.asList(lore.split("%enter%")), 22, inv);

		int WILL = GoldBigDragon_RPG.Main.ServerOption.PlayerList.get(player.getUniqueId().toString()).getStat_WILL();
		if(WILL > MaxStats)
			WILL = MaxStats;
		EquipmentStat=dam.getPlayerEquipmentStat(player, "WILL", null, false)[0];
		Additional = ChatColor.RED + "" + ChatColor.BOLD + "" + ((WILL+dam.getPlayerEquipmentStat(player, "WILL", null, false)[0])*0.6+100) + " %";
		if(EquipmentStat == 0)
			CurrentStat = ChatColor.WHITE +""+ChatColor.BOLD +""+ WILL;
		else if(EquipmentStat > 0)
			CurrentStat = ChatColor.YELLOW +""+ChatColor.BOLD +""+ (WILL + EquipmentStat) +ChatColor.WHITE + "("+ WILL+")";
		else
			CurrentStat = ChatColor.RED +""+ChatColor.BOLD +""+(WILL + EquipmentStat) +ChatColor.WHITE + "("+ WILL+")";

		lore = GoldBigDragon_RPG.Main.ServerOption.WILL_Lore;
		lore = LineUp(CurrentStat, (byte) (GoldBigDragon_RPG.Main.ServerOption.WILL.length()+20))+"%enter%"+lore.replace("%stat%", GoldBigDragon_RPG.Main.ServerOption.WILL)
					+"%enter%"+ChatColor.AQUA + "" + ChatColor.BOLD +"[�߰� ��ų ���ݷ�]%enter%"+LineUp(Additional, (byte) 24);
			
		Stack2(LineUp(ChatColor.GRAY + "[" + ChatColor.WHITE +""+ChatColor.BOLD + ""+GoldBigDragon_RPG.Main.ServerOption.WILL+""+ChatColor.GRAY + "]",(byte) 24), 370,0,1,
				Arrays.asList(lore.split("%enter%")), 23, inv);
		
		int LUK = GoldBigDragon_RPG.Main.ServerOption.PlayerList.get(player.getUniqueId().toString()).getStat_LUK();
		if(LUK > MaxStats)
			LUK = MaxStats;
		EquipmentStat=dam.getPlayerEquipmentStat(player, "LUK", null, false)[0];
		if(EquipmentStat == 0)
			CurrentStat = ChatColor.WHITE +""+ChatColor.BOLD +""+ LUK;
		else if(EquipmentStat > 0)
			CurrentStat = ChatColor.YELLOW +""+ChatColor.BOLD +""+ (LUK + EquipmentStat) +ChatColor.WHITE + "("+ LUK+")";
		else
			CurrentStat = ChatColor.RED +""+ChatColor.BOLD +""+(LUK + EquipmentStat) +ChatColor.WHITE + "("+ LUK+")";

		lore = GoldBigDragon_RPG.Main.ServerOption.LUK_Lore;
		lore = LineUp(CurrentStat, (byte) (GoldBigDragon_RPG.Main.ServerOption.LUK.length()+20))+"%enter%"+lore.replace("%stat%", GoldBigDragon_RPG.Main.ServerOption.LUK)
					+"%enter%";
			
		Stack2(LineUp(ChatColor.YELLOW + "[" + ChatColor.WHITE +""+ChatColor.BOLD + ""+GoldBigDragon_RPG.Main.ServerOption.LUK+""+ChatColor.YELLOW + "]",(byte) 24), 322,0,1,
				Arrays.asList(lore.split("%enter%")), 24, inv);


		if(Config.getBoolean("Server.Like_The_Mabinogi_Online_Stat_System") == false)
		{
			Stack2(ChatColor.GOLD + "    [" + ChatColor.WHITE +""+ChatColor.BOLD + ""+GoldBigDragon_RPG.Main.ServerOption.STR+" ���"+ChatColor.GOLD + "]", 399,0,1,
					Arrays.asList(ChatColor.GRAY + ""+GoldBigDragon_RPG.Main.ServerOption.STR+" ������ �Ѵܰ� ��� ��ŵ�ϴ�.",ChatColor.GRAY + "���� ���� ����Ʈ : "+StatPoint), 29, inv);
			Stack2(ChatColor.GOLD + "    [" + ChatColor.WHITE +""+ChatColor.BOLD + ""+GoldBigDragon_RPG.Main.ServerOption.DEX+" ���"+ChatColor.GOLD + "]", 399,0,1,
					Arrays.asList(ChatColor.GRAY + ""+GoldBigDragon_RPG.Main.ServerOption.DEX+" ������ �Ѵܰ� ��� ��ŵ�ϴ�.",ChatColor.GRAY + "���� ���� ����Ʈ : "+StatPoint), 30, inv);
			Stack2(ChatColor.GOLD + "    [" + ChatColor.WHITE +""+ChatColor.BOLD + ""+GoldBigDragon_RPG.Main.ServerOption.INT+" ���"+ChatColor.GOLD + "]", 399,0,1,
					Arrays.asList(ChatColor.GRAY + ""+GoldBigDragon_RPG.Main.ServerOption.INT+" ������ �Ѵܰ� ��� ��ŵ�ϴ�.",ChatColor.GRAY + "���� ���� ����Ʈ : "+StatPoint), 31, inv);
			Stack2(ChatColor.GOLD + "    [" + ChatColor.WHITE +""+ChatColor.BOLD + ""+GoldBigDragon_RPG.Main.ServerOption.WILL+" ���"+ChatColor.GOLD + "]", 399,0,1,
					Arrays.asList(ChatColor.GRAY + ""+GoldBigDragon_RPG.Main.ServerOption.WILL+" ������ �Ѵܰ� ��� ��ŵ�ϴ�.",ChatColor.GRAY + "���� ���� ����Ʈ : "+StatPoint), 32, inv);
			Stack2(ChatColor.GOLD + "    [" + ChatColor.WHITE +""+ChatColor.BOLD + ""+GoldBigDragon_RPG.Main.ServerOption.LUK+" ���"+ChatColor.GOLD + "]", 399,0,1,
					Arrays.asList(ChatColor.GRAY + ""+GoldBigDragon_RPG.Main.ServerOption.LUK+" ������ �Ѵܰ� ��� ��ŵ�ϴ�.",ChatColor.GRAY + "���� ���� ����Ʈ : "+StatPoint), 33, inv);
		}
		GoldBigDragon_RPG.Attack.Damage d = new GoldBigDragon_RPG.Attack.Damage();
		Stack2(ChatColor.GRAY + "    [" + ChatColor.WHITE +""+ChatColor.BOLD + "���"+ChatColor.GRAY + "]", 307,0,1,
				Arrays.asList(ChatColor.WHITE + "���� ��� : "+ChatColor.WHITE +(GoldBigDragon_RPG.Main.ServerOption.PlayerList.get(player.getUniqueId().toString()).getStat_DEF()+d.getPlayerEquipmentStat(player, "���", null, false)[0]),
						ChatColor.GRAY + "�߰� ���� ��ȣ : "+ChatColor.WHITE + (GoldBigDragon_RPG.Main.ServerOption.PlayerList.get(player.getUniqueId().toString()).getStat_Protect()+d.getPlayerEquipmentStat(player, "��ȣ", null, false)[0]),
						ChatColor.AQUA + "�߰� ���� ��� : "+ChatColor.WHITE + (GoldBigDragon_RPG.Main.ServerOption.PlayerList.get(player.getUniqueId().toString()).getStat_Magic_DEF()+d.getMagicDEF(player,INT)),
						ChatColor.DARK_AQUA + "�߰� ���� ��ȣ : "+ChatColor.WHITE + (GoldBigDragon_RPG.Main.ServerOption.PlayerList.get(player.getUniqueId().toString()).getStat_Magic_Protect()+d.getMagicProtect(player, INT))), 38, inv);

		Stack2(ChatColor.RED + "    [" + ChatColor.WHITE +""+ChatColor.BOLD + "����"+ChatColor.RED + "]", 409,0,1,
				Arrays.asList(ChatColor.RED + "�߰� ���� ��� ���� : "+ChatColor.WHITE + (GoldBigDragon_RPG.Main.ServerOption.PlayerList.get(player.getUniqueId().toString()).getStat_DEFcrash()+d.getDEFcrash(player, DEX)),
						ChatColor.BLUE + "�߰� ���� ��� ���� : "+ChatColor.WHITE + (GoldBigDragon_RPG.Main.ServerOption.PlayerList.get(player.getUniqueId().toString()).getStat_MagicDEFcrash()+d.getMagicDEFcrash(player, INT))), 39, inv);
		
		Stack2(ChatColor.GREEN + "    [" + ChatColor.WHITE +""+ChatColor.BOLD + "��ȸ"+ChatColor.GREEN + "]", 377,0,1,
				Arrays.asList(ChatColor.GREEN + "�߰� �뷱�� : "+ChatColor.WHITE + d.getBalance(player, DEX, GoldBigDragon_RPG.Main.ServerOption.PlayerList.get(player.getUniqueId().toString()).getStat_Balance())+"%",
						ChatColor.YELLOW + "�߰� ũ��Ƽ�� : "+ChatColor.WHITE + d.getCritical(player,LUK, WILL,GoldBigDragon_RPG.Main.ServerOption.PlayerList.get(player.getUniqueId().toString()).getStat_Critical())+"%"), 42, inv);
		
		player.openInventory(inv);
	}
	
	//���� GUIâ ���� �������� ������ ��, �ش� �����ܿ� ����� �ִ� �޼ҵ�1   -���� GUI, ���ǹڽ�, Ŀ���� ����GUI-//
	public void StatusInventoryclick(InventoryClickEvent event)
	{
	    YamlController YC = new YamlController(GoldBigDragon_RPG.Main.Main.plugin);
		event.setCancelled(true);
		Player player = (Player) event.getWhoClicked();
		GoldBigDragon_RPG.Effect.Sound s = new GoldBigDragon_RPG.Effect.Sound();
		YamlManager Config = YC.getNewConfig("config.yml");
		int MaxStats = new GoldBigDragon_RPG.Main.ServerOption().MaxStats;
		switch (event.getSlot())
		{
		case 36:
			s.SP(player, Sound.ITEM_PICKUP, 0.8F, 1.0F);
			ETCGUI EGUI = new ETCGUI();
			EGUI.ETCGUI_Main(player);
			break;
		case 9:
			s.SP(player, Sound.ITEM_PICKUP, 0.8F, 1.0F);
			PlayerSkillGUI PGUI = new PlayerSkillGUI();
			PGUI.MainSkillsListGUI(player, (short) 0);
			break;
		case 18:
			GoldBigDragon_RPG.Quest.QuestGUI QGUI = new GoldBigDragon_RPG.Quest.QuestGUI();
			QGUI.MyQuestListGUI(player, (short) 0);
			s.SP(player, Sound.ITEM_PICKUP, 0.8F, 1.0F);
			break;
		case 27:
			GoldBigDragon_RPG.GUI.OptionGUI oGUI = new GoldBigDragon_RPG.GUI.OptionGUI();
			s.SP(player, Sound.ITEM_PICKUP, 0.8F, 1.0F);
			oGUI.optionGUI(player);
			break;
		case 29:
			if(Config.getBoolean("Server.Like_The_Mabinogi_Online_Stat_System") == false)
				if(GoldBigDragon_RPG.Main.ServerOption.PlayerList.get(player.getUniqueId().toString()).getStat_StatPoint() >= 1)
				{
					if(GoldBigDragon_RPG.Main.ServerOption.PlayerList.get(player.getUniqueId().toString()).getStat_STR() < MaxStats)
					{
						GoldBigDragon_RPG.Main.ServerOption.PlayerList.get(player.getUniqueId().toString()).addStat_StatPoint(-1);
						GoldBigDragon_RPG.Main.ServerOption.PlayerList.get(player.getUniqueId().toString()).addStat_STR(1);
						s.SP(player, Sound.ITEM_PICKUP, 0.8F, 1.0F);
					}
					else
					{
						s.SP(player, Sound.ORB_PICKUP, 1.0F, 1.8F);
						new GoldBigDragon_RPG.Effect.PacketSender().sendActionBar(player, ChatColor.RED+""+ChatColor.BOLD+"[�ش� �ɷ��� �� �̻� ��½�ų �� �����ϴ�!]");
					}
				}
			StatusGUI(player);
			break;
		case 30:
			if(Config.getBoolean("Server.Like_The_Mabinogi_Online_Stat_System") == false)
				if(GoldBigDragon_RPG.Main.ServerOption.PlayerList.get(player.getUniqueId().toString()).getStat_StatPoint() >= 1)
				{
					if(GoldBigDragon_RPG.Main.ServerOption.PlayerList.get(player.getUniqueId().toString()).getStat_DEX() < MaxStats)
					{
						GoldBigDragon_RPG.Main.ServerOption.PlayerList.get(player.getUniqueId().toString()).addStat_StatPoint(-1);
						GoldBigDragon_RPG.Main.ServerOption.PlayerList.get(player.getUniqueId().toString()).addStat_DEX(1);
						s.SP(player, Sound.ITEM_PICKUP, 0.8F, 1.0F);
					}
					else
					{
						s.SP(player, Sound.ORB_PICKUP, 1.0F, 1.8F);
						new GoldBigDragon_RPG.Effect.PacketSender().sendActionBar(player, ChatColor.RED+""+ChatColor.BOLD+"[�ش� �ɷ��� �� �̻� ��½�ų �� �����ϴ�!]");
					}
				}
			StatusGUI(player);
			break;
		case 31:
			if(Config.getBoolean("Server.Like_The_Mabinogi_Online_Stat_System") == false)
				if(GoldBigDragon_RPG.Main.ServerOption.PlayerList.get(player.getUniqueId().toString()).getStat_StatPoint() >= 1)
				{
					if(GoldBigDragon_RPG.Main.ServerOption.PlayerList.get(player.getUniqueId().toString()).getStat_INT() < MaxStats)
					{
						GoldBigDragon_RPG.Main.ServerOption.PlayerList.get(player.getUniqueId().toString()).addStat_StatPoint(-1);
						GoldBigDragon_RPG.Main.ServerOption.PlayerList.get(player.getUniqueId().toString()).addStat_INT(1);
						s.SP(player, Sound.ITEM_PICKUP, 0.8F, 1.0F);
					}
					else
					{
						s.SP(player, Sound.ORB_PICKUP, 1.0F, 1.8F);
						new GoldBigDragon_RPG.Effect.PacketSender().sendActionBar(player, ChatColor.RED+""+ChatColor.BOLD+"[�ش� �ɷ��� �� �̻� ��½�ų �� �����ϴ�!]");
					}

				}
			StatusGUI(player);
			break;
		case 32:
			if(Config.getBoolean("Server.Like_The_Mabinogi_Online_Stat_System") == false)
				if(GoldBigDragon_RPG.Main.ServerOption.PlayerList.get(player.getUniqueId().toString()).getStat_StatPoint() >= 1)
				{
					if(GoldBigDragon_RPG.Main.ServerOption.PlayerList.get(player.getUniqueId().toString()).getStat_WILL() < MaxStats)
					{
						GoldBigDragon_RPG.Main.ServerOption.PlayerList.get(player.getUniqueId().toString()).addStat_StatPoint(-1);
						GoldBigDragon_RPG.Main.ServerOption.PlayerList.get(player.getUniqueId().toString()).addStat_WILL(1);
						s.SP(player, Sound.ITEM_PICKUP, 0.8F, 1.0F);
					}
					else
					{
						s.SP(player, Sound.ORB_PICKUP, 1.0F, 1.8F);
						new GoldBigDragon_RPG.Effect.PacketSender().sendActionBar(player, ChatColor.RED+""+ChatColor.BOLD+"[�ش� �ɷ��� �� �̻� ��½�ų �� �����ϴ�!]");
					}
				}
			StatusGUI(player);
			break;
		case 33:
			if(Config.getBoolean("Server.Like_The_Mabinogi_Online_Stat_System") == false)
				if(GoldBigDragon_RPG.Main.ServerOption.PlayerList.get(player.getUniqueId().toString()).getStat_StatPoint() >= 1)
				{
					if(GoldBigDragon_RPG.Main.ServerOption.PlayerList.get(player.getUniqueId().toString()).getStat_LUK() < MaxStats)
					{
						GoldBigDragon_RPG.Main.ServerOption.PlayerList.get(player.getUniqueId().toString()).addStat_StatPoint(-1);
						GoldBigDragon_RPG.Main.ServerOption.PlayerList.get(player.getUniqueId().toString()).addStat_LUK(1);
						s.SP(player, Sound.ITEM_PICKUP, 0.8F, 1.0F);
					}
					else
					{
						s.SP(player, Sound.ORB_PICKUP, 1.0F, 1.8F);
						new GoldBigDragon_RPG.Effect.PacketSender().sendActionBar(player, ChatColor.RED+""+ChatColor.BOLD+"[�ش� �ɷ��� �� �̻� ��½�ų �� �����ϴ�!]");
					}
				}
			StatusGUI(player);
			break;
		case 26:
			s.SP(player, Sound.PISTON_RETRACT, 0.8F, 1.8F);
			player.closeInventory();
			break;
		}
		return;
	}
	
	
	public String LineUp(String RawString,byte size)
	{
		if(RawString.length()>=size)
			return RawString;
		else
		{
			short spaceSize = (short) (size - RawString.length());
			StringBuffer TempString = new StringBuffer();
			for(short count = 0; count < spaceSize/2; count++)
				TempString.append(" ");
			TempString.append(RawString);
			for(short count = 0; count < spaceSize/2; count++)
				TempString.append(" ");
			return TempString.toString();
		}
	}
}
