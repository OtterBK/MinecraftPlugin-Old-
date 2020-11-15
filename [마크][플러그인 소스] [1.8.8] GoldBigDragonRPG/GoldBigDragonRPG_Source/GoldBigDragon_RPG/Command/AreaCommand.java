package GoldBigDragon_RPG.Command;

import org.bukkit.ChatColor;
import org.bukkit.Sound;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import GoldBigDragon_RPG.Util.YamlController;
import GoldBigDragon_RPG.Util.YamlManager;

public class AreaCommand extends HelpMessage
{
	public void onCommand(CommandSender talker, Command command, String string, String[] args)
	{
	    GoldBigDragon_RPG.Effect.Sound s = new GoldBigDragon_RPG.Effect.Sound();
		Player player = (Player) talker;
		if(player.isOp() == false)
		{
			talker.sendMessage(ChatColor.RED + "[SYSTEM] : �ش� ��ɾ �����ϱ� ���ؼ��� ������ ������ �ʿ��մϴ�!");
			s.SP((Player)talker, org.bukkit.Sound.ORB_PICKUP, 2.0F, 1.7F);
			return;
		}
		if(args.length == 1)
		{
			if(args[0].equalsIgnoreCase("���"))
			{
				GoldBigDragon_RPG.GUI.AreaGUI AGUI = new GoldBigDragon_RPG.GUI.AreaGUI();
				s.SP(player, org.bukkit.Sound.HORSE_SADDLE, 1.0F, 1.8F);
				AGUI.AreaListGUI(player, (short) 0);
				return;
			}
			else
			{
			  	YamlController YC = new YamlController(GoldBigDragon_RPG.Main.Main.plugin);
				YamlManager AreaList = YC.getNewConfig("Area/AreaList.yml");
				
				if(AreaList.contains(args[0]))
				{
					s.SP(player, Sound.HORSE_SADDLE, 1.0F, 1.8F);
					GoldBigDragon_RPG.GUI.AreaGUI AGUI = new GoldBigDragon_RPG.GUI.AreaGUI();
					AGUI.AreaGUI_Main(player, args[0]);
				}
				else
				{
					s.SP(player, org.bukkit.Sound.ORB_PICKUP, 2.0F, 1.7F);
					player.sendMessage(ChatColor.RED + "[SYSTEM] : �ش� �̸��� ������ �����ϴ�!");
				}
				return;
			}
		}
		if(args.length == 2)
		{
			GoldBigDragon_RPG.ETC.Area A = new GoldBigDragon_RPG.ETC.Area();
			switch(args[1])
			{
			case "����" :
				if(GoldBigDragon_RPG.Main.ServerOption.catchedLocation1.containsKey(player)==true && GoldBigDragon_RPG.Main.ServerOption.catchedLocation2.containsKey(player) ==true)
				{
					A.CreateNewArea(player, GoldBigDragon_RPG.Main.ServerOption.catchedLocation1.get(player), GoldBigDragon_RPG.Main.ServerOption.catchedLocation2.get(player), args[0]);
					return;
				}
				else
				{
					GoldBigDragon_RPG.Event.Interact IT = new GoldBigDragon_RPG.Event.Interact();
				  	YamlController YC = new YamlController(GoldBigDragon_RPG.Main.Main.plugin);
				  	YamlManager Config = YC.getNewConfig("config.yml");
					player.sendMessage(ChatColor.RED + "[SYSTEM] : ���� " + IT.SetItemDefaultName((short) Config.getInt("Server.AreaSettingWand"),(byte)0) +ChatColor.RED+" �������� �տ� �� ä�� ����� ��/�� Ŭ���Ͽ� ������ ������ �ּ���!");
					s.SP((Player)player, org.bukkit.Sound.ORB_PICKUP, 2.0F, 1.7F);
				}
				return;
			case "����" :
				A.RemoveArea(player, args[0]);
				return;
			}
		}
		if(args.length <= 2)
		{
			HelpMessager(player, (byte) 6);
			return;
		}
		else
		{
			GoldBigDragon_RPG.ETC.Area A = new GoldBigDragon_RPG.ETC.Area();
			String SB = "";
			for(byte a =2; a<= ((args.length)-1);a++)
				SB = SB+args[a]+" ";
			switch(args[1])
			{
			case "�̸�" :
				A.OptionSetting(player, args[0],(char) 0, SB);
				return;
			case "����" :
				A.OptionSetting(player, args[0],(char) 1, SB);
				return;
			}
		}
	}
}
