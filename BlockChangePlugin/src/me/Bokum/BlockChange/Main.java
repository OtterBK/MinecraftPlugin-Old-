package me.Bokum.BlockChange;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.Location;
import org.bukkit.block.Block;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.event.Listener;
import org.bukkit.plugin.java.JavaPlugin;

public class Main extends JavaPlugin implements Listener{
	public static List<ChangingBlock> changelist = new ArrayList<ChangingBlock>(200);
	
	public void onEnable(){
		getServer().getPluginManager().registerEvents(this, this);
		
		changelist.add(new ChangingBlock(1, (byte) 1, 1, (byte) 0));
		changelist.add(new ChangingBlock(1, (byte) 2, 1, (byte) 0));
		changelist.add(new ChangingBlock(1, (byte) 3, 1, (byte) 0));
		changelist.add(new ChangingBlock(1, (byte) 4, 1, (byte) 0));
		changelist.add(new ChangingBlock(1, (byte) 5, 1, (byte) 0));
		changelist.add(new ChangingBlock(1, (byte) 6, 1, (byte) 0));
		changelist.add(new ChangingBlock(3, (byte) 1, 3, (byte) 0));
		changelist.add(new ChangingBlock(3, (byte) 2, 3, (byte) 0));
		changelist.add(new ChangingBlock(5, (byte) 4, 5, (byte) 0));
		changelist.add(new ChangingBlock(5, (byte) 5, 5, (byte) 0));
		changelist.add(new ChangingBlock(12, (byte) 1, 12, (byte) 0));
		changelist.add(new ChangingBlock(19, (byte) 1, 19, (byte) 0));
		changelist.add(new ChangingBlock(95, (byte) 0, 20, (byte) 0));
		changelist.add(new ChangingBlock(95, (byte) 1, 20, (byte) 0));
		changelist.add(new ChangingBlock(95, (byte) 2, 20, (byte) 0));
		changelist.add(new ChangingBlock(95, (byte) 3, 20, (byte) 0));
		changelist.add(new ChangingBlock(95, (byte) 4, 20, (byte) 0));
		changelist.add(new ChangingBlock(95, (byte) 5, 20, (byte) 0));
		changelist.add(new ChangingBlock(95, (byte) 6, 20, (byte) 0));
		changelist.add(new ChangingBlock(95, (byte) 7, 20, (byte) 0));
		changelist.add(new ChangingBlock(95, (byte) 8, 20, (byte) 0));
		changelist.add(new ChangingBlock(95, (byte) 9, 20, (byte) 0));
		changelist.add(new ChangingBlock(95, (byte) 10, 20, (byte) 0));
		changelist.add(new ChangingBlock(95, (byte) 11, 20, (byte) 0));
		changelist.add(new ChangingBlock(95, (byte) 12, 20, (byte) 0));
		changelist.add(new ChangingBlock(95, (byte) 13, 20, (byte) 0));
		changelist.add(new ChangingBlock(95, (byte) 14, 20, (byte) 0));
		changelist.add(new ChangingBlock(95, (byte) 15, 20, (byte) 0));
		changelist.add(new ChangingBlock(126, (byte) 5, 126, (byte) 1));
		changelist.add(new ChangingBlock(126, (byte) 6, 126, (byte) 0));
		changelist.add(new ChangingBlock(159, (byte) 0, 35, (byte) 0));
		changelist.add(new ChangingBlock(159, (byte) 1, 35, (byte) 1));
		changelist.add(new ChangingBlock(159, (byte) 2, 35, (byte) 2));
		changelist.add(new ChangingBlock(159, (byte) 3, 35, (byte) 3));
		changelist.add(new ChangingBlock(159, (byte) 4, 35, (byte) 4));
		changelist.add(new ChangingBlock(159, (byte) 5, 35, (byte) 5));
		changelist.add(new ChangingBlock(159, (byte) 6, 35, (byte) 6));
		changelist.add(new ChangingBlock(159, (byte) 7, 35, (byte) 7));
		changelist.add(new ChangingBlock(159, (byte) 8, 35, (byte) 8));
		changelist.add(new ChangingBlock(159, (byte) 9, 35, (byte) 9));
		changelist.add(new ChangingBlock(159, (byte) 10, 35, (byte) 10));
		changelist.add(new ChangingBlock(159, (byte) 11, 35, (byte) 11));
		changelist.add(new ChangingBlock(159, (byte) 12, 35, (byte) 12));
		changelist.add(new ChangingBlock(159, (byte) 13, 35, (byte) 13));
		changelist.add(new ChangingBlock(159, (byte) 14, 35, (byte) 14));
		changelist.add(new ChangingBlock(159, (byte) 15, 35, (byte) 15));
		changelist.add(new ChangingBlock(162, (byte) 0, 17, (byte) 1));
		changelist.add(new ChangingBlock(162, (byte) 1, 17, (byte) 1));
		for(byte d = 0; d <= 15; d++){
			changelist.add(new ChangingBlock(163, d, 134, d));	
		}
		for(byte d = 0; d <= 15; d++){
			changelist.add(new ChangingBlock(164, d, 134, d));	
		}
		changelist.add(new ChangingBlock(168, (byte) 0, 57, (byte) 0));
		changelist.add(new ChangingBlock(168, (byte) 1, 57, (byte) 0));
		changelist.add(new ChangingBlock(168, (byte) 2, 98, (byte) 0));
		changelist.add(new ChangingBlock(169, (byte) 0, 138, (byte) 0));
		changelist.add(new ChangingBlock(170, (byte) 0, 88, (byte) 0));
		changelist.add(new ChangingBlock(172, (byte) 0, 82, (byte) 0));
		changelist.add(new ChangingBlock(173, (byte) 0, 35, (byte) 15));
		changelist.add(new ChangingBlock(174, (byte) 0, 79, (byte) 0));
		changelist.add(new ChangingBlock(179, (byte) 0, 24, (byte) 0));
		changelist.add(new ChangingBlock(179, (byte) 1, 24, (byte) 1));
		changelist.add(new ChangingBlock(179, (byte) 2, 24, (byte) 2));
		for(byte d = 0; d <= 15; d++){
			changelist.add(new ChangingBlock(180, d, 134, d));	
		}
		changelist.add(new ChangingBlock(182, (byte) 0, 44, (byte) 1));
		changelist.add(new ChangingBlock(6, (byte) 4, 6, (byte) 0));
		changelist.add(new ChangingBlock(6, (byte) 5, 6, (byte) 0));
		changelist.add(new ChangingBlock(38, (byte) 0, 37, (byte) 0));
		changelist.add(new ChangingBlock(38, (byte) 1, 37, (byte) 0));
		changelist.add(new ChangingBlock(38, (byte) 2, 37, (byte) 0));
		changelist.add(new ChangingBlock(38, (byte) 3, 37, (byte) 0));
		changelist.add(new ChangingBlock(38, (byte) 4, 37, (byte) 0));
		changelist.add(new ChangingBlock(38, (byte) 5, 37, (byte) 0));
		changelist.add(new ChangingBlock(38, (byte) 6, 37, (byte) 0));
		changelist.add(new ChangingBlock(38, (byte) 7, 37, (byte) 0));
		changelist.add(new ChangingBlock(38, (byte) 8, 37, (byte) 0));
		changelist.add(new ChangingBlock(97, (byte) 0, 1, (byte) 0));
		changelist.add(new ChangingBlock(97, (byte) 1, 4, (byte) 0));
		changelist.add(new ChangingBlock(97, (byte) 2, 98, (byte) 0));
		changelist.add(new ChangingBlock(97, (byte) 3, 98, (byte) 1));
		changelist.add(new ChangingBlock(97, (byte) 4, 98, (byte) 2));
		changelist.add(new ChangingBlock(97, (byte) 5, 98, (byte) 3));
		changelist.add(new ChangingBlock(160, (byte) 0, 102, (byte) 0));
		changelist.add(new ChangingBlock(160, (byte) 1, 102, (byte) 0));
		changelist.add(new ChangingBlock(160, (byte) 2, 102, (byte) 0));
		changelist.add(new ChangingBlock(160, (byte) 3, 102, (byte) 0));
		changelist.add(new ChangingBlock(160, (byte) 4, 102, (byte) 0));
		changelist.add(new ChangingBlock(160, (byte) 5, 102, (byte) 0));
		changelist.add(new ChangingBlock(160, (byte) 6, 102, (byte) 0));
		changelist.add(new ChangingBlock(160, (byte) 7, 102, (byte) 0));
		changelist.add(new ChangingBlock(160, (byte) 8, 102, (byte) 0));
		changelist.add(new ChangingBlock(160, (byte) 9, 102, (byte) 0));
		changelist.add(new ChangingBlock(160, (byte) 10, 102, (byte) 0));
		changelist.add(new ChangingBlock(160, (byte) 11, 102, (byte) 0));
		changelist.add(new ChangingBlock(160, (byte) 12, 102, (byte) 0));
		changelist.add(new ChangingBlock(160, (byte) 13, 102, (byte) 0));
		changelist.add(new ChangingBlock(160, (byte) 14, 102, (byte) 0));
		changelist.add(new ChangingBlock(160, (byte) 15, 102, (byte) 0));
		changelist.add(new ChangingBlock(161, (byte) 0, 18, (byte) 3));
		changelist.add(new ChangingBlock(161, (byte) 1, 18, (byte) 3));
		changelist.add(new ChangingBlock(165, (byte) 0, 35, (byte) 5));
		for(byte d = 0; d <= 15; d++){
			changelist.add(new ChangingBlock(171, d, 72, (byte) 0));	
		}
		changelist.add(new ChangingBlock(167, (byte) 0, 96, (byte) 5));
		changelist.add(new ChangingBlock(183, (byte) 0, 107, (byte) 5));
		changelist.add(new ChangingBlock(184, (byte) 0, 107, (byte) 5));
		changelist.add(new ChangingBlock(185, (byte) 0, 107, (byte) 5));
		changelist.add(new ChangingBlock(186, (byte) 0, 107, (byte) 5));
		changelist.add(new ChangingBlock(187, (byte) 0, 107, (byte) 5));
		changelist.add(new ChangingBlock(175, (byte) 0, 0, (byte) 0));
		changelist.add(new ChangingBlock(175, (byte) 1, 0, (byte) 0));
		changelist.add(new ChangingBlock(175, (byte) 2, 0, (byte) 0));
		changelist.add(new ChangingBlock(175, (byte) 3, 0, (byte) 0));
		changelist.add(new ChangingBlock(175, (byte) 4, 0, (byte) 0));
		changelist.add(new ChangingBlock(175, (byte) 5, 0, (byte) 0));
		changelist.add(new ChangingBlock(188, (byte) 0, 85, (byte) 0));
		changelist.add(new ChangingBlock(189, (byte) 0, 85, (byte) 0));
		changelist.add(new ChangingBlock(190, (byte) 0, 85, (byte) 0));
		changelist.add(new ChangingBlock(191, (byte) 0, 85, (byte) 0));
		changelist.add(new ChangingBlock(192, (byte) 0, 85, (byte) 0));
		changelist.add(new ChangingBlock(390, (byte) 0, 0, (byte) 0));
		changelist.add(new ChangingBlock(416, (byte) 0, 0, (byte) 0));
		changelist.add(new ChangingBlock(425, (byte) 0, 0, (byte) 0));
		changelist.add(new ChangingBlock(425, (byte) 1, 0, (byte) 0));
		changelist.add(new ChangingBlock(425, (byte) 2, 0, (byte) 0));
		changelist.add(new ChangingBlock(425, (byte) 3, 0, (byte) 0));
		changelist.add(new ChangingBlock(425, (byte) 4, 0, (byte) 0));
		changelist.add(new ChangingBlock(425, (byte) 5, 0, (byte) 0));
		changelist.add(new ChangingBlock(425, (byte) 6, 0, (byte) 0));
		changelist.add(new ChangingBlock(425, (byte) 7, 0, (byte) 0));
		changelist.add(new ChangingBlock(425, (byte) 8, 0, (byte) 0));
		changelist.add(new ChangingBlock(425, (byte) 9, 0, (byte) 0));
		changelist.add(new ChangingBlock(425, (byte) 10, 0, (byte) 0));
		changelist.add(new ChangingBlock(425, (byte) 11, 0, (byte) 0));
		changelist.add(new ChangingBlock(425, (byte) 12, 0, (byte) 0));
		changelist.add(new ChangingBlock(425, (byte) 13, 0, (byte) 0));
		changelist.add(new ChangingBlock(425, (byte) 14, 0, (byte) 0));
		changelist.add(new ChangingBlock(425, (byte) 15, 0, (byte) 0));
		changelist.add(new ChangingBlock(427, (byte) 0, 324, (byte) 0));
		changelist.add(new ChangingBlock(428, (byte) 0, 324, (byte) 0));
		changelist.add(new ChangingBlock(429, (byte) 0, 324, (byte) 0));
		changelist.add(new ChangingBlock(430, (byte) 0, 324, (byte) 0));
		changelist.add(new ChangingBlock(431, (byte) 0, 324, (byte) 0));
		changelist.add(new ChangingBlock(201, (byte) 0, 98, (byte) 0));
		changelist.add(new ChangingBlock(202, (byte) 0, 98, (byte) 0));
		for(byte d = 0; d <= 15; d++){
			changelist.add(new ChangingBlock(203, d, 109, d));	
		}
		changelist.add(new ChangingBlock(205, (byte) 0, 44, (byte) 5));
		changelist.add(new ChangingBlock(206, (byte) 0, 24, (byte) 1));
		changelist.add(new ChangingBlock(213, (byte) 0, 89, (byte) 0));
		changelist.add(new ChangingBlock(214, (byte) 0, 35, (byte) 14));
		changelist.add(new ChangingBlock(215, (byte) 0, 112, (byte) 0));
		changelist.add(new ChangingBlock(216, (byte) 0, 155, (byte) 0));

		
		getLogger().info(changelist.size()+"개의 목록 로드");
		getLogger().info("1.8 블럭을 1.5.2 블럭으로 바꾸는 플러그인 로드 완료!");
	}
	
	public void onDisable(){
		getLogger().info("1.8 블럭을 1.5.2 블럭으로 바꾸는 플러그인 언로드 완료");
	}
	
	public boolean onCommand(CommandSender talker, Command command, String str, String[] args){
		if(talker instanceof Player){
			Player p = (Player) talker;
			if(str.equalsIgnoreCase("bbc") && p.isOp()){
				if(args.length <= 5){
					p.sendMessage("/bbc x좌표 y좌표 z좌표 x좌표 y좌표 z좌표");
					return true;
				}else{
					Checkpos(p, args);
					return true;
				}
			}
		}
		return true;
	}
	
	public void Checkpos(Player p, String args[]){
		try{
			int x1 = Integer.valueOf(args[0]);
			int y1 = Integer.valueOf(args[1]);
			int z1 = Integer.valueOf(args[2]);
			int x2 = Integer.valueOf(args[3]);
			int y2 = Integer.valueOf(args[4]);
			int z2 = Integer.valueOf(args[5]);
			ChangeBlock(p, x1, y1, z1, x2, y2, z2);
		}catch(Exception e){
			p.sendMessage("좌표는 숫자로만!");
		}
	}
	
	public void ChangeBlock(Player p, int x1, int y1, int z1, int x2, int y2, int z2){
		if(x1 > x2){
			int tmpint = x1;
			x1 = x2;
			x2 = tmpint;
		}
		if(y1 > y2){
			int tmpint = y1;
			y1 = y2;
			y2 = tmpint;
		}		
		if(z1 > z2){
			int tmpint = z1;
			z1 = z2;
			z2 = tmpint;
		}
		int amt = 0;
		for(int tmpy = y1; tmpy <= y2; tmpy++){
			for(int tmpx = x1; tmpx <= x2; tmpx++){
				for(int tmpz = z1; tmpz <= z2; tmpz++){
					Location l = new Location(p.getWorld(), tmpx, tmpy, tmpz);
					Block b = l.getBlock();
					for(ChangingBlock cb : changelist){
						if(cb.replaceId == b.getTypeId() && cb.replaceData == b.getData()){
							b.setTypeIdAndData(cb.changeId, cb.changeData, true);
							amt++;
						}
					}
				}
			}
		}
		if(amt == 0){
			p.sendMessage("바뀐 블럭이 없습니다.");
		}else{
			p.sendMessage(amt+"개의 블럭 변경");
		}
	}
}
