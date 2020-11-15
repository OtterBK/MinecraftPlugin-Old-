package me.Bokum.AutoCrops;

import org.bukkit.Material;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.java.JavaPlugin;

public class Main extends JavaPlugin implements Listener{
	
	public void onEnable(){
		getServer().getPluginManager().registerEvents(this, this);
		getLogger().info("자동 씨앗심기 플러그인이 로드되었습니다."); //버킷에다 활성화 문장출력
	}
	
	public void onDisable(){
		getLogger().info("자동 씨앗심기 플러그인이 비활성화 되었습니다."); //버킷에다 비활성화 문장출력
	}
	
	@EventHandler
	public void onBlockBreak(BlockBreakEvent e){ //블럭을 부쉈을때 (플러그인에 의해 기능이 다 실행될때까지 부숴지는걸 막음)
		//블럭의 코드가 59이고 블럭의 데이터가 7이면
		//@IF i <blockid> = 59
		//밀 = 59:7
		//씨앗 = 59:0
		//&& - 그리고 == - 같다
		//e.getBlock().getData() - 블럭의 데이터 가져오기
		//e.getBlock() - 블럭 가져오기 e.getBlock().getTypeId() - 블럭 코드 가져오기
		//e.getBlock().getLocation() - 블럭을 부순 좌표
		//e.getBlock().getLocation().getBlock() - 블럭을 부순 좌표에 존재하는 블럭
		if(e.getBlock().getTypeId() == 59 && e.getBlock().getData() == 7){//부순 블럭의 코드가 59이고 데이터가 7이면
			e.getBlock().breakNaturally(); //그 블럭을 부숴라
			e.setCancelled(true); // 블럭을 부순 사실을 없애자
			e.getBlock().getLocation().getBlock().setTypeIdAndData(59,(byte) 0, true);// 씨앗으로 변경해준다
		}
		if(e.getBlock().getTypeId() == 104 && e.getBlock().getData() == 7){//부순 블럭의 코드가 104이고 데이터가 7이면
			e.getBlock().breakNaturally(); //그 블럭을 부숴라
			e.setCancelled(true); // 블럭을 부순 사실을 없애자
			e.getBlock().getLocation().getBlock().setTypeIdAndData(104,(byte) 0, true);// 호박씨앗으로 변경해준다
		}
		if(e.getBlock().getTypeId() == 105 && e.getBlock().getData() == 7){//부순 블럭의 코드가 105이고 데이터가 7이면
			e.getBlock().breakNaturally(); //그 블럭을 부숴라
			e.setCancelled(true); // 블럭을 부순 사실을 없애자
			e.getBlock().getLocation().getBlock().setTypeIdAndData(105,(byte) 0, true);// 수박씨앗으로 변경해준다
		}
	}// 기능이 다 끝났으니 블럭을 마저 부숨
	
}
