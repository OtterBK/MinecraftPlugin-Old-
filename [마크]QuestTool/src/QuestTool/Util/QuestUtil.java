package QuestTool.Util;

import java.io.File;

import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;

import QuestTool.Main.Main;

public class QuestUtil {
	public static int getAddObjectNumber(String questName){
		File file = new File(Main.instance.getDataFolder()+"/Quests", questName+".quest");
		FileConfiguration fileConfig = YamlConfiguration.loadConfiguration(file);
		
		for(int i = 53; i > 1; i--){
			if(fileConfig.contains(""+i)){
				return i+1;
			}
		}
		
		return 1;
	}
}
