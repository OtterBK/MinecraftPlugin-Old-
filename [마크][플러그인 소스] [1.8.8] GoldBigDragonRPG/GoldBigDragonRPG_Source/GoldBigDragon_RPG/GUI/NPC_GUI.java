package GoldBigDragon_RPG.GUI;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;
import java.util.Set;

import org.bukkit.entity.Player;
import org.apache.logging.log4j.core.jmx.Server;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.entity.Damageable;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.potion.PotionEffectType;

import GoldBigDragon_RPG.Main.ServerOption;
import GoldBigDragon_RPG.Main.Object_UserData;
import GoldBigDragon_RPG.Util.Number;
import GoldBigDragon_RPG.Util.YamlController;
import GoldBigDragon_RPG.Util.YamlManager;

public class NPC_GUI extends GUIutil
{
	private GoldBigDragon_RPG.Effect.Sound s = new GoldBigDragon_RPG.Effect.Sound();
	private GoldBigDragon_RPG.ETC.NPC NPC = new GoldBigDragon_RPG.ETC.NPC();
	
	public void MainGUI(Player player, String NPCname, boolean isOP)
	{
		Inventory inv = Bukkit.createInventory(null, 27, ChatColor.BLACK + "[NPC] "+ChatColor.stripColor(NPCname));
		Object_UserData u = new Object_UserData();
		Stack2(ChatColor.WHITE +""+ChatColor.BOLD + "��ȭ�� �Ѵ�", 340,0,1,Arrays.asList(ChatColor.YELLOW + ""+ChatColor.stripColor(NPCname)+ChatColor.GRAY +"����",ChatColor.GRAY + "��ȭ�� �մϴ�."), 10, inv);
		Stack2(ChatColor.WHITE +""+ChatColor.BOLD + "�ŷ��� �Ѵ�", 371,0,1,Arrays.asList(ChatColor.YELLOW + ""+ChatColor.stripColor(NPCname)+ChatColor.GRAY +"����",ChatColor.GRAY + "�ŷ��� ��û�մϴ�."), 12, inv);
		Stack2(ChatColor.WHITE +""+ChatColor.BOLD + "����Ʈ", 386,0,1,Arrays.asList(ChatColor.YELLOW + ""+ChatColor.stripColor(NPCname)+ChatColor.GRAY +"����",ChatColor.GRAY + "���� ���� ������",ChatColor.GRAY + "����ϴ�."), 14, inv);

		YamlController YC = new YamlController(GoldBigDragon_RPG.Main.Main.plugin);
	  	YamlManager PlayerNPC = null;
    	if(YC.isExit("NPC/PlayerData/"+player.getUniqueId()+".yml")==false)
    	{
    		PlayerNPC=YC.getNewConfig("NPC/PlayerData/"+player.getUniqueId()+".yml");
    		PlayerNPC.set(u.getNPCuuid(player)+".love", 0);
    		PlayerNPC.set(u.getNPCuuid(player)+".Career", 0);
    		PlayerNPC.saveConfig();
    	}
    	else
    		PlayerNPC=YC.getNewConfig("NPC/PlayerData/"+player.getUniqueId()+".yml");
		Stack2(ChatColor.WHITE +""+ChatColor.BOLD + "�����ϱ�", 54,0,1,Arrays.asList(ChatColor.YELLOW + ""+ChatColor.stripColor(NPCname)+ChatColor.GRAY +"����",ChatColor.GRAY + "�ڽ��� ������ �ִ�",ChatColor.GRAY + "�������� �����մϴ�.",ChatColor.GRAY + "(NPC���� ȣ���� ���)","",
				ChatColor.LIGHT_PURPLE+"[���� ȣ����]",ChatColor.RED+""+ChatColor.BOLD+" �� "+ChatColor.WHITE+""+ChatColor.BOLD+PlayerNPC.getInt(u.getNPCuuid(player)+".love") + " / 1000"), 16, inv);
		Stack2(ChatColor.WHITE +""+ChatColor.BOLD + "������", 324,0,1,Arrays.asList(ChatColor.YELLOW + ""+ChatColor.stripColor(NPCname)+ChatColor.GRAY +"����",ChatColor.GRAY + "��ȭ�� �����մϴ�.",ChatColor.BLACK + u.getNPCuuid(player)), 26, inv);
		if(player.isOp())
			Stack2(ChatColor.WHITE +""+ChatColor.BOLD + "GUI �� Ȱ��ȭ", 166,0,1,Arrays.asList(ChatColor.GRAY +"�� NPC�� GoldBigDragonRPG��",ChatColor.GRAY + "NPC GUI ȭ���� ������� �ʰ� �մϴ�.",""), 8, inv);

		YamlManager NPCscript = YC.getNewConfig("NPC/NPCData/"+ u.getNPCuuid(player)  +".yml");
		if(NPCscript.contains("Job") == false)
		{
		  	NPCscript.set("Job.Type", "null");
			NPCscript.saveConfig();
		}
		if(NPCscript.contains("NatureTalk") == false)
		{
    	  	NPCscript.set("NPCuuid", "NPC's uuid");
    	  	NPCscript.set("KoreaLanguage(UTF-8)->JavaEntityLanguage", "http://itpro.cz/juniconv/");
    	  	NPCscript.set("NatureTalk.1.love", 0);
    	  	NPCscript.set("NatureTalk.1.Script", "��f�ȳ�, ��e%player%?");
    	  	NPCscript.set("NatureTalk.2.love", 0);
    	  	NPCscript.set("NatureTalk.2.Script", "��f��� �����%enter%��f�̷��� ����.");
    	  	NPCscript.set("NatureTalk.3.love", 0);
    	  	NPCscript.set("NatureTalk.3.Script", "��1������ ��4�̷��� ��f���� �� �־�!");
    	  	NPCscript.saveConfig();
		}
		if(NPCscript.contains("NearByNEWS") == false)
		{
    	  	NPCscript.set("NearByNEWS.1.love", 0);
    	  	NPCscript.set("NearByNEWS.1.Script", "��f���������� ���� ���̾Ƹ�带 �� �� ������...");
    	  	NPCscript.set("NearByNEWS.2.love", 0);
    	  	NPCscript.set("NearByNEWS.2.Script", "��f�����ϴ°� ����.%enter%��f���� ��4���κ� ��f�� �� ��ó�� �־��ŵ�...");
    	  	NPCscript.set("NearByNEWS.3.love", 0);
    	  	NPCscript.set("NearByNEWS.3.Script", "��f��...");
    	  	NPCscript.saveConfig();
		}
		if(NPCscript.contains("AboutSkills") == false)
		{
    	  	NPCscript.set("AboutSkills.1.love", 0);
    	  	NPCscript.set("AboutSkills.1.giveSkill", "null");
    	  	NPCscript.set("AboutSkills.1.AlreadyGetScript", "null");
    	  	NPCscript.set("AboutSkills.1.Script", "��f���� ��eä�� ��ų��f�� ����!%enter%��f��? �ʵ� �ִٰ�?");
    	  	NPCscript.set("AboutSkills.2.love", 0);
    	  	NPCscript.set("AboutSkills.2.giveSkill", "null");
    	  	NPCscript.set("AboutSkills.2.AlreadyGetScript", "null");
    	  	NPCscript.set("AboutSkills.2.Script", "��f�޸���� ���� �ǰ����� ������%enter%��f�������� ����.");
    	  	NPCscript.set("AboutSkills.3.love", 0);
    	  	NPCscript.set("AboutSkills.3.giveSkill", "null");
    	  	NPCscript.set("AboutSkills.3.AlreadyGetScript", "null");
    	  	NPCscript.set("AboutSkills.3.Script", "��f�ʿ��� ������ �ٸ���%enter%��f����� ���°� ������...");
    	  	NPCscript.saveConfig();
		}
		if(NPCscript.contains("Shop.Sell") == false)
		{
		  	NPCscript.createSection("Shop.Sell");
    	  	NPCscript.saveConfig();
		}
		if(NPCscript.contains("Shop.Buy") == false)
		{
		  	NPCscript.createSection("Shop.Buy");
    	  	NPCscript.saveConfig();
		}
		if(NPCscript.contains("Quest") == false)
		{
		  	NPCscript.createSection("Quest");
    	  	NPCscript.saveConfig();
		}
		switch(NPCscript.getString("Job.Type"))
		{
		case "BlackSmith":
				Stack2(ChatColor.GOLD +""+ChatColor.BOLD + "��������", 145,0,1,Arrays.asList(ChatColor.GRAY+"�տ� �� ���� �� ����, ����",ChatColor.GRAY+"������ Ȯ���� ���ݿ� ���� �� �ݴϴ�.","",ChatColor.DARK_AQUA +"���� ������ : "+ChatColor.WHITE +NPCscript.getInt("Job.FixRate")+ChatColor.DARK_AQUA +" %",ChatColor.GREEN + "������ 10 �� ���� : "+ChatColor.YELLOW +""+NPCscript.getInt("Job.10PointFixDeal")+" "+ChatColor.GREEN+ServerOption.Money,"",ChatColor.YELLOW + "[�� Ŭ���� ���� ����ȭ�� �̵�]",ChatColor.RED+"[�Ϲ� ������ ���� ���н�]",ChatColor.RED+" - ���� ������ ��� ���� ��� �Ҹ�",ChatColor.RED+"[Ŀ���� ������ ���� ���н�]",ChatColor.RED+" - �ִ� ������ ����"), 4, inv);
			break;
		case "Shaman":
			Stack2(ChatColor.GOLD +""+ChatColor.BOLD + "�ּ���",381,0,1,Arrays.asList(ChatColor.GRAY+"�÷��̾�� ������ ���� ȿ����",ChatColor.GRAY+"���� �ݾ��� �ް� �ο��� �ݴϴ�.",ChatColor.AQUA+"���� ������ : "+ChatColor.WHITE + NPCscript.getInt("Job.GoodRate") + ChatColor.DARK_AQUA+"%",ChatColor.AQUA+"���� �ð� : "+ChatColor.WHITE + NPCscript.getInt("Job.BuffTime")+ChatColor.DARK_AQUA+" ��",ChatColor.GREEN+"��ä ��� : "+ChatColor.WHITE+NPCscript.getInt("Job.Deal")+ChatColor.GREEN+ServerOption.Money,ChatColor.YELLOW + "[�� Ŭ���� �� ġ��]",ChatColor.RED+"[���� ���н�]",ChatColor.RED+" - ������ ������� ��� �Ҹ�",ChatColor.RED+" - ���� �ð����� ���� ȿ��"), 4, inv);
		break;
		case "Healer":
			Stack2(ChatColor.GOLD +""+ChatColor.BOLD + "����", 373,8261,1,Arrays.asList(ChatColor.GRAY+"������ ���� �ݾ��� �ް�",ChatColor.GRAY + "�÷��̾��� ����� ȸ�� ��",ChatColor.GRAY+"���� ���� ȿ���� ������ �ݴϴ�.",ChatColor.GREEN+"ġ�� ��� : "+ChatColor.WHITE+NPCscript.getInt("Job.Deal")+ChatColor.GREEN+ServerOption.Money,ChatColor.YELLOW + "[�� Ŭ���� ġ�� �ޱ�]"), 4, inv);
		break;
		case "Master":
			YamlManager JobList  = YC.getNewConfig("Skill/JobList.yml");
			boolean isExitJob = false;
			Object[] Job = JobList.getConfigurationSection("MapleStory").getKeys(false).toArray();
			for(short count = 0; count < Job.length; count++)
			{
				Object[] a = JobList.getConfigurationSection("MapleStory."+Job[count].toString()).getKeys(false).toArray();
				for(byte counter=0;counter<a.length;counter++)
				{
					if(a[counter].toString().equalsIgnoreCase(NPCscript.getString("Job.Job"))==true)
						isExitJob = true;
				}
			}
			if(isExitJob == false)
			{
				NPCscript.removeKey("Job");
				NPCscript.set("Job.Type","null");
				NPCscript.saveConfig();
			}
			for(short count = 0; count < Job.length; count++)
			{
				Object[] a = JobList.getConfigurationSection("MapleStory."+Job[count].toString()).getKeys(false).toArray();
				for(byte counter=0;counter<a.length;counter++)
				{
					if(a[counter].toString().equalsIgnoreCase(NPCscript.getString("Job.Job"))==true)
					{
						YamlManager PlayerJob  = YC.getNewConfig("Skill/PlayerData/"+player.getUniqueId().toString()+".yml");
						short ID = (short) JobList.getInt("MapleStory."+Job[count].toString()+"."+a[counter].toString()+".IconID");
						byte DATA = (byte) JobList.getInt("MapleStory."+Job[count].toString()+"."+a[counter].toString()+".IconData");
						byte Amount = (byte) JobList.getInt("MapleStory."+Job[count].toString()+"."+a[counter].toString()+".IconAmount");
						int NeedLV = JobList.getInt("MapleStory."+Job[count].toString()+"."+a[counter].toString()+".NeedLV");
						int NeedSTR = JobList.getInt("MapleStory."+Job[count].toString()+"."+a[counter].toString()+".NeedSTR");
						int NeedDEX = JobList.getInt("MapleStory."+Job[count].toString()+"."+a[counter].toString()+".NeedDEX");
						int NeedINT = JobList.getInt("MapleStory."+Job[count].toString()+"."+a[counter].toString()+".NeedINT");
						int NeedWILL = JobList.getInt("MapleStory."+Job[count].toString()+"."+a[counter].toString()+".NeedWILL");
						int NeedLUK = JobList.getInt("MapleStory."+Job[count].toString()+"."+a[counter].toString()+".NeedLUK");
						String PrevJob = JobList.getString("MapleStory."+Job[count].toString()+"."+a[counter].toString()+".PrevJob");

						String lore = "%enter%"+ChatColor.DARK_AQUA+""+ChatColor.BOLD+"   ["+NPCscript.getString("Job.Job")+" ����]   %enter%"+ChatColor.GRAY+"%enter%"+ChatColor.GRAY + "     [���� ����]     %enter%%enter%";

						if(GoldBigDragon_RPG.Main.ServerOption.PlayerList.get(player.getUniqueId().toString()).getStat_Level()<NeedLV)
							lore = lore + ChatColor.RED+"�ʿ� ���� : "+NeedLV+"%enter%";
						else
							lore = lore + ChatColor.AQUA+"�ʿ� ���� : "+NeedLV+"%enter%";
						if(GoldBigDragon_RPG.Main.ServerOption.PlayerList.get(player.getUniqueId().toString()).getStat_STR()<NeedSTR)
							lore = lore + ChatColor.RED+"�ʿ� "+ServerOption.STR+" : "+NeedSTR+"%enter%";
						else
							lore = lore + ChatColor.AQUA+"�ʿ� "+ServerOption.STR+" : "+NeedSTR+"%enter%";
						if(GoldBigDragon_RPG.Main.ServerOption.PlayerList.get(player.getUniqueId().toString()).getStat_DEX()<NeedDEX)
							lore = lore + ChatColor.RED+"�ʿ� "+ServerOption.DEX+" : "+NeedDEX+"%enter%";
						else
							lore = lore + ChatColor.AQUA+"�ʿ� "+ServerOption.DEX+" : "+NeedDEX+"%enter%";
						if(GoldBigDragon_RPG.Main.ServerOption.PlayerList.get(player.getUniqueId().toString()).getStat_INT()<NeedINT)
							lore = lore + ChatColor.RED+"�ʿ� "+ServerOption.INT+" : "+NeedINT+"%enter%";
						else
							lore = lore + ChatColor.AQUA+"�ʿ� "+ServerOption.INT+" : "+NeedINT+"%enter%";
						if(GoldBigDragon_RPG.Main.ServerOption.PlayerList.get(player.getUniqueId().toString()).getStat_WILL()<NeedWILL)
							lore = lore + ChatColor.RED+"�ʿ� "+ServerOption.WILL+" : "+NeedWILL+"%enter%";
						else
							lore = lore + ChatColor.AQUA+"�ʿ� "+ServerOption.WILL+" : "+NeedWILL+"%enter%";
						if(GoldBigDragon_RPG.Main.ServerOption.PlayerList.get(player.getUniqueId().toString()).getStat_LUK()<NeedLUK)
							lore = lore + ChatColor.RED+"�ʿ� "+ServerOption.LUK+" : "+NeedLUK+"%enter%";
						else
							lore = lore + ChatColor.AQUA+"�ʿ� "+ServerOption.LUK+" : "+NeedLUK+"%enter%";
						if(PrevJob.equalsIgnoreCase("null")==false)
						{
							if(PlayerJob.getString("Job.Type").equalsIgnoreCase(PrevJob)==false)
								lore = lore + ChatColor.RED+"���� ���� : "+PrevJob+"%enter%";
							else
								lore = lore + ChatColor.AQUA+"���� ���� : "+PrevJob+"%enter%";
						}
						
						lore = lore + "%enter%"+ChatColor.YELLOW + "[�� Ŭ���� ����]";
						
						String[] scriptA = lore.split("%enter%");
						for(byte county = 0; county < scriptA.length; county++)
							scriptA[county] =  scriptA[county];
						
						Stack2(ChatColor.GOLD +""+ChatColor.BOLD + "���� ����", ID,DATA,Amount,Arrays.asList(scriptA), 4, inv);
					}
				}
			}
			break;
		case "Warper":
			Stack2(ChatColor.GOLD +""+ChatColor.BOLD + "���� �̵�����", 368,0,1,Arrays.asList(ChatColor.GRAY+"���� �̵������ ���� �ݾ��� �ް�",ChatColor.GRAY + "�÷��̾ Ư�� �������� �̵����� �ݴϴ�.",ChatColor.GREEN+"��ϵ� ���� : "+ChatColor.WHITE+NPCscript.getConfigurationSection("Job.WarpList").getKeys(false).size()+ChatColor.GREEN+" ����","",ChatColor.YELLOW + "[�� Ŭ���� ���� ��� ����]"), 4, inv);
		break;
		case "Upgrader":
			Stack2(ChatColor.GOLD +""+ChatColor.BOLD + "���� ����", 417,0,1,Arrays.asList(ChatColor.GRAY+"���� ������ ���� �ݾ��� �ް�",ChatColor.GRAY + "���� ���θ��� �˰� �ִ�",ChatColor.GRAY+"���� �����Ǹ� �����Ͽ�",ChatColor.GRAY+"���� �տ� �� ���⸦",ChatColor.GRAY+"���� ���� �ݴϴ�.",ChatColor.GREEN+"�̿� ������ ������ : "+ChatColor.WHITE+NPCscript.getConfigurationSection("Job.UpgradeRecipe").getKeys(false).size()+ChatColor.GREEN+" ����","",ChatColor.YELLOW + "[�� Ŭ���� ���� ������ ����]"), 4, inv);
		break;
		case "Rune":
			Stack2(ChatColor.GOLD +""+ChatColor.BOLD + "�� ������", 351,10,1,Arrays.asList(ChatColor.GRAY+"�� ������� ���� �ݾ��� �ް�",ChatColor.GRAY + "������ �ִ� ���� �����Ͽ�",ChatColor.GRAY+"���� �տ� �� ���⿡",ChatColor.GRAY+"����ִ� �� ������ �ִٸ�",ChatColor.GRAY+"���� ���� ���� �ݴϴ�.","",ChatColor.GREEN+"�� ���� ������ : "+ChatColor.WHITE +NPCscript.getInt("Job.SuccessRate")+ChatColor.DARK_AQUA +"%",ChatColor.GREEN + "�� ���� ���� : "+ChatColor.YELLOW +""+NPCscript.getInt("Job.Deal")+ChatColor.GREEN+ServerOption.Money,"",ChatColor.YELLOW + "[�� Ŭ���� �� ����]"), 4, inv);
		break;
		}
		
		if(isOP == true)
		{
			switch(NPCscript.getString("Job.Type"))
			{
			case "null":
				Stack2(ChatColor.GOLD +""+ChatColor.BOLD + "���� ����", 403,0,1,Arrays.asList(ChatColor.GRAY + "�� NPC���Դ� ���� ������ �����ϴ�!",ChatColor.GRAY + "������ ������ �پ��� ����� �����մϴ�.","",ChatColor.YELLOW + "[Ŭ���� ���� ����]"), 4, inv);
				break;
			case "BlackSmith":
					Stack2(ChatColor.GOLD +""+ChatColor.BOLD + "��������", 145,0,1,Arrays.asList(ChatColor.GRAY+"�÷��̾��� ���� �� ����, ����",ChatColor.GRAY+"������ Ȯ���� ���ݿ� ���� �� �ݴϴ�.","",ChatColor.DARK_AQUA +"���� ������ : "+ChatColor.WHITE +NPCscript.getInt("Job.FixRate")+ChatColor.DARK_AQUA +" %",ChatColor.GREEN + "������ 10 �� ���� : "+ChatColor.YELLOW +""+NPCscript.getInt("Job.10PointFixDeal")+" "+ServerOption.Money,"",ChatColor.RED+"[�Ϲ� ������ ���� ���н�]",ChatColor.RED+" - ���� ������ ��� ���� "+ServerOption.Money+ChatColor.RED+" �Ҹ�",ChatColor.RED+"[Ŀ���� ������ ���� ���н�]",ChatColor.RED+" - �ִ� ������ ����","",ChatColor.YELLOW + "[�� Ŭ���� ���� ����ȭ�� �̵�]",ChatColor.RED+ "[�� Ŭ���� ���� ����]"), 4, inv);
				break;
			case "Shaman":
				Stack2(ChatColor.GOLD +""+ChatColor.BOLD + "�ּ���",381,0,1,Arrays.asList(ChatColor.GRAY+"�÷��̾�� ������ ���� ȿ����",ChatColor.GRAY+"���� �ݾ��� �ް� �ο��� �ݴϴ�.","",ChatColor.AQUA+"���� ������ : "+ChatColor.WHITE + NPCscript.getInt("Job.GoodRate") + ChatColor.DARK_AQUA+"%",ChatColor.AQUA+"���� �ð� : "+ChatColor.WHITE + NPCscript.getInt("Job.BuffTime")+ChatColor.DARK_AQUA+" ��",ChatColor.GREEN+"��ä ��� : "+ChatColor.WHITE+NPCscript.getInt("Job.Deal")+ChatColor.GREEN+ServerOption.Money,ChatColor.RED+"[���� ���н�]",ChatColor.RED+" - ������ ������� ��� �Ҹ�",ChatColor.RED+" - ���� �ð����� ���� ȿ��","",ChatColor.YELLOW + "[�� Ŭ���� �� ġ��]",ChatColor.RED+ "[�� Ŭ���� ���� ����]"), 4, inv);
			break;
			case "Healer":
				Stack2(ChatColor.GOLD +""+ChatColor.BOLD + "����", 373,8261,1,Arrays.asList(ChatColor.GRAY+"������ ���� �ݾ��� �ް�",ChatColor.GRAY + "�÷��̾��� ����� ȸ�� ��",ChatColor.GRAY+"���� ���� ȿ���� ������ �ݴϴ�.",ChatColor.GREEN+"ġ�� ��� : "+ChatColor.WHITE+NPCscript.getInt("Job.Deal")+ChatColor.GREEN+ServerOption.Money,"",ChatColor.YELLOW + "[�� Ŭ���� ġ�� �ޱ�]",ChatColor.RED+ "[�� Ŭ���� ���� ����]"), 4, inv);
			break;
			case "Master":
				YamlManager JobList  = YC.getNewConfig("Skill/JobList.yml");
				Object[] Job = JobList.getConfigurationSection("MapleStory").getKeys(false).toArray();
				for(short count = 0; count < Job.length; count++)
				{
					Object[] a = JobList.getConfigurationSection("MapleStory."+Job[count].toString()).getKeys(false).toArray();
					for(byte counter=0;counter<a.length;counter++)
					{
						if(a[counter].toString().equalsIgnoreCase(NPCscript.getString("Job.Job"))==true)
						{
							YamlManager PlayerJob  = YC.getNewConfig("Skill/PlayerData/"+player.getUniqueId().toString()+".yml");
							short ID = (short) JobList.getInt("MapleStory."+Job[count].toString()+"."+a[counter].toString()+".IconID");
							byte DATA = (byte) JobList.getInt("MapleStory."+Job[count].toString()+"."+a[counter].toString()+".IconData");
							byte Amount = (byte) JobList.getInt("MapleStory."+Job[count].toString()+"."+a[counter].toString()+".IconAmount");
							int NeedLV = JobList.getInt("MapleStory."+Job[count].toString()+"."+a[counter].toString()+".NeedLV");
							int NeedSTR = JobList.getInt("MapleStory."+Job[count].toString()+"."+a[counter].toString()+".NeedSTR");
							int NeedDEX = JobList.getInt("MapleStory."+Job[count].toString()+"."+a[counter].toString()+".NeedDEX");
							int NeedINT = JobList.getInt("MapleStory."+Job[count].toString()+"."+a[counter].toString()+".NeedINT");
							int NeedWILL = JobList.getInt("MapleStory."+Job[count].toString()+"."+a[counter].toString()+".NeedWILL");
							int NeedLUK = JobList.getInt("MapleStory."+Job[count].toString()+"."+a[counter].toString()+".NeedLUK");
							String PrevJob = JobList.getString("MapleStory."+Job[count].toString()+"."+a[counter].toString()+".PrevJob");						
							String lore = "%enter%"+ChatColor.DARK_AQUA+""+ChatColor.BOLD+"   ["+NPCscript.getString("Job.Job")+" ����]   %enter%"+ChatColor.GRAY+"%enter%"+ChatColor.GRAY + "     [���� ����]     %enter%%enter%";
							
							if(GoldBigDragon_RPG.Main.ServerOption.PlayerList.get(player.getUniqueId().toString()).getStat_Level()<NeedLV)
								lore = lore + ChatColor.RED+"�ʿ� ���� : "+NeedLV+"%enter%";
							else
								lore = lore + ChatColor.AQUA+"�ʿ� ���� : "+NeedLV+"%enter%";
							if(GoldBigDragon_RPG.Main.ServerOption.PlayerList.get(player.getUniqueId().toString()).getStat_STR()<NeedSTR)
								lore = lore + ChatColor.RED+"�ʿ� "+ServerOption.STR+" : "+NeedSTR+"%enter%";
							else
								lore = lore + ChatColor.AQUA+"�ʿ� "+ServerOption.STR+" : "+NeedSTR+"%enter%";
							if(GoldBigDragon_RPG.Main.ServerOption.PlayerList.get(player.getUniqueId().toString()).getStat_DEX()<NeedDEX)
								lore = lore + ChatColor.RED+"�ʿ� "+ServerOption.DEX+" : "+NeedDEX+"%enter%";
							else
								lore = lore + ChatColor.AQUA+"�ʿ� "+ServerOption.DEX+" : "+NeedDEX+"%enter%";
							if(GoldBigDragon_RPG.Main.ServerOption.PlayerList.get(player.getUniqueId().toString()).getStat_INT()<NeedINT)
								lore = lore + ChatColor.RED+"�ʿ� "+ServerOption.INT+" : "+NeedINT+"%enter%";
							else
								lore = lore + ChatColor.AQUA+"�ʿ� "+ServerOption.INT+" : "+NeedINT+"%enter%";
							if(GoldBigDragon_RPG.Main.ServerOption.PlayerList.get(player.getUniqueId().toString()).getStat_WILL()<NeedWILL)
								lore = lore + ChatColor.RED+"�ʿ� "+ServerOption.WILL+" : "+NeedWILL+"%enter%";
							else
								lore = lore + ChatColor.AQUA+"�ʿ� "+ServerOption.WILL+" : "+NeedWILL+"%enter%";
							if(GoldBigDragon_RPG.Main.ServerOption.PlayerList.get(player.getUniqueId().toString()).getStat_LUK()<NeedLUK)
								lore = lore + ChatColor.RED+"�ʿ� "+ServerOption.LUK+" : "+NeedLUK+"%enter%";
							else
								lore = lore + ChatColor.AQUA+"�ʿ� "+ServerOption.LUK+" : "+NeedLUK+"%enter%";

							if(PrevJob.equalsIgnoreCase("null")==false)
							{
								if(PlayerJob.getString("Job.Type").equalsIgnoreCase(PrevJob)==false)
									lore = lore + ChatColor.RED+"���� ���� : "+PrevJob+"%enter%";
								else
									lore = lore + ChatColor.AQUA+"���� ���� : "+PrevJob+"%enter%";
							}
							
							lore = lore + "%enter%"+ChatColor.YELLOW + "[�� Ŭ���� ����]%enter%"+ChatColor.RED + "[�� Ŭ���� ���� ����]";
							
							String[] scriptA = lore.split("%enter%");
							for(byte county = 0; county < scriptA.length; county++)
								scriptA[county] =  scriptA[county];
							
							Stack2(ChatColor.GOLD +""+ChatColor.BOLD + "���� ����", ID,DATA,Amount,Arrays.asList(scriptA), 4, inv);
						}
					}
				}
				break;
			case "Warper":
				Stack2(ChatColor.GOLD +""+ChatColor.BOLD + "���� �̵�����", 368,0,1,Arrays.asList(ChatColor.GRAY+"���� �̵������ ���� �ݾ��� �ް�",ChatColor.GRAY + "�÷��̾ Ư�� �������� �̵����� �ݴϴ�.",ChatColor.GREEN+"��ϵ� ���� : "+ChatColor.WHITE+NPCscript.getConfigurationSection("Job.WarpList").getKeys(false).size()+ChatColor.GREEN+" ����","",ChatColor.YELLOW + "[�� Ŭ���� ���� ��� ����]",ChatColor.RED + "[�� Ŭ���� ���� ����]"), 4, inv);
			break;
			case "Upgrader":
				Stack2(ChatColor.GOLD +""+ChatColor.BOLD + "���� ����", 417,0,1,Arrays.asList(ChatColor.GRAY+"���� ������ ���� �ݾ��� �ް�",ChatColor.GRAY + "���� ���θ��� �˰� �ִ�",ChatColor.GRAY+"���� �����Ǹ� �����Ͽ�",ChatColor.GRAY+"���� �տ� �� ���⸦",ChatColor.GRAY+"���� ���� �ݴϴ�.",ChatColor.GREEN+"�̿� ������ ������ : "+ChatColor.WHITE+NPCscript.getConfigurationSection("Job.UpgradeRecipe").getKeys(false).size()+ChatColor.GREEN+" ����","",ChatColor.YELLOW + "[�� Ŭ���� ���� ������ ����]",ChatColor.RED + "[�� Ŭ���� ���� ����]"), 4, inv);
			break;
			case "Rune":
				Stack2(ChatColor.GOLD +""+ChatColor.BOLD + "�� ������", 351,10,1,Arrays.asList(ChatColor.GRAY+"�� ������� ���� �ݾ��� �ް�",ChatColor.GRAY + "������ �ִ� ���� �����Ͽ�",ChatColor.GRAY+"���� �տ� �� ���⿡",ChatColor.GRAY+"����ִ� �� ������ �ִٸ�",ChatColor.GRAY+"���� ���� ���� �ݴϴ�.","",ChatColor.GREEN+"�� ���� ������ : "+ChatColor.WHITE +NPCscript.getInt("Job.SuccessRate")+ChatColor.DARK_AQUA +"%",ChatColor.GREEN + "�� ���� ���� : "+ChatColor.YELLOW +""+NPCscript.getInt("Job.Deal")+" "+ServerOption.Money,"",ChatColor.YELLOW + "[�� Ŭ���� �� ����]",ChatColor.RED + "[�� Ŭ���� ���� ����]"), 4, inv);
			break;
			}
			Stack2(ChatColor.GOLD +""+ChatColor.BOLD + "��ȭ ����", 403,0,1,Arrays.asList(ChatColor.YELLOW + ""+ChatColor.stripColor(NPCname)+ChatColor.GRAY +"�� ��ȭ�� �� ��",ChatColor.GRAY + "�÷��̾�� ���� ��縦",ChatColor.GRAY + "�߰�/����/���� �մϴ�."), 19, inv);
			Stack2(ChatColor.GOLD +""+ChatColor.BOLD + "�ŷ� ����", 403,0,1,Arrays.asList(ChatColor.YELLOW + ""+ChatColor.stripColor(NPCname)+ChatColor.GRAY +"��",ChatColor.GRAY + "�ŷ� ǰ���� ��Ŭ����",ChatColor.GRAY + "�ش� �������� ���� ������,",ChatColor.GRAY + "�ŷ� ǰ���� ��Ŭ����",ChatColor.GRAY + "�ش� �������� ����/�Ǹ� �����˴ϴ�.","",ChatColor.WHITE + "[����/�Ǹ��� ������ ��� ��ɾ�]",ChatColor.GOLD+""+ChatColor.BOLD + "/���� [����/�Ǹ�] [����]"), 21, inv);
			Stack2(ChatColor.GOLD +""+ChatColor.BOLD + "����Ʈ ����", 403,0,1,Arrays.asList(ChatColor.YELLOW + ""+ChatColor.stripColor(NPCname)+ChatColor.GRAY +"��",ChatColor.GRAY + "����Ʈ ������ �����մϴ�."), 23, inv);
			Stack2(ChatColor.GOLD +""+ChatColor.BOLD + "���� ����", 403,0,1,Arrays.asList(ChatColor.YELLOW + ""+ChatColor.stripColor(NPCname)+ChatColor.GRAY +"���� �÷��̾",ChatColor.GRAY + "�ִ� ������ ���� ����ϴ�",ChatColor.GRAY+"ȣ���� ��� ��ġ�� �����մϴ�."), 25, inv);
			if(NPCscript.getBoolean("Sale.Enable"))
				Stack2(ChatColor.GOLD +""+ChatColor.BOLD + "���� ����", 38,0,1,Arrays.asList(ChatColor.YELLOW + ""+ChatColor.stripColor(NPCname)+ChatColor.GRAY +"���� ģ�е��� ���� ���",ChatColor.GRAY + "���� �Ǹ� ��ǰ�� ���� �� �ݴϴ�.","",ChatColor.GREEN + "ȣ���� "+NPCscript.getInt("Sale.Minlove") + "�̻�� " +NPCscript.getInt("Sale.discount")+"% ����","", ChatColor.RED+"[Shift + �� Ŭ���� ����]"), 7, inv);
			else
				Stack2(ChatColor.GOLD +""+ChatColor.BOLD + "���� ����", 38,0,1,Arrays.asList(ChatColor.YELLOW + ""+ChatColor.stripColor(NPCname)+ChatColor.GRAY +"���� ģ�е��� ���� ���",ChatColor.GRAY + "���� �Ǹ� ��ǰ�� ���� �� �ݴϴ�.","",ChatColor.YELLOW + "[�� Ŭ���� ����]"), 7, inv);
		}
		
		player.openInventory(inv);
	}
	
	public void ShopGUI(Player player, String NPCname, short page, boolean Buy, boolean isEditMode)
	{
		Object_UserData u = new Object_UserData();
		Inventory inv = Bukkit.createInventory(null, 54, ChatColor.BLACK + "[NPC] "+ChatColor.stripColor(NPCname));
		
		YamlController YC = new YamlController(GoldBigDragon_RPG.Main.Main.plugin);
	  	if(YC.isExit("NPC/NPCData/"+ u.getNPCuuid(player)  +".yml") == false)
	  	{
	  		GoldBigDragon_RPG.Config.NPCconfig NPCC = new GoldBigDragon_RPG.Config.NPCconfig();
	  		NPCC.NPCNPCconfig(u.getNPCuuid(player));
	  	}
		YamlManager NPCscript = YC.getNewConfig("NPC/NPCData/"+ u.getNPCuuid(player) +".yml");
		ItemStack item;
		ItemMeta IM = null;
		short a = 0;
		if(Buy == true)
		{
			a = (short) NPCscript.getConfigurationSection("Shop.Sell").getKeys(false).size();
			
			if(isEditMode == false)
				Stack2(ChatColor.AQUA +"     [����]     ", 160,11,1,null, 0, inv);
			else
				Stack2(ChatColor.AQUA +"     [����]     ", 160,11,1,Arrays.asList(ChatColor.BLACK + "-1"), 0, inv);
				
			Stack2(ChatColor.AQUA +"     [����]     ", 160,11,1,null, 1, inv);
			Stack2(ChatColor.AQUA +"     [����]     ", 160,11,1,null, 2, inv);
			Stack2(ChatColor.AQUA +"     [����]     ", 160,11,1,null, 3, inv);
			Stack2(ChatColor.AQUA +"     [����]     ", 160,11,1,null, 4, inv);
			Stack2(ChatColor.AQUA +"     [����]     ", 160,11,1,null, 5, inv);
			Stack2(ChatColor.AQUA +"     [����]     ", 160,11,1,null, 6, inv);
			Stack2(ChatColor.AQUA +"     [����]     ", 160,11,1,null, 7, inv);
			Stack2(ChatColor.AQUA +"     [����]     ", 160,11,1,Arrays.asList(ChatColor.BLACK + ""+page), 8, inv);
			Stack2(ChatColor.AQUA +"     [����]     ", 160,11,1,null, 9, inv);
			Stack2(ChatColor.AQUA +"     [����]     ", 160,11,1,null, 18, inv);
			Stack2(ChatColor.AQUA +"     [����]     ", 160,11,1,null, 17, inv);
			Stack2(ChatColor.AQUA +"     [����]     ", 160,11,1,null, 26, inv);
			Stack2(ChatColor.AQUA +"     [����]     ", 160,11,1,null, 27, inv);
			Stack2(ChatColor.AQUA +"     [����]     ", 160,11,1,null, 36, inv);
			Stack2(ChatColor.AQUA +"     [����]     ", 160,11,1,null, 35, inv);
			Stack2(ChatColor.AQUA +"     [����]     ", 160,11,1,null, 36, inv);
			Stack2(ChatColor.AQUA +"     [����]     ", 160,11,1,null, 37, inv);
			Stack2(ChatColor.AQUA +"     [����]     ", 160,11,1,null, 38, inv);
			Stack2(ChatColor.AQUA +"     [����]     ", 160,11,1,null, 39, inv);
			Stack2(ChatColor.AQUA +"     [����]     ", 160,11,1,null, 40, inv);
			Stack2(ChatColor.AQUA +"     [����]     ", 160,11,1,null, 41, inv);
			Stack2(ChatColor.AQUA +"     [����]     ", 160,11,1,null, 42, inv);
			Stack2(ChatColor.AQUA +"     [����]     ", 160,11,1,null, 43, inv);
			Stack2(ChatColor.AQUA +"     [����]     ", 160,11,1,null, 44, inv);
			Stack2(ChatColor.RED +"   [��ǰ �Ǹ�]   ", 160,14,1,null, 49, inv);
			byte loc=0;
			boolean Sale = NPCscript.getBoolean("Sale.Enable");
			int discount = 0;
			if(Sale)
			{
			  	YamlManager PlayerNPC = null;
		    	if(YC.isExit("NPC/PlayerData/"+player.getUniqueId()+".yml")==false)
		    	{
		    		PlayerNPC=YC.getNewConfig("NPC/PlayerData/"+player.getUniqueId()+".yml");
		    		PlayerNPC.set(u.getNPCuuid(player)+".love", 0);
		    		PlayerNPC.set(u.getNPCuuid(player)+".Career", 0);
		    		PlayerNPC.saveConfig();
		    	}
		    	else
		    		PlayerNPC=YC.getNewConfig("NPC/PlayerData/"+player.getUniqueId()+".yml");
		    	if(PlayerNPC.getInt(u.getNPCuuid(player)+".love") >= NPCscript.getInt("Sale.Minlove"))
					discount = NPCscript.getInt("Sale.discount");
		    	else
		    		Sale = false;
			}
			for(int count = page*21; count < a; count++)
			{
				if(count > a || loc >= 21) break;
				item = NPCscript.getItemStack("Shop.Sell."+count + ".item");
				IM = item.getItemMeta();
				long price = NPCscript.getLong("Shop.Sell."+count+".price");
				if(Sale)
					price = price - ((price/100) * discount);
				if(item.hasItemMeta() == true)
				{
					if(item.getItemMeta().hasLore() == true)
					{
						String[] lore = new String[IM.getLore().size()+3];
						for(byte counter=0; counter < lore.length-3;counter++)
							lore[counter] = IM.getLore().get(counter);
						lore[lore.length-3] = "";

						if(isEditMode == false)
						{
							if(price >= GoldBigDragon_RPG.Main.ServerOption.PlayerList.get(player.getUniqueId().toString()).getStat_Money())
								lore[lore.length-2] = ChatColor.RED+"[���� : " + price + " "+ChatColor.stripColor(ServerOption.Money)+"]";
							else
								lore[lore.length-2] = ChatColor.AQUA+"[���� : " + price + " "+ChatColor.stripColor(ServerOption.Money)+"]";
							lore[lore.length-1] = ChatColor.WHITE+"[������ : " + GoldBigDragon_RPG.Main.ServerOption.PlayerList.get(player.getUniqueId().toString()).getStat_Money() + " "+ChatColor.stripColor(ServerOption.Money)+"]";
						}
						else
						{
							lore[lore.length-2] = ChatColor.WHITE+"[���� : " + price + " "+ChatColor.stripColor(ServerOption.Money)+"]";
							lore[lore.length-1] = ChatColor.BLACK +""+ count;
						}
						IM.setLore(Arrays.asList(lore));
					}
					else
					{
						String[] lore = new String[3];
						lore[0] = "";
						if(isEditMode == false)
						{
							if(price >= GoldBigDragon_RPG.Main.ServerOption.PlayerList.get(player.getUniqueId().toString()).getStat_Money())
								lore[1] = ChatColor.RED+"[���� : " + price + " "+ChatColor.stripColor(ServerOption.Money)+"]";
							else
								lore[1] = ChatColor.AQUA+"[���� : " + price + " "+ChatColor.stripColor(ServerOption.Money)+"]";
							lore[2] = ChatColor.WHITE+"[������ : " + GoldBigDragon_RPG.Main.ServerOption.PlayerList.get(player.getUniqueId().toString()).getStat_Money() + " "+ChatColor.stripColor(ServerOption.Money)+"]";
						}
						else
						{
							lore[1] = ChatColor.WHITE+"[���� : " + price + " "+ChatColor.stripColor(ServerOption.Money)+"]";
							lore[2] = ChatColor.BLACK +""+ count;
						}
						IM.setLore(Arrays.asList(lore));
					}
				}
				else
				{
					if(isEditMode == false)
					{
						if(price >= GoldBigDragon_RPG.Main.ServerOption.PlayerList.get(player.getUniqueId().toString()).getStat_Money())
						{
							List<String> l = Arrays.asList("",ChatColor.RED+"[���� : " + price + " "+ChatColor.stripColor(ServerOption.Money)+"]",ChatColor.WHITE + "[������ : " + GoldBigDragon_RPG.Main.ServerOption.PlayerList.get(player.getUniqueId().toString()).getStat_Money() + " "+ChatColor.stripColor(ServerOption.Money)+"]");
							IM.setLore(l);
						}
						else
						{
							List<String> l = Arrays.asList("",ChatColor.AQUA+"[���� : " + price + " "+ChatColor.stripColor(ServerOption.Money)+"]",ChatColor.WHITE + "[������ : " + GoldBigDragon_RPG.Main.ServerOption.PlayerList.get(player.getUniqueId().toString()).getStat_Money() + " "+ChatColor.stripColor(ServerOption.Money)+"]");
							IM.setLore(l);
						}
					}
					else
					{
						List<String> l = Arrays.asList("",ChatColor.WHITE+"[���� : " + price + " "+ChatColor.stripColor(ServerOption.Money)+"]",ChatColor.BLACK +""+ count);
						IM.setLore(l);
					}
				}
				item.setItemMeta(IM);
				if(loc >=0 && loc <= 6)
					inv.setItem(loc+10, item);
				if(loc >=7 && loc <= 13)
					inv.setItem(loc+12, item);
				if(loc >=14 && loc <= 21)
					inv.setItem(loc+14, item);
					
				loc++;
			}
			if(a-(page*20)>21)
				Stack2(ChatColor.WHITE + "" + ChatColor.BOLD + "���� ������", 323,0,1,Arrays.asList(ChatColor.GRAY + "���� �������� �̵� �մϴ�.", ChatColor.GRAY + "������ : " + (page+2)), 50, inv);
				if(page!=0)
				Stack2(ChatColor.WHITE + "" + ChatColor.BOLD + "���� ������", 323,0,1,Arrays.asList(ChatColor.GRAY + "���� �������� �̵� �մϴ�.", ChatColor.GRAY + "������ : " + (page)), 48, inv);
			
		}
		else
		{
			a = (short) NPCscript.getConfigurationSection("Shop.Buy").getKeys(false).toArray().length;

			if(isEditMode == false)
				Stack2(ChatColor.RED +"     [�Ǹ�]     ", 160,14,1,null, 0, inv);
			else
				Stack2(ChatColor.RED +"     [�Ǹ�]     ", 160,14,1,Arrays.asList(ChatColor.BLACK + "-1"), 0, inv);
			Stack2(ChatColor.RED +"     [�Ǹ�]     ", 160,14,1,null, 1, inv);
			Stack2(ChatColor.RED +"     [�Ǹ�]     ", 160,14,1,null, 2, inv);
			Stack2(ChatColor.RED +"     [�Ǹ�]     ", 160,14,1,null, 3, inv);
			Stack2(ChatColor.RED +"     [�Ǹ�]     ", 160,14,1,null, 4, inv);
			Stack2(ChatColor.RED +"     [�Ǹ�]     ", 160,14,1,null, 5, inv);
			Stack2(ChatColor.RED +"     [�Ǹ�]     ", 160,14,1,null, 6, inv);
			Stack2(ChatColor.RED +"     [�Ǹ�]     ", 160,14,1,null, 7, inv);
			Stack2(ChatColor.RED +"     [�Ǹ�]     ", 160,14,1,Arrays.asList(ChatColor.BLACK + ""+page), 8, inv);
			Stack2(ChatColor.RED +"     [�Ǹ�]     ", 160,14,1,null, 9, inv);
			Stack2(ChatColor.RED +"     [�Ǹ�]     ", 160,14,1,null, 18, inv);
			Stack2(ChatColor.RED +"     [�Ǹ�]     ", 160,14,1,null, 17, inv);
			Stack2(ChatColor.RED +"     [�Ǹ�]     ", 160,14,1,null, 26, inv);
			Stack2(ChatColor.RED +"     [�Ǹ�]     ", 160,14,1,null, 27, inv);
			Stack2(ChatColor.RED +"     [�Ǹ�]     ", 160,14,1,null, 36, inv);
			Stack2(ChatColor.RED +"     [�Ǹ�]     ", 160,14,1,null, 35, inv);
			Stack2(ChatColor.RED +"     [�Ǹ�]     ", 160,14,1,null, 36, inv);
			Stack2(ChatColor.RED +"     [�Ǹ�]     ", 160,14,1,null, 37, inv);
			Stack2(ChatColor.RED +"     [�Ǹ�]     ", 160,14,1,null, 38, inv);
			Stack2(ChatColor.RED +"     [�Ǹ�]     ", 160,14,1,null, 39, inv);
			Stack2(ChatColor.RED +"     [�Ǹ�]     ", 160,14,1,null, 40, inv);
			Stack2(ChatColor.RED +"     [�Ǹ�]     ", 160,14,1,null, 41, inv);
			Stack2(ChatColor.RED +"     [�Ǹ�]     ", 160,14,1,null, 42, inv);
			Stack2(ChatColor.RED +"     [�Ǹ�]     ", 160,14,1,null, 43, inv);
			Stack2(ChatColor.RED +"     [�Ǹ�]     ", 160,14,1,null, 44, inv);
			Stack2(ChatColor.AQUA +"   [��ǰ ����]   ", 160,11,1,null, 49, inv);
			byte loc=0;
			for(int count = page*21; count < a; count++)
			{
				if(count > a || loc >= 21) break;
				item = NPCscript.getItemStack("Shop.Buy."+count + ".item");
				IM = item.getItemMeta();
				long price = NPCscript.getLong("Shop.Buy."+count+".price");
				
				if(item.hasItemMeta() == true)
				{
					if(item.getItemMeta().hasLore() == true)
					{
						String[] lore = new String[IM.getLore().size()+3];
						for(byte counter=0; counter < lore.length-3;counter++)
							lore[counter] = IM.getLore().get(counter);
						lore[lore.length-3] = "";

						if(isEditMode == false)
						{
							lore[lore.length-2] = ChatColor.AQUA+"[���� : " + price + " "+ChatColor.stripColor(ServerOption.Money)+"]";
							lore[lore.length-1] = ChatColor.WHITE+"[������ : " + GoldBigDragon_RPG.Main.ServerOption.PlayerList.get(player.getUniqueId().toString()).getStat_Money() + " "+ChatColor.stripColor(ServerOption.Money)+"]";
						}
						else
						{
							lore[lore.length-2] = ChatColor.WHITE+"[���� : " + price + " "+ChatColor.stripColor(ServerOption.Money)+"]";
							lore[lore.length-1] = ChatColor.BLACK +""+ count;
						}
						IM.setLore(Arrays.asList(lore));
					}
					else
					{
						String[] lore = new String[3];
						lore[0] = "";
						
						if(isEditMode == false)
						{
							lore[1] = ChatColor.AQUA+"[���� : " + price + " "+ChatColor.stripColor(ServerOption.Money)+"]";
							lore[2] = ChatColor.WHITE+"[������ : " + GoldBigDragon_RPG.Main.ServerOption.PlayerList.get(player.getUniqueId().toString()).getStat_Money() + " "+ChatColor.stripColor(ServerOption.Money)+"]";
						}
						else
						{
							lore[1] = ChatColor.WHITE+"[���� : " + price + " "+ChatColor.stripColor(ServerOption.Money)+"]";
							lore[2] = ChatColor.BLACK +""+ count;
						}
						IM.setLore(Arrays.asList(lore));
					}
				}
				else
				{
					List<String> l =null;
					if(isEditMode == false)
						l = Arrays.asList("",ChatColor.AQUA+"[���� : " + price + " "+ChatColor.stripColor(ServerOption.Money)+"]",ChatColor.WHITE + "[������ : " + GoldBigDragon_RPG.Main.ServerOption.PlayerList.get(player.getUniqueId().toString()).getStat_Money() + " "+ChatColor.stripColor(ServerOption.Money)+"]");
					else
						l = Arrays.asList("",ChatColor.WHITE+"[���� : " + price + " "+ChatColor.stripColor(ServerOption.Money)+"]",ChatColor.BLACK +""+ count);
					IM.setLore(l);
				}
				item.setItemMeta(IM);
				if(loc >=0 && loc <= 6)
					inv.setItem(loc+10, item);
				if(loc >=7 && loc <= 13)
					inv.setItem(loc+12, item);
				if(loc >=14 && loc <= 21)
					inv.setItem(loc+14, item);
					
				loc++;
			}
			if(a-(page*20)>21)
				Stack2(ChatColor.WHITE + "" + ChatColor.BOLD + "���� ������", 323,0,1,Arrays.asList(ChatColor.GRAY + "���� �������� �̵� �մϴ�.", ChatColor.GRAY + "������ : " + (page+2)), 50, inv);
				if(page!=0)
				Stack2(ChatColor.WHITE + "" + ChatColor.BOLD + "���� ������", 323,0,1,Arrays.asList(ChatColor.GRAY + "���� �������� �̵� �մϴ�.", ChatColor.GRAY + "������ : " + (page)), 48, inv);
		}

	
		Stack2(ChatColor.WHITE + "" + ChatColor.BOLD + "���� ���", 323,0,1,Arrays.asList(ChatColor.GRAY + "���� ȭ������ ���ư��ϴ�."), 45, inv);
		Stack2(ChatColor.WHITE + "" + ChatColor.BOLD + "�ݱ�", 324,0,1,Arrays.asList(ChatColor.GRAY + "â�� �ݽ��ϴ�.",ChatColor.BLACK + u.getNPCuuid(player)), 53, inv);

		player.openInventory(inv);
	}

	public void TalkGUI(Player player, String NPCname, String[] strings, char TalkType)
	{
		Object_UserData u = new Object_UserData();
		Inventory inv = Bukkit.createInventory(null, 9, ChatColor.BLACK + "[NPC] "+ChatColor.stripColor(NPCname));

		Stack2(ChatColor.WHITE +""+ChatColor.BOLD + "�������� ��ȭ", 340,0,1,Arrays.asList(ChatColor.GRAY + "����� �̾߱� �Ÿ��� ����ϴ�."), 2, inv);
		Stack2(ChatColor.WHITE +""+ChatColor.BOLD + "��ó�� �ҹ�", 340,0,1,Arrays.asList(ChatColor.GRAY + "�ֱ� �鸮�� �ҹ��� ���� ����ϴ�."), 4, inv);
		Stack2(ChatColor.WHITE +""+ChatColor.BOLD + "��ų�� ���Ͽ�", 340,0,1,Arrays.asList(ChatColor.GRAY + "��ų�� ���Ͽ� ����ϴ�."), 6, inv);
		if(TalkType != -1)
		if(strings != null)
			Stack2(ChatColor.YELLOW +""+ChatColor.BOLD + " "+NPCname, 386,0,1,Arrays.asList(NPC.getScript(player, TalkType)),(int) TalkType, inv);
		Stack2(ChatColor.WHITE +""+ChatColor.BOLD + "���� �޴�", 323,0,1,Arrays.asList(ChatColor.GRAY + "���� �޴��� ���ư��ϴ�."), 0, inv);
		Stack2(ChatColor.WHITE +""+ChatColor.BOLD + "������", 324,0,1,Arrays.asList(ChatColor.YELLOW + ""+ChatColor.stripColor(NPCname)+ChatColor.GRAY +"����",ChatColor.GRAY + "��ȭ�� �����մϴ�.",ChatColor.BLACK + u.getNPCuuid(player)), 8, inv);
		
		player.openInventory(inv);
	}
	
	public void AllOfQuestListGUI(Player player, short page)
	{
		Object_UserData u = new Object_UserData();
		YamlController YC = new YamlController(GoldBigDragon_RPG.Main.Main.plugin);
		YamlManager QuestList  = YC.getNewConfig("Quest/QuestList.yml");

	  	if(YC.isExit("NPC/NPCData/"+ u.getNPCuuid(player) +".yml") == false)
	  	{
	  		GoldBigDragon_RPG.Config.NPCconfig NPCC = new GoldBigDragon_RPG.Config.NPCconfig();
	  		NPCC.NPCNPCconfig(u.getNPCuuid(player));
	  	}
		YamlManager NPCscript = YC.getNewConfig("NPC/NPCData/"+ u.getNPCuuid(player) +".yml");
		
		Inventory inv = Bukkit.createInventory(null, 54, ChatColor.BLACK + "��� ���� ����Ʈ ��� : " + (page+1));

		Object[] a= QuestList.getKeys().toArray();
		Object[] c =  NPCscript.getConfigurationSection("Quest").getKeys(false).toArray();
		
		byte loc=0;
		for(int count = page*45; count < a.length;count++)
		{
			String QuestName = a[count].toString();
			Set<String> QuestFlow= QuestList.getConfigurationSection(QuestName+".FlowChart").getKeys(false);

			boolean isExit =false;
			for(short counter = 0; counter < c.length; counter ++)
			{
				if(NPCscript.getString("Quest."+counter).equals(QuestName))
				{
					isExit = true;
					break;
				}
			}
			
				
			if(count > a.length || loc >= 45) break;

			String[] Temp = new String[12];
			Temp[0] = ChatColor.WHITE+"����Ʈ ���� ��� : "+QuestFlow.size()+"��";
			int lorecount = 2;
			if(QuestList.getInt(QuestName + ".Need.LV")!=0)
			{
				Temp[lorecount] = ChatColor.RED+"���� ���� : " + QuestList.getInt(QuestName + ".Need.LV") + " �̻�";
				lorecount = lorecount+1;
			}
			if(QuestList.getInt(QuestName + ".Need.Love")!=0)
			{
				Temp[lorecount] = ChatColor.RED + "ģ�е� ���� : " + QuestList.getInt(QuestName + ".Need.Love") + " �̻�";
				lorecount = lorecount+1;
			}
			if(QuestList.getInt(QuestName + ".Need.STR")!=0)
			{
				Temp[lorecount] = ChatColor.RED + ""+ServerOption.STR+" ���� : " + QuestList.getInt(QuestName + ".Need.STR") + " �̻�";
				lorecount = lorecount+1;
			}
			if(QuestList.getInt(QuestName + ".Need.DEX")!=0)
			{
				Temp[lorecount] = ChatColor.RED + ""+ServerOption.DEX+" ���� : " + QuestList.getInt(QuestName + ".Need.DEX") + " �̻�";
				lorecount = lorecount+1;
			}
			if(QuestList.getInt(QuestName + ".Need.INT")!=0)
			{
				Temp[lorecount] = ChatColor.RED + ""+ServerOption.INT+" ���� : " + QuestList.getInt(QuestName + ".Need.INT") + " �̻�";
				lorecount = lorecount+1;
			}
			if(QuestList.getInt(QuestName + ".Need.WILL")!=0)
			{
				Temp[lorecount] = ChatColor.RED + ""+ServerOption.WILL+" ���� : " + QuestList.getInt(QuestName + ".Need.WILL") + " �̻�";
				lorecount = lorecount+1;
			}
			if(QuestList.getInt(QuestName + ".Need.LUK")!=0)
			{
				Temp[lorecount] = ChatColor.RED + ""+ServerOption.LUK+" ���� : " + QuestList.getInt(QuestName + ".Need.LUK" )+ " �̻�";
				lorecount = lorecount+1;
			}
			if(QuestList.getInt(QuestName + ".Server.Limit")==-1)
			{
				Temp[lorecount] = ChatColor.RED + "[����Ʈ �����ο� ����]";
				lorecount = lorecount+1;
			}
			if(QuestList.getInt(QuestName + ".Server.Limit")==0)
			{
				Temp[lorecount] = ChatColor.GREEN + "[����Ʈ ���࿡ ���� ����]";
				lorecount = lorecount+1;
			}
			if(QuestList.getInt(QuestName + ".Server.Limit")>0)
			{
				Temp[lorecount] = ChatColor.DARK_AQUA + "����Ʈ ���� ���� �ο� : " + QuestList.getInt(QuestName + ".Server.Limit")+ " �� ����";
				lorecount = lorecount+1;
			}
			if(QuestList.getString(QuestName + ".Need.PrevQuest") != null && QuestList.getString(QuestName + ".Need.PrevQuest").equalsIgnoreCase("null") == false)
			{
				if(QuestList.contains(QuestList.getString(QuestName + ".Need.PrevQuest")) == false)
				{
					QuestList.set(QuestName + ".Need.PrevQuest","null");
					QuestList.saveConfig();
				}
				else
				{
					Temp[lorecount] = ChatColor.RED + "�ʼ� �Ϸ� ����Ʈ : " + QuestList.getString(QuestName + ".Need.PrevQuest");
					lorecount = lorecount+1;
				}
			}
			
			Temp[lorecount] = "";
			lorecount = lorecount+1;
			if(isExit == true)
				Temp[lorecount] = ChatColor.GREEN+"[��� �� ����Ʈ]";
			else
				Temp[lorecount] = ChatColor.RED+"[��� ���� ���� ����Ʈ]";
			lorecount = lorecount+1;
			
			String[] lore = new String[lorecount];
			for(byte counter = 0; counter < lorecount; counter++)
				lore[counter] = Temp[counter];
			
			String QuestType = QuestList.getString(QuestName + ".Type");
			switch(QuestType)
			{
			case "N" :
				lore[1] = ChatColor.DARK_AQUA+"����Ʈ Ÿ�� : �Ϲ� ����Ʈ";
				Stack2(ChatColor.WHITE + "" + ChatColor.BOLD + QuestName, 340,0,1,Arrays.asList(lore), loc, inv);
				break;
			case "R" :
				lore[1] =ChatColor.DARK_AQUA+"����Ʈ Ÿ�� : �ݺ� ����Ʈ";
				Stack2(ChatColor.WHITE + "" + ChatColor.BOLD + QuestName, 386,0,1,Arrays.asList(lore), loc, inv);
				break;
			case "D" :
				lore[1] =ChatColor.DARK_AQUA+"����Ʈ Ÿ�� : ���� ����Ʈ";
				Stack2(ChatColor.WHITE + "" + ChatColor.BOLD + QuestName, 403,0,1,Arrays.asList(lore), loc, inv);
				break;
			case "W" :
				lore[1] =ChatColor.DARK_AQUA+"����Ʈ Ÿ�� : ���� ����Ʈ";
				Stack2(ChatColor.WHITE + "" + ChatColor.BOLD + QuestName, 403,0,7,Arrays.asList(lore), loc, inv);
				break;
			case "M" :
				lore[1] =ChatColor.DARK_AQUA+"����Ʈ Ÿ�� : �Ѵ� ����Ʈ";
				Stack2(ChatColor.WHITE + "" + ChatColor.BOLD + QuestName, 403,0,31,Arrays.asList(lore), loc, inv);
				break;
			}
			loc++;
		}
		
		if(a.length-(page*44)>45)
			Stack2(ChatColor.WHITE + "" + ChatColor.BOLD + "���� ������", 323,0,1,Arrays.asList(ChatColor.GRAY + "���� �������� �̵� �մϴ�."), 50, inv);
		if(page!=0)
			Stack2(ChatColor.WHITE + "" + ChatColor.BOLD + "���� ������", 323,0,1,Arrays.asList(ChatColor.GRAY + "���� �������� �̵� �մϴ�."), 48, inv);

		//Stack2(ChatColor.WHITE + "" + ChatColor.BOLD + "���� ���", 323,0,1,Arrays.asList(ChatColor.GRAY + "���� ȭ������ ���ư��ϴ�."), 45, inv);
		Stack2(ChatColor.WHITE + "" + ChatColor.BOLD + "�ݱ�", 324,0,1,Arrays.asList(ChatColor.GRAY + "â�� �ݽ��ϴ�.",ChatColor.BLACK + u.getNPCuuid(player)), 53, inv);
		player.openInventory(inv);
	}
	
	public void NPCQuest(Player player, short page)
	{
		Object_UserData u = new Object_UserData();
		YamlController YC = new YamlController(GoldBigDragon_RPG.Main.Main.plugin);
		YamlManager QuestList  = YC.getNewConfig("Quest/QuestList.yml");

	  	if(YC.isExit("NPC/NPCData/"+ u.getNPCuuid(player) +".yml") == false)
	  	{
	  		GoldBigDragon_RPG.Config.NPCconfig NPCC = new GoldBigDragon_RPG.Config.NPCconfig();
	  		NPCC.NPCNPCconfig(u.getNPCuuid(player));
	  	}
		YamlManager NPCscript = YC.getNewConfig("NPC/NPCData/"+ u.getNPCuuid(player) +".yml");
		
		Inventory inv = Bukkit.createInventory(null, 54, ChatColor.BLACK + "���� ������ ����Ʈ ��� : " + (page+1));

		Object[] a = NPCscript.getConfigurationSection("Quest").getKeys(false).toArray();

		YamlManager PlayerQuestList=YC.getNewConfig("Quest/PlayerData/"+player.getUniqueId()+".yml");
		
    	if(YC.isExit("Quest/PlayerData/"+player.getUniqueId()+".yml")==false)
    	{
    		PlayerQuestList.set("PlayerName", player.getName());
    		PlayerQuestList.set("PlayerUUID", player.getUniqueId().toString());
    		PlayerQuestList.createSection("Started");
    		PlayerQuestList.createSection("Ended");
    		PlayerQuestList.saveConfig();
    	}

		PlayerQuestList=YC.getNewConfig("Quest/PlayerData/"+player.getUniqueId()+".yml");
    	
		Set<String> PlayerHas = PlayerQuestList.getConfigurationSection("Started").getKeys(false);
		Set<String> PlayerFinished = PlayerQuestList.getConfigurationSection("Ended").getKeys(false);
		
	  	YamlManager PlayerNPC;
    	if(YC.isExit("NPC/PlayerData/"+player.getUniqueId()+".yml")==false)
    	{
    		PlayerNPC=YC.getNewConfig("NPC/PlayerData/"+player.getUniqueId()+".yml");
    		PlayerNPC.set(u.getNPCuuid(player)+".love", 0);
    		PlayerNPC.set(u.getNPCuuid(player)+".Career", 0);
    		PlayerNPC.saveConfig();
    	}

		PlayerNPC=YC.getNewConfig("NPC/PlayerData/"+player.getUniqueId()+".yml");
		
		byte loc=0;
		for(int count = page*45; count < a.length;count++)
		{
			String QuestName = NPCscript.getString("Quest."+a[count]);
			if(count > a.length || loc >= 45) break;

			if(QuestList.contains(QuestName) == true)
			{
				if(QuestList.getString(QuestName + ".Need.PrevQuest") != null && QuestList.getString(QuestName + ".Need.PrevQuest").equalsIgnoreCase("null") == false)
				{
					if(QuestList.contains(QuestList.getString(QuestName + ".Need.PrevQuest")) == false)
					{
						QuestList.set(QuestName + ".Need.PrevQuest","null");
						QuestList.saveConfig();
					}
				}
				boolean gogogo = false;
				if(QuestList.getString(QuestName + ".Need.PrevQuest") == null || QuestList.getString(QuestName + ".Need.PrevQuest").equalsIgnoreCase("null") == true)
				{
					gogogo = true;
				}
				if(QuestList.getString(QuestName + ".Need.PrevQuest") != null && QuestList.getString(QuestName + ".Need.PrevQuest").equalsIgnoreCase("null") == false)
				{
					if(PlayerQuestList.contains("Ended."+QuestList.getString(QuestName + ".Need.PrevQuest")) == true)
						gogogo = true;
				}
				else
				{
					gogogo = true;
				}
				if(gogogo==true)
				{
					String[] Temp = new String[12];
					Temp[0]="";
					byte lorecount = 1;
					if(QuestList.getInt(QuestName + ".Need.LV")!=0)
					{
					  	YamlManager Config = YC.getNewConfig("config.yml");
						if(Config.getBoolean("Server.Like_The_Mabinogi_Online_Stat_System") == true)
						{
							if(GoldBigDragon_RPG.Main.ServerOption.PlayerList.get(player.getUniqueId().toString()).getStat_RealLevel() >= QuestList.getInt(QuestName + ".Need.LV"))
								Temp[lorecount] = ChatColor.GREEN+"���� ���� : " + QuestList.getInt(QuestName + ".Need.LV") + " �̻�";
							else
								Temp[lorecount] = ChatColor.RED+"���� ���� : " + QuestList.getInt(QuestName + ".Need.LV") + " �̻�";
						}
						else
						{
							if(GoldBigDragon_RPG.Main.ServerOption.PlayerList.get(player.getUniqueId().toString()).getStat_Level() >= QuestList.getInt(QuestName + ".Need.LV"))
								Temp[lorecount] = ChatColor.GREEN+"���� ���� : " + QuestList.getInt(QuestName + ".Need.LV") + " �̻�";
							else
								Temp[lorecount] = ChatColor.RED+"���� ���� : " + QuestList.getInt(QuestName + ".Need.LV") + " �̻�";
						}
						lorecount++;
					}
					if(QuestList.getInt(QuestName + ".Need.Love")!=0)
					{
						if(PlayerNPC.getInt(u.getNPCuuid(player)+".love") >= QuestList.getInt(QuestName + ".Need.Love"))
							Temp[lorecount] = ChatColor.GREEN + "ȣ���� ���� : " + QuestList.getInt(QuestName + ".Need.Love") + " �̻�";
						else
							Temp[lorecount] = ChatColor.RED + "ȣ���� ���� : " + QuestList.getInt(QuestName + ".Need.Love") + " �̻�";
						lorecount++;
					}
					if(QuestList.getInt(QuestName + ".Need.STR")!=0)
					{
						if(GoldBigDragon_RPG.Main.ServerOption.PlayerList.get(player.getUniqueId().toString()).getStat_STR() >= QuestList.getInt(QuestName + ".Need.STR"))
							Temp[lorecount] = ChatColor.GREEN + ""+ServerOption.STR+" ���� : " + QuestList.getInt(QuestName + ".Need.STR") + " �̻�";
						else
							Temp[lorecount] = ChatColor.RED + ""+ServerOption.STR+" ���� : " + QuestList.getInt(QuestName + ".Need.STR") + " �̻�";
						lorecount++;
					}
					if(QuestList.getInt(QuestName + ".Need.DEX")!=0)
					{
						if(GoldBigDragon_RPG.Main.ServerOption.PlayerList.get(player.getUniqueId().toString()).getStat_DEX() >= QuestList.getInt(QuestName + ".Need.DEX"))
							Temp[lorecount] = ChatColor.GREEN + ""+ServerOption.DEX+" ���� : " + QuestList.getInt(QuestName + ".Need.DEX") + " �̻�";
						else
							Temp[lorecount] = ChatColor.RED + ""+ServerOption.DEX+" ���� : " + QuestList.getInt(QuestName + ".Need.DEX") + " �̻�";
						lorecount++;
					}
					if(QuestList.getInt(QuestName + ".Need.INT")!=0)
					{
						if(GoldBigDragon_RPG.Main.ServerOption.PlayerList.get(player.getUniqueId().toString()).getStat_INT() >= QuestList.getInt(QuestName + ".Need.INT"))
							Temp[lorecount] = ChatColor.GREEN + ""+ServerOption.INT+" ���� : " + QuestList.getInt(QuestName + ".Need.INT") + " �̻�";
						else
							Temp[lorecount] = ChatColor.RED + ""+ServerOption.INT+" ���� : " + QuestList.getInt(QuestName + ".Need.INT") + " �̻�";
						lorecount++;
					}
					if(QuestList.getInt(QuestName + ".Need.WILL")!=0)
					{
						if(GoldBigDragon_RPG.Main.ServerOption.PlayerList.get(player.getUniqueId().toString()).getStat_WILL() >= QuestList.getInt(QuestName + ".Need.WILL"))
							Temp[lorecount] = ChatColor.GREEN + ""+ServerOption.WILL+" ���� : " + QuestList.getInt(QuestName + ".Need.WILL") + " �̻�";
						else
							Temp[lorecount] = ChatColor.RED + ""+ServerOption.WILL+" ���� : " + QuestList.getInt(QuestName + ".Need.WILL") + " �̻�";
						lorecount++;
					}
					if(QuestList.getInt(QuestName + ".Need.LUK")!=0)
					{
						if(GoldBigDragon_RPG.Main.ServerOption.PlayerList.get(player.getUniqueId().toString()).getStat_LUK() >= QuestList.getInt(QuestName + ".Need.LUK"))
							Temp[lorecount] = ChatColor.GREEN + ""+ServerOption.LUK+" ���� : " + QuestList.getInt(QuestName + ".Need.LUK" )+ " �̻�";
						else
							Temp[lorecount] = ChatColor.RED + ""+ServerOption.LUK+" ���� : " + QuestList.getInt(QuestName + ".Need.LUK" )+ " �̻�";
						lorecount++;
					}
					if(QuestList.getInt(QuestName + ".Server.Limit")!=0)
					{
						if(QuestList.getInt(QuestName + ".Server.Limit")==-1)
							Temp[lorecount] = ChatColor.RED + "���̻� ����Ʈ�� ������ �� �����ϴ�.";
						else
							Temp[lorecount] = ChatColor.GREEN + "����Ʈ ���� ���� �ο� : " + QuestList.getInt(QuestName + ".Server.Limit")+ " �� ����";
						lorecount++;
					}
					
					Temp[lorecount] = "";
					lorecount++;
					
					String[] lore = new String[lorecount];
					for(byte counter = 0; counter < lorecount; counter++)
						lore[counter] = Temp[counter];
					
					
					switch(QuestList.getString(QuestName+".Type"))
					{
					case "N" :
						if(PlayerHas.toString().contains(QuestName) == false && PlayerFinished.toString().contains(QuestName) == false)
						{
							lore[0] =ChatColor.WHITE+"[�Ϲ� ����Ʈ]";
							Stack2(ChatColor.WHITE + "" + ChatColor.BOLD + QuestName, 340,0,1,Arrays.asList(lore), loc, inv);
							loc++;
						}
						break;
					case "R" :
						if(PlayerHas.toString().contains(QuestName) == false)
						{
							lore[0] =ChatColor.WHITE+"[�ݺ� ����Ʈ]";
							Stack2(ChatColor.WHITE + "" + ChatColor.BOLD + QuestName, 386,0,1,Arrays.asList(lore), loc, inv);
							loc++;
						}
						break;
					case "D" :
						if(PlayerHas.toString().contains(QuestName) == false)
						{
							if(PlayerFinished.contains(QuestName) == true)
							{
								GoldBigDragon_RPG.Util.ETC ETC = new GoldBigDragon_RPG.Util.ETC();
								Object e[] = PlayerFinished.toArray();
								for(int counter=0; counter < e.length; counter++)
								{
									if(e[counter].toString().equalsIgnoreCase(QuestName))
									{
										Long ClearTime = PlayerQuestList.getLong("Ended."+e[counter]+".ClearTime") + (86400000) ;
										if(ClearTime < ETC.getNowUTC())
										{
											lore[0] =ChatColor.WHITE+"[���� ����Ʈ]";
											Stack2(ChatColor.WHITE + "" + ChatColor.BOLD + QuestName, 403,0,1,Arrays.asList(lore), loc, inv);
											loc++;
										}
										else
										{
											ClearTime = PlayerQuestList.getLong("Ended."+e[counter]+".ClearTime") + (86400000) - ETC.getNowUTC() ;
											ClearTime = ClearTime/1000;
											lore[0] =ChatColor.WHITE+"[���� ����Ʈ]";
											String[] timelore = new String[lore.length+3];
											for(byte counter2=0;counter2 < lore.length;counter2++)
												timelore[counter2] = lore[counter2];
											byte hour = 0;
											byte min = 0;
											
											hour = (byte) (ClearTime/3600);
											ClearTime = ClearTime-(3600*hour);
											
											min = (byte)(ClearTime/60);
											ClearTime = ClearTime-(60*min);
											
											timelore[lore.length]="";
											timelore[lore.length+1] = ChatColor.RED + "[����Ʈ ��� �ð�]";
											if(hour>0)
												timelore[lore.length+2] = ChatColor.RED +""+hour+" �ð� " ;
											if(min>0)
												timelore[lore.length+2] =timelore[lore.length+2] + min+" �� " ;
											if(ClearTime>0)
												timelore[lore.length+2] =timelore[lore.length+2] + ClearTime+" �� " ;
											
											Stack2(ChatColor.WHITE + "" + ChatColor.BOLD + QuestName, 403,0,1,Arrays.asList(timelore), loc, inv);
											loc++;
										}
									}
								}
							}
							else
							{
								lore[0] =ChatColor.WHITE+"[���� ����Ʈ]";
								Stack2(ChatColor.WHITE + "" + ChatColor.BOLD + QuestName, 403,0,1,Arrays.asList(lore), loc, inv);
								loc++;
							}
						}
						break;
					case "W" :
						if(PlayerHas.toString().contains(QuestName) == false)
						{
							if(PlayerFinished.contains(QuestName) == true)
							{
								GoldBigDragon_RPG.Util.ETC ETC = new GoldBigDragon_RPG.Util.ETC();
								Object e[] = PlayerFinished.toArray();
								for(int counter=0; counter < e.length; counter++)
								{
									if(e[counter].toString().equalsIgnoreCase(QuestName))
									{
										Long ClearTime = PlayerQuestList.getLong("Ended."+e[counter]+".ClearTime") + (604800000) ;
										if(ClearTime < ETC.getNowUTC())
										{
											lore[0] =ChatColor.WHITE+"[�ְ� ����Ʈ]";
											Stack2(ChatColor.WHITE + "" + ChatColor.BOLD + QuestName, 403,0,7,Arrays.asList(lore), loc, inv);
											loc++;
										}
										else
										{
											ClearTime = PlayerQuestList.getLong("Ended."+e[counter]+".ClearTime") + (604800000) - ETC.getNowUTC() ;
											ClearTime = ClearTime/1000;
											lore[0] =ChatColor.WHITE+"[�ְ� ����Ʈ]";
											String[] timelore = new String[lore.length+3];
											for(int counter2=0;counter2 < lore.length;counter2++)
												timelore[counter2] = lore[counter2];
											byte day = 0;
											byte hour = 0;
											byte min = 0;
											
											day = (byte) (ClearTime/86400);
											ClearTime = ClearTime-(86400*day);
											
											hour = (byte) (ClearTime/3600);
											ClearTime = ClearTime-(3600*hour);
											
											min = (byte)(ClearTime/60);
											ClearTime = ClearTime-(60*min);
											
											timelore[lore.length]="";
											timelore[lore.length+1] = ChatColor.RED + "[����Ʈ ��� �ð�]";
											if(hour>0)
												timelore[lore.length+2] = ChatColor.RED +""+day+" �� " ;
											if(hour>0)
												timelore[lore.length+2] = timelore[lore.length+2] +""+hour+" �ð� " ;
											if(min>0)
												timelore[lore.length+2] =timelore[lore.length+2] + min+" �� " ;
											if(ClearTime>0)
												timelore[lore.length+2] =timelore[lore.length+2] + ClearTime+" �� " ;
											
											Stack2(ChatColor.WHITE + "" + ChatColor.BOLD + QuestName, 403,0,7,Arrays.asList(timelore), loc, inv);
											loc++;
										}
									}
								}
							}
							else
							{
								lore[0] =ChatColor.WHITE+"[�ְ� ����Ʈ]";
								Stack2(ChatColor.WHITE + "" + ChatColor.BOLD + QuestName, 403,0,7,Arrays.asList(lore), loc, inv);
								loc++;
							}
						}
						break;
					case "M" :
						if(PlayerHas.toString().contains(QuestName) == false)
						{
							if(PlayerFinished.contains(QuestName) == true)
							{
								GoldBigDragon_RPG.Util.ETC ETC = new GoldBigDragon_RPG.Util.ETC();
								Object e[] = PlayerFinished.toArray();
								for(int counter=0; counter < e.length; counter++)
								{
									if(e[counter].toString().equalsIgnoreCase(QuestName))
									{
										Long ClearTime = (PlayerQuestList.getLong("Ended."+e[counter]+".ClearTime") + Long.parseLong("2678400000")) ;
										if(ClearTime < ETC.getNowUTC())
										{
											lore[0] =ChatColor.WHITE+"[���� ����Ʈ]";
											Stack2(ChatColor.WHITE + "" + ChatColor.BOLD + QuestName, 403,0,31,Arrays.asList(lore), loc, inv);
											loc++;
										}
										else
										{
											ClearTime = PlayerQuestList.getLong("Ended."+e[counter]+".ClearTime") + Long.parseLong("2678400000") - ETC.getNowUTC() ;
											ClearTime = ClearTime/1000;
											lore[0] =ChatColor.WHITE+"[���� ����Ʈ]";
											String[] timelore = new String[lore.length+3];
											for(byte counter2=0;counter2 < lore.length;counter2++)
												timelore[counter2] = lore[counter2];
											byte day = 0;
											byte hour = 0;
											byte min = 0;
											
											day = (byte) (ClearTime/86400);
											ClearTime = ClearTime-(86400*day);
											
											hour = (byte) (ClearTime/3600);
											ClearTime = ClearTime-(3600*hour);
											
											min = (byte)(ClearTime/60);
											ClearTime = ClearTime-(60*min);
											
											timelore[lore.length]="";
											timelore[lore.length+1] = ChatColor.RED + "[����Ʈ ��� �ð�]";
											if(hour>0)
												timelore[lore.length+2] = ChatColor.RED +""+day+" �� " ;
											if(hour>0)
												timelore[lore.length+2] = timelore[lore.length+2] +""+hour+" �ð� " ;
											if(min>0)
												timelore[lore.length+2] =timelore[lore.length+2] + min+" �� " ;
											if(ClearTime>0)
												timelore[lore.length+2] =timelore[lore.length+2] + ClearTime+" �� " ;
											
											Stack2(ChatColor.WHITE + "" + ChatColor.BOLD + QuestName, 403,0,31,Arrays.asList(timelore), loc, inv);
											loc++;
										}
									}
								}
							}
							else
							{
								lore[0] =ChatColor.WHITE+"[���� ����Ʈ]";
								Stack2(ChatColor.WHITE + "" + ChatColor.BOLD + QuestName, 403,0,31,Arrays.asList(lore), loc, inv);
								loc++;
							}
						}
						break;
					}
				}
			}
			else
			{
				NPCscript.removeKey("Quest."+a[count]);
				NPCscript.saveConfig();
			}
		}
		
		if(a.length-(page*44)>45)
			Stack2(ChatColor.WHITE + "" + ChatColor.BOLD + "���� ������", 323,0,1,Arrays.asList(ChatColor.GRAY + "���� �������� �̵� �մϴ�."), 50, inv);
		if(page!=0)
			Stack2(ChatColor.WHITE + "" + ChatColor.BOLD + "���� ������", 323,0,1,Arrays.asList(ChatColor.GRAY + "���� �������� �̵� �մϴ�."), 48, inv);

		//Stack2(ChatColor.WHITE + "" + ChatColor.BOLD + "���� ���", 323,0,1,Arrays.asList(ChatColor.GRAY + "���� ȭ������ ���ư��ϴ�."), 45, inv);
		Stack2(ChatColor.WHITE + "" + ChatColor.BOLD + "�ݱ�", 324,0,1,Arrays.asList(ChatColor.GRAY + "â�� �ݽ��ϴ�.",ChatColor.BLACK + u.getNPCuuid(player)), 53, inv);
		player.openInventory(inv);
	}
	
	public void NPCjobGUI(Player player,String NPCname)
	{
		Object_UserData u = new Object_UserData();
		Inventory inv = Bukkit.createInventory(null, 27, ChatColor.BLACK + "NPC ���� ����");

		Stack2(ChatColor.WHITE +""+ChatColor.BOLD + "���� ����", 397,3,1,Arrays.asList(ChatColor.GRAY + "NPC�� ������ ���۴ϴ�."), 1, inv);
		Stack2(ChatColor.WHITE +""+ChatColor.BOLD + "��������", 145,0,1,Arrays.asList(ChatColor.GRAY + "����, ����, �� ���",ChatColor.GRAY+"�ݼ����� ���۵� ������ ��Ĩ�ϴ�."), 2, inv);
		Stack2(ChatColor.WHITE +""+ChatColor.BOLD + "�ּ���", 116,0,1,Arrays.asList(ChatColor.GRAY + "�÷��̾�� ���� ������ �̴ϴ�."), 3, inv);
		Stack2(ChatColor.WHITE +""+ChatColor.BOLD + "����", 373,8261,1,Arrays.asList(ChatColor.GRAY + "�÷��̾ ������ ġ���� �ݴϴ�."), 4, inv);
		Stack2(ChatColor.WHITE +""+ChatColor.BOLD + "���� ����", 314,0,1,Arrays.asList(ChatColor.GRAY + "�÷��̾ ���� ���ǿ� ������ ���",ChatColor.GRAY+"�÷��̾ Ư�� �������� ���� �����ݴϴ�.","",ChatColor.RED+"�� ����� ���� �ý�����",ChatColor.RED+"������ ���丮�� ��츸 ��� �����մϴ�."), 5, inv);
		Stack2(ChatColor.WHITE +""+ChatColor.BOLD + "���� �̵�����", 368,0,1,Arrays.asList(ChatColor.GRAY + "Ư�� ��ġ�� �ڷ���Ʈ �����ݴϴ�."), 6, inv);
		Stack2(ChatColor.WHITE +""+ChatColor.BOLD + "���� ����", 417,0,1,Arrays.asList(ChatColor.GRAY + "�������� ���� �� �ݴϴ�."), 7, inv);
		Stack2(ChatColor.WHITE +""+ChatColor.BOLD + "�� ������", 351,10,1,Arrays.asList(ChatColor.GRAY + "�����ۿ� ���� ���� �����ݴϴ�."), 10, inv);

		Stack2(ChatColor.WHITE +""+ChatColor.BOLD + "���� �޴�", 323,0,1,Arrays.asList(ChatColor.GRAY + "���� �޴��� ���ư��ϴ�.",ChatColor.BLACK + NPCname), 18, inv);
		Stack2(ChatColor.WHITE +""+ChatColor.BOLD + "������", 324,0,1,Arrays.asList(ChatColor.YELLOW + ""+ChatColor.stripColor(NPCname)+ChatColor.GRAY +"����",ChatColor.GRAY + "��ȭ�� �����մϴ�.",ChatColor.BLACK + u.getNPCuuid(player)), 26, inv);
		
		player.openInventory(inv);
	}
	
	public void WarpMainGUI(Player player, int page,String NPCname)
	{
		Object_UserData u = new Object_UserData();
		YamlController YC = new YamlController(GoldBigDragon_RPG.Main.Main.plugin);
		YamlManager NPCConfig =YC.getNewConfig("NPC/NPCData/"+u.getNPCuuid(player)+".yml");
		Inventory inv = Bukkit.createInventory(null, 54, ChatColor.BLACK + "NPC ���� ���� ��� : " + (page+1));
		Object[] WarpList= NPCConfig.getConfigurationSection("Job.WarpList").getKeys(false).toArray();
		YamlManager AreaConfig = YC.getNewConfig("Area/AreaList.yml");

		Object[] AreaList = null;
		boolean isExit = false;
		for(byte c = 0; c < 5; c++)
		{
			AreaList = AreaConfig.getKeys().toArray();
			for(short count = 0; count < WarpList.length;count++)
			{
				isExit = false;
				for(short counter = 0; counter < AreaList.length; counter++)
				{
					if(AreaList[counter].equals(NPCConfig.getString("Job.WarpList."+count+".Area"))==true)
					{
						isExit = true;
						break;
					}
				}
				if(isExit == false)
				{
					short Acount =  (short) (WarpList.length-1);
					for(short counter = count;counter <Acount;counter++)
						NPCConfig.set("Job.WarpList."+counter, NPCConfig.get("Job.WarpList."+(counter+1)));
					NPCConfig.removeKey("Job.WarpList."+Acount);
				}
			}
			NPCConfig.saveConfig();
		}
		WarpList= NPCConfig.getConfigurationSection("Job.WarpList").getKeys(false).toArray();
		byte loc=0;
		for(int count = page*45; count < WarpList.length;count++)
		{
			if(count > WarpList.length || loc >= 45) break;

			if(player.isOp() == true)
			Stack2(ChatColor.WHITE + "" + ChatColor.BOLD + NPCConfig.getString("Job.WarpList."+count+".DisplayName"), 368,0,1,Arrays.asList("",
					ChatColor.YELLOW + "���� ��� : "+ChatColor.WHITE + "" +NPCConfig.getInt("Job.WarpList."+count+".Cost")+" "+ServerOption.Money
					,"",ChatColor.YELLOW+"[�� Ŭ���� �ش� �������� �̵�]",ChatColor.RED+"[Shift + ��Ŭ���� ���� ����]"), loc, inv);
			else
				Stack2(ChatColor.WHITE + "" + ChatColor.BOLD + NPCConfig.getString("Job.WarpList."+count+".DisplayName"), 368,0,1,Arrays.asList("",
						ChatColor.YELLOW + "���� ��� : "+ChatColor.WHITE + "" +NPCConfig.getInt("Job.WarpList."+count+".Cost") +" "+ServerOption.Money
						,"",ChatColor.YELLOW+"[�� Ŭ���� �ش� �������� �̵�]"), loc, inv);
			loc++;
		}
		
		if(WarpList.length-(page*44)>45)
			Stack2(ChatColor.WHITE + "" + ChatColor.BOLD + "���� ������", 323,0,1,Arrays.asList(ChatColor.GRAY + "���� �������� �̵� �մϴ�."), 50, inv);
		if(page!=0)
			Stack2(ChatColor.WHITE + "" + ChatColor.BOLD + "���� ������", 323,0,1,Arrays.asList(ChatColor.GRAY + "���� �������� �̵� �մϴ�."), 48, inv);

		if(player.isOp() == true)
			Stack2(ChatColor.WHITE + "" + ChatColor.BOLD + "�� ����", 381,0,1,Arrays.asList(ChatColor.GRAY + "���ο� ���� ������ �����մϴ�.","",ChatColor.YELLOW+"[������ ������ ������ ��� �����մϴ�.]"), 49, inv);
		Stack2(ChatColor.WHITE + "" + ChatColor.BOLD + "���� ���", 323,0,1,Arrays.asList(ChatColor.GRAY + "���� ȭ������ ���ư��ϴ�."), 45, inv);
		Stack2(ChatColor.WHITE + "" + ChatColor.BOLD + "�ݱ�", 324,0,1,Arrays.asList(ChatColor.GRAY + "â�� �ݽ��ϴ�.",ChatColor.BLACK+NPCname), 53, inv);
		player.openInventory(inv);
	}
	
	public void WarperGUI(Player player, short page, String NPCname)
	{
		YamlController YC = new YamlController(GoldBigDragon_RPG.Main.Main.plugin);
		YamlManager AreaConfig =YC.getNewConfig("Area/AreaList.yml");
		
		Inventory inv = Bukkit.createInventory(null, 54, ChatColor.BLACK + "NPC ���� ��� : " + (page+1));

		Object[] AreaList= AreaConfig.getConfigurationSection("").getKeys(false).toArray();
		
		byte loc=0;
		for(int count = page*45; count < AreaList.length;count++)
		{
			String AreaName = AreaList[count].toString();
			
			if(count > AreaList.length || loc >= 45) break;
			String world = AreaConfig.getString(AreaName+".World");
			int MinXLoc = AreaConfig.getInt(AreaName+".X.Min");
			short MinYLoc = (short) AreaConfig.getInt(AreaName+".Y.Min");
			int MinZLoc = AreaConfig.getInt(AreaName+".Z.Min");
			int MaxXLoc = AreaConfig.getInt(AreaName+".X.Max");
			short MaxYLoc = (short) AreaConfig.getInt(AreaName+".Y.Max");
			int MaxZLoc = AreaConfig.getInt(AreaName+".Z.Max");
			Stack2(ChatColor.WHITE + "" + ChatColor.BOLD + AreaName, 395,0,1,Arrays.asList(
					ChatColor.DARK_AQUA+"���� : "+world,ChatColor.DARK_AQUA+"X ���� : "+MinXLoc+" ~ " + MaxXLoc
					,ChatColor.DARK_AQUA+"Y ���� : "+MinYLoc+" ~ " + MaxYLoc
					,ChatColor.DARK_AQUA+"Z ���� : "+MinZLoc+" ~ " + MaxZLoc
					,"",ChatColor.YELLOW+"[�� Ŭ���� ���� �߰�]"), loc, inv);
			
			loc++;
		}
		
		if(AreaList.length-(page*44)>45)
			Stack2(ChatColor.WHITE + "" + ChatColor.BOLD + "���� ������", 323,0,1,Arrays.asList(ChatColor.GRAY + "���� �������� �̵� �մϴ�."), 50, inv);
		if(page!=0)
			Stack2(ChatColor.WHITE + "" + ChatColor.BOLD + "���� ������", 323,0,1,Arrays.asList(ChatColor.GRAY + "���� �������� �̵� �մϴ�."), 48, inv);

		Stack2(ChatColor.WHITE + "" + ChatColor.BOLD + "���� ���", 323,0,1,Arrays.asList(ChatColor.GRAY + "���� ȭ������ ���ư��ϴ�."), 45, inv);
		Stack2(ChatColor.WHITE + "" + ChatColor.BOLD + "�ݱ�", 324,0,1,Arrays.asList(ChatColor.GRAY + "â�� �ݽ��ϴ�.",ChatColor.BLACK+NPCname), 53, inv);
		player.openInventory(inv);
	}
	
	public void UpgraderGUI(Player player, short page, String NPCname)
	{
		Object_UserData u = new Object_UserData();
		YamlController YC = new YamlController(GoldBigDragon_RPG.Main.Main.plugin);
		YamlManager NPCConfig =YC.getNewConfig("NPC/NPCData/"+u.getNPCuuid(player)+".yml");
		YamlManager UpgradeRecipe =YC.getNewConfig("Item/Upgrade.yml");
		
		Inventory inv = Bukkit.createInventory(null, 54, ChatColor.BLACK + "NPC ���� ���� : " + (page+1));

		Object[] UpgradeAbleList = NPCConfig.getConfigurationSection("Job.UpgradeRecipe").getKeys(false).toArray();
		long playerMoney = GoldBigDragon_RPG.Main.ServerOption.PlayerList.get(player.getUniqueId().toString()).getStat_Money();
		byte loc=0;
		for(int count = page*45; count < UpgradeAbleList.length;count++)
		{
			String RecipeName = UpgradeAbleList[count].toString();
			
			String Lore=null;
			if(UpgradeRecipe.getString(RecipeName+".Only").compareTo("null")==0)
				Lore = ChatColor.WHITE+"[��� ���]%enter%%enter%";
			else
				Lore = UpgradeRecipe.getString(RecipeName+".Only")+"%enter%%enter%";

			if(UpgradeRecipe.getInt(RecipeName+".MaxDurability") > 0)
				Lore = Lore+ChatColor.DARK_AQUA+" �� �ִ� ������ : "+UpgradeRecipe.getInt(RecipeName+".MaxDurability")+"%enter%";
			else if(UpgradeRecipe.getInt(RecipeName+".MaxDurability") < 0)
				Lore = Lore+ChatColor.RED+" �� �ִ� ������ : "+UpgradeRecipe.getInt(RecipeName+".MaxDurability")+"%enter%";
			if(UpgradeRecipe.getInt(RecipeName+".MinDamage") > 0)
				Lore = Lore+ChatColor.DARK_AQUA+" �� �ּ� "+ServerOption.Damage+" : "+UpgradeRecipe.getInt(RecipeName+".MinDamage")+"%enter%";
			else if(UpgradeRecipe.getInt(RecipeName+".MinDamage") < 0)
				Lore = Lore+ChatColor.RED+" �� �ּ� "+ServerOption.Damage+" : "+UpgradeRecipe.getInt(RecipeName+".MinDamage")+"%enter%";
			if(UpgradeRecipe.getInt(RecipeName+".MaxDamage") > 0)
				Lore = Lore+ChatColor.DARK_AQUA+" �� �ִ� "+ServerOption.Damage+" : "+UpgradeRecipe.getInt(RecipeName+".MaxDamage")+"%enter%";
			else if(UpgradeRecipe.getInt(RecipeName+".MaxDamage") < 0)
				Lore = Lore+ChatColor.RED+" �� �ִ� "+ServerOption.Damage+" : "+UpgradeRecipe.getInt(RecipeName+".MaxDamage")+"%enter%";
			if(UpgradeRecipe.getInt(RecipeName+".MinMaDamage") > 0)
				Lore = Lore+ChatColor.DARK_AQUA+" �� �ּ� "+ServerOption.MagicDamage+" : "+UpgradeRecipe.getInt(RecipeName+".MinMaDamage")+"%enter%";
			else if(UpgradeRecipe.getInt(RecipeName+".MinMaDamage") < 0)
				Lore = Lore+ChatColor.RED+" �� �ּ� "+ServerOption.MagicDamage+" : "+UpgradeRecipe.getInt(RecipeName+".MinMaDamage")+"%enter%";
			if(UpgradeRecipe.getInt(RecipeName+".MaxMaDamage") > 0)
				Lore = Lore+ChatColor.DARK_AQUA+" �� �ִ� "+ServerOption.MagicDamage+" : "+UpgradeRecipe.getInt(RecipeName+".MaxMaDamage")+"%enter%";
			else if(UpgradeRecipe.getInt(RecipeName+".MaxMaDamage") < 0)
				Lore = Lore+ChatColor.RED+" �� �ִ� "+ServerOption.MagicDamage+" : "+UpgradeRecipe.getInt(RecipeName+".MaxMaDamage")+"%enter%";
			if(UpgradeRecipe.getInt(RecipeName+".DEF") > 0)
				Lore = Lore+ChatColor.DARK_AQUA+" �� ��� : "+UpgradeRecipe.getInt(RecipeName+".DEF")+"%enter%";
			else if(UpgradeRecipe.getInt(RecipeName+".DEF") < 0)
				Lore = Lore+ChatColor.RED+" �� ��� : "+UpgradeRecipe.getInt(RecipeName+".DEF")+"%enter%";
			if(UpgradeRecipe.getInt(RecipeName+".Protect") > 0)
				Lore = Lore+ChatColor.DARK_AQUA+" �� ��ȣ : "+UpgradeRecipe.getInt(RecipeName+".Protect")+"%enter%";
			else if(UpgradeRecipe.getInt(RecipeName+".Protect") < 0)
				Lore = Lore+ChatColor.RED+" �� ��ȣ : "+UpgradeRecipe.getInt(RecipeName+".Protect")+"%enter%";
			if(UpgradeRecipe.getInt(RecipeName+".MaDEF") > 0)
				Lore = Lore+ChatColor.DARK_AQUA+" �� ���� ��� : "+UpgradeRecipe.getInt(RecipeName+".MaDEF")+"%enter%";
			else if(UpgradeRecipe.getInt(RecipeName+".MaDEF") < 0)
				Lore = Lore+ChatColor.RED+" �� ���� ��� : "+UpgradeRecipe.getInt(RecipeName+".MaDEF")+"%enter%";
			if(UpgradeRecipe.getInt(RecipeName+".MaProtect") > 0)
				Lore = Lore+ChatColor.DARK_AQUA+" �� ���� ��ȣ : "+UpgradeRecipe.getInt(RecipeName+".MaProtect")+"%enter%";
			else if(UpgradeRecipe.getInt(RecipeName+".MaProtect") < 0)
				Lore = Lore+ChatColor.RED+" �� ���� ��ȣ : "+UpgradeRecipe.getInt(RecipeName+".MaProtect")+"%enter%";
			if(UpgradeRecipe.getInt(RecipeName+".Balance") > 0)
				Lore = Lore+ChatColor.DARK_AQUA+" �� �뷱�� : "+UpgradeRecipe.getInt(RecipeName+".Balance")+"%enter%";
			else if(UpgradeRecipe.getInt(RecipeName+".Balance") < 0)
				Lore = Lore+ChatColor.RED+" �� �뷱�� : "+UpgradeRecipe.getInt(RecipeName+".Balance")+"%enter%";
			if(UpgradeRecipe.getInt(RecipeName+".Critical") > 0)
				Lore = Lore+ChatColor.DARK_AQUA+" �� ũ��Ƽ�� : "+UpgradeRecipe.getInt(RecipeName+".Critical")+"%enter%";
			else if(UpgradeRecipe.getInt(RecipeName+".Critical") < 0)
				Lore = Lore+ChatColor.RED+" �� ũ��Ƽ�� : "+UpgradeRecipe.getInt(RecipeName+".Critical")+"%enter%";
			
			Lore = Lore+"%enter%"+UpgradeRecipe.getString(RecipeName+".Lore")+"%enter%%enter%";

			Lore = Lore+ChatColor.YELLOW+" �� ���� Ƚ�� : "+ChatColor.WHITE+UpgradeRecipe.getInt(RecipeName+".UpgradeAbleLevel")+ChatColor.YELLOW+" ȸ° ���� ����%enter%";
			Lore = Lore+ChatColor.YELLOW+" �� �ʿ� ���õ� : "+ChatColor.WHITE+UpgradeRecipe.getInt(RecipeName+".DecreaseProficiency")+"%enter% ";

			Lore = Lore+"%enter%"+ChatColor.RED+""+ChatColor.BOLD+"���� ��� : "+NPCConfig.getInt("Job.UpgradeRecipe."+RecipeName)+" "+ServerOption.Money+"%enter% " ;

			if(playerMoney < NPCConfig.getInt("Job.UpgradeRecipe."+RecipeName+".Cost"))
				Lore = Lore+ChatColor.RED+""+ChatColor.BOLD+"���� �ݾ� : " +playerMoney+" "+ServerOption.Money+"%enter%";
			else
				Lore = Lore+ChatColor.AQUA+""+ChatColor.BOLD+"���� �ݾ� : " +playerMoney+" "+ServerOption.Money+"%enter%";
				
			if(player.isOp() == true)
				Lore = Lore+"%enter%"+ChatColor.RED+"[Shift + �� Ŭ���� ������ ����]%enter% ";
			
			String[] scriptA = Lore.split("%enter%");
			for(byte counter = 0; counter < scriptA.length; counter++)
				scriptA[counter] =  " "+scriptA[counter];
			
			
			if(count > UpgradeAbleList.length || loc >= 45) break;
			Stack2(ChatColor.WHITE + "" + ChatColor.BOLD + RecipeName, 395,0,1,Arrays.asList(scriptA), loc, inv);
			
			loc++;
		}
		
		if(UpgradeAbleList.length-(page*44)>45)
			Stack2(ChatColor.WHITE + "" + ChatColor.BOLD + "���� ������", 323,0,1,Arrays.asList(ChatColor.GRAY + "���� �������� �̵� �մϴ�."), 50, inv);
		if(page!=0)
			Stack2(ChatColor.WHITE + "" + ChatColor.BOLD + "���� ������", 323,0,1,Arrays.asList(ChatColor.GRAY + "���� �������� �̵� �մϴ�."), 48, inv);

		if(player.isOp() == true)
			Stack2(ChatColor.WHITE + "" + ChatColor.BOLD + "������ �߰�", 386,0,1,Arrays.asList(ChatColor.GRAY + "���� ���� ������ ���ο�",ChatColor.GRAY+"���� �����Ǹ� �˰� �մϴ�."), 49, inv);
		Stack2(ChatColor.WHITE + "" + ChatColor.BOLD + "���� ���", 323,0,1,Arrays.asList(ChatColor.GRAY + "���� ȭ������ ���ư��ϴ�."), 45, inv);
		Stack2(ChatColor.WHITE + "" + ChatColor.BOLD + "�ݱ�", 324,0,1,Arrays.asList(ChatColor.GRAY + "â�� �ݽ��ϴ�.",ChatColor.BLACK+NPCname), 53, inv);
		player.openInventory(inv);
	}
	
	public void SelectUpgradeRecipeGUI(Player player, short page, String NPCname)
	{
		YamlController YC = new YamlController(GoldBigDragon_RPG.Main.Main.plugin);
		YamlManager RecipeList = YC.getNewConfig("Item/Upgrade.yml");
		
		Inventory inv = Bukkit.createInventory(null, 54, ChatColor.BLACK + "NPC ������ �߰� : " + (page+1));

		Object[] a= RecipeList.getKeys().toArray();

		byte loc=0;
		for(int count = page*45; count < a.length;count++)
		{
			if(count > a.length || loc >= 45) break;
			String ItemName =a[count].toString();
			String Lore=null;
			if(RecipeList.getString(ItemName+".Only").compareTo("null")==0)
				Lore = ChatColor.WHITE+"[��� ���]%enter%%enter%";
			else
				Lore = RecipeList.getString(ItemName+".Only")+"%enter%%enter%";

			if(RecipeList.getInt(ItemName+".MaxDurability") > 0)
				Lore = Lore+ChatColor.DARK_AQUA+" �� �ִ� ������ : "+RecipeList.getInt(ItemName+".MaxDurability")+"%enter%";
			else if(RecipeList.getInt(ItemName+".MaxDurability") < 0)
				Lore = Lore+ChatColor.RED+" �� �ִ� ������ : "+RecipeList.getInt(ItemName+".MaxDurability")+"%enter%";
			if(RecipeList.getInt(ItemName+".MinDamage") > 0)
				Lore = Lore+ChatColor.DARK_AQUA+" �� �ּ� "+ServerOption.Damage+" : "+RecipeList.getInt(ItemName+".MinDamage")+"%enter%";
			else if(RecipeList.getInt(ItemName+".MinDamage") < 0)
				Lore = Lore+ChatColor.RED+" �� �ּ� "+ServerOption.Damage+" : "+RecipeList.getInt(ItemName+".MinDamage")+"%enter%";
			if(RecipeList.getInt(ItemName+".MaxDamage") > 0)
				Lore = Lore+ChatColor.DARK_AQUA+" �� �ִ� "+ServerOption.Damage+" : "+RecipeList.getInt(ItemName+".MaxDamage")+"%enter%";
			else if(RecipeList.getInt(ItemName+".MaxDamage") < 0)
				Lore = Lore+ChatColor.RED+" �� �ִ� "+ServerOption.Damage+" : "+RecipeList.getInt(ItemName+".MaxDamage")+"%enter%";
			if(RecipeList.getInt(ItemName+".MinMaDamage") > 0)
				Lore = Lore+ChatColor.DARK_AQUA+" �� �ּ� "+ServerOption.MagicDamage+" : "+RecipeList.getInt(ItemName+".MinMaDamage")+"%enter%";
			else if(RecipeList.getInt(ItemName+".MinMaDamage") < 0)
				Lore = Lore+ChatColor.RED+" �� �ּ� "+ServerOption.MagicDamage+" : "+RecipeList.getInt(ItemName+".MinMaDamage")+"%enter%";
			if(RecipeList.getInt(ItemName+".MaxMaDamage") > 0)
				Lore = Lore+ChatColor.DARK_AQUA+" �� �ִ� "+ServerOption.MagicDamage+" : "+RecipeList.getInt(ItemName+".MaxMaDamage")+"%enter%";
			else if(RecipeList.getInt(ItemName+".MaxMaDamage") < 0)
				Lore = Lore+ChatColor.RED+" �� �ִ� "+ServerOption.MagicDamage+" : "+RecipeList.getInt(ItemName+".MaxMaDamage")+"%enter%";
			if(RecipeList.getInt(ItemName+".DEF") > 0)
				Lore = Lore+ChatColor.DARK_AQUA+" �� ��� : "+RecipeList.getInt(ItemName+".DEF")+"%enter%";
			else if(RecipeList.getInt(ItemName+".DEF") < 0)
				Lore = Lore+ChatColor.RED+" �� ��� : "+RecipeList.getInt(ItemName+".DEF")+"%enter%";
			if(RecipeList.getInt(ItemName+".Protect") > 0)
				Lore = Lore+ChatColor.DARK_AQUA+" �� ��ȣ : "+RecipeList.getInt(ItemName+".Protect")+"%enter%";
			else if(RecipeList.getInt(ItemName+".Protect") < 0)
				Lore = Lore+ChatColor.RED+" �� ��ȣ : "+RecipeList.getInt(ItemName+".Protect")+"%enter%";
			if(RecipeList.getInt(ItemName+".MaDEF") > 0)
				Lore = Lore+ChatColor.DARK_AQUA+" �� ���� ��� : "+RecipeList.getInt(ItemName+".MaDEF")+"%enter%";
			else if(RecipeList.getInt(ItemName+".MaDEF") < 0)
				Lore = Lore+ChatColor.RED+" �� ���� ��� : "+RecipeList.getInt(ItemName+".MaDEF")+"%enter%";
			if(RecipeList.getInt(ItemName+".MaProtect") > 0)
				Lore = Lore+ChatColor.DARK_AQUA+" �� ���� ��ȣ : "+RecipeList.getInt(ItemName+".MaProtect")+"%enter%";
			else if(RecipeList.getInt(ItemName+".MaProtect") < 0)
				Lore = Lore+ChatColor.RED+" �� ���� ��ȣ : "+RecipeList.getInt(ItemName+".MaProtect")+"%enter%";
			if(RecipeList.getInt(ItemName+".Balance") > 0)
				Lore = Lore+ChatColor.DARK_AQUA+" �� �뷱�� : "+RecipeList.getInt(ItemName+".Balance")+"%enter%";
			else if(RecipeList.getInt(ItemName+".Balance") < 0)
				Lore = Lore+ChatColor.RED+" �� �뷱�� : "+RecipeList.getInt(ItemName+".Balance")+"%enter%";
			if(RecipeList.getInt(ItemName+".Critical") > 0)
				Lore = Lore+ChatColor.DARK_AQUA+" �� ũ��Ƽ�� : "+RecipeList.getInt(ItemName+".Critical")+"%enter%";
			else if(RecipeList.getInt(ItemName+".Critical") < 0)
				Lore = Lore+ChatColor.RED+" �� ũ��Ƽ�� : "+RecipeList.getInt(ItemName+".Critical")+"%enter%";
			
			Lore = Lore+"%enter%"+RecipeList.getString(ItemName+".Lore")+"%enter%%enter%";

			Lore = Lore+ChatColor.YELLOW+" �� ���� Ƚ�� : "+ChatColor.WHITE+RecipeList.getInt(ItemName+".UpgradeAbleLevel")+ChatColor.YELLOW+" ȸ° ���� ����%enter%";
			Lore = Lore+ChatColor.YELLOW+" �� �ʿ� ���õ� : "+ChatColor.WHITE+RecipeList.getInt(ItemName+".DecreaseProficiency")+"%enter% ";

			Lore = Lore+"%enter%"+ChatColor.YELLOW+"[�� Ŭ���� ������ ���]%enter%";

			String[] scriptA = Lore.split("%enter%");
			for(byte counter = 0; counter < scriptA.length; counter++)
				scriptA[counter] =  " "+scriptA[counter];
			
			Stack(ChatColor.WHITE+ItemName, 395, 0, 1,Arrays.asList(scriptA), loc, inv);
			loc++;
		}
		
		if(a.length-(page*44)>45)
			Stack(ChatColor.WHITE + "" + ChatColor.BOLD + "���� ������", 323,0,1,Arrays.asList(ChatColor.GRAY + "���� �������� �̵� �մϴ�."), 50, inv);
		if(page!=0)
			Stack(ChatColor.WHITE + "" + ChatColor.BOLD + "���� ������", 323,0,1,Arrays.asList(ChatColor.GRAY + "���� �������� �̵� �մϴ�."), 48, inv);

		Stack(ChatColor.WHITE + "" + ChatColor.BOLD + "���� ���", 323,0,1,Arrays.asList(ChatColor.GRAY + "���� ȭ������ ���ư��ϴ�."), 45, inv);
		Stack(ChatColor.WHITE + "" + ChatColor.BOLD + "�ݱ�", 324,0,1,Arrays.asList(ChatColor.GRAY + "â�� �ݽ��ϴ�.",ChatColor.BLACK+NPCname), 53, inv);
		player.openInventory(inv);
	}
	
	public void RuneEquipGUI(Player player, String NPCname)
	{
		Object_UserData u = new Object_UserData();
		Inventory inv = Bukkit.createInventory(null, 27, ChatColor.BLACK + "NPC �� ����");

		YamlController YC = new YamlController(GoldBigDragon_RPG.Main.Main.plugin);
		YamlManager NPCscript = YC.getNewConfig("NPC/NPCData/"+ u.getNPCuuid(player)  +".yml");
		Stack2(ChatColor.BLUE+"", 160,7,1,null, 0, inv);
		Stack2(ChatColor.BLUE+"", 160,7,1,null, 1, inv);
		Stack2(ChatColor.BLUE+"", 160,7,1,null, 2, inv);
		Stack2(ChatColor.BLUE+"", 160,7,1,null, 9, inv);
		Stack2(ChatColor.WHITE + "" + ChatColor.BOLD + "���� ���", 323,0,1,Arrays.asList(ChatColor.GRAY + "���� ȭ������ ���ư��ϴ�."), 10, inv);
		Stack2(ChatColor.BLUE+"", 160,7,1,null,11, inv);
		Stack2(ChatColor.BLUE+"", 160,7,1,null,18, inv);
		Stack2(ChatColor.BLUE+"", 160,7,1,null,19, inv);
		Stack2(ChatColor.BLUE+"", 160,7,1,null,20, inv);
		
		Stack2(ChatColor.BLUE+""+ChatColor.BOLD + "[���� �÷� ��������]", 160,11,1,null, 3, inv);
		Stack2(ChatColor.BLUE+""+ChatColor.BOLD + "[���� �÷� ��������]", 160,11,1,null, 4, inv);
		Stack2(ChatColor.BLUE+""+ChatColor.BOLD + "[���� �÷� ��������]", 160,11,1,null, 5, inv);
		Stack2(ChatColor.BLUE+""+ChatColor.BOLD + "[���� �÷� ��������]", 160,11,1,null, 12, inv);
		Stack2(ChatColor.BLUE+""+ChatColor.BOLD + "[���� �÷� ��������]", 160,11,1,null,14, inv);
		Stack2(ChatColor.BLUE+""+ChatColor.BOLD + "[���� �÷� ��������]", 160,11,1,null,21, inv);
		Stack2(ChatColor.BLUE+""+ChatColor.BOLD + "[���� �÷� ��������]", 160,11,1,null,22, inv);
		Stack2(ChatColor.BLUE+""+ChatColor.BOLD + "[���� �÷� ��������]", 160,11,1,null,23, inv);
		
		Stack2(ChatColor.BLUE+"", 160,7,1,null, 6, inv);
		Stack2(ChatColor.BLUE+"", 160,7,1,null, 7, inv);
		Stack2(ChatColor.BLUE+"", 160,7,1,null, 8, inv);
		Stack2(ChatColor.BLUE+"", 160,7,1,null, 15, inv);
		Stack2(ChatColor.AQUA+""+ChatColor.BOLD+"[     �� ����     ]", 145,0,1,Arrays.asList("",ChatColor.GREEN+"�� ���� ������ : "+ChatColor.WHITE +NPCscript.getInt("Job.SuccessRate")+ChatColor.GREEN +"%",ChatColor.GREEN + "�� ���� ���� : "+ChatColor.YELLOW +""+NPCscript.getInt("Job.Deal")+ServerOption.Money), 16, inv);
		Stack2(ChatColor.BLUE+"", 160,7,1,null,17, inv);
		Stack2(ChatColor.BLUE+"", 160,7,1,null,24, inv);
		Stack2(ChatColor.BLUE+"", 160,7,1,null,25, inv);
		Stack2(ChatColor.BLUE+"", 160,7,1,Arrays.asList(ChatColor.BLACK+NPCname),26, inv);
		player.openInventory(inv);
	}
	
	public void NPCTalkGUI(Player player, short page, String NPCname,String TalkType)
	{
		Object_UserData u = new Object_UserData();
		YamlController YC = new YamlController(GoldBigDragon_RPG.Main.Main.plugin);
		YamlManager NPCConfig =YC.getNewConfig("NPC/NPCData/"+u.getNPCuuid(player)+".yml");
		
		Inventory inv = Bukkit.createInventory(null, 54, ChatColor.BLACK + "NPC �Ϲ� ��� : " + (page+1));
		switch(TalkType)
		{
		case "NT"://NatureTalk
			inv = Bukkit.createInventory(null, 54, ChatColor.BLACK + "NPC [�������� ��ȭ] ��� : " + (page+1));
			break;
		case "NN"://NearbyNews
			inv = Bukkit.createInventory(null, 54, ChatColor.BLACK + "NPC [��ó�� �ҹ�] ��� : " + (page+1));
			break;
		case "AS"://AboutSkill
			inv = Bukkit.createInventory(null, 54, ChatColor.BLACK + "NPC [��ų�� ���Ͽ�] ��� : " + (page+1));
			break;
		}
		Object[] TalkList = NPCConfig.getConfigurationSection("NatureTalk").getKeys(false).toArray();
		switch(TalkType)
		{
		case "NT"://NatureTalk
			TalkList = NPCConfig.getConfigurationSection("NatureTalk").getKeys(false).toArray();
			break;
		case "NN"://NearbyNews
			TalkList = NPCConfig.getConfigurationSection("NearByNEWS").getKeys(false).toArray();
			break;
		case "AS"://AboutSkill
			TalkList = NPCConfig.getConfigurationSection("AboutSkills").getKeys(false).toArray();
			break;
		}
		int loc=0;
		for(int count = page*45; count < TalkList.length;count++)
		{
			if(count > TalkList.length || loc >= 45) break;
			String Lore = "";
			switch(TalkType)
			{
			case "NT"://NatureTalk
				Lore = "%enter%"+ChatColor.LIGHT_PURPLE+"[�ʿ� ȣ����]%enter%"+ChatColor.WHITE+""+NPCConfig.getInt("NatureTalk."+(count+1)+".love")+" ~ "+NPCConfig.getInt("NatureTalk."+(count+1)+".loveMax")+"%enter%%enter%"+ChatColor.YELLOW+"[��ϵ� ���]%enter%"+ChatColor.WHITE+""+NPCConfig.getString("NatureTalk."+(count+1)+".Script");
				break;
			case "NN"://NearbyNews
				Lore = "%enter%"+ChatColor.LIGHT_PURPLE+"[�ʿ� ȣ����]%enter%"+ChatColor.WHITE+""+NPCConfig.getInt("NearByNEWS."+(count+1)+".love")+" ~ "+NPCConfig.getInt("NearByNEWS."+(count+1)+".loveMax")+"%enter%%enter%"+ChatColor.YELLOW+"[��ϵ� ���]%enter%"+ChatColor.WHITE+""+NPCConfig.getString("NearByNEWS."+(count+1)+".Script");
				break;
			case "AS"://AboutSkill
				Lore = "%enter%"+ChatColor.LIGHT_PURPLE+"[�ʿ� ȣ����]%enter%"+ChatColor.WHITE+""+NPCConfig.getInt("AboutSkills."+(count+1)+".love")+" ~ "+NPCConfig.getInt("AboutSkills."+(count+1)+".loveMax")+"%enter%%enter%"+ChatColor.YELLOW+"[��ų ���� ���]%enter%"+ChatColor.WHITE+""+NPCConfig.getString("AboutSkills."+(count+1)+".Script")+"%enter%%enter%"+ChatColor.YELLOW+"[���� ���� ���]%enter%"+ChatColor.WHITE+""+NPCConfig.getString("AboutSkills."+(count+1)+".AlreadyGetScript")+"%enter%%enter%"+ChatColor.YELLOW+"[��� �� �ִ� ��ų]%enter%"+ChatColor.WHITE+""+NPCConfig.getString("AboutSkills."+(count+1)+".giveSkill")+"%enter%"+ChatColor.RED+"[���� �ý����� ������ �� ��츸 ���� �մϴ�.]";
				break;
			}
			
			String[] scriptA = Lore.split("%enter%");
			for(int counter = 0; counter < scriptA.length; counter++)
				scriptA[counter] =  " "+scriptA[counter];
			
			Stack2(ChatColor.WHITE + "" + ChatColor.BOLD +""+ (count+1), 386,0,1,Arrays.asList(scriptA), loc, inv);
			
			loc=loc+1;
		}
		
		if(TalkList.length-(page*44)>45)
			Stack2(ChatColor.WHITE + "" + ChatColor.BOLD + "���� ������", 323,0,1,Arrays.asList(ChatColor.GRAY + "���� �������� �̵� �մϴ�."), 50, inv);
		Stack2(ChatColor.WHITE + "" + ChatColor.BOLD + "[��� �߰�]", 403,0,1,Arrays.asList(ChatColor.GRAY + "��縦 �߰� ��ŵ�ϴ�."), 49, inv);
		if(page!=0)
			Stack2(ChatColor.WHITE + "" + ChatColor.BOLD + "���� ������", 323,0,1,Arrays.asList(ChatColor.GRAY + "���� �������� �̵� �մϴ�."), 48, inv);

		switch(TalkType)
		{
		case "NT"://NatureTalk
			Stack2(ChatColor.WHITE + "" + ChatColor.BOLD + "[��ó�� �ҹ�]", 340,0,1,Arrays.asList(ChatColor.GRAY + "�ٸ� ������ ��縦 �����մϴ�."), 46, inv);
			Stack2(ChatColor.WHITE + "" + ChatColor.BOLD + "[��ų�� ���Ͽ�]", 340,0,1,Arrays.asList(ChatColor.GRAY + "�ٸ� ������ ��縦 �����մϴ�."), 52, inv);
			break;
		case "NN"://NearbyNews
			Stack2(ChatColor.WHITE + "" + ChatColor.BOLD + "[��ų�� ���Ͽ�]", 340,0,1,Arrays.asList(ChatColor.GRAY + "�ٸ� ������ ��縦 �����մϴ�."), 46, inv);
			Stack2(ChatColor.WHITE + "" + ChatColor.BOLD + "[�������� ��ȭ]", 340,0,1,Arrays.asList(ChatColor.GRAY + "�ٸ� ������ ��縦 �����մϴ�."), 52, inv);
			break;
		case "AS"://AboutSkill
			Stack2(ChatColor.WHITE + "" + ChatColor.BOLD + "[�������� ��ȭ]", 340,0,1,Arrays.asList(ChatColor.GRAY + "�ٸ� ������ ��縦 �����մϴ�."), 46, inv);
			Stack2(ChatColor.WHITE + "" + ChatColor.BOLD + "[��ó�� �ҹ�]", 340,0,1,Arrays.asList(ChatColor.GRAY + "�ٸ� ������ ��縦 �����մϴ�."), 52, inv);
			break;
		}
		Stack2(ChatColor.WHITE + "" + ChatColor.BOLD + "���� ���", 323,0,1,Arrays.asList(ChatColor.GRAY + "���� ȭ������ ���ư��ϴ�.",ChatColor.BLACK+TalkType), 45, inv);
		Stack2(ChatColor.WHITE + "" + ChatColor.BOLD + "�ݱ�", 324,0,1,Arrays.asList(ChatColor.GRAY + "â�� �ݽ��ϴ�.",ChatColor.BLACK+NPCname), 53, inv);
		player.openInventory(inv);
	}
	
	public void TalkSettingGUI(Player player,String NPCname, String TalkType, short TalkNumber)
	{
		Object_UserData u = new Object_UserData();
		YamlController YC = new YamlController(GoldBigDragon_RPG.Main.Main.plugin);
		YamlManager NPCConfig =YC.getNewConfig("NPC/NPCData/"+u.getNPCuuid(player)+".yml");
		
		Inventory inv = Bukkit.createInventory(null, 36, ChatColor.BLACK + "NPC ��� ����");
		String Lore = "";
		switch(TalkType)
		{
		case "NT"://NatureTalk
			Lore = "%enter%"+ChatColor.LIGHT_PURPLE+"[�ʿ� ȣ����]%enter%"+ChatColor.WHITE+""+NPCConfig.getInt("NatureTalk."+TalkNumber+".love")+" ~ "+NPCConfig.getInt("NatureTalk."+TalkNumber+".loveMax")+"%enter%%enter%"+ChatColor.YELLOW+"[��ϵ� ���]%enter%"+ChatColor.WHITE+""+NPCConfig.getString("NatureTalk."+TalkNumber+".Script");
			break;
		case "NN"://NearbyNews
			Lore = "%enter%"+ChatColor.LIGHT_PURPLE+"[�ʿ� ȣ����]%enter%"+ChatColor.WHITE+""+NPCConfig.getInt("NearByNEWS."+TalkNumber+".love")+" ~ "+NPCConfig.getInt("NearByNEWS."+TalkNumber+".loveMax")+"%enter%%enter%"+ChatColor.YELLOW+"[��ϵ� ���]%enter%"+ChatColor.WHITE+""+NPCConfig.getString("NearByNEWS."+TalkNumber+".Script");
			break;
		case "AS"://AboutSkill
			Lore = "%enter%"+ChatColor.LIGHT_PURPLE+"[�ʿ� ȣ����]%enter%"+ChatColor.WHITE+""+NPCConfig.getInt("AboutSkills."+TalkNumber+".love")+" ~ "+NPCConfig.getInt("AboutSkills."+TalkNumber+".loveMax")+"%enter%%enter%"+ChatColor.YELLOW+"[��ų ���� ���]%enter%"+ChatColor.WHITE+""+NPCConfig.getString("AboutSkills."+TalkNumber+".Script")+"%enter%%enter%"+ChatColor.YELLOW+"[���� ���� ���]%enter%"+ChatColor.WHITE+""+NPCConfig.getString("AboutSkills."+TalkNumber+".AlreadyGetScript")+"%enter%%enter%"+ChatColor.YELLOW+"[��� �� �ִ� ��ų]%enter%"+ChatColor.WHITE+""+NPCConfig.getString("AboutSkills."+TalkNumber+".giveSkill")+"%enter%"+ChatColor.RED+"[���� �ý����� ������ �� ��츸 ���� �մϴ�.]";
			break;
		}
			
			String[] scriptA = Lore.split("%enter%");
			for(byte counter = 0; counter < scriptA.length; counter++)
				scriptA[counter] =  " "+scriptA[counter];

			Stack2(ChatColor.AQUA + "[     ���     ]", 160,3,1,null, 0, inv);
			Stack2(ChatColor.AQUA + "[     ���     ]", 160,3,1,null, 1, inv);
			Stack2(ChatColor.AQUA + "[     ���     ]", 160,3,1,null, 2, inv);
			Stack2(ChatColor.AQUA + "[     ���     ]", 160,3,1,null, 9, inv);
			Stack2(ChatColor.WHITE + "" + ChatColor.BOLD +""+ TalkNumber, 386,0,1,Arrays.asList(scriptA), 10, inv);
			Stack2(ChatColor.AQUA + "[     ���     ]", 160,3,1,null, 11, inv);
			Stack2(ChatColor.AQUA + "[     ���     ]", 160,3,1,null, 18, inv);
			Stack2(ChatColor.AQUA + "[     ���     ]", 160,3,1,null, 19, inv);
			Stack2(ChatColor.AQUA + "[     ���     ]", 160,3,1,null, 20, inv);
			

			Stack2(ChatColor.GRAY + "[     �ɼ�     ]", 160,7,1,null, 3, inv);
			Stack2(ChatColor.GRAY + "[     �ɼ�     ]", 160,7,1,null, 4, inv);
			Stack2(ChatColor.GRAY + "[     �ɼ�     ]", 160,7,1,null, 5, inv);
			Stack2(ChatColor.GRAY + "[     �ɼ�     ]", 160,7,1,null, 6, inv);
			Stack2(ChatColor.GRAY + "[     �ɼ�     ]", 160,7,1,null, 7, inv);
			Stack2(ChatColor.GRAY + "[     �ɼ�     ]", 160,7,1,null, 8, inv);
			Stack2(ChatColor.GRAY + "[     �ɼ�     ]", 160,7,1,null, 12, inv);
			Stack2(ChatColor.GRAY + "[     �ɼ�     ]", 160,7,1,null, 17, inv);
			Stack2(ChatColor.GRAY + "[     �ɼ�     ]", 160,7,1,null, 21, inv);
			Stack2(ChatColor.GRAY + "[     �ɼ�     ]", 160,7,1,null, 22, inv);
			Stack2(ChatColor.GRAY + "[     �ɼ�     ]", 160,7,1,null, 23, inv);
			Stack2(ChatColor.GRAY + "[     �ɼ�     ]", 160,7,1,null, 24, inv);
			Stack2(ChatColor.GRAY + "[     �ɼ�     ]", 160,7,1,null, 25, inv);
			Stack2(ChatColor.GRAY + "[     �ɼ�     ]", 160,7,1,null, 26, inv);
			

			switch(TalkType)
			{
			case "NT"://NatureTalk
			case "NN"://NearbyNews
				Stack2(ChatColor.LIGHT_PURPLE + "[     ȣ����     ]", 38,0,1,null, 13, inv);
				Stack2(ChatColor.WHITE + "[     �� ��     ]", 386,0,1,null, 14, inv);
				break;
			case "AS"://AboutSkill
				Stack2(ChatColor.LIGHT_PURPLE + "[     ȣ����     ]", 38,0,1,null, 13, inv);
				Stack2(ChatColor.WHITE + "[     �� �� 1     ]", 386,0,1,null, 14, inv);
				Stack2(ChatColor.DARK_AQUA + "[     ��ų     ]", 403,0,1,null, 15, inv);
				Stack2(ChatColor.WHITE + "[     �� �� 2     ]", 386,0,1,null, 16, inv);
				break;
			}
		Stack2(ChatColor.WHITE + "" + ChatColor.BOLD + "���� ���", 323,0,1,Arrays.asList(ChatColor.GRAY + "���� ȭ������ ���ư��ϴ�.",ChatColor.BLACK+TalkType), 27, inv);
		Stack2(ChatColor.WHITE + "" + ChatColor.BOLD + "�ݱ�", 324,0,1,Arrays.asList(ChatColor.GRAY + "â�� �ݽ��ϴ�.",ChatColor.BLACK+NPCname), 35, inv);
		player.openInventory(inv);
	}
	
	public void AddAbleSkillsGUI(Player player, short page, String NPCname,int TalkNumber)
	{
		YamlController YC = new YamlController(GoldBigDragon_RPG.Main.Main.plugin);
		YamlManager SkillList =YC.getNewConfig("Skill/JobList.yml");
		YamlManager RealSkills =YC.getNewConfig("Skill/SkillList.yml");
		
		Inventory inv = Bukkit.createInventory(null, 54, ChatColor.BLACK + "NPC ����ĥ ��ų ���� : " + (page+1));
		Object[] Skills = SkillList.getConfigurationSection("Mabinogi.Added").getKeys(false).toArray();

		byte loc=0;
		for(int count = page*45; count < Skills.length;count++)
		{
			if(count > Skills.length || loc >= 45) break;
			if(RealSkills.contains(Skills[count].toString())==true)
			{
				Stack2(ChatColor.WHITE + "" + ChatColor.BOLD +""+ Skills[count], RealSkills.getInt(Skills[count].toString()+".ID"),RealSkills.getInt(Skills[count].toString()+".DATA"),RealSkills.getInt(Skills[count].toString()+".Amount"),Arrays.asList("",ChatColor.GREEN+ "[�ִ� ��ũ]",ChatColor.WHITE+""+RealSkills.getConfigurationSection(Skills[count].toString()+".SkillRank").getKeys(false).size()), loc, inv);
				loc++;
			}
		}
		
		if(Skills.length-(page*44)>45)
			Stack2(ChatColor.WHITE + "" + ChatColor.BOLD + "���� ������", 323,0,1,Arrays.asList(ChatColor.GRAY + "���� �������� �̵� �մϴ�."), 50, inv);
		if(page!=0)
			Stack2(ChatColor.WHITE + "" + ChatColor.BOLD + "���� ������", 323,0,1,Arrays.asList(ChatColor.GRAY + "���� �������� �̵� �մϴ�."), 48, inv);

		Stack2(ChatColor.WHITE + "" + ChatColor.BOLD + "���� ���", 323,0,1,Arrays.asList(ChatColor.GRAY + "���� ȭ������ ���ư��ϴ�.",ChatColor.BLACK+""+TalkNumber), 45, inv);
		Stack2(ChatColor.WHITE + "" + ChatColor.BOLD + "�ݱ�", 324,0,1,Arrays.asList(ChatColor.GRAY + "â�� �ݽ��ϴ�.",ChatColor.BLACK+NPCname), 53, inv);
		player.openInventory(inv);
	}

	public void ItemBuy(Player player, ItemStack item, int value, String NPCname, boolean isItemBuy, int count)
	{
		Inventory inv = null;
		String itemName = null;
		if(item.hasItemMeta()&&item.getItemMeta().hasDisplayName())
			itemName = item.getItemMeta().getDisplayName();
		else
			itemName = new GoldBigDragon_RPG.Event.Interact().SetItemDefaultName((short)item.getTypeId(), item.getData().getData());
		if(isItemBuy)
		{
			inv = Bukkit.createInventory(null, 54, ChatColor.BLACK + "[NPC]"+ChatColor.BLUE+ChatColor.BOLD+" ��ǰ ����");
			Stack2(ChatColor.AQUA +"     [����]     ", 160,11,1,Arrays.asList(ChatColor.BLACK+""+value), 0, inv);
			Stack2(ChatColor.AQUA +"     [����]     ", 160,11,1,Arrays.asList(ChatColor.BLACK+""+count), 1, inv);
			Stack2(ChatColor.AQUA +"     [����]     ", 160,11,1,null, 2, inv);
			Stack2(ChatColor.AQUA +"     [����]     ", 160,11,1,null, 3, inv);
			Stack2(ChatColor.AQUA +"     [����]     ", 160,11,1,null, 4, inv);
			Stack2(ChatColor.AQUA +"     [����]     ", 160,11,1,null, 5, inv);
			Stack2(ChatColor.AQUA +"     [����]     ", 160,11,1,null, 6, inv);
			Stack2(ChatColor.AQUA +"     [����]     ", 160,11,1,null, 7, inv);
			Stack2(ChatColor.AQUA +"     [����]     ", 160,11,1,null, 8, inv);
			Stack2(ChatColor.AQUA +"     [����]     ", 160,11,1,null, 9, inv);
			Stack2(ChatColor.AQUA +"     [����]     ", 160,11,1,null, 18, inv);
			Stack2(ChatColor.AQUA +"     [����]     ", 160,11,1,null, 17, inv);
			Stack2(ChatColor.AQUA +"     [����]     ", 160,11,1,null, 26, inv);
			Stack2(ChatColor.AQUA +"     [����]     ", 160,11,1,null, 27, inv);
			Stack2(ChatColor.AQUA +"     [����]     ", 160,11,1,null, 36, inv);
			Stack2(ChatColor.AQUA +"     [����]     ", 160,11,1,null, 35, inv);
			Stack2(ChatColor.AQUA +"     [����]     ", 160,11,1,null, 36, inv);
			Stack2(ChatColor.AQUA +"     [����]     ", 160,11,1,null, 37, inv);
			Stack2(ChatColor.AQUA +"     [����]     ", 160,11,1,null, 38, inv);
			Stack2(ChatColor.AQUA +"     [����]     ", 160,11,1,null, 39, inv);
			Stack2(ChatColor.AQUA +"     [����]     ", 160,11,1,null, 40, inv);
			Stack2(ChatColor.AQUA +"     [����]     ", 160,11,1,null, 41, inv);
			Stack2(ChatColor.AQUA +"     [����]     ", 160,11,1,null, 42, inv);
			Stack2(ChatColor.AQUA +"     [����]     ", 160,11,1,null, 43, inv);
			Stack2(ChatColor.AQUA +"     [����]     ", 160,11,1,null, 44, inv);
			Stack2(ChatColor.AQUA + "" + ChatColor.BOLD + "[��ǰ ����]", 54,0,1,Arrays.asList(itemName+ChatColor.WHITE+ChatColor.BOLD+"��"+item.getAmount()+ChatColor.WHITE+" ��������",ChatColor.WHITE+"�� "+ ChatColor.YELLOW+ChatColor.BOLD+count +ChatColor.WHITE+ " �� �����մϴ�."
					,"",ChatColor.WHITE+"���� : "+ChatColor.YELLOW+ value*count + " "+ChatColor.GOLD + ServerOption.Money, ChatColor.WHITE+"������ : "+ChatColor.WHITE+ChatColor.BOLD+ServerOption.PlayerList.get(player.getUniqueId().toString()).getStat_Money()+" "+ServerOption.Money), 49, inv);
		}
		else
		{
			inv = Bukkit.createInventory(null, 54, ChatColor.BLACK + "[NPC]"+ChatColor.RED+ChatColor.BOLD+" ��ǰ �Ǹ�");
			Stack2(ChatColor.RED +"     [�Ǹ�]     ", 160,14,1,Arrays.asList(ChatColor.BLACK+""+value), 0, inv);
			Stack2(ChatColor.RED +"     [�Ǹ�]     ", 160,14,1,Arrays.asList(ChatColor.BLACK+""+count), 1, inv);
			Stack2(ChatColor.RED +"     [�Ǹ�]     ", 160,14,1,null, 2, inv);
			Stack2(ChatColor.RED +"     [�Ǹ�]     ", 160,14,1,null, 3, inv);
			Stack2(ChatColor.RED +"     [�Ǹ�]     ", 160,14,1,null, 4, inv);
			Stack2(ChatColor.RED +"     [�Ǹ�]     ", 160,14,1,null, 5, inv);
			Stack2(ChatColor.RED +"     [�Ǹ�]     ", 160,14,1,null, 6, inv);
			Stack2(ChatColor.RED +"     [�Ǹ�]     ", 160,14,1,null, 7, inv);
			Stack2(ChatColor.RED +"     [�Ǹ�]     ", 160,14,1,null, 8, inv);
			Stack2(ChatColor.RED +"     [�Ǹ�]     ", 160,14,1,null, 9, inv);
			Stack2(ChatColor.RED +"     [�Ǹ�]     ", 160,14,1,null, 18, inv);
			Stack2(ChatColor.RED +"     [�Ǹ�]     ", 160,14,1,null, 17, inv);
			Stack2(ChatColor.RED +"     [�Ǹ�]     ", 160,14,1,null, 26, inv);
			Stack2(ChatColor.RED +"     [�Ǹ�]     ", 160,14,1,null, 27, inv);
			Stack2(ChatColor.RED +"     [�Ǹ�]     ", 160,14,1,null, 36, inv);
			Stack2(ChatColor.RED +"     [�Ǹ�]     ", 160,14,1,null, 35, inv);
			Stack2(ChatColor.RED +"     [�Ǹ�]     ", 160,14,1,null, 36, inv);
			Stack2(ChatColor.RED +"     [�Ǹ�]     ", 160,14,1,null, 37, inv);
			Stack2(ChatColor.RED +"     [�Ǹ�]     ", 160,14,1,null, 38, inv);
			Stack2(ChatColor.RED +"     [�Ǹ�]     ", 160,14,1,null, 39, inv);
			Stack2(ChatColor.RED +"     [�Ǹ�]     ", 160,14,1,null, 40, inv);
			Stack2(ChatColor.RED +"     [�Ǹ�]     ", 160,14,1,null, 41, inv);
			Stack2(ChatColor.RED +"     [�Ǹ�]     ", 160,14,1,null, 42, inv);
			Stack2(ChatColor.RED +"     [�Ǹ�]     ", 160,14,1,null, 43, inv);
			Stack2(ChatColor.RED +"     [�Ǹ�]     ", 160,14,1,null, 44, inv);
			Stack2(ChatColor.RED + "" + ChatColor.BOLD + "[��ǰ �Ǹ�]", 54,0,1,Arrays.asList(itemName+ChatColor.WHITE+ChatColor.BOLD+"��"+item.getAmount()+ChatColor.WHITE+" ��������",ChatColor.WHITE+"�� "+ ChatColor.YELLOW+ChatColor.BOLD+count +ChatColor.WHITE+ " �� �Ǹ��մϴ�."
					,"",ChatColor.WHITE+"���� : "+ChatColor.YELLOW+ value*count + " "+ChatColor.GOLD + ServerOption.Money, ChatColor.WHITE+"������ : "+ChatColor.WHITE+ChatColor.BOLD+ServerOption.PlayerList.get(player.getUniqueId().toString()).getStat_Money()+" "+ServerOption.Money), 49, inv);
		}

		Stack2(ChatColor.RED +""+ ChatColor.BOLD+"     [���� - 64]     ", 374,0,64,Arrays.asList("",ChatColor.WHITE+"���� �� "+ ChatColor.YELLOW+ChatColor.BOLD+count +ChatColor.WHITE+ " ��"), 19, inv);
		Stack2(ChatColor.RED +""+ ChatColor.BOLD+"     [���� - 10]     ", 374,0,10,Arrays.asList("",ChatColor.WHITE+"���� �� "+ ChatColor.YELLOW+ChatColor.BOLD+count +ChatColor.WHITE+ " ��"), 20, inv);
		Stack2(ChatColor.RED +""+ ChatColor.BOLD+"     [���� - 1]     ", 374,0,1,Arrays.asList("",ChatColor.WHITE+"���� �� "+ ChatColor.YELLOW+ChatColor.BOLD+count +ChatColor.WHITE+ " ��"), 21, inv);

		Stack2(ChatColor.RED +""+ ChatColor.BOLD+"     [�ּ���]     ", 325,0,1,Arrays.asList("",ChatColor.WHITE+"���� �� "+ ChatColor.YELLOW+ChatColor.BOLD+count +ChatColor.WHITE+ " ��"), 13, inv);
		ItemStackStack(item, 22, inv);
		Stack2(ChatColor.AQUA +""+ ChatColor.BOLD+"     [�ִ���]     ", 326,0,1,Arrays.asList("",ChatColor.WHITE+"���� �� "+ ChatColor.YELLOW+ChatColor.BOLD+count +ChatColor.WHITE+ " ��"), 31, inv);
		Stack2(ChatColor.AQUA +""+ ChatColor.BOLD+"     [���� + 1]     ", 373,0,1,Arrays.asList("",ChatColor.WHITE+"���� �� "+ ChatColor.YELLOW+ChatColor.BOLD+count +ChatColor.WHITE+ " ��"), 23, inv);
		Stack2(ChatColor.AQUA +""+ ChatColor.BOLD+"     [���� + 10]     ", 373,0,10,Arrays.asList("",ChatColor.WHITE+"���� �� "+ ChatColor.YELLOW+ChatColor.BOLD+count +ChatColor.WHITE+ " ��"), 24, inv);
		Stack2(ChatColor.AQUA +""+ ChatColor.BOLD+"     [���� + 64]     ", 373,0,64,Arrays.asList("",ChatColor.WHITE+"���� �� "+ ChatColor.YELLOW+ChatColor.BOLD+count +ChatColor.WHITE+ " ��"), 25, inv);
		
		Stack2(ChatColor.WHITE + "" + ChatColor.BOLD + "���� ���", 323,0,1,Arrays.asList(ChatColor.GRAY + "���� ȭ������ ���ư��ϴ�."), 45, inv);
		Stack2(ChatColor.WHITE + "" + ChatColor.BOLD + "�ݱ�", 324,0,1,Arrays.asList(ChatColor.GRAY + "â�� �ݽ��ϴ�.",ChatColor.BLACK+NPCname), 53, inv);
		player.openInventory(inv);
	}
	
	public void ItemFix(Player player, String NPCname, int successRate, int value)
	{
		Inventory inv = Bukkit.createInventory(null, 9, ChatColor.BLACK + "[NPC]"+ChatColor.BLACK+ChatColor.BOLD+" ������ ��� ���� �ϼ���.");
		Stack2(ChatColor.WHITE + "" + ChatColor.BOLD + "���� ���"+ChatColor.BLACK+ChatColor.YELLOW+ChatColor.RED, 323,0,1,Arrays.asList(ChatColor.GRAY + "���� ȭ������ ���ư��ϴ�.",ChatColor.BLACK+""+successRate,ChatColor.BLACK+""+value), 0, inv);
		Stack2(ChatColor.WHITE + "" + ChatColor.BOLD + "�ݱ�"+ChatColor.BLACK+ChatColor.YELLOW+ChatColor.RED, 324,0,1,Arrays.asList(ChatColor.GRAY + "â�� �ݽ��ϴ�.",ChatColor.BLACK+NPCname), 8, inv);

		Stack2(""+ChatColor.BLACK+ChatColor.YELLOW+ChatColor.RED, 265,0,1,null, 1, inv);
		Stack2(""+ChatColor.BLACK+ChatColor.YELLOW+ChatColor.RED, 265,0,1,null, 2, inv);
		Stack2(""+ChatColor.BLACK+ChatColor.YELLOW+ChatColor.RED, 265,0,1,null, 3, inv);
		Stack2(ChatColor.GOLD +""+ChatColor.BOLD + "[��� ����]"+ChatColor.BLACK+ChatColor.YELLOW+ChatColor.RED, 145,0,1,Arrays.asList(ChatColor.GRAY+"�κ��丮 �� �������� ���� ���",ChatColor.GRAY+"������ Ȯ���� ���ݿ� ���� �� �ݴϴ�.","",ChatColor.DARK_AQUA +"���� ������ : "+ChatColor.WHITE +successRate+ChatColor.DARK_AQUA +" %",ChatColor.GREEN + "������ 10 �� ���� : "+ChatColor.YELLOW +""+value+" "+ChatColor.GREEN+ServerOption.Money,"",ChatColor.RED+"[�Ϲ� ������ ���� ���н�]",ChatColor.RED+" - ���� ������ ��� ���� ��� �Ҹ�",ChatColor.RED+"[Ŀ���� ������ ���� ���н�]",ChatColor.RED+" - �ִ� ������ ����"), 4, inv);
		Stack2(""+ChatColor.BLACK+ChatColor.YELLOW+ChatColor.RED, 265,0,1,null, 5, inv);
		Stack2(""+ChatColor.BLACK+ChatColor.YELLOW+ChatColor.RED, 265,0,1,null, 6, inv);
		Stack2(""+ChatColor.BLACK+ChatColor.YELLOW+ChatColor.RED, 265,0,1,null, 7, inv);
		player.openInventory(inv);
	}

	public void PresentGiveGUI(Player player, String NPCname, boolean isSettingMode, int number)
	{
		Inventory inv = Bukkit.createInventory(null, 9, ChatColor.BLACK + "[NPC] ���� �������� �÷� �ּ���");
		if(isSettingMode)
		{
			Stack2(ChatColor.WHITE + "" + ChatColor.BOLD + "���� ���"+ChatColor.BLACK+ChatColor.YELLOW+ChatColor.RED, 389,0,1,Arrays.asList(ChatColor.GRAY + "�� ���������� �����մϴ�.",ChatColor.BLACK+""+isSettingMode), 0, inv);

			YamlController YC = new YamlController(GoldBigDragon_RPG.Main.Main.plugin);
			YamlManager NPCConfig =YC.getNewConfig("NPC/NPCData/"+new Object_UserData().getNPCuuid(player)+".yml");
			ItemStack item = NPCConfig.getItemStack("Present."+number+".item");
			if(item != null)
				ItemStackStack(item, 4, inv);
			Stack2(ChatColor.WHITE + "" + ChatColor.BOLD + "�ݱ�"+ChatColor.BLACK+ChatColor.YELLOW+ChatColor.RED, 324,0,1,Arrays.asList(ChatColor.GRAY + "â�� �ݽ��ϴ�.",ChatColor.BLACK+NPCname), 8, inv);
		}
		else
		{
			Stack2(ChatColor.WHITE + "" + ChatColor.BOLD + "���� ���"+ChatColor.BLACK+ChatColor.YELLOW+ChatColor.RED, 323,0,1,Arrays.asList(ChatColor.GRAY + "���� ȭ������ ���ư��ϴ�.",ChatColor.BLACK+""+isSettingMode), 0, inv);
			Stack2(ChatColor.WHITE + "" + ChatColor.BOLD + "���� �ֱ�"+ChatColor.BLACK+ChatColor.YELLOW+ChatColor.RED, 54,0,1,Arrays.asList(ChatColor.GRAY + "�÷��� �������� �����մϴ�.",ChatColor.BLACK+NPCname), 8, inv);
		}
		Stack2(""+ChatColor.BLACK+ChatColor.YELLOW+ChatColor.RED, 160,5,1,Arrays.asList(ChatColor.BLACK + ""+number), 1, inv);
		Stack2(""+ChatColor.BLACK+ChatColor.YELLOW+ChatColor.RED, 160,5,1,null, 2, inv);
		Stack2(""+ChatColor.BLACK+ChatColor.YELLOW+ChatColor.RED, 160,5,1,null, 3, inv);
		Stack2(""+ChatColor.BLACK+ChatColor.YELLOW+ChatColor.RED, 160,5,1,null, 5, inv);
		Stack2(""+ChatColor.BLACK+ChatColor.YELLOW+ChatColor.RED, 160,5,1,null, 6, inv);
		Stack2(""+ChatColor.BLACK+ChatColor.YELLOW+ChatColor.RED, 160,5,1,null, 7, inv);
		player.openInventory(inv);
	}

	public void PresentSettingGUI(Player player, String NPCname)
	{
		Inventory inv = Bukkit.createInventory(null, 9, ChatColor.BLACK + "[NPC] ���� ���� ������ ���");
		Stack2(ChatColor.WHITE + "" + ChatColor.BOLD + "���� ���"+ChatColor.BLACK+ChatColor.YELLOW+ChatColor.RED, 323,0,1,Arrays.asList(ChatColor.GRAY + "���� ȭ������ ���ư��ϴ�."), 0, inv);
		Stack2(ChatColor.WHITE + "" + ChatColor.BOLD + "�ݱ�"+ChatColor.BLACK+ChatColor.YELLOW+ChatColor.RED, 324,0,1,Arrays.asList(ChatColor.GRAY + "â�� �ݽ��ϴ�.",ChatColor.BLACK+NPCname), 8, inv);

		YamlController YC = new YamlController(GoldBigDragon_RPG.Main.Main.plugin);
		YamlManager NPCConfig =YC.getNewConfig("NPC/NPCData/"+new Object_UserData().getNPCuuid(player)+".yml");
		
		Stack2(ChatColor.WHITE+"��Ÿ ������", 138,0,1,Arrays.asList(ChatColor.GRAY + "������ ������ ���� �ٸ�",ChatColor.GRAY+"�������� �־��� ����",ChatColor.GRAY+"ȣ���� ��·��� �����մϴ�.","",ChatColor.GREEN+"ȣ���� : " + NPCConfig.getInt("Present.1.love")), 1, inv);

		for(int count = 2; count < 8; count++)
		{
			if(NPCConfig.getItemStack("Present."+count+".item") == null)
				Stack2(ChatColor.RED+"[�������� ���� ����]", 166,0,1,Arrays.asList(ChatColor.YELLOW +""+ChatColor.BOLD+ "[Ŭ���� ���� ����]"), count, inv);
			else
			{
				ItemStack item = NPCConfig.getItemStack("Present."+count+".item");
				ItemMeta im = item.getItemMeta();
				if(im.hasLore() == false)
					im.setLore(Arrays.asList("",ChatColor.GREEN+"ȣ���� : " + NPCConfig.getInt("Present."+count+".love")));
				else
				{
					List<String> lore = item.getItemMeta().getLore();
					lore.add(lore.size(), "");
					lore.add(lore.size(),ChatColor.GREEN+"ȣ���� : " + NPCConfig.getInt("Present."+count+".love"));
					im.setLore(lore);
				}
				item.setItemMeta(im);
				ItemStackStack(item, count, inv);
			}
		}
		player.openInventory(inv);
	}
	
	
	
	
	
	
	public void NPCQuestclickMain(InventoryClickEvent event)
	{
		event.setCancelled(true);
		Player player = (Player) event.getWhoClicked();
		if(event.getClickedInventory().getTitle().equalsIgnoreCase("container.inventory") == true)
			return;
			if (event.getCurrentItem() == null
					||event.getCurrentItem().getType() == Material.AIR
					||!event.getCurrentItem().hasItemMeta())
			{
					return;
			}
			else
			{
				if(event.getInventory().getTitle().contains("��� ���� ����Ʈ ���") == true)
				{
					switch(event.getSlot())
					{
					case 45://���� �������
						//MainGUI(player,NPCuuid,player.isOp());
						break;
					case 48://���� ������
						AllOfQuestListGUI(player,(short) (Integer.parseInt(event.getInventory().getTitle().split(" : ")[1])-2));
						break;
					case 50://���� ������
						AllOfQuestListGUI(player,(short) Integer.parseInt(event.getInventory().getTitle().split(" : ")[1]));
						break;
					case 53://������
						s.SP(player, Sound.PISTON_RETRACT, 0.8F, 1.8F);
						player.closeInventory();
						return;
					default:
						{
							YamlController YC = new YamlController(GoldBigDragon_RPG.Main.Main.plugin);
							Object_UserData u = new Object_UserData();
							YamlManager NPCscript = YC.getNewConfig("NPC/NPCData/"+ u.getNPCuuid(player) +".yml");
							
							String QuestName = ChatColor.stripColor(event.getCurrentItem().getItemMeta().getDisplayName());
							Set<String> NPChasQuest = NPCscript.getConfigurationSection("Quest").getKeys(false);
							Object[] a = NPChasQuest.toArray();
							
							boolean isExit=false;
							for(short count = 0; count < a.length;count++)
							{
								if(NPCscript.getString("Quest."+a[count]).equalsIgnoreCase(QuestName) == true)
								{
									isExit = true;
									NPCscript.removeKey("Quest."+count);
								}
								if(isExit == true)
								{
									if(count < a.length-1)
									NPCscript.set("Quest."+count, NPCscript.getString("Quest."+(count+1)));
								}
							}
							if(isExit == true)
							{
								NPCscript.removeKey("Quest."+ (a.length-1));
								s.SP(player, Sound.LAVA_POP, 1.0F, 0.8F);
								player.sendMessage(ChatColor.RED + "[SYSTEM] : ����Ʈ ���� �Ϸ�!");
							}
							else
							{
								s.SP(player, Sound.ITEM_PICKUP, 1.0F, 0.8F);
								player.sendMessage(ChatColor.GREEN + "[SYSTEM] : ����Ʈ ��� �Ϸ�!");
								NPCscript.set("Quest."+a.length, QuestName);
							}
							NPCscript.saveConfig();
							AllOfQuestListGUI(player,(short) (Integer.parseInt(event.getInventory().getTitle().split(" : ")[1])-1));
							break;
						}
					}
					return;
				}
			}
	}
	
	public void QuestAcceptclickMain(InventoryClickEvent event)
	{
		event.setCancelled(true);
		Player player = (Player) event.getWhoClicked();
		if(event.getClickedInventory().getTitle().equalsIgnoreCase("container.inventory") == true)
			return;
			if (event.getCurrentItem() == null
					||event.getCurrentItem().getType() == Material.AIR
					||!event.getCurrentItem().hasItemMeta())
			{
					return;
			}
			else
			{
				if(event.getInventory().getTitle().contains("���� ������ ����Ʈ ���") == true)
				{
					switch(event.getSlot())
					{
					case 45://���� �������
						//MainGUI(player,NPCuuid,player.isOp());
						break;
					case 48://���� ������
						NPCQuest(player,(short) (Integer.parseInt(event.getInventory().getTitle().split(" : ")[1])-2));
						break;
					case 50://���� ������
						NPCQuest(player,(short) Integer.parseInt(event.getInventory().getTitle().split(" : ")[1]));
						break;
					case 53://������
						s.SP(player, Sound.PISTON_RETRACT, 0.8F, 1.8F);
						player.closeInventory();
						return;
					default:
						String QuestName = ChatColor.stripColor(event.getCurrentItem().getItemMeta().getDisplayName());
						YamlController YC = new YamlController(GoldBigDragon_RPG.Main.Main.plugin);
						YamlManager PlayerQuest = YC.getNewConfig("Quest/PlayerData/"+ player.getUniqueId().toString() +".yml");
						YamlManager QuestList  = YC.getNewConfig("Quest/QuestList.yml");
						YamlManager Config = YC.getNewConfig("config.yml");
						for(byte counter = 0; counter < event.getCurrentItem().getItemMeta().getLore().size();counter ++)
						{
							if(event.getCurrentItem().getItemMeta().getLore().get(counter).contains("���") == true)
							{
								s.SP(player, Sound.ORB_PICKUP, 1.0F, 0.8F);
								player.sendMessage(ChatColor.RED + "[����Ʈ] : ������ ���̻� ����Ʈ�� ������ �� �����ϴ�!");
								return;
							}
						}
						if(QuestList.getInt(QuestName+".Server.Limit") == -1)
						{
							s.SP(player, Sound.ORB_PICKUP, 1.0F, 0.8F);
							player.sendMessage(ChatColor.RED + "[����Ʈ] : ���̻� �� ����Ʈ�� ���� �� �� �����ϴ�!");
							return;
						}
						else
						{
							int NeedLevel = QuestList.getInt(QuestName+".Need.LV");
							int NeedLove = QuestList.getInt(QuestName+".Need.Love");
							int NeedSTR = QuestList.getInt(QuestName+".Need.STR");
							int NeedDEX = QuestList.getInt(QuestName+".Need.DEX");
							int NeedINT = QuestList.getInt(QuestName+".Need.INT");
							int NeedWILL = QuestList.getInt(QuestName+".Need.WILL");
							int NeedLUK = QuestList.getInt(QuestName+".Need.LUK");
							String PrevQuest = QuestList.getString(QuestName+".Need.PrevQuest");

							
							int PLV = 0;
							if(Config.getBoolean("Server.Like_The_Mabinogi_Online_Stat_System") == false)
								PLV = GoldBigDragon_RPG.Main.ServerOption.PlayerList.get(player.getUniqueId().toString()).getStat_Level();
							else
								PLV = GoldBigDragon_RPG.Main.ServerOption.PlayerList.get(player.getUniqueId().toString()).getStat_RealLevel();
							if(NeedLevel <= PLV)
							{
								if(NeedSTR <= GoldBigDragon_RPG.Main.ServerOption.PlayerList.get(player.getUniqueId().toString()).getStat_STR()&&
								NeedDEX <= GoldBigDragon_RPG.Main.ServerOption.PlayerList.get(player.getUniqueId().toString()).getStat_DEX()&&
								NeedINT <= GoldBigDragon_RPG.Main.ServerOption.PlayerList.get(player.getUniqueId().toString()).getStat_INT()&&
								NeedWILL <= GoldBigDragon_RPG.Main.ServerOption.PlayerList.get(player.getUniqueId().toString()).getStat_WILL()&&
								NeedLUK <= GoldBigDragon_RPG.Main.ServerOption.PlayerList.get(player.getUniqueId().toString()).getStat_LUK())
								{
									Object_UserData u = new Object_UserData();
									YamlManager PlayerNPC = YC.getNewConfig("NPC/PlayerData/" + player.getUniqueId()+".yml");
									if(NeedLove <= PlayerNPC.getInt(u.getNPCuuid(player)+".love"))
									{
										if(PrevQuest.equalsIgnoreCase("null")||PlayerQuest.contains("Ended."+PrevQuest) == true)
										{
											if(QuestList.getInt(QuestName+".Server.Limit") != 0)
											{
												if(QuestList.getInt(QuestName+".Server.Limit") == 1|| QuestList.getInt(QuestName+".Server.Limit") < -1)
												{
													QuestList.set(QuestName+".Server.Limit", -1);
												}
												else
												{
													QuestList.set(QuestName+".Server.Limit", QuestList.getInt(QuestName+".Server.Limit")-1);
												}
												QuestList.saveConfig();
											}
											player.closeInventory();
											s.SP(player, Sound.ORB_PICKUP, 1.0F, 0.8F);
											String message = Config.getString("Quest.AcceptMessage").replace("%QuestName%", QuestName);
											player.sendMessage(message);

											PlayerQuest.set("Started."+ChatColor.stripColor(event.getCurrentItem().getItemMeta().getDisplayName())+".Flow", 0);
											PlayerQuest.set("Started."+ChatColor.stripColor(event.getCurrentItem().getItemMeta().getDisplayName())+".Type", QuestList.getString(ChatColor.stripColor(event.getCurrentItem().getItemMeta().getDisplayName())+".FlowChart."+0+".Type"));
											
											PlayerQuest.saveConfig();
											
											GoldBigDragon_RPG.Quest.QuestGUI QGUI = new GoldBigDragon_RPG.Quest.QuestGUI();
											QGUI.QuestTypeRouter(player, ChatColor.stripColor(event.getCurrentItem().getItemMeta().getDisplayName()));
											
										}
										else
										{
											s.SP(player, Sound.ORB_PICKUP, 1.0F, 0.8F);
											player.sendMessage(ChatColor.RED+"[����Ʈ] : ���� ����Ʈ�� �������� �ʾ� ����Ʈ�� ������ �� �����ϴ�!");
											return;
										}
									}
									else
									{
										s.SP(player, Sound.ORB_PICKUP, 1.0F, 0.8F);
										player.sendMessage(ChatColor.RED+"[����Ʈ] : ȣ������ �����Ͽ� ����Ʈ�� ������ �� �����ϴ�!");
										return;
									}
								}
								else
								{
									s.SP(player, Sound.ORB_PICKUP, 1.0F, 0.8F);
									player.sendMessage(ChatColor.RED+"[����Ʈ] : ������ �����Ͽ� ����Ʈ�� ������ �� �����ϴ�!");
									return;
								}
							}
							else
							{
								s.SP(player, Sound.ORB_PICKUP, 1.0F, 0.8F);
								player.sendMessage(ChatColor.RED+"[����Ʈ] : ���� ������ ������ �ƴմϴ�!");
								return;
							}
						}
						break;
					}//switch ����
					
					return;
				}
			}
	}
	
	public void NPCclickMain(InventoryClickEvent event, String NPCname)
	{
		event.setCancelled(true);
		Player player = (Player) event.getWhoClicked();
		if(event.getClickedInventory().getTitle().equalsIgnoreCase("container.inventory") == true)
			return;
			if (event.getCurrentItem() == null ||event.getCurrentItem().getType() == Material.AIR ||!event.getCurrentItem().hasItemMeta())
			{
				if(event.getInventory().getName().compareTo(ChatColor.BLACK + "[NPC] ���� �������� �÷� �ּ���")==0||
						event.getInventory().getName().compareTo(ChatColor.BLACK + "[NPC] ���� ���� ������ ���")==0)
				{
					event.setCancelled(false);
					PresentGuiClick(event);
				}
				return;
			}
			else
			{
				if(event.getInventory().getName().compareTo(ChatColor.BLACK + "[NPC]"+ChatColor.BLUE+ChatColor.BOLD+" ��ǰ ����")==0
				||event.getInventory().getName().compareTo(ChatColor.BLACK + "[NPC]"+ChatColor.RED+ChatColor.BOLD+" ��ǰ �Ǹ�")==0)
				{
					ItemBuyGuiClick(event);
					return;
				}
				else if(event.getInventory().getSize() == 9)//NPC ��ȭâ GUI
				{
					event.setCancelled(true);
					if(event.getInventory().getName().compareTo(ChatColor.BLACK + "[NPC]"+ChatColor.BLACK+ChatColor.BOLD+" ������ ��� ���� �ϼ���.")==0)
					{
						ItemFixGuiClick(event);
						return;
					}
					else if(event.getInventory().getName().compareTo(ChatColor.BLACK + "[NPC] ���� �������� �÷� �ּ���")==0||
							event.getInventory().getName().compareTo(ChatColor.BLACK + "[NPC] ���� ���� ������ ���")==0)
					{
						event.setCancelled(false);
						PresentGuiClick(event);
						return;
					}
					int slot = event.getSlot();
					if(slot > 0 && slot < 8)
					{
						s.SP(player, Sound.ITEM_PICKUP, 0.8F, 1.0F);
						TalkGUI(player,NPCname,NPC.getScript(player,(char)-1),(char)slot);
					}
					else
					{
						s.SP(player, Sound.PISTON_RETRACT, 0.8F, 1.8F);
						if(slot == 0)
							MainGUI(player,NPCname,player.isOp());
						else
							player.closeInventory();
						return;
					}
				}
				if(!(event.getInventory().getSize() == 54))
				{
					String Case = (ChatColor.stripColor(event.getCurrentItem().getItemMeta().getDisplayName()));
					if(Case.compareTo("GUI �� Ȱ��ȭ")==0)
					{
						s.SP(player, Sound.VILLAGER_HIT, 0.8F, 1.0F);
						YamlController YC = new YamlController(GoldBigDragon_RPG.Main.Main.plugin);
						YamlManager DNPC = YC.getNewConfig("NPC/DistrictNPC.yml");

						Object_UserData u = new Object_UserData();
						DNPC.set(u.getNPCuuid(player).toString(), true);
						DNPC.saveConfig();
						player.closeInventory();
						player.sendMessage(ChatColor.YELLOW+"[NPC] : �ش� NPC�� GUIȭ���� ������� �ʰ� �Ǿ����ϴ�!");
						player.sendMessage(ChatColor.GOLD+"/gui���"+ChatColor.WHITE+" ��ɾ� �Է� ��, NPC Ŭ����, �ٽ� Ȱ��ȭ �˴ϴ�.");
						return;
					}
					else if(Case.compareTo("��������")==0 || Case.compareTo("�� ������")==0 || Case.compareTo("�ּ���")==0 ||
							Case.compareTo("����")==0 || Case.compareTo("���� ����")==0 || Case.compareTo("���� �̵�����")==0 ||
							Case.compareTo("���� ����")==0)
					{
						if(event.getClick().isLeftClick() == true)
						{
							YamlController YC = new YamlController(GoldBigDragon_RPG.Main.Main.plugin);
							Object_UserData u = new Object_UserData();
						  	if(YC.isExit("NPC/NPCData/"+ u.getNPCuuid(player) +".yml") == false)
						  	{
						  		GoldBigDragon_RPG.Config.NPCconfig NPCC = new GoldBigDragon_RPG.Config.NPCconfig();
						  		NPCC.NPCNPCconfig(u.getNPCuuid(player));
						  	}
							YamlManager NPCscript = YC.getNewConfig("NPC/NPCData/"+ u.getNPCuuid(player) +".yml");
							GoldBigDragon_RPG.Util.Number n = new GoldBigDragon_RPG.Util.Number();

							s.SP(player, Sound.ITEM_PICKUP, 1.0F, 1.0F);
							if(Case.compareTo("�� ������")==0)
								RuneEquipGUI(player, NPCname);
							else if(Case.compareTo("���� ����")==0)
								UpgraderGUI(player, (short) 0,NPCname);
							else if(Case.compareTo("���� �̵�����")==0)
								WarpMainGUI(player, 0,NPCname);
							else if(Case.compareTo("���� ����")==0)
								UpgraderGUI(player, (short) 0,NPCname);
							else if(Case.compareTo("��������")==0)
								ItemFix(player, NPCname, Integer.parseInt(ChatColor.stripColor(event.getCurrentItem().getItemMeta().getLore().get(3).split(" ")[3])), Integer.parseInt(ChatColor.stripColor(event.getCurrentItem().getItemMeta().getLore().get(4).split(" ")[5])));
							else if(Case.compareTo("�ּ���")==0)
							{
								GoldBigDragon_RPG.Util.ETC ETC = new GoldBigDragon_RPG.Util.ETC();
								YamlManager Config = YC.getNewConfig("config.yml");

								if(GoldBigDragon_RPG.Main.ServerOption.PlayerList.get(player.getUniqueId().toString()).getETC_BuffCoolTime()+(Config.getInt("NPC.Shaman.BuffCoolTime")*1000) > ETC.getNowUTC())
								{
									s.SP(player,Sound.ORB_PICKUP, 1.0F, 1.8F);
									player.sendMessage(ChatColor.RED + "[SYSTEM] : "+ChatColor.WHITE+((GoldBigDragon_RPG.Main.ServerOption.PlayerList.get(player.getUniqueId().toString()).getETC_BuffCoolTime()+(Config.getInt("NPC.Shaman.BuffCoolTime")*1000) -ETC.getNowUTC())/1000)+ChatColor.RED+"�� �� �̿� �����մϴ�!");
									return;
								}
								if(GoldBigDragon_RPG.Main.ServerOption.PlayerList.get(player.getUniqueId().toString()).getStat_Money() < NPCscript.getInt("Job.Deal"))
								{
									s.SP(player,Sound.ORB_PICKUP, 1.0F, 1.8F);
									player.sendMessage(ChatColor.RED + "[SYSTEM] : ��ä ����� �����մϴ�!");
									return;
								}
								else
								{
									GoldBigDragon_RPG.Effect.Potion P = new GoldBigDragon_RPG.Effect.Potion();
									GoldBigDragon_RPG.Main.ServerOption.PlayerList.get(player.getUniqueId().toString()).setETC_BuffCoolTime(ETC.getNowUTC());
									if(n.RandomNum(0, 100) <= NPCscript.getInt("Job.GoodRate"))
									{
										switch(n.RandomNum(1, 8))
										{
										case 1:
											s.SP(player,Sound.ANVIL_LAND, 1.0F, 1.0F);
											player.sendMessage(ChatColor.WHITE+""+ChatColor.BOLD+"[��������] �غ�� ���� �߰����� ���� �ܴ��ϰ� ������...");
											P.givePotionEffect(player,PotionEffectType.DAMAGE_RESISTANCE, NPCscript.getInt("Job.BuffTime"), n.RandomNum(1,  NPCscript.getInt("Job.BuffMaxStrog")));
											break;
										case 2:
											s.SP(player,Sound.DIG_GRAVEL, 1.5F, 1.0F);
											player.sendMessage(ChatColor.WHITE+""+ChatColor.BOLD+"[�������] ������ ���� ���� �ϰ� ū ���� ������ ��������...");
											P.givePotionEffect(player,PotionEffectType.FAST_DIGGING, NPCscript.getInt("Job.BuffTime"), n.RandomNum(1,  NPCscript.getInt("Job.BuffMaxStrog")));
											break;
										case 3:
											s.SP(player,Sound.FIZZ, 1.5F, 1.0F);
											player.sendMessage(ChatColor.WHITE+""+ChatColor.BOLD+"[٥�κ��] ���� ��վ� ���ٸ� ���̻� ���� �η��� ��������...");
											P.givePotionEffect(player,PotionEffectType.FIRE_RESISTANCE, NPCscript.getInt("Job.BuffTime"), n.RandomNum(1,  NPCscript.getInt("Job.BuffMaxStrog")));
											break;
										case 4:
											s.SP(player,Sound.LEVEL_UP, 1.5F, 0.8F);
											player.sendMessage(ChatColor.WHITE+""+ChatColor.BOLD+"[����̪�] ���� ������ ��� ǳ��ο�� �� ���� ����� �ƴ��Ѱ�...");
											P.givePotionEffect(player,PotionEffectType.HEAL, NPCscript.getInt("Job.BuffTime"), n.RandomNum(1,  NPCscript.getInt("Job.BuffMaxStrog")));
											P.givePotionEffect(player,PotionEffectType.SATURATION, NPCscript.getInt("Job.BuffTime"), n.RandomNum(1,  NPCscript.getInt("Job.BuffMaxStrog")));
											P.givePotionEffect(player,PotionEffectType.REGENERATION, NPCscript.getInt("Job.BuffTime"), n.RandomNum(1,  NPCscript.getInt("Job.BuffMaxStrog")));
											P.givePotionEffect(player,PotionEffectType.HEALTH_BOOST, NPCscript.getInt("Job.BuffTime"), n.RandomNum(1,  NPCscript.getInt("Job.BuffMaxStrog")));
											break;
										case 5:
											s.SP(player,Sound.MINECART_INSIDE, 1.0F, 1.0F);
											player.sendMessage(ChatColor.WHITE+""+ChatColor.BOLD+"[�������] �� ���� �̸� ���� ���ƴٴϱ� �����ϴ�, ������ ���� ��ϸ�...");
											P.givePotionEffect(player,PotionEffectType.SPEED, NPCscript.getInt("Job.BuffTime"), n.RandomNum(1,  NPCscript.getInt("Job.BuffMaxStrog")));
											P.givePotionEffect(player,PotionEffectType.JUMP, NPCscript.getInt("Job.BuffTime"), n.RandomNum(1,  NPCscript.getInt("Job.BuffMaxStrog")));
											break;
										case 6:
											s.SP(player,Sound.ANVIL_LAND, 1.0F, 1.8F);
											player.sendMessage(ChatColor.WHITE+""+ChatColor.BOLD+"[Ӥ������] ��鸮�� �ʴ� �ų��� ���� ���� �����, ö�ٰ��� "+ServerOption.WILL+"�� ���� ���� �ٽ��� �߸���...");
											P.givePotionEffect(player,PotionEffectType.INCREASE_DAMAGE, NPCscript.getInt("Job.BuffTime"), n.RandomNum(1,  NPCscript.getInt("Job.BuffMaxStrog")));
											break;
										case 7:
											s.SP(player,Sound.ENDERDRAGON_GROWL, 1.0F, 1.0F);
											player.sendMessage(ChatColor.WHITE+""+ChatColor.BOLD+"[ˬ������] �� ���� ���� �޺��̿�, �� �߱� ��� ���� ���̴�...");
											P.givePotionEffect(player,PotionEffectType.NIGHT_VISION, NPCscript.getInt("Job.BuffTime"), n.RandomNum(1,  NPCscript.getInt("Job.BuffMaxStrog")));
											break;
										case 8:
											s.SP(player,Sound.WATER, 1.5F, 1.0F);
											player.sendMessage(ChatColor.WHITE+""+ChatColor.BOLD+"[٥����] �ſ︸ġ ���� ������ ���� ���⸦ �����ϸ���...");
											P.givePotionEffect(player,PotionEffectType.WATER_BREATHING, NPCscript.getInt("Job.BuffTime"), n.RandomNum(1,  NPCscript.getInt("Job.BuffMaxStrog")));
											break;
										}
									}
									else
									{
										switch(n.RandomNum(1, 8))
										{
										case 1:
											s.SP(player,Sound.BLAZE_BREATH, 1.0F, 1.0F);
											player.sendMessage(ChatColor.RED+""+ChatColor.BOLD+"[��������] �Ÿ��� ���� ���� ������ �ѳ� ���̷� �ٶ󺸸���...");
											P.givePotionEffect(player,PotionEffectType.WEAKNESS, NPCscript.getInt("Job.BuffTime"), n.RandomNum(1,  NPCscript.getInt("Job.BuffMaxStrog")+1));
											break;
										case 2:
											s.SP(player,Sound.AMBIENCE_CAVE, 1.0F, 1.0F);
											player.sendMessage(ChatColor.RED+""+ChatColor.BOLD+"[��������] ��ġ �յ� ������ �ʰŴ�...");
											P.givePotionEffect(player,PotionEffectType.BLINDNESS, NPCscript.getInt("Job.BuffTime"), n.RandomNum(1,  NPCscript.getInt("Job.BuffMaxStrog")+1));
											break;
										case 3:
											s.SP(player,Sound.ENDERDRAGON_GROWL, 0.8F,0.5F);
											player.sendMessage(ChatColor.RED+""+ChatColor.BOLD+"[�������] ���� ���� �� ���� �����̶�...");
											P.givePotionEffect(player,PotionEffectType.CONFUSION, NPCscript.getInt("Job.BuffTime"), n.RandomNum(1,  NPCscript.getInt("Job.BuffMaxStrog")+1));
											break;
										case 4:
											s.SP(player,Sound.ZOMBIE_DEATH, 0.8F,0.5F);
											player.sendMessage(ChatColor.RED+""+ChatColor.BOLD+"[ӯ������] �� ����� ��ġ �ƱͿ� ���� �ӻ��ϴ�...");
											P.givePotionEffect(player,PotionEffectType.HUNGER, NPCscript.getInt("Job.BuffTime"), n.RandomNum(1,  NPCscript.getInt("Job.BuffMaxStrog")+1));
											break;
										case 5:
											s.SP(player,Sound.HORSE_ZOMBIE_DEATH, 0.8F,0.5F);
											player.sendMessage(ChatColor.RED+""+ChatColor.BOLD+"[�������] ��Ʈ���� �Ⱑ �� ��ü�� ���� ���� ������ ���·ӱⰡ ¦�� ������...");
											P.givePotionEffect(player,PotionEffectType.POISON, NPCscript.getInt("Job.BuffTime"), n.RandomNum(1,  NPCscript.getInt("Job.BuffMaxStrog")+1));
											break;
										case 6:
											s.SP(player,Sound.ITEM_BREAK, 0.8F,0.5F);
											player.sendMessage(ChatColor.RED+""+ChatColor.BOLD+"[����گ��] �������� ����� �Ͽ���, �ð��� ��ٷ� ���� ��������...");
											P.givePotionEffect(player,PotionEffectType.SLOW_DIGGING, NPCscript.getInt("Job.BuffTime"), n.RandomNum(1,  NPCscript.getInt("Job.BuffMaxStrog")+1));
											break;
										case 7:
											s.SP(player,Sound.SPIDER_IDLE, 0.8F,0.5F);
											player.sendMessage(ChatColor.RED+""+ChatColor.BOLD+"[ʹ����ͪ] ���� ������ ������ �پ�� ������...");
											P.givePotionEffect(player,PotionEffectType.SLOW, NPCscript.getInt("Job.BuffTime"), n.RandomNum(1,  NPCscript.getInt("Job.BuffMaxStrog")+1));
											break;
										case 8:
											s.SP(player,Sound.WITHER_HURT, 0.8F,0.5F);
											player.sendMessage(ChatColor.RED+""+ChatColor.BOLD+"[������] ���� ���� ���� ��ư��� ���밡 ���±���...");
											P.givePotionEffect(player,PotionEffectType.WITHER, NPCscript.getInt("Job.BuffTime"), n.RandomNum(1,  NPCscript.getInt("Job.BuffMaxStrog")+1));
											break;
										}
									}
							  		GoldBigDragon_RPG.Main.ServerOption.PlayerList.get(player.getUniqueId().toString()).addStat_MoneyAndEXP(-1 * NPCscript.getInt("Job.Deal"), 0, false);
								}
								NPCscript.set("Job.Type","Shaman");
								NPCscript.set("Job.GoodRate",50);
								NPCscript.set("Job.BuffMaxStrog",2);
								NPCscript.set("Job.BuffTime",60);
								NPCscript.set("Job.Deal",500);
							}
							else if(Case.compareTo("����")==0)
							{
								Damageable getouter = (Damageable)player;
								int a = (int)getouter.getHealth();
								if(player.getHealthScale()== a&&(player.hasPotionEffect(PotionEffectType.BLINDNESS)||
										player.hasPotionEffect(PotionEffectType.CONFUSION)||player.hasPotionEffect(PotionEffectType.HARM)||
										player.hasPotionEffect(PotionEffectType.HUNGER)||player.hasPotionEffect(PotionEffectType.POISON)||
										player.hasPotionEffect(PotionEffectType.SLOW)||player.hasPotionEffect(PotionEffectType.SLOW_DIGGING)||
										player.hasPotionEffect(PotionEffectType.WEAKNESS)||player.hasPotionEffect(PotionEffectType.WITHER))==false)
								{
									s.SP(player,Sound.ORB_PICKUP, 1.0F, 1.8F);
									player.sendMessage(ChatColor.DARK_AQUA+"[SYSTEM] : ����� ġ����� �ʿ䰡 �����ϴ�!");
									return;
								}
								if(GoldBigDragon_RPG.Main.ServerOption.PlayerList.get(player.getUniqueId().toString()).getStat_Money() < NPCscript.getInt("Job.Deal"))
								{
									s.SP(player,Sound.ORB_PICKUP, 1.0F, 1.8F);
									player.sendMessage(ChatColor.RED + "[SYSTEM] : ġ�� ����� �����մϴ�!");
								}
								else
								{
									s.SP(player,Sound.LEVEL_UP, 1.0F, 0.5F);
									Damageable p = player;
									p.setHealth(p.getMaxHealth());
									player.removePotionEffect(PotionEffectType.BLINDNESS);
									player.removePotionEffect(PotionEffectType.CONFUSION);
									player.removePotionEffect(PotionEffectType.HARM);
									player.removePotionEffect(PotionEffectType.HUNGER);
									player.removePotionEffect(PotionEffectType.POISON);
									player.removePotionEffect(PotionEffectType.SLOW);
									player.removePotionEffect(PotionEffectType.SLOW_DIGGING);
									player.removePotionEffect(PotionEffectType.WEAKNESS);
									player.removePotionEffect(PotionEffectType.WITHER);
							  		GoldBigDragon_RPG.Main.ServerOption.PlayerList.get(player.getUniqueId().toString()).addStat_MoneyAndEXP(-1 * NPCscript.getInt("Job.Deal"), 0, false);
									player.sendMessage(ChatColor.DARK_AQUA+"[SYSTEM] : ����� ������ ġ��Ǿ����ϴ�!");
								}
							}
							else if(Case.compareTo("���� ����")==0)
							{
								YamlManager JobList  = YC.getNewConfig("Skill/JobList.yml");
								Object[] Job = JobList.getConfigurationSection("MapleStory").getKeys(false).toArray();
								for(short count = 0; count < Job.length; count++)
								{
									Object[] q = JobList.getConfigurationSection("MapleStory."+Job[count].toString()).getKeys(false).toArray();
									for(short counter=0;counter<q.length;counter++)
									{
										if(q[counter].toString().compareTo(NPCscript.getString("Job.Job"))==0)
										{
											YamlManager PlayerJob  = YC.getNewConfig("Skill/PlayerData/"+player.getUniqueId().toString()+".yml");
											int NeedLV = JobList.getInt("MapleStory."+Job[count].toString()+"."+q[counter].toString()+".NeedLV");
											int NeedSTR = JobList.getInt("MapleStory."+Job[count].toString()+"."+q[counter].toString()+".NeedSTR");
											int NeedDEX = JobList.getInt("MapleStory."+Job[count].toString()+"."+q[counter].toString()+".NeedDEX");
											int NeedINT = JobList.getInt("MapleStory."+Job[count].toString()+"."+q[counter].toString()+".NeedINT");
											int NeedWILL = JobList.getInt("MapleStory."+Job[count].toString()+"."+q[counter].toString()+".NeedWILL");
											int NeedLUK = JobList.getInt("MapleStory."+Job[count].toString()+"."+q[counter].toString()+".NeedLUK");
											String PrevJob = JobList.getString("MapleStory."+Job[count].toString()+"."+q[counter].toString()+".PrevJob");
											
											if((GoldBigDragon_RPG.Main.ServerOption.PlayerList.get(player.getUniqueId().toString()).getStat_Level()>=NeedLV)&&
											(GoldBigDragon_RPG.Main.ServerOption.PlayerList.get(player.getUniqueId().toString()).getStat_STR()>=NeedSTR)&&
											(GoldBigDragon_RPG.Main.ServerOption.PlayerList.get(player.getUniqueId().toString()).getStat_DEX()>=NeedDEX)&&
											(GoldBigDragon_RPG.Main.ServerOption.PlayerList.get(player.getUniqueId().toString()).getStat_INT()>=NeedINT)&&
											(GoldBigDragon_RPG.Main.ServerOption.PlayerList.get(player.getUniqueId().toString()).getStat_WILL()>=NeedWILL)&&
											(GoldBigDragon_RPG.Main.ServerOption.PlayerList.get(player.getUniqueId().toString()).getStat_LUK()>=NeedLUK))
											{
												if(PrevJob.compareTo("null")!=0)
												{
													if(PlayerJob.getString("Job.Type").compareTo(PrevJob)!=0)
													{
														player.sendMessage(ChatColor.RED + "[����] : ����� �������δ� ���� �� �� ���� ����Դϴ�.");
														s.SP(player, org.bukkit.Sound.ORB_PICKUP, 2.0F, 1.7F);
														return;	
													}
												}
												if(NPCscript.getString("Job.Job").compareTo(PlayerJob.getString("Job.Type"))!=0)
												{
													//�÷��̾� ������
													PlayerJob.set("Job.Root", Job[count].toString());
													PlayerJob.set("Job.Type", NPCscript.getString("Job.Job"));
													PlayerJob.createSection("MapleStory."+NPCscript.getString("Job.Job")+".Skill");
													PlayerJob.saveConfig();
													ServerOption.PlayerList.get(player.getUniqueId().toString()).setPlayerRootJob(Job[count].toString());
													GoldBigDragon_RPG.ETC.Job J = new GoldBigDragon_RPG.ETC.Job();
													J.FixPlayerJobList(player);
													player.closeInventory();
													Bukkit.broadcastMessage(ChatColor.GREEN+""+ChatColor.BOLD+"["+ChatColor.YELLOW+""+ChatColor.BOLD+player.getName()+ChatColor.GREEN+""+ChatColor.BOLD+"�Բ��� "+ChatColor.YELLOW+""+ChatColor.BOLD+NPCscript.getString("Job.Job")+ChatColor.GREEN+""+ChatColor.BOLD+" �±޿� ���� �ϼ̽��ϴ�!]");
												}
												else
												{
													player.sendMessage(ChatColor.RED + "[����] : ���� �������� ���� �� �� �����ϴ�!");
													s.SP(player, org.bukkit.Sound.ORB_PICKUP, 2.0F, 1.7F);
												}
											}
											else
											{
												player.sendMessage(ChatColor.RED + "[����] : ����� ������ ���� ��ǿ� ���� �ʽ��ϴ�.");
												s.SP(player, org.bukkit.Sound.ORB_PICKUP, 2.0F, 1.7F);
											}
										}
									}
								}
							}
						}
						else if(event.getClick().isRightClick() == true && player.isOp() == true)
						{
							s.SP(player, Sound.ITEM_PICKUP, 0.8F, 1.0F);
							NPCjobGUI(player,NPCname);
						}
						return;
					}
					else if(Case.compareTo("���� �޴�") == 0)
					{
						s.SP(player, Sound.PISTON_RETRACT, 0.8F, 1.8F);
						MainGUI(player,NPCname,player.isOp());
					}
					else if(Case.compareTo("������") == 0)
					{
						s.SP(player, Sound.PISTON_RETRACT, 0.8F, 1.8F);
						player.closeInventory();
					}
					else
					{
						s.SP(player, Sound.ITEM_PICKUP, 0.8F, 1.0F);
						if(Case.compareTo("��ȭ�� �Ѵ�")==0)
							TalkGUI(player,NPCname,null,(char)-1);
						else if(Case.compareTo("�ŷ��� �Ѵ�")==0)
							ShopGUI(player,NPCname,(short) 0,true,false);
						else if(Case.compareTo("����Ʈ")==0)
							NPCQuest(player, (short) 0);
						else if(Case.compareTo("��ȭ ����")==0)
							NPCTalkGUI(player, (short) 0, NPCname,"NT");
						else if(Case.compareTo("�ŷ� ����")==0)
							ShopGUI(player,NPCname,(short) 0,true,true);
						else if(Case.compareTo("����Ʈ ����")==0)
							AllOfQuestListGUI(player, (short) 0);
						else if(Case.compareTo("���� ����")==0)
							NPCjobGUI(player,NPCname);
						else if(Case.compareTo("�����ϱ�")==0)
							PresentGiveGUI(player, NPCname, false, -1);
						else if(Case.compareTo("���� ����")==0)
							PresentSettingGUI(player, NPCname);
						else if(Case.compareTo("���� ����")==0)
						{
							Object_UserData u = new Object_UserData();
							YamlController YC = new YamlController(GoldBigDragon_RPG.Main.Main.plugin);
							YamlManager NPCConfig = YC.getNewConfig("NPC/NPCData/"+ u.getNPCuuid(player) +".yml");
							if(event.isShiftClick()&&event.isRightClick())
							{
								NPCConfig.set("Sale.Enable", false);
								NPCConfig.saveConfig();
								s.SP(player, Sound.LAVA_POP, 0.8F, 1.0F);
								MainGUI(player, NPCname, player.isOp());
							}
							else if(event.isLeftClick()&&event.isShiftClick()==false)
							{
								s.SP(player, Sound.ITEM_PICKUP, 0.8F, 1.0F);
								u.setType(player, "NPC");
								u.setString(player, (byte)2,NPCname);
								u.setString(player, (byte)3,u.getNPCuuid(player));
								u.setString(player, (byte)4,"SaleSetting1");
								player.sendMessage(ChatColor.DARK_AQUA + "[NPC] : ������ ���� �� �ּ� ȣ������ �Է� �� �ּ���! (-1000 ~ 1000 ���� ��)");
								player.closeInventory();
							}
						}
					}
				}
				else //���� GUI
				{
					switch(event.getSlot())
					{
						case 0:
						case 1:
						case 2:
						case 3:
						case 4:
						case 5:
						case 6:
						case 7:
						case 8:
						case 9:
						case 17:
						case 18:
						case 26:
						case 27:
						case 35:
						case 36:
						case 37:
						case 38:
						case 39:
						case 40:
						case 41:
						case 42:
						case 43:
						case 44:
							return;
						case 45:
							MainGUI(player,NPCname,player.isOp());
							s.SP(player, Sound.ITEM_PICKUP, 0.8F, 1.0F);
							break;
						case 48:
						{
							int showingPage = Integer.parseInt(ChatColor.stripColor(event.getCurrentItem().getItemMeta().getLore().get(1).split("������ : ")[1]));
							s.SP(player, Sound.ITEM_PICKUP, 0.8F, 1.0F);
							if(event.getInventory().getItem(0).getItemMeta().hasLore() == false)
							{
								if(event.getInventory().getItem(0).getData().getData() ==(byte)14)
									ShopGUI(player, NPCname, (short) (showingPage-1) , false,false);
								else
									ShopGUI(player, NPCname, (short) (showingPage-1), true,false);
							}
							else
							{
								if(event.getInventory().getItem(0).getData().getData() ==(byte)14)
									ShopGUI(player, NPCname, (short) (showingPage-1) , false,true);
								else
									ShopGUI(player, NPCname, (short) (showingPage-1), true,true);
							}
							break;
						}
						case 49:
							s.SP(player, Sound.CHEST_OPEN, 0.8F, 1.0F);
							if(event.getInventory().getItem(0).getItemMeta().hasLore() == false)
							{
								if(event.getCurrentItem().getData().getData() ==(byte)14)
									ShopGUI(player, NPCname, (short) 0, false,false);
								else
									ShopGUI(player, NPCname, (short) 0, true,false);
							}
							else
							{
								if(event.getCurrentItem().getData().getData() ==(byte)14)
									ShopGUI(player, NPCname, (short) 0, false,true);
								else
									ShopGUI(player, NPCname, (short) 0, true,true);
							}
							break;
						case 50:
							s.SP(player, Sound.ITEM_PICKUP, 0.8F, 1.0F);
							int showingPage2 = Integer.parseInt(ChatColor.stripColor(event.getCurrentItem().getItemMeta().getLore().get(1).split("������ : ")[1]));
							if(event.getInventory().getItem(0).getItemMeta().hasLore() == false)
							{
								if(event.getInventory().getItem(0).getData().getData() ==(byte)14)
									ShopGUI(player, NPCname, (short) (showingPage2-1), false,false);
								else
									ShopGUI(player, NPCname, (short) (showingPage2-1), true,false);
							}
							else
							{
								if(event.getInventory().getItem(0).getData().getData() ==(byte)14)
									ShopGUI(player, NPCname, (short) (showingPage2-1), false,true);
								else
									ShopGUI(player, NPCname, (short) (showingPage2-1), true,true);
							}
							break;
						case 53:
							s.SP(player, Sound.PISTON_RETRACT, 0.8F, 1.8F);
							player.closeInventory();
							break;
						default:
						{
							ItemStack item = event.getCurrentItem();
							boolean isBuy = true;
							if(event.getInventory().getItem(0).getData().getData() == (byte)14)
								isBuy=false;
							if(event.getInventory().getItem(0).getItemMeta().hasLore())
							{
								if(event.getClick().isRightClick() == true)
								{
									Object_UserData u = new Object_UserData();
									String Type=null;
									if(isBuy == true)
										Type = "Sell";
									else
										Type = "Buy";
									
									byte num = Byte.parseByte(ChatColor.stripColor(item.getItemMeta().getLore().get(item.getItemMeta().getLore().size()-1)));
									YamlController YC = new YamlController(GoldBigDragon_RPG.Main.Main.plugin);
									YamlManager NPCscript = YC.getNewConfig("NPC/NPCData/"+ u.getNPCuuid(player) +".yml");

									Set<String> a = NPCscript.getConfigurationSection("Shop."+Type).getKeys(false);
									
									for(short count = 0; count < a.size(); count++)
									{
										if(count == num)
										{
											for(short counter =count; counter < a.size()-1; counter++)
											{
												NPCscript.set("Shop."+Type+"."+counter + ".item", NPCscript.getItemStack("Shop."+Type+"."+(counter+1) + ".item"));
												NPCscript.set("Shop."+Type+"."+counter + ".price", NPCscript.getLong("Shop."+Type+"."+(counter+1) + ".price"));
											}
											NPCscript.removeKey("Shop."+Type+"."+(a.size()-1) + ".item");
											NPCscript.removeKey("Shop."+Type+"."+(a.size()-1) + ".price");
											NPCscript.removeKey("Shop."+Type+"."+(a.size()-1));
											NPCscript.saveConfig();
										}
									}
								}
								else
								{
									if(item.getItemMeta().getLore().size() < 4)
									{
										ItemMeta Icon_Meta = item.getItemMeta();
										Icon_Meta.setLore(null);
										item.setItemMeta(Icon_Meta);
										player.getInventory().addItem(item);
									}
									else
									{
										ItemMeta Icon_Meta = item.getItemMeta();
										String[] l = new String[item.getItemMeta().getLore().size()-3];
										for(byte count =0;count <l.length;count++)
											l[count] = (item.getItemMeta().getLore().get(count));
										Icon_Meta.setLore(Arrays.asList(l));
										item.setItemMeta(Icon_Meta);
										player.getInventory().addItem(item);
									}
								}
								s.SP(player, org.bukkit.Sound.LAVA_POP, 2.0F, 1.7F);
								int showingPage3 = Integer.parseInt(ChatColor.stripColor(event.getInventory().getItem(8).getItemMeta().getLore().get(0)));
								if(isBuy == true)
									ShopGUI(player, NPCname, (short) showingPage3, true,true);
								else
									ShopGUI(player, NPCname, (short) showingPage3, false,true);
							}
							else
							{
								int value = Integer.parseInt(item.getItemMeta().getLore().get(item.getItemMeta().getLore().size()-2).split(" ")[2]);
								if(item.getItemMeta().getLore().size() < 4)
								{
									ItemMeta Icon_Meta = item.getItemMeta();
									Icon_Meta.setLore(null);
									item.setItemMeta(Icon_Meta);
								}
								else
								{
									ItemMeta Icon_Meta = item.getItemMeta();
									String[] l = new String[item.getItemMeta().getLore().size()-3];
									for(int count =0;count <l.length;count++)
										l[count] = (item.getItemMeta().getLore().get(count));
									Icon_Meta.setLore(Arrays.asList(l));
									item.setItemMeta(Icon_Meta);
								}
								s.SP(player, Sound.DOOR_OPEN, 0.8F, 0.5F);
								if(event.getInventory().getItem(0).getData().getData() == (byte)14)
									ItemBuy(player, item, value, NPCname, isBuy, 0);
								else
									ItemBuy(player, item, value, NPCname, isBuy, 0);
							}
						}
					}
				}
			}
			return;
	}

	public void NPCJobClick(InventoryClickEvent event, String NPCname)
	{
		event.setCancelled(true);
		Player player = (Player) event.getWhoClicked();

		Object_UserData u = new Object_UserData();
		YamlController YC = new YamlController(GoldBigDragon_RPG.Main.Main.plugin);
	  	if(YC.isExit("NPC/NPCData/"+ u.getNPCuuid(player) +".yml") == false)
	  	{
	  		GoldBigDragon_RPG.Config.NPCconfig NPCC = new GoldBigDragon_RPG.Config.NPCconfig();
	  		NPCC.NPCNPCconfig(u.getNPCuuid(player));
	  	}
		YamlManager NPCscript = YC.getNewConfig("NPC/NPCData/"+ u.getNPCuuid(player) +".yml");
		
		if(event.getClickedInventory().getTitle().equalsIgnoreCase("container.inventory") == true)
			return;
		if (event.getCurrentItem() == null
				||event.getCurrentItem().getType() == Material.AIR
				||!event.getCurrentItem().hasItemMeta())
		{
				return;
		}
		else
		{
			switch(event.getSlot())
			{
			case 1://����
				s.SP(player, Sound.ITEM_PICKUP, 1.0F, 0.8F);
				NPCscript.removeKey("Job");
				NPCscript.saveConfig();
				NPCscript.set("Job.Type","null");
				NPCscript.saveConfig();
				MainGUI(player,NPCname,player.isOp());
				return;
			case 2://��������
				s.SP(player, Sound.ITEM_PICKUP, 1.0F, 0.8F);
				NPCscript.removeKey("Job");
				NPCscript.saveConfig();
				NPCscript.set("Job.Type","BlackSmith");
				NPCscript.set("Job.FixRate",50);
				NPCscript.set("Job.10PointFixDeal",500);
				NPCscript.saveConfig();
				u.setType(player, "NPC");
				u.setString(player, (byte)2,NPCname);
				u.setString(player, (byte)3,u.getNPCuuid(player));
				u.setString(player, (byte)4,"FixRate");
				player.sendMessage(ChatColor.DARK_AQUA + "[NPC] : �� NPC�� ���� �������� �Է� �� �ּ���! (1~100 ���� ��)");
				player.closeInventory();
				return;
			case 3://�ּ���
				s.SP(player, Sound.ITEM_PICKUP, 1.0F, 0.8F);
				NPCscript.removeKey("Job");
				NPCscript.saveConfig();
				NPCscript.set("Job.Type","Shaman");
				NPCscript.set("Job.GoodRate",50);
				NPCscript.set("Job.BuffMaxStrog",2);
				NPCscript.set("Job.BuffTime",60);
				NPCscript.set("Job.Deal",500);
				NPCscript.saveConfig();
				u.setType(player, "NPC");
				u.setString(player, (byte)2,NPCname);
				u.setString(player, (byte)3,u.getNPCuuid(player));
				u.setString(player, (byte)4,"GoodRate");
				player.sendMessage(ChatColor.DARK_AQUA + "[NPC] : �� NPC�� ���� �������� �Է� �� �ּ���! (1~100 ���� ��)");
				player.closeInventory();
				return;
			case 4://����
				s.SP(player, Sound.ITEM_PICKUP, 1.0F, 0.8F);
				NPCscript.removeKey("Job");
				NPCscript.saveConfig();
				NPCscript.set("Job.Type","Healer");
				NPCscript.set("Job.Deal",500);
				NPCscript.saveConfig();
				u.setType(player, "NPC");
				u.setString(player, (byte)2,NPCname);
				u.setString(player, (byte)3,u.getNPCuuid(player));
				u.setString(player, (byte)4,"Deal");
				player.sendMessage(ChatColor.DARK_AQUA + "[NPC] : �� NPC�� ġ�� ����� �Է� �� �ּ���!");
				player.closeInventory();
				return;
			case 5://���� ����
				YamlManager Config = YC.getNewConfig("config.yml");
				if(Config.getBoolean("Server.Like_The_Mabinogi_Online_Stat_System") == false)
				{
					YamlManager JobList  = YC.getNewConfig("Skill/JobList.yml");
					Object[] Job = JobList.getConfigurationSection("MapleStory").getKeys(false).toArray();
					if(Job.length == 1)
					{
						s.SP(player, Sound.ORB_PICKUP, 1.0F, 1.8F);
						player.sendMessage(ChatColor.RED + "[NPC] : ���� ������ ������ �����ϴ�! " + ChatColor.YELLOW+"/���ǹڽ�"+ChatColor.RED+" ��ɾ ����Ͽ� �������� ����ʽÿ�!");
						return;
					}
					u.setType(player, "NPC");
					u.setString(player, (byte)2,u.getNPCuuid(player));
					u.setString(player, (byte)3,NPCname);
					u.setString(player, (byte)4,"NPCJL");

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
					player.closeInventory();
				}
				else
				{
					s.SP(player,Sound.ORB_PICKUP, 1.0F, 1.8F);
					player.sendMessage(ChatColor.RED + "[NPC] : ���� ����� ����Ͻ÷���"+ChatColor.YELLOW+" /���ǹڽ�"+ChatColor.RED + " ���� ���� �ý����� '������ ���丮'�� ������ �ֽñ� �ٶ��ϴ�.");
				}
				return;
			case 6://���� �̵�����
				s.SP(player, Sound.ITEM_PICKUP, 1.0F, 0.8F);
				NPCscript.removeKey("Job");
				NPCscript.saveConfig();
				NPCscript.set("Job.Type","Warper");
				NPCscript.createSection("Job.WarpList");
				NPCscript.saveConfig();
				WarpMainGUI(player, 0,NPCname);
				return;
			case 7://���� ����
				s.SP(player, Sound.ITEM_PICKUP, 1.0F, 0.8F);
				NPCscript.removeKey("Job");
				NPCscript.saveConfig();
				NPCscript.set("Job.Type","Upgrader");
				NPCscript.createSection("Job.UpgradeRecipe");
				NPCscript.saveConfig();
				MainGUI(player, NPCname, player.isOp());
				return;
			case 10://�� ������
				s.SP(player, Sound.ITEM_PICKUP, 1.0F, 0.8F);
				NPCscript.removeKey("Job");
				NPCscript.saveConfig();
				NPCscript.set("Job.Type","Rune");
				NPCscript.set("Job.SuccessRate",50);
				NPCscript.set("Job.Deal",5000);
				NPCscript.saveConfig();
				u.setType(player, "NPC");
				u.setString(player, (byte)4,"SuccessRate");
				u.setString(player, (byte)3,u.getNPCuuid(player));
				u.setString(player, (byte)2,NPCname);
				player.sendMessage(ChatColor.DARK_AQUA + "[NPC] : �� NPC�� �� ���� �������� �Է� �� �ּ���! (1~100 ���� ��)");
				player.closeInventory();
				return;
			case 18:
				s.SP(player, Sound.ITEM_PICKUP, 1.0F, 0.8F);
				MainGUI(player,NPCname,player.isOp());
				return;
			case 26:
				s.SP(player, Sound.PISTON_RETRACT, 0.8F, 1.8F);
				player.closeInventory();
				return;
			}
		}
	}
	
	public void WarpMainGUIClick(InventoryClickEvent event)
	{
		short page =  (short) (Short.parseShort(event.getInventory().getTitle().split(" : ")[1])-1);
		String NPCname = ChatColor.stripColor(event.getInventory().getItem(53).getItemMeta().getLore().get(1));

		Object_UserData u = new Object_UserData();
		Player player = (Player) event.getWhoClicked();
		YamlController YC = new YamlController(GoldBigDragon_RPG.Main.Main.plugin);
		YamlManager NPCConfig =YC.getNewConfig("NPC/NPCData/"+u.getNPCuuid(player)+".yml");
		GoldBigDragon_RPG.Effect.Sound s = new GoldBigDragon_RPG.Effect.Sound();

		event.setCancelled(true);
		switch (event.getSlot())
		{
			case 45://���� �������
				s.SP(player, Sound.ITEM_PICKUP, 1.0F, 1.0F);
				MainGUI(player, NPCname, player.isOp());
				break;
			case 48://���� ������
				s.SP(player, Sound.ITEM_PICKUP, 1.0F, 1.0F);
				WarpMainGUI(player,page-1,NPCname);
				break;
			case 49://�� ����
				s.SP(player, Sound.ITEM_PICKUP, 1.0F, 1.0F);
				WarperGUI(player, (short) 0, NPCname);
				break;
			case 50://���� ������
				s.SP(player, Sound.ITEM_PICKUP, 1.0F, 1.0F);
				WarpMainGUI(player,page+1,NPCname);
				break;
			case 53://������
				s.SP(player, Sound.PISTON_RETRACT, 0.8F, 1.8F);
				player.closeInventory();
				return;
			default:
				if(event.isLeftClick()==true&&event.isShiftClick()==false)
				{
					GoldBigDragon_RPG.Util.ETC ETC = new GoldBigDragon_RPG.Util.ETC();
					if(GoldBigDragon_RPG.Main.ServerOption.PlayerList.get(player.getUniqueId().toString()).getStat_AttackTime()+15000 >= ETC.getSec())
					{
						player.sendMessage(ChatColor.RED+"[�̵� �Ұ�] : "+ChatColor.YELLOW+((GoldBigDragon_RPG.Main.ServerOption.PlayerList.get(player.getUniqueId().toString()).getStat_AttackTime()+15000 - ETC.getSec())/1000)+ChatColor.RED+" �� �Ŀ� �̵� �����մϴ�!");
						s.SP(player, Sound.ORB_PICKUP, 1.0F, 1.8F);
						return;
					}
					if(GoldBigDragon_RPG.Main.ServerOption.PlayerList.get(player.getUniqueId().toString()).getStat_Money() >= NPCConfig.getInt("Job.WarpList."+((page*45)+event.getSlot())+".Cost"))
					{
				  		GoldBigDragon_RPG.Main.ServerOption.PlayerList.get(player.getUniqueId().toString()).addStat_MoneyAndEXP(-1 * NPCConfig.getInt("Job.WarpList."+((page*45)+event.getSlot())+".Cost"), 0, false);
						String AreaName = NPCConfig.getString("Job.WarpList."+((page*45)+event.getSlot())+".Area");
						YamlManager AreaConfig =YC.getNewConfig("Area/AreaList.yml");
						s.SL(player.getLocation(), Sound.ENDERMAN_TELEPORT, 1.0F, 1.0F);
						player.teleport(new Location(Bukkit.getWorld(AreaConfig.getString(AreaName+".World")), AreaConfig.getInt(AreaName+".SpawnLocation.X"), AreaConfig.getInt(AreaName+".SpawnLocation.Y"), AreaConfig.getInt(AreaName+".SpawnLocation.Z")));
						s.SL(player.getLocation(), Sound.ENDERMAN_TELEPORT, 1.0F, 1.0F);
					}
					else
					{
						s.SP(player, Sound.ORB_PICKUP, 1.0F, 1.8F);
						player.sendMessage(ChatColor.RED+"[����] : �ڷ���Ʈ ����� �����մϴ�!");
					}
				}
				else if(event.isRightClick()==true&&event.isShiftClick()==true&&player.isOp()==true)
				{
					int number = ((page*45)+event.getSlot());
					int Acount =  NPCConfig.getConfigurationSection("Job.WarpList").getKeys(false).toArray().length-1;

					for(int counter = number;counter <Acount;counter++)
						NPCConfig.set("Job.WarpList."+counter, NPCConfig.get("Job.WarpList."+(counter+1)));
					NPCConfig.removeKey("Job.WarpList."+Acount);
					NPCConfig.saveConfig();
					WarpMainGUI(player, page, NPCname);
				}
		}
	}

	public void WarperGUIClick(InventoryClickEvent event)
	{
		int page =  Integer.parseInt(event.getInventory().getTitle().split(" : ")[1])-1;
		String NPCname = ChatColor.stripColor(event.getInventory().getItem(53).getItemMeta().getLore().get(1));

		Object_UserData u = new Object_UserData();
		Player player = (Player) event.getWhoClicked();
		YamlController YC = new YamlController(GoldBigDragon_RPG.Main.Main.plugin);
		YamlManager NPCConfig =YC.getNewConfig("NPC/NPCData/"+u.getNPCuuid(player)+".yml");
		GoldBigDragon_RPG.Effect.Sound s = new GoldBigDragon_RPG.Effect.Sound();

		event.setCancelled(true);
		switch (event.getSlot())
		{
			case 45://���� �������
				s.SP(player, Sound.ITEM_PICKUP, 1.0F, 1.0F);
				WarpMainGUI(player, 0, NPCname);
				break;
			case 48://���� ������
				s.SP(player, Sound.ITEM_PICKUP, 1.0F, 1.0F);
				WarperGUI(player, (short) (page-1), NPCname);
				break;
			case 50://���� ������
				s.SP(player, Sound.ITEM_PICKUP, 1.0F, 1.0F);
				WarperGUI(player, (short) (page+1), NPCname);
				break;
			case 53://������
				s.SP(player, Sound.PISTON_RETRACT, 0.8F, 1.8F);
				player.closeInventory();
				return;
			default:
			{
				s.SP(player, Sound.ITEM_PICKUP, 1.0F, 0.8F);
				int number = NPCConfig.getConfigurationSection("Job.WarpList").getKeys(false).size();
				NPCConfig.set("Job.WarpList."+number+".Area",ChatColor.stripColor(event.getCurrentItem().getItemMeta().getDisplayName()));
				NPCConfig.set("Job.WarpList."+number+".DisplayName","��������");
				NPCConfig.set("Job.WarpList."+number+".Cost",1000);
				NPCConfig.saveConfig();
				u.setType(player, "NPC");
				u.setString(player, (byte)4,"WDN");
				u.setString(player, (byte)3,u.getNPCuuid(player));
				u.setString(player, (byte)2,NPCname);
				u.setInt(player, (byte)4, number);
				player.sendMessage(ChatColor.DARK_AQUA + "[NPC] : �ش� ���� ������ �̸��� ������ �ּ���!");
				player.closeInventory();
			}
		}
	}

	public void UpgraderGUIClick(InventoryClickEvent event)
	{
		short page =  (short) (Short.parseShort(event.getInventory().getTitle().split(" : ")[1])-1);
		String NPCname = ChatColor.stripColor(event.getInventory().getItem(53).getItemMeta().getLore().get(1));

		Object_UserData u = new Object_UserData();
		Player player = (Player) event.getWhoClicked();
		YamlController YC = new YamlController(GoldBigDragon_RPG.Main.Main.plugin);
		YamlManager NPCConfig =YC.getNewConfig("NPC/NPCData/"+u.getNPCuuid(player)+".yml");
		GoldBigDragon_RPG.Effect.Sound s = new GoldBigDragon_RPG.Effect.Sound();

		event.setCancelled(true);
		switch (event.getSlot())
		{
			case 45://���� �������
				s.SP(player, Sound.ITEM_PICKUP, 1.0F, 0.8F);
				MainGUI(player, NPCname,player.isOp());
				break;
			case 48://���� ������
				s.SP(player, Sound.ITEM_PICKUP, 1.0F, 0.8F);
				UpgraderGUI(player, (short) (page-1), NPCname);
				break;
			case 49://�� ������
				if(player.isOp() == true)
				{
					s.SP(player, Sound.ITEM_PICKUP, 1.0F, 0.8F);
					SelectUpgradeRecipeGUI(player, (short) 0,NPCname);
				}
				break;
			case 50://���� ������
				UpgraderGUI(player, (short) (page+1), NPCname);
				break;
			case 53://������
				s.SP(player, Sound.PISTON_RETRACT, 0.8F, 1.8F);
				player.closeInventory();
				return;
			default:
				if(event.isLeftClick() == true && event.isShiftClick()==false)
				{
					if(player.getInventory().getItemInHand() != null)
					{
						if(player.getInventory().getItemInHand().hasItemMeta() == true)
						{
							if(player.getInventory().getItemInHand().getItemMeta().hasLore()==true)
							{
								List<String> Lore = event.getCurrentItem().getItemMeta().getLore();
								int Cost = 0;
								int NeedProficiency = 100;
								int UpgradeLevel = 0;
								for(byte counter = 0; counter < Lore.size();counter++)
								{
									if(Lore.get(counter).contains("���") == true)
									{
										if(Lore.get(counter).contains(" : ") == true)
										{
											String RawString = ChatColor.stripColor(Lore.get(counter)).split(" : ")[1];
											Cost = Integer.parseInt(RawString.split(" ")[0]);
										}
									}
									else if(Lore.get(counter).contains("���õ�") == true)
									{
										if(Lore.get(counter).contains(" : ") == true)
											NeedProficiency = Integer.parseInt(ChatColor.stripColor(Lore.get(counter)).split(" : ")[1]);
									}
									else if(Lore.get(counter).contains("Ƚ��") == true)
									{
										if(Lore.get(counter).contains(" : ") == true)
										{
											String RawString = ChatColor.stripColor(Lore.get(counter)).split(" : ")[1];
											UpgradeLevel = Integer.parseInt(RawString.split(" ")[0]);
										}
									}
								}
								float playerProficiency=0.0F;
								int playerNowUpgradeLevel = -1;
								int playerMaxUpgradeLevel = -1;
								String PlayerWeaponType = "null";

								Lore = player.getInventory().getItemInHand().getItemMeta().getLore();
								for(byte counter = 0; counter < Lore.size();counter++)
								{
									if(Lore.get(counter).length()!=0)
									{
										if(Lore.get(counter).contains("���õ�") == true)
										{
											if(Lore.get(counter).contains(" : ") == true)
											{
												String RawString = ChatColor.stripColor(Lore.get(counter)).split(" : ")[1];
												playerProficiency = Float.parseFloat(RawString.split("%")[0]);
											}
										}
										else if(Lore.get(counter).contains("����") == true)
										{
											if(Lore.get(counter).contains(" : ") == true)
											{
												playerNowUpgradeLevel = Integer.parseInt(ChatColor.stripColor(Lore.get(counter)).split(" : ")[1].split(" / ")[0]);
												playerMaxUpgradeLevel =  Integer.parseInt(ChatColor.stripColor(Lore.get(counter)).split(" : ")[1].split(" / ")[1]);
											}
										}
										else if(ChatColor.stripColor(Lore.get(counter)).charAt(0)=='['&&Lore.get(counter).contains("]") == true)
										{
											PlayerWeaponType = "";
											if(Lore.get(counter).contains("[����") == true)
												PlayerWeaponType="[���� ����]";
											else if(Lore.get(counter).contains("[�Ѽ�") == true)
												PlayerWeaponType="[�Ѽ� ��]";
											else if(Lore.get(counter).contains("[���") == true)
												PlayerWeaponType="[��� ��]";
											else if(Lore.get(counter).contains("[����]") == true)
												PlayerWeaponType="[����]";
											else if(Lore.get(counter).contains("[��]") == true)
												PlayerWeaponType="[��]";
											else if(Lore.get(counter).contains("[���Ÿ�") == true)
												PlayerWeaponType="[���Ÿ� ����]";
											else if(Lore.get(counter).contains("[Ȱ]") == true)
												PlayerWeaponType="[Ȱ]";
											else if(Lore.get(counter).contains("[����]") == true)
												PlayerWeaponType="[����]";
											else if(Lore.get(counter).contains("[����") == true)
												PlayerWeaponType="[���� ����]";
											else if(Lore.get(counter).contains("[����]") == true)
												PlayerWeaponType="[����]";
											else if(Lore.get(counter).contains("[������]") == true)
												PlayerWeaponType="[������]";
											else if(Lore.get(counter).contains("[��]") == true)
												PlayerWeaponType="[��]";
											else if(Lore.get(counter).contains("[��Ÿ]") == true)
												PlayerWeaponType="[��Ÿ]";
											else
											{
												String LoreLine = ChatColor.stripColor(Lore.get(counter));
												for(short count = 0; count < LoreLine.length(); count++)
												{
													if(LoreLine.charAt(count)!=']')
														PlayerWeaponType = PlayerWeaponType+LoreLine.charAt(count);
													else
														break;
												}
												PlayerWeaponType = PlayerWeaponType+"]";
											}
										}	
									}
								}
						  		GoldBigDragon_RPG.Main.ServerOption.PlayerList.get(player.getUniqueId().toString()).addStat_MoneyAndEXP(-1 * NPCConfig.getInt("Job.WarpList."+((page*45)+event.getSlot())+".Cost"), 0, false);
								if(GoldBigDragon_RPG.Main.ServerOption.PlayerList.get(player.getUniqueId().toString()).getStat_Money() >=  Cost)
								{
									String RecipeName = ChatColor.stripColor(event.getCurrentItem().getItemMeta().getDisplayName());
									YamlManager UpgradeRecipe =YC.getNewConfig("Item/Upgrade.yml");
									String Type = ChatColor.stripColor(UpgradeRecipe.getString(RecipeName+".Only"));
									if(Type.equals(PlayerWeaponType)||
											((Type.compareTo("[���� ����]")==0&&PlayerWeaponType.compareTo("[�Ѽ� ��]")==0)||(Type.compareTo("[���� ����]")==0&&PlayerWeaponType.compareTo("[��� ��]")==0)||
											(Type.compareTo("[���� ����]")==0&&PlayerWeaponType.compareTo("[����]")==0)||(Type.compareTo("[���Ÿ� ����]")==0&&PlayerWeaponType.compareTo("[Ȱ]")==0)||
											(Type.compareTo("[���Ÿ� ����]")==0&&PlayerWeaponType.compareTo("[����]")==0)||(Type.compareTo("[���� ����]")==0&&PlayerWeaponType.compareTo("[����]")==0)||
											(Type.compareTo("[���� ����]")==0&&PlayerWeaponType.compareTo("[������]")==0)))
									{
										if(playerMaxUpgradeLevel!=-1&&playerNowUpgradeLevel!=-1&&UpgradeLevel==playerNowUpgradeLevel)
										{
											if(!(playerMaxUpgradeLevel==playerNowUpgradeLevel))
											{
												if(playerProficiency >= NeedProficiency)
												{
											  		GoldBigDragon_RPG.Main.ServerOption.PlayerList.get(player.getUniqueId().toString()).addStat_MoneyAndEXP(-1 * Cost, 0, false);
													ItemStack item = player.getInventory().getItemInHand();
													if(UpgradeRecipe.getInt(RecipeName+".MaxDurability")!=0)
														item = Calculator(item, "Durability", UpgradeRecipe.getInt(RecipeName+".MaxDurability"));
													if(UpgradeRecipe.getInt(RecipeName+".MinDamage")!=0)
														item = Calculator(item, "MinDamage", UpgradeRecipe.getInt(RecipeName+".MinDamage"));
													if(UpgradeRecipe.getInt(RecipeName+".MaxDamage")!=0)
														item = Calculator(item, "MaxDamage", UpgradeRecipe.getInt(RecipeName+".MaxDamage"));
													if(UpgradeRecipe.getInt(RecipeName+".MinMaDamage")!=0)
														item = Calculator(item, "MinMaDamage", UpgradeRecipe.getInt(RecipeName+".MinMaDamage"));
													if(UpgradeRecipe.getInt(RecipeName+".MaxMaDamage")!=0)
														item = Calculator(item, "MaxMaDamage", UpgradeRecipe.getInt(RecipeName+".MaxMaDamage"));
													if(UpgradeRecipe.getInt(RecipeName+".DEF")!=0)
														item = Calculator(item, "DEF", UpgradeRecipe.getInt(RecipeName+".DEF"));
													if(UpgradeRecipe.getInt(RecipeName+".Protect")!=0)
														item = Calculator(item, "Protect", UpgradeRecipe.getInt(RecipeName+".Protect"));
													if(UpgradeRecipe.getInt(RecipeName+".MaDEF")!=0)
														item = Calculator(item, "MaDEF", UpgradeRecipe.getInt(RecipeName+".MaDEF"));
													if(UpgradeRecipe.getInt(RecipeName+".MaProtect")!=0)
														item = Calculator(item, "MaProtect", UpgradeRecipe.getInt(RecipeName+".MaProtect"));
													if(UpgradeRecipe.getInt(RecipeName+".Critical")!=0)
														item = Calculator(item, "Critical", UpgradeRecipe.getInt(RecipeName+".Critical"));
													if(UpgradeRecipe.getInt(RecipeName+".Balance")!=0)
														item = Calculator(item, "Balance", UpgradeRecipe.getInt(RecipeName+".Balance"));
													if(UpgradeRecipe.getInt(RecipeName+".DecreaseProficiency")!=0)
														item = Calculator(item, "Proficiency", UpgradeRecipe.getInt(RecipeName+".DecreaseProficiency"));
													item = Calculator(item, "UpgradeLevelIncrease", 1);
													//���� ����
													
													player.getInventory().setItemInHand(item);
													player.closeInventory();
													s.SP(player, Sound.ANVIL_USE, 1.0F, 0.8F);
													player.sendMessage(ChatColor.DARK_AQUA + "[����] : ������ �Ϸ�Ǿ����ϴ�!");
												}
												else
												{
													s.SP(player, Sound.ORB_PICKUP, 1.0F, 1.8F);
													player.sendMessage(ChatColor.RED+"[����] : ���õ��� �����մϴ�!");
												}
											}
											else
											{
												s.SP(player, Sound.ORB_PICKUP, 1.0F, 1.8F);
												player.sendMessage(ChatColor.RED+"[����] : ���̻� ������ �� �����ϴ�!");
											}
										}
										else
										{
											s.SP(player, Sound.ORB_PICKUP, 1.0F, 1.8F);
											player.sendMessage(ChatColor.RED+"[����] : ���� ������ ���� �ʽ��ϴ�!");
										}
									}
									else
									{
										s.SP(player, Sound.ORB_PICKUP, 1.0F, 1.8F);
										player.sendMessage(ChatColor.RED+"[����] : ���� ������ ���� Ÿ���� �ƴմϴ�!");
									}
								}
								else
								{
									s.SP(player, Sound.ORB_PICKUP, 1.0F, 1.8F);
									player.sendMessage(ChatColor.RED+"[����] : ���� ����� �����մϴ�!");
								}
							}
							else
							{
								s.SP(player, Sound.ORB_PICKUP, 1.0F, 1.8F);
								player.sendMessage(ChatColor.RED+"[����] : ���� �տ� ��� �ִ� ��������  ������ �Ұ��� �մϴ�!");
							}
						}
						else
						{
							s.SP(player, Sound.ORB_PICKUP, 1.0F, 1.8F);
							player.sendMessage(ChatColor.RED+"[����] : ���� �տ� ��� �ִ� ��������  ������ �Ұ��� �մϴ�!");
						}
					}
					else
					{
						s.SP(player, Sound.ORB_PICKUP, 1.0F, 1.8F);
						player.sendMessage(ChatColor.RED+"[����] : �������� �տ� �����ϰ� �־�� �մϴ�!");
					}
				}
				else if(event.isRightClick()==true && event.isShiftClick()==true&&player.isOp()==true)
				{
					s.SP(player, Sound.LAVA_POP, 1.2F, 1.0F);
					NPCConfig.removeKey("Job.UpgradeRecipe."+ChatColor.stripColor(event.getCurrentItem().getItemMeta().getDisplayName()));
					NPCConfig.saveConfig();
					UpgraderGUI(player, page, NPCname);
				}
				
		}
	}

	public void SelectUpgradeRecipeGUIClick(InventoryClickEvent event)
	{
		short page =  (short) (Short.parseShort(event.getInventory().getTitle().split(" : ")[1])-1);
		String NPCname = ChatColor.stripColor(event.getInventory().getItem(53).getItemMeta().getLore().get(1));

		Player player = (Player) event.getWhoClicked();
		Object_UserData u = new Object_UserData();
		YamlController YC = new YamlController(GoldBigDragon_RPG.Main.Main.plugin);
		YamlManager NPCConfig =YC.getNewConfig("NPC/NPCData/"+u.getNPCuuid(player)+".yml");
		GoldBigDragon_RPG.Effect.Sound s = new GoldBigDragon_RPG.Effect.Sound();

		event.setCancelled(true);
		switch (event.getSlot())
		{
			case 45://���� �������
				s.SP(player, Sound.ITEM_PICKUP, 1.0F, 0.8F);
				UpgraderGUI(player, (short) 0, NPCname);
				break;
			case 48://���� ������
				s.SP(player, Sound.ITEM_PICKUP, 1.0F, 0.8F);
				SelectUpgradeRecipeGUI(player, (short) (page-1),NPCname);
				break;
			case 50://���� ������
				s.SP(player, Sound.ITEM_PICKUP, 1.0F, 0.8F);
				SelectUpgradeRecipeGUI(player, (short) (page+1),NPCname);
				break;
			case 53://������
				s.SP(player, Sound.PISTON_RETRACT, 0.8F, 1.8F);
				player.closeInventory();
				return;
			default:
			{
				String RecipeName = ChatColor.stripColor(event.getCurrentItem().getItemMeta().getDisplayName());
				NPCConfig.set("Job.UpgradeRecipe."+RecipeName, 2147483647);
				player.sendMessage("[NPC] : ������ �������� ���� ����� �Է� �� �ּ���!");
				player.sendMessage(ChatColor.GREEN + "("+ChatColor.YELLOW +"0"+ChatColor.GREEN+" ~ "+ChatColor.YELLOW+""+Integer.MAX_VALUE+ChatColor.GREEN+")");
				u.setType(player, "NPC");
				u.setString(player, (byte)4,"NUC");
				u.setString(player, (byte)6,RecipeName);
				u.setString(player, (byte)8,NPCname);
				player.closeInventory();
			}	
		}
	}
	
	public ItemStack Calculator(ItemStack item, String WeaponOption, int PlusOption)
	{
		String Option = "null";
		switch(WeaponOption)
		{
		case "Durability":
			Option = "������";
			break;
		case "MinDamage":
			Option = "�Ҵ�";
			break;
		case "MaxDamage":
			Option = "�ƴ�";
			break;
		case "MinMaDamage":
			Option = "���Ҵ�";
			break;
		case "MaxMaDamage":
			Option = "���ƴ�";
			break;
		case "DEF":
			Option = "���";
			break;
		case "Protect":
			Option = "��ȣ";
			break;
		case "MaDEF":
			Option = "���� ���";
			break;
		case "MaProtect":
			Option = "���� ��ȣ";
			break;
		case "Critical":
			Option = "ũ��Ƽ��";
			break;
		case "Balance":
			Option = "�뷱��";
			break;
		case "Proficiency":
			Option = "���õ�";
			break;
		case "UpgradeLevelIncrease":
			Option = "����";
			break;
		case "NowDura":
			Option = "���系����";
			break;
		case "HP":
			Option = "�����";
			break;
		case "MP":
			Option = "����";
			break;
		case "STR":
			Option = ""+ServerOption.STR+"";
			break;
		case "DEX":
			Option = ""+ServerOption.DEX+"";
			break;
		case "INT":
			Option = ""+ServerOption.INT+"";
			break;
		case "WILL":
			Option = ""+ServerOption.WILL+"";
			break;
		case "LUK":
			Option = ""+ServerOption.LUK+"";
			break;
		}
		ItemMeta IM = item.getItemMeta();
		List<String> Lore = item.getItemMeta().getLore();
		for(byte count = 0; count < Lore.size(); count++)
		{
			String SliceOfLore = Lore.get(count);
			if(SliceOfLore.contains(Option) == true||(SliceOfLore.contains(ServerOption.Damage)&&!(SliceOfLore.contains("����"))&&(Option.compareTo("�Ҵ�")==0))||
					(SliceOfLore.contains(ServerOption.Damage)&&!(SliceOfLore.contains("����"))&&(Option.compareTo("�ƴ�")==0))||(SliceOfLore.contains(ServerOption.MagicDamage)&&(Option.compareTo("���Ҵ�")==0))||
					(SliceOfLore.contains(ServerOption.MagicDamage)&&(Option.compareTo("���ƴ�")==0))
					||(Option.compareTo("���系����")==0&&SliceOfLore.contains("������")))
			{
				String WillBeLore=" ";
				if(SliceOfLore.contains(" : ") == true)
				{
					switch(Option)
					{
					case "���系����":
						if(SliceOfLore.contains("������")&&!SliceOfLore.contains(ServerOption.Damage))
						{
							int low = Integer.parseInt(ChatColor.stripColor(SliceOfLore.split(" : ")[1].split(" / ")[0]));
							int max = Integer.parseInt(ChatColor.stripColor(SliceOfLore.split(" : ")[1].split(" / ")[1]));
							if((low+PlusOption)  <= max)
								WillBeLore = SliceOfLore.split(" : ")[0] + " : "+ChatColor.WHITE+(low+ PlusOption)  + " / "+ max;
							else
								WillBeLore = SliceOfLore.split(" : ")[0] + " : "+ChatColor.WHITE+max  + " / "+ max;
						}
						break;
					case "������":
						if(SliceOfLore.contains("������")&&!SliceOfLore.contains(ServerOption.Damage))
						{
							if(Integer.parseInt(ChatColor.stripColor(SliceOfLore.split(" : ")[1].split(" / ")[0])) <= (Integer.parseInt(ChatColor.stripColor(SliceOfLore.split(" : ")[1].split(" / ")[1])) + PlusOption))
								WillBeLore = SliceOfLore.split(" : ")[0] + " : "+ChatColor.WHITE+Integer.parseInt(ChatColor.stripColor(SliceOfLore.split(" : ")[1].split(" / ")[0])) + " / "+ (Integer.parseInt(ChatColor.stripColor(SliceOfLore.split(" : ")[1].split(" / ")[1])) + PlusOption);
							else if((Integer.parseInt(SliceOfLore.split(" : ")[1].split(" / ")[1]) + PlusOption) != 0)
								WillBeLore = SliceOfLore.split(" : ")[0] + " : "+ ChatColor.WHITE+(Integer.parseInt(ChatColor.stripColor(SliceOfLore.split(" : ")[1].split(" / ")[1])) + PlusOption) + " / "+ (Integer.parseInt(ChatColor.stripColor(SliceOfLore.split(" : ")[1].split(" / ")[1])) + PlusOption);
							else
								WillBeLore = SliceOfLore.split(" : ")[0] + ChatColor.WHITE+" : 0 / 0";
						}
						break;
					case "�Ҵ�":
						if(SliceOfLore.contains(ServerOption.Damage))
						{
							if(Integer.parseInt(ChatColor.stripColor(SliceOfLore.split(" : ")[1].split(" ~ ")[0])) + PlusOption > 0)
								WillBeLore = SliceOfLore.split(" : ")[0] + " : "+ChatColor.WHITE+(Integer.parseInt(ChatColor.stripColor(SliceOfLore.split(" : ")[1].split(" ~ ")[0]))+ PlusOption)+ " ~ "+Integer.parseInt(ChatColor.stripColor(SliceOfLore.split(" : ")[1].split(" ~ ")[1]));
							else
								WillBeLore = SliceOfLore.split(" : ")[0] +ChatColor.WHITE+ " : 0 ~ "+Integer.parseInt(ChatColor.stripColor(SliceOfLore.split(" : ")[1].split(" ~ ")[1]));
						}
						break;
					case "���Ҵ�":
						if(SliceOfLore.contains(ServerOption.MagicDamage))
						{
							if(Integer.parseInt(ChatColor.stripColor(SliceOfLore.split(" : ")[1].split(" ~ ")[0])) + PlusOption > 0)
								WillBeLore = SliceOfLore.split(" : ")[0] + " : "+ChatColor.WHITE+(Integer.parseInt(ChatColor.stripColor(SliceOfLore.split(" : ")[1].split(" ~ ")[0]))+ PlusOption)+ " ~ "+Integer.parseInt(ChatColor.stripColor(SliceOfLore.split(" : ")[1].split(" ~ ")[1]));
							else
								WillBeLore = SliceOfLore.split(" : ")[0] +ChatColor.WHITE+ " : 0 ~ "+Integer.parseInt(ChatColor.stripColor(SliceOfLore.split(" : ")[1].split(" ~ ")[1]));
						}
						break;
					case "�ƴ�":
						if(SliceOfLore.contains(ServerOption.Damage))
						{
							if(Integer.parseInt(ChatColor.stripColor(SliceOfLore.split(" : ")[1].split(" ~ ")[1])) + PlusOption > 0)
								WillBeLore = SliceOfLore.split(" : ")[0] + " : "+ChatColor.WHITE+Integer.parseInt(ChatColor.stripColor(SliceOfLore.split(" : ")[1].split(" ~ ")[0])) + " ~ "+ (Integer.parseInt(ChatColor.stripColor(SliceOfLore.split(" : ")[1].split(" ~ ")[1])) + PlusOption);
							else
								WillBeLore = SliceOfLore.split(" : ")[0] + " : "+ChatColor.WHITE+Integer.parseInt(ChatColor.stripColor(SliceOfLore.split(" : ")[1].split(" ~ ")[0])) + " ~ 0";
						}
						break;
					case "���ƴ�":
						if(SliceOfLore.contains(ServerOption.MagicDamage))
						{
							if(Integer.parseInt(ChatColor.stripColor(SliceOfLore.split(" : ")[1].split(" ~ ")[1])) + PlusOption > 0)
								WillBeLore = SliceOfLore.split(" : ")[0] + " : "+ChatColor.WHITE+Integer.parseInt(ChatColor.stripColor(SliceOfLore.split(" : ")[1].split(" ~ ")[0])) + " ~ "+ (Integer.parseInt(ChatColor.stripColor(SliceOfLore.split(" : ")[1].split(" ~ ")[1])) + PlusOption);
							else
								WillBeLore = SliceOfLore.split(" : ")[0] + " : "+ChatColor.WHITE+Integer.parseInt(ChatColor.stripColor(SliceOfLore.split(" : ")[1].split(" ~ ")[0])) + " ~ 0";
						}
						break;
					case "���õ�":
						DecimalFormat format = new DecimalFormat(".##");
						String str = "0";
						if((Float.parseFloat(ChatColor.stripColor(SliceOfLore.split(" : ")[1].split("%")[0]))-PlusOption) > 0)
							str = format.format((Float.parseFloat(ChatColor.stripColor(SliceOfLore.split(" : ")[1].split("%")[0]))-PlusOption));
						else
							str = "0.0";
						WillBeLore = SliceOfLore.split(" : ")[0] + " : "+ChatColor.WHITE+str +"%"+ChatColor.WHITE+"";
						break;
					case "����":
						WillBeLore = SliceOfLore.split(" : ")[0] + " : "+ ChatColor.WHITE+(Integer.parseInt(ChatColor.stripColor(SliceOfLore.split(" : ")[1].split(" / ")[0]))+ 1) + " / "+ (Integer.parseInt(ChatColor.stripColor(SliceOfLore.split(" : ")[1].split(" / ")[1])));
						break;
					default:
						if(!SliceOfLore.contains("������"))
							WillBeLore = SliceOfLore.split(" : ")[0] + " : "+ChatColor.WHITE+(Integer.parseInt(ChatColor.stripColor(SliceOfLore.split(" : ")[1])) + PlusOption);
						break;
					}
					Lore.set(count, WillBeLore);
					IM.setLore(Lore);
					item.setItemMeta(IM);
					return item;
				}
			}
		}
		switch(Option)
		{
		case "�Ҵ�":
			Lore.add(2, ChatColor.WHITE+" "+ServerOption.Damage+" : " + PlusOption + " ~ 0");
			break;
		case "���Ҵ�":
			Lore.add(3, ChatColor.WHITE+" "+ServerOption.MagicDamage+" : " + PlusOption + " ~ 0");
			break;
		case "�ƴ�":
			Lore.add(2, ChatColor.WHITE+" "+ServerOption.Damage+" : 0 ~ "+PlusOption);
			break;
		case "���ƴ�":
			Lore.add(3, ChatColor.WHITE+" "+ServerOption.MagicDamage+" : 0 ~ "+PlusOption);
			break;
		default:
			Lore.add(4, " " +ChatColor.WHITE+ Option + " : " + PlusOption);
			break;
		}
		IM.setLore(Lore);
		item.setItemMeta(IM);
		return item;
	}
	
	public void RuneEquipGUIClick(InventoryClickEvent event)
	{
		String NPCname = ChatColor.stripColor(event.getInventory().getItem(26).getItemMeta().getLore().get(0));

		Object_UserData u = new Object_UserData();
		Player player = (Player) event.getWhoClicked();
		YamlController YC = new YamlController(GoldBigDragon_RPG.Main.Main.plugin);
		YamlManager NPCConfig =YC.getNewConfig("NPC/NPCData/"+u.getNPCuuid(player)+".yml");
		GoldBigDragon_RPG.Effect.Sound s = new GoldBigDragon_RPG.Effect.Sound();

		switch (event.getSlot())
		{
		case 13:
			return;
			case 10://���� �������
				s.SP(player, Sound.ITEM_PICKUP, 1.0F, 0.8F);
				event.setCancelled(true);
				MainGUI(player, NPCname, player.isOp());
				return;
			case 16://�� ����
				event.setCancelled(true);
				int SuccessRate = NPCConfig.getInt("Job.SuccessRate");
				boolean Success = false;
				if(event.getInventory().getItem(13)!=null)
				{
					if(event.getInventory().getItem(13).hasItemMeta()==true)
					{
						if(event.getInventory().getItem(13).getItemMeta().hasLore()==true)
						{
							if(event.getInventory().getItem(13).getItemMeta().getLore().toString().contains("[��]"))
							{
								if(player.getInventory().getItemInHand()!=null)
								{
									if(player.getInventory().getItemInHand().hasItemMeta()==true)
									{
										if(player.getInventory().getItemInHand().getItemMeta().hasLore()==true)
										{
											int Pay = NPCConfig.getInt("Job.Deal");
											if(GoldBigDragon_RPG.Main.ServerOption.PlayerList.get(player.getUniqueId().toString()).getStat_Money() >= Pay)
											{
												ItemStack item = player.getInventory().getItemInHand();
												int MinDamage = 0;
												int MaxDamage = 0;
												int MinMaDamage = 0;
												int MaxMaDamage = 0;
												int DEF = 0;
												int Protect = 0;
												int MaDEF = 0;
												int MaProtect = 0;
												int Balance = 0;
												int Critical = 0;
												int NowDura = 0;
												int MaxDura = 0;
												int HP = 0;
												int MP = 0;
												int STR = 0;
												int DEX = 0;
												int INT = 0;
												int WILL = 0;
												int LUK = 0;
												for(byte count = 0; count < event.getInventory().getItem(13).getItemMeta().getLore().size();count++)
												{
													if(event.getInventory().getItem(13).getItemMeta().getLore().get(count).contains(" : "))
													{
														String Rune = event.getInventory().getItem(13).getItemMeta().getLore().get(count);
														if(Rune.contains("���ݷ�"))
														{
															if(Rune.contains("����"))
															{
																if(Rune.contains("�ּ�"))
																	MinMaDamage = Integer.parseInt(Rune.split(" : ")[1]);
																else
																	MaxMaDamage = Integer.parseInt(Rune.split(" : ")[1]);
															}
															else
															{
																if(Rune.contains("�ּ�"))
																	MinDamage = Integer.parseInt(Rune.split(" : ")[1]);
																else
																	MaxDamage = Integer.parseInt(Rune.split(" : ")[1]);
															}
														}
														else if(Rune.contains("���"))
														{
															if(Rune.contains("����"))
																MaDEF = Integer.parseInt(Rune.split(" : ")[1]);
															else
																DEF = Integer.parseInt(Rune.split(" : ")[1]);
														}
														else if(Rune.contains("��ȣ"))
														{
															if(Rune.contains("����"))
																MaProtect = Integer.parseInt(Rune.split(" : ")[1]);
															else
																Protect = Integer.parseInt(Rune.split(" : ")[1]);
														}
														else if(Rune.contains("������"))
														{
															if(Rune.contains("����"))
																NowDura = Integer.parseInt(Rune.split(" : ")[1]);
															else
																MaxDura = Integer.parseInt(Rune.split(" : ")[1]);
														}
														else if(Rune.contains("�뷱��"))
															Balance = Integer.parseInt(Rune.split(" : ")[1]);
														else if(Rune.contains("ũ��Ƽ��"))
															Critical = Integer.parseInt(Rune.split(" : ")[1]);
														else if(Rune.contains("�����"))
															HP = Integer.parseInt(Rune.split(" : ")[1]);
														else if(Rune.contains("����"))
															MP = Integer.parseInt(Rune.split(" : ")[1]);
														else if(Rune.contains(""+ServerOption.STR+""))
															STR = Integer.parseInt(Rune.split(" : ")[1]);
														else if(Rune.contains(""+ServerOption.DEX+""))
															DEX = Integer.parseInt(Rune.split(" : ")[1]);
														else if(Rune.contains(""+ServerOption.INT+""))
															INT = Integer.parseInt(Rune.split(" : ")[1]);
														else if(Rune.contains(""+ServerOption.WILL+""))
															WILL = Integer.parseInt(Rune.split(" : ")[1]);
														else if(Rune.contains(""+ServerOption.LUK+""))
															LUK = Integer.parseInt(Rune.split(" : ")[1]);
													}
												}
												
												for(int count = 0; count < item.getItemMeta().getLore().size();count++)
												{
													if(item.getItemMeta().getLore().get(count).contains("��") == true)
													{
														if(item.getItemMeta().getLore().get(count).contains(" : ") == true)
														{
															if(item.getItemMeta().getLore().get(count).contains("��") == true)
															{
																String Lore = item.getItemMeta().getLore().get(count).split(" : ")[1];
																int EmptyCircle = 0;
																String Circle = "";
																for(short k = 0; k < Lore.split(" ").length;k++)
																{
																	if(Lore.split(" ")[k].contains("��")==true)
																	{
																		EmptyCircle = k;
																		break;
																	}
																}
																Number n = new Number();
																if(n.RandomNum(1, 100)<= SuccessRate)
																{
																	for(short k = 0; k < Lore.split(" ").length;k++)
																	{
																		if(k != EmptyCircle)
																			Circle = Circle+Lore.split(" ")[k]+" ";
																		else
																			Circle = Circle+ChatColor.BLUE+"�� ";
																	}
																	Lore =  item.getItemMeta().getLore().get(count).split(" : ")[0] + " : " + Circle;
																	player.sendMessage(ChatColor.BLUE+"[�� ����] : �����ۿ� ���� �����Ͽ����ϴ�!");
																	s.SP(player, Sound.LEVEL_UP, 1.0F, 0.5F);
																	Success = true;
																}
																else
																{
																	for(short k = 0; k < Lore.split(" ").length;k++)
																	{
																		if(k != EmptyCircle)
																			Circle = Circle+Lore.split(" ")[k]+" ";
																		else
																			Circle = Circle+ChatColor.RED+"�� ";
																	}
																	Lore =  item.getItemMeta().getLore().get(count).split(" : ")[0] + " : " + Circle;
																	player.sendMessage(ChatColor.RED+"[�� ����] : �� ���� ����!!!");
																	player.sendMessage(ChatColor.RED+"[�������� �� ������ �ı��Ǿ����ϴ�!]");
																	s.SP(player, Sound.ANVIL_BREAK, 1.0F, 1.1F);
																}
																if(event.getInventory().getItem(13).getAmount()==1)
																	event.getInventory().setItem(13, new ItemStack(0));
																else
																	event.getInventory().getItem(13).setAmount(event.getInventory().getItem(13).getAmount()-1);
														  		GoldBigDragon_RPG.Main.ServerOption.PlayerList.get(player.getUniqueId().toString()).addStat_MoneyAndEXP(-1 * Pay, 0, false);

																ItemStack ii = item;
																ItemMeta IM = item.getItemMeta();
																String LL = "";
																for(byte k = 0; k < item.getItemMeta().getLore().size();k++)
																{
																	if(item.getItemMeta().getLore().get(k).contains("��") == true&&item.getItemMeta().getLore().get(k).contains(" : ") == true)
																	{
																		if(k < item.getItemMeta().getLore().size()-1)
																			LL = LL+Lore+"%enter%";
																		else
																			LL = LL+Lore;
																	}
																	else
																		LL = LL+item.getItemMeta().getLore().get(k) + "%enter%";
																}
																String[] scriptA = LL.split("%enter%");
																IM.setLore(Arrays.asList(scriptA));
																ii.setItemMeta(IM);
																player.getInventory().setItemInHand(ii);

																if(Success == true)
																{
																	ii = player.getInventory().getItemInHand();
																	if(MaxDura!=0)
																		ii = Calculator(ii, "Durability", MaxDura);
																	if(MinDamage!=0)
																		ii = Calculator(ii, "MinDamage", MinDamage);
																	if(MaxDamage!=0)
																		ii = Calculator(ii, "MaxDamage", MaxDamage);
																	if(MinMaDamage!=0)
																		ii = Calculator(ii, "MinMaDamage", MinMaDamage);
																	if(MaxMaDamage!=0)
																		ii = Calculator(ii, "MaxMaDamage", MaxMaDamage);
																	if(DEF!=0)
																		ii = Calculator(ii, "DEF", DEF);
																	if(Protect!=0)
																		ii = Calculator(ii, "Protect", Protect);
																	if(MaDEF!=0)
																		ii = Calculator(ii, "MaDEF", MaDEF);
																	if(MaProtect!=0)
																		ii = Calculator(ii, "MaProtect", MaProtect);
																	if(Critical!=0)
																		ii = Calculator(ii, "Critical", Critical);
																	if(Balance!=0)
																		ii = Calculator(ii, "Balance", Balance);
																	if(HP!=0)
																		ii = Calculator(ii, "HP", HP);
																	if(MP!=0)
																		ii = Calculator(ii, "MP", MP);
																	if(STR!=0)
																		ii = Calculator(ii, "STR", STR);
																	if(DEX!=0)
																		ii = Calculator(ii, "DEX", DEX);
																	if(INT!=0)
																		ii = Calculator(ii, "INT", INT);
																	if(WILL!=0)
																		ii = Calculator(ii, "WILL", WILL);
																	if(LUK!=0)
																		ii = Calculator(ii, "LUK", LUK);
																	if(NowDura!=0)
																		ii = Calculator(ii, "NowDura", NowDura);
																}
																return;
															}
														}
													}
												}
												player.sendMessage(ChatColor.RED+"[�� ����] : ���� ���� ��ų �� �ִ� ���� ������ �����ϴ�!");
												s.SP(player, Sound.ORB_PICKUP, 1.0F, 1.8F);
											}
											else
											{
												player.sendMessage(ChatColor.RED+"[�� ����] : �������� �����մϴ�!");
												s.SP(player, Sound.ORB_PICKUP, 1.0F, 1.8F);
											}
										}
										else
										{
											player.sendMessage(ChatColor.RED+"[�� ����] : ���� ������ų �� ���� ������ �Դϴ�!");
											s.SP(player, Sound.ORB_PICKUP, 1.0F, 1.8F);
										}
										
									}
									else
									{
										player.sendMessage(ChatColor.RED+"[�� ����] : ���� ������ų �� ���� ������ �Դϴ�!");
										s.SP(player, Sound.ORB_PICKUP, 1.0F, 1.8F);
									}
								}
								else
								{
									player.sendMessage(ChatColor.RED+"[�� ����] : �տ� �������� �����ϰ� �־�� �մϴ�!");
									s.SP(player, Sound.ORB_PICKUP, 1.0F, 1.8F);
								}
							}
							else
							{
								player.sendMessage(ChatColor.RED+"[�� ����] : ���� �ø� �������� ���� �ƴմϴ�!");
								s.SP(player, Sound.ORB_PICKUP, 1.0F, 1.8F);
							}
						}
						else
						{
							player.sendMessage(ChatColor.RED+"[�� ����] : ���� �ø� �������� ���� �ƴմϴ�!");
							s.SP(player, Sound.ORB_PICKUP, 1.0F, 1.8F);
						}
					}
					else
					{
						player.sendMessage(ChatColor.RED+"[�� ����] : ���� �ø� �������� ���� �ƴմϴ�!");
						s.SP(player, Sound.ORB_PICKUP, 1.0F, 1.8F);
					}
				}
				else
				{
					player.sendMessage(ChatColor.RED+"[�� ����] : ���� ��ų ���� �÷� �ּ���!");
					s.SP(player, Sound.ORB_PICKUP, 1.0F, 1.8F);
				}
				return;
			default :
				event.setCancelled(true);
				return;
		}
	}

	public void TalkGUIClick(InventoryClickEvent event)
	{
		short page =  (short) (Short.parseShort(event.getInventory().getTitle().split(" : ")[1])-1);
		String TalkType = ChatColor.stripColor(event.getInventory().getItem(45).getItemMeta().getLore().get(1));
		String NPCname = ChatColor.stripColor(event.getInventory().getItem(53).getItemMeta().getLore().get(1));

		Object_UserData u = new Object_UserData();
		Player player = (Player) event.getWhoClicked();
		YamlController YC = new YamlController(GoldBigDragon_RPG.Main.Main.plugin);
		YamlManager NPCConfig =YC.getNewConfig("NPC/NPCData/"+u.getNPCuuid(player)+".yml");
		GoldBigDragon_RPG.Effect.Sound s = new GoldBigDragon_RPG.Effect.Sound();

		event.setCancelled(true);
		switch (event.getSlot())
		{
		case 45:
			s.SP(player, Sound.ITEM_PICKUP, 1.0F, 1.0F);
			MainGUI(player, NPCname, player.isOp());
			break;
		case 46:
			s.SP(player, Sound.ITEM_PICKUP, 1.0F, 1.0F);
			switch(TalkType)
			{
			case "NT"://NatureTalk
				NPCTalkGUI(player, (short) 0, NPCname, "NN");
				break;
			case "NN"://NearbyNews
				NPCTalkGUI(player, (short) 0, NPCname, "AS");
				break;
			case "AS"://AboutSkill
				NPCTalkGUI(player, (short) 0, NPCname, "NT");
				break;
			}
			break;
		case 48:
			s.SP(player, Sound.ITEM_PICKUP, 1.0F, 1.8F);
			NPCTalkGUI(player, (short) (page-1), NPCname, TalkType);
			break;
		case 49://��� �߰�
			s.SP(player, Sound.ITEM_PICKUP, 1.0F, 1.8F);
			short number = 1;
			switch(TalkType)
			{
			case "NT"://NatureTalk
				number = (short) (NPCConfig.getConfigurationSection("NatureTalk").getKeys(false).toArray().length+1);
				NPCConfig.set("NatureTalk."+number+".love", 0);
				NPCConfig.set("NatureTalk."+number+".Script", ChatColor.WHITE+"��� ����");
				break;
			case "NN"://NearbyNews
				number = (short) (NPCConfig.getConfigurationSection("NearByNEWS").getKeys(false).toArray().length+1);
				NPCConfig.set("NearByNEWS."+number+".love", 0);
				NPCConfig.set("NearByNEWS."+number+".Script", ChatColor.WHITE+"��� ����");
				break;
			case "AS"://AboutSkill
				number = (short) (NPCConfig.getConfigurationSection("AboutSkills").getKeys(false).toArray().length+1);
				NPCConfig.set("AboutSkills."+number+".love", 0);
				NPCConfig.set("AboutSkills."+number+".giveSkill", "null");
				NPCConfig.set("AboutSkills."+number+".Script", ChatColor.WHITE+"��� ����");
				NPCConfig.set("AboutSkills."+number+".AlreadyGetScript", "��� ����");
				break;
			}
			NPCConfig.saveConfig();
			NPCTalkGUI(player, page, NPCname, TalkType);
			break;
		case 50:
			s.SP(player, Sound.ITEM_PICKUP, 1.0F, 1.8F);
			NPCTalkGUI(player, (short) (page+1), NPCname, TalkType);
			break;
		case 52:
			s.SP(player, Sound.ITEM_PICKUP, 1.0F, 1.0F);
			switch(TalkType)
			{
			case "NT"://NatureTalk
				NPCTalkGUI(player, (short) 0, NPCname, "AS");
				break;
			case "NN"://NearbyNews
				NPCTalkGUI(player, (short) 0, NPCname, "NT");
				break;
			case "AS"://AboutSkill
				NPCTalkGUI(player, (short) 0, NPCname, "NN");
				break;
			}
			break;
		case 53:
			s.SP(player, Sound.PISTON_RETRACT, 1.0F, 1.8F);
			player.closeInventory();
			break;
		default:
			short Number = Short.parseShort(ChatColor.stripColor(event.getCurrentItem().getItemMeta().getDisplayName()));
			if(event.isRightClick()&&event.isShiftClick())
			{
				s.SP(player, Sound.LAVA_POP, 1.0F, 1.0F);
				Short Acount = 0;
				switch(TalkType)
				{
				case "NT"://NatureTalk
					Acount =  (short) NPCConfig.getConfigurationSection("NatureTalk").getKeys(false).toArray().length;
					for(short counter = Number;counter <Acount;counter++)
					{
						NPCConfig.set("NatureTalk."+counter+".love", NPCConfig.getInt("NatureTalk."+(counter+1)+".love"));
						NPCConfig.set("NatureTalk."+counter+".Script", NPCConfig.getString("NatureTalk."+(counter+1)+".Script"));
					}
					NPCConfig.removeKey("NatureTalk."+Acount);
					NPCConfig.saveConfig();
					NPCTalkGUI(player, page, NPCname, TalkType);
					break;
				case "NN"://NearbyNews
					Acount =  (short) NPCConfig.getConfigurationSection("NearByNEWS").getKeys(false).toArray().length;
					for(short counter = Number;counter <Acount;counter++)
					{
						NPCConfig.set("NearByNEWS."+counter+".love", NPCConfig.getInt("NearByNEWS."+(counter+1)+".love"));
						NPCConfig.set("NearByNEWS."+counter+".Script", NPCConfig.getString("NearByNEWS."+(counter+1)+".Script"));
					}
					NPCConfig.removeKey("NearByNEWS."+Acount);
					NPCConfig.saveConfig();
					NPCTalkGUI(player, page, NPCname, TalkType);
					break;
				case "AS"://AboutSkill
					Acount =  (short) NPCConfig.getConfigurationSection("AboutSkills").getKeys(false).toArray().length;
					for(short counter = Number;counter <Acount;counter++)
					{
						NPCConfig.set("AboutSkills."+counter+".love", NPCConfig.getInt("AboutSkills."+(counter+1)+".love"));
						NPCConfig.set("AboutSkills."+counter+".giveSkill", NPCConfig.getInt("AboutSkills."+(counter+1)+".giveSkill"));
						NPCConfig.set("AboutSkills."+counter+".Script", NPCConfig.getString("AboutSkills."+(counter+1)+".Script"));
						NPCConfig.set("AboutSkills."+counter+".AlreadyGetScript", NPCConfig.getString("AboutSkills."+(counter+1)+".AlreadyGetScript"));
					}
					NPCConfig.removeKey("AboutSkills."+Acount);
					NPCConfig.saveConfig();
					NPCTalkGUI(player, page, NPCname, TalkType);
					break;
				}
			}
			else
			{
				s.SP(player, Sound.ITEM_PICKUP, 1.0F, 1.0F);
				TalkSettingGUI(player, NPCname, TalkType, Number);
			}
			break;
		}
	}
	
	public void TalkSettingGUIClick(InventoryClickEvent event)
	{
		String TalkType = ChatColor.stripColor(event.getInventory().getItem(27).getItemMeta().getLore().get(1));
		String NPCname = ChatColor.stripColor(event.getInventory().getItem(35).getItemMeta().getLore().get(1));

		short number =  Short.parseShort(ChatColor.stripColor(event.getInventory().getItem(10).getItemMeta().getDisplayName()));
		Player player = (Player) event.getWhoClicked();
		YamlController YC = new YamlController(GoldBigDragon_RPG.Main.Main.plugin);
		GoldBigDragon_RPG.Effect.Sound s = new GoldBigDragon_RPG.Effect.Sound();

		event.setCancelled(true);
		switch (event.getSlot())
		{
		case 13://ȣ����
			{
				s.SP(player, Sound.ITEM_PICKUP, 1.0F, 1.0F);
				player.closeInventory();
				player.sendMessage(ChatColor.DARK_AQUA+"[���] : �� ��縦 ���� ���ؼ��� �ּ� ���� ȣ������ �ʿ��Ѱ���?");
				player.sendMessage(ChatColor.GREEN + "("+ChatColor.YELLOW + "0"+ChatColor.GREEN+" ~ "+ChatColor.YELLOW+""+Integer.MAX_VALUE+ChatColor.GREEN+")");
				Object_UserData u = new Object_UserData();
				u.setType(player, "NPC");
				u.setString(player, (byte)2,NPCname);
				u.setString(player, (byte)3,u.getNPCuuid(player));
				u.setString(player, (byte)4,"NPC_TNL");
				u.setString(player, (byte)5,TalkType);
				u.setString(player, (byte)6,number+"");
			}
			return;
		case 14://���
			{	s.SP(player, Sound.ITEM_PICKUP, 1.0F, 1.0F);
				player.closeInventory();
				player.sendMessage(ChatColor.DARK_AQUA+"[���] : NPC�� �� ��縦 �Է��� �ּ���!");
				player.sendMessage(ChatColor.GOLD + "%enter%"+ChatColor.WHITE + " - ���� ��� ���� -");
				player.sendMessage(ChatColor.GOLD + "%player%"+ChatColor.WHITE + " - �÷��̾� ��Ī�ϱ� -");
				player.sendMessage(ChatColor.WHITE + ""+ChatColor.BOLD + "&l " + ChatColor.BLACK + "&0 "+ChatColor.DARK_BLUE+"&1 "+ChatColor.DARK_GREEN+"&2 "+
				ChatColor.DARK_AQUA + "&3 " +ChatColor.DARK_RED + "&4 " + ChatColor.DARK_PURPLE + "&5 " +
						ChatColor.GOLD + "&6 " + ChatColor.GRAY + "&7 " + ChatColor.DARK_GRAY + "&8 " +
				ChatColor.BLUE + "&9 " + ChatColor.GREEN + "&a " + ChatColor.AQUA + "&b " + ChatColor.RED + "&c " +
						ChatColor.LIGHT_PURPLE + "&d " + ChatColor.YELLOW + "&e "+ChatColor.WHITE + "&f");
				Object_UserData u = new Object_UserData();
				u.setType(player, "NPC");
				u.setString(player, (byte)2,NPCname);
				u.setString(player, (byte)3,u.getNPCuuid(player));
				u.setString(player, (byte)4,"NPC_TS");
				u.setString(player, (byte)5,TalkType);
				u.setString(player, (byte)6,number+"");
			}
			return;
		case 15://��ų
			s.SP(player, Sound.ITEM_PICKUP, 1.0F, 1.0F);
			YamlManager Config =YC.getNewConfig("config.yml");
			if(Config.getBoolean("Server.Like_The_Mabinogi_Online_Stat_System") == true)
			{
				YamlManager SkillList =YC.getNewConfig("Skill/JobList.yml");
				if(SkillList.getConfigurationSection("Mabinogi").getKeys(false).toArray().length >= 0)
				{
					if(SkillList.getConfigurationSection("Mabinogi.Added").getKeys(false).toArray().length >= 0)
					{
						AddAbleSkillsGUI(player, (short) 0, NPCname,number);
						return;
					}
					else
					{
						s.SP(player, Sound.ITEM_PICKUP, 1.0F, 1.0F);
						player.sendMessage(ChatColor.RED+"[SYSTEM] : ��� ������ ��ų�� �����ϴ�!");
					}
				}
				else
				{
					s.SP(player, Sound.ITEM_PICKUP, 1.0F, 1.0F);
					player.sendMessage(ChatColor.RED+"[SYSTEM] : ��� ������ ��ų�� �����ϴ�!");
				}
			}
			else
			{
				s.SP(player, Sound.ITEM_PICKUP, 1.0F, 1.0F);
				player.sendMessage(ChatColor.RED+"[SYSTEM] : ���� �ý����� ������ ������ �ƴմϴ�!");
			}
			break;
		case 16://���2
			{
				s.SP(player, Sound.ITEM_PICKUP, 1.0F, 1.0F);
				player.closeInventory();
				player.sendMessage(ChatColor.DARK_AQUA+"[���] : �÷��̾�� ��ų�� ������ ��, NPC�� �� ��縦 �Է��� �ּ���!");
				player.sendMessage(ChatColor.GOLD + "%enter%"+ChatColor.WHITE + " - ���� ��� ���� -");
				player.sendMessage(ChatColor.GOLD + "%player%"+ChatColor.WHITE + " - �÷��̾� ��Ī�ϱ� -");
				player.sendMessage(ChatColor.WHITE + ""+ChatColor.BOLD + "&l " + ChatColor.BLACK + "&0 "+ChatColor.DARK_BLUE+"&1 "+ChatColor.DARK_GREEN+"&2 "+
				ChatColor.DARK_AQUA + "&3 " +ChatColor.DARK_RED + "&4 " + ChatColor.DARK_PURPLE + "&5 " +
						ChatColor.GOLD + "&6 " + ChatColor.GRAY + "&7 " + ChatColor.DARK_GRAY + "&8 " +
				ChatColor.BLUE + "&9 " + ChatColor.GREEN + "&a " + ChatColor.AQUA + "&b " + ChatColor.RED + "&c " +
						ChatColor.LIGHT_PURPLE + "&d " + ChatColor.YELLOW + "&e "+ChatColor.WHITE + "&f");
				Object_UserData u = new Object_UserData();
				u.setType(player, "NPC");
				u.setString(player, (byte)2,NPCname);
				u.setString(player, (byte)3,u.getNPCuuid(player));
				u.setString(player, (byte)4,"NPC_TS2");
				u.setString(player, (byte)5,TalkType);
				u.setString(player, (byte)6,number+"");
			}
			return;
		case 27:
			s.SP(player, Sound.ITEM_PICKUP, 1.0F, 1.0F);
			NPCTalkGUI(player,(short) (number/45), NPCname, TalkType);
			break;
		case 35:
			s.SP(player, Sound.PISTON_RETRACT, 1.0F, 1.8F);
			player.closeInventory();
			break;
		}
	}
	
	public void AddAbleSkillsGUIClick(InventoryClickEvent event)
	{
		short page =  (short) (Short.parseShort(event.getInventory().getTitle().split(" : ")[1])-1);
		String TalkType = "AS";
		String NPCname = ChatColor.stripColor(event.getInventory().getItem(53).getItemMeta().getLore().get(1));
		short number =  Short.parseShort(ChatColor.stripColor(event.getInventory().getItem(45).getItemMeta().getLore().get(1)));

		Object_UserData u = new Object_UserData();
		Player player = (Player) event.getWhoClicked();
		YamlController YC = new YamlController(GoldBigDragon_RPG.Main.Main.plugin);
		YamlManager NPCConfig =YC.getNewConfig("NPC/NPCData/"+u.getNPCuuid(player)+".yml");
		GoldBigDragon_RPG.Effect.Sound s = new GoldBigDragon_RPG.Effect.Sound();

		event.setCancelled(true);
		switch (event.getSlot())
		{
		case 45:
			s.SP(player, Sound.ITEM_PICKUP, 1.0F, 1.0F);
			TalkSettingGUI(player, NPCname, TalkType,number);
			break;
		case 48:
			s.SP(player, Sound.ITEM_PICKUP, 1.0F, 1.8F);
			AddAbleSkillsGUI(player, (short) (page-1), NPCname, number);
			break;
		case 50:
			s.SP(player, Sound.ITEM_PICKUP, 1.0F, 1.8F);
			AddAbleSkillsGUI(player, (short) (page+1), NPCname, number);
			break;
		case 53:
			s.SP(player, Sound.PISTON_RETRACT, 1.0F, 1.8F);
			player.closeInventory();
			break;
		default:
			s.SP(player, Sound.ITEM_PICKUP, 1.0F, 1.8F);
			NPCConfig.set("AboutSkills."+number+".giveSkill", ChatColor.stripColor(event.getCurrentItem().getItemMeta().getDisplayName()));
			NPCConfig.saveConfig();
			TalkSettingGUI(player, NPCname, TalkType,number);
			break;
		}
	}

	public void ItemBuyGuiClick(InventoryClickEvent event)
	{
		event.setCancelled(true);
		Player player = (Player) event.getWhoClicked();
		String NPCname = ChatColor.stripColor(event.getInventory().getItem(53).getItemMeta().getLore().get(1));
		boolean isBuy = false;
		isBuy = event.getInventory().getName().contains("����");

		if(event.getSlot() == 45)
		{
			s.SP(player, Sound.ITEM_PICKUP, 0.8F, 1.0F);
			ShopGUI(player, NPCname, (short)0, isBuy, false);
			return;
		}
		else if(event.getSlot() == 53)
		{
			s.SP(player, Sound.PISTON_RETRACT, 0.8F, 1.8F);
			player.closeInventory();
			return;
		}
		ItemStack item = event.getInventory().getItem(22);
		int value = Integer.parseInt(ChatColor.stripColor(event.getInventory().getItem(0).getItemMeta().getLore().get(0)));
		int count = Integer.parseInt(ChatColor.stripColor(event.getInventory().getItem(1).getItemMeta().getLore().get(0)));
		int slot = event.getSlot();

		if(isBuy)
		{
			if(slot == 49 && count != 0)
			{
				if(ServerOption.PlayerList.get(player.getUniqueId().toString()).getStat_Money() >= value*count)
				{
					for(int counter = 0; counter < count; counter++)
					{
						if(new GoldBigDragon_RPG.Util.PlayerUtil().giveItem(player, item)==false)
						{
							s.SP(player, Sound.ORB_PICKUP, 0.8F, 1.8F);
							player.sendMessage(ChatColor.RED+"[���� ����] : �κ��丮�� �����Ͽ� "+(count-counter)+"���� �������� ���Ͽ����ϴ�!");
							ServerOption.PlayerList.get(player.getUniqueId().toString()).setStat_Money(ServerOption.PlayerList.get(player.getUniqueId().toString()).getStat_Money() - (counter*value));
							return;
						}
					}
					s.SP(player, Sound.CHEST_CLOSE, 0.8F, 0.5F);
					ServerOption.PlayerList.get(player.getUniqueId().toString()).setStat_Money(ServerOption.PlayerList.get(player.getUniqueId().toString()).getStat_Money() - (count*value));
				}
				else
				{
					s.SP(player, Sound.ORB_PICKUP, 0.8F, 1.8F);
					player.sendMessage(ChatColor.RED+"[���� ����] : "+ServerOption.Money+ChatColor.RED+"�� �����Ͽ� ������ �� �����ϴ�!");
				}
			}
			else if(slot == 31)
			{
				s.SP(player, Sound.CHEST_OPEN, 0.8F, 0.5F);
				count = (int) (ServerOption.PlayerList.get(player.getUniqueId().toString()).getStat_Money() / value);
			}
			else if(slot == 13)
			{
				s.SP(player, Sound.PISTON_RETRACT, 0.6F, 1.8F);
				count = 1;
			}
			else if(slot>=19&&slot<=21)
			{
				if(slot==19)
				{
					s.SP(player, Sound.LAVA_POP, 0.8F, 0.5F);
					if(count-64 > 0)
						count -= 64;
					else
						count = 1;
				}
				else if(slot==20)
				{
					s.SP(player, Sound.LAVA_POP, 0.8F, 1.2F);
					if(count-10 > 0)
						count -= 10;
					else
						count = 1;
				}
				else if(slot==21)
				{
					s.SP(player, Sound.LAVA_POP, 0.8F, 1.8F);
					if(count-1 > 0)
						count -= 1;
				}
			}
			else if(slot >= 23 && slot <= 25)
			{
				int TempCount = count;
				if(slot == 23)
				{
					s.SP(player, Sound.ITEM_PICKUP, 0.8F, 1.8F);
					TempCount += 1;
				}
				else if(slot == 24)
				{
					s.SP(player, Sound.ITEM_PICKUP, 0.8F, 1.2F);
					TempCount += 10;
				}
				else if(slot == 25)
				{
					s.SP(player, Sound.ITEM_PICKUP, 0.8F, 0.5F);
					TempCount += 64;
				}
				if(value * TempCount <= ServerOption.PlayerList.get(player.getUniqueId().toString()).getStat_Money())
					count = TempCount;
				else if(slot != 23)
					count = (int) (ServerOption.PlayerList.get(player.getUniqueId().toString()).getStat_Money() / value);
			}
			ItemBuy(player, item, value, NPCname, isBuy, count);
		}
		else
		{
			int ItemHave = new GoldBigDragon_RPG.Util.PlayerUtil().getItemAmount(player, item);

			if(slot == 49 && count != 0)
			{
				if(new GoldBigDragon_RPG.Util.PlayerUtil().deleteItem(player, item, count)==false)
				{
					s.SP(player, Sound.ORB_PICKUP, 0.8F, 1.8F);
					player.sendMessage(ChatColor.RED+"[�Ǹ� ����] : ��ǰ�� �����Ͽ� �Ǹ����� ���Ͽ����ϴ�!");
					return;
				}
				else
				{
					s.SP(player, Sound.ORB_PICKUP, 0.8F, 1.0F);
					ServerOption.PlayerList.get(player.getUniqueId().toString()).addStat_MoneyAndEXP(value*count, 0, false);
				}
			}
			
			if(slot == 31)
			{
				s.SP(player, Sound.CHEST_OPEN, 0.8F, 0.5F);
				count = ItemHave;
			}
			else if(slot == 13)
			{
				s.SP(player, Sound.PISTON_RETRACT, 0.6F, 1.8F);
				if(ItemHave > 0)
					count = 1;
				else
					count = 0;
			}
			else if(slot>=19&&slot<=21)
			{
				if(slot==19)
				{
					s.SP(player, Sound.LAVA_POP, 0.8F, 0.5F);
					if(count-64 > 0)
						count -= 64;
					else
						count = 1;
				}
				else if(slot==20)
				{
					s.SP(player, Sound.LAVA_POP, 0.8F, 1.2F);
					if(count-10 > 0)
						count -= 10;
					else
						count = 1;
				}
				else if(slot==21)
				{
					s.SP(player, Sound.LAVA_POP, 0.8F, 1.8F);
					if(count-1 > 0)
						count -= 1;
				}
			}
			else if(slot >= 23 && slot <= 25)
			{
				int TempCount = count;
				if(slot == 23)
				{
					s.SP(player, Sound.ITEM_PICKUP, 0.8F, 1.8F);
					TempCount += 1;
				}
				else if(slot == 24)
				{
					s.SP(player, Sound.ITEM_PICKUP, 0.8F, 1.2F);
					TempCount += 10;
				}
				else if(slot == 25)
				{
					s.SP(player, Sound.ITEM_PICKUP, 0.8F, 0.5F);
					TempCount += 64;
				}
				if(TempCount <= ItemHave)
					count = TempCount;
				else
					count = ItemHave;
			}
			ItemBuy(player, item, value, NPCname, isBuy, count);
		}
	}

	public void ItemFixGuiClick(InventoryClickEvent event)
	{
		Player player = (Player) event.getWhoClicked();
		if(event.getCurrentItem().getType()==Material.AIR)
			return;
		
		if(event.getCurrentItem().hasItemMeta())
		{
			if(event.getCurrentItem().getItemMeta().hasDisplayName())
			{
				String DisplayName = event.getCurrentItem().getItemMeta().getDisplayName();
				byte size = (byte) DisplayName.length();
				if(DisplayName.charAt(size-1)=='c'&&DisplayName.charAt(size-2)=='��'&&DisplayName.charAt(size-3)=='e'&&DisplayName.charAt(size-4)=='��'&& DisplayName.charAt(size-5)=='0'&&DisplayName.charAt(size-6)=='��')
				{
					if(event.getSlot()==0)
					{
						String NPCname = ChatColor.stripColor(event.getInventory().getItem(8).getItemMeta().getLore().get(1));
						s.SP(player, Sound.ITEM_PICKUP, 0.8F, 1.0F);
						MainGUI(player, NPCname, player.isOp());
					}
					else if(event.getSlot() == 8)
					{
						s.SP(player, Sound.PISTON_RETRACT, 0.8F, 1.8F);
						player.closeInventory();
					}
					else
						s.SP(player, Sound.ITEM_PICKUP, 0.8F, 1.0F);
					return;
				}
			}
		}
		ItemStack clickedItem = event.getCurrentItem();
		short ItemID = (short) clickedItem.getTypeId();
		boolean HasCustomDurability = false;
		if(clickedItem.hasItemMeta()==true)
		{
			if(clickedItem.getItemMeta().hasLore()==true)
			{
				for(byte count = 0; count < clickedItem.getItemMeta().getLore().size(); count++)
				{
					if(clickedItem.getItemMeta().getLore().get(count).contains("������") == true)
					{
						HasCustomDurability= true;
						break;
					}
				}
			}
		}
		if((ItemID>=256&&ItemID<=259)||(ItemID==261)||(ItemID>=267&&ItemID<=279)||(ItemID>=283&&ItemID<=286)||(ItemID>=290&&ItemID<=294)
				||(ItemID>=298&&ItemID<=317)||ItemID==346||ItemID==359||ItemID==398||HasCustomDurability==true)
		{
		  	long playerMoney = GoldBigDragon_RPG.Main.ServerOption.PlayerList.get(player.getUniqueId().toString()).getStat_Money();
		  	long FixPrice = Long.parseLong(ChatColor.stripColor(event.getInventory().getItem(0).getItemMeta().getLore().get(2)));
		  	byte FixRate = (byte)Integer.parseInt(ChatColor.stripColor(event.getInventory().getItem(0).getItemMeta().getLore().get(1)));

			if(HasCustomDurability == false)
			{
				short nowDurability = (short) (clickedItem.getDurability());
				
				if(nowDurability == 0)
				{
					s.SP(player,Sound.ANVIL_LAND, 0.8F, 1.6F);
					player.sendMessage(ChatColor.DARK_AQUA + "[����] : �ش� ����� �������� �ʿ䰡 �����ϴ�.");
					return;
				}
				
				int point10need = (nowDurability/10);
				int point1need = (nowDurability%10);

				int point10success = 0;
				int point1success = 0;
				
				if(playerMoney < point10need*FixPrice ||playerMoney < ((point1success*FixPrice)/10))
				{
					s.SP(player,Sound.ORB_PICKUP, 1.0F, 1.8F);
					player.sendMessage(ChatColor.RED + "[SYSTEM] : ���� ����� �����մϴ�!");
					return;
				}
				
				for(int count = 0; count < point10need;count++)
				{
					if(new Random().nextInt(101) <= FixRate)
						point10success = (point10success+1);
				}
				for(int count = 0; count < point1need;count++)
				{
					if(new Random().nextInt(101) <= FixRate)
						point1success = (point1success+1);
				}
				if(point10success==0 && point1success==0)
				{
					player.sendMessage(ChatColor.RED + "[����] : ���� ���� ����!");
					s.SP(player,Sound.ANVIL_BREAK, 1.2F, 1.0F);
					return;
				}
				s.SP(player,Sound.ANVIL_USE, 1.0F, 1.0F);
				if(point10success == point10need && point1success ==point1need)
					player.sendMessage(ChatColor.DARK_AQUA + "[����] : ���� �뼺��!");
				if(point10success != point10need || point1success !=point1need)
					player.sendMessage(ChatColor.YELLOW + "[����] : "+ChatColor.WHITE+((point10success*10)+point1success)+ChatColor.YELLOW+" ����Ʈ ���� ����, "+ChatColor.WHITE+((point10need-(point10success))*10+(point1need-point1success))+ChatColor.YELLOW+" ����Ʈ ���� ���� ");

				clickedItem.setDurability((short) (clickedItem.getDurability()-((point10success*10)+point1success)));
		  		GoldBigDragon_RPG.Main.ServerOption.PlayerList.get(player.getUniqueId().toString()).addStat_MoneyAndEXP(-1 * ((point10need*FixPrice)+((point1need*FixPrice)/10)), 0, false);
			}
			else//Ŀ���� ������ ���� ���
			{
				int Maxdurability = 0;
				int nowDurability =0;
				for(byte count = 0; count < clickedItem.getItemMeta().getLore().size();count++)
				{
					if(clickedItem.getItemMeta().getLore().get(count).contains("������") == true)
					{
						Maxdurability = Integer.parseInt(ChatColor.stripColor(clickedItem.getItemMeta().getLore().get(count)).split(" : ")[1].split(" / ")[1]);
						nowDurability = Integer.parseInt(ChatColor.stripColor(clickedItem.getItemMeta().getLore().get(count)).split(" : ")[1].split(" / ")[0]);
						break;
					}
				}
				
				if(nowDurability == Maxdurability)
				{
					s.SP(player,Sound.ANVIL_LAND, 0.8F, 1.6F);
					player.sendMessage(ChatColor.DARK_AQUA + "[����] : �ش� ����� �������� �ʿ䰡 �����ϴ�.");
					return;
				}
				
				int point10need = (Maxdurability-nowDurability)/10;
				int point1need = (Maxdurability-nowDurability)%10;

				int point10success = 0;
				int point1success = 0;
				
				if(playerMoney < point10need*FixPrice ||playerMoney < ((point1success*FixPrice)/10))
				{
					s.SP(player,Sound.ORB_PICKUP, 1.0F, 1.8F);
					player.sendMessage(ChatColor.RED + "[SYSTEM] : ���� ����� �����մϴ�!");
					return;
				}
				
				for(int count = 0; count < point10need;count++)
				{
					if(new Random().nextInt(101) <= FixRate)
						point10success = (point10success+1);
				}
				for(int count = 0; count < point1need;count++)
				{
					if(new Random().nextInt(101) <= FixRate)
						point1success = (point1success+1);
				}
				if(point10success==0 && point1success==0)
				{
					player.sendMessage(ChatColor.RED + "[����] : ���� ���� ����!");
					s.SP(player,Sound.ANVIL_BREAK, 1.2F, 1.0F);
				}
				s.SP(player,Sound.ANVIL_BREAK, 1.0F, 1.0F);
				if(point10success == point10need && point1success ==point1need)
					player.sendMessage(ChatColor.DARK_AQUA + "[����] : ���� �뼺��!");
				if((point10success != point10need || point1success !=point1need)&&(point10success!=0 || point1success!=0))
					player.sendMessage(ChatColor.YELLOW + "[����] : "+ChatColor.WHITE+((point10success*10)+point1success)+ChatColor.YELLOW+" ����Ʈ ���� ����, "+ChatColor.WHITE+((point10need-(point10success))*10+(point1need-point1success))+ChatColor.YELLOW+" ����Ʈ ���� ���� ");

				if(clickedItem.hasItemMeta() == true)
				{
					if(clickedItem.getItemMeta().hasLore() == true)
					{
						for(byte count = 0; count < clickedItem.getItemMeta().getLore().size(); count++)
						{
							ItemMeta Meta = clickedItem.getItemMeta();
							if(Meta.getLore().get(count).contains("������") == true)
							{
								String[] Lore = ChatColor.stripColor(Meta.getLore().get(count)).split(" : ");
								String[] SubLore = Lore[1].split(" / ");
								List<String> PLore = Meta.getLore();
								PLore.set(count,ChatColor.WHITE+ Lore[0] + " : "+(Integer.parseInt(SubLore[0])+((point10success*10)+point1success))+" / "+(Integer.parseInt(SubLore[1])-(((point10need-point10success)*10)+(point1need-point1success))));
								Meta.setLore(PLore);
								clickedItem.setItemMeta(Meta);
							}
						}
					}
				}
		  		GoldBigDragon_RPG.Main.ServerOption.PlayerList.get(player.getUniqueId().toString()).addStat_MoneyAndEXP(-1 * ((point10need*FixPrice)+((point1need*FixPrice)/10)), 0, false);
			}
		}
		else
		{
			s.SP(player, Sound.ORB_PICKUP, 1.8F, 1.0F);
			player.sendMessage(ChatColor.RED + "[SYSTEM] : ���� �� �� ���� �����Դϴ�!");
		}
	}

	public void PresentGuiClick(InventoryClickEvent event)
	{
		Player player = (Player) event.getWhoClicked();
		if(event.getCurrentItem().getType()==Material.AIR)
			return;
		int number = -1;
		boolean isSettingMode = false;
		String NPCname = ChatColor.stripColor(event.getInventory().getItem(8).getItemMeta().getLore().get(1)); 
		if(ChatColor.stripColor(event.getInventory().getName()).compareTo("[NPC] ���� �������� �÷� �ּ���")==0)
		{
			number = Integer.parseInt(ChatColor.stripColor(event.getInventory().getItem(1).getItemMeta().getLore().get(0)));
			isSettingMode = Boolean.parseBoolean(ChatColor.stripColor(event.getInventory().getItem(0).getItemMeta().getLore().get(1)));

			if(event.getSlot()==4)
			{
				event.setCancelled(false);
				return;
			}
			String DisplayName = null;
			if(event.getCurrentItem().hasItemMeta() && event.getCurrentItem().getItemMeta().hasDisplayName())
			{
				DisplayName = event.getCurrentItem().getItemMeta().getDisplayName();
				byte size = (byte) DisplayName.length();
				if(size >= 6 && DisplayName.charAt(size-1)=='c'&&DisplayName.charAt(size-2)=='��'&&DisplayName.charAt(size-3)=='e'&&DisplayName.charAt(size-4)=='��'&& DisplayName.charAt(size-5)=='0'&&DisplayName.charAt(size-6)=='��')
				{
					event.setCancelled(true);
					YamlController YC = new YamlController(GoldBigDragon_RPG.Main.Main.plugin);
					YamlManager NPCConfig =YC.getNewConfig("NPC/NPCData/"+new Object_UserData().getNPCuuid(player)+".yml");
					if(event.getSlot()==0)//���� ����
					{
						if(isSettingMode)
						{
							ItemStack item = NPCConfig.getItemStack("Present."+number+".item");
							if(event.getInventory().getItem(4) != null)
								NPCConfig.set("Present."+number+".item", event.getInventory().getItem(4));
							else
							{
								NPCConfig.set("Present."+number+".item", null);
								NPCConfig.saveConfig();
								s.SP(player, Sound.ITEM_PICKUP, 0.8F, 1.0F);
								PresentSettingGUI(player, NPCname);
								return;
							}
							NPCConfig.set("Present."+number+".love", 0);
							NPCConfig.saveConfig();
							s.SP(player, Sound.ITEM_PICKUP, 0.8F, 1.0F);
							Object_UserData u = new Object_UserData();
							u.setType(player, "NPC");
							u.setString(player, (byte)2,NPCname);
							u.setString(player, (byte)3,u.getNPCuuid(player));
							u.setString(player, (byte)4,"PresentLove");
							u.setInt(player, (byte)0, number);
							player.sendMessage(ChatColor.DARK_AQUA + "[NPC] : �ش� �������� �� �� ����ϴ� ȣ���� ��ġ�� �Է� �� �ּ���! (-1000 ~ 1000 ���� ��)");
							player.closeInventory();
						}
						else
						{
							s.SP(player, Sound.ITEM_PICKUP, 0.8F, 1.0F);
							MainGUI(player, NPCname, player.isOp());
						}
					}
					else if(event.getSlot() == 8)//�ݴ�(���� �ֱ�)
					{
						if(isSettingMode)
						{
							s.SP(player, Sound.PISTON_RETRACT, 0.8F, 1.8F);
							player.closeInventory();
							return;
						}
					  	YamlManager PlayerNPC = null;
				    	if(YC.isExit("NPC/PlayerData/"+player.getUniqueId()+".yml")==false)
				    	{
							Object_UserData u = new Object_UserData();
				    		PlayerNPC=YC.getNewConfig("NPC/PlayerData/"+player.getUniqueId()+".yml");
				    		PlayerNPC.set(u.getNPCuuid(player)+".love", 0);
				    		PlayerNPC.set(u.getNPCuuid(player)+".Career", 0);
				    		PlayerNPC.saveConfig();
				    	}
				    	else
				    		PlayerNPC=YC.getNewConfig("NPC/PlayerData/"+player.getUniqueId()+".yml");
						if(event.getInventory().getItem(4) != null)
						{
					    	ItemStack Present = new ItemStack(event.getInventory().getItem(4));
					    	int Amount = Present.getAmount();
					    	Present.setAmount(1);

							Object_UserData u = new Object_UserData();
							for(int count = 2; count < 8; count++)
							{
								if(NPCConfig.getItemStack("Present."+count+".item") != null)
								{
									ItemStack LoveItem = new ItemStack(NPCConfig.getItemStack("Present."+count+".item"));
									int LoveItemAmount = LoveItem.getAmount();
									LoveItem.setAmount(1);
									if(LoveItem.equals(Present))
									{
										event.getInventory().setItem(4, null);
										int LoveValue = NPCConfig.getInt("Present."+count+".love");
										int love = 0;
										if(LoveValue >= 0)
										{
											if(Amount / LoveItemAmount == 0)
												love = 1;
											else
												love = LoveValue * (Amount/LoveItemAmount);
										}
										else
											love = LoveValue;
										if(PlayerNPC.getInt(u.getNPCuuid(player)+".love") + love >= 1000)
											PlayerNPC.set(u.getNPCuuid(player)+".love", 1000);
										else if(PlayerNPC.getInt(u.getNPCuuid(player)+".love") + love <= -1000)
											PlayerNPC.set(u.getNPCuuid(player)+".love", -1000);
										else
											PlayerNPC.set(u.getNPCuuid(player)+".love", PlayerNPC.getInt(u.getNPCuuid(player)+".love") + love);
										PlayerNPC.saveConfig();
										if(love >= 0)
										{
											s.SP(player, Sound.LEVEL_UP, 1.0F, 1.8F);
											player.sendMessage(ChatColor.GREEN + "[SYSTEM] : "+ChatColor.YELLOW+NPCname+ChatColor.GREEN+"�� ȣ������ "+ChatColor.YELLOW+love+ChatColor.GREEN+" ����Ͽ����ϴ�!");
										}
										else
										{
											s.SP(player, Sound.ANVIL_LAND, 1.0F, 1.8F);
											player.sendMessage(ChatColor.RED + "[SYSTEM] : "+ChatColor.YELLOW+NPCname+ChatColor.RED+"�� ȣ������ "+ChatColor.YELLOW+(love*-1)+ChatColor.RED+" �϶� �Ͽ����ϴ�!");
										}
										return;
									}
								}
							}
							if(NPCConfig.getInt("Present.1.love")!=0)
							{
								event.getInventory().setItem(4, null);
								int LoveValue = NPCConfig.getInt("Present.1.love");
								int love = 0;
								if(LoveValue >= 0)
									love = LoveValue * Amount;
								else
									love = LoveValue;
								if(PlayerNPC.getInt(u.getNPCuuid(player)+".love") + love >= 1000)
									PlayerNPC.set(u.getNPCuuid(player)+".love", 1000);
								else if(PlayerNPC.getInt(u.getNPCuuid(player)+".love") + love <= -1000)
									PlayerNPC.set(u.getNPCuuid(player)+".love", -1000);
								else
									PlayerNPC.set(u.getNPCuuid(player)+".love", PlayerNPC.getInt(u.getNPCuuid(player)+".love") + love);
								PlayerNPC.saveConfig();
								if(love >= 0)
								{
									s.SP(player, Sound.LEVEL_UP, 1.0F, 1.8F);
									player.sendMessage(ChatColor.GREEN + "[SYSTEM] : "+ChatColor.YELLOW+NPCname+ChatColor.GREEN+"�� ȣ������ "+ChatColor.YELLOW+love+ChatColor.GREEN+" ����Ͽ����ϴ�!");
								}
								else
								{
									s.SP(player, Sound.ANVIL_LAND, 1.0F, 1.8F);
									player.sendMessage(ChatColor.RED + "[SYSTEM] : "+ChatColor.YELLOW+NPCname+ChatColor.RED+"�� ȣ������ "+ChatColor.YELLOW+(love*-1)+ChatColor.RED+" �϶� �Ͽ����ϴ�!");
								}
								return;
							}
							else
							{
								s.SP(player, Sound.DIG_WOOL, 1.0F, 1.8F);
								player.sendMessage(ChatColor.YELLOW + "[SYSTEM] : "+ChatColor.GOLD+NPCname+ChatColor.YELLOW+"�� ������ ����Ͽ���.");
							}
						}
						else
							s.SP(player, Sound.ITEM_PICKUP, 0.8F, 1.0F);
					}
					else
						s.SP(player, Sound.ITEM_PICKUP, 0.8F, 1.0F);
					return;
				}
			}
		}
		else//[NPC] ���� ���� ������ ���
		{
			event.setCancelled(true);
			if(event.getSlot()==0)
			{
				s.SP(player, Sound.ITEM_PICKUP, 0.8F, 1.0F);
				MainGUI(player, NPCname, player.isOp());
			}
			else if(event.getSlot() == 8)
			{
				s.SP(player, Sound.PISTON_RETRACT, 0.8F, 1.8F);
				player.closeInventory();
			}
			else
			{
				s.SP(player, Sound.ITEM_PICKUP, 0.8F, 1.0F);
				if(event.getSlot()==1)
				{
					Object_UserData u = new Object_UserData();
					u.setType(player, "NPC");
					u.setString(player, (byte)2,NPCname);
					u.setString(player, (byte)3,u.getNPCuuid(player));
					u.setString(player, (byte)4,"PresentLove");
					u.setInt(player, (byte)0, 1);
					player.sendMessage(ChatColor.DARK_AQUA + "[NPC] : �ƹ� �������̳� �� �� ����ϴ� ȣ���� ��ġ�� �Է� �� �ּ���! (-1000 ~ 1000 ���� ��)");
					player.closeInventory();
				}
				else
					PresentGiveGUI(player, NPCname, true, event.getSlot());
			}
			
			return;
		}
		return;
	}
	
	public void PresentInventoryClose(InventoryCloseEvent event)
	{
		if(event.getInventory().getItem(4)!=null)
			event.getPlayer().getInventory().addItem(event.getInventory().getItem(4));
		return;
	}
}