package GoldBigDragon_RPG.GUI;

import java.util.Arrays;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryAction;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.material.MaterialData;

import GoldBigDragon_RPG.ServerTick.ServerTickMain;
import GoldBigDragon_RPG.Util.YamlController;
import GoldBigDragon_RPG.Util.YamlManager;

public class EquipGUI extends GUIutil
{
	public void EquipWatchGUI(Player player, Player target)
	{
		Inventory inv = Bukkit.createInventory(null, 54, ChatColor.BLACK + "��� ����");
	  	if(GoldBigDragon_RPG.Main.ServerOption.PlayerList.get(target.getUniqueId().toString()).isOption_EquipLook())
	  	{
			Stack(ChatColor.DARK_AQUA + "[    ���    ]", 160,7,1,null, 0, inv);
			Stack(ChatColor.DARK_AQUA + "[    ���    ]", 160,7,1,null, 1, inv);
			Stack(ChatColor.DARK_AQUA + "[    ���    ]", 160,7,1,null, 2, inv);
			Stack(ChatColor.DARK_AQUA + "[    ���    ]", 160,7,1,null, 3, inv);
			Stack(ChatColor.DARK_AQUA + "[    ���    ]", 160,7,1,null, 4, inv);
			Stack(ChatColor.DARK_AQUA + "[    ���    ]", 160,7,1,null, 9, inv);
			Stack(ChatColor.DARK_AQUA + "[    ���    ]", 160,7,1,null, 18, inv);
			Stack(ChatColor.DARK_AQUA + "[    ���    ]", 160,7,1,null, 27, inv);
			Stack(ChatColor.DARK_AQUA + "[    ���    ]", 160,7,1,null, 36, inv);
			Stack(ChatColor.DARK_AQUA + "[    ���    ]", 160,7,1,null, 45, inv);
			Stack(ChatColor.DARK_AQUA + "[    ���    ]", 160,7,1,null, 46, inv);
			Stack(ChatColor.DARK_AQUA + "[    ���    ]", 160,7,1,null, 47, inv);
			Stack(ChatColor.DARK_AQUA + "[    ���    ]", 160,7,1,null, 48, inv);
			Stack(ChatColor.DARK_AQUA + "[    ���    ]", 160,7,1,null, 49, inv);
			Stack(ChatColor.DARK_AQUA + "[    ���    ]", 160,7,1,null, 40, inv);
			Stack(ChatColor.DARK_AQUA + "[    ���    ]", 160,7,1,null, 31, inv);
			Stack(ChatColor.DARK_AQUA + "[    ���    ]", 160,7,1,null, 22, inv);
			Stack(ChatColor.DARK_AQUA + "[    ���    ]", 160,7,1,null, 13, inv);
			
			
	  		if(target.getInventory().getHelmet() == null)
	  			Stack2(ChatColor.WHITE  + "" + ChatColor.BOLD + "������", 302,0,1,null, 11, inv);
	  		else
	  			ItemStackStack(target.getInventory().getHelmet(), 11, inv);
	  		if(target.getInventory().getChestplate() == null)
	  			Stack2(ChatColor.WHITE  + "" + ChatColor.BOLD + "������", 303,0,01,null, 20, inv);
	  		else
	  			ItemStackStack(target.getInventory().getChestplate(), 20, inv);
	  		if(target.getInventory().getLeggings() == null)
	  			Stack2(ChatColor.WHITE  + "" + ChatColor.BOLD + "������", 304,0,1,null, 29, inv);
	  		else
	  			ItemStackStack(target.getInventory().getLeggings(), 29, inv);
	  		if(target.getInventory().getBoots() == null)
	  			Stack2(ChatColor.WHITE  + "" + ChatColor.BOLD + "������", 305,0,1,null, 38, inv);
	  		else
	  			ItemStackStack(target.getInventory().getBoots(), 38, inv);
	  		if(target.getInventory().getItemInHand() == null)
	  			Stack2(ChatColor.WHITE  + "" + ChatColor.BOLD + "�Ǽ�", 336,0,1,null, 28, inv);
	  		else
	  			if(target.getInventory().getItemInHand().getType() == Material.AIR)
		  			Stack2(ChatColor.WHITE  + "" + ChatColor.BOLD + "�Ǽ�", 336,0,1,null, 28, inv);
	  			else
	  				ItemStackStack(target.getInventory().getItemInHand(), 28, inv);
	  	}
	  	else
	  	{
			Stack(ChatColor.RED + "[    �����    ]", 160,14,1,null, 0, inv);
			Stack(ChatColor.RED + "[    �����    ]", 160,14,1,null, 1, inv);
			Stack(ChatColor.RED + "[    �����    ]", 160,14,1,null, 2, inv);
			Stack(ChatColor.RED + "[    �����    ]", 160,14,1,null, 3, inv);
			Stack(ChatColor.RED + "[    �����    ]", 160,14,1,null, 4, inv);
			Stack(ChatColor.RED + "[    �����    ]", 160,14,1,null, 9, inv);
			Stack(ChatColor.RED + "[    �����    ]", 160,14,1,null, 18, inv);
			Stack(ChatColor.RED + "[    �����    ]", 160,14,1,null, 27, inv);
			Stack(ChatColor.RED + "[    �����    ]", 160,14,1,null, 36, inv);
			Stack(ChatColor.RED + "[    �����    ]", 160,14,1,null, 45, inv);
			Stack(ChatColor.RED + "[    �����    ]", 160,14,1,null, 46, inv);
			Stack(ChatColor.RED + "[    �����    ]", 160,14,1,null, 47, inv);
			Stack(ChatColor.RED + "[    �����    ]", 160,14,1,null, 48, inv);
			Stack(ChatColor.RED + "[    �����    ]", 160,14,1,null, 49, inv);
			Stack(ChatColor.RED + "[    �����    ]", 160,14,1,null, 40, inv);
			Stack(ChatColor.RED + "[    �����    ]", 160,14,1,null, 31, inv);
			Stack(ChatColor.RED + "[    �����    ]", 160,14,1,null, 22, inv);
			Stack(ChatColor.RED + "[    �����    ]", 160,14,1,null, 13, inv);
			
	  		Stack2(ChatColor.WHITE  + "" + ChatColor.BOLD + "�˼�����", 302,0,1,null, 11, inv);
  			Stack2(ChatColor.WHITE  + "" + ChatColor.BOLD + "�˼�����", 303,0,1,null, 20, inv);
  			Stack2(ChatColor.WHITE  + "" + ChatColor.BOLD + "�˼�����", 304,0,1,null, 29, inv);
  			Stack2(ChatColor.WHITE  + "" + ChatColor.BOLD + "�˼�����", 305,0,1,null, 38, inv);
  			Stack2(ChatColor.WHITE  + "" + ChatColor.BOLD + "�˼�����", 336,0,1,null, 28, inv);
	  	}

		Stack(ChatColor.DARK_AQUA + "[    �ൿ    ]", 160,9,1,null, 5, inv);
		Stack(ChatColor.DARK_AQUA + "[    �ൿ    ]", 160,9,1,null, 6, inv);
		Stack(ChatColor.DARK_AQUA + "[    �ൿ    ]", 160,9,1,null, 7, inv);
		Stack(ChatColor.DARK_AQUA + "[    �ൿ    ]", 160,9,1,null, 8, inv);
		Stack(ChatColor.DARK_AQUA + "[    �ൿ    ]", 160,9,1,null, 14, inv);
		Stack(ChatColor.DARK_AQUA + "[    �ൿ    ]", 160,9,1,null, 17, inv);
		Stack(ChatColor.DARK_AQUA + "[    �ൿ    ]", 160,9,1,null, 23, inv);
		Stack(ChatColor.DARK_AQUA + "[    �ൿ    ]", 160,9,1,null, 26, inv);
		Stack(ChatColor.DARK_AQUA + "[    �ൿ    ]", 160,9,1,null, 32, inv);
		Stack(ChatColor.DARK_AQUA + "[    �ൿ    ]", 160,9,1,null, 35, inv);
		Stack(ChatColor.DARK_AQUA + "[    �ൿ    ]", 160,9,1,null, 41, inv);
		Stack(ChatColor.DARK_AQUA + "[    �ൿ    ]", 160,9,1,null, 44, inv);
		Stack(ChatColor.DARK_AQUA + "[    �ൿ    ]", 160,9,1,null, 50, inv);
		Stack(ChatColor.DARK_AQUA + "[    �ൿ    ]", 160,9,1,null, 51, inv);
		Stack(ChatColor.DARK_AQUA + "[    �ൿ    ]", 160,9,1,null, 52, inv);
		Stack(ChatColor.DARK_AQUA + "[    �ൿ    ]", 160,9,1,null, 53, inv);
		
	  	Stack2(ChatColor.WHITE  + "" + ChatColor.BOLD + "��ȯ ��û", 388,0,1,Arrays.asList(
	  			ChatColor.GRAY+"��ȯ ��û�� �մϴ�.",ChatColor.GRAY+"������ ���� �������� ������",ChatColor.GRAY+"������ �����Ǿ� �ֽ��ϴ�.","",ChatColor.YELLOW+"[    ���    ]",ChatColor.WHITE+""+ChatColor.BOLD+target.getName()), 15, inv);
		Stack2(ChatColor.WHITE  + "" + ChatColor.BOLD + "ģ�� �߰�", 397,3,1,Arrays.asList(
				ChatColor.GRAY+"ģ�� �߰� ��û�� �մϴ�.","",ChatColor.YELLOW+"[    ���    ]",ChatColor.WHITE+""+ChatColor.BOLD+target.getName()), 16, inv);
		player.openInventory(inv);
	}
	
	public void ExChangeGUI(Player player, Player target, ItemStack[] SideItems, boolean MyReady,boolean TargetReady)
	{
		Inventory inv = Bukkit.createInventory(null, 54, ChatColor.BLACK + "��ȯ");

		for(byte count = 0; count < 5; count++)
			Stack(ChatColor.GRAY +""+ChatColor.BOLD+ "[   ĭ����   ]", 160,15,1,null, 4+(count*9), inv);

		Stack(ChatColor.WHITE +""+ChatColor.BOLD+ "[ ��ȯ ��� ]", 166,0,1,null, 49, inv);
		
		ItemStackStack(getPlayerSkull(ChatColor.GREEN +""+ChatColor.BOLD+player.getName(), 1, null, player.getName()), 0, inv);
		ItemStackStack(getPlayerSkull(ChatColor.GREEN +""+ChatColor.BOLD+target.getName(), 1, null, target.getName()), 8, inv);

		for(byte count = 1; count < 4; count++)
			Stack(ChatColor.BLUE +""+ChatColor.BOLD+ "[   �ڽ�   ]", 160,11,1,null, count, inv);
		for(byte count = 5; count < 8; count++)
			Stack(ChatColor.RED +""+ChatColor.BOLD+ "[   ���   ]", 160,14,1,null, count, inv);

		for(byte count = 0; count < 4; count++)
			Stack(ChatColor.BLUE +""+ChatColor.BOLD+ "[   �ڽ�   ]", 160,11,1,null, 9+(count*9), inv);
		for(byte count = 0; count < 4; count++)
			Stack(ChatColor.RED +""+ChatColor.BOLD+ "[   ���   ]", 160,14,1,null, 17+(count*9), inv);
		
		for(byte count = 46; count < 49; count++)
			Stack(ChatColor.BLUE +""+ChatColor.BOLD+ "[   �ڽ�   ]", 160,11,1,null, count, inv);
		for(byte count = 50; count < 53; count++)
			Stack(ChatColor.RED +""+ChatColor.BOLD+ "[   ���   ]", 160,14,1,null, count, inv);

		for(byte count = 0; count < 4; count ++)
			for(byte count2 = 10; count2 < 13; count2 ++)
				Stack(ChatColor.GRAY +""+ChatColor.BOLD+ "[�ƹ��͵� �÷����� ����]", 160,8,1,null, count2+(count*9), inv);
		
		for(byte count = 0; count < 4; count ++)
			for(byte count2 = 14; count2 < 17; count2 ++)
				Stack(ChatColor.GRAY +""+ChatColor.BOLD+ "[�ƹ��͵� �÷����� ����]", 160,8,1,null, count2+(count*9), inv);
		
		if(SideItems != null)
		{
			if(SideItems.length != 0)
			{
				int SIC = 0;
				for(byte count = 0; count < 3; count ++)
				{
					for(byte count2 = 10; count2 < 13; count2 ++)
					{
						if(SideItems[SIC] != null)
							ItemStackStack(SideItems[SIC], count2+(count*9), inv);
						SIC = SIC+1;
					}
				}
				for(byte count = 0; count < 3; count ++)
				{
					for(byte count2 = 14; count2 < 17; count2 ++)
					{
						if(SideItems[SIC] != null)
							ItemStackStack(SideItems[SIC], count2+(count*9), inv);
						SIC = SIC+1;
					}
				}
			}
		}
		
		if(MyReady)
			Stack(ChatColor.BLUE +""+ChatColor.BOLD+ "[ �غ� �Ϸ� ]", 35,11,1,null, 45, inv);
		else
			Stack(ChatColor.RED +""+ChatColor.BOLD+ "[  �����  ]", 35,14,1,null, 45, inv);

		if(TargetReady)
			Stack(ChatColor.BLUE +""+ChatColor.BOLD+ "[ �غ� �Ϸ� ]", 35,11,1,null, 53, inv);
		else
			Stack(ChatColor.RED +""+ChatColor.BOLD+ "[  �����  ]", 35,14,1,null, 53, inv);
		
		player.openInventory(inv);
	}
	
	public void optionInventoryclick(InventoryClickEvent event)
	{
		event.setCancelled(true);
		Player player = (Player) event.getWhoClicked();
		Player target = (Player) Bukkit.getServer().getPlayer(ChatColor.stripColor(event.getInventory().getItem(16).getItemMeta().getLore().get(3)));

		switch(event.getSlot())
		{
		case 15:
			AddExchangeTarget(player, target);
			break;
		case 16:
			SetFriends(player, target);
			break;
		}
	}

	public void ExchangeInventoryclick(InventoryClickEvent event)
	{
		Player player = (Player) event.getWhoClicked();
		Player target = Bukkit.getServer().getPlayer(ChatColor.stripColor(event.getInventory().getItem(8).getItemMeta().getDisplayName()));
		GoldBigDragon_RPG.Effect.Sound s = new GoldBigDragon_RPG.Effect.Sound();

		event.setCancelled(true);
		if((event.getAction() == InventoryAction.PICKUP_ALL||
		event.getAction() == InventoryAction.PICKUP_HALF||
		event.getAction() == InventoryAction.PICKUP_ONE||
		event.getAction() == InventoryAction.PICKUP_SOME)&&
		ChatColor.stripColor(event.getClickedInventory().getName()).compareTo("��ȯ") != 0)
		{
			ItemStack mySideSlot[] = new ItemStack[12];
			byte MSS = 0;
			for(byte count = 0; count < 4; count ++)
			{
				for(byte count2 = 10; count2 < 13; count2 ++)
				{
					if(event.getInventory().getItem(count2+(count*9)) != null)
						mySideSlot[MSS] = event.getInventory().getItem(count2+(count*9));
					MSS++;
				}
			}
			byte emptySlot = -1;
			for(byte count = 0;count < 12; count ++)
			{
				if(mySideSlot[count]!=null)
				{
					if(mySideSlot[count].hasItemMeta())
					{
						if(mySideSlot[count].getItemMeta().hasLore()==false&&
							mySideSlot[count].getItemMeta().hasDisplayName())
						{
							if(mySideSlot[count].getItemMeta().getDisplayName().compareTo(ChatColor.GRAY +""+ChatColor.BOLD+ "[�ƹ��͵� �÷����� ����]")==0)
							{emptySlot = count; break;}
						}
					}
				}
			}
			if(emptySlot != -1)
			{
				s.SP(player, Sound.DIG_STONE, 1.0F, 1.0F);
				s.SP(target, Sound.DIG_STONE, 1.0F, 1.0F);
				switch(emptySlot)
				{
				case 0 :
					player.getOpenInventory().setItem(10, event.getCurrentItem());
					target.getOpenInventory().setItem(14, event.getCurrentItem());
					break;
				case 1 :
					player.getOpenInventory().setItem(11, event.getCurrentItem());
					target.getOpenInventory().setItem(15, event.getCurrentItem());
					break;
				case 2 :
					player.getOpenInventory().setItem(12, event.getCurrentItem());
					target.getOpenInventory().setItem(16, event.getCurrentItem());
					break;
				case 3 :
					player.getOpenInventory().setItem(19, event.getCurrentItem());
					target.getOpenInventory().setItem(23, event.getCurrentItem());
					break;
				case 4 :
					player.getOpenInventory().setItem(20, event.getCurrentItem());
					target.getOpenInventory().setItem(24, event.getCurrentItem());
					break;
				case 5 :
					player.getOpenInventory().setItem(21, event.getCurrentItem());
					target.getOpenInventory().setItem(25, event.getCurrentItem());
					break;
				case 6 :
					player.getOpenInventory().setItem(28, event.getCurrentItem());
					target.getOpenInventory().setItem(32, event.getCurrentItem());
					break;
				case 7 :
					player.getOpenInventory().setItem(29, event.getCurrentItem());
					target.getOpenInventory().setItem(33, event.getCurrentItem());
					break;
				case 8 :
					player.getOpenInventory().setItem(30, event.getCurrentItem());
					target.getOpenInventory().setItem(34, event.getCurrentItem());
					break;
				case 9 :
					player.getOpenInventory().setItem(37, event.getCurrentItem());
					target.getOpenInventory().setItem(41, event.getCurrentItem());
					break;
				case 10 :
					player.getOpenInventory().setItem(38, event.getCurrentItem());
					target.getOpenInventory().setItem(42, event.getCurrentItem());
					break;
				case 11 :
					player.getOpenInventory().setItem(39, event.getCurrentItem());
					target.getOpenInventory().setItem(43, event.getCurrentItem());
					break;
				}
				event.getClickedInventory().setItem(event.getSlot(), new ItemStack(0));

				ItemStack Icon = new MaterialData(35, (byte) 14).toItemStack();
				Icon.setAmount(1);
				ItemMeta Icon_Meta = Icon.getItemMeta();
				Icon_Meta.setLore(null);
				Icon_Meta.setDisplayName(ChatColor.RED +""+ChatColor.BOLD+ "[  �����  ]");
				Icon.setItemMeta(Icon_Meta);
				
				target.getOpenInventory().setItem(45, Icon);
				event.getInventory().setItem(45, Icon);
				
				target.getOpenInventory().setItem(53, Icon);
				event.getInventory().setItem(53, Icon);
			}
			else
			{
				s.SP(player, Sound.ORB_PICKUP, 1.0F, 1.8F);
				player.sendMessage(ChatColor.RED+"[��ȯ] : �� �̻� ������ �ø� �� �����ϴ�!");
			}
		}
		else if((event.getAction() == InventoryAction.PICKUP_ALL||
				event.getAction() == InventoryAction.PICKUP_HALF||
				event.getAction() == InventoryAction.PICKUP_ONE||
				event.getAction() == InventoryAction.PICKUP_SOME)&&
				ChatColor.stripColor(event.getClickedInventory().getName()).compareTo("��ȯ") == 0)
		{
			if((event.getSlot()>=10&&event.getSlot()<=12)||
			(event.getSlot()>=19&&event.getSlot()<=21)||
			(event.getSlot()>=28&&event.getSlot()<=30)||
			(event.getSlot()>=37&&event.getSlot()<=39))
			{
				if(event.getCurrentItem()!=null)
				{
					if(event.getCurrentItem().hasItemMeta())
					{
						if(event.getCurrentItem().getItemMeta().hasLore()==false&&
								event.getCurrentItem().getItemMeta().hasDisplayName())
						{
							if(event.getCurrentItem().getItemMeta().getDisplayName().compareTo(ChatColor.GRAY +""+ChatColor.BOLD+ "[�ƹ��͵� �÷����� ����]")==0)
							{return;}
						}
					}
				}
				ItemStack Icon = new MaterialData(160, (byte) 8).toItemStack();
				Icon.setAmount(1);
				ItemMeta Icon_Meta = Icon.getItemMeta();
				Icon_Meta.setLore(null);
				Icon_Meta.setDisplayName(ChatColor.GRAY +""+ChatColor.BOLD+ "[�ƹ��͵� �÷����� ����]");
				Icon.setItemMeta(Icon_Meta);
				player.getInventory().addItem(event.getCurrentItem());
				target.getOpenInventory().setItem(event.getSlot()+4, Icon);
				event.getClickedInventory().setItem(event.getSlot(), Icon);
				

				Icon = new MaterialData(35, (byte) 14).toItemStack();
				Icon.setAmount(1);
				Icon_Meta = Icon.getItemMeta();
				Icon_Meta.setLore(null);
				Icon_Meta.setDisplayName(ChatColor.RED +""+ChatColor.BOLD+ "[  �����  ]");
				Icon.setItemMeta(Icon_Meta);
				
				target.getOpenInventory().setItem(45, Icon);
				event.getClickedInventory().setItem(45, Icon);
				
				target.getOpenInventory().setItem(53, Icon);
				event.getClickedInventory().setItem(53, Icon);
				s.SP(player, Sound.DIG_WOOL, 1.0F, 1.0F);
				s.SP(target, Sound.DIG_WOOL, 1.0F, 1.0F);
			}
		}
	}
	
	public void ExchangeGUIclick(InventoryClickEvent event)
	{
		Player player = Bukkit.getServer().getPlayer(ChatColor.stripColor(event.getInventory().getItem(0).getItemMeta().getDisplayName()));
		Player target = Bukkit.getServer().getPlayer(ChatColor.stripColor(event.getInventory().getItem(8).getItemMeta().getDisplayName()));
		GoldBigDragon_RPG.Effect.Sound s = new GoldBigDragon_RPG.Effect.Sound();
		if((event.getSlot() >= 0 && event.getSlot() <= 9)||
		(event.getSlot() >= 13 && event.getSlot() <= 18)||
		(event.getSlot() >= 22 && event.getSlot() <= 27)||
		(event.getSlot() >= 31 && event.getSlot() <= 36)||
		(event.getSlot() >= 40 && event.getSlot() <= 53))
			event.setCancelled(true);
		
		switch(event.getSlot())
		{
			case 45:
			{
				if(event.getInventory().getItem(45).getData().getData()==14)
				{
					if(event.getInventory().getItem(53).getData().getData()==11)
					{
						byte needSlot = 0;
						byte mySlot = 0;
						
						for(byte count = 0; count < 4; count ++)
							for(byte count2 = 10; count2 < 13; count2 ++)
							{
								ItemStack item = target.getOpenInventory().getItem(count2+(count*9));
								if(item != null)
								{
									if(item.getTypeId()==160&&item.getData().getData()==8)
										if(item.hasItemMeta())
											if(item.getItemMeta().hasDisplayName())
											{
												if(ChatColor.stripColor(item.getItemMeta().getDisplayName()).compareTo("[�ƹ��͵� �÷����� ����]")!=0)
													needSlot++;
											}
											else
												needSlot++;
										else
											needSlot++;
									else
										needSlot++;
								}
							}
						
						for(byte count = 0; count < 36;count++)
							if(player.getInventory().getItem(count)==null)
								mySlot++;
						if(mySlot < needSlot)
						{
							player.sendMessage(ChatColor.RED+"[��ȯ] : ����� �κ��丮 ������ �����մϴ�!");
							target.sendMessage(ChatColor.RED+"[��ȯ] : ������ �κ��丮 ������ �����մϴ�!");
							return;
						}
						
						needSlot=0;
						mySlot=0;

						for(byte count = 0; count < 4; count ++)
							for(byte count2 = 10; count2 < 13; count2 ++)
							{
								ItemStack item = event.getInventory().getItem(count2+(count*9));
								if(item != null)
								{
									if(item.getTypeId()==160&&item.getData().getData()==8)
										if(item.hasItemMeta())
											if(item.getItemMeta().hasDisplayName())
											{
												if(ChatColor.stripColor(item.getItemMeta().getDisplayName()).compareTo("[�ƹ��͵� �÷����� ����]")!=0)
													needSlot++;
											}
											else
												needSlot++;
										else
											needSlot++;
									else
										needSlot++;
								}
							}

						for(byte count = 0; count < 36;count++)
							if(target.getInventory().getItem(count)==null)
								mySlot++;

						if(mySlot < needSlot)
						{
							player.sendMessage(ChatColor.RED+"[��ȯ] : ������ �κ��丮 ������ �����մϴ�!");
							target.sendMessage(ChatColor.RED+"[��ȯ] : ����� �κ��丮 ������ �����մϴ�!");
							return;
						}
						
						for(byte count = 0; count < 4; count ++)
						{
							for(byte count2 = 10; count2 < 13; count2 ++)
							{
								if(event.getInventory().getItem(count2+(count*9))!=null)
								{
									if(event.getInventory().getItem(count2+(count*9)).hasItemMeta())
									{
										if(event.getInventory().getItem(count2+(count*9)).getItemMeta().hasLore()==false&&
												event.getInventory().getItem(count2+(count*9)).getItemMeta().hasDisplayName())
										{
											if(event.getInventory().getItem(count2+(count*9)).getItemMeta().getDisplayName().compareTo(ChatColor.GRAY +""+ChatColor.BOLD+ "[�ƹ��͵� �÷����� ����]")!=0)
												target.getInventory().addItem(event.getInventory().getItem(count2+(count*9)));
										}
										else
											target.getInventory().addItem(event.getInventory().getItem(count2+(count*9)));
									}
									else
										target.getInventory().addItem(event.getInventory().getItem(count2+(count*9)));
								}
								else
									target.getInventory().addItem(event.getInventory().getItem(count2+(count*9)));
							}
						}
	
						for(byte count = 0; count < 4; count ++)
						{
							for(byte count2 = 10; count2 < 13; count2 ++)
							{
								if(target.getOpenInventory().getItem(count2+(count*9))!=null)
								{
									if(target.getOpenInventory().getItem(count2+(count*9)).hasItemMeta())
									{
										if(target.getOpenInventory().getItem(count2+(count*9)).getItemMeta().hasLore()==false&&
												target.getOpenInventory().getItem(count2+(count*9)).getItemMeta().hasDisplayName())
										{
											if(target.getOpenInventory().getItem(count2+(count*9)).getItemMeta().getDisplayName().compareTo(ChatColor.GRAY +""+ChatColor.BOLD+ "[�ƹ��͵� �÷����� ����]")!=0)
												player.getInventory().addItem(target.getOpenInventory().getItem(count2+(count*9)));
										}
										else
											player.getInventory().addItem(target.getOpenInventory().getItem(count2+(count*9)));
									}
									else
										player.getInventory().addItem(target.getOpenInventory().getItem(count2+(count*9)));
								}
								else
									player.getInventory().addItem(target.getOpenInventory().getItem(count2+(count*9)));
							}
						}
						target.getOpenInventory().setItem(0, new ItemStack(2));
						event.getInventory().setItem(0, new ItemStack(2));
						target.closeInventory();
						player.closeInventory();
					}
					else
					{
						ItemStack Icon = new MaterialData(35, (byte) 11).toItemStack();
						Icon.setAmount(1);
						ItemMeta Icon_Meta = Icon.getItemMeta();
						Icon_Meta.setDisplayName(ChatColor.BLUE +""+ChatColor.BOLD+ "[ �غ� �Ϸ� ]");
						Icon.setItemMeta(Icon_Meta);
						event.getInventory().setItem(45, Icon);
						target.getOpenInventory().setItem(53, Icon);
						s.SP(player, Sound.VILLAGER_IDLE, 1.0F, 1.0F);
						s.SP(target, Sound.VILLAGER_IDLE, 1.0F, 1.0F);
					}
				}
				else
				{
					ItemStack Icon = new MaterialData(35, (byte) 14).toItemStack();
					Icon.setAmount(1);
					ItemMeta Icon_Meta = Icon.getItemMeta();
					Icon_Meta.setDisplayName(ChatColor.RED +""+ChatColor.BOLD+ "[  �����  ]");
					Icon.setItemMeta(Icon_Meta);
					event.getInventory().setItem(45, Icon);
					target.getOpenInventory().setItem(53, Icon);
					s.SP(player, Sound.VILLAGER_IDLE, 1.0F, 1.0F);
					s.SP(target, Sound.VILLAGER_IDLE, 1.0F, 1.0F);
				}
			}
			return;
			case 49:
			{
				event.getInventory().setItem(0, new ItemStack(138,1));
				for(byte count = 0; count < 4; count ++)
				{
					for(byte count2 = 10; count2 < 13; count2 ++)
					{
						if(event.getInventory().getItem(count2+(count*9))!=null)
						{
							if(event.getInventory().getItem(count2+(count*9)).hasItemMeta())
							{
								if(event.getInventory().getItem(count2+(count*9)).getItemMeta().hasLore()==false&&
										event.getInventory().getItem(count2+(count*9)).getItemMeta().hasDisplayName())
								{
									if(event.getInventory().getItem(count2+(count*9)).getItemMeta().getDisplayName().compareTo(ChatColor.GRAY +""+ChatColor.BOLD+ "[�ƹ��͵� �÷����� ����]")!=0)
										player.getInventory().addItem(event.getInventory().getItem(count2+(count*9)));
								}
								else
									player.getInventory().addItem(event.getInventory().getItem(count2+(count*9)));
							}
							else
								player.getInventory().addItem(event.getInventory().getItem(count2+(count*9)));
						}
						else
							player.getInventory().addItem(event.getInventory().getItem(count2+(count*9)));
					}
				}
				target.closeInventory();
				player.closeInventory();
			}
			return;
		}
		return;
	}
	
	
	public void InventoryClose_ExchangeGUI(InventoryCloseEvent event)
	{
		GoldBigDragon_RPG.Effect.Sound s = new GoldBigDragon_RPG.Effect.Sound();
		if(event.getInventory().getItem(0).getTypeId() == 138)
		{
			s.SP((Player)event.getPlayer(), Sound.PISTON_RETRACT, 1.0F, 1.8F);
			event.getPlayer().sendMessage(ChatColor.RED+"[��ȯ] : ��ȯ�� ����Ͽ����ϴ�!");
		}
		else if(event.getInventory().getItem(0).getTypeId()== 397)
		{
			Player target = Bukkit.getServer().getPlayer(ChatColor.stripColor(event.getInventory().getItem(8).getItemMeta().getDisplayName()));
			Player player = (Player) event.getPlayer();
			s.SP((Player)event.getPlayer(), Sound.PISTON_RETRACT, 1.0F, 1.8F);
			event.getPlayer().sendMessage(ChatColor.RED+"[��ȯ] : ��ȯ�� ��ҵǾ����ϴ�!");
			for(byte count = 0; count < 4; count ++)
			{
				for(byte count2 = 10; count2 < 13; count2 ++)
				{
					if(event.getInventory().getItem(count2+(count*9))!=null)
					{
						if(event.getInventory().getItem(count2+(count*9)).hasItemMeta())
						{
							if(event.getInventory().getItem(count2+(count*9)).getItemMeta().hasLore()==false&&
									event.getInventory().getItem(count2+(count*9)).getItemMeta().hasDisplayName())
							{
								if(event.getInventory().getItem(count2+(count*9)).getItemMeta().getDisplayName().compareTo(ChatColor.GRAY +""+ChatColor.BOLD+ "[�ƹ��͵� �÷����� ����]")!=0)
									player.getInventory().addItem(event.getInventory().getItem(count2+(count*9)));
							}
							else
								player.getInventory().addItem(event.getInventory().getItem(count2+(count*9)));
						}
						else
							player.getInventory().addItem(event.getInventory().getItem(count2+(count*9)));
					}
					else
						player.getInventory().addItem(event.getInventory().getItem(count2+(count*9)));
				}
			}
			event.getInventory().setItem(0, new ItemStack(166,1));
			target.closeInventory();
		}
		else if(event.getInventory().getItem(0).getTypeId()== 2)
		{
			s.SP((Player)event.getPlayer(), Sound.LEVEL_UP, 1.0F, 1.8F);
			event.getPlayer().sendMessage(ChatColor.GREEN+"[��ȯ] : ��ȯ�� ����Ǿ����ϴ�!");
		}
	}
	
	
	
	public void SetFriends(Player player, Player target)
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
		YamlManager SideFriendsList  = YC.getNewConfig("Friend/"+target.getUniqueId().toString()+".yml");
		if(SideFriendsList.contains("Name")==false)
		{
			SideFriendsList.set("Name", target.getName());
			SideFriendsList.set("Friends.1", null);
			SideFriendsList.set("Waitting.1", null);
			SideFriendsList.saveConfig();
		}

		GoldBigDragon_RPG.Effect.Sound s = new GoldBigDragon_RPG.Effect.Sound();
		Long AddTime = new GoldBigDragon_RPG.Util.ETC().getSec();
		Object[] Friend = FriendsList.getConfigurationSection("Waitting").getKeys(false).toArray();
		Object[] SideFriend = SideFriendsList.getConfigurationSection("Waitting").getKeys(false).toArray();

		for(short counter= 0; counter <SideFriend.length;counter++)
		{
			if(SideFriend[counter].toString().equals(player.getName()))
			{
				s.SP(player, Sound.ORB_PICKUP, 1.0F, 1.8F);
				player.sendMessage(ChatColor.RED+"[ģ��] : �̹� ���濡�� ģ�� ��û�� �� �����Դϴ�!");
				return;
			}
		}
		
		for(short counter= 0; counter < Friend.length;counter++)
		{
			if(Friend[counter].toString().equals(target.getName()))
			{
				SideFriendsList.removeKey("Waitting."+player.getName());
				SideFriendsList.set("Friends."+player.getName(),AddTime);
				SideFriendsList.saveConfig();
				FriendsList.removeKey("Waitting."+target.getName());
				FriendsList.set("Friends."+target.getName(),AddTime);
				FriendsList.saveConfig();
				s.SP(player, Sound.LAVA_POP,1.0F, 1.2F);
				player.sendMessage(ChatColor.AQUA+"[ģ��] : "+ChatColor.YELLOW+target.getName()+ChatColor.AQUA+"�Բ��� ģ���� ��ϵǾ����ϴ�!");
				if(target.isOnline())
				{
					s.SP(target, Sound.LAVA_POP,1.0F, 1.2F);
					target.sendMessage(ChatColor.AQUA+"[ģ��] : "+ChatColor.YELLOW+player.getName()+ChatColor.AQUA+"�Բ��� ģ���� ��ϵǾ����ϴ�!");
				}
				return;
			}
		}
		Friend = FriendsList.getConfigurationSection("Friends").getKeys(false).toArray();
		for(short counter= 0; counter < Friend.length;counter++)
		{
			if(Friend[counter].toString().equals(target.getName()))
			{
				s.SP(player, Sound.ORB_PICKUP, 1.0F, 1.8F);
				player.sendMessage(ChatColor.RED+"[ģ��] : �̹� ģ���� ��ϵ� �÷��̾� �Դϴ�!");
				return;
			}
		}
		SideFriendsList.removeKey("Friends."+player.getName());
		SideFriendsList.set("Waitting."+player.getName(), AddTime);
		SideFriendsList.saveConfig();
		s.SP(player, Sound.ITEM_PICKUP, 1.0F, 1.8F);
		player.sendMessage(ChatColor.YELLOW+"[ģ��] : ���濡�� ģ�� ��û�� �Ͽ����ϴ�!");
		if(target.isOnline())
		{
			s.SP(target, Sound.WOLF_BARK,1.0F, 1.0F);
			target.sendMessage(ChatColor.YELLOW+"[ģ��] : "+ChatColor.WHITE+player.getName()+ChatColor.YELLOW+"�Բ��� ģ�� ��û�� �ϼ̽��ϴ�!");
			target.sendMessage(ChatColor.GOLD+"/ģ��"+ChatColor.WHITE+" ��ɾ �Է��Ͽ� ģ�� ��û�� Ȯ�� �� �ּ���!");
		}
	}
	
	public void FriendJoinQuitMessage(Player player, boolean isJoinMessage)
	{
	  	YamlController YC = new YamlController(GoldBigDragon_RPG.Main.Main.plugin);
		YamlManager FriendsList  = YC.getNewConfig("Friend/"+player.getUniqueId().toString()+".yml");
		if(FriendsList.contains("Name")==false)
		{
			FriendsList.set("Name", player.getName());
			FriendsList.set("Friends.1", null);
			FriendsList.set("Waitting.1", null);
			FriendsList.saveConfig();
			return;
		}
		Player target = null;
		YamlManager SideFriendsList  = null;
		GoldBigDragon_RPG.Effect.Sound s = new GoldBigDragon_RPG.Effect.Sound();
		Object[] Friend = FriendsList.getConfigurationSection("Friends").getKeys(false).toArray();
		for(short counter= 0; counter < Friend.length;counter++)
		{
			target = Bukkit.getServer().getPlayer(Friend[counter].toString());
			if(target!=null)
			if(target.isOnline())
			{
				SideFriendsList  = YC.getNewConfig("Friend/"+target.getUniqueId().toString()+".yml");
				if(SideFriendsList.contains("Name"))
				{
					if(SideFriendsList.contains("Friends."+player.getName()))
					{
						if(isJoinMessage)
						{
							s.SP(target, Sound.DOOR_OPEN, 0.5F, 1.0F);
							target.sendMessage(ChatColor.GREEN+""+ChatColor.BOLD+"[����] "+ChatColor.WHITE+""+ChatColor.BOLD+player.getName());
						}
						else
						{
							s.SP(target, Sound.DOOR_CLOSE, 0.5F, 1.0F);
							target.sendMessage(ChatColor.RED+""+ChatColor.BOLD+"[����] "+ChatColor.GRAY+""+ChatColor.BOLD+player.getName());
						}
						break;
					}
				}
			}
		}
	}

	public void AddExchangeTarget(Player player, Player target)
	{
		GoldBigDragon_RPG.Effect.Sound s = new GoldBigDragon_RPG.Effect.Sound();
		if(ServerTickMain.PlayerTaskList.containsKey(target.getName())==true)
		{
			s.SP(player, Sound.ORB_PICKUP, 1.0F, 1.8F);
			player.sendMessage(ChatColor.RED+"[��ȯ] : �ش� �÷��̾�� ���� �ٸ� ��û�� ó���ϰ� �ֽ��ϴ�.");
			player.sendMessage(ChatColor.GRAY+"(��� �� �ٽ� �õ� �� ������.)");
			return;
		}
		if(ServerTickMain.PlayerTaskList.containsKey(player.getName())==true)
		{
			s.SP(player, Sound.ORB_PICKUP, 1.0F, 1.8F);
			player.sendMessage(ChatColor.RED+"[��ȯ] : ����� ���� ��û���� ���� ó���ؾ� �մϴ�.");
			return;
		}
		long UTC = new ServerTickMain().getNowUTC()-1;
		ServerTickMain.PlayerTaskList.put(player.getName(), ""+UTC);
		ServerTickMain.PlayerTaskList.put(player.getName(), ""+UTC);
		player.closeInventory();
		GoldBigDragon_RPG.ServerTick.ServerTickScheduleObject STSO = new GoldBigDragon_RPG.ServerTick.ServerTickScheduleObject(UTC, "P_EC");
		STSO.setCount(0);//Ƚ �� �ʱ�ȭ
		STSO.setMaxCount(10);//��ȯ ��û �ִ� ��� �ð�
		STSO.setString((byte)0, player.getName());//��ȯ ��û �� �÷��̾� �̸� ����(�÷��̾ ������������ Ȯ���ؾ� �ϹǷ�)
		STSO.setString((byte)1, target.getName());//��ȯ ��û ���� �÷��̾� �̸� ����(�÷��̾ ������������ Ȯ���ؾ� �ϹǷ�)
		GoldBigDragon_RPG.ServerTick.ServerTickMain.Schedule.put(UTC, STSO);
		s.SP(player, Sound.NOTE_PLING, 1.0F, 1.0F);
		player.sendMessage(ChatColor.YELLOW+"[��ȯ] : ���濡�� ��ȯ ��û�� �Ͽ����ϴ�!");
		target.sendMessage(ChatColor.YELLOW+"[��ȯ] : "+ChatColor.WHITE+ChatColor.BOLD+player.getName()+ChatColor.GREEN+" �Բ��� ��ȯ ��û�� �Ͽ����ϴ�!");
		target.sendMessage(ChatColor.GOLD+"/����"+ChatColor.WHITE+" ����� ��û�� �����մϴ�.");
		target.sendMessage(ChatColor.GOLD+"/����"+ChatColor.WHITE+" ����� ��û�� �����մϴ�.");
	}

}
