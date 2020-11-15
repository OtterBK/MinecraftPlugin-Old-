package GoldBigDragon_RPG.Skill;

import org.bukkit.ChatColor;
import org.bukkit.entity.Player;

import GoldBigDragon_RPG.Util.YamlController;
import GoldBigDragon_RPG.Util.YamlManager;

public class SkillConfig
{
    public void CreateNewPlayerSkill(Player player)
	{
    	YamlController YC = new YamlController(GoldBigDragon_RPG.Main.Main.plugin);
		YamlManager PlayerSkillYML = YC.getNewConfig("Skill/PlayerData/"+player.getUniqueId()+".yml");
		YamlManager Config = YC.getNewConfig("config.yml");
		YamlManager JobList  = YC.getNewConfig("Skill/JobList.yml");

		PlayerSkillYML.set("Job.Root",Config.getString("Server.DefaultJob"));
		PlayerSkillYML.set("Job.Type",Config.getString("Server.DefaultJob"));
		PlayerSkillYML.set("Job.LV",1);
		PlayerSkillYML.set("MapleStory.LV",null);
		PlayerSkillYML.set("MapleStory."+Config.getString("Server.DefaultJob")+".Skill.1",null);
		Object[] DefaultSkills = null;
		if(JobList.contains(("MapleStory."+Config.getString("Server.DefaultJob")+"."+Config.getString("Server.DefaultJob")+".Skill"))==true)
			DefaultSkills = JobList.getConfigurationSection("MapleStory."+Config.getString("Server.DefaultJob")+"."+Config.getString("Server.DefaultJob")+".Skill").getKeys(false).toArray();
		if(DefaultSkills!=null)
			for(short count = 0; count < DefaultSkills.length;count++)
				PlayerSkillYML.set("MapleStory."+Config.getString("Server.DefaultJob")+".Skill."+DefaultSkills[count],1);
		PlayerSkillYML.set("Mabinogi.LV",null);
		PlayerSkillYML.saveConfig();
	}
    
    public void CreateNewJobList()
	{
    	YamlController YC = new YamlController(GoldBigDragon_RPG.Main.Main.plugin);
		YamlManager SkillList  = YC.getNewConfig("Skill/JobList.yml");
		
		SkillList.set("Mabinogi.Added.1",null);
		SkillList.set("MapleStory.�ʺ���.�ʺ���.NeedLV",0);
		SkillList.set("MapleStory.�ʺ���.�ʺ���.NeedSTR",0);
		SkillList.set("MapleStory.�ʺ���.�ʺ���.NeedDEX",0);
		SkillList.set("MapleStory.�ʺ���.�ʺ���.NeedINT",0);
		SkillList.set("MapleStory.�ʺ���.�ʺ���.NeedWILL",0);
		SkillList.set("MapleStory.�ʺ���.�ʺ���.NeedLUK",0);
		SkillList.set("MapleStory.�ʺ���.�ʺ���.PrevJob","null");
		SkillList.set("MapleStory.�ʺ���.�ʺ���.IconID",268);
		SkillList.set("MapleStory.�ʺ���.�ʺ���.IconData",0);
		SkillList.set("MapleStory.�ʺ���.�ʺ���.IconAmount",1);
		SkillList.set("MapleStory.�ʺ���.�ʺ���.Skill.1",null);
		SkillList.saveConfig();
	}
    
    public void CreateNewSkillList()
	{
    	YamlController YC = new YamlController(GoldBigDragon_RPG.Main.Main.plugin);
		YamlManager SkillList  = YC.getNewConfig("Skill/SkillList.yml");
		SkillList.set("CloudKill.ID",151);
		SkillList.set("CloudKill.DATA",0);
		SkillList.set("CloudKill.Amount",1);
		SkillList.set("CloudKill.SkillRank."+(int)1+".Command","/weather clear 9999");
		SkillList.set("CloudKill.SkillRank."+(int)1+".BukkitPermission",true);
		SkillList.set("CloudKill.SkillRank."+(int)1+".MagicSpells","null");
		SkillList.set("CloudKill.SkillRank."+(int)1+".Lore",ChatColor.GRAY + "     [���� ����]     ");
		SkillList.set("CloudKill.SkillRank."+(int)1+".AffectStat","����");
		SkillList.set("CloudKill.SkillRank."+(int)1+".DistrictWeapon","����");
		SkillList.saveConfig();
	}
}
