package GoldBigDragon_RPG.Command;

import java.util.Arrays;
import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Sound;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.material.MaterialData;

import GoldBigDragon_RPG.Main.ServerOption;
import GoldBigDragon_RPG.ServerTick.ServerTickMain;
import GoldBigDragon_RPG.Util.YamlController;
import GoldBigDragon_RPG.Util.YamlManager;

public class SystemCommand
{
	public void onCommand(Player player, String[] args, String string)
	{
	  	YamlController YC = new YamlController(GoldBigDragon_RPG.Main.Main.plugin);
		GoldBigDragon_RPG.Effect.Sound s = new GoldBigDragon_RPG.Effect.Sound();
		switch(string)
		{
			case "Ÿ���߰�":
				if(player.isOp() == true)
				{
					if(args.length!=1)
					{
						player.sendMessage(ChatColor.RED + "[SYSTEM] : /Ÿ���߰� [���ο� ������ Ÿ��]");
						s.SP(player, org.bukkit.Sound.ORB_PICKUP, 1.0F, 1.7F);
					}
					else
					{
					  	YamlManager Target = YC.getNewConfig("Item/CustomType.yml");
				  		Target.set("["+args[0]+"]", 0);
				  		Target.saveConfig();
				  		player.sendMessage(ChatColor.GREEN+"[SYSTEM] : ���ο� ������ Ÿ�� �߰� �Ϸ�!  " + ChatColor.WHITE+args[0]);
						s.SP(player, org.bukkit.Sound.ITEM_PICKUP, 1.0F, 1.7F);
					}
				}
				else
				{
					player.sendMessage(ChatColor.RED + "[SYSTEM] : �ش� ��ɾ �����ϱ� ���ؼ��� ������ ������ �ʿ��մϴ�!");
					s.SP(player, org.bukkit.Sound.ORB_PICKUP, 1.0F, 1.7F);
				}
				return;
			case "����":
			{
				if(ServerTickMain.PlayerTaskList.containsKey(player.getName()))
				{
					long UTC = Long.parseLong(ServerTickMain.PlayerTaskList.get(player.getName()));
					GoldBigDragon_RPG.ServerTick.ServerTickScheduleObject STSO = ServerTickMain.Schedule.get(UTC);
					String taskType = STSO.getType();
					
					switch(taskType)
					{
						case "P_EC"://Player Exchange
						{
							if(STSO.getString((byte)1).compareTo(player.getName())==0)
							{
								s.SP(player, Sound.HORSE_ARMOR, 1.0F, 1.7F);
								s.SP(Bukkit.getServer().getPlayer(STSO.getString((byte)1)), Sound.HORSE_ARMOR, 1.0F, 1.7F);
								new GoldBigDragon_RPG.GUI.EquipGUI().ExChangeGUI(Bukkit.getServer().getPlayer(STSO.getString((byte)0)), Bukkit.getServer().getPlayer(STSO.getString((byte)1)),null,false,false);
								new GoldBigDragon_RPG.GUI.EquipGUI().ExChangeGUI(Bukkit.getServer().getPlayer(STSO.getString((byte)1)), Bukkit.getServer().getPlayer(STSO.getString((byte)0)),null,false,false);
								ServerTickMain.PlayerTaskList.remove(ServerTickMain.Schedule.get(UTC).getString((byte)0));
								ServerTickMain.PlayerTaskList.remove(ServerTickMain.Schedule.get(UTC).getString((byte)1));
								ServerTickMain.Schedule.remove(UTC);
							}
						}
						break;
					}
				}
			}
			return;
			case "����":
			{
				if(ServerTickMain.PlayerTaskList.containsKey(player.getName()))
				{
					GoldBigDragon_RPG.ServerTick.ServerTickScheduleObject STSO = ServerTickMain.Schedule.get(Long.parseLong(ServerTickMain.PlayerTaskList.get(player.getName())));
					String taskType = STSO.getType();
					switch(taskType)
					{
						case "P_EC"://Player Exchange
						{
							if(STSO.getString((byte)1).compareTo(player.getName())==0)
							{
								GoldBigDragon_RPG.ServerTick.ServerTask_Player SP = new GoldBigDragon_RPG.ServerTick.ServerTask_Player();
								SP.Cancel(STSO.getTick(), (short) 0);
							}
						}
						break;
					}
				}
			}
			return;
			case"�׽�Ʈ":
			if(player.isOp() == true)
			{
				
			}
			else
			{
				player.sendMessage(ChatColor.RED + "[SYSTEM] : �ش� ��ɾ �����ϱ� ���ؼ��� ������ ������ �ʿ��մϴ�!");
				s.SP(player, org.bukkit.Sound.ORB_PICKUP, 2.0F, 1.7F);
			}
			return;
			case"�׽�Ʈ2":
			if(player.isOp() == true)
			{
			}
			else
			{
				player.sendMessage(ChatColor.RED + "[SYSTEM] : �ش� ��ɾ �����ϱ� ���ؼ��� ������ ������ �ʿ��մϴ�!");
				s.SP(player, org.bukkit.Sound.ORB_PICKUP, 2.0F, 1.7F);
			}
			return;
			case "��":
	  		{
	  			long Money = GoldBigDragon_RPG.Main.ServerOption.PlayerList.get(player.getUniqueId().toString()).getStat_Money();
	  			if(args.length == 0)
	  			{
				 	s.SP(player, org.bukkit.Sound.LAVA_POP, 0.8F, 1.8F);
				 	player.sendMessage(ChatColor.YELLOW + "[���� ���� �ݾ�] " + ChatColor.WHITE+ChatColor.BOLD +"" +Money + " "+ServerOption.Money);
				 	player.sendMessage(ChatColor.GOLD + "/�� ������ [�ݾ�]"+ChatColor.WHITE+" �ش� �ݾ� ��ŭ ���� ���������� �����ϴ�.");
	  				if(player.isOp()==true)
		  				player.sendMessage(ChatColor.AQUA + "/�� �ֱ� [�ݾ�] [�÷��̾�]"+ChatColor.WHITE+" �ش� �ݾ� ��ŭ �÷��̾�� ���� �ݴϴϴ�."+ChatColor.AQUA+""+ChatColor.BOLD+"(������)");
	  			}
	  			else if(args.length == 2&&args[0].compareTo("������")==0)
	  			{
					try
					{
						if(args[1].length() >= 1 && Integer.parseInt(args[1]) >= 1&& Integer.parseInt(args[1]) <= 100000000)
						{
							if(Money >= Integer.parseInt(args[1]))
							{
								for(byte count = 0; count < 36;count++)
								{
									if(player.getInventory().getItem(count)==null)
									{
										int money = Integer.parseInt(args[1]);
										GoldBigDragon_RPG.Main.ServerOption.PlayerList.get(player.getUniqueId().toString()).addStat_MoneyAndEXP(-1 * money, 0, false);
										ItemStack Icon;
										if(money <= 50)
											Icon = new MaterialData(ServerOption.Money1ID, (byte) ServerOption.Money1DATA).toItemStack();
										else if(money <= 100)
											Icon = new MaterialData(ServerOption.Money2ID, (byte) ServerOption.Money2DATA).toItemStack();
										else if(money <= 1000)
											Icon = new MaterialData(ServerOption.Money3ID, (byte) ServerOption.Money3DATA).toItemStack();
										else if(money <= 10000)
											Icon = new MaterialData(ServerOption.Money4ID, (byte) ServerOption.Money4DATA).toItemStack();
										else if(money <= 50000)
											Icon = new MaterialData(ServerOption.Money5ID, (byte) ServerOption.Money5DATA).toItemStack();
										else
											Icon = new MaterialData(ServerOption.Money6ID, (byte) ServerOption.Money6DATA).toItemStack();
										Icon.setAmount(1);
										ItemMeta Icon_Meta = Icon.getItemMeta();
										Icon_Meta.addItemFlags(ItemFlag.HIDE_ATTRIBUTES);
										Icon_Meta.addItemFlags(ItemFlag.HIDE_POTION_EFFECTS);
										Icon_Meta.addItemFlags(ItemFlag.HIDE_UNBREAKABLE);
										Icon_Meta.addItemFlags(ItemFlag.HIDE_PLACED_ON);
										Icon_Meta.addItemFlags(ItemFlag.HIDE_ENCHANTS);
										Icon_Meta.addItemFlags(ItemFlag.HIDE_DESTROYS);
										Icon_Meta.setDisplayName(ServerOption.Money);
										StringBuffer MoneyString = new StringBuffer();
										short Mok = 0;
										if(money==100000000||money==10000000||money==1000000||
											money==100000||money==10000||money==1000||money==100||
											money==10)
										{
											switch(money)
											{
												case 100000000:
													MoneyString.append("1��");
													break;
												case 10000000:
													MoneyString.append("1õ��");
													break;
												case 1000000:
													MoneyString.append("1�鸸");
													break;
												case 100000:
													MoneyString.append("1�ʸ�");
													break;
												case 10000:
													MoneyString.append("1��");
													break;
												case 1000:
													MoneyString.append("1õ");
													break;
												case 100:
													MoneyString.append("1��");
													break;
												case 10:
													MoneyString.append("1��");
													break;
											}
										}
										else
										{
											if(money >= 10000000)
											{
												Mok = (short) (money / 10000000);
												MoneyString.append(Mok+"õ");
												money = money-(Mok*10000000);
											}
											if(money >= 1000000)
											{
												Mok = (short) (money / 1000000);
												MoneyString.append(Mok+"��");
												money = money-(Mok*1000000);
											}
											if(money >= 100000)
											{
												Mok = (short) (money / 100000);
												MoneyString.append(Mok+"��");
												money = money-(Mok*100000);
											}
											if(money >= 10000)
											{
												Mok = (short) (money / 10000);
												MoneyString.append(Mok+"�� ");
												money = money-(Mok*10000);
											}
											else if(Integer.parseInt(args[1]) >= 10000)
											{
												MoneyString.append("�� ");
											}
											if(money >= 1000)
											{
												Mok = (short) (money / 1000);
												MoneyString.append(Mok+"õ");
												money = money-(Mok*1000);
											}
											if(money >= 100)
											{
												Mok = (short) (money / 100);
												MoneyString.append(Mok+"��");
												money = money-(Mok*100);
											}
											if(money >= 10)
											{
												Mok = (short) (money / 10);
												MoneyString.append(Mok+"��");
												money = money-(Mok*10);
											}
											if(money >= 1)
											{
												Mok = (short) (money / 1);
												MoneyString.append(Mok);
											}
										}
										Icon_Meta.setLore(Arrays.asList(ChatColor.YELLOW+"[��]             "+ChatColor.WHITE+"[�Ϲ�]",ChatColor.WHITE+""+ChatColor.BOLD+args[1]+" "+ServerOption.Money,ChatColor.GRAY+"("+MoneyString.toString()+" "+ChatColor.stripColor(ServerOption.Money)+")","",ChatColor.GRAY+"�� Ŭ���� �� ���·�",ChatColor.GRAY+"�Աݵ˴ϴ�."));
										Icon.setItemMeta(Icon_Meta);
										player.getInventory().addItem(Icon);
										s.SP(player, org.bukkit.Sound.LAVA_POP, 2.0F, 1.7F);
										player.sendMessage(ChatColor.GREEN + "[System] : "+ChatColor.WHITE+""+ChatColor.BOLD+args[1]+" "+ServerOption.Money+ChatColor.GREEN+" ��(��) ���½��ϴ�!");
										return;
									}
								}
								s.SP(player, org.bukkit.Sound.ORB_PICKUP, 2.0F, 1.7F);
								player.sendMessage(ChatColor.RED + "[System] : �κ��丮 ������ �����մϴ�!");
							}
							else
							{
								player.sendMessage(ChatColor.RED + "[SYSTEM] : ���� ���� �ݾ��� �ʰ��ϴ� ���Դϴ�!");
								s.SP(player, org.bukkit.Sound.ORB_PICKUP, 2.0F, 1.7F);
							}
						}
						else
						{
							player.sendMessage(ChatColor.RED + "[SYSTEM] : �ּ� "+ChatColor.YELLOW +""+1+ChatColor.RED+", �ִ� "+ChatColor.YELLOW+""+100000000+ChatColor.RED+" ������ ���ڸ� �Է��ϼ���!");
							s.SP(player, org.bukkit.Sound.ORB_PICKUP, 2.0F, 1.7F);
						}
					}
					catch(NumberFormatException e)
					{
						player.sendMessage(ChatColor.RED + "[SYSTEM] : ���� ������ ��(����)�� �Է��ϼ���. ("+ChatColor.YELLOW +""+1+ChatColor.RED+" ~ "+ChatColor.YELLOW+""+100000000+ChatColor.RED+")");
						s.SP(player, org.bukkit.Sound.ORB_PICKUP, 2.0F, 1.7F);
					}
	  			}
	  			else if(args.length == 3&&args[0].compareTo("�ֱ�")==0&&player.isOp())
	  			{
	  				if(Bukkit.getServer().getPlayer(args[2]) != null)
	  				{
	  					Player target = Bukkit.getServer().getPlayer(args[2]);
		  				if(target.isOnline())
		  				{
							try
							{
								if(args[1].length() >= 1 && Integer.parseInt(args[1]) >= 1&& Integer.parseInt(args[1]) <= 100000000)
								{
									for(byte count = 0; count < 36;count++)
									{
										if(target.getInventory().getItem(count)==null)
										{
											int money = Integer.parseInt(args[1]);
											ItemStack Icon;
											if(money <= 50)
												Icon = new MaterialData(ServerOption.Money1ID, (byte) ServerOption.Money1DATA).toItemStack();
											else if(money <= 100)
												Icon = new MaterialData(ServerOption.Money2ID, (byte) ServerOption.Money2DATA).toItemStack();
											else if(money <= 1000)
												Icon = new MaterialData(ServerOption.Money3ID, (byte) ServerOption.Money3DATA).toItemStack();
											else if(money <= 10000)
												Icon = new MaterialData(ServerOption.Money4ID, (byte) ServerOption.Money4DATA).toItemStack();
											else if(money <= 50000)
												Icon = new MaterialData(ServerOption.Money5ID, (byte) ServerOption.Money5DATA).toItemStack();
											else
												Icon = new MaterialData(ServerOption.Money6ID, (byte) ServerOption.Money6DATA).toItemStack();
											Icon.setAmount(1);
											ItemMeta Icon_Meta = Icon.getItemMeta();
											Icon_Meta.addItemFlags(ItemFlag.HIDE_ATTRIBUTES);
											Icon_Meta.addItemFlags(ItemFlag.HIDE_POTION_EFFECTS);
											Icon_Meta.addItemFlags(ItemFlag.HIDE_UNBREAKABLE);
											Icon_Meta.addItemFlags(ItemFlag.HIDE_PLACED_ON);
											Icon_Meta.addItemFlags(ItemFlag.HIDE_ENCHANTS);
											Icon_Meta.addItemFlags(ItemFlag.HIDE_DESTROYS);
											Icon_Meta.setDisplayName(ServerOption.Money);
											StringBuffer MoneyString = new StringBuffer();
											short Mok = 0;
											if(money==100000000||money==10000000||money==1000000||
												money==100000||money==10000||money==1000||money==100||
												money==10)
											{
												switch(money)
												{
													case 100000000:
														MoneyString.append("1��");
														break;
													case 10000000:
														MoneyString.append("1õ��");
														break;
													case 1000000:
														MoneyString.append("1�鸸");
														break;
													case 100000:
														MoneyString.append("1�ʸ�");
														break;
													case 10000:
														MoneyString.append("1��");
														break;
													case 1000:
														MoneyString.append("1õ");
														break;
													case 100:
														MoneyString.append("1��");
														break;
													case 10:
														MoneyString.append("1��");
														break;
												}
											}
											else
											{
												if(money >= 10000000)
												{
													Mok = (short) (money / 10000000);
													MoneyString.append(Mok+"õ");
													money = money-(Mok*10000000);
												}
												if(money >= 1000000)
												{
													Mok = (short) (money / 1000000);
													MoneyString.append(Mok+"��");
													money = money-(Mok*1000000);
												}
												if(money >= 100000)
												{
													Mok = (short) (money / 100000);
													MoneyString.append(Mok+"��");
													money = money-(Mok*100000);
												}
												if(money >= 10000)
												{
													Mok = (short) (money / 10000);
													MoneyString.append(Mok+"�� ");
													money = money-(Mok*10000);
												}
												else if(Integer.parseInt(args[1]) >= 10000)
												{
													MoneyString.append("�� ");
												}
												if(money >= 1000)
												{
													Mok = (short) (money / 1000);
													MoneyString.append(Mok+"õ");
													money = money-(Mok*1000);
												}
												if(money >= 100)
												{
													Mok = (short) (money / 100);
													MoneyString.append(Mok+"��");
													money = money-(Mok*100);
												}
												if(money >= 10)
												{
													Mok = (short) (money / 10);
													MoneyString.append(Mok+"��");
													money = money-(Mok*10);
												}
												if(money >= 1)
												{
													Mok = (short) (money / 1);
													MoneyString.append(Mok);
												}
											}
											Icon_Meta.setLore(Arrays.asList(ChatColor.YELLOW+"[��]             "+ChatColor.WHITE+"[�Ϲ�]",ChatColor.WHITE+""+ChatColor.BOLD+args[1]+" "+ServerOption.Money,ChatColor.GRAY+"("+MoneyString.toString()+" "+ChatColor.stripColor(ServerOption.Money)+")","",ChatColor.GRAY+"�� Ŭ���� �� ���·�",ChatColor.GRAY+"�Աݵ˴ϴ�."));
											Icon.setItemMeta(Icon_Meta);
											target.getInventory().addItem(Icon);
											s.SP(target, org.bukkit.Sound.LAVA_POP, 2.0F, 1.7F);
											target.sendMessage(ChatColor.GREEN + "[System] : �����ڷ� ���� "+ChatColor.WHITE+""+ChatColor.BOLD+args[1]+" "+ServerOption.Money+ChatColor.GREEN+" ��(��) �޾ҽ��ϴ�!");
											s.SP(player, org.bukkit.Sound.LAVA_POP, 2.0F, 1.7F);
											player.sendMessage(ChatColor.GREEN + "[System] : "+target.getName()+"���� "+ChatColor.WHITE+""+ChatColor.BOLD+args[1]+" "+ServerOption.Money+ChatColor.GREEN+" ��(��) �־����ϴ�!");
											return;
										}
									}
									s.SP(player, org.bukkit.Sound.ORB_PICKUP, 2.0F, 1.7F);
									player.sendMessage(ChatColor.RED + "[System] : �κ��丮 ������ �����մϴ�!");
									return;
								}
								else
								{
									player.sendMessage(ChatColor.RED + "[SYSTEM] : �ּ� "+ChatColor.YELLOW +""+1+ChatColor.RED+", �ִ� "+ChatColor.YELLOW+""+100000000+ChatColor.RED+" ������ ���ڸ� �Է��ϼ���!");
									s.SP(player, org.bukkit.Sound.ORB_PICKUP, 2.0F, 1.7F);
								}
							}
							catch(NumberFormatException e)
							{
								player.sendMessage(ChatColor.RED + "[SYSTEM] : ���� ������ ��(����)�� �Է��ϼ���. ("+ChatColor.YELLOW +""+1+ChatColor.RED+" ~ "+ChatColor.YELLOW+""+100000000+ChatColor.RED+")");
								s.SP(player, org.bukkit.Sound.ORB_PICKUP, 2.0F, 1.7F);
							}
		  				}
		  				else
		  				{
							player.sendMessage(ChatColor.RED + "[SYSTEM] : �ش� �÷��̾�� �������� �ƴմϴ�!");
							s.SP(player, org.bukkit.Sound.ORB_PICKUP, 2.0F, 1.7F);
		  				}
	  				}
	  				else
	  				{
						player.sendMessage(ChatColor.RED + "[SYSTEM] : �ش� �÷��̾�� �������� �ƴմϴ�!");
						s.SP(player, org.bukkit.Sound.ORB_PICKUP, 2.0F, 1.7F);
	  				}
	  			}
	  			else
	  			{
	  				s.SP(player, org.bukkit.Sound.ORB_PICKUP, 0.8F, 1.8F);
	  				player.sendMessage(ChatColor.GOLD + "/��"+ChatColor.WHITE+" ���� �ڽ��� ������ �ݾ��� Ȯ���մϴ�.");
	  				player.sendMessage(ChatColor.GOLD + "/�� ������ [�ݾ�]"+ChatColor.WHITE+" �ش� �ݾ� ��ŭ ���� ���������� �����ϴ�.");
	  				if(player.isOp()==true)
		  				player.sendMessage(ChatColor.AQUA + "/�� �ֱ� [�ݾ�] [�÷��̾�]"+ChatColor.WHITE+" �ش� �ݾ� ��ŭ �÷��̾�� ���� �ݴϴϴ�."+ChatColor.AQUA+""+ChatColor.BOLD+"(������)");
	  			}
	  		}
	  		return;
			case "����ö��":
				if(args.length != 1 ||Integer.parseInt(args[0]) > 10000)
				{
					player.sendMessage(ChatColor.RED + "[SYSTEM] : /����ö�� [1~10000]");
					s.SP(player, org.bukkit.Sound.ORB_PICKUP, 2.0F, 1.7F);
					return;
				}
				if(player.isOp() == true)
				{
				    List<Entity> entities = player.getNearbyEntities(Integer.parseInt(args[0]), Integer.parseInt(args[0]), Integer.parseInt(args[0]));
				    short amount = 0;
				    for(short count = 0; count < entities.size(); count++)
				    {
				    	if(entities.get(count).getType() != EntityType.PLAYER)
				    	{
				    		entities.get(count).remove();
				    		amount = (short) (amount+1);
				    	}
				    }
				    player.sendMessage(ChatColor.GREEN + "[SYSTEM] : �ݰ� "+args[0]+"��� �̳��� �ִ� "+amount+"������ ��ƼƼ�� ���� ö���Ͽ����ϴ�!");
				}
				else
				{
					player.sendMessage(ChatColor.RED + "[SYSTEM] : �ش� ��ɾ �����ϱ� ���ؼ��� ������ ������ �ʿ��մϴ�!");
					s.SP(player, org.bukkit.Sound.ORB_PICKUP, 2.0F, 1.7F);
					return;
				}
				return;
			case "��ƼƼ����":
				if(args.length != 1 ||Integer.parseInt(args[0]) > 10000)
				{
					player.sendMessage(ChatColor.RED + "[SYSTEM] : /��ƼƼ���� [1~10000]");
					s.SP(player, org.bukkit.Sound.ORB_PICKUP, 2.0F, 1.7F);
					return;
				}
				if(player.isOp() == true)
				{
				    List<Entity> entities = player.getNearbyEntities(Integer.parseInt(args[0]), Integer.parseInt(args[0]), Integer.parseInt(args[0]));
				    short amount = 0;
				    for(short count = 0; count < entities.size(); count++)
				    {
				    	if(entities.get(count).getType() != EntityType.PLAYER &&entities.get(count).getType() != EntityType.ITEM_FRAME&&entities.get(count).getType()!= EntityType.ARMOR_STAND)
				    	{
				    		entities.get(count).remove();
				    		amount = (short) (amount+1);
				    	}
				    }
				    player.sendMessage(ChatColor.GREEN + "[SYSTEM] : �ݰ� "+args[0]+"��� �̳��� �ִ� "+amount+"������ ��ƼƼ�� �����Ͽ����ϴ�!");
				}
				else
				{
					player.sendMessage(ChatColor.RED + "[SYSTEM] : �ش� ��ɾ �����ϱ� ���ؼ��� ������ ������ �ʿ��մϴ�!");
					s.SP(player, org.bukkit.Sound.ORB_PICKUP, 2.0F, 1.7F);
					return;
				}
				return;
			case "����������":
				if(args.length != 1 ||Integer.parseInt(args[0]) > 10000)
				{
					player.sendMessage(ChatColor.RED + "[SYSTEM] : /���������� [1~10000]");
					s.SP(player, org.bukkit.Sound.ORB_PICKUP, 2.0F, 1.7F);
					return;
				}
				if(player.isOp() == true)
				{
				    List<Entity> entities = player.getNearbyEntities(Integer.parseInt(args[0]), Integer.parseInt(args[0]), Integer.parseInt(args[0]));
				    short amount = 0;
				    for(short count = 0; count < entities.size(); count++)
				    {
				    	if(entities.get(count).getType() == EntityType.DROPPED_ITEM)
				    	{
				    		entities.get(count).remove();
				    		amount++;
				    	}
				    }
				    player.sendMessage(ChatColor.GREEN + "[SYSTEM] : �ݰ� "+args[0]+"��� �̳��� �ִ� "+amount+"���� �������� �����Ͽ����ϴ�!");
				}
				else
				{
					player.sendMessage(ChatColor.RED + "[SYSTEM] : �ش� ��ɾ �����ϱ� ���ؼ��� ������ ������ �ʿ��մϴ�!");
					s.SP(player, org.bukkit.Sound.ORB_PICKUP, 2.0F, 1.7F);
					return;
				}
				return;
		}
		return;
	}
}
