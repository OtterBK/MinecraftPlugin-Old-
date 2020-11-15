package me.Bokum.OverWatch;

import org.bukkit.block.Block;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.plugin.java.JavaPlugin;

import me.Bokum.Messenger.Messenger;

public class Main extends JavaPlugin implements Listener{
	
	public static Main instance;
	
	public void onEnable(){
		getServer().getPluginManager().registerEvents(this, this);
		getLogger().info("오버워치 시스템 플러그인(거점 점령)이 로드되었습니다.");
		instance = this;
	}
	
	public void onDisable(){
		getLogger().info("오버워치 시스템 플러그인이 언로드 되었습니다.");
	}
	
	public boolean onCommand(CommandSender talker, Command command, String str, String[] args){
		if(talker instanceof Player){
			Player p = (Player) talker;
			if(str.equalsIgnoreCase("ows")){
				Messenger mg = new Messenger();
				if(args.length <= 0){
					mg.commandMainMsg(p);
				} else {
					CommandManager cm = new CommandManager();
					switch(args[0]){
					case "team":
						cm.commandTeam(p, args); break;
					case "setspawn": 
						cm.commandSetSpawn(p, args); break;
					case "setarea":
						
					}	
				}
			}
		}
		
		return false;
	}
	
	@EventHandler
	public void onPlayerInteract(PlayerInteractEvent e){
		Player p = e.getPlayer();
		if(CommandManager.setAreaPlayer.containsKey(p.getName())){
			SetArea area = CommandManager.setAreaPlayer.get(p.getName());
			if(e.getClickedBlock() != null){
				Block b = e.getClickedBlock();
				if(area.pos1 == null){
					area.pos1 = b.getLocation();
					p.sendMessage("§f[§b 거점 설정 §f] 해당 블럭의 위치를 Pos1로 설정하였습니다.");
				} else {
					area.pos2 = b.getLocation();
					p.sendMessage("§f[§b 거점 설정 §f] 해당 블럭의 위치를 Pos2로 설정하였습니다. 거점 설정을 완료했습니다.");
					area.save();
				}
			}
		}

	}

}
