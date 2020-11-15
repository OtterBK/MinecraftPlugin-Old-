package GoldBigDragon_RPG.Party;

import java.util.Arrays;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Sound;
import org.bukkit.entity.Damageable;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

import GoldBigDragon_RPG.GUI.ETCGUI;
import GoldBigDragon_RPG.GUI.EquipGUI;
import GoldBigDragon_RPG.GUI.GUIutil;

public final class PartyGUI extends GUIutil
{
	private GoldBigDragon_RPG.Effect.Sound s = new GoldBigDragon_RPG.Effect.Sound();
	private ETCGUI EGUI = new ETCGUI();

	public void PartyGUI_Main(Player player)
	{
		Inventory inv = Bukkit.createInventory(null, 45, ChatColor.BLACK + "��Ƽ");
		Stack2(ChatColor.WHITE +""+ ChatColor.BOLD + "��Ƽ ���", 340,0,1,Arrays.asList(ChatColor.GRAY + "���� ������ ��Ƽ ����� ���ϴ�."), 12, inv);
		if(GoldBigDragon_RPG.Main.ServerOption.PartyJoiner.containsKey(player)==false)
		{
			Stack2(ChatColor.WHITE +""+ ChatColor.BOLD + "��Ƽ ����", 323,0,1,Arrays.asList(ChatColor.GRAY + "���ο� ��Ƽ�� �����մϴ�."), 10, inv);
			Stack2(ChatColor.WHITE +""+ ChatColor.BOLD + "��Ƽ ����", 386,0,1,Arrays.asList(ChatColor.GRAY + "�����Ǿ� �ִ� ��Ƽ�� �����մϴ�."), 12, inv);
		}
		else
		{
			Stack2(ChatColor.WHITE +""+ ChatColor.BOLD + "��Ƽ ����", 397,3,1,Arrays.asList(ChatColor.GRAY + "���� ��Ƽ�� ������ �˾ƺ��ϴ�.",ChatColor.GRAY+"������ ���, ��Ƽ �����",ChatColor.GRAY+"���� ��ų ���� �ֽ��ϴ�."), 10, inv);
			Stack2(ChatColor.WHITE +""+ ChatColor.BOLD + "��Ƽ Ż��", 52,0,1,Arrays.asList(ChatColor.GRAY + "��Ƽ���� Ż���մϴ�."), 14, inv);
			if(GoldBigDragon_RPG.Main.ServerOption.Party.get(GoldBigDragon_RPG.Main.ServerOption.PartyJoiner.get(player)).getLeader().equalsIgnoreCase(player.getName()) == true)
			{
				Stack2(ChatColor.WHITE +""+ ChatColor.BOLD + "���� ����", 386,0,1,Arrays.asList(ChatColor.GRAY + "��Ƽ�� ������ �����մϴ�."), 28, inv);
				Stack2(ChatColor.WHITE +""+ ChatColor.BOLD + "�ο� ����", 386,0,1,Arrays.asList(ChatColor.GRAY + "���� �ο��� �����մϴ�."), 30, inv);
				if(GoldBigDragon_RPG.Main.ServerOption.Party.get(GoldBigDragon_RPG.Main.ServerOption.PartyJoiner.get(player)).getLock() == false) Stack2(ChatColor.BLUE +""+ ChatColor.BOLD + "��Ƽ ����", 54,0,1,Arrays.asList(ChatColor.GRAY + "��Ƽ ���� ��û�� �޽��ϴ�."), 34, inv);
				else Stack2(ChatColor.RED +""+ ChatColor.BOLD + "��Ƽ ���", 130,0,1,Arrays.asList(ChatColor.GRAY + "��Ƽ ���� ��û�� ���� �ʽ��ϴ�."), 34, inv);
			}
		}
		Stack2(ChatColor.WHITE  + "" + ChatColor.BOLD + "���� ���", 323,0,1,Arrays.asList(ChatColor.GRAY + "��Ÿ â���� ���ư��ϴ�."), 36, inv);
		Stack2(ChatColor.WHITE +""+ ChatColor.BOLD + "�ݱ�", 324,0,1,Arrays.asList(ChatColor.GRAY + "�۾� ������ â�� �ݽ��ϴ�."), 44, inv);
		player.openInventory(inv);
	}
	
	public void PartyListGUI(Player player, short page)
	{
		Inventory inv = Bukkit.createInventory(null, 54, ChatColor.BLACK + "��Ƽ ��� : " + (page+1));

		Object[] a = GoldBigDragon_RPG.Main.ServerOption.Party.keySet().toArray();
		if(GoldBigDragon_RPG.Main.ServerOption.Party.size() <= 0)
		{
			s.SP(player, org.bukkit.Sound.ORB_PICKUP, 2.0F, 1.7F);
			player.sendMessage(ChatColor.RED + "[��Ƽ] : ������ ��Ƽ�� �����ϴ�!");
			player.sendMessage(ChatColor.GOLD + "/��Ƽ ���� <�̸�>");
			return;
		}
		else
		{
			byte loc=0;
			for(int count = page*45; count < GoldBigDragon_RPG.Main.ServerOption.Party.size();count++)
			{
				if(count > GoldBigDragon_RPG.Main.ServerOption.Party.size() || loc >= 45) break;
				if(GoldBigDragon_RPG.Main.ServerOption.Party.get(a[count]).getLock()==false)
					Stack2(ChatColor.WHITE + "" + ChatColor.BOLD + GoldBigDragon_RPG.Main.ServerOption.Party.get(a[count]).getTitle(), 54,0,1,Arrays.asList(ChatColor.GRAY + "��Ƽ ���� �ð� : "+ChatColor.DARK_GRAY+a[count],"",ChatColor.AQUA + "��Ƽ�� : "+ChatColor.GRAY+GoldBigDragon_RPG.Main.ServerOption.Party.get(a[count]).getLeader(),ChatColor.AQUA + "�ο� : "+ChatColor.GRAY + GoldBigDragon_RPG.Main.ServerOption.Party.get(a[count]).getPartyMembers()+"/"+GoldBigDragon_RPG.Main.ServerOption.Party.get(a[count]).getCapacity()), loc, inv);
				else
					Stack2(ChatColor.WHITE + "" + ChatColor.BOLD + GoldBigDragon_RPG.Main.ServerOption.Party.get(a[count]).getTitle(), 130,0,1,Arrays.asList(ChatColor.GRAY + "��Ƽ ���� �ð� : "+ChatColor.DARK_GRAY+a[count],"",ChatColor.AQUA + "��Ƽ�� : "+ChatColor.GRAY+GoldBigDragon_RPG.Main.ServerOption.Party.get(a[count]).getLeader(),ChatColor.AQUA + "�ο� : "+ChatColor.GRAY + GoldBigDragon_RPG.Main.ServerOption.Party.get(a[count]).getPartyMembers()+"/"+GoldBigDragon_RPG.Main.ServerOption.Party.get(a[count]).getCapacity(),"",ChatColor.RED + "[���]",ChatColor.RED + "��Ƽ ������ �Ͻ÷���",ChatColor.RED +"��Ƽ�忡�� �����ϼ���!"), loc, inv);
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
	
	public void PartyMemberInformation(Player player, short page, long PartyCreateTime, boolean isLeaderChange)
	{
		Inventory inv = null;
		if(isLeaderChange == false)
			inv = Bukkit.createInventory(null, 54, ChatColor.BLACK + "��Ƽ ��� : " + (page+1));
		else
			inv = Bukkit.createInventory(null, 54, ChatColor.BLACK + "��Ƽ ���� ��ü : " + (page+1));
			
		Player[] Member = GoldBigDragon_RPG.Main.ServerOption.Party.get(PartyCreateTime).getMember();
			byte loc=0;
			for(int count = page*45; count < Member.length;count++)
			{
				ItemStack ph = null;
				if(count > Member.length || loc >= 45) break;
				Damageable pl = Member[count];
				if(player.getName().equals(GoldBigDragon_RPG.Main.ServerOption.Party.get(PartyCreateTime).getLeader()))
				{
					if(isLeaderChange == false)
					{
						if(!Member[count].getName().equals(GoldBigDragon_RPG.Main.ServerOption.Party.get(PartyCreateTime).getLeader()))
							ph = getPlayerSkull(ChatColor.WHITE+""+ChatColor.BOLD+Member[count].getName(), 1, Arrays.asList("",ChatColor.GRAY +""+ChatColor.BOLD+ "[   ����   ]",ChatColor.RED+""+ChatColor.BOLD+(int)pl.getHealth()+ChatColor.GRAY +""+ChatColor.BOLD+ " / "+ChatColor.RED+""+ChatColor.BOLD+(int)pl.getMaxHealth(),
									"",ChatColor.GRAY+""+ChatColor.BOLD+"[   ��ġ   ]",ChatColor.DARK_AQUA+pl.getLocation().getWorld().getName()+" "+(int)pl.getLocation().getX()+","+(int)pl.getLocation().getY()+","+(int)pl.getLocation().getZ(),"",ChatColor.WHITE +"" + ChatColor.BOLD+ "[   �Ϲ� ���   ]","",ChatColor.YELLOW +"" + ChatColor.BOLD+ "[ �� Ŭ���� ��� ���� ]",ChatColor.RED +"" + ChatColor.BOLD+ "[Shift + �� Ŭ���� ����]"), Member[count].getName());
						else
							ph = getPlayerSkull(ChatColor.YELLOW+""+ChatColor.BOLD+Member[count].getName(), 1, Arrays.asList("",ChatColor.GRAY +""+ChatColor.BOLD+ "[   ����   ]",ChatColor.RED+""+ChatColor.BOLD+(int)pl.getHealth()+ChatColor.GRAY +""+ChatColor.BOLD+ " / "+ChatColor.RED+""+ChatColor.BOLD+(int)pl.getMaxHealth(),
									"",ChatColor.GRAY+""+ChatColor.BOLD+"[   ��ġ   ]",ChatColor.DARK_AQUA+pl.getLocation().getWorld().getName()+" "+(int)pl.getLocation().getX()+","+(int)pl.getLocation().getY()+","+(int)pl.getLocation().getZ(),"",ChatColor.YELLOW +"" + ChatColor.BOLD+ "[   ��Ƽ ����   ]","",ChatColor.YELLOW +"" + ChatColor.BOLD+ "[ �� Ŭ���� ��� ���� ]"), Member[count].getName());
					}
					else
					{
						if(!Member[count].getName().equals(GoldBigDragon_RPG.Main.ServerOption.Party.get(PartyCreateTime).getLeader()))
							ph = getPlayerSkull(ChatColor.WHITE+""+ChatColor.BOLD+Member[count].getName(), 1, Arrays.asList("",ChatColor.GRAY +""+ChatColor.BOLD+ "[   ����   ]",ChatColor.RED+""+ChatColor.BOLD+(int)pl.getHealth()+ChatColor.GRAY +""+ChatColor.BOLD+ " / "+ChatColor.RED+""+ChatColor.BOLD+(int)pl.getMaxHealth(),
									"",ChatColor.GRAY+""+ChatColor.BOLD+"[   ��ġ   ]",ChatColor.DARK_AQUA+pl.getLocation().getWorld().getName()+" "+(int)pl.getLocation().getX()+","+(int)pl.getLocation().getY()+","+(int)pl.getLocation().getZ(),"",ChatColor.WHITE +"" + ChatColor.BOLD+ "[   �Ϲ� ���   ]","",ChatColor.YELLOW +"" + ChatColor.BOLD+ "[ �� Ŭ���� ���� ���� ]"), Member[count].getName());
						else
							ph = getPlayerSkull(ChatColor.YELLOW+""+ChatColor.BOLD+Member[count].getName(), 1, Arrays.asList("",ChatColor.GRAY +""+ChatColor.BOLD+ "[   ����   ]",ChatColor.RED+""+ChatColor.BOLD+(int)pl.getHealth()+ChatColor.GRAY +""+ChatColor.BOLD+ " / "+ChatColor.RED+""+ChatColor.BOLD+(int)pl.getMaxHealth(),
									"",ChatColor.GRAY+""+ChatColor.BOLD+"[   ��ġ   ]",ChatColor.DARK_AQUA+pl.getLocation().getWorld().getName()+" "+(int)pl.getLocation().getX()+","+(int)pl.getLocation().getY()+","+(int)pl.getLocation().getZ(),"",ChatColor.YELLOW +"" + ChatColor.BOLD+ "[   ��Ƽ ����   ]","",ChatColor.RED +"" + ChatColor.BOLD+ "[ ���� ���� �Ұ��� ]"), Member[count].getName());
					}
				}
				else
				{
					if(!Member[count].getName().equals(GoldBigDragon_RPG.Main.ServerOption.Party.get(PartyCreateTime).getLeader()))
						ph = getPlayerSkull(ChatColor.WHITE+""+ChatColor.BOLD+Member[count].getName(), 1, Arrays.asList("",ChatColor.GRAY +""+ChatColor.BOLD+ "[   ����   ]",ChatColor.RED+""+ChatColor.BOLD+(int)pl.getHealth()+ChatColor.GRAY +""+ChatColor.BOLD+ " / "+ChatColor.RED+""+ChatColor.BOLD+(int)pl.getMaxHealth(),
								"",ChatColor.GRAY+""+ChatColor.BOLD+"[   ��ġ   ]",ChatColor.DARK_AQUA+pl.getLocation().getWorld().getName()+" "+(int)pl.getLocation().getX()+","+(int)pl.getLocation().getY()+","+(int)pl.getLocation().getZ(),"",ChatColor.WHITE +"" + ChatColor.BOLD+ "[   �Ϲ� ���   ]","",ChatColor.YELLOW +"" + ChatColor.BOLD+ "[ �� Ŭ���� ��� ���� ]"), Member[count].getName());
					else
						ph = getPlayerSkull(ChatColor.YELLOW+""+ChatColor.BOLD+Member[count].getName(), 1, Arrays.asList("",ChatColor.GRAY +""+ChatColor.BOLD+ "[   ����   ]",ChatColor.RED+""+ChatColor.BOLD+(int)pl.getHealth()+ChatColor.GRAY +""+ChatColor.BOLD+ " / "+ChatColor.RED+""+ChatColor.BOLD+(int)pl.getMaxHealth(),
								"",ChatColor.GRAY+""+ChatColor.BOLD+"[   ��ġ   ]",ChatColor.DARK_AQUA+pl.getLocation().getWorld().getName()+" "+(int)pl.getLocation().getX()+","+(int)pl.getLocation().getY()+","+(int)pl.getLocation().getZ(),"",ChatColor.YELLOW +"" + ChatColor.BOLD+ "[   ��Ƽ ����   ]","",ChatColor.YELLOW +"" + ChatColor.BOLD+ "[ �� Ŭ���� ��� ���� ]"), Member[count].getName());
				}

				ItemStackStack(ph, loc, inv);
				loc++;
			}
		if(Member.length-(page*44)>45)
			Stack2(ChatColor.WHITE + "" + ChatColor.BOLD + "���� ������", 323,0,1,Arrays.asList(ChatColor.GRAY + "���� �������� �̵� �մϴ�."), 50, inv);
		if(page!=0)
			Stack2(ChatColor.WHITE + "" + ChatColor.BOLD + "���� ������", 323,0,1,Arrays.asList(ChatColor.GRAY + "���� �������� �̵� �մϴ�."), 48, inv);
		
		Stack2(ChatColor.WHITE + "" + ChatColor.BOLD + "���� ���", 323,0,1,Arrays.asList(ChatColor.GRAY + "���� ȭ������ ���ư��ϴ�."), 45, inv);
		Stack2(ChatColor.WHITE + "" + ChatColor.BOLD + "�ݱ�", 324,0,1,Arrays.asList(ChatColor.GRAY + "â�� �ݽ��ϴ�."), 53, inv);
		player.openInventory(inv);
	}
	
	
	public void PartyGUIClickRouter(InventoryClickEvent event, String InventoryName)
	{
	    if(InventoryName.compareTo("��Ƽ")==0)
	    	partyInventoryclick(event);
		else if(InventoryName.contains("���"))
			PartyListInventoryclick(event);
		else if(InventoryName.contains("���")||InventoryName.contains("��ü"))
			PartyMemberInformationClick(event);
		return;
	}
	
	
	public void partyInventoryclick(InventoryClickEvent event)
	{
		event.setCancelled(true);
		Player player = (Player) event.getWhoClicked();
		switch ((ChatColor.stripColor(event.getCurrentItem().getItemMeta().getDisplayName())))
		{
		case "��Ƽ ����":
			s.SP(player, Sound.ITEM_PICKUP, 0.8F, 1.8F);
			player.closeInventory();
			player.sendMessage(ChatColor.GOLD + "/��Ƽ ���� <�̸�>");
			break;
		case "��Ƽ ����":
		case "��Ƽ ���":
			s.SP(player, Sound.ITEM_PICKUP, 0.8F, 1.8F);
			PartyListGUI(player, (short) 0);
			break;
		case "��Ƽ Ż��":
			player.closeInventory();
			GoldBigDragon_RPG.Main.ServerOption.Party.get(GoldBigDragon_RPG.Main.ServerOption.PartyJoiner.get(player)).QuitParty(player);
			break;
		case "��Ƽ ����":
			s.SP(player, Sound.ITEM_PICKUP, 0.8F, 1.8F);
			player.closeInventory();
			PartyMemberInformation(player, (short) 0, GoldBigDragon_RPG.Main.ServerOption.PartyJoiner.get(player),false);
			break;
		case "���� ����":
			s.SP(player, Sound.ITEM_PICKUP, 0.8F, 1.8F);
			PartyMemberInformation(player, (short) 0, GoldBigDragon_RPG.Main.ServerOption.PartyJoiner.get(player),true);
			break;
		case "�ο� ����":
			s.SP(player, Sound.ITEM_PICKUP, 0.8F, 1.8F);
			player.closeInventory();	player.sendMessage(ChatColor.GOLD + "/��Ƽ �ο� [�ο� ��]");
			break;
		case "����":
			s.SP(player, Sound.ITEM_PICKUP, 0.8F, 1.8F);
			player.closeInventory();
			player.sendMessage(ChatColor.GOLD + "/��Ƽ ���� [�г���]");
			break;
		case "��Ƽ ���":
		case "��Ƽ ����":
			GoldBigDragon_RPG.Main.ServerOption.Party.get(GoldBigDragon_RPG.Main.ServerOption.PartyJoiner.get(player)).ChangeLock(player);
			PartyGUI_Main(player);
			break;
		case "���� ���":
			s.SP(player, Sound.ITEM_PICKUP, 0.8F, 1.0F);
			break;
		case "���� ���":
			s.SP(player, Sound.ITEM_PICKUP, 0.8F, 1.0F);
			EGUI.ETCGUI_Main(player);
			break;
		case "�ݱ�":
			s.SP(player, Sound.PISTON_RETRACT, 0.8F, 1.8F);
			player.closeInventory();
			break;
		}
			return;
	}
	
	public void PartyListInventoryclick(InventoryClickEvent event)
	{
		event.setCancelled(true);
		Player player = (Player) event.getWhoClicked();
		
		switch ((ChatColor.stripColor(event.getCurrentItem().getItemMeta().getDisplayName())))
		{
			case "���� ������":
				s.SP(player, Sound.ITEM_PICKUP, 0.8F, 1.0F);
				PartyListGUI(player,(short) (Integer.parseInt(event.getInventory().getTitle().split(" : ")[1])-2));
				break;
			case "���� ������":
				s.SP(player, Sound.ITEM_PICKUP, 0.8F, 1.0F);
				PartyListGUI(player,(short) Integer.parseInt(event.getInventory().getTitle().split(" : ")[1]));
				break;
			case "���� ���":
				s.SP(player, Sound.ITEM_PICKUP, 0.8F, 1.0F);
				PartyGUI_Main(player);
				break;
			case "�ݱ�":
				s.SP(player, Sound.PISTON_RETRACT, 0.8F, 1.8F);
				player.closeInventory();
				break;
			default:
				if(GoldBigDragon_RPG.Main.ServerOption.Party.get(Long.parseLong(ChatColor.stripColor(event.getCurrentItem().getItemMeta().getLore().get(0).split(" : ")[1]))).getLock())
				{
				  s.SP(player, org.bukkit.Sound.ORB_PICKUP, 2.0F, 1.7F);
		    	  player.sendMessage(ChatColor.RED + "[��Ƽ] : �ش� ��Ƽ�� ��� �����Դϴ�! �������� �����ϼ���!");
				}
				else
				{
					GoldBigDragon_RPG.Main.ServerOption.Party.get(Long.parseLong(ChatColor.stripColor(event.getCurrentItem().getItemMeta().getLore().get(0).split(" : ")[1]))).JoinParty(player);
					PartyGUI_Main(player);
				}
				return;
		}
		return;
	}

	public void PartyMemberInformationClick(InventoryClickEvent event)
	{
		event.setCancelled(true);
		Player player = (Player) event.getWhoClicked();
		boolean isLeaderChange = false;
		if(event.getInventory().getTitle().contains("��ü"))
			isLeaderChange = true;
			
		switch ((ChatColor.stripColor(event.getCurrentItem().getItemMeta().getDisplayName())))
		{
			case "���� ������":
				s.SP(player, Sound.ITEM_PICKUP, 0.8F, 1.0F);
				PartyMemberInformation(player,(short) (Integer.parseInt(event.getInventory().getTitle().split(" : ")[1])-2),GoldBigDragon_RPG.Main.ServerOption.PartyJoiner.get(player),isLeaderChange);
				break;
			case "���� ������":
				s.SP(player, Sound.ITEM_PICKUP, 0.8F, 1.0F);
				PartyMemberInformation(player,(short) Integer.parseInt(event.getInventory().getTitle().split(" : ")[1]),GoldBigDragon_RPG.Main.ServerOption.PartyJoiner.get(player),isLeaderChange);
				break;
			case "���� ���":
				s.SP(player, Sound.ITEM_PICKUP, 0.8F, 1.0F);
				PartyGUI_Main(player);
				break;
			case "�ݱ�":
				s.SP(player, Sound.PISTON_RETRACT, 0.8F, 1.8F);
				player.closeInventory();
				break;
			default:
				if(event.isLeftClick())
				{
					if(isLeaderChange == false)
					{
						s.SP(player, Sound.ITEM_PICKUP, 0.8F, 1.0F);
						new EquipGUI().EquipWatchGUI(player, Bukkit.getServer().getPlayer(ChatColor.stripColor(event.getCurrentItem().getItemMeta().getDisplayName())));
					}
					else
					{
						GoldBigDragon_RPG.Main.ServerOption.Party.get(GoldBigDragon_RPG.Main.ServerOption.PartyJoiner.get(player)).ChangeLeader(player, ChatColor.stripColor(event.getCurrentItem().getItemMeta().getDisplayName()));
					}
				}
				else if(event.isRightClick()&&event.isShiftClick())
				{
					if(isLeaderChange == false)
					{
						GoldBigDragon_RPG.Main.ServerOption.Party.get(GoldBigDragon_RPG.Main.ServerOption.PartyJoiner.get(player)).KickPartyMember(player, ChatColor.stripColor(event.getCurrentItem().getItemMeta().getDisplayName()));
						PartyMemberInformation(player,(short) (Integer.parseInt(event.getInventory().getTitle().split(" : ")[1])-1),GoldBigDragon_RPG.Main.ServerOption.PartyJoiner.get(player),isLeaderChange);
					}
				}
				return;
		}
		return;
		
	}


}
