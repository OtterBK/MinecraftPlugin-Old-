package me.Bokum.Party;

import java.util.ArrayList;
import java.util.List;

import javax.xml.bind.Marshaller.Listener;

public class Party extends Listener
{
	public static String leader;
	public static List<String> member = new ArrayList<String>();
	
	
	public static String getLeader(){
		if(leader == null){
			return "파티장 없음"; 
		}
		return leader;
	} 
}
