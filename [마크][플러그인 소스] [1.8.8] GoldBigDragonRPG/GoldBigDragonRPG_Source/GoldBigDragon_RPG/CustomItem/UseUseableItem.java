package GoldBigDragon_RPG.CustomItem;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.Sound;
import org.bukkit.entity.Damageable;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import GoldBigDragon_RPG.Main.ServerOption;
import GoldBigDragon_RPG.Util.YamlController;
import GoldBigDragon_RPG.Util.YamlManager;

public class UseUseableItem
{
	public void UseAbleItemUse(Player player, String type)
	{
		GoldBigDragon_RPG.Effect.Sound sound = new GoldBigDragon_RPG.Effect.Sound();
		ItemStack item = player.getItemInHand();
		if(type.compareTo("��ȯ��")==0)
		{
			GoldBigDragon_RPG.Util.ETC ETC = new GoldBigDragon_RPG.Util.ETC();
			if(GoldBigDragon_RPG.Main.ServerOption.PlayerList.get(player.getUniqueId().toString()).getStat_AttackTime() >= ETC.getSec())
			{
				player.sendMessage(ChatColor.RED+"[�̵� �Ұ�] : "+ChatColor.YELLOW+((GoldBigDragon_RPG.Main.ServerOption.PlayerList.get(player.getUniqueId().toString()).getStat_AttackTime()+15000 - ETC.getSec())/1000)+ChatColor.RED+" �� �Ŀ� �̵� �����մϴ�!");
				sound.SP(player, Sound.ORB_PICKUP, 1.0F, 1.8F);
				return;
			}
			String world = "";
			int X = 0;
			short Y = 0;
			int Z = 0;
			for(short counter = 0; counter < item.getItemMeta().getLore().size();counter++)
			{
				String nowlore=ChatColor.stripColor(item.getItemMeta().getLore().get(counter));
				if(nowlore.contains(" : "))
				{
					if(nowlore.contains("����"))
						world = nowlore.split(" : ")[1];
					else if(nowlore.contains("X ��ǥ"))
						X = Integer.parseInt(nowlore.split(" : ")[1]);
					else if(nowlore.contains("Y ��ǥ"))
						Y = Short.parseShort(nowlore.split(" : ")[1]);
					else if(nowlore.contains("Z ��ǥ"))
						Z = Integer.parseInt(nowlore.split(" : ")[1]);
				}
			}
			if(item.getAmount() != 1)
			{
				item.setAmount(item.getAmount()-1);
				player.getInventory().setItem(player.getInventory().getHeldItemSlot(), item);
			}
			else
				player.getInventory().setItem(player.getInventory().getHeldItemSlot(), new ItemStack(0));
			player.teleport(new Location(Bukkit.getWorld(world), X, Y, Z));
		}
		else if(type.compareTo("�ֹ���")==0)
		{
			if(item.getItemMeta().getDisplayName().compareTo("��2��3��4��3��3��l[���� �ʱ�ȭ �ֹ���]")==0)
			{
			  	YamlController YC = new YamlController(GoldBigDragon_RPG.Main.Main.plugin);
				YamlManager Config = YC.getNewConfig("config.yml");
				if(Config.getBoolean("Server.Like_The_Mabinogi_Online_Stat_System")==false)
				{
					if(item.getAmount() != 1)
					{
						item.setAmount(item.getAmount()-1);
						player.getInventory().setItem(player.getInventory().getHeldItemSlot(), item);
					}
					else
						player.getInventory().setItem(player.getInventory().getHeldItemSlot(), new ItemStack(0));
					int TotalStatPoint = Config.getInt("DefaultStat.StatPoint")+ServerOption.PlayerList.get(player.getUniqueId().toString()).getStat_StatPoint();
					TotalStatPoint += ServerOption.PlayerList.get(player.getUniqueId().toString()).getStat_STR() - Config.getInt("DefaultStat.STR");
					TotalStatPoint += ServerOption.PlayerList.get(player.getUniqueId().toString()).getStat_DEX() - Config.getInt("DefaultStat.DEX");
					TotalStatPoint += ServerOption.PlayerList.get(player.getUniqueId().toString()).getStat_INT() - Config.getInt("DefaultStat.INT");
					TotalStatPoint += ServerOption.PlayerList.get(player.getUniqueId().toString()).getStat_WILL() - Config.getInt("DefaultStat.WILL");
					TotalStatPoint += ServerOption.PlayerList.get(player.getUniqueId().toString()).getStat_LUK() - Config.getInt("DefaultStat.LUK");
					ServerOption.PlayerList.get(player.getUniqueId().toString()).setStat_STR(Config.getInt("DefaultStat.STR"));
					ServerOption.PlayerList.get(player.getUniqueId().toString()).setStat_DEX(Config.getInt("DefaultStat.DEX"));
					ServerOption.PlayerList.get(player.getUniqueId().toString()).setStat_INT(Config.getInt("DefaultStat.INT"));
					ServerOption.PlayerList.get(player.getUniqueId().toString()).setStat_WILL(Config.getInt("DefaultStat.WILL"));
					ServerOption.PlayerList.get(player.getUniqueId().toString()).setStat_LUK(Config.getInt("DefaultStat.LUK"));
					ServerOption.PlayerList.get(player.getUniqueId().toString()).setStat_StatPoint(TotalStatPoint);
					sound.SP(player, Sound.ENDERDRAGON_GROWL, 1.2F, 0.5F);
					player.sendMessage(ChatColor.YELLOW+""+ChatColor.BOLD+"[SYSTEM] : ������ �ʱ�ȭ�Ǿ����ϴ�!");
				}
				else
				{
					sound.SP(player, Sound.ORB_PICKUP, 1.0F, 1.8F);
					player.sendMessage(ChatColor.RED+"[System] : ������ ���丮 �ý����� ��츸 ��� �����մϴ�!");
				}
				return;
			}
			
			int StatPoint = 0;
			int SkillPoint = 0;
			int DEF = 0;
			int Protect = 0;
			int MaDEF = 0;
			int MaProtect  = 0;
			int Balance = 0;
			int Critical  = 0;
			int HP  = 0;
			int MP  = 0;
			int STR  = 0;
			int DEX  = 0;
			int INT  = 0;
			int WILL  = 0;
			int LUK  = 0;
			
			for(short counter = 0; counter < item.getItemMeta().getLore().size();counter++)
			{
				String nowlore=ChatColor.stripColor(item.getItemMeta().getLore().get(counter));
				if(nowlore.contains(" : "))
				{
					if(nowlore.contains("����Ʈ"))
					{
						if(nowlore.contains("����"))
							StatPoint = Integer.parseInt(nowlore.split(" : ")[1]);
						if(nowlore.contains("��ų"))
							SkillPoint = Integer.parseInt(nowlore.split(" : ")[1]);
					}
					if(nowlore.contains("���"))
						if(nowlore.contains("����"))
							MaDEF = Integer.parseInt(nowlore.split(" : ")[1]);
						else
							DEF = Integer.parseInt(nowlore.split(" : ")[1]);
					if(nowlore.contains("��ȣ"))
						if(nowlore.contains("����"))
							MaProtect = Integer.parseInt(nowlore.split(" : ")[1]);
						else
							Protect = Integer.parseInt(nowlore.split(" : ")[1]);
					if(nowlore.contains("�뷱��"))
						Balance = Integer.parseInt(nowlore.split(" : ")[1]);
					if(nowlore.contains("ũ��Ƽ��"))
						Critical = Integer.parseInt(nowlore.split(" : ")[1]);
					if(nowlore.contains("�����"))
						HP = Integer.parseInt(nowlore.split(" : ")[1]);
					if(nowlore.contains("����"))
						MP = Integer.parseInt(nowlore.split(" : ")[1]);
					if(nowlore.contains(ServerOption.STR))
						STR = Integer.parseInt(nowlore.split(" : ")[1]);
					if(nowlore.contains(ServerOption.DEX))
						DEX = Integer.parseInt(nowlore.split(" : ")[1]);
					if(nowlore.contains(ServerOption.INT))
						INT = Integer.parseInt(nowlore.split(" : ")[1]);
					if(nowlore.contains(ServerOption.WILL))
						WILL = Integer.parseInt(nowlore.split(" : ")[1]);
					if(nowlore.contains(ServerOption.LUK))
						LUK = Integer.parseInt(nowlore.split(" : ")[1]);
				}
			}
			if(SkillPoint!=0)
				GoldBigDragon_RPG.Main.ServerOption.PlayerList.get(player.getUniqueId().toString()).addStat_SkillPoint(SkillPoint);
			if(StatPoint!=0)
				GoldBigDragon_RPG.Main.ServerOption.PlayerList.get(player.getUniqueId().toString()).addStat_StatPoint(StatPoint);
			if(DEF!=0)
				GoldBigDragon_RPG.Main.ServerOption.PlayerList.get(player.getUniqueId().toString()).addStat_DEF(DEF);
			if(Protect!=0)
			GoldBigDragon_RPG.Main.ServerOption.PlayerList.get(player.getUniqueId().toString()).addStat_Protect(Protect);
			if(MaDEF!=0)
				GoldBigDragon_RPG.Main.ServerOption.PlayerList.get(player.getUniqueId().toString()).addStat_Magic_DEF(MaDEF);
			if(MaProtect!=0)
				GoldBigDragon_RPG.Main.ServerOption.PlayerList.get(player.getUniqueId().toString()).addStat_Magic_Protect(MaProtect);
			if(Balance!=0)
				GoldBigDragon_RPG.Main.ServerOption.PlayerList.get(player.getUniqueId().toString()).addStat_Balance(Balance);
			if(Critical!=0)
				GoldBigDragon_RPG.Main.ServerOption.PlayerList.get(player.getUniqueId().toString()).addStat_Critical(Critical);
			if(HP!=0)
				GoldBigDragon_RPG.Main.ServerOption.PlayerList.get(player.getUniqueId().toString()).addStat_MaxHP(HP);
			if(MP!=0)
				GoldBigDragon_RPG.Main.ServerOption.PlayerList.get(player.getUniqueId().toString()).addStat_MaxMP(MP);
			if(STR!=0)
				GoldBigDragon_RPG.Main.ServerOption.PlayerList.get(player.getUniqueId().toString()).addStat_STR(STR);
			if(DEX!=0)
				GoldBigDragon_RPG.Main.ServerOption.PlayerList.get(player.getUniqueId().toString()).addStat_DEX(DEX);
			if(INT!=0)
				GoldBigDragon_RPG.Main.ServerOption.PlayerList.get(player.getUniqueId().toString()).addStat_INT(INT);
			if(WILL!=0)
				GoldBigDragon_RPG.Main.ServerOption.PlayerList.get(player.getUniqueId().toString()).addStat_WILL(WILL);
			if(LUK!=0)
				GoldBigDragon_RPG.Main.ServerOption.PlayerList.get(player.getUniqueId().toString()).addStat_LUK(LUK);

			if(item.getAmount() != 1)
			{
				item.setAmount(item.getAmount()-1);
				player.getInventory().setItem(player.getInventory().getHeldItemSlot(), item);
			}
			else
				player.getInventory().setItem(player.getInventory().getHeldItemSlot(), new ItemStack(0));
			
			if(SkillPoint>=0&&StatPoint>=0&&DEF>=0&&Protect>=0&&MaDEF>=0&&MaProtect>=0&&Balance>=0&&Critical>=0&&HP>0
			&&MP>=0&&STR>=0&&DEX>=0&&INT>=0&&WILL>=0&&LUK>0)
			{
				sound.SP(player, Sound.LEVEL_UP, 0.8F, 0.5F);
				player.sendMessage(ChatColor.GREEN+""+ChatColor.BOLD+"[      �ɷ�ġ�� ��� �Ͽ����ϴ�!      ]");
			}
			else if(SkillPoint<0&&StatPoint<0&&DEF<0&&Protect<0&&MaDEF<0&&MaProtect<0&&Balance<0&&Critical<0&&HP<0
					&&MP<0&&STR<0&&DEX<0&&INT<0&&WILL<0&&LUK<0)
			{
				sound.SP(player, Sound.ZOMBIE_METAL, 0.8F, 0.5F);
				player.sendMessage(ChatColor.RED+""+ChatColor.BOLD+"[      �ɷ�ġ�� ���� �Ͽ����ϴ�!      ]");
			}
			else
			{
				sound.SP(player, Sound.ORB_PICKUP, 0.8F, 1.5F);
				player.sendMessage(ChatColor.YELLOW+""+ChatColor.BOLD+"[      �ɷ�ġ�� ��ȭ�� ������ϴ�!      ]");
			}
		}
		else if(type.compareTo("��ų��")==0)
		{
		  	YamlController YC = new YamlController(GoldBigDragon_RPG.Main.Main.plugin);
			YamlManager Config = YC.getNewConfig("config.yml");
			if(Config.getBoolean("Server.Like_The_Mabinogi_Online_Stat_System")==true)
			{
				String Skillname = null;
				for(short counter = 0; counter < item.getItemMeta().getLore().size();counter++)
				{
					String nowlore=ChatColor.stripColor(item.getItemMeta().getLore().get(counter));
					if(nowlore.contains("[��"))
					{
						if(nowlore.contains("Ŭ����"))
							if(nowlore.contains("�Ʒ�"))
								if(nowlore.contains("��ų"))
									if(nowlore.contains("ȹ��]"))
									{
										nowlore=ChatColor.stripColor(item.getItemMeta().getLore().get(counter+1));
										Skillname = nowlore.replace(" + ", "");
										break;
									}
					}
				}
				if(Skillname == null)
					return;
				YamlManager AllSkills = YC.getNewConfig("Skill/SkillList.yml");
				if(AllSkills.contains(Skillname))
				{
					AllSkills = YC.getNewConfig("Skill/JobList.yml");
					if(AllSkills.contains("Mabinogi.Added."+Skillname)==true)
					{
						YamlManager PlayerSkillList = YC.getNewConfig("Skill/PlayerData/"+player.getUniqueId().toString()+".yml");
						if(PlayerSkillList.contains("Mabinogi."+AllSkills.getString("Mabinogi.Added."+Skillname)+"."+Skillname) == false)
						{
							PlayerSkillList.set("Mabinogi."+AllSkills.getString("Mabinogi.Added."+Skillname)+"."+Skillname, 1);
							PlayerSkillList.saveConfig();
							if(item.getAmount() != 1)
							{
								item.setAmount(item.getAmount()-1);
								player.getInventory().setItem(player.getInventory().getHeldItemSlot(), item);
							}
							else
								player.getInventory().setItem(player.getInventory().getHeldItemSlot(), new ItemStack(0));
							sound.SP(player, Sound.LEVEL_UP, 1.0F, 1.8F);
							player.sendMessage(ChatColor.LIGHT_PURPLE+""+ChatColor.BOLD+"[���ο� ��ų�� ȹ�� �Ͽ����ϴ�!] "+ChatColor.YELLOW+""+ChatColor.BOLD+""+ChatColor.UNDERLINE+Skillname);
							return;
						}
						else
						{
							sound.SP(player, Sound.ORB_PICKUP, 1.0F, 1.8F);
							player.sendMessage(ChatColor.RED+""+ChatColor.BOLD+"[         ����� �̹� �ش� ��ų�� �˰� �ֽ��ϴ�!         ]");
							return;
						}
					}
					else
					{
						sound.SP(player, Sound.ORB_PICKUP, 1.0F, 1.8F);
						player.sendMessage(ChatColor.RED+""+ChatColor.BOLD+"[�ش� ��ų�� ��� ī�װ����� �������� �ʽ��ϴ�! �����ڿ��� �����ϼ���!]");
						return;
					}
				}
				else
				{
					sound.SP(player, Sound.ORB_PICKUP, 1.0F, 1.8F);
					player.sendMessage(ChatColor.RED+""+ChatColor.BOLD+"[������ �ش� ��ų�� �������� �ʽ��ϴ�! �����ڿ��� �����ϼ���!]");
					return;
				}
			}
			else
			{
				sound.SP(player, Sound.ORB_PICKUP, 1.0F, 1.8F);
				player.sendMessage(ChatColor.RED+""+ChatColor.BOLD+"[   ���� �ý����� '������'�� ��츸 ��� �����մϴ�!   ]");
				return;
			}
		}
		else if(type.compareTo("�Һ�")==0)
		{
			int Health = 0;
			int Mana = 0;
			int Food = 0;
			for(short counter = 0; counter < item.getItemMeta().getLore().size();counter++)
			{
				String nowlore=ChatColor.stripColor(item.getItemMeta().getLore().get(counter));
				if(nowlore.contains(" : "))
				{
					if(nowlore.contains("�����"))
						Health = Integer.parseInt(nowlore.split(" : ")[1]);
					else if(nowlore.contains("����"))
						Mana = Integer.parseInt(nowlore.split(" : ")[1]);
					else if(nowlore.contains("������"))
						Food = Integer.parseInt(nowlore.split(" : ")[1]);
				}
			}
			if(item.getAmount() != 1)
			{
				item.setAmount(item.getAmount()-1);
				player.getInventory().setItem(player.getInventory().getHeldItemSlot(), item);
			}
			else
				player.getInventory().setItem(player.getInventory().getHeldItemSlot(), new ItemStack(0));
			
			if(Health > 0)
			{
				sound.SL(player.getLocation(), Sound.DRINK, 2.0F, 0.8F);
				Damageable Dp = player;
				if(Dp.getMaxHealth() < Dp.getHealth()+Health)
					Dp.setHealth(Dp.getMaxHealth());
				else
					Dp.setHealth(Dp.getHealth() + Health);
			}
			if(Mana >0)
			{
				if(GoldBigDragon_RPG.Main.ServerOption.MagicSpellsCatched == true)
				{
					OtherPlugins.SpellMain MG = new OtherPlugins.SpellMain();
					MG.DrinkManaPotion(player, Mana);
					sound.SL(player.getLocation(), Sound.WATER, 2.0F, 1.9F);
				}
			}
			if(Food > 0)
			{
			sound.SL(player.getLocation(), Sound.EAT, 2.0F, 1.2F);
				if(player.getFoodLevel()+Food > 20)
					player.setFoodLevel(20);
				player.setFoodLevel(player.getFoodLevel()+Food);
			}
		}
		else if(type.compareTo("��")==0)
		{
			int money=Integer.parseInt(ChatColor.stripColor(item.getItemMeta().getLore().get(1).split(" ")[0]));
			if(GoldBigDragon_RPG.Main.ServerOption.PlayerList.get(player.getUniqueId().toString()).getStat_Money() + money <= 2000000000)
			{
				GoldBigDragon_RPG.Main.ServerOption.PlayerList.get(player.getUniqueId().toString()).addStat_MoneyAndEXP(money, 0, false);
				if(item.getAmount() != 1)
				{
					item.setAmount(item.getAmount()-1);
					player.getInventory().setItem(player.getInventory().getHeldItemSlot(), item);
				}
				else
					player.getInventory().setItem(player.getInventory().getHeldItemSlot(), new ItemStack(0));
				sound.SP(player, Sound.LAVA_POP, 0.8F, 1.8F);
				player.sendMessage(ChatColor.GREEN+"[SYSTEM] : "+ChatColor.WHITE+""+ChatColor.BOLD+money+" "+ServerOption.Money+ChatColor.GREEN+" �Ա� �Ϸ�!");
				player.sendMessage(ChatColor.GRAY+"(���� "+GoldBigDragon_RPG.Main.ServerOption.PlayerList.get(player.getUniqueId().toString()).getStat_Money()+ChatColor.stripColor(ServerOption.Money)+" ������)");
			}
			else
			{
				sound.SP(player, Sound.ORB_PICKUP, 1.0F, 1.8F);
				player.sendMessage(ChatColor.RED+"[System] : "+ServerOption.Money+ChatColor.RED+" ��(��) 2000000000(20��)�̻� ���� �� �����ϴ�!");
			}
		}
		return;
	}
}
