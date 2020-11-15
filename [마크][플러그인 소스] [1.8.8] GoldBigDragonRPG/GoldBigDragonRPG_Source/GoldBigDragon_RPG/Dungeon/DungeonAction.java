package GoldBigDragon_RPG.Dungeon;

import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.event.player.PlayerChatEvent;

import GoldBigDragon_RPG.Main.Object_UserData;
import GoldBigDragon_RPG.Util.YamlController;
import GoldBigDragon_RPG.Util.YamlManager;
import net.md_5.bungee.api.ChatColor;

public class DungeonAction
{
	public void PlayerChatrouter(PlayerChatEvent event)
	{
		Player player = event.getPlayer();
		Object_UserData u = new Object_UserData();
		if(u.getType(player).compareTo("DungeonMain")==0)
			DungeonMainChatting(event);
		else if(u.getType(player).compareTo("EnterCard")==0)
			EnterCardChatting(event);
		else if(u.getType(player).compareTo("Altar")==0)
			AltarChatting(event);
	}
	
	
	private void DungeonMainChatting(PlayerChatEvent event)
	{
		GoldBigDragon_RPG.Effect.Sound s = new GoldBigDragon_RPG.Effect.Sound();
		Player player = event.getPlayer();
		Object_UserData u = new Object_UserData();
		String Message = ChatColor.stripColor(event.getMessage());
	  	YamlController YC = new YamlController(GoldBigDragon_RPG.Main.Main.plugin);
		if(u.getString(player,(byte)0).compareTo("ND")==0)
		{
			YamlManager DungeonList = YC.getNewConfig("Dungeon/DungeonList.yml");
			if(Message.length()>=11)
			{
				s.SP(player, Sound.ORB_PICKUP, 1.0F, 1.8F);
				player.sendMessage(ChatColor.RED+"[����] : �̸��� �ʹ� ��ϴ�! (10�� �̳�)");
				return;
			}
			if(DungeonList.contains(Message))
			{
				s.SP(player, Sound.ORB_PICKUP, 1.0F, 1.8F);
				player.sendMessage(ChatColor.RED+"[����] : �ش� �̸��� ������ �̹� �����մϴ�!");
				return;
			}
			else
			{
				DungeonList.set(Message, GoldBigDragon_RPG.ServerTick.ServerTickMain.nowUTC);
				DungeonList.saveConfig();
				DungeonList = YC.getNewConfig("Dungeon/Dungeon/"+Message+"/Option.yml");
				DungeonList.set("Type.ID", 1);
				DungeonList.set("Type.DATA", 0);
				DungeonList.set("Type.Name", GoldBigDragon_RPG.Main.ServerOption.DungeonTheme.get(0));
				DungeonList.set("Size", 5);
				DungeonList.set("Maze_Level", 1);
				DungeonList.set("District.Level", 0);
				DungeonList.set("District.RealLevel", 0);
				DungeonList.set("Reward.Money", 1000);
				DungeonList.set("Reward.EXP", 10000);
				DungeonList.saveConfig();
				DungeonList = YC.getNewConfig("Dungeon/Dungeon/"+Message+"/Monster.yml");
				for(int count = 0; count < 8; count ++)
				{
					DungeonList.set("Boss."+count+".1", null);
					DungeonList.set("SubBoss."+count+".1", null);
					DungeonList.set("High."+count+".1", null);
					DungeonList.set("Middle."+count+".1", null);
					DungeonList.set("Normal."+count+".1", null);
				}
				DungeonList.saveConfig();
				DungeonList = YC.getNewConfig("Dungeon/Dungeon/"+Message+"/Reward.yml");
				for(int count = 0; count < 8; count++)
				{
					DungeonList.set("100."+count+".1", null);
					DungeonList.set("90."+count+".1", null);
					DungeonList.set("50."+count+".1", null);
					DungeonList.set("10."+count+".1", null);
					DungeonList.set("1."+count+".1", null);
					DungeonList.set("0."+count+".1", null);
				}
				DungeonList.saveConfig();
				s.SP(player, Sound.ITEM_PICKUP, 1.0F, 1.0F);
				player.sendMessage(ChatColor.GREEN+"[����] : ���� �߰� �Ϸ�!");
			}
			u.clearAll(player);
			new GoldBigDragon_RPG.Dungeon.DungeonGUI().DungeonListMainGUI(player, 0, 52);
			return;
		}
		else if(u.getString(player,(byte)0).compareTo("DS")==0)
		{
			if(isIntMinMax(event.getMessage(), player, 5, 50))
			{
				YamlManager DungeonList = YC.getNewConfig("Dungeon/Dungeon/"+u.getString(player, (byte)1)+"/Option.yml");
				DungeonList.set("Size", Integer.parseInt(event.getMessage()));
				DungeonList.saveConfig();
				s.SP(player, Sound.ITEM_PICKUP, 1.0F, 1.0F);
				new GoldBigDragon_RPG.Dungeon.DungeonGUI().DungeonSetUpGUI(player, u.getString(player, (byte)1));
				u.clearAll(player);
			}
		}
		else if(u.getString(player,(byte)0).compareTo("DML")==0)
		{
			if(isIntMinMax(event.getMessage(), player, 0, 10))
			{
				YamlManager DungeonList = YC.getNewConfig("Dungeon/Dungeon/"+u.getString(player, (byte)1)+"/Option.yml");
				DungeonList.set("Maze_Level", Integer.parseInt(event.getMessage()));
				DungeonList.saveConfig();
				s.SP(player, Sound.ITEM_PICKUP, 1.0F, 1.0F);
				new GoldBigDragon_RPG.Dungeon.DungeonGUI().DungeonSetUpGUI(player, u.getString(player, (byte)1));
				u.clearAll(player);
			}
		}
		else if(u.getString(player,(byte)0).compareTo("DDL")==0)
		{
			if(isIntMinMax(event.getMessage(), player, 0, 2000000))
			{
				YamlManager DungeonList = YC.getNewConfig("Dungeon/Dungeon/"+u.getString(player, (byte)1)+"/Option.yml");
				DungeonList.set("District.Level", Integer.parseInt(event.getMessage()));
				DungeonList.saveConfig();
				u.setString(player, (byte)0, "DDRL");//DungeonDistrictRealLevel
				s.SP(player, Sound.ITEM_PICKUP, 1.0F, 1.0F);
				player.sendMessage(ChatColor.GREEN+"[����] : ���� ���� ���� ���� ������ �Է� �� �ּ���!");
			}
		}
		else if(u.getString(player,(byte)0).compareTo("DDRL")==0)
		{
			if(isIntMinMax(event.getMessage(), player, 0, 2000000))
			{
				YamlManager DungeonList = YC.getNewConfig("Dungeon/Dungeon/"+u.getString(player, (byte)1)+"/Option.yml");
				DungeonList.set("District.RealLevel", Integer.parseInt(event.getMessage()));
				DungeonList.saveConfig();
				s.SP(player, Sound.ITEM_PICKUP, 1.0F, 1.0F);
				new GoldBigDragon_RPG.Dungeon.DungeonGUI().DungeonSetUpGUI(player, u.getString(player, (byte)1));
				u.clearAll(player);
			}
		}
		else if(u.getString(player,(byte)0).compareTo("DRM")==0)
		{
			if(isIntMinMax(event.getMessage(), player, 0, 20000000))
			{
				YamlManager DungeonList = YC.getNewConfig("Dungeon/Dungeon/"+u.getString(player, (byte)1)+"/Option.yml");
				DungeonList.set("Reward.Money", Integer.parseInt(event.getMessage()));
				DungeonList.saveConfig();
				u.setString(player, (byte)0, "DRE");//DungeonRewardEXP
				s.SP(player, Sound.ITEM_PICKUP, 1.0F, 1.0F);
				player.sendMessage(ChatColor.GREEN+"[����] : ���� Ŭ���� ���� ����ġ�� �Է� �� �ּ���!");
			}
		}
		else if(u.getString(player,(byte)0).compareTo("DRE")==0)
		{
			if(isIntMinMax(event.getMessage(), player, 0, 100000000))
			{
				YamlManager DungeonList = YC.getNewConfig("Dungeon/Dungeon/"+u.getString(player, (byte)1)+"/Option.yml");
				DungeonList.set("Reward.EXP", Integer.parseInt(event.getMessage()));
				DungeonList.saveConfig();
				s.SP(player, Sound.ITEM_PICKUP, 1.0F, 1.0F);
				new GoldBigDragon_RPG.Dungeon.DungeonGUI().DungeonSetUpGUI(player, u.getString(player, (byte)1));
				u.clearAll(player);
			}
		}
	}
	
	private void EnterCardChatting(PlayerChatEvent event)
	{
		GoldBigDragon_RPG.Effect.Sound s = new GoldBigDragon_RPG.Effect.Sound();
		Player player = event.getPlayer();
		Object_UserData u = new Object_UserData();
		String Message = ChatColor.stripColor(event.getMessage());
	  	YamlController YC = new YamlController(GoldBigDragon_RPG.Main.Main.plugin);
		if(u.getString(player,(byte)0).compareTo("NEC")==0)
		{
			YamlManager EnterCardList = YC.getNewConfig("Dungeon/EnterCardList.yml");
			if(Message.length()>=16)
			{
				s.SP(player, Sound.ORB_PICKUP, 1.0F, 1.8F);
				player.sendMessage(ChatColor.RED+"[����] : �̸��� �ʹ� ��ϴ�! (15�� �̳�)");
				return;
			}
			if(EnterCardList.contains(Message))
			{
				s.SP(player, Sound.ORB_PICKUP, 1.0F, 1.8F);
				player.sendMessage(ChatColor.RED+"[����] : �ش� �̸��� �������� �̹� �����մϴ�!");
				return;
			}
			else
			{
				EnterCardList.set(Message+".ID", 358);
				EnterCardList.set(Message+".DATA", 0);
				EnterCardList.set(Message+".Dungeon.1", null);
				EnterCardList.set(Message+".Capacity", 8);
				EnterCardList.set(Message+".Hour", -1);
				EnterCardList.set(Message+".Min", 0);
				EnterCardList.set(Message+".Sec", 0);
				EnterCardList.saveConfig();
				s.SP(player, Sound.ITEM_PICKUP, 1.0F, 1.0F);
				player.sendMessage(ChatColor.GREEN+"[����] : ������ �߰� �Ϸ�!");
			}
			u.clearAll(player);
			new GoldBigDragon_RPG.Dungeon.DungeonGUI().DungeonListMainGUI(player, 0, 358);
			return;
		}
		else if(u.getString(player,(byte)0).compareTo("ECID")==0)
		{
			if(isIntMinMax(event.getMessage(), player, 1, 2267))
			{
				YamlManager EnterCardConfig = YC.getNewConfig("Dungeon/EnterCardList.yml");
				EnterCardConfig.set(u.getString(player, (byte)1)+".ID", Integer.parseInt(event.getMessage()));
				EnterCardConfig.saveConfig();
				s.SP(player, Sound.ITEM_PICKUP, 1.0F, 1.0F);
				u.setString(player, (byte)0, "ECDATA");
				player.sendMessage(ChatColor.GREEN+"[����] : ������ ������ Ÿ�� DATA�� �Է� �� �ּ���.");
			}
		}
		else if(u.getString(player,(byte)0).compareTo("ECDATA")==0)
		{
			if(isIntMinMax(event.getMessage(), player, 0, 20))
			{
				YamlManager EnterCardConfig = YC.getNewConfig("Dungeon/EnterCardList.yml");
				EnterCardConfig.set(u.getString(player, (byte)1)+".DATA", Integer.parseInt(event.getMessage()));
				EnterCardConfig.saveConfig();
				s.SP(player, Sound.ITEM_PICKUP, 1.0F, 1.0F);
				new GoldBigDragon_RPG.Dungeon.DungeonGUI().EnterCardSetUpGUI(player, u.getString(player, (byte)1));
				u.clearAll(player);
			}
		}
		else if(u.getString(player,(byte)0).compareTo("ECC")==0)
		{
			if(isIntMinMax(event.getMessage(), player, 1, 32))
			{
				YamlManager EnterCardConfig = YC.getNewConfig("Dungeon/EnterCardList.yml");
				EnterCardConfig.set(u.getString(player, (byte)1)+".Capacity", Integer.parseInt(event.getMessage()));
				EnterCardConfig.saveConfig();
				s.SP(player, Sound.ITEM_PICKUP, 1.0F, 1.0F);
				new GoldBigDragon_RPG.Dungeon.DungeonGUI().EnterCardSetUpGUI(player, u.getString(player, (byte)1));
				u.clearAll(player);
			}
		}
		else if(u.getString(player,(byte)0).compareTo("ECUH")==0)
		{
			if(isIntMinMax(event.getMessage(), player, -1, 24))
			{
				YamlManager EnterCardConfig = YC.getNewConfig("Dungeon/EnterCardList.yml");
				if(Integer.parseInt(event.getMessage())==-1)
				{
					EnterCardConfig.set(u.getString(player, (byte)1)+".Hour", -1);
					EnterCardConfig.saveConfig();
					s.SP(player, Sound.ITEM_PICKUP, 1.0F, 1.0F);
					new GoldBigDragon_RPG.Dungeon.DungeonGUI().EnterCardSetUpGUI(player, u.getString(player, (byte)1));
					u.clearAll(player);
				}
				else
				{
					EnterCardConfig.set(u.getString(player, (byte)1)+".Hour", Integer.parseInt(event.getMessage()));
					EnterCardConfig.saveConfig();
					u.setString(player, (byte)0, "ECUM");//EnterCardUseableMinute 
					s.SP(player, Sound.ITEM_PICKUP, 1.0F, 1.0F);
					player.sendMessage(ChatColor.GREEN+"[������] : ��ȿ ���� �Է� �� �ּ���. (�ִ� 59��)");
				}
			}
		}
		else if(u.getString(player,(byte)0).compareTo("ECUM")==0)
		{
			if(isIntMinMax(event.getMessage(), player, 0, 59))
			{
				YamlManager EnterCardConfig = YC.getNewConfig("Dungeon/EnterCardList.yml");
				EnterCardConfig.set(u.getString(player, (byte)1)+".Min",Integer.parseInt(event.getMessage()));
				EnterCardConfig.saveConfig();
				u.setString(player, (byte)0, "ECUS");//EnterCardUseableSecond
				s.SP(player, Sound.ITEM_PICKUP, 1.0F, 1.0F);
				player.sendMessage(ChatColor.GREEN+"[������] : ��ȿ �ʸ� �Է� �� �ּ���. (�ִ� 59��)");
			}
		}
		else if(u.getString(player,(byte)0).compareTo("ECUS")==0)
		{
			if(isIntMinMax(event.getMessage(), player, 0, 59))
			{
				YamlManager EnterCardConfig = YC.getNewConfig("Dungeon/EnterCardList.yml");
				EnterCardConfig.set(u.getString(player, (byte)1)+".Sec",Integer.parseInt(event.getMessage()));
				EnterCardConfig.saveConfig();
				s.SP(player, Sound.ITEM_PICKUP, 1.0F, 1.0F);
				new GoldBigDragon_RPG.Dungeon.DungeonGUI().EnterCardSetUpGUI(player, u.getString(player, (byte)1));
				u.clearAll(player);
			}
		}
	}
	
	private void AltarChatting(PlayerChatEvent event)
	{
		GoldBigDragon_RPG.Effect.Sound s = new GoldBigDragon_RPG.Effect.Sound();
		Player player = event.getPlayer();
		Object_UserData u = new Object_UserData();
		String Message = ChatColor.stripColor(event.getMessage());
	  	YamlController YC = new YamlController(GoldBigDragon_RPG.Main.Main.plugin);
		if(u.getString(player,(byte)0).compareTo("NA_Q")==0)
		{
			if(askOX(Message, player)==1)
			{
				s.SP(player, Sound.ITEM_PICKUP, 1.0F, 1.2F);
				u.clearAll(player);
				new GoldBigDragon_RPG.Dungeon.DungeonGUI().AltarShapeListGUI(player);
			}
			else if(Message.compareTo("�ƴϿ�")==0||Message.compareTo("x")==0
				||Message.compareTo("X")==0)
			{
				s.SP(player, Sound.ITEM_PICKUP, 1.0F, 1.0F);
				player.sendMessage(ChatColor.GREEN+"[����] : ���� ��ġ�� ��ҵǾ����ϴ�.");
				u.clearAll(player);
				new GoldBigDragon_RPG.Dungeon.DungeonGUI().DungeonListMainGUI(player, 0, 120);
			}
			return;
		}
		else if(u.getString(player,(byte)0).compareTo("EAN")==0)
		{
			YamlManager AltarConfig = YC.getNewConfig("Dungeon/AltarList.yml");
			String AltarName = u.getString(player, (byte)1).substring(2, u.getString(player, (byte)1).length());
			AltarConfig.set(AltarName+".Name", event.getMessage());
			AltarConfig.saveConfig();
			s.SP(player, Sound.ITEM_PICKUP, 1.0F, 1.0F);
			new GoldBigDragon_RPG.Dungeon.DungeonGUI().AltarSettingGUI(player, AltarName);
			u.clearAll(player);
		}
	}
	
	
	
	
	
	private byte askOX(String message,Player player)
	{
		GoldBigDragon_RPG.Effect.Sound sound = new GoldBigDragon_RPG.Effect.Sound();
		if(message.split(" ").length <= 1)
		{
			if(message.compareTo("x")==0||message.compareTo("X")==0||message.compareTo("�ƴϿ�")==0)
				return 0;
			else if(message.compareTo("o")==0||message.compareTo("O")==0||message.compareTo("��")==0)
				return 1;
			else
			{
				player.sendMessage(ChatColor.RED + "[SYSTEM] : [��/O] Ȥ�� [�ƴϿ�/X]�� �Է� �� �ּ���!");
				sound.SP(player, org.bukkit.Sound.ORB_PICKUP, 2.0F, 1.7F);
			}
			
		}
		else
		{
			player.sendMessage(ChatColor.RED + "[SYSTEM] : [��/O] Ȥ�� [�ƴϿ�/X]�� �Է� �� �ּ���!");
			sound.SP(player, org.bukkit.Sound.ORB_PICKUP, 2.0F, 1.7F);
		}
		return -1;
	}
	
	private boolean isIntMinMax(String message,Player player, int Min, int Max)
	{
		GoldBigDragon_RPG.Effect.Sound sound = new GoldBigDragon_RPG.Effect.Sound();
		try
		{
			if(message.split(" ").length <= 1 && Integer.parseInt(message) >= Min&& Integer.parseInt(message) <= Max)
				return true;
			else
			{
				player.sendMessage(ChatColor.RED + "[SYSTEM] : �ּ� "+ChatColor.YELLOW +""+Min+ChatColor.RED+", �ִ� "+ChatColor.YELLOW+""+Max+ChatColor.RED+" ������ ���ڸ� �Է��ϼ���!");
				sound.SP(player, org.bukkit.Sound.ORB_PICKUP, 2.0F, 1.7F);
			}
		}
		catch(NumberFormatException e)
		{
			player.sendMessage(ChatColor.RED + "[SYSTEM] : ���� ������ ��(����)�� �Է��ϼ���. ("+ChatColor.YELLOW +""+Min+ChatColor.RED+" ~ "+ChatColor.YELLOW+""+Max+ChatColor.RED+")");
				sound.SP(player, org.bukkit.Sound.ORB_PICKUP, 2.0F, 1.7F);
		}
		return false;
	}
}
