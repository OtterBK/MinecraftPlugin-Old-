package me.Bokum.SMG.Games;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.entity.Player;
import org.bukkit.event.block.Action;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.event.entity.EntityDamageByBlockEvent;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.entity.EntityDamageEvent.DamageCause;
import org.bukkit.event.entity.EntityRegainHealthEvent;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.player.PlayerDropItemEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

import me.Bokum.SMG.Main;
import me.Bokum.SMG.Messenger.Messenger;
import me.Bokum.SMG.Minigame.Minigame;
import me.Bokum.SMG.Utility.Cooldown;
import me.Bokum.SMG.Utility.Utility;
import net.milkbowl.vault.economy.EconomyResponse;

public class AvoidAnvil extends Minigame{
	public Location pos1;
	public Location pos2;
	public Cooldown cooldown;
	public ItemStack jumpSkill;
	public int anvilcnt = 1;
	
	public AvoidAnvil(String title, int max, int min){
		super(title, max, min);
		cooldown = new Cooldown(this);
		
		jumpSkill = new ItemStack(280, 1);
		ItemMeta meta = jumpSkill.getItemMeta();
		meta.setDisplayName("��f[ ��a���� ��ȭ ��f]");
		jumpSkill.setItemMeta(meta);
		
		helpInventory = Bukkit.createInventory(null, 27, "��c��l�����");
		
		ItemStack item = new ItemStack(34, 1);
		ItemMeta meta2 = item.getItemMeta();
		meta2.setDisplayName("��6���");
		item.setItemMeta(meta2);
		for(int i = 0; i <= 9; i++){
			helpInventory.setItem(i, item);
		}
		for(int i = 17; i < 27; i++){
			helpInventory.setItem(i, item);
		}
		
		item = new ItemStack(Material.BOOK, 1);
		meta2 = item.getItemMeta();
		meta2.setDisplayName("��e�÷��� ���");
		item.setItemMeta(meta2);
		helpInventory.setItem(11, item);
		
		item = new ItemStack(Material.BOOK, 1);
		meta2 = item.getItemMeta();
		meta2.setDisplayName("��e���� ��Ģ");
		item.setItemMeta(meta2);
		helpInventory.setItem(13, item);
		
		loadConfig();
	}
	
	public void loadConfig(){
		try{
			ConfigurationSection sec = Main.instance.getConfig().getConfigurationSection(gameTitle).getConfigurationSection("joinPos");
			this.joinPos = new Location(Bukkit.getWorld(sec.getString("world")), sec.getInt("x"), sec.getInt("y"), sec.getInt("z"));
		}catch(Exception e){
			Main.instance.getLogger().info(gameTitle+" ������ ���� ������ �������� �ʾҽ��ϴ�.");
		}
		try{
			ConfigurationSection sec = Main.instance.getConfig().getConfigurationSection(gameTitle).getConfigurationSection("startPos");
			this.startPos = new Location(Bukkit.getWorld(sec.getString("world")), sec.getInt("x"), sec.getInt("y"), sec.getInt("z"));
		}catch(Exception e){
			Main.instance.getLogger().info(gameTitle+" ������ ���� ������ �������� �ʾҽ��ϴ�.");
		}
		try{
			ConfigurationSection sec = Main.instance.getConfig().getConfigurationSection(gameTitle).getConfigurationSection("pos1");
			this.pos1 = new Location(Bukkit.getWorld(sec.getString("world")), sec.getInt("x"), sec.getInt("y"), sec.getInt("z"));
		}catch(Exception e){
			Main.instance.getLogger().info(gameTitle+" ������ pos1 ������ �������� �ʾҽ��ϴ�.");
		}
		try{
			ConfigurationSection sec = Main.instance.getConfig().getConfigurationSection(gameTitle).getConfigurationSection("pos2");
			this.pos2 = new Location(Bukkit.getWorld(sec.getString("world")), sec.getInt("x"), sec.getInt("y"), sec.getInt("z"));
		}catch(Exception e){
			Main.instance.getLogger().info(gameTitle+" ������ pos2 ������ �������� �ʾҽ��ϴ�.");
		}
		try{
			final int x1 = pos1.getBlockX() >= pos2.getBlockX() ? pos2.getBlockX() : pos1.getBlockX();
			final int x2 = pos1.getBlockX() >= pos2.getBlockX() ? pos1.getBlockX() : pos2.getBlockX();
			final int y1 = pos1.getBlockY() >= pos2.getBlockY() ? pos2.getBlockY() : pos1.getBlockY();
			final int y2 = pos1.getBlockY() >= pos2.getBlockY() ? pos1.getBlockY() : pos2.getBlockY();
			final int z1 = pos1.getBlockZ() >= pos2.getBlockZ() ? pos2.getBlockZ() : pos1.getBlockZ();
			final int z2 = pos1.getBlockZ() >= pos2.getBlockZ() ? pos1.getBlockZ() : pos2.getBlockZ();
			for(int x = x1; x <= x2; x++){
				for(int y = y1; y <= y2; y++){
					for(int z = z1; z <= z2; z++){
						Location l = new Location(pos1.getWorld(), x, y, z);
						if(l.getBlock().getType() == Material.ANVIL) l.getBlock().setType(Material.AIR); 
					}
				}
			}
		}catch(Exception e){
			
		}
	}
	
	public void setLoc(Player p, String args[]){
		if(args.length == 2){
			p.sendMessage("/smg set ava join");
			p.sendMessage("/smg set ava start");
			p.sendMessage("/smg set ava pos1");
			p.sendMessage("/smg set ava pos2");
		}else if(args.length == 3){
			if(args[2].equalsIgnoreCase("join")){
				if(!Main.instance.getConfig().isConfigurationSection(gameTitle)){
					Main.instance.getConfig().createSection(gameTitle);
					Main.instance.saveConfig();
				}
				if(!Main.instance.getConfig().getConfigurationSection(gameTitle).isConfigurationSection("joinPos")){
					Main.instance.getConfig().getConfigurationSection(gameTitle).createSection("joinPos");
					Main.instance.saveConfig();
				}
				ConfigurationSection sec = Main.instance.getConfig().getConfigurationSection(gameTitle).getConfigurationSection("joinPos");
				sec.set("world", p.getWorld().getName());
				sec.set("x", p.getLocation().getBlockX());
				sec.set("y", p.getLocation().getBlockY()+1);
				sec.set("z", p.getLocation().getBlockZ());
				Main.instance.saveConfig();
				loadConfig();
			}else if(args[2].equalsIgnoreCase("start")){
				if(!Main.instance.getConfig().isConfigurationSection(gameTitle)){
					Main.instance.getConfig().createSection(gameTitle);
					Main.instance.saveConfig();
				}
				if(!Main.instance.getConfig().getConfigurationSection(gameTitle).isConfigurationSection("startPos")){
					Main.instance.getConfig().getConfigurationSection(gameTitle).createSection("startPos");
					Main.instance.saveConfig();
				}ConfigurationSection sec = Main.instance.getConfig().getConfigurationSection(gameTitle).getConfigurationSection("startPos");
				sec.set("world", p.getWorld().getName());
				sec.set("x", p.getLocation().getBlockX());
				sec.set("y", p.getLocation().getBlockY()+1);
				sec.set("z", p.getLocation().getBlockZ());
				Main.instance.saveConfig();
				loadConfig();
			}else if(args[2].equalsIgnoreCase("pos1")){
				if(!Main.instance.getConfig().isConfigurationSection(gameTitle)){
					Main.instance.getConfig().createSection(gameTitle);
					Main.instance.saveConfig();
				}
				if(!Main.instance.getConfig().getConfigurationSection(gameTitle).isConfigurationSection("pos1")){
					Main.instance.getConfig().getConfigurationSection(gameTitle).createSection("pos1");
					Main.instance.saveConfig();
				}
				ConfigurationSection sec = Main.instance.getConfig().getConfigurationSection(gameTitle).getConfigurationSection("pos1");
				sec.set("world", p.getWorld().getName());
				sec.set("x", p.getLocation().getBlockX());
				sec.set("y", p.getLocation().getBlockY());
				sec.set("z", p.getLocation().getBlockZ());
				Main.instance.saveConfig();
				loadConfig();
			}else if(args[2].equalsIgnoreCase("pos2")){
				if(!Main.instance.getConfig().isConfigurationSection(gameTitle)){
					Main.instance.getConfig().createSection(gameTitle);
					Main.instance.saveConfig();
				}
				if(!Main.instance.getConfig().getConfigurationSection(gameTitle).isConfigurationSection("pos2")){
					Main.instance.getConfig().getConfigurationSection(gameTitle).createSection("pos2");
					Main.instance.saveConfig();
				}
				ConfigurationSection sec = Main.instance.getConfig().getConfigurationSection(gameTitle).getConfigurationSection("pos2");
				sec.set("world", p.getWorld().getName());
				sec.set("x", p.getLocation().getBlockX());
				sec.set("y", p.getLocation().getBlockY());
				sec.set("z", p.getLocation().getBlockZ());
				Main.instance.saveConfig();
				loadConfig();
			}
		}
	}
	
	public void clearData(){
		for(int i = 0; i < tasknum.length; i++){
			if(tasknum[i] != -5) Utility.Canceltask(minigame, i);
		}
		for(Player p : playerList){
			Main.playingList.remove(p.getName());
		}
		final int x1 = pos1.getBlockX() >= pos2.getBlockX() ? pos2.getBlockX() : pos1.getBlockX();
		final int x2 = pos1.getBlockX() >= pos2.getBlockX() ? pos1.getBlockX() : pos2.getBlockX();
		final int y1 = pos1.getBlockY() >= pos2.getBlockY() ? pos2.getBlockY() : pos1.getBlockY();
		final int y2 = pos1.getBlockY() >= pos2.getBlockY() ? pos1.getBlockY() : pos2.getBlockY();
		final int z1 = pos1.getBlockZ() >= pos2.getBlockZ() ? pos2.getBlockZ() : pos1.getBlockZ();
		final int z2 = pos1.getBlockZ() >= pos2.getBlockZ() ? pos1.getBlockZ() : pos2.getBlockZ();
		for(int x = x1; x <= x2; x++){
			for(int y = y1; y <= y2; y++){
				for(int z = z1; z <= z2; z++){
					Location l = new Location(pos1.getWorld(), x, y, z);
					if(l.getBlock().getType() == Material.ANVIL) l.getBlock().setType(Material.AIR); 
				}
			}
		}
		playerList.clear();
		isStart = false;
		isGameEnd = false;
		isLobbyStart = false;
		Forcestoptimer = 0;
		for(int i = 0; i < tasknum.length; i++){
			tasknum[i] = -5;
			tasktime[i] = -5;
		}
		cooldown.cooldownlist.clear();
	}
	
	public void forceStop(){
		Messenger.sendMessage(playerList, "������ ���� ���� �Ǿ����ϴ�.", true);
		Bukkit.broadcastMessage(Messenger.MS+gameTitle+" ������ ���� ���� �Ǿ����ϴ�.");
		for(Player p : playerList){
			try{
				me.Bokum.EGM.Main.Spawn(p);
			}catch(Exception e){
				p.teleport(Main.lobby);
			}
		}
		clearData();
	}
	
	public void gameJoin(Player p){
		if(playerList.contains(p)){
			p.sendMessage(Messenger.MS+"�̹� ���ӿ� �������̽ʴϴ�.");
			return;
		}
		if(playerList.size() >= maxPlayer){
			p.sendMessage(Messenger.MS+"�̹� �ִ��ο�(+"+maxPlayer+")�Դϴ�.");
			return;
		}
		if(isStart){
			p.sendMessage(Messenger.MS+"�̹� ������ �������Դϴ�.");
			return;
		}
		else{
			playerList.add(p);
			p.teleport(joinPos);
			p.getInventory().clear();
			p.getInventory().setHelmet(null);
			p.getInventory().setChestplate(null);
			p.getInventory().setLeggings(null);
			p.getInventory().setBoots(null);
			p.getInventory().setItem(8, helpItem);
			me.Bokum.EGM.Main.gamelist.put(p.getName(), gameTitle);
			Main.playingList.put(p.getName(), Main.avoidanvil);
			Messenger.sendMessage(playerList, 
					p.getName()+" ���� "+gameTitle+"�� �����ϼ̽��ϴ�. �ο� (��e "+playerList.size()+"��7 / ��c"+minPlayer+" ��f)"
					,true);
			if(!isLobbyStart && playerList.size() >= minPlayer){
				startGame();
			}
			Messenger.sendSound(playerList, Sound.NOTE_PLING, 2.0f, 0.5f);
		}
	}
	
	public void startGame(){
		isLobbyStart = true;
		final int cur = Utility.Getcursch(this);
		tasktime[cur] = 5;
		Bukkit.broadcastMessage(Messenger.MS+"��6��l������ϱ��f�� �� ���۵˴ϴ�.");
		tasknum[cur] = Bukkit.getScheduler().scheduleSyncRepeatingTask(Main.instance, new Runnable(){
			public void run(){
				if(tasktime[cur] > 0){
					Messenger.sendMessage(playerList, "������ "+tasktime[cur]*10+" �� �� ���۵˴ϴ�.", true);
					tasktime[cur]--;
				}
				else{
					Utility.Canceltask(minigame, cur);
					isStart = true;
					for(int i = 0; i < playerList.size(); i++){
						Player p = playerList.get(i);
						p.getInventory().clear();
						p.playSound(p.getLocation(), Sound.ANVIL_LAND, 2.0f, 1.0f);
						p.sendMessage(Messenger.MS+"������ ���� �ƽ��ϴ�.");
						p.getInventory().setItem(0, jumpSkill);
						p.addPotionEffect(new PotionEffect(PotionEffectType.JUMP, 72000, 0));
					}
					timer();
				}
			}
		}, 200L, 200L);
	}
	
	public void timer(){
		Messenger.sendMessage(playerList, "�ϴÿ��� �������� ��縦 ���ϼ���!", true);
		final int x1 = pos1.getBlockX() >= pos2.getBlockX() ? pos2.getBlockX() : pos1.getBlockX();
		final int x2 = pos1.getBlockX() >= pos2.getBlockX() ? pos1.getBlockX() : pos2.getBlockX();
		final int y = pos1.getBlockY() >= pos2.getBlockY() ? pos1.getBlockY() : pos2.getBlockY();
		final int miny = pos1.getBlockY() >= pos2.getBlockY() ? pos2.getBlockY() : pos1.getBlockY();
		final int remove_max_y = miny+4;
		final int z1 = pos1.getBlockZ() >= pos2.getBlockZ() ? pos2.getBlockZ() : pos1.getBlockZ();
		final int z2 = pos1.getBlockZ() >= pos2.getBlockZ() ? pos1.getBlockZ() : pos2.getBlockZ();
		final int cur = Utility.Getcursch(this);
		tasktime[cur] = 0;
		tasknum[cur] = Bukkit.getScheduler().scheduleSyncRepeatingTask(Main.instance, new Runnable(){
			public void run(){
				if(!isGameEnd){
					for(int i = 0; i < anvilcnt; i++){
						Location l = new Location(pos1.getWorld(), Utility.Getrandom(x1, x2), 
								y, Utility.Getrandom(z1, z2));
						if(l.getBlock().getType() == Material.AIR){
							l.getBlock().setType(Material.ANVIL);
						}	
					}
					if(++tasktime[cur]%100==0){
						for(int x = x1; x <= x2; x++){
							for(int z = z1; z <= z2; z++){
								for(int remove_min_y = miny; remove_min_y < remove_max_y; remove_min_y++){
									Location l2 = new Location(pos1.getWorld(), x, miny, z);
									if(l2.getBlock().getType() == Material.ANVIL) l2.getBlock().setType(Material.AIR);
								}
							}
						}
						if(tasktime[cur]%200==0) {
							anvilcnt++;
							tasktime[cur] = 0;
						}
					}
				}else{
					Utility.Canceltask(minigame, cur);
				}
			}
		}, 100l, 2l);
	}
	
	public void gameQuit(Player p){
		if(!playerList.contains(p)) return;
		Main.playingList.remove(p.getName());
		playerList.remove(p);
		if(!isStart) return;
		Messenger.sendMessage(playerList, p.getName()+" ��f���� Ż���Ǽ̽��ϴ�.", true);
		if(playerList.size() <= 1){
			try{
				winPlayer(playerList.get(0));
			}catch(Exception e){
				
			}
		}
	}
	
	public void gameHelper(Player p, int slot){
		switch(slot){
			case 11:
				p.sendMessage("��7���� �̸� ��f: ��c������ϱ�");
				p.sendMessage("��f������ ���۵� �� �ϴÿ��� ��簡 �������� �����մϴ�.");
				p.sendMessage("��f�ϴÿ��� �������� ��縦 ��� ���Ͽ� ����������");
			 	p.sendMessage("��f��Ƴ��� �Ǹ� �¸��ϰ� �˴ϴ�.");
			 	p.sendMessage("��f���� ���븦 ��Ŭ���� �������� 3�ʰ� ��ȭ�˴ϴ�.");
				p.closeInventory();
				return;
				  
			case 13:
				p.sendMessage("��f�������� �������� �ʽ��ϴ�.");
				p.sendMessage("��fPVP�� �Ұ����մϴ�.");
				p.closeInventory();
				return;
				  
			default: return;
			}  
	}
	
	public void winPlayer(final Player p){
		isGameEnd = true;
		p.playSound(p.getLocation(), Sound.ANVIL_LAND, 2.5f, 1.5f);
		Messenger.sendMessage(playerList, "����� ���ķ� ���ҽ��ϴ�!", true);
		try{
		Bukkit.getScheduler().scheduleSyncDelayedTask(Main.instance, new Runnable()
		{
			public void run()
			{
				EconomyResponse r = Main.econ.depositPlayer(p.getName(), 500);
				if (r.transactionSuccess()) {
					p.sendMessage(ChatColor.GOLD + "�¸� �������� 500���� �����̽��ϴ�.");
				}
				try{
					me.Bokum.EGM.Main.Spawn(p);
					} catch(Exception e){
					p.teleport(Main.lobby);
					}
				clearData();
				Bukkit.broadcastMessage(Messenger.MS+"��6��l������ϱ��f�� ��9"+p.getName()+"��f���� �¸��� ���� �ƽ��ϴ�.");
			}
				}, 140L);
		}catch(Exception e){
	    	forceStop();
	    }
	}
	
	//�̺�Ʈ
	
	public void onBlockPlace(BlockPlaceEvent e){
		if(!(e.getPlayer() instanceof Player)) return;
		Player p = e.getPlayer();
		if(!playerList.contains(p)) return;
		e.setCancelled(true);
	}
	
	public void onBlockBreak(BlockBreakEvent e){
		if(!(e.getPlayer() instanceof Player)) return;
		Player p = e.getPlayer();
		if(!playerList.contains(p)) return;
		e.setCancelled(true);
	}
	
	public void onQuit(PlayerQuitEvent e){
		if(!playerList.contains(e.getPlayer())) return;
		gameQuit(e.getPlayer());
	}
	
	public void onEntityDamage(EntityDamageEvent e){
		if(!(e.getEntity() instanceof Player)) return;
		Player p = (Player) e.getEntity();
		if(!playerList.contains(p)) return;
		if(!(e.getCause() == DamageCause.FALLING_BLOCK)){
			e.setCancelled(true);
		}else{
			e.setDamage(15);
		}
	}

	public void onRegainHealth(EntityRegainHealthEvent e){
		e.setCancelled(true);
	}
	public void onDropItem(PlayerDropItemEvent e){
		if(!playerList.contains(e.getPlayer())) return;
		e.setCancelled(true);
	}
	
	public void onPlayerDeath(PlayerDeathEvent e){
		if(!playerList.contains(e.getEntity())) return;
		gameQuit(e.getEntity());
	}
	
	public void onInteract(PlayerInteractEvent e){
		final Player p = (Player) e.getPlayer();
		if(!playerList.contains(p)) return;
		if(e.getAction() == Action.RIGHT_CLICK_AIR || e.getAction() == Action.RIGHT_CLICK_BLOCK){
			if(e.getItem() != null){
				if(e.getItem().getType() == Material.STICK && cooldown.checkCooldown(p, "������ȭ")){
					p.removePotionEffect(PotionEffectType.JUMP);
					p.addPotionEffect(new PotionEffect(PotionEffectType.JUMP, 60, 2));
					p.sendMessage(Messenger.MS+"3�ʰ� �������� ��ȭ�˴ϴ�.");
					cooldown.setCooldown(p, "������ȭ", 10, false, 0);
					Bukkit.getScheduler().scheduleSyncDelayedTask(Main.instance, new Runnable(){
						public void run(){
							p.addPotionEffect(new PotionEffect(PotionEffectType.JUMP, 72000, 0));
						}
					}, 60l);
				}else if(e.getItem().getType() == Material.COMPASS){
					p.openInventory(helpInventory);
				}
			}
		}
	}
	
	public void onClickInventory(InventoryClickEvent e){
		if(!(e.getWhoClicked() instanceof Player)) return;
		Player p = (Player) e.getWhoClicked();
		if(!playerList.contains(p)) return;
		if(e.getInventory().getTitle().equalsIgnoreCase("��c��l�����")){
			gameHelper(p, e.getSlot());
			e.setCancelled(true);
			p.updateInventory();
		}
	}
}
