package GoldBigDragon_RPG.Party;

import org.bukkit.ChatColor;
import org.bukkit.Sound;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import GoldBigDragon_RPG.Command.HelpMessage;
import GoldBigDragon_RPG.Util.ETC;
import GoldBigDragon_RPG.Util.YamlController;
import GoldBigDragon_RPG.Util.YamlManager;

public class PartyCommand extends HelpMessage
{
	public void onCommand(CommandSender talker, Command command, String string, String[] args)
    {
	    GoldBigDragon_RPG.Effect.Sound s = new GoldBigDragon_RPG.Effect.Sound();
			Player player = (Player) talker;
			if(args.length == 0)
			{
				s.SP((Player)talker, org.bukkit.Sound.HORSE_ARMOR, 0.8F, 1.8F);
				new GoldBigDragon_RPG.Party.PartyGUI().PartyGUI_Main(player); return;
			}
			if(args.length <= 1)
			{
				switch(args[0])
				{
					case "���":
						{
						 	s.SP((Player)talker, org.bukkit.Sound.HORSE_ARMOR, 0.8F, 1.8F);
						 	new GoldBigDragon_RPG.Party.PartyGUI().PartyListGUI(player, (short) 0);
						}
						return;
					case "Ż��":
						{
							if(GoldBigDragon_RPG.Main.ServerOption.PartyJoiner.containsKey(player))
								GoldBigDragon_RPG.Main.ServerOption.Party.get(GoldBigDragon_RPG.Main.ServerOption.PartyJoiner.get(player)).QuitParty(player);
							else
							{
								s.SP(player, Sound.ORB_PICKUP, 1.0F, 1.8F);
								player.sendMessage(ChatColor.RED + "[��Ƽ] : ����� ��Ƽ�� �������� ���� �����Դϴ�!");
							}
						}
						return;
					case "����":
						{
							if(GoldBigDragon_RPG.Main.ServerOption.PartyJoiner.containsKey(player))
								GoldBigDragon_RPG.Main.ServerOption.Party.get(GoldBigDragon_RPG.Main.ServerOption.PartyJoiner.get(player)).getPartyInformation();
							else
							{
								s.SP(player, Sound.ORB_PICKUP, 1.0F, 1.8F);
								player.sendMessage(ChatColor.RED + "[��Ƽ] : ����� ��Ƽ�� �������� ���� �����Դϴ�!");
							}
						}
						return;
					case "���":
						{
							if(GoldBigDragon_RPG.Main.ServerOption.PartyJoiner.containsKey(player))
								GoldBigDragon_RPG.Main.ServerOption.Party.get(GoldBigDragon_RPG.Main.ServerOption.PartyJoiner.get(player)).ChangeLock(player);
							else
							{
								s.SP(player, Sound.ORB_PICKUP, 1.0F, 1.8F);
								player.sendMessage(ChatColor.RED + "[��Ƽ] : ����� ��Ƽ�� �������� ���� �����Դϴ�!");
							}
						}
						return;
					default :
						{
							HelpMessager(player, (byte) 3);
						}
						return;
				}
			}
			else
			{
				switch(args[0])
				{
					case "����":
						{
							if(GoldBigDragon_RPG.Main.ServerOption.PartyJoiner.containsKey(player)==false)
							{
								ETC e = new ETC();
			  					long nowSec = e.getSec();
				  				if(args.length >= 3)
				  				{
				  					String SB=null;
				  					for(byte a = 1; a<= ((args.length)-1);a++)
				  					{
				  						if(a == (args.length)-2)
				  							SB=SB+args[a]+" ";
				  						else
				  							SB=SB+args[a];
				  					}
				  					GoldBigDragon_RPG.Main.ServerOption.Party.put(nowSec, new PartyDataObject(nowSec, player, SB.toString()));
				  				}
				  				else
				  					GoldBigDragon_RPG.Main.ServerOption.Party.put(nowSec, new PartyDataObject(nowSec, player, args[1]));
				  				s.SP(player, Sound.DOOR_OPEN, 1.0F, 1.1F);
				  				new GoldBigDragon_RPG.Party.PartyGUI().PartyGUI_Main(player);
							}
							else
							{
								s.SP(player, Sound.ORB_PICKUP, 1.0F, 1.8F);
								player.sendMessage(ChatColor.RED + "[��Ƽ] : ����� �̹� ��Ƽ�� ������ �����Դϴ�!");
							}
						}
						return;
					case "����":
						{
			  				if(args.length >= 3)
			  				{
			  					String SB=null;
			  					for(byte a = 1; a<= ((args.length)-1);a++)
			  					{
			  						if(a == (args.length)-2)
			  							SB=SB+args[a]+" ";
			  						else
			  							SB=SB+args[a];
			  					}
								GoldBigDragon_RPG.Main.ServerOption.Party.get(GoldBigDragon_RPG.Main.ServerOption.PartyJoiner.get(player)).ChangeTitle(player, SB.toString());
			  				}
			  				else
								GoldBigDragon_RPG.Main.ServerOption.Party.get(GoldBigDragon_RPG.Main.ServerOption.PartyJoiner.get(player)).ChangeTitle(player, args[1]);
						}
						return;
					case "����":
						{
							if(GoldBigDragon_RPG.Main.ServerOption.PartyJoiner.containsKey(player))
							{
				  				if(args.length >= 3)
				  				{
				  					HelpMessager(player, (byte) 3); return;
				  				}
								GoldBigDragon_RPG.Main.ServerOption.Party.get(GoldBigDragon_RPG.Main.ServerOption.PartyJoiner.get(player)).ChangeLeader(player, args[1]);
							}
							else
							{
								s.SP(player, Sound.ORB_PICKUP, 1.0F, 1.8F);
								player.sendMessage(ChatColor.RED + "[��Ƽ] : ����� ��Ƽ�� �������� ���� �����Դϴ�!");
							}
						}
						return;
					case "�ο�":
						{
							if(GoldBigDragon_RPG.Main.ServerOption.PartyJoiner.containsKey(player))
							{
				  				if(args.length >= 3)
				  					HelpMessager(player, (byte) 3);
				  				else
			  					{
				  					YamlController YC = new YamlController(GoldBigDragon_RPG.Main.Main.plugin);
				  					YamlManager Config = YC.getNewConfig("config.yml");
				  					if(isIntMinMax(args[1], player, 2, Config.getInt("Party.MaxPartyUnit")))
				  						GoldBigDragon_RPG.Main.ServerOption.Party.get(GoldBigDragon_RPG.Main.ServerOption.PartyJoiner.get(player)).ChangeMaxCpacity(player, (byte) Integer.parseInt(args[1]));
			  					}
							}
							else
							{
								s.SP(player, Sound.ORB_PICKUP, 1.0F, 1.8F);
								player.sendMessage(ChatColor.RED + "[��Ƽ] : ����� ��Ƽ�� �������� ���� �����Դϴ�!");
							}
						}
						return;
					case "����":
						{
							if(GoldBigDragon_RPG.Main.ServerOption.PartyJoiner.containsKey(player))
							{
				  				if(args.length >= 3)
				  				{
				  					HelpMessager(player, (byte) 3); return;
				  				}
								GoldBigDragon_RPG.Main.ServerOption.Party.get(GoldBigDragon_RPG.Main.ServerOption.PartyJoiner.get(player)).KickPartyMember(player, args[1]);
							}
							else
							{
								s.SP(player, Sound.ORB_PICKUP, 1.0F, 1.8F);
								player.sendMessage(ChatColor.RED + "[��Ƽ] : ����� ��Ƽ�� �������� ���� �����Դϴ�!");
							}
						}
						return;
					default :
						{
							HelpMessager(player, (byte) 3);
						}
						return;
				}
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

}
