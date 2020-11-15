package me.Bokum.PhantasyWar;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.plugin.java.JavaPlugin;

public class Main extends JavaPlugin implements Listener{
	public static Location redSpawn;
	public static Location blueSpawn;
	public static HashMap<String, String> teamList = new HashMap<String, String>();
	public static Location coreCatchLocation;
	public static String catchingTeam = "중립";
	public static Main instance;
	public static List<Location> coreBlock = new ArrayList<Location>();
	public static String MS = "§f[ §aBJ뱀사 §f] ";
	
	public void onEnable(){
		getServer().getPluginManager().registerEvents(this, this);
		instance = this;
		loadConfig();
		getLogger().info("판타지 공성전 플러그인 로드 완료");
	}
	
	private void loadConfig(){
		try{
			ConfigurationSection sec = getConfig().getConfigurationSection("redSpawn");
			redSpawn = new Location(getServer().getWorld(sec.getString("world")), sec.getInt("x"), sec.getInt("y"), sec.getInt("z"));
			sec = getConfig().getConfigurationSection("blueSpawn");
			blueSpawn = new Location(getServer().getWorld(sec.getString("world")), sec.getInt("x"), sec.getInt("y"), sec.getInt("z"));
		}catch(Exception e){
			getLogger().info("레드팀 또는 블루팀 스폰이 설정되지 않았습니다.");
		}
		try{
			ConfigurationSection sec = getConfig().getConfigurationSection("coreCatchLocation");
			coreCatchLocation = new Location(getServer().getWorld(sec.getString("world")), sec.getInt("x"), sec.getInt("y"), sec.getInt("z"));
		}catch(Exception e){
			getLogger().info("점령지점이 설정되지 않았습니다.");
		}
		try{
			for(int j = 1; j <= getConfig().getInt("coreBlockAmount"); j++){
				  coreBlock.add(new Location(getServer().getWorld("world"), getConfig().getInt("coreBlock_"+j+"_x"), getConfig().getInt("coreBlock_"+j+"_y"), getConfig().getInt("coreBlock_"+j+"_z")));
			}
		}catch (Exception e){
			 getLogger().info("코어블럭이 완벽히 설정 되어있지 않습니다. 버그 발생의 우려가 있습니다.");
		}
	}
	
	public void onDisable(){
		getLogger().info("판타지 공성전 플러그인 언로드 완료");
	}
	
	public boolean onCommand(CommandSender talker, Command command, String str, String[] args){
		if(talker instanceof Player){
			Player p = (Player) talker;
			if(str.equalsIgnoreCase("pw")){
				if(args.length <= 0){
					helpMessages(p);
				}else{
					if(args[0].equalsIgnoreCase("red")){
						setRedSpawn(p);
					}else if(args[0].equalsIgnoreCase("blue")){
						setBlueSpawn(p);
					}else if(args[0].equalsIgnoreCase("block")){
						setWool(p, args[1], args[2], args[3], args[4],args[5], args[6]);
					}else if(args[0].equalsIgnoreCase("core")){
						setCoreLocation(p);
					}else if(args[0].equalsIgnoreCase("join")){
						if(args.length <= 1){
							p.sendMessage(MS+"참여할 팀을 선택해주세요. \na§c/pw join red/blue");
						}else if(args[1].equalsIgnoreCase("red")){
							teamList.put(p.getName(), "레드");
							p.sendMessage(MS+"레드팀으로 설정되셨습니다.");
						}else if(args[1].equalsIgnoreCase("blue")){
							teamList.put(p.getName(), "블루");
							p.sendMessage(MS+"블루팀으로 설정되셨습니다.");
						}
					}
				}
			}
		}
		return false;
	}
	
	private void helpMessages(Player p){
		p.sendMessage(MS+"/pw block 좌표");
		p.sendMessage(MS+"/pw red");
		p.sendMessage(MS+"/pw blue");
		p.sendMessage(MS+"/pw join <blue/red>");
	}
	
	private void setRedSpawn(Player p){
		if(!getConfig().isConfigurationSection("redSpawn")) getConfig().createSection("redSpawn");
		ConfigurationSection sec = getConfig().getConfigurationSection("redSpawn");
		sec.set("world", p.getWorld().getName());
		sec.set("x", p.getLocation().getBlockX());
		sec.set("y", p.getLocation().getBlockY()+1);
		sec.set("z", p.getLocation().getBlockZ());
		saveConfig();
		loadConfig();
		p.sendMessage(MS+"레드 스폰 설정완료");
	}
	
	private void setBlueSpawn(Player p){
		if(!getConfig().isConfigurationSection("blueSpawn")) getConfig().createSection("blueSpawn");
		ConfigurationSection sec = getConfig().getConfigurationSection("blueSpawn");
		sec.set("world", p.getWorld().getName());
		sec.set("x", p.getLocation().getBlockX());
		sec.set("y", p.getLocation().getBlockY()+1);
		sec.set("z", p.getLocation().getBlockZ());
		saveConfig();
		loadConfig();
		p.sendMessage(MS+"블루 스폰 설정완료");
	}
	
	private void setCoreLocation(Player p){
		if(!getConfig().isConfigurationSection("coreCatchLocation")) getConfig().createSection("coreCatchLocation");
		ConfigurationSection sec = getConfig().getConfigurationSection("coreCatchLocation");
		sec.set("world", p.getWorld().getName());
		sec.set("x", p.getLocation().getBlockX());
		sec.set("y", p.getLocation().getBlockY());
		sec.set("z", p.getLocation().getBlockZ());
		saveConfig();
		loadConfig();
		p.sendMessage(MS+"점령지 설정완료");
	}
	
	private void setWool(Player p, String x1, String y1, String z1, String x2, String y2, String z2){
		coreBlock.clear();
		int amt = 0;
		Location pos1 = new Location(getServer().getWorld("world"), Integer.valueOf(x1), Integer.valueOf(y1), Integer.valueOf(z1));
		Location pos2 = new Location(getServer().getWorld("world"), Integer.valueOf(x2), Integer.valueOf(y2), Integer.valueOf(z2));
		for(int x = pos1.getBlockX(); x <= pos2.getBlockX(); x++){
			for(int y = pos1.getBlockY(); y <= pos2.getBlockY(); y++){
				for(int z = pos1.getBlockZ(); z <= pos2.getBlockZ(); z++){
					Location block_loc = new Location(getServer().getWorld("world"), Integer.valueOf(x), Integer.valueOf(y), Integer.valueOf(z));
					if(block_loc.getBlock().getType() == Material.WOOL){
						amt++;
						getConfig().set("coreBlock_"+amt+"_x", block_loc.getBlockX());
						getConfig().set("coreBlock_"+amt+"_y", block_loc.getBlockY());
						getConfig().set("coreblock_"+amt+"_z", block_loc.getBlockZ());			
						coreBlock.add(block_loc);
					}
				}
			}
		}
		getConfig().set("coreBlockAmount", amt);
		saveConfig();
		loadConfig();
		p.sendMessage(MS+"설정완료");
	}
	
	@EventHandler
	public void onPlayerMove(PlayerMoveEvent e){
		if(e.getFrom().getBlockX() == e.getTo().getBlockX() && e.getFrom().getBlockY() == e.getTo().getBlockY()
				&& e.getFrom().getBlockZ() == e.getTo().getBlockZ()) return;
		Location nowLocation = new Location(e.getTo().getWorld(), e.getTo().getBlockX(), e.getTo().getBlockY(), e.getTo().getBlockZ());
		Player p = e.getPlayer();
		if(nowLocation.equals(coreCatchLocation)){
			Timer timer = new Timer(p);
		}
		if(!teamList.containsKey(p.getName())) return;
		if(redSpawn.distance(p.getLocation()) <= 75 && teamList.get(p.getName()).equalsIgnoreCase("블루") && !catchingTeam.equalsIgnoreCase("블루")){
			p.sendMessage(MS+"점령을 하지 않으면 적팀의 기지에 입장이 불가능합니다.");
			e.setCancelled(true);
		}else if(blueSpawn.distance(p.getLocation()) <= 75 && teamList.get(p.getName()).equalsIgnoreCase("레드") && !catchingTeam.equalsIgnoreCase("레드")){
			p.sendMessage(MS+"점령을 하지 않으면 적팀의 기지에 입장이 불가능합니다.");
			e.setCancelled(true);
		}
	}
}
