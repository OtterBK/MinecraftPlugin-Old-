package GoldBigDragon_RPG.Event;

import java.util.Calendar;
import java.util.Collection;
import java.util.Set;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.GameMode;
import org.bukkit.Sound;
import org.bukkit.WorldCreator;
import org.bukkit.WorldType;
import org.bukkit.entity.Player;
import org.bukkit.event.player.PlayerChatEvent;
import org.bukkit.event.player.PlayerMoveEvent;

import GoldBigDragon_RPG.Main.Object_UserData;
import GoldBigDragon_RPG.Util.YamlController;
import GoldBigDragon_RPG.Util.YamlManager;

public class PlayerAction
{
	public void PlayerMove(PlayerMoveEvent event)
	{
		Player player = event.getPlayer();
		if(new GoldBigDragon_RPG.Effect.Corpse().DeathCapture(player,false))
			return;

		if(player.getLocation().getWorld().getName().compareTo("Dungeon")!=0)
		{
			if(GoldBigDragon_RPG.Main.ServerOption.PlayerList.get(event.getPlayer().getUniqueId().toString()).getDungeon_Enter() != null)
			{
				if(Bukkit.getPluginManager().isPluginEnabled("NoteBlockAPI"))
					new OtherPlugins.NoteBlockAPIMain().Stop(player);
				GoldBigDragon_RPG.Main.ServerOption.PlayerList.get(event.getPlayer().getUniqueId().toString()).setDungeon_Enter(null);
				GoldBigDragon_RPG.Main.ServerOption.PlayerList.get(event.getPlayer().getUniqueId().toString()).setDungeon_UTC(-1);
			}
			if(new GoldBigDragon_RPG.ETC.Area().getAreaName(event.getPlayer()) != null)
			{
				if(GoldBigDragon_RPG.Main.ServerOption.PlayerList.get(event.getPlayer().getUniqueId().toString()).getETC_CurrentArea()==null)
					GoldBigDragon_RPG.Main.ServerOption.PlayerList.get(event.getPlayer().getUniqueId().toString()).setETC_CurrentArea("null");
				GoldBigDragon_RPG.ETC.Area A = new GoldBigDragon_RPG.ETC.Area();
				String Area = A.getAreaName(event.getPlayer())[0];
				if(GoldBigDragon_RPG.Main.ServerOption.PlayerList.get(event.getPlayer().getUniqueId().toString()).getETC_CurrentArea().compareTo(Area) != 0)
				{
					GoldBigDragon_RPG.Main.ServerOption.PlayerList.get(event.getPlayer().getUniqueId().toString()).setETC_CurrentArea(Area);
					GoldBigDragon_RPG.Main.ServerOption.PlayerCurrentArea.put(player, Area);
					A.AreaMonsterSpawnAdd(Area, "-1");
					if(Bukkit.getPluginManager().isPluginEnabled("NoteBlockAPI") == true)
						new OtherPlugins.NoteBlockAPIMain().Stop(player);
					GoldBigDragon_RPG.Main.ServerOption.PlayerList.get(event.getPlayer().getUniqueId().toString()).setETC_CurrentArea(Area);
					if(A.getAreaOption(Area, (char) 2) == true)
						GoldBigDragon_RPG.Main.ServerOption.PlayerList.get(event.getPlayer().getUniqueId().toString()).setETC_LastVisited(Area);
					if(GoldBigDragon_RPG.Main.ServerOption.PlayerList.get(player.getUniqueId().toString()).isBgmOn())
    				{
    					if(A.getAreaOption(Area, (char) 6) == true)
    					{
    					  	YamlController YC = new YamlController(GoldBigDragon_RPG.Main.Main.plugin);
    						YamlManager AreaList = YC.getNewConfig("Area/AreaList.yml");
    						if(Bukkit.getPluginManager().isPluginEnabled("NoteBlockAPI") == true)
    							new OtherPlugins.NoteBlockAPIMain().Play(player, AreaList.getInt(Area+".BGM"));
    					}
    				}
					if(A.getAreaOption(Area, (char) 4) == true)
					{
					  	YamlController YC = new YamlController(GoldBigDragon_RPG.Main.Main.plugin);
						YamlManager QuestList  = YC.getNewConfig("Quest/QuestList.yml");
						YamlManager PlayerQuestList  = YC.getNewConfig("Quest/PlayerData/"+player.getUniqueId()+".yml");
						
						Object[] b = PlayerQuestList.getConfigurationSection("Started").getKeys(false).toArray();
						for(short count = 0; count < b.length; count++)
						{
							String QuestName = (String) b[count];
							if(PlayerQuestList.getString("Started."+QuestName+".Type").equalsIgnoreCase("Visit"))
							{
								if(PlayerQuestList.getString("Started."+QuestName+".AreaName").equalsIgnoreCase(Area))
									{
										PlayerQuestList.set("Started."+QuestName+".Type",QuestList.getString(QuestName+".FlowChart."+(PlayerQuestList.getInt("Started."+QuestName+".Flow")+1)+".Type"));
										PlayerQuestList.set("Started."+QuestName+".Flow",PlayerQuestList.getInt("Started."+QuestName+".Flow")+1);
										PlayerQuestList.removeKey("Started."+QuestName+".AreaName");
										PlayerQuestList.saveConfig();
										GoldBigDragon_RPG.Quest.QuestGUI QGUI = new GoldBigDragon_RPG.Quest.QuestGUI();
										QGUI.QuestTypeRouter(player, QuestName);
										//����Ʈ �Ϸ� �޽���//
										break;
									}
							}
						}
						A.sendAreaTitle(player, Area);
					}
					return;
				}
			}
			else
			{
				GoldBigDragon_RPG.Main.ServerOption.PlayerCurrentArea.put(player, "null");
				GoldBigDragon_RPG.Main.ServerOption.PlayerList.get(event.getPlayer().getUniqueId().toString()).setETC_CurrentArea("null");
				if(Bukkit.getPluginManager().isPluginEnabled("NoteBlockAPI") == true)
					new OtherPlugins.NoteBlockAPIMain().Stop(player);
			}
			return;
		}
	}

	public void PlayerChatting(PlayerChatEvent event)
	{
		Object_UserData u = new Object_UserData();
		GoldBigDragon_RPG.Effect.Sound sound = new GoldBigDragon_RPG.Effect.Sound();
	    Player player = event.getPlayer();

	    if(u.getTemp(player)!=null)
	    {
	    	TEMProuter(event);
	    	return;
	    }
	    
	    if(player.isOp()==true)
		    if(u.getType(player) != null)
			    if(u.getType(player).equals("Quest"))
		    	{QuestTypeChatting(event); return;}
			    else if(u.getType(player).equals("WorldCreater"))
		    	{WorldCreaterTypeChatting(event);return;}
			    else if(u.getType(player).equals("UseableItem")
			    		||u.getType(player).equals("Upgrade")
			    		||u.getType(player).equals("Item"))
	    		{ItemTypeChatting(event);return;}
			    else if(u.getType(player).equals("Area"))
		    	{AreaTypeChatting(event);return;}
			    else if(u.getType(player).equals("NPC"))
		    	{NPCTypeChatting(event);return;}
			    else if(u.getType(player).equals("NewBie"))
		    	{NewBieTypeChatting(event);return;}
			    else if(u.getType(player).equals("Skill"))
		    	{SkillTypeChatting(event);return;}
			    else if(u.getType(player).equals("Job"))
		    	{JobTypeChatting(event);return;}
			    else if(u.getType(player).equals("Monster"))
		    	{MonsterTypeChatting(event);return;}
			    else if(u.getType(player).equals("Teleport"))
		    	{TeleportTypeChatting(event);return;}
			    else if(u.getType(player).equals("Event"))
		    	{EventChatting(event);return;}
			    else if(u.getType(player).equals("System"))
		    	{SystemTypeChatting(event);return;}
			    else if(u.getType(player).equals("Navi"))
		    	{NaviTypeChatting(event);return;}
			    else if(u.getType(player).equals("Gamble"))
		    	{GambleChatting(event);return;}
	  	YamlController YC = new YamlController(GoldBigDragon_RPG.Main.Main.plugin);
	  	YamlManager Config = YC.getNewConfig("config.yml");
	  	String Prefix = "";
	  	if(Config.contains("Server.ChatPrefix"))
	  	{
		  	Calendar C = Calendar.getInstance();
	  		Prefix = Config.getString("Server.ChatPrefix");
			Prefix=Prefix.replace("%t%",C.get(Calendar.HOUR_OF_DAY)+"��"+C.get(Calendar.MINUTE)+"��");
			if(player.getGameMode()==GameMode.SURVIVAL)
				Prefix=Prefix.replace("%gm%","�����̹�");
			else if(player.getGameMode()==GameMode.ADVENTURE)
				Prefix=Prefix.replace("%gm%","������");
			else if(player.getGameMode()==GameMode.CREATIVE)
				Prefix=Prefix.replace("%gm%","ũ������Ƽ��");
			else if(player.getGameMode()==GameMode.SPECTATOR)
				Prefix=Prefix.replace("%gm%","����");
			
			if(GoldBigDragon_RPG.Main.ServerOption.PlayerList.get(event.getPlayer().getUniqueId().toString()).getOption_ChattingType()==0)
				Prefix=Prefix.replace("%ct%","�Ϲ�");
			else if(GoldBigDragon_RPG.Main.ServerOption.PlayerList.get(event.getPlayer().getUniqueId().toString()).getOption_ChattingType()==1)
				Prefix=Prefix.replace("%ct%","��Ƽ");
			else if(GoldBigDragon_RPG.Main.ServerOption.PlayerList.get(event.getPlayer().getUniqueId().toString()).getOption_ChattingType()==3)
				Prefix=Prefix.replace("%ct%","������");
			Prefix=Prefix.replace("%lv%",GoldBigDragon_RPG.Main.ServerOption.PlayerList.get(event.getPlayer().getUniqueId().toString()).getStat_Level()+"");
			Prefix=Prefix.replace("%rlv%",GoldBigDragon_RPG.Main.ServerOption.PlayerList.get(event.getPlayer().getUniqueId().toString()).getStat_RealLevel()+"");
		  	YamlManager Job = YC.getNewConfig("Skill/PlayerData/" + player.getUniqueId()+".yml");
			Prefix=Prefix.replace("%job%",Job.getString("Job.Type"));
			Prefix=Prefix.replace("%player%",player.getName());
			Prefix=Prefix.replace("%message%",event.getMessage());
  			event.setCancelled(true);
		  	switch(GoldBigDragon_RPG.Main.ServerOption.PlayerList.get(event.getPlayer().getUniqueId().toString()).getOption_ChattingType())
		  	{
		  	case 0:
		  		Bukkit.broadcastMessage(Prefix);
		  		return;
		  	case 1: 
		  		if(GoldBigDragon_RPG.Main.ServerOption.PartyJoiner.containsKey(player) == false)
		  		{
		  			player.sendMessage(ChatColor.BLUE + "[��Ƽ] : ��Ƽ�� ���ԵǾ� ���� �ʽ��ϴ�!");
	  				sound.SP(player, org.bukkit.Sound.ORB_PICKUP, 2.0F, 1.7F);
		  		}
		  		else
		  		{
		  			GoldBigDragon_RPG.Main.ServerOption.Party.get(GoldBigDragon_RPG.Main.ServerOption.PartyJoiner.get(player)).PartyBroadCastMessage(ChatColor.BLUE + "[��Ƽ] "+Prefix,null);
			  		Bukkit.getConsoleSender().sendMessage("[��Ƽ] "+Prefix);
		  		}
	  			return;
		  	case 2:
	  			event.setCancelled(true);
		  		return;
		  	case 3:
	  			event.setCancelled(true);
	  			if(player.isOp() == false)
	  			{
		  			player.sendMessage(ChatColor.LIGHT_PURPLE + "[������] : ����� �����ڰ� �ƴմϴ�!");
	  				sound.SP(player, org.bukkit.Sound.ORB_PICKUP, 2.0F, 1.7F);
	  			}
	  			else
	  			{
	  		    	Collection<? extends Player> playerlist = Bukkit.getServer().getOnlinePlayers();
	  		    	Player[] a = new Player[playerlist.size()];
	  		    	playerlist.toArray(a);
	  	  			for(short count = 0; count<a.length;count++)
	  	  			{
	  	  		    	if(a[count].isOnline() == true)
	  	  		    	{
	  	  		    		Player send = (Player) Bukkit.getOfflinePlayer(((Player)a[count]).getName());
	  	  		    		send.sendMessage(ChatColor.LIGHT_PURPLE + "[������] "+Prefix);
	  	  		    	}	
	  	  		    }
	  		  		Bukkit.getConsoleSender().sendMessage("[������] "+Prefix);
	  			}
	  			return;
		  	}
	  	}
	  	else
	  	{
		  	switch(GoldBigDragon_RPG.Main.ServerOption.PlayerList.get(event.getPlayer().getUniqueId().toString()).getOption_ChattingType())
		  	{
		  	case 0:
		  		return;
		  	case 1: 
	  			event.setCancelled(true);
		  		if(GoldBigDragon_RPG.Main.ServerOption.PartyJoiner.containsKey(player) == false)
		  		{
		  			player.sendMessage(ChatColor.BLUE + "[��Ƽ] : ��Ƽ�� ���ԵǾ� ���� �ʽ��ϴ�!");
	  				sound.SP(player, org.bukkit.Sound.ORB_PICKUP, 2.0F, 1.7F);
		  		}
		  		else
		  		{
		  			GoldBigDragon_RPG.Main.ServerOption.Party.get(GoldBigDragon_RPG.Main.ServerOption.PartyJoiner.get(player)).PartyBroadCastMessage(ChatColor.BLUE + "[��Ƽ] "+player.getName()+" : " + event.getMessage(),null);
			  		Bukkit.getConsoleSender().sendMessage("[��Ƽ] "+player.getName()+" : " + event.getMessage());
		  		}
	  			return;
		  	case 2:
	  			event.setCancelled(true);
		  		return;
		  	case 3:
	  			event.setCancelled(true);
	  			if(player.isOp() == false)
	  			{
		  			player.sendMessage(ChatColor.LIGHT_PURPLE + "[������] : ����� �����ڰ� �ƴմϴ�!");
	  				sound.SP(player, org.bukkit.Sound.ORB_PICKUP, 2.0F, 1.7F);
	  			}
	  			else
	  			{
	  		    	Collection<? extends Player> playerlist = Bukkit.getServer().getOnlinePlayers();
	  		    	Player[] a = new Player[playerlist.size()];
	  		    	playerlist.toArray(a);
	  	  			for(short count = 0; count<a.length;count++)
	  	  			{
	  	  		    	if(a[count].isOnline() == true)
	  	  		    	{
	  	  		    		Player send = (Player) Bukkit.getOfflinePlayer(((Player)a[count]).getName());
	  	  		    		send.sendMessage(ChatColor.LIGHT_PURPLE + "[������] "+player.getName()+" : " + event.getMessage());
	  	  		    	}	
	  	  		    }
	  		  		Bukkit.getConsoleSender().sendMessage("[������] "+player.getName()+" : " + event.getMessage());
	  			}
	  			return;
		  	}
	  	}
	}

	private void TEMProuter(PlayerChatEvent event)
	{
		Object_UserData u = new Object_UserData();
		event.setCancelled(true);
		Player player = event.getPlayer();
		GoldBigDragon_RPG.Effect.Sound s = new GoldBigDragon_RPG.Effect.Sound();
		String Message = ChatColor.stripColor(event.getMessage());
		if(u.getTemp(player).compareTo("FA")==0)
		{
			if(Message.equals(player.getName()))
			{
				s.SP(player, Sound.ORB_PICKUP, 1.0F, 1.8F);
				player.sendMessage(ChatColor.RED+"[ģ��] : �ڱ� �ڽ��� �߰��� �� �����ϴ�!");
			}
			else
			{
				Message.replace(".", "");
				if(Bukkit.getServer().getPlayer(Message) != null)
					new GoldBigDragon_RPG.GUI.EquipGUI().SetFriends(player, Bukkit.getServer().getPlayer(Message));
				else
				{
					s.SP(player, Sound.ORB_PICKUP, 1.0F, 1.8F);
					player.sendMessage(ChatColor.RED+"[ģ��] : �ش� �÷��̾ ã�� �� �����ϴ�!");
				}
			}
			new GoldBigDragon_RPG.GUI.ETCGUI().FriendsGUI(player, (short) 0);
			u.initTemp(player);
		}
		else if(u.getTemp(player).compareTo("Structure")==0)
			new GoldBigDragon_RPG.Structure.StructureAction().PlayerChatrouter(event);
		else if(u.getTemp(player).compareTo("Dungeon")==0)
			new GoldBigDragon_RPG.Dungeon.DungeonAction().PlayerChatrouter(event);
		return;
	}
	
	private void QuestTypeChatting(PlayerChatEvent event)
	{
		Object_UserData u = new Object_UserData();
		GoldBigDragon_RPG.Effect.Sound sound = new GoldBigDragon_RPG.Effect.Sound();
	    Player player = event.getPlayer();
    	GoldBigDragon_RPG.Quest.QuestGUI QGUI = new GoldBigDragon_RPG.Quest.QuestGUI();
	  	YamlController YC = new YamlController(GoldBigDragon_RPG.Main.Main.plugin);
		YamlManager QuestConfig=YC.getNewConfig("Quest/QuestList.yml");

    	event.setCancelled(true);
    	String message = ChatColor.stripColor(event.getMessage());
    	switch(u.getString(player, (byte)1))
    	{
	    	case "Cal"://Caluclate
	    	{
				if(isIntMinMax(message, player, 1, 5))
				{
					sound.SP(player, Sound.ITEM_PICKUP, 1.0F, 1.0F);
			    	u.setString(player, (byte)1, "CVS");
			    	u.setInt(player, (byte)1, Integer.parseInt(message));
			    	String Example=null;
			    	switch(Integer.parseInt(message))
			    	{
			    	case 1:
			    		Example = "�÷��̾� ���� �� B";
			    		break;
			    	case 2:
			    		Example = "�÷��̾� ���� �� B";
			    		break;
			    	case 3:
			    		Example = "�÷��̾� ���� �� B";
			    		break;
			    	case 4:
			    		Example = "�÷��̾� ���� �� B";
			    		break;
			    	case 5:
			    		Example = "�÷��̾� ���� �� B";
			    		break;
			    	}
					player.sendMessage(ChatColor.GREEN + "[����Ʈ] : "+ChatColor.YELLOW+Example+ChatColor.GREEN+" ���� "+ChatColor.YELLOW+"B"+ChatColor.GREEN+" ���� �� ���� �� �ΰ���?");
			    	if(Integer.parseInt(message) <= 2)
						player.sendMessage(ChatColor.GRAY + "(�ּ� -1000 ~ �ִ� 20000)");
			    	else
						player.sendMessage(ChatColor.GRAY + "(�ּ� 1 ~ �ִ� 100)");
					player.sendMessage(ChatColor.GRAY + "(��� ��� -2000 ���ϰų� 40000 �̻��� ��� ���� -2000�� 40000���� ����)");
					player.sendMessage(ChatColor.GRAY + "(���� Ÿ����  Integer�̹Ƿ�, ��� ���� �ʹ� ũ�ų� ������ �̻��� ���� ���ü��� ����)");
				}
	    	}
	    	return;
	    	case "CVS"://Calculate Value Set
	    	{
	    		if(u.getInt(player, (byte)1)<=2)
	    		{
	    			if(isIntMinMax(message, player, -1000, 20000))
					{
		    			short size = (short) QuestConfig.getConfigurationSection(u.getString(player, (byte)2)+".FlowChart").getKeys(false).size();
						sound.SP(player, Sound.ITEM_PICKUP, 1.0F, 1.0F);
						QuestConfig.set(u.getString(player, (byte)2)+".FlowChart."+size+".Type", "Cal");
		        		QuestConfig.set(u.getString(player, (byte)2)+".FlowChart."+size+".Comparison", u.getInt(player, (byte)1));
			    		QuestConfig.set(u.getString(player, (byte)2)+".FlowChart."+size+".Value", Integer.parseInt(message));
		    	    	QuestConfig.saveConfig();
		    			player.sendMessage(ChatColor.GREEN+"[����Ʈ] : ��� ���� ������ �Ϸ�Ǿ����ϴ�!");
		    			QGUI.FixQuestGUI(player, (short) 0, u.getString(player, (byte)2));
		    	    	u.clearAll(player);
					}
	    		}
	    		else
	    		{
	    			if(isIntMinMax(message, player, 1, 100))
					{
		    			short size = (short) QuestConfig.getConfigurationSection(u.getString(player, (byte)2)+".FlowChart").getKeys(false).size();
						sound.SP(player, Sound.ITEM_PICKUP, 1.0F, 1.0F);
						QuestConfig.set(u.getString(player, (byte)2)+".FlowChart."+size+".Type", "Cal");
		        		QuestConfig.set(u.getString(player, (byte)2)+".FlowChart."+size+".Comparison", u.getInt(player, (byte)1));
			    		QuestConfig.set(u.getString(player, (byte)2)+".FlowChart."+size+".Value", Integer.parseInt(message));
		    	    	QuestConfig.saveConfig();
		    			player.sendMessage(ChatColor.GREEN+"[����Ʈ] : ��� ���� ������ �Ϸ�Ǿ����ϴ�!");
		    			QGUI.FixQuestGUI(player, (short) 0, u.getString(player, (byte)2));
		    	    	u.clearAll(player);
					}
	    		}
	    	}
	    	return;
    	case "IFMVS"://IF Max Value Set
    	{
			if(isIntMinMax(message, player, u.getInt(player, (byte)2), 40000))
			{
    			short size = (short) QuestConfig.getConfigurationSection(u.getString(player, (byte)2)+".FlowChart").getKeys(false).size();
				sound.SP(player, Sound.ITEM_PICKUP, 1.0F, 1.0F);
				QuestConfig.set(u.getString(player, (byte)2)+".FlowChart."+size+".Type", "IF");
        		QuestConfig.set(u.getString(player, (byte)2)+".FlowChart."+size+".Comparison", u.getInt(player, (byte)1));
	    		QuestConfig.set(u.getString(player, (byte)2)+".FlowChart."+size+".Min", u.getInt(player, (byte)2));
	    		QuestConfig.set(u.getString(player, (byte)2)+".FlowChart."+size+".Max", Integer.parseInt(message));
    	    	QuestConfig.saveConfig();
    			player.sendMessage(ChatColor.GREEN+"[����Ʈ] : IF�� ������ �Ϸ�Ǿ����ϴ�!");
    			QGUI.FixQuestGUI(player, (short) 0, u.getString(player, (byte)2));
    	    	u.clearAll(player);
			}
    	}
    	return;
    	case "IFVS"://IF Value Set
    	{
			if(isIntMinMax(message, player, -2000, 40000))
			{
    			short size = (short) QuestConfig.getConfigurationSection(u.getString(player, (byte)2)+".FlowChart").getKeys(false).size();
				sound.SP(player, Sound.ITEM_PICKUP, 1.0F, 1.0F);
		    	if(u.getInt(player, (byte)1)!=7)
		    	{
	        		QuestConfig.set(u.getString(player, (byte)2)+".FlowChart."+size+".Type", "IF");
	        		QuestConfig.set(u.getString(player, (byte)2)+".FlowChart."+size+".Comparison", u.getInt(player, (byte)1));
		    		QuestConfig.set(u.getString(player, (byte)2)+".FlowChart."+size+".Value", Integer.parseInt(message));
		    	}
		    	else
		    	{
		    		u.setInt(player, (byte)2, Integer.parseInt(message));
		    		QuestConfig.saveConfig();
			    	u.setString(player, (byte)1, "IFMVS");
					player.sendMessage(ChatColor.GREEN + "[����Ʈ] : "+ChatColor.YELLOW+Integer.parseInt(message)+" <= �÷��̾� ���� <= B"+ChatColor.GREEN+" ���� "+ChatColor.YELLOW+"C"+ChatColor.GREEN+" ���� �� ���� �� �ΰ���?");
					player.sendMessage(ChatColor.GRAY + "(�ּ� "+Integer.parseInt(message)+" ~ �ִ� 40000)");
		    		return;
		    	}
    	    	QuestConfig.saveConfig();
    			player.sendMessage(ChatColor.GREEN+"[����Ʈ] : IF�� ������ �Ϸ�Ǿ����ϴ�!");
    			QGUI.FixQuestGUI(player, (short) 0, u.getString(player, (byte)2));
    	    	u.clearAll(player);
			}
    	}
    	return;
    	case "IFTS"://IF Type Select
    	{
			if(isIntMinMax(message, player, 1, 7))
			{
				sound.SP(player, Sound.ITEM_PICKUP, 1.0F, 1.0F);
		    	u.setString(player, (byte)1, "IFVS");
		    	u.setInt(player, (byte)1, Integer.parseInt(message));
		    	String Example=null;
		    	switch(Integer.parseInt(message))
		    	{
		    	case 1:
		    		Example = "�÷��̾� ���� == B";
		    		break;
		    	case 2:
		    		Example = "�÷��̾� ���� != B";
		    		break;
		    	case 3:
		    		Example = "�÷��̾� ���� > B";
		    		break;
		    	case 4:
		    		Example = "�÷��̾� ���� < B";
		    		break;
		    	case 5:
		    		Example = "�÷��̾� ���� >= B";
		    		break;
		    	case 6:
		    		Example = "�÷��̾� ���� <= B";
		    		break;
		    	case 7:
		    		Example = "C <= �÷��̾� ���� <= B";
					player.sendMessage(ChatColor.GREEN + "[����Ʈ] : "+ChatColor.YELLOW+Example+ChatColor.GREEN+" ���� "+ChatColor.YELLOW+"C"+ChatColor.GREEN+" ���� �� ���� �� �ΰ���?");
					player.sendMessage(ChatColor.GRAY + "(�ּ� -2000 ~ �ִ� 40000)");
		    		return;
		    	}
				player.sendMessage(ChatColor.GREEN + "[����Ʈ] : "+ChatColor.YELLOW+Example+ChatColor.GREEN+" ���� "+ChatColor.YELLOW+"B"+ChatColor.GREEN+" ���� �� ���� �� �ΰ���?");
				player.sendMessage(ChatColor.GRAY + "(�ּ� -2000 ~ �ִ� 40000)");
			}
    	}
    	return;
	    	case "CV"://ChangeVariable
	    	{
				if(isIntMinMax(message, player, -1000, 30000))
				{
					sound.SP(player, Sound.ITEM_PICKUP, 1.0F, 1.0F);
	    			short size = (short) QuestConfig.getConfigurationSection(u.getString(player, (byte)2)+".FlowChart").getKeys(false).size();
	        		QuestConfig.set(u.getString(player, (byte)2)+".FlowChart."+size+".Type", "VarChange");
	        		QuestConfig.set(u.getString(player, (byte)2)+".FlowChart."+size+".Value", Integer.parseInt(message));
	    	    	QuestConfig.saveConfig();
	    			player.sendMessage(ChatColor.GREEN+"[����Ʈ] : ���� ���� ������ �Ϸ�Ǿ����ϴ�!");
	    			QGUI.FixQuestGUI(player, (short) 0, u.getString(player, (byte)2));
	    	    	u.clearAll(player);
				}
	    	}
	    	return;
    	case "SCV"://SetChoiceVariable
    	{
			if(isIntMinMax(message, player, -1000, 30000))
			{
				sound.SP(player, Sound.ITEM_PICKUP, 1.0F, 1.0F);
				u.setInt(player, (byte)(u.getInt(player, (byte)1)+2),Integer.parseInt(message));
				u.setInt(player, (byte)1,u.getInt(player, (byte)1)+1);
	    		if(u.getInt(player, (byte)0)==u.getInt(player, (byte)1))
	    		{
	    			short size = (short) QuestConfig.getConfigurationSection(u.getString(player, (byte)2)+".FlowChart").getKeys(false).size();
	        		QuestConfig.set(u.getString(player, (byte)2)+".FlowChart."+size+".Type", "Choice");
	        		for(int count = 0; count <u.getInt(player, (byte)0);count++)
	        		{
	        			QuestConfig.set(u.getString(player, (byte)2)+".FlowChart."+size+".Choice."+count+".Lore", u.getString(player, (byte)(count+4)));
	        			QuestConfig.set(u.getString(player, (byte)2)+".FlowChart."+size+".Choice."+count+".Var", u.getInt(player, (byte)(count+2)));
	        		}
	    	    	QuestConfig.saveConfig();
	    			player.sendMessage(ChatColor.GREEN+"[����Ʈ] : �������� ���������� ��ϵǾ����ϴ�!");
	    			QGUI.FixQuestGUI(player, (short) 0, u.getString(player, (byte)2));
	    	    	u.clearAll(player);
	    		}
	    		else
	    		{
			    	u.setString(player, (byte)1, "SCL");
			    	player.sendMessage(ChatColor.GREEN + "[����Ʈ] : "+(u.getInt(player, (byte)1)+1)+"��° ���ÿ� ���� ���� �Է� �ϼ���!");
					player.sendMessage(ChatColor.GOLD + "%enter%"+ChatColor.WHITE + " - ���� ��� ���� -");
					player.sendMessage(ChatColor.GOLD + "%player%"+ChatColor.WHITE + " - �÷��̾� ��Ī�ϱ� -");
					player.sendMessage(ChatColor.WHITE + ""+ChatColor.BOLD + "&l " + ChatColor.BLACK + "&0 "+ChatColor.DARK_BLUE+"&1 "+ChatColor.DARK_GREEN+"&2 "+
					ChatColor.DARK_AQUA + "&3 " +ChatColor.DARK_RED + "&4 " + ChatColor.DARK_PURPLE + "&5 " +
							ChatColor.GOLD + "&6 " + ChatColor.GRAY + "&7 " + ChatColor.DARK_GRAY + "&8 " +
					ChatColor.BLUE + "&9 " + ChatColor.GREEN + "&a " + ChatColor.AQUA + "&b " + ChatColor.RED + "&c" +
							ChatColor.LIGHT_PURPLE + "&d " + ChatColor.YELLOW + "&e "+ChatColor.WHITE + "&f");
	    		}
			}
			
    	}
    	return;
    	case "SCL"://SetChoiceLore
    	{
			sound.SP(player, Sound.ITEM_PICKUP, 1.0F, 1.0F);
			u.setString(player, (byte)(u.getInt(player, (byte)1)+4), event.getMessage());
	    	u.setString(player, (byte)1, "SCV");
			player.sendMessage(ChatColor.GREEN + "[����Ʈ] : "+(u.getInt(player, (byte)1)+1)+"�� �������� �� ���, �÷��̾� ������ ������ ��ȯ��ų���?");
			player.sendMessage(ChatColor.GRAY + "(�ּ� -1000 ~ �ִ� 30000)");
    	}
    	return;
    	case "CS"://ChoiceSize
			if(isIntMinMax(message, player, 1, 4))
			{
				sound.SP(player, Sound.ITEM_PICKUP, 1.0F, 1.0F);
				u.setInt(player, (byte)0, Integer.parseInt(message));
				u.setInt(player, (byte)1, 0);
		    	u.setString(player, (byte)1, "SCL");
		    	player.sendMessage(ChatColor.GREEN + "[����Ʈ] : 1��° ���ÿ� ���� ���� �Է� �ϼ���!");
				player.sendMessage(ChatColor.GOLD + "%enter%"+ChatColor.WHITE + " - ���� ��� ���� -");
				player.sendMessage(ChatColor.GOLD + "%player%"+ChatColor.WHITE + " - �÷��̾� ��Ī�ϱ� -");
				player.sendMessage(ChatColor.WHITE + ""+ChatColor.BOLD + "&l " + ChatColor.BLACK + "&0 "+ChatColor.DARK_BLUE+"&1 "+ChatColor.DARK_GREEN+"&2 "+
				ChatColor.DARK_AQUA + "&3 " +ChatColor.DARK_RED + "&4 " + ChatColor.DARK_PURPLE + "&5 " +
						ChatColor.GOLD + "&6 " + ChatColor.GRAY + "&7 " + ChatColor.DARK_GRAY + "&8 " +
				ChatColor.BLUE + "&9 " + ChatColor.GREEN + "&a " + ChatColor.AQUA + "&b " + ChatColor.RED + "&c" +
						ChatColor.LIGHT_PURPLE + "&d " + ChatColor.YELLOW + "&e "+ChatColor.WHITE + "&f");
			}
	    	return;
    	case "Whisper":
    	case "BroadCast":
    	case "PScript":
	    	{
	    		short size = (short) QuestConfig.getConfigurationSection(u.getString(player, (byte)2)+".FlowChart").getKeys(false).size();
	    		QuestConfig.set(u.getString(player, (byte)2)+".FlowChart."+size+".Type", u.getString(player, (byte)1));
		    	QuestConfig.set(u.getString(player, (byte)2)+".FlowChart."+size+".Message", event.getMessage());
		    	QuestConfig.saveConfig();
				player.sendMessage(ChatColor.GREEN+"[����Ʈ] : ��簡 ���������� ��ϵǾ����ϴ�!");
				QGUI.FixQuestGUI(player, (short) 0, u.getString(player, (byte)2));
		    	u.clearAll(player);
	    	}
	    	return;
    	case "BPID"://BlockPlaceID
			if(isIntMinMax(message, player, 1, Integer.MAX_VALUE))
			{
				GoldBigDragon_RPG.Event.Interact I = new GoldBigDragon_RPG.Event.Interact();
				if(I.SetItemDefaultName(Short.parseShort(message),(byte)0).equalsIgnoreCase("�������� ���� ������"))
				{
					player.sendMessage(ChatColor.RED + "[SYSTEM] : �ش� �������� �������� �ʽ��ϴ�!");
	  				sound.SP(player, org.bukkit.Sound.ORB_PICKUP, 2.0F, 1.7F);
	  				return;
				}
				String QuestName = u.getString(player, (byte)2);
				int size = u.getInt(player, (byte)1);
				sound.SP(player, Sound.ITEM_PICKUP, 1.0F, 1.0F);
				QuestConfig.set(QuestName+".FlowChart."+size+".ID", Integer.parseInt(message));
				QuestConfig.saveConfig();
		    	u.setString(player, (byte)1, "BPDATA");
		    	player.sendMessage(ChatColor.GREEN + "[����Ʈ] : ��ġ �� ��� DATA�� �Է� �� �ּ���!");
			}
	    	return;
    	case "BPDATA"://BlockPlaceDATA
			if(isIntMinMax(message, player, 0, Integer.MAX_VALUE))
			{
				String QuestName = u.getString(player, (byte)2);
				int size = u.getInt(player, (byte)1);
				sound.SP(player, Sound.ITEM_PICKUP, 1.0F, 1.0F);
				QuestConfig.set(QuestName+".FlowChart."+size+".DATA", Integer.parseInt(message));
				QuestConfig.saveConfig();
		    	u.setString(player, (byte)1, "BPDATA");
		    	u.clearAll(player);
		    	QGUI.FixQuestGUI(player, (short) 0, QuestName);
			}
	    	return;
    	case "Script":
	    	u.setString(player, (byte)3,ChatColor.WHITE + event.getMessage());
			player.sendMessage(ChatColor.GREEN+"[����Ʈ] : �ش� ��縦 ���� NPC�� ��Ŭ�� �ϼ���.");
	    	return;
    	case "Visit":
			YamlManager AreaList = YC.getNewConfig("Area/AreaList.yml");
			Object[] arealist = AreaList.getConfigurationSection("").getKeys(false).toArray();

			if(arealist.length <= 0)
			{
				GoldBigDragon_RPG.Effect.Sound s = new GoldBigDragon_RPG.Effect.Sound();
				s.SP(player, org.bukkit.Sound.ORB_PICKUP, 2.0F, 1.7F);
				player.sendMessage(ChatColor.RED + "[SYSTEM] : ������ ������ �����ϴ�!");
				u.clearAll(player);
				return;
			}
			for(short count =0; count <arealist.length;count++)
			{
				if(event.getMessage().equalsIgnoreCase((String) arealist[count]) == true)
				{
					player.sendMessage(ChatColor.GREEN+"[����Ʈ] : "+ChatColor.YELLOW + arealist[count] + ChatColor.GREEN+" ������ �湮�ϵ��� ��� �Ǿ����ϴ�!");

					Set<String> b4 = QuestConfig.getConfigurationSection(u.getString(player, (byte)2)+".FlowChart").getKeys(false);
					
			    	QuestConfig.set(u.getString(player, (byte)2)+".FlowChart."+b4.size()+".Type", "Visit");
			    	QuestConfig.set(u.getString(player, (byte)2)+".FlowChart."+b4.size()+".AreaName", (String) arealist[count]);
			    	QuestConfig.saveConfig();

					QGUI.FixQuestGUI(player, (short) 0, u.getString(player, (byte)2));
					u.clearAll(player);
					return;
				}
				player.sendMessage(ChatColor.GREEN +"  "+ arealist[count].toString());
			}
			player.sendMessage(ChatColor.GREEN + "���������������������� ��Ϧ�����������������");
			for(short count =0; count <arealist.length;count++)
				player.sendMessage(ChatColor.GREEN +"  "+ arealist[count].toString());
			player.sendMessage(ChatColor.GREEN + "���������������������� ��Ϧ�����������������");
			player.sendMessage(ChatColor.DARK_AQUA + "[����Ʈ] : �湮�ؾ� �� ���� �̸��� ���� �ּ���!");
	    	return;
    	case "Hunt":
			if(isIntMinMax(event.getMessage(), player, 1, Integer.MAX_VALUE))
			{
				short Flownumber=0;
				short Monsternumber =0;
				Set<String> b = QuestConfig.getConfigurationSection(u.getString(player, (byte)2)+".FlowChart").getKeys(false);
				if(u.getInt(player, (byte)3) != -1)
					Flownumber = (short) (b.size()-1);
				else
					Flownumber = (short) b.size();
				QuestConfig.set(u.getString(player, (byte)2)+".FlowChart."+Flownumber+".Type","Hunt");
				QuestConfig.set(u.getString(player, (byte)2)+".FlowChart."+Flownumber+".Monster."+(-1)+".MonsterName", null);
				QuestConfig.removeKey(u.getString(player, (byte)2)+".FlowChart."+Flownumber+".Monster."+(-1));
				QuestConfig.saveConfig();
				Set<String> c = QuestConfig.getConfigurationSection(u.getString(player, (byte)2)+".FlowChart."+Flownumber+".Monster").getKeys(false);
				if(u.getInt(player, (byte)2) != -1)
					Monsternumber = (short) u.getInt(player, (byte)2);
				else
					Monsternumber = (short) c.size();
				QuestConfig.set(u.getString(player, (byte)2)+".FlowChart."+Flownumber+".Monster."+Monsternumber+".MonsterName", u.getString(player, (byte)3));
				QuestConfig.set(u.getString(player, (byte)2)+".FlowChart."+Flownumber+".Monster."+Monsternumber+".Amount", Integer.parseInt(event.getMessage()));
				QuestConfig.saveConfig();
				player.sendMessage(ChatColor.GREEN + "[����Ʈ] : " + ChatColor.YELLOW + QGUI.SkullType(u.getString(player, (byte)3)) + ChatColor.GREEN + " (��)�� " + ChatColor.YELLOW + Integer.parseInt(event.getMessage())+ ChatColor.GREEN +" ���� ����ϵ��� �����Ǿ����ϴ�!");

				if(u.getInt(player, (byte)2) < 17)
					QGUI.KeepGoing(player, u.getString(player, (byte)2), (short) Flownumber, (short) Monsternumber,false);
				else
					QGUI.FixQuestGUI(player, (short) 0, u.getString(player, (byte)2));
				u.clearAll(player);
			}
			return;
    	case "Harvest":
	    	if(u.getString(player, (byte)3)!=null)
	    	{
	    		if(ChatColor.stripColor(event.getMessage()).equalsIgnoreCase("x") ||ChatColor.stripColor(event.getMessage()).equalsIgnoreCase("X") ||
	    				ChatColor.stripColor(event.getMessage()).equalsIgnoreCase("o") ||ChatColor.stripColor(event.getMessage()).equalsIgnoreCase("O"))
	    		{
		    		if(ChatColor.stripColor(event.getMessage()).equalsIgnoreCase("x") ||ChatColor.stripColor(event.getMessage()).equalsIgnoreCase("X"))
		    			u.setBoolean(player, (byte)1, false);
		    		else
		    			u.setBoolean(player, (byte)1, true);
					u.setString(player, (byte)3,null);
			    	player.sendMessage(ChatColor.GREEN + "[SYSTEM] : ����� �󸶳� ä���ؾ� ���� �����ϼ���! ("+ChatColor.YELLOW + "1"+ChatColor.GREEN+" ~ "+ChatColor.YELLOW+""+Integer.MAX_VALUE+ChatColor.GREEN+"��)");
	    		}
	    		else
	    		{
    				player.sendMessage(ChatColor.RED + "[SYSTEM] : xȤ�� o�� �Է� ��  �ּ���.");
      				sound.SP(player, org.bukkit.Sound.ORB_PICKUP, 2.0F, 1.7F);
	    		}
  				return;
	    	}
	    	else
	    	{
				if(isIntMinMax(event.getMessage(), player, 1, Integer.MAX_VALUE))
				{
    				short Flownumber=0;
    				short BlockNumber =0;
    				Set<String> b = QuestConfig.getConfigurationSection(u.getString(player, (byte)2)+".FlowChart").getKeys(false);
    				if(u.getInt(player, (byte)3) != -1)
    					Flownumber = (short) (b.size()-1);
    				else
    					Flownumber = (short) b.size();
    				QuestConfig.set(u.getString(player, (byte)2)+".FlowChart."+Flownumber+".Type","Harvest");
    				QuestConfig.set(u.getString(player, (byte)2)+".FlowChart."+Flownumber+".Block."+(-1)+".BlockID", null);
    				QuestConfig.removeKey(u.getString(player, (byte)2)+".FlowChart."+Flownumber+".Block."+(-1));
    				QuestConfig.saveConfig();
    				Set<String> c = QuestConfig.getConfigurationSection(u.getString(player, (byte)2)+".FlowChart."+Flownumber+".Block").getKeys(false);
    				if(u.getInt(player, (byte)4) != -1)
    					BlockNumber = (short) u.getInt(player, (byte)4);
    				else
    					BlockNumber = 0;
    				QuestConfig.set(u.getString(player, (byte)2)+".FlowChart."+Flownumber+".Block."+BlockNumber+".BlockID", u.getInt(player, (byte)1));
    				QuestConfig.set(u.getString(player, (byte)2)+".FlowChart."+Flownumber+".Block."+BlockNumber+".BlockData", u.getInt(player, (byte)2));
    				QuestConfig.set(u.getString(player, (byte)2)+".FlowChart."+Flownumber+".Block."+BlockNumber+".Amount", Integer.parseInt(event.getMessage()));
    				QuestConfig.set(u.getString(player, (byte)2)+".FlowChart."+Flownumber+".Block."+BlockNumber+".DataEquals", u.getBoolean(player, (byte)1));
    				QuestConfig.saveConfig();
    				
    				if(u.getBoolean(player, (byte)1) == false)
    					player.sendMessage(ChatColor.GREEN + "[����Ʈ] : ������ ID�� " + ChatColor.YELLOW + u.getInt(player, (byte)1) +ChatColor.GREEN + " �� ��� ����� " + ChatColor.YELLOW + Integer.parseInt(event.getMessage())+ ChatColor.GREEN +" �� ä���ϵ��� �����Ǿ����ϴ�!");
    				else
    					player.sendMessage(ChatColor.GREEN + "[����Ʈ] : ������ �ڵ� " + ChatColor.YELLOW + u.getInt(player, (byte)1) +  ":"+ u.getInt(player, (byte)2) + ChatColor.GREEN + " �� ����� " + ChatColor.YELLOW + Integer.parseInt(event.getMessage())+ ChatColor.GREEN +" �� ä���ϵ��� �����Ǿ����ϴ�!");

    				if(u.getInt(player, (byte)2) < 17)
    					QGUI.KeepGoing(player, u.getString(player, (byte)2), (short) Flownumber, (short) BlockNumber,true);
    				else
    					QGUI.FixQuestGUI(player, (short) 0, u.getString(player, (byte)2));
    				u.clearAll(player);
				}
    			return;
	    	}
    	}
    	
    	if(u.getString(player, (byte)2)!=null)
    	{
    		if(u.getString(player, (byte)1).contains("District") == true)
    		{
				if(isIntMinMax(event.getMessage(), player, 0, Integer.MAX_VALUE))
				{
    				String QuestName = u.getString(player, (byte)2);
    				int value = Integer.parseInt(event.getMessage());
    				YamlManager QuestList  = YC.getNewConfig("Quest/QuestList.yml");
    				switch(u.getString(player, (byte)1))
    				{
    				case "Level District":
    					QuestList.set(QuestName+".Need.LV", value);
    					break;
    				case "Love District":
    					QuestList.set(QuestName+".Need.Love", value);
    					break;
    				case "STR District":
    					QuestList.set(QuestName+".Need.STR", value);
    					break;
    				case "DEX District":
    					QuestList.set(QuestName+".Need.DEX", value);
    					break;
    				case "INT District":
    					QuestList.set(QuestName+".Need.INT", value);
    					break;
    				case "WILL District":
    					QuestList.set(QuestName+".Need.WILL", value);
    					break;
    				case "LUK District":
    					QuestList.set(QuestName+".Need.LUK", value);
    					break;
    				case "Accept District":
    					QuestList.set(QuestName+".Server.Limit", value);
    					break;
    				}
    				QuestList.saveConfig();
    				u.clearAll(player);
    				QGUI.QuestOptionGUI(player, QuestName);
				}
    			return;
    		}
    	}
    	
		if(u.getString(player, (byte)4)!=null)
		{
			if(isIntMinMax(event.getMessage(), player, 0, Integer.MAX_VALUE))
			{
    			switch(u.getString(player, (byte)4))
    			{
	    			case "M":
	    		    	event.setCancelled(true);
	    				u.setString(player, (byte)4,null);
	    				u.setInt(player, (byte)1, Integer.parseInt(ChatColor.stripColor(event.getMessage())));
			    		QGUI.GetPresentGUI(player, u.getString(player, (byte)3));
	    				break;
	    			case "E":
	    		    	event.setCancelled(true);
	    				u.setString(player, (byte)4,null);
	    				u.setInt(player, (byte)2, Integer.parseInt(ChatColor.stripColor(event.getMessage())));
			    		QGUI.GetPresentGUI(player, u.getString(player, (byte)3));
	    				break;
	    			case "L":
	    		    	event.setCancelled(true);
	    				u.setString(player, (byte)4,null);
	    				u.setInt(player, (byte)3, Integer.parseInt(ChatColor.stripColor(event.getMessage())));
			    		QGUI.GetPresentGUI(player, u.getString(player, (byte)3));
	    				break;
    				default :
    					break;
    			}
			}
			return;
    	}
		return;
	}
	
	private void WorldCreaterTypeChatting(PlayerChatEvent event)
	{
		Object_UserData u = new Object_UserData();
		event.setCancelled(true);
		Player player = event.getPlayer();
		GoldBigDragon_RPG.Effect.Sound s = new GoldBigDragon_RPG.Effect.Sound();
		String Message = ChatColor.stripColor(event.getMessage());
		s.SP(player, Sound.ANVIL_USE,1.0F, 0.8F);
		player.sendMessage(ChatColor.GOLD+""+ChatColor.BOLD+"[���� ����] : ���� ���� ��...");
		WorldType TYPE = WorldType.FLAT;
		
		switch(u.getString(player, (byte)3))
		{
		case "NORMAL":
			TYPE = WorldType.NORMAL;
			break;
		case "FLAT":
			TYPE = WorldType.FLAT;
			break;
		case "LARGE_BIOMES":
			TYPE = WorldType.LARGE_BIOMES;
			break;
		}
		Message = Message.replace(" ", "_");
		WorldCreator world = new WorldCreator(Message);
		world.type(TYPE);
		world.generateStructures();
		Bukkit.createWorld(world);
		u.clearAll(player);
	  	YamlController YC = new YamlController(GoldBigDragon_RPG.Main.Main.plugin);
		YamlManager WorldConfig =YC.getNewConfig("WorldList.yml");
		WorldConfig.createSection(Message);
		WorldConfig.saveConfig();
		Object[] worldname = WorldConfig.getKeys().toArray();
		for(short count = 0; count < WorldConfig.getKeys().size();count++)
			if(Bukkit.getWorld(worldname[count].toString()) == null)
				WorldCreator.name(worldname[count].toString()).createWorld();
    	s.SP(player, Sound.WOLF_BARK,1.0F, 0.8F);
		player.sendMessage(ChatColor.GOLD+""+ChatColor.BOLD+"[���� ����] : ���� ���� ����!");
		return;
	}
	
	private void ItemTypeChatting(PlayerChatEvent event)
	{
		Object_UserData u = new Object_UserData();
		GoldBigDragon_RPG.Effect.Sound sound = new GoldBigDragon_RPG.Effect.Sound();
		Player player = event.getPlayer();
		GoldBigDragon_RPG.CustomItem.ItemGUI IGUI = new GoldBigDragon_RPG.CustomItem.ItemGUI();
		GoldBigDragon_RPG.GUI.UpGradeGUI UpGUI = new GoldBigDragon_RPG.GUI.UpGradeGUI();
		GoldBigDragon_RPG.Effect.Sound s = new GoldBigDragon_RPG.Effect.Sound();

	  	YamlController YC = new YamlController(GoldBigDragon_RPG.Main.Main.plugin);
		YamlManager RecipeList = YC.getNewConfig("Item/Upgrade.yml");
		YamlManager ItemList = YC.getNewConfig("Item/ItemList.yml");
		if(u.getType(player).compareTo("UseableItem")==0||u.getType(player).compareTo("UseableItem")==0)
			ItemList = YC.getNewConfig("Item/Consume.yml");
		event.setCancelled(true);
		int number = 0;
		String Message = ChatColor.stripColor(event.getMessage());
		if(u.getInt(player, (byte)3)!=-1)
			number = u.getInt(player, (byte)3);
		String Type = u.getString(player, (byte)1);
		if(ItemList.getString(number+"")==null)
		{
			player.sendMessage(ChatColor.RED+"[SYSTEM] : �ٸ� OP�� �������� �����Ͽ� �ݿ����� �ʾҽ��ϴ�!");
			return;
		}
		String SayType = u.getString(player, (byte)1);
		if(SayType.compareTo("DisplayName")==0 || SayType.compareTo("Lore")==0)
			ItemList.set(number+"."+Type,event.getMessage());
		else if(SayType.compareTo("ID")==0)
		{
			if(isIntMinMax(Message, player, 1, Integer.MAX_VALUE))
			{
				GoldBigDragon_RPG.Event.Interact I = new GoldBigDragon_RPG.Event.Interact();
				if(I.SetItemDefaultName(Short.parseShort(Message),(byte)0).compareTo("�������� ���� ������")==0)
				{
					player.sendMessage(ChatColor.RED + "[SYSTEM] : �ش� �������� �������� �ʽ��ϴ�!");
	  				sound.SP(player, org.bukkit.Sound.ORB_PICKUP, 2.0F, 1.7F);
	  				return;
				}
				ItemList.set(number+"."+Type,Integer.parseInt(Message));
			}
		}
		else if(SayType.compareTo("MinDamage")==0)
		{
			if(isIntMinMax(Message, player, 0, Integer.MAX_VALUE))
			{
				ItemList.set(number+"."+Type,Integer.parseInt(Message));
				ItemList.saveConfig();
				u.setType(player, u.getType(player));
				u.setString(player, (byte)1, "MaxDamage");
				player.sendMessage(ChatColor.DARK_AQUA+"[������] : �������� �ִ� "+GoldBigDragon_RPG.Main.ServerOption.Damage+"�� �Է��� �ּ���!");
				player.sendMessage(ChatColor.DARK_AQUA+"(0 ~ "+Integer.MAX_VALUE+")");
			}
			return;
		}
		else if(SayType.compareTo("MinMaDamage")==0)
		{
			if(isIntMinMax(Message, player, 0, Integer.MAX_VALUE))
			{
				ItemList.set(number+"."+Type,Integer.parseInt(Message));
				ItemList.saveConfig();
				u.setType(player, u.getType(player));
				u.setString(player, (byte)1, "MaxMaDamage");
				player.sendMessage(ChatColor.DARK_AQUA+"[������] : �������� �ִ� "+GoldBigDragon_RPG.Main.ServerOption.MagicDamage+"�� �Է��� �ּ���!");
				player.sendMessage(ChatColor.DARK_AQUA+"(0 ~ "+Integer.MAX_VALUE+")");
			}
			return;
		}
		else if(SayType.compareTo("MaxDurability")==0)
		{
			if(isIntMinMax(Message, player, 0, Integer.MAX_VALUE))
			{
				ItemList.set(number+"."+Type,Integer.parseInt(Message));
				ItemList.saveConfig();
				u.setType(player, u.getType(player));
				u.setString(player, (byte)1, "Durability");
				player.sendMessage(ChatColor.DARK_AQUA+"[������] : �������� ���� �������� �Է��� �ּ���!");
				player.sendMessage(ChatColor.DARK_AQUA+"(0 ~ "+ItemList.getInt(number+".MaxDurability")+")");
			}
			return;
		}
		else if(SayType.compareTo("HP")==0)
		{
			if(isIntMinMax(Message, player, Integer.MIN_VALUE, Integer.MAX_VALUE))
			{
				ItemList.set(number+"."+Type,Integer.parseInt(Message));
				ItemList.saveConfig();
				if(u.getInt(player, (byte)4) != -1)
				{
					if(u.getInt(player, (byte)4) == -8)
					{
						GoldBigDragon_RPG.CustomItem.UseableItemGUI UGUI = new GoldBigDragon_RPG.CustomItem.UseableItemGUI();
						UGUI.NewUseableItemGUI(player, number);
						u.clearAll(player);
						return;
					}
					else
					{
						u.setType(player, u.getType(player));
						u.setString(player, (byte)1, "MP");
						player.sendMessage(ChatColor.DARK_AQUA+"[������] : �������� ���ʽ� ������ �Է��� �ּ���!");
						player.sendMessage(ChatColor.DARK_AQUA+"(-127 ~ 127)");
						return;
					}
				}
				else
				{
					u.setType(player, u.getType(player));
					u.setString(player, (byte)1, "MP");
					player.sendMessage(ChatColor.DARK_AQUA+"[������] : �������� ���ʽ� ������ �Է��� �ּ���!");
					player.sendMessage(ChatColor.DARK_AQUA+"(-127 ~ 127)");
				}
			}
			return;
		}
		else if(SayType.compareTo("MP")==0)
		{
			if(isIntMinMax(Message, player, Integer.MIN_VALUE, Integer.MAX_VALUE))
			{
				ItemList.set(number+"."+Type,Integer.parseInt(Message));
				ItemList.saveConfig();
				if(u.getInt(player, (byte)4) != -1)
				{
					if(u.getInt(player, (byte)4) == -8)
					{
						GoldBigDragon_RPG.CustomItem.UseableItemGUI UGUI = new GoldBigDragon_RPG.CustomItem.UseableItemGUI();
						UGUI.NewUseableItemGUI(player, number);
						u.clearAll(player);
						return;
					}
					else
					{
						u.setType(player, u.getType(player));
						u.setString(player, (byte)1, "STR");
						player.sendMessage(ChatColor.DARK_AQUA+"[������] : �������� ���ʽ� "+GoldBigDragon_RPG.Main.ServerOption.STR+"�� �Է��� �ּ���!");
						player.sendMessage(ChatColor.DARK_AQUA+"(-127 ~ 127)");
						return;
					}
				}
				else
				{
					u.setType(player, u.getType(player));
					u.setString(player, (byte)1, "STR");
					player.sendMessage(ChatColor.DARK_AQUA+"[������] : �������� ���ʽ� "+GoldBigDragon_RPG.Main.ServerOption.STR+"�� �Է��� �ּ���!");
					player.sendMessage(ChatColor.DARK_AQUA+"(-127 ~ 127)");
					return;
				}
			}
			return;
		}
		else if(SayType.compareTo("STR")==0||SayType.compareTo("DEX")==0||SayType.compareTo("INT")==0||SayType.compareTo("WILL")==0||
				SayType.compareTo("LUK")==0||SayType.compareTo("Balance")==0)
		{
			if(isIntMinMax(Message, player, -127, 127))
			{
				ItemList.set(number+"."+Type,Integer.parseInt(Message));
				ItemList.saveConfig();
				u.setType(player, u.getType(player));
				if(SayType.compareTo("STR")==0)
				{
					u.setString(player, (byte)1, "DEX");
					player.sendMessage(ChatColor.DARK_AQUA+"[������] : �������� ���ʽ� "+GoldBigDragon_RPG.Main.ServerOption.DEX+"�� �Է��� �ּ���!");
				}
				else if(SayType.compareTo("DEX")==0)
				{
					u.setString(player, (byte)1, "INT");
					player.sendMessage(ChatColor.DARK_AQUA+"[������] : �������� ���ʽ� "+GoldBigDragon_RPG.Main.ServerOption.INT+"�� �Է��� �ּ���!");
				}
				else if(SayType.compareTo("INT")==0)
				{
					u.setString(player, (byte)1, "WILL");
					player.sendMessage(ChatColor.DARK_AQUA+"[������] : �������� ���ʽ� "+GoldBigDragon_RPG.Main.ServerOption.WILL+"�� �Է��� �ּ���!");
				}
				else if(SayType.compareTo("WILL")==0)
				{
					u.setString(player, (byte)1, "LUK");
					player.sendMessage(ChatColor.DARK_AQUA+"[������] : �������� ���ʽ� "+GoldBigDragon_RPG.Main.ServerOption.LUK+"�� �Է��� �ּ���!");
				}
				else if(SayType.compareTo("LUK")==0)
				{
					u.setString(player, (byte)1, "Balance");
					player.sendMessage(ChatColor.DARK_AQUA+"[������] : �������� �뷱���� �Է��� �ּ���!");
				}
				else if(SayType.compareTo("Balance")==0)
				{
					u.setString(player, (byte)1, "Critical");
					player.sendMessage(ChatColor.DARK_AQUA+"[������] : �������� ũ��Ƽ���� �Է��� �ּ���!");
				}
				player.sendMessage(ChatColor.DARK_AQUA+"(-127 ~ 127)");
			}
			return;
		}
		else if(SayType.compareTo("Critical")==0)
		{
			if(isIntMinMax(Message, player, -127, 127))
			{
				ItemList.set(number+"."+Type,Integer.parseInt(Message));
				ItemList.saveConfig();
				s.SP(player, Sound.HORSE_SADDLE, 1.0F, 1.8F);
				if(u.getType(player).compareTo("UseableItem")==0)
				{
					GoldBigDragon_RPG.CustomItem.UseableItemGUI UGUI = new GoldBigDragon_RPG.CustomItem.UseableItemGUI();
					UGUI.NewUseableItemGUI(player, number);
				}
				else
					IGUI.NewItemGUI(player, number);
				u.clearAll(player);
			}
			return;
		}
		else if(SayType.compareTo("Saturation")==0 ||SayType.compareTo("SkillPoint")==0 ||SayType.compareTo("StatPoint")==0 ||
				SayType.compareTo("Data")==0 ||SayType.compareTo("DEF")==0 ||SayType.compareTo("Protect")==0 ||SayType.compareTo("MaDEF")==0 ||
				SayType.compareTo("MaProtect")==0 ||SayType.compareTo("MaxUpgrade")==0 ||SayType.compareTo("MaxDamage")==0 ||SayType.compareTo("MaxMaDamage")==0 ||
				SayType.compareTo("Durability")==0)
		{
			if(isIntMinMax(Message, player, 0, Integer.MAX_VALUE))
				ItemList.set(number+"."+Type,Integer.parseInt(Message));
		}
		else if(SayType.compareTo("NUR")==0)//NewUpgradeRecipe
		{
			Message = Message.replace(".", "");
			if(RecipeList.contains(Message)==true)
			{
				player.sendMessage(ChatColor.RED+"[����] : �ش� �̸��� �������� �̹� �����մϴ�!");
				s.SP(player, Sound.ORB_PICKUP, 1.0F, 1.8F);
				return;
			}
			RecipeList.set(Message+".Lore", ChatColor.WHITE+"������ ���� �ٵ�� �������̴�.%enter%"+ChatColor.WHITE+"���� �ٵ��� ����� ��������%enter%"+ChatColor.WHITE+"����������, �������̴�.");
			RecipeList.set(Message+".Only",ChatColor.RED+ "[���� ����]");
			RecipeList.set(Message+".MaxDurability", -50);
			RecipeList.set(Message+".MinDamage", 1);
			RecipeList.set(Message+".MaxDamage", 8);
			RecipeList.set(Message+".MinMaDamage", 0);
			RecipeList.set(Message+".MaxMaDamage", 0);
			RecipeList.set(Message+".DEF", 0);
			RecipeList.set(Message+".Protect", 0);
			RecipeList.set(Message+".MaDEF", 0);
			RecipeList.set(Message+".MaProtect", 0);
			RecipeList.set(Message+".Critical", 2);
			RecipeList.set(Message+".Balance", 0);
			RecipeList.set(Message+".UpgradeAbleLevel", 0);
			RecipeList.set(Message+".DecreaseProficiency",30);
			RecipeList.saveConfig();
			s.SP(player, Sound.HORSE_ARMOR, 1.0F, 1.8F);
			UpGUI.UpgradeRecipeSettingGUI(player, Message);
			u.clearAll(player);
			return;
		}
		else if(SayType.compareTo("UMinD")==0)//UpgradeMinDamage
		{
			if(isIntMinMax(Message, player, Integer.MIN_VALUE, Integer.MAX_VALUE))
			{
				RecipeList.set(u.getString(player, (byte)6)+".MinDamage", Integer.parseInt(Message));
				RecipeList.saveConfig();
				s.SP(player, Sound.ITEM_PICKUP, 0.8F, 1.0F);
				u.setType(player, u.getType(player));
				u.setString(player, (byte)1, "UMaxD");
				player.sendMessage(ChatColor.DARK_AQUA+"[����] : ��ȭ�� �ִ� ���ݷ� ��ġ�� �Է��ϼ���!");
				player.sendMessage(ChatColor.GREEN + "("+ChatColor.YELLOW + Integer.MIN_VALUE+ChatColor.GREEN+" ~ "+ChatColor.YELLOW+""+Integer.MAX_VALUE+ChatColor.GREEN+")");
			}
			return;
		}
		else if(SayType.compareTo("UMaxD")==0)//UpgradeMaxDamage
		{
			if(isIntMinMax(Message, player, Integer.MIN_VALUE, Integer.MAX_VALUE))
			{
				RecipeList.set(u.getString(player, (byte)6)+".MaxDamage", Integer.parseInt(Message));
				RecipeList.saveConfig();
				UpGUI.UpgradeRecipeSettingGUI(player, u.getString(player, (byte)6));
				u.clearAll(player);
				s.SP(player, Sound.HORSE_SADDLE, 1.0F, 1.8F);
			}
			return;
		}
		else if(SayType.compareTo("UMMinD")==0)//UpgradeMagicMinDamage
		{
			if(isIntMinMax(Message, player, Integer.MIN_VALUE, Integer.MAX_VALUE))
			{
				RecipeList.set(u.getString(player, (byte)6)+".MinMaDamage", Integer.parseInt(Message));
				RecipeList.saveConfig();
				s.SP(player, Sound.ITEM_PICKUP, 0.8F, 1.0F);
				u.setType(player, u.getType(player));
				u.setString(player, (byte)1, "UMMaxD");
				player.sendMessage(ChatColor.DARK_AQUA+"[����] : ��ȭ�� �ִ� ���� ���ݷ� ��ġ�� �Է��ϼ���!");
				player.sendMessage(ChatColor.GREEN + "("+ChatColor.YELLOW + Integer.MIN_VALUE+ChatColor.GREEN+" ~ "+ChatColor.YELLOW+""+Integer.MAX_VALUE+ChatColor.GREEN+")");
			}
			return;
		}
		else if(SayType.compareTo("UMMaxD")==0)//UpgradeMagicMaxDamage
		{
			if(isIntMinMax(Message, player, Integer.MIN_VALUE, Integer.MAX_VALUE))
			{
				RecipeList.set(u.getString(player, (byte)6)+".MaxMaDamage", Integer.parseInt(Message));
				RecipeList.saveConfig();
				UpGUI.UpgradeRecipeSettingGUI(player, u.getString(player, (byte)6));
				u.clearAll(player);
				s.SP(player, Sound.HORSE_SADDLE, 1.0F, 1.8F);
			}
			return;
		}
		else if(SayType.compareTo("UB")==0)//UpgradeBalance
		{
			if(isIntMinMax(Message, player, Integer.MIN_VALUE, Integer.MAX_VALUE))
			{
				RecipeList.set(u.getString(player, (byte)6)+".Balance", Integer.parseInt(Message));
				RecipeList.saveConfig();
				UpGUI.UpgradeRecipeSettingGUI(player, u.getString(player, (byte)6));
				u.clearAll(player);
				s.SP(player, Sound.HORSE_SADDLE, 1.0F, 1.8F);
			}
			return;
		}
		else if(SayType.compareTo("UDEF")==0)//UpgradeDefense
		{
			if(isIntMinMax(Message, player, Integer.MIN_VALUE, Integer.MAX_VALUE))
			{
				RecipeList.set(u.getString(player, (byte)6)+".DEF", Integer.parseInt(Message));
				RecipeList.saveConfig();
				UpGUI.UpgradeRecipeSettingGUI(player, u.getString(player, (byte)6));
				u.clearAll(player);
				s.SP(player, Sound.HORSE_SADDLE, 1.0F, 1.8F);
			}
			return;
		}
		else if(SayType.compareTo("UP")==0)//UpgradeProtect
		{
			if(isIntMinMax(Message, player, Integer.MIN_VALUE, Integer.MAX_VALUE))
			{
				RecipeList.set(u.getString(player, (byte)6)+".Protect", Integer.parseInt(Message));
				RecipeList.saveConfig();
				UpGUI.UpgradeRecipeSettingGUI(player, u.getString(player, (byte)6));
				u.clearAll(player);
				s.SP(player, Sound.HORSE_SADDLE, 1.0F, 1.8F);
			}
			return;
		}
		else if(SayType.compareTo("UMDEF")==0)//UpgradeMagicDefense
		{
			if(isIntMinMax(Message, player, Integer.MIN_VALUE, Integer.MAX_VALUE))
			{
				RecipeList.set(u.getString(player, (byte)6)+".MaDEF", Integer.parseInt(Message));
				RecipeList.saveConfig();
				UpGUI.UpgradeRecipeSettingGUI(player, u.getString(player, (byte)6));
				u.clearAll(player);
				s.SP(player, Sound.HORSE_SADDLE, 1.0F, 1.8F);
			}
			return;
		}
		else if(SayType.compareTo("UMP")==0)//UpgradeMagicProtect
		{
			if(isIntMinMax(Message, player, Integer.MIN_VALUE, Integer.MAX_VALUE))
			{
				RecipeList.set(u.getString(player, (byte)6)+".MaProtect", Integer.parseInt(Message));
				RecipeList.saveConfig();
				UpGUI.UpgradeRecipeSettingGUI(player, u.getString(player, (byte)6));
				u.clearAll(player);
				s.SP(player, Sound.HORSE_SADDLE, 1.0F, 1.8F);
			}
			return;
		}
		else if(SayType.compareTo("UC")==0)//UpgradeCritical
		{
			if(isIntMinMax(Message, player, Integer.MIN_VALUE, Integer.MAX_VALUE))
			{
				RecipeList.set(u.getString(player, (byte)6)+".Critical", Integer.parseInt(Message));
				RecipeList.saveConfig();
				UpGUI.UpgradeRecipeSettingGUI(player, u.getString(player, (byte)6));
				u.clearAll(player);
				s.SP(player, Sound.HORSE_SADDLE, 1.0F, 1.8F);
			}
			return;
		}
		else if(SayType.compareTo("UMD")==0)//UpgradeMaxDurability
		{
			if(isIntMinMax(Message, player, Integer.MIN_VALUE, Integer.MAX_VALUE))
			{
				RecipeList.set(u.getString(player, (byte)6)+".MaxDurability", Integer.parseInt(Message));
				RecipeList.saveConfig();
				UpGUI.UpgradeRecipeSettingGUI(player, u.getString(player, (byte)6));
				u.clearAll(player);
				s.SP(player, Sound.HORSE_SADDLE, 1.0F, 1.8F);
			}
			return;
		}
		else if(SayType.compareTo("UUL")==0)//UpgradeUpgradeLevel
		{
			if(isIntMinMax(Message, player, 0, Integer.MAX_VALUE))
			{
				RecipeList.set(u.getString(player, (byte)6)+".UpgradeAbleLevel", Integer.parseInt(Message));
				RecipeList.saveConfig();
				UpGUI.UpgradeRecipeSettingGUI(player, u.getString(player, (byte)6));
				u.clearAll(player);
				s.SP(player, Sound.HORSE_SADDLE, 1.0F, 1.8F);
			}
			return;
		}
		else if(SayType.compareTo("UDP")==0)//UpgradeDecreaseProficiency
		{
			if(isIntMinMax(Message, player, 0, 100))
			{
				RecipeList.set(u.getString(player, (byte)6)+".DecreaseProficiency", Integer.parseInt(Message));
				RecipeList.saveConfig();
				UpGUI.UpgradeRecipeSettingGUI(player, u.getString(player, (byte)6));
				u.clearAll(player);
				s.SP(player, Sound.HORSE_SADDLE, 1.0F, 1.8F);
			}
			return;
		}
		else if(SayType.compareTo("ULC")==0)//Upgrade Lore Change
		{
			RecipeList.set(u.getString(player, (byte)6)+".Lore", event.getMessage());
			RecipeList.saveConfig();
			UpGUI.UpgradeRecipeSettingGUI(player, u.getString(player, (byte)6));
			u.clearAll(player);
			s.SP(player, Sound.HORSE_SADDLE, 1.0F, 1.8F);
			return;
		}
		else if(SayType.compareTo("MinSTR")==0||SayType.compareTo("MinDEX")==0||SayType.compareTo("MinINT")==0||SayType.compareTo("MinWILL")==0||
				SayType.compareTo("MinLV")==0||SayType.compareTo("MinRLV")==0||SayType.compareTo("MinLUK")==0)
		{
			if(isIntMinMax(Message, player, 0, Integer.MAX_VALUE))
			{
				ItemList.set(number+"."+SayType, Integer.parseInt(Message));
				ItemList.saveConfig();
				u.setType(player, u.getType(player));
				if(SayType.compareTo("MinLV")==0)
				{
					u.setString(player, (byte)1, "MinRLV");
					player.sendMessage(ChatColor.DARK_AQUA+"[������] : �������� �������� ������ �Է� �� �ּ���!");
				}
				else if(SayType.compareTo("MinRLV")==0)
				{
					u.setString(player, (byte)1, "MinSTR");
					player.sendMessage(ChatColor.DARK_AQUA+"[������] : �������� "+GoldBigDragon_RPG.Main.ServerOption.STR+" ������ �Է� �� �ּ���!");
				}
				else if(SayType.compareTo("MinSTR")==0)
				{
					u.setString(player, (byte)1, "MinDEX");
					player.sendMessage(ChatColor.DARK_AQUA+"[������] : �������� "+GoldBigDragon_RPG.Main.ServerOption.DEX+" ������ �Է� �� �ּ���!");
				}
				else if(SayType.compareTo("MinDEX")==0)
				{
					u.setString(player, (byte)1, "MinINT");
					player.sendMessage(ChatColor.DARK_AQUA+"[������] : �������� "+GoldBigDragon_RPG.Main.ServerOption.INT+" ������ �Է� �� �ּ���!");
				}
				else if(SayType.compareTo("MinINT")==0)
				{
					u.setString(player, (byte)1, "MinWILL");
					player.sendMessage(ChatColor.DARK_AQUA+"[������] : �������� "+GoldBigDragon_RPG.Main.ServerOption.WILL+" ������ �Է� �� �ּ���!");
				}
				else if(SayType.compareTo("MinWILL")==0)
				{
					u.setString(player, (byte)1, "MinLUK");
					player.sendMessage(ChatColor.DARK_AQUA+"[������] : �������� "+GoldBigDragon_RPG.Main.ServerOption.LUK+" ������ �Է� �� �ּ���!");
				}
				if(SayType.compareTo("MinLUK")==0)
				{
					s.SP(player, Sound.HORSE_SADDLE, 1.0F, 1.8F);
					IGUI.NewItemGUI(player, number);
					u.clearAll(player);
				}
				else
				{
					s.SP(player, Sound.ITEM_PICKUP, 1.0F, 1.8F);
					player.sendMessage(ChatColor.DARK_AQUA+"(0 ~ "+Integer.MAX_VALUE+")");
				}
			}
			return;
		}
		ItemList.saveConfig();
		s.SP(player, Sound.HORSE_SADDLE, 1.0F, 1.8F);
		if(u.getType(player).compareTo("UseableItem")==0)
			new GoldBigDragon_RPG.CustomItem.UseableItemGUI().NewUseableItemGUI(player, number);
		else
			IGUI.NewItemGUI(player, number);
		u.clearAll(player);
		return;
	}

	private void AreaTypeChatting(PlayerChatEvent event)
	{
		Object_UserData u = new Object_UserData();
		GoldBigDragon_RPG.Effect.Sound s = new GoldBigDragon_RPG.Effect.Sound();
		Player player = event.getPlayer();
	  	YamlController YC = new YamlController(GoldBigDragon_RPG.Main.Main.plugin);
		YamlManager AreaConfig =YC.getNewConfig("Area/AreaList.yml");
		event.setCancelled(true);
		GoldBigDragon_RPG.GUI.AreaGUI AGUI = new GoldBigDragon_RPG.GUI.AreaGUI();
		String Message = ChatColor.stripColor(event.getMessage());
		switch(u.getString(player, (byte)2))
		{
			case "ARR"://AreaRegenBlock
				if(isIntMinMax(Message, player, 1, 3600))
				{
					AreaConfig.set(u.getString(player, (byte)3)+".RegenBlock", Integer.parseInt(Message));
	    			AreaConfig.saveConfig();
	    			s.SP(player, Sound.ITEM_PICKUP, 1.0F, 1.0F);
					AGUI.AreaGUI_Main(player, u.getString(player, (byte)3));
	    			u.clearAll(player);
				}
				return;
			case "AMSC"://AreaMonsterSpawnCount
				if(isIntMinMax(Message, player, 1, 100))
				{
					AreaConfig.set(u.getString(player, (byte)3)+".MonsterSpawnRule."+u.getString(player, (byte)1)+".count", Integer.parseInt(Message));
	    			AreaConfig.saveConfig();
	    			s.SP(player, Sound.ITEM_PICKUP, 1.0F, 1.0F);
					u.setString(player, (byte)2, "AMSMC");
					player.sendMessage(ChatColor.GREEN+"[����] : �ݰ� 20��� �̳� ��ƼƼ�� �� ���� �̸��� ���� ���� �ұ��?");
					player.sendMessage(ChatColor.YELLOW+"(�ּ� 1���� ~ �ִ� 300����)");
				}
				return;
			case "AMSMC"://AreaMonsterSpawnMaximumCount
				if(isIntMinMax(Message, player, 1, 300))
				{
					AreaConfig.set(u.getString(player, (byte)3)+".MonsterSpawnRule."+u.getString(player, (byte)1)+".max", Integer.parseInt(Message));
	    			AreaConfig.saveConfig();
	    			s.SP(player, Sound.ITEM_PICKUP, 1.0F, 1.0F);
					u.setString(player, (byte)2, "AMST");
					player.sendMessage(ChatColor.GREEN+"[����] : �� �ʸ��� �����ǰ� �ұ��?");
					player.sendMessage(ChatColor.YELLOW+"(�ּ� 1�� ~ �ִ� 7200��(2�ð�))");
				}
				return;
			case "AMST"://AreaMonsterSpawnTimer
				if(isIntMinMax(Message, player, 1, 7200))
				{
					AreaConfig.set(u.getString(player, (byte)3)+".MonsterSpawnRule."+u.getString(player, (byte)1)+".timer", Integer.parseInt(Message));
	    			AreaConfig.saveConfig();
	    			s.SP(player, Sound.ITEM_PICKUP, 1.0F, 1.0F);
	    			/*
					u.setString(player, (byte)2, "AMSR");
					player.sendMessage(ChatColor.GREEN+"[����] : ���� ���� '����'�� �÷��̾ �ݰ� �� ��� �̳��� ��� �� �� ���� ������ ���� �ұ��?");
					player.sendMessage(ChatColor.YELLOW+"(�ּ� 1��� ~ �ִ� 1000���)");
					*/
	    			u.setString(player, (byte)2, "AMSM");
					player.sendMessage(ChatColor.GREEN+"[����] : Ư���� ���� �ϰ� ���� ���Ͱ� �ֳ���?");
					player.sendMessage(ChatColor.YELLOW+"(O Ȥ�� X�� ����ϼ���!)");
				}
				return;
				/*
			case "AMSR"://AreaMonsterSpawnRange
				if(isIntMinMax(Message, player, 1, 1000))
				{
					AreaConfig.set(u.getString(player, (byte)3)+".MonsterSpawnRule."+u.getString(player, (byte)1)+".range", Integer.parseInt(Message));
	    			AreaConfig.saveConfig();
	    			s.SP(player, Sound.ITEM_PICKUP, 1.0F, 1.0F);
					u.setString(player, (byte)2, "AMSM");
					player.sendMessage(ChatColor.GREEN+"[����] : Ư���� ���� �ϰ� ���� ���Ͱ� �ֳ���?");
					player.sendMessage(ChatColor.YELLOW+"(O Ȥ�� X�� ����ϼ���!)");
				}
				return;
				*/
			case "AMSM"://AreaMonsterSpawnMonster
				byte answer = askOX(Message, player);
				if(answer!=-1)
				{
					if(answer==0)
					{
		    			s.SP(player, Sound.ANVIL_LAND, 1.0F, 1.0F);
		    			AGUI.AreaMonsterSpawnSettingGUI(player, (short) 0, u.getString(player, (byte)3));
		    			String AreaName =u.getString(player, (byte)3);
		    			new GoldBigDragon_RPG.ETC.Area().AreaMonsterSpawnAdd(AreaName, u.getString(player, (byte)1));
					}
					else
					{
						s.SP(player, Sound.HORSE_ARMOR, 1.0F, 1.7F);
						AGUI.AreaSpawnSpecialMonsterListGUI(player, (short) 0, u.getString(player, (byte)3),u.getString(player, (byte)1));
					}
	    			u.clearAll(player);
				}
				return;
			case "Priority"://���� �켱���� ����
				if(isIntMinMax(Message, player, 0, 100))
				{
	    			AreaConfig.set(u.getString(player, (byte)3)+".Priority", Integer.parseInt(Message));
	    			AreaConfig.saveConfig();
	    			s.SP(player, Sound.ITEM_PICKUP, 1.0F, 1.0F);
	    			AGUI.AreaGUI_Main(player, u.getString(player, (byte)3));
	    			u.clearAll(player);
				}
				return;
		}
		return;
	}

	private void NPCTypeChatting(PlayerChatEvent event)
	{
		Object_UserData u = new Object_UserData();
		GoldBigDragon_RPG.Effect.Sound sound = new GoldBigDragon_RPG.Effect.Sound();
		Player player = event.getPlayer();

		String NPCuuid = u.getString(player, (byte)3);
	  	YamlController YC = new YamlController(GoldBigDragon_RPG.Main.Main.plugin);
		
	  	if(YC.isExit("NPC/NPCData/"+ NPCuuid +".yml") == false)
	  	{
	  		GoldBigDragon_RPG.Config.NPCconfig NPCC = new GoldBigDragon_RPG.Config.NPCconfig();
	  		NPCC.NPCNPCconfig(NPCuuid);
	  	}
		YamlManager NPCscript = YC.getNewConfig("NPC/NPCData/"+ NPCuuid +".yml");
		GoldBigDragon_RPG.GUI.NPC_GUI NPGUI = new GoldBigDragon_RPG.GUI.NPC_GUI();
		event.setCancelled(true);
		String Message = ChatColor.stripColor(event.getMessage());
		switch(u.getString(player, (byte)4))
		{
		case "SaleSetting1":
			if(isIntMinMax(Message, player, -1000, 1000))
			{
				u.setInt(player, (byte)0, Integer.parseInt(Message));
				sound.SP(player, Sound.ITEM_PICKUP, 1.0F, 1.8F);
				u.setString(player, (byte)4,"SaleSetting2");
				player.sendMessage(ChatColor.DARK_AQUA + "[NPC] : �� % ������ �Ͻǰǰ���? (0 ~ 100 ���� ��)");
			}
			return;
		case "SaleSetting2":
			if(isIntMinMax(Message, player, 0, 100))
			{
				YamlManager NPCConfig =YC.getNewConfig("NPC/NPCData/"+u.getNPCuuid(player)+".yml");
				NPCConfig.set("Sale.Enable", true);
				NPCConfig.set("Sale.Minlove", u.getInt(player, (byte)0));
				NPCConfig.set("Sale.discount", Integer.parseInt(Message));
				NPCConfig.saveConfig();
				new GoldBigDragon_RPG.GUI.NPC_GUI().MainGUI(player, u.getString(player, (byte)2), player.isOp());
				sound.SP(player, Sound.ITEM_PICKUP, 1.0F, 1.8F);
				u.clearAll(player);
			}
			return;
		case "PresentLove":
			if(isIntMinMax(Message, player, -1000, 1000))
			{
				YamlManager NPCConfig =YC.getNewConfig("NPC/NPCData/"+u.getNPCuuid(player)+".yml");
				NPCConfig.set("Present."+u.getInt(player, (byte)0)+".love", Integer.parseInt(Message));
				NPCConfig.saveConfig();
				new GoldBigDragon_RPG.GUI.NPC_GUI().PresentSettingGUI(player, u.getString(player, (byte)2));
				sound.SP(player, Sound.ITEM_PICKUP, 1.0F, 1.8F);
				u.clearAll(player);
			}
			return;
		case "NUC"://NPC'sUpgradeCost
			if(isIntMinMax(Message, player, 0, Integer.MAX_VALUE))
			{
				YamlManager NPCConfig =YC.getNewConfig("NPC/NPCData/"+u.getNPCuuid(player)+".yml");
				NPCConfig.set("Job.UpgradeRecipe."+u.getString(player, (byte)6),  Integer.parseInt(Message));
				NPCConfig.saveConfig();
				GoldBigDragon_RPG.GUI.NPC_GUI NGUI = new GoldBigDragon_RPG.GUI.NPC_GUI();
				NGUI.UpgraderGUI(player, (short) 0, u.getString(player, (byte)8));
				sound.SP(player, Sound.HORSE_SADDLE, 1.0F, 1.8F);
				u.clearAll(player);
			}
			return;
		case "NPC_TNL"://NPC_TalkNeedLove
			if(isIntMinMax(Message, player, 0, Integer.MAX_VALUE))
			{
				switch(u.getString(player, (byte)5))
				{
				case "NT":
					NPCscript.set("NatureTalk."+u.getString(player, (byte)6)+".love", Integer.parseInt(Message));
					break;
				case "NN":
					NPCscript.set("NearByNEWS."+u.getString(player, (byte)6)+".love", Integer.parseInt(Message));
					break;
				case "AS":
					NPCscript.set("AboutSkills."+u.getString(player, (byte)6)+".love", Integer.parseInt(Message));
					break;
				}
				NPCscript.saveConfig();
				sound.SP(player, Sound.ITEM_PICKUP, 1.0F, 1.0F);
				player.closeInventory();
				player.sendMessage(ChatColor.DARK_AQUA+"[���] : �׷��ٸ� �ִ� ���� ȣ������ �ʿ��Ѱ���?");
				player.sendMessage(ChatColor.GREEN + "("+ChatColor.YELLOW + "0"+ChatColor.GREEN+" ~ "+ChatColor.YELLOW+""+Integer.MAX_VALUE+ChatColor.GREEN+")");
				u.setType(player, "NPC");
				u.setString(player, (byte)4,"NPC_TNL2");
			}
			return;
		case "NPC_TNL2"://NPC_TalkNeedLove
			if(isIntMinMax(Message, player, 0, Integer.MAX_VALUE))
			{
				switch(u.getString(player, (byte)5))
				{
				case "NT":
					NPCscript.set("NatureTalk."+u.getString(player, (byte)6)+".loveMax", Integer.parseInt(Message));
					break;
				case "NN":
					NPCscript.set("NearByNEWS."+u.getString(player, (byte)6)+".loveMax", Integer.parseInt(Message));
					break;
				case "AS":
					NPCscript.set("AboutSkills."+u.getString(player, (byte)6)+".loveMax", Integer.parseInt(Message));
					break;
				}
				NPCscript.saveConfig();
				sound.SP(player, Sound.ITEM_PICKUP, 1.0F, 1.0F);
				NPGUI.TalkSettingGUI(player, u.getString(player, (byte)2), u.getString(player, (byte)5), (short) Integer.parseInt(u.getString(player, (byte)6)));
				u.clearAll(player);
			}
			return;
		case "NPC_TS"://NPC_TalkScript
			switch(u.getString(player, (byte)5))
			{
			case "NT":
				NPCscript.set("NatureTalk."+u.getString(player, (byte)6)+".Script",event.getMessage());
				break;
			case "NN":
				NPCscript.set("NearByNEWS."+u.getString(player, (byte)6)+".Script", event.getMessage());
				break;
			case "AS":
				NPCscript.set("AboutSkills."+u.getString(player, (byte)6)+".Script",event.getMessage());
				break;
			}
			NPCscript.saveConfig();
			NPGUI.TalkSettingGUI(player, u.getString(player, (byte)2), u.getString(player, (byte)5), (short) Integer.parseInt(u.getString(player, (byte)6)));

			u.clearAll(player);
			return;
		case "NPC_TS2"://NPC_TalkScript2
			switch(u.getString(player, (byte)5))
			{
			case "AS":
				NPCscript.set("AboutSkills."+u.getString(player, (byte)6)+".AlreadyGetScript",event.getMessage());
				break;
			}
			NPCscript.saveConfig();
			NPGUI.TalkSettingGUI(player, u.getString(player, (byte)2), u.getString(player, (byte)5), (short) Integer.parseInt(u.getString(player, (byte)6)));

			u.clearAll(player);
			return;
		case "WDN"://WarpDisplayName
			NPCscript.set("Job.WarpList."+u.getInt(player, (byte)4)+".DisplayName",event.getMessage());
			NPCscript.saveConfig();
			player.sendMessage(ChatColor.DARK_AQUA + "[NPC] : "+ChatColor.YELLOW+event.getMessage()+ChatColor.DARK_AQUA+" ���� ������ �̵� ����� �Է� �ϼ���!");
			u.setString(player, (byte)4,"WC");
			return;
		case "WC"://WarpCost
			if(isIntMinMax(Message, player, 0, Integer.MAX_VALUE))
			{
				NPCscript.set("Job.WarpList."+u.getInt(player, (byte)4)+".Cost",Integer.parseInt(ChatColor.stripColor(event.getMessage())));
				NPCscript.saveConfig();
				NPGUI.WarpMainGUI(player, 0, u.getString(player, (byte)2));

				u.clearAll(player);
			}
			return;
		case "FixRate":
			if(isIntMinMax(Message, player, 1, 100))
			{
				NPCscript.set("Job.FixRate",Integer.parseInt(ChatColor.stripColor(event.getMessage())));
				NPCscript.saveConfig();
				player.sendMessage(ChatColor.DARK_AQUA + "[NPC] : �� NPC�� ���� �������� "+ChatColor.WHITE+ChatColor.stripColor(event.getMessage())+"%"+ChatColor.DARK_AQUA+"�� �Ǿ����ϴ�!");
				player.sendMessage(ChatColor.DARK_AQUA + "[NPC] : NPC�� ������ ������ 10����Ʈ�� ���� ����� �Է� �ϼ���! ");

				u.setType(player, "NPC");
				u.setString(player, (byte)4, "10Point");
			}
			return;
		case "10Point":
			if(isIntMinMax(Message, player, 0, Integer.MAX_VALUE))
			{
				NPCscript.set("Job.10PointFixDeal",Integer.parseInt(ChatColor.stripColor(event.getMessage())));
				NPCscript.saveConfig();
				NPGUI.MainGUI(player, u.getString(player, (byte)2), player.isOp());

				u.clearAll(player);
			}
			return;
		case "GoodRate":
			if(isIntMinMax(Message, player, 1, 100))
			{
				NPCscript.set("Job.GoodRate",Integer.parseInt(ChatColor.stripColor(event.getMessage())));
				NPCscript.saveConfig();
				player.sendMessage(ChatColor.DARK_AQUA + "[NPC] : �� NPC�� ���� �������� "+ChatColor.WHITE+ChatColor.stripColor(event.getMessage())+"%"+ChatColor.DARK_AQUA+"�� �Ǿ����ϴ�!");
				player.sendMessage(ChatColor.DARK_AQUA + "[NPC] : NPC�� �ִ� ���� ���⸦ ������ �ּ���. ");

				u.setType(player, "NPC");
				u.setString(player, (byte)4, "BuffMaxStrog");
				
			}
			return;
		case "BuffMaxStrog":
			if(isIntMinMax(Message, player, 1,100))
			{
				NPCscript.set("Job.BuffMaxStrog",Integer.parseInt(ChatColor.stripColor(event.getMessage())));
				NPCscript.saveConfig();
				player.sendMessage(ChatColor.DARK_AQUA + "[NPC] : �� NPC�� �ִ� ���� ����� "+ChatColor.WHITE+ChatColor.stripColor(event.getMessage())+ChatColor.DARK_AQUA+"�� �Ǿ����ϴ�!");
				player.sendMessage(ChatColor.DARK_AQUA + "[NPC] : NPC�� ���� �ð��� ������ �ּ���. (�� ����)");

				u.setType(player, "NPC");
				u.setString(player, (byte)4, "BuffTime");
			}
			return;
		case "BuffTime":
			if(isIntMinMax(Message, player, 1, Integer.MAX_VALUE))
			{
				NPCscript.set("Job.BuffTime",Integer.parseInt(ChatColor.stripColor(event.getMessage())));
				NPCscript.saveConfig();
				player.sendMessage(ChatColor.DARK_AQUA + "[NPC] : �� NPC�� �ִ� ���� �ð��� "+ChatColor.WHITE+ChatColor.stripColor(event.getMessage())+ChatColor.DARK_AQUA+"�� �Ǿ����ϴ�!");
				player.sendMessage(ChatColor.DARK_AQUA + "[NPC] : NPC�� ��ä ����� ������ �ּ���. ");

				u.setType(player, "NPC");
				u.setString(player, (byte)4, "Deal");
			}
			return;
		case "SuccessRate":
			if(isIntMinMax(Message, player, 1, 100))
			{
				NPCscript.set("Job.SuccessRate",Integer.parseInt(ChatColor.stripColor(event.getMessage())));
				NPCscript.saveConfig();
				player.sendMessage(ChatColor.DARK_AQUA + "[NPC] : �� NPC�� �� ���� �������� "+ChatColor.WHITE+ChatColor.stripColor(event.getMessage())+"%"+ChatColor.DARK_AQUA+"�� �Ǿ����ϴ�!");
				player.sendMessage(ChatColor.DARK_AQUA + "[NPC] : NPC�� �� ���� ����� �Է� �ϼ���! ");

				u.setType(player, "NPC");
				u.setString(player, (byte)4, "Deal");
			}
			return;
		case "Deal":
			if(isIntMinMax(Message, player, 0, Integer.MAX_VALUE))
			{
				NPCscript.set("Job.Deal",Integer.parseInt(ChatColor.stripColor(event.getMessage())));
				NPCscript.saveConfig();
				NPGUI.MainGUI(player, u.getString(player, (byte)2), player.isOp());

				u.clearAll(player);
			}
			return;
		case "NPCJL" ://NPC Job Lord 
			event.setCancelled(true);
			YamlManager JobList  = YC.getNewConfig("Skill/JobList.yml");
			YamlManager Config = YC.getNewConfig("config.yml");
			boolean isExitJob = false;
			Object[] Job = JobList.getConfigurationSection("MapleStory").getKeys(false).toArray();
			for(short count = 0; count < Job.length; count++)
			{
				Object[] a = JobList.getConfigurationSection("MapleStory."+Job[count].toString()).getKeys(false).toArray();
				for(short counter=0;counter<a.length;counter++)
				{
					if(a[counter].toString().equalsIgnoreCase(Message)==true && a[counter].toString().equalsIgnoreCase(Config.getString("Server.DefaultJob"))==false)
						isExitJob = true;
				}
			}
			if(isExitJob == true)
			{
				NPCscript = YC.getNewConfig("NPC/NPCData/"+ u.getString(player, (byte)2) +".yml");
				NPCscript.removeKey("Job");
				NPCscript.set("Job.Type", "Master");
				NPCscript.set("Job.Job", Message);
				NPCscript.saveConfig();
				NPGUI.MainGUI(player, u.getString(player, (byte)3),player.isOp());
				u.clearAll(player);
			}
			else
			{
				player.sendMessage(ChatColor.LIGHT_PURPLE + "[NPC] : �� NPC�� � �������� ���� ���� �ִ� �����ΰ���?");
				for(short count = 0; count < Job.length; count++)
				{
					Object[] a = JobList.getConfigurationSection("MapleStory."+Job[count].toString()).getKeys(false).toArray();
					for(short counter=0;counter<a.length;counter++)
					{
						if(a[counter].toString().equalsIgnoreCase(Config.getString("Server.DefaultJob"))==false)
							player.sendMessage(ChatColor.LIGHT_PURPLE + " "+Job[count].toString()+" �� "+ChatColor.YELLOW + a[counter].toString());
					}
				}
			}
			return;
		}
		return;
	}

	private void NewBieTypeChatting(PlayerChatEvent event)
	{
		Object_UserData u = new Object_UserData();
		Player player = event.getPlayer();
	  	YamlController YC = new YamlController(GoldBigDragon_RPG.Main.Main.plugin);
		event.setCancelled(true);
		String Message = ChatColor.stripColor(event.getMessage());
		switch(u.getString(player, (byte)1))
		{
		case "NSM"://NewbieSupportMoney
			if(isIntMinMax(Message, player, 0, Integer.MAX_VALUE))
			{
				YamlManager NewBieYM = YC.getNewConfig("ETC/NewBie.yml");
				NewBieYM.set("SupportMoney", Integer.parseInt(Message));
				NewBieYM.saveConfig();
				GoldBigDragon_RPG.GUI.NewBieGUI NGUI = new GoldBigDragon_RPG.GUI.NewBieGUI();
				NGUI.NewBieSupportItemGUI(player);
				u.clearAll(player);
			}
			return;
		}
		return;
	}

	private void SkillTypeChatting(PlayerChatEvent event)
	{
		Object_UserData u = new Object_UserData();
		GoldBigDragon_RPG.Effect.Sound sound = new GoldBigDragon_RPG.Effect.Sound();
		Player player = event.getPlayer();
		GoldBigDragon_RPG.Skill.OPBoxSkillGUI SKGUI = new GoldBigDragon_RPG.Skill.OPBoxSkillGUI();
	  	YamlController YC = new YamlController(GoldBigDragon_RPG.Main.Main.plugin);

		YamlManager SkillList  = YC.getNewConfig("Skill/SkillList.yml");
		event.setCancelled(true);
		
		String Message = ChatColor.stripColor(event.getMessage());
		
		switch(u.getString(player, (byte)1))
		{
		case "SKL"://SkillLore
			sound.SP(player, org.bukkit.Sound.ITEM_PICKUP, 1.0F, 0.5F);
			SkillList.set(u.getString(player, (byte)2)+".SkillRank."+u.getInt(player, (byte)4)+".Lore", event.getMessage());
			SkillList.saveConfig();
			SKGUI.SkillRankOptionGUI(player, u.getString(player, (byte)2), (short) u.getInt(player, (byte)4));
			u.clearAll(player);
			return;

		case "CS"://CreateSkill
			Message.replace(".", "");
			Message.replace("\"", "");
			Message.replace("\'", "");
			Message.replace("\\", "");
			if(Message.equals("")||Message.equals(null))
				Message = "�̸����� ��ų";
			SkillList.set(Message+".ID",403);
			SkillList.set(Message+".DATA",0);
			SkillList.set(Message+".Amount",1);
			SkillList.set(Message+".SkillRank."+1+".Command","null");
			SkillList.set(Message+".SkillRank."+1+".BukkitPermission",false);
			SkillList.set(Message+".SkillRank."+1+".MagicSpells","null");
			SkillList.set(Message+".SkillRank."+1+".Lore",ChatColor.GRAY + "     [���� ����]     ");
			SkillList.set(Message+".SkillRank."+1+".AffectStat","����");
			SkillList.set(Message+".SkillRank."+1+".DistrictWeapon","����");
			SkillList.saveConfig();
			sound.SP(player, org.bukkit.Sound.HORSE_SADDLE, 1.0F, 0.5F);
			SKGUI.AllSkillsGUI(player, (short) 0,false,"Maple");
			u.clearAll(player);
			return;
		case "CSID" ://ChangeSkillID 
			if(isIntMinMax(Message, player, 1, 2267))
			{
				GoldBigDragon_RPG.Event.Interact I = new GoldBigDragon_RPG.Event.Interact();
				if(I.SetItemDefaultName(Short.parseShort(Message),(byte)0).equalsIgnoreCase("�������� ���� ������"))
				{
					player.sendMessage(ChatColor.RED + "[SYSTEM] : �ش� �������� �������� �ʽ��ϴ�!");
	  				sound.SP(player, org.bukkit.Sound.ORB_PICKUP, 2.0F, 1.7F);
	  				return;
				}
				SkillList.set(u.getString(player, (byte)2)+".ID", Integer.parseInt(Message));
				SkillList.saveConfig();
				u.setType(player, "Skill");
				u.setString(player, (byte)1, "CSD");
				sound.SP(player, org.bukkit.Sound.ITEM_PICKUP, 1.0F, 1.0F);
				player.sendMessage(ChatColor.LIGHT_PURPLE+"[��ų] : ��ų �������� DATA���� �Է� �� �ּ���!!");
			}
			return;
		case "CSD" ://ChangeSkillData
			if(isIntMinMax(Message, player, 0, Integer.MAX_VALUE))
			{
				SkillList.set(u.getString(player, (byte)2)+".DATA", Integer.parseInt(Message));
				SkillList.saveConfig();
				u.setType(player, "Skill");
				u.setString(player, (byte)1, "CSA");
				sound.SP(player, org.bukkit.Sound.ITEM_PICKUP, 1.0F, 1.0F);
				player.sendMessage(ChatColor.LIGHT_PURPLE+"[��ų] : ��ų �������� ������ �Է� �� �ּ���!!");
			}
			return;
		case "CSA" ://ChangeSkillAmount
			if(isIntMinMax(Message, player, 1, 127))
			{
				SkillList.set(u.getString(player, (byte)2)+".Amount", Integer.parseInt(Message));
				SkillList.saveConfig();
				sound.SP(player, org.bukkit.Sound.ANVIL_USE, 1.0F, 1.0F);
				SKGUI.AllSkillsGUI(player, (short) 0,false,"Maple");
				u.clearAll(player);
			}
			return;
		case "NeedLV"://NeedLevel
			if(isIntMinMax(Message, player, 0, Integer.MAX_VALUE))
			{
				SkillList.set(u.getString(player, (byte)2)+".SkillRank."+u.getInt(player, (byte)4)+".NeedLevel",Integer.parseInt(Message));
				SkillList.saveConfig();
				sound.SP(player, Sound.ITEM_PICKUP, 0.8F, 1.0F);
				player.sendMessage(ChatColor.LIGHT_PURPLE+"[��ų] : ��ų�� ��� �� �ִ� ���� ������ ������ �ּ���!");
				player.sendMessage(ChatColor.LIGHT_PURPLE+"[���� ���� : 0] [�ִ� : "+Integer.MAX_VALUE+"]");
				u.setType(player, "Skill");
				u.setString(player, (byte)1, "NeedRealLV");
				u.setString(player, (byte)2, u.getString(player, (byte)2));
				u.setInt(player, (byte)4, u.getInt(player, (byte)4));
			}
			return;
		case "NeedRealLV"://SkillPoint
			if(isIntMinMax(Message, player, 0, Integer.MAX_VALUE))
			{
				SkillList.set(u.getString(player, (byte)2)+".SkillRank."+u.getInt(player, (byte)4)+".NeedRealLevel",Integer.parseInt(Message));
				SkillList.saveConfig();
				sound.SP(player, org.bukkit.Sound.ITEM_PICKUP, 1.0F, 1.0F);
				SKGUI.SkillRankOptionGUI(player, u.getString(player, (byte)2), (short) u.getInt(player, (byte)4));
				u.clearAll(player);
			}
			return;
		case "SP"://SkillPoint
			if(isIntMinMax(Message, player, 0, Integer.MAX_VALUE))
			{
				SkillList.set(u.getString(player, (byte)2)+".SkillRank."+u.getInt(player, (byte)4)+".SkillPoint",Integer.parseInt(Message));
				SkillList.saveConfig();
				sound.SP(player, org.bukkit.Sound.ANVIL_USE, 1.0F, 1.0F);
				SKGUI.SkillRankOptionGUI(player, u.getString(player, (byte)2), (short) u.getInt(player, (byte)4));
				u.clearAll(player);
			}
			return;
		case "BH"://BonusHealth
			if(isIntMinMax(Message, player, Integer.MIN_VALUE, Integer.MAX_VALUE))
			{
				SkillList.set(u.getString(player, (byte)2)+".SkillRank."+u.getInt(player, (byte)4)+".BonusHP",Integer.parseInt(Message));
				SkillList.saveConfig();
				sound.SP(player, org.bukkit.Sound.ANVIL_USE, 1.0F, 1.0F);
				SKGUI.SkillRankOptionGUI(player, u.getString(player, (byte)2), (short) u.getInt(player, (byte)4));
				u.clearAll(player);
			}
			return;
		case "BM"://BonusMana
			if(isIntMinMax(Message, player, Integer.MIN_VALUE, Integer.MAX_VALUE))
			{
				SkillList.set(u.getString(player, (byte)2)+".SkillRank."+u.getInt(player, (byte)4)+".BonusMP",Integer.parseInt(Message));
				SkillList.saveConfig();
				sound.SP(player, org.bukkit.Sound.ANVIL_USE, 1.0F, 1.0F);
				SKGUI.SkillRankOptionGUI(player, u.getString(player, (byte)2), (short) u.getInt(player, (byte)4));
				u.clearAll(player);
			}
			return;
		case "BSTR"://BonusSTR
			if(isIntMinMax(Message, player, Integer.MIN_VALUE, Integer.MAX_VALUE))
			{
				SkillList.set(u.getString(player, (byte)2)+".SkillRank."+u.getInt(player, (byte)4)+".BonusSTR",Integer.parseInt(Message));
				SkillList.saveConfig();
				sound.SP(player, org.bukkit.Sound.ANVIL_USE, 1.0F, 1.0F);
				SKGUI.SkillRankOptionGUI(player, u.getString(player, (byte)2), (short) u.getInt(player, (byte)4));
				u.clearAll(player);
			}
			return;
		case "BDEX"://BonusDEX
			if(isIntMinMax(Message, player, Integer.MIN_VALUE, Integer.MAX_VALUE))
			{
				SkillList.set(u.getString(player, (byte)2)+".SkillRank."+u.getInt(player, (byte)4)+".BonusDEX",Integer.parseInt(Message));
				SkillList.saveConfig();
				sound.SP(player, org.bukkit.Sound.ANVIL_USE, 1.0F, 1.0F);
				SKGUI.SkillRankOptionGUI(player, u.getString(player, (byte)2), (short) u.getInt(player, (byte)4));
				u.clearAll(player);
			}
			return;
		case "BINT"://BonusINT
			if(isIntMinMax(Message, player, Integer.MIN_VALUE, Integer.MAX_VALUE))
			{
				SkillList.set(u.getString(player, (byte)2)+".SkillRank."+u.getInt(player, (byte)4)+".BonusINT",Integer.parseInt(Message));
				SkillList.saveConfig();
				sound.SP(player, org.bukkit.Sound.ANVIL_USE, 1.0F, 1.0F);
				SKGUI.SkillRankOptionGUI(player, u.getString(player, (byte)2), (short) u.getInt(player, (byte)4));
				u.clearAll(player);
			}
			return;
		case "BWILL"://BonusWILL
			if(isIntMinMax(Message, player, Integer.MIN_VALUE, Integer.MAX_VALUE))
			{
				SkillList.set(u.getString(player, (byte)2)+".SkillRank."+u.getInt(player, (byte)4)+".BonusWILL",Integer.parseInt(Message));
				SkillList.saveConfig();
				sound.SP(player, org.bukkit.Sound.ANVIL_USE, 1.0F, 1.0F);
				SKGUI.SkillRankOptionGUI(player, u.getString(player, (byte)2), (short) u.getInt(player, (byte)4));
				u.clearAll(player);
			}
			return;
		case "BLUK"://BonusLUK
			if(isIntMinMax(Message, player, Integer.MIN_VALUE, Integer.MAX_VALUE))
			{
				SkillList.set(u.getString(player, (byte)2)+".SkillRank."+u.getInt(player, (byte)4)+".BonusLUK",Integer.parseInt(Message));
				SkillList.saveConfig();
				sound.SP(player, org.bukkit.Sound.ANVIL_USE, 1.0F, 1.0F);
				SKGUI.SkillRankOptionGUI(player, u.getString(player, (byte)2), (short) u.getInt(player, (byte)4));
				u.clearAll(player);
			}
			return;
		case "BBAL"://BonusBalance
			if(isIntMinMax(Message, player, Integer.MIN_VALUE, Integer.MAX_VALUE))
			{
				SkillList.set(u.getString(player, (byte)2)+".SkillRank."+u.getInt(player, (byte)4)+".BonusBAL",Integer.parseInt(Message));
				SkillList.saveConfig();
				sound.SP(player, org.bukkit.Sound.ANVIL_USE, 1.0F, 1.0F);
				SKGUI.SkillRankOptionGUI(player, u.getString(player, (byte)2), (short) u.getInt(player, (byte)4));
				u.clearAll(player);
			}
			return;
		case "BCRI"://BonusCritical
			if(isIntMinMax(Message, player, Integer.MIN_VALUE, Integer.MAX_VALUE))
			{
				SkillList.set(u.getString(player, (byte)2)+".SkillRank."+u.getInt(player, (byte)4)+".BonusCRI",Integer.parseInt(Message));
				SkillList.saveConfig();
				sound.SP(player, org.bukkit.Sound.ANVIL_USE, 1.0F, 1.0F);
				SKGUI.SkillRankOptionGUI(player, u.getString(player, (byte)2), (short) u.getInt(player, (byte)4));
				u.clearAll(player);
			}
			return;
		case "BDEF"://BonusDefense
			if(isIntMinMax(Message, player, Integer.MIN_VALUE, Integer.MAX_VALUE))
			{
				SkillList.set(u.getString(player, (byte)2)+".SkillRank."+u.getInt(player, (byte)4)+".BonusDEF",Integer.parseInt(Message));
				SkillList.saveConfig();
				sound.SP(player, org.bukkit.Sound.ANVIL_USE, 1.0F, 1.0F);
				SKGUI.SkillRankOptionGUI(player, u.getString(player, (byte)2), (short) u.getInt(player, (byte)4));
				u.clearAll(player);
			}
			return;
		case "BPRO"://BonusProtect
			if(isIntMinMax(Message, player, Integer.MIN_VALUE, Integer.MAX_VALUE))
			{
				SkillList.set(u.getString(player, (byte)2)+".SkillRank."+u.getInt(player, (byte)4)+".BonusPRO",Integer.parseInt(Message));
				SkillList.saveConfig();
				sound.SP(player, org.bukkit.Sound.ANVIL_USE, 1.0F, 1.0F);
				SKGUI.SkillRankOptionGUI(player, u.getString(player, (byte)2), (short) u.getInt(player, (byte)4));
				u.clearAll(player);
			}
			return;
		case "BMDEF"://BonusMagicDefense
			if(isIntMinMax(Message, player, Integer.MIN_VALUE, Integer.MAX_VALUE))
			{
				SkillList.set(u.getString(player, (byte)2)+".SkillRank."+u.getInt(player, (byte)4)+".BonusMDEF",Integer.parseInt(Message));
				SkillList.saveConfig();
				sound.SP(player, org.bukkit.Sound.ANVIL_USE, 1.0F, 1.0F);
				SKGUI.SkillRankOptionGUI(player, u.getString(player, (byte)2), (short) u.getInt(player, (byte)4));
				u.clearAll(player);
			}
			return;
		case "BMPRO"://BonusMagicProtect
			if(isIntMinMax(Message, player, Integer.MIN_VALUE, Integer.MAX_VALUE))
			{
				SkillList.set(u.getString(player, (byte)2)+".SkillRank."+u.getInt(player, (byte)4)+".BonusMPRO",Integer.parseInt(Message));
				SkillList.saveConfig();
				sound.SP(player, org.bukkit.Sound.ANVIL_USE, 1.0F, 1.0F);
				SKGUI.SkillRankOptionGUI(player, u.getString(player, (byte)2), (short) u.getInt(player, (byte)4));
				u.clearAll(player);
			}
			return;
		}//Main.JobHashMap1�� ���ϴ� switch�� ��
	}

	private void JobTypeChatting(PlayerChatEvent event)
	{
		Object_UserData u = new Object_UserData();
		event.setCancelled(true);
		GoldBigDragon_RPG.Effect.Sound sound = new GoldBigDragon_RPG.Effect.Sound();
		Player player = event.getPlayer();
		GoldBigDragon_RPG.Skill.OPBoxSkillGUI SKGUI = new GoldBigDragon_RPG.Skill.OPBoxSkillGUI();
	  	YamlController YC = new YamlController(GoldBigDragon_RPG.Main.Main.plugin);

		String Message = ChatColor.stripColor(event.getMessage());
		YamlManager JobList  = YC.getNewConfig("Skill/JobList.yml");
		GoldBigDragon_RPG.GUI.JobGUI JGUI = new GoldBigDragon_RPG.GUI.JobGUI();

		switch(u.getString(player, (byte)1))
		{
		case "CC"://CreateCategory
			if(JobList.getConfigurationSection("Mabinogi").getKeys(false).toString().contains(Message) == false)
			{
				JobList.set("Mabinogi."+Message+".LV",null);
				JobList.saveConfig();
				sound.SP(player, org.bukkit.Sound.HORSE_SADDLE, 1.0F, 0.5F);
				JGUI.Mabinogi_ChooseCategory(player,(short) 0);

				YamlManager Config  = YC.getNewConfig("config.yml");
				Config.set("Time.LastSkillChanged", new GoldBigDragon_RPG.Util.Number().RandomNum(0, 100000)-new GoldBigDragon_RPG.Util.Number().RandomNum(0, 100000));
				Config.saveConfig();
				
				new GoldBigDragon_RPG.ETC.Job().AllPlayerFixAllSkillAndJobYML();
				u.clearAll(player);
			}
			else
			{
				player.sendMessage(ChatColor.RED + "[ī�װ�] : �̹� �����ϴ� ī�װ� �̸��Դϴ�!");
  				sound.SP(player, org.bukkit.Sound.ORB_PICKUP, 2.0F, 1.7F);
			}
			return;
		case "CJ"://CreateJob
			JobList.set("MapleStory."+Message+"."+Message+".NeedLV",0);
			JobList.set("MapleStory."+Message+"."+Message+".NeedSTR",0);
			JobList.set("MapleStory."+Message+"."+Message+".NeedDEX",0);
			JobList.set("MapleStory."+Message+"."+Message+".NeedINT",0);
			JobList.set("MapleStory."+Message+"."+Message+".NeedWILL",0);
			JobList.set("MapleStory."+Message+"."+Message+".NeedLUK",0);
			JobList.set("MapleStory."+Message+"."+Message+".PrevJob","null");
			JobList.set("MapleStory."+Message+"."+Message+".IconID",267);
			JobList.set("MapleStory."+Message+"."+Message+".IconData",0);
			JobList.set("MapleStory."+Message+"."+Message+".IconAmount",1);
			JobList.set("MapleStory."+Message+"."+Message+".Skill.1",null);
			JobList.saveConfig();
			sound.SP(player, org.bukkit.Sound.HORSE_SADDLE, 1.0F, 0.5F);
			JGUI.MapleStory_JobSetting(player,Message);
			u.clearAll(player);
			return;
		case "CJL"://CreateJobLevel (�±� �����)
			String JobName2 = u.getString(player, (byte)2);
			short NowJobLevel = (short) JobList.getConfigurationSection("MapleStory."+JobName2).getKeys(false).size();
			JobList.set("MapleStory."+JobName2+"."+Message+".NeedLV",0);
			JobList.set("MapleStory."+JobName2+"."+Message+".NeedSTR",0);
			JobList.set("MapleStory."+JobName2+"."+Message+".NeedDEX",0);
			JobList.set("MapleStory."+JobName2+"."+Message+".NeedINT",0);
			JobList.set("MapleStory."+JobName2+"."+Message+".NeedWILL",0);
			JobList.set("MapleStory."+JobName2+"."+Message+".NeedLUK",0);
			JobList.set("MapleStory."+JobName2+"."+Message+".PrevJob","null");
			JobList.set("MapleStory."+JobName2+"."+Message+".IconID",267);
			JobList.set("MapleStory."+JobName2+"."+Message+".IconData",0);
			JobList.set("MapleStory."+JobName2+"."+Message+".IconAmount",1);
			JobList.set("MapleStory."+JobName2+"."+Message+".Skill.1",null);
			JobList.saveConfig();
			sound.SP(player, org.bukkit.Sound.ANVIL_USE, 1.0F, 1.0F);
			JGUI.MapleStory_JobSetting(player, JobName2);
			u.clearAll(player);
			return;
		case "JNL"://JobNeedLevel (�±��� ���� �ʿ� ����)
			if(isIntMinMax(Message, player, 0, Integer.MAX_VALUE))
			{
				String JobName3 = u.getString(player, (byte)2);
				String JobNick2 = u.getString(player, (byte)3);
				JobList.set("MapleStory."+JobName3+"."+JobNick2+".NeedLV",Integer.parseInt(Message));
				JobList.saveConfig();
				u.setType(player, "Job");
				u.setString(player, (byte)1, "JNS");
				sound.SP(player, org.bukkit.Sound.ITEM_PICKUP, 1.0F, 1.0F);
				player.sendMessage(ChatColor.LIGHT_PURPLE + "[����] : "+ChatColor.YELLOW +JobNick2+ChatColor.LIGHT_PURPLE +"�� �±� �ʿ� "+GoldBigDragon_RPG.Main.ServerOption.STR+"�� �����ϼ���.");
			}
			return;
		case "JNS" : 
			if(isIntMinMax(Message, player, 0, Integer.MAX_VALUE))
			{
				String JobName3 = u.getString(player, (byte)2);
				String JobNick2 = u.getString(player, (byte)3);
				JobList.set("MapleStory."+JobName3+"."+JobNick2+".NeedSTR",Integer.parseInt(Message));
				JobList.saveConfig();
				u.setType(player, "Job");
				u.setString(player, (byte)1, "JND");
				sound.SP(player, org.bukkit.Sound.ITEM_PICKUP, 1.0F, 1.0F);
				player.sendMessage(ChatColor.LIGHT_PURPLE + "[����] : "+ChatColor.YELLOW +JobNick2+ChatColor.LIGHT_PURPLE +"�� �±� �ʿ� "+GoldBigDragon_RPG.Main.ServerOption.DEX+"�� �����ϼ���.");
			}
			return;
		case "JND" : 
			if(isIntMinMax(Message, player, 0, Integer.MAX_VALUE))
			{
				String JobName3 = u.getString(player, (byte)2);
				String JobNick2 = u.getString(player, (byte)3);
				JobList.set("MapleStory."+JobName3+"."+JobNick2+".NeedDEX",Integer.parseInt(Message));
				JobList.saveConfig();
				u.setType(player, "Job");
				u.setString(player, (byte)1, "JNI");
				sound.SP(player, org.bukkit.Sound.ITEM_PICKUP, 1.0F, 1.0F);
				player.sendMessage(ChatColor.LIGHT_PURPLE + "[����] : "+ChatColor.YELLOW +JobNick2+ChatColor.LIGHT_PURPLE +"�� �±� �ʿ� "+GoldBigDragon_RPG.Main.ServerOption.INT+"�� �����ϼ���.");
			}
			return;
		case "JNI" : 
			if(isIntMinMax(Message, player, 0, Integer.MAX_VALUE))
			{
				String JobName3 = u.getString(player, (byte)2);
				String JobNick2 = u.getString(player, (byte)3);
				JobList.set("MapleStory."+JobName3+"."+JobNick2+".NeedINT",Integer.parseInt(Message));
				JobList.saveConfig();
				u.setType(player, "Job");
				u.setString(player, (byte)1, "JNW");
				sound.SP(player, org.bukkit.Sound.ITEM_PICKUP, 1.0F, 1.0F);
				player.sendMessage(ChatColor.LIGHT_PURPLE + "[����] : "+ChatColor.YELLOW +JobNick2+ChatColor.LIGHT_PURPLE +"�� �±� �ʿ� "+GoldBigDragon_RPG.Main.ServerOption.WILL+"�� �����ϼ���.");
			}
			return;
		case "JNW" : 
			if(isIntMinMax(Message, player, 0, Integer.MAX_VALUE))
			{
				String JobName3 = u.getString(player, (byte)2);
				String JobNick2 = u.getString(player, (byte)3);
				JobList.set("MapleStory."+JobName3+"."+JobNick2+".NeedWILL",Integer.parseInt(Message));
				JobList.saveConfig();
				u.setType(player, "Job");
				u.setString(player, (byte)1, "JNLU");
				sound.SP(player, org.bukkit.Sound.ITEM_PICKUP, 1.0F, 1.0F);
				player.sendMessage(ChatColor.LIGHT_PURPLE + "[����] : "+ChatColor.YELLOW +JobNick2+ChatColor.LIGHT_PURPLE +"�� �±� �ʿ� "+GoldBigDragon_RPG.Main.ServerOption.LUK+"�� �����ϼ���.");
			}
			return;
		case "JNLU" : 
			if(isIntMinMax(Message, player, 0, Integer.MAX_VALUE))
			{
				String JobName3 = u.getString(player, (byte)2);
				String JobNick2 = u.getString(player, (byte)3);
				JobList.set("MapleStory."+JobName3+"."+JobNick2+".NeedLUK",Integer.parseInt(Message));
				JobList.saveConfig();
				u.setType(player, "Job");
				u.setString(player, (byte)1, "JNPJ");
				sound.SP(player, org.bukkit.Sound.ITEM_PICKUP, 1.0F, 1.0F);
				player.sendMessage(ChatColor.LIGHT_PURPLE + "[����] : � ������"+ChatColor.YELLOW +JobNick2+ChatColor.LIGHT_PURPLE +" �� �±� �����ϰ� �ұ��?");
				
				Object[] Job2 = JobList.getConfigurationSection("MapleStory").getKeys(false).toArray();
				for(short count = 0; count < Job2.length; count++)
				{
					Object[] a = JobList.getConfigurationSection("MapleStory."+Job2[count].toString()).getKeys(false).toArray();
					for(short counter=0;counter<a.length;counter++)
					{
						if(a[counter].toString().equalsIgnoreCase(JobNick2)==false)
						player.sendMessage(ChatColor.LIGHT_PURPLE + " "+Job2[count].toString()+" �� "+ChatColor.YELLOW + a[counter].toString());
					}
				}
				player.sendMessage(ChatColor.LIGHT_PURPLE + " ���� ������ �����̵� ��� ���� ��� �� "+ChatColor.YELLOW + "����");
			}
			return;
		case "JNPJ" : 
			String JobName3 = u.getString(player, (byte)2);
			String JobNick2 = u.getString(player, (byte)3);
			Object[] Job2 = JobList.getConfigurationSection("MapleStory").getKeys(false).toArray();
			boolean checked = false;
			if(Message.equalsIgnoreCase("����")==false)
			{
				for(short count = 0; count < Job2.length; count++)
				{
					Object[] a = JobList.getConfigurationSection("MapleStory."+Job2[count].toString()).getKeys(false).toArray();
					for(short counter=0;counter<a.length;counter++)
					{
						if(a[counter].toString().equalsIgnoreCase(ChatColor.stripColor(Message)))
						{
							checked = true;
							break;
						}
					}
				}
			}
			else
				checked = true;
			if(checked == true)
			{
				if(Message.equalsIgnoreCase("����")==false)
					JobList.set("MapleStory."+JobName3+"."+JobNick2+".PrevJob",ChatColor.stripColor(Message));
				else
					JobList.set("MapleStory."+JobName3+"."+JobNick2+".PrevJob","null");
					
				JobList.saveConfig();
				u.setType(player, "Job");
				u.setString(player, (byte)1, "JNICONID");
				sound.SP(player, org.bukkit.Sound.ITEM_PICKUP, 1.0F, 1.0F);
				player.sendMessage(ChatColor.LIGHT_PURPLE + "[����] : "+ChatColor.YELLOW +JobNick2+ChatColor.LIGHT_PURPLE +" �� ��Ÿ���� ������ ID�� �����ΰ���?");
			}
			else
			{
				Object[] Job1 = JobList.getConfigurationSection("MapleStory").getKeys(false).toArray();
				for(short count = 0; count < Job1.length; count++)
				{
					Object[] a = JobList.getConfigurationSection("MapleStory."+Job1[count].toString()).getKeys(false).toArray();
					for(short counter=0;counter<a.length;counter++)
					{
						if(a[counter].toString().equalsIgnoreCase(JobNick2)==false)
						player.sendMessage(ChatColor.LIGHT_PURPLE + " "+Job2[count].toString()+" �� "+ChatColor.YELLOW + a[counter].toString());
					}
				}
				player.sendMessage(ChatColor.LIGHT_PURPLE + " ���� ������ �����̵� ��� ���� ��� �� "+ChatColor.YELLOW + "����");
			}
			return;
		case "JNICONID" : 
			if(isIntMinMax(Message, player, 1, 2267))
			{
				GoldBigDragon_RPG.Event.Interact I = new GoldBigDragon_RPG.Event.Interact();
				if(I.SetItemDefaultName(Short.parseShort(Message),(byte)0).equalsIgnoreCase("�������� ���� ������"))
				{
					player.sendMessage(ChatColor.RED + "[SYSTEM] : �ش� �������� �������� �ʽ��ϴ�!");
	  				sound.SP(player, org.bukkit.Sound.ORB_PICKUP, 2.0F, 1.7F);
	  				return;
				}
				String JobName4 = u.getString(player, (byte)2);
				String JobNick3 = u.getString(player, (byte)3);
				JobList.set("MapleStory."+JobName4+"."+JobNick3+".IconID",Integer.parseInt(Message));
				JobList.saveConfig();
				u.setType(player, "Job");
				u.setString(player, (byte)1, "JNICONDATA");
				sound.SP(player, org.bukkit.Sound.ITEM_PICKUP, 1.0F, 1.0F);
				player.sendMessage(ChatColor.LIGHT_PURPLE + "[����] : "+ChatColor.YELLOW +JobNick3+ChatColor.LIGHT_PURPLE +" �� ��Ÿ���� ������ DATA�� �����ΰ���?");
			}
			return;
		case "JNICONDATA" : 
			if(isIntMinMax(Message, player, 0, Integer.MAX_VALUE))
			{
				String JobName4 = u.getString(player, (byte)2);
				String JobNick3 = u.getString(player, (byte)3);
				JobList.set("MapleStory."+JobName4+"."+JobNick3+".IconData",Integer.parseInt(Message));
				JobList.saveConfig();
				u.setType(player, "Job");
				u.setString(player, (byte)1, "JNICONAMOUNT");
				sound.SP(player, org.bukkit.Sound.ITEM_PICKUP, 1.0F, 1.0F);
				player.sendMessage(ChatColor.LIGHT_PURPLE + "[����] : "+ChatColor.YELLOW +JobNick3+ChatColor.LIGHT_PURPLE +" �� ��Ÿ���� ������ ������ �� ���ΰ���?");
			}
			return;
		case "JNICONAMOUNT" : 
			if(isIntMinMax(Message, player, 1, 127))
			{
				String JobName4 = u.getString(player, (byte)2);
				String JobNick3 = u.getString(player, (byte)3);
				JobList.set("MapleStory."+JobName4+"."+JobNick3+".IconAmount",Integer.parseInt(Message));
				JobList.saveConfig();
				sound.SP(player, org.bukkit.Sound.ANVIL_USE, 1.0F, 1.0F);
				JGUI.MapleStory_JobSetting(player, JobName4);
				u.clearAll(player);
			}
			return;
		}
		return;
	}

	private void MonsterTypeChatting(PlayerChatEvent event)
	{
		Object_UserData u = new Object_UserData();
		Player player = event.getPlayer();
		GoldBigDragon_RPG.Monster.MonsterGUI MGUI = new GoldBigDragon_RPG.Monster.MonsterGUI();
		GoldBigDragon_RPG.Monster.MonsterSpawn MC = new GoldBigDragon_RPG.Monster.MonsterSpawn();
	  	YamlController YC = new YamlController(GoldBigDragon_RPG.Main.Main.plugin);
		YamlManager Monster  = YC.getNewConfig("Monster/MonsterList.yml");
		Object[] monsterlist = Monster.getConfigurationSection("").getKeys(false).toArray();
	    GoldBigDragon_RPG.Effect.Sound s = new GoldBigDragon_RPG.Effect.Sound();
	    event.setCancelled(true);
	    String message = ChatColor.stripColor(event.getMessage());
		switch(u.getString(player, (byte)1))
		{
		case "Potion":
			if(isIntMinMax(message, player, 0, 100))
			{
				String MonsterName = u.getString(player, (byte)3);
				switch(u.getString(player, (byte)2))
				{
				case "Regenerate":
					Monster.set(MonsterName+".Potion.Regenerate", Integer.parseInt(message));break;
				case "Poision":
					Monster.set(MonsterName+".Potion.Poison", Integer.parseInt(message));break;
				case "Speed":
					Monster.set(MonsterName+".Potion.Speed", Integer.parseInt(message));break;
				case "Slow":
					Monster.set(MonsterName+".Potion.Slow", Integer.parseInt(message));break;
				case "Strength":
					Monster.set(MonsterName+".Potion.Strength", Integer.parseInt(message));break;
				case "Weak":
					Monster.set(MonsterName+".Potion.Weak", Integer.parseInt(message));break;
				case "Jump":
					Monster.set(MonsterName+".Potion.JumpBoost", Integer.parseInt(message));break;
				}
				Monster.saveConfig();
				u.clearAll(player);
				s.SP(player, Sound.DRINK, 1.0F, 1.0F);
				MGUI.MonsterPotionGUI(player, MonsterName);
			}
		return;
		case "NM"://NewMonster
			message = message.replace(".", "");
			for(short count = 0; count < monsterlist.length;count++)
	    	{
	    		if(monsterlist[count].toString().equals(message) == true)
	    		{
				  	s.SP(player, org.bukkit.Sound.ORB_PICKUP, 2.0F, 1.7F);
			    	player.sendMessage(ChatColor.RED+"[SYSTEM] : �ش� �̸��� ���ʹ� �̹� �����մϴ�!");
			    	return;
	    		}
	    	}
			s.SP(player, org.bukkit.Sound.WOLF_BARK, 1.0F, 1.0F);
	    	MC.CreateMonster(message);

		  	GoldBigDragon_RPG.Main.ServerOption.MonsterNameMatching.put(message, message);
		  	GoldBigDragon_RPG.Main.ServerOption.MonsterList.put(message, new GoldBigDragon_RPG.Main.Object_Monster(message, message, 15, 20, 1, 10, 10, 10, 10, 10, 10, 0, 0, 0, 0));
		  	
	    	player.sendMessage(ChatColor.GREEN+"[SYSTEM] : "+ChatColor.YELLOW+message+ChatColor.GREEN+" ���� ���� �Ϸ�! (�߰� ������ �� �ּ���)");
			s.SP(player, Sound.HORSE_ARMOR, 1.0F, 1.8F);
			MGUI.MonsterOptionSettingGUI(player, message);
			u.clearAll(player);
	    	return;
		case "CN"://ChangeName
			message= event.getMessage().replace(".", "");
			GoldBigDragon_RPG.Main.ServerOption.MonsterNameMatching.remove(u.getString(player, (byte)2)+Monster.getString(".Name"));
			Monster.set(u.getString(player, (byte)2)+".Name", message);
			Monster.saveConfig();
			
			GoldBigDragon_RPG.Main.ServerOption.MonsterList.get(u.getString(player, (byte)2)).setCustomName(message);
			GoldBigDragon_RPG.Main.ServerOption.MonsterNameMatching.put(message, u.getString(player, (byte)2));
			
			s.SP(player, Sound.HORSE_ARMOR, 1.0F, 1.8F);
			MGUI.MonsterOptionSettingGUI(player, u.getString(player, (byte)2));
			u.clearAll(player);
	    	return;
		case "HP"://HealthPoint
			if(isIntMinMax(message, player, 1, Integer.MAX_VALUE))
			{
				Monster.set(u.getString(player, (byte)2)+"."+u.getString(player, (byte)1), Integer.parseInt(message));
				Monster.saveConfig();
				GoldBigDragon_RPG.Main.ServerOption.MonsterList.get(u.getString(player, (byte)2)).setHP(Integer.parseInt(message));
				s.SP(player, Sound.HORSE_ARMOR, 1.0F, 1.8F);
				MGUI.MonsterOptionSettingGUI(player, u.getString(player, (byte)2));
				u.clearAll(player);
			}
			return;
		case "EXP":
		case "LUK":
		case "Magic_Protect" : //MagicProtect
			if(isIntMinMax(message, player, 0, Integer.MAX_VALUE))
			{
				Monster.set(u.getString(player, (byte)2)+"."+u.getString(player, (byte)1), Integer.parseInt(message));
				Monster.saveConfig();
				if(u.getString(player, (byte)1).compareTo("EXP")==0)
					GoldBigDragon_RPG.Main.ServerOption.MonsterList.get(u.getString(player, (byte)2)).setEXP(Integer.parseInt(message));
				else if(u.getString(player, (byte)1).compareTo("LUK")==0)
					GoldBigDragon_RPG.Main.ServerOption.MonsterList.get(u.getString(player, (byte)2)).setLUK(Integer.parseInt(message));
				else
					GoldBigDragon_RPG.Main.ServerOption.MonsterList.get(u.getString(player, (byte)2)).setMPRO(Integer.parseInt(message));
				s.SP(player, Sound.HORSE_ARMOR, 1.0F, 1.8F);
				MGUI.MonsterOptionSettingGUI(player, u.getString(player, (byte)2));
				u.clearAll(player);
			}
			return;
		case "MAX_Money"://Maximum Drop Money
			if(isIntMinMax(message, player, u.getInt(player, (byte)1), Integer.MAX_VALUE))
			{
				Monster.set(u.getString(player, (byte)2)+"."+u.getString(player, (byte)1), Integer.parseInt(message));
				Monster.saveConfig();
				GoldBigDragon_RPG.Main.ServerOption.MonsterList.get(u.getString(player, (byte)2)).setMaxMoney(Integer.parseInt(message));
				s.SP(player, Sound.HORSE_ARMOR, 1.0F, 1.8F);
				MGUI.MonsterOptionSettingGUI(player, u.getString(player, (byte)2));
				u.clearAll(player);
			}
			return;
		case "MIN_Money"://Minimum Drop Money
			if(isIntMinMax(message, player, 1, Integer.MAX_VALUE))
			{
				u.setInt(player, (byte)1, Integer.parseInt(message));
				Monster.set(u.getString(player, (byte)2)+"."+u.getString(player, (byte)1), Integer.parseInt(message));
				Monster.saveConfig();
				GoldBigDragon_RPG.Main.ServerOption.MonsterList.get(u.getString(player, (byte)2)).setMinMoney(Integer.parseInt(message));
				s.SP(player, Sound.ITEM_PICKUP, 1.0F, 1.0F);
				player.sendMessage(ChatColor.GREEN+"[����] : �ش� ���Ͱ� ����ϴ� �ִ� ��差�� ������ �ּ���!");
				player.sendMessage(ChatColor.DARK_AQUA+"("+message+" ~ "+Integer.MAX_VALUE+")");
				u.setString(player, (byte)1, "MAX_Money");
			}
			return;
		case "STR"://Strength
			if(isIntMinMax(message, player, 0, Integer.MAX_VALUE))
			{
				Monster.set(u.getString(player, (byte)2)+"."+u.getString(player, (byte)1), Integer.parseInt(message));
				Monster.saveConfig();
				GoldBigDragon_RPG.Main.ServerOption.MonsterList.get(u.getString(player, (byte)2)).setSTR(Integer.parseInt(message));
				s.SP(player, Sound.ITEM_PICKUP, 1.0F, 1.0F);
				player.sendMessage(ChatColor.GRAY+"("+GoldBigDragon_RPG.Main.ServerOption.DEX+"�� ������ ���Ÿ� ���ݷ��� ��½��� �ݴϴ�.)");
				player.sendMessage(ChatColor.GREEN+"[����] : ������ "+GoldBigDragon_RPG.Main.ServerOption.DEX+"�� ������ �ּ���!");
				player.sendMessage(ChatColor.DARK_AQUA+"(1 ~ "+Integer.MAX_VALUE+")");
				u.setString(player, (byte)1, "DEX");
			}
			return;
		case "DEX"://DEX
			if(isIntMinMax(message, player, 0, Integer.MAX_VALUE))
			{
				Monster.set(u.getString(player, (byte)2)+"."+u.getString(player, (byte)1), Integer.parseInt(message));
				Monster.saveConfig();
				GoldBigDragon_RPG.Main.ServerOption.MonsterList.get(u.getString(player, (byte)2)).setDEX(Integer.parseInt(message));
				s.SP(player, Sound.ITEM_PICKUP, 1.0F, 1.0F);
				player.sendMessage(ChatColor.GRAY+"("+GoldBigDragon_RPG.Main.ServerOption.INT+"�� ������ ���� ���ݷ��� ��½��� �ݴϴ�.)");
				player.sendMessage(ChatColor.GREEN+"[����] : ������ "+GoldBigDragon_RPG.Main.ServerOption.INT+"�� ������ �ּ���!");
				player.sendMessage(ChatColor.DARK_AQUA+"(1 ~ "+Integer.MAX_VALUE+")");
				u.setString(player, (byte)1, "INT");
			}
			return;
		case "INT"://INT
			if(isIntMinMax(message, player, 0, Integer.MAX_VALUE))
			{
				Monster.set(u.getString(player, (byte)2)+"."+u.getString(player, (byte)1), Integer.parseInt(message));
				Monster.saveConfig();
				GoldBigDragon_RPG.Main.ServerOption.MonsterList.get(u.getString(player, (byte)2)).setINT(Integer.parseInt(message));
				s.SP(player, Sound.ITEM_PICKUP, 1.0F, 1.0F);
				player.sendMessage(ChatColor.GRAY+"("+GoldBigDragon_RPG.Main.ServerOption.WILL+"�� ������ ũ��Ƽ�� Ȯ���� ��½��� �ݴϴ�.)");
				player.sendMessage(ChatColor.GREEN+"[����] : ������ "+GoldBigDragon_RPG.Main.ServerOption.WILL+"�� ������ �ּ���!");
				player.sendMessage(ChatColor.DARK_AQUA+"(1 ~ "+Integer.MAX_VALUE+")");
				u.setString(player, (byte)1, "WILL");
			}
			return;
		case "WILL"://WILL
			if(isIntMinMax(message, player, 0, Integer.MAX_VALUE))
			{
				Monster.set(u.getString(player, (byte)2)+"."+u.getString(player, (byte)1), Integer.parseInt(message));
				Monster.saveConfig();
				GoldBigDragon_RPG.Main.ServerOption.MonsterList.get(u.getString(player, (byte)2)).setWILL(Integer.parseInt(message));
				s.SP(player, Sound.ITEM_PICKUP, 1.0F, 1.0F);
				player.sendMessage(ChatColor.GRAY+"("+GoldBigDragon_RPG.Main.ServerOption.LUK+"�� ������ ũ��Ƽ�� Ȯ���� ũ�� ��½��� �ݴϴ�.)");
				player.sendMessage(ChatColor.GREEN+"[����] : ������ "+GoldBigDragon_RPG.Main.ServerOption.LUK+"�� ������ �ּ���!");
				player.sendMessage(ChatColor.DARK_AQUA+"(1 ~ "+Integer.MAX_VALUE+")");
				u.setString(player, (byte)1, "LUK");
			}
			return;
		case "DEF"://Defense
			if(isIntMinMax(message, player, 0, Integer.MAX_VALUE))
			{
				Monster.set(u.getString(player, (byte)2)+"."+u.getString(player, (byte)1), Integer.parseInt(message));
				Monster.saveConfig();
				GoldBigDragon_RPG.Main.ServerOption.MonsterList.get(u.getString(player, (byte)2)).setDEF(Integer.parseInt(message));
				s.SP(player, Sound.ITEM_PICKUP, 1.0F, 1.0F);
				player.sendMessage(ChatColor.GRAY+"(��ȣ�� ������ ���� ���׷��� ��½��� �ݴϴ�.)");
				player.sendMessage(ChatColor.GREEN+"[����] : ������ ��ȣ�� ������ �ּ���!");
				player.sendMessage(ChatColor.DARK_AQUA+"(1 ~ "+Integer.MAX_VALUE+")");
				u.setString(player, (byte)1, "Protect");
			}
			return;
		case "Protect"://Protect
			if(isIntMinMax(message, player, 0, Integer.MAX_VALUE))
			{
				Monster.set(u.getString(player, (byte)2)+"."+u.getString(player, (byte)1), Integer.parseInt(message));
				Monster.saveConfig();
				GoldBigDragon_RPG.Main.ServerOption.MonsterList.get(u.getString(player, (byte)2)).setPRO(Integer.parseInt(message));
				s.SP(player, Sound.ITEM_PICKUP, 1.0F, 1.0F);
				player.sendMessage(ChatColor.GRAY+"(���� ���� ������ ���� ������ ��½��� �ݴϴ�.)");
				player.sendMessage(ChatColor.GREEN+"[����] : ������ ���� �� ������ �ּ���!");
				player.sendMessage(ChatColor.DARK_AQUA+"(1 ~ "+Integer.MAX_VALUE+")");
				u.setString(player, (byte)1, "Magic_DEF");
			}
			return;
		case "Magic_DEF"://MagicDefense
			if(isIntMinMax(message, player, 0, Integer.MAX_VALUE))
			{
				Monster.set(u.getString(player, (byte)2)+"."+u.getString(player, (byte)1), Integer.parseInt(message));
				Monster.saveConfig();
				GoldBigDragon_RPG.Main.ServerOption.MonsterList.get(u.getString(player, (byte)2)).setMDEF(Integer.parseInt(message));
				s.SP(player, Sound.ITEM_PICKUP, 1.0F, 1.0F);
				player.sendMessage(ChatColor.GRAY+"(���� ��ȣ�� ������ ���� ���׷��� ��½��� �ݴϴ�.)");
				player.sendMessage(ChatColor.GREEN+"[����] : ������ ���� ��ȣ�� ������ �ּ���!");
				player.sendMessage(ChatColor.DARK_AQUA+"(1 ~ "+Integer.MAX_VALUE+")");
				u.setString(player, (byte)1, "Magic_Protect");
			}
			return;
		case "Head.DropChance"://DropChance
			if(isIntMinMax(message, player, 0, 1000))
			{
				Monster.set(u.getString(player, (byte)2)+"."+u.getString(player, (byte)1), Integer.parseInt(message));
				Monster.saveConfig();
				s.SP(player, Sound.ITEM_PICKUP, 1.0F, 1.0F);
				player.sendMessage(ChatColor.GRAY+"(Ȯ�� ��� : 1000 = 100%, 1 = 0.1%)");
				player.sendMessage(ChatColor.GREEN+"[����] : ������ ���� ������� ������ �ּ���!");
				player.sendMessage(ChatColor.DARK_AQUA+"(0 ~ 1000)");
				u.setString(player, (byte)1, "Chest.DropChance");
			}
			return;
		case "Chest.DropChance"://DropChance
			if(isIntMinMax(message, player, 0, 1000))
			{
				Monster.set(u.getString(player, (byte)2)+"."+u.getString(player, (byte)1), Integer.parseInt(message));
				Monster.saveConfig();
				s.SP(player, Sound.ITEM_PICKUP, 1.0F, 1.0F);
				player.sendMessage(ChatColor.GRAY+"(Ȯ�� ��� : 1000 = 100%, 1 = 0.1%)");
				player.sendMessage(ChatColor.GREEN+"[����] : ������ ���� ������� ������ �ּ���!");
				player.sendMessage(ChatColor.DARK_AQUA+"(0 ~ 1000)");
				u.setString(player, (byte)1, "Leggings.DropChance");
			}
			return;
		case "Leggings.DropChance"://DropChance
			if(isIntMinMax(message, player, 0, 1000))
			{
				Monster.set(u.getString(player, (byte)2)+"."+u.getString(player, (byte)1), Integer.parseInt(message));
				Monster.saveConfig();
				s.SP(player, Sound.ITEM_PICKUP, 1.0F, 1.0F);
				player.sendMessage(ChatColor.GRAY+"(Ȯ�� ��� : 1000 = 100%, 1 = 0.1%)");
				player.sendMessage(ChatColor.GREEN+"[����] : ������ ���� ������� ������ �ּ���!");
				player.sendMessage(ChatColor.DARK_AQUA+"(0 ~ 1000)");
				u.setString(player, (byte)1, "Boots.DropChance");
			}
			return;
		case "Boots.DropChance"://DropChance
			if(isIntMinMax(message, player, 0, 1000))
			{
				Monster.set(u.getString(player, (byte)2)+"."+u.getString(player, (byte)1), Integer.parseInt(message));
				Monster.saveConfig();
				s.SP(player, Sound.ITEM_PICKUP, 1.0F, 1.0F);
				player.sendMessage(ChatColor.GRAY+"(Ȯ�� ��� : 1000 = 100%, 1 = 0.1%)");
				player.sendMessage(ChatColor.GREEN+"[����] : ������ ���� ������� ������ �ּ���!");
				player.sendMessage(ChatColor.DARK_AQUA+"(0 ~ 1000)");
				u.setString(player, (byte)1, "Hand.DropChance");
			}
			return;
		case "Hand.DropChance"://DropChance
			if(isIntMinMax(message, player, 0, 1000))
			{
				Monster.set(u.getString(player, (byte)2)+"."+u.getString(player, (byte)1), Integer.parseInt(message));
				Monster.saveConfig();
				s.SP(player, Sound.HORSE_ARMOR, 1.0F, 1.8F);
				MGUI.MonsterOptionSettingGUI(player, u.getString(player, (byte)2));
				u.clearAll(player);
			}
			return;
		}
		return;
	}
	
	private void TeleportTypeChatting(PlayerChatEvent event)
	{
		Object_UserData u = new Object_UserData();
		Player player = event.getPlayer();
		GoldBigDragon_RPG.GUI.WarpGUI WGUI = new GoldBigDragon_RPG.GUI.WarpGUI();
	    GoldBigDragon_RPG.Effect.Sound s = new GoldBigDragon_RPG.Effect.Sound();
	    event.setCancelled(true);
	    String message = ChatColor.stripColor(event.getMessage());
		switch(u.getString(player, (byte)1))
		{
		case "NW"://NewWarp
			message = message.replace(".", "");
			new GoldBigDragon_RPG.ETC.Teleport().CreateNewTeleportSpot(player, message);
			u.clearAll(player);
			return;
		}
		return;
	}
	
	private void EventChatting(PlayerChatEvent event)
	{
		Object_UserData u = new Object_UserData();
		Player player = event.getPlayer();
		GoldBigDragon_RPG.Effect.Sound s = new GoldBigDragon_RPG.Effect.Sound();
		GoldBigDragon_RPG.Effect.PacketSender PS = new GoldBigDragon_RPG.Effect.PacketSender();
	  	YamlController YC = new YamlController(GoldBigDragon_RPG.Main.Main.plugin);
		YamlManager Config =YC.getNewConfig("config.yml");
	    event.setCancelled(true);
	    String message = ChatColor.stripColor(event.getMessage());
	    
		switch(u.getString(player, (byte)1))
		{
		case "SKP"://SkillPoint
			if(isIntMinMax(message, player, 1, 10))
			{
				int Value = Integer.parseInt(message);
				s.SP(player, Sound.ITEM_PICKUP, 0.8F, 1.0F);
				if(Config.getInt("Event.Multiple_Level_Up_SkillPoint") == 1)
				{
					if(Value != 1)
					{
						Config.set("Event.Multiple_Level_Up_SkillPoint", Value);
						PS.sendTitleAllPlayers("\'"+ChatColor.WHITE + "��ų ����Ʈ "+ChatColor.YELLOW +""+ Config.getInt("Event.Multiple_Level_Up_SkillPoint")+ChatColor.WHITE +"�� �̺�Ʈ�� �����մϴ�.\'");
						PS.sendActionBarAllPlayers(ChatColor.BOLD+""+ChatColor.YELLOW +"[�������� ��� ��ų ����Ʈ�� �����˴ϴ�.]");
						GoldBigDragon_RPG.Main.ServerOption.Event_SkillPoint = (byte) Value;
					}
				}
				else
				{
					if(Value != 1)
					{
						Config.set("Event.Multiple_Level_Up_SkillPoint", Value);
						PS.sendTitleAllPlayers("\'"+ChatColor.WHITE + "��ų ����Ʈ "+ChatColor.YELLOW +""+ Config.getInt("Event.Multiple_Level_Up_SkillPoint")+ChatColor.WHITE +"�� �̺�Ʈ�� �����մϴ�.\'");
						PS.sendActionBarAllPlayers(ChatColor.BOLD+""+ChatColor.YELLOW +"[�������� ��� ��ų ����Ʈ�� �����˴ϴ�.]");
						GoldBigDragon_RPG.Main.ServerOption.Event_SkillPoint = (byte) Value;
					}
					else
					{
						Bukkit.broadcastMessage(ChatColor.LIGHT_PURPLE+"[Server] : ��ų ����Ʈ  "+ Config.getInt("Event.Multiple_Level_Up_SkillPoint")+"�� �̺�Ʈ�� �����մϴ�.");
						PS.sendTitleAllPlayers("\'"+ChatColor.WHITE + "��ų ����Ʈ "+ChatColor.YELLOW +""+ Config.getInt("Event.Multiple_Level_Up_SkillPoint")+ChatColor.WHITE +"�� �̺�Ʈ�� ����Ǿ����ϴ�.\'");
						PS.sendActionBarAllPlayers(ChatColor.BOLD+""+ChatColor.YELLOW +"[�������� ��� ��ų ����Ʈ�� ������� ���ƿɴϴ�.]");
						Config.set("Event.Multiple_Level_Up_SkillPoint",1);
						GoldBigDragon_RPG.Main.ServerOption.Event_SkillPoint = 1;
					}
				}
				Config.saveConfig();
				new GoldBigDragon_RPG.GUI.EventGUI().EventGUI_Main(player);
				u.clearAll(player);
			}
			return;
		case "STP"://StatPoint
			if(isIntMinMax(message, player, 1, 10))
			{
				int Value = Integer.parseInt(message);
				s.SP(player, Sound.ITEM_PICKUP, 0.8F, 1.0F);
				if(Config.getInt("Event.Multiple_Level_Up_StatPoint") == 1)
				{
					if(Value != 1)
					{
						Config.set("Event.Multiple_Level_Up_StatPoint", Value);
						PS.sendTitleAllPlayers("\'"+ChatColor.WHITE + "���� ����Ʈ "+ChatColor.YELLOW +""+ Config.getInt("Event.Multiple_Level_Up_StatPoint")+ChatColor.WHITE +"�� �̺�Ʈ�� �����մϴ�.\'");
						PS.sendActionBarAllPlayers(ChatColor.BOLD+""+ChatColor.YELLOW +"[�������� ��� ���� ����Ʈ�� �����˴ϴ�.]");
						GoldBigDragon_RPG.Main.ServerOption.Event_StatPoint = (byte) Value;
					}
				}
				else
				{
					if(Value != 1)
					{
						Config.set("Event.Multiple_Level_Up_StatPoint", Value);
						PS.sendTitleAllPlayers("\'"+ChatColor.WHITE + "���� ����Ʈ "+ChatColor.YELLOW +""+ Config.getInt("Event.Multiple_Level_Up_StatPoint")+ChatColor.WHITE +"�� �̺�Ʈ�� �����մϴ�.\'");
						PS.sendActionBarAllPlayers(ChatColor.BOLD+""+ChatColor.YELLOW +"[�������� ��� ���� ����Ʈ�� �����˴ϴ�.]");
						GoldBigDragon_RPG.Main.ServerOption.Event_StatPoint = (byte) Value;
					}
					else
					{
						Bukkit.broadcastMessage(ChatColor.LIGHT_PURPLE+"[Server] : ���� ����Ʈ  "+ Config.getInt("Event.Multiple_Level_Up_StatPoint")+"�� �̺�Ʈ�� �����մϴ�.");
						PS.sendTitleAllPlayers("\'"+ChatColor.WHITE + "���� ����Ʈ "+ChatColor.YELLOW +""+ Config.getInt("Event.Multiple_Level_Up_StatPoint")+ChatColor.WHITE +"�� �̺�Ʈ�� ����Ǿ����ϴ�.\'");
						PS.sendActionBarAllPlayers(ChatColor.BOLD+""+ChatColor.YELLOW +"[�������� ��� ���� ����Ʈ�� ������� ���ƿɴϴ�.]");
						Config.set("Event.Multiple_Level_Up_StatPoint",1);
						GoldBigDragon_RPG.Main.ServerOption.Event_StatPoint = 1;
					}
				}
				Config.saveConfig();
				new GoldBigDragon_RPG.GUI.EventGUI().EventGUI_Main(player);
				u.clearAll(player);
			}
			return;

		case "EXP"://EXP
			if(isIntMinMax(message, player, 1, 10))
			{
				int Value = Integer.parseInt(message);
				s.SP(player, Sound.ITEM_PICKUP, 0.8F, 1.0F);
				if(Config.getInt("Event.Multiple_EXP_Get") == 1)
				{
					if(Value != 1)
					{
						Config.set("Event.Multiple_EXP_Get", Value);
						PS.sendTitleAllPlayers("\'"+ChatColor.WHITE + "����ġ "+ChatColor.YELLOW +""+ Config.getInt("Event.Multiple_EXP_Get")+ChatColor.WHITE +"�� �̺�Ʈ�� �����մϴ�.\'");
						PS.sendActionBarAllPlayers(ChatColor.BOLD+""+ChatColor.YELLOW +"[ȹ���ϴ� ����ġ���� �����˴ϴ�.]");
						GoldBigDragon_RPG.Main.ServerOption.Event_Exp= (byte) Value;
					}
				}
				else
				{
					if(Value != 1)
					{
						Config.set("Event.Multiple_EXP_Get", Value);
						PS.sendTitleAllPlayers("\'"+ChatColor.WHITE + "����ġ "+ChatColor.YELLOW +""+ Config.getInt("Event.Multiple_EXP_Get")+ChatColor.WHITE +"�� �̺�Ʈ�� �����մϴ�.\'");
						PS.sendActionBarAllPlayers(ChatColor.BOLD+""+ChatColor.YELLOW +"[ȹ���ϴ� ����ġ���� �����˴ϴ�.]");
						GoldBigDragon_RPG.Main.ServerOption.Event_Exp= (byte) Value;
					}
					else
					{
						Bukkit.broadcastMessage(ChatColor.LIGHT_PURPLE+"[Server] : ����ġ  "+ Config.getInt("Event.Multiple_EXP_Get")+"�� �̺�Ʈ�� �����մϴ�.");
						PS.sendTitleAllPlayers("\'"+ChatColor.WHITE + "����ġ "+ChatColor.YELLOW +""+ Config.getInt("Event.Multiple_EXP_Get")+ChatColor.WHITE +"�� �̺�Ʈ�� ����Ǿ����ϴ�.\'");
						PS.sendActionBarAllPlayers(ChatColor.BOLD+""+ChatColor.YELLOW +"[ȹ���ϴ� ����ġ���� ������� ���ƿɴϴ�.]");
						Config.set("Event.Multiple_EXP_Get",1);
						GoldBigDragon_RPG.Main.ServerOption.Event_Exp= 1;
					}
				}
				Config.saveConfig();
				new GoldBigDragon_RPG.GUI.EventGUI().EventGUI_Main(player);
				u.clearAll(player);
			}
			return;
		case "DROP"://DropChance
			if(isIntMinMax(message, player, 1, 10))
			{
				int Value = Integer.parseInt(message);
				s.SP(player, Sound.ITEM_PICKUP, 0.8F, 1.0F);
				if(Config.getInt("Event.DropChance") == 1)
				{
					if(Value != 1)
					{
						Config.set("Event.DropChance", Value);
						PS.sendTitleAllPlayers("\'"+ChatColor.WHITE + "����� "+ChatColor.YELLOW +""+ Config.getInt("Event.DropChance")+ChatColor.WHITE +"�� �̺�Ʈ�� �����մϴ�.\'");
						PS.sendActionBarAllPlayers(ChatColor.BOLD+""+ChatColor.YELLOW +"[������ ������� �����˴ϴ�.]");
						GoldBigDragon_RPG.Main.ServerOption.Event_DropChance= (byte) Value;
					}
				}
				else
				{
					if(Value != 1)
					{
						Config.set("Event.DropChance", Value);
						PS.sendTitleAllPlayers("\'"+ChatColor.WHITE + "����� "+ChatColor.YELLOW +""+ Config.getInt("Event.DropChance")+ChatColor.WHITE +"�� �̺�Ʈ�� �����մϴ�.\'");
						PS.sendActionBarAllPlayers(ChatColor.BOLD+""+ChatColor.YELLOW +"[������ ������� �����˴ϴ�.]");
						GoldBigDragon_RPG.Main.ServerOption.Event_DropChance= (byte) Value;
					}
					else
					{
						Bukkit.broadcastMessage(ChatColor.LIGHT_PURPLE+"[Server] : �����  "+ Config.getInt("Event.DropChance")+"�� �̺�Ʈ�� �����մϴ�.");
						PS.sendTitleAllPlayers("\'"+ChatColor.WHITE + "����� "+ChatColor.YELLOW +""+ Config.getInt("Event.DropChance")+ChatColor.WHITE +"�� �̺�Ʈ�� ����Ǿ����ϴ�.\'");
						PS.sendActionBarAllPlayers(ChatColor.BOLD+""+ChatColor.YELLOW +"[������ ������� ������� ���ƿɴϴ�.]");
						Config.set("Event.DropChance",1);
						GoldBigDragon_RPG.Main.ServerOption.Event_DropChance= 1;
					}
				}
				Config.saveConfig();
				new GoldBigDragon_RPG.GUI.EventGUI().EventGUI_Main(player);
				u.clearAll(player);
			}
			return;
		case "Proficiency"://Proficiency
			if(isIntMinMax(message, player, 1, 10))
			{
				int Value = Integer.parseInt(message);
				s.SP(player, Sound.ITEM_PICKUP, 0.8F, 1.0F);
				if(Config.getInt("Event.Multiple_Proficiency_Get") == 1)
				{
					if(Value != 1)
					{
						Config.set("Event.Multiple_Proficiency_Get", Value);
						PS.sendTitleAllPlayers("\'"+ChatColor.WHITE + "���õ� "+ChatColor.YELLOW +""+ Config.getInt("Event.Multiple_Proficiency_Get")+ChatColor.WHITE +"�� �̺�Ʈ�� �����մϴ�.\'");
						PS.sendActionBarAllPlayers(ChatColor.BOLD+""+ChatColor.YELLOW +"[��� ���õ� ��·��� �����˴ϴ�.]");
						GoldBigDragon_RPG.Main.ServerOption.Event_Proficiency= (byte) Value;
					}
				}
				else
				{
					if(Value != 1)
					{
						Config.set("Event.Multiple_Proficiency_Get", Value);
						PS.sendTitleAllPlayers("\'"+ChatColor.WHITE + "���õ� "+ChatColor.YELLOW +""+ Config.getInt("Event.Multiple_Proficiency_Get")+ChatColor.WHITE +"�� �̺�Ʈ�� �����մϴ�.\'");
						PS.sendActionBarAllPlayers(ChatColor.BOLD+""+ChatColor.YELLOW +"[��� ���õ� ��·��� �����˴ϴ�.]");
						GoldBigDragon_RPG.Main.ServerOption.Event_Proficiency= (byte) Value;
					}
					else
					{
						Bukkit.broadcastMessage(ChatColor.LIGHT_PURPLE+"[Server] : ���õ�  "+ Config.getInt("Event.Multiple_Proficiency_Get")+"�� �̺�Ʈ�� �����մϴ�.");
						PS.sendTitleAllPlayers("\'"+ChatColor.WHITE + "���õ� "+ChatColor.YELLOW +""+ Config.getInt("Event.Multiple_Proficiency_Get")+ChatColor.WHITE +"�� �̺�Ʈ�� ����Ǿ����ϴ�.\'");
						PS.sendActionBarAllPlayers(ChatColor.BOLD+""+ChatColor.YELLOW +"[��� ���õ� ��·��� ������� ���ƿɴϴ�.]");
						Config.set("Event.Multiple_Proficiency_Get",1);
						GoldBigDragon_RPG.Main.ServerOption.Event_Proficiency= 1;
					}
				}
				Config.saveConfig();
				new GoldBigDragon_RPG.GUI.EventGUI().EventGUI_Main(player);
				u.clearAll(player);
			}
			return;
		}
		return;
	}

	private void SystemTypeChatting(PlayerChatEvent event)
	{
		Object_UserData u = new Object_UserData();
		Player player = event.getPlayer();

	  	YamlController YC = new YamlController(GoldBigDragon_RPG.Main.Main.plugin);
		YamlManager Config =YC.getNewConfig("config.yml");
		
	    GoldBigDragon_RPG.Effect.Sound s = new GoldBigDragon_RPG.Effect.Sound();
	    event.setCancelled(true);
	    String message = ChatColor.stripColor(event.getMessage());
		switch(u.getString(player, (byte)1))
		{
		case "RO_S_H"://RespawnOption_SpawnPoint_Health
		case "RO_T_H"://RespawnOption_There_Health
		case "RO_H_H"://RespawnOption_Help_Health
		case "RO_I_H"://RespawnOption_Item_Health
			if(isIntMinMax(message, player, 1, 100))
			{
				s.SP(player, Sound.ITEM_PICKUP, 1.0F, 1.0F);
				switch(u.getString(player, (byte)1))
				{
					case "RO_S_H":
						Config.set("Death.Spawn_Home.SetHealth", message+"%");
						u.setString(player, (byte)1, "RO_S_E");
						player.sendMessage(ChatColor.GREEN+"[��Ȱ] : ������ �������� ��Ȱ�� ���, �� %�� "+ChatColor.YELLOW+"����ġ"+ChatColor.GREEN+"�� �Ҿ�������� �ϰڽ��ϱ�?");
						player.sendMessage(ChatColor.GRAY + "(�ּ� 0 ~ �ִ� 100)");
						break;
					case "RO_T_H":
						Config.set("Death.Spawn_Here.SetHealth", message+"%");
						u.setString(player, (byte)1, "RO_T_E");
						player.sendMessage(ChatColor.GREEN+"[��Ȱ] : ���ڸ����� ��Ȱ�� ���, �� %�� "+ChatColor.YELLOW+"����ġ"+ChatColor.GREEN+"�� �Ҿ�������� �ϰڽ��ϱ�?");
						player.sendMessage(ChatColor.GRAY + "(�ּ� 0 ~ �ִ� 100)");
						break;
					case "RO_H_H":
						Config.set("Death.Spawn_Help.SetHealth", message+"%");
						u.setString(player, (byte)1, "RO_H_E");
						player.sendMessage(ChatColor.GREEN+"[��Ȱ] : ������ �޾� ��Ȱ�� ���, �� %�� "+ChatColor.YELLOW+"����ġ"+ChatColor.GREEN+"�� �Ҿ�������� �ϰڽ��ϱ�?");
						player.sendMessage(ChatColor.GRAY + "(�ּ� 0 ~ �ִ� 100)");
						break;
					case "RO_I_H":
						Config.set("Death.Spawn_Item.SetHealth", message+"%");
						u.setString(player, (byte)1, "RO_I_E");
						player.sendMessage(ChatColor.GREEN+"[��Ȱ] : �������� ����Ͽ� ��Ȱ�� ���, �� %�� "+ChatColor.YELLOW+"����ġ"+ChatColor.GREEN+"�� �Ҿ�������� �ϰڽ��ϱ�?");
						player.sendMessage(ChatColor.GRAY + "(�ּ� 0 ~ �ִ� 100)");
						break;
				}
				Config.saveConfig();
			}
			return;
		case "RO_S_E"://RespawnOption_SpawnPoint_EXP
		case "RO_T_E"://RespawnOption_There_EXP
		case "RO_H_E"://RespawnOption_Help_EXP
		case "RO_I_E"://RespawnOption_Item_EXP
			if(isIntMinMax(message, player, 0, 100))
			{
				s.SP(player, Sound.ITEM_PICKUP, 1.0F, 1.0F);
				switch(u.getString(player, (byte)1))
				{
					case "RO_S_E":
						Config.set("Death.Spawn_Home.PenaltyEXP", message+"%");
						u.setString(player, (byte)1, "RO_S_M");
						player.sendMessage(ChatColor.GREEN+"[��Ȱ] : ������ �������� ��Ȱ�� ���, �� %�� "+ChatColor.YELLOW+"��"+ChatColor.GREEN+"�� �Ҿ�������� �ϰڽ��ϱ�?");
						player.sendMessage(ChatColor.GRAY + "(�ּ� 0 ~ �ִ� 100)");
						break;
					case "RO_T_E":
						Config.set("Death.Spawn_Here.PenaltyEXP", message+"%");
						u.setString(player, (byte)1, "RO_T_M");
						player.sendMessage(ChatColor.GREEN+"[��Ȱ] : ���ڸ����� ��Ȱ�� ���, �� %�� "+ChatColor.YELLOW+"��"+ChatColor.GREEN+"�� �Ҿ�������� �ϰڽ��ϱ�?");
						player.sendMessage(ChatColor.GRAY + "(�ּ� 0 ~ �ִ� 100)");
						break;
					case "RO_H_E":
						Config.set("Death.Spawn_Help.PenaltyEXP", message+"%");
						u.setString(player, (byte)1, "RO_H_M");
						player.sendMessage(ChatColor.GREEN+"[��Ȱ] : ������ �޾� ��Ȱ�� ���, �� %�� "+ChatColor.YELLOW+"��"+ChatColor.GREEN+"�� �Ҿ�������� �ϰڽ��ϱ�?");
						player.sendMessage(ChatColor.GRAY + "(�ּ� 0 ~ �ִ� 100)");
						break;
					case "RO_I_E":
						Config.set("Death.Spawn_Item.PenaltyEXP", message+"%");
						u.setString(player, (byte)1, "RO_I_M");
						player.sendMessage(ChatColor.GREEN+"[��Ȱ] : �������� ����Ͽ� ��Ȱ�� ���, �� %�� "+ChatColor.YELLOW+"��"+ChatColor.GREEN+"�� �Ҿ�������� �ϰڽ��ϱ�?");
						player.sendMessage(ChatColor.GRAY + "(�ּ� 0 ~ �ִ� 100)");
						break;
				}
				Config.saveConfig();
			}
			return;
		case "RO_S_M"://RespawnOption_SpawnPoint_Money
		case "RO_T_M"://RespawnOption_There_Money
		case "RO_H_M"://RespawnOption_Help_Money
		case "RO_I_M"://RespawnOption_Item_Money
			if(isIntMinMax(message, player, 0, 100))
			{
				s.SP(player, Sound.ANVIL_USE, 1.0F, 1.0F);
				switch(u.getString(player, (byte)1))
				{
					case "RO_S_M":
						Config.set("Death.Spawn_Home.PenaltyMoney", message+"%");
						break;
					case "RO_T_M":
						Config.set("Death.Spawn_Here.PenaltyMoney", message+"%");
						break;
					case "RO_H_M":
						Config.set("Death.Spawn_Help.PenaltyMoney", message+"%");
						break;
					case "RO_I_M":
						Config.set("Death.Spawn_Item.PenaltyMoney", message+"%");
						break;
				}
				Config.saveConfig();
				u.clearAll(player);
				new GoldBigDragon_RPG.GUI.OPBoxGUI().OPBoxGUI_Death(player);
			}
			return;
		case "CCP"://ChangeChatPrefix
			s.SP(player, Sound.ITEM_PICKUP, 1.0F, 1.0F);
			Config.set("Server.ChatPrefix", event.getMessage());
			Config.saveConfig();
			u.clearAll(player);
			new GoldBigDragon_RPG.GUI.OPBoxGUI().OPBoxGUI_Setting(player);
			return;
		case "BMT"://BroadcastMessageTick
			if(isIntMinMax(message, player, 1, 3600))
			{
				s.SP(player, Sound.ITEM_PICKUP, 1.0F, 1.0F);
				Config.set("Server.BroadCastSecond", Integer.parseInt(message));
				Config.saveConfig();
				new GoldBigDragon_RPG.GUI.OPBoxGUI().OPBoxGUI_BroadCast(player, (byte) 0);
				u.clearAll(player);
			}
			return;
		case "NBM"://NewBroadcastMessage
			s.SP(player, Sound.ITEM_PICKUP, 1.0F, 1.0F);
			YamlManager BroadCast =YC.getNewConfig("BroadCast.yml");
			BroadCast.set(u.getInt(player, (byte)0)+"", ChatColor.WHITE+event.getMessage());
			BroadCast.saveConfig();
			u.clearAll(player);
			new GoldBigDragon_RPG.GUI.OPBoxGUI().OPBoxGUI_BroadCast(player, (byte) 0);
			return;
		case "JM"://JoinMessage
			s.SP(player, Sound.ITEM_PICKUP, 1.0F, 1.0F);
			if(message.equals("����"))
				Config.set("Server.JoinMessage", null);
			else
				Config.set("Server.JoinMessage", ChatColor.WHITE+event.getMessage());
			Config.saveConfig();
			u.clearAll(player);
			new GoldBigDragon_RPG.GUI.OPBoxGUI().OPBoxGUI_Setting(player);
			return;
		case "QM"://QuitMessage
			s.SP(player, Sound.ITEM_PICKUP, 1.0F, 1.0F);
			if(message.equals("����"))
				Config.set("Server.QuitMessage", null);
			else
				Config.set("Server.QuitMessage", ChatColor.WHITE+event.getMessage());
			Config.saveConfig();
			u.clearAll(player);
			new GoldBigDragon_RPG.GUI.OPBoxGUI().OPBoxGUI_Setting(player);
			return;
		case "CSN"://ChangeStatName
			{
				message.replace(".", "");
				message.replace(":", "");
				message.replace(" ", "");
				String Message = event.getMessage();
				Message.replace(".", "");
				Message.replace(":", "");
				s.SP(player, Sound.ITEM_PICKUP, 1.0F, 1.0F);
				switch(u.getString(player, (byte)2))
				{
				case "ü��":
					Config.set("Server.STR", message);
					break;
				case "ü�� ����":
					Config.set("Server.STR_Lore", Message);
					break;
				case "�ؾ�":
					Config.set("Server.DEX", message);
					break;
				case "�ؾ� ����":
					Config.set("Server.DEX_Lore", Message);
					break;
				case "����":
					Config.set("Server.INT", message);
					break;
				case "���� ����":
					Config.set("Server.INT_Lore", Message);
					break;
				case "����":
					Config.set("Server.WILL", message);
					break;
				case "���� ����":
					Config.set("Server.WILL_Lore", Message);
					break;
				case "���":
					Config.set("Server.LUK", message);
					break;
				case "��� ����":
					Config.set("Server.LUK_Lore", Message);
					break;
				case "�����":
					Config.set("Server.Damage", message);
					break;
				case "���� �����":
					Config.set("Server.MagicDamage", message);
					break;
				case "ȭ��":
					String Pa = event.getMessage();
					Pa.replace(".", "");
					Pa.replace(":", "");
					Pa.replace(" ", "");
					Config.set("Server.MoneyName", Pa);
					Config.saveConfig();
					u.clearAll(player);
					GoldBigDragon_RPG.Main.ServerOption.Money = Pa;
					new GoldBigDragon_RPG.GUI.OPBoxGUI().OPBoxGUI_Money(player);
					return;
				}
				Config.saveConfig();
				u.clearAll(player);
				player.sendMessage(ChatColor.GREEN + "[System] : ����� ������ ���� ������ ����, ���� ���ε� ���� �ϰ� ����˴ϴ�.");
				new GoldBigDragon_RPG.GUI.OPBoxGUI().OPBoxGUI_StatChange(player);
			}
			return;
	
			case "CDML": //Change Drop Money Limit
			{
				if(isIntMinMax(message, player, 1000, 100000000))
				{
					int value = Integer.parseInt(message);
					GoldBigDragon_RPG.Main.ServerOption.MaxDropMoney = value;
					Config.set("Server.Max_Drop_Money",value);
					Config.saveConfig();
					u.clearAll(player);
					new GoldBigDragon_RPG.GUI.OPBoxGUI().OPBoxGUI_Money(player);
				}
			}
			return;
			case "CMID": //Change Money ID
			{
				if(isIntMinMax(message, player, 1, Integer.MAX_VALUE))
				{
					if(new GoldBigDragon_RPG.Event.Interact().SetItemDefaultName(Short.parseShort(message),(byte)0).compareTo("�������� ���� ������")==0)
					{
						player.sendMessage(ChatColor.RED + "[SYSTEM] : �ش� �������� �������� �ʽ��ϴ�!");
		  				s.SP(player, org.bukkit.Sound.ORB_PICKUP, 2.0F, 1.7F);
		  				return;
					}
					int value = Integer.parseInt(message);
					switch(u.getInt(player, (byte)1))
					{
					case 50:
						Config.set("Server.Money.1.ID",value);
						GoldBigDragon_RPG.Main.ServerOption.Money1ID = (short) value;
						break;
					case 100:
						Config.set("Server.Money.2.ID",value);
						GoldBigDragon_RPG.Main.ServerOption.Money2ID = (short) value;
						break;
					case 1000:
						Config.set("Server.Money.3.ID",value);
						GoldBigDragon_RPG.Main.ServerOption.Money3ID = (short) value;
						break;
					case 10000:
						Config.set("Server.Money.4.ID",value);
						GoldBigDragon_RPG.Main.ServerOption.Money4ID = (short) value;
						break;
					case 50000:
						Config.set("Server.Money.5.ID",value);
						GoldBigDragon_RPG.Main.ServerOption.Money5ID = (short) value;
						break;
					case -1:
						Config.set("Server.Money.6.ID",value);
						GoldBigDragon_RPG.Main.ServerOption.Money6ID = (short) value;
						break;
					}
					Config.saveConfig();
					player.sendMessage(ChatColor.DARK_AQUA+"[System] : ȭ�� ������� ������ ������ DATA�� �Է� �� �ּ���!");
					u.setString(player, (byte)1, "CMDATA");
				}
			}
			return;
			case "CMDATA": //Change Money DATA
			{
				if(isIntMinMax(message, player, 0, Integer.MAX_VALUE))
				{
					int value = Integer.parseInt(message);
					switch(u.getInt(player, (byte)1))
					{
					case 50:
						Config.set("Server.Money.1.DATA",value);
						GoldBigDragon_RPG.Main.ServerOption.Money1DATA = (byte) value;
						break;
					case 100:
						Config.set("Server.Money.2.DATA",value);
						GoldBigDragon_RPG.Main.ServerOption.Money2DATA = (byte) value;
						break;
					case 1000:
						Config.set("Server.Money.3.DATA",value);
						GoldBigDragon_RPG.Main.ServerOption.Money3DATA = (byte) value;
						break;
					case 10000:
						Config.set("Server.Money.4.DATA",value);
						GoldBigDragon_RPG.Main.ServerOption.Money4DATA = (byte) value;
						break;
					case 50000:
						Config.set("Server.Money.5.DATA",value);
						GoldBigDragon_RPG.Main.ServerOption.Money5DATA = (byte) value;
						break;
					case -1:
						Config.set("Server.Money.6.DATA",value);
						GoldBigDragon_RPG.Main.ServerOption.Money6DATA = (byte) value;
						break;
					}
					Config.saveConfig();
					u.clearAll(player);
					new GoldBigDragon_RPG.GUI.OPBoxGUI().OPBoxGUI_Money(player);
				}
			}
			return;
		}
		return;
	}

	private void NaviTypeChatting(PlayerChatEvent event)
	{
		Object_UserData u = new Object_UserData();
		Player player = event.getPlayer();

	  	YamlController YC = new YamlController(GoldBigDragon_RPG.Main.Main.plugin);
		YamlManager NavigationConfig =YC.getNewConfig("Navigation/NavigationList.yml");

	    GoldBigDragon_RPG.Effect.Sound s = new GoldBigDragon_RPG.Effect.Sound();
	    event.setCancelled(true);
	    String message = ChatColor.stripColor(event.getMessage());
		switch(u.getString(player, (byte)0))
		{
		case "NN"://NewNavigation
			s.SP(player, Sound.ITEM_PICKUP, 1.0F, 1.0F);
			long UTC = new GoldBigDragon_RPG.Util.ETC().getNowUTC();
			NavigationConfig.set(UTC+".Name", event.getMessage());
			NavigationConfig.set(UTC+".world", player.getLocation().getWorld().getName());
			NavigationConfig.set(UTC+".x", (int)player.getLocation().getX());
			NavigationConfig.set(UTC+".y", (short)player.getLocation().getY());
			NavigationConfig.set(UTC+".z", (int)player.getLocation().getZ());
			NavigationConfig.set(UTC+".time", -1);
			NavigationConfig.set(UTC+".sensitive", 5);
			NavigationConfig.set(UTC+".onlyOPuse", true);
			NavigationConfig.set(UTC+".ShowArrow", 0);
			NavigationConfig.saveConfig();
			u.clearAll(player);
			new GoldBigDragon_RPG.GUI.NavigationGUI().NavigationOptionGUI(player,UTC+"");
			return;
		case "CNN"://ChangeNavigationName�̸� ����
			s.SP(player, Sound.ITEM_PICKUP, 1.0F, 1.0F);
			NavigationConfig.set(u.getString(player, (byte)1)+".Name", event.getMessage());
			NavigationConfig.saveConfig();
			new GoldBigDragon_RPG.GUI.NavigationGUI().NavigationOptionGUI(player,u.getString(player, (byte)1));
			u.clearAll(player);
			return;
		case "CNT"://ChangeNavigationTimer���� �ð�
			if(isIntMinMax(message, player, -1, 3600))
			{
				s.SP(player, Sound.ITEM_PICKUP, 1.0F, 1.0F);
				NavigationConfig.set(u.getString(player, (byte)1)+".time", Integer.parseInt(message));
				NavigationConfig.saveConfig();
				new GoldBigDragon_RPG.GUI.NavigationGUI().NavigationOptionGUI(player,u.getString(player, (byte)1));
				u.clearAll(player);
			}
			return;
		case "CNS"://ChangeNavigationSensitive���� �ݰ�
			if(isIntMinMax(message, player, 1, 1000))
			{
				s.SP(player, Sound.ITEM_PICKUP, 1.0F, 1.0F);
				NavigationConfig.set(u.getString(player, (byte)1)+".sensitive", Integer.parseInt(message));
				NavigationConfig.saveConfig();
				new GoldBigDragon_RPG.GUI.NavigationGUI().NavigationOptionGUI(player,u.getString(player, (byte)1));
				u.clearAll(player);
			}
			return;
		case "CNA"://ChangeNavigationArrow�׺� Ÿ��
			if(isIntMinMax(message, player, 0, 10))
			{
				s.SP(player, Sound.ITEM_PICKUP, 1.0F, 1.0F);
				NavigationConfig.set(u.getString(player, (byte)1)+".ShowArrow", Integer.parseInt(message));
				NavigationConfig.saveConfig();
				new GoldBigDragon_RPG.GUI.NavigationGUI().NavigationOptionGUI(player,u.getString(player, (byte)1));
				u.clearAll(player);
			}
			return;
		}
		return;
	}
	
	private void GambleChatting(PlayerChatEvent event)
	{
		Object_UserData u = new Object_UserData();
		Player player = event.getPlayer();

	  	YamlController YC = new YamlController(GoldBigDragon_RPG.Main.Main.plugin);
		YamlManager GambleYML =YC.getNewConfig("Item/GamblePresent.yml");

	    GoldBigDragon_RPG.Effect.Sound s = new GoldBigDragon_RPG.Effect.Sound();
	    event.setCancelled(true);
	    String message = ChatColor.stripColor(event.getMessage().replace(".",""));
		switch(u.getString(player, (byte)0))
		{
		case "NP"://New Package
			{
				if(GambleYML.contains(message))
				{
					s.SP(player, Sound.ORB_PICKUP, 1.0F, 1.8F);
					player.sendMessage(ChatColor.RED+"[����] : �ش� �̸��� ��ǰ�� �̹� �����մϴ�!");
					return;
				}
				s.SP(player, Sound.ITEM_PICKUP, 1.0F, 1.0F);
				GambleYML.set(message+".Grade", ChatColor.WHITE+"[�Ϲ�]");
				GambleYML.set(message+".Present.1", null);
				GambleYML.saveConfig();
				u.clearAll(player);
				new GoldBigDragon_RPG.GUI.GambleGUI().GamblePresentGUI(player, (short)0, (byte)0, (short)-1, null);
			}
			return;
		}
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
				player.sendMessage(ChatColor.RED + "[SYSTEM] : O Ȥ�� X�� �Է� �� �ּ���!");
				sound.SP(player, org.bukkit.Sound.ORB_PICKUP, 2.0F, 1.7F);
			}
			
		}
		else
		{
			player.sendMessage(ChatColor.RED + "[SYSTEM] : O Ȥ�� X�� �Է� �� �ּ���!");
			sound.SP(player, org.bukkit.Sound.ORB_PICKUP, 2.0F, 1.7F);
		}
		return -1;
	}
}