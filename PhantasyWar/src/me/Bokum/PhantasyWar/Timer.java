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
					p.sendMessage(Main.MS+"���ɱ��� ���� �ð� "+coreCatchTime--+"��");
				}
			}
		}, 0);
		Bukkit.getScheduler().scheduleSyncDelayedTask(Main.instance, new Runnable(){
			public void run(){
				Player p = Main.instance.getServer().getPlayer(name);
				Location ploc = new Location(p.getWorld(), p.getLocation().getBlockX(), p.getLocation().getBlockY(), p.getLocation().getBlockZ());
				if(ploc.equals(Main.coreCatchLocation) && coreCatchTime == 9){
					p.sendMessage(Main.MS+"���ɱ��� ���� �ð� "+coreCatchTime--+"��");
				}
			}
		}, 20);
		Bukkit.getScheduler().scheduleSyncDelayedTask(Main.instance, new Runnable(){
			public void run(){
				Player p = Main.instance.getServer().getPlayer(name);
				Location ploc = new Location(p.getWorld(), p.getLocation().getBlockX(), p.getLocation().getBlockY(), p.getLocation().getBlockZ());
				if(ploc.equals(Main.coreCatchLocation) && coreCatchTime == 8){
					p.sendMessage(Main.MS+"���ɱ��� ���� �ð� "+coreCatchTime--+"��");
				}
			}
		}, 40);
		Bukkit.getScheduler().scheduleSyncDelayedTask(Main.instance, new Runnable(){
			public void run(){
				Player p = Main.instance.getServer().getPlayer(name);
				Location ploc = new Location(p.getWorld(), p.getLocation().getBlockX(), p.getLocation().getBlockY(), p.getLocation().getBlockZ());
				if(ploc.equals(Main.coreCatchLocation) && coreCatchTime == 7){
					p.sendMessage(Main.MS+"���ɱ��� ���� �ð� "+coreCatchTime--+"��");
				}
			}
		}, 60);
		Bukkit.getScheduler().scheduleSyncDelayedTask(Main.instance, new Runnable(){
			public void run(){
				Player p = Main.instance.getServer().getPlayer(name);
				Location ploc = new Location(p.getWorld(), p.getLocation().getBlockX(), p.getLocation().getBlockY(), p.getLocation().getBlockZ());
				if(ploc.equals(Main.coreCatchLocation) && coreCatchTime == 6){
					p.sendMessage(Main.MS+"���ɱ��� ���� �ð� "+coreCatchTime--+"��");
				}
			}
		}, 80);
		Bukkit.getScheduler().scheduleSyncDelayedTask(Main.instance, new Runnable(){
			public void run(){
				Player p = Main.instance.getServer().getPlayer(name);
				Location ploc = new Location(p.getWorld(), p.getLocation().getBlockX(), p.getLocation().getBlockY(), p.getLocation().getBlockZ());
				if(ploc.equals(Main.coreCatchLocation) && coreCatchTime == 5){
					p.sendMessage(Main.MS+"���ɱ��� ���� �ð� "+coreCatchTime--+"��");
				}
			}
		}, 100);
		Bukkit.getScheduler().scheduleSyncDelayedTask(Main.instance, new Runnable(){
			public void run(){
				Player p = Main.instance.getServer().getPlayer(name);
				Location ploc = new Location(p.getWorld(), p.getLocation().getBlockX(), p.getLocation().getBlockY(), p.getLocation().getBlockZ());
				if(ploc.equals(Main.coreCatchLocation) && coreCatchTime == 4){
					p.sendMessage(Main.MS+"���ɱ��� ���� �ð� "+coreCatchTime--+"��");
				}
			}
		}, 120);
		Bukkit.getScheduler().scheduleSyncDelayedTask(Main.instance, new Runnable(){
			public void run(){
				Player p = Main.instance.getServer().getPlayer(name);
				Location ploc = new Location(p.getWorld(), p.getLocation().getBlockX(), p.getLocation().getBlockY(), p.getLocation().getBlockZ());
				if(ploc.equals(Main.coreCatchLocation) && coreCatchTime == 3){
					p.sendMessage(Main.MS+"���ɱ��� ���� �ð� "+coreCatchTime--+"��");
				}
			}
		}, 140);
		Bukkit.getScheduler().scheduleSyncDelayedTask(Main.instance, new Runnable(){
			public void run(){
				Player p = Main.instance.getServer().getPlayer(name);
				Location ploc = new Location(p.getWorld(), p.getLocation().getBlockX(), p.getLocation().getBlockY(), p.getLocation().getBlockZ());
				if(ploc.equals(Main.coreCatchLocation) && coreCatchTime == 2){
					p.sendMessage(Main.MS+"���ɱ��� ���� �ð� "+coreCatchTime--+"��");
				}
			}
		}, 160);
		Bukkit.getScheduler().scheduleSyncDelayedTask(Main.instance, new Runnable(){
			public void run(){
				Player p = Main.instance.getServer().getPlayer(name);
				Location ploc = new Location(p.getWorld(), p.getLocation().getBlockX(), p.getLocation().getBlockY(), p.getLocation().getBlockZ());
				if(ploc.equals(Main.coreCatchLocation) && coreCatchTime == 1){
					p.sendMessage(Main.MS+"���ɱ��� ���� �ð� "+coreCatchTime--+"��");
				}
			}
		}, 180);
		Bukkit.getScheduler().scheduleSyncDelayedTask(Main.instance, new Runnable(){
			public void run(){
				Player p = Main.instance.getServer().getPlayer(name);
				Location ploc = new Location(p.getWorld(), p.getLocation().getBlockX(), p.getLocation().getBlockY(), p.getLocation().getBlockZ());
				if(ploc.equals(Main.coreCatchLocation) && coreCatchTime == 0){
					p.sendMessage(Main.MS+"���ɿϷ�");
					if(!Main.teamList.containsKey(name)){
						p.sendMessage(Main.MS+"������ ���� �����ϴ�!");
						return;
					}
					Main.catchingTeam = Main.teamList.get(name);
					Bukkit.broadcastMessage(Main.MS+Main.catchingTeam+"�� "+name+"�Բ��� ���ɿ� �����ϼ̽��ϴ�!");
					if(Main.catchingTeam.equalsIgnoreCase("���")){
						for(Location l : Main.coreBlock){
							Block b = l.getBlock();
							b.setTypeIdAndData(35, (byte) 11, true);
						}
						for(Player t : Bukkit.getOnlinePlayers()){
							if(Main.teamList.containsKey(t.getName()) && Main.teamList.get(t.getName()).equalsIgnoreCase("����") && t.getLocation().distance(Main.blueSpawn) <= 75){
								t.sendMessage(Main.MS+"������� ������ �Ͽ� ������ �̵��˴ϴ�.");
								t.teleport(Main.redSpawn);
							}
						}
					}else if(Main.catchingTeam.equalsIgnoreCase("����")){
						for(Location l : Main.coreBlock){
							Block b = l.getBlock();
							b.setTypeIdAndData(35, (byte) 14, true);
						}
						for(Player t : Bukkit.getOnlinePlayers()){
							if(Main.teamList.containsKey(t.getName()) && Main.teamList.get(t.getName()).equalsIgnoreCase("���") && t.getLocation().distance(Main.redSpawn) <= 75){
								t.sendMessage(Main.MS+"�������� ������ �Ͽ� ������ �̵��˴ϴ�.");
								t.teleport(Main.blueSpawn);
							}
						}
					}
				}
			}
		}, 200);
	}
}
