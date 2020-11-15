package me.Bokum.PhantasyWar;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;

public class Timer {
	public int coreCatchTime = 10;
	public Timer(Player p){
		final String name = p.getName();
		coreCatchTime = 10;
		Bukkit.getScheduler().scheduleSyncDelayedTask(Main.instance, new Runnable(){
			public void run(){
				Player p = Main.instance.getServer().getPlayer(name);
				Location ploc = new Location(p.getWorld(), p.getLocation().getBlockX(), p.getLocation().getBlockY(), p.getLocation().getBlockZ());
				if(ploc.equals(Main.coreCatchLocation) && coreCatchTime == 10){
					p.sendMessage(Main.MS+"점령까지 남은 시간 "+coreCatchTime--+"초");
				}
			}
		}, 0);
		Bukkit.getScheduler().scheduleSyncDelayedTask(Main.instance, new Runnable(){
			public void run(){
				Player p = Main.instance.getServer().getPlayer(name);
				Location ploc = new Location(p.getWorld(), p.getLocation().getBlockX(), p.getLocation().getBlockY(), p.getLocation().getBlockZ());
				if(ploc.equals(Main.coreCatchLocation) && coreCatchTime == 9){
					p.sendMessage(Main.MS+"점령까지 남은 시간 "+coreCatchTime--+"초");
				}
			}
		}, 20);
		Bukkit.getScheduler().scheduleSyncDelayedTask(Main.instance, new Runnable(){
			public void run(){
				Player p = Main.instance.getServer().getPlayer(name);
				Location ploc = new Location(p.getWorld(), p.getLocation().getBlockX(), p.getLocation().getBlockY(), p.getLocation().getBlockZ());
				if(ploc.equals(Main.coreCatchLocation) && coreCatchTime == 8){
					p.sendMessage(Main.MS+"점령까지 남은 시간 "+coreCatchTime--+"초");
				}
			}
		}, 40);
		Bukkit.getScheduler().scheduleSyncDelayedTask(Main.instance, new Runnable(){
			public void run(){
				Player p = Main.instance.getServer().getPlayer(name);
				Location ploc = new Location(p.getWorld(), p.getLocation().getBlockX(), p.getLocation().getBlockY(), p.getLocation().getBlockZ());
				if(ploc.equals(Main.coreCatchLocation) && coreCatchTime == 7){
					p.sendMessage(Main.MS+"점령까지 남은 시간 "+coreCatchTime--+"초");
				}
			}
		}, 60);
		Bukkit.getScheduler().scheduleSyncDelayedTask(Main.instance, new Runnable(){
			public void run(){
				Player p = Main.instance.getServer().getPlayer(name);
				Location ploc = new Location(p.getWorld(), p.getLocation().getBlockX(), p.getLocation().getBlockY(), p.getLocation().getBlockZ());
				if(ploc.equals(Main.coreCatchLocation) && coreCatchTime == 6){
					p.sendMessage(Main.MS+"점령까지 남은 시간 "+coreCatchTime--+"초");
				}
			}
		}, 80);
		Bukkit.getScheduler().scheduleSyncDelayedTask(Main.instance, new Runnable(){
			public void run(){
				Player p = Main.instance.getServer().getPlayer(name);
				Location ploc = new Location(p.getWorld(), p.getLocation().getBlockX(), p.getLocation().getBlockY(), p.getLocation().getBlockZ());
				if(ploc.equals(Main.coreCatchLocation) && coreCatchTime == 5){
					p.sendMessage(Main.MS+"점령까지 남은 시간 "+coreCatchTime--+"초");
				}
			}
		}, 100);
		Bukkit.getScheduler().scheduleSyncDelayedTask(Main.instance, new Runnable(){
			public void run(){
				Player p = Main.instance.getServer().getPlayer(name);
				Location ploc = new Location(p.getWorld(), p.getLocation().getBlockX(), p.getLocation().getBlockY(), p.getLocation().getBlockZ());
				if(ploc.equals(Main.coreCatchLocation) && coreCatchTime == 4){
					p.sendMessage(Main.MS+"점령까지 남은 시간 "+coreCatchTime--+"초");
				}
			}
		}, 120);
		Bukkit.getScheduler().scheduleSyncDelayedTask(Main.instance, new Runnable(){
			public void run(){
				Player p = Main.instance.getServer().getPlayer(name);
				Location ploc = new Location(p.getWorld(), p.getLocation().getBlockX(), p.getLocation().getBlockY(), p.getLocation().getBlockZ());
				if(ploc.equals(Main.coreCatchLocation) && coreCatchTime == 3){
					p.sendMessage(Main.MS+"점령까지 남은 시간 "+coreCatchTime--+"초");
				}
			}
		}, 140);
		Bukkit.getScheduler().scheduleSyncDelayedTask(Main.instance, new Runnable(){
			public void run(){
				Player p = Main.instance.getServer().getPlayer(name);
				Location ploc = new Location(p.getWorld(), p.getLocation().getBlockX(), p.getLocation().getBlockY(), p.getLocation().getBlockZ());
				if(ploc.equals(Main.coreCatchLocation) && coreCatchTime == 2){
					p.sendMessage(Main.MS+"점령까지 남은 시간 "+coreCatchTime--+"초");
				}
			}
		}, 160);
		Bukkit.getScheduler().scheduleSyncDelayedTask(Main.instance, new Runnable(){
			public void run(){
				Player p = Main.instance.getServer().getPlayer(name);
				Location ploc = new Location(p.getWorld(), p.getLocation().getBlockX(), p.getLocation().getBlockY(), p.getLocation().getBlockZ());
				if(ploc.equals(Main.coreCatchLocation) && coreCatchTime == 1){
					p.sendMessage(Main.MS+"점령까지 남은 시간 "+coreCatchTime--+"초");
				}
			}
		}, 180);
		Bukkit.getScheduler().scheduleSyncDelayedTask(Main.instance, new Runnable(){
			public void run(){
				Player p = Main.instance.getServer().getPlayer(name);
				Location ploc = new Location(p.getWorld(), p.getLocation().getBlockX(), p.getLocation().getBlockY(), p.getLocation().getBlockZ());
				if(ploc.equals(Main.coreCatchLocation) && coreCatchTime == 0){
					p.sendMessage(Main.MS+"점령완료");
					if(!Main.teamList.containsKey(name)){
						p.sendMessage(Main.MS+"설정된 팀이 없습니다!");
						return;
					}
					Main.catchingTeam = Main.teamList.get(name);
					Bukkit.broadcastMessage(Main.MS+Main.catchingTeam+"팀 "+name+"님께서 점령에 성공하셨습니다!");
					if(Main.catchingTeam.equalsIgnoreCase("블루")){
						for(Location l : Main.coreBlock){
							Block b = l.getBlock();
							b.setTypeIdAndData(35, (byte) 11, true);
						}
						for(Player t : Bukkit.getOnlinePlayers()){
							if(Main.teamList.containsKey(t.getName()) && Main.teamList.get(t.getName()).equalsIgnoreCase("레드") && t.getLocation().distance(Main.blueSpawn) <= 75){
								t.sendMessage(Main.MS+"블루팀이 점령을 하여 기지로 이동됩니다.");
								t.teleport(Main.redSpawn);
							}
						}
					}else if(Main.catchingTeam.equalsIgnoreCase("레드")){
						for(Location l : Main.coreBlock){
							Block b = l.getBlock();
							b.setTypeIdAndData(35, (byte) 14, true);
						}
						for(Player t : Bukkit.getOnlinePlayers()){
							if(Main.teamList.containsKey(t.getName()) && Main.teamList.get(t.getName()).equalsIgnoreCase("블루") && t.getLocation().distance(Main.redSpawn) <= 75){
								t.sendMessage(Main.MS+"레드팀이 점령을 하여 기지로 이동됩니다.");
								t.teleport(Main.blueSpawn);
							}
						}
					}
				}
			}
		}, 200);
	}
}
