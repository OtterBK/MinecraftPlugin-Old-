package me.Bokum.RBD;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.java.JavaPlugin;

public class Main extends JavaPlugin implements Listener{

	private String MS = "§f[ §aRDB §f] §e";
	private HashMap<String, Location> pos1List = new HashMap<String, Location>();
	private HashMap<String, Location> pos2List = new HashMap<String, Location>();
	private List<String> areaList = new ArrayList<String>();
	private HashMap<String, Location> areaPos1List = new HashMap<String, Location>();
	private HashMap<String, Location> areaPos2List = new HashMap<String, Location>();
	
	
	public void onEnable(){
		getServer().getPluginManager().registerEvents(this, this);
		getLogger().info("RDB 플러그인 로드 완료");
		loadConfig();
	}
	
	public void onDisable(){
		getLogger().info("RDB 플러그인 언로드 완료");
	}

	private void loadConfig(){
		areaList.clear();
		areaList.addAll(getConfig().getStringList("areaList"));
		areaPos1List.clear();
		areaPos2List.clear();
		for(String name : areaList){
			Bukkit.broadcastMessage(MS+name);
			if(!getConfig().isConfigurationSection(name)){
				getLogger().info(MS+name+" 영역의 좌표가 없습니다. 다시 설정해주세요.");
				continue;
			}
			try{
			ConfigurationSection sec = getConfig().getConfigurationSection(name);
			areaPos1List.put(name, new Location(getServer().getWorld(sec.getString("world")),
					sec.getInt("pos1_x"), sec.getInt("pos1_y"), sec.getInt("pos1_z")));
			areaPos2List.put(name, new Location(getServer().getWorld(sec.getString("world")),
					sec.getInt("pos2_x"), sec.getInt("pos2_y"), sec.getInt("pos2_z")));
			} catch(Exception e){
				getLogger().info(MS+name+" 영역을 불러오는 도중 오류가 발생했습니다.");
			}
		}
	}
	
	public boolean onCommand(CommandSender talker, Command command, String str, String[] args){
		if(str.equalsIgnoreCase("rbd")){
			if(talker instanceof Player){
				Player p = (Player) talker;
				if(args.length >= 1){
					if(args[0].equalsIgnoreCase("define")){
						if(args.length == 1){
							p.sendMessage(MS+"저장할 영역의 이름을 설정해주세요. /rbd define <영역이름>");
						} else {
							saveArea(p, args[1]);
						}
					} else if(args[0].equalsIgnoreCase("list")){
						if(areaList.size() == 0){
							p.sendMessage(MS+"설정된 영역이 존재하지 않습니다.");
						} else {
							for(String s : areaList){
								p.sendMessage(MS+s);
							}
						}
					}
				} else {
					helpMessages(p);
				}
			} else {
				talker.sendMessage(MS+"콘솔에서는 사용할 수 없는 명령어입니다.");
			}
		}
		return false;
	}
	
	private void helpMessages(Player p){
		p.sendMessage(MS+"/rbd define <이름>");
	}
	
	private void saveArea(Player p, String name){
		if(!pos1List.containsKey(p.getName())){
			p.sendMessage(MS+"1번 지점이 설정되어 있지 않습니다.");
			return;
		} else if(!pos2List.containsKey(p.getName())){
			p.sendMessage(MS+"2번 지점이 설정되어 있지 않습니다.");
			return;
		}
		if(!getConfig().isConfigurationSection(name)) getConfig().createSection(name);
		ConfigurationSection sec = getConfig().getConfigurationSection(name);
		Location pos1 = pos1List.get(p.getName());
		Location pos2 = pos2List.get(p.getName());
		sec.set("world", pos1.getWorld().getName());
		sec.set("pos1_x", pos1.getBlockX());
		sec.set("pos1_y", pos1.getBlockY());
		sec.set("pos1_z", pos1.getBlockZ());
		sec.set("pos2_x", pos2.getBlockX());
		sec.set("pos2_y", pos2.getBlockY());
		sec.set("pos2_z", pos2.getBlockZ());
		if(areaList.contains(name)) areaList.remove(name);
		areaList.add(name);
		getConfig().set("areaList", areaList);
		saveConfig();
		loadConfig();
		p.sendMessage(MS+name+" 영역 설정완료"
				+ "\n§71번 지점 : §e"+pos1.getBlockX()+", "+pos1.getBlockY()+", "+pos1.getBlockZ()+
				"\n§72번 지점 : §e"+pos2.getBlockX()+", "+pos2.getBlockY()+", "+pos2.getBlockZ());
		
	}
	
	private void savePos1(Player p, Block b){
		Location l = b.getLocation();
		pos1List.put(p.getName(), new Location(b.getWorld(), l.getBlockX(), l.getBlockY(), l.getBlockZ()));
		p.sendMessage(MS+l.getBlockX()+", "+l.getBlockY()+", "+l.getBlockZ()+" 좌표를 1번지점으로 설정했습니다.");
	}
	
	private void savePos2(Player p, Block b){
		Location l = b.getLocation();
		pos2List.put(p.getName(), new Location(b.getWorld(), l.getBlockX(), l.getBlockY(), l.getBlockZ()));
		p.sendMessage(MS+l.getBlockX()+", "+l.getBlockY()+", "+l.getBlockZ()+" 좌표를 2번지점으로 설정했습니다.");
	}
	
	@EventHandler
	public void onPlayerDeath(PlayerDeathEvent e){
		Player p = e.getEntity();
		if(!(p.getKiller() instanceof Player)) return;
		Player k = (Player) p.getKiller();
		for(String areaName : areaList){
			if(!areaPos1List.containsKey(areaName) || !areaPos2List.containsKey(areaName)) continue;
			Location l1 = areaPos1List.get(areaName);
			Location l2 = areaPos2List.get(areaName);
			Location pl = p.getLocation();
			int minX = l1.getBlockX() <= l2.getBlockX() ? l1.getBlockX() : l2.getBlockX();
			int maxX = l1.getBlockX() > l2.getBlockX() ? l1.getBlockX() : l2.getBlockX();
			int minY = l1.getBlockY() <= l2.getBlockY() ? l1.getBlockY() : l2.getBlockY();
			int maxY = l1.getBlockY() > l2.getBlockY() ? l1.getBlockY() : l2.getBlockY();
			int minZ = l1.getBlockZ() <= l2.getBlockZ() ? l1.getBlockZ() : l2.getBlockZ();
			int maxZ = l1.getBlockZ() > l2.getBlockZ() ? l1.getBlockZ() : l2.getBlockZ();
			int X = pl.getBlockX();
			int Y = pl.getBlockY();
			int Z = pl.getBlockZ();
			if((X <= maxX && X >= minX) && (Y <= maxY && Y >= minY) && (Z <= maxZ && Z >= minZ)){
				k.getInventory().addItem(new ItemStack(Material.WOOD_PLATE, 1));
				k.sendMessage(MS+"영역내에서 사람을 죽여 감압판을 얻었습니다.");
				return;
			}
		}
	}
	
	@EventHandler
	public void onClickBlock(PlayerInteractEvent e){
		if(e.getItem() == null || !(e.getItem().getType() == Material.STONE_AXE) || e.getClickedBlock() == null || !e.getPlayer().isOp()) return;
		if(e.getAction() == Action.LEFT_CLICK_BLOCK){
			savePos1(e.getPlayer(), e.getClickedBlock());
			e.setCancelled(true);
		}else if(e.getAction() == Action.RIGHT_CLICK_BLOCK){
			savePos2(e.getPlayer(), e.getClickedBlock());
			e.setCancelled(true);
		}
	}
	
}
