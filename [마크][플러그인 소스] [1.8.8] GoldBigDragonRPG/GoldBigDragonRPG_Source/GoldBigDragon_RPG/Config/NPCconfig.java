package GoldBigDragon_RPG.Config;

import org.bukkit.entity.Player;

import GoldBigDragon_RPG.Util.YamlController;
import GoldBigDragon_RPG.Util.YamlManager;

public class NPCconfig
{
    public void PlayerNPCconfig(Player player, String NPCuuid)
	{
	    YamlManager NPCscript = null;
	  	YamlController YC = new YamlController(GoldBigDragon_RPG.Main.Main.plugin);
    	if(YC.isExit("NPC/PlayerData/"+player.getUniqueId()+".yml")==false)
    	{
    		NPCscript=YC.getNewConfig("NPC/PlayerData/"+player.getUniqueId()+".yml");
    	  	NPCscript.set(NPCuuid+".love", 0);
    	  	NPCscript.set(NPCuuid+".Career", 0);
    	  	NPCscript.saveConfig();
    	}
		NPCscript=YC.getNewConfig("NPC/PlayerData/"+player.getUniqueId()+".yml");
    	if(NPCscript.contains(NPCuuid) == false)
    	{
    		NPCscript=YC.getNewConfig("NPC/PlayerData/"+player.getUniqueId()+".yml");
    	  	NPCscript.set(NPCuuid+".love", 0);
    	  	NPCscript.set(NPCuuid+".Career", 0);
    	  	NPCscript.saveConfig();
    	}
	}
    public void NPCNPCconfig(String NPCuuid)
	{
	    YamlManager NPCscript = null;
	  	YamlController YC = new YamlController(GoldBigDragon_RPG.Main.Main.plugin);
    	if(YC.isExit("NPC/NPCData/"+NPCuuid+".yml")==false)
    	{
    		NPCscript = YC.getNewConfig("NPC/NPCData/"+NPCuuid+".yml");
    	  	NPCscript.set("NPCuuid", "NPC's uuid");
    	  	NPCscript.set("KoreaLanguage(UTF-8)->JavaEntityLanguage", "http://itpro.cz/juniconv/");
    	  	NPCscript.set("NatureTalk.1.love", 0);
    	  	NPCscript.set("NatureTalk.1.Script", "��f�ȳ�, ��e%player%?");
    	  	NPCscript.set("NatureTalk.2.love", 0);
    	  	NPCscript.set("NatureTalk.2.Script", "��f��� �����%enter%��f�̷��� ����.");
    	  	NPCscript.set("NatureTalk.3.love", 0);
    	  	NPCscript.set("NatureTalk.3.Script", "��1������ ��4�̷��� ��f���� �� �־�!");
    	  	NPCscript.set("NearByNEWS.1.love", 0);
    	  	NPCscript.set("NearByNEWS.1.Script", "��f���������� ���� ���̾Ƹ�带 �� �� ������...");
    	  	NPCscript.set("NearByNEWS.2.love", 0);
    	  	NPCscript.set("NearByNEWS.2.Script", "��f�����ϴ°� ����.%enter%��f���� ��4���κ� ��f�� �� ��ó�� �־��ŵ�...");
    	  	NPCscript.set("NearByNEWS.3.love", 0);
    	  	NPCscript.set("NearByNEWS.3.Script", "��f��...");
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

    	  	NPCscript.set("Shop.Sell.item", null);
    	  	NPCscript.set("Shop.Buy.item", null);
    	  	NPCscript.set("Quest.0", null);
    	  	
    	  	NPCscript.saveConfig();
    	}
	}
}
