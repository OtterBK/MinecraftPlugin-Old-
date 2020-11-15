package GoldBigDragon_RPG.GUI;

import java.util.Arrays;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.Inventory;

import GoldBigDragon_RPG.Main.Object_UserData;
import GoldBigDragon_RPG.Skill.PlayerSkillGUI;
import GoldBigDragon_RPG.Util.YamlController;
import GoldBigDragon_RPG.Util.YamlManager;

public final class ETCGUI extends GUIutil
{
	private GoldBigDragon_RPG.Effect.Sound s = new GoldBigDragon_RPG.Effect.Sound();
	
	public void ETCGUI_Main(Player player)
	{
		Inventory inv = Bukkit.createInventory(null, 45, ChatColor.BLACK + "��Ÿ");

		Stack2(ChatColor.WHITE + "����", 397,3,1,Arrays.asList(ChatColor.GRAY + "������ Ȯ���մϴ�."), 0, inv);
		Stack2(ChatColor.WHITE + "��ų", 403,0,1,Arrays.asList(ChatColor.GRAY + "��ų�� Ȯ���մϴ�."), 9, inv);
		Stack2(ChatColor.WHITE + "����Ʈ", 358,0,1,Arrays.asList(ChatColor.GRAY + "���� �������� ����Ʈ�� Ȯ���մϴ�."), 18, inv);
		Stack2(ChatColor.WHITE + "�ɼ�", 145,0,1,Arrays.asList(ChatColor.GRAY + "��Ÿ ������ �մϴ�."), 27, inv);
		Stack2(ChatColor.WHITE + "��Ÿ", 160,4,1,Arrays.asList(ChatColor.GRAY + "��Ÿ ������ Ȯ���մϴ�."), 36, inv);
		
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

		Stack2(ChatColor.WHITE  + "" + ChatColor.BOLD + "���̵�", 340,0,1,Arrays.asList(ChatColor.GRAY + "������ ���� ������ �˾ƺ��ϴ�."), 2, inv);
		Stack2(ChatColor.WHITE  + "" + ChatColor.BOLD + "��Ƽ", 389,0,1,Arrays.asList(ChatColor.GRAY + "��Ƽ�� ���� ������ Ȯ���մϴ�."), 3, inv);
		Stack2(ChatColor.WHITE  + "" + ChatColor.BOLD + "����", 345,0,1,Arrays.asList(ChatColor.GRAY + "���� ������ ������ Ȯ���մϴ�."), 4, inv);
		Stack2(ChatColor.WHITE  + "" + ChatColor.BOLD + "ģ��", 397,3,1,Arrays.asList(ChatColor.GRAY + "ģ�� ����� Ȯ���մϴ�."), 5, inv);
		Stack2(ChatColor.WHITE  + "" + ChatColor.BOLD + "�׺���̼�", 358,3,1,Arrays.asList(ChatColor.GRAY + "������ ������ �׺���̼���",ChatColor.GRAY+"���� ��ŵ�ϴ�."), 6, inv);
		
		Stack2(ChatColor.WHITE  + "" + ChatColor.BOLD + "�ݱ�", 324,0,1,Arrays.asList(ChatColor.GRAY + "â�� �ݽ��ϴ�."), 26, inv);

		player.openInventory(inv);
	}

	public void Information(Player player)
	{
		Inventory inv = Bukkit.createInventory(null, 54, ChatColor.BLACK + "���̵�");
	  	YamlController YC = new YamlController(GoldBigDragon_RPG.Main.Main.plugin);
		YamlManager NewBieYM = YC.getNewConfig("ETC/NewBie.yml");

		if(NewBieYM.contains("Guide")==false)
		{
			NewBieYM.set("Guide.1", null);
			NewBieYM.saveConfig();
		}
		Object[] a= NewBieYM.getConfigurationSection("Guide").getKeys(false).toArray();

		byte loc = 0;
		for(short count = 0; count < a.length;count++)
			if(NewBieYM.getItemStack("Guide."+count) != null)
			{
				ItemStackStack(NewBieYM.getItemStack("Guide."+count), loc, inv);
				loc++;
			}
		Stack2(ChatColor.WHITE + "" + ChatColor.BOLD + "���� ���", 323,0,1,Arrays.asList(ChatColor.GRAY + "���� ȭ������ ���ư��ϴ�."), 45, inv);
		Stack2(ChatColor.WHITE +""+ ChatColor.BOLD + "�ݱ�", 324,0,1,Arrays.asList(ChatColor.GRAY + "�۾� ������ â�� �ݽ��ϴ�."), 53, inv);
		
		player.openInventory(inv);
	}
	
	public void FriendsGUI(Player player, short page)
	{
	  	YamlController YC = new YamlController(GoldBigDragon_RPG.Main.Main.plugin);
		YamlManager FriendsList  = YC.getNewConfig("Friend/"+player.getUniqueId().toString()+".yml");
		
		YamlManager SideFriendsList  = null;
		if(FriendsList.contains("Name")==false)
		{
			FriendsList.set("Name", player.getName());
			FriendsList.set("Friends.1", null);
			FriendsList.set("Waitting.1", null);
			FriendsList.saveConfig();
		}
		Inventory inv = Bukkit.createInventory(null, 54, ChatColor.BLACK + "ģ�� ��� : " + (page+1));

		if(FriendsList.getConfigurationSection("Waitting").getKeys(false).size()!=0)
			Stack(ChatColor.WHITE + "" + ChatColor.BOLD + "ģ�� ��û", 386,0,1,Arrays.asList(ChatColor.GRAY + "ģ�� ��û�� ��� �� �����Դϴ�!","",ChatColor.DARK_AQUA+"[   ������� ��û   ]",ChatColor.WHITE+""+ChatColor.BOLD+FriendsList.getConfigurationSection("Waitting").getKeys(false).size()+ChatColor.DARK_AQUA+" ��"), 52, inv);
		
		Object[] Friends= FriendsList.getConfigurationSection("Friends").getKeys(false).toArray();
		byte loc=0;
		Long nowTime = new GoldBigDragon_RPG.Util.ETC().getNowUTC();
		for(int count = page*45; count < Friends.length;count++)
		{
			if(loc >= 45) break;
			Player target = (Player) Bukkit.getServer().getPlayer(Friends[count].toString());
			Long AcceptedTime = FriendsList.getLong("Friends."+Friends[count].toString());
			Long WaitingTime = (nowTime-AcceptedTime)/1000;
			byte day = 0;
			
			day = (byte) (WaitingTime/86400);
			WaitingTime = WaitingTime-(86400*day);

			String TimeSetting=day+"�� ° ģ�� ���� ���� ��";
			
			if(target!=null)
			{
				Stack2(ChatColor.YELLOW+""+ChatColor.BOLD+target.getName(), 166, 0, 1, Arrays.asList(ChatColor.GRAY+"[   ��������   ]","",ChatColor.RED+"[Shift + �� Ŭ���� ģ�� ����]","","",ChatColor.GOLD+TimeSetting), loc, inv);
				SideFriendsList = YC.getNewConfig("Friend/"+target.getUniqueId().toString()+".yml");
				Object[] SideFriends = SideFriendsList.getConfigurationSection("Friends").getKeys(false).toArray();
				for(short counter=0;counter<SideFriends.length;counter++)
				{
					if(SideFriends[counter].equals(player.getName()))
						if(target.isOnline())
						{
							ItemStackStack(getPlayerSkull(ChatColor.YELLOW+""+ChatColor.BOLD+target.getName(), 1, Arrays.asList(ChatColor.DARK_AQUA+"[   �¶���   ]","",ChatColor.DARK_AQUA+"[����] : "+ChatColor.WHITE+target.getLocation().getWorld().getName(),
							ChatColor.DARK_AQUA+"[��ǥ] : "+ChatColor.WHITE+""+(int)target.getLocation().getX()+","+(int)target.getLocation().getY()+","+(int)target.getLocation().getZ(),
							"",ChatColor.RED+"[Shift + �� Ŭ���� ģ�� ����]","","",ChatColor.GOLD+TimeSetting), target.getName()), loc, inv);
							break;
						}
				}
			}
			else
				Stack2(ChatColor.YELLOW+""+ChatColor.BOLD+Friends[count].toString(), 166, 0, 1, Arrays.asList(ChatColor.GRAY+"[   ��������   ]","",ChatColor.RED+"[Shift + �� Ŭ���� ģ�� ����]","","",ChatColor.GOLD+TimeSetting), loc, inv);
			
			loc++;
		}
		
		if(Friends.length-(page*44)>45)
		Stack(ChatColor.WHITE + "" + ChatColor.BOLD + "���� ������", 323,0,1,Arrays.asList(ChatColor.GRAY + "���� �������� �̵� �մϴ�."), 50, inv);
		if(page!=0)
		Stack(ChatColor.WHITE + "" + ChatColor.BOLD + "���� ������", 323,0,1,Arrays.asList(ChatColor.GRAY + "���� �������� �̵� �մϴ�."), 48, inv);
		Stack(ChatColor.WHITE + "" + ChatColor.BOLD + "�� ģ��", 397,3,1,Arrays.asList(ChatColor.GRAY + "���ο� ģ���� �߰��մϴ�."), 49, inv);
		Stack(ChatColor.WHITE + "" + ChatColor.BOLD + "���� ���", 323,0,1,Arrays.asList(ChatColor.GRAY + "���� ȭ������ ���ư��ϴ�."), 45, inv);
		Stack(ChatColor.WHITE + "" + ChatColor.BOLD + "�ݱ�", 324,0,1,Arrays.asList(ChatColor.GRAY + "â�� �ݽ��ϴ�."), 53, inv);
		player.openInventory(inv);
	}
	
	public void WaittingFriendsGUI(Player player, short page)
	{
	  	YamlController YC = new YamlController(GoldBigDragon_RPG.Main.Main.plugin);
		YamlManager FriendsList  = YC.getNewConfig("Friend/"+player.getUniqueId().toString()+".yml");
		if(FriendsList.contains("Name")==false)
		{
			FriendsList.set("Name", player.getName());
			FriendsList.set("Friends.1", null);
			FriendsList.set("Waitting.1", null);
			FriendsList.saveConfig();
		}
		Inventory inv = Bukkit.createInventory(null, 54, ChatColor.BLACK + "ģ�� ��û : " + (page+1));

		Object[] Friends= FriendsList.getConfigurationSection("Waitting").getKeys(false).toArray();
		byte loc=0;
		Long nowTime = new GoldBigDragon_RPG.Util.ETC().getNowUTC();
		for(int count = page*45; count < Friends.length;count++)
		{
			if(loc >= 45) break;
			Player target = (Player) Bukkit.getServer().getPlayer(Friends[count].toString());
			Long AcceptedTime = FriendsList.getLong("Waitting."+Friends[count].toString());
			Long WaitingTime = (nowTime-AcceptedTime)/1000;
			byte day = 0;
			byte week = 0;
			String TimeSetting=null;
			
			if(WaitingTime >= 2419200)
				TimeSetting = "���� ��";
			else
			{
				week = (byte)(WaitingTime/604800);
				WaitingTime = WaitingTime-(604800*week);

				day = (byte) (WaitingTime/86400);
				WaitingTime = WaitingTime-(86400*day);

				if(week>0)
					TimeSetting = week+"�� ��";
				else if(day>=0)
					if(day==0)
						TimeSetting = "����";
					else
						TimeSetting = day+"�� ��";
			}
			if(TimeSetting == null)
				TimeSetting = "�� �� ����";
			
			if(target!=null)
			{
				Stack2(ChatColor.YELLOW+""+ChatColor.BOLD+target.getName(), 166, 0, 1, Arrays.asList(ChatColor.GRAY+"[   ��������   ]","",ChatColor.YELLOW+"[�� Ŭ���� ģ�� ����]",ChatColor.RED+"[Shift + �� Ŭ���� ����]","","",ChatColor.GOLD+"��û�� : "+TimeSetting), loc, inv);
				if(target.isOnline())
				{
					ItemStackStack(getPlayerSkull(ChatColor.YELLOW+""+ChatColor.BOLD+target.getName(), 1, Arrays.asList(ChatColor.DARK_AQUA+"[   �¶���   ]","",ChatColor.YELLOW+"[�� Ŭ���� ģ�� ����]",ChatColor.RED+"[Shift + �� Ŭ���� ����]","","",ChatColor.GOLD+"��û�� : "+TimeSetting), target.getName()), loc, inv);
					break;
				}
			}
			else
				Stack2(ChatColor.YELLOW+""+ChatColor.BOLD+Friends[count].toString(), 166, 0, 1, Arrays.asList(ChatColor.GRAY+"[   ��������   ]","",ChatColor.YELLOW+"[�� Ŭ���� ģ�� ����]",ChatColor.RED+"[Shift + �� Ŭ���� ����]","","",ChatColor.GOLD+"��û�� : "+TimeSetting), loc, inv);
			
			loc++;
		}
		
		if(Friends.length-(page*44)>45)
		Stack(ChatColor.WHITE + "" + ChatColor.BOLD + "���� ������", 323,0,1,Arrays.asList(ChatColor.GRAY + "���� �������� �̵� �մϴ�."), 50, inv);
		if(page!=0)
		Stack(ChatColor.WHITE + "" + ChatColor.BOLD + "���� ������", 323,0,1,Arrays.asList(ChatColor.GRAY + "���� �������� �̵� �մϴ�."), 48, inv);
		Stack(ChatColor.WHITE + "" + ChatColor.BOLD + "���� ���", 323,0,1,Arrays.asList(ChatColor.GRAY + "���� ȭ������ ���ư��ϴ�."), 45, inv);
		Stack(ChatColor.WHITE + "" + ChatColor.BOLD + "�ݱ�", 324,0,1,Arrays.asList(ChatColor.GRAY + "â�� �ݽ��ϴ�."), 53, inv);
		player.openInventory(inv);
	}
	
	
	
	
	
	public void ETCInventoryclick(InventoryClickEvent event)
	{
		event.setCancelled(true);
		Player player = (Player) event.getWhoClicked();
		if(ChatColor.stripColor(event.getInventory().getTitle()).equalsIgnoreCase("���̵�") == true)
		{
			switch (event.getSlot())
			{
				case 45://���� ���
					ETCGUI_Main(player);
					break;
				case 53://�ݱ�
					s.SP(player, Sound.ITEM_PICKUP, 0.8F, 1.0F);
					s.SP(player, Sound.PISTON_RETRACT, 0.8F, 1.8F);
					player.closeInventory();
					break;
				case 36:
					s.SP(player, Sound.ITEM_PICKUP, 0.8F, 1.0F);
					player.closeInventory();
					event.setCancelled(true);
					player.sendMessage(ChatColor.DARK_RED+""+ChatColor.BOLD+"[YouTube] "+ChatColor.WHITE+""+ChatColor.BOLD+": "+ChatColor.DARK_AQUA+""+ChatColor.BOLD+"https://www.youtube.com/playlist?list=PLxqihkJXVJABIlxU3n6bNhhC8x6xPbORP   "+ChatColor.YELLOW+""+ChatColor.BOLD+"[Ŭ���� ���̵� �������� ����˴ϴ�]");
					break;
					default : return;
			}
			return;
		}
		switch (event.getSlot())
		{
		case 0://����
			GoldBigDragon_RPG.GUI.StatsGUI stat = new GoldBigDragon_RPG.GUI.StatsGUI();
			s.SP(player, Sound.ITEM_PICKUP, 0.8F, 1.0F);
			stat.StatusGUI(player);
			break;
		case 27://�ɼ�
			s.SP(player, Sound.ITEM_PICKUP, 0.8F, 1.0F);
			OptionGUI OGUI = new OptionGUI();
			OGUI.optionGUI(player);
			break;
		case 9://��ų
			s.SP(player, Sound.ITEM_PICKUP, 0.8F, 1.0F);
			PlayerSkillGUI PSGUI = new PlayerSkillGUI();
			PSGUI.MainSkillsListGUI(player, (short) 0);
			break;
		case 18://����Ʈ
			GoldBigDragon_RPG.Quest.QuestGUI QGUI = new GoldBigDragon_RPG.Quest.QuestGUI();
			QGUI.MyQuestListGUI(player, (short) 0);
			s.SP(player, Sound.ITEM_PICKUP, 0.8F, 1.0F);
			break;
		case 2://���̵�
			s.SP(player, Sound.ITEM_PICKUP, 0.8F, 1.0F);
			Information(player);
			break;
		case 3://��Ƽ
			s.SP(player, Sound.ITEM_PICKUP, 0.8F, 1.0F);
			new GoldBigDragon_RPG.Party.PartyGUI().PartyGUI_Main(player);
			break;
		case 4://����
			s.SP(player, Sound.ITEM_PICKUP, 0.8F, 1.0F);
			new GoldBigDragon_RPG.GUI.WarpGUI().WarpListGUI(player, 0);
			break;
		case 5://ģ��
			s.SP(player, Sound.ITEM_PICKUP, 0.8F, 1.0F);
			FriendsGUI(player, (short) 0);
			break;
		case 6://�׺���̼�
			s.SP(player, Sound.ITEM_PICKUP, 0.8F, 1.0F);
			new NavigationGUI().UseNavigationGUI(player, (short) 0);
			break;
		case 26://�ݱ�
			s.SP(player, Sound.PISTON_RETRACT, 0.8F, 1.8F);
			player.closeInventory();
			break;
			default: return;
		}
			return;
	}
	
	public void FriendsGUIclick(InventoryClickEvent event)
	{
		GoldBigDragon_RPG.Effect.Sound s = new GoldBigDragon_RPG.Effect.Sound();
		event.setCancelled(true);
		Player player = (Player) event.getWhoClicked();

		int page =  Integer.parseInt(event.getInventory().getTitle().split(" : ")[1])-1;
		switch (event.getSlot())
		{
		case 45://���� ���
			s.SP(player, Sound.ITEM_PICKUP, 0.8F, 1.0F);
			ETCGUI_Main(player);
			return;
		case 52://ģ�� ��û
			s.SP(player, Sound.ITEM_PICKUP, 0.8F, 1.8F);
			WaittingFriendsGUI(player, (short) 0);
			return;
		case 53://������
			s.SP(player, Sound.PISTON_RETRACT, 0.8F, 1.8F);
			player.closeInventory();
			return;
		case 48://���� ������
			s.SP(player, Sound.ITEM_PICKUP, 0.8F, 1.0F);
			FriendsGUI(player, (short) (page-1));
			return;
		case 49://�� ģ��
			{
				s.SP(player, Sound.ITEM_PICKUP, 0.8F, 1.0F);
				player.closeInventory();
				player.sendMessage(ChatColor.GREEN+"[ģ��] : ģ�� ��û�� �Ͻ� ������ �г����� �Է� �ϼ���!");
				new Object_UserData().setTemp(player, "FA");
			}
		return;
		case 50://���� ������
			s.SP(player, Sound.ITEM_PICKUP, 0.8F, 1.0F);
			FriendsGUI(player, (short) (page+1));
			return;
		default:
			if(event.isShiftClick()&&event.isRightClick())
			{
				String FName = ChatColor.stripColor(event.getCurrentItem().getItemMeta().getDisplayName());
			  	YamlController YC = new YamlController(GoldBigDragon_RPG.Main.Main.plugin);
				YamlManager FriendsList  = YC.getNewConfig("Friend/"+player.getUniqueId().toString()+".yml");
				FriendsList.removeKey("Friends."+FName);
				FriendsList.saveConfig();
				s.SP(player, Sound.LAVA_POP, 1.0F, 0.7F);
				player.sendMessage(ChatColor.LIGHT_PURPLE+"[ģ��] : "+ChatColor.YELLOW+FName+ChatColor.LIGHT_PURPLE+"���� ģ�� ��Ͽ��� �����Ͽ����ϴ�!");
				FriendsGUI(player, (short) page);
				return;
			}
		}
	}

	public void WaittingFriendsGUIclick(InventoryClickEvent event)
	{
		GoldBigDragon_RPG.Effect.Sound s = new GoldBigDragon_RPG.Effect.Sound();
		event.setCancelled(true);
		Player player = (Player) event.getWhoClicked();

		int page =  Integer.parseInt(event.getInventory().getTitle().split(" : ")[1])-1;
		switch (event.getSlot())
		{
		case 45://���� ���
			s.SP(player, Sound.ITEM_PICKUP, 0.8F, 1.0F);
			FriendsGUI(player, (short) 0);
			return;
		case 53://������
			s.SP(player, Sound.PISTON_RETRACT, 0.8F, 1.8F);
			player.closeInventory();
			return;
		case 48://���� ������
			s.SP(player, Sound.ITEM_PICKUP, 0.8F, 1.0F);
			WaittingFriendsGUI(player, (short) (page-1));
			return;
		case 50://���� ������
			s.SP(player, Sound.ITEM_PICKUP, 0.8F, 1.0F);
			WaittingFriendsGUI(player, (short) (page+1));
			return;
		default:
			String FName = ChatColor.stripColor(event.getCurrentItem().getItemMeta().getDisplayName());
		  	YamlController YC = new YamlController(GoldBigDragon_RPG.Main.Main.plugin);
			YamlManager FriendsList  = YC.getNewConfig("Friend/"+player.getUniqueId().toString()+".yml");
			if(event.isShiftClick()&&event.isRightClick())
			{
				FriendsList.removeKey("Waitting."+FName);
				FriendsList.saveConfig();
				s.SP(player, Sound.LAVA_POP, 1.0F, 0.7F);
			}
			else if(event.isLeftClick()&&!event.isShiftClick())
			{
				new GoldBigDragon_RPG.GUI.EquipGUI().SetFriends(player, Bukkit.getPlayer(FName));
			}
			FriendsList  = YC.getNewConfig("Friend/"+player.getUniqueId().toString()+".yml");
			if(FriendsList.getConfigurationSection("Waitting").getKeys(false).toArray().length == 0)
				FriendsGUI(player, (short) 0);
			else
				WaittingFriendsGUI(player, (short) page);
			break;
		}
	}
}
