package GoldBigDragon_RPG.Quest;

import org.bukkit.ChatColor;
import org.bukkit.Sound;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import GoldBigDragon_RPG.Command.HelpMessage;
import GoldBigDragon_RPG.Util.YamlController;
import GoldBigDragon_RPG.Util.YamlManager;

public class QuestCommand extends HelpMessage
{
	public void onCommand(CommandSender talker, Command command, String string, String[] args)
	{
		Player player = (Player) talker;
		if(args.length==0)
		{
			new GoldBigDragon_RPG.Quest.QuestGUI().MyQuestListGUI(player, (short) 0);
			return;
		}
		if(talker.isOp() == true)
		{
			if(player.isOp() == true)
			{
			    YamlController YC = new YamlController(GoldBigDragon_RPG.Main.Main.plugin);
			    YamlManager QuestConfig=YC.getNewConfig("Quest/QuestList.yml");
		    	if(YC.isExit("Quest/QuestList.yml")==false)
		    	{
		    		QuestConfig.set("Do_not_Touch_This", true);
		    		QuestConfig.saveConfig();
		    	}

			    
				switch(ChatColor.stripColor(args[0]))
				{
		  			case "구성" :
		  			{
		  					s.SP(player, Sound.NOTE_PLING, 1.0F, 0.8F);
		  					new GoldBigDragon_RPG.Quest.QuestGUI().AllOfQuestListGUI(player, (short) 0,false);
		  			}
		  			break;
			  		case "생성" :
				  		{
				  			if(args.length <= 2)
				  			{
								HelpMessager((Player)talker,(byte) 8);
							  	return;
				  			}
				  			if(args[1].equalsIgnoreCase("일반")||args[1].equalsIgnoreCase("반복")||args[1].equalsIgnoreCase("일일")
				  			||args[1].equalsIgnoreCase("일주")||args[1].equalsIgnoreCase("한달"))
				  			{
							  	StringBuffer QN = new StringBuffer();
							    for(byte i =2; i<args.length-1;i++)
						    	{
							    	QN.append(args[i]+" ");
						    	}
							    QN.append(args[args.length-1]);
							    String QuestName = QN.toString().replace(".", "");
							    String QuestNameString = ChatColor.stripColor(QuestName);
								if(QuestConfig.contains(QuestNameString))
						    	{
								  	s.SP(player, org.bukkit.Sound.ORB_PICKUP, 2.0F, 1.7F);
							    	player.sendMessage(ChatColor.RED+"[SYSTEM] : 해당 이름의 퀘스트가 이미 존재합니다!");
								    return;
						    	}
								String QuestType = null;
								switch(args[1])
								{
									case "일반" : QuestType = "N"; break;
									case "반복" : QuestType = "R"; break;
									case "일일" : QuestType = "D"; break;
									case "일주" : QuestType = "W"; break;
									case "한달" : QuestType = "M"; break;
								}
								QuestConfig.set(QuestNameString + ".QuestMaker", player.getName());
								QuestConfig.set(QuestNameString + ".Type", QuestType);
								QuestConfig.set(QuestNameString + ".Server.Limit", 0);
								QuestConfig.set(QuestNameString + ".Need.LV", 0);
								QuestConfig.set(QuestNameString + ".Need.Love", 0);
								QuestConfig.set(QuestNameString + ".Need.Skill.0", null);
								QuestConfig.set(QuestNameString + ".Need.STR", 0);
								QuestConfig.set(QuestNameString + ".Need.DEX", 0);
								QuestConfig.set(QuestNameString + ".Need.INT", 0);
								QuestConfig.set(QuestNameString + ".Need.WILL", 0);
								QuestConfig.set(QuestNameString + ".Need.LUK", 0);
								QuestConfig.set(QuestNameString + ".Need.PrevQuest", "null");
								QuestConfig.set(QuestNameString + ".FlowChart.0", null);
								QuestConfig.saveConfig();
							    player.sendMessage(ChatColor.GREEN+"[SYSTEM] : "+ChatColor.YELLOW+QuestNameString+ChatColor.DARK_AQUA+" 퀘스트가 생성되었습니다!");
			  					s.SP(player, Sound.NOTE_PLING, 1.0F, 0.8F);
			  					new GoldBigDragon_RPG.Quest.QuestGUI().FixQuestGUI(player, (short) 0, QuestNameString);
				  			}
				  			else
							HelpMessager((Player)talker,(byte) 8);
				  		}
				  		return;
				    default:
						{
							HelpMessager((Player)talker,(byte) 8);
						}
				  		return;
				}
			}
			else
			{
				talker.sendMessage(ChatColor.RED + "[SYSTEM] : 해당 명령어를 실행하기 위해서는 관리자 권한이 필요합니다!");
				s.SP((Player)talker, org.bukkit.Sound.ORB_PICKUP, 2.0F, 1.7F);
	  			return;
			}
		}
	}
}