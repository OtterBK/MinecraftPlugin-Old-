package me.Bokum.OverWatch;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;

import me.Bokum.Team.Team;

public class GameData {

	public static Team redTeam;
	public static Team blueTeam;
	public static Location attackSpawn1;
	public static Location attackSpawn2;
	public static Location defenseSpawn1;
	public static Location defenseSpawn2;
	public static Location area1Pos1;
	public static Location area1Pos2;
	public static Location area2Pos1;
	public static Location area2Pos2;
	
	public GameData(){
		redTeam.setTeamName("레드팀");
		blueTeam.setTeamName("블루팀");
	}
	
	public static void save(){
		File file = new File(Main.instance.getDataFolder().getPath()+"/gamedata", "gamedata.data");
		if(!file.exists() || file.isDirectory()){
			Bukkit.getLogger().info("게임 데이터를 저장하는 도중 오류가 발생했습니다.");
			return;
		}
		try {
			FileConfiguration dataCfg = YamlConfiguration.loadConfiguration(file);
			ConfigurationSection spawnSection = dataCfg.createSection("spawn");
			ConfigurationSection attackSpawn1Sec = spawnSection.createSection("attackSpawn1");
			ConfigurationSection attackSpawn2Sec = spawnSection.createSection("attackSpawn1");
			ConfigurationSection defenseSpawn1Sec = spawnSection.createSection("attackSpawn1");
			ConfigurationSection defenseSpawn2Sec = spawnSection.createSection("attackSpawn1");
			List<ConfigurationSection> spawnSectionList = new ArrayList<ConfigurationSection>(4);
			List<Location> spawnLocationList = new ArrayList<Location>(4);
			spawnSectionList.add(attackSpawn1Sec);
			spawnSectionList.add(attackSpawn2Sec);
			spawnSectionList.add(defenseSpawn1Sec);
			spawnSectionList.add(defenseSpawn2Sec);
			spawnLocationList.add(attackSpawn1);
			spawnLocationList.add(attackSpawn2);
			spawnLocationList.add(defenseSpawn1);
			spawnLocationList.add(defenseSpawn2);
			for(int i = 0; i < 4; i++){
				ConfigurationSection section = spawnSectionList.get(i);
				Location saveLocation = spawnLocationList.get(i);
				section.set("x", saveLocation.getX());
				section.set("y", saveLocation.getY());
				section.set("z", saveLocation.getZ());
				section.set("world", saveLocation.getWorld().getName());
			}
			
			Bukkit.getLogger().info("§b[§aows§f] §f- §4공격/수비 진영 스폰지점 데이터 저장완료");
			
			ConfigurationSection areaSection = dataCfg.createSection("area");
			ConfigurationSection area1Section = areaSection.createSection("area1");
			ConfigurationSection area1Pos1Section = area1Section.createSection("pos1");
			ConfigurationSection area1Pos2Section = area1Section.createSection("pos2");
			ConfigurationSection area2Section = areaSection.createSection("area2");
			ConfigurationSection area2Pos1Section = area2Section.createSection("pos1");
			ConfigurationSection area2Pos2Section = area2Section.createSection("pos2");
			area1Pos1Section.set("x", area1Pos1.getX());
			area1Pos1Section.set("y", area1Pos1.getX());
			area1Pos1Section.set("z", area1Pos1.getX());
			area1Pos1Section.set("world", area1Pos1.getX());
			
			area1Pos2Section.set("x", area1Pos2.getX());
			area1Pos2Section.set("y", area1Pos2.getX());
			area1Pos2Section.set("z", area1Pos2.getX());
			area1Pos2Section.set("world", area1Pos2.getX());
			
			area2Pos1Section.set("x", area2Pos1.getX());
			area2Pos1Section.set("y", area2Pos1.getX());
			area2Pos1Section.set("z", area2Pos1.getX());
			area2Pos1Section.set("world", area2Pos1.getX());
			
			area2Pos2Section.set("x", area2Pos2.getX());
			area2Pos2Section.set("y", area2Pos2.getX());
			area2Pos2Section.set("z", area2Pos2.getX());
			area2Pos2Section.set("world", area2Pos2.getX());
			
			Bukkit.getLogger().info("§b[§aows§f] §f- §4거점 데이터 저장완료");
			
			return;
		} catch(Exception e){
			Bukkit.getLogger().info("게임 데이터를 저장하는 도중 오류가 발생했습니다.");
			return;
		}
	}
	
	public void load(){
		
	}
	
}
