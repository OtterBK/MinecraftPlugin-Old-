package me.Bokum.OverWatch;

import org.bukkit.Location;
import org.bukkit.entity.Player;

public class SetArea {
	public int areaNum;
	public Location pos1;
	public Location pos2;
	
	public SetArea(Player p, int areaType){
		this.areaNum = areaType;
		pos1 = null;
		pos2 = null;
	}
	
	public void save(){
		if(areaNum == 1){
			GameData.area1Pos1 = pos1;
			GameData.area1Pos2 = pos2;	
		} else if(areaNum == 2){
			GameData.area2Pos1 = pos1;
			GameData.area2Pos2 = pos2;
		}
	}
	
}
