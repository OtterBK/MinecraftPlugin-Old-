package GoldBigDragon_RPG.Structure;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import GoldBigDragon_RPG.GUI.GUIutil;
import GoldBigDragon_RPG.Main.Object_UserData;
import GoldBigDragon_RPG.Main.ServerOption;
import GoldBigDragon_RPG.Util.YamlController;
import GoldBigDragon_RPG.Util.YamlManager;

public class Structure_PostBox extends GUIutil
{
	public void PostBoxMainGUI(Player player, byte Type)
	{
		YamlController YC = new YamlController(GoldBigDragon_RPG.Main.Main.plugin);
		YamlManager PlayerPost =YC.getNewConfig("Post/"+player.getUniqueId().toString()+".yml");
		Inventory inv = Bukkit.createInventory(null, 45, ChatColor.RED +""+ChatColor.BOLD +""+ "������");
		if(Type==0)//���� ����
		{
			if(PlayerPost.contains("Recieve")==false)
			{
				PlayerPost.set("Recieve.1", null);
				PlayerPost.saveConfig();
			}
			Object[] PostList = PlayerPost.getConfigurationSection("Recieve").getKeys(false).toArray();
			byte loc=2;
			for(byte count = 0; count < PostList.length;count++)
			{
				if(count>=25)
					break;
				String PostFrom = PlayerPost.getString("Recieve."+PostList[count].toString()+".From");
				String PostTitle = PlayerPost.getString("Recieve."+PostList[count].toString()+".Title");
				String PostMemo = PlayerPost.getString("Recieve."+PostList[count].toString()+".Memo");
				ItemStack PostItem = PlayerPost.getItemStack("Recieve."+PostList[count].toString()+".Item");

				List<String> Memo = new ArrayList<String>();
				Memo.add("");
				Memo.add(ChatColor.BLUE+"���� : "+ChatColor.WHITE+PostTitle);
				Memo.add("");
				for(byte count2=0;count2<(PostMemo.length()/20)+1;count2++)
				{
					if((count2+1)*20<PostMemo.length())
						Memo.add(ChatColor.WHITE+PostMemo.substring(0+(count2*20), ((count2+1)*20)));
					else
						Memo.add(ChatColor.WHITE+PostMemo.substring(0+(count2*20), PostMemo.length()));
				}
				Memo.add("");
				Memo.add(ChatColor.BLUE+"���� �� : " + ChatColor.WHITE+PostFrom);
				if(PostItem==null)
				{
					Memo.add(ChatColor.YELLOW+"[�� Ŭ���� �޽��� ����]");
					Memo.add(ChatColor.BLACK+PostList[count].toString());
					Stack2(ChatColor.WHITE+"[�޽���]", 358,0,1,Memo, loc, inv);
				}
				else
				{
					int PostValue = PlayerPost.getInt("Recieve."+PostList[count].toString()+".Value");
					ItemMeta PIMeta = PostItem.getItemMeta();
					if(PostItem.hasItemMeta())
					{
						Memo.add(ChatColor.BLUE +"��� û�� : " + ChatColor.WHITE+PostValue);
						Memo.add(ChatColor.YELLOW +"[�� Ŭ���� ��ǰ ����]");
						if(PlayerPost.getString("Recieve."+PostList[count]+".From").compareTo("[�ݼ�]")!=0
						&&PlayerPost.getString("Recieve."+PostList[count]+".From").compareTo("[��ǰ ȸ��]")!=0
						&&PlayerPost.getString("Recieve."+PostList[count]+".From").compareTo("[�ŷ� �Խ���]")!=0
						&&PlayerPost.getString("Recieve."+PostList[count]+".From").compareTo("[�̺�Ʈ]")!=0
						&&PlayerPost.getString("Recieve."+PostList[count]+".From").compareTo("[�ý���]")!=0)
							Memo.add(ChatColor.RED +"[�� Ŭ���� ��ǰ �ݼ�]");
						Memo.add(ChatColor.BLACK+PostList[count].toString());
					}
					else
					{
						Memo.add(ChatColor.BLUE+"��� û�� : " + ChatColor.WHITE+PostValue);
						Memo.add(ChatColor.YELLOW+"[�� Ŭ���� ��ǰ ����]");
						if(PlayerPost.getString("Recieve."+PostList[count]+".From").compareTo("[�ݼ�]")!=0
						&&PlayerPost.getString("Recieve."+PostList[count]+".From").compareTo("[��ǰ ȸ��]")!=0
						&&PlayerPost.getString("Recieve."+PostList[count]+".From").compareTo("[�ŷ� �Խ���]")!=0
						&&PlayerPost.getString("Recieve."+PostList[count]+".From").compareTo("[�̺�Ʈ]")!=0
						&&PlayerPost.getString("Recieve."+PostList[count]+".From").compareTo("[�ý���]")!=0)
							Memo.add(ChatColor.RED +"[�� Ŭ���� ��ǰ �ݼ�]");
						Memo.add(ChatColor.BLACK+PostList[count].toString());
						PIMeta.setLore(Memo);
					}
					PIMeta.setLore(Memo);
					PostItem.setItemMeta(PIMeta);
					ItemStackStack(PostItem, loc, inv);
				}
				if(loc==6||loc==15||loc==24||loc==33||loc==42)
					loc = (byte) (loc+5);
				else
					loc++;
			}
		}
		else//���� ����
		{
			if(PlayerPost.contains("Send")==false)
			{
				PlayerPost.set("Send.1", null);
				PlayerPost.saveConfig();
			}
			Object[] PostList = PlayerPost.getConfigurationSection("Send").getKeys(false).toArray();
			byte loc=2;
			for(byte count = 0; count < PostList.length;count++)
			{
				if(count==25)
					break;
				String PostTo = PlayerPost.getString("Send."+PostList[count].toString()+".To");
				String PostTitle = PlayerPost.getString("Send."+PostList[count].toString()+".Title");
				String PostMemo = PlayerPost.getString("Send."+PostList[count].toString()+".Memo");
				ItemStack PostItem = PlayerPost.getItemStack("Send."+PostList[count].toString()+".Item");
	
				List<String> Memo = new ArrayList<String>();
				Memo.add("");
				Memo.add(ChatColor.BLUE+"���� : "+ChatColor.WHITE+PostTitle);
				Memo.add("");
				for(int count2=0;count2<(PostMemo.length()/20)+1;count2++)
				{
					if((count2+1)*20<PostMemo.length())
						Memo.add(ChatColor.WHITE+PostMemo.substring(0+(count2*20), ((count2+1)*20)));
					else
						Memo.add(ChatColor.WHITE+PostMemo.substring(0+(count2*20), PostMemo.length()));
				}
				Memo.add("");
				Memo.add(ChatColor.BLUE+"�޴� �� : " + ChatColor.WHITE+PostTo);
				if(PostItem==null)
				{
					Memo.add(ChatColor.YELLOW+"[�� Ŭ���� �޽��� ���� ���]");
					Memo.add(ChatColor.BLACK+PostList[count].toString());
					Stack2(ChatColor.WHITE+"[�޽���]", 358,0,1,Memo, loc, inv);
				}
				else
				{
					int PostValue = PlayerPost.getInt("Send."+PostList[count].toString()+".Value");
					ItemMeta PIMeta = PostItem.getItemMeta();
					if(PostItem.hasItemMeta())
					{
						PIMeta.getLore().add("");
						PIMeta.getLore().add(ChatColor.BLUE +"���� �� : " + ChatColor.WHITE+PostTo);
						PIMeta.getLore().add(ChatColor.BLUE +"��� û�� : " + ChatColor.WHITE+PostValue);
						PIMeta.getLore().add(ChatColor.YELLOW +"[�� Ŭ���� ��ǰ ȸ��]");
						PIMeta.getLore().add(ChatColor.BLACK+PostList[count].toString());
					}
					else
					{
						Memo.add(ChatColor.BLUE+"��� û�� : " + ChatColor.WHITE+PostValue);
						Memo.add(ChatColor.YELLOW+"[�� Ŭ���� ��ǰ ȸ��]");
						Memo.add(ChatColor.BLACK+PostList[count].toString());
						PIMeta.setLore(Memo);
					}
					PostItem.setItemMeta(PIMeta);
					ItemStackStack(PostItem, loc, inv);
				}
				if(loc==6||loc==15||loc==24||loc==33||loc==42)
					loc = (byte) (loc+5);
				else
					loc++;
			}
		}

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

		int id = 166;
		if(Type==0)//���� ����
			id = 166;
		else
			id = 54;
		Stack2(ChatColor.WHITE + "" + ChatColor.BOLD + "[���� ����]", id,0,1,Arrays.asList(ChatColor.GRAY + "���� ������ Ȯ���մϴ�."), 0, inv);

		if(Type==0)//���� ����
			id = 333;
		else
			id = 166;
		Stack2(ChatColor.WHITE + "" + ChatColor.BOLD + "[���� ����]", id,0,1,Arrays.asList(ChatColor.GRAY + "���� ������ Ȯ���մϴ�."), 9, inv);
		Stack2(ChatColor.WHITE + "" + ChatColor.BOLD + "[�ݱ�]", 324,0,1,Arrays.asList(ChatColor.GRAY + "â�� �ݽ��ϴ�.",ChatColor.BLACK+""+Type), 26, inv);
		Stack2(ChatColor.WHITE + "" + ChatColor.BOLD + "[���� ����]", 386,0,1,Arrays.asList(ChatColor.GRAY + "���ο� ������ �����ϴ�."), 36, inv);
		player.openInventory(inv);
		return;
	}

	public void ItemPutterGUI(Player player)
	{
		Inventory inv = Bukkit.createInventory(null, 9, ChatColor.RED +""+ChatColor.BOLD +""+ "���� ������");
		Stack2(ChatColor.RED + " ", 166,0,1,null, 0, inv);
		Stack2(ChatColor.RED + " ", 166,0,1,null, 1, inv);
		Stack2(ChatColor.RED + " ", 166,0,1,null, 2, inv);
		Stack2(ChatColor.RED + " ", 166,0,1,null, 3, inv);
		Stack2(ChatColor.RED + " ", 166,0,1,null, 5, inv);
		Stack2(ChatColor.RED + " ", 166,0,1,null, 6, inv);
		Stack2(ChatColor.RED + " ", 166,0,1,null, 7, inv);
		Stack2(ChatColor.RED + " ", 166,0,1,null, 8, inv);
		player.openInventory(inv);
	}
	
	
	
	
	public void PostBoxMainGUIClick(InventoryClickEvent event)
	{
		GoldBigDragon_RPG.Effect.Sound s = new GoldBigDragon_RPG.Effect.Sound();
		event.setCancelled(true);
		Player player = (Player) event.getWhoClicked();
		player.updateInventory();
		byte Type = Byte.parseByte(ChatColor.stripColor(event.getInventory().getItem(26).getItemMeta().getLore().get(1)));

		YamlController YC = new YamlController(GoldBigDragon_RPG.Main.Main.plugin);
		YamlManager PlayerPost =YC.getNewConfig("Post/"+player.getUniqueId().toString()+".yml");

		if(event.getClickedInventory().getName().compareTo(ChatColor.RED +""+ChatColor.BOLD +""+ "������")==0)
		switch (event.getSlot())
		{
		case 0://������
			s.SP(player, Sound.CHEST_OPEN, 0.8F, 1.0F);
			PostBoxMainGUI(player, (byte) 0);
			return;
		case 9://�۽���
			s.SP(player, Sound.CHEST_OPEN, 0.8F, 1.0F);
			PostBoxMainGUI(player, (byte) 1);
			return;
		case 26://������
			s.SP(player, Sound.PISTON_RETRACT, 0.8F, 1.8F);
			player.closeInventory();
			return;
		case 36://�� ����
			if(PlayerPost.contains("Send"))
				if(PlayerPost.getConfigurationSection("Send").getKeys(false).size()<25)
				{
					Object_UserData u = new Object_UserData();
					s.SP(player, Sound.STEP_WOOL, 0.8F, 1.8F);
					u.setTemp(player, "Structure");
					u.setType(player, "Post");
					u.setString(player, (byte)0, "RN");//Reciever Nickname
					u.setString(player, (byte)1, "");//�޴���
					u.setString(player, (byte)2, ChatColor.WHITE+"���� ����");//���� ����
					u.setString(player, (byte)3, ChatColor.WHITE+"���� ����");//���� ����
					u.setBoolean(player, (byte)0, false);//������ �ۺ�
					u.setInt(player, (byte)0, 0);//��� û��
					player.closeInventory();
					player.sendMessage(ChatColor.GREEN+"[����] : ������ ���� �г����� �Է� �ϼ���.");
				}
				else
				{
					s.SP(player, Sound.ORB_PICKUP, 0.8F, 1.8F);
					player.sendMessage(ChatColor.RED+"[����] : ������ �ִ� 25�� ������ ���� �� �ֽ��ϴ�.");
				}
			else
			{
				Object_UserData u = new Object_UserData();
				s.SP(player, Sound.STEP_WOOL, 0.8F, 1.8F);
				u.setTemp(player, "Structure");
				u.setType(player, "Post");
				u.setString(player, (byte)0, "RN");//Reciever Nickname
				u.setString(player, (byte)1, "");//�޴���
				u.setString(player, (byte)2, ChatColor.WHITE+"���� ����");//���� ����
				u.setString(player, (byte)3, ChatColor.WHITE+"���� ����");//���� ����
				u.setBoolean(player, (byte)0, false);//������ �ۺ�
				u.setInt(player, (byte)0, 0);//��� û��
				player.closeInventory();
				player.sendMessage(ChatColor.GREEN+"[����] : ������ ���� �г����� �Է� �ϼ���.");
			}
			return;
		case 1:
		case 7:
		case 10:
		case 16:
		case 19:
		case 25:
		case 28:
		case 34:
		case 37:
		case 43:
			return;
		default :
			if(event.getCurrentItem().hasItemMeta())
			{
				s.SP(player, Sound.ITEM_PICKUP, 0.8F, 1.0F);
				long UTC = Long.parseLong(ChatColor.stripColor(event.getCurrentItem().getItemMeta().getLore().get(event.getCurrentItem().getItemMeta().getLore().size()-1)));
				if(Type==0)//������
				{
					if(PlayerPost.contains("Recieve."+UTC)==false)
					{
						PostBoxMainGUI(player, Type);
						return;
					}
					String Sender = PlayerPost.getString("Recieve."+UTC+".From");
					if(Sender.compareTo("[�ý���]")==0|| Sender.compareTo("[�ݼ�]")==0||
					Sender.compareTo("[�ŷ� ������]")==0||Sender.compareTo("[�ŷ� �Խ���]")==0)
					{
						if(new GoldBigDragon_RPG.Util.PlayerUtil().giveItem(player, PlayerPost.getItemStack("Recieve."+UTC+".Item")))
						{
							PlayerPost.removeKey("Recieve."+UTC);
							PlayerPost.saveConfig();
							PostBoxMainGUI(player, Type);
							return;
						}
						else
						{
							s.SP(player, Sound.ORB_PICKUP, 1.0F, 1.8F);
							player.sendMessage(ChatColor.RED+"[����] : �κ��丮 ������ �����մϴ�!");
						}
					}
					else
					{

						Sender = Bukkit.getOfflinePlayer(Sender).getUniqueId().toString();
						YamlManager SenderPost =YC.getNewConfig("Post/"+Sender+".yml");
						if(PlayerPost.getItemStack("Recieve."+UTC+".Item")==null)
						{
							PlayerPost.removeKey("Recieve."+UTC);
							PlayerPost.saveConfig();
							SenderPost.removeKey("Send."+UTC);
							SenderPost.saveConfig();
							PostBoxMainGUI(player, Type);
							return;
						}
						else
						{
							if(event.isLeftClick())
							{
								if(PlayerPost.getInt("Recieve."+UTC+".Value")==0)
								{
									if(new GoldBigDragon_RPG.Util.PlayerUtil().giveItem(player, PlayerPost.getItemStack("Recieve."+UTC+".Item")))
									{
										PlayerPost.removeKey("Recieve."+UTC);
										PlayerPost.saveConfig();
										SenderPost.removeKey("Send."+UTC);
										SenderPost.saveConfig();
										PostBoxMainGUI(player, Type);
										return;
									}
									else
									{
										s.SP(player, Sound.ORB_PICKUP, 1.0F, 1.8F);
										player.sendMessage(ChatColor.RED+"[����] : �κ��丮 ������ �����մϴ�!");
									}
								}
								else
								{
									if(ServerOption.PlayerList.get(player.getUniqueId().toString()).getStat_Money() >=PlayerPost.getInt("Recieve."+UTC+".Value"))
									{
										if(new GoldBigDragon_RPG.Util.PlayerUtil().giveItem(player, PlayerPost.getItemStack("Recieve."+UTC+".Item")))
										{
											ServerOption.PlayerList.get(player.getUniqueId().toString()).setStat_Money(ServerOption.PlayerList.get(player.getUniqueId().toString()).getStat_Money() -PlayerPost.getInt("Recieve."+UTC+".Value")); 
											if(ServerOption.PlayerList.containsKey(Sender))
												ServerOption.PlayerList.get(Sender).setStat_Money(ServerOption.PlayerList.get(Sender).getStat_Money() +PlayerPost.getInt("Recieve."+UTC+".Value"));
											else
											{
												YamlManager target = YC.getNewConfig("Stats/"+Sender+".yml");
												target.set("Stat.Money", target.getLong("Stat.Money")+PlayerPost.getInt("Recieve."+UTC+".Value"));
											}
											Sender = Bukkit.getPlayer(PlayerPost.getString("Recieve."+UTC+".From")).getUniqueId().toString();
											int value = PlayerPost.getInt("Recieve."+UTC+".Value");
											PlayerPost.removeKey("Recieve."+UTC);
											PlayerPost.saveConfig();
											SenderPost.removeKey("Send."+UTC);
											SenderPost.saveConfig();
											PostBoxMainGUI(player, Type);
											SendPost_Server(Sender, "[�ŷ� ������]", "[���� �Ա� �Ϸ�]", player.getName()+" �Բ��� ��� "+value+" "+GoldBigDragon_RPG.Main.ServerOption.Money+ChatColor.WHITE+" �Ա��Ͽ����ϴ�.", null);
											return;
										}
										else
										{
											s.SP(player, Sound.ORB_PICKUP, 1.0F, 1.8F);
											player.sendMessage(ChatColor.RED+"[����] : �κ��丮 ������ �����մϴ�!");
										}
									}
									else
									{
										s.SP(player, Sound.ORB_PICKUP, 1.0F, 1.8F);
										player.sendMessage(ChatColor.RED+"[����] : �������� �����մϴ�!");
									}
								}
							}
							else if(event.isRightClick()&&event.getCurrentItem().getItemMeta().getLore().get(event.getCurrentItem().getItemMeta().getLore().size()-2).contains("�ݼ�")==true)
							{
								if(PlayerPost.getString("Recieve."+UTC+".From").compareTo("[�ݼ�]")!=0)
								{
									SenderPost.removeKey("Send."+UTC);
									SenderPost.saveConfig();
									Sender = Bukkit.getPlayer(PlayerPost.getString("Recieve."+UTC+".From")).getUniqueId().toString();
									SendPost_Server(Sender, "[�ݼ�]", "[��ǰ �ݼ�]", player.getName()+" �Բ��� ��ǰ�� �ݼ��Ͽ����ϴ�.", PlayerPost.getItemStack("Recieve."+UTC+".Item"));
									PlayerPost.removeKey("Recieve."+UTC);
									PlayerPost.saveConfig();
									PostBoxMainGUI(player, Type);
								}
								return;
							}
						}
					}
				}
				else//�۽���
				{
					if(PlayerPost.contains("Send."+UTC)==false)
					{
						PostBoxMainGUI(player, Type);
						return;
					}
					String Sender = PlayerPost.getString("Send."+UTC+".To");
					Sender = Bukkit.getOfflinePlayer(Sender).getUniqueId().toString();
					YamlManager SenderPost =YC.getNewConfig("Post/"+Sender+".yml");
					if(PlayerPost.getItemStack("Send."+UTC+".Item")==null)
					{
						PlayerPost.removeKey("Send."+UTC);
						PlayerPost.saveConfig();
						SenderPost.removeKey("Recieve."+UTC);
						SenderPost.saveConfig();
						PostBoxMainGUI(player, Type);
						return;
					}
					else
					{
						if(new GoldBigDragon_RPG.Util.PlayerUtil().giveItem(player, PlayerPost.getItemStack("Send."+UTC+".Item")))
						{
							PlayerPost.removeKey("Send."+UTC);
							PlayerPost.saveConfig();
							SenderPost.removeKey("Recieve."+UTC);
							SenderPost.saveConfig();
							PostBoxMainGUI(player, Type);
							return;
						}
						else
						{
							s.SP(player, Sound.ORB_PICKUP, 1.0F, 1.8F);
							player.sendMessage(ChatColor.RED+"[����] : �κ��丮 ������ �����մϴ�!");
						}
					}
				}
			}
			return;
		}
	}
	
	public void ItemPutterGUIClick(InventoryClickEvent event)
	{
		GoldBigDragon_RPG.Effect.Sound s = new GoldBigDragon_RPG.Effect.Sound();
		Player player = (Player) event.getWhoClicked();
		if(event.getCurrentItem().getTypeId()==166&&event.getCurrentItem().hasItemMeta())
		{
			switch (event.getSlot())
			{
			case 0:
			case 1:
			case 2:
			case 3:
			case 5:
			case 6:
			case 7:
			case 8:
				s.SP(player, Sound.ANVIL_LAND, 0.8F, 1.9F);
				event.setCancelled(true);
				return;
			}
		}
	}
	

	public void ItemPutterGUIClose(InventoryCloseEvent event)
	{
		ItemStack item = event.getInventory().getItem(4);
		Player player = (Player) event.getPlayer();
		if(item!=null)
		{
			GoldBigDragon_RPG.Effect.Sound s = new GoldBigDragon_RPG.Effect.Sound();
			Object_UserData u = new Object_UserData();
			u.setItemStack(player, item);
			s.SP(player, Sound.PISTON_RETRACT, 1.0F, 1.0F);
			u.setString(player, (byte)0, "Value");
			u.setTemp(player,"Structure");
			player.sendMessage(ChatColor.GREEN+"[����] : ���� ������ ���� ����� �Է� �ϼ���.");
		}
		else
			SendPost(player);
	}


	public void SendPost(Player player)
	{
		GoldBigDragon_RPG.Effect.Sound s = new GoldBigDragon_RPG.Effect.Sound();
		Object_UserData u = new Object_UserData();
		String targetUID = Bukkit.getPlayer(u.getString(player, (byte)1)).getUniqueId().toString();
		YamlController YC = new YamlController(GoldBigDragon_RPG.Main.Main.plugin);
		YamlManager TargetPost =YC.getNewConfig("Post/"+targetUID+".yml");
		YamlManager PlayerPost =YC.getNewConfig("Post/"+player.getUniqueId().toString()+".yml");
		if(TargetPost.contains("Recieve")==false)
		{
			TargetPost.set("Recieve.1", null);
			TargetPost.saveConfig();
		}
		if(TargetPost.contains("Send")==false)
		{
			TargetPost.set("Send.1", null);
			TargetPost.saveConfig();
		}
		long UTC = new GoldBigDragon_RPG.ServerTick.ServerTickMain().nowUTC +new GoldBigDragon_RPG.Util.Number().RandomNum(0, 1200);
		if(TargetPost.getConfigurationSection("Recieve").getKeys(false).size()<25)
		{
			TargetPost.set("Recieve."+UTC+".From", player.getName());
			TargetPost.set("Recieve."+UTC+".Title", u.getString(player, (byte)2));
			TargetPost.set("Recieve."+UTC+".Memo", u.getString(player, (byte)3));
			TargetPost.set("Recieve."+UTC+".Item", u.getItemStack(player));
			TargetPost.set("Recieve."+UTC+".Value", u.getInt(player,(byte)0));
			PlayerPost.set("Send."+UTC+".To", u.getString(player, (byte)1));
			PlayerPost.set("Send."+UTC+".Title", u.getString(player, (byte)2));
			PlayerPost.set("Send."+UTC+".Memo", u.getString(player, (byte)3));
			PlayerPost.set("Send."+UTC+".Item", u.getItemStack(player));
			PlayerPost.set("Send."+UTC+".Value", u.getInt(player, (byte)0));
			TargetPost.saveConfig();
			PlayerPost.saveConfig();
			s.SP(player, Sound.CHEST_CLOSE, 1.0F, 1.8F);
			player.sendMessage(ChatColor.GREEN+"[����] : ������ �߼��Ͽ����ϴ�!");
		}
		else
		{
			s.SP(player, Sound.ORB_PICKUP, 1.0F, 1.8F);
			player.sendMessage(ChatColor.RED+"[����] : �ش� �÷��̾��� �������� ���� á���ϴ�.");
			if(u.getItemStack(player)!=null)
			{
				PlayerPost.set("Recieve."+UTC+".From", "[����]");
				PlayerPost.set("Recieve."+UTC+".Title", "[��� ����]");
				PlayerPost.set("Recieve."+UTC+".Memo", "[������ �������� �� á���ϴ�.]");
				PlayerPost.set("Recieve."+UTC+".Item", u.getItemStack(player));
				PlayerPost.saveConfig();
			}
		}
		u.clearAll(player);
		return;
	}

	public void SendPost_Server(String targetUUID, String Name, String Title, String Memo, ItemStack item)
	{
		YamlController YC = new YamlController(GoldBigDragon_RPG.Main.Main.plugin);
		YamlManager TargetPost =YC.getNewConfig("Post/"+targetUUID+".yml");
		if(TargetPost.contains("Recieve")==false)
		{
			TargetPost.set("Recieve.1", null);
			TargetPost.saveConfig();
		}
		long UTC = new GoldBigDragon_RPG.ServerTick.ServerTickMain().nowUTC +new GoldBigDragon_RPG.Util.Number().RandomNum(0, 1200);
		TargetPost.set("Recieve."+UTC+".From", Name);
		TargetPost.set("Recieve."+UTC+".Title", Title);
		TargetPost.set("Recieve."+UTC+".Memo", Memo);
		TargetPost.set("Recieve."+UTC+".Item", item);
		TargetPost.set("Recieve."+UTC+".Value", 0);
		TargetPost.saveConfig();
		return;
	}


	public String CreatePostBox(int LineNumber, String StructureCode, byte Direction)
	{
		if(LineNumber<=19) //��ü�� �ٸ� 4��
		{
			if(LineNumber<=4)
				return "/summon ArmorStand ~-0.18 ~"+(0.652+(LineNumber*0.34))+" ~-0.28 {CustomName:\""+StructureCode+"\",ShowArms:1,Invisible:1,NoBasePlate:1,NoGravity:1,Equipment:[{id:stained_hardened_clay,Damage:14,Count:1},{},{},{},{}],Pose:{Body:[0f,0f,0f],LeftArm:[0f,0f,0f],RightArm:[346f,44f,270f],LeftLeg:[0f,0f,0f],RightLeg:[0f,0f,0f],Head:[0f,0f,0f]}}";
			else if(LineNumber<=9)
				return "/summon ArmorStand ~-0.18 ~"+(0.652+((LineNumber-5)*0.34))+" ~-0.96 {CustomName:\""+StructureCode+"\",ShowArms:1,Invisible:1,NoBasePlate:1,NoGravity:1,Equipment:[{id:stained_hardened_clay,Damage:14,Count:1},{},{},{},{}],Pose:{Body:[0f,0f,0f],LeftArm:[0f,0f,0f],RightArm:[346f,44f,270f],LeftLeg:[0f,0f,0f],RightLeg:[0f,0f,0f],Head:[0f,0f,0f]}}";
			else if(LineNumber<=14)
				return "/summon ArmorStand ~-0.86 ~"+(0.652+((LineNumber-10)*0.34))+" ~-0.96 {CustomName:\""+StructureCode+"\",ShowArms:1,Invisible:1,NoBasePlate:1,NoGravity:1,Equipment:[{id:stained_hardened_clay,Damage:14,Count:1},{},{},{},{}],Pose:{Body:[0f,0f,0f],LeftArm:[0f,0f,0f],RightArm:[346f,44f,270f],LeftLeg:[0f,0f,0f],RightLeg:[0f,0f,0f],Head:[0f,0f,0f]}}";
			else if(LineNumber<=19)
				return "/summon ArmorStand ~-0.86 ~"+(0.652+((LineNumber-15)*0.34))+" ~-0.28 {CustomName:\""+StructureCode+"\",ShowArms:1,Invisible:1,NoBasePlate:1,NoGravity:1,Equipment:[{id:stained_hardened_clay,Damage:14,Count:1},{},{},{},{}],Pose:{Body:[0f,0f,0f],LeftArm:[0f,0f,0f],RightArm:[346f,44f,270f],LeftLeg:[0f,0f,0f],RightLeg:[0f,0f,0f],Head:[0f,0f,0f]}}";
		}

		switch(Direction)
		{
		case 1://��
			switch(LineNumber)
			{
				case 25:
					return "/summon ArmorStand ~-0.86 ~1.672 ~-0.62 {CustomName:\""+StructureCode+"\",ShowArms:1,Invisible:1,NoBasePlate:1,NoGravity:1,Equipment:[{id:stained_hardened_clay,Damage:14,Count:1},{},{},{},{}],Pose:{Body:[0f,0f,0f],LeftArm:[0f,0f,0f],RightArm:[346f,44f,270f],LeftLeg:[0f,0f,0f],RightLeg:[0f,0f,0f],Head:[0f,0f,0f]}}";
				case 26:
					return "/summon ArmorStand ~-0.52 ~1.672 ~-0.28 {CustomName:\""+StructureCode+"\",ShowArms:1,Invisible:1,NoBasePlate:1,NoGravity:1,Equipment:[{id:stained_hardened_clay,Damage:14,Count:1},{},{},{},{}],Pose:{Body:[0f,0f,0f],LeftArm:[0f,0f,0f],RightArm:[346f,44f,270f],LeftLeg:[0f,0f,0f],RightLeg:[0f,0f,0f],Head:[0f,0f,0f]}}";
				case 27:
					return "/summon ArmorStand ~-0.52 ~1.672 ~-0.96 {CustomName:\""+StructureCode+"\",ShowArms:1,Invisible:1,NoBasePlate:1,NoGravity:1,Equipment:[{id:stained_hardened_clay,Damage:14,Count:1},{},{},{},{}],Pose:{Body:[0f,0f,0f],LeftArm:[0f,0f,0f],RightArm:[346f,44f,270f],LeftLeg:[0f,0f,0f],RightLeg:[0f,0f,0f],Head:[0f,0f,0f]}}";
			}
			break;
		case 3://��
			switch(LineNumber)
			{
				case 25:
					return "/summon ArmorStand ~-0.52 ~1.672 ~-0.96 {CustomName:\""+StructureCode+"\",ShowArms:1,Invisible:1,NoBasePlate:1,NoGravity:1,Equipment:[{id:stained_hardened_clay,Damage:14,Count:1},{},{},{},{}],Pose:{Body:[0f,0f,0f],LeftArm:[0f,0f,0f],RightArm:[346f,44f,270f],LeftLeg:[0f,0f,0f],RightLeg:[0f,0f,0f],Head:[0f,0f,0f]}}";
				case 26:
					return "/summon ArmorStand ~-0.52 ~1.672 ~-0.28 {CustomName:\""+StructureCode+"\",ShowArms:1,Invisible:1,NoBasePlate:1,NoGravity:1,Equipment:[{id:stained_hardened_clay,Damage:14,Count:1},{},{},{},{}],Pose:{Body:[0f,0f,0f],LeftArm:[0f,0f,0f],RightArm:[346f,44f,270f],LeftLeg:[0f,0f,0f],RightLeg:[0f,0f,0f],Head:[0f,0f,0f]}}";
				case 27:
					return "/summon ArmorStand ~-0.18 ~1.672 ~-0.62 {CustomName:\""+StructureCode+"\",ShowArms:1,Invisible:1,NoBasePlate:1,NoGravity:1,Equipment:[{id:stained_hardened_clay,Damage:14,Count:1},{},{},{},{}],Pose:{Body:[0f,0f,0f],LeftArm:[0f,0f,0f],RightArm:[346f,44f,270f],LeftLeg:[0f,0f,0f],RightLeg:[0f,0f,0f],Head:[0f,0f,0f]}}";
			}
			break;
		case 5://��
			switch(LineNumber)
			{
				case 25:
					return "/summon ArmorStand ~-0.86 ~1.672 ~-0.62 {CustomName:\""+StructureCode+"\",ShowArms:1,Invisible:1,NoBasePlate:1,NoGravity:1,Equipment:[{id:stained_hardened_clay,Damage:14,Count:1},{},{},{},{}],Pose:{Body:[0f,0f,0f],LeftArm:[0f,0f,0f],RightArm:[346f,44f,270f],LeftLeg:[0f,0f,0f],RightLeg:[0f,0f,0f],Head:[0f,0f,0f]}}";
				case 26:
					return "/summon ArmorStand ~-0.52 ~1.672 ~-0.96 {CustomName:\""+StructureCode+"\",ShowArms:1,Invisible:1,NoBasePlate:1,NoGravity:1,Equipment:[{id:stained_hardened_clay,Damage:14,Count:1},{},{},{},{}],Pose:{Body:[0f,0f,0f],LeftArm:[0f,0f,0f],RightArm:[346f,44f,270f],LeftLeg:[0f,0f,0f],RightLeg:[0f,0f,0f],Head:[0f,0f,0f]}}";
				case 27:
					return "/summon ArmorStand ~-0.18 ~1.672 ~-0.62 {CustomName:\""+StructureCode+"\",ShowArms:1,Invisible:1,NoBasePlate:1,NoGravity:1,Equipment:[{id:stained_hardened_clay,Damage:14,Count:1},{},{},{},{}],Pose:{Body:[0f,0f,0f],LeftArm:[0f,0f,0f],RightArm:[346f,44f,270f],LeftLeg:[0f,0f,0f],RightLeg:[0f,0f,0f],Head:[0f,0f,0f]}}";
			}
			break;
		case 7://��
			switch(LineNumber)
			{
				case 25:
					return "/summon ArmorStand ~-0.86 ~1.672 ~-0.62 {CustomName:\""+StructureCode+"\",ShowArms:1,Invisible:1,NoBasePlate:1,NoGravity:1,Equipment:[{id:stained_hardened_clay,Damage:14,Count:1},{},{},{},{}],Pose:{Body:[0f,0f,0f],LeftArm:[0f,0f,0f],RightArm:[346f,44f,270f],LeftLeg:[0f,0f,0f],RightLeg:[0f,0f,0f],Head:[0f,0f,0f]}}";
				case 26:
					return "/summon ArmorStand ~-0.52 ~1.672 ~-0.28 {CustomName:\""+StructureCode+"\",ShowArms:1,Invisible:1,NoBasePlate:1,NoGravity:1,Equipment:[{id:stained_hardened_clay,Damage:14,Count:1},{},{},{},{}],Pose:{Body:[0f,0f,0f],LeftArm:[0f,0f,0f],RightArm:[346f,44f,270f],LeftLeg:[0f,0f,0f],RightLeg:[0f,0f,0f],Head:[0f,0f,0f]}}";
				case 27:
					return "/summon ArmorStand ~-0.18 ~1.672 ~-0.62 {CustomName:\""+StructureCode+"\",ShowArms:1,Invisible:1,NoBasePlate:1,NoGravity:1,Equipment:[{id:stained_hardened_clay,Damage:14,Count:1},{},{},{},{}],Pose:{Body:[0f,0f,0f],LeftArm:[0f,0f,0f],RightArm:[346f,44f,270f],LeftLeg:[0f,0f,0f],RightLeg:[0f,0f,0f],Head:[0f,0f,0f]}}";
			}
			break;
		}
		
		switch(LineNumber)
		{
		case 20:
			return "/summon ArmorStand ~-0.18 ~2.012 ~-0.62 {CustomName:\""+StructureCode+"\",ShowArms:1,Invisible:1,NoBasePlate:1,NoGravity:1,Equipment:[{id:stained_hardened_clay,Damage:14,Count:1},{},{},{},{}],Pose:{Body:[0f,0f,0f],LeftArm:[0f,0f,0f],RightArm:[346f,44f,270f],LeftLeg:[0f,0f,0f],RightLeg:[0f,0f,0f],Head:[0f,0f,0f]}}";
		case 21:
			return "/summon ArmorStand ~-0.52 ~2.012 ~-0.62 {CustomName:\""+StructureCode+"\",ShowArms:1,Invisible:1,NoBasePlate:1,NoGravity:1,Equipment:[{id:stained_hardened_clay,Damage:14,Count:1},{},{},{},{}],Pose:{Body:[0f,0f,0f],LeftArm:[0f,0f,0f],RightArm:[346f,44f,270f],LeftLeg:[0f,0f,0f],RightLeg:[0f,0f,0f],Head:[0f,0f,0f]}}";
		case 22:
			return "/summon ArmorStand ~-0.86 ~2.012 ~-0.62 {CustomName:\""+StructureCode+"\",ShowArms:1,Invisible:1,NoBasePlate:1,NoGravity:1,Equipment:[{id:stained_hardened_clay,Damage:14,Count:1},{},{},{},{}],Pose:{Body:[0f,0f,0f],LeftArm:[0f,0f,0f],RightArm:[346f,44f,270f],LeftLeg:[0f,0f,0f],RightLeg:[0f,0f,0f],Head:[0f,0f,0f]}}";
		case 23:
			return "/summon ArmorStand ~-0.52 ~2.012 ~-0.96 {CustomName:\""+StructureCode+"\",ShowArms:1,Invisible:1,NoBasePlate:1,NoGravity:1,Equipment:[{id:stained_hardened_clay,Damage:14,Count:1},{},{},{},{}],Pose:{Body:[0f,0f,0f],LeftArm:[0f,0f,0f],RightArm:[346f,44f,270f],LeftLeg:[0f,0f,0f],RightLeg:[0f,0f,0f],Head:[0f,0f,0f]}}";
		case 24:
			return "/summon ArmorStand ~-0.52 ~2.012 ~-0.28 {CustomName:\""+StructureCode+"\",ShowArms:1,Invisible:1,NoBasePlate:1,NoGravity:1,Equipment:[{id:stained_hardened_clay,Damage:14,Count:1},{},{},{},{}],Pose:{Body:[0f,0f,0f],LeftArm:[0f,0f,0f],RightArm:[346f,44f,270f],LeftLeg:[0f,0f,0f],RightLeg:[0f,0f,0f],Head:[0f,0f,0f]}}";

		case 28:
			return "/summon ArmorStand ~-0.18 ~1.332 ~-0.62 {CustomName:\""+StructureCode+"\",ShowArms:1,Invisible:1,NoBasePlate:1,NoGravity:1,Equipment:[{id:stained_hardened_clay,Damage:14,Count:1},{},{},{},{}],Pose:{Body:[0f,0f,0f],LeftArm:[0f,0f,0f],RightArm:[346f,44f,270f],LeftLeg:[0f,0f,0f],RightLeg:[0f,0f,0f],Head:[0f,0f,0f]}}";
		case 29:
			return "/summon ArmorStand ~-0.86 ~1.332 ~-0.62 {CustomName:\""+StructureCode+"\",ShowArms:1,Invisible:1,NoBasePlate:1,NoGravity:1,Equipment:[{id:stained_hardened_clay,Damage:14,Count:1},{},{},{},{}],Pose:{Body:[0f,0f,0f],LeftArm:[0f,0f,0f],RightArm:[346f,44f,270f],LeftLeg:[0f,0f,0f],RightLeg:[0f,0f,0f],Head:[0f,0f,0f]}}";
		case 30:
			return "/summon ArmorStand ~-0.52 ~1.332 ~-0.96 {CustomName:\""+StructureCode+"\",ShowArms:1,Invisible:1,NoBasePlate:1,NoGravity:1,Equipment:[{id:stained_hardened_clay,Damage:14,Count:1},{},{},{},{}],Pose:{Body:[0f,0f,0f],LeftArm:[0f,0f,0f],RightArm:[346f,44f,270f],LeftLeg:[0f,0f,0f],RightLeg:[0f,0f,0f],Head:[0f,0f,0f]}}";
		case 31:
			return "/summon ArmorStand ~-0.52 ~1.332 ~-0.28 {CustomName:\""+StructureCode+"\",ShowArms:1,Invisible:1,NoBasePlate:1,NoGravity:1,Equipment:[{id:stained_hardened_clay,Damage:14,Count:1},{},{},{},{}],Pose:{Body:[0f,0f,0f],LeftArm:[0f,0f,0f],RightArm:[346f,44f,270f],LeftLeg:[0f,0f,0f],RightLeg:[0f,0f,0f],Head:[0f,0f,0f]}}";

		case 32:
			return "/summon ArmorStand ~-0.18 ~0.992 ~-0.62 {CustomName:\""+StructureCode+"\",ShowArms:1,Invisible:1,NoBasePlate:1,NoGravity:1,Equipment:[{id:stained_hardened_clay,Damage:14,Count:1},{},{},{},{}],Pose:{Body:[0f,0f,0f],LeftArm:[0f,0f,0f],RightArm:[346f,44f,270f],LeftLeg:[0f,0f,0f],RightLeg:[0f,0f,0f],Head:[0f,0f,0f]}}";
		case 33:
			return "/summon ArmorStand ~-0.52 ~0.992 ~-0.62 {CustomName:\""+StructureCode+"\",ShowArms:1,Invisible:1,NoBasePlate:1,NoGravity:1,Equipment:[{id:stained_hardened_clay,Damage:14,Count:1},{},{},{},{}],Pose:{Body:[0f,0f,0f],LeftArm:[0f,0f,0f],RightArm:[346f,44f,270f],LeftLeg:[0f,0f,0f],RightLeg:[0f,0f,0f],Head:[0f,0f,0f]}}";
		case 34:
			return "/summon ArmorStand ~-0.86 ~0.992 ~-0.62 {CustomName:\""+StructureCode+"\",ShowArms:1,Invisible:1,NoBasePlate:1,NoGravity:1,Equipment:[{id:stained_hardened_clay,Damage:14,Count:1},{},{},{},{}],Pose:{Body:[0f,0f,0f],LeftArm:[0f,0f,0f],RightArm:[346f,44f,270f],LeftLeg:[0f,0f,0f],RightLeg:[0f,0f,0f],Head:[0f,0f,0f]}}";
		case 35:
			return "/summon ArmorStand ~-0.52 ~0.992 ~-0.96 {CustomName:\""+StructureCode+"\",ShowArms:1,Invisible:1,NoBasePlate:1,NoGravity:1,Equipment:[{id:stained_hardened_clay,Damage:14,Count:1},{},{},{},{}],Pose:{Body:[0f,0f,0f],LeftArm:[0f,0f,0f],RightArm:[346f,44f,270f],LeftLeg:[0f,0f,0f],RightLeg:[0f,0f,0f],Head:[0f,0f,0f]}}";
		case 36:
			return "/summon ArmorStand ~-0.52 ~0.992 ~-0.28 {CustomName:\""+StructureCode+"\",ShowArms:1,Invisible:1,NoBasePlate:1,NoGravity:1,Equipment:[{id:stained_hardened_clay,Damage:14,Count:1},{},{},{},{}],Pose:{Body:[0f,0f,0f],LeftArm:[0f,0f,0f],RightArm:[346f,44f,270f],LeftLeg:[0f,0f,0f],RightLeg:[0f,0f,0f],Head:[0f,0f,0f]}}";
			
		case 37:
			return "/summon ArmorStand ~-0.28 ~1.332 ~-0.28 {CustomName:\""+StructureCode+"\",CustomNameVisible:1,ShowArms:1,Invisible:1,NoBasePlate:1,NoGravity:1}";

		}
		return "null";
	}
}
