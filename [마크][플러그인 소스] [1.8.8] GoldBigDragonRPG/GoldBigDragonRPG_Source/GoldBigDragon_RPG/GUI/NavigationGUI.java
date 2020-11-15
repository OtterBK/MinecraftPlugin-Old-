package GoldBigDragon_RPG.GUI;

import java.util.Arrays;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.Inventory;

import GoldBigDragon_RPG.Main.Object_UserData;
import GoldBigDragon_RPG.ServerTick.ServerTickMain;
import GoldBigDragon_RPG.Util.YamlController;
import GoldBigDragon_RPG.Util.YamlManager;

public class NavigationGUI extends GUIutil
{
	public void NavigationListGUI(Player player, short page)
	{
		YamlController YC = new YamlController(GoldBigDragon_RPG.Main.Main.plugin);
		YamlManager NavigationConfig =YC.getNewConfig("Navigation/NavigationList.yml");

		Inventory inv = Bukkit.createInventory(null, 54, ChatColor.BLACK + "�׺� ��� : " + (page+1));

		Object[] Navi= NavigationConfig.getConfigurationSection("").getKeys(false).toArray();
		
		byte loc=0;
		for(int count = page*45; count < Navi.length;count++)
		{
			if(count > Navi.length || loc >= 45) break;
			String NaviName = NavigationConfig.getString(Navi[count]+".Name");
			String world = NavigationConfig.getString(Navi[count]+".world");
			int x = NavigationConfig.getInt(Navi[count]+".x");
			int y = NavigationConfig.getInt(Navi[count]+".y");
			int z = NavigationConfig.getInt(Navi[count]+".z");
			int Time = NavigationConfig.getInt(Navi[count]+".time");
			int sensitive = NavigationConfig.getInt(Navi[count]+".sensitive");
			boolean Permition = NavigationConfig.getBoolean(Navi[count]+".onlyOPuse");
			int ShowArrow = NavigationConfig.getInt(Navi[count]+".ShowArrow");
			
			
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
			,ChatColor.YELLOW+"[�� Ŭ���� �׺� ����]",ChatColor.RED+"[Shift + ��Ŭ���� �׺� ����]"), loc, inv);
			loc++;
		}
		
		if(Navi.length-(page*44)>45)
		Stack2(ChatColor.WHITE + "" + ChatColor.BOLD + "���� ������", 323,0,1,Arrays.asList(ChatColor.GRAY + "���� �������� �̵� �մϴ�."), 50, inv);
		if(page!=0)
		Stack2(ChatColor.WHITE + "" + ChatColor.BOLD + "���� ������", 323,0,1,Arrays.asList(ChatColor.GRAY + "���� �������� �̵� �մϴ�."), 48, inv);
		Stack2(ChatColor.WHITE + "" + ChatColor.BOLD + "�� �׺�", 386,0,1,Arrays.asList(ChatColor.GRAY + "�� �׺� �����մϴ�."), 49, inv);
		Stack2(ChatColor.WHITE + "" + ChatColor.BOLD + "���� ���", 323,0,1,Arrays.asList(ChatColor.GRAY + "���� ȭ������ ���ư��ϴ�."), 45, inv);
		Stack2(ChatColor.WHITE + "" + ChatColor.BOLD + "�ݱ�", 324,0,1,Arrays.asList(ChatColor.GRAY + "â�� �ݽ��ϴ�."), 53, inv);
		player.openInventory(inv);
	}

	public void NavigationOptionGUI(Player player, String NaviUTC)
	{
		YamlController YC = new YamlController(GoldBigDragon_RPG.Main.Main.plugin);
		YamlManager NavigationConfig =YC.getNewConfig("Navigation/NavigationList.yml");

		Inventory inv = Bukkit.createInventory(null, 36, ChatColor.BLACK + "�׺� ����");

		String NaviName = NavigationConfig.getString(NaviUTC+".Name");
		String world = NavigationConfig.getString(NaviUTC+".world");
		int x = NavigationConfig.getInt(NaviUTC+".x");
		short y = (short) NavigationConfig.getInt(NaviUTC+".y");
		int z = NavigationConfig.getInt(NaviUTC+".z");
		int Time = NavigationConfig.getInt(NaviUTC+".time");
		short sensitive = (short) NavigationConfig.getInt(NaviUTC+".sensitive");
		boolean Permition = NavigationConfig.getBoolean(NaviUTC+".onlyOPuse");
		byte ShowArrow = (byte) NavigationConfig.getInt(NaviUTC+".ShowArrow");

		String TimeS = ChatColor.BLUE+"[������ �� ���� ����]";
		String PermitionS = ChatColor.BLUE+"[OP�� ��� ����]";
		String sensitiveS = ChatColor.BLUE+"[�ݰ� "+sensitive+"��� �̳��� �������� ����]";
		String ShowArrowS = ChatColor.BLUE+"[�⺻ ȭ��ǥ ���]";
		if(Permition == false)
			PermitionS = ChatColor.BLUE+"[��� ��� ����]";
		if(Time >= 0)
			TimeS = ChatColor.BLUE+"["+Time+"�� ���� ����]";
		switch(ShowArrow)
		{
		default:
			ShowArrowS = ChatColor.BLUE+"[�⺻ ȭ��ǥ ���]";
			break;
		}
		
		Stack2(ChatColor.WHITE + "" + ChatColor.BOLD + "�̸� ����", 386,0,1,Arrays.asList(ChatColor.GRAY + "�׺���̼� �̸��� �����մϴ�.","",ChatColor.BLUE+"[���� �̸�]",ChatColor.WHITE+NaviName,""), 10, inv);
		Stack2(ChatColor.WHITE + "" + ChatColor.BOLD + "��ǥ ����", 358,0,1,Arrays.asList(ChatColor.GRAY + "Ŭ���� ���� ��ġ�� �����մϴ�.","",ChatColor.BLUE+"[���� ����]",ChatColor.BLUE+"���� : "+ChatColor.WHITE+world,ChatColor.BLUE+"��ǥ : " + ChatColor.WHITE+x+","+y+","+z,""), 11, inv);
		Stack2(ChatColor.WHITE + "" + ChatColor.BOLD + "���� �ð�", 347,0,1,Arrays.asList(ChatColor.GRAY + "�׺���̼� ���� �ð��� �����մϴ�.","",TimeS,""), 12, inv);
		Stack2(ChatColor.WHITE + "" + ChatColor.BOLD + "���� �ݰ�", 420,0,1,Arrays.asList(ChatColor.GRAY + "���� ���� ������ �����մϴ�.","",sensitiveS,""), 13, inv);
		Stack2(ChatColor.WHITE + "" + ChatColor.BOLD + "��� ����", 137,0,1,Arrays.asList(ChatColor.GRAY + "�׺���̼� ��� ������ �����մϴ�.","",PermitionS,""), 14, inv);
		Stack2(ChatColor.WHITE + "" + ChatColor.BOLD + "�׺� Ÿ��", 381,0,1,Arrays.asList(ChatColor.GRAY + "�׺���̼� Ÿ���� �����մϴ�.","",ShowArrowS,""), 15, inv);

		Stack2(ChatColor.WHITE + "" + ChatColor.BOLD + "���� ���", 323,0,1,Arrays.asList(ChatColor.GRAY + "���� ȭ������ ���ư��ϴ�."), 27, inv);
		Stack2(ChatColor.WHITE + "" + ChatColor.BOLD + "�ݱ�", 324,0,1,Arrays.asList(ChatColor.GRAY + "â�� �ݽ��ϴ�.",ChatColor.BLACK+NaviUTC), 35, inv);
		player.openInventory(inv);
	}
	
	public void UseNavigationGUI(Player player, short page)
	{
		YamlController YC = new YamlController(GoldBigDragon_RPG.Main.Main.plugin);
		YamlManager NavigationConfig =YC.getNewConfig("Navigation/NavigationList.yml");

		Inventory inv = Bukkit.createInventory(null, 54, ChatColor.BLACK + "�׺� ��� : " + (page+1));

		Object[] Navi= NavigationConfig.getConfigurationSection("").getKeys(false).toArray();
		
		byte loc=0;
		for(int count = page*45; count < Navi.length;count++)
		{
			if(count > Navi.length || loc >= 45) break;
			boolean Permition = NavigationConfig.getBoolean(Navi[count]+".onlyOPuse");
			if(Permition)
			{
				if(player.isOp())
				{
					String NaviName = NavigationConfig.getString(Navi[count]+".Name");
					String world = NavigationConfig.getString(Navi[count]+".world");
					int x = NavigationConfig.getInt(Navi[count]+".x");
					short y = (short) NavigationConfig.getInt(Navi[count]+".y");
					int z = NavigationConfig.getInt(Navi[count]+".z");

					int Time = NavigationConfig.getInt(Navi[count]+".time");
					String TimeS = ChatColor.DARK_AQUA+"<������ �� ���� ����>";
					if(Time >= 0)
						TimeS = ChatColor.DARK_AQUA+"<"+Time+"�� ���� ����>";
					
					Stack2(ChatColor.BLACK + "" + ChatColor.BOLD + Navi[count].toString(), 395,0,1,Arrays.asList(
					ChatColor.YELLOW+""+ChatColor.BOLD+NaviName,"",
					ChatColor.BLUE+"[���� ����]",ChatColor.BLUE+"���� : "+ChatColor.WHITE+world,
					ChatColor.BLUE+"��ǥ : " + ChatColor.WHITE+x+","+y+","+z,"",TimeS,"",ChatColor.YELLOW+"[�� Ŭ���� �׺� ���]"), loc, inv);
					loc++;
				}
			}
			else
			{
				String NaviName = NavigationConfig.getString(Navi[count]+".Name");
				String world = NavigationConfig.getString(Navi[count]+".world");
				int x = NavigationConfig.getInt(Navi[count]+".x");
				short y = (short) NavigationConfig.getInt(Navi[count]+".y");
				int z = NavigationConfig.getInt(Navi[count]+".z");

				int Time = NavigationConfig.getInt(Navi[count]+".time");
				String TimeS = ChatColor.DARK_AQUA+"<������ �� ���� ����>";
				if(Time >= 0)
					TimeS = ChatColor.DARK_AQUA+"<"+Time+"�� ���� ����>";
				
				Stack2(ChatColor.BLACK + "" + ChatColor.BOLD + Navi[count].toString(), 395,0,1,Arrays.asList(
				ChatColor.YELLOW+""+ChatColor.BOLD+NaviName,"",
				ChatColor.BLUE+"[���� ����]",ChatColor.BLUE+"���� : "+ChatColor.WHITE+world,
				ChatColor.BLUE+"��ǥ : " + ChatColor.WHITE+x+","+y+","+z,"",TimeS,"",ChatColor.YELLOW+"[�� Ŭ���� �׺� ���]"), loc, inv);
				loc++;
			}
		}
		
		if(Navi.length-(page*44)>45)
			Stack2(ChatColor.WHITE + "" + ChatColor.BOLD + "���� ������", 323,0,1,Arrays.asList(ChatColor.GRAY + "���� �������� �̵� �մϴ�."), 50, inv);
		if(page!=0)
			Stack2(ChatColor.WHITE + "" + ChatColor.BOLD + "���� ������", 323,0,1,Arrays.asList(ChatColor.GRAY + "���� �������� �̵� �մϴ�."), 48, inv);
		Stack2(ChatColor.WHITE + "" + ChatColor.BOLD + "���� ���", 323,0,1,Arrays.asList(ChatColor.GRAY + "���� ȭ������ ���ư��ϴ�."), 45, inv);
		Stack2(ChatColor.WHITE + "" + ChatColor.BOLD + "�ݱ�", 324,0,1,Arrays.asList(ChatColor.GRAY + "â�� �ݽ��ϴ�."), 53, inv);
		player.openInventory(inv);
	}
	
	
	public void NavigationListGUIClick(InventoryClickEvent event)
	{
		GoldBigDragon_RPG.Effect.Sound s = new GoldBigDragon_RPG.Effect.Sound();
		event.setCancelled(true);
		Player player = (Player) event.getWhoClicked();
		short page =  (short) (Short.parseShort(event.getInventory().getTitle().split(" : ")[1])-1);
		
		switch (event.getSlot())
		{
		case 45://���� ���
			s.SP(player, Sound.ITEM_PICKUP, 0.8F, 1.0F);
			new OPBoxGUI().OPBoxGUI_Main(player, (byte) 1);
			return;
		case 53://������
			s.SP(player, Sound.PISTON_RETRACT, 0.8F, 1.8F);
			player.closeInventory();
			return;
		case 48://���� ������
			s.SP(player, Sound.ITEM_PICKUP, 0.8F, 1.0F);
			NavigationListGUI(player, (short) (page-1));
			return;
		case 49://�� �׺�
			{
				s.SP(player, Sound.PISTON_RETRACT, 0.8F, 1.8F);
				player.closeInventory();
				player.sendMessage(ChatColor.GREEN+"[�׺�] : ���ο� �׺���̼� �̸��� �Է� �� �ּ���!");
				Object_UserData u = new Object_UserData();
				u.setType(player, "Navi");
				u.setString(player, (byte)0, "NN");
			}
			return;
		case 50://���� ������
			s.SP(player, Sound.ITEM_PICKUP, 0.8F, 1.0F);
			NavigationListGUI(player, (short) (page+1));
			return;
		default :
			if(event.isLeftClick() == true)
			{
				s.SP(player, Sound.ITEM_PICKUP, 0.8F, 1.0F);
				NavigationOptionGUI(player, ChatColor.stripColor(event.getCurrentItem().getItemMeta().getDisplayName()));
			}
			else if(event.isShiftClick() == true && event.isRightClick() == true)
			{
				s.SP(player, Sound.LAVA_POP, 0.8F, 1.0F);
				YamlController YC = new YamlController(GoldBigDragon_RPG.Main.Main.plugin);
				YamlManager NavigationConfig =YC.getNewConfig("Navigation/NavigationList.yml");
				NavigationConfig.removeKey(ChatColor.stripColor(event.getCurrentItem().getItemMeta().getDisplayName()));
				NavigationConfig.saveConfig();
				NavigationListGUI(player, page);
			}
			return;
		}
	}
	
	public void NavigationOptionGUIClick(InventoryClickEvent event)
	{
		GoldBigDragon_RPG.Effect.Sound s = new GoldBigDragon_RPG.Effect.Sound();
		event.setCancelled(true);
		Player player = (Player) event.getWhoClicked();

		YamlController YC = new YamlController(GoldBigDragon_RPG.Main.Main.plugin);
		YamlManager NavigationConfig =YC.getNewConfig("Navigation/NavigationList.yml");
		String UTC = ChatColor.stripColor(event.getInventory().getItem(35).getItemMeta().getLore().get(1));
		
		switch (event.getSlot())
		{
		case 10://�̸� ����
			{
				s.SP(player, Sound.ITEM_PICKUP, 1.0F, 0.9F);
				player.closeInventory();
				player.sendMessage(ChatColor.GREEN+"[�׺�] : ���ϴ� �׺���̼� �̸��� �Է� �� �ּ���!");
				Object_UserData u = new Object_UserData();
				u.setType(player, "Navi");
				u.setString(player, (byte)0, "CNN");
				u.setString(player, (byte)1, UTC);
			}
			return;
		case 11://��ǥ ����
			s.SP(player, Sound.ANVIL_LAND, 1.0F, 0.9F);
			NavigationConfig.set(UTC+".world", player.getLocation().getWorld().getName());
			NavigationConfig.set(UTC+".x", (int)player.getLocation().getX());
			NavigationConfig.set(UTC+".y", (int)player.getLocation().getY());
			NavigationConfig.set(UTC+".z", (int)player.getLocation().getZ());
			NavigationConfig.saveConfig();
			NavigationOptionGUI(player, UTC);
			return;
		case 12://���� �ð�
			{
				s.SP(player, Sound.ITEM_PICKUP, 1.0F, 0.9F);
				player.closeInventory();
				player.sendMessage(ChatColor.GREEN+"[�׺�] : �׺���̼� ���� �ð��� �Է� �� �ּ���!");
				player.sendMessage(ChatColor.YELLOW+"(-1��(ã�� �� ����) ~ 3600��(1�ð�))");
				Object_UserData u = new Object_UserData();
				u.setType(player, "Navi");
				u.setString(player, (byte)0, "CNT");
				u.setString(player, (byte)1, UTC);
			}
			return;
		case 13://���� �ݰ�
			{
				s.SP(player, Sound.ITEM_PICKUP, 1.0F, 0.9F);
				player.closeInventory();
				player.sendMessage(ChatColor.GREEN+"[�׺�] : ���� ���� ������ �Է� �� �ּ���!");
				player.sendMessage(ChatColor.YELLOW+"(1 ~ 1000)");
				Object_UserData u = new Object_UserData();
				u.setType(player, "Navi");
				u.setString(player, (byte)0, "CNS");
				u.setString(player, (byte)1, UTC);
			}
			return;
		case 14://��� ����
			s.SP(player, Sound.ANVIL_LAND, 1.0F, 0.9F);
			if(NavigationConfig.getBoolean(UTC+".onlyOPuse"))
				NavigationConfig.set(UTC+".onlyOPuse", false);
			else
				NavigationConfig.set(UTC+".onlyOPuse", true);
			NavigationConfig.saveConfig();
			NavigationOptionGUI(player, UTC);
			return;
		case 15://�׺� Ÿ��
			{
				s.SP(player, Sound.ITEM_PICKUP, 1.0F, 0.9F);
				player.closeInventory();
				player.sendMessage(ChatColor.GREEN+"[�׺�] : �׺���̼� ȭ��ǥ ����� �����ϼ���!");
				player.sendMessage(ChatColor.YELLOW+"(0 ~ 10)");
				Object_UserData u = new Object_UserData();
				u.setType(player, "Navi");
				u.setString(player, (byte)0, "CNA");
				u.setString(player, (byte)1, UTC);
			}
			return;
		case 27://���� ���
			s.SP(player, Sound.ITEM_PICKUP, 0.8F, 1.0F);
			NavigationListGUI(player, (short) 0);
			return;
		case 35://������
			s.SP(player, Sound.PISTON_RETRACT, 0.8F, 1.8F);
			player.closeInventory();
			return;
		}
	}

	public void UseNavigationGUIClick(InventoryClickEvent event)
	{
		GoldBigDragon_RPG.Effect.Sound s = new GoldBigDragon_RPG.Effect.Sound();
		event.setCancelled(true);
		Player player = (Player) event.getWhoClicked();
		short page =  (short) (Short.parseShort(event.getInventory().getTitle().split(" : ")[1])-1);
		
		switch (event.getSlot())
		{
		case 45://���� ���
			s.SP(player, Sound.ITEM_PICKUP, 0.8F, 1.0F);
			new ETCGUI().ETCGUI_Main(player);
			return;
		case 53://������
			s.SP(player, Sound.PISTON_RETRACT, 0.8F, 1.8F);
			player.closeInventory();
			return;

		case 48://���� ������
			s.SP(player, Sound.ITEM_PICKUP, 0.8F, 1.0F);
			UseNavigationGUI(player, (short) (page-1));
			return;
		case 50://���� ������
			s.SP(player, Sound.ITEM_PICKUP, 0.8F, 1.0F);
			UseNavigationGUI(player, (short) (page+1));
			return;
		default :
			if(event.isLeftClick() == true)
			{
				for(int count = 0; count < ServerTickMain.NaviUsingList.size();count++)
				{
					if(ServerTickMain.NaviUsingList.get(count).equals(player.getName()))
					{
						s.SP(player, Sound.ORB_PICKUP, 1.0F, 1.8F);
						player.sendMessage(ChatColor.RED+"[�׺���̼�] : ����� �̹� �׺���̼��� ��� ���Դϴ�!");
						return;
					}
				}
				ServerTickMain.NaviUsingList.add(player.getName());
				YamlController YC = new YamlController(GoldBigDragon_RPG.Main.Main.plugin);
				YamlManager NavigationConfig =YC.getNewConfig("Navigation/NavigationList.yml");
				String UTC = ChatColor.stripColor(event.getCurrentItem().getItemMeta().getDisplayName());
				player.closeInventory();
				s.SP(player, Sound.NOTE_PLING, 1.0F, 1.0F);
				
				GoldBigDragon_RPG.ServerTick.ServerTickScheduleObject STSO = new GoldBigDragon_RPG.ServerTick.ServerTickScheduleObject(GoldBigDragon_RPG.ServerTick.ServerTickMain.nowUTC, "NV");
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
				
				GoldBigDragon_RPG.ServerTick.ServerTickMain.Schedule.put(GoldBigDragon_RPG.ServerTick.ServerTickMain.nowUTC, STSO);
				player.sendMessage(ChatColor.YELLOW+"[�׺���̼�] : ��ã�� �ý����� �����˴ϴ�!");
				player.sendMessage(ChatColor.YELLOW+"(ȭ��ǥ�� ������ ���� ���, [ESC] �� [����] �� [���� ����] ���� [����]�� [���]�� ������ �ּ���!)");
			}
			return;
		}
	}
}
